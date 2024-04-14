package plus.easydo.bot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.context.ContextBean;
import org.springframework.stereotype.Service;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.context.ContextBeanDesc;
import plus.easydo.bot.lowcode.context.ContextBeanMethodDesc;
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
@ContextBean("botApi")
@ContextBeanDesc("机器人API")
public class OneBotApiWcfClientServiceImpl implements OneBotApiService {


    @Override
    @ContextBeanMethodDesc("获取登录信息")
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
    @ContextBeanMethodDesc("获取群组列表")
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
    @ContextBeanMethodDesc("发送群消息")
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.sendText(message, groupId, "");
    }

    @Override
    @ContextBeanMethodDesc("发送群文件")
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.sendFile(filePath, groupId);
    }

    @Override
    @ContextBeanMethodDesc("撤回消息")
    public void deleteMsg(String botNumber, String messageId) {
        Client client = OneBotWcfClientUtils.getClient(botNumber);
        client.revokeMsg(Integer.parseInt(messageId));
    }

    @Override
    @ContextBeanMethodDesc("禁言群组指定成员 duration:时间(分钟) 0代表解除禁言")
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        throw new BaseException("暂暂不支持的api");
    }

    @Override
    @ContextBeanMethodDesc("设置群组全体禁言 duration:时间(分钟) 0代表解除禁言")
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    @ContextBeanMethodDesc("群组踢人 rejectAddRequest:是否加入黑名单")
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        throw new BaseException("暂不支持的api");
    }
}
