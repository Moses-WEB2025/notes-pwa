package com.miui.handwriting.common.mask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.creation.common.R;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RoundedImageView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J\u0006\u0010\u0018\u001a\u00020\rJ\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\rJ(\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007H\u0014J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/miui/handwriting/common/mask/RoundedImageView;", "Landroidx/appcompat/widget/AppCompatImageView;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mRoundCornerRect", "Landroid/graphics/RectF;", "mCornerRadius", "", "getMCornerRadius", "()F", "setMCornerRadius", "(F)V", "path", "Landroid/graphics/Path;", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "getmCornerRadius", "setmCornerRadius", "value", "onSizeChanged", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "onInitializeAccessibilityNodeInfo", Constants.JSON_FILTER_INFO, "Landroid/view/accessibility/AccessibilityNodeInfo;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RoundedImageView extends AppCompatImageView {
    private float mCornerRadius;
    private final RectF mRoundCornerRect;
    private final Path path;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RoundedImageView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RoundedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ RoundedImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RoundedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mRoundCornerRect = new RectF();
        this.path = new Path();
        this.mCornerRadius = getResources().getDimension(R.dimen.ai_generate_picture_corner_radius);
    }

    public final float getMCornerRadius() {
        return this.mCornerRadius;
    }

    public final void setMCornerRadius(float f) {
        this.mCornerRadius = f;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.mRoundCornerRect.isEmpty()) {
            return;
        }
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }

    public final float getmCornerRadius() {
        return this.mCornerRadius;
    }

    public final void setmCornerRadius(float value) {
        this.mCornerRadius = value;
        this.path.reset();
        Path path = this.path;
        RectF rectF = this.mRoundCornerRect;
        float f = this.mCornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w == 0 || h == 0) {
            return;
        }
        this.path.reset();
        this.mRoundCornerRect.set(0.0f, 0.0f, w, h);
        Path path = this.path;
        RectF rectF = this.mRoundCornerRect;
        float f = this.mCornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TextView.class.getName());
    }
}
