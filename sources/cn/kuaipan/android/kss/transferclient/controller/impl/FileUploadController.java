package cn.kuaipan.android.kss.transferclient.controller.impl;

import cn.kuaipan.android.kss.transferclient.controller.UploadController;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public abstract class FileUploadController implements UploadController {
    public abstract Map<String, String> getFileCommitUploadParams(int i, CommitParameter commitParameter) throws JSONException;

    public abstract Map<String, String> getFileRequestUploadParams(int i, RequestUploadParameter requestUploadParameter) throws JSONException, IOException;

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public ThumbnailUploadController getThumbnailUploadController() {
        return null;
    }

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getRequestUploadParams(int i, RequestUploadParameter requestUploadParameter) throws JSONException, E2EEException, IOException {
        Map<String, String> fileRequestUploadParams = getFileRequestUploadParams(i, requestUploadParameter);
        if (fileRequestUploadParams == null) {
            fileRequestUploadParams = new HashMap<>();
        }
        String encryptInfoStrOrNull = requestUploadParameter.getEncryptInfoStrOrNull();
        if (encryptInfoStrOrNull != null) {
            fileRequestUploadParams.put("encryptInfo", encryptInfoStrOrNull);
        }
        return fileRequestUploadParams;
    }

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getCommitUploadParams(int i, CommitParameter commitParameter) throws JSONException, E2EEException {
        Map<String, String> fileCommitUploadParams = getFileCommitUploadParams(i, commitParameter);
        if (fileCommitUploadParams == null) {
            fileCommitUploadParams = new HashMap<>();
        }
        String encryptInfoStrOrNull = commitParameter.getEncryptInfoStrOrNull();
        if (encryptInfoStrOrNull != null) {
            fileCommitUploadParams.put("encryptInfo", encryptInfoStrOrNull);
        }
        return fileCommitUploadParams;
    }
}
