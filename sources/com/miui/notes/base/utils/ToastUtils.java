package com.miui.notes.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ToastUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u000e\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/base/utils/ToastUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "toastMsg", "", "msg", "", "ctx", "Landroid/content/Context;", "showShortText", CoreConstants.CONTEXT_SCOPE_VALUE, "text", "", "showLongText", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ToastUtils {
    public static final ToastUtils INSTANCE = new ToastUtils();

    private ToastUtils() {
    }

    public final void toastMsg(final String msg, final Context ctx) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.notes.base.utils.ToastUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ToastUtils.toastMsg$lambda$0(ctx, msg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toastMsg$lambda$0(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public final void showShortText(Context context, CharSequence text) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(text, "text");
        Toast.makeText(context, text, 1).show();
    }

    public final void showShortText(final CharSequence text, final Context ctx) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.notes.base.utils.ToastUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ToastUtils.showShortText$lambda$1(ctx, text);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showShortText$lambda$1(Context context, CharSequence charSequence) {
        Toast.makeText(context, charSequence, 0).show();
    }

    public final void showLongText(final CharSequence text, final Context ctx) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.notes.base.utils.ToastUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ToastUtils.showLongText$lambda$2(ctx, text);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLongText$lambda$2(Context context, CharSequence charSequence) {
        Toast.makeText(context, charSequence, 1).show();
    }
}
