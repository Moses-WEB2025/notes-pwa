package com.miui.handwriting.common.view.quickMenu.color;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.view.quickMenu.FloatAngleExtendKt;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuView;
import com.miui.handwriting.common.view.quickMenu.base.Sector;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorMenuView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0014J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\tH\u0016¨\u0006\u0018"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/color/ColorMenuView;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuView;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onMeasure", "widthMeasureSpec", "", "heightMeasureSpec", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "performClick", "getWrapperSector", "Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "notifyMenuOpened", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorMenuView extends BaseMenuView {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorMenuView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public void notifyMenuOpened() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        setMMenuStartAngle(FloatAngleExtendKt.minusAngle(getMenuRes().getColorMenuStartAngle(), getMArcExtensionAngle()));
        setMMenuEndAngle(FloatAngleExtendKt.addAngle(getMenuRes().getColorMenuEndAngle(), getMArcExtensionAngle()));
    }

    public /* synthetic */ ColorMenuView(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            setTouchArcMenuState(x, y);
        } else {
            if (action == 1) {
                setMIsTouchMenu(false);
                return performClick();
            }
            if (action == 2) {
                setTouchArcMenuState(x, y);
            }
        }
        return isTouchInArcMenu();
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuView
    public Sector getWrapperSector() {
        float outerCircleRadius = getMenuRes().getOuterCircleRadius() - getMenuRes().getInnerCircleRadius();
        Sector sector$default = QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getColorMenuStartAngle(), getMenuRes().getColorMenuEndAngle(), null, 8, null);
        getMWrapperOuterOvalRect().set(sector$default.getOuterOvalRect());
        getMWrapperInnerOvalRect().set(sector$default.getInnerOvalRect());
        getMSliderTrackOvalRect().set(sector$default.getOuterOvalRect());
        float f = outerCircleRadius / 2;
        getMSliderTrackOvalRect().inset(f, f);
        return sector$default;
    }
}
