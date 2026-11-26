package com.miui.handwriting.common.latex;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import ch.qos.logback.core.CoreConstants;
import com.agog.mathdisplay.MTMathView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LatexPopupWindow.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003J.\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/handwriting/common/latex/LatexPopupWindow;", "Landroid/widget/PopupWindow;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mathView", "Lcom/agog/mathdisplay/MTMathView;", "anchorView", "Landroid/view/View;", "setAnchorView", "", "view", "initView", "updateMathViewColors", "isDarkMode", "", "showAtLocation", "gravity", "", "centerPointTop", "Landroid/graphics/Point;", "centerPointBottom", "limitArea", "Landroid/graphics/Rect;", "latex", "", "setLatexString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LatexPopupWindow extends PopupWindow {
    private View anchorView;
    private final Context context;
    private MTMathView mathView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LatexPopupWindow(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        initView();
    }

    public final void setAnchorView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.anchorView = view;
    }

    public final void initView() {
        MTMathView mTMathView = null;
        setContentView(View.inflate(this.context, R.layout.popup_math_latex, null));
        setBackgroundDrawable(new ColorDrawable(0));
        View viewFindViewById = getContentView().findViewById(R.id.mathview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        MTMathView mTMathView2 = (MTMathView) viewFindViewById;
        this.mathView = mTMathView2;
        if (mTMathView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
        } else {
            mTMathView = mTMathView2;
        }
        mTMathView.setFontSize(MTMathView.INSTANCE.convertDpToPixel(18.0f));
        updateMathViewColors();
    }

    public final void updateMathViewColors() {
        MTMathView mTMathView = null;
        if (isDarkMode(this.context)) {
            MTMathView mTMathView2 = this.mathView;
            if (mTMathView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mathView");
                mTMathView2 = null;
            }
            mTMathView2.setTextColor(this.context.getColor(R.color.white));
            MTMathView mTMathView3 = this.mathView;
            if (mTMathView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mathView");
            } else {
                mTMathView = mTMathView3;
            }
            mTMathView.setBackgroundColor(this.context.getColor(R.color.Late_bg_black_transparent));
            return;
        }
        MTMathView mTMathView4 = this.mathView;
        if (mTMathView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
            mTMathView4 = null;
        }
        mTMathView4.setTextColor(this.context.getColor(R.color.black));
        MTMathView mTMathView5 = this.mathView;
        if (mTMathView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
        } else {
            mTMathView = mTMathView5;
        }
        mTMathView.setBackgroundColor(this.context.getColor(R.color.Late_bg_white_transparent));
    }

    public final boolean isDarkMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final void showAtLocation(int gravity, Point centerPointTop, Point centerPointBottom, Rect limitArea, String latex) {
        int i;
        Intrinsics.checkNotNullParameter(centerPointTop, "centerPointTop");
        Intrinsics.checkNotNullParameter(centerPointBottom, "centerPointBottom");
        Intrinsics.checkNotNullParameter(limitArea, "limitArea");
        Intrinsics.checkNotNullParameter(latex, "latex");
        if (this.anchorView == null) {
            return;
        }
        updateMathViewColors();
        setLatexString(latex);
        MTMathView mTMathView = this.mathView;
        MTMathView mTMathView2 = null;
        if (mTMathView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
            mTMathView = null;
        }
        mTMathView.measure(0, 0);
        MTMathView mTMathView3 = this.mathView;
        if (mTMathView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
            mTMathView3 = null;
        }
        int measuredWidth = mTMathView3.getMeasuredWidth();
        MTMathView mTMathView4 = this.mathView;
        if (mTMathView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
        } else {
            mTMathView2 = mTMathView4;
        }
        int measuredHeight = mTMathView2.getMeasuredHeight();
        int i2 = centerPointTop.x - (measuredWidth / 2);
        int i3 = centerPointTop.y;
        int i4 = limitArea.left;
        int i5 = limitArea.top;
        int i6 = limitArea.right;
        int i7 = limitArea.bottom;
        if (i2 < i4) {
            i2 = i4;
        } else if (i2 + measuredWidth > i6) {
            i2 = i6 - measuredWidth;
        }
        int i8 = i3 - measuredHeight;
        if (i8 < i5) {
            i = centerPointBottom.y + measuredHeight > i7 ? i7 - measuredHeight : centerPointBottom.y;
        } else {
            i = i8 - 20;
        }
        super.showAtLocation(this.anchorView, gravity, i2, i);
    }

    public final void setLatexString(String latex) {
        Intrinsics.checkNotNullParameter(latex, "latex");
        MTMathView mTMathView = this.mathView;
        if (mTMathView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mathView");
            mTMathView = null;
        }
        mTMathView.setLatex(latex);
    }
}
