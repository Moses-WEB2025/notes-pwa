package com.miui.notes.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteModel;
import com.miui.notes.tool.util.EncryptNewUtil;
import com.miui.notes.tool.util.EncryptUtil;

/* JADX INFO: loaded from: classes4.dex */
public class NoteCursorWrapper extends CursorWrapper {
    private static final String TAG = "StyleableCursorWrapper";
    private Context mContext;
    private int mOriginParentIdColumnIndex;
    private int mParentIdColumnIndex;
    private int mPlainTextColumnIndex;
    private int mSnippetColumnIndex;

    public NoteCursorWrapper(Context context, Cursor cursor) {
        super(cursor);
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        if (applicationContext == null) {
            Logger.INSTANCE.w(TAG, "Fail to get application context");
            this.mContext = context;
        }
        this.mSnippetColumnIndex = cursor.getColumnIndex("snippet");
        this.mParentIdColumnIndex = cursor.getColumnIndex("parent_id");
        this.mOriginParentIdColumnIndex = cursor.getColumnIndex("origin_parent_id");
        this.mPlainTextColumnIndex = cursor.getColumnIndex("plain_text");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        if ((this.mSnippetColumnIndex == i || this.mPlainTextColumnIndex == i) && isPrivateNote()) {
            String string = super.getString(i);
            try {
                if (RomUtils.isHyperOS3Rom() && Utils.isAboveSDK34() && string.startsWith(EncryptNewUtil.MI_UNIFIED_ENCRYPT_FLAG)) {
                    return EncryptNewUtil.decrypt(NoteApp.getInstance(), string);
                }
                return EncryptUtil.decrypt(NoteApp.getInstance(), string);
            } catch (Throwable th) {
                Logger.INSTANCE.e(TAG, "decrypt error: " + th);
                return string;
            }
        }
        return super.getString(i);
    }

    private boolean isPrivateNote() {
        int i = this.mParentIdColumnIndex;
        long j = i >= 0 ? getLong(i) : -4098L;
        int i2 = this.mOriginParentIdColumnIndex;
        return NoteModel.isHidden(j, i2 >= 0 ? getLong(i2) : -4098L);
    }
}
