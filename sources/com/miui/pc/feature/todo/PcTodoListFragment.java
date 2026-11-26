package com.miui.pc.feature.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.hardware.DataSpace;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SpringItemTouchHelper;
import com.miui.common.base.BaseTodoFragment;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.DoneSparkAnim;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.SyncStateController;
import com.miui.notes.ui.view.HomepageSpringBackLayout;
import com.miui.pc.component.PcListBlankView;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.ProTodoRecyclerView;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.base.TodoListOptionMenu;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import com.miui.todo.feature.todolist.TodoItemOperationListener;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.TodoListItemTouchHelperCallback;
import com.miui.todo.feature.todolist.TodoSearchCallback;
import com.miui.todo.utils.TodoAnimHelper;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import miuix.animation.Folme;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.recyclerview.widget.MiuiScaleItemAnimator;
import miuix.springback.view.SpringBackLayout;
import miuix.view.EditActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoListFragment extends BaseTodoFragment implements TodoListContract.View, SearchView.OnQueryTextListener, TodoSearchCallback.OnSearchListener {
    private static final String TAG = "PcTodoListFragment";
    private boolean isInFlowAnim;
    protected ActionMode mActionMode;
    private PcTodoListAdapterBindContext mBindContext;
    protected View mBlankViewContainer;
    protected boolean mInSearchMode;
    private ItemTouchHelper mItemTouchHelper;
    private TodoListItemTouchHelperCallback mItemTouchHelperCallBack;
    private PcListBlankView mListBlankView;
    private NestedHeaderLayout mNestedHeaderLayout;
    private TodoListContract.Presenter mPresenter;
    private ObservableEmitter<Boolean> mRemindTimeSettingDialogEmitter;
    private View mRootView;
    private TodoLayoutManager mRvLinearLayoutManager;
    protected ProTodoRecyclerView mRvTodo;
    protected EditableTodoListWrapper mRvTodoWrapper;
    protected TextView mSearchResult;
    protected View mSearchResultGroup;
    protected SpringBackLayout mSpringBackLayout;
    private SyncStateController mSyncStateController;
    private PcTodoEditController mTodoEditController;
    private PcTodoFastCreateController mTodoFastCreateController;
    protected PcTodoListAdapter mTodoListAdapter;
    private TodoListOptionMenu mTodoListOptionMenu;
    private TextView mTvCategoryTitle;
    private int mTouchTodoItemPosFromRightClick = -1;
    private PcTodoChildItemVh mEditingTodoVh = null;
    private boolean mIsInFastCreateMode = false;
    float mSearchModePaddingTopExtra = 0.0f;
    private String mIntentAction = null;
    private final BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.1
        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragDrop(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragEntered(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected void handleDragLocation(View view, DragEvent dragEvent) {
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragStarted(View view, DragEvent dragEvent) {
            return true;
        }
    };
    private OnDragItemStateListener mTouchDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.5
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        }
    };
    private final OnDragItemStateListener mBtnDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.6
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            if (PcTodoListFragment.this.mTouchDragItemStateListener != null) {
                PcTodoListFragment.this.mTouchDragItemStateListener.onStartDrag(viewHolder);
            }
            if (PcTodoListFragment.this.mItemTouchHelper != null) {
                PcTodoListFragment.this.mItemTouchHelper.startDrag(viewHolder);
            }
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
            if (PcTodoListFragment.this.mTouchDragItemStateListener != null) {
                PcTodoListFragment.this.mTouchDragItemStateListener.onCancelDrag(viewHolder);
            }
            if (PcTodoListFragment.this.mItemTouchHelperCallBack != null) {
                PcTodoListFragment.this.mItemTouchHelperCallBack.onSelectedChanged(viewHolder, 0);
            }
        }
    };
    private TodoItemOperationListener mItemOperationListener = new AnonymousClass7();
    private final View.OnDragListener mGroupDragListener = new View.OnDragListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.8
        boolean dragedResult = false;

        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            if (action == 3) {
                try {
                    this.dragedResult = PcTodoListFragment.this.mTodoListAdapter.dragItemBetweenGroup((DataGroupInfo) dragEvent.getLocalState(), PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view));
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
                ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.pc_todo_item_menu_delete_color));
                return true;
            }
            if (action != 6) {
                return true;
            }
            ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.widget_text));
            return true;
        }
    };

    public interface OnTimeSetListener {
        void onCancel();

        void onTimeSet(RemindTimeConfig remindTimeConfig);
    }

    @Override // com.miui.common.base.BaseTodoFragment
    protected BaseTodoEditController createEditController(TodoBaseEntity todoBaseEntity) {
        return null;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public boolean getIsNewTodo() {
        return false;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onAddNewTodo(boolean z) {
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onNewTodoAdded(TodoEntity todoEntity, int i, boolean z) {
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void setIsNewTodo(boolean z) {
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void showRemindTimePickerDialog(Long l, int i) {
    }

    private PcTodoEditController createPcEditController(TodoBaseEntity todoBaseEntity) {
        return new PcTodoEditController(getContext(), this.mPresenter.getEditViewClickListener(), this.mPresenter.getTodoEditorListener(), todoBaseEntity == null);
    }

    @Override // com.miui.common.base.BaseTodoFragment, com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBindContext = new PcTodoListAdapterBindContext();
        setThemeRes(R.style.TodoTheme_PageList);
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.setShowListType(getTodoListType(), false);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.i(TAG, "onResume");
        this.mPresenter.subscribe();
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mPresenter.unsubscribe();
        dismissDialog();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.pc_todo_fragment, viewGroup, false);
        this.mRootView = viewInflate;
        viewInflate.setOnDragListener(this.mBlockDragListener);
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$0(view);
            }
        });
        ((ViewGroup) this.mRootView).setMotionEventSplittingEnabled(false);
        initView(viewInflate);
        onUpdateView(getTodoListType());
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$0(View view) {
        finishActionMode();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        if (this.mPresenter.getMIsInEdit()) {
            exitEdit(false);
            this.mTodoEditController.dismiss();
        }
        this.mSyncStateController = null;
        this.mTodoListAdapter = null;
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            Folme.clean(nestedHeaderLayout);
        }
        notifyAction(2, null);
        PermissionUtils.releaseListeners();
        super.onDestroyView();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intent intent;
        String action;
        super.onViewCreated(view, bundle);
        if (!isAdded() || (intent = getActivity().getIntent()) == null || (action = intent.getAction()) == null) {
            return;
        }
        if (TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT.equals(action) || TodoIntent.ACTION_INSERT_OR_EDIT.equals(action)) {
            this.mIntentAction = action;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
    }

    @Override // com.miui.common.base.BaseTodoFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.miui.common.base.BaseTodoFragment
    protected void refreshTodoData(final int i) {
        Runnable runnable = new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$refreshTodoData$1(i);
            }
        };
        if (!this.mRvTodo.isLayoutSuppressed() && this.mRvTodo.isAttachedToWindow()) {
            this.mRvTodo.suppressLayout(true);
            this.mRvTodo.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTodoData$1(int i) {
        if (isAdded()) {
            finishActionMode();
            PcTodoEditController pcTodoEditController = this.mTodoEditController;
            if (pcTodoEditController != null && pcTodoEditController.isShow()) {
                exitEdit(false);
                this.mTodoEditController.dismiss();
            }
            setTodoListType(i);
            this.mPresenter.setShowListType(i, true);
            this.mRvTodo.suppressLayout(false);
            onUpdateView(i);
        }
    }

    private void initView(View view) {
        SyncStateController syncStateController;
        this.mNestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nhl_group);
        SpringBackLayout springBackLayout = (SpringBackLayout) view.findViewById(R.id.scrollable_view_drawer);
        this.mSpringBackLayout = springBackLayout;
        springBackLayout.setNestedScrollingEnabled(false);
        this.mBlankViewContainer = view.findViewById(R.id.sv_blank);
        this.mSearchResultGroup = view.findViewById(R.id.todo_search_result_group);
        this.mSearchResult = (TextView) view.findViewById(R.id.todo_search_result);
        ProTodoRecyclerView proTodoRecyclerView = (ProTodoRecyclerView) view.findViewById(R.id.recycler_view);
        this.mRvTodo = proTodoRecyclerView;
        proTodoRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        ProTodoRecyclerView proTodoRecyclerView2 = this.mRvTodo;
        proTodoRecyclerView2.setPadding(proTodoRecyclerView2.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.v12_grid_item_space) / 2, this.mRvTodo.getPaddingRight(), this.mRvTodo.getPaddingBottom());
        TodoLayoutManager todoLayoutManager = new TodoLayoutManager(getActivity());
        this.mRvLinearLayoutManager = todoLayoutManager;
        this.mRvTodo.setLayoutManager(todoLayoutManager);
        this.mRvTodo.setItemAnimator(new MiuiScaleItemAnimator());
        this.mRvTodo.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$2(view2);
            }
        });
        this.mRvTodo.setOnRightClickListener(new ProTodoRecyclerView.OnRightClickListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda3
            @Override // com.miui.todo.base.ProTodoRecyclerView.OnRightClickListener
            public final void onRightClick(float f, float f2, int i) {
                this.f$0.lambda$initView$3(f, f2, i);
            }
        });
        this.mRvTodoWrapper = new EditableTodoListWrapper(this.mRvTodo);
        initAdapterIfNeeded();
        this.mRvTodoWrapper.setAdapter(this.mTodoListAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRvTodoWrapper);
        HomepageSpringBackLayout homepageSpringBackLayout = (HomepageSpringBackLayout) getActivity().getWindow().getDecorView().findViewById(R.id.sbl_fragments);
        if (homepageSpringBackLayout != null) {
            homepageSpringBackLayout.setTodoRecyclerView(this.mRvTodo);
        }
        if (PermissionUtils.supportNewPermissionStyle()) {
            if (PreferenceUtils.getAllowNetwork(getActivity()) && (syncStateController = this.mSyncStateController) != null) {
                syncStateController.onDataSetChanged();
            }
        } else if (!PreferenceUtils.isCTAAccepted(getContext())) {
            CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.3
                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onAccept() {
                    if (PcTodoListFragment.this.mSyncStateController != null) {
                        PcTodoListFragment.this.mSyncStateController.onDataSetChanged();
                    }
                }

                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onReject() {
                    Logger.INSTANCE.w(PcTodoListFragment.TAG, "user deny to authorization");
                }
            });
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_category_title);
        this.mTvCategoryTitle = textView;
        textView.setText(R.string.todo_item_status_unfinished);
        PcTodoFastCreateController pcTodoFastCreateController = new PcTodoFastCreateController(view.findViewById(R.id.todo_fast_create_group));
        this.mTodoFastCreateController = pcTodoFastCreateController;
        pcTodoFastCreateController.setSaveListener(new PcTodoFastCreateController.SaveListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda4
            @Override // com.miui.pc.feature.todo.PcTodoFastCreateController.SaveListener
            public final void onSave(String str, RemindTimeConfig remindTimeConfig) {
                this.f$0.lambda$initView$4(str, remindTimeConfig);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        finishActionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(float f, float f2, int i) {
        PcTodoEditController pcTodoEditController = this.mTodoEditController;
        if (pcTodoEditController != null && pcTodoEditController.isShow()) {
            exitEdit(false);
            this.mTodoEditController.dismiss();
            return;
        }
        if (this.mTodoListOptionMenu == null) {
            TodoListOptionMenu todoListOptionMenu = new TodoListOptionMenu(getContext());
            this.mTodoListOptionMenu = todoListOptionMenu;
            todoListOptionMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.2
                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onShow() {
                }

                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onDismiss() {
                    PcTodoListFragment.this.mTouchTodoItemPosFromRightClick = -1;
                    if (PcTodoListFragment.this.mRvTodoWrapper.isInActionMode()) {
                        return;
                    }
                    PcTodoListFragment.this.mRvTodoWrapper.clearChoices();
                }

                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onItemSelected(View view, int i2) {
                    int id = view.getId();
                    if (id == R.id.delete) {
                        PcTodoListFragment.this.deleteCheckedItems();
                    } else {
                        if (id != R.id.multi_choice) {
                            return;
                        }
                        PcTodoListFragment.this.mRvTodoWrapper.startActionMode(PcTodoListFragment.this.createEditModeCallback());
                    }
                }
            });
        }
        this.mTodoListOptionMenu.show(this.mRvTodo, new int[]{(int) f, (int) f2});
        this.mTouchTodoItemPosFromRightClick = i;
        this.mRvTodoWrapper.setItemChecked(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(String str, RemindTimeConfig remindTimeConfig) {
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter == null) {
            return;
        }
        this.mIsInFastCreateMode = true;
        presenter.enterEditMode(null, -1, false, false);
        this.mPresenter.getMWorkingTodo().setContent(str);
        if (remindTimeConfig != null) {
            this.mPresenter.getMWorkingTodo().setRemindTime(remindTimeConfig);
        }
        this.mPresenter.exitEditMode(true, false);
        this.mIsInFastCreateMode = false;
        if (this.mRvTodo.getVisibility() != 0) {
            onDataEmpty(false);
        }
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    private void onUpdateView(int i) {
        if (i == 0) {
            TextView textView = this.mTvCategoryTitle;
            if (textView != null) {
                textView.setText(R.string.todo_item_status_unfinished);
            }
            PcTodoFastCreateController pcTodoFastCreateController = this.mTodoFastCreateController;
            if (pcTodoFastCreateController != null) {
                pcTodoFastCreateController.show();
                return;
            }
            return;
        }
        TextView textView2 = this.mTvCategoryTitle;
        if (textView2 != null) {
            textView2.setText(R.string.todo_item_group_finished);
        }
        PcTodoFastCreateController pcTodoFastCreateController2 = this.mTodoFastCreateController;
        if (pcTodoFastCreateController2 != null) {
            pcTodoFastCreateController2.hide();
        }
    }

    private void initAdapterIfNeeded() {
        if (this.mTodoListAdapter == null) {
            PcTodoListAdapter pcTodoListAdapter = new PcTodoListAdapter(getActivity(), this.mPresenter.getMSubTodoListListener());
            this.mTodoListAdapter = pcTodoListAdapter;
            pcTodoListAdapter.setOnDragItemListener(this.mBtnDragItemStateListener, this.mTouchDragItemStateListener);
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

    public boolean isInEdit() {
        return this.mPresenter.getMIsInEdit();
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
        if (this.mIsInFastCreateMode) {
            return;
        }
        PcTodoChildItemVh pcTodoChildItemVh = this.mEditingTodoVh;
        if (pcTodoChildItemVh != null) {
            pcTodoChildItemVh.itemView.findViewById(R.id.v_bg).setSelected(true);
        }
        showTodoPadEditGroup(todoBaseEntity, z2);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onExitEditMode(int i, boolean z) {
        if (this.mIsInFastCreateMode) {
            return;
        }
        PcTodoChildItemVh pcTodoChildItemVh = this.mEditingTodoVh;
        if (pcTodoChildItemVh != null) {
            pcTodoChildItemVh.itemView.findViewById(R.id.v_bg).setSelected(false);
            this.mEditingTodoVh = null;
        }
        refreshSearchResult();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        PcTodoEditController pcTodoEditController = this.mTodoEditController;
        if (pcTodoEditController != null) {
            pcTodoEditController.updateRemindCustomTag(z, remindTimeConfig);
        }
    }

    public void showRemindTimePickerPopupWin(View view, Long l, int i) {
        PcTodoEditController pcTodoEditController = this.mTodoEditController;
        if (pcTodoEditController == null) {
            return;
        }
        pcTodoEditController.showRemindTimePickerPopupWin(l, i, new OnTimeSetListener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.4
            @Override // com.miui.pc.feature.todo.PcTodoListFragment.OnTimeSetListener
            public void onCancel() {
                if (PcTodoListFragment.this.mRemindTimeSettingDialogEmitter != null) {
                    PcTodoListFragment.this.mRemindTimeSettingDialogEmitter.onNext(false);
                    PcTodoListFragment.this.mRemindTimeSettingDialogEmitter.onComplete();
                }
            }

            @Override // com.miui.pc.feature.todo.PcTodoListFragment.OnTimeSetListener
            public void onTimeSet(RemindTimeConfig remindTimeConfig) {
                if (PcTodoListFragment.this.mPresenter.getMIsInEdit()) {
                    PcTodoListFragment.this.mPresenter.setRemindTimeInEditMode(remindTimeConfig);
                    return;
                }
                PcTodoListFragment.this.mPresenter.setRemindTimeInDragMode(remindTimeConfig);
                if (PcTodoListFragment.this.mRemindTimeSettingDialogEmitter != null) {
                    PcTodoListFragment.this.mRemindTimeSettingDialogEmitter.onNext(true);
                    PcTodoListFragment.this.mRemindTimeSettingDialogEmitter.onComplete();
                }
            }
        });
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public PcTodoListAdapter getListAdapter() {
        return this.mTodoListAdapter;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public RecyclerView getListView() {
        return this.mRvTodo;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onMarkTodo(int i) {
        this.mBindContext.setMarkIndex(i);
        this.mBindContext.setLastMarkIndex(i);
        this.mRvLinearLayoutManager.scrollToPositionWithOffset(i, 0);
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodo(int i, boolean z) {
        this.mTodoListAdapter.updateData(i);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataLoaded() {
        ActionMode actionMode;
        if (!this.mInSearchMode) {
            this.mTodoListAdapter.notifyDataSetChanged();
            onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        } else {
            refreshSearchResult();
        }
        if (!isAdded() || (actionMode = this.mActionMode) == null) {
            return;
        }
        updateActionModeButton2(actionMode);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataEmpty(boolean z) {
        if (this.mListBlankView == null) {
            this.mListBlankView = new PcListBlankView(getActivity(), this.mRootView.findViewById(R.id.blankview_container), R.string.data_empty_todo, "pc_ic_todo_content_blank");
        }
        this.mListBlankView.onDataEmpty(z);
        if (z) {
            if (this.mRvTodo.getVisibility() != 8) {
                this.mRvTodo.postDelayed(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDataEmpty$5();
                    }
                }, 300L);
            }
        } else if (this.mRvTodo.getVisibility() != 0) {
            this.mRvTodo.setVisibility(0);
            this.mBlankViewContainer.setVisibility(8);
            this.mSpringBackLayout.setTarget(this.mRvTodo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDataEmpty$5() {
        this.mRvTodo.setVisibility(8);
        this.mBlankViewContainer.setVisibility(0);
        this.mSpringBackLayout.setTarget(this.mBlankViewContainer);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoFinishState(boolean z, boolean z2, int i) {
        if (!this.mInSearchMode) {
            this.mTodoListAdapter.updateFinishData(z, i);
        }
        refreshSearchResult();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoData() {
        if (this.mInSearchMode) {
            refreshSearchResult();
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDeleteTodo() {
        refreshSearchResult();
        this.mTodoListAdapter.notifyDataSetChanged();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onDeleteTodos() {
        finishActionMode();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void refreshTodoList() {
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onShowSearchResult(List<TodoEntity> list) {
        if (this.mTodoListAdapter == null) {
            Logger.INSTANCE.w(TAG, "onShowSearchResult mTodoListAdapter is null");
            return;
        }
        PcTodoEditController pcTodoEditController = this.mTodoEditController;
        if (pcTodoEditController != null && pcTodoEditController.isShow()) {
            exitEdit(false);
            this.mTodoEditController.dismiss();
        }
        this.mTodoListAdapter.showSearchResult(list);
        if (this.mInSearchMode) {
            this.mSearchResult.setText(getResources().getQuantityString(R.plurals.pc_todo_search_result, list.size(), Integer.valueOf(list.size())));
        }
        onDataEmpty(list.size() == 0);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void exitEdit(boolean z) {
        saveTodoData(true);
    }

    private void saveTodoData(boolean z) {
        this.mPresenter.exitEditMode(z, false);
    }

    private void refreshSearchResult() {
        if (this.mInSearchMode) {
            this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
        }
    }

    private void showTodoPadEditGroup(TodoBaseEntity todoBaseEntity, boolean z) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        Window window = getActivity().getWindow();
        if (window != null) {
            window.setSoftInputMode(34);
        }
        PcTodoEditController pcTodoEditControllerCreatePcEditController = createPcEditController(todoBaseEntity);
        this.mTodoEditController = pcTodoEditControllerCreatePcEditController;
        pcTodoEditControllerCreatePcEditController.setContentData(todoBaseEntity);
        pcTodoEditControllerCreatePcEditController.show(this.mRootView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteCheckedItems() {
        long[] checkedItemIndexs = this.mRvTodoWrapper.getCheckedItemIndexs();
        this.mRvTodoWrapper.clearChoices();
        this.mPresenter.deleteTodos(checkedItemIndexs);
    }

    /* JADX INFO: renamed from: com.miui.pc.feature.todo.PcTodoListFragment$7, reason: invalid class name */
    class AnonymousClass7 implements TodoItemOperationListener {
        AnonymousClass7() {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public Observable<Boolean> onMoved(final TodoEntity todoEntity, final int i, final int i2) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$7$$ExternalSyntheticLambda0
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f$0.lambda$onMoved$0(todoEntity, i, i2, observableEmitter);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMoved$0(TodoEntity todoEntity, int i, int i2, ObservableEmitter observableEmitter) throws Exception {
            PcTodoListFragment.this.mRemindTimeSettingDialogEmitter = observableEmitter;
            if (PcTodoListFragment.this.mPresenter.moveTodo(todoEntity, i, i2)) {
                return;
            }
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public boolean onSimpleMoved(int i, int i2) {
            if (!PcTodoListFragment.this.mRvTodoWrapper.isInActionMode()) {
                return true;
            }
            PcTodoListFragment.this.mRvTodoWrapper.swapCheckedStates(i, i2);
            return true;
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onLongClick(View view) {
            if (view == null || PcTodoListFragment.this.mRvTodoWrapper.isInActionMode() || !PcTodoListFragment.this.canEnterEditMode()) {
                return;
            }
            PcTodoListFragment.this.mItemTouchHelperCallBack.setEnableDrag(true);
            PcTodoListFragment.this.mRvTodoWrapper.setItemChecked(view, true);
            SwipeMenuLayout.sForbidSwipe = true;
            PcTodoListFragment.this.mRvTodoWrapper.startActionMode(PcTodoListFragment.this.createEditModeCallback());
            if (PcTodoListFragment.this.mBtnDragItemStateListener != null) {
                PcTodoListFragment.this.mBtnDragItemStateListener.onStartDrag(PcTodoListFragment.this.mRvTodo.getChildViewHolder(view));
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onClick(View view) {
            TodoEntity childItem;
            PcTodoListFragment.this.mTodoFastCreateController.clearEditorFocus();
            if (PcTodoListFragment.this.mRvTodo.isLayoutSuppressed()) {
                return;
            }
            PcTodoListFragment.this.cancelMarkTodo();
            if (PcTodoListFragment.this.mRvTodoWrapper.isInActionMode()) {
                onSelected(view);
                return;
            }
            if (PcTodoListFragment.this.mTodoListAdapter.isInDrag() || PcTodoListFragment.this.mTodoListAdapter == null || PcTodoListFragment.this.mPresenter.getMIsInEdit()) {
                return;
            }
            int childLayoutPosition = PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view);
            PcTodoListFragment pcTodoListFragment = PcTodoListFragment.this;
            pcTodoListFragment.mEditingTodoVh = (PcTodoChildItemVh) pcTodoListFragment.mRvTodo.getChildViewHolder(view);
            int itemViewType = PcTodoListFragment.this.mTodoListAdapter.getItemViewType(childLayoutPosition);
            if ((itemViewType == 0 || itemViewType == 3) && (childItem = PcTodoListFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition)) != null) {
                PcTodoListFragment.this.mPresenter.enterEditMode(childItem, childLayoutPosition, false, false);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onSelected(View view) {
            PcTodoListFragment.this.mTodoFastCreateController.clearEditorFocus();
            if (PcTodoListFragment.this.mRvTodoWrapper.isInActionMode()) {
                PcTodoListFragment.this.mRvTodoWrapper.toggleItemChecked(view);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onDelete(View view) {
            int childLayoutPosition = PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view);
            PcTodoListFragment.this.cancelMarkTodo();
            TodoEntity childItem = PcTodoListFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                PcTodoListFragment.this.mRvTodo.setMenuSwiping(false);
                TodoUtils.setSwiping(false, "pcTodoListFragment-onDel");
                PcTodoListFragment.this.mPresenter.deleteTodo(childItem, childLayoutPosition);
                PcTodoListFragment.this.mTodoListAdapter.deleteData(childLayoutPosition, 1);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onFinished(View view) {
            int childLayoutPosition = PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((PcTodoListFragment.this.mBindContext.pLastClickCheckTime == null || jCurrentTimeMillis - PcTodoListFragment.this.mBindContext.pLastClickCheckTime.longValue() > 1000) && PcTodoListFragment.this.mRvTodo.isAnimating() && PcTodoListFragment.this.mRvTodo.getItemAnimator() != null) {
                PcTodoListFragment.this.mRvTodo.getItemAnimator().endAnimations();
            }
            PcTodoListFragment.this.mBindContext.pLastClickCheckTime = Long.valueOf(jCurrentTimeMillis);
            if (PcTodoListFragment.this.mRvTodo.isLayoutSuppressed() || PcTodoListFragment.this.mRvTodo.isAnimating()) {
                return;
            }
            if (!PcTodoListFragment.this.mRvTodo.getChildViewHolder(view).isRecyclable()) {
                Logger.INSTANCE.d("DoFinish", " item recycleable is false, don't work");
                return;
            }
            PcTodoListFragment.this.cancelMarkTodo();
            TodoEntity childItem = PcTodoListFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                childItem.setIsFinish(1);
                if (PcTodoListFragment.this.mTodoListAdapter.isAllTodoWillFinished() && childItem.getRemindRepeatType() == 0) {
                    PcTodoListFragment.this.isInFlowAnim = true;
                    PcTodoListFragment.this.doAllFinishAnim(childItem, childLayoutPosition, view);
                    return;
                } else {
                    PcTodoListFragment.this.notifyAction(1, null);
                    PcTodoListFragment.this.doFinishAnim(childItem, childLayoutPosition, view);
                    return;
                }
            }
            PcTodoListFragment.this.mRvTodo.suppressLayout(false);
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onUnFinished(View view) {
            final int childLayoutPosition = PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view);
            final DataGroupInfo itemInfo = PcTodoListFragment.this.mTodoListAdapter.getItemInfo(childLayoutPosition);
            view.postDelayed(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListFragment$7$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUnFinished$1(childLayoutPosition, itemInfo);
                }
            }, 50L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnFinished$1(int i, DataGroupInfo dataGroupInfo) {
            if (PcTodoListFragment.this.mTodoListAdapter != null) {
                TodoEntity childItem = PcTodoListFragment.this.mTodoListAdapter.getChildItem(i);
                if (childItem != null) {
                    PcTodoListFragment.this.mPresenter.setTodoFinishState(childItem, dataGroupInfo.mSubPosition, i);
                }
                PcTodoListFragment.this.mRvTodo.suppressLayout(false);
                PcTodoListFragment.this.refreshTodoList();
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onToggleGroup(View view) {
            PcTodoListFragment.this.mTodoFastCreateController.clearEditorFocus();
            PcTodoListFragment.this.mTodoListAdapter.toggleGroupExpand(PcTodoListFragment.this.mRvTodo.getChildLayoutPosition(view));
        }
    }

    protected boolean canEnterEditMode() {
        return !this.mInSearchMode;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        if (!TextUtils.isEmpty(str)) {
            return true;
        }
        exitSearchMode();
        return true;
    }

    public void enterSearchMode(String str) {
        this.mInSearchMode = true;
        this.mPresenter.enterSearchMode();
        this.mBindContext.setSearchToken(str);
        this.mSearchResultGroup.setVisibility(0);
        this.mTvCategoryTitle.setVisibility(8);
        this.mTodoFastCreateController.hide();
        this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
    }

    public void exitSearchMode() {
        if (this.mInSearchMode) {
            this.mInSearchMode = false;
            this.mBindContext.setSearchToken("");
            TodoListContract.Presenter presenter = this.mPresenter;
            if (presenter != null) {
                presenter.exitSearchMode();
            }
            this.mSearchResultGroup.setVisibility(8);
            this.mTvCategoryTitle.setVisibility(0);
            if (this.mRvTodo.getVisibility() != 0) {
                this.mRvTodo.setVisibility(0);
                this.mBlankViewContainer.setVisibility(8);
                this.mSpringBackLayout.setTarget(this.mRvTodo);
            }
            if (getTodoListType() != 1) {
                this.mTodoFastCreateController.show();
            }
            PcTodoListAdapter pcTodoListAdapter = this.mTodoListAdapter;
            if (pcTodoListAdapter != null) {
                pcTodoListAdapter.exitSearchMode();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded()) {
            if (z) {
                if (this.mNestedHeaderLayout.isTriggerOpen()) {
                    this.mNestedHeaderLayout.setAutoTriggerClose(true);
                }
                this.mNestedHeaderLayout.setTriggerViewVisible(false);
                this.mSearchModePaddingTopExtra = getResources().getDimension(R.dimen.search_result_list_padding_top);
                return;
            }
            this.mRvTodo.closeOpenedItem();
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean z, float f) {
        if (isAdded()) {
            if (!z) {
                f = 1.0f - f;
            }
            float f2 = f * this.mSearchModePaddingTopExtra;
            try {
                View view = this.mRootView;
                view.setPadding(view.getPaddingLeft(), (int) f2, this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded() && !z) {
            this.mNestedHeaderLayout.setTriggerViewVisible(true);
            this.mNestedHeaderLayout.setHeaderViewVisible(true);
            this.mSearchModePaddingTopExtra = 0.0f;
            try {
                View view = this.mRootView;
                view.setPadding(view.getPaddingLeft(), 0, this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
        if (isAdded() && UIUtils.isInFullWindowGestureMode(getContext())) {
            getActivity().getWindow().clearFlags(DataSpace.RANGE_FULL);
        }
        this.mInSearchMode = true;
        this.mPresenter.enterSearchMode();
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        if (isAdded() && UIUtils.isInFullWindowGestureMode(getContext())) {
            getActivity().getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        this.mInSearchMode = false;
        this.mPresenter.exitSearchMode();
        this.mTodoListAdapter.exitSearchMode();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    @Override // com.miui.todo.base.IBaseContract.View
    public void setPresenter(IBaseTodoListContract.Presenter presenter) {
        this.mPresenter = (TodoListContract.Presenter) Preconditions.checkNotNull(presenter);
    }

    public EditableTodoListWrapper.MultiChoiceModeListener createEditModeCallback() {
        return new EditModeCallback();
    }

    protected class EditModeCallback implements EditableTodoListWrapper.MultiChoiceModeListener {
        @Override // com.miui.pc.feature.todo.EditableTodoListWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(boolean z) {
        }

        @Override // com.miui.pc.feature.todo.EditableTodoListWrapper.MultiChoiceModeListener
        public void onItemCheckedStateChanged(int i, long j, boolean z) {
        }

        protected EditModeCallback() {
        }

        @Override // com.miui.pc.feature.todo.EditableTodoListWrapper.MultiChoiceModeListener
        public void onStartEditMode() {
            PcTodoListFragment.this.mTodoListAdapter.setEditMode(true);
            PcTodoListFragment.this.mTodoListAdapter.notifyDataSetChanged();
        }

        @Override // com.miui.pc.feature.todo.EditableTodoListWrapper.MultiChoiceModeListener
        public void onFinishEditMode() {
            PcTodoListFragment.this.mTodoListAdapter.setEditMode(false);
            PcTodoListFragment.this.mTodoListAdapter.notifyDataSetChanged();
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
            return;
        }
        editActionMode.setButton(16908314, this.mRvTodoWrapper.isAllItemsChecked() ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
    }

    public boolean finishActionMode() {
        boolean zIsInActionMode = this.mRvTodoWrapper.isInActionMode();
        this.mRvTodoWrapper.finishActionMode();
        return zIsInActionMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelMarkTodo() {
        if (this.mBindContext.getLastMarkIndex() != -1) {
            this.mTodoListAdapter.notifyItemChanged(this.mBindContext.getLastMarkIndex());
            this.mBindContext.setLastMarkIndex(-1);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void finishTodoInData(TodoEntity todoEntity, int i) {
        TodoEntity childItem = this.mTodoListAdapter.getChildItem(i);
        DataGroupInfo itemInfo = this.mTodoListAdapter.getItemInfo(i);
        if (childItem != null && todoEntity != null) {
            this.mPresenter.updateTodo(todoEntity, this.mTodoListAdapter.getItemInfo(i).mSubPosition, true);
        } else if (childItem != null) {
            this.mPresenter.setTodoFinishState(childItem, itemInfo.mSubPosition, i);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doFinishAnim(TodoEntity todoEntity, int i, View view) {
        TodoAnimHelper.doFinishAnim(this, todoEntity, i, view, new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.9
            @Override // java.lang.Runnable
            public void run() {
                PcTodoListFragment.this.mPresenter.getAndUpdateDataToView();
                PcTodoListFragment.this.notifyAction(2, null);
            }
        });
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doAllFinishAnim(TodoEntity todoEntity, int i, View view) {
        ViewGroup viewGroup = (ViewGroup) getView();
        FrameLayout frameLayout = new FrameLayout(getContext());
        TodoAnimHelper.doAllFinishAnim(this, todoEntity, i, view, viewGroup, frameLayout, PcTodoItemForAllFinishVh.newInstance(frameLayout), new TodoAnimHelper.Listener() { // from class: com.miui.pc.feature.todo.PcTodoListFragment.10
            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onStart(DoneSparkAnim doneSparkAnim) {
                PcTodoListFragment.this.isInFlowAnim = true;
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onEnd() {
                PcTodoListFragment.this.isInFlowAnim = false;
                PcTodoListFragment.this.notifyAction(2, null);
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onCancel() {
                PcTodoListFragment.this.isInFlowAnim = false;
                PcTodoListFragment.this.notifyAction(2, null);
            }
        });
    }
}
