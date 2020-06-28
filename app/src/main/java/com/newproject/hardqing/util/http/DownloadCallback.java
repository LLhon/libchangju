
package com.newproject.hardqing.util.http;

import java.io.File;

/**
 * 文件下载的回调，可以指定下载保存的目录和文件名，回调下载进度
 */

public abstract class DownloadCallback extends Callback<File> {
    /**
     * 下载存放的文件目录
     */
    private String destFileDir;
    /**
     * 下载存放的文件名
     */
    private String destFileName;

    /**
     * 无参构造，下载的文件会保存在{@code /download}目录，文件名根据请求的{@code url}返回决定
     */
    public DownloadCallback() {
        this(null);
    }

    /**
     * 指定文件保存路径的有参构造，文件名根据请求的{@code url}返回决定
     * @param destFileDir   - 文件保存目录
     */
    public DownloadCallback(String destFileDir) {
        this(destFileDir, null);
    }

    /**
     * 指定文件路径和文件名的有参构造
     * @param destFileDir   - 文件目录
     * @param destFileName  - 文件名
     */
    public DownloadCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    String getDestFileDir() {
        return destFileDir;
    }

    String getDestFileName() {
        return destFileName;
    }
}
