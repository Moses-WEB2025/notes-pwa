package cn.kuaipan.android.kss.upload;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.ErrorHelper;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.exception.ServerException;
import cn.kuaipan.android.exception.ServerMsgException;
import cn.kuaipan.android.exception.SessionExpiredException;
import cn.kuaipan.android.http.DecoderInputStream;
import cn.kuaipan.android.http.IKscDecoder;
import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.http.KscHttpRequest;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.http.KscHttpTransmitter;
import cn.kuaipan.android.http.KssTransferStopper;
import cn.kuaipan.android.kss.CRC32Encoder;
import cn.kuaipan.android.kss.E2EETransferDecoder;
import cn.kuaipan.android.kss.EmptyDecoder;
import cn.kuaipan.android.kss.FileTranceListener;
import cn.kuaipan.android.kss.IKssUploadRequestResult;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.RC4Encoder;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.utils.ApiDataHelper;
import cn.kuaipan.android.utils.Encode;
import cn.kuaipan.android.utils.IObtainable;
import cn.kuaipan.android.utils.RandomInputStream;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import com.xiaomi.micloudsdk.stat.MiCloudStatManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpEntity;

/* JADX INFO: loaded from: classes.dex */
public class KssUploader implements KssDef {
    private static final String LOG_TAG = "KssUploader";
    private static final long SLEEP_DUR = 5000;
    public static volatile boolean sBreakForUT;
    private final UploadEncryptInfo mEncryptInfo;
    private final UploadTaskStore mTaskStore;
    private final KscHttpTransmitter mTransmitter;

    public KssUploader(KscHttpTransmitter kscHttpTransmitter, UploadTaskStore uploadTaskStore, UploadEncryptInfo uploadEncryptInfo) {
        this.mTaskStore = uploadTaskStore;
        this.mTransmitter = kscHttpTransmitter;
        this.mEncryptInfo = uploadEncryptInfo;
    }

    public void upload(File file, IKscTransferListener iKscTransferListener, int i, KssUploadInfo kssUploadInfo) throws InterruptedException, KscException {
        upload(file, iKscTransferListener, (KssTransferStopper) null, i, kssUploadInfo);
    }

    public void upload(File file, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, int i, KssUploadInfo kssUploadInfo) throws InterruptedException, KscException {
        upload(KssUploadFile.createByFile(file), iKscTransferListener, kssTransferStopper, i, kssUploadInfo);
    }

