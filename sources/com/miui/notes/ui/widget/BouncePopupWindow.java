package com.miui.notes.ui.widget;

import android.R;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class BouncePopupWindow extends PopupWindow {
    private static final int HOST_BRIGHT_ALPHA = 255;
    private static final int HOST_DIM_ALPHA = 127;
    private static final String TAG = "BouncePopupWindow";
    private ListAdapter mAdapter;
    private AnimationListener mAnimationListener;
    private ClipDrawable mClipDrawable;
    private View mContentView;
    private Activity mContext;
    private LayoutAnimationController mFadeInAnimationController;
    private LayoutAnimationController mFadeOutAnimationController;
    private ListView mListView;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private FrameLayout mRootView;

    public BouncePopupWindow(Activity activity) {
        super(activity);
        this.mContext = activity;
        setFocusable(true);
        this.mAnimationListener = new AnimationListener();
        setWindowLayoutMode(-1, -2);
        FrameLayout frameLayout = new FrameLayout(activity);
        this.mRootView = frameLayout;
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.widget.BouncePopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BouncePopupWindow.this.dismiss();
            }
        });
        super.setContentView(this.mRootView);
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void show(View view) {
        if (view == null) {
            Logger.INSTANCE.e(TAG, "show: anchor is null");
            return;
        }
        if (this.mContentView == null) {
            ListView listView = new ListView(this.mContext);
            this.mContentView = listView;
            listView.setId(R.id.list);
            this.mContentView.setPadding(0, 0, 0, (int) view.getContext().getResources().getDimension(com.miui.notes.R.dimen.menu_popup_padding_bottom));
        }
        if (this.mRootView.getChildCount() != 1 || this.mRootView.getChildAt(0) != this.mContentView) {
            this.mRootView.removeAllViews();
            this.mRootView.addView(this.mContentView);
            ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
        }
        ListView listView2 = (ListView) this.mContentView.findViewById(R.id.list);
        this.mListView = listView2;
        if (listView2 == null) {
            Logger.INSTANCE.e(TAG, "list not found");
            return;
        }
        if (this.mFadeInAnimationController == null) {
            this.mFadeInAnimationController = createDefaultFadeInAnimation();
        }
        this.mListView.setLayoutAnimation(this.mFadeInAnimationController);
        this.mListView.setLayoutAnimationListener(this.mAnimationListener);
        this.mAnimationListener.setIsInAnimation(true);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.miui.notes.ui.widget.BouncePopupWindow.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                if (i < 0 || i >= BouncePopupWindow.this.mAdapter.getCount()) {
                    return;
                }
                BouncePopupWindow.this.mOnItemClickListener.onItemClick(adapterView, view2, i, j);
            }
        });
        this.mListView.setAdapter(this.mAdapter);
        ClipDrawable clipDrawable = new ClipDrawable(new ColorDrawable(this.mContext.getResources().getColor(com.miui.notes.R.color.bounce_popupwindow_background_color)));
        this.mClipDrawable = clipDrawable;
        clipDrawable.setRangeRatio(0.0f);
        setBackgroundDrawable(this.mClipDrawable);
        showAtLocation(view, 80, 0, 0);
        adjustHostBrightness(127);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustHostBrightness(int i) {
        ViewGroup viewGroup = (ViewGroup) this.mContext.getWindow().getDecorView().getRootView();
        if (i == 127) {
            applyDim(viewGroup, 127);
        } else {
            clearDim(viewGroup);
        }
    }

    private static void applyDim(ViewGroup viewGroup, int i) {
        ColorDrawable colorDrawable = new ColorDrawable(-16777216);
        colorDrawable.setBounds(0, 0, viewGroup.getWidth(), viewGroup.getHeight());
        colorDrawable.setAlpha(i);
        viewGroup.getOverlay().add(colorDrawable);
    }

    private static void clearDim(ViewGroup viewGroup) {
        viewGroup.getOverlay().clear();
    }

    protected LayoutAnimationController createDefaultFadeInAnimation() {
        return AnimationUtils.loadLayoutAnimation(this.mContext, com.miui.notes.R.anim.bounce_layout_fade_in);
    }

    protected LayoutAnimationController createDefaultFadeOutAnimation() {
        return AnimationUtils.loadLayoutAnimation(this.mContext, com.miui.notes.R.anim.bounce_layout_fade_out);
    }

    private class AnimationListener implements Animation.AnimationListener {
        private ValueAnimator mBackgroundAnimator;
        private boolean mInAnimation;

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        private AnimationListener() {
        }

        public void setIsInAnimation(boolean z) {
            this.mInAnimation = z;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            ValueAnimator backgroundAnimator = BouncePopupWindow.this.getBackgroundAnimator(this.mInAnimation ? BouncePopupWindow.this.mFadeInAnimationController : BouncePopupWindow.this.mFadeOutAnimationController, this.mInAnimation);
            this.mBackgroundAnimator = backgroundAnimator;
            if (backgroundAnimator != null) {
                backgroundAnimator.start();
            }
            this.mBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.notes.ui.widget.BouncePopupWindow.AnimationListener.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    BouncePopupWindow.this.mClipDrawable.setRangeRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    BouncePopupWindow.this.setBackgroundDrawable(BouncePopupWindow.this.mClipDrawable);
                }
            });
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            ValueAnimator valueAnimator = this.mBackgroundAnimator;
            if (valueAnimator != null) {
                valueAnimator.end();
                this.mBackgroundAnimator = null;
            }
            BouncePopupWindow.this.mListView.setLayoutAnimation(null);
            if (this.mInAnimation) {
                return;
            }
            BouncePopupWindow.super.dismiss();
            BouncePopupWindow.this.adjustHostBrightness(255);
        }
    }

    protected ValueAnimator getBackgroundAnimator(LayoutAnimationController layoutAnimationController, boolean z) {
        ValueAnimator valueAnimatorOfFloat;
        if (z) {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        } else {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
        }
        long duration = layoutAnimationController.getAnimation().getDuration();
        valueAnimatorOfFloat.setTarget(getBackground());
        valueAnimatorOfFloat.setDuration(duration);
        return valueAnimatorOfFloat;
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (isShowing() && this.mListView.getLayoutAnimation() == null) {
            if (this.mFadeOutAnimationController == null) {
                this.mFadeOutAnimationController = createDefaultFadeOutAnimation();
            }
            LayoutAnimationController layoutAnimationController = this.mFadeOutAnimationController;
            if (layoutAnimationController != null) {
                this.mListView.setLayoutAnimation(layoutAnimationController);
                this.mListView.setLayoutAnimationListener(this.mAnimationListener);
                this.mAnimationListener.setIsInAnimation(false);
                this.mListView.startLayoutAnimation();
            }
        }
    }

    private class ClipDrawable extends StateListDrawable {
        private float mRangeRatio;

        @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        public ClipDrawable(Drawable drawable) {
            addState(new int[0], drawable == null ? new ColorDrawable(BouncePopupWindow.this.mContext.getResources().getColor(R.color.transparent)) : drawable);
        }

        public void setRangeRatio(float f) {
            this.mRangeRatio = f;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.clipRect(BouncePopupWindow.this.mContentView.getLeft(), BouncePopupWindow.this.mRootView.getBottom() * (1.0f - this.mRangeRatio), BouncePopupWindow.this.mContentView.getRight(), BouncePopupWindow.this.mContentView.getTop() + BouncePopupWindow.this.mRootView.getBottom());
            super.draw(canvas);
            canvas.restore();
        }
    }
}
