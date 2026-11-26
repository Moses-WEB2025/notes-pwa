package com.xiaomi.micloudsdk.micloudrichmedia;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.NetworkErrorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudParameterError;
import com.xiaomi.micloudsdk.exception.CloudRichMediaServerException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.FileTooLargeException;
import com.xiaomi.micloudsdk.exception.RichmediaServerFailureException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudRichMediaManager2 {
    private static final String AUTH_TOKEN_TYPE = "micfile";
    private static final String TAG = "MiCloudRichMediaManager";
    private Account mAccount;
    private ExtendedAuthToken mAuthToken;
    private Context mContext;
    private Utils mMiCloudRichUtils;
    private String mUserId;

    public MiCloudRichMediaManager2(Context context, String str, ExtendedAuthToken extendedAuthToken, boolean z) {
        this(context, str, extendedAuthToken, null, z);
    }

    public MiCloudRichMediaManager2(Context context, String str, ExtendedAuthToken extendedAuthToken, String str2, boolean z) {
        this.mContext = context;
        this.mAuthToken = extendedAuthToken;
        Utils utils = new Utils(this.mContext, str, str2, z);
        this.mMiCloudRichUtils = utils;
        utils.updateToken(extendedAuthToken);
        this.mUserId = str;
    }

    public MiCloudRichMediaManager2(Context context, Account account, boolean z) {
        if (account == null || TextUtils.isEmpty(account.name) || TextUtils.isEmpty(account.type)) {
            throw new IllegalArgumentException("The account should not be null" + account);
        }
        this.mAccount = account;
        this.mContext = context;
        this.mUserId = account.name;
        this.mMiCloudRichUtils = new Utils(this.mContext, account.name, z);
    }

    private ExtendedAuthToken initAuthToken() throws IOException {
        ExtendedAuthToken extendedAuthToken = this.mAuthToken;
        if (extendedAuthToken != null) {
            return extendedAuthToken;
        }
        AccountManagerFuture<Bundle> authToken = AccountManager.get(this.mContext).getAuthToken(this.mAccount, AUTH_TOKEN_TYPE, true, null, null);
        try {
            if (authToken != null) {
                if (authToken.getResult() != null) {
                    ExtendedAuthToken extendedAuthToken2 = ExtendedAuthToken.parse(authToken.getResult().getString("authtoken"));
                    this.mAuthToken = extendedAuthToken2;
                    if (extendedAuthToken2 != null) {
                        this.mMiCloudRichUtils.updateToken(extendedAuthToken2);
                        return this.mAuthToken;
                    }
                } else {
                    log("getAuthToken: future getResult is null");
                }
            } else {
                log("getAuthToken: future is null");
            }
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        } catch (OperationCanceledException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        throw new IOException("failed to get auth token");
    }

    public UploadResult upload(UploadEntity uploadEntity) throws NetworkErrorException, CloudRichMediaServerException, FileTooLargeException, IOException, CloudParameterError {
        return upload(uploadEntity, null);
    }

    public UploadResult upload(UploadEntity uploadEntity, Collection<String> collection) throws NetworkErrorException, CloudRichMediaServerException, IOException, FileTooLargeException, CloudParameterError {
        initAuthToken();
        try {
            return this.mMiCloudRichUtils.upload(uploadEntity, collection);
        } catch (CloudServerException e) {
            if (e.statusCode == 401 && this.mAccount != null) {
                invalidateAuthToken();
                try {
                    return this.mMiCloudRichUtils.upload(uploadEntity, collection);
                } catch (CloudServerException e2) {
                    throw new CloudRichMediaServerException(e2.statusCode);
                } catch (RichmediaServerFailureException e3) {
                    log("upload:" + e3);
                    return null;
                }
            }
            throw new CloudRichMediaServerException(e.getStatusCode());
        } catch (RichmediaServerFailureException e4) {
            log("upload:" + e4);
            return null;
        }
    }

    public MicloudDownload.Result download2(String str, String str2, File file) throws NetworkErrorException, CloudRichMediaServerException, IOException, CloudParameterError {
        initAuthToken();
        try {
            return this.mMiCloudRichUtils.download2(this.mUserId, str, str2, file);
        } catch (CloudServerException e) {
            if (e.statusCode == 401 && this.mAccount != null) {
                invalidateAuthToken();
                try {
                    return this.mMiCloudRichUtils.download2(this.mUserId, str, str2, file);
                } catch (CloudServerException e2) {
                    throw new CloudRichMediaServerException(e2.statusCode);
                } catch (RichmediaServerFailureException e3) {
                    log("download:" + e3);
                    return MicloudDownload.Result.Error;
                } catch (JSONException e4) {
                    log("download:" + e4);
                    return MicloudDownload.Result.Error;
                }
            }
            throw new CloudRichMediaServerException(e.statusCode);
        } catch (RichmediaServerFailureException e5) {
            log("download:" + e5);
            return MicloudDownload.Result.Error;
        } catch (JSONException e6) {
            log("download:" + e6);
            return MicloudDownload.Result.Error;
        }
    }

    public byte[] downloadFromPublicUrl(String str, String str2) throws CloudRichMediaServerException, IOException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            return this.mMiCloudRichUtils.downloadFromPublicUrl(str, str2);
        } catch (CloudServerException e) {
            throw new CloudRichMediaServerException(e.statusCode);
        }
    }

    public byte[] download2(String str, String str2) throws NetworkErrorException, CloudRichMediaServerException, IOException, CloudParameterError {
        initAuthToken();
        try {
            return this.mMiCloudRichUtils.download2(this.mUserId, str, str2);
        } catch (CloudServerException e) {
            if (e.statusCode == 401 && this.mAccount != null) {
                invalidateAuthToken();
                try {
                    return this.mMiCloudRichUtils.download2(this.mUserId, str, str2);
                } catch (CloudServerException e2) {
                    throw new CloudRichMediaServerException(e2.statusCode);
                } catch (RichmediaServerFailureException e3) {
                    log("download:" + e3);
                    return null;
                } catch (JSONException e4) {
                    log("download:" + e4);
                    return null;
                }
            }
            throw new CloudRichMediaServerException(e.statusCode);
        } catch (RichmediaServerFailureException e5) {
            log("download:" + e5);
            return null;
        } catch (JSONException e6) {
            log("download:" + e6);
            return null;
        }
    }

    public UploadResult getFileId(UploadEntity uploadEntity) throws CloudRichMediaServerException, IOException, CloudParameterError {
        return getFileId(uploadEntity, null);
    }

    public UploadResult getFileId(UploadEntity uploadEntity, Collection<String> collection) throws CloudRichMediaServerException, IOException, CloudParameterError {
        initAuthToken();
        try {
            return this.mMiCloudRichUtils.getFileId(uploadEntity, collection);
        } catch (CloudServerException e) {
            if (e.statusCode == 401 && this.mAccount != null) {
                invalidateAuthToken();
                try {
                    return this.mMiCloudRichUtils.getFileId(uploadEntity, collection);
                } catch (CloudServerException e2) {
                    throw new CloudRichMediaServerException(e2.statusCode);
                } catch (RichmediaServerFailureException e3) {
                    log("getFileId:" + e3);
                    return null;
                } catch (JSONException e4) {
                    log("getFileId:" + e4);
                    return null;
                }
            }
            throw new CloudRichMediaServerException(e.statusCode);
        } catch (RichmediaServerFailureException e5) {
            log("getFileId:" + e5);
            return null;
        } catch (JSONException e6) {
            log("getFileId:" + e6);
            return null;
        }
    }

    public void updateAuthToken(ExtendedAuthToken extendedAuthToken) {
        this.mMiCloudRichUtils.updateToken(extendedAuthToken);
    }

    private void invalidateAuthToken() {
        log("invalidateAuthToken:MiCloud rich media token expired.");
        if (this.mAuthToken != null) {
            AccountManager.get(this.mContext).invalidateAuthToken(this.mAccount.type, this.mAuthToken.toPlain());
        }
        this.mAuthToken = null;
    }

    static final void log(String str) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, str);
        }
    }
}
