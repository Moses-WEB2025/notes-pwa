package com.miui.handwriting.common.view.quickMenu.color;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.bean.PenBean;
import com.miui.handwriting.common.tools.HSLColor;
import com.miui.handwriting.common.view.colorpicker.ColorSelectView;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.QuickColorPickerPopupWindow;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickColorSelectUpdateListener;
import com.miui.handwriting.common.view.quickMenu.utils.AngleUtils;
import com.miui.handwriting.common.view.quickMenu.utils.HoverUtils;
import com.miui.handwriting.common.view.quickMenu.utils.PolarUtils;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import com.xiaomi.onetrack.b.e;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;

/* JADX INFO: compiled from: ColorMenuWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0014J0\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020$2\u0006\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020$H\u0014J\b\u0010-\u001a\u00020\"H\u0002J\u000e\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\u0011J\b\u00100\u001a\u00020\"H\u0002J\u0010\u00101\u001a\u00020\"2\u0006\u00102\u001a\u00020$H\u0002J\b\u00103\u001a\u00020\"H\u0002J\u000e\u00104\u001a\u00020\"2\u0006\u00105\u001a\u00020\u0013J\u0010\u00106\u001a\u00020\"2\u0006\u00107\u001a\u00020$H\u0002J\u0010\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020$H\u0002J\b\u00109\u001a\u00020\"H\u0016J\u000e\u0010:\u001a\u00020\"2\u0006\u0010;\u001a\u00020\u001aJ\b\u0010<\u001a\u00020\"H\u0014J\b\u0010=\u001a\u00020\"H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u00150\u00150\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/color/ColorMenuWrapper;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuWrapper;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "mColorMenuView", "Lcom/miui/handwriting/common/view/quickMenu/color/ColorMenuView;", "mFirstColorSelectView", "Lcom/miui/handwriting/common/view/colorpicker/ColorSelectView;", "mSecondColorSelectView", "mThirdColorSelectView", "mColorSelectViewList", "", "mCurPenBean", "Lcom/miui/handwriting/common/data/bean/PenBean;", "mColorSelectUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickColorSelectUpdateListener;", "mAnimStateList", "Lmiuix/animation/IStateStyle;", "kotlin.jvm.PlatformType", "mQuickColorPickerPopupWindow", "Lcom/miui/handwriting/common/view/popup/QuickColorPickerPopupWindow;", "mColorPickPopupLimitRect", "Landroid/graphics/Rect;", "mColorSelectLocationList", "Landroid/graphics/Point;", "mFirstColorSelectPosition", "Landroid/graphics/PointF;", "mSecondColorSelectPosition", "mThirdColorSelectPosition", "onMeasure", "", "widthMeasureSpec", "", "heightMeasureSpec", "onLayout", "changed", "", e.a, Constants.TIMESTAMP, Constants.RANDOM_LONG, "b", "initColorPickerPopupWindowListener", "setCurPenBean", "bean", "initColorSelectView", "setColorSelectViewChecked", "checkedIndex", "initColorSelectViewListener", "setColorSelectUpdateListener", "listener", "hoverAppearAnimation", "index", "hoverDisappearAnimation", "notifyMenuOpened", "setColorPickPopupLimitRect", "rect", "onOpenAnimationEnd", "onDismissAnimationStart", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorMenuWrapper extends BaseMenuWrapper {
    private List<? extends IStateStyle> mAnimStateList;
    private final ColorMenuView mColorMenuView;
    private final Rect mColorPickPopupLimitRect;
    private final List<Point> mColorSelectLocationList;
    private OnQuickColorSelectUpdateListener mColorSelectUpdateListener;
    private final List<ColorSelectView> mColorSelectViewList;
    private PenBean mCurPenBean;
    private PointF mFirstColorSelectPosition;
    private final ColorSelectView mFirstColorSelectView;
    private QuickColorPickerPopupWindow mQuickColorPickerPopupWindow;
    private PointF mSecondColorSelectPosition;
    private final ColorSelectView mSecondColorSelectView;
    private PointF mThirdColorSelectPosition;
    private final ColorSelectView mThirdColorSelectView;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorMenuWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        ColorMenuView colorMenuView = new ColorMenuView(context, null, 2, 0 == true ? 1 : 0);
        this.mColorMenuView = colorMenuView;
        ColorSelectView colorSelectView = new ColorSelectView(context, (Boolean) true);
        this.mFirstColorSelectView = colorSelectView;
        ColorSelectView colorSelectView2 = new ColorSelectView(context, (Boolean) true);
        this.mSecondColorSelectView = colorSelectView2;
        ColorSelectView colorSelectView3 = new ColorSelectView(context, (Boolean) true);
        this.mThirdColorSelectView = colorSelectView3;
        this.mColorSelectViewList = CollectionsKt.listOf((Object[]) new ColorSelectView[]{colorSelectView, colorSelectView2, colorSelectView3});
        this.mAnimStateList = CollectionsKt.listOf((Object[]) new IStateStyle[]{Folme.use((View) colorSelectView).state(), Folme.use((View) colorSelectView2).state(), Folme.use((View) colorSelectView3).state()});
        this.mQuickColorPickerPopupWindow = new QuickColorPickerPopupWindow(context);
        this.mColorPickPopupLimitRect = new Rect();
        this.mColorSelectLocationList = CollectionsKt.listOf((Object[]) new Point[]{new Point(), new Point(), new Point()});
        this.mFirstColorSelectPosition = new PointF();
        this.mSecondColorSelectPosition = new PointF();
        this.mThirdColorSelectPosition = new PointF();
        this.mQuickColorPickerPopupWindow.initUi();
        addView(colorMenuView);
        initColorSelectView();
        initColorSelectViewListener();
        initColorPickerPopupWindowListener();
        setShadow(QuickUtils.INSTANCE.getSectorPath(QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getColorMenuStartAngle(), getMenuRes().getColorMenuEndAngle(), null, 8, null)), 40.0f);
    }

    public /* synthetic */ ColorMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int uniformCircleDiameter = (int) getMenuRes().getUniformCircleDiameter();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(uniformCircleDiameter, 1073741824);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(uniformCircleDiameter, 1073741824);
        this.mFirstColorSelectView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mSecondColorSelectView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mThirdColorSelectView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ColorMenuView colorMenuView = this.mColorMenuView;
        colorMenuView.layout(0, 0, colorMenuView.getMeasuredWidth(), this.mColorMenuView.getMeasuredHeight());
        int uniformCircleDiameter = (int) getMenuRes().getUniformCircleDiameter();
        int i = uniformCircleDiameter / 2;
        int i2 = ((int) this.mFirstColorSelectPosition.x) - i;
        int i3 = ((int) this.mFirstColorSelectPosition.y) - i;
        this.mFirstColorSelectView.layout(i2, i3, i2 + uniformCircleDiameter, i3 + uniformCircleDiameter);
        int i4 = ((int) this.mSecondColorSelectPosition.x) - i;
        int i5 = ((int) this.mSecondColorSelectPosition.y) - i;
        this.mSecondColorSelectView.layout(i4, i5, i4 + uniformCircleDiameter, i5 + uniformCircleDiameter);
        int i6 = ((int) this.mThirdColorSelectPosition.x) - i;
        int i7 = ((int) this.mThirdColorSelectPosition.y) - i;
        this.mThirdColorSelectView.layout(i6, i7, i6 + uniformCircleDiameter, uniformCircleDiameter + i7);
    }

    private final void initColorPickerPopupWindowListener() {
        this.mQuickColorPickerPopupWindow.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.quickMenu.color.ColorMenuWrapper$$ExternalSyntheticLambda0
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public final void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorMenuWrapper.initColorPickerPopupWindowListener$lambda$1(this.f$0, colorPickerChangeEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initColorPickerPopupWindowListener$lambda$1(ColorMenuWrapper colorMenuWrapper, ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
        HSLColor hslColor = colorPickerChangeEvent.getHslColor();
        PenBean penBean = colorMenuWrapper.mCurPenBean;
        if (penBean != null) {
            OnQuickColorSelectUpdateListener onQuickColorSelectUpdateListener = colorMenuWrapper.mColorSelectUpdateListener;
            if (onQuickColorSelectUpdateListener != null) {
                onQuickColorSelectUpdateListener.onUpdate(penBean.getCheckedItemIndex(), hslColor.getColor());
            }
            colorMenuWrapper.mColorSelectViewList.get(penBean.getCheckedItemIndex()).setColor(hslColor.getColor());
        }
    }

    public final void setCurPenBean(PenBean bean) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        this.mCurPenBean = bean;
        Log.d(ColorMenuWrapperKt.TAG, "setCurPenBean: ");
        ColorSelectView colorSelectView = this.mFirstColorSelectView;
        colorSelectView.setHSLColor(bean.getDefaultHSLArray()[0]);
        colorSelectView.setCheck(bean.getCheckedItemIndex() == 0);
        ColorSelectView colorSelectView2 = this.mSecondColorSelectView;
        colorSelectView2.setHSLColor(bean.getDefaultHSLArray()[1]);
        colorSelectView2.setCheck(bean.getCheckedItemIndex() == 1);
        ColorSelectView colorSelectView3 = this.mThirdColorSelectView;
        colorSelectView3.setHSLColor(bean.getDefaultHSLArray()[2]);
        colorSelectView3.setCheck(bean.getCheckedItemIndex() == 2);
    }

    private final void initColorSelectView() {
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(getMenuRes().getFirstColorSelectButtonAngle());
        float fAdjustAngle2 = AngleUtils.INSTANCE.adjustAngle(getMenuRes().getSecondColorSelectButtonAngle());
        float fAdjustAngle3 = AngleUtils.INSTANCE.adjustAngle(getMenuRes().getThirdColorSelectButtonAngle());
        float f = 2;
        this.mFirstColorSelectPosition = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMCenterTrackOvalRect().width() / f, fAdjustAngle), getMCenterTrackOvalRect());
        this.mSecondColorSelectPosition = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMCenterTrackOvalRect().width() / f, fAdjustAngle2), getMCenterTrackOvalRect());
        this.mThirdColorSelectPosition = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMCenterTrackOvalRect().width() / f, fAdjustAngle3), getMCenterTrackOvalRect());
        ColorSelectView colorSelectView = this.mFirstColorSelectView;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = getResources().getString(R.string.creation_tool_color_left);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String str = String.format(string, Arrays.copyOf(new Object[]{1}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        colorSelectView.setContentDescription(str);
        ColorSelectView colorSelectView2 = this.mSecondColorSelectView;
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String string2 = getResources().getString(R.string.creation_tool_color_center);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        String str2 = String.format(string2, Arrays.copyOf(new Object[]{2}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        colorSelectView2.setContentDescription(str2);
        ColorSelectView colorSelectView3 = this.mThirdColorSelectView;
        StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
        String string3 = getResources().getString(R.string.creation_tool_color_right);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        String str3 = String.format(string3, Arrays.copyOf(new Object[]{3}, 1));
        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
        colorSelectView3.setContentDescription(str3);
        addView(this.mFirstColorSelectView);
        addView(this.mSecondColorSelectView);
        addView(this.mThirdColorSelectView);
    }

    private final void setColorSelectViewChecked(int checkedIndex) {
        int i = 0;
        for (Object obj : this.mColorSelectViewList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ((ColorSelectView) obj).setCheck(i == checkedIndex);
            i = i2;
        }
    }

    private final void initColorSelectViewListener() {
        final int i = 0;
        for (Object obj : this.mColorSelectViewList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            final ColorSelectView colorSelectView = (ColorSelectView) obj;
            colorSelectView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.quickMenu.color.ColorMenuWrapper$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ColorMenuWrapper.initColorSelectViewListener$lambda$8$lambda$6(colorSelectView, this, i, view);
                }
            });
            colorSelectView.setOnHoverListener(new View.OnHoverListener() { // from class: com.miui.handwriting.common.view.quickMenu.color.ColorMenuWrapper$$ExternalSyntheticLambda2
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    return ColorMenuWrapper.initColorSelectViewListener$lambda$8$lambda$7(this.f$0, i, view, motionEvent);
                }
            });
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initColorSelectViewListener$lambda$8$lambda$6(ColorSelectView colorSelectView, ColorMenuWrapper colorMenuWrapper, int i, View view) {
        if (!colorSelectView.isCheck()) {
            colorMenuWrapper.setColorSelectViewChecked(i);
            OnQuickColorSelectUpdateListener onQuickColorSelectUpdateListener = colorMenuWrapper.mColorSelectUpdateListener;
            if (onQuickColorSelectUpdateListener != null) {
                onQuickColorSelectUpdateListener.onUpdate(i, colorSelectView.getColor());
                return;
            }
            return;
        }
        colorMenuWrapper.mQuickColorPickerPopupWindow.initPopupWindow();
        QuickColorPickerPopupWindow quickColorPickerPopupWindow = colorMenuWrapper.mQuickColorPickerPopupWindow;
        PenBean penBean = colorMenuWrapper.mCurPenBean;
        quickColorPickerPopupWindow.setColor(penBean != null ? penBean.getHSLColor() : null);
        colorMenuWrapper.mQuickColorPickerPopupWindow.showAsDropDown(colorSelectView, colorMenuWrapper.mColorSelectLocationList.get(i), (int) colorMenuWrapper.getMenuRes().getColorPopupWindowGap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initColorSelectViewListener$lambda$8$lambda$7(ColorMenuWrapper colorMenuWrapper, int i, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9) {
            colorMenuWrapper.hoverAppearAnimation(i);
        }
        if (motionEvent.getAction() != 10) {
            return false;
        }
        colorMenuWrapper.hoverDisappearAnimation(i);
        return false;
    }

    public final void setColorSelectUpdateListener(OnQuickColorSelectUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mColorSelectUpdateListener = listener;
    }

    private final void hoverAppearAnimation(int index) {
        IStateStyle iStateStyle = this.mAnimStateList.get(index);
        ColorSelectView colorSelectView = this.mColorSelectViewList.get(index);
        HoverUtils hoverUtils = HoverUtils.INSTANCE;
        Intrinsics.checkNotNull(iStateStyle);
        hoverUtils.appearAnimation(iStateStyle, colorSelectView);
    }

    private final void hoverDisappearAnimation(int index) {
        IStateStyle iStateStyle = this.mAnimStateList.get(index);
        ColorSelectView colorSelectView = this.mColorSelectViewList.get(index);
        HoverUtils hoverUtils = HoverUtils.INSTANCE;
        Intrinsics.checkNotNull(iStateStyle);
        hoverUtils.disappearAnimation(iStateStyle, colorSelectView);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper
    public void notifyMenuOpened() {
        this.mColorMenuView.notifyMenuOpened();
        PenBean penBean = this.mCurPenBean;
        if (penBean != null) {
            setCurPenBean(penBean);
        }
    }

    public final void setColorPickPopupLimitRect(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.mColorPickPopupLimitRect.set(rect);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper
    protected void onOpenAnimationEnd() {
        int i = 0;
        for (Object obj : this.mColorSelectViewList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int[] iArr = new int[2];
            ((ColorSelectView) obj).getLocationInWindow(iArr);
            this.mColorSelectLocationList.get(i).x = iArr[0];
            this.mColorSelectLocationList.get(i).y = iArr[1];
            i = i2;
        }
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper
    protected void onDismissAnimationStart() {
        if (this.mQuickColorPickerPopupWindow.isShowing()) {
            this.mQuickColorPickerPopupWindow.dismiss();
        }
    }
}
