package com.xiaomi.opensdk.file.model;

import android.content.Context;
import android.text.TextUtils;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.exception.KscTransferStopByCallerException;
import cn.kuaipan.android.exception.NetworkException;
import cn.kuaipan.android.exception.ServerException;
import cn.kuaipan.android.exception.SessionExpiredException;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.download.DownloadEncryptInfo;
import cn.kuaipan.android.kss.download.KssDownloadFile;
import cn.kuaipan.android.kss.thumbnail.ThumbnailUploadParameter;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.TransferStopByCallerException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.sdk.KssMasterRef;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.io.File;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public final class MiCloudFileClient {
    private static volatile MiCloudFileClient sInstance;
    private KssMasterRef mKssMasterRef;

    private MiCloudFileClient(Context context) {
        this.mKssMasterRef = new KssMasterRef(context);
    }

    public static MiCloudFileClient getInstance(Context context) {
        if (sInstance == null) {
            synchronized (MiCloudFileClient.class) {
                if (sInstance == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("context can't be null");
                    }
                    sInstance = new MiCloudFileClient(context);
                }
            }
        }
        return sInstance;
    }

    public UploadFileInfo getUploadFileInfo(UploadContext uploadContext) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        try {
            return this.mKssMasterRef.getUploadFileInfo(uploadContext);
        } catch (KscException e) {
            transferException(e);
            return null;
        } catch (KscRuntimeException e2) {
            transferException(e2);
            return null;
        }
    }

    @Deprecated
    public void upload(UploadContext uploadContext) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        try {
            this.mKssMasterRef.upload(uploadContext, null);
        } catch (KscException e) {
            transferException(e);
        } catch (KscRuntimeException e2) {
            transferException(e2);
        } catch (E2EEException e3) {
            transferException(e3);
        }
    }

    public void upload(UploadContext uploadContext, UploadFileInfo uploadFileInfo) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        try {
            this.mKssMasterRef.upload(uploadContext, uploadFileInfo);
        } catch (KscException | KscRuntimeException | E2EEException e) {
            transferException(e);
        }
    }

    public void download(File file, DownloadParameter downloadParameter, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        download(file, downloadParameter, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public void download(File file, DownloadParameter downloadParameter, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (file == null) {
            throw new UnretriableException("downloadFile can't be null.");
        }
        download(KssDownloadFile.createByFile(file), downloadParameter, miCloudFileListener, miCloudTransferStopper);
    }

    public void download(KssDownloadFile kssDownloadFile, DownloadParameter downloadParameter, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (!TextUtils.isEmpty(downloadParameter.getKssDownloadString())) {
            try {
                this.mKssMasterRef.download(kssDownloadFile, downloadParameter, miCloudFileListener, miCloudTransferStopper, true);
                return;
            } catch (KscException e) {
                transferException(e);
                return;
            } catch (KscRuntimeException e2) {
                transferException(e2);
                return;
            }
        }
        throw new IllegalArgumentException("Cannot detect download sdk");
    }

    public RequestUploadParameter getRequestUploadParameter(UploadContext uploadContext) {
        return new RequestUploadParameter(uploadContext.getKssString(), null, uploadContext.getSha1(), uploadContext.getFileSize(), uploadContext.getFilePath(), uploadContext.getEncryptInfo());
    }

    public UploadParameter getUploadParameter(JSONObject jSONObject) throws JSONException {
        UploadParameter uploadParameter = new UploadParameter();
        JSONObject jSONObject2 = jSONObject.getJSONObject(FileSDKUtils.J_STORAGE);
        uploadParameter.setUploadId(jSONObject2.getString("uploadId"));
        uploadParameter.setKssUploadString(jSONObject2.getJSONObject("kss").toString());
        return uploadParameter;
    }

    public UploadParameter getUploadParameterForSFS(JSONObject jSONObject) throws JSONException {
        String string;
        UploadParameter uploadParameter = new UploadParameter();
        if (jSONObject.has("upload_id")) {
            string = jSONObject.getString("upload_id");
        } else if (jSONObject.has("uploadId")) {
            string = jSONObject.getString("uploadId");
        } else {
            throw new JSONException("Missing necessary field : upload_id / uploadId");
        }
        uploadParameter.setUploadId(string);
        uploadParameter.setKssUploadString(jSONObject.getJSONObject("kss").toString());
        return uploadParameter;
    }

    public ThumbnailUploadParameter getThumbnailParamForSFS(JSONObject jSONObject) throws JSONException {
        String string;
        ThumbnailUploadParameter thumbnailUploadParameter = new ThumbnailUploadParameter();
        JSONObject jSONObject2 = jSONObject.getJSONObject("kss");
        if (jSONObject2.has(FileSDKUtils.J_SMALL_THUMB)) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject(FileSDKUtils.J_SMALL_THUMB);
            if (jSONObject3.has("upload_id")) {
                string = jSONObject3.getString("upload_id");
            } else if (jSONObject3.has("uploadId")) {
                string = jSONObject3.getString("uploadId");
            } else {
                throw new JSONException("Missing necessary field : upload_id / uploadId");
            }
        } else if (jSONObject2.has(FileSDKUtils.J_LARGE_THUMB)) {
            JSONObject jSONObject4 = jSONObject2.getJSONObject(FileSDKUtils.J_LARGE_THUMB);
            if (jSONObject4.has("upload_id")) {
                string = jSONObject4.getString("upload_id");
            } else if (jSONObject4.has("uploadId")) {
                string = jSONObject4.getString("uploadId");
            } else {
                throw new JSONException("Missing necessary field : upload_id / uploadId");
            }
        } else {
            throw new JSONException("Missing necessary field : small / large");
        }
        thumbnailUploadParameter.setUploadId(string);
        thumbnailUploadParameter.setKssUploadString(jSONObject.getJSONObject("kss").toString());
        return thumbnailUploadParameter;
    }

    public CommitParameter getCommitParameter(UploadContext uploadContext) {
        return new CommitParameter(uploadContext.getCommitString(), null, uploadContext.getUploadId(), uploadContext.getSha1(), uploadContext.getFileSize(), uploadContext.getEncryptInfo(), uploadContext.getThumbnailCommitString());
    }

    public DownloadParameter getDownloadParameter(JSONObject jSONObject) throws JSONException {
        DownloadParameter downloadParameter = new DownloadParameter();
        downloadParameter.setKssDownloadString(jSONObject.getJSONObject(FileSDKUtils.J_STORAGE).getJSONObject("kss").toString());
        downloadParameter.setDownloadEncryptInfo(DownloadEncryptInfo.parseDownloadEncryptOrNull(jSONObject));
        return downloadParameter;
    }

    public DownloadParameter getDownloadParameterForSFS(JSONObject jSONObject) throws JSONException {
        DownloadParameter downloadParameter = new DownloadParameter();
        downloadParameter.setKssDownloadString(jSONObject.getJSONObject("kss").toString());
        downloadParameter.setDownloadEncryptInfo(DownloadEncryptInfo.parseDownloadEncryptOrNull(jSONObject));
        return downloadParameter;
    }

    public void transferException(Exception exc) throws AuthenticationException, RetriableException, UnretriableException {
        if (exc instanceof KscException) {
            KscException kscException = (KscException) exc;
            TransferStep transferStep = kscException.getTransferStep();
            Throwable cause = exc.getCause();
            if (cause instanceof KscTransferStopByCallerException) {
                throw new UnretriableException(new TransferStopByCallerException(cause));
            }
            String simpleMessage = kscException.getSimpleMessage();
            if (exc instanceof NetworkException) {
                RetriableException retriableException = new RetriableException(cause, simpleMessage, DesktopRecommendInfo.DEFAULT_CACHE_TIME);
                retriableException.setStep(transferStep.toString());
                throw retriableException;
            }
            if (exc instanceof ServerException) {
                if (((ServerException) exc).getStatusCode() / 100 == 5) {
                    RetriableException retriableException2 = new RetriableException(exc, simpleMessage, DesktopRecommendInfo.DEFAULT_CACHE_TIME);
                    retriableException2.setStep(transferStep.toString());
                    throw retriableException2;
                }
            } else if (exc instanceof SessionExpiredException) {
                Objects.requireNonNull((SessionExpiredException) exc);
                RetriableException retriableException3 = new RetriableException(exc, simpleMessage, 5000L);
                retriableException3.setStep(transferStep.toString());
                throw retriableException3;
            }
            UnretriableException unretriableException = new UnretriableException(cause, simpleMessage);
            unretriableException.setStep(transferStep.toString());
            throw unretriableException;
        }
        if (exc instanceof KscRuntimeException) {
            throw new UnretriableException(exc, ((KscRuntimeException) exc).getErrorCode());
        }
        if (exc instanceof E2EEException) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent((E2EEException) exc);
            throw new UnretriableException(exc);
        }
    }
}
