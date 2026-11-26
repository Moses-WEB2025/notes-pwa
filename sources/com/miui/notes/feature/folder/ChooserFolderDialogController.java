package com.miui.notes.feature.folder;

import android.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.BaseActivity;
import com.miui.notes.ui.IFragmentController;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: ChooserFolderDialogController.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 +2\u00020\u0001:\u0001+B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J \u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0016H\u0016J\b\u0010 \u001a\u00020\tH\u0016J\b\u0010!\u001a\u00020\tH\u0016J\b\u0010\"\u001a\u00020\tH\u0016J\b\u0010#\u001a\u00020\u0016H\u0016J \u0010$\u001a\u00020\u00162\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020\u0011H\u0016J\u0010\u0010)\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020*H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/miui/notes/feature/folder/ChooserFolderDialogController;", "Lcom/miui/notes/ui/IFragmentController;", "mActivity", "Lcom/miui/common/base/BaseActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/common/base/BaseActivity;)V", "chooserFolderDialogFragment", "Lcom/miui/notes/feature/folder/ChooserFolderDialogFragment;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onPostCreate", "onResume", "onStop", "onNewIntent", "intent", "Landroid/content/Intent;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onBackPressed", "", "onSearchRequested", "dispatchKeyEvent", "event", "Landroid/view/KeyEvent;", "onResponsiveLayout", "configuration", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "onDestroy", "onDestroyEditFragment", "resetNeedLockInPrivate", "needLockInPrivate", "onActivityResult", "requestCode", "", "resultCode", "data", "dispatchTouchEvent", "Landroid/view/MotionEvent;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ChooserFolderDialogController implements IFragmentController {
    private static final String CHOOSER_FOLDER_DIALOG_FRAGMENT_TAG = "chooser_folder_dialog_fragment_tag";
    private ChooserFolderDialogFragment chooserFolderDialogFragment;
    private final BaseActivity mActivity;

    @Override // com.miui.notes.ui.IFragmentController
    public void dispatchKeyEvent(KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void dispatchTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean needLockInPrivate() {
        return true;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
    }

    @Override // com.miui.notes.ui.IFragmentDestroy
    public void onDestroy() {
    }

    @Override // com.miui.notes.ui.IFragmentDestroy
    public void onDestroyEditFragment() {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onPostCreate(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onResume() {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onSearchRequested() {
        return false;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onStop() {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void resetNeedLockInPrivate() {
    }

    public ChooserFolderDialogController(BaseActivity baseActivity) {
        this.mActivity = baseActivity;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onCreate(Bundle savedInstanceState) {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            Bundle extras = baseActivity.getIntent() != null ? this.mActivity.getIntent().getExtras() : null;
            FragmentManager supportFragmentManager = this.mActivity.getSupportFragmentManager();
            Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
            ChooserFolderDialogFragment chooserFolderDialogFragment = (ChooserFolderDialogFragment) supportFragmentManager.findFragmentByTag(CHOOSER_FOLDER_DIALOG_FRAGMENT_TAG);
            this.chooserFolderDialogFragment = chooserFolderDialogFragment;
            if (chooserFolderDialogFragment == null) {
                ChooserFolderDialogFragment chooserFolderDialogFragment2 = new ChooserFolderDialogFragment();
                this.chooserFolderDialogFragment = chooserFolderDialogFragment2;
                Intrinsics.checkNotNull(chooserFolderDialogFragment2);
                chooserFolderDialogFragment2.setController(this);
                ChooserFolderDialogFragment chooserFolderDialogFragment3 = this.chooserFolderDialogFragment;
                Intrinsics.checkNotNull(chooserFolderDialogFragment3);
                chooserFolderDialogFragment3.setArguments(extras);
                ChooserFolderDialogFragment chooserFolderDialogFragment4 = this.chooserFolderDialogFragment;
                Intrinsics.checkNotNull(chooserFolderDialogFragment4);
                Intrinsics.checkNotNull(fragmentTransactionBeginTransaction.replace(R.id.content, chooserFolderDialogFragment4, CHOOSER_FOLDER_DIALOG_FRAGMENT_TAG));
            } else {
                Intrinsics.checkNotNull(chooserFolderDialogFragment);
                chooserFolderDialogFragment.setController(this);
            }
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onBackPressed() {
        ChooserFolderDialogFragment chooserFolderDialogFragment = this.chooserFolderDialogFragment;
        if (chooserFolderDialogFragment == null) {
            return false;
        }
        Intrinsics.checkNotNull(chooserFolderDialogFragment);
        return chooserFolderDialogFragment.onBackPressed();
    }
}