    public void upload(KssUploadFile kssUploadFile, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, int i, KssUploadInfo kssUploadInfo) throws InterruptedException, KscException {
        FileTranceListener fileTranceListener;
        if (iKscTransferListener != null) {
            fileTranceListener = new FileTranceListener(iKscTransferListener, true);
            iKscTransferListener.setSendTotal(kssUploadFile.fileSize);
        } else {
            fileTranceListener = null;
        }
        FileTranceListener fileTranceListener2 = fileTranceListener;
        int i2 = 0;
        while (!Thread.interrupted()) {
            Log.d(LOG_TAG, "upload blockIndex: " + i2);
            IKssUploadRequestResult.Block block = kssUploadInfo.getRequestResult().getBlock(i2);
            if (block == null) {
                throw new KscRuntimeException(ErrorCode.ILLEGAL_PROCESS, "Block should not be null");
            }
            KssUploader kssUploader = this;
            KssUploadFile kssUploadFile2 = kssUploadFile;
            KssTransferStopper kssTransferStopper2 = kssTransferStopper;
            int i3 = i;
            KssUploadInfo kssUploadInfo2 = kssUploadInfo;
            if (!block.isComplete()) {
                kssUploader.uploadBlock(i3, kssUploadFile2, fileTranceListener2, kssTransferStopper2, kssUploadInfo2, i2);
            }
            i2++;
            if (kssUploadInfo2.isCompleted()) {
                return;
            }
            this = kssUploader;
            i = i3;
            kssUploadFile = kssUploadFile2;
            kssTransferStopper = kssTransferStopper2;
            kssUploadInfo = kssUploadInfo2;
        }
        throw new InterruptedException();
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01d2 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:119:0x00b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:131:0x007d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:135:0x0059 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:139:0x013a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x0111 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x01c5 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:19:0x0064  */
    /* JADX WARN: Code duplicated, block: B:28:0x0085 A[Catch: all -> 0x014d, TRY_LEAVE, TryCatch #5 {all -> 0x014d, blocks: (B:26:0x007f, B:28:0x0085), top: B:115:0x007f }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:42:0x00cb A[Catch: all -> 0x014b, TRY_ENTER, TryCatch #3 {all -> 0x014b, blocks: (B:32:0x00ac, B:39:0x00c3, B:42:0x00cb, B:44:0x00d1, B:46:0x00d7, B:50:0x00fd, B:52:0x0103, B:57:0x0111, B:58:0x0139, B:59:0x013a, B:60:0x014a), top: B:112:0x00c3 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x00fd A[Catch: all -> 0x014b, TRY_ENTER, TryCatch #3 {all -> 0x014b, blocks: (B:32:0x00ac, B:39:0x00c3, B:42:0x00cb, B:44:0x00d1, B:46:0x00d7, B:50:0x00fd, B:52:0x0103, B:57:0x0111, B:58:0x0139, B:59:0x013a, B:60:0x014a), top: B:112:0x00c3 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x0103 A[Catch: all -> 0x014b, TryCatch #3 {all -> 0x014b, blocks: (B:32:0x00ac, B:39:0x00c3, B:42:0x00cb, B:44:0x00d1, B:46:0x00d7, B:50:0x00fd, B:52:0x0103, B:57:0x0111, B:58:0x0139, B:59:0x013a, B:60:0x014a), top: B:112:0x00c3 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x0162  */
    /* JADX WARN: Code duplicated, block: B:86:0x01a2 A[Catch: all -> 0x01cb, TryCatch #2 {all -> 0x01cb, blocks: (B:84:0x019c, B:86:0x01a2, B:88:0x01a8, B:90:0x01ae, B:94:0x01bf, B:95:0x01c4, B:96:0x01c5, B:97:0x01ca), top: B:111:0x019c }] */
    private void uploadBlock(int i, KssUploadFile kssUploadFile, FileTranceListener fileTranceListener, KssTransferStopper kssTransferStopper, KssUploadInfo kssUploadInfo, int i2) throws InterruptedException, KscException {
        RandomInputStream randomFileInputStream;
        IKscTransferListener blockListener;
        UploadBlockResponse uploadBlockResponseUploadBlock;
        TransferStep transferStep;
        long j = KssDef.BLOCKSIZE;
        long j2 = ((long) i2) * KssDef.BLOCKSIZE;
        IKssUploadRequestResult requestResult = kssUploadInfo.getRequestResult();
        Log.d(LOG_TAG, "RC4 key:" + Arrays.toString(requestResult.getSecureKey()));
        TransferStep transferStep2 = TransferStep.UPLOAD_PREPARE;
        AtomicInteger atomicInteger = new AtomicInteger(3);
        RandomInputStream randomInputStream = null;
        TransferStep transferStep3 = transferStep2;
        RandomInputStream randomInputStream2 = null;
        while (atomicInteger.get() >= 0) {
            try {
                transferStep3 = TransferStep.UPLOAD_PREPARE_ENCODE_KEY;
                TransferStep transferStep4 = TransferStep.UPLOAD_PREPARE_STREAM;
                if (randomInputStream2 == null) {
                    randomFileInputStream = kssUploadFile.getRandomFileInputStream();
                    if (fileTranceListener != null) {
                        fileTranceListener.setSendPos(j2);
                    }
                    if (fileTranceListener == null) {
                        blockListener = randomInputStream;
                    } else {
                        blockListener = fileTranceListener.getBlockListener(j2);
                    }
                    uploadBlockResponseUploadBlock = uploadBlock(randomFileInputStream, requestResult, blockListener, kssTransferStopper, kssUploadInfo, i2);
                    if (uploadBlockResponseUploadBlock == null) {
                        throw new KscRuntimeException(ErrorCode.ILLEGAL_PROCESS, "Return uploadBlockResponse is null");
                    }
                    transferStep = TransferStep.UPLOAD_REQUEST_XMS_HTTP;
                    if (uploadBlockResponseUploadBlock.isComplete()) {
                        UploadBlockInfoPersist uploadBlockInfoPersist = new UploadBlockInfoPersist();
                        j2 = j2;
                        uploadBlockInfoPersist.pos = Math.min(((long) (i2 + 1)) * j, kssUploadFile.fileSize);
                        uploadBlockInfoPersist.upload_id = "";
                        IKssUploadRequestResult.Block block = requestResult.getBlock(i2);
                        block.meta = uploadBlockResponseUploadBlock.commit_meta;
                        block.exist = true;
                        updateUploadInfo(i, kssUploadInfo, uploadBlockInfoPersist);
                        if (randomFileInputStream != null) {
                            randomFileInputStream.close();
                            return;
                        }
                        return;
                    }
                    j2 = j2;
                    if (!uploadBlockResponseUploadBlock.isSessionExpired()) {
                        kssUploadInfo.markBroken();
                        deleteUploadInfo(i);
                        SessionExpiredException sessionExpiredException = new SessionExpiredException(uploadBlockResponseUploadBlock.stat, transferStep);
                        Log.w(LOG_TAG, "Exception in uploadBlock", sessionExpiredException);
                        throw sessionExpiredException;
                    }
                    if (!uploadBlockResponseUploadBlock.needBlockRetry()) {
                        if (uploadBlockResponseUploadBlock.canRetry()) {
                        }
                        ServerMsgException serverMsgException = new ServerMsgException(200, uploadBlockResponseUploadBlock.stat, "upload url=" + uploadBlockResponseUploadBlock.request_url, transferStep);
                        Log.w(LOG_TAG, "Exception in uploadBlock", serverMsgException);
                        kssUploadInfo.markBroken();
                        deleteUploadInfo(i);
                        throw serverMsgException;
                    }
                    if (uploadBlockResponseUploadBlock.canRetry()) {
                    }
                    ServerMsgException serverMsgException2 = new ServerMsgException(200, uploadBlockResponseUploadBlock.stat, "upload url=" + uploadBlockResponseUploadBlock.request_url, transferStep);
                    Log.w(LOG_TAG, "Exception in uploadBlock", serverMsgException2);
                    kssUploadInfo.markBroken();
                    deleteUploadInfo(i);
                    throw serverMsgException2;
                    randomInputStream2 = randomFileInputStream;
                    transferStep3 = transferStep;
                    randomInputStream2 = randomFileInputStream;
                    transferStep3 = transferStep;
                    if (ErrorHelper.isNetworkException(th)) {
                    }
                    throw KscException.newException(th, null, transferStep3);
                    randomInputStream2 = randomFileInputStream;
                    transferStep3 = transferStep4;
                    if (ErrorHelper.isNetworkException(th)) {
                    }
                    throw KscException.newException(th, null, transferStep3);
                }
                try {
                    try {
                        randomInputStream2.close();
                        randomInputStream2 = randomInputStream;
                        try {
                            randomFileInputStream = kssUploadFile.getRandomFileInputStream();
                            if (fileTranceListener != null) {
                                try {
                                    fileTranceListener.setSendPos(j2);
                                } catch (Throwable th) {
                                    th = th;
                                    randomInputStream2 = randomFileInputStream;
                                    transferStep3 = transferStep4;
                                }
                            }
                            if (fileTranceListener == null) {
                                blockListener = randomInputStream;
                            } else {
                                try {
                                    blockListener = fileTranceListener.getBlockListener(j2);
                                } catch (Throwable th2) {
                                    th = th2;
                                    j = j;
                                }
                            }
                            try {
                                uploadBlockResponseUploadBlock = uploadBlock(randomFileInputStream, requestResult, blockListener, kssTransferStopper, kssUploadInfo, i2);
                                if (uploadBlockResponseUploadBlock == null) {
                                    try {
                                        transferStep = TransferStep.UPLOAD_REQUEST_XMS_HTTP;
                                        try {
                                            if (uploadBlockResponseUploadBlock.isComplete()) {
                                                UploadBlockInfoPersist uploadBlockInfoPersist2 = new UploadBlockInfoPersist();
                                                j2 = j2;
                                                try {
                                                    uploadBlockInfoPersist2.pos = Math.min(((long) (i2 + 1)) * j, kssUploadFile.fileSize);
                                                    uploadBlockInfoPersist2.upload_id = "";
                                                    IKssUploadRequestResult.Block block2 = requestResult.getBlock(i2);
                                                    block2.meta = uploadBlockResponseUploadBlock.commit_meta;
                                                    block2.exist = true;
                                                    updateUploadInfo(i, kssUploadInfo, uploadBlockInfoPersist2);
                                                    if (randomFileInputStream != null) {
                                                        try {
                                                            randomFileInputStream.close();
                                                            return;
                                                        } catch (Throwable unused) {
                                                            return;
                                                        }
                                                    }
                                                    return;
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                }
                                            } else {
                                                j2 = j2;
                                                try {
                                                    if (!uploadBlockResponseUploadBlock.isSessionExpired()) {
                                                        kssUploadInfo.markBroken();
                                                        deleteUploadInfo(i);
                                                        SessionExpiredException sessionExpiredException2 = new SessionExpiredException(uploadBlockResponseUploadBlock.stat, transferStep);
                                                        Log.w(LOG_TAG, "Exception in uploadBlock", sessionExpiredException2);
                                                        throw sessionExpiredException2;
                                                    }
                                                    if (!uploadBlockResponseUploadBlock.needBlockRetry() && atomicInteger.decrementAndGet() > 0) {
                                                        verifyBlock(kssUploadFile, kssUploadInfo.getFileInfo(), i2);
                                                        Log.d(LOG_TAG, "upload needBlockRetry: " + uploadBlockResponseUploadBlock.stat);
                                                        if (randomFileInputStream != null) {
                                                            randomFileInputStream.close();
                                                        }
                                                    } else {
                                                        if (uploadBlockResponseUploadBlock.canRetry() || atomicInteger.decrementAndGet() <= 0) {
                                                            ServerMsgException serverMsgException3 = new ServerMsgException(200, uploadBlockResponseUploadBlock.stat, "upload url=" + uploadBlockResponseUploadBlock.request_url, transferStep);
                                                            Log.w(LOG_TAG, "Exception in uploadBlock", serverMsgException3);
                                                            kssUploadInfo.markBroken();
                                                            deleteUploadInfo(i);
                                                            throw serverMsgException3;
                                                        }
                                                        if (randomFileInputStream != null) {
                                                            try {
                                                                randomFileInputStream.close();
                                                            } catch (Throwable unused2) {
                                                            }
                                                        }
                                                    }
                                                    randomInputStream2 = randomFileInputStream;
                                                    transferStep3 = transferStep;
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    randomInputStream2 = randomFileInputStream;
                                                    transferStep3 = transferStep;
                                                    if (ErrorHelper.isNetworkException(th)) {
                                                    }
                                                    throw KscException.newException(th, null, transferStep3);
                                                }
                                            }
                                        } catch (Throwable th5) {
                                            th = th5;
                                            j2 = j2;
                                        }
                                        randomInputStream2 = randomFileInputStream;
                                        transferStep3 = transferStep;
                                    } catch (Throwable th6) {
                                        th = th6;
                                        randomInputStream2 = randomFileInputStream;
                                        transferStep3 = transferStep4;
                                    }
                                    if (ErrorHelper.isNetworkException(th) || atomicInteger.decrementAndGet() <= 0) {
                                        throw KscException.newException(th, null, transferStep3);
                                    }
                                    if (Thread.interrupted()) {
                                        throw new InterruptedException();
                                    }
                                    Thread.sleep(5000L);
                                    if (randomInputStream2 != null) {
                                        try {
                                            randomInputStream2.close();
                                        } catch (Throwable unused3) {
                                        }
                                    }
                                } else {
                                    try {
                                        throw new KscRuntimeException(ErrorCode.ILLEGAL_PROCESS, "Return uploadBlockResponse is null");
                                    } catch (Throwable th7) {
                                        th = th7;
                                    }
                                }
                            } catch (Throwable th8) {
                                th = th8;
                            }
                            randomInputStream2 = randomFileInputStream;
                        } catch (Throwable th9) {
                            th = th9;
                            j = j;
                            j2 = j2;
                        }
                        transferStep3 = transferStep4;
                    } catch (Throwable th10) {
                        th = th10;
                        transferStep3 = transferStep4;
                        if (ErrorHelper.isNetworkException(th)) {
                        }
                        throw KscException.newException(th, null, transferStep3);
                    }
                    if (ErrorHelper.isNetworkException(th)) {
                    }
                    throw KscException.newException(th, null, transferStep3);
                } catch (Throwable th11) {
                    if (randomInputStream2 != null) {
                        try {
                            randomInputStream2.close();
                        } catch (Throwable unused4) {
                        }
                    }
                    throw th11;
                }
            } catch (Throwable th12) {
                th = th12;
            }
            j = j;
            j2 = j2;
            randomInputStream = null;
        }
    }

    private UploadBlockResponse uploadBlock(RandomInputStream randomInputStream, IKssUploadRequestResult iKssUploadRequestResult, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper, KssUploadInfo kssUploadInfo, int i) throws InterruptedException, KscException {
        IKssUploadRequestResult requestResult = kssUploadInfo.getRequestResult();
        String[] nodeUrls = requestResult.getNodeUrls();
        if (nodeUrls == null || nodeUrls.length <= 0) {
            throw new IllegalArgumentException("No available urls.");
        }
        for (int i2 = 0; i2 < nodeUrls.length; i2++) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            try {
                randomInputStream.moveToPos(((long) i) * KssDef.BLOCKSIZE);
                randomInputStream.mark(4194304);
                Uri.Builder builderBuildUpon = Uri.parse(nodeUrls[i2] + KssDef.FUNC_UPLOAD).buildUpon();
                builderBuildUpon.appendQueryParameter("chunk_pos", String.valueOf(0));
                builderBuildUpon.appendQueryParameter("file_meta", requestResult.getFileMeta());
                builderBuildUpon.appendQueryParameter("block_meta", requestResult.getBlock(i).meta);
                return _uploadBlock(builderBuildUpon.build(), randomInputStream, iKssUploadRequestResult, iKscTransferListener, kssTransferStopper);
            } catch (Exception e) {
                ErrorHelper.handleInterruptException(e);
                if (ErrorHelper.isStopByCallerException(e) || i2 >= nodeUrls.length - 1) {
                    throw KscException.newException(e, "Failed when upload a kss block.", TransferStep.UPLOAD_PREPARE_STREAM);
                }
            }
        }
        return null;
    }

