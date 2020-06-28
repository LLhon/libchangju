package com.newproject.hardqing.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * heweidong created by 2017/11/14
 */
public class JsonUtil {
    private static final String CMD = "cmd";
    public static final String ERROR = "error";
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String NOTICE = "notice";
    private static final String CONTENT = "content";

    public static boolean is200(String body) {
        boolean is200 = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getInt(CODE) == 200) {
                is200 = true;
//                ToastUtil.showShort(context, root.getString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is200;
    }

    public static boolean is14(String body) {
        boolean is200 = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getInt(CODE) == 14) {
                is200 = true;
//                ToastUtil.showShort(context, root.getString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is200;
    }

    public static boolean is3(String body) {
        boolean is200 = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getInt(CODE) == 3) {
                is200 = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is200;
    }

    public static boolean is0(String body) {
        boolean is0 = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getInt(CODE) == 0) {
                is0 = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is0;
    }


    public static boolean is1(String body) {
        boolean is1 = false;
        try {
            JSONObject root = new JSONObject(body);
            boolean has = root.has(CODE);
            if (has) {
                if (root.getInt(CODE) == 1) {
                    is1 = true;
                }
            } else {
                is1 = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is1;
    }

    //Token 超时 检验token是否超时
    public static boolean isTokenIsExpire(String body) {
        boolean isTimeOut = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("token is expire")) {
                isTimeOut = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isTimeOut;
    }

    //Token 超时 检验token是否错误
    public static boolean isTokenErr(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("token error")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }


    // code tip 用户已经注册
    public static boolean isHasReigister(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("mobile has been register")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static boolean sendErrCode(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("send code error")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    // code tip code err
    public static boolean isCodeErr(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("code error")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static boolean phoneRegister(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("mobile exist")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    // 手机号是否注册
    public static boolean isMoblieRegister(String body) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(body);
            if (root.getString(MSG).equals("mobile not register")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }


    public static boolean isPasswordErr(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("password error")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static String toString(String body) {
        JSONObject root = null;
        String msg = "";
        try {
            root = new JSONObject(body);
            msg = root.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;

    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

    public static String getStringByKey(String key, String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSocketCmd(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.has(CMD)) {
                return jsonObject.getString(CMD);
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSocketMsg(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.has(MSG)) {
                return jsonObject.getString(MSG);
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getSocketContent(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.has(CONTENT)) {
                return jsonObject.getString(CONTENT);
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getSocketErr(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.has(ERROR)) {
                return jsonObject.getString(ERROR);
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getIntByKey(String key, String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getData(String s) {
        JsonElement parse = new JsonParser().parse(s);
        if (parse.isJsonObject()) {
            JsonObject json = parse.getAsJsonObject();
            if (json.has("data")) {
                return json.get("data").toString();
            }
        }
        return "";
    }


    public static String getXinData(String s) {
        String data = "";
        try {
            JSONObject jsonObject = new JSONObject(s);
            data = jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Map<String, String> getMap(String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Map<String, String> map = gson.fromJson(JsonUtil.getData(s), type);
        Set<String> keySet = map.keySet();
        return map;
    }

    public static String getMsg(String content) {
        String msg = "";
        try {
            JSONObject json = new JSONObject(content);
            msg = json.getString("msg");
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getDescription(String content) {
        String msg = "";
        try {
            JSONObject json = new JSONObject(content);
            boolean msg1 = json.has("description");
            if (msg1) {
                msg = json.getString("description");
            }
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static boolean isCompany(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("account is company")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    //二维码已失效
    public static boolean isQrcodeInvalid(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("invalid qrcode")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static boolean isQrExpire(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("qrcode is expire")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static boolean isQrNotExist(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("qrcode not exist")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static boolean isTokenError(String msg) {
        boolean isErr = false;
        try {
            JSONObject root = new JSONObject(msg);
            if (root.getString(MSG).equals("token error")) {
                isErr = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErr;
    }

    public static CharSequence getCmd(String content) {
        String msg = "";
        try {
            JSONObject json = new JSONObject(content);
            boolean msg1 = json.has("cmd");
            if (msg1) {
                msg = json.getString("cmd");
            }
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
