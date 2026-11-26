package cn.kuaipan.kss.implement;

import android.text.TextUtils;
import cn.kuaipan.kss.DownloadEncryptInfo;
import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssDownload;
import cn.kuaipan.kss.utils.Encode;
import cn.kuaipan.kss.utils.RC4;
import com.xiaomi.e2ee.utils.CipherUtils;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/* JADX INFO: loaded from: classes.dex */
public class HttpClientDownload implements KssDownload {
    private HttpClient m_client;
    private byte[] m_dataBuffer;
    private _DownloadProgress m_downProgress;
    private HttpClientProxyHelper m_proxyHelper;
    private KssDownload.RequestDownloadInfo m_downloadInfo = null;
    private int m_currentBlockPos = 0;
    private KssDef.NetState m_netState = KssDef.NetState.Wifi;
    private int m_maxRetryCount = 0;

    public HttpClientDownload(HttpClient httpClient, HttpClientProxyHelper httpClientProxyHelper) {
        this.m_client = null;
        this.m_dataBuffer = null;
        this.m_proxyHelper = null;
        this.m_downProgress = null;
        this.m_client = httpClient;
        this.m_proxyHelper = httpClientProxyHelper;
        this.m_downProgress = new _DownloadProgress();
        this.m_dataBuffer = new byte[KssDef.DOWNLOAD_BUFFER];
    }

    @Override // cn.kuaipan.kss.KssDownload
    public boolean init(KssDownload.RequestDownloadInfo requestDownloadInfo, KssDef.OnUpDownload onUpDownload, KssDef.NetState netState) {
        this.m_downloadInfo = requestDownloadInfo;
        this.m_downProgress.init(requestDownloadInfo, onUpDownload);
        this.m_netState = netState;
        this.m_maxRetryCount = KssDef.NET_RETRY_TIMES;
        if (this.m_netState == KssDef.NetState.Wifi) {
            return true;
        }
        this.m_maxRetryCount = KssDef.NET_RETRY_TIMES * 2;
        return true;
    }

    @Override // cn.kuaipan.kss.KssDownload
    public KssDef.KssAPIResult download(OutputStream outputStream, int i) throws Exception {
        if (i < 0) {
            i = 0;
        }
        KPDownloadTransControl kPDownloadTransControl = new KPDownloadTransControl();
        kPDownloadTransControl.init(outputStream);
        kPDownloadTransControl.setStartPos(i);
        return download(kPDownloadTransControl);
    }

    @Override // cn.kuaipan.kss.KssDownload
    public KssDef.KssAPIResult download(KssDownload.DownloadTransControl downloadTransControl) throws Exception {
        Cipher decodeFileCipher;
        HttpClientProxyHelper httpClientProxyHelper;
        this.m_downProgress.startTrans(downloadTransControl);
        int startPos = downloadTransControl.getStartPos() / KssDef.BLOCKSIZE;
        this.m_currentBlockPos = downloadTransControl.getStartPos() % KssDef.BLOCKSIZE;
        DownloadEncryptInfo downloadEncryptInfo = this.m_downloadInfo.getDownloadEncryptInfo();
        if (downloadEncryptInfo != null) {
            String strCalculateDecodeFileIV = downloadEncryptInfo.recordIV;
            if (startPos != 0) {
                strCalculateDecodeFileIV = E2EECommonUtils.calculateDecodeFileIV(((long) startPos) * ((long) KssDef.BLOCKSIZE), strCalculateDecodeFileIV);
            }
            decodeFileCipher = CipherUtils.getDecodeFileCipher(downloadEncryptInfo.getRecordKey(), strCalculateDecodeFileIV, 2);
        } else {
            decodeFileCipher = null;
        }
        KssDef.KssAPIResult kssAPIResult_downloadBlock = KssDef.KssAPIResult.Error;
        while (startPos < this.m_downloadInfo.getBlockCount()) {
            KssDownload.BlockDownloadInfo blockInfos = this.m_downloadInfo.getBlockInfos(startPos);
            while (true) {
                boolean z = true;
                while (true) {
                    if (!z) {
                        break;
                    }
                    kssAPIResult_downloadBlock = _downloadBlock(downloadTransControl, blockInfos, decodeFileCipher);
                    if (kssAPIResult_downloadBlock != KssDef.KssAPIResult.NetTimeout || (httpClientProxyHelper = this.m_proxyHelper) == null || !httpClientProxyHelper.nextProxy()) {
                        z = false;
                    }
                }
            }
            if (kssAPIResult_downloadBlock != KssDef.KssAPIResult.OK) {
                break;
            }
            this.m_currentBlockPos = 0;
            startPos++;
        }
        if (kssAPIResult_downloadBlock == KssDef.KssAPIResult.OK) {
            downloadTransControl.setEndState(KssDownload.DownloadTransControl.EndState.Completed);
            return kssAPIResult_downloadBlock;
        }
        downloadTransControl.setEndState(KssDownload.DownloadTransControl.EndState.Interrupt);
        return kssAPIResult_downloadBlock;
    }

