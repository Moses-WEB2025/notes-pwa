package com.miui.todo.data.utils;

import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.mode.SubModeImpl;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoUtils {
    public static boolean isSameContentTodo(TodoBaseEntity todoBaseEntity, TodoBaseEntity todoBaseEntity2) {
        if (todoBaseEntity.getListType() == 1 && todoBaseEntity2.getListType() == 1) {
            SubModeImpl subModeImpl = new SubModeImpl(todoBaseEntity.getContent());
            SubModeImpl subModeImpl2 = new SubModeImpl(todoBaseEntity2.getContent());
            if (subModeImpl.getTitle().equals(subModeImpl2.getTitle()) && subModeImpl.getSubTodoSize() == subModeImpl2.getSubTodoSize()) {
                int subTodoSize = subModeImpl.getSubTodoSize();
                List<SubTodoEntity> list = subModeImpl.getSubTodoList().getList();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    arrayList.add(list.get(i).getContent());
                }
                List<SubTodoEntity> list2 = subModeImpl.getSubTodoList().getList();
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    arrayList2.add(list2.get(i2).getContent());
                }
                for (int i3 = 0; i3 < subTodoSize; i3++) {
                    if (!arrayList2.contains((String) arrayList.get(i3))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
