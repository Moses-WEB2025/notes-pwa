package com.miui.notes.action.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.hardware.DataSpace;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.RomUtils;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.data.Todo;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.mirror.synergy.CallMethod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AiActionProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u0000 *2\u00020\u0001:\u0001*B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016JO\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J1\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0017J;\u0010\u0018\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0019J.\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\f2\b\u0010\u001e\u001a\u0004\u0018\u00010\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001bH\u0016J\u0010\u0010 \u001a\u00020!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001bJ\u0010\u0010\"\u001a\u00020!2\b\u0010#\u001a\u0004\u0018\u00010\fJ \u0010$\u001a\u00020!2\b\u0010#\u001a\u0004\u0018\u00010\f2\u0006\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020'J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020\fH\u0002¨\u0006+"}, d2 = {"Lcom/miui/notes/action/provider/AiActionProvider;", "Landroid/content/ContentProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", CallMethod.ARG_URI, "Landroid/net/Uri;", "projection", "", "", "selection", "selectionArgs", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "getType", "insert", "values", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "call", "Landroid/os/Bundle;", "authority", XMLWriter.METHOD, "arg", SecureDatabaseHelper.TABLE_EXTRAS, "handleCall", "", "insertNoteFromAiAction", "content", "insertTodoFromAiAction", "repeat_type", Todo.REMIND_TIME, "", "isEmpty", TypedValues.Custom.S_STRING, "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiActionProvider extends ContentProvider {
    private static final String CREATE_NEW_NOTE = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/create_new_note]:0:1.0";
    private static final String DONE_TODO = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/done_todo]:0:1.0";
    private static final String EXECUTE_ACTION = "execute_action";
    private static final String INSERT_IMAGE = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/insert_info]:0:1.0";
    private static final String INSERT_NOTE = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/insert_note]:0:1.0";
    private static final String INSERT_TODO = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/insert_todo]:0:1.0";
    private static final String KEY_IN = "in";
    private static final String KEY_TYPE = "type";
    private static final String PERMISSION_READ_AIACTION = "com.xiaomi.aicr";
    private static final String PERMISSION_READ_AI_ACTION = "hyperos.permission.READ_AIACTION";
    private static final String SEARCH_NOTE = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/search_note]:0:1.0";
    private static final String SEARCH_TODO = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/search_todo]:0:1.0";
    private static final String SHARE_IMAGE = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/share_image]:0:1.0";
    private static final String SHARE_TEXT = "urn:aiot-spec-v3:com.mi.phones:action:[com.miui.notes/notes/share_text]:0:1.0";
    private static final String TAG = "NotesAiAction";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logger.INSTANCE.i(TAG, "onCreate");
        return false;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return "";
    }

    @Override // android.content.ContentProvider
    public Bundle call(String authority, String method, String arg, Bundle extras) {
        Intrinsics.checkNotNullParameter(authority, "authority");
        Intrinsics.checkNotNullParameter(method, "method");
        Logger.INSTANCE.d(TAG, "call ai action provider");
        Bundle bundle = new Bundle();
        int callingUid = Binder.getCallingUid();
        Context context = getContext();
        String str = null;
        PackageManager packageManager = context != null ? context.getPackageManager() : null;
        String[] packagesForUid = packageManager != null ? packageManager.getPackagesForUid(callingUid) : null;
        if (packagesForUid != null && packagesForUid.length != 0) {
            str = packagesForUid[0];
        }
        if (str != null && Intrinsics.areEqual(str, "com.xiaomi.aicr")) {
            Logger.INSTANCE.d(TAG, "method: " + method);
            if (Intrinsics.areEqual(method, EXECUTE_ACTION)) {
                try {
                    Binder.clearCallingIdentity();
                    handleCall(extras);
                    return bundle;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            Logger.INSTANCE.i(TAG, "Invalid action");
            return bundle;
        }
        Logger.INSTANCE.i(TAG, "Unauthorized calling package");
        return bundle;
    }

    /* JADX WARN: Code duplicated, block: B:131:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:33:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:41:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:43:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:45:0x010d  */
    /* JADX WARN: Code duplicated, block: B:47:0x0119  */
    /* JADX WARN: Code duplicated, block: B:48:0x012a  */
    /* JADX WARN: Code duplicated, block: B:51:0x0147  */
    /* JADX WARN: Code duplicated, block: B:59:0x015d  */
    /* JADX WARN: Code duplicated, block: B:61:0x0161  */
    /* JADX WARN: Code duplicated, block: B:63:0x016c  */
    /* JADX WARN: Code duplicated, block: B:64:0x0176  */
    /* JADX WARN: Code duplicated, block: B:66:0x017c  */
    /* JADX WARN: Code duplicated, block: B:67:0x0186  */
    /* JADX WARN: Code duplicated, block: B:70:0x01a2  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void handleCall(Bundle extras) throws JSONException {
        Intent intent;
        long time;
        Intent intent2;
        Intent intent3;
        if (extras == null) {
            return;
        }
        AiActionUtil.setExtra(extras);
        String string = extras.getString("type");
        JSONObject jSONObject = new JSONObject(extras.getString("in"));
        Logger.INSTANCE.d(TAG, "type :" + string);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        String strOptString = jSONObject.optString(NoteIntent.EXTRA_INSERT_TITLE_NOTE);
        Intrinsics.checkNotNull(strOptString);
        int iIsEmpty = isEmpty(strOptString) - 1;
        String strOptString2 = jSONObject.optString(NoteIntent.EXTRA_INSERT_CONTENT_NOTE);
        Intrinsics.checkNotNull(strOptString2);
        int iIsEmpty2 = iIsEmpty + isEmpty(strOptString2);
        String strOptString3 = jSONObject.optString(NoteIntent.EXTRA_INSERT_IMAGE_NOTE);
        Intrinsics.checkNotNull(strOptString3);
        int iIsEmpty3 = iIsEmpty2 + isEmpty(strOptString3);
        if (string != null) {
            switch (string.hashCode()) {
                case -1491007524:
                    if (string.equals(CREATE_NEW_NOTE)) {
                        if (RomUtils.isPadMode()) {
                            intent = new Intent(getContext(), (Class<?>) NotesListActivity.class);
                            intent.setAction(NoteIntent.ACTION_PAD_INSERT_OR_EDIT);
                        } else {
                            intent = new Intent(getContext(), (Class<?>) EditActivity.class);
                            intent.setAction("android.intent.action.INSERT_OR_EDIT");
                        }
                        if (!TextUtils.isEmpty(strOptString2) || !TextUtils.isEmpty(strOptString3)) {
                            intent.setAction("android.intent.action.SEND");
                        }
                        intent.setType(NoteIntent.ACTION_CREATE_NOTE);
                        if (RomUtils.isPadMode()) {
                            intent.setFlags(268468224);
                        } else {
                            intent.setFlags(DataSpace.RANGE_LIMITED);
                        }
                        if (getContext() == null) {
                            AiActionUtil.asyncNotifyResult(-1);
                            return;
                        }
                        if (PreferenceUtils.isFirstHandle(getContext())) {
                            Context context = getContext();
                            Intrinsics.checkNotNull(context);
                            context.startActivity(intent);
                            AiActionUtil.asyncNotifyResult(-103);
                            return;
                        }
                        intent.putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, strOptString);
                        intent.putExtra("android.intent.extra.TEXT", strOptString2);
                        intent.putExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE, strOptString3);
                        Context context2 = getContext();
                        Intrinsics.checkNotNull(context2);
                        context2.startActivity(intent);
                        AiActionUtil.setReturnCount(iIsEmpty3);
                        return;
                    }
                    return;
                case -800678867:
                    if (string.equals(DONE_TODO) && getContext() != null) {
                        Intent intent4 = new Intent();
                        if (!BaseNoteApp.isTodoInForeground || !BaseNoteApp.isInForeground || BaseNoteApp.isEditInForeground) {
                            AiActionUtil.asyncNotifyResult(-1);
                            return;
                        }
                        intent4.setAction(TodoIntent.ACTION_DONE_TODO);
                        intent4.putExtra(TodoIntent.EXTRA_DONE_TODO_POSITION, (int) Math.max(0.0d, jSONObject.optInt(TodoIntent.EXTRA_DONE_TODO_POSITION) - 1));
                        Context context3 = getContext();
                        Intrinsics.checkNotNull(context3);
                        context3.sendBroadcast(intent4);
                        return;
                    }
                    return;
                case -238283080:
                    if (string.equals(INSERT_NOTE)) {
                        insertNoteFromAiAction(jSONObject.optString(NoteIntent.EXTRA_INSERT_NOTE));
                        return;
                    }
                    return;
                case 135845500:
                    if (!string.equals(INSERT_IMAGE)) {
                        return;
                    }
                    if (getContext() == null) {
                        return;
                    }
                    if (!BaseNoteApp.isEditInForeground) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    intent2 = new Intent();
                    if (Intrinsics.areEqual(SHARE_TEXT, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_TEXT_NOTES));
                    } else if (Intrinsics.areEqual(SHARE_IMAGE, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_IMAGE_NOTES));
                    } else {
                        intent2.setAction(NoteIntent.ACTION_INSERT_IMAGE_NOTES);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, strOptString);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE, strOptString3);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_CONTENT_NOTE, strOptString2);
                        AiActionUtil.setReturnCount(iIsEmpty3);
                    }
                    Context context4 = getContext();
                    Intrinsics.checkNotNull(context4);
                    context4.sendBroadcast(intent2);
                    return;
                case 323341095:
                    if (!string.equals(SEARCH_NOTE)) {
                        return;
                    }
                    intent3 = new Intent(getContext(), (Class<?>) NotesListActivity.class);
                    if (getContext() != null) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    if (PreferenceUtils.isFirstHandle(getContext())) {
                        intent3.setFlags(805306368);
                        Context context5 = getContext();
                        Intrinsics.checkNotNull(context5);
                        context5.startActivity(intent3);
                        AiActionUtil.asyncNotifyResult(-103);
                        return;
                    }
                    intent3.setFlags(805339136);
                    if (Intrinsics.areEqual(SEARCH_NOTE, string)) {
                        intent3.putExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY_FROM_AI_ACTION, jSONObject.optString(NoteIntent.EXTRA_SEARCH_NOTE));
                        intent3.setAction(NoteIntent.ACTION_SEARCH_NOTES);
                    } else {
                        intent3.putExtra(TodoIntent.EXTRA_SEARCH_TODO, jSONObject.optString(TodoIntent.EXTRA_SEARCH_TODO));
                        intent3.setAction(TodoIntent.ACTION_SEARCH_NOTES);
                    }
                    Context context6 = getContext();
                    Intrinsics.checkNotNull(context6);
                    context6.startActivity(intent3);
                    AiActionUtil.asyncNotifyResult(0);
                    return;
                case 967645156:
                    if (string.equals(INSERT_TODO)) {
                        String strOptString4 = jSONObject.optString(TodoIntent.EXTRA_INSERT_TODO_CONTENT);
                        int iOptInt = jSONObject.optInt(TodoIntent.EXTRA_INSERT_TODO_TYPE);
                        String strOptString5 = jSONObject.optString(TodoIntent.EXTRA_INSERT_TODO_TIME);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (!TextUtils.isEmpty(strOptString5)) {
                            try {
                                Date date = simpleDateFormat.parse(strOptString5);
                                if (date != null) {
                                    time = date.getTime();
                                } else {
                                    time = 0;
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        } else {
                            time = 0;
                        }
                        insertTodoFromAiAction(strOptString4, iOptInt, time);
                        return;
                    }
                    return;
                case 988917269:
                    if (!string.equals(SHARE_IMAGE)) {
                        return;
                    }
                    if (getContext() == null) {
                        return;
                    }
                    if (!BaseNoteApp.isEditInForeground) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    intent2 = new Intent();
                    if (Intrinsics.areEqual(SHARE_TEXT, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_TEXT_NOTES));
                    } else if (Intrinsics.areEqual(SHARE_IMAGE, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_IMAGE_NOTES));
                    } else {
                        intent2.setAction(NoteIntent.ACTION_INSERT_IMAGE_NOTES);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, strOptString);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE, strOptString3);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_CONTENT_NOTE, strOptString2);
                        AiActionUtil.setReturnCount(iIsEmpty3);
                    }
                    Context context7 = getContext();
                    Intrinsics.checkNotNull(context7);
                    context7.sendBroadcast(intent2);
                    return;
                case 1502080507:
                    if (!string.equals(SHARE_TEXT)) {
                        return;
                    }
                    if (getContext() == null) {
                        return;
                    }
                    if (!BaseNoteApp.isEditInForeground) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    intent2 = new Intent();
                    if (Intrinsics.areEqual(SHARE_TEXT, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_TEXT_NOTES));
                    } else if (Intrinsics.areEqual(SHARE_IMAGE, string)) {
                        Intrinsics.checkNotNull(intent2.setAction(NoteIntent.ACTION_SHARE_IMAGE_NOTES));
                    } else {
                        intent2.setAction(NoteIntent.ACTION_INSERT_IMAGE_NOTES);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE, strOptString);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_IMAGE_NOTE, strOptString3);
                        intent2.putExtra(NoteIntent.EXTRA_INSERT_CONTENT_NOTE, strOptString2);
                        AiActionUtil.setReturnCount(iIsEmpty3);
                    }
                    Context context8 = getContext();
                    Intrinsics.checkNotNull(context8);
                    context8.sendBroadcast(intent2);
                    return;
                case 1529269331:
                    if (!string.equals(SEARCH_TODO)) {
                        return;
                    }
                    intent3 = new Intent(getContext(), (Class<?>) NotesListActivity.class);
                    if (getContext() != null) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    if (PreferenceUtils.isFirstHandle(getContext())) {
                        intent3.setFlags(805306368);
                        Context context9 = getContext();
                        Intrinsics.checkNotNull(context9);
                        context9.startActivity(intent3);
                        AiActionUtil.asyncNotifyResult(-103);
                        return;
                    }
                    intent3.setFlags(805339136);
                    if (Intrinsics.areEqual(SEARCH_NOTE, string)) {
                        intent3.putExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY_FROM_AI_ACTION, jSONObject.optString(NoteIntent.EXTRA_SEARCH_NOTE));
                        intent3.setAction(NoteIntent.ACTION_SEARCH_NOTES);
                    } else {
                        intent3.putExtra(TodoIntent.EXTRA_SEARCH_TODO, jSONObject.optString(TodoIntent.EXTRA_SEARCH_TODO));
                        intent3.setAction(TodoIntent.ACTION_SEARCH_NOTES);
                    }
                    Context context10 = getContext();
                    Intrinsics.checkNotNull(context10);
                    context10.startActivity(intent3);
                    AiActionUtil.asyncNotifyResult(0);
                    return;
                default:
                    return;
            }
        }
    }

    public final void insertNoteFromAiAction(String content) {
        Uri uri = Notes.Note.CONTENT_URI_ATOMIC;
        ContentValues contentValues = new ContentValues();
        contentValues.put("snippet", content);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("content", content);
        contentValues2.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        ArrayList arrayList = new ArrayList();
        arrayList.add(contentValues2);
        Notes.Utils.addDataValuesToNoteValues(contentValues, arrayList);
        AiActionUtil.asyncNotifyResult((!TextUtils.isEmpty(content) ? BaseNoteApp.INSTANCE.getInstance().getContentResolver().insert(uri, contentValues) : null) == null ? -1 : 0);
    }

    public final void insertTodoFromAiAction(String content, int repeat_type, long remind_time) {
        Uri uriInsert;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Todo.CREATE_TIME, Long.valueOf(System.currentTimeMillis()));
        contentValues.put(Todo.REMIND_REPEAT_TYPE, Integer.valueOf(repeat_type));
        if (repeat_type > 0) {
            contentValues.put(Todo.REMIND_TYPE, (Integer) 2);
            if (remind_time <= 0) {
                remind_time = System.currentTimeMillis();
            }
            contentValues.put(Todo.REMIND_TIME, Long.valueOf(remind_time));
            contentValues.put(Todo.FIRST_REMIND_TIME, Long.valueOf(remind_time));
            contentValues.put(Todo.EXPIRE_TIME, Long.valueOf(remind_time));
        } else {
            contentValues.put(Todo.REMIND_TYPE, (Integer) 0);
            contentValues.put(Todo.EXPIRE_TIME, (Integer) 0);
            contentValues.put(Todo.REMIND_TIME, (Integer) 0);
        }
        if (TextUtils.isEmpty(content)) {
            uriInsert = null;
        } else {
            contentValues.put("content", content);
            uriInsert = BaseNoteApp.INSTANCE.getInstance().getContentResolver().insert(Uri.parse(Todo.URI_TODO_OUTER_INSERT), contentValues);
        }
        AiActionUtil.asyncNotifyResult(uriInsert == null ? -1 : 0);
    }

    private final int isEmpty(String string) {
        return !TextUtils.isEmpty(string) ? 1 : 0;
    }
}
