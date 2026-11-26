package com.miui.notes.tool.tapshareutils;

import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.mishare.tapshare.TapSharePreviewBean;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.model.WorkingNote;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.schema.RichEditorContentLiteHandler;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareDataModel {
    public static final String DEFAULT_TITLE = NoteApp.getInstance().getString(R.string.tapshare_notes_without_title);
    private static final String TAG = "TapShareDataModel";

    public static class MultipleNotesTapShareData {
        public List<TapSharePreviewBean> previewBeanList;
        public String subTitle;
        public String title;
        public List<Uri> zipUris;
    }

    public static class SingleNoteTapShareData {
        public long noteId;
        public Uri previewImageUri;
        public String subTitle;
        public String title;
        public Uri zipUri;
    }

    public static SingleNoteTapShareData createSingleNoteData(WorkingNote workingNote) {
        if (!isNoteValid(workingNote)) {
            Logger.INSTANCE.e(TAG, "Invalid working note for single note data");
            return null;
        }
        SingleNoteTapShareData singleNoteTapShareData = new SingleNoteTapShareData();
        singleNoteTapShareData.noteId = workingNote.getNoteId();
        String title = workingNote.getTitle();
        String strExtractPlainText = extractPlainText(workingNote.getContent());
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(strExtractPlainText)) {
            title = DEFAULT_TITLE;
        }
        singleNoteTapShareData.title = title;
        singleNoteTapShareData.subTitle = strExtractPlainText;
        return singleNoteTapShareData;
    }

    public static MultipleNotesTapShareData createMultipleNotesData(List<WorkingNote> list) {
        if (list == null || list.isEmpty()) {
            Logger.INSTANCE.e(TAG, "Invalid working notes list for multiple notes data");
            return null;
        }
        MultipleNotesTapShareData multipleNotesTapShareData = new MultipleNotesTapShareData();
        multipleNotesTapShareData.zipUris = new ArrayList();
        multipleNotesTapShareData.previewBeanList = new ArrayList();
        WorkingNote workingNote = list.get(0);
        String title = workingNote.getTitle();
        String strExtractPlainText = extractPlainText(workingNote.getContent());
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(strExtractPlainText)) {
            title = DEFAULT_TITLE;
        }
        multipleNotesTapShareData.title = title;
        multipleNotesTapShareData.subTitle = strExtractPlainText;
        return multipleNotesTapShareData;
    }

    public static boolean isNoteValid(WorkingNote workingNote) {
        if (workingNote == null || workingNote.getIsInvalid()) {
            return false;
        }
        return (TextUtils.isEmpty(workingNote.getTitle()) && TextUtils.isEmpty(workingNote.getContent())) ? false : true;
    }

    public static String extractPlainText(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new SpannableStringBuilder(NoteSchema.parseAncient(str, new RichEditorContentLiteHandler(), NoteApp.getInstance())).toString().replace(String.valueOf(HtmlParser.MEDIA_DUMMY_CHAR), "").trim();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error extracting plain text: " + e.getMessage());
            return str;
        }
    }

    public static void addPreviewInfo(MultipleNotesTapShareData multipleNotesTapShareData, WorkingNote workingNote) {
        if (multipleNotesTapShareData == null || workingNote == null || !isNoteValid(workingNote)) {
            return;
        }
        String title = workingNote.getTitle();
        String strExtractPlainText = extractPlainText(workingNote.getContent());
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(strExtractPlainText)) {
            title = DEFAULT_TITLE;
        }
        multipleNotesTapShareData.previewBeanList.add(new TapSharePreviewBean(null, title, strExtractPlainText));
    }
}
