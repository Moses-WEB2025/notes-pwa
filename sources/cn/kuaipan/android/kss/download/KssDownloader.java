package cn.kuaipan.android.kss.download;

import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.ErrorHelper;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.exception.ServerException;
import cn.kuaipan.android.http.IKscDecoder;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.http.KscHttpRequest;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.http.KssTransferStopper;
import cn.kuaipan.android.kss.E2EETransferDecoder;
import cn.kuaipan.android.kss.EmptyDecoder;
import cn.kuaipan.android.kss.IKssDownloadRequestResult;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.RC4Encoder;
import cn.kuaipan.android.kss.TransferStep;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import com.xiaomi.micloudsdk.stat.DownloadFileFailedStatParam;
import com.xiaomi.micloudsdk.stat.MiCloudNetEventStatInjector;
import com.xiaomi.micloudsdk.stat.MiCloudStatManager;
import com.xiaomi.micloudsdk.stat.NetFailedStatParam;
import com.xiaomi.micloudsdk.stat.NetSuccessStatParam;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpEntity;

/* JADX INFO: loaded from: classes.dex */
public class KssDownloader implements KssDef {
    private static final String LOG_TAG = "KssDownloader";
    private final KscHttpTransmitter mTransmitter;

    public KssDownloader(KscHttpTransmitter kscHttpTransmitter) {
        this.mTransmitter = kscHttpTransmitter;
    }

    public void download(File file, boolean z, IKscTransferListener iKscTransferListener, IKssDownloadRequestResult iKssDownloadRequestResult) throws Throwable {
        download(file, z, iKscTransferListener, (KssTransferStopper) null, iKssDownloadRequestResult);
    }

