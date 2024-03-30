package plus.easydo.bot.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.wcf.Client;
import plus.easydo.bot.wcf.Wcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-29
 * @Description wcf工具
 */
public class OneBotWcfClientUtils {

    private OneBotWcfClientUtils() {
    }

    public static final Map<String,Client> CLIENT_CACHE = new HashMap<>();

    public static void saveClient(Client client){
        CLIENT_CACHE.put(client.getSelfWxid(),client);
    }
    public static Client getClient(String botNumber){
        if(!CLIENT_CACHE.isEmpty()){
            return CLIENT_CACHE.get(botNumber);
        }
        throw new BaseException("client缓存为空");
    }

    public static String getLoginInfo(String botNumber) {
            Client client = getClient(botNumber);
            Wcf.UserInfo userInfo = client.getUserInfo();
            JSONObject result = new JSONObject();
            result.set("home",userInfo.getHome());
            result.set("mobile",userInfo.getMobile());
            result.set("nem",userInfo.getName());
            result.set("wxId",userInfo.getWxid());
            return  result.toStringPretty();
    }

    public static String getGroupList(String botNumber) {
        List<Wcf.RpcContact> contacts = getClient(botNumber).getContacts();
        JSONArray array = JSONUtil.createArray();
        for (Wcf.RpcContact contact : contacts){
            String wxid = contact.getWxid();
            if(StrUtil.endWith(wxid,"@chatroom")){
                JSONObject obj = JSONUtil.createObj();
                obj.set("wxid",wxid);
                obj.set("code",contact.getCode());
                obj.set("remark",contact.getRemark());
                obj.set("name",contact.getName());
                obj.set("country",contact.getCountry());
                obj.set("province",contact.getProvince());
                obj.set("city",contact.getCity());
                obj.set("gender",contact.getGender());
                array.add(obj);
            }
        }
        return array.toStringPretty();
    }

    public static void sendMessage(String botNumber, String receiver, String message) {
        Client client = getClient(botNumber);
        client.sendText(message,receiver,"");
    }

    public static void deleteMsg(String botNumber, String messageId) {
        Client client = getClient(botNumber);
        client.revokeMsg(Integer.parseInt(messageId));
    }

    public static void sendGroupFile(String botNumber, String path, String receiver) {
        Client client = getClient(botNumber);
        client.sendFile(path,receiver);
    }
}
