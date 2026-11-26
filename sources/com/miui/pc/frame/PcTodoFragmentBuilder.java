package com.miui.pc.frame;

import android.os.Bundle;
import com.miui.common.base.BaseTodoFragment;
import com.miui.pc.feature.todo.PcTodoListFragment;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoFragmentBuilder {
    public static BaseTodoFragment newInstance(int i) {
        PcTodoListFragment pcTodoListFragment = new PcTodoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("todo_list_type", i);
        pcTodoListFragment.setArguments(bundle);
        return pcTodoListFragment;
    }
}
