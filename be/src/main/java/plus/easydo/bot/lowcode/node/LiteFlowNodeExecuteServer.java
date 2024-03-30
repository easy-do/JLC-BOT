package plus.easydo.bot.lowcode.node;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.model.Node;
import plus.easydo.bot.manager.BotNodeConfExecuteLogManager;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description LiteFlow的节点执行服务
 */
@Component
@RequiredArgsConstructor
public class LiteFlowNodeExecuteServer {

    private final FlowExecutor flowExecutor;

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    private final BotNodeConfExecuteLogManager nodeConfExecuteLogManager;

    public LiteflowResponse execute(LowCodeNodeConf lowCodeNodeConf, JSONObject paramsJson) {

        StringBuilder stringBuilder = new StringBuilder();

        //将节点配置转为EL表达式
        String elStart = "THEN(";
        String elEnd = ");";

        stringBuilder.append(elStart);
        String toElData = nodeConfToLiteFlowEL(lowCodeNodeConf);
        stringBuilder.append(toElData);
        stringBuilder.append(elEnd);

        String elData = stringBuilder.toString();
        String chainName = "botChain" + lowCodeNodeConf.getId();
        LiteFlowChainELBuilder.createChain().setChainName(chainName).setEL(elData).build();

        //初始化上下文
        JLCLiteFlowContext context = new JLCLiteFlowContext();
        context.setParam(paramsJson);
        String confData = lowCodeNodeConf.getConfData();
        context.setNodeConf(JSONUtil.parseObj(confData));
        //执行并返回结果
        LiteflowResponse res = flowExecutor.execute2Resp(chainName, "", context);
        CompletableFuture.runAsync(() -> saveExecuteLog(lowCodeNodeConf, res));
        return res;
    }


    private void saveExecuteLog(LowCodeNodeConf lowCodeNodeConf, LiteflowResponse res) {
        Queue<CmpStep> cmpSteps = res.getExecuteStepQueue();
        long timeNum = 0L;
        for (CmpStep cmpStep : cmpSteps) {
            com.yomahub.liteflow.flow.element.Node node = cmpStep.getRefNode();
            nodeExecuteLogManager.save(BotNodeExecuteLog.builder()
                    .confId(lowCodeNodeConf.getId())
                    .confName(lowCodeNodeConf.getConfName())
                    .nodeCode(node.getId())
                    .nodeName(node.getName())
                    .executeTime(timeNum)
                    .createTime(LocalDateTimeUtil.now())
                    .build());
            timeNum += cmpStep.getTimeSpent();
        }
        nodeConfExecuteLogManager.save(BotNodeConfExecuteLog.builder()
                .confId(lowCodeNodeConf.getId())
                .confName(lowCodeNodeConf.getConfName())
                .executeTime(timeNum)
                .createTime(LocalDateTimeUtil.now())
                .build());
    }

    private String nodeConfToLiteFlowEL(LowCodeNodeConf lowCodeNodeConf) {
        String nodeStr = lowCodeNodeConf.getNodeData();
        JSONObject nodeStrJson = JSONUtil.parseObj(nodeStr);
        JSONArray nodeObjArray = nodeStrJson.getJSONArray("cells");
        //所有node集合
        List<Node> allNodeList = nodeObjArray.stream().map(nodeObj -> JSONUtil.toBean(JSONUtil.parseObj(nodeObj), Node.class)).toList();
        Map<String, Node> nodeMap = allNodeList.stream().collect(Collectors.toMap(Node::getId, node -> node));
        Map<String, Node> nodeShapeMap = new HashMap<>();
        allNodeList.forEach(node -> nodeShapeMap.put(node.getShape(), node));

        //查找并校验开始节点
        Node startNode = getStartNode(nodeShapeMap);
        //查找并校验结束节点
        getEndNode(nodeShapeMap);

        // 所有port的map 节点id为key
        Map<String, List<Node.Port.Item>> portMap = new HashMap<>();
        // 连线map 来源节点id为key value是连线
        Map<String, List<Node>> sourceEdgeMap = new HashMap<>();
        buildPortAndSourceEdgeMap(allNodeList, sourceEdgeMap, portMap);
        //开始节点后的首个节点
        Node firstNode = getFirstNode(nodeMap, sourceEdgeMap, startNode);
        //遍历解析剩余节点
        return startNode.getShape() + ", " + buildEl(null, firstNode, sourceEdgeMap, nodeMap, portMap);
    }

