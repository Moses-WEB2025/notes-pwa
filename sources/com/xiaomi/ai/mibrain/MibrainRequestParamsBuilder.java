package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainRequestParamsBuilder {
    public static int MI_CMD_ASR_NLP = MibrainNativeRequest.MIBRAINSDK_CMD_ASR_NLP;
    public static int MI_CMD_ASR = MibrainNativeRequest.MIRBAINSDK_CMD_ASR;
    public static int MI_CMD_ASR_NLP_TTS = MibrainNativeRequest.MIBRAINSDK_CMD_ASR_NLP_TTS;
    public static int MI_CMD_TTS = MibrainNativeRequest.MIBRAINSDK_CMD_TTS;
    public static int MI_CMD_NLP = MibrainNativeRequest.MIBRAINSDK_CMD_NLP;
    public static int MI_CMD_NLP_TTS = MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS;
    public static int MI_CMD_VOR_REG = MibrainNativeRequest.MIBRAINSDK_CMD_VOR_REG;
    public static int MI_CMD_VOR = MibrainNativeRequest.MIBRAINSDK_CMD_VOR;
    public static int MI_CMD_VOR_DELETE = MibrainNativeRequest.MIBRAINSDK_CMD_VOR_DEL;
    public static int MI_CMD_VOR_QUERY = MibrainNativeRequest.MIBRAINSDK_CMD_VOR_QUERY;
    public static int MI_CMD_EVENT = MibrainNativeRequest.MIBRAINSDK_CMD_EVENT;
    public static int MI_CMD_EVENT_TTS = MibrainNativeRequest.MIBRAINSDK_CMD_EVENT_TTS;
    public static int EVN_PRODUCTION = 0;
    public static int EVN_PREVIEW = 1;
    public static int EVN_STAGING = 2;
    public static int EVN_PRODUCTION_SGP = 3;
    public static int MIBRAIN_AUTH_APPIDTOKEN = 0;
    public static int MIBRAIN_AUTH_MIAITOKEN = 1;
    public static int MIBRAIN_AUTH_MIOTTOKEN = 2;
    public static int MIBRAIN_AUTH_TPTOKEN = 3;
    public static int MIBRAIN_AUTH_MIAOTOKEN = 4;
    public static int MIBRAIN_AUTH_ANONYMOUS = 5;
    public static int MIBRAIN_AUTH_ANONYMOUS_PROXY = 6;
    public static int MIBRAIN_AUTH_DS_SIGNATURE = 7;
    private static int MIBRAINSDK_INTERNAL_VAD_NONE = 0;
    private static int MIBRAINSDK_INTERNAL_VAD_LOCAL = 1;
    private static int MIBRAINSDK_INTERNAL_VAD_CLOUD = 2;
    private VadMode mLocalVad = VadMode.VAD_MODE_LOCAL;
    private boolean mVadOpen = false;
    private MibrainRequestParams mMibrainRequestParams = new MibrainRequestParams();

    public enum VadMode {
        VAD_MODE_LOCAL,
        VAD_MODE_CLOUD
    }

    public MibrainRequestParamsBuilder setNeedWakeupData(boolean z) {
        this.mMibrainRequestParams.needWakeupData = z ? 1 : 0;
        return this;
    }

    public MibrainRequestParamsBuilder setWakeupInfo(String str) {
        this.mMibrainRequestParams.wakeupInfo = str;
        return this;
    }

    public MibrainRequestParamsBuilder setAsrRemoveEndPunc(boolean z) {
        this.mMibrainRequestParams.asrRemoveEndPunc = z ? 1 : 0;
        return this;
    }

    public MibrainRequestParamsBuilder setIsPendingSend(int i) {
        this.mMibrainRequestParams.isPendingSend = i;
        return this;
    }

    public MibrainRequestParamsBuilder setToken(String str) {
        this.mMibrainRequestParams.appToken = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventLevel(int i) {
        this.mMibrainRequestParams.eventLevel = i;
        return this;
    }

    public MibrainRequestParamsBuilder setApiKey(String str) {
        this.mMibrainRequestParams.apiKey = str;
        return this;
    }

    public MibrainRequestParamsBuilder setAppId(String str) {
        this.mMibrainRequestParams.appId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setMaxEmptyAudioTime(int i) {
        this.mMibrainRequestParams.maxEmptyAudioTime = i;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintUserId(String str) {
        this.mMibrainRequestParams.voiceprintUserId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintVendor(String str) {
        this.mMibrainRequestParams.voiceprintVendor = str;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceId(int i) {
        this.mMibrainRequestParams.voiceId = i;
        return this;
    }

    public MibrainRequestParamsBuilder setAudioBitNum(int i) {
        this.mMibrainRequestParams.audioBitNum = i;
        return this;
    }

    public MibrainRequestParamsBuilder setRequestCmd(int i) {
        this.mMibrainRequestParams.sdkCmd = i;
        return this;
    }

    public MibrainRequestParamsBuilder setSampleRate(int i) {
        this.mMibrainRequestParams.sampleRate = i;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintFamilyId(String str) {
        this.mMibrainRequestParams.voiceprintFamilyId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setAsrLang(String str) {
        this.mMibrainRequestParams.asrLang = str;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintDeleteIds(String str) {
        this.mMibrainRequestParams.voiceprintDeleteIds = str;
        return this;
    }

    public MibrainRequestParamsBuilder setChannels(int i) {
        this.mMibrainRequestParams.channels = i;
        return this;
    }

    public MibrainRequestParamsBuilder setReceiverTimeOut(int i) {
        this.mMibrainRequestParams.receiveTimeout = i;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPSpeechData(String str) {
        this.mMibrainRequestParams.speechData = str;
        return this;
    }

    public MibrainRequestParamsBuilder setProtocolVersion(String str) {
        this.mMibrainRequestParams.protocolVersion = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSTimeout(int i) {
        this.mMibrainRequestParams.ttsTimeout = i;
        return this;
    }

    public MibrainRequestParamsBuilder setConnectTimeOut(int i) {
        this.mMibrainRequestParams.connectTimeOut = i;
        return this;
    }

    public MibrainRequestParamsBuilder setUseInternalVadFlag(boolean z) {
        this.mVadOpen = z;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPContext(String str) {
        this.mMibrainRequestParams.nlpContext = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPSession(String str) {
        this.mMibrainRequestParams.nlpSession = str;
        return this;
    }

    public MibrainRequestParamsBuilder setDevice(String str) {
        this.mMibrainRequestParams.device = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSText(String str) {
        this.mMibrainRequestParams.ttsText = str;
        return this;
    }

    public MibrainRequestParamsBuilder setDeviceId(String str) {
        this.mMibrainRequestParams.deviceId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSSpeed(int i) {
        this.mMibrainRequestParams.ttsSpeed = i;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSSpeaker(int i) {
        this.mMibrainRequestParams.ttsSpeaker = i;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSTone(int i) {
        this.mMibrainRequestParams.ttsTone = i;
        return this;
    }

    public MibrainRequestParamsBuilder setNeedExtraNLP(int i) {
        this.mMibrainRequestParams.needExtraNlp = i;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSLang(String str) {
        this.mMibrainRequestParams.ttsLang = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSAdditional(boolean z) {
        this.mMibrainRequestParams.ttsSuperaddition = z ? 1 : 0;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoAge(int i) {
        this.mMibrainRequestParams.userInfoAge = i;
        return this;
    }

    public MibrainRequestParamsBuilder setUserAgent(String str) {
        this.mMibrainRequestParams.userAgent = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoId(String str) {
        this.mMibrainRequestParams.userInfoId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoGender(String str) {
        this.mMibrainRequestParams.userInfoGender = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoIp(String str) {
        this.mMibrainRequestParams.userInfoIp = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoIdType(String str) {
        this.mMibrainRequestParams.userInfoIdType = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPUserInfoExtend(String str) {
        this.mMibrainRequestParams.userInfoExtend = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSVendor(String str) {
        this.mMibrainRequestParams.ttsVendor = str;
        return this;
    }

    public MibrainRequestParamsBuilder setNLPText(String str) {
        this.mMibrainRequestParams.nlpText = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSVolume(int i) {
        this.mMibrainRequestParams.ttsVolume = i;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSCodec(String str) {
        this.mMibrainRequestParams.ttsCodec = str;
        return this;
    }

    public MibrainRequestParamsBuilder setASRVendor(String str) {
        this.mMibrainRequestParams.asrVendor = str;
        return this;
    }

    public MibrainRequestParamsBuilder setCustomNLPVersion(String str) {
        this.mMibrainRequestParams.nlpVersion = str;
        return this;
    }

    public MibrainRequestParamsBuilder setCustomNLPApi(String str) {
        this.mMibrainRequestParams.customNlPApi = str;
        return this;
    }

    public MibrainRequestParamsBuilder setCustomNLPAppId(String str) {
        this.mMibrainRequestParams.customNlPAppID = str;
        return this;
    }

    public MibrainRequestParamsBuilder setCustomNLPAppToken(String str) {
        this.mMibrainRequestParams.customNlPAppToKen = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEnv(int i) {
        this.mMibrainRequestParams.env = i;
        return this;
    }

    public MibrainRequestParamsBuilder setVadTypeLocal(VadMode vadMode) {
        this.mLocalVad = vadMode;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintQueries(String str) {
        this.mMibrainRequestParams.voiceprintQueries = str;
        return this;
    }

    public MibrainRequestParamsBuilder setIsForSai(boolean z) {
        this.mMibrainRequestParams.isForSai = z ? 1 : 0;
        return this;
    }

    public MibrainRequestParamsBuilder setAuthMode(int i) {
        this.mMibrainRequestParams.authMode = i;
        return this;
    }

    public MibrainRequestParamsBuilder setScopeData(String str) {
        this.mMibrainRequestParams.scopeData = str;
        return this;
    }

    public MibrainRequestParamsBuilder setPreAsrTrack(int i) {
        this.mMibrainRequestParams.preAsrTrack = i;
        return this;
    }

    public MibrainRequestParamsBuilder setCodecMode(short s) {
        this.mMibrainRequestParams.codecMode = s;
        return this;
    }

    public MibrainRequestParamsBuilder setOpusFrameSize(int i) {
        this.mMibrainRequestParams.opusFrameSize = i;
        return this;
    }

    public MibrainRequestParamsBuilder setOpusBitRates(int i) {
        this.mMibrainRequestParams.opusBitRates = i;
        return this;
    }

    public MibrainRequestParamsBuilder setRequestId(String str) {
        this.mMibrainRequestParams.requestId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setTTSAsFileUrl(int i) {
        this.mMibrainRequestParams.ttsAsFileUrl = i;
        return this;
    }

    public MibrainRequestParamsBuilder setEventNameSpace(String str) {
        this.mMibrainRequestParams.eventNameSpace = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventName(String str) {
        this.mMibrainRequestParams.eventName = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventPayload(String str) {
        this.mMibrainRequestParams.eventPayload = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventSessionId(String str) {
        this.mMibrainRequestParams.eventSessionId = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventContextNameSpace(String str) {
        this.mMibrainRequestParams.eventContextNameSpace = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventContextName(String str) {
        this.mMibrainRequestParams.eventContextName = str;
        return this;
    }

    public MibrainRequestParamsBuilder setEventContextPayload(String str) {
        this.mMibrainRequestParams.eventContextPayload = str;
        return this;
    }

    public MibrainRequestParamsBuilder setASRDialect(String str) {
        this.mMibrainRequestParams.asrDialect = str;
        return this;
    }

    public MibrainRequestParamsBuilder setASRDisableTimeout(int i) {
        this.mMibrainRequestParams.asrDisableTimeout = i;
        return this;
    }

    public MibrainRequestParamsBuilder setASRAudioMaxTime(int i) {
        this.mMibrainRequestParams.asrAudioMaxTime = i;
        return this;
    }

    public MibrainRequestParamsBuilder setStartEventTrack(int i) {
        this.mMibrainRequestParams.startEventTrack = i;
        return this;
    }

    public MibrainRequestParamsBuilder setEventTrackReceiver(int i) {
        this.mMibrainRequestParams.eventTrackReceiver = i;
        return this;
    }

    public MibrainRequestParamsBuilder setUseExternalOAuthToken(int i) {
        this.mMibrainRequestParams.useExternalOAuthToken = i;
        return this;
    }

    public MibrainRequestParamsBuilder setVoiceprintDisableTextDependent(int i) {
        this.mMibrainRequestParams.vorDisableTextDependent = i;
        return this;
    }

    public MibrainRequestParamsBuilder setUploadTimeout(int i) {
        this.mMibrainRequestParams.uploadTimeout = i;
        return this;
    }

    public MibrainRequestParamsBuilder setActiveTimeout(int i) {
        this.mMibrainRequestParams.activeTimeout = i;
        return this;
    }

    public MibrainRequestParams buid() throws MibrainException {
        if (this.mMibrainRequestParams.appId == null) {
            throw new MibrainException("appid can't be null!");
        }
        if (this.mMibrainRequestParams.sdkCmd != MI_CMD_ASR_NLP && this.mMibrainRequestParams.sdkCmd != MI_CMD_ASR_NLP_TTS && this.mMibrainRequestParams.sdkCmd != MI_CMD_ASR && this.mMibrainRequestParams.sdkCmd != MI_CMD_TTS && this.mMibrainRequestParams.sdkCmd != MI_CMD_NLP && this.mMibrainRequestParams.sdkCmd != MI_CMD_NLP_TTS && this.mMibrainRequestParams.sdkCmd != MI_CMD_VOR && this.mMibrainRequestParams.sdkCmd != MI_CMD_VOR_REG && this.mMibrainRequestParams.sdkCmd != MI_CMD_VOR_DELETE && this.mMibrainRequestParams.sdkCmd != MI_CMD_VOR_QUERY && this.mMibrainRequestParams.sdkCmd != MI_CMD_EVENT && this.mMibrainRequestParams.sdkCmd != MI_CMD_EVENT_TTS) {
            throw new MibrainException("RequestCmd error");
        }
        if (this.mMibrainRequestParams.sdkCmd == MI_CMD_TTS && this.mMibrainRequestParams.ttsText == null) {
            throw new MibrainException("cmd = MI_CMD_TTS but text input = null");
        }
        if (this.mMibrainRequestParams.scopeData == null && this.mMibrainRequestParams.authMode == MIBRAIN_AUTH_MIAITOKEN) {
            throw new MibrainException("mMibrainRequestParams.scopeData = null but authMode = MIBRAIN_AUTH_MIAITOKEN ");
        }
        if (this.mMibrainRequestParams.sdkCmd == MI_CMD_NLP && this.mMibrainRequestParams.nlpText == null && this.mMibrainRequestParams.isPendingSend != 1) {
            throw new MibrainException("cmd = MI_CMD_NLP but nlpText input = null");
        }
        if (this.mMibrainRequestParams.sdkCmd == MI_CMD_NLP_TTS && this.mMibrainRequestParams.nlpText == null) {
            throw new MibrainException("cmd = MI_CMD_NLP_TTS but nlpText input = null");
        }
        if (this.mVadOpen) {
            if (this.mLocalVad == VadMode.VAD_MODE_LOCAL) {
                this.mMibrainRequestParams.useInternalVad = MIBRAINSDK_INTERNAL_VAD_LOCAL;
            } else {
                this.mMibrainRequestParams.useInternalVad = MIBRAINSDK_INTERNAL_VAD_CLOUD;
            }
        }
        return this.mMibrainRequestParams;
    }
}
