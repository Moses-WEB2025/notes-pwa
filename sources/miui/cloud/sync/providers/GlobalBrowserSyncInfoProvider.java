package miui.cloud.sync.providers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import miui.cloud.sync.SyncInfoProviderBase;

/* JADX INFO: loaded from: classes5.dex */
public class GlobalBrowserSyncInfoProvider extends SyncInfoProviderBase {
    public static final String AUTHORITY = "com.miui.browser.global";
    private static final Uri AUTHORITY_URI;
    private static final Uri BOOKMARK_CONTENT_URI;
    private static final String BOOKMARK_SELECTION_ALL = "_id != 1";
    private static final String DIRTY_BOOKMARK_SELECTION = "_id != 1 AND dirty=1";
    private static final String DIRTY_HISTORY_SELECTION = "(sourceid IS NULL OR deleted=1)";
    private static final Uri HISTORY_CONTENT_URI;
    private static final String SYNCED_BOOKMARK_SELECTION = "_id != 1 AND dirty =0  AND sourceid is not null";
    private static final String SYNCED_HISTORY_SELECTION = "(sourceid IS not NULL AND deleted=0)";
    private static final String TAG = "GlobalBrowserSyncInfoProvider";

    public int getWifiOnlyUnsyncedCount(Context context) {
        return 0;
    }

    public static final class Bookmarks {
        static final String DIRTY = "dirty";
        static final String SOURCE_ID = "sourceid";
        static final String _ID = "_id";

        private Bookmarks() {
        }
    }

    static {
        Uri uri = Uri.parse("content://com.miui.browser.global");
        AUTHORITY_URI = uri;
        BOOKMARK_CONTENT_URI = Uri.withAppendedPath(uri, "bookmarks");
        HISTORY_CONTENT_URI = Uri.withAppendedPath(uri, "historysync");
    }

    public int getUnsyncedCount(Context context) {
        int bookmarksDirtyCount = getBookmarksDirtyCount(context);
        int historyDirtyCount = getHistoryDirtyCount(context);
        if (bookmarksDirtyCount == -1 || historyDirtyCount == -1) {
            return -1;
        }
        return bookmarksDirtyCount + historyDirtyCount;
    }

    private int getBookmarksDirtyCount(Context context) {
        int iQueryCount = queryCount(context, BOOKMARK_CONTENT_URI, DIRTY_BOOKMARK_SELECTION, null);
        if (isDebug()) {
            Log.d(TAG, "getGlobalBrowserDirtyBookmarksCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    private int getHistoryDirtyCount(Context context) {
        int iQueryCount = queryCount(context, HISTORY_CONTENT_URI, DIRTY_HISTORY_SELECTION, null);
        if (isDebug()) {
            Log.d(TAG, "getGlobalBrowserDirtyHistoryCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    public int getSyncedCount(Context context) {
        int bookmarksSyncedCount = getBookmarksSyncedCount(context);
        int historySyncedCount = getHistorySyncedCount(context);
        if (bookmarksSyncedCount == -1 || historySyncedCount == -1) {
            return -1;
        }
        return bookmarksSyncedCount + historySyncedCount;
    }

    private int getBookmarksSyncedCount(Context context) {
        int iQueryCount = queryCount(context, BOOKMARK_CONTENT_URI, SYNCED_BOOKMARK_SELECTION, null);
        if (isDebug()) {
            Log.d(TAG, "getGlobalBrowserSyncedBookmarksCount count = " + iQueryCount);
        }
        return iQueryCount;
    }

    private int getHistorySyncedCount(Context context) {
        int iQueryCount = queryCount(context, HISTORY_CONTENT_URI, SYNCED_HISTORY_SELECTION, null);
        if (isDebug()) {
            Log.d(TAG, "getGlobalBrowserSyncedHistoryCount count = " + iQueryCount);
        }
        return iQueryCount;
    }
}
