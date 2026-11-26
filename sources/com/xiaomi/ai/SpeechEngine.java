package com.xiaomi.ai;

import android.content.Context;
import android.media.AudioTrack;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.mibrain.MibrainOauthManager;
import com.xiaomi.ai.mibrain.MibrainRequestParamsBuilder;
import com.xiaomi.ai.utils.DeviceUtils;
import com.xiaomi.ai.utils.Log;
import com.xiaomi.ai.utils.UploadHelper;
import com.xiaomi.onetrack.api.ah;
import java.util.ArrayList;
import miuix.smartaction.SmartAction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SpeechEngine {
    public static final int ENGINE_MI_AI = 1;
    public static final int ENV_PREVIEW = 1;
    public static final int ENV_PRODUCTION = 0;
    public static final int ENV_PRODUCTION_SGP = 3;
    public static final int ENV_STAGING = 2;
    public static final int EVENT_TRACK_RECEIVER_DEFALUT = 0;
    public static final int EVENT_TRACK_RECEIVER_UPSTREAM = 128;
    public static final int STREAMING_PLAYER_DEFAULT = 1;
    public static final int STREAMING_PLAYER_GET_PCM = 2;
    private static final String TAG = "MiSpeechSDK:SpeechEngine";
    public static final int USE_ANONYMOUS = 1;
    public static final int USE_ANONYMOUS_PROXY = 2;
    protected String mAppId;
    protected AsrListener mAsrListener;
    protected int mAuthMode;
    private BluetoothManager mBluetoothManager;
    protected Context mContext;
    protected String mDeviceModel;
    protected EventTrackListener mEventTrackListener;
    protected InstructionListener mInstructionListener;
    private boolean mIsScoEnable;
    protected NlpListener mNlpListener;
    protected MiAiOauthCallbacks mOauthListener;
    protected String mRedirectUrl;
    protected q mResultParser;
    protected ServiceEventListener mServiceEventListener;
    protected SpeechError mSpeechError;
    protected String mToken;
    protected com.xiaomi.ai.utils.k mTrackUtils;
    protected TtsListener mTtsListener;
    protected NeedUpdateTokenCallback mUpdateTokenCallback;
    protected String mUserAgent;
    protected VoiceprintDeleteListener mVPDelListener;
    protected VoiceprintRecognizeListener mVPListener;
    protected VoiceprintQueryListener mVPQryListener;
    protected VoiceprintRegisterListener mVPRListener;
    public static final int ENGINE_AUTH_APPIDTOKEN = MibrainRequestParamsBuilder.MIBRAIN_AUTH_APPIDTOKEN;
    public static final int ENGINE_AUTH_MIOT = MibrainRequestParamsBuilder.MIBRAIN_AUTH_MIOTTOKEN;
    public static final int ENGINE_AUTH_MIAI = MibrainRequestParamsBuilder.MIBRAIN_AUTH_MIAITOKEN;
    public static final int ENGINE_AUTH_TP = MibrainRequestParamsBuilder.MIBRAIN_AUTH_TPTOKEN;
    public static final int ENGINE_AUTH_MIAO = MibrainRequestParamsBuilder.MIBRAIN_AUTH_MIAOTOKEN;
    public static final int ENGINE_AUTH_ANONYMOUS = MibrainRequestParamsBuilder.MIBRAIN_AUTH_ANONYMOUS;
    public static final int ENGINE_AUTH_ANONYMOUS_PROXY = MibrainRequestParamsBuilder.MIBRAIN_AUTH_ANONYMOUS_PROXY;
    protected int mStreamType = 3;
    protected String mProtocolVersion = "1.0.5";
    protected int mStartEventTrack = 1;
    protected int mConnectTimeout = 5;
    protected int mReceiveTimeout = 10;
    protected int mUploadTimeout = 4;
    protected int mActiveTimeout = 10;
    protected int mEventTrackReceiver = 0;
    protected int mUseExternalOAuthToken = 0;

    public static class ParamBuilder {
        public static final int EVENT_LEVEL_DEBUG = 2;
        public static final int EVENT_LEVEL_INFO = 1;
        public static final int EVENT_LEVEL_WARN = 0;
        String apiKey;
        AsrRequest asrRequest;
        EventRequest eventRequest;
        int mEventLevel = 1;
        boolean needAsr;
        boolean needEvent;
        boolean needExtraNlp;
        boolean needNlp;
        boolean needTts;
        boolean needVpDel;
        boolean needVpQry;
        boolean needVpRec;
        boolean needVpReg;
        boolean needWakeupData;
        NlpRequest nlpRequest;
        TtsRequest ttsRequest;
        VoiceprintDeleteRequest voiceprintDeleteRequest;
        VoiceprintQueryRequest voiceprintQueryRequest;
        VoiceprintRecognizeRequest voiceprintRecognizeRequest;
        VoiceprintRegisterRequest voiceprintRegisterRequest;
        byte[] wakeupData;
        String wakeupInfoString;

        public ParamBuilder needAsr() {
            needAsr(true);
            return this;
        }

        public ParamBuilder needAsr(boolean z) {
            this.needAsr = z;
            return this;
        }

        public ParamBuilder needEvent() {
            this.needEvent = true;
            return this;
        }

        public ParamBuilder needEvent(boolean z) {
            this.needEvent = z;
            return this;
        }

        public ParamBuilder needNlp() {
            needNlp(true);
            return this;
        }

        public ParamBuilder needNlp(boolean z) {
            this.needNlp = z;
            return this;
        }

        public ParamBuilder needTts() {
            needTts(true);
            return this;
        }

        public ParamBuilder needTts(boolean z) {
            this.needTts = z;
            return this;
        }

        public ParamBuilder needWakeupData() {
            needWakeupData(true);
            return this;
        }

        public ParamBuilder needWakeupData(boolean z) {
            this.needWakeupData = z;
            return this;
        }

        public ParamBuilder setApiKey(String str) {
            this.apiKey = str;
            return this;
        }

        public ParamBuilder setAsrRequest(AsrRequest asrRequest) {
            this.asrRequest = asrRequest;
            return this;
        }

        public ParamBuilder setEventLevel(int i) {
            this.mEventLevel = i;
            return this;
        }

        public ParamBuilder setEventRequest(EventRequest eventRequest) {
            this.eventRequest = eventRequest;
            return this;
        }

        public ParamBuilder setNlpRequest(NlpRequest nlpRequest) {
            this.nlpRequest = nlpRequest;
            return this;
        }

        public ParamBuilder setTtsRequest(TtsRequest ttsRequest) {
            this.ttsRequest = ttsRequest;
            return this;
        }

        public ParamBuilder setWakeupData(byte[] bArr) {
            this.wakeupData = bArr;
            return this;
        }

        public ParamBuilder setWakeupInfo(UploadHelper.WakeupDataInfo wakeupDataInfo) {
            this.wakeupInfoString = UploadHelper.generateWakeupDataMessage(wakeupDataInfo);
            return this;
        }
    }

    public enum ProcessStage {
        STAGE_REQUESTING,
        STAGE_ASR,
        STAGE_NLP,
        STAGE_TTS,
        STAGE_VP_REG,
        STAGE_VP,
        STAGE_VP_DEL,
        STAGE_VP_QRY,
        STAGE_EVENT,
        STAGE_IDLE
    }

    public static class ResultParser extends q {
        @Override // com.xiaomi.ai.q
        public SpeechResult getAsrResult(JSONObject jSONObject) throws JSONException {
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            JSONArray jSONArray = jSONObject2.getJSONArray("queries");
            if (jSONArray != null && jSONArray.length() > 0) {
                speechResult.query = jSONObject2.getJSONArray("queries").getJSONObject(0).getString(SmartAction.Feature.QUERY);
                speechResult.response = jSONObject2.toString();
            }
            speechResult.requestId = jSONObject.getJSONObject("meta").getString("request_id");
            return speechResult;
        }

        @Override // com.xiaomi.ai.q
        public Instruction[] getInstruction(JSONObject jSONObject) throws JSONException {
            String str;
            if (!jSONObject.has(ah.H)) {
                return null;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            if (!jSONObject2.has("instructions")) {
                return null;
            }
            JSONArray jSONArray = jSONObject2.getJSONArray("instructions");
            if (jSONArray != null) {
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        arrayList.add(APIUtils.readInstruction(jSONArray.get(i).toString()));
                    } catch (Exception e) {
                        Log.MiLogE(SpeechEngine.TAG, "getInstruction: failed to readInstruction, " + jSONArray.get(i).toString());
                        e.printStackTrace();
                    }
                }
                if (!arrayList.isEmpty()) {
                    return (Instruction[]) arrayList.toArray(new Instruction[arrayList.size()]);
                }
                str = "getInstruction: instruction array is empty";
            } else {
                str = "getInstruction: no instructions";
            }
            Log.MiLogE(SpeechEngine.TAG, str);
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x006b  */
        @Override // com.xiaomi.ai.q
        public SpeechResult getNlpResult(JSONObject jSONObject) throws JSONException {
            JSONObject jSONObject2;
            String str;
            String strOptString;
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject3 = jSONObject.getJSONObject(ah.H);
            double d = jSONObject3.getDouble("version");
            Object obj = jSONObject3.get("answer");
            if (obj instanceof JSONArray) {
                jSONObject2 = ((JSONArray) obj).getJSONObject(0);
            } else {
                jSONObject2 = obj instanceof JSONObject ? (JSONObject) obj : null;
            }
            JSONObject jSONObject4 = jSONObject2.getJSONObject("intention");
            JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("content");
            String strOptString2 = jSONObject3.optString("session_id");
            String strOptString3 = jSONObject2.optString("action");
            String strOptString4 = jSONObject2.optString("domain");
            String string = jSONObject2.toString();
            String strOptString5 = jSONObject2.optString("text");
            String strOptString6 = jSONObject4.optString(SmartAction.Feature.QUERY);
            if (d >= 2.2d) {
                if (jSONObjectOptJSONObject == null) {
                    strOptString = null;
                } else {
                    str = "to_speak";
                    strOptString = jSONObjectOptJSONObject.optString(str);
                }
            } else if (jSONObjectOptJSONObject == null) {
                strOptString = null;
            } else {
                str = "toSpeak";
                strOptString = jSONObjectOptJSONObject.optString(str);
            }
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.optJSONObject("to_display");
            String strOptString7 = jSONObjectOptJSONObject2 == null ? null : jSONObjectOptJSONObject2.optString("text");
            int iOptInt = jSONObjectOptJSONObject2 == null ? Integer.MIN_VALUE : jSONObjectOptJSONObject2.optInt("duration", Integer.MIN_VALUE);
            int iOptInt2 = jSONObjectOptJSONObject == null ? 0 : jSONObjectOptJSONObject.optInt("unknown_domain_action");
            boolean zOptBoolean = jSONObjectOptJSONObject == null ? false : jSONObjectOptJSONObject.optBoolean("open_mic", false);
            String strOptString8 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("directive") : null;
            int iOptInt3 = jSONObjectOptJSONObject == null ? Integer.MIN_VALUE : jSONObjectOptJSONObject.optInt("vad_idle", Integer.MIN_VALUE);
            speechResult.answerText = strOptString5;
            speechResult.query = strOptString6;
            speechResult.intention = jSONObject4.toString();
            speechResult.content = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.toString();
            speechResult.requestId = jSONObject.getJSONObject("meta").getString("request_id");
            speechResult.sessionId = strOptString2;
            speechResult.domain = strOptString4;
            speechResult.action = strOptString3;
            speechResult.response = jSONObject3.toString();
            speechResult.answer = string;
            speechResult.toSpeak = strOptString;
            speechResult.toDisplay = strOptString7;
            speechResult.displayDuration = iOptInt;
            speechResult.openMic = zOptBoolean;
            speechResult.directive = strOptString8;
            speechResult.unknownDomainAction = iOptInt2;
            speechResult.vadIdle = iOptInt3;
            return speechResult;
        }

        @Override // com.xiaomi.ai.q
        public SpeechResult getVoiceDelResult(JSONObject jSONObject) throws JSONException {
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            speechResult.regResponse = jSONObject2;
            speechResult.deleteSuccess = jSONObject2.optBoolean("is_success", false);
            speechResult.extraMessage = jSONObject2.optString("error_msg");
            speechResult.requestId = jSONObject.getJSONObject("meta").getString("request_id");
            speechResult.regResult = jSONObject2.optJSONArray("result");
            return speechResult;
        }

        @Override // com.xiaomi.ai.q
        public SpeechResult getVoiceQryResult(JSONObject jSONObject) throws JSONException {
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            speechResult.regResponse = jSONObject2;
            JSONArray jSONArray = jSONObject2.getJSONArray("result");
            speechResult.regResult = jSONArray;
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null && jSONArray.length() > 0) {
                JSONArray jSONArrayOptJSONArray = jSONArray.getJSONObject(0).optJSONArray("members");
                if (jSONArrayOptJSONArray != null) {
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        arrayList.add(jSONArrayOptJSONArray.getString(i));
                    }
                }
            }
            speechResult.extraMessage = jSONObject2.optString("error_msg");
            speechResult.voiceUserIds = arrayList;
            speechResult.requestId = jSONObject.getJSONObject("meta").getString("request_id");
            return speechResult;
        }

        @Override // com.xiaomi.ai.q
        public SpeechResult getVoiceRecResult(JSONObject jSONObject) throws JSONException {
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            speechResult.regResponse = jSONObject2;
            JSONArray jSONArray = jSONObject2.getJSONArray("result");
            if (jSONArray != null && jSONArray.length() > 0) {
                speechResult.voiceUserId = jSONArray.getJSONObject(0).getString("role");
            }
            speechResult.extraMessage = jSONObject2.optString("error_msg");
            speechResult.requestId = jSONObject.getJSONObject("meta").getString("request_id");
            speechResult.regResult = jSONObject2.optJSONArray("result");
            return speechResult;
        }

        @Override // com.xiaomi.ai.q
        public SpeechResult getVoiceRegResult(JSONObject jSONObject) throws JSONException {
            SpeechResult speechResult = new SpeechResult();
            JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
            speechResult.regResponse = jSONObject2;
            speechResult.regSuccess = jSONObject2.optBoolean("is_success", false);
            speechResult.isRegFinal = "RESULT_VOR_REG_FINAL".equals(jSONObject.getJSONObject("meta").getString("type"));
            speechResult.extraMessage = jSONObject2.optString("error_msg");
            speechResult.requestId = jSONObject.optJSONObject("meta").getString("request_id");
            speechResult.regResult = jSONObject2.optJSONArray("result");
            return speechResult;
        }
    }

    SpeechEngine(Context context, String str, String str2) {
        this.mContext = context.getApplicationContext();
        this.mAppId = str;
        this.mToken = str2;
        this.mTrackUtils = new com.xiaomi.ai.utils.k(this.mAppId, DeviceUtils.getDeviceId(this.mContext));
    }

    public static SpeechEngine createEngine(Context context, int i, String str) {
        c cVar = i != 1 ? null : new c(context, str, null);
        if (cVar != null) {
            cVar.init(context, null, ENGINE_AUTH_MIOT);
        }
        return cVar;
    }

    @Deprecated
    public static SpeechEngine createEngine(Context context, int i, String str, String str2) {
        c cVar = i != 1 ? null : new c(context, str, str2);
        if (cVar != null) {
            cVar.init(context, null, ENGINE_AUTH_APPIDTOKEN);
        }
        return cVar;
    }

    public static SpeechEngine createEngine(Context context, int i, String str, String str2, String str3) {
        c cVar = i != 1 ? null : new c(context, str, str2);
        if (cVar != null) {
            cVar.init(context, str3, ENGINE_AUTH_MIAI);
        }
        return cVar;
    }

    public static SpeechEngine createEngine(Context context, int i, String str, String str2, String str3, int i2) {
        int i3;
        Log.MiLogD("SpeechEngine", "use anonymous mode " + i2);
        c cVar = i != 1 ? null : new c(context, str, str2);
        if (cVar == null) {
            return cVar;
        }
        if (i2 == 1) {
            i3 = ENGINE_AUTH_ANONYMOUS;
        } else {
            if (i2 != 2) {
                return null;
            }
            if (!context.getPackageName().equals("com.xiaomi.mibrain.speech") && !context.getPackageName().equals("com.xiaomi.mibrain.demo")) {
                Log.MiLogE("ERROR", "illegal call");
                return null;
            }
            i3 = ENGINE_AUTH_ANONYMOUS_PROXY;
        }
        cVar.init(context, str3, i3);
        cVar.setAnonymousAuthCallbacks(new o());
        return cVar;
    }

    public static SpeechEngine createEngine(Context context, int i, String str, String str2, String str3, boolean z) {
        int i2;
        c cVar = i != 1 ? null : new c(context, str, str2);
        if (z) {
            if (cVar != null) {
                i2 = ENGINE_AUTH_MIAO;
                cVar.init(context, str3, i2);
                return cVar;
            }
            return cVar;
        }
        if (cVar != null) {
            i2 = ENGINE_AUTH_MIAI;
            cVar.init(context, str3, i2);
            return cVar;
        }
        return cVar;
    }

    public static SpeechEngine createEngine(Context context, int i, String str, boolean z) {
        c cVar = i != 1 ? null : new c(context, str, null);
        if (cVar != null) {
            cVar.init(context, null, z ? ENGINE_AUTH_TP : ENGINE_AUTH_MIOT);
        }
        return cVar;
    }

    public static int getSDKVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    public static String getSDKVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public abstract void addBuffer(byte[] bArr, int i, int i2);

    public abstract void addNlpData(JSONObject jSONObject);

    public abstract void addVADBegin();

    public abstract void cancelTTS();

    public abstract void cleanAllUserLoginData();

    public abstract void cleanUserLoginData();

    public abstract void cleanUserLoginData(String str);

    public final void deleteVoiceprint(VoiceprintDeleteRequest voiceprintDeleteRequest) {
        forceStop();
        doDeleteVoiceprint(voiceprintDeleteRequest);
    }

    public abstract void doAsyncTrackUpload(HTTPCallback hTTPCallback, TrackInfo trackInfo, String str);

    public abstract void doAsyncTrackUpload(TrackInfo trackInfo, String str);

    protected abstract void doDeleteVoiceprint(VoiceprintDeleteRequest voiceprintDeleteRequest);

    protected abstract void doListenSpeech(AsrRequest asrRequest);

    protected abstract void doQueryVoiceprint(VoiceprintQueryRequest voiceprintQueryRequest);

    protected abstract void doSemanticsParse(NlpRequest nlpRequest);

    protected abstract void doSpeak(TtsRequest ttsRequest);

    protected abstract void doStartIntegrally(ParamBuilder paramBuilder);

    protected abstract void doVoiceprintRecognize(VoiceprintRecognizeRequest voiceprintRecognizeRequest);

    protected abstract void doVoiceprintRegister(VoiceprintRegisterRequest voiceprintRegisterRequest);

    public void enableScoCheck(boolean z) {
        this.mIsScoEnable = z;
        if (!z) {
            BluetoothManager bluetoothManager = this.mBluetoothManager;
            if (bluetoothManager != null) {
                bluetoothManager.unregister();
                return;
            }
            return;
        }
        if (this.mBluetoothManager == null) {
            BluetoothManager bluetoothManager2 = BluetoothManager.getInstance(this.mContext);
            this.mBluetoothManager = bluetoothManager2;
            bluetoothManager2.register();
        }
    }

    public void endSpeech() {
    }

    public abstract void endVoiceprintRecognize();

    public abstract void endVoiceprintRegister();

    public void forceStop() {
        this.mSpeechError = null;
    }

    public abstract String getAuthorizationValue();

    public abstract String getAuthorizationValue(boolean z);

    public SpeechError getError() {
        return this.mSpeechError;
    }

    protected final ProcessStage getNextStage(ParamBuilder paramBuilder, ProcessStage processStage) {
        if (processStage == ProcessStage.STAGE_REQUESTING) {
            if (paramBuilder.needAsr) {
                return ProcessStage.STAGE_ASR;
            }
            if (paramBuilder.needNlp) {
                return ProcessStage.STAGE_NLP;
            }
            if (paramBuilder.needTts) {
                return ProcessStage.STAGE_TTS;
            }
            if (paramBuilder.needVpRec) {
                return ProcessStage.STAGE_VP;
            }
            if (paramBuilder.needVpReg) {
                return ProcessStage.STAGE_VP_REG;
            }
            return paramBuilder.needVpDel ? ProcessStage.STAGE_VP_DEL : ProcessStage.STAGE_VP_QRY;
        }
        if (processStage == ProcessStage.STAGE_ASR) {
            if (paramBuilder.needNlp) {
                return ProcessStage.STAGE_NLP;
            }
            if (paramBuilder.needTts) {
                return ProcessStage.STAGE_TTS;
            }
        } else if (processStage == ProcessStage.STAGE_NLP) {
            if (paramBuilder.needTts) {
                return ProcessStage.STAGE_TTS;
            }
        } else if (processStage == ProcessStage.STAGE_VP_REG) {
            return ProcessStage.STAGE_VP_REG;
        }
        return ProcessStage.STAGE_IDLE;
    }

    public abstract boolean getOAuthStatus();

    public abstract String getResourceDetails(String str, String str2);

    public abstract int getTTSMode();

    public abstract UploadHelper getUploadHelper(SendWakeupDataStatusInterface sendWakeupDataStatusInterface);

    public boolean hasError() {
        return this.mSpeechError != null;
    }

    public void init(Context context, String str, int i) {
        Log.init(context);
        this.mRedirectUrl = str;
        this.mAuthMode = i;
    }

    public final void listenSpeech(AsrRequest asrRequest) {
        forceStop();
        doListenSpeech(asrRequest);
    }

    protected void notifyAsrBeginningOfSpeech() {
        BluetoothManager bluetoothManager;
        Log.MiLogE(TAG, "mIsScoEnable" + this.mIsScoEnable);
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(true);
        }
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onBeginningOfSpeech();
        }
    }

    protected void notifyAsrBufferReceived(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        this.mAsrListener.onBufferReceived(bArr);
    }

    protected void notifyAsrEndOfSpeech() {
        BluetoothManager bluetoothManager;
        Log.MiLogE(TAG, "notifyAsrEndOfSpeech:=" + this.mIsScoEnable);
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(false);
        }
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onEndOfSpeech();
        }
    }

    protected void notifyAsrError(SpeechError speechError) {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onError(speechError);
        }
    }

    protected void notifyAsrEvent() {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onEvent();
        }
    }

    protected void notifyAsrFileStored(String str, String str2) {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onFileStored(str, str2);
        }
    }

    protected void notifyAsrParticalResults(SpeechResult speechResult) {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onPartialResults(speechResult);
        }
    }

    protected void notifyAsrReadyForSpeech() {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onReadyForSpeech();
        }
    }

    protected void notifyAsrResults(SpeechResult speechResult) {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onResults(speechResult);
        }
    }

    protected void notifyAsrRmsChanged(float f) {
        AsrListener asrListener = this.mAsrListener;
        if (asrListener != null) {
            asrListener.onRmsChanged(f);
        }
    }

    protected void notifyError(ParamBuilder paramBuilder, SpeechError speechError) {
        Log.MiLogE(TAG, "notifyError: " + speechError + "state" + speechError.stage);
        this.mSpeechError = speechError;
        if (speechError.stage == ProcessStage.STAGE_REQUESTING) {
            speechError.stage = getNextStage(paramBuilder, speechError.stage);
        }
        switch (p.a[speechError.stage.ordinal()]) {
            case 1:
                notifyAsrError(speechError);
                break;
            case 2:
                notifyNlpError(speechError);
                break;
            case 3:
                notifyTtsError(speechError);
                break;
            case 4:
                notifyVoiceRecError(speechError);
                break;
            case 5:
                notifyVoiceRegError(speechError);
                break;
            case 6:
                notifyVoiceDelError(speechError);
                break;
            case 7:
                notifyVoiceQryError(speechError);
                break;
        }
    }

    protected void notifyEventTrack(String str) {
        EventTrackListener eventTrackListener = this.mEventTrackListener;
        if (eventTrackListener != null) {
            eventTrackListener.onEventTrack(str);
        }
    }

    protected void notifyInstruction(Instruction[] instructionArr, SpeechResult speechResult) {
        InstructionListener instructionListener = this.mInstructionListener;
        if (instructionListener != null) {
            instructionListener.onInstruction(instructionArr, speechResult);
        }
    }

    protected void notifyNlpError(SpeechError speechError) {
        NlpListener nlpListener = this.mNlpListener;
        if (nlpListener != null) {
            nlpListener.onError(speechError);
        }
    }

    protected void notifyNlpResults(SpeechResult speechResult) {
        NlpListener nlpListener = this.mNlpListener;
        if (nlpListener != null) {
            nlpListener.onResult(speechResult);
        }
    }

    protected void notifyServiceEvent(ServiceEvent serviceEvent) {
        ServiceEventListener serviceEventListener = this.mServiceEventListener;
        if (serviceEventListener != null) {
            serviceEventListener.onEvent(serviceEvent);
        }
    }

    protected void notifyTtsError(SpeechError speechError) {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onError(speechError);
        }
    }

    protected void notifyTtsGotUrl(String str) {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onTtsGotURL(str);
        }
    }

    protected void notifyTtsPCMData(PCMInfo pCMInfo) {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onPCMData(pCMInfo);
        }
    }

    protected void notifyTtsPlayFinish() {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onPlayFinish();
        }
    }

    protected void notifyTtsPlayStart(AudioTrack audioTrack) {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onPlayStart(audioTrack);
        }
    }

    protected void notifyTtsTransEnd(boolean z) {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onTtsTransEnd(z);
        }
    }

    protected void notifyTtsTransStart() {
        TtsListener ttsListener = this.mTtsListener;
        if (ttsListener != null) {
            ttsListener.onTtsTransStart();
        }
    }

    protected void notifyVoiceDelError(SpeechError speechError) {
        VoiceprintDeleteListener voiceprintDeleteListener = this.mVPDelListener;
        if (voiceprintDeleteListener != null) {
            voiceprintDeleteListener.onError(speechError);
        }
    }

    protected void notifyVoiceDelResults(SpeechResult speechResult) {
        VoiceprintDeleteListener voiceprintDeleteListener = this.mVPDelListener;
        if (voiceprintDeleteListener != null) {
            voiceprintDeleteListener.onResults(speechResult);
        }
    }

    protected void notifyVoiceQryError(SpeechError speechError) {
        VoiceprintQueryListener voiceprintQueryListener = this.mVPQryListener;
        if (voiceprintQueryListener != null) {
            voiceprintQueryListener.onError(speechError);
        }
    }

    protected void notifyVoiceQryResults(SpeechResult speechResult) {
        VoiceprintQueryListener voiceprintQueryListener = this.mVPQryListener;
        if (voiceprintQueryListener != null) {
            voiceprintQueryListener.onResults(speechResult);
        }
    }

    protected void notifyVoiceRecBeginningOfSpeech() {
        BluetoothManager bluetoothManager;
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(true);
        }
        VoiceprintRecognizeListener voiceprintRecognizeListener = this.mVPListener;
        if (voiceprintRecognizeListener != null) {
            voiceprintRecognizeListener.onBeginningOfDataInput();
        }
    }

    protected void notifyVoiceRecBufferReceived(byte[] bArr) {
        VoiceprintRecognizeListener voiceprintRecognizeListener;
        if (bArr == null || (voiceprintRecognizeListener = this.mVPListener) == null) {
            return;
        }
        voiceprintRecognizeListener.onBufferReceived(bArr);
    }

    protected void notifyVoiceRecEndOfSpeech() {
        BluetoothManager bluetoothManager;
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(false);
        }
        VoiceprintRecognizeListener voiceprintRecognizeListener = this.mVPListener;
        if (voiceprintRecognizeListener != null) {
            voiceprintRecognizeListener.onEndOfDataInput();
        }
    }

    protected void notifyVoiceRecError(SpeechError speechError) {
        VoiceprintRecognizeListener voiceprintRecognizeListener = this.mVPListener;
        if (voiceprintRecognizeListener != null) {
            voiceprintRecognizeListener.onError(speechError);
        }
    }

    protected void notifyVoiceRecResults(SpeechResult speechResult) {
        VoiceprintRecognizeListener voiceprintRecognizeListener = this.mVPListener;
        if (voiceprintRecognizeListener != null) {
            voiceprintRecognizeListener.onResults(speechResult);
        }
    }

    protected void notifyVoiceRecRmsChanged(float f) {
        VoiceprintRecognizeListener voiceprintRecognizeListener = this.mVPListener;
        if (voiceprintRecognizeListener != null) {
            voiceprintRecognizeListener.onRmsChanged(f);
        }
    }

    protected void notifyVoiceRegBeginningOfSpeech() {
        BluetoothManager bluetoothManager;
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(true);
        }
        VoiceprintRegisterListener voiceprintRegisterListener = this.mVPRListener;
        if (voiceprintRegisterListener != null) {
            voiceprintRegisterListener.onBeginningOfDataInput();
        }
    }

    protected void notifyVoiceRegBufferReceived(byte[] bArr) {
        VoiceprintRegisterListener voiceprintRegisterListener;
        if (bArr == null || (voiceprintRegisterListener = this.mVPRListener) == null) {
            return;
        }
        voiceprintRegisterListener.onBufferReceived(bArr);
    }

    protected void notifyVoiceRegEndOfSpeech() {
        BluetoothManager bluetoothManager;
        if (this.mIsScoEnable && (bluetoothManager = this.mBluetoothManager) != null) {
            bluetoothManager.setBluetoothOn(false);
        }
        VoiceprintRegisterListener voiceprintRegisterListener = this.mVPRListener;
        if (voiceprintRegisterListener != null) {
            voiceprintRegisterListener.onEndOfDataInput();
        }
    }

    protected void notifyVoiceRegError(SpeechError speechError) {
        VoiceprintRegisterListener voiceprintRegisterListener = this.mVPRListener;
        if (voiceprintRegisterListener != null) {
            voiceprintRegisterListener.onError(speechError);
        }
    }

    protected void notifyVoiceRegResults(SpeechResult speechResult) {
        VoiceprintRegisterListener voiceprintRegisterListener = this.mVPRListener;
        if (voiceprintRegisterListener != null) {
            voiceprintRegisterListener.onResults(speechResult);
        }
    }

    protected void notifyVoiceRegRmsChanged(float f) {
        VoiceprintRegisterListener voiceprintRegisterListener = this.mVPRListener;
        if (voiceprintRegisterListener != null) {
            voiceprintRegisterListener.onRmsChanged(f);
        }
    }

    public final void queryVoiceprint(VoiceprintQueryRequest voiceprintQueryRequest) {
        forceStop();
        doQueryVoiceprint(voiceprintQueryRequest);
    }

    public void release() {
        BluetoothManager bluetoothManager = this.mBluetoothManager;
        if (bluetoothManager != null) {
            bluetoothManager.unregister();
        }
    }

    public final void semanticsParse(NlpRequest nlpRequest) {
        forceStop();
        doSemanticsParse(nlpRequest);
    }

    public abstract int sendPendingNLPData(NlpRequest nlpRequest);

    public abstract void sendWakeupData(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, String str, String str2, String str3, int i, int i2, String str4, byte[] bArr);

    public void setActiveTimeout(int i) {
        this.mActiveTimeout = i;
    }

    public void setAnonymousAuthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        this.mOauthListener = miAiOauthCallbacks;
    }

    public void setAsrLisnter(AsrListener asrListener) {
        this.mAsrListener = asrListener;
    }

    public abstract boolean setAuthorizationTokens(String str, String str2, long j);

    protected void setBluetoothOn(boolean z) {
        BluetoothManager bluetoothManager;
        if (!this.mIsScoEnable || (bluetoothManager = this.mBluetoothManager) == null) {
            return;
        }
        bluetoothManager.setBluetoothOn(z);
    }

    public void setConnectTimeout(int i) {
        this.mConnectTimeout = i;
    }

    public void setCustomNlpUrl(String str) {
    }

    public void setDeviceModel(String str) {
        this.mDeviceModel = str;
    }

    public void setEnv(int i) {
    }

    public void setEnv(int i, String str) {
    }

    public void setEnv(int i, String str, String str2) {
    }

    public void setEventTrackListener(EventTrackListener eventTrackListener) {
        this.mEventTrackListener = eventTrackListener;
    }

    public void setEventTrackReceiver(int i) {
        this.mEventTrackReceiver = i;
    }

    public abstract void setExternalOAuthToken(String str);

    public void setInstructionListener(InstructionListener instructionListener) {
        this.mInstructionListener = instructionListener;
    }

    public void setMiAIOauthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        this.mOauthListener = miAiOauthCallbacks;
    }

    public void setMiAOOauthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        this.mOauthListener = miAiOauthCallbacks;
    }

    public void setNlpListener(NlpListener nlpListener) {
        this.mNlpListener = nlpListener;
    }

    public void setProtocolVersion(String str) {
        this.mProtocolVersion = str;
    }

    public void setReceiveTimeout(int i) {
        this.mReceiveTimeout = i;
    }

    public void setServiceEventListener(ServiceEventListener serviceEventListener) {
        this.mServiceEventListener = serviceEventListener;
    }

    public void setStartEventTrack(boolean z) {
        this.mStartEventTrack = z ? 1 : 0;
    }

    public abstract void setTTSMode(int i);

    public abstract void setTokenUpdatedCallback(MibrainOauthManager.updateTokenCallback updatetokencallback);

    public abstract void setTrackUploadCallback(HTTPCallback hTTPCallback);

    public void setTtsAudioStreamType(int i) {
        this.mStreamType = i;
    }

    public abstract void setTtsEnable(boolean z);

    public void setTtsListener(TtsListener ttsListener) {
        this.mTtsListener = ttsListener;
    }

    public void setUpdateTokenCallback(NeedUpdateTokenCallback needUpdateTokenCallback) {
        this.mUpdateTokenCallback = needUpdateTokenCallback;
    }

    public void setUploadTimeout(int i) {
        this.mUploadTimeout = i;
    }

    public void setUseExternalOAuthToken(boolean z) {
        this.mUseExternalOAuthToken = z ? 1 : 0;
    }

    public void setUserAgent(String str) {
        Log.MiLogE("SET UA", str);
        this.mUserAgent = str;
    }

    public void setVoiceprintDeleteListener(VoiceprintDeleteListener voiceprintDeleteListener) {
        this.mVPDelListener = voiceprintDeleteListener;
    }

    public void setVoiceprintQueryListener(VoiceprintQueryListener voiceprintQueryListener) {
        this.mVPQryListener = voiceprintQueryListener;
    }

    public void setVoiceprintReconListener(VoiceprintRecognizeListener voiceprintRecognizeListener) {
        this.mVPListener = voiceprintRecognizeListener;
    }

    public void setVoiceprintRegistListener(VoiceprintRegisterListener voiceprintRegisterListener) {
        this.mVPRListener = voiceprintRegisterListener;
    }

    public final void speak(TtsRequest ttsRequest) {
        forceStop();
        doSpeak(ttsRequest);
    }

    public final void startIntegrally(ParamBuilder paramBuilder) {
        forceStop();
        doStartIntegrally(paramBuilder);
    }

    public abstract int startUploadASRRecord(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.ASRRecordInfo aSRRecordInfo, byte[] bArr);

    public abstract int startUploadWakeupData(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.WakeupDataInfo wakeupDataInfo, byte[] bArr);

    public abstract boolean updateAnonymousProxyArgs(String str, String str2, String str3, String str4, String str5, String str6);

    public void updateMiotAuth(String str, String str2) {
    }

    public void updateTPAuth(String str) {
    }

    public final void voicePrintRecognize(VoiceprintRecognizeRequest voiceprintRecognizeRequest) {
        forceStop();
        doVoiceprintRecognize(voiceprintRecognizeRequest);
    }

    public final void voiceprintRegister(VoiceprintRegisterRequest voiceprintRegisterRequest) {
        forceStop();
        doVoiceprintRegister(voiceprintRegisterRequest);
    }
}
