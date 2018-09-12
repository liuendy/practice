package com.shangde.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author luwenlong
 * @date 2017/11/22 0022
 * @description EHR对外接口 签名验证工具类
 */
public class CheckTokenUtils {

    private static final String TOKEN_SPLIT = "&";

    public static String decryptToken(String encToken ,String key) {
        if (encToken != null) {
            try {
                //byte[] decode = Base64.decode(encToken);
                String decrypt = EncryptUtil.decrypt(encToken, key);

                return decrypt;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getSystemName(String decToken) {
        if (decToken != null) {
            String[] split = decToken.split(TOKEN_SPLIT);
            if (split.length >= 2) {
                String sysName = split[split.length - 1];
                return sysName;
            }
        }
        return null;
    }

    public static JSONObject getParamJson(String encToken,String key) {
        String decToken = decryptToken(encToken,key);
        if (decToken != null) {
            try {
                JSONObject parseObject = JSON.parseObject(decToken.substring(0, decToken.lastIndexOf(TOKEN_SPLIT)));
                return parseObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
