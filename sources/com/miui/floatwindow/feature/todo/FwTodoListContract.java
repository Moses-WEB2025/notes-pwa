package com.miui.floatwindow.feature.todo;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import kotlin.Metadata;

/* JADX INFO: compiled from: FwTodoListContract.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u0004\u0005B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"Lcom/miui/floatwindow/feature/todo/FwTodoListContract;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "View", "Presenter", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FwTodoListContract {

    /* JADX INFO: compiled from: FwTodoListContract.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b`\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/floatwindow/feature/todo/FwTodoListContract$Presenter;", "Lcom/miui/todo/base/todolist/IBaseTodoListContract$Presenter;", "addNewTodo", "", "useAudio", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Presenter extends IBaseTodoListContract.Presenter {
        void addNewTodo(boolean useAudio);
    }

    /* JADX INFO: compiled from: FwTodoListContract.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001¨\u0006\u0002À\u0006\u0003"}, d2 = {"Lcom/miui/floatwindow/feature/todo/FwTodoListContract$View;", "Lcom/miui/todo/base/todolist/IBaseTodoListContract$View;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface View extends IBaseTodoListContract.View {
    }
}
