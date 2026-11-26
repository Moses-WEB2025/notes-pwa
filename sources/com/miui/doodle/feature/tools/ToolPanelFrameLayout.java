package com.miui.doodle.feature.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ToolPanelFrameLayout.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/doodle/feature/tools/ToolPanelFrameLayout;", "Landroid/widget/FrameLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "mShaderPaint", "Landroid/graphics/Paint;", "mViewWidth", "", "mViewHeight", "init", "", "dispatchDraw", "canvas", "Landroid/graphics/Canvas;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ToolPanelFrameLayout extends FrameLayout {
    private final Paint mShaderPaint;
    private int mViewHeight;
    private int mViewWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ToolPanelFrameLayout(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mShaderPaint = new Paint();
        init(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ToolPanelFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mShaderPaint = new Paint();
        init(context);
    }

    private final void init(Context context) {
        this.mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mShaderPaint.setAntiAlias(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.mViewWidth != getWidth() || this.mViewHeight != getHeight()) {
            this.mViewWidth = getWidth();
            this.mViewHeight = getHeight();
            this.mShaderPaint.setShader(new LinearGradient(0.0f, 0.0f, 30.0f, 0.0f, 0, -16777216, Shader.TileMode.CLAMP));
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, null);
        super.dispatchDraw(canvas);
        canvas.drawRect(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, this.mShaderPaint);
        canvas.restoreToCount(iSaveLayer);
    }
}
