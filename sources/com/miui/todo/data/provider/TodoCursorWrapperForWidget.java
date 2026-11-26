package com.miui.todo.data.provider;

import android.database.Cursor;
import android.database.CursorWrapper;
import org.apache.commons.text.StringEscapeUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoCursorWrapperForWidget extends CursorWrapper {
    private final int mContentColumnIndex;

    public TodoCursorWrapperForWidget(Cursor cursor) {
        super(cursor);
        this.mContentColumnIndex = cursor.getColumnIndex("content");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        if (this.mContentColumnIndex == i) {
            return StringEscapeUtils.unescapeHtml4(super.getString(i));
        }
        return super.getString(i);
    }
}
