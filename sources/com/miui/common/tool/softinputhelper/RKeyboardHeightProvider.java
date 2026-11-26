package com.miui.common.tool.softinputhelper;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import androidx.core.view.WindowInsetsCompat;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.KeyboardHelper;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class RKeyboardHeightProvider extends BaseKeyboardHeightProvider {
    private final MainHandler<Activity> handler;
    private boolean mAnimationStart;
    private boolean mStartKeyboardStatus;

    public RKeyboardHeightProvider(final View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        super(view, view2, keyboardHeightListener);
        this.mStartKeyboardStatus = false;
        this.mAnimationStart = false;
        this.handler = new MainHandler<>(Looper.getMainLooper(), null);
        view.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(1) { // from class: com.miui.common.tool.softinputhelper.RKeyboardHeightProvider.1
            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                if (RKeyboardHeightProvider.this.mEnable) {
                    RKeyboardHeightProvider.this.mAnimationStart = true;
                    super.onPrepare(windowInsetsAnimation);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                return super.onStart(windowInsetsAnimation, bounds);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (RKeyboardHeightProvider.this.mEnable) {
                    RKeyboardHeightProvider.this.mAnimationStart = false;
                    WindowInsets rootWindowInsets = view.getRootWindowInsets();
                    if (rootWindowInsets == null) {
                        Logger.INSTANCE.d("RKeyboardHeightProvider", "rootWindowInsets null");
                        return;
                    }
                    boolean zIsVisible = rootWindowInsets.isVisible(WindowInsets.Type.ime());
                    KeyboardHelper.INSTANCE.setShowKeyboard(zIsVisible);
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onEnd imeShow: " + zIsVisible);
                    if (!zIsVisible) {
                        RKeyboardHeightProvider.this.mStartKeyboardStatus = false;
                        RKeyboardHeightProvider.this.mImeHeight = 0;
                        RKeyboardHeightProvider rKeyboardHeightProvider = RKeyboardHeightProvider.this;
                        rKeyboardHeightProvider.notifyKeyboardHeightChanged(rootWindowInsets, 0, rKeyboardHeightProvider.getScreenOrientation());
                    } else {
                        Insets insets = rootWindowInsets.getInsets(WindowInsets.Type.ime());
                        Insets insets2 = rootWindowInsets.getInsets(WindowInsets.Type.systemBars());
                        int i = insets.bottom <= insets2.bottom ? insets2.bottom : insets.bottom;
                        RKeyboardHeightProvider.this.mStartKeyboardStatus = true;
                        RKeyboardHeightProvider.this.mImeHeight = i;
                        RKeyboardHeightProvider rKeyboardHeightProvider2 = RKeyboardHeightProvider.this;
                        rKeyboardHeightProvider2.notifyKeyboardHeightChanged(rootWindowInsets, i, rKeyboardHeightProvider2.getScreenOrientation());
                    }
                    super.onEnd(windowInsetsAnimation);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                if (!RKeyboardHeightProvider.this.mEnable) {
                    return windowInsets;
                }
                Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
                boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                Insets insets2 = windowInsets.getInsets(WindowInsets.Type.navigationBars());
                int i = insets.bottom - insets.top;
                if (insets.bottom >= insets2.bottom) {
                    RKeyboardHeightProvider.this.mStartKeyboardStatus = true;
                }
                if (!RKeyboardHeightProvider.this.mStartKeyboardStatus) {
                    i = insets2.bottom;
                }
                if (zIsVisible && RKeyboardHeightProvider.this.mStartKeyboardStatus && insets.bottom < insets2.bottom) {
                    zIsVisible = false;
                } else if (!zIsVisible) {
                    RKeyboardHeightProvider.this.mStartKeyboardStatus = false;
                    i = 0;
                }
                Logger.INSTANCE.d("RKeyboardHeightProvider", "onProgress imeInset.bottom: " + insets.bottom + " systemBarInset.bottom: " + insets2.bottom);
                Logger.INSTANCE.d("RKeyboardHeightProvider", "onProgress mStartKeyboardStatus: " + RKeyboardHeightProvider.this.mStartKeyboardStatus + "imeShow: " + zIsVisible);
                Logger.INSTANCE.d("RKeyboardHeightProvider", "onProgress imeHeight" + i);
                RKeyboardHeightProvider.this.mImeHeight = i;
                KeyboardHelper.INSTANCE.setShowKeyboard(zIsVisible);
                RKeyboardHeightProvider rKeyboardHeightProvider = RKeyboardHeightProvider.this;
                rKeyboardHeightProvider.notifyKeyboardHeightChanged(windowInsets, i, rKeyboardHeightProvider.getScreenOrientation());
                return windowInsets;
            }
        });
        view2.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.common.tool.softinputhelper.RKeyboardHeightProvider.2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view3, WindowInsets windowInsets) {
                if (!RKeyboardHeightProvider.this.mEnable || RKeyboardHeightProvider.this.mAnimationStart) {
                    return windowInsets;
                }
                Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
                boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                Insets insets2 = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                if (BaseNoteApp.INSTANCE.isFreeFormScreen()) {
                    RKeyboardHeightProvider.this.mImeHeight = insets2.bottom;
                } else if (BaseNoteApp.INSTANCE.isMultiWindowMode()) {
                    if (insets2.bottom == 0 || insets.bottom > insets2.bottom) {
                        RKeyboardHeightProvider.this.mStartKeyboardStatus = true;
                    }
                    if (RKeyboardHeightProvider.this.mStartKeyboardStatus) {
                        RKeyboardHeightProvider.this.mImeHeight = insets.bottom;
                    } else if (zIsVisible) {
                        RKeyboardHeightProvider.this.mImeHeight = insets2.bottom;
                    }
                    if (zIsVisible && RKeyboardHeightProvider.this.mStartKeyboardStatus && RKeyboardHeightProvider.this.mImeHeight < insets2.bottom) {
                        zIsVisible = false;
                    } else if (!zIsVisible) {
                        RKeyboardHeightProvider.this.mStartKeyboardStatus = false;
                    }
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets imeInset.bottom: " + insets.bottom + " systemBarInset.bottom: " + insets2.bottom);
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets mStartKeyboardStatus: " + RKeyboardHeightProvider.this.mStartKeyboardStatus + "imeShow: " + zIsVisible);
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets imeHeight: " + RKeyboardHeightProvider.this.mImeHeight);
                } else {
                    if (zIsVisible && insets.bottom <= insets2.bottom) {
                        RKeyboardHeightProvider.this.mImeHeight = insets2.bottom;
                    } else if (zIsVisible) {
                        RKeyboardHeightProvider.this.mImeHeight = insets.bottom;
                    } else {
                        RKeyboardHeightProvider.this.mImeHeight = 0;
                    }
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets else imeShow: " + zIsVisible);
                    Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets else imeHeight: " + RKeyboardHeightProvider.this.mImeHeight);
                }
                KeyboardHelper.INSTANCE.setShowKeyboard(zIsVisible);
                Logger.INSTANCE.d("RKeyboardHeightProvider", "onApplyWindowInsets final imeShow: " + zIsVisible + ", mImeHeight: " + RKeyboardHeightProvider.this.mImeHeight);
                RKeyboardHeightProvider rKeyboardHeightProvider = RKeyboardHeightProvider.this;
                rKeyboardHeightProvider.notifyKeyboardHeightChanged(windowInsets, rKeyboardHeightProvider.mImeHeight, RKeyboardHeightProvider.this.getScreenOrientation());
                RKeyboardHeightProvider rKeyboardHeightProvider2 = RKeyboardHeightProvider.this;
                rKeyboardHeightProvider2.notifyKeyboardHeightChangedEnd(rKeyboardHeightProvider2.getScreenOrientation(), windowInsets);
                return view3.onApplyWindowInsets(windowInsets);
            }
        });
    }

    @Override // com.miui.common.tool.softinputhelper.BaseKeyboardHeightProvider
    public void release() {
        notifyKeyboardHeightChanged(null, 0, getScreenOrientation());
        this.mDecorView.setOnApplyWindowInsetsListener(null);
        this.mContentView.setWindowInsetsAnimationCallback(null);
        this.handler.removeCallbacksAndMessages(null);
        removeKeyboardHeightListener();
    }
}
