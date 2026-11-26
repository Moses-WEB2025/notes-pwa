package com.miui.notes.cloudservice;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.provider.Notes;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AccountEntity {
    private static final int ACCOUNT_COLUMN_ACCOUNT_NAME_INDEX = 1;
    private static final int ACCOUNT_COLUMN_ACCOUNT_TYPE_INDEX = 2;
    private static final int ACCOUNT_COLUMN_DATA_INDEX = 3;
    private static final int ACCOUNT_COLUMN_ID_INDEX = 0;
    private static final String[] ACCOUNT_PROJECTIONS = {"_id", Notes.Account.ACCOUNT_NAME, Notes.Account.ACCOUNT_TYPE, "data"};
    private static final String TAG = "AccountEntity";
    private static final int UNRECOVERABLE_LIMIT = 10;
    private static long mAccountId;
    private Account mAccount;
    private final String mAccountName;
    private final String mAccountType;
    private AccountData mData;
    private long mId;

    public static AccountEntity getAccount(Context context, Account account) throws Throwable {
        AccountEntity accountEntityLoadAccount = loadAccount(context, account);
        if (accountEntityLoadAccount != null) {
            accountEntityLoadAccount.mAccount = account;
            mAccountId = accountEntityLoadAccount.mId;
        }
        return accountEntityLoadAccount;
    }

    public static long getAccountId() {
        return mAccountId;
    }

    private static AccountEntity loadAccount(Context context, Account account) throws Throwable {
        Throwable th;
        Cursor cursor = null;
        byte b = 0;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = Notes.Account.CONTENT_URI;
            String[] strArr = ACCOUNT_PROJECTIONS;
            Cursor cursorQuery = contentResolver.query(uri, strArr, null, null, null);
            try {
                if (cursorQuery == null) {
                    Logger.INSTANCE.e(TAG, "getInstance failed: cursor = null");
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                if (cursorQuery.getColumnCount() != strArr.length) {
                    Logger.INSTANCE.i(TAG, "getInstance failed: in privacy mode");
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                if (cursorQuery.getCount() == 1 && cursorQuery.moveToFirst()) {
                    AccountEntity accountEntity = new AccountEntity(cursorQuery.getString(1), cursorQuery.getString(2));
                    accountEntity.mId = cursorQuery.getLong(0);
                    accountEntity.mData = AccountData.parse(cursorQuery.getString(3));
                    if (accountEntity.mAccountName.equals(account.name) && accountEntity.mAccountType.equals(account.type)) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return accountEntity;
                    }
                }
                if (cursorQuery.getCount() > 0) {
                    Logger.INSTANCE.e(TAG, "getInstance failed: cursor.getCount() = " + cursorQuery.getCount());
                    if (!Notes.Utils.clearAccount(context, false)) {
                        Logger.INSTANCE.e(TAG, "getInstance failed: clear account failed");
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                }
                AccountEntity accountEntity2 = new AccountEntity(account.name, account.type);
                accountEntity2.mData = new AccountData();
                try {
                    accountEntity2.persist(context);
                    if (accountEntity2.mId > 0) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return accountEntity2;
                    }
                } catch (NoteSyncException e) {
                    Logger.INSTANCE.e(TAG, "Fail to load account" + e);
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor == null) {
                    throw th;
                }
                cursor.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private AccountEntity(String str, String str2) {
        this.mAccountName = str;
        this.mAccountType = str2;
    }

    public long getId() {
        return this.mId;
    }

    public String getAccountName() {
        return this.mAccountName;
    }

    public String getAccountType() {
        return this.mAccountType;
    }

    public void setSyncTag(String str) {
        this.mData.setSyncTag(str);
    }

    public String getSyncTag() {
        return this.mData.getSyncTag();
    }

    public void setSyncExtraInfo(String str) {
        this.mData.setSyncExtraInfo(str);
    }

    public String getSyncExtraInfo() {
        return this.mData.getSyncExtraInfo();
    }

    public void setUnrecoverableCount(int i) {
        this.mData.setUnrecoverableCount(i);
    }

    public int getUnrecoverableCount() {
        return this.mData.iUnrecoverableCount;
    }

    public boolean hasSyncedBefore() {
        return !TextUtils.isEmpty(getSyncTag());
    }

    public Account getAccount() {
        return this.mAccount;
    }

    public void persist(Context context) throws NoteSyncException {
        if (this.mId == 0) {
            Uri uriInsert = context.getContentResolver().insert(Notes.Account.CONTENT_URI, getContentValues());
            if (uriInsert != null) {
                this.mId = Long.parseLong(uriInsert.getLastPathSegment());
                this.mData.markClean();
                return;
            }
            return;
        }
        if (this.mData.isDirty()) {
            if (context.getContentResolver().update(ContentUris.withAppendedId(Notes.appendSyncAdapterFlag(Notes.Account.CONTENT_URI), this.mId), getContentValues(), "data=?", new String[]{this.mData.getOldDataText()}) <= 0) {
                throw new NoteSyncException("Fail to update account");
            }
            this.mData.markClean();
        }
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Account.ACCOUNT_NAME, this.mAccountName);
        contentValues.put(Notes.Account.ACCOUNT_TYPE, this.mAccountType);
        contentValues.put("data", this.mData.toData());
        return contentValues;
    }

    public boolean increaseUnrecoverableCount() {
        setUnrecoverableCount(this.mData.getUnrecoverableCount() + 1);
        return this.mData.getUnrecoverableCount() >= 10;
    }

    public void fixSyncError(Context context) {
        Notes.Utils.clearAccount(context, false);
    }

    public void setupResync(Context context) {
        setSyncTag(null);
        setSyncExtraInfo(null);
        setUnrecoverableCount(0);
        try {
            persist(context);
        } catch (NoteSyncException e) {
            Logger.INSTANCE.e(TAG, "Fail to setup resync" + e);
        }
    }

    private static class AccountData {
        private static final String KEY_SYNC_EXTRA_INFO = "syncExtraInfo";
        private static final String KEY_SYNC_TAG = "syncTag";
        private static final String KEY_UNRECOVERABLE_COUNT = "unrecoverableCount";
        private boolean iDirty;
        private String iOldDataText;
        private String iSyncExtraInfo;
        private String iSyncTag;
        private int iUnrecoverableCount;

        private AccountData() {
        }

        public static AccountData parse(String str) {
            AccountData accountData = new AccountData();
            accountData.initialize(str);
            return accountData;
        }

        private void initialize(String str) {
            this.iOldDataText = str;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (TextUtils.isDigitsOnly(str)) {
                this.iSyncTag = str;
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.iSyncTag = jSONObject.optString("syncTag", null);
                this.iUnrecoverableCount = jSONObject.optInt(KEY_UNRECOVERABLE_COUNT, 0);
                this.iSyncExtraInfo = jSONObject.optString("syncExtraInfo", null);
            } catch (JSONException e) {
                Logger.INSTANCE.e(AccountEntity.TAG, "Fail to parse account data: " + str + ",exception:" + e);
            }
        }

        public void setSyncTag(String str) {
            if (TextUtils.equals(str, this.iSyncTag)) {
                return;
            }
            this.iSyncTag = str;
            this.iDirty = true;
        }

        public String getSyncTag() {
            return this.iSyncTag;
        }

        public void setUnrecoverableCount(int i) {
            if (i != this.iUnrecoverableCount) {
                this.iUnrecoverableCount = i;
                this.iDirty = true;
            }
        }

        public int getUnrecoverableCount() {
            return this.iUnrecoverableCount;
        }

        public void setSyncExtraInfo(String str) {
            if (TextUtils.equals(str, this.iSyncExtraInfo)) {
                return;
            }
            this.iSyncExtraInfo = str;
            this.iDirty = true;
        }

        public String getSyncExtraInfo() {
            return this.iSyncExtraInfo;
        }

        public void markClean() {
            this.iOldDataText = toData();
            this.iDirty = false;
        }

        public String getOldDataText() {
            return this.iOldDataText;
        }

        public String toData() {
            String str;
            if (!this.iDirty && (str = this.iOldDataText) != null) {
                return str;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("syncTag", this.iSyncTag);
                jSONObject.put(KEY_UNRECOVERABLE_COUNT, this.iUnrecoverableCount);
                jSONObject.put("syncExtraInfo", this.iSyncExtraInfo);
                return jSONObject.toString();
            } catch (JSONException e) {
                Logger.INSTANCE.e(AccountEntity.TAG, "Fail to create json:" + e);
                return "";
            }
        }

        public boolean isDirty() {
            return this.iDirty;
        }
    }
}
