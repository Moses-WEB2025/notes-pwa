package com.miui.notes.ai.utils;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextExt.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"setTextProperty", "", "Landroid/widget/TextView;", "value", "", "setEditProperty", "Landroid/widget/EditText;", "libAiTextWidget_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class TextExtKt {
    public static final void setTextProperty(TextView textView, String value) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Typeface typefaceBuild = new Typeface.Builder(Constant.INSTANCE.getFONT_MIPRO_PATH()).setFontVariationSettings("'wght' " + value).build();
        Intrinsics.checkNotNullExpressionValue(typefaceBuild, "build(...)");
        textView.setTypeface(typefaceBuild);
    }

    public static final void setEditProperty(EditText editText, String value) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Typeface typefaceBuild = new Typeface.Builder(Constant.INSTANCE.getFONT_MIPRO_PATH()).setFontVariationSettings("'wght' " + value).build();
        Intrinsics.checkNotNullExpressionValue(typefaceBuild, "build(...)");
        editText.setTypeface(typefaceBuild);
    }
}
