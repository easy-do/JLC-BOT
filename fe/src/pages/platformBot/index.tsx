import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Modal, Drawer, Dropdown } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { ModalForm, ProFormItem, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { infoBot, addBot, pageBot, removeBot, updateBot } from '@/services/jlc-bot/jiqiren';
import EditBotConf from './EditBotConf';
import { getBotNode, listNodeConf, setBotNode } from '@/services/jlc-bot/didaima';
import { getBotSimpleCmdDevelop, listSimpleDevelop, setBotSimpleCmdDevelop } from '@/services/jlc-bot/jiandanzhilingkaifa';
import { getBotHighLevelDevelop, highLevelDevList, setBotHighLevelDevelop } from '@/services/jlc-bot/gaojikaifa';

const platfromBot: React.FC = () => {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.BotInfo>();
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.BotInfo) => {
    const hide = message.loading('正在添加');

    try {
      await addBot({ ...fields });
      hide();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };

  /** 编辑窗口的弹窗 */
  const [editModalVisible, handleEditModalVisible] = useState<boolean>(false);

  const openEditModal = (id: string) => {
    infoBot({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        data.platform1 = data.platform;
        setCurrentRow(res.data);
        handleEditModalVisible(true);
      } else {
        message.warn(res.errorMessage);
      }
    });
  };

  /** 编辑配置窗口的弹窗 */
  const [editBotConfModalVisible, handleEditBotConfModalVisible] = useState<boolean>(false);

  const openEditBotConfModal = (id: string) => {
    infoBot({ id: id }).then((res) => {
      if (res.success) {
        setCurrentRow(res.data);
        handleEditBotConfModalVisible(true);
      } else {
        message.warn(res.errorMessage);
      }
    });
  };

  /** 启用低代码窗口的弹窗 */
  const [enableBotNodeModalVisible, handleEnableBotNodeModalVisible] = useState<boolean>(false);
  const [enableBotNodeData, setEnableBotNodeData] = useState<API.SetBotConfIdDto>({
    botId: undefined,
    confIdList: [],
  });

  const openEnableBotNodeModal = (id: string) => {
    getBotNode({ id: id }).then((res) => {
      if (res.success) {
        setEnableBotNodeData({ botId: id, confIdList: res.data });
        handleEnableBotNodeModalVisible(true);
      } else {
        message.warn(res.errorMessage);
      }
    });
  };
  
    /** 启用简单指令窗口的弹窗 */
    const [enableBotSimpleModalVisible, handleEnableBotSimpleModalVisible] = useState<boolean>(false);
    const [enableBotSimpleData, setEnableBotSimpleData] = useState<API.SetBotConfIdDto>({
      botId: undefined,
      confIdList: [],
    });
  
    const openEnableBotSimpleModal = (id: string) => {
      getBotSimpleCmdDevelop({ id: id }).then((res) => {
        if (res.success) {
          setEnableBotSimpleData({ botId: id, confIdList: res.data });
          handleEnableBotSimpleModalVisible(true);
        } else {
          message.warn(res.errorMessage);
        }
      });
    };
  
      /** 启用高级开发窗口的弹窗 */
      const [enableBotHighLevelModalVisible, handleEnableBotHighLevelModalVisible] = useState<boolean>(false);
      const [enableBotHighLevelData, setEnableBotHighLevelData] = useState<API.SetBotConfIdDto>({
        botId: undefined,
        confIdList: [],
      });
    
      const openEnableBotHighLevelModal = (id: string) => {
        getBotHighLevelDevelop({ id: id }).then((res) => {
          if (res.success) {
            setEnableBotHighLevelData({ botId: id, confIdList: res.data });
            handleEnableBotHighLevelModalVisible(true);
          } else {
            message.warn(res.errorMessage);
          }
        });
      };


  /** 国际化配置 */

  const columns: ProColumns<API.BotInfo>[] = [
    {
      title: 'bot账号',
      dataIndex: 'botNumber',
    },
    {
      title: '最后心跳时间',
      dataIndex: 'lastHeartbeatTime',
    },
    {
      title: '备注',
      dataIndex: 'remark',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Dropdown.Button
        menu={{
          items: [
            {
              key: 'update',
              label: '编辑信息',
              onClick: () => {
                openEditModal(record.id);
              },
            },
            {
              key: 'editBotConf',
              label: '编辑配置',
              onClick: (e) => {
                openEditBotConfModal(record.id);
              },
            },
            {
              key: 'enableBotNode',
              label: '低代码',
              onClick: (e) => {
                openEnableBotNodeModal(record.id);(record.id);

              },
            },
            {
              key: 'enableBotSimple',
              label: '简单指令',
              onClick: (e) => {
                openEnableBotSimpleModal(record.id);(record.id);

              },
            },
            {
              key: 'enableBotHighLevel',
              label: '高级开发',
              onClick: (e) => {
                openEnableBotHighLevelModal(record.id);(record.id);

              },
            },
            {
              key: 'remove',
              label: '删除',
              onClick: (e) => {
                removeBot([record.id]).then((res) => {
                  actionRef.current.reload();
                });
              },
            },
          ],
          onClick: (e) => console.log(e),
        }}
      >
        操作
      </Dropdown.Button>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.BotInfo, API.RListBotInfo>
        headerTitle="机器人列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            添加bot
          </Button>
        ]}
        request={pageBot}
        columns={columns}
      />
      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.BotInfo>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.BotInfo>[]}
          />
        )}
      </Drawer>
      <ModalForm
        title="添加机器人"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onFinish={async (value) => {
          if (value.platform == 'other' && value.platform1) {
            value.platform = value.platform1;
          }
          const success = await handleAdd(value as API.BotInfo);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="botNumber"
          label="bot账号"
          rules={[
            {
              required: true,
              message: '请输入bot账号',
            },
          ]}
        />
        <ProFormSelect
          name="platform"
          label="平台"
          showSearch

          fieldProps={{

          }}
          rules={[
            {
              required: true,
              message: '请选择平台',
            },
          ]}
          options={[
            {
              label: 'QQ',
              value: 'qq',
            },
            {
              label: 'WeChat',
              value: 'wx',
            },
            {
              label: '其他',
              value: 'other',
            }
          ]}
        />
        <ProFormItem
          noStyle
          shouldUpdate
        >
          {({ getFieldValue }) => getFieldValue('platform') === 'other' && <ProFormText name={'platform1'} rules={[
            {
              required: true,
              message: '请输入平台编码',
            },
          ]} label={"自定义平台"} />}
        </ProFormItem>
        <ProFormText
          name="botUrl"
          label="通信地址"
        />
        <ProFormSelect
          name="postType"
          label="上报方式"
          rules={[
            {
              required: true,
              message: '请选择上报方式',
            },
          ]}
          options={[
            {
              label: '正向websocket',
              value: 'websocket',
            },
            {
              label: '反向websocket',
              value: 'websocket_reverse',
            },
            {
              label: 'HTTP_POST',
              value: 'http_post',
            },
            {
              label: 'wcf-http上报',
              value: 'wcf_http',
            },
            {
              label: 'wcf-客户端通信',
              value: 'wcf_client',
            },
          ]}
        />
        <ProFormSelect
          name="invokeType"
          label="调用方式"
          rules={[
            {
              required: true,
              message: '请选择调用方式',
            },
          ]}
          options={[
            {
              label: 'websocket',
              value: 'websocket',
            },
            {
              label: 'http',
              value: 'http',
            },
            {
              label: 'wcf客户端通信',
              value: 'wcf_client',
            },
          ]}
        />
        <ProFormTextArea
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="编辑机器人"
        visible={editModalVisible}
        initialValues={currentRow}
        onVisibleChange={handleEditModalVisible}
        onFinish={(value) => {
          if (value.platform == 'other' && value.platform1) {
            value.platform = value.platform1;
          }
          updateBot(value).then((res) => {
            if (res.success) {
              message.success('修改成功');
              handleEditModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="id" />
        <ProFormText
          name="botNumber"
          label="bot账号"
          rules={[
            {
              required: true,
              message: '请输入bot账号',
            },
          ]}
        />
        <ProFormSelect
          name="platform"
          label="平台"
          showSearch

          fieldProps={{

          }}
          rules={[
            {
              required: true,
              message: '请选择平台',
            },
          ]}
          options={[
            {
              label: 'QQ',
              value: 'qq',
            },
            {
              label: 'WeChat',
              value: 'wx',
            },
            {
              label: '其他',
              value: 'other',
            }
          ]}
        />
        <ProFormItem
          noStyle
          shouldUpdate
        >
          {({ getFieldValue }) => getFieldValue('platform') === 'other' && <ProFormText name={'platform1'} rules={[
            {
              required: true,
              message: '请输入平台编码',
            },
          ]} label={"自定义平台"} />}
        </ProFormItem>
        <ProFormText
          name="botUrl"
          label="通信地址"
        />
        <ProFormSelect
          name="postType"
          label="上报方式"
          rules={[
            {
              required: true,
              message: '请选择上报方式',
            },
          ]}
          options={[
            {
              label: '正向websocket',
              value: 'websocket',
            },
            {
              label: '反向websocket',
              value: 'websocket_reverse',
            },
            {
              label: 'HTTP_POST',
              value: 'http_post',
            },
            {
              label: 'wcf-http上报',
              value: 'wcf_http',
            },
            {
              label: 'wcf-客户端通信',
              value: 'wcf_client',
            },
          ]}
        />
        <ProFormSelect
          name="invokeType"
          label="调用方式"
          rules={[
            {
              required: true,
              message: '请选择调用方式',
            },
          ]}
          options={[
            {
              label: 'websocket',
              value: 'websocket',
            },
            {
              label: 'http',
              value: 'http',
            },
            {
              label: 'wcf-客户端通信',
              value: 'wcf_client',
            },
          ]}
        />
        <ProFormText
          name="botSecret"
          label="密钥"
          rules={[
            {
              required: true,
              message: '请输入密钥',
            },
          ]}
        />
        <ProFormTextArea
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注',
            },
          ]}
        />
      </ModalForm>
      <Modal
        title="机器人配置信息"
        style={{ minHeight: '50%', minWidth: '80%' }}
        open={editBotConfModalVisible}
        destroyOnClose
        onOk={() => handleEditBotConfModalVisible(false)}
        onCancel={() => handleEditBotConfModalVisible(false)}
        okText={'确定'}
        cancelText={'关闭'}
      >
        <EditBotConf botNumber={currentRow?.botNumber} visible={editBotConfModalVisible} />
      </Modal>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="启用低代码"
        visible={enableBotNodeModalVisible}
        initialValues={enableBotNodeData}
        onVisibleChange={handleEnableBotNodeModalVisible}
        onFinish={(value) => {
          console.log(value);
          setBotNode(value).then((res) => {
            if (res.success) {
              message.success('更新成功');
              handleEnableBotNodeModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="botId" />
        <ProFormSelect
          name="confIdList"
          label="低代码流程"
          mode="multiple"
          debounceTime={10000}
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'confName',
              value: 'id',
            },
          }}
          request={() => listNodeConf({}).then(res => {
            return res.data
          })}
        />
      </ModalForm>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="启用简单指令"
        visible={enableBotSimpleModalVisible}
        initialValues={enableBotSimpleData}
        onVisibleChange={handleEnableBotSimpleModalVisible}
        onFinish={(value) => {
          console.log(value);
          setBotSimpleCmdDevelop(value).then((res) => {
            if (res.success) {
              message.success('更新成功');
              handleEnableBotSimpleModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="botId" />
        <ProFormSelect
          name="confIdList"
          label="简单指令配置"
          mode="multiple"
          debounceTime={10000}
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'confName',
              value: 'id',
            },
          }}
          request={() => listSimpleDevelop({}).then(res => {
            return res.data
          })}
        />
      </ModalForm>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="启用高级开发"
        visible={enableBotHighLevelModalVisible}
        initialValues={enableBotHighLevelData}
        onVisibleChange={handleEnableBotHighLevelModalVisible}
        onFinish={(value) => {
          console.log(value);
          setBotHighLevelDevelop(value).then((res) => {
            if (res.success) {
              message.success('更新成功');
              handleEnableBotHighLevelModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="botId" />
        <ProFormSelect
          name="confIdList"
          label="高级开发配置"
          mode="multiple"
          debounceTime={10000}
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'confName',
              value: 'id',
            },
          }}
          request={() => highLevelDevList({}).then(res => {
            return res.data
          })}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default platfromBot;
