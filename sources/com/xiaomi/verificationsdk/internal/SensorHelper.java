package com.xiaomi.verificationsdk.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.account.passportsdk.account_sso.BuildConfig;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.SecurityDeviceSignManager;
import com.xiaomi.passport.compat.ContextCompat;
import com.xiaomi.verificationsdk.VerificationManager;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import miui.cloud.CloudPushConstants;
import org.apache.commons.lang3.SystemProperties;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SensorHelper implements SensorEventListener {
    private static HandlerThread sensorThread;
    private JSONArray mAccSpeedJsonData;
    private JSONArray mBarometerJsonData;
    private BatteryReceiver mBatteryReceiver;
    private String mCollectedData;
    private Context mContext;
    private int mCurrentBattery;
    private Handler mHandler;
    private int mIsCharging;
    private JSONArray mLightJsonData;
    private JSONArray mMagneticJsonData;
    private int mSamplingPeriodUs;
    private SensorManager mSensorManager;
    private long mStartCollectedDataTimestamp;
    private TelephonyManager mTelephonyManager;
    private final String TAG = "SensorHelper";
    private volatile boolean startFlag = false;
    private volatile boolean mReceiverTag = false;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public String getCollectedData() {
        return this.mCollectedData;
    }

    public void setCollectedData(String str) {
        this.mCollectedData = str;
    }

    public void clearCollectedData() {
        this.mCollectedData = "";
        this.mAccSpeedJsonData = new JSONArray();
        this.mMagneticJsonData = new JSONArray();
        this.mLightJsonData = new JSONArray();
        this.mBarometerJsonData = new JSONArray();
    }

    public void asyncSetCollectedData() {
        if (this.mStartCollectedDataTimestamp == 0) {
            AccountLogger.log("SensorHelper", "Collected Data not start");
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        stop();
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.internal.SensorHelper.1
            @Override // java.lang.Runnable
            public void run() {
                SensorHelper sensorHelper = SensorHelper.this;
                sensorHelper.setCollectedData(sensorHelper.getData(sensorHelper.mStartCollectedDataTimestamp, jCurrentTimeMillis));
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("sensor");
        sensorThread = handlerThread;
        handlerThread.start();
    }

    public SensorHelper(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("SensorHelper init : context  should not be null");
        }
        this.mContext = context;
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        this.mHandler = new Handler(sensorThread.getLooper());
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public void uploadData(final String str, final String str2, final String str3, final Boolean bool, final String str4, final String str5, final Boolean bool2, final VerificationManager.Verify2Callback verify2Callback) {
        ScoreManager.clearScore();
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.internal.SensorHelper.2
            @Override // java.lang.Runnable
            public void run() {
                String string;
                try {
                    if (verify2Callback == null) {
                        throw new IllegalArgumentException("uploadData :verifyCallback not be null");
                    }
                    EnvEncryptUtils.EncryptResult encryptResultEncrypt = EnvEncryptUtils.encrypt(str);
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2).append(str3).append(encryptResultEncrypt.encryptedKey).append(encryptResultEncrypt.content);
                    try {
                        Bundle bundle = SecurityDeviceSignManager.sign(SensorHelper.this.mContext, sb.toString(), null).get(EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS, TimeUnit.MILLISECONDS);
                        if (bundle == null) {
                            string = "";
                        } else if (bundle.getBoolean("booleanResult")) {
                            string = bundle.getString("userData");
                        } else {
                            int i = bundle.getInt("errorCode");
                            String string2 = bundle.getString("errorMessage");
                            AccountLogger.log("SensorHelper", "error code: " + i);
                            AccountLogger.log("SensorHelper", "error msg: " + string2);
                            string = "";
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                    } catch (TimeoutException e3) {
                        e3.printStackTrace();
                    }
                    EasyMap easyMap = new EasyMap();
                    if (bool.booleanValue()) {
                        easyMap.easyPut(CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "");
                        easyMap.easyPut(DateTokenConverter.CONVERTER_KEY, "");
                    } else {
                        easyMap.easyPut(CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, encryptResultEncrypt.encryptedKey);
                        easyMap.easyPut(DateTokenConverter.CONVERTER_KEY, encryptResultEncrypt.content);
                    }
                    if (!TextUtils.isEmpty(string)) {
                        easyMap.easyPut(Constants.TIMESTAMP, string);
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        easyMap.easyPut(HtmlParser.LinkCardElement.TAG, str3);
                    }
                    EasyMap easyMapEasyPut = new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidVerifySDK/5.3.0.release.94");
                    String localeString = str4;
                    if (TextUtils.isEmpty(localeString)) {
                        localeString = CommonUtils.getLocaleString(Locale.getDefault());
                    }
                    SimpleRequest.StringContent stringContentPostAsString = SimpleRequest.postAsString(Constants.getUrl(str5, Constants.UPLAOD_URL) + "k=" + str2 + "&locale=" + localeString + "&errorAction=" + bool2, (Map<String, String>) easyMap, (Map<String, String>) null, (Map<String, String>) easyMapEasyPut, true, (Integer) 3000);
                    if (stringContentPostAsString == null) {
                        verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_NETWORK_EXCEPTION.getCode(), "uploadData:network exception", ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_NETWORK_EXCEPTION)));
                        return;
                    }
                    JSONObject jSONObject = new JSONObject(stringContentPostAsString.getBody());
                    int iOptInt = jSONObject.optInt("code");
                    String strOptString = jSONObject.optString("msg");
                    if (iOptInt != 0) {
                        if (iOptInt == 500) {
                            verify2Callback.onVerifySucess(new VerifyResult.Builder().token(EnvEncryptUtils.getUUID()).build());
                            return;
                        } else {
                            verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(iOptInt, "uploadData:" + strOptString, ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_SERVER)));
                            return;
                        }
                    }
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    boolean zOptBoolean = jSONObject2.optBoolean("result");
                    String strOptString2 = jSONObject2.optString(Constants.VERIFY_SUCESS_TOKEN);
                    String strOptString3 = jSONObject2.optString("url");
                    JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject(CallMethod.ARG_EXTRA_STRING);
                    if (jSONObjectOptJSONObject != null) {
                        ScoreManager.setScore(jSONObjectOptJSONObject.toString());
                    }
                    if (!TextUtils.isEmpty(strOptString3)) {
                        verify2Callback.onShowWebView(strOptString3);
                    } else if (zOptBoolean) {
                        verify2Callback.onVerifySucess(new VerifyResult.Builder().token(strOptString2).scoreData(ScoreManager.getScore()).build());
                    } else {
                        verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_HUMANCOMPUTER_VERIFICATION_FAILED.getCode(), "uploadData:human computer verification failed", ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_HUMANCOMPUTER_VERIFICATION_FAILED)));
                    }
                } catch (AccessDeniedException e4) {
                    AccountLogger.log("SensorHelper", "", e4);
                    verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_ACCESSDENIED_EXCEPTION.getCode(), "uploadData:" + e4.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_ACCESSDENIED_EXCEPTION)));
                } catch (AuthenticationFailureException e5) {
                    AccountLogger.log("SensorHelper", "", e5);
                    verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_AUTHENTICATIONFAILURE_EXCEPTION.getCode(), "uploadData:" + e5.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_AUTHENTICATIONFAILURE_EXCEPTION)));
                } catch (EnvEncryptUtils.EncryptException e6) {
                    AccountLogger.log("SensorHelper", "", e6);
                    verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_ENCRYPT_EXCEPTION.getCode(), "uploadData:" + e6.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_ENCRYPT_EXCEPTION)));
                } catch (IOException e7) {
                    SensorHelper.this.handleIOException(verify2Callback, e7);
                } catch (JSONException e8) {
                    AccountLogger.log("SensorHelper", "fail to parse JSONObject", e8);
                    verify2Callback.onVerifyFail(SensorHelper.getErrorMessage(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION.getCode(), "uploadData:" + e8.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION)));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIOException(VerificationManager.Verify2Callback verify2Callback, IOException iOException) {
        AccountLogger.log("SensorHelper", "", iOException);
        if (iOException instanceof ConnectException) {
            verify2Callback.onVerifyFail(getErrorMessage(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), "uploadData:" + iOException.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION)));
            return;
        }
        if (iOException instanceof SocketTimeoutException) {
            verify2Callback.onVerifyFail(getErrorMessage(ErrorInfo.ErrorCode.ERROR_SOCKET_TIMEOUT_EXCEPTION.getCode(), "uploadData:" + iOException.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_SOCKET_TIMEOUT_EXCEPTION)));
        } else if (iOException instanceof ConnectTimeoutException) {
            verify2Callback.onVerifyFail(getErrorMessage(ErrorInfo.ErrorCode.ERROR_CONNECT_TIMEOUT_EXCEPTION.getCode(), "uploadData:" + iOException.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_TIMEOUT_EXCEPTION)));
        } else {
            verify2Callback.onVerifyFail(getErrorMessage(ErrorInfo.ErrorCode.ERROR_IO_EXCEPTION.getCode(), "uploadData:" + iOException.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_IO_EXCEPTION)));
        }
    }

    public void collectSensorData(int i, int i2) {
        this.mSamplingPeriodUs = i;
        final long jCurrentTimeMillis = System.currentTimeMillis();
        start();
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.verificationsdk.internal.SensorHelper.3
            @Override // java.lang.Runnable
            public void run() {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                SensorHelper.this.stop();
                SensorHelper.this.setCollectedData(SensorHelper.this.getData(jCurrentTimeMillis, jCurrentTimeMillis2));
            }
        }, i2);
    }

    public String getData(long j, long j2) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject.put("type", 2);
            jSONObject.put(Constants.START_TS, j);
            jSONObject.put(Constants.END_TS, j2);
            jSONObject2.put(Constants.BATTERY, this.mCurrentBattery);
            jSONObject2.put("device_id", getDeviceId());
            jSONObject2.put("app_version", getAppVersionName());
            jSONObject2.put(Constants.COLLECT_SDK_VERSION, BuildConfig.VERSION_NAME);
            jSONObject2.put(Constants.VPN, getVpn());
            jSONObject2.put(Constants.BRAND_MODEL, getBrandModel());
            jSONObject2.put(Constants.SYSTEM_VERSION, getSystemVersion());
            jSONObject2.put(Constants.CUSTOMED_SYSTEM, getCustomedSystem());
            jSONObject2.put(Constants.SCREEN_BRIGHTNESS, getScreenBrightness());
            jSONObject2.put("debug", getEnableAdbState());
            jSONObject2.put(Constants.SIMULATOR, getEmulatorState());
            jSONObject2.put(Constants.CHARGING, this.mIsCharging);
            jSONObject2.put(Constants.NEVER_LOCK_SCREEN, getNeverLockScreenState());
            jSONObject2.put(Constants.APS, new JSONArray());
            jSONObject2.put(Constants.XPOSED, getXposedHookState());
            jSONObject2.put(Constants.SUBSTRATE, getSubstrateHookState());
            jSONObject2.put(Constants.ROOT, getRootState());
            jSONObject2.put(Constants.BOOT_TIME, getBootTime());
            jSONObject2.put(Constants.INSTALL_TIME, getApkInstallTime(this.mContext));
            jSONObject2.put("package_name", getPackageName());
            jSONObject2.put(Constants.APP_NAME, getAppName(this.mContext, getPackageName()));
            fillAppVirtualStateIfNeeded(jSONObject2);
            jSONObject.put("env", jSONObject2);
            if (this.mAccSpeedJsonData == null) {
                this.mAccSpeedJsonData = new JSONArray();
            }
            jSONObject3.put(Constants.ACCELERATION, this.mAccSpeedJsonData);
            if (this.mMagneticJsonData == null) {
                this.mMagneticJsonData = new JSONArray();
            }
            jSONObject3.put(Constants.MAGNETIC, this.mMagneticJsonData);
            if (this.mLightJsonData == null) {
                this.mLightJsonData = new JSONArray();
            }
            jSONObject3.put(Constants.LIGHT, this.mLightJsonData);
            if (this.mBarometerJsonData == null) {
                this.mBarometerJsonData = new JSONArray();
            }
            jSONObject3.put(Constants.BAROMETER, this.mBarometerJsonData);
            jSONObject.put("action", jSONObject3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private String getDeviceId() {
        return AccountDeviceIdManager.getInstance().getDeviceId();
    }

    private String getAppVersionName() {
        Exception e;
        String str;
        try {
            str = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
            if (str != null) {
                try {
                    if (str.length() > 0) {
                        return str;
                    }
                } catch (Exception e2) {
                    e = e2;
                    AccountLogger.log("VersionInfo", "Exception", e);
                    return str;
                }
            }
            return "";
        } catch (Exception e3) {
            e = e3;
            str = "";
        }
    }

    private String getPackageName() {
        return this.mContext.getPackageName();
    }

    private String getAppName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private long getApkInstallTime(Context context) {
        try {
            return new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir).lastModified();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private long getBootTime() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

    private int getXposedHookState() {
        try {
            this.mContext.getPackageManager().getPackageInfo("de.robv.android.xposed.installer", 0);
            AccountLogger.log("SensorHelper", "Xposed found on the system.");
            return 1;
        } catch (PackageManager.NameNotFoundException unused) {
            AccountLogger.log("SensorHelper", "Xposed NOT found on the system.");
            return 0;
        }
    }

    private int getSubstrateHookState() {
        try {
            this.mContext.getPackageManager().getPackageInfo("com.saurik.substrate", 0);
            AccountLogger.log("SensorHelper", "Substrate found on the system.");
            return 1;
        } catch (PackageManager.NameNotFoundException unused) {
            AccountLogger.log("SensorHelper", "Substrate NOT found on the system.");
            return 0;
        }
    }

    private int getRootState() {
        return CheckRoot.isDeviceRooted() ? 1 : 0;
    }

    private void fillAppVirtualStateIfNeeded(JSONObject jSONObject) throws JSONException {
        try {
            jSONObject.put(Constants.VIRTUALAPP, CheckVirtual.isRunInVirtual() ? 1 : 0);
        } catch (UnknownValueException e) {
            e.printStackTrace();
        }
    }

    private int getVpn() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return 0;
            }
            for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
                if (networkInterface.isUp() && networkInterface.getInterfaceAddresses().size() != 0 && ("tun0".equals(networkInterface.getName()) || "ppp0".equals(networkInterface.getName()))) {
                    return 1;
                }
            }
            return 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    private String getBrandModel() {
        return Build.MODEL;
    }

    private String getSystemVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    private String getCustomedSystem() {
        return Build.FINGERPRINT;
    }

    private int getScreenBrightness() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), Constants.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getEnableAdbState() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "adb_enabled", 0) > 0 ? 1 : 0;
    }

    private int getNeverLockScreenState() {
        int i;
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "screen_off_timeout");
        } catch (Exception unused) {
            i = 0;
        }
        return i == Integer.MAX_VALUE ? 1 : 0;
    }

    private int getEmulatorState() {
        return ((TelephonyManager) this.mContext.getSystemService("phone")).getNetworkOperatorName().toLowerCase().equals(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME) ? 1 : 0;
    }

    private void start() {
        this.startFlag = true;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        if (this.mReceiverTag) {
            return;
        }
        BatteryReceiver batteryReceiver = new BatteryReceiver();
        this.mBatteryReceiver = batteryReceiver;
        ContextCompat.registerReceiver(this.mContext, batteryReceiver, intentFilter, true);
        this.mReceiverTag = true;
        this.mStartCollectedDataTimestamp = System.currentTimeMillis();
    }

    public void stop() {
        if (this.startFlag) {
            this.startFlag = false;
            unregisterListener();
        }
    }

    private void registerListener(int i) {
        Sensor defaultSensor = this.mSensorManager.getDefaultSensor(i);
        if (defaultSensor == null) {
            return;
        }
        this.mSensorManager.registerListener(this, defaultSensor, this.mSamplingPeriodUs * 1000);
    }

    private synchronized void unregisterListener() {
        try {
            this.mSensorManager.unregisterListener(this);
            if (this.mReceiverTag) {
                this.mContext.unregisterReceiver(this.mBatteryReceiver);
                this.mReceiverTag = false;
            }
        } catch (Exception e) {
            AccountLogger.log("SensorHelper", e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recoredData(SensorData sensorData) {
        if (this.startFlag) {
            if (this.mAccSpeedJsonData == null) {
                this.mAccSpeedJsonData = new JSONArray();
            }
            if (this.mMagneticJsonData == null) {
                this.mMagneticJsonData = new JSONArray();
            }
            if (this.mLightJsonData == null) {
                this.mLightJsonData = new JSONArray();
            }
            if (this.mBarometerJsonData == null) {
                this.mBarometerJsonData = new JSONArray();
            }
            try {
                int i = sensorData.sensorType;
                if (i == 1) {
                    this.mAccSpeedJsonData.put(sensorData.getJson());
                    return;
                }
                if (i == 2) {
                    this.mMagneticJsonData.put(sensorData.getJson());
                } else if (i == 5) {
                    this.mLightJsonData.put(sensorData.getJson());
                } else {
                    if (i != 6) {
                        return;
                    }
                    this.mBarometerJsonData.put(sensorData.getJson());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(final SensorEvent sensorEvent) {
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.internal.SensorHelper.4
            @Override // java.lang.Runnable
            public void run() {
                SensorHelper.this.recoredData(SensorHelper.this.new SensorData(sensorEvent));
            }
        });
    }

    private class SensorData {
        private final int sensorType;
        private final float[] values;

        SensorData(SensorEvent sensorEvent) {
            this.values = sensorEvent.values;
            this.sensorType = sensorEvent.sensor.getType();
        }

        byte[] getBtye() {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((this.values.length * 4) + 12);
            for (float f : this.values) {
                byteBufferAllocate.putFloat(f);
            }
            byteBufferAllocate.putInt(getSenserType());
            byteBufferAllocate.putLong(System.currentTimeMillis());
            return byteBufferAllocate.array();
        }

        String getCSV() {
            StringBuilder sb = new StringBuilder();
            for (float f : this.values) {
                sb.append(f);
                sb.append(",");
            }
            sb.append(getSenserType()).append(",").append(System.currentTimeMillis());
            return sb.toString();
        }

        JSONArray getJson() throws JSONException {
            double d;
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(System.currentTimeMillis() - SensorHelper.this.mStartCollectedDataTimestamp);
            DecimalFormat decimalFormat = new DecimalFormat("##0.0");
            double d2 = 0.0d;
            if (this.sensorType == 5) {
                try {
                    d2 = Double.parseDouble(decimalFormat.format(this.values[0]));
                } catch (Exception unused) {
                }
                jSONArray.put(d2);
            } else {
                for (float f : this.values) {
                    try {
                        d = Double.parseDouble(new DecimalFormat("##0.0000").format(f));
                    } catch (Exception e) {
                        AccountLogger.log("SensorHelper", e.toString());
                        d = 0.0d;
                    }
                    jSONArray.put(d);
                }
            }
            return jSONArray;
        }

        private int getSenserType() {
            int i = this.sensorType;
            if (i == 1) {
                return 2;
            }
            if (i == 2) {
                return 3;
            }
            if (i != 5) {
                return i != 6 ? 0 : 5;
            }
            return 4;
        }

        public String toString() {
            return getCSV();
        }
    }

    public class BatteryReceiver extends BroadcastReceiver {
        public BatteryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                SensorHelper.this.mCurrentBattery = intent.getExtras().getInt("level");
            }
            if (!"android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                SensorHelper.this.mIsCharging = 0;
            } else if (intent.getIntExtra("status", 1) == 2) {
                SensorHelper.this.mIsCharging = 1;
            }
        }
    }

    public static VerifyError getErrorMessage(int i, String str, int i2) {
        return new VerifyError.Build().code(i).msg(str).dialogMsg(i2).build();
    }
}
