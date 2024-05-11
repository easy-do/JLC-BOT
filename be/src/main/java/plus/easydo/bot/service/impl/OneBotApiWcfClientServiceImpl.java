package plus.easydo.bot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotWcfClientUtils;
import plus.easydo.bot.wcf.Client;
import plus.easydo.bot.wcf.Wcf;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议wcf-client请求api实现
 */
@Service("wcf_client_one_bot_api")
public class OneBotApiWcfClientServiceImpl implements OneBotApiService {


    @Override
    public String getLoginInfo(String botNumber) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        Wcf.UserInfo userInfo = client.getUserInfo();
        JSONObject result = new JSONObject();
        result.set("home", userInfo.getHome());
        result.set("mobile", userInfo.getMobile());
        result.set("nem", userInfo.getName());
        result.set("wxId", userInfo.getWxid());
        return result.toStringPretty();
    }

    @Override
    public String getGroupList(String botNumber) {
        List<Wcf.RpcContact> contacts = OneBotWcfClientUtils.getClient(botNumber).getContacts();
        JSONArray array = JSONUtil.createArray();
        for (Wcf.RpcContact contact : contacts) {
            String wxid = contact.getWxid();
            if (StrUtil.endWith(wxid, "@chatroom")) {
                JSONObject obj = JSONUtil.createObj();
                obj.set("wxid", wxid);
                obj.set("code", contact.getCode());
                obj.set("remark", contact.getRemark());
                obj.set("name", contact.getName());
                obj.set("country", contact.getCountry());
                obj.set("province", contact.getProvince());
                obj.set("city", contact.getCity());
                obj.set("gender", contact.getGender());
                array.add(obj);
            }
        }
        return array.toStringPretty();
    }

    @Override
    public void sendMessage(String botNumber, String message) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void sendPrivateMessage(String botNumber, String userId, String message) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.sendText(message, groupId, "");
    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.sendFile(filePath, groupId);
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.revokeMsg(Integer.parseInt(messageId));
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupLeave(String botNumber, String groupId, boolean isDismiss) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupCard(String botNumber, String groupId, String userId, String card) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupName(String botNumber, String groupId, String groupName) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public String getMsg(String botNumber, String messageId) {
        throw new BaseException("暂不支持的api");
    }
}
