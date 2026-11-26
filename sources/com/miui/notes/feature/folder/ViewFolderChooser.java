package com.miui.notes.feature.folder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.util.AsyncTaskWithProgressDialog;
import com.miui.notes.ui.fragment.BaseFolderChooser;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import java.util.Arrays;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ViewFolderChooser extends BaseFolderChooser {
    private NewFolderButtonAccessibilityDelegatte accessibilityDelegatte;
    private View createFolderBtn;
    private View createFolderLayout;
    private CommonFolderDialogListAdapter mAdapter;
    private Context mContext;
    private final List<FolderModel> mFolderModels;
    private RecyclerView mRecyclerView;
    private FrameLayout mRootView;
    private View rvContent;
    private View view;

    /* JADX INFO: Access modifiers changed from: private */
    interface onTaskFinished {
        void onTaskFinished(int i);
    }

    public ViewFolderChooser(DialogManagedFragment dialogManagedFragment, long j, long[] jArr, List<FolderModel> list, Context context) {
        super(dialogManagedFragment, null, Long.valueOf(j), jArr);
        this.mFolderModels = list;
        this.mContext = context;
    }

    @Override // com.miui.notes.ui.fragment.BaseFolderChooser
    public void show() {
        if (this.mFragment == null || this.mFragment.getView() == null || this.mFragment.getView().getContext() == null) {
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mFragment.getView().findViewById(R.id.chooser_folder_dialog_fragment);
        this.mRootView = frameLayout;
        if (frameLayout != null) {
            frameLayout.removeView(this.view);
            this.view = LayoutInflater.from(this.mFragment.getView().getContext()).inflate(R.layout.view_choice_folder, (ViewGroup) this.mRootView, true);
            initView();
            initData();
        }
    }

    private void initView() {
        this.rvContent = this.view.findViewById(R.id.rv_content);
        RecyclerView recyclerView = (RecyclerView) this.view.findViewById(R.id.chooser_dialog_rv);
        this.mRecyclerView = recyclerView;
        recyclerView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.feature.folder.ViewFolderChooser.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getResources().getDimensionPixelOffset(R.dimen.folder_list_item_bg_radius));
            }
        });
        this.mRecyclerView.setClipToOutline(true);
        this.mRecyclerView.addItemDecoration(new FolderItemDecoration(this.view.getResources().getDimensionPixelOffset(R.dimen.folder_list_item_margin_bottom), 0));
        this.mRecyclerView.setLayoutManager(new NoteGridLayoutManager(1, 1));
        View viewFindViewById = this.view.findViewById(R.id.fold_folder_dialog_create_folder_layout);
        this.createFolderLayout = viewFindViewById;
        Folme.useAt(viewFindViewById).touch().handleTouchOf(this.createFolderLayout, new AnimConfig[0]);
        setNewFolderAccessibilityDelegatte(this.createFolderLayout);
        View viewFindViewById2 = this.view.findViewById(R.id.folder_dialog_list_create_folder);
        this.createFolderBtn = viewFindViewById2;
        setNewFolderAccessibilityDelegatte(viewFindViewById2);
        this.createFolderBtn.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.ViewFolderChooser.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ViewFolderChooser.this.showCreateFolderDialog();
            }
        });
        smallWindowAdapter();
    }

    public void stopScroll() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.stopScroll();
        }
    }

    private void initData() {
        CommonFolderDialogListAdapter commonFolderDialogListAdapter = new CommonFolderDialogListAdapter();
        this.mAdapter = commonFolderDialogListAdapter;
        commonFolderDialogListAdapter.setFromFolderIcon(false);
        this.mAdapter.setBindContext(new BindContext());
        this.mAdapter.setSelectId(this.mCurrentId.longValue());
        this.mAdapter.setData(this.mFolderModels);
        this.mAdapter.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.ViewFolderChooser$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$0(view);
            }
        });
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$0(View view) {
        onFolderItemClick(view, this.mRecyclerView.getChildLayoutPosition(view));
    }

    @Override // com.miui.notes.ui.fragment.BaseFolderChooser
    public void onFolderItemClick(View view, int i) {
        if ((this.mFragment == null ? null : this.mFragment.getActivity()) == null) {
            return;
        }
        long itemId = this.mAdapter.getItemId(i);
        if (itemId == this.mCurrentId.longValue()) {
            return;
        }
        String folderName = this.mAdapter.getFolderName(i);
        if (itemId == -4097) {
            itemId = 0;
        }
        if (itemId == 0) {
            folderName = view.getResources().getString(R.string.unclassfied_folder_name);
        }
        moveCheckedItems(itemId, this.mCheckedIds, folderName);
        Logger.INSTANCE.d(getClass().getSimpleName(), "id:" + itemId + ",mCheckedIds" + Arrays.toString(this.mCheckedIds));
        PadHandWriteStat.reportMoreClickEvent("move_to_exist");
    }

    @Override // com.miui.notes.ui.fragment.BaseFolderChooser
    public void showCreateFolderDialog() {
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(this.mFragment.getActivity());
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.feature.folder.ViewFolderChooser$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showCreateFolderDialog$1(dialogInterface);
            }
        });
        createFolderDialog.show();
        this.mFragment.manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateFolderDialog$1(DialogInterface dialogInterface) {
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        if (createFolderDialog == null || createFolderDialog.getFolderId() <= 0) {
            return;
        }
        refreshFolderInfo();
        moveCheckedItems(createFolderDialog.getFolderId(), this.mCheckedIds, createFolderDialog.getFolderName());
        stopScroll();
    }

    public void moveCheckedItems(final long j, long[] jArr, final String str) {
        Logger.INSTANCE.d(getClass().getSimpleName(), "moveCheckedItems checkedIds:" + Arrays.toString(jArr) + "->folderId" + j);
        NoteMoveTask noteMoveTask = new NoteMoveTask(this.mFragment, jArr, j, str);
        noteMoveTask.setCallback(new onTaskFinished() { // from class: com.miui.notes.feature.folder.ViewFolderChooser$$ExternalSyntheticLambda3
            @Override // com.miui.notes.feature.folder.ViewFolderChooser.onTaskFinished
            public final void onTaskFinished(int i) {
                this.f$0.lambda$moveCheckedItems$2(j, str, i);
            }
        });
        noteMoveTask.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveCheckedItems$2(long j, String str, int i) {
        onFolderChoose(j, this.mCheckedIds, str);
    }

    public static class NoteMoveTask extends NoteOperationTask {
        private onTaskFinished callback;
        protected long iFolderId;
        protected String iFolderName;

        /* JADX INFO: Access modifiers changed from: private */
        public void setCallback(onTaskFinished ontaskfinished) {
            this.callback = ontaskfinished;
        }

        public NoteMoveTask(DialogManagedFragment dialogManagedFragment, long[] jArr, long j, String str) {
            super(dialogManagedFragment, jArr, R.string.dlg_move_message);
            this.iFolderId = j;
            this.iFolderName = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            int iMove = NoteStore.move(context, this.iIds, this.iFolderId);
            Logger.INSTANCE.d(getClass().getSimpleName(), "doBackground work move finished " + iMove);
            return Integer.valueOf(iMove);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.feature.folder.ViewFolderChooser.NoteOperationTask, com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            Logger.INSTANCE.d(getClass().getSimpleName(), "on post work move finished :" + num);
            onTaskFinished ontaskfinished = this.callback;
            if (ontaskfinished != null) {
                ontaskfinished.onTaskFinished(num.intValue());
            }
            if (num != null) {
                showToast(activity, num.intValue());
            }
        }

        protected void showToast(Activity activity, int i) {
            String quantityString;
            if (this.iFolderId == 0) {
                quantityString = activity.getResources().getQuantityString(R.plurals.format_move_notes_out_folder, i, Integer.valueOf(i));
            } else {
                quantityString = activity.getResources().getQuantityString(R.plurals.format_move_notes_to_folder, i, Integer.valueOf(i), this.iFolderName);
            }
            Toast.makeText(activity.getApplicationContext(), quantityString, 0).show();
        }
    }

    protected static abstract class NoteOperationTask extends AsyncTaskWithProgressDialog<DialogManagedFragment, Void, Void, Integer> {
        protected long[] iIds;
        private int iMsgResId;

        public NoteOperationTask(DialogManagedFragment dialogManagedFragment, long[] jArr, int i) {
            super(dialogManagedFragment);
            this.iIds = jArr;
            this.iMsgResId = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            updateWorkingNoteAfterListOperation(activity);
        }

        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        protected Dialog onCreateDialog(Activity activity) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(activity.getString(this.iMsgResId));
            progressDialog.setCancelable(false);
            return progressDialog;
        }

        protected void updateWorkingNoteAfterListOperation(Activity activity) {
            Intent intent = new Intent();
            intent.setAction(FrameConstant.ACTION_NOTE_DATA_UPDATE);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    private void setNewFolderAccessibilityDelegatte(View view) {
        this.accessibilityDelegatte = new NewFolderButtonAccessibilityDelegatte();
        this.accessibilityDelegatte.onInitializeAccessibilityNodeInfo(view, view.createAccessibilityNodeInfo());
        view.setAccessibilityDelegate(this.accessibilityDelegatte);
    }

    private class NewFolderButtonAccessibilityDelegatte extends View.AccessibilityDelegate {
        public NewFolderButtonAccessibilityDelegatte() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", ViewFolderChooser.this.mContext.getString(R.string.button_talkback));
        }
    }

    @Override // com.miui.notes.ui.fragment.BaseFolderChooser
    public void cancel() {
        onCancel();
        onDismiss(false);
    }

    public void smallWindowAdapter() {
        View view = this.createFolderBtn;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.folder.ViewFolderChooser.3
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    Activity activity = UIUtils.getActivity(ViewFolderChooser.this.createFolderBtn.getContext());
                    if (rootWindowInsets != null && activity != null) {
                        if (UIUtils.isSmallWindowMode(activity) || UIUtils.isInMultiWindowMode(activity) || UIUtils.isInNarrowScreen(activity) || RomUtils.isPhoneDevice()) {
                            int navigationBarHeight = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).bottom;
                            if (navigationBarHeight == 0) {
                                navigationBarHeight = UIUtils.getNavigationBarHeight(activity);
                            }
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ViewFolderChooser.this.createFolderBtn.getLayoutParams();
                            marginLayoutParams.bottomMargin = navigationBarHeight;
                            ViewFolderChooser.this.createFolderBtn.setLayoutParams(marginLayoutParams);
                        } else {
                            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) ViewFolderChooser.this.createFolderBtn.getLayoutParams();
                            marginLayoutParams2.bottomMargin = 0;
                            ViewFolderChooser.this.createFolderBtn.setLayoutParams(marginLayoutParams2);
                            return windowInsetsCompat;
                        }
                    }
                    return windowInsetsCompat;
                }
            });
        }
        View view2 = this.rvContent;
        if (view2 != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view2, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.folder.ViewFolderChooser.4
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view3, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view3);
                    Activity activity = UIUtils.getActivity(ViewFolderChooser.this.rvContent.getContext());
                    if (rootWindowInsets != null && activity != null) {
                        if (UIUtils.isInMultiWindowMode(activity)) {
                            ViewFolderChooser.this.rvContent.setPadding(0, 0, rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).right, 0);
                            return windowInsetsCompat;
                        }
                        ViewFolderChooser.this.rvContent.setPadding(0, 0, 0, 0);
                    }
                    return windowInsetsCompat;
                }
            });
        }
        View view3 = this.createFolderBtn;
        if (view3 != null) {
            view3.post(new Runnable() { // from class: com.miui.notes.feature.folder.ViewFolderChooser$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$smallWindowAdapter$3();
                }
            });
        }
        View view4 = this.rvContent;
        if (view4 != null) {
            view4.post(new Runnable() { // from class: com.miui.notes.feature.folder.ViewFolderChooser$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$smallWindowAdapter$4();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$smallWindowAdapter$3() {
        ViewCompat.requestApplyInsets(this.createFolderBtn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$smallWindowAdapter$4() {
        ViewCompat.requestApplyInsets(this.rvContent);
    }
}
