package com.xiaomi.account.privacy_data.master;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.xiaomi.account.privacy_data.android_id.AndroidIdGetter;
import com.xiaomi.account.privacy_data.bluetooth_address.BluetoothAddressGetter;
import com.xiaomi.account.privacy_data.bluetooth_name.BluetoothNameGetter;
import com.xiaomi.account.privacy_data.bssid.BSSIDGetter;
import com.xiaomi.account.privacy_data.configured_ssids.ConfiguredSSIDsGetter;
import com.xiaomi.account.privacy_data.device_id.DeviceIdGetter;
import com.xiaomi.account.privacy_data.iccid.ICCIDGetter;
import com.xiaomi.account.privacy_data.imsi.IMSIGetter;
import com.xiaomi.account.privacy_data.lib.IPrivacyDataGetter;
import com.xiaomi.account.privacy_data.lib.PrivacyDataException;
import com.xiaomi.account.privacy_data.line_1_number.Line1NumberGetter;
import com.xiaomi.account.privacy_data.mac_address.MacAddressGetter;
import com.xiaomi.account.privacy_data.mccmnc.MCCMNCGetter;
import com.xiaomi.account.privacy_data.miui_device_id.MiuiDeviceIdGetter;
import com.xiaomi.account.privacy_data.mobile_data_enable.MobileDataEnableGetter;
import com.xiaomi.account.privacy_data.network_mccmnc.NetworkMCCMNCGetter;
import com.xiaomi.account.privacy_data.oaid.OAIDGetter;
import com.xiaomi.account.privacy_data.sim_in_service.SimInServiceGetter;
import com.xiaomi.account.privacy_data.ssid.SSIDGetter;
import com.xiaomi.account.privacy_data.sub_id.SubIdGetter;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyDataMaster {
    private static final String SP_FILE_NAME = "com.xiaomi.account.privacy_data.master.data";
    private static final String TAG = "PrivacyDataMaster";
    private static final Map<String, IPrivacyDataGetter> sPrivacyDataGetterMap;

    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(SP_FILE_NAME, 0);
    }

    static {
        HashMap map = new HashMap();
        sPrivacyDataGetterMap = map;
        try {
            map.put(PrivacyDataType.OAID.name, new OAIDGetter());
        } catch (NoClassDefFoundError unused) {
            Log.e(TAG, "OAID privacy-data-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.ANDROID_ID.name, new AndroidIdGetter());
        } catch (NoClassDefFoundError unused2) {
            Log.e(TAG, "ANDROID_ID privacy-data-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.DEVICE_ID.name, new DeviceIdGetter());
        } catch (NoClassDefFoundError unused3) {
            Log.e(TAG, "device-id privacy-data-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.MIUI_DEVICE_ID.name, new MiuiDeviceIdGetter());
        } catch (NoClassDefFoundError unused4) {
            Log.e(TAG, "miui-device-id privacy-data-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.BLUETOOTH_NAME.name, new BluetoothNameGetter());
        } catch (NoClassDefFoundError unused5) {
            Log.e(TAG, "bluetooth-data bluetooth-name-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.BLUETOOTH_ADDRESS.name, new BluetoothAddressGetter());
        } catch (NoClassDefFoundError unused6) {
            Log.e(TAG, "bluetooth-data bluetooth-address-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.MAC_ADDRESS.name, new MacAddressGetter());
        } catch (NoClassDefFoundError unused7) {
            Log.e(TAG, "bluetooth-data mac-address-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.SSID.name, new SSIDGetter());
        } catch (NoClassDefFoundError unused8) {
            Log.e(TAG, "bluetooth-data ssid-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.BSSID.name, new BSSIDGetter());
        } catch (NoClassDefFoundError unused9) {
            Log.e(TAG, "bluetooth-data bssid-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.CONFIGURED_SSIDS.name, new ConfiguredSSIDsGetter());
        } catch (NoClassDefFoundError unused10) {
            Log.e(TAG, "bluetooth-data configured-ssids-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.ICCID.name, new ICCIDGetter());
        } catch (NoClassDefFoundError unused11) {
            Log.e(TAG, "bluetooth-data iccid-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.IMSI.name, new IMSIGetter());
        } catch (NoClassDefFoundError unused12) {
            Log.e(TAG, "bluetooth-data imsi-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.LINE_1_NUMBER.name, new Line1NumberGetter());
        } catch (NoClassDefFoundError unused13) {
            Log.e(TAG, "bluetooth-data line-1-number-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.MCCMNC.name, new MCCMNCGetter());
        } catch (NoClassDefFoundError unused14) {
            Log.e(TAG, "bluetooth-data mccmnc-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.NETWORK_MCCMNC.name, new NetworkMCCMNCGetter());
        } catch (NoClassDefFoundError unused15) {
            Log.e(TAG, "bluetooth-data network-mccmnc-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.SUB_ID.name, new SubIdGetter());
        } catch (NoClassDefFoundError unused16) {
            Log.e(TAG, "bluetooth-data sub-id-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.MOBILE_DATA_ENABLE.name, new MobileDataEnableGetter());
        } catch (NoClassDefFoundError unused17) {
            Log.e(TAG, "bluetooth-data mobile-data-enable-getter not defined");
        }
        try {
            sPrivacyDataGetterMap.put(PrivacyDataType.SIM_IN_SERVICE.name, new SimInServiceGetter());
        } catch (NoClassDefFoundError unused18) {
            Log.e(TAG, "sim-in-service privacy-data-getter not defined");
        }
    }

    public static String forceGet(Context context, PrivacyDataType privacyDataType, String... strArr) {
        return forceGet(context, privacyDataType.name, strArr);
    }

    public static String forceGet(Context context, String str, String... strArr) {
        IPrivacyDataGetter iPrivacyDataGetter = sPrivacyDataGetterMap.get(str);
        if (iPrivacyDataGetter == null) {
            Log.e(TAG, "no privacy data getter for type: " + str);
            return null;
        }
        try {
            String str2 = iPrivacyDataGetter.get(context, strArr);
            getSharedPrefs(context).edit().putString(str, str2).commit();
            return str2;
        } catch (PrivacyDataException e) {
            Log.e(TAG, "get privacy data failed for type: " + str, e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "unknown error: get privacy data failed for type: " + str, e2);
            return null;
        }
    }

    public static String get(Context context, PrivacyDataType privacyDataType, String... strArr) {
        return get(context, privacyDataType.name, strArr);
    }

    public static String get(Context context, String str, String... strArr) {
        SharedPreferences sharedPrefs = getSharedPrefs(context);
        String string = sharedPrefs.getString(str, null);
        if (string != null) {
            Log.e(TAG, "read privacy data from local cache for type: " + str);
            return string;
        }
        String strForceGet = forceGet(context, str, strArr);
        if (strForceGet != null) {
            sharedPrefs.edit().putString(str, strForceGet).commit();
            Log.e(TAG, "get privacy data success and cache for type: " + str);
        }
        return strForceGet;
    }

    public static void setPrivacyDataGetter(PrivacyDataType privacyDataType, IPrivacyDataGetter iPrivacyDataGetter) {
        setPrivacyDataGetter(privacyDataType.name, iPrivacyDataGetter);
    }

    public static void setPrivacyDataGetter(String str, IPrivacyDataGetter iPrivacyDataGetter) {
        Log.e(TAG, "set privacy data getter for type: " + str);
        sPrivacyDataGetterMap.put(str, iPrivacyDataGetter);
    }
}
