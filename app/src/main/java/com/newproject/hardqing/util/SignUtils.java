package com.newproject.hardqing.util;

import android.text.TextUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author LLhon
 * @description
 * @date 2019/8/16 21:20
 */
public class SignUtils {

    public static String getSign(Map<String, String> map, String key) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (!TextUtils.isEmpty(item.getKey())) {
                    String k = item.getKey();
                    String v = item.getValue();
                    if (!TextUtils.isEmpty(v)) {
                        sb.append(k + "=" + v + "&");
                    }
                }
            }
            sb.append("key=" + key);
            result = sb.toString();

            //进行MD5加密，并转为大写字母
            result = getMD5Value(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static String getMD5Value(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5ValueByteArray = messageDigest.digest(value.getBytes());
            BigInteger bigInteger = new BigInteger(1, md5ValueByteArray);
            return bigInteger.toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
