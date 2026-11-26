package com.xiaomi.micloudsdk.file;

import android.content.Context;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.kss.download.DownloadDescriptorFile;
import cn.kuaipan.android.kss.download.KssDownloadFile;
import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.UploadDescriptorFile;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyServiceManager;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.MiCloudFileClient;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;
import com.xiaomi.opensdk.file.model.UploadContext;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudFileMaster<T extends FileTransferContext> {
    private Context mContext;
    private MiCloudFileRequestor<T> mRequestor;
    private final ThumbnailFileMaster<T> mThumbnailFileMaster;

    public MiCloudFileMaster(Context context, MiCloudFileRequestor<T> miCloudFileRequestor) {
        this.mContext = context;
        this.mRequestor = miCloudFileRequestor;
        this.mThumbnailFileMaster = new ThumbnailFileMaster<>(context, miCloudFileRequestor);
    }

    public T upload(T t, File file, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        return (T) upload(t, file, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public T upload(T t, File file, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        return (T) upload(t, KssUploadFile.createByFile(file), miCloudFileListener, miCloudTransferStopper);
    }

    public T upload(T t, UploadDescriptorFile uploadDescriptorFile, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        return (T) upload(t, uploadDescriptorFile, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public T upload(T t, UploadDescriptorFile uploadDescriptorFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (uploadDescriptorFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        return (T) upload(t, KssUploadFile.createByFileDescriptor(uploadDescriptorFile), miCloudFileListener, miCloudTransferStopper);
    }

    private T upload(T t, KssUploadFile kssUploadFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (!MiCloudFileMasterInjector.checkUploadConditions(this.mContext)) {
            throw new UnretriableException("Upload is forbidden by injector");
        }
        if (t == null || kssUploadFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            UploadContext uploadContext = new UploadContext(kssUploadFile, miCloudFileListener, miCloudTransferStopper, UploadEncryptInfo.Factory.create(this.mRequestor.needEncryptFile(t)));
            UploadFileInfo uploadFileInfo = MiCloudFileClient.getInstance(this.mContext).getUploadFileInfo(uploadContext);
            MiCloudFileClient.getInstance(this.mContext).upload(uploadContext, uploadFileInfo);
            TransferStep transferStep = TransferStep.UPLOAD_REQUEST_BIZ_HTTP;
            for (int i = 0; uploadContext.isNeedRequestUpload() && i < 5; i++) {
                try {
                    if (i != 0) {
                        uploadFileInfo = MiCloudFileClient.getInstance(this.mContext).getUploadFileInfo(uploadContext);
                    }
                    JSONObject jSONObjectRequestUpload = this.mRequestor.requestUpload((FileTransferContext) t, MiCloudFileClient.getInstance(this.mContext).getRequestUploadParameter(uploadContext));
                    if (jSONObjectRequestUpload.getInt("code") == 11001) {
                        AppKeyServiceManager.getInstance().setAppKeyExpired();
                        MiCloudFileClient.getInstance(this.mContext).upload(uploadContext, uploadFileInfo);
                    } else {
                        T t2 = (T) this.mRequestor.handleRequestUploadResultJson(t, jSONObjectRequestUpload);
                        if (t2 == null) {
                            uploadContext.setUploadParam(MiCloudFileClient.getInstance(this.mContext).getUploadParameterForSFS(jSONObjectRequestUpload.getJSONObject("data")));
                            MiCloudFileClient.getInstance(this.mContext).upload(uploadContext, uploadFileInfo);
                        } else {
                            uploadThumbnailIfNeed(t, uploadContext.getSha1(), kssUploadFile, miCloudTransferStopper, jSONObjectRequestUpload.getJSONObject("data").optJSONObject("encryptInfo"));
                            return t2;
                        }
                    }
                } catch (IOException | JSONException e) {
                    UnretriableException unretriableException = new UnretriableException(e);
                    unretriableException.setStep(transferStep.toString());
                    throw unretriableException;
                }
            }
            TransferStep transferStep2 = TransferStep.UPLOAD_REQUEST_COMMIT;
            JSONObject jSONObjectCommitUpload = this.mRequestor.commitUpload((FileTransferContext) t, MiCloudFileClient.getInstance(this.mContext).getCommitParameter(uploadContext));
            this.mRequestor.handleCommitUploadResult(t, jSONObjectCommitUpload);
            uploadThumbnailIfNeed(t, uploadContext.getSha1(), kssUploadFile, miCloudTransferStopper, jSONObjectCommitUpload.getJSONObject("data").optJSONObject("encryptInfo"));
            return t;
        } catch (E2EEException e2) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e2);
            throw new UnretriableException(e2, "create encrypt info failed");
        }
    }

    public void download(T t, File file, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        download(t, file, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public void download(T t, File file, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (file == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        download(t, KssDownloadFile.createByFile(file), miCloudFileListener, miCloudTransferStopper);
    }

    public void download(T t, DownloadDescriptorFile downloadDescriptorFile, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        download(t, downloadDescriptorFile, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public void download(T t, DownloadDescriptorFile downloadDescriptorFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (downloadDescriptorFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        download(t, KssDownloadFile.createByFileDescriptor(downloadDescriptorFile), miCloudFileListener, miCloudTransferStopper);
    }

    private void download(T t, KssDownloadFile kssDownloadFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (!MiCloudFileMasterInjector.checkDownloadConditions(this.mContext)) {
            throw new UnretriableException("Upload is forbidden by injector");
        }
        if (kssDownloadFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            JSONObject jSONObjectRequestDownload = this.mRequestor.requestDownload((FileTransferContext) t);
            JSONObject downloadJson = this.mRequestor.handleRequestDownloadResultJson(t, jSONObjectRequestDownload) ? this.mRequestor.getDownloadJson(t, jSONObjectRequestDownload) : null;
            if (downloadJson != null) {
                MiCloudFileClient.getInstance(this.mContext).download(kssDownloadFile, MiCloudFileClient.getInstance(this.mContext).getDownloadParameterForSFS(downloadJson), miCloudFileListener, miCloudTransferStopper);
            }
        } catch (JSONException e) {
            UnretriableException unretriableException = new UnretriableException(e);
            unretriableException.setStep(TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP.toString());
            throw unretriableException;
        }
    }

    private void uploadThumbnailIfNeed(T t, String str, KssUploadFile kssUploadFile, MiCloudTransferStopper miCloudTransferStopper, JSONObject jSONObject) throws JSONException, InterruptedException, AuthenticationException, RetriableException, IOException, UnretriableException {
        this.mThumbnailFileMaster.uploadThumbnailIfNeed(t, kssUploadFile.fileAbsPath, miCloudTransferStopper, jSONObject != null ? OriginFileAndThumbRelatedInfo.Factory.create(str, jSONObject) : null);
    }
}
