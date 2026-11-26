package com.miui.common.base;

import android.text.TextUtils;
import ch.qos.logback.classic.net.SyslogAppender;
import com.google.gson.Gson;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.WorkingNote;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class NoteLoadDataBuilder {
    public static NoteLoadDataEntity create(WorkingNote workingNote) {
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity();
        noteLoadDataEntity.setNoteId(workingNote.getNoteId());
        noteLoadDataEntity.setFolderId(workingNote.getFolderId());
        if (TextUtils.equals(workingNote.getWorkingNoteType(), "common")) {
            noteLoadDataEntity.setNoteType(0);
        } else {
            noteLoadDataEntity.setNoteType(1);
            String workingMindContent = workingNote.getWorkingMindContent();
            noteLoadDataEntity.setMindContentType(TextUtils.isEmpty(workingMindContent) ? 0 : ((MindEntity) new Gson().fromJson(workingMindContent.substring(15), MindEntity.class)).getContentType());
        }
        noteLoadDataEntity.setModifiedTime(workingNote.getModifiedDate());
        noteLoadDataEntity.setWidgetId(workingNote.getWidgetId());
        noteLoadDataEntity.setWidgetType(workingNote.getWidgetType());
        return noteLoadDataEntity;
    }

    public static NoteLoadDataEntity createMindNoteFromNormalNote(int i, String str) {
        if (str == null) {
            str = "";
        }
        return new NoteLoadDataEntity(1, i, 0, str.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"").replace(StringUtils.LF, "\\n").replace(StringUtils.CR, "\\r").replace(SyslogAppender.DEFAULT_STACKTRACE_PATTERN, "\\t"), false);
    }
}
