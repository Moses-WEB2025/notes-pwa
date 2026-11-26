package cn.kuaipan.android.kss.transferclient;

import android.content.Context;
import cn.kuaipan.android.kss.transferclient.context.DownloadFileTransferContext;
import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import cn.kuaipan.android.kss.transferclient.context.UploadFileTransferContext;
import cn.kuaipan.android.kss.transferclient.controller.impl.ThumbnailUploadController;
import cn.kuaipan.android.kss.transferclient.exception.SFSFileTransferException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestBadResponseException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestServiceTemporaryNotAvailableException;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.micloudsdk.file.MiCloudFileRequestor;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MiCloudFileMasterRequestor extends MiCloudFileRequestor<FileTransferContext> {
    public MiCloudFileMasterRequestor(Context context) {
        super(context);
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected String getRequestUploadURL(FileTransferContext fileTransferContext, RequestUploadParameter requestUploadParameter) {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        return uploadFileTransferContext.controller.getRequestUploadURL(uploadFileTransferContext.retryCountDown, requestUploadParameter);
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getRequestUploadParams(FileTransferContext fileTransferContext, RequestUploadParameter requestUploadParameter) throws JSONException, E2EEException, IOException {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        return uploadFileTransferContext.controller.getRequestUploadParams(uploadFileTransferContext.retryCountDown, requestUploadParameter);
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getRequestUploadHeaders(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.getRequestUploadHeaders();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected FileTransferContext handleRequestUploadResultJson(FileTransferContext fileTransferContext, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        try {
            if (uploadFileTransferContext.controller.onRequestUploadResponse(uploadFileTransferContext.retryCountDown, jSONObject)) {
                return fileTransferContext;
            }
            return null;
        } catch (SFSFileTransferException e) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e));
        } catch (SFSRequestBadResponseException e2) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e2));
        } catch (SFSRequestServiceTemporaryNotAvailableException e3) {
            throw new RetriableException(e3, e3.getMessage(), e3.retryDelayHintMillis);
        }
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected String getCommitUploadURL(FileTransferContext fileTransferContext, CommitParameter commitParameter) {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        return uploadFileTransferContext.controller.getCommitUploadURL(uploadFileTransferContext.retryCountDown, commitParameter);
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getCommitUploadParams(FileTransferContext fileTransferContext, CommitParameter commitParameter) throws JSONException, E2EEException {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        Map<String, String> commitUploadParams = uploadFileTransferContext.controller.getCommitUploadParams(uploadFileTransferContext.retryCountDown, commitParameter);
        String encryptInfoStrOrNull = commitParameter.getEncryptInfoStrOrNull();
        if (encryptInfoStrOrNull != null) {
            commitUploadParams.put("encryptInfo", encryptInfoStrOrNull);
        }
        return commitUploadParams;
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getCommitUploadHeaders(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.getCommitUploadHeaders();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected FileTransferContext handleCommitUploadResult(FileTransferContext fileTransferContext, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException {
        UploadFileTransferContext uploadFileTransferContext = (UploadFileTransferContext) fileTransferContext;
        try {
            uploadFileTransferContext.controller.onCommitUploadResponse(uploadFileTransferContext.retryCountDown, jSONObject);
            return fileTransferContext;
        } catch (SFSFileTransferException e) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e));
        } catch (SFSRequestBadResponseException e2) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e2));
        } catch (SFSRequestServiceTemporaryNotAvailableException e3) {
            throw new RetriableException(e3, e3.getMessage(), e3.retryDelayHintMillis);
        }
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected boolean needEncryptFile(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.needEncryptFile();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected boolean needUploadLargeThumbnail(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.needUploadLargeThumbnail();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected boolean needUploadSmallThumbnail(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.needUploadSmallThumbnail();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected ThumbnailUploadController getThumbnailUploadController(FileTransferContext fileTransferContext) {
        return ((UploadFileTransferContext) fileTransferContext).controller.getThumbnailUploadController();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected String getRequestDownloadURL(FileTransferContext fileTransferContext) {
        return ((DownloadFileTransferContext) fileTransferContext).controller.getRequestDownloadURL();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getRequestDownloadParams(FileTransferContext fileTransferContext) throws JSONException {
        DownloadFileTransferContext downloadFileTransferContext = (DownloadFileTransferContext) fileTransferContext;
        return downloadFileTransferContext.controller.getRequestDownloadParams(downloadFileTransferContext.retryCountDown);
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected Map<String, String> getRequestDownloadHeaders(FileTransferContext fileTransferContext) {
        return ((DownloadFileTransferContext) fileTransferContext).controller.getRequestDownloadHeaders();
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected boolean handleRequestDownloadResultJson(FileTransferContext fileTransferContext, JSONObject jSONObject) throws AuthenticationException, RetriableException, UnretriableException {
        DownloadFileTransferContext downloadFileTransferContext = (DownloadFileTransferContext) fileTransferContext;
        try {
            downloadFileTransferContext.controller.onRequestDownloadResponse(downloadFileTransferContext.retryCountDown, jSONObject);
            return true;
        } catch (SFSFileTransferException e) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e));
        } catch (SFSRequestBadResponseException e2) {
            throw new UnretriableException(ControllerExceptionWrapper.wrap(e2));
        } catch (SFSRequestServiceTemporaryNotAvailableException e3) {
            throw new RetriableException(e3, e3.getMessage(), e3.retryDelayHintMillis);
        }
    }

    @Override // com.xiaomi.micloudsdk.file.MiCloudFileRequestor
    protected JSONObject getDownloadJson(FileTransferContext fileTransferContext, JSONObject jSONObject) throws JSONException {
        return ((DownloadFileTransferContext) fileTransferContext).controller.getDownloadJson(jSONObject);
    }
}
