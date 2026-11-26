package com.miui.notes.cache;

import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.model.NoteModel;
import com.miui.notes.tool.SnippetFormatter;
import com.miui.notes.tool.util.DateHelper;
import com.miui.richeditor.schema.NoteSchema;
import defpackage.R2;
import java.util.ArrayList;
import miuix.pickerwidget.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteCache {
    public static final int TYPE_HAND_WRITE = 6;
    public static final int TYPE_HAS_AUDIO = 3;
    public static final int TYPE_HAS_PIC_AND_TEXT = 4;
    public static final int TYPE_MI_MIND = 5;
    public static final int TYPE_MULTI_HAND_WRITE = 7;
    public static final int TYPE_ONLY_PIC = 2;
    public static final int TYPE_ONLY_TEXT = 1;
    public static final int TYPE_UNKNOWN = 0;
    private boolean mExpired;
    public String mFirstImage;
    private CharSequence mFormattedSnippet;
    private NoteModel mNote;
    private String mSearchToken;
    private int mType = 0;
    private ArrayList<String> mHighlightKeywords = null;
    private StringBuilder mFormattedSortDateSb = new StringBuilder();

    public NoteCache(NoteModel noteModel) {
        this.mNote = noteModel;
    }

    private void parseContentType() {
        if (!this.mNote.isHiddenDeleted()) {
            this.mFirstImage = NoteSchema.retrieveFirstImage(this.mNote.getSnippet());
        }
        if (AudioUtils.hasAudioLabel(this.mNote.getSnippet())) {
            this.mType = 3;
        } else {
            String strReplaceAll = TextProcessUtils.removeCheckboxTag(NoteSchema.removeImagesTag(this.mNote.getSnippet())).replaceAll(StringUtils.LF, "").replaceAll(StringUtils.SPACE, "").replaceAll("<textindent=\"1\"></text>", "");
            if (!TextUtils.isEmpty(this.mFirstImage)) {
                if (strReplaceAll.length() == 0) {
                    this.mType = 2;
                } else {
                    this.mType = 4;
                }
            } else {
                this.mType = 1;
            }
        }
        if (this.mNote.getNoteType().equals(NoteModel.NoteType.TYPE_MIND)) {
            this.mType = 5;
        } else if (this.mNote.getNoteType().equals("handwrite")) {
            this.mType = 6;
        } else if (this.mNote.getNoteType().equals("multi_handwrite")) {
            this.mType = 7;
        }
    }

    public NoteModel getNote() {
        return this.mNote;
    }

    public boolean isExpired() {
        return this.mExpired;
    }

    public void setExpired(boolean z) {
        this.mExpired = z;
    }

    public void update(NoteModel noteModel) {
        NoteModel noteModel2 = this.mNote;
        if (noteModel2 == noteModel) {
            return;
        }
        if (!noteModel2.getSnippet().equals(noteModel.getSnippet())) {
            this.mFormattedSnippet = null;
            this.mType = 0;
        }
        this.mNote = noteModel;
        this.mExpired = false;
    }

    public synchronized CharSequence getFormattedSortedDate() {
        StringBuilder sb = this.mFormattedSortDateSb;
        sb.delete(0, sb.length());
        long sortDate = this.mNote.getSortDate();
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zIsSameYear = DateHelper.isSameYear(sortDate, jCurrentTimeMillis);
        boolean zIsSameWeek = DateHelper.isSameWeek(sortDate, jCurrentTimeMillis);
        boolean zIsYesterday = DateHelper.isYesterday(sortDate);
        if (zIsSameWeek || zIsYesterday) {
            this.mFormattedSortDateSb.append(DateUtils.formatRelativeTime(NoteApp.getInstance(), sortDate, true));
        } else if (!zIsSameYear) {
            this.mFormattedSortDateSb.append(DateUtils.formatDateTime(NoteApp.getInstance(), this.mNote.getSortDate(), 896));
        } else {
            this.mFormattedSortDateSb.append(DateUtils.formatDateTime(NoteApp.getInstance(), this.mNote.getSortDate(), R2.attr.alignItems));
        }
        return this.mFormattedSortDateSb;
    }

    public CharSequence getFormattedSnippet(String str) {
        return getFormattedSnippet(str, this.mNote.getSnippet());
    }

    public CharSequence getFormattedSnippet(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            str2 = this.mNote.getSnippet();
        }
        if (this.mNote.isHiddenDeleted()) {
            return NoteApp.getInstance().getString(R.string.hint_cannot_view_hidden_note_content);
        }
        if (this.mFormattedSnippet != null) {
            if (TextUtils.isEmpty(str)) {
                if (TextUtils.isEmpty(this.mSearchToken)) {
                    return this.mFormattedSnippet;
                }
            } else if (str.equals(this.mSearchToken)) {
                return this.mFormattedSnippet;
            }
        }
        this.mSearchToken = str;
        CharSequence charSequence = SnippetFormatter.format(NoteApp.getInstance(), str2, str);
        this.mFormattedSnippet = charSequence;
        return charSequence;
    }

    public int getNoteContentType() {
        if (this.mType == 0) {
            parseContentType();
        }
        return this.mType;
    }

    public String getFirstImage() {
        getNoteContentType();
        return this.mFirstImage;
    }

    public ArrayList<String> getHighlightKeywords() {
        return this.mHighlightKeywords;
    }

    public void setHighlightKeywords(ArrayList<String> arrayList) {
        this.mHighlightKeywords = arrayList;
    }
}