    private UploadBlockResponse _uploadBlock(Uri uri, RandomInputStream randomInputStream, IKssUploadRequestResult iKssUploadRequestResult, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper) throws InterruptedException, IOException, KscException {
        long j;
        randomInputStream.reset();
        long jMin = Math.min(KssDef.BLOCKSIZE, randomInputStream.available());
        if (jMin <= 0) {
            Log.d(LOG_TAG, "blockSize<=0, adjust upload len to 4M");
            j = 4194304;
        } else {
            j = jMin;
        }
        Log.d(LOG_TAG, "upload length is " + j);
        CRC32Encoder cRC32Encoder = new CRC32Encoder();
        try {
            IKscDecoder uploadDecoder = getUploadDecoder(iKssUploadRequestResult, randomInputStream.getCurrentPos());
            uploadDecoder.init();
            Uri uriBuild = uri.buildUpon().appendQueryParameter("body_sum", String.valueOf(cRC32Encoder.getCRC(new DecoderInputStream(randomInputStream, uploadDecoder, 16384), j))).build();
            randomInputStream.reset();
            DecoderInputStream decoderInputStream = new DecoderInputStream(randomInputStream, uploadDecoder, 16384);
            if (iKscTransferListener != null) {
                iKscTransferListener.setSendPos(0L);
            }
            return doUpload(uriBuild, decoderInputStream, j, iKscTransferListener, kssTransferStopper);
        } catch (InvalidKeyException e) {
            throw KscException.newException(e, null, TransferStep.UPLOAD_PREPARE_ENCODE_KEY);
        }
    }

