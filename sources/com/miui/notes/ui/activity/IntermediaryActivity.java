package com.miui.notes.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.tool.tapshareutils.TapShareRestoreHelper;
import com.miui.notes.ui.NotesListActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class IntermediaryActivity extends BaseActivity {
    private static final int DUPLICATE_CHECK_INTERVAL_MS = 10000;
    private static final String EXTRA_IS_APPLE_DEVICE = "isAppleDevice";
    private static final String EXTRA_SAVE_WITHOUT_REDIRECTION = "SAVE_WITHOUT_REDIRECTION";
    private static final String EXTRA_TAP_SHARE_BATCH_RESTORE = "EXTRA_TAP_SHARE_BATCH_RESTORE";
    private static final String EXTRA_TAP_SHARE_URIS = "tap_share_uris";
    private static final String TAG = "IntermediaryActivity";
    private static String sLastProcessedIntentData;
    private static long sLastProcessedTime;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Logger.INSTANCE.d(TAG, "onCreate intent: " + intent + ", action=" + intent.getAction() + ", type=" + intent.getType() + ", extras=" + intent.getExtras());
        String action = intent.getAction();
        boolean z = "android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action);
        if (intent.getComponent() != null && !z && !isCallerAllowed()) {
            Logger.INSTANCE.e(TAG, "Blocked direct component call from untrusted caller");
            finish();
            return;
        }
        IntentData intentDataExtractIntentData = extractIntentData(intent);
        if (!intentDataExtractIntentData.isTapShareLink) {
            showLaunchedAppToast();
        }
        if (intentDataExtractIntentData.isTapShareLink && isDuplicateTapShareIntent(intent)) {
            Logger.INSTANCE.d(TAG, "Duplicate TapShare intent detected, ignoring");
            finish();
            return;
        }
        if (intentDataExtractIntentData.isTapShareLink) {
            recordProcessedIntent(intent);
        }
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_SAVE_WITHOUT_REDIRECTION, false);
        Logger.INSTANCE.d(TAG, "SAVE_WITHOUT_REDIRECTION: " + booleanExtra);
        if (intentDataExtractIntentData.isTapShareLink && booleanExtra) {
            sendCancelPrivateActivityBroadcast();
            handleBackgroundTapShareRestore(intentDataExtractIntentData);
            finishAndRemoveTask();
        } else {
            if (shouldProcessIntent(intentDataExtractIntentData)) {
                processShareIntent(intent, intentDataExtractIntentData);
            }
            finish();
        }
    }

    private void showLaunchedAppToast() {
        String launchedAppName = getLaunchedAppName();
        if (TextUtils.isEmpty(launchedAppName)) {
            return;
        }
        Toast.makeText(this, String.format(NoteApp.getInstance().getString(R.string.external_share_to_note), launchedAppName), 0).show();
    }

    private IntentData extractIntentData(Intent intent) {
        ArrayList parcelableArrayListExtra;
        Uri data = intent.getData();
        boolean z = false;
        boolean z2 = data != null && "minote".equals(data.getScheme());
        boolean z3 = data != null && "mishare".equals(data.getScheme());
        if (z3) {
            parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_TAP_SHARE_URIS);
            boolean booleanExtra = intent.getBooleanExtra(EXTRA_IS_APPLE_DEVICE, false);
            intent.setAction("android.intent.action.SEND");
            Logger.INSTANCE.d(TAG, "tapShareUris counts: " + (parcelableArrayListExtra != null ? parcelableArrayListExtra.size() : 0) + " isAppleDevice: " + booleanExtra);
            z = booleanExtra;
        } else {
            parcelableArrayListExtra = null;
        }
        return new IntentData(z2, z3, parcelableArrayListExtra, z);
    }

    private boolean shouldProcessIntent(IntentData intentData) {
        String action = getIntent().getAction();
        if (intentData.isMiuiPlus) {
            return false;
        }
        return "android.intent.action.SEND".equals(action) || "android.intent.action.SEND".equals(getIntent().getAction());
    }

    private void handleBackgroundTapShareRestore(IntentData intentData) {
        if (intentData.tapShareUris == null || intentData.tapShareUris.isEmpty()) {
            Logger.INSTANCE.e(TAG, "TapShare URIs are null or empty for background restore");
            return;
        }
        Logger.INSTANCE.d(TAG, "Starting background TapShare restore, count: " + intentData.tapShareUris.size());
        if (intentData.tapShareUris.size() == 1) {
            restoreSingleNoteInBackground(intentData.tapShareUris.get(0), intentData.isAppleDevice);
        } else {
            restoreMultipleNotesInBackground(intentData.tapShareUris);
        }
    }

    private void restoreSingleNoteInBackground(Uri uri, boolean z) {
        TapShareRestoreHelper.RestoreCallback restoreCallback = new TapShareRestoreHelper.RestoreCallback() { // from class: com.miui.notes.ui.activity.IntermediaryActivity.1
            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onMultipleSuccess(List<Long> list) {
            }

            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onSuccess(long j) {
                Logger.INSTANCE.d(IntermediaryActivity.TAG, "Background restore successful, note ID: " + j);
                IntermediaryActivity.this.savePendingTapShareNoteId(j);
                Toast.makeText(IntermediaryActivity.this.getApplicationContext(), IntermediaryActivity.this.getString(R.string.tapshare_restore_notes_success_toast), 0).show();
            }

            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onError(String str) {
                Logger.INSTANCE.e(IntermediaryActivity.TAG, "Background restore failed: " + str);
                Toast.makeText(IntermediaryActivity.this.getApplicationContext(), IntermediaryActivity.this.getString(R.string.tapshare_restore_notes_fail_toast), 0).show();
            }
        };
        if (z) {
            Logger.INSTANCE.d(TAG, "Restoring note from iOS device");
            TapShareRestoreHelper.restoreFromIOS(this, uri, restoreCallback);
        } else {
            Logger.INSTANCE.d(TAG, "Restoring note from Android device");
            TapShareRestoreHelper.restoreSingleNote(this, uri, restoreCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePendingTapShareNoteId(long j) {
        getSharedPreferences("tapshare_prefs", 0).edit().putLong("pending_note_id", j).apply();
        Logger.INSTANCE.d(TAG, "Saved pending TapShare note ID: " + j);
    }

    private void restoreMultipleNotesInBackground(List<Uri> list) {
        TapShareRestoreHelper.restoreMultipleNotes(this, list, new TapShareRestoreHelper.RestoreCallback() { // from class: com.miui.notes.ui.activity.IntermediaryActivity.2
            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onSuccess(long j) {
            }

            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onMultipleSuccess(List<Long> list2) {
                Logger.INSTANCE.d(IntermediaryActivity.TAG, "Background restore all successful, count: " + list2.size());
                Toast.makeText(IntermediaryActivity.this.getApplicationContext(), IntermediaryActivity.this.getString(R.string.tapshare_restore_notes_success_toast), 0).show();
            }

            @Override // com.miui.notes.tool.tapshareutils.TapShareRestoreHelper.RestoreCallback
            public void onError(String str) {
                Logger.INSTANCE.e(IntermediaryActivity.TAG, "Background restore failed: " + str);
                Toast.makeText(IntermediaryActivity.this.getApplicationContext(), IntermediaryActivity.this.getString(R.string.tapshare_restore_notes_fail_toast), 0).show();
            }
        });
    }

    private void processShareIntent(Intent intent, IntentData intentData) {
        setupIntentFlags(intent, determineTargetActivity(intentData));
        PasswordController.isSendAction.set(true);
        Logger.INSTANCE.d(TAG, "finish intermediary getAction: " + getIntent().getAction());
        if (Objects.equals(getIntent().getAction(), "android.intent.action.SEND")) {
            sendCancelPrivateActivityBroadcast();
        }
        Logger.INSTANCE.d(TAG, "finish: ");
        startActivitySafely(intent);
    }

    private Class<?> determineTargetActivity(IntentData intentData) {
        boolean zIsPadMode = RomUtils.isPadMode();
        if (intentData.isTapShareLink && zIsPadMode) {
            return NotesListActivity.class;
        }
        return (!intentData.isTapShareLink || intentData.tapShareUris == null || intentData.tapShareUris.size() <= 1) ? EditActivity.class : NotesListActivity.class;
    }

    private void setupIntentFlags(Intent intent, Class<?> cls) {
        ArrayList parcelableArrayListExtra;
        intent.setClass(this, cls);
        if (cls == NotesListActivity.class && (parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_TAP_SHARE_URIS)) != null && parcelableArrayListExtra.size() > 1) {
            intent.putExtra(EXTRA_TAP_SHARE_BATCH_RESTORE, true);
        }
        if (isInMultiWindowMode()) {
            return;
        }
        if (cls == EditActivity.class) {
            intent.addFlags(276856832);
            Logger.INSTANCE.d(TAG, "startEditActivity: ");
        } else {
            intent.addFlags(268468224);
            Logger.INSTANCE.d(TAG, "startNotesListActivity: ");
        }
    }

    private void sendCancelPrivateActivityBroadcast() {
        Intent intent = new Intent();
        intent.setAction(PasswordController.ACTIN_CANCEL);
        Logger.INSTANCE.d(TAG, "finish send broadcast: ");
        sendBroadcast(intent);
    }

    private void startActivitySafely(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "startActivity error:" + e);
            e.printStackTrace();
        }
    }

    private static class IntentData {
        final boolean isAppleDevice;
        final boolean isMiuiPlus;
        final boolean isTapShareLink;
        final List<Uri> tapShareUris;

        IntentData(boolean z, boolean z2, List<Uri> list, boolean z3) {
            this.isMiuiPlus = z;
            this.isTapShareLink = z2;
            this.tapShareUris = list;
            this.isAppleDevice = z3;
        }
    }

    public String getLaunchedAppName() {
        try {
            IBinder iBinder = (IBinder) Activity.class.getMethod("getActivityToken", new Class[0]).invoke(this, new Object[0]);
            Object objInvoke = ((ActivityManager) getSystemService("activity")).getClass().getMethod("getService", new Class[0]).invoke(null, new Object[0]);
            String str = (String) objInvoke.getClass().getMethod("getLaunchedFromPackage", IBinder.class).invoke(objInvoke, iBinder);
            PackageManager packageManager = getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getPackageInfo(str, 0).applicationInfo).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Logger.INSTANCE.d(TAG, "onDestroy: ");
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    public void finish() {
        super.finish();
    }

    private boolean isCallerAllowed() {
        String callingPackage = getCallingPackage();
        if (callingPackage != null) {
            if (callingPackage.equals(getPackageName())) {
                return true;
            }
            Logger.INSTANCE.e(TAG, "Rejected startActivityForResult from: " + callingPackage);
            return false;
        }
        Uri referrer = getReferrer();
        if (referrer == null || !"android-app".equals(referrer.getScheme())) {
            Logger.INSTANCE.e(TAG, "Rejected call with null referrer");
            return false;
        }
        String host = referrer.getHost();
        if (getPackageName().equals(host) || "com.android.systemui".equals(host) || AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME.equals(host) || "com.miui.mishare.connectivity".equals(host)) {
            return true;
        }
        Logger.INSTANCE.e(TAG, "Rejected direct call from: " + host);
        return false;
    }

    private boolean isDuplicateTapShareIntent(Intent intent) {
        if (sLastProcessedTime == 0 || sLastProcessedIntentData == null) {
            Logger.INSTANCE.d(TAG, "No previous intent recorded, not a duplicate");
            return false;
        }
        if (System.currentTimeMillis() - sLastProcessedTime > 10000) {
            return false;
        }
        String intentKeyData = getIntentKeyData(intent);
        if (intentKeyData == null) {
            Logger.INSTANCE.d(TAG, "Current intent data is null, not a duplicate");
            return false;
        }
        return intentKeyData.equals(sLastProcessedIntentData);
    }

    private void recordProcessedIntent(Intent intent) {
        sLastProcessedTime = System.currentTimeMillis();
        sLastProcessedIntentData = getIntentKeyData(intent);
        Logger.INSTANCE.d(TAG, "Recorded intent for duplicate check: " + sLastProcessedIntentData);
    }

    private String getIntentKeyData(Intent intent) {
        if (intent == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Uri data = intent.getData();
        if (data != null) {
            sb.append("scheme:").append(data.getScheme()).append(",path:").append(data.getPath());
        }
        String action = intent.getAction();
        if (action != null) {
            sb.append(",action:").append(action);
        }
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_TAP_SHARE_URIS);
        if (parcelableArrayListExtra != null) {
            sb.append(",uris_count:").append(parcelableArrayListExtra.size());
            if (!parcelableArrayListExtra.isEmpty()) {
                sb.append(",first_uri:").append(((Uri) parcelableArrayListExtra.get(0)).toString());
            }
        }
        return sb.toString();
    }
}
