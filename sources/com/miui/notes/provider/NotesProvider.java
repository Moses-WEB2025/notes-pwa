package com.miui.notes.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import android.webkit.MimeTypeMap;
import androidx.appsearch.ast.FunctionNode;
import androidx.hardware.DataSpace;
import ch.qos.logback.classic.pattern.CallerDataConverter;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.common.stat.NoteAppStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.doodle.document.Layer;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.model.HandWriteDataUtils;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.preference.PreferenceDatabaseHelper;
import com.miui.notes.preference.PreferenceProviderHelper;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.tool.util.EncryptFailedException;
import com.miui.notes.tool.util.EncryptUtil;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.richeditor.schema.NoteSchema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.internal.url._UrlKt;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NotesProvider extends ContentProvider {
    public static final Uri AI_QUERY_URI;
    public static final String CALLER_IS_AI_TEXT = "caller_is_ai_text";
    public static final String CALLER_IS_BLACKBOARD_TEXT = "caller_is_blackboard_text";
    public static final String CALLER_IS_CALL_NOTE = "caller_is_call_note";
    public static final String CALLER_IS_EXCERPT_TEXT = "caller_is_excerpt_text";
    public static final String CALLER_IS_FOLDER = "caller_is_folder";
    public static final String CALLER_IS_HEAR_TEXT = "caller_is_hear_text";
    public static final String CALLER_IS_PERSONAL_ASSISTANT = "caller_is_personal_assistant";
    public static final String CALLER_IS_PLAIN_TEXT = "caller_is_plain_text";
    public static final String CALLER_IS_PRIVACY = "caller_is_privacy";
    public static final String CALLER_IS_STYULE_TEXT = "caller_is_stylue_text";
    private static final String COLUMN_MEDIA_NAME = "xmn_media_name";
    private static final String COLUMN_MEDIA_PATH = "xmn_media_path";
    private static final String EXPECTED_COUNT = "expectedCount";
    private static final String NOTES_SEARCH_PROJECTION = "_id,snippet";
    private static final String NOTES_SNIPPET_SEARCH_QUERY = "SELECT _id,snippet FROM note_view WHERE plain_text LIKE ? ESCAPE '/' AND type=0";
    private static final String NOTES_SNIPPET_SEARCH_VALIDATE = "SELECT _id,snippet FROM note_view WHERE type=0 AND _id=?";
    private static final String QUERY_ALL_NOTES_NO_DELETED_SELECTION = "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4";
    private static final String QUERY_PARAM_KEEP_STYLE = "keepStyle";
    private static final Uri RESTORE_CONTENT_URI;
    private static final int RESULT_NO_PARENT_ID = -100;
    private static final int RESULT_OK = 0;
    private static final String SORT_BY_CREATED_DATE = "stick_date DESC, created_date DESC,modified_date DESC";
    private static final String TAG = "NotesProvider";
    private static final int URI_ACCOUNT = 9;
    private static final int URI_ACCOUNT_ITEM = 10;
    private static final int URI_DATA = 3;
    private static final int URI_DATA_ITEM = 4;
    private static final int URI_DATA_MISSED = 11;
    private static final int URI_MEDIA = 8;
    private static final int URI_NOTE = 1;
    private static final int URI_NOTE_ATOMIC = 12;
    private static final int URI_NOTE_ATOMIC_ITEM = 13;
    private static final int URI_NOTE_ATOMIC_MULTI_ITEM = 19;
    private static final int URI_NOTE_ITEM = 2;
    private static final int URI_NOTE_RESTORE = 17;
    private static final int URI_NOTE_WIDGET = 16;
    private static final int URI_PREF = 18;
    private static final int URI_SCRAP = 7;
    private static final int URI_SEARCH = 5;
    private static final int URI_SEARCH_SUGGEST = 6;
    private static final int URI_SEARCH_VALIDATE = 15;
    public static final Uri WIDGET_CONTENT_URI;
    private static final UriMatcher mMatcher;
    private Bundle mExtras;
    private NotesDatabaseHelper mHelper;
    private ContentChangeNotifier mNotifier;
    private PreferenceProviderHelper mPrefHelper;
    private ContentResolver mResolver;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        mMatcher = uriMatcher;
        uriMatcher.addURI("notes", "note", 1);
        uriMatcher.addURI("notes", "note/#", 2);
        uriMatcher.addURI("notes", "data", 3);
        uriMatcher.addURI("notes", "data/#", 4);
        uriMatcher.addURI("notes", FunctionNode.FUNCTION_NAME_SEARCH, 5);
        uriMatcher.addURI("notes", "search_suggest_query", 6);
        uriMatcher.addURI("notes", "search_suggest_query/*", 6);
        uriMatcher.addURI("notes", "search_suggest_shortcut/#", 15);
        uriMatcher.addURI("notes", "scrap", 7);
        uriMatcher.addURI("notes", "data/media/#", 8);
        uriMatcher.addURI("notes", NotesDatabaseHelper.TABLE.ACCOUNT, 9);
        uriMatcher.addURI("notes", "account/#", 10);
        uriMatcher.addURI("notes", "data/missed", 11);
        uriMatcher.addURI("notes", "note/atomic", 12);
        uriMatcher.addURI("notes", "note/atomic/#", 13);
        uriMatcher.addURI("notes", "note/atomic_multi_page/#", 19);
        uriMatcher.addURI("notes", "note/widget", 16);
        uriMatcher.addURI("notes", "note/restore", 17);
        uriMatcher.addURI("notes", "pref", 18);
        AI_QUERY_URI = Uri.parse("content://notes/ai/notify");
        WIDGET_CONTENT_URI = Uri.parse("content://notes/note/widget");
        RESTORE_CONTENT_URI = Uri.parse("content://notes/note/restore");
    }

    public static String getScrapPath(Context context) {
        if (context == null || context.getExternalCacheDir() == null) {
            return null;
        }
        return context.getExternalCacheDir().getAbsolutePath() + "/.temp.jpg";
    }

    public static Uri appendFlag(Uri uri, String str) {
        return uri.buildUpon().appendQueryParameter(str, "true").build();
    }

    public static Uri getRestoreUri(int i) {
        return RESTORE_CONTENT_URI.buildUpon().appendQueryParameter(EXPECTED_COUNT, String.valueOf(i)).build();
    }

    public static File getScrapFile(Context context) {
        String scrapPath = getScrapPath(context);
        if (scrapPath == null) {
            return null;
        }
        try {
            File file = new File(scrapPath);
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    Logger.INSTANCE.e(TAG, "Fail to create parent directory: " + parentFile.getPath());
                    return null;
                }
                parentFile.setExecutable(true, true);
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            file.setReadable(true, true);
            file.setWritable(true, true);
            return file;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "File to getScrapFile" + scrapPath + ",exception:" + e);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        Logger.INSTANCE.d(TAG, "onCreate: context=" + context);
        this.mHelper = NotesDatabaseHelper.getInstance(context);
        this.mResolver = context.getContentResolver();
        Bundle bundle = new Bundle();
        this.mExtras = bundle;
        bundle.putInt("push_type", 1);
        this.mNotifier = new ContentChangeNotifier();
        this.mPrefHelper = getPreferenceProviderHelper(context);
        return true;
    }

    private PreferenceProviderHelper getPreferenceProviderHelper(Context context) {
        return new PreferenceProviderHelper(context, PreferenceDatabaseHelper.getInstance(context, new NotesSharedPreferenceProvider(context)));
    }

    /* JADX WARN: Code duplicated, block: B:111:0x0325  */
    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String[] strArr3;
        String str3;
        String string;
        String str4;
        Cursor cursorQuery;
        Cursor noteCursorWrapper;
        String queryParameter;
        Cursor cursorRawQuery;
        Cursor searchCursorWrapper;
        UriMatcher uriMatcher = mMatcher;
        int iMatch = uriMatcher.match(uri);
        Logger.INSTANCE.d(TAG, "query:,sel:" + str + ",matchCode:" + iMatch);
        if (!enforceNewPermission(true, uri)) {
            Logger.INSTANCE.d(TAG, "query: no permission");
            return null;
        }
        if (iMatch == 18) {
            Logger.INSTANCE.d(TAG, "URI PREF MATCH");
            return this.mPrefHelper.query(uri, strArr, str, strArr2, str2);
        }
        SQLiteDatabase readableDatabase = this.mHelper.getReadableDatabase();
        boolean booleanQueryParameter = uri.getBooleanQueryParameter(Notes.CALLER_IS_SYNCADAPTER, false);
        boolean booleanQueryParameter2 = uri.getBooleanQueryParameter(CALLER_IS_PRIVACY, false);
        boolean booleanQueryParameter3 = uri.getBooleanQueryParameter(CALLER_IS_AI_TEXT, false);
        boolean booleanQueryParameter4 = uri.getBooleanQueryParameter(CALLER_IS_FOLDER, false);
        if (booleanQueryParameter3) {
            strArr3 = strArr == null ? NoteProviderAccessUtils.PROJECTION : strArr;
            String str5 = str == null ? "type=0 AND deletion_tag <> 1 AND parent_id <> -3 AND parent_id <> -4" : str;
            if (str2 == null) {
                string = str5;
                str3 = "stick_date DESC, created_date DESC,modified_date DESC";
            } else {
                string = str5;
                str3 = str2;
            }
        } else {
            strArr3 = strArr;
            str3 = str2;
            string = str;
        }
        boolean z = booleanQueryParameter || booleanQueryParameter2 || booleanQueryParameter3;
        boolean booleanQueryParameter5 = uri.getBooleanQueryParameter(CALLER_IS_CALL_NOTE, false);
        if (z) {
            str4 = "note";
        } else {
            str4 = booleanQueryParameter5 ? NotesDatabaseHelper.VIEW.NOTE_CALL : NotesDatabaseHelper.VIEW.NOTE;
        }
        if (booleanQueryParameter4) {
            str4 = "note";
        }
        String str6 = z ? "data" : NotesDatabaseHelper.VIEW.DATA;
        Logger.INSTANCE.d(TAG, "query: matchCode=" + iMatch);
        switch (iMatch) {
            case 1:
                cursorQuery = readableDatabase.query(str4, strArr3, string, strArr2, null, null, str3);
                if (z) {
                    noteCursorWrapper = new NoteCursorWrapper(getContext(), cursorQuery);
                    cursorQuery = noteCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 2:
                cursorQuery = readableDatabase.query(str4, strArr3, "_id=" + ContentUris.parseId(uri) + parseSelection(string), strArr2, null, null, str3);
                if (z) {
                    noteCursorWrapper = new NoteCursorWrapper(getContext(), cursorQuery);
                    cursorQuery = noteCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 3:
                String[] strArr4 = strArr3;
                cursorQuery = readableDatabase.query(z ? join(str6, str4) : str6, z ? appendColumn(addPrefix(str6, strArr4), "parent_id", "origin_parent_id") : strArr4, string, strArr2, null, null, str3);
                if (z) {
                    noteCursorWrapper = new DataCursorWrapper(getContext(), cursorQuery);
                    cursorQuery = noteCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 4:
                String[] strArr5 = strArr3;
                cursorQuery = readableDatabase.query(z ? join(str6, str4) : str6, z ? appendColumn(addPrefix(str6, strArr5), "parent_id", "origin_parent_id") : strArr5, "_id=" + ContentUris.parseId(uri) + parseSelection(string), strArr2, null, null, str3);
                if (z) {
                    noteCursorWrapper = new DataCursorWrapper(getContext(), cursorQuery);
                    cursorQuery = noteCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 5:
            case 6:
                String[] strArr6 = strArr3;
                if (str3 != null || strArr6 != null) {
                    throw new IllegalArgumentException("do not specify sortOrder, selection, selectionArgs, or projectionwith this query");
                }
                if (uriMatcher.match(uri) == 6) {
                    queryParameter = uri.getPathSegments().size() > 1 ? uri.getPathSegments().get(1) : null;
                } else {
                    queryParameter = uri.getQueryParameter("pattern");
                }
                if (TextUtils.isEmpty(queryParameter)) {
                    return null;
                }
                try {
                    cursorRawQuery = readableDatabase.rawQuery(NOTES_SNIPPET_SEARCH_QUERY, new String[]{String.format("%%%s%%", queryParameter.replaceAll("([_%/])", "/$1").replaceAll("'", "''"))});
                    if (cursorRawQuery != null) {
                        try {
                            cursorQuery = new SearchCursorWrapper(getContext(), cursorRawQuery, queryParameter);
                        } catch (IllegalStateException e) {
                            e = e;
                            Logger.INSTANCE.e(TAG, "got exception: " + e.toString());
                            cursorQuery = cursorRawQuery;
                        }
                    } else {
                        cursorQuery = cursorRawQuery;
                    }
                    break;
                } catch (IllegalStateException e2) {
                    e = e2;
                    cursorRawQuery = null;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 7:
            case 12:
            case 13:
            case 14:
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
            case 8:
                cursorQuery = getFakeCursorForMedia(readableDatabase, uri, strArr3, string, strArr2, str3);
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 9:
                cursorQuery = readableDatabase.query(NotesDatabaseHelper.TABLE.ACCOUNT, strArr3, string, strArr2, null, null, str3);
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 10:
                cursorQuery = readableDatabase.query(NotesDatabaseHelper.TABLE.ACCOUNT, strArr3, "_id=" + ContentUris.parseId(uri) + parseSelection(string), strArr2, null, null, str3);
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 11:
                cursorQuery = transformMissedCursor(readableDatabase.query("data", strArr3, String.format("mime_type<>'%s' AND mime_type<>'%s'", Notes.TextData.CONTENT_ITEM_TYPE, Notes.CallData.CONTENT_ITEM_TYPE) + parseSelection(string), strArr2, null, null, str3));
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 15:
                long id = ContentUris.parseId(uri);
                String queryParameter2 = uri.getQueryParameter("suggest_intent_extra_data");
                cursorQuery = readableDatabase.rawQuery(NOTES_SNIPPET_SEARCH_VALIDATE, new String[]{Long.toString(id)});
                if (cursorQuery != null) {
                    searchCursorWrapper = new SearchCursorWrapper(getContext(), cursorQuery, queryParameter2);
                    cursorQuery = searchCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
            case 16:
                boolean booleanQueryParameter6 = uri.getBooleanQueryParameter(QUERY_PARAM_KEEP_STYLE, true);
                if (!TextUtils.isEmpty(string)) {
                    StringBuffer stringBuffer = new StringBuffer(string);
                    stringBuffer.append("and note_content_type = 'common'");
                    string = stringBuffer.toString();
                }
                cursorQuery = readableDatabase.query(str4, strArr3, string, strArr2, null, null, str3);
                if (cursorQuery != null) {
                    searchCursorWrapper = new StyleableCursorWrapper(getContext(), cursorQuery, booleanQueryParameter6);
                    cursorQuery = searchCursorWrapper;
                }
                if (cursorQuery != null) {
                    cursorQuery.setNotificationUri(this.mResolver, uri);
                }
                return cursorQuery;
        }
    }

    private String join(String str, String str2) {
        return str + " inner join " + str2 + " on " + str2 + "._id=note_id";
    }

    private String[] addPrefix(String str, String[] strArr) {
        if (strArr == null) {
            return null;
        }
        for (int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i];
            if ("_id".equals(str2)) {
                String[] strArr2 = new String[strArr.length];
                System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                strArr2[i] = str + "." + str2;
                return strArr2;
            }
        }
        return strArr;
    }

    private String[] appendColumn(String[] strArr, String... strArr2) {
        if (strArr == null) {
            return null;
        }
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    private Cursor getFakeCursorForMedia(SQLiteDatabase sQLiteDatabase, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return getFakeCursorForMedia(strArr, getMediaInfo(sQLiteDatabase, uri, str, strArr2, str2));
    }

    private Cursor getFakeCursorForMedia(String[] strArr, ContentValues contentValues) {
        if (contentValues == null) {
            return null;
        }
        String asString = contentValues.getAsString(Notes.Data.MIME_TYPE);
        if (isImageType(asString)) {
            return getFakeCursorForImage(strArr, contentValues);
        }
        Logger.INSTANCE.w(TAG, "Unsupported MimeType: " + asString);
        return null;
    }

    private Cursor getFakeCursorForImage(String[] strArr, ContentValues contentValues) {
        String str;
        String str2 = "_size";
        int i = 0;
        String[] strArr2 = strArr == null ? new String[]{"_display_name", "_size", "_data", "date_added", "date_modified", Notes.Data.MIME_TYPE, "title", Layer.KEY_WIDTH, Layer.KEY_HEIGHT} : strArr;
        MatrixCursor matrixCursor = new MatrixCursor(strArr2, 1);
        MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
        int length = strArr2.length;
        while (i < length) {
            int i2 = i;
            String str3 = strArr2[i2];
            if ("_display_name".equals(str3)) {
                rowBuilderNewRow.add(getMediaName(contentValues));
                str = str2;
            } else if (str2.equals(str3)) {
                str = str2;
                rowBuilderNewRow.add(Long.valueOf(new File(getMediaPath(contentValues)).length()));
            } else {
                str = str2;
                if ("_data".equals(str3)) {
                    rowBuilderNewRow.add(getMediaPath(contentValues));
                } else if ("date_added".equals(str3)) {
                    rowBuilderNewRow.add(contentValues.getAsLong("date_added"));
                } else if ("date_modified".equals(str3)) {
                    rowBuilderNewRow.add(contentValues.getAsLong("date_modified"));
                } else if (Notes.Data.MIME_TYPE.equals(str3)) {
                    rowBuilderNewRow.add(contentValues.getAsString(Notes.Data.MIME_TYPE));
                } else if ("title".equals(str3)) {
                    rowBuilderNewRow.add(contentValues.getAsString("content"));
                } else if (Layer.KEY_WIDTH.equals(str3)) {
                    loadImageSpecific(contentValues);
                    rowBuilderNewRow.add(contentValues.getAsString(Layer.KEY_WIDTH));
                } else if (Layer.KEY_HEIGHT.equals(str3)) {
                    loadImageSpecific(contentValues);
                    rowBuilderNewRow.add(contentValues.getAsString(Layer.KEY_HEIGHT));
                } else {
                    rowBuilderNewRow.add(null);
                }
            }
            i = i2 + 1;
            str2 = str;
        }
        return matrixCursor;
    }

    private ContentValues getMediaInfo(SQLiteDatabase sQLiteDatabase, Uri uri, String str, String[] strArr, String str2) {
        String str3;
        long id = ContentUris.parseId(uri);
        if (str == null) {
            str3 = "_id=" + id;
        } else {
            str3 = String.format(Locale.US, "(%s=%d) AND (%s)", "_id", Long.valueOf(id), str);
        }
        Cursor cursorQuery = sQLiteDatabase.query("data", new String[]{Notes.Data.MIME_TYPE, "created_date", "modified_date", "content"}, str3, strArr, null, null, str2);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (!cursorQuery.moveToNext()) {
                return null;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(Notes.Data.MIME_TYPE, cursorQuery.getString(0));
            contentValues.put("date_added", Long.valueOf(cursorQuery.getLong(1)));
            contentValues.put("date_modified", Long.valueOf(cursorQuery.getLong(2)));
            contentValues.put("content", cursorQuery.getString(3));
            contentValues.put("_id", Long.valueOf(id));
            return contentValues;
        } finally {
            cursorQuery.close();
        }
    }

    private ContentValues getMediaInfo(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Logger.INSTANCE.w(TAG, "File doesn't exist, path=" + str);
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEDIA_PATH, str);
        contentValues.put(COLUMN_MEDIA_NAME, file.getName());
        contentValues.put("date_added", Long.valueOf(file.lastModified()));
        contentValues.put("date_modified", Long.valueOf(file.lastModified()));
        contentValues.put("content", file.getName());
        loadImageSpecific(contentValues);
        return contentValues;
    }

    private String getMediaPath(ContentValues contentValues) {
        String asString = contentValues.getAsString(COLUMN_MEDIA_PATH);
        if (asString != null) {
            return asString;
        }
        String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), contentValues.getAsString("content"));
        contentValues.put(COLUMN_MEDIA_PATH, attachmentPath);
        return attachmentPath;
    }

    private String getMediaName(ContentValues contentValues) {
        if (!contentValues.containsKey(COLUMN_MEDIA_NAME)) {
            String string = Long.toString(contentValues.getAsLong("_id").longValue());
            String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentValues.getAsString(Notes.Data.MIME_TYPE));
            if (extensionFromMimeType != null) {
                string = string + "." + extensionFromMimeType;
            }
            contentValues.put(COLUMN_MEDIA_NAME, string);
        }
        return contentValues.getAsString(COLUMN_MEDIA_NAME);
    }

    private void loadImageSpecific(ContentValues contentValues) {
        if (contentValues.containsKey(Layer.KEY_WIDTH)) {
            return;
        }
        Object[] objArrLoadImageSpecific = loadImageSpecific(getMediaPath(contentValues));
        contentValues.put(Notes.Data.MIME_TYPE, (String) objArrLoadImageSpecific[0]);
        contentValues.put(Layer.KEY_WIDTH, (Integer) objArrLoadImageSpecific[1]);
        contentValues.put(Layer.KEY_HEIGHT, (Integer) objArrLoadImageSpecific[2]);
    }

    private Object[] loadImageSpecific(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new Object[]{options.outMimeType, Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight)};
    }

    private Cursor transformMissedCursor(Cursor cursor) {
        boolean z;
        if (cursor == null) {
            return null;
        }
        try {
            int columnIndex = cursor.getColumnIndex("content");
            if (columnIndex < 0) {
                throw new IllegalArgumentException("projections must contain content column");
            }
            int columnIndex2 = cursor.getColumnIndex(Notes.Data.DATA_NEED_UPDATE);
            int columnIndex3 = cursor.getColumnIndex(Notes.Data.DATA_CONTENT_TYPE);
            MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());
            int columnCount = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                String string = cursor.getString(columnIndex);
                String string2 = "";
                int i = 0;
                if (columnIndex2 < 0 || columnIndex3 < 0) {
                    z = false;
                } else {
                    try {
                        z = cursor.getInt(columnIndex2) == 1;
                        try {
                            string2 = cursor.getString(columnIndex3);
                        } catch (Exception e) {
                            e = e;
                            Logger.INSTANCE.d(TAG, "getNeedUpdate error = " + e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        z = false;
                    }
                }
                Logger.INSTANCE.d(TAG, "transformMissedCursor: context=" + getContext());
                String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), string);
                if (attachmentPath != null) {
                    File file = new File(attachmentPath);
                    if (TextUtils.equals(string2, "multi_handwrite")) {
                        if (!file.exists() || z) {
                            MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
                            while (i < columnCount) {
                                rowBuilderNewRow.add(cursor.getString(i));
                                i++;
                            }
                        }
                    } else if (!file.exists()) {
                        MatrixCursor.RowBuilder rowBuilderNewRow2 = matrixCursor.newRow();
                        while (i < columnCount) {
                            rowBuilderNewRow2.add(cursor.getString(i));
                            i++;
                        }
                    }
                }
            }
            cursor.close();
            return matrixCursor;
        } catch (Throwable th) {
            cursor.close();
            throw th;
        }
    }

    private Uri insertPersonalAssistantNote(ContentValues contentValues) {
        NoteEntity noteEntity = new NoteEntity();
        String asString = contentValues.getAsString("snippet");
        if (asString == null) {
            return null;
        }
        noteEntity.setContent(asString);
        String asString2 = contentValues.getAsString("title");
        if (asString2 != null) {
            noteEntity.setTitle(asString2);
        }
        noteEntity.setParentId(0L);
        noteEntity.applyWidgetId(contentValues.getAsInteger("widget_id").intValue());
        noteEntity.applyWidgetType(contentValues.getAsInteger("widget_type").intValue());
        return noteEntity.insertNote(getContext());
    }

    private Uri insertPlainTextNote(ContentValues contentValues) {
        Logger.INSTANCE.d(TAG, "insertPlainTextNote");
        return insertPlainTextNote(contentValues, 0L);
    }

    private Uri insertPlainTextNote(ContentValues contentValues, long j) {
        NoteEntity noteEntity = new NoteEntity();
        String asString = contentValues.getAsString("snippet");
        if (asString == null) {
            return null;
        }
        if (contentValues.containsKey("title")) {
            String asString2 = contentValues.getAsString("title");
            if (!TextUtils.isEmpty(asString2)) {
                noteEntity.setTitle(asString2);
            }
        }
        noteEntity.setContent(asString);
        noteEntity.setParentId(j);
        return noteEntity.insertNote(getContext());
    }

    private void processMindContentFile(ContentValues contentValues, String str) {
        File file = new File(str);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    if (Build.VERSION.SDK_INT >= 33) {
                        contentValues.put(Notes.Note.MIND_CONTENT, new String(fileInputStream.readAllBytes()));
                        file.delete();
                    }
                    fileInputStream.close();
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Logger.INSTANCE.e(TAG, e.getMessage() + StringUtils.SPACE);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:79:0x0160  */
    /* JADX WARN: Code duplicated, block: B:82:0x018d  */
    /* JADX WARN: Code duplicated, block: B:85:0x01a5  */
    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        long jInsert;
        long j;
        long jInsert2;
        String asString = contentValues.getAsString(Notes.Note.MIND_CONTENT_PATH);
        contentValues.remove(Notes.Note.MIND_CONTENT_PATH);
        ContentValues contentValues2 = new ContentValues(contentValues);
        if (asString != null) {
            processMindContentFile(contentValues2, asString);
        }
        Logger.INSTANCE.d(TAG, "insert uri ");
        if (!enforceNewPermission(false, uri)) {
            Logger.INSTANCE.d(TAG, "insert: no permission");
            return null;
        }
        int iMatch = mMatcher.match(uri);
        if (iMatch == 18) {
            return this.mPrefHelper.insert(uri, contentValues2);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_PERSONAL_ASSISTANT, false)) {
            return insertPersonalAssistantNote(contentValues2);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_PLAIN_TEXT, false)) {
            return insertPlainTextNote(contentValues2);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_HEAR_TEXT, false)) {
            return insertPlainTextNote(contentValues2, -5L);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_EXCERPT_TEXT, false)) {
            return insertPlainTextNote(contentValues2, -6L);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_BLACKBOARD_TEXT, false)) {
            return insertPlainTextNote(contentValues2, -7L);
        }
        if (uri.getBooleanQueryParameter(CALLER_IS_STYULE_TEXT, false)) {
            return insertPlainTextNote(contentValues2, -8L);
        }
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        if (iMatch == 1) {
            Integer asInteger = contentValues2.getAsInteger("type");
            if (asInteger == null) {
                asInteger = 0;
            }
            if (1 == asInteger.intValue()) {
                verifyNoteValues(contentValues2);
            } else if (asInteger.intValue() == 0) {
                insertPlainTextValue(contentValues2);
            }
            updateSystemFolderTimeIfNeed(contentValues2);
            jInsert = writableDatabase.insert("note", null, contentValues2);
            if (contentValues2.containsKey("parent_id")) {
                updateFolder(writableDatabase, contentValues2.getAsInteger("parent_id").intValue());
            }
        } else {
            if (iMatch == 3) {
                if (contentValues2.containsKey("note_id")) {
                    jInsert = contentValues2.getAsLong("note_id").longValue();
                } else {
                    Logger.INSTANCE.w(TAG, "Wrong data format without note id");
                    jInsert = 0;
                }
                jInsert2 = writableDatabase.insert("data", null, contentValues2);
                j = jInsert2;
            } else if (iMatch == 9) {
                jInsert2 = writableDatabase.insert(NotesDatabaseHelper.TABLE.ACCOUNT, null, contentValues2);
                jInsert = 0;
                j = 0;
            } else if (iMatch == 12) {
                Integer asInteger2 = contentValues2.getAsInteger("type");
                if (asInteger2 == null) {
                    asInteger2 = 0;
                }
                if (1 == asInteger2.intValue()) {
                    verifyNoteValues(contentValues2);
                }
                updateSystemFolderTimeIfNeed(contentValues2);
                jInsert = insertNoteAtomic(writableDatabase, contentValues2);
                if (jInsert > 0 && contentValues2.containsKey("parent_id")) {
                    updateFolder(writableDatabase, contentValues2.getAsInteger("parent_id").intValue());
                }
            } else {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
            if (jInsert > 0) {
                this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, jInsert), 0));
                this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, jInsert), 4));
            }
            if (j > 0) {
                this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j), 0));
            }
            if (jInsert <= 0 || j > 0) {
                this.mNotifier.addUri(new Pair<>(WIDGET_CONTENT_URI, 0));
                this.mNotifier.notifyChange(this.mResolver);
                requestSync();
            }
            return ContentUris.withAppendedId(uri, jInsert2);
        }
        jInsert2 = jInsert;
        j = 0;
        if (jInsert > 0) {
            this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, jInsert), 0));
            this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, jInsert), 4));
        }
        if (j > 0) {
            this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(Notes.Note.CONTENT_URI, j), 0));
        }
        if (jInsert <= 0) {
            this.mNotifier.addUri(new Pair<>(WIDGET_CONTENT_URI, 0));
            this.mNotifier.notifyChange(this.mResolver);
            requestSync();
        } else {
            this.mNotifier.addUri(new Pair<>(WIDGET_CONTENT_URI, 0));
            this.mNotifier.notifyChange(this.mResolver);
            requestSync();
        }
        return ContentUris.withAppendedId(uri, jInsert2);
    }

    private void insertPlainTextValue(ContentValues contentValues) {
        if (contentValues.containsKey("snippet")) {
            String asString = contentValues.getAsString("snippet");
            if (!TextUtils.isEmpty(asString)) {
                contentValues.put("plain_text", NoteSchema.extractPlainTextForNotes(asString));
            } else {
                contentValues.put("plain_text", "");
            }
        }
    }

    public void resolveId(CharSequence charSequence, ArrayList<Long> arrayList) {
        String strGroup;
        Matcher matcher = Pattern.compile("_id\\s+IN\\s+\\(\\d+(,\\d+)*\\)").matcher(charSequence);
        if (!matcher.find()) {
            strGroup = "";
        } else {
            strGroup = matcher.group();
        }
        if (strGroup.isEmpty()) {
            return;
        }
        Matcher matcher2 = Pattern.compile("(?<!\\d)\\d+(?!\\d)").matcher(strGroup);
        while (matcher2.find()) {
            arrayList.add(Long.valueOf(Long.parseLong(matcher2.group())));
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0188 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x018a  */
    /* JADX WARN: Code duplicated, block: B:50:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:52:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:55:0x01b4 A[LOOP:0: B:53:0x01ae->B:55:0x01b4, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:56:0x01d3  */
    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        String str2;
        int iDelete;
        long j;
        boolean z;
        Iterator<Long> it;
        int iDeleteNotes;
        Logger.INSTANCE.d(TAG, "delete uri: -selection:" + str);
        if (!enforceNewPermission(false, uri)) {
            Logger.INSTANCE.d(TAG, "delete: no permission");
            return 0;
        }
        int iMatch = mMatcher.match(uri);
        if (iMatch == 18) {
            return this.mPrefHelper.delete(uri, str, strArr);
        }
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        boolean booleanQueryParameter = uri.getBooleanQueryParameter(Notes.CALLER_IS_SYNCADAPTER, false);
        boolean booleanQueryParameter2 = uri.getBooleanQueryParameter(CALLER_IS_FOLDER, false);
        ArrayList<Long> arrayList = new ArrayList<>();
        Logger.INSTANCE.d(TAG, "delete matchCode " + iMatch);
        boolean z2 = true;
        if (iMatch == 1) {
            if (str != null) {
                str2 = "(" + str + ") AND (_id>0)";
            } else {
                str2 = "(_id>0)";
            }
            resolveId(str2, arrayList);
            HashSet<Long> updatedFolderIds = getUpdatedFolderIds(writableDatabase, null, str2, strArr);
            if (!booleanQueryParameter && !booleanQueryParameter2) {
                z2 = false;
            }
            int iDeleteNotes2 = deleteNotes(writableDatabase, str2, strArr, z2);
            updateFolder(writableDatabase, updatedFolderIds);
            iDelete = iDeleteNotes2;
            j = 0;
        } else {
            if (iMatch == 2) {
                j = Long.parseLong(uri.getPathSegments().get(1));
                if (j <= 0) {
                    iDelete = 0;
                } else {
                    String str3 = "_id=" + j + parseSelection(str);
                    HashSet<Long> updatedFolderIds2 = getUpdatedFolderIds(writableDatabase, null, str3, strArr);
                    iDeleteNotes = deleteNotes(writableDatabase, str3, strArr, booleanQueryParameter);
                    updateFolder(writableDatabase, updatedFolderIds2);
                    iDelete = iDeleteNotes;
                }
            } else if (iMatch == 3) {
                iDelete = writableDatabase.delete("data", str, strArr);
                z = true;
                j = 0;
            } else if (iMatch == 4) {
                j = Long.parseLong(uri.getPathSegments().get(1));
                iDelete = writableDatabase.delete("data", "_id=" + j + parseSelection(str), strArr);
                z = true;
            } else if (iMatch == 9) {
                iDelete = writableDatabase.delete(NotesDatabaseHelper.TABLE.ACCOUNT, str, strArr);
                j = 0;
            } else if (iMatch == 10) {
                j = Long.parseLong(uri.getPathSegments().get(1));
                iDeleteNotes = writableDatabase.delete(NotesDatabaseHelper.TABLE.ACCOUNT, "_id=" + j + parseSelection(str), strArr);
                iDelete = iDeleteNotes;
            } else {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
            Logger.INSTANCE.d(TAG, "URI_NOTE_ITEM deleteNotes count:" + iDelete);
            if (iDelete > 0) {
                if (z) {
                    this.mNotifier.addUri(new Pair<>(Notes.Note.CONTENT_URI, 0));
                }
                this.mNotifier.addUri(new Pair<>(uri, 0));
                if (!booleanQueryParameter) {
                    if (!arrayList.isEmpty()) {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it.next().longValue()), 16));
                        }
                    } else {
                        this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, j), 16));
                    }
                }
                this.mNotifier.notifyChange(this.mResolver);
                requestSync();
            }
            return iDelete;
        }
        z = false;
        Logger.INSTANCE.d(TAG, "URI_NOTE_ITEM deleteNotes count:" + iDelete);
        if (iDelete > 0) {
            if (z) {
                this.mNotifier.addUri(new Pair<>(Notes.Note.CONTENT_URI, 0));
            }
            this.mNotifier.addUri(new Pair<>(uri, 0));
            if (!booleanQueryParameter) {
                if (!arrayList.isEmpty()) {
                    it = arrayList.iterator();
                    while (it.hasNext()) {
                        this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it.next().longValue()), 16));
                    }
                } else {
                    this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, j), 16));
                }
            }
            this.mNotifier.notifyChange(this.mResolver);
            requestSync();
        }
        return iDelete;
    }

    /* JADX WARN: Code duplicated, block: B:62:0x024c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:63:0x024e  */
    /* JADX WARN: Code duplicated, block: B:65:0x025c  */
    /* JADX WARN: Code duplicated, block: B:67:0x0262  */
    /* JADX WARN: Code duplicated, block: B:70:0x026c A[LOOP:0: B:68:0x0266->B:70:0x026c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:71:0x028b  */
    /* JADX WARN: Code duplicated, block: B:73:0x028f  */
    /* JADX WARN: Code duplicated, block: B:74:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:76:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:79:0x02b6 A[LOOP:1: B:77:0x02b0->B:79:0x02b6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:83:0x02f8  */
    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) throws Throwable {
        long j;
        int iUpdate;
        long j2;
        boolean z;
        boolean z2;
        Iterator<Long> it;
        Iterator<Long> it2;
        long j3;
        int i;
        Logger.INSTANCE.d(TAG, "DbOP update -selection:" + str);
        if (!enforceNewPermission(false, uri)) {
            Logger.INSTANCE.d(TAG, "update: no permission");
            return 0;
        }
        int iMatch = mMatcher.match(uri);
        if (iMatch == 18) {
            return this.mPrefHelper.update(uri, contentValues);
        }
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        boolean booleanQueryParameter = uri.getBooleanQueryParameter(Notes.CALLER_IS_SYNCADAPTER, false);
        ArrayList<Long> arrayList = new ArrayList<>();
        ArrayList<Long> arrayList2 = new ArrayList<>();
        Logger.INSTANCE.d(TAG, "update matchCode = " + iMatch);
        int i2 = 4;
        if (iMatch != 1) {
            j = -2;
            if (iMatch != 2) {
                if (iMatch == 3) {
                    iUpdate = writableDatabase.update("data", contentValues, str, strArr);
                    j2 = -2;
                } else if (iMatch != 4) {
                    if (iMatch == 9) {
                        iUpdate = writableDatabase.update(NotesDatabaseHelper.TABLE.ACCOUNT, contentValues, str, strArr);
                        j2 = -2;
                    } else if (iMatch != 10) {
                        if (iMatch == 13) {
                            i2 = 4;
                            verifyNoteValues(contentValues);
                            updateSystemFolderTimeIfNeed(contentValues);
                            long j4 = Long.parseLong(uri.getLastPathSegment());
                            String str2 = "_id=" + j4 + parseSelection(str);
                            HashSet<Long> updatedFolderIds = getUpdatedFolderIds(writableDatabase, contentValues, str2, strArr);
                            j3 = j4;
                            iUpdate = updateNoteAtomic(writableDatabase, j3, contentValues, str2, strArr);
                            if (iUpdate > 0) {
                                updateFolder(writableDatabase, updatedFolderIds);
                            }
                        } else if (iMatch == 17) {
                            String queryParameter = uri.getQueryParameter(EXPECTED_COUNT);
                            if (TextUtils.isEmpty(queryParameter)) {
                                i = 0;
                            } else {
                                try {
                                    i = Integer.parseInt(queryParameter);
                                } catch (NumberFormatException e) {
                                    Logger.INSTANCE.e(TAG, "Fail to parse expectedCount: " + queryParameter + ",exception:" + e);
                                    i = 0;
                                }
                            }
                            resolveId(str, arrayList);
                            iUpdate = restore(writableDatabase, contentValues, str, strArr, i);
                        } else if (iMatch == 19) {
                            verifyNoteValues(contentValues);
                            updateSystemFolderTimeIfNeed(contentValues);
                            i2 = 4;
                            long j5 = Long.parseLong(uri.getLastPathSegment());
                            String str3 = "_id=" + j5 + parseSelection(str);
                            HashSet<Long> updatedFolderIds2 = getUpdatedFolderIds(writableDatabase, contentValues, str3, strArr);
                            j3 = j5;
                            iUpdate = updateNoteMultiAtomic(writableDatabase, j3, contentValues, str3, strArr);
                            if (iUpdate > 0) {
                                updateFolder(writableDatabase, updatedFolderIds2);
                            }
                        } else {
                            throw new IllegalArgumentException("Unknown URI " + uri);
                        }
                        arrayList = arrayList;
                        z = false;
                        z2 = false;
                        long j6 = j3;
                        booleanQueryParameter = booleanQueryParameter;
                        j2 = j6;
                    } else {
                        j2 = Long.parseLong(uri.getPathSegments().get(1));
                        iUpdate = writableDatabase.update(NotesDatabaseHelper.TABLE.ACCOUNT, contentValues, "_id=" + j2 + parseSelection(str), strArr);
                    }
                    z = false;
                    z2 = true;
                } else {
                    j2 = Long.parseLong(uri.getPathSegments().get(1));
                    iUpdate = writableDatabase.update("data", contentValues, "_id=" + j2 + parseSelection(str), strArr);
                }
                z = true;
                z2 = false;
            } else {
                booleanQueryParameter = booleanQueryParameter;
                i2 = 4;
                arrayList = arrayList;
                verifyNoteValues(contentValues);
                updateSystemFolderTimeIfNeed(contentValues);
                insertPlainTextValue(contentValues);
                j2 = Long.parseLong(uri.getPathSegments().get(1));
                String str4 = "_id=" + j2 + parseSelection(str);
                HashSet<Long> updatedFolderIds3 = getUpdatedFolderIds(writableDatabase, contentValues, str4, strArr);
                int iUpdate2 = writableDatabase.update("note", contentValues, str4, strArr);
                printCommonUpdate("URI_NOTE_ITEM", contentValues, str4, strArr);
                if (iUpdate2 > 0) {
                    updateFolder(writableDatabase, updatedFolderIds3);
                }
                iUpdate = iUpdate2;
                z = false;
                z2 = false;
            }
            if (iUpdate > 0) {
                if (z) {
                    this.mNotifier.addUri(new Pair<>(Notes.Note.CONTENT_URI, 0));
                }
                if (!booleanQueryParameter) {
                    if (!arrayList.isEmpty()) {
                        it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it2.next().longValue()), Integer.valueOf(i2)));
                        }
                    } else if (j2 != j) {
                        this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, j2), 8));
                    }
                } else if (!arrayList2.isEmpty()) {
                    it = arrayList2.iterator();
                    while (it.hasNext()) {
                        this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it.next().longValue()), 16));
                    }
                }
                this.mNotifier.addUri(new Pair<>(uri, 0));
                this.mNotifier.addUri(new Pair<>(WIDGET_CONTENT_URI, 0));
                this.mNotifier.notifyChange(this.mResolver);
                if (booleanQueryParameter || !z2) {
                    requestSync();
                }
            }
            return iUpdate;
        }
        j = -2;
        verifyNoteValues(contentValues);
        updateSystemFolderTimeIfNeed(contentValues);
        insertPlainTextValue(contentValues);
        HashSet<Long> updatedFolderIds4 = getUpdatedFolderIds(writableDatabase, contentValues, str, strArr);
        int iUpdate3 = writableDatabase.update("note", contentValues, str, strArr);
        printCommonUpdate("URI_NOTE", contentValues, str, strArr);
        resolveId(str, arrayList2);
        if (iUpdate3 > 0) {
            updateFolder(writableDatabase, updatedFolderIds4);
        }
        iUpdate = iUpdate3;
        j2 = j;
        z = false;
        z2 = false;
        if (iUpdate > 0) {
            if (z) {
                this.mNotifier.addUri(new Pair<>(Notes.Note.CONTENT_URI, 0));
            }
            if (!booleanQueryParameter) {
                if (!arrayList.isEmpty()) {
                    it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it2.next().longValue()), Integer.valueOf(i2)));
                    }
                } else if (j2 != j) {
                    this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, j2), 8));
                }
            } else if (!arrayList2.isEmpty()) {
                it = arrayList2.iterator();
                while (it.hasNext()) {
                    this.mNotifier.addUri(new Pair<>(ContentUris.withAppendedId(AI_QUERY_URI, it.next().longValue()), 16));
                }
            }
            this.mNotifier.addUri(new Pair<>(uri, 0));
            this.mNotifier.addUri(new Pair<>(WIDGET_CONTENT_URI, 0));
            this.mNotifier.notifyChange(this.mResolver);
            if (booleanQueryParameter) {
                requestSync();
            } else {
                requestSync();
            }
        }
        return iUpdate;
    }

    private void verifyNoteValues(ContentValues contentValues) {
        if (contentValues.containsKey("subject") && contentValues.getAsString("subject") == null) {
            throw new IllegalArgumentException("Folder's subject can't be null");
        }
    }

    private void updateSystemFolderTimeIfNeed(ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        Long asLong = contentValues.getAsLong("parent_id");
        if (asLong == null || -2 != asLong.longValue()) {
            return;
        }
        ContentValues contentValues2 = new ContentValues();
        long jCurrentTimeMillis = System.currentTimeMillis();
        contentValues2.put("created_date", Long.valueOf(jCurrentTimeMillis));
        contentValues2.put("modified_date", Long.valueOf(jCurrentTimeMillis));
        writableDatabase.update("note", contentValues2, String.format(Locale.US, "%s=%d AND %s=%d", "_id", asLong, Notes.Note.NOTES_COUNT, 0), null);
    }

    private HashSet<Long> getUpdatedFolderIds(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr) {
        HashSet<Long> hashSet = new HashSet<>();
        if (contentValues != null && contentValues.containsKey("parent_id")) {
            hashSet.add(contentValues.getAsLong("parent_id"));
        }
        Logger.INSTANCE.d(TAG, "getUpdatedFolderIds,cursor open with source:note");
        Cursor cursorQuery = sQLiteDatabase.query(true, "note", new String[]{"parent_id"}, str, strArr, null, null, null, null);
        if (cursorQuery == null) {
            return hashSet;
        }
        while (cursorQuery.moveToNext()) {
            try {
                long j = cursorQuery.getLong(0);
                if (j > 0) {
                    hashSet.add(Long.valueOf(j));
                }
            } finally {
                cursorQuery.close();
                Logger.INSTANCE.d(TAG, "getUpdatedFolderIds,cursor close with source:note");
            }
        }
        return hashSet;
    }

    private String parseSelection(String str) {
        return !TextUtils.isEmpty(str) ? " AND (" + str + CoreConstants.RIGHT_PARENTHESIS_CHAR : "";
    }

    private List<String> getStringListFromContent(ContentValues contentValues, String str) {
        ArrayList arrayList = new ArrayList();
        if (!contentValues.containsKey(str)) {
            return arrayList;
        }
        String asString = contentValues.getAsString(str);
        if (asString == null || asString.trim().isEmpty()) {
            asString = _UrlKt.PATH_SEGMENT_ENCODE_SET_URI;
        }
        return (List) new Gson().fromJson(asString, new TypeToken<List<String>>() { // from class: com.miui.notes.provider.NotesProvider.1
        }.getType());
    }

    private List<String> getCombinImageList(List<String> list, List<String> list2, List<String> list3) {
        if (list == null) {
            list = new ArrayList<>();
        }
        ArrayList arrayList = new ArrayList(list);
        Iterator<String> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.remove(it.next());
        }
        Iterator<String> it2 = list3.iterator();
        while (it2.hasNext()) {
            arrayList.remove(it2.next());
        }
        arrayList.addAll(list3);
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x009c  */
    /* JADX WARN: Code duplicated, block: B:43:0x01c7  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5, types: [java.lang.String, java.lang.String[]] */
    private int updateNoteMultiAtomic(SQLiteDatabase sQLiteDatabase, long j, ContentValues contentValues, String str, String[] strArr) throws Throwable {
        List<String> list;
        long j2;
        ContentValues contentValues2;
        String str2;
        String str3;
        String str4;
        ContentValues contentValues3;
        ?? r11;
        Cursor cursorQuery;
        SQLiteDatabase sQLiteDatabase2;
        List list2;
        List list3;
        List list4;
        SQLiteDatabase sQLiteDatabase3 = sQLiteDatabase;
        ContentValues contentValuesEncryptSnippet = contentValues;
        sQLiteDatabase3.beginTransaction();
        try {
            int iCheckIfParentIdExist = checkIfParentIdExist(sQLiteDatabase3, contentValuesEncryptSnippet);
            if (iCheckIfParentIdExist != 0) {
                sQLiteDatabase3.endTransaction();
                return iCheckIfParentIdExist;
            }
            Cursor cursorQuery2 = sQLiteDatabase3.query("note", new String[]{Notes.Note.IMAGES}, "type=0 AND _id=" + j, null, null, null, null);
            Logger.INSTANCE.d(TAG, "updateNoteMultiAtomic,cursor open with source:note");
            if (cursorQuery2 == null || cursorQuery2.getCount() <= 0) {
                list = null;
            } else {
                cursorQuery2.moveToPosition(0);
                String string = cursorQuery2.getString(0);
                if (TextUtils.isEmpty(string)) {
                    list = null;
                } else {
                    list = (List) new Gson().fromJson(string, new TypeToken<List<String>>() { // from class: com.miui.notes.provider.NotesProvider.2
                    }.getType());
                }
            }
            if (cursorQuery2 != null) {
                cursorQuery2.close();
                Logger.INSTANCE.d(TAG, "updateNoteMultiAtomic,cursor close with source:note");
            }
            insertPlainTextValue(contentValuesEncryptSnippet);
            if (isPrivateNote(contentValuesEncryptSnippet)) {
                contentValuesEncryptSnippet = encryptSnippet(contentValuesEncryptSnippet);
            }
            ContentValues contentValues4 = contentValuesEncryptSnippet;
            List<String> stringListFromContent = getStringListFromContent(contentValues4, Notes.Note.IMAGES);
            List<String> stringListFromContent2 = getStringListFromContent(contentValues4, Notes.Note.DELETE_IMAGES);
            final List<String> combinImageList = getCombinImageList(list, stringListFromContent2, stringListFromContent);
            Objects.requireNonNull(combinImageList);
            stringListFromContent2.removeIf(new Predicate() { // from class: com.miui.notes.provider.NotesProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return combinImageList.contains((String) obj);
                }
            });
            contentValues4.put(Notes.Note.IMAGES, new Gson().toJson(combinImageList));
            contentValues4.remove(Notes.Note.DELETE_IMAGES);
            int iUpdate = sQLiteDatabase3.update("note", contentValues4, str, strArr);
            if (iUpdate > 0) {
                ArrayList arrayList = new ArrayList();
                boolean zContainsKey = contentValues4.containsKey("snippet");
                String str5 = Notes.TextData.CONTENT_ITEM_TYPE;
                String str6 = Notes.Data.MIME_TYPE;
                List<String> list5 = stringListFromContent2;
                String str7 = "content";
                if (zContainsKey) {
                    String asString = contentValues4.getAsString("snippet");
                    ContentValues contentValues5 = new ContentValues();
                    if (TextUtils.isEmpty(asString)) {
                        asString = NoteApp.getInstance().getString(R.string.pad_note_list_not_text_stub);
                    }
                    contentValues5.put("content", asString);
                    contentValues5.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
                    arrayList.add(contentValues5);
                }
                if (contentValues4.containsKey("thumbnail")) {
                    try {
                        String asString2 = contentValues4.getAsString("thumbnail");
                        if (!TextUtils.isEmpty(asString2)) {
                            ContentValues contentValues6 = new ContentValues();
                            contentValues6.put("content", asString2);
                            contentValues6.put(Notes.Data.MIME_TYPE, "image/jpeg");
                            contentValues6.put(Notes.Data.DATA_NEED_PUSH, (Integer) 1);
                            contentValues6.put(Notes.Data.DATA_NEED_UPDATE, (Integer) 0);
                            arrayList.add(contentValues6);
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLiteDatabase3 = sQLiteDatabase;
                        sQLiteDatabase3.endTransaction();
                        throw th;
                    }
                }
                if (contentValues4.containsKey("ent_name")) {
                    String asString3 = contentValues4.getAsString("ent_name");
                    if (!TextUtils.isEmpty(asString3)) {
                        ContentValues contentValues7 = new ContentValues();
                        contentValues7.put(Notes.Data.MIME_TYPE, "ent/file");
                        contentValues7.put("content", asString3);
                        contentValues7.put(Notes.Data.DATA_NEED_PUSH, (Integer) 1);
                        contentValues7.put(Notes.Data.DATA_NEED_UPDATE, (Integer) 0);
                        arrayList.add(contentValues7);
                        String iinkFileName = HandWriteDataUtils.getIinkFileName(asString3);
                        if (TextUtils.isEmpty(iinkFileName)) {
                            Logger.INSTANCE.w(TAG, "saveIinkFile: failed to generate iink fileName from entName=" + asString3);
                        } else {
                            ContentValues contentValues8 = new ContentValues();
                            contentValues8.put(Notes.Data.MIME_TYPE, HandWriteDataUtils.IINK_FILE_TYPE);
                            contentValues8.put("content", iinkFileName);
                            contentValues8.put(Notes.Data.DATA_NEED_PUSH, (Integer) 1);
                            contentValues8.put(Notes.Data.DATA_NEED_UPDATE, (Integer) 0);
                            arrayList.add(contentValues8);
                        }
                    }
                }
                if (contentValues4.containsKey("recognized_file_names") && (list4 = (List) new Gson().fromJson(contentValues4.getAsString("recognized_file_names"), new TypeToken<List<String>>() { // from class: com.miui.notes.provider.NotesProvider.3
                }.getType())) != null) {
                    for (int i = 0; i < list4.size(); i++) {
                        if (!TextUtils.isEmpty((CharSequence) list4.get(i))) {
                            ContentValues contentValues9 = new ContentValues();
                            contentValues9.put("content", (String) list4.get(i));
                            contentValues9.put(Notes.Data.MIME_TYPE, "ent/file");
                            contentValues9.put(Notes.Data.DATA_NEED_PUSH, (Integer) 1);
                            contentValues9.put(Notes.Data.DATA_NEED_UPDATE, (Integer) 0);
                            arrayList.add(contentValues9);
                        }
                    }
                }
                if (contentValues4.containsKey(Notes.Note.IMAGES) && (list3 = (List) new Gson().fromJson(contentValues4.getAsString(Notes.Note.IMAGES), new TypeToken<List<String>>() { // from class: com.miui.notes.provider.NotesProvider.4
                }.getType())) != null) {
                    for (int i2 = 0; i2 < list3.size(); i2++) {
                        if (!TextUtils.isEmpty((CharSequence) list3.get(i2)) && (list == null || list.isEmpty() || !list.contains(list3.get(i2)))) {
                            ContentValues contentValues10 = new ContentValues();
                            contentValues10.put("content", (String) list3.get(i2));
                            contentValues10.put(Notes.Data.MIME_TYPE, "image/jpeg");
                            arrayList.add(contentValues10);
                        }
                    }
                }
                if (contentValues4.containsKey(Notes.Note.WEB_IMAGES) && (list2 = (List) new Gson().fromJson(contentValues4.getAsString(Notes.Note.WEB_IMAGES), new TypeToken<List<WebImage>>() { // from class: com.miui.notes.provider.NotesProvider.5
                }.getType())) != null) {
                    for (int i3 = 0; i3 < list2.size(); i3++) {
                        if (!TextUtils.isEmpty(((WebImage) list2.get(i3)).getImage()) && (list == null || list.isEmpty() || !list.contains(list2.get(i3)))) {
                            ContentValues contentValues11 = new ContentValues();
                            contentValues11.put("content", ((WebImage) list2.get(i3)).getImage());
                            contentValues11.put(Notes.Data.MIME_TYPE, "image/jpeg");
                            arrayList.add(contentValues11);
                        }
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues12 = (ContentValues) it.next();
                    contentValues12.put("note_id", Long.valueOf(j));
                    contentValues12.put(Notes.Data.DATA_CONTENT_TYPE, "multi_handwrite");
                    String asString4 = contentValues12.getAsString(str7);
                    if (str5.equals(contentValues12.getAsString(str6))) {
                        ContentValues contentValues13 = contentValues4;
                        str2 = str5;
                        str3 = str6;
                        cursorQuery = sQLiteDatabase.query("data", new String[]{"_id", str7, str6}, "note_id=" + j + " AND " + str6 + "='" + str5 + "'", null, null, null, null);
                        sQLiteDatabase2 = sQLiteDatabase;
                        str4 = str7;
                        contentValues3 = contentValues13;
                        r11 = 0;
                    } else {
                        ContentValues contentValues14 = contentValues4;
                        String str8 = str7;
                        str2 = str5;
                        str3 = str6;
                        String str9 = "note_id=" + j + " AND " + str8 + "='" + asString4 + "'";
                        SQLiteDatabase sQLiteDatabase4 = sQLiteDatabase;
                        str4 = str8;
                        contentValues3 = contentValues14;
                        r11 = 0;
                        cursorQuery = sQLiteDatabase4.query("data", new String[]{"_id", str8, str3}, str9, null, null, null, null);
                        sQLiteDatabase2 = sQLiteDatabase4;
                    }
                    Logger.INSTANCE.d(TAG, "updateNoteMultiAtomic--,cursor open with source:data");
                    if (cursorQuery == null || cursorQuery.getCount() <= 0) {
                        sQLiteDatabase2.insert("data", r11, contentValues12);
                    } else {
                        cursorQuery.moveToPosition(0);
                        sQLiteDatabase2.update("data", contentValues12, "_id=" + cursorQuery.getLong(0), r11);
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        Logger.INSTANCE.d(TAG, "updateNoteMultiAtomic--,cursor close with source:data");
                    }
                    it = it;
                    list5 = list5;
                    str5 = str2;
                    str6 = str3;
                    contentValues4 = contentValues3;
                    str7 = str4;
                }
                sQLiteDatabase3 = sQLiteDatabase;
                j2 = j;
                contentValues2 = contentValues4;
                String str10 = str7;
                String[] strArr2 = null;
                for (String str11 : list5) {
                    String str12 = str10;
                    if (sQLiteDatabase3.delete("data", "note_id=" + j2 + " AND " + str12 + "='" + str11 + "'", strArr2) > 0) {
                        try {
                            String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), str11);
                            if (!TextUtils.isEmpty(attachmentPath)) {
                                File file = new File(attachmentPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        } catch (Exception e) {
                            Logger.INSTANCE.d(TAG, "deleteImage fail e:" + e);
                        }
                    }
                    str10 = str12;
                    strArr2 = null;
                }
            } else {
                j2 = j;
                contentValues2 = contentValues4;
                iUpdate = iUpdate;
            }
            sQLiteDatabase3.setTransactionSuccessful();
            printAtomicUpdate("update multi page", j2, contentValues2);
            sQLiteDatabase3.endTransaction();
            return iUpdate;
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase3.endTransaction();
            throw th;
        }
    }

    private int updateNoteAtomic(SQLiteDatabase sQLiteDatabase, long j, ContentValues contentValues, String str, String[] strArr) throws Throwable {
        ContentValues contentValues2;
        ContentValues contentValues3;
        ContentValues contentValues4;
        String str2;
        ContentValues contentValues5;
        SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
        ContentValues contentValuesEncryptSnippet = contentValues;
        sQLiteDatabase2.beginTransaction();
        try {
            int iCheckIfParentIdExist = checkIfParentIdExist(sQLiteDatabase2, contentValuesEncryptSnippet);
            if (iCheckIfParentIdExist == 0) {
                ArrayList<ContentValues> arrayListRemoveDataValuesFromNoteValues = Notes.Utils.removeDataValuesFromNoteValues(contentValuesEncryptSnippet);
                insertPlainTextValue(contentValuesEncryptSnippet);
                boolean zIsPrivateNote = isPrivateNote(contentValuesEncryptSnippet);
                if (zIsPrivateNote) {
                    contentValuesEncryptSnippet = encryptSnippet(contentValuesEncryptSnippet);
                }
                int iUpdate = sQLiteDatabase2.update("note", contentValuesEncryptSnippet, str, strArr);
                if (iUpdate > 0) {
                    if (arrayListRemoveDataValuesFromNoteValues != null) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<ContentValues> it = arrayListRemoveDataValuesFromNoteValues.iterator();
                        ContentValues next = null;
                        ContentValues contentValues6 = null;
                        while (true) {
                            Iterator<ContentValues> it2 = it;
                            contentValues3 = next;
                            if (!it.hasNext()) {
                                break;
                            }
                            try {
                                next = it2.next();
                                ContentValues contentValues7 = contentValuesEncryptSnippet;
                                boolean z = zIsPrivateNote;
                                next.put("note_id", Long.valueOf(j));
                                String asString = next.getAsString(Notes.Data.MIME_TYPE);
                                if (!Notes.TextData.CONTENT_ITEM_TYPE.equals(asString)) {
                                    if (Notes.CallData.CONTENT_ITEM_TYPE.equals(asString)) {
                                        contentValues6 = next;
                                    } else {
                                        arrayList.add(next);
                                    }
                                    next = contentValues3;
                                } else if (z) {
                                    encryptContent(next, true);
                                }
                                it = it2;
                                contentValuesEncryptSnippet = contentValues7;
                                zIsPrivateNote = z;
                            } catch (Throwable th) {
                                th = th;
                                sQLiteDatabase2 = sQLiteDatabase;
                            }
                            th = th;
                            sQLiteDatabase2 = sQLiteDatabase;
                            sQLiteDatabase2.endTransaction();
                            throw th;
                        }
                        ContentValues contentValues8 = contentValues6;
                        ContentValues contentValues9 = contentValues3;
                        ArrayList arrayList2 = arrayList;
                        String str3 = "file_id";
                        contentValues2 = contentValuesEncryptSnippet;
                        int i = 0;
                        sQLiteDatabase2 = sQLiteDatabase;
                        Cursor cursorQuery = sQLiteDatabase2.query("data", new String[]{"_id", "content", Notes.Data.MIME_TYPE, "file_id", Notes.Data.DATA_CONTENT_TYPE}, "note_id=" + j, null, null, null, null);
                        Logger.INSTANCE.d(TAG, "updateNoteAtomic,cursor open with source:data");
                        if (cursorQuery != null) {
                            while (cursorQuery.moveToNext()) {
                                try {
                                    long j2 = cursorQuery.getLong(i);
                                    String string = cursorQuery.getString(2);
                                    if (Notes.TextData.CONTENT_ITEM_TYPE.equals(string)) {
                                        if (contentValues9 != null && contentValues9.size() > 0) {
                                            Logger.INSTANCE.e(TAG, "textData = null or textData.size() == 0");
                                            sQLiteDatabase2.update("data", contentValues9, "_id=" + j2, null);
                                        }
                                        arrayListRemoveDataValuesFromNoteValues.remove(contentValues9);
                                    } else {
                                        if (Notes.CallData.CONTENT_ITEM_TYPE.equals(string)) {
                                            if (contentValues8 != null && contentValues8.size() > 0) {
                                                Logger.INSTANCE.e(TAG, "callData = null or callData.size() == 0");
                                                sQLiteDatabase2.update("data", contentValues8, "_id=" + j2, null);
                                            }
                                            arrayListRemoveDataValuesFromNoteValues.remove(contentValues8);
                                        } else {
                                            int i2 = 1;
                                            String string2 = cursorQuery.getString(1);
                                            Iterator it3 = arrayList2.iterator();
                                            while (true) {
                                                if (!it3.hasNext()) {
                                                    contentValues4 = contentValues9;
                                                    str2 = str3;
                                                    contentValues5 = null;
                                                    break;
                                                }
                                                int i3 = i2;
                                                contentValues5 = (ContentValues) it3.next();
                                                if (string2.equals(contentValues5.getAsString("content"))) {
                                                    if (!TextUtils.equals(cursorQuery.getString(4), "multi_handwrite")) {
                                                        contentValues4 = contentValues9;
                                                        str2 = str3;
                                                        break;
                                                    }
                                                    String string3 = cursorQuery.getString(3);
                                                    str2 = str3;
                                                    if (!contentValues5.containsKey(str2)) {
                                                        contentValues4 = contentValues9;
                                                        break;
                                                    }
                                                    contentValues4 = contentValues9;
                                                    String asString2 = contentValues5.getAsString(str2);
                                                    if (!TextUtils.isEmpty(asString2) && !TextUtils.equals(string3, asString2)) {
                                                        contentValues5.put(Notes.Data.DATA_NEED_UPDATE, Integer.valueOf(i3));
                                                        break;
                                                    }
                                                    break;
                                                    break;
                                                }
                                                i2 = i3;
                                            }
                                            if (contentValues5 == null) {
                                                sQLiteDatabase2.delete("data", "_id=" + j2, null);
                                            } else {
                                                arrayList2 = arrayList2;
                                                arrayList2.remove(contentValues5);
                                                if (contentValues5 != null && contentValues5.size() > 0) {
                                                    Logger.INSTANCE.e(TAG, "imageData = null or imageData.size() == 0");
                                                    sQLiteDatabase2.update("data", contentValues5, "_id=" + j2, null);
                                                }
                                                arrayListRemoveDataValuesFromNoteValues.remove(contentValues5);
                                            }
                                        }
                                        contentValues9 = contentValues4;
                                        str3 = str2;
                                        i = 0;
                                    }
                                    contentValues4 = contentValues9;
                                    str2 = str3;
                                    contentValues9 = contentValues4;
                                    str3 = str2;
                                    i = 0;
                                } catch (Throwable th2) {
                                    cursorQuery.close();
                                    Logger.INSTANCE.d(TAG, "updateNoteAtomic,cursor close with source:data");
                                    throw th2;
                                }
                            }
                            cursorQuery.close();
                            Logger.INSTANCE.d(TAG, "updateNoteAtomic,cursor close with source:data");
                        }
                        Iterator<ContentValues> it4 = arrayListRemoveDataValuesFromNoteValues.iterator();
                        while (it4.hasNext()) {
                            sQLiteDatabase2.insert("data", null, it4.next());
                        }
                    } else {
                        contentValues2 = contentValuesEncryptSnippet;
                    }
                    sQLiteDatabase2.setTransactionSuccessful();
                    printAtomicUpdate("update", j, contentValues2);
                }
                sQLiteDatabase2.endTransaction();
                return iUpdate;
            }
            sQLiteDatabase2.endTransaction();
            return iCheckIfParentIdExist;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private ContentValues encryptContent(ContentValues contentValues, boolean z) {
        if (!z) {
            contentValues = new ContentValues(contentValues);
        }
        if (!contentValues.containsKey("content")) {
            return contentValues;
        }
        try {
            contentValues.put("content", EncryptUtil.encrypt(getContext(), contentValues.getAsString("content")));
            return contentValues;
        } catch (EncryptFailedException e) {
            throw new RuntimeException("can not encrypt notes", e);
        }
    }

    private ContentValues encryptContent(ContentValues contentValues) {
        return encryptContent(contentValues, false);
    }

    private ContentValues encryptSnippet(ContentValues contentValues, boolean z) {
        if (!z) {
            contentValues = new ContentValues(contentValues);
        }
        if (contentValues.containsKey("snippet")) {
            try {
                contentValues.put("snippet", EncryptUtil.encrypt(getContext(), contentValues.getAsString("snippet")));
            } catch (EncryptFailedException e) {
                throw new RuntimeException("can not encrypt notes", e);
            }
        }
        if (!contentValues.containsKey("plain_text")) {
            return contentValues;
        }
        try {
            contentValues.put("plain_text", EncryptUtil.encrypt(getContext(), contentValues.getAsString("plain_text")));
            return contentValues;
        } catch (EncryptFailedException e2) {
            throw new RuntimeException("can not encrypt notes", e2);
        }
    }

    private ContentValues encryptSnippet(ContentValues contentValues) {
        return encryptSnippet(contentValues, false);
    }

    private void printAtomicUpdate(String str, long j, ContentValues contentValues) {
        Long asLong = contentValues.getAsLong("parent_id");
        Long asLong2 = contentValues.getAsLong("origin_parent_id");
        Long asLong3 = contentValues.getAsLong("sync_id");
        String asString = contentValues.getAsString("sync_tag");
        Long asLong4 = contentValues.getAsLong("created_date");
        Long asLong5 = contentValues.getAsLong("modified_date");
        Integer asInteger = contentValues.getAsInteger(Notes.Note.LOCAL_MODIFIED);
        String asString2 = contentValues.getAsString("snippet");
        Logger.INSTANCE.i(TAG, "Success to " + str + " note atomic, uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", id=" + j + ", parentId=" + asLong + ", originParentId=" + asLong2 + ", syncId=" + asLong3 + ", syncTag=" + asString + ", createdDate=" + asLong4 + ", modifiedDate=" + asLong5 + ", localModified=" + asInteger + ", size=" + (asString2 == null ? "null" : String.valueOf(asString2.length())));
    }

    private void printCommonUpdate(String str, ContentValues contentValues, String str2, String[] strArr) {
        if (contentValues.containsKey(Notes.Note.LOCAL_MODIFIED)) {
            Logger.INSTANCE.i(TAG, "update " + str + ", uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", localModified=" + contentValues.get(Notes.Note.LOCAL_MODIFIED).toString() + ", selection=" + str2 + ", selectionArgs=" + Arrays.toString(strArr));
        }
        if (contentValues.containsKey("snippet") && TextUtils.isEmpty(contentValues.get("snippet").toString())) {
            Logger.INSTANCE.i(TAG, "update " + str + ", uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", snippet is update to ''!, selection=" + str2 + ", selectionArgs=" + Arrays.toString(strArr));
        }
    }

    private void printCommonDelete(String str, String[] strArr, boolean z, int i) {
        if (i > 0) {
            Logger.INSTANCE.i(TAG, "delete note, uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ", selection=" + str + ", selectionArgs=" + Arrays.toString(strArr) + ", force=" + z + ",counter=" + i);
        }
    }

    private long insertNoteAtomic(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        sQLiteDatabase.beginTransaction();
        try {
            int iCheckIfParentIdExist = checkIfParentIdExist(sQLiteDatabase, contentValues);
            if (iCheckIfParentIdExist == 0) {
                ArrayList<ContentValues> arrayListRemoveDataValuesFromNoteValues = Notes.Utils.removeDataValuesFromNoteValues(contentValues);
                insertPlainTextValue(contentValues);
                boolean zIsPrivateNote = isPrivateNote(contentValues);
                if (zIsPrivateNote) {
                    contentValues = encryptSnippet(contentValues);
                }
                long jInsert = sQLiteDatabase.insert("note", null, contentValues);
                if (jInsert > 0) {
                    if (arrayListRemoveDataValuesFromNoteValues != null) {
                        for (ContentValues contentValuesEncryptContent : arrayListRemoveDataValuesFromNoteValues) {
                            if (zIsPrivateNote && Notes.TextData.CONTENT_ITEM_TYPE.equals(contentValuesEncryptContent.getAsString(Notes.Data.MIME_TYPE))) {
                                contentValuesEncryptContent = encryptContent(contentValuesEncryptContent);
                            }
                            contentValuesEncryptContent.put("note_id", Long.valueOf(jInsert));
                            sQLiteDatabase.insert("data", null, contentValuesEncryptContent);
                        }
                    }
                    sQLiteDatabase.setTransactionSuccessful();
                }
                printAtomicUpdate("insert", jInsert, contentValues);
                return jInsert;
            }
            return iCheckIfParentIdExist;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private boolean isPrivateNote(ContentValues contentValues) {
        Long asLong = contentValues.getAsLong("parent_id");
        long jLongValue = asLong != null ? asLong.longValue() : -4098L;
        Long asLong2 = contentValues.getAsLong("origin_parent_id");
        return NoteModel.isHidden(jLongValue, asLong2 != null ? asLong2.longValue() : -4098L);
    }

    private int checkIfParentIdExist(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        if (contentValues.containsKey("parent_id")) {
            Long asLong = contentValues.getAsLong("parent_id");
            if (asLong.longValue() > 0) {
                Cursor cursorQuery = sQLiteDatabase.query("note", new String[]{"_id"}, "_id=" + asLong + " AND type=1", null, null, null, null);
                Logger.INSTANCE.d(TAG, "checkIfParentIdExist,cursor open with source:note");
                int i = -100;
                if (cursorQuery != null) {
                    try {
                        int i2 = cursorQuery.getCount() <= 0 ? -100 : 0;
                        cursorQuery.close();
                        Logger.INSTANCE.d(TAG, "checkIfParentIdExist,cursor close with source:note");
                        i = i2;
                    } catch (Throwable th) {
                        cursorQuery.close();
                        Logger.INSTANCE.d(TAG, "checkIfParentIdExist,cursor close with source:note");
                        throw th;
                    }
                }
                Logger.INSTANCE.e(TAG, "Cann't find parent_id = " + asLong);
                return i;
            }
        }
        return 0;
    }

    private int restore(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr, int i) {
        if (contentValues == null) {
            contentValues = new ContentValues();
        }
        contentValues.put("parent_id=origin_parent_id, gtask_id", "");
        contentValues.put("widget_id", (Integer) 0);
        contentValues.put("widget_type", (Integer) (-1));
        int iUpdate = sQLiteDatabase.update("note", contentValues, str, strArr);
        contentValues.clear();
        contentValues.put("parent_id", (Integer) 0);
        sQLiteDatabase.update("note", contentValues, "type=1 AND parent_id=-3 AND notes_count>0", null);
        return iUpdate;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int iMatch = mMatcher.match(uri);
        if (iMatch == 4 || iMatch == 8) {
            Cursor cursorQuery = this.mHelper.getReadableDatabase().query("data", new String[]{Notes.Data.MIME_TYPE}, "_id=?", new String[]{Long.toString(Long.parseLong(uri.getLastPathSegment()))}, null, null, null);
            Logger.INSTANCE.d(TAG, "getType,cursor open with source:data");
            if (cursorQuery != null) {
                try {
                    return cursorQuery.moveToNext() ? cursorQuery.getString(0) : null;
                } finally {
                    cursorQuery.close();
                    Logger.INSTANCE.d(TAG, "getType,cursor close with source:data");
                }
            }
        } else if (iMatch == 7) {
            return "*/*";
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        int iMatch = mMatcher.match(uri);
        if (iMatch == 4 || iMatch == 8) {
            if (!enforceNewPermission(false, uri)) {
                Logger.INSTANCE.d(TAG, "openFile: no permission");
                return null;
            }
            String strDecode = Uri.decode(uri.toString());
            if (strDecode != null && strDecode.contains(CallerDataConverter.DEFAULT_RANGE_DELIMITER)) {
                throw new IllegalArgumentException();
            }
            String filePath = getFilePath(uri);
            Logger.INSTANCE.d(TAG, "openFile path:" + filePath);
            if (filePath != null) {
                File file = new File(filePath);
                if (AnimatedProperty.PROPERTY_NAME_W.equals(str)) {
                    ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 738197504);
                    file.setReadable(true, false);
                    return parcelFileDescriptorOpen;
                }
                if (file.exists() && file.isFile()) {
                    return ParcelFileDescriptor.open(file, DataSpace.RANGE_LIMITED);
                }
            }
        } else if (iMatch == 7) {
            return getTempStoreFd();
        }
        return super.openFile(uri, str);
    }

    private String getFilePath(Uri uri) {
        Cursor cursorQuery = this.mHelper.getReadableDatabase().query("data", new String[]{"content"}, "_id=?", new String[]{Long.toString(Long.parseLong(uri.getLastPathSegment()))}, null, null, null);
        Logger.INSTANCE.d(TAG, "getFilePath,cursor open with source:data");
        if (cursorQuery == null) {
            return null;
        }
        try {
            return cursorQuery.moveToNext() ? AttachmentUtils.getAttachmentPath(getContext(), cursorQuery.getString(0)) : null;
        } finally {
            cursorQuery.close();
            Logger.INSTANCE.d(TAG, "getFilePath,cursor open with source:data");
        }
    }

    private void updateFolder(SQLiteDatabase sQLiteDatabase, long j) {
        updateFolder(sQLiteDatabase, String.format(Locale.US, "%s=%d", "_id", Long.valueOf(j)));
    }

    private void updateFolder(SQLiteDatabase sQLiteDatabase, HashSet<Long> hashSet) {
        updateFolder(sQLiteDatabase, String.format("%s IN (%s)", "_id", TextUtils.join(",", hashSet)));
    }

    private void updateFolder(SQLiteDatabase sQLiteDatabase, String str) {
        StringBuilder sb = new StringBuilder("UPDATE note SET bg_color_id=(  SELECT t.bg_color_id   FROM note AS t   WHERE t.parent_id=note._id   ORDER BY t.modified_date DESC LIMIT 1) WHERE type!=0  AND notes_count>0  AND (");
        sb.append(str).append(")");
        sQLiteDatabase.execSQL(sb.toString());
    }

    private ParcelFileDescriptor getTempStoreFd() {
        File scrapFile = getScrapFile(getContext());
        if (scrapFile == null) {
            return null;
        }
        try {
            return ParcelFileDescriptor.open(scrapFile, 939524096);
        } catch (FileNotFoundException e) {
            Logger.INSTANCE.e(TAG, "Fail to open " + scrapFile.getPath() + ",exception:" + e);
            return null;
        }
    }

    private boolean isImageType(String str) {
        return str != null && str.startsWith("image/");
    }

    private boolean enforceNewPermission(boolean z, Uri uri) {
        Logger.INSTANCE.d(TAG, "enforceNewPermission");
        if (Binder.getCallingUid() == Process.myUid()) {
            return true;
        }
        String callingPackage = getCallingPackage();
        Logger.INSTANCE.d(TAG, "callingPackageName :" + callingPackage);
        if (!TextUtils.isEmpty(callingPackage)) {
            if (RomUtils.isInternationalBuild()) {
                enforcePermission(uri, callingPackage);
                return true;
            }
            return handleDomesticBuildPermission(z, uri, callingPackage);
        }
        Logger.INSTANCE.d(TAG, "this is callingPackageName is null");
        return false;
    }

    private boolean handleDomesticBuildPermission(boolean z, Uri uri, String str) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (!isSignedWithPlatformKey(packageManager.getApplicationInfo(str, 64)) && !SignatureValidator.validateSignature(context, str)) {
                Logger.INSTANCE.d(TAG, "this is callingPackageName is notSignedWithPlatformKey");
                return false;
            }
            PermissionInfo permissionInfo = getPermissionInfo(context);
            if (z && permissionInfo != null) {
                if (new ArrayList(Arrays.asList(packageManager.getPackageInfo(str, 4096).requestedPermissions)).contains(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1)) {
                    return NoteAppStat.APP_PKG.search.equals(str) || context.checkCallingPermission(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1) == 0;
                }
                enforcePermission(uri, str);
                return true;
            }
            enforcePermission(uri, str);
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "exception : " + e);
            return false;
        }
    }

    private boolean isSignedWithPlatformKey(ApplicationInfo applicationInfo) {
        try {
            return ((Boolean) ApplicationInfo.class.getDeclaredMethod("isSignedWithPlatformKey", new Class[0]).invoke(applicationInfo, new Object[0])).booleanValue();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Reflect exception!,exception:" + e);
            return false;
        }
    }

    private PermissionInfo getPermissionInfo(Context context) {
        try {
            return context.getPackageManager().getPermissionInfo(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1, 0);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getPermissionInfo,exception:" + e);
            return null;
        }
    }

    private void enforcePermission(Uri uri, String str) {
        Logger.INSTANCE.d(TAG, "enforcePermission");
        Binder.clearCallingIdentity();
        if ("com.xiaomi.smarthome".equals(str) || "com.android.itransfer".equals(str) || "com.miui.cloudservice".equals(str) || TapShareConstant.GALLERY_PACKAGE_NAME.equals(str) || NoteAppStat.APP_PKG.desktop.equals(str) || "com.miui.personalassistant".equals(str) || NoteAppStat.APP_PKG.search.equals(str) || "com.xiaomi.aiasst.vision".equals(str) || "com.miui.accessibility".equals(str) || "com.miui.mediaviewer".equals(str) || "com.phonetest.application".equals(str) || "com.mi.android.globalminusscreen".equals(str) || "com.mi.globalminusscreen".equals(str) || "com.mi.android.globallauncher".equals(str) || NoteAppStat.APP_PKG.securitycenter.equals(str) || "com.xiaomi.mihomemanager".equals(str) || NotesNormalConstants.AICR_PACKAGE.equals(str) || "com.mi.appfinder".equals(str) || "com.xiaomi.app.toolbox".equals(str) || SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY.equals(str)) {
            Logger.INSTANCE.e(TAG, "callingPackageName is " + str);
            if (SignatureValidator.validateSignature(getContext(), str)) {
                return;
            }
            getContext().enforceCallingPermission("com.miui.notes.permission.ACCESS_NOTE", "Opening uri " + uri);
            return;
        }
        getContext().enforceCallingPermission("com.miui.notes.permission.ACCESS_NOTE", "Opening uri " + uri);
    }

    private int deleteNotes(SQLiteDatabase sQLiteDatabase, String str, String[] strArr, boolean z) {
        Logger.INSTANCE.d(TAG, "deleteNotes,selection:" + str + ",force " + z);
        if (z) {
            int iDelete = sQLiteDatabase.delete("note", str, strArr);
            printCommonDelete(str, strArr, z, iDelete);
            return iDelete;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", (Integer) (-3));
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        return sQLiteDatabase.update("note", contentValues, str, strArr);
    }

    private void requestSync() {
        SyncUtils.requestSync(getContext(), this.mExtras, false);
    }

    private static class ContentChangeNotifier {
        private ArrayList<Pair<Uri, Integer>> iUris;

        private ContentChangeNotifier() {
            this.iUris = new ArrayList<>();
        }

        public synchronized void addUri(Pair<Uri, Integer> pair) {
            this.iUris.add(pair);
        }

        public synchronized void notifyChange(ContentResolver contentResolver) {
            if (this.iUris.isEmpty()) {
                return;
            }
            int size = this.iUris.size();
            int i = 1;
            if (size > 1) {
                Collections.sort(this.iUris, new Comparator<Pair<Uri, Integer>>() { // from class: com.miui.notes.provider.NotesProvider.ContentChangeNotifier.1
                    @Override // java.util.Comparator
                    public int compare(Pair<Uri, Integer> pair, Pair<Uri, Integer> pair2) {
                        return ((Uri) pair.first).toString().compareTo(((Uri) pair2.first).toString());
                    }
                });
                while (i < size) {
                    int i2 = i - 1;
                    if (((Uri) this.iUris.get(i).first).toString().startsWith(((Uri) this.iUris.get(i2).first).toString())) {
                        this.iUris.remove(i2);
                        size--;
                    } else {
                        i++;
                    }
                }
            }
            for (Pair<Uri, Integer> pair : this.iUris) {
                Logger.INSTANCE.d(NotesProvider.TAG, "notifyChange uri");
                contentResolver.notifyChange((Uri) pair.first, (ContentObserver) null, ((Integer) pair.second).intValue());
            }
            this.iUris.clear();
        }
    }
}
