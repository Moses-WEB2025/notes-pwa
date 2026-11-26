package com.miui.doodle.component;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import ch.qos.logback.core.CoreConstants;
import com.airbnb.lottie.LottieAnimationView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import miuix.appcompat.app.AlertDialog;
import miuix.core.widget.NestedScrollView;

/* JADX INFO: compiled from: AppGuideDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/miui/doodle/component/AppGuideDialog;", "Lmiuix/appcompat/app/AlertDialog;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "themeResId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;I)V", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AppGuideDialog extends AlertDialog {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppGuideDialog(Context context, int i) {
        String str;
        super(context, i);
        Intrinsics.checkNotNull(context);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.layout_user_guide_dialog_view, (ViewGroup) null);
        setTitle(R.string.doodle_guide_dialog_title);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) viewInflate.findViewById(R.id.guide_content);
        AppCompatTextView appCompatTextView = (AppCompatTextView) viewInflate.findViewById(R.id.tv_guide_des);
        Resources resources = context.getResources();
        String string = resources != null ? resources.getString(R.string.doodle_guide_quick_shape_hint) : null;
        if (string != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            str = String.format(string, Arrays.copyOf(new Object[]{1, 2}, 2));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        } else {
            str = "";
        }
        appCompatTextView.setText(str);
        lottieAnimationView.setRepeatCount(-1);
        lottieAnimationView.setAnimation("doodle_graffiti.json");
        NestedScrollView nestedScrollView = new NestedScrollView(context);
        nestedScrollView.addView(viewInflate);
        setView(nestedScrollView);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setButton(-1, context.getString(R.string.doodle_guide_dialog_confirm), (DialogInterface.OnClickListener) null);
        setButton(-2, context.getString(R.string.doodle_guide_dialog_skip), (DialogInterface.OnClickListener) null);
    }
}
