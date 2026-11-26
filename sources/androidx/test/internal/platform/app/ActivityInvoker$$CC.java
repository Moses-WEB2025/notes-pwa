package androidx.test.internal.platform.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityInvoker$$CC {
    private ActivityInvoker$$CC() {
    }

    public static Intent getIntentForActivity$$dflt$$(ActivityInvoker invoker, Class<? extends Activity> activityClass) {
        Intent intentMakeMainActivity = Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getTargetContext(), activityClass));
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageManager().resolveActivity(intentMakeMainActivity, 0) != null ? intentMakeMainActivity : Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getContext(), activityClass));
    }
}