    @Override // cn.kuaipan.kss.KssDownload
    public void terminal() {
        this.m_client = null;
        this.m_dataBuffer = null;
        this.m_proxyHelper = null;
        this.m_downloadInfo = null;
        this.m_downProgress = null;
    }

    private KssDef.KssAPIResult _downloadBlock(KssDownload.DownloadTransControl downloadTransControl, KssDownload.BlockDownloadInfo blockDownloadInfo, Cipher cipher) throws Exception {
        byte[] bArr;
        RC4 rc4;
        HttpGet httpGet;
        HttpGet httpGetCreateGet;
        String secureKey = this.m_downloadInfo.getSecureKey();
        if (TextUtils.isEmpty(secureKey)) {
            bArr = null;
            rc4 = null;
        } else {
            byte[] bArrHexStringToByteArray = Encode.hexStringToByteArray(secureKey);
            rc4 = new RC4();
            bArr = bArrHexStringToByteArray;
        }
        KssDef.KssAPIResult kssAPIResult = KssDef.KssAPIResult.Error;
        for (int i = 0; i < this.m_maxRetryCount; i++) {
            try {
                try {
                    HttpClientProxyHelper httpClientProxyHelper = this.m_proxyHelper;
                    if (httpClientProxyHelper == null) {
                        httpGetCreateGet = new HttpGet(blockDownloadInfo.getBlockDownload_FullURL(0));
                    } else {
                        httpGetCreateGet = httpClientProxyHelper.createGet(blockDownloadInfo.getBlockDownload_FullURL(0));
                    }
                    httpGet = httpGetCreateGet;
                    try {
                        try {
                            httpGet.addHeader("Range", "bytes=" + Integer.toString(this.m_currentBlockPos) + "-");
                            if (rc4 != null) {
                                rc4.makeKey(bArr);
                            }
                            InputStream content = this.m_client.execute(httpGet).getEntity().getContent();
                            KssDef.KssAPIResult kssAPIResult2 = KssDef.KssAPIResult.OK;
                            while (true) {
                                int i2 = content.read(this.m_dataBuffer, 0, KssDef.DOWNLOAD_BUFFER);
                                if (i2 < 0) {
                                    break;
                                }
                                if (rc4 != null) {
                                    byte[] bArr2 = this.m_dataBuffer;
                                    rc4.genRC4(bArr2, 0, i2, bArr2, 0);
                                }
                                if (cipher != null) {
                                    try {
                                        byte[] bArrUpdate = cipher.update(this.m_dataBuffer, 0, i2);
                                        downloadTransControl.addData(bArrUpdate, bArrUpdate.length);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        kssAPIResult2 = KssDef.KssAPIResult.Error;
                                    }
                                } else {
                                    downloadTransControl.addData(this.m_dataBuffer, i2);
                                }
                                this.m_currentBlockPos += i2;
                                this.m_downProgress.TransBytes(i2);
                                if (this.m_downProgress.getIsCanceled()) {
                                    httpGet.abort();
                                    kssAPIResult2 = KssDef.KssAPIResult.Cancel;
                                    break;
                                }
                            }
                            kssAPIResult = kssAPIResult2;
                        } catch (IOException unused) {
                            httpGet.abort();
                            kssAPIResult = KssDef.KssAPIResult.NetTimeout;
                        }
                    } catch (RuntimeException unused2) {
                        httpGet.abort();
                        kssAPIResult = KssDef.KssAPIResult.Error;
                    }
                } catch (IOException unused3) {
                    httpGet = null;
                } catch (RuntimeException unused4) {
                    httpGet = null;
                }
            } catch (Exception unused5) {
                kssAPIResult = KssDef.KssAPIResult.Error;
            }
            if (kssAPIResult != KssDef.KssAPIResult.NetTimeout) {
                break;
            }
        }
        return kssAPIResult;
    }
}
