package com.miui.doodle.base;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.feature.draw.DoodlePen;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.UIUtils;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: DoodleRotatableItemBase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000 32\u00020\u0001:\u00013BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f¢\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0016J\u0018\u0010+\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0016H\u0004J\u0018\u0010-\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0016H\u0004J\u0018\u0010.\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0016H\u0004J\u0016\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\tJ\u0016\u00102\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\tR\u001a\u0010\r\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lcom/miui/doodle/base/DoodleRotatableItemBase;", "Lcom/miui/doodle/base/DoodleSelectableItemBase;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "id", "", "itemRotate", "", "itemScale", "location", "Landroid/graphics/PointF;", "pivotPoint", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Lcom/miui/doodle/feature/draw/DoodlePen;Ljava/lang/String;FFLandroid/graphics/PointF;Landroid/graphics/PointF;)V", "getPivotPoint", "()Landroid/graphics/PointF;", "setPivotPoint", "(Landroid/graphics/PointF;)V", FilePathConstant.TEMP, "rectTemp", "Landroid/graphics/Rect;", "getRectTemp", "()Landroid/graphics/Rect;", "setRectTemp", "(Landroid/graphics/Rect;)V", "isRotating", "", "()Z", "setRotating", "(Z)V", "isScaling", "setScaling", "paint", "Landroid/graphics/Paint;", "closeRect", "Landroid/graphics/RectF;", "scaleRect", "doDrawAtTheTop", "", "canvas", "Landroid/graphics/Canvas;", "drawBorder", "rect", "drawCloseButton", "drawScaleButton", "canRotate", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "shouldClose", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class DoodleRotatableItemBase extends DoodleSelectableItemBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int TOUCH_PADDING = 8;
    private final RectF closeRect;
    private boolean isRotating;
    private boolean isScaling;
    private final Paint paint;
    private PointF pivotPoint;
    private Rect rectTemp;
    private final RectF scaleRect;
    private final PointF temp;

    public /* synthetic */ DoodleRotatableItemBase(IDoodle iDoodle, DoodlePen doodlePen, String str, float f, float f2, PointF pointF, PointF pointF2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, doodlePen, (i & 4) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i & 8) != 0 ? 0.0f : f, (i & 16) != 0 ? 1.0f : f2, (i & 32) != 0 ? new PointF() : pointF, pointF2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleRotatableItemBase(IDoodle doodle, DoodlePen pen, String id, float f, float f2, PointF location, PointF pivotPoint) {
        super(doodle, pen, id, f, f2, location);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(pen, "pen");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.pivotPoint = pivotPoint;
        this.temp = new PointF();
        this.rectTemp = new Rect();
        this.paint = new Paint();
        this.closeRect = new RectF();
        this.scaleRect = new RectF();
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public PointF getPivotPoint() {
        return this.pivotPoint;
    }

    @Override // com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public void setPivotPoint(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.pivotPoint = pointF;
    }

    protected final Rect getRectTemp() {
        return this.rectTemp;
    }

    protected final void setRectTemp(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<set-?>");
        this.rectTemp = rect;
    }

    /* JADX INFO: renamed from: isRotating, reason: from getter */
    public final boolean getIsRotating() {
        return this.isRotating;
    }

    public final void setRotating(boolean z) {
        this.isRotating = z;
    }

    /* JADX INFO: renamed from: isScaling, reason: from getter */
    public final boolean getIsScaling() {
        return this.isScaling;
    }

    public final void setScaling(boolean z) {
        this.isScaling = z;
    }

    @Override // com.miui.doodle.base.DoodleSelectableItemBase
    public void doDrawAtTheTop(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getIsSelected()) {
            float f = 1;
            float doodleScale = f / getDoodle().getDoodleScale();
            float doodleScale2 = f / getDoodle().getDoodleScale();
            float f2 = getPivotPoint().x;
            float f3 = getPivotPoint().y;
            int iSave = canvas.save();
            canvas.scale(doodleScale, doodleScale2, f2, f3);
            try {
                resetBoundsScaled(this.rectTemp);
                DrawUtil.INSTANCE.scaleRect(this.rectTemp, getDoodle().getDoodleScale(), getPivotPoint().x, getPivotPoint().y);
                float unitSize = getDoodle().getUnitSize();
                int i = ((int) unitSize) * 20;
                this.rectTemp.left -= i;
                this.rectTemp.top -= i;
                this.rectTemp.right += i;
                this.rectTemp.bottom += i;
                drawBorder(canvas, this.rectTemp);
                drawCloseButton(canvas, this.rectTemp);
                drawScaleButton(canvas, this.rectTemp);
            } finally {
                canvas.restoreToCount(iSave);
            }
        }
    }

    protected final void drawBorder(Canvas canvas, Rect rect) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rect, "rect");
        float unitSize = getDoodle().getUnitSize();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(2 * unitSize);
        this.paint.setColor(1711276032);
        this.paint.setStrokeWidth(0.8f * unitSize);
        float f = 4 * unitSize;
        this.paint.setPathEffect(new DashPathEffect(new float[]{f, f}, 0.0f));
        canvas.drawRect(rect, this.paint);
    }

    protected final void drawCloseButton(Canvas canvas, Rect rect) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rect, "rect");
        canvas.save();
        canvas.translate(rect.left, rect.top);
        float unitSize = getDoodle().getUnitSize();
        float f = 15 * unitSize;
        float f2 = f / 2;
        float f3 = 8 * unitSize;
        this.closeRect.set((rect.left - f2) - f3, (rect.top - f2) - f3, rect.left + f2 + f3, rect.top + f2 + f3);
        this.paint.setColor(-1);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setShadowLayer(10.909091f * unitSize, 0.0f, 7.2727275f * unitSize, 335544320);
        canvas.drawCircle(0.0f, 0.0f, f, this.paint);
        this.paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        this.paint.setColor(16382457);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(1 * unitSize);
        this.paint.setPathEffect(null);
        canvas.drawCircle(0.0f, 0.0f, f, this.paint);
        this.paint.setColor(-16777216);
        this.paint.setStyle(Paint.Style.FILL);
        float f4 = 13.090909f * unitSize;
        float f5 = 1.8181819f * unitSize;
        float f6 = unitSize * 3.6363637f;
        float f7 = (-f4) / 2.0f;
        float f8 = (-f5) / 2.0f;
        RectF rectF = new RectF(f7, f8, f4 + f7, f5 + f8);
        canvas.rotate(45.0f);
        canvas.drawRoundRect(rectF, f6, f6, this.paint);
        canvas.rotate(90.0f);
        canvas.drawRoundRect(rectF, f6, f6, this.paint);
        canvas.restore();
    }

    protected final void drawScaleButton(Canvas canvas, Rect rect) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(rect, "rect");
        canvas.save();
        float unitSize = getDoodle().getUnitSize();
        float f = 15 * unitSize;
        float f2 = f / 2;
        float f3 = 8 * unitSize;
        canvas.translate(this.rectTemp.right, this.rectTemp.bottom);
        this.scaleRect.set((this.rectTemp.right - f2) - f3, (this.rectTemp.bottom - f2) - f3, this.rectTemp.right + f2 + f3, this.rectTemp.bottom + f2 + f3);
        this.paint.setShadowLayer(10.909091f * unitSize, 0.0f, 7.2727275f * unitSize, 335544320);
        this.paint.setColor(-1);
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0.0f, 0.0f, f, this.paint);
        this.paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        this.paint.setColor(16382457);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(1 * unitSize);
        this.paint.setPathEffect(null);
        canvas.drawCircle(0.0f, 0.0f, f, this.paint);
        this.paint.setColor(-16777216);
        this.paint.setStyle(Paint.Style.FILL);
        float f4 = 3.6363637f * unitSize;
        float f5 = (-4.818182f) * unitSize;
        float f6 = (-4.872727f) * unitSize;
        float f7 = (5.818182f * unitSize) + f5;
        float f8 = (1.8181819f * unitSize) + f6;
        RectF rectF = new RectF(f5, f6, f7, f8);
        canvas.drawRoundRect(rectF, f4, f4, this.paint);
        float f9 = 7.72f * unitSize;
        rectF.offset(3.7199998f * unitSize, f9);
        canvas.drawRoundRect(rectF, f4, f4, this.paint);
        RectF rectF2 = new RectF(f6, f5, f8, f7);
        canvas.drawRoundRect(rectF2, f4, f4, this.paint);
        rectF2.offset(f9, unitSize * 3.7163637f);
        canvas.drawRoundRect(rectF2, f4, f4, this.paint);
        canvas.save();
    }

    public final boolean canRotate(float x, float y) {
        PointF pointFRotatePoint = DrawUtil.INSTANCE.rotatePoint(this.temp, -getItemRotate(), x - getLocation().x, y - getLocation().y, getPivotPoint().x, getPivotPoint().y);
        return pointFRotatePoint.x >= this.scaleRect.left && pointFRotatePoint.x <= this.scaleRect.right && pointFRotatePoint.y >= this.scaleRect.top && pointFRotatePoint.y <= this.scaleRect.bottom;
    }

    public final boolean shouldClose(float x, float y) {
        PointF pointFRotatePoint = DrawUtil.INSTANCE.rotatePoint(this.temp, -getItemRotate(), x - getLocation().x, y - getLocation().y, getPivotPoint().x, getPivotPoint().y);
        float f = 5;
        return UIUtils.INSTANCE.detectIntersect(this.closeRect, new RectF(pointFRotatePoint.x - f, pointFRotatePoint.y - f, pointFRotatePoint.x + f, pointFRotatePoint.y + f));
    }

    /* JADX INFO: compiled from: DoodleRotatableItemBase.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JN\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/doodle/base/DoodleRotatableItemBase$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TOUCH_PADDING", "", "createBaseItem", "", "id", "", "hasAdded", "", "locationX", "", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/base/DoodleRotatableItemBase;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createBaseItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, DoodleRotatableItemBase item) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(item, "item");
            DoodleSelectableItemBase.INSTANCE.createBaseItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, item);
        }
    }
}
