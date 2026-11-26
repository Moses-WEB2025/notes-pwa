package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechError {
    public static final int ERROR_ASR_NULL = 12;
    public static final int ERR_ENGINE_EXECUTION_ERROR = 121;
    public static final int ERR_ENGINE_EXECUTION_TIMEOUT = 111;
    public static final int ERR_ENGINE_INVALID_INVALID_PARAM_END_WITHOUT_AUDIO = 102;
    public static final int ERR_ENGINE_INVALID_PARAMETER = 101;
    public static final int ERR_ENGINE_SERVICE_TIMEOUT = 131;
    public static final int ERR_LCOAL_STAGE = 3;
    public static final int ERR_LOCAL_BINARY_ERROR = 6;
    public static final int ERR_LOCAL_FAIL_TO_INIT_RECORDER = 5;
    public static final int ERR_LOCAL_JSON_PARSE = 2;
    public static final int ERR_LOCAL_NATIVE_BUFFERQUEUE = -7;
    public static final int ERR_LOCAL_NATIVE_ENDED = -5;
    public static final int ERR_LOCAL_NATIVE_HAS_INITIALIZED = -3;
    public static final int ERR_LOCAL_NATIVE_INIT_FAILED = -1;
    public static final int ERR_LOCAL_NATIVE_MEM_FAILED = -4;
    public static final int ERR_LOCAL_NATIVE_PARAMS = -6;
    public static final int ERR_LOCAL_NATIVE_RECVDATA = -9;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_ACTIVE_TIMEOUT = -20;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_AUTH_FAILED = -22;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_CONNECT_TIMEOUT = -10;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_CRT_ERROR = -14;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_GET_TOKEN_FAILED = -23;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_HANDSHAKE_ERROR = -13;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_INIT_FAILED_ERROR = -16;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_INIT_STATE_ERROR = -17;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_NETWORK_ERROR = -15;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_READ_DATA_ERROR = -11;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_READ_DATA_TIMEOUT = -18;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_SYSTEM_TIME_ERROR = -21;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_WRITE_DATA_ERROR = -12;
    public static final int ERR_LOCAL_NATIVE_TRANSMISSION_WRITE_DATA_TIMEOUT = -19;
    public static final int ERR_LOCAL_NATIVE_UNINITIALED = -2;
    public static final int ERR_LOCAL_NATIVE_UNKNOWN = -8;
    public static final int ERR_LOCAL_NETWORK = 13;
    public static final int ERR_LOCAL_RECORDING_ERROR = 8;
    public static final int ERR_LOCAL_TIME_OUT = 4;
    public static final int ERR_LOCAL_TTS_DOWNLOAD = 7;
    public static final int ERR_LOCAL_TTS_ENGINE = 14;
    public static final int ERR_LOCAL_TTS_INTERNAL = 15;
    public static final int ERR_RECORD_START_FAILED = 11;
    public static final int ERR_TYPE_ENGINE = 1;
    public static final int ERR_TYPE_LOCAL = 2;
    public static final int ERR_UNKNOWN = 1;
    public static final int ERR_VAD_INIT_FAIL = 10;
    int errCode;
    String errMsg;
    int errType;
    String requestId;
    SpeechEngine.ProcessStage stage;

    public SpeechError(SpeechEngine.ProcessStage processStage) {
        this.stage = processStage;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public int getErrType() {
        return this.errType;
    }

    public String toString() {
        return "stage = " + this.stage + " errType = " + this.errType + " errCode = " + this.errCode + " errMsg = " + this.errMsg + " requestId =" + this.requestId + "  ";
    }
}
