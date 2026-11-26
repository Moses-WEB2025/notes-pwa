package com.miui.todo.utils;

import android.animation.Animator;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.Logger;
import com.miui.maml.ActionCommand;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.base.todolist.BaseTodoListAdapter;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TodoAnimHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\t"}, d2 = {"com/miui/todo/utils/TodoAnimHelper$doAllFinishAnim$listener$1", "Landroid/animation/Animator$AnimatorListener;", "onAnimationStart", "", ActionCommand.AnimationProperty.PROPERTY_NAME, "Landroid/animation/Animator;", "onAnimationEnd", "onAnimationCancel", "onAnimationRepeat", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TodoAnimHelper$doAllFinishAnim$listener$1 implements Animator.AnimatorListener {
    final /* synthetic */ ViewGroup $contentView;
    final /* synthetic */ BaseTodoListAdapter $dataAdapter;
    final /* synthetic */ ViewGroup $maskView;
    final /* synthetic */ TodoAnimHelper.Listener $processListener;
    final /* synthetic */ RecyclerView $rvTodo;

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
    }

    TodoAnimHelper$doAllFinishAnim$listener$1(BaseTodoListAdapter baseTodoListAdapter, RecyclerView recyclerView, ViewGroup viewGroup, ViewGroup viewGroup2, TodoAnimHelper.Listener listener) {
        this.$dataAdapter = baseTodoListAdapter;
        this.$rvTodo = recyclerView;
        this.$contentView = viewGroup;
        this.$maskView = viewGroup2;
        this.$processListener = listener;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        BaseTodoListAdapter baseTodoListAdapter = this.$dataAdapter;
        if (baseTodoListAdapter != null) {
            baseTodoListAdapter.frozenDataChanged(false);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Logger.INSTANCE.d("TodoAnimHelper", "onAnimationEnd");
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.$rvTodo.findViewHolderForAdapterPosition(0);
        if (viewHolderFindViewHolderForAdapterPosition instanceof BaseTodoChildItemVh) {
            ((BaseTodoChildItemVh) viewHolderFindViewHolderForAdapterPosition).onCheckBox();
        }
        this.$contentView.removeView(this.$maskView);
        ViewGroup viewGroup = this.$contentView;
        final RecyclerView recyclerView = this.$rvTodo;
        final BaseTodoListAdapter baseTodoListAdapter = this.$dataAdapter;
        viewGroup.post(new Runnable() { // from class: com.miui.todo.utils.TodoAnimHelper$doAllFinishAnim$listener$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TodoAnimHelper$doAllFinishAnim$listener$1.onAnimationEnd$lambda$0(recyclerView, baseTodoListAdapter);
            }
        });
        BaseTodoListAdapter baseTodoListAdapter2 = this.$dataAdapter;
        if (baseTodoListAdapter2 != null) {
            baseTodoListAdapter2.setLastFinishId(-1L);
        }
        TodoAnimHelper.Listener listener = this.$processListener;
        if (listener != null) {
            listener.onEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAnimationEnd$lambda$0(RecyclerView recyclerView, BaseTodoListAdapter baseTodoListAdapter) {
        IBaseTodoListContract.Presenter presenter;
        recyclerView.suppressLayout(false);
        if (baseTodoListAdapter == null || (presenter = baseTodoListAdapter.getPresenter()) == null) {
            return;
        }
        presenter.getAndUpdateDataToView();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Logger.INSTANCE.d("TodoAnimHelper", "onAnimationEnd");
        BaseTodoListAdapter baseTodoListAdapter = this.$dataAdapter;
        if (baseTodoListAdapter != null) {
            baseTodoListAdapter.getPresenter().getAndUpdateDataToView();
            this.$dataAdapter.setLastFinishId(-1L);
        }
        TodoAnimHelper.Listener listener = this.$processListener;
        if (listener != null) {
            listener.onCancel();
        }
    }
}
