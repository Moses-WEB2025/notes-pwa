package com.miui.notes.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.widget.WidgetProcessDatabaseUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NoteEntityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0005H\u0007J\u001a\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0007J1\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014H\u0007¢\u0006\u0002\u0010\u0015J1\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014H\u0002¢\u0006\u0002\u0010\u0018J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J&\u0010\u001b\u001a\u00020\u001c*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00052\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001c0 J/\u0010!\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00052\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014H\u0007¢\u0006\u0002\u0010\u0015J/\u0010\"\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00052\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014H\u0002¢\u0006\u0002\u0010\u0018J\u001a\u0010#\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/miui/notes/model/NoteEntityHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "isContentEmpty", "", "content", "loadById", "Lcom/miui/notes/model/NoteEntity;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", NotifyUtils.KEY_NOTE_ID, "", "loadByExternalId", "externalId", "load", "selection", "selectionArgs", "", "(Landroid/content/Context;Ljava/lang/String;[Ljava/lang/String;)Lcom/miui/notes/model/NoteEntity;", "loadNoteInfo", "Lcom/miui/notes/model/NoteInfo;", "(Landroid/content/Context;Ljava/lang/String;[Ljava/lang/String;)Lcom/miui/notes/model/NoteInfo;", "loadDataInfo", "Lcom/miui/notes/model/DataInfo;", "useWithClose", "", "Landroid/database/Cursor;", "from", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "loadByDB", "loadNoteInfoByDB", "loadDataInfoByDB", "getModifyDate", "id", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteEntityHelper {
    public static final NoteEntityHelper INSTANCE = new NoteEntityHelper();
    public static final String TAG = "NoteEntityHelper";

    private NoteEntityHelper() {
    }

    @JvmStatic
    public static final boolean isContentEmpty(String content) {
        String strReplace$default = content != null ? StringsKt.replace$default(content, "<new-format/>", "", false, 4, (Object) null) : null;
        return strReplace$default == null || Intrinsics.areEqual("<text indent=\"1\"></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><right></right></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><center></center></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><size></size></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><mid-size></mid-size></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><h3-size></h3-size></text>", strReplace$default) || Intrinsics.areEqual("<text indent=\"1\"><s1-size></s1-size></text>", strReplace$default);
    }

    @JvmStatic
    public static final NoteEntity loadById(Context context, long noteId) {
        Intrinsics.checkNotNullParameter(context, "context");
        return load(context, "_id=" + noteId, null);
    }

    @JvmStatic
    public static final NoteEntity loadByExternalId(Context context, String externalId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(externalId, "externalId");
        return load(context, "external_id = '" + externalId + "'", null);
    }

    @JvmStatic
    public static final NoteEntity load(Context context, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(context, "context");
        NoteEntityHelper noteEntityHelper = INSTANCE;
        NoteInfo noteInfoLoadNoteInfo = noteEntityHelper.loadNoteInfo(context, selection, selectionArgs);
        if (noteInfoLoadNoteInfo == null) {
            return null;
        }
        DataInfo dataInfoLoadDataInfo = noteEntityHelper.loadDataInfo(context, noteInfoLoadNoteInfo.getId());
        Logger.INSTANCE.i(TAG, "dataInfo load with result " + dataInfoLoadDataInfo);
        if (dataInfoLoadDataInfo == null) {
            return null;
        }
        noteInfoLoadNoteInfo.setContent(dataInfoLoadDataInfo.getContent());
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setOriginalInfo(noteInfoLoadNoteInfo);
        noteEntity.setUpdatedInfo(new NoteInfo(noteInfoLoadNoteInfo));
        noteEntity.setCurrentInfo(new NoteInfo(noteInfoLoadNoteInfo));
        noteEntity.setDataInfo(dataInfoLoadDataInfo);
        return noteEntity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final NoteInfo loadNoteInfo(Context context, String selection, String[] selectionArgs) {
        Logger.INSTANCE.d(TAG, "loadNoteInfo");
        Uri uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        String str = "type=0 AND " + selection;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Cursor cursorQuery = context.getContentResolver().query(uriAppendFlag, NoteEntityDBConstKt.getNOTE_PROJECTION(), str, selectionArgs, null);
        if (cursorQuery != null) {
            useWithClose(cursorQuery, String.valueOf(uriAppendFlag), new Function1() { // from class: com.miui.notes.model.NoteEntityHelper$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NoteEntityHelper.loadNoteInfo$lambda$0(objectRef, (Cursor) obj);
                }
            });
        }
        Logger.INSTANCE.d(TAG, "loadNoteInfo ");
        return (NoteInfo) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.miui.notes.model.NoteInfo] */
    public static final Unit loadNoteInfo$lambda$0(Ref.ObjectRef objectRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.moveToNext()) {
            objectRef.element = NoteEntityHelperKt.load2NoteInfo(it);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final DataInfo loadDataInfo(Context context, long noteId) {
        if (noteId == -2) {
            Logger.INSTANCE.i(TAG, "loadDataInfo with invalid noteId ");
            return null;
        }
        Uri uriAppendFlag = NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        final Cursor cursorQuery = context.getContentResolver().query(uriAppendFlag, NoteEntityDBConstKt.getDATA_PROJECTION(), "note_id=" + noteId, null, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (cursorQuery != null) {
            useWithClose(cursorQuery, " loadDataInfo for uri " + uriAppendFlag, new Function1() { // from class: com.miui.notes.model.NoteEntityHelper$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NoteEntityHelper.loadDataInfo$lambda$1(cursorQuery, objectRef, (Cursor) obj);
                }
            });
        }
        return (DataInfo) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, com.miui.notes.model.DataInfo] */
    public static final Unit loadDataInfo$lambda$1(Cursor cursor, Ref.ObjectRef objectRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.getCount() > 0) {
            objectRef.element = NoteEntityHelperKt.valueOf(cursor);
        }
        return Unit.INSTANCE;
    }

    public final void useWithClose(Cursor cursor, String from, Function1<? super Cursor, Unit> callback) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            try {
                Logger.INSTANCE.d(TAG, "useWithClose");
                callback.invoke(cursor);
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "e " + ExceptionsKt.stackTraceToString(e));
            }
        } finally {
            cursor.close();
            Logger.INSTANCE.d(TAG, "cursor closed");
        }
    }

    @JvmStatic
    public static final NoteEntity loadByDB(Context context, String selection, String[] selectionArgs) {
        DataInfo dataInfoLoadDataInfoByDB;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(selection, "selection");
        if (WidgetProcessDatabaseUtils.appIsRunning(context)) {
            try {
                return load(context, selection, selectionArgs);
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "load widget:" + e.getMessage());
            }
        }
        NoteEntityHelper noteEntityHelper = INSTANCE;
        NoteInfo noteInfoLoadNoteInfoByDB = noteEntityHelper.loadNoteInfoByDB(context, selection, selectionArgs);
        if (noteInfoLoadNoteInfoByDB == null || (dataInfoLoadDataInfoByDB = noteEntityHelper.loadDataInfoByDB(context, noteInfoLoadNoteInfoByDB.getId())) == null) {
            return null;
        }
        noteInfoLoadNoteInfoByDB.setContent(dataInfoLoadDataInfoByDB.getContent());
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setOriginalInfo(noteInfoLoadNoteInfoByDB);
        noteEntity.setUpdatedInfo(new NoteInfo(noteInfoLoadNoteInfoByDB));
        noteEntity.setCurrentInfo(new NoteInfo(noteInfoLoadNoteInfoByDB));
        noteEntity.setDataInfo(dataInfoLoadDataInfoByDB);
        return noteEntity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final NoteInfo loadNoteInfoByDB(Context context, String selection, String[] selectionArgs) {
        final Cursor cursorQueryNote = WidgetProcessDatabaseUtils.queryNote(context, NoteEntityDBConstKt.getNOTE_PROJECTION(), "type=0 AND " + selection, selectionArgs, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (cursorQueryNote != null) {
            useWithClose(cursorQueryNote, "loadNoteInfoByDB", new Function1() { // from class: com.miui.notes.model.NoteEntityHelper$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NoteEntityHelper.loadNoteInfoByDB$lambda$2(cursorQueryNote, objectRef, (Cursor) obj);
                }
            });
        }
        return (NoteInfo) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, com.miui.notes.model.NoteInfo] */
    public static final Unit loadNoteInfoByDB$lambda$2(Cursor cursor, Ref.ObjectRef objectRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.moveToNext()) {
            objectRef.element = NoteEntityHelperKt.load2NoteInfo(cursor);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final DataInfo loadDataInfoByDB(Context context, long noteId) {
        if (noteId == -2) {
            return null;
        }
        NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
        final Cursor cursorQueryData = WidgetProcessDatabaseUtils.queryData(context, NoteEntityDBConstKt.getDATA_PROJECTION(), "note_id=" + noteId, null, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (cursorQueryData != null) {
            useWithClose(cursorQueryData, "loadDataInfoByDB", new Function1() { // from class: com.miui.notes.model.NoteEntityHelper$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NoteEntityHelper.loadDataInfoByDB$lambda$3(cursorQueryData, objectRef, (Cursor) obj);
                }
            });
        }
        return (DataInfo) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, com.miui.notes.model.DataInfo] */
    public static final Unit loadDataInfoByDB$lambda$3(Cursor cursor, Ref.ObjectRef objectRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.getCount() > 0) {
            objectRef.element = NoteEntityHelperKt.valueOf(cursor);
        }
        return Unit.INSTANCE;
    }

    public final long getModifyDate(long id) {
        if (id == -2) {
            return 0L;
        }
        Uri uriAppendFlag = NotesProvider.appendFlag(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, id), NotesProvider.CALLER_IS_PRIVACY);
        final Cursor cursorQuery = BaseNoteApp.INSTANCE.getInstance().getContentResolver().query(uriAppendFlag, new String[]{"modified_date"}, null, null, null);
        final Ref.LongRef longRef = new Ref.LongRef();
        if (cursorQuery != null) {
            useWithClose(cursorQuery, String.valueOf(uriAppendFlag), new Function1() { // from class: com.miui.notes.model.NoteEntityHelper$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NoteEntityHelper.getModifyDate$lambda$4(cursorQuery, longRef, (Cursor) obj);
                }
            });
        }
        return longRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getModifyDate$lambda$4(Cursor cursor, Ref.LongRef longRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.moveToNext()) {
            longRef.element = cursor.getLong(0);
        }
        return Unit.INSTANCE;
    }
}
