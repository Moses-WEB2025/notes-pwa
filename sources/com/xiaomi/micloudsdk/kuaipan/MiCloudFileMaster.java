package com.xiaomi.micloudsdk.kuaipan;

import android.content.Context;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.kss.KssMaster;
import cn.kuaipan.android.kss.KssMaster.IRemote;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.opensdk.exception.ServerException;
import com.xiaomi.opensdk.file.sdk.FileDataFactory;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class MiCloudFileMaster<T extends KssMaster.IRemote> {
    private static final String TAG = "MiCloudFileMaster";
    KssMaster<T> kssMaster;

    public MiCloudFileMaster(Context context, DeprecatedMiCloudRequestor<? super T> deprecatedMiCloudRequestor) {
        this.kssMaster = new KssMaster<>(context, deprecatedMiCloudRequestor, new FileDataFactory());
    }

    public void upload(File file, T t, IKscTransferListener iKscTransferListener) throws CloudServerException, InterruptedException, KscException {
        try {
            this.kssMaster.upload(file, t, iKscTransferListener);
        } catch (ServerException e) {
            throw new CloudServerException(e.getStatusCode(), e.getMessage());
        }
    }

    public void download(T t, File file, IKscTransferListener iKscTransferListener, boolean z) throws Throwable {
        try {
            this.kssMaster.download(t, file, iKscTransferListener, z);
        } catch (ServerException e) {
            throw new CloudServerException(e.getStatusCode(), e.getMessage());
        }
    }
}
