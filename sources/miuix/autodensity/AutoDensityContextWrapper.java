package miuix.autodensity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;

/* JADX INFO: loaded from: classes5.dex */
public class AutoDensityContextWrapper extends ContextThemeWrapper {
    private Configuration mOriginConfiguration;

    public AutoDensityContextWrapper(Context context, int i) {
        super(context, i);
    }

    public AutoDensityContextWrapper(Context context, Resources.Theme theme) {
        super(context, theme);
    }

    public void setOriginConfiguration(Configuration configuration) {
        this.mOriginConfiguration = configuration;
    }

    public Configuration getOriginConfiguration() {
        return this.mOriginConfiguration;
    }

    public void restoreOriginConfig() {
        getResources().getConfiguration().setTo(this.mOriginConfiguration);
        getResources().getDisplayMetrics().density = this.mOriginConfiguration.densityDpi / 160.0f;
        getResources().getDisplayMetrics().densityDpi = this.mOriginConfiguration.densityDpi;
        float f = this.mOriginConfiguration.fontScale;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float f2 = getResources().getDisplayMetrics().density;
        if (f == 0.0f) {
            f = 1.0f;
        }
        displayMetrics.scaledDensity = f2 * f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.ContextThemeWrapper] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    public Configuration getNoOverrideConfiguration() {
        ?? r1 = this;
        while (r1.getBaseContext() instanceof ContextThemeWrapper) {
            r1 = (ContextThemeWrapper) r1.getBaseContext();
        }
        return r1.getResources().getConfiguration();
    }
}
