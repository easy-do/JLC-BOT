import React, { useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { cleanNodeConfExecuteLog, pageNodeConfExecuteLog} from '@/services/jlc-bot/jiedianpeizhizhixingrizhi';
import { Button, message } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';

const NodeConfExecuteLog: React.FC = () => {
  
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */

  const columns: ProColumns<API.BotPostLog>[] = [
    {
      title: '配置名称',
      dataIndex: 'confName',
    },
    {
      title: '发生时间',
      ellipsis:true,
      dataIndex: 'createTime',
      search: false
    },
    {
      title: '耗时(ms)',
      ellipsis:true,
      dataIndex: 'executeTime',
      search: false
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.BotPostLog, API.RListBotPostLog>
        headerTitle="节点配置执行日志"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageNodeConfExecuteLog}
        columns={columns}
        toolBarRender={() => [
          <Button
            type="primary"
            icon={<DeleteOutlined />}
            onClick={() => {
              cleanNodeConfExecuteLog().then(res=>{
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

export default NodeConfExecuteLog;
