package com.miui.todo.cloudservice;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cloudservice.NoteE2EEManager;
import com.miui.notes.cloudservice.NoteSyncException;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.todo.data.Todo;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.DragSort;
import com.miui.todo.data.utils.GuideUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import com.xiaomi.e2ee.appkey.KeyEntry;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.pdc.Pdc4SyncServerAdapter;
import com.xiaomi.opensdk.pdc.Constants;
import com.xiaomi.opensdk.pdc.DownloadOperation;
import com.xiaomi.opensdk.pdc.FetchRecordOperation;
import com.xiaomi.opensdk.pdc.SyncException;
import com.xiaomi.opensdk.pdc.SyncFactory;
import com.xiaomi.opensdk.pdc.SyncRecord;
import com.xiaomi.opensdk.pdc.UploadOperation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class TodoSyncManager {
    private static final SyncFactory SYNC_FACTORY = new SyncFactory(new Pdc4SyncServerAdapter("0", "0", new TodoUrlGenerator()));
    private static final String TAG = "CloudDebug：TodoSyncManager";
    private boolean isNeedUploadAgain;
    private boolean isNeedUploadSort;
    private List<Long> localOrdersList;
    private final Account mAccount;
    private TodoMediaManager mTodoMediaManager;
    private String mToken;
    private long mWaterMarker;
    private List<Long> serverOrdersList;
    private volatile boolean mSyncCanceled = false;
    private Gson mGson = new Gson();

    private class SyncType {
        private static final String ENTITY = "entity";
        private static final String FOLDER = "folder";
        private static final String SORT = "sort";

        private SyncType() {
        }
    }

    private class SYNC_OPERATION {
        private static final int CREATE = 1;
        private static final int DELETE = 3;
        private static final int UPDATE = 2;

        private SYNC_OPERATION() {
        }
    }

    TodoSyncManager(Context context, Account account, String str, long j) {
        this.mAccount = account;
        this.mToken = str;
        this.mWaterMarker = j;
        this.mTodoMediaManager = new TodoMediaManager(context, account);
    }

    public boolean sync() throws SyncException, JSONException, CloudServerException, RetriableSyncException, ReSyncException {
        Logger.INSTANCE.d(TAG, "start sync");
        if (this.mSyncCanceled) {
            Logger.INSTANCE.w(TAG, "Current sync is canceled");
            return false;
        }
        TodoProviderAccessUtils.clearItemWithoutContentSync();
        downloadSort();
        download();
        PreferenceUtils.setAddTodoIntroduction(NoteApp.getInstance(), true);
        TodoProviderAccessUtils.clearItemWithoutContentSync();
        downloadAccessory();
        uploadAccessory();
        upload();
        if (this.isNeedUploadAgain) {
            upload();
            this.isNeedUploadAgain = false;
        }
        if (this.isNeedUploadSort) {
            uploadSort();
        }
        updateMaxSort();
        AlarmReceiver.setupTodoAlarms(NoteApp.getInstance());
        if (!this.mSyncCanceled) {
            return true;
        }
        Logger.INSTANCE.w(TAG, "Current sync is canceled");
        return false;
    }

    private void downloadAccessory() throws Throwable {
        Cursor cursorQueryAllItem = TodoProviderAccessUtils.queryAllItem();
        if (cursorQueryAllItem != null) {
            while (cursorQueryAllItem.moveToNext()) {
                try {
                    this.mTodoMediaManager.download(TodoDataUtils.getTodoEntity(cursorQueryAllItem));
                } catch (NoteSyncException e) {
                    if (e.getInnerException() != null) {
                        Logger.INSTANCE.i(TAG, "NoteSyncException in download " + e.getInnerException().toString());
                    } else {
                        Logger.INSTANCE.i(TAG, "NoteSyncException in download " + e.getMessage());
                    }
                } catch (Exception e2) {
                    Logger.INSTANCE.i(TAG, "error in download " + e2);
                }
            }
            cursorQueryAllItem.close();
        }
    }

    private void uploadAccessory() {
        Cursor cursorQueryNeedUploadAccessory = TodoProviderAccessUtils.queryNeedUploadAccessory();
        if (cursorQueryNeedUploadAccessory == null) {
            return;
        }
        while (cursorQueryNeedUploadAccessory.moveToNext()) {
            try {
                this.mTodoMediaManager.upload(TodoDataUtils.getTodoEntity(cursorQueryNeedUploadAccessory));
            } catch (NoteSyncException e) {
                if (e.getInnerException() != null) {
                    Logger.INSTANCE.i(TAG, "NoteSyncException in upload " + e.getInnerException().toString());
                } else {
                    Logger.INSTANCE.i(TAG, "NoteSyncException in upload " + e.getMessage());
                }
            } catch (Exception e2) {
                Logger.INSTANCE.i(TAG, "error in upload " + e2.toString());
            }
        }
        cursorQueryNeedUploadAccessory.close();
    }

    private void download() throws SyncException, CloudServerException, RetriableSyncException, ReSyncException {
        DownloadOperation.Result resultDownload;
        Logger.INSTANCE.d(TAG, "TodoSyncManager download start");
        do {
            resultDownload = SYNC_FACTORY.download(this.mToken);
            Logger.INSTANCE.i(TAG, "TodoSyncManager download" + resultDownload.isSuccess() + StringUtils.SPACE + resultDownload.getRecordCount() + StringUtils.SPACE + this.mToken + StringUtils.SPACE + resultDownload.hasMore());
            if (resultDownload.isSuccess()) {
                this.mToken = resultDownload.getSyncToken();
                this.mWaterMarker = resultDownload.getWatermark();
                for (int i = 0; i < resultDownload.getRecordCount(); i++) {
                    TodoEntity record = SyncHelper.parseRecord(resultDownload.getRecordAt(i));
                    if (record != null) {
                        Logger.INSTANCE.i(TAG, "TodoSyncManager download SyncId:" + record.getSyncId() + " syncTag:" + record.getSyncEtag() + " content size:" + (record.getContent() != null ? record.getContent().length() : 0));
                        try {
                            saveServerToLocal(record);
                        } catch (Exception e) {
                            Logger.INSTANCE.i(TAG, "saveServerToLocal Exception:" + e);
                        }
                    }
                }
                SyncHelper.saveSyncInfoToLocal(this.mAccount, this.mToken, this.mWaterMarker);
            } else {
                if (resultDownload.getErrorType() == Constants.ErrorType.NEED_RESYNC) {
                    throw new ReSyncException("fail to download,NEED_RESYNC");
                }
                if (resultDownload.getErrorType() == Constants.ErrorType.RETRIABLE_ERROR) {
                    throw new RetriableSyncException("fail to download,RETRIABLE_ERROR", resultDownload.getRetryTime());
                }
                throw new SyncException("fail to download,error type:" + resultDownload.getErrorType());
            }
        } while (resultDownload.hasMore());
        Logger.INSTANCE.d(TAG, "TodoSyncManager download end");
    }

    private void upload() throws Throwable {
        Cursor cursorQueryNeedUpdateItem;
        Logger.INSTANCE.d(TAG, "TodoSyncManager upload start");
        try {
            cursorQueryNeedUpdateItem = TodoProviderAccessUtils.queryNeedUpdateItem();
            if (cursorQueryNeedUpdateItem == null) {
                if (cursorQueryNeedUpdateItem != null) {
                    cursorQueryNeedUpdateItem.close();
                    return;
                }
                return;
            }
            try {
                Logger.INSTANCE.i(TAG, "update size:" + cursorQueryNeedUpdateItem.getCount());
                while (cursorQueryNeedUpdateItem.moveToNext()) {
                    TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQueryNeedUpdateItem);
                    String content = todoEntity.getContent();
                    String content2 = todoEntity.getContent();
                    Logger.INSTANCE.i(TAG, "update getLocalStatus " + todoEntity.getLocalStatus() + " content:" + (todoEntity.getContent() != null ? todoEntity.getContent().length() : 0));
                    if (todoEntity.getLocalStatus() == 2 && TextUtils.isEmpty(todoEntity.getContent())) {
                        Logger.INSTANCE.i(TAG, "TodoSyncManager NEED_UPDATE to newDeleteOp content is null");
                        todoEntity.setLocalStatus(3);
                        TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
                    }
                    JSONObject json = todoEntity.toJson();
                    if (content != null) {
                        String strSha1 = NoteE2EEManager.sha1(content);
                        encryptData(json, content, content2);
                        json.put(NotesConstants.JSON_KEY_CONTENTHA1, strSha1);
                    }
                    int localStatus = todoEntity.getLocalStatus();
                    if (localStatus != 0) {
                        if (localStatus != 2) {
                            if (localStatus == 3) {
                                Logger.INSTANCE.i(TAG, "TodoSyncManager newDeleteOp " + (todoEntity.getContent() != null ? todoEntity.getContent().length() : 0) + " syncId " + todoEntity.getSyncId() + " eTag " + todoEntity.getSyncEtag());
                                if (todoEntity.getSyncId() <= 100000000) {
                                    TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
                                } else {
                                    parseAndSaveResponseResult(SYNC_FACTORY.newDeleteOp("entity", todoEntity.getSyncId() + "", todoEntity.getSyncEtag()).execute(), todoEntity, 3);
                                }
                            } else {
                                Logger.INSTANCE.i(TAG, "error while want to upload entity in error  localStatus" + todoEntity.getLocalStatus());
                            }
                        } else if (todoEntity.getSyncId() > 100000000 && todoEntity.getSyncEtag() > 0) {
                            Logger.INSTANCE.i(TAG, "TodoSyncManager newUpdateOp " + (todoEntity.getContent() != null ? todoEntity.getContent().length() : 0) + " syncId " + todoEntity.getSyncId() + " eTag " + todoEntity.getSyncEtag());
                            UploadOperation.Result resultExecute = SYNC_FACTORY.newUpdateOp("entity", todoEntity.getSyncId() + "", null, null, json, todoEntity.getSyncEtag()).execute();
                            if (resultExecute.getErrorCode() == 11001) {
                                retryEncryptData(json, content, content2);
                            }
                            parseAndSaveResponseResult(resultExecute, todoEntity, 2);
                        } else {
                            SyncHelper.cleanSyncInfo(todoEntity.getId());
                        }
                    } else if (todoEntity.getSyncId() >= 100000000) {
                        Logger.INSTANCE.i(TAG, "error while want to create entity again " + todoEntity.getSyncId());
                    } else if (TextUtils.isEmpty(todoEntity.getContent())) {
                        TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
                    } else {
                        UploadOperation.Result resultExecute2 = SYNC_FACTORY.newCreateOp("entity", null, null, null, json).execute();
                        Logger.INSTANCE.i(TAG, "TodoSyncManager newCreateOp content:" + (todoEntity.getContent() != null ? todoEntity.getContent().length() : 0) + " success:" + resultExecute2.isSuccess());
                        if (resultExecute2.getErrorCode() == 11001) {
                            retryEncryptData(json, content, content2);
                        }
                        parseAndSaveResponseResult(resultExecute2, todoEntity, 1);
                    }
                }
                if (cursorQueryNeedUpdateItem != null) {
                    cursorQueryNeedUpdateItem.close();
                }
            } catch (Throwable th) {
                th = th;
                if (cursorQueryNeedUpdateItem != null) {
                    cursorQueryNeedUpdateItem.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            cursorQueryNeedUpdateItem = null;
        }
    }

    private void encryptData(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (NoteE2EEManager.isE2EEOpen()) {
            KeyEntry keyEntry = NoteE2EEManager.getKeyEntry();
            if (keyEntry == null) {
                jSONObject.put("encryptInfo", "");
                return;
            }
            jSONObject.put("content", NoteE2EEManager.encryptData(keyEntry, str));
            jSONObject.put("plainText", NoteE2EEManager.encryptData(keyEntry, str2));
            jSONObject.put("encryptInfo", keyEntry.toServerJson());
        }
    }

    private void retryEncryptData(JSONObject jSONObject, String str, String str2) throws JSONException {
        NoteE2EEManager.setAppKeyExpired();
        encryptData(jSONObject, str, str2);
    }

    private void saveServerToLocal(TodoEntity todoEntity) throws SyncException {
        Cursor cursorQueryPlainTextItem;
        boolean z;
        Cursor cursorQueryItemBySyncId = TodoProviderAccessUtils.queryItemBySyncId(todoEntity.getSyncId());
        if (cursorQueryItemBySyncId == null || cursorQueryItemBySyncId.getCount() == 0) {
            Logger.INSTANCE.d(TAG, "TodoSyncManager saveServerToLocal not has syncId plainText:" + (todoEntity.getPlainText() != null ? todoEntity.getPlainText().length() : 0));
            if (todoEntity.getListType() == 0) {
                Logger.INSTANCE.i(TAG, "TodoSyncManager Todo.ListType.COMMON ");
                cursorQueryPlainTextItem = TodoProviderAccessUtils.queryContentItem(todoEntity.getContent());
            } else {
                cursorQueryPlainTextItem = TodoProviderAccessUtils.queryPlainTextItem(todoEntity.getPlainText());
            }
            if (cursorQueryPlainTextItem != null) {
                while (true) {
                    if (!cursorQueryPlainTextItem.moveToNext()) {
                        z = false;
                        break;
                    }
                    Logger.INSTANCE.i(TAG, "TodoSyncManager moveToNext ");
                    TodoEntity todoEntity2 = TodoDataUtils.getTodoEntity(cursorQueryPlainTextItem);
                    if (todoEntity2.getCreateTime() == todoEntity.getCreateTime()) {
                        Logger.INSTANCE.i(TAG, "TodoSyncManager localTodoEntity.getCreateTime() == serverTodoEntity.getCreateTime() ");
                        updateServerToLoacl(SyncHelper.changeServerEntityToSave(todoEntity, todoEntity2), todoEntity2);
                        z = true;
                        break;
                    }
                }
                cursorQueryPlainTextItem.close();
            } else {
                z = false;
            }
            Logger.INSTANCE.i(TAG, "TodoSyncManager isSameTodo " + z);
            if (!z && todoEntity.getServerStatus() == 0) {
                todoEntity.setLocalStatus(1);
                todoEntity.setCustomSortId(DragSort.getBigSortId());
                Logger.INSTANCE.i(TAG, "saveServerToLocal addItem plainText:" + (todoEntity.getPlainText() != null ? todoEntity.getPlainText().length() : 0));
                TodoProviderAccessUtils.addItem(todoEntity, true);
            }
        } else {
            cursorQueryItemBySyncId.moveToFirst();
            TodoEntity todoEntity3 = TodoDataUtils.getTodoEntity(cursorQueryItemBySyncId);
            todoEntity.setId(todoEntity3.getId());
            if (cursorQueryItemBySyncId.getInt(50) == 4) {
                Logger.INSTANCE.i(TAG, "TodoSyncManager saveServerToLocal  has syncId and LocalStatus is JUST_SORT");
                Cursor cursorQueryItemByCreateTime = TodoProviderAccessUtils.queryItemByCreateTime(todoEntity.getCreateTime());
                if (cursorQueryItemByCreateTime != null && cursorQueryItemByCreateTime.getCount() != 0) {
                    Logger.INSTANCE.i(TAG, "TodoSyncManager saveServerToLocal  has syncId and LocalStatus has same createTime");
                    cursorQueryItemByCreateTime.moveToFirst();
                    TodoEntity todoEntity4 = TodoDataUtils.getTodoEntity(cursorQueryItemByCreateTime);
                    todoEntity.setId(todoEntity4.getId());
                    if (todoEntity4.getServerStatus() != 3) {
                        todoEntity4.setServerStatus(2);
                    }
                    if (todoEntity4.getLocalStatus() != 3) {
                        todoEntity4.setLocalStatus(2);
                    }
                    updateServerToLoacl(SyncHelper.changeServerEntityToUpdate(todoEntity, todoEntity4), todoEntity4);
                    TodoProviderAccessUtils.deleteItem(cursorQueryItemBySyncId.getInt(0), true);
                } else if (todoEntity.getServerStatus() != 0) {
                    TodoProviderAccessUtils.deleteItem(cursorQueryItemBySyncId.getInt(0), true);
                } else {
                    updateServerToLoacl(SyncHelper.changeServerEntityToSave(todoEntity, todoEntity3), todoEntity3);
                    TodoProviderAccessUtils.updateItem(todoEntity, true);
                }
                if (cursorQueryItemByCreateTime != null) {
                    cursorQueryItemByCreateTime.close();
                }
            } else {
                Logger.INSTANCE.i(TAG, "TodoSyncManager saveServerToLocal  has syncId and LocalStatus is not JUST_SORT");
                int localStatus = todoEntity3.getLocalStatus();
                if ((localStatus != 2 && localStatus != 3) || todoEntity3.getLastModifiedTime() <= todoEntity.getLastModifiedTime()) {
                    Logger.INSTANCE.i(TAG, "TodoSyncManager No use local ");
                    updateServerToLoacl(SyncHelper.changeServerEntityToSave(todoEntity, todoEntity3), todoEntity3);
                } else if (todoEntity.getServerStatus() == 1) {
                    Logger.INSTANCE.i(TAG, "TodoSyncManager conflict delete local ");
                    TodoProviderAccessUtils.deleteItem(todoEntity3.getId(), true);
                } else {
                    todoEntity3.setSyncEtag(todoEntity.getSyncEtag());
                    todoEntity3.setSyncId(todoEntity.getSyncId());
                    Logger.INSTANCE.i(TAG, "TodoSyncManager conflict only update syncInfo ");
                    TodoProviderAccessUtils.updateItem(todoEntity3, true);
                }
            }
            while (cursorQueryItemBySyncId.moveToNext()) {
                TodoProviderAccessUtils.deleteItem(TodoDataUtils.getTodoEntity(cursorQueryItemBySyncId).getId(), true);
            }
        }
        if (cursorQueryItemBySyncId != null) {
            cursorQueryItemBySyncId.close();
        }
    }

    private void updateServerToLoacl(TodoEntity todoEntity, TodoEntity todoEntity2) {
        int serverStatus = todoEntity.getServerStatus();
        if (serverStatus != 0) {
            if (serverStatus == 1) {
                handleServerDelete(todoEntity, todoEntity2);
                return;
            } else {
                if (serverStatus != 2) {
                    return;
                }
                handleServerDelete(todoEntity, todoEntity2);
                return;
            }
        }
        int serverStatus2 = todoEntity2.getServerStatus();
        if (serverStatus2 == 0) {
            TodoProviderAccessUtils.updateItem(todoEntity, true);
            return;
        }
        if (serverStatus2 == 1) {
            TodoProviderAccessUtils.updateItem(todoEntity, true);
            return;
        }
        if (serverStatus2 == 2) {
            if (todoEntity.getLastModifiedTime() > todoEntity2.getLastModifiedTime()) {
                TodoProviderAccessUtils.updateItem(todoEntity, true);
                return;
            }
            todoEntity2.setSyncId(todoEntity.getSyncId());
            todoEntity2.setSyncEtag(todoEntity.getSyncEtag());
            TodoProviderAccessUtils.updateItem(todoEntity2, true);
            return;
        }
        if (serverStatus2 != 3) {
            return;
        }
        if (SyncHelper.isContentSame(todoEntity2, todoEntity)) {
            todoEntity2.setSyncId(todoEntity.getSyncId());
            todoEntity2.setLocalStatus(3);
            TodoProviderAccessUtils.updateItem(todoEntity2, true);
            return;
        }
        TodoProviderAccessUtils.updateItem(todoEntity, true);
    }

    private void downloadSort() throws SyncException {
        Logger.INSTANCE.i(TAG, "TodoSyncManager downloadSort start");
        try {
            this.isNeedUploadSort = false;
            FetchRecordOperation.Result resultFetchRecord = SYNC_FACTORY.fetchRecord("0");
            if (resultFetchRecord.isSuccess()) {
                try {
                    JSONObject jSONObject = resultFetchRecord.getRecord().contentJson.getJSONObject("sort");
                    JSONArray jSONArray = jSONObject.getJSONArray("orders");
                    long j = jSONObject.getLong("eTag");
                    long sortEtag = PreferenceUtils.getSortEtag();
                    localOrder();
                    Logger.INSTANCE.i(TAG, "TodoSyncManager downloadSort localEtag " + sortEtag + " serverEtag " + j + StringUtils.SPACE + jSONArray);
                    List<Long> list = (List) this.mGson.fromJson(jSONArray.toString(), new TypeToken<List<Long>>() { // from class: com.miui.todo.cloudservice.TodoSyncManager.1
                    }.getType());
                    this.serverOrdersList = list;
                    if (list != null && !list.isEmpty()) {
                        if (PreferenceUtils.getIsFirstTodoSync(NoteApp.getInstance())) {
                            Logger.INSTANCE.i(TAG, "TodoSyncManager deleteGuideTodo ");
                            GuideUtils.deleteGuideTodo();
                            PreferenceUtils.setIsFirstTodoSync(NoteApp.getInstance(), false);
                            PreferenceUtils.setAddTodoIntroduction(NoteApp.getInstance(), true);
                        }
                        if (!PreferenceUtils.getPrefDeleteTodoGuide(NoteApp.getInstance())) {
                            Logger.INSTANCE.i(TAG, "TodoSyncManager deleteGuideTodo again");
                            try {
                                PreferenceUtils.setPrefDeleteTodoGuide(NoteApp.getInstance(), true);
                                GuideUtils.updateGuideTodo();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Logger.INSTANCE.i(TAG, "TodoSyncManager deleteGuideTodo again error:" + e);
                            }
                        }
                    }
                    if (sortEtag != j) {
                        PreferenceUtils.setSortEtag(j);
                        List<Long> list2 = this.serverOrdersList;
                        if (list2 != null && list2.size() != 0) {
                            SyncHelper.rolveSort(this.serverOrdersList);
                        }
                    }
                    this.isNeedUploadSort = true;
                    if (this.serverOrdersList != null) {
                        for (int i = 0; i < this.serverOrdersList.size(); i++) {
                            Logger.INSTANCE.d(TAG, "serverOrdersList i:" + i + this.serverOrdersList.get(i));
                            if (this.serverOrdersList.get(i).longValue() < 100000000) {
                                this.isNeedUploadSort = false;
                                break;
                            }
                        }
                    }
                } catch (JSONException e2) {
                    Logger.INSTANCE.i(TAG, "downloadSort Json error,expcetion:" + e2);
                    throw new SyncException("downloadSort error");
                }
            } else {
                Logger.INSTANCE.i(TAG, "download Sort is not Success " + resultFetchRecord.getDescription());
            }
            Logger.INSTANCE.i(TAG, "TodoSyncManager downloadSort end");
        } catch (Throwable th) {
            Logger.INSTANCE.i(TAG, "TodoSyncManager downloadSort end");
            throw th;
        }
    }

    private void localOrder() {
        this.localOrdersList = new ArrayList();
        Cursor cursorQueryAllUnFinishItemReverse = TodoProviderAccessUtils.queryAllUnFinishItemReverse();
        if (cursorQueryAllUnFinishItemReverse == null) {
            return;
        }
        if (cursorQueryAllUnFinishItemReverse.getCount() == 0) {
            cursorQueryAllUnFinishItemReverse.close();
            return;
        }
        while (cursorQueryAllUnFinishItemReverse.moveToNext()) {
            this.localOrdersList.add(Long.valueOf(cursorQueryAllUnFinishItemReverse.getLong(18)));
        }
        cursorQueryAllUnFinishItemReverse.close();
    }

    private void uploadSort() throws SyncException {
        boolean z;
        SyncRecord record;
        long j;
        Logger.INSTANCE.i(TAG, "TodoSyncManager uploadSort ");
        ArrayList arrayList = new ArrayList();
        Cursor cursorQueryAllUnFinishItemReverse = TodoProviderAccessUtils.queryAllUnFinishItemReverse();
        if (cursorQueryAllUnFinishItemReverse == null) {
            return;
        }
        if (cursorQueryAllUnFinishItemReverse.getCount() == 0) {
            cursorQueryAllUnFinishItemReverse.close();
            return;
        }
        while (true) {
            z = true;
            if (cursorQueryAllUnFinishItemReverse.moveToNext()) {
                long j2 = cursorQueryAllUnFinishItemReverse.getLong(18);
                if (j2 < 100000000 || arrayList.contains(Long.valueOf(j2))) {
                    Logger.INSTANCE.d(TAG, "cleanSyncInfo:" + j2);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("sync_id", (Long) 0L);
                    contentValues.put(Todo.SYNC_ETAG, (Long) 0L);
                    if (arrayList.contains(Long.valueOf(j2))) {
                        contentValues.put(Todo.CREATE_TIME, Long.valueOf(System.currentTimeMillis()));
                    }
                    contentValues.put("local_status", (Integer) 0);
                    TodoProviderAccessUtils.updateItem(cursorQueryAllUnFinishItemReverse.getLong(0), contentValues, true);
                } else {
                    arrayList.add(Long.valueOf(j2));
                }
            } else {
                try {
                    break;
                } catch (Exception e) {
                    Logger.INSTANCE.i(TAG, "compareAndReset error:" + e);
                }
            }
        }
        compareAndReset(arrayList);
        long sortEtag = PreferenceUtils.getSortEtag();
        Logger.INSTANCE.d(TAG, "upload sort local tag:" + sortEtag);
        try {
            UploadOperation.Result resultExecute = SYNC_FACTORY.newUpdateOp("sort", "0", null, null, new JSONObject(this.mGson.toJson(new SortEntity(sortEtag, arrayList))), 0L).execute();
            if (!resultExecute.isSuccess()) {
                Logger.INSTANCE.i(TAG, "uploadSort Sort fail " + resultExecute.getErrorType() + StringUtils.SPACE + resultExecute.getDescription());
                return;
            }
            try {
                if (resultExecute.getConflictType() == Constants.ConflictType.ETAG) {
                    record = resultExecute.getConflictRecord();
                    j = record.contentJson.getJSONObject("sort").getLong("eTag");
                } else {
                    record = resultExecute.getRecord();
                    z = false;
                    j = record.contentJson.getJSONObject("sort").getLong("eTag");
                }
                Logger.INSTANCE.i(TAG, "TodoSyncManager  uploadSort SuccessserverEtag " + j + " isConflict " + z + StringUtils.SPACE + record.contentJson.getJSONObject("sort").getJSONArray("orders").toString());
                PreferenceUtils.setSortEtag(j);
                if (z) {
                    uploadSort();
                }
            } catch (JSONException e2) {
                Logger.INSTANCE.i(TAG, "uploadSort getRecord error,expcetion:" + e2);
                throw new SyncException("uploadSort getRecord error");
            }
        } catch (JSONException e3) {
            Logger.INSTANCE.i(TAG, "uploadSort Json error,expcetion:" + e3);
            throw new SyncException("downloadSort error");
        }
    }

    private void compareAndReset(List<Long> list) {
        List<Long> list2;
        Logger.INSTANCE.d(TAG, "ordersList:" + list + " localOrdersList:" + this.localOrdersList + " serverOrdersList:" + this.serverOrdersList);
        if (list == null || this.localOrdersList == null || (list2 = this.serverOrdersList) == null || list2.isEmpty() || list.size() != this.localOrdersList.size()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).longValue() != this.localOrdersList.get(i).longValue()) {
                return;
            }
        }
        if (this.localOrdersList.size() < this.serverOrdersList.size()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.localOrdersList.size(); i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.serverOrdersList.size()) {
                    arrayList.add(this.localOrdersList.get(i2));
                    break;
                } else if (this.localOrdersList.get(i2).longValue() == this.serverOrdersList.get(i3).longValue()) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        Logger.INSTANCE.d(TAG, "resultList:" + arrayList);
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            Cursor cursorQueryItemBySyncId = TodoProviderAccessUtils.queryItemBySyncId(((Long) arrayList.get(i4)).longValue());
            if (cursorQueryItemBySyncId != null && cursorQueryItemBySyncId.getCount() > 0) {
                cursorQueryItemBySyncId.moveToFirst();
                SyncHelper.cleanSyncInfo(TodoDataUtils.getTodoEntity(cursorQueryItemBySyncId).getId());
            }
            if (cursorQueryItemBySyncId != null) {
                cursorQueryItemBySyncId.close();
            }
        }
    }

    private boolean parseAndSaveResponseResult(UploadOperation.Result result, TodoEntity todoEntity, int i) throws SyncException {
        if (!result.isSuccess()) {
            Logger.INSTANCE.i(TAG, "fail to upload at " + todoEntity.getId() + StringUtils.SPACE + result.getDescription() + StringUtils.SPACE + result.getErrorType());
            return false;
        }
        if (result.getConflictType() == Constants.ConflictType.UNIQUE_KEY) {
            if (i == 1) {
                Logger.INSTANCE.i(TAG, "TodoSyncManager SYNC_OPERATION.CREATE ");
                resolveConflictUpload(result, todoEntity);
                return true;
            }
            Logger.INSTANCE.i(TAG, "error ConflictType for UNIQUE_KEY " + i);
            return false;
        }
        if (result.getConflictType() == Constants.ConflictType.ETAG) {
            if (i == 2) {
                resolveConflictUpload(result, todoEntity);
                return true;
            }
            if (i == 3) {
                resolveConflictDelete(result, todoEntity);
                return true;
            }
            Logger.INSTANCE.i(TAG, "error ConflictType for ETAG " + i);
            return false;
        }
        if (result.getConflictType() == Constants.ConflictType.ID) {
            Logger.INSTANCE.i(TAG, "error ConflictType for ID");
            return false;
        }
        TodoEntity record = SyncHelper.parseRecord(result.getRecord());
        if (record == null) {
            return false;
        }
        record.setId(todoEntity.getId());
        if (todoEntity.getSyncId() < 100000000) {
            TodoEntity todoEntityQueryItemEntity = TodoProviderAccessUtils.queryItemEntity(todoEntity.getId());
            if (todoEntityQueryItemEntity != null && todoEntityQueryItemEntity.getLastModifiedTime() != todoEntity.getLastModifiedTime()) {
                todoEntityQueryItemEntity.setSyncId(record.getSyncId());
                todoEntityQueryItemEntity.setSyncEtag(record.getSyncEtag());
                todoEntityQueryItemEntity.setLocalStatus(2);
                TodoProviderAccessUtils.updateItem(todoEntityQueryItemEntity, true);
            } else {
                record.setLocalStatus(1);
                TodoProviderAccessUtils.updateItem(record, true);
            }
        } else if (todoEntity.getLocalStatus() == 2) {
            if (result.getConflictType() == Constants.ConflictType.NONE || record.getLastModifiedTime() > todoEntity.getLastModifiedTime()) {
                record.setLocalStatus(1);
                TodoProviderAccessUtils.updateItem(record, true);
            } else {
                todoEntity.setSyncEtag(record.getSyncEtag());
                record.setLocalStatus(2);
                TodoProviderAccessUtils.updateItem(todoEntity, true);
            }
        } else if (todoEntity.getLocalStatus() == 3) {
            if (result.getConflictType() == Constants.ConflictType.NONE) {
                TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
            } else {
                int serverStatus = record.getServerStatus();
                if (serverStatus != 0) {
                    if (serverStatus == 1 || serverStatus == 2) {
                        TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
                    }
                } else if (SyncHelper.isContentSame(todoEntity, record)) {
                    TodoProviderAccessUtils.deleteItem(todoEntity.getId(), true);
                } else {
                    record.setLocalStatus(1);
                    TodoProviderAccessUtils.updateItem(record, true);
                }
            }
        }
        return true;
    }

    private void resolveConflictUpload(UploadOperation.Result result, TodoEntity todoEntity) throws SyncException {
        TodoEntity record = SyncHelper.parseRecord(result.getConflictRecord());
        if (record == null) {
            return;
        }
        if (record.getLastModifiedTime() >= todoEntity.getLastModifiedTime()) {
            updateServerToLoacl(SyncHelper.changeServerEntityToSave(record, todoEntity), todoEntity);
            return;
        }
        todoEntity.setSyncId(record.getSyncId());
        todoEntity.setSyncEtag(record.getSyncEtag());
        todoEntity.setLocalStatus(2);
        TodoProviderAccessUtils.updateItem(todoEntity, true);
        this.isNeedUploadAgain = true;
    }

    private void resolveConflictDelete(UploadOperation.Result result, TodoEntity todoEntity) throws SyncException {
        TodoEntity record = SyncHelper.parseRecord(result.getConflictRecord());
        if (record == null) {
            return;
        }
        if (record.getServerStatus() != 0) {
            todoEntity.setSyncId(record.getSyncId());
            todoEntity.setSyncEtag(record.getSyncEtag());
            todoEntity.setLocalStatus(3);
            TodoProviderAccessUtils.updateItem(todoEntity, true);
            this.isNeedUploadAgain = true;
            return;
        }
        SyncHelper.cleanEntitySyncInfo(todoEntity);
        this.isNeedUploadAgain = true;
    }

    private void handleServerDelete(TodoEntity todoEntity, TodoEntity todoEntity2) {
        if (todoEntity2.getLocalStatus() != 3) {
            if (SyncHelper.isContentSame(todoEntity2, todoEntity)) {
                TodoProviderAccessUtils.deleteItem(todoEntity2.getId(), true);
                return;
            } else {
                SyncHelper.cleanEntitySyncInfo(todoEntity2);
                return;
            }
        }
        TodoProviderAccessUtils.deleteItem(todoEntity2.getId(), true);
    }

    void setSyncCanceled(boolean z) {
        this.mSyncCanceled = z;
    }

    private void updateMaxSort() {
        Logger.INSTANCE.i(TAG, "updateMaxSort");
        Cursor cursorQueryAllUnFinishItemReverse = TodoProviderAccessUtils.queryAllUnFinishItemReverse();
        if (cursorQueryAllUnFinishItemReverse != null && cursorQueryAllUnFinishItemReverse.getCount() > 0) {
            cursorQueryAllUnFinishItemReverse.moveToFirst();
            PreferenceUtils.setMaxSortID(TodoDataUtils.getTodoEntity(cursorQueryAllUnFinishItemReverse).getCustomSortId());
        }
        if (cursorQueryAllUnFinishItemReverse != null) {
            cursorQueryAllUnFinishItemReverse.close();
        }
    }
}