    private UploadBlockResponse doUpload(Uri uri, InputStream inputStream, long j, IKscTransferListener iKscTransferListener, KssTransferStopper kssTransferStopper) throws InterruptedException, KscException {
        HttpEntity entity;
        Object obj = null;
        try {
            KscHttpRequest kscHttpRequest = new KscHttpRequest(KscHttpRequest.HttpMethod.POST, uri, (IKscDecoder) null, iKscTransferListener);
            kscHttpRequest.setPostEntity(new KssInputStreamEntity(inputStream, j));
            long jCurrentTimeMillis = System.currentTimeMillis();
            KscHttpResponse kscHttpResponseExecute = this.mTransmitter.execute(kscHttpRequest, 4, kssTransferStopper);
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            long contentLength = (kscHttpResponseExecute.getResponse() == null || (entity = kscHttpResponseExecute.getResponse().getEntity()) == null) ? 0L : entity.getContentLength();
            int statusCode = kscHttpResponseExecute.getStatusCode();
            String simpleName = "";
            if (kscHttpResponseExecute.getError() != null) {
                simpleName = kscHttpResponseExecute.getError().getClass().getSimpleName();
            }
            String string = uri.toString();
            MiCloudStatManager.getInstance().addHttpEvent(string, jCurrentTimeMillis2, contentLength, statusCode, simpleName);
            ErrorHelper.throwError(kscHttpResponseExecute, TransferStep.UPLOAD_REQUEST_XMS_HTTP);
            if (statusCode != 200) {
                ServerException serverException = new ServerException(statusCode, "upload url=" + string, TransferStep.UPLOAD_REQUEST_XMS_HTTP);
                Log.e(LOG_TAG, "ServerException in doUpload: " + serverException.getSimpleMessage());
                throw serverException;
            }
            Map<String, Object> mapContentToMap = ApiDataHelper.contentToMap(kscHttpResponseExecute, TransferStep.UPLOAD_REQUEST_XMS_HTTP);
            UploadBlockResponse uploadBlockResponse = new UploadBlockResponse(mapContentToMap);
            uploadBlockResponse.expect_info = ServerExpect.getServerExpect(kscHttpResponseExecute);
            uploadBlockResponse.request_url = string;
            if (mapContentToMap instanceof IObtainable) {
                ((IObtainable) mapContentToMap).recycle();
            }
            return uploadBlockResponse;
        } catch (Throwable th) {
            if (obj instanceof IObtainable) {
                ((IObtainable) null).recycle();
            }
            throw th;
        }
    }

