package com.miui.pc.frame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.FolderCache;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.AsyncTaskWithProgressDialog;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import com.miui.pc.component.EditableNoteListWrapper;
import com.miui.pc.feature.notes.FolderListOptionMenu;
import com.miui.pc.feature.notes.NoteListOptionMenu;
import com.miui.pc.feature.notes.PcNoteBindContext;
import com.miui.pc.view.CreateMorePopupWindow;
import com.miui.pc.view.ExpandableRecyclerView;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.util.ResourceParser;
import java.util.ArrayList;
import java.util.HashSet;
import miuix.appcompat.app.ProgressDialog;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class LeftFragment extends DialogManagedFragment {
    protected static final int REQUEST_CODE_QUERY_FOLDER = 2;
    protected static final int REQUEST_CODE_QUERY_NOTE = 1;
    public static String mDefaultFolderName = "";
    private BindContext mBindContext;
    private View mCreateMoreIcon;
    private View mCreateMoreRegion;
    private View mCreateNoteRegion;
    private int mCurTabIndex;
    private Cursor mCurrentFolderNotes;
    private View mEmptyNoteView;
    private PcFolderListAdapter mFolderAdapter;
    private ExpandableRecyclerView mFolderExpandView;
    private FolderListOptionMenu mFolderListOptionMenu;
    private PcBaseNoteRecyclerView mFolderRv;
    private TextView mFolderTitle;
    private View mNoteListLane;
    private NoteListOptionMenu mNoteListOptionMenu;
    private NotesListAdapter mNotesListAdapter;
    private PcBaseNoteRecyclerView mNotesRv;
    private EditableNoteListWrapper mNotesRvWrapper;
    private BaseLeftLayout mRootView;
    private EditText mSearchInput;
    private ExpandableRecyclerView mTodoCategoryExpandView;
    private RecyclerView mTodoCategoryRv;
    private TodoCategoryAdapter mTodoTypeListAdapter;
    protected long mFolderId = -4097;
    private FolderLoaderCallbacks mFolderLoaderCallbacks = new FolderLoaderCallbacks();
    private NotesLoaderCallbacks mNotesLoaderCallbacks = new NotesLoaderCallbacks();
    private boolean isCreateNewNote = false;
    private long mNewSavedNoteId = -2;
    private int mTouchNoteItemPosFromRightClick = -1;
    private int mTouchFolderItemPosFromRightClick = -1;
    private long mNewFolderId = Long.MIN_VALUE;
    private boolean mNeedRefresh = false;
    private int mGridItemSpace = -1;
    private androidx.recyclerview.widget.RecyclerView.ItemDecoration mNoteItemDecoration = new androidx.recyclerview.widget.RecyclerView.ItemDecoration() { // from class: com.miui.pc.frame.LeftFragment.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.State state) {
            rect.set(LeftFragment.this.mGridItemSpace, LeftFragment.this.mGridItemSpace, LeftFragment.this.mGridItemSpace, LeftFragment.this.mGridItemSpace);
        }
    };
    private int mCurrentNotePosition = -1;
    private long mLastViewNoteId = -1;
    private long mCurrentNoteId = -1;
    private int mCurrentTodoIndex = 0;
    private String mCurrentSearchText = "";
    private boolean mIsInSearch = false;
    private TextWatcher mSearchInputWatch = new SearchInputWatch();
    private boolean mIsLockUI = false;
    private PcDialog mShowingDialog = null;
    private OnKeyDownAndShortCutListener mKeyListener = new OnKeyDownAndShortCutListener() { // from class: com.miui.pc.frame.LeftFragment.2
        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
            if (!keyEvent.hasModifiers(4096) || i != 34) {
                return false;
            }
            LeftFragment.this.mSearchInput.requestFocus();
            KeyBoardStateHelper.setCtrlPress(false);
            return true;
        }

        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 19) {
                LeftFragment.this.preNote(true);
                return true;
            }
            if (i == 20) {
                LeftFragment.this.nextNote(true);
                return true;
            }
            if (i != 113 && i != 114) {
                return false;
            }
            KeyBoardStateHelper.setCtrlPress(true);
            return false;
        }

        @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
        public boolean onKeyUp(int i, KeyEvent keyEvent) {
            if (i != 113 && i != 114) {
                return false;
            }
            KeyBoardStateHelper.setCtrlPress(false);
            return false;
        }
    };
    private View.OnClickListener mFolderItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.14
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int childAdapterPosition;
            if (LeftFragment.this.isUILocking() || (childAdapterPosition = LeftFragment.this.mFolderRv.getChildAdapterPosition(view)) == -1) {
                return;
            }
            LeftFragment.this.convertToNote();
            LeftFragment.this.mNeedRefresh = true;
            LeftFragment.this.clearLastFolderEmptyNotes();
            LeftFragment.this.setInSearchMode(false);
            LeftFragment.this.updateFolder(childAdapterPosition);
        }
    };
    private View.OnClickListener mCreateMenuClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.15
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (LeftFragment.this.isUILocking()) {
                return;
            }
            LeftFragment.this.convertToNote();
            int id = view.getId();
            if (id == R.id.create_folder_view) {
                LeftFragment.this.onAddFolder();
            } else if (id == R.id.create_mind_view) {
                LeftFragment.this.createMindNote();
            } else {
                if (id != R.id.create_note_view) {
                    return;
                }
                LeftFragment.this.createNote();
            }
        }
    };
    private View.OnClickListener mTodoCategoryListener = new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.16
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int childAdapterPosition;
            if (LeftFragment.this.isUILocking() || (childAdapterPosition = LeftFragment.this.mTodoCategoryRv.getChildAdapterPosition(view)) == -1 || !LeftFragment.this.isAdded()) {
                return;
            }
            LeftFragment.this.setInSearchMode(false);
            LeftFragment leftFragment = LeftFragment.this;
            leftFragment.convertToTodo(childAdapterPosition, leftFragment.mSearchInput.getText().toString());
            LeftFragment.this.onTodoCategorySelected(childAdapterPosition);
        }
    };

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(R.style.NoteTheme_Pc);
        PcFolderListAdapter pcFolderListAdapter = new PcFolderListAdapter();
        this.mFolderAdapter = pcFolderListAdapter;
        pcFolderListAdapter.setOnClickListener(this.mFolderItemClickListener);
        this.mFolderAdapter.setFolderCallBack(new PcFolderListAdapter.FolderOperationCallBack() { // from class: com.miui.pc.frame.LeftFragment.3
            @Override // com.miui.pc.frame.PcFolderListAdapter.FolderOperationCallBack
            public void changeFolder(long j, long j2) {
                LeftFragment.this.changeFolderToTarget(new long[]{j}, j2);
            }

            @Override // com.miui.pc.frame.PcFolderListAdapter.FolderOperationCallBack
            public boolean nameFolder(long j, String str, String str2) {
                boolean zRename = false;
                if (!TextUtils.isEmpty(str2) && !str.contentEquals(str2)) {
                    if (LeftFragment.this.mFolderAdapter.isFolderExist(str2)) {
                        FolderStore.rename(LeftFragment.this.getContext(), j, LeftFragment.this.getCorrectFolderName(str, str2));
                    } else {
                        FolderStore folderStore = FolderStore.INSTANCE;
                        zRename = FolderStore.rename(LeftFragment.this.getContext(), j, str2);
                    }
                    if (LeftFragment.this.mFolderId == j) {
                        LeftFragment.this.mFolderTitle.setText(str2);
                    }
                }
                if (LeftFragment.this.mFolderAdapter.getEditFolderId() == j) {
                    LeftFragment.this.mNewFolderId = Long.MIN_VALUE;
                    LeftFragment.this.mFolderAdapter.setEditableFolder(Long.MIN_VALUE);
                }
                LeftFragment.this.mFolderAdapter.notifyDataSetChanged();
                return zRename;
            }
        });
        this.mNotesListAdapter = new NotesListAdapter(false);
        PcNoteBindContext pcNoteBindContext = new PcNoteBindContext();
        this.mBindContext = pcNoteBindContext;
        this.mNotesListAdapter.setBindContext(pcNoteBindContext);
        this.mNotesListAdapter.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LeftFragment.this.mSearchInput.hasFocus()) {
                    LeftFragment.this.mSearchInput.clearFocus();
                }
                if (LeftFragment.this.mFolderRv.hasFocus()) {
                    LeftFragment.this.mFolderRv.clearFocus();
                }
                if (LeftFragment.this.mNotesRvWrapper.isInActionMode()) {
                    LeftFragment.this.mNotesRvWrapper.toggleItemChecked(view);
                } else {
                    if (KeyBoardStateHelper.isCtrlPress()) {
                        LeftFragment.this.mTouchNoteItemPosFromRightClick = -1;
                        LeftFragment.this.mNotesRvWrapper.setItemChecked(LeftFragment.this.mNotesRv.getChildAdapterPosition(view), true);
                        LeftFragment.this.mNotesRvWrapper.startActionMode(null);
                        return;
                    }
                    LeftFragment.this.openNote(view);
                }
            }
        });
        this.mTodoTypeListAdapter = new TodoCategoryAdapter();
        queryALL();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.pc_folder_fragment, viewGroup, false);
        BaseLeftLayout baseLeftLayout = (BaseLeftLayout) viewInflate;
        this.mRootView = baseLeftLayout;
        baseLeftLayout.setOnDragListener(new BaseDragListener() { // from class: com.miui.pc.frame.LeftFragment.5
            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View view, DragEvent dragEvent) {
                return true;
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View view, DragEvent dragEvent) {
                return true;
            }
        });
        this.mRootView.setKeyShortCutListener(this.mKeyListener);
        this.mRootView.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.frame.LeftFragment.6
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LeftFragment.this.finishNotesEditMode();
            }
        });
        viewInflate.findViewById(R.id.navi_group).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LeftFragment.this.mSearchInput.hasFocus()) {
                    LeftFragment.this.mSearchInput.clearFocus();
                }
                if (LeftFragment.this.mFolderRv.hasFocus()) {
                    LeftFragment.this.mFolderRv.clearFocus();
                }
                LeftFragment.this.finishNotesEditMode();
            }
        });
        ExpandableRecyclerView expandableRecyclerView = (ExpandableRecyclerView) viewInflate.findViewById(R.id.pc_folder_list);
        this.mFolderExpandView = expandableRecyclerView;
        expandableRecyclerView.setHeaderTitle(getContext().getString(R.string.pc_module_notes_name));
        this.mFolderExpandView.setIcon(R.drawable.pc_icon_note);
        PcBaseNoteRecyclerView recyclerView = this.mFolderExpandView.getRecyclerView();
        this.mFolderRv = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mFolderRv.setRecycledViewPool(new androidx.recyclerview.widget.RecyclerView.RecycledViewPool());
        this.mFolderRv.setOnRightClickListener(new PcBaseNoteRecyclerView.OnRightClickListener() { // from class: com.miui.pc.frame.LeftFragment.9
            @Override // com.miui.pc.frame.PcBaseNoteRecyclerView.OnRightClickListener
            public void onRightClick(float f, float f2, int i) {
                if (LeftFragment.this.mShowingDialog != null) {
                    LeftFragment.this.mShowingDialog.dismiss();
                    LeftFragment.this.mShowingDialog = null;
                }
                if (LeftFragment.this.mFolderListOptionMenu == null) {
                    LeftFragment.this.mFolderListOptionMenu = new FolderListOptionMenu(LeftFragment.this.getContext());
                    LeftFragment.this.mFolderListOptionMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.frame.LeftFragment.9.1
                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onShow() {
                        }

                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onDismiss() {
                            LeftFragment.this.mTouchFolderItemPosFromRightClick = -1;
                        }

                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onItemSelected(View view, int i2) {
                            int id = view.getId();
                            if (id == R.id.create_folder) {
                                LeftFragment.this.onAddFolder();
                            } else if (id == R.id.delete_folder) {
                                LeftFragment.this.showDeleteFolderDialog();
                            } else {
                                if (id != R.id.rename_folder) {
                                    return;
                                }
                                LeftFragment.this.renameFolder();
                            }
                        }
                    });
                }
                int[] iArr = {(int) f, (int) f2};
                if (LeftFragment.this.mNoteListOptionMenu != null) {
                    LeftFragment.this.mNoteListOptionMenu.dismiss();
                }
                if (i >= 0) {
                    LeftFragment.this.mTouchFolderItemPosFromRightClick = i;
                    LeftFragment.this.mFolderListOptionMenu.updateFolderId(LeftFragment.this.mFolderAdapter.getFolderCache(i).getFolder().getId());
                    LeftFragment.this.mFolderListOptionMenu.show(LeftFragment.this.mFolderRv, iArr);
                }
            }
        });
        View viewFindViewById = viewInflate.findViewById(R.id.note_list_lane);
        this.mNoteListLane = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LeftFragment.this.finishNotesEditMode();
            }
        });
        PcBaseNoteRecyclerView pcBaseNoteRecyclerView = (PcBaseNoteRecyclerView) viewInflate.findViewById(R.id.pc_note_list);
        this.mNotesRv = pcBaseNoteRecyclerView;
        pcBaseNoteRecyclerView.setLayoutManager(new GridLayoutManager((Context) NoteApp.getInstance(), 1, 1, false));
        this.mNotesRv.setRecycledViewPool(new androidx.recyclerview.widget.RecyclerView.RecycledViewPool());
        this.mNotesRv.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LeftFragment.this.finishNotesEditMode();
            }
        });
        EditableNoteListWrapper<?> editableNoteListWrapper = new EditableNoteListWrapper<>(this.mNotesRv);
        this.mNotesRvWrapper = editableNoteListWrapper;
        ((PcNoteBindContext) this.mBindContext).setEditableNoteListWrapper(editableNoteListWrapper);
        this.mNotesRvWrapper.setAdapter(this.mNotesListAdapter);
        this.mNotesRv.addItemDecoration(this.mNoteItemDecoration);
        this.mNotesRv.setOnRightClickListener(new PcBaseNoteRecyclerView.OnRightClickListener() { // from class: com.miui.pc.frame.LeftFragment.12
            @Override // com.miui.pc.frame.PcBaseNoteRecyclerView.OnRightClickListener
            public void onRightClick(float f, float f2, int i) {
                if (LeftFragment.this.mShowingDialog != null) {
                    LeftFragment.this.mShowingDialog.dismiss();
                    LeftFragment.this.mShowingDialog = null;
                }
                if (LeftFragment.this.mNoteListOptionMenu == null) {
                    LeftFragment.this.mNoteListOptionMenu = new NoteListOptionMenu(LeftFragment.this.getContext(), LeftFragment.this.mNotesRvWrapper);
                    LeftFragment.this.mNoteListOptionMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.frame.LeftFragment.12.1
                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onShow() {
                        }

                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onDismiss() {
                            LeftFragment.this.mTouchNoteItemPosFromRightClick = -1;
                            if (LeftFragment.this.mNotesRvWrapper.isInActionMode()) {
                                return;
                            }
                            LeftFragment.this.mNotesRvWrapper.clearChoices();
                        }

                        @Override // com.miui.common.view.IPopMenu.OnMenuListener
                        public void onItemSelected(View view, int i2) {
                            switch (view.getId()) {
                                case R.id.delete /* 2131362396 */:
                                    if (LeftFragment.this.mFolderId == -3) {
                                        LeftFragment.this.deleteCheckedItems(true);
                                    } else {
                                        LeftFragment.this.deleteCheckedItems(false);
                                    }
                                    break;
                                case R.id.move_to_folder /* 2131362991 */:
                                    LeftFragment.this.showMoveToFolderDialog(LeftFragment.this.mNotesRvWrapper.getCheckedItemIds());
                                    break;
                                case R.id.multi_choice /* 2131363016 */:
                                    LeftFragment.this.mNotesRvWrapper.startActionMode(null);
                                    break;
                                case R.id.new_note /* 2131363049 */:
                                    LeftFragment.this.createNote();
                                    break;
                                case R.id.restore /* 2131363295 */:
                                    LeftFragment.this.restoreCheckedItems();
                                    break;
                                case R.id.stick /* 2131363535 */:
                                    LeftFragment.this.stickCheckedItems(!LeftFragment.this.mNotesRvWrapper.isAllCheckedItemSticking());
                                    break;
                            }
                        }
                    });
                } else {
                    LeftFragment.this.mNotesRvWrapper.setItemChecked(LeftFragment.this.mTouchNoteItemPosFromRightClick, false);
                }
                if (LeftFragment.this.mFolderListOptionMenu != null) {
                    LeftFragment.this.mFolderListOptionMenu.dismiss();
                }
                int[] iArr = {(int) f, (int) f2};
                LeftFragment.this.mTouchNoteItemPosFromRightClick = i;
                if (i >= 0) {
                    LeftFragment.this.mNotesRvWrapper.setItemChecked(LeftFragment.this.mTouchNoteItemPosFromRightClick, true);
                    LeftFragment.this.mNoteListOptionMenu.updateFolderId(LeftFragment.this.mFolderId);
                    LeftFragment.this.mNoteListOptionMenu.show(LeftFragment.this.mNotesRv, iArr);
                }
            }
        });
        ExpandableRecyclerView expandableRecyclerView2 = (ExpandableRecyclerView) viewInflate.findViewById(R.id.pc_todo_type_list);
        this.mTodoCategoryExpandView = expandableRecyclerView2;
        expandableRecyclerView2.setHeaderTitle(getContext().getString(R.string.pc_module_todo_name));
        this.mTodoCategoryExpandView.setIcon(R.drawable.pc_icon_todo);
        PcBaseNoteRecyclerView recyclerView2 = this.mTodoCategoryExpandView.getRecyclerView();
        this.mTodoCategoryRv = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mTodoCategoryRv.setRecycledViewPool(new androidx.recyclerview.widget.RecyclerView.RecycledViewPool());
        this.mTodoCategoryRv.setAdapter(this.mTodoTypeListAdapter);
        this.mTodoCategoryExpandView.setExpand(true);
        this.mTodoTypeListAdapter.setOnClickListener(this.mTodoCategoryListener);
        View viewFindViewById2 = viewInflate.findViewById(R.id.create_note_region);
        this.mCreateNoteRegion = viewFindViewById2;
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$0(view);
            }
        });
        View viewFindViewById3 = viewInflate.findViewById(R.id.create_more_region);
        this.mCreateMoreRegion = viewFindViewById3;
        viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$1(view);
            }
        });
        this.mCreateMoreIcon = viewInflate.findViewById(R.id.ic_create_more);
        this.mFolderTitle = (TextView) viewInflate.findViewById(R.id.pc_folder_title);
        EditText editText = (EditText) viewInflate.findViewById(R.id.search_input);
        this.mSearchInput = editText;
        editText.addTextChangedListener(this.mSearchInputWatch);
        View viewFindViewById4 = viewInflate.findViewById(R.id.no_note_hint);
        this.mEmptyNoteView = viewFindViewById4;
        viewFindViewById4.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.LeftFragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LeftFragment.this.mSearchInput != null && LeftFragment.this.mSearchInput.hasFocus()) {
                    LeftFragment.this.mSearchInput.clearFocus();
                }
                if (LeftFragment.this.mFolderRv == null || !LeftFragment.this.mFolderRv.hasFocus()) {
                    return;
                }
                LeftFragment.this.mFolderRv.clearFocus();
            }
        });
        this.mFolderRv.setAdapter(this.mFolderAdapter);
        this.mFolderExpandView.setExpand(true);
        this.mFolderId = -4097L;
        onFolderSelected(-4097L);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$0(View view) {
        convertToNote();
        createNote();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$1(View view) {
        moreNote();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renameFolder() {
        PcFolderListAdapter pcFolderListAdapter = this.mFolderAdapter;
        pcFolderListAdapter.setEditableFolder(pcFolderListAdapter.getItemId(this.mTouchFolderItemPosFromRightClick));
        this.mFolderAdapter.notifyDataSetChanged();
    }

    public void setCurrentTab(int i) {
        this.mCurTabIndex = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCorrectFolderName(String str, String str2) {
        String str3 = str2;
        int i = 1;
        while (this.mFolderAdapter.isFolderExist(str3)) {
            if (str.equals(str3)) {
                return str;
            }
            str3 = str2 + i;
            i++;
        }
        return str3;
    }

    public void setNotesListLaneVisible(int i) {
        if (i == 8) {
            finishNotesEditMode();
        }
        this.mNoteListLane.setVisibility(i);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        if (isUILocking() || (onKeyDownAndShortCutListener = this.mKeyListener) == null) {
            return false;
        }
        return onKeyDownAndShortCutListener.onKeyDown(i, keyEvent);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        if (isUILocking() || (onKeyDownAndShortCutListener = this.mKeyListener) == null) {
            return false;
        }
        return onKeyDownAndShortCutListener.onKeyUp(i, keyEvent);
    }

    public boolean onKeyShortCut(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        if (isUILocking() || (onKeyDownAndShortCutListener = this.mKeyListener) == null) {
            return false;
        }
        return onKeyDownAndShortCutListener.onKeyShortcut(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLastFolderEmptyNotes() {
        Cursor cursor = this.mCurrentFolderNotes;
        if (cursor == null || cursor.getCount() <= 0) {
            return;
        }
        this.mCurrentFolderNotes.moveToFirst();
        ArrayList arrayList = new ArrayList();
        do {
            long j = this.mCurrentFolderNotes.getLong(0);
            if (isNoteEmpty(j, this.mCurrentFolderNotes)) {
                arrayList.add(Long.valueOf(j));
            }
            if (!this.mCurrentFolderNotes.isLast()) {
                this.mCurrentFolderNotes.moveToNext();
            }
        } while (!this.mCurrentFolderNotes.isLast());
        if (arrayList.size() > 0) {
            long[] jArr = new long[arrayList.size()];
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                jArr[i] = ((Long) arrayList.get(i)).longValue();
            }
            new NoteDeleteTask(this, jArr, false).execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNoteEmpty(long j, Cursor cursor) {
        NoteCache note = CacheManager.getDefault().getNote(j);
        NoteModel noteModel = NoteStore.parse(cursor);
        if (note == null) {
            note = CacheManager.getDefault().getNote(noteModel);
        } else {
            note.update(noteModel);
        }
        return TextUtils.isEmpty(note.getNote().getTitle()) && TextUtils.isEmpty(note.getNote().getSnippet());
    }

    public void lockUI(boolean z) {
        this.mIsLockUI = z;
    }

    public boolean isUILocking() {
        return this.mIsLockUI;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFolder(int i) {
        long folderId = this.mFolderAdapter.getItem(i).getFolderId();
        if (folderId != this.mFolderId) {
            finishNotesEditMode();
        }
        this.mFolderTitle.setText(this.mFolderAdapter.getItem(i).getFolderName());
        this.mFolderId = folderId;
        onFolderSelected(folderId);
        queryNotes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeFolderToTarget(long[] jArr, long j) {
        if (j == this.mFolderId || j == NoteConfig.getDefaultFolderId()) {
            return;
        }
        NoteStore.move(getContext(), jArr, j);
        queryNotes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertToNote() {
        ((PcMainActivity) getActivity()).convertNote();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        ((PcMainActivity) getActivity()).showNote(noteLoadDataEntity, z);
    }

    private boolean isNoteDateEmpty() {
        return ((PcMainActivity) getActivity()).isNoteDateEmpty();
    }

    private boolean isMindDateEmpty() {
        return ((PcMainActivity) getActivity()).isMindDateEmpty();
    }

    private void convertToTodo(int i) {
        ((PcMainActivity) getActivity()).convertTodo(i, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertToTodo(int i, String str) {
        setLastViewNoteId(-1L);
        ((PcMainActivity) getActivity()).convertTodo(i, str);
    }

    public void setLastViewNoteId(long j) {
        this.mLastViewNoteId = j;
    }

    public long getLaseViewNoteId() {
        return this.mLastViewNoteId;
    }

    public String getSearchText() {
        return this.mCurrentSearchText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createNote() {
        if (!isAdded() || isNoteDateEmpty()) {
            return;
        }
        finishNotesEditMode();
        this.mCurrentNoteId = -2L;
        this.mCurrentNotePosition = 0;
        this.mNotesListAdapter.setSelectedNodeId(-2L);
        if (!FolderModel.isCustomFolder(this.mFolderId)) {
            this.mFolderId = 0L;
            onFolderSelected(0L);
        }
        this.isCreateNewNote = true;
        showNote(new NoteLoadDataEntity(-2L, this.mFolderId, "", 0, false), false);
    }

    public void createMindNote() {
        TextView textView;
        if (!isAdded() || isMindDateEmpty()) {
            return;
        }
        finishNotesEditMode();
        this.mCurrentNoteId = -2L;
        boolean z = false;
        this.mCurrentNotePosition = 0;
        this.mNotesListAdapter.setSelectedNodeId(-2L);
        if (!FolderModel.isCustomFolder(this.mFolderId)) {
            this.mFolderId = 0L;
            onFolderSelected(0L);
            queryNotes();
            z = true;
        }
        this.isCreateNewNote = true;
        boolean z2 = z;
        final NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(-2L, this.mFolderId, "", 1, System.currentTimeMillis(), 0, 0, "", false, null);
        if (z2 && (textView = this.mFolderTitle) != null) {
            textView.removeCallbacks(null);
            this.mFolderTitle.postDelayed(new Runnable() { // from class: com.miui.pc.frame.LeftFragment.17
                @Override // java.lang.Runnable
                public void run() {
                    LeftFragment.this.showNote(noteLoadDataEntity, true);
                }
            }, 300L);
        } else {
            showNote(noteLoadDataEntity, true);
        }
    }

    public void createMindNoteFromNormalNote(Bundle bundle) {
        if (isAdded()) {
            finishNotesEditMode();
            if (!FolderModel.isCustomFolder(this.mFolderId)) {
                this.mFolderId = 0L;
                onFolderSelected(0L);
            }
            this.isCreateNewNote = true;
            String string = bundle.getString("note_title");
            int i = bundle.getInt("mind_content_type");
            int i2 = bundle.getInt("widget_id");
            int i3 = bundle.getInt("widget_type");
            MindEntity mindEntity = new MindEntity(i == 0, TextUtils.isEmpty(string) ? "" : string, "", "");
            WorkingNote workingNoteCreateEmptyMindNote = WorkingNote.createEmptyMindNote(this.mFolderId, i2, i3, ResourceParser.getDefaultBgId(getContext()));
            workingNoteCreateEmptyMindNote.setWorkingTitle(TextUtils.isEmpty(string) ? "" : string);
            workingNoteCreateEmptyMindNote.setWorkingText("");
            workingNoteCreateEmptyMindNote.setWorkingMindContent(mindEntity.toNoteContent());
            if (workingNoteCreateEmptyMindNote.createNoteByPad(true)) {
                long noteId = workingNoteCreateEmptyMindNote.getNoteId();
                this.mCurrentNoteId = noteId;
                this.mNotesListAdapter.setSelectedNodeId(noteId);
                showNote(new NoteLoadDataEntity(this.mCurrentNoteId, this.mFolderId, this.mBindContext.getSearchToken(), 1, workingNoteCreateEmptyMindNote.getModifiedDate(), i, i, string, false, null), true);
            }
        }
    }

    public void onNewNoteSaved(long j) {
        this.mNewSavedNoteId = j;
    }

    private void moreNote() {
        CreateMorePopupWindow createMorePopupWindow = new CreateMorePopupWindow(getActivity(), this.mCreateMenuClickListener);
        createMorePopupWindow.getContentView().measure(makeDropDownMeasureSpec(createMorePopupWindow.getWidth()), makeDropDownMeasureSpec(createMorePopupWindow.getHeight()));
        createMorePopupWindow.showPopupWindow(this.mCreateMoreIcon, -(createMorePopupWindow.getContentView().getMeasuredWidth() - 44), 0);
    }

    private static int makeDropDownMeasureSpec(int i) {
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), i == -2 ? 0 : 1073741824);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openNote(View view) {
        int childAdapterPosition = this.mNotesRv.getChildAdapterPosition(view);
        if (childAdapterPosition != -1 && isAdded()) {
            openNoteByPosition(childAdapterPosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nextNote(boolean z) {
        if (isAdded() && openNoteByPosition(this.mCurrentNotePosition + 1) && z) {
            this.mNotesRv.smoothScrollToPosition(this.mCurrentNotePosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void preNote(boolean z) {
        if (isAdded() && openNoteByPosition(this.mCurrentNotePosition - 1) && z) {
            this.mNotesRv.smoothScrollToPosition(this.mCurrentNotePosition);
        }
    }

    private boolean openNoteByPosition(int i) {
        int i2;
        int i3;
        if (i >= 0 && i < this.mNotesListAdapter.getItemCount()) {
            long itemId = this.mNotesListAdapter.getItemId(i);
            String itemNoteType = this.mNotesListAdapter.getItemNoteType(i);
            this.mCurrentNoteId = itemId;
            this.mCurrentNotePosition = i;
            this.mNotesListAdapter.setSelectedNodeId(itemId);
            ItemCache item = this.mNotesListAdapter.getItem(i);
            if (item != null && item.getCacheType() == 0) {
                NoteCache noteCache = (NoteCache) item.getCacheObject();
                if (NoteModel.NoteType.TYPE_MIND.equals(itemNoteType)) {
                    String mindContent = noteCache.getNote().getMindContent();
                    if (TextUtils.isEmpty(mindContent)) {
                        i2 = 0;
                        i3 = 0;
                    } else {
                        i2 = !((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).isMap ? 1 : 0;
                        i3 = 1;
                    }
                    showNote(new NoteLoadDataEntity(noteCache.getNote().getId(), noteCache.getNote().getFolderId(), this.mBindContext.getSearchToken(), 1, noteCache.getNote().getUpdatedAt(), i2, i3, noteCache.getNote().getTitle(), false, null), true);
                    return true;
                }
                if ("handwrite".equals(itemNoteType)) {
                    showNote(new NoteLoadDataEntity(noteCache.getNote().getId(), noteCache.getNote().getFolderId(), this.mBindContext.getSearchToken(), 2, noteCache.getNote().getUpdatedAt(), false, null), true);
                    return true;
                }
                if ("multi_handwrite".equals(itemNoteType)) {
                    showNote(new NoteLoadDataEntity(noteCache.getNote().getId(), noteCache.getNote().getFolderId(), this.mBindContext.getSearchToken(), 3, noteCache.getNote().getUpdatedAt(), false, null), true);
                    return true;
                }
            }
            showNote(new NoteLoadDataEntity(itemId, this.mFolderId, this.mBindContext.getSearchToken(), 0, false), false);
            return true;
        }
        Logger.INSTANCE.d("LeftFragment", "openNoteByPosition error position = " + i + " out of range");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFolderDeleted() {
        this.mFolderId = 0L;
        onFolderSelected(0L);
        this.mFolderTitle.setText(NoteApp.getInstance().getString(R.string.pc_all_folder_name));
        queryNotes();
    }

    private void queryALL() {
        FolderStore.INSTANCE.query(getLoaderManager(), 1, 2, PreferenceUtils.getNoteSortWay(NoteApp.getInstance(), 1) == 0 ? "stick_date DESC, created_date DESC,modified_date DESC" : "stick_date DESC, modified_date DESC", this.mFolderLoaderCallbacks);
        NoteStore.query(getLoaderManager(), this.mFolderId, this.mBindContext, 1, this.mNotesLoaderCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryNotes() {
        NoteStore.query(getLoaderManager(), this.mFolderId, this.mBindContext, 1, this.mNotesLoaderCallbacks);
    }

    protected class FolderLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
        }

        protected FolderLoaderCallbacks() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (loader.getId() == 2) {
                LeftFragment.this.refreshFolderInfo(cursor);
                LeftFragment.this.mFolderAdapter.setCursor(cursor);
                if (LeftFragment.this.mNewFolderId != Long.MIN_VALUE) {
                    LeftFragment.this.mFolderRv.scrollToPosition(LeftFragment.this.mFolderAdapter.getPosByFolderId(LeftFragment.this.mNewFolderId));
                    LeftFragment.this.mFolderAdapter.setEditableFolder(LeftFragment.this.mNewFolderId);
                }
                LeftFragment.this.mFolderExpandView.refreshRecyclerViewHeight();
                if ((LeftFragment.this.mFolderId == -2 || LeftFragment.this.mFolderId == -5) && LeftFragment.this.mFolderAdapter.getPosByFolderId(LeftFragment.this.mFolderId) == -1) {
                    LeftFragment.this.mFolderId = -4097L;
                }
                LeftFragment.this.mFolderAdapter.notifyDataSetChanged();
                if (LeftFragment.this.mCurTabIndex == 0) {
                    if (LeftFragment.this.mFolderId == -4097) {
                        LeftFragment.this.updateFolder(0);
                    }
                    if (LeftFragment.this.mFolderId == 0) {
                        LeftFragment.this.updateFolder(1);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshFolderInfo(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        cursor.moveToPosition(-1);
        String string = NoteApp.getInstance().getResources().getString(R.string.default_folder_name);
        HashSet hashSet = new HashSet();
        while (cursor.moveToNext()) {
            FolderStore folderStore = FolderStore.INSTANCE;
            String subject = FolderStore.valueOf(cursor).getSubject();
            int length = string.length();
            if (subject.length() >= length && subject.subSequence(0, length).equals(string)) {
                if (subject.length() == string.length()) {
                    hashSet.add(1);
                } else {
                    try {
                        hashSet.add(Integer.valueOf(Integer.parseInt(subject.substring(length))));
                    } catch (NumberFormatException unused) {
                    }
                }
            }
        }
        boolean z = true;
        int i = 1;
        while (z) {
            if (hashSet.contains(Integer.valueOf(i))) {
                i++;
            } else {
                z = false;
            }
        }
        if (i == 1) {
            mDefaultFolderName = string;
        } else {
            mDefaultFolderName = string + i;
        }
    }

    protected class NotesLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
        }

        protected NotesLoaderCallbacks() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int i;
            long j;
            long j2;
            int i2;
            long j3;
            NoteLoadDataEntity noteLoadDataEntity;
            NoteLoadDataEntity noteLoadDataEntity2;
            int i3;
            int i4;
            if (loader.getId() == 1) {
                if (!TextUtils.isEmpty(LeftFragment.this.mBindContext.getSearchToken())) {
                    LeftFragment.this.mFolderTitle.setText(LeftFragment.this.getResources().getQuantityString(R.plurals.pc_notes_search_result, cursor.getCount(), Integer.valueOf(cursor.getCount())));
                } else {
                    FolderItem itemByFolderId = LeftFragment.this.mFolderAdapter.getItemByFolderId(LeftFragment.this.mFolderId);
                    if (itemByFolderId != null) {
                        LeftFragment.this.mFolderTitle.setText(itemByFolderId.getFolderName());
                    }
                }
                if (cursor == null || cursor.getCount() == 0) {
                    Logger.INSTANCE.d("NotesLoaderCallbacks", "loader callback1");
                    LeftFragment.this.mEmptyNoteView.setVisibility(0);
                    LeftFragment.this.mCurrentNotePosition = -1;
                    if (LeftFragment.this.isAdded()) {
                        LeftFragment.this.showNote(new NoteLoadDataEntity(-1L, LeftFragment.this.mFolderId, LeftFragment.this.mBindContext.getSearchToken(), 0, false), true);
                    }
                } else {
                    LeftFragment.this.mEmptyNoteView.setVisibility(8);
                    cursor.moveToFirst();
                    if (LeftFragment.this.isCreateNewNote) {
                        Logger.INSTANCE.d("NotesLoaderCallbacks", "REQUEST_CODE_CREATE_NOTE");
                        while (cursor.getLong(11) > 0 && !cursor.isLast()) {
                            cursor.moveToNext();
                        }
                    }
                    long j4 = cursor.getLong(0);
                    Logger.INSTANCE.d("NotesLoaderCallbacks", "cursor move to first  noteId = " + j4);
                    long j5 = cursor.getLong(1);
                    long j6 = cursor.getLong(9);
                    int i5 = cursor.getInt(6);
                    int i6 = cursor.getInt(7);
                    String string = cursor.getString(18);
                    if (NoteModel.NoteType.TYPE_MIND.equals(string)) {
                        i = 1;
                    } else if ("handwrite".equals(string)) {
                        i = 2;
                    } else {
                        i = "multi_handwrite".equals(string) ? 3 : 0;
                    }
                    String string2 = cursor.getString(19);
                    int i7 = (TextUtils.isEmpty(string2) || !string2.startsWith(MindDataHelper.getMindDataTag()) || ((MindEntity) new Gson().fromJson(string2.substring(15), MindEntity.class)).isMap) ? 0 : 1;
                    if (LeftFragment.this.isCreateNewNote && LeftFragment.isNoteEmpty(j4, cursor)) {
                        LeftFragment.this.isCreateNewNote = false;
                        Logger.INSTANCE.d("NotesLoaderCallbacks", "mNewSavedNoteId = " + LeftFragment.this.mNewSavedNoteId);
                        LeftFragment leftFragment = LeftFragment.this;
                        leftFragment.mCurrentNoteId = leftFragment.mNewSavedNoteId;
                        j = -2;
                        LeftFragment.this.mNewSavedNoteId = -2L;
                    } else {
                        j = -2;
                    }
                    if (j4 != LeftFragment.this.mCurrentNoteId && LeftFragment.this.mCurrentNoteId != j) {
                        while (true) {
                            if (cursor.isLast()) {
                                j2 = j5;
                                i2 = i;
                                j3 = j4;
                                break;
                            }
                            cursor.moveToNext();
                            long j7 = cursor.getLong(0);
                            Logger.INSTANCE.d("NotesLoaderCallbacks", "cursor move to next  queryId = " + j7);
                            if (j7 == LeftFragment.this.mCurrentNoteId) {
                                long j8 = LeftFragment.this.mCurrentNoteId;
                                long j9 = cursor.getLong(1);
                                String string3 = cursor.getString(18);
                                if (NoteModel.NoteType.TYPE_MIND.equals(string3)) {
                                    i3 = 19;
                                    i4 = 1;
                                } else if ("handwrite".equals(string3)) {
                                    i3 = 19;
                                    i4 = 2;
                                } else {
                                    i3 = 19;
                                    i4 = "multi_handwrite".equals(string3) ? 3 : 0;
                                }
                                String string4 = cursor.getString(i3);
                                if (!TextUtils.isEmpty(string4) && string4.startsWith(MindDataHelper.getMindDataTag())) {
                                    j3 = j8;
                                    j2 = j9;
                                    i2 = i4;
                                    string2 = string4;
                                    i7 = !((MindEntity) new Gson().fromJson(string4.substring(15), MindEntity.class)).isMap ? 1 : 0;
                                    break;
                                }
                                j3 = j8;
                                j2 = j9;
                                i2 = i4;
                                string2 = string4;
                                i7 = 0;
                                break;
                            }
                        }
                    } else {
                        j2 = j5;
                        i2 = i;
                        j3 = j4;
                        break;
                    }
                    Logger.INSTANCE.d("NotesLoaderCallbacks", "noteId = " + cursor.getLong(0) + " mCurrentNoteId = " + LeftFragment.this.mCurrentNoteId + "  noteId1 = " + j3);
                    if (LeftFragment.this.isAdded()) {
                        long j10 = LeftFragment.this.mCurrentNoteId;
                        LeftFragment.this.mCurrentNoteId = j3;
                        boolean z = LeftFragment.this.mNotesListAdapter.getCurrentNoteId() != LeftFragment.this.mCurrentNoteId || LeftFragment.this.mIsInSearch || j10 < 0 || LeftFragment.this.mNeedRefresh;
                        LeftFragment.this.mNeedRefresh = false;
                        LeftFragment.this.mCurrentNotePosition = 0;
                        Logger.INSTANCE.d("NotesLoaderCallbacks", "loader cursor setSelectedNodeId" + LeftFragment.this.mCurrentNoteId);
                        LeftFragment.this.mNotesListAdapter.setSelectedNodeId(LeftFragment.this.mCurrentNoteId);
                        Logger.INSTANCE.d("NotesLoaderCallbacks", "loader callback0  " + i2 + "  mindContentType " + i7);
                        if (i2 == 0) {
                            noteLoadDataEntity2 = new NoteLoadDataEntity(j3, j2, LeftFragment.this.mBindContext.getSearchToken(), i2, false);
                        } else {
                            if (i2 != 1) {
                                long j11 = j2;
                                if (i2 == 2) {
                                    noteLoadDataEntity = new NoteLoadDataEntity(j3, j11, LeftFragment.this.mBindContext.getSearchToken(), i2, j6, i5, i6, false, "", 0L, (String) null);
                                } else {
                                    noteLoadDataEntity = new NoteLoadDataEntity(j3, j11, LeftFragment.this.mBindContext.getSearchToken(), i2, j6, i5, i6, false, "", 0L, (String) null);
                                }
                            } else if (!TextUtils.isEmpty(string2)) {
                                noteLoadDataEntity = new NoteLoadDataEntity(j3, j2, LeftFragment.this.mBindContext.getSearchToken(), i2, j6, i7, 1, "", false, null);
                            } else {
                                noteLoadDataEntity = new NoteLoadDataEntity(j3, j2, LeftFragment.this.mBindContext.getSearchToken(), i2, j6, i7, 0, "", false, null);
                            }
                            noteLoadDataEntity2 = noteLoadDataEntity;
                        }
                        if (LeftFragment.this.mSearchInput.hasFocus() || z || (LeftFragment.this.getActivity() != null && ((PcMainActivity) LeftFragment.this.getActivity()).isBlankViewShow())) {
                            LeftFragment.this.showNote(noteLoadDataEntity2, false);
                        }
                    }
                }
                LeftFragment.this.mCurrentFolderNotes = cursor;
                LeftFragment.this.mNotesListAdapter.setCursor(cursor);
                LeftFragment.this.mNotesListAdapter.notifyDataSetChanged();
            }
        }
    }

    protected void onAddFolder() {
        finishNotesEditMode();
        this.mNewFolderId = FolderOperationUtils.INSTANCE.addFolder(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteFolderDialog() {
        final FolderCache folderCache = this.mFolderAdapter.getFolderCache(this.mTouchFolderItemPosFromRightClick);
        PcDeleteFolderDialog pcDeleteFolderDialog = new PcDeleteFolderDialog(getContext(), new PcDeleteFolderDialog.ActionListener() { // from class: com.miui.pc.frame.LeftFragment.18
            @Override // com.miui.pc.frame.PcDeleteFolderDialog.ActionListener
            public void onCancel() {
            }

            @Override // com.miui.pc.frame.PcDeleteFolderDialog.ActionListener
            public void onConfirm() {
                new FolderDeleteTask(LeftFragment.this, folderCache.getFolder().getId()).execute(new Void[0]);
            }
        });
        pcDeleteFolderDialog.setCanceledOnTouchOutside(false);
        pcDeleteFolderDialog.show(this.mRootView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMoveToFolderDialog(final long[] jArr) {
        PcChangeFolderDialog pcChangeFolderDialog = new PcChangeFolderDialog(getContext(), new PcChangeFolderDialog.ActionListener() { // from class: com.miui.pc.frame.LeftFragment.19
            @Override // com.miui.pc.frame.PcChangeFolderDialog.ActionListener
            public void onCancel() {
            }

            @Override // com.miui.pc.frame.PcChangeFolderDialog.ActionListener
            public void onConfirm(long j) {
                LeftFragment.this.changeFolderToTarget(jArr, j);
            }
        }, this.mFolderAdapter.getItems(), this.mFolderAdapter.getSelectedFolderId());
        pcChangeFolderDialog.setCanceledOnTouchOutside(false);
        pcChangeFolderDialog.show(this.mRootView);
    }

    private static class FolderDeleteTask extends AsyncTaskWithProgressDialog<LeftFragment, Void, Void, Void> {
        private long iId;

        public FolderDeleteTask(LeftFragment leftFragment, long j) {
            super(leftFragment);
            this.iId = j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public Void doBackgroundWork(Context context, Void... voidArr) {
            Logger.INSTANCE.d(getClass().getSimpleName(), "doBackgroundWork/delete for " + this.iId);
            FolderStore folderStore = FolderStore.INSTANCE;
            FolderStore.delete(context, this.iId);
            return null;
        }

        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        protected Dialog onCreateDialog(Activity activity) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(activity.getString(R.string.dlg_delete_message));
            progressDialog.setCancelable(false);
            return progressDialog;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.notes.tool.util.AsyncTaskWithProgressDialog
        public void onPostWork(Activity activity, Void r2) {
            super.onPostWork(activity, r2);
            LeftFragment fragment = getFragment();
            if (fragment != null) {
                fragment.onFolderDeleted();
            }
            AudioUtils.playDeleteRingtone();
        }
    }

    private void onFolderSelected(long j) {
        this.mFolderAdapter.setSelectedFolderId(j);
        this.mTodoTypeListAdapter.clearSelectedType();
        this.mSearchInput.setHint(getString(R.string.pc_action_search_notes_hint));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTodoCategorySelected(int i) {
        this.mTodoTypeListAdapter.setSelectedType(i);
        this.mFolderAdapter.clearSelectedType();
        this.mSearchInput.setHint(getString(R.string.pc_action_search_todo_hint));
    }

    private class SearchInputWatch implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        private SearchInputWatch() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String string = editable.toString();
            if (LeftFragment.this.mFolderAdapter.isFolderSelected()) {
                LeftFragment.this.mBindContext.setSearchToken(string);
                LeftFragment.this.mCurrentSearchText = string;
                LeftFragment.this.mFolderId = NoteConfig.getDefaultFolderId();
                LeftFragment.this.mFolderAdapter.setSelectedFolderId(LeftFragment.this.mFolderId);
                LeftFragment.this.queryNotes();
            } else {
                LeftFragment leftFragment = LeftFragment.this;
                leftFragment.convertToTodo(leftFragment.mTodoTypeListAdapter.getSelectedType(), string);
            }
            if (TextUtils.isEmpty(string)) {
                LeftFragment.this.setInSearchMode(false);
            } else {
                LeftFragment.this.setInSearchMode(true);
            }
        }
    }

    public OnKeyDownAndShortCutListener getKeyListener() {
        return this.mKeyListener;
    }

    public void clearFocus() {
        this.mSearchInput.clearFocus();
        finishNotesEditMode();
        if (this.mFolderAdapter.getEditItem() != null) {
            this.mFolderAdapter.getEditItem().exitEditMode();
        }
    }

    public void exitSearchMode() {
        if (this.mIsInSearch) {
            setInSearchMode(false);
            queryNotes();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInSearchMode(boolean z) {
        if (this.mIsInSearch == z) {
            return;
        }
        this.mIsInSearch = z;
        if (isAdded()) {
            if (z) {
                getView().findViewById(R.id.create_region).setEnabled(false);
                this.mCreateNoteRegion.setEnabled(false);
                this.mCreateMoreRegion.setEnabled(false);
                return;
            }
            getView().findViewById(R.id.create_region).setEnabled(true);
            this.mCreateNoteRegion.setEnabled(true);
            this.mCreateMoreRegion.setEnabled(true);
            this.mSearchInput.removeTextChangedListener(this.mSearchInputWatch);
            this.mSearchInput.setText("");
            this.mBindContext.setSearchToken("");
            this.mCurrentSearchText = "";
            this.mSearchInput.addTextChangedListener(this.mSearchInputWatch);
            FolderItem itemByFolderId = this.mFolderAdapter.getItemByFolderId(this.mFolderId);
            if (itemByFolderId != null) {
                this.mFolderTitle.setText(itemByFolderId.getFolderName());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishNotesEditMode() {
        EditableNoteListWrapper editableNoteListWrapper = this.mNotesRvWrapper;
        if (editableNoteListWrapper != null) {
            editableNoteListWrapper.finishActionMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stickCheckedItems(boolean z) {
        this.isCreateNewNote = false;
        new NoteStickTask(this, this.mNotesRvWrapper.getCheckedItemIds(), z).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteCheckedItems(final boolean z) {
        String quantityString;
        this.isCreateNewNote = false;
        final long[] checkedItemIds = this.mNotesRvWrapper.getCheckedItemIds();
        PcDialog pcDialog = new PcDialog(getContext(), R.layout.pc_note_delete_dialog, new PcDialog.ActionListener() { // from class: com.miui.pc.frame.LeftFragment.20
            @Override // com.miui.pc.frame.PcDialog.ActionListener
            public void onCancel() {
            }

            @Override // com.miui.pc.frame.PcDialog.ActionListener
            public void onConfirm() {
                new NoteDeleteTask(LeftFragment.this, checkedItemIds, z).execute(new Void[0]);
            }
        });
        if (Utils.isUseChinese()) {
            quantityString = getString(R.string.alert_message_delete, Utils.getAllItemTerms(checkedItemIds.length, 0));
        } else {
            int checkedItemCount = this.mNotesRvWrapper.getCheckedItemCount();
            quantityString = getResources().getQuantityString(R.plurals.alert_message_delete, checkedItemCount, Integer.valueOf(checkedItemCount));
        }
        ((TextView) pcDialog.getView().findViewById(R.id.message)).setText(quantityString);
        pcDialog.show(getView());
        this.mShowingDialog = pcDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreCheckedItems() {
        this.isCreateNewNote = false;
        new NoteRestoreTask(this, this.mNotesRvWrapper.getCheckedItemIds()).execute(new Void[0]);
    }

    private static class NoteDeleteTask extends NoteOperationTask {
        private boolean iForce;

        public NoteDeleteTask(LeftFragment leftFragment, long[] jArr, boolean z) {
            super(leftFragment, jArr, R.string.dlg_delete_message);
            this.iForce = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.pc.frame.PcDataAsyncTask
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            return Integer.valueOf(NoteStore.delete(context, this.iIds, this.iForce));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.pc.frame.LeftFragment.NoteOperationTask, com.miui.pc.frame.PcDataAsyncTask
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            AudioUtils.playDeleteRingtone();
        }
    }

    private static class NoteStickTask extends NoteOperationTask {
        private boolean iStick;

        public NoteStickTask(LeftFragment leftFragment, long[] jArr, boolean z) {
            super(leftFragment, jArr, R.string.dlg_stick_message);
            this.iStick = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.pc.frame.PcDataAsyncTask
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            return Integer.valueOf(ItemStore.stick(context, this.iIds, this.iStick));
        }
    }

    private static class NoteRestoreTask extends NoteOperationTask {
        public NoteRestoreTask(LeftFragment leftFragment, long[] jArr) {
            super(leftFragment, jArr, R.string.dlg_restore_message);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.pc.frame.PcDataAsyncTask
        public Integer doBackgroundWork(Context context, Void... voidArr) {
            return Integer.valueOf(NoteStore.restore(context, this.iIds));
        }
    }

    protected static abstract class NoteOperationTask extends PcDataAsyncTask<DialogManagedFragment, Void, Void, Integer> {
        protected long[] iIds;
        private int iMsgResId;

        public NoteOperationTask(DialogManagedFragment dialogManagedFragment, long[] jArr, int i) {
            super(dialogManagedFragment);
            this.iIds = jArr;
            this.iMsgResId = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.pc.frame.PcDataAsyncTask
        public void onPostWork(Activity activity, Integer num) {
            super.onPostWork(activity, num);
            DialogManagedFragment fragment = getFragment();
            if (fragment instanceof LeftFragment) {
                LeftFragment leftFragment = (LeftFragment) fragment;
                leftFragment.finishNotesEditMode();
                leftFragment.queryNotes();
            }
        }
    }
}
