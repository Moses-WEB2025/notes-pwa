package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainOauthManager {
    public static int MIBRAIN_OAUTH_PT_DEV = 0;
    public static int MIBRAIN_OAUTH_PT_SHUIDI = 1;
    public static int MIBRAIN_OAUTH_UPDATE_TOKEN_BY_CODE = 3;
    public static int MIBRAIN_OAUTH_UPDATE_TOKEN_BY_REFRESHTOKEN = 2;
    private String mClientId;
    private String mClientSecret;
    private MibrainOauthManagerCallbacks mMibrainOauthManagerCallbacks;
    private long mNativeInstance;
    private String mRedirectUrl;
    private String mScopeData;
    private updateTokenCallback updateCallback;

    public static class AccessTokenResult {
        public String accessToken;
        public int errorCode;
        public boolean isfromCode;
    }

    public interface updateTokenCallback {
        void onUpdatedToken(int i, String str);
    }

    private native String genAnonymousAuthorization(long j, String str, String str2, String str3);

    private native void getAccessToken(long j, AccessTokenResult accessTokenResult, int i);

    private native int getErrorCode(long j);

    private native int init(long j, String str, String str2, String str3, String str4);

    private native long newInstance();

    private native void releaseInstance(long j);

    private native int setAnonymousAuth(long j, String str, String str2, String str3, int i);

    private native void setEnv(long j, int i);

    private native void setPt(long j, int i);

    private native void setTimeout(long j, int i);

    private native void unInit(long j);

    private native int updateAnonymousArgs(long j, String str, String str2, String str3, String str4);

    protected String getOauthData(String str) {
        return this.mMibrainOauthManagerCallbacks.getOauthData(str);
    }

    protected boolean putOauthData(String str, String str2) {
        return this.mMibrainOauthManagerCallbacks.putOauthData(str, str2);
    }

    protected String getOauthCode() {
        return this.mMibrainOauthManagerCallbacks.getOauthCode();
    }

    protected void updateToken(int i, String str) {
        updateTokenCallback updatetokencallback = this.updateCallback;
        if (updatetokencallback != null) {
            updatetokencallback.onUpdatedToken(i, str);
        }
    }

    public void setUpdateTokenCallback(updateTokenCallback updatetokencallback) {
        this.updateCallback = updatetokencallback;
    }

    static {
        Mibrainsdk.loadMibrainLibs();
    }

    public String getClientId() {
        return this.mClientId;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public String getcopeData() {
        return this.mScopeData;
    }

    public String getClientSecret() {
        return this.mClientSecret;
    }

    public MibrainOauthManager(MibrainOauthManagerCallbacks mibrainOauthManagerCallbacks, String str, String str2, String str3, String str4) {
        this.mRedirectUrl = str;
        this.mClientSecret = str2;
        this.mClientId = str3;
        this.mScopeData = str4;
        this.mMibrainOauthManagerCallbacks = mibrainOauthManagerCallbacks;
    }

    public void init() {
        long jNewInstance = newInstance();
        this.mNativeInstance = jNewInstance;
        if (jNewInstance == 0) {
            throw new RuntimeException("newInstance failed ");
        }
        if (init(jNewInstance, this.mRedirectUrl, this.mClientSecret, this.mClientId, this.mScopeData) != 0) {
            throw new IllegalArgumentException("init failed,please check params");
        }
    }

    public int getErrorCode() {
        long j = this.mNativeInstance;
        if (j != 0) {
            return getErrorCode(j);
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public AccessTokenResult getAccessToken(boolean z) {
        if (this.mNativeInstance != 0) {
            AccessTokenResult accessTokenResult = new AccessTokenResult();
            getAccessToken(this.mNativeInstance, accessTokenResult, z ? 1 : 0);
            return accessTokenResult;
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public void setOauthEnv(int i) {
        long j = this.mNativeInstance;
        if (j != 0) {
            setEnv(j, i);
            return;
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public void setOauthPt(int i) {
        long j = this.mNativeInstance;
        if (j != 0) {
            setPt(j, i);
            return;
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public void setNetTimeOut(int i) {
        long j = this.mNativeInstance;
        if (j != 0) {
            setTimeout(j, i);
            return;
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public void release() {
        long j = this.mNativeInstance;
        if (j != 0) {
            unInit(j);
            releaseInstance(this.mNativeInstance);
            this.mNativeInstance = 0L;
            return;
        }
        throw new IllegalStateException("MibrainOauthManager not init");
    }

    public int setAnonymousAuth(String str, String str2, String str3, int i) {
        long j = this.mNativeInstance;
        if (j == 0) {
            throw new RuntimeException("newInstance failed ");
        }
        return setAnonymousAuth(j, str, str2, str3, i);
    }

    public int updateAnonymousArgs(String str, String str2, String str3, String str4, String str5, String str6) {
        if (str == null || str2 == null || str3 == null || str5 == null || str6 == null) {
            return -1;
        }
        String str7 = String.format("%s;%s;%s", str4, str5.toLowerCase(), str6.toLowerCase());
        long j = this.mNativeInstance;
        if (j == 0) {
            throw new RuntimeException("newInstance failed ");
        }
        return updateAnonymousArgs(j, str, str2, str3, str7);
    }

    public String genAnonymousAuthorization(String str, String str2, String str3) {
        if (str == null || str2 == null || str3 == null) {
            return null;
        }
        long j = this.mNativeInstance;
        if (j == 0) {
            throw new RuntimeException("newInstance failed ");
        }
        return genAnonymousAuthorization(j, str, str2, str3);
    }
}
