package com.newproject.hardqing.util;

import android.util.Log;

import com.newproject.hardqing.entity.SunSongRequest;
import com.newproject.hardqing.entity.SunSongResponse;
import com.newproject.hardqing.entity.SunSongResult;
import com.newproject.hardqing.entity.SunTime;
import com.newproject.hardqing.listener.ICallback;
import com.newproject.hardqing.util.http.ApiEndpointClient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;


public class SunShineUtils {

    private static final String API_KEY = "RrC4mD1gD2bA2nQ0fF6eL4qF1aM2cM8Z";
    private static final String PLAT_CODE = "sh_hL8fK3fF0fB7";


    /***
     * 获取歌曲信息
     */
    public static void getSongList(final String searchValue, final int theme, final int startPage, final int pageSize, final ICallback<List<SunSongResult>> callback) {
        ApiEndpointClient.getEndpointV2().synchronizationTime()
                .enqueue(new Callback<SunTime>() {
                    @Override
                    public void onResponse(Call<SunTime> call, retrofit2.Response<SunTime> response) {
                        Log.i("SunShineUtils", "getServerTimeInSecond onSuccess : " + response);
                        if (response == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("response==null"));
                            }
                            return;
                        }
                        SunTime timeStampBean = response.body();
                        Log.i("SunShineUtils", "getServerTimeInSecond timeStampBean : " + timeStampBean);
                        if (timeStampBean == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("timeStampBean==null"));

                            }
                            return;
                        }
                        getSong(timeStampBean.getResult(), searchValue, theme, startPage, pageSize, callback);
                    }

                    @Override
                    public void onFailure(Call<SunTime> call, Throwable t) {
                        Log.i("SunShineUtils", "getServerTimeInSecond onFailure : " + t);
                        if (callback != null) {
                            callback.onError(t);
                        }
                    }
                });
    }

    /***
     * 获取歌手列表
     */
    public static void getSingerList(final String searchValue, final int theme, final int startPage, final int pageSize, final ICallback<List<SunSongResult>> callback) {
        ApiEndpointClient.getEndpointV2().synchronizationTime()
                .enqueue(new Callback<SunTime>() {
                    @Override
                    public void onResponse(Call<SunTime> call, retrofit2.Response<SunTime> response) {
                        Log.i("SunShineUtils", "getServerTimeInSecond onSuccess : " + response);
                        if (response == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("synchronizationTime response null"));
                            }
                            return;
                        }
                        SunTime timeStampBean = response.body();
                        if (timeStampBean == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("timeStampBean==null"));

                            }
                            return;
                        }
                        getSingerInfo(timeStampBean.getResult(), searchValue, theme, startPage, pageSize, callback);
                    }

                    @Override
                    public void onFailure(Call<SunTime> call, Throwable t) {
                        if (callback != null) {
                            callback.onError(t);
                        }
                    }
                });
    }

    public static void getSong(long timeStamp, String searchValue, int theme, int startPage, int pageSize, final ICallback<List<SunSongResult>> callback) {

        String noncestr = ("" + (int) (Math.random() * 10000000));

        HashMap<String, String> param = new HashMap<>();
        param.put("platcode", PLAT_CODE);
        param.put("startpage", String.valueOf(startPage));
        param.put("pagesize", String.valueOf(pageSize));
        param.put("theme", String.valueOf(theme));
        param.put("timestamp", String.valueOf(timeStamp));
        param.put("noncestr", noncestr);
        String sign = getSign(param);

        SunSongRequest sunSongRequest = new SunSongRequest();
        sunSongRequest.setPlatcode(PLAT_CODE);
        sunSongRequest.setStartpage(startPage);
        sunSongRequest.setPagesize(pageSize);
        sunSongRequest.setSearch(searchValue);
        sunSongRequest.setTheme(theme);
        sunSongRequest.setTimestamp(timeStamp);
        sunSongRequest.setNoncestr(noncestr);
        sunSongRequest.setSign(sign);
        Log.i("SunShineUtils", "songSearch onResponse sunSongRequest   : " + sunSongRequest);
        ApiEndpointClient.getEndpointV2().getSongInfo(sunSongRequest)
                .enqueue(new Callback<SunSongResponse>() {
                    @Override
                    public void onResponse(Call<SunSongResponse> call, retrofit2.Response<SunSongResponse> response) {
                        if (response == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("response null"));
                            }
                            return;
                        }
                        Log.i("SunShineUtils", "getSong onResponse onSuccess 22 : " + response.body());
                        SunSongResponse sunSongResponse = response.body();
                        if (sunSongResponse == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("response null"));
                            }
                            return;
                        }
                        List<SunSongResult> songResultList = sunSongResponse.getSongResultList();
                        if (songResultList == null || songResultList.isEmpty()) {
                            if (callback != null) {
                                callback.onError(new Throwable("songResultList null"));
                            }
                            return;
                        }
                        if (callback != null) {
                            callback.onResult(songResultList);
                        }
                    }

                    @Override
                    public void onFailure(Call<SunSongResponse> call, Throwable t) {
                        Log.i("SunShineUtils", "getSong onFailure Throwable : " + t);
                        if (callback != null) {
                            callback.onError(t);
                        }

                    }
                });

    }

    public static void getSingerInfo(long timeStamp, String searchValue, int theme, int startPage, int pageSize, final ICallback<List<SunSongResult>> callback) {

        String noncestr = ("" + (int) (Math.random() * 10000000));
        HashMap<String, String> param = new HashMap<>();
        param.put("platcode", PLAT_CODE);
        param.put("startpage", String.valueOf(startPage));
        param.put("pagesize", String.valueOf(pageSize));
        param.put("timestamp", String.valueOf(timeStamp));
        param.put("noncestr", noncestr);
        String sign = getSign(param);

        SunSongRequest sunSongRequest = new SunSongRequest();
        sunSongRequest.setPlatcode(PLAT_CODE);
        sunSongRequest.setStartpage(startPage);
        sunSongRequest.setPagesize(pageSize);
        sunSongRequest.setSearch(searchValue);
        sunSongRequest.setTheme(theme);
        sunSongRequest.setTimestamp(timeStamp);
        sunSongRequest.setNoncestr(noncestr);
        sunSongRequest.setSign(sign);
        Log.i("SunShineUtils", "getSingerInfo onResponse sunSongRequest   : " + sunSongRequest);
        ApiEndpointClient.getEndpointV2().getSingerInfo(sunSongRequest)
                .enqueue(new Callback<SunSongResponse>() {
                    @Override
                    public void onResponse(Call<SunSongResponse> call, retrofit2.Response<SunSongResponse> response) {
                        Log.i("SunShineUtils", "getSingerInfo onResponse onSuccess 444 : " + response.body());
                        if (response == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("response null"));
                            }
                            return;
                        }
                        Log.i("SunShineUtils", "getSingerInfo onResponse onSuccess 555 : " + response.body());
                        SunSongResponse sunSongResponse = response.body();
                        if (sunSongResponse == null) {
                            if (callback != null) {
                                callback.onError(new Throwable("response null"));
                            }
                            return;
                        }
                        List<SunSongResult> songResultList = sunSongResponse.getSongResultList();
                        if (songResultList == null || songResultList.isEmpty()) {
                            if (callback != null) {
                                callback.onError(new Throwable("songResultList null"));
                            }
                            return;
                        }
                        if (callback != null) {
                            callback.onResult(songResultList);
                        }
                    }

                    @Override
                    public void onFailure(Call<SunSongResponse> call, Throwable t) {
                        Log.i("SunShineUtils", "getSingerInfo onFailure Throwable : " + t.getMessage());
                        if (callback != null) {
                            callback.onError(t);
                        }
                    }
                });

    }

    private static String getSign(Map<String, String> params) {
        return string2MD5(getSortParams(params) + "&key=" + API_KEY).toUpperCase();
    }

    private static String getSortParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            if (sb.length() != 0) {
                sb.append("");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }


    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String str) {
        String result = "";
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte b[] = md5.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        result = buf.toString();
        return result;
    }
}
