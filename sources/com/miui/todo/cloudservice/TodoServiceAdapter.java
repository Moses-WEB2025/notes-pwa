package com.miui.todo.cloudservice;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.utils.SerializableUtils;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.opensdk.pdc.SyncException;
import miui.cloud.CloudPushConstants;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public class TodoServiceAdapter {
    private static final String TAG = "TodoServiceAdapter";
    private Account mAccount;
    private Context mContext;
    private TodoSyncManager mTodoSyncManager;

    public TodoServiceAdapter(Context context) {
        this.mContext = context;
    }

    public void onPerformMiCloudSync(Bundle bundle) throws SyncException, JSONException, CloudServerException, RetriableSyncException, ReSyncException {
        AccountEntity accountEntity;
        String token;
        long waterMarker;
        if (this.mContext.getContentResolver() == null) {
            Logger.INSTANCE.e(TAG, "fail to get resolver");
            return;
        }
        if (SerializableUtils.readObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY) == null || (accountEntity = (AccountEntity) SerializableUtils.readObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY)) == null || accountEntity.getAccountName() == null || !accountEntity.getAccountName().equals(this.mAccount.name)) {
            refreshAccount();
            token = null;
            waterMarker = 0;
        } else {
            token = accountEntity.getToken();
            waterMarker = accountEntity.getWaterMarker();
        }
        String str = token;
        long j = waterMarker;
        String string = bundle.getString(CloudPushConstants.PUSH_DATA);
        if (!TextUtils.isEmpty(string) && Long.parseLong(string) <= j) {
            Logger.INSTANCE.i(TAG, "push water <= local,return");
            return;
        }
        TodoSyncManager todoSyncManager = new TodoSyncManager(this.mContext, this.mAccount, str, j);
        this.mTodoSyncManager = todoSyncManager;
        todoSyncManager.sync();
    }

    private void refreshAccount() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountName(this.mAccount.name);
        accountEntity.setAccountType(this.mAccount.type);
        accountEntity.setSyncStatus(0L);
        SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY, accountEntity);
    }

    public void setAccount(Account account) {
        this.mAccount = account;
    }

    public void setSyncCanceled(boolean z) {
        this.mTodoSyncManager.setSyncCanceled(z);
    }
}
