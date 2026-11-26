package miuix.autodensity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import miuix.reflect.ReflectionHelper;

/* JADX INFO: loaded from: classes5.dex */
public class ConfigurationChangeFragment extends Fragment {
    private AutoDensityConfig mDensityProcessor;
    private boolean mRemoveDensityChangeFlag = false;

    public void setDensityProcessor(AutoDensityConfig autoDensityConfig) {
        this.mDensityProcessor = autoDensityConfig;
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        AutoDensityConfig autoDensityConfig;
        super.onAttach(context);
        if (Build.VERSION.SDK_INT >= 36 && (autoDensityConfig = this.mDensityProcessor) != null && autoDensityConfig.mEnableUpdateInFragment) {
            DensityConfigManager.getInstance().tryUpdateConfig(context, context.getResources().getConfiguration());
        }
        if (DebugUtil.isEnableDebug()) {
            DebugUtil.printDensityLog("->ConfigurationChangeFragment onAttach newConfig " + context.getResources().getConfiguration() + " context: " + context);
        }
        AutoDensityConfig.updateDensity(context);
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Activity activity = getActivity();
        if (DebugUtil.isEnableDebug()) {
            DebugUtil.printDensityLog("->ConfigurationChangeFragment onConfigurationChanged newConfig " + configuration + " activity: " + activity);
        }
        AutoDensityConfig autoDensityConfig = this.mDensityProcessor;
        if (autoDensityConfig != null) {
            autoDensityConfig.updateDensityOnConfigChanged(activity, configuration);
        } else {
            Log.w("AutoDensity", "Warning! ConfigurationChangeFragment density processor is null, " + Log.getStackTraceString(new Throwable()));
        }
        super.onConfigurationChanged(configuration);
        try {
            if (this.mRemoveDensityChangeFlag) {
                ((ActivityInfo) ReflectionHelper.getFieldValue(Activity.class, activity, "mActivityInfo")).configChanges &= -4097;
                this.mRemoveDensityChangeFlag = false;
            }
        } catch (Exception unused) {
        }
    }

    public void removeDensityChangeFlag() {
        this.mRemoveDensityChangeFlag = true;
    }
}
