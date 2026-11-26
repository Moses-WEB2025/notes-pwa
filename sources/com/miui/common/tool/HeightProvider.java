package com.miui.common.tool;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HeightProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001 B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0015\u001a\u00020\u0000J\u0010\u0010\u0016\u001a\u00020\u00002\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006!"}, d2 = {"Lcom/miui/common/tool/HeightProvider;", "Landroid/widget/PopupWindow;", "activity", "Landroid/app/Activity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Activity;)V", "mActivity", "rootView", "Landroid/view/View;", "listener", "Lcom/miui/common/tool/HeightProvider$HeightListener;", "heightMax", "", "mNavigationHeight", "mWindow", "Landroid/view/Window;", "needCalculateNavigation", "", "view", "mWindowManager", "Landroid/view/WindowManager;", "init", "setHeightListener", "registerView", "", "unRegisterView", "mOnGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "getMOnGlobalLayoutListener", "()Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "setMOnGlobalLayoutListener", "(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V", "HeightListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HeightProvider extends PopupWindow {
    private int heightMax;
    private HeightListener listener;
    private final Activity mActivity;
    private final int mNavigationHeight;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private final Window mWindow;
    private WindowManager mWindowManager;
    private boolean needCalculateNavigation;
    private final View rootView;
    private View view;

    /* JADX INFO: compiled from: HeightProvider.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/common/tool/HeightProvider$HeightListener;", "", "onHeightChanged", "", Layer.KEY_HEIGHT, "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface HeightListener {
        void onHeightChanged(int height);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public HeightProvider(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Activity activity2 = activity;
        super(activity2);
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.common.tool.HeightProvider$mOnGlobalLayoutListener$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                Rect rect = new Rect();
                this.this$0.rootView.getWindowVisibleDisplayFrame(rect);
                if (rect.bottom > this.this$0.heightMax) {
                    this.this$0.heightMax = rect.bottom;
                }
                int i = this.this$0.needCalculateNavigation ? this.this$0.heightMax - rect.bottom : (this.this$0.heightMax - rect.bottom) + this.this$0.mNavigationHeight;
                if (this.this$0.listener != null) {
                    HeightProvider.HeightListener heightListener = this.this$0.listener;
                    Intrinsics.checkNotNull(heightListener);
                    heightListener.onHeightChanged(i);
                }
            }
        };
        this.mActivity = activity;
        View view = new View(activity2);
        this.rootView = view;
        setContentView(view);
        Window window = activity.getWindow();
        this.mWindow = window;
        if (this.mWindowManager == null) {
            this.mWindowManager = (WindowManager) activity.getSystemService("window");
        }
        if (UIUtils.hasNavigationBar(activity2)) {
            this.needCalculateNavigation = true;
        }
        this.mNavigationHeight = UIUtils.getRealNavigationBarHeight(window);
        setBackgroundDrawable(new ColorDrawable(0));
        setWidth(0);
        setHeight(-1);
        setSoftInputMode(16);
        setInputMethodMode(1);
    }

    public final HeightProvider init() {
        if (!isShowing()) {
            Activity activity = this.mActivity;
            Intrinsics.checkNotNull(activity);
            View decorView = activity.getWindow().getDecorView();
            this.view = decorView;
            Intrinsics.checkNotNull(decorView);
            decorView.post(new Runnable() { // from class: com.miui.common.tool.HeightProvider.init.1
                @Override // java.lang.Runnable
                public void run() {
                    if (HeightProvider.this.view == null || HeightProvider.this.mActivity == null || HeightProvider.this.mActivity.isFinishing()) {
                        return;
                    }
                    HeightProvider heightProvider = HeightProvider.this;
                    heightProvider.showAtLocation(heightProvider.view, 0, 0, 0);
                }
            });
        }
        return this;
    }

    public final HeightProvider setHeightListener(HeightListener listener) {
        this.listener = listener;
        return this;
    }

    public final void registerView() {
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    public final void unRegisterView() {
        this.rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        dismiss();
    }

    public final ViewTreeObserver.OnGlobalLayoutListener getMOnGlobalLayoutListener() {
        return this.mOnGlobalLayoutListener;
    }

    public final void setMOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        Intrinsics.checkNotNullParameter(onGlobalLayoutListener, "<set-?>");
        this.mOnGlobalLayoutListener = onGlobalLayoutListener;
    }
}