    private String buildEl(Node parentNode, Node currentNode, Map<String, List<Node>> sourceEdgeMap, Map<String, Node> nodeMap, Map<String, List<Node.Port.Item>> portMap) {
        if (Objects.nonNull(currentNode)) {
            StringBuilder result = new StringBuilder();
            if (!CharSequenceUtil.equals(currentNode.getShape(), LowCodeConstants.END_NODE)) {
                String currentShape = currentNode.getShape();
                String currentNodeId = currentNode.getId();
                //校验节点连线
                List<Node> edgeNodeList = sourceEdgeMap.get(currentNode.getId());
                if (Objects.isNull(edgeNodeList)) {
                    throw new BaseException("节点[" + currentNode.getLabel() + "]没有配置连线");
                }
                //是否为判断节点
                boolean ifNode = isIfNode(currentNode);
                if (ifNode) {
                    checkIfNodePorts(portMap, currentNode, edgeNodeList);
                    List<Node> nodes = getLeftAndRightNode(edgeNodeList, nodeMap, portMap, currentNode);
                    Node leftNode = nodes.get(0);
                    Node rightNode = nodes.get(1);

                    //找到连线 构建EL表达式  IF(x, a, b)
                    result.append(CharSequenceUtil.format("IF(node(\"{}\").tag(\"{}\"), ", currentShape, currentNodeId));

                    String leftRes = buildEl(currentNode, leftNode, sourceEdgeMap, nodeMap, portMap);
                    if (Objects.nonNull(leftRes)) {
                        result.append(CharSequenceUtil.format("{}, ", leftRes));
                    } else {
                        result.append(CharSequenceUtil.format("node(\"{}\").tag(\"{}\"), ", leftNode.getShape(), leftNode.getId()));
                    }
                    String rightRes = buildEl(currentNode, rightNode, sourceEdgeMap, nodeMap, portMap);
                    if (Objects.nonNull(rightRes)) {
                        result.append(CharSequenceUtil.format("{}", rightRes));
                    } else {
                        result.append(CharSequenceUtil.format("node(\"{}\").tag(\"{}\")", rightNode.getShape(), rightNode.getId()));
                    }
                    result.append(")");
                    return result.toString();
                } else {
                    // 单个连线的节点 c, d
                    if (edgeNodeList.size() > 1) {
                        throw new BaseException("节点[" + currentNode.getLabel() + "]的目标连线超过两条");
                    }
                    Node childrenNode = nodeMap.get(edgeNodeList.get(0).getTarget().getCell());
                    if (isIfNode(parentNode)) {
                        result.append("THEN(");
                    }
                    if (Objects.nonNull(childrenNode)) {
                        result.append(CharSequenceUtil.format("node(\"{}\").tag(\"{}\"), ", currentShape, currentNodeId));
                        String res = buildEl(parentNode, childrenNode, sourceEdgeMap, nodeMap, portMap);
                        if (Objects.nonNull(res)) {
                            result.append(res);
                        }
                    } else {
                        result.append(CharSequenceUtil.format("node(\"{}\").tag(\"{}\")", childrenNode.getShape(), childrenNode.getId()));
                    }

                    if (isIfNode(parentNode)) {
                        result.append(")");
                    }
                }
                return result.toString();
            } else {
                result.append(CharSequenceUtil.format("node(\"{}\").tag(\"{}\")", currentNode.getShape(), currentNode.getId()));
                return result.toString();
            }
        }
        return null;
    }


    private boolean isIfNode(Node node) {
        return Objects.nonNull(node) && StrUtil.containsAny(node.getShape(), "if", "If");
    }

