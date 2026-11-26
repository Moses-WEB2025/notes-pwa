package cn.kuaipan.android.kss;

import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
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
public class UploadRequestResult implements IKssUploadRequestResult, KssDef {
    private static final String LOG_TAG = "UploadRequestResult";
    private IKssUploadRequestResult.Block[] blocks;
    private final String file_meta;
    private String[] node_urls;
    private final byte[] secure_key;

    public UploadRequestResult(Map<String, Object> map) throws KscException {
        String strAsString = ApiDataHelper.asString(map, "secure_key");
        this.secure_key = TextUtils.isEmpty(strAsString) ? null : Encode.hexStringToByteArray(strAsString);
        this.file_meta = ApiDataHelper.asString(map, "file_meta");
        Collection<Map> collection = (Collection) map.get("block_metas");
        int i = 0;
        if (collection != null) {
            this.blocks = new IKssUploadRequestResult.Block[collection.size()];
            int i2 = 0;
            for (Map map2 : collection) {
                boolean z = ApiDataHelper.asNumber(map2.get("is_existed"), 0).intValue() != 0;
                this.blocks[i2] = new IKssUploadRequestResult.Block(ApiDataHelper.asString(map2, z ? "commit_meta" : "block_meta"), z);
                i2++;
            }
        }
        Collection collection2 = (Collection) map.get("node_urls");
        if (collection2 != null) {
            this.node_urls = new String[collection2.size()];
            Iterator it = collection2.iterator();
            while (it.hasNext()) {
                this.node_urls[i] = (String) it.next();
                i++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v7 */
    public static UploadRequestResult create(String str) throws Throwable {
        Throwable th;
        JSONException e;
        IOException e2;
        try {
            try {
                Map map = (Map) JsonUtils.parser(new StringReader(str));
                try {
                    UploadRequestResult uploadRequestResult = new UploadRequestResult(map);
                    if (map != null && (map instanceof IObtainable)) {
                        ((IObtainable) map).recycle();
                    }
                    return uploadRequestResult;
                } catch (IOException e3) {
                    e2 = e3;
                    throw new KscException(ErrorCode.DATA_IS_EMPTY, "kss is null", e2, TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
                } catch (JSONException e4) {
                    e = e4;
                    throw new KscException(ErrorCode.DATA_IS_NOT_JSON, "kss is not json", e, TransferStep.UPLOAD_REQUEST_BIZ_HTTP);
                }
            } catch (IOException e5) {
                e2 = e5;
            } catch (JSONException e6) {
                e = e6;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
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

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public byte[] getSecureKey() {
        return this.secure_key;
    }

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public String[] getNodeUrls() {
        return this.node_urls;
    }

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public String getFileMeta() {
        return this.file_meta;
    }

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public int getBlockCount() {
        IKssUploadRequestResult.Block[] blockArr = this.blocks;
        if (blockArr == null) {
            return 0;
        }
        return blockArr.length;
    }

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public IKssUploadRequestResult.Block getBlock(int i) {
        IKssUploadRequestResult.Block[] blockArr = this.blocks;
        if (blockArr == null || i >= blockArr.length) {
            return null;
        }
        return blockArr[i];
    }

    @Override // cn.kuaipan.android.kss.IKssUploadRequestResult
    public boolean isCompleted() {
        IKssUploadRequestResult.Block[] blockArr = this.blocks;
        if (blockArr == null) {
            return true;
        }
        for (IKssUploadRequestResult.Block block : blockArr) {
            if (!block.exist) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        Object obj;
        Object jSONArray;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("secure_key", Encode.byteArrayToHexString(this.secure_key));
            jSONObject.put("file_meta", this.file_meta);
            if (this.node_urls != null) {
                jSONArray = new JSONArray((Collection) Arrays.asList(this.node_urls));
            } else {
                jSONArray = new JSONArray();
            }
            jSONObject.put("node_urls", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            IKssUploadRequestResult.Block[] blockArr = this.blocks;
            if (blockArr != null) {
                for (IKssUploadRequestResult.Block block : blockArr) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("is_existed", block.exist ? 1 : 0);
                    if (block.exist) {
                        jSONObject2.put("commit_meta", block.meta);
                    } else {
                        jSONObject2.put("block_meta", block.meta);
                    }
                    jSONArray2.put(jSONObject2);
                }
            }
            jSONObject.put("block_metas", jSONArray2);
            obj = jSONObject;
        } catch (JSONException unused) {
            Log.w(LOG_TAG, "Failed generate Json String for UploadRequestResult");
            obj = null;
        }
        return String.valueOf(obj);
    }
}
