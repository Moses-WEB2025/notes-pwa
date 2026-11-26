package cn.kuaipan.android.kss;

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
import cn.kuaipan.android.kss.KssMaster.IRemote;
import cn.kuaipan.android.kss.download.KssDownloader;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.KssUploader;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import cn.kuaipan.android.kss.upload.UploadTaskStore;
import cn.kuaipan.android.utils.ContextUtils;
import com.xiaomi.opensdk.exception.ServerException;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class KssMaster<T extends IRemote> implements KssDef {
    private final KssDownloader mDownloader;
    private final IKssRequestor<? super T> mRequestor;
    private final UploadTaskStore mTaskStore;
    private final KssUploader mUploader;

    public interface IRemote {
        String getIdentity();
    }

    public KssMaster(Context context, IKssRequestor<? super T> iKssRequestor, IDataFactory iDataFactory) {
        this.mRequestor = iKssRequestor;
        UploadEncryptInfo uploadEncryptInfoCreate = null;
        this.mTaskStore = iDataFactory == null ? null : new UploadTaskStore(context, iDataFactory);
        KscHttpTransmitter kscHttpTransmitter = new KscHttpTransmitter(context);
        kscHttpTransmitter.setUserAgent(4, getUserAgent(context));
        try {
            uploadEncryptInfoCreate = UploadEncryptInfo.Factory.create(false);
        } catch (Exception e) {
            Log.d("KssMaster", e.getMessage());
        }
        this.mUploader = new KssUploader(kscHttpTransmitter, this.mTaskStore, uploadEncryptInfoCreate);
        this.mDownloader = new KssDownloader(kscHttpTransmitter);
    }

    public void upload(File file, T t, IKscTransferListener iKscTransferListener) throws InterruptedException, ServerException, KscException {
        if (file == null || !file.isFile() || !file.canRead() || file.length() <= 0) {
            throw new KscRuntimeException(ErrorCode.INVALID_PARAM, file + " is not a exist file.");
        }
        UploadFileInfo fileInfo = UploadFileInfo.getFileInfo(file);
        int uploadHash = getUploadHash(file, t, fileInfo);
        boolean z = false;
        while (true) {
            KssUploadInfo uploadInfo = null;
            while (true) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                if (uploadInfo == null || uploadInfo.isBroken()) {
                    uploadInfo = getUploadInfo(file, t, fileInfo, uploadHash);
                }
                if (uploadInfo == null) {
                    if (iKscTransferListener != null) {
                        iKscTransferListener.setSendTotal(file.length());
                        iKscTransferListener.setSendPos(file.length());
                        return;
                    }
                    return;
                }
                if (uploadInfo.isCompleted()) {
                    break;
                } else {
                    this.mUploader.upload(file, iKscTransferListener, uploadHash, uploadInfo);
                }
            }
            try {
                this.mRequestor.commitUpload(file, t, uploadInfo);
                if (iKscTransferListener != null) {
                    iKscTransferListener.setSendTotal(file.length());
                    iKscTransferListener.setSendPos(file.length());
                }
                deleteUploadInfo(uploadHash);
                return;
            } catch (ServerException e) {
                if (!z) {
                    deleteUploadInfo(uploadHash);
                    z = true;
                } else {
                    throw e;
                }
            }
        }
    }

    public void download(T t, File file, IKscTransferListener iKscTransferListener, boolean z) throws Throwable {
        if (file == null) {
            throw new KscRuntimeException(ErrorCode.INVALID_PARAM, "Save path can't be null.");
        }
        IKssDownloadRequestResult iKssDownloadRequestResultRequestDownload = this.mRequestor.requestDownload(t);
        if (iKssDownloadRequestResultRequestDownload == null || iKssDownloadRequestResultRequestDownload.getStatus() != 0) {
            String message = iKssDownloadRequestResultRequestDownload == null ? null : iKssDownloadRequestResultRequestDownload.getMessage();
            if (TextUtils.isEmpty(message)) {
                throw new KscException(503000, "Unknow error when requestDownload.", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
            }
            throw new ServerMsgException(200, message, "Failed on requestDownload", TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
        }
        this.mDownloader.download(file, z, iKscTransferListener, iKssDownloadRequestResultRequestDownload);
    }

    public void cleanDownload(File file) {
        this.mDownloader.clean(file);
    }

    private KssUploadInfo getUploadInfo(File file, T t, UploadFileInfo uploadFileInfo, int i) throws InterruptedException, ServerException, KscException {
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        KssUploadInfo uploadInfo = uploadTaskStore == null ? null : uploadTaskStore.getUploadInfo(i);
        if (uploadInfo != null) {
            return uploadInfo;
        }
        KssUploadInfo kssUploadInfo = new KssUploadInfo(uploadFileInfo, this.mRequestor.requestUpload(file, t, uploadFileInfo));
        UploadTaskStore uploadTaskStore2 = this.mTaskStore;
        if (uploadTaskStore2 != null) {
            uploadTaskStore2.putUploadInfo(i, kssUploadInfo);
        }
        return kssUploadInfo;
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

    private static int getUploadHash(File file, IRemote iRemote, UploadFileInfo uploadFileInfo) {
        return (file + ":" + iRemote.getIdentity() + ":" + (uploadFileInfo == null ? "" : uploadFileInfo.getSha1())).hashCode();
    }
}
