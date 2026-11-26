package com.miui.common.base;

import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ui.fragment.DialogManagedFragment;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: BaseTodoFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000 62\u00020\u0001:\u00016B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000eH$J\u0014\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010'H$J\u0012\u0010(\u001a\u00020#2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0010\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020*H\u0016J\u0006\u0010-\u001a\u00020\u0005J\u0010\u0010.\u001a\u00020#2\b\u0010/\u001a\u0004\u0018\u00010\u0014J\u000e\u00100\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020\u000e2\b\u00103\u001a\u0004\u0018\u00010*J\u000e\u00104\u001a\u00020#2\u0006\u00105\u001a\u00020\u0005R\u001a\u0010\u0004\u001a\u00020\u0005X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u000eX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u000eX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0010\"\u0004\b!\u0010\u0012¨\u00067"}, d2 = {"Lcom/miui/common/base/BaseTodoFragment;", "Lcom/miui/notes/ui/fragment/DialogManagedFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mInSearchMode", "", "getMInSearchMode", "()Z", "setMInSearchMode", "(Z)V", "mIsShow", "getMIsShow", "setMIsShow", "todoListType", "", "getTodoListType", "()I", "setTodoListType", "(I)V", "mActionListener", "Lcom/miui/common/base/FragmentActionListener;", "getMActionListener", "()Lcom/miui/common/base/FragmentActionListener;", "setMActionListener", "(Lcom/miui/common/base/FragmentActionListener;)V", "mScreenSpec", "Lmiuix/responsive/map/ScreenSpec;", "getMScreenSpec", "()Lmiuix/responsive/map/ScreenSpec;", "setMScreenSpec", "(Lmiuix/responsive/map/ScreenSpec;)V", "mSplitMode", "getMSplitMode", "setMSplitMode", "refreshTodoData", "", "createEditController", "Lcom/miui/todo/feature/todoedit/BaseTodoEditController;", "todoData", "Lcom/miui/todo/data/bean/TodoBaseEntity;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "onBackPressed", "setActionListener", "listener", "changeTodoCategory", "notifyAction", "actionId", "bundle", "setInSearchMode", "inSearchMode", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseTodoFragment extends DialogManagedFragment {
    public static final String TODO_LIST_TYPE = "todo_list_type";
    private FragmentActionListener mActionListener;
    private boolean mInSearchMode;
    private int mSplitMode;
    private boolean mIsShow = true;
    private int todoListType = -1;
    private ScreenSpec mScreenSpec = new ScreenSpec();

    protected abstract BaseTodoEditController createEditController(TodoBaseEntity todoData);

    public final boolean onBackPressed() {
        return false;
    }

    protected abstract void refreshTodoData(int todoListType);

    protected final boolean getMInSearchMode() {
        return this.mInSearchMode;
    }

    protected final void setMInSearchMode(boolean z) {
        this.mInSearchMode = z;
    }

    protected final boolean getMIsShow() {
        return this.mIsShow;
    }

    protected final void setMIsShow(boolean z) {
        this.mIsShow = z;
    }

    protected final int getTodoListType() {
        return this.todoListType;
    }

    protected final void setTodoListType(int i) {
        this.todoListType = i;
    }

    protected final FragmentActionListener getMActionListener() {
        return this.mActionListener;
    }

    protected final void setMActionListener(FragmentActionListener fragmentActionListener) {
        this.mActionListener = fragmentActionListener;
    }

    protected final ScreenSpec getMScreenSpec() {
        return this.mScreenSpec;
    }

    protected final void setMScreenSpec(ScreenSpec screenSpec) {
        Intrinsics.checkNotNullParameter(screenSpec, "<set-?>");
        this.mScreenSpec = screenSpec;
    }

    protected final int getMSplitMode() {
        return this.mSplitMode;
    }

    protected final void setMSplitMode(int i) {
        this.mSplitMode = i;
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            this.todoListType = requireArguments().getInt("todo_list_type", 0);
        } else {
            this.todoListType = savedInstanceState.getInt("todo_list_type", 0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putInt("todo_list_type", this.todoListType);
    }

    public final void setActionListener(FragmentActionListener listener) {
        this.mActionListener = listener;
    }

    public final void changeTodoCategory(int todoListType) {
        if (this.todoListType != todoListType) {
            this.todoListType = todoListType;
            refreshTodoData(todoListType);
        }
    }

    public final void notifyAction(int actionId, Bundle bundle) {
        FragmentActionListener fragmentActionListener = this.mActionListener;
        if (fragmentActionListener != null) {
            Intrinsics.checkNotNull(fragmentActionListener);
            fragmentActionListener.onAction(actionId, bundle);
        }
    }

    public final void setInSearchMode(boolean inSearchMode) {
        this.mInSearchMode = inSearchMode;
    }
}
