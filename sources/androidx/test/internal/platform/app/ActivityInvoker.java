package androidx.test.internal.platform.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import androidx.test.platform.app.InstrumentationRegistry;

/* JADX INFO: loaded from: classes.dex */
public interface ActivityInvoker {
    void finishActivity(Activity activity);

    Instrumentation.ActivityResult getActivityResult();

    void pauseActivity(Activity activity);

    void recreateActivity(Activity activity);

    void resumeActivity(Activity activity);

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle activityOptions);

    void startActivityForResult(Intent intent);

    void startActivityForResult(Intent intent, Bundle activityOptions);

    void stopActivity(Activity activity);

    /* JADX INFO: renamed from: androidx.test.internal.platform.app.ActivityInvoker$-CC, reason: invalid class name */
    /* JADX INFO: compiled from: ActivityInvokerDesugar.java */
    public final class CC {
        private CC() {
        }

        public static Intent $default$getIntentForActivity(ActivityInvoker invoker, Class<? extends Activity> activityClass) {
            Intent intentMakeMainActivity = Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getTargetContext(), activityClass));
            return InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageManager().resolveActivity(intentMakeMainActivity, 0) != null ? intentMakeMainActivity : Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getContext(), activityClass));
        }
    }

    default Intent getIntentForActivity(Class<? extends Activity> activityClass) {
        Intent intentMakeMainActivity = Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getTargetContext(), activityClass));
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageManager().resolveActivity(intentMakeMainActivity, 0) != null ? intentMakeMainActivity : Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getContext(), activityClass));
    }
}
