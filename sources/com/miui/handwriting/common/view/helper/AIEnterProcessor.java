package com.miui.handwriting.common.view.helper;

import android.content.Context;
import android.content.SharedPreferences;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AIEnterProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/handwriting/common/view/helper/AIEnterProcessor;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "AI_FILE", "", AIEnterProcessor.IS_SHOW_GUIDE, "isShowGuide", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "showGuide", "", "sureGuide", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AIEnterProcessor {
    private static final String AI_FILE = "ai_file";
    public static final AIEnterProcessor INSTANCE = new AIEnterProcessor();
    private static final String IS_SHOW_GUIDE = "IS_SHOW_GUIDE";

    public final boolean isShowGuide(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }

    public final void showGuide(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private AIEnterProcessor() {
    }

    public final void sureGuide(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(AI_FILE, 0).edit();
        editorEdit.putBoolean(IS_SHOW_GUIDE, true);
        editorEdit.apply();
    }
}
