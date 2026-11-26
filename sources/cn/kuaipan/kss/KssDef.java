package cn.kuaipan.kss;

/* JADX INFO: loaded from: classes.dex */
public class KssDef {
    public static int BLOCKSIZE = 4194304;
    public static int CHUNKSIZE_MIN = 65536;
    public static int DOWNLOAD_BUFFER = 8192;
    public static final String FUNC_UPLOADBLOCKCHUNK = "upload_block_chunk";
    public static final String KEY_BLOCKINFO = "block_infos";
    public static final String KEY_BLOCKMETA = "block_meta";
    public static final String KEY_BLOCKMETAS = "block_metas";
    public static final String KEY_BLOCKS = "blocks";
    public static final String KEY_BODYSUM = "body_sum";
    public static final String KEY_CHUNKPOS = "chunk_pos";
    public static final String KEY_COMMITMETA = "commit_meta";
    public static final String KEY_COMMITMETAS = "commit_metas";
    public static final String KEY_DESTURL = "Dest-Url";
    public static final String KEY_FILEMETA = "file_meta";
    public static final String KEY_ISEXISTED = "is_existed";
    public static final String KEY_MD5 = "md5";
    public static final String KEY_NODEURLS = "node_urls";
    public static final String KEY_PROXIES = "proxies";
    public static final String KEY_SECUREKEY = "secure_key";
    public static final String KEY_SHA1 = "sha1";
    public static final String KEY_SIZE = "size";
    public static final String KEY_STAT = "stat";
    public static final String KEY_STOID = "stoid";
    public static final String KEY_UPLOADID = "upload_id";
    public static final String KEY_URL = "url";
    public static final String KEY_URLS = "urls";
    public static int NET_RETRY_TIMES = 3;
    public static final String VALUE_BLOCKCOMPLETED = "BLOCK_COMPLETED";
    public static final String VALUE_CONTINUEUPLOAD = "CONTINUE_UPLOAD";
    public static final String VALUE_FILEEXISTED = "FILE_EXISTED";
    public static final String VALUE_OK = "OK";

    public enum KssAPIResult {
        OK,
        Error,
        Cancel,
        NetTimeout,
        NeedRequest,
        DataCorrupted,
        SpaceOver,
        ServerDenyReadOnly
    }

    public enum NetState {
        Wifi,
        UNKNOWN,
        MOBILE
    }

    public interface OnUpDownload {
        boolean OnTransData(int i);

        boolean OnTransFail(int i);
    }
}
