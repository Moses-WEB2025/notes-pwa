package com.miui.handwriting.common.view.quickMenu.brush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.handwriting.common.view.quickMenu.FloatAngleExtendKt;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.accessibility.AccessibilityUtils;
import com.miui.handwriting.common.view.quickMenu.accessibility.BrushQuickMenuAccessibilityHelper;
import com.miui.handwriting.common.view.quickMenu.accessibility.SeekBarAccessibilityInfo;
import com.miui.handwriting.common.view.quickMenu.accessibility.onAccessibilitySeekBarProgressUpdateListener;
import com.miui.handwriting.common.view.quickMenu.base.ArcArea;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuView;
import com.miui.handwriting.common.view.quickMenu.base.PenSizeInfo;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import com.miui.handwriting.common.view.quickMenu.base.Sector;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenSizeColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.utils.AngleUtils;
import com.miui.handwriting.common.view.quickMenu.utils.PenMenuRes;
import com.miui.handwriting.common.view.quickMenu.utils.PolarUtils;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.maml.folme.AnimatedProperty;
import java.text.NumberFormat;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;

/* JADX INFO: compiled from: BrushMenuView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000202H\u0014J\u0010\u00104\u001a\u0002002\u0006\u00105\u001a\u000206H\u0014J\u000e\u00107\u001a\u0002002\u0006\u00108\u001a\u00020&J\b\u00109\u001a\u000200H\u0016J\u0010\u0010:\u001a\u0002002\u0006\u0010;\u001a\u00020\u001cH\u0002J\u000e\u0010<\u001a\u0002002\u0006\u0010=\u001a\u00020\u001cJ\u000e\u0010>\u001a\u0002002\u0006\u0010?\u001a\u00020\u001eJ\u000e\u0010@\u001a\u0002002\u0006\u0010?\u001a\u00020 J\b\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u000200H\u0002J\b\u0010D\u001a\u000200H\u0002J\b\u0010E\u001a\u000200H\u0002J\u0010\u0010F\u001a\u00020\u00192\u0006\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020\u0019H\u0016J\u0010\u0010J\u001a\u0002002\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010K\u001a\u0002002\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010L\u001a\u0002002\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010M\u001a\u00020N2\u0006\u00105\u001a\u000206H\u0002J\u0010\u0010O\u001a\u00020N2\u0006\u00105\u001a\u000206H\u0002J\u0018\u0010P\u001a\u0002002\u0006\u00105\u001a\u0002062\u0006\u0010Q\u001a\u00020\u0016H\u0002J\u0018\u0010R\u001a\u0002002\u0006\u00105\u001a\u0002062\u0006\u0010Q\u001a\u00020\u0016H\u0002J\u0010\u0010S\u001a\u0002002\u0006\u00105\u001a\u000206H\u0002J\u0016\u0010T\u001a\u00020\u00192\u0006\u0010U\u001a\u00020\u00162\u0006\u0010V\u001a\u00020\u0016J\u0016\u0010W\u001a\u00020\u00192\u0006\u0010U\u001a\u00020\u00162\u0006\u0010V\u001a\u00020\u0016J\u0018\u0010X\u001a\u0002002\u0006\u0010Y\u001a\u00020N2\u0006\u0010Z\u001a\u00020NH\u0002J\u0018\u0010[\u001a\u0002002\u0006\u0010\\\u001a\u0002022\u0006\u0010]\u001a\u000202H\u0002J\b\u0010^\u001a\u000200H\u0002J\u0010\u0010_\u001a\u00020\u00192\u0006\u0010G\u001a\u00020HH\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006`"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuView;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuView;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "mSeekBarPaint", "Landroid/graphics/Paint;", "mAlphaSliderPaint", "mAlphaSliderStrokePaint", "mAlphaSeekbarPaint", "mAlphaSeekbarStrokePaint", "mSizeSeekbarPaint", "mSizeSeekbarStrokePaint", "mSizeSliderPaint", "mSizeSliderStrokePaint", "mAlphaSliderRectF", "Landroid/graphics/RectF;", "mSizeSliderRectF", "mAlphaSliderAngle", "", "mSizeSliderAngle", "mIsTouchAlphaSlider", "", "mIsTouchSizeSlider", "penInfo", "Lxc/engine/data/CurveProp;", "mAlphaListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenColorUpdateListener;", "mPenSizeListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenSizeColorUpdateListener;", "mCurPenSizeInfo", "Lcom/miui/handwriting/common/view/quickMenu/base/PenSizeInfo;", "brushPreviewBitmap", "Landroid/graphics/Bitmap;", "mPageInkModel", "Lcom/miui/libhandwritingpage/model/PageInkModel;", "mBrushPreviewBitmapHeight", "mSizeSeekBarArea", "Lcom/miui/handwriting/common/view/quickMenu/base/ArcArea;", "mAlphaSeekBarArea", "mBrushQuickMenuAccessHelper", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/BrushQuickMenuAccessibilityHelper;", "isInitAccessibilityHelper", "isBrushPreviewDirty", "onMeasure", "", "widthMeasureSpec", "", "heightMeasureSpec", "onDraw", "canvas", "Landroid/graphics/Canvas;", "setPageInkModel", "model", "notifyMenuOpened", "generateBrushPreview", "curveProp", "setPenInfo", Constants.JSON_FILTER_INFO, "setAlphaListener", "listener", "setPenSizeListener", "getWrapperSector", "Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "initSeekBarArea", "initPaint", "updatePaintColor", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "performClick", "handleTouchSeekbarOnDown", "handleAlphaSeekbarEvent", "handleSizeSeekbarEvent", "drawSizeSeekBar", "Landroid/graphics/Rect;", "drawAlphaSeekBar", "drawAlphaSlider", "angle", "drawSizeSlider", "drawBrush", "isTouchInSizeSeekBarAccessibility", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "isTouchInAlphaSeekBarAccessibility", "initAccessibilityHelper", "sizeSeekbarRectBounds", "alphaSeekbarRectBounds", "updateAccessibilitySeekbarCurrentValue", "curPenSize", "curAlpha", "setContentDescription", "dispatchHoverEvent", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BrushMenuView extends BaseMenuView {
    private Bitmap brushPreviewBitmap;
    private boolean isBrushPreviewDirty;
    private boolean isInitAccessibilityHelper;
    private OnQuickPenColorUpdateListener mAlphaListener;
    private ArcArea mAlphaSeekBarArea;
    private Paint mAlphaSeekbarPaint;
    private Paint mAlphaSeekbarStrokePaint;
    private float mAlphaSliderAngle;
    private Paint mAlphaSliderPaint;
    private final RectF mAlphaSliderRectF;
    private Paint mAlphaSliderStrokePaint;
    private float mBrushPreviewBitmapHeight;
    private BrushQuickMenuAccessibilityHelper mBrushQuickMenuAccessHelper;
    private PenSizeInfo mCurPenSizeInfo;
    private boolean mIsTouchAlphaSlider;
    private boolean mIsTouchSizeSlider;
    private PageInkModel mPageInkModel;
    private OnQuickPenSizeColorUpdateListener mPenSizeListener;
    private Paint mSeekBarPaint;
    private ArcArea mSizeSeekBarArea;
    private Paint mSizeSeekbarPaint;
    private Paint mSizeSeekbarStrokePaint;
    private float mSizeSliderAngle;
    private Paint mSizeSliderPaint;
    private final RectF mSizeSliderRectF;
    private Paint mSizeSliderStrokePaint;
    private CurveProp penInfo;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BrushMenuView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrushMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mAlphaSliderRectF = new RectF();
        this.mSizeSliderRectF = new RectF();
        this.mCurPenSizeInfo = getMenuRes().getPenSizeInfoByType(PenType.INK);
        this.isBrushPreviewDirty = true;
        setImportantForAccessibility(1);
        initPaint();
        updatePaintColor();
        setMMenuStartAngle(FloatAngleExtendKt.minusAngle(getMenuRes().getPencilMenuStartAngle(), getMArcExtensionAngle()));
        setMMenuEndAngle(FloatAngleExtendKt.addAngle(getMenuRes().getPencilMenuEndAngle(), getMArcExtensionAngle()));
    }

    public /* synthetic */ BrushMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824));
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.isBrushPreviewDirty) {
            CurveProp curveProp = this.penInfo;
            if (curveProp != null) {
                generateBrushPreview(curveProp);
            }
            this.isBrushPreviewDirty = false;
        }
        super.onDraw(canvas);
        Rect rectDrawSizeSeekBar = drawSizeSeekBar(canvas);
        Rect rectDrawAlphaSeekBar = drawAlphaSeekBar(canvas);
        drawBrush(canvas);
        initAccessibilityHelper(rectDrawSizeSeekBar, rectDrawAlphaSeekBar);
    }

    public final void setPageInkModel(PageInkModel model) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.mPageInkModel = model;
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public void notifyMenuOpened() {
        CurveProp curveProp = this.penInfo;
        if (curveProp != null) {
            generateBrushPreview(curveProp);
            invalidate();
        }
    }

    private final void generateBrushPreview(CurveProp curveProp) {
        Float fValueOf;
        float pencilMenuSizeSeekbarStartAngle = getMenuRes().getPencilMenuSizeSeekbarStartAngle();
        float pencilMenuSizeSeekbarEndAngle = getMenuRes().getPencilMenuSizeSeekbarEndAngle();
        float brushPreviewBitmapWidth = getMenuRes().getBrushPreviewBitmapWidth();
        float brushPreviewBitmapMinHeight = getMenuRes().getBrushPreviewBitmapMinHeight();
        float brushPreviewBitmapMaxHeight = getMenuRes().getBrushPreviewBitmapMaxHeight();
        QuickUtils quickUtils = QuickUtils.INSTANCE;
        float f = this.mSizeSliderAngle;
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(pencilMenuSizeSeekbarStartAngle);
        float fAdjustAngle2 = AngleUtils.INSTANCE.adjustAngle(pencilMenuSizeSeekbarEndAngle);
        Float fValueOf2 = Float.valueOf(brushPreviewBitmapMinHeight);
        double d = f;
        double d2 = fAdjustAngle;
        double d3 = fAdjustAngle2;
        double dDoubleValue = fValueOf2.doubleValue();
        double d4 = brushPreviewBitmapMaxHeight;
        if (d2 >= d3) {
            throw new IllegalArgumentException("fromMin must be less than fromMax".toString());
        }
        if (dDoubleValue > d4) {
            throw new IllegalArgumentException("toMin must be less than or equal to toMax".toString());
        }
        double d5 = dDoubleValue + (((d - d2) / (d3 - d2)) * (d4 - dDoubleValue));
        if (fValueOf2 instanceof Integer) {
            fValueOf = (Float) Integer.valueOf((int) d5);
        } else {
            fValueOf = fValueOf2 instanceof Long ? (Float) Long.valueOf((long) d5) : Float.valueOf((float) d5);
        }
        this.mBrushPreviewBitmapHeight = fValueOf.floatValue();
        CurveProp curveProp2 = new CurveProp(curveProp);
        curveProp2.setPenAlpha(255);
        if (curveProp.getPenType() != PenType.PENCIL) {
            curveProp2.setPenSize(this.mBrushPreviewBitmapHeight);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) brushPreviewBitmapWidth, (int) this.mBrushPreviewBitmapHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        this.brushPreviewBitmap = bitmapCreateBitmap;
        PageInkModel pageInkModel = this.mPageInkModel;
        if (pageInkModel != null) {
            if (bitmapCreateBitmap == null) {
                Intrinsics.throwUninitializedPropertyAccessException("brushPreviewBitmap");
                bitmapCreateBitmap = null;
            }
            pageInkModel.generateBrushPreview(curveProp2, bitmapCreateBitmap);
        }
    }

    public final void setPenInfo(CurveProp info) {
        PenType penType;
        Intrinsics.checkNotNullParameter(info, "info");
        this.isBrushPreviewDirty = true;
        this.penInfo = info;
        Pair<Integer, Integer> alphaRange = getMenuRes().getAlphaRange();
        PolarUtils polarUtils = PolarUtils.INSTANCE;
        CurveProp curveProp = this.penInfo;
        this.mAlphaSliderAngle = polarUtils.valueToAngle(curveProp != null ? curveProp.getPenAlpha() : 255, alphaRange.getFirst().intValue(), alphaRange.getSecond().intValue(), getMenuRes().getPencilMenuAlphaSeekbarStartAngle(), getMenuRes().getPencilMenuAlphaSeekbarEndAngle());
        PenMenuRes menuRes = getMenuRes();
        CurveProp curveProp2 = this.penInfo;
        if (curveProp2 == null || (penType = curveProp2.getPenType()) == null) {
            penType = PenType.INK;
        }
        PenSizeInfo penSizeInfoByType = menuRes.getPenSizeInfoByType(penType);
        this.mCurPenSizeInfo = penSizeInfoByType;
        CurveProp curveProp3 = this.penInfo;
        int penSize = curveProp3 != null ? (int) curveProp3.getPenSize() : penSizeInfoByType.getMinSize();
        this.mSizeSliderAngle = PolarUtils.INSTANCE.valueToAngle(penSize, this.mCurPenSizeInfo.getMinSize(), this.mCurPenSizeInfo.getMaxSize(), getMenuRes().getPencilMenuSizeSeekbarStartAngle(), getMenuRes().getPencilMenuSizeSeekbarEndAngle());
        invalidate();
        Log.d(BrushMenuViewKt.TAG, "setPenInfo generateBrushPreview");
        setContentDescription();
        CurveProp curveProp4 = this.penInfo;
        updateAccessibilitySeekbarCurrentValue(penSize, curveProp4 != null ? curveProp4.getPenAlpha() : -1);
    }

    public final void setAlphaListener(OnQuickPenColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mAlphaListener = listener;
    }

    public final void setPenSizeListener(OnQuickPenSizeColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mPenSizeListener = listener;
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public Sector getWrapperSector() {
        float outerCircleRadius = getMenuRes().getOuterCircleRadius() - getMenuRes().getInnerCircleRadius();
        Sector sector$default = QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getPencilMenuStartAngle(), getMenuRes().getPencilMenuEndAngle(), null, 8, null);
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
        float fMinusAngle = FloatAngleExtendKt.minusAngle(getMenuRes().getPencilMenuSizeSeekbarStartAngle(), fComputeExtensionAngle);
        float fAddAngle = FloatAngleExtendKt.addAngle(getMenuRes().getPencilMenuSizeSeekbarEndAngle(), fComputeExtensionAngle);
        float fMinusAngle2 = FloatAngleExtendKt.minusAngle(getMenuRes().getPencilMenuAlphaSeekbarStartAngle(), fComputeExtensionAngle);
        float fAddAngle2 = FloatAngleExtendKt.addAngle(getMenuRes().getPencilMenuAlphaSeekbarEndAngle(), fComputeExtensionAngle);
        PointF pointF = new PointF(getMWrapperOuterOvalRect().centerX(), getMWrapperOuterOvalRect().centerY());
        this.mSizeSeekBarArea = new ArcArea(f, f2, fMinusAngle, fAddAngle, pointF);
        this.mAlphaSeekBarArea = new ArcArea(f, f2, fMinusAngle2, fAddAngle2, pointF);
    }

    private final void initPaint() {
        this.mAlphaSeekbarPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.mAlphaSliderPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        Paint paint2 = this.mAlphaSliderPaint;
        Paint paint3 = null;
        if (paint2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderPaint");
            paint2 = null;
        }
        paint2.setStrokeWidth(getMenuRes().getSliderStrokeBold());
        Paint paint4 = new Paint(1);
        this.mAlphaSliderStrokePaint = paint4;
        paint4.setStyle(Paint.Style.STROKE);
        Paint paint5 = this.mAlphaSliderStrokePaint;
        if (paint5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderStrokePaint");
            paint5 = null;
        }
        paint5.setStrokeWidth(getMenuRes().getSliderStrokeMiddle());
        Paint paint6 = new Paint(1);
        this.mAlphaSeekbarStrokePaint = paint6;
        paint6.setStyle(Paint.Style.STROKE);
        Paint paint7 = this.mAlphaSeekbarStrokePaint;
        if (paint7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekbarStrokePaint");
            paint7 = null;
        }
        paint7.setStrokeWidth(getMenuRes().getAlphaSeekbarInnerStrokeSize());
        Paint paint8 = new Paint(1);
        this.mSizeSeekbarPaint = paint8;
        paint8.setStyle(Paint.Style.FILL);
        this.mSizeSliderPaint = new Paint(1);
        Paint paint9 = new Paint(1);
        this.mSizeSliderStrokePaint = paint9;
        paint9.setStyle(Paint.Style.STROKE);
        Paint paint10 = this.mSizeSliderStrokePaint;
        if (paint10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderStrokePaint");
            paint10 = null;
        }
        paint10.setStrokeWidth(getMenuRes().getSliderStrokeThin());
        Paint paint11 = new Paint(1);
        this.mSizeSeekbarStrokePaint = paint11;
        paint11.setStyle(Paint.Style.STROKE);
        Paint paint12 = this.mSizeSeekbarStrokePaint;
        if (paint12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
        } else {
            paint3 = paint12;
        }
        paint3.setStrokeWidth(getMenuRes().getSizeSeekbarInnerStrokeSize());
    }

    private final void updatePaintColor() {
        Paint paint = this.mAlphaSeekbarPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekbarPaint");
            paint = null;
        }
        paint.setColor(-1);
        Paint paint3 = this.mAlphaSliderPaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderPaint");
            paint3 = null;
        }
        paint3.setColor(-1);
        Paint paint4 = this.mAlphaSliderStrokePaint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderStrokePaint");
            paint4 = null;
        }
        paint4.setColor(getMenuRes().getAlphaSliderStrokeColor());
        Paint paint5 = this.mAlphaSeekbarStrokePaint;
        if (paint5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekbarStrokePaint");
            paint5 = null;
        }
        paint5.setColor(getMenuRes().getAlphaSeekbarInnerStrokeColor());
        Paint paint6 = this.mSizeSliderPaint;
        if (paint6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderPaint");
            paint6 = null;
        }
        paint6.setColor(-1);
        Paint paint7 = this.mSizeSliderStrokePaint;
        if (paint7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderStrokePaint");
            paint7 = null;
        }
        paint7.setColor(getMenuRes().getSizeSliderStrokeColor());
        Paint paint8 = this.mSizeSeekbarPaint;
        if (paint8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarPaint");
            paint8 = null;
        }
        paint8.setColor(getMenuRes().getPencilSizeSeekbarBackgroundColor());
        Paint paint9 = this.mSizeSeekbarStrokePaint;
        if (paint9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
        } else {
            paint2 = paint9;
        }
        paint2.setColor(getMenuRes().getSizeSliderStrokeColor());
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            this.mIsTouchAlphaSlider = this.mAlphaSliderRectF.contains(x, y);
            this.mIsTouchSizeSlider = this.mSizeSliderRectF.contains(x, y);
            setTouchArcMenuState(x, y);
            handleTouchSeekbarOnDown(event);
        } else {
            if (action == 1) {
                this.mIsTouchAlphaSlider = false;
                this.mIsTouchSizeSlider = false;
                setMIsTouchMenu(false);
                return performClick();
            }
            if (action == 2) {
                setTouchArcMenuState(x, y);
                if (this.mIsTouchAlphaSlider) {
                    handleAlphaSeekbarEvent(event);
                }
                if (this.mIsTouchSizeSlider) {
                    handleSizeSeekbarEvent(event);
                }
            }
        }
        return this.mIsTouchAlphaSlider || this.mIsTouchSizeSlider || isTouchInArcMenu();
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    private final void handleTouchSeekbarOnDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (this.mIsTouchSizeSlider || this.mIsTouchAlphaSlider) {
            return;
        }
        PolarUtils polarUtils = PolarUtils.INSTANCE;
        ArcArea arcArea = this.mSizeSeekBarArea;
        ArcArea arcArea2 = null;
        if (arcArea == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekBarArea");
            arcArea = null;
        }
        if (polarUtils.isTouchInArc(x, y, arcArea)) {
            this.mIsTouchSizeSlider = true;
            handleSizeSeekbarEvent(event);
        }
        PolarUtils polarUtils2 = PolarUtils.INSTANCE;
        ArcArea arcArea3 = this.mAlphaSeekBarArea;
        if (arcArea3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekBarArea");
        } else {
            arcArea2 = arcArea3;
        }
        if (polarUtils2.isTouchInArc(x, y, arcArea2)) {
            this.mIsTouchAlphaSlider = true;
            handleAlphaSeekbarEvent(event);
        }
    }

    private final void handleAlphaSeekbarEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float pencilMenuAlphaSeekbarStartAngle = getMenuRes().getPencilMenuAlphaSeekbarStartAngle();
        float pencilMenuAlphaSeekbarEndAngle = getMenuRes().getPencilMenuAlphaSeekbarEndAngle();
        float fCoerceIn = RangesKt.coerceIn(PolarUtils.INSTANCE.convertPlaneToPolar(new PointF(x, y), new PointF(getMWrapperOuterOvalRect().centerX(), getMWrapperOuterOvalRect().centerY())).getAngle(), AngleUtils.INSTANCE.adjustAngle(pencilMenuAlphaSeekbarStartAngle), AngleUtils.INSTANCE.adjustAngle(pencilMenuAlphaSeekbarEndAngle));
        Pair<Integer, Integer> alphaRange = getMenuRes().getAlphaRange();
        int iAngleToValue = PolarUtils.INSTANCE.angleToValue(fCoerceIn, pencilMenuAlphaSeekbarStartAngle, pencilMenuAlphaSeekbarEndAngle, alphaRange.getFirst().intValue(), alphaRange.getSecond().intValue());
        CurveProp curveProp = this.penInfo;
        int penColor = curveProp != null ? curveProp.getPenColor() : -65536;
        int iArgb = Color.argb(iAngleToValue, Color.red(penColor), Color.green(penColor), Color.blue(penColor));
        OnQuickPenColorUpdateListener onQuickPenColorUpdateListener = this.mAlphaListener;
        if (onQuickPenColorUpdateListener != null) {
            onQuickPenColorUpdateListener.update(iArgb, iAngleToValue);
        }
    }

    private final void handleSizeSeekbarEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float pencilMenuSizeSeekbarStartAngle = getMenuRes().getPencilMenuSizeSeekbarStartAngle();
        float pencilMenuSizeSeekbarEndAngle = getMenuRes().getPencilMenuSizeSeekbarEndAngle();
        int iAngleToValue = PolarUtils.INSTANCE.angleToValue(RangesKt.coerceIn(PolarUtils.INSTANCE.convertPlaneToPolar(new PointF(x, y), new PointF(getMWrapperOuterOvalRect().centerX(), getMWrapperOuterOvalRect().centerY())).getAngle(), AngleUtils.INSTANCE.adjustAngle(pencilMenuSizeSeekbarStartAngle), AngleUtils.INSTANCE.adjustAngle(pencilMenuSizeSeekbarEndAngle)), pencilMenuSizeSeekbarStartAngle, pencilMenuSizeSeekbarEndAngle, this.mCurPenSizeInfo.getMinSize(), this.mCurPenSizeInfo.getMaxSize());
        OnQuickPenSizeColorUpdateListener onQuickPenSizeColorUpdateListener = this.mPenSizeListener;
        if (onQuickPenSizeColorUpdateListener != null) {
            onQuickPenSizeColorUpdateListener.update(iAngleToValue);
        }
    }

    private final Rect drawSizeSeekBar(Canvas canvas) {
        float pencilMenuSizeSeekbarStartAngle = getMenuRes().getPencilMenuSizeSeekbarStartAngle();
        float pencilMenuSizeSeekbarEndAngle = getMenuRes().getPencilMenuSizeSeekbarEndAngle();
        Paint paint = this.mSizeSeekbarPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarPaint");
            paint = null;
        }
        Path pathDrawSeekBar = drawSeekBar(canvas, pencilMenuSizeSeekbarStartAngle, pencilMenuSizeSeekbarEndAngle, paint);
        Paint paint3 = this.mSizeSeekbarStrokePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
        } else {
            paint2 = paint3;
        }
        canvas.drawPath(pathDrawSeekBar, paint2);
        drawSizeSlider(canvas, this.mSizeSliderAngle);
        RectF rectF = new RectF();
        pathDrawSeekBar.computeBounds(rectF, true);
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return rect;
    }

    private final Rect drawAlphaSeekBar(Canvas canvas) {
        float pencilMenuAlphaSeekbarStartAngle = getMenuRes().getPencilMenuAlphaSeekbarStartAngle();
        float pencilMenuAlphaSeekbarEndAngle = getMenuRes().getPencilMenuAlphaSeekbarEndAngle();
        int alphaSeekbarGradientStartColor = getMenuRes().getAlphaSeekbarGradientStartColor();
        CurveProp curveProp = this.penInfo;
        int penColor = (curveProp != null ? curveProp.getPenColor() : -65536) | (-16777216);
        Paint paint = this.mAlphaSeekbarPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekbarPaint");
            paint = null;
        }
        Path pathDrawSeekBar = drawSeekBar(canvas, pencilMenuAlphaSeekbarStartAngle, pencilMenuAlphaSeekbarEndAngle, paint);
        Paint paint3 = this.mAlphaSeekbarStrokePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekbarStrokePaint");
        } else {
            paint2 = paint3;
        }
        canvas.drawPath(pathDrawSeekBar, paint2);
        canvas.save();
        canvas.clipPath(pathDrawSeekBar);
        RectF rectF = new RectF();
        pathDrawSeekBar.computeBounds(rectF, true);
        Drawable alphaBackground = getMenuRes().getAlphaBackground();
        if (alphaBackground != null) {
            Rect rect = new Rect();
            rectF.roundOut(rect);
            alphaBackground.setBounds(rect);
        }
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{alphaSeekbarGradientStartColor, penColor});
        if (alphaBackground != null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{alphaBackground, gradientDrawable});
            Rect rect2 = new Rect();
            rectF.roundOut(rect2);
            layerDrawable.setBounds(rect2);
            layerDrawable.draw(canvas);
        } else {
            Rect rect3 = new Rect();
            rectF.roundOut(rect3);
            gradientDrawable.setBounds(rect3);
            gradientDrawable.draw(canvas);
        }
        drawAlphaSlider(canvas, this.mAlphaSliderAngle);
        canvas.restore();
        RectF rectF2 = new RectF();
        pathDrawSeekBar.computeBounds(rectF2, true);
        Rect rect4 = new Rect();
        rectF2.roundOut(rect4);
        return rect4;
    }

    private final void drawAlphaSlider(Canvas canvas, float angle) {
        int sliderRadius = getMenuRes().getSliderRadius();
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMSliderTrackOvalRect().width() / 2.0f, angle), getMSliderTrackOvalRect());
        Path path = new Path();
        path.moveTo(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y);
        float f = sliderRadius;
        path.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f - getMenuRes().getSliderStrokeMiddle(), Path.Direction.CW);
        Path path2 = new Path();
        path2.moveTo(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y);
        path2.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f, Path.Direction.CW);
        Paint paint = this.mAlphaSliderPaint;
        Paint paint2 = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderPaint");
            paint = null;
        }
        canvas.drawPath(path, paint);
        Paint paint3 = this.mAlphaSliderStrokePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSliderStrokePaint");
        } else {
            paint2 = paint3;
        }
        canvas.drawPath(path2, paint2);
        path2.computeBounds(this.mAlphaSliderRectF, true);
    }

    private final void drawSizeSlider(Canvas canvas, float angle) {
        float sliderStrokeThin = getMenuRes().getSliderStrokeThin();
        int sliderRadius = getMenuRes().getSliderRadius();
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMSliderTrackOvalRect().width() / 2.0f, angle), getMSliderTrackOvalRect());
        Path path = new Path();
        float f = sliderRadius;
        path.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f - sliderStrokeThin, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f, Path.Direction.CW);
        path2.computeBounds(this.mSizeSliderRectF, true);
        Paint paint = this.mSizeSliderPaint;
        Bitmap bitmap = null;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSliderPaint");
            paint = null;
        }
        canvas.drawPath(path, paint);
        Paint paint2 = this.mSizeSeekbarStrokePaint;
        if (paint2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekbarStrokePaint");
            paint2 = null;
        }
        canvas.drawPath(path2, paint2);
        float f2 = 2;
        float brushPreviewBitmapWidth = getMenuRes().getBrushPreviewBitmapWidth() / f2;
        float f3 = this.mBrushPreviewBitmapHeight / f2;
        RectF rectF = new RectF(pointFConvertPolarToPlane.x - brushPreviewBitmapWidth, pointFConvertPolarToPlane.y - f3, pointFConvertPolarToPlane.x + brushPreviewBitmapWidth, pointFConvertPolarToPlane.y + f3);
        canvas.save();
        canvas.rotate(30.0f, pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y);
        Bitmap bitmap2 = this.brushPreviewBitmap;
        if (bitmap2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPreviewBitmap");
            bitmap2 = null;
        }
        Bitmap bitmap3 = this.brushPreviewBitmap;
        if (bitmap3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPreviewBitmap");
            bitmap3 = null;
        }
        int width = bitmap3.getWidth();
        Bitmap bitmap4 = this.brushPreviewBitmap;
        if (bitmap4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("brushPreviewBitmap");
        } else {
            bitmap = bitmap4;
        }
        canvas.drawBitmap(bitmap2, new Rect(0, 0, width, bitmap.getHeight()), rectF, new Paint());
        canvas.restore();
    }

    private final void drawBrush(Canvas canvas) {
        PenType penType;
        CurveProp curveProp = this.penInfo;
        int penColor = curveProp != null ? curveProp.getPenColor() : -65536;
        PenMenuRes menuRes = getMenuRes();
        CurveProp curveProp2 = this.penInfo;
        if (curveProp2 == null || (penType = curveProp2.getPenType()) == null) {
            penType = PenType.INK;
        }
        drawTool(menuRes.getBrushDrawable(penType, penColor), AngleUtils.INSTANCE.adjustAngle(getMenuRes().getBrushAngle()), canvas);
    }

    public final boolean isTouchInSizeSeekBarAccessibility(float x, float y) {
        PolarUtils polarUtils = PolarUtils.INSTANCE;
        ArcArea arcArea = this.mSizeSeekBarArea;
        if (arcArea == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSizeSeekBarArea");
            arcArea = null;
        }
        return polarUtils.isTouchInArc(x, y, arcArea);
    }

    public final boolean isTouchInAlphaSeekBarAccessibility(float x, float y) {
        PolarUtils polarUtils = PolarUtils.INSTANCE;
        ArcArea arcArea = this.mAlphaSeekBarArea;
        if (arcArea == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAlphaSeekBarArea");
            arcArea = null;
        }
        return polarUtils.isTouchInArc(x, y, arcArea);
    }

    private final void initAccessibilityHelper(Rect sizeSeekbarRectBounds, Rect alphaSeekbarRectBounds) {
        if (this.isInitAccessibilityHelper) {
            return;
        }
        float minSize = this.mCurPenSizeInfo.getMinSize();
        float maxSize = this.mCurPenSizeInfo.getMaxSize();
        CurveProp curveProp = this.penInfo;
        AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat = new AccessibilityNodeInfoCompat.RangeInfoCompat(0, minSize, maxSize, curveProp != null ? curveProp.getPenSize() : this.mCurPenSizeInfo.getMinSize());
        SeekBarAccessibilityInfo seekBarAccessibilityInfo = new SeekBarAccessibilityInfo(sizeSeekbarRectBounds, rangeInfoCompat, AccessibilityUtils.INSTANCE.getSeekBarStep(rangeInfoCompat), getMenuRes().getPenThicknessText(), false, 16, null);
        final Pair<Integer, Integer> alphaRange = getMenuRes().getAlphaRange();
        float fIntValue = alphaRange.getFirst().intValue();
        float fIntValue2 = alphaRange.getSecond().intValue();
        CurveProp curveProp2 = this.penInfo;
        final BrushQuickMenuAccessibilityHelper brushQuickMenuAccessibilityHelper = new BrushQuickMenuAccessibilityHelper(this, seekBarAccessibilityInfo, new SeekBarAccessibilityInfo(alphaSeekbarRectBounds, new AccessibilityNodeInfoCompat.RangeInfoCompat(0, fIntValue, fIntValue2, curveProp2 != null ? curveProp2.getPenAlpha() : alphaRange.getFirst().intValue()), AccessibilityUtils.INSTANCE.getSeekBarStep(rangeInfoCompat), getMenuRes().getPenAlphaText(), false, 16, null));
        brushQuickMenuAccessibilityHelper.setAccessibilitySizeSeekBarProgressUpdateListener(new onAccessibilitySeekBarProgressUpdateListener() { // from class: com.miui.handwriting.common.view.quickMenu.brush.BrushMenuView$initAccessibilityHelper$1$1
            @Override // com.miui.handwriting.common.view.quickMenu.accessibility.onAccessibilitySeekBarProgressUpdateListener
            public void update(int step, int virtualViewId) {
                CurveProp curveProp3 = this.this$0.penInfo;
                int iCoerceIn = RangesKt.coerceIn((curveProp3 != null ? (int) curveProp3.getPenSize() : this.this$0.mCurPenSizeInfo.getMinSize()) + step, this.this$0.mCurPenSizeInfo.getMinSize(), this.this$0.mCurPenSizeInfo.getMaxSize());
                OnQuickPenSizeColorUpdateListener onQuickPenSizeColorUpdateListener = this.this$0.mPenSizeListener;
                if (onQuickPenSizeColorUpdateListener != null) {
                    onQuickPenSizeColorUpdateListener.update(iCoerceIn);
                }
                this.this$0.updateAccessibilitySeekbarCurrentValue(iCoerceIn, -1);
                this.this$0.setAccessibilityPaneTitle(String.valueOf(iCoerceIn));
                this.this$0.setContentDescription("");
                brushQuickMenuAccessibilityHelper.sendAccessibilityEvent(this, 16384);
                this.this$0.setContentDescription();
            }
        });
        brushQuickMenuAccessibilityHelper.setAccessibilityAlphaSeekBarProgressUpdateListener(new onAccessibilitySeekBarProgressUpdateListener() { // from class: com.miui.handwriting.common.view.quickMenu.brush.BrushMenuView$initAccessibilityHelper$1$2
            @Override // com.miui.handwriting.common.view.quickMenu.accessibility.onAccessibilitySeekBarProgressUpdateListener
            public void update(int step, int virtualViewId) {
                CurveProp curveProp3 = this.this$0.penInfo;
                int iCoerceIn = RangesKt.coerceIn((curveProp3 != null ? curveProp3.getPenAlpha() : this.this$0.mCurPenSizeInfo.getMinSize()) + step, alphaRange.getFirst().intValue(), alphaRange.getSecond().intValue());
                CurveProp curveProp4 = this.this$0.penInfo;
                if (curveProp4 != null) {
                    BrushMenuView brushMenuView = this.this$0;
                    int iArgb = Color.argb(iCoerceIn, Color.red(curveProp4.getPenColor()), Color.green(curveProp4.getPenColor()), Color.blue(curveProp4.getPenColor()));
                    OnQuickPenColorUpdateListener onQuickPenColorUpdateListener = brushMenuView.mAlphaListener;
                    if (onQuickPenColorUpdateListener != null) {
                        onQuickPenColorUpdateListener.update(iArgb, iCoerceIn);
                    }
                }
                this.this$0.updateAccessibilitySeekbarCurrentValue(-1, iCoerceIn);
                BrushMenuView brushMenuView2 = this.this$0;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("%s", Arrays.copyOf(new Object[]{NumberFormat.getPercentInstance().format(Float.valueOf(iCoerceIn / 255.0f))}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                brushMenuView2.setAccessibilityPaneTitle(str);
                this.this$0.setContentDescription("");
                brushQuickMenuAccessibilityHelper.sendAccessibilityEvent(this, 16384);
                this.this$0.setContentDescription();
            }
        });
        this.mBrushQuickMenuAccessHelper = brushQuickMenuAccessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, brushQuickMenuAccessibilityHelper);
        this.isInitAccessibilityHelper = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateAccessibilitySeekbarCurrentValue(int curPenSize, int curAlpha) {
        BrushQuickMenuAccessibilityHelper brushQuickMenuAccessibilityHelper = this.mBrushQuickMenuAccessHelper;
        if (brushQuickMenuAccessibilityHelper != null) {
            SeekBarAccessibilityInfo mSizeSeekBarAccessibilityInfo = brushQuickMenuAccessibilityHelper.getMSizeSeekBarAccessibilityInfo();
            AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo = mSizeSeekBarAccessibilityInfo.getRangeInfo();
            if (curPenSize > 0) {
                AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat = new AccessibilityNodeInfoCompat.RangeInfoCompat(rangeInfo.getType(), this.mCurPenSizeInfo.getMinSize(), this.mCurPenSizeInfo.getMaxSize(), curPenSize);
                brushQuickMenuAccessibilityHelper.setMSizeSeekBarAccessibilityInfo(new SeekBarAccessibilityInfo(mSizeSeekBarAccessibilityInfo.getBoundsInParent(), rangeInfoCompat, AccessibilityUtils.INSTANCE.getSeekBarStep(rangeInfoCompat), getMenuRes().getPenThicknessText(), true));
            }
            if (curAlpha > 0) {
                SeekBarAccessibilityInfo mAlphaSeekBarAccessibilityInfo = brushQuickMenuAccessibilityHelper.getMAlphaSeekBarAccessibilityInfo();
                AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo2 = mAlphaSeekBarAccessibilityInfo.getRangeInfo();
                brushQuickMenuAccessibilityHelper.setMAlphaSeekBarAccessibilityInfo(new SeekBarAccessibilityInfo(mAlphaSeekBarAccessibilityInfo.getBoundsInParent(), new AccessibilityNodeInfoCompat.RangeInfoCompat(rangeInfo2.getType(), rangeInfo2.getMin(), rangeInfo2.getMax(), curAlpha), 12, getMenuRes().getPenAlphaText(), true));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setContentDescription() {
        CurveProp curveProp = this.penInfo;
        if (curveProp != null) {
            setAccessibilityPaneTitle("");
            PenMenuRes menuRes = getMenuRes();
            PenType penType = curveProp.getPenType();
            Intrinsics.checkNotNullExpressionValue(penType, "getPenType(...)");
            setContentDescription(menuRes.getBrushMenuAccessibilityContentDescription(penType));
        }
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BrushQuickMenuAccessibilityHelper brushQuickMenuAccessibilityHelper = this.mBrushQuickMenuAccessHelper;
        if (brushQuickMenuAccessibilityHelper == null || brushQuickMenuAccessibilityHelper == null || !brushQuickMenuAccessibilityHelper.dispatchHoverEvent(event)) {
            return super.dispatchHoverEvent(event);
        }
        return true;
    }
}
