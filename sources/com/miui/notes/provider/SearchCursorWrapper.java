package com.miui.notes.provider;

import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.SnippetFormatter;

/* JADX INFO: loaded from: classes4.dex */
public class SearchCursorWrapper extends CursorWrapper {
    private static final String[] COLUMNS = {"suggest_text_1", "suggest_intent_data_id", "suggest_intent_extra_data", "suggest_shortcut_id"};
    private static final int COLUMN_INTENT_DATA_ID_INDEX = 1;
    private static final int COLUMN_INTENT_EXTRA_DATA_INDEX = 2;
    private static final int COLUMN_SHORTCUT_ID_INDEX = 3;
    private static final int COLUMN_TEXT_1_INDEX = 0;
    private static final String ELLIPSIS = "…";
    private static final String TAG = "StyleableCursorWrapper";
    private Context mContext;
    private int mIdColumnIndex;
    private String mPattern;
    private int mSnippetColumnIndex;

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        return 3;
    }

    public SearchCursorWrapper(Context context, Cursor cursor, String str) {
        super(cursor);
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        if (applicationContext == null) {
            Logger.INSTANCE.w(TAG, "Fail to get application context");
            this.mContext = context;
        }
        this.mPattern = str;
        this.mIdColumnIndex = cursor.getColumnIndex("_id");
        this.mSnippetColumnIndex = cursor.getColumnIndex("snippet");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnIndex(String str) {
        int i = 0;
        while (true) {
            String[] strArr = COLUMNS;
            if (i >= strArr.length) {
                return -1;
            }
            if (strArr[i].equalsIgnoreCase(str)) {
                return i;
            }
            i++;
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
        int columnIndex = getColumnIndex(str);
        if (columnIndex >= 0) {
            return columnIndex;
        }
        throw new IllegalArgumentException("column '" + str + "' does not exist");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getColumnName(int i) {
        return COLUMNS[i];
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String[] getColumnNames() {
        return COLUMNS;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        if (i == 0) {
            return super.isNull(this.mSnippetColumnIndex);
        }
        if (i != 1) {
            if (i == 2) {
                return false;
            }
            if (i != 3) {
                Logger.INSTANCE.w(TAG, "getString: unknown column index " + i);
                return true;
            }
        }
        return super.isNull(this.mIdColumnIndex);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        if (i == 0) {
            String string = super.getString(this.mSnippetColumnIndex);
            return !TextUtils.isEmpty(string) ? trimSnippet(SnippetFormatter.format(this.mContext, string, this.mPattern).toString(), this.mPattern, 18) : string;
        }
        if (i != 1) {
            if (i == 2) {
                return this.mPattern;
            }
            if (i != 3) {
                Logger.INSTANCE.w(TAG, "getString: unknown column index " + i);
                return null;
            }
        }
        return super.getString(this.mIdColumnIndex);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        throw new IllegalArgumentException("Wrong data type for column " + i);
    }

    private String trimSnippet(String str, String str2, int i) {
        if (str.length() > i) {
            int length = i - str2.length();
            if (length <= 0) {
                return ELLIPSIS + str2.substring(0, i - 2) + ELLIPSIS;
            }
            int i2 = length / 2;
            int iIndexOf = str.indexOf(str2);
            if (iIndexOf >= 0) {
                int length2 = str2.length() + iIndexOf;
                int iMin = Math.min(iIndexOf, i2);
                int iMin2 = Math.min(str.length() - length2, i2);
                if (iMin2 < i2) {
                    iMin = Math.min(iIndexOf, length - iMin2);
                }
                if (iMin < i2) {
                    iMin2 = Math.min(str.length() - length2, length - iMin);
                }
                StringBuilder sb = new StringBuilder();
                if (iMin < iIndexOf) {
                    sb.append(ELLIPSIS);
                    sb.append(str.substring((iIndexOf - iMin) + ELLIPSIS.length(), iIndexOf));
                } else {
                    sb.append(str.substring(0, iMin));
                }
                sb.append(str2);
                if (iMin2 < str.length() - length2) {
                    sb.append(str.substring(length2, (iMin2 + length2) - ELLIPSIS.length()));
                    sb.append(ELLIPSIS);
                } else {
                    sb.append(str.substring(length2));
                }
                return sb.toString();
            }
        }
        return str;
    }
}
