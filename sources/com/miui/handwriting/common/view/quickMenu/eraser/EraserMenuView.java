package com.miui.handwriting.common.view.quickMenu.eraser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.quickMenu.FloatAngleExtendKt;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.accessibility.AccessibilityUtils;
import com.miui.handwriting.common.view.quickMenu.accessibility.EraserQuickMenuAccessibilityHelper;
import com.miui.handwriting.common.view.quickMenu.accessibility.SeekBarAccessibilityInfo;
import com.miui.handwriting.common.view.quickMenu.accessibility.onAccessibilitySeekBarProgressUpdateListener;
import com.miui.handwriting.common.view.quickMenu.base.ArcArea;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuView;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import com.miui.handwriting.common.view.quickMenu.base.Sector;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickEraserUpdateListener;
import com.miui.handwriting.common.view.quickMenu.utils.AngleUtils;
import com.miui.handwriting.common.view.quickMenu.utils.PolarUtils;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: compiled from: EraserMenuView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u001eH\u0014J\u000e\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020\u001aJ\u0016\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u000fJ\u0010\u0010-\u001a\u00020%2\u0006\u0010.\u001a\u00020/H\u0014J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u00020%H\u0002J\b\u00103\u001a\u00020%H\u0002J\b\u00104\u001a\u00020%H\u0002J\u0010\u00105\u001a\u00020\u001c2\u0006\u00106\u001a\u000207H\u0016J\b\u00108\u001a\u00020\u001cH\u0016J\u0010\u00109\u001a\u00020%2\u0006\u00106\u001a\u000207H\u0002J\u0010\u0010:\u001a\u00020;2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u0010<\u001a\u00020%2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u0010=\u001a\u00020%2\u0006\u0010.\u001a\u00020/H\u0002J\b\u0010>\u001a\u00020%H\u0016J\u0006\u0010?\u001a\u00020\u000fJ\u0016\u0010@\u001a\u00020\u001c2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000fJ\u0010\u0010C\u001a\u00020%2\u0006\u0010D\u001a\u00020;H\u0002J\u0010\u0010E\u001a\u00020%2\u0006\u0010F\u001a\u00020\u000fH\u0002J\b\u0010G\u001a\u00020%H\u0002J\u0010\u0010H\u001a\u00020\u001c2\u0006\u00106\u001a\u000207H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuView;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuView;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "mSizeSeekbarPaint", "Landroid/graphics/Paint;", "mSizeSliderPaint", "mSizeSeekbarStrokePaint", "mEraserIconBackgroundPaint", "mEraserTextPaint", "mTextHeight", "", "mEraserSwitchButtonIconSize", "mEraserSwitchButtonIconMaxSize", "mEraserSwitchButtonIconMinSize", "mSizeSliderAngle", "mSizeSliderRectF", "Landroid/graphics/RectF;", "mEraserType", "Lxc/engine/operate/canvas/track/delete/DeleteType;", "mEraserSize", "mEraserUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickEraserUpdateListener;", "mIsTouchSlider", "", "mEraserButtonIconColor", "", "mEraserSeekBarArea", "Lcom/miui/handwriting/common/view/quickMenu/base/ArcArea;", "mEraserQuickMenuAccessHelper", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/EraserQuickMenuAccessibilityHelper;", "isInitAccessibilityHelper", "onMeasure", "", "widthMeasureSpec", "heightMeasureSpec", "setEraserUpdateListener", "listener", "setEraser", "type", "size", "onDraw", "canvas", "Landroid/graphics/Canvas;", "getWrapperSector", "Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "initSeekBarArea", "initPaint", "updatePaintColor", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "performClick", "handleSizeSeekbarEvent", "drawSizeSeekBar", "Landroid/graphics/Rect;", "drawSlider", "drawEraser", "notifyMenuOpened", "getEraserSize", "isTouchInEraserSeekBarAccessibility", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "initAccessibilityHelper", "eraserSeekbarRectBounds", "updateAccessibilitySeekbarCurrentValue", "curEraserSize", "setContentDescription", "dispatchHoverEvent", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraserMenuView extends BaseMenuView {
    private boolean isInitAccessibilityHelper;
    private int mEraserButtonIconColor;
    private Paint mEraserIconBackgroundPaint;
    private EraserQuickMenuAccessibilityHelper mEraserQuickMenuAccessHelper;
    private ArcArea mEraserSeekBarArea;
    private float mEraserSize;
    private float mEraserSwitchButtonIconMaxSize;
    private float mEraserSwitchButtonIconMinSize;
    private float mEraserSwitchButtonIconSize;
    private Paint mEraserTextPaint;
    private DeleteType mEraserType;
    private OnQuickEraserUpdateListener mEraserUpdateListener;
    private boolean mIsTouchSlider;
    private Paint mSizeSeekbarPaint;
    private Paint mSizeSeekbarStrokePaint;
    private float mSizeSliderAngle;
    private Paint mSizeSliderPaint;
    private final RectF mSizeSliderRectF;
    private float mTextHeight;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EraserMenuView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public void notifyMenuOpened() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EraserMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mSizeSliderAngle = AngleUtils.INSTANCE.adjustAngle(getMenuRes().getEraserMenuSizeSeekbarStartAngle());
        this.mSizeSliderRectF = new RectF();
        this.mEraserType = DeleteType.POINT;
        this.mEraserButtonIconColor = -16777216;
        initPaint();
        updatePaintColor();
        float eraserIconSize = getMenuRes().getEraserIconSize();
        this.mEraserSwitchButtonIconSize = eraserIconSize;
        this.mEraserSwitchButtonIconMinSize = eraserIconSize;
        this.mEraserSwitchButtonIconMaxSize = eraserIconSize * 1.1f;
        setMMenuStartAngle(FloatAngleExtendKt.minusAngle(getMenuRes().getEraserMenuStartAngle(), getMArcExtensionAngle()));
        setMMenuEndAngle(FloatAngleExtendKt.addAngle(getMenuRes().getEraserMenuEndAngle(), getMArcExtensionAngle()));
        setContentDescription(context.getString(R.string.creation_tool_eraser));
    }

    public /* synthetic */ EraserMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824));
    }

    public final void setEraserUpdateListener(OnQuickEraserUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mEraserUpdateListener = listener;
    }

    public final void setEraser(DeleteType type, float size) {
        int pencilSizeSeekbarBackgroundColor;
        Intrinsics.checkNotNullParameter(type, "type");
        this.mEraserSize = size;
        this.mEraserType = type;
        Paint paint = this.mEraserIconBackgroundPaint;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserIconBackgroundPaint");
            paint = null;
        }
        if (type == DeleteType.LINE) {
            pencilSizeSeekbarBackgroundColor = getMenuRes().getEraserSwitchButtonSelectedColor();
        } else {
            pencilSizeSeekbarBackgroundColor = getMenuRes().getPencilSizeSeekbarBackgroundColor();
        }
        paint.setColor(pencilSizeSeekbarBackgroundColor);
        this.mEraserButtonIconColor = type == DeleteType.LINE ? -1 : -16777216;
        this.mSizeSliderAngle = PolarUtils.INSTANCE.valueToAngle((int) size, 4, 32, getMenuRes().getEraserMenuSizeSeekbarStartAngle(), getMenuRes().getEraserMenuSizeSeekbarEndAngle());
        invalidate();
        updateAccessibilitySeekbarCurrentValue(size);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        Rect rectDrawSizeSeekBar = drawSizeSeekBar(canvas);
        drawEraser(canvas);
        drawSlider(canvas);
        initAccessibilityHelper(rectDrawSizeSeekBar);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public Sector getWrapperSector() {
        float outerCircleRadius = getMenuRes().getOuterCircleRadius() - getMenuRes().getInnerCircleRadius();
        Sector sector$default = QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getEraserMenuStartAngle(), getMenuRes().getEraserMenuEndAngle(), null, 8, null);
        getMWrapperOuterOvalRect().set(sector$default.getOuterOvalRect());
        getMWrapperInnerOvalRect().set(sector$default.getInnerOvalRect());
        getMSliderTrackOvalRect().set(sector$default.getOuterOvalRect());
        float f = outerCircleRadius / 2;
        getMSliderTrackOvalRect().inset(f, f);
        initSeekBarArea();
        return sector$default;
    }

    private final void initSeekBarArea() {
        float outerCircleRadius = getMenuRes().getOuterCircleRadius();
        float innerCircleRadius = getMenuRes().getInnerCircleRadius();
        float menuGapSize = getMenuRes().getMenuGapSize();
        float f = innerCircleRadius + menuGapSize;
        float f2 = outerCircleRadius - menuGapSize;
        float f3 = (f2 - f) / 2;
        float fComputeExtensionAngle = AngleUtils.INSTANCE.computeExtensionAngle(f3, f + f3);
        this.mEraserSeekBarArea = new ArcArea(f, f2, FloatAngleExtendKt.minusAngle(getMenuRes().getEraserMenuSizeSeekbarStartAngle(), fComputeExtensionAngle), FloatAngleExtendKt.addAngle(getMenuRes().getEraserMenuSizeSeekbarEndAngle(), fComputeExtensionAngle), new PointF(getMWrapperOuterOvalRect().centerX(), getMWrapperOuterOvalRect().centerY()));
    }

    private final void initPaint() {
        Paint paint = new Paint(1);
        this.mSizeSeekbarPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mEraserIconBackgroundPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mSizeSliderPaint = new Paint(1);
        Paint paint3 = new Paint(1);
        this.mSizeSeekbarStrokePaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        Paint paint4 = this.mSizeSeekbarStrokePaint;
        Paint paint5 = null;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
            paint4 = null;
        }
        paint4.setStrokeWidth(getMenuRes().getSizeSeekbarInnerStrokeSize());
        Paint paint6 = new Paint(1);
        this.mEraserTextPaint = paint6;
        paint6.setTextSize(getMenuRes().getEraserDrawTextSize());
        Paint paint7 = this.mEraserTextPaint;
        if (paint7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserTextPaint");
            paint7 = null;
        }
        paint7.setTextAlign(Paint.Align.CENTER);
        Paint paint8 = this.mEraserTextPaint;
        if (paint8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserTextPaint");
        } else {
            paint5 = paint8;
        }
        Paint.FontMetrics fontMetrics = paint5.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics, "getFontMetrics(...)");
        this.mTextHeight = fontMetrics.descent + fontMetrics.ascent;
    }

    private final void updatePaintColor() {
        Paint paint = this.mSizeSeekbarPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarPaint");
            paint = null;
        }
        paint.setColor(getMenuRes().getPencilSizeSeekbarBackgroundColor());
        Paint paint3 = this.mEraserIconBackgroundPaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserIconBackgroundPaint");
            paint3 = null;
        }
        paint3.setColor(getMenuRes().getPencilSizeSeekbarBackgroundColor());
        Paint paint4 = this.mSizeSliderPaint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderPaint");
            paint4 = null;
        }
        paint4.setColor(-1);
        Paint paint5 = this.mSizeSeekbarStrokePaint;
        if (paint5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
            paint5 = null;
        }
        paint5.setColor(getMenuRes().getSizeSliderStrokeColor());
        Paint paint6 = this.mEraserTextPaint;
        if (paint6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserTextPaint");
        } else {
            paint2 = paint6;
        }
        paint2.setColor(-16777216);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            setTouchArcMenuState(x, y);
            boolean zContains = this.mSizeSliderRectF.contains(x, y);
            this.mIsTouchSlider = zContains;
            if (!zContains) {
                PolarUtils polarUtils = PolarUtils.INSTANCE;
                ArcArea arcArea = this.mEraserSeekBarArea;
                if (arcArea == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mEraserSeekBarArea");
                    arcArea = null;
                }
                if (polarUtils.isTouchInArc(x, y, arcArea)) {
                    this.mIsTouchSlider = true;
                    handleSizeSeekbarEvent(event);
                }
            }
        } else {
            if (action == 1) {
                this.mIsTouchSlider = false;
                setMIsTouchMenu(false);
                return performClick();
            }
            if (action == 2) {
                setTouchArcMenuState(x, y);
                if (this.mIsTouchSlider) {
                    handleSizeSeekbarEvent(event);
                }
            }
        }
        Log.d(EraserMenuViewKt.TAG, "onTouchEvent isTouchInArcMenu " + isTouchInArcMenu());
        return this.mIsTouchSlider || isTouchInArcMenu();
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    private final void handleSizeSeekbarEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float eraserMenuSizeSeekbarStartAngle = getMenuRes().getEraserMenuSizeSeekbarStartAngle();
        float eraserMenuSizeSeekbarEndAngle = getMenuRes().getEraserMenuSizeSeekbarEndAngle();
        int iAngleToValue = PolarUtils.INSTANCE.angleToValue(RangesKt.coerceIn(PolarUtils.INSTANCE.convertPlaneToPolar(new PointF(x, y), new PointF(getMWrapperOuterOvalRect().centerX(), getMWrapperOuterOvalRect().centerY())).getAngle(), AngleUtils.INSTANCE.adjustAngle(eraserMenuSizeSeekbarStartAngle), AngleUtils.INSTANCE.adjustAngle(eraserMenuSizeSeekbarEndAngle)), eraserMenuSizeSeekbarStartAngle, eraserMenuSizeSeekbarEndAngle, 4, 32);
        OnQuickEraserUpdateListener onQuickEraserUpdateListener = this.mEraserUpdateListener;
        if (onQuickEraserUpdateListener != null) {
            onQuickEraserUpdateListener.onUpdate(DeleteType.POINT, iAngleToValue);
        }
    }

    private final Rect drawSizeSeekBar(Canvas canvas) {
        float eraserMenuSizeSeekbarStartAngle = getMenuRes().getEraserMenuSizeSeekbarStartAngle();
        float eraserMenuSizeSeekbarEndAngle = getMenuRes().getEraserMenuSizeSeekbarEndAngle();
        Paint paint = this.mSizeSeekbarPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarPaint");
            paint = null;
        }
        Path pathDrawSeekBar = drawSeekBar(canvas, eraserMenuSizeSeekbarStartAngle, eraserMenuSizeSeekbarEndAngle, paint);
        Paint paint3 = this.mSizeSeekbarStrokePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
        } else {
            paint2 = paint3;
        }
        canvas.drawPath(pathDrawSeekBar, paint2);
        RectF rectF = new RectF();
        pathDrawSeekBar.computeBounds(rectF, true);
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return rect;
    }

    private final void drawSlider(Canvas canvas) {
        float sliderStrokeThin = getMenuRes().getSliderStrokeThin();
        int sliderRadius = getMenuRes().getSliderRadius();
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMSliderTrackOvalRect().width() / 2.0f, this.mSizeSliderAngle), getMSliderTrackOvalRect());
        Path path = new Path();
        float f = sliderRadius;
        path.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f - sliderStrokeThin, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f, Path.Direction.CW);
        path2.computeBounds(this.mSizeSliderRectF, true);
        Paint paint = this.mSizeSliderPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderPaint");
            paint = null;
        }
        canvas.drawPath(path, paint);
        Paint paint3 = this.mSizeSeekbarStrokePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
            paint3 = null;
        }
        canvas.drawPath(path2, paint3);
        String strValueOf = String.valueOf((int) this.mEraserSize);
        float f2 = pointFConvertPolarToPlane.x;
        float f3 = pointFConvertPolarToPlane.y - (this.mTextHeight / 2);
        Paint paint4 = this.mEraserTextPaint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserTextPaint");
        } else {
            paint2 = paint4;
        }
        canvas.drawText(strValueOf, f2, f3, paint2);
    }

    private final void drawEraser(Canvas canvas) {
        drawTool(getMenuRes().getEraserDrawable(), AngleUtils.INSTANCE.adjustAngle(getMenuRes().getEraserAngle()), canvas);
    }

    /* JADX INFO: renamed from: getEraserSize, reason: from getter */
    public final float getMEraserSize() {
        return this.mEraserSize;
    }

    public final boolean isTouchInEraserSeekBarAccessibility(float x, float y) {
        PolarUtils polarUtils = PolarUtils.INSTANCE;
        ArcArea arcArea = this.mEraserSeekBarArea;
        if (arcArea == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mEraserSeekBarArea");
            arcArea = null;
        }
        return polarUtils.isTouchInArc(x, y, arcArea);
    }

    private final void initAccessibilityHelper(Rect eraserSeekbarRectBounds) {
        if (this.isInitAccessibilityHelper) {
            return;
        }
        AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat = new AccessibilityNodeInfoCompat.RangeInfoCompat(0, 4.0f, 32.0f, this.mEraserSize);
        final EraserQuickMenuAccessibilityHelper eraserQuickMenuAccessibilityHelper = new EraserQuickMenuAccessibilityHelper(this, new SeekBarAccessibilityInfo(eraserSeekbarRectBounds, rangeInfoCompat, AccessibilityUtils.INSTANCE.getSeekBarStep(rangeInfoCompat), getMenuRes().getEraserSizeText(), false, 16, null));
        eraserQuickMenuAccessibilityHelper.setAccessibilityEraserSeekBarProgressUpdateListener(new onAccessibilitySeekBarProgressUpdateListener() { // from class: com.miui.handwriting.common.view.quickMenu.eraser.EraserMenuView$initAccessibilityHelper$1$1
            @Override // com.miui.handwriting.common.view.quickMenu.accessibility.onAccessibilitySeekBarProgressUpdateListener
            public void update(int step, int virtualViewId) {
                float fCoerceIn = RangesKt.coerceIn(this.this$0.mEraserSize + step, 4.0f, 32.0f);
                OnQuickEraserUpdateListener onQuickEraserUpdateListener = this.this$0.mEraserUpdateListener;
                if (onQuickEraserUpdateListener != null) {
                    onQuickEraserUpdateListener.onUpdate(this.this$0.mEraserType, fCoerceIn);
                }
                this.this$0.updateAccessibilitySeekbarCurrentValue(fCoerceIn);
                this.this$0.setAccessibilityPaneTitle(String.valueOf((int) fCoerceIn));
                this.this$0.setContentDescription("");
                eraserQuickMenuAccessibilityHelper.sendAccessibilityEvent(this, 16384);
                this.this$0.setContentDescription();
            }
        });
        this.mEraserQuickMenuAccessHelper = eraserQuickMenuAccessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, eraserQuickMenuAccessibilityHelper);
        this.isInitAccessibilityHelper = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateAccessibilitySeekbarCurrentValue(float curEraserSize) {
        EraserQuickMenuAccessibilityHelper eraserQuickMenuAccessibilityHelper = this.mEraserQuickMenuAccessHelper;
        if (eraserQuickMenuAccessibilityHelper != null) {
            SeekBarAccessibilityInfo mEraserSeekBarAccessibilityInfo = eraserQuickMenuAccessibilityHelper.getMEraserSeekBarAccessibilityInfo();
            AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo = mEraserSeekBarAccessibilityInfo.getRangeInfo();
            if (curEraserSize > 0.0f) {
                AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat = new AccessibilityNodeInfoCompat.RangeInfoCompat(rangeInfo.getType(), 4.0f, 32.0f, curEraserSize);
                eraserQuickMenuAccessibilityHelper.setMEraserSeekBarAccessibilityInfo(new SeekBarAccessibilityInfo(mEraserSeekBarAccessibilityInfo.getBoundsInParent(), rangeInfoCompat, AccessibilityUtils.INSTANCE.getSeekBarStep(rangeInfoCompat), getMenuRes().getPenThicknessText(), true));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setContentDescription() {
        setAccessibilityPaneTitle("");
        setContentDescription(getContext().getResources().getString(R.string.creation_tool_eraser));
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EraserQuickMenuAccessibilityHelper eraserQuickMenuAccessibilityHelper = this.mEraserQuickMenuAccessHelper;
        if (eraserQuickMenuAccessibilityHelper == null || eraserQuickMenuAccessibilityHelper == null || !eraserQuickMenuAccessibilityHelper.dispatchHoverEvent(event)) {
            return super.dispatchHoverEvent(event);
        }
        return true;
    }
}
