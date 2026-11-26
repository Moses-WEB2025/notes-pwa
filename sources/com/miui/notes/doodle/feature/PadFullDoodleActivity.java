package com.miui.notes.doodle.feature;

import android.content.res.Configuration;
import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.utils.UIUtils;
import com.miui.notes.R;
import kotlin.Metadata;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: PadFullDoodleActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0014J\b\u0010\b\u001a\u00020\u0005H\u0014J\b\u0010\t\u001a\u00020\u0005H\u0014J\b\u0010\n\u001a\u00020\u000bH\u0014J$\u0010\f\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u000bH\u0016¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/doodle/feature/PadFullDoodleActivity;", "Lcom/miui/notes/doodle/feature/DoodleActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "createFragment", "isResponsiveEnabled", "", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadFullDoodleActivity extends DoodleActivity {
    @Override // miuix.appcompat.app.AppCompatActivity
    protected boolean isResponsiveEnabled() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.doodle.feature.DoodleActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.miui.notes.doodle.feature.DoodleActivity, com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        UIUtils.INSTANCE.checkOrientationInFoldDevice(this);
    }

    @Override // com.miui.notes.doodle.feature.DoodleActivity
    protected void createFragment() {
        setMDoodleFragment((DoodleFragment) getSupportFragmentManager().findFragmentById(R.id.content));
        if (getMDoodleFragment() == null) {
            PadDoodleFragment padDoodleFragment = new PadDoodleFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content, padDoodleFragment).commit();
            setMDoodleFragment(padDoodleFragment);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        UIUtils.INSTANCE.checkOrientationInFoldDevice(this);
    }
}
