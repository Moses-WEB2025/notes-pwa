package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainRequest {
    private static final int BRAIN_ADD_DATA_FLAG_CONTINUE = 0;
    private static final int BRAIN_ADD_DATA_FLAG_TASK_END = 1;
    private static final int BRAIN_ADD_DATA_FLAG_VOR_REG_DIV = 2;
    public static final int MIBRAINSDK_ERR_BUFFERQUEUE = -7;
    public static final int MIBRAINSDK_ERR_ENDED = -5;
    public static final int MIBRAINSDK_ERR_HAS_INITIALIZED = -3;
    public static final int MIBRAINSDK_ERR_INIT_FAILED = -1;
    public static final int MIBRAINSDK_ERR_MEM_FAILED = -4;
    public static final int MIBRAINSDK_ERR_PARAMS = -6;
    public static final int MIBRAINSDK_ERR_RECVDATA = -9;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_ACTIVE_TIMEOUT = -20;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_AUTH_FAILED = -22;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_CONNECT_TIMEOUT = -10;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_CRT_ERROR = -14;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_GET_TOKEN_FAILED = -23;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_HANDSHAKE_ERROR = -13;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_INIT_FAILED_ERROR = -16;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_INIT_STATE_ERROR = -17;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_NETWORK_ERROR = -15;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_READ_DATA_ERROR = -11;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_READ_DATA_TIMEOUT = -18;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_SYSTEM_TIME_ERROR = -21;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_WRITE_DATA_ERROR = -12;
    public static final int MIBRAINSDK_ERR_TRANSMISSION_WRITE_DATA_TIMEOUT = -19;
    public static final int MIBRAINSDK_ERR_UNINITIALED = -2;
    public static final int MIBRAINSDK_ERR_UNKNOWN = -8;
    public static final int MIBRAINSDK_NEW_MODE_NORMAL = 0;
    public static final int MIBRAINSDK_NEW_MODE_PENDING_DATA = 1;
    public static final int MIBRAINSDK_OK = 0;
    public static final int MIBRAIN_IPC_BINARY_MSG = 1;
    public static final int MIBRAIN_IPC_TEXT_MSG = 4;
    public static final int MI_ERR_ERRORTYPE_LOCAL = 2;
    public static final int MI_ERR_ERRORTYPE_SERVICE = 1;
    public int mErrorCode;
    GetAnonymousAuthorizationInterface mInter;
    private long mNativeContext;
    private MibrainRequestListener mRequestListener;
    private MibrainRequestParams mRequestParams;
    private MibrainWakeupRequestParams mWakeupRequestParams;
    private int mode;
    private boolean mEnd = false;
    private MibrainRequestListener internalRequestListener = new MibrainRequestListener() { // from class: com.xiaomi.ai.mibrain.MibrainRequest.1
        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestSessionStart(MibrainRequest mibrainRequest) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestSessionStart(mibrainRequest);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestError(MibrainRequest mibrainRequest, String str, int i, int i2) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestError(mibrainRequest, str, i, i2);
            }
            MibrainRequest.this.mErrorCode = i2;
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public String onRequestGetToken(MibrainRequest mibrainRequest, boolean z) {
            if (MibrainRequest.this.mRequestListener != null) {
                return MibrainRequest.this.mRequestListener.onRequestGetToken(mibrainRequest, z);
            }
            return null;
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestOtherResult(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestOtherResult(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestNLpResult(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestNLpResult(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestASRResult(MibrainRequest mibrainRequest, String str, boolean z) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestASRResult(mibrainRequest, str, z);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestTTSResult(MibrainRequest mibrainRequest, String str, byte[] bArr, boolean z) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestTTSResult(mibrainRequest, str, bArr, z);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onInstruction(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onInstruction(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintRecognizedResult(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestVoiceprintRecognizedResult(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintRegisteredResult(MibrainRequest mibrainRequest, String str, boolean z) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestVoiceprintRegisteredResult(mibrainRequest, str, z);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintQueryResult(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestVoiceprintQueryResult(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintDeleteResult(MibrainRequest mibrainRequest, String str) {
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestVoiceprintDeleteResult(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestSessionEnd(MibrainRequest mibrainRequest) {
            synchronized (MibrainRequest.this) {
                if (MibrainRequest.this.mInter != null) {
                    MibrainRequest.this.mInter.onGetAnonymousAuthorization(MibrainRequest.this.mMibrainNativeRequest.getAnonymousAuthorization(MibrainRequest.this.mNativeContext));
                }
                MibrainRequest.this.mMibrainNativeRequest.deleteContext(MibrainRequest.this.mNativeContext);
                MibrainRequest.this.mEnd = true;
            }
            if (MibrainRequest.this.mRequestListener != null) {
                MibrainRequest.this.mRequestListener.onRequestSessionEnd(mibrainRequest);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public String onNeedUpdateToken() {
            return MibrainRequest.this.mRequestListener.onNeedUpdateToken();
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onEventTrack(String str) {
            MibrainRequest.this.mRequestListener.onEventTrack(str);
        }
    };
    private MibrainNativeRequest mMibrainNativeRequest = new MibrainNativeRequest(this.internalRequestListener, this);

    public void setGetAnonymousAuthorizationCallback(GetAnonymousAuthorizationInterface getAnonymousAuthorizationInterface) {
        this.mInter = getAnonymousAuthorizationInterface;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public MibrainRequest(MibrainRequestParams mibrainRequestParams) {
        this.mRequestParams = mibrainRequestParams;
    }

    public MibrainRequest(MibrainWakeupRequestParams mibrainWakeupRequestParams) {
        this.mWakeupRequestParams = mibrainWakeupRequestParams;
    }

    public static String getResourceDetails(String str, String str2, String str3, int i) {
        return MibrainNativeRequest.getResourceDetails(str, str2, str3, i);
    }

    public void updateRequestParams(MibrainRequestParams mibrainRequestParams) {
        this.mRequestParams = mibrainRequestParams;
    }

    public int startWakeupRequest(int i, int i2) {
        long jNewWakeUpRequestParams = this.mMibrainNativeRequest.newWakeUpRequestParams(i, i2);
        if (jNewWakeUpRequestParams == 0) {
            return -1;
        }
        int iSendWakeUpRequest = this.mMibrainNativeRequest.sendWakeUpRequest(jNewWakeUpRequestParams, this.mWakeupRequestParams);
        this.mMibrainNativeRequest.releaseWakeUpRequestParams(jNewWakeUpRequestParams);
        return iSendWakeUpRequest;
    }

    public long startUploadRequest(int i, int i2) {
        long jNewWakeUpRequestParams = this.mMibrainNativeRequest.newWakeUpRequestParams(i, i2);
        if (jNewWakeUpRequestParams == 0) {
            return -1L;
        }
        return this.mMibrainNativeRequest.startUploadRequest(jNewWakeUpRequestParams, this.mWakeupRequestParams);
    }

    public int addUploadData(long j, byte[] bArr, int i) {
        return this.mMibrainNativeRequest.addUploadData(j, bArr, i);
    }

    public int endUploadData(long j) {
        return this.mMibrainNativeRequest.endUploadData(j);
    }

    public void releaseUploadRequest(long j) {
        this.mMibrainNativeRequest.releaseWakeUpRequestParams(j);
    }

    public void setRequestListener(MibrainRequestListener mibrainRequestListener) {
        this.mRequestListener = mibrainRequestListener;
    }

    public synchronized String getAnonymousAuthorization() {
        return this.mMibrainNativeRequest.getAnonymousAuthorization(this.mNativeContext);
    }

    public synchronized int startRequest() {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext != 0) {
            return -3;
        }
        long jNewContext = this.mMibrainNativeRequest.newContext(this.mRequestParams, 0, 0L);
        this.mNativeContext = jNewContext;
        if (jNewContext == 0) {
            this.mEnd = true;
            return -4;
        }
        int iStartRequest = this.mMibrainNativeRequest.startRequest(jNewContext);
        if (iStartRequest != 0) {
            this.mEnd = true;
            this.mMibrainNativeRequest.deleteContext(this.mNativeContext);
        }
        return iStartRequest;
    }

    public synchronized int sendPendingData() {
        if (this.mEnd) {
            return -5;
        }
        long j = this.mNativeContext;
        if (j == 0) {
            return -5;
        }
        return (int) this.mMibrainNativeRequest.newContext(this.mRequestParams, 1, j);
    }

    public synchronized int addRawData(byte[] bArr, int i, boolean z, boolean z2) {
        if (this.mEnd) {
            return -5;
        }
        long j = this.mNativeContext;
        if (j == 0) {
            return -2;
        }
        return this.mMibrainNativeRequest.addDataRaw(j, i, bArr, bArr == null ? 0 : bArr.length, z ? 1 : 0, z2 ? 1 : 0);
    }

    public synchronized int addAudioData(byte[] bArr, boolean z) {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext == 0) {
            return -2;
        }
        if ((this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_TTS || this.mRequestParams.ttsSuperaddition != 0) && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS) {
            return this.mMibrainNativeRequest.addData(this.mNativeContext, bArr, bArr == null ? 0 : bArr.length, z ? 1 : 0);
        }
        return -6;
    }

    public synchronized int addAudioData(byte[] bArr, boolean z, boolean z2) {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext == 0) {
            return -2;
        }
        if ((this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_TTS || this.mRequestParams.ttsSuperaddition != 0) && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS) {
            int length = 0;
            int i = z ? 2 : 0;
            if (z2) {
                i = 1;
            }
            int i2 = i;
            MibrainNativeRequest mibrainNativeRequest = this.mMibrainNativeRequest;
            long j = this.mNativeContext;
            if (bArr != null) {
                length = bArr.length;
            }
            return mibrainNativeRequest.addData(j, bArr, length, i2);
        }
        return -6;
    }

    public synchronized int addAudioData(byte[] bArr, int i, boolean z) {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext == 0) {
            return -2;
        }
        if ((this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_TTS || this.mRequestParams.ttsSuperaddition != 0) && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS) {
            return this.mMibrainNativeRequest.addData(this.mNativeContext, bArr, i, z ? 1 : 0);
        }
        return -6;
    }

    public synchronized int addAudioData(byte[] bArr, int i, boolean z, boolean z2) {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext == 0) {
            return -2;
        }
        if ((this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_TTS || this.mRequestParams.ttsSuperaddition != 0) && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS) {
            int i2 = z ? 2 : 0;
            if (z2) {
                i2 = 1;
            }
            return this.mMibrainNativeRequest.addData(this.mNativeContext, bArr, i, i2);
        }
        return -6;
    }

    public synchronized int addNlpData(String str) {
        if (this.mEnd) {
            return -5;
        }
        if (this.mNativeContext == 0) {
            return -2;
        }
        if ((this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_TTS || this.mRequestParams.ttsSuperaddition != 0) && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_ASR_NLP_TTS && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_ASR_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_NLP_TTS && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_EVENT && this.mRequestParams.sdkCmd != MibrainNativeRequest.MIBRAINSDK_CMD_EVENT_TTS) {
            return -6;
        }
        return this.mMibrainNativeRequest.addNlpData(this.mNativeContext, str);
    }

    public synchronized void asyncStopRequest() {
        if (this.mEnd) {
            return;
        }
        long j = this.mNativeContext;
        if (j == 0) {
            return;
        }
        this.mMibrainNativeRequest.stopRequest(j, MibrainNativeRequest.MI_STOP_FLAG_ASYNC_STOP);
    }

    public synchronized void waitForResult() {
        if (this.mEnd) {
            return;
        }
        long j = this.mNativeContext;
        if (j == 0) {
            return;
        }
        this.mMibrainNativeRequest.stopRequest(j, MibrainNativeRequest.MI_STOP_FLAG_WAIT_RESULT);
    }
}
