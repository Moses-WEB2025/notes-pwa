package com.miui.notes.page;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.google.gson.Gson;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.NoteQueryStat;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.floatwindow.feature.note.NoteOperationHelper;
import com.miui.hybirdeditor.WebRichEditorClient;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.assist.ActivityOptionCompatImpl;
import com.miui.notes.base.utils.BitmapUtils;
import com.miui.notes.base.utils.ThreadPoolUtils;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.SimpleTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.BitmapCacheManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.tapshareutils.TapShareDataPrepareUtils;
import com.miui.notes.ui.PreLoadLiteNoteThemeResTask;
import com.miui.notes.ui.PreLoadNoteThemeResTask;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.PhoneHandWriteActivity;
import com.miui.notes.ui.activity.PhoneMultiHandWriteActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.notes.ui.widget.FloatingActionButton;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.util.ResourceParser;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import miuix.animation.Folme;
import miuix.core.util.MiuixUIUtils;
import miuix.transition.ActivityOptionsHelper;
import miuix.view.animation.CubicEaseOutInterpolator;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNoteListPageFragment extends NoteListPageFragment {
    public String TAG = "PhoneNoteListPageFragment";
    private boolean hasToggledDarkModeInDetail = false;
    protected View.OnClickListener mActionListener = new View.OnClickListener() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda8
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f$0.lambda$new$6(view);
        }
    };
    private ActivityOptions mActivityOptions;

    private static class SafeViewCallback implements Runnable {
        private final WeakReference<PhoneNoteListPageFragment> fragmentRef;
        private final boolean isFloatingActionButton;
        private final boolean isStartCallback;
        private final int viewId;

        public SafeViewCallback(PhoneNoteListPageFragment phoneNoteListPageFragment, View view, boolean z) {
            this.fragmentRef = new WeakReference<>(phoneNoteListPageFragment);
            this.viewId = view.getId();
            this.isStartCallback = z;
            this.isFloatingActionButton = view instanceof FloatingActionButton;
        }

        @Override // java.lang.Runnable
        public void run() {
            View viewFindViewById;
            PhoneNoteListPageFragment phoneNoteListPageFragment = this.fragmentRef.get();
            if (phoneNoteListPageFragment == null || !phoneNoteListPageFragment.isAdded() || phoneNoteListPageFragment.getView() == null || (viewFindViewById = phoneNoteListPageFragment.getView().findViewById(this.viewId)) == null) {
                return;
            }
            if (this.isStartCallback) {
                Logger.INSTANCE.d(phoneNoteListPageFragment.TAG, "exit started...");
                viewFindViewById.setVisibility(4);
                return;
            }
            Logger.INSTANCE.d(phoneNoteListPageFragment.TAG, "exit finished...");
            viewFindViewById.setVisibility(0);
            if (this.isFloatingActionButton && (viewFindViewById instanceof FloatingActionButton)) {
                FloatingActionButton floatingActionButton = (FloatingActionButton) viewFindViewById;
                floatingActionButton.setShadowAlpha(0);
                floatingActionButton.showShadow();
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.hasToggledDarkModeInDetail = true;
        }
        Logger.INSTANCE.d(this.TAG, "onCreate");
        this.mIsInPrivate = false;
        LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_GRID_MODE, Boolean.class).observe(this, new Observer() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.updateGridMode(((Boolean) obj).booleanValue());
            }
        });
    }

    @Override // com.miui.notes.page.NoteListPageFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.INSTANCE.d(this.TAG, "onStart");
        if (!DualScreenManager.INSTANCE.isPcActivityLiving()) {
            Logger.INSTANCE.d(this.TAG, " onStart DeleteEmptyRunnable");
            Thread thread = new Thread(new NoteStore.DeleteEmptyRunnable());
            thread.setName("DeleteEmptyRunnable");
            thread.start();
        }
        if (this.mRecyclerViewWrapper == null || !this.mRecyclerViewWrapper.isInActionMode()) {
            return;
        }
        this.mRecyclerViewWrapper.finishActionMode();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onPause() {
        Trace.beginSection("PhoneNotePageFragment-onPause");
        super.onPause();
        this.mAdapter.resetDataSetChangedTime();
        Logger.INSTANCE.d(this.TAG, "onPause");
        Trace.endSection();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mActivityOptions != null) {
            this.mActivityOptions = null;
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        Trace.beginSection("PhoneNotePageFragment-onResume");
        super.onResume();
        Logger.INSTANCE.d(this.TAG, "onResume");
        boolean gridMode = PreferenceUtils.getGridMode(getActivity());
        if (gridMode != this.mAdapter.isGridMode() && !LiteUtils.isLiteOrMiddle()) {
            updateGridMode(gridMode);
        }
        Logger.INSTANCE.d(this.TAG, "onResume clickedNotePosition = " + this.homeViewModel.get_clickedNotePosition() + " hasToggledDarkModeInDetail = " + this.hasToggledDarkModeInDetail);
        if (this.homeViewModel.get_clickedNotePosition() != -1 && this.hasToggledDarkModeInDetail) {
            this.hasToggledDarkModeInDetail = false;
            this.homeViewModel.setClickedNotePosition(-1);
            cancelScaleUpDownAnimation();
        }
        Trace.endSection();
    }

    private void cancelScaleUpDownAnimation() {
        Logger.INSTANCE.d(this.TAG, "cancelScaleUpDownAnimation");
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("disableBackAnimation", true);
            ActivityOptionsHelper.updateScaleUpDownData(getActivity(), bundle);
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "cancelScaleUpDownAnimation failed, e: " + e);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void initView(View view) {
        super.initView(view);
        initGridMode();
    }

    private void initGridMode() {
        updateGridMode(PreferenceUtils.getGridMode(getActivity()));
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        this.mBtnContentAdd.setOnClickListener(this.mActionListener);
        Logger.INSTANCE.d(this.TAG, "onViewInflated,observe data set via livedata");
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.homeViewModel.getListDataCursor().observe(this, new Observer() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$0(jCurrentTimeMillis, (Cursor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$0(long j, Cursor cursor) {
        Logger.INSTANCE.d(this.TAG, "on data changed,with cursor count:" + cursor.getCount() + ",ts cost:" + (System.currentTimeMillis() - j) + "ms");
        onNotesLoadFinished(cursor);
        refreshFolderInfo();
        cachePhoneNumberResource();
        BitmapCacheManager.resize(getActivity());
    }

    protected void updateRecyclerViewLayoutManager(boolean z) {
        Logger.INSTANCE.d(this.TAG, "updateRecyclerViewLayoutManager " + z);
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_base);
        final int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_horizontal_common);
        if (z) {
            this.mGridItemSpace = dimensionPixelSize2 / 2;
            this.mRecyclerView.setPadding(this.mGridItemSpace, dimensionPixelSize, this.mGridItemSpace, this.mRecyclerView.getPaddingBottom());
            this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
            this.mRecyclerView.addItemDecoration(this.mItemDecoration);
            this.mRvLayoutManager = new NoteGridLayoutManager(MiuixUIUtils.getFontLevel(getContext()) == 2 ? 1 : 2, 1);
            this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
            this.mRvLayoutManager.setAutoMeasureEnabled(false);
            this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PhoneNoteListPageFragment.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    int dimensionPixelOffset = PhoneNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                    outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
                }
            });
            this.mSpringBackLayout.setClipToOutline(true);
            return;
        }
        this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
        this.mRecyclerView.setPadding(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, this.mRecyclerView.getPaddingBottom());
        this.mRvLayoutManager = new NoteGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
        this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PhoneNoteListPageFragment.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                int dimensionPixelOffset = PhoneNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
            }
        });
        this.mSpringBackLayout.setClipToOutline(true);
    }

    protected void updateGridMode(final boolean z) {
        if (this.mAdapter.getItemCount() == 0) {
            onGridModeChanged(z);
            return;
        }
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.recyclerview_scale_out);
        animationLoadAnimation.setInterpolator(new CubicEaseOutInterpolator());
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.miui.notes.page.PhoneNoteListPageFragment.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                PhoneNoteListPageFragment.this.onGridModeChanged(z);
                PhoneNoteListPageFragment.this.onUpdateGridModeAnimEnd();
            }
        });
        this.mRecyclerView.startAnimation(animationLoadAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGridModeChanged(boolean z) {
        Logger.INSTANCE.d(this.TAG, "onGridModeChanged,gridMode:" + z);
        if (LiteUtils.isLiteOrMiddle()) {
            this.mAdapter.setGridMode(false);
            updateRecyclerViewLayoutManager(false);
        } else {
            this.mAdapter.setGridMode(z);
            updateRecyclerViewLayoutManager(z);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    protected void onUpdateGridModeAnimEnd() {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.recyclerview_scale_in);
        animationLoadAnimation.setInterpolator(new CubicEaseOutInterpolator());
        this.mRecyclerView.startAnimation(animationLoadAnimation);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    protected void initResultLauncher() {
        super.initResultLauncher();
        this.homeViewModel.setupToDetailActivityResultLauncher(getThisFragment());
        this.homeViewModel.setupToDetailLauncher(getThisFragment());
        this.homeViewModel.getToDetailPage().observe(this, new Observer() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$initResultLauncher$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$1(Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        toDetailActivityResult();
    }

    protected void toDetailActivityResult() {
        Logger.INSTANCE.d(this.TAG, "toDetailActivityResult");
        this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toDetailActivityResult$2();
            }
        }, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toDetailActivityResult$2() {
        if (isAdded()) {
            this.mAdapter.frozeData(false);
            Utils.hideSoftInput(getView());
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    public void deliverIntent(Intent intent) {
        Logger.INSTANCE.d(this.TAG, "deliverIntent");
        String action = intent.getAction();
        boolean z = !TextUtils.isEmpty(action) && action.equals(NoteIntent.ACTION_SEARCH_NOTES);
        boolean zEquals = NoteIntent.ACTION_VIEW_HIDDEN_NOTE_BY_ID.equals(action);
        boolean z2 = intent.getData() != null && "mishare".equals(intent.getData().getScheme()) && "/notes/Share".equals(intent.getData().getPath());
        if (!z) {
            if (zEquals) {
                openNoteById(intent.getLongExtra("android.intent.extra.UID", -1L), -4L);
                return;
            } else {
                if (z2) {
                    Logger.INSTANCE.d(this.TAG, "handle tap share, isTapShareLink: " + z2 + ", isTapShareSend: " + z2);
                    handleTapShare(intent);
                    return;
                }
                return;
            }
        }
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
        String stringExtra = intent.getStringExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY);
        String stringExtra2 = intent.getStringExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY_FROM_AI_ACTION);
        if (stringExtra != null) {
            onTextSearch(stringExtra, 2);
            return;
        }
        if (stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
            if (stringExtra2 != null) {
                onTextSearch(stringExtra2, 3);
                return;
            } else {
                Logger.INSTANCE.e(this.TAG, "Intent has no extra query text!");
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArrayListExtra.size(); i++) {
            String phoneNum = TextProcessUtils.formatPhoneNum(stringArrayListExtra.get(i));
            if (!TextUtils.isEmpty(phoneNum)) {
                if (i > 0) {
                    sb.append(StringUtils.SPACE);
                }
                sb.append(phoneNum);
            }
        }
        String string = sb.toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        Logger.INSTANCE.i(this.TAG, "queryNum:" + string);
        onTextSearch(sb.toString(), 1);
    }

    public void openNoteById(long j, long j2) {
        Intent intent;
        Logger.INSTANCE.d(this.TAG, "openNoteById:" + j + ",folderId:" + j2);
        NoteEntity noteEntityLoadById = NoteEntityHelper.loadById(getActivity(), j);
        if (noteEntityLoadById == null) {
            return;
        }
        if (NoteModel.NoteType.TYPE_MIND.equals(noteEntityLoadById.getNoteType())) {
            intent = new Intent(getActivity(), (Class<?>) WebViewBrainActivity.class);
            intent.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 1);
        } else if ("handwrite".equals(noteEntityLoadById.getNoteType()) || "multi_handwrite".equals(noteEntityLoadById.getNoteType())) {
            intent = new Intent(getActivity(), (Class<?>) PhoneMultiHandWriteActivity.class);
        } else if ("common".equals(noteEntityLoadById.getNoteType())) {
            Intent intent2 = new Intent(getActivity(), (Class<?>) EditActivity.class);
            intent2.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteEntityLoadById.getThemeId());
            intent2.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
            intent2.putExtra("com.miui.notes.folder_id", j2);
            intent = intent2;
        } else {
            intent = null;
        }
        if (intent == null) {
            return;
        }
        intent.setAction("android.intent.action.VIEW");
        intent.putExtra("android.intent.extra.UID", j);
        this.homeViewModel.toDetailLauncher.launch(intent);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void addNote(View view) {
        Logger.INSTANCE.d(this.TAG, "addNote");
        Trace.beginSection("addNote()");
        this.mMiCloudStateView.setLayoutUpdateListener(null);
        this.mAdapter.frozeData(true);
        addNoteFromFab(this, view, null, FolderModel.isFakeFolder(this.homeViewModel.getFolderId()) ? 0L : this.homeViewModel.getFolderId(), EditorActionMode.EDIT, this.mIsFromPrivate);
        NotesPageStat.reportNewNote();
        Trace.endSection();
        NotesPageStat.QUIT_TYPE = NotesPageStat.GO_NEW_NOTES;
    }

    public void addNoteFromFab(Fragment fragment, View view, Integer num, long j, EditorActionMode editorActionMode, boolean z) {
        boolean zCanUseMiuiSdkAnim = canUseMiuiSdkAnim(view, fragment.getActivity());
        Logger.INSTANCE.d(this.TAG, "addNoteFromFab,use miuix ani:" + zCanUseMiuiSdkAnim);
        Trace.beginSection("addNoteFromFab(with miuix-animation:" + zCanUseMiuiSdkAnim + ")");
        Intent intent = new Intent(getActivity(), (Class<?>) EditActivity.class);
        intent.putExtra("com.miui.notes.folder_id", j);
        intent.putExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, ResourceParser.getDefaultBgId(getActivity()));
        intent.putExtra(NoteIntent.KEY_ACTION_MODE, editorActionMode);
        intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, 0);
        intent.putExtra(NoteIntent.KEY_IS_PENDING, false);
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = z;
        intent.setAction("android.intent.action.INSERT_OR_EDIT");
        NotesTextStat.isCreateCommonNote = true;
        if (zCanUseMiuiSdkAnim) {
            Logger.INSTANCE.d(this.TAG, "userMiuixAniToEditorActivity");
            float scaleX = view.getScaleX();
            Rect rect = new Rect(view.getPaddingStart(), view.getPaddingTop(), view.getWidth() - view.getPaddingEnd(), view.getHeight() - view.getPaddingBottom());
            int iRound = Math.round(((view.getWidth() - view.getPaddingRight()) - view.getPaddingLeft()) / 2.0f);
            int iIntValue = ((Integer) PhoneNoteListPageFragment$$ExternalSyntheticBackport0.m(num, new Supplier() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Integer.valueOf(NoteApp.getInstance().getColor(R.color.content_add_floating_action_button_color_n));
                }
            })).intValue();
            Bitmap bitmapCaptureViewSnapshot = BitmapUtils.INSTANCE.captureViewSnapshot(view);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapCaptureViewSnapshot, rect.left, rect.top, rect.width(), rect.height());
            ActivityOptions activityOptionsMakeScaleUpAnim = ActivityOptionsHelper.makeScaleUpAnim(view, bitmapCreateBitmap, view.getLeft(), view.getTop(), iRound, iIntValue, scaleX, this.handler, new SafeViewCallback(this, view, true), new SafeViewCallback(this, view, false), null, null, 102);
            Logger.INSTANCE.d(this.TAG, "jumpToDetailActivityWithMiuixAnimation...");
            this.homeViewModel.launchToDetailActivity(intent, new ActivityOptionCompatImpl(activityOptionsMakeScaleUpAnim));
            bitmapCreateBitmap.recycle();
            if (bitmapCaptureViewSnapshot != null) {
                bitmapCaptureViewSnapshot.recycle();
            }
        } else {
            Logger.INSTANCE.d(this.TAG, "jumpToDetailActivity");
            this.homeViewModel.launchToDetailActivity(intent);
        }
        Trace.endSection();
    }

    private boolean canUseMiuiSdkAnim(View view, Activity activity) {
        if (LiteUtils.isLiteOrMiddle()) {
            return false;
        }
        String str = Build.DEVICE;
        return (TextUtils.isEmpty(str) || !(str.equals("vangogh") || str.equals("lime"))) && view != null && ActivityOptionsHelper.isSupportScaleUpDown() && !UIUtils.isInMultiWindowMode(activity);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void openNoteFromClick(View view) {
        Logger.INSTANCE.d(this.TAG, "openNoteFromClick");
        int childLayoutPosition = this.mRecyclerView.getChildLayoutPosition(view);
        ItemCache item = this.mAdapter.getItem(childLayoutPosition);
        if (item != null) {
            NoteContentStat.IS_STICK = item.isStick();
            if (item.getCacheType() == 0) {
                if (this.homeViewModel.isSearchMode() && this.searchInput != null && this.searchInput.hasFocus()) {
                    this.searchInput.clearFocus();
                }
                this.homeViewModel.setClickedNotePosition(childLayoutPosition);
                NoteCache noteCache = (NoteCache) item.getCacheObject();
                if (noteCache.getNoteContentType() == 5) {
                    int color = NoteApp.getInstance().getColor(R.color.anim_fore_ground_color);
                    Folme.use(view).touch().setTouchUp();
                    onOpenMindNote(this, view, Integer.valueOf(color), this.homeViewModel.getFolderId(), noteCache, TextUtils.isEmpty(this.mBindContext.getSearchToken()) ? this.mBindContext.getSearchTokenBackUp() : this.mBindContext.getSearchToken());
                    return;
                }
                if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                    Folme.use(view).touch().setTouchUp();
                    onOpenMultiHandwriteNote(this, view, Integer.valueOf(NoteApp.getInstance().getColor(R.color.anim_fore_ground_color)), noteCache, TextUtils.isEmpty(this.mBindContext.getSearchToken()) ? this.mBindContext.getSearchTokenBackUp() : this.mBindContext.getSearchToken());
                    return;
                }
                this.homeViewModel.getFolderId();
                Folme.use(view).touch().setTouchUp();
                this.mPreLoadThemeResDisposable.clear();
                int color2 = getContext().getColor(((SimpleTheme) ResourceManager.getTheme(noteCache.getNote().getThemeId())).getBlurMixColor4());
                ResourceManager.clearPreloadResCache();
                if (LiteUtils.isLiteOrMiddle() && LiteUtils.isNewLiteOrMiddle()) {
                    new PreLoadLiteNoteThemeResTask(this.mPreLoadThemeResDisposable, noteCache.getNote().getThemeId(), getContext()).execute();
                } else {
                    new PreLoadNoteThemeResTask(this.mPreLoadThemeResDisposable, noteCache.getNote().getThemeId(), getContext()).execute();
                }
                if (noteCache.getNote().getThemeId() == 0) {
                    color2 = NoteApp.getInstance().getColor(R.color.note_edit_blur_mix_color4_default);
                }
                if (!this.mAdapter.isGridMode()) {
                    color2 = NoteApp.getInstance().getColor(R.color.anim_fore_ground_color);
                }
                onOpenNote(this, view, Integer.valueOf(color2), this.homeViewModel.getFolderId(), noteCache, TextUtils.isEmpty(this.mBindContext.getSearchToken()) ? this.mBindContext.getSearchTokenBackUp() : this.mBindContext.getSearchToken());
                if (this.homeViewModel.isSearchMode()) {
                    Logger.INSTANCE.d(this.TAG, "openNoteFromClick in search mode");
                    NoteQueryStat.reportNoteSearchResultPositiveClickEvent(String.valueOf(this.mAdapter.getRealItemCount()), this.mBindContext.getSearchToken(), childLayoutPosition, String.valueOf(this.homeViewModel.isAiSearchMode()));
                }
            }
        }
    }

    protected void onOpenNote(Fragment fragment, View view, Integer num, long j, NoteCache noteCache, String str) {
        openNote(fragment, view, num, j, noteCache, str, this.mIsFromPrivate);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void openSearchResultNote(View view, NoteCache noteCache, String str) {
        int color = NoteApp.getInstance().getColor(R.color.anim_fore_ground_color);
        if (noteCache.getNoteContentType() == 5) {
            Folme.use(view).touch().setTouchUp();
            onOpenMindNote(this, view, Integer.valueOf(color), this.homeViewModel.getFolderId(), noteCache, str);
        } else if (noteCache.getNoteContentType() == 6) {
            Folme.use(view).touch().setTouchUp();
            onOpenHandwriteNote(this, view, Integer.valueOf(color), noteCache, str);
        } else if (noteCache.getNoteContentType() == 7) {
            Folme.use(view).touch().setTouchUp();
            onOpenMultiHandwriteNote(this, view, Integer.valueOf(color), noteCache, str);
        } else {
            Folme.use(view).touch().setTouchUp();
            onOpenNote(this, view, Integer.valueOf(color), this.homeViewModel.getFolderId(), noteCache, str);
        }
    }

    protected void onOpenMindNote(Fragment fragment, View view, Integer num, long j, NoteCache noteCache, String str) {
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = this.mIsFromPrivate;
        openMindNote(fragment, view, num, j, noteCache, str, this.mIsFromPrivate);
    }

    protected void onOpenHandwriteNote(Fragment fragment, View view, Integer num, NoteCache noteCache, String str) {
        openHandWriteNote(fragment, view, num, noteCache, str, this.mIsFromPrivate);
    }

    protected void onOpenMultiHandwriteNote(Fragment fragment, View view, Integer num, NoteCache noteCache, String str) {
        openMultiHandWriteNote(fragment, view, num, noteCache, str, this.mIsFromPrivate);
    }

    public void openNote(Fragment fragment, View view, Integer num, long j, NoteCache noteCache, String str, boolean z) {
        int color;
        boolean zCanUseMiuiSdkAnim = canUseMiuiSdkAnim(view, fragment.getActivity());
        try {
            Trace.beginSection("openNote(" + zCanUseMiuiSdkAnim + ")");
            Logger.INSTANCE.d(this.TAG, "openNote with miuiAni:" + zCanUseMiuiSdkAnim);
            Intent intent = new Intent(getContext(), (Class<?>) EditActivity.class);
            intent.putExtra("com.miui.notes.folder_id", j);
            intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
            intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, NoteOperationHelper.hasNewStyle(noteCache.getNote().getSnippet()));
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("intent_extra_data_key", str);
            }
            if (this.homeViewModel.isAiSearchMode() && this.mBindContext.getAiQueryNoteList(noteCache.getNote().getId()) != null) {
                intent.putStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY, this.mBindContext.getAiQueryNoteList(noteCache.getNote().getId()).getHighlightKeywords());
            }
            intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
            intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteCache.getNote().getThemeId());
            PrivateNotesListActivity.ACTION_FROM_PRIVACY = z;
            intent.setAction("android.intent.action.VIEW");
            if (zCanUseMiuiSdkAnim) {
                Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                int dimensionPixelSize = fragment.getContext().getResources().getDimensionPixelSize(R.dimen.v12_grid_item_shape_radius);
                if (num != null) {
                    color = num.intValue();
                } else {
                    color = NoteApp.getInstance().getColor(R.color.anim_fore_ground_color);
                }
                this.homeViewModel.launchToDetailActivity(intent, new ActivityOptionCompatImpl(ActivityOptionsHelper.makeScaleUpAnim(view, rect, dimensionPixelSize, color, 103)));
            } else {
                this.homeViewModel.setClickedNotePosition(-1);
                this.homeViewModel.launchToDetailActivity(intent);
            }
        } finally {
            Trace.endSection();
        }
    }

    public void openMindNote(Fragment fragment, View view, Integer num, long j, NoteCache noteCache, String str, boolean z) {
        String str2;
        int i;
        int i2;
        if (LiteUtils.isSuperLite()) {
            Toast.makeText(getContext(), R.string.note_mindnote_not_support_in_phone_model, 0).show();
            return;
        }
        boolean zCanUseMiuiSdkAnim = canUseMiuiSdkAnim(view, fragment.getActivity());
        try {
            Trace.beginSection("openMindNote(" + zCanUseMiuiSdkAnim + ")");
            Logger.INSTANCE.d(this.TAG, "openMindNote with miuiAni:" + zCanUseMiuiSdkAnim);
            Intent intent = new Intent(getContext(), (Class<?>) WebViewBrainActivity.class);
            intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
            intent.putExtra("com.miui.notes.folder_id", j);
            if (TextUtils.isEmpty(str)) {
                str2 = str;
            } else {
                str2 = str;
                intent.putExtra("intent_extra_data_key", str2);
            }
            boolean z2 = true;
            intent.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 1);
            PrivateNotesListActivity.ACTION_FROM_PRIVACY = z;
            String mindContent = noteCache.getNote().getMindContent();
            if (TextUtils.isEmpty(mindContent)) {
                i = 0;
                i2 = 0;
            } else {
                i = !((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).isMap ? 1 : 0;
                i2 = 1;
            }
            if (!this.homeViewModel.isAiSearchMode() || this.mBindContext.getAiQueryNoteList(noteCache.getNote().getId()) == null) {
                z2 = false;
            }
            NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(noteCache.getNote().getId(), noteCache.getNote().getFolderId(), str2, 1, noteCache.getNote().getUpdatedAt(), i, i2, noteCache.getNote().getTitle(), false, z2 ? this.mBindContext.getAiQueryNoteList(noteCache.getNote().getId()).getHighlightKeywords() : null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", noteLoadDataEntity);
            intent.putExtras(bundle);
            intent.setAction("android.intent.action.VIEW");
            if (zCanUseMiuiSdkAnim) {
                this.homeViewModel.launchToDetailActivity(intent, new ActivityOptionCompatImpl(ActivityOptionsHelper.makeScaleUpAnim(view, new Rect(0, 0, view.getWidth(), view.getHeight()), NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.v12_grid_item_shape_radius), num.intValue(), 103)));
            } else {
                this.homeViewModel.setClickedNotePosition(-1);
                this.homeViewModel.launchToDetailActivity(intent);
            }
        } finally {
            Trace.endSection();
        }
    }

    public void openHandWriteNote(Fragment fragment, View view, Integer num, NoteCache noteCache, String str, boolean z) {
        Intent intent = new Intent(getContext(), (Class<?>) PhoneHandWriteActivity.class);
        intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("intent_extra_data_key", str);
        }
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = z;
        intent.setAction("android.intent.action.VIEW");
        if (canUseMiuiSdkAnim(view, fragment.getActivity())) {
            this.homeViewModel.launchToDetailActivity(intent, new ActivityOptionCompatImpl(ActivityOptionsHelper.makeScaleUpAnim(view, new Rect(0, 0, view.getWidth(), view.getHeight()), NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.v12_grid_item_shape_radius), num.intValue(), 103)));
        } else {
            this.homeViewModel.setClickedNotePosition(-1);
            this.homeViewModel.launchToDetailActivity(intent);
        }
    }

    public void openMultiHandWriteNote(Fragment fragment, View view, Integer num, NoteCache noteCache, String str, boolean z) {
        Intent intent = new Intent(getContext(), (Class<?>) PhoneMultiHandWriteActivity.class);
        intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("intent_extra_data_key", str);
        }
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = z;
        intent.setAction("android.intent.action.VIEW");
        if (canUseMiuiSdkAnim(view, fragment.getActivity())) {
            this.homeViewModel.launchToDetailActivity(intent, new ActivityOptionCompatImpl(ActivityOptionsHelper.makeScaleUpAnim(view, new Rect(0, 0, view.getWidth(), view.getHeight()), NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.v12_grid_item_shape_radius), num.intValue(), 103)));
        } else {
            this.homeViewModel.setClickedNotePosition(-1);
            this.homeViewModel.launchToDetailActivity(intent);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (this.mBlankView != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBlankView.getLayoutParams();
            if (z) {
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.phone_multi_screen_note_blank_view_margin_top);
            } else {
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.phone_note_blank_view_margin_top);
            }
            this.mBlankView.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(final View view) {
        if (view.getId() == R.id.note_add) {
            Logger.INSTANCE.d(this.TAG, "click note add");
            if (UIUtils.isBrowseMode(getActivity())) {
                Toast.makeText(getContext(), R.string.warn_enter_browse_mode_forbid_create, 0).show();
                return;
            }
            Trace.beginSection("addNoteBtnClick");
            if (!LiteUtils.isLiteOrMiddle() && !LiteUtils.isNewLiteOrMiddle()) {
                Logger.INSTANCE.d(this.TAG, "click note add,not liteMiddleOrNewLiteMiddle");
                ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(new Runnable() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$4();
                    }
                });
            } else if (LiteUtils.isNewLiteOrMiddle()) {
                Logger.INSTANCE.d(this.TAG, "click note add,newLiteOrMiddle");
                new AsyncInflate().inflateLayout(getActivity());
            }
            if (LiteUtils.isNewLiteOrMiddle() && this.handler != null) {
                Logger.INSTANCE.d(this.TAG, "click note add btn ,post delay 180ms->addNote");
                this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$5(view);
                    }
                }, 180L);
            } else {
                Logger.INSTANCE.d(this.TAG, "click note add,addNote..");
                addNote(view);
            }
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        WebSettings.getDefaultUserAgent(NoteApp.getInstance());
        new WebRichEditorClient(getContext());
        HybridEditorScriptInterface.initStaticInfo();
        BaseNormalNoteEditFragment.mActionbarView = LayoutInflater.from(NoteApp.getInstance()).inflate(R.layout.v12_phone_edit_header, (ViewGroup) null);
        Logger.INSTANCE.d(this.TAG, "preInitForEditActivity ts cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        if (isAdded()) {
            addNote(view);
        }
    }

    private void handleTapShare(Intent intent) {
        Logger.INSTANCE.d(this.TAG, "handleTapShareSend: handle TapShare intent");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        ArrayList parcelableArrayList = extras.getParcelableArrayList("tap_share_uris");
        if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
            Logger.INSTANCE.d(this.TAG, "noteListTapShareUris is null");
        } else {
            new TapShareNotesInsertTask().execute(parcelableArrayList);
        }
    }

    public class TapShareNotesInsertTask extends AsyncTask<List<Uri>, Void, List<Long>> {
        public TapShareNotesInsertTask() {
        }

        @Override // android.os.AsyncTask
        @Deprecated
        protected void onPreExecute() {
            super.onPreExecute();
            Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "TapShareNotesInsertTask: Starting to insert notes");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public List<Long> doInBackground(List<Uri>... listArr) {
            List<Uri> list = listArr[0];
            Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "uriList: " + list.size());
            ArrayList arrayList = new ArrayList();
            try {
                List<Long> listRestoreNotesFromUris = TapShareDataPrepareUtils.restoreNotesFromUris(PhoneNoteListPageFragment.this.getActivity(), list);
                Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "noteListInsertNoteIds: " + listRestoreNotesFromUris);
                if (listRestoreNotesFromUris != null) {
                    arrayList.addAll(listRestoreNotesFromUris);
                }
                return arrayList;
            } catch (Exception e) {
                Toast.makeText(PhoneNoteListPageFragment.this.getActivity(), PhoneNoteListPageFragment.this.getString(R.string.tapshare_restore_notes_fail_toast), 0).show();
                Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "TapShareNotesInsertTask: Error restoring notes: " + e.getMessage());
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(List<Long> list) {
            super.onPostExecute(list);
            if (list != null && !list.isEmpty()) {
                Toast.makeText(PhoneNoteListPageFragment.this.getActivity(), PhoneNoteListPageFragment.this.getString(R.string.tapshare_restore_notes_success_toast), 0).show();
                Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "TapShareNotesInsertTask: Successfully restored " + list.size() + " notes");
            } else {
                Logger.INSTANCE.d(PhoneNoteListPageFragment.this.TAG, "TapShareNotesInsertTask: No notes were restored");
            }
            PhoneNoteListPageFragment.this.clearTapShareUrisFromIntent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearTapShareUrisFromIntent() {
        try {
            Intent value = this.homeViewModel.getIntent().getValue();
            if (value != null) {
                value.removeExtra("tap_share_uris");
                this.homeViewModel.setIntent(value);
                Logger.INSTANCE.d(this.TAG, "Cleared tap_share_uris from intent to prevent duplicate import");
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(this.TAG, "Error clearing tap_share_uris from intent: " + e.getMessage());
        }
    }
}
