package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.xiaomi.accounts.secure.SecureDatabase;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class AccountManagerService {
    private static final String ACCOUNTS_ID = "_id";
    private static final String ACCOUNTS_NAME = "name";
    private static final String ACCOUNTS_PASSWORD = "password";
    private static final String ACCOUNTS_TYPE = "type";
    private static final String ACCOUNTS_TYPE_COUNT = "count(type)";
    private static final String AUTHTOKENS_ACCOUNTS_ID = "accounts_id";
    private static final String AUTHTOKENS_AUTHTOKEN = "authtoken";
    private static final String AUTHTOKENS_ID = "_id";
    private static final String AUTHTOKENS_TYPE = "type";
    private static final int DATABASE_VERSION = 4;
    private static final String ENCRYPTED_DATABASE_NAME = "sec_accounts.db";
    private static final String EXTRAS_ACCOUNTS_ID = "accounts_id";
    private static final String EXTRAS_ID = "_id";
    private static final String EXTRAS_KEY = "key";
    private static final String EXTRAS_VALUE = "value";
    private static final String GRANTS_ACCOUNTS_ID = "accounts_id";
    private static final String GRANTS_AUTH_TOKEN_TYPE = "auth_token_type";
    private static final String GRANTS_GRANTEE_UID = "uid";
    private static final int MESSAGE_TIMED_OUT = 3;
    private static final String META_KEY = "key";
    private static final String META_VALUE = "value";
    private static final String OLD_PLAIN_DATABASE_NAME = "accounts.db";
    private static final String SELECTION_AUTHTOKENS_BY_ACCOUNT = "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)";
    private static final String SELECTION_USERDATA_BY_ACCOUNT = "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)";
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_AUTHTOKENS = "authtokens";
    private static final String TABLE_EXTRAS = "extras";
    private static final String TABLE_GRANTS = "grants";
    private static final String TABLE_META = "meta";
    private static final String TAG = "AccountManagerService";
    private static final int TIMEOUT_DELAY_MS = 60000;
    private final AccountAuthenticatorCache mAuthenticatorCache;
    private final Context mContext;
    private final MessageHandler mMessageHandler;
    private HandlerThread mMessageThread;
    private final PackageManager mPackageManager;
    private final LinkedHashMap<String, Session> mSessions;
    private final SparseArray<UserAccounts> mUsers;
    private static final ExecutorService REMOTE_THREAD_POOL_EXECUTOR = Executors.newCachedThreadPool();
    private static final String[] COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN = {"type", "authtoken"};
    private static final String[] COLUMNS_EXTRAS_KEY_AND_VALUE = {"key", "value"};
    private static AtomicReference<AccountManagerService> sThis = new AtomicReference<>();
    private static final Account[] EMPTY_ACCOUNT_ARRAY = new Account[0];
    private static final Intent ACCOUNTS_CHANGED_INTENT = new Intent(AccountManager.LOGIN_ACCOUNTS_CHANGED_ACTION);

    private long clearCallingIdentity() {
        return 0L;
    }

    private void restoreCallingIdentity(long j) {
    }

    static class UserAccounts {
        private final HashMap<String, Account[]> accountCache;
        private HashMap<Account, HashMap<String, String>> authTokenCache;
        private final Object cacheLock;
        private final SecureDatabaseHelper openHelper;
        private HashMap<Account, HashMap<String, String>> userDataCache;
        private final int userId;

        UserAccounts(Context context, int i) {
            Object obj = new Object();
            this.cacheLock = obj;
            this.accountCache = new LinkedHashMap();
            this.userDataCache = new HashMap<>();
            this.authTokenCache = new HashMap<>();
            this.userId = i;
            synchronized (obj) {
                this.openHelper = new SecureDatabaseHelper(context, AccountManagerService.getSecureDatabasePath(context, i));
            }
        }
    }

    public static AccountManagerService getSingleton() {
        return sThis.get();
    }

    public AccountManagerService(Context context) {
        this(context, context.getPackageManager(), new AccountAuthenticatorCache(context));
    }

    public AccountManagerService(Context context, PackageManager packageManager, AccountAuthenticatorCache accountAuthenticatorCache) {
        this.mSessions = new LinkedHashMap<>();
        this.mUsers = new SparseArray<>();
        this.mContext = context;
        this.mPackageManager = packageManager;
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mMessageThread = handlerThread;
        handlerThread.start();
        this.mMessageHandler = new MessageHandler(this.mMessageThread.getLooper());
        this.mAuthenticatorCache = accountAuthenticatorCache;
        sThis.set(this);
        initUser(0);
    }

    private UserAccounts initUser(int i) {
        UserAccounts userAccounts;
        synchronized (this.mUsers) {
            userAccounts = this.mUsers.get(i);
            if (userAccounts == null) {
                userAccounts = new UserAccounts(this.mContext, i);
                this.mUsers.append(i, userAccounts);
                purgeOldGrants(userAccounts);
                validateAccountsAndPopulateCache(userAccounts);
            }
        }
        return userAccounts;
    }

    private void purgeOldGrantsAll() {
        synchronized (this.mUsers) {
            for (int i = 0; i < this.mUsers.size(); i++) {
                purgeOldGrants(this.mUsers.valueAt(i));
            }
        }
    }

    private void purgeOldGrants(UserAccounts userAccounts) {
        synchronized (userAccounts.cacheLock) {
            try {
                SecureDatabase secureDatabase = userAccounts.openHelper.getSecureDatabase(this.mContext);
                Cursor cursorQuery = secureDatabase.query(false, "grants", new String[]{"uid"}, null, null, "uid", null, null, null);
                while (cursorQuery.moveToNext()) {
                    try {
                        int i = cursorQuery.getInt(0);
                        if (this.mPackageManager.getPackagesForUid(i) == null) {
                            AccountLogger.log(TAG, "deleting grants for UID " + i + " because its package is no longer installed");
                            secureDatabase.delete("grants", "uid=?", new Object[]{Integer.valueOf(i)});
                        }
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                cursorQuery.close();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    private void validateAccountsAndPopulateCache(UserAccounts userAccounts) {
        boolean z;
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase = userAccounts.openHelper.getSecureDatabase(this.mContext);
            int i = 0;
            Cursor cursorQuery = secureDatabase.query(false, "accounts", new String[]{"_id", "type", "name"}, null, null, null, null, null, null);
            try {
                userAccounts.accountCache.clear();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                boolean z2 = false;
                while (cursorQuery.moveToNext()) {
                    try {
                        long j = cursorQuery.getLong(i);
                        String string = cursorQuery.getString(1);
                        String string2 = cursorQuery.getString(2);
                        AccountLogger.log(TAG, "validateAccountsAndPopulateCache>>>accountId=" + j + " accountType=" + string + " accountName=" + string2);
                        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                            secureDatabase.delete("accounts", "_id=" + j, null);
                        } else if (this.mAuthenticatorCache.getServiceInfo(AuthenticatorDescription.newKey(string)) == null) {
                            AccountLogger.log(TAG, "deleting account " + string2 + " because type " + string + " no longer has a registered authenticator");
                            secureDatabase.delete("accounts", "_id=" + j, null);
                            try {
                                Account account = new Account(string2, string);
                                userAccounts.userDataCache.remove(account);
                                userAccounts.authTokenCache.remove(account);
                                z2 = true;
                            } catch (Throwable th) {
                                th = th;
                                z = true;
                                cursorQuery.close();
                                if (z) {
                                    sendAccountsChangedBroadcast(userAccounts.userId);
                                }
                                throw th;
                            }
                        } else {
                            ArrayList arrayList = (ArrayList) linkedHashMap.get(string);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                linkedHashMap.put(string, arrayList);
                            }
                            arrayList.add(string2);
                        }
                        i = 0;
                    } catch (Throwable th2) {
                        th = th2;
                        z = z2;
                    }
                }
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    String str = (String) entry.getKey();
                    ArrayList arrayList2 = (ArrayList) entry.getValue();
                    Account[] accountArr = new Account[arrayList2.size()];
                    Iterator it = arrayList2.iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        accountArr[i2] = new Account((String) it.next(), str);
                        i2++;
                    }
                    userAccounts.accountCache.put(str, accountArr);
                }
                cursorQuery.close();
                if (z2) {
                    sendAccountsChangedBroadcast(userAccounts.userId);
                }
            } catch (Throwable th3) {
                th = th3;
                z = false;
            }
        }
    }

    private UserAccounts getUserAccountsForCaller() {
        return getUserAccounts(UserId.getCallingUserId());
    }

    protected UserAccounts getUserAccounts(int i) {
        UserAccounts userAccountsInitUser;
        synchronized (this.mUsers) {
            userAccountsInitUser = this.mUsers.get(i);
            if (userAccountsInitUser == null) {
                userAccountsInitUser = initUser(i);
                this.mUsers.append(i, userAccountsInitUser);
            }
        }
        return userAccountsInitUser;
    }

    public String getPassword(Account account) {
        AccountLogger.log(TAG, "getPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return readPasswordInternal(userAccountsForCaller, account);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private String readPasswordInternal(UserAccounts userAccounts, Account account) {
        if (account == null) {
            return null;
        }
        synchronized (userAccounts.cacheLock) {
            Cursor cursorQuery = userAccounts.openHelper.getSecureDatabase(this.mContext).query(false, "accounts", new String[]{"password"}, "name=? AND type=?", new Object[]{account.name, account.type}, null, null, null, null);
            try {
                if (!cursorQuery.moveToNext()) {
                    cursorQuery.close();
                    return null;
                }
                String string = cursorQuery.getString(0);
                cursorQuery.close();
                return string;
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
    }

    public String getUserData(Account account, String str) {
        AccountLogger.log(TAG, "getUserData: " + account + ", key " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return readUserDataInternal(userAccountsForCaller, account, str);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public Map<String, String> getAllUserData(Account account) {
        AccountLogger.log(TAG, "getAllUserData: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return readAllUserDataInternal(userAccountsForCaller, account);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        AccountLogger.log(TAG, "getAuthenticatorTypes: caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            Collection<AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription>> allServices = this.mAuthenticatorCache.getAllServices();
            AuthenticatorDescription[] authenticatorDescriptionArr = new AuthenticatorDescription[allServices.size()];
            Iterator<AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription>> it = allServices.iterator();
            int i = 0;
            while (it.hasNext()) {
                authenticatorDescriptionArr[i] = it.next().type;
                i++;
            }
            restoreCallingIdentity(jClearCallingIdentity);
            return authenticatorDescriptionArr;
        } catch (Throwable th) {
            restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    public boolean addAccount(Account account, String str, Bundle bundle) {
        AccountLogger.log(TAG, "addAccount: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return addAccountInternal(userAccountsForCaller, account, str, bundle);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v3 */
    private boolean addAccountInternal(UserAccounts userAccounts, Account account, String str, Bundle bundle) {
        SecureDatabase secureDatabase = "insertAccountIntoDatabase: ";
        if (account == null) {
            return false;
        }
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase2 = userAccounts.openHelper.getSecureDatabase(this.mContext);
            secureDatabase2.beginTransaction();
            Cursor cursorRawQuery = null;
            try {
                try {
                    cursorRawQuery = secureDatabase2.rawQuery("select count(*) from accounts WHERE name=? AND type=?", new Object[]{account.name, account.type});
                    long j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
                    if (cursorRawQuery != null) {
                        try {
                            cursorRawQuery.close();
                        } catch (Throwable th) {
                            th = th;
                            secureDatabase = secureDatabase2;
                            secureDatabase.endTransaction();
                            throw th;
                        }
                    }
                    if (j <= 0) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("name", account.name);
                        contentValues.put("type", account.type);
                        contentValues.put("password", str);
                        long jInsert = secureDatabase2.insert("accounts", "name", contentValues);
                        if (jInsert >= 0) {
                            if (bundle != null) {
                                for (String str2 : bundle.keySet()) {
                                    long j2 = jInsert;
                                    SecureDatabase secureDatabase3 = secureDatabase2;
                                    try {
                                        if (insertExtraLocked(secureDatabase3, j2, str2, bundle.getString(str2)) < 0) {
                                            AccountLogger.log(TAG, "insertAccountIntoDatabase: " + account + ", skipping since insertExtra failed for key " + str2);
                                            secureDatabase3.endTransaction();
                                        } else {
                                            jInsert = j2;
                                            secureDatabase2 = secureDatabase3;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        secureDatabase = secureDatabase3;
                                        secureDatabase.endTransaction();
                                        throw th;
                                    }
                                }
                            }
                            SecureDatabase secureDatabase4 = secureDatabase2;
                            secureDatabase4.setTransactionSuccessful();
                            insertAccountIntoCacheLocked(userAccounts, account);
                            secureDatabase4.endTransaction();
                            sendAccountsChangedBroadcast(userAccounts.userId);
                            return true;
                        }
                        AccountLogger.log(TAG, "insertAccountIntoDatabase: " + account + ", skipping the DB insert failed");
                        return false;
                    }
                    AccountLogger.log(TAG, "insertAccountIntoDatabase: " + account + ", skipping since the account already exists");
                    secureDatabase2.endTransaction();
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                throw th4;
            }
        }
    }

    private long insertExtraLocked(SecureDatabase secureDatabase, long j, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", str);
        contentValues.put("accounts_id", Long.valueOf(j));
        contentValues.put("value", str2);
        return secureDatabase.insert("extras", "key", contentValues);
    }

    public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr) throws Throwable {
        AccountManagerService accountManagerService;
        AccountLogger.log(TAG, "hasFeatures: " + account + ", response " + iAccountManagerResponse + ", features " + stringArrayToString(strArr) + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (strArr == null) {
            throw new IllegalArgumentException("features is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            accountManagerService = this;
            try {
                accountManagerService.new TestFeaturesSession(userAccountsForCaller, iAccountManagerResponse, account, strArr).bind();
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            accountManagerService = this;
        }
    }

    private class TestFeaturesSession extends Session {
        private final Account mAccount;
        private final String[] mFeatures;

        public TestFeaturesSession(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr) {
            super(userAccounts, iAccountManagerResponse, account.type, false, true);
            this.mFeatures = strArr;
            this.mAccount = account;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        public void run() throws RemoteException {
            try {
                this.mAuthenticator.hasFeatures(this, this.mAccount, this.mFeatures);
            } catch (RemoteException unused) {
                onError(1, "remote exception");
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    if (bundle != null) {
                        AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                        Bundle bundle2 = new Bundle();
                        bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                        responseAndClose.onResult(bundle2);
                        return;
                    }
                    responseAndClose.onError(5, "null bundle");
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "failure while notifying response", e);
                }
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        protected String toDebugString(long j) {
            StringBuilder sbAppend = new StringBuilder().append(super.toDebugString(j)).append(", hasFeatures, ").append(this.mAccount).append(", ");
            String[] strArr = this.mFeatures;
            return sbAppend.append(strArr != null ? TextUtils.join(",", strArr) : null).toString();
        }
    }

    public void removeAccount(IAccountManagerResponse iAccountManagerResponse, Account account) {
        AccountLogger.log(TAG, "removeAccount: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            new RemoveAccountSession(userAccountsForCaller, iAccountManagerResponse, account).bind();
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private class RemoveAccountSession extends Session {
        final Account mAccount;

        public RemoveAccountSession(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, Account account) {
            super(userAccounts, iAccountManagerResponse, account.type, false, true);
            this.mAccount = account;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        protected String toDebugString(long j) {
            return super.toDebugString(j) + ", removeAccount, account " + this.mAccount;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        public void run() throws RemoteException {
            this.mAuthenticator.getAccountRemovalAllowed(this, this.mAccount);
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            if (bundle != null && bundle.containsKey("booleanResult") && !bundle.containsKey("intent")) {
                boolean z = bundle.getBoolean("booleanResult");
                if (z) {
                    AccountManagerService.this.removeAccountInternal(this.mAccounts, this.mAccount);
                }
                IAccountManagerResponse responseAndClose = getResponseAndClose();
                if (responseAndClose != null) {
                    AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("booleanResult", z);
                    try {
                        responseAndClose.onResult(bundle2);
                    } catch (RemoteException unused) {
                    }
                }
            }
            super.onResult(bundle);
        }
    }

    protected void removeAccountInternal(Account account) {
        removeAccountInternal(getUserAccountsForCaller(), account);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAccountInternal(UserAccounts userAccounts, Account account) {
        synchronized (userAccounts.cacheLock) {
            userAccounts.openHelper.getSecureDatabase(this.mContext).delete("accounts", "name=? AND type=?", new Object[]{account.name, account.type});
            removeAccountFromCacheLocked(userAccounts, account);
            sendAccountsChangedBroadcast(userAccounts.userId);
        }
    }

    public void invalidateAuthToken(String str, String str2) {
        AccountLogger.log(TAG, "invalidateAuthToken: accountType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("authToken is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            synchronized (userAccountsForCaller.cacheLock) {
                SecureDatabase secureDatabase = userAccountsForCaller.openHelper.getSecureDatabase(this.mContext);
                secureDatabase.beginTransaction();
                try {
                    invalidateAuthTokenLocked(userAccountsForCaller, secureDatabase, str, str2);
                    secureDatabase.setTransactionSuccessful();
                    secureDatabase.endTransaction();
                } catch (Throwable th) {
                    secureDatabase.endTransaction();
                    throw th;
                }
            }
            restoreCallingIdentity(jClearCallingIdentity);
        } catch (Throwable th2) {
            restoreCallingIdentity(jClearCallingIdentity);
            throw th2;
        }
    }

    private void invalidateAuthTokenLocked(UserAccounts userAccounts, SecureDatabase secureDatabase, String str, String str2) {
        if (str2 == null || str == null) {
            return;
        }
        Cursor cursorRawQuery = secureDatabase.rawQuery("SELECT authtokens._id, accounts.name, authtokens.type FROM accounts JOIN authtokens ON accounts._id = accounts_id WHERE authtoken = ? AND accounts.type = ?", new Object[]{str2, str});
        while (cursorRawQuery.moveToNext()) {
            try {
                long j = cursorRawQuery.getLong(0);
                String string = cursorRawQuery.getString(1);
                String string2 = cursorRawQuery.getString(2);
                secureDatabase.delete("authtokens", "_id=" + j, null);
                AccountManagerService accountManagerService = this;
                UserAccounts userAccounts2 = userAccounts;
                SecureDatabase secureDatabase2 = secureDatabase;
                accountManagerService.writeAuthTokenIntoCacheLocked(userAccounts2, secureDatabase2, new Account(string, str), string2, null);
                this = accountManagerService;
                userAccounts = userAccounts2;
                secureDatabase = secureDatabase2;
            } catch (Throwable th) {
                cursorRawQuery.close();
                throw th;
            }
        }
        this.forceInvalidateAuthTokenCache(userAccounts, str, str2);
        cursorRawQuery.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveAuthTokenToDatabase(UserAccounts userAccounts, Account account, String str, String str2) {
        SecureDatabase secureDatabase;
        if (account == null || str == null) {
            return false;
        }
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase2 = userAccounts.openHelper.getSecureDatabase(this.mContext);
            secureDatabase2.beginTransaction();
            try {
                long accountIdLocked = getAccountIdLocked(secureDatabase2, account);
                if (accountIdLocked >= 0) {
                    secureDatabase2.delete("authtokens", "accounts_id=" + accountIdLocked + " AND type=?", new String[]{str});
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("accounts_id", Long.valueOf(accountIdLocked));
                    contentValues.put("type", str);
                    contentValues.put("authtoken", str2);
                    if (secureDatabase2.insert("authtokens", "authtoken", contentValues) >= 0) {
                        secureDatabase2.setTransactionSuccessful();
                        try {
                            writeAuthTokenIntoCacheLocked(userAccounts, secureDatabase2, account, str, str2);
                            secureDatabase2.endTransaction();
                            return true;
                        } catch (Throwable th) {
                            th = th;
                            secureDatabase = secureDatabase2;
                        }
                    } else {
                        secureDatabase2.endTransaction();
                    }
                    Throwable th2 = th;
                    secureDatabase.endTransaction();
                    throw th2;
                }
                secureDatabase2.endTransaction();
                return false;
            } catch (Throwable th3) {
                th = th3;
                secureDatabase = secureDatabase2;
            }
        }
    }

    public String peekAuthToken(Account account, String str) {
        AccountLogger.log(TAG, "peekAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return readAuthTokenInternal(userAccountsForCaller, account, str);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public Map<String, String> peekAllAuthToken(Account account) {
        AccountLogger.log(TAG, "peekAllAuthToken: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            return readAllAuthTokenInternal(userAccountsForCaller, account);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void setAuthToken(Account account, String str, String str2) {
        AccountLogger.log(TAG, "setAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            saveAuthTokenToDatabase(userAccountsForCaller, account, str, str2);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private void _deleteAccountFromDb(SecureDatabase secureDatabase) {
        Cursor cursorRawQuery = secureDatabase.rawQuery("select _id from accounts", null);
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                AccountLogger.log(TAG, "delete account ret=" + secureDatabase.delete("accounts", "_id=?", new Object[]{Long.valueOf(cursorRawQuery.getLong(0))}));
            }
        }
    }

    public void setPassword(Account account, String str) {
        AccountLogger.log(TAG, "setPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            setPasswordInternal(userAccountsForCaller, account, str);
            if (!TextUtils.equals(str, getPassword(account))) {
                AccountLogger.log(TAG, "save password failed, not equals");
                SecureDatabase secureDatabase = userAccountsForCaller.openHelper.getSecureDatabase(this.mContext);
                _deleteAccountFromDb(secureDatabase);
                boolean zAddAccount = addAccount(account, str, null);
                AccountLogger.log(TAG, "add account ret=" + zAddAccount);
                if (zAddAccount) {
                    if (TextUtils.equals(str, getPassword(account))) {
                        AccountLogger.log(TAG, "account & password correct");
                    } else {
                        AccountLogger.log(TAG, "account & password not match, error, delete account");
                        _deleteAccountFromDb(secureDatabase);
                    }
                }
            }
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private void setPasswordInternal(UserAccounts userAccounts, Account account, String str) {
        if (account == null) {
            return;
        }
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase = userAccounts.openHelper.getSecureDatabase(this.mContext);
            secureDatabase.beginTransaction();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("password", str);
                long accountIdLocked = getAccountIdLocked(secureDatabase, account);
                if (accountIdLocked >= 0) {
                    secureDatabase.update("accounts", contentValues, "_id=?", new Object[]{Long.valueOf(accountIdLocked)});
                    secureDatabase.delete("authtokens", "accounts_id=?", new Object[]{Long.valueOf(accountIdLocked)});
                    userAccounts.authTokenCache.remove(account);
                    secureDatabase.setTransactionSuccessful();
                }
                secureDatabase.endTransaction();
                sendAccountsChangedBroadcast(userAccounts.userId);
            } catch (Throwable th) {
                secureDatabase.endTransaction();
                throw th;
            }
        }
    }

    private void sendAccountsChangedBroadcast(int i) {
        StringBuilder sb = new StringBuilder("the accounts changed, sending broadcast of ");
        Intent intent = ACCOUNTS_CHANGED_INTENT;
        AccountLogger.log(TAG, sb.append(intent.getAction()).toString());
        intent.setPackage(this.mContext.getPackageName());
        this.mContext.sendBroadcast(intent);
    }

    public void clearPassword(Account account) {
        AccountLogger.log(TAG, "clearPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            setPasswordInternal(userAccountsForCaller, account, null);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void setUserData(Account account, String str, String str2) {
        AccountLogger.log(TAG, "setUserData: " + account + ", key " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            setUserdataInternal(userAccountsForCaller, account, str, str2);
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        if (1 != r5.update("extras", r2, "_id=" + r10, null)) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setUserdataInternal(com.xiaomi.accounts.AccountManagerService.UserAccounts r13, android.accounts.Account r14, java.lang.String r15, java.lang.String r16) {
        /*
            r12 = this;
            java.lang.String r0 = "_id="
            if (r14 == 0) goto L84
            if (r15 != 0) goto L8
            goto L84
        L8:
            java.lang.Object r1 = com.xiaomi.accounts.AccountManagerService.UserAccounts.access$100(r13)
            monitor-enter(r1)
            com.xiaomi.accounts.secure.SecureDatabaseHelper r2 = com.xiaomi.accounts.AccountManagerService.UserAccounts.access$200(r13)     // Catch: java.lang.Throwable -> L80
            android.content.Context r3 = r12.mContext     // Catch: java.lang.Throwable -> L80
            com.xiaomi.accounts.secure.SecureDatabase r5 = r2.getSecureDatabase(r3)     // Catch: java.lang.Throwable -> L80
            r5.beginTransaction()     // Catch: java.lang.Throwable -> L80
            long r6 = r12.getAccountIdLocked(r5, r14)     // Catch: java.lang.Throwable -> L7a
            r2 = 0
            int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r4 >= 0) goto L29
        L24:
            r5.endTransaction()     // Catch: java.lang.Throwable -> L80
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L80
            return
        L29:
            long r10 = r12.getExtrasIdLocked(r5, r6, r15)     // Catch: java.lang.Throwable -> L7a
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 >= 0) goto L46
            r4 = r12
            r8 = r15
            r9 = r16
            long r6 = r4.insertExtraLocked(r5, r6, r8, r9)     // Catch: java.lang.Throwable -> L7a
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 >= 0) goto L3e
            goto L24
        L3e:
            r9 = r16
        L40:
            r4 = r12
            r7 = r14
            r8 = r15
            r6 = r5
            r5 = r13
            goto L6b
        L46:
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L7a
            r2.<init>()     // Catch: java.lang.Throwable -> L7a
            java.lang.String r3 = "value"
            r9 = r16
            r2.put(r3, r9)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r3 = "extras"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7a
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L7a
            java.lang.StringBuilder r0 = r4.append(r10)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L7a
            r4 = 0
            int r0 = r5.update(r3, r2, r0, r4)     // Catch: java.lang.Throwable -> L7a
            r2 = 1
            if (r2 == r0) goto L40
            goto L24
        L6b:
            r4.writeUserDataIntoCacheLocked(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L77
            r5 = r6
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L7a
            r5.endTransaction()     // Catch: java.lang.Throwable -> L80
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L80
            return
        L77:
            r0 = move-exception
            r5 = r6
            goto L7b
        L7a:
            r0 = move-exception
        L7b:
            r12 = r0
            r5.endTransaction()     // Catch: java.lang.Throwable -> L80
            throw r12     // Catch: java.lang.Throwable -> L80
        L80:
            r0 = move-exception
            r12 = r0
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L80
            throw r12
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.accounts.AccountManagerService.setUserdataInternal(com.xiaomi.accounts.AccountManagerService$UserAccounts, android.accounts.Account, java.lang.String, java.lang.String):void");
    }

    private void onResult(IAccountManagerResponse iAccountManagerResponse, Bundle bundle) {
        if (bundle == null) {
            AccountLogger.log(TAG, "the result is unexpectedly null", new Exception());
        }
        AccountLogger.log(TAG, getClass().getSimpleName() + " calling onResult() on response " + iAccountManagerResponse);
        try {
            iAccountManagerResponse.onResult(bundle);
        } catch (RemoteException e) {
            AccountLogger.log(TAG, "failure while notifying response", e);
        }
    }

    public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2) throws Throwable {
        AccountManagerService accountManagerService;
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        int callingUid = getCallingUid();
        clearCallingIdentity();
        if (callingUid != 1000) {
            throw new SecurityException("can only call from system");
        }
        UserAccounts userAccounts = getUserAccounts(UserId.getUserId(callingUid));
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            accountManagerService = this;
            try {
                new Session(userAccounts, iAccountManagerResponse, str, false, false) { // from class: com.xiaomi.accounts.AccountManagerService.1
                    @Override // com.xiaomi.accounts.AccountManagerService.Session
                    protected String toDebugString(long j) {
                        return super.toDebugString(j) + ", getAuthTokenLabel, " + str + ", authTokenType " + str2;
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.Session
                    public void run() throws RemoteException {
                        this.mAuthenticator.getAuthTokenLabel(this, str2);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.Session, com.xiaomi.accounts.IAccountAuthenticatorResponse
                    public void onResult(Bundle bundle) {
                        if (bundle != null) {
                            String string = bundle.getString("authTokenLabelKey");
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("authTokenLabelKey", string);
                            super.onResult(bundle2);
                            return;
                        }
                        super.onResult(bundle);
                    }
                }.bind();
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            accountManagerService = this;
        }
    }

    public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, final boolean z, boolean z2, Bundle bundle) {
        AccountLogger.log(TAG, "getAuthToken: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", notifyOnAuthFailure " + z + ", expectActivityLaunch " + z2 + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        this.mAuthenticatorCache.getServiceInfo(AuthenticatorDescription.newKey(account.type));
        int callingUid = Binder.getCallingUid();
        final Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putInt(AccountManager.KEY_CALLER_UID, callingUid);
        bundle2.putInt(AccountManager.KEY_CALLER_PID, Binder.getCallingPid());
        if (z) {
            bundle2.putBoolean(AccountManager.KEY_NOTIFY_ON_FAILURE, true);
        }
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            String authTokenInternal = readAuthTokenInternal(userAccountsForCaller, account, str);
            if (authTokenInternal != null) {
                Bundle bundle3 = new Bundle();
                bundle3.putString("authtoken", authTokenInternal);
                bundle3.putString("authAccount", account.name);
                bundle3.putString("accountType", account.type);
                onResult(iAccountManagerResponse, bundle3);
                return;
            }
            new Session(userAccountsForCaller, iAccountManagerResponse, account.type, z2, false) { // from class: com.xiaomi.accounts.AccountManagerService.2
                @Override // com.xiaomi.accounts.AccountManagerService.Session
                protected String toDebugString(long j) {
                    Bundle bundle4 = bundle2;
                    if (bundle4 != null) {
                        bundle4.keySet();
                    }
                    return super.toDebugString(j) + ", getAuthToken, " + account + ", authTokenType " + str + ", loginOptions " + bundle2 + ", notifyOnAuthFailure " + z;
                }

                @Override // com.xiaomi.accounts.AccountManagerService.Session
                public void run() throws RemoteException {
                    this.mAuthenticator.getAuthToken(this, account, str, bundle2);
                }

                @Override // com.xiaomi.accounts.AccountManagerService.Session, com.xiaomi.accounts.IAccountAuthenticatorResponse
                public void onResult(Bundle bundle4) {
                    String string;
                    if (bundle4 != null && (string = bundle4.getString("authtoken")) != null) {
                        String string2 = bundle4.getString("authAccount");
                        String string3 = bundle4.getString("accountType");
                        if (!TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string2)) {
                            AccountManagerService.this.saveAuthTokenToDatabase(this.mAccounts, new Account(string2, string3), str, string);
                        } else {
                            onError(5, "the type and name should not be empty");
                            return;
                        }
                    }
                    super.onResult(bundle4);
                }
            }.bind();
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void addAcount(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2, final String[] strArr, boolean z, Bundle bundle) {
        AccountLogger.log(TAG, "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + stringArrayToString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        final Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putInt(AccountManager.KEY_CALLER_UID, callingUid);
        bundle2.putInt(AccountManager.KEY_CALLER_PID, callingPid);
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            new Session(userAccountsForCaller, iAccountManagerResponse, str, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.3
                @Override // com.xiaomi.accounts.AccountManagerService.Session
                public void run() throws RemoteException {
                    this.mAuthenticator.addAccount(this, this.mAccountType, str2, strArr, bundle2);
                }

                @Override // com.xiaomi.accounts.AccountManagerService.Session
                protected String toDebugString(long j) {
                    StringBuilder sbAppend = new StringBuilder().append(super.toDebugString(j)).append(", addAccount, accountType ").append(str).append(", requiredFeatures ");
                    String[] strArr2 = strArr;
                    return sbAppend.append(strArr2 != null ? TextUtils.join(",", strArr2) : null).toString();
                }
            }.bind();
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void confirmCredentials(IAccountManagerResponse iAccountManagerResponse, final Account account, final Bundle bundle, boolean z) {
        AccountLogger.log(TAG, "confirmCredentials: " + account + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            new Session(userAccountsForCaller, iAccountManagerResponse, account.type, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.4
                @Override // com.xiaomi.accounts.AccountManagerService.Session
                public void run() throws RemoteException {
                    this.mAuthenticator.confirmCredentials(this, account, bundle);
                }

                @Override // com.xiaomi.accounts.AccountManagerService.Session
                protected String toDebugString(long j) {
                    return super.toDebugString(j) + ", confirmCredentials, " + account;
                }
            }.bind();
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, boolean z, final Bundle bundle) {
        AccountLogger.log(TAG, "updateCredentials: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authTokenType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            new Session(userAccountsForCaller, iAccountManagerResponse, account.type, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.5
                @Override // com.xiaomi.accounts.AccountManagerService.Session
                public void run() throws RemoteException {
                    this.mAuthenticator.updateCredentials(this, account, str, bundle);
                }

                @Override // com.xiaomi.accounts.AccountManagerService.Session
                protected String toDebugString(long j) {
                    Bundle bundle2 = bundle;
                    if (bundle2 != null) {
                        bundle2.keySet();
                    }
                    return super.toDebugString(j) + ", updateCredentials, " + account + ", authTokenType " + str + ", loginOptions " + bundle;
                }
            }.bind();
        } finally {
            restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void editProperties(IAccountManagerResponse iAccountManagerResponse, final String str, boolean z) throws Throwable {
        AccountManagerService accountManagerService;
        AccountLogger.log(TAG, "editProperties: accountType " + str + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            accountManagerService = this;
            try {
                new Session(userAccountsForCaller, iAccountManagerResponse, str, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.6
                    @Override // com.xiaomi.accounts.AccountManagerService.Session
                    public void run() throws RemoteException {
                        this.mAuthenticator.editProperties(this, this.mAccountType);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.Session
                    protected String toDebugString(long j) {
                        return super.toDebugString(j) + ", editProperties, accountType " + str;
                    }
                }.bind();
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            accountManagerService = this;
        }
    }

    private class GetAccountsByTypeAndFeatureSession extends Session {
        private volatile Account[] mAccountsOfType;
        private volatile ArrayList<Account> mAccountsWithFeatures;
        private volatile int mCurrentAccount;
        private final String[] mFeatures;

        public GetAccountsByTypeAndFeatureSession(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr) {
            super(userAccounts, iAccountManagerResponse, str, false, true);
            this.mAccountsOfType = null;
            this.mAccountsWithFeatures = null;
            this.mCurrentAccount = 0;
            this.mFeatures = strArr;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        public void run() throws RemoteException {
            synchronized (this.mAccounts.cacheLock) {
                this.mAccountsOfType = AccountManagerService.this.getAccountsFromCacheLocked(this.mAccounts, this.mAccountType);
            }
            this.mAccountsWithFeatures = new ArrayList<>(this.mAccountsOfType.length);
            this.mCurrentAccount = 0;
            checkAccount();
        }

        public void checkAccount() {
            if (this.mCurrentAccount >= this.mAccountsOfType.length) {
                sendResult();
                return;
            }
            IAccountAuthenticator iAccountAuthenticator = this.mAuthenticator;
            if (iAccountAuthenticator == null) {
                AccountLogger.log(AccountManagerService.TAG, "checkAccount: aborting session since we are no longer connected to the authenticator, " + toDebugString());
                return;
            }
            try {
                iAccountAuthenticator.hasFeatures(this, this.mAccountsOfType[this.mCurrentAccount], this.mFeatures);
            } catch (RemoteException unused) {
                onError(1, "remote exception");
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            this.mNumResults++;
            if (bundle == null) {
                onError(5, "null bundle");
                return;
            }
            if (bundle.getBoolean("booleanResult", false)) {
                this.mAccountsWithFeatures.add(this.mAccountsOfType[this.mCurrentAccount]);
            }
            this.mCurrentAccount++;
            checkAccount();
        }

        public void sendResult() {
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    int size = this.mAccountsWithFeatures.size();
                    Account[] accountArr = new Account[size];
                    for (int i = 0; i < size; i++) {
                        accountArr[i] = this.mAccountsWithFeatures.get(i);
                    }
                    AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray("accounts", accountArr);
                    responseAndClose.onResult(bundle);
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "failure while notifying response", e);
                }
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.Session
        protected String toDebugString(long j) {
            StringBuilder sbAppend = new StringBuilder().append(super.toDebugString(j)).append(", getAccountsByTypeAndFeatures, ");
            String[] strArr = this.mFeatures;
            return sbAppend.append(strArr != null ? TextUtils.join(",", strArr) : null).toString();
        }
    }

    public Account[] getAccounts(int i) {
        Account[] accountsFromCacheLocked;
        UserAccounts userAccounts = getUserAccounts(i);
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            synchronized (userAccounts.cacheLock) {
                accountsFromCacheLocked = getAccountsFromCacheLocked(userAccounts, null);
            }
            restoreCallingIdentity(jClearCallingIdentity);
            return accountsFromCacheLocked;
        } catch (Throwable th) {
            restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    public Account[] getAccounts(String str) {
        Account[] accountsFromCacheLocked;
        AccountLogger.log(TAG, "getAccounts: accountType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            synchronized (userAccountsForCaller.cacheLock) {
                accountsFromCacheLocked = getAccountsFromCacheLocked(userAccountsForCaller, str);
            }
            restoreCallingIdentity(jClearCallingIdentity);
            return accountsFromCacheLocked;
        } catch (Throwable th) {
            restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr) throws Throwable {
        AccountManagerService accountManagerService;
        Account[] accountsFromCacheLocked;
        AccountLogger.log(TAG, "getAccounts: accountType " + str + ", response " + iAccountManagerResponse + ", features " + stringArrayToString(strArr) + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        long jClearCallingIdentity = clearCallingIdentity();
        try {
            if (strArr != null) {
                try {
                    if (strArr.length != 0) {
                        new GetAccountsByTypeAndFeatureSession(userAccountsForCaller, iAccountManagerResponse, str, strArr).bind();
                        restoreCallingIdentity(jClearCallingIdentity);
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    accountManagerService = this;
                    Throwable th2 = th;
                    accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
                    throw th2;
                }
            }
            accountManagerService = this;
            synchronized (userAccountsForCaller.cacheLock) {
                accountsFromCacheLocked = accountManagerService.getAccountsFromCacheLocked(userAccountsForCaller, str);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("accounts", accountsFromCacheLocked);
            accountManagerService.onResult(iAccountManagerResponse, bundle);
            accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
        } catch (Throwable th3) {
            th = th3;
            Throwable th4 = th;
            accountManagerService.restoreCallingIdentity(jClearCallingIdentity);
            throw th4;
        }
    }

    private long getAccountIdLocked(SecureDatabase secureDatabase, Account account) {
        Cursor cursorQuery = secureDatabase.query(false, "accounts", new String[]{"_id"}, "name=? AND type=?", new Object[]{account.name, account.type}, null, null, null, null);
        try {
            if (cursorQuery.moveToNext()) {
                return cursorQuery.getLong(0);
            }
            return -1L;
        } finally {
            cursorQuery.close();
        }
    }

    private long getExtrasIdLocked(SecureDatabase secureDatabase, long j, String str) {
        Cursor cursorQuery = secureDatabase.query(false, "extras", new String[]{"_id"}, "accounts_id=" + j + " AND key=?", new Object[]{str}, null, null, null, null);
        try {
            if (cursorQuery.moveToNext()) {
                return cursorQuery.getLong(0);
            }
            return -1L;
        } finally {
            cursorQuery.close();
        }
    }

    private abstract class Session extends IAccountAuthenticatorResponse.Stub implements IBinder.DeathRecipient, ServiceConnection {
        final String mAccountType;
        protected final UserAccounts mAccounts;
        final long mCreationTime;
        final boolean mExpectActivityLaunch;
        IAccountManagerResponse mResponse;
        private final boolean mStripAuthTokenFromResult;
        public int mNumResults = 0;
        private int mNumRequestContinued = 0;
        private int mNumErrors = 0;
        IAccountAuthenticator mAuthenticator = null;

        public abstract void run() throws RemoteException;

        public Session(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, boolean z2) {
            if (iAccountManagerResponse == null) {
                throw new IllegalArgumentException("response is null");
            }
            if (str == null) {
                throw new IllegalArgumentException("accountType is null");
            }
            this.mAccounts = userAccounts;
            this.mStripAuthTokenFromResult = z2;
            this.mResponse = iAccountManagerResponse;
            this.mAccountType = str;
            this.mExpectActivityLaunch = z;
            this.mCreationTime = SystemClock.elapsedRealtime();
            synchronized (AccountManagerService.this.mSessions) {
                AccountManagerService.this.mSessions.put(toString(), this);
            }
            try {
                iAccountManagerResponse.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
                this.mResponse = null;
                binderDied();
            }
        }

        IAccountManagerResponse getResponseAndClose() {
            IAccountManagerResponse iAccountManagerResponse = this.mResponse;
            if (iAccountManagerResponse == null) {
                return null;
            }
            close();
            return iAccountManagerResponse;
        }

        private void close() {
            synchronized (AccountManagerService.this.mSessions) {
                if (AccountManagerService.this.mSessions.remove(toString()) == null) {
                    return;
                }
                IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                if (iAccountManagerResponse != null) {
                    iAccountManagerResponse.asBinder().unlinkToDeath(this, 0);
                    this.mResponse = null;
                }
                cancelTimeout();
                unbind();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mResponse = null;
            close();
        }

        protected String toDebugString() {
            return toDebugString(SystemClock.elapsedRealtime());
        }

        protected String toDebugString(long j) {
            return "Session: expectLaunch " + this.mExpectActivityLaunch + ", connected " + (this.mAuthenticator != null) + ", stats (" + this.mNumResults + InternalZipConstants.ZIP_FILE_SEPARATOR + this.mNumRequestContinued + InternalZipConstants.ZIP_FILE_SEPARATOR + this.mNumErrors + "), lifetime " + ((j - this.mCreationTime) / 1000.0d);
        }

        void bind() {
            AccountLogger.log(AccountManagerService.TAG, "initiating bind to authenticator type " + this.mAccountType);
            if (bindToAuthenticator(this.mAccountType)) {
                return;
            }
            AccountLogger.log(AccountManagerService.TAG, "bind attempt failed for " + toDebugString());
            onError(1, "bind failure");
        }

        private void unbind() {
            if (this.mAuthenticator != null) {
                this.mAuthenticator = null;
                AccountManagerService.this.mContext.unbindService(this);
            }
        }

        public void scheduleTimeout() {
            AccountManagerService.this.mMessageHandler.sendMessageDelayed(AccountManagerService.this.mMessageHandler.obtainMessage(3, this), 60000L);
        }

        public void cancelTimeout() {
            AccountManagerService.this.mMessageHandler.removeMessages(3, this);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.mAuthenticator = IAccountAuthenticator.Stub.asInterface(iBinder);
            AccountManagerService.REMOTE_THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.accounts.AccountManagerService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mAuthenticator == null) {
                            AccountLogger.log(AccountManagerService.TAG, "onServiceConnected>>mAuthenticator=null");
                            Session.this.onError(1, "authenticator is null");
                        } else {
                            Session.this.run();
                        }
                    } catch (RemoteException unused) {
                        Session.this.onError(1, "remote exception");
                    }
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.mAuthenticator = null;
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "disconnected");
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "Session.onServiceDisconnected: caught RemoteException while responding", e);
                }
            }
        }

        public void onTimedOut() {
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "timeout");
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "Session.onTimedOut: caught RemoteException while responding", e);
                }
            }
        }

        public void onResult(Bundle bundle) {
            IAccountManagerResponse responseAndClose;
            this.mNumResults++;
            if (this.mExpectActivityLaunch && bundle != null && bundle.containsKey("intent")) {
                responseAndClose = this.mResponse;
            } else {
                responseAndClose = getResponseAndClose();
            }
            if (responseAndClose != null) {
                try {
                    if (bundle == null) {
                        AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                        responseAndClose.onError(5, "null bundle returned");
                    } else {
                        if (this.mStripAuthTokenFromResult) {
                            bundle.remove("authtoken");
                        }
                        AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                        responseAndClose.onResult(bundle);
                    }
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "failure while notifying response", e);
                }
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onRequestContinued() {
            this.mNumRequestContinued++;
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onError(int i, String str) {
            this.mNumErrors++;
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                AccountLogger.log(AccountManagerService.TAG, getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                try {
                    responseAndClose.onError(i, str);
                    return;
                } catch (RemoteException e) {
                    AccountLogger.log(AccountManagerService.TAG, "Session.onError: caught RemoteException while responding", e);
                    return;
                }
            }
            AccountLogger.log(AccountManagerService.TAG, "Session.onError: already closed");
        }

        private boolean bindToAuthenticator(String str) {
            AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription> serviceInfo = AccountManagerService.this.mAuthenticatorCache.getServiceInfo(AuthenticatorDescription.newKey(str));
            if (serviceInfo == null) {
                AccountLogger.log(AccountManagerService.TAG, "there is no authenticator for " + str + ", bailing out");
                return false;
            }
            Intent intent = new Intent();
            intent.setAction(AccountManager.ACTION_AUTHENTICATOR_INTENT);
            intent.setComponent(serviceInfo.componentName);
            AccountLogger.log(AccountManagerService.TAG, "performing bindService to " + serviceInfo.componentName);
            if (AccountManagerService.this.mContext.bindService(intent, this, 1)) {
                return true;
            }
            AccountLogger.log(AccountManagerService.TAG, "bindService to " + serviceInfo.componentName + " failed");
            return false;
        }
    }

    private class MessageHandler extends Handler {
        MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 3) {
                ((Session) message.obj).onTimedOut();
                return;
            }
            throw new IllegalStateException("unhandled message: " + message.what);
        }
    }

    private static File getSecureDatabaseFile(Context context, int i) {
        File file = new File(context.getFilesDir(), "xiaomi_account/" + i);
        file.mkdirs();
        return new File(file, OLD_PLAIN_DATABASE_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSecureDatabasePath(Context context, int i) {
        return getSecureDatabaseFile(context, i).getPath();
    }

    public void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException {
        if (getCallingUid() != 1000) {
            throw new SecurityException();
        }
        if (z) {
            grantAppPermission(account, str, i);
        } else {
            revokeAppPermission(account, str, i);
        }
    }

    private void grantAppPermission(Account account, String str, int i) {
        if (account == null || str == null) {
            AccountLogger.log(TAG, "grantAppPermission: called with invalid arguments", new Exception());
            return;
        }
        UserAccounts userAccounts = getUserAccounts(UserId.getUserId(i));
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase = userAccounts.openHelper.getSecureDatabase(this.mContext);
            secureDatabase.beginTransaction();
            try {
                long accountIdLocked = getAccountIdLocked(secureDatabase, account);
                if (accountIdLocked >= 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("accounts_id", Long.valueOf(accountIdLocked));
                    contentValues.put("auth_token_type", str);
                    contentValues.put("uid", Integer.valueOf(i));
                    secureDatabase.insert("grants", "accounts_id", contentValues);
                    secureDatabase.setTransactionSuccessful();
                }
                secureDatabase.endTransaction();
            } catch (Throwable th) {
                secureDatabase.endTransaction();
                throw th;
            }
        }
    }

    private void revokeAppPermission(Account account, String str, int i) {
        if (account == null || str == null) {
            AccountLogger.log(TAG, "revokeAppPermission: called with invalid arguments", new Exception());
            return;
        }
        UserAccounts userAccounts = getUserAccounts(UserId.getUserId(i));
        synchronized (userAccounts.cacheLock) {
            SecureDatabase secureDatabase = userAccounts.openHelper.getSecureDatabase(this.mContext);
            secureDatabase.beginTransaction();
            try {
                long accountIdLocked = getAccountIdLocked(secureDatabase, account);
                if (accountIdLocked >= 0) {
                    secureDatabase.delete("grants", "accounts_id=? AND auth_token_type=? AND uid=?", new Object[]{Long.valueOf(accountIdLocked), str, Integer.valueOf(i)});
                    secureDatabase.setTransactionSuccessful();
                }
                secureDatabase.endTransaction();
            } catch (Throwable th) {
                secureDatabase.endTransaction();
                throw th;
            }
        }
    }

    private static final String stringArrayToString(String[] strArr) {
        if (strArr != null) {
            return "[" + TextUtils.join(",", strArr) + "]";
        }
        return null;
    }

    private void removeAccountFromCacheLocked(UserAccounts userAccounts, Account account) {
        Account[] accountArr = (Account[]) userAccounts.accountCache.get(account.type);
        if (accountArr != null) {
            ArrayList arrayList = new ArrayList();
            for (Account account2 : accountArr) {
                if (!account2.equals(account)) {
                    arrayList.add(account2);
                }
            }
            if (arrayList.isEmpty()) {
                userAccounts.accountCache.remove(account.type);
            } else {
                userAccounts.accountCache.put(account.type, (Account[]) arrayList.toArray(new Account[arrayList.size()]));
            }
        }
        userAccounts.userDataCache.remove(account);
        userAccounts.authTokenCache.remove(account);
    }

    private void insertAccountIntoCacheLocked(UserAccounts userAccounts, Account account) {
        Account[] accountArr = (Account[]) userAccounts.accountCache.get(account.type);
        int length = accountArr != null ? accountArr.length : 0;
        Account[] accountArr2 = new Account[length + 1];
        if (accountArr != null) {
            System.arraycopy(accountArr, 0, accountArr2, 0, length);
        }
        accountArr2[length] = account;
        userAccounts.accountCache.put(account.type, accountArr2);
    }

    protected Account[] getAccountsFromCacheLocked(UserAccounts userAccounts, String str) {
        validateAccountsAndPopulateCache(userAccounts);
        if (str != null) {
            Account[] accountArr = (Account[]) userAccounts.accountCache.get(str);
            if (accountArr == null) {
                return EMPTY_ACCOUNT_ARRAY;
            }
            return (Account[]) Arrays.copyOf(accountArr, accountArr.length);
        }
        Iterator it = userAccounts.accountCache.values().iterator();
        int length = 0;
        while (it.hasNext()) {
            length += ((Account[]) it.next()).length;
        }
        if (length == 0) {
            return EMPTY_ACCOUNT_ARRAY;
        }
        Account[] accountArr2 = new Account[length];
        int length2 = 0;
        for (Account[] accountArr3 : userAccounts.accountCache.values()) {
            System.arraycopy(accountArr3, 0, accountArr2, length2, accountArr3.length);
            length2 += accountArr3.length;
        }
        return accountArr2;
    }

    protected void writeUserDataIntoCacheLocked(UserAccounts userAccounts, SecureDatabase secureDatabase, Account account, String str, String str2) {
        HashMap<String, String> userDataForAccountFromDatabaseLocked = (HashMap) userAccounts.userDataCache.get(account);
        if (userDataForAccountFromDatabaseLocked == null) {
            userDataForAccountFromDatabaseLocked = readUserDataForAccountFromDatabaseLocked(secureDatabase, account);
            userAccounts.userDataCache.put(account, userDataForAccountFromDatabaseLocked);
        }
        if (str2 == null) {
            userDataForAccountFromDatabaseLocked.remove(str);
        } else {
            userDataForAccountFromDatabaseLocked.put(str, str2);
        }
    }

    protected void writeAuthTokenIntoCacheLocked(UserAccounts userAccounts, SecureDatabase secureDatabase, Account account, String str, String str2) {
        AccountLogger.log(TAG, "writeAuthTokenIntoCacheLocked>>>key=" + str + " value=" + str2);
        HashMap<String, String> authTokensForAccountFromDatabaseLocked = (HashMap) userAccounts.authTokenCache.get(account);
        if (authTokensForAccountFromDatabaseLocked == null) {
            authTokensForAccountFromDatabaseLocked = readAuthTokensForAccountFromDatabaseLocked(secureDatabase, account);
            userAccounts.authTokenCache.put(account, authTokensForAccountFromDatabaseLocked);
        }
        if (str2 == null) {
            authTokensForAccountFromDatabaseLocked.remove(str);
        } else {
            authTokensForAccountFromDatabaseLocked.put(str, str2);
        }
    }

    private void forceInvalidateAuthTokenCache(UserAccounts userAccounts, String str, String str2) {
        HashMap map;
        AccountLogger.log(TAG, "invalidateAuthTokenCache>>>");
        Account[] accounts = getAccounts(str);
        Account account = (accounts == null || accounts.length <= 0) ? null : accounts[0];
        if (TextUtils.isEmpty(str2) || account == null || (map = (HashMap) userAccounts.authTokenCache.get(account)) == null || map.size() == 0 || !map.containsValue(str2)) {
            return;
        }
        Set<Map.Entry> setEntrySet = map.entrySet();
        HashSet hashSet = new HashSet();
        for (Map.Entry entry : setEntrySet) {
            String str3 = (String) entry.getKey();
            if (str2.equals((String) entry.getValue())) {
                hashSet.add(str3);
            }
        }
        AccountLogger.log(TAG, "invalidateAuthTokenCache>>>needRemoveTypes=" + hashSet);
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            map.remove((String) it.next());
        }
    }

    protected String readAuthTokenInternal(UserAccounts userAccounts, Account account, String str) {
        String str2;
        synchronized (userAccounts.cacheLock) {
            HashMap<String, String> authTokensForAccountFromDatabaseLocked = (HashMap) userAccounts.authTokenCache.get(account);
            if (authTokensForAccountFromDatabaseLocked == null) {
                authTokensForAccountFromDatabaseLocked = readAuthTokensForAccountFromDatabaseLocked(userAccounts.openHelper.getSecureDatabase(this.mContext), account);
                userAccounts.authTokenCache.put(account, authTokensForAccountFromDatabaseLocked);
            }
            str2 = authTokensForAccountFromDatabaseLocked.get(str);
        }
        return str2;
    }

    protected Map<String, String> readAllAuthTokenInternal(UserAccounts userAccounts, Account account) {
        HashMap<String, String> authTokensForAccountFromDatabaseLocked;
        synchronized (userAccounts.cacheLock) {
            authTokensForAccountFromDatabaseLocked = (HashMap) userAccounts.authTokenCache.get(account);
            if (authTokensForAccountFromDatabaseLocked == null) {
                authTokensForAccountFromDatabaseLocked = readAuthTokensForAccountFromDatabaseLocked(userAccounts.openHelper.getSecureDatabase(this.mContext), account);
                userAccounts.authTokenCache.put(account, authTokensForAccountFromDatabaseLocked);
            }
        }
        return authTokensForAccountFromDatabaseLocked;
    }

    protected String readUserDataInternal(UserAccounts userAccounts, Account account, String str) {
        String str2;
        synchronized (userAccounts.cacheLock) {
            HashMap<String, String> userDataForAccountFromDatabaseLocked = (HashMap) userAccounts.userDataCache.get(account);
            if (userDataForAccountFromDatabaseLocked == null || TextUtils.isEmpty(userDataForAccountFromDatabaseLocked.get(str))) {
                userDataForAccountFromDatabaseLocked = readUserDataForAccountFromDatabaseLocked(userAccounts.openHelper.getSecureDatabase(this.mContext), account);
                userAccounts.userDataCache.put(account, userDataForAccountFromDatabaseLocked);
            }
            str2 = userDataForAccountFromDatabaseLocked.get(str);
        }
        return str2;
    }

    protected Map<String, String> readAllUserDataInternal(UserAccounts userAccounts, Account account) {
        HashMap<String, String> userDataForAccountFromDatabaseLocked;
        synchronized (userAccounts.cacheLock) {
            userDataForAccountFromDatabaseLocked = (HashMap) userAccounts.userDataCache.get(account);
            if (userDataForAccountFromDatabaseLocked == null) {
                userDataForAccountFromDatabaseLocked = readUserDataForAccountFromDatabaseLocked(userAccounts.openHelper.getSecureDatabase(this.mContext), account);
                userAccounts.userDataCache.put(account, userDataForAccountFromDatabaseLocked);
            }
        }
        return userDataForAccountFromDatabaseLocked;
    }

    protected HashMap<String, String> readUserDataForAccountFromDatabaseLocked(SecureDatabase secureDatabase, Account account) {
        HashMap<String, String> map = new HashMap<>();
        Cursor cursorQuery = secureDatabase.query(false, "extras", COLUMNS_EXTRAS_KEY_AND_VALUE, "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)", new Object[]{account.name, account.type}, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                map.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        cursorQuery.close();
        return map;
    }

    protected HashMap<String, String> readAuthTokensForAccountFromDatabaseLocked(SecureDatabase secureDatabase, Account account) {
        HashMap<String, String> map = new HashMap<>();
        Cursor cursorQuery = secureDatabase.query(false, "authtokens", COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN, "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)", new Object[]{account.name, account.type}, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                map.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        cursorQuery.close();
        return map;
    }

    private int getCallingUid() {
        return this.mContext.getApplicationInfo().uid;
    }

    public void release() {
        AccountLogger.log(TAG, "release>>>");
        try {
            MessageHandler messageHandler = this.mMessageHandler;
            if (messageHandler != null) {
                messageHandler.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = this.mMessageThread;
            if (handlerThread != null) {
                handlerThread.quit();
            }
        } catch (Exception e) {
            AccountLogger.log(TAG, "release err", e);
        }
    }
}
