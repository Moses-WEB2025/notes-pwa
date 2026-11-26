package com.xiaomi.passport.ui.utils;

import android.R;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.hardware.DataSpace;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public class AdaptUtil {
    public static void fitDeviceNotch(final Activity activity) {
        String str = Build.DEVICE;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        str.hashCode();
        if (str.equals("jinghu") || str.equals("goku")) {
            activity.getWindow().getDecorView().setSystemUiVisibility(R2.dimen.note_share_theme_default_text_padding);
            ViewCompat.setOnApplyWindowInsetsListener(activity.getWindow().getDecorView().findViewById(R.id.content), new OnApplyWindowInsetsListener() { // from class: com.xiaomi.passport.ui.utils.AdaptUtil$$ExternalSyntheticLambda0
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return AdaptUtil.lambda$fitDeviceNotch$0(activity, view, windowInsetsCompat);
                }
            });
        } else if (Build.VERSION.SDK_INT >= 35) {
            ViewCompat.setOnApplyWindowInsetsListener(activity.getWindow().getDecorView().findViewById(R.id.content), new OnApplyWindowInsetsListener() { // from class: com.xiaomi.passport.ui.utils.AdaptUtil$$ExternalSyntheticLambda1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return AdaptUtil.lambda$fitDeviceNotch$1(view, windowInsetsCompat);
                }
            });
        } else {
            activity.getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    static /* synthetic */ WindowInsetsCompat lambda$fitDeviceNotch$0(Activity activity, View view, WindowInsetsCompat windowInsetsCompat) {
        view.setPadding(view.getPaddingLeft(), windowInsetsCompat.getSystemWindowInsetTop(), view.getPaddingRight(), view.getPaddingBottom());
        activity.getWindow().addFlags(DataSpace.RANGE_FULL);
        return windowInsetsCompat;
    }

    static /* synthetic */ WindowInsetsCompat lambda$fitDeviceNotch$1(View view, WindowInsetsCompat windowInsetsCompat) {
        view.setPadding(view.getPaddingLeft(), windowInsetsCompat.getSystemWindowInsetTop(), view.getPaddingRight(), windowInsetsCompat.getSystemWindowInsetBottom());
        return windowInsetsCompat;
    }
}
