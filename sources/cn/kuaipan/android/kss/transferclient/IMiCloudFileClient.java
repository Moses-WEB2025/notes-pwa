package cn.kuaipan.android.kss.transferclient;

import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;

/* JADX INFO: loaded from: classes.dex */
public interface IMiCloudFileClient {
    <T> void download(FileTransferContext fileTransferContext, T t, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException;

    <T> FileTransferContext upload(FileTransferContext fileTransferContext, T t, MiCloudFileListener miCloudFileListener, MiCloudTransferStopper miCloudTransferStopper) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException;
}
