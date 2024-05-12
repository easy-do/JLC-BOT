package plus.easydo.bot.service;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description 抽象 onebot的api能力
 */
public interface OneBotApiService {


    /**
     * 机器人登录信息
     *
     * @param botNumber botNumber
     * @return java.lang.String
     * @author laoyu
     * @date 2024-03-31
     */
    String getLoginInfo(String botNumber);

    /**
     * 群组列表
     *
     * @param botNumber botNumber
     * @return java.lang.String
     * @author laoyu
     * @date 2024-03-31
     */
    String getGroupList(String botNumber);

    /**
     * 发送群消息
     *
     * @param botNumber botNumber
     * @param message   message
     * @author laoyu
     * @date 2024-03-31
     */
    void sendMessage(String botNumber, String message);


    /**
     * 发送群消息
     *
     * @param botNumber botNumber
     * @param userId    userId
     * @param message   message
     * @author laoyu
     * @date 2024-03-31
     */
    void sendPrivateMessage(String botNumber, String userId, String message);


    /**
     * 发送群消息
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param message   message
     * @author laoyu
     * @date 2024-03-31
     */
    void sendGroupMessage(String botNumber, String groupId, String message);


    /**
     * 向群组发送文件
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param filePath  filePath
     * @author laoyu
     * @date 2024-03-31
     */
    void sendGroupFile(String botNumber, String groupId, String filePath);

    /**
     * 撤回消息
     *
     * @param botNumber botNumber
     * @param messageId messageId
     * @author laoyu
     * @date 2024-03-31
     */
    void deleteMsg(String botNumber, String messageId);

    /**
     * 群组禁言
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param userId    userId
     * @param duration  duration
     * @author laoyu
     * @date 2024-03-31
     */
    void setGroupBan(String botNumber, String groupId, String userId, Long duration);

    /**
     * 群组全体禁言
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param enable    enable
     * @author laoyu
     * @date 2024-03-31
     */
    void setGroupWholeBan(String botNumber, String groupId, boolean enable);


    /**
     * 移除群组成员
     *
     * @param botNumber        botNumber
     * @param groupId          groupId
     * @param userId           userId
     * @param rejectAddRequest rejectAddRequest
     * @author laoyu
     * @date 2024-03-31
     */
    void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest);

    /**
     * 设置群组添加请求
     *
     * @param botNumber 机器人号码
     * @param flag      标识
     * @param type      类型
     * @param approve   审核结论
     * @param remark    备注
     */
    void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark);

    /**
     * 设置群管理员
     *
     * @param botNumber botNumber
     * @param groupId   群组ID
     * @param userId    用户ID
     * @param enable    是否设置为管理员
     * @author laoyu
     * @date 2024/4/23
     */
    void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable);


    /**
     * 设置群组退出
     *
     * @param botNumber 机器人号码
     * @param groupId   群组ID
     * @param isDismiss 是否解散群组
     */
    void setGroupLeave(String botNumber, String groupId, boolean isDismiss);

    /**
     * 设置群组成员名片
     *
     * @param botNumber 机器人号码
     * @param groupId   群组ID
     * @param userId    用户ID
     * @param card      名片信息
     */
    void setGroupCard(String botNumber, String groupId, String userId, String card);

    /**
     * 设置群组名称
     *
     * @param botNumber 机器人号码
     * @param groupId   群组ID
     * @param groupName 新的群组名称
     */
    void setGroupName(String botNumber, String groupId, String groupName);

    /**
     * 设置好友添加请求
     *
     * @param botNumber 机器人号码
     * @param flag      标识
     * @param approve   是否同意好友请求
     * @param remark    备注信息
     */
    void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark);

    /**
     * 获取消息详情
     *
     * @param botNumber botNumber
     * @param messageId messageId
     * @return java.lang.String
     * @author laoyu
     * @date 2024/5/11
     */
    String getMsg(String botNumber, String messageId);

    /**
     * 获取合并转发消息
     *
     * @param botNumber botNumber
     * @param messageId messageId
     * @return java.lang.String
     * @author laoyu
     * @date 2024/5/11
     */
    String getForwardMsg(String botNumber, String messageId);

    /**
     * 获取群成员列表
     * @param botNumber
     * @param groupId
     * @return
     */
    String getGroupMemberList(String botNumber, String groupId);

    /**
     * 获取群成员信息
     *
     * @param botNumber botNumber
     * @param groupId groupId
     * @param userId userId
     * @param noCache noCache
     * @return java.lang.String
     * @author laoyu
     * @date 2024/5/11
     */
    String getGroupMemberInfo(String botNumber, String groupId, String userId, boolean noCache);

    /**
     * 获取群消息历史记录
     *
     * @param botNumber botNumber
     * @param groupId groupId
     * @param messageSeq messageSeq
     * @return java.lang.String
     * @author laoyu
     * @date 2024/5/11
     */
    String getGroupMsgHistory(String botNumber, String groupId, Integer messageSeq);

    /**
     * 发送合并转发消息
     *
     * @param botNumber botNumber
     * @param data data
     * @author laoyu
     * @date 2024/5/11
     */
    void sendForwardMsg(String botNumber, String data);

    /**
     * 发送合并转发消息 ( 群聊 )
     *
     * @param botNumber botNumber
     * @param groupId groupId
     * @param messages messages
     * @author laoyu
     * @date 2024/5/11
     */
    void sendGroupForwardMsg(String botNumber, String groupId, String messages);

    /**
     * 发送合并转发消息 ( 好友 )
     *
     * @param botNumber botNumber
     * @param userId userId
     * @param messages messages
     * @author laoyu
     * @date 2024/5/11
     */
    void sendPrivateForwardMsg(String botNumber, String userId, String messages);

    /**
     * 调用自定义API
     *
     * @param botNumber botNumber
     * @param action action
     * @param data data
     * @return java.lang.String
     * @author laoyu
     * @date 2024/5/12
     */
    String customApi(String botNumber,String action, String data);
}
