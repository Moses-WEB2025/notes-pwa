package com.miui.todo.base.todolist;

import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.todo.base.BaseSchedulerProvider;
import com.miui.todo.data.TodoDataDepository;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataBrowseConfig;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.data.mode.IDataSource;
import com.miui.todo.data.mode.SearchMode;
import com.miui.todo.data.mode.SearchModeImpl;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.WorkingTodo;
import com.miui.todo.utils.CharUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: BaseTodoListPresenter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001cJ\b\u0010'\u001a\u00020\u001fH$J\b\u0010(\u001a\u00020\u001fH\u0016J\b\u0010)\u001a\u00020\u001fH\u0016J\b\u0010*\u001a\u00020\u001fH\u0016J\b\u0010+\u001a\u00020\u0014H\u0016J*\u0010,\u001a\u00020\u001f2\b\u0010-\u001a\u0004\u0018\u00010\u00192\u0006\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u00142\u0006\u00100\u001a\u00020\u0014H\u0016J\u0018\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u00142\u0006\u00103\u001a\u00020\u0014H\u0016J\n\u00104\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u00105\u001a\u00020\u001fH\u0016J\b\u00106\u001a\u00020\u001fH\u0016J\u0010\u00107\u001a\u00020\u001f2\u0006\u00108\u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020\u001f2\u0006\u00108\u001a\u000209H\u0016J\b\u0010;\u001a\u00020\u001fH\u0002J\u0018\u0010<\u001a\u00020\u001f2\u0006\u0010=\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u001cH\u0016J \u0010>\u001a\u00020\u001f2\u0006\u0010=\u001a\u00020\u00192\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\u001cH\u0016J \u0010@\u001a\u00020\u001f2\u0006\u0010A\u001a\u00020\u00192\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\u0014H\u0016J\b\u0010C\u001a\u00020\u001fH\u0016J\u001a\u0010D\u001a\u00020\u00142\b\u0010E\u001a\u0004\u0018\u00010\u00192\u0006\u00108\u001a\u000209H\u0004J\b\u0010F\u001a\u00020\u001fH\u0016J\u0018\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u0014H\u0014J\u0010\u0010K\u001a\u00020\u001f2\u0006\u0010L\u001a\u00020\u0017H\u0004J\u0010\u0010M\u001a\u00020\u001f2\u0006\u0010N\u001a\u00020\u0019H\u0004J \u0010O\u001a\u00020\u001f2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020Q2\u0006\u0010S\u001a\u00020\u0014H\u0004J\u0018\u0010T\u001a\u00020\u001f2\u0006\u0010P\u001a\u00020Q2\u0006\u0010S\u001a\u00020\u0014H\u0004J\b\u0010U\u001a\u00020\"H\u0016R\u0014\u0010\b\u001a\u0004\u0018\u00010\u00038\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00058\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00020\u00128\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00020\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00178\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010#\u001a\u0004\u0018\u00010$8F¢\u0006\u0006\u001a\u0004\b%\u0010&¨\u0006V"}, d2 = {"Lcom/miui/todo/base/todolist/BaseTodoListPresenter;", "Lcom/miui/todo/base/todolist/IBaseTodoListContract$Presenter;", "view", "Lcom/miui/todo/base/todolist/IBaseTodoListContract$View;", "schedulerProvider", "Lcom/miui/todo/base/BaseSchedulerProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/todo/base/todolist/IBaseTodoListContract$View;Lcom/miui/todo/base/BaseSchedulerProvider;)V", "mView", "mSchedulerProvider", "mDisposables", "", "Lio/reactivex/disposables/Disposable;", "mDepository", "Lcom/miui/todo/data/TodoDataDepository;", "mDataSource", "Lcom/miui/todo/data/mode/IDataSource;", "mSearchMode", "Lcom/miui/todo/data/mode/SearchMode;", "mIsInEdit", "", "mInSearchMode", "mWorkingTodo", "Lcom/miui/todo/feature/todolist/WorkingTodo;", "mUnsavedTodo", "Lcom/miui/todo/data/bean/TodoEntity;", "mDraggedTodoData", "mPendingFinishAdapterPosition", "", "mSkipNextObserverRefresh", "setPendingFinishPosition", "", "position", "mSubTodoListListener", "Lcom/miui/todo/feature/todolist/SubTodoListListener;", "listAdapter", "Lcom/miui/todo/base/todolist/BaseTodoListAdapter;", "getListAdapter", "()Lcom/miui/todo/base/todolist/BaseTodoListAdapter;", "initDataSource", "subscribe", "unsubscribe", "destroy", "isInEdit", "enterEditMode", "todoData", "totalPos", "fromIntent", "useAudio", "exitEditMode", "saveData", "withAnim", "getWorkingTodo", "setRemindCustom", "cancelRemindCustom", "setRemindTimeInEditMode", "remindTimeConfig", "Lcom/miui/todo/data/bean/RemindTimeConfig;", "setRemindTimeInDragMode", "refreshTodoData", "deleteTodo", "todoEntity", "setTodoFinishState", "subPos", "updateTodo", "updateEntity", "finishStateChanged", "executePendingDataTask", "setRemindTime", "data", "getAndUpdateDataToView", "addTodoToDb", "workingData", "Lcom/miui/todo/data/bean/TodoBaseEntity;", "isFinish", "updateTodoToDb", "workingTodo", "checkAndScheduleAlert", "scheduleData", "scheduleAlert", "todoId", "", "date", ToDoStat.CANCEL, "cancelAlert", "getSubTodoListListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseTodoListPresenter implements IBaseTodoListContract.Presenter {
    protected IDataSource mDataSource;
    protected TodoDataDepository mDepository;
    protected List<Disposable> mDisposables;
    protected TodoEntity mDraggedTodoData;
    protected boolean mInSearchMode;
    protected boolean mIsInEdit;
    private int mPendingFinishAdapterPosition;
    protected final BaseSchedulerProvider mSchedulerProvider;
    protected SearchMode mSearchMode;
    protected boolean mSkipNextObserverRefresh;
    private final SubTodoListListener mSubTodoListListener;
    protected TodoEntity mUnsavedTodo;
    protected IBaseTodoListContract.View mView;
    protected WorkingTodo mWorkingTodo;

    protected abstract void initDataSource();

    public BaseTodoListPresenter(IBaseTodoListContract.View view, BaseSchedulerProvider schedulerProvider) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(schedulerProvider, "schedulerProvider");
        this.mDisposables = new ArrayList();
        this.mPendingFinishAdapterPosition = -1;
        this.mSubTodoListListener = new SubTodoListListener() { // from class: com.miui.todo.base.todolist.BaseTodoListPresenter$mSubTodoListListener$1
            @Override // com.miui.todo.feature.todolist.SubTodoListListener
            public void scroll(int x, int y, int todoItemHeight) {
            }

            @Override // com.miui.todo.feature.todolist.SubTodoListListener
            public void onDataChanged(TodoEntity entity, int position, View view2) {
                Intrinsics.checkNotNullParameter(entity, "entity");
                Intrinsics.checkNotNullParameter(view2, "view");
                IBaseTodoListContract.View view3 = this.this$0.mView;
                Intrinsics.checkNotNull(view3);
                if (view3.getListView().isLayoutSuppressed()) {
                    return;
                }
                BaseTodoListPresenter baseTodoListPresenter = this.this$0;
                IBaseTodoListContract.View view4 = baseTodoListPresenter.mView;
                Intrinsics.checkNotNull(view4);
                baseTodoListPresenter.updateTodo(entity, view4.getListAdapter().getItemInfo(position).mSubPosition, false);
                if (this.this$0.mInSearchMode) {
                    return;
                }
                IBaseTodoListContract.View view5 = this.this$0.mView;
                Intrinsics.checkNotNull(view5);
                view5.refreshTodoList();
            }

            @Override // com.miui.todo.feature.todolist.SubTodoListListener
            public void onDataFinishStateChanged(TodoEntity changedData, int position, View view2, boolean doFinish) {
                Intrinsics.checkNotNullParameter(changedData, "changedData");
                Intrinsics.checkNotNullParameter(view2, "view");
                if (this.this$0.mView != null) {
                    IBaseTodoListContract.View view3 = this.this$0.mView;
                    Intrinsics.checkNotNull(view3);
                    if (!view3.getListView().isLayoutSuppressed() && doFinish) {
                        this.this$0.mPendingFinishAdapterPosition = position;
                        this.this$0.mSkipNextObserverRefresh = true;
                        if (!LiteUtils.isLiteOrMiddle()) {
                            IBaseTodoListContract.View view4 = this.this$0.mView;
                            Intrinsics.checkNotNull(view4);
                            if (view4.getListAdapter().isAllTodoWillFinished() && changedData.getRemindRepeatType() == 0) {
                                IBaseTodoListContract.View view5 = this.this$0.mView;
                                Intrinsics.checkNotNull(view5);
                                view5.getListView().suppressLayout(true);
                                view2.findViewById(R.id.ll_expand_group).setClickable(false);
                                IBaseTodoListContract.View view6 = this.this$0.mView;
                                Intrinsics.checkNotNull(view6);
                                view6.doAllFinishAnim(changedData, position, view2);
                                return;
                            }
                        }
                        IBaseTodoListContract.View view7 = this.this$0.mView;
                        Intrinsics.checkNotNull(view7);
                        view7.getListView().suppressLayout(true);
                        view2.findViewById(R.id.ll_expand_group).setClickable(false);
                        IBaseTodoListContract.View view8 = this.this$0.mView;
                        Intrinsics.checkNotNull(view8);
                        view8.doFinishAnim(changedData, position, view2);
                    }
                }
            }
        };
        this.mView = view;
        this.mSchedulerProvider = schedulerProvider;
        this.mSearchMode = new SearchModeImpl();
        initDataSource();
    }

    public final void setPendingFinishPosition(int position) {
        this.mPendingFinishAdapterPosition = position;
        this.mSkipNextObserverRefresh = true;
    }

    public final BaseTodoListAdapter getListAdapter() {
        IBaseTodoListContract.View view = this.mView;
        if (view == null) {
            return null;
        }
        Intrinsics.checkNotNull(view);
        return view.getListAdapter();
    }

    @Override // com.miui.todo.base.IBaseContract.Presenter
    public void subscribe() {
        getAndUpdateDataToView();
    }

    @Override // com.miui.todo.base.IBaseContract.Presenter
    public void unsubscribe() {
        if (this.mDisposables.size() > 0) {
            int size = this.mDisposables.size();
            for (int i = 0; i < size; i++) {
                if (!this.mDisposables.get(i).isDisposed()) {
                    this.mDisposables.get(i).dispose();
                }
            }
            this.mDisposables.clear();
        }
    }

    @Override // com.miui.todo.base.IBaseContract.Presenter
    public void destroy() {
        unsubscribe();
        this.mView = null;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    /* JADX INFO: renamed from: isInEdit, reason: from getter */
    public boolean getMIsInEdit() {
        return this.mIsInEdit;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void enterEditMode(TodoEntity todoData, int totalPos, boolean fromIntent, boolean useAudio) {
        this.mIsInEdit = true;
        if (todoData != null) {
            this.mWorkingTodo = new WorkingTodo(todoData, totalPos);
        } else if (!useAudio) {
            WorkingTodo workingTodo = new WorkingTodo(false);
            this.mWorkingTodo = workingTodo;
            if (this.mUnsavedTodo != null) {
                Intrinsics.checkNotNull(workingTodo);
                workingTodo.restoreUnSaveData(this.mUnsavedTodo);
                todoData = this.mUnsavedTodo;
            }
        } else {
            this.mWorkingTodo = new WorkingTodo(true);
        }
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.onEnterEditMode(todoData, totalPos, fromIntent, useAudio);
        }
    }

    /* JADX WARN: Code duplicated, block: B:55:0x018a  */
    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void exitEditMode(boolean saveData, boolean withAnim) {
        TodoEntity todoData;
        this.mIsInEdit = false;
        WorkingTodo workingTodo = this.mWorkingTodo;
        if (workingTodo != null) {
            if (saveData) {
                WorkingTodo mWorkingTodo = getMWorkingTodo();
                Intrinsics.checkNotNull(mWorkingTodo);
                TodoEntity todoData2 = mWorkingTodo.getTodoData();
                if (todoData2.getListType() == 1) {
                    SubModeImpl subModeImpl = new SubModeImpl(todoData2.getContent());
                    List<SubTodoEntity> list = subModeImpl.getSubTodoList().getList();
                    for (int size = list.size() - 1; -1 < size; size--) {
                        String content = list.get(size).getContent();
                        if (TextUtils.isEmpty(content) || Intrinsics.areEqual(CharUtils.ZERO_WIDTH_SPACE, content)) {
                            subModeImpl.delete(size);
                        }
                    }
                    int subTodoSize = subModeImpl.getSubTodoSize();
                    if (subTodoSize > 0) {
                        if (subTodoSize == 1 && TextUtils.isEmpty(subModeImpl.getTitle())) {
                            WorkingTodo mWorkingTodo2 = getMWorkingTodo();
                            Intrinsics.checkNotNull(mWorkingTodo2);
                            mWorkingTodo2.setContent(subModeImpl.getSubTodoList().getList().get(0).getContent());
                            WorkingTodo mWorkingTodo3 = getMWorkingTodo();
                            Intrinsics.checkNotNull(mWorkingTodo3);
                            mWorkingTodo3.setListType(0);
                        } else {
                            if (TextUtils.isEmpty(subModeImpl.getTitle())) {
                                subModeImpl.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.todo_list));
                            }
                            if (subModeImpl.getTitle() != null) {
                                String title = subModeImpl.getTitle();
                                Intrinsics.checkNotNullExpressionValue(title, "getTitle(...)");
                                subModeImpl.setTitle(new Regex("\\n+$").replace(title, ""));
                            }
                            WorkingTodo mWorkingTodo4 = getMWorkingTodo();
                            Intrinsics.checkNotNull(mWorkingTodo4);
                            mWorkingTodo4.setContent(subModeImpl.getContent());
                        }
                    } else {
                        WorkingTodo mWorkingTodo5 = getMWorkingTodo();
                        Intrinsics.checkNotNull(mWorkingTodo5);
                        mWorkingTodo5.setContent(subModeImpl.getTitle());
                        WorkingTodo mWorkingTodo6 = getMWorkingTodo();
                        Intrinsics.checkNotNull(mWorkingTodo6);
                        mWorkingTodo6.setListType(0);
                    }
                }
                WorkingTodo workingTodo2 = this.mWorkingTodo;
                Intrinsics.checkNotNull(workingTodo2);
                if (workingTodo2.isInNewDataMode()) {
                    WorkingTodo workingTodo3 = this.mWorkingTodo;
                    Intrinsics.checkNotNull(workingTodo3);
                    TodoEntity todoEntityCreateTodo = workingTodo3.createTodo();
                    if (todoEntityCreateTodo != null && !TextUtils.equals(todoEntityCreateTodo.getContent(), StringUtils.LF)) {
                        if (todoEntityCreateTodo.getContent() != null && LiteUtils.isLiteOrMiddle() && !AudioUtils.hasAudioLabel(todoEntityCreateTodo.getContent())) {
                            String content2 = todoEntityCreateTodo.getContent();
                            Intrinsics.checkNotNullExpressionValue(content2, "getContent(...)");
                            todoEntityCreateTodo.setContent(new Regex("\\n+$").replace(content2, ""));
                        }
                        addTodoToDb(todoEntityCreateTodo, todoEntityCreateTodo.getIsFinish() == 1);
                        withAnim = false;
                    }
                } else {
                    WorkingTodo workingTodo4 = this.mWorkingTodo;
                    Intrinsics.checkNotNull(workingTodo4);
                    if (!workingTodo4.isEdited()) {
                        WorkingTodo workingTodo5 = this.mWorkingTodo;
                        Intrinsics.checkNotNull(workingTodo5);
                        if (workingTodo5.getTodoData() != null) {
                            WorkingTodo workingTodo6 = this.mWorkingTodo;
                            Intrinsics.checkNotNull(workingTodo6);
                            if (TextUtils.isEmpty(workingTodo6.getTodoData().getContent())) {
                                WorkingTodo workingTodo7 = this.mWorkingTodo;
                                Intrinsics.checkNotNull(workingTodo7);
                                todoData = workingTodo7.getTodoData();
                                if (todoData != null && todoData.getContent() != null && LiteUtils.isLiteOrMiddle() && !AudioUtils.hasAudioLabel(todoData.getContent())) {
                                    String content3 = todoData.getContent();
                                    Intrinsics.checkNotNullExpressionValue(content3, "getContent(...)");
                                    todoData.setContent(new Regex("\\n+$").replace(content3, ""));
                                    WorkingTodo workingTodo8 = this.mWorkingTodo;
                                    Intrinsics.checkNotNull(workingTodo8);
                                    workingTodo8.setContent(todoData.getContent());
                                }
                                WorkingTodo workingTodo9 = this.mWorkingTodo;
                                Intrinsics.checkNotNull(workingTodo9);
                                updateTodoToDb(workingTodo9);
                            }
                        }
                    } else {
                        WorkingTodo workingTodo10 = this.mWorkingTodo;
                        Intrinsics.checkNotNull(workingTodo10);
                        todoData = workingTodo10.getTodoData();
                        if (todoData != null) {
                            String content4 = todoData.getContent();
                            Intrinsics.checkNotNullExpressionValue(content4, "getContent(...)");
                            todoData.setContent(new Regex("\\n+$").replace(content4, ""));
                            WorkingTodo workingTodo11 = this.mWorkingTodo;
                            Intrinsics.checkNotNull(workingTodo11);
                            workingTodo11.setContent(todoData.getContent());
                        }
                        WorkingTodo workingTodo12 = this.mWorkingTodo;
                        Intrinsics.checkNotNull(workingTodo12);
                        updateTodoToDb(workingTodo12);
                    }
                }
                WorkingTodo workingTodo13 = this.mWorkingTodo;
                Intrinsics.checkNotNull(workingTodo13);
                if (!workingTodo13.isUseAudio()) {
                    this.mUnsavedTodo = null;
                }
            } else {
                Intrinsics.checkNotNull(workingTodo);
                if (workingTodo.isInNewDataMode()) {
                    WorkingTodo workingTodo14 = this.mWorkingTodo;
                    Intrinsics.checkNotNull(workingTodo14);
                    TodoEntity todoData3 = workingTodo14.getTodoData();
                    this.mUnsavedTodo = todoData3;
                    if (TextUtils.isEmpty(todoData3 != null ? todoData3.getContent() : null)) {
                        this.mUnsavedTodo = null;
                    }
                }
            }
            WorkingTodo workingTodo15 = this.mWorkingTodo;
            Intrinsics.checkNotNull(workingTodo15);
            int workingIndex = workingTodo15.getWorkingIndex();
            this.mWorkingTodo = null;
            IBaseTodoListContract.View view = this.mView;
            if (view != null) {
                Intrinsics.checkNotNull(view);
                view.onExitEditMode(workingIndex, withAnim);
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    /* JADX INFO: renamed from: getWorkingTodo, reason: from getter */
    public WorkingTodo getMWorkingTodo() {
        return this.mWorkingTodo;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void setRemindCustom() {
        IBaseTodoListContract.View view;
        if (this.mWorkingTodo == null || (view = this.mView) == null) {
            return;
        }
        Intrinsics.checkNotNull(view);
        WorkingTodo workingTodo = this.mWorkingTodo;
        Intrinsics.checkNotNull(workingTodo);
        Long currentCustomRemindTime = workingTodo.getCurrentCustomRemindTime();
        WorkingTodo workingTodo2 = this.mWorkingTodo;
        Intrinsics.checkNotNull(workingTodo2);
        view.showRemindTimePickerDialog(currentCustomRemindTime, workingTodo2.getWorkingRepeatMode());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void cancelRemindCustom() {
        IBaseTodoListContract.View view;
        WorkingTodo workingTodo = this.mWorkingTodo;
        if (workingTodo != null) {
            Intrinsics.checkNotNull(workingTodo);
            if (!workingTodo.cancelRemindTime() || (view = this.mView) == null) {
                return;
            }
            Intrinsics.checkNotNull(view);
            view.updateRemindCustomTag(false, null);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void setRemindTimeInEditMode(RemindTimeConfig remindTimeConfig) {
        IBaseTodoListContract.View view;
        Intrinsics.checkNotNullParameter(remindTimeConfig, "remindTimeConfig");
        if (this.mWorkingTodo == null || (view = this.mView) == null) {
            return;
        }
        Intrinsics.checkNotNull(view);
        WorkingTodo workingTodo = this.mWorkingTodo;
        Intrinsics.checkNotNull(workingTodo);
        view.updateRemindCustomTag(workingTodo.setRemindTime(remindTimeConfig), remindTimeConfig);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void setRemindTimeInDragMode(RemindTimeConfig remindTimeConfig) {
        Intrinsics.checkNotNullParameter(remindTimeConfig, "remindTimeConfig");
        if (!setRemindTime(this.mDraggedTodoData, remindTimeConfig) || this.mView == null) {
            return;
        }
        IDataSource iDataSource = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource);
        TodoEntity todoEntity = this.mDraggedTodoData;
        BaseTodoListAdapter listAdapter = getListAdapter();
        Intrinsics.checkNotNull(listAdapter);
        iDataSource.updateItem(todoEntity, listAdapter.getBeforeDragItemIndex());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshTodoData() {
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (view.getListAdapter() != null) {
                IBaseTodoListContract.View view2 = this.mView;
                Intrinsics.checkNotNull(view2);
                if (view2.getListView() == null) {
                    return;
                }
                IBaseTodoListContract.View view3 = this.mView;
                Intrinsics.checkNotNull(view3);
                if (view3.getListAdapter().isFrozenDataChanged()) {
                    return;
                }
                IBaseTodoListContract.View view4 = this.mView;
                Intrinsics.checkNotNull(view4);
                view4.getListView().stopScroll();
                IBaseTodoListContract.View view5 = this.mView;
                Intrinsics.checkNotNull(view5);
                view5.getListAdapter().bindDataSource(this.mDataSource);
                IBaseTodoListContract.View view6 = this.mView;
                Intrinsics.checkNotNull(view6);
                view6.onDataLoaded();
                BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
                IDataSource iDataSource = this.mDataSource;
                Intrinsics.checkNotNull(iDataSource);
                UIUtils.updateWidgetTodoNums(companion, iDataSource.getList(0).size());
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void deleteTodo(TodoEntity todoEntity, int position) {
        Intrinsics.checkNotNullParameter(todoEntity, "todoEntity");
        if (this.mInSearchMode) {
            this.mSearchMode.deleteItem(todoEntity);
        } else if (this.mView != null && getListAdapter() != null) {
            BaseTodoListAdapter listAdapter = getListAdapter();
            Intrinsics.checkNotNull(listAdapter);
            DataGroupInfo itemInfo = listAdapter.getItemInfo(position);
            IDataSource iDataSource = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource);
            iDataSource.deleteItem(todoEntity, itemInfo.mSubPosition);
        }
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.onDeleteTodo();
        }
        AudioUtils.playDeleteRingtone();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void setTodoFinishState(TodoEntity todoEntity, int subPos, int totalPos) {
        TodoEntity todoEntityCreateRepeatTodo;
        Intrinsics.checkNotNullParameter(todoEntity, "todoEntity");
        boolean z = todoEntity.getIsFinish() == 1 ? 1 : 0;
        if (todoEntity.getIsFinish() == 0) {
            cancelAlert(todoEntity.getId(), true);
        } else if (todoEntity.getRemindType() != 0 && todoEntity.getRemindTime() >= System.currentTimeMillis()) {
            scheduleAlert(todoEntity.getId(), todoEntity.getRemindTime(), false);
        }
        TodoEntity todoEntityCopy = todoEntity.copy();
        todoEntityCopy.setIsFinish(!z);
        todoEntityCopy.setLastModifiedTime(System.currentTimeMillis());
        todoEntityCopy.setMarkFinishTime(System.currentTimeMillis());
        todoEntityCopy.setRemindRepeatType(0);
        if (todoEntityCopy.getListType() == 1) {
            SubModeImpl subModeImpl = new SubModeImpl(todoEntityCopy.getContent());
            subModeImpl.changeFinishStatusAll(!z);
            todoEntityCopy.setContent(subModeImpl.getContent());
        }
        if (this.mInSearchMode) {
            this.mSearchMode.updateItem(todoEntityCopy);
            IDataSource iDataSource = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource);
            iDataSource.updateItem(todoEntityCopy, -1);
        } else {
            IDataSource iDataSource2 = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource2);
            iDataSource2.updateItem(todoEntityCopy, subPos);
        }
        if (z != 0 || todoEntity.getRemindRepeatType() <= 0) {
            todoEntityCreateRepeatTodo = null;
        } else {
            todoEntityCreateRepeatTodo = WorkingTodo.createRepeatTodo(todoEntity);
            if (!TextUtils.isEmpty(todoEntityCreateRepeatTodo.getContent())) {
                if (todoEntityCreateRepeatTodo.getListType() == 1) {
                    SubModeImpl subModeImpl2 = new SubModeImpl(todoEntityCopy.getContent());
                    subModeImpl2.changeFinishStatusAll(false);
                    todoEntityCreateRepeatTodo.setContent(subModeImpl2.getContent());
                }
                IDataSource iDataSource3 = this.mDataSource;
                Intrinsics.checkNotNull(iDataSource3);
                TodoEntity todoEntityAddItemToDb = iDataSource3.addItemToDb(todoEntityCreateRepeatTodo);
                if (todoEntity.getRemindType() != 0) {
                    scheduleAlert(todoEntityAddItemToDb.getId(), todoEntityAddItemToDb.getRemindTime(), false);
                }
            }
        }
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.onUpdateTodoFinishState(z, todoEntityCreateRepeatTodo != null, totalPos);
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0094  */
    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void updateTodo(TodoEntity updateEntity, int subPos, boolean finishStateChanged) {
        TodoEntity todoEntityCreateRepeatTodo;
        BaseTodoListPresenter baseTodoListPresenter;
        Intrinsics.checkNotNullParameter(updateEntity, "updateEntity");
        boolean z = updateEntity.getIsFinish() == 1;
        if (finishStateChanged && z) {
            updateEntity.setMarkFinishTime(System.currentTimeMillis());
        }
        if (finishStateChanged && z && updateEntity.getRemindRepeatType() > 0) {
            todoEntityCreateRepeatTodo = WorkingTodo.createRepeatTodo(updateEntity);
            updateEntity.setRemindRepeatType(0);
            if (!TextUtils.isEmpty(todoEntityCreateRepeatTodo.getContent()) && todoEntityCreateRepeatTodo.getListType() == 1) {
                SubModeImpl subModeImpl = new SubModeImpl(updateEntity.getContent());
                subModeImpl.changeFinishStatusAll(false);
                todoEntityCreateRepeatTodo.setContent(subModeImpl.getContent());
            }
        } else {
            todoEntityCreateRepeatTodo = null;
        }
        if (this.mInSearchMode) {
            this.mSearchMode.updateItem(updateEntity);
            IDataSource iDataSource = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource);
            iDataSource.updateItem(updateEntity, -1);
        } else {
            IDataSource iDataSource2 = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource2);
            iDataSource2.updateItem(updateEntity, subPos);
        }
        if (todoEntityCreateRepeatTodo != null) {
            IDataSource iDataSource3 = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource3);
            TodoEntity todoEntityAddItemToDb = iDataSource3.addItemToDb(todoEntityCreateRepeatTodo);
            if (todoEntityAddItemToDb == null || updateEntity.getRemindType() == 0) {
                baseTodoListPresenter = this;
            } else {
                baseTodoListPresenter = this;
                baseTodoListPresenter.scheduleAlert(todoEntityAddItemToDb.getId(), todoEntityAddItemToDb.getRemindTime(), false);
            }
        } else {
            baseTodoListPresenter = this;
        }
        IBaseTodoListContract.View view = baseTodoListPresenter.mView;
        if (view == null || z) {
            return;
        }
        Intrinsics.checkNotNull(view);
        view.onUpdateTodoData();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void executePendingDataTask() {
        if (this.mView == null || getListAdapter() == null) {
            IDataSource iDataSource = this.mDataSource;
            Intrinsics.checkNotNull(iDataSource);
            iDataSource.executePendingTask();
            this.mPendingFinishAdapterPosition = -1;
            return;
        }
        final BaseTodoListAdapter listAdapter = getListAdapter();
        Intrinsics.checkNotNull(listAdapter);
        IBaseTodoListContract.View view = this.mView;
        Intrinsics.checkNotNull(view);
        RecyclerView listView = view.getListView();
        int i = this.mPendingFinishAdapterPosition;
        IDataSource iDataSource2 = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource2);
        int size = iDataSource2.getList(1).size();
        IDataSource iDataSource3 = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource3);
        iDataSource3.executePendingTask();
        this.mPendingFinishAdapterPosition = -1;
        IDataSource iDataSource4 = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource4);
        int size2 = iDataSource4.getList(0).size();
        IDataSource iDataSource5 = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource5);
        int size3 = iDataSource5.getList(1).size();
        Logger.INSTANCE.d("BaseTodoListPresenter", "executePendingDataTask finishPos=" + i + " oldFinishSize=" + size + " newFinishSize=" + size3 + " newUnfinishSize=" + size2);
        if (i >= 0 && size3 == size + 1) {
            try {
                if (!listView.isComputingLayout() && !listView.isInLayout()) {
                    listAdapter.notifyItemRemoved(i);
                    IDataSource iDataSource6 = this.mDataSource;
                    Intrinsics.checkNotNull(iDataSource6);
                    DataBrowseConfig browseConfig = iDataSource6.getBrowseConfig();
                    if (browseConfig == null || !browseConfig.isShowGroup()) {
                        if (browseConfig != null && browseConfig.pShowFinished) {
                            listAdapter.notifyItemInserted(size2);
                        }
                    } else if (size == 0) {
                        listAdapter.notifyItemInserted(size2);
                        listAdapter.notifyItemInserted(size2 + 1);
                    } else if (browseConfig.pGroupExpandArray != null && browseConfig.pGroupExpandArray[1]) {
                        listAdapter.notifyItemInserted(size2 + 1);
                        listAdapter.notifyItemChanged(size2);
                    } else {
                        listAdapter.notifyItemChanged(size2);
                    }
                    UIUtils.updateWidgetTodoNums(BaseNoteApp.INSTANCE.getInstance(), size2);
                    return;
                }
                listView.post(new Runnable() { // from class: com.miui.todo.base.todolist.BaseTodoListPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });
                return;
            } catch (Exception e) {
                Logger.INSTANCE.d("BaseTodoListPresenter", "executePendingDataTask precise notify error: " + e);
            }
        }
        this.mSkipNextObserverRefresh = false;
        IBaseTodoListContract.View view2 = this.mView;
        if (view2 != null) {
            Intrinsics.checkNotNull(view2);
            view2.onUpdateTodoData();
        }
    }

    protected final boolean setRemindTime(TodoEntity data, RemindTimeConfig remindTimeConfig) {
        Intrinsics.checkNotNullParameter(remindTimeConfig, "remindTimeConfig");
        if (data == null) {
            return false;
        }
        data.setRemindType(remindTimeConfig.isWholeDay ? 1 : 2);
        data.setRemindTime(remindTimeConfig.remindTime);
        data.setFirstRemindTime(remindTimeConfig.remindTime);
        data.setRemindRepeatType(remindTimeConfig.repeatType);
        return true;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    public void getAndUpdateDataToView() {
        Logger.INSTANCE.d("BaseTodoListPresenter", "getAndUpdateDataToView ");
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.todo.base.todolist.BaseTodoListPresenter$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                BaseTodoListPresenter.getAndUpdateDataToView$lambda$1(this.f$0, observableEmitter);
            }
        }).subscribeOn(this.mSchedulerProvider.io()).observeOn(this.mSchedulerProvider.ui()).subscribe(new Observer<Boolean>() { // from class: com.miui.todo.base.todolist.BaseTodoListPresenter.getAndUpdateDataToView.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkNotNullParameter(e, "e");
            }

            @Override // io.reactivex.Observer
            public /* bridge */ /* synthetic */ void onNext(Boolean bool) {
                onNext(bool.booleanValue());
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkNotNullParameter(d, "d");
                BaseTodoListPresenter.this.mDisposables.add(d);
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0015  */
            public void onNext(boolean result) {
                if (result) {
                    if (BaseTodoListPresenter.this.mView != null) {
                        IBaseTodoListContract.View view = BaseTodoListPresenter.this.mView;
                        Intrinsics.checkNotNull(view);
                        if (view.getListAdapter() != null) {
                            onError(new Throwable("mView or listAdapter is null"));
                        }
                    } else {
                        onError(new Throwable("mView or listAdapter is null"));
                    }
                    BaseTodoListPresenter.this.refreshTodoData();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getAndUpdateDataToView$lambda$1(BaseTodoListPresenter baseTodoListPresenter, ObservableEmitter emitter) {
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        IDataSource iDataSource = baseTodoListPresenter.mDataSource;
        Intrinsics.checkNotNull(iDataSource);
        iDataSource.refreshData();
        emitter.onNext(true);
        emitter.onComplete();
    }

    protected void addTodoToDb(TodoBaseEntity workingData, boolean isFinish) {
        Intrinsics.checkNotNullParameter(workingData, "workingData");
        if (TextUtils.isEmpty(workingData.getContent())) {
            this.mUnsavedTodo = null;
            return;
        }
        IDataSource iDataSource = this.mDataSource;
        Intrinsics.checkNotNull(iDataSource);
        TodoEntity todoEntityAddItem = iDataSource.addItem(workingData);
        if (todoEntityAddItem == null) {
            return;
        }
        if (!isFinish) {
            checkAndScheduleAlert(todoEntityAddItem);
        }
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.onNewTodoAdded(todoEntityAddItem, 0, isFinish);
        }
    }

    protected final void updateTodoToDb(WorkingTodo workingTodo) {
        Intrinsics.checkNotNullParameter(workingTodo, "workingTodo");
        TodoEntity todoEntityCreateTodo = workingTodo.createTodo();
        if (todoEntityCreateTodo == null) {
            Logger.INSTANCE.e("TodoListPresenter", "updateTodoToDb workingData is null! :");
            return;
        }
        if (TextUtils.isEmpty(todoEntityCreateTodo.getContent())) {
            TodoEntity oldTodoData = workingTodo.getOldTodoData();
            Intrinsics.checkNotNullExpressionValue(oldTodoData, "getOldTodoData(...)");
            deleteTodo(oldTodoData, workingTodo.getWorkingIndex());
            return;
        }
        if (this.mInSearchMode) {
            this.mSearchMode.updateItem(todoEntityCreateTodo);
        } else {
            BaseTodoListAdapter listAdapter = getListAdapter();
            if (listAdapter != null) {
                DataGroupInfo itemInfo = listAdapter.getItemInfo(workingTodo.getWorkingIndex());
                IDataSource iDataSource = this.mDataSource;
                Intrinsics.checkNotNull(iDataSource);
                iDataSource.updateItem(todoEntityCreateTodo, itemInfo.mSubPosition);
            }
        }
        if (todoEntityCreateTodo.getRemindType() != 0 && workingTodo.isRemindTimeChanged() && todoEntityCreateTodo.getIsFinish() == 0) {
            checkAndScheduleAlert(todoEntityCreateTodo);
        } else if (workingTodo.isNeedCancelRemind()) {
            cancelAlert(todoEntityCreateTodo.getId(), true);
        }
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.onUpdateTodo(workingTodo.getWorkingIndex(), workingTodo.getTodoData().getIsFinish() == 1);
        }
    }

    protected final void checkAndScheduleAlert(TodoEntity scheduleData) {
        Intrinsics.checkNotNullParameter(scheduleData, "scheduleData");
        if (scheduleData.getRemindType() != 0) {
            if (scheduleData.getRemindRepeatType() == 0) {
                scheduleAlert(scheduleData.getId(), scheduleData.getRemindTime(), false);
            } else if (scheduleData.getRemindTime() < System.currentTimeMillis()) {
                scheduleAlert(scheduleData.getId(), TimeUtils.getNextRemindTime(scheduleData.getRemindRepeatType(), scheduleData.getRemindTime(), scheduleData.getFirstRemindTime()), false);
            } else {
                scheduleAlert(scheduleData.getId(), scheduleData.getRemindTime(), false);
            }
        }
    }

    protected final void scheduleAlert(long todoId, long date, boolean cancel) {
        IBaseTodoListContract.View view = this.mView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (view.getViewContext() == null) {
                return;
            }
            IBaseTodoListContract.View view2 = this.mView;
            Intrinsics.checkNotNull(view2);
            AlarmReceiver.scheduleTodoAlert(view2.getViewContext().getApplicationContext(), todoId, date, cancel);
        }
    }

    protected final void cancelAlert(long todoId, boolean cancel) {
        if (this.mView == null) {
            return;
        }
        AlarmReceiver.scheduleTodoAlert(BaseNoteApp.INSTANCE.getInstance(), todoId, 0L, cancel);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.Presenter
    /* JADX INFO: renamed from: getSubTodoListListener, reason: from getter */
    public SubTodoListListener getMSubTodoListListener() {
        return this.mSubTodoListListener;
    }
}
