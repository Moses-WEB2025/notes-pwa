package com.miui.notes.ai.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class BottomSheetView {
    private String TAG = "Notes:AiBootSheetView";
    private Activity mActivity;
    Animation mBgInAnimation;
    Animation mBgOutAnimation;
    private View mBgView;
    private View mBottomFlagView;
    private View mContentView;
    DismissCallback mDismissCallback;
    Animation mInAnimation;
    Animation mOutAnimation;
    private ViewGroup mParentView;
    private AutoFloatingContainer mRootView;

    public interface DismissCallback {
        void onDismiss();
    }

    public BottomSheetView(Activity activity, int i) {
        this.mActivity = activity;
        AutoFloatingContainer autoFloatingContainer = (AutoFloatingContainer) LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_layout, (ViewGroup) null);
        this.mRootView = autoFloatingContainer;
        View viewFindViewById = autoFloatingContainer.findViewById(R.id.background);
        this.mBgView = viewFindViewById;
        viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ai.ui.BottomSheetView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.mBottomFlagView = this.mRootView.findViewById(R.id.bottom_flag);
        this.mContentView = LayoutInflater.from(this.mBgView.getContext()).inflate(i, (ViewGroup) this.mRootView.findViewById(R.id.content), true);
        adapterNavigationBar();
    }

    public void adapterNavigationBar() {
        int realNavigationBarHeight;
        if (this.mActivity == null) {
            return;
        }
        if (!RomUtils.isPadMode()) {
            realNavigationBarHeight = UIUtils.getRealNavigationBarHeight(this.mActivity);
        } else if (UIUtils.isInFullWindowGestureMode(this.mActivity)) {
            realNavigationBarHeight = UIUtils.getRealNavigationBarHeight(this.mActivity.getWindow());
        } else {
            realNavigationBarHeight = UIUtils.isFreeFormScreen(this.mActivity.getResources().getConfiguration()) ? 41 : 0;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBottomFlagView.getLayoutParams();
        layoutParams.height = realNavigationBarHeight;
        this.mBottomFlagView.setLayoutParams(layoutParams);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void show() {
        ViewGroup viewGroup = (ViewGroup) this.mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        this.mParentView = viewGroup;
        if (viewGroup.findViewById(R.id.bottom_sheet_root) != null) {
            this.mParentView.removeView(this.mRootView);
        }
        this.mParentView.addView(this.mRootView);
        if (this.mInAnimation == null) {
            this.mInAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.miuix_bottom_sheet_slide_in);
        }
        if (this.mBgInAnimation == null) {
            this.mBgInAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.miuix_bottom_bg_slide_in);
        }
        this.mContentView.startAnimation(this.mInAnimation);
        this.mBgView.startAnimation(this.mBgInAnimation);
    }

    public boolean isShowing() {
        AutoFloatingContainer autoFloatingContainer;
        return (this.mParentView == null || (autoFloatingContainer = this.mRootView) == null || autoFloatingContainer.getParent() != this.mParentView) ? false : true;
    }

    public void dismissWithoutAnim() {
        AutoFloatingContainer autoFloatingContainer;
        Animation animation = this.mOutAnimation;
        if (animation != null) {
            animation.cancel();
        }
        Animation animation2 = this.mBgOutAnimation;
        if (animation2 != null) {
            animation2.cancel();
        }
        View view = this.mContentView;
        if (view != null) {
            view.clearAnimation();
        }
        View view2 = this.mBgView;
        if (view2 != null) {
            view2.clearAnimation();
        }
        ViewGroup viewGroup = this.mParentView;
        if (viewGroup == null || viewGroup.findViewById(R.id.bottom_sheet_root) == null || (autoFloatingContainer = this.mRootView) == null) {
            return;
        }
        this.mParentView.removeView(autoFloatingContainer);
    }

    public void dismiss() {
        if (this.mOutAnimation == null) {
            this.mOutAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.miuix_bottom_sheet_slide_out);
        }
        if (this.mBgOutAnimation == null) {
            this.mBgOutAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.miuix_bottom_bg_slide_out);
        }
        this.mOutAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.miui.notes.ai.ui.BottomSheetView.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (BottomSheetView.this.mParentView.findViewById(R.id.bottom_sheet_root) != null) {
                    BottomSheetView.this.mParentView.removeView(BottomSheetView.this.mRootView);
                }
                if (BottomSheetView.this.mDismissCallback != null) {
                    BottomSheetView.this.mDismissCallback.onDismiss();
                }
            }
        });
        this.mContentView.startAnimation(this.mOutAnimation);
        this.mBgView.startAnimation(this.mBgOutAnimation);
    }

    public void addDismissCallback(DismissCallback dismissCallback) {
        this.mDismissCallback = dismissCallback;
    }
}
