package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;
import cn.kuaipan.kss.utils.RC4;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.zip.CRC32;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: classes.dex */
public class HttpClientUpload implements KssUpload {
    private int CHUNKSIZE;
    private byte[] m_chunkDataBuffer;
    private HttpClient m_client;
    private CRC32 m_crc32;
    private _UploadProgress m_progress;
    private HttpClientProxyHelper m_proxyHelper;
    private RC4 m_rc4;
    private KssUpload.RequestUploadInfo m_requestInfo;
    private _UploadResult m_uploadResult;

    public HttpClientUpload(HttpClient httpClient, HttpClientProxyHelper httpClientProxyHelper) {
        int i = KssDef.CHUNKSIZE_MIN;
        this.CHUNKSIZE = i;
        this.m_client = null;
        this.m_proxyHelper = null;
        this.m_requestInfo = null;
        this.m_progress = null;
        this.m_uploadResult = null;
        this.m_chunkDataBuffer = new byte[i];
        this.m_rc4 = new RC4();
        this.m_crc32 = new CRC32();
        this.m_client = httpClient;
        this.m_proxyHelper = httpClientProxyHelper;
        this.m_progress = new _UploadProgress();
        this.m_uploadResult = new _UploadResult();
    }

    @Override // cn.kuaipan.kss.KssUpload
    public boolean init(KssUpload.RequestUploadInfo requestUploadInfo, KssDef.OnUpDownload onUpDownload, KssDef.NetState netState) {
        if (requestUploadInfo == null) {
            return false;
        }
        this.m_requestInfo = requestUploadInfo;
        this.m_progress.init(requestUploadInfo, onUpDownload);
        this.m_uploadResult.setFileMeta(this.m_requestInfo.getFileMeta());
        return true;
    }

    @Override // cn.kuaipan.kss.KssUpload
    @Deprecated
    public KssDef.KssAPIResult upload(byte[] bArr) throws Exception {
        return _upload(new ByteArrayInputStream(bArr));
    }

    @Override // cn.kuaipan.kss.KssUpload
    public KssDef.KssAPIResult upload(InputStream inputStream) throws Exception {
        return _upload(inputStream);
    }

    @Override // cn.kuaipan.kss.KssUpload
    public KssUpload.UploadResult getCommitInfo() throws Exception {
        return this.m_uploadResult;
    }

    @Override // cn.kuaipan.kss.KssUpload
    public void terminal() {
        this.m_client = null;
        this.m_requestInfo = null;
        this.m_uploadResult = null;
        this.m_progress = null;
        this.m_proxyHelper = null;
        this.m_crc32 = null;
        this.m_rc4 = null;
        this.m_chunkDataBuffer = null;
    }

    private KssDef.KssAPIResult _upload(InputStream inputStream) throws Exception {
        int blockCount = this.m_requestInfo.getBlockCount();
        for (int i = 0; i < blockCount; i++) {
            if (this.m_requestInfo.getBlockIsExist(i)) {
                inputStream.skip(KssDef.BLOCKSIZE);
                this.m_uploadResult.addCommitMeta(this.m_requestInfo.getBlockMeta(i));
                if (!this.m_progress.TransBytes(KssDef.BLOCKSIZE)) {
                    return KssDef.KssAPIResult.Cancel;
                }
            } else {
                _UploadChunkInfo _uploadchunkinfo = new _UploadChunkInfo();
                KssDef.KssAPIResult kssAPIResult_uploadBlock = _uploadBlock(inputStream, i, _uploadchunkinfo);
                if (kssAPIResult_uploadBlock != KssDef.KssAPIResult.OK) {
                    return kssAPIResult_uploadBlock;
                }
                this.m_uploadResult.addCommitMeta(_uploadchunkinfo.getCommitMeta());
            }
        }
        return KssDef.KssAPIResult.OK;
    }

