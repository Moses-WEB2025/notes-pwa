package com.miui.notes.action.provider;

import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionElementNotFoundException;
import com.google.android.appfunctions.schema.notes.v1.CreateNote;
import com.google.android.appfunctions.schema.notes.v1.CreateNoteParams;
import com.google.android.appfunctions.schema.notes.v1.FindNotes;
import com.google.android.appfunctions.schema.notes.v1.FindNotesParams;
import com.google.android.appfunctions.schema.notes.v1.GetNotes;
import com.google.android.appfunctions.schema.notes.v1.Note;
import com.google.android.appfunctions.schema.notes.v1.ShowNote;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.DbQueryHelper;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.PhoneHandWriteActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.util.EditorUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* JADX INFO: compiled from: NoteAppFunction.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \u001c2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0001\u001cB\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0097@¢\u0006\u0002\u0010\rJ$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0097@¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0097@¢\u0006\u0002\u0010\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J*\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u000f2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fH\u0097@¢\u0006\u0002\u0010\u001b¨\u0006\u001d"}, d2 = {"Lcom/miui/notes/action/provider/NoteAppFunction;", "Lcom/google/android/appfunctions/schema/notes/v1/FindNotes;", "Lcom/google/android/appfunctions/schema/notes/v1/CreateNote;", "Lcom/google/android/appfunctions/schema/notes/v1/ShowNote;", "Lcom/google/android/appfunctions/schema/notes/v1/GetNotes;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createNote", "Lcom/google/android/appfunctions/schema/notes/v1/Note;", "appFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "createNoteParams", "Lcom/google/android/appfunctions/schema/notes/v1/CreateNoteParams;", "(Landroidx/appfunctions/AppFunctionContext;Lcom/google/android/appfunctions/schema/notes/v1/CreateNoteParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findNotes", "", "findNotesParams", "Lcom/google/android/appfunctions/schema/notes/v1/FindNotesParams;", "(Landroidx/appfunctions/AppFunctionContext;Lcom/google/android/appfunctions/schema/notes/v1/FindNotesParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "showNote", "Landroid/app/PendingIntent;", NotifyUtils.KEY_NOTE_ID, "", "(Landroidx/appfunctions/AppFunctionContext;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNoteById", "getNotes", "noteIds", "(Landroidx/appfunctions/AppFunctionContext;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteAppFunction implements FindNotes, CreateNote, ShowNote, GetNotes {
    public static final int NOTE_COLUMN_INDEX_ID = 0;
    public static final int NOTE_COLUMN_INDEX_PARENT_ID = 1;
    public static final int NOTE_COLUMN_INDEX_SNIPPET = 3;
    public static final int NOTE_COLUMN_INDEX_TITLE = 2;
    public static final String QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_NO_PRIVATE_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4";
    private static final String TAG = "NoteAppFunctions";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] NOTE_PROJECTION = {"_id", "parent_id", "title", "snippet"};

    @Override // com.google.android.appfunctions.schema.notes.v1.CreateNote
    public Object createNote(AppFunctionContext appFunctionContext, CreateNoteParams createNoteParams, Continuation<? super Note> continuation) throws AppFunctionAppUnknownException, IOException {
        Log.d(TAG, "createNote");
        String content = createNoteParams.getContent();
        String title = createNoteParams.getTitle();
        Uri uri = Notes.Note.CONTENT_URI_ATOMIC;
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("snippet", content);
        contentValues.put("title", title);
        contentValues.put(Notes.Note.EXTERNAL_ID, createNoteParams.getExternalId());
        contentValues.put("created_date", Boxing.boxLong(jCurrentTimeMillis));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("content", content);
        contentValues2.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        ArrayList arrayList = new ArrayList();
        arrayList.add(contentValues2);
        Notes.Utils.addDataValuesToNoteValues(contentValues, arrayList);
        try {
            Uri uriInsert = appFunctionContext.getContext().getContentResolver().insert(uri, contentValues);
            if (uriInsert != null) {
                long id = ContentUris.parseId(uriInsert);
                Log.d(TAG, "createNote success");
                Log.d(TAG, "createNote: noteId = " + id + "; externalId = " + createNoteParams.getExternalId());
                return new Note(String.valueOf(id), createNoteParams.getTitle(), createNoteParams.getContent(), createNoteParams.getAttachments(), "0");
            }
            throw new AppFunctionAppUnknownException("Create note failed");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override // com.google.android.appfunctions.schema.notes.v1.FindNotes
    public Object findNotes(AppFunctionContext appFunctionContext, FindNotesParams findNotesParams, Continuation<? super List<Note>> continuation) {
        Log.d(TAG, "findNotes");
        Uri uri = Notes.Note.CONTENT_URI;
        String query = findNotesParams.getQuery();
        int maxCount = findNotesParams.getMaxCount();
        String str = query;
        String str2 = QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_NO_PRIVATE_SELECTION;
        if (str != null && str.length() != 0) {
            str2 = QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_NO_PRIVATE_SELECTION + ((Object) DbQueryHelper.wrapAndSelection(INSTANCE.getSearchContentSelection(query, TextProcessUtils.hasPhoneNumbers(query))));
        }
        Cursor cursorQuery = appFunctionContext.getContext().getContentResolver().query(uri, NOTE_PROJECTION, str2 + " LIMIT " + maxCount, null, null);
        ArrayList arrayList = new ArrayList();
        Cursor cursor = cursorQuery;
        try {
            Cursor cursor2 = cursor;
            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    long j = cursor2.getLong(0);
                    long j2 = cursor2.getLong(1);
                    String string = cursor2.getString(2);
                    String string2 = cursor2.getString(3);
                    String strValueOf = String.valueOf(j);
                    Intrinsics.checkNotNull(string);
                    arrayList.add(new Note(strValueOf, string, EditorUtils.normalizeSnippet(appFunctionContext.getContext(), string2).toString(), new ArrayList(), String.valueOf(j2)));
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            Log.d(TAG, "findNotes: noteNum = " + arrayList.size());
            return arrayList;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(cursor, th);
                throw th2;
            }
        }
    }

    @Override // com.google.android.appfunctions.schema.notes.v1.ShowNote
    public Object showNote(AppFunctionContext appFunctionContext, String str, Continuation<? super PendingIntent> continuation) throws AppFunctionAppUnknownException, AppFunctionElementNotFoundException {
        NoteEntity noteEntityLoadByExternalId;
        Intent intent;
        Log.d(TAG, "showNote: noteId = " + str);
        if (new Regex("\\d+").matches(str)) {
            Log.d(TAG, "showNote: search by id");
            noteEntityLoadByExternalId = NoteEntityHelper.loadById(appFunctionContext.getContext(), Long.parseLong(str));
        } else {
            Log.d(TAG, "showNote: search by externalId");
            noteEntityLoadByExternalId = NoteEntityHelper.loadByExternalId(appFunctionContext.getContext(), str);
        }
        if (noteEntityLoadByExternalId == null) {
            Log.d(TAG, "showNote: entity is null");
            throw new AppFunctionElementNotFoundException("note not found");
        }
        Log.d(TAG, "showNote entityId: " + noteEntityLoadByExternalId.getId() + "; showNote: " + noteEntityLoadByExternalId.getNoteType());
        if (RomUtils.isPadMode()) {
            intent = new Intent(appFunctionContext.getContext(), (Class<?>) NotesListActivity.class);
        } else {
            String noteType = noteEntityLoadByExternalId.getNoteType();
            if (noteType != null) {
                int iHashCode = noteType.hashCode();
                if (iHashCode != -1354814997) {
                    if (iHashCode != 3351634) {
                        if (iHashCode == 74679504 && noteType.equals("handwrite")) {
                            intent = new Intent(appFunctionContext.getContext(), (Class<?>) PhoneHandWriteActivity.class);
                        }
                    } else if (noteType.equals(NoteModel.NoteType.TYPE_MIND)) {
                        intent = new Intent(appFunctionContext.getContext(), (Class<?>) WebViewBrainActivity.class);
                        Intrinsics.checkNotNull(intent.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 1));
                    }
                } else if (noteType.equals("common")) {
                    intent = new Intent(appFunctionContext.getContext(), (Class<?>) EditActivity.class);
                    intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteEntityLoadByExternalId.getThemeId());
                    intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
                    Intrinsics.checkNotNull(intent.putExtra("com.miui.notes.folder_id", noteEntityLoadByExternalId.getParentId()));
                }
            }
            Log.d(TAG, "showNote: note type unknown");
            throw new AppFunctionAppUnknownException("note type unknown");
        }
        intent.setAction("android.intent.action.VIEW");
        intent.putExtra("android.intent.extra.UID", noteEntityLoadByExternalId.getId());
        Log.d(TAG, "showNote: return PendingIntent");
        PendingIntent activity = PendingIntent.getActivity(appFunctionContext.getContext(), 0, intent, 201326592);
        Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
        return activity;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x00da  */
    private final Note getNoteById(AppFunctionContext appFunctionContext, String noteId) {
        String str;
        Note note;
        Log.d(TAG, "getNoteById noteId = " + noteId);
        Uri uri = Notes.Note.CONTENT_URI;
        if (new Regex("\\d+").matches(noteId)) {
            Log.d(TAG, "getNoteById: search by id");
            str = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4 AND _id=" + noteId;
        } else {
            Log.d(TAG, "getNoteById: search by externalId");
            str = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4 AND external_id = '" + noteId + "'";
        }
        Cursor cursorQuery = appFunctionContext.getContext().getContentResolver().query(uri, NOTE_PROJECTION, str, null, null);
        if (cursorQuery == null) {
            return null;
        }
        Cursor cursor = cursorQuery;
        try {
            Cursor cursor2 = cursor;
            Log.d(TAG, "getNoteById: cursor count = " + cursor2.getCount());
            if (!cursor2.moveToFirst() || cursor2.getCount() <= 0) {
                note = null;
            } else {
                int columnIndex = cursor2.getColumnIndex("_id");
                int columnIndex2 = cursor2.getColumnIndex("parent_id");
                int columnIndex3 = cursor2.getColumnIndex("title");
                int columnIndex4 = cursor2.getColumnIndex("snippet");
                if (columnIndex == -1 || columnIndex2 == -1 || columnIndex3 == -1 || columnIndex4 == -1) {
                    note = null;
                } else {
                    long j = cursor2.getLong(columnIndex);
                    long j2 = cursor2.getLong(columnIndex2);
                    String string = cursor2.getString(columnIndex3);
                    String string2 = cursor2.getString(columnIndex4);
                    String strValueOf = String.valueOf(j);
                    Intrinsics.checkNotNull(string);
                    note = new Note(strValueOf, string, string2, null, String.valueOf(j2), 8, null);
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return note;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(cursor, th);
                throw th2;
            }
        }
    }

    @Override // com.google.android.appfunctions.schema.notes.v1.GetNotes
    public Object getNotes(AppFunctionContext appFunctionContext, List<String> list, Continuation<? super List<Note>> continuation) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Note noteById = getNoteById(appFunctionContext, (String) it.next());
            if (noteById != null) {
                arrayList.add(noteById);
            }
        }
        Log.d(TAG, "getNotes: num = " + arrayList.size());
        return arrayList;
    }

    /* JADX INFO: compiled from: NoteAppFunction.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0018\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/action/provider/NoteAppFunction$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "NOTE_PROJECTION", "", "[Ljava/lang/String;", "NOTE_COLUMN_INDEX_ID", "", "NOTE_COLUMN_INDEX_PARENT_ID", "NOTE_COLUMN_INDEX_TITLE", "NOTE_COLUMN_INDEX_SNIPPET", "QUERY_ALL_NOTES_NO_DELETED_NO_TRASH_NO_PRIVATE_SELECTION", "getSearchContentSelection", "searchWords", "isPhoneNumber", "", "generateCallNoteSearchSelection", "generateNoteContentSearchSelection", "generateNoteContentSelectionByContentType", "contentType", "formattedWords", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getSearchContentSelection(String searchWords, boolean isPhoneNumber) {
            StringBuilder sb = new StringBuilder();
            String strGenerateNoteContentSearchSelection = generateNoteContentSearchSelection(searchWords);
            if (isPhoneNumber) {
                sb.append((CharSequence) DbQueryHelper.wrapBracketSelection(strGenerateNoteContentSearchSelection)).append((CharSequence) DbQueryHelper.wrapOrSelection(generateCallNoteSearchSelection(searchWords)));
            } else {
                sb.append(strGenerateNoteContentSearchSelection);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        private final String generateCallNoteSearchSelection(String searchWords) {
            List listEmptyList;
            StringBuilder sb = new StringBuilder();
            String str = DbQueryHelper.formattedKeyWords(searchWords);
            Intrinsics.checkNotNull(str);
            String str2 = str;
            int length = str2.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str2.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            List<String> listSplit = new Regex(TextProcessUtils.ONE_OR_MULTI_SPACE_REG).split(str2.subSequence(i, length + 1).toString(), 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (true) {
                    if (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() != 0) {
                            listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
            int length2 = strArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (i2 > 0) {
                    sb.append(" OR ");
                }
                sb.append(" LIKE ('%").append(strArr[i2]).append("%',data3,'/')");
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        private final String generateNoteContentSearchSelection(String searchWords) {
            StringBuilder sb = new StringBuilder();
            String str = DbQueryHelper.formattedKeyWords(searchWords);
            Intrinsics.checkNotNull(str);
            sb.append(generateNoteContentSelectionByContentType("plain_text", str));
            sb.append(" OR ");
            sb.append(generateNoteContentSelectionByContentType(Notes.Note.MIND_CONTENT_PLAIN_TEXT, str));
            sb.append(" OR ");
            sb.append(generateNoteContentSelectionByContentType("title", str));
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        private final String generateNoteContentSelectionByContentType(String contentType, String formattedWords) {
            StringBuilder sb = new StringBuilder("LIKE('%");
            sb.append(formattedWords).append("%',").append(contentType).append(",'/')");
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }
}
