package com.miui.notes.backup;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.DragSort;
import com.miui.todo.data.utils.GuideUtils;
import defpackage.R2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: TodoManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 02\u00020\u0001:\u00010B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\nJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\u0015J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0003J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\nH\u0002J\u000e\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0#J\u001a\u0010$\u001a\u0004\u0018\u00010\u000b2\u0006\u0010!\u001a\u00020\n2\u0006\u0010%\u001a\u00020&H\u0002J \u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010.\u001a\u00020\n2\b\u0010/\u001a\u0004\u0018\u00010\nH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\b\u001a&\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\tj\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R)\u0010\r\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\u000f0\u000e8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u00061"}, d2 = {"Lcom/miui/notes/backup/TodoManager;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mResolver", "Landroid/content/ContentResolver;", "mAttach2Uri", "Ljava/util/HashMap;", "", "Landroid/net/Uri;", "Lkotlin/collections/HashMap;", "attachesIterator", "", "", "getAttachesIterator", "()Ljava/util/Iterator;", "getAttachesUriByName", "name", "restoreInfo", "", Todo.Table.TODO_NAME, "Lcom/miui/notes/backup/TodoProtos$TodoEntity;", "loadTodoEntity", "id", "insertTodoEntity", "deleteAllData", "", "backupAttachmentIfNeed", "cursor", "Landroid/database/Cursor;", "restoreAttachmentIfNeed", "fileName", "prepareIds", "", "audioFile2Uri", "checkExist", "", "setTodoEntityField", "todoEntity", "Lcom/miui/notes/backup/TodoProtos$TodoEntity$Builder;", "index", "", "prepareTodoContentValues", "Landroid/content/ContentValues;", "getNonEmptyString", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TodoManager {
    public static final long NEED_RESTORE = 0;
    private static final String TAG = "TodoManager";
    private final HashMap<String, Uri> mAttach2Uri;
    private final ContentResolver mResolver;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long NO_NEED_RESTORE = -1;

    public TodoManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        this.mResolver = contentResolver;
        this.mAttach2Uri = new HashMap<>();
    }

    public final Iterator<Map.Entry<String, Uri>> getAttachesIterator() {
        return this.mAttach2Uri.entrySet().iterator();
    }

    public final Uri getAttachesUriByName(String name) {
        return this.mAttach2Uri.get(name);
    }

    public final long restoreInfo(TodoProtos.TodoEntity todo) throws IOException {
        Intrinsics.checkNotNullParameter(todo, "todo");
        TodoBaseEntity todoBaseEntity = new TodoBaseEntity(todo.getContent(), todo.getRemindTime(), todo.getRemindType(), todo.getInputType(), todo.getAudioFileName(), todo.getPlainText(), todo.getRemindRepeatType(), todo.getColorLabel(), todo.getListType(), todo.getFirstRemindTime());
        if (PreferenceUtils.getAddTodoIntroduction(BaseNoteApp.INSTANCE.getInstance()) && GuideUtils.isGuideTodo(todoBaseEntity)) {
            return NO_NEED_RESTORE;
        }
        if (todo.getLocalStatus() == 3) {
            return NO_NEED_RESTORE;
        }
        long j = NO_NEED_RESTORE;
        ContentResolver contentResolver = this.mResolver;
        Uri uri = Uri.parse(Todo.URI_TODO_OUTER);
        String content = todo.getContent();
        Intrinsics.checkNotNullExpressionValue(content, "getContent(...)");
        Cursor cursorQuery = contentResolver.query(uri, null, "content= ? and create_time= ?", new String[]{content, new StringBuilder().append(todo.getCreateTime()).toString()}, null);
        if (cursorQuery == null || cursorQuery.getCount() == 0) {
            j = 0;
        }
        if (cursorQuery != null) {
            if (cursorQuery.getCount() != 0 && cursorQuery.moveToFirst() && cursorQuery.getInt(50) == 3 && todo.getLocalStatus() != 3) {
                j = cursorQuery.getInt(0);
                TodoProviderAccessUtils.deleteItem(j);
            }
            cursorQuery.close();
        }
        return j;
    }

    public final TodoProtos.TodoEntity loadTodoEntity(long id) throws Throwable {
        Throwable th;
        Exception exc;
        TodoProtos.TodoEntity.Builder builderNewBuilder = TodoProtos.TodoEntity.newBuilder();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = this.mResolver.query(Uri.parse(Todo.URI_TODO_OUTER), null, "id= ? ", new String[]{String.valueOf(id)}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            int columnCount = cursorQuery.getColumnCount();
                            for (int i = 0; i < columnCount; i++) {
                                Intrinsics.checkNotNull(builderNewBuilder);
                                setTodoEntityField(builderNewBuilder, cursorQuery, i);
                            }
                            backupAttachmentIfNeed(cursorQuery);
                            cursorQuery.close();
                        }
                    } catch (Exception e) {
                        exc = e;
                        cursor = cursorQuery;
                        Logger.INSTANCE.e(TAG, "Fail to load todo entity, id=" + id + ",exception:" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                    return builderNewBuilder.build();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
        }
    }

    public final long insertTodoEntity(TodoProtos.TodoEntity todo) {
        long j;
        Intrinsics.checkNotNullParameter(todo, "todo");
        synchronized (Reflection.getOrCreateKotlinClass(TodoManager.class)) {
            ContentValues contentValuesPrepareTodoContentValues = prepareTodoContentValues(todo);
            contentValuesPrepareTodoContentValues.put(Todo.CUSTOM_SORT_ID, Long.valueOf(DragSort.getBigSortId()));
            contentValuesPrepareTodoContentValues.put("sync_id", (Long) 0L);
            contentValuesPrepareTodoContentValues.put(Todo.SYNC_ETAG, (Long) 0L);
            contentValuesPrepareTodoContentValues.put("local_status", (Integer) 0);
            Uri uriInsert = this.mResolver.insert(Uri.parse(TodoProvider.URI_TODO_PRIVATE), contentValuesPrepareTodoContentValues);
            Intrinsics.checkNotNull(uriInsert);
            String str = uriInsert.getPathSegments().get(1);
            Intrinsics.checkNotNullExpressionValue(str, "get(...)");
            j = Long.parseLong(str);
            if (todo.getInputType() == 1) {
                String audioFileName = todo.getAudioFileName();
                Intrinsics.checkNotNullExpressionValue(audioFileName, "getAudioFileName(...)");
                restoreAttachmentIfNeed(audioFileName);
            }
        }
        return j;
    }

    public final void deleteAllData() {
        synchronized (Reflection.getOrCreateKotlinClass(TodoManager.class)) {
            this.mResolver.delete(Uri.parse(TodoProvider.URI_TODO_PRIVATE), null, null);
        }
    }

    private final void backupAttachmentIfNeed(Cursor cursor) {
        if (cursor.getInt(cursor.getColumnIndex(Todo.INPUT_TYPE)) != 1) {
            return;
        }
        String string = cursor.getString(cursor.getColumnIndex(Todo.AUDIO_FILE_NAME));
        Intrinsics.checkNotNull(string);
        Uri uriAudioFile2Uri = audioFile2Uri(string, true);
        if (uriAudioFile2Uri != null) {
            this.mAttach2Uri.put(string, uriAudioFile2Uri);
            Logger.INSTANCE.d(TAG, "backup attachment " + string + " with uri:");
        }
    }

    private final void restoreAttachmentIfNeed(String fileName) {
        Uri uriAudioFile2Uri = audioFile2Uri(fileName, false);
        if (uriAudioFile2Uri != null) {
            this.mAttach2Uri.put(fileName, uriAudioFile2Uri);
        }
    }

    public final List<String> prepareIds() {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.mResolver.query(Uri.parse(Todo.URI_TODO_OUTER), new String[]{"id"}, "local_status != ?", new String[]{"3"}, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
                    while (!cursorQuery.isAfterLast()) {
                        arrayList.add(String.valueOf(cursorQuery.getLong(columnIndexOrThrow)));
                        cursorQuery.moveToNext();
                    }
                    cursorQuery.close();
                    return arrayList;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                    return arrayList;
                }
            } catch (Exception e) {
                com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "Fail to prepare ids,exception:" + e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorQuery == null) {
                throw th;
            }
            cursorQuery.close();
            throw th;
        }
    }

    private final Uri audioFile2Uri(String fileName, boolean checkExist) {
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.TODO_MP3_DIR_NAME), fileName);
        if (checkExist) {
            if (file.exists()) {
                return Uri.fromFile(file);
            }
            return null;
        }
        return Uri.fromFile(file);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void setTodoEntityField(TodoProtos.TodoEntity.Builder todoEntity, Cursor cursor, int index) {
        String columnName = cursor.getColumnName(index);
        if (columnName != null) {
            switch (columnName.hashCode()) {
                case -2122412475:
                    if (columnName.equals(Todo.AUDIO_FILE_NAME)) {
                        todoEntity.setAudioFileName(getNonEmptyString(cursor.getString(index)));
                        return;
                    }
                    break;
                case -2122255429:
                    if (columnName.equals(Todo.AUDIO_FILE_SIZE)) {
                        todoEntity.setAudioFileSize(cursor.getLong(index));
                        return;
                    }
                    break;
                case -2061635299:
                    if (columnName.equals("snippet")) {
                        todoEntity.setSnippet(getNonEmptyString(cursor.getString(index)));
                        return;
                    }
                    break;
                case -2024591524:
                    if (columnName.equals(Todo.SORT_ID)) {
                        todoEntity.setSortId(cursor.getLong(index));
                        return;
                    }
                    break;
                case -2019038985:
                    if (columnName.equals(Todo.AUDIO_FILE_FIELD)) {
                        todoEntity.setAudioFileField(getNonEmptyString(cursor.getString(index)));
                        return;
                    }
                    break;
                case -2012611449:
                    if (columnName.equals(Todo.MARK_FINISH_TIME)) {
                        todoEntity.setMarkFinishTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case -1742500545:
                    if (columnName.equals("sync_id")) {
                        todoEntity.setSyncId(cursor.getInt(index));
                        return;
                    }
                    break;
                case -1280520378:
                    if (columnName.equals("local_status")) {
                        todoEntity.setLocalStatus(cursor.getInt(index));
                        return;
                    }
                    break;
                case -1263189212:
                    if (columnName.equals(Todo.REMIND_REPEAT_TYPE)) {
                        todoEntity.setRemindRepeatType(cursor.getInt(index));
                        return;
                    }
                    break;
                case -1224062405:
                    if (columnName.equals(Todo.LIST_TYPE)) {
                        todoEntity.setListType(cursor.getInt(index));
                        return;
                    }
                    break;
                case -1165461084:
                    if (columnName.equals("priority")) {
                        todoEntity.setPriority(cursor.getInt(index));
                        return;
                    }
                    break;
                case -896732440:
                    if (columnName.equals(Todo.SON_ID)) {
                        todoEntity.setSonId(cursor.getLong(index));
                        return;
                    }
                    break;
                case -896505829:
                    if (columnName.equals("source")) {
                        todoEntity.setSource(cursor.getInt(index));
                        return;
                    }
                    break;
                case -810403512:
                    if (columnName.equals(Todo.IS_FINISH)) {
                        todoEntity.setIsFinish(cursor.getInt(index));
                        return;
                    }
                    break;
                case -803333011:
                    if (columnName.equals("account_id")) {
                        todoEntity.setAccountId(cursor.getLong(index));
                        return;
                    }
                    break;
                case -699202105:
                    if (columnName.equals(Todo.REMIND_TIME)) {
                        todoEntity.setRemindTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case -699186636:
                    if (columnName.equals(Todo.REMIND_TYPE)) {
                        todoEntity.setRemindType(cursor.getInt(index));
                        return;
                    }
                    break;
                case -671513446:
                    if (columnName.equals(Todo.LAST_MODIFIED_TIME)) {
                        todoEntity.setLastModifiedTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case -493574096:
                    if (columnName.equals(Todo.CREATE_TIME)) {
                        todoEntity.setCreateTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case -411586888:
                    if (columnName.equals(Todo.FIRST_REMIND_TIME)) {
                        todoEntity.setFirstRemindTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case -161102496:
                    if (columnName.equals(Todo.REMIND_TAG)) {
                        todoEntity.setRemindTag(cursor.getInt(index));
                        return;
                    }
                    break;
                case -96179731:
                    if (columnName.equals(Todo.EXPIRE_TIME)) {
                        todoEntity.setExpiredTime(cursor.getLong(index));
                        return;
                    }
                    break;
                case R2.color.m3_calendar_item_stroke_color /* 3355 */:
                    if (columnName.equals("id")) {
                        todoEntity.setId(cursor.getLong(index));
                        return;
                    }
                    break;
                case 3575610:
                    if (columnName.equals("type")) {
                        todoEntity.setType(cursor.getInt(index));
                        return;
                    }
                    break;
                case 50511102:
                    if (columnName.equals("category")) {
                        todoEntity.setCategory(cursor.getInt(index));
                        return;
                    }
                    break;
                case 102727412:
                    if (columnName.equals(Todo.LABEL)) {
                        todoEntity.setLabel(getNonEmptyString(cursor.getString(index)));
                        return;
                    }
                    break;
                case 351608024:
                    if (columnName.equals("version")) {
                        todoEntity.setVersion(cursor.getInt(index));
                        return;
                    }
                    break;
                case 494121017:
                    if (columnName.equals(Todo.SYNC_ETAG)) {
                        todoEntity.setSyncEtag(cursor.getLong(index));
                        return;
                    }
                    break;
                case 527488652:
                    if (columnName.equals("folder_id")) {
                        todoEntity.setFolderId(cursor.getInt(index));
                        return;
                    }
                    break;
                case 667315193:
                    if (columnName.equals(Todo.WORDS_COUNT)) {
                        todoEntity.setWordsCount(cursor.getInt(index));
                        return;
                    }
                    break;
                case 853697559:
                    if (columnName.equals(Todo.HIDE_TYPE)) {
                        todoEntity.setHideType(cursor.getInt(index));
                        return;
                    }
                    break;
                case 951530617:
                    if (columnName.equals("content")) {
                        todoEntity.setContent(cursor.getString(index));
                        return;
                    }
                    break;
                case 1021751938:
                    if (columnName.equals("plain_text")) {
                        todoEntity.setPlainText(getNonEmptyString(cursor.getString(index)));
                        return;
                    }
                    break;
                case 1126661230:
                    if (columnName.equals(Todo.SERVER_STATUS)) {
                        todoEntity.setServerStatus(cursor.getInt(index));
                        return;
                    }
                    break;
                case 1317845784:
                    if (columnName.equals(Todo.COLOR_LABEL)) {
                        todoEntity.setColorLabel(cursor.getInt(index));
                        return;
                    }
                    break;
                case 1386692239:
                    if (columnName.equals(Todo.INPUT_TYPE)) {
                        todoEntity.setInputType(cursor.getInt(index));
                        return;
                    }
                    break;
                case 1744003214:
                    if (columnName.equals(Todo.CUSTOM_SORT_ID)) {
                        todoEntity.setCustomSortId(cursor.getLong(index));
                        return;
                    }
                    break;
                case 1817068534:
                    if (columnName.equals("sync_tag")) {
                        todoEntity.setSyncEtag(cursor.getLong(index));
                        return;
                    }
                    break;
                case 2070327504:
                    if (columnName.equals("parent_id")) {
                        todoEntity.setParenId(cursor.getLong(index));
                        return;
                    }
                    break;
            }
        }
        com.miui.notes.base.utils.Logger.INSTANCE.w(TAG, "setTodoEntityField in invalid columnName " + columnName);
        Unit unit = Unit.INSTANCE;
    }

    private final ContentValues prepareTodoContentValues(TodoProtos.TodoEntity todo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", todo.getContent());
        contentValues.put(Todo.REMIND_TYPE, Integer.valueOf(todo.getRemindType()));
        contentValues.put(Todo.EXPIRE_TIME, Long.valueOf(todo.getExpiredTime()));
        contentValues.put(Todo.REMIND_TIME, Long.valueOf(todo.getRemindTime()));
        contentValues.put(Todo.IS_FINISH, Integer.valueOf(todo.getIsFinish()));
        contentValues.put(Todo.MARK_FINISH_TIME, Long.valueOf(todo.getMarkFinishTime()));
        contentValues.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(todo.getLastModifiedTime()));
        contentValues.put(Todo.CREATE_TIME, Long.valueOf(todo.getCreateTime()));
        contentValues.put(Todo.INPUT_TYPE, Integer.valueOf(todo.getInputType()));
        contentValues.put(Todo.AUDIO_FILE_NAME, todo.getAudioFileName());
        contentValues.put(Todo.CUSTOM_SORT_ID, Long.valueOf(todo.getCustomSortId()));
        contentValues.put("plain_text", todo.getPlainText());
        contentValues.put(Todo.HIDE_TYPE, Integer.valueOf(todo.getHideType()));
        contentValues.put("priority", Integer.valueOf(todo.getPriority()));
        contentValues.put(Todo.REMIND_REPEAT_TYPE, Integer.valueOf(todo.getRemindRepeatType()));
        contentValues.put("category", Integer.valueOf(todo.getCategory()));
        contentValues.put(Todo.LABEL, todo.getLabel());
        contentValues.put("sync_id", Integer.valueOf(todo.getSyncId()));
        contentValues.put("sync_tag", todo.getSyncTag());
        contentValues.put(Todo.REMIND_TAG, Integer.valueOf(todo.getRemindTag()));
        contentValues.put(Todo.WORDS_COUNT, Integer.valueOf(todo.getWordsCount()));
        contentValues.put("parent_id", Long.valueOf(todo.getParenId()));
        contentValues.put(Todo.SON_ID, Long.valueOf(todo.getSonId()));
        contentValues.put("type", Integer.valueOf(todo.getType()));
        contentValues.put(Todo.SORT_ID, Long.valueOf(todo.getSortId()));
        contentValues.put("account_id", Long.valueOf(todo.getAccountId()));
        contentValues.put("version", Integer.valueOf(todo.getVersion()));
        contentValues.put("snippet", todo.getSnippet());
        contentValues.put(Todo.SYNC_ETAG, Long.valueOf(todo.getSyncEtag()));
        contentValues.put(Todo.SERVER_STATUS, Integer.valueOf(todo.getServerStatus()));
        contentValues.put(Todo.AUDIO_FILE_FIELD, todo.getAudioFileField());
        contentValues.put(Todo.FIRST_REMIND_TIME, Long.valueOf(todo.getFirstRemindTime()));
        contentValues.put("folder_id", Integer.valueOf(todo.getFolderId()));
        contentValues.put("local_status", Integer.valueOf(todo.getLocalStatus()));
        contentValues.put("source", Integer.valueOf(todo.getSource()));
        contentValues.put(Todo.AUDIO_FILE_SIZE, Long.valueOf(todo.getAudioFileSize()));
        contentValues.put(Todo.COLOR_LABEL, Integer.valueOf(todo.getColorLabel()));
        contentValues.put(Todo.LIST_TYPE, Integer.valueOf(todo.getListType()));
        return contentValues;
    }

    private final String getNonEmptyString(String s) {
        return (s == null || TextUtils.isEmpty(s)) ? "" : s;
    }

    /* JADX INFO: compiled from: TodoManager.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/backup/TodoManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "NO_NEED_RESTORE", "", "getNO_NEED_RESTORE", "()J", "NEED_RESTORE", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long getNO_NEED_RESTORE() {
            return TodoManager.NO_NEED_RESTORE;
        }
    }
}
