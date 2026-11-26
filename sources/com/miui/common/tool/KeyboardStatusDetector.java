package com.miui.common.tool;

import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.fragment.app.Fragment;
import com.miui.notes.NoteApp;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardStatusDetector {
    private KeyboardAnimStateListener mAnimStateListener;
    private int mNavigationHeight;
    private int mOffsetY;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private int mStatusBarHeight;
    private KeyboardVisibilityListener mVisibilityListener;
    private Window mWindow;
    private int mMinKeyBoardHeight = 0;
    private int mMinKeyBoardHeightInMultiDown = 0;
    private boolean mKeyboardVisible = false;
    private boolean mForbidListenWindowChange = false;
    private boolean mForbidRootViewPaddingBottomChange = false;
    private boolean isInMultiWindowMode = false;
    private boolean isInSmallWindowMode = false;
    private boolean isInMultiWindowDown = false;
    private boolean isInFullWindowGestureMode = false;
    private View mRegisterView = null;
    private boolean mEnableListenImeAnim = false;
    private boolean mRWindowAnimStart = false;
    private boolean mRWindowAnimProgress = false;
    protected int mRKeyboardHeightOnEnd = 0;
    private ValueAnimator mPaddingAnimator = null;

    public interface KeyboardAnimStateListener {
        void onEnd();

        void onPrepare();

        void onProgress();

        void onStart();
    }

    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean z);
    }

    public void registerFragment(Fragment fragment) {
        if (isAboveAndroidR()) {
            registerViewAboveAndroidR(fragment.getView());
        } else {
            registerView(fragment.getView());
        }
        this.mWindow = fragment.getActivity().getWindow();
        this.isInMultiWindowMode = UIUtils.isInMultiWindowMode(fragment.getActivity());
        this.isInSmallWindowMode = UIUtils.isSmallWindowMode(fragment.getActivity());
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(fragment.getActivity());
        initWindowParam();
        this.mWindow.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.common.tool.KeyboardStatusDetector.1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Logger.INSTANCE.i("EditFragment", " onApplyWindowInsets = " + windowInsets.toString());
                int iAbs = Math.abs(windowInsets.getSystemWindowInsetBottom() - windowInsets.getSystemWindowInsetTop());
                if (KeyboardStatusDetector.this.isInMultiWindowMode) {
                    if (iAbs == KeyboardStatusDetector.this.mNavigationHeight) {
                        KeyboardStatusDetector.this.isInMultiWindowDown = true;
                    } else if (iAbs == KeyboardStatusDetector.this.mStatusBarHeight) {
                        KeyboardStatusDetector.this.isInMultiWindowDown = false;
                    }
                }
                KeyboardStatusDetector.this.mMinKeyBoardHeight = windowInsets.getStableInsetBottom() + windowInsets.getStableInsetTop();
                Logger.INSTANCE.i("EditFragment", "onApplyWindowInsets mMinKeyBoardHeight = " + KeyboardStatusDetector.this.mMinKeyBoardHeight);
                if (KeyboardStatusDetector.this.isAboveAndroidR() && !KeyboardStatusDetector.this.mForbidListenWindowChange) {
                    boolean zIsVisible = view.getRootWindowInsets().isVisible(WindowInsets.Type.ime());
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                    Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
                    int i = insets2.bottom;
                    if (!KeyboardStatusDetector.this.isInFullWindowGestureMode || KeyboardStatusDetector.this.isInMultiWindowMode) {
                        i = insets2.bottom - insets.bottom;
                    }
                    KeyboardStatusDetector.this.mRKeyboardHeightOnEnd = Math.max(0, i);
                    Logger.INSTANCE.d("KeyBoardAnimCallBack", "onApplyWindowInsets imeHeight = " + i);
                    if (i <= 0) {
                        KeyboardStatusDetector.this.mEnableListenImeAnim = false;
                    } else {
                        KeyboardStatusDetector.this.mEnableListenImeAnim = true;
                    }
                    if (!KeyboardStatusDetector.this.mRWindowAnimStart && KeyboardStatusDetector.this.mRegisterView != null) {
                        View view2 = (View) KeyboardStatusDetector.this.mRegisterView.getParent();
                        if (KeyboardStatusDetector.this.isInSmallWindowMode) {
                            KeyboardStatusDetector.this.mKeyboardVisible = zIsVisible;
                            if (KeyboardStatusDetector.this.mVisibilityListener != null && !zIsVisible) {
                                KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(false);
                            }
                            KeyboardStatusDetector.this.setPaddingBottom(0);
                        } else if (view2 != null) {
                            if (i > 0) {
                                int paddingBottom = view2.getPaddingBottom();
                                Logger.INSTANCE.d("KeyBoardAnimCallBack", "onApplyWindowInsets paddingBottomNow = " + paddingBottom);
                                if (paddingBottom != i) {
                                    KeyboardStatusDetector.this.setPaddingBottomWithAnim(i);
                                }
                                if (i > KeyboardStatusDetector.this.mMinKeyBoardHeight && !KeyboardStatusDetector.this.mKeyboardVisible) {
                                    KeyboardStatusDetector.this.mKeyboardVisible = true;
                                    if (KeyboardStatusDetector.this.mVisibilityListener != null) {
                                        KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(true);
                                    }
                                }
                            } else {
                                if (KeyboardStatusDetector.this.mKeyboardVisible) {
                                    KeyboardStatusDetector.this.mKeyboardVisible = false;
                                }
                                if (KeyboardStatusDetector.this.mVisibilityListener != null && !KeyboardStatusDetector.this.isInMultiWindowMode) {
                                    KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(false);
                                }
                                KeyboardStatusDetector.this.setPaddingBottomWithAnim(0);
                            }
                        }
                    }
                }
                return view.onApplyWindowInsets(windowInsets);
            }
        });
    }

    private void initWindowParam() {
        this.mOffsetY = DisplayUtils.getRealHeight(NoteApp.getInstance()) - UIUtils.getAppHeight(this.mWindow);
        if (!this.isInFullWindowGestureMode) {
            this.mNavigationHeight = UIUtils.getRealNavigationBarHeight(this.mWindow);
        } else {
            this.mNavigationHeight = 0;
        }
        int statusBarHeight = UIUtils.getStatusBarHeight(NoteApp.getInstance());
        this.mStatusBarHeight = statusBarHeight;
        int i = this.mNavigationHeight;
        this.mMinKeyBoardHeight = statusBarHeight + i;
        this.mMinKeyBoardHeightInMultiDown = i;
        Logger.INSTANCE.i("KeyboardStatusDetector", " mNavigationHeight = " + this.mNavigationHeight + " mStatusBarHeight = " + this.mStatusBarHeight);
    }

    public void forbidRootViewPaddingBottomChange(boolean z) {
        this.mForbidRootViewPaddingBottomChange = z;
    }

    public void forbidListenWindowChange(boolean z) {
        this.mForbidListenWindowChange = z;
    }

    public void onResume(Fragment fragment) {
        View view;
        if (isAboveAndroidR() && (view = this.mRegisterView) != null) {
            registerViewAboveAndroidR(view);
        }
        if (fragment == null || !fragment.isAdded()) {
            return;
        }
        this.isInMultiWindowMode = UIUtils.isInMultiWindowMode(fragment.getActivity());
        this.isInSmallWindowMode = UIUtils.isSmallWindowMode(fragment.getActivity());
        if (!this.isInMultiWindowMode) {
            this.isInSmallWindowMode = false;
        }
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(fragment.getActivity());
        initWindowParam();
    }

    public void onPause() {
        if (isAboveAndroidR() && isKeyboardVisible()) {
            unRegisterViewAboveAndroidR();
            setPaddingBottom(0);
        }
    }

    public void setPaddingBottom(int i) {
        View view;
        if (this.mForbidRootViewPaddingBottomChange || (view = this.mRegisterView) == null) {
            return;
        }
        if (i == 0) {
            this.mKeyboardVisible = false;
        }
        ((View) view.getParent()).setPadding(0, 0, 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPaddingBottomWithAnim(int i) {
        View view;
        if (this.mForbidRootViewPaddingBottomChange || (view = this.mRegisterView) == null) {
            return;
        }
        if (i == 0) {
            this.mKeyboardVisible = false;
        }
        View view2 = (View) view.getParent();
        AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new PaddingTransitionListener(view2, i));
        Folme.useValue(view2).setTo("paddingBottom", Integer.valueOf(view2.getPaddingBottom())).to("paddingBottom", Integer.valueOf(i), animConfig);
    }

    private KeyboardStatusDetector registerView(final View view) {
        this.mRegisterView = view;
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.common.tool.KeyboardStatusDetector.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int i;
                if (KeyboardStatusDetector.this.mVisibilityListener == null) {
                    return;
                }
                Rect rect = new Rect();
                View view2 = (View) view.getParent();
                view.getWindowVisibleDisplayFrame(rect);
                int height = view.getRootView().getHeight() - (rect.bottom - rect.top);
                int i2 = KeyboardStatusDetector.this.mMinKeyBoardHeight;
                if (KeyboardStatusDetector.this.isInMultiWindowMode && KeyboardStatusDetector.this.isInMultiWindowDown) {
                    i2 = KeyboardStatusDetector.this.mMinKeyBoardHeightInMultiDown;
                }
                if (height > i2) {
                    i = (height - KeyboardStatusDetector.this.mStatusBarHeight) - KeyboardStatusDetector.this.mNavigationHeight;
                    if (!KeyboardStatusDetector.this.mKeyboardVisible) {
                        KeyboardStatusDetector.this.mKeyboardVisible = true;
                        KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(true);
                    }
                    if (KeyboardStatusDetector.this.needSetPaddingForSoftInput()) {
                        if (KeyboardStatusDetector.this.mPaddingAnimator == null) {
                            KeyboardStatusDetector keyboardStatusDetector = KeyboardStatusDetector.this;
                            keyboardStatusDetector.mPaddingAnimator = keyboardStatusDetector.createPaddingBottomAnimator(view2, view2.getPaddingBottom(), i);
                            KeyboardStatusDetector.this.mPaddingAnimator.setDuration(150L).start();
                        } else if (!KeyboardStatusDetector.this.mPaddingAnimator.isRunning() && i != view2.getPaddingBottom()) {
                            KeyboardStatusDetector keyboardStatusDetector2 = KeyboardStatusDetector.this;
                            keyboardStatusDetector2.mPaddingAnimator = keyboardStatusDetector2.createPaddingBottomAnimator(view2, view2.getPaddingBottom(), i);
                            KeyboardStatusDetector.this.mPaddingAnimator.setDuration(150L).start();
                        }
                    }
                } else {
                    if (KeyboardStatusDetector.this.mKeyboardVisible) {
                        KeyboardStatusDetector.this.mKeyboardVisible = false;
                        KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(false);
                    }
                    if (KeyboardStatusDetector.this.needSetPaddingForSoftInput()) {
                        i = (height - KeyboardStatusDetector.this.mOffsetY) - KeyboardStatusDetector.this.mStatusBarHeight;
                        if (i < 0) {
                            i = 0;
                        }
                        if (view2 != null) {
                            view2.setPadding(0, 0, 0, i);
                        }
                    } else {
                        i = 0;
                    }
                }
                Logger.INSTANCE.i("EditFragment", " paddingBottom = " + i + " heightDiff = " + height + " mMinKeyBoardHeight = " + i2);
            }
        };
        view.post(new Runnable() { // from class: com.miui.common.tool.KeyboardStatusDetector.3
            @Override // java.lang.Runnable
            public void run() {
                view.getViewTreeObserver().addOnGlobalLayoutListener(KeyboardStatusDetector.this.mOnGlobalLayoutListener);
            }
        });
        return this;
    }

    public KeyboardStatusDetector registerViewAboveAndroidR(View view) {
        this.mRegisterView = view;
        view.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(0) { // from class: com.miui.common.tool.KeyboardStatusDetector.4
            private int mImeHeight;
            private float mLastFraction = 0.0f;

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                if (!KeyboardStatusDetector.this.mForbidListenWindowChange) {
                    if (KeyboardStatusDetector.this.mAnimStateListener != null) {
                        KeyboardStatusDetector.this.mAnimStateListener.onProgress();
                    }
                    this.mLastFraction = list.get(0).getFraction();
                    windowInsets.getSystemWindowInsetBottom();
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                    Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
                    int i = insets2.bottom;
                    if (!KeyboardStatusDetector.this.isInFullWindowGestureMode) {
                        i = insets2.bottom - insets.bottom;
                    }
                    if (i <= 0) {
                        i = 0;
                    }
                    if (KeyboardStatusDetector.this.mEnableListenImeAnim || this.mImeHeight != i) {
                        if (i > KeyboardStatusDetector.this.mMinKeyBoardHeight) {
                            if (!KeyboardStatusDetector.this.mKeyboardVisible && i > this.mImeHeight) {
                                KeyboardStatusDetector.this.mKeyboardVisible = true;
                                if (KeyboardStatusDetector.this.mVisibilityListener != null) {
                                    KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(true);
                                }
                            }
                        } else if (KeyboardStatusDetector.this.mKeyboardVisible && i < this.mImeHeight) {
                            KeyboardStatusDetector.this.mKeyboardVisible = false;
                            if (KeyboardStatusDetector.this.mVisibilityListener != null) {
                                KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(false);
                            }
                        }
                        this.mImeHeight = i;
                        if (!KeyboardStatusDetector.this.isInMultiWindowMode) {
                            KeyboardStatusDetector.this.setPaddingBottom(i);
                        }
                    }
                }
                return windowInsets;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                if (KeyboardStatusDetector.this.mForbidListenWindowChange) {
                    super.onPrepare(windowInsetsAnimation);
                    return;
                }
                KeyboardStatusDetector.this.mRWindowAnimStart = true;
                super.onPrepare(windowInsetsAnimation);
                if (KeyboardStatusDetector.this.mAnimStateListener != null) {
                    KeyboardStatusDetector.this.mAnimStateListener.onPrepare();
                }
                Logger.INSTANCE.d("KeyBoardAnimCallBack", "onPrepare " + windowInsetsAnimation.getTypeMask());
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                if (KeyboardStatusDetector.this.mForbidListenWindowChange) {
                    return super.onStart(windowInsetsAnimation, bounds);
                }
                Logger.INSTANCE.d("KeyBoardAnimCallBack", "onStart " + windowInsetsAnimation.getTypeMask() + " bounds " + bounds.toString());
                if (KeyboardStatusDetector.this.mAnimStateListener != null) {
                    KeyboardStatusDetector.this.mAnimStateListener.onStart();
                }
                return super.onStart(windowInsetsAnimation, bounds);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (KeyboardStatusDetector.this.mForbidListenWindowChange) {
                    super.onEnd(windowInsetsAnimation);
                    return;
                }
                KeyboardStatusDetector.this.mRWindowAnimStart = false;
                if (KeyboardStatusDetector.this.mAnimStateListener != null) {
                    KeyboardStatusDetector.this.mAnimStateListener.onEnd();
                }
                super.onEnd(windowInsetsAnimation);
                if (windowInsetsAnimation.getFraction() < 1.0f && KeyboardStatusDetector.this.mKeyboardVisible) {
                    KeyboardStatusDetector.this.mKeyboardVisible = false;
                    if (KeyboardStatusDetector.this.mVisibilityListener != null) {
                        KeyboardStatusDetector.this.mVisibilityListener.onVisibilityChanged(false);
                    }
                    KeyboardStatusDetector.this.setPaddingBottomWithAnim(0);
                }
                Logger.INSTANCE.d("KeyBoardAnimCallBack", "onEnd " + windowInsetsAnimation.getTypeMask());
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ValueAnimator createPaddingBottomAnimator(final View view, int i, int i2) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i, i2);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.common.tool.KeyboardStatusDetector.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(0, 0, 0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        valueAnimatorOfInt.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean needSetPaddingForSoftInput() {
        return !this.isInMultiWindowMode;
    }

    public KeyboardStatusDetector setVisibilityListener(KeyboardVisibilityListener keyboardVisibilityListener) {
        this.mVisibilityListener = keyboardVisibilityListener;
        return this;
    }

    public KeyboardStatusDetector setAnimStateListener(KeyboardAnimStateListener keyboardAnimStateListener) {
        this.mAnimStateListener = keyboardAnimStateListener;
        return this;
    }

    public int getNavigationHeight() {
        return this.mNavigationHeight;
    }

    public void unRegisterViewAboveAndroidR() {
        this.mRegisterView.setWindowInsetsAnimationCallback(null);
    }

    public void unRegister(Fragment fragment) {
        unRegisterView(fragment.getView());
        this.mRegisterView = null;
    }

    public void unRegisterView(View view) {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    public boolean isKeyboardVisible() {
        return this.mKeyboardVisible;
    }

    public void setVisible(boolean z) {
        this.mKeyboardVisible = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAboveAndroidR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    private static class PaddingTransitionListener extends TransitionListener {
        private int mEndPaddingBottom;
        private WeakReference<View> mWkContentView;

        public PaddingTransitionListener(View view, int i) {
            this.mWkContentView = new WeakReference<>(view);
            this.mEndPaddingBottom = i;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
            super.onBegin(obj, collection);
            int intValue = UpdateInfo.findByName(collection, "paddingBottom").getIntValue();
            if (this.mWkContentView.get() != null) {
                this.mWkContentView.get().setPadding(0, 0, 0, intValue);
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
            super.onUpdate(obj, collection);
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "paddingBottom");
            int intValue = updateInfoFindByName != null ? updateInfoFindByName.getIntValue() : 0;
            if (this.mWkContentView.get() != null) {
                this.mWkContentView.get().setPadding(0, 0, 0, intValue);
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (this.mWkContentView.get() != null) {
                this.mWkContentView.get().setPadding(0, 0, 0, this.mEndPaddingBottom);
            }
        }
    }
}