    public void download(File file, boolean z, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, IKssDownloadRequestResult iKssDownloadRequestResult) throws Throwable {
        download(KssDownloadFile.createByFile(file), z, iKscTransferListener, kssTransferStopper, iKssDownloadRequestResult);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0037  */
    public void download(KssDownloadFile kssDownloadFile, boolean z, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, IKssDownloadRequestResult iKssDownloadRequestResult) throws Throwable {
        Throwable th;
        KInfo kInfo;
        LoadMap loadMap;
        boolean zLoadToMap;
        IOException iOException;
        KssAccessor kssAccessor = null;
        boolean zIsCompleted = false;
        try {
            kssDownloadFile.resetFileIfNeeded(z, iKssDownloadRequestResult.getTotalSize());
            IKssDownloadRequestResult iKssDownloadRequestResult2 = iKssDownloadRequestResult;
            loadMap = new LoadMap(iKssDownloadRequestResult2, iKscTransferListener);
            try {
                File infoFile = KInfo.getInfoFile(kssDownloadFile.innerCacheFile);
                kInfo = new KInfo(infoFile);
                try {
                    if (infoFile.exists()) {
                        kInfo.load();
                        if (TextUtils.equals(kInfo.getHash(), iKssDownloadRequestResult2.getHash())) {
                            zLoadToMap = kInfo.loadToMap(loadMap);
                        } else {
                            zLoadToMap = false;
                        }
                    } else {
                        zLoadToMap = false;
                    }
                    if (!zLoadToMap && kssDownloadFile.exists()) {
                        loadMap.initSize(kssDownloadFile.currentSize);
                    }
                    try {
                        KssAccessor kssAccessor2 = kssDownloadFile.getKssAccessor();
                        try {
                            try {
                                loadMap.verify(kssAccessor2, false);
                                long totalSize = iKssDownloadRequestResult2.getTotalSize();
                                if (kssDownloadFile.currentSize != totalSize) {
                                    kssAccessor2.inflate(totalSize);
                                }
                                AtomicInteger atomicInteger = new AtomicInteger(3);
                                while (true) {
                                    zIsCompleted = loadMap.isCompleted();
                                    if (!zIsCompleted) {
                                        if (Thread.interrupted()) {
                                            throw new InterruptedException();
                                        }
                                        try {
                                            download(iKssDownloadRequestResult2, kssAccessor2, loadMap, kssTransferStopper, atomicInteger);
                                            atomicInteger.set(3);
                                        } catch (KscException e) {
                                            if (ErrorHelper.isNetworkException(e) && atomicInteger.decrementAndGet() >= 0) {
                                                Thread.sleep(5000L);
                                            } else {
                                                throw e;
                                            }
                                        } catch (E2EEException e2) {
                                            throw KscException.newException(e2, "Failed when download kss block", TransferStep.DOWNLOAD_PREPARE_E2EE_APPKEY);
                                        } catch (InvalidKeyException e3) {
                                            throw KscException.newException(e3, "Failed when download kss block.", TransferStep.DOWNLOAD_PREPARE_DECODE_KEY);
                                        }
                                        iKssDownloadRequestResult2 = iKssDownloadRequestResult;
                                    } else {
                                        long modifyTime = iKssDownloadRequestResult.getModifyTime();
                                        if (modifyTime > 0) {
                                            kssDownloadFile.setLastModifyTime(modifyTime);
                                        }
                                        if (kssAccessor2 != null) {
                                            try {
                                                kssAccessor2.close();
                                            } catch (Throwable unused) {
                                            }
                                        }
                                        if (zIsCompleted) {
                                            kInfo.delete();
                                            return;
                                        }
                                        boolean zInterrupted = Thread.interrupted();
                                        kInfo.setHash(iKssDownloadRequestResult.getHash());
                                        kInfo.setLoadMap(loadMap);
                                        kInfo.save();
                                        if (zInterrupted) {
                                            Thread.currentThread().interrupt();
                                            return;
                                        }
                                        return;
                                    }
                                }
                                throw e;
                            } catch (IOException e4) {
                                iOException = e4;
                                kssAccessor = kssAccessor2;
                                throw KscException.newException(iOException, "Local IO error, when prepare kss download.", TransferStep.DOWNLOAD_PREPARE);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            kssAccessor = kssAccessor2;
                            if (kssAccessor != null) {
                                try {
                                    kssAccessor.close();
                                } catch (Throwable unused2) {
                                }
                            }
                            if (kInfo == null) {
                                throw th;
                            }
                            if (zIsCompleted) {
                                kInfo.delete();
                                throw th;
                            }
                            if (loadMap != null) {
                                boolean zInterrupted2 = Thread.interrupted();
                                kInfo.setHash(iKssDownloadRequestResult.getHash());
                                kInfo.setLoadMap(loadMap);
                                kInfo.save();
                                if (zInterrupted2) {
                                    Thread.currentThread().interrupt();
                                    throw th;
                                }
                                throw th;
                            }
                            throw th;
                        }
                    } catch (IOException e5) {
                        iOException = e5;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
                kInfo = null;
            }
        } catch (Throwable th5) {
            th = th5;
            kInfo = null;
            loadMap = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:111:0x0223 A[Catch: all -> 0x0255, TRY_LEAVE, TryCatch #12 {all -> 0x0255, blocks: (B:75:0x01b9, B:77:0x01c1, B:109:0x021a, B:111:0x0223, B:124:0x024c, B:125:0x0254, B:123:0x0248), top: B:144:0x01b9 }] */
    /* JADX WARN: Code duplicated, block: B:122:0x0245  */
    /* JADX WARN: Code duplicated, block: B:123:0x0248 A[Catch: all -> 0x0255, TryCatch #12 {all -> 0x0255, blocks: (B:75:0x01b9, B:77:0x01c1, B:109:0x021a, B:111:0x0223, B:124:0x024c, B:125:0x0254, B:123:0x0248), top: B:144:0x01b9 }] */
    /* JADX WARN: Code duplicated, block: B:130:0x025b  */
    /* JADX WARN: Code duplicated, block: B:131:0x0263  */
    /* JADX WARN: Code duplicated, block: B:133:0x0268  */
    /* JADX WARN: Code duplicated, block: B:177:0x0243 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:180:0x0276 A[EDGE_INSN: B:180:0x0276->B:138:0x0276 BREAK  A[LOOP:1: B:11:0x002c->B:120:0x023d], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:80:0x01c9  */
    private void download(IKssDownloadRequestResult iKssDownloadRequestResult, KssAccessor kssAccessor, LoadMap loadMap, KssTransferStopper kssTransferStopper, AtomicInteger atomicInteger) throws Throwable {
        LoadRecorder loadRecorder;
        KscHttpRequest kscHttpRequest;
        KscHttpResponse kscHttpResponse;
        KscHttpRequest kscHttpRequest2;
        KscHttpResponse kscHttpResponseExecute;
        String strDump;
        long contentLength;
        String simpleName;
        LoadRecorder loadRecorder2;
        KssDownloader kssDownloader = this;
        LoadRecorder loadRecorderObtainRecorder = loadMap.obtainRecorder();
        while (loadRecorderObtainRecorder != null) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            long start = loadRecorderObtainRecorder.getSpace().getStart();
            String[] blockUrls = iKssDownloadRequestResult.getBlockUrls(start);
            long blockStart = start - loadMap.getBlockStart(start);
            if (blockUrls == null || blockUrls.length <= 0) {
                throw new IllegalArgumentException("No available urls to download.");
            }
            int i = 0;
            while (i < blockUrls.length) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                StepHolder stepHolder = new StepHolder();
                stepHolder.transferStep = TransferStep.DOWNLOAD_REQUEST_XMS_HTTP;
                try {
                    IKscDecoder downloadDecoder = kssDownloader.getDownloadDecoder(iKssDownloadRequestResult, start);
                    downloadDecoder.init();
                    String str = blockUrls[i];
                    KscHttpRequest kscHttpRequest3 = new KscHttpRequest(KscHttpRequest.HttpMethod.GET, str, downloadDecoder, (IKscTransferListener) null);
                    if (blockStart > 0) {
                        try {
                            kscHttpRequest3.getRequest().addHeader("Partial-Download", "supported");
                            loadRecorder = loadRecorderObtainRecorder;
                            try {
                                try {
                                    kscHttpRequest3.getRequest().addHeader("Range", "bytes=" + blockStart + "-");
                                    downloadDecoder.skip(blockStart);
                                } catch (Exception e) {
                                    e = e;
                                    kscHttpRequest2 = kscHttpRequest3;
                                    kscHttpResponse = null;
                                    ErrorHelper.handleInterruptException(e);
                                    if (ErrorHelper.isStopByCallerException(e)) {
                                    }
                                    if (kscHttpResponse == null) {
                                        strDump = "No response.";
                                    } else {
                                        strDump = kscHttpResponse.dump();
                                    }
                                    throw KscException.newException(e, strDump, stepHolder.transferStep);
                                }
                            } catch (Throwable th) {
                                th = th;
                                kscHttpRequest = kscHttpRequest3;
                                kscHttpResponseExecute = null;
                                if (kscHttpRequest != null) {
                                    kscHttpRequest.getRequest().abort();
                                } else {
                                    kssDownloader.releaseResponse(kscHttpResponseExecute);
                                }
                                if (loadRecorder != null) {
                                    loadRecorder.recycle();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            loadRecorder = loadRecorderObtainRecorder;
                        } catch (Throwable th2) {
                            th = th2;
                            loadRecorder = loadRecorderObtainRecorder;
                        }
                    } else {
                        loadRecorder = loadRecorderObtainRecorder;
                    }
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        try {
                            kscHttpResponseExecute = kssDownloader.mTransmitter.execute(kscHttpRequest3, 4, kssTransferStopper);
                            try {
                                try {
                                    long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                                    if (kscHttpResponseExecute.getResponse() != null) {
                                        try {
                                            HttpEntity entity = kscHttpResponseExecute.getResponse().getEntity();
                                            if (entity != null) {
                                                contentLength = entity.getContentLength();
                                            } else {
                                                contentLength = 0;
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            kscHttpRequest = kscHttpRequest3;
                                        }
                                    } else {
                                        contentLength = 0;
                                    }
                                    int statusCode = kscHttpResponseExecute.getStatusCode();
                                    Throwable error = kscHttpResponseExecute.getError();
                                    if (error == null) {
                                        simpleName = "";
                                    } else {
                                        simpleName = error.getClass().getSimpleName();
                                    }
                                    long j = contentLength;
                                    String str2 = simpleName;
                                    MiCloudStatManager.getInstance().addHttpEvent(str, jCurrentTimeMillis2, j, statusCode, str2);
                                    if (error == null) {
                                        try {
                                            MiCloudNetEventStatInjector miCloudNetEventStatInjector = MiCloudNetEventStatInjector.getInstance();
                                            NetSuccessStatParam netSuccessStatParam = new NetSuccessStatParam(str, jCurrentTimeMillis, jCurrentTimeMillis2, j, statusCode, 0);
                                            kscHttpRequest2 = kscHttpRequest3;
                                            try {
                                                try {
                                                    miCloudNetEventStatInjector.addNetSuccessEvent(netSuccessStatParam);
                                                    if (statusCode != 200 && statusCode != 206) {
                                                        MiCloudNetEventStatInjector.getInstance().addAddDownloadFileFailedEvent(DownloadFileFailedStatParam.createDownloadFileFailedStatParam(str, statusCode, ""));
                                                        ServerException serverException = new ServerException(statusCode, "download url=" + str, stepHolder.transferStep);
                                                        Log.e(LOG_TAG, "ServerException in download: " + serverException.getSimpleMessage());
                                                        throw serverException;
                                                    }
                                                    kssDownloader = this;
                                                    kscHttpResponse = kscHttpResponseExecute;
                                                    loadRecorder2 = loadRecorder;
                                                    try {
                                                        kssDownloader.save(kscHttpResponse, kssAccessor, loadRecorder2, atomicInteger, stepHolder);
                                                        loadRecorder = loadRecorder2;
                                                        try {
                                                            try {
                                                                stepHolder.transferStep = TransferStep.DOWNLOAD_VERIFY_FILE;
                                                                try {
                                                                    loadMap.verify(kssAccessor, true);
                                                                    kssDownloader.releaseResponse(kscHttpResponse);
                                                                    if (loadRecorder != null) {
                                                                        break;
                                                                    }
                                                                    loadRecorder.recycle();
                                                                    break;
                                                                } catch (Exception e3) {
                                                                    e = e3;
                                                                    ErrorHelper.handleInterruptException(e);
                                                                    if (!ErrorHelper.isStopByCallerException(e) || i >= blockUrls.length - 1) {
                                                                        if (kscHttpResponse == null) {
                                                                            strDump = "No response.";
                                                                        } else {
                                                                            strDump = kscHttpResponse.dump();
                                                                        }
                                                                        throw KscException.newException(e, strDump, stepHolder.transferStep);
                                                                    }
                                                                    if (kscHttpRequest2 != null) {
                                                                        kscHttpRequest2.getRequest().abort();
                                                                    } else {
                                                                        kssDownloader.releaseResponse(kscHttpResponse);
                                                                    }
                                                                    if (loadRecorder != null) {
                                                                        loadRecorder.recycle();
                                                                    }
                                                                    i++;
                                                                    loadRecorderObtainRecorder = loadRecorder;
                                                                }
                                                            } catch (Throwable th4) {
                                                                th = th4;
                                                                kscHttpResponseExecute = kscHttpResponse;
                                                                kscHttpRequest = kscHttpRequest2;
                                                                if (kscHttpRequest != null) {
                                                                    kscHttpRequest.getRequest().abort();
                                                                } else {
                                                                    kssDownloader.releaseResponse(kscHttpResponseExecute);
                                                                }
                                                                if (loadRecorder != null) {
                                                                    loadRecorder.recycle();
                                                                }
                                                                throw th;
                                                            }
                                                        } catch (Exception e4) {
                                                            e = e4;
                                                        }
                                                    } catch (Exception e5) {
                                                        e = e5;
                                                        loadRecorder = loadRecorder2;
                                                    } catch (Throwable th5) {
                                                        th = th5;
                                                        loadRecorder = loadRecorder2;
                                                        kscHttpResponseExecute = kscHttpResponse;
                                                        kscHttpRequest = kscHttpRequest2;
                                                        if (kscHttpRequest != null) {
                                                            kscHttpRequest.getRequest().abort();
                                                        } else {
                                                            kssDownloader.releaseResponse(kscHttpResponseExecute);
                                                        }
                                                        if (loadRecorder != null) {
                                                            loadRecorder.recycle();
                                                        }
                                                        throw th;
                                                    }
                                                } catch (Exception e6) {
                                                    e = e6;
                                                    kssDownloader = this;
                                                    kscHttpResponse = kscHttpResponseExecute;
                                                    ErrorHelper.handleInterruptException(e);
                                                    if (ErrorHelper.isStopByCallerException(e)) {
                                                    }
                                                    if (kscHttpResponse == null) {
                                                        strDump = "No response.";
                                                    } else {
                                                        strDump = kscHttpResponse.dump();
                                                    }
                                                    throw KscException.newException(e, strDump, stepHolder.transferStep);
                                                }
                                            } catch (Throwable th6) {
                                                th = th6;
                                                kssDownloader = this;
                                                kscHttpRequest = kscHttpRequest2;
                                                if (kscHttpRequest != null) {
                                                    kscHttpRequest.getRequest().abort();
                                                } else {
                                                    kssDownloader.releaseResponse(kscHttpResponseExecute);
                                                }
                                                if (loadRecorder != null) {
                                                    loadRecorder.recycle();
                                                }
                                                throw th;
                                            }
                                        } catch (Exception e7) {
                                            e = e7;
                                            kscHttpRequest2 = kscHttpRequest3;
                                        } catch (Throwable th7) {
                                            th = th7;
                                            kscHttpRequest2 = kscHttpRequest3;
                                        }
                                    } else {
                                        kscHttpRequest2 = kscHttpRequest3;
                                        try {
                                            MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(str, jCurrentTimeMillis, jCurrentTimeMillis2, error, 0));
                                            MiCloudNetEventStatInjector.getInstance().addAddDownloadFileFailedEvent(DownloadFileFailedStatParam.createDownloadFileFailedStatParam(str, statusCode, str2));
                                            ErrorHelper.throwError(kscHttpResponseExecute, stepHolder.transferStep);
                                            kssDownloader = this;
                                            kscHttpResponse = kscHttpResponseExecute;
                                            loadRecorder2 = loadRecorder;
                                            kssDownloader.save(kscHttpResponse, kssAccessor, loadRecorder2, atomicInteger, stepHolder);
                                            loadRecorder = loadRecorder2;
                                            stepHolder.transferStep = TransferStep.DOWNLOAD_VERIFY_FILE;
                                            loadMap.verify(kssAccessor, true);
                                            kssDownloader.releaseResponse(kscHttpResponse);
                                            if (loadRecorder != null) {
                                                break;
                                                break;
                                            } else {
                                                loadRecorder.recycle();
                                                break;
                                                break;
                                            }
                                        } catch (Throwable th8) {
                                            th = th8;
                                            kssDownloader = this;
                                        }
                                    }
                                } catch (Throwable th9) {
                                    th = th9;
                                    kscHttpRequest2 = kscHttpRequest3;
                                }
                            } catch (Exception e8) {
                                e = e8;
                                kscHttpResponse = kscHttpResponseExecute;
                                kscHttpRequest2 = kscHttpRequest3;
                            }
                        } catch (Exception e9) {
                            e = e9;
                            kscHttpRequest2 = kscHttpRequest3;
                            kscHttpResponse = null;
                            ErrorHelper.handleInterruptException(e);
                            if (ErrorHelper.isStopByCallerException(e)) {
                            }
                            if (kscHttpResponse == null) {
                                strDump = "No response.";
                            } else {
                                strDump = kscHttpResponse.dump();
                            }
                            throw KscException.newException(e, strDump, stepHolder.transferStep);
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        kscHttpRequest = kscHttpRequest3;
                        kscHttpResponseExecute = null;
                    }
                } catch (Exception e10) {
                    e = e10;
                    loadRecorder = loadRecorderObtainRecorder;
                    kscHttpResponse = null;
                    kscHttpRequest2 = null;
                } catch (Throwable th11) {
                    th = th11;
                    loadRecorder = loadRecorderObtainRecorder;
                    kscHttpRequest = null;
                }
                kscHttpRequest = kscHttpRequest2;
                if (kscHttpRequest != null) {
                    kscHttpRequest.getRequest().abort();
                } else {
                    kssDownloader.releaseResponse(kscHttpResponseExecute);
                }
                if (loadRecorder != null) {
                    loadRecorder.recycle();
                }
                throw th;
            }
            loadRecorderObtainRecorder = loadMap.obtainRecorder();
        }
    }

    private void save(KscHttpResponse kscHttpResponse, KssAccessor kssAccessor, LoadRecorder loadRecorder, AtomicInteger atomicInteger, StepHolder stepHolder) throws Throwable {
        InputStream content;
        boolean z = false;
        try {
            if (kscHttpResponse.getStatusCode() == 200 || kscHttpResponse.getStatusCode() == 206) {
                stepHolder.transferStep = TransferStep.DOWNLOAD_GET_CONTENT;
                content = kscHttpResponse.getContent();
            } else {
                content = null;
            }
            if (content == null) {
                throw new KscRuntimeException(ErrorCode.ILLEGAL_PROCESS, "Not meet exception, but no response.\n" + kscHttpResponse.dump());
            }
            stepHolder.transferStep = TransferStep.DOWNLOAD_READ_STREAM;
            byte[] bArr = new byte[524288];
            boolean z2 = false;
            while (true) {
                try {
                    int i = content.read(bArr);
                    if (i < 0) {
                        break;
                    }
                    z2 = true;
                    stepHolder.transferStep = TransferStep.DOWNLOAD_WRITE_FILE;
                    if (i > 0 && kssAccessor.write(bArr, 0, i, loadRecorder) < i) {
                        break;
                    } else {
                        stepHolder.transferStep = TransferStep.DOWNLOAD_READ_STREAM;
                    }
                } catch (Throwable th) {
                    th = th;
                    z = z2;
                    if (z) {
                        atomicInteger.set(3);
                    }
                    throw th;
                }
            }
            if (z2) {
                atomicInteger.set(3);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void releaseResponse(KscHttpResponse kscHttpResponse) throws InterruptedException {
        if (kscHttpResponse != null) {
            try {
                kscHttpResponse.release();
            } catch (Throwable th) {
                InterruptedException interruptedExceptionIsInterrupted = ErrorHelper.isInterrupted(th);
                if (interruptedExceptionIsInterrupted != null) {
                    throw interruptedExceptionIsInterrupted;
                }
            }
        }
    }

    public void clean(File file) {
        KInfo.getInfoFile(file).delete();
        file.delete();
    }

    private static class StepHolder {
        private TransferStep transferStep;

        private StepHolder() {
        }
    }

    private IKscDecoder getDownloadDecoder(IKssDownloadRequestResult iKssDownloadRequestResult, long j) throws InterruptedException, E2EEException, InvalidKeyException {
        byte[] secureKey = iKssDownloadRequestResult.getSecureKey();
        if (secureKey != null) {
            return new RC4Encoder(secureKey);
        }
        DownloadEncryptInfo downloadEncryptInfo = iKssDownloadRequestResult.getDownloadEncryptInfo();
        if (downloadEncryptInfo != null) {
            downloadEncryptInfo.fillRecordKey();
            return new E2EETransferDecoder(downloadEncryptInfo.getRecordKey(), E2EECommonUtils.calculateDecodeFileIV(j, downloadEncryptInfo.getRecordIV()), 2);
        }
        return new EmptyDecoder();
    }
}
