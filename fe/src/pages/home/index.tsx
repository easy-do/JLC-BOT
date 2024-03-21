import { FC, useEffect, useState } from 'react';
import { Skeleton, Tag, Modal } from 'antd';

import { request, useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import styles from './style.less';

import ProList from '@ant-design/pro-list';

import React from 'react';

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
          {currentUser.accountname}
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
        } catch (error) {}
      }
    });
  }, []);

  return (
    <PageContainer
      content={<PageHeaderContent currentUser={initialState?.currentUser} />}
    >
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
          itemCardProps={{ onClick: () => {} }}
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
