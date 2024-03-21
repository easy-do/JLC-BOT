import React, { useRef, useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useAccess } from 'umi';
import {
  getSysNodeInfo,
  pageSysNode,
  removeSysNode,
  saveSysNode,
  updateSysNode,
} from '@/services/jlc-bot/lowCodeSysNodeController';
import {
  ModalForm,
  ProFormDigit,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { Button, message } from 'antd';
import EditNodeForm from './editNodeForm';

const sysNode: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const access = useAccess();

  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaLowCodeSysNode) => {
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
  const [currentRow, setCurrentRow] = useState<API.DaLowCodeSysNode>();

  const openEditModal = (id: string) => {
    getSysNodeInfo({ id: id }).then((res) => {
      if (res.success) {
        const data = res.data;
        data.nodeName1 = data.nodeName;
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

  const handleUpdate = async (fields: API.DaLowCodeSysNode) => {
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
        data.nodeName1 = data.nodeName;
        setCurrentRow(data);
        handleEditFormDataModalVisible(true);
      }
    });
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaLowCodeSysNode>[] = [
    {
      title: '节点名称',
      dataIndex: 'nodeName',
    },
    {
      title: '分组类型',
      dataIndex: 'groupType',
    },
    {
      title: '节点编号',
      dataIndex: 'nodeCode',
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
        <a
          key="id"
          onClick={() => {
            openEditModal(record.id);
          }}
        >
          编辑
        </a>,
        <a
          key="id"
          onClick={() => {
            openEditFormDataModal(record.id);
          }}
        >
          配置表单
        </a>,
        <a
          key="id"
          onClick={() => {
            removeSysNode({ id: record.id }).then((res) => {
              actionRef.current.reload();
            });
          }}
        >
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaLowCodeSysNode, API.RListDaLowCodeSysNode>
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
          const success = await handleAdd(value as API.DaLowCodeSysNode);
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
          label="分组类型"
          rules={[
            {
              required: true,
              message: '请选择分组类型',
            },
          ]}
        />
        <ProFormText
          name="nodeCode"
          label="节点编号"
          rules={[
            {
              required: true,
              message: '请输入节点编号',
            },
          ]}
        />
        <ProFormText name="nodeColor" label="节点颜色" />
        <ProFormText name="nodeIcon" label="节点图标" />
        <ProFormDigit
          name="maxSize"
          label="最大数量"
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
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        title="编辑节点"
        visible={editModalVisible}
        onVisibleChange={handleEditModalVisible}
        onFinish={async (value) => {
          const success = await handleUpdate(value as API.DaLowCodeSysNode);
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
          label="分组类型"
          rules={[
            {
              required: true,
              message: '请选择分组类型',
            },
          ]}
        />
        <ProFormText
          name="nodeCode"
          label="节点编号"
          rules={[
            {
              required: true,
              message: '请输入节点编号',
            },
          ]}
        />
        <ProFormText name="nodeColor" label="节点颜色" />
        <ProFormText name="nodeIcon" label="节点图标" />
        <ProFormDigit
          name="maxSize"
          label="最大数量"
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
    </PageContainer>
  );
};

export default sysNode;
