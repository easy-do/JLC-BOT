import { useIntl } from 'umi';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';
import { version } from '@/services/jlc-bot/systemController';
import { useMemo, useState } from 'react';
import { mode } from '@/services/jlc-bot/systemController';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: '隔壁老于',
  });

  const currentYear = new Date().getFullYear();

  const [currentVersion,setCurrentVersion] = useState<string>();

  const [currentMode,setCurrentMode] = useState<string>();


  useMemo(() => {
    version().then(res=>{
      setCurrentVersion(res);
    });
    mode().then(res=>{
      setCurrentMode(res);
    });

}, [])


  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: '交流群',
          title: '交流群',
          href: 'http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=jKliJIxAFvZoZxBSw1NnlMjOj8pRR42f&authKey=vnozKSs2ou1MO68VXH1ct2AReURSyIj4jlVe%2BVAlA5h%2F0M1BsdhQP0YN6MqwRwBB&noverify=0&group_code=154213998',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/easy-do/jlc-bot',
          blankTarget: true,
        },
        {
          key: 'gitee',
          title: 'gitee仓库',
          href: 'https://gitee.com/yuzhanfeng/JLC-BOT',
          blankTarget: true,
        },
        {
          key: 'currentVersion',
          title: '当前版本:'+currentVersion,
          href: 'https://gitee.com/yuzhanfeng/JLC-BOT',
          blankTarget: true,
        },
        {
          key: 'currentMode',
          title: '当前模式:'+currentMode,
          href: 'https://gitee.com/yuzhanfeng/JLC-BOT',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
