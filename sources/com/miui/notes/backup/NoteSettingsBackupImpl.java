package com.miui.notes.backup;

import android.content.Context;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.fragment.AppSettingsFragment;
import com.xiaomi.settingsdk.backup.ICloudBackup;
import com.xiaomi.settingsdk.backup.data.DataPackage;
import com.xiaomi.settingsdk.backup.data.KeyStringSettingItem;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteSettingsBackupService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u000e\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\u0011"}, d2 = {"Lcom/miui/notes/backup/NoteSettingsBackupImpl;", "Lcom/xiaomi/settingsdk/backup/ICloudBackup;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onBackupSettings", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "dataPackage", "Lcom/xiaomi/settingsdk/backup/data/DataPackage;", "backupGridModeSetting", "onRestoreSettings", "packageVersion", "", "restoreGridMode", "getCurrentVersion", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteSettingsBackupImpl implements ICloudBackup {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_BIG_SCREEN_LAYOUTS = "big_screen_layouts";
    private static final String KEY_FONT_SIZE = "font_size";
    private static final String KEY_IS_GRID_MODE = "is_grid_mode";
    private static final String KEY_QUICK_DELETE = "quick_delete";
    private static final String KEY_SORT_WAY = "sort_way";
    public static final String tag = "NoteSettingsBackupService";

    public int getCurrentVersion(Context context) {
        return 1;
    }

    public void onBackupSettings(Context context, DataPackage dataPackage) {
        Intrinsics.checkNotNullParameter(dataPackage, "dataPackage");
        Logger.INSTANCE.d(tag, "onBackupSettings");
        Companion companion = INSTANCE;
        companion.backupFontSizeSetting(context, dataPackage);
        companion.backupQuickDelete(context, dataPackage);
        companion.backupSortWay(context, dataPackage);
        backupGridModeSetting(context, dataPackage);
        companion.backupBigScreenLayouts(context, dataPackage);
    }

    private final void backupGridModeSetting(Context context, DataPackage dataPackage) {
        dataPackage.addKeyValue(KEY_IS_GRID_MODE, String.valueOf(PreferenceUtils.getGridMode(context)));
    }

    public void onRestoreSettings(Context context, DataPackage dataPackage, int packageVersion) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dataPackage, "dataPackage");
        Logger.INSTANCE.d(tag, "onRestoreSettings");
        Companion companion = INSTANCE;
        companion.restoreFontSizeSetting(context, dataPackage);
        companion.restoreQuickDelete(context, dataPackage);
        companion.restoreSortWay(context, dataPackage);
        restoreGridMode(context, dataPackage);
        companion.restoreBigScreenLayouts(context, dataPackage);
    }

    private final void restoreGridMode(Context context, DataPackage dataPackage) {
        String stringValueFromPackage = INSTANCE.getStringValueFromPackage(dataPackage, KEY_IS_GRID_MODE);
        if (TextUtils.isEmpty(stringValueFromPackage)) {
            return;
        }
        PreferenceUtils.setGridMode(context, Boolean.parseBoolean(stringValueFromPackage) || !LiteUtils.isLiteOrMiddle());
    }

    /* JADX INFO: compiled from: NoteSettingsBackupService.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0011\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0012\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0013\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u0002J\u001a\u0010\u0017\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0019\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/backup/NoteSettingsBackupImpl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tag", "", "KEY_FONT_SIZE", "KEY_QUICK_DELETE", "KEY_SORT_WAY", "KEY_IS_GRID_MODE", "KEY_BIG_SCREEN_LAYOUTS", "backupFontSizeSetting", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "dataPackage", "Lcom/xiaomi/settingsdk/backup/data/DataPackage;", "backupQuickDelete", "backupSortWay", "backupBigScreenLayouts", "restoreFontSizeSetting", "getStringValueFromPackage", "key", "restoreQuickDelete", "restoreSortWay", "restoreBigScreenLayouts", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void backupFontSizeSetting(Context context, DataPackage dataPackage) {
            dataPackage.addKeyValue(NoteSettingsBackupImpl.KEY_FONT_SIZE, String.valueOf(PreferenceUtils.getFontSize(context, 1)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void backupQuickDelete(Context context, DataPackage dataPackage) {
            dataPackage.addKeyValue(NoteSettingsBackupImpl.KEY_QUICK_DELETE, String.valueOf(PreferenceUtils.getSkipConfirmForQuickDelete(context)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void backupSortWay(Context context, DataPackage dataPackage) {
            dataPackage.addKeyValue(NoteSettingsBackupImpl.KEY_SORT_WAY, String.valueOf(PreferenceUtils.getNoteSortWay(context, 1)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void backupBigScreenLayouts(Context context, DataPackage dataPackage) {
            dataPackage.addKeyValue(NoteSettingsBackupImpl.KEY_BIG_SCREEN_LAYOUTS, String.valueOf(PreferenceUtils.getNoteHomePageArrayWay(context, 0)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void restoreFontSizeSetting(Context context, DataPackage dataPackage) {
            String stringValueFromPackage = getStringValueFromPackage(dataPackage, NoteSettingsBackupImpl.KEY_FONT_SIZE);
            if (TextUtils.isEmpty(stringValueFromPackage)) {
                return;
            }
            AppSettingsFragment.Companion companion = AppSettingsFragment.INSTANCE;
            Intrinsics.checkNotNull(stringValueFromPackage);
            companion.saveFontSize(context, Integer.parseInt(stringValueFromPackage));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getStringValueFromPackage(DataPackage dataPackage, String key) {
            KeyStringSettingItem keyStringSettingItem = dataPackage.get(key);
            if (keyStringSettingItem instanceof KeyStringSettingItem) {
                return (String) keyStringSettingItem.getValue();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void restoreQuickDelete(Context context, DataPackage dataPackage) {
            String stringValueFromPackage = getStringValueFromPackage(dataPackage, NoteSettingsBackupImpl.KEY_QUICK_DELETE);
            if (TextUtils.isEmpty(stringValueFromPackage)) {
                return;
            }
            PreferenceUtils.setSkipConfirmForQuickDelete(context, Boolean.parseBoolean(stringValueFromPackage));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void restoreSortWay(Context context, DataPackage dataPackage) {
            String stringValueFromPackage = getStringValueFromPackage(dataPackage, NoteSettingsBackupImpl.KEY_SORT_WAY);
            if (TextUtils.isEmpty(stringValueFromPackage)) {
                return;
            }
            Intrinsics.checkNotNull(stringValueFromPackage);
            PreferenceUtils.setNoteSortWay(context, Integer.parseInt(stringValueFromPackage));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void restoreBigScreenLayouts(Context context, DataPackage dataPackage) {
            String stringValueFromPackage = getStringValueFromPackage(dataPackage, NoteSettingsBackupImpl.KEY_BIG_SCREEN_LAYOUTS);
            if (TextUtils.isEmpty(stringValueFromPackage)) {
                return;
            }
            Intrinsics.checkNotNull(stringValueFromPackage);
            PreferenceUtils.setNoteHomePageArrayWay(context, Integer.parseInt(stringValueFromPackage));
        }
    }
}