    /**
     * 找到判断节点左右两个连线的目标节点
     *
     * @param edgeNodeList edgeNodeList
     * @param portMap      portMap
     * @param currentNode  currentNode
     * @return void
     * @author laoyu
     * @date 2024-03-27
     */
    private List<Node> getLeftAndRightNode(List<Node> edgeNodeList, Map<String, Node> nodeMap, Map<String, List<Node.Port.Item>> portMap, Node currentNode) {
        //用每个节点的来源port和当前节点的所有port进行对比筛选以找到两个目标节点
        Node leftNode = null;
        Node rightNode = null;
        for (Node edgeNode : edgeNodeList) {
            //连线的来源节点portId
            String edgeSourcePortId = edgeNode.getSource().getPort();
            //当前节点的所有port
            List<Node.Port.Item> currentNodePorts = portMap.get(currentNode.getId());
            for (Node.Port.Item item : currentNodePorts) {
                if (Objects.isNull(leftNode) || Objects.isNull(rightNode)) {
                    // 如果port的group和id都相同 则命中
                    if (CharSequenceUtil.equals(item.getGroup(), "left") && CharSequenceUtil.equals(item.getId(), edgeSourcePortId)) {
                        leftNode = nodeMap.get(edgeNode.getTarget().getCell());
                    }
                    if (CharSequenceUtil.equals(item.getGroup(), "right") && CharSequenceUtil.equals(item.getId(), edgeSourcePortId)) {
                        rightNode = nodeMap.get(edgeNode.getTarget().getCell());
                    }
                }
            }
        }
        if (Objects.isNull(leftNode) || Objects.isNull(rightNode)) {
            throw new BaseException("判断节点[" + currentNode.getLabel() + "]的port不全");
        }
        List<Node> nodes = new ArrayList<>();
        nodes.add(leftNode);
        nodes.add(rightNode);
        return nodes;
    }

    /**
     * 判断条件节点的port是否合法
     *
     * @param portMap      portMap
     * @param currentNode  currentNode
     * @param edgeNodeList edgeNodeList
     * @return void
     * @author laoyu
     * @date 2024-03-27
     */
    private void checkIfNodePorts(Map<String, List<Node.Port.Item>> portMap, Node currentNode, List<Node> edgeNodeList) {
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


    private Node getStartNode(Map<String, Node> nodeShapeMap) {
        Node startNode = nodeShapeMap.get(LowCodeConstants.START_NODE);
        if (Objects.isNull(startNode)) {
            throw new BaseException("没有配置开始节点");
        }
        return startNode;
    }

    private Node getEndNode(Map<String, Node> nodeShapeMap) {
        Node endNode = nodeShapeMap.get(LowCodeConstants.END_NODE);
        if (Objects.isNull(endNode)) {
            throw new BaseException("没有配置结束节点");
        }
        return endNode;
    }

    /**
     * 都贱port和连线的map
     *
     * @param allNodeList   allNodeList
     * @param sourceEdgeMap sourceEdgeMap
     * @param portMap       portMap
     * @return void
     * @author laoyu
     * @date 2024-03-27
     */
    private void buildPortAndSourceEdgeMap(List<Node> allNodeList, Map<String, List<Node>> sourceEdgeMap, Map<String, List<Node.Port.Item>> portMap) {
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
    }

    private Node getFirstNode(Map<String, Node> nodeMap, Map<String, List<Node>> sourceEdgeMap, Node startNode) {
        //开始节点的连线
        List<Node> startEdgeNodeList = sourceEdgeMap.get(startNode.getId());
        if (Objects.isNull(startEdgeNodeList) || startEdgeNodeList.isEmpty()) {
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
        String startNextNodeId = startEdgeTarget.getCell();
        Node firstNode = nodeMap.get(startNextNodeId);
        if (Objects.isNull(firstNode)) {
            throw new BaseException("开始节点的下个节点不存在");
        }
        return firstNode;
    }
}
