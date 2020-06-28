package com.newproject.hardqing.util;

import android.hardware.Camera;

import java.util.ArrayList;
import java.util.List;

public class CameraUtils {
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;

    //查找前置摄像头Id
    public static List<Integer> getFontCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        List<Integer> fontNumList = new ArrayList<Integer>();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                fontNumList.add(i);
            }
        }
        return fontNumList;
    }

    //查找后摄像头Id
    public static List<Integer> getBackCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        List<Integer> fontNumList = new ArrayList<Integer>();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_BACK) {
                fontNumList.add(i);
            }
        }
        return fontNumList;
    }
}