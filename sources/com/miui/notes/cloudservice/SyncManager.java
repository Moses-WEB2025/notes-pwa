package com.miui.notes.cloudservice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteFullException;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.model.NoteModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.utils.SerializableUtils;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.micloudsdk.exception.CipherException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.CloudRequestHelper;
import com.xiaomi.micloudsdk.request.utils.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import miui.cloud.CloudSyncUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SyncManager {
    public static final String MICLOUD_NOTE_SYNC = "micloud.note.sync";
    public static final String NOTES_VERSIONCODE = String.valueOf(2437);
    private static final String TAG = "Notes:SyncManager";
    private final Context mContext;
    private final Set<String> mFilterTags;
    private NoteSyncException mLastException;
    private final NoteSyncContext mSyncContext;
    private String mSyncExtraInfo;
    private String mSyncTag;

    public SyncManager(NoteSyncContext noteSyncContext) {
        this.mSyncContext = noteSyncContext;
        this.mContext = noteSyncContext.getContext();
        this.mFilterTags = noteSyncContext.getFilterTags();
    }

    public void fireException() throws NoteSyncException {
        NoteSyncException noteSyncException = this.mLastException;
        if (noteSyncException == null) {
            return;
        }
        this.mLastException = null;
        throw noteSyncException;
    }

    private void logException(NoteSyncException noteSyncException) {
        if (this.mLastException != null) {
            Logger.INSTANCE.e(TAG, "updateException: previous exception is overwrite,exception:" + this.mLastException);
            noteSyncException.setRecoverable(this.mLastException.isRecoverable() && noteSyncException.isRecoverable());
        }
        this.mLastException = noteSyncException;
    }

    /* JADX WARN: Code duplicated, block: B:88:0x01cd  */
    /* JADX WARN: Code duplicated, block: B:90:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:91:0x01f7  */
    public void pull() throws Throwable {
        int i;
        JSONObject jSONObjectPullData;
        int size = 0;
        try {
            try {
                Logger.INSTANCE.i(TAG, "pull start");
                if (!CloudSyncUtils.shouldPullFromServer(this.mContext, "micloud.note.sync", -1L, 0)) {
                    Logger.INSTANCE.i(TAG, "Cancel pull by CloudSyncUtils.shouldPullFromServer");
                } else {
                    int i2 = 0;
                    do {
                        try {
                            NotesUtils.checkInterrupted();
                            jSONObjectPullData = pullData();
                            Logger.INSTANCE.i(TAG, "pull In while ");
                            ArrayList<BaseEntity> entityArray = parseEntityArray(jSONObjectPullData);
                            size += entityArray.size();
                            for (Iterator<BaseEntity> it = entityArray.iterator(); it.hasNext(); it = it) {
                                BaseEntity next = it.next();
                                Logger.INSTANCE.i(TAG, "Pull entity: " + next.getSyncId());
                                next.persist();
                                this.mFilterTags.remove(next.getSyncTag());
                                i2++;
                                Logger.INSTANCE.i(TAG, "Pull entity success: " + next.getSyncId());
                            }
                            AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
                            if (!TextUtils.equals(this.mSyncTag, accountEntity.getSyncTag()) || !TextUtils.equals(this.mSyncExtraInfo, accountEntity.getSyncExtraInfo())) {
                                accountEntity.setSyncTag(this.mSyncTag);
                                accountEntity.setSyncExtraInfo(this.mSyncExtraInfo);
                                accountEntity.persist(this.mContext);
                            }
                        } catch (SQLiteFullException e) {
                            e = e;
                            throw new NoteSyncException(e);
                        } catch (AccessDeniedException e2) {
                            e = e2;
                            throw new NoteSyncException(e);
                        } catch (AuthenticationFailureException e3) {
                            e = e3;
                            throw new NoteSyncException(e);
                        } catch (InvalidResponseException e4) {
                            e = e4;
                            throw new NoteSyncException(e);
                        } catch (CipherException e5) {
                            e = e5;
                            throw new NoteSyncException(e);
                        } catch (IOException e6) {
                            e = e6;
                            throw new NoteSyncException(e);
                        } catch (BadPaddingException e7) {
                            e = e7;
                            throw new NoteSyncException(e);
                        } catch (IllegalBlockSizeException e8) {
                            e = e8;
                            throw new NoteSyncException(e);
                        } catch (JSONException e9) {
                            e = e9;
                            throw new NoteSyncException(e);
                        }
                    } while (!jSONObjectPullData.getBoolean(NotesConstants.JSON_KEY_LAST_PAGE));
                    Logger.INSTANCE.i(TAG, "Pull last page");
                    try {
                        CloudSyncUtils.clearShouldPullAndUpdateLocalWatermark(this.mSyncContext.getContext(), Long.parseLong(this.mSyncTag), "micloud.note.sync");
                    } catch (NumberFormatException unused) {
                        Logger.INSTANCE.e(TAG, "Number error: " + this.mSyncTag);
                    }
                    NoteEntity.resolveIsolated(this.mSyncContext);
                    NoteEntity.completeResync(this.mContext);
                    FolderEntity.completeResync(this.mContext);
                    if (size > 0) {
                        int i3 = (i2 * 100) / size;
                        if (i3 <= 100) {
                            Logger.INSTANCE.w(TAG, "pullSuccessRateOk: successRate=" + i3 + " dataPullSuccessSize =" + i2 + " dataPullSize =" + size);
                        } else {
                            Logger.INSTANCE.w(TAG, "pullSuccessRateError: successRate=" + i3 + " dataPullSuccessSize =" + i2 + " dataPullSize =" + size);
                        }
                    }
                }
                Logger.INSTANCE.i(TAG, "pull end");
            } catch (SQLiteFullException e10) {
                e = e10;
            } catch (AccessDeniedException e11) {
                e = e11;
            } catch (AuthenticationFailureException e12) {
                e = e12;
            } catch (InvalidResponseException e13) {
                e = e13;
            } catch (CipherException e14) {
                e = e14;
            } catch (IOException e15) {
                e = e15;
            } catch (BadPaddingException e16) {
                e = e16;
            } catch (IllegalBlockSizeException e17) {
                e = e17;
            } catch (JSONException e18) {
                e = e18;
            } catch (Throwable th) {
                th = th;
                if (0 > 0) {
                    i = (0 * 100) / 0;
                    if (i <= 100) {
                        Logger.INSTANCE.w(TAG, "pullSuccessRateOk: successRate=" + i + " dataPullSuccessSize =0 dataPullSize =0");
                    } else {
                        Logger.INSTANCE.w(TAG, "pullSuccessRateError: successRate=" + i + " dataPullSuccessSize =0 dataPullSize =0");
                    }
                }
                Logger.INSTANCE.i(TAG, "pull end");
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 > 0) {
                i = (0 * 100) / 0;
                if (i <= 100) {
                    Logger.INSTANCE.w(TAG, "pullSuccessRateOk: successRate=" + i + " dataPullSuccessSize =0 dataPullSize =0");
                } else {
                    Logger.INSTANCE.w(TAG, "pullSuccessRateError: successRate=" + i + " dataPullSuccessSize =0 dataPullSize =0");
                }
            }
            Logger.INSTANCE.i(TAG, "pull end");
            throw th;
        }
    }

    private JSONObject pullData() throws CipherException, BadPaddingException, AccessDeniedException, JSONException, CloudServerException, IllegalBlockSizeException, IOException, InvalidResponseException, NoteSyncException, AuthenticationFailureException {
        AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
        ExtendedAuthToken extToken = this.mSyncContext.getExtToken();
        String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_PULL, new Object[0]);
        HashMap map = new HashMap();
        map.put("syncTag", accountEntity.getSyncTag());
        map.put("limit", Long.toString(10L));
        map.put("syncExtraInfo", accountEntity.getSyncExtraInfo());
        map.put(NotesConstants.PARAM_KEY_APK_VERSION, NOTES_VERSIONCODE);
        CloudRequestHelper.addFilterTagsToParams(strConcatUrl, map, extToken.security, this.mFilterTags);
        return NoteSchemaUtils.getResponseDataObject(Request.secureGet(strConcatUrl, map));
    }

    private BaseEntity parseEntity(JSONObject jSONObject) throws JSONException, NoteSyncException {
        if ("note".equals(jSONObject.getString("type"))) {
            return NoteEntity.valueOf(this.mSyncContext, jSONObject);
        }
        return FolderEntity.valueOf(this.mSyncContext, jSONObject);
    }

    private ArrayList<BaseEntity> parseEntityArray(JSONObject jSONObject) throws JSONException, NoteSyncException {
        this.mSyncTag = jSONObject.getString("syncTag");
        this.mSyncExtraInfo = jSONObject.getString("syncExtraInfo");
        JSONArray jSONArray = jSONObject.getJSONArray(NotesConstants.JSON_KEY_ENTRIES);
        int length = jSONArray.length();
        ArrayList<BaseEntity> arrayList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            BaseEntity entity = parseEntity(jSONArray.getJSONObject(i));
            if (entity != null) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }

    public void push() throws CloudServerException, NoteSyncException {
        NoteBatchRequest noteBatchRequest = new NoteBatchRequest(this.mSyncContext);
        Cursor dirtyCursor = null;
        try {
            Logger.INSTANCE.i(TAG, "push start");
            Cursor dirtyCursor2 = NoteEntity.getDirtyCursor(this.mContext);
            Set<Long> unRetriableFolders = FolderEntity.getUnRetriableFolders(this.mContext);
            while (dirtyCursor2.moveToNext()) {
                NotesUtils.checkInterrupted();
                NoteEntity noteEntityValueOf = NoteEntity.valueOf(this.mSyncContext, dirtyCursor2);
                if (!unRetriableFolders.contains(Long.valueOf(noteEntityValueOf.getFolderId()))) {
                    try {
                        if (noteEntityValueOf.readySync()) {
                            try {
                                noteBatchRequest.add(noteEntityValueOf);
                            } catch (NoteSyncException e) {
                                logException(e);
                                noteBatchRequest.clear();
                            }
                        }
                    } catch (NoteSyncException e2) {
                        logException(e2);
                    }
                }
            }
            noteBatchRequest.commit();
            dirtyCursor2.close();
            dirtyCursor = FolderEntity.getDirtyCursor(this.mContext);
            while (dirtyCursor.moveToNext()) {
                NotesUtils.checkInterrupted();
                FolderEntity folderEntityValueOf = FolderEntity.valueOf(this.mSyncContext, dirtyCursor);
                if (folderEntityValueOf.readySync()) {
                    noteBatchRequest.add(folderEntityValueOf);
                }
            }
            noteBatchRequest.commit();
            dirtyCursor.close();
        } catch (Exception e3) {
            Logger.INSTANCE.i(TAG, "push exception :" + e3);
        } finally {
            Logger.INSTANCE.i(TAG, "push end");
            if (dirtyCursor != null) {
                dirtyCursor.close();
            }
        }
    }

    public void down() throws NoteSyncException {
        Logger.INSTANCE.i(TAG, "down start");
        Cursor fileMissedCursor = DataEntity.getFileMissedCursor(this.mContext);
        if (fileMissedCursor == null) {
            return;
        }
        NoteMediaManager mediaManager = this.mSyncContext.getMediaManager();
        while (fileMissedCursor.moveToNext()) {
            try {
                NotesUtils.checkInterrupted();
                DataEntity dataEntityValueOf = DataEntity.valueOf(fileMissedCursor);
                String accountName = this.mSyncContext.getAccountEntity().getAccountName();
                if (TextUtils.equals(dataEntityValueOf.getDataContentType(), "multi_handwrite")) {
                    if ((!TextUtils.isEmpty(dataEntityValueOf.getFileId()) || dataEntityValueOf.isNeedUpdate()) && dataEntityValueOf.getFileId().startsWith(accountName)) {
                        mediaManager.download(dataEntityValueOf);
                    } else {
                        Logger.INSTANCE.d(TAG, "file is null or not current user=== FileId:" + dataEntityValueOf.getFileId());
                    }
                } else if (!TextUtils.isEmpty(dataEntityValueOf.getFileId()) && dataEntityValueOf.getFileId().startsWith(accountName)) {
                    mediaManager.download(dataEntityValueOf);
                } else {
                    Logger.INSTANCE.d(TAG, "file is null or not current user=== FileId:" + dataEntityValueOf.getFileId());
                }
            } catch (Throwable th) {
                fileMissedCursor.close();
                Logger.INSTANCE.i(TAG, "down end");
                throw th;
            }
        }
        fileMissedCursor.close();
        Logger.INSTANCE.i(TAG, "down end");
    }

    public void upgrade() throws NoteSyncException {
        try {
            try {
                try {
                    try {
                        try {
                            Logger.INSTANCE.i(TAG, "upgrade start");
                            AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
                            String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_UPGRADE, new Object[0]);
                            HashMap map = new HashMap();
                            map.put("syncTag", accountEntity.getSyncTag());
                            map.put(NotesConstants.PARAM_KEY_APK_VERSION, NOTES_VERSIONCODE);
                            String strSecureGet = Request.secureGet(strConcatUrl, map);
                            Logger.INSTANCE.e(TAG, "upgrade: response=" + strSecureGet);
                            accountEntity.setSyncExtraInfo(NoteSchemaUtils.getResponseDataObject(strSecureGet).getString("syncExtraInfo"));
                            accountEntity.persist(this.mSyncContext.getContext());
                            Logger.INSTANCE.i(TAG, "upgrade end");
                        } catch (IOException e) {
                            throw new NoteSyncException(e);
                        } catch (JSONException e2) {
                            throw new NoteSyncException(e2);
                        }
                    } catch (BadPaddingException e3) {
                        throw new NoteSyncException(e3);
                    }
                } catch (IllegalBlockSizeException e4) {
                    throw new NoteSyncException(e4);
                }
            } catch (CloudServerException e5) {
                throw new NoteSyncException(e5);
            }
        } catch (Throwable th) {
            Logger.INSTANCE.i(TAG, "upgrade end");
            throw th;
        }
    }

    public void fullUpgrade() throws JSONException, NoteSyncException {
        JSONObject jSONObjectFullUpgradeData;
        try {
            try {
                try {
                    Logger.INSTANCE.i(TAG, "fullUpgrade start");
                    do {
                        NotesUtils.checkInterrupted();
                        jSONObjectFullUpgradeData = fullUpgradeData();
                        Iterator<BaseEntity> it = parseEntityArray(jSONObjectFullUpgradeData).iterator();
                        while (it.hasNext()) {
                            it.next().persist();
                        }
                    } while (!jSONObjectFullUpgradeData.getBoolean(NotesConstants.JSON_KEY_LAST_PAGE));
                    PreferenceUtils.setIsNeedFullUpgrade(false);
                    Logger.INSTANCE.i(TAG, "fullUpgrade end");
                } catch (IOException e) {
                    Logger.INSTANCE.i(TAG, "fullUpgrade IOException, " + e.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgrade end");
                } catch (BadPaddingException e2) {
                    Logger.INSTANCE.i(TAG, "fullUpgrade BadPaddingException," + e2.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgrade end");
                }
            } catch (CloudServerException e3) {
                Logger.INSTANCE.i(TAG, "fullUpgrade CloudServerException," + e3.getMessage());
                Logger.INSTANCE.i(TAG, "fullUpgrade end");
            } catch (IllegalBlockSizeException e4) {
                Logger.INSTANCE.i(TAG, "fullUpgrade IllegalBlockSizeException, " + e4.getMessage());
                Logger.INSTANCE.i(TAG, "fullUpgrade end");
            }
        } catch (Throwable th) {
            Logger.INSTANCE.i(TAG, "fullUpgrade end");
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004c  */
    public JSONObject fullUpgradeData() throws BadPaddingException, CloudServerException, JSONException, IllegalBlockSizeException, IOException, NoteSyncException {
        String str;
        Logger.INSTANCE.i(TAG, "fullUpgradeData start");
        AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
        String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_FULL_UPGRADE, new Object[0]);
        Object object = SerializableUtils.readObject(PreferenceUtils.PREFERENCE_MIND_FULL_UPGRADE_WATER_ENTITY);
        String str2 = "";
        if (object instanceof DifferenceFullUpgradeWater) {
            DifferenceFullUpgradeWater differenceFullUpgradeWater = (DifferenceFullUpgradeWater) object;
            if (TextUtils.equals(differenceFullUpgradeWater.accountName, accountEntity.getAccountName())) {
                String str3 = !TextUtils.isEmpty(differenceFullUpgradeWater.syncTag) ? differenceFullUpgradeWater.syncTag : "";
                str = TextUtils.isEmpty(differenceFullUpgradeWater.syncExtraInfo) ? "" : differenceFullUpgradeWater.syncExtraInfo;
                str2 = str3;
            } else {
                str = "";
            }
        } else {
            str = "";
        }
        Logger logger = Logger.INSTANCE;
        StringBuilder sbAppend = new StringBuilder("fullUpgradeData request start  syncTag=").append(str2).append(" syncExtraInfo=").append(str).append(" versionCode=");
        String str4 = NOTES_VERSIONCODE;
        logger.i(TAG, sbAppend.append(str4).toString());
        HashMap map = new HashMap();
        map.put("syncTag", str2);
        map.put(NotesConstants.PARAM_KEY_APK_VERSION, str4);
        map.put(NotesConstants.PARAM_KEY_UPGRADE_NOTE_TYPES, NoteModel.NoteType.TYPE_MIND);
        map.put("syncExtraInfo", str);
        String strSecureGet = Request.secureGet(strConcatUrl, map);
        Logger.INSTANCE.e(TAG, "upgrade: response=====");
        JSONObject responseDataObject = NoteSchemaUtils.getResponseDataObject(strSecureGet);
        String string = responseDataObject.getString("syncExtraInfo");
        String string2 = responseDataObject.getString("syncTag");
        DifferenceFullUpgradeWater differenceFullUpgradeWater2 = new DifferenceFullUpgradeWater();
        differenceFullUpgradeWater2.accountName = accountEntity.getAccountName();
        differenceFullUpgradeWater2.syncTag = string2;
        differenceFullUpgradeWater2.syncExtraInfo = string;
        SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_MIND_FULL_UPGRADE_WATER_ENTITY, differenceFullUpgradeWater2);
        return responseDataObject;
    }

    public void fullUpgradeWithHandwrite() throws JSONException, NoteSyncException {
        JSONObject jSONObjectFullUpgradeDataWithHandWrite;
        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite start");
        do {
            try {
                try {
                    try {
                        NotesUtils.checkInterrupted();
                        jSONObjectFullUpgradeDataWithHandWrite = fullUpgradeDataWithHandWrite();
                        Iterator<BaseEntity> it = parseEntityArray(jSONObjectFullUpgradeDataWithHandWrite).iterator();
                        while (it.hasNext()) {
                            it.next().persist();
                        }
                    } catch (IOException e) {
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite IOException, " + e.getMessage());
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
                        return;
                    } catch (BadPaddingException e2) {
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite BadPaddingException," + e2.getMessage());
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
                        return;
                    }
                } catch (CloudServerException e3) {
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite CloudServerException," + e3.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
                    return;
                } catch (IllegalBlockSizeException e4) {
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite IllegalBlockSizeException, " + e4.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
                    return;
                }
            } catch (Throwable th) {
                Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
                throw th;
            }
        } while (!jSONObjectFullUpgradeDataWithHandWrite.getBoolean(NotesConstants.JSON_KEY_LAST_PAGE));
        PreferenceUtils.setIsNeedFullUpgradeWithHandwrite(false);
        Logger.INSTANCE.i(TAG, "fullUpgradeWithHandwrite end");
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004c  */
    public JSONObject fullUpgradeDataWithHandWrite() throws BadPaddingException, CloudServerException, JSONException, IllegalBlockSizeException, IOException, NoteSyncException {
        String str;
        Logger.INSTANCE.i(TAG, "fullUpgradeDataWithHandWrite start");
        AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
        String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_FULL_UPGRADE, new Object[0]);
        Object object = SerializableUtils.readObject(PreferenceUtils.PREFERENCE_HAND_FULL_UPGRADE_WATER_ENTITY);
        String str2 = "";
        if (object instanceof DifferenceFullUpgradeWater) {
            DifferenceFullUpgradeWater differenceFullUpgradeWater = (DifferenceFullUpgradeWater) object;
            if (TextUtils.equals(differenceFullUpgradeWater.accountName, accountEntity.getAccountName())) {
                String str3 = !TextUtils.isEmpty(differenceFullUpgradeWater.syncTag) ? differenceFullUpgradeWater.syncTag : "";
                str = TextUtils.isEmpty(differenceFullUpgradeWater.syncExtraInfo) ? "" : differenceFullUpgradeWater.syncExtraInfo;
                str2 = str3;
            } else {
                str = "";
            }
        } else {
            str = "";
        }
        Logger logger = Logger.INSTANCE;
        StringBuilder sbAppend = new StringBuilder("fullUpgradeDataWithHandWrite request start  syncTag=").append(str2).append(" syncExtraInfo=").append(str).append(" versionCode=");
        String str4 = NOTES_VERSIONCODE;
        logger.e(TAG, sbAppend.append(str4).toString());
        HashMap map = new HashMap();
        map.put("syncTag", str2);
        map.put(NotesConstants.PARAM_KEY_APK_VERSION, str4);
        map.put(NotesConstants.PARAM_KEY_UPGRADE_NOTE_TYPES, "handwrite");
        map.put("syncExtraInfo", str);
        String strSecureGet = Request.secureGet(strConcatUrl, map);
        Logger.INSTANCE.e(TAG, "hand upgrade: response===");
        JSONObject responseDataObject = NoteSchemaUtils.getResponseDataObject(strSecureGet);
        String string = responseDataObject.getString("syncExtraInfo");
        String string2 = responseDataObject.getString("syncTag");
        DifferenceFullUpgradeWater differenceFullUpgradeWater2 = new DifferenceFullUpgradeWater();
        differenceFullUpgradeWater2.accountName = accountEntity.getAccountName();
        differenceFullUpgradeWater2.syncTag = string2;
        differenceFullUpgradeWater2.syncExtraInfo = string;
        SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_HAND_FULL_UPGRADE_WATER_ENTITY, differenceFullUpgradeWater2);
        Logger.INSTANCE.i(TAG, "fullUpgradeDataWithHandWrite end");
        return responseDataObject;
    }

    public void fullUpgradeWithMultiHandwrite() throws JSONException, NoteSyncException {
        JSONObject jSONObjectFullUpgradeDataWithMultiHandWrite;
        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite start");
        do {
            try {
                try {
                    try {
                        NotesUtils.checkInterrupted();
                        jSONObjectFullUpgradeDataWithMultiHandWrite = fullUpgradeDataWithMultiHandWrite();
                        Iterator<BaseEntity> it = parseEntityArray(jSONObjectFullUpgradeDataWithMultiHandWrite).iterator();
                        while (it.hasNext()) {
                            it.next().persist();
                        }
                    } catch (CloudServerException e) {
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite CloudServerException," + e.getMessage());
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
                        return;
                    } catch (IOException e2) {
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite IOException, " + e2.getMessage());
                        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
                        return;
                    }
                } catch (BadPaddingException e3) {
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite BadPaddingException," + e3.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
                    return;
                } catch (IllegalBlockSizeException e4) {
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite IllegalBlockSizeException, " + e4.getMessage());
                    Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
                    return;
                }
            } catch (Throwable th) {
                Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
                throw th;
            }
        } while (!jSONObjectFullUpgradeDataWithMultiHandWrite.getBoolean(NotesConstants.JSON_KEY_LAST_PAGE));
        Logger.INSTANCE.i(TAG, "JSON_KEY_LAST_PAGE is true");
        PreferenceUtils.setIsNeedFullUpgradeWithMultiHandwrite(false);
        Logger.INSTANCE.i(TAG, "fullUpgradeWithMultiHandwrite end");
    }

    public JSONObject fullUpgradeDataWithMultiHandWrite() throws BadPaddingException, CloudServerException, JSONException, IllegalBlockSizeException, IOException, NoteSyncException {
        String str;
        String str2;
        Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite start");
        AccountEntity accountEntity = this.mSyncContext.getAccountEntity();
        String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_FULL_UPGRADE, new Object[0]);
        Object object = SerializableUtils.readObject(PreferenceUtils.PREFERENCE_MULTI_HAND_FULL_UPGRADE_WATER_ENTITY);
        Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite object is ".concat(object == null ? "null" : "not null"));
        String str3 = "";
        if (object instanceof DifferenceFullUpgradeWater) {
            Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite object is DifferenceFullUpgradeWater");
            DifferenceFullUpgradeWater differenceFullUpgradeWater = (DifferenceFullUpgradeWater) object;
            Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite object is not null");
            if (TextUtils.equals(differenceFullUpgradeWater.accountName, accountEntity.getAccountName())) {
                Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite accountName right");
                if (TextUtils.isEmpty(differenceFullUpgradeWater.syncTag)) {
                    str2 = "";
                } else {
                    Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite syncTag is not empty");
                    str2 = differenceFullUpgradeWater.syncTag;
                }
                if (!TextUtils.isEmpty(differenceFullUpgradeWater.syncExtraInfo)) {
                    Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite syncExtraInfo is not empty");
                    str3 = differenceFullUpgradeWater.syncExtraInfo;
                }
                str = str3;
                str3 = str2;
            } else {
                Logger.INSTANCE.i(TAG, "fullUpgradeDataWithMultiHandWrite accountName wrong");
                str = "";
            }
        } else {
            str = "";
        }
        Logger logger = Logger.INSTANCE;
        StringBuilder sbAppend = new StringBuilder("fullUpgradeDataWithMultiHandWrite request start  syncTag=").append(str3).append(" syncExtraInfo=").append(str).append(" versionCode=");
        String str4 = NOTES_VERSIONCODE;
        logger.e(TAG, sbAppend.append(str4).toString());
        HashMap map = new HashMap();
        map.put("syncTag", str3);
        map.put(NotesConstants.PARAM_KEY_APK_VERSION, str4);
        map.put(NotesConstants.PARAM_KEY_UPGRADE_NOTE_TYPES, "multi_handwrite");
        map.put("syncExtraInfo", str);
        String strSecureGet = Request.secureGet(strConcatUrl, map);
        Logger.INSTANCE.e(TAG, "hand upgrade: response===");
        JSONObject responseDataObject = NoteSchemaUtils.getResponseDataObject(strSecureGet);
        String string = responseDataObject.getString("syncExtraInfo");
        String string2 = responseDataObject.getString("syncTag");
        DifferenceFullUpgradeWater differenceFullUpgradeWater2 = new DifferenceFullUpgradeWater();
        differenceFullUpgradeWater2.accountName = accountEntity.getAccountName();
        differenceFullUpgradeWater2.syncTag = string2;
        differenceFullUpgradeWater2.syncExtraInfo = string;
        SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_MULTI_HAND_FULL_UPGRADE_WATER_ENTITY, differenceFullUpgradeWater2);
        return responseDataObject;
    }
}
