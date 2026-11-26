package com.miui.pad.feature.settings;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.UIUtils;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.feature.settings.PermissionSettingsFragment;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadPermissionPreferenceActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/pad/feature/settings/PadPermissionPreferenceActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isInFullWindowGestureMode", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadPermissionPreferenceActivity extends CTAActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isInFullWindowGestureMode;

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, new PermissionSettingsFragment()).commit();
        }
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        UIUtils.setNavigationTrans(this);
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.entityMatchRequired);
        PadPermissionPreferenceActivity padPermissionPreferenceActivity = this;
        if (this.isInFullWindowGestureMode != UIUtils.isInFullWindowGestureMode(padPermissionPreferenceActivity)) {
            this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(padPermissionPreferenceActivity);
            UIUtils.setNavigationTrans(this);
        }
    }

    /* JADX INFO: compiled from: PadPermissionPreferenceActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/pad/feature/settings/PadPermissionPreferenceActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "open", "", "activity", "Landroid/app/Activity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void open(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            activity.startActivity(new Intent(activity, (Class<?>) PadPermissionPreferenceActivity.class));
        }
    }
}
