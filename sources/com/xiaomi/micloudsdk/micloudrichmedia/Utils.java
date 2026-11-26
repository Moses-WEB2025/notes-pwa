package com.xiaomi.micloudsdk.micloudrichmedia;

import android.accounts.NetworkErrorException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import cn.kuaipan.kss.implement.HttpClientProxyHelper;
import cn.kuaipan.kss.implement.KssDownloadFile;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyInfo;
import com.xiaomi.e2ee.appkey.AppKeyServiceManager;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudParameterError;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.FileTooLargeException;
import com.xiaomi.micloudsdk.exception.InvalidWritePositionException;
import com.xiaomi.micloudsdk.exception.RichmediaServerFailureException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.commons.codec.DecoderException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class Utils {
    private Context mContext;
    private String mCookie;
    private int mCurrentUploadChunk;
    private ExtendedAuthToken mExtAuthToken;
    private NetworkConnectivityChangedReceiver mNetworkConnectivityReceiver;
    private HttpClientProxyHelper mProxyHelper;
    private String mUrl;
    private String mUserId;
    private final boolean needE2eeEncrypt;

    Utils(Context context, String str, boolean z) {
        this(context, str, null, z);
    }

    Utils(Context context, String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The userId and authtoken should not be null");
        }
        this.mUserId = str;
        this.mContext = context;
        this.mUrl = str2;
        this.needE2eeEncrypt = z;
    }

    void updateToken(ExtendedAuthToken extendedAuthToken) {
        if (extendedAuthToken == null) {
            throw new IllegalArgumentException("The authtoken should not be null");
        }
        this.mCookie = getCookies(this.mUserId, com.xiaomi.micloudsdk.request.utils.Request.getRequestEnv().queryEncryptedAccountName(), extendedAuthToken.authToken);
        this.mExtAuthToken = extendedAuthToken;
    }

    private void registerConnectivityReceiver() {
        MiCloudRichMediaManager2.log("Register network connectivity changed receiver");
        if (this.mNetworkConnectivityReceiver == null) {
            this.mNetworkConnectivityReceiver = new NetworkConnectivityChangedReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.mContext.registerReceiver(this.mNetworkConnectivityReceiver, intentFilter);
    }

    private void unregisterConnectivityReceiver() {
        MiCloudRichMediaManager2.log("Unregister network connectivity changed receiver");
        this.mContext.unregisterReceiver(this.mNetworkConnectivityReceiver);
    }

    static String getCookies(String str, String str2) {
        return getCookies(str, null, str2);
    }

    static String getCookies(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("serviceToken=");
        sb.append(str3);
        if (!TextUtils.isEmpty(str2)) {
            sb.append("; cUserId=");
            sb.append(str2);
        }
        return sb.toString();
    }

    MicloudDownload.Result download2(String str, String str2, String str3, File file) throws NetworkErrorException, CloudServerException, JSONException, IOException, CloudParameterError, RichmediaServerFailureException {
        preDownload(str2, str3);
        try {
            JSONObject downloadUrl2 = getDownloadUrl2(str2, str3);
            if (downloadUrl2 == null) {
                return MicloudDownload.Result.Error;
            }
            JSONObject jSONObjectOptJSONObject = downloadUrl2.optJSONObject("data");
            if (jSONObjectOptJSONObject == null) {
                return MicloudDownload.Result.Error;
            }
            if (!jSONObjectOptJSONObject.has(ResponseParameters.StorageType.MFS) && !MiCloudRichMediaSupportedFileType.isMiXin2(str3)) {
                if (jSONObjectOptJSONObject.has("kss")) {
                    return downloadFromKss(str, downloadUrl2, file);
                }
                return MicloudDownload.Result.Unsupported;
            }
            byte[] bArrDownloadFromMfs = downloadFromMfs(jSONObjectOptJSONObject, str3);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArrDownloadFromMfs);
            fileOutputStream.close();
            return MicloudDownload.Result.OK;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return MicloudDownload.Result.Error;
        } catch (BadPaddingException e2) {
            throw new IOException(e2);
        } catch (IllegalBlockSizeException e3) {
            throw new IOException(e3);
        } catch (DecoderException e4) {
            throw new IOException(e4);
        }
    }

    byte[] download2(String str, String str2, String str3) throws Throwable {
        JSONObject jSONObjectOptJSONObject;
        FileInputStream fileInputStream;
        preDownload(str2, str3);
        try {
            JSONObject downloadUrl2 = getDownloadUrl2(str2, str3);
            if (downloadUrl2 == null || (jSONObjectOptJSONObject = downloadUrl2.optJSONObject("data")) == null) {
                return null;
            }
            if (jSONObjectOptJSONObject.has(ResponseParameters.StorageType.MFS)) {
                return downloadFromMfs(jSONObjectOptJSONObject);
            }
            if (jSONObjectOptJSONObject.has("kss")) {
                File file = new File(this.mContext.getCacheDir() + File.separator + str2);
                if (downloadFromKss(str, downloadUrl2, file) == MicloudDownload.Result.OK) {
                    try {
                        fileInputStream = new FileInputStream(file);
                        try {
                            byte[] bArr = new byte[fileInputStream.available()];
                            fileInputStream.read(bArr);
                            fileInputStream.close();
                            if (file.exists()) {
                                file.delete();
                            }
                            return bArr;
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (file.exists()) {
                                file.delete();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = null;
                    }
                }
            }
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (BadPaddingException e2) {
            throw new IOException(e2);
        } catch (IllegalBlockSizeException e3) {
            throw new IOException(e3);
        } catch (DecoderException e4) {
            throw new IOException(e4);
        }
    }

    private void preDownload(String str, String str2) throws NetworkErrorException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The download parameters should not be null");
        }
        if (!MiCloudRichMediaSupportedFileType.isSupported(str2)) {
            throw new IllegalArgumentException(String.format("The type %s is not supported", str2));
        }
        int chunkSize = getChunkSize(this.mContext);
        this.mCurrentUploadChunk = chunkSize;
        if (chunkSize == 0) {
            throw new NetworkErrorException("Network is not connected");
        }
    }

    private class DownloadFileInfo {
        public String ckey;
        public String fileId;
        public String fileSha1;
        public String tmpUrl;

        private DownloadFileInfo() {
        }
    }

    UploadResult upload(UploadEntity uploadEntity, Collection<String> collection) throws NetworkErrorException, CloudServerException, IOException, FileTooLargeException, CloudParameterError, RichmediaServerFailureException {
        if (uploadEntity == null) {
            MiCloudRichMediaManager2.log("The file should not be null");
            return null;
        }
        int chunkSize = getChunkSize(this.mContext);
        this.mCurrentUploadChunk = chunkSize;
        if (chunkSize == 0) {
            throw new NetworkErrorException("Network is not connected");
        }
        registerConnectivityReceiver();
        try {
            try {
                try {
                    uploadEntity.open();
                    UploadResult fileId = getFileId(uploadEntity, collection);
                    if (fileId != null && (!TextUtils.isEmpty(fileId.fileId) || !TextUtils.isEmpty(fileId.shareId))) {
                        MiCloudRichMediaManager2.log("The file already exist:" + fileId);
                        return fileId;
                    }
                    UploadResult uploadResultUploadFile = uploadFile(uploadEntity, collection);
                    if (uploadResultUploadFile != null) {
                        MiCloudRichMediaManager2.log("The file upload success:" + uploadResultUploadFile);
                        return uploadResultUploadFile;
                    }
                    throw new RichmediaServerFailureException("Cloud server fails when upload files");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            } catch (E2EEException e3) {
                e3.printStackTrace();
            } catch (InvalidWritePositionException e4) {
                e4.printStackTrace();
            }
        } finally {
            uploadEntity.close();
            unregisterConnectivityReceiver();
        }
    }

    private UploadResult uploadFile(UploadEntity uploadEntity, Collection<String> collection) throws InvalidWritePositionException, JSONException, CloudServerException, InterruptedException, E2EEException, IOException, FileTooLargeException, CloudParameterError, RichmediaServerFailureException {
        AppKeyInfo appKeyInfo;
        int i = 0;
        UploadEncryptInfo uploadEncryptInfo = (!this.needE2eeEncrypt || (appKeyInfo = AppKeyServiceManager.getInstance().getAppKeyInfo()) == null) ? null : new UploadEncryptInfo(uploadEntity, appKeyInfo);
        while (uploadEntity.getOffset() < uploadEntity.getLength()) {
            int i2 = this.mCurrentUploadChunk;
            if (i2 != 0) {
                MiCloudRichMediaManager2.log("Current chunk size is:" + i2 + " the " + (uploadEntity.getOffset() / i2) + " chunk");
            }
            boolean zIsFirstChunk = uploadEntity.isFirstChunk();
            boolean zIsLastChunk = uploadEntity.isLastChunk(i2);
            UploadEntity uploadEntity2 = uploadEntity;
            UploadChunkRequest uploadChunkRequest = new UploadChunkRequest(uploadEntity2, zIsLastChunk, i2, uploadEntity.getOffset(), this.mUrl, uploadEncryptInfo, this.mContext);
            if (collection != null && !collection.isEmpty()) {
                uploadChunkRequest.addParam(RequestParameters.SHARE_TO, TextUtils.join(",", collection));
            }
            JSONObject jSONObjectRequest = uploadChunkRequest.request(this.mUserId, this.mExtAuthToken, this.mCookie);
            if (jSONObjectRequest != null) {
                ResponseParameters response = ResponseParameters.parseResponse(jSONObjectRequest);
                int i3 = response.mCode != 80002 ? 0 : i;
                if (response.mCode == 0) {
                    if (zIsFirstChunk && !zIsLastChunk) {
                        uploadEntity2.mTempId = response.mData.optString("tmpid", null);
                        uploadEntity2.mHostingServer = response.mData.optString("_hostingserver", null);
                        if (TextUtils.isEmpty(uploadEntity2.mTempId) || TextUtils.isEmpty(uploadEntity2.mHostingServer)) {
                            throw new IllegalStateException("Server error: The first chunk's response does not contain the tempid or hosting server");
                        }
                    } else if (zIsLastChunk) {
                        return UploadResult.fromJson(response.mData);
                    }
                } else if (response.mCode == 80005) {
                    uploadEntity2.resetOffset();
                    if (uploadEncryptInfo != null) {
                        uploadEncryptInfo.regenerateCipher();
                    }
                } else if (response.mCode == 80002) {
                    if (i3 == 3) {
                        break;
                    }
                    try {
                        Thread.sleep(5000L);
                        i3++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.mCode == 80001) {
                        throw new IOException("The file digest error");
                    }
                    if (response.mCode == 80003) {
                        throw new InvalidWritePositionException("Can't write file at offset:" + uploadEntity2.getOffset());
                    }
                    if (response.mCode == 80004) {
                        throw new FileTooLargeException("The file size:" + uploadEntity2.getLength() + " exceeds the limit");
                    }
                    if (response.mCode == 10008) {
                        throw new CloudParameterError(response.mDescription);
                    }
                }
                i = i3;
                uploadEntity = uploadEntity2;
            } else {
                throw new RichmediaServerFailureException("Cloud service fails when upload file");
            }
        }
        throw new IOException("Read file error");
    }

    UploadResult getFileId(UploadEntity uploadEntity, Collection<String> collection) throws JSONException, CloudServerException, IOException, CloudParameterError, RichmediaServerFailureException {
        MiCloudRichMediaManager2.log("Current chunk size is:" + this.mCurrentUploadChunk);
        CheckRequest checkRequest = new CheckRequest(uploadEntity, this.mUrl, this.mContext);
        if (collection != null && !collection.isEmpty()) {
            checkRequest.addParam(RequestParameters.SHARE_TO, TextUtils.join(",", collection));
        }
        JSONObject jSONObjectRequest = checkRequest.request(this.mUserId, this.mExtAuthToken, this.mCookie);
        if (jSONObjectRequest != null) {
            ResponseParameters response = ResponseParameters.parseResponse(jSONObjectRequest);
            if (response.mCode == 0) {
                if (MiCloudRichMediaSupportedFileType.isMiXin2(uploadEntity.getType())) {
                    uploadEntity.setTempId(response.mData.optString("tmpid", null));
                    uploadEntity.setHostingServer(response.mData.optString("_hostingserver", null));
                }
                return UploadResult.fromJson(response.mData);
            }
            if (response.mCode == 10008) {
                throw new CloudParameterError(response.mDescription);
            }
        }
        throw new RichmediaServerFailureException("Cloud service error in check file exits");
    }

    private class NetworkConnectivityChangedReceiver extends BroadcastReceiver {
        private NetworkConnectivityChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Utils utils = Utils.this;
            utils.mCurrentUploadChunk = utils.getChunkSize(context);
        }
    }

    private static boolean isWifi(Context context) {
        return getActiveNetworkType(context) == 1;
    }

    private static int getActiveNetworkType(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChunkSize(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? 0 : 4194304;
    }

    private JSONObject getDownloadUrl2(String str, String str2) throws NetworkErrorException, CloudServerException, JSONException, CloudParameterError, RichmediaServerFailureException {
        JSONObject jSONObject;
        String str3;
        JSONObject jSONObjectRequest = new DownloadUrlRequest(str, str2, this.mUrl, this.mContext).request(this.mUserId, this.mExtAuthToken, this.mCookie);
        if (jSONObjectRequest != null) {
            ResponseParameters response = ResponseParameters.parseResponse(jSONObjectRequest);
            if (response.mCode == 0 && response.mData != null) {
                if (!MiCloudRichMediaSupportedFileType.isMiXin2(str2)) {
                    jSONObject = response.mData.getJSONObject("data");
                    str3 = "fileId";
                } else {
                    jSONObject = response.mData.getJSONArray("data").getJSONObject(0);
                    str3 = "shareId";
                }
                if (jSONObject != null) {
                    if (TextUtils.equals(str, jSONObject.optString(str3, null))) {
                        return response.mData;
                    }
                    throw new RichmediaServerFailureException(String.format("The local fileId is not accord with server fileId", new Object[0]));
                }
                MiCloudRichMediaManager2.log(String.format("The local fileId %s unable to obtain the download", str));
                return null;
            }
            if (response.mCode == 10008) {
                throw new CloudParameterError(response.mDescription);
            }
        }
        throw new RichmediaServerFailureException("Cloud service error on getDownloadUrl for " + str);
    }

    private byte[] downloadFromMfs(JSONObject jSONObject) throws JSONException, BadPaddingException, CloudServerException, DecoderException, IllegalBlockSizeException, IOException, RichmediaServerFailureException {
        return downloadFromMfs(jSONObject, null);
    }

    private byte[] downloadFromMfs(JSONObject jSONObject, String str) throws JSONException, BadPaddingException, CloudServerException, DecoderException, IllegalBlockSizeException, IOException, RichmediaServerFailureException {
        DownloadFileInfo downloadFileInfo = getDownloadFileInfo(jSONObject, str);
        if (downloadFileInfo.tmpUrl == null || downloadFileInfo.fileSha1 == null) {
            return null;
        }
        return new DownloadRequest(downloadFileInfo.tmpUrl, downloadFileInfo.ckey, downloadFileInfo.fileSha1, true, this.mContext).download(this.mContext, this.mUserId, this.mExtAuthToken, this.mCookie);
    }

    private DownloadFileInfo getDownloadFileInfo(JSONObject jSONObject) throws JSONException {
        return getDownloadFileInfo(jSONObject, null);
    }

    private DownloadFileInfo getDownloadFileInfo(JSONObject jSONObject, String str) throws JSONException {
        JSONObject jSONObject2;
        String str2;
        if (MiCloudRichMediaSupportedFileType.isMiXin2(str)) {
            str2 = ResponseParameters.TAG_DATA_DOWNLOADURL;
            jSONObject2 = jSONObject;
        } else {
            jSONObject2 = jSONObject.getJSONObject(ResponseParameters.StorageType.MFS);
            str2 = ResponseParameters.TAG_DATA_TEMPURL;
        }
        DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
        if (jSONObject2 != null) {
            downloadFileInfo.tmpUrl = jSONObject2.optString(str2, null);
            downloadFileInfo.ckey = jSONObject2.optString("ckey", null);
            downloadFileInfo.fileId = jSONObject.optString("fileId", null);
            downloadFileInfo.fileSha1 = jSONObject2.optString(ResponseParameters.TAG_DATA_SHA1, null);
        }
        return downloadFileInfo;
    }

    private MicloudDownload.Result downloadFromKss(String str, JSONObject jSONObject, File file) throws CloudServerException, BadPaddingException, JSONException, IllegalBlockSizeException, URISyntaxException, IOException {
        if (this.mProxyHelper == null) {
            try {
                this.mProxyHelper = KssDownloadFile.getDownloadProxyHelper(this.mContext, str, this.mExtAuthToken);
            } catch (CloudServerException e) {
                throw new CloudServerException(e.getStatusCode());
            }
        }
        return MicloudDownload.getResult(new KssDownloadFile().downloadFile(this.mContext, this.mProxyHelper, null, jSONObject, file));
    }

    public byte[] downloadFromPublicUrl(String str, String str2) throws CloudServerException, IOException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            return new DownloadRequest(str, null, str2, this.mContext).download(this.mContext, this.mUserId, this.mExtAuthToken, null, false);
        } catch (RichmediaServerFailureException e) {
            throw new IOException(e);
        } catch (BadPaddingException e2) {
            throw new IOException(e2);
        } catch (IllegalBlockSizeException e3) {
            throw new IOException(e3);
        } catch (DecoderException e4) {
            throw new IOException(e4);
        }
    }
}
