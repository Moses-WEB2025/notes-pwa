package cn.kuaipan.android.kss;

import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.kss.KssMaster.IRemote;
import cn.kuaipan.android.kss.upload.KssUploadInfo;
import cn.kuaipan.android.kss.upload.UploadFileInfo;
import com.xiaomi.opensdk.exception.ServerException;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public interface IKssRequestor<T extends KssMaster.IRemote> {
    void commitUpload(File file, T t, KssUploadInfo kssUploadInfo) throws InterruptedException, ServerException, KscException;

    IKssDownloadRequestResult requestDownload(T t) throws InterruptedException, ServerException, KscException;

    IKssUploadRequestResult requestUpload(File file, T t, UploadFileInfo uploadFileInfo) throws InterruptedException, ServerException, KscException;
}
