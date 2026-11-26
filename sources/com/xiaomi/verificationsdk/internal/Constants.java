package com.xiaomi.verificationsdk.internal;

import android.text.TextUtils;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class Constants {
    public static final String ACCELERATION = "acceleration";
    public static final String ACTION = "action";
    public static final String APP_NAME = "app_name";
    public static final String APP_VERSION_NAME = "app_version";
    public static final String APS = "aps";
    public static final String BAROMETER = "barometer";
    public static final String BATTERY = "battery";
    public static final String BIZACTIVEVERIFY = "activeVerify";
    public static final String BOOT_TIME = "boot_time";
    public static final String BRAND_MODEL = "brand_model";
    public static final String CELL_ID = "cell_id";
    public static final String CHARGING = "charging";
    public static final String CLIENTID = "clientId";
    public static final String COLLECT_SDK_VERSION = "sdk_version";
    public static final int CONFIG_TYPE = 2;
    public static final String CONFIG_URL = "/captcha/v2/config";
    public static final String CUSTOMED_SYSTEM = "customed_system";
    public static final String DEBUG = "debug";
    public static final String DEFAULT_DOMAIN = "https://verify.sec.xiaomi.com";
    public static final int DEFAULT_FREQUENCY = 50;
    public static final int DEFAULT_MAXDURATION = 5000;
    public static final String DEFAULT_STAGING_DOMAIN = "https://infosec-captcha-staging.pt.xiaomi.com";
    public static final String DEVICE_ID = "device_id";
    public static final int DIALOG_CANCEL_TIME = 2000;
    public static final String END_TS = "endTs";
    public static final String ENV = "env";
    public static final String EVENTID = "eventId";
    public static final int EVENTID_EXPIRED = 2;
    public static final String FLAG = "flag";
    public static final String FORCE = "force";
    public static final String FREQUENCY = "frequency";
    public static final String GPS = "gps";
    public static final String ICCID = "iccid";
    public static final String INSTALL_TIME = "install_time";
    public static final String LAST_DOWNLOAD_APP_CONFIG_TIME = "lastDownloadTime";
    public static final String LIGHT = "light";
    public static final String MAGNETIC = "magnetic";
    public static final int MANUAL_CLOSE = 1;
    public static final String MAXDURATION = "maxduration";
    public static final long MILLI_SECONDS_IN_24_HOURS = 86400000;
    public static final int NETWORK_REQUEST_TIMED_OUT = 3000;
    public static final String NEVER_LOCK_SCREEN = "never_lock_screen";
    public static final String NONCE = "nonce";
    public static final String OPERATOR_TYPE = "operator_type";
    public static final String PACKAGE_NAME = "package_name";
    public static final String PASSTIME = "passtime";
    public static final String PHONE_I = "phone_i";
    public static final String PHONE_SIM = "phone_sim";
    public static final String RANDOM_LONG = "r";
    public static final String REDIRECT_URL = "/captcha/status";
    public static final boolean RELEASE = true;
    public static final String ROOT = "root";
    public static final String ROTATION_SPEED = "rotation_speed";
    public static final String SCENE = "scene";
    public static final String SCREEN_BRIGHTNESS = "screen_brightness";
    public static final String SCREEN_OPERATION = "screen_operation";
    public static final int SERVER_ERROR = 500;
    public static final int SERVER_EXCEPTION = 3;
    public static final int SERVER_SUCESS = 0;
    public static final String SIMULATOR = "simulator";
    public static final String START_TS = "startTs";
    public static final String SUBSTRATE = "substrate";
    public static final String SYSTEM_VERSION = "system_version";
    public static final String TALK_BACK = "talkBack";
    public static final String TIMESTAMP = "t";
    public static final String TYPE = "type";
    public static final String UPLAOD_URL = "/captcha/v2/data?";
    public static final String USERID = "uid";
    private static final boolean USE_PREVIEW = new File("/data/system/verifaction_staging_preview").exists();
    public static final String VERIFY_CODE = "code";
    public static final String VERIFY_ERROR_CODE = "errorCode";
    public static final String VERIFY_ERROR_STATUS = "errorStatus";
    public static final int VERIFY_LOAD_JS_EXCEPTION = 95008;
    public static final int VERIFY_OTHER_EXCEPTION = 95009;
    public static final int VERIFY_SUCESS = 0;
    public static final String VERIFY_SUCESS_FLAG = "flag";
    public static final String VERIFY_SUCESS_TOKEN = "token";
    public static final String VERSION = "version";
    public static final String VERSION_VALUE = "2.0";
    public static final String VIRTUALAPP = "virtualapp";
    public static final String VOICEOVER = "voiceover";
    public static final String VPN = "vpn";
    public static final String WIFI_SSID = "wifi_ssid";
    public static final String XPOSED = "xposed";
    public static volatile boolean mIsUsePreview;

    public static String getUrl(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            if (mIsUsePreview || USE_PREVIEW) {
                return DEFAULT_STAGING_DOMAIN + str2;
            }
            return "https://verify.sec.xiaomi.com" + str2;
        }
        return str + str2;
    }
}
