package com.xiaomi.micloudsdk.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import cn.kuaipan.android.kss.transferclient.controller.impl.ThumbnailUploadController;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import miui.cloud.log.PrivacyFilter;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class MiCloudFileRequestor<T extends FileTransferContext> implements IFileRequestor<T> {
    private static final String TAG = "MiCloudFileRequestor";

    protected Map<String, String> getCommitUploadHeaders(T t) {
        return null;
    }

    protected abstract Map<String, String> getCommitUploadParams(T t, CommitParameter commitParameter) throws JSONException, E2EEException;

    protected abstract String getCommitUploadURL(T t, CommitParameter commitParameter);

    protected abstract JSONObject getDownloadJson(T t, JSONObject jSONObject) throws JSONException;

    protected Map<String, String> getRequestDownloadHeaders(T t) {
        return null;
    }

    protected abstract Map<String, String> getRequestDownloadParams(T t) throws JSONException;

    protected abstract String getRequestDownloadURL(T t);

    protected Map<String, String> getRequestUploadHeaders(T t) {
        return null;
    }

    protected abstract Map<String, String> getRequestUploadParams(T t, RequestUploadParameter requestUploadParameter) throws JSONException, E2EEException, IOException;

    protected abstract String getRequestUploadURL(T t, RequestUploadParameter requestUploadParameter);

    protected abstract ThumbnailUploadController getThumbnailUploadController(T t);

    protected abstract T handleCommitUploadResult(T t, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException;

    protected abstract boolean handleRequestDownloadResultJson(T t, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException;

    protected abstract T handleRequestUploadResultJson(T t, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException;

    protected abstract boolean needEncryptFile(T t);

    protected abstract boolean needUploadLargeThumbnail(T t);

    protected abstract boolean needUploadSmallThumbnail(T t);

    public MiCloudFileRequestor(Context context) {
        Request.init(context);
    }

    @Override // com.xiaomi.micloudsdk.file.IFileRequestor
    public JSONObject requestUpload(T t, RequestUploadParameter requestUploadParameter) throws AuthenticationException, RetriableException, UnretriableException {
        String requestUploadURL = getRequestUploadURL(t, requestUploadParameter);
        if (TextUtils.isEmpty(requestUploadURL)) {
            throw new UnretriableException("requestUploadUrl is null or empty.");
        }
        try {
            try {
                Map<String, String> requestUploadParams = getRequestUploadParams(t, requestUploadParameter);
                if (requestUploadParams == null) {
                    throw new UnretriableException("getRequestUploadParams() can't return null.");
                }
                Map<String, String> requestUploadHeaders = getRequestUploadHeaders(t);
                if (requestUploadHeaders == null) {
                    requestUploadHeaders = new HashMap<>();
                }
                requestUploadHeaders.put("X-SUPPORT-HTTPS", "true");
                try {
                    return new JSONObject(getHttpResopnse(requestUploadURL, requestUploadHeaders, requestUploadParams, true));
                } catch (JSONException e) {
                    throw new UnretriableException("Http 200 返回的不是JSON格式:" + e);
                }
            } catch (E2EEException e2) {
                throw new UnretriableException(e2, "error in getRequestUploadParams");
            }
        } catch (IOException | JSONException e3) {
            throw new UnretriableException("error in getRequestUploadParams():" + e3);
        }
    }

    @Override // com.xiaomi.micloudsdk.file.IFileRequestor
    public JSONObject commitUpload(T t, CommitParameter commitParameter) throws AuthenticationException, RetriableException, UnretriableException {
        String commitUploadURL = getCommitUploadURL(t, commitParameter);
        if (TextUtils.isEmpty(commitUploadURL)) {
            throw new UnretriableException("commitUploadUrl is null or empty.");
        }
        try {
            try {
                Map<String, String> commitUploadParams = getCommitUploadParams(t, commitParameter);
                if (commitUploadParams == null) {
                    throw new UnretriableException("getCommitUploadParams() can't return null.");
                }
                try {
                    return new JSONObject(getHttpResopnse(commitUploadURL, getCommitUploadHeaders(t), commitUploadParams, true));
                } catch (JSONException e) {
                    throw new UnretriableException("Http 200 返回的不是JSON格式:" + e);
                }
            } catch (JSONException e2) {
                throw new UnretriableException("error in getCommitUploadParams():" + e2);
            }
        } catch (E2EEException e3) {
            throw new UnretriableException(e3, "error when getCommitUploadParams");
        }
    }

    @Override // com.xiaomi.micloudsdk.file.IFileRequestor
    public JSONObject requestDownload(T t) throws AuthenticationException, RetriableException, UnretriableException {
        String requestDownloadURL = getRequestDownloadURL(t);
        if (TextUtils.isEmpty(requestDownloadURL)) {
            throw new UnretriableException("requestDownloadUrl is null or empty.");
        }
        try {
            Map<String, String> requestDownloadParams = getRequestDownloadParams(t);
            if (requestDownloadParams == null) {
                throw new UnretriableException("getRequestDownloadParams() can't return null.");
            }
            Map<String, String> requestDownloadHeaders = getRequestDownloadHeaders(t);
            if (requestDownloadHeaders == null) {
                requestDownloadHeaders = new HashMap<>();
            }
            requestDownloadHeaders.put("X-SUPPORT-HTTPS", "true");
            try {
                return new JSONObject(getHttpResopnse(requestDownloadURL, requestDownloadHeaders, requestDownloadParams, false));
            } catch (JSONException e) {
                throw new UnretriableException("Http 200 返回的不是JSON格式:" + e);
            }
        } catch (JSONException e2) {
            throw new UnretriableException("error in getRequestDownloadParams():" + e2);
        }
    }

    private String getHttpResopnse(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AuthenticationException, RetriableException, UnretriableException {
        try {
            return z ? Request.securePost(str, map, map2, null) : Request.secureGet(str, map, map2, null);
        } catch (CloudServerException e) {
            int statusCode = e.getStatusCode();
            if (statusCode == 401 || statusCode == 403) {
                throw new AuthenticationException("status code: " + statusCode);
            }
            if (statusCode != 500) {
                switch (statusCode) {
                    case 502:
                    case 503:
                    case 504:
                        break;
                    default:
                        throw new UnretriableException("requestServer error: " + e);
                }
            }
            throw new RetriableException("requestServer error: " + e, e.retryTime);
        } catch (UnsupportedEncodingException e2) {
            Log.e(TAG, "requestServer error: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e2)));
            throw new UnretriableException("requestServer error:" + e2);
        } catch (IllegalBlockSizeException e3) {
            Log.e(TAG, "requestServer error: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e3)));
            throw new UnretriableException("requestServer error:" + e3);
        } catch (ClientProtocolException e4) {
            Log.e(TAG, "requestServer error: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e4)));
            throw new UnretriableException("requestServer error:" + e4);
        } catch (IOException e5) {
            Log.e(TAG, "requestServer error: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e5)));
            if (RetriableException.isRetriableException(e5)) {
                throw new RetriableException("IOException:" + e5, DesktopRecommendInfo.DEFAULT_CACHE_TIME);
            }
            throw new UnretriableException("requestServer error:" + e5);
        } catch (BadPaddingException e6) {
            Log.e(TAG, "requestServer error: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e6)));
            throw new UnretriableException("requestServer error:" + e6);
        }
    }
}
