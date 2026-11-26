package com.miui.todo.data.utils;

import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.mode.SubModeImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoGuideConstant {
    private String mGuideCommonTip;
    private List<String> mGuideString = new ArrayList();
    private String mGuideSubPlainText;
    private TodoBaseEntity mGuideSubTodo;

    public TodoGuideConstant() {
        initGuide();
    }

    private void initGuide() {
        this.mGuideCommonTip = NoteApp.getInstance().getString(R.string.todo_user_guide_tip5);
        this.mGuideSubTodo = new TodoBaseEntity();
        SubModeImpl subModeImpl = new SubModeImpl("");
        SubTodoEntity subTodoEntity = new SubTodoEntity(0, NoteApp.getInstance().getString(R.string.todo_user_guide_tip2), false);
        SubTodoEntity subTodoEntity2 = new SubTodoEntity(1, NoteApp.getInstance().getString(R.string.todo_user_guide_tip3), false);
        SubTodoEntity subTodoEntity3 = new SubTodoEntity(2, NoteApp.getInstance().getString(R.string.todo_user_guide_tip6), false);
        subModeImpl.add(subTodoEntity);
        subModeImpl.add(subTodoEntity2);
        subModeImpl.add(subTodoEntity3);
        subModeImpl.setTitle(NoteApp.getInstance().getString(R.string.todo_user_guide_tip1));
        this.mGuideSubTodo.setContent(subModeImpl.getContent());
        this.mGuideSubTodo.setPlainText(subModeImpl.getPlainTextForSearch());
        this.mGuideSubTodo.setListType(1);
        initGuideStringArray();
    }

    private void initGuideStringArray() {
        SubTodoEntity subTodoEntity;
        SubTodoEntity subTodoEntity2;
        SubTodoEntity subTodoEntity3;
        boolean z = false;
        int i = 0;
        while (i < 2) {
            int i2 = z ? 1 : 0;
            while (i2 < 2) {
                if (i2 == 0) {
                    subTodoEntity = new SubTodoEntity(z ? 1 : 0, NoteApp.getInstance().getString(R.string.todo_user_guide_tip2), z);
                } else {
                    subTodoEntity = new SubTodoEntity(z ? 1 : 0, NoteApp.getInstance().getString(R.string.todo_user_guide_tip2), true);
                }
                int i3 = z ? 1 : 0;
                while (i3 < 2) {
                    if (i3 == 0) {
                        subTodoEntity2 = new SubTodoEntity(1, NoteApp.getInstance().getString(R.string.todo_user_guide_tip3), z);
                    } else {
                        subTodoEntity2 = new SubTodoEntity(1, NoteApp.getInstance().getString(R.string.todo_user_guide_tip3), true);
                    }
                    int i4 = z ? 1 : 0;
                    while (i4 < 2) {
                        if (i4 == 0) {
                            subTodoEntity3 = new SubTodoEntity(2, NoteApp.getInstance().getString(R.string.todo_user_guide_tip6), z);
                        } else {
                            subTodoEntity3 = new SubTodoEntity(2, NoteApp.getInstance().getString(R.string.todo_user_guide_tip6), true);
                        }
                        SubModeImpl subModeImpl = new SubModeImpl("");
                        subModeImpl.setTitle(NoteApp.getInstance().getString(R.string.todo_user_guide_tip1));
                        subModeImpl.add(subTodoEntity);
                        subModeImpl.add(subTodoEntity2);
                        subModeImpl.add(subTodoEntity3);
                        subModeImpl.setExpand(i != 0);
                        this.mGuideString.add(subModeImpl.getContent());
                        i4++;
                        z = false;
                    }
                    i3++;
                    z = false;
                }
                i2++;
                z = false;
            }
            i++;
            z = false;
        }
        this.mGuideSubPlainText = NoteApp.getInstance().getString(R.string.todo_user_guide_tip1) + StringUtils.SPACE + NoteApp.getInstance().getString(R.string.todo_user_guide_tip2) + StringUtils.SPACE + NoteApp.getInstance().getString(R.string.todo_user_guide_tip3) + StringUtils.SPACE + NoteApp.getInstance().getString(R.string.todo_user_guide_tip6);
    }

    public String getGuideCommonTip() {
        return this.mGuideCommonTip;
    }

    public String getGuideSubPlainText() {
        return this.mGuideSubPlainText;
    }

    public TodoBaseEntity getGuideSubTodo() {
        return this.mGuideSubTodo;
    }

    public List<String> getGuideString() {
        return this.mGuideString;
    }
}
