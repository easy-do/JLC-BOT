import ProDescriptions from '@ant-design/pro-descriptions';
import ProTable from '@ant-design/pro-table';
import { Button, Modal } from 'antd';
import { useState } from 'react';

function OneNodeExecuteResultVivew(props: { visible: boolean; handleVisible: (data: boolean) => void; debugResult: API.CmpStepResult; }) {


  //上下文实例弹窗
  const [contextBeanModalVisible, handleContextBeanModalVisible] = useState<boolean>(false);

  //函数明细弹窗
  const [contextBeanFunctionModalVisible, handleContextBeanFunctionModalVisible] = useState<boolean>(false);

  const [currentContextBean, setCurrentContextBean] = useState<API.CmpContextBean>();


  const openContextBeanFunctionModal = (data: API.CmpContextBean) => {
    setCurrentContextBean(data);
    handleContextBeanFunctionModalVisible(true);
  }


  //日志信息弹窗
  const [logVivewModalVisible, handleLogVivewModalVisible] = useState<boolean>(false);



  return (
    <><Modal
      title="节点执行结果"
      open={props.visible}
      destroyOnClose
      onOk={() => {
        props.handleVisible(false);
      }}
      onCancel={() => {
        props.handleVisible(false);
      }}
      width={'50%'}
    >
      <ProDescriptions column={2} title="调试结果" tooltip="配置执行详情">
        <ProDescriptions.Item
          label="状态"
          valueEnum={{
            false: {
              text: '失败',
              status: 'Error',
            },
            true: {
              text: '成功',
              status: 'Success',
            },
          }}
        >
          {props.debugResult?.success}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="总耗时(毫秒)" valueType="text">
          {props.debugResult?.timeSpent}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="开始时间" valueType="text">
          {props.debugResult?.startTime}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="结束时间" valueType="text">
          {props.debugResult?.endTime}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="参数" valueType="text" span={4}>
          {props.debugResult?.param && JSON.stringify(props.debugResult?.param)}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="其他信息" valueType="text">
          {props.debugResult?.message}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="日志" valueType="text">
          <Button type={'primary'} onClick={() => { handleLogVivewModalVisible(true) }}>查看</Button>
        </ProDescriptions.Item>
        <ProDescriptions.Item label="上下文实例" valueType="text">
          <Button type={'primary'} onClick={() => { handleContextBeanModalVisible(true) }}>查看</Button>
        </ProDescriptions.Item>
      </ProDescriptions>

    </Modal>
      <Modal
        title="上下文实例"
        open={contextBeanModalVisible}
        destroyOnClose
        onOk={() => {
          handleContextBeanModalVisible(false);
        }}
        onCancel={() => {
          handleContextBeanModalVisible(false);
        }}
        width={'90%'}
      >
        <ProTable<API.CmpContextBean, API.CmpContextBean>
          toolBarRender={false}
          // actionRef={actionRef}
          rowKey="name"
          search={false}
          pagination={false}
          dataSource={props.debugResult?.contextBeanList}
          columns={[
            {
              title: '实例名称',
              dataIndex: 'name',
            },
            {
              title: '描述',
              ellipsis: true,
              dataIndex: 'desc',
            },
            {
              title: '可用函数',
              ellipsis: true,
              dataIndex: 'methods',
              render: (text, record) => {
                return <Button type='primary' onClick={() => openContextBeanFunctionModal(record)}>查看</Button>
              }
            },
          ]}
        />
      </Modal>
      <Modal
        title="函数明细"
        open={contextBeanFunctionModalVisible}
        destroyOnClose
        onOk={() => {
          handleContextBeanFunctionModalVisible(false);
        }}
        onCancel={() => {
          handleContextBeanFunctionModalVisible(false);
        }}
        width={'90%'}
      >
        <ProTable<API.CmpContextBeanMethod, API.CmpContextBeanMethod>
          toolBarRender={false}
          // actionRef={actionRef}
          rowKey="name"
          search={false}
          pagination={false}
          dataSource={currentContextBean?.methods}
          columns={[
            {
              title: '函数名称',
              dataIndex: 'name',
            },
            {
              title: '返回类型',
              dataIndex: 'returnType',
            },
            {
              title: '描述',
              ellipsis: true,
              dataIndex: 'desc'
            },
            {
              title: '示例',
              ellipsis: true,
              dataIndex: 'demo',
              render: (text, record) => {
                return currentContextBean?.name + '.' + record.demo;
              }
            },
          ]}
        />
      </Modal>
      <Modal
        title="日志信息"
        open={logVivewModalVisible}
        destroyOnClose
        onOk={() => {
          handleLogVivewModalVisible(false);
        }}
        onCancel={() => {
          handleLogVivewModalVisible(false);
        }}
        width={'90%'}
      >
        <ProTable<API.CmpLog, API.CmpLog>
          toolBarRender={false}
          // actionRef={actionRef}
          rowKey="name"
          search={false}
          pagination={false}
          dataSource={props.debugResult?.logs}
          columns={[
            {
              title: '日志级别',
              dataIndex: 'type',
            },
            {
              title: '内容',
              ellipsis: true,
              dataIndex: 'context',
            },
          ]}
        />
      </Modal>
    </>
  );
}

export default OneNodeExecuteResultVivew;
