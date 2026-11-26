package miuix.view;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: classes6.dex */
public class CompatViewMethod {
    public static void setForceDarkAllowed(View view, boolean z) {
        view.setForceDarkAllowed(z);
    }

    public static void setActivityTranslucent(Activity activity, boolean z) {
        if (Build.VERSION.SDK_INT >= 30) {
            activity.setTranslucent(z);
        }
    }
}
