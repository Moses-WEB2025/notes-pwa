package com.miui.todo.feature.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.PermissionUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: QuickNotePreferenceActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0017J\b\u0010\n\u001a\u00020\u0007H\u0014J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0016J-\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¢\u0006\u0002\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/todo/feature/settings/QuickNotePreferenceActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isInFullWindowGestureMode", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class QuickNotePreferenceActivity extends CTAActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isInFullWindowGestureMode;

    @JvmStatic
    public static final void open(Activity activity) {
        INSTANCE.open(activity);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        if (RomUtils.isPadMode()) {
            setTheme(R.style.NoteTheme_PadSettings);
        } else {
            setTheme(R.style.NoteTheme_Settings);
        }
        super.onCreate(savedInstanceState);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        if (supportFragmentManager.findFragmentById(android.R.id.content) == null) {
            supportFragmentManager.beginTransaction().replace(android.R.id.content, new QuickNoteSettingsFragment()).commit();
        }
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        UIUtils.setNavigationTrans(this);
        this.mHandoffSession = HandoffHelper.onQuickNoteSettings(this);
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        UIUtils.checkOrientationInFoldDevice(this);
        QuickNotePreferenceActivity quickNotePreferenceActivity = this;
        if (this.isInFullWindowGestureMode != UIUtils.isInFullWindowGestureMode(quickNotePreferenceActivity)) {
            this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(quickNotePreferenceActivity);
            UIUtils.setNavigationTrans(this);
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        UIUtils.checkOrientationInFoldDevice(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.INSTANCE.i("TodoSetting", "onRequestPermissionsResult requestCode = " + requestCode);
        if (requestCode == 4) {
            PermissionUtils.processOnStoragePermission(this, permissions, grantResults);
        }
    }

    /* JADX INFO: compiled from: QuickNotePreferenceActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, d2 = {"Lcom/miui/todo/feature/settings/QuickNotePreferenceActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "open", "", "activity", "Landroid/app/Activity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void open(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            activity.startActivity(new Intent(activity, (Class<?>) QuickNotePreferenceActivity.class));
        }
    }
}
