package com.miui.notes.adapter;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.miui.common.tool.Logger;
import com.miui.notes.provider.Notes;
import com.miui.richeditor.schema.NoteSchema;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class SearchCursorAdapter {
    private int mContentColumnIndex;
    protected Cursor mCursor;
    private int mMindContentColumnIndex;
    private String mSearchString;
    protected SparseIntArray mSparseIntArray = new SparseIntArray();
    private int mTitleColumnIndex;

    public SearchCursorAdapter(Cursor cursor, String str) {
        this.mCursor = cursor;
        this.mSearchString = str;
        this.mContentColumnIndex = cursor.getColumnIndex("snippet");
        this.mTitleColumnIndex = cursor.getColumnIndex("title");
        this.mMindContentColumnIndex = cursor.getColumnIndex(Notes.Note.MIND_CONTENT_PLAIN_TEXT);
        if (TextUtils.isEmpty(this.mSearchString)) {
            return;
        }
        try {
            refreshSparseArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SearchCursorAdapter(Cursor cursor, ArrayList<Long> arrayList) {
        this.mCursor = cursor;
    }

    public void refreshSparseArray() {
        SparseIntArray sparseIntArray = this.mSparseIntArray;
        if (sparseIntArray != null) {
            sparseIntArray.clear();
        } else {
            this.mSparseIntArray = new SparseIntArray();
        }
        Cursor cursor = this.mCursor;
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        this.mCursor.moveToPosition(-1);
        int i = 0;
        int i2 = 0;
        while (this.mCursor.moveToNext()) {
            String strExtractPlainText = NoteSchema.extractPlainText(this.mCursor.getString(this.mContentColumnIndex));
            String string = this.mCursor.getString(this.mTitleColumnIndex);
            String string2 = this.mCursor.getString(this.mMindContentColumnIndex);
            String strExtractPlainText2 = !TextUtils.isEmpty(string) ? NoteSchema.extractPlainText(string) : "";
            if (string2.toLowerCase().contains(this.mSearchString.toLowerCase()) || strExtractPlainText.toLowerCase().contains(this.mSearchString.toLowerCase()) || strExtractPlainText2.toLowerCase().contains(this.mSearchString.toLowerCase())) {
                this.mSparseIntArray.put(i2, i);
                i2++;
            }
            i++;
        }
    }

    public int getCount() {
        SparseIntArray sparseIntArray;
        if (TextUtils.isEmpty(this.mSearchString)) {
            Cursor cursor = this.mCursor;
            if (cursor == null || cursor.isClosed()) {
                return 0;
            }
            return this.mCursor.getCount();
        }
        Cursor cursor2 = this.mCursor;
        if (cursor2 == null || cursor2.isClosed() || (sparseIntArray = this.mSparseIntArray) == null) {
            return 0;
        }
        return sparseIntArray.size();
    }

    public boolean moveToPosition(int i) {
        try {
            Cursor cursor = this.mCursor;
            if (cursor != null && this.mSparseIntArray != null) {
                if (!TextUtils.isEmpty(this.mSearchString)) {
                    i = this.mSparseIntArray.get(i);
                }
                if (cursor.moveToPosition(i)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Logger.INSTANCE.d("SearchCursorAdapter", "moveToPosition error ," + e.getMessage());
            return false;
        }
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public void setCursor(Cursor cursor) {
        this.mCursor = cursor;
    }
}
