package com.miui.notes.feature.mindnote;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.mindnoteeditor.MindNoteWebView;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface;
import com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.HandoffHelper;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneWebMindNoteFragment extends BaseWebMindNoteFragment {
    private final String TAG = "PhoneWebMindNoteFragment";
    private View mWebContent;

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected int getLayoutId() {
        return R.layout.mindnote_webview_layout;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public int getUiMode() {
        return 0;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public Uri getDeepLink() {
        if (this.mWorkingNote != null) {
            return HandoffHelper.getNoteDeepLink(this.mWorkingNote.getSyncId(), this.mWorkingNote.getFolderId());
        }
        return null;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mBridgeInterface.releaseReference();
        if (this.mLocalBroadcastManager != null) {
            try {
                this.mLocalBroadcastManager.unregisterReceiver(this.mDualScreenEditReceiver);
            } catch (Exception e) {
                Logger.INSTANCE.e("PhoneWebMindNoteFragment", "unregisterReceiver error" + e.getMessage());
            }
            this.mLocalBroadcastManager = null;
        }
        killSaveTask();
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        deleteEmptyNote();
        if (this.mPasswordController != null) {
            this.mPasswordController.destroy();
        }
    }

    private void deleteEmptyNote() {
        if (getContext() == null || this.mWorkingNote == null || !this.mWorkingNote.isEmpty()) {
            return;
        }
        NoteStore.delete(getContext(), new long[]{this.mWorkingNote.getNoteId()});
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initPasswordController() {
        this.mPasswordController = new PasswordController(this, true);
        this.mPasswordController.setOnSetPasswordListener(this.mOnSetPasswordListener);
        this.mPasswordController.setLockSateListener(this.mLockStateListener);
        this.homeViewModel.getToDetailResult().observe(this, new Observer() { // from class: com.miui.notes.feature.mindnote.PhoneWebMindNoteFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initPasswordController$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPasswordController$0(Boolean bool) {
        if (this.mPasswordController != null) {
            this.mPasswordController.activeUnlock();
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initView(View view) {
        this.mWebView = (WebView) view.findViewById(R.id.web_view);
        this.mWebContent = view.findViewById(R.id.web_content);
        smallWindowAdapter();
        if (this.mWebView instanceof MindNoteWebView) {
            ((MindNoteWebView) this.mWebView).setFeatureConfig("{\n\t\"select\": 1,\n    \"query\": 1,\n\t\"phrase\": 1,\n\t\"translate\": 1\n}");
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected BaseMindNoteBridgeInterface createBridgeInterFace() {
        return new BaseMindNoteBridgeInterface(this);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onNoteMovedSuccess(long j) {
        super.onNoteMovedSuccess(j);
        if (this.mWorkingNote != null) {
            Logger.INSTANCE.d("PhoneWebMindNoteFragment", "update folder id for current working note");
            this.mWorkingNote.setFolderId(j);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initWebView() {
        super.initWebView();
        this.mWebView.setBackgroundColor(getActivity().getResources().getColor(R.color.v12_mind_note_webview_background));
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        Bundle extras;
        super.onViewInflated(view, bundle);
        Intent intent = getActivity().getIntent();
        if (handleInstanceState(bundle) || intent == null || (extras = intent.getExtras()) == null) {
            return;
        }
        NoteLoadDataEntity noteLoadDataEntity = (NoteLoadDataEntity) extras.getSerializable("entity");
        if (noteLoadDataEntity == null) {
            this.mPendingHandleIntent = intent;
            if (((Bundle) intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA)) == null) {
                convertIntent(intent);
                preHandleIntent(this.mPendingHandleIntent);
                return;
            }
            return;
        }
        refreshNote(noteLoadDataEntity, false);
    }

    protected void convertIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        long longExtra = intent.getLongExtra("android.intent.extra.UID", -1L);
        long longExtra2 = intent.getLongExtra(NoteIntent.KEY_FOLDER_ID, 0L);
        int intExtra = intent.getIntExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 0);
        int intExtra2 = intent.getIntExtra(NoteIntent.KEY_MIND_OR_OVERVIEW, 1);
        int intExtra3 = intent.getIntExtra("com.miui.notes.widget_id", 0);
        int intExtra4 = intent.getIntExtra("com.miui.notes.widget_id", -1);
        String stringExtra = intent.getStringExtra("note_title");
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity();
        noteLoadDataEntity.setNoteId(longExtra);
        noteLoadDataEntity.setFolderId(longExtra2);
        noteLoadDataEntity.setTitle(stringExtra);
        noteLoadDataEntity.setMindEditMode(intExtra);
        noteLoadDataEntity.setMindContentType(intExtra2);
        noteLoadDataEntity.setWidgetId(intExtra3);
        noteLoadDataEntity.setWidgetType(intExtra4);
        try {
            setArguments(noteLoadDataEntity.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent2 = new Intent();
        intent2.putExtra(NoteIntent.KEY_EXTRA_LOAD_DATA, getArguments());
        if (longExtra == -2) {
            intent2.setAction("android.intent.action.EDIT");
            intent2.putExtra("android.intent.extra.UID", longExtra);
            intent2.putExtra("com.miui.notes.folder_id", this.mFolderId);
        } else {
            intent2.setAction("android.intent.action.VIEW");
            intent2.putExtra("android.intent.extra.UID", longExtra);
            intent2.putExtra("com.miui.notes.folder_id", longExtra2);
        }
        this.mPendingHandleIntent = intent2;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void preHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (this.mLoadTask != null) {
            this.mLoadTask.cancel();
            this.mLoadTask = null;
        }
        this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
        Bundle bundle = (Bundle) intent.getParcelableExtra(NoteIntent.KEY_EXTRA_LOAD_DATA);
        if (bundle != null) {
            this.mEditMode = bundle.getInt(NoteLoadDataEntity.MIND_EDIT_MODE);
            this.mMindContentType = bundle.getInt("mind_content_type");
            this.mTitle = bundle.getString("note_title");
            long j = bundle.getLong("data_id");
            if (j == -2) {
                this.mMindContentType = 1;
            }
            if (this.mMindContentType == 1) {
                getActivity().setRequestedOrientation(1);
            } else {
                getActivity().setRequestedOrientation(-1);
            }
            this.mWebView.loadUrl("javascript:clearModifiedFlag()");
            if (this.mWorkingNote != null && j != this.mWorkingNote.getNoteId() && !this.mWorkingNote.isEmpty()) {
                saveNoteInstantlyIfNeeded();
            }
            updateWebContentType(this.mMindContentType);
        }
        this.mWorkingNote = null;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void updatePaddingBottom(int i) {
        super.updatePaddingBottom(i);
        dealMindFootbar(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealMindFootbar(int i) {
        int i2 = this.mNavigationBarHeight + 40;
        if (this.mMindContentType == 0) {
            if (this.mInFreeForm) {
                this.mWebView.loadUrl("javascript:dealMindFootbar(" + i2 + ")");
                return;
            } else if (i < i2) {
                this.mWebView.loadUrl("javascript:dealMindFootbar(" + (i2 - i) + ")");
                return;
            } else {
                this.mWebView.loadUrl("javascript:dealMindFootbar(40)");
                return;
            }
        }
        if (this.mMindContentType == 1) {
            if (this.mInFreeForm) {
                this.mWebView.loadUrl("javascript:dealMindFootbar(" + this.mNavigationBarHeight + ")");
            } else {
                this.mWebView.loadUrl("javascript:dealMindFootbar(0)");
            }
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onPagePrepare() {
        super.onPagePrepare();
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.feature.mindnote.PhoneWebMindNoteFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    PhoneWebMindNoteFragment.this.dealMindFootbar(0);
                }
            });
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void disableLandscape(String str) {
        super.disableLandscape(str);
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.feature.mindnote.PhoneWebMindNoteFragment.2
                @Override // java.lang.Runnable
                public void run() {
                    PhoneWebMindNoteFragment.this.dealMindFootbar(0);
                }
            });
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void asyncSaveNoteInstantlyIfNeeded(String str) {
        if (isAdded()) {
            if (this.mMindNoteDataJson.contentEquals(str)) {
                saveAndExit();
            } else {
                updateWorkingText(str);
                saveAndExit();
            }
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(getContext());
        if (screenSizeDp[0] > screenSizeDp[1]) {
            setScreenOrientationToWebView(2);
        } else {
            setScreenOrientationToWebView(1);
            getActivity().getWindow().clearFlags(131072);
        }
        updateWindowStatus(DisplayUtils.isNightMode());
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onUpdateViewState(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2) {
        if (this.mWebView instanceof MindNoteWebView) {
            ((MindNoteWebView) this.mWebView).updateViewState(z, z2, z3, z4, str, str2);
        }
    }

    private void smallWindowAdapter() {
        View view = this.mWebContent;
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.mindnote.PhoneWebMindNoteFragment.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                Activity activity = UIUtils.getActivity(PhoneWebMindNoteFragment.this.getContext());
                if (rootWindowInsets != null && activity != null) {
                    Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                    if (PhoneWebMindNoteFragment.this.mNavigationBarHeight != insetsIgnoringVisibility.bottom) {
                        PhoneWebMindNoteFragment.this.mNavigationBarHeight = insetsIgnoringVisibility.bottom;
                        PhoneWebMindNoteFragment.this.dealMindFootbar(0);
                    }
                    if (PhoneWebMindNoteFragment.this.mInFreeForm) {
                        PhoneWebMindNoteFragment phoneWebMindNoteFragment = PhoneWebMindNoteFragment.this;
                        phoneWebMindNoteFragment.dealMindFootbar(phoneWebMindNoteFragment.mLastViewPaddingBottom);
                    }
                    Logger.INSTANCE.d("PhoneWebMindNoteFragment", "mNavigationBarHeight:" + PhoneWebMindNoteFragment.this.mNavigationBarHeight);
                    PhoneWebMindNoteFragment.this.mWebContent.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                }
                return windowInsetsCompat;
            }
        });
    }
}
