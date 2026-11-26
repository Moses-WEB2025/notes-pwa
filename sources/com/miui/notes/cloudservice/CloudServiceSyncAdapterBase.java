package com.miui.notes.cloudservice;

import android.content.Context;
import com.xiaomi.micloudsdk.sync.SyncAdapterBase;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CloudServiceSyncAdapterBase extends SyncAdapterBase {
    @Override // com.xiaomi.micloudsdk.sync.SyncAdapterBase
    protected String getErrorTitle() {
        return null;
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncAdapterBase
    protected String getForbiddenErrorText() {
        return null;
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncAdapterBase
    protected String getNotAcceptableErrorText() {
        return null;
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncAdapterBase
    protected String getUnauthorizedErrorText() {
        return null;
    }

    public CloudServiceSyncAdapterBase(Context context, boolean z, String str) {
        super(context, z, str);
    }
}
