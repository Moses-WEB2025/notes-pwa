package com.miui.notes.feature.folder;

import android.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.Logger;
import com.miui.notes.basefeature.folder.BaseFolderDialogFragment;
import com.miui.notes.tool.LogUtils;
import com.miui.notes.ui.IFragmentController;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class FolderDialogController implements IFragmentController {
    private static final int FOLDER_FRAGMENT = 0;
    private static final String FOLDER_LIST_TAG = "dialog_folder_list_tag";
    public static final String INTENT_PARAM_FOLDER_DIALOG_FOLDER_ID = "folderId";
    private static final String TAG = "FolderDialogController";
    private static final int TRASH_FRAGMENT = 1;
    private static final String TRASH_LIST_TAG = "dialog_trash_list_tag";
    private int curShowFragment = 0;
    protected BaseActivity mActivity;
    private BaseFolderDialogFragment mFolderListFragment;
    private TrashDialogFragment mTrashDialogFragment;
    private boolean toTrash;

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

    public FolderDialogController(BaseActivity baseActivity) {
        this.mActivity = baseActivity;
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onCreate(Bundle bundle) {
        if (this.mActivity.getIntent() != null && FolderDialogActivity.ACTION_TO_TRASH.equals(this.mActivity.getIntent().getAction())) {
            this.toTrash = true;
            showTrashFragment();
        } else {
            if ((bundle != null ? bundle.getInt("curShowFragment", 0) : 0) == 1) {
                showTrashFragment();
            } else {
                showFolderFragment();
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.putInt("curShowFragment", this.curShowFragment);
        }
    }

    public void showFolderFragment() {
        Logger.INSTANCE.d(TAG, "showFolderFragment");
        this.curShowFragment = 0;
        FragmentManager supportFragmentManager = this.mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        BaseFolderDialogFragment baseFolderDialogFragment = (BaseFolderDialogFragment) supportFragmentManager.findFragmentByTag(FOLDER_LIST_TAG);
        this.mFolderListFragment = baseFolderDialogFragment;
        if (baseFolderDialogFragment == null) {
            Bundle extras = this.mActivity.getIntent() != null ? this.mActivity.getIntent().getExtras() : null;
            CommonFolderDialogFragment commonFolderDialogFragment = new CommonFolderDialogFragment();
            this.mFolderListFragment = commonFolderDialogFragment;
            commonFolderDialogFragment.setArguments(extras);
            this.mFolderListFragment.setController(this);
            fragmentTransactionBeginTransaction.replace(R.id.content, this.mFolderListFragment, FOLDER_LIST_TAG);
            fragmentTransactionBeginTransaction.commit();
            return;
        }
        baseFolderDialogFragment.setController(this);
        if (this.mTrashDialogFragment != null) {
            this.mFolderListFragment.setAccessibility(true);
            supportFragmentManager.popBackStack();
        }
    }

    public void finishWithResult(long j) {
        Intent intent = new Intent();
        intent.putExtra("folderId", j);
        this.mActivity.setResult(-1, intent);
        this.mActivity.finish();
    }

    public void showTrashFragment() {
        this.curShowFragment = 1;
        FragmentManager supportFragmentManager = this.mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        TrashDialogFragment trashDialogFragment = (TrashDialogFragment) supportFragmentManager.findFragmentByTag(TRASH_LIST_TAG);
        this.mTrashDialogFragment = trashDialogFragment;
        if (trashDialogFragment == null) {
            TrashDialogFragment trashDialogFragment2 = new TrashDialogFragment();
            this.mTrashDialogFragment = trashDialogFragment2;
            trashDialogFragment2.setController(this);
            fragmentTransactionBeginTransaction.setTransition(4099);
            fragmentTransactionBeginTransaction.add(R.id.content, this.mTrashDialogFragment, TRASH_LIST_TAG);
            fragmentTransactionBeginTransaction.addToBackStack(TRASH_LIST_TAG);
            fragmentTransactionBeginTransaction.commit();
            return;
        }
        trashDialogFragment.setController(this);
    }

    @Override // com.miui.notes.ui.IFragmentController
    public void onNewIntent(Intent intent) {
        LogUtils.d(TAG, intent.toString());
    }

    @Override // com.miui.notes.ui.IFragmentController
    public boolean onBackPressed() {
        if (this.curShowFragment == 0) {
            BaseFolderDialogFragment baseFolderDialogFragment = this.mFolderListFragment;
            if (baseFolderDialogFragment != null) {
                return baseFolderDialogFragment.onBackPressed();
            }
            return false;
        }
        TrashDialogFragment trashDialogFragment = this.mTrashDialogFragment;
        if (trashDialogFragment == null) {
            return false;
        }
        if (trashDialogFragment.onBackPressed()) {
            return true;
        }
        if (this.toTrash) {
            this.mActivity.finish();
            return true;
        }
        showFolderFragment();
        return true;
    }
}
