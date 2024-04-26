import React, { useRef, useState } from 'react';
import { PlusOutlined, UploadOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { Button, Dropdown, Upload, message, Modal } from 'antd';
import type { UploadProps } from 'antd';
import { request } from 'umi';
import {
  getWebhooksConfInfo,
  pageWebhooksConf,
  removeWebhooksConf,
  saveWebhooksConf,
  updateWebhooksConf,
  debugWebhooks,
  generateHookUrl
} from '@/services/jlc-bot/webhook';
import EditLiteFlowScript from '@/components/EditLiteFlowScript';
import OneNodeExecuteResultVivew from '../sandbox/oneNodeExecuteResultVivew';
import { listBot } from '@/services/jlc-bot/jiqiren';

const Webhooks: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const props: UploadProps = {
    showUploadList: false,
    maxCount: 1,
    withCredentials: true,
    onChange(info) {
      if (info.file.status !== 'uploading') {
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} 上传成功`);
        actionRef.current?.reload();
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} 上传失败.`);
      }
    },
    customRequest: (options) => {
      const formData = new FormData();
      formData.append('file', options.file as Blob);
      request<API.RLong>('/api/simpleCmdDevelop/importConf', {
        method: 'POST',
        requestType: 'form',
        data: formData,
      }).then((res) => {
        if (res.success) {
          message.success('导入成功');
          actionRef.current?.reload();
        } else {
          message.error(res.errorMessage);
        }
      });
    },
    beforeUpload: (file) => {
      const isJlc = file.name.endsWith('.jlcsdev');
      if (!isJlc) {
        message.error(`请上传.jlcsdev文件`);
      }
      return isJlc || Upload.LIST_IGNORE;
    },
  };

  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.WebhooksConf) => {
    const hide = message.loading('正在添加');

    try {
      await saveWebhooksConf({ ...fields });
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
  const [currentRow, setCurrentRow] = useState<API.WebhooksConf>();

  const openEditModal = (id: number) => {
    getWebhooksConfInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        setCurrentRow(data);
        handleEditModalVisible(true);
      }
    });
  };

  /**
   * 更新节点
   *
   * @param fields
   */

  const handleUpdate = async (fields: API.WebhooksConf) => {
    const hide = message.loading('正在更新');

    try {
      await updateWebhooksConf({ ...fields });
      hide();
      message.success('更新成功');
      return true;
    } catch (error) {
      hide();
      message.error('更新失败请重试！');
      return false;
    }
  };

  /** 编辑脚本的弹窗 */
  const [editFormScriptModalVisible, handleEditFormScriptModalVisible] = useState<boolean>(false);
  const [editScript, setEditScript] = useState<API.LiteFlowScript>();

  const openEditFormScriptModal = (id: number) => {
    getWebhooksConfInfo({ id: id }).then((res) => {
      if (res.success && res.data) {
        message.success('加载脚本成功');
        setEditScript(res.data.script);
        handleEditFormScriptModalVisible(true);
      } else {
        message.warning('加载失败,未找到脚本');
      }
    });
  };

  /** 调试配置弹窗 */
  const [debugModalVisible, handleDebugModalVisible] = useState<boolean>(false);
  const [debugResult, setDebugResult] = useState<API.CmpStepResult>();
  const [debugResultModalVisible, handleDebugResultModalVisible] = useState<boolean>(false);

  const openDebugModal = (id: number) => {
    getWebhooksConfInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        setCurrentRow(data);
        handleDebugModalVisible(true);
      }
    });
  };

  /** 生成hook地址的弹窗 */
  const [generateHookUrlModalVisible, handleGenerateHookUrlModalVisible] = useState<boolean>(false);


  const openGenerateHookModal = (id: number) => {
    getWebhooksConfInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        setCurrentRow(data);
        handleGenerateHookUrlModalVisible(true);
      }
    });
  };



  /** 国际化配置 */

  const columns: ProColumns<API.WebhooksConf>[] = [
    {
      title: '配置名称',
      dataIndex: 'confName',
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
                key: 'editScript',
                label: '编辑脚本',
                onClick: (e) => {
                  openEditFormScriptModal(record.id);
                },
              },
              {
                key: 'generateHookUrl',
                label: '生成并复制hook地址',
                onClick: (e) => {
                  openGenerateHookModal(record.id);
                },
              },
              {
                key: 'debug',
                label: '高级调试',
                onClick: (e) => {
                  openDebugModal(record.id);
                },
              },
              {
                key: 'export',
                label: '导出',
                onClick: (e) => {
                  getWebhooksConfInfo({ id: record.id }).then((res) => {
                    if (res.success) {
                      const node = res.data;
                      const blob = new Blob([JSON.stringify(node)]);
                      const objectURL = URL.createObjectURL(blob);
                      let btn = document.createElement('a');
                      btn.download = node?.confName + '.jlcsdev';
                      btn.href = objectURL;
                      btn.click();
                      URL.revokeObjectURL(objectURL);
                      message.success('导出成功');
                    } else {
                      message.warning('导出失败');
                    }
                  });
                },
              },
              {
                key: 'remove',
                label: '删除',
                onClick: (e) => {
                  removeWebhooksConf({ id: record.id }).then((res) => {
                    actionRef.current.reload();
                  });
                },
              },
            ],
            onClick: (e) => console.log(e),
          }}
        >
          操作
        </Dropdown.Button>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.WebhooksConf, API.RListWebhooksConf>
        headerTitle="配置列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageWebhooksConf}
        columns={columns}
        toolBarRender={() => [
          <Upload {...props}>
            <Button type="primary" icon={<UploadOutlined />}>
              导入配置
            </Button>
          </Upload>,
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            添加配置
          </Button>,
        ]}
      />

      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加配置"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.WebhooksConf);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
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
          name="scriptLanguage"
          label="编程语言"
          options={[
            {
              label: 'java',
              value: 'java',
            },
            {
              label: 'python',
              value: 'python',
            },
            {
              label: 'js',
              value: 'js',
            },
            {
              label: 'groovy',
              value: 'groovy',
            },
            {
              label: 'lua',
              value: 'lua',
            },
            {
              label: 'aviator',
              value: 'aviator',
            },
          ]}
          rules={[
            {
              required: true,
              message: '请选择编程语言',
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
        initialValues={currentRow}
        title="编辑信息"
        visible={editModalVisible}
        onVisibleChange={handleEditModalVisible}
        onFinish={async (value) => {
          const success = await handleUpdate(value as API.WebhooksConf);
          if (success) {
            handleEditModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText name="id" hidden />
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
          name="scriptLanguage"
          label="编程语言"
          options={[
            {
              label: 'java',
              value: 'java',
            },
            {
              label: 'groovy',
              value: 'groovy',
            },
            {
              label: 'python',
              value: 'python',
            },
            {
              label: 'lua',
              value: 'lua',
            },
            {
              label: 'aviator',
              value: 'aviator',
            },
          ]}
          rules={[
            {
              required: true,
              message: '请选择编程语言',
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
        initialValues={currentRow}
        title="调试配置"
        visible={debugModalVisible}
        onVisibleChange={handleDebugModalVisible}
        onFinish={(values) => {
          if (currentRow) {
            debugWebhooks(values).then((res) => {
              if (res.success) {
                setDebugResult(res.data);
                handleDebugResultModalVisible(true);
                message.success('调试成功');
              }
            });
          } else {
          }
        }}
      >
        <ProFormText name="id" hidden />
        <ProFormTextArea
          name="params"
          label={
            <>
              请求参数
              <a>&nbsp;&nbsp;&nbsp;</a>
              <a
                onClick={() => {
                  window.open('https://gitee.com/help/articles/4183');
                }}
              >
                gitee
              </a>
              <a>&nbsp;&nbsp;&nbsp;</a>
              <a
                onClick={() => {
                  window.open('https://docs.github.com/zh/webhooks');
                }}
              >
                github
              </a>
              <a>&nbsp;&nbsp;&nbsp;</a></>
          }
          tooltip={<span>webhook请求的body参数</span>}
          initialValue={
            '{"botNumber": "jlc-bot-sandbox","action": "started"}'
          }
          rules={[
            {
              required: true,
              message: '请填写模拟参数',
            },
          ]}
        />
      </ModalForm>
      <OneNodeExecuteResultVivew visible={debugResultModalVisible} handleVisible={handleDebugResultModalVisible} debugResult={debugResult} />
      <EditLiteFlowScript
        visible={editFormScriptModalVisible}
        handleVisible={handleEditFormScriptModalVisible}
        script={editScript}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        title="生成hook地址"
        visible={generateHookUrlModalVisible}
        onVisibleChange={handleGenerateHookUrlModalVisible}
        onFinish={(values) => {
          generateHookUrl({ 'confId': currentRow?.id, ...values }).then(res => {
            if (res.success) {
              const path = res.data;
              let url = window.location.href + "";
              const endIndex = url.indexOf("/#");
              url = url.substring(0, endIndex);
              if (url.endsWith(":8000")) {
                url = url.replace("8000", "8888");
              }
              navigator.clipboard.writeText(url + path);
              handleGenerateHookUrlModalVisible(false);
              message.success("已复制到剪切板");
            } else {
              message.warning(res.errorMessage);
            }
          })
        }}
      >
        <ProFormSelect
          name="botId"
          label="机器人"
          debounceTime={10000}
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'botNumber',
              value: 'id',
            },
          }}
          rules={[
            {
              required: true,
              message: '请选择机器人',
            },
          ]}
          request={() => listBot({}).then(res => {
            return res.data
          })}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default Webhooks;
