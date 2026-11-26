package miuix.appcompat.internal.app.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.IStateStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.R;
import miuix.appcompat.app.ActionBarTransitionListener;
import miuix.appcompat.app.TextViewDrawableConfig;
import miuix.appcompat.internal.app.NavigatorSwitchPresenter;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.util.ActionBarViewFactory;
import miuix.appcompat.internal.view.ActionBarPolicy;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuPresenter;
import miuix.appcompat.internal.view.menu.MenuView;
import miuix.appcompat.internal.view.menu.SubMenuBuilder;
import miuix.appcompat.internal.view.menu.action.ActionMenuItem;
import miuix.appcompat.internal.view.menu.action.ActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.ActionMenuView;
import miuix.appcompat.internal.view.menu.action.EndActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.HyperActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.HyperSplitActionMenuPresenter;
import miuix.appcompat.internal.view.menu.action.ResponsiveActionMenuView;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.ViewUtils;
import miuix.view.ActionModeAnimationListener;

/* JADX INFO: loaded from: classes5.dex */
public class ActionBarView extends AbsActionBarView implements ActionModeAnimationListener {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 8223;
    private static final int ICON_INITIALIZED = 1;
    private static final int LOGO_INITIALIZED = 2;
    private static final String TAG = "ActionBarView";
    private static final int TYPE_NON_TOUCH = 1;
    private static final int TYPE_TOUCH = 0;
    private boolean mAnimateStart;
    private boolean mApplyBgBlur;
    private OnBackInvokedCallback mBackInvokedCallback;
    private OnBackInvokedDispatcher mBackInvokedDispatcher;
    private TransitionListener mBottomMenuTransitionListener;
    private ActionBar.OnNavigationListener mCallback;
    protected TransitionListener mCollapseAnimHideConfigListener;
    protected TransitionListener mCollapseAnimShowConfigListener;
    private final AbsActionBarView.CollapseView mCollapseController;
    private FrameLayout mCollapseCustomContainer;
    private int mCollapseMainContainerHeight;
    private int mCollapseSecondaryTabHeight;
    private final int mCollapseSubtitleStyleRes;
    private FrameLayout mCollapseTabContainer;
    private ScrollingTabContainerView mCollapseTabs;
    private CollapseTitle mCollapseTitle;
    private boolean mCollapseTitleShowable;
    private int mCollapseTitleStyleRes;
    int mCollapseTotalHeight;
    private boolean mCollapsedTitleVisible;
    private Context mContext;
    private View mCustomNavView;
    private final TextWatcher mCustomTitleWatcher;
    private float mDensity;
    private int mDisplayOptions;
    private boolean mDoContainerShowAnimInFinishActionMode;
    private int mEndActionMenuItemLimit;
    private ActionMenuPresenter mEndActionMenuPresenter;
    private MenuBuilder mEndMenu;
    private ActionMenuView mEndMenuView;
    private View mEndView;
    private int mExpandSubtitlePaddingBottom;
    private int mExpandTabTopPadding;
    private ScrollingTabContainerView mExpandTabs;
    private ExpandTitle mExpandTitle;
    private int mExpandTitlePaddingBottom;
    private boolean mExpandTitleVisible;
    int mExpandTotalHeight;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener;
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mExtraPadding;
    private ExtraPaddingPolicy mExtraPaddingPolicy;
    private final Handler mHandler;
    private boolean mHasNavigatorSwitchView;
    private boolean mHasStartView;
    private Drawable mHomeAsUpIndicatorDrawable;
    private int mHomeAsUpIndicatorResId;
    private HomeView mHomeLayout;
    private final int mHomeResId;
    private Drawable mIcon;
    private int mIconLogoInitIndicator;
    private boolean mInActionMode;
    private boolean mInActionModeAnimating;
    private boolean mInSearchMode;
    private ProgressBar mIndeterminateProgressView;
    private CharSequence mInitCustomTitle;
    private boolean mIsBottomMenuVisible;
    private boolean mIsCollapseTitleShowingOnResizing;
    private boolean mIsCollapsed;
    private int mItemPadding;
    private float mLastResizingProcess;
    private LifecycleOwner mLifecycleOwner;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private FrameLayout mMainContainer;
    private AnimConfig mMenuAnimConfig;
    protected TransitionListener mMovableAlphaShowListener;
    protected TransitionListener mMovableAnimAlphaListener;
    private final AbsActionBarView.CollapseView mMovableController;
    private FrameLayout mMovableMainContainer;
    private int mMovableSecondaryTabHeight;
    private FrameLayout mMovableTabContainer;
    private final AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private View mNavigatorSwitch;
    private final int mNavigatorSwitchResId;
    private boolean mNeedRequestLayoutOnExpandTitleShowing;
    private boolean mNonTouchScrolling;
    private boolean mOptionalIconsVisible;
    private MenuBuilder mOptionsMenu;
    private boolean mPendingCreated;
    private int mPendingHeight;
    private final Runnable mPostAnimationRunnable;
    private Runnable mPostScroll;
    private final Scroller mPostScroller;
    private int mProgressBarPadding;
    private ProgressBar mProgressView;
    private Runnable mScheduleBottomMenuRunnable;
    private SecondaryTabBar mSecondaryCollapseTabs;
    private SecondaryTabBar mSecondaryExpandTabs;
    private int mSecondaryTabVerticalPadding;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private View mStartView;
    private IStateStyle mStateChangeAnimStateStyle;
    private final View.OnClickListener mSubTitleClickListener;
    private CharSequence mSubtitle;
    private boolean mTabsExit;
    private boolean mTempResizable;
    private CharSequence mTitle;
    private boolean mTitleCenter;
    private final View.OnClickListener mTitleClickListener;
    private int mTitleGapPaddingStart;
    private ActionMenuItem mTitleNavItem;
    private View mTitleUpView;
    private int mTitleUpViewMarginEnd;
    private int mTitleUpViewMarginStart;
    private boolean mTouchScrolling;
    private int mTransitionTarget;
    private int mUncollapsePaddingH;
    private int mUncollapseTabPaddingH;
    private final View.OnClickListener mUpClickListener;
    private boolean mUserSetEndActionMenuItemLimit;
    private boolean mUserTitle;
    Window.Callback mWindowCallback;

    private int normalizeHorizontalGravity(int i, boolean z) {
        int i2 = 8388615 & i;
        if ((i & 8388608) == 0) {
            if (i2 == 3) {
                return z ? GravityCompat.END : GravityCompat.START;
            }
            if (i2 == 5) {
                return z ? GravityCompat.START : GravityCompat.END;
            }
        }
        return i2;
    }

