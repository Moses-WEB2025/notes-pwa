package com.miui.mishare.tapshare.Util;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareConstant {
    public static final String FILE_EXPLORER_GLOBAL_PACKAGE_NAME = "com.mi.android.globalFileexplorer";
    public static final String FILE_EXPLORER_PACKAGE_NAME = "com.android.fileexplorer";
    public static final String GALLERY_PACKAGE_NAME = "com.miui.gallery";
    public static final String KEY_DEVICE_PLATFORM = "key_device_platform";
    public static final String SETTING_PACKAGE_NAME = "com.android.settings";
    public static final int TAP_SHARE_SDK_VERSION = 2;
    public static final int TAP_SHARE_SDK_VERSION_1 = 1;
    public static final int TAP_SHARE_SDK_VERSION_2 = 2;
    public static final int TAP_SHARE_SERVICE_VERSION = 1;

    public interface ErrorCode {
        public static final int CALLBACK_NOT_MATCH = 2;
        public static final int METHOD_CALL_INVALID = 3;
        public static final int METHOD_CALL_SUCCEED = 0;
        public static final int METHOD_PARAM_INVALID = 4;
        public static final int WINDOW_SPECIAL_STATES = 1;
    }

    public interface SendChannel {
        public static final int BASIC_CHANNEL = 1;
        public static final int NOT_BASIC_CHANNEL = 0;
    }

    public interface SendResult {
        public static final int SEND_FAILED = 1;
        public static final int SEND_SUCCESS = 0;
    }

    public interface SupportedPlatform {
        public static final int ANDROID_MI_PHONE = 0;
        public static final int IOS_IPHONE = 1;
        public static final int UNKNOWN = -1;
    }
}
