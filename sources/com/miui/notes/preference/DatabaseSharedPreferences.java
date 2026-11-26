package com.miui.notes.preference;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.floatwindow.FloatService;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.g.a;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DatabaseSharedPreferences.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\u001c\n\u0002\b\t\u0018\u0000 M2\u00020\u0001:\u0004JKLMB\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010%\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\u00170&H\u0016J\u001c\u0010'\u001a\u0004\u0018\u00010\u00172\u0006\u0010(\u001a\u00020\u00172\b\u0010)\u001a\u0004\u0018\u00010\u0017H\u0016J(\u0010*\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010+2\u0006\u0010(\u001a\u00020\u00172\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010+H\u0016J\u0018\u0010-\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u0019H\u0016J\u0018\u0010.\u001a\u00020/2\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020/H\u0016J\u0018\u00100\u001a\u0002012\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u000201H\u0016J\u0018\u00102\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u001bH\u0016J\u0011\u00103\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020\u0017H\u0096\u0002J\b\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u0012H\u0016J\u0010\u00109\u001a\u0002072\u0006\u00108\u001a\u00020\u0012H\u0016J\b\u0010:\u001a\u000207H\u0002J\u000e\u0010;\u001a\u0002072\u0006\u0010<\u001a\u00020\u0003J\b\u0010=\u001a\u000207H\u0002J\b\u0010>\u001a\u000207H\u0002J\u0010\u0010?\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002J\b\u0010@\u001a\u000207H\u0002J\u0010\u0010A\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002J\u0010\u0010B\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002J\u0016\u0010C\u001a\u0002072\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00170EH\u0002J\u0010\u0010F\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002J\u0016\u0010G\u001a\u0002072\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00170EH\u0002J\u0010\u0010H\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002J\u0010\u0010I\u001a\u0002072\u0006\u0010(\u001a\u00020\u0017H\u0002R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001f\u001a\u00020 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b!\u0010\"¨\u0006N"}, d2 = {"Lcom/miui/notes/preference/DatabaseSharedPreferences;", "Lcom/miui/notes/preference/AbstractSharedPreferences;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/app/Application;", "contentUri", "Landroid/net/Uri;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;Landroid/net/Uri;)V", "mContext", "mContentUri", "mWorkThread", "Landroid/os/HandlerThread;", "mBackgroundHandler", "Lcom/miui/notes/preference/DatabaseSharedPreferences$BackgroundHandler;", "mMainHandler", "Landroid/os/Handler;", "mListeners", "", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "", "mOuterListeners", "mExpiredKeys", "", "", "mPendingKeys", "", "mLoaded", "", "lock", "Ljava/lang/Object;", a.e, "migratedToMMKV", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getMigratedToMMKV", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "migratedToMMKV$delegate", "Lkotlin/Lazy;", "getAll", "", "getString", "key", "defValue", "getStringSet", "", "defValues", "getInt", "getLong", "", "getFloat", "", "getBoolean", "contains", "edit", "Landroid/content/SharedPreferences$Editor;", "registerOnSharedPreferenceChangeListener", "", "listener", "unregisterOnSharedPreferenceChangeListener", "registerFloatServiceSwitchContentObserver", "updateFloatWindowByConfigChangedFromOuter", "app", "startLoadFromDatabase", "loadFromDatabase", "awaitKeyLoadedLocked", "awaitAllKeyLoadedLocked", "awaitLoadedLocked", "notifySharedPreferenceChanged", "notifySharedPreferenceChangedInMainThread", PreferenceProviderHelper.URI_PARAM_KEYS, "", "notifySharedPreferenceChangedFromOuter", "notifySharedPreferenceChangedFromOuterInMainThread", "increasePendingKeyLocked", "decreasePendingKeyLocked", "EditorImpl", "ContentObserverImpl", "BackgroundHandler", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DatabaseSharedPreferences extends AbstractSharedPreferences {
    public static final int MESSAGE_ID_LOAD = 1;
    private final int hash;
    private final Object lock = new Object();
    private final BackgroundHandler mBackgroundHandler;
    private final Uri mContentUri;
    private final Application mContext;
    private final Set<String> mExpiredKeys;
    private final Map<SharedPreferences.OnSharedPreferenceChangeListener, Object> mListeners;
    private boolean mLoaded;
    private final Handler mMainHandler;
    private final Map<SharedPreferences.OnSharedPreferenceChangeListener, Object> mOuterListeners;
    private final Map<String, Integer> mPendingKeys;
    private final HandlerThread mWorkThread;

    /* JADX INFO: renamed from: migratedToMMKV$delegate, reason: from kotlin metadata */
    private final Lazy migratedToMMKV;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "DatabaseSharedPreferences";
    private static final Lazy<DatabaseSharedPreferences> default$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DatabaseSharedPreferences.default_delegate$lambda$18();
        }
    });

    private DatabaseSharedPreferences(Application application, Uri uri) {
        int iHashCode = hashCode();
        this.hash = iHashCode;
        this.migratedToMMKV = LazyKt.lazy(new Function0() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DatabaseSharedPreferences.migratedToMMKV_delegate$lambda$0();
            }
        });
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "DatabaseSharedPreferences constructor, " + this + ",sp@" + iHashCode + " ,migratedToMMKV:" + getMigratedToMMKV());
        this.mContext = application;
        this.mContentUri = uri;
        HandlerThread handlerThread = new HandlerThread("sp-worker");
        this.mWorkThread = handlerThread;
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Intrinsics.checkNotNullExpressionValue(looper, "getLooper(...)");
        this.mBackgroundHandler = new BackgroundHandler(this, looper);
        this.mMainHandler = new Handler(application.getMainLooper());
        this.mListeners = new WeakHashMap();
        this.mOuterListeners = new HashMap();
        this.mExpiredKeys = new HashSet();
        this.mPendingKeys = new HashMap();
        registerFloatServiceSwitchContentObserver();
        if (getMigratedToMMKV().get()) {
            Logger.INSTANCE.e(str, "sp@" + iHashCode + ",migratedToMMKV already done!!!");
        } else {
            startLoadFromDatabase();
        }
        Logger.INSTANCE.d(str, "sp@" + iHashCode + ",constructor finished");
    }

    private final AtomicBoolean getMigratedToMMKV() {
        return (AtomicBoolean) this.migratedToMMKV.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AtomicBoolean migratedToMMKV_delegate$lambda$0() {
        return new AtomicBoolean(MMKVSharedPreferences.INSTANCE.getDefault().isMigratedFromDB());
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public Map<String, String> getAll() {
        Map<String, String> all;
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getAll();
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get all ");
        synchronized (this.lock) {
            awaitAllKeyLoadedLocked();
            all = super.getAll();
        }
        return all;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public String getString(String key, String defValue) {
        String string;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getString(key, defValue);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get str key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            string = super.getString(key, defValue);
        }
        return string;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Set<String> stringSet;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getStringSet(key, defValues);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get str set " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            stringSet = super.getStringSet(key, defValues);
        }
        return stringSet;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public int getInt(String key, int defValue) {
        int i;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getInt(key, defValue);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get int key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            i = super.getInt(key, defValue);
        }
        return i;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public long getLong(String key, long defValue) {
        long j;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getLong(key, defValue);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get long key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            j = super.getLong(key, defValue);
        }
        return j;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public float getFloat(String key, float defValue) {
        float f;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getFloat(key, defValue);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get float key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            f = super.getFloat(key, defValue);
        }
        return f;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public boolean getBoolean(String key, boolean defValue) {
        boolean z;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().getBoolean(key, defValue);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get bool key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            z = super.getBoolean(key, defValue);
        }
        return z;
    }

    @Override // com.miui.notes.preference.AbstractSharedPreferences, android.content.SharedPreferences
    public boolean contains(String key) {
        boolean zContains;
        Intrinsics.checkNotNullParameter(key, "key");
        if (getMigratedToMMKV().get()) {
            return MMKVSharedPreferences.INSTANCE.getDefault().contains(key);
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",contains key " + key);
        synchronized (this.lock) {
            awaitKeyLoadedLocked(key);
            zContains = super.contains(key);
        }
        return zContains;
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        return getMigratedToMMKV().get() ? MMKVSharedPreferences.INSTANCE.getDefault().edit() : new EditorImpl();
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",registerOnSharedPreferenceChangeListener");
        synchronized (this.lock) {
            this.mListeners.put(listener, null);
        }
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",unregisterOnSharedPreferenceChangeListener");
        synchronized (this.lock) {
            this.mListeners.remove(listener);
        }
    }

    private final void registerFloatServiceSwitchContentObserver() {
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",registerContentObserver");
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda5
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                DatabaseSharedPreferences.registerFloatServiceSwitchContentObserver$lambda$11(this.f$0, sharedPreferences, str);
            }
        };
        if (getMigratedToMMKV().get()) {
            MMKVSharedPreferences.INSTANCE.getDefault().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        } else {
            this.mContext.getContentResolver().registerContentObserver(this.mContentUri, true, new ContentObserverImpl(this.mBackgroundHandler));
        }
        this.mOuterListeners.put(onSharedPreferenceChangeListener, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerFloatServiceSwitchContentObserver$lambda$11(DatabaseSharedPreferences databaseSharedPreferences, SharedPreferences sharedPreferences, String str) {
        Logger.INSTANCE.e(TAG, "sp@" + databaseSharedPreferences.hash + ",registerContentObserver/onPreferenceChange,onKeyChange " + str);
        if (Intrinsics.areEqual(PreferenceUtils.PREFERENCE_ENABLE_FLOAT_MODE, str)) {
            databaseSharedPreferences.updateFloatWindowByConfigChangedFromOuter(databaseSharedPreferences.mContext);
        }
    }

    public final void updateFloatWindowByConfigChangedFromOuter(Application app) {
        Intrinsics.checkNotNullParameter(app, "app");
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "updateFloatWindowByConfigChangedFromOuter");
        Application application = app;
        if (PreferenceUtils.getEnableFloatMode(application) && Settings.canDrawOverlays(application)) {
            FloatService.INSTANCE.startExcerptService(application, str + "->updateFloatWindowByConfigChangedFromOuter");
            return;
        }
        Intent intent = new Intent();
        intent.setAction(FloatService.ACTION_STOP_FLOAT_SERVICE);
        intent.setPackage("com.miui.notes");
        app.sendBroadcast(intent);
    }

    private final void startLoadFromDatabase() {
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",startLoadFromDatabase ");
        synchronized (this.lock) {
            this.mLoaded = false;
            Unit unit = Unit.INSTANCE;
        }
        this.mBackgroundHandler.removeMessages(1);
        this.mBackgroundHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadFromDatabase() {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        int i = this.hash;
        logger.d(str, "sp@" + i + ",loadFromDatabase,try lock ,sp@" + i + ",isMigratedFromDB: " + MMKVSharedPreferences.INSTANCE.getDefault().isMigratedFromDB());
        if (MMKVSharedPreferences.INSTANCE.getDefault().isMigratedFromDB()) {
            return;
        }
        synchronized (this.lock) {
            Logger.INSTANCE.d(str, "loadFromDatabase,locked,sp@" + this.hash);
            final HashMap<String, String> map = new HashMap<>();
            Cursor cursorQuery = this.mContext.getContentResolver().query(this.mContentUri, new String[]{"key", "value"}, null, null, null);
            if (cursorQuery != null) {
                NoteEntityHelper.INSTANCE.useWithClose(cursorQuery, "loadFromDatabase " + this.mContentUri + " ,sp@" + hashCode(), new Function1() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return DatabaseSharedPreferences.loadFromDatabase$lambda$15$lambda$13(map, (Cursor) obj);
                    }
                });
            }
            Set<Map.Entry<String, String>> setEntrySet = this.mPrefs.entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            for (Map.Entry<String, String> entry : setEntrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (this.mPendingKeys.containsKey(key)) {
                    map.put(key, value);
                }
            }
            this.mPrefs = map;
            this.mExpiredKeys.clear();
            this.mLoaded = true;
            HashMap<String, String> map2 = this.mPrefs;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<String, String> entry2 : map2.entrySet()) {
                if (!Intrinsics.areEqual(entry2.getKey(), PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY)) {
                    linkedHashMap.put(entry2.getKey(), entry2.getValue());
                }
            }
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            if (MMKVSharedPreferences.INSTANCE.getDefault().getEnableMMKV()) {
                MMKVSharedPreferences.INSTANCE.getDefault().migratedFromDB(this.mPrefs);
                getMigratedToMMKV().set(true);
            }
            Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",loadFromDb for sp:" + linkedHashMap2 + ",release lock");
            this.lock.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadFromDatabase$lambda$15$lambda$13(HashMap map, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        while (it.moveToNext()) {
            map.put(it.getString(0), it.getString(1));
        }
        return Unit.INSTANCE;
    }

    private final void awaitKeyLoadedLocked(String key) {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "sp@" + this.hash + ",awaitKeyLoadedLocked method invoked, for sp key " + key);
        if (this.mExpiredKeys.contains(key)) {
            Logger.INSTANCE.d(str, "sp@" + this.hash + ",awaitKeyLoadedLocked method invoked, for sp key " + key + ",key expired!!!need reload!!");
            startLoadFromDatabase();
        }
        awaitLoadedLocked(key);
    }

    private final void awaitAllKeyLoadedLocked() {
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",awaitAllKeyLoadedLocked");
        if (!this.mExpiredKeys.isEmpty()) {
            startLoadFromDatabase();
        }
        awaitLoadedLocked("allKeys");
    }

    private final void awaitLoadedLocked(String key) {
        while (!this.mLoaded) {
            try {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                logger.d(str, "sp@" + this.hash + ",awaitLoadedLocked,for key " + key + ",gonna wait....");
                this.lock.wait(500L);
                Logger.INSTANCE.d(str, "sp@" + this.hash + ",awaitLoadedLocked,for key " + key + ",wait done.......");
            } catch (InterruptedException e) {
                Logger.INSTANCE.e(TAG, "sp@" + this.hash + ",awaitLoadedLocked,for key " + key + ",interrupted " + e.getMessage());
            }
        }
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",awaitLoadedLocked,for key " + key + ",wait finished!!!!!!!");
    }

    private final void notifySharedPreferenceChanged(String key) {
        Iterator<SharedPreferences.OnSharedPreferenceChangeListener> it = this.mListeners.keySet().iterator();
        while (it.hasNext()) {
            it.next().onSharedPreferenceChanged(this, key);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifySharedPreferenceChangedInMainThread(final Iterable<String> keys) {
        this.mMainHandler.post(new Runnable() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DatabaseSharedPreferences.notifySharedPreferenceChangedInMainThread$lambda$16(keys, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifySharedPreferenceChangedInMainThread$lambda$16(Iterable iterable, DatabaseSharedPreferences databaseSharedPreferences) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            databaseSharedPreferences.notifySharedPreferenceChanged((String) it.next());
        }
    }

    private final void notifySharedPreferenceChangedFromOuter(String key) {
        Iterator<SharedPreferences.OnSharedPreferenceChangeListener> it = this.mOuterListeners.keySet().iterator();
        while (it.hasNext()) {
            it.next().onSharedPreferenceChanged(this, key);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifySharedPreferenceChangedFromOuterInMainThread(final Iterable<String> keys) {
        this.mMainHandler.post(new Runnable() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DatabaseSharedPreferences.notifySharedPreferenceChangedFromOuterInMainThread$lambda$17(keys, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifySharedPreferenceChangedFromOuterInMainThread$lambda$17(Iterable iterable, DatabaseSharedPreferences databaseSharedPreferences) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            databaseSharedPreferences.notifySharedPreferenceChangedFromOuter((String) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void increasePendingKeyLocked(String key) {
        Integer num = this.mPendingKeys.get(key);
        this.mPendingKeys.put(key, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void decreasePendingKeyLocked(String key) {
        Integer num = this.mPendingKeys.get(key);
        if (num != null) {
            if (num.intValue() > 1) {
                this.mPendingKeys.put(key, Integer.valueOf(num.intValue() - 1));
            } else {
                this.mPendingKeys.remove(key);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DatabaseSharedPreferences.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016J\b\u0010\u000b\u001a\u00020\u0005H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/preference/DatabaseSharedPreferences$EditorImpl;", "Lcom/miui/notes/preference/AbstractSharedPreferences$AbstractEditor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/preference/DatabaseSharedPreferences;)V", "iCommitted", "", "remove", "Landroid/content/SharedPreferences$Editor;", "key", "", "clear", "commit", "apply", "", "commitToMemoryLocked", "lockSource", "", "commitToDatabase", "invokeSource", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    final class EditorImpl extends AbstractSharedPreferences.AbstractEditor {
        private boolean iCommitted;

        public EditorImpl() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            throw new UnsupportedOperationException();
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            throw new UnsupportedOperationException();
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commit,try lock");
            synchronized (DatabaseSharedPreferences.this.lock) {
                commitToMemoryLocked(0);
                Unit unit = Unit.INSTANCE;
            }
            return commitToDatabase(0);
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",apply,try lock");
            synchronized (DatabaseSharedPreferences.this.lock) {
                commitToMemoryLocked(1);
                Unit unit = Unit.INSTANCE;
            }
            BackgroundHandler backgroundHandler = DatabaseSharedPreferences.this.mBackgroundHandler;
            final DatabaseSharedPreferences databaseSharedPreferences = DatabaseSharedPreferences.this;
            backgroundHandler.post(new Runnable() { // from class: com.miui.notes.preference.DatabaseSharedPreferences$EditorImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DatabaseSharedPreferences.EditorImpl.apply$lambda$3(databaseSharedPreferences, this);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void apply$lambda$3(DatabaseSharedPreferences databaseSharedPreferences, EditorImpl editorImpl) {
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + databaseSharedPreferences.hash + ",sp bg worker,try lock");
            synchronized (databaseSharedPreferences.lock) {
                Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + databaseSharedPreferences.hash + ",sp bg worker,locked");
                editorImpl.commitToDatabase(1);
                Unit unit = Unit.INSTANCE;
            }
        }

        public final void commitToMemoryLocked(int lockSource) {
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToMemoryLocked " + lockSource);
            if (this.iCommitted) {
                throw new IllegalStateException("Can't commit editor twice".toString());
            }
            Set<String> setKeySet = this.iValues.keySet();
            for (String str : setKeySet) {
                String asString = this.iValues.getAsString(str);
                Intrinsics.checkNotNullExpressionValue(asString, "getAsString(...)");
                DatabaseSharedPreferences.this.mPrefs.put(str, this.iValues.getAsString(str));
                if (!Intrinsics.areEqual(str, PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY)) {
                    Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToMemoryLocked with key:" + str + "=" + asString);
                } else {
                    Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToMemoryLocked with key:" + str + "=***");
                }
                DatabaseSharedPreferences.this.mExpiredKeys.remove(str);
                DatabaseSharedPreferences databaseSharedPreferences = DatabaseSharedPreferences.this;
                Intrinsics.checkNotNull(str);
                databaseSharedPreferences.increasePendingKeyLocked(str);
            }
            DatabaseSharedPreferences databaseSharedPreferences2 = DatabaseSharedPreferences.this;
            Intrinsics.checkNotNull(setKeySet);
            databaseSharedPreferences2.notifySharedPreferenceChangedInMainThread(setKeySet);
            this.iCommitted = true;
        }

        public final boolean commitToDatabase(int invokeSource) {
            int iUpdate;
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToDatabase ->" + invokeSource);
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                iUpdate = DatabaseSharedPreferences.this.mContext.getContentResolver().update(DatabaseSharedPreferences.this.mContentUri, this.iValues, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.INSTANCE.e(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToDatabase exception!!" + e.getMessage());
                iUpdate = 0;
            }
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",commitToDatabase,ts cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
            return iUpdate == this.iValues.size();
        }
    }

    /* JADX INFO: compiled from: DatabaseSharedPreferences.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/miui/notes/preference/DatabaseSharedPreferences$ContentObserverImpl;", "Landroid/database/ContentObserver;", "handler", "Landroid/os/Handler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/preference/DatabaseSharedPreferences;Landroid/os/Handler;)V", "onChange", "", "selfChange", "", CallMethod.ARG_URI, "Landroid/net/Uri;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class ContentObserverImpl extends ContentObserver {
        public ContentObserverImpl(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Uri must not be null".toString());
            }
            String[] keysParam = PreferenceProviderHelper.getKeysParam(uri);
            boolean zIsCallerMyself = PreferenceProviderHelper.isCallerMyself(uri);
            Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + DatabaseSharedPreferences.this.hash + ",onChange: uri=" + uri + ",caller from myself:" + zIsCallerMyself);
            ArrayList arrayList = new ArrayList();
            Object obj = DatabaseSharedPreferences.this.lock;
            DatabaseSharedPreferences databaseSharedPreferences = DatabaseSharedPreferences.this;
            synchronized (obj) {
                try {
                    if (keysParam == null) {
                        Set<String> setKeySet = databaseSharedPreferences.mPrefs.keySet();
                        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
                        arrayList.addAll(setKeySet);
                    } else {
                        Collections.addAll(arrayList, Arrays.copyOf(keysParam, keysParam.length));
                    }
                    for (Object obj2 : arrayList) {
                        Intrinsics.checkNotNullExpressionValue(obj2, "next(...)");
                        String str = (String) obj2;
                        databaseSharedPreferences.decreasePendingKeyLocked(str);
                        if (!zIsCallerMyself) {
                            databaseSharedPreferences.mExpiredKeys.add(str);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (zIsCallerMyself) {
                return;
            }
            ArrayList arrayList2 = arrayList;
            DatabaseSharedPreferences.this.notifySharedPreferenceChangedFromOuterInMainThread(arrayList2);
            DatabaseSharedPreferences.this.notifySharedPreferenceChangedInMainThread(arrayList2);
        }
    }

    /* JADX INFO: compiled from: DatabaseSharedPreferences.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/miui/notes/preference/DatabaseSharedPreferences$BackgroundHandler;", "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/preference/DatabaseSharedPreferences;Landroid/os/Looper;)V", "handleMessage", "", "msg", "Landroid/os/Message;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class BackgroundHandler extends Handler {
        final /* synthetic */ DatabaseSharedPreferences this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BackgroundHandler(DatabaseSharedPreferences databaseSharedPreferences, Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(looper, "looper");
            this.this$0 = databaseSharedPreferences;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            if (msg.what == 1) {
                Logger.INSTANCE.d(DatabaseSharedPreferences.TAG, "sp@" + this.this$0.hash + ",msg_id_load");
                this.this$0.loadFromDatabase();
            }
        }
    }

    /* JADX INFO: compiled from: DatabaseSharedPreferences.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/preference/DatabaseSharedPreferences$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MESSAGE_ID_LOAD", "", "TAG", "", "default", "Lcom/miui/notes/preference/DatabaseSharedPreferences;", "getDefault", "()Lcom/miui/notes/preference/DatabaseSharedPreferences;", "default$delegate", "Lkotlin/Lazy;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DatabaseSharedPreferences getDefault() {
            return (DatabaseSharedPreferences) DatabaseSharedPreferences.default$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DatabaseSharedPreferences default_delegate$lambda$18() {
        BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        Uri CONTENT_URI = PreferenceProviderHelper.CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(CONTENT_URI, "CONTENT_URI");
        return new DatabaseSharedPreferences(companion, CONTENT_URI);
    }
}
