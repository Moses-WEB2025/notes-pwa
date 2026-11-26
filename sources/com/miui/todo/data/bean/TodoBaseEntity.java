package com.miui.todo.data.bean;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoBaseEntity {
    protected String audioFileName;
    protected int colorLabel;
    protected String content;
    private long firstRemindTime;
    protected int inputType;
    private int listType;
    protected String plainText;
    protected int remindRepeatType;
    protected long remindTime;
    protected int remindType;

    public TodoBaseEntity() {
    }

    public TodoBaseEntity(String str, long j, int i, int i2, String str2, String str3, int i3, int i4, int i5, long j2) {
        this.content = str;
        this.remindTime = j;
        this.remindType = i;
        this.inputType = i2;
        this.audioFileName = str2;
        this.plainText = str3;
        this.remindRepeatType = i3;
        this.colorLabel = i4;
        this.listType = i5;
        this.firstRemindTime = j2;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public long getRemindTime() {
        return this.remindTime;
    }

    public void setRemindTime(long j) {
        this.remindTime = j;
    }

    public int getRemindType() {
        return this.remindType;
    }

    public void setRemindType(int i) {
        this.remindType = i;
    }

    public int getInputType() {
        return this.inputType;
    }

    public void setInputType(int i) {
        this.inputType = i;
    }

    public String getAudioFileName() {
        return this.audioFileName;
    }

    public void setAudioFileName(String str) {
        this.audioFileName = str;
    }

    public String getPlainText() {
        if (TextUtils.isEmpty(this.plainText)) {
            return "";
        }
        return this.plainText;
    }

    public void setPlainText(String str) {
        this.plainText = str;
    }

    public int getRemindRepeatType() {
        return this.remindRepeatType;
    }

    public void setRemindRepeatType(int i) {
        this.remindRepeatType = i;
    }

    public int getColorLabel() {
        return this.colorLabel;
    }

    public void setColorLabel(int i) {
        this.colorLabel = i;
    }

    public int getListType() {
        return this.listType;
    }

    public void setListType(int i) {
        this.listType = i;
    }

    public long getFirstRemindTime() {
        return this.firstRemindTime;
    }

    public void setFirstRemindTime(long j) {
        this.firstRemindTime = j;
    }
}
