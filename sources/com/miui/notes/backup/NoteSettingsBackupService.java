package com.miui.notes.backup;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.settingsdk.backup.CloudBackupServiceBase;
import com.xiaomi.settingsdk.backup.ICloudBackup;
import kotlin.Metadata;

/* JADX INFO: compiled from: NoteSettingsBackupService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014¨\u0006\u0006"}, d2 = {"Lcom/miui/notes/backup/NoteSettingsBackupService;", "Lcom/xiaomi/settingsdk/backup/CloudBackupServiceBase;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getBackupImpl", "Lcom/xiaomi/settingsdk/backup/ICloudBackup;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteSettingsBackupService extends CloudBackupServiceBase {
    protected ICloudBackup getBackupImpl() {
        return new NoteSettingsBackupImpl();
    }
}
