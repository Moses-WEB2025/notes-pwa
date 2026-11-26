package com.xiaomi.passport;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.hasheddeviceidlib.HardwareInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.utils.SubId;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PassportUserEnvironment {
    private static final String DELIMITER = "#";
    private static final int ENV_FACTOR_LENGTH = 6;
    private static final int NETWORK_TYPE_NONE = -1;
    private static final String TAG = "PassportUserEnvironment";
    private static final String UTF8 = "utf-8";

    protected List<String> getPhoneNumbers(Application application) {
        return null;
    }

    public List<String> getSimId() {
        return null;
    }

    public static class Holder {
        private static final PassportUserEnvironment sDefaultInstance;
        private static PassportUserEnvironment sInstance;

        static {
            PassportUserEnvironment passportUserEnvironment = new PassportUserEnvironment();
            sDefaultInstance = passportUserEnvironment;
            sInstance = passportUserEnvironment;
        }

        public static void setInstance(PassportUserEnvironment passportUserEnvironment) {
            if (passportUserEnvironment == null) {
                throw new IllegalArgumentException("PassportUserEnvironment instance cannot be null!");
            }
            sInstance = passportUserEnvironment;
        }

        public static PassportUserEnvironment getInstance() {
            return sInstance;
        }

        public static PassportUserEnvironment getDefaultInstance() {
            return sDefaultInstance;
        }
    }

    public enum TelePhonyInfo {
        SUBSCRIBE_ID("getSubscriberIdForSlot"),
        SERIAL_NUMBER("getSimSerialNumberForSlot"),
        OPERATOR("getSimOperatorForSlot"),
        DEVICE_ID_LIST("getDeviceIdList");

        String methodToGetValue;

        TelePhonyInfo(String str) {
            this.methodToGetValue = str;
        }
    }

    public String getSSID() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return PrivacyDataMaster.forceGet(applicationContext, PrivacyDataType.SSID, new String[0]);
    }

    public List<String> getConfiguredSSIDs() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        String strForceGet = PrivacyDataMaster.forceGet(applicationContext, PrivacyDataType.CONFIGURED_SSIDS, new String[0]);
        if (TextUtils.isEmpty(strForceGet)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, strForceGet.split(StringUtils.LF));
        return arrayList;
    }

    public List<String> getConfiguredSSIDLimit(int i) {
        List<String> configuredSSIDs = getConfiguredSSIDs();
        return (configuredSSIDs == null || configuredSSIDs.size() <= i) ? configuredSSIDs : configuredSSIDs.subList(0, i);
    }

    @Deprecated
    public String getBluetoothId() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return PrivacyDataMaster.forceGet(applicationContext, PrivacyDataType.BLUETOOTH_ADDRESS, new String[0]);
    }

    @Deprecated
    public String getNetworkOperator() {
        String strForceGet;
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        int phoneCount = PhoneInfo.get(applicationContext).getPhoneCount();
        for (int i = 0; i < phoneCount; i++) {
            int i2 = SubId.get(applicationContext, i);
            if (i2 != -1 && (strForceGet = PrivacyDataMaster.forceGet(applicationContext, PrivacyDataType.NETWORK_MCCMNC, String.valueOf(i2))) != null) {
                return strForceGet;
            }
        }
        return null;
    }

    private String getBSSID(Application application) {
        if (application == null) {
            return null;
        }
        return PrivacyDataMaster.forceGet(application, PrivacyDataType.BSSID, new String[0]);
    }

    private String getBluetoothMacAddress(Application application) {
        return HardwareInfo.getBluetoothMacAddress(application);
    }

    private int getNetWorkType(Application application) {
        if (application == null) {
            return -1;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) application.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo.getType();
            }
        } catch (SecurityException e) {
            AccountLogger.log(TAG, "failed to getNetWorkType with SecurityException " + e.getMessage());
        }
        return -1;
    }

    private String getAndroidId(Application application) {
        if (application == null) {
            return null;
        }
        return PrivacyDataMaster.get(application, PrivacyDataType.ANDROID_ID, new String[0]);
    }

    protected String getUserSetDeviceName(Application application) {
        return PrivacyDataMaster.forceGet(application, PrivacyDataType.BLUETOOTH_NAME, new String[0]);
    }

    protected List<String> getSubscriberIds(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.SUBSCRIBE_ID);
    }

    protected List<String> getSimSerialNumbers(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.SERIAL_NUMBER);
    }

    protected List<String> getSimOperators(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.OPERATOR);
    }

    protected List<String> getDeviceIdList(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.DEVICE_ID_LIST);
    }

    private List<String> getTelePhonyInfoFromNoMiui(Application application, TelePhonyInfo telePhonyInfo) {
        String strForceGet;
        if (application == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (telePhonyInfo == TelePhonyInfo.DEVICE_ID_LIST) {
            String str = PrivacyDataMaster.get(application, PrivacyDataType.DEVICE_ID, new String[0]);
            if (str != null) {
                arrayList.add(str);
            }
        } else {
            int phoneCount = PhoneInfo.get(application).getPhoneCount();
            for (int i = 0; i < phoneCount; i++) {
                int i2 = SubId.get(application, i);
                if (i2 != -1) {
                    int i3 = AnonymousClass1.$SwitchMap$com$xiaomi$passport$PassportUserEnvironment$TelePhonyInfo[telePhonyInfo.ordinal()];
                    if (i3 == 1) {
                        strForceGet = PrivacyDataMaster.forceGet(application, PrivacyDataType.MCCMNC, String.valueOf(i2));
                    } else if (i3 == 2) {
                        strForceGet = PrivacyDataMaster.forceGet(application, PrivacyDataType.ICCID, String.valueOf(i2));
                    } else if (i3 == 3) {
                        strForceGet = PrivacyDataMaster.forceGet(application, PrivacyDataType.IMSI, String.valueOf(i2));
                    } else {
                        throw new IllegalStateException("not here");
                    }
                    if (strForceGet != null) {
                        arrayList.add(strForceGet);
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.PassportUserEnvironment$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$passport$PassportUserEnvironment$TelePhonyInfo;

        static {
            int[] iArr = new int[TelePhonyInfo.values().length];
            $SwitchMap$com$xiaomi$passport$PassportUserEnvironment$TelePhonyInfo = iArr;
            try {
                iArr[TelePhonyInfo.OPERATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$PassportUserEnvironment$TelePhonyInfo[TelePhonyInfo.SERIAL_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$PassportUserEnvironment$TelePhonyInfo[TelePhonyInfo.SUBSCRIBE_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private LinkedList<Object> getAllLinkedEnvInfos(Application application) {
        String strHash = hash(getSSID());
        String strHash2 = hash(getBSSID(application));
        List<String> listHash = hash(getConfiguredSSIDLimit(50));
        String strBase64 = base64(String.valueOf(getNetWorkType(application)));
        String strBase65 = base64(Build.MODEL);
        String strBase66 = base64(Build.SERIAL);
        String strHash3 = hash(getAndroidId(application));
        List<String> listHash2 = hash(getDeviceIdList(application));
        String strHash4 = hash(getBluetoothMacAddress(application));
        String strHash5 = hash(HardwareInfo.FAKE_MAC_ADDRESS);
        List<String> listBase64 = base64(getSubscriberIds(application));
        List<String> listBase65 = base64(getSimSerialNumbers(application));
        List<String> listBase66 = base64(getPhoneNumbers(application));
        List<String> listBase67 = base64(getSimOperators(application));
        List<String> listHash3 = hash(getSimId());
        String strBase67 = base64(getUserSetDeviceName(application));
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add(strHash);
        linkedList.add(strHash2);
        linkedList.add(listHash);
        linkedList.add(strBase64);
        linkedList.add(strBase65);
        linkedList.add(strBase66);
        linkedList.add(strHash3);
        linkedList.add(listHash2);
        linkedList.add(strHash4);
        linkedList.add(strHash5);
        linkedList.add(listBase64);
        linkedList.add(listBase65);
        linkedList.add(listBase66);
        linkedList.add(listBase67);
        linkedList.add(listHash3);
        linkedList.add(strBase67);
        return linkedList;
    }

    public String[] getEnvInfoArray(Application application) {
        String strJoinEnvParam;
        LinkedList<Object> allLinkedEnvInfos = getAllLinkedEnvInfos(application);
        ArrayList arrayList = new ArrayList();
        for (Object obj : allLinkedEnvInfos) {
            if (obj == null) {
                strJoinEnvParam = "";
            } else if (obj instanceof List) {
                strJoinEnvParam = joinEnvParam((List) obj);
            } else if (obj instanceof String) {
                strJoinEnvParam = (String) obj;
            } else {
                throw new IllegalStateException("not here");
            }
            arrayList.add(strJoinEnvParam);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static List<String> blurLocationInfo(double d, double d2) {
        long jRound = Math.round(d * 10.0d) * 10;
        long jRound2 = Math.round(d2 * 10.0d) * 10;
        ArrayList arrayList = new ArrayList(8);
        long j = jRound - 10;
        arrayList.add(String.valueOf(j));
        long j2 = jRound2 - 10;
        arrayList.add(String.valueOf(j2));
        arrayList.add(String.valueOf(j));
        arrayList.add(String.valueOf(jRound2));
        arrayList.add(String.valueOf(jRound));
        arrayList.add(String.valueOf(jRound2));
        arrayList.add(String.valueOf(jRound));
        arrayList.add(String.valueOf(j2));
        return arrayList;
    }

    public static String hashEnvParamString(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return "";
        }
        return CloudCoder.hashAndJoin("#", arrayList, 6);
    }

    private static String joinEnvParam(List list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        return TextUtils.join("#", list);
    }

    private List<String> hash(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(hash(it.next()));
        }
        return arrayList;
    }

    private String hash(String str) {
        return hash(str, 6);
    }

    private String hash(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String strHash4SHA1 = CloudCoder.hash4SHA1(str);
        return (i <= 0 || i > strHash4SHA1.length()) ? strHash4SHA1 : strHash4SHA1.substring(0, i);
    }

    private List<String> base64(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(base64(it.next()));
        }
        return arrayList;
    }

    private String base64(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Base64.encodeToString(str.getBytes("utf-8"), 10);
        } catch (UnsupportedEncodingException e) {
            AccountLogger.log(TAG, "base64 failed: ", e);
            return null;
        }
    }
}
