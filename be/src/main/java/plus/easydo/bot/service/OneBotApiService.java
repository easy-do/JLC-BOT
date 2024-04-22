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
     * @param userId   userId
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

}
