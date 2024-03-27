import ProTable, { ActionType, ProColumns } from "@ant-design/pro-table";
import { Modal } from "antd";
import { useEffect, useRef, useState } from "react";

function NodeExecutetResultVivew(props: any) {

  const actionRef = useRef<ActionType>();


  const columns: ProColumns<API.CmpStepResult>[] = [
    {
      title: '节点ID',
      dataIndex: 'nodeId',
    },
    {
      title: '节点名称',
      dataIndex: 'nodeName',
    },
    {
      title: '状态',
      dataIndex: 'success',
      valueEnum: {
        false: { text: '失败', status: 'Error' },
        true: { text: '成功', status: 'Success' }
      }
    },
    {
      title: '耗时(ms)',
      dataIndex: 'timeSpent',
    },
    {
      title: '开始时间',
      dataIndex: 'startTime',
    },
    {
      title: '结束时间',
      dataIndex: 'endTime',
    },
    {
      title: '上下文变化',
      dataIndex: 'param',
      ellipsis: true,
      renderText(text, record, index, action) {
          return record.param ? JSON.stringify(record.param) : '';
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
        <ProTable<API.CmpStepResult, API.CmpStepResult>
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