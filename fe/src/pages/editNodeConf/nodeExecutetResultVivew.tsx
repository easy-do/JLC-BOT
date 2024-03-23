import ProTable, { ActionType, ProColumns } from "@ant-design/pro-table";
import { Modal } from "antd";
import { useEffect, useRef, useState } from "react";

function NodeExecutetResultVivew(props: any) {

  const actionRef = useRef<ActionType>();

  const [resultList, setResultList] = useState<API.NodeExecuteResult[]>([]);

  // useEffect(() => {
  //   setResultList(props.resultList);
  // }, [props.resultList]);

  const columns: ProColumns<API.NodeExecuteResult>[] = [
    {
      title: '节点编号',
      dataIndex: 'nodeCode',
    },
    {
      title: '节点名称',
      dataIndex: 'nodeName',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        0: { text: '失败', status: 'Error' },
        1: { text: '成功', status: 'Success' },
        2: { text: '异常', status: 'Error' }
      }
    },
    {
      title: '全局参数',
      dataIndex: 'paramsJson',
      ellipsis: true,
      renderText(text, record, index, action) {
          return record.paramsJson ? JSON.stringify(record.paramsJson) : '';
      },
    },
    {
      title: '详细信息',
      dataIndex: 'message',
      ellipsis: true
    },
  ];

    return(
        <Modal
          title="节点执行结果"
          open={props.visible}
          destroyOnClose
          onOk={() => {
            props.handleVisible(false);
          }}
          onCancel={() => {
            props.handleVisible(false);
          }}
          width={"50%"}
        >
        <ProTable<API.NodeExecuteResult, API.NodeExecuteResult>
        toolBarRender={false}
        actionRef={actionRef}
        rowKey="nodeId"
        search={false}
        pagination={false}
        dataSource={props.resultList}
        columns={columns}
      />
        </Modal>
    );
}

export default NodeExecutetResultVivew;