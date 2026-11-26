package com.xiaomi.opensdk.pdc.asset;

import android.content.Context;
import android.text.TextUtils;
import cn.kuaipan.android.kss.download.KssDownloadFile;
import cn.kuaipan.android.kss.upload.KssUploadFile;
import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.MiCloudFileClient;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;
import com.xiaomi.opensdk.file.model.UploadContext;
import com.xiaomi.opensdk.pdc.Constants;
import com.xiaomi.opensdk.pdc.SyncServerAdapter;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public final class AssetFileMaster {
    private static int MAX_RETRY_TIME = 5;
    private static volatile AssetFileMaster sInstance;
    private Context mContext;

    private AssetFileMaster(Context context) {
        this.mContext = context;
    }

    public static AssetFileMaster getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AssetFileMaster.class) {
                if (sInstance == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("context can't be null");
                    }
                    sInstance = new AssetFileMaster(context);
                }
            }
        }
        return sInstance;
    }

    public AssetEntity upload(String str, SyncServerAdapter syncServerAdapter, File file, MiCloudFileListener miCloudFileListener, boolean z) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        return upload(str, syncServerAdapter, file, miCloudFileListener, (MiCloudTransferStopper) null, z);
    }

    public AssetEntity upload(String str, SyncServerAdapter syncServerAdapter, File file, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper, boolean z) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (file == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        return upload(str, syncServerAdapter, KssUploadFile.createByFile(file), miCloudFileListener, miCloudTransferStopper, z);
    }

    public AssetEntity upload(String str, SyncServerAdapter syncServerAdapter, KssUploadFile kssUploadFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper, boolean z) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (TextUtils.isEmpty(str) || syncServerAdapter == null || kssUploadFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            UploadContext uploadContext = new UploadContext(kssUploadFile, miCloudFileListener, miCloudTransferStopper, UploadEncryptInfo.Factory.create(z));
            UploadFileInfo uploadFileInfo = MiCloudFileClient.getInstance(this.mContext).getUploadFileInfo(uploadContext);
            MiCloudFileClient.getInstance(this.mContext).upload(uploadContext, uploadFileInfo);
            for (int i = 0; uploadContext.isNeedRequestUpload() && i < MAX_RETRY_TIME; i++) {
                try {
                    if (i != 0) {
                        uploadFileInfo = MiCloudFileClient.getInstance(this.mContext).getUploadFileInfo(uploadContext);
                    }
                    AssetResult assetResultRequestUploadAsset = syncServerAdapter.requestUploadAsset(str, MiCloudFileClient.getInstance(this.mContext).getRequestUploadParameter(uploadContext));
                    if (!assetResultRequestUploadAsset.isSuccess()) {
                        throw new AssetException(assetResultRequestUploadAsset.getDescription(), assetResultRequestUploadAsset.getErrorType(), assetResultRequestUploadAsset.code, assetResultRequestUploadAsset.isRetriable(), assetResultRequestUploadAsset.getRetryTime());
                    }
                    JSONObject jSONObject = assetResultRequestUploadAsset.data;
                    if (jSONObject.getBoolean("existed")) {
                        return AssetUtils.getAssetEntityFromJson(str, jSONObject);
                    }
                    uploadContext.setUploadParam(MiCloudFileClient.getInstance(this.mContext).getUploadParameter(jSONObject));
                    MiCloudFileClient.getInstance(this.mContext).upload(uploadContext, uploadFileInfo);
                } catch (AssetException e) {
                    transferException(e);
                    return null;
                } catch (JSONException e2) {
                    transferException(e2);
                    return null;
                }
            }
            AssetResult assetResultCommitAsset = syncServerAdapter.commitAsset(str, MiCloudFileClient.getInstance(this.mContext).getCommitParameter(uploadContext));
            if (!assetResultCommitAsset.isSuccess()) {
                throw new AssetException(assetResultCommitAsset.getDescription(), assetResultCommitAsset.getErrorType(), assetResultCommitAsset.code, assetResultCommitAsset.isRetriable(), assetResultCommitAsset.getRetryTime());
            }
            return AssetUtils.getAssetEntityFromJson(str, assetResultCommitAsset.data);
        } catch (E2EEException e3) {
            throw new UnretriableException(e3);
        }
    }

    public void download(String str, AssetEntity assetEntity, SyncServerAdapter syncServerAdapter, File file, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        download(str, assetEntity, syncServerAdapter, file, miCloudFileListener, (MiCloudTransferStopper) null);
    }

    public void download(String str, AssetEntity assetEntity, SyncServerAdapter syncServerAdapter, File file, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (file == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        download(str, assetEntity, syncServerAdapter, KssDownloadFile.createByFile(file), miCloudFileListener, miCloudTransferStopper);
    }

    public void download(String str, AssetEntity assetEntity, SyncServerAdapter syncServerAdapter, KssDownloadFile kssDownloadFile, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (TextUtils.isEmpty(str) || assetEntity == null || syncServerAdapter == null || kssDownloadFile == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            AssetResult assetResultRequestDownload = syncServerAdapter.requestDownload(str, assetEntity);
            if (!assetResultRequestDownload.isSuccess()) {
                throw new AssetException(assetResultRequestDownload.getDescription(), assetResultRequestDownload.getErrorType(), assetResultRequestDownload.code, assetResultRequestDownload.isRetriable(), assetResultRequestDownload.getRetryTime());
            }
            MiCloudFileClient.getInstance(this.mContext).download(kssDownloadFile, MiCloudFileClient.getInstance(this.mContext).getDownloadParameter(assetResultRequestDownload.data), miCloudFileListener, miCloudTransferStopper);
        } catch (AssetException e) {
            transferException(e);
        } catch (JSONException e2) {
            transferException(e2);
        }
    }

    public UrlEntity getAssetUrl(String str, AssetEntity assetEntity, SyncServerAdapter syncServerAdapter, MiCloudFileListener miCloudFileListener) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
        if (TextUtils.isEmpty(str) || assetEntity == null || syncServerAdapter == null) {
            throw new UnretriableException("参数错误，请参考API文档");
        }
        try {
            AssetResult assetUrl = syncServerAdapter.getAssetUrl(str, assetEntity);
            if (!assetUrl.isSuccess()) {
                throw new AssetException(assetUrl.getDescription(), assetUrl.getErrorType(), assetUrl.code, assetUrl.isRetriable(), assetUrl.getRetryTime());
            }
            return AssetUtils.getUrlEntityFromJson(assetUrl.data);
        } catch (AssetException e) {
            transferException(e);
            return null;
        } catch (JSONException e2) {
            transferException(e2);
            return null;
        }
    }

    private void transferException(Exception exc) throws AuthenticationException, RetriableException, UnretriableException {
        if (exc instanceof AssetException) {
            AssetException assetException = (AssetException) exc;
            if (assetException.errorType != null) {
                int i = AnonymousClass1.$SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[assetException.errorType.ordinal()];
                if (i == 1) {
                    throw new RetriableException(assetException.description, assetException.retrytime);
                }
                if (i == 2) {
                    throw new UnretriableException(assetException.description, assetException.errorCode);
                }
                if (i == 3) {
                    throw new AuthenticationException();
                }
                throw new UnretriableException(assetException.description);
            }
            throw new UnretriableException(assetException.toString());
        }
        if (exc instanceof JSONException) {
            throw new UnretriableException(exc);
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.opensdk.pdc.asset.AssetFileMaster$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType;

        static {
            int[] iArr = new int[Constants.ErrorType.values().length];
            $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType = iArr;
            try {
                iArr[Constants.ErrorType.RETRIABLE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.UNRETRIABLE_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.AUTH_FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
