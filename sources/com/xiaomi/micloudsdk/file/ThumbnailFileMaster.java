package com.xiaomi.micloudsdk.file;

import android.content.Context;
import android.util.Log;
import cn.kuaipan.android.Constants;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.http.KssTransferStopper;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.thumbnail.ThumbnailUploadParameter;
import cn.kuaipan.android.kss.thumbnail.ThumbnailUploader;
import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import cn.kuaipan.android.kss.transferclient.context.UploadFileTransferContext;
import cn.kuaipan.android.kss.transferclient.controller.impl.ThumbnailUploadController;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import cn.kuaipan.android.utils.Base64;
import cn.kuaipan.android.utils.ContextUtils;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.MiCloudFileClient;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;
import com.xiaomi.opensdk.file.model.UploadContext;
import com.xiaomi.opensdk.file.sdk.FileUploadRequestResult;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import defpackage.R2;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ThumbnailFileMaster<T extends FileTransferContext> {
    private static final String TAG = "ThumbnailFileMaster";
    private final Context mContext;
    private final MiCloudFileRequestor<T> mRequestor;
    private final KscHttpTransmitter mTransmitter;

    public ThumbnailFileMaster(Context context, MiCloudFileRequestor<T> miCloudFileRequestor) {
        this.mContext = context;
        this.mRequestor = miCloudFileRequestor;
        KscHttpTransmitter kscHttpTransmitter = new KscHttpTransmitter(context);
        this.mTransmitter = kscHttpTransmitter;
        kscHttpTransmitter.setUserAgent(4, getUserAgent(context));
    }

    public void uploadThumbnailIfNeed(T t, String str, MiCloudTransferStopper miCloudTransferStopper, OriginFileAndThumbRelatedInfo originFileAndThumbRelatedInfo) throws InterruptedException, AuthenticationException, RetriableException, IOException, UnretriableException {
        ThumbnailFileMaster<T> thumbnailFileMaster;
        MiCloudTransferStopper miCloudTransferStopper2;
        OriginFileAndThumbRelatedInfo originFileAndThumbRelatedInfo2;
        ThumbnailUploadController thumbnailUploadController = this.mRequestor.getThumbnailUploadController(t);
        if (thumbnailUploadController == null) {
            Log.i(TAG, "thumbnail controller is null, not need upload thumbnail");
            return;
        }
        UploadFileTransferContext uploadFileTransferContext = new UploadFileTransferContext(3, thumbnailUploadController);
        try {
            if (this.mRequestor.needUploadSmallThumbnail(t)) {
                Log.i(TAG, "upload small thumbnail");
                File smallThumbFile = thumbnailUploadController.getSmallThumbFile(str);
                thumbnailUploadController.setCurrentResolution(R2.attr.actionBarPauseIcon);
                KssUploadFile kssUploadFileCreateByFile = KssUploadFile.createByFile(smallThumbFile);
                thumbnailFileMaster = this;
                miCloudTransferStopper2 = miCloudTransferStopper;
                originFileAndThumbRelatedInfo2 = originFileAndThumbRelatedInfo;
                try {
                    thumbnailFileMaster.upload(uploadFileTransferContext, kssUploadFileCreateByFile, miCloudTransferStopper2, R2.attr.actionBarPauseIcon, originFileAndThumbRelatedInfo2);
                } catch (KscException e) {
                    e = e;
                    thumbnailFileMaster.transferException(e);
                    return;
                }
            } else {
                thumbnailFileMaster = this;
                miCloudTransferStopper2 = miCloudTransferStopper;
                originFileAndThumbRelatedInfo2 = originFileAndThumbRelatedInfo;
            }
            if (thumbnailFileMaster.mRequestor.needUploadLargeThumbnail(t)) {
                Log.i(TAG, "upload large thumbnail");
                File largeThumbFile = thumbnailUploadController.getLargeThumbFile(str);
                thumbnailUploadController.setCurrentResolution(1080);
                thumbnailFileMaster.upload(uploadFileTransferContext, KssUploadFile.createByFile(largeThumbFile), miCloudTransferStopper2, 1080, originFileAndThumbRelatedInfo2);
            }
        } catch (KscException e2) {
            e = e2;
            thumbnailFileMaster = this;
        }
    }

    public void uploadThumbnail(T t, File file, MiCloudTransferStopper miCloudTransferStopper, int i, String str, JSONObject jSONObject) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        Exception exc;
        ThumbnailFileMaster<T> thumbnailFileMaster;
        OriginFileAndThumbRelatedInfo originFileAndThumbRelatedInfoCreate;
        if (jSONObject != null) {
            try {
                originFileAndThumbRelatedInfoCreate = OriginFileAndThumbRelatedInfo.Factory.create(str, jSONObject);
            } catch (KscException | JSONException e) {
                exc = e;
                thumbnailFileMaster = this;
                thumbnailFileMaster.transferException(exc);
            }
        } else {
            originFileAndThumbRelatedInfoCreate = null;
        }
        try {
            thumbnailFileMaster = this;
            try {
                thumbnailFileMaster.upload(t, KssUploadFile.createByFile(file), miCloudTransferStopper, i, originFileAndThumbRelatedInfoCreate);
            } catch (KscException | JSONException e2) {
                e = e2;
                exc = e;
                thumbnailFileMaster.transferException(exc);
            }
        } catch (KscException | JSONException e3) {
            e = e3;
            thumbnailFileMaster = this;
        }
    }

    private void upload(T t, KssUploadFile kssUploadFile, MiCloudTransferStopper miCloudTransferStopper, int i, OriginFileAndThumbRelatedInfo originFileAndThumbRelatedInfo) throws InterruptedException, AuthenticationException, RetriableException, KscException, UnretriableException {
        UploadEncryptInfo uploadEncryptInfoCreate;
        if (t == null || kssUploadFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            if (originFileAndThumbRelatedInfo != null) {
                uploadEncryptInfoCreate = UploadEncryptInfo.Factory.createThumbnailEncryptInfo(getRecordIV(i, originFileAndThumbRelatedInfo.originFileSha1), originFileAndThumbRelatedInfo.originFileEncryptInfo);
            } else {
                uploadEncryptInfoCreate = UploadEncryptInfo.Factory.create(false);
            }
        } catch (E2EEException | JSONException e) {
            transferException(e);
            uploadEncryptInfoCreate = null;
        }
        UploadFileInfo fileInfo = UploadFileInfo.getFileInfo(kssUploadFile, uploadEncryptInfoCreate);
        UploadContext uploadContext = new UploadContext(kssUploadFile, null, miCloudTransferStopper, uploadEncryptInfoCreate);
        uploadContext.setSha1(fileInfo.getSha1());
        uploadContext.setKssString(fileInfo.getKssString());
        uploadContext.setNeedRequestUpload(true);
        TransferStep transferStep = TransferStep.UPLOAD_THUMBNAIL_REQUEST_BIZ_HTTP;
        for (int i2 = 0; i2 < 5; i2++) {
            try {
                if (!uploadContext.isNeedRequestUpload()) {
                    break;
                }
                JSONObject jSONObjectRequestUpload = this.mRequestor.requestUpload((FileTransferContext) t, MiCloudFileClient.getInstance(this.mContext).getRequestUploadParameter(uploadContext));
                if (this.mRequestor.handleRequestUploadResultJson(t, jSONObjectRequestUpload) != null) {
                    return;
                }
                upload(uploadContext, getKssUploadInfo(MiCloudFileClient.getInstance(this.mContext).getThumbnailParamForSFS(jSONObjectRequestUpload.getJSONObject("data")), fileInfo, uploadContext), kssUploadFile);
            } catch (JSONException e2) {
                UnretriableException unretriableException = new UnretriableException(e2);
                unretriableException.setStep(transferStep.toString());
                throw unretriableException;
            }
        }
        transferStep = TransferStep.UPLOAD_THUMBNAIL_REQUEST_COMMIT;
        this.mRequestor.handleCommitUploadResult(t, this.mRequestor.commitUpload((FileTransferContext) t, MiCloudFileClient.getInstance(this.mContext).getCommitParameter(uploadContext)));
    }

    private String getRecordIV(int i, String str) throws E2EEException, UnretriableException {
        try {
            if (str == null) {
                throw new UnretriableException("origin file sha1 is null");
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update((str + i).getBytes());
            byte[] bArr = new byte[16];
            System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
            return Base64.encodeToString(bArr, 11);
        } catch (NoSuchAlgorithmException e) {
            throw new E2EEException("NoSuchAlgorithmException happened when get record iv", e);
        }
    }

    private KssUploadInfo getKssUploadInfo(ThumbnailUploadParameter thumbnailUploadParameter, UploadFileInfo uploadFileInfo, UploadContext uploadContext) throws JSONException, InterruptedException, KscException {
        KssUploadInfo kssUploadInfo = new KssUploadInfo(uploadFileInfo, new FileUploadRequestResult(FileSDKUtils.contentKssJsonToMap(thumbnailUploadParameter.toJsonObject(), TransferStep.UPLOAD_THUMBNAIL_REQUEST_BIZ_HTTP)));
        kssUploadInfo.setUploadId(thumbnailUploadParameter.getUploadId());
        if (uploadContext.getEncryptInfo().isNeedEncrypt()) {
            kssUploadInfo.setRecordIV(uploadContext.getEncryptInfo().getRecordIV());
            kssUploadInfo.setAppKeyVersion(uploadContext.getEncryptInfo().getAppKeyInfo().appKeyVersion);
        }
        return kssUploadInfo;
    }

    private void upload(UploadContext uploadContext, KssUploadInfo kssUploadInfo, KssUploadFile kssUploadFile) throws InterruptedException, AuthenticationException, RetriableException, KscException, UnretriableException {
        while (!Thread.interrupted()) {
            if (kssUploadInfo.isCompleted()) {
                uploadContext.setUploadId(kssUploadInfo.getUploadId());
                uploadContext.setSha1(kssUploadInfo.getFileInfo().getSha1());
                uploadContext.setNeedRequestUpload(false);
                return;
            } else if (kssUploadInfo.isBroken()) {
                uploadContext.setNeedRequestUpload(true);
            } else {
                new ThumbnailUploader(this.mTransmitter, uploadContext).upload(kssUploadFile, kssUploadInfo, KssTransferStopper.KssTransferStopperFromMiCloudTransferStopper.get(uploadContext.getStopper()));
            }
        }
        throw new InterruptedException();
    }

    private void transferException(Exception exc) throws AuthenticationException, RetriableException, UnretriableException {
        MiCloudFileClient.getInstance(this.mContext).transferException(exc);
    }

    private static String getUserAgent(Context context) {
        return String.format("KssRC4/1.0 %s/%s S3SDK/%s", context.getPackageName(), ContextUtils.getAppVersion(context), Constants.VERSION);
    }
}
