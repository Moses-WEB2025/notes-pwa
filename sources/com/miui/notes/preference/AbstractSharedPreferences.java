package com.miui.notes.preference;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: AbstractSharedPreferences.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u0000 \u001a2\u00020\u0001:\u0002\u0019\u001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\tH\u0016J\u001c\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0016J(\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u000e2\u0006\u0010\u000b\u001a\u00020\u00062\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u000eH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0017H\u0016J\u0011\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u0006H\u0096\u0002R.\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006`\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/preference/AbstractSharedPreferences;", "Landroid/content/SharedPreferences;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mPrefs", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getAll", "", "getString", "key", "defValue", "getStringSet", "", "defValues", "getInt", "", "getLong", "", "getFloat", "", "getBoolean", "", "contains", "AbstractEditor", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AbstractSharedPreferences implements SharedPreferences {
    private static final String STRING_SET_DELIMITER = "@^@";
    private static final String TAG = "AbstractSP";
    protected HashMap<String, String> mPrefs = new HashMap<>();

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<String> filterKeyArray = CollectionsKt.mutableListOf("keypref_account_entity");

    @Override // android.content.SharedPreferences
    public Map<String, String> getAll() {
        return this.mPrefs;
    }

    @Override // android.content.SharedPreferences
    public String getString(String key, String defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        INSTANCE.printKV(key, defValue, "getString");
        return str == null ? defValue : str;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        Companion companion = INSTANCE;
        companion.printKV(key, defValues, "getStringSet");
        Set<String> stringSet = companion.parseStringSet(str);
        return stringSet == null ? defValues : stringSet;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String key, int defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        INSTANCE.printKV(key, Integer.valueOf(defValue), "getInt");
        if (TextUtils.isEmpty(str)) {
            return defValue;
        }
        try {
            Intrinsics.checkNotNull(str);
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Logger.INSTANCE.e(TAG, "Invalid int value: " + str + ",exception:" + e);
            return defValue;
        }
    }

    @Override // android.content.SharedPreferences
    public long getLong(String key, long defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        INSTANCE.printKV(key, Long.valueOf(defValue), CallMethod.METHOD_GET);
        if (TextUtils.isEmpty(str)) {
            return defValue;
        }
        try {
            Intrinsics.checkNotNull(str);
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            Logger.INSTANCE.e(TAG, "Invalid long value: " + str + ",exception:" + e);
            return defValue;
        }
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String key, float defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        INSTANCE.printKV(key, Float.valueOf(defValue), CallMethod.METHOD_GET);
        if (TextUtils.isEmpty(str)) {
            return defValue;
        }
        try {
            Intrinsics.checkNotNull(str);
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            Logger.INSTANCE.e(TAG, "Invalid float value: " + str + ",exception:" + e);
            return defValue;
        }
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String key, boolean defValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = this.mPrefs.get(key);
        INSTANCE.printKV(key, Boolean.valueOf(defValue), CallMethod.METHOD_GET);
        if (TextUtils.isEmpty(str)) {
            return defValue;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (NumberFormatException e) {
            Logger.INSTANCE.e(TAG, "Invalid bool value: " + str + ",exception:" + e);
            return defValue;
        }
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.mPrefs.containsKey(key);
    }

    /* JADX INFO: compiled from: AbstractSharedPreferences.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\b$\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016J \u0010\n\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\fH\u0016J\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0014H\u0016R\u0012\u0010\u0004\u001a\u00020\u00058\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/preference/AbstractSharedPreferences$AbstractEditor;", "Landroid/content/SharedPreferences$Editor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "iValues", "Landroid/content/ContentValues;", "putString", "key", "", "value", "putStringSet", "values", "", "putInt", "", "putLong", "", "putFloat", "", "putBoolean", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    protected static abstract class AbstractEditor implements SharedPreferences.Editor {
        protected ContentValues iValues = new ContentValues();

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String key, String value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, value, null, 4, null);
            this.iValues.put(key, value);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.iValues.put(key, AbstractSharedPreferences.INSTANCE.buildStringSet(values));
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, values, null, 4, null);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String key, int value) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.iValues.put(key, String.valueOf(value));
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, Integer.valueOf(value), null, 4, null);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String key, long value) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.iValues.put(key, String.valueOf(value));
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, Long.valueOf(value), null, 4, null);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String key, float value) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.iValues.put(key, String.valueOf(value));
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, Float.valueOf(value), null, 4, null);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.iValues.put(key, String.valueOf(value));
            Companion.printKV$default(AbstractSharedPreferences.INSTANCE, key, Boolean.valueOf(value), null, 4, null);
            return this;
        }
    }

    /* JADX INFO: compiled from: AbstractSharedPreferences.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\b\u0010\f\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005H\u0002J\u001a\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0002J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/preference/AbstractSharedPreferences$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "STRING_SET_DELIMITER", "filterKeyArray", "", "printKV", "", "key", "value", XMLWriter.METHOD, "parseStringSet", "", "rawTxt", "buildStringSet", "stringSet", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        static /* synthetic */ void printKV$default(Companion companion, String str, Object obj, String str2, int i, Object obj2) {
            if ((i & 4) != 0) {
                str2 = "put";
            }
            companion.printKV(str, obj, str2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void printKV(String key, Object value, String method) {
            if (AbstractSharedPreferences.filterKeyArray.contains(key)) {
                return;
            }
            Logger.INSTANCE.d(AbstractSharedPreferences.TAG, method + " key:" + key + "->" + value);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<String> parseStringSet(String rawTxt) {
            if (rawTxt == null) {
                return null;
            }
            String[] strArrSplit = TextUtils.split(rawTxt, AbstractSharedPreferences.STRING_SET_DELIMITER);
            HashSet hashSet = new HashSet(strArrSplit.length);
            Collections.addAll(hashSet, Arrays.copyOf(strArrSplit, strArrSplit.length));
            return hashSet;
        }

        public final String buildStringSet(Set<String> stringSet) {
            if (stringSet == null) {
                return null;
            }
            return TextUtils.join(AbstractSharedPreferences.STRING_SET_DELIMITER, stringSet);
        }
    }
}
