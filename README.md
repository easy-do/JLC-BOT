# JLC-BOT

#### 介绍

* java语言实现的机器人框架，提取自dnf-admin的机器人功能独立迭代
* 使用门槛低、不需要精通某种语言、只需要有最基本的编程经验和网络相关知识
* 低代码拖拽开发,类工作流网页配置但要比工作流更简单.
* 加入liteflow,节点执行性能强、扩展性高、支持的节点语言丰富
* 实现了antvx的节点数据格式转liteflow节点数据
* 支持节点执行详细的全链路在线网页调试、方便高级开发
* 支持沙盒测试

* gitee https://gitee.com/yuzhanfeng/JLC-BOT
* github https://github.com/easy-do/JLC-BOT

#### 使用技术

- 后端 spring-boot , sa-token , mybatis-flex , liteflow 
- 前端 antd-pro 、antvx6
- bot框架 LLOneBot 、 WeChatFerry
- 数据库: 默认使用H2DB、当前支持mysql和H2DB (其他数据库是否支持请查阅mybatis-flex官方文档)
- 使用OneBotV11协议的数据格式进行对接适配、理论上所有v11协议的bot客户端可直接对接


#### 相关链接
- sa-token: https://sa-token.cc/
- mybatis-flex: https://mybatis-flex.com/
- liteflow: https://liteflow.cc/
- antd-pro: https://pro.ant.design/docs/getting-started/
- antvx6: https://x6.antv.antgroup.com/tutorial/getting-started
- LLOneBot: https://github.com/LLOneBot/LLOneBot
- WeChatFerry: https://github.com/lich0821/WeChatFerry
- OneBot-V11标准: https://github.com/botuniverse/onebot-11


#### 支持功能

- 对接方式: httpPost 、正向websocket、反向websocket、 wx本地hook(windows)、WeChatFerry http上报
- 兼容平台 QQ （centos、windows、docker、mac） 微信(只能windows) 、其他平台(客户端支持什么就可以在什么平台跑)
- 已支持全部上报事件处理
- 低代码能力(部署后查看、未完善文档)


#### 快速开始

##### docker命令一键启动

``` shell
  docker run -dit --name jlc-bot -p 8888:8888 -v /data/jlc-bot:/data registry.cn-hangzhou.aliyuncs.com/gebilaoyu/jlc-bot:1.0.1
```
* 访问地址: http://服务器或主机ip:8888
* 接口文档: http://服务器或主机ip:8888/doc.html
* 数据库文件存放地址: /data/jlc-bot/db

##### docker编排

下载docker-compose.yaml后一键启动编排 

* 访问地址: http://服务器或主机ip:8888 
* 接口文档：http://服务器或主机ip:8888/doc.html
* 数据库文件存放地址: /data/db

##### windows安装使用

* https://www.123pan.com/s/DUoAjv-aBDRA.html 提取码: i4lJ
* 下载文件解压缩到本地磁盘、双击启动.bat 
* 访问地址：http://localhost:8888
* 接口文档：http://localhost:8888/doc.html
* 数据库文件存放地址: 解压目录/data/db


#### 页面截图展示

* 节点配置

<img src="./img/img.png">

* 在线调试

<img src="./img/img_1.png">

* 调试结果

<img src="./img/img_2.png">

* 模拟沙盒测试

<img src="./img/img_3.png">

#### 使用问题反馈和技术交流

http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=jKliJIxAFvZoZxBSw1NnlMjOj8pRR42f&authKey=vnozKSs2ou1MO68VXH1ct2AReURSyIj4jlVe%2BVAlA5h%2F0M1BsdhQP0YN6MqwRwBB&noverify=0&group_code=154213998

#### 文档地址（未完善该框架功能，后续补全）

https://doc.easydo.plus

#### 更新日志

* 省略.....
* 添加沙盒机器人测试 2024.3.31
* 添加上报日志管理 2024.4.1
* 添加java脚本节点支持 2024.4.1
* 补全所有对接方式 2024.4.4
* 添加系统节点导入导出 2024.4.4
* 支持并切换至H2DB数据库 2024.4.4





