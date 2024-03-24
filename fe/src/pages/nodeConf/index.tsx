import React, { useRef } from 'react';
import { PlusOutlined, UploadOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { Button, message, Upload, UploadProps } from 'antd';
import { getNodeConf, pageNodeConf, removeNodeConf,copyNodeConf } from '@/services/jlc-bot/lowCodeController';

const nodeConf: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const props: UploadProps = {
    name: 'file',
    action: '/api/lowcode/importNodeConf',
    maxCount: 1,
    headers: {
      "Authorization": localStorage.getItem('Authorization')
    },
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
    beforeUpload: (file) => {
      const isJlc = file.name.endsWith('.jlc');
      if (!isJlc) {
        message.error(`只支持.jlc文件`);
      }
      return isJlc || Upload.LIST_IGNORE;
    },
  };

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
          copyNodeConf({ id: record.id }).then(res => {
            if (res.success) {
              message.success('复制成功');
              setTimeout(() => {
                window.open("/#/editNodeConf?confId=" + res.data);
              }, 2000);
            } else {
              message.warning('复制失败')
            }
            actionRef.current.reload();
          })
          
        }}
      >
        复制
      </a>,
        <a
          key="id"
          onClick={() => {
            getNodeConf({ id: record.id }).then(res => {
              if (res.success) {
                const nodeConf = res.data;
                const blob = new Blob([JSON.stringify(nodeConf)]);
                const objectURL = URL.createObjectURL(blob);
                let btn = document.createElement('a');
                btn.download = nodeConf?.confName + '.jlc';
                btn.href = objectURL;
                btn.click();
                URL.revokeObjectURL(objectURL);
                message.success('导出成功')
              } else {
                message.warning('导出失败')
              }
            })
          }}
        >
          导出
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
          <Upload {...props}>
            <Button type="primary" icon={<UploadOutlined />}>
              导入配置
            </Button>
          </Upload>,
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
