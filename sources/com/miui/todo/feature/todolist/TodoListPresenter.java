package com.miui.todo.feature.todolist;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.page.PadTodoPageContainerFragment;
import com.miui.notes.page.TodoListPageFragment;
import com.miui.todo.base.BaseSchedulerProvider;
import com.miui.todo.base.todolist.BaseTodoListPresenter;
import com.miui.todo.data.TodoDataDepository;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.provider.TodoDao;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListPresenter extends BaseTodoListPresenter implements TodoListContract.Presenter {
    public static final int HANDLE_TODO_CHANGED = 1;
    private static final String TAG = "TodoListPresenter";
    public static final int UPDATE_UI_DURATION_TIME = 500;
    protected View.OnClickListener mEditViewClickListener;
    private RefrashHandler mRefrashHandler;
    protected final TodoEditListener mTodoEditorListener;
    private final TodoObserver mTodoProviderObserver;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.cancel_button /* 2131362139 */:
            case R.id.root /* 2131363318 */:
            case R.id.send_button /* 2131363404 */:
            case R.id.todo_edit_mask /* 2131363638 */:
                if (this.mWorkingTodo != null) {
                    TodoEntity todoData = this.mWorkingTodo.getTodoData();
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
                            this.mWorkingTodo.setContent(subModeImpl.getContent());
                        } else {
                            this.mWorkingTodo.setContent(subModeImpl.getTitle());
                            this.mWorkingTodo.setListType(0);
                        }
                    }
                    if ((getView() instanceof PadTodoPageContainerFragment) || (getView() instanceof TodoListPageFragment)) {
                        ToDoStat.markDoneEdit(this, getView().getIsNewTodo());
                        getView().setIsNewTodo(false);
                    }
                    getView().exitEdit(true);
                }
                break;
            case R.id.group_tag_custom /* 2131362677 */:
            case R.id.iv_tag_custom /* 2131362790 */:
                if (getView() != null) {
                    if (this.mWorkingTodo != null && this.mWorkingTodo.getTodoData().getRemindType() != 0) {
                        getView().showRemindTimePickerDialog(this.mWorkingTodo.getCurrentCustomRemindTime(), this.mWorkingTodo.getWorkingRepeatMode());
                    } else {
                        getView().showRemindTimePickerDialog(Long.valueOf(System.currentTimeMillis() + 60000), 0);
                    }
                }
                break;
            case R.id.iv_tag_cancel /* 2131362789 */:
                cancelRemindCustom();
                break;
            case R.id.record /* 2131363272 */:
                getView().exitEdit(true);
                break;
        }
    }

    public TodoListPresenter(TodoListContract.View view, BaseSchedulerProvider baseSchedulerProvider) {
        super(view, baseSchedulerProvider);
        this.mEditViewClickListener = new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.TodoListPresenter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view2);
            }
        };
        this.mTodoEditorListener = new TodoEditListener() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.1
            @Override // com.miui.todo.feature.todolist.TodoEditListener
            public void onTextChanged(CharSequence charSequence) {
                if ((TodoListPresenter.this.mView instanceof Fragment) && ((Fragment) TodoListPresenter.this.mView).isAdded() && ((Fragment) TodoListPresenter.this.mView).getActivity() != null) {
                    ((Fragment) TodoListPresenter.this.mView).getActivity().onUserInteraction();
                }
                if (TodoListPresenter.this.getMWorkingTodo() != null) {
                    TodoListPresenter.this.getMWorkingTodo().setContent(charSequence.toString());
                }
            }

            @Override // com.miui.todo.feature.todolist.TodoEditListener
            public void onTodoTypeChanged(int i) {
                if ((TodoListPresenter.this.mView instanceof Fragment) && ((Fragment) TodoListPresenter.this.mView).isAdded() && ((Fragment) TodoListPresenter.this.mView).getActivity() != null) {
                    ((Fragment) TodoListPresenter.this.mView).getActivity().onUserInteraction();
                }
                if (TodoListPresenter.this.getMWorkingTodo() != null) {
                    TodoListPresenter.this.getMWorkingTodo().setListType(i);
                    TodoListPresenter.this.getMWorkingTodo().setPlaint(TodoUtils.getString(R.string.todo_list));
                }
            }

            @Override // com.miui.todo.feature.todolist.TodoEditListener
            public void onSubTodoChanged(String str) {
                String strReplace = str.replace(CharUtils.ZERO_WIDTH_SPACE, "");
                if ((TodoListPresenter.this.mView instanceof Fragment) && ((Fragment) TodoListPresenter.this.mView).isAdded() && ((Fragment) TodoListPresenter.this.mView).getActivity() != null) {
                    ((Fragment) TodoListPresenter.this.mView).getActivity().onUserInteraction();
                }
                WorkingTodo workingTodo = TodoListPresenter.this.getMWorkingTodo();
                if (workingTodo != null) {
                    workingTodo.setContent(strReplace);
                    if (workingTodo.getListType() == 1) {
                        SubModeImpl subModeImpl = new SubModeImpl(strReplace);
                        int finishOperation = subModeImpl.getFinishOperation();
                        if (finishOperation != 2) {
                            if (finishOperation == 1) {
                                subModeImpl.setExpand(false);
                                workingTodo.setContent(subModeImpl.getContent());
                            }
                            workingTodo.setFinishType(finishOperation);
                        }
                        TodoListPresenter.this.getMWorkingTodo().setPlaint(subModeImpl.getTitle());
                        return;
                    }
                    TodoListPresenter.this.getMWorkingTodo().setContent(strReplace);
                }
            }
        };
        this.mView.setPresenter(this);
        this.mTodoProviderObserver = new TodoObserver(new Handler(), this);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter
    protected void initDataSource() {
        this.mDepository = TodoDataDepository.getInstance();
        this.mDataSource = this.mDepository.getDataSource();
    }

    public TodoListContract.View getView() {
        return (TodoListContract.View) this.mView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TodoListAdapter getMyListAdapter() {
        return (TodoListAdapter) getListAdapter();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void subscribe() {
        Logger.INSTANCE.d(TAG, "subscribe");
        registerDataObserver(true);
        getAndUpdateDataToView();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void unsubscribe() {
        if (this.mDisposables.size() > 0) {
            for (int i = 0; i < this.mDisposables.size(); i++) {
                if (!this.mDisposables.get(i).isDisposed()) {
                    this.mDisposables.get(i).dispose();
                }
            }
            this.mDisposables.clear();
        }
        registerDataObserver(false);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.IBaseContract.Presenter
    public void destroy() {
        unsubscribe();
        this.mView = null;
        RefrashHandler refrashHandler = this.mRefrashHandler;
        if (refrashHandler != null) {
            refrashHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void setShowListType(int i, boolean z) {
        Logger.INSTANCE.d(TAG, "setShowListType" + i + " updateData:" + z);
        this.mDataSource.getBrowseConfig().setShowListType(i);
        if (z) {
            getAndUpdateDataToView();
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void enterEditMode(TodoEntity todoEntity, int i, boolean z, boolean z2) {
        super.enterEditMode(todoEntity, i, z, z2);
        registerDataObserver(false);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void exitEditMode(boolean z, boolean z2) {
        super.exitEditMode(z, z2);
        registerDataObserver(true);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void enterAudioEditMode() {
        this.mWorkingTodo = new WorkingTodo(true);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void enterSearchMode() {
        this.mInSearchMode = true;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void exitSearchMode() {
        this.mInSearchMode = false;
        refreshCacheData();
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void setTodosRemindCustom(final RemindTimeConfig remindTimeConfig, final long[] jArr) {
        Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                if (TodoListPresenter.this.mView == null) {
                    observableEmitter.onNext(false);
                    observableEmitter.onComplete();
                    return;
                }
                for (long j : jArr) {
                    try {
                        TodoEntity childItem = TodoListPresenter.this.getMyListAdapter().getChildItem((int) j);
                        if (childItem != null) {
                            arrayList.add(childItem);
                        }
                    } catch (Exception e) {
                        observableEmitter.onError(e);
                    }
                }
                int[] iArr = new int[jArr.length];
                while (true) {
                    long[] jArr2 = jArr;
                    if (i >= jArr2.length) {
                        break;
                    }
                    iArr[i] = (int) jArr2[i];
                    i++;
                }
                TodoListPresenter.this.mDataSource.setBatchTime(iArr, remindTimeConfig.isWholeDay ? 1 : 2, remindTimeConfig.remindTime);
                observableEmitter.onNext(true);
                observableEmitter.onComplete();
            }
        }).subscribeOn(this.mSchedulerProvider.io()).observeOn(this.mSchedulerProvider.ui()).subscribe(new Observer<Boolean>() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                TodoListPresenter.this.mDisposables.add(disposable);
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (!bool.booleanValue() || TodoListPresenter.this.mView == null) {
                    return;
                }
                TodoListPresenter.this.mView.refreshTodoList();
            }
        });
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void hideTodo(TodoEntity todoEntity, int i, boolean z) {
        if (z) {
            this.mDataSource.hideItem(todoEntity, i);
        } else {
            this.mDataSource.cancelHideItem(todoEntity, i);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void deleteTodo(TodoEntity todoEntity, int i) {
        registerDataObserver(false);
        if (this.mInSearchMode) {
            this.mSearchMode.deleteItem(todoEntity);
        } else if (this.mView != null) {
            this.mDataSource.deleteItem(todoEntity, getMyListAdapter().getItemInfo(i).mSubPosition);
        }
        if (this.mView != null) {
            this.mView.onDeleteTodo();
        }
        AudioUtils.playDeleteRingtone();
        registerDataObserver(true);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void deleteTodos(final long[] jArr) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.todo.feature.todolist.TodoListPresenter$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.lambda$deleteTodos$1(jArr, observableEmitter);
            }
        }).subscribeOn(this.mSchedulerProvider.io()).observeOn(this.mSchedulerProvider.ui()).subscribe(new Observer<Boolean>() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.4
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                TodoListPresenter.this.mDisposables.add(disposable);
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    if (TodoListPresenter.this.mView != null) {
                        TodoListPresenter.this.getView().onDeleteTodos();
                    }
                    AudioUtils.playDeleteRingtone();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteTodos$1(long[] jArr, ObservableEmitter observableEmitter) throws Exception {
        if (this.mView == null) {
            observableEmitter.onNext(false);
            observableEmitter.onComplete();
            return;
        }
        for (int i = 0; i < jArr.length; i++) {
            try {
                TodoEntity childItem = getMyListAdapter().getChildItem((int) jArr[i]);
                if (i == 0) {
                    ToDoStat.markTodoCardOperate(childItem);
                }
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        }
        int[] iArr = new int[jArr.length];
        for (int i2 = 0; i2 < jArr.length; i2++) {
            iArr[i2] = (int) jArr[i2];
        }
        this.mDataSource.deleteBatch(iArr);
        observableEmitter.onNext(true);
        observableEmitter.onComplete();
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public boolean moveTodo(TodoEntity todoEntity, int i, int i2) {
        if (i == i2 || i2 != 3) {
            return false;
        }
        this.mDraggedTodoData = todoEntity;
        if (this.mDraggedTodoData.getRemindType() == 0) {
            if (this.mView == null) {
                return true;
            }
            this.mView.showRemindTimePickerDialog(null, this.mDraggedTodoData.getRemindRepeatType());
            return true;
        }
        if (this.mView == null) {
            return true;
        }
        this.mView.showRemindTimePickerDialog(Long.valueOf(this.mDraggedTodoData.getRemindTime()), this.mDraggedTodoData.getRemindRepeatType());
        return true;
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void setTodoFinishState(TodoEntity todoEntity, int i, int i2) {
        registerDataObserver(false);
        super.setTodoFinishState(todoEntity, i, i2);
        registerDataObserver(true);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoListPresenter, com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void updateTodo(TodoEntity todoEntity, int i, boolean z) {
        registerDataObserver(false);
        super.updateTodo(todoEntity, i, z);
        registerDataObserver(true);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void setEditTodoFromIntent(long j) {
        TodoEntity todoEntityQueryItemEntity = TodoDao.queryItemEntity(j);
        if (getView() == null || todoEntityQueryItemEntity == null) {
            return;
        }
        getView().onMarkTodo(getMyListAdapter().getItemTotalPosByData(todoEntityQueryItemEntity));
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void queryTodo(String str) {
        queryTodo(str, 2);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void queryTodo(String str, int i) {
        if (getView() == null) {
            return;
        }
        this.mSearchMode.queryItem(getView().getViewContext(), str, i).observeOn(this.mSchedulerProvider.ui()).subscribe(new Observer<List<TodoEntity>>() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.5
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                TodoListPresenter.this.mDisposables.add(disposable);
            }

            @Override // io.reactivex.Observer
            public void onNext(List<TodoEntity> list) {
                if (TodoListPresenter.this.getView() != null) {
                    TodoListPresenter.this.getView().onShowSearchResult(list);
                }
            }
        });
    }

    private void registerDataObserver(boolean z) {
        if (z) {
            try {
                NoteApp.getInstance().getContentResolver().registerContentObserver(Uri.parse(TodoProvider.URI_TODO_PRIVATE), true, this.mTodoProviderObserver);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            NoteApp.getInstance().getContentResolver().unregisterContentObserver(this.mTodoProviderObserver);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public void refreshCacheData() {
        this.mDataSource.refreshData();
    }

    private static final class TodoObserver extends ContentObserver {
        WeakReference<TodoListPresenter> presenterWeakReference;

        public TodoObserver(Handler handler, TodoListPresenter todoListPresenter) {
            super(handler);
            this.presenterWeakReference = new WeakReference<>(todoListPresenter);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Logger.INSTANCE.d(TodoListPresenter.TAG, "onChange selfChange " + z);
            WeakReference<TodoListPresenter> weakReference = this.presenterWeakReference;
            TodoListPresenter todoListPresenter = weakReference == null ? null : weakReference.get();
            if (todoListPresenter != null) {
                boolean zHasMessages = todoListPresenter.getRefreshHandler().hasMessages(1);
                if (zHasMessages) {
                    Logger.INSTANCE.d(TodoListPresenter.TAG, "onChange hasMessages " + zHasMessages);
                } else {
                    todoListPresenter.getRefreshHandler().sendEmptyMessageDelayed(1, 500L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RefrashHandler getRefreshHandler() {
        if (this.mRefrashHandler == null) {
            this.mRefrashHandler = new RefrashHandler(this);
        }
        return this.mRefrashHandler;
    }

    public void handleTodoChanged() {
        Logger.INSTANCE.d(TAG, "handleTodoChanged mSkipNextObserverRefresh=" + this.mSkipNextObserverRefresh);
        if (this.mSkipNextObserverRefresh) {
            Logger.INSTANCE.d(TAG, "handleTodoChanged skipped, already did precise notify");
            this.mSkipNextObserverRefresh = false;
            return;
        }
        if (this.mView == null || this.mView.getListView() == null) {
            return;
        }
        if (!this.mView.getListView().isLayoutSuppressed() && this.mView.getListView().isAttachedToWindow()) {
            if (!this.mView.getListView().isComputingLayout() && !this.mView.getListView().isAnimating()) {
                this.mView.getListView().suppressLayout(true);
                this.mView.getListView().post(new Runnable() { // from class: com.miui.todo.feature.todolist.TodoListPresenter.6
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TodoListPresenter.this.mView == null || TodoListPresenter.this.mView.getListView() == null) {
                            return;
                        }
                        TodoListPresenter.this.mView.getListView().suppressLayout(false);
                        TodoListPresenter.this.getAndUpdateDataToView();
                    }
                });
                return;
            } else {
                getAndUpdateDataToView();
                return;
            }
        }
        getAndUpdateDataToView();
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public View.OnClickListener getEditViewClickListener() {
        return this.mEditViewClickListener;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.Presenter
    public TodoEditListener getTodoEditorListener() {
        return this.mTodoEditorListener;
    }
}
