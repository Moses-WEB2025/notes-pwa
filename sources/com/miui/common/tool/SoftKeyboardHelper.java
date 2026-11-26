package com.miui.common.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.notes.ai.utils.FreeModeUtils;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SoftKeyboardHelper {

    public interface KeyboardCloseListener {
        void onEnd();
    }

    public static void listenKeyboardClose(Context context, final View view, final KeyboardCloseListener keyboardCloseListener) {
        Activity activity = UIUtils.getActivity(context);
        if (activity != null && (FreeModeUtils.isFreeFormScreen(activity.getResources().getConfiguration()) || activity.isInMultiWindowMode())) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.common.tool.SoftKeyboardHelper.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    if (!windowInsetsCompat.isVisible(WindowInsetsCompat.Type.ime())) {
                        keyboardCloseListener.onEnd();
                        ViewCompat.setOnApplyWindowInsetsListener(view, null);
                    }
                    return windowInsetsCompat;
                }
            });
        } else {
            ViewCompat.setWindowInsetsAnimationCallback(view, new WindowInsetsAnimationCompat.Callback(1) { // from class: com.miui.common.tool.SoftKeyboardHelper.2
                private boolean isKeyboardShow = false;

                @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
                public void onPrepare(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
                    super.onPrepare(windowInsetsAnimationCompat);
                }

                @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
                public WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List<WindowInsetsAnimationCompat> list) {
                    this.isKeyboardShow = windowInsetsCompat.isVisible(WindowInsetsCompat.Type.ime());
                    return windowInsetsCompat;
                }

                @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
                public void onEnd(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
                    if (!this.isKeyboardShow) {
                        keyboardCloseListener.onEnd();
                        ViewCompat.setWindowInsetsAnimationCallback(view, null);
                    }
                    super.onEnd(windowInsetsAnimationCompat);
                }
            });
        }
    }
}
