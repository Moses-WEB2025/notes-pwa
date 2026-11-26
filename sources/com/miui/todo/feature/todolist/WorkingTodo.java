package com.miui.todo.feature.todolist;

import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.utils.CalenderUtils;
import java.util.regex.Matcher;

/* JADX INFO: loaded from: classes4.dex */
public class WorkingTodo {
    private boolean isAudioTodo;
    private boolean isDirty;
    private String mAudioText;
    private Long mCurrentCustomRemindTime;
    private TodoEntity mData;
    private TodoEntity mOldData;
    private int mWorkingIndex;
    private int mWorkingRepeatMode;

    public WorkingTodo(boolean z) {
        this.mOldData = null;
        this.isAudioTodo = false;
        this.mCurrentCustomRemindTime = null;
        this.mWorkingRepeatMode = 0;
        this.mWorkingIndex = -1;
        TodoEntity todoEntity = new TodoEntity();
        this.mData = todoEntity;
        this.isDirty = true;
        this.isAudioTodo = z;
        if (z) {
            todoEntity.setInputType(1);
        } else {
            todoEntity.setInputType(0);
        }
    }

    public WorkingTodo(TodoEntity todoEntity, int i) {
        Matcher audioLabelIndex;
        this.isAudioTodo = false;
        this.mCurrentCustomRemindTime = null;
        this.mWorkingRepeatMode = 0;
        this.mWorkingIndex = -1;
        this.isDirty = false;
        this.mOldData = todoEntity;
        TodoEntity todoEntityCopy = todoEntity.copy();
        this.mData = todoEntityCopy;
        if (todoEntityCopy.getInputType() == 1 && (audioLabelIndex = AudioUtils.getAudioLabelIndex(this.mData.getContent())) != null && audioLabelIndex.find()) {
            this.mAudioText = this.mData.getContent().substring(audioLabelIndex.start(), audioLabelIndex.end() + 1);
        }
        this.mCurrentCustomRemindTime = Long.valueOf(this.mOldData.getRemindTime());
        this.mWorkingIndex = i;
        this.mWorkingRepeatMode = this.mOldData.getRemindRepeatType();
    }

    public void setContent(String str) {
        this.mData.setContent(str);
        this.isDirty = true;
    }

    public void setFinishType(int i) {
        this.mData.setIsFinish(i);
        this.isDirty = true;
    }

    public void setListType(int i) {
        this.mData.setListType(i);
        this.isDirty = true;
    }

    public void setPlaint(String str) {
        this.mData.setPlainText(str);
        this.isDirty = true;
    }

    public int getListType() {
        return this.mData.getListType();
    }

    public boolean cancelRemindTime() {
        this.isDirty = true;
        this.mData.setRemindType(0);
        this.mData.setRemindTime(0L);
        this.mData.setExpireTime(0L);
        this.mData.setRemindRepeatType(0);
        this.mWorkingRepeatMode = 0;
        this.mCurrentCustomRemindTime = null;
        return true;
    }

    public boolean setRemindTime(RemindTimeConfig remindTimeConfig) {
        this.isDirty = true;
        this.mData.setRemindType(remindTimeConfig.isWholeDay ? 1 : 2);
        this.mData.setRemindTime(remindTimeConfig.remindTime);
        this.mData.setFirstRemindTime(remindTimeConfig.remindTime);
        this.mData.setExpireTime(remindTimeConfig.isWholeDay ? CalenderUtils.createDayStartAndExpireTime(remindTimeConfig.remindTime)[1] : remindTimeConfig.remindTime);
        this.mCurrentCustomRemindTime = Long.valueOf(remindTimeConfig.remindTime);
        this.mWorkingRepeatMode = remindTimeConfig.repeatType;
        this.mData.setRemindRepeatType(remindTimeConfig.repeatType);
        if (this.mOldData == null || this.mData.getRemindRepeatType() != this.mOldData.getRemindRepeatType() || this.mData.getRemindTime() != this.mOldData.getRemindTime()) {
            this.mData.setIsFinish(0);
            if (this.mData.getListType() == 1) {
                SubModeImpl subModeImpl = new SubModeImpl(this.mData.getContent());
                subModeImpl.changeFinishStatusAll(false);
                this.mData.setContent(subModeImpl.getContent());
            }
        }
        return true;
    }