    private KssDef.KssAPIResult _uploadBlock(InputStream inputStream, int i, _UploadChunkInfo _uploadchunkinfo) throws Exception {
        int i_readChunkData;
        KssDef.KssAPIResult kssAPIResult_uploadChunk;
        KssDef.KssAPIResult kssAPIResult = KssDef.KssAPIResult.OK;
        int i2 = 0;
        while (true) {
            try {
                i_readChunkData = _readChunkData(inputStream, this.m_chunkDataBuffer);
            } catch (IOException unused) {
                kssAPIResult = KssDef.KssAPIResult.Error;
                i_readChunkData = 0;
            }
            if (i_readChunkData == 0) {
                return kssAPIResult;
            }
            this.m_rc4.makeKey(this.m_requestInfo.getSeureKey());
            RC4 rc4 = this.m_rc4;
            byte[] bArr = this.m_chunkDataBuffer;
            rc4.genRC4(bArr, 0, i_readChunkData, bArr, 0);
            boolean z = false;
            do {
                kssAPIResult_uploadChunk = _uploadChunk(i, i2, i_readChunkData, _uploadchunkinfo);
                if (KssDef.KssAPIResult.NetTimeout == kssAPIResult_uploadChunk) {
                    HttpClientProxyHelper httpClientProxyHelper = this.m_proxyHelper;
                    z = httpClientProxyHelper != null && httpClientProxyHelper.nextProxy();
                }
            } while (z);
            if (KssDef.KssAPIResult.OK != kssAPIResult_uploadChunk || (i2 = i2 + i_readChunkData) >= KssDef.BLOCKSIZE) {
                return kssAPIResult_uploadChunk;
            }
            kssAPIResult = kssAPIResult_uploadChunk;
        }
    }

