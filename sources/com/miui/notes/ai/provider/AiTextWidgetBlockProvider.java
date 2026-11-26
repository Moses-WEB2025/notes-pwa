package com.miui.notes.ai.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.smartaction.SmartAction;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: AiTextWidgetBlockProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 (2\u00020\u0001:\u0001(B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J&\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0016J.\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0016JO\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0010\u0010\u0019\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0010\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u001c\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J1\u0010#\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0010\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u001aH\u0016¢\u0006\u0002\u0010%J;\u0010&\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0010\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u001aH\u0016¢\u0006\u0002\u0010'R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/miui/notes/ai/provider/AiTextWidgetBlockProvider;", "Landroid/content/ContentProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DEVICE_BLACKLIST", "", "", "GLOBAL_DEVICE_BLACKLIST", "CALC_WHITE_DEVICE_LIST", "CALC_WHITE_GLOBAL_DEVICE_LIST", "onCreate", "", "loadBlackListFromResources", "", "loadCalcWhiteListFromResources", "call", "Landroid/os/Bundle;", XMLWriter.METHOD, "arg", SecureDatabaseHelper.TABLE_EXTRAS, "authority", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", CallMethod.ARG_URI, "Landroid/net/Uri;", "projection", "", "selection", "selectionArgs", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "getType", "insert", "values", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiTextWidgetBlockProvider extends ContentProvider {
    public static final String AUTHORITY = "com.miui.notes.ai.text.widget.provider";
    private static final String METHOD_IS_AI_TEXT_WIDGET_ALLOWED = "allowAiEnhancedDisplay";
    private static final String METHOD_IS_AI_WIDGET_ALLOWED = "isAiWidgetAllowed";
    private static final String METHOD_IS_CALC_HAND_WHITE_SUPPORT = "calcHandWhiteSupport";
    private static final String RESULT_IS_ALLOWED = "is_allowed";
    private static final String TAG = "AiTextWidgetBlockProvider";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Uri CONTENT_URI = Uri.parse("content://com.miui.notes.ai.text.widget.provider/device");
    private final Set<String> DEVICE_BLACKLIST = new HashSet();
    private final Set<String> GLOBAL_DEVICE_BLACKLIST = new HashSet();
    private final Set<String> CALC_WHITE_DEVICE_LIST = new HashSet();
    private final Set<String> CALC_WHITE_GLOBAL_DEVICE_LIST = new HashSet();

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logger.INSTANCE.i(TAG, "AiTextWidgetBlockProvider onCreate");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(null), 3, null);
        return true;
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.provider.AiTextWidgetBlockProvider$onCreate$1, reason: invalid class name */
    /* JADX INFO: compiled from: AiTextWidgetBlockProvider.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.provider.AiTextWidgetBlockProvider$onCreate$1", f = "AiTextWidgetBlockProvider.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AiTextWidgetBlockProvider.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                AiTextWidgetBlockProvider.this.loadBlackListFromResources();
                AiTextWidgetBlockProvider.this.loadCalcWhiteListFromResources();
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadBlackListFromResources() {
        Context context = getContext();
        Resources resources = context != null ? context.getResources() : null;
        String[] stringArray = resources != null ? resources.getStringArray(R.array.ai_widget_device_blacklist) : null;
        String[] stringArray2 = resources != null ? resources.getStringArray(R.array.ai_widget_global_device_blacklist) : null;
        if (stringArray != null) {
            for (String str : stringArray) {
                Set<String> set = this.DEVICE_BLACKLIST;
                Intrinsics.checkNotNull(str);
                set.add(StringsKt.trim((CharSequence) str).toString());
            }
        }
        if (stringArray2 != null) {
            for (String str2 : stringArray2) {
                Set<String> set2 = this.GLOBAL_DEVICE_BLACKLIST;
                Intrinsics.checkNotNull(str2);
                set2.add(StringsKt.trim((CharSequence) str2).toString());
            }
        }
        Logger.INSTANCE.d(TAG, "loadBlackListFromResources,CN,black list:" + this.DEVICE_BLACKLIST + ",GLO:" + this.GLOBAL_DEVICE_BLACKLIST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadCalcWhiteListFromResources() {
        Context context = getContext();
        Resources resources = context != null ? context.getResources() : null;
        String[] stringArray = resources != null ? resources.getStringArray(R.array.calc_hand_write_white_device_list) : null;
        if (stringArray != null) {
            for (String str : stringArray) {
                Set<String> set = this.CALC_WHITE_DEVICE_LIST;
                Intrinsics.checkNotNull(str);
                set.add(StringsKt.trim((CharSequence) str).toString());
            }
        }
        String[] stringArray2 = resources != null ? resources.getStringArray(R.array.calc_hand_write_white_global_device_list) : null;
        if (stringArray2 != null) {
            for (String str2 : stringArray2) {
                Set<String> set2 = this.CALC_WHITE_GLOBAL_DEVICE_LIST;
                Intrinsics.checkNotNull(str2);
                set2.add(StringsKt.trim((CharSequence) str2).toString());
            }
        }
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        Intrinsics.checkNotNullParameter(method, "method");
        return super.call(method, arg, extras);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0088  */
    @Override // android.content.ContentProvider
    public Bundle call(String authority, String method, String arg, Bundle extras) {
        Intrinsics.checkNotNullParameter(authority, "authority");
        Intrinsics.checkNotNullParameter(method, "method");
        Logger.INSTANCE.d(TAG, "call method is " + method);
        boolean z = true;
        if (Intrinsics.areEqual(METHOD_IS_AI_WIDGET_ALLOWED, method)) {
            boolean z2 = !this.DEVICE_BLACKLIST.contains(Build.DEVICE);
            Bundle bundle = new Bundle();
            bundle.putBoolean(RESULT_IS_ALLOWED, z2);
            Logger.INSTANCE.d(TAG, "AiTextWidgetBlockProvider call method result is " + bundle);
            return bundle;
        }
        boolean zContains = false;
        if (Intrinsics.areEqual(METHOD_IS_AI_TEXT_WIDGET_ALLOWED, method)) {
            String str = Build.DEVICE;
            if (!RomUtils.isAboveHyperOS2Rom()) {
                z = false;
            } else if (RomUtils.isInternationalBuild()) {
                if (RomUtils.isP16UP()) {
                    Logger.INSTANCE.d(TAG, "international P16UP does not support AI Text.");
                } else if (this.GLOBAL_DEVICE_BLACKLIST.contains(str)) {
                }
                z = false;
            } else if (this.DEVICE_BLACKLIST.contains(str)) {
                z = false;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean(method, z);
            Logger.INSTANCE.i(TAG, "allowAiEnhancedDisplay call method result is " + z);
            return bundle2;
        }
        if (Intrinsics.areEqual(METHOD_IS_CALC_HAND_WHITE_SUPPORT, method)) {
            String str2 = Build.DEVICE;
            Bundle bundle3 = new Bundle();
            if (RomUtils.isAboveHyperOS2Rom() && !RomUtils.isRussiaRegion()) {
                zContains = (RomUtils.isInternationalBuild() ? this.CALC_WHITE_GLOBAL_DEVICE_LIST : this.CALC_WHITE_DEVICE_LIST).contains(str2);
            }
            bundle3.putBoolean(method, zContains);
            Logger.INSTANCE.d(TAG, "METHOD_IS_CALC_HAND_WHITE_SUPPORT: " + this.CALC_WHITE_DEVICE_LIST.contains(str2));
            return bundle3;
        }
        return super.call(authority, method, arg, extras);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException("Not implemented");
    }

    /* JADX INFO: compiled from: AiTextWidgetBlockProvider.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/ai/provider/AiTextWidgetBlockProvider$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AUTHORITY", "CONTENT_URI", "Landroid/net/Uri;", "getCONTENT_URI", "()Landroid/net/Uri;", "METHOD_IS_AI_WIDGET_ALLOWED", "RESULT_IS_ALLOWED", "METHOD_IS_AI_TEXT_WIDGET_ALLOWED", "METHOD_IS_CALC_HAND_WHITE_SUPPORT", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Uri getCONTENT_URI() {
            return AiTextWidgetBlockProvider.CONTENT_URI;
        }
    }
}
