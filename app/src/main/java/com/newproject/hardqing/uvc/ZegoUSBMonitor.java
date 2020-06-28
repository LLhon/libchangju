package com.newproject.hardqing.uvc;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ZegoUSBMonitor {
    private static final boolean DEBUG = true;   //to do
    private static final String ACTION_USB_PERMISSION_BASE = "com.zego.USB_PERMISSION.";
    private final String ACTION_USB_PERMISSION = ACTION_USB_PERMISSION_BASE + hashCode();
    private static final String TAG = "ZegoUSBMonitor";
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";


    private final WeakReference<Context> mWeakContext;
    private final UsbManager mUsbManager;
    private final OnDeviceConnectListener mOnDeviceConnectListener;
    private PendingIntent mPermissionIntent = null;
    private final HandlerThread mHandlerThread;
    private final Handler mAsyncHandler;
    private volatile boolean destroyed;

    private final ConcurrentHashMap<UsbDevice, UsbControlBlock> mCtrlBlocks = new ConcurrentHashMap<UsbDevice, UsbControlBlock>();
    private final SparseArray<WeakReference<UsbDevice>> mHasPermissions = new SparseArray<WeakReference<UsbDevice>>();
    private volatile int mDeviceCounts = 0;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (destroyed) return;
            if (DEBUG) Log.v(TAG, "onReceive:");
            final String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                // when received the result of requesting USB permission
                synchronized (ZegoUSBMonitor.this) {
                    final UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            // get permission, call onConnect
                            processConnect(device);
                        }
                    } else {
                        // failed to get permission
                        processCancel(device);
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                final UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                updatePermission(device, hasPermission(device));
                processAttach(device);
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Log.e("test", "**** UsbManager.ACTION_USB_DEVICE_DETACHED");
                // when device removed
                final UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    UsbControlBlock ctrlBlock = mCtrlBlocks.remove(device);
                    if (ctrlBlock != null) {
                        // cleanup
                        ctrlBlock.close();
                    }
                    mDeviceCounts = 0;
                    processDettach(device);
                }
            }
        }
    };

    private final void processConnect(final UsbDevice device) {
        if (destroyed) return;
        updatePermission(device, true);
        mAsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                UsbControlBlock ctrlBlock;
                final boolean createNew;
                ctrlBlock = mCtrlBlocks.get(device);
                if (ctrlBlock == null) {
                    ctrlBlock = new UsbControlBlock(ZegoUSBMonitor.this, device);
                    mCtrlBlocks.put(device, ctrlBlock);
                    createNew = true;
                } else {
                    createNew = false;
                }
                if (mOnDeviceConnectListener != null) {
                    mOnDeviceConnectListener.onConnect(device, ctrlBlock, createNew);
                }
            }
        });
    }

    private final void processCancel(final UsbDevice device) {
        if (destroyed) return;
        if (DEBUG) Log.v(TAG, "processCancel:");
        updatePermission(device, false);
        if (mOnDeviceConnectListener != null) {
            mAsyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnDeviceConnectListener.onCancel(device);
                }
            });
        }
    }

    private final void processAttach(final UsbDevice device) {
        if (destroyed) return;
        if (DEBUG) Log.v(TAG, "processAttach:");
        if (mOnDeviceConnectListener != null) {
            mAsyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnDeviceConnectListener.onAttach(device);
                }
            });
        }
    }

    private final void processDettach(final UsbDevice device) {
        if (destroyed) return;
        if (DEBUG) Log.v(TAG, "processDettach:");
        if (mOnDeviceConnectListener != null) {
            mAsyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnDeviceConnectListener.onDettach(device);
                }
            });
        }
    }

    public interface OnDeviceConnectListener {
        /**
         * called when device attached
         */
        public void onAttach(UsbDevice device);

        /**
         * called when device dettach(after onDisconnect)
         */
        public void onDettach(UsbDevice device);

        /**
         * called after device opend
         */
        public void onConnect(UsbDevice device, UsbControlBlock ctrlBlock, boolean createNew);

        /**
         * called when USB device removed or its power off (this callback is called after device
         * closing)
         */
        public void onDisconnect(UsbDevice device, UsbControlBlock ctrlBlock);

        /**
         * called when canceled or could not get permission from user
         */
        public void onCancel(UsbDevice device);
    }

    public ZegoUSBMonitor(final Context context, final OnDeviceConnectListener listener) {
        mWeakContext = new WeakReference<Context>(context);
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mOnDeviceConnectListener = listener;
        mHandlerThread = new HandlerThread("USBMonitor");
        mHandlerThread.start();
        mAsyncHandler = new Handler(mHandlerThread.getLooper());
        destroyed = false;
    }

    /**
     * Release all related resources, never reuse again
     */
    public void destroy() {
        if (DEBUG) Log.i(TAG, "destroy:");
        unregister();
        if (!destroyed) {
            destroyed = true;
            // モニターしているUSB機器を全てcloseする
            final Set<UsbDevice> keys = mCtrlBlocks.keySet();
            if (keys != null) {
                UsbControlBlock ctrlBlock;
                try {
                    for (final UsbDevice key : keys) {
                        ctrlBlock = mCtrlBlocks.remove(key);
                        if (ctrlBlock != null) {
                            ctrlBlock.close();
                        }
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            mCtrlBlocks.clear();
            try {
                mAsyncHandler.getLooper().quit();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void register() throws IllegalStateException {
        if (destroyed) throw new IllegalStateException("already destroyed");
        if (mPermissionIntent == null) {
            final Context context = mWeakContext.get();
            if (context != null) {
                mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
                final IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                // ACTION_USB_DEVICE_ATTACHED never comes on some devices so it should not be added here
                filter.addAction(ACTION_USB_DEVICE_ATTACHED);
                filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
                context.registerReceiver(mUsbReceiver, filter);
            }
            // start connection check
            mDeviceCounts = 0;
            mAsyncHandler.postDelayed(mDeviceCheckRunnable, 1000);
        }
    }

    public synchronized void unregister() throws IllegalStateException {
        // 接続チェック用Runnableを削除
        mDeviceCounts = 0;
        if (!destroyed) {
            mAsyncHandler.removeCallbacks(mDeviceCheckRunnable);
        }
        if (mPermissionIntent != null) {
//			if (DEBUG) Log.i(TAG, "unregister:");
            final Context context = mWeakContext.get();
            try {
                if (context != null) {
                    context.unregisterReceiver(mUsbReceiver);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                Log.w(TAG, e);
            }
            mPermissionIntent = null;
        }
    }

    private final Runnable mDeviceCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (destroyed) return;
            final List<UsbDevice> devices = getDeviceList();
            final int n = devices.size();
            final int hasPermissionCounts;
            final int m;
            synchronized (mHasPermissions) {
                hasPermissionCounts = mHasPermissions.size();
                mHasPermissions.clear();
                for (final UsbDevice device : devices) {
                    try {
                        hasPermission(device);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                m = mHasPermissions.size();
            }
            if ((n > mDeviceCounts) || (m > hasPermissionCounts)) {
                mDeviceCounts = n;
                if (mOnDeviceConnectListener != null) {
                    for (int i = 0; i < n; i++) {
                        final UsbDevice device = devices.get(i);
                        mAsyncHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOnDeviceConnectListener.onAttach(device);

                                requestPermission(device);
                            }
                        });
                    }
                }
            }
            mAsyncHandler.postDelayed(this, 2000);    // confirm every 2 seconds
        }
    };

    public final boolean hasPermission(final UsbDevice device) throws IllegalStateException {
        if (destroyed) throw new IllegalStateException("already destroyed");
        return updatePermission(device, device != null && mUsbManager.hasPermission(device));
    }

    public static final int getDeviceKey(final UsbDevice device, boolean useNewAPI) {
        if (device == null)
            return 0;

        final StringBuilder sb = new StringBuilder();
        sb.append(device.getVendorId());
        sb.append("#");    // API >= 12
        sb.append(device.getProductId());
        sb.append("#");    // API >= 12
        sb.append(device.getDeviceClass());
        sb.append("#");    // API >= 12
        sb.append(device.getDeviceSubclass());
        sb.append("#");    // API >= 12
        sb.append(device.getDeviceProtocol());

//        if (useNewAPI && BuildCheck.isAndroid5()) {
//            sb.append("#");
//            sb.append(device.getManufacturerName());
//            sb.append("#");	// API >= 21
//            sb.append(device.getConfigurationCount());
//            sb.append("#");	// API >= 21
//            if (BuildCheck.isMarshmallow()) {
//                sb.append(device.getVersion());
//                sb.append("#");	// API >= 23
//            }
//        }

        return sb.toString().hashCode();
    }

    private boolean updatePermission(final UsbDevice device, final boolean hasPermission) {
        final int deviceKey = getDeviceKey(device, false);
        synchronized (mHasPermissions) {
            if (hasPermission) {
                if (mHasPermissions.get(deviceKey) == null) {
                    mHasPermissions.put(deviceKey, new WeakReference<UsbDevice>(device));
                }
            } else {
                mHasPermissions.remove(deviceKey);
            }
        }
        return hasPermission;
    }

    public synchronized boolean requestPermission(final UsbDevice device) {
//		if (DEBUG) Log.v(TAG, "requestPermission:device=" + device);
        boolean result = false;
        if (!destroyed && (mPermissionIntent != null)) {
            if (device != null) {
                if (mUsbManager.hasPermission(device)) {
                    // call onConnect if app already has permission
                    processConnect(device);
                } else {
                    try {
                        // パーミッションがなければ要求する
                        mUsbManager.requestPermission(device, mPermissionIntent);
                    } catch (final Exception e) {
                        // Android5.1.xのGALAXY系でandroid.permission.sec.MDM_APP_MGMTという意味不明の例外生成するみたい
                        Log.w(TAG, e);
                        processCancel(device);
                        result = true;
                    }
                }
            } else {
                processCancel(device);
                result = true;
            }
        } else {
            processCancel(device);
            result = true;
        }
        return result;
    }

    public List<UsbDevice> getDeviceList() {
        try {
            if (destroyed) {
                throw new IllegalStateException("already destroyed");
            }
            if (mUsbManager == null) {
                return null;
            }
            final HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            final List<UsbDevice> result = new ArrayList<UsbDevice>();
            if (deviceList != null) {
                for (final UsbDevice device : deviceList.values()) {
                    //UVC device class=239, subclass=2
                    if (device.getDeviceClass() == 239 && device.getDeviceSubclass() == 2) {
                        result.add(device);
                    }
                }
            }
            return result;
        } catch (Exception e) {

        }
        return null;
    }

    /********************** USB Control *******************************/

    public static final class UsbControlBlock implements Cloneable {
        private final WeakReference<ZegoUSBMonitor> mWeakMonitor;
        private final WeakReference<UsbDevice> mWeakDevice;
        protected UsbDeviceConnection mConnection;
        //protected final UsbDeviceInfo mInfo;
        private final int mBusNum;
        private final int mDevNum;

        /**
         * this class needs permission to access USB device before constructing
         */
        private UsbControlBlock(final ZegoUSBMonitor monitor, final UsbDevice device) {
            if (DEBUG) Log.i(TAG, "UsbControlBlock:constructor");
            mWeakMonitor = new WeakReference<ZegoUSBMonitor>(monitor);
            mWeakDevice = new WeakReference<UsbDevice>(device);
            mConnection = monitor.mUsbManager.openDevice(device);
            //mInfo = updateDeviceInfo(monitor.mUsbManager, device, null);
            final String name = device.getDeviceName();
            final String[] v = !TextUtils.isEmpty(name) ? name.split("/") : null;
            int busnum = 0;
            int devnum = 0;
            if (v != null) {
                busnum = Integer.parseInt(v[v.length - 2]);
                devnum = Integer.parseInt(v[v.length - 1]);
            }
            mBusNum = busnum;
            mDevNum = devnum;

            if (mConnection != null) {
                final int desc = mConnection.getFileDescriptor();
                final byte[] rawDesc = mConnection.getRawDescriptors();
                Log.i(TAG, String.format(Locale.US, "name=%s,desc=%d,busnum=%d,devnum=%d,rawDesc=", name, desc, busnum, devnum) + rawDesc);
            } else {
                Log.e(TAG, "could not connect to device " + name);
            }
        }

        private UsbControlBlock(final UsbControlBlock src) throws IllegalStateException {
            final ZegoUSBMonitor monitor = src.getUSBMonitor();
            final UsbDevice device = src.getDevice();
            if (device == null) {
                Log.e(TAG, " UsbControlBlock device may already be removed");
                throw new IllegalStateException("device may already be removed");
            }
            mConnection = monitor.mUsbManager.openDevice(device);
            if (mConnection == null) {
                Log.e(TAG, " UsbControlBlock device may already be removed or have no permission");
                throw new IllegalStateException("device may already be removed or have no permission");
            }
            //mInfo = updateDeviceInfo(monitor.mUsbManager, device, null);
            mWeakMonitor = new WeakReference<ZegoUSBMonitor>(monitor);
            mWeakDevice = new WeakReference<UsbDevice>(device);
            mBusNum = src.mBusNum;
            mDevNum = src.mDevNum;
            // FIXME USBMonitor.mCtrlBlocksに追加する(今はHashMapなので追加すると置き換わってしまうのでだめ, ListかHashMapにListをぶら下げる?)
        }

        public synchronized void close() {
            if (DEBUG) Log.i(TAG, "UsbControlBlock#close:");

            if (mConnection != null) {
                mConnection.close();
                mConnection = null;
                final ZegoUSBMonitor monitor = mWeakMonitor.get();
                if (monitor != null) {
                    if (monitor.mOnDeviceConnectListener != null) {
                        monitor.mOnDeviceConnectListener.onDisconnect(mWeakDevice.get(), UsbControlBlock.this);
                    }
                    monitor.mCtrlBlocks.remove(mWeakDevice.get());
                }
            }
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null) return false;
            if (o instanceof UsbControlBlock) {
                final UsbDevice device = ((UsbControlBlock) o).getDevice();
                return device == null ? mWeakDevice.get() == null
                        : device.equals(mWeakDevice.get());
            } else if (o instanceof UsbDevice) {
                return o.equals(mWeakDevice.get());
            }
            return super.equals(o);
        }

        public UsbControlBlock clone() throws CloneNotSupportedException {
            final UsbControlBlock ctrlblock;
            try {
                ctrlblock = new UsbControlBlock(this);
            } catch (final IllegalStateException e) {
                throw new CloneNotSupportedException(e.getMessage());
            }
            return ctrlblock;
        }

        public ZegoUSBMonitor getUSBMonitor() {
            return mWeakMonitor.get();
        }

        public final UsbDevice getDevice() {
            return mWeakDevice.get();
        }

        public String getDeviceName() {
            final UsbDevice device = mWeakDevice.get();
            return device != null ? device.getDeviceName() : "";
        }

        public int getVenderId() {
            final UsbDevice device = mWeakDevice.get();
            return device != null ? device.getVendorId() : 0;
        }

        public int getProductId() {
            final UsbDevice device = mWeakDevice.get();
            return device != null ? device.getProductId() : 0;
        }

        public int getBusNum() {
            return mBusNum;
        }

        public int getDevNum() {
            return mDevNum;
        }

        public synchronized int getFileDescriptor() throws IllegalStateException {
            checkConnection();
            return mConnection.getFileDescriptor();
        }

        private synchronized void checkConnection() throws IllegalStateException {
            if (mConnection == null) {
                throw new IllegalStateException("already closed");
            }
        }
    }
}
