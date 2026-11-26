package com.miui.handwriting.common.softinputhelper;

import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RKeyboardHeightProvider extends BaseKeyboardHeightProvider {
    private String TAG;

    public RKeyboardHeightProvider(View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        super(view, view2, keyboardHeightListener);
        this.TAG = "RKeyboardHeightProvider";
        this.mContentView.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(1) { // from class: com.miui.handwriting.common.softinputhelper.RKeyboardHeightProvider.1
            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                if (RKeyboardHeightProvider.this.mEnable) {
                    Log.d(RKeyboardHeightProvider.this.TAG, "onPrepare");
                    super.onPrepare(windowInsetsAnimation);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                if (RKeyboardHeightProvider.this.mEnable) {
                    Log.d(RKeyboardHeightProvider.this.TAG, "onStart bounds " + bounds.toString());
                    return super.onStart(windowInsetsAnimation, bounds);
                }
                return super.onStart(windowInsetsAnimation, bounds);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (RKeyboardHeightProvider.this.mEnable && windowInsetsAnimation.getFraction() >= 1.0f) {
                    super.onEnd(windowInsetsAnimation);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                if (!RKeyboardHeightProvider.this.mEnable) {
                    return windowInsets;
                }
                int i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                if (i <= 0) {
                    i = 0;
                }
                RKeyboardHeightProvider rKeyboardHeightProvider = RKeyboardHeightProvider.this;
                rKeyboardHeightProvider.notifyKeyboardHeightChangedProgress(i, rKeyboardHeightProvider.getScreenOrientation());
                return windowInsets;
            }
        });
        this.mDecorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.handwriting.common.softinputhelper.RKeyboardHeightProvider.2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view3, WindowInsets windowInsets) {
                if (!RKeyboardHeightProvider.this.mEnable) {
                    return windowInsets;
                }
                int i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                Log.d(RKeyboardHeightProvider.this.TAG, "decorView onApplyWindowInsets imeHeight = " + i);
                Log.d(RKeyboardHeightProvider.this.TAG, "decorView v = " + view3.toString());
                if (i <= 0) {
                    i = 0;
                }
                if (RKeyboardHeightProvider.this.mContentView != null) {
                    RKeyboardHeightProvider rKeyboardHeightProvider = RKeyboardHeightProvider.this;
                    rKeyboardHeightProvider.notifyKeyboardHeightChanged(i, rKeyboardHeightProvider.getScreenOrientation());
                }
                return view3.onApplyWindowInsets(windowInsets);
            }
        });
    }

    @Override // com.miui.handwriting.common.softinputhelper.BaseKeyboardHeightProvider
    public void release() {
        notifyKeyboardHeightChanged(0, getScreenOrientation());
        this.mDecorView.setOnApplyWindowInsetsListener(null);
        this.mContentView.setWindowInsetsAnimationCallback(null);
    }
}
