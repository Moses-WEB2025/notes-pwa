package com.miui.pad.feature.settings;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadAppPreferenceActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0014J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0007H\u0002J\"\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/miui/pad/feature/settings/PadAppPreferenceActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isInFullWindowGestureMode", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "setInPadMode", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadAppPreferenceActivity extends CTAActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "PadAppPreferenceActivity";
    private boolean isInFullWindowGestureMode;

    @JvmStatic
    public static final void open(Activity activity) {
        INSTANCE.open(activity);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        PadAppSettingsFragment padAppSettingsFragment;
        super.onCreate(savedInstanceState);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            if (RomUtils.isFoldDevice()) {
                Logger.INSTANCE.d(TAG, "FoldAppSettingsFragment");
                padAppSettingsFragment = new FoldAppSettingsFragment();
            } else {
                Logger.INSTANCE.d(TAG, "PadAppSettingsFragment");
                padAppSettingsFragment = new PadAppSettingsFragment();
            }
            supportFragmentManager.beginTransaction().replace(R.id.content, padAppSettingsFragment).commit();
        }
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        UIUtils.setNavigationTrans(this);
        this.mHandoffSession = HandoffHelper.onSettings(this);
        BaseHybridNoteEditFragment.INSTANCE.removeAiFloatingWindow();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        getWindow().setAttributes(attributes);
        PadAppPreferenceActivity padAppPreferenceActivity = this;
        if (this.isInFullWindowGestureMode != UIUtils.isInFullWindowGestureMode(padAppPreferenceActivity)) {
            this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(padAppPreferenceActivity);
            UIUtils.setNavigationTrans(this);
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        UIUtils.setNavigationTrans(this);
    }

    private final void setInPadMode() {
        View decorView = getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) decorView;
        Object parent = viewGroup.findViewById(R.id.content).getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.View");
        View view = (View) parent;
        ViewParent parent2 = viewGroup.findViewById(R.id.content).getParent();
        Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type android.view.ViewGroup");
        Object parent3 = view.getParent();
        Intrinsics.checkNotNull(parent3, "null cannot be cast to non-null type android.view.View");
        View view2 = (View) parent3;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        view2.getResources().getDimension(com.miui.notes.R.dimen.miuix_appcompat_message_view_bg_radius);
        marginLayoutParams.leftMargin = 200;
        marginLayoutParams.topMargin = 100;
        marginLayoutParams.rightMargin = 200;
        marginLayoutParams.bottomMargin = 200;
        view.setLayoutParams(marginLayoutParams);
        View childAt = ((ViewGroup) parent2).getChildAt(1);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) childAt).getChildAt(1).setBackground(ResourcesCompat.getDrawable(view2.getResources(), com.miui.notes.R.drawable.pad_bg_round_child, null));
        viewGroup.findViewById(R.id.content).setBackground(ResourcesCompat.getDrawable(view2.getResources(), com.miui.notes.R.drawable.pad_bg_round_child, null));
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.settings.PadAppPreferenceActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.finish();
            }
        });
        view2.setBackgroundColor(805306368);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == 1) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                this.needShowCtaDialog = false;
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(this, true);
                CTAManager.getInstance().notifyAccept();
                Bundle bundle = new Bundle();
                bundle.putInt("push_type", 0);
                SyncUtils.requestSync(BaseNoteApp.INSTANCE.getInstance(), bundle, true);
            } else if (resultCode == 666) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                this.needShowCtaDialog = false;
                CTAManager.getInstance().notifyReject();
            }
            this.isShowedCtaDialogV12 = false;
            PermissionUtils.processCtaResult(this, resultCode);
        }
    }

    /* JADX INFO: compiled from: PadAppPreferenceActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/miui/pad/feature/settings/PadAppPreferenceActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "open", "", "activity", "Landroid/app/Activity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void open(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            activity.startActivity(new Intent(activity, (Class<?>) PadAppPreferenceActivity.class));
        }
    }
}
