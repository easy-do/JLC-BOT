import { FC, useEffect, useState } from 'react';
import { Skeleton, Tag, Modal, Card } from 'antd';

import { request, useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import styles from './style.less';

import ProList from '@ant-design/pro-list';

import React from 'react';

import { Bar } from '@ant-design/charts';
import { nodeExecutePa } from '@/services/jlc-bot/xingnengfenxi';

const PageHeaderContent: FC<{ currentUser: API.CurrentUser }> = ({ currentUser }) => {
  const loading = currentUser && Object.keys(currentUser).length;
  if (!loading) {
    return <Skeleton avatar paragraph={{ rows: 1 }} active />;
  }
  return (
    <div className={styles.pageHeaderContent}>
      {/* <div className={styles.avatar}>
        <Avatar size="large" src={''} />
      </div> */}
      <div className={styles.content}>
        <div className={styles.contentTitle}>
          你好，
          {currentUser.userName}
          ，祝你开心每一天！
        </div>
        {/* <div>
          {currentUser.title} |{currentUser.group}
        </div> */}
      </div>
    </div>
  );
};

const Home: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  const [adVisible, handleAdVisible] = useState<boolean>(true);

  function getAdList(options?: { [key: string]: any }) {
    return request<any>('https://magic.easydo.plus/api/adlist', {
      method: 'GET',
      ...(options || {}),
    });
  }

  const [dataSource, setDatasource] = useState([]);

  const [nodeExecuteTopData, setNodeExecuteTopData] = useState();
  const [nodeExecuteMaxData, setNodeExecuteMaxData] = useState();
  const [nodeExecutePaData, setNodeExecutePaData] = useState();
  const [nodeExecuteConfPaData, setNodeExecuteConfPaData] = useState();
  const [nodeExecuteConfTopData, setNodeExecuteConfTopData] = useState();
  const [nodeExecuteConfMaxData, setNodeExecuteConfMaxData] = useState();

  useEffect(() => {
    getAdList().then((res) => {
      if (res.success) {
        try {
          const data = [];
          for (let index = 0; index < res.data.length; index++) {
            const ad = res.data[index];
            const tags = [];
            if (ad.tag) {
              const tagStrs = ad.tag.split(',');
              for (let index1 = 0; index1 < tagStrs.length; index1++) {
                const element = tagStrs[index1];
                tags.push(<Tag color={'blue'}>{element}</Tag>);
              }
            }
            data.push({
              title: (
                <span
                  style={{ fontWeight: 'bold' }}
                  onClick={() => {
                    window.open(ad.url);
                  }}
                >
                  {ad.title}
                </span>
              ),
              description: <>{tags}</>,
              content: <span style={{ color: 'red' }}>{ad.content}</span>,
              // extra: <img width={160} alt="logo" src={ad.logo} />,
            });
          }
          setDatasource(data);
        } catch (error) { }
      }
    });

    nodeExecutePa().then((res) => {
      if (res.success) {
        setNodeExecutePaData({
          title: {
            visible: true,
            text: '节点平均执行效率',
          },
          data: res.data.node,
          forceFit: true,
          xField: 'executeTime',
          yField: 'nodeName',
          description: {
            visible: true,
            text: '节点执行完毕的平均耗时,单位毫秒',
          },
        });
        setNodeExecuteConfPaData({
          title: {
            visible: true,
            text: '节点配置平均执行效率',
          },
          data: res.data.nodeConf,
          forceFit: true,
          xField: 'executeTime',
          yField: 'confName',
          description: {
            visible: true,
            text: '节点配置执行完毕的平均耗时,单位毫秒',
          },
        });
        setNodeExecuteTopData({
          title: {
            visible: true,
            text: '节点执行次数排行',
          },
          data: res.data.nodeTop,
          forceFit: true,
          xField: 'count',
          yField: 'nodeName',
          description: {
            visible: true,
            text: '节点执行次数',
          },
        });
        setNodeExecuteConfTopData({
          title: {
            visible: true,
            text: '节点配置执行次数排行',
          },
          data: res.data.nodeConfTop,
          forceFit: true,
          xField: 'count',
          yField: 'confName',
          description: {
            visible: true,
            text: '节点配置执行次数',
          },
        });
        setNodeExecuteMaxData({
          title: {
            visible: true,
            text: '节点最大耗时排行',
          },
          data: res.data.nodeMax,
          forceFit: true,
          xField: 'count',
          yField: 'nodeName',
          description: {
            visible: true,
            text: '每个节点执行耗时最长的记录',
          },
        });
        setNodeExecuteConfMaxData({
          title: {
            visible: true,
            text: '节点配置最大耗时排行',
          },
          data: res.data.nodeConfMax,
          forceFit: true,
          xField: 'count',
          yField: 'confName',
          description: {
            visible: true,
            text: '每个节点配置执行耗时最长的记录',
          },
        });
      }
    });
  }, []);

  return (
    <PageContainer content={<PageHeaderContent currentUser={initialState?.currentUser} />}>
      <Card>
        <Bar {...nodeExecuteMaxData} />
      </Card>
      <Card>
        <Bar {...nodeExecuteConfMaxData} />
      </Card>
      <Card>
        <Bar {...nodeExecuteConfTopData} />
      </Card>
      <Card>
        <Bar {...nodeExecuteTopData} />
      </Card>
      <Card>
        <Bar {...nodeExecuteConfPaData} />
      </Card>
      <Card>
        <Bar {...nodeExecutePaData} />
      </Card>
      <Modal
        open={adVisible}
        onCancel={() => handleAdVisible(false)}
        okText={undefined}
        destroyOnClose
        // title={'广告'}
        footer={null}
        style={{ minHeight: '50%', minWidth: '30%' }}
      >
        <ProList<{ title: any }>
          // toolBarRender={() => {
          //   return [
          //     <Button key="1" type="link">
          //       加入
          //     </Button>,
          //   ];
          // }}
          itemLayout="vertical"
          rowKey="id"
          headerTitle=""
          dataSource={dataSource}
          itemCardProps={{ onClick: () => { } }}
          metas={{
            title: {},
            description: {},
            extra: {},
            content: {},
          }}
        />
      </Modal>
    </PageContainer>
  );
};

export default Home;
