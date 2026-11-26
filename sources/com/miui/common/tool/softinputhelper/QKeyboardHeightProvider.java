package com.miui.common.tool.softinputhelper;

import android.view.View;
import android.view.WindowInsets;
import com.miui.common.tool.KeyboardHelper;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class QKeyboardHeightProvider extends BaseKeyboardHeightProvider {
    private int mMinKeyBoardHeight;

    public QKeyboardHeightProvider(View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        super(view, view2, keyboardHeightListener);
        this.mMinKeyBoardHeight = 0;
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.common.tool.softinputhelper.QKeyboardHeightProvider$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view3, WindowInsets windowInsets) {
                return this.f$0.lambda$new$0(view3, windowInsets);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsets lambda$new$0(View view, WindowInsets windowInsets) {
        int iAbs = Math.abs(windowInsets.getSystemWindowInsetBottom() - windowInsets.getSystemWindowInsetTop());
        Logger.INSTANCE.i("QKeyboardHeightProvider", "diff = " + iAbs + " onApplyWindowInsets = " + windowInsets.toString());
        int stableInsetBottom = windowInsets.getStableInsetBottom() + windowInsets.getStableInsetTop();
        this.mMinKeyBoardHeight = stableInsetBottom;
        if (iAbs <= stableInsetBottom) {
            this.mImeHeight = 0;
            KeyboardHelper.INSTANCE.setShowKeyboard(false);
            notifyKeyboardHeightChanged(null, 0, getScreenOrientation());
        } else {
            this.mImeHeight = windowInsets.getSystemWindowInsetBottom();
            KeyboardHelper.INSTANCE.setShowKeyboard(true);
            notifyKeyboardHeightChanged(null, this.mImeHeight, getScreenOrientation());
        }
        Logger.INSTANCE.i("QKeyboardHeightProvider", "onApplyWindowInsets mMinKeyBoardHeight = " + this.mMinKeyBoardHeight);
        return view.onApplyWindowInsets(windowInsets);
    }

    @Override // com.miui.common.tool.softinputhelper.BaseKeyboardHeightProvider
    public void release() {
        notifyKeyboardHeightChanged(null, 0, getScreenOrientation());
        this.mDecorView.setOnApplyWindowInsetsListener(null);
        removeKeyboardHeightListener();
    }
}
