package com.miui.handwriting.common.ai;

import android.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import com.miui.handwriting.common.view.BaseActivity;
import com.miui.libhandwritingpage.utils.UIUtils;
import java.lang.ref.WeakReference;
import miuix.appcompat.app.floatingactivity.OnFloatingActivityCallback;

/* JADX INFO: loaded from: classes3.dex */
public class CreationAiActivityForOS1 extends BaseActivity {
    public static final int AREA_CREATION = 2;
    public static final String INTENT_AI_CREATION_BG = "intent_ai_bg";
    public static final String INTENT_AI_CREATION_TYPE = "intent_ai_create_type";
    public static final String INTENT_AI_IMAGE_URL = "intent_ai_image_url";
    public static final int PAGE_CREATION = 1;
    private ExitLoginReceiver exitLoginReceiver;

    static /* synthetic */ boolean lambda$onCreate$0(int i) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, new CreationAiFragmentForOS1()).commitAllowingStateLoss();
        }
        UIUtils.setFullScreenMode(this, true);
        setOnFloatingWindowCallback(new OnFloatingActivityCallback() { // from class: com.miui.handwriting.common.ai.CreationAiActivityForOS1$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.app.floatingactivity.OnFloatingActivityCallback
            public final boolean onFinish(int i) {
                return CreationAiActivityForOS1.lambda$onCreate$0(i);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ExitLoginReceiver.ACTION_EXIT_LOGIN);
        this.exitLoginReceiver = new ExitLoginReceiver(this);
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(this.exitLoginReceiver, intentFilter, 4);
        } else {
            registerReceiver(this.exitLoginReceiver, intentFilter);
        }
    }

    public static class ExitLoginReceiver extends BroadcastReceiver {
        public static final String ACTION_EXIT_LOGIN = "com.miui.creation.ai.ExitLoginReceiver";
        private WeakReference<Activity> activityRf;

        public ExitLoginReceiver(Activity activity) {
            this.activityRf = new WeakReference<>(activity);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (this.activityRf.get() != null) {
                this.activityRf.get().finish();
            }
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.exitLoginReceiver);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public static void open(Activity activity, String str, int i, int i2, int i3) {
        Intent intent = new Intent(activity, (Class<?>) CreationAiActivityForOS1.class);
        intent.putExtra(INTENT_AI_IMAGE_URL, str);
        intent.putExtra(INTENT_AI_CREATION_TYPE, i);
        intent.putExtra(INTENT_AI_CREATION_BG, i2);
        activity.startActivityForResult(intent, i3);
    }
}
