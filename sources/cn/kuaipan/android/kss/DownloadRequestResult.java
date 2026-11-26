package cn.kuaipan.android.kss;

import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.kss.download.DownloadEncryptInfo;
import cn.kuaipan.android.utils.ApiDataHelper;
import cn.kuaipan.android.utils.Encode;
import cn.kuaipan.android.utils.IObtainable;
import cn.kuaipan.android.utils.JsonUtils;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DownloadRequestResult implements IKssDownloadRequestResult, KssDef {
    private static final String LOG_TAG = "DownloadRequestResult";
    private IKssDownloadRequestResult.Block[] blocks;
    private DownloadEncryptInfo mDownloadEncryptInfo;
    private byte[] secure_key;
    private final String stat;

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public long getModifyTime() {
        return -1L;
    }

    public DownloadRequestResult(Map<String, Object> map) throws KscException {
        String[] strArr;
        String strAsString = ApiDataHelper.asString(map, "stat");
        this.stat = strAsString;
        if ("OK".equalsIgnoreCase(strAsString)) {
            this.mDownloadEncryptInfo = (DownloadEncryptInfo) map.get("encryptInfo");
            String strAsString2 = ApiDataHelper.asString(map, "secure_key");
            this.secure_key = TextUtils.isEmpty(strAsString2) ? null : Encode.hexStringToByteArray(strAsString2);
            Collection<Map> collection = (Collection) map.get("blocks");
            if (collection != null) {
                this.blocks = new IKssDownloadRequestResult.Block[collection.size()];
                int i = 0;
                for (Map map2 : collection) {
                    String strAsString3 = ApiDataHelper.asString(map2, "sha1");
                    String strAsString4 = ApiDataHelper.asString(map2, "encryptedSha1");
                    long jLongValue = ApiDataHelper.asNumber(map2.get("size"), 0).longValue();
                    Collection collection2 = (Collection) map2.get("urls");
                    if (collection2 != null) {
                        String[] strArr2 = new String[collection2.size()];
                        Iterator it = collection2.iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            strArr2[i2] = (String) it.next();
                            i2++;
                        }
                        strArr = strArr2;
                    } else {
                        strArr = null;
                    }
                    this.blocks[i] = new IKssDownloadRequestResult.Block(strAsString3, strAsString4, strArr, jLongValue);
                    i++;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v7 */
    public DownloadRequestResult create(String str) throws Throwable {
        Throwable th;
        JSONException e;
        IOException e2;
        try {
            try {
                Map map = (Map) JsonUtils.parser(new StringReader(str));
                try {
                    DownloadRequestResult downloadRequestResult = new DownloadRequestResult(map);
                    if (map != null && (map instanceof IObtainable)) {
                        ((IObtainable) map).recycle();
                    }
                    return downloadRequestResult;
                } catch (IOException e3) {
                    e2 = e3;
                    throw new KscException(ErrorCode.DATA_IS_EMPTY, "kss is null", e2, TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
                } catch (JSONException e4) {
                    e = e4;
                    throw new KscException(ErrorCode.DATA_IS_NOT_JSON, "kss is not json", e, TransferStep.DOWNLOAD_REQUEST_BIZ_HTTP);
                }
            } catch (IOException e5) {
                e2 = e5;
            } catch (JSONException e6) {
                e = e6;
            } catch (Throwable th2) {
                str = 0;
                th = th2;
                if (str != 0) {
                    ((IObtainable) str).recycle();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (str != 0 && (str instanceof IObtainable)) {
                ((IObtainable) str).recycle();
            }
            throw th;
        }
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public int getStatus() {
        return "OK".equalsIgnoreCase(this.stat) ? 0 : -1;
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public String getMessage() {
        return this.stat;
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public String getHash() {
        StringBuilder sb = new StringBuilder();
        IKssDownloadRequestResult.Block[] blockArr = this.blocks;
        sb.append(blockArr == null ? 0 : blockArr.length);
        sb.append(CoreConstants.COLON_CHAR);
        sb.append(getTotalSize());
        sb.append(CoreConstants.COLON_CHAR);
        StringBuilder sb2 = new StringBuilder();
        IKssDownloadRequestResult.Block[] blockArr2 = this.blocks;
        if (blockArr2 != null) {
            for (IKssDownloadRequestResult.Block block : blockArr2) {
                sb2.append(block.sha1);
            }
        }
        sb.append(Encode.MD5Encode(sb2.toString().getBytes()));
        return sb.toString();
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public byte[] getSecureKey() {
        return this.secure_key;
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public int getBlockCount() {
        IKssDownloadRequestResult.Block[] blockArr = this.blocks;
        if (blockArr == null) {
            return -1;
        }
        return blockArr.length;
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public IKssDownloadRequestResult.Block getBlock(int i) {
        return this.blocks[i];
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public String[] getBlockUrls(long j) {
        long j2 = 0;
        if (j >= 0 && this.blocks != null) {
            int i = 0;
            while (true) {
                IKssDownloadRequestResult.Block[] blockArr = this.blocks;
                if (i >= blockArr.length) {
                    break;
                }
                long j3 = blockArr[i].size + j2;
                if (j >= j2 && j < j3) {
                    return this.blocks[i].urls;
                }
                i++;
                j2 = j3;
            }
        }
        return null;
    }

    public String toString() {
        Object obj;
        JSONArray jSONArray;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("stat", this.stat);
            jSONObject.put("secure_key", Encode.byteArrayToHexString(this.secure_key));
            JSONArray jSONArray2 = new JSONArray();
            IKssDownloadRequestResult.Block[] blockArr = this.blocks;
            if (blockArr != null) {
                for (IKssDownloadRequestResult.Block block : blockArr) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("sha1", block.sha1);
                    jSONObject2.put("size", block.size);
                    if (block.urls != null) {
                        jSONArray = new JSONArray((Collection) Arrays.asList(block.urls));
                    } else {
                        jSONArray = new JSONArray();
                    }
                    jSONObject2.put("urls", jSONArray);
                    jSONArray2.put(jSONObject2);
                }
            }
            jSONObject.put("blocks", jSONArray2);
            obj = jSONObject;
        } catch (JSONException unused) {
            Log.w(LOG_TAG, "Failed generate Json String for UploadRequestResult");
            obj = null;
        }
        return String.valueOf(obj);
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public long getTotalSize() {
        IKssDownloadRequestResult.Block[] blockArr = this.blocks;
        long j = 0;
        if (blockArr == null) {
            return 0L;
        }
        for (IKssDownloadRequestResult.Block block : blockArr) {
            j += block.size;
        }
        return j;
    }

    @Override // cn.kuaipan.android.kss.IKssDownloadRequestResult
    public DownloadEncryptInfo getDownloadEncryptInfo() {
        return this.mDownloadEncryptInfo;
    }
}
