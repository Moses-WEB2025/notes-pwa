package com.miui.notes.preference;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVContentChangeNotification;
import com.xiaomi.onetrack.g.a;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: MMKVSharedPreferences.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 =2\u00020\u0001:\u0002<=B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u001f\u001a\u00020\u001cJ*\u0010 \u001a\u00020!2\"\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b`\fJ\u0016\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0$H\u0016J\u001c\u0010%\u001a\u0004\u0018\u00010\u000b2\u0006\u0010&\u001a\u00020\u000b2\b\u0010'\u001a\u0004\u0018\u00010\u000bH\u0016J(\u0010(\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010)2\u0006\u0010&\u001a\u00020\u000b2\u000e\u0010*\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010)H\u0016J\u001a\u0010+\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010)2\b\u0010,\u001a\u0004\u0018\u00010\u000bH\u0002J\u0018\u0010-\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\bH\u0016J\u0018\u0010.\u001a\u00020/2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020/H\u0016J\u0018\u00100\u001a\u0002012\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u000201H\u0016J\u0018\u00102\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u001cH\u0016J\u0011\u00103\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u000bH\u0096\u0002J\b\u00104\u001a\u000205H\u0016J\u001a\u00106\u001a\u0004\u0018\u00010\u000b2\u0010\u00107\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010)J\u0010\u00108\u001a\u00020!2\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020!2\u0006\u00109\u001a\u00020:H\u0016R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R:\u0010\t\u001a\"\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nj\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R \u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u001cX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006>"}, d2 = {"Lcom/miui/notes/preference/MMKVSharedPreferences;", "Landroid/content/SharedPreferences;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/app/Application;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;)V", "mContext", a.e, "", "mPrefs", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getMPrefs", "()Ljava/util/HashMap;", "setMPrefs", "(Ljava/util/HashMap;)V", "mmkvStore", "Lcom/tencent/mmkv/MMKV;", "getMmkvStore", "()Lcom/tencent/mmkv/MMKV;", "mmkvStore$delegate", "Lkotlin/Lazy;", "dataSetRepo", "", "Landroidx/lifecycle/MutableLiveData;", "", "enableMMKV", "", "getEnableMMKV", "()Z", "isMigratedFromDB", "migratedFromDB", "", "hashMap", "getAll", "", "getString", "key", "defValue", "getStringSet", "", "defValues", "parseStringSet", "rawTxt", "getInt", "getLong", "", "getFloat", "", "getBoolean", "contains", "edit", "Landroid/content/SharedPreferences$Editor;", "buildStringSet", "stringSet", "registerOnSharedPreferenceChangeListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "unregisterOnSharedPreferenceChangeListener", "MMKVEditorImpl", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class MMKVSharedPreferences implements SharedPreferences {
    private static final String STRING_SET_DELIMITER = "@^@";
    private final Map<String, MutableLiveData<Object>> dataSetRepo;
    private final boolean enableMMKV;
    private final int hash;
    private final Application mContext;
    private HashMap<String, String> mPrefs;

    /* JADX INFO: renamed from: mmkvStore$delegate, reason: from kotlin metadata */
    private final Lazy mmkvStore;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "MMKVSharedPreferences";
    private static final Lazy<MMKVSharedPreferences> default$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.notes.preference.MMKVSharedPreferences$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MMKVSharedPreferences.default_delegate$lambda$6();
        }
    });

    private MMKVSharedPreferences(Application application) {
        int iHashCode = hashCode();
        this.hash = iHashCode;
        this.mmkvStore = LazyKt.lazy(new Function0() { // from class: com.miui.notes.preference.MMKVSharedPreferences$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MMKVSharedPreferences.mmkvStore_delegate$lambda$0();
            }
        });
        this.dataSetRepo = new LinkedHashMap();
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, str + " constructor,enableMMKV:" + this.enableMMKV + ", " + this + ",sp@" + iHashCode + StringUtils.SPACE);
        this.mContext = application;
        if (this.enableMMKV) {
            Logger.INSTANCE.d(str, "mmkv path " + MMKV.initialize(application));
        }
        Logger.INSTANCE.d(str, "sp@" + iHashCode + ",constructor finished");
    }

    protected final HashMap<String, String> getMPrefs() {
        return this.mPrefs;
    }

    protected final void setMPrefs(HashMap<String, String> map) {
        this.mPrefs = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MMKV getMmkvStore() {
        return (MMKV) this.mmkvStore.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MMKV mmkvStore_delegate$lambda$0() {
        return MMKV.mmkvWithID("mmkv_sp", 2);
    }

    public final boolean getEnableMMKV() {
        return this.enableMMKV;
    }

    public final boolean isMigratedFromDB() {
        return this.enableMMKV && getMmkvStore().getBoolean("migrated", false);
    }

    public final void migratedFromDB(HashMap<String, String> hashMap) {
        Intrinsics.checkNotNullParameter(hashMap, "hashMap");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",transferFromDB");
        if (this.enableMMKV) {
            Set<String> setKeySet = hashMap.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            for (String str : setKeySet) {
                Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",transferFromDB," + str);
                getMmkvStore().putString(str, hashMap.get(str));
            }
            getMmkvStore().putBoolean("migrated", true);
            com.miui.common.tool.Logger.INSTANCE.e(TAG, "sp@" + this.hash + ",transferFromDB finished!!!! " + isMigratedFromDB());
        }
    }

    @Override // android.content.SharedPreferences
    public Map<String, String> getAll() {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "sp@" + this.hash + ",get all ");
        if (this.mPrefs == null) {
            this.mPrefs = new HashMap<>();
            Logger.INSTANCE.d(str, "sp@" + this.hash + ",get all ");
            String[] strArrAllKeys = getMmkvStore().allKeys();
            if (strArrAllKeys != null) {
                for (String str2 : strArrAllKeys) {
                    Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get all ");
                    HashMap<String, String> map = this.mPrefs;
                    Intrinsics.checkNotNull(map);
                    String string = getMmkvStore().getString(str2, "null");
                    Intrinsics.checkNotNull(string);
                    map.put(str2, string);
                }
            }
        }
        HashMap<String, String> map2 = this.mPrefs;
        Intrinsics.checkNotNull(map2);
        return map2;
    }

    @Override // android.content.SharedPreferences
    public String getString(String key, String defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get str key " + key);
        return getMmkvStore().getString(key, defValue);
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Intrinsics.checkNotNullParameter(key, "key");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get str set " + key);
        String string = getMmkvStore().getString(key, null);
        if (string == null) {
            return null;
        }
        return parseStringSet(string);
    }

    private final Set<String> parseStringSet(String rawTxt) {
        if (rawTxt == null) {
            return null;
        }
        String[] strArrSplit = TextUtils.split(rawTxt, STRING_SET_DELIMITER);
        HashSet hashSet = new HashSet(strArrSplit.length);
        Collections.addAll(hashSet, Arrays.copyOf(strArrSplit, strArrSplit.length));
        return hashSet;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String key, int defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getMmkvStore().getString(key, null);
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get in key " + key + ":" + string);
        return string != null ? Integer.parseInt(string) : defValue;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String key, long defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getMmkvStore().getString(key, null);
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get long key " + key + ":" + string);
        return string != null ? Long.parseLong(string) : defValue;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String key, float defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getMmkvStore().getString(key, null);
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get float key " + key + ":" + string);
        return string != null ? Float.parseFloat(string) : defValue;
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String key, boolean defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getMmkvStore().getString(key, null);
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",get bool key " + key + ":" + string);
        return string != null ? Boolean.parseBoolean(string) : defValue;
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",contains key " + key);
        return getMmkvStore().contains(key);
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        return new MMKVEditorImpl();
    }

    public final String buildStringSet(Set<String> stringSet) {
        if (stringSet == null) {
            return null;
        }
        return TextUtils.join(STRING_SET_DELIMITER, stringSet);
    }

    /* JADX INFO: compiled from: MMKVSharedPreferences.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016J$\u0010\b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\nH\u0016J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000eH\u0016J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0010H\u0016J\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/preference/MMKVSharedPreferences$MMKVEditorImpl;", "Landroid/content/SharedPreferences$Editor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/preference/MMKVSharedPreferences;)V", "putString", "key", "", "value", "putStringSet", "values", "", "putInt", "", "putLong", "", "putFloat", "", "putBoolean", "", "remove", "clear", "commit", "apply", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class MMKVEditorImpl implements SharedPreferences.Editor {
        public MMKVEditorImpl() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String key, String value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",put str key " + key + ":" + value);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, value);
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(value));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(value);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            Intrinsics.checkNotNullParameter(key, "key");
            String strBuildStringSet = MMKVSharedPreferences.this.buildStringSet(values);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, strBuildStringSet);
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(strBuildStringSet));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(strBuildStringSet);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String key, int value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",put int key " + key + ":" + value);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, String.valueOf(value));
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(Integer.valueOf(value)));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(Integer.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String key, long value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",put long key " + key + ":" + value);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, String.valueOf(value));
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(Long.valueOf(value)));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(Long.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String key, float value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",put float key " + key + ":" + value);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, String.valueOf(value));
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(Float.valueOf(value)));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(Float.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",put int key " + key + ":" + value);
            MMKVSharedPreferences.this.getMmkvStore().putString(key, String.valueOf(value));
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(Boolean.valueOf(value)));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(Boolean.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",remove  key " + key);
            MMKVSharedPreferences.this.getMmkvStore().remove(key);
            if (MMKVSharedPreferences.this.dataSetRepo.get(key) == null) {
                MMKVSharedPreferences.this.dataSetRepo.put(key, new MutableLiveData(null));
            }
            Object obj = MMKVSharedPreferences.this.dataSetRepo.get(key);
            Intrinsics.checkNotNull(obj);
            ((MutableLiveData) obj).postValue(null);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",clear");
            MMKVSharedPreferences.this.getMmkvStore().clear();
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            com.miui.common.tool.Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",commit");
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            com.miui.common.tool.Logger.INSTANCE.d(MMKVSharedPreferences.TAG, "sp@" + MMKVSharedPreferences.this.hash + ",apply");
        }
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(final SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",registerOnSharedPreferenceChangeListener");
        if (this.dataSetRepo.get(PreferenceUtils.PREFERENCE_ENABLE_FLOAT_MODE) == null) {
            this.dataSetRepo.put(PreferenceUtils.PREFERENCE_ENABLE_FLOAT_MODE, new MutableLiveData<>(false));
        }
        MutableLiveData<Object> mutableLiveData = this.dataSetRepo.get(PreferenceUtils.PREFERENCE_ENABLE_FLOAT_MODE);
        Intrinsics.checkNotNull(mutableLiveData);
        mutableLiveData.observeForever(new Observer() { // from class: com.miui.notes.preference.MMKVSharedPreferences$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MMKVSharedPreferences.registerOnSharedPreferenceChangeListener$lambda$4(listener, this, obj);
            }
        });
        MMKV.registerContentChangeNotify(new MMKVContentChangeNotification() { // from class: com.miui.notes.preference.MMKVSharedPreferences$$ExternalSyntheticLambda3
            @Override // com.tencent.mmkv.MMKVContentChangeNotification
            public final void onContentChangedByOuterProcess(String str) {
                MMKVSharedPreferences.registerOnSharedPreferenceChangeListener$lambda$5(this.f$0, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerOnSharedPreferenceChangeListener$lambda$4(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener, MMKVSharedPreferences mMKVSharedPreferences, Object obj) {
        onSharedPreferenceChangeListener.onSharedPreferenceChanged(mMKVSharedPreferences, PreferenceUtils.PREFERENCE_ENABLE_FLOAT_MODE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerOnSharedPreferenceChangeListener$lambda$5(MMKVSharedPreferences mMKVSharedPreferences, String str) {
        Logger.INSTANCE.d(TAG, "sp@" + mMKVSharedPreferences.hash + ",registerOnSharedPreferenceChangeListener,onChanged by other process,mmapID: " + str);
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Logger.INSTANCE.d(TAG, "sp@" + this.hash + ",unregisterOnSharedPreferenceChangeListener");
        MMKV.unregisterContentChangeNotify();
    }

    /* JADX INFO: compiled from: MMKVSharedPreferences.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/miui/notes/preference/MMKVSharedPreferences$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "STRING_SET_DELIMITER", "default", "Lcom/miui/notes/preference/MMKVSharedPreferences;", "getDefault", "()Lcom/miui/notes/preference/MMKVSharedPreferences;", "default$delegate", "Lkotlin/Lazy;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MMKVSharedPreferences getDefault() {
            return (MMKVSharedPreferences) MMKVSharedPreferences.default$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MMKVSharedPreferences default_delegate$lambda$6() {
        return new MMKVSharedPreferences(BaseNoteApp.INSTANCE.getInstance());
    }
}
