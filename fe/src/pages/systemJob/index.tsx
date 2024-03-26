import React, { useRef, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { addJob, pageJob, runOneJob, startJob, stopJob, removeJob, jobInfo, updateJob } from '@/services/jlc-bot/jobController';
import { Button, Dropdown, FormInstance, message } from 'antd';
import { CaretUpFilled, PlusOutlined, SettingTwoTone } from '@ant-design/icons';
import ProForm, { ModalForm, ProFormItem, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import FormItem from 'antd/es/form/FormItem';
import { CronProps, CronFns } from "qnn-react-cron";
import QnnReactCron from "qnn-react-cron";

const SystemJob: React.FC = () => {

  const actionRef = useRef<ActionType>();

  const addFormRef = useRef<FormInstance>();
  const editFormRef = useRef<FormInstance>();
  let cronRef = useRef<CronFns>();
  const [currentRow, setCurrentRow] = useState<API.QuartzJob>();
  const [cornHidden, setCornHidden] = useState<boolean>(true);

  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [cronVaue, setCronVaue] = useState<string>("* * * * * ? *");
  /** 编辑窗口的弹窗 */
  const [editModalVisible, handleEditModalVisible] = useState<boolean>(false);

  const openEditJobModal = (id) => {
    jobInfo({ id: id }).then(res => {
      if (res.success) {
        const row = res.data;
        row.status = row?.status + '';
        setCurrentRow(row);
        handleEditModalVisible(true);
      }
    });
  }

  /** 国际化配置 */

  const columns: ProColumns<API.QuartzJob>[] = [
    {
      title: '任务编号',
      dataIndex: 'id'
    },
    {
      title: '任务名称',
      dataIndex: 'jobName',
    },
    {
      title: 'cron表达式',
      dataIndex: 'cronExpression',
    },
    {
      title: '任务执行类',
      dataIndex: 'jobClass',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        false: { text: '停止', status: 'Error' },
        true: { text: '启动', status: 'Success' },
      }
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
                onClick: (e) => {
                  openEditJobModal(record.id);
                }
              },
              {
                key: 'triggerJob',
                label: '运行一次',
                onClick: (e) => {
                  runOneJob({ id: record.id }).then(res => {
                    if (res.success && res.data) {
                      handleModalVisible(false);
                      message.success('操作成功');
                      if (actionRef.current) {
                        actionRef.current.reload();
                      }
                    } else {
                      message.success('操作失败');
                    }
                  })
                }
              },
              {
                key: 'start',
                label: '启动',
                disabled: record.status,
                onClick: (e) => {
                  startJob({ id: record.id }).then(res => {
                    if (res.success && res.data) {
                      handleModalVisible(false);
                      message.success('操作成功');
                      if (actionRef.current) {
                        actionRef.current.reload();
                      }
                    } else {
                      message.success('操作失败');
                    }
                  })
                }
              },
              {
                key: 'stop',
                label: '停止',
                disabled: !record.status,
                onClick: (e) => {
                  stopJob({ id: record.id }).then(res => {
                    if (res.success && res.data) {
                      handleModalVisible(false);
                      message.success('操作成功');
                      if (actionRef.current) {
                        actionRef.current.reload();
                      }
                    } else {
                      message.success('操作失败');
                    }
                  })
                }
              },
              {
                key: 'remove',
                label: '删除',
                onClick: (e) => {
                  removeJob({ id: record.id }).then(res => {
                    if (res.success && res.data) {
                      handleModalVisible(false);
                      message.success('操作成功');
                      if (actionRef.current) {
                        actionRef.current.reload();
                      }
                    } else {
                      message.success('操作失败');
                    }
                  })
                }
              },

            ], onClick: (e) => console.log(e)
          }}>
          操作
        </Dropdown.Button>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.QuartzJob, API.RListQuartzJob>
        headerTitle="系统任务"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageJob}
        columns={columns}
        toolBarRender={() => [
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            添加任务
          </Button>
        ]}
      />
      <ModalForm
        title="添加任务"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        formRef={addFormRef}
        modalProps={{
          destroyOnClose: true,
        }}
        onFinish={async (value) => {
          addJob(value).then(res => {
            if (res.success && res.data) {
              handleModalVisible(false);
              message.success('添加成功');
              if (actionRef.current) {
                actionRef.current.reload();
              }
            } else {
              message.success('添加失败');
            }
          })

        }}
      >
        <ProFormText
          name="jobName"
          label="任务名"
          rules={[
            {
              required: true,
              message: '请输入任务名',
            },
          ]}
        />
        <ProFormText
          name="cronExpression"
          label="cron表达式"
          initialValue={"* * * * * ? *"}
          fieldProps={
            {onFocus:()=>setCornHidden(false)}
          }
          rules={[
            {
              required: true,
              message: '请输入cron表达式',
            },
          ]}
        />
         <ProFormItem
          hidden={cornHidden}
        >
          <QnnReactCron
          value={cronVaue}
          getCronFns={(fns: any) => cronRef = fns}
          footer={[
            <Button type='primary' style={{ marginRight: 10 }} 
            onClick={
              ()=>{
                addFormRef.current?.setFieldValue('cronExpression',"* * * * * ? *");
                setCronVaue('');
            }}>
            重置
           </Button>,
            <Button type='primary' style={{ marginRight: 10 }} onClick={()=>{
              addFormRef.current?.setFieldValue('cronExpression',cronRef.getValue());
            }}>
            解析到UI
           </Button>,
            <Button icon={<CaretUpFilled />} type='primary' style={{ marginRight: 10 }} onClick={()=>{
              setCornHidden(true);
            }}>
            收起
           </Button>
          ]}
        />
        </ProFormItem>
        <ProFormText
          name="jobClass"
          label="执行任务类"
          rules={[
            {
              required: true,
              message: '请输入执行任务类',
            },
          ]}
        />
        <ProFormSelect
          name="status"
          label="状态"
          initialValue={'false'}
          options={[
            {
              label: '启用',
              value: 'true',
            },
            {
              label: '停止',
              value: 'false',
            },
          ]}
        />
        <ProFormTextArea
          name="jobParam"
          label="任务参数"
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
        title="编辑任务"
        visible={editModalVisible}
        formRef={editFormRef}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={(value) => {
          handleEditModalVisible(value);
        }}
        onFinish={(values) => {
          updateJob(values).then(res => {
            if (res.success && res.data) {
              message.success('操作成功');
              handleEditModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            } else {
              message.success('操作失败');
            }
          })
        }}
      >
        <ProFormText hidden name="id" />
        <ProFormText
          name="jobName"
          label="任务名"
          rules={[
            {
              required: true,
              message: '请输入任务名',
            },
          ]}
        />
        <ProFormText
          name="cronExpression"
          label="cron表达式"
          initialValue={"* * * * * ? *"}
          fieldProps={
            {onFocus:()=>setCornHidden(false)}
          }
          rules={[
            {
              required: true,
              message: '请输入cron表达式',
            },
          ]}
        />
         <ProFormItem
          hidden={cornHidden}
        >
          <QnnReactCron
          value={currentRow?.cronExpression}
          getCronFns={(fns: any) => cronRef = fns}
          footer={[
            <Button type='primary' style={{ marginRight: 10 }} 
            onClick={
              ()=>{
                editFormRef.current?.setFieldValue('cronExpression',"* * * * * ? *");
                setCronVaue('');
            }}>
            重置
           </Button>,
            <Button type='primary' style={{ marginRight: 10 }} onClick={()=>{
              editFormRef.current?.setFieldValue('cronExpression',cronRef.getValue());
            }}>
            解析到UI
           </Button>,
            <Button icon={<CaretUpFilled />} type='primary' style={{ marginRight: 10 }} onClick={()=>{
              setCornHidden(true);
            }}>
            收起
           </Button>
          ]}
        />
        </ProFormItem>
        <ProFormText
          name="jobClass"
          label="执行任务类"
          rules={[
            {
              required: true,
              message: '请输入执行任务类',
            },
          ]}
        />
        <ProFormSelect
          name="status"
          label="状态"
          options={[
            {
              label: '启用',
              value: 'true',
            },
            {
              label: '停止',
              value: 'false',
            },
          ]}
        />
        <ProFormTextArea
          name="jobParam"
          label="任务参数"
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
    </PageContainer>
  );
};

export default SystemJob;