    private void updateUploadInfo(int i, KssUploadInfo kssUploadInfo, UploadBlockInfoPersist uploadBlockInfoPersist) {
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        if (uploadTaskStore == null) {
            return;
        }
        uploadTaskStore.updateUploadInfo(i, kssUploadInfo, uploadBlockInfoPersist);
    }

    private void deleteUploadInfo(int i) throws InterruptedException {
        UploadTaskStore uploadTaskStore = this.mTaskStore;
        if (uploadTaskStore == null) {
            return;
        }
        uploadTaskStore.removeUploadInfo(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    private void verifyBlock(KssUploadFile kssUploadFile, UploadFileInfo uploadFileInfo, int i) throws Throwable {
        UploadFileInfo.BlockInfo blockInfo = uploadFileInfo.getBlockInfo(i);
        long j = kssUploadFile.fileSize;
        long j2 = ((long) i) * KssDef.BLOCKSIZE;
        int iMin = (int) Math.min(j - j2, KssDef.BLOCKSIZE);
        if (iMin != blockInfo.size) {
            throw new KscException(ErrorCode.IOERR_FILE_CHANGED, "Block size has changed.", TransferStep.UPLOAD_PREPARE);
        }
        ?? r1 = 0;
        try {
            try {
                InputStream inputStream = kssUploadFile.getInputStream();
                try {
                    if (inputStream.skip(j2) != j2) {
                        throw new KscException(ErrorCode.IOERR_FILE_CHANGED, "File size has changed.", TransferStep.UPLOAD_PREPARE);
                    }
                    if (!TextUtils.equals(Encode.SHA1Encode(inputStream, iMin), blockInfo.sha1)) {
                        throw new KscException(ErrorCode.IOERR_FILE_CHANGED, "Block has changed.", TransferStep.UPLOAD_PREPARE);
                    }
                    try {
                        inputStream.close();
                    } catch (Throwable unused) {
                    }
                } catch (IOException e) {
                    e = e;
                    throw KscException.newException(e, null, TransferStep.UPLOAD_PREPARE);
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Throwable th) {
                th = th;
                try {
                    r1.close();
                } catch (Throwable unused2) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r1 = kssUploadFile;
            r1.close();
            throw th;
        }
    }

    private IKscDecoder getUploadDecoder(IKssUploadRequestResult iKssUploadRequestResult, long j) throws InvalidKeyException {
        byte[] secureKey = iKssUploadRequestResult.getSecureKey();
        if (secureKey != null) {
            return new RC4Encoder(secureKey);
        }
        if (this.mEncryptInfo.isNeedEncrypt() && this.mEncryptInfo.getRecordKey() != null) {
            return new E2EETransferDecoder(this.mEncryptInfo.getRecordKey(), E2EECommonUtils.calculateDecodeFileIV(j, this.mEncryptInfo.getRecordIV()), 1);
        }
        return new EmptyDecoder();
    }
}
