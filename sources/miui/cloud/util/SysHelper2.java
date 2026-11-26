package miui.cloud.util;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.miui.notes.provider.NotesDatabaseHelper;

/* JADX INFO: loaded from: classes5.dex */
public class SysHelper2 extends SysHelper {
    public static Intent getCustomSyncSettings(Context context, Account account, String str) {
        Intent intent = new Intent(str + ".SYNC_SETTINGS");
        intent.putExtra(NotesDatabaseHelper.TABLE.ACCOUNT, account);
        intent.putExtra("authority", str);
        if (context.getPackageManager().queryIntentActivities(intent, 32).isEmpty()) {
            return null;
        }
        return intent;
    }
}
