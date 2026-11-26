package com.xiaomi.mirror;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class RemoteDeviceInfo implements Parcelable {
    public static final int CONNECT_TYPE_ADVANCED = 2;
    public static final int CONNECT_TYPE_ADVANCED_USER_AWARE = 6;
    public static final int CONNECT_TYPE_BASIC = 1;
    public static final int CONNECT_TYPE_NONE = 0;
    public static final int CONNECT_TYPE_USER_AWARE = 4;
    public static final Parcelable.Creator<RemoteDeviceInfo> CREATOR = new Parcelable.Creator<RemoteDeviceInfo>() { // from class: com.xiaomi.mirror.RemoteDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteDeviceInfo createFromParcel(Parcel parcel) {
            return new RemoteDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteDeviceInfo[] newArray(int i) {
            return new RemoteDeviceInfo[i];
        }
    };
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_BT_MAC = "bt_mac";
    public static final String KEY_CAPABILITIES = "capabilities";
    public static final String KEY_CONNECT_TYPE = "connect_type";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_DISPLAY_NAME = "display_name";
    public static final String KEY_ID = "id";
    public static final String KEY_IS_LYRA = "is_lyra";
    public static final String KEY_IS_SHOW_MIRROR = "is_show_mirror";
    public static final String KEY_IS_SUPPORT_SEND_APP = "is_support_send_app";
    public static final String KEY_LAST_CONNECT_TIMESTAMP = "last_connect_timestamp";
    public static final String KEY_MANUFACTURER = "manufacturer";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_SN = "sn";
    public static final String MANUFACTURER_OTHER = "other";
    public static final String MANUFACTURER_XIAOMI = "xiaomi";
    public static final String PLATFORM_ANDROID_PAD = "AndroidPad";
    public static final String PLATFORM_ANDROID_PHONE = "AndroidPhone";
    public static final String PLATFORM_WINDOWS = "Windows";
    public Bundle mBundle;

    public RemoteDeviceInfo() {
        Bundle bundle = new Bundle();
        this.mBundle = bundle;
        bundle.putString("platform", PLATFORM_ANDROID_PHONE);
        this.mBundle.putInt(KEY_CONNECT_TYPE, 0);
    }

    public RemoteDeviceInfo(Parcel parcel) {
        this.mBundle = new Bundle();
        this.mBundle = parcel.readBundle(RemoteDeviceInfo.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.mBundle.getString(KEY_ADDRESS);
    }

    public long getAppVersion() {
        return this.mBundle.getLong("app_version");
    }

    public String getBtMac() {
        return this.mBundle.getString(KEY_BT_MAC);
    }

    public Bundle getBundle() {
        return new Bundle(this.mBundle);
    }

    public HashMap<String, String> getCapabilities() {
        return (HashMap) this.mBundle.getSerializable(KEY_CAPABILITIES);
    }

    public int getConnectType() {
        return this.mBundle.getInt(KEY_CONNECT_TYPE);
    }

    public String getDeviceId() {
        return this.mBundle.getString("device_id");
    }

    public String getDisplayName() {
        return this.mBundle.getString("display_name");
    }

    public String getId() {
        return this.mBundle.getString("id");
    }

    public long getLastConnectTimestamp() {
        return this.mBundle.getLong(KEY_LAST_CONNECT_TIMESTAMP);
    }

    public String getManufacturer() {
        return this.mBundle.getString(KEY_MANUFACTURER);
    }

    public Bundle getMutableBundle() {
        return this.mBundle;
    }

    public String getPlatform() {
        return this.mBundle.getString("platform");
    }

    public String getSn() {
        return this.mBundle.getString(KEY_SN);
    }

    public boolean isLyra() {
        return this.mBundle.getBoolean(KEY_IS_LYRA, false);
    }

    public boolean isShowMirror() {
        return this.mBundle.getBoolean(KEY_IS_SHOW_MIRROR);
    }

    public boolean isSupportSendApp() {
        return this.mBundle.getBoolean(KEY_IS_SUPPORT_SEND_APP);
    }

    public String toString() {
        return "RemoteDeviceInfo{mBundle=" + this.mBundle + CoreConstants.CURLY_RIGHT;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }
}
