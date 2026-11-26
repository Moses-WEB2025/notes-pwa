package com.miui.handwriting.common.view.quickMenu.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.utils.AngleUtils;
import com.miui.handwriting.common.view.quickMenu.utils.PenMenuRes;
import com.miui.handwriting.common.view.quickMenu.utils.PolarUtils;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseMenuView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0014J\b\u00106\u001a\u000203H\u0002J\b\u00107\u001a\u000203H\u0002J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0004J\b\u0010<\u001a\u00020;H&J\b\u0010=\u001a\u000203H&J\u0010\u0010>\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002J(\u0010?\u001a\u0002092\u0006\u00104\u001a\u0002052\u0006\u0010@\u001a\u00020#2\u0006\u0010A\u001a\u00020#2\u0006\u0010B\u001a\u00020\u000fH\u0004J \u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020#2\u0006\u0010H\u001a\u00020#H\u0004J \u0010I\u001a\u0002032\u0006\u0010J\u001a\u00020F2\u0006\u0010H\u001a\u00020#2\u0006\u00104\u001a\u000205H\u0004J\u0006\u0010K\u001a\u00020\u001dJ\u0018\u0010L\u001a\u0002032\u0006\u0010M\u001a\u00020#2\u0006\u0010N\u001a\u00020#H\u0004R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u001a\u0010\u001c\u001a\u00020\u001dX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020#X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010%R\u001a\u0010(\u001a\u00020#X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010%\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020#X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010%\"\u0004\b.\u0010+R\u001a\u0010/\u001a\u00020#X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010%\"\u0004\b1\u0010+¨\u0006O"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuView;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "getAnchor", "()Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", "menuRes", "Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "getMenuRes", "()Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "mPaint", "Landroid/graphics/Paint;", "getMPaint", "()Landroid/graphics/Paint;", "setMPaint", "(Landroid/graphics/Paint;)V", "mWrapperOuterOvalRect", "Landroid/graphics/RectF;", "getMWrapperOuterOvalRect", "()Landroid/graphics/RectF;", "mWrapperInnerOvalRect", "getMWrapperInnerOvalRect", "mSliderTrackOvalRect", "getMSliderTrackOvalRect", "mIsTouchMenu", "", "getMIsTouchMenu", "()Z", "setMIsTouchMenu", "(Z)V", "mMenuInnerCircleRadius", "", "getMMenuInnerCircleRadius", "()F", "mMenuCutDiagramOuterCircleRadius", "getMMenuCutDiagramOuterCircleRadius", "mMenuStartAngle", "getMMenuStartAngle", "setMMenuStartAngle", "(F)V", "mMenuEndAngle", "getMMenuEndAngle", "setMMenuEndAngle", "mArcExtensionAngle", "getMArcExtensionAngle", "setMArcExtensionAngle", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "initPaint", "updatePaintColor", "getSectorPath", "Landroid/graphics/Path;", "sector", "Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "getWrapperSector", "notifyMenuOpened", "drawWrapperArc", "drawSeekBar", "startAngle", "endAngle", "paint", "setDrawableBounds", "Landroid/graphics/PointF;", "element", "Landroid/graphics/drawable/Drawable;", Layer.KEY_HEIGHT, "angle", "drawTool", "tool", "isTouchInArcMenu", "setTouchArcMenuState", "touchX", "touchY", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseMenuView extends View {
    private final StylusPenQuickMenu anchor;
    private float mArcExtensionAngle;
    private boolean mIsTouchMenu;
    private final float mMenuCutDiagramOuterCircleRadius;
    private float mMenuEndAngle;
    private final float mMenuInnerCircleRadius;
    private float mMenuStartAngle;
    protected Paint mPaint;
    private final RectF mSliderTrackOvalRect;
    private final RectF mWrapperInnerOvalRect;
    private final RectF mWrapperOuterOvalRect;
    private final PenMenuRes menuRes;

    public abstract Sector getWrapperSector();

    public abstract void notifyMenuOpened();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.anchor = stylusPenQuickMenu;
        PenMenuRes penMenuRes = new PenMenuRes(context);
        this.menuRes = penMenuRes;
        this.mWrapperOuterOvalRect = new RectF();
        this.mWrapperInnerOvalRect = new RectF();
        this.mSliderTrackOvalRect = new RectF();
        this.mMenuInnerCircleRadius = penMenuRes.getInnerCircleRadius();
        this.mMenuCutDiagramOuterCircleRadius = penMenuRes.getBrushOuterCircleRadius();
        initPaint();
        updatePaintColor();
        float outerCircleRadius = penMenuRes.getOuterCircleRadius();
        float innerCircleRadius = penMenuRes.getInnerCircleRadius();
        float f = (outerCircleRadius - innerCircleRadius) / 2;
        this.mArcExtensionAngle = AngleUtils.INSTANCE.computeExtensionAngle(f, innerCircleRadius + f);
    }

    protected final StylusPenQuickMenu getAnchor() {
        return this.anchor;
    }

    protected final PenMenuRes getMenuRes() {
        return this.menuRes;
    }

    protected final Paint getMPaint() {
        Paint paint = this.mPaint;
        if (paint != null) {
            return paint;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mPaint");
        return null;
    }

    protected final void setMPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.mPaint = paint;
    }

    protected final RectF getMWrapperOuterOvalRect() {
        return this.mWrapperOuterOvalRect;
    }

    protected final RectF getMWrapperInnerOvalRect() {
        return this.mWrapperInnerOvalRect;
    }

    protected final RectF getMSliderTrackOvalRect() {
        return this.mSliderTrackOvalRect;
    }

    protected final boolean getMIsTouchMenu() {
        return this.mIsTouchMenu;
    }

    protected final void setMIsTouchMenu(boolean z) {
        this.mIsTouchMenu = z;
    }

    protected final float getMMenuInnerCircleRadius() {
        return this.mMenuInnerCircleRadius;
    }

    protected final float getMMenuCutDiagramOuterCircleRadius() {
        return this.mMenuCutDiagramOuterCircleRadius;
    }

    protected final float getMMenuStartAngle() {
        return this.mMenuStartAngle;
    }

    protected final void setMMenuStartAngle(float f) {
        this.mMenuStartAngle = f;
    }

    protected final float getMMenuEndAngle() {
        return this.mMenuEndAngle;
    }

    protected final void setMMenuEndAngle(float f) {
        this.mMenuEndAngle = f;
    }

    protected final float getMArcExtensionAngle() {
        return this.mArcExtensionAngle;
    }

    protected final void setMArcExtensionAngle(float f) {
        this.mArcExtensionAngle = f;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        drawWrapperArc(canvas);
    }

    private final void initPaint() {
        setMPaint(new Paint(1));
    }

    private final void updatePaintColor() {
        getMPaint().setColor(this.menuRes.getMenuBackgroundColor());
    }

    protected final Path getSectorPath(Sector sector) {
        Intrinsics.checkNotNullParameter(sector, "sector");
        float startAngle = sector.getStartAngle();
        float endAngle = sector.getEndAngle();
        RectF outerOvalRect = sector.getOuterOvalRect();
        RectF innerOvalRect = sector.getInnerOvalRect();
        float outerCircleRadius = sector.getOuterCircleRadius();
        float innerCircleRadius = sector.getInnerCircleRadius();
        float fWidth = (outerOvalRect.width() / 2.0f) + outerOvalRect.left;
        float fHeight = (outerOvalRect.height() / 2.0f) + outerOvalRect.top;
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(startAngle);
        float fAdjustAngle2 = AngleUtils.INSTANCE.adjustAngle(endAngle);
        float f = fAdjustAngle2 - fAdjustAngle;
        Path path = new Path();
        path.moveTo(fWidth, fHeight);
        path.addArc(outerOvalRect, fAdjustAngle, f);
        path.arcTo(innerOvalRect, fAdjustAngle2, (-1) * f);
        path.close();
        Path path2 = new Path();
        float f2 = (outerCircleRadius - innerCircleRadius) / 2;
        float f3 = innerCircleRadius + f2;
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(f3, fAdjustAngle), outerOvalRect);
        path2.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f2, Path.Direction.CW);
        Path path3 = new Path(path2);
        PointF pointFConvertPolarToPlane2 = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(f3, fAdjustAngle2), outerOvalRect);
        path3.addCircle(pointFConvertPolarToPlane2.x, pointFConvertPolarToPlane2.y, f2, Path.Direction.CW);
        path.op(path2, Path.Op.UNION);
        path.op(path3, Path.Op.UNION);
        return path;
    }

    private final void drawWrapperArc(Canvas canvas) {
        canvas.drawPath(getSectorPath(getWrapperSector()), getMPaint());
    }

    protected final Path drawSeekBar(Canvas canvas, float startAngle, float endAngle, Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        RectF rectF = new RectF(this.mWrapperOuterOvalRect);
        rectF.inset(this.menuRes.getMenuGapSize(), this.menuRes.getMenuGapSize());
        RectF rectF2 = new RectF(this.mWrapperInnerOvalRect);
        float f = -1;
        rectF2.inset(this.menuRes.getMenuGapSize() * f, this.menuRes.getMenuGapSize() * f);
        Path sectorPath = getSectorPath(new Sector(startAngle, endAngle, rectF, rectF2, rectF.width() / 2.0f, rectF2.width() / 2.0f));
        canvas.drawPath(sectorPath, paint);
        return sectorPath;
    }

    protected final PointF setDrawableBounds(Drawable element, float height, float angle) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.getMinimumHeight() == 0) {
            return new PointF(0.0f, 0.0f);
        }
        float minimumWidth = (element.getMinimumWidth() / element.getMinimumHeight()) * height;
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(this.mSliderTrackOvalRect.width() / 2.0f, angle), this.mSliderTrackOvalRect);
        float f = 2;
        float f2 = minimumWidth / f;
        float f3 = height / f;
        RectF rectF = new RectF(pointFConvertPolarToPlane.x - f2, pointFConvertPolarToPlane.y - f3, pointFConvertPolarToPlane.x + f2, pointFConvertPolarToPlane.y + f3);
        Rect rect = new Rect();
        rectF.roundOut(rect);
        element.setBounds(rect);
        return pointFConvertPolarToPlane;
    }

    protected final void drawTool(Drawable tool, float angle, Canvas canvas) {
        Intrinsics.checkNotNullParameter(tool, "tool");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        PointF drawableBounds = setDrawableBounds(tool, this.menuRes.getBrushOuterCircleRadius() - this.menuRes.getBrushInnerCircleRadius(), angle);
        canvas.save();
        Path path = new Path();
        path.addRect(this.mWrapperOuterOvalRect, Path.Direction.CW);
        Path path2 = new Path();
        float f = 2;
        path2.addCircle(this.mWrapperOuterOvalRect.width() / f, this.mWrapperOuterOvalRect.height() / f, this.mWrapperInnerOvalRect.width() / f, Path.Direction.CW);
        path.op(path2, Path.Op.DIFFERENCE);
        canvas.clipPath(path);
        canvas.rotate((angle - R2.array.ai_tag_top_item_gradient_border_colors) - 90, drawableBounds.x, drawableBounds.y);
        tool.draw(canvas);
        canvas.restore();
    }

    public final boolean isTouchInArcMenu() {
        return this.mIsTouchMenu;
    }

    protected final void setTouchArcMenuState(float touchX, float touchY) {
        this.mIsTouchMenu = PolarUtils.INSTANCE.isTouchInArc(touchX, touchY, this.mMenuInnerCircleRadius, this.mMenuCutDiagramOuterCircleRadius, this.mMenuStartAngle, this.mMenuEndAngle, new PointF(this.mWrapperOuterOvalRect.centerX(), this.mWrapperOuterOvalRect.centerY()));
    }
}
