package com.miui.notes.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.text.Spanned;
import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.SnippetFormatter;
import com.miui.richeditor.schema.EditorHtml;
import com.miui.richeditor.schema.NoteSchema;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class StyleableCursorWrapper extends CursorWrapper {
    private static final String TAG = "StyleableCursorWrapper";
    private Context mContext;
    private boolean mKeepStyle;
    private int mPlainTextColumnIndex;
    private int mSnippetColumnIndex;

    public StyleableCursorWrapper(Context context, Cursor cursor, boolean z) {
        super(cursor);
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        if (applicationContext == null) {
            Logger.INSTANCE.w(TAG, "Fail to get application context");
            this.mContext = context;
        }
        this.mSnippetColumnIndex = cursor.getColumnIndex("snippet");
        this.mKeepStyle = z;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        if (this.mSnippetColumnIndex == i) {
            String string = super.getString(i);
            CharSequence charSequence = SnippetFormatter.format(this.mContext, string, null);
            if (this.mKeepStyle) {
                if (charSequence instanceof Spanned) {
                    return EditorHtml.toHtml(this.mContext, (Spanned) charSequence);
                }
                return EditorHtml.escapeHtml(charSequence.toString());
            }
            if (TextUtils.isEmpty(charSequence.toString().replaceAll(StringUtils.LF, ""))) {
                if (AudioUtils.hasAudioLabel(string)) {
                    return this.mContext.getResources().getString(R.string.snippet_audio_stub);
                }
                if (!TextUtils.isEmpty(NoteSchema.retrieveFirstImage(string))) {
                    return this.mContext.getResources().getString(R.string.snippet_image_stub);
                }
                return charSequence.toString();
            }
            return charSequence.toString();
        }
        return super.getString(i);
    }
}
