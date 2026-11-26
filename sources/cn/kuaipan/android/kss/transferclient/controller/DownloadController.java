package cn.kuaipan.android.kss.transferclient.controller;

import cn.kuaipan.android.kss.transferclient.exception.SFSFileTransferException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestBadResponseException;
import cn.kuaipan.android.kss.transferclient.exception.SFSRequestServiceTemporaryNotAvailableException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface DownloadController extends TransferController {
    JSONObject getDownloadJson(JSONObject jSONObject) throws JSONException;

    Map<String, String> getRequestDownloadHeaders();

    Map<String, String> getRequestDownloadParams(int i);

    String getRequestDownloadURL();

    void onRequestDownloadResponse(int i, JSONObject jSONObject) throws SFSFileTransferException, SFSRequestServiceTemporaryNotAvailableException, SFSRequestBadResponseException;
}
