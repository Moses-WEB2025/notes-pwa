package com.miui.todo.data.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.NoteAppStat;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.NotificationUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.R;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.provider.SignatureValidator;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.todo.cloudservice.LogUtil;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.feature.todolist.WorkingTodo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: TodoProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 22\u00020\u0001:\u000212B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016JO\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0010\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0010\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0012H\u0016¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J1\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0010\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u0011H\u0016¢\u0006\u0002\u0010\u001cJ;\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0010\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u0011H\u0016¢\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\u0012\u0010(\u001a\u00020\u000b2\b\u0010)\u001a\u0004\u0018\u00010*H\u0003J\u001c\u0010+\u001a\u00020!2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010,\u001a\u0004\u0018\u00010\u0012H\u0002J\b\u0010/\u001a\u00020!H\u0002J\b\u00100\u001a\u00020!H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010$\u001a\u0004\u0018\u00010%8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010-\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.¨\u00063"}, d2 = {"Lcom/miui/todo/data/provider/TodoProvider;", "Landroid/content/ContentProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "dbHelper", "Lcom/miui/todo/data/provider/TodoDatabaseHelper;", "mExtras", "Landroid/os/Bundle;", "mSyncHandler", "Lcom/miui/todo/data/provider/TodoProvider$SyncHandler;", "onCreate", "", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", CallMethod.ARG_URI, "Landroid/net/Uri;", "projection", "", "", "selection", "selectionArgs", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "insert", "values", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "getType", "notifyDataChanged", "", "enforceNewPermission", "isQuery", "permissionInfo", "Landroid/content/pm/PermissionInfo;", "getPermissionInfo", "()Landroid/content/pm/PermissionInfo;", "isSignedWithPlatformKey", "applicationInfo", "Landroid/content/pm/ApplicationInfo;", "enforcePermission", "callingPackageName", "isNeedInsertNotification", "()Z", "requestSync", "requestSyncDelayed", "SyncHandler", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TodoProvider extends ContentProvider {
    private static final int MSG_REQUEST_SYNC_DELAYED = 301;
    private static final String RETURN_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.miui.todo.provider.todo";
    private static final String RETURN_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.miui.todo.provider.todo";
    private static final String TAG = "TodoProvider";
    private static final int URI_TODO = 1;
    private static final int URI_TODO_ITEM = 2;
    private static final int URI_TODO_OUTER = 3;
    private static final int URI_TODO_OUTER_FINISH = 5;
    private static final int URI_TODO_OUTER_FLEX = 8;
    private static final int URI_TODO_OUTER_INSERT = 4;
    public static final String URI_TODO_PRIVATE = "content://com.miui.todo.provider/todo/private";
    public static final String URI_TODO_PRIVATE_SYNC = "content://com.miui.todo.provider/todo/pri_sync";
    private static final int URI_TODO_SYNC = 6;
    private static final int URI_TODO_SYNC_ITEM = 7;
    private static final UriMatcher uriMatcher;
    private TodoDatabaseHelper dbHelper;
    private Bundle mExtras;
    private SyncHandler mSyncHandler;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logger.INSTANCE.d(TAG, "onCreate");
        this.dbHelper = TodoDatabaseHelper.getInstance(getContext());
        Bundle bundle = new Bundle();
        this.mExtras = bundle;
        Intrinsics.checkNotNull(bundle);
        bundle.putInt("push_type", 2);
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        this.mSyncHandler = new SyncHandler(this, mainLooper);
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Logger.INSTANCE.d(TAG, "query...");
        if (!enforceNewPermission(true, uri)) {
            return null;
        }
        TodoDatabaseHelper todoDatabaseHelper = this.dbHelper;
        Intrinsics.checkNotNull(todoDatabaseHelper);
        SQLiteDatabase readableDatabase = todoDatabaseHelper.getReadableDatabase();
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            return readableDatabase.query(Todo.Table.TODO_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (iMatch == 2) {
            return readableDatabase.query(Todo.Table.TODO_NAME, projection, "id = ?", new String[]{uri.getPathSegments().get(2)}, null, null, sortOrder);
        }
        if (iMatch != 3) {
            return null;
        }
        Cursor cursorQuery = readableDatabase.query(Todo.Table.TODO_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        return cursorQuery != null ? new TodoCursorWrapperForWidget(cursorQuery) : cursorQuery;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Integer asInteger;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Logger.INSTANCE.d(TAG, "insert");
        boolean zIsNeedInsertNotification = isNeedInsertNotification();
        int iIntValue = 0;
        Uri uri2 = null;
        if (!enforceNewPermission(false, uri)) {
            return null;
        }
        TodoDatabaseHelper todoDatabaseHelper = this.dbHelper;
        Intrinsics.checkNotNull(todoDatabaseHelper);
        SQLiteDatabase readableDatabase = todoDatabaseHelper.getReadableDatabase();
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1 || iMatch == 2) {
            LogUtil.d("CloudDebug6", "TodoProvider addItem URI_TODO or URI_TODO_ITEM");
            Intrinsics.checkNotNull(values);
            values.put("local_status", (Integer) 0);
            values.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis()));
            uri2 = Uri.parse("content://com.miui.todo.provider/todo/" + readableDatabase.insert(Todo.Table.TODO_NAME, null, values));
        } else if (iMatch == 4) {
            LogUtil.d("CloudDebug6", "TodoProvider addItem URI_TODO_OUTER_INSERT");
            Intrinsics.checkNotNull(values);
            String asString = values.getAsString("content");
            if (values.getAsInteger(Todo.REMIND_REPEAT_TYPE) != null) {
                Integer asInteger2 = values.getAsInteger(Todo.REMIND_REPEAT_TYPE);
                Intrinsics.checkNotNullExpressionValue(asInteger2, "getAsInteger(...)");
                iIntValue = asInteger2.intValue();
            }
            Long asLong = values.getAsLong(Todo.REMIND_TIME);
            Intrinsics.checkNotNullExpressionValue(asLong, "getAsLong(...)");
            long jLongValue = asLong.longValue();
            Integer asInteger3 = values.getAsInteger(Todo.REMIND_TYPE);
            Intrinsics.checkNotNullExpressionValue(asInteger3, "getAsInteger(...)");
            int iIntValue2 = asInteger3.intValue();
            String asString2 = values.getAsString("content");
            Long asLong2 = values.getAsLong(Todo.REMIND_TIME);
            Intrinsics.checkNotNullExpressionValue(asLong2, "getAsLong(...)");
            TodoBaseEntity todoBaseEntity = new TodoBaseEntity(asString, jLongValue, iIntValue2, 0, "", asString2, iIntValue, 0, 0, asLong2.longValue());
            if (values.get(Todo.LIST_TYPE) != null && (asInteger = values.getAsInteger(Todo.LIST_TYPE)) != null && asInteger.intValue() == 1) {
                todoBaseEntity.setListType(1);
                SubModeImpl subModeImpl = new SubModeImpl(asString);
                String title = subModeImpl.getTitle();
                if (TextUtils.isEmpty(title)) {
                    title = BaseNoteApp.INSTANCE.getInstance().getString(R.string.todo_list);
                }
                todoBaseEntity.setContent(subModeImpl.getContent());
                todoBaseEntity.setPlainText(title);
            }
            long jAddItem = TodoDao.addItem(todoBaseEntity);
            Uri uri3 = Uri.parse("content://com.miui.todo.provider/todo/" + jAddItem);
            if (values.getAsLong(Todo.REMIND_TIME).longValue() > 0) {
                AlarmReceiver.setupTodoAlarms(getContext());
            }
            if (zIsNeedInsertNotification) {
                NotificationUtils notificationUtils = NotificationUtils.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNull(context);
                notificationUtils.createNotificationByOutInsertTodo(context, jAddItem);
            }
            uri2 = uri3;
        } else if (iMatch == 6 || iMatch == 7) {
            LogUtil.d("CloudDebug6", "TodoProvider addItem URI_TODO_SYNC or URI_TODO_SYNC_ITEM");
            Intrinsics.checkNotNull(values);
            values.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis()));
            uri2 = Uri.parse("content://com.miui.todo.provider/todo/" + readableDatabase.insert(Todo.Table.TODO_NAME, null, values));
        }
        notifyDataChanged(uri);
        requestSync();
        return uri2;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Logger.INSTANCE.d(TAG, ToDoStat.DELETE);
        int iDelete = 0;
        if (!enforceNewPermission(false, uri)) {
            return 0;
        }
        TodoDatabaseHelper todoDatabaseHelper = this.dbHelper;
        Intrinsics.checkNotNull(todoDatabaseHelper);
        SQLiteDatabase readableDatabase = todoDatabaseHelper.getReadableDatabase();
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            iDelete = readableDatabase.delete(Todo.Table.TODO_NAME, selection, selectionArgs);
        } else if (iMatch == 2) {
            iDelete = readableDatabase.delete(Todo.Table.TODO_NAME, "id = ?", new String[]{uri.getPathSegments().get(2)});
        } else if (iMatch == 6) {
            iDelete = readableDatabase.delete(Todo.Table.TODO_NAME, selection, selectionArgs);
        } else if (iMatch == 7) {
            iDelete = readableDatabase.delete(Todo.Table.TODO_NAME, "id = ?", new String[]{uri.getPathSegments().get(2)});
        }
        notifyDataChanged(uri);
        requestSync();
        return iDelete;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int iUpdate;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Logger.INSTANCE.d(TAG, "update...");
        boolean z = false;
        z = false;
        z = false;
        int iUpdate2 = 0;
        if (!enforceNewPermission(false, uri)) {
            return 0;
        }
        TodoDatabaseHelper todoDatabaseHelper = this.dbHelper;
        Intrinsics.checkNotNull(todoDatabaseHelper);
        SQLiteDatabase readableDatabase = todoDatabaseHelper.getReadableDatabase();
        int iMatch = uriMatcher.match(uri);
        boolean z2 = true;
        if (iMatch != 1) {
            if (iMatch == 2) {
                String str = uri.getPathSegments().get(2);
                Intrinsics.checkNotNull(values);
                values.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis()));
                iUpdate2 = readableDatabase.update(Todo.Table.TODO_NAME, values, "id = ?", new String[]{str});
                Intrinsics.checkNotNull(str);
                TodoEntity todoEntityQueryItemEntity = TodoProviderAccessUtils.queryItemEntity(Long.parseLong(str));
                if (todoEntityQueryItemEntity != null && todoEntityQueryItemEntity.getLocalStatus() == 1) {
                    todoEntityQueryItemEntity.setLocalStatus(2);
                    TodoProviderAccessUtils.updateItem(todoEntityQueryItemEntity, true);
                }
            } else if (iMatch != 5) {
                if (iMatch != 7) {
                    if (iMatch == 8 && values != null) {
                        Long asLong = values.getAsLong("id");
                        Intrinsics.checkNotNull(asLong);
                        TodoEntity todoEntityQueryItemEntity2 = TodoDao.queryItemEntity(asLong.longValue());
                        if (todoEntityQueryItemEntity2 == null) {
                            return 0;
                        }
                        TodoEntity todoEntityCopy = todoEntityQueryItemEntity2.copy();
                        Boolean asBoolean = values.getAsBoolean(Todo.IS_FLEX);
                        SubModeImpl subModeImpl = new SubModeImpl(todoEntityCopy.getContent());
                        Intrinsics.checkNotNull(asBoolean);
                        subModeImpl.setExpand(asBoolean.booleanValue());
                        todoEntityCopy.setContent(subModeImpl.getContent());
                        todoEntityCopy.setLocalStatus(2);
                        todoEntityCopy.setLastModifiedTime(System.currentTimeMillis());
                        TodoProviderAccessUtils.updateItem(todoEntityCopy, true);
                    }
                    iUpdate = 0;
                } else {
                    String str2 = uri.getPathSegments().get(2);
                    Intrinsics.checkNotNull(values);
                    values.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis()));
                    iUpdate = readableDatabase.update(Todo.Table.TODO_NAME, values, "id = ?", new String[]{str2});
                    z2 = false;
                }
            } else {
                if (values == null) {
                    return 0;
                }
                Long asLong2 = values.getAsLong("id");
                Integer asInteger = values.getAsInteger(Todo.IS_FINISH);
                boolean z3 = asInteger != null && asInteger.intValue() == 1;
                Intrinsics.checkNotNull(asLong2);
                TodoEntity todoEntityQueryItemEntity3 = TodoDao.queryItemEntity(asLong2.longValue());
                if (todoEntityQueryItemEntity3 == null) {
                    return 0;
                }
                TodoEntity todoEntityCopy2 = todoEntityQueryItemEntity3.copy();
                values.put(Todo.LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis()));
                if (values.getAsInteger(Todo.SUB_TODO_INDEX) == null) {
                    if (todoEntityQueryItemEntity3.getListType() == 0) {
                        TodoDao.updateItemFinishStatus(asLong2.longValue(), z3);
                    } else {
                        Intrinsics.checkNotNull(todoEntityCopy2);
                        SubModeImpl subModeImpl2 = new SubModeImpl(todoEntityCopy2.getContent());
                        subModeImpl2.changeFinishStatusAll(z3);
                        todoEntityCopy2.setContent(subModeImpl2.getContent());
                        todoEntityCopy2.setIsFinish(z3 ? 1 : 0);
                        todoEntityCopy2.setLocalStatus(2);
                        TodoProviderAccessUtils.updateItem(todoEntityCopy2, false);
                    }
                } else if (todoEntityCopy2 != null) {
                    Integer asInteger2 = values.getAsInteger(Todo.SUB_TODO_INDEX);
                    SubModeImpl subModeImpl3 = new SubModeImpl(todoEntityCopy2.getContent());
                    Intrinsics.checkNotNull(asInteger2);
                    int iChangeFinishStatus = subModeImpl3.changeFinishStatus(asInteger2.intValue(), z3);
                    if (iChangeFinishStatus == 0) {
                        todoEntityCopy2.setIsFinish(0);
                    } else {
                        if (iChangeFinishStatus == 1) {
                            todoEntityCopy2.setIsFinish(1);
                            z3 = true;
                        } else if (iChangeFinishStatus == 2) {
                        }
                        todoEntityCopy2.setContent(subModeImpl3.getContent());
                        todoEntityCopy2.setLocalStatus(2);
                        TodoProviderAccessUtils.updateItem(todoEntityCopy2, true);
                    }
                    z3 = false;
                    todoEntityCopy2.setContent(subModeImpl3.getContent());
                    todoEntityCopy2.setLocalStatus(2);
                    TodoProviderAccessUtils.updateItem(todoEntityCopy2, true);
                }
                if (z3) {
                    AlarmReceiver.scheduleTodoAlert(BaseNoteApp.INSTANCE.getInstance(), asLong2.longValue(), 0L, true);
                    if (todoEntityQueryItemEntity3.getRemindRepeatType() > 0) {
                        if (todoEntityCopy2 != null) {
                            try {
                                if (todoEntityCopy2.getListType() != 0) {
                                    SubModeImpl subModeImpl4 = new SubModeImpl(todoEntityCopy2.getContent());
                                    subModeImpl4.changeFinishStatusAll(false);
                                    todoEntityCopy2.setContent(subModeImpl4.getContent());
                                }
                            } catch (Exception e) {
                                com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception:" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        TodoEntity todoEntityCreateRepeatTodo = WorkingTodo.createRepeatTodo(todoEntityCopy2);
                        if (!TextUtils.isEmpty(todoEntityCreateRepeatTodo.getContent())) {
                            long jAddItem = TodoDao.addItem(todoEntityCreateRepeatTodo);
                            if (todoEntityCreateRepeatTodo.getRemindType() != 0) {
                                AlarmReceiver.scheduleTodoAlert(BaseNoteApp.INSTANCE.getInstance(), jAddItem, todoEntityCreateRepeatTodo.getRemindTime(), false);
                            }
                        }
                    }
                }
                UIUtils.INSTANCE.updateWidgetTodo(BaseNoteApp.INSTANCE.getInstance());
            }
            iUpdate = iUpdate2;
            z = true;
        } else {
            iUpdate = readableDatabase.update(Todo.Table.TODO_NAME, values, selection, selectionArgs);
        }
        notifyDataChanged(uri);
        Logger.INSTANCE.d(TAG, "update with delay: " + z);
        if (z) {
            requestSyncDelayed();
        } else if (z2) {
            requestSync();
        }
        return iUpdate;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        switch (uriMatcher.match(uri)) {
            case 1:
                return RETURN_TYPE_DIR;
            case 2:
            case 3:
            case 4:
            case 5:
                return RETURN_TYPE_ITEM;
            case 6:
                return RETURN_TYPE_DIR;
            case 7:
                return RETURN_TYPE_ITEM;
            default:
                return null;
        }
    }

    private final void notifyDataChanged(Uri uri) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            contentResolver.notifyChange(uri, (ContentObserver) null, false);
            if (!uri.toString().equals(Uri.parse(URI_TODO_PRIVATE))) {
                contentResolver.notifyChange(Uri.parse(URI_TODO_PRIVATE), (ContentObserver) null, false);
            }
            if (uri.toString().equals(Uri.parse(Todo.URI_TODO_OUTER))) {
                return;
            }
            contentResolver.notifyChange(Uri.parse(Todo.URI_TODO_OUTER), (ContentObserver) null, false);
        }
    }

    private final boolean enforceNewPermission(boolean isQuery, Uri uri) {
        List listEmptyList;
        if (Binder.getCallingUid() == Process.myUid()) {
            return true;
        }
        String callingPackage = getCallingPackage();
        com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "callingPackageName :" + callingPackage);
        PackageManager packageManager = BaseNoteApp.INSTANCE.getInstance().getPackageManager();
        if (!TextUtils.isEmpty(callingPackage)) {
            if (RomUtils.isInternationalBuild()) {
                enforcePermission(uri, callingPackage);
                return true;
            }
            try {
                Intrinsics.checkNotNull(callingPackage);
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(callingPackage, 64);
                Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
                if (!isSignedWithPlatformKey(applicationInfo) && !SignatureValidator.validateSignature(BaseNoteApp.INSTANCE.getInstance(), callingPackage)) {
                    com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "this is callingPackageName is notSignedWithPlatformKey");
                    return false;
                }
                PermissionInfo permissionInfo = getPermissionInfo();
                if (isQuery && permissionInfo != null) {
                    String[] strArr = packageManager.getPackageInfo(callingPackage, 4096).requestedPermissions;
                    if (strArr == null || (listEmptyList = ArraysKt.toList(strArr)) == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    if (new ArrayList(listEmptyList).contains(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1)) {
                        return BaseNoteApp.INSTANCE.getInstance().checkCallingPermission(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1) == 0;
                    }
                    enforcePermission(uri, callingPackage);
                    return true;
                }
                enforcePermission(uri, callingPackage);
                return true;
            } catch (Exception e) {
                com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, " todo exception : " + e);
                return false;
            }
        }
        com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "this is callingPackageName is null");
        return false;
    }

    private final PermissionInfo getPermissionInfo() {
        try {
            return BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPermissionInfo(NotesNormalConstants.ACCESS_NOTE_ABOVE_OS1, 0);
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "getPermissionInfo,exception:" + e);
            return null;
        }
    }

    private final boolean isSignedWithPlatformKey(ApplicationInfo applicationInfo) {
        try {
            Boolean bool = (Boolean) ApplicationInfo.class.getDeclaredMethod("isSignedWithPlatformKey", new Class[0]).invoke(applicationInfo, new Object[0]);
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "Reflect exception!,exception:" + e);
            return false;
        }
    }

    private final void enforcePermission(Uri uri, String callingPackageName) {
        Binder.clearCallingIdentity();
        if ((!TextUtils.isEmpty(callingPackageName) && (Intrinsics.areEqual("com.miui.voiceassist", callingPackageName) || Intrinsics.areEqual("com.android.itransfer", callingPackageName) || Intrinsics.areEqual("com.android.calendar", callingPackageName) || Intrinsics.areEqual("com.xiaomi.calendar", callingPackageName) || Intrinsics.areEqual("com.miui.smarttravel", callingPackageName) || Intrinsics.areEqual(NoteAppStat.APP_PKG.desktop, callingPackageName) || Intrinsics.areEqual("com.miui.personalassistant", callingPackageName) || Intrinsics.areEqual("com.mi.health", callingPackageName) || Intrinsics.areEqual("com.xiaomi.wearable", callingPackageName) || Intrinsics.areEqual("com.xiaomi.aireco", callingPackageName) || Intrinsics.areEqual("com.mi.globalminusscreen", callingPackageName) || Intrinsics.areEqual("com.phonetest.application", callingPackageName) || Intrinsics.areEqual("com.mi.android.globallauncher", callingPackageName) || Intrinsics.areEqual("com.xiaomi.mihomemanager", callingPackageName) || Intrinsics.areEqual("com.android.mms", callingPackageName))) || Intrinsics.areEqual("com.mi.appfinder", callingPackageName)) {
            if (SignatureValidator.validateSignature(getContext(), callingPackageName)) {
                return;
            }
            Context context = getContext();
            Intrinsics.checkNotNull(context);
            context.enforceCallingPermission("com.miui.notes.permission.ACCESS_NOTE", "Opening uri " + uri);
            return;
        }
        Context context2 = getContext();
        Intrinsics.checkNotNull(context2);
        context2.enforceCallingPermission("com.miui.notes.permission.ACCESS_NOTE", "Opening uri " + uri);
    }

    private final boolean isNeedInsertNotification() {
        return Intrinsics.areEqual("com.android.mms", getCallingPackage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestSync() {
        SyncUtils.requestSync(getContext(), this.mExtras, false);
    }

    private final void requestSyncDelayed() {
        Logger.INSTANCE.d(TAG, "requestSyncDelayed");
        SyncHandler syncHandler = this.mSyncHandler;
        Intrinsics.checkNotNull(syncHandler);
        syncHandler.removeMessages(301);
        SyncHandler syncHandler2 = this.mSyncHandler;
        Intrinsics.checkNotNull(syncHandler2);
        SyncHandler syncHandler3 = this.mSyncHandler;
        Intrinsics.checkNotNull(syncHandler3);
        syncHandler2.sendMessageDelayed(syncHandler3.obtainMessage(301), EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
    }

    /* JADX INFO: compiled from: TodoProvider.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\"\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lcom/miui/todo/data/provider/TodoProvider$SyncHandler;", "Landroid/os/Handler;", "todoProvider", "Lcom/miui/todo/data/provider/TodoProvider;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/todo/data/provider/TodoProvider;Landroid/os/Looper;)V", "mTodoProvider", "Ljava/lang/ref/WeakReference;", "getMTodoProvider", "()Ljava/lang/ref/WeakReference;", "setMTodoProvider", "(Ljava/lang/ref/WeakReference;)V", "handleMessage", "", "msg", "Landroid/os/Message;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class SyncHandler extends Handler {
        private WeakReference<TodoProvider> mTodoProvider;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SyncHandler(TodoProvider todoProvider, Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(looper, "looper");
            this.mTodoProvider = new WeakReference<>(todoProvider);
        }

        public final WeakReference<TodoProvider> getMTodoProvider() {
            return this.mTodoProvider;
        }

        public final void setMTodoProvider(WeakReference<TodoProvider> weakReference) {
            Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
            this.mTodoProvider = weakReference;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            if (msg.what == 301) {
                TodoProvider todoProvider = this.mTodoProvider.get();
                Intrinsics.checkNotNull(todoProvider);
                todoProvider.requestSync();
            }
        }
    }

    static {
        UriMatcher uriMatcher2 = new UriMatcher(-1);
        uriMatcher = uriMatcher2;
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/private", 1);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/private/#", 2);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/outer", 3);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/out_insert", 4);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/out_finish", 5);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/pri_sync", 6);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/pri_sync/#", 7);
        uriMatcher2.addURI(Todo.AUTHORITY, "todo/out_flex", 8);
    }
}
