import React, { useRef, useState, useEffect } from 'react';
import { Button, Dropdown, Input, message, Modal } from 'antd';
import { Edge, Graph } from '@antv/x6';
import { Dnd } from '@antv/x6-plugin-dnd';
import { register } from '@antv/x6-react-shape';
import { portMap } from '../../utils/ports';
import './index.less';
import { Export } from '@antv/x6-plugin-export';
import { Transform } from '@antv/x6-plugin-transform';
import { Selection } from '@antv/x6-plugin-selection';
import { Snapline } from '@antv/x6-plugin-snapline';
import { Keyboard } from '@antv/x6-plugin-keyboard';
import { Clipboard } from '@antv/x6-plugin-clipboard';
import { History } from '@antv/x6-plugin-history';
import { Scroller } from '@antv/x6-plugin-scroller';
import { reset, showPorts, removeNode } from '../../utils/method';
import { MiniMap } from '@antv/x6-plugin-minimap';
import { PlayCircleOutlined, SaveOutlined } from '@ant-design/icons';
import { listSysNode } from '@/services/jlc-bot/lowCodeSysNodeController';
import { PageContainer } from '@ant-design/pro-layout';
import EditEdgeData from './editEdgeData';
import EditNodeData from './editNodeData';
import { saveNodeConf, getNodeConf, debugNodeConf } from '@/services/jlc-bot/lowCodeController';
import { updateNodeConf } from '@/services/jlc-bot/lowCodeController';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import NodeExecutetResultVivew from './nodeExecutetResultVivew';

import styles from './index.less';


