package com.miui.common.tool.softinputhelper;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.view.WindowInsetsCompat;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.KeyboardHelper;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class PhoneHybridKeyboardHeightProvider extends BaseKeyboardHeightProvider {
    private boolean mStartKeyboardStatus;

    public PhoneHybridKeyboardHeightProvider(View view, View view2, KeyboardHeightListener keyboardHeightListener) {
        super(view, view2, keyboardHeightListener);
        this.mStartKeyboardStatus = false;
        view2.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.common.tool.softinputhelper.PhoneHybridKeyboardHeightProvider.1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view3, WindowInsets windowInsets) {
                if (!PhoneHybridKeyboardHeightProvider.this.mEnable) {
                    return windowInsets;
                }
                Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
                boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                Insets insets2 = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                if (BaseNoteApp.INSTANCE.isFreeFormScreen()) {
                    PhoneHybridKeyboardHeightProvider.this.mImeHeight = insets2.bottom;
                } else if (BaseNoteApp.INSTANCE.isMultiWindowMode()) {
                    zIsVisible = PhoneHybridKeyboardHeightProvider.this.handleMultiWindowMode(insets, insets2, zIsVisible);
                    PhoneHybridKeyboardHeightProvider.this.logMultiWindowStatus(insets, insets2, zIsVisible);
                } else {
                    PhoneHybridKeyboardHeightProvider.this.handleNormalMode(insets, insets2, zIsVisible);
                }
                KeyboardHelper.INSTANCE.setShowKeyboard(zIsVisible);
                Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets final imeShow: " + zIsVisible + ", mImeHeight: " + PhoneHybridKeyboardHeightProvider.this.mImeHeight);
                PhoneHybridKeyboardHeightProvider phoneHybridKeyboardHeightProvider = PhoneHybridKeyboardHeightProvider.this;
                phoneHybridKeyboardHeightProvider.notifyKeyboardHeightChanged(windowInsets, phoneHybridKeyboardHeightProvider.mImeHeight, PhoneHybridKeyboardHeightProvider.this.getScreenOrientation());
                PhoneHybridKeyboardHeightProvider phoneHybridKeyboardHeightProvider2 = PhoneHybridKeyboardHeightProvider.this;
                phoneHybridKeyboardHeightProvider2.notifyKeyboardHeightChangedEnd(phoneHybridKeyboardHeightProvider2.getScreenOrientation(), windowInsets);
                return view3.onApplyWindowInsets(windowInsets);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMultiWindowMode(Insets insets, Insets insets2, boolean z) {
        if (insets2.bottom == 0 || insets.bottom > insets2.bottom) {
            this.mStartKeyboardStatus = true;
        }
        if (this.mStartKeyboardStatus) {
            this.mImeHeight = insets.bottom;
        } else if (z) {
            this.mImeHeight = insets2.bottom;
        }
        if (z && this.mStartKeyboardStatus && this.mImeHeight < insets2.bottom) {
            return false;
        }
        if (!z) {
            this.mStartKeyboardStatus = false;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logMultiWindowStatus(Insets insets, Insets insets2, boolean z) {
        Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets imeInset.bottom: " + insets.bottom + " systemBarInset.bottom: " + insets2.bottom);
        Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets mStartKeyboardStatus: " + this.mStartKeyboardStatus + " imeShow: " + z);
        Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets imeHeight: " + this.mImeHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNormalMode(Insets insets, Insets insets2, boolean z) {
        if (z && insets.bottom <= insets2.bottom) {
            this.mImeHeight = insets2.bottom;
        } else if (z) {
            this.mImeHeight = insets.bottom;
        } else {
            this.mImeHeight = 0;
        }
        Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets else imeShow: " + z);
        Logger.INSTANCE.d("PhoneHybridKeyboardHeightProvider", "onApplyWindowInsets else imeHeight: " + this.mImeHeight);
    }

    @Override // com.miui.common.tool.softinputhelper.BaseKeyboardHeightProvider
    public void release() {
        notifyKeyboardHeightChanged(null, 0, getScreenOrientation());
        this.mDecorView.setOnApplyWindowInsetsListener(null);
        removeKeyboardHeightListener();
    }
}