    private KssDef.KssAPIResult _uploadChunk(int i, int i2, int i3, _UploadChunkInfo _uploadchunkinfo) throws Exception {
        KssDef.KssAPIResult kssAPIResult;
        KssDef.KssAPIResult kssAPIResult2 = KssDef.KssAPIResult.OK;
        this.m_crc32.reset();
        int i4 = 0;
        this.m_crc32.update(this.m_chunkDataBuffer, 0, i3);
        HttpPost httpPost = null;
        try {
            try {
                URI uri_genUploadChunkURI = _genUploadChunkURI(Integer.toString((int) this.m_crc32.getValue()), i2, i, _uploadchunkinfo);
                HttpPost httpPost_genUploadChunkPost = null;
                HttpResponse httpResponseExecute = null;
                while (i4 < KssDef.NET_RETRY_TIMES) {
                    try {
                        try {
                            httpPost_genUploadChunkPost = _genUploadChunkPost(uri_genUploadChunkURI, this.m_chunkDataBuffer, i3);
                            try {
                                httpResponseExecute = this.m_client.execute(httpPost_genUploadChunkPost);
                            } catch (ClientProtocolException e) {
                                throw e;
                            } catch (IOException unused) {
                                httpPost_genUploadChunkPost.abort();
                                httpResponseExecute = null;
                            } catch (Exception e2) {
                                throw e2;
                            }
                            i4++;
                            if (httpResponseExecute != null) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            httpPost = httpPost_genUploadChunkPost;
                            httpPost.abort();
                            throw th;
                        }
                    } catch (Exception e3) {
                        throw e3;
                    }
                }
                HttpPost httpPost2 = httpPost_genUploadChunkPost;
                if (httpResponseExecute == null) {
                    kssAPIResult = KssDef.KssAPIResult.NetTimeout;
                } else {
                    KssDef.KssAPIResult kssAPIResult_parseResponse = _parseResponse(httpResponseExecute, _uploadchunkinfo);
                    kssAPIResult = (kssAPIResult_parseResponse != KssDef.KssAPIResult.OK || this.m_progress.TransBytes(i3)) ? kssAPIResult_parseResponse : KssDef.KssAPIResult.Cancel;
                }
                httpPost2.abort();
                return kssAPIResult;
            } catch (Exception e4) {
                throw e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private int _readChunkData(InputStream inputStream, byte[] bArr) throws IOException {
        int i;
        int i2 = 0;
        while (true) {
            int i3 = this.CHUNKSIZE;
            if (i2 >= i3 || (i = inputStream.read(bArr, i2, i3 - i2)) < 0) {
                break;
            }
            i2 += i;
        }
        return i2;
    }

    private URI _genUploadChunkURI(String str, int i, int i2, _UploadChunkInfo _uploadchunkinfo) throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("chunk_pos", Integer.toString(i)));
        arrayList.add(new BasicNameValuePair("body_sum", str));
        if (i == 0) {
            arrayList.add(new BasicNameValuePair("file_meta", this.m_requestInfo.getFileMeta()));
            arrayList.add(new BasicNameValuePair("block_meta", this.m_requestInfo.getBlockMeta(i2)));
        } else {
            arrayList.add(new BasicNameValuePair("upload_id", _uploadchunkinfo.getUploadID()));
        }
        return URIUtils.createURI(this.m_requestInfo.getProtocol(), this.m_requestInfo.getNodeIP(0), -1, KssDef.FUNC_UPLOADBLOCKCHUNK, URLEncodedUtils.format(arrayList, "UTF-8"), null);
    }

    private HttpPost _genUploadChunkPost(URI uri, byte[] bArr, int i) {
        HttpPost httpPostCreatePost;
        HttpEntity inputStreamEntity;
        HttpClientProxyHelper httpClientProxyHelper = this.m_proxyHelper;
        if (httpClientProxyHelper == null) {
            httpPostCreatePost = new HttpPost(uri);
        } else {
            httpPostCreatePost = httpClientProxyHelper.createPost(uri);
        }
        if (i == bArr.length) {
            inputStreamEntity = new ByteArrayEntity(bArr);
        } else {
            inputStreamEntity = new InputStreamEntity(new ByteArrayInputStream(bArr, 0, i), i);
        }
        httpPostCreatePost.setEntity(inputStreamEntity);
        return httpPostCreatePost;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0039, code lost:
    
        if (r1 != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0045, code lost:
    
        if (r1 != null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private cn.kuaipan.kss.KssDef.KssAPIResult _parseResponse(org.apache.http.HttpResponse r5, cn.kuaipan.kss.implement._UploadChunkInfo r6) throws java.lang.Throwable {
        /*
            r4 = this;
            cn.kuaipan.kss.KssDef$KssAPIResult r4 = cn.kuaipan.kss.KssDef.KssAPIResult.OK
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
            org.apache.http.HttpEntity r5 = r5.getEntity()     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
            java.io.InputStream r5 = r5.getContent()     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37 java.lang.IllegalStateException -> L3f
        L1a:
            java.lang.String r5 = r2.readLine()     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30 java.lang.IllegalStateException -> L32
            if (r5 == 0) goto L24
            r0.append(r5)     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30 java.lang.IllegalStateException -> L32
            goto L1a
        L24:
            r2.close()     // Catch: java.lang.Exception -> L28
            goto L48
        L28:
            r5 = move-exception
            r5.printStackTrace()
            goto L48
        L2d:
            r4 = move-exception
            r1 = r2
            goto L55
        L30:
            r1 = r2
            goto L37
        L32:
            r4 = move-exception
            r1 = r2
            goto L40
        L35:
            r4 = move-exception
            goto L55
        L37:
            cn.kuaipan.kss.KssDef$KssAPIResult r4 = cn.kuaipan.kss.KssDef.KssAPIResult.NetTimeout     // Catch: java.lang.Throwable -> L35
            if (r1 == 0) goto L48
        L3b:
            r1.close()     // Catch: java.lang.Exception -> L28
            goto L48
        L3f:
            r4 = move-exception
        L40:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L35
            cn.kuaipan.kss.KssDef$KssAPIResult r4 = cn.kuaipan.kss.KssDef.KssAPIResult.Error     // Catch: java.lang.Throwable -> L35
            if (r1 == 0) goto L48
            goto L3b
        L48:
            cn.kuaipan.kss.KssDef$KssAPIResult r5 = cn.kuaipan.kss.KssDef.KssAPIResult.OK
            if (r4 != r5) goto L54
            java.lang.String r4 = r0.toString()
            cn.kuaipan.kss.KssDef$KssAPIResult r4 = r6.parseInfo(r4)
        L54:
            return r4
        L55:
            if (r1 == 0) goto L5f
            r1.close()     // Catch: java.lang.Exception -> L5b
            goto L5f
        L5b:
            r5 = move-exception
            r5.printStackTrace()
        L5f:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.kuaipan.kss.implement.HttpClientUpload._parseResponse(org.apache.http.HttpResponse, cn.kuaipan.kss.implement._UploadChunkInfo):cn.kuaipan.kss.KssDef$KssAPIResult");
    }
}
