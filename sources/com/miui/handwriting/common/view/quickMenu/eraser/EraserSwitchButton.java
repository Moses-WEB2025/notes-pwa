package com.miui.handwriting.common.view.quickMenu.eraser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.quickMenu.utils.PenMenuRes;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: EraserSwitchButton.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0014J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\rH\u0002J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0006\u0010\u0017\u001a\u00020\rJ\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0007J\u0006\u0010\u001a\u001a\u00020\u0007J\b\u0010\u001b\u001a\u00020\rH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserSwitchButton;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "isChecked", "", "menuRes", "Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "mBackgroundPaint", "Landroid/graphics/Paint;", "onMeasure", "", "widthMeasureSpec", "", "heightMeasureSpec", "onDraw", "canvas", "Landroid/graphics/Canvas;", "initPaint", "drawBackground", "drawEraserIcon", "toggleChecked", "setIsChecked", "checked", "getIsChecked", "initAccessibilityDelegate", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraserSwitchButton extends View {
    private boolean isChecked;
    private Paint mBackgroundPaint;
    private final PenMenuRes menuRes;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EraserSwitchButton(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.menuRes = new PenMenuRes(context);
        initPaint();
        initAccessibilityDelegate();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int uniformCircleDiameter = (int) this.menuRes.getUniformCircleDiameter();
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(uniformCircleDiameter, 1073741824), View.MeasureSpec.makeMeasureSpec(uniformCircleDiameter, 1073741824));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        drawBackground(canvas);
        drawEraserIcon(canvas);
    }

    private final void initPaint() {
        Paint paint = new Paint(1);
        this.mBackgroundPaint = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    private final void drawBackground(Canvas canvas) {
        Paint paint = null;
        if (this.isChecked) {
            Paint paint2 = this.mBackgroundPaint;
            if (paint2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBackgroundPaint");
                paint2 = null;
            }
            paint2.setColor(this.menuRes.getEraserSwitchButtonSelectedColor());
        } else {
            Paint paint3 = this.mBackgroundPaint;
            if (paint3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBackgroundPaint");
                paint3 = null;
            }
            paint3.setColor(this.menuRes.getEraserSwitchButtonUnselectedColor());
        }
        float width = getWidth() / 2.0f;
        Paint paint4 = this.mBackgroundPaint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBackgroundPaint");
        } else {
            paint = paint4;
        }
        canvas.drawCircle(width, width, width, paint);
    }

    private final void drawEraserIcon(Canvas canvas) {
        Drawable eraserIcon = this.menuRes.getEraserIcon(this.isChecked);
        if (eraserIcon == null) {
            return;
        }
        float eraserIconSize = this.menuRes.getEraserIconSize();
        float f = eraserIconSize / 2.0f;
        float width = (getWidth() / 2.0f) - f;
        float height = (getHeight() / 2.0f) - f;
        eraserIcon.setBounds(new Rect(MathKt.roundToInt(width), MathKt.roundToInt(height), MathKt.roundToInt(width + eraserIconSize), MathKt.roundToInt(height + eraserIconSize)));
        eraserIcon.draw(canvas);
    }

    public final void toggleChecked() {
        this.isChecked = !this.isChecked;
        invalidate();
    }

    public final void setIsChecked(boolean checked) {
        this.isChecked = checked;
        invalidate();
    }

    public final boolean getIsChecked() {
        return this.isChecked;
    }

    private final void initAccessibilityDelegate() {
        setContentDescription(getContext().getResources().getString(R.string.creation_eraser_stroke));
        setImportantForAccessibility(1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.miui.handwriting.common.view.quickMenu.eraser.EraserSwitchButton.initAccessibilityDelegate.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                Log.d(EraserMenuViewKt.TAG, "onInitializeAccessibilityNodeInfo: " + EraserSwitchButton.this.isChecked);
                info.setSelected(EraserSwitchButton.this.isChecked);
                info.setClickable(true);
                info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                if (EraserSwitchButton.this.isChecked) {
                    return;
                }
                info.setStateDescription(EraserSwitchButton.this.getContext().getResources().getString(miuix.miuixbasewidget.R.string.accessibility_tab_state_description_unselect));
            }
        });
    }
}