function EditNodeConf(props: { location: { search: string | string[][] | Record<string, string> | URLSearchParams | undefined; }; }) {
  let graph: Graph;
  const dndRef = useRef();
  const graphRef = useRef();
  const containerRef = useRef(null);
  const dndContainerRef = useRef(null);
  const [newGraph, setNewGraph] = useState(null); // 画布
  const [currentNode, setCurrentNode] = useState(); // 双击选中的节点
  const [currentSysNode, setCurrentSysNode] = useState<API.LowCodeSysNode>(); // 双击选中节点对应系统节点信息
  const sysNodeCodeMap = new Map();
  const sysNodeMap = new Map();
  const editEdgeLableFlag = new Map();
  const [sysNodeConf, setSysNodeConf] = useState<API.LowCodeNodeConf>();
  const [nodeConf, setNodeConf] = useState<any>({});
  const searchParams = new URLSearchParams(props.location.search); let confId = searchParams.get('confId');

  const [ulData, setUlData] = useState([]);


  //编辑节点弹窗
  const [isEditNodeOpen, setIsEditNodeModalOpen] = useState(false);
  const opneEditNodeModal = (node: any) => {
    setCurrentNode(node);
    const shape = node.getProp('shape');
    const sysNode = sysNodeCodeMap.get(shape);
    setCurrentSysNode(sysNode);
    setIsEditNodeModalOpen(true);
  }

  const openEditEdgModal = (edge: React.SetStateAction<undefined> | Edge<Edge.Properties>) => {
    setCurrentEdgeData(edge);
    setIsEditEdgeModalOpen(true);
  }

  // 编辑连线弹框
  const [isEditEdgeModalOpen, setIsEditEdgeModalOpen] = useState(false);
  const [currentEdgeData, setCurrentEdgeData] = useState();

  /** 保存编辑配置名称弹窗 */
  const [editConfNameModalVisible, handleEditConfNameModalVisible] = useState<boolean>(false);

  /** 调试配置弹窗 */
  const [debugConfNameModalVisible, handleDebugConfNameModalVisible] = useState<boolean>(false);

  const getAndInitNodeConf = () => {
    getNodeConf({ id: confId }).then(res => {
      if (res.success) {
        setSysNodeConf(res.data);
        setNodeConf(res.data?.nodeConf);
        graph.fromJSON(res.data.nodes);
      }
    })
  }

  // 节点debug结果弹框
  const [isDebuModalOpen, setIsDebuModalOpen] = useState(false);
  const [debugResultData, setDebugResultData] = useState<API.CmpStepResult[]>([]);

  // 沙箱弹框a
  const [sandboxOpen, setSandboxOpen] = useState(false);
  const [websocket, setWebsocket] = useState<WebSocket>();
  const [sandboxMessageList, setSandboxMessageList] = useState([]);
  const [messageInput, setMessageInput] = useState<string>();
  const chatContent = useRef<HTMLDivElement>(null)


  const createSandbox = () => {
    const ws = new WebSocket('ws://localhost:8888/ws/sandbox');
    setSandboxMessageList([]);
    ws.onopen = () => {
      console.log('WebSocket connected');
      if (websocket) {
        websocket.close();
      }
      setWebsocket(ws);
    }
    ws.onmessage = (message) => {
      const a = sandboxMessageList;
      a.push(JSON.parse(message.data));
      setSandboxMessageList(a);
      chatContent.current.scrollTop = chatContent.current?.scrollHeight
    }
    ws.onerror = (error) => console.error('WebSocket error:', error);
    ws.onclose = () => console.log('WebSocket disconnected');

    setSandboxOpen(true);
  }

  const changeMessageInput = (e: any) => {
    setMessageInput(e.target.value);
    chatContent.current.scrollTop = chatContent.current?.scrollHeight
  }

  const sendSandboxMessage = () => {
    if (!messageInput || messageInput == '') {
      message.warn('请先输入消息');
      return;
    }
    websocket?.send(messageInput);
    setMessageInput(undefined);
  }

  // 初始化antvx的window开发工具hook
  // window.__x6_instances__ = [];

  /** 拖拽完成前 */
  const startDrag = (e: React.MouseEvent<HTMLDivElement, MouseEvent>, val: API.LowCodeSysNode) => {
    //判断是否节点数量已达到上限
    const nodes = graph.getNodes();
    let count = 0;
    nodes.map(node => {
      const shape = node.getProp('shape');
      if (shape == val.nodeCode) {
        count++;
      }
    })
    if (val.maxSize > 0 && count >= val.maxSize) {
      message.warning('已放置最大数量');
      return;
    }
    const g = graph ? graph : newGraph;
    //构建node结构
    const node = g?.createNode({
      label: val.nodeName,
      ports: { ...portMap[val.nodePort ? val.nodePort : 'allPorts'] },
      color: val.nodeColor,
      shape: val.nodeCode,
    });
    dndRef.current?.start(node, e?.nativeEvent);
  };

  useEffect(() => {
    //获取节点数据
    listSysNode().then((res) => {
      if (res.success) {
        // 创建空的 Map
        // 遍历对象的属性并添加到 Map 中
        for (let [key, value] of Object.entries(res.data)) {
          sysNodeMap.set(key, value);
        }
        // 构建拖拽节点
        const ulVal: React.SetStateAction<undefined> | JSX.Element[] = [];
        sysNodeMap.forEach((value, key, map) => {
          ulVal.push(
            <li>
              {key}
              <ul>
                {value.map((val: { nodeCode: React.Key | null | undefined; nodeName: boolean | React.ReactChild | React.ReactFragment | React.ReactPortal | null | undefined; }) => {

                  return (
                    <div
                      style={{ marginTop: '10px' }}
                      key={val.nodeCode}
                      onMouseDown={(e) => {
                        startDrag(e, val);
                      }}
                    >
                      {val.nodeName}
                    </div>
                  );
                })}
              </ul>
            </li>,
          );
        });
        setUlData(ulVal);

        // 画布注册
        graph = new Graph({
          container: containerRef.current,
          grid: true,
          panning: true,
          mousewheel: {
            enabled: true,
            zoomAtMousePosition: true,
            modifiers: 'ctrl',
            minScale: 0.4,
            maxScale: 3,
          },

          connecting: {
            router: 'manhattan', // orth
            connector: 'rounded',
            allowBlank: false, // 是否允许连接到画布空白位置的点
            allowLoop: false, // 是否允许创建循环连线，即边的起始节点和终止节点为同一节点
          },
        });

        // 把画布放到开发者工具中
        // window.__x6_instances__.push(graph)

        // 注册节点

        const CustomComponent = ({ node }) => {
          const label = node.prop('label');
          const color = node.prop('color');
          const image = node.store.data.attrs.image['xlink:href']; // 获取图片路径
          return (
            <Dropdown
              menu={{
                items: [
                  {
                    key: 'console',
                    label: '打印节点信息',
                    onClick: () => {
                      console.log('节点信息', node);
                    },
                  },
                  {
                    key: 'remove',
                    label: '删除节点',
                    onClick: () => {
                      graph.removeCells([node]);
                    },
                  },
                ],
              }}
              trigger={['contextMenu']}
            >
              <div
                className="custom-react-node"
                style={{
                  borderRadius: 4,
                  background: color,
                }}
              >
                {image ? <img style={{ width: '15px', marginRight: '5px' }} src={image} alt="Icon" /> : null}
                {label}
              </div>
            </Dropdown>
          );
        };
        for (const value of sysNodeMap.values()) {
          value.map((val: { nodeCode: any; nodeName: any }) => {
            sysNodeCodeMap.set(val.nodeCode, val);
            register({
              shape: val.nodeCode,
              width: 100,
              height: 40,
              attrs: {
                image: {
                  'xlink:href': val.nodeIcon,
                },
              },
              component: (props) => (
                <CustomComponent {...props} image={val.nodeIcon} label={val.nodeName} />
              ),
            });
          });
        }

        // 拖拽工具注册
        const dnd = new Dnd({
          target: graph,
          scaled: false,
          dndContainer: dndContainerRef.current,
          getDragNode: (node) => node?.clone({ keepId: true }), //确保id一致
          getDropNode: (node) => node?.clone({ keepId: true }),
        });

        graphRef.current = graph;
        dndRef.current = dnd;

        graph.on('node:mouseenter', () => {
          const container = document.getElementById('graph-container');
          const ports = container.querySelectorAll('.x6-port-body');
          showPorts(ports, true);
        });
        graph.on('node:mouseleave', () => {
          const container = document.getElementById('graph-container');
          const ports = container.querySelectorAll('.x6-port-body');
          showPorts(ports, false);
        }); // 显示/隐藏连接桩

        graph
          .use(
            new Snapline({
              enabled: true,
            }),
          )
          .use(
            new Selection({
              enabled: true,
            }),
          )
          .use(
            new Keyboard({
              enabled: true,
            }),
          )
          .use(
            new Clipboard({
              enabled: true,
            }),
          )
          .use(
            new History({
              enabled: true,
            }),
          )
          .use(
            new Transform({
              resizing: true,
              rotating: true,
              enabled: true,
            }),
          )
          .use(
            new Scroller({
              enabled: true,
              pageVisible: true,
              pageBreak: true,
              pannable: true,
            }),
          )
          .use(
            new MiniMap({
              container: document.getElementById('minimap'),
            }),
          )
          .use(new Export());

        graph.bindKey('backspace', () => {
          removeNode(graph);
        });
        //撤销
        graph.bindKey('ctrl+z', () => {
          graph.undo();
        });
        //重做
        graph.bindKey('ctrl+y', () => {
          graph.redo();
        });
        //复制
        graph.bindKey('ctrl+c', () => {
          const cells = graph.getSelectedCells();
          if (cells.length) {
            graph.copy(cells);
          }
          return false;
        });
        //粘贴
        graph.bindKey('ctrl+v', () => {
          if (!graph.isClipboardEmpty()) {
            const cells = graph.paste({ offset: 32 });
            graph.cleanSelection();
            graph.select(cells);
          }
          return false;
        });

        /** 单击连接柱 */
        graph.on('edge:click', ({ edge }) => {
          reset(graph);
          edge.attr('line/stroke', 'orange');
        });

        /** 双击连接柱 */
        graph.on('edge:dblclick', ({ edge }) => {
          if (edge) {
            openEditEdgModal(edge);
          }
        });
        /** 拖拽连接柱后放下 */
        graph.on('edge:mouseup', ({ edge }) => {
          if (edge) {
            //拿到源头的节点和portId
            const sourceNode = edge.getSourceNode();
            const sourcePortId = edge.getSourcePortId();
            const edgeId = edge.getProp("id")
            if (sourcePortId && !editEdgeLableFlag.get(edgeId)) {
              //拿到port信息
              const port = sourceNode?.getPort(sourcePortId);
              //拿到节点的shape
              const shape = sourceNode?.getProp('shape');
              if (shape?.startsWith('if_else_') && port?.group == 'left') {
                //如果是ifelse节点并且是左port 则命名如果
                edge.setLabels('如果');
              } else if (shape?.startsWith('if_else_') && port?.group == 'right') {
                //如果是ifelse节点并且是右port 则命名否则
                edge.setLabels('否则');
              } else {
                edge.setLabels('继续');
              }
              editEdgeLableFlag.set(edgeId, 1);
            }
          }
        });



        /** 单击节点 */
        graph.on('node:click', ({ node }) => {
          reset(graph);
          node.attr('body/stroke', 'orange');
          //console.log(node.getParentId(), 'Id'); // 获取父节点
        });

        /** 双击节点 */
        graph.on('cell:dblclick', ({ node }) => {
          if (node) {
            opneEditNodeModal(node);
          }
        });
        // 如果传id则加载配置信息
        if (confId) {
          getAndInitNodeConf();
        }
        setNewGraph(graph);
      }
    });
  }, []);


  return (
    <PageContainer>
      <div className="Flow">
        <div className="graph" style={{ display: 'flex' }}>
          {/* 拖拽框 */}
          <div className="dnd-wrap" ref={dndContainerRef}>
            <div>
              <li>
                <ul>{ulData}</ul>
              </li>
            </div>
          </div>
          {/* 画布 */}
          <div id="graph-container" ref={containerRef}></div>
        </div>
        {/* 编辑节点组件 */}
        <EditNodeData node={currentNode} sysNode={currentSysNode} visible={isEditNodeOpen} handleVisible={setIsEditNodeModalOpen} nodeConf={nodeConf} setNodeConf={setNodeConf} />
        {/* 编辑连线组件 */}
        <EditEdgeData visible={isEditEdgeModalOpen} handleVisible={setIsEditEdgeModalOpen} edgeData={currentEdgeData} />
        {/* debug执行结果 */}
        <NodeExecutetResultVivew visible={isDebuModalOpen} handleVisible={setIsDebuModalOpen} resultList={debugResultData} />
        {/* 小地图 */}
        <div
          id="minimap"
          style={{
            position: 'fixed',
            cursor: 'pointer',
            right: '60px',
            top: '100px',
            zIndex: 99,
          }}
        ></div>
        <Button
          type="primary"
          icon={<SaveOutlined />}
          style={{
            position: 'fixed',
            cursor: 'pointer',
            right: '0',
            top: '320px',
            zIndex: 99,
          }}
          onClick={() => {
            handleEditConfNameModalVisible(true);
          }
          }
        >
          保存
        </Button>
        <Button
          type="primary"
          icon={<PlayCircleOutlined />}
          style={{
            position: 'fixed',
            cursor: 'pointer',
            right: '0',
            top: '360px',
            zIndex: 99,
          }}
          onClick={() => {
            handleDebugConfNameModalVisible(true);
          }}
        >
          调试
        </Button>
        <Button
          type="primary"
          icon={<PlayCircleOutlined />}
          style={{
            position: 'fixed',
            cursor: 'pointer',
            right: '0',
            top: '400px',
            zIndex: 99,
          }}
          onClick={() => {
            createSandbox(true);
          }}
        >
          沙箱
        </Button>
        <ModalForm
          modalProps={{
            destroyOnClose: true,
          }}
          initialValues={sysNodeConf}
          title="保存节点配置"
          visible={editConfNameModalVisible}
          onVisibleChange={handleEditConfNameModalVisible}
          onFinish={(values) => {
            if (confId && sysNodeConf) {
              updateNodeConf({ id: confId, confName: values.confName, eventType: values.eventType, nodes: newGraph.toJSON(), nodeConf: nodeConf }).then(res => {
                message.success(res.message);
              })
            } else {
              saveNodeConf({ nodes: newGraph.toJSON(), confName: values.confName, nodeConf: nodeConf }).then(res => {
                message.success(res.message);
                confId = res.data + '';
                getAndInitNodeConf();
              })
            }
          }}
        >
          <ProFormText
            name="confName"
            label="配置名称"
            rules={[
              {
                required: true,
                message: '请输入配置名称',
              },
            ]}
          />
          <ProFormSelect
            name="eventType"
            label="事件类型"
            rules={[
              {
                required: true,
                message: '请选择事件类型',
              },
            ]}
            options={[
              {
                label: '所有事件',
                value: 'all',
              },
              {
                label: '接消息',
                value: 'message',
              },
              {
                label: '通知',
                value: 'notice',
              },
              {
                label: '元事件',
                value: 'meta_event',
              },
              {
                label: '游戏角色消息',
                value: 'game_user_send_msg',
              },
              {
                label: '装备拾取事件',
                value: 'pick_up_item_event',
              },
            ]}
          />
        </ModalForm>
        <ModalForm
          modalProps={{
            destroyOnClose: true,
          }}
          initialValues={sysNodeConf}
          title="调试节点配置"
          visible={debugConfNameModalVisible}
          onVisibleChange={handleDebugConfNameModalVisible}
          onFinish={(values) => {
            if (confId && sysNodeConf) {
              console.log(newGraph.toJSON());
              debugNodeConf(values).then(res => {
                if (res.success) {
                  message.success('调试成功');
                  setDebugResultData(res.data);
                  setIsDebuModalOpen(true);
                }
              })
            } else {

            }
          }}
        >
          <ProFormText name='id' hidden />
          <ProFormTextArea
            name="params"
            label="模拟参数(json)"
            initialValue={'{"data":test}'}
            rules={[
              {
                required: true,
                message: '请填写模拟参数',
              },
            ]}
          />
        </ModalForm>
        <Modal
          open={sandboxOpen}
          onCancel={() => {
            setSandboxOpen(false);
            websocket?.close();
            setSandboxMessageList([]);
          }}
          okText={undefined}
          destroyOnClose
          title={'沙箱测试'}
          footer={null}
          style={{ minHeight: '50%', minWidth: '30%' }}
        >
          <div className={styles.chatRoom}>
            <div className={styles.chatContent} ref={chatContent}>
              {sandboxMessageList?.map((chatItem) => {
                if (!chatItem?.isSelf) {
                  return (
                    <div className={styles.chatLeft}>
                      {/* <img src={(chatItem?.isSelf ? chatItem?.expertImgUrl : chatItem?.userImgUrl) ?? 'jlc'} alt="" /> */}
                      <div className={styles.info}>
                        <div className={styles.name}>{'JLC-BOT'}</div>
                        <div className={styles.textCon}>
                          <div className={styles.text}>{chatItem?.message}</div>
                        </div>
                      </div>
                    </div>
                  )
                } else {
                  return (
                    <div className={styles.chatRight}>
                      {/* <img src={(chatItem?.isSelf ? chatItem?.userImgUrl : chatItem?.expertImgUrl) ?? 'me'} alt="" /> */}
                      <div className={styles.info}>
                        <div className={styles.name}>{'我'}</div>
                        <div className={styles.textCon}>
                          <div className={styles.text}>{chatItem?.message}</div>
                        </div>
                      </div>
                    </div>
                  )
                }
              })}
            </div>
            <div className={styles.inputArea}>
              <Input placeholder="请输入内容" size="large" onKeyDown={(event)=>{
                if(event.key == 'Enter' && messageInput && messageInput != ''){
                  sendSandboxMessage();
                }
              }} value={messageInput} onChange={(e: any) => changeMessageInput(e)} />
              <Button size="large" type="primary" onClick={sendSandboxMessage}>发送</Button>
            </div>
          </div>
        </Modal>
      </div>
    </PageContainer>
  );
}

export default EditNodeConf;


