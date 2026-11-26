package com.xiaomi.opensdk.file.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.Constants;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.exception.ServerMsgException;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.http.KssTransferStopper;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.download.KssDownloadFile;
import cn.kuaipan.android.kss.download.KssDownloader;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.KssUploader;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import cn.kuaipan.android.kss.upload.UploadTaskStore;
import cn.kuaipan.android.utils.ContextUtils;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.file.model.DownloadParameter;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;
import com.xiaomi.opensdk.file.model.UploadContext;
import com.xiaomi.opensdk.file.model.UploadParameter;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.io.File;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public class KssMasterRef implements KssDef {
    private final String TAG = "KssMasterRef";
    private final KssDownloader mDownloader;
    private final UploadTaskStore mTaskStore;
    private final KscHttpTransmitter mTransmitter;

    public KssMasterRef(Context context) {
        this.mTaskStore = new UploadTaskStore(context, new FileDataFactory());
        KscHttpTransmitter kscHttpTransmitter = new KscHttpTransmitter(context);
        this.mTransmitter = kscHttpTransmitter;
        kscHttpTransmitter.setUserAgent(4, getUserAgent(context));
        this.mDownloader = new KssDownloader(kscHttpTransmitter);
    }

    public void upload(UploadContext uploadContext, UploadFileInfo uploadFileInfo) throws InterruptedException, E2EEException, KscException {
        KssUploadFile localFile = uploadContext.getLocalFile();
        if (localFile.isFileInvalid()) {
            throw new KscRuntimeException(ErrorCode.INVALID_PARAM, localFile.filePath + " is not a exist file.");
        }
        KscTransferListener kscTransferListener = new KscTransferListener(uploadContext.getListener());
        if (uploadFileInfo == null) {
            uploadFileInfo = UploadFileInfo.getFileInfo(localFile, uploadContext.getEncryptInfo());
        }
        int uploadHash = getUploadHash(localFile.filePath, localFile.fileAbsPath, uploadFileInfo);
        if (uploadContext.getUploadParam() == null) {
            UploadTaskStore uploadTaskStore = this.mTaskStore;
            KssUploadInfo uploadInfo = uploadTaskStore == null ? null : uploadTaskStore.getUploadInfo(uploadHash);
            if (uploadInfo != null) {
                if (needRequestUpload(uploadContext, uploadInfo)) {
                    this.mTaskStore.removeUploadInfo(uploadHash);
                    uploadContext.setNeedRequestUpload(true);
                    uploadContext.setKssString(uploadFileInfo.getKssString());
                    uploadContext.setSha1(uploadFileInfo.getSha1());
                    return;
                }
            } else {
                uploadContext.setNeedRequestUpload(true);
                uploadContext.setKssString(uploadFileInfo.getKssString());
                uploadContext.setSha1(uploadFileInfo.getSha1());
                return;
            }
        }
        KssUploadInfo uploadInfo2 = null;
        while (!Thread.interrupted()) {
            if (uploadInfo2 == null) {
                uploadInfo2 = getUploadInfo(uploadFileInfo, uploadContext, uploadHash);
                if (uploadContext.getEncryptInfo().isNeedEncrypt()) {
                    uploadContext.getEncryptInfo().setRecordIV(uploadInfo2.getRecordIV());
                    uploadContext.getEncryptInfo().setEncryptSha1(uploadInfo2.getEncryptedSha1());
                    uploadContext.getEncryptInfo().setEncryptedRecordKeyAndDecrypt(uploadInfo2.getEncryptedRecordKey());
                }
            }
            KssUploadInfo kssUploadInfo = uploadInfo2;
            if (kssUploadInfo.isBroken()) {
                uploadContext.setNeedRequestUpload(true);
                uploadContext.setUploadParam(null);
                deleteUploadInfo(uploadHash);
                return;
            } else {
                if (kssUploadInfo.isCompleted()) {
                    kscTransferListener.setSendTotal(localFile.fileSize);
                    kscTransferListener.setSendPos(localFile.fileSize);
                    deleteUploadInfo(uploadHash);
                    uploadContext.setNeedRequestUpload(false);
                    uploadContext.setCommitString(kssUploadInfo.getCommitString());
                    uploadContext.setUploadId(kssUploadInfo.getUploadId());
                    uploadContext.setSha1(kssUploadInfo.getFileInfo().getSha1());
                    return;
                }
                new KssUploader(this.mTransmitter, this.mTaskStore, uploadContext.getEncryptInfo()).upload(localFile, kscTransferListener, KssTransferStopper.KssTransferStopperFromMiCloudTransferStopper.get(uploadContext.getStopper()), uploadHash, kssUploadInfo);
                uploadInfo2 = kssUploadInfo;
            }
        }
        throw new InterruptedException();
    }

    public void download(KssDownloadFile kssDownloadFile, DownloadParameter downloadParameter, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper, boolean z) throws InterruptedException, KscException {
        if (kssDownloadFile == null) {
            throw new KscRuntimeException(ErrorCode.INVALID_PARAM, "downloadFile can't be null.");
        }
        KscTransferListener kscTransferListener = new KscTransferListener(miCloudFileListener);
        try {
            Map<String, Object> mapContentKssJsonToMap = FileSDKUtils.contentKssJsonToMap(downloadParameter.toJsonObject(), TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
            mapContentKssJsonToMap.put("encryptInfo", downloadParameter.getDownloadEncryptInfo());
            FileDownloadRequestResult fileDownloadRequestResult = new FileDownloadRequestResult(mapContentKssJsonToMap);
            if (fileDownloadRequestResult.getStatus() != 0) {
                String message = fileDownloadRequestResult.getMessage();
                if (TextUtils.isEmpty(message)) {
                    throw new KscException(503000, "server did not return 'stat' field.", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
                }
                throw new ServerMsgException(200, message, "Failed on requestDownload", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
            }
            this.mDownloader.download(kssDownloadFile, z, kscTransferListener, KssTransferStopper.KssTransferStopperFromMiCloudTransferStopper.get(miCloudTransferStopper), fileDownloadRequestResult);
        } catch (JSONException e) {
            throw KscException.newException(e, "download failed", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
        }
    }

    public void cleanDownload(File file) {
        this.mDownloader.clean(file);
    }

    private boolean needRequestUpload(UploadContext uploadContext, KssUploadInfo kssUploadInfo) {
        boolean z = kssUploadInfo.getRecordIV() != null;
        boolean zIsNeedEncrypt = uploadContext.getEncryptInfo().isNeedEncrypt();
        if (zIsNeedEncrypt != z) {
            return true;
        }
        if (zIsNeedEncrypt) {
            if (uploadContext.getEncryptInfo().getAppKeyInfo().appKeyVersion != kssUploadInfo.getAppKeyVersion()) {
                return true;
            }
        }
        return false;
    }

    private KssUploadInfo getUploadInfo(UploadFileInfo uploadFileInfo, UploadContext uploadContext, int i) throws InterruptedException, KscException {
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        KssUploadInfo uploadInfo = uploadTaskStore == null ? null : uploadTaskStore.getUploadInfo(i);
        if (uploadInfo == null) {
            UploadParameter uploadParam = uploadContext.getUploadParam();
            if (uploadParam == null) {
                throw new KscRuntimeException(ErrorCode.INVALID_PARAM, "uploadParam null");
            }
            try {
                KssUploadInfo kssUploadInfo = new KssUploadInfo(uploadFileInfo, new FileUploadRequestResult(FileSDKUtils.contentKssJsonToMap(uploadParam.toJsonObject(), TransferStep.UPLOAD_REQUEST_BIZ_HTTP)));
                kssUploadInfo.setUploadId(uploadParam.getUploadId());
                if (uploadContext.getEncryptInfo().isNeedEncrypt()) {
                    kssUploadInfo.setEncryptInfo(uploadContext.getEncryptInfo());
                }
                UploadTaskStore uploadTaskStore2 = this.mTaskStore;
                if (uploadTaskStore2 != null) {
                    uploadTaskStore2.putUploadInfo(i, kssUploadInfo);
                }
                uploadInfo = kssUploadInfo;
            } catch (JSONException e) {
                throw KscException.newException(e, "getUploadInfo failed", TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
            }
        }
        Log.w("KssMasterRef", "KssUploadInfo Return:" + uploadInfo.getUploadId());
        return uploadInfo;
    }

    public boolean hasStoredUploadInfo(int i) throws InterruptedException {
        Boolean boolValueOf;
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        if (uploadTaskStore == null) {
            boolValueOf = null;
        } else {
            boolValueOf = Boolean.valueOf(uploadTaskStore.getUploadInfo(i) != null);
        }
        return boolValueOf.booleanValue();
    }

    private void deleteUploadInfo(int i) throws InterruptedException {
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        if (uploadTaskStore == null) {
            return;
        }
        uploadTaskStore.removeUploadInfo(i);
    }

    private static String getUserAgent(Context context) {
        return String.format("KssRC4/1.0 %s/%s S3SDK/%s", context.getPackageName(), ContextUtils.getAppVersion(context), Constants.VERSION);
    }

    private static int getUploadHash(String str, String str2, UploadFileInfo uploadFileInfo) {
        return (str + ":" + str2 + ":" + (uploadFileInfo == null ? "" : uploadFileInfo.getSha1())).hashCode();
    }

    public UploadFileInfo getUploadFileInfo(UploadContext uploadContext) throws InterruptedException, KscException {
        KssUploadFile localFile = uploadContext.getLocalFile();
        if (localFile.isFileInvalid()) {
            throw new KscRuntimeException(ErrorCode.INVALID_PARAM, localFile.filePath + " is not a exist file.");
        }
        return UploadFileInfo.getFileInfo(localFile, uploadContext.getEncryptInfo());
    }

    private class KscTransferListener extends IKscTransferListener.KscTransferListener {
        public MiCloudFileListener mMiCloudFileListener;

        public KscTransferListener(MiCloudFileListener miCloudFileListener) {
            this.mMiCloudFileListener = miCloudFileListener;
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener.KscTransferListener
        public void onDataSended(long j, long j2) {
            MiCloudFileListener miCloudFileListener = this.mMiCloudFileListener;
            if (miCloudFileListener != null) {
                miCloudFileListener.onDataSended(j, j2);
            }
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener.KscTransferListener
        public void onDataReceived(long j, long j2) {
            MiCloudFileListener miCloudFileListener = this.mMiCloudFileListener;
            if (miCloudFileListener != null) {
                miCloudFileListener.onDataReceived(j, j2);
            }
        }
    }
}
