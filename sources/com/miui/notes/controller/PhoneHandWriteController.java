package com.miui.notes.controller;

import android.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.UIUtils;
import com.miui.notes.ui.IFragmentController;
import com.miui.notes.ui.fragment.PhoneHandWriteFragment;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneHandWriteController implements IFragmentController {
    private static final String PHONE_HAND_WRITE_FRAGMENT_TAG = "phone_hand_write_fragment_tag";
    private PhoneHandWriteFragment handWriteFragment;
    private BaseActivity mActivity;

    @Override // com.miui.notes.ui.IFragmentController
    public void dispatchKeyEvent(KeyEvent keyEvent) {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void dispatchTouchEvent(MotionEvent motionEvent) {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean needLockInPrivate() {
        return true;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onActivityResult(int i, int i2, Intent intent) {
        return false;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // com.miui.notes.ui.IFragmentDestroy
    public void onDestroy() {
    }

    @Override // com.miui.notes.ui.IFragmentDestroy
    public void onDestroyEditFragment() {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onNewIntent(Intent intent) {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onPostCreate(Bundle bundle) {
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
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

    public PhoneHandWriteController(BaseActivity baseActivity) {
        this.mActivity = baseActivity;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onCreate(Bundle bundle) {
        UIUtils.setNavigationTrans(this.mActivity);
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            FragmentManager supportFragmentManager = baseActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
            PhoneHandWriteFragment phoneHandWriteFragment = (PhoneHandWriteFragment) supportFragmentManager.findFragmentByTag(PHONE_HAND_WRITE_FRAGMENT_TAG);
            this.handWriteFragment = phoneHandWriteFragment;
            if (phoneHandWriteFragment == null) {
                PhoneHandWriteFragment phoneHandWriteFragment2 = new PhoneHandWriteFragment();
                this.handWriteFragment = phoneHandWriteFragment2;
                phoneHandWriteFragment2.setController(this);
                fragmentTransactionBeginTransaction.add(R.id.content, this.handWriteFragment, PHONE_HAND_WRITE_FRAGMENT_TAG);
            } else {
                phoneHandWriteFragment.setController(this);
            }
            fragmentTransactionBeginTransaction.commit();
        }
    }

    public Uri getDeepLink() {
        PhoneHandWriteFragment phoneHandWriteFragment = this.handWriteFragment;
        if (phoneHandWriteFragment == null || !(phoneHandWriteFragment instanceof PhoneHandWriteFragment)) {
            return null;
        }
        return phoneHandWriteFragment.getDeepLink();
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onBackPressed() {
        PhoneHandWriteFragment phoneHandWriteFragment = this.handWriteFragment;
        if (phoneHandWriteFragment != null) {
            return phoneHandWriteFragment.onBackPressed();
        }
        return false;
    }
}
