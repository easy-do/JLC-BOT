import React, { useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { cleanPostLog, pagePostLog } from '@/services/jlc-bot/botPostLogController';
import { Button, message } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';

const BotPostLog: React.FC = () => {
  
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */

  const columns: ProColumns<API.BotPostLog>[] = [
    {
      title: '平台',
      dataIndex: 'platform',
    },
    {
      title: '上报时间',
      dataIndex: 'postTime',
      search: false
    },
    {
      title: '消息参数',
      ellipsis:true,
      dataIndex: 'message',
      search: false
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Button type='primary' onClick={(e)=>{
          navigator.clipboard.writeText(record.message);
          message.success("复制成功");
        }} >复制参数</Button>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.BotPostLog, API.RListBotPostLog>
        headerTitle="上报日志"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pagePostLog}
        columns={columns}
        toolBarRender={() => [
          <Button
            type="primary"
            icon={<DeleteOutlined />}
            onClick={() => {
              cleanPostLog().then(res=>{
                if(res.success){
                  message.success(res.message);
                  actionRef.current?.reload();
                }else{
                  message.warning(res.errorMessage);
                }
              })
            }}
          >
            清空日志
          </Button>,
        ]}
      />
    </PageContainer>
  );
};

export default BotPostLog;
