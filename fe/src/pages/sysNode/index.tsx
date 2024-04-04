import React, { useRef, useState } from 'react';
import { PlusOutlined, UploadOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  getSysNodeInfo,
  pageSysNode,
  removeSysNode,
  saveSysNode,
  updateSysNode,
} from '@/services/jlc-bot/xitongjiedian';
import {
  ModalForm,
  ProFormDigit,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { Button, Dropdown, Upload, message } from 'antd';
import type { UploadProps } from 'antd';
import EditNodeForm from './editNodeForm';
import EditNodeScript from './editNodeScript';
import { request } from 'umi';

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

  const handleAdd = async (fields: API.LowCodeSysNode) => {
    const hide = message.loading('正在添加');

    try {
      fields.nodeName = fields.nodeName1;
      await saveSysNode({ ...fields });
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
  const [currentRow, setCurrentRow] = useState<API.LowCodeSysNode>();

  const openEditModal = (id: string) => {
    getSysNodeInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        data.nodeName1 = data.nodeName;
        data.systemNode = data.systemNode + '';
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

  const handleUpdate = async (fields: API.LowCodeSysNode) => {
    const hide = message.loading('正在更新');

    try {
      fields.nodeName = fields.nodeName1;
      await updateSysNode({ ...fields });
      hide();
      message.success('更新成功');
      return true;
    } catch (error) {
      hide();
      message.error('更新失败请重试！');
      return false;
    }
  };

  /** 编辑表单的弹窗 */
  const [editFormDataModalVisible, handleEditFormDataModalVisible] = useState<boolean>(false);

  const openEditFormDataModal = (id: string) => {
    getSysNodeInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        setCurrentRow(data);
        handleEditFormDataModalVisible(true);
      }
    });
  };

  /** 编辑脚本的弹窗 */
  const [editFormScriptModalVisible, handleEditFormScriptModalVisible] = useState<boolean>(false);
  const [editNodeScriptId, setEditNideScriptId] = useState<string>();

  const openEditFormScriptModal = (id: string) => {
    setEditNideScriptId(id);
    handleEditFormScriptModalVisible(true);
  };

  /** 国际化配置 */

  const columns: ProColumns<API.LowCodeSysNode>[] = [
    {
      title: '节点名称',
      renderText(_text, record) {
        return record.nodeName;
      },
    },
    {
      title: '节点编号',
      dataIndex: 'nodeCode',
    },
    {
      title: '分组名称',
      dataIndex: 'groupType',
    },
    {
      title: '系统节点',
      dataIndex: 'systemNode',
      valueEnum: {
        true: '是',
        false: '否',
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
                key: 'editForm',
                label: '配置表单',
                onClick: (e) => {
                  openEditFormDataModal(record.id);
                },
              },
              {
                key: 'editScript',
                label: '编辑脚本',
                disabled: record.systemNode,
                onClick: (e) => {
                  openEditFormScriptModal(record.id);
                },
              },
              {
                key: 'export',
                label: '导出',
                onClick: (e) => {
                  getSysNodeInfo({ id: record.id }).then((res) => {
                    if (res.success) {
                      const node = res.data;
                      const blob = new Blob([JSON.stringify(node)]);
                      const objectURL = URL.createObjectURL(blob);
                      let btn = document.createElement('a');
                      btn.download = node?.nodeName + '.jlcbsn';
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
                  removeSysNode({ id: record.id }).then((res) => {
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
      <ProTable<API.LowCodeSysNode, API.RListLowCodeSysNode>
        headerTitle="节点列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageSysNode}
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
            添加节点
          </Button>,
        ]}
      />

      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加节点"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.LowCodeSysNode);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="nodeName1"
          label="节点名称"
          rules={[
            {
              required: true,
              message: '请输入节点名称',
            },
          ]}
        />
        <ProFormText
          name="groupType"
          label="分组名称"
          tooltip="系统会根据分组名称对所有节点进行分组"
          rules={[
            {
              required: true,
              message: '请输入分组名称',
            },
          ]}
        />
        <ProFormText
          name="nodeCode"
          label="节点编号"
          tooltip="当节点编号包含'if'或'If'关键字时,例如 'filedIfNode', 系统会认为这是一个条件判断节点"
          rules={[
            {
              required: true,
              message: '请输入节点编号',
            },
          ]}
        />
        <ProFormSelect
          name="systemNode"
          label="系统节点"
          tooltip="系统节点为框架自带的节点，非系统节点执行自定义脚本内容"
          initialValue={'false'}
          options={[
            {
              label: '是',
              value: 'true',
            },
            {
              label: '否',
              value: 'false',
            },
          ]}
          rules={[
            {
              required: true,
              message: '请设置是否为系统节点',
            },
          ]}
        />
        <ProFormText name="nodeColor" label="节点颜色" />
        <ProFormText name="nodeIcon" label="节点图标" tooltip="base64或url连接" />
        <ProFormDigit
          name="maxSize"
          label="最大数量"
          min={0}
          tooltip="可拖拽到画布上的最大数量,0为不限制"
          rules={[
            {
              required: true,
              message: '请设置最大数量',
            },
          ]}
        />
        <ProFormSelect
          name="nodePort"
          label="port类型"
          options={[
            {
              label: '所有',
              value: 'allPorts',
            },
            {
              label: '顶部加底部',
              value: 'topBottomPorts',
            },
            {
              label: '是否',
              value: 'ifElsePorts',
            },
            {
              label: '顶部',
              value: 'oneTopPorts',
            },
            {
              label: '底部',
              value: 'oneBottomPorts',
            },
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
          const success = await handleUpdate(value as API.LowCodeSysNode);
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
          name="nodeName1"
          label="节点名称"
          rules={[
            {
              required: true,
              message: '请输入节点名称',
            },
          ]}
        />
        <ProFormText
          name="groupType"
          label="分组名称"
          tooltip="系统会根据分组名称对所有节点进行分组"
          rules={[
            {
              required: true,
              message: '请输入分组名称',
            },
          ]}
        />
        <ProFormText
          name="nodeCode"
          label="节点编号"
          tooltip="当节点编号包含'if'或'If'关键字时,例如 'filedIfNode', 系统会认为这是一个条件判断节点"
          rules={[
            {
              required: true,
              message: '请输入节点编号',
            },
          ]}
        />
        <ProFormSelect
          name="systemNode"
          label="系统节点"
          tooltip="系统节点为系统自带节点，代码写死不可直接修改, 非系统节点可动态更新代码逻辑"
          initialValue={'false'}
          options={[
            {
              label: '是',
              value: 'true',
            },
            {
              label: '否',
              value: 'false',
            },
          ]}
          rules={[
            {
              required: true,
              message: '请设置是否为系统节点',
            },
          ]}
        />
        <ProFormText name="nodeColor" label="节点颜色" />
        <ProFormText name="nodeIcon" label="节点图标" tooltip="base64或url连接" />
        <ProFormDigit
          name="maxSize"
          label="最大数量"
          tooltip="可拖拽到画布上的最大数量,0为不限制"
          min={0}
          rules={[
            {
              required: true,
              message: '请设置最大数量',
            },
          ]}
        />
        <ProFormSelect
          name="nodePort"
          label="port类型"
          options={[
            {
              label: '所有',
              value: 'allPorts',
            },
            {
              label: '顶部加底部',
              value: 'topBottomPorts',
            },
            {
              label: '是否',
              value: 'ifElsePorts',
            },
            {
              label: '顶部',
              value: 'oneTopPorts',
            },
            {
              label: '底部',
              value: 'oneBottomPorts',
            },
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
      <EditNodeForm
        visible={editFormDataModalVisible}
        handleVisible={handleEditFormDataModalVisible}
        currentRow={currentRow}
      />
      <EditNodeScript
        visible={editFormScriptModalVisible}
        handleVisible={handleEditFormScriptModalVisible}
        editNodeScriptId={editNodeScriptId}
      />
    </PageContainer>
  );
};

export default sysNode;
