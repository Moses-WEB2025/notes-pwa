package com.miui.notes.feature.folder;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Outline;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.folder.BaseFolderDialogFragment;
import com.miui.notes.model.FolderModel;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.responsive.map.ScreenSpec;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class CommonFolderDialogFragment extends BaseFolderDialogFragment {
    public static final int ALL_FOLDER_ITEM_ID = -4097;
    private static final String TAG = "FoldFolderDialogFragment";
    private ImageView actionBack;
    private ImageView actionEnd;
    private View createFolderBtn;
    private View createFolderLayout;
    private View createFolderMask;
    private TextView mFolderDialogTitle;
    private boolean mIsActionMode;
    protected View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment.2
        private Long lastEnterEditTime = null;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CommonFolderDialogFragment.this.mRecyclerView.stopScroll();
            FolderModel item = CommonFolderDialogFragment.this.mAdapter.getItem(CommonFolderDialogFragment.this.mRecyclerView.getChildLayoutPosition(view));
            if (item == null) {
                return;
            }
            if (CommonFolderDialogFragment.this.mRecyclerViewWrapper.isInActionMode()) {
                if (item.getId() == -4097 || item.getId() == 0) {
                    return;
                }
                if (!view.isSelected()) {
                    CommonFolderDialogFragment.this.mRecyclerViewWrapper.toggleItemChecked(view);
                    return;
                } else {
                    view.setSelected(false);
                    CommonFolderDialogFragment.this.mRecyclerViewWrapper.setItemChecked(view, false);
                    return;
                }
            }
            if (CommonFolderDialogFragment.this.mAdapter != null) {
                if (this.lastEnterEditTime != null && System.currentTimeMillis() - this.lastEnterEditTime.longValue() <= 400) {
                    Logger.INSTANCE.i(CommonFolderDialogFragment.TAG, "mItemClickListener onClick time too short");
                    this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
                } else {
                    this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
                    if (CommonFolderDialogFragment.this.mController != null) {
                        CommonFolderDialogFragment.this.mController.finishWithResult(item.getId());
                    }
                }
            }
        }
    };
    private View rvContent;

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    public int getLayoutId() {
        return R.layout.common_folder_list_dialog_fragment;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected void updateActionModeButton1(ActionMode actionMode) {
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    public void initActionBar() {
        this.mActionBar = getActionBar();
        if (this.mActionBar != null) {
            this.mActionBar.setDisplayHomeAsUpEnabled(false);
            this.mActionBar.setExpandState(0);
            this.actionBack = new ImageView(getActivity());
            this.actionEnd = new ImageView(getActivity());
            this.mFolderDialogTitle = (TextView) this.mActivity.findViewById(R.id.action_bar_title);
            updateActionbarButtonToNormal();
        }
    }

    private void updateActionbarButtonToNormal() {
        this.actionBack.setBackgroundResource(R.drawable.miui_action_bar_back);
        this.actionBack.setScaleType(ImageView.ScaleType.CENTER);
        this.actionBack.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateActionbarButtonToNormal$0(view);
            }
        });
        this.actionBack.setContentDescription(getResources().getString(R.string.actionbar_home_description));
        this.mActionBar.setStartView(this.actionBack);
        this.actionEnd.setBackgroundResource(R.drawable.miui_ic_delete);
        this.actionBack.setScaleType(ImageView.ScaleType.CENTER);
        this.actionEnd.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateActionbarButtonToNormal$1(view);
            }
        });
        this.actionEnd.setContentDescription(getResources().getString(R.string.trash_folder_name));
        this.mActionBar.setEndView(this.actionEnd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateActionbarButtonToNormal$0(View view) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.stopScroll();
        }
        if (this.mActivity != null) {
            this.mActivity.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateActionbarButtonToNormal$1(View view) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.stopScroll();
        }
        setAccessibility(false);
        this.mController.showTrashFragment();
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    public int getThemeRes() {
        return !RomUtils.isPadMode() ? R.style.NoteTheme_CommonPhoneFolderDialogFragment : R.style.NoteTheme_CommonFolderDialogFragment;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    public void setAccessibility(boolean z) {
        int i = z ? 1 : 4;
        if (this.mRecyclerView != null) {
            this.mRecyclerView.setImportantForAccessibility(i);
        }
        View view = this.createFolderLayout;
        if (view != null) {
            view.setImportantForAccessibility(i);
        }
        ImageView imageView = this.actionBack;
        if (imageView != null) {
            imageView.setImportantForAccessibility(i);
        }
        ImageView imageView2 = this.actionEnd;
        if (imageView2 != null) {
            imageView2.setImportantForAccessibility(i);
        }
        TextView textView = this.mFolderDialogTitle;
        if (textView != null) {
            textView.setImportantForAccessibility(i);
        }
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    public void initView(View view) {
        initCreateFolderLayout(view);
        initRecyclerView(view);
        queryFolderInfo();
        smallWindowAdapter();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
    }

    private void initCreateFolderLayout(View view) {
        this.rvContent = view.findViewById(R.id.rv_content);
        this.createFolderLayout = view.findViewById(R.id.fold_folder_dialog_create_folder_layout);
        this.createFolderMask = view.findViewById(R.id.folder_dialog_create_folder_mask);
        View viewFindViewById = view.findViewById(R.id.folder_dialog_list_create_folder);
        this.createFolderBtn = viewFindViewById;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(viewFindViewById);
        Folme.useAt(this.createFolderBtn).touch().setScale(0.93f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.createFolderBtn, new AnimConfig[0]);
        this.createFolderBtn.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initCreateFolderLayout$2(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCreateFolderLayout$2(View view) {
        showCreateFolderDialog();
    }

    protected void initRecyclerView(View view) {
        this.mRecyclerView = (BaseNoteRecyclerView) view.findViewById(R.id.fold_folder_dialog_rv);
        this.mRecyclerView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), CommonFolderDialogFragment.this.getResources().getDimensionPixelOffset(R.dimen.folder_list_item_bg_radius));
            }
        });
        this.mRecyclerView.setClipToOutline(true);
        this.mRecyclerView.addItemDecoration(new FolderItemDecoration(getResources().getDimensionPixelOffset(R.dimen.miuix_theme_margin_horizontal_common), 0));
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        this.mRecyclerViewWrapper = new EditableRecyclerViewWrapper(this.mRecyclerView);
        if (this.mAdapter == null) {
            this.mAdapter = new CommonFolderDialogListAdapter();
            this.mAdapter.setOnClickListener(this.mItemClickListener);
            this.mAdapter.setOnLongClickListener(this.mItemLongClickListener);
            this.mAdapter.setBindContext(this.mBindContext);
            this.mAdapter.setSelectId(this.mFolderId);
        }
        this.mRecyclerViewWrapper.setAdapter(this.mAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRecyclerViewWrapper);
        updateRecyclerViewGridMode();
    }

    protected void updateRecyclerViewGridMode() {
        this.mRvLayoutManager = new NoteGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected void updateList(List<FolderModel> list, int i) {
        if (this.mAdapter != null) {
            FolderModel folderModel = new FolderModel();
            folderModel.setId(-4097L);
            list.add(0, folderModel);
            this.mAdapter.setData(list);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected void updateActionModeButton2(ActionMode actionMode) {
        EditActionMode editActionMode = (EditActionMode) actionMode;
        if (UIUtils.isMiuiXSdkSupported()) {
            int i = DisplayUtils.isNightMode() ? R.drawable.miuix_appcompat_action_mode_title_button_deselect_all_dark : R.drawable.miuix_appcompat_action_mode_title_button_deselect_all_light;
            int i2 = DisplayUtils.isNightMode() ? R.drawable.miuix_appcompat_action_mode_title_button_select_all_dark : R.drawable.miuix_appcompat_action_mode_title_button_select_all_light;
            if (!isAllItemChecked()) {
                i = i2;
            }
            editActionMode.setButton(16908314, (CharSequence) null, i);
        } else {
            editActionMode.setButton(16908314, isAllItemChecked() ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
        }
        updateCheckStatus(actionMode);
        checkTalkBackAndHint(true, isAllItemChecked());
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected LongSparseLongArray getCheckedItems() {
        LongSparseLongArray checkedItems = this.mRecyclerViewWrapper.getCheckedItems();
        if (checkedItems.indexOfKey(-4097L) >= 0) {
            checkedItems.delete(-4097L);
        }
        if (checkedItems.indexOfKey(0L) >= 0) {
            checkedItems.delete(0L);
        }
        return checkedItems;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected boolean isAllItemChecked() {
        int size = getCheckedItems().size();
        int itemCount = this.mAdapter.getItemCount();
        int i = itemCount - 1;
        if (this.mAdapter.getItemId(i) == 0) {
            return size == itemCount + (-2);
        }
        return size == i;
    }

    private void updateCheckStatus(ActionMode actionMode) {
        int size = getCheckedItems().size();
        if (actionMode != null) {
            Resources resources = this.mRecyclerView.getResources();
            if (size == 0) {
                actionMode.setTitle(resources.getString(R.string.miuix_appcompat_select_item));
            } else {
                actionMode.setTitle(String.format(resources.getQuantityString(R.plurals.miuix_appcompat_items_selected, size), Integer.valueOf(size)));
            }
            actionMode.getMenu().setGroupEnabled(0, size != 0);
        }
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogFragment
    protected void changeActionMode(boolean z) {
        this.mIsActionMode = z;
        if (z) {
            this.createFolderLayout.setVisibility(8);
        } else {
            this.createFolderLayout.setVisibility(0);
        }
    }

    private void smallWindowAdapter() {
        View view = this.createFolderBtn;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment.3
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    if (rootWindowInsets == null) {
                        return windowInsetsCompat;
                    }
                    Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) CommonFolderDialogFragment.this.createFolderBtn.getLayoutParams();
                    marginLayoutParams.bottomMargin = insetsIgnoringVisibility.bottom;
                    CommonFolderDialogFragment.this.createFolderBtn.setLayoutParams(marginLayoutParams);
                    return windowInsetsCompat;
                }
            });
        }
        View view2 = this.rvContent;
        if (view2 != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view2, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.feature.folder.CommonFolderDialogFragment.4
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view3, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view3);
                    Activity activity = UIUtils.getActivity(CommonFolderDialogFragment.this.rvContent.getContext());
                    if (rootWindowInsets != null && activity != null) {
                        Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        if (UIUtils.isInMultiWindowMode(activity)) {
                            CommonFolderDialogFragment.this.rvContent.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                            return windowInsetsCompat;
                        }
                        CommonFolderDialogFragment.this.rvContent.setPadding(0, 0, 0, 0);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }
}
