package com.miui.floatwindow.feature.root;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FloatFullCoverView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u001eR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0005R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\"\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006 "}, d2 = {"Lcom/miui/floatwindow/feature/root/FloatFullCoverView;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "floatFullWindow", "Lcom/miui/floatwindow/feature/root/FloatFullWindow;", "(Landroid/content/Context;Lcom/miui/floatwindow/feature/root/FloatFullWindow;)V", "mFloatFullWindow", "mContext", "getMContext", "()Landroid/content/Context;", "setMContext", "windowManager", "Landroid/view/WindowManager;", "getWindowManager", "()Landroid/view/WindowManager;", "setWindowManager", "(Landroid/view/WindowManager;)V", "value", "Landroid/view/WindowManager$LayoutParams;", "windowParams", "getWindowParams", "()Landroid/view/WindowManager$LayoutParams;", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "updateWindowParams", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatFullCoverView extends View {
    private static final String TAG = "FloatFullCoverView";
    private Context mContext;
    private FloatFullWindow mFloatFullWindow;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;

    public FloatFullCoverView(Context context) {
        super(context);
    }

    protected final Context getMContext() {
        return this.mContext;
    }

    protected final void setMContext(Context context) {
        this.mContext = context;
    }

    protected final WindowManager getWindowManager() {
        return this.windowManager;
    }

    protected final void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public final WindowManager.LayoutParams getWindowParams() {
        return this.windowParams;
    }

    public FloatFullCoverView(Context context, FloatFullWindow floatFullWindow) {
        this(context);
        this.mContext = context;
        this.mFloatFullWindow = floatFullWindow;
        setBackgroundColor(0);
        Context context2 = this.mContext;
        Intrinsics.checkNotNull(context2);
        Object systemService = context2.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.windowManager = (WindowManager) systemService;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.windowParams = layoutParams;
        Intrinsics.checkNotNull(layoutParams);
        FloatFullWindow floatFullWindow2 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow2);
        layoutParams.copyFrom(floatFullWindow2.getFloatWindowParams());
        WindowManager.LayoutParams layoutParams2 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams2);
        layoutParams2.type = R2.attr.preferenceCardStyleEnable;
        WindowManager.LayoutParams layoutParams3 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams3);
        FloatFullWindow floatFullWindow3 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow3);
        layoutParams3.width = floatFullWindow3.getMeasuredWidth();
        WindowManager.LayoutParams layoutParams4 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams4);
        FloatFullWindow floatFullWindow4 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow4);
        layoutParams4.height = floatFullWindow4.getMeasuredHeight();
        WindowManager.LayoutParams layoutParams5 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams5);
        FloatFullWindow floatFullWindow5 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow5);
        layoutParams5.x = floatFullWindow5.getFloatWindowParams().x;
        WindowManager.LayoutParams layoutParams6 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams6);
        FloatFullWindow floatFullWindow6 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow6);
        layoutParams6.y = floatFullWindow6.getFloatWindowParams().y;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Logger.INSTANCE.d(TAG, "onTouchEvent: action = " + event.getAction() + " xInScreen = " + event.getRawX() + " yInScreen = " + event.getRawY());
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow);
        return floatFullWindow.dispatchTouchEvent(event);
    }

    public final void updateWindowParams() {
        WindowManager.LayoutParams layoutParams = this.windowParams;
        Intrinsics.checkNotNull(layoutParams);
        FloatFullWindow floatFullWindow = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow);
        layoutParams.width = floatFullWindow.getMeasuredWidth();
        WindowManager.LayoutParams layoutParams2 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams2);
        FloatFullWindow floatFullWindow2 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow2);
        layoutParams2.height = floatFullWindow2.getMeasuredHeight();
        WindowManager.LayoutParams layoutParams3 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams3);
        FloatFullWindow floatFullWindow3 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow3);
        layoutParams3.x = floatFullWindow3.getFloatWindowParams().x;
        WindowManager.LayoutParams layoutParams4 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams4);
        FloatFullWindow floatFullWindow4 = this.mFloatFullWindow;
        Intrinsics.checkNotNull(floatFullWindow4);
        layoutParams4.y = floatFullWindow4.getFloatWindowParams().y;
        com.miui.common.tool.Logger logger = com.miui.common.tool.Logger.INSTANCE;
        WindowManager.LayoutParams layoutParams5 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams5);
        int i = layoutParams5.width;
        WindowManager.LayoutParams layoutParams6 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams6);
        int i2 = layoutParams6.height;
        WindowManager.LayoutParams layoutParams7 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams7);
        int i3 = layoutParams7.x;
        WindowManager.LayoutParams layoutParams8 = this.windowParams;
        Intrinsics.checkNotNull(layoutParams8);
        logger.v(TAG, "updateWindowParams: width = " + i + " height = " + i2 + " x = " + i3 + " y = " + layoutParams8.y);
        WindowManager windowManager = this.windowManager;
        Intrinsics.checkNotNull(windowManager);
        windowManager.updateViewLayout(this, this.windowParams);
    }
}
