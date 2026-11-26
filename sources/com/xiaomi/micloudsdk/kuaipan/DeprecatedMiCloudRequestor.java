package com.xiaomi.micloudsdk.kuaipan;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.ErrorHelper;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.ServerException;
import cn.kuaipan.android.exception.ServerMsgException;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.http.KscHttpRequest;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.kss.IKssRequestor;
import cn.kuaipan.android.kss.KssMaster;
import cn.kuaipan.android.kss.KssMaster.IRemote;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import cn.kuaipan.android.utils.ApiDataHelper;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.CloudRelocationUtils;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.micloudsdk.stat.MiCloudNetEventStatInjector;
import com.xiaomi.micloudsdk.stat.MiCloudStatManager;
import com.xiaomi.micloudsdk.stat.NetFailedStatParam;
import com.xiaomi.micloudsdk.stat.NetSuccessStatParam;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.file.sdk.FileCommitResult;
import com.xiaomi.opensdk.file.sdk.FileDownloadRequestResult;
import com.xiaomi.opensdk.file.sdk.FileUploadRequestResult;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public abstract class DeprecatedMiCloudRequestor<T extends KssMaster.IRemote> implements IKssRequestor<T> {
    public static final String JSON_TAG_DATA = "data";
    public static final String JSON_TAG_SIGNATURE = "signature";
    public static final String KEY_ENCRYPTED_USER_ID = "encrypted_user_id";
    private static final String TAG = "MiCloudRequestor";
    protected Account mAccount;
    private Context mContext;
    protected ExtendedAuthToken mExtToken;
    private final KscHttpTransmitter mTransmitter;

    protected HashMap<String, String> getCommitUploadParams(T t, KssUploadInfo kssUploadInfo) throws InterruptedException, KscException {
        return null;
    }

    protected abstract String getCommitUploadPostString(T t, KssUploadInfo kssUploadInfo) throws JSONException;

    protected abstract String getCommitUploadURL(T t, KssUploadInfo kssUploadInfo);

    protected HashMap<String, String> getRequestDownloadParams(T t) throws InterruptedException, KscException {
        return null;
    }

    protected String getRequestDownloadPostString(T t) {
        return null;
    }

    protected abstract String getRequestDownloadURL(T t);

    protected HashMap<String, String> getRequestUploadParams(T t, UploadFileInfo uploadFileInfo) throws InterruptedException, KscException {
        return null;
    }

    protected abstract String getRequestUploadPostString(T t, UploadFileInfo uploadFileInfo) throws JSONException;

    protected abstract String getRequestUploadURL(T t);

    protected abstract JSONObject handleCommitUploadResult(JSONObject jSONObject, T t) throws JSONException;

    protected abstract JSONObject handleRequestDownloadResultJson(JSONObject jSONObject, T t) throws JSONException;

    protected abstract JSONObject handleRequestUploadResultJson(JSONObject jSONObject, T t) throws JSONException;

    public DeprecatedMiCloudRequestor(Context context, Account account, ExtendedAuthToken extendedAuthToken) {
        KscHttpTransmitter kscHttpTransmitter = new KscHttpTransmitter(context);
        this.mTransmitter = kscHttpTransmitter;
        kscHttpTransmitter.setUserAgent(2, getUserAgent());
        this.mAccount = account;
        this.mExtToken = extendedAuthToken;
        this.mContext = context;
    }

    private static String getUserAgent() {
        return Request.getUserAgent();
    }

    @Override // cn.kuaipan.android.kss.IKssRequestor
    public FileUploadRequestResult requestUpload(File file, T t, UploadFileInfo uploadFileInfo) throws CloudServerException, InterruptedException, KscException {
        String requestUploadURL = getRequestUploadURL(t);
        if (TextUtils.isEmpty(requestUploadURL)) {
            throw new KscException(ErrorCode.INVALID_DATA, "requestUploadUrl is null or empty.", TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
        }
        try {
            JSONObject jSONObjectExec = exec(new ApiConfig(KscHttpRequest.HttpMethod.POST, requestUploadURL), getRequestUploadParams(t, uploadFileInfo), getRequestUploadPostString(t, uploadFileInfo), TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
            JSONObject jSONObjectHandleRequestUploadResultJson = handleRequestUploadResultJson(jSONObjectExec, t);
            if (jSONObjectHandleRequestUploadResultJson == null || jSONObjectHandleRequestUploadResultJson.length() <= 0) {
                throw new KscException(ErrorCode.INVALID_DATA, "kssJson is null or empty, result:" + jSONObjectExec.toString(), TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
            }
            return (FileUploadRequestResult) ApiDataHelper.parser(jSONObjectExec, FileSDKUtils.contentKssJsonToMap(jSONObjectHandleRequestUploadResultJson, TransferStep.UPLOAD_REQUEST_BIZ_HTTP), FileUploadRequestResult.class, TransferStep.UPLOAD_REQUEST_BIZ_HTTP, new String[0]);
        } catch (JSONException e) {
            throw KscException.newException(e, "requestUpload failed.", TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
        }
    }

    @Override // cn.kuaipan.android.kss.IKssRequestor
    public void commitUpload(File file, T t, KssUploadInfo kssUploadInfo) throws CloudServerException, InterruptedException, KscException {
        if (kssUploadInfo == null) {
            Log.e(TAG, "UploadResult is null.");
            return;
        }
        String commitUploadURL = getCommitUploadURL(t, kssUploadInfo);
        if (TextUtils.isEmpty(commitUploadURL)) {
            throw new KscException(ErrorCode.INVALID_DATA, "commitUrl is null or empty.", TransferStep.UPLOAD_REQUEST_COMMIT);
        }
        try {
            JSONObject jSONObjectExec = exec(new ApiConfig(KscHttpRequest.HttpMethod.POST, commitUploadURL), getCommitUploadParams(t, kssUploadInfo), getCommitUploadPostString(t, kssUploadInfo), TransferStep.UPLOAD_REQUEST_COMMIT);
            JSONObject jSONObjectHandleCommitUploadResult = handleCommitUploadResult(jSONObjectExec, t);
            if (jSONObjectHandleCommitUploadResult == null || jSONObjectHandleCommitUploadResult.length() <= 0) {
                throw new KscException(ErrorCode.INVALID_DATA, "kssJson is null or empty, result:" + jSONObjectExec.toString(), TransferStep.UPLOAD_REQUEST_COMMIT);
            }
            FileCommitResult fileCommitResult = (FileCommitResult) ApiDataHelper.parser(jSONObjectExec, FileSDKUtils.contentKssJsonToMap(jSONObjectHandleCommitUploadResult, TransferStep.UPLOAD_REQUEST_COMMIT), FileCommitResult.class, TransferStep.UPLOAD_REQUEST_COMMIT, new String[0]);
            if ("OK".equalsIgnoreCase(fileCommitResult.stat)) {
                return;
            }
            requestUpload(file, (KssMaster.IRemote) t, kssUploadInfo.getFileInfo());
            throw new ServerMsgException(200, fileCommitResult.stat, TransferStep.UPLOAD_REQUEST_COMMIT);
        } catch (JSONException e) {
            throw KscException.newException(e, "commitUpload failed.", TransferStep.UPLOAD_REQUEST_COMMIT);
        }
    }

    @Override // cn.kuaipan.android.kss.IKssRequestor
    public FileDownloadRequestResult requestDownload(T t) throws CloudServerException, InterruptedException, KscException {
        String requestDownloadURL = getRequestDownloadURL(t);
        if (TextUtils.isEmpty(requestDownloadURL)) {
            throw new KscException(ErrorCode.INVALID_DATA, "requestDownloadUrl is null or empty.", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
        }
        JSONObject jSONObjectExec = exec(new ApiConfig(KscHttpRequest.HttpMethod.GET, requestDownloadURL), getRequestDownloadParams(t), getRequestDownloadPostString(t), TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
        try {
            JSONObject jSONObjectHandleRequestDownloadResultJson = handleRequestDownloadResultJson(jSONObjectExec, t);
            if (jSONObjectHandleRequestDownloadResultJson == null || jSONObjectHandleRequestDownloadResultJson.length() <= 0) {
                throw new KscException(ErrorCode.INVALID_DATA, "kssJson is null or empty, result:" + jSONObjectExec.toString(), TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
            }
            return (FileDownloadRequestResult) ApiDataHelper.parser(jSONObjectExec, FileSDKUtils.contentKssJsonToMap(jSONObjectHandleRequestDownloadResultJson, TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP), FileDownloadRequestResult.class, TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP, new String[0]);
        } catch (JSONException e) {
            throw KscException.newException(e, "handleRequestDownloadResultJson failed.", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private JSONObject exec(DeprecatedMiCloudRequestor<T>.ApiConfig apiConfig, Map<String, String> map, String str, TransferStep transferStep) throws CloudServerException, InterruptedException, KscException {
        KscHttpResponse kscHttpResponse = null;
        try {
            try {
                try {
                    try {
                        KscHttpResponse kscHttpResponseExecute = execute(apiConfig, map, str, 0, transferStep);
                        ErrorHelper.throwError(kscHttpResponseExecute, transferStep);
                        verify(kscHttpResponseExecute, false, transferStep);
                        InputStream content = kscHttpResponseExecute.getContent();
                        if (content == null) {
                            throw new KscException(ErrorCode.DATA_IS_NOT_JSON, kscHttpResponseExecute.dump(), transferStep);
                        }
                        JSONObject resultJSON = getResultJSON(content, this.mExtToken.security, transferStep);
                        try {
                            kscHttpResponseExecute.release();
                        } catch (Throwable unused) {
                        }
                        return resultJSON;
                    } catch (IOException e) {
                        throw KscException.newException(e, "exec failed.", transferStep);
                    } catch (IllegalBlockSizeException e2) {
                        throw KscException.newException(e2, "exec failed.", transferStep);
                    }
                } catch (IllegalStateException e3) {
                    throw KscException.newException(e3, "exec failed.", transferStep);
                }
            } catch (BadPaddingException e4) {
                throw KscException.newException(e4, "exec failed.", transferStep);
            }
        } catch (Throwable th) {
            try {
                kscHttpResponse.release();
                throw th;
            } catch (Throwable unused2) {
                throw th;
            }
        }
    }

    public static JSONObject getResultJSON(InputStream inputStream, String str, TransferStep transferStep) throws InterruptedException, KscException {
        try {
            String strDecodeData = decodeData(str, inputStream);
            if (TextUtils.isEmpty(strDecodeData)) {
                return null;
            }
            return new JSONObject(strDecodeData);
        } catch (IOException e) {
            throw KscException.newException(e, "getResultJSON failed.", transferStep);
        } catch (BadPaddingException e2) {
            throw KscException.newException(e2, "getResultJSON failed.", transferStep);
        } catch (IllegalBlockSizeException e3) {
            throw KscException.newException(e3, "getResultJSON failed.", transferStep);
        } catch (JSONException e4) {
            throw KscException.newException(e4, "getResultJSON failed.", transferStep);
        }
    }

    private void verify(KscHttpResponse kscHttpResponse, boolean z, TransferStep transferStep) throws ServerException, CloudServerException {
        int statusCode = kscHttpResponse.getStatusCode();
        if (CloudServerException.isMiCloudServerException(statusCode)) {
            throw new CloudServerException(statusCode, kscHttpResponse.getResponse());
        }
        if (statusCode != 200) {
            throw new ServerException(statusCode, kscHttpResponse.dump(), transferStep);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private KscHttpResponse execute(DeprecatedMiCloudRequestor<T>.ApiConfig apiConfig, Map<String, ? extends Object> map, String str, int i, TransferStep transferStep) throws BadPaddingException, CloudServerException, InterruptedException, IllegalBlockSizeException, IOException, KscException {
        HttpEntity entity;
        if (apiConfig == 0) {
            throw new RuntimeException("API Config can not be null");
        }
        KscHttpRequest kscHttpRequest = new KscHttpRequest(apiConfig.method, apiConfig.getUri(), (IKscTransferListener) null);
        apiConfig.filterQuerys(map, str, kscHttpRequest, transferStep);
        String simpleName = "";
        if (i > 0) {
            kscHttpRequest.getRequest().setHeader(MiCloudConstants.REQUEST.X_XIAOMI_REDIRECT_COUNT, i + "");
        }
        kscHttpRequest.getRequest().setHeader(MiCloudConstants.REQUEST.X_XIAOMI_SUPPORT_REDIRECT, "true");
        long jCurrentTimeMillis = System.currentTimeMillis();
        KscHttpResponse kscHttpResponseExecute = this.mTransmitter.execute(kscHttpRequest, 2);
        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        long contentLength = (kscHttpResponseExecute.getResponse() == null || (entity = kscHttpResponseExecute.getResponse().getEntity()) == null) ? 0L : entity.getContentLength();
        int statusCode = kscHttpResponseExecute.getStatusCode();
        Throwable error = kscHttpResponseExecute.getError();
        if (error != null) {
            simpleName = error.getClass().getSimpleName();
        }
        String str2 = simpleName;
        if (Log.isLoggable("Micloud", 3)) {
            Log.d(TAG, "execute response status code: " + kscHttpResponseExecute.getStatusCode());
        }
        MiCloudStatManager.getInstance().addHttpEvent(apiConfig.getUri().toString(), jCurrentTimeMillis2, contentLength, statusCode, str2);
        if (error == null) {
            MiCloudNetEventStatInjector.getInstance().addNetSuccessEvent(new NetSuccessStatParam(apiConfig.getUri().toString(), jCurrentTimeMillis, jCurrentTimeMillis2, contentLength, statusCode, 0));
        } else {
            MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(apiConfig.getUri().toString(), jCurrentTimeMillis, jCurrentTimeMillis2, error, 0));
        }
        if (statusCode == 200) {
            String strDecodeData = decodeData(this.mExtToken.security, kscHttpResponseExecute.getResponse().getEntity().getContent());
            if (Log.isLoggable("Micloud", 3)) {
                Log.d(TAG, strDecodeData);
            }
            String strCheckRedirect = CloudRelocationUtils.checkRedirect(strDecodeData, i);
            if (!TextUtils.isEmpty(strCheckRedirect)) {
                ((ApiConfig) apiConfig).uri = Uri.parse(strCheckRedirect);
                return execute(apiConfig, map, str, i + 1, transferStep);
            }
        }
        return kscHttpResponseExecute;
    }

    public class ApiConfig {
        public final KscHttpRequest.HttpMethod method;
        private Uri uri;

        public ApiConfig(KscHttpRequest.HttpMethod httpMethod, String str) throws CloudServerException {
            this.method = httpMethod;
            this.uri = Uri.parse(CloudRelocationUtils.updateRequestHost(str, false));
        }

        Uri getUri() {
            return this.uri;
        }

        public List<NameValuePair> filterQuerys(Map<String, ? extends Object> map, String str, KscHttpRequest kscHttpRequest, TransferStep transferStep) throws InterruptedException, KscException {
            Header cookies;
            ArrayList arrayList = new ArrayList();
            if (map != null && !map.isEmpty()) {
                for (String str2 : map.keySet()) {
                    arrayList.add(new BasicNameValuePair(str2, String.valueOf(map.get(str2))));
                }
            }
            if (str != null) {
                try {
                    String string = str.toString();
                    if (DeprecatedMiCloudRequestor.this.mExtToken != null) {
                        string = DeprecatedMiCloudRequestor.encodeData(DeprecatedMiCloudRequestor.this.mExtToken.security, string);
                    }
                    arrayList.add(new BasicNameValuePair("data", string));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw KscException.newException(e, "filterQuerys failed.", transferStep);
                } catch (BadPaddingException e2) {
                    e2.printStackTrace();
                    throw KscException.newException(e2, "filterQuerys failed.", transferStep);
                } catch (IllegalBlockSizeException e3) {
                    throw KscException.newException(e3, "filterQuerys failed.", transferStep);
                }
            }
            if (DeprecatedMiCloudRequestor.this.mAccount == null || DeprecatedMiCloudRequestor.this.mExtToken == null) {
                cookies = null;
            } else {
                arrayList.add(new BasicNameValuePair("signature", DeprecatedMiCloudRequestor.getSignature(this.method, this.uri.toString(), arrayList, DeprecatedMiCloudRequestor.this.mExtToken.security)));
                cookies = DeprecatedMiCloudRequestor.getCookies(DeprecatedMiCloudRequestor.getCUserId(DeprecatedMiCloudRequestor.this.mContext, DeprecatedMiCloudRequestor.this.mAccount), DeprecatedMiCloudRequestor.this.mExtToken);
            }
            if (KscHttpRequest.HttpMethod.GET == this.method) {
                kscHttpRequest.appendQueryParameters(arrayList);
            } else {
                kscHttpRequest.appendPostParameters(arrayList);
                kscHttpRequest.getRequest().setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
            }
            kscHttpRequest.getRequest().setHeader(cookies);
            return arrayList;
        }
    }

    public static String encodeData(String str, String str2) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return Base64.encodeToString(CloudCoder.newAESCipher(str, 1).doFinal(str2.getBytes("UTF-8")), 2);
    }

    public static String decodeData(String str, InputStream inputStream) throws BadPaddingException, IllegalBlockSizeException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        while (true) {
            try {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                bufferedReader.close();
                throw th;
            }
        }
        bufferedReader.close();
        Cipher cipherNewAESCipher = CloudCoder.newAESCipher(str, 2);
        if (cipherNewAESCipher == null) {
            throw new IOException("decoder is null");
        }
        try {
            return new String(cipherNewAESCipher.doFinal(Base64.decode(sb.toString(), 2)));
        } catch (IllegalArgumentException unused) {
            throw new IOException();
        }
    }

    public static String getSignature(KscHttpRequest.HttpMethod httpMethod, String str, List<NameValuePair> list, String str2) throws UnsupportedEncodingException {
        TreeMap treeMap = new TreeMap();
        if (list != null) {
            for (NameValuePair nameValuePair : list) {
                treeMap.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        return CloudCoder.generateSignature(httpMethod.name(), str, treeMap, str2);
    }

    public static Header getCookies(String str, ExtendedAuthToken extendedAuthToken) {
        StringBuilder sb = new StringBuilder("serviceToken=");
        sb.append(extendedAuthToken.authToken);
        if (str != null) {
            sb.append("; cUserId=");
            sb.append(str);
        }
        return new BasicHeader("Cookie", sb.toString());
    }

    public static String getCUserId(Context context, Account account) {
        return AccountManager.get(context).getUserData(account, "encrypted_user_id");
    }
}
