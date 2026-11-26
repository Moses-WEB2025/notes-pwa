package com.miui.notes.tool;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.aios.apptoolsdk.AnnotatedToolService;
import com.aios.apptoolsdk.AppResult;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.provider.Notes;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NotesToolService extends AnnotatedToolService {
    private static final Set<Long> sPendingChangedNoteIds = ConcurrentHashMap.newKeySet();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = ThreadLocal.withInitial(new Supplier() { // from class: com.miui.notes.tool.NotesToolService$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return NotesToolService.lambda$static$0();
        }
    });
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_SHORT_THREAD_LOCAL = ThreadLocal.withInitial(new Supplier() { // from class: com.miui.notes.tool.NotesToolService$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return NotesToolService.lambda$static$1();
        }
    });

    public static boolean consumeIfChanged(long j) {
        return sPendingChangedNoteIds.remove(Long.valueOf(j));
    }

    private void notifyNoteChanged(long j) {
        if (j > 0) {
            sPendingChangedNoteIds.add(Long.valueOf(j));
            getContentResolver().notifyChange(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j), null);
        }
    }

    static /* synthetic */ SimpleDateFormat lambda$static$0() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    }

    static /* synthetic */ SimpleDateFormat lambda$static$1() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    private SimpleDateFormat getSafeDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

    public AppResult readNotes(Integer num, String str, String str2) {
        return readNotesInternal(num, str, str2);
    }

    private AppResult readNotesInternal(Integer num, String str, String str2) {
        if (num == null || num.intValue() <= 0) {
            num = 10;
        }
        TimeParseResult timeParams = parseTimeParams(str, str2);
        if (timeParams.errorResult != null) {
            return timeParams.errorResult;
        }
        SelectionAndArgs selectionAndArgsBuildSelection = buildSelection(timeParams.startMs, timeParams.endMs);
        return executeNoteQuery(num.intValue(), selectionAndArgsBuildSelection.selection, selectionAndArgsBuildSelection.args);
    }

    private static class TimeParseResult {
        Long endMs;
        AppResult errorResult;
        Long startMs;

        private TimeParseResult() {
        }
    }

    private static class SelectionAndArgs {
        String[] args;
        String selection;

        private SelectionAndArgs() {
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0017  */
    /* JADX WARN: Code duplicated, block: B:9:0x001d  */
    private TimeParseResult parseTimeParams(String str, String str2) {
        TimeParseResult timeParseResult = new TimeParseResult();
        if (!TextUtils.isEmpty(str)) {
            timeParseResult.errorResult = parseStartTime(timeParseResult, str);
            if (timeParseResult.errorResult == null) {
                if (!TextUtils.isEmpty(str2)) {
                    timeParseResult.errorResult = parseEndTime(timeParseResult, str2);
                }
            }
        } else if (!TextUtils.isEmpty(str2)) {
            timeParseResult.errorResult = parseEndTime(timeParseResult, str2);
        }
        return timeParseResult;
    }

    private AppResult parseStartTime(TimeParseResult timeParseResult, String str) {
        if (timeParseResult == null) {
            return AppResult.error("内部错误：result 不能为空");
        }
        timeParseResult.startMs = Long.valueOf(parseTime(str));
        if (timeParseResult.startMs.longValue() < 0) {
            return AppResult.error("start_time 格式错误，请使用 yyyy-MM-dd 或 yyyy-MM-dd HH:mm");
        }
        return null;
    }

    private AppResult parseEndTime(TimeParseResult timeParseResult, String str) {
        if (timeParseResult == null) {
            return AppResult.error("内部错误：result 不能为空");
        }
        timeParseResult.endMs = Long.valueOf(parseTime(str));
        if (timeParseResult.endMs.longValue() < 0) {
            return AppResult.error("end_time 格式错误，请使用 yyyy-MM-dd 或 yyyy-MM-dd HH:mm");
        }
        if (str.contains(":")) {
            return null;
        }
        timeParseResult.endMs = Long.valueOf(timeParseResult.endMs.longValue() + 86399999);
        return null;
    }

    private SelectionAndArgs buildSelection(Long l, Long l2) {
        String str;
        ArrayList arrayList = new ArrayList();
        if (l == null) {
            str = "type=0 AND parent_id!=-3";
        } else {
            arrayList.add(String.valueOf(l));
            str = "type=0 AND parent_id!=-3 AND modified_date>=?";
        }
        if (l2 != null) {
            str = str + " AND modified_date<=?";
            arrayList.add(String.valueOf(l2));
        }
        SelectionAndArgs selectionAndArgs = new SelectionAndArgs();
        selectionAndArgs.selection = str;
        selectionAndArgs.args = arrayList.isEmpty() ? null : (String[]) arrayList.toArray(new String[0]);
        return selectionAndArgs;
    }

    private AppResult executeNoteQuery(int i, String str, String[] strArr) {
        AppResult appResultProcessCursor;
        try {
            Cursor cursorQuery = getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"_id", "title", "snippet", "modified_date", Notes.Note.NOTE_CONTENT_TYPE}, str, strArr, "modified_date DESC");
            try {
                if (cursorQuery == null) {
                    appResultProcessCursor = AppResult.success("未找到笔记");
                    if (cursorQuery == null) {
                        return appResultProcessCursor;
                    }
                } else {
                    appResultProcessCursor = processCursor(cursorQuery, i);
                    if (cursorQuery != null) {
                    }
                    return appResultProcessCursor;
                }
                cursorQuery.close();
                return appResultProcessCursor;
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (Exception e) {
            return AppResult.error("查询失败: " + e.getMessage());
        }
    }

    private AppResult processCursor(Cursor cursor, int i) {
        if (cursor.getColumnCount() < 5) {
            return AppResult.error("游标列数不足，期望至少5列，实际" + cursor.getColumnCount() + "列");
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (cursor.moveToNext() && i2 < i) {
            long j = cursor.getLong(0);
            String string = cursor.getString(1);
            if (string == null) {
                string = "";
            }
            String string2 = cursor.getString(2);
            String str = string2 != null ? string2 : "";
            long j2 = cursor.getLong(3);
            String string3 = cursor.getString(4);
            if (string3 == null) {
                string3 = "common";
            }
            if (str.length() > 200) {
                str = str.substring(0, 200) + "...";
            }
            sb.append("ID:").append(j).append(" | 标题:").append(string).append(" | 类型:").append(string3).append(" | 修改时间:").append(getSafeDateFormat().format(new Date(j2))).append(" | 内容:").append(str).append(StringUtils.LF);
            i2++;
        }
        if (i2 == 0) {
            return AppResult.success("该时间段内暂无笔记");
        }
        return AppResult.success(sb.toString().trim());
    }

    public AppResult createNote(String str, String str2) {
        return createNoteInternal(str, str2);
    }

    private AppResult createNoteInternal(String str, String str2) throws Throwable {
        if (str == null || str.isEmpty()) {
            return AppResult.error("缺少 content 参数");
        }
        try {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setContent(toRichText(str));
            noteEntity.setParentId(0L);
            if (str2 != null && !str2.isEmpty()) {
                noteEntity.setTitle(str2);
            }
            Uri uriInsertNote = noteEntity.insertNote(this);
            if (uriInsertNote != null) {
                try {
                    long id = ContentUris.parseId(uriInsertNote);
                    if (id > 0) {
                        return AppResult.success("笔记创建成功，ID: " + id);
                    }
                } catch (NumberFormatException unused) {
                    return AppResult.error("笔记创建失败：返回的 Uri 格式异常");
                }
            }
            return AppResult.error("笔记创建失败");
        } catch (Exception e) {
            return AppResult.error("创建失败: " + e.getMessage());
        }
    }

    public AppResult editNoteWithContext(Long l, String str) {
        return appendNoteInternal(l, str);
    }

    private AppResult appendNoteInternal(Long l, String str) {
        if (l == null) {
            return AppResult.error("缺少 note_id 参数");
        }
        if (str == null || str.isEmpty()) {
            return AppResult.error("缺少 append_content 参数");
        }
        try {
            NoteEntity noteEntityLoadById = NoteEntityHelper.loadById(this, l.longValue());
            if (noteEntityLoadById == null) {
                return AppResult.error("未找到 ID 为 " + l + " 的笔记");
            }
            String noteType = noteEntityLoadById.getNoteType();
            if (!isValidNoteType(noteType)) {
                return AppResult.error("该笔记类型(" + noteType + ")暂不支持追加内容");
            }
            String content = noteEntityLoadById.getContent();
            if (content == null) {
                content = "";
            }
            noteEntityLoadById.setContent(content + StringUtils.LF + toRichText(str));
            noteEntityLoadById.setModifiedDate(System.currentTimeMillis());
            if (noteEntityLoadById.syncNote(this)) {
                notifyNoteChanged(l.longValue());
                return AppResult.success("笔记 ID:" + l + " 文字追加成功");
            }
            return AppResult.error("笔记 ID:" + l + " 文字追加失败");
        } catch (Exception e) {
            return AppResult.error("编辑失败: " + e.getMessage());
        }
    }

    public AppResult editNoteWithImage(Long l, String str) {
        return appendNoteWithImageInternal(l, str);
    }

    private AppResult appendNoteWithImageInternal(Long l, String str) throws Throwable {
        if (l == null) {
            return AppResult.error("缺少 note_id 参数");
        }
        if (str == null || str.isEmpty()) {
            return AppResult.error("缺少 image_uri 参数");
        }
        try {
            NoteEntity noteEntityLoadById = NoteEntityHelper.loadById(this, l.longValue());
            if (noteEntityLoadById == null) {
                return AppResult.error("未找到 ID 为 " + l + " 的笔记");
            }
            String noteType = noteEntityLoadById.getNoteType();
            if (!isValidNoteType(noteType)) {
                return AppResult.error("该笔记类型(" + noteType + ")暂不支持追加内容");
            }
            String strCreateImageTagFromUri = createImageTagFromUri(str);
            if (strCreateImageTagFromUri == null) {
                return AppResult.error("图片保存失败，请检查图片URI是否有效");
            }
            String content = noteEntityLoadById.getContent();
            if (content == null) {
                content = "";
            }
            noteEntityLoadById.setContent(content + StringUtils.LF + strCreateImageTagFromUri);
            noteEntityLoadById.setModifiedDate(System.currentTimeMillis());
            if (noteEntityLoadById.syncNote(this)) {
                notifyNoteChanged(l.longValue());
                return AppResult.success("笔记 ID:" + l + " 图片追加成功");
            }
            return AppResult.error("笔记 ID:" + l + " 图片追加失败");
        } catch (Exception e) {
            return AppResult.error("编辑失败: " + e.getMessage());
        }
    }

    private boolean isValidNoteType(String str) {
        return "common".equals(str) || TextUtils.isEmpty(str);
    }

    private String createImageTagFromUri(String str) throws Throwable {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String strSaveImageFile = AttachmentUtils.saveImageFile((Context) this, parseImageUri(str), true);
        if (TextUtils.isEmpty(strSaveImageFile)) {
            return null;
        }
        return "<img fileid=\"" + strSaveImageFile + "\" imgshow=\"0\" imgdes=\"\" />";
    }

    private Uri parseImageUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("file://")) {
            return Uri.parse(str);
        }
        if (str.startsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            return Uri.fromFile(new File(str));
        }
        return Uri.parse(str);
    }

    private String toRichText(String str) {
        if (str == null) {
            return "";
        }
        String[] strArrSplit = str.split(StringUtils.LF, -1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArrSplit.length; i++) {
            String str2 = strArrSplit[i];
            if (str2.isEmpty() || str2.startsWith("<")) {
                sb.append(str2);
            } else {
                sb.append("<text indent=\"1\">").append(str2).append("</text>");
            }
            if (i < strArrSplit.length - 1) {
                sb.append(StringUtils.LF);
            }
        }
        return sb.toString();
    }

    private long parseTime(String str) {
        try {
            if (str.contains(":")) {
                return getSafeDateFormat().parse(str).getTime();
            }
            return DATE_FORMAT_SHORT_THREAD_LOCAL.get().parse(str).getTime();
        } catch (Exception unused) {
            return -1L;
        }
    }

    public AppResult createNoteWithImage(String str, String str2, String str3) {
        return createNoteWithImageInternal(str, str2, str3);
    }

    private AppResult createNoteWithImageInternal(String str, String str2, String str3) throws Throwable {
        if (str == null || str.isEmpty()) {
            return AppResult.error("缺少 image_uri 参数");
        }
        try {
            String strSaveImageFile = AttachmentUtils.saveImageFile((Context) this, parseImageUri(str), true);
            if (TextUtils.isEmpty(strSaveImageFile)) {
                return AppResult.error("图片保存失败，请检查图片URI是否有效");
            }
            String str4 = "<img fileid=\"" + strSaveImageFile + "\" imgshow=\"0\" imgdes=\"\" />";
            if (str2 != null && !str2.isEmpty()) {
                str4 = toRichText(str2) + StringUtils.LF + str4;
            }
            return createNoteEntity(str4, str3);
        } catch (Exception e) {
            return AppResult.error("创建笔记时发生异常：" + e.getMessage());
        }
    }

    private AppResult createNoteEntity(String str, String str2) throws Throwable {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setContent(str);
        noteEntity.setParentId(0L);
        if (str2 != null && !str2.isEmpty()) {
            noteEntity.setTitle(str2);
        }
        Uri uriInsertNote = noteEntity.insertNote(this);
        if (uriInsertNote != null) {
            try {
                long id = ContentUris.parseId(uriInsertNote);
                if (id > 0) {
                    return AppResult.success("带图片的笔记创建成功，ID: " + id);
                }
            } catch (NumberFormatException unused) {
                return AppResult.error("笔记创建失败：返回的 Uri 格式异常");
            }
        }
        return AppResult.error("笔记创建失败");
    }

    public AppResult findNoteId(String str, Integer num) {
        return findNoteIdInternal(str, num);
    }

    private AppResult findNoteIdInternal(String str, Integer num) {
        if (str == null || str.isEmpty()) {
            return AppResult.error("缺少 keyword 参数");
        }
        if (num == null || num.intValue() <= 0) {
            num = 5;
        }
        Cursor cursorQuery = null;
        try {
            cursorQuery = getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"_id", "title", "modified_date"}, "type=0 AND parent_id!=-3 AND (title LIKE ? OR snippet LIKE ? OR plain_text LIKE ?)", new String[]{"%" + str + "%", "%" + str + "%", "%" + str + "%"}, "modified_date DESC");
            return processCursorResults(cursorQuery, str, num.intValue());
        } catch (Exception e) {
            return AppResult.error("查找失败: " + e.getMessage());
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    private AppResult processCursorResults(Cursor cursor, String str, int i) {
        if (cursor == null) {
            return AppResult.success("未找到包含\"" + str + "\"的笔记");
        }
        StringBuilder sb = new StringBuilder();
        if (buildResultFromCursor(cursor, sb, i) == 0) {
            return AppResult.success("未找到包含\"" + str + "\"的笔记");
        }
        return AppResult.success(sb.toString().trim());
    }

    private int buildResultFromCursor(Cursor cursor, StringBuilder sb, int i) {
        int i2 = 0;
        while (cursor.moveToNext() && i2 < i) {
            long j = cursor.getLong(0);
            String string = cursor.getString(1);
            long j2 = cursor.getLong(2);
            if (string == null) {
                string = "";
            }
            sb.append("ID:").append(j).append(" | 标题:").append(string).append(" | 修改时间:").append(getSafeDateFormat().format(new Date(j2))).append(StringUtils.LF);
            i2++;
        }
        return i2;
    }
}
