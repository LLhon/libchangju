package com.newproject.hardqing.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.Log;
import com.newproject.hardqing.constant.PreConst;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by LLhon
 */
public class FileUtil {

    public static String getPath(Context context, String fileName) {
        String path = context.getExternalCacheDir().getPath();
        File pathFile = new File(path + "/" + fileName);
        if (!pathFile.exists()) {
            copyFileFromAssets(context, fileName, pathFile.getPath());
        }
        return pathFile.getPath();
    }

    /**
     * 从assets目录下拷贝文件到存储卡.
     *
     * @param context            安卓上下文：用于获取 assets 目录下的资源
     * @param assetsFilePath     assets文件的路径名如：xxx.mp3
     * @param targetFileFullPath sd卡目标文件路径如：/sdcard/xxx.mp3
     *
     */
    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        Log.d("Tag", "copyFileFromAssets ");
        InputStream assetsFileInputStream;
        try {
            assetsFileInputStream = context.getAssets().open(assetsFilePath);
            copyFile(assetsFileInputStream, targetFileFullPath);
        } catch (IOException e) {
            Log.d("Tag", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从raw目录下拷贝文件到存储卡.
     *
     * @param context            安卓上下文：用于获取 assets 目录下的资源
     * @param id     assets文件的路径名如：xxx.mp3
     * @param targetFileFullPath sd卡目标文件路径如：/sdcard/xxx.mp3
     *
     */
    public static void copyFileFromRaw(Context context, @RawRes int id, String targetFileFullPath) {
        Log.d("Tag", "copyFileFromAssets ");
        InputStream rawFileInputStream;
        try {
            rawFileInputStream = context.getResources().openRawResource(id);
            copyFile(rawFileInputStream, targetFileFullPath);
        } catch (Exception e) {
            Log.d("Tag", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void copyFile(InputStream in, String targetPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param buffer 写入文件的内容
     * @param folder 保存文件的文件夹名称,如log；可为null，默认保存在sd卡根目录
     * @param fileName 文件名称，默认app_log.txt
     * @param append 是否追加写入，true为追加写入，false为重写文件
     * @param autoLine 针对追加模式，true为增加时换行，false为增加时不换行
     */
    public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer, @Nullable final String folder,
        @Nullable final String fileName, final boolean append, final boolean autoLine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
                String folderPath = "";
                if (sdCardExist) {
                    //TextUtils为android自带的帮助类
                    if (TextUtils.isEmpty(folder)) {
                        //如果folder为空，则直接保存在sd卡的根目录
                        folderPath = Environment.getExternalStorageDirectory()
                            + File.separator;
                    } else {
                        folderPath = Environment.getExternalStorageDirectory()
                            + File.separator + folder + File.separator;
                    }
                } else {
                    return;
                }

                File fileDir = new File(folderPath);
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        return;
                    }
                }
                File file;
                //判断文件名是否为空
                if (TextUtils.isEmpty(fileName)) {
                    file = new File(folderPath + "app_log.txt");
                } else {
                    file = new File(folderPath + fileName);
                }
                RandomAccessFile raf = null;
                FileOutputStream out = null;
                try {
                    if (append) {
                        //如果为追加则在原来的基础上继续写文件
                        raf = new RandomAccessFile(file, "rw");
                        raf.seek(file.length());
                        raf.write(buffer);
                        if (autoLine) {
                            raf.write("\n".getBytes());
                        }
                    } else {
                        //重写文件，覆盖掉原来的数据
                        out = new FileOutputStream(file);
                        out.write(buffer);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (raf != null) {
                            raf.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
