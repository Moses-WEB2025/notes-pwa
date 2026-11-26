package com.miui.notes.cloudservice;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSyncContext {
    private final AccountEntity mAccountEntity;
    private final Context mContext;
    private String mDeviceDigest;
    private final ExtendedAuthToken mExtToken;
    private FolderCache mFolderCache;
    private NoteMediaManager mMediaManager;
    private NoteCache mNoteCache;
    private final HashSet<String> mFilterTags = new HashSet<>();
    private boolean mIsResync = false;

    public NoteSyncContext(Context context, AccountEntity accountEntity, ExtendedAuthToken extendedAuthToken) {
        this.mContext = context;
        this.mAccountEntity = accountEntity;
        this.mExtToken = extendedAuthToken;
    }

    public Context getContext() {
        return this.mContext;
    }

    public AccountEntity getAccountEntity() {
        return this.mAccountEntity;
    }

    public ExtendedAuthToken getExtToken() {
        return this.mExtToken;
    }

    public Set<String> getFilterTags() {
        return this.mFilterTags;
    }

    public void setIsResync(boolean z) {
        this.mIsResync = z;
    }

    public boolean isResync() {
        return this.mIsResync;
    }

    public NoteCache getNoteCache() throws NoteSyncException {
        if (this.mNoteCache == null) {
            this.mNoteCache = NoteCache.create(this);
        }
        return this.mNoteCache;
    }

    public FolderCache getFolderCache() throws NoteSyncException {
        if (this.mFolderCache == null) {
            this.mFolderCache = FolderCache.create(this);
        }
        return this.mFolderCache;
    }

    public String getDeviceDigest() throws NoteSyncException {
        if (this.mDeviceDigest == null) {
            this.mDeviceDigest = "";
        }
        return this.mDeviceDigest;
    }

    public NoteMediaManager getMediaManager() {
        if (this.mMediaManager == null) {
            this.mMediaManager = new NoteMediaManager(this.mContext, this.mAccountEntity.getAccount());
        }
        return this.mMediaManager;
    }
}
