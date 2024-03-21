import React, { useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useAccess } from 'umi';
import { Button, message } from 'antd';
import { pageNodeConf, removeNodeConf } from '@/services/jlc-bot/lowCodeController';

const nodeConf: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const access = useAccess();


  /** 国际化配置 */

  const columns: ProColumns<API.DaLowCodeSysNode>[] = [
    {
      title: '配置名称',
      dataIndex: 'confName',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="id"
          onClick={() => {
            window.open("/#/editNodeConf?confId=" + record.id);
          }}
        >
          编辑
        </a>,
        <a
          key="id"
          onClick={() => {
            removeNodeConf({ id: record.id }).then((res) => {
              if (res.success) {
                message.success(res.message);
              }
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
        headerTitle="节点配置"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageNodeConf}
        columns={columns}
        toolBarRender={() => [
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              window.open("/#/editNodeConf");
            }}
          >
            添加配置
          </Button>,
        ]}
      />
    </PageContainer>
  );
};

export default nodeConf;
