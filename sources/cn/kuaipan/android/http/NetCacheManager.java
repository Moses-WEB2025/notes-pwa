package cn.kuaipan.android.http;

import android.content.Context;
import android.text.TextUtils;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.utils.FileUtils;
import cn.kuaipan.android.utils.TwoKeyHashMap;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class NetCacheManager {
    private static final String CACHE_DIR = "net_cache";
    private static final String CACHE_FILE = "%08d.tmp";
    private static final TwoKeyHashMap<Boolean, String, NetCacheManager> sCaches = new TwoKeyHashMap<>();
    private final Context mContext;
    private final String mDirName;
    private final boolean mExternal;
    private String mFolderPath;
    private int mLatestId = 0;

    public static synchronized NetCacheManager getInstance(Context context, boolean z) {
        return getInstance(context, z, null);
    }

    public static synchronized NetCacheManager getInstance(Context context, boolean z, String str) {
        NetCacheManager netCacheManager;
        if (TextUtils.isEmpty(str)) {
            str = CACHE_DIR;
        }
        TwoKeyHashMap<Boolean, String, NetCacheManager> twoKeyHashMap = sCaches;
        netCacheManager = twoKeyHashMap.get(Boolean.valueOf(z), str);
        if (netCacheManager == null) {
            netCacheManager = new NetCacheManager(context, z, str);
            twoKeyHashMap.put(Boolean.valueOf(z), str, netCacheManager);
        }
        return netCacheManager;
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [cn.kuaipan.android.http.NetCacheManager$1] */
    private NetCacheManager(Context context, boolean z, String str) {
        if (context == null) {
            throw new NullPointerException("Context can't be null.");
        }
        this.mContext = context;
        this.mExternal = z;
        this.mDirName = str;
        final File cacheDir = FileUtils.getCacheDir(context, str, z);
        if (cacheDir != null) {
            this.mFolderPath = cacheDir.getAbsolutePath();
            new Thread() { // from class: cn.kuaipan.android.http.NetCacheManager.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    FileUtils.deleteChildren(cacheDir);
                }
            }.start();
        }
    }

    public File assignCache() {
        File nextCache;
        do {
            nextCache = getNextCache();
        } while (nextCache.exists());
        nextCache.deleteOnExit();
        return nextCache;
    }

    private File getNextCache() {
        int i;
        synchronized (this) {
            i = this.mLatestId + 1;
            this.mLatestId = i;
        }
        String str = String.format(CACHE_FILE, Integer.valueOf(i));
        File cacheDir = FileUtils.getCacheDir(this.mContext, this.mDirName, this.mExternal);
        if (cacheDir == null) {
            throw new KscRuntimeException(ErrorCode.LIMIT_NO_SPACE);
        }
        this.mFolderPath = cacheDir.getAbsolutePath();
        return new File(cacheDir, str);
    }

    public void releaseCache(File file) {
        if (file == null || !TextUtils.equals(this.mFolderPath, file.getParent())) {
            return;
        }
        file.delete();
    }
}
