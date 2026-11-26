package com.miui.notes.store;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.DataUtils;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.richeditor.schema.HtmlParser;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: FolderStore.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u0016\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0018\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007J\u000e\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J\u000e\u0010$\u001a\u00020%2\u0006\u0010!\u001a\u00020\"J<\u0010&\u001a\b\u0012\u0004\u0012\u00020\"0'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u00112\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010-2\u0006\u0010.\u001a\u00020\u0005J4\u0010&\u001a\b\u0012\u0004\u0012\u00020\"0'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u00112\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010-J\u0010\u0010&\u001a\u0004\u0018\u00010\"2\u0006\u0010*\u001a\u00020\u0011J>\u0010&\u001a\b\u0012\u0004\u0012\u00020\"0'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010\u00052\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010-J2\u00100\u001a\b\u0012\u0004\u0012\u00020\"0'2\u0006\u0010(\u001a\u00020)2\u0006\u00101\u001a\u00020%2\u0006\u0010+\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0-J\u0010\u00102\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u0011H\u0002J\u0018\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020%H\u0007J\u001e\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u000e\u00108\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%09J\u0018\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u00108\u001a\u00020:H\u0007J(\u00103\u001a\u00020\u00112\u0006\u00105\u001a\u0002062\u0006\u00108\u001a\u00020:2\u0006\u0010;\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\u0011H\u0002J\u0018\u0010=\u001a\u00020>2\u0006\u00105\u001a\u0002062\u0006\u0010?\u001a\u00020\u0005H\u0007J\u0018\u0010@\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u0010?\u001a\u00020\u0005H\u0007J\u0018\u0010A\u001a\u00020%2\u0006\u00105\u001a\u0002062\u0006\u0010?\u001a\u00020\u0005H\u0007J\u0012\u0010B\u001a\u0004\u0018\u00010\u00052\u0006\u0010C\u001a\u00020%H\u0007J\u0012\u0010D\u001a\u0004\u0018\u00010\u00052\u0006\u0010C\u001a\u00020%H\u0002J$\u0010E\u001a\u00020%2\u0006\u00105\u001a\u0002062\b\u0010?\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010F\u001a\u00020%H\u0007J\u0018\u0010G\u001a\u00020>2\u0006\u00105\u001a\u0002062\u0006\u0010?\u001a\u00020\u0005H\u0007J\"\u0010H\u001a\u00020>2\u0006\u00105\u001a\u0002062\u0006\u0010C\u001a\u00020%2\b\u0010?\u001a\u0004\u0018\u00010\u0005H\u0007J\"\u0010I\u001a\u00020>2\u0006\u00105\u001a\u0002062\u0006\u0010C\u001a\u00020%2\b\b\u0002\u0010F\u001a\u00020%H\u0007J\u0018\u0010J\u001a\u00020>2\u0006\u00105\u001a\u0002062\u0006\u0010!\u001a\u00020\"H\u0007J\u001a\u0010K\u001a\u00020L2\u0006\u00105\u001a\u0002062\b\u0010!\u001a\u0004\u0018\u00010\"H\u0007J\u0006\u0010M\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\u00058\u0002@\u0002X\u0083\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u001e\u0010\u0003¨\u0006N"}, d2 = {"Lcom/miui/notes/store/FolderStore;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "SELECTION_FOLDER_USER", "SELECTION_FOLDER_USER_INCLUDE_UNCATEGORIZED", "SELECTION_FOLDER_USER_AND_SYSTEM", "SELECTION_FOLDER_USER_AND_SYSTEM_WITH_MENU", "PAD_FOLDER_DIALOG_SELECTION_FOLDER_LIST", "SELECTION_FOLDER_HOMEPAGE", "SELECTION_FOLDER_PRIVATE", "QUERY_SORT_ORDER", "RANGE_SELECTION", "FOLDER_TYPE_SELECTION", "MAX_BATCH_SIZE", "", "QUERY_FOLDER_USER", "QUERY_FOLDER_USER_AND_SYSTEM", "QUERY_FOLDER_PRIVATE", "PAD_QUERY_FOLDER_DIALOG_FOLDER_LIST", "QUERY_FOLDER_USER_INCLUDE_UNCATEGORIZED", "QUERY_FOLDER_USER_AND_SYSTEM_WITH_MENU", "pDefaultFolderName", "getPDefaultFolderName", "()Ljava/lang/String;", "setPDefaultFolderName", "(Ljava/lang/String;)V", "sFolderNumStr", "getSFolderNumStr$annotations", CoreConstants.VALUE_OF, "Lcom/miui/notes/model/FolderModel;", "cursor", "Landroid/database/Cursor;", "parsePartFieldsWithMenu", "getId", "", SmartAction.Feature.QUERY, "Landroidx/loader/content/Loader;", "loaderManager", "Landroidx/loader/app/LoaderManager;", "queryType", "requestCode", "callbacks", "Landroidx/loader/app/LoaderManager$LoaderCallbacks;", "appendSelection", HtmlParser.OrderElement.TAG, "queryById", "folderid", "getSelectionForType", ToDoStat.DELETE, "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "id", "ids", "", "", "start", "end", "exist", "", "subject", "existSameAndDelete", "subjectToId", "getSubject", "folderId", "querySubject", "add", "time", "restore", "rename", "updateFolderModifiedTime", "updateEnableNewDefaultFolderNameForPop", "updateEnableNewDefaultFolderName", "", "getNewDefaultFolderName", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FolderStore {
    private static final String FOLDER_TYPE_SELECTION = "type=1";
    private static final int MAX_BATCH_SIZE = 500;
    private static final String PAD_FOLDER_DIALOG_SELECTION_FOLDER_LIST = "parent_id=0 AND (type=1      OR (_id=0 AND notes_count<>0)      OR (_id=-2 AND notes_count<>0)      OR (_id=-5 AND notes_count<>0)      OR (_id=-6 AND notes_count<>0)      OR (_id=-7 AND notes_count<>0)      OR (_id=-8 AND notes_count<>0))";
    public static final int PAD_QUERY_FOLDER_DIALOG_FOLDER_LIST = 3;
    public static final int QUERY_FOLDER_PRIVATE = 2;
    public static final int QUERY_FOLDER_USER = 0;
    public static final int QUERY_FOLDER_USER_AND_SYSTEM = 1;
    public static final int QUERY_FOLDER_USER_AND_SYSTEM_WITH_MENU = 5;
    public static final int QUERY_FOLDER_USER_INCLUDE_UNCATEGORIZED = 4;
    private static final String QUERY_SORT_ORDER = "subject ASC";
    private static final String RANGE_SELECTION = "_id IN (%s) AND type=1";
    public static final String SELECTION_FOLDER_HOMEPAGE = "parent_id=0 AND (type=1 OR (_id=-2 AND notes_count<>0) OR (_id=-5 AND notes_count<>0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0))";
    private static final String SELECTION_FOLDER_PRIVATE = "parent_id=0 AND (_id=-4)";
    public static final String SELECTION_FOLDER_USER = "parent_id=0 AND (type=1 OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0))";
    private static final String SELECTION_FOLDER_USER_AND_SYSTEM = "parent_id=0 AND (type=1      OR _id=0      OR _id=-2      OR _id=-3      OR _id=-5      OR _id=-6      OR _id=-7      OR _id=-8)";
    private static final String SELECTION_FOLDER_USER_AND_SYSTEM_WITH_MENU = "parent_id=0 AND (type=1      OR _id=0      OR _id=-3      OR (_id=-2 AND notes_count<>0)      OR (_id=-5 AND notes_count<>0)      OR (_id=-6 AND notes_count<>0)      OR (_id=-7 AND notes_count<>0)      OR (_id=-8 AND notes_count<>0))";
    private static final String SELECTION_FOLDER_USER_INCLUDE_UNCATEGORIZED = "parent_id=0 AND (type=1 OR (_id=0) OR (_id=-6 AND notes_count<>0) OR (_id=-7 AND notes_count<>0) OR (_id=-8 AND notes_count<>0))";
    private static final String TAG = "FolderStore";
    public static final FolderStore INSTANCE = new FolderStore();
    private static String pDefaultFolderName = "";
    private static String sFolderNumStr = "";

    @JvmStatic
    private static /* synthetic */ void getSFolderNumStr$annotations() {
    }

    public final long add(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        return add$default(this, context, str, 0L, 4, null);
    }

    public final boolean updateFolderModifiedTime(Context context, long j) {
        Intrinsics.checkNotNullParameter(context, "context");
        return updateFolderModifiedTime$default(this, context, j, 0L, 4, null);
    }

    private FolderStore() {
    }

    public final String getPDefaultFolderName() {
        return pDefaultFolderName;
    }

    public final void setPDefaultFolderName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        pDefaultFolderName = str;
    }

    @JvmStatic
    public static final FolderModel valueOf(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        FolderModel folderModel = new FolderModel();
        folderModel.setId(cursor.getLong(0));
        folderModel.setParentId(cursor.getLong(1));
        folderModel.setSubject(cursor.getString(14));
        folderModel.setCount(cursor.getInt(15));
        folderModel.setType(cursor.getInt(5));
        folderModel.setCreatedAt(cursor.getLong(8));
        folderModel.setUpdatedAt(cursor.getLong(9));
        return folderModel;
    }

    public final FolderModel parsePartFieldsWithMenu(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        FolderModel folderModel = new FolderModel();
        folderModel.setId(cursor.getLong(0));
        folderModel.setSubject(cursor.getString(14));
        folderModel.setCount(cursor.getInt(15));
        folderModel.setStickAt(cursor.getLong(11));
        return folderModel;
    }

    public final long getId(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        return cursor.getLong(0);
    }

    public final Loader<Cursor> query(LoaderManager loaderManager, int queryType, int requestCode, LoaderManager.LoaderCallbacks<Cursor> callbacks, String appendSelection) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(appendSelection, "appendSelection");
        return new CursorLoaderBuilder(loaderManager, requestCode).setUri(Notes.Note.CONTENT_URI).setProjection(ItemStore.PROJECTION).setSelection("( " + getSelectionForType(queryType) + ") AND (" + appendSelection + ")").setSortOrder("subject ASC").setCallbacks(callbacks).build();
    }

    public final Loader<Cursor> query(LoaderManager loaderManager, int queryType, int requestCode, LoaderManager.LoaderCallbacks<Cursor> callbacks) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        return new CursorLoaderBuilder(loaderManager, requestCode).setUri(Notes.Note.CONTENT_URI).setProjection(ItemStore.PROJECTION).setSelection(getSelectionForType(queryType)).setSortOrder("subject ASC").setCallbacks(callbacks).build();
    }

    public final Cursor query(int queryType) {
        return BaseNoteApp.INSTANCE.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, ItemStore.PROJECTION, getSelectionForType(queryType), null, "subject ASC");
    }

    public final Loader<Cursor> query(LoaderManager loaderManager, int queryType, int requestCode, String order, LoaderManager.LoaderCallbacks<Cursor> callbacks) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        return new CursorLoaderBuilder(loaderManager, requestCode).setUri(Notes.Note.CONTENT_URI).setProjection(ItemStore.PROJECTION).setSelection(getSelectionForType(queryType)).setSortOrder(order).setCallbacks(callbacks).build();
    }

    public final Loader<Cursor> queryById(LoaderManager loaderManager, long folderid, int requestCode, LoaderManager.LoaderCallbacks<Cursor> callbacks) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        return new CursorLoaderBuilder(loaderManager, requestCode).setUri(Notes.Note.CONTENT_URI).setProjection(ItemStore.PROJECTION).setSelection("parent_id=0 AND _id=" + folderid).setSortOrder(null).setCallbacks(callbacks).build();
    }

    private final String getSelectionForType(int queryType) {
        if (queryType == 0) {
            return SELECTION_FOLDER_USER;
        }
        if (queryType == 1) {
            return SELECTION_FOLDER_USER_AND_SYSTEM;
        }
        if (queryType == 2) {
            return SELECTION_FOLDER_PRIVATE;
        }
        if (queryType == 3) {
            return PAD_FOLDER_DIALOG_SELECTION_FOLDER_LIST;
        }
        if (queryType == 4) {
            return SELECTION_FOLDER_USER_INCLUDE_UNCATEGORIZED;
        }
        if (queryType == 5) {
            return SELECTION_FOLDER_USER_AND_SYSTEM_WITH_MENU;
        }
        throw new IllegalArgumentException("Folder query type " + queryType + " is not defined");
    }

    @JvmStatic
    public static final void delete(Context context, long id) {
        Intrinsics.checkNotNullParameter(context, "context");
        delete(context, new long[]{id});
    }

    public final void delete(Context context, Collection<Long> ids) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ids, "ids");
        long[] array = DataUtils.toArray(ids);
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        delete(context, array);
    }

    @JvmStatic
    public static final void delete(Context context, long[] ids) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ids, "ids");
        int i = 0;
        while (i < ids.length) {
            int iMin = (int) Math.min(i + 500, ids.length);
            INSTANCE.delete(context, ids, i, iMin);
            i = iMin;
        }
    }

    private final int delete(Context context, long[] ids, int start, int end) {
        String strJoin = DataUtils.join(ids, start, end);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, RANGE_SELECTION, Arrays.copyOf(new Object[]{strJoin}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        int iDelete = context.getContentResolver().delete(Notes.Note.CONTENT_URI, str, null);
        if (iDelete > 0) {
            Notes.Utils.updateAllWidgets(context);
        }
        return iDelete;
    }

    @JvmStatic
    public static final boolean exist(Context context, String subject) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subject, "subject");
        boolean z = true;
        Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI, null, "type=1 AND subject=?", new String[]{subject}, null);
        if (cursorQuery == null) {
            return false;
        }
        if (cursorQuery.getCount() > 0) {
            Logger.INSTANCE.d(TAG, "cursor count " + cursorQuery.getCount());
        } else {
            z = false;
        }
        cursorQuery.close();
        return z;
    }

    @JvmStatic
    public static final void existSameAndDelete(Context context, String subject) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subject, "subject");
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_FOLDER), new String[]{"_id", "sync_id"}, "parent_id=-3 AND type=1 AND subject=?", new String[]{subject}, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    cursorQuery.moveToFirst();
                    long j = cursorQuery.getLong(0);
                    cursorQuery.getLong(1);
                    if (j > 0) {
                        context.getContentResolver().delete(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_FOLDER), "_id=" + j, null);
                    }
                }
            } catch (Exception e) {
                com.miui.common.tool.Logger.INSTANCE.d(TAG, "existSameAndDelete " + ExceptionsKt.stackTraceToString(e));
                if (cursorQuery != null) {
                    return;
                } else {
                    return;
                }
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }
    }

    @JvmStatic
    public static final long subjectToId(Context context, String subject) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subject, "subject");
        final Cursor cursorQuery = context.getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"_id"}, "subject=? AND type=?", new String[]{subject, "1"}, null);
        final Ref.LongRef longRef = new Ref.LongRef();
        longRef.element = -1L;
        if (cursorQuery != null) {
            NoteEntityHelper.INSTANCE.useWithClose(cursorQuery, "FolderStore/subjectToId->subject", new Function1() { // from class: com.miui.notes.store.FolderStore$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return FolderStore.subjectToId$lambda$0(cursorQuery, longRef, (Cursor) obj);
                }
            });
        }
        return longRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit subjectToId$lambda$0(Cursor cursor, Ref.LongRef longRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.moveToNext()) {
            longRef.element = cursor.getLong(0);
        }
        return Unit.INSTANCE;
    }

    @JvmStatic
    public static final String getSubject(long folderId) {
        return folderId > 0 ? INSTANCE.querySubject(folderId) : FolderModel.INSTANCE.getSystemSubject(folderId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final String querySubject(long folderId) {
        final Cursor cursorQuery = BaseNoteApp.INSTANCE.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, new String[]{"subject"}, "_id=? AND type=?", new String[]{String.valueOf(folderId), "1"}, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (cursorQuery != null) {
            NoteEntityHelper.INSTANCE.useWithClose(cursorQuery, "FolderStore/querySubject->" + Notes.Note.CONTENT_URI, new Function1() { // from class: com.miui.notes.store.FolderStore$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return FolderStore.querySubject$lambda$1(cursorQuery, objectRef, (Cursor) obj);
                }
            });
        }
        return (String) objectRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.String] */
    public static final Unit querySubject$lambda$1(Cursor cursor, Ref.ObjectRef objectRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (cursor.moveToNext()) {
            objectRef.element = cursor.getString(0);
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ long add$default(FolderStore folderStore, Context context, String str, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = System.currentTimeMillis();
        }
        return folderStore.add(context, str, j);
    }

    public final long add(Context context, String subject, long time) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put("type", (Integer) 1);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("modified_date", Long.valueOf(time));
        Uri uriInsert = context.getContentResolver().insert(Notes.Note.CONTENT_URI, contentValues);
        Intrinsics.checkNotNull(uriInsert);
        return ContentUris.parseId(uriInsert);
    }

    @JvmStatic
    public static final boolean restore(Context context, String subject) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subject, "subject");
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("parent_id", (Integer) 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues.put("created_date", Long.valueOf(jCurrentTimeMillis));
        contentValues.put("modified_date", Long.valueOf(jCurrentTimeMillis));
        return context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "subject=? AND type=? AND parent_id=?", new String[]{subject, "1", "-3"}) > 0;
    }

    @JvmStatic
    public static final boolean rename(Context context, long folderId, String subject) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("modified_date", Long.valueOf(System.currentTimeMillis()));
        try {
            return context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, "_id=? AND type=?", new String[]{String.valueOf(folderId), "1"}) > 0;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Fail to rename folder,exception:" + e);
            return false;
        }
    }

    public static /* synthetic */ boolean updateFolderModifiedTime$default(FolderStore folderStore, Context context, long j, long j2, int i, Object obj) {
        if ((i & 4) != 0) {
            j2 = System.currentTimeMillis();
        }
        return folderStore.updateFolderModifiedTime(context, j, j2);
    }

    public final boolean updateFolderModifiedTime(Context context, long folderId, long time) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("modified_date", Long.valueOf(time));
        try {
            return context.getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, "_id=? ", new String[]{String.valueOf(folderId)}) > 0;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Fail to update folder modifiedTime,exception:" + e);
            return false;
        }
    }

    @JvmStatic
    public static final boolean updateEnableNewDefaultFolderNameForPop(Context context, Cursor cursor) {
        String strValueOf;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        cursor.moveToPosition(-1);
        String string = context.getResources().getString(R.string.default_folder_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        HashSet hashSet = new HashSet();
        boolean z = false;
        while (cursor.moveToNext()) {
            if (cursor.getLong(0) > 0) {
                z = true;
            }
            String string2 = cursor.getString(1);
            int length = string.length();
            if (string2 != null && string2.length() >= length && Intrinsics.areEqual(string2.subSequence(0, length), string)) {
                if (string2.length() == string.length()) {
                    hashSet.add(1);
                } else {
                    String strSubstring = string2.substring(length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    try {
                        Boolean.valueOf(hashSet.add(Integer.valueOf(Integer.parseInt(strSubstring))));
                    } catch (NumberFormatException unused) {
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        boolean z2 = true;
        int i = 1;
        while (z2) {
            if (hashSet.contains(Integer.valueOf(i))) {
                i++;
            } else {
                z2 = false;
            }
        }
        if (i == 1) {
            strValueOf = "";
        } else {
            strValueOf = String.valueOf(i);
        }
        sFolderNumStr = strValueOf;
        return z;
    }

    @JvmStatic
    public static final boolean[] updateEnableNewDefaultFolderName(Context context, Cursor cursor) {
        String strValueOf;
        Intrinsics.checkNotNullParameter(context, "context");
        if (cursor != null) {
            cursor.moveToPosition(-1);
        }
        String string = context.getResources().getString(R.string.default_folder_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        HashSet hashSet = new HashSet();
        boolean z = false;
        boolean z2 = false;
        while (cursor != null && cursor.moveToNext()) {
            FolderModel folderModelValueOf = valueOf(cursor);
            long id = folderModelValueOf.getId();
            if (id > 0) {
                z = true;
            }
            if (FolderModel.INSTANCE.isSpecialFolder(id)) {
                z2 = true;
            }
            String subject = folderModelValueOf.getSubject();
            int length = string.length();
            if (subject != null && subject.length() >= length && Intrinsics.areEqual(subject.subSequence(0, length), string)) {
                if (subject.length() == string.length()) {
                    hashSet.add(1);
                } else {
                    String strSubstring = subject.substring(length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    try {
                        Boolean.valueOf(hashSet.add(Integer.valueOf(Integer.parseInt(strSubstring))));
                    } catch (NumberFormatException unused) {
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        boolean z3 = true;
        int i = 1;
        while (z3) {
            if (hashSet.contains(Integer.valueOf(i))) {
                i++;
            } else {
                z3 = false;
            }
        }
        if (i == 1) {
            strValueOf = "";
        } else {
            strValueOf = String.valueOf(i);
        }
        sFolderNumStr = strValueOf;
        return new boolean[]{z, z2};
    }

    public final String getNewDefaultFolderName() {
        String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.default_folder_name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        if (!TextUtils.isEmpty(sFolderNumStr)) {
            string = string + sFolderNumStr;
        }
        com.miui.common.tool.Logger.INSTANCE.d(TAG, "newDefaultFolderName " + string);
        return string;
    }
}
