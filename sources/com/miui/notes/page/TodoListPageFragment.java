package com.miui.notes.page;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SpringItemTouchHelper;
import com.miui.common.record.AudioInputListener;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.SoftKeyboardHelper;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.tool.softinputhelper.KeyboardMarginProcessor;
import com.miui.common.view.BaseAudioCreatePanel;
import com.miui.common.view.DoneSparkAnim;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.component.CreateAsrDataDialog;
import com.miui.notes.component.datafolder.DataFolderListAdapter;
import com.miui.notes.component.datafolder.FolderClickCallBack;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.SyncStateController;
import com.miui.notes.ui.view.HomepageSpringBackLayout;
import com.miui.notes.ui.widget.FloatingActionButton;
import com.miui.todo.base.EditableRecyclerViewWrapper;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.OnSwipeItemListener;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.base.todolist.BaseTodoListPresenter;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import com.miui.todo.feature.todoedit.TodoEditController;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.TodoItemForAllFinishVh;
import com.miui.todo.feature.todolist.TodoItemOperationListener;
import com.miui.todo.feature.todolist.TodoListAdapter;
import com.miui.todo.feature.todolist.TodoListAdapterBindContext;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.TodoListItemTouchHelperCallback;
import com.miui.todo.feature.todolist.TodoListPresenter;
import com.miui.todo.feature.todolist.TodoSearchCallback;
import com.miui.todo.utils.SchedulerProvider;
import com.miui.todo.utils.TodoAnimHelper;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.RemindTimePickerDialog;
import com.miui.todo.view.TodoAudioCreateWindow;
import com.miui.todo.view.TodoRecyclerView;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import java.util.Map;
import miui.os.Build;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.IStateStyle;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.internal.app.widget.ActionBarImpl;
import miuix.micloudview.MiCloudStateView;
import miuix.micloudview.accounts.ExtraAccountManager;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.navigator.Navigator;
import miuix.navigator.navigation.NavigationBarView;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.responsive.map.ScreenSpec;
import miuix.springback.view.SpringBackLayout;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListPageFragment extends BaseListPageFragment implements TodoListContract.View, SearchView.OnQueryTextListener, TodoSearchCallback.OnSearchListener {
    private static final int EDIT_CODE = 100;
    public static final String TODO_LIST_TYPE = "todo_list_type";
    public static final boolean USE_TODO_LIST_TYPE = false;
    private TextView cancleView;
    private boolean isInFlowAnim;
    protected ActionMode mActionMode;
    private TodoAudioCreateWindow mAudioCreatePanel;
    private TodoListAdapterBindContext mBindContext;
    protected View mBlankViewContainer;
    protected CreateAsrDataDialog mCreateAsrDataDialog;
    private int mCurrentContentAccessibilityImportant;
    private DataFolderListAdapter mDataFolderListAdapter;
    protected AlertDialog mDeleteTodoDialog;
    protected boolean mInEdit;
    protected boolean mInSearchMode;
    private ItemTouchHelper mItemTouchHelper;
    private TodoListItemTouchHelperCallback mItemTouchHelperCallBack;
    private MiCloudStateView mMiCloudStateView;
    private NestedHeaderLayout mNestedHeaderLayout;
    protected TodoListContract.Presenter mPresenter;
    private View mRootView;
    private TodoLayoutManager mRvLinearLayoutManager;
    protected TodoRecyclerView mRvTodo;
    protected EditableRecyclerViewWrapper mRvTodoWrapper;
    protected ActionMode mSearchActionMode;
    protected View mSearchBar;
    private TextView mSearchBarTextView;
    protected TodoSearchCallback mSearchCallBack;
    protected SpringBackLayout mSpringBackLayout;
    private SyncStateController mSyncStateController;
    private RemindTimePickerDialog mTimePickerDialog;
    protected BaseTodoEditController mTodoEditController;
    protected TodoListAdapter mTodoListAdapter;
    private ActivityResultLauncher<String[]> requestAudioPermission;
    private ActivityResultLauncher<Intent> toCtaBeforeAudio;
    private ActivityResultLauncher<Intent> toCtaBeforeCloud;
    protected String TAG = "TodoListPageFragment";
    protected boolean mIsDragging = false;
    protected int mTodoListType = 2;
    private DoneSparkAnim mRunningSparkAnim = null;
    protected BaseTodoChildItemVh mEditingTodoVh = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected Runnable mPendingShowDataEmpty = new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment.1
        @Override // java.lang.Runnable
        public void run() {
            TodoListPageFragment.this.mRvTodo.setVisibility(8);
            TodoListPageFragment.this.mBlankViewContainer.setVisibility(0);
            TodoListPageFragment.this.mSpringBackLayout.setTarget(TodoListPageFragment.this.mBlankViewContainer);
        }
    };
    private Intent mIntentAction = null;
    private long mLastTriggerAddTime = -1;
    private long mStartTime = -1;
    private boolean isNewTodo = false;
    protected EmptyStateView mListBlankView = null;
    private int mOrientation = 0;
    private boolean mIsFoldDevice = false;
    protected KeyboardHelper mSearchKeyboardHelper = null;
    private boolean mConfigurationChanged = false;
    private Configuration mLastConfiguration = null;
    private final BroadcastReceiver mTodoDoneActionReceiver = new BroadcastReceiver() { // from class: com.miui.notes.page.TodoListPageFragment.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (TodoIntent.ACTION_DONE_TODO.equals(intent.getAction())) {
                if (TodoListPageFragment.this.mRvTodo != null) {
                    if (TodoListPageFragment.this.mRvTodo.getChildCount() < 1) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    int intExtra = intent.getIntExtra(TodoIntent.EXTRA_DONE_TODO_POSITION, 0);
                    RecyclerView.LayoutManager layoutManager = TodoListPageFragment.this.mRvTodo.getLayoutManager();
                    if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
                        intExtra += ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    }
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = TodoListPageFragment.this.mRvTodo.findViewHolderForAdapterPosition(intExtra);
                    if (viewHolderFindViewHolderForAdapterPosition != null && (viewHolderFindViewHolderForAdapterPosition instanceof BaseTodoChildItemVh)) {
                        View viewFindViewById = viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(R.id.checkbox_mask);
                        if (!((CheckBox) viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(android.R.id.checkbox)).isChecked()) {
                            viewFindViewById.callOnClick();
                            AiActionUtil.asyncNotifyResult(0);
                            return;
                        } else {
                            AiActionUtil.asyncNotifyResult(-1);
                            return;
                        }
                    }
                    AiActionUtil.asyncNotifyResult(-1);
                    return;
                }
                AiActionUtil.asyncNotifyResult(-1);
            }
        }
    };
    private final BaseAudioCreatePanel.StateCallback mAudioPanelCallBack = new BaseAudioCreatePanel.StateCallback() { // from class: com.miui.notes.page.TodoListPageFragment.3
        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onAnimUpdate(float f) {
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onPrepareRecord() {
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onShow() {
            TodoListPageFragment.this.mPresenter.enterAudioEditMode();
            if (TodoListPageFragment.this.getActivity() != null) {
                TodoListPageFragment.this.getActivity().getWindow().addFlags(128);
            }
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onToast(String str) {
            Toast.makeText(TodoListPageFragment.this.getContext(), str, 0).show();
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onCompleteRecord() {
            TodoListPageFragment.this.mPresenter.exitEditMode(true, false);
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onCancelRecord() {
            TodoListPageFragment.this.mPresenter.exitEditMode(false, false);
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onDismiss() {
            TodoListPageFragment.this.mAudioCreatePanel.dismiss();
            TodoListPageFragment.this.homeViewModel.setVpScrollable(true);
            if (TodoListPageFragment.this.getActivity() != null) {
                TodoListPageFragment.this.getActivity().getWindow().clearFlags(128);
            }
        }
    };
    private final SubTodoListListener mSubTodoListListener = new SubTodoListListener() { // from class: com.miui.notes.page.TodoListPageFragment.4
        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void onDataChanged(TodoEntity todoEntity, int i, View view) {
            if (TodoListPageFragment.this.mRvTodo.isLayoutSuppressed() || todoEntity == null) {
                return;
            }
            TodoListPageFragment.this.mPresenter.updateTodo(todoEntity, TodoListPageFragment.this.mTodoListAdapter.getItemInfo(i).mSubPosition, false);
            if (TodoListPageFragment.this.mInSearchMode) {
                return;
            }
            TodoListPageFragment.this.refreshTodoList();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void onDataFinishStateChanged(TodoEntity todoEntity, int i, View view, boolean z) {
            if (!TodoListPageFragment.this.mRvTodo.isLayoutSuppressed() && z) {
                if (!LiteUtils.isLiteOrMiddle() && TodoListPageFragment.this.mTodoListAdapter != null && TodoListPageFragment.this.mTodoListAdapter.isAllTodoWillFinished() && todoEntity.getRemindRepeatType() == 0) {
                    TodoListPageFragment.this.mRvTodo.suppressLayout(true);
                    view.findViewById(R.id.ll_expand_group).setClickable(false);
                    TodoListPageFragment.this.doAllFinishAnim(todoEntity, i, view);
                } else {
                    TodoListPageFragment.this.mRvTodo.suppressLayout(true);
                    view.findViewById(R.id.ll_expand_group).setClickable(false);
                    TodoListPageFragment.this.doFinishAnim(todoEntity, i, view);
                }
            }
        }

        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void scroll(int i, int i2, int i3) {
            int windowHeight = UIUtils.getWindowHeight(NoteApp.getInstance());
            NavigationBarView bottomTabView = Navigator.get(TodoListPageFragment.this.getThisFragment()).getBottomTabView();
            int height = bottomTabView == null ? 0 : bottomTabView.getHeight();
            Logger.INSTANCE.d(TodoListPageFragment.this.TAG, "scroll: y = " + i2 + " todoItemHeight = " + i3 + " windowHeight = " + windowHeight + " bottomTabHeight = " + height);
            if (i3 + i2 <= windowHeight - height) {
                return;
            }
            int height2 = (TodoListPageFragment.this.mMiCloudStateView == null || TodoListPageFragment.this.mMiCloudStateView.getVisibility() != 0) ? 0 : TodoListPageFragment.this.mMiCloudStateView.getHeight();
            if (TodoListPageFragment.this.mSearchBar != null && TodoListPageFragment.this.mSearchBar.getVisibility() == 0) {
                height2 += TodoListPageFragment.this.mSearchBar.getHeight();
            }
            int expandedHeight = height2 + TodoListPageFragment.this.getActionBar().getExpandedHeight();
            TodoListPageFragment.this.mNestedHeaderLayout.updateScrollingProgress(-((int) TodoListPageFragment.this.getResources().getDimension(R.dimen.miuix_appcompat_search_view_default_height)));
            TodoListPageFragment.this.getActionBar().setExpandState(0);
            TodoListPageFragment.this.mRvTodo.smoothScrollBy(0, (i2 - expandedHeight) - (TodoListPageFragment.this.getActionBar().getHeight() - TodoListPageFragment.this.getActionBar().getExpandedHeight()));
        }
    };
    private final MiCloudStateView.ILayoutUpdateListener mLayoutUpdateListener = new MiCloudStateView.ILayoutUpdateListener() { // from class: com.miui.notes.page.TodoListPageFragment.8
        @Override // miuix.micloudview.MiCloudStateView.ILayoutUpdateListener
        public void onLayoutUpdate(boolean z, boolean z2, int[] iArr) {
            if (TodoListPageFragment.this.isInFlowAnim) {
                return;
            }
            if (z && iArr != null && iArr.length > 0) {
                int i = 0;
                for (int i2 : iArr) {
                    i += i2;
                }
                if (i == 0 && TodoListPageFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                    TodoListPageFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
                    return;
                }
            }
            if (TodoListPageFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                TodoListPageFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
            }
        }
    };
    private final View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment.14
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() == R.id.content_add && !TodoListPageFragment.this.isInEdit() && TodoListPageFragment.this.mTodoEditController == null) {
                Trace.beginSection("addNoteBtnClick(todo)");
                TodoListPageFragment.this.onContentAdd();
                Trace.endSection();
                NotesPageStat.QUIT_TYPE = NotesPageStat.GO_NEW_TODO;
            }
        }
    };
    private final AudioInputListener mEditViewAudioInputListener = new AudioInputListener() { // from class: com.miui.notes.page.TodoListPageFragment.15
        private String lastValidContent = "";

        @Override // com.miui.common.record.AudioInputListener
        public void onAppendText(String str, String str2) {
            if (TodoListPageFragment.this.mPresenter.getMWorkingTodo() == null) {
                Logger.INSTANCE.w(TodoListPageFragment.this.TAG, " mEditViewAudioInputListener->onAppendText workingTodo is null");
                return;
            }
            if (!TextUtils.isEmpty(str)) {
                this.lastValidContent = str;
            }
            TodoListPageFragment.this.mPresenter.getMWorkingTodo().setContent(this.lastValidContent);
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            TodoListPageFragment.this.mPresenter.getMWorkingTodo().setAudioFileName(str2);
        }
    };
    private final OnSwipeItemListener mSwipeItemListener = new OnSwipeItemListener() { // from class: com.miui.notes.page.TodoListPageFragment.16
        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeFinish(int i, boolean z) {
        }

        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeStart(int i, boolean z) {
            TodoListPageFragment.this.mRvTodo.setMenuSwiping(true);
        }

        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeStop(int i, boolean z) {
            TodoListPageFragment.this.mRvTodo.setMenuSwiping(false);
            TodoUtils.setSwiping(false, "TodoPageFrag-swpieStop");
        }
    };
    private final OnDragItemStateListener mTouchDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.notes.page.TodoListPageFragment.17
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = true;
            TodoListPageFragment.this.homeViewModel.changeDragMode(1);
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = false;
            TodoListPageFragment.this.homeViewModel.changeDragMode(0);
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = false;
        }
    };
    private final OnDragItemStateListener mBtnDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.notes.page.TodoListPageFragment.18
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = true;
            if (TodoListPageFragment.this.mTouchDragItemStateListener != null) {
                TodoListPageFragment.this.mTouchDragItemStateListener.onStartDrag(viewHolder);
            }
            if (TodoListPageFragment.this.mItemTouchHelper != null) {
                TodoListPageFragment.this.mItemTouchHelper.startDrag(viewHolder);
            }
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = false;
            if (TodoListPageFragment.this.mTouchDragItemStateListener != null) {
                TodoListPageFragment.this.mTouchDragItemStateListener.onCancelDrag(viewHolder);
            }
            if (TodoListPageFragment.this.mItemTouchHelperCallBack != null) {
                TodoListPageFragment.this.mItemTouchHelperCallBack.onSelectedChanged(viewHolder, 0);
            }
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
            TodoListPageFragment.this.mIsDragging = false;
        }
    };
    private TodoItemOperationListener mItemOperationListener = new AnonymousClass19();
    private final View.OnDragListener mGroupDragListener = new View.OnDragListener() { // from class: com.miui.notes.page.TodoListPageFragment.20
        boolean dragedResult = false;

        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            if (action == 3) {
                try {
                    this.dragedResult = TodoListPageFragment.this.mTodoListAdapter.dragItemBetweenGroup((DataGroupInfo) dragEvent.getLocalState(), TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
            }
            if (action == 4) {
                ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.widget_text));
                return true;
            }
            if (action == 5) {
                ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.todo_item_menu_delete_color));
                return true;
            }
            if (action != 6) {
                return true;
            }
            ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.widget_text));
            return true;
        }
    };
    protected Runnable mPendingCreateCommonTodo = new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment.28
        @Override // java.lang.Runnable
        public void run() {
            if (TodoListPageFragment.this.mPresenter != null) {
                TodoListPageFragment.this.mPresenter.enterEditMode(null, -1, false, false);
            }
        }
    };

    private interface EditModeCallBackHandler {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        void updateActionItems();
    }

    private void initCategoryView(View view) {
    }

    protected int getLayoutId() {
        return R.layout.todo_fragment;
    }

    public boolean isInEdit() {
        return false;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onAddNewTodo(boolean z) {
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean z, float f) {
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            getArguments();
        }
        new TodoListPresenter(this, SchedulerProvider.getInstance());
        this.mBindContext = new TodoListAdapterBindContext();
        setThemeRes(R.style.NavigatorContentChildTheme);
        IntentFilter intentFilter = new IntentFilter(TodoIntent.ACTION_DONE_TODO);
        if (getActivity() != null) {
            if (Utils.isAboveSDK34()) {
                getActivity().registerReceiver(this.mTodoDoneActionReceiver, intentFilter, 2);
            } else {
                getActivity().registerReceiver(this.mTodoDoneActionReceiver, intentFilter);
            }
        }
        this.mIsFoldDevice = RomUtils.isFoldDevice();
        initResultLauncher();
        if (getActivity() != null) {
            this.mLastConfiguration = new Configuration();
            this.mConfigurationChanged = false;
        }
        LiveDataEventBus.with(LiveDataEventBus.SELECT_POSITION, Integer.class).observe(this, new Observer() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$0((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(Integer num) {
        if (num.intValue() == 0) {
            NotesPageStat.reportNotesQuit(getActivity(), NotesPageStat.mFolderId, System.currentTimeMillis() - this.mStartTime, "内部切换", false);
        } else {
            this.mStartTime = System.currentTimeMillis();
            NotesPageStat.reportNotesEnter(getActivity(), 0L, false);
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        Trace.beginSection(this.TAG + ".onResume");
        super.onResume();
        this.mLastTriggerAddTime = System.currentTimeMillis();
        Logger.INSTANCE.i(this.TAG, "onResume");
        this.mStartTime = System.currentTimeMillis();
        NotesPageStat.QUIT_TYPE = NotesPageStat.GO_TODO;
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.onResume();
        }
        this.mSyncStateController.onResume();
        if (isHideCloudGuide()) {
            this.mMiCloudStateView.setVisibility(8);
        } else if (!this.mInSearchMode) {
            this.mMiCloudStateView.setVisibility(0);
        }
        this.mBtnContentAdd.setContentDescription(getResources().getString(R.string.app_widget_name_create_todo));
        Trace.endSection();
        if (NotesListActivity.sSelectedPageIndex != 1 || this.homeViewModel.isSearchMode()) {
            return;
        }
        NotesPageStat.reportNotesEnter(getActivity(), 0L, false);
    }

    @Override // com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onPause() {
        Trace.beginSection("TodoPageFragment.onPause");
        super.onPause();
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null && baseTodoEditController.isShow()) {
            this.mTodoEditController.saveTodoBeforeDestroy();
        }
        this.mRvTodo.stopScroll();
        this.mSyncStateController.onPause();
        notifyTakeSnapshotQs();
        if (RomUtils.isFoldDevice()) {
            dismissDialog();
        }
        Trace.endSection();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.subscribe();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.unsubscribe();
        }
        if (NotesListActivity.sSelectedPageIndex == 1) {
            NotesPageStat.reportNotesQuit(getActivity(), 0L, System.currentTimeMillis() - this.mStartTime, NotesPageStat.QUIT_TYPE, false);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Trace.beginSection("TodoPageFragment.onInflateView");
        View viewInflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        this.mRootView = viewInflate;
        ((ViewGroup) viewInflate).setMotionEventSplittingEnabled(false);
        initView(viewInflate);
        Trace.endSection();
        return viewInflate;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        Logger.INSTANCE.d(this.TAG, "Todo Page onContentInsetChanged:" + rect);
        TodoRecyclerView todoRecyclerView = this.mRvTodo;
        if (todoRecyclerView != null) {
            todoRecyclerView.setPadding(todoRecyclerView.getPaddingLeft(), this.mRvTodo.getPaddingTop(), this.mRvTodo.getPaddingRight(), rect.bottom);
        }
        if (this.mBtnContentAdd != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBtnContentAdd.getLayoutParams();
            layoutParams.bottomMargin = rect.bottom + getResources().getDimensionPixelSize(R.dimen.floating_action_bar_margin_bottom);
            this.mBtnContentAdd.setLayoutParams(layoutParams);
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        registerCoordinatedOffsetView(true);
        setExtraHorizontalPaddingEnable(true);
        if (getActionBar() != null) {
            getActionBar().registerCoordinatedScrollView(this.mNestedHeaderLayout);
        }
        this.homeViewModel.getPageSelectedLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$1((Integer) obj);
            }
        });
        this.homeViewModel.getWindowsFocus().observe(this, new Observer() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$2((Boolean) obj);
            }
        });
        this.homeViewModel.addObserverForGlobalComposeBtnShow(this, new Observer() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$3((Boolean) obj);
            }
        }, this.TAG);
        this.homeViewModel.getIntent().observe(this, new Observer() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$4((Intent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$1(Integer num) {
        Logger.INSTANCE.d(this.TAG, "todoPage observer selected: " + num);
        if (this.mTodoEditController == null || num.intValue() != 0) {
            return;
        }
        this.mTodoEditController.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$2(Boolean bool) {
        if (bool.booleanValue() && this.mTodoEditController == null) {
            Utils.hideSoftInput(getActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$3(Boolean bool) {
        showCreateBtn(bool.booleanValue(), this.TAG + ".observer showAddBtn");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$4(Intent intent) {
        this.mIntentAction = intent;
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !action.equals(TodoIntent.ACTION_SEARCH_NOTES)) {
            return;
        }
        onTextSearch(intent.getStringExtra(TodoIntent.EXTRA_SEARCH_TODO));
        Logger.INSTANCE.d(this.TAG, "");
        this.homeViewModel.setIntent(new Intent("android.intent.action.MAIN"));
    }

    protected void registerCoordinatedOffsetView(boolean z) {
        if (z) {
            registerCoordinateScrollView(this.mNestedHeaderLayout);
        } else {
            unregisterCoordinateScrollView(this.mNestedHeaderLayout);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        Logger.INSTANCE.d(this.TAG, "todoPage onExtraPaddingChanged:" + i);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        Logger.INSTANCE.e(this.TAG, "onDestroyView");
        registerCoordinatedOffsetView(false);
        if (getActionBar() != null) {
            getActionBar().unregisterCoordinatedScrollView(this.mNestedHeaderLayout);
        }
        this.mSyncStateController = null;
        PermissionUtils.releaseListeners();
        RemindTimePickerDialog remindTimePickerDialog = this.mTimePickerDialog;
        if (remindTimePickerDialog != null) {
            remindTimePickerDialog.dismiss();
            Logger.INSTANCE.e(this.TAG, "dialog set null");
            this.mTimePickerDialog = null;
        }
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.onPause();
        }
        dismissDialog();
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            nestedHeaderLayout.removeNestedHeaderChangedListener();
            Folme.clean(this.mNestedHeaderLayout);
        }
        this.mLastConfiguration = null;
        this.mConfigurationChanged = false;
        super.onDestroyView();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intent intent;
        Logger.INSTANCE.d(this.TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        if (isAdded() && (intent = getActivity().getIntent()) != null && TodoIntent.ACTION_VIEW.equals(intent.getAction())) {
            intent.setAction("android.intent.action.MAIN");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mSearchBarTextView.setText("");
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Trace.beginSection("TodoPageFragment.onDestroy");
        ActionMode actionMode = this.mSearchActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.destroy();
        }
        CreateAsrDataDialog createAsrDataDialog = this.mCreateAsrDataDialog;
        if (createAsrDataDialog != null) {
            createAsrDataDialog.onPause();
            this.mCreateAsrDataDialog = null;
        }
        if (getActivity() != null) {
            getActivity().unregisterReceiver(this.mTodoDoneActionReceiver);
        }
        super.onDestroy();
        Trace.endSection();
    }

    @Override // com.miui.notes.page.BaseListPageFragment
    public boolean onBackPressed() {
        if (isInEdit()) {
            exitEdit(false);
            return true;
        }
        return finishActionMode();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        if (isShowEdit() && this.mOrientation != 0 && getActivity() != null && this.mIsFoldDevice && UIUtils.isInNarrowScreen(getContext()) && this.mOrientation != configuration.orientation) {
            Utils.hideSoftInput(getActivity());
        }
        this.mOrientation = configuration.orientation;
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.updateScreenSpec(getResponsiveState().getType());
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        Logger.INSTANCE.d(this.TAG, "onMultiWindowModeChanged: " + z);
        if (!RomUtils.isPadMode() && this.mRootView.findViewById(R.id.blankview_container) != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mRootView.findViewById(R.id.blankview_container).getLayoutParams();
            if (z) {
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.phone_multi_screen_note_blank_view_margin_top);
            } else {
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.phone_note_blank_view_margin_top);
            }
            this.mRootView.findViewById(R.id.blankview_container).setLayoutParams(layoutParams);
        }
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.onMultiWindowModeChanged();
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        RemindTimePickerDialog remindTimePickerDialog;
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(this.TAG, "onConfigurationChanged getNavigationMode: " + Navigator.get(this).getNavigationMode());
        checkConfigurationChanged(configuration);
        if (Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            this.mTodoListAdapter.setIsSingleColumn(1);
        } else {
            this.mTodoListAdapter.setIsSingleColumn(0);
        }
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.onConfigurationChanged();
        }
        if (this.mConfigurationChanged && (remindTimePickerDialog = this.mTimePickerDialog) != null && remindTimePickerDialog.isShowing()) {
            Logger.INSTANCE.d(this.TAG, "Configuration changed, dismissing time picker dialog");
            this.mTimePickerDialog.dismiss();
            this.mTimePickerDialog = null;
        }
    }

    private void checkConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = this.mLastConfiguration;
        if (configuration2 == null) {
            this.mLastConfiguration = new Configuration(configuration);
            this.mConfigurationChanged = false;
        } else {
            this.mConfigurationChanged = (configuration2.orientation != configuration.orientation) || (this.mLastConfiguration.screenWidthDp != configuration.screenWidthDp || this.mLastConfiguration.screenHeightDp != configuration.screenHeightDp) || (this.mLastConfiguration.densityDpi != configuration.densityDpi);
            this.mLastConfiguration = new Configuration(configuration);
        }
    }

    public void bindComposeAction(View view) {
        if (this.mBtnContentAdd.getVisibility() != 0 && this.mActionMode == null) {
            showCreateBtn(!this.mInEdit, "bindComposeAction");
        }
        if (view.getId() == R.id.content_add && !isInEdit() && this.mTodoEditController == null) {
            Trace.beginSection("addNoteBtnClick(todo)");
            onContentAdd();
            Trace.endSection();
            NotesPageStat.QUIT_TYPE = NotesPageStat.GO_NEW_TODO;
        }
    }

    protected void initView(View view) {
        SyncStateController syncStateController;
        Logger.INSTANCE.d(this.TAG, "initView");
        this.mNestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nhl_group);
        this.mSpringBackLayout = (SpringBackLayout) view.findViewById(R.id.scrollable_view_drawer);
        this.mBlankViewContainer = view.findViewById(R.id.sv_blank);
        this.mBtnContentAdd = (FloatingActionButton) view.findViewById(R.id.content_add);
        this.mBtnContentAdd.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.bindComposeAction(view2);
            }
        });
        this.mListBlankView = (EmptyStateView) view.findViewById(R.id.blankview_container);
        MiCloudStateView miCloudStateView = (MiCloudStateView) view.findViewById(R.id.micloud_state_view);
        this.mMiCloudStateView = miCloudStateView;
        miCloudStateView.setLayoutUpdateListener(this.mLayoutUpdateListener);
        this.mSyncStateController = new SyncStateController(this.mMiCloudStateView);
        this.mMiCloudStateView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$5(view2);
            }
        });
        TodoRecyclerView todoRecyclerView = (TodoRecyclerView) view.findViewById(R.id.recycler_view);
        this.mRvTodo = todoRecyclerView;
        todoRecyclerView.setImportantForAccessibility(2);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 10);
        recycledViewPool.setMaxRecycledViews(3, 5);
        recycledViewPool.setMaxRecycledViews(1, 3);
        this.mRvTodo.setRecycledViewPool(recycledViewPool);
        TodoRecyclerView todoRecyclerView2 = this.mRvTodo;
        todoRecyclerView2.setPadding(todoRecyclerView2.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.data_list_padding_top), this.mRvTodo.getPaddingRight(), this.mRvTodo.getPaddingBottom());
        TodoLayoutManager todoLayoutManager = new TodoLayoutManager(getActivity());
        this.mRvLinearLayoutManager = todoLayoutManager;
        todoLayoutManager.setInitialPrefetchItemCount(8);
        this.mRvTodo.setLayoutManager(this.mRvLinearLayoutManager);
        this.mRvTodo.setItemViewCacheSize(6);
        this.mRvTodoWrapper = new EditableRecyclerViewWrapper(this.mRvTodo);
        initAdapterIfNeeded();
        this.mRvTodoWrapper.setAdapter(this.mTodoListAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRvTodoWrapper);
        this.mRvTodo.addOnScrollListener(new AnonymousClass5());
        View viewFindViewById = view.findViewById(R.id.search_bar);
        this.mSearchBar = viewFindViewById;
        TextView textView = (TextView) viewFindViewById.findViewById(android.R.id.input);
        this.mSearchBarTextView = textView;
        textView.setHint(getString(R.string.todo_search_hint));
        this.mSearchBar.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment.6
            private long lastClickTime = System.currentTimeMillis();

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.lastClickTime;
                if (jCurrentTimeMillis > 300 || jCurrentTimeMillis < 0) {
                    this.lastClickTime = System.currentTimeMillis();
                    TodoListPageFragment.this.cancelMarkTodo();
                    TodoListPageFragment.this.closeOpenedItem();
                    TodoListPageFragment.this.onSearchRequest(view2, false, "");
                }
            }
        });
        this.mSearchBarTextView.setTextSize(DisplayUtils.getXmlDef(getContext(), R.dimen.search_bar_text_size));
        this.mAudioCreatePanel = new TodoAudioCreateWindow(getActivity(), this.mAudioPanelCallBack, this.mEditViewAudioInputListener);
        HomepageSpringBackLayout homepageSpringBackLayout = (HomepageSpringBackLayout) getActivity().getWindow().getDecorView().findViewById(R.id.sbl_fragments);
        if (homepageSpringBackLayout != null) {
            homepageSpringBackLayout.setTodoRecyclerView(this.mRvTodo);
        }
        if (PreferenceUtils.getAllowNetwork(getActivity()) && (syncStateController = this.mSyncStateController) != null) {
            syncStateController.onDataSetChanged();
        }
        initCategoryView(view);
        registerCoordinateScrollView(this.mNestedHeaderLayout);
        this.mNestedHeaderLayout.setNestedHeaderChangedListener(new NestedHeaderLayout.NestedHeaderChangedListener() { // from class: com.miui.notes.page.TodoListPageFragment.7
            @Override // miuix.nestedheader.widget.NestedHeaderLayout.NestedHeaderChangedListener
            public void onOverViewBlurStateChanged(boolean z) {
                ActionBar actionBar = TodoListPageFragment.this.getActionBar();
                if (actionBar != null) {
                    ((ActionBarImpl) actionBar).getActionBarContainer().updateBackground(z);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeCloud);
        } else {
            fragmentViewCloud();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.page.TodoListPageFragment$5, reason: invalid class name */
    class AnonymousClass5 extends RecyclerView.OnScrollListener {
        private boolean mIsLoadingMore = false;

        AnonymousClass5() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            if (NoteApp.getInstance().getTodoNumLimit() - TodoListPageFragment.this.mRvLinearLayoutManager.findFirstVisibleItemPosition() >= 20 || this.mIsLoadingMore) {
                return;
            }
            this.mIsLoadingMore = true;
            NoteApp.getInstance().addTodoNumLimit();
            TodoListPageFragment.this.mRvTodo.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onScrolled$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrolled$0() {
            if (TodoListPageFragment.this.mPresenter != null) {
                if (TodoListPageFragment.this.mInSearchMode) {
                    TodoListPageFragment.this.mPresenter.queryTodo(TodoListPageFragment.this.mBindContext.getSearchToken());
                } else {
                    TodoListPageFragment.this.mPresenter.getAndUpdateDataToView();
                }
            }
            this.mIsLoadingMore = false;
        }
    }

    public int getTodoListType() {
        return this.mTodoListType;
    }

    public void changeTodoCategory(int i) {
        if (this.mTodoListType != i) {
            Logger.INSTANCE.d(this.TAG, "changeTodoCategory");
            this.mTodoListType = i;
            refreshTodoData(i);
            DataFolderListAdapter dataFolderListAdapter = this.mDataFolderListAdapter;
            if (dataFolderListAdapter != null) {
                dataFolderListAdapter.refreshSelectStatus(this.mTodoListType);
            }
        }
    }

    protected void refreshTodoData(final int i) {
        Logger.INSTANCE.d(this.TAG, "changeTodoCategory");
        this.mRvTodo.suppressLayout(true);
        this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$refreshTodoData$6(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTodoData$6(int i) {
        if (isAdded()) {
            Logger.INSTANCE.d(this.TAG, "changeTodoCategory,task run");
            finishActionMode();
            exitEdit(false);
            this.mPresenter.setShowListType(i, true);
            this.mRvTodo.suppressLayout(false);
            onUpdateView(i);
        }
    }

    private void onUpdateView(int i) {
        this.mTodoListType = i;
        if (i == 0) {
            showCreateBtn(true, "onUpdateView,todo finish type no");
            return;
        }
        DoneSparkAnim doneSparkAnim = this.mRunningSparkAnim;
        if (doneSparkAnim != null) {
            doneSparkAnim.cancel();
            this.mRunningSparkAnim = null;
        }
        showCreateBtn(false, "onUpdateView");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fragmentViewCloud() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ExternalAppUtils.viewCloud(activity);
        }
    }

    private void initAdapterIfNeeded() {
        if (this.mTodoListAdapter == null) {
            TodoListAdapter todoListAdapter = new TodoListAdapter(getActivity(), this.mSubTodoListListener);
            this.mTodoListAdapter = todoListAdapter;
            todoListAdapter.setIsSingleColumn(Navigator.get(this).getNavigationMode() == Navigator.Mode.C ? 1 : 0);
            this.mTodoListAdapter.setOnSwipeItemListener(this.mSwipeItemListener);
            this.mTodoListAdapter.setOnDragItemListener(this.mBtnDragItemStateListener, this.mTouchDragItemStateListener);
            this.mTodoListAdapter.setItemOperationListener(this.mItemOperationListener);
            this.mTodoListAdapter.setOnGroupDragItemListener(this.mGroupDragListener);
            this.mTodoListAdapter.setBindContext(this.mBindContext);
            this.mTodoListAdapter.setPresenter(this.mPresenter);
            this.mTodoListAdapter.notifyDataSetChanged();
            this.mItemTouchHelperCallBack = new TodoListItemTouchHelperCallback(this.mTodoListAdapter);
            SpringItemTouchHelper springItemTouchHelper = new SpringItemTouchHelper(this.mItemTouchHelperCallBack);
            this.mItemTouchHelper = springItemTouchHelper;
            springItemTouchHelper.attachToRecyclerView(this.mRvTodo);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.page.TodoListPageFragment$9, reason: invalid class name */
    class AnonymousClass9 implements FolderClickCallBack {
        private Long lastEnterEditTime = null;

        AnonymousClass9() {
        }

        @Override // com.miui.notes.component.datafolder.FolderClickCallBack
        public void openFolder(View view, long j, String str, boolean z) {
            if (this.lastEnterEditTime != null && System.currentTimeMillis() - this.lastEnterEditTime.longValue() <= 400) {
                Logger.INSTANCE.i(TodoListPageFragment.this.TAG, "mItemClickListener onClick time too short");
                this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
            } else {
                this.lastEnterEditTime = Long.valueOf(System.currentTimeMillis());
                TodoListPageFragment.this.changeTodoCategory((int) j);
            }
        }
    }

    public boolean closeOpenedItem() {
        return this.mRvTodo.closeOpenedItem();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public Context getViewContext() {
        return getContext();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onEnterEditMode(TodoBaseEntity todoBaseEntity, int i, boolean z, boolean z2) {
        this.mInEdit = true;
        showCreateBtn(false, "enterEditMode");
        BaseTodoChildItemVh baseTodoChildItemVh = this.mEditingTodoVh;
        if (baseTodoChildItemVh != null) {
            baseTodoChildItemVh.itemView.findViewById(R.id.v_bg).setSelected(true);
        }
        if (z2) {
            showAsrDialog();
        } else {
            showTodoEditGroup(todoBaseEntity, z2);
        }
        View view = this.mRootView;
        if (view != null) {
            View viewFindViewById = view.getRootView().findViewById(R.id.fragment_content);
            View viewFindViewById2 = this.mRootView.getRootView().findViewById(R.id.action_bar_container);
            if (viewFindViewById != null) {
                this.mCurrentContentAccessibilityImportant = viewFindViewById.getImportantForAccessibility();
                viewFindViewById.setImportantForAccessibility(4);
            }
            if (viewFindViewById2 != null) {
                viewFindViewById2.setImportantForAccessibility(4);
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onExitEditMode(int i, boolean z) {
        SwipeMenuLayout.sForbidSwipe = false;
        this.mInEdit = false;
        if (!this.mInSearchMode) {
            showCreateBtn(true, "onExitEditMode");
        }
        BaseTodoChildItemVh baseTodoChildItemVh = this.mEditingTodoVh;
        if (baseTodoChildItemVh != null) {
            baseTodoChildItemVh.itemView.findViewById(R.id.v_bg).setSelected(false);
            this.mEditingTodoVh = null;
        }
        refreshSearchResult();
        View view = this.mRootView;
        if (view != null) {
            View viewFindViewById = view.getRootView().findViewById(R.id.fragment_content);
            View viewFindViewById2 = this.mRootView.getRootView().findViewById(R.id.action_bar_container);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(this.mCurrentContentAccessibilityImportant);
            }
            if (viewFindViewById2 != null) {
                viewFindViewById2.setImportantForAccessibility(this.mCurrentContentAccessibilityImportant);
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        if (baseTodoEditController != null) {
            baseTodoEditController.updateRemindCustomTag(z, remindTimeConfig);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void showRemindTimePickerDialog(Long l, int i) {
        if (!isAdded() || getContext() == null) {
            return;
        }
        this.mTimePickerDialog = new RemindTimePickerDialog(getContext(), R.style.NoteTheme_RemindAlertDialog, new RemindTimePickerDialog.OnTimeSetListener() { // from class: com.miui.notes.page.TodoListPageFragment.10
            @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
            public void onCancel() {
                Logger.INSTANCE.e(TodoListPageFragment.this.TAG, "dialog on cancel set null");
                TodoListPageFragment.this.mTimePickerDialog = null;
            }

            @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
            public void onTimeSet(RemindTimePickerDialog remindTimePickerDialog, RemindTimeConfig remindTimeConfig) {
                TodoListPageFragment.this.mPresenter.setRemindTimeInEditMode(remindTimeConfig);
            }
        }, i);
        Logger.INSTANCE.e(this.TAG, "picker dialog new");
        this.mTimePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.page.TodoListPageFragment.11
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (TodoListPageFragment.this.mTodoEditController != null) {
                    TodoListPageFragment.this.mTodoEditController.showSoftInput();
                }
            }
        });
        if (l != null) {
            this.mTimePickerDialog.update(l.longValue());
        }
        if (LiteUtils.isNewLiteOrMiddle()) {
            this.mTimePickerDialog.setEnableImmersive(true);
        }
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.mRootView);
        if (rootWindowInsets != null && rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
            SoftKeyboardHelper.listenKeyboardClose(getContext(), this.mRootView, new SoftKeyboardHelper.KeyboardCloseListener() { // from class: com.miui.notes.page.TodoListPageFragment.12
                @Override // com.miui.common.tool.SoftKeyboardHelper.KeyboardCloseListener
                public void onEnd() {
                    if (TodoListPageFragment.this.mTimePickerDialog != null) {
                        TodoListPageFragment.this.mTimePickerDialog.show();
                    }
                }
            });
            BaseTodoEditController baseTodoEditController = this.mTodoEditController;
            if (baseTodoEditController != null) {
                baseTodoEditController.hideSoftInput();
                return;
            }
            return;
        }
        this.mTimePickerDialog.show();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public TodoListAdapter getListAdapter() {
        return this.mTodoListAdapter;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public RecyclerView getListView() {
        return this.mRvTodo;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onMarkTodo(int i) {
        if (this.mNestedHeaderLayout.isHeaderOpen()) {
            this.mNestedHeaderLayout.setAutoHeaderClose(false);
        }
        this.mBindContext.setMarkIndex(i);
        this.mBindContext.setLastMarkIndex(i);
        this.mRvLinearLayoutManager.scrollToPositionWithOffset(i, 0);
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodo(final int i, final boolean z) {
        this.mTodoListAdapter.updateData(i);
        Logger.INSTANCE.d(this.TAG, "onUpdateTodo: updatePosition = " + i);
        this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onUpdateTodo$7(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodo$7(int i, boolean z) {
        View viewFindViewByPosition = this.mRvLinearLayoutManager.findViewByPosition(i);
        if (viewFindViewByPosition == null || !z) {
            return;
        }
        RecyclerView.ViewHolder childViewHolder = this.mRvTodo.getChildViewHolder(viewFindViewByPosition);
        if (childViewHolder instanceof BaseTodoChildItemVh) {
            BaseTodoChildItemVh baseTodoChildItemVh = (BaseTodoChildItemVh) childViewHolder;
            TodoListAdapter todoListAdapter = this.mTodoListAdapter;
            if (todoListAdapter.getRealType(todoListAdapter.getItemId(i)) == 0) {
                baseTodoChildItemVh.setTodoItemFinished();
                return;
            }
            return;
        }
        Logger.INSTANCE.w(this.TAG, "ViewHolder at position " + i + " is not BaseTodoChildItemVh ");
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onNewTodoAdded(TodoEntity todoEntity, int i, final boolean z) {
        if (isAdded()) {
            if (this.mRvTodo.getVisibility() != 0) {
                onDataEmpty(false);
            } else {
                TodoListAdapter todoListAdapter = this.mTodoListAdapter;
                if (todoListAdapter != null) {
                    todoListAdapter.notifyDataSetChanged();
                    UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
                }
            }
            this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onNewTodoAdded$8(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNewTodoAdded$8(boolean z) {
        BaseTodoChildItemVh baseTodoChildItemVh;
        View viewFindViewByPosition = this.mRvLinearLayoutManager.findViewByPosition(0);
        if (viewFindViewByPosition == null || !z || (baseTodoChildItemVh = (BaseTodoChildItemVh) this.mRvTodo.getChildViewHolder(viewFindViewByPosition)) == null) {
            return;
        }
        baseTodoChildItemVh.setTodoItemFinished();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataLoaded() {
        Logger.INSTANCE.d(this.TAG, "onDataLoaded isAdded:" + isAdded());
        if (!this.mInSearchMode || TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
            this.mTodoListAdapter.notifyDataSetChanged();
            onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
            getListAdapter().setLastFinishId(-1L);
            this.mSyncStateController.onDataSetChanged();
        } else {
            refreshSearchResult();
        }
        if (isAdded()) {
            ActionMode actionMode = this.mActionMode;
            if (actionMode != null) {
                updateActionModeButton2(actionMode);
            }
            if (getActivity() == null || getActivity().getIntent() == null || this.mIntentAction == null) {
                return;
            }
            Logger.INSTANCE.d(this.TAG, "mIntentAction:" + this.mIntentAction.getAction());
            Logger.INSTANCE.d(this.TAG, "intent.getAction():" + getActivity().getIntent().getAction());
            deliverTodoIntent(getActivity().getIntent());
        }
    }

    private void notifyTakeSnapshotQs() {
        if (LiteUtils.isSuperLite() && this.mActionMode == null && !this.mInSearchMode) {
            Utils.notifyTakeSnapshotQs(getActivity());
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataEmpty(boolean z) {
        this.mListBlankView.setIcon(R.drawable.todo_blank_icon);
        this.mListBlankView.setTitle(getString(R.string.data_empty_todo));
        this.mListBlankView.setVisibility(z ? 0 : 8);
        if (z) {
            if (this.mRvTodo.getVisibility() != 8) {
                this.mTodoListAdapter.notifyDataSetChanged();
                this.handler.postDelayed(this.mPendingShowDataEmpty, 300L);
                return;
            }
            return;
        }
        this.handler.removeCallbacks(this.mPendingShowDataEmpty);
        if (this.mRvTodo.getVisibility() != 0) {
            this.mRvTodo.setVisibility(0);
            this.mBlankViewContainer.setVisibility(8);
            this.mSpringBackLayout.setTarget(this.mRvTodo);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoFinishState(boolean z, boolean z2, int i) {
        if (isAdded()) {
            if (!this.mInSearchMode) {
                this.mTodoListAdapter.updateFinishData(z, i);
            }
            refreshSearchResult();
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoData() {
        if (isAdded()) {
            if (!this.mInSearchMode) {
                this.mRvTodo.stopScroll();
                if (this.mTodoListAdapter != null) {
                    if (!this.mRvTodo.isComputingLayout() && !this.mRvTodo.isInLayout()) {
                        this.mTodoListAdapter.notifyDataSetChanged();
                    } else {
                        this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onUpdateTodoData$9();
                            }
                        });
                    }
                    UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
                    return;
                }
                return;
            }
            refreshSearchResult();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodoData$9() {
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDeleteTodo() {
        refreshSearchResult();
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onDeleteTodos() {
        this.mRvTodoWrapper.finishActionMode();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void refreshTodoList() {
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onShowSearchResult(List<TodoEntity> list) {
        this.mTodoListAdapter.showSearchResult(list);
        onDataEmpty(list.size() == 0);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100) {
            this.mInEdit = false;
            SwipeMenuLayout.sForbidSwipe = false;
            if (this.mInSearchMode) {
                return;
            }
            showCreateBtn(true, "onActivityResult");
        }
    }

    private void deliverTodoIntent(Intent intent) {
        if (this.mIntentAction.getAction() != null) {
            String action = this.mIntentAction.getAction();
            String type = this.mIntentAction.getType();
            if (action.equals(TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT)) {
                intent.setAction("android.intent.action.MAIN");
                if (TodoIntent.TYPE_ADD_TEXT.equals(type)) {
                    if (this.mIntentAction.getIntExtra("com.miui.notes.widget_type", -1) != -1) {
                        createCommonTodoFromWidget();
                    } else {
                        createCommonTodo();
                    }
                    if (getActivity() != null) {
                        getActivity().setIntent(intent);
                        return;
                    }
                    return;
                }
                if (TodoIntent.TYPE_ADD_AUDIO.equals(type)) {
                    createCommonTodo();
                    return;
                } else {
                    showTodoFromIntent(intent);
                    return;
                }
            }
            if (action.equals(TodoIntent.ACTION_INSERT_OR_EDIT)) {
                intent.setAction("android.intent.action.MAIN");
                showTodoFromIntent(intent);
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void exitEdit(boolean z) {
        TodoEntity todoData;
        if (this.mTodoEditController == null) {
            return;
        }
        Logger.INSTANCE.d(this.TAG, "exitEdit " + z);
        if (isShowEdit()) {
            TodoListContract.Presenter presenter = this.mPresenter;
            if (presenter != null && presenter.getMWorkingTodo() != null && (todoData = this.mPresenter.getMWorkingTodo().getTodoData()) != null && !TextUtils.isEmpty(todoData.getContent()) && todoData.getRemindTime() > 0) {
                PermissionUtils.showNotificationPermission(getAppCompatActivity());
            }
            this.mTodoEditController.onPause();
            if (z) {
                this.mTodoEditController.dismiss();
            } else {
                this.mTodoEditController.dismissNoAnim();
            }
            BaseTodoEditController baseTodoEditController = this.mTodoEditController;
            if (baseTodoEditController != null) {
                baseTodoEditController.onPause();
            }
        }
    }

    public void exitSearchMode() {
        TodoSearchCallback todoSearchCallback = this.mSearchCallBack;
        if (todoSearchCallback != null) {
            todoSearchCallback.finish();
        }
    }

    public boolean isShowEdit() {
        BaseTodoEditController baseTodoEditController = this.mTodoEditController;
        return baseTodoEditController != null && baseTodoEditController.isShow();
    }

    private void createAudioTodoFromIntent(Intent intent) {
        Utils.hideSoftInput(getActivity());
        createAudioTodo();
        getActivity().setIntent(intent);
    }

    private void refreshSearchResult() {
        if (!this.mInSearchMode || this.mBindContext.getSearchToken() == null) {
            return;
        }
        Logger.INSTANCE.d(this.TAG, "refreshSearchResult");
        this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteCheckedItems() {
        FragmentActivity activity = getActivity();
        String string = getString(R.string.todo_alert_title_delete);
        int checkedItemCount = this.mRvTodoWrapper.getCheckedItemCount();
        String quantityString = getResources().getQuantityString(R.plurals.todo_alert_message_delete, checkedItemCount, Integer.valueOf(checkedItemCount));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 2131951644);
        builder.setTitle(string);
        builder.setMessage(quantityString);
        builder.setPositiveButton(R.string.todo_menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                long[] checkedItemIndexs = TodoListPageFragment.this.mRvTodoWrapper.getCheckedItemIndexs();
                TodoListPageFragment.this.mRvTodoWrapper.clearChoices();
                if (TodoListPageFragment.this.mRvTodo != null) {
                    TodoListPageFragment.this.mRvTodo.stopScroll();
                }
                TodoListPageFragment.this.mPresenter.deleteTodos(checkedItemIndexs);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = this.mDeleteTodoDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDeleteTodoDialog.dismiss();
        }
        if (LiteUtils.isSuperLite()) {
            builder.setEnableDialogImmersive(true);
        }
        AlertDialog alertDialogShow = builder.show();
        this.mDeleteTodoDialog = alertDialogShow;
        manageDialog(alertDialogShow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContentAdd() {
        Logger.INSTANCE.d(this.TAG, "onContentAdd");
        if (System.currentTimeMillis() - this.mLastTriggerAddTime <= 400) {
            return;
        }
        this.mLastTriggerAddTime = System.currentTimeMillis();
        createCommonTodo();
    }

    /* JADX INFO: renamed from: com.miui.notes.page.TodoListPageFragment$19, reason: invalid class name */
    class AnonymousClass19 implements TodoItemOperationListener {
        AnonymousClass19() {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public Observable<Boolean> onMoved(final TodoEntity todoEntity, final int i, final int i2) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.page.TodoListPageFragment$19$$ExternalSyntheticLambda0
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f$0.lambda$onMoved$0(todoEntity, i, i2, observableEmitter);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMoved$0(TodoEntity todoEntity, int i, int i2, ObservableEmitter observableEmitter) throws Exception {
            if (TodoListPageFragment.this.mPresenter.moveTodo(todoEntity, i, i2)) {
                return;
            }
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public boolean onSimpleMoved(int i, int i2) {
            if (!TodoListPageFragment.this.mRvTodoWrapper.isInActionMode()) {
                return true;
            }
            TodoListPageFragment.this.mRvTodoWrapper.swapCheckedStates(i, i2);
            return true;
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onLongClick(View view) {
            if (view == null || TodoListPageFragment.this.mRvTodoWrapper.isInActionMode() || !TodoListPageFragment.this.canEnterEditMode()) {
                return;
            }
            TodoListPageFragment.this.mItemTouchHelperCallBack.setEnableDrag(true);
            TodoListPageFragment.this.mRvTodoWrapper.setItemChecked(view, true);
            SwipeMenuLayout.sForbidSwipe = true;
            TodoListPageFragment.this.mRvTodoWrapper.startActionMode(TodoListPageFragment.this.createEditModeCallback(), TodoListPageFragment.this);
            try {
                TodoListPageFragment.this.mBtnDragItemStateListener.onStartDrag(TodoListPageFragment.this.mRvTodo.getChildViewHolder(view));
            } catch (Exception e) {
                Logger.INSTANCE.e(TodoListPageFragment.this.TAG, "error in onStartDrag " + e);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onClick(View view) {
            TodoEntity childItem;
            if (TodoListPageFragment.this.mRvTodo.isLayoutSuppressed()) {
                return;
            }
            TodoListPageFragment.this.cancelMarkTodo();
            if (TodoListPageFragment.this.mRvTodoWrapper.isInActionMode()) {
                onSelected(view);
                return;
            }
            if (TodoListPageFragment.this.mTodoListAdapter.isInDrag() || TodoListPageFragment.this.mTodoListAdapter == null || TodoListPageFragment.this.mPresenter.getMIsInEdit()) {
                return;
            }
            int childLayoutPosition = TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view);
            Log.d(TodoListPageFragment.this.TAG, "onClick: positon");
            int itemViewType = TodoListPageFragment.this.mTodoListAdapter.getItemViewType(childLayoutPosition);
            if ((itemViewType == 0 || itemViewType == 3) && (childItem = TodoListPageFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition)) != null) {
                TodoListPageFragment todoListPageFragment = TodoListPageFragment.this;
                todoListPageFragment.mEditingTodoVh = (BaseTodoChildItemVh) todoListPageFragment.mRvTodo.getChildViewHolder(view);
                TodoListPageFragment.this.mPresenter.enterEditMode(childItem, childLayoutPosition, false, false);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onSelected(View view) {
            if (TodoListPageFragment.this.mRvTodoWrapper.isInActionMode()) {
                TodoListPageFragment.this.mRvTodoWrapper.toggleItemChecked(view);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onDelete(View view) {
            int childLayoutPosition = TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view);
            TodoListPageFragment.this.cancelMarkTodo();
            TodoEntity childItem = TodoListPageFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                TodoListPageFragment.this.mRvTodo.setMenuSwiping(false);
                TodoUtils.setSwiping(false, "onDel-TodoPageFragment");
                TodoListPageFragment.this.mPresenter.deleteTodo(childItem, childLayoutPosition);
                TodoListPageFragment.this.mTodoListAdapter.deleteData(childLayoutPosition, 1);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onFinished(View view) {
            int childLayoutPosition = TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view);
            Logger.INSTANCE.d(TodoListPageFragment.this.TAG, "onFinished: position: " + childLayoutPosition);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((TodoListPageFragment.this.mBindContext.pLastClickCheckTime == null || jCurrentTimeMillis - TodoListPageFragment.this.mBindContext.pLastClickCheckTime.longValue() > 1000) && TodoListPageFragment.this.mRvTodo.isAnimating() && TodoListPageFragment.this.mRvTodo.getItemAnimator() != null) {
                TodoListPageFragment.this.mRvTodo.getItemAnimator().endAnimations();
            }
            TodoListPageFragment.this.mBindContext.pLastClickCheckTime = Long.valueOf(jCurrentTimeMillis);
            if (TodoListPageFragment.this.mRvTodo.isLayoutSuppressed() || TodoListPageFragment.this.mRvTodo.isAnimating()) {
                return;
            }
            if (!TodoListPageFragment.this.mRvTodo.getChildViewHolder(view).isRecyclable()) {
                Logger.INSTANCE.d("DoFinish", " item recycleable is false, don't work");
                return;
            }
            TodoListPageFragment.this.cancelMarkTodo();
            TodoEntity childItem = TodoListPageFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                childItem.setIsFinish(1);
                if (!LiteUtils.isLiteOrMiddle() && TodoListPageFragment.this.mTodoListAdapter.isAllTodoWillFinished() && childItem.getRemindRepeatType() == 0) {
                    TodoListPageFragment.this.isInFlowAnim = true;
                    TodoListPageFragment.this.doAllFinishAnim(childItem, childLayoutPosition, view);
                    return;
                } else {
                    TodoListPageFragment.this.doFinishAnim(childItem, childLayoutPosition, view);
                    return;
                }
            }
            TodoListPageFragment.this.mRvTodo.suppressLayout(false);
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onUnFinished(View view) {
            final int childLayoutPosition = TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view);
            final DataGroupInfo itemInfo = TodoListPageFragment.this.mTodoListAdapter.getItemInfo(childLayoutPosition);
            TodoListPageFragment.this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$19$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUnFinished$1(childLayoutPosition, itemInfo);
                }
            }, 50L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnFinished$1(int i, DataGroupInfo dataGroupInfo) {
            if (TodoListPageFragment.this.mTodoListAdapter != null) {
                TodoEntity childItem = TodoListPageFragment.this.mTodoListAdapter.getChildItem(i);
                if (childItem != null) {
                    TodoListPageFragment.this.mPresenter.setTodoFinishState(childItem, dataGroupInfo.mSubPosition, i);
                }
                TodoListPageFragment.this.mRvTodo.suppressLayout(false);
                if (!TodoListPageFragment.this.mInSearchMode) {
                    TodoListPageFragment.this.refreshTodoList();
                }
            }
            TodoListPageFragment.this.mRvTodo.suppressLayout(false);
            if (TodoListPageFragment.this.mInSearchMode) {
                return;
            }
            TodoListPageFragment.this.refreshTodoList();
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onToggleGroup(View view) {
            TodoListPageFragment.this.mTodoListAdapter.toggleGroupExpand(TodoListPageFragment.this.mRvTodo.getChildLayoutPosition(view));
        }
    }

    protected boolean canEnterEditMode() {
        return !this.mInSearchMode;
    }

    private void onAddAudioTodo(boolean z) {
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeAudio);
            return;
        }
        if (ActivityCompat.checkSelfPermission(getContext(), "android.permission.RECORD_AUDIO") == 0) {
            if (Utils.isInvisibleMode()) {
                Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.stealth_mode_tip), 0).show();
                return;
            }
            if (getActivity() != null && CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
                Utils.hideSoftInput(getActivity());
                Toast.makeText(getActivity(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
                return;
            }
            if (requestXiaoAiPermissionV2()) {
                if (z) {
                    this.mPresenter.enterEditMode(null, -1, false, true);
                    return;
                }
                this.homeViewModel.setVpScrollable(false);
                TodoAudioCreateWindow todoAudioCreateWindow = this.mAudioCreatePanel;
                if (todoAudioCreateWindow != null) {
                    todoAudioCreateWindow.show(getActivity());
                    this.homeViewModel.changeMultiMode(1);
                    return;
                }
                return;
            }
            return;
        }
        PermissionNewUtils.requestOnAudioNote(this.requestAudioPermission);
    }

    private void initResultLauncher() {
        this.requestAudioPermission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: com.miui.notes.page.TodoListPageFragment.21
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(Map<String, Boolean> map) {
                if (Utils.isInvisibleMode()) {
                    Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.stealth_mode_tip), 0).show();
                    return;
                }
                if (TodoListPageFragment.this.getActivity() != null && CompatUtils.invokeIsInMultiWindowMode(TodoListPageFragment.this.getActivity())) {
                    Utils.hideSoftInput(TodoListPageFragment.this.getActivity());
                    Toast.makeText(TodoListPageFragment.this.getActivity(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
                } else {
                    if (Boolean.TRUE.equals(map.get("android.permission.RECORD_AUDIO"))) {
                        if (TodoListPageFragment.this.requestXiaoAiPermissionV2()) {
                            TodoListPageFragment.this.mPresenter.enterEditMode(null, -1, false, true);
                            return;
                        }
                        return;
                    }
                    Toast.makeText(TodoListPageFragment.this.getActivity(), R.string.toast_refuse_to_use_record_audio, 0).show();
                }
            }
        });
        this.toCtaBeforeAudio = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.page.TodoListPageFragment.22
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(TodoListPageFragment.this.TAG, "toCtaBeforeAudio result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    PermissionNewUtils.requestOnAudioNote(TodoListPageFragment.this.requestAudioPermission);
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(TodoListPageFragment.this.toCtaBeforeAudio);
                }
            }
        });
        this.toCtaBeforeCloud = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.page.TodoListPageFragment.23
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(TodoListPageFragment.this.TAG, "toCtaBeforeCloud result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    TodoListPageFragment.this.fragmentViewCloud();
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(TodoListPageFragment.this.toCtaBeforeCloud);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestXiaoAiPermissionV2() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return false;
        }
        if (PreferenceUtils.getAllowXiaoai(activity) || Build.IS_GLOBAL_BUILD) {
            return true;
        }
        manageDialog(PermissionUtils.showXiaoaiAcceptDialog(activity, new PermissionUtils.AcceptListener() { // from class: com.miui.notes.page.TodoListPageFragment.24
            @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
            public void reject() {
            }

            @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
            public void accept() {
                TodoListPageFragment.this.mPresenter.enterEditMode(null, -1, false, true);
            }
        }));
        return false;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        this.mPresenter.enterSearchMode();
        this.mBindContext.setSearchToken(this.mSearchCallBack.getSearchText());
        if (TextUtils.isEmpty(str)) {
            this.mPresenter.getAndUpdateDataToView();
            setForAccessibility(true);
        } else {
            this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
            setForAccessibility(false);
        }
        return true;
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded()) {
            if (z) {
                this.mNestedHeaderLayout.setInSearchMode(true);
            } else {
                this.mRvTodo.stopScroll();
                this.mRvTodo.closeOpenedItem();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded()) {
            if (z) {
                this.homeViewModel.setVpScrollable(false);
                return;
            }
            this.mNestedHeaderLayout.setInSearchMode(false);
            this.homeViewModel.setVpScrollable(true);
            this.mRvTodo.scrollToPosition(0);
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
        this.mInSearchMode = true;
        this.mSearchActionMode = actionMode;
        this.mPresenter.enterSearchMode();
        showCreateBtn(false, "onCreateSearchMode");
        setForAccessibility(true);
        ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        if (!UIUtils.isInMultiWindowMode(getActivity())) {
            KeyboardHelper keyboardHelper = new KeyboardHelper(this.mSpringBackLayout, viewGroup, new KeyboardMarginProcessor() { // from class: com.miui.notes.page.TodoListPageFragment.25
                @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor
                protected void updateViewMarginBottom(View view, WindowInsets windowInsets, int i) {
                    if (TodoListPageFragment.this.mRvTodo.getVisibility() == 0) {
                        super.updateViewMarginBottom(view, windowInsets, Math.max(i, 0));
                    }
                }
            });
            this.mSearchKeyboardHelper = keyboardHelper;
            keyboardHelper.setForceMode(false);
        }
        TextView textView = (TextView) viewGroup.findViewById(R.id.search_panel).findViewById(R.id.search_text_cancel);
        this.cancleView = textView;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(textView);
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        this.mInSearchMode = false;
        this.mSearchActionMode = null;
        this.homeViewModel.setVpScrollable(true);
        this.mPresenter.exitSearchMode();
        showCreateBtn(true, "onDestroySearchMode");
        this.mTodoListAdapter.exitSearchMode();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        setForAccessibility(false);
        KeyboardHelper keyboardHelper = this.mSearchKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
            this.mSearchKeyboardHelper = null;
        }
    }

    public void setForAccessibility(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        int i = z ? 4 : 1;
        if (this.mRvTodo == null || (nestedHeaderLayout = this.mNestedHeaderLayout) == null) {
            return;
        }
        nestedHeaderLayout.setImportantForAccessibility(i);
        this.mRvTodo.setImportantForAccessibility(i);
    }

    protected void onSearchRequest(View view, final boolean z, final String str) {
        if (this.mRvTodoWrapper.isInActionMode()) {
            return;
        }
        if (this.mSearchCallBack == null) {
            this.mSearchCallBack = new TodoSearchCallback(getActivity(), this, this);
        }
        this.mSearchCallBack.setup(view, this.mNestedHeaderLayout.getScrollableView());
        if (this.handler != null) {
            this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSearchRequest$10(z, str);
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSearchRequest$10(boolean z, String str) {
        if (isAdded()) {
            startActionMode(this.mSearchCallBack);
            if (z) {
                realSetSearchText(str);
            }
        }
    }

    private void realSetSearchText(String str) {
        this.mSearchCallBack.setSearchText(str, false, false);
    }

    @Override // com.miui.todo.base.IBaseContract.View
    public void setPresenter(IBaseTodoListContract.Presenter presenter) {
        this.mPresenter = (TodoListContract.Presenter) Preconditions.checkNotNull(presenter);
    }

    protected BaseTodoEditController createEditController(TodoBaseEntity todoBaseEntity) {
        return new TodoEditController(getActivity(), this.mPresenter.getEditViewClickListener(), this.mPresenter.getTodoEditorListener(), todoBaseEntity == null);
    }

    private void showTodoEditGroup(TodoBaseEntity todoBaseEntity, boolean z) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseTodoEditController baseTodoEditControllerCreateEditController = createEditController(todoBaseEntity);
        this.mTodoEditController = baseTodoEditControllerCreateEditController;
        baseTodoEditControllerCreateEditController.setContentData(todoBaseEntity);
        baseTodoEditControllerCreateEditController.setWindowListener(new BaseTodoEditController.OnWindowListener() { // from class: com.miui.notes.page.TodoListPageFragment.26
            @Override // com.miui.todo.feature.todoedit.BaseTodoEditController.OnWindowListener
            public void onShow() {
            }

            @Override // com.miui.todo.feature.todoedit.BaseTodoEditController.OnWindowListener
            public void onDismiss() {
                if (TodoListPageFragment.this.mPresenter != null && TodoListPageFragment.this.mPresenter.getMIsInEdit()) {
                    TodoListPageFragment.this.mPresenter.exitEditMode(true, false);
                }
                TodoListPageFragment.this.mTodoEditController = null;
                if (TodoListPageFragment.this.mInSearchMode && TodoListPageFragment.this.mSearchCallBack != null) {
                    TodoListPageFragment.this.mSearchCallBack.restoreFocus();
                }
                if (TodoListPageFragment.this.mTimePickerDialog != null) {
                    TodoListPageFragment.this.mTimePickerDialog.dismiss();
                    Logger.INSTANCE.e(TodoListPageFragment.this.TAG, "dismiss set null");
                    TodoListPageFragment.this.mTimePickerDialog = null;
                }
            }
        });
        baseTodoEditControllerCreateEditController.updateScreenSpec(getResponsiveState().getType());
        baseTodoEditControllerCreateEditController.show(this.mRootView);
    }

    private void showAsrDialog() {
        CreateAsrDataDialog createAsrDataDialog = this.mCreateAsrDataDialog;
        if (createAsrDataDialog == null || !createAsrDataDialog.isShowing()) {
            CreateAsrDataDialog createAsrDataDialog2 = new CreateAsrDataDialog(getContext(), 1, new View.OnClickListener() { // from class: com.miui.notes.page.TodoListPageFragment.27
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (view.getId() == R.id.record && TodoListPageFragment.this.mPresenter != null && TodoListPageFragment.this.mPresenter.getMIsInEdit()) {
                        TodoListPageFragment.this.mPresenter.exitEditMode(true, false);
                        ToDoStat.markDoneEdit(0, 1, 0, true, ToDoStat.NEW);
                    }
                    TodoListPageFragment.this.homeViewModel.setVpScrollable(true);
                }
            }, this.mEditViewAudioInputListener);
            this.mCreateAsrDataDialog = createAsrDataDialog2;
            createAsrDataDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$showAsrDialog$11(dialogInterface);
                }
            });
            this.mCreateAsrDataDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAsrDialog$11(DialogInterface dialogInterface) {
        CreateAsrDataDialog createAsrDataDialog = this.mCreateAsrDataDialog;
        if (createAsrDataDialog != null) {
            createAsrDataDialog.onPause();
            this.mCreateAsrDataDialog = null;
        }
    }

    public EditModeCallback createEditModeCallback() {
        return new EditModeCallback();
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        TodoListContract.Presenter presenter;
        super.onVisibilityChanged(z);
        if (!z || (presenter = this.mPresenter) == null) {
            return;
        }
        presenter.subscribe();
    }

    public void onTextSearch(String str) {
        View view = this.mSearchBar;
        if (view != null) {
            onSearchRequest(view, true, str);
        }
    }

    protected class EditModeCallback implements EditableRecyclerViewWrapper.MultiChoiceModeListener {
        private boolean isFinishGroupExpand = false;
        private EditModeCallBackHandler mCallbackHandler;

        public EditModeCallback() {
            this.mCallbackHandler = new TodoListEditModeCallBackHandler();
        }

        @Override // com.miui.todo.base.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            TodoListPageFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
            TodoListPageFragment.this.checkTalkBackAndHint(false, z);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            TodoListPageFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            Rect rect = new Rect();
            if (TodoListPageFragment.this.mSearchBar.getBackground() != null) {
                TodoListPageFragment.this.mSearchBar.getBackground().getPadding(rect);
            }
            TodoListPageFragment.this.mTodoListAdapter.setEditMode(true);
            this.isFinishGroupExpand = TodoListPageFragment.this.mTodoListAdapter.isExpandGroup(1);
            TodoListPageFragment.this.updateActionModeButton2(actionMode);
            TodoListPageFragment.this.mActionMode = actionMode;
            return this.mCallbackHandler.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.mCallbackHandler.updateActionItems();
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (TodoListPageFragment.this.mIsDragging) {
                return true;
            }
            if (menuItem.getItemId() == 16908313) {
                actionMode.finish();
            } else if (menuItem.getItemId() == 16908314) {
                if (!TodoListPageFragment.this.mTodoListAdapter.isExpandGroup(1)) {
                    TodoListPageFragment.this.mTodoListAdapter.toggleGroupExpandByType(1);
                    TodoListPageFragment.this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$EditModeCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onActionItemClicked$0();
                        }
                    });
                } else {
                    TodoListPageFragment.this.mRvTodoWrapper.setAllItemsChecked(true ^ TodoListPageFragment.this.mRvTodoWrapper.isAllItemsChecked());
                }
            }
            return this.mCallbackHandler.onActionItemClicked(actionMode, menuItem);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActionItemClicked$0() {
            TodoListPageFragment.this.mRvTodoWrapper.setAllItemsChecked(!TodoListPageFragment.this.mRvTodoWrapper.isAllItemsChecked());
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            Logger.INSTANCE.d(TodoListPageFragment.this.TAG, "onDestroyActionMode: ");
            TodoListPageFragment.this.mTodoListAdapter.setEditMode(false);
            if (this.isFinishGroupExpand != TodoListPageFragment.this.mTodoListAdapter.isExpandGroup(1)) {
                TodoListPageFragment.this.mTodoListAdapter.toggleGroupExpandByType(1);
            } else {
                TodoListPageFragment.this.mTodoListAdapter.notifyDataSetChanged();
            }
            TodoListPageFragment.this.mActionMode = null;
            if (TodoListPageFragment.this.mTodoListType == 0) {
                TodoListPageFragment.this.showCreateBtn(true, "category no");
            }
            TodoListPageFragment.this.cancelMarkTodo();
            TodoListPageFragment.this.mItemTouchHelperCallBack.setEnableDrag(false);
            TodoListPageFragment.this.homeViewModel.changeMultiMode(0);
            TodoListPageFragment.this.homeViewModel.setVpScrollable(true);
            this.mCallbackHandler.onDestroyActionMode(actionMode);
            SwipeMenuLayout.sForbidSwipe = false;
        }
    }

    private class TodoListEditModeCallBackHandler implements EditModeCallBackHandler {
        @Override // com.miui.notes.page.TodoListPageFragment.EditModeCallBackHandler
        public void updateActionItems() {
        }

        private TodoListEditModeCallBackHandler() {
        }

        @Override // com.miui.notes.page.TodoListPageFragment.EditModeCallBackHandler
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            TodoListPageFragment.this.getMenuInflater().inflate(R.menu.todo_list_options, menu);
            TodoListPageFragment.this.showCreateBtn(false, "onCreateActionMode");
            TodoListPageFragment.this.homeViewModel.changeMultiMode(1);
            TodoListPageFragment.this.homeViewModel.setVpScrollable(false);
            return true;
        }

        @Override // com.miui.notes.page.TodoListPageFragment.EditModeCallBackHandler
        public void onDestroyActionMode(ActionMode actionMode) {
            TodoListPageFragment.this.showCreateBtn(true, "onDestroyActionMode");
        }

        @Override // com.miui.notes.page.TodoListPageFragment.EditModeCallBackHandler
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (TodoListPageFragment.this.mIsDragging) {
                return true;
            }
            if (menuItem.getItemId() != R.id.delete) {
                return false;
            }
            if (TodoListPageFragment.this.mRvTodo != null) {
                TodoListPageFragment.this.mRvTodo.stopScroll();
            }
            TodoListPageFragment.this.deleteCheckedItems();
            return true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void updateActionModeButton2(ActionMode actionMode) {
        EditActionMode editActionMode = (EditActionMode) actionMode;
        if (UIUtils.isMiuiXSdkSupported()) {
            int i = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_deselect_all_dark : R.drawable.miuix_action_icon_deselect_all_light;
            int i2 = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_select_all_dark : R.drawable.miuix_action_icon_select_all_light;
            if (!this.mRvTodoWrapper.isAllItemsChecked()) {
                i = i2;
            }
            editActionMode.setButton(16908314, (CharSequence) null, i);
        } else {
            editActionMode.setButton(16908314, this.mRvTodoWrapper.isAllItemsChecked() ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
        }
        checkTalkBackAndHint(true, this.mRvTodoWrapper.isAllItemsChecked());
    }

    public boolean finishActionMode() {
        if (this.mActionMode == null) {
            return false;
        }
        Logger.INSTANCE.d(this.TAG, "finishActionMode");
        this.mRvTodoWrapper.finishActionMode();
        return true;
    }

    private void showTodoFromIntent(Intent intent) {
        openTodoFromId(intent.getLongExtra(TodoIntent.EXTRA_TODO_ID, -1L));
        getActivity().setIntent(intent);
    }

    public void openTodoFromId(long j) {
        finishActionMode();
        if (j != -1) {
            try {
                this.mPresenter.setEditTodoFromIntent(j);
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "error in showTodoFromId " + e);
            }
        }
    }

    protected void createCommonTodo() {
        if (isShowEdit()) {
            return;
        }
        Logger.INSTANCE.d(this.TAG, "createCommonTodo");
        finishActionMode();
        cancelMarkTodo();
        closeOpenedItem();
        this.isNewTodo = true;
        CreateAsrDataDialog createAsrDataDialog = this.mCreateAsrDataDialog;
        if (createAsrDataDialog != null && createAsrDataDialog.isShowing()) {
            this.mCreateAsrDataDialog.dismissWithoutAnimation();
            this.handler.removeCallbacks(this.mPendingCreateCommonTodo);
            this.handler.postDelayed(this.mPendingCreateCommonTodo, 300L);
        } else {
            this.handler.removeCallbacks(this.mPendingCreateCommonTodo);
            this.mPresenter.enterEditMode(null, -1, false, false);
        }
    }

    protected void createCommonTodoFromWidget() {
        Logger.INSTANCE.d(this.TAG, "createCommonTodoFromWidget");
        if (isShowEdit()) {
            return;
        }
        finishActionMode();
        cancelMarkTodo();
        closeOpenedItem();
        this.isNewTodo = true;
        this.handler.removeCallbacks(this.mPendingCreateCommonTodo);
        this.handler.postDelayed(this.mPendingCreateCommonTodo, 600L);
    }

    protected void createAudioTodo() {
        Logger.INSTANCE.d(this.TAG, "createAudioTodo");
        if (isShowEdit()) {
            this.mTodoEditController.dismissNoAnim();
        }
        finishActionMode();
        cancelMarkTodo();
        closeOpenedItem();
        onAddAudioTodo(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelMarkTodo() {
        if (this.mBindContext.getLastMarkIndex() != -1) {
            Logger.INSTANCE.d(this.TAG, "cancelMarkTodo");
            this.mTodoListAdapter.notifyItemChanged(this.mBindContext.getLastMarkIndex());
            this.mBindContext.setLastMarkIndex(-1);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void finishTodoInData(TodoEntity todoEntity, int i) {
        if (this.mTodoListAdapter.getChildItem(i) == null || todoEntity == null) {
            return;
        }
        this.mPresenter.updateTodo(todoEntity, this.mTodoListAdapter.getItemInfo(i).mSubPosition, true);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doFinishAnim(TodoEntity todoEntity, int i, View view) {
        ((BaseTodoListPresenter) this.mPresenter).setPendingFinishPosition(i);
        TodoAnimHelper.doFinishAnim(this, todoEntity, i, view, null);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doAllFinishAnim(TodoEntity todoEntity, int i, View view) {
        if (!isAdded() || getContext() == null) {
            return;
        }
        ((BaseTodoListPresenter) this.mPresenter).setPendingFinishPosition(i);
        ViewGroup viewGroup = (ViewGroup) this.mRvTodo.getRootView();
        FrameLayout frameLayout = new FrameLayout(getContext());
        TodoAnimHelper.doAllFinishAnim(this, todoEntity, i, view, viewGroup, frameLayout, TodoItemForAllFinishVh.newInstance(frameLayout, this.mInSearchMode), new TodoAnimHelper.Listener() { // from class: com.miui.notes.page.TodoListPageFragment.29
            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onStart(DoneSparkAnim doneSparkAnim) {
                TodoListPageFragment.this.isInFlowAnim = true;
                TodoListPageFragment.this.mRunningSparkAnim = doneSparkAnim;
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onEnd() {
                TodoListPageFragment.this.isInFlowAnim = false;
                TodoListPageFragment.this.mRunningSparkAnim = null;
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onCancel() {
                TodoListPageFragment.this.isInFlowAnim = false;
                TodoListPageFragment.this.mRunningSparkAnim = null;
            }
        });
    }

    private boolean isHideCloudGuide() {
        if (!LiteUtils.isMiddle() && !LiteUtils.isSuperLite() && !RomUtils.isInternationalBuild() && !RomUtils.isRedmiDigitSeries()) {
            return false;
        }
        if (!PreferenceUtils.isCTAAccepted(getContext())) {
            return true;
        }
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance());
        return !ContentResolver.getSyncAutomatically(xiaomiAccount, "notes") || xiaomiAccount == null;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public boolean getIsNewTodo() {
        return this.isNewTodo;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void setIsNewTodo(boolean z) {
        this.isNewTodo = z;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.homeViewModel.getPageSelected() == 1 && keyEvent.hasModifiers(4096) && keyEvent.getRepeatCount() == 0 && i == 42 && this.mBtnContentAdd != null) {
            this.mBtnContentAdd.callOnClick();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    protected void showCreateBtn(boolean z, String str) {
        Logger.INSTANCE.d(this.TAG, "showCreateBtn " + z + ",addBtn:vis:" + this.mBtnContentAdd.getVisibility() + ",currentPage = " + this.homeViewModel.getPageSelected() + ",invokeSource = " + str + Log.getStackTraceString(new Throwable()));
        Folme.clean(this.mBtnContentAdd);
        Folme.use((View) this.mBtnContentAdd).touch().setTint(0);
        Folme.use((View) this.mBtnContentAdd).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(this.mBtnContentAdd, new AnimConfig[0]);
        if (z) {
            if (this.homeViewModel.getPageSelected() == 1 && (this.mBtnContentAdd.getVisibility() != 0 || this.mBtnContentAdd.getAlpha() < 1.0f)) {
                this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$showCreateBtn$12();
                    }
                });
                return;
            } else {
                Logger.INSTANCE.i(this.TAG, "showCreateBtn,abort!!");
                return;
            }
        }
        if (this.mBtnContentAdd.getVisibility() == 0 && this.homeViewModel.getPageSelected() == 1) {
            this.handler.post(new Runnable() { // from class: com.miui.notes.page.TodoListPageFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showCreateBtn$13();
                }
            });
        } else {
            Logger.INSTANCE.i(this.TAG, "showCreateBtn,hide abort!!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateBtn$12() {
        Logger.INSTANCE.i(this.TAG, "showCreateBtn,gonna show");
        this.mBtnContentAdd.setVisibility(0);
        this.mBtnContentAdd.setScaleX(0.8f);
        this.mBtnContentAdd.setScaleY(0.8f);
        Folme.use((View) this.mBtnContentAdd).visible().show(new AnimConfig[0]);
        this.mBtnContentAdd.setShadowAlpha(0);
        this.mBtnContentAdd.showShadow();
        IStateStyle iStateStyleState = Folme.use((View) this.mBtnContentAdd).state();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(1.0f);
        iStateStyleState.to(viewProperty, fValueOf).to(ViewProperty.SCALE_Y, fValueOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateBtn$13() {
        Logger.INSTANCE.i(this.TAG, "showCreateBtn,gonna hide");
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(EaseManager.getStyle(0, 250.0f, 0.95f, 0.3f));
        Folme.use((View) this.mBtnContentAdd).visible().setScale(0.8f, IVisibleStyle.VisibleType.HIDE).hide(animConfig);
    }

    public void checkTalkBackAndHint(boolean z, boolean z2) {
        View decorView = getActivity() != null ? getActivity().getWindow().getDecorView() : null;
        if (decorView != null) {
            View viewFindViewById = decorView.findViewById(R.id.action_context_bar).findViewById(16908314);
            if (z) {
                viewFindViewById.setContentDescription(getString(z2 ? R.string.miuix_appcompat_deselect_all_description : R.string.miuix_appcompat_select_all_description));
            } else {
                viewFindViewById.announceForAccessibility(getString(z2 ? R.string.note_select_all_talkback : R.string.note_unselect_all_talkback));
            }
        }
    }
}
