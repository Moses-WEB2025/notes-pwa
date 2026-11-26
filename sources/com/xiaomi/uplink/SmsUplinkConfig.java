package com.xiaomi.uplink;

/* JADX INFO: loaded from: classes4.dex */
public class SmsUplinkConfig {
    public static final String ACTIVATION_SMS_PREFIX = "MO";
    public static final int MAX_RESEND_SMS_COUNT = 2;
    public static final String SCENE_LOGIN = "login";
    public static final String SMS_GW_V2 = "sms_gw_config_v2";
    public static final String SMS_UPLINK_ID_TYPE = "PH";
    public static final String USER_TYPE = "PH";

    public enum UplinkScene {
        LOGIN("LG"),
        REGISTER("RG");

        String sceneId;

        UplinkScene(String str) {
            this.sceneId = str;
        }

        public String getSceneId() {
            return this.sceneId;
        }
    }
}