    public void setAudioFileName(String str) {
        this.mData.setAudioFileName(str);
    }

    public long getId() {
        return this.mData.getId();
    }

    public TodoEntity createTodo() {
        if (!AudioUtils.hasAudioLabel(this.mData.getContent())) {
            if (isUseAudio()) {
                return null;
            }
            this.mData.setAudioFileName(null);
            this.mData.setInputType(0);
            String content = this.mData.getContent();
            if (!TextUtils.isEmpty(content)) {
                String strExtractPlainText = NoteSchema.extractPlainText(this.mData.getContent());
                TodoEntity todoEntity = this.mData;
                if (LiteUtils.isNewLiteOrMiddle()) {
                    strExtractPlainText = strExtractPlainText.replaceAll("\n$", "");
                }
                todoEntity.setContent(strExtractPlainText);
            }
            if (this.mData.getListType() == 1) {
                this.mData.setPlainText(new SubModeImpl(content).getPlainTextForSearch());
            } else {
                TodoEntity todoEntity2 = this.mData;
                todoEntity2.setPlainText(todoEntity2.getContent());
            }
        } else {
            Matcher audioLabelIndex = AudioUtils.getAudioLabelIndex(this.mData.getContent());
            if (audioLabelIndex != null && audioLabelIndex.find()) {
                String strSubstring = this.mData.getContent().substring(audioLabelIndex.start(), audioLabelIndex.end() + 1);
                String strReplace = this.mData.getContent().replace(strSubstring, "");
                if (!TextUtils.isEmpty(strReplace)) {
                    this.mData.setPlainText(NoteSchema.extractPlainText(strReplace).replaceAll("\\n+$", ""));
                } else {
                    this.mData.setPlainText("");
                }
                this.mData.setContent(strSubstring + this.mData.getPlainText());
            }
        }
        return this.mData;
    }

    public Long getCurrentCustomRemindTime() {
        return this.mCurrentCustomRemindTime;
    }

    public TodoEntity getOldTodoData() {
        return this.mOldData;
    }

    public void restoreUnSaveData(TodoEntity todoEntity) {
        this.mData = todoEntity;
    }

    public TodoEntity getTodoData() {
        return this.mData;
    }

    public int getWorkingIndex() {
        return this.mWorkingIndex;
    }

    public int getWorkingRepeatMode() {
        return this.mWorkingRepeatMode;
    }

    public boolean isUseAudio() {
        return this.isAudioTodo;
    }

    public boolean isEdited() {
        return this.isDirty;
    }

    public boolean isNeedCancelRemind() {
        return this.mOldData.getRemindType() != 0 && this.mData.getRemindType() == 0;
    }

    public boolean isRemindTimeChanged() {
        return this.mOldData == null || this.mData.getRemindTime() != this.mOldData.getRemindTime();
    }

    public boolean isInNewDataMode() {
        return this.mOldData == null;
    }

    public static TodoEntity createRepeatTodo(TodoEntity todoEntity) {
        TodoEntity todoEntityCopy = todoEntity.copy();
        todoEntityCopy.setIsFinish(0);
        todoEntityCopy.setFirstRemindTime(todoEntity.getFirstRemindTime() == 0 ? todoEntity.getRemindTime() : todoEntity.getFirstRemindTime());
        todoEntityCopy.setRemindTime(TimeUtils.getNextRemindTime(todoEntityCopy.getRemindRepeatType(), todoEntityCopy.getRemindTime(), todoEntityCopy.getFirstRemindTime()));
        return todoEntityCopy;
    }
}
