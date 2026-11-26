package com.miui.notes.doodle.feature;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.Utils;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.miui.doodle.feature.draw.DoodlePenManager;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.doodle.utils.RomUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.controller.PasswordController;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 '2\u00020\u00012\u00020\u0002:\u0001'B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\u0012\u0010\u0017\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0014J\b\u0010\u001a\u001a\u00020\u0013H\u0014J\b\u0010\u001b\u001a\u00020\u0013H\u0014J\b\u0010\u001c\u001a\u00020\u0013H\u0014J\b\u0010\u001d\u001a\u00020\u0013H\u0014J\u0012\u0010\u001e\u001a\u00020\u00132\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\"H\u0016J\b\u0010$\u001a\u00020%H\u0016J\n\u0010&\u001a\u0004\u0018\u00010\u0006H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/miui/notes/doodle/feature/DoodleActivity;", "Lcom/miui/common/base/BaseActivity;", "Lcom/miui/common/base/DialogManagerInterface;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mDoodleFragment", "Lcom/miui/notes/doodle/feature/DoodleFragment;", "getMDoodleFragment", "()Lcom/miui/notes/doodle/feature/DoodleFragment;", "setMDoodleFragment", "(Lcom/miui/notes/doodle/feature/DoodleFragment;)V", "passwordController", "Lcom/miui/notes/controller/PasswordController;", "extra_key_is_privacy", "", "TAG", "mFinishReceiver", "Landroid/content/BroadcastReceiver;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setFinishBroadcastReceiver", "onBeforeSetContent", "setHiddenFlag", "createFragment", "onStop", "onResume", "onStart", "onDestroy", "manageDialog", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/app/Dialog;", "dismissDialog", "", "isDialogShowing", "getThisActivity", "Landroid/app/Activity;", "getThisFragment", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleActivity extends BaseActivity implements DialogManagerInterface {
    public static final String EXTRA_KEY_IS_HIDDEN = "extra_key_is_hidden";
    private DoodleFragment mDoodleFragment;
    private BroadcastReceiver mFinishReceiver;
    private PasswordController passwordController;
    private final String extra_key_is_privacy = "extra_is_privacy";
    private final String TAG = "DoodleActivity";

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        return false;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        return false;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
    }

    protected void onBeforeSetContent(Bundle savedInstanceState) {
    }

    protected final DoodleFragment getMDoodleFragment() {
        return this.mDoodleFragment;
    }

    protected final void setMDoodleFragment(DoodleFragment doodleFragment) {
        this.mDoodleFragment = doodleFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.INSTANCE.d(this.TAG, "extra_key_is_privacy: " + getIntent().getBooleanExtra(this.extra_key_is_privacy, false));
        if (getIntent().getBooleanExtra(this.extra_key_is_privacy, false)) {
            Logger.INSTANCE.d("DoodleActivity", "init passwordController");
            PasswordController passwordController = new PasswordController(this, false);
            this.passwordController = passwordController;
            Intrinsics.checkNotNull(passwordController);
            passwordController.setLockSateListener(new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.doodle.feature.DoodleActivity.onCreate.1
                @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
                public void onLockStateChanged(PasswordController controller, int lockState) {
                    DoodleView doodleView;
                    DoodleView doodleView2;
                    super.onLockStateChanged(controller, lockState);
                    if (lockState == 0) {
                        DoodleFragment mDoodleFragment = DoodleActivity.this.getMDoodleFragment();
                        if (mDoodleFragment == null || (doodleView2 = mDoodleFragment.getDoodleView()) == null) {
                            return;
                        }
                        doodleView2.setVisibility(8);
                        return;
                    }
                    DoodleFragment mDoodleFragment2 = DoodleActivity.this.getMDoodleFragment();
                    if (mDoodleFragment2 == null || (doodleView = mDoodleFragment2.getDoodleView()) == null) {
                        return;
                    }
                    doodleView.setVisibility(0);
                }
            }, 2);
            getWindow().addFlags(8192);
        }
        onBeforeSetContent(savedInstanceState);
        setContentView(R.layout.doodle_activity);
        setHiddenFlag();
        createFragment();
        if (RomUtils.INSTANCE.isFoldDevice() || RomUtils.INSTANCE.isPadDevice()) {
            Utils utils = Utils.INSTANCE;
            Utils.isDoodleOnWin = true;
        }
        DoodlePenManager doodlePenManager = DoodlePenManager.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        doodlePenManager.initAllPens(applicationContext);
        setFinishBroadcastReceiver();
    }

    private final void setFinishBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordController.ACTIN_CANCEL);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.notes.doodle.feature.DoodleActivity.setFinishBroadcastReceiver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                DoodleActivity.this.passwordController = null;
                Log.d(DoodleActivity.this.TAG, "doodle onReceive action_cancel: ");
                DoodleActivity.this.getThisActivity().finish();
            }
        };
        this.mFinishReceiver = broadcastReceiver;
        registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    private final void setHiddenFlag() {
        if (getIntent().getBooleanExtra(EXTRA_KEY_IS_HIDDEN, false)) {
            getWindow().addFlags(8192);
        } else {
            getWindow().clearFlags(8192);
        }
    }

    protected void createFragment() {
        DoodleFragment doodleFragment = (DoodleFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        this.mDoodleFragment = doodleFragment;
        if (doodleFragment == null) {
            DoodleFragment doodleFragment2 = new DoodleFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content, doodleFragment2).commit();
            this.mDoodleFragment = doodleFragment2;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.d(this.TAG, "onStop: ");
        PasswordController passwordController = this.passwordController;
        if (passwordController != null) {
            passwordController.lock();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Logger.INSTANCE.d(this.TAG, "onResume: ");
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        Logger.INSTANCE.d(this.TAG, "onStart: ");
        PasswordController passwordController = this.passwordController;
        if (passwordController != null) {
            passwordController.unlock();
        }
        super.onStart();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Logger.INSTANCE.d(this.TAG, "onDestroy: ");
        try {
            BroadcastReceiver broadcastReceiver = this.mFinishReceiver;
            if (broadcastReceiver == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFinishReceiver");
                broadcastReceiver = null;
            }
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e(this.TAG, "Receiver is unregister or canceled " + e);
        }
        DoodleCacheManager.INSTANCE.clear();
        DoodleBrushPath.INSTANCE.clearCache();
        if (RomUtils.INSTANCE.isFoldDevice() || RomUtils.INSTANCE.isPadDevice()) {
            Utils utils = Utils.INSTANCE;
            Utils.isDoodleOnWin = false;
        }
        PasswordController passwordController = this.passwordController;
        if (passwordController != null) {
            passwordController.destroy();
        }
        super.onDestroy();
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return this;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public DoodleFragment getThisFragment() {
        return this.mDoodleFragment;
    }
}
