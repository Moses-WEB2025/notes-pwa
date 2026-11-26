package cn.kuaipan.android.kss.transferclient;

import android.content.Context;
import cn.kuaipan.android.KssConfig;
import cn.kuaipan.android.kss.download.DownloadDescriptorFile;
import cn.kuaipan.android.kss.transferclient.context.DownloadFileTransferContext;
import cn.kuaipan.android.kss.transferclient.context.FileTransferContext;
import cn.kuaipan.android.kss.transferclient.context.UploadFileTransferContext;
import cn.kuaipan.android.kss.transferclient.controller.DownloadController;
import cn.kuaipan.android.kss.transferclient.controller.TransferController;
import cn.kuaipan.android.kss.transferclient.controller.UploadController;
import cn.kuaipan.android.kss.transferclient.controller.impl.ThumbnailUploadController;
import cn.kuaipan.android.kss.transferclient.exception.SFSFileTransferException;
import cn.kuaipan.android.kss.transferclient.exception.SFSNetworkNotAvailableException;
import cn.kuaipan.android.kss.transferclient.retry.RetryControl;
import cn.kuaipan.android.kss.transferclient.retry.RetryDelayConfig;
import cn.kuaipan.android.kss.upload.UploadDescriptorFile;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.micloudsdk.file.MiCloudFileMaster;
import com.xiaomi.micloudsdk.file.ThumbnailFileMaster;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.TransferStopByCallerException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;
import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;
import java.io.File;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SFSFileTransferClient {
    private static final int DEFAULT_MAX_RETRY_DELAY = 10;
    private static final int DEFAULT_MIN_RETRY_DELAY = 1;
    private static final int DEFAULT_RETRY_COUNT = 3;
    private static volatile MiCloudFileMaster<FileTransferContext> sClient;
    private static volatile MiCloudFileMasterRequestor sRequestor;
    private static volatile ThumbnailFileMaster<FileTransferContext> sThumbnailClient;

    private interface TransferAction {
        void doAction(int i) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException;
    }

    public static void init(Context context, int i, long j, long j2) {
        initInner(context, i, j, j2);
    }

    public static void init(Context context) {
        initInner(context, 3, 1L, 10L);
    }

    public static <K> void upload(K k, UploadController uploadController, TransferProgressListener transferProgressListener) throws Exception {
        upload(k, uploadController, transferProgressListener, null);
    }

    public static <K> void upload(final K k, final UploadController uploadController, final TransferProgressListener transferProgressListener, MiCloudTransferStopper miCloudTransferStopper) throws Exception {
        checkInit();
        final MiCloudTransferStopper miCloudTransferStopperPrepareStopper = miCloudTransferStopper == null ? prepareStopper(uploadController) : miCloudTransferStopper;
        transfer(uploadController, new TransferAction() { // from class: cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.1
            @Override // cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.TransferAction
            public void doAction(int i) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
                Object obj = k;
                if (obj instanceof File) {
                    SFSFileTransferClient.sClient.upload(new UploadFileTransferContext(i, uploadController), (File) k, SFSFileTransferClient.prepareListener(transferProgressListener, true), miCloudTransferStopperPrepareStopper);
                } else {
                    if (obj instanceof UploadDescriptorFile) {
                        SFSFileTransferClient.sClient.upload(new UploadFileTransferContext(i, uploadController), (UploadDescriptorFile) k, SFSFileTransferClient.prepareListener(transferProgressListener, true), miCloudTransferStopperPrepareStopper);
                        return;
                    }
                    throw new IllegalArgumentException("upload file must instanceof File or UploadDescriptorFile");
                }
            }
        }, miCloudTransferStopper == null);
    }

    public static void uploadThumbnail(File file, ThumbnailUploadController thumbnailUploadController, int i, String str, JSONObject jSONObject) throws Exception {
        uploadThumbnail(file, thumbnailUploadController, i, str, null, jSONObject);
    }

    public static void uploadThumbnail(final File file, final ThumbnailUploadController thumbnailUploadController, final int i, final String str, MiCloudTransferStopper miCloudTransferStopper, final JSONObject jSONObject) throws Exception {
        checkInit();
        final MiCloudTransferStopper miCloudTransferStopperPrepareStopper = miCloudTransferStopper == null ? prepareStopper(thumbnailUploadController) : miCloudTransferStopper;
        transfer(thumbnailUploadController, new TransferAction() { // from class: cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.2
            @Override // cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.TransferAction
            public void doAction(int i2) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
                thumbnailUploadController.setCurrentResolution(i);
                SFSFileTransferClient.sThumbnailClient.uploadThumbnail(new UploadFileTransferContext(i2, thumbnailUploadController), file, miCloudTransferStopperPrepareStopper, i, str, jSONObject);
            }
        }, miCloudTransferStopper == null);
    }

    public static <K> void download(K k, DownloadController downloadController, TransferProgressListener transferProgressListener) throws Exception {
        download(k, downloadController, transferProgressListener, null);
    }

    public static <K> void download(final K k, final DownloadController downloadController, final TransferProgressListener transferProgressListener, MiCloudTransferStopper miCloudTransferStopper) throws Exception {
        checkInit();
        final MiCloudTransferStopper miCloudTransferStopperPrepareStopper = miCloudTransferStopper == null ? prepareStopper(downloadController) : miCloudTransferStopper;
        transfer(downloadController, new TransferAction() { // from class: cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.3
            @Override // cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.TransferAction
            public void doAction(int i) throws InterruptedException, AuthenticationException, RetriableException, UnretriableException {
                Object obj = k;
                if (obj instanceof File) {
                    SFSFileTransferClient.sClient.download(new DownloadFileTransferContext(i, downloadController), (File) k, SFSFileTransferClient.prepareListener(transferProgressListener, false), miCloudTransferStopperPrepareStopper);
                } else {
                    if (obj instanceof DownloadDescriptorFile) {
                        SFSFileTransferClient.sClient.download(new DownloadFileTransferContext(i, downloadController), (DownloadDescriptorFile) k, SFSFileTransferClient.prepareListener(transferProgressListener, false), miCloudTransferStopperPrepareStopper);
                        return;
                    }
                    throw new IllegalArgumentException("download file must instanceof File or DownloadDescriptorFile");
                }
            }
        }, miCloudTransferStopper == null);
    }

    private static void initInner(Context context, int i, long j, long j2) {
        KssConfig.FIX_OUT_OF_THIN_AIR_INTERRUPT_EXCEPTION = true;
        RetryControl.setMaxRetryLimit(i);
        RetryDelayConfig.setRetryDelaySeconds(j, j2);
        sRequestor = new MiCloudFileMasterRequestor(context);
        sClient = new MiCloudFileMaster<>(context, sRequestor);
        sThumbnailClient = new ThumbnailFileMaster<>(context, sRequestor);
    }

    private static void checkInit() {
        if (sClient == null || sRequestor == null) {
            throw new IllegalStateException("SFSFileTransferClient not init yet.");
        }
    }

    private static MiCloudTransferStopper prepareStopper(final TransferController transferController) {
        return new MiCloudTransferStopper() { // from class: cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.4
            @Override // com.xiaomi.opensdk.file.model.MiCloudTransferStopper
            public boolean checkStop() {
                try {
                    transferController.checkNetwork();
                    return false;
                } catch (SFSNetworkNotAvailableException unused) {
                    return true;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MiCloudFileListener prepareListener(final TransferProgressListener transferProgressListener, final boolean z) {
        return new MiCloudFileListener() { // from class: cn.kuaipan.android.kss.transferclient.SFSFileTransferClient.5
            @Override // com.xiaomi.opensdk.file.model.MiCloudFileListener
            public void onDataSended(long j, long j2) {
                TransferProgressListener transferProgressListener2;
                if (!z || (transferProgressListener2 = transferProgressListener) == null) {
                    return;
                }
                transferProgressListener2.onProgress(j, j2);
            }

            @Override // com.xiaomi.opensdk.file.model.MiCloudFileListener
            public void onDataReceived(long j, long j2) {
                TransferProgressListener transferProgressListener2;
                if (z || (transferProgressListener2 = transferProgressListener) == null) {
                    return;
                }
                transferProgressListener2.onProgress(j, j2);
            }
        };
    }

    private static void transfer(TransferController transferController, TransferAction transferAction, boolean z) throws Exception {
        RetryControl retryControl = new RetryControl();
        while (retryControl.loopForRetry()) {
            transferController.checkNetwork();
            try {
                transferAction.doAction(retryControl.getRetryCountDown());
                retryControl.done();
            } catch (AuthenticationException e) {
                throw new SFSFileTransferException(e);
            } catch (RetriableException e2) {
                retryControl.retry(new SFSFileTransferException(e2), e2.getRetryTime());
            } catch (UnretriableException e3) {
                Throwable cause = e3.getCause();
                if (cause instanceof ControllerExceptionWrapper) {
                    cause = cause.getCause();
                } else if (!(cause instanceof E2EEException)) {
                    cause = e3;
                }
                if (cause instanceof SFSFileTransferException) {
                    throw ((SFSFileTransferException) cause);
                }
                if ((cause.getCause() instanceof TransferStopByCallerException) && z) {
                    throw new SFSNetworkNotAvailableException(e3);
                }
                throw new SFSFileTransferException(cause);
            }
        }
    }
}