    public void setCollapsable(boolean z) {
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void animateToVisibility(int i) {
        super.animateToVisibility(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void dismissPopupMenus() {
        super.dismissPopupMenus();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getActionMenuView() {
        return super.getActionMenuView();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getExpandState() {
        return super.getExpandState();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getMenuView() {
        return super.getMenuView();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean hideOverflowMenu() {
        return super.hideOverflowMenu();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowing() {
        return super.isOverflowMenuShowing();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isResizable() {
        return super.isResizable();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isUserSetExpandState() {
        return super.isUserSetExpandState();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setBottomMenuMode(int i) {
        super.setBottomMenuMode(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setExpandState(int i) {
        super.setExpandState(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setPendingInset(Rect rect) {
        super.setPendingInset(rect);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setResizable(boolean z) {
        super.setResizable(z);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitView(ActionBarContainer actionBarContainer) {
        super.setSplitView(actionBarContainer);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitWhenNarrow(boolean z) {
        super.setSplitWhenNarrow(z);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean showOverflowMenu() {
        return super.showOverflowMenu();
    }

    public ActionBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayOptions = -1;
        this.mLifecycleOwner = null;
        this.mDoContainerShowAnimInFinishActionMode = false;
        this.mIsBottomMenuVisible = true;
        this.mPendingCreated = false;
        this.mCollapsedTitleVisible = true;
        this.mExpandTitleVisible = true;
        this.mHasStartView = false;
        this.mHasNavigatorSwitchView = false;
        this.mApplyBgBlur = false;
        this.mCollapseTitleShowable = true;
        this.mLastResizingProcess = 0.0f;
        this.mNeedRequestLayoutOnExpandTitleShowing = false;
        this.mHandler = new Handler(Looper.myLooper());
        this.mPostAnimationRunnable = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarView.this.mPostScroll != null) {
                    ActionBarView actionBarView = ActionBarView.this;
                    actionBarView.postOnAnimation(actionBarView.mPostScroll);
                }
            }
        };
        this.mCollapseAnimShowConfigListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                if (ActionBarView.this.mCollapseController != null) {
                    ActionBarView.this.mCollapseController.onShow();
                }
            }
        };
        this.mCollapseAnimHideConfigListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.3
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mCollapseController != null) {
                    ActionBarView.this.mCollapseController.onHide();
                }
            }
        };
        this.mMovableAlphaShowListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                super.onBegin(obj, collection);
                if (ActionBarView.this.mMovableMainContainer == null || ActionBarView.this.mMovableMainContainer.getVisibility() == 0) {
                    return;
                }
                ActionBarView.this.mMovableController.setVisibility(0);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
                if (ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing) {
                    ActionBarView.this.requestLayout();
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing) {
                    ActionBarView.this.requestLayout();
                }
                ActionBarView.this.mNeedRequestLayoutOnExpandTitleShowing = false;
            }
        };
        this.mMovableAnimAlphaListener = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.5
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                super.onBegin(obj, collection);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ActionBarView.this.mMovableMainContainer.getAlpha() != 0.0f) {
                    if (ActionBarView.this.mMovableMainContainer.getVisibility() != 0) {
                        ActionBarView.this.mMovableController.setVisibility(0);
                        return;
                    }
                    return;
                }
                int i = ActionBarView.this.mInnerExpandState;
                if (i != 0) {
                    if (i == 2 && ActionBarView.this.mMovableMainContainer.getVisibility() != 4) {
                        ActionBarView.this.mMovableController.setVisibility(4);
                        return;
                    }
                    return;
                }
                if (ActionBarView.this.mMovableMainContainer.getVisibility() != 8) {
                    ActionBarView.this.mMovableController.setVisibility(8);
                }
            }
        };
        this.mNavItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (ActionBarView.this.mCallback != null) {
                    ActionBarView.this.mCallback.onNavigationItemSelected(i, j);
                }
            }
        };
        this.mExpandedActionViewUpListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MenuItemImpl menuItemImpl = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.collapseActionView();
                }
            }
        };
        this.mUpClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mLogoNavItem);
            }
        };
        this.mTitleClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mTitleNavItem);
            }
        };
        this.mSubTitleClickListener = new View.OnClickListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ActionBarView.this.mUserSubTitleClickListener != null) {
                    ActionBarView.this.mUserSubTitleClickListener.onClick(view);
                }
            }
        };
        this.mCustomTitleWatcher = new TextWatcher() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.11
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!charSequence.equals(ActionBarView.this.mTitle)) {
                    ActionBarView.this.mInitCustomTitle = charSequence;
                }
                if (ActionBarView.this.mExpandTitle != null) {
                    ActionBarView.this.mExpandTitle.setTitle(charSequence);
                }
            }
        };
        this.mIsCollapseTitleShowingOnResizing = false;
        this.mTransitionTarget = 0;
        AbsActionBarView.CollapseView collapseView = new AbsActionBarView.CollapseView();
        this.mCollapseController = collapseView;
        AbsActionBarView.CollapseView collapseView2 = new AbsActionBarView.CollapseView();
        this.mMovableController = collapseView2;
        this.mTouchScrolling = false;
        this.mNonTouchScrolling = false;
        this.mInActionMode = false;
        this.mInSearchMode = false;
        this.mInActionModeAnimating = false;
        this.mStateChangeAnimStateStyle = null;
        this.mPostScroll = new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.15
            @Override // java.lang.Runnable
            public void run() {
                if (ActionBarView.this.mPostScroller.computeScrollOffset()) {
                    ActionBarView actionBarView = ActionBarView.this;
                    actionBarView.mPendingHeight = (actionBarView.mPostScroller.getCurrY() - ActionBarView.this.mCollapseTotalHeight) + ActionBarView.this.mCollapseSecondaryTabHeight;
                    ActionBarView.this.requestLayout();
                    if (ActionBarView.this.mPostScroller.isFinished()) {
                        if (ActionBarView.this.mPostScroller.getCurrY() != ActionBarView.this.mCollapseTotalHeight) {
                            if (ActionBarView.this.mPostScroller.getCurrY() == ActionBarView.this.mCollapseTotalHeight + ActionBarView.this.mMovableMainContainer.getMeasuredHeight()) {
                                ActionBarView.this.setExpandState(1);
                                return;
                            }
                            return;
                        }
                        ActionBarView.this.setExpandState(0);
                        return;
                    }
                    ActionBarView.this.postOnAnimation(this);
                }
            }
        };
        this.mContext = context;
        this.mPostScroller = new Scroller(context);
        this.mInActionMode = false;
        this.mInSearchMode = false;
        Resources resources = this.mContext.getResources();
        this.mDensity = resources.getDisplayMetrics().density;
        this.mUncollapsePaddingH = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_horizontal_padding);
        this.mUncollapseTabPaddingH = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_tab_horizontal_padding);
        this.mExpandTabTopPadding = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_top_padding);
        this.mExpandTitlePaddingBottom = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_bottom_padding);
        this.mExpandSubtitlePaddingBottom = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_subtitle_bottom_padding);
        this.mSecondaryTabVerticalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_secondary_tab_vertical_padding);
        this.mTitleUpViewMarginStart = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_up_view_margin_start);
        this.mTitleUpViewMarginEnd = 0;
        this.mTitleGapPaddingStart = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_padding_gap);
        this.mMovableAnimShowConfig.addListeners(this.mMovableAlphaShowListener);
        this.mMovableAnimNormalConfig.addListeners(this.mMovableAnimAlphaListener);
        this.mCollapseAnimShowConfig.addListeners(this.mCollapseAnimShowConfigListener);
        this.mCollapseAnimHideConfig.addListeners(this.mCollapseAnimHideConfigListener);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mMainContainer = frameLayout;
        frameLayout.setId(R.id.action_bar_collapse_container);
        this.mMainContainer.setForegroundGravity(17);
        this.mMainContainer.setVisibility(0);
        this.mMainContainer.setAlpha(this.mInnerExpandState == 0 ? 1.0f : 0.0f);
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.mMovableMainContainer = frameLayout2;
        frameLayout2.setId(R.id.action_bar_movable_container);
        FrameLayout frameLayout3 = this.mMovableMainContainer;
        int i = this.mUncollapsePaddingH;
        frameLayout3.setPaddingRelative(i, this.mExpandTabTopPadding, i, this.mExpandTitlePaddingBottom);
        this.mMovableMainContainer.setVisibility(0);
        this.mMovableMainContainer.setAlpha(this.mInnerExpandState != 0 ? 1.0f : 0.0f);
        collapseView.attachViews(this.mMainContainer);
        collapseView2.attachViews(this.mMovableMainContainer);
        setBackgroundResource(0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar, android.R.attr.actionBarStyle, 0);
        this.mNavigationMode = typedArrayObtainStyledAttributes.getInt(R.styleable.ActionBar_android_navigationMode, 0);
        this.mTitle = typedArrayObtainStyledAttributes.getText(R.styleable.ActionBar_android_title);
        this.mSubtitle = typedArrayObtainStyledAttributes.getText(R.styleable.ActionBar_android_subtitle);
        this.mTitleCenter = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ActionBar_titleCenter, false);
        this.mLogo = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_android_logo);
        this.mIcon = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_android_icon);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.mNavigatorSwitchResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_navigatorSwitchLayout, R.layout.miuix_appcompat_action_bar_navigator_switch);
        this.mHomeResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_android_homeLayout, R.layout.miuix_appcompat_action_bar_home);
        this.mCollapseTitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_android_titleTextStyle, 0);
        this.mCollapseSubtitleStyleRes = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_android_subtitleTextStyle, 0);
        this.mProgressBarPadding = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_android_progressBarPadding, 0);
        this.mItemPadding = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_android_itemPadding, 0);
        setDisplayOptions(typedArrayObtainStyledAttributes.getInt(R.styleable.ActionBar_android_displayOptions, 0));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_android_customNavigationLayout, 0);
        if (resourceId != 0) {
            View viewInflate = layoutInflaterFrom.inflate(resourceId, (ViewGroup) this, false);
            this.mCustomNavView = viewInflate;
            viewInflate.setLayoutParams(new ActionBar.LayoutParams(-1, -2, DEFAULT_CUSTOM_GRAVITY));
            this.mNavigationMode = 0;
        }
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_android_minHeight, 0);
        this.mTitleMaxHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_android_maxHeight, 0);
        this.mTitleMaxHeight = (AttributeResolver.resolveBoolean(this.mContext, R.attr.actionBarTitleAdaptLargeFont, true) && (MiuixUIUtils.getFontLevel(this.mContext) == 2)) ? typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_actionBarMaxSizeInLargeFont, this.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_bar_large_font_max_height)) : this.mTitleMaxHeight;
        this.mOptionalIconsVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ActionBar_showOptionIcons, false);
        typedArrayObtainStyledAttributes.recycle();
        this.mLogoNavItem = new ActionMenuItem(context, 0, android.R.id.home, 0, 0, this.mTitle);
        this.mTitleNavItem = new ActionMenuItem(context, 0, android.R.id.title, 0, 0, this.mTitle);
        postRefreshTitleControllerStatus();
    }

    private void postRefreshTitleControllerStatus() {
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4248xf20a4d16();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$postRefreshTitleControllerStatus$0$miuix-appcompat-internal-app-widget-ActionBarView, reason: not valid java name */
    /* synthetic */ void m4248xf20a4d16() {
        if (this.mInnerExpandState == 0) {
            this.mCollapseController.setAnimFrom(1.0f, 0, 0, true);
            this.mMovableController.setAnimFrom(0.0f, 0, 0, true);
        } else if (this.mInnerExpandState == 1) {
            this.mCollapseController.setAnimFrom(0.0f, 0, 20, true);
            this.mMovableController.setAnimFrom(1.0f, 0, 0, true);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, getActionBarStyle(), 0);
        this.mTitleMinHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_android_minHeight, 0);
        this.mTitleMaxHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_android_maxHeight, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_actionBarMaxSizeInLargeFont, this.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_bar_large_font_max_height));
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(this.mContext, R.attr.actionBarTitleAdaptLargeFont, true);
        boolean z = MiuixUIUtils.getFontLevel(this.mContext) == 2;
        if (!zResolveBoolean || !z) {
            dimensionPixelSize = this.mTitleMaxHeight;
        }
        this.mTitleMaxHeight = dimensionPixelSize;
        typedArrayObtainStyledAttributes.recycle();
        Configuration configuration2 = getResources().getConfiguration();
        this.mCollapseTitleShowable = true;
        updateTightTitle();
        if ((getDisplayOptions() & 8) != 0) {
            CollapseTitle collapseTitle = this.mCollapseTitle;
            if (collapseTitle != null) {
                collapseTitle.onConfigurationChanged(configuration2);
            }
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                expandTitle.onConfigurationChanged(configuration2);
            }
        }
        Resources resources = this.mContext.getResources();
        float f = resources.getDisplayMetrics().density;
        if (f != this.mDensity) {
            this.mDensity = f;
            this.mUncollapseTabPaddingH = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_tab_horizontal_padding);
            this.mExpandTabTopPadding = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_top_padding);
            this.mExpandTitlePaddingBottom = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_bottom_padding);
            this.mExpandSubtitlePaddingBottom = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_subtitle_bottom_padding);
            this.mTitleUpViewMarginStart = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_up_view_margin_start);
            this.mTitleUpViewMarginEnd = 0;
            this.mTitleGapPaddingStart = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_padding_gap);
        }
        this.mUncollapsePaddingH = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_horizontal_padding);
        this.mMovableMainContainer.setPaddingRelative(this.mUncollapsePaddingH, getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_top_padding), this.mUncollapsePaddingH, TextUtils.isEmpty(this.mSubtitle) ? this.mExpandTitlePaddingBottom : this.mExpandSubtitlePaddingBottom);
        this.mSecondaryTabVerticalPadding = resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_secondary_tab_vertical_padding);
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), this.mCollapseTabContainer.getPaddingTop(), this.mCollapseTabContainer.getPaddingEnd(), this.mSecondaryTabVerticalPadding);
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            frameLayout2.setPaddingRelative(frameLayout2.getPaddingStart(), this.mMovableTabContainer.getPaddingTop(), this.mMovableTabContainer.getPaddingEnd(), this.mSecondaryTabVerticalPadding);
        }
        setPaddingRelative(AttributeResolver.resolveDimensionPixelSize(getContext(), R.attr.actionBarPaddingStart), getPaddingTop(), AttributeResolver.resolveDimensionPixelSize(getContext(), R.attr.actionBarPaddingEnd), getPaddingBottom());
        if (this.mTabsExit) {
            updateTabsLayoutParams();
        }
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4247xcd586da9();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onConfigurationChanged$1$miuix-appcompat-internal-app-widget-ActionBarView, reason: not valid java name */
    /* synthetic */ void m4247xcd586da9() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null || !actionMenuPresenter.isOverflowMenuShowing()) {
            return;
        }
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner != null ? lifecycleOwner.getLifecycle().getState().equals(Lifecycle.State.RESUMED) : true) {
            return;
        }
        this.mEndActionMenuPresenter.hideOverflowMenu(false);
    }

    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackInvokedCallbackState() {
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher();
            boolean z = hasExpandedActionView() && onBackInvokedDispatcherFindOnBackInvokedDispatcher != null && ViewCompat.isAttachedToWindow(this);
            if (z && this.mBackInvokedDispatcher == null) {
                if (this.mBackInvokedCallback == null) {
                    this.mBackInvokedCallback = new OnBackInvokedCallback() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda3
                        @Override // android.window.OnBackInvokedCallback
                        public final void onBackInvoked() {
                            this.f$0.collapseActionView();
                        }
                    };
                }
                onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, this.mBackInvokedCallback);
                this.mBackInvokedDispatcher = onBackInvokedDispatcherFindOnBackInvokedDispatcher;
                return;
            }
            if (z || (onBackInvokedDispatcher = this.mBackInvokedDispatcher) == null) {
                return;
            }
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackInvokedCallback);
            this.mBackInvokedDispatcher = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mCollapseController.onAttachedToWindow();
        this.mMovableController.onAttachedToWindow();
        updateBackInvokedCallbackState();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu(false);
            this.mActionMenuPresenter.hideSubMenus();
        }
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu(false);
            this.mEndActionMenuPresenter.hideSubMenus();
        }
        this.mCollapseController.onDetachedFromWindow();
        this.mMovableController.onDetachedFromWindow();
        updateBackInvokedCallbackState();
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public View getTitleView(int i) {
        if (i == 0) {
            return findViewById(R.id.action_bar_title);
        }
        if (i != 1) {
            return null;
        }
        return findViewById(R.id.action_bar_title_expand);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public View getSubTitleView(int i) {
        if (i == 0) {
            return findViewById(R.id.action_bar_subtitle);
        }
        if (i != 1) {
            return null;
        }
        return findViewById(R.id.action_bar_subtitle_expand);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setTitleClickable(boolean z) {
        super.setTitleClickable(z);
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setTitleClickable(z);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setTitleClickable(z);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setSubTitleClickListener(View.OnClickListener onClickListener) {
        super.setSubTitleClickListener(onClickListener);
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleClickable(onClickListener != null);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setSubTitleClickable(onClickListener != null);
        }
    }

    public void initIndeterminateProgress() {
        ProgressBar progressBar = new ProgressBar(this.mContext, null, R.attr.actionBarIndeterminateProgressStyle);
        this.mIndeterminateProgressView = progressBar;
        progressBar.setId(R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        this.mIndeterminateProgressView.setIndeterminate(true);
        addView(this.mIndeterminateProgressView);
    }

    public void setEndActionMenuEnable(boolean z) {
        this.mEndActionMenuEnable = z;
    }

    public void setHyperActionMenuEnable(boolean z) {
        this.mHyperActionMenuEnable = z;
    }

    public void setHyperSplitMenuEnabled(boolean z) {
        this.mHyperSplitMenuEnabled = z;
    }

    public boolean isEndActionMenuEnable() {
        return this.mEndActionMenuEnable;
    }

    public boolean isHyperActionMenuEnable() {
        return this.mHyperActionMenuEnable;
    }

    public boolean isHyperSplitMenuEnabled() {
        return this.mHyperSplitMenuEnabled;
    }

    boolean updateExpandStateOnLayout() {
        if (this.mInnerExpandState != 2) {
            return false;
        }
        int i = this.mExpandStateOnLayout;
        int i2 = this.mPendingHeight;
        if (i2 == 0) {
            i = 0;
        } else if (i2 == this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight) {
            i = 1;
        }
        if (this.mExpandStateOnLayout == i) {
            return false;
        }
        this.mExpandStateOnLayout = i;
        this.mExpandStateBeforeResizing = this.mExpandStateOnLayout;
        return true;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setSplitActionBar(boolean z) {
        if (this.mSplitActionBarEnable != z) {
            if (this.mMenuView != null) {
                removeMenuViewFromOldParent(this.mMenuView);
                if (z) {
                    if (this.mSplitView != null) {
                        this.mSplitView.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            if (this.mSplitView != null) {
                this.mSplitView.setVisibility(z ? 0 : 8);
            }
            if (this.mActionMenuPresenter != null) {
                if (!z) {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.abc_action_bar_expanded_action_views_exclusive));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                }
            }
            super.setSplitActionBar(z);
        }
    }

    public boolean isSplitActionBar() {
        return this.mSplitActionBarEnable;
    }

    public int getSplitActionBarHeight(boolean z) {
        if (z) {
            if (this.mSplitView != null) {
                return this.mSplitView.getSplitCollapsedHeight();
            }
            return 0;
        }
        if (this.mSplitActionBarEnable) {
            return this.mSplitView.getHeight();
        }
        return 0;
    }

    public boolean isTightTitleWithEmbeddedTabs() {
        return this.mTabsExit && ActionBarPolicy.get(this.mContext).isTightTitle();
    }

    public int getCollapsedHeight() {
        return this.mCollapseTotalHeight;
    }

    public int getExpandedHeight() {
        return this.mExpandTotalHeight;
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, SecondaryTabBar secondaryTabBar, SecondaryTabBar secondaryTabBar2) {
        boolean z = scrollingTabContainerView != null;
        this.mTabsExit = z;
        if (z) {
            setupTabView(scrollingTabContainerView, scrollingTabContainerView2, secondaryTabBar, secondaryTabBar2);
            if (this.mNavigationMode == 2) {
                addTabsContainer();
            }
        }
    }

    public void checkTabsAdded() {
        if (this.mTabsExit && this.mNavigationMode == 2 && this.mCollapseTabs.getParent() == null) {
            addTabsContainer();
        }
    }

    private void setupTabView(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, SecondaryTabBar secondaryTabBar, SecondaryTabBar secondaryTabBar2) {
        this.mCollapseTabs = scrollingTabContainerView;
        this.mExpandTabs = scrollingTabContainerView2;
        this.mSecondaryCollapseTabs = secondaryTabBar;
        this.mSecondaryExpandTabs = secondaryTabBar2;
        if (secondaryTabBar != null) {
            secondaryTabBar.setParentBlurEnabled(this.mApplyBgBlur);
        }
        SecondaryTabBar secondaryTabBar3 = this.mSecondaryExpandTabs;
        if (secondaryTabBar3 != null) {
            secondaryTabBar3.setParentBlurEnabled(this.mApplyBgBlur);
        }
    }

    private void addTabsContainer() {
        FrameLayout frameLayout;
        View view;
        View layout = null;
        if (this.mInnerExpandState == 1) {
            frameLayout = this.mMovableMainContainer;
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                layout = expandTitle.getLayout();
            }
        } else {
            frameLayout = this.mMainContainer;
            CollapseTitle collapseTitle = this.mCollapseTitle;
            if (collapseTitle != null) {
                layout = collapseTitle.getLayout();
            }
        }
        boolean z = (!((this.mDisplayOptions & 16) != 0) || (view = this.mCustomNavView) == null || getCustomTitleView((FrameLayout) view.findViewById(R.id.action_bar_expand_container)) == null) ? false : true;
        boolean z2 = ((this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) ? false : true;
        if ((frameLayout.getChildCount() == 0 && !z) || !z2) {
            addTabsToMainContainers();
        } else if (z) {
            addSecondaryTabsToCollapseTabContainers();
            addSecondaryTabsToExpandTabContainers();
        } else if (layout != null && layout.getParent() == frameLayout) {
            if (ActionBarPolicy.get(this.mContext).isTightTitle() || hasTabsInContainer(frameLayout)) {
                addTabsToMainContainers();
            } else {
                addSecondaryTabsToCollapseTabContainers();
                addSecondaryTabsToExpandTabContainers();
            }
        }
        if (this.mMainContainer.getParent() != this) {
            safeAddView(this, this.mMainContainer);
        }
        if (this.mMovableMainContainer.getParent() != this) {
            safeAddView(this, this.mMovableMainContainer, 0);
        }
        updateTabsLayoutParams();
        updateTightTitle();
    }

    private void removeTabsFromContainer() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            if (frameLayout.getParent() != null) {
                removeView(this.mCollapseTabContainer);
                this.mCollapseController.detachView(this.mCollapseTabContainer);
            }
            this.mCollapseTabContainer.removeAllViews();
            this.mCollapseTabContainer = null;
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            if (frameLayout2.getParent() != null) {
                removeView(this.mMovableTabContainer);
                this.mMovableController.detachView(this.mMovableTabContainer);
            }
            this.mMovableTabContainer.removeAllViews();
            this.mMovableTabContainer = null;
        }
        SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
        if (secondaryTabBar != null && secondaryTabBar.asViewGroup().getParent() != null) {
            removeView(this.mSecondaryCollapseTabs.asViewGroup());
        }
        SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
        if (secondaryTabBar2 != null && secondaryTabBar2.asViewGroup().getParent() != null) {
            removeView(this.mSecondaryExpandTabs.asViewGroup());
        }
        if (!this.mPostScroller.isFinished()) {
            this.mPostScroller.forceFinished(true);
        }
        removeCallbacks(this.mPostScroll);
        setExpandState(this.mExpandState);
    }

    private void updateTabsLayoutParams() {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        ViewGroup.LayoutParams layoutParams3;
        ViewGroup.LayoutParams layoutParams4;
        ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
        if (scrollingTabContainerView != null && (layoutParams4 = scrollingTabContainerView.getLayoutParams()) != null) {
            layoutParams4.width = -2;
            layoutParams4.height = -1;
        }
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabs;
        if (scrollingTabContainerView2 != null && (layoutParams3 = scrollingTabContainerView2.getLayoutParams()) != null) {
            layoutParams3.width = -2;
            layoutParams3.height = -2;
        }
        SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
        if (secondaryTabBar != null && (layoutParams2 = secondaryTabBar.asViewGroup().getLayoutParams()) != null) {
            layoutParams2.width = -1;
            layoutParams2.height = -1;
        }
        SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
        if (secondaryTabBar2 == null || (layoutParams = secondaryTabBar2.asViewGroup().getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = -1;
        layoutParams.height = -1;
    }

    private void addTabsToMainContainers() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        if (frameLayout != null) {
            if (frameLayout.getParent() == this) {
                removeView(this.mCollapseTabContainer);
                this.mCollapseController.detachView(this.mCollapseTabContainer);
            }
            this.mCollapseTabContainer.removeAllViews();
            this.mCollapseTabContainer = null;
        }
        FrameLayout frameLayout2 = this.mMovableTabContainer;
        if (frameLayout2 != null) {
            if (frameLayout2.getParent() == this) {
                removeView(this.mMovableTabContainer);
                this.mMovableController.detachView(this.mMovableTabContainer);
            }
            this.mMovableTabContainer.removeAllViews();
            this.mMovableTabContainer = null;
        }
        this.mMainContainer.removeAllViews();
        ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.setVisibility(0);
            safeAddView(this.mMainContainer, this.mCollapseTabs);
        }
        this.mMovableMainContainer.removeAllViews();
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabs;
        if (scrollingTabContainerView2 != null) {
            scrollingTabContainerView2.setVisibility(0);
            safeAddView(this.mMovableMainContainer, this.mExpandTabs);
        }
        if (this.mInnerExpandState == 2) {
            setExpandState(this.mExpandState, false, false);
        }
    }

    private void addSecondaryTabsToCollapseTabContainers() {
        if (this.mSecondaryCollapseTabs != null) {
            FrameLayout frameLayout = this.mCollapseTabContainer;
            if (frameLayout == null) {
                this.mCollapseTabContainer = createSecondaryTabContainer(R.id.action_bar_collapse_tab_container);
                if (this.mInnerExpandState == 1) {
                    this.mCollapseTabContainer.setAlpha(0.0f);
                }
            } else {
                frameLayout.removeAllViews();
            }
            this.mCollapseTabContainer.addView(this.mSecondaryCollapseTabs.asViewGroup(), new FrameLayout.LayoutParams(-1, -2, 1));
            if (this.mCollapseTabContainer.getParent() == null) {
                addView(this.mCollapseTabContainer, new FrameLayout.LayoutParams(-1, -2));
                if (this.mInnerExpandState == 1) {
                    this.mCollapseTabContainer.setVisibility(8);
                }
                this.mCollapseController.attachViews(this.mCollapseTabContainer);
            }
        }
    }

    private void addSecondaryTabsToExpandTabContainers() {
        if (this.mSecondaryExpandTabs != null) {
            FrameLayout frameLayout = this.mMovableTabContainer;
            if (frameLayout == null) {
                this.mMovableTabContainer = createSecondaryTabContainer(R.id.action_bar_movable_tab_container);
                if (this.mInnerExpandState == 0) {
                    this.mMovableTabContainer.setAlpha(0.0f);
                }
            } else {
                frameLayout.removeAllViews();
            }
            this.mMovableTabContainer.addView(this.mSecondaryExpandTabs.asViewGroup(), new FrameLayout.LayoutParams(-1, -2, 1));
            if (this.mMovableTabContainer.getParent() == null) {
                addView(this.mMovableTabContainer, new FrameLayout.LayoutParams(-1, -2));
                if (this.mInnerExpandState == 0) {
                    this.mMovableTabContainer.setVisibility(8);
                }
                this.mMovableController.attachViews(this.mMovableTabContainer);
            }
        }
    }

    public void setCallback(ActionBar.OnNavigationListener onNavigationListener) {
        this.mCallback = onNavigationListener;
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        MenuBuilder menuBuilder;
        MenuBuilder menuBuilder2 = this.mOptionsMenu;
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        MenuBuilder menuBuilder3 = this.mEndMenu;
        if (menuBuilder3 != null) {
            menuBuilder3.removeMenuPresenter(this.mEndActionMenuPresenter);
        }
        removeMenuViewFromOldParent(this.mMenuView);
        removeMenuViewFromOldParent(this.mEndMenuView);
        if (menu == null || (!this.mSplitActionBarEnable && !this.mEndActionMenuEnable)) {
            this.mActionMenuPresenter = null;
            this.mEndActionMenuPresenter = null;
            this.mExpandedMenuPresenter = null;
            return;
        }
        if (this.mEndActionMenuEnable && this.mHyperActionMenuEnable) {
            Pair<MenuBuilder, MenuBuilder> pairDivideHyperMenuAndSplitMenu = divideHyperMenuAndSplitMenu(menu);
            this.mOptionsMenu = (MenuBuilder) pairDivideHyperMenuAndSplitMenu.first;
            this.mEndMenu = (MenuBuilder) pairDivideHyperMenuAndSplitMenu.second;
        } else {
            Pair<MenuBuilder, MenuBuilder> pairDivideMenuByGroup = divideMenuByGroup(menu);
            this.mOptionsMenu = (MenuBuilder) pairDivideMenuByGroup.first;
            this.mEndMenu = (MenuBuilder) pairDivideMenuByGroup.second;
        }
        if (this.mSplitActionBarEnable) {
            if (this.mActionMenuPresenter == null) {
                this.mActionMenuPresenter = createActionMenuPresenter(callback, this.mHyperSplitMenuEnabled);
                this.mExpandedMenuPresenter = createExpandedActionViewMenuPresenter();
            }
            MenuBuilder menuBuilder4 = this.mOptionsMenu;
            if (menuBuilder4 != null) {
                menuBuilder4.addMenuPresenter(this.mActionMenuPresenter);
                this.mOptionsMenu.addMenuPresenter(this.mExpandedMenuPresenter);
                this.mOptionsMenu.setForceShowOptionalIcon(this.mOptionalIconsVisible);
            } else {
                this.mActionMenuPresenter.initForMenu(this.mContext, null);
                this.mExpandedMenuPresenter.initForMenu(this.mContext, null);
            }
            this.mActionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
            addSplitMenuView();
        }
        if (this.mEndActionMenuEnable && (menuBuilder = this.mEndMenu) != null && menuBuilder.size() > 0) {
            if (this.mEndActionMenuPresenter == null) {
                this.mEndActionMenuPresenter = createEndActionMenuPresenter(callback, this.mHyperActionMenuEnable);
            }
            this.mEndMenu.addMenuPresenter(this.mEndActionMenuPresenter);
            this.mEndMenu.setForceShowOptionalIcon(this.mOptionalIconsVisible);
            this.mEndActionMenuPresenter.updateMenuView(true);
            addEndMenuView();
        }
        updateBadgeOnMenuItemViews();
        updateBackInvokedCallbackState();
    }

    public MenuBuilder getEndMenu() {
        return this.mEndMenu;
    }

    public void setBottomMenuCustomView(View view) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setBottomMenuCustomView(view);
        }
    }

    public void removeBottomMenuCustomView() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.removeBottomMenuCustomView();
        }
    }

    public void showBottomMenuCustomView() {
        if (this.mMenuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) this.mMenuView).showBottomMenuCustomView();
        }
    }

    public void hideBottomMenuCustomView() {
        if (this.mMenuView != null) {
            ((ResponsiveActionMenuView) this.mMenuView).hideBottomMenuCustomView();
        }
    }

    public void setBottomMenuCustomViewTranslationYWithPx(int i) {
        if (this.mMenuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) this.mMenuView).setBottomMenuCustomViewTranslationYWithPx(i);
        }
    }

    public int getBottomMenuCustomViewOffset() {
        if (this.mMenuView instanceof ResponsiveActionMenuView) {
            return ((ResponsiveActionMenuView) this.mMenuView).getBottomMenuCustomViewOffset();
        }
        return 0;
    }

    private void updateBadgeOnMenuItemViews() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.updateBadgeOnItemViews();
        ActionMenuPresenter actionMenuPresenter2 = this.mEndActionMenuPresenter;
        if (actionMenuPresenter2 instanceof EndActionMenuPresenter) {
            ((EndActionMenuPresenter) actionMenuPresenter2).updateBadgeOnMoreButton();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Pair<MenuBuilder, MenuBuilder> divideHyperMenuAndSplitMenu(Menu menu) {
        SubMenu subMenu;
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        MenuBuilder menuBuilder2 = new MenuBuilder(this.mContext);
        menuBuilder2.setCallback(menuBuilder.getCallback());
        ArrayList arrayList = new ArrayList();
        ArrayList<MenuItem> arrayList2 = new ArrayList<>();
        for (int size = menu.size() - 1; size >= 0; size--) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) menu.getItem(size);
            if (menuItemImpl.getGroupId() == R.id.miuix_split_action_menu_group) {
                menuBuilder.removeItemAt(size);
                SubMenu subMenu2 = menuItemImpl.getSubMenu();
                if (subMenu2 instanceof SubMenuBuilder) {
                    ((SubMenuBuilder) subMenu2).setParentMenu(menuBuilder2);
                }
                menuItemImpl.setMenu(menuBuilder2);
                arrayList.add(menuItemImpl);
            }
            if ((menuItemImpl.getGroupId() != R.id.miuix_split_action_menu_group || menuItemImpl.getItemId() != R.id.miuix_hyper_split_parent_item) && this.mHyperActionMenuEnable) {
                checkItemsVisibleIsExpected(menuItemImpl, arrayList2);
            }
        }
        if (this.mHyperActionMenuEnable) {
            correctItemsVisible(arrayList2);
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            menuBuilder2.add((MenuItemImpl) arrayList.get(size2));
        }
        int size3 = menuBuilder.size() - 1;
        while (true) {
            if (size3 < 0) {
                subMenu = null;
                break;
            }
            MenuItem item = menuBuilder.getItem(size3);
            if (item.getItemId() == R.id.miuix_hyper_split_parent_item) {
                menuBuilder.removeItemAt(size3);
                subMenu = item.getSubMenu();
                break;
            }
            size3--;
        }
        if (this.mHyperSplitMenuEnabled && subMenu != null) {
            ArrayList<MenuItem> arrayList3 = new ArrayList<>();
            for (int i = 0; i < subMenu.size(); i++) {
                MenuItem item2 = subMenu.getItem(i);
                if (item2 instanceof MenuItemImpl) {
                    menuBuilder2.add((MenuItemImpl) item2);
                }
                checkItemsVisibleIsExpected(item2, arrayList3);
            }
            subMenu.clear();
            correctItemsVisible(arrayList3);
        }
        return new Pair<>(menuBuilder2, menuBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Pair<MenuBuilder, MenuBuilder> divideMenuByGroup(Menu menu) {
        SubMenu subMenu;
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        MenuBuilder menuBuilder2 = new MenuBuilder(this.mContext);
        menuBuilder2.setCallback(menuBuilder.getCallback());
        ArrayList arrayList = new ArrayList();
        ArrayList<MenuItem> arrayList2 = new ArrayList<>();
        for (int size = menu.size() - 1; size >= 0; size--) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) menu.getItem(size);
            if (menuItemImpl.getGroupId() == R.id.miuix_action_end_menu_group) {
                menuBuilder.removeItemAt(size);
                SubMenu subMenu2 = menuItemImpl.getSubMenu();
                if (subMenu2 instanceof SubMenuBuilder) {
                    ((SubMenuBuilder) subMenu2).setParentMenu(menuBuilder2);
                }
                menuItemImpl.setMenu(menuBuilder2);
                arrayList.add(menuItemImpl);
            }
            if ((menuItemImpl.getGroupId() != R.id.miuix_split_action_menu_group || menuItemImpl.getItemId() != R.id.miuix_hyper_split_parent_item) && this.mHyperActionMenuEnable) {
                checkItemsVisibleIsExpected(menuItemImpl, arrayList2);
            }
        }
        if (this.mHyperActionMenuEnable) {
            correctItemsVisible(arrayList2);
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            menuBuilder2.add((MenuItemImpl) arrayList.get(size2));
        }
        int i = 0;
        while (true) {
            if (i >= menuBuilder.size()) {
                subMenu = null;
                break;
            }
            MenuItem item = menuBuilder.getItem(i);
            if (item.getItemId() == R.id.miuix_hyper_split_parent_item) {
                menuBuilder.removeItemAt(i);
                subMenu = item.getSubMenu();
                break;
            }
            i++;
        }
        if (this.mHyperSplitMenuEnabled && subMenu != null) {
            ArrayList<MenuItem> arrayList3 = new ArrayList<>();
            for (int i2 = 0; i2 < subMenu.size(); i2++) {
                MenuItem item2 = subMenu.getItem(i2);
                if (item2 instanceof MenuItemImpl) {
                    menuBuilder.add((MenuItemImpl) item2);
                }
                checkItemsVisibleIsExpected(item2, arrayList3);
            }
            subMenu.clear();
            correctItemsVisible(arrayList3);
        }
        return new Pair<>(menuBuilder, menuBuilder2);
    }

    private void checkItemsVisibleIsExpected(MenuItem menuItem, ArrayList<MenuItem> arrayList) {
        if (menuItem == null || !menuItem.hasSubMenu()) {
            return;
        }
        if (menuItem.isVisible() != hasAnySubMenuVisibleItem(menuItem.getSubMenu())) {
            arrayList.add(menuItem);
        }
    }

    private boolean hasAnySubMenuVisibleItem(SubMenu subMenu) {
        if (subMenu != null && subMenu.size() != 0) {
            for (int i = 0; i < subMenu.size(); i++) {
                if (subMenu.getItem(i).isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void correctItemsVisible(ArrayList<MenuItem> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            MenuItem menuItem = arrayList.get(i);
            if (menuItem != null) {
                menuItem.setVisible(!menuItem.isVisible());
            }
        }
    }

    private void addEndMenuView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
        this.mEndActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.abc_action_bar_expanded_action_views_exclusive));
        this.mEndActionMenuPresenter.setItemLimit(this.mEndActionMenuItemLimit);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = GravityCompat.END;
        ActionMenuView actionMenuView = (ActionMenuView) this.mEndActionMenuPresenter.getMenuView(this);
        ViewGroup viewGroup = (ViewGroup) actionMenuView.getParent();
        if (viewGroup != null && viewGroup != this) {
            viewGroup.removeView(actionMenuView);
        }
        addView(actionMenuView, layoutParams);
        this.mEndMenuView = actionMenuView;
    }

    protected void makeMenuViewShowHideWithAnimation(final boolean z) {
        int i;
        int i2;
        if (z == this.mIsBottomMenuVisible) {
            return;
        }
        if (this.mMenuView == null) {
            scheduleBottomMenuAnimation(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.12
                @Override // java.lang.Runnable
                public void run() {
                    ActionBarView.this.makeMenuViewShowHide(z);
                    if (ActionBarView.this.mMenuView != null) {
                        ActionBarView.this.mMenuView.setVisibility(0);
                    }
                }
            });
            return;
        }
        this.mIsBottomMenuVisible = z;
        this.mAnimateStart = false;
        if (this.mSplitActionBarEnable) {
            ActionMenuView actionMenuView = this.mMenuView;
            final ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) getParent().getParent();
            final int collapsedHeight = actionMenuView == null ? 0 : actionMenuView.getCollapsedHeight();
            if (z) {
                i2 = 0;
                i = collapsedHeight;
            } else {
                i = 0;
                i2 = collapsedHeight;
            }
            if (actionMenuView != null) {
                if (this.mMenuAnimConfig == null) {
                    this.mMenuAnimConfig = new AnimConfig().setEase(-2, 0.95f, 0.25f);
                }
                TransitionListener transitionListener = this.mBottomMenuTransitionListener;
                if (transitionListener != null) {
                    this.mMenuAnimConfig.removeListeners(transitionListener);
                }
                AnimConfig animConfig = this.mMenuAnimConfig;
                TransitionListener transitionListener2 = new TransitionListener() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.13
                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        if (ActionBarView.this.mAnimateStart) {
                            return;
                        }
                        ActionBarView.this.mAnimateStart = true;
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                        UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, View.TRANSLATION_Y.getName());
                        if (updateInfoFindByName == null) {
                            return;
                        }
                        actionBarOverlayLayout.onMenuStateChanged((int) (collapsedHeight - updateInfoFindByName.getFloatValue()), 0);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                        ActionBarView.this.mAnimateStart = false;
                    }
                };
                this.mBottomMenuTransitionListener = transitionListener2;
                animConfig.addListeners(transitionListener2);
                actionMenuView.setTranslationY(i);
                Folme.useAt(actionMenuView).state().to(new AnimState("menu_end_state").add(ViewProperty.TRANSLATION_Y, i2), this.mMenuAnimConfig);
                if (actionMenuView instanceof ResponsiveActionMenuView) {
                    ((ResponsiveActionMenuView) actionMenuView).setHidden(!this.mIsBottomMenuVisible);
                }
            }
        }
    }

    protected void makeMenuViewShowHide(final boolean z) {
        if (this.mSplitActionBarEnable && z != this.mIsBottomMenuVisible) {
            if (this.mMenuView == null) {
                scheduleBottomMenuAnimation(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.14
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarView.this.makeMenuViewShowHide(z);
                        if (ActionBarView.this.mMenuView != null) {
                            ActionBarView.this.mMenuView.setVisibility(0);
                        }
                    }
                });
                return;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.mSplitView.getParent();
            int collapsedHeight = this.mMenuView.getCollapsedHeight();
            this.mMenuView.setTranslationY(z ? 0.0f : collapsedHeight);
            if (!z) {
                collapsedHeight = 0;
            }
            actionBarOverlayLayout.animateContentMarginBottomByBottomMenu(collapsedHeight);
            this.mIsBottomMenuVisible = z;
            if (this.mMenuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) this.mMenuView).setHidden(!this.mIsBottomMenuVisible);
            }
        }
    }

    private void scheduleBottomMenuAnimation(Runnable runnable) {
        this.mScheduleBottomMenuRunnable = runnable;
    }

    private void addSplitMenuView() {
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        if (this.mMenuView != null && this.mScheduleBottomMenuRunnable != null) {
            this.mMenuView.setVisibility(4);
            this.mMenuView.post(this.mScheduleBottomMenuRunnable);
            this.mScheduleBottomMenuRunnable = null;
        }
        boolean z = this.mBottomMenuMode == 3;
        this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
        this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 1;
        if (z) {
            layoutParams.bottomMargin = MiuixUIUtils.dp2px(getContext(), 16.0f);
        }
        if (this.mPendingInset != null) {
            if (z) {
                layoutParams.bottomMargin += this.mPendingInset.bottom;
                ViewUtils.resetPaddingBottom(this.mMenuView, 0);
            } else {
                ViewUtils.resetPaddingBottom(this.mMenuView, this.mPendingInset.bottom);
            }
        }
        if (this.mSplitView != null) {
            removeMenuViewFromOldParent(this.mMenuView);
            this.mSplitView.onResidentActionMenuViewRemoved(this.mMenuView);
            if (this.mMenuView instanceof ResponsiveActionMenuView) {
                ResponsiveActionMenuView responsiveActionMenuView = (ResponsiveActionMenuView) this.mMenuView;
                responsiveActionMenuView.setSuspendEnabled(z);
                responsiveActionMenuView.setHidden(!this.mIsBottomMenuVisible);
            }
            this.mSplitView.addView(this.mMenuView, 0, layoutParams);
            this.mSplitView.onResidentActionMenuViewAdded(this.mMenuView);
            View viewFindViewById = this.mMenuView.findViewById(R.id.expanded_menu);
            if (viewFindViewById != null) {
                viewFindViewById.requestLayout();
            }
            requestLayout();
            return;
        }
        this.mMenuView.setLayoutParams(layoutParams);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void refreshBottomMenu() {
        if (!this.mSplitActionBarEnable || this.mActionMenuPresenter == null) {
            return;
        }
        addSplitMenuView();
    }

    private void removeMenuViewFromOldParent(View view) {
        ViewGroup viewGroup;
        if (view == null || (viewGroup = (ViewGroup) view.getParent()) == null) {
            return;
        }
        viewGroup.removeView(view);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
    }

    public boolean showEndOverflowMenu() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        return (lifecycleOwner != null ? lifecycleOwner.getLifecycle().getState().equals(Lifecycle.State.RESUMED) : true) && this.mEndActionMenuPresenter != null && this.mEndActionMenuEnable && this.mEndActionMenuPresenter.showOverflowMenu();
    }

    public void postShowEndOverflowMenu() {
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.showEndOverflowMenu();
            }
        });
    }

    public boolean hideEndOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu(false);
    }

    public boolean isEndOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    public boolean isEndOverflowReserved() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowReserved();
    }

    public void dismissEndPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus(false);
        }
    }

    private FrameLayout createSecondaryTabContainer(int i) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(i);
        frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), frameLayout.getPaddingTop(), frameLayout.getPaddingEnd(), this.mSecondaryTabVerticalPadding);
        frameLayout.setVisibility(0);
        return frameLayout;
    }

    private boolean shouldMeasureCollapseTabContainer() {
        FrameLayout frameLayout = this.mCollapseTabContainer;
        return (frameLayout == null || frameLayout.getParent() != this || this.mCollapseTabContainer.getChildCount() == 0) ? false : true;
    }

    private boolean shouldMeasureMovableTabContainer() {
        FrameLayout frameLayout = this.mMovableTabContainer;
        return (frameLayout == null || frameLayout.getParent() != this || this.mMovableTabContainer.getChildCount() == 0) ? false : true;
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        return (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void addBadgeOnItemView(MenuItem menuItem, int i) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.addBadgeOnItemView(menuItem, i);
    }

    public void addBadgeOnItemView(int i, int i2) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.addBadgeOnItemView(i, i2);
    }

    public void addNumberBadgeOnItemView(int i, int i2, int i3) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.addNumberBadgeOnItemView(i, i2, i3);
    }

    public void addBadgeOnMoreButton(int i) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof EndActionMenuPresenter) {
            ((EndActionMenuPresenter) actionMenuPresenter).addBadgeOnMoreButton(i);
        }
    }

    public void addNumberBadgeOnMoreButton(int i, int i2) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof EndActionMenuPresenter) {
            ((EndActionMenuPresenter) actionMenuPresenter).addNumberBadgeOnMoreButton(i, i2);
        }
    }

    public void clearBadgeOnItemView(int i) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.clearBadgeOnItemView(i);
    }

    public Map<Integer, Boolean> getHyperMenuPrimaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            return ((HyperActionMenuPresenter) actionMenuPresenter).getHyperPrimaryCheckedData();
        }
        return null;
    }

    public Map<Integer, Boolean[]> getHyperMenuSecondaryCheckedData() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            return ((HyperActionMenuPresenter) actionMenuPresenter).getHyperSecondaryCheckedData();
        }
        return null;
    }

    public void restorePrimaryMenuCheckedData(Map<Integer, Boolean> map) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            ((HyperActionMenuPresenter) actionMenuPresenter).restorePrimaryMenuCheckedData(map);
        }
    }

    public void restoreSecondaryMenuCheckedData(Map<Integer, Boolean[]> map) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            ((HyperActionMenuPresenter) actionMenuPresenter).restoreSecondaryMenuCheckedData(map);
        }
    }

    public void setHyperMenuSaveStatusByIdEnabled(boolean z) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof HyperActionMenuPresenter) {
            ((HyperActionMenuPresenter) actionMenuPresenter).setHyperMenuSaveStatusByIdEnabled(z);
        }
    }

    public void setHyperSplitMenuSaveStatusByIdEnabled(boolean z) {
        if (this.mActionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            ((HyperSplitActionMenuPresenter) this.mActionMenuPresenter).setHyperSplitMenuSaveStatusByIdEnabled(z);
        }
    }

    public Map<Integer, Boolean> getHyperSplitMenuPrimaryCheckedData() {
        if (this.mActionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            return ((HyperSplitActionMenuPresenter) this.mActionMenuPresenter).getHyperSplitMenuPrimaryCheckedMap();
        }
        return null;
    }

    public Map<Integer, Boolean[]> getHyperSplitMenuSecondaryCheckedData() {
        if (this.mActionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            return ((HyperSplitActionMenuPresenter) this.mActionMenuPresenter).getHyperSplitMenuSecondaryCheckedMap();
        }
        return null;
    }

    public void restoreHyperSplitMenuPrimaryCheckedData(Map<Integer, Boolean> map) {
        if (this.mActionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            ((HyperSplitActionMenuPresenter) this.mActionMenuPresenter).restoreHyperSplitPrimaryCheckedData(map);
        }
    }

    public void restoreHyperSplitMenuSecondaryCheckedData(Map<Integer, Boolean[]> map) {
        if (this.mActionMenuPresenter instanceof HyperSplitActionMenuPresenter) {
            ((HyperSplitActionMenuPresenter) this.mActionMenuPresenter).restoreHyperSplitSecondaryCheckedData(map);
        }
    }

    public void clearBadgeOnItemView(MenuItem menuItem) {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter == null) {
            return;
        }
        actionMenuPresenter.clearBadgeOnItemView(menuItem);
    }

    public void clearBadgeOnMoreButton() {
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter instanceof EndActionMenuPresenter) {
            ((EndActionMenuPresenter) actionMenuPresenter).clearBadgeOnMoreButton();
        }
    }

    public void setExtraPaddingPolicy(ExtraPaddingPolicy extraPaddingPolicy) {
        this.mExtraPaddingPolicy = extraPaddingPolicy;
    }

    public void collapseActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    public void setCustomNavigationView(View view) {
        boolean z = (this.mDisplayOptions & 16) != 0;
        View view2 = this.mCustomNavView;
        if (view2 != null && z) {
            removeView(view2);
        }
        this.mCustomNavView = view;
        if (view != null && z) {
            addView(view);
            addCustomView();
        } else {
            this.mCollapseController.attachViews(this.mMainContainer);
        }
    }

    private TextView getCustomTitleView(View view) {
        if (view != null) {
            return (TextView) view.findViewById(android.R.id.title);
        }
        return null;
    }

    private boolean hasTabsInContainer(ViewGroup viewGroup) {
        return viewGroup != null && viewGroup.getChildCount() == 1 && (viewGroup.getChildAt(0) instanceof ScrollingTabContainerView);
    }

    private boolean freeMainContainerChildren() {
        if (hasTabsInContainer(this.mMainContainer)) {
            addSecondaryTabsToCollapseTabContainers();
        }
        if (hasTabsInContainer(this.mMovableMainContainer)) {
            addSecondaryTabsToExpandTabContainers();
        }
        this.mMainContainer.removeAllViews();
        this.mMovableMainContainer.removeAllViews();
        return true;
    }

    public void setStartView(View view) {
        View view2 = this.mStartView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mStartView = view;
        if (view != null) {
            addView(view);
            Folme.useAt(view).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(this.mStartView).hover().setFeedbackRadius(60.0f);
            Folme.useAt(this.mStartView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mStartView, new AnimConfig[0]);
        }
    }

    public View getStartView() {
        return this.mStartView;
    }

    public void setEndView(View view) {
        View view2 = this.mEndView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mEndView = view;
        if (view != null) {
            addView(view);
            Folme.useAt(this.mEndView).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(0.6f, new ITouchStyle.TouchType[0]).handleTouchOf(view, new AnimConfig[0]);
            Folme.useAt(this.mEndView).hover().setFeedbackRadius(60.0f);
            Folme.useAt(this.mEndView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mEndView, new AnimConfig[0]);
        }
    }

    public View getEndView() {
        return this.mEndView;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(CharSequence charSequence) {
        this.mUserTitle = true;
        setTitleImpl(charSequence);
    }

    public void setWindowTitle(CharSequence charSequence) {
        if (this.mUserTitle) {
            return;
        }
        setTitleImpl(charSequence);
    }

    private boolean isAllTitlesEmpty() {
        return TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle);
    }

    private boolean shouldTitleVisible() {
        return (this.mExpandedActionView != null || (this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) ? false : true;
    }

    private void setTitleImpl(CharSequence charSequence) {
        boolean zShouldTitleVisible = shouldTitleVisible();
        this.mTitle = charSequence;
        boolean z = false;
        if (((this.mDisplayOptions & 16) == 0 || this.mCustomNavView == null) ? false : updateExpandTitleOnShowCustom()) {
            return;
        }
        updateCollapseTitle();
        updateExpandTitle();
        boolean zShouldTitleVisible2 = shouldTitleVisible();
        setTitleVisibility(zShouldTitleVisible2);
        ActionMenuItem actionMenuItem = this.mLogoNavItem;
        if (actionMenuItem != null) {
            actionMenuItem.setTitle(charSequence);
        }
        ActionMenuItem actionMenuItem2 = this.mTitleNavItem;
        if (actionMenuItem2 != null) {
            actionMenuItem2.setTitle(charSequence);
        }
        if (zShouldTitleVisible && !zShouldTitleVisible2) {
            if ((getNavigationMode() == 2) || isTightTitleWithEmbeddedTabs()) {
                addTabsToMainContainers();
                return;
            }
            return;
        }
        if (zShouldTitleVisible || !zShouldTitleVisible2) {
            return;
        }
        if ((getNavigationMode() == 2) && isTightTitleWithEmbeddedTabs()) {
            return;
        }
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null && collapseTitle.getLayout().getParent() == null) {
            z = true;
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if ((expandTitle == null || z || expandTitle.getLayout().getParent() != null) ? z : true) {
            freeMainContainerChildren();
            CollapseTitle collapseTitle2 = this.mCollapseTitle;
            if (collapseTitle2 != null) {
                safeAddView(this.mMainContainer, collapseTitle2.getLayout());
            }
            ExpandTitle expandTitle2 = this.mExpandTitle;
            if (expandTitle2 != null) {
                safeAddView(this.mMovableMainContainer, expandTitle2.getLayout());
            }
        }
    }

    private void updateCollapseTitle() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            if (collapseTitle.getTitleVisibility() != 0) {
                this.mCollapseTitle.setTitleVisibility(0);
            }
            this.mCollapseTitle.setTitle(this.mTitle);
            this.mCollapseTitle.setSubTitle(this.mSubtitle);
            post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4250xd3029915();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$updateCollapseTitle$2$miuix-appcompat-internal-app-widget-ActionBarView, reason: not valid java name */
    /* synthetic */ void m4250xd3029915() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleTextSize(collapseTitle.getSubtitleAdjustSize());
        }
    }

    private void updateExpandTitle() {
        if (this.mExpandTitle != null) {
            boolean zUpdateExpandTitleOnShowCustom = (!((this.mDisplayOptions & 16) != 0) || this.mCustomNavView == null) ? false : updateExpandTitleOnShowCustom();
            this.mExpandTitle.setTitleVisibility(0);
            if (!zUpdateExpandTitleOnShowCustom) {
                this.mExpandTitle.setTitle(this.mTitle);
            }
            this.mExpandTitle.setSubTitle(this.mSubtitle);
        }
    }

    private boolean updateExpandTitleOnShowCustom() {
        TextView customTitleView = getCustomTitleView((FrameLayout) this.mCustomNavView.findViewById(R.id.action_bar_expand_container));
        if (customTitleView == null) {
            return false;
        }
        if (this.mExpandTitle == null) {
            return true;
        }
        if (!TextUtils.isEmpty(this.mInitCustomTitle)) {
            if (!this.mInitCustomTitle.equals(customTitleView.getText())) {
                customTitleView.removeTextChangedListener(this.mCustomTitleWatcher);
                customTitleView.setText(this.mInitCustomTitle);
                customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
            }
            this.mExpandTitle.setTitle(this.mInitCustomTitle);
        } else {
            this.mExpandTitle.setTitle(this.mTitle);
            customTitleView.removeTextChangedListener(this.mCustomTitleWatcher);
            customTitleView.setText(this.mTitle);
            customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
        }
        if (this.mExpandTitle.getVisibility() != 0) {
            this.mExpandTitle.setVisibility(0);
        }
        this.mExpandTitle.setSubTitleVisibility(8);
        return true;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitle(charSequence);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setSubTitle(charSequence);
        }
        setTitleVisibility(shouldTitleVisible());
        updateTightTitle();
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4249x30adb00e();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setSubtitle$3$miuix-appcompat-internal-app-widget-ActionBarView, reason: not valid java name */
    /* synthetic */ void m4249x30adb00e() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleTextSize(collapseTitle.getSubtitleAdjustSize());
        }
    }

    public void setSubTitleDrawable(TextViewDrawableConfig textViewDrawableConfig) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setSubTitleDrawable(textViewDrawableConfig);
        }
    }

    public void setHomeButtonEnabled(boolean z) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setEnabled(z);
            this.mHomeLayout.setFocusable(z);
            if (!z) {
                this.mHomeLayout.setContentDescription(null);
            } else if ((this.mDisplayOptions & 4) != 0) {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_up_description));
            } else {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_home_description));
            }
        }
    }

    public void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOptions;
        int i3 = i2 != -1 ? i ^ i2 : -1;
        this.mDisplayOptions = i;
        if ((i3 & 8223) != 0) {
            boolean z = (i & 2) != 0;
            int i4 = 8;
            if (z) {
                initHomeLayout();
                this.mHomeLayout.setVisibility(this.mExpandedActionView == null ? 0 : 8);
                if ((i3 & 4) != 0) {
                    boolean z2 = (i & 4) != 0;
                    this.mHomeLayout.setUp(z2);
                    if (z2) {
                        setHomeButtonEnabled(true);
                    }
                }
                if ((i3 & 1) != 0) {
                    Drawable logo = getLogo();
                    boolean z3 = (logo == null || (i & 1) == 0) ? false : true;
                    HomeView homeView = this.mHomeLayout;
                    if (!z3) {
                        logo = getIcon();
                    }
                    homeView.setIcon(logo);
                }
            } else {
                HomeView homeView2 = this.mHomeLayout;
                if (homeView2 != null) {
                    removeView(homeView2);
                }
            }
            if ((i3 & 8) != 0) {
                if ((i & 8) != 0) {
                    if (getNavigationMode() == 2) {
                        freeMainContainerChildren();
                    }
                    initTitle();
                } else {
                    CollapseTitle collapseTitle = this.mCollapseTitle;
                    if (collapseTitle != null) {
                        this.mMainContainer.removeView(collapseTitle.getLayout());
                    }
                    ExpandTitle expandTitle = this.mExpandTitle;
                    if (expandTitle != null) {
                        this.mMovableMainContainer.removeView(expandTitle.getLayout());
                    }
                    this.mCollapseTitle = null;
                    this.mExpandTitle = null;
                    if ((getDisplayOptions() & 32) == 0) {
                        removeView(this.mTitleUpView);
                        this.mTitleUpView = null;
                    }
                    if (getNavigationMode() == 2) {
                        addTabsToMainContainers();
                    }
                }
            }
            if ((i3 & 6) != 0) {
                boolean z4 = (this.mDisplayOptions & 4) != 0;
                CollapseTitle collapseTitle2 = this.mCollapseTitle;
                boolean z5 = collapseTitle2 != null && collapseTitle2.getVisibility() == 0;
                ExpandTitle expandTitle2 = this.mExpandTitle;
                if (expandTitle2 != null && expandTitle2.getVisibility() == 0) {
                    z5 = true;
                }
                if (this.mTitleUpView != null && (z5 || (getDisplayOptions() & 32) != 0)) {
                    View view2 = this.mTitleUpView;
                    if (!z) {
                        i4 = z4 ? 0 : 4;
                    }
                    view2.setVisibility(i4);
                }
            }
            if ((i3 & 16) != 0 && (view = this.mCustomNavView) != null) {
                if ((i & 16) != 0) {
                    safeAddView(this, view);
                    addCustomView();
                } else {
                    removeView(view);
                }
            }
            if ((i3 & 8192) != 0) {
                if ((i & 8192) != 0) {
                    View viewInflate = LayoutInflater.from(this.mContext).inflate(this.mNavigatorSwitchResId, (ViewGroup) this, false);
                    this.mNavigatorSwitch = viewInflate;
                    viewInflate.setTag(R.id.miuix_appcompat_navigator_switch_presenter, new NavigatorSwitchPresenter(this.mNavigatorSwitch));
                    Folme.useAt(this.mNavigatorSwitch).hover().setFeedbackRadius(60.0f);
                    Folme.useAt(this.mNavigatorSwitch).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mNavigatorSwitch, new AnimConfig[0]);
                    addView(this.mNavigatorSwitch);
                } else {
                    removeView(this.mNavigatorSwitch);
                    this.mNavigatorSwitch = null;
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        HomeView homeView3 = this.mHomeLayout;
        if (homeView3 != null) {
            if (!homeView3.isEnabled()) {
                this.mHomeLayout.setContentDescription(null);
            } else if ((i & 4) != 0) {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_up_description));
            } else {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_home_description));
            }
        }
    }

    private void addCustomView() {
        FrameLayout frameLayout = (FrameLayout) this.mCustomNavView.findViewById(R.id.action_bar_expand_container);
        TextView customTitleView = getCustomTitleView(frameLayout);
        if (customTitleView != null) {
            this.mInitCustomTitle = customTitleView.getText();
            freeMainContainerChildren();
            this.mCollapseCustomContainer = frameLayout;
            this.mCollapseController.attachViews(frameLayout);
            ExpandTitle expandTitle = this.mExpandTitle;
            if (expandTitle != null) {
                expandTitle.setTitle(this.mInitCustomTitle);
                this.mExpandTitle.setTitleVisibility(0);
                this.mExpandTitle.setVisibility(0);
                this.mExpandTitle.setSubTitleVisibility(8);
                if (this.mMovableMainContainer != this.mExpandTitle.getLayout().getParent()) {
                    safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
                }
            }
            customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
        }
    }

    public void setIcon(Drawable drawable) {
        HomeView homeView;
        this.mIcon = drawable;
        this.mIconLogoInitIndicator |= 1;
        if (drawable != null && (((this.mDisplayOptions & 1) == 0 || getLogo() == null) && (homeView = this.mHomeLayout) != null)) {
            homeView.setIcon(drawable);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    public void setIcon(int i) {
        setIcon(this.mContext.getResources().getDrawable(i));
    }

    public void setLogo(Drawable drawable) {
        HomeView homeView;
        this.mLogo = drawable;
        this.mIconLogoInitIndicator |= 2;
        if (drawable == null || (this.mDisplayOptions & 1) == 0 || (homeView = this.mHomeLayout) == null) {
            return;
        }
        homeView.setIcon(drawable);
    }

    public void setLogo(int i) {
        setLogo(this.mContext.getResources().getDrawable(i));
    }

    public void setNavigationMode(int i) {
        LinearLayout linearLayout;
        int i2 = this.mNavigationMode;
        if (i != i2) {
            if (i2 == 1 && (linearLayout = this.mListNavLayout) != null) {
                removeView(linearLayout);
            }
            if (i != 0) {
                if (i == 1) {
                    throw new UnsupportedOperationException("MIUIX Deleted");
                }
                if (i == 2 && this.mTabsExit) {
                    addTabsContainer();
                }
            } else if (this.mTabsExit) {
                removeTabsFromContainer();
            }
            this.mNavigationMode = i;
            requestLayout();
        }
    }

    public void setDropdownAdapter(SpinnerAdapter spinnerAdapter) {
        this.mSpinnerAdapter = spinnerAdapter;
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
        }
    }

    public SpinnerAdapter getDropdownAdapter() {
        return this.mSpinnerAdapter;
    }

    public void setDropdownSelectedPosition(int i) {
        this.mSpinner.setSelection(i);
    }

    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    public View getCustomNavigationView() {
        return this.mCustomNavView;
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams(DEFAULT_CUSTOM_GRAVITY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTitle() {
        this.mPendingCreated = false;
        initTitleUpView();
        if (this.mNavigationMode == 2) {
            freeMainContainerChildren();
        }
        if (this.mInnerExpandState == 1) {
            if (this.mExpandTitle == null) {
                createExpandTitle(false);
            }
            AbsActionBarView.CollapseView collapseView = this.mCollapseController;
            if (collapseView != null) {
                collapseView.onHide();
            }
        } else if (this.mInnerExpandState == 0 && this.mCollapseTitle == null) {
            createCollapseTitle(false);
        }
        updateTightTitle();
        post(new Runnable() { // from class: miuix.appcompat.internal.app.widget.ActionBarView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4246x78313c1d();
            }
        });
        if (this.mExpandedActionView != null || isAllTitlesEmpty()) {
            setTitleVisibility(false);
        }
        safeAddView(this, this.mMainContainer);
        safeAddView(this, this.mMovableMainContainer, 0);
    }

    /* JADX INFO: renamed from: lambda$initTitle$4$miuix-appcompat-internal-app-widget-ActionBarView, reason: not valid java name */
    /* synthetic */ void m4246x78313c1d() {
        pendingCreateTitle();
        setTitleVisibility(shouldTitleVisible());
        updateTightTitle();
    }

    private void initTitleUpView() {
        if (this.mTitleUpView == null) {
            View viewGenerateTitleUpView = ActionBarViewFactory.generateTitleUpView(getContext(), null);
            this.mTitleUpView = viewGenerateTitleUpView;
            viewGenerateTitleUpView.setOnClickListener(this.mUpClickListener);
        }
        int i = this.mDisplayOptions;
        int i2 = 0;
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 2) != 0;
        View view = this.mTitleUpView;
        if (z2) {
            i2 = 8;
        } else if (!z) {
            i2 = 4;
        }
        view.setVisibility(i2);
        this.mTitleUpView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        safeAddView(this, this.mTitleUpView);
    }

    private void pendingCreateTitle() {
        if (this.mPendingCreated) {
            return;
        }
        this.mPendingCreated = true;
        if ((this.mDisplayOptions & 8) != 0) {
            if (this.mExpandTitle == null) {
                createExpandTitle(true);
                updateExpandTitle();
            }
            if (this.mCollapseTitle == null) {
                createCollapseTitle(true);
            }
            updateCollapseTitle();
        }
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            Rect hitRect = collapseTitle.getHitRect();
            hitRect.left -= AttributeResolver.resolveDimensionPixelSize(getContext(), R.attr.actionBarPaddingStart);
            setTouchDelegate(new TouchDelegate(hitRect, this.mCollapseTitle.getLayout()));
        }
    }

    private void createCollapseTitle(boolean z) {
        if (this.mCollapseTitle == null) {
            CollapseTitle collapseTitleGenerateCollapseTitle = ActionBarViewFactory.generateCollapseTitle(getContext(), this.mCollapseTitleStyleRes, this.mCollapseSubtitleStyleRes);
            this.mCollapseTitle = collapseTitleGenerateCollapseTitle;
            collapseTitleGenerateCollapseTitle.setVisible(this.mCollapsedTitleVisible);
            this.mCollapseTitle.setTextColorTransitEnable(this.mCollapseTitleColorTransitEnable, this.mInnerExpandState);
            this.mCollapseTitle.setAllTitlesClickable(this.mTitleClickable);
            this.mCollapseTitle.setTitle(this.mTitle);
            this.mCollapseTitle.setOnClickListener(this.mTitleClickListener, this.mTitleClickable);
            this.mCollapseTitle.setSubTitleOnClickListener(this.mSubTitleClickListener, this.mUserSubTitleClickListener != null);
            this.mCollapseTitle.setSubTitle(this.mSubtitle);
            if (!z) {
                safeAddView(this.mMainContainer, this.mCollapseTitle.getLayout());
                return;
            }
            if ((this.mDisplayOptions & 8) != 0) {
                if (getNavigationMode() == 2 && isTightTitleWithEmbeddedTabs()) {
                    return;
                }
                if (hasTabsInContainer(this.mMainContainer)) {
                    addSecondaryTabsToCollapseTabContainers();
                }
                this.mMainContainer.removeAllViews();
                safeAddView(this.mMainContainer, this.mCollapseTitle.getLayout());
            }
        }
    }

    private void createExpandTitle(boolean z) {
        boolean z2;
        View view;
        if (this.mExpandTitle == null) {
            ExpandTitle expandTitleGenerateExpandTitle = ActionBarViewFactory.generateExpandTitle(getContext());
            this.mExpandTitle = expandTitleGenerateExpandTitle;
            expandTitleGenerateExpandTitle.setVisible(this.mExpandTitleVisible);
            this.mExpandTitle.setTextColorTransitEnable(this.mExpandTitleColorTransitEnable, this.mInnerExpandState);
            this.mExpandTitle.setAllTitlesClickable(this.mTitleClickable);
            CharSequence charSequence = this.mTitle;
            if (!z || (this.mDisplayOptions & 16) == 0 || (view = this.mCustomNavView) == null || getCustomTitleView((FrameLayout) view.findViewById(R.id.action_bar_expand_container)) == null || TextUtils.isEmpty(this.mInitCustomTitle)) {
                z2 = false;
            } else {
                charSequence = this.mInitCustomTitle;
                z2 = true;
            }
            this.mExpandTitle.setTitle(charSequence);
            this.mExpandTitle.setOnClickListener(this.mTitleClickListener, this.mTitleClickable);
            this.mExpandTitle.setSubTitleOnClickListener(this.mSubTitleClickListener, this.mUserSubTitleClickListener != null);
            if (!z2) {
                this.mExpandTitle.setSubTitle(this.mSubtitle);
            } else {
                this.mExpandTitle.setSubTitle(null);
            }
            if (!z) {
                safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
                return;
            }
            if ((this.mDisplayOptions & 8) != 0) {
                if (getNavigationMode() == 2 && isTightTitleWithEmbeddedTabs()) {
                    return;
                }
                if (hasTabsInContainer(this.mMovableMainContainer)) {
                    addSecondaryTabsToExpandTabContainers();
                }
                this.mMovableMainContainer.removeAllViews();
                safeAddView(this.mMovableMainContainer, this.mExpandTitle.getLayout());
            }
        }
    }

    private void safeAddView(ViewGroup viewGroup, View view) {
        safeAddView(viewGroup, view, -1);
    }

    private void safeAddView(ViewGroup viewGroup, View view, int i) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        if (viewGroup != null) {
            viewGroup.addView(view, i);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void setExpandState(int i, boolean z, boolean z2) {
        if (!z) {
            pendingCreateTitle();
        }
        super.setExpandState(i, z, z2);
    }

    public boolean isCollapsed() {
        return this.mIsCollapsed;
    }

    public void setTitleVisible(boolean z, boolean z2) {
        this.mCollapsedTitleVisible = z;
        this.mExpandTitleVisible = z2;
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setVisible(z);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setVisible(z2);
        }
    }

    public void setEndActionMenuItemLimit(int i) {
        this.mEndActionMenuItemLimit = i;
        ActionMenuPresenter actionMenuPresenter = this.mEndActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.setItemLimit(i);
        }
    }

    public int getEndActionMenuItemLimit() {
        return this.mEndActionMenuItemLimit;
    }

    public void setUserSetEndActionMenuItemLimit(boolean z) {
        this.mUserSetEndActionMenuItemLimit = z;
    }

    public boolean isUserSetEndActionMenuItemLimit() {
        return this.mUserSetEndActionMenuItemLimit;
    }

    private void updateTightTitle() {
        boolean z = isTightTitleWithEmbeddedTabs() && TextUtils.isEmpty(this.mTitle);
        boolean zIsEmpty = TextUtils.isEmpty(this.mSubtitle);
        int i = (!zIsEmpty || (!z && this.mCollapseTitleShowable)) ? 0 : 8;
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.setTitleVisibility(i);
        }
        int i2 = zIsEmpty ? 8 : 0;
        CollapseTitle collapseTitle2 = this.mCollapseTitle;
        if (collapseTitle2 != null) {
            collapseTitle2.setSubTitleVisibility(i2);
        }
    }

    private boolean isSimpleCustomNavView() {
        View view = this.mCustomNavView;
        if (view == null || view.getVisibility() != 0) {
            return true;
        }
        ViewGroup.LayoutParams layoutParams = this.mCustomNavView.getLayoutParams();
        ActionBar.LayoutParams layoutParams2 = layoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParams : null;
        return layoutParams2 != null && normalizeHorizontalGravity(layoutParams2.gravity, ViewUtils.isLayoutRtl(this)) == 8388613;
    }

    private boolean isTitleCenter() {
        if (!this.mTitleCenter || !isSimpleCustomNavView()) {
            return false;
        }
        HomeView homeView = this.mHomeLayout;
        return homeView == null || homeView.getVisibility() == 8;
    }

    private boolean isShowTitle() {
        if (this.mMainContainer.getChildCount() <= 0) {
            return (this.mCustomNavView == null || this.mCollapseCustomContainer == null) ? false : true;
        }
        return true;
    }

    private void updateTitleCenter() {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        if (collapseTitle != null) {
            collapseTitle.updateTitleCenter(isTitleCenter());
        }
    }

    /* JADX WARN: Code duplicated, block: B:148:0x02a9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:149:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:150:0x02b2  */
    /* JADX WARN: Code duplicated, block: B:152:0x02b7  */
    /* JADX WARN: Code duplicated, block: B:154:0x02be  */
    /* JADX WARN: Code duplicated, block: B:155:0x02c3  */
    /* JADX WARN: Code duplicated, block: B:159:0x02cc  */
    /* JADX WARN: Code duplicated, block: B:160:0x02cf  */
    /* JADX WARN: Code duplicated, block: B:163:0x02d5  */
    /* JADX WARN: Code duplicated, block: B:164:0x02e0  */
    /* JADX WARN: Code duplicated, block: B:167:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:168:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:201:0x03b4  */
    /* JADX WARN: Code duplicated, block: B:204:0x03bd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:205:0x03bf  */
    /* JADX WARN: Code duplicated, block: B:206:0x03c1  */
    /* JADX WARN: Code duplicated, block: B:208:0x03d6  */
    /* JADX WARN: Code duplicated, block: B:214:0x0408  */
    /* JADX WARN: Code duplicated, block: B:217:0x0423  */
    /* JADX WARN: Code duplicated, block: B:218:0x043b  */
    /* JADX WARN: Code duplicated, block: B:221:0x0442  */
    /* JADX WARN: Code duplicated, block: B:222:0x0459  */
    /* JADX WARN: Code duplicated, block: B:232:0x046c  */
    /* JADX WARN: Code duplicated, block: B:235:0x0481  */
    /* JADX WARN: Code duplicated, block: B:236:0x0488  */
    /* JADX WARN: Code duplicated, block: B:239:0x04a1  */
    /* JADX WARN: Code duplicated, block: B:241:0x04a9  */
    /* JADX WARN: Code duplicated, block: B:243:0x04af  */
    /* JADX WARN: Code duplicated, block: B:244:0x04be  */
    /* JADX WARN: Code duplicated, block: B:246:0x04c3  */
    /* JADX WARN: Code duplicated, block: B:247:0x04c9  */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int iMax;
        int i3;
        int iMax2;
        int i4;
        int i5;
        int iMax3;
        boolean z;
        int iMax4;
        int i6;
        int i7;
        boolean zShouldMeasureCollapseTabContainer;
        boolean zShouldMeasureMovableTabContainer;
        ExtraPaddingPolicy extraPaddingPolicy;
        int i8;
        int iMax5;
        int i9;
        int i10;
        int measuredHeight;
        int i11;
        int measuredHeight2;
        ProgressBar progressBar;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int iMax6;
        int iMin;
        int i17;
        int iMin2;
        int iMax7;
        int i18;
        View view;
        View view2;
        int iMakeMeasureSpec;
        int childCount = getChildCount();
        int i19 = 0;
        for (int i20 = 0; i20 < childCount; i20++) {
            View childAt = getChildAt(i20);
            if (childAt.getVisibility() != 8 && (childAt != this.mMenuView || this.mMenuView.getChildCount() != 0)) {
                i19++;
            }
        }
        if (i19 == 0) {
            setMeasuredDimension(0, 0);
            this.mIsCollapsed = true;
            return;
        }
        this.mIsCollapsed = false;
        int size = View.MeasureSpec.getSize(i);
        int i21 = this.mTitleMaxHeight;
        int i22 = this.mTitleMinHeight;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int size2 = (i21 > 0 ? i21 : View.MeasureSpec.getSize(i2)) - paddingTop;
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE);
        int iMeasureChildView = (size - paddingStart) - paddingEnd;
        int iMax8 = iMeasureChildView / 2;
        boolean zHasTitle = hasTitle();
        View view3 = this.mStartView;
        if (view3 == null || view3.getVisibility() == 8) {
            iMax = 0;
        } else {
            iMeasureChildView = measureChildView(this.mStartView, iMeasureChildView, iMakeMeasureSpec2, 0);
            paddingStart += this.mStartView.getMeasuredWidth();
            iMax = this.mStartView.getMeasuredHeight() + paddingTop;
        }
        View view4 = this.mEndView;
        if (view4 != null && view4.getVisibility() != 8) {
            iMeasureChildView = measureChildView(this.mEndView, iMeasureChildView, iMakeMeasureSpec2, 0);
            paddingEnd += this.mEndView.getMeasuredWidth();
            iMax = Math.max(iMax, this.mEndView.getMeasuredHeight() + paddingTop);
        }
        View view5 = this.mNavigatorSwitch;
        if (view5 != null) {
            ((NavigatorSwitchPresenter) view5.getTag(R.id.miuix_appcompat_navigator_switch_presenter)).suppressVisibility(false, 0);
            if (this.mNavigatorSwitch.getVisibility() != 8) {
                iMeasureChildView = measureChildView(this.mNavigatorSwitch, iMeasureChildView, iMakeMeasureSpec2, 0);
                paddingStart += this.mNavigatorSwitch.getMeasuredWidth();
                iMax = Math.max(iMax, this.mNavigatorSwitch.getMeasuredHeight() + paddingTop);
            }
        }
        View view6 = this.mStartView;
        if (view6 != null && view6.getVisibility() != 0) {
            this.mHasStartView = false;
        }
        View view7 = this.mNavigatorSwitch;
        if (view7 != null && view7.getVisibility() != 0) {
            this.mHasNavigatorSwitchView = false;
        }
        HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        if (this.mStartView == null || homeView == null) {
            i3 = 8;
        } else {
            i3 = 8;
            homeView.setVisibility(8);
        }
        if (homeView == null || homeView.getVisibility() == i3) {
            iMax2 = iMax8;
        } else {
            ViewGroup.LayoutParams layoutParams = homeView.getLayoutParams();
            if (layoutParams.width < 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMeasureChildView, Integer.MIN_VALUE);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
            }
            homeView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = homeView.getMeasuredWidth() + homeView.getStartOffset();
            iMeasureChildView = Math.max(0, iMeasureChildView - measuredWidth);
            iMax2 = Math.max(0, iMeasureChildView - measuredWidth);
            paddingStart += measuredWidth;
            iMax = Math.max(iMax, homeView.getMeasuredHeight() + paddingTop);
        }
        ActionMenuView actionMenuView = this.mEndMenuView;
        if (actionMenuView != null && actionMenuView.getParent() == this && this.mEndMenuView.getVisibility() != 8) {
            iMeasureChildView = measureChildView(this.mEndMenuView, iMeasureChildView, iMakeMeasureSpec2, 0);
            iMax8 = Math.max(0, iMax8 - this.mEndMenuView.getMeasuredWidth());
            paddingEnd += this.mEndMenuView.getMeasuredWidth();
            iMax = Math.max(iMax, this.mEndMenuView.getMeasuredHeight() + paddingTop);
        }
        ProgressBar progressBar2 = this.mIndeterminateProgressView;
        if (progressBar2 != null) {
            i4 = 2;
            if (progressBar2.getVisibility() != 8) {
                iMeasureChildView = measureChildView(this.mIndeterminateProgressView, iMeasureChildView, iMakeMeasureSpec2, this.mProgressBarPadding * 2);
                iMax8 = Math.max(0, (iMax8 - this.mIndeterminateProgressView.getMeasuredWidth()) - (this.mProgressBarPadding * 2));
                paddingEnd += this.mIndeterminateProgressView.getMeasuredWidth();
                iMax = Math.max(iMax, this.mIndeterminateProgressView.getMeasuredHeight() + paddingTop);
            }
        } else {
            i4 = 2;
        }
        boolean zIsShowTitle = isShowTitle();
        if (zIsShowTitle) {
            updateTitleCenter();
        }
        if (this.mExpandedActionView == null && ((zIsShowTitle || (getDisplayOptions() & 32) != 0) && (view2 = this.mTitleUpView) != null && view2.getVisibility() == 0)) {
            iMeasureChildView = measureChildView(this.mTitleUpView, iMeasureChildView, iMakeMeasureSpec2, (this.mHasStartView || this.mHasNavigatorSwitchView) ? this.mTitleUpViewMarginStart + this.mTitleUpViewMarginEnd : 0);
            paddingStart += this.mTitleUpView.getMeasuredWidth() + ((this.mHasStartView || this.mHasNavigatorSwitchView) ? this.mTitleUpViewMarginEnd + this.mTitleUpViewMarginStart : 0);
            Math.max(iMax, this.mTitleUpView.getMeasuredHeight() + paddingTop);
        }
        View view8 = this.mExpandedActionView;
        if (view8 == null && ((this.mDisplayOptions & 16) == 0 || (view8 = this.mCustomNavView) == null)) {
            view8 = null;
        }
        if ((this.mStartView == null && this.mEndView == null) || view8 == null) {
            i5 = 8;
        } else {
            i5 = 8;
            view8.setVisibility(8);
        }
        if (view8 != null && view8.getVisibility() != i5) {
            View view9 = this.mTitleUpView;
            int measuredWidth2 = (view9 == null || view9.getVisibility() != 4) ? 0 : this.mTitleUpView.getMeasuredWidth();
            ViewGroup.LayoutParams layoutParamsGenerateLayoutParams = generateLayoutParams(view8.getLayoutParams());
            ActionBar.LayoutParams layoutParams2 = layoutParamsGenerateLayoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParamsGenerateLayoutParams : null;
            int i23 = measuredWidth2;
            if (layoutParams2 != null) {
                i15 = layoutParams2.leftMargin + layoutParams2.rightMargin;
                i14 = layoutParams2.topMargin + layoutParams2.bottomMargin;
            } else {
                i14 = 0;
                i15 = 0;
            }
            if (i21 <= 0) {
                i16 = i14;
            } else {
                i16 = i14;
                int i24 = layoutParamsGenerateLayoutParams.height >= 0 ? 1073741824 : Integer.MIN_VALUE;
                if (layoutParamsGenerateLayoutParams.height >= 0) {
                    if (layoutParamsGenerateLayoutParams.height == -1) {
                        iMax6 = Math.max(i22, i21);
                    } else {
                        iMax6 = size2;
                    }
                    iMin = iMax6 - i16;
                } else if (i21 > 0) {
                    iMin = Math.min(layoutParamsGenerateLayoutParams.height, i21);
                } else {
                    iMin = layoutParamsGenerateLayoutParams.height;
                }
                if (layoutParamsGenerateLayoutParams.width != -2) {
                    i17 = 1073741824;
                } else {
                    i17 = Integer.MIN_VALUE;
                }
                if (layoutParamsGenerateLayoutParams.width >= 0) {
                    iMin2 = Math.min(layoutParamsGenerateLayoutParams.width, iMeasureChildView + i23);
                } else {
                    iMin2 = iMeasureChildView + i23;
                }
                iMax7 = Math.max(0, iMin2 - i15);
                if (layoutParams2 != null) {
                    i18 = layoutParams2.gravity;
                } else {
                    i18 = DEFAULT_CUSTOM_GRAVITY;
                }
                if ((i18 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 1 && layoutParamsGenerateLayoutParams.width == -1) {
                    iMax7 = Math.min(iMax2, iMax8) * 2;
                }
                view = this.mNavigatorSwitch;
                if (view != null && view.getVisibility() == 0) {
                    iMax7 = (int) (iMax7 + ((this.mNavigatorSwitch.getMeasuredWidth() * (1.0f - this.mNavigatorSwitch.getAlpha())) - (this.mTitleUpViewMarginStart * this.mNavigatorSwitch.getAlpha())));
                }
                view8.measure(View.MeasureSpec.makeMeasureSpec(iMax7, i17), View.MeasureSpec.makeMeasureSpec(iMin, i24));
                iMeasureChildView -= (i15 + view8.getMeasuredWidth()) - i23;
                iMax3 = Math.max(i22, view8.getMeasuredHeight());
            }
            if (layoutParamsGenerateLayoutParams.height >= 0) {
                if (layoutParamsGenerateLayoutParams.height == -1) {
                    iMax6 = Math.max(i22, i21);
                } else {
                    iMax6 = size2;
                }
                iMin = iMax6 - i16;
            } else if (i21 > 0) {
                iMin = Math.min(layoutParamsGenerateLayoutParams.height, i21);
            } else {
                iMin = layoutParamsGenerateLayoutParams.height;
            }
            if (layoutParamsGenerateLayoutParams.width != -2) {
                i17 = 1073741824;
            } else {
                i17 = Integer.MIN_VALUE;
            }
            if (layoutParamsGenerateLayoutParams.width >= 0) {
                iMin2 = Math.min(layoutParamsGenerateLayoutParams.width, iMeasureChildView + i23);
            } else {
                iMin2 = iMeasureChildView + i23;
            }
            iMax7 = Math.max(0, iMin2 - i15);
            if (layoutParams2 != null) {
                i18 = layoutParams2.gravity;
            } else {
                i18 = DEFAULT_CUSTOM_GRAVITY;
            }
            if ((i18 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 1) {
                iMax7 = Math.min(iMax2, iMax8) * 2;
            }
            view = this.mNavigatorSwitch;
            if (view != null) {
                iMax7 = (int) (iMax7 + ((this.mNavigatorSwitch.getMeasuredWidth() * (1.0f - this.mNavigatorSwitch.getAlpha())) - (this.mTitleUpViewMarginStart * this.mNavigatorSwitch.getAlpha())));
            }
            view8.measure(View.MeasureSpec.makeMeasureSpec(iMax7, i17), View.MeasureSpec.makeMeasureSpec(iMin, i24));
            iMeasureChildView -= (i15 + view8.getMeasuredWidth()) - i23;
            iMax3 = Math.max(i22, view8.getMeasuredHeight());
        } else {
            size = size;
            zHasTitle = zHasTitle;
            ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
            if (scrollingTabContainerView != null) {
                measureChildView(scrollingTabContainerView, iMeasureChildView, iMakeMeasureSpec2, 0);
                iMax3 = Math.max(i22, this.mCollapseTabs.getMeasuredHeight());
            } else {
                if (view8 != null && view8.getVisibility() == 8 && zIsShowTitle == 0) {
                    iMax3 = i22;
                    z = true;
                } else {
                    iMax3 = i22;
                }
                if (this.mExpandedActionView == null || !zIsShowTitle) {
                    iMax4 = i22;
                } else {
                    if (isTitleCenter()) {
                        this.mMainContainer.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, paddingStart > paddingEnd ? size - (paddingStart * 2) : iMeasureChildView), Integer.MIN_VALUE), iMakeMeasureSpec2);
                        iMeasureChildView -= this.mMainContainer.getMeasuredWidth();
                    } else {
                        iMeasureChildView = measureChildView(this.mMainContainer, iMeasureChildView, iMakeMeasureSpec2, 0);
                    }
                    iMax4 = Math.max(i22, this.mMainContainer.getMeasuredHeight());
                }
                if (this.mMovableMainContainer.getChildCount() != 0) {
                    if (zHasTitle) {
                        i13 = 0;
                    } else {
                        i13 = 1073741824;
                    }
                    i6 = size;
                    i7 = 0;
                    this.mMovableMainContainer.measure(View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, i13));
                } else {
                    i6 = size;
                    i7 = 0;
                }
                zShouldMeasureCollapseTabContainer = shouldMeasureCollapseTabContainer();
                zShouldMeasureMovableTabContainer = shouldMeasureMovableTabContainer();
                this.mMovableSecondaryTabHeight = i7;
                float f = getResources().getConfiguration().densityDpi / 160.0f;
                extraPaddingPolicy = this.mExtraPaddingPolicy;
                if (extraPaddingPolicy == null && extraPaddingPolicy.isEnable()) {
                    this.mExtraPadding = (int) (this.mExtraPaddingPolicy.getExtraPaddingDp() * f);
                    i8 = 0;
                } else {
                    i8 = 0;
                    this.mExtraPadding = 0;
                }
                iMax5 = Math.max(i8, i6 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2));
                i9 = i6 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2);
                if (zShouldMeasureMovableTabContainer) {
                    i10 = 1073741824;
                    this.mMovableTabContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax5, 1073741824), View.MeasureSpec.makeMeasureSpec(i8, i8));
                    measuredHeight = this.mMovableTabContainer.getMeasuredHeight();
                    this.mMovableSecondaryTabHeight = measuredHeight;
                } else {
                    i10 = 1073741824;
                    measuredHeight = i8;
                }
                this.mCollapseSecondaryTabHeight = i8;
                if (zShouldMeasureCollapseTabContainer) {
                    this.mCollapseTabContainer.measure(View.MeasureSpec.makeMeasureSpec(i9, i10), View.MeasureSpec.makeMeasureSpec(i8, i8));
                    int measuredHeight3 = this.mCollapseTabContainer.getMeasuredHeight();
                    this.mCollapseSecondaryTabHeight = measuredHeight3;
                    i11 = measuredHeight3;
                } else {
                    i11 = 0;
                }
                if (this.mExpandedActionView == null && this.mNavigationMode == 1 && this.mListNavLayout != null) {
                    i12 = this.mItemPadding;
                    if (zIsShowTitle != 0) {
                        i12 *= 2;
                    }
                    this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, iMeasureChildView - i12), Integer.MIN_VALUE), iMakeMeasureSpec2);
                }
                if (zHasTitle) {
                    measuredHeight2 = this.mMovableMainContainer.getMeasuredHeight();
                } else {
                    measuredHeight2 = 0;
                }
                int iMax9 = Math.max(iMax3, iMax4 + i11);
                this.mCollapseTotalHeight = iMax9;
                this.mCollapseTotalHeight = Math.max(i22, iMax9);
                this.mExpandTotalHeight = Math.max(iMax3, iMax4) + measuredHeight2 + measuredHeight;
                if (z) {
                    setMeasuredDimension(i6, 0);
                    this.mIsCollapsed = true;
                    return;
                }
                if (this.mInnerExpandState == i4) {
                    int i25 = this.mCollapseTotalHeight;
                    setMeasuredDimension(i6, Math.max((i25 - i11) + this.mPendingHeight, i25));
                } else if (this.mInnerExpandState == 1) {
                    setMeasuredDimension(i6, this.mExpandTotalHeight);
                } else {
                    setMeasuredDimension(i6, this.mCollapseTotalHeight);
                }
                progressBar = this.mProgressView;
                if (progressBar != null || progressBar.getVisibility() == 8) {
                }
                this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(i6 - (this.mProgressBarPadding * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
                return;
            }
        }
        z = false;
        if (this.mExpandedActionView == null) {
            iMax4 = i22;
        } else {
            iMax4 = i22;
        }
        if (this.mMovableMainContainer.getChildCount() != 0) {
            if (zHasTitle) {
                i13 = 0;
            } else {
                i13 = 1073741824;
            }
            i6 = size;
            i7 = 0;
            this.mMovableMainContainer.measure(View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, i13));
        } else {
            i6 = size;
            i7 = 0;
        }
        zShouldMeasureCollapseTabContainer = shouldMeasureCollapseTabContainer();
        zShouldMeasureMovableTabContainer = shouldMeasureMovableTabContainer();
        this.mMovableSecondaryTabHeight = i7;
        float f2 = getResources().getConfiguration().densityDpi / 160.0f;
        extraPaddingPolicy = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy == null) {
            i8 = 0;
            this.mExtraPadding = 0;
        } else {
            i8 = 0;
            this.mExtraPadding = 0;
        }
        iMax5 = Math.max(i8, i6 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2));
        i9 = i6 - ((this.mUncollapseTabPaddingH + this.mExtraPadding) * 2);
        if (zShouldMeasureMovableTabContainer) {
            i10 = 1073741824;
            this.mMovableTabContainer.measure(View.MeasureSpec.makeMeasureSpec(iMax5, 1073741824), View.MeasureSpec.makeMeasureSpec(i8, i8));
            measuredHeight = this.mMovableTabContainer.getMeasuredHeight();
            this.mMovableSecondaryTabHeight = measuredHeight;
        } else {
            i10 = 1073741824;
            measuredHeight = i8;
        }
        this.mCollapseSecondaryTabHeight = i8;
        if (zShouldMeasureCollapseTabContainer) {
            this.mCollapseTabContainer.measure(View.MeasureSpec.makeMeasureSpec(i9, i10), View.MeasureSpec.makeMeasureSpec(i8, i8));
            int measuredHeight4 = this.mCollapseTabContainer.getMeasuredHeight();
            this.mCollapseSecondaryTabHeight = measuredHeight4;
            i11 = measuredHeight4;
        } else {
            i11 = 0;
        }
        if (this.mExpandedActionView == null) {
            i12 = this.mItemPadding;
            if (zIsShowTitle != 0) {
                i12 *= 2;
            }
            this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, iMeasureChildView - i12), Integer.MIN_VALUE), iMakeMeasureSpec2);
        }
        if (zHasTitle) {
            measuredHeight2 = this.mMovableMainContainer.getMeasuredHeight();
        } else {
            measuredHeight2 = 0;
        }
        int iMax10 = Math.max(iMax3, iMax4 + i11);
        this.mCollapseTotalHeight = iMax10;
        this.mCollapseTotalHeight = Math.max(i22, iMax10);
        this.mExpandTotalHeight = Math.max(iMax3, iMax4) + measuredHeight2 + measuredHeight;
        if (z) {
            setMeasuredDimension(i6, 0);
            this.mIsCollapsed = true;
            return;
        }
        if (this.mInnerExpandState == i4) {
            int i26 = this.mCollapseTotalHeight;
            setMeasuredDimension(i6, Math.max((i26 - i11) + this.mPendingHeight, i26));
        } else if (this.mInnerExpandState == 1) {
            setMeasuredDimension(i6, this.mExpandTotalHeight);
        } else {
            setMeasuredDimension(i6, this.mCollapseTotalHeight);
        }
        progressBar = this.mProgressView;
        if (progressBar != null) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int iMax = Math.max(this.mTitleMinHeight, this.mMainContainer.getMeasuredHeight());
        View view = this.mCustomNavView;
        if (view != null && view.getParent() == this) {
            iMax = Math.max(iMax, this.mCustomNavView.getMeasuredHeight());
        }
        int i6 = iMax;
        int i7 = this.mCollapseSecondaryTabHeight;
        int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
        int i8 = this.mMovableSecondaryTabHeight;
        if (this.mInnerExpandState == 2) {
            i5 = this.mPendingHeight;
        } else {
            i5 = this.mInnerExpandState == 1 ? measuredHeight + i8 : 0;
        }
        int i9 = (i4 - i2) - i8;
        int i10 = i9 - i5;
        float fMin = (hasTitle() || measuredHeight != 0) ? Math.min(1.0f, ((measuredHeight + i8) - i5) / measuredHeight) : 1.0f;
        onLayoutCollapseViews(z, i, 0, i3, i6, i7);
        float f = fMin;
        onLayoutExpandViews(z, i, i10, i3, i9, i8, f);
        notifyMenuStateChange();
        if (!this.mInActionMode && !this.mInActionModeAnimating) {
            animateLayoutWithProcess(f);
        }
        this.mLastProcess = f;
        updateBadgeOnMenuItemViews();
    }

    private void animateLayoutWithProcess(float f) {
        float fMin = 1.0f - Math.min(1.0f, 3.0f * f);
        int i = this.mInnerExpandState;
        if (i == 2) {
            if (this.mLastProcess == f) {
                this.mLastResizingProcess = fMin;
                return;
            }
            if (fMin > 0.0f) {
                if (this.mIsCollapseTitleShowingOnResizing) {
                    this.mIsCollapseTitleShowingOnResizing = false;
                    this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
                    if (this.mActionBarTransitionListeners.size() > 0) {
                        Folme.useValue(TypedValues.AttributesType.S_TARGET, 0).setFlags(1L).setup(1).setTo("expand", Integer.valueOf(this.mTransitionTarget)).to("expand", 20, this.mHideProcessConfig);
                    }
                    this.mMovableController.setVisibility(0);
                }
            } else if (!this.mIsCollapseTitleShowingOnResizing) {
                this.mIsCollapseTitleShowingOnResizing = true;
                this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
                if (this.mActionBarTransitionListeners.size() > 0) {
                    Folme.useValue(TypedValues.AttributesType.S_TARGET, 0).setFlags(1L).setup(0).setTo("collapse", Integer.valueOf(this.mTransitionTarget)).to("collapse", 0, this.mShowProcessConfig);
                }
                this.mCollapseController.setVisibility(0);
            }
            if (this.mLastResizingProcess != fMin) {
                this.mMovableController.animTo(fMin, 0, 0, this.mMovableAnimNormalConfig);
                this.mLastResizingProcess = fMin;
                return;
            }
            return;
        }
        if (i == 1) {
            this.mNeedRequestLayoutOnExpandTitleShowing = this.mLastResizingProcess == 0.0f;
            this.mTransitionTarget = 20;
            this.mLastResizingProcess = 1.0f;
            this.mIsCollapseTitleShowingOnResizing = false;
            if (this.mLastProcess == f) {
                return;
            }
            this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            this.mMovableController.animTo(1.0f, 0, 0, this.mMovableAnimShowConfig);
            return;
        }
        if (i == 0) {
            this.mNeedRequestLayoutOnExpandTitleShowing = false;
            this.mTransitionTarget = 0;
            this.mLastResizingProcess = 0.0f;
            this.mIsCollapseTitleShowingOnResizing = true;
            if (this.mLastProcess == f) {
                return;
            }
            this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
            this.mMovableController.animTo(0.0f, 0, 0, this.mMovableAnimNormalConfig);
        }
    }

    private boolean hasTitle() {
        return !((this.mDisplayOptions & 8) == 0 || isAllTitlesEmpty()) || getNavigationMode() == 2;
    }

    private boolean canCollapseTitleBeShown() {
        if (this.mCollapseTitle == null || TextUtils.isEmpty(this.mTitle)) {
            return false;
        }
        boolean zCanTitleBeShown = this.mCollapseTitle.canTitleBeShown(this.mTitle.toString());
        if (!ActionBarPolicy.get(this.mContext).isTitleEnableEllipsis() || zCanTitleBeShown) {
            return zCanTitleBeShown;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:121:0x01f1  */
    private void onLayoutCollapseViews(boolean z, int i, int i2, int i3, int i4, int i5) {
        int iPositionChild;
        int i6;
        int i7;
        int paddingBottom;
        View view;
        int iPositionChildWithOffset;
        int i8;
        View view2;
        int measuredWidth;
        int measuredWidth2;
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        FrameLayout frameLayout = this.mMainContainer;
        FrameLayout frameLayout2 = this.mCollapseTabContainer;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = (i4 - getPaddingTop()) - getPaddingBottom();
        if (paddingTop2 <= 0) {
            return;
        }
        int paddingEnd = (i3 - i) - getPaddingEnd();
        ActionMenuView actionMenuView = this.mEndMenuView;
        if (actionMenuView != null && actionMenuView.getParent() == this && this.mEndMenuView.getVisibility() != 8) {
            positionChildInverse(this.mEndMenuView, paddingEnd, paddingTop, paddingTop2);
            paddingEnd -= this.mEndMenuView.getMeasuredWidth();
        }
        View view3 = this.mEndView;
        if (view3 != null && view3.getVisibility() != 8) {
            positionChildInverse(this.mEndView, paddingEnd, paddingTop, paddingTop2);
            paddingEnd -= this.mEndView.getMeasuredWidth();
        }
        ProgressBar progressBar = this.mIndeterminateProgressView;
        if (progressBar != null && progressBar.getVisibility() != 8) {
            positionChildInverse(this.mIndeterminateProgressView, paddingEnd - this.mProgressBarPadding, paddingTop, paddingTop2);
            paddingEnd -= this.mIndeterminateProgressView.getMeasuredWidth() - (this.mProgressBarPadding * 2);
        }
        int marginEnd = paddingEnd;
        View view4 = this.mNavigatorSwitch;
        int iPositionChild2 = (view4 == null || view4.getVisibility() == 8) ? 0 : positionChild(this.mNavigatorSwitch, paddingStart, paddingTop, paddingTop2, false);
        HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        if (homeView != null && homeView.getVisibility() == 0) {
            int startOffset = homeView.getStartOffset();
            iPositionChild = paddingStart + positionChild(homeView, paddingStart + startOffset, paddingTop, paddingTop2, false) + startOffset;
        } else {
            View view5 = this.mStartView;
            iPositionChild = (view5 == null || view5.getVisibility() == 8) ? paddingStart : paddingStart + positionChild(this.mStartView, paddingStart, paddingTop, paddingTop2, false);
        }
        if (this.mExpandedActionView == null) {
            boolean zIsShowTitle = isShowTitle();
            if ((zIsShowTitle || (getDisplayOptions() & 32) != 0) && (view = this.mTitleUpView) != null && view.getVisibility() == 0) {
                if (this.mHasStartView || this.mHasNavigatorSwitchView) {
                    iPositionChildWithOffset = positionChildWithOffset(this.mTitleUpView, iPositionChild, paddingTop, paddingTop2, false, this.mTitleUpViewMarginStart);
                } else {
                    iPositionChildWithOffset = positionChild(this.mTitleUpView, iPositionChild, paddingTop, paddingTop2, false);
                }
                iPositionChild += iPositionChildWithOffset;
            }
            if (!zIsShowTitle || frameLayout == null) {
                i8 = paddingTop2;
            } else {
                int iPositionChild3 = iPositionChild2 + iPositionChild;
                if (isTitleCenter()) {
                    int iComputeTitleCenterLayoutStart = frameLayout.getVisibility() != 8 ? computeTitleCenterLayoutStart(frameLayout) : iPositionChild3;
                    this.mCollapseTitleShowable = canCollapseTitleBeShown();
                    updateTightTitle();
                    int measuredWidth3 = frameLayout.getMeasuredWidth() + iComputeTitleCenterLayoutStart;
                    if (frameLayout.getMeasuredWidth() + iPositionChild3 > marginEnd) {
                        measuredWidth2 = marginEnd;
                    } else {
                        if (measuredWidth3 > marginEnd) {
                            measuredWidth = marginEnd - frameLayout.getMeasuredWidth();
                            paddingTop2 = paddingTop2;
                            measuredWidth2 = marginEnd;
                        } else if (iComputeTitleCenterLayoutStart < iPositionChild3) {
                            measuredWidth2 = iPositionChild3 + frameLayout.getMeasuredWidth();
                        } else {
                            measuredWidth = iComputeTitleCenterLayoutStart;
                            paddingTop2 = paddingTop2;
                            measuredWidth2 = measuredWidth3;
                        }
                        int measuredHeight = frameLayout.getMeasuredHeight();
                        int i9 = ((paddingTop2 - measuredHeight) / 2) + paddingTop;
                        i8 = paddingTop2;
                        ViewUtils.layoutChildView(this, frameLayout, measuredWidth, i9, measuredWidth2, measuredHeight + i9);
                    }
                    measuredWidth = iPositionChild3;
                    int measuredHeight2 = frameLayout.getMeasuredHeight();
                    int i10 = ((paddingTop2 - measuredHeight2) / 2) + paddingTop;
                    i8 = paddingTop2;
                    ViewUtils.layoutChildView(this, frameLayout, measuredWidth, i10, measuredWidth2, measuredHeight2 + i10);
                } else {
                    i8 = paddingTop2;
                    iPositionChild3 += positionChild(frameLayout, this.mTitleGapPaddingStart + iPositionChild3, paddingTop, i8);
                }
                iPositionChild = iPositionChild3;
            }
            if (this.mNavigationMode == 1 && (view2 = this.mListNavLayout) != null) {
                if (zIsShowTitle) {
                    iPositionChild += this.mItemPadding;
                }
                iPositionChild += positionChild(view2, iPositionChild, paddingTop, i8) + this.mItemPadding;
            }
        }
        View view6 = this.mExpandedActionView;
        if (view6 == null && ((this.mDisplayOptions & 16) == 0 || (view6 = this.mCustomNavView) == null)) {
            view6 = null;
        }
        if (view6 != null && view6.getVisibility() != 8) {
            ViewGroup.LayoutParams layoutParams = view6.getLayoutParams();
            ActionBar.LayoutParams layoutParams2 = layoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParams : null;
            int i11 = layoutParams2 != null ? layoutParams2.gravity : DEFAULT_CUSTOM_GRAVITY;
            int measuredWidth4 = view6.getMeasuredWidth();
            if (layoutParams2 != null) {
                iPositionChild += layoutParams2.getMarginStart();
                marginEnd -= layoutParams2.getMarginEnd();
                i7 = layoutParams2.topMargin;
                i6 = layoutParams2.bottomMargin;
            } else {
                i6 = 0;
                i7 = 0;
            }
            int i12 = 8388615 & i11;
            if (i12 == 1) {
                int width = (getWidth() - measuredWidth4) / 2;
                if (width < iPositionChild) {
                    i12 = 8388611;
                } else if (width + measuredWidth4 > marginEnd) {
                    i12 = 8388613;
                }
            } else if (i11 == -1) {
                i12 = 8388611;
            }
            int paddingStart2 = getPaddingStart();
            int iNormalizeHorizontalGravity = normalizeHorizontalGravity(i12, zIsLayoutRtl);
            if (iNormalizeHorizontalGravity == 1) {
                iPositionChild = (getWidth() - measuredWidth4) / 2;
            } else if (iNormalizeHorizontalGravity != 8388611) {
                iPositionChild = iNormalizeHorizontalGravity != 8388613 ? paddingStart2 : marginEnd - measuredWidth4;
            }
            int i13 = i11 & 112;
            if (i11 == -1) {
                i13 = 16;
            }
            if (i13 == 16) {
                paddingBottom = ((((this.mCollapseTotalHeight - i5) - getPaddingBottom()) - getPaddingTop()) - view6.getMeasuredHeight()) / 2;
            } else if (i13 == 48) {
                paddingBottom = getPaddingTop() + i7;
            } else {
                paddingBottom = i13 != 80 ? 0 : (((this.mCollapseTotalHeight - i5) - getPaddingBottom()) - view6.getMeasuredHeight()) - i6;
            }
            int measuredWidth5 = view6.getMeasuredWidth();
            view6.layout(zIsLayoutRtl ? (getWidth() - iPositionChild) - measuredWidth5 : iPositionChild, paddingBottom, zIsLayoutRtl ? getWidth() - iPositionChild : measuredWidth5 + iPositionChild, view6.getMeasuredHeight() + paddingBottom);
        }
        ProgressBar progressBar2 = this.mProgressView;
        if (progressBar2 != null) {
            progressBar2.bringToFront();
            int measuredHeight3 = this.mProgressView.getMeasuredHeight() / 2;
            ProgressBar progressBar3 = this.mProgressView;
            int i14 = this.mProgressBarPadding;
            progressBar3.layout(i14, -measuredHeight3, progressBar3.getMeasuredWidth() + i14, measuredHeight3);
        }
        if (i5 > 0) {
            int i15 = this.mExtraPadding + this.mUncollapseTabPaddingH;
            ViewUtils.layoutChildView(this, frameLayout2, i15, i4, i15 + frameLayout2.getMeasuredWidth(), i4 + i5);
        }
    }

    private void notifyMenuStateChange() {
        if (!this.mSplitActionBarEnable || this.mMenuView == null) {
            return;
        }
        ((ActionBarOverlayLayout) this.mSplitView.getParent()).onMenuStateChanged((int) (this.mMenuView.getCollapsedHeight() - this.mMenuView.getTranslationY()), 0);
    }

    protected void onLayoutExpandViews(boolean z, int i, int i2, int i3, int i4, int i5, float f) {
        int i6;
        int measuredWidth;
        if (hasTitle()) {
            FrameLayout frameLayout = this.mMovableMainContainer;
            FrameLayout frameLayout2 = this.mMovableTabContainer;
            int i7 = 1.0f - Math.min(1.0f, 3.0f * f) <= 0.0f ? this.mCollapseSecondaryTabHeight : 0;
            int measuredHeight = (frameLayout == null || frameLayout.getVisibility() != 0) ? 0 : frameLayout.getMeasuredHeight();
            int i8 = this.mMovableSecondaryTabHeight;
            int i9 = i7 + (((i2 + measuredHeight) + i8) - i4);
            if (frameLayout != null && frameLayout.getVisibility() == 0 && this.mInnerExpandState != 0) {
                frameLayout.layout(i, i4 - measuredHeight, i3, i4);
                ScrollingTabContainerView scrollingTabContainerView = hasTabsInContainer(this.mMovableMainContainer) ? (ScrollingTabContainerView) this.mMovableMainContainer.getChildAt(0) : null;
                if (scrollingTabContainerView != null) {
                    int measuredWidth2 = this.mUncollapsePaddingH;
                    if (ViewUtils.isLayoutRtl(this)) {
                        measuredWidth2 = (i3 - this.mUncollapsePaddingH) - scrollingTabContainerView.getMeasuredWidth();
                    }
                    scrollingTabContainerView.layout(measuredWidth2, this.mExpandTabTopPadding, scrollingTabContainerView.getMeasuredWidth() + measuredWidth2, scrollingTabContainerView.getMeasuredHeight() + this.mExpandTabTopPadding);
                }
                clipViewBounds(this.mMovableMainContainer, i, i9, i3, measuredHeight + i8);
            }
            if (i8 <= 0 || this.mInnerExpandState == 0) {
                return;
            }
            int i10 = i + this.mUncollapseTabPaddingH + this.mExtraPadding;
            int i11 = i4 + i5;
            ViewUtils.layoutChildView(this, frameLayout2, i10, i11 - i8, i10 + frameLayout2.getMeasuredWidth(), i11);
            ScrollingTabContainerView scrollingTabContainerView2 = hasTabsInContainer(frameLayout2) ? (ScrollingTabContainerView) frameLayout2.getChildAt(0) : null;
            if (scrollingTabContainerView2 != null) {
                int measuredWidth3 = scrollingTabContainerView2.getMeasuredWidth();
                if (ViewUtils.isLayoutRtl(this)) {
                    measuredWidth = (i3 - (this.mUncollapseTabPaddingH * 2)) - scrollingTabContainerView2.getMeasuredWidth();
                    i6 = i3 - (this.mUncollapseTabPaddingH * 2);
                } else {
                    i6 = measuredWidth3;
                    measuredWidth = 0;
                }
                scrollingTabContainerView2.layout(measuredWidth, 0, i6, scrollingTabContainerView2.getMeasuredHeight());
            }
            clipViewBounds(frameLayout2, i, measuredHeight >= i8 ? i9 - (measuredHeight - i8) : i9 - measuredHeight, i3, measuredHeight + i8);
        }
    }

    private void clipViewBounds(View view, int i, int i2, int i3, int i4) {
        Rect rect = new Rect();
        rect.set(i, i2, i3, i4);
        view.setClipBounds(rect);
    }

    private int computeTitleCenterLayoutStart(View view) {
        int width = (getWidth() - view.getMeasuredWidth()) / 2;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return width - (layoutParams instanceof LinearLayout.LayoutParams ? ((LinearLayout.LayoutParams) layoutParams).getMarginStart() : 0);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ActionBar.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams == null ? generateDefaultLayoutParams() : layoutParams;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        } else {
            savedState.expandedMenuItemId = 0;
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        savedState.isEndOverflowOpen = isEndOverflowMenuShowing();
        if (this.mInnerExpandState == 2) {
            savedState.expandState = 0;
        } else {
            savedState.expandState = this.mInnerExpandState;
        }
        savedState.userSetExpandState = this.mUserSetExpandState;
        savedState.userExpandState = this.mUserExpandState;
        savedState.applyBlur = this.mApplyBgBlur;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuBuilder menuBuilder;
        MenuItem menuItemFindItem;
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && (menuBuilder = this.mOptionsMenu) != null && (menuItemFindItem = menuBuilder.findItem(savedState.expandedMenuItemId)) != null) {
                menuItemFindItem.expandActionView();
            }
            if (savedState.isOverflowOpen) {
                postShowOverflowMenu();
            }
            if (savedState.isEndOverflowOpen) {
                postShowEndOverflowMenu();
            }
            if (this.mUserExpandState == -1) {
                this.mUserSetExpandState = savedState.userSetExpandState;
                this.mUserExpandState = savedState.userExpandState;
                setExpandState(isUserSetExpandState() ? this.mUserExpandState : savedState.expandState, false, false);
            }
            if (savedState.applyBlur) {
                setApplyBgBlur(this.mApplyBgBlur);
                return;
            }
            return;
        }
        Log.w(TAG, "Wrong state class, expecting SavedState! This usually happens when two views of different type have the same id in the same hierarchy.");
        super.onRestoreInstanceState(parcelable);
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(drawable);
        } else {
            this.mHomeAsUpIndicatorDrawable = drawable;
            this.mHomeAsUpIndicatorResId = 0;
        }
    }

    public void setHomeAsUpIndicator(int i) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(i);
        } else {
            this.mHomeAsUpIndicatorDrawable = null;
            this.mHomeAsUpIndicatorResId = i;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.appcompat.internal.app.widget.ActionBarView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        boolean applyBlur;
        int expandState;
        int expandedMenuItemId;
        boolean isEndOverflowOpen;
        boolean isOverflowOpen;
        int userExpandState;
        boolean userSetExpandState;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
            this.isEndOverflowOpen = parcel.readInt() != 0;
            this.expandState = parcel.readInt();
            this.userSetExpandState = parcel.readInt() != 0;
            this.userExpandState = parcel.readInt();
            this.applyBlur = parcel.readInt() != 0;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
            this.isEndOverflowOpen = parcel.readInt() != 0;
            this.expandState = parcel.readInt();
            this.userSetExpandState = parcel.readInt() != 0;
            this.userExpandState = parcel.readInt();
            this.applyBlur = parcel.readInt() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
            parcel.writeInt(this.isEndOverflowOpen ? 1 : 0);
            parcel.writeInt(this.expandState);
            parcel.writeInt(this.userSetExpandState ? 1 : 0);
            parcel.writeInt(this.userExpandState);
            parcel.writeInt(this.applyBlur ? 1 : 0);
        }
    }

    private static class HomeView extends FrameLayout {
        private Drawable mDefaultUpIndicator;
        private int mHorizontalPadding;
        private ImageView mIconView;
        private int mUpIndicatorRes;
        private ImageView mUpView;

        public int getStartOffset() {
            return 0;
        }

        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mHorizontalPadding = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_view_padding_horizontal);
        }

        public void setUp(boolean z) {
            this.mUpView.setVisibility(z ? 0 : 8);
        }

        public void setIcon(Drawable drawable) {
            this.mIconView.setImageDrawable(drawable);
        }

        public void setUpIndicator(Drawable drawable) {
            ImageView imageView = this.mUpView;
            if (drawable == null) {
                drawable = this.mDefaultUpIndicator;
            }
            imageView.setImageDrawable(drawable);
            this.mUpIndicatorRes = 0;
        }

        public void setUpIndicator(int i) {
            this.mUpIndicatorRes = i;
            this.mUpView.setImageDrawable(i != 0 ? getResources().getDrawable(i) : null);
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            int i = this.mUpIndicatorRes;
            if (i != 0) {
                setUpIndicator(i);
            }
        }

        @Override // android.view.View
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            CharSequence contentDescription = getContentDescription();
            if (TextUtils.isEmpty(contentDescription)) {
                return true;
            }
            accessibilityEvent.getText().add(contentDescription);
            return true;
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            this.mUpView = (ImageView) findViewById(R.id.up);
            this.mIconView = (ImageView) findViewById(R.id.home);
            ImageView imageView = this.mUpView;
            if (imageView != null) {
                this.mDefaultUpIndicator = imageView.getDrawable();
                Folme.useAt(this.mUpView).hover().setFeedbackRadius(60.0f);
                Folme.useAt(this.mUpView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mUpView, new AnimConfig[0]);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            measureChildWithMargins(this.mUpView, i, 0, i2, 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            int measuredWidth = this.mUpView.getVisibility() == 8 ? 0 : layoutParams.leftMargin + this.mUpView.getMeasuredWidth() + layoutParams.rightMargin;
            int measuredHeight = layoutParams.topMargin + this.mUpView.getMeasuredHeight() + layoutParams.bottomMargin;
            measureChildWithMargins(this.mIconView, i, measuredWidth, i2, 0);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredWidth2 = measuredWidth + (this.mIconView.getVisibility() != 8 ? layoutParams2.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams2.rightMargin : 0);
            if (measuredWidth2 > 0) {
                measuredWidth2 += this.mHorizontalPadding;
            }
            int iMax = Math.max(measuredHeight, layoutParams2.topMargin + this.mIconView.getMeasuredHeight() + layoutParams2.bottomMargin);
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == Integer.MIN_VALUE) {
                measuredWidth2 = Math.min(measuredWidth2, size);
            } else if (mode == 1073741824) {
                measuredWidth2 = size;
            }
            if (mode2 == Integer.MIN_VALUE) {
                iMax = Math.min(iMax, size2);
            } else if (mode2 == 1073741824) {
                iMax = size2;
            }
            setMeasuredDimension(measuredWidth2, iMax);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7;
            int i8;
            int i9 = (i4 - i2) / 2;
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
            if (this.mUpView.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int measuredHeight = this.mUpView.getMeasuredHeight();
                int measuredWidth = this.mUpView.getMeasuredWidth();
                int i10 = i9 - (measuredHeight / 2);
                ViewUtils.layoutChildView(this, this.mUpView, 0, i10, measuredWidth, measuredHeight + i10);
                i5 = layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin;
                if (zIsLayoutRtl) {
                    i7 = i5;
                    i8 = i3 - i5;
                    i6 = i;
                } else {
                    i6 = i + i5;
                }
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
                int measuredHeight2 = this.mIconView.getMeasuredHeight();
                int measuredWidth2 = this.mIconView.getMeasuredWidth();
                int iMax = i7 + Math.max(layoutParams2.getMarginStart(), (((i8 - i6) - this.mHorizontalPadding) / 2) - (measuredWidth2 / 2));
                int iMax2 = Math.max(layoutParams2.topMargin, i9 - (measuredHeight2 / 2));
                ViewUtils.layoutChildView(this, this.mIconView, iMax, iMax2, iMax + measuredWidth2, measuredHeight2 + iMax2);
            }
            i5 = 0;
            i6 = i;
            i7 = i5;
            i8 = i3;
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredHeight3 = this.mIconView.getMeasuredHeight();
            int measuredWidth3 = this.mIconView.getMeasuredWidth();
            int iMax3 = i7 + Math.max(layoutParams3.getMarginStart(), (((i8 - i6) - this.mHorizontalPadding) / 2) - (measuredWidth3 / 2));
            int iMax4 = Math.max(layoutParams3.topMargin, i9 - (measuredHeight3 / 2));
            ViewUtils.layoutChildView(this, this.mIconView, iMax3, iMax4, iMax3 + measuredWidth3, measuredHeight3 + iMax4);
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (menuBuilder2 != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            return;
                        }
                    }
                }
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            ActionBarView.this.initExpandedHomeLayout();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.getIcon().getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            ViewParent parent = ActionBarView.this.mExpandedActionView.getParent();
            ActionBarView actionBarView = ActionBarView.this;
            if (parent != actionBarView) {
                actionBarView.addView(actionBarView.mExpandedActionView);
            }
            ViewParent parent2 = ActionBarView.this.mExpandedHomeLayout.getParent();
            ActionBarView actionBarView2 = ActionBarView.this;
            if (parent2 != actionBarView2) {
                actionBarView2.addView(actionBarView2.mExpandedHomeLayout);
            }
            if (ActionBarView.this.mHomeLayout != null) {
                ActionBarView.this.mHomeLayout.setVisibility(8);
            }
            if (ActionBarView.this.mCollapseTitle != null) {
                ActionBarView.this.setTitleVisibility(false);
            }
            if (ActionBarView.this.mCollapseTabs != null) {
                ActionBarView.this.mCollapseTabs.setVisibility(8);
            }
            if (ActionBarView.this.mExpandTabs != null) {
                ActionBarView.this.mExpandTabs.setVisibility(8);
            }
            if (ActionBarView.this.mSecondaryCollapseTabs != null) {
                ActionBarView.this.mSecondaryCollapseTabs.asViewGroup().setVisibility(8);
            }
            if (ActionBarView.this.mSecondaryExpandTabs != null) {
                ActionBarView.this.mSecondaryExpandTabs.asViewGroup().setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewExpanded();
            }
            ActionBarView.this.updateBackInvokedCallbackState();
            return true;
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewCollapsed();
            }
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.removeView(actionBarView.mExpandedActionView);
            ActionBarView actionBarView2 = ActionBarView.this;
            actionBarView2.removeView(actionBarView2.mExpandedHomeLayout);
            ActionBarView.this.mExpandedActionView = null;
            if ((ActionBarView.this.mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mCollapseTitle == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.setTitleVisibility(true);
                }
            }
            if (ActionBarView.this.mCollapseTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mCollapseTabs.setVisibility(0);
            }
            if (ActionBarView.this.mExpandTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mExpandTabs.setVisibility(0);
            }
            if (ActionBarView.this.mSecondaryCollapseTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mSecondaryCollapseTabs.asViewGroup().setVisibility(0);
            }
            if (ActionBarView.this.mSecondaryExpandTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mSecondaryExpandTabs.asViewGroup().setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null && ActionBarView.this.mNavigationMode == 1) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (ActionBarView.this.mCustomNavView != null && (ActionBarView.this.mDisplayOptions & 16) != 0) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            ActionBarView.this.updateBackInvokedCallbackState();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitleVisibility(boolean z) {
        CollapseTitle collapseTitle = this.mCollapseTitle;
        int i = 8;
        if (collapseTitle != null) {
            collapseTitle.setVisibility(z ? 0 : 8);
        }
        ExpandTitle expandTitle = this.mExpandTitle;
        if (expandTitle != null) {
            expandTitle.setVisibility(z ? 0 : 4);
        }
        if (this.mTitleUpView != null && (getDisplayOptions() & 32) == 0) {
            int i2 = this.mDisplayOptions;
            boolean z2 = (i2 & 4) != 0;
            boolean z3 = (i2 & 2) != 0;
            View view = this.mTitleUpView;
            if (!z3) {
                i = z2 ? 0 : 4;
            }
            view.setVisibility(i);
        }
        int i3 = TextUtils.isEmpty(this.mSubtitle) ? this.mExpandTitlePaddingBottom : this.mExpandSubtitlePaddingBottom;
        FrameLayout frameLayout = this.mMovableMainContainer;
        frameLayout.setPaddingRelative(frameLayout.getPaddingStart(), this.mMovableMainContainer.getPaddingTop(), this.mMovableMainContainer.getPaddingEnd(), i3);
    }

    protected ActionMenuPresenter createActionMenuPresenter(MenuPresenter.Callback callback, boolean z) {
        ActionMenuPresenter actionMenuPresenter;
        ActionBarOverlayLayout actionBarOverlayLayoutFindActionBarOverlayLayout = findActionBarOverlayLayout();
        if (z) {
            actionMenuPresenter = new HyperSplitActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, R.layout.miuix_appcompat_responsive_action_menu_layout, R.layout.miuix_appcompat_action_menu_item_layout);
        } else {
            actionMenuPresenter = new ActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, R.layout.miuix_appcompat_responsive_action_menu_layout, R.layout.miuix_appcompat_action_menu_item_layout);
        }
        actionMenuPresenter.setCallback(callback);
        actionMenuPresenter.setId(R.id.action_menu_presenter);
        return actionMenuPresenter;
    }

    protected EndActionMenuPresenter createEndActionMenuPresenter(MenuPresenter.Callback callback, boolean z) {
        EndActionMenuPresenter endActionMenuPresenter;
        ActionBarOverlayLayout actionBarOverlayLayoutFindActionBarOverlayLayout = findActionBarOverlayLayout();
        if (z) {
            endActionMenuPresenter = new HyperActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, R.layout.miuix_appcompat_action_end_menu_layout, R.layout.miuix_appcompat_action_end_menu_item_layout, R.layout.miuix_appcompat_action_bar_expanded_menu_layout, R.layout.miuix_appcompat_action_bar_list_menu_item_layout);
        } else {
            endActionMenuPresenter = new EndActionMenuPresenter(this.mContext, actionBarOverlayLayoutFindActionBarOverlayLayout, R.layout.miuix_appcompat_action_end_menu_layout, R.layout.miuix_appcompat_action_end_menu_item_layout, R.layout.miuix_appcompat_action_bar_expanded_menu_layout, R.layout.miuix_appcompat_action_bar_list_menu_item_layout);
        }
        endActionMenuPresenter.setCallback(callback);
        endActionMenuPresenter.setId(R.id.miuix_action_end_menu_presenter);
        return endActionMenuPresenter;
    }

    private ActionBarOverlayLayout findActionBarOverlayLayout() {
        Object parent = getParent();
        while (true) {
            View view = (View) parent;
            if (!(view instanceof ActionBarOverlayLayout)) {
                if (view.getParent() instanceof View) {
                    parent = view.getParent();
                } else {
                    throw new IllegalStateException("ActionBarOverlayLayout not found");
                }
            } else {
                return (ActionBarOverlayLayout) view;
            }
        }
    }

    protected ExpandedActionViewMenuPresenter createExpandedActionViewMenuPresenter() {
        return new ExpandedActionViewMenuPresenter();
    }

    public void onWindowShow() {
        this.mSplitView.onWindowShow();
    }

    public void onWindowHide() {
        this.mSplitView.onWindowHide();
    }

    public void setProgressBarVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    public void setProgressBarIndeterminateVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    public void setProgressBarIndeterminate(boolean z) {
        updateProgressBars(z ? -3 : -4);
    }

    public void setProgress(int i) {
        updateProgressBars(i);
    }

    private void updateProgressBars(int i) {
        ProgressBar circularProgressBar = getCircularProgressBar();
        ProgressBar horizontalProgressBar = getHorizontalProgressBar();
        if (i == -1) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(0);
                return;
            }
            return;
        }
        if (i == -2) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility(8);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(8);
                return;
            }
            return;
        }
        if (i == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
            }
        } else if (i == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
            }
        } else {
            if (i < 0 || i > 10000 || horizontalProgressBar == null) {
                return;
            }
            horizontalProgressBar.setProgress(i);
            if (i < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
            }
        }
    }

    private void showProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if (progressBar == null || progressBar.getProgress() >= 10000) {
            return;
        }
        progressBar.setVisibility(0);
    }

    private void hideProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.setVisibility(4);
        }
        if (progressBar == null || progressBar.getVisibility() != 0) {
            return;
        }
        progressBar.setVisibility(4);
    }

    private ProgressBar getCircularProgressBar() {
        ProgressBar progressBar = this.mIndeterminateProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    private ProgressBar getHorizontalProgressBar() {
        ProgressBar progressBar = this.mProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getIcon() {
        if ((this.mIconLogoInitIndicator & 1) != 1) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    this.mIcon = context.getPackageManager().getActivityIcon(((Activity) this.mContext).getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "Activity component name not found!", e);
                }
            }
            if (this.mIcon == null) {
                this.mIcon = this.mContext.getApplicationInfo().loadIcon(this.mContext.getPackageManager());
            }
            this.mIconLogoInitIndicator |= 1;
        }
        return this.mIcon;
    }

    private Drawable getLogo() {
        if ((this.mIconLogoInitIndicator & 2) != 2) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    this.mLogo = context.getPackageManager().getActivityLogo(((Activity) this.mContext).getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "Activity component name not found!", e);
                }
            }
            if (this.mLogo == null) {
                this.mLogo = this.mContext.getApplicationInfo().loadLogo(this.mContext.getPackageManager());
            }
            this.mIconLogoInitIndicator |= 2;
        }
        return this.mLogo;
    }

    private void initHomeLayout() {
        if (this.mHomeLayout == null) {
            HomeView homeView = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mHomeLayout = homeView;
            homeView.setOnClickListener(this.mUpClickListener);
            this.mHomeLayout.setClickable(true);
            this.mHomeLayout.setFocusable(true);
            int i = this.mHomeAsUpIndicatorResId;
            if (i != 0) {
                this.mHomeLayout.setUpIndicator(i);
                this.mHomeAsUpIndicatorResId = 0;
            }
            Drawable drawable = this.mHomeAsUpIndicatorDrawable;
            if (drawable != null) {
                this.mHomeLayout.setUpIndicator(drawable);
                this.mHomeAsUpIndicatorDrawable = null;
            }
            addView(this.mHomeLayout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initExpandedHomeLayout() {
        if (this.mExpandedHomeLayout == null) {
            HomeView homeView = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mExpandedHomeLayout = homeView;
            homeView.setUp(true);
            this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    protected void onExpandStateChanged(int i, int i2) {
        AbsActionBarView.CollapseView collapseView;
        if (i == 2) {
            this.mPendingHeight = 0;
            if (!this.mPostScroller.isFinished()) {
                this.mPostScroller.forceFinished(true);
            }
        }
        if (i2 == 2 && (collapseView = this.mMovableController) != null) {
            collapseView.setVisibility(0);
        }
        if (i2 == 1) {
            if (this.mMovableMainContainer.getAlpha() > 0.0f) {
                AbsActionBarView.CollapseView collapseView2 = this.mCollapseController;
                if (collapseView2 != null) {
                    collapseView2.setAnimFrom(0.0f, 0, 20, true);
                }
                AbsActionBarView.CollapseView collapseView3 = this.mMovableController;
                if (collapseView3 != null) {
                    collapseView3.setAnimFrom(1.0f, 0, 0, true);
                }
            }
            AbsActionBarView.CollapseView collapseView4 = this.mMovableController;
            if (collapseView4 != null) {
                collapseView4.setVisibility(0);
            }
        }
        if (i2 == 0) {
            AbsActionBarView.CollapseView collapseView5 = this.mCollapseController;
            if (collapseView5 != null && !this.mInActionMode) {
                collapseView5.setAnimFrom(1.0f, 0, 0, true);
                this.mCollapseController.setVisibility(0);
                this.mCollapseController.onShow();
            }
            AbsActionBarView.CollapseView collapseView6 = this.mMovableController;
            if (collapseView6 != null) {
                collapseView6.setVisibility(8);
            }
        } else {
            this.mPendingHeight = (getHeight() - this.mCollapseTotalHeight) + this.mCollapseSecondaryTabHeight;
        }
        if (this.mActionBarTransitionListeners.size() > 0) {
            if (this.mExpandStateBeforeResizing == i2 && this.mExpandStateOnLayout == i2) {
                return;
            }
            for (ActionBarTransitionListener actionBarTransitionListener : this.mActionBarTransitionListeners) {
                if (i2 == 1) {
                    actionBarTransitionListener.onExpandStateChanged(1);
                } else if (i2 == 0) {
                    actionBarTransitionListener.onExpandStateChanged(0);
                }
            }
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    protected void onAnimatedExpandStateChanged(int i, int i2) {
        IStateStyle iStateStyle = this.mStateChangeAnimStateStyle;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
        if (i == 1) {
            this.mPendingHeight = this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight;
        } else if (i == 0) {
            this.mPendingHeight = 0;
        }
        AnimConfig animConfigAddListeners = new AnimConfig().addListeners(new InnerTransitionListener(this));
        int measuredHeight = i2 == 1 ? this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight : 0;
        if (i2 == 1) {
            this.mCollapseController.setVisibility(4);
        } else if (i2 == 0) {
            this.mCollapseController.setVisibility(0);
        }
        this.mStateChangeAnimStateStyle = Folme.useValue(new Object[0]).setFlags(1L).setTo("actionbar_state_change", Integer.valueOf(this.mPendingHeight)).to("actionbar_state_change", Integer.valueOf(measuredHeight), animConfigAddListeners);
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) {
        if (canConsumeScroll()) {
            int measuredHeight = this.mMovableMainContainer.getMeasuredHeight() + this.mMovableSecondaryTabHeight;
            if (!hasTitle() && (this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
                measuredHeight = 0;
            }
            int i6 = (this.mCollapseTotalHeight - this.mCollapseSecondaryTabHeight) + measuredHeight;
            int height = getHeight();
            if (i4 >= 0 || height >= i6) {
                return;
            }
            int i7 = this.mPendingHeight;
            if (height - i4 <= i6) {
                this.mPendingHeight = i7 - i4;
                iArr[1] = iArr[1] + i4;
            } else {
                this.mPendingHeight = measuredHeight;
                iArr[1] = iArr[1] + (-(i6 - height));
            }
            if (this.mPendingHeight != i7) {
                if (this.mInnerExpandState != 2) {
                    if (!this.mPostScroller.isFinished()) {
                        this.mPostScroller.forceFinished(true);
                    }
                    setExpandState(2);
                }
                iArr2[1] = i4;
                requestLayout();
            }
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return this.mExpandedActionView == null || this.mCustomNavView != null;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        if (canConsumeScroll()) {
            if (i2 == 0) {
                this.mTouchScrolling = true;
            } else {
                this.mNonTouchScrolling = true;
            }
            if (!this.mPostScroller.isFinished()) {
                this.mPostScroller.forceFinished(true);
                Runnable runnable = this.mPostScroll;
                if (runnable != null) {
                    removeCallbacks(runnable);
                }
            }
            setExpandState(2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0015  */
    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onStopNestedScroll(View view, int i) {
        boolean z;
        if (this.mTouchScrolling) {
            this.mTouchScrolling = false;
            if (this.mNonTouchScrolling) {
                z = false;
            } else {
                z = true;
            }
        } else if (this.mNonTouchScrolling) {
            this.mNonTouchScrolling = false;
            z = true;
        } else {
            z = false;
        }
        if (canConsumeScroll()) {
            int measuredHeight = this.mMovableMainContainer.getMeasuredHeight();
            int height = getHeight();
            if (z) {
                int i2 = this.mPendingHeight;
                if (i2 == 0) {
                    setExpandState(0);
                    return;
                }
                int i3 = this.mMovableSecondaryTabHeight;
                if (i2 == measuredHeight + i3) {
                    setExpandState(1);
                    return;
                }
                int i4 = this.mCollapseTotalHeight;
                if (height > ((i3 + measuredHeight) / 2) + i4) {
                    this.mPostScroller.startScroll(0, height, 0, (i4 + measuredHeight) - height);
                } else {
                    this.mPostScroller.startScroll(0, height, 0, i4 - height);
                }
                this.mHandler.postDelayed(this.mPostAnimationRunnable, 20L);
            }
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3, int[] iArr2) {
        int i4;
        if (canConsumeScroll()) {
            int height = getHeight();
            if (i2 <= 0 || height <= (i4 = this.mCollapseTotalHeight)) {
                return;
            }
            int i5 = height - i2;
            int i6 = this.mPendingHeight;
            if (i5 >= i4) {
                this.mPendingHeight = i6 - i2;
            } else {
                this.mPendingHeight = 0;
            }
            iArr[1] = iArr[1] + i2;
            if (this.mPendingHeight != i6) {
                if (this.mInnerExpandState != 2) {
                    if (!this.mPostScroller.isFinished()) {
                        this.mPostScroller.forceFinished(true);
                    }
                    setExpandState(2);
                }
                iArr2[1] = i2;
                requestLayout();
            }
        }
    }

    private boolean canConsumeScroll() {
        return (isShowTitle() || this.mCustomNavView != null) && isResizable();
    }

    public void onActionModeStart(boolean z, boolean z2) {
        this.mInActionMode = true;
        this.mInSearchMode = z;
        if (!z) {
            this.mCollapseController.setVisibility(8);
            this.mMovableController.setVisibility(8);
            setVisibility(8);
        } else {
            this.mCollapseController.setAlpha(0.0f);
            this.mMovableController.setAlpha(0.0f);
        }
        View view = this.mStartView;
        if (view != null) {
            view.setAlpha(0.0f);
        }
        View view2 = this.mEndView;
        if (view2 != null) {
            view2.setAlpha(0.0f);
        }
        View view3 = this.mTitleUpView;
        if (view3 != null) {
            view3.setAlpha(0.0f);
        }
        View view4 = this.mNavigatorSwitch;
        if (view4 != null) {
            NavigatorSwitchPresenter navigatorSwitchPresenter = (NavigatorSwitchPresenter) view4.getTag(R.id.miuix_appcompat_navigator_switch_presenter);
            if (navigatorSwitchPresenter != null) {
                navigatorSwitchPresenter.suppressAlpha(true, 0.0f);
            } else {
                this.mNavigatorSwitch.setAlpha(0.0f);
            }
        }
        if (z2) {
            this.mMovableController.setAcceptAlphaChange(false);
            this.mCollapseController.setAcceptAlphaChange(false);
        }
    }

    public void onActionModeEnd(boolean z) {
        this.mInActionMode = false;
        if (!this.mInSearchMode) {
            setAlpha(0.0f);
            setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f);
            objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
            objectAnimatorOfFloat.setDuration(300L);
            objectAnimatorOfFloat.start();
        }
        this.mInSearchMode = false;
        if (getExpandState() == 0) {
            this.mCollapseController.setVisibility(0);
            this.mMovableController.setVisibility(8);
        } else if (getExpandState() == 1) {
            this.mCollapseController.setVisibility(4);
            this.mMovableController.setVisibility(0);
        }
        View view = this.mStartView;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        View view2 = this.mEndView;
        if (view2 != null) {
            view2.setAlpha(1.0f);
        }
        View view3 = this.mTitleUpView;
        if (view3 != null) {
            view3.setAlpha(1.0f);
        }
        View view4 = this.mNavigatorSwitch;
        if (view4 != null) {
            NavigatorSwitchPresenter navigatorSwitchPresenter = (NavigatorSwitchPresenter) view4.getTag(R.id.miuix_appcompat_navigator_switch_presenter);
            if (navigatorSwitchPresenter != null) {
                navigatorSwitchPresenter.suppressAlpha(false, 0.0f);
            } else {
                this.mNavigatorSwitch.setAlpha(1.0f);
            }
        }
        if (z) {
            this.mMovableController.setAcceptAlphaChange(true);
            this.mCollapseController.setAcceptAlphaChange(true);
            postRefreshTitleControllerStatus();
        }
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onStart(boolean z) {
        this.mInActionModeAnimating = true;
        if (z) {
            this.mDoContainerShowAnimInFinishActionMode = false;
            return;
        }
        if (getExpandState() == 0) {
            this.mCollapseController.setVisibility(0);
            this.mCollapseController.setAlpha(0.0f);
            this.mMovableController.setVisibility(8);
        } else if (getExpandState() == 1) {
            this.mCollapseController.setVisibility(4);
            this.mMovableController.setVisibility(0);
            this.mMovableController.setAlpha(0.0f);
        }
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onUpdate(boolean z, float f) {
        if (this.mDoContainerShowAnimInFinishActionMode || z || f <= 0.8f) {
            return;
        }
        this.mDoContainerShowAnimInFinishActionMode = true;
        showContainerInFinishActionMode();
    }

    @Override // miuix.view.ActionModeAnimationListener
    public void onStop(boolean z) {
        this.mInActionModeAnimating = false;
        if (z) {
            this.mCollapseController.setVisibility(4);
            this.mMovableController.setVisibility(4);
        } else {
            if (!this.mDoContainerShowAnimInFinishActionMode) {
                showContainerInFinishActionMode();
            }
            this.mDoContainerShowAnimInFinishActionMode = false;
        }
    }

    private void showContainerInFinishActionMode() {
        if (getExpandState() == 0) {
            this.mCollapseController.animTo(1.0f, 0, 0, this.mMovableAnimNormalConfig);
        } else if (getExpandState() == 1) {
            this.mCollapseController.setAlpha(0.0f);
            this.mCollapseController.setVisibility(0);
            this.mMovableController.animTo(1.0f, 0, 0, this.mMovableAnimShowConfig);
        }
    }

    private static class InnerTransitionListener extends TransitionListener {
        private WeakReference<ActionBarView> mRef;

        public InnerTransitionListener(ActionBarView actionBarView) {
            this.mRef = new WeakReference<>(actionBarView);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
            ActionBarView actionBarView;
            super.onUpdate(obj, collection);
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "actionbar_state_change");
            if (updateInfoFindByName == null || (actionBarView = this.mRef.get()) == null) {
                return;
            }
            actionBarView.mPendingHeight = updateInfoFindByName.getIntValue();
            actionBarView.requestLayout();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            this.mRef.clear();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            super.onCancel(obj);
            this.mRef.clear();
        }
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public CollapseTitle getCollapseTitle() {
        if (this.mCollapseTitle == null) {
            createCollapseTitle(true);
        }
        return this.mCollapseTitle;
    }

    @Override // miuix.appcompat.internal.app.widget.AbsActionBarView
    public ExpandTitle getExpandTitle() {
        if (this.mExpandTitle == null) {
            createExpandTitle(true);
        }
        return this.mExpandTitle;
    }

    public void setApplyBgBlur(boolean z) {
        if (this.mApplyBgBlur != z) {
            this.mApplyBgBlur = z;
            SecondaryTabBar secondaryTabBar = this.mSecondaryCollapseTabs;
            if (secondaryTabBar != null) {
                secondaryTabBar.setParentBlurEnabled(z);
            }
            SecondaryTabBar secondaryTabBar2 = this.mSecondaryExpandTabs;
            if (secondaryTabBar2 != null) {
                secondaryTabBar2.setParentBlurEnabled(z);
            }
        }
    }
}
