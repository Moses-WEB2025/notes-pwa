package com.miui.doodle.feature.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.doodle.utils.HapticFeedbackTool;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.aicr.vision.VisionAttribute;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/* JADX INFO: compiled from: ColorGroupPickerView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000 k2\u00020\u0001:\u0002jkB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u0010\u0010A\u001a\u00020B2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u0010C\u001a\u00020BH\u0002J\u0010\u0010D\u001a\u00020B2\b\u0010E\u001a\u0004\u0018\u00010@J\u0018\u0010F\u001a\u00020B2\b\u0010G\u001a\u0004\u0018\u0001032\u0006\u0010H\u001a\u00020\u0014J\u0010\u0010I\u001a\u00020B2\b\u0010G\u001a\u0004\u0018\u000103J\u000e\u0010J\u001a\u00020B2\u0006\u0010H\u001a\u00020\u0014J\u0010\u0010K\u001a\u00020B2\u0006\u0010L\u001a\u00020MH\u0002J\u0018\u0010N\u001a\u00020B2\u0006\u0010O\u001a\u00020 2\u0006\u0010P\u001a\u00020 H\u0002J\u0018\u0010Q\u001a\u00020B2\u0006\u0010O\u001a\u00020 2\u0006\u0010P\u001a\u00020 H\u0002J\b\u0010R\u001a\u00020BH\u0002J\u0018\u0010S\u001a\u00020\u00142\u0006\u0010O\u001a\u00020 2\u0006\u0010P\u001a\u00020 H\u0002J\u0012\u0010T\u001a\u0004\u0018\u0001002\u0006\u0010U\u001a\u00020\u0014H\u0002J\u0010\u0010V\u001a\u00020M2\u0006\u0010W\u001a\u00020XH\u0017J\u0010\u0010Y\u001a\u00020B2\u0006\u0010Z\u001a\u00020)H\u0014J\u0018\u0010[\u001a\u00020B2\u0006\u0010\\\u001a\u00020M2\u0006\u0010]\u001a\u00020 H\u0002J\u0010\u0010^\u001a\u00020B2\u0006\u0010_\u001a\u00020 H\u0002J\u0010\u0010`\u001a\u00020B2\u0006\u0010_\u001a\u00020 H\u0002J\u0010\u0010a\u001a\u00020B2\u0006\u0010_\u001a\u00020 H\u0002J\u0010\u0010b\u001a\u00020B2\u0006\u0010Z\u001a\u00020)H\u0002J\u0010\u0010c\u001a\u00020B2\u0006\u0010Z\u001a\u00020)H\u0002J\u0010\u0010d\u001a\u00020B2\u0006\u0010Z\u001a\u00020)H\u0002J\u0010\u0010e\u001a\u00020 2\u0006\u0010f\u001a\u00020 H\u0002J\u0010\u0010g\u001a\u00020B2\u0006\u0010h\u001a\u00020 H\u0002J\b\u0010i\u001a\u00020BH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\b\u0012\u0004\u0012\u00020005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020807X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006l"}, d2 = {"Lcom/miui/doodle/feature/color/ColorGroupPickerView;", "Landroid/widget/FrameLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "bgPaint", "Landroid/graphics/Paint;", "mBgXfermode", "Landroid/graphics/PorterDuffXfermode;", "mBgMaskFilter", "Landroid/graphics/MaskFilter;", "mSelectorPaint", "mBgPath", "Landroid/graphics/Path;", "mSelectorPath", "mSelectorHeight", "", "mSelectorWidth", "mRadius", "mSelectorRadius", "mColorItemWidth", "mViewWidth", "mViewHeight", "mSelectorOffsetX", "mBarOffsetY", "mBarHeight", "mBarRadius", "mLastSelectorX", "", "mSelectorX", "mSelectorY", "mDownX", "mDownY", "mLastUpX", "mLastUpY", "mScaledTouchSlop", "bgBmpCanvas", "Landroid/graphics/Canvas;", "bgBitmap", "Landroid/graphics/Bitmap;", "fgCanvas", "fgBmpPaint", "fgBitmap", "tmpRect", "Landroid/graphics/Rect;", "colorBarBmp", "mColorData", "", "mRects", "Ljava/util/ArrayList;", "mPathPoint", "", "Landroid/graphics/PointF;", "mTransXAnim", "Lmiuix/animation/IStateStyle;", "mTransXMoveAnimConfig", "Lmiuix/animation/base/AnimConfig;", "mTransXClickAnimConfig", "mSelectGroup", "mGroupPickListener", "Lcom/miui/doodle/feature/color/ColorGroupPickListener;", "initView", "", "initPoints", "setGroupPickListener", "listener", "setData", "colorData", "selectGroup", "setColorData", "setSelectGroup", "updateDrawParams", "forceUpdate", "", "checkSelectGroupOnClick", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "checkSelectGroupOnMove", "checkSelectGroupAfterMove", "getGroupIndexByTouchPos", "getRectByGroupIndex", "groupIndex", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "dispatchDraw", "canvas", "touchOrMoveSlider", "isTouch", "value", "moveSliderToPosition", "targetX", "smoothToPosition", "clickToPosition", "drawPanelShadowBg", "drawBlurFgBmp", "drawSelectorContent", "getPositionAfterTrans", "targetMoveX", "buildPath", "moveX", "createBlurColorBar", "TransitionListenerImpl", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorGroupPickerView extends FrameLayout {
    private static final float RC_C = 0.55191505f;
    private Bitmap bgBitmap;
    private final Canvas bgBmpCanvas;
    private Paint bgPaint;
    private Bitmap colorBarBmp;
    private Bitmap fgBitmap;
    private final Paint fgBmpPaint;
    private final Canvas fgCanvas;
    private int mBarHeight;
    private final int mBarOffsetY;
    private final int mBarRadius;
    private MaskFilter mBgMaskFilter;
    private Path mBgPath;
    private PorterDuffXfermode mBgXfermode;
    private int[] mColorData;
    private int mColorItemWidth;
    private float mDownX;
    private float mDownY;
    private ColorGroupPickListener mGroupPickListener;
    private float mLastSelectorX;
    private float mLastUpX;
    private float mLastUpY;
    private final List<PointF> mPathPoint;
    private int mRadius;
    private final ArrayList<Rect> mRects;
    private int mScaledTouchSlop;
    private int mSelectGroup;
    private int mSelectorHeight;
    private final int mSelectorOffsetX;
    private Paint mSelectorPaint;
    private Path mSelectorPath;
    private int mSelectorRadius;
    private int mSelectorWidth;
    private float mSelectorX;
    private float mSelectorY;
    private IStateStyle mTransXAnim;
    private AnimConfig mTransXClickAnimConfig;
    private AnimConfig mTransXMoveAnimConfig;
    private int mViewHeight;
    private int mViewWidth;
    private final Rect tmpRect;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorGroupPickerView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mRadius = 40;
        this.mSelectorRadius = 20;
        this.mSelectorOffsetX = 20;
        this.mBarOffsetY = 30;
        this.mBarHeight = 80;
        this.mBarRadius = 40;
        this.mLastSelectorX = 200.0f;
        this.bgBmpCanvas = new Canvas();
        this.fgCanvas = new Canvas();
        this.fgBmpPaint = new Paint();
        this.tmpRect = new Rect();
        this.mRects = new ArrayList<>();
        this.mPathPoint = new ArrayList(32);
        initView(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorGroupPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mRadius = 40;
        this.mSelectorRadius = 20;
        this.mSelectorOffsetX = 20;
        this.mBarOffsetY = 30;
        this.mBarHeight = 80;
        this.mBarRadius = 40;
        this.mLastSelectorX = 200.0f;
        this.bgBmpCanvas = new Canvas();
        this.fgCanvas = new Canvas();
        this.fgBmpPaint = new Paint();
        this.tmpRect = new Rect();
        this.mRects = new ArrayList<>();
        this.mPathPoint = new ArrayList(32);
        initView(context);
    }

    private final void initView(Context context) {
        Paint paint = new Paint();
        this.bgPaint = paint;
        Intrinsics.checkNotNull(paint);
        paint.setAntiAlias(true);
        this.mBgXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
        this.mBgMaskFilter = new BlurMaskFilter(40.0f, BlurMaskFilter.Blur.NORMAL);
        Paint paint2 = new Paint();
        this.mSelectorPaint = paint2;
        Intrinsics.checkNotNull(paint2);
        paint2.setAntiAlias(true);
        this.mSelectorHeight = context.getResources().getDimensionPixelOffset(R.dimen.color_selector_height);
        this.mSelectorWidth = context.getResources().getDimensionPixelOffset(R.dimen.color_selector_width);
        this.mBarHeight = getContext().getResources().getDimensionPixelOffset(R.dimen.color_selector_content_height);
        this.mRadius = getContext().getResources().getDimensionPixelOffset(R.dimen.color_group_picker_view_radius);
        this.mSelectorRadius = getContext().getResources().getDimensionPixelOffset(R.dimen.color_group_picker_selector_radius);
        this.mSelectorX = this.mLastSelectorX;
        this.mTransXMoveAnimConfig = new AnimConfig().addListeners(new TransitionListenerImpl(this, false)).setEase(-2, 0.95f, 0.4f);
        this.mTransXClickAnimConfig = new AnimConfig().addListeners(new TransitionListenerImpl(this, true)).setEase(-2, 0.95f, 0.4f);
        this.mTransXAnim = Folme.useValue(this);
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initPoints();
    }

    /* JADX INFO: compiled from: ColorGroupPickerView.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/miui/doodle/feature/color/ColorGroupPickerView$TransitionListenerImpl;", "Lmiuix/animation/listener/TransitionListener;", "mColorGroupPickerView", "Lcom/miui/doodle/feature/color/ColorGroupPickerView;", "isTouch", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/color/ColorGroupPickerView;Z)V", "Ljava/lang/ref/WeakReference;", "onBegin", "", "toTag", "", "updateList", "", "Lmiuix/animation/listener/UpdateInfo;", "onUpdate", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TransitionListenerImpl extends TransitionListener {
        private final boolean isTouch;
        private final WeakReference<ColorGroupPickerView> mColorGroupPickerView;

        public TransitionListenerImpl(ColorGroupPickerView mColorGroupPickerView, boolean z) {
            Intrinsics.checkNotNullParameter(mColorGroupPickerView, "mColorGroupPickerView");
            this.isTouch = z;
            this.mColorGroupPickerView = new WeakReference<>(mColorGroupPickerView);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object toTag, Collection<? extends UpdateInfo> updateList) {
            Intrinsics.checkNotNullParameter(toTag, "toTag");
            Intrinsics.checkNotNullParameter(updateList, "updateList");
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(updateList, "transX");
            Float fValueOf = updateInfoFindByName != null ? Float.valueOf(updateInfoFindByName.getFloatValue()) : null;
            if (fValueOf != null) {
                float fFloatValue = fValueOf.floatValue();
                ColorGroupPickerView colorGroupPickerView = this.mColorGroupPickerView.get();
                if (colorGroupPickerView != null) {
                    colorGroupPickerView.touchOrMoveSlider(this.isTouch, fFloatValue);
                }
            }
            ColorGroupPickerView colorGroupPickerView2 = this.mColorGroupPickerView.get();
            if (colorGroupPickerView2 != null) {
                colorGroupPickerView2.invalidate();
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object toTag, Collection<? extends UpdateInfo> updateList) {
            Intrinsics.checkNotNullParameter(toTag, "toTag");
            Intrinsics.checkNotNullParameter(updateList, "updateList");
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(updateList, "transX");
            Float fValueOf = updateInfoFindByName != null ? Float.valueOf(updateInfoFindByName.getFloatValue()) : null;
            if (fValueOf != null) {
                float fFloatValue = fValueOf.floatValue();
                ColorGroupPickerView colorGroupPickerView = this.mColorGroupPickerView.get();
                if (colorGroupPickerView != null) {
                    colorGroupPickerView.touchOrMoveSlider(this.isTouch, fFloatValue);
                }
            }
            ColorGroupPickerView colorGroupPickerView2 = this.mColorGroupPickerView.get();
            if (colorGroupPickerView2 != null) {
                colorGroupPickerView2.invalidate();
            }
        }
    }

    private final void initPoints() {
        for (int i = 0; i < 32; i++) {
            this.mPathPoint.add(new PointF());
        }
    }

    public final void setGroupPickListener(ColorGroupPickListener listener) {
        this.mGroupPickListener = listener;
    }

    public final void setData(int[] colorData, int selectGroup) {
        setColorData(colorData);
        setSelectGroup(selectGroup);
    }

    public final void setColorData(int[] colorData) {
        this.mColorData = colorData;
        updateDrawParams(true);
        invalidate();
    }

    public final void setSelectGroup(int selectGroup) {
        this.mSelectGroup = selectGroup;
        Rect rectByGroupIndex = getRectByGroupIndex(selectGroup);
        if (this.mSelectorPath == null || rectByGroupIndex == null) {
            return;
        }
        buildPath((rectByGroupIndex.left - this.mLastSelectorX) - this.mSelectorOffsetX);
        invalidate();
    }

    private final void updateDrawParams(boolean forceUpdate) {
        if (!forceUpdate && this.mViewWidth == getWidth() && this.mViewHeight == getHeight()) {
            return;
        }
        this.mViewWidth = getWidth();
        this.mViewHeight = getHeight();
        int[] iArr = this.mColorData;
        if (iArr == null) {
            return;
        }
        int i = this.mViewWidth;
        Intrinsics.checkNotNull(iArr);
        int length = i / iArr.length;
        this.mColorItemWidth = length;
        this.mSelectorWidth = length + (this.mSelectorRadius * 2);
        int i2 = this.mViewWidth;
        int[] iArr2 = this.mColorData;
        Intrinsics.checkNotNull(iArr2);
        int length2 = i2 % iArr2.length;
        int i3 = length2 / 2;
        int i4 = length2 - i3;
        int i5 = this.mColorItemWidth + i3;
        int i6 = this.mViewHeight;
        int i7 = this.mSelectorHeight;
        int i8 = this.mBarOffsetY;
        int i9 = (i6 - i7) + i8;
        int i10 = (i6 - i7) + i8 + this.mBarHeight;
        this.mRects.clear();
        Rect rect = new Rect();
        int i11 = 0;
        rect.set(0, i9, i5, i10);
        this.mRects.add(rect);
        int[] iArr3 = this.mColorData;
        Intrinsics.checkNotNull(iArr3);
        int i12 = 1;
        int length3 = iArr3.length - 1;
        while (i12 < length3) {
            Rect rect2 = new Rect();
            int i13 = this.mColorItemWidth;
            int i14 = ((i12 - 1) * i13) + i5;
            int i15 = i13 + i14;
            rect2.set(i14, i9, i15, i10);
            this.mRects.add(rect2);
            i12++;
            i11 = i15;
        }
        Rect rect3 = new Rect();
        rect3.set(i11, i9, this.mColorItemWidth + i11 + i4, i10);
        this.mRects.add(rect3);
    }

    private final void checkSelectGroupOnClick(float x, float y) {
        int groupIndexByTouchPos = getGroupIndexByTouchPos(x, y);
        if (groupIndexByTouchPos >= 0 && this.mSelectGroup != groupIndexByTouchPos) {
            HapticFeedbackTool.INSTANCE.performHapticFeedback(this);
            ColorGroupPickListener colorGroupPickListener = this.mGroupPickListener;
            Intrinsics.checkNotNull(colorGroupPickListener);
            colorGroupPickListener.onGroupPick(groupIndexByTouchPos);
            this.mSelectGroup = groupIndexByTouchPos;
        }
        Rect rectByGroupIndex = getRectByGroupIndex(this.mSelectGroup);
        if (rectByGroupIndex != null) {
            clickToPosition((rectByGroupIndex.left - this.mLastSelectorX) - this.mSelectorOffsetX);
        }
    }

    private final void checkSelectGroupOnMove(float x, float y) {
        int groupIndexByTouchPos = getGroupIndexByTouchPos(x, y);
        if (groupIndexByTouchPos < 0 || this.mSelectGroup == groupIndexByTouchPos) {
            return;
        }
        HapticFeedbackTool.INSTANCE.performHapticFeedback(this);
        ColorGroupPickListener colorGroupPickListener = this.mGroupPickListener;
        Intrinsics.checkNotNull(colorGroupPickListener);
        colorGroupPickListener.onGroupPick(groupIndexByTouchPos);
        this.mSelectGroup = groupIndexByTouchPos;
    }

    private final void checkSelectGroupAfterMove() {
        Rect rectByGroupIndex = getRectByGroupIndex(this.mSelectGroup);
        if (rectByGroupIndex != null) {
            float f = (rectByGroupIndex.left - this.mLastSelectorX) - this.mSelectorOffsetX;
            ColorGroupPickListener colorGroupPickListener = this.mGroupPickListener;
            Intrinsics.checkNotNull(colorGroupPickListener);
            colorGroupPickListener.onGroupPickEnd(this.mSelectGroup);
            smoothToPosition(f);
        }
    }

    private final int getGroupIndexByTouchPos(float x, float y) {
        int size = this.mRects.size();
        for (int i = 0; i < size; i++) {
            if (this.mRects.get(i).contains((int) x, (int) y)) {
                return i;
            }
        }
        return -1;
    }

    private final Rect getRectByGroupIndex(int groupIndex) {
        if (groupIndex < this.mRects.size()) {
            return this.mRects.get(groupIndex);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001a, code lost:
    
        if (r6 != 3) goto L13;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            float r0 = r6.getX()
            float r1 = r6.getY()
            int r6 = r6.getAction()
            r2 = 1
            if (r6 == 0) goto L68
            if (r6 == r2) goto L33
            r3 = 2
            if (r6 == r3) goto L1d
            r3 = 3
            if (r6 == r3) goto L33
            goto L32
        L1d:
            float r6 = r5.mDownX
            float r0 = r0 - r6
            float r6 = java.lang.Math.abs(r0)
            int r1 = r5.mScaledTouchSlop
            float r1 = (float) r1
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 < 0) goto L32
            float r6 = r5.getPositionAfterTrans(r0)
            r5.smoothToPosition(r6)
        L32:
            return r2
        L33:
            r5.mLastUpX = r0
            r5.mLastUpY = r1
            float r6 = r5.mDownX
            float r0 = r0 - r6
            float r6 = java.lang.Math.abs(r0)
            float r0 = r5.mLastUpY
            float r1 = r5.mDownY
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)
            int r1 = r5.mScaledTouchSlop
            float r3 = (float) r1
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L64
            float r6 = (float) r1
            int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r6 >= 0) goto L64
            float r6 = r5.mLastUpX
            int r0 = r5.getHeight()
            int r1 = r5.mSelectorHeight
            int r0 = r0 - r1
            float r0 = (float) r0
            r1 = 1114636288(0x42700000, float:60.0)
            float r0 = r0 + r1
            r5.checkSelectGroupOnClick(r6, r0)
            return r2
        L64:
            r5.checkSelectGroupAfterMove()
            return r2
        L68:
            float r6 = r5.mSelectorX
            r5.mLastSelectorX = r6
            miuix.animation.IStateStyle r6 = r5.mTransXAnim
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            r3 = 0
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            java.lang.String r4 = "transX"
            java.lang.Object[] r3 = new java.lang.Object[]{r4, r3}
            r6.setTo(r3)
            r5.mDownX = r0
            r5.mDownY = r1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.color.ColorGroupPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0053  */
    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int i;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.mBgPath == null || ((i = this.mViewWidth) != 0 && i != getWidth())) {
            this.mBgPath = new Path();
            this.mSelectorPath = new Path();
            updateDrawParams(true);
            Rect rectByGroupIndex = getRectByGroupIndex(this.mSelectGroup);
            if (rectByGroupIndex != null) {
                float f = rectByGroupIndex.left - this.mSelectorOffsetX;
                this.mSelectorX = f;
                this.mLastSelectorX = f;
            }
            buildPath(0.0f);
            createBlurColorBar();
        }
        Bitmap bitmap = this.colorBarBmp;
        if (bitmap != null) {
            Intrinsics.checkNotNull(bitmap);
            if (!bitmap.isRecycled()) {
                drawPanelShadowBg(canvas);
                drawBlurFgBmp(canvas);
            } else {
                drawPanelShadowBg(canvas);
            }
        } else {
            drawPanelShadowBg(canvas);
        }
        drawSelectorContent(canvas);
        super.dispatchDraw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void touchOrMoveSlider(boolean isTouch, float value) {
        if (isTouch) {
            buildPath(value);
        } else {
            moveSliderToPosition(value);
        }
    }

    private final void moveSliderToPosition(float targetX) {
        buildPath(targetX);
        invalidate();
        checkSelectGroupOnMove(this.mSelectorX + ((this.mSelectorWidth * 1.0f) / 2), (getHeight() - this.mSelectorHeight) + 60.0f);
    }

    private final void smoothToPosition(float targetX) {
        IStateStyle iStateStyle = this.mTransXAnim;
        Intrinsics.checkNotNull(iStateStyle);
        iStateStyle.to("transX", Float.valueOf(targetX), this.mTransXMoveAnimConfig);
    }

    private final void clickToPosition(float targetX) {
        IStateStyle iStateStyle = this.mTransXAnim;
        Intrinsics.checkNotNull(iStateStyle);
        iStateStyle.to("transX", Float.valueOf(targetX), this.mTransXClickAnimConfig);
    }

    private final void drawPanelShadowBg(Canvas canvas) {
        Paint paint = this.bgPaint;
        Intrinsics.checkNotNull(paint);
        paint.setXfermode(null);
        Paint paint2 = this.bgPaint;
        Intrinsics.checkNotNull(paint2);
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = this.bgPaint;
        Intrinsics.checkNotNull(paint3);
        paint3.setColor(-1);
        Paint paint4 = this.bgPaint;
        Intrinsics.checkNotNull(paint4);
        paint4.setMaskFilter(null);
        Paint paint5 = this.bgPaint;
        Intrinsics.checkNotNull(paint5);
        paint5.setShadowLayer(getElevation(), 0.0f, 10.0f, VisionAttribute.VISION_TYPE_CLASS_ENTITY);
        Path path = this.mBgPath;
        Intrinsics.checkNotNull(path);
        Paint paint6 = this.bgPaint;
        Intrinsics.checkNotNull(paint6);
        canvas.drawPath(path, paint6);
        Paint paint7 = this.bgPaint;
        Intrinsics.checkNotNull(paint7);
        paint7.clearShadowLayer();
    }

    private final void drawBlurFgBmp(Canvas canvas) {
        Bitmap bitmap = this.fgBitmap;
        if (bitmap != null) {
            Intrinsics.checkNotNull(bitmap);
            if (!bitmap.isRecycled()) {
                Bitmap bitmap2 = this.fgBitmap;
                Intrinsics.checkNotNull(bitmap2);
                bitmap2.recycle();
            }
        }
        Bitmap bitmap3 = this.bgBitmap;
        if (bitmap3 != null) {
            Intrinsics.checkNotNull(bitmap3);
            if (!bitmap3.isRecycled()) {
                Bitmap bitmap4 = this.bgBitmap;
                Intrinsics.checkNotNull(bitmap4);
                bitmap4.recycle();
            }
        }
        this.fgBitmap = Bitmap.createBitmap(this.mViewWidth, this.mViewHeight, Bitmap.Config.ARGB_8888);
        this.fgBmpPaint.setStyle(Paint.Style.FILL);
        this.fgBmpPaint.setColor(-1);
        this.fgCanvas.setBitmap(this.fgBitmap);
        Rect rect = this.tmpRect;
        Bitmap bitmap5 = this.colorBarBmp;
        Intrinsics.checkNotNull(bitmap5);
        int width = bitmap5.getWidth();
        Bitmap bitmap6 = this.colorBarBmp;
        Intrinsics.checkNotNull(bitmap6);
        rect.set(0, 0, width, bitmap6.getHeight());
        Canvas canvas2 = this.fgCanvas;
        Bitmap bitmap7 = this.colorBarBmp;
        Intrinsics.checkNotNull(bitmap7);
        Rect rect2 = this.tmpRect;
        canvas2.drawBitmap(bitmap7, rect2, rect2, this.bgPaint);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mViewWidth, this.mViewHeight, Bitmap.Config.ARGB_8888);
        this.bgBitmap = bitmapCreateBitmap;
        this.bgBmpCanvas.setBitmap(bitmapCreateBitmap);
        this.fgBmpPaint.setStyle(Paint.Style.FILL);
        this.fgBmpPaint.setColor(-1);
        Canvas canvas3 = this.bgBmpCanvas;
        Path path = this.mBgPath;
        Intrinsics.checkNotNull(path);
        canvas3.drawPath(path, this.fgBmpPaint);
        this.fgBmpPaint.setXfermode(this.mBgXfermode);
        Rect rect3 = this.tmpRect;
        Bitmap bitmap8 = this.bgBitmap;
        Intrinsics.checkNotNull(bitmap8);
        int width2 = bitmap8.getWidth();
        Bitmap bitmap9 = this.bgBitmap;
        Intrinsics.checkNotNull(bitmap9);
        rect3.set(0, 0, width2, bitmap9.getHeight());
        Canvas canvas4 = this.fgCanvas;
        Bitmap bitmap10 = this.bgBitmap;
        Intrinsics.checkNotNull(bitmap10);
        canvas4.drawBitmap(bitmap10, (Rect) null, this.tmpRect, this.fgBmpPaint);
        Paint paint = this.bgPaint;
        Intrinsics.checkNotNull(paint);
        paint.clearShadowLayer();
        Paint paint2 = this.bgPaint;
        Intrinsics.checkNotNull(paint2);
        paint2.setXfermode(null);
        Paint paint3 = this.bgPaint;
        Intrinsics.checkNotNull(paint3);
        paint3.setStyle(Paint.Style.FILL);
        Paint paint4 = this.bgPaint;
        Intrinsics.checkNotNull(paint4);
        paint4.setColor(-1);
        Paint paint5 = this.bgPaint;
        Intrinsics.checkNotNull(paint5);
        paint5.setMaskFilter(null);
        Bitmap bitmap11 = this.fgBitmap;
        Intrinsics.checkNotNull(bitmap11);
        canvas.drawBitmap(bitmap11, (Rect) null, this.tmpRect, (Paint) null);
    }

    private final void drawSelectorContent(Canvas canvas) {
        Paint paint = this.mSelectorPaint;
        Intrinsics.checkNotNull(paint);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = this.mSelectorPaint;
        Intrinsics.checkNotNull(paint2);
        int[] iArr = this.mColorData;
        Intrinsics.checkNotNull(iArr);
        paint2.setColor(iArr[this.mSelectGroup]);
        Path path = this.mSelectorPath;
        Intrinsics.checkNotNull(path);
        Paint paint3 = this.mSelectorPaint;
        Intrinsics.checkNotNull(paint3);
        canvas.drawPath(path, paint3);
    }

    private final float getPositionAfterTrans(float targetMoveX) {
        float f = this.mLastSelectorX;
        float f2 = 2;
        float f3 = ((this.mSelectorWidth * 1.0f) / f2) + f;
        float f4 = f + this.mSelectorOffsetX + targetMoveX;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        int i = this.mViewWidth;
        if (f4 > i) {
            f4 = i;
        }
        int groupIndexByTouchPos = getGroupIndexByTouchPos(f4, (getHeight() - this.mSelectorHeight) + 60.0f);
        if (groupIndexByTouchPos == -1) {
            groupIndexByTouchPos = this.mSelectGroup;
        }
        Rect rectByGroupIndex = getRectByGroupIndex(groupIndexByTouchPos);
        if (rectByGroupIndex == null) {
            return targetMoveX;
        }
        float fCoerceAtMost = RangesKt.coerceAtMost(1.0f, RangesKt.coerceAtLeast(-1.0f, (f4 - rectByGroupIndex.centerX()) / ((rectByGroupIndex.width() * 1.0f) / f2)));
        float f5 = 0.5f;
        float f6 = fCoerceAtMost * 0.5f;
        if ((groupIndexByTouchPos == 0 && f6 < 0.0f) || (groupIndexByTouchPos == this.mRects.size() - 1 && f6 > 0.0f)) {
            f5 = 0.1f;
        }
        return (rectByGroupIndex.centerX() + ((f6 * f5) * rectByGroupIndex.width())) - f3;
    }

    private final void buildPath(float moveX) {
        float f;
        float f2 = this.mLastSelectorX + moveX;
        this.mSelectorX = f2;
        if (f2 < 0.0f) {
            this.mSelectorX = 0.0f;
        }
        if (this.mSelectorX + this.mSelectorWidth > getWidth()) {
            this.mSelectorX = getWidth() - this.mSelectorWidth;
        }
        this.mSelectorY = getHeight() - this.mSelectorHeight;
        new RectF().set(0.0f, 0.0f, getWidth(), getHeight() - this.mSelectorHeight);
        Path path = this.mSelectorPath;
        Intrinsics.checkNotNull(path);
        path.reset();
        Path path2 = this.mBgPath;
        Intrinsics.checkNotNull(path2);
        path2.reset();
        PointF pointF = this.mPathPoint.get(0);
        pointF.x = this.mRadius;
        pointF.y = 0.0f;
        PointF pointF2 = this.mPathPoint.get(1);
        pointF2.x = getWidth() - this.mRadius;
        pointF2.y = 0.0f;
        PointF pointF3 = this.mPathPoint.get(2);
        pointF3.x = pointF2.x + (this.mRadius * RC_C);
        pointF3.y = 0.0f;
        PointF pointF4 = this.mPathPoint.get(3);
        pointF4.x = getWidth();
        pointF4.y = this.mRadius * 0.44808495f;
        PointF pointF5 = this.mPathPoint.get(4);
        pointF5.x = getWidth();
        pointF5.y = this.mRadius;
        path2.moveTo(pointF.x, pointF.y);
        path2.lineTo(pointF2.x, pointF2.y);
        path2.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
        float width = this.mSelectorX + this.mSelectorWidth + this.mSelectorRadius;
        if (width > getWidth()) {
            width = getWidth();
        }
        float f3 = width;
        PointF pointF6 = this.mPathPoint.get(5);
        pointF6.x = getWidth();
        pointF6.y = (getHeight() - this.mSelectorHeight) - this.mRadius;
        PointF pointF7 = this.mPathPoint.get(6);
        pointF7.x = getWidth();
        int height = getHeight() - this.mSelectorHeight;
        int i = this.mRadius;
        pointF7.y = (height - i) + (i * RC_C);
        PointF pointF8 = this.mPathPoint.get(7);
        int width2 = getWidth();
        int i2 = this.mRadius;
        pointF8.x = (width2 - i2) + (i2 * RC_C);
        pointF8.y = getHeight() - this.mSelectorHeight;
        PointF pointF9 = this.mPathPoint.get(8);
        pointF9.x = getWidth() - this.mRadius;
        pointF9.y = getHeight() - this.mSelectorHeight;
        path2.lineTo(pointF6.x, pointF6.y);
        if (f3 <= getWidth() - this.mRadius) {
            path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
        }
        PointF pointF10 = this.mPathPoint.get(9);
        pointF10.x = f3;
        pointF10.y = getHeight() - this.mSelectorHeight;
        PointF pointF11 = this.mPathPoint.get(10);
        pointF11.x = f3 - (this.mSelectorRadius * RC_C);
        pointF11.y = getHeight() - this.mSelectorHeight;
        PointF pointF12 = this.mPathPoint.get(11);
        pointF12.x = this.mSelectorX + this.mSelectorWidth;
        int height2 = getHeight() - this.mSelectorHeight;
        int i3 = this.mSelectorRadius;
        pointF12.y = (height2 + i3) - (i3 * RC_C);
        PointF pointF13 = this.mPathPoint.get(12);
        pointF13.x = this.mSelectorX + this.mSelectorWidth;
        pointF13.y = (getHeight() - this.mSelectorHeight) + this.mSelectorRadius;
        if (f3 <= getWidth() - this.mRadius) {
            path2.lineTo(pointF10.x, pointF10.y);
            path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
            f = 0.55191505f;
        } else {
            float width3 = getWidth();
            float width4 = (getWidth() - this.mSelectorX) - this.mSelectorWidth;
            int i4 = this.mRadius;
            f = 0.55191505f;
            pointF9.x = width3 - ((width4 * i4) / (i4 + this.mSelectorRadius));
            if (pointF9.x > getWidth()) {
                pointF9.x = getWidth();
            }
            float width5 = (getWidth() - f3) / this.mRadius;
            pointF8.x = pointF9.x + (this.mRadius * RC_C * width5);
            pointF11.x = pointF9.x - ((this.mSelectorRadius * RC_C) * width5);
            path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
            path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
        }
        PointF pointF14 = this.mPathPoint.get(13);
        pointF14.x = this.mSelectorX + this.mSelectorWidth;
        pointF14.y = getHeight() - this.mSelectorRadius;
        PointF pointF15 = this.mPathPoint.get(14);
        pointF15.x = this.mSelectorX + this.mSelectorWidth;
        int height3 = getHeight();
        int i5 = this.mSelectorRadius;
        pointF15.y = (height3 - i5) + (i5 * f);
        PointF pointF16 = this.mPathPoint.get(15);
        float f4 = this.mSelectorX + this.mSelectorWidth;
        int i6 = this.mSelectorRadius;
        pointF16.x = (f4 - i6) + (i6 * f);
        pointF16.y = getHeight();
        PointF pointF17 = this.mPathPoint.get(16);
        pointF17.x = (this.mSelectorX + this.mSelectorWidth) - this.mSelectorRadius;
        pointF17.y = getHeight();
        path2.lineTo(pointF14.x, pointF14.y);
        path2.cubicTo(pointF15.x, pointF15.y, pointF16.x, pointF16.y, pointF17.x, pointF17.y);
        PointF pointF18 = this.mPathPoint.get(17);
        pointF18.x = this.mSelectorX + this.mSelectorRadius;
        pointF18.y = getHeight();
        PointF pointF19 = this.mPathPoint.get(18);
        float f5 = this.mSelectorX;
        int i7 = this.mSelectorRadius;
        pointF19.x = (f5 + i7) - (i7 * f);
        pointF19.y = getHeight();
        PointF pointF20 = this.mPathPoint.get(19);
        pointF20.x = this.mSelectorX;
        int height4 = getHeight();
        int i8 = this.mSelectorRadius;
        pointF20.y = (height4 - i8) + (i8 * f);
        PointF pointF21 = this.mPathPoint.get(20);
        pointF21.x = this.mSelectorX;
        pointF21.y = getHeight() - this.mSelectorRadius;
        path2.lineTo(pointF18.x, pointF18.y);
        path2.cubicTo(pointF19.x, pointF19.y, pointF20.x, pointF20.y, pointF21.x, pointF21.y);
        PointF pointF22 = this.mPathPoint.get(21);
        pointF22.x = this.mSelectorX;
        pointF22.y = (getHeight() - this.mSelectorHeight) + this.mSelectorRadius;
        PointF pointF23 = this.mPathPoint.get(22);
        pointF23.x = this.mSelectorX;
        int height5 = getHeight() - this.mSelectorHeight;
        int i9 = this.mSelectorRadius;
        pointF23.y = (height5 + i9) - (i9 * f);
        PointF pointF24 = this.mPathPoint.get(23);
        float f6 = this.mSelectorX;
        int i10 = this.mSelectorRadius;
        float f7 = f6 - i10;
        float f8 = f7 < 0.0f ? 0.0f : f7;
        pointF24.x = (i10 * f) + f8;
        pointF24.y = getHeight() - this.mSelectorHeight;
        PointF pointF25 = this.mPathPoint.get(24);
        pointF25.x = f8;
        pointF25.y = getHeight() - this.mSelectorHeight;
        path2.lineTo(pointF22.x, pointF22.y);
        if (f8 >= this.mRadius) {
            path2.cubicTo(pointF23.x, pointF23.y, pointF24.x, pointF24.y, pointF25.x, pointF25.y);
        }
        PointF pointF26 = this.mPathPoint.get(25);
        int i11 = this.mRadius;
        float f9 = i11;
        if (f8 < i11) {
            f9 = f8;
        }
        pointF26.x = f9;
        pointF26.y = getHeight() - this.mSelectorHeight;
        PointF pointF27 = this.mPathPoint.get(26);
        pointF27.x = f9 * 0.44808495f;
        pointF27.y = getHeight() - this.mSelectorHeight;
        PointF pointF28 = this.mPathPoint.get(27);
        pointF28.x = 0.0f;
        int height6 = getHeight() - this.mSelectorHeight;
        int i12 = this.mRadius;
        pointF28.y = (height6 - i12) + (i12 * f);
        PointF pointF29 = this.mPathPoint.get(28);
        pointF29.x = 0.0f;
        pointF29.y = (getHeight() - this.mSelectorHeight) - this.mRadius;
        int i13 = this.mRadius;
        if (f8 >= i13) {
            path2.lineTo(pointF26.x, pointF26.y);
            path2.cubicTo(pointF27.x, pointF27.y, pointF28.x, pointF28.y, pointF29.x, pointF29.y);
        } else {
            pointF25.x = (this.mSelectorX * i13) / (i13 + this.mSelectorRadius);
            if (pointF25.x < 0.0f) {
                pointF25.x = 0.0f;
            }
            float f10 = f8 / this.mRadius;
            pointF24.x = pointF25.x + (this.mSelectorRadius * f * f10);
            pointF27.x = pointF25.x - ((this.mRadius * f) * f10);
            path2.cubicTo(pointF23.x, pointF23.y, pointF24.x, pointF24.y, pointF25.x, pointF25.y);
            path2.cubicTo(pointF27.x, pointF27.y, pointF28.x, pointF28.y, pointF29.x, pointF29.y);
        }
        PointF pointF30 = this.mPathPoint.get(29);
        pointF30.x = 0.0f;
        pointF30.y = this.mRadius;
        PointF pointF31 = this.mPathPoint.get(30);
        pointF31.x = 0.0f;
        pointF31.y = this.mRadius * 0.44808495f;
        PointF pointF32 = this.mPathPoint.get(31);
        pointF32.x = this.mRadius * 0.44808495f;
        pointF32.y = 0.0f;
        path2.lineTo(pointF30.x, pointF30.y);
        path2.cubicTo(pointF31.x, pointF31.y, pointF32.x, pointF32.y, pointF.x, pointF.y);
        RectF rectF = new RectF();
        float f11 = this.mSelectorRadius - 6.0f;
        float f12 = this.mSelectorX + this.mSelectorOffsetX;
        int height7 = getHeight() - this.mSelectorHeight;
        int i14 = this.mSelectorOffsetX;
        rectF.set(f12, height7 + i14 + 6.0f, (this.mSelectorX + this.mSelectorWidth) - i14, (getHeight() - this.mSelectorOffsetX) - 6.0f);
        Path path3 = this.mSelectorPath;
        Intrinsics.checkNotNull(path3);
        path3.addRoundRect(rectF, f11, f11, Path.Direction.CW);
    }

    private final void createBlurColorBar() {
        if (this.colorBarBmp != null || this.mColorData == null) {
            return;
        }
        Canvas canvas = new Canvas();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mViewWidth, this.mViewHeight, Bitmap.Config.ARGB_8888);
        this.colorBarBmp = bitmapCreateBitmap;
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        bitmapCreateBitmap.eraseColor(0);
        canvas.setBitmap(this.colorBarBmp);
        Paint paint = this.bgPaint;
        Intrinsics.checkNotNull(paint);
        paint.setXfermode(null);
        Paint paint2 = this.bgPaint;
        Intrinsics.checkNotNull(paint2);
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = this.bgPaint;
        Intrinsics.checkNotNull(paint3);
        paint3.setColor(0);
        Paint paint4 = this.bgPaint;
        Intrinsics.checkNotNull(paint4);
        paint4.setMaskFilter(this.mBgMaskFilter);
        int size = this.mRects.size();
        int[] iArr = this.mColorData;
        Intrinsics.checkNotNull(iArr);
        if (size == iArr.length) {
            Paint paint5 = this.bgPaint;
            Intrinsics.checkNotNull(paint5);
            int[] iArr2 = this.mColorData;
            Intrinsics.checkNotNull(iArr2);
            paint5.setColor(iArr2[0]);
            Paint paint6 = this.bgPaint;
            Intrinsics.checkNotNull(paint6);
            paint6.setAlpha(80);
            Rect rect = new Rect(this.mRects.get(0));
            rect.left += this.mBarRadius;
            RectF rectF = new RectF(this.mRects.get(0));
            int i = this.mBarRadius;
            Paint paint7 = this.bgPaint;
            Intrinsics.checkNotNull(paint7);
            canvas.drawRoundRect(rectF, i, i, paint7);
            Paint paint8 = this.bgPaint;
            Intrinsics.checkNotNull(paint8);
            canvas.drawRect(rect, paint8);
            int[] iArr3 = this.mColorData;
            Intrinsics.checkNotNull(iArr3);
            int length = iArr3.length - 1;
            for (int i2 = 1; i2 < length; i2++) {
                Paint paint9 = this.bgPaint;
                Intrinsics.checkNotNull(paint9);
                int[] iArr4 = this.mColorData;
                Intrinsics.checkNotNull(iArr4);
                paint9.setColor(iArr4[i2]);
                Paint paint10 = this.bgPaint;
                Intrinsics.checkNotNull(paint10);
                paint10.setAlpha(80);
                Rect rect2 = this.mRects.get(i2);
                Paint paint11 = this.bgPaint;
                Intrinsics.checkNotNull(paint11);
                canvas.drawRect(rect2, paint11);
            }
            Paint paint12 = this.bgPaint;
            Intrinsics.checkNotNull(paint12);
            int[] iArr5 = this.mColorData;
            Intrinsics.checkNotNull(iArr5);
            int[] iArr6 = this.mColorData;
            Intrinsics.checkNotNull(iArr6);
            paint12.setColor(iArr5[iArr6.length - 1]);
            Paint paint13 = this.bgPaint;
            Intrinsics.checkNotNull(paint13);
            paint13.setAlpha(80);
            ArrayList<Rect> arrayList = this.mRects;
            int[] iArr7 = this.mColorData;
            Intrinsics.checkNotNull(iArr7);
            Rect rect3 = new Rect(arrayList.get(iArr7.length - 1));
            rect3.right -= this.mBarRadius;
            ArrayList<Rect> arrayList2 = this.mRects;
            int[] iArr8 = this.mColorData;
            Intrinsics.checkNotNull(iArr8);
            RectF rectF2 = new RectF(arrayList2.get(iArr8.length - 1));
            Paint paint14 = this.bgPaint;
            Intrinsics.checkNotNull(paint14);
            canvas.drawRect(rect3, paint14);
            int i3 = this.mBarRadius;
            Paint paint15 = this.bgPaint;
            Intrinsics.checkNotNull(paint15);
            canvas.drawRoundRect(rectF2, i3, i3, paint15);
        }
    }
}
