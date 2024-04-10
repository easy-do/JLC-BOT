import React, { useRef, useState } from 'react';
import { PlusOutlined, UploadOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  ModalForm,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { Button, Dropdown, Upload, message } from 'antd';
import type { UploadProps } from 'antd';
import EditScript from './editScript';
import { request } from 'umi';
import { getHighLevelDevInfo, highLevelDevPage, removeHighLevelDev, saveHighLevelDev, updateHighLevelDev } from '@/services/jlc-bot/gaojikaifa';

const sysNode: React.FC = () => {

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
    customRequest:(options)=>{
      const formData = new FormData();
      formData.append('file', options.file as Blob);
      request<API.RLong>('/api/sysNode/importNode', {
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
      const isJlc = file.name.endsWith('.jlcbsn');
      if (!isJlc) {
        message.error(`请上传.jlcbsn文件`);
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

  const handleAdd = async (fields: API.HighLevelDevelopConf) => {
    const hide = message.loading('正在添加');

    try {
      await saveHighLevelDev({ ...fields });
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
  const [currentRow, setCurrentRow] = useState<API.HighLevelDevelopConf>();

  const openEditModal = (id: string) => {
    getHighLevelDevInfo({ id: id }).then((res) => {
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

  const handleUpdate = async (fields: API.HighLevelDevelopConf) => {
    const hide = message.loading('正在更新');

    try {
      await updateHighLevelDev({ ...fields });
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
  const [editNodeScriptId, setEditNideScriptId] = useState<string>();

  const openEditFormScriptModal = (id: string) => {
    setEditNideScriptId(id);
    handleEditFormScriptModalVisible(true);
  };

  /** 国际化配置 */

  const columns: ProColumns<API.HighLevelDevelopConf>[] = [
    {
      title: '配置名称',
      renderText(_text, record) {
        return record.confName;
      },
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
                label: '编辑',
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
                key: 'export',
                label: '导出',
                onClick: (e) => {
                  getHighLevelDevInfo({ id: record.id }).then((res) => {
                    if (res.success) {
                      const node = res.data;
                      const blob = new Blob([JSON.stringify(node)]);
                      const objectURL = URL.createObjectURL(blob);
                      let btn = document.createElement('a');
                      btn.download = node?.confName + '.jlchdev';
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
                  removeHighLevelDev({ id: record.id }).then((res) => {
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
      <ProTable<API.HighLevelDevelopConf, API.RListHighLevelDevelopConf>
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
        request={highLevelDevPage}
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
          const success = await handleAdd(value as API.HighLevelDevelopConf);
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
              message: '请输入节点名称',
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
              label: 'lua',
              value: 'lua',
            }
          ]}
          rules={[
            {
              required: true,
              message: '请选择port类型',
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
        title="编辑节点"
        visible={editModalVisible}
        onVisibleChange={handleEditModalVisible}
        onFinish={async (value) => {
          const success = await handleUpdate(value as API.HighLevelDevelopConf);
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
              message: '请输入节点名称',
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
              label: 'lua',
              value: 'lua',
            }
          ]}
          rules={[
            {
              required: true,
              message: '请选择port类型',
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
      <EditScript
        visible={editFormScriptModalVisible}
        handleVisible={handleEditFormScriptModalVisible}
        editNodeScriptId={editNodeScriptId}
      />
    </PageContainer>
  );
};

export default sysNode;
