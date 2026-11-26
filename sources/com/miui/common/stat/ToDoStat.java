package com.miui.common.stat;

import android.text.TextUtils;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.WorkingTodo;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class ToDoStat extends BaseStat {
    public static final String AUTO_DELETE = "auto_delete";
    public static final String CANCEL = "cancel";
    public static final String CANCEL_DONE = "cancel_done";
    public static final String CHECK_DONE = "check_done";
    public static final String CHECK_SUB_DONE = "check_sub_done";
    public static final String COMPLETE_STATUS = "complete_status";
    public static final String DELETE = "delete";
    public static final String EDIT_AGAIN = "edit_again";
    public static final String END_LOCATION = "end_location";
    public static final String FINISHED = "finished";
    public static final String INTERNAL_JUMP = "内部切换";
    public static final String IS_CUSTOM_TITLE = "is_custom_title";
    public static final String IS_VOICE = "is_voice";
    public static final String LEFT_SLIDE_DELETE = "left_slide_delete";
    public static final String LONG_CLICK_DELETE = "long_click_delete";
    public static final String LONG_PRESS_DURATION = "long_press_duration";
    public static final String LONG_PRESS_TO_CREATE_RESULT = "long_press_to_create_result";
    public static final String NEW = "new";
    public static final String NORMAL_TODO = "normal";
    public static final String NOTE_PAGE = "笔记首页";
    public static final String OLD = "old";
    public static final String OUTER_JUMP = "外部";
    public static final String RADIO = "radio";
    public static final String REMINDER_FREQUENCY = "reminder_frequency";
    public static final String START_LOCATION = "start_location";
    public static final String SUCCESS = "success";
    public static final String SWITCH_CLICK = "点击";
    public static final String SWITCH_MODE = "switch_mode";
    public static final String SWITCH_SLIDE = "滑动";
    public static final String TODO_CARD_OPERATE = "729.31.0.1.20035";
    public static final String TODO_CREATE_AUDIO = "729.31.2.1.20037";
    public static final String TODO_CREATE_NORMAL = "729.31.2.1.20036";
    public static final String TODO_DRAG = "729.31.0.1.20042";
    public static final String TODO_EDIT = "729.31.1.1.20030";
    public static final String TODO_EDIT_DELETE_AUDIO_CLICK = "729.31.1.1.20031";
    public static final String TODO_EDIT_DONE_CREATE = "729.31.0.1.20029";
    public static final String TODO_EDIT_SET_REMIND_CLICK = "729.31.1.1.20032";
    public static final String TODO_EDIT_SET_REMIND_DELETE = "729.31.1.1.20034";
    public static final String TODO_EDIT_SET_REMIND_DONE_CREATE = "729.31.1.1.20033";
    public static final String TODO_ENTER = "729.31.0.1.20026";
    public static final String TODO_EXIT = "729.31.0.1.20027";
    public static final String TODO_FINISHED_ZOOM_CLICK = "729.31.5.1.20040";
    public static final String TODO_LIST = "list";
    public static final String TODO_LIST_ZOOM_CLICK = "729.31.6.1.20041";
    public static final String TODO_MENU_CLICK = "729.31.7.1.20043";
    public static final String TODO_MICLOUD_CLICK = "729.31.4.1.20039";
    public static final String TODO_NOTE_HOME_OPEN_SOURCE = "todo_note_home_open_source";
    public static final String TODO_NOTE_STATUS = "todo_note_status";
    public static final String TODO_NOTE_TYPE = "todo_note_type";
    public static final String TODO_PAGE = "待办首页";
    public static final String TODO_SEARCHBAR_CLICK = "729.31.3.1.20038";
    public static final String TODO_SWITCH_NOTE_CLICK = "729.31.0.1.20028";
    public static final String TODO_TOOLBAR_CLICK = "729.31.8.1.20044";
    public static final String TODO_TOOLBAR_DELETE_CLICK = "729.31.9.1.20045";
    public static final String UNFINISHED = "unfinished";
    public static final String VALUE_FALSE = "否";
    public static final String VALUE_TRUE = "是";
    public static long mTodoEnter;

    public static void markTodoCardOperate(TodoEntity todoEntity) {
        String str;
        String str2;
        if (todoEntity == null) {
            return;
        }
        if (todoEntity.getListType() == 0) {
            str = "normal";
        } else {
            str = "list";
        }
        if (todoEntity.getIsFinish() == 0) {
            str2 = UNFINISHED;
        } else {
            str2 = FINISHED;
        }
        reportCardOperateEvent(LONG_CLICK_DELETE, str, str2);
    }

    public static void reportCardOperateEvent(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        map.put(TODO_NOTE_TYPE, str2);
        map.put(TODO_NOTE_STATUS, str3);
        reportOperateEvent(TODO_CARD_OPERATE, map);
    }

    private static void reportDoneCreateEvent(String str, String str2, String str3, String str4, String str5) {
        HashMap map = new HashMap();
        map.put(TODO_NOTE_TYPE, str);
        map.put(IS_VOICE, str2);
        map.put(REMINDER_FREQUENCY, str3);
        map.put(IS_CUSTOM_TITLE, str4);
        map.put(COMPLETE_STATUS, str5);
        reportCreateEvent(TODO_EDIT_DONE_CREATE, map);
    }

    public static void markDoneEdit(TodoListContract.Presenter presenter, boolean z) {
        String str;
        if (presenter == null || presenter.getWorkingTodo() == null || presenter.getWorkingTodo().getTodoData() == null) {
            return;
        }
        WorkingTodo workingTodo = presenter.getWorkingTodo();
        TodoEntity todoData = workingTodo.getTodoData();
        int listType = workingTodo.getListType();
        int inputType = todoData.getInputType();
        int workingRepeatMode = workingTodo.getWorkingRepeatMode();
        boolean z2 = true;
        if (listType == 1) {
            SubModeImpl subModeImpl = new SubModeImpl(todoData.getContent());
            boolean zIsEmpty = TextUtils.isEmpty(subModeImpl.getTitle());
            if (zIsEmpty && subModeImpl.getSubTodoSize() == 1) {
                listType = 0;
            }
            z2 = zIsEmpty;
        }
        if (z) {
            str = NEW;
        } else {
            str = EDIT_AGAIN;
        }
        if (TextUtils.isEmpty(todoData.getContent())) {
            str = AUTO_DELETE;
        }
        markDoneEdit(listType, inputType, workingRepeatMode, z2, str);
    }

    public static void markDoneEdit(int i, int i2, int i3, boolean z, String str) {
        String str2;
        if (i == 0) {
            str2 = "normal";
        } else {
            str2 = "list";
        }
        reportDoneCreateEvent(str2, i2 == 1 ? "是" : "否", i3 + "", z ? "否" : "是", str);
    }

    public static void markTodoCardOperate(String str, int i, boolean z) {
        String str2;
        String str3;
        if (i == 0) {
            str2 = "normal";
        } else {
            str2 = "list";
        }
        if (str.equals(LEFT_SLIDE_DELETE)) {
            z = !z;
        }
        if (z) {
            str3 = UNFINISHED;
        } else {
            str3 = FINISHED;
        }
        reportCardOperateEvent(str, str2, str3);
    }
}
