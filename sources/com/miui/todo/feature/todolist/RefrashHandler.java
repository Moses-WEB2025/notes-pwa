package com.miui.todo.feature.todolist;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.miui.common.tool.Logger;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class RefrashHandler extends Handler {
    private final String TAG;
    private final WeakReference<TodoListPresenter> todoListPresenterWeakReference;

    public RefrashHandler(TodoListPresenter todoListPresenter) {
        super(Looper.getMainLooper());
        this.TAG = "RefrashHandler";
        this.todoListPresenterWeakReference = new WeakReference<>(todoListPresenter);
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        super.dispatchMessage(message);
        Logger.INSTANCE.d("RefrashHandler", "dispatchMessage msg.what " + message.what);
        if (message.what == 1) {
            WeakReference<TodoListPresenter> weakReference = this.todoListPresenterWeakReference;
            TodoListPresenter todoListPresenter = weakReference == null ? null : weakReference.get();
            if (todoListPresenter != null) {
                todoListPresenter.handleTodoChanged();
            }
        }
    }
}
