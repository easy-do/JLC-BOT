package plus.easydo.bot.lowcode.exec;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.lowcode.model.NodeExecuteResult;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.lowcode.model.Node;
import plus.easydo.bot.manager.BotNodeConfExecuteLogManager;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author yuzhanfeng
 * @Date 2024-03-06 11:35
 * @Description 节点执行服务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NodeExecuteServer {

    @Autowired
    private Map<String, NodeExecute> nodeExecMap;

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    private final BotNodeConfExecuteLogManager nodeConfExecuteLogManager;


    private static String getStartNextNodeId(List<Node> startEdgeNodeList) {
        if (Objects.isNull(startEdgeNodeList)) {
            throw new BaseException("找不到开始节点的连线");
        }
        if (startEdgeNodeList.size() > 1) {
            throw new BaseException("开始节点只能有一条连线");
        }
        //开始节点的下一个节点
        Node startEdgeNode = startEdgeNodeList.get(0);
        Node.EdgInfo startEdgeTarget = startEdgeNode.getTarget();
        if (Objects.isNull(startEdgeTarget)) {
            throw new BaseException("找不到开始节点连线定义的下个节点");
        }
        return startEdgeTarget.getCell();
    }

    public List<NodeExecuteResult> execute(LowCodeNodeConf lowCodeNodeConf, Object params) {
        log.debug("========== 节点配置[{}]开始执行 ==========", lowCodeNodeConf.getConfName());
        long startTime = System.currentTimeMillis();
        List<NodeExecuteResult> result = new ArrayList<>();
        JSONObject paramsJson = JSONUtil.createObj();
        if (Objects.nonNull(params)) {
            paramsJson = JSONUtil.parseObj(params);
        }
        Object botNumber = paramsJson.get(OneBotConstants.SELF_ID);
        if (Objects.nonNull(botNumber)) {
            paramsJson.set("botNumber", botNumber);
            paramsJson.set("bot_conf", CacheManager.BOT_CONF_CACHE.get(botNumber));
        }
        String message = paramsJson.getStr(OneBotConstants.MESSAGE);
        if (Objects.nonNull(message)) {
            OneBotMessageParse messageParse = OneBotUtils.parseMessage(message);
            paramsJson.set(OneBotConstants.MESSAGE, messageParse);
        }
        String nodeStr = lowCodeNodeConf.getNodeData();
        JSONObject nodeStrJson = JSONUtil.parseObj(nodeStr);
        String confStr = lowCodeNodeConf.getConfData();
        JSONObject confJson = JSONUtil.parseObj(confStr);
        JSONArray nodeObjArray = nodeStrJson.getJSONArray("cells");
        //所有node集合
        List<Node> allNodeList = nodeObjArray.stream().map(nodeObj -> JSONUtil.toBean(JSONUtil.parseObj(nodeObj), Node.class)).toList();
        Map<String, Node> nodeMap = allNodeList.stream().collect(Collectors.toMap(Node::getId, node -> node));
        Map<String, Node> nodeShapeMap = new HashMap<>();
        allNodeList.forEach(node -> nodeShapeMap.put(node.getShape(), node));
        //先找开始节点
        Node startNode = nodeShapeMap.get(LowCodeConstants.START_NODE);
        if (Objects.isNull(startNode)) {
            throw new BaseException("没有配置开始节点");
        }
        result.add(NodeExecuteResult.builder().nodeId(startNode.getId()).nodeName(startNode.getLabel()).nodeCode(startNode.getShape()).executeTime(0L).build());
        //找结束节点
        Node endNode = nodeShapeMap.get(LowCodeConstants.END_NODE);
        if (Objects.isNull(endNode)) {
            throw new BaseException("没有配置结束节点");
        }
        // 所有port的map
        Map<String, List<Node.Port.Item>> portMap = new HashMap<>();
        // 连线map 来源节点id为key value是连线
        Map<String, List<Node>> sourceEdgeMap = new HashMap<>();
        allNodeList.forEach(node -> {
            if (CharSequenceUtil.equals(node.getShape(), "edge")) {
                //连线
                Node.EdgInfo source = node.getSource();
                if (Objects.nonNull(source)) {
                    List<Node> list = sourceEdgeMap.get(source.getCell());
                    if (Objects.isNull(list)) {
                        list = new ArrayList<>();
                    }
                    list.add(node);
                    sourceEdgeMap.put(source.getCell(), list);
                }
            } else {
                //节点
                portMap.put(node.getId(), node.getPorts().getItems());
            }
        });
        List<Node> startEdgeNodeList = sourceEdgeMap.get(startNode.getId());
        //开始节点的下一个节点
        String startNextNodeId = getStartNextNodeId(startEdgeNodeList);
        Node startNextNode = nodeMap.get(startNextNodeId);
        if (Objects.isNull(startNextNode)) {
            throw new BaseException("开始节点的下个节点不存在");
        }
        //依次执行后面的节点
        String currentShape;
        Node currentNode = startNextNode;
        boolean error = false;
        boolean currentNodeExecuteSuccess = false;
        long execTime;
        do {
            currentShape = currentNode.getShape();
            NodeExecute nodeExecute = nodeExecMap.get(currentShape);
            if (Objects.isNull(nodeExecute)) {
                throw new BaseException("没有找到类型为[" + currentNode.getShape() + "]的节点执行器");
            }
            //校验节点连线
            List<Node> edgeNodeList = sourceEdgeMap.get(currentNode.getId());
            if (Objects.isNull(edgeNodeList)) {
                throw new BaseException("节点[" + currentNode.getLabel() + "]没有配置连线");
            }
            //是否为判断节点
            boolean isElseNode = CharSequenceUtil.startWith(currentShape, "if_else_");
            if (isElseNode) {
                List<Node.Port.Item> ports = portMap.get(currentNode.getId());
                if (edgeNodeList.size() != 2) {
                    throw new BaseException("判断节点[" + currentNode.getLabel() + "]的目标连线必须为两条");
                }
                if (Objects.isNull(ports)) {
                    throw new BaseException("没有找到判断节点[" + currentNode.getLabel() + "]的port,应该至少定义top、left、right三个节点");
                }
                if (ports.isEmpty()) {
                    throw new BaseException("条件节点[" + currentNode.getLabel() + "]的port为空");
                }
            }

            if (!CharSequenceUtil.equals(currentShape, LowCodeConstants.END_NODE)) {
                //节点执行  失败 成功 异常应给出报错信息
                long nodeStartTime = System.currentTimeMillis();
                ExecuteResult res = null;
                try {
                    res = nodeExecute.execute(paramsJson, confJson.getJSONObject(currentNode.getId()));
                    Node finalCurrentNode = currentNode;
                    CompletableFuture.runAsync(() -> saveNodeExecLog(lowCodeNodeConf, finalCurrentNode, System.currentTimeMillis() - nodeStartTime));
                    currentNodeExecuteSuccess = res.isSuccess();
                } catch (Exception e) {
                    error = true;
                    result.add(NodeExecuteResult.builder()
                            .nodeId(currentNode.getId())
                            .nodeName(currentNode.getLabel())
                            .nodeCode(currentNode.getShape())
                            .status(2)
                            .message(ExceptionUtil.getMessage(e))
                            .executeTime(System.currentTimeMillis() - nodeStartTime)
                            .build());
                }
                if (!error) {
                    result.add(NodeExecuteResult.builder()
                            .nodeId(currentNode.getId())
                            .nodeName(currentNode.getLabel())
                            .nodeCode(currentNode.getShape())
                            .data(res.getData())
                            .status(currentNodeExecuteSuccess ? 1 : 0)
                            .message(res.getMessage())
                            .executeTime(System.currentTimeMillis() - nodeStartTime)
                            .build());
                }
                if (!error && currentNodeExecuteSuccess) {
                    if (isElseNode) {
                        // 如果是条件判断节点则根据结果处理连线
                        String portGroup = (Boolean) res.getData() ? "left" : "right";
                        Node portGroupEdge = null;
                        //用每个节点的来源port和当前节点的所有port进行对比筛选
                        for (Node edgeNode : edgeNodeList) {
                            //连线的来源节点portId
                            String edgeSourcePortId = edgeNode.getSource().getPort();
                            //当前节点的所有port
                            List<Node.Port.Item> currentNodePorts = portMap.get(currentNode.getId());
                            for (Node.Port.Item item : currentNodePorts) {
                                // 如果port的group和id都相同 则命中
                                if (CharSequenceUtil.equals(item.getGroup(), portGroup) && CharSequenceUtil.equals(item.getId(), edgeSourcePortId)) {
                                    portGroupEdge = edgeNode;
                                }
                            }
                        }
                        if (Objects.isNull(portGroupEdge)) {
                            throw new BaseException("未找到判断节点[" + currentNode.getLabel() + "]中group为[" + portGroup + "]的port");
                        }
                        //找到连线，往下走
                        currentNode = nodeMap.get(portGroupEdge.getTarget().getCell());
                    } else {

                        // 其他节点 暂时只支持单个连线
                        if (edgeNodeList.size() > 1) {
                            throw new BaseException("节点[" + currentNode.getLabel() + "]的目标连线超过两条");
                        }
                        Node edgeNode = edgeNodeList.get(0);
                        currentNode = nodeMap.get(edgeNode.getTarget().getCell());
                    }
                }
            }
        } while (Objects.nonNull(currentNode) && !error && currentNodeExecuteSuccess && !CharSequenceUtil.equals(currentNode.getShape(), LowCodeConstants.END_NODE));
        if (CharSequenceUtil.equals(currentNode.getShape(), LowCodeConstants.END_NODE) && !error && currentNodeExecuteSuccess) {
            result.add(NodeExecuteResult.builder().nodeId(endNode.getId()).nodeName(endNode.getLabel()).nodeCode(endNode.getShape()).executeTime(0L).build());
        }
        CompletableFuture.runAsync(() -> saveNodeConfExecLog(lowCodeNodeConf, System.currentTimeMillis() - startTime));
        log.debug("========== 节点配置[{}]处理器执行结束,耗时{}ms ==========", lowCodeNodeConf.getConfName(), System.currentTimeMillis() - startTime);
        return result;
    }

    private void saveNodeConfExecLog(LowCodeNodeConf lowCodeNodeConf, long execTime) {
        nodeConfExecuteLogManager.save(BotNodeConfExecuteLog.builder()
                .confId(lowCodeNodeConf.getId())
                .confName(lowCodeNodeConf.getConfName())
                .executeTime(execTime)
                .createTime(LocalDateTimeUtil.now())
                .build());
    }

    private void saveNodeExecLog(LowCodeNodeConf lowCodeNodeConf, Node currentNode, long execTime) {
        nodeExecuteLogManager.save(BotNodeExecuteLog.builder()
                .confId(lowCodeNodeConf.getId())
                .confName(lowCodeNodeConf.getConfName())
                .nodeCode(currentNode.getShape())
                .nodeName(currentNode.getLabel())
                .executeTime(execTime)
                .createTime(LocalDateTimeUtil.now())
                .build());
    }

}
