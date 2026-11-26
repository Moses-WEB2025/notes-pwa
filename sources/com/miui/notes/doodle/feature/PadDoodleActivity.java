package com.miui.notes.doodle.feature;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.utils.RomUtils;
import com.miui.doodle.utils.UIUtils;
import com.miui.notes.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.floatingactivity.FloatingActivitySwitcher;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: PadDoodleActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0011\u001a\u00020\rH\u0014J\"\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0006\u0010\u0019\u001a\u00020\rJ\b\u0010\u001a\u001a\u00020\rH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/doodle/feature/PadDoodleActivity;", "Lcom/miui/notes/doodle/feature/DoodleActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "screenStatus", "", "getScreenStatus", "()I", "setScreenStatus", "(I)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onBeforeSetContent", "createFragment", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "", "updateWindowStateBeforeCreate", "onDestroy", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadDoodleActivity extends DoodleActivity {
    private final String TAG = "PadDoodleActivity";
    private int screenStatus;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFragment$lambda$0(View view) {
    }

    @Override // com.miui.notes.doodle.feature.DoodleActivity
    protected void onBeforeSetContent(Bundle savedInstanceState) {
    }

    public final int getScreenStatus() {
        return this.screenStatus;
    }

    public final void setScreenStatus(int i) {
        this.screenStatus = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.doodle.feature.DoodleActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActivitySwitcher.install(this, savedInstanceState);
        if (getIntent() != null) {
            this.screenStatus = getIntent().getIntExtra("screen_status", 0);
            updateWindowStateBeforeCreate();
        }
    }

    @Override // com.miui.notes.doodle.feature.DoodleActivity
    protected void createFragment() {
        setMDoodleFragment((DoodleFragment) getSupportFragmentManager().findFragmentById(R.id.content));
        if (getMDoodleFragment() == null) {
            PadDoodleFragment padDoodleFragment = new PadDoodleFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content, padDoodleFragment).commit();
            setMDoodleFragment(padDoodleFragment);
        }
        View decorView = getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        View viewFindViewById = ((ViewGroup) decorView).findViewById(UIUtils.INSTANCE.getMiuixAppcompatViewId("action_bar_overlay_bg"));
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.doodle.feature.PadDoodleActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PadDoodleActivity.createFragment$lambda$0(view);
                }
            });
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        this.screenStatus = screenSpec.screenMode;
        if (RomUtils.INSTANCE.isFoldDevice()) {
            return;
        }
        if (screenSpec.type == 1) {
            setFloatingWindowMode(false);
        } else {
            if (RomUtils.INSTANCE.isFoldDevice()) {
                return;
            }
            setFloatingWindowMode(true);
            setFloatingWindowBorderEnable(false);
        }
    }

    public final void updateWindowStateBeforeCreate() {
        if (RomUtils.INSTANCE.isFoldDevice()) {
            setFloatingWindowMode(false);
        }
        if (getResponsiveState().getType() == 1) {
            setFloatingWindowMode(false);
        } else {
            if (RomUtils.INSTANCE.isFoldDevice()) {
                return;
            }
            setFloatingWindowMode(true);
            setFloatingWindowBorderEnable(false);
        }
    }

    @Override // com.miui.notes.doodle.feature.DoodleActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
