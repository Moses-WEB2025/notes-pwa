package com.xiaomi.mimoverbackup.backup;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiMoverBackup {
    void getBackupInfo(Context context, Bundle bundle);

    void onBackup(Context context, ParcelFileDescriptor parcelFileDescriptor);

    void onCancel();

    void onRestore(Context context, ParcelFileDescriptor parcelFileDescriptor);
}
