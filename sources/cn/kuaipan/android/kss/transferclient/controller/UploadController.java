package cn.kuaipan.android.kss.transferclient.controller;

import cn.kuaipan.android.kss.transferclient.controller.impl.ThumbnailUploadController;
import cn.kuaipan.android.kss.transferclient.exception.SFSFileTransferException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestBadResponseException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestServiceTemporaryNotAvailableException;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface UploadController extends TransferController {
    Map<String, String> getCommitUploadHeaders();

    Map<String, String> getCommitUploadParams(int i, CommitParameter commitParameter) throws JSONException, E2EEException;

    String getCommitUploadURL(int i, CommitParameter commitParameter);

    Map<String, String> getRequestUploadHeaders();

    Map<String, String> getRequestUploadParams(int i, RequestUploadParameter requestUploadParameter) throws JSONException, E2EEException, IOException;

    String getRequestUploadURL(int i, RequestUploadParameter requestUploadParameter);

    ThumbnailUploadController getThumbnailUploadController();

    boolean needEncryptFile();

    default boolean needUploadLargeThumbnail() {
        return false;
    }

    default boolean needUploadSmallThumbnail() {
        return false;
    }

    void onCommitUploadResponse(int i, JSONObject jSONObject) throws SFSFileTransferException, SFSRequestServiceTemporaryNotAvailableException, SFSRequestBadResponseException;

    boolean onRequestUploadResponse(int i, JSONObject jSONObject) throws SFSFileTransferException, SFSRequestServiceTemporaryNotAvailableException, SFSRequestBadResponseException;
}
