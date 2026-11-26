package com.miui.floatwindow.feature.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.SoftKeyboardHelper;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.floatwindow.frame.IFloatWindowView;
import com.miui.floatwindow.view.FwFullScrollView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.OnSwipeItemListener;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.feature.todolist.TodoItemOperationListener;
import com.miui.todo.feature.todolist.WorkingTodo;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoAnimHelper;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.RemindTimePickerDialog;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import defpackage.R2;
import io.reactivex.Observable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.app.AlertDialog;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.recyclerview.widget.MiuiScaleItemAnimator;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoListView implements IFloatWindowView, FwTodoListContract.View {
    private static final int FAST_CLICK_ID = -1;
    static final String TAG = "FwTodoListView";
    private final FwTodoListAdapterBindContext mBindContext;
    private final View mBlankView;
    private final View mBtnAddTodo;
    private final Context mContext;
    private AlertDialog mCtaDialog;
    private int mDownX;
    private int mDownY;
    protected EmptyStateView mListBlankView;
    private IFloatWindowView mParentHelper;
    private final View mParentView;
    private FwTodoListContract.Presenter mPresenter;
    private final View mRootView;
    private final TodoLayoutManager mRvLinearLayoutManager;
    private final FwTodoRecyclerView mRvTodo;
    private RecyclerView.ItemAnimator mRvTodoAnimator;
    private final FwFullScrollView mScrollView;
    private RemindTimePickerDialog mTimePickerDialog;
    private FwTodoListAdapter mTodoListAdapter;
    private boolean mNeedShowDialog = true;
    private boolean isEditMode = false;
    private final TodoItemOperationListener mItemOperationListener = new AnonymousClass1();
    private final OnSwipeItemListener mSwipeItemListener = new OnSwipeItemListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.2
        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeStart(int i, boolean z) {
            FwTodoListView.this.mRvTodo.setMenuSwiping(true);
        }

        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeStop(int i, boolean z) {
            FwTodoListView.this.mRvTodo.setMenuSwiping(false);
        }

        @Override // com.miui.todo.base.OnSwipeItemListener
        public void onSwipeFinish(int i, boolean z) {
            FwTodoListView.this.mRvTodo.setMenuSwiping(false);
        }
    };
    private final View.OnClickListener mItemOtherViewClickListener = new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.ll_expand_group) {
                if (FwTodoListView.this.mPresenter.getMIsInEdit()) {
                    FwTodoListView.this.exitEditing(true);
                }
            } else {
                if (id != R.id.normal_item_group) {
                    if (id == R.id.rich_editor && (view instanceof EditText)) {
                        EditText editText = (EditText) view;
                        if (editText.isCursorVisible()) {
                            return;
                        }
                        editText.setCursorVisible(true);
                        return;
                    }
                    return;
                }
                FwTodoListView.this.mRvTodo.closeOpenedItem();
                if (FwTodoListView.this.mPresenter.getMIsInEdit()) {
                    FwTodoListView.this.exitEditing(true);
                } else {
                    FloatWindowManagerAgent.changeFullLayoutToGuideService();
                }
            }
        }
    };
    private final OnItemEditListener mItemEditListener = new OnItemEditListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.4
        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onEnterEdit(TodoEntity todoEntity, int i, FwTodoChildItemVh fwTodoChildItemVh, boolean z) {
            if (Utils.isFastClick(-1, 300L)) {
                return;
            }
            fwTodoChildItemVh.itemView.getLocationInWindow(new int[2]);
            if (fwTodoChildItemVh.itemView.getTop() < 0) {
                FwTodoListView.this.mRvTodo.scrollToPosition(fwTodoChildItemVh.getLayoutPosition());
            }
            if (FwTodoListView.this.mRvTodo.isLayoutSuppressed() || FwTodoListView.this.mTodoListAdapter.isInDrag() || FwTodoListView.this.mTodoListAdapter == null) {
                return;
            }
            if (FwTodoListView.this.mPresenter.getMIsInEdit()) {
                FwTodoListView.this.exitEditing(true);
                return;
            }
            FwTodoListView.this.mBindContext.refreshEditView(fwTodoChildItemVh);
            if (todoEntity != null) {
                FwTodoListView.this.mBindContext.setEditContent(todoEntity, todoEntity.getId());
            } else {
                FwTodoListView.this.mBindContext.setEditContent(null, 0L);
            }
            FwTodoListView.this.mPresenter.enterEditMode(todoEntity, i, false, z);
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onTextChanged(FwTodoChildItemVh fwTodoChildItemVh, CharSequence charSequence) {
            if (FwTodoListView.this.mPresenter.getMWorkingTodo() != null) {
                FwTodoListView.this.mPresenter.getMWorkingTodo().setContent(charSequence.toString());
            }
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onAudioTextChanged(String str) {
            if (FwTodoListView.this.mPresenter.getMWorkingTodo() == null) {
                return;
            }
            FwTodoListView.this.mPresenter.getMWorkingTodo().setContent(str);
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onAudioFileCreated(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            FwTodoListView.this.mPresenter.getMWorkingTodo().setAudioFileName(str);
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onSetRemindTime(boolean z) {
            if (!z) {
                FwTodoListView.this.mPresenter.setRemindCustom();
            } else {
                FwTodoListView.this.mPresenter.cancelRemindCustom();
            }
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onTodoTypeChanged(int i) {
            if (FwTodoListView.this.mPresenter.getMWorkingTodo() != null) {
                FwTodoListView.this.mPresenter.getMWorkingTodo().setListType(i);
                FwTodoListView.this.mPresenter.getMWorkingTodo().setPlaint(TodoUtils.getString(R.string.todo_list));
            }
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onSubTodoChanged(String str) {
            WorkingTodo workingTodo = FwTodoListView.this.mPresenter.getMWorkingTodo();
            if (workingTodo != null) {
                workingTodo.setContent(str);
                if (workingTodo.getListType() == 1) {
                    SubModeImpl subModeImpl = new SubModeImpl(str);
                    int finishOperation = subModeImpl.getFinishOperation();
                    if (finishOperation != 2) {
                        workingTodo.setFinishType(finishOperation);
                    }
                    FwTodoListView.this.mPresenter.getMWorkingTodo().setPlaint(subModeImpl.getTitle());
                }
            }
        }

        @Override // com.miui.floatwindow.feature.todo.OnItemEditListener
        public void onExitEdit(boolean z) {
            Logger.INSTANCE.d(FwTodoListView.TAG, "onExitEdit");
            if (FwTodoListView.this.mPresenter == null || FwTodoListView.this.mPresenter.getMWorkingTodo() == null) {
                Logger.INSTANCE.d(FwTodoListView.TAG, "presenter or workingTodo is null");
                return;
            }
            TodoEntity todoData = FwTodoListView.this.mPresenter.getMWorkingTodo().getTodoData();
            if (todoData.getListType() == 1) {
                SubModeImpl subModeImpl = new SubModeImpl(todoData.getContent());
                List<SubTodoEntity> list = subModeImpl.getSubTodoList().getList();
                for (int size = list.size() - 1; size >= 0; size--) {
                    String content = list.get(size).getContent();
                    if (TextUtils.isEmpty(content) || CharUtils.ZERO_WIDTH_SPACE.equals(content)) {
                        subModeImpl.delete(size);
                    }
                }
                if (subModeImpl.getSubTodoSize() > 0) {
                    FwTodoListView.this.mPresenter.getMWorkingTodo().setContent(subModeImpl.getContent());
                } else {
                    FwTodoListView.this.mPresenter.getMWorkingTodo().setContent(subModeImpl.getTitle());
                    FwTodoListView.this.mPresenter.getMWorkingTodo().setListType(0);
                }
            }
            FwTodoListView.this.mPresenter.exitEditMode(z, true);
        }
    };
    private final OnDragItemStateListener mDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.5
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            FwTodoListView.this.mRvTodo.setInDragMode(true);
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
            FwTodoListView.this.mRvTodo.setInDragMode(false);
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
            FwTodoListView.this.mRvTodo.setInDragMode(false);
        }
    };

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onShowSearchResult(List<TodoEntity> list) {
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void refreshTodoList() {
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.todo.FwTodoListView$1, reason: invalid class name */
    class AnonymousClass1 implements TodoItemOperationListener {
        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onClick(View view) {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onLongClick(View view) {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public Observable<Boolean> onMoved(TodoEntity todoEntity, int i, int i2) {
            return null;
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onSelected(View view) {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public boolean onSimpleMoved(int i, int i2) {
            return true;
        }

        AnonymousClass1() {
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onDelete(View view) {
            int childLayoutPosition = FwTodoListView.this.mRvTodo.getChildLayoutPosition(view);
            TodoEntity childItem = FwTodoListView.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                FwTodoListView.this.mPresenter.deleteTodo(childItem, childLayoutPosition);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onFinished(View view) {
            if (FwTodoListView.this.mBindContext.getEditView() != null) {
                FwTodoListView.this.exitEditing(true);
                return;
            }
            int childLayoutPosition = FwTodoListView.this.mRvTodo.getChildLayoutPosition(view);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((FwTodoListView.this.mBindContext.pLastClickCheckTime == null || jCurrentTimeMillis - FwTodoListView.this.mBindContext.pLastClickCheckTime.longValue() > 1000) && FwTodoListView.this.mRvTodo.isAnimating() && FwTodoListView.this.mRvTodo.getItemAnimator() != null) {
                FwTodoListView.this.mRvTodo.getItemAnimator().endAnimations();
            }
            FwTodoListView.this.mBindContext.pLastClickCheckTime = Long.valueOf(jCurrentTimeMillis);
            if (FwTodoListView.this.mRvTodo.isLayoutSuppressed() || FwTodoListView.this.mRvTodo.isAnimating()) {
                return;
            }
            TodoEntity childItem = FwTodoListView.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                childItem.setIsFinish(1);
                if (!LiteUtils.isLiteOrMiddle() && FwTodoListView.this.mTodoListAdapter.isAllTodoWillFinished() && childItem.getRemindRepeatType() == 0) {
                    FwTodoListView.this.doAllFinishAnim(childItem, childLayoutPosition, view);
                    return;
                } else {
                    FwTodoListView.this.doFinishAnim(childItem, childLayoutPosition, view);
                    return;
                }
            }
            FwTodoListView.this.mRvTodo.suppressLayout(false);
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onUnFinished(View view) {
            if (FwTodoListView.this.mBindContext.getEditView() != null) {
                FwTodoListView.this.exitEditing(true);
                return;
            }
            final int childLayoutPosition = FwTodoListView.this.mRvTodo.getChildLayoutPosition(view);
            final DataGroupInfo itemInfo = FwTodoListView.this.mTodoListAdapter.getItemInfo(childLayoutPosition);
            view.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUnFinished$0(childLayoutPosition, itemInfo);
                }
            }, 50L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnFinished$0(int i, DataGroupInfo dataGroupInfo) {
            if (FwTodoListView.this.mTodoListAdapter != null) {
                TodoEntity childItem = FwTodoListView.this.mTodoListAdapter.getChildItem(i);
                if (childItem != null) {
                    FwTodoListView.this.mPresenter.setTodoFinishState(childItem, dataGroupInfo.mSubPosition, i);
                }
                FwTodoListView.this.mRvTodo.suppressLayout(false);
                FwTodoListView.this.refreshTodoList();
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onToggleGroup(View view) {
            if (FwTodoListView.this.mPresenter.getMIsInEdit()) {
                FwTodoListView.this.exitEditing(true);
            } else {
                FwTodoListView.this.mTodoListAdapter.toggleGroupExpand(FwTodoListView.this.mRvTodo.getChildLayoutPosition(view));
            }
        }
    }

    public FwTodoListView(View view) {
        this.mRvTodoAnimator = null;
        this.mListBlankView = null;
        this.mParentView = view;
        View viewFindViewById = view.findViewById(R.id.todo_group);
        this.mRootView = viewFindViewById;
        this.mScrollView = (FwFullScrollView) view.findViewById(R.id.data_content);
        FwTodoRecyclerView fwTodoRecyclerView = (FwTodoRecyclerView) view.findViewById(R.id.rv_todo);
        this.mRvTodo = fwTodoRecyclerView;
        this.mListBlankView = (EmptyStateView) view.findViewById(R.id.blankview_container);
        this.mContext = NoteApp.getInstance();
        View viewFindViewById2 = viewFindViewById.findViewById(R.id.iv_data_add);
        this.mBtnAddTodo = viewFindViewById2;
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (FwTodoListView.this.mRvTodo.isLayoutSuppressed() || FwTodoListView.this.mTodoListAdapter.isInDrag()) {
                    return;
                }
                if (FwTodoListView.this.mBlankView.getVisibility() == 0) {
                    FwTodoListView.this.mRvTodo.setVisibility(0);
                    FwTodoListView.this.mBlankView.setVisibility(8);
                }
                FwTodoListView.this.mPresenter.addNewTodo(false);
            }
        });
        Folme.useAt(viewFindViewById2).touch().handleTouchOf(viewFindViewById2, new AnimConfig[0]);
        TodoLayoutManager todoLayoutManager = new TodoLayoutManager(getContext());
        this.mRvLinearLayoutManager = todoLayoutManager;
        fwTodoRecyclerView.setLayoutManager(todoLayoutManager);
        fwTodoRecyclerView.setClickable(true);
        fwTodoRecyclerView.setSpringEnabled(false);
        fwTodoRecyclerView.setNestedScrollingEnabled(true);
        fwTodoRecyclerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.lambda$new$0(view2, motionEvent);
            }
        });
        MiuiScaleItemAnimator miuiScaleItemAnimator = new MiuiScaleItemAnimator();
        this.mRvTodoAnimator = miuiScaleItemAnimator;
        fwTodoRecyclerView.setItemAnimator(miuiScaleItemAnimator);
        FwTodoListAdapterBindContext fwTodoListAdapterBindContext = new FwTodoListAdapterBindContext();
        this.mBindContext = fwTodoListAdapterBindContext;
        fwTodoListAdapterBindContext.setRecyclerView(fwTodoRecyclerView);
        fwTodoListAdapterBindContext.maxContentPanelWidth = setMaxContentWidth();
        fwTodoRecyclerView.setVisibility(0);
        fwTodoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.7
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (BaseNoteApp.getInstance().getTodoNumLimit() - FwTodoListView.this.mRvLinearLayoutManager.findFirstVisibleItemPosition() < 20) {
                    NoteApp.getInstance().addTodoNumLimit();
                    if (FwTodoListView.this.mPresenter != null) {
                        FwTodoListView.this.mPresenter.getAndUpdateDataToView();
                    }
                }
            }
        });
        View viewFindViewById3 = viewFindViewById.findViewById(R.id.blankview_container);
        this.mBlankView = viewFindViewById3;
        viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FloatWindowManagerAgent.changeFullLayoutToGuideService();
            }
        });
        viewFindViewById3.setVisibility(0);
        viewFindViewById.findViewById(R.id.head_blank).setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FloatWindowManagerAgent.changeFullLayoutToGuideService();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownX = (int) motionEvent.getX();
            this.mDownY = (int) motionEvent.getY();
        } else {
            if (action != 1 || Utils.isFastClick(-1, 300L) || this.mDownX != ((int) motionEvent.getX()) || this.mDownY != ((int) motionEvent.getY()) || this.mRvTodo.closeOpenedItem()) {
                return false;
            }
            if (this.mPresenter.getMIsInEdit()) {
                exitEditing(true);
                TodoUtils.hideSoftInput(view);
            } else {
                FloatWindowManagerAgent.changeFullLayoutToGuideService();
            }
        }
        return false;
    }

    public void setParentHelper(IFloatWindowView iFloatWindowView) {
        this.mParentHelper = iFloatWindowView;
    }

    public void forceRefreshData() {
        Logger.INSTANCE.d(TAG, "forceRefreshData");
        this.mPresenter.getAndUpdateDataToView();
    }

    private int setMaxContentWidth() {
        return ((((DisplayUtils.getRealWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.todo_item_margin_start) * 2)) - this.mContext.getResources().getDimensionPixelSize(R.dimen.todo_item_content_bg_margin_start)) - this.mContext.getResources().getDimensionPixelSize(R.dimen.todo_item_content_bg_margin_end)) - UIUtils.dip2px(this.mContext, 40.0f)) - this.mContext.getResources().getDrawable(R.drawable.ic_list_expand).getIntrinsicWidth();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preToFullWindow() {
        NoteApp.getInstance().resetTodoNumLimitForFloat();
        this.mRvTodo.setItemAnimator(null);
        this.mPresenter.subscribe();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preChangeToGuide() {
        this.mRvTodo.closeOpenedItem();
        RemindTimePickerDialog remindTimePickerDialog = this.mTimePickerDialog;
        if (remindTimePickerDialog != null && remindTimePickerDialog.isShowing()) {
            this.mTimePickerDialog.dismiss();
        }
        this.mTimePickerDialog = null;
        if (this.mPresenter.getMIsInEdit()) {
            exitEditing(false);
            NoteApp.getInstance().getContentResolver().notifyChange(Uri.parse(TodoProvider.URI_TODO_PRIVATE), (ContentObserver) null, false);
        }
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onFullWindowShow() {
        this.mRvTodo.setItemAnimator(this.mRvTodoAnimator);
        Logger.INSTANCE.i(TAG, "rv child count " + this.mRvTodo.getChildCount());
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public int getVisibleViewWidth() {
        return DisplayUtils.getRealWidth(this.mContext);
    }

    private void initAdapterIfNeeded() {
        if (this.mTodoListAdapter == null) {
            FwTodoListAdapter fwTodoListAdapter = new FwTodoListAdapter(getContext(), this.mPresenter.getMSubTodoListListener());
            this.mTodoListAdapter = fwTodoListAdapter;
            fwTodoListAdapter.setOtherViewClickListener(this.mItemOtherViewClickListener);
            this.mTodoListAdapter.setOnItemEditListener(this.mItemEditListener);
            this.mTodoListAdapter.setOnSwipeItemListener(this.mSwipeItemListener);
            this.mTodoListAdapter.setOnDragItemListener(this.mDragItemStateListener, null);
            this.mTodoListAdapter.setItemOperationListener(this.mItemOperationListener);
            this.mTodoListAdapter.setBindContext(this.mBindContext);
            this.mTodoListAdapter.setPresenter(this.mPresenter);
            this.mTodoListAdapter.updateData();
            new ItemTouchHelper(new FwTodoListItemTouchHelperCallback(this.mTodoListAdapter, this.mBindContext)).attachToRecyclerView(this.mRvTodo);
        }
    }

    @Override // com.miui.todo.base.IBaseContract.View
    public void setPresenter(IBaseTodoListContract.Presenter presenter) {
        this.mPresenter = (FwTodoListContract.Presenter) Preconditions.checkNotNull(presenter);
        initAdapterIfNeeded();
        this.mRvTodo.setAdapter(this.mTodoListAdapter);
        this.mBindContext.setListAdapter(this.mTodoListAdapter);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public Context getViewContext() {
        return NoteApp.getInstance();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onEnterEditMode(TodoBaseEntity todoBaseEntity, int i, boolean z, boolean z2) {
        if (this.mBindContext.getEditView() != null) {
            this.mBindContext.bindWorkingTodo(this.mPresenter.getMWorkingTodo());
            this.mBindContext.getEditView().enterEditMode(todoBaseEntity, z2, todoBaseEntity != null);
            SwipeMenuLayout.sForbidSwipe = true;
            this.isEditMode = true;
            if (z2) {
                this.mRvTodo.setEnableScroll(false);
            }
        }
        if (z2) {
            return;
        }
        enableCreateBtn(false);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onExitEditMode(int i, boolean z) {
        this.isEditMode = false;
        SwipeMenuLayout.sForbidSwipe = false;
        FwFullScrollView.sForbidScrollY = false;
        this.mRvTodo.setEnableScroll(true);
        this.mRvTodo.requestLayout();
        if (this.mBindContext.getEditView() != null) {
            this.mBindContext.setEditContent(null, -1L);
            this.mBindContext.getEditView().exitEditMode(true, z);
            this.mBindContext.refreshEditView(null);
        }
        if (i >= 0) {
            this.mTodoListAdapter.updateData(i);
        }
        if (this.mTodoListAdapter.getItemCount() == 0) {
            this.mRvTodo.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onExitEditMode$3();
                }
            }, 300L);
            this.mScrollView.scrollTo(0, 0);
        }
        enableCreateBtn(true);
        Utils.hideSoftInput(this.mRvTodo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onExitEditMode$3() {
        if (this.mRvTodo.getItemAnimator() == null) {
            this.mRvTodo.setItemAnimator(this.mRvTodoAnimator);
        }
        this.mBlankView.setVisibility(0);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        if (this.mBindContext.getEditView() != null) {
            this.mBindContext.getEditView().updateRemindCustomInfo(z, remindTimeConfig);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void showRemindTimePickerDialog(Long l, int i) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mRootView.getContext().getSystemService("input_method");
        RemindTimePickerDialog remindTimePickerDialog = new RemindTimePickerDialog(this.mContext, R.style.NoteTheme_RemindAlertDialog, new RemindTimePickerDialog.OnTimeSetListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.8
            @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
            public void onCancel() {
                if (FwTodoListView.this.mBindContext.getEditView() != null) {
                    FwTodoListView.this.mBindContext.getEditView().cancelSetRemindTime();
                }
            }

            @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
            public void onTimeSet(RemindTimePickerDialog remindTimePickerDialog2, RemindTimeConfig remindTimeConfig) {
                if (FwTodoListView.this.mPresenter.getMIsInEdit()) {
                    FwTodoListView.this.mPresenter.setRemindTimeInEditMode(remindTimeConfig);
                } else {
                    FwTodoListView.this.mPresenter.setRemindTimeInDragMode(remindTimeConfig);
                }
            }
        }, i);
        this.mTimePickerDialog = remindTimePickerDialog;
        remindTimePickerDialog.setEnableImmersive(true);
        if (l != null) {
            this.mTimePickerDialog.update(l.longValue());
        }
        this.mTimePickerDialog.getWindow().setType(R2.attr.preferenceCheckableItemSetMaskPaddingEnd);
        this.mTimePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.9
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                final View viewFindViewById = FwTodoListView.this.mTimePickerDialog.getWindow().findViewById(R.id.remind_picker_dialog);
                if (viewFindViewById != null) {
                    viewFindViewById.post(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (((int) (viewFindViewById.getWidth() / FwTodoListView.this.mContext.getResources().getDisplayMetrics().density)) > 394) {
                                try {
                                    Field declaredField = AlertDialog.class.getDeclaredField("mAlert");
                                    declaredField.setAccessible(true);
                                    Object obj = declaredField.get(FwTodoListView.this.mTimePickerDialog);
                                    if (obj != null) {
                                        Method declaredMethod = obj.getClass().getDeclaredMethod("updateImmersiveDialogPanel", new Class[0]);
                                        declaredMethod.setAccessible(true);
                                        declaredMethod.invoke(obj, new Object[0]);
                                    }
                                } catch (Exception e) {
                                    Logger.INSTANCE.e(FwTodoListView.TAG, "get reflection error: " + e);
                                }
                            }
                        }
                    });
                }
            }
        });
        View editTextView = this.mBindContext.getEditView().getEditTextView();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(editTextView);
        if (rootWindowInsets != null && rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
            SoftKeyboardHelper.listenKeyboardClose(getContext(), editTextView, new SoftKeyboardHelper.KeyboardCloseListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.10
                @Override // com.miui.common.tool.SoftKeyboardHelper.KeyboardCloseListener
                public void onEnd() {
                    if (FwTodoListView.this.mTimePickerDialog != null) {
                        FwTodoListView.this.mTimePickerDialog.show();
                    }
                }
            });
            inputMethodManager.hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
        } else {
            RemindTimePickerDialog remindTimePickerDialog2 = this.mTimePickerDialog;
            if (remindTimePickerDialog2 != null) {
                remindTimePickerDialog2.show();
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public FwTodoListAdapter getListAdapter() {
        return this.mTodoListAdapter;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public RecyclerView getListView() {
        return this.mRvTodo;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodo(final int i, final boolean z) {
        this.mTodoListAdapter.updateData(i);
        Logger.INSTANCE.d(TAG, "onUpdateTodo: updatePosition = " + i);
        this.mRvTodo.post(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onUpdateTodo$4(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodo$4(int i, boolean z) {
        View viewFindViewByPosition = this.mRvLinearLayoutManager.findViewByPosition(i);
        if (viewFindViewByPosition == null || !z) {
            return;
        }
        try {
            BaseTodoChildItemVh baseTodoChildItemVh = (BaseTodoChildItemVh) this.mRvTodo.getChildViewHolder(viewFindViewByPosition);
            if (baseTodoChildItemVh != null) {
                FwTodoListAdapter fwTodoListAdapter = this.mTodoListAdapter;
                if (fwTodoListAdapter.getRealType(fwTodoListAdapter.getItemId(i)) == 0) {
                    baseTodoChildItemVh.setTodoItemFinished();
                }
            }
        } catch (ClassCastException e) {
            Logger.INSTANCE.e(TAG, "fatal exception happened!!!!onUpdateTodo: " + e);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onAddNewTodo(boolean z) {
        if (z) {
            return;
        }
        this.mTodoListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.11
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                FwTodoListView.this.mRvLinearLayoutManager.scrollToPositionWithOffset(0, 0);
                try {
                    FwTodoListView.this.mTodoListAdapter.unregisterAdapterDataObserver(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onNewTodoAdded(TodoEntity todoEntity, int i, final boolean z) {
        if (todoEntity == null) {
            this.mTodoListAdapter.notifyDataSetChanged();
        } else {
            this.mTodoListAdapter.updateData(i);
        }
        this.mRvTodo.post(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.12
            @Override // java.lang.Runnable
            public void run() {
                BaseTodoChildItemVh baseTodoChildItemVh;
                View viewFindViewByPosition = FwTodoListView.this.mRvLinearLayoutManager.findViewByPosition(0);
                Logger.INSTANCE.d(FwTodoListView.TAG, "run: newTodo = " + viewFindViewByPosition + " isFinish = " + z);
                if (viewFindViewByPosition == null || !z || (baseTodoChildItemVh = (BaseTodoChildItemVh) FwTodoListView.this.mRvTodo.getChildViewHolder(viewFindViewByPosition)) == null) {
                    return;
                }
                baseTodoChildItemVh.setTodoItemFinished();
            }
        });
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoFinishState(boolean z, boolean z2, int i) {
        if (z) {
            this.mTodoListAdapter.notifyDataSetChanged();
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoData() {
        this.mRvTodo.stopScroll();
        if (this.mTodoListAdapter != null) {
            if (!this.mRvTodo.isComputingLayout() && !this.mRvTodo.isInLayout()) {
                FwTodoListAdapter fwTodoListAdapter = this.mTodoListAdapter;
                if (fwTodoListAdapter != null) {
                    fwTodoListAdapter.updateData();
                }
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onUpdateTodoData$5();
                    }
                });
            }
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodoData$5() {
        this.mTodoListAdapter.updateData();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDeleteTodo() {
        AudioUtils.playDeleteRingtone();
        FwTodoListAdapter fwTodoListAdapter = this.mTodoListAdapter;
        if (fwTodoListAdapter == null) {
            return;
        }
        fwTodoListAdapter.updateData();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        if (this.mTodoListAdapter.getItemCount() == 0) {
            this.mRvTodo.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDeleteTodo$6();
                }
            }, 300L);
            this.mScrollView.scrollTo(0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeleteTodo$6() {
        if (this.mRvTodo.getItemAnimator() == null) {
            this.mRvTodo.setItemAnimator(this.mRvTodoAnimator);
        }
        this.mBlankView.setVisibility(0);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataLoaded() {
        this.mTodoListAdapter.notifyDataSetChanged();
        if (this.mTodoListAdapter.getItemCount() != 0) {
            if (this.mBlankView.getVisibility() == 0) {
                this.mBlankView.setVisibility(8);
                if (this.mRvTodo.getItemAnimator() == null) {
                    this.mRvTodo.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onDataLoaded$7();
                        }
                    }, 100L);
                    return;
                }
                return;
            }
            return;
        }
        this.mRvTodo.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDataLoaded$8();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDataLoaded$7() {
        this.mRvTodo.setItemAnimator(this.mRvTodoAnimator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDataLoaded$8() {
        if (this.mRvTodo.getItemAnimator() == null) {
            this.mRvTodo.setItemAnimator(this.mRvTodoAnimator);
        }
        this.mBlankView.setVisibility(0);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataEmpty(boolean z) {
        this.mListBlankView.setIcon(R.drawable.todo_blank_icon);
        this.mListBlankView.setTitle(TodoUtils.getString(R.string.data_empty_todo));
        this.mListBlankView.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Context getContext() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEditing(boolean z) {
        this.mBindContext.setEditContent(null, -1L);
        this.mPresenter.exitEditMode(true, z);
    }

    private void enableCreateBtn(boolean z) {
        this.mBtnAddTodo.setEnabled(z);
    }

    public void showCtaDialog() {
        AlertDialog alertDialog = this.mCtaDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteApp.getInstance(), 2131952667);
            builder.setCancelable(false);
            builder.setTitle(R.string.cta_dialog_title);
            builder.setMessage(R.string.cta_dialog_message);
            builder.setPositiveButton(R.string.cta_confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.13
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    Logger.INSTANCE.i(FwTodoListView.TAG, "user agree to authorization");
                    PreferenceUtils.setCTAAccepted(true, FwTodoListView.this.getContext().getApplicationContext());
                    PreferenceUtils.setAllowXiaoai(NoteApp.getInstance(), true);
                    PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
                    CTAManager.getInstance().notifyAccept();
                    Bundle bundle = new Bundle();
                    bundle.putInt("push_type", 0);
                    SyncUtils.requestSync(NoteApp.getInstance(), bundle, true);
                }
            });
            builder.setNegativeButton(R.string.cta_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoListView.14
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    Logger.INSTANCE.i(FwTodoListView.TAG, "user deny to authorization");
                    PreferenceUtils.setCTAAccepted(false, FwTodoListView.this.getContext().getApplicationContext());
                    PreferenceUtils.setAllowXiaoai(NoteApp.getInstance(), false);
                    PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), false);
                    CTAManager.getInstance().notifyReject();
                }
            });
            this.mCtaDialog = builder.create();
            this.mCtaDialog.getWindow().setType(R2.attr.preferenceCheckableItemSetMaskPaddingEnd);
            this.mCtaDialog.show();
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
        TodoAnimHelper.doFinishAnim(this, todoEntity, i, view, null);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doAllFinishAnim(TodoEntity todoEntity, int i, View view) {
        ViewGroup viewGroup = (ViewGroup) this.mParentView.findViewById(R.id.full_float_view);
        FrameLayout frameLayout = new FrameLayout(getContext());
        ((TextView) view.findViewById(R.id.content)).setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        TodoAnimHelper.doAllFinishAnim(this, todoEntity, i, view, viewGroup, frameLayout, FwTodoItemForAllFinishVh.newInstance(frameLayout), null);
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void showToast(String str, long j) {
        this.mParentHelper.showToast(str, j);
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public boolean canToGuide() {
        return !this.mRvTodo.isInSwipingMode() || this.mRvTodo.hasMenuOpen();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onDetachFromWindow() {
        if (this.isEditMode) {
            onExitEditMode(-1, false);
        }
        RemindTimePickerDialog remindTimePickerDialog = this.mTimePickerDialog;
        if (remindTimePickerDialog != null && remindTimePickerDialog.isShowing()) {
            this.mTimePickerDialog.dismiss();
        }
        this.mTimePickerDialog = null;
    }

    public void forceExitEditing() {
        FwTodoListContract.Presenter presenter = this.mPresenter;
        if (presenter == null || !presenter.getMIsInEdit()) {
            return;
        }
        exitEditing(false);
    }
}
