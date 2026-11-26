package miuix.popupwidget.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.view.DisplayCutoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Locale;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.WindowUtils;
import miuix.internal.util.DeviceHelper;
import miuix.internal.util.ViewUtils;
import miuix.popupwidget.R;
import miuix.smooth.SmoothFrameLayout2;
import miuix.theme.token.DimToken;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes3.dex */
public class DropDownPopupWindow {
    private static final int SHADOW_OFFSET_X = 0;
    private static final int SHADOW_OFFSET_Y = 26;
    private static final int SHADOW_RADIUS = 32;
    private static final String TAG = "DropDownPopupWindow";
    private View mAnchorView;
    private ValueAnimator mAnimator;
    private int mBottomEdge;
    private ContainerView mContainer;
    private ContainerController mContainerController;
    private ContentController mContentController;
    private int mContentHeight;
    private View mContentView;
    private Context mContext;
    private boolean mDismissPending;
    private Controller mDropDownController;
    private int mEdgeDistance;
    private int mElevation;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mMinWidth;
    private android.widget.PopupWindow mPopupWindow;
    private View mRealContainerView;
    private int mTopEdge;
    private int mWindowHeight;
    private int mWindowWidth;
    private int mShowDuration = 300;
    private int mDismissDuration = 300;
    private int mShadowColor = 0;
    private ValueAnimator.AnimatorUpdateListener mValueUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) DropDownPopupWindow.this.mAnimator.getAnimatedValue()).floatValue();
            if (DropDownPopupWindow.this.mContainerController != null) {
                DropDownPopupWindow.this.mContainerController.onAnimationUpdate(DropDownPopupWindow.this.mContainer, fFloatValue);
            }
            if (DropDownPopupWindow.this.mContentController != null) {
                DropDownPopupWindow.this.mContentController.onAnimationUpdate(DropDownPopupWindow.this.mContentView, fFloatValue);
            }
        }
    };
    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.2
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        private void tryDismiss() {
            if (DropDownPopupWindow.this.mDismissPending) {
                DropDownPopupWindow.this.realDismiss();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (!DropDownPopupWindow.this.mDismissPending || DropDownPopupWindow.this.mDropDownController == null) {
                return;
            }
            DropDownPopupWindow.this.mDropDownController.onDismiss();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            tryDismiss();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            tryDismiss();
        }
    };

    public interface ContainerController extends Controller {
        boolean onAddContent(View view, View view2);
    }

    public interface ContentController extends Controller {
        View getContentView();
    }

    public interface Controller {
        void onAnimationUpdate(View view, float f);

        void onDismiss();

        void onShow();
    }

    private class ContainerView extends FrameLayout {
        public ContainerView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            setClipChildren(false);
            setClipToPadding(false);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!super.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
                DropDownPopupWindow.this.dismiss();
            }
            return true;
        }

        @Override // android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i != 4 || keyEvent.getAction() != 1) {
                return false;
            }
            DropDownPopupWindow.this.dismiss();
            return true;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            DropDownPopupWindow.this.configurationChanged(configuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configurationChanged(Configuration configuration) {
        Activity activityContextFromView = getActivityContextFromView(this.mContainer);
        View decorView = activityContextFromView != null ? activityContextFromView.getWindow().getDecorView() : null;
        if (decorView != null) {
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.3
                @Override // android.view.View.OnApplyWindowInsetsListener
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    DropDownPopupWindow dropDownPopupWindow = DropDownPopupWindow.this;
                    DropDownPopupWindow.this.adjustLocation(dropDownPopupWindow.getCutout(dropDownPopupWindow.mContainer), windowInsets);
                    return windowInsets;
                }
            });
        }
        this.mContainer.post(new Runnable() { // from class: miuix.popupwidget.widget.DropDownPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4338x80e5bde1();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$configurationChanged$0$miuix-popupwidget-widget-DropDownPopupWindow, reason: not valid java name */
    /* synthetic */ void m4338x80e5bde1() {
        initData();
        adjustLocation(getCutout(this.mContainer), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0030  */
    public void adjustLocation(Rect rect, WindowInsets windowInsets) {
        DropDownPopupWindow dropDownPopupWindow;
        if (this.mAnchorView == null || this.mRealContainerView == null) {
            return;
        }
        updateMaxWidth(rect);
        updateMaxHeight(windowInsets);
        int width = this.mPopupWindow.getWidth();
        ContentController contentController = this.mContentController;
        if (contentController != null) {
            View contentView = contentController.getContentView();
            this.mContentView = contentView;
            if (contentView != null) {
                dropDownPopupWindow = this;
                width = dropDownPopupWindow.setupContentView(this.mContainer, contentView, this.mElevation, this.mMinWidth, this.mContainerController);
            } else {
                dropDownPopupWindow = this;
            }
        } else {
            dropDownPopupWindow = this;
        }
        int i = dropDownPopupWindow.mMaxWidth;
        if (width > i) {
            width = i;
        }
        int i2 = dropDownPopupWindow.mContentHeight;
        int i3 = dropDownPopupWindow.mMaxHeight;
        if (i2 > i3) {
            dropDownPopupWindow.mPopupWindow.setHeight(i3);
        } else {
            dropDownPopupWindow.mPopupWindow.setHeight(-2);
        }
        dropDownPopupWindow.mPopupWindow.setWidth(width);
        int height = dropDownPopupWindow.mPopupWindow.getHeight();
        View view = dropDownPopupWindow.mAnchorView;
        if (view != null && view.isAttachedToWindow()) {
            int[] iArrComputeLocation = dropDownPopupWindow.computeLocation(width, rect);
            dropDownPopupWindow.mPopupWindow.update(iArrComputeLocation[0], iArrComputeLocation[1], width, height);
        } else if (dropDownPopupWindow.mContainer.isAttachedToWindow()) {
            dropDownPopupWindow.mPopupWindow.update(0, 0, width, height);
        }
    }

    public void setContainerController(ContainerController containerController) {
        this.mContainerController = containerController;
    }

    public void setContentController(ContentController contentController) {
        this.mContentController = contentController;
    }

    public void setDropDownController(Controller controller) {
        this.mDropDownController = controller;
    }

    public Context getContext() {
        return this.mContext;
    }

    public static class DefaultContainerController implements ContainerController {
        @Override // miuix.popupwidget.widget.DropDownPopupWindow.ContainerController
        public boolean onAddContent(View view, View view2) {
            return false;
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onDismiss() {
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onShow() {
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onAnimationUpdate(View view, float f) {
            Drawable background = view == null ? null : view.getBackground();
            if (background != null) {
                background.setAlpha((int) (f * 255.0f));
            }
        }
    }

    public static class ViewContentController implements ContentController {
        private View mContent;
        private Context mContext;
        private int mLayoutId;

        protected void onContentInit(View view) {
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onDismiss() {
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onShow() {
        }

        public ViewContentController(DropDownPopupWindow dropDownPopupWindow, int i) {
            this(dropDownPopupWindow.getContext(), i);
            dropDownPopupWindow.setContentController(this);
        }

        public ViewContentController(Context context, int i) {
            this.mContext = context;
            this.mLayoutId = i;
        }

        protected void initContent() {
            if (this.mContent == null) {
                View viewInflate = LayoutInflater.from(this.mContext).inflate(this.mLayoutId, (ViewGroup) null);
                this.mContent = viewInflate;
                onContentInit(viewInflate);
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
        public void onAnimationUpdate(View view, float f) {
            if (view != null) {
                view.setTranslationY((-view.getHeight()) * (1.0f - f));
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.ContentController
        public View getContentView() {
            initContent();
            return this.mContent;
        }
    }

    public static class ListController extends ViewContentController {
        private ListView mListView;

        public ListController(DropDownPopupWindow dropDownPopupWindow) {
            super(dropDownPopupWindow, R.layout.miuix_appcompat_drop_down_popup_list);
        }

        public ListController(DropDownPopupWindow dropDownPopupWindow, int i) {
            super(dropDownPopupWindow, i);
        }

        public ListController(Context context) {
            this(context, R.layout.miuix_appcompat_drop_down_popup_list);
        }

        public ListController(Context context, int i) {
            super(context, i);
        }

        /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownPopupWindow$ListController$1, reason: invalid class name */
        class AnonymousClass1 implements View.OnTouchListener {
            int lastIndex = -1;

            AnonymousClass1() {
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(final View view, MotionEvent motionEvent) {
                int firstVisiblePosition;
                int i;
                int iPointToPosition = ListController.this.mListView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1 || action == 3 || action == 6) {
                        this.lastIndex = -1;
                        ListController.this.mListView.postDelayed(new Runnable() { // from class: miuix.popupwidget.widget.DropDownPopupWindow$ListController$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DropDownPopupWindow.ListController.AnonymousClass1.lambda$onTouch$0(view);
                            }
                        }, ViewConfiguration.getPressedStateDuration());
                    }
                } else if (iPointToPosition != -1 && (firstVisiblePosition = iPointToPosition - ListController.this.mListView.getFirstVisiblePosition()) != (i = this.lastIndex)) {
                    if (i != -1) {
                        ListController.this.mListView.getChildAt(this.lastIndex).setPressed(false);
                    }
                    ListController.this.mListView.getChildAt(firstVisiblePosition).setPressed(true);
                    this.lastIndex = firstVisiblePosition;
                }
                return false;
            }

            static /* synthetic */ void lambda$onTouch$0(View view) {
                if (view instanceof ViewGroup) {
                    try {
                        int childCount = ((ViewGroup) view).getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            ((ViewGroup) view).getChildAt(i).setPressed(false);
                        }
                    } catch (Exception e) {
                        Log.e(DropDownPopupWindow.TAG, "list onTouch error " + e);
                    }
                }
            }
        }

        @Override // miuix.popupwidget.widget.DropDownPopupWindow.ViewContentController
        protected void onContentInit(View view) {
            ListView listView = (ListView) view.findViewById(android.R.id.list);
            this.mListView = listView;
            listView.setOnTouchListener(new AnonymousClass1());
        }

        public ListView getListView() {
            initContent();
            return this.mListView;
        }
    }

    public DropDownPopupWindow(Context context, AttributeSet attributeSet, int i) {
        this.mContext = context;
        this.mPopupWindow = new android.widget.PopupWindow(context, attributeSet, 0, i);
        ContainerView containerView = new ContainerView(context, attributeSet, i);
        this.mContainer = containerView;
        containerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.4
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                DropDownPopupWindow.this.adjustLocation(DropDownPopupWindow.this.getCutout(view), null);
            }
        });
        this.mPopupWindow.setAnimationStyle(DeviceHelper.isFeatureWholeAnim() ? R.style.Animation_PopupWindow_DropDown : 0);
        initPopupWindow();
    }

    private void updateMaxWidth(Rect rect) {
        if (rect.left > 0) {
            this.mMaxWidth = (this.mWindowWidth - rect.left) - this.mEdgeDistance;
        } else if (rect.right > 0) {
            this.mMaxWidth = (this.mWindowWidth - rect.right) - this.mEdgeDistance;
        } else {
            this.mMaxWidth = this.mWindowWidth - (this.mEdgeDistance * 2);
        }
    }

    private void updateMaxHeight(WindowInsets windowInsets) {
        View view = this.mAnchorView;
        if (view == null || this.mWindowHeight == 0) {
            return;
        }
        int height = view.getRootView().getHeight();
        if (windowInsets == null) {
            windowInsets = this.mAnchorView.getRootWindowInsets();
        }
        if (windowInsets != null) {
            if (Build.VERSION.SDK_INT >= 30) {
                Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                this.mTopEdge = insets.top;
                this.mBottomEdge = insets.bottom;
            } else {
                this.mTopEdge = windowInsets.getSystemWindowInsetTop();
                this.mBottomEdge = windowInsets.getSystemWindowInsetBottom();
            }
        }
        this.mMaxHeight = (height - this.mTopEdge) - this.mBottomEdge;
    }

    private void initPopupWindow() {
        initData();
        this.mPopupWindow.setWidth(-2);
        this.mPopupWindow.setHeight(-2);
        this.mPopupWindow.setSoftInputMode(3);
        this.mPopupWindow.setOutsideTouchable(false);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mContainer.setFocusableInTouchMode(true);
        this.mPopupWindow.setContentView(this.mContainer);
    }

    private void initData() {
        this.mElevation = (int) (this.mContext.getResources().getDisplayMetrics().density * 32.0f);
        this.mShadowColor = this.mContext.getResources().getColor(R.color.miuix_appcompat_drop_down_menu_spot_shadow_color);
        this.mEdgeDistance = this.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_horizontal_edge_margin);
        this.mMinWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_min_width);
        this.mWindowWidth = WindowUtils.getWindowSize(this.mContext).x;
        this.mWindowHeight = WindowUtils.getWindowSize(this.mContext).y;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getCutout(View view) {
        DisplayCutout cutout;
        Rect rect = new Rect();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
        if (rootWindowInsets != null) {
            DisplayCutoutCompat displayCutout = rootWindowInsets.getDisplayCutout();
            if (displayCutout == null) {
                Activity activityContextFromView = getActivityContextFromView(view);
                if (activityContextFromView != null && (cutout = activityContextFromView.getWindowManager().getDefaultDisplay().getCutout()) != null) {
                    rect.left = cutout.getSafeInsetLeft();
                    rect.right = cutout.getSafeInsetRight();
                    return rect;
                }
            } else {
                rect.left = displayCutout.getSafeInsetLeft();
                rect.right = displayCutout.getSafeInsetRight();
            }
        }
        return rect;
    }

    private Activity getActivityContextFromView(View view) {
        Context context = ((ViewGroup) view.getRootView()).getChildAt(0).getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public void setAnchor(View view) {
        this.mAnchorView = view;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003d  */
    public void show() {
        DropDownPopupWindow dropDownPopupWindow;
        int i;
        View view;
        if (this.mPopupWindow.isShowing()) {
            View view2 = this.mAnchorView;
            if (view2 != null) {
                this.mPopupWindow.update(view2, 0, 0);
            } else {
                this.mPopupWindow.update(0, 0);
            }
            dropDownPopupWindow = this;
        } else {
            ContentController contentController = this.mContentController;
            if (contentController != null) {
                View contentView = contentController.getContentView();
                this.mContentView = contentView;
                if (contentView != null) {
                    dropDownPopupWindow = this;
                    i = dropDownPopupWindow.setupContentView(this.mContainer, contentView, this.mElevation, this.mMinWidth, this.mContainerController);
                    dropDownPopupWindow.prepareWindowElevation(dropDownPopupWindow.mContentView, dropDownPopupWindow.mElevation);
                } else {
                    dropDownPopupWindow = this;
                    i = -2;
                }
            } else {
                dropDownPopupWindow = this;
                i = -2;
            }
            int i2 = dropDownPopupWindow.mMaxWidth;
            if (i > i2) {
                i = i2;
            }
            View view3 = dropDownPopupWindow.mContentView;
            if (view3 instanceof SmoothFrameLayout2) {
                dropDownPopupWindow.mRealContainerView = view3;
            } else {
                SmoothFrameLayout2 smoothFrameLayout2 = new SmoothFrameLayout2(dropDownPopupWindow.mContext);
                smoothFrameLayout2.setCornerRadius(dropDownPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_radius));
                smoothFrameLayout2.addView(dropDownPopupWindow.mContentView);
                dropDownPopupWindow.mRealContainerView = smoothFrameLayout2;
            }
            dropDownPopupWindow.mPopupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.5
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view4, MotionEvent motionEvent) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() != 0) {
                        return false;
                    }
                    if (x >= 0 && x < DropDownPopupWindow.this.mRealContainerView.getWidth() && y >= 0 && y < DropDownPopupWindow.this.mRealContainerView.getHeight()) {
                        return false;
                    }
                    DropDownPopupWindow.this.dismiss();
                    return true;
                }
            });
            ContainerController containerController = dropDownPopupWindow.mContainerController;
            if (containerController != null) {
                containerController.onShow();
            }
            dropDownPopupWindow.mPopupWindow.setWidth(i);
            dropDownPopupWindow.mPopupWindow.setHeight(-2);
            dropDownPopupWindow.mPopupWindow.setElevation(dropDownPopupWindow.mElevation);
            dropDownPopupWindow.mContainer.removeAllViews();
            dropDownPopupWindow.mContainer.addView(dropDownPopupWindow.mRealContainerView);
            View view4 = dropDownPopupWindow.mAnchorView;
            if (view4 != null && view4.isAttachedToWindow()) {
                dropDownPopupWindow.mPopupWindow.setHeight(-2);
                int[] iArrComputeLocation = dropDownPopupWindow.computeLocation(i, dropDownPopupWindow.getCutout(dropDownPopupWindow.mAnchorView));
                dropDownPopupWindow.mPopupWindow.showAtLocation(dropDownPopupWindow.mRealContainerView, 0, iArrComputeLocation[0], iArrComputeLocation[1]);
                view = dropDownPopupWindow.mAnchorView;
            } else {
                dropDownPopupWindow.mPopupWindow.showAtLocation(dropDownPopupWindow.mRealContainerView, 8388659, 0, 0);
                view = dropDownPopupWindow.mRealContainerView;
            }
            dropDownPopupWindow.mContainer.setElevation(0.0f);
            if (view != null) {
                HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_BUTTON_SMALL, HapticFeedbackConstants.MIUI_POPUP_NORMAL);
            }
        }
        View view5 = dropDownPopupWindow.mRealContainerView;
        if (view5 != null) {
            dropDownPopupWindow.changeWindowBackground(view5.getRootView(), ViewUtils.isNightMode(dropDownPopupWindow.mContext) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
        }
    }

    private void prepareWindowElevation(final View view, int i) {
        if (MiShadowUtils.SUPPORT_MI_SHADOW) {
            float f = view.getContext().getResources().getDisplayMetrics().density;
            MiShadowUtils.setMiShadow(view, this.mShadowColor, 0.0f * f, f * 26.0f, this.mElevation);
        } else {
            view.setElevation(i);
            view.setOutlineProvider(new ViewOutlineProvider() { // from class: miuix.popupwidget.widget.DropDownPopupWindow.6
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view2, Outline outline) {
                    if (view2.getWidth() == 0 || view2.getHeight() == 0) {
                        return;
                    }
                    outline.setAlpha(0.3f);
                    if (view.getBackground() != null) {
                        view.getBackground().getOutline(outline);
                    }
                }
            });
            view.setOutlineSpotShadowColor(this.mContext.getColor(R.color.miuix_appcompat_drop_down_menu_spot_shadow_color));
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0048 A[PHI: r5
  0x0048: PHI (r5v3 int) = (r5v2 int), (r5v5 int) binds: [B:40:0x0078, B:25:0x0046] A[DONT_GENERATE, DONT_INLINE]] */
    private int[] computeLocation(int i, Rect rect) {
        int i2;
        boolean z;
        int width;
        int i3;
        int i4;
        int[] iArr = new int[2];
        this.mAnchorView.getLocationInWindow(iArr);
        int i5 = this.mMaxWidth;
        if (i > i5) {
            i = i5;
        }
        int i6 = iArr[1];
        if (i == i5) {
            if (rect.left > 0) {
                i3 = rect.left;
            } else {
                i3 = this.mEdgeDistance;
            }
        } else if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            width = this.mWindowWidth;
            int i7 = iArr[0];
            int i8 = width - (i7 + i);
            i2 = this.mEdgeDistance;
            boolean z2 = i8 < i2;
            z = i7 < i2;
            if (!z && z2) {
                i += i2;
                i3 = width - i;
            } else if (z2 || !z) {
                i3 = i7;
            } else {
                i3 = i2;
            }
        } else {
            boolean z3 = (iArr[0] + this.mAnchorView.getWidth()) - i < this.mEdgeDistance;
            int width2 = this.mWindowWidth - (iArr[0] + this.mAnchorView.getWidth());
            i2 = this.mEdgeDistance;
            z = width2 < i2;
            if (!z3 && z) {
                width = this.mWindowWidth;
                i += i2;
            } else if (z || !z3) {
                width = iArr[0] + this.mAnchorView.getWidth();
            } else {
                i3 = i2;
            }
            i3 = width - i;
        }
        int i9 = this.mWindowHeight;
        int i10 = this.mContentHeight;
        int i11 = (i9 - i6) - i10;
        int i12 = this.mBottomEdge;
        if (i11 < i12 && (i6 = i6 - (i12 - ((i9 - i6) - i10))) < (i4 = this.mTopEdge)) {
            i6 = i4;
        }
        return new int[]{i3, i6};
    }

    public void changeWindowBackground(View view, float f) {
        if (view != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.flags |= 2;
            layoutParams.dimAmount = f;
            ((WindowManager) view.getContext().getSystemService("window")).updateViewLayout(view, layoutParams);
            return;
        }
        Log.w(TAG, "can't change window dim with null view");
    }

    public int setupContentView(FrameLayout frameLayout, View view, int i, int i2, ContainerController containerController) {
        int iMeasureListViewWidth;
        if (view == null) {
            return -2;
        }
        if (view instanceof ListView) {
            iMeasureListViewWidth = measureListViewWidth((ListView) view);
        } else {
            view.measure(0, 0);
            int measuredWidth = view.getMeasuredWidth();
            this.mContentHeight = view.getMeasuredHeight();
            iMeasureListViewWidth = measuredWidth;
        }
        return iMeasureListViewWidth < i2 ? i2 : iMeasureListViewWidth;
    }

    private int measureListViewWidth(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = adapter.getCount();
        int measuredHeight = 0;
        int i = 0;
        int i2 = 0;
        View view = null;
        for (int i3 = 0; i3 < count; i3++) {
            int itemViewType = adapter.getItemViewType(i3);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = adapter.getView(i3, view, listView);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            measuredHeight += view.getMeasuredHeight();
            if (measuredWidth > i) {
                i = measuredWidth;
            }
        }
        this.mContentHeight = measuredHeight;
        return i;
    }

    private void startAnimation(float f, float f2, int i) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (this.mContainerController == null && this.mContentController == null) {
            return;
        }
        ValueAnimator valueAnimator2 = this.mAnimator;
        if (valueAnimator2 == null) {
            this.mAnimator = ValueAnimator.ofFloat(f, f2);
        } else {
            valueAnimator2.setFloatValues(f, f2);
        }
        this.mAnimator.setDuration(DeviceHelper.isFeatureWholeAnim() ? i : 0L);
        this.mAnimator.addUpdateListener(this.mValueUpdateListener);
        this.mAnimator.addListener(this.mAnimatorListener);
        this.mAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realDismiss() {
        android.widget.PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        ContainerController containerController = this.mContainerController;
        if (containerController != null) {
            containerController.onDismiss();
        }
        ContentController contentController = this.mContentController;
        if (contentController != null) {
            contentController.onDismiss();
        }
        Controller controller = this.mDropDownController;
        if (controller != null) {
            controller.onDismiss();
        }
        this.mDismissPending = false;
    }

    public void dismiss() {
        this.mDismissPending = true;
        realDismiss();
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mPopupWindow.setOnDismissListener(onDismissListener);
    }
}
