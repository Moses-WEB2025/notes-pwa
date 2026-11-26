package com.miui.todo.cloudservice;

import android.accounts.Account;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Pair;
import com.google.gson.Gson;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cloudservice.NoteE2EEManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import com.miui.todo.utils.SerializableUtils;
import com.xiaomi.opensdk.pdc.Constants;
import com.xiaomi.opensdk.pdc.SyncException;
import com.xiaomi.opensdk.pdc.SyncRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class SyncHelper {
    static final int INSERT_MARGIN = 100;
    static final long SYNC_ID_STARTINDEX = 100000000;
    private static final String TAG = "SyncHelper";

    SyncHelper() {
    }

    static boolean isContentSame(TodoEntity todoEntity, TodoEntity todoEntity2) {
        return todoEntity.getContent().equals(todoEntity2.getContent()) && todoEntity.getRemindType() == todoEntity2.getRemindType() && todoEntity.getRemindRepeatType() == todoEntity2.getRemindRepeatType() && todoEntity.getRemindType() == todoEntity2.getRemindType();
    }

    static void saveSyncInfoToLocal(Account account, String str, long j) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setToken(str);
        accountEntity.setAccountName(account.name);
        accountEntity.setWaterMarker(j);
        accountEntity.setLastSyncTime(System.currentTimeMillis());
        accountEntity.setAccountType(account.type);
        accountEntity.setSyncStatus(0L);
        SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY, accountEntity);
    }

    static TodoEntity parseRecord(SyncRecord syncRecord) throws SyncException {
        TodoEntity todoEntity = getTodoEntity(syncRecord);
        if (todoEntity == null) {
            return null;
        }
        if (syncRecord.status == Constants.RecordStatus.NORMAL) {
            todoEntity.setServerStatus(0);
        } else if (syncRecord.status == Constants.RecordStatus.DELETED) {
            todoEntity.setServerStatus(1);
        }
        todoEntity.setSyncId(Long.parseLong(syncRecord.id));
        todoEntity.setSyncEtag(syncRecord.eTag);
        return todoEntity;
    }

    private static TodoEntity getTodoEntity(SyncRecord syncRecord) throws SyncException {
        try {
            JSONObject jSONObject = syncRecord.contentJson.getJSONObject("entity");
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("encryptInfo");
            Pair<String, Boolean> pairDecryptData = NoteE2EEManager.decryptData(jSONObject.getString("content"), jSONObjectOptJSONObject);
            Pair<String, Boolean> pairDecryptData2 = NoteE2EEManager.decryptData(jSONObject.getString("plainText"), jSONObjectOptJSONObject);
            String str = (String) pairDecryptData.first;
            String str2 = (String) pairDecryptData2.first;
            if (((Boolean) pairDecryptData.second).booleanValue() && ((Boolean) pairDecryptData2.second).booleanValue()) {
                jSONObject.put("content", str);
                jSONObject.put("plainText", str2);
                return (TodoEntity) new Gson().fromJson(jSONObject.toString(), TodoEntity.class);
            }
            return null;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getUpdateJson error " + e);
            throw new SyncException("TodoSyncManager getTodoEntity error");
        }
    }

    static TodoEntity changeServerEntityToSave(TodoEntity todoEntity, TodoEntity todoEntity2) {
        todoEntity.setLocalStatus(1);
        todoEntity.setCustomSortId(todoEntity2.getCustomSortId());
        todoEntity.setId(todoEntity2.getId());
        todoEntity.setSource(todoEntity2.getSource());
        return todoEntity;
    }

    static TodoEntity changeServerEntityToUpdate(TodoEntity todoEntity, TodoEntity todoEntity2) {
        todoEntity.setLocalStatus(2);
        todoEntity.setCustomSortId(todoEntity2.getCustomSortId());
        todoEntity.setId(todoEntity2.getId());
        todoEntity.setSource(todoEntity2.getSource());
        return todoEntity;
    }

    static void cleanEntitySyncInfo(TodoEntity todoEntity) {
        todoEntity.setLocalStatus(0);
        todoEntity.setSyncId(0L);
        todoEntity.setSyncEtag(0L);
        TodoProviderAccessUtils.updateItem(todoEntity, true);
    }

    static void cleanSyncInfo(long j) {
        Logger.INSTANCE.d(TAG, "cleanSyncInfo id:" + j);
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync_id", (Long) 0L);
        contentValues.put(Todo.SYNC_ETAG, (Long) 0L);
        contentValues.put("local_status", (Integer) 0);
        TodoProviderAccessUtils.updateItem(j, contentValues, true);
    }

    static void rolveSort(List<Long> list) {
        long j;
        LogUtil.i("rolveSort ");
        HashSet hashSet = new HashSet();
        hashSet.addAll(list);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Cursor cursorQueryAllUnFinishItemReverse = TodoProviderAccessUtils.queryAllUnFinishItemReverse();
        int count = cursorQueryAllUnFinishItemReverse == null ? 0 : cursorQueryAllUnFinishItemReverse.getCount();
        long j2 = 1048576;
        PreferenceUtils.setMaxSortID(((long) (count + 100 + list.size())) * 1048576);
        long j3 = 0;
        if (count != 0) {
            while (cursorQueryAllUnFinishItemReverse.moveToNext()) {
                TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQueryAllUnFinishItemReverse);
                if (todoEntity.getSyncId() == 0) {
                    todoEntity.setSyncId(todoEntity.getId());
                    TodoProviderAccessUtils.updateItem(todoEntity, true);
                }
                arrayList.add(Long.valueOf(todoEntity.getSyncId()));
            }
            ArrayList arrayList3 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (hashSet.contains(arrayList.get(i))) {
                    arrayList3.add(Integer.valueOf(i));
                }
            }
            arrayList2.addAll(list);
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i2 < arrayList3.size()) {
                int iIntValue = ((Integer) arrayList3.get(i2)).intValue();
                long jLongValue = ((Long) arrayList.get(iIntValue)).longValue();
                long j4 = j3;
                for (int i5 = i3; i5 < arrayList2.size(); i5++) {
                    if (((Long) arrayList2.get(i5)).longValue() == jLongValue) {
                        i3 = i5;
                        break;
                    }
                }
                while (i4 < iIntValue) {
                    arrayList2.add(i3, (Long) arrayList.get(i4));
                    i3++;
                    i4++;
                }
                i4 = iIntValue + 1;
                i2++;
                j3 = j4;
            }
            j = j3;
            int size = arrayList2.size() - 1;
            int iIntValue2 = arrayList3.size() != 0 ? ((Integer) arrayList3.get(arrayList3.size() - 1)).intValue() + 1 : 0;
            if (iIntValue2 < arrayList.size()) {
                while (iIntValue2 < arrayList.size()) {
                    size++;
                    arrayList2.add(size, (Long) arrayList.get(iIntValue2));
                    iIntValue2++;
                }
            }
        } else {
            j = 0;
            arrayList2.addAll(list);
        }
        if (cursorQueryAllUnFinishItemReverse != null) {
            cursorQueryAllUnFinishItemReverse.close();
        }
        int size2 = arrayList2.size();
        HashMap map = new HashMap();
        Cursor cursorQueryAllItemsSyncIdAndId = TodoProviderAccessUtils.queryAllItemsSyncIdAndId();
        if (cursorQueryAllItemsSyncIdAndId != null) {
            try {
                int columnIndex = cursorQueryAllItemsSyncIdAndId.getColumnIndex("sync_id");
                int columnIndex2 = cursorQueryAllItemsSyncIdAndId.getColumnIndex("id");
                while (cursorQueryAllItemsSyncIdAndId.moveToNext()) {
                    long j5 = cursorQueryAllItemsSyncIdAndId.getLong(columnIndex);
                    long j6 = cursorQueryAllItemsSyncIdAndId.getLong(columnIndex2);
                    if (j5 != j) {
                        map.put(Long.valueOf(j5), Long.valueOf(j6));
                    }
                }
                cursorQueryAllItemsSyncIdAndId.close();
            } catch (Throwable th) {
                cursorQueryAllItemsSyncIdAndId.close();
                throw th;
            }
        }
        ArrayList<ContentProviderOperation> arrayList4 = new ArrayList<>(200);
        Uri uri = Uri.parse(TodoProvider.URI_TODO_PRIVATE_SYNC);
        int i6 = 0;
        while (i6 < size2) {
            long jLongValue2 = ((Long) arrayList2.get(i6)).longValue();
            long j7 = ((long) (size2 - i6)) * j2;
            Long l = (Long) map.get(Long.valueOf(jLongValue2));
            if (l == null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("sync_id", Long.valueOf(jLongValue2));
                contentValues.put(Todo.CUSTOM_SORT_ID, Long.valueOf(j7));
                contentValues.put("source", (Integer) 1);
                contentValues.put("local_status", (Integer) 4);
                arrayList4.add(ContentProviderOperation.newInsert(uri).withValues(contentValues).build());
            } else {
                Uri uriWithAppendedId = ContentUris.withAppendedId(uri, l.longValue());
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(Todo.CUSTOM_SORT_ID, Long.valueOf(j7));
                arrayList4.add(ContentProviderOperation.newUpdate(uriWithAppendedId).withValues(contentValues2).build());
            }
            if (arrayList4.size() >= 200 || i6 == size2 - 1) {
                try {
                    NoteApp.getInstance().getContentResolver().applyBatch(Todo.AUTHORITY, arrayList4);
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "rolveSort batch error: " + e);
                }
                arrayList4.clear();
            }
            i6++;
            j2 = 1048576;
        }
    }
}
