package com.xiaomi.ai.utils;

import android.content.Context;
import com.market.sdk.utils.Constants;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.ai.SendWakeupDataStatusInterface;
import com.xiaomi.ai.SpeechEngine;
import com.xiaomi.ai.mibrain.MibrainRequest;
import com.xiaomi.ai.mibrain.MibrainWakeupRequestParams;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UploadHelper {
    private static final String TAG = "UploadHelper";
    long mCurrentContext;
    MibrainRequest mCurrentRequest;
    AtomicBoolean mFlag = new AtomicBoolean(false);

    public static class ASRRecordInfo {
        String asrRecordWord;
        String asrVendor;
        String clientId;
        JSONObject device;
        String deviceId;
        String locale;
        String requestId;
        JSONObject session;
        JSONObject userInfo;

        public String getAsrRecordWord() {
            return this.asrRecordWord;
        }

        public String getAsrVendor() {
            return this.asrVendor;
        }

        public String getClientId() {
            return this.clientId;
        }

        public JSONObject getDevice() {
            return this.device;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getLocale() {
            return this.locale;
        }

        public String getRequestId() {
            return this.requestId;
        }

        public JSONObject getSession() {
            return this.session;
        }

        public JSONObject getUserInfo() {
            return this.userInfo;
        }

        public void setAsrRecordWord(String str) {
            this.asrRecordWord = str;
        }

        public void setAsrVendor(String str) {
            this.asrVendor = str;
        }

        public void setClientId(String str) {
            this.clientId = str;
        }

        public void setDevice(JSONObject jSONObject) {
            this.device = jSONObject;
        }

        public void setDeviceId(String str) {
            this.deviceId = str;
        }

        public void setLocale(String str) {
            this.locale = str;
        }

        public void setRequestId(String str) {
            this.requestId = str;
        }

        public void setSession(JSONObject jSONObject) {
            this.session = jSONObject;
        }

        public void setUserInfo(JSONObject jSONObject) {
            this.userInfo = jSONObject;
        }
    }

    public static class WakeupDataInfo {
        JSONObject acousticInfo;
        int audioChannel;
        String audioCodec;
        String audioInfo;
        int audioRate;
        JSONObject device;
        String deviceId;
        JSONObject userInfo;
        String wakeupType;
        String wakeupVendor;
        String wakeupWord;

        public JSONObject getAcousticInfo() {
            return this.acousticInfo;
        }

        public int getAudioChannel() {
            return this.audioChannel;
        }

        public String getAudioCodec() {
            return this.audioCodec;
        }

        public String getAudioInfo() {
            return this.audioInfo;
        }

        public int getAudioRate() {
            return this.audioRate;
        }

        public JSONObject getDevice() {
            return this.device;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public JSONObject getUserInfo() {
            return this.userInfo;
        }

        public String getWakeupType() {
            return this.wakeupType;
        }

        public String getWakeupVendor() {
            return this.wakeupVendor;
        }

        public String getWakeupWord() {
            return this.wakeupWord;
        }

        public void setAcousticInfo(JSONObject jSONObject) {
            this.acousticInfo = jSONObject;
        }

        public void setAudioChannel(int i) {
            this.audioChannel = i;
        }

        public void setAudioCodec(String str) {
            this.audioCodec = str;
        }

        public void setAudioInfo(String str) {
            this.audioInfo = str;
        }

        public void setAudioRate(int i) {
            this.audioRate = i;
        }

        public void setDevice(JSONObject jSONObject) {
            this.device = jSONObject;
        }

        public void setDeviceId(String str) {
            this.deviceId = str;
        }

        public void setUserInfo(JSONObject jSONObject) {
            this.userInfo = jSONObject;
        }

        public void setWakeupType(String str) {
            this.wakeupType = str;
        }

        public void setWakeupVendor(String str) {
            this.wakeupVendor = str;
        }

        public void setWakeupWord(String str) {
            this.wakeupWord = str;
        }
    }

    public static String generateASRRecordMessage(Context context, ASRRecordInfo aSRRecordInfo) {
        if (aSRRecordInfo == null) {
            return null;
        }
        if (aSRRecordInfo.getClientId() == null) {
            Log.e("generateASRRecordMessage", "missing params");
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            putDataToJSON(jSONObject2, "ASR_RECORD", "type");
            putDataToJSON(jSONObject, jSONObject2, "meta");
            if (aSRRecordInfo.getDeviceId() == null) {
                aSRRecordInfo.setDeviceId(n.b(context));
            }
            if (aSRRecordInfo.getRequestId() == null) {
                String str = "";
                try {
                    for (byte b : MessageDigest.getInstance("MD5").digest(UUID.randomUUID().toString().getBytes())) {
                        String hexString = Integer.toHexString(b & 255);
                        if (hexString.length() == 1) {
                            hexString = "0" + hexString;
                        }
                        str = str + hexString;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                aSRRecordInfo.setRequestId(str.toLowerCase(Locale.US));
            }
            putDataToJSON(jSONObject3, aSRRecordInfo.getDeviceId(), "device_id");
            putDataToJSON(jSONObject3, aSRRecordInfo.getClientId(), com.xiaomi.onetrack.b.g.d);
            putDataToJSON(jSONObject3, aSRRecordInfo.getRequestId(), "request_id");
            putDataToJSON(jSONObject3, aSRRecordInfo.getLocale(), "locale");
            putDataToJSON(jSONObject3, aSRRecordInfo.getAsrVendor(), "asr_vendor");
            putDataToJSON(jSONObject3, aSRRecordInfo.getAsrRecordWord(), "asr_record_words");
            putDataToJSON(jSONObject3, aSRRecordInfo.getDevice(), Constants.JSON_DEVICE);
            putDataToJSON(jSONObject3, aSRRecordInfo.getUserInfo(), "user_info");
            putDataToJSON(jSONObject, jSONObject3, StatConstants.Event.REQUEST);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String generateSpliterJSON() {
        return "{\"meta\": {\"type\": \"VOICE_SPLITTER\"}}";
    }

    public static String generateVADBeginJSON() {
        return "{\"meta\": {\"type\": \"VAD_BEGIN\"}}";
    }

    public static String generateWakeupDataMessage(Context context, WakeupDataInfo wakeupDataInfo) {
        if (wakeupDataInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObject4 = new JSONObject();
        try {
            jSONObject2.put("type", "WAKEUP_AUDIO");
            jSONObject.put("meta", jSONObject2);
            if (wakeupDataInfo.getDeviceId() == null) {
                wakeupDataInfo.setDeviceId(n.b(context));
            }
            putDataToJSON(jSONObject3, wakeupDataInfo.getDeviceId(), "device_id");
            putDataToJSON(jSONObject3, wakeupDataInfo.getDevice(), Constants.JSON_DEVICE);
            putDataToJSON(jSONObject3, wakeupDataInfo.getUserInfo(), "user_info");
            putDataToJSON(jSONObject3, wakeupDataInfo.getWakeupVendor(), "wakeup_vendor");
            putDataToJSON(jSONObject3, wakeupDataInfo.getWakeupWord(), "wakeup_word");
            putDataToJSON(jSONObject3, wakeupDataInfo.getWakeupType(), "wakeup_type");
            putDataToJSON(jSONObject4, wakeupDataInfo.getAudioCodec(), "codec");
            putDataToJSON(jSONObject4, Integer.valueOf(wakeupDataInfo.getAudioChannel()), "channel");
            putDataToJSON(jSONObject4, Integer.valueOf(wakeupDataInfo.getAudioRate()), "rate");
            putDataToJSON(jSONObject3, jSONObject4, "audio_meta");
            putDataToJSON(jSONObject3, wakeupDataInfo.getAudioInfo(), "audio_info");
            putDataToJSON(jSONObject3, wakeupDataInfo.getAcousticInfo(), "acoustic_info");
            putDataToJSON(jSONObject, jSONObject3, StatConstants.Event.REQUEST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String generateWakeupDataMessage(WakeupDataInfo wakeupDataInfo) {
        if (wakeupDataInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            putDataToJSON(jSONObject, wakeupDataInfo.getWakeupVendor(), "vendor");
            putDataToJSON(jSONObject, wakeupDataInfo.getWakeupWord(), "word");
            putDataToJSON(jSONObject, wakeupDataInfo.getAcousticInfo(), "acoustic_info");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private static void putDataToJSON(JSONObject jSONObject, Object obj, String str) throws JSONException {
        if (jSONObject == null || obj == null) {
            return;
        }
        jSONObject.put(str, obj);
    }

    public synchronized int addUploadData(byte[] bArr, boolean z) {
        MibrainRequest mibrainRequest = this.mCurrentRequest;
        if (mibrainRequest != null) {
            long j = this.mCurrentContext;
            if (j != 0 && j != -1) {
                return mibrainRequest.addUploadData(j, bArr, z ? 1 : 0);
            }
        }
        Log.d(TAG, "addUploadData failed" + this.mCurrentContext);
        return -3;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0024  */
    public synchronized int endUploadData() {
        int iEndUploadData;
        MibrainRequest mibrainRequest = this.mCurrentRequest;
        if (mibrainRequest != null) {
            long j = this.mCurrentContext;
            if (j == 0 || j == -1) {
                iEndUploadData = -3;
            } else {
                iEndUploadData = mibrainRequest.endUploadData(j);
                this.mCurrentContext = 0L;
                this.mCurrentRequest = null;
                this.mFlag.compareAndSet(true, false);
            }
        } else {
            iEndUploadData = -3;
        }
        return iEndUploadData;
    }

    public synchronized int startUploadRequest(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, com.xiaomi.ai.m mVar, int i, int i2, String str, String str2, String str3, String str4) {
        if (this.mFlag.get()) {
            Log.d(TAG, "start startUploadRequest Already");
            return -2;
        }
        this.mFlag.compareAndSet(false, true);
        m mVar2 = new m(this, sendWakeupDataStatusInterface, mVar);
        MibrainWakeupRequestParams mibrainWakeupRequestParams = new MibrainWakeupRequestParams();
        mibrainWakeupRequestParams.setApiKey(str3);
        mibrainWakeupRequestParams.setAppId(str);
        if (i2 == SpeechEngine.ENGINE_AUTH_MIAI || i2 == SpeechEngine.ENGINE_AUTH_MIAO || i2 == SpeechEngine.ENGINE_AUTH_ANONYMOUS || i2 == SpeechEngine.ENGINE_AUTH_ANONYMOUS_PROXY) {
            mibrainWakeupRequestParams.setToken(null);
        } else {
            mibrainWakeupRequestParams.setToken(str2);
        }
        mibrainWakeupRequestParams.setScopeData(str4);
        MibrainRequest mibrainRequest = new MibrainRequest(mibrainWakeupRequestParams);
        mibrainRequest.setRequestListener(mVar2);
        long jStartUploadRequest = mibrainRequest.startUploadRequest(i, i2);
        this.mCurrentContext = jStartUploadRequest;
        if (jStartUploadRequest != 0 && jStartUploadRequest != -1) {
            this.mCurrentRequest = mibrainRequest;
            return 0;
        }
        this.mFlag.compareAndSet(true, false);
        Log.d(TAG, "upload data new context failed " + this.mCurrentContext);
        this.mCurrentRequest = null;
        return -1;
    }
}
