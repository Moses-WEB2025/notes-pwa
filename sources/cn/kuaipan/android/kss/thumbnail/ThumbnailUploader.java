package cn.kuaipan.android.kss.thumbnail;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.ErrorHelper;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.exception.ServerException;
import cn.kuaipan.android.exception.ServerMsgException;
import cn.kuaipan.android.exception.SessionExpiredException;
import cn.kuaipan.android.http.DecoderInputStream;
import cn.kuaipan.android.http.IKscDecoder;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.http.KscHttpRequest;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.http.KssTransferStopper;
import cn.kuaipan.android.kss.E2EETransferDecoder;
import cn.kuaipan.android.kss.EmptyDecoder;
import cn.kuaipan.android.kss.IKssUploadRequestResult;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.RC4Encoder;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.upload.KssInputStreamEntity;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import cn.kuaipan.android.kss.upload.UploadThumbnailInfo;
import cn.kuaipan.android.utils.ApiDataHelper;
import cn.kuaipan.android.utils.Encode;
import cn.kuaipan.android.utils.IObtainable;
import cn.kuaipan.android.utils.RandomInputStream;
import com.xiaomi.opensdk.file.model.UploadContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ThumbnailUploader implements KssDef {
    private static final String LOG_TAG = "ThumbnailUploader_Log";
    private static final long SLEEP_DUR = 5000;
    private final UploadEncryptInfo mEncryptInfo;
    private final KscHttpTransmitter mTransmitter;
    private final UploadContext mUploadContext;

    public ThumbnailUploader(KscHttpTransmitter kscHttpTransmitter, UploadContext uploadContext) {
        this.mTransmitter = kscHttpTransmitter;
        this.mEncryptInfo = uploadContext.getEncryptInfo();
        this.mUploadContext = uploadContext;
    }

    public void upload(KssUploadFile kssUploadFile, KssUploadInfo kssUploadInfo, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        verifyFile(kssUploadFile, kssUploadInfo);
        uploadFile(kssUploadFile, kssUploadInfo, kssTransferStopper);
    }

    private void uploadFile(KssUploadFile kssUploadFile, KssUploadInfo kssUploadInfo, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        ThumbnailUploader thumbnailUploader;
        KssUploadInfo kssUploadInfo2;
        KssTransferStopper kssTransferStopper2;
        TransferStep transferStep = TransferStep.UPLOAD_PREPARE;
        AtomicInteger atomicInteger = new AtomicInteger(6);
        TransferStep transferStep2 = transferStep;
        RandomInputStream randomInputStream = null;
        while (atomicInteger.get() >= 0) {
            try {
                transferStep2 = TransferStep.UPLOAD_THUMBNAIL_PREPARE_STREAM;
                if (randomInputStream != null) {
                    randomInputStream.close();
                }
                RandomInputStream randomFileInputStream = kssUploadFile.getRandomFileInputStream();
                try {
                    thumbnailUploader = this;
                    kssUploadInfo2 = kssUploadInfo;
                    kssTransferStopper2 = kssTransferStopper;
                    try {
                        UploadThumbnailInfo uploadThumbnailInfoUploadThumbnail = thumbnailUploader.uploadThumbnail(randomFileInputStream, kssUploadFile.fileSize, kssUploadInfo2, kssTransferStopper2);
                        transferStep2 = TransferStep.UPLOAD_THUMBNAIL_REQUEST_XMS_HTTP;
                        if (uploadThumbnailInfoUploadThumbnail == null) {
                            throw new KscRuntimeException(ErrorCode.ILLEGAL_PROCESS, "Return thumbnail info is null");
                        }
                        if (uploadThumbnailInfoUploadThumbnail.isComplete()) {
                            kssUploadInfo2.getRequestResult().getBlock(0).exist = true;
                            thumbnailUploader.setCommitThumbnailJsonStr(uploadThumbnailInfoUploadThumbnail);
                            if (randomFileInputStream != null) {
                                try {
                                    randomFileInputStream.close();
                                    return;
                                } catch (Throwable unused) {
                                    return;
                                }
                            }
                            return;
                        }
                        if (uploadThumbnailInfoUploadThumbnail.isSessionExpired()) {
                            kssUploadInfo2.markBroken();
                            SessionExpiredException sessionExpiredException = new SessionExpiredException(uploadThumbnailInfoUploadThumbnail.stat, transferStep2);
                            Log.w(LOG_TAG, "Exception in upload thumbnail", sessionExpiredException);
                            throw sessionExpiredException;
                        }
                        if (uploadThumbnailInfoUploadThumbnail.canRetry() && atomicInteger.decrementAndGet() > 0) {
                            Log.d(LOG_TAG, "retry upload thumbnail file");
                            if (randomFileInputStream != null) {
                                randomFileInputStream.close();
                            }
                        } else {
                            if (!uploadThumbnailInfoUploadThumbnail.needRetry() || atomicInteger.decrementAndGet() <= 0) {
                                ServerMsgException serverMsgException = new ServerMsgException(200, uploadThumbnailInfoUploadThumbnail.stat, transferStep2);
                                Log.w(LOG_TAG, "Exception in upload thumbnail file", serverMsgException);
                                kssUploadInfo2.markBroken();
                                throw serverMsgException;
                            }
                            Log.d(LOG_TAG, "retry upload thumbnail");
                            if (randomFileInputStream != null) {
                                try {
                                    randomFileInputStream.close();
                                } catch (Throwable unused2) {
                                }
                            }
                        }
                        this = thumbnailUploader;
                        randomInputStream = randomFileInputStream;
                        kssUploadInfo = kssUploadInfo2;
                        kssTransferStopper = kssTransferStopper2;
                    } catch (Throwable th) {
                        th = th;
                        randomInputStream = randomFileInputStream;
                        try {
                            if (!ErrorHelper.isNetworkException(th) || atomicInteger.decrementAndGet() <= 0) {
                                throw KscException.newException(th, null, transferStep2);
                            }
                            if (Thread.interrupted()) {
                                throw new InterruptedException();
                            }
                            Thread.sleep(5000L);
                            if (randomInputStream != null) {
                                try {
                                    randomInputStream.close();
                                } catch (Throwable unused3) {
                                }
                            }
                            this = thumbnailUploader;
                        } catch (Throwable th2) {
                            if (randomInputStream == null) {
                                throw th2;
                            }
                            try {
                                randomInputStream.close();
                                throw th2;
                            } catch (Throwable unused4) {
                                throw th2;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    thumbnailUploader = this;
                    kssUploadInfo2 = kssUploadInfo;
                    kssTransferStopper2 = kssTransferStopper;
                }
            } catch (Throwable th4) {
                th = th4;
                thumbnailUploader = this;
                kssUploadInfo2 = kssUploadInfo;
                kssTransferStopper2 = kssTransferStopper;
            }
        }
    }

    private UploadThumbnailInfo uploadThumbnail(RandomInputStream randomInputStream, long j, KssUploadInfo kssUploadInfo, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        ThumbnailUploader thumbnailUploader;
        RandomInputStream randomInputStream2;
        long j2;
        KssTransferStopper kssTransferStopper2;
        IKssUploadRequestResult requestResult = kssUploadInfo.getRequestResult();
        String[] nodeUrls = requestResult.getNodeUrls();
        if (nodeUrls == null || nodeUrls.length <= 0) {
            throw new IllegalArgumentException("No available urls.");
        }
        int i = 0;
        while (i < nodeUrls.length) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            Uri.Builder builderBuildUpon = Uri.parse(nodeUrls[i] + KssDef.FUNC_UPLOAD_THUMBNAIL).buildUpon();
            builderBuildUpon.appendQueryParameter("meta", requestResult.getFileMeta());
            try {
                thumbnailUploader = this;
                randomInputStream2 = randomInputStream;
                j2 = j;
                kssTransferStopper2 = kssTransferStopper;
                try {
                    return thumbnailUploader._uploadThumbnail(builderBuildUpon.build(), randomInputStream2, j2, requestResult, kssTransferStopper2);
                } catch (Exception e) {
                    e = e;
                    Exception exc = e;
                    ErrorHelper.handleInterruptException(exc);
                    if (ErrorHelper.isStopByCallerException(exc) || i >= nodeUrls.length - 1) {
                        throw KscException.newException(exc, "Failed when upload thumbnail", TransferStep.UPLOAD_THUMBNAIL_PREPARE_STREAM);
                    }
                    i++;
                    this = thumbnailUploader;
                    randomInputStream = randomInputStream2;
                    j = j2;
                    kssTransferStopper = kssTransferStopper2;
                }
            } catch (Exception e2) {
                e = e2;
                thumbnailUploader = this;
                randomInputStream2 = randomInputStream;
                j2 = j;
                kssTransferStopper2 = kssTransferStopper;
            }
        }
        return null;
    }

    private UploadThumbnailInfo _uploadThumbnail(Uri uri, RandomInputStream randomInputStream, long j, IKssUploadRequestResult iKssUploadRequestResult, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        try {
            return doUpload(uri, new DecoderInputStream(randomInputStream, getUploadThumbnailDecoder(iKssUploadRequestResult), 16384), j, kssTransferStopper);
        } catch (InvalidKeyException e) {
            throw KscException.newException(e, null, TransferStep.UPLOAD_THUMBNAIL_PREPARE_ENCODE_KEY);
        }
    }

    private UploadThumbnailInfo doUpload(Uri uri, DecoderInputStream decoderInputStream, long j, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        Object obj = null;
        try {
            KscHttpRequest kscHttpRequest = new KscHttpRequest(KscHttpRequest.HttpMethod.POST, uri, (IKscDecoder) null, (IKscTransferListener) null);
            kscHttpRequest.setPostEntity(new KssInputStreamEntity(decoderInputStream, j));
            KscHttpResponse kscHttpResponseExecute = this.mTransmitter.execute(kscHttpRequest, 4, kssTransferStopper);
            ErrorHelper.throwError(kscHttpResponseExecute, TransferStep.DOWNLOAD_REQUEST_XMS_HTTP);
            if (kscHttpResponseExecute.getStatusCode() != 200) {
                ServerException serverException = new ServerException(kscHttpResponseExecute.getStatusCode(), kscHttpResponseExecute.dump(), TransferStep.UPLOAD_THUMBNAIL_REQUEST_XMS_HTTP);
                Log.w(LOG_TAG, "Exception in doUpload", serverException);
                throw serverException;
            }
            Map<String, Object> mapContentToMap = ApiDataHelper.contentToMap(kscHttpResponseExecute, TransferStep.UPLOAD_THUMBNAIL_REQUEST_XMS_HTTP);
            UploadThumbnailInfo uploadThumbnailInfo = new UploadThumbnailInfo(mapContentToMap);
            if (mapContentToMap instanceof IObtainable) {
                ((IObtainable) mapContentToMap).recycle();
            }
            return uploadThumbnailInfo;
        } catch (Throwable th) {
            if (obj instanceof IObtainable) {
                ((IObtainable) null).recycle();
            }
            throw th;
        }
    }

    private IKscDecoder getUploadThumbnailDecoder(IKssUploadRequestResult iKssUploadRequestResult) throws InvalidKeyException {
        byte[] secureKey = iKssUploadRequestResult.getSecureKey();
        if (secureKey != null) {
            return new RC4Encoder(secureKey);
        }
        if (this.mEncryptInfo.isNeedEncrypt() && this.mEncryptInfo.getRecordKey() != null) {
            return new E2EETransferDecoder(this.mEncryptInfo.getRecordKey(), this.mEncryptInfo.getRecordIV(), 1);
        }
        return new EmptyDecoder();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v3 */
    private void verifyFile(KssUploadFile kssUploadFile, KssUploadInfo kssUploadInfo) throws Throwable {
        String sha1 = kssUploadInfo.getFileInfo().getSha1();
        ?? r5 = 0;
        try {
            try {
                InputStream inputStream = kssUploadFile.getInputStream();
                try {
                    if (!TextUtils.equals(sha1, Encode.SHA1Encode(inputStream))) {
                        throw new KscException(ErrorCode.IOERR_FILE_CHANGED, "Thumbnail file has changed.", TransferStep.UPLOAD_PREPARE);
                    }
                    try {
                        inputStream.close();
                    } catch (Throwable unused) {
                    }
                } catch (IOException e) {
                    e = e;
                    throw KscException.newException(e, null, TransferStep.UPLOAD_PREPARE);
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Throwable th) {
                th = th;
                try {
                    r5.close();
                } catch (Throwable unused2) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r5 = kssUploadFile;
            r5.close();
            throw th;
        }
    }

    private void setCommitThumbnailJsonStr(UploadThumbnailInfo uploadThumbnailInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("commit_meta", uploadThumbnailInfo.commitMeta);
        jSONObject.put("meta", uploadThumbnailInfo.meta);
        jSONObject.put("resolution", uploadThumbnailInfo.resolution);
        jSONObject.put("sha1", uploadThumbnailInfo.sha1);
        jSONObject.put("size", uploadThumbnailInfo.size);
        jSONObject.put(KssDef.KEY_CLUSTER_ID, uploadThumbnailInfo.clusterId);
        this.mUploadContext.setThumbnailCommitString(jSONObject.toString());
    }
}
