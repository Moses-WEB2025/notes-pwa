package com.miui.notes.mishow;

import android.content.SharedPreferences;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.excerpt.NotifyUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MiShowPreferenceHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/mishow/MiShowPreferenceHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PREF_MI_SHOW_DATA", "", "miShowDataSharedPreferences", "Landroid/content/SharedPreferences;", "getMiShowDataSharedPreferences", "()Landroid/content/SharedPreferences;", "miShowDataSharedPreferences$delegate", "Lkotlin/Lazy;", "setNoteId", "", "fileName", NotifyUtils.KEY_NOTE_ID, "", "getNoteId", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiShowPreferenceHelper {
    private static final String PREF_MI_SHOW_DATA = "mishow_data";
    public static final MiShowPreferenceHelper INSTANCE = new MiShowPreferenceHelper();

    /* JADX INFO: renamed from: miShowDataSharedPreferences$delegate, reason: from kotlin metadata */
    private static final Lazy miShowDataSharedPreferences = LazyKt.lazy(new Function0() { // from class: com.miui.notes.mishow.MiShowPreferenceHelper$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MiShowPreferenceHelper.miShowDataSharedPreferences_delegate$lambda$0();
        }
    });

    private MiShowPreferenceHelper() {
    }

    private final SharedPreferences getMiShowDataSharedPreferences() {
        Object value = miShowDataSharedPreferences.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (SharedPreferences) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedPreferences miShowDataSharedPreferences_delegate$lambda$0() {
        return BaseNoteApp.INSTANCE.getInstance().getSharedPreferences(PREF_MI_SHOW_DATA, 0);
    }

    public final void setNoteId(String fileName, long noteId) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        getMiShowDataSharedPreferences().edit().putLong(fileName, noteId).apply();
    }

    @JvmStatic
    public static final long getNoteId(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return INSTANCE.getMiShowDataSharedPreferences().getLong(fileName, 0L);
    }
}
