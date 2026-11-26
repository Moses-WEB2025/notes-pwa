package com.miui.notes.ai.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import com.miui.notes.base.utils.Logger;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class KeyboardListenerHelper {
    private static int IME_MASK = 8;
    int mImeHeight;
    private IKeyBoardChangedListener mKeyBoardChangedListener;
    View mRootView;
    int mTargetHeight;
    boolean mTargetImeVisible;

    public interface IKeyBoardChangedListener {
        void onHideKeyboard();

        void onKeyBoardChanged(int i, boolean z);

        void onKeyBoardChangedEnd(int i, boolean z);

        void onPrepare();

        void onShowKeyboard(int i);
    }

    public KeyboardListenerHelper(View view) {
        this.mRootView = view;
    }

    public void addListener() {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mRootView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.notes.ai.utils.KeyboardListenerHelper.1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    int i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                    Logger.INSTANCE.d("KeyboardListenerHelper", "onApplyWindowInsets IME_MASK: " + windowInsets.isVisible(KeyboardListenerHelper.IME_MASK) + " ,imeHeight:" + i);
                    if (windowInsets.isVisible(KeyboardListenerHelper.IME_MASK) && i >= 0) {
                        if (KeyboardListenerHelper.this.mKeyBoardChangedListener != null) {
                            KeyboardListenerHelper.this.mKeyBoardChangedListener.onShowKeyboard(i);
                            return windowInsets;
                        }
                    } else if (!windowInsets.isVisible(KeyboardListenerHelper.IME_MASK) && KeyboardListenerHelper.this.mKeyBoardChangedListener != null) {
                        KeyboardListenerHelper.this.mKeyBoardChangedListener.onHideKeyboard();
                    }
                    return windowInsets;
                }
            });
            this.mRootView.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(1) { // from class: com.miui.notes.ai.utils.KeyboardListenerHelper.2
                @Override // android.view.WindowInsetsAnimation.Callback
                public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                    if (KeyboardListenerHelper.this.mKeyBoardChangedListener != null) {
                        KeyboardListenerHelper.this.mKeyBoardChangedListener.onPrepare();
                    }
                    super.onPrepare(windowInsetsAnimation);
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                    if (KeyboardListenerHelper.this.mRootView.getRootWindowInsets() != null) {
                        int i = KeyboardListenerHelper.this.mRootView.getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
                        KeyboardListenerHelper keyboardListenerHelper = KeyboardListenerHelper.this;
                        keyboardListenerHelper.mTargetImeVisible = keyboardListenerHelper.mRootView.getRootWindowInsets().isVisible(KeyboardListenerHelper.IME_MASK);
                        KeyboardListenerHelper.this.mTargetHeight = i;
                    }
                    return super.onStart(windowInsetsAnimation, bounds);
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                    int i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                    if (KeyboardListenerHelper.this.mKeyBoardChangedListener != null) {
                        KeyboardListenerHelper.this.mKeyBoardChangedListener.onKeyBoardChanged(i, KeyboardListenerHelper.this.mTargetImeVisible);
                    }
                    return windowInsets;
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                    if (KeyboardListenerHelper.this.mRootView.getRootWindowInsets() != null) {
                        int i = KeyboardListenerHelper.this.mRootView.getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
                        if (KeyboardListenerHelper.this.mKeyBoardChangedListener != null) {
                            KeyboardListenerHelper.this.mKeyBoardChangedListener.onKeyBoardChangedEnd(i, KeyboardListenerHelper.this.mTargetImeVisible);
                        }
                    }
                    super.onEnd(windowInsetsAnimation);
                }
            });
        }
    }

    public void removeListener() {
        this.mRootView.setOnApplyWindowInsetsListener(null);
        if (Build.VERSION.SDK_INT >= 30) {
            this.mRootView.setWindowInsetsAnimationCallback(null);
        }
    }

    public void setKeyBoardChangedListener(IKeyBoardChangedListener iKeyBoardChangedListener) {
        this.mKeyBoardChangedListener = iKeyBoardChangedListener;
    }

    public static boolean isHardwareKeyboard(Context context) {
        return context.getResources().getConfiguration().keyboard == 2;
    }
}
