package xc.richtext.utils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import xc.richtext.view.img.CsImageView;
import xc.richtext.view.text.CsEditText;

/* JADX INFO: loaded from: classes2.dex */
public class CsUtil {
    public static int getRealTextColor(int i, boolean z) {
        if (z) {
            if (i == -16777216 || i == -16777216) {
                return -1;
            }
            if (i == -1) {
                return -16777216;
            }
        }
        return i;
    }

    public static void closeSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isSpanView(View view) {
        return (view instanceof CsEditText) || (view instanceof CsImageView);
    }
}
