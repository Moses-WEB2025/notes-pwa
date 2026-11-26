package miui.cloud.sync;

import android.content.Context;
import com.miui.notes.ui.view.SearchCallback;
import java.util.HashMap;
import java.util.Map;
import miui.cloud.sync.providers.AntispamSyncInfoProvider;
import miui.cloud.sync.providers.BluetoothSyncInfoProvider;
import miui.cloud.sync.providers.BrowserSyncInfoProvider;
import miui.cloud.sync.providers.CalendarSyncInfoProvider;
import miui.cloud.sync.providers.CalllogSyncInfoProvider;
import miui.cloud.sync.providers.ContactsSyncInfoProvider;
import miui.cloud.sync.providers.GallerySyncInfoProvider;
import miui.cloud.sync.providers.GlobalBrowserSyncInfoProvider;
import miui.cloud.sync.providers.NotesSyncInfoProvider;
import miui.cloud.sync.providers.PersonalAssistantSyncInfoProvider;
import miui.cloud.sync.providers.PhraseSyncInfoProvider;
import miui.cloud.sync.providers.QuickSearchBoxProvider;
import miui.cloud.sync.providers.SmsSyncInfoProvider;
import miui.cloud.sync.providers.SoundRecorderSyncInfoProvider;
import miui.cloud.sync.providers.WifiSyncInfoProvider;

/* JADX INFO: loaded from: classes5.dex */
public final class SyncInfoHelper {
    public static final int INVALID_COUNT = -1;
    public static final String TAG = "SyncInfoHelper";
    private static final Map<String, SyncInfoProvider> authorityMap;

    static {
        HashMap map = new HashMap();
        authorityMap = map;
        map.put(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY, new ContactsSyncInfoProvider());
        map.put("sms", new SmsSyncInfoProvider());
        map.put("com.miui.gallery.cloud.provider", new GallerySyncInfoProvider());
        map.put("call_log", new CalllogSyncInfoProvider());
        map.put("notes", new NotesSyncInfoProvider());
        map.put("wifi", new WifiSyncInfoProvider());
        map.put("records", new SoundRecorderSyncInfoProvider());
        map.put("com.miui.browser", new BrowserSyncInfoProvider());
        map.put(GlobalBrowserSyncInfoProvider.AUTHORITY, new GlobalBrowserSyncInfoProvider());
        map.put("antispam", new AntispamSyncInfoProvider());
        map.put("com.android.calendar", new CalendarSyncInfoProvider());
        map.put("personal_assistant", new PersonalAssistantSyncInfoProvider());
        map.put("com.android.quicksearchbox.cloud", new QuickSearchBoxProvider());
        map.put(PhraseSyncInfoProvider.AUTHORITY, new PhraseSyncInfoProvider());
        map.put(BluetoothSyncInfoProvider.AUTHORITY, new BluetoothSyncInfoProvider());
    }

    public static int getUnsyncedDataCount(Context context, String str) throws SyncInfoUnavailableException {
        SyncInfoProvider syncInfoProvider = authorityMap.get(str);
        if (syncInfoProvider == null) {
            throw new SyncInfoUnavailableException("getUnsyncedDataCount not implemented on authority: " + str);
        }
        return syncInfoProvider.getUnsyncedCount(context);
    }

    public static int getWifiOnlyUnsyncedDataCount(Context context, String str) throws SyncInfoUnavailableException {
        SyncInfoProvider syncInfoProvider = authorityMap.get(str);
        if (syncInfoProvider == null) {
            throw new SyncInfoUnavailableException("getWifiOnlyUnsyncedDataCount not implemented on authority: " + str);
        }
        return syncInfoProvider.getWifiOnlyUnsyncedCount(context);
    }

    public static int getUnSyncedSecretDataCount(Context context, String str) throws SyncInfoUnavailableException {
        SyncInfoProvider syncInfoProvider = authorityMap.get(str);
        if (syncInfoProvider == null) {
            throw new SyncInfoUnavailableException("getUnsyncedSecretDataCount not implemented on authority: " + str);
        }
        return syncInfoProvider.getUnSyncedSecretCount(context);
    }

    public static int getSyncedDataCount(Context context, String str) throws SyncInfoUnavailableException {
        SyncInfoProvider syncInfoProvider = authorityMap.get(str);
        if (syncInfoProvider == null) {
            throw new SyncInfoUnavailableException("getSyncedDataCount not implemented on authority: " + str);
        }
        return syncInfoProvider.getSyncedCount(context);
    }
}
