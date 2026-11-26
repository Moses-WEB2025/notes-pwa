package com.miui.notes.ui;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import androidx.fragment.app.Fragment;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.NotesUtils;
import com.miui.notes.tool.PreferenceUtils;
import defpackage.R2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class AlarmAlertActivity extends BaseAlarmAlertActivity {
    private static final String TAG = "AlarmAlertActivity";
    private final String EXTRA_NOTE_KEY = "android.intent.extra.UID";
    private String mChannelId = "notes_alert_id";

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Logger.INSTANCE.i(TAG, "onCreate");
        requestWindowFeature(1);
        super.onCreate(bundle);
        Window window = getWindow();
        window.addFlags(589824);
        if (((KeyguardManager) getSystemService("keyguard")).isKeyguardLocked()) {
            window.addFlags(1048576);
        }
        setTranslucentSystemBars();
        this.mAudioManager = (AudioManager) getSystemService(AITextWidgetStat.TYPE_AUDIO);
        IntentFilter intentFilter = new IntentFilter("com.miui.notes.ui.AlarmAlertActivity.SKIP");
        if (Utils.isAboveSDK34()) {
            registerReceiver(this.mAlarmReceiver, intentFilter, 2);
        } else {
            registerReceiver(this.mAlarmReceiver, intentFilter);
        }
        if (bundle == null) {
            this.mBundles = new ArrayList<>();
            addIntent(getIntent());
        } else {
            this.mBundles = bundle.getParcelableArrayList(BaseAlarmAlertActivity.EXTRA_FORMATTED_BUNDLES);
            Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG);
            if (fragmentFindFragmentByTag != null) {
                getSupportFragmentManager().beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                getSupportFragmentManager().executePendingTransactions();
            }
        }
        boolean z = !this.mBundles.isEmpty() && bundle == null;
        initialize(z);
        if (z) {
            playMusic();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Logger.INSTANCE.i(TAG, "onNewIntent: intent=" + intent.toString());
        super.onNewIntent(intent);
        setTranslucentSystemBars();
        setIntent(intent);
        boolean zAddIntent = addIntent(intent);
        initialize(false);
        if (zAddIntent) {
            resetMusic();
        } else {
            muteMusic();
            releaseWakeLock(this, this.mWakeLock);
        }
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getNotificationTag() {
        return TAG;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getAlarmChannelId() {
        return this.mChannelId;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getViewDataAction() {
        return "android.intent.action.VIEW";
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getExtraDataKey() {
        return "android.intent.extra.UID";
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getExtraDataId() {
        return AlarmAlertHelper.EXTRA_NOTE_ID;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    protected BaseAlarmAlertActivity.BaseDialogFragment createDialogFragment() {
        if (this.mBundles.size() == 1) {
            return new DetailDialogFragment();
        }
        return new ListDialogFragment();
    }

    private boolean addIntent(Intent intent) {
        String action = intent.getAction();
        if (((this.mBundles == null ? 0 : this.mBundles.size()) != 0) && ("com.miui.notes.ui.AlarmAlertActivity.REPEAT".equals(action) || "com.miui.notes.ui.AlarmAlertActivity.VIEW".equals(action))) {
            Logger.INSTANCE.i(TAG, "addIntent failed: action=" + action);
            return false;
        }
        try {
            ArrayList<Bundle> parcelableArrayListExtra = intent.getParcelableArrayListExtra(Notes.Intents.INTENT_EXTRA_SNIPPET_LIST);
            if (parcelableArrayListExtra != null && parcelableArrayListExtra.size() != 0) {
                if (this.mBundles == null) {
                    this.mBundles = new ArrayList<>();
                }
                HashSet hashSet = new HashSet();
                Iterator<Bundle> it = this.mBundles.iterator();
                while (it.hasNext()) {
                    hashSet.add(Long.valueOf(it.next().getLong(getExtraDataId())));
                }
                for (Bundle bundle : parcelableArrayListExtra) {
                    if (!hashSet.contains(Long.valueOf(bundle.getLong(getExtraDataId())))) {
                        this.mBundles.add(bundle);
                    }
                }
                return true;
            }
            Logger.INSTANCE.i(TAG, "addIntent failed: data is null");
            return false;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Fail to add intent, uri=" + intent.getData() + ",exception:" + e);
            return false;
        }
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    protected void showAlarmNotification(boolean z) {
        Logger.INSTANCE.i(TAG, "showAlarmNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Intent intent = new Intent(getIntent());
        intent.setAction("com.miui.notes.ui.AlarmAlertActivity.VIEW");
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 201326592);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, new Intent("com.miui.notes.ui.AlarmAlertActivity.SKIP"), 201326592);
        String string = getString(R.string.notify_alarm_title);
        notificationManager.deleteNotificationChannel(this.mChannelId);
        NotificationChannel notificationChannel = new NotificationChannel(this.mChannelId, string, 3);
        notificationChannel.enableLights(true);
        if (z) {
            notificationChannel.setSound(null, null);
        }
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(this, this.mChannelId);
        builder.addAction(new Notification.Action.Builder(Icon.createWithResource(NoteApp.getInstance(), R.drawable.stat_action_mute), getString(R.string.notealert_ok), broadcast).build());
        Bundle bundle = new Bundle();
        bundle.putBoolean("miui.showAction", true);
        bundle.putBoolean("miui.expandableOnKeyguard", true);
        builder.setContentTitle(getString(R.string.notify_alarm_title));
        builder.setContentText(getString(R.string.to_view_alarm_content));
        builder.setSmallIcon(R.drawable.stat_notify_alarm);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setContentIntent(activity);
        builder.addExtras(bundle);
        notificationManager.notify(TAG, 0, builder.build());
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        setTranslucentSystemBars();
        FloatWindowManagerAgent.changeFullLayoutToGuideService();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setTranslucentSystemBars();
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        unregisterReceiver(this.mAlarmReceiver);
        cancelAlarmNotification();
        muteMusic();
        releaseWakeLock(this, this.mWakeLock);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(BaseAlarmAlertActivity.EXTRA_FORMATTED_BUNDLES, this.mBundles);
    }

    public static class DetailDialogFragment extends BaseAlarmAlertActivity.BaseDialogFragment {
        Bundle bundle;

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment
        public void setBundles(ArrayList<Bundle> arrayList) {
            if (arrayList != null) {
                this.bundle = arrayList.get(0);
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.notify_alarm_title);
            builder.setEnableDialogImmersive(false);
            builder.setMessage(this.bundle.getCharSequence(BaseAlarmAlertActivity.EXTRA_FORMATTED_SNIPPET));
            builder.setPositiveButton(R.string.notealert_enter, this);
            builder.setNegativeButton(R.string.notealert_ok, this);
            AlertDialog alertDialogCreate = builder.create();
            if (alertDialogCreate.getWindow() != null) {
                alertDialogCreate.getWindow().addFlags(524288);
                if (Settings.canDrawOverlays(NoteApp.getInstance()) && !NotesUtils.isInLockState(NoteApp.getInstance()) && (PreferenceUtils.getIsFullWindow(getContext()) || com.miui.notes.ai.utils.PreferenceUtils.isAiFloatingWindowShowing(getContext()))) {
                    alertDialogCreate.getWindow().setType(R2.attr.preferenceCheckableItemSetMaskPaddingEnd);
                }
                if (isAdded() && UIUtils.isInFullWindowGestureMode(getActivity())) {
                    alertDialogCreate.getWindow().addFlags(Integer.MIN_VALUE);
                }
            }
            return alertDialogCreate;
        }

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment, android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -2) {
                this.activity.mBundles.clear();
            } else if (i == -1) {
                openData(this.bundle);
            }
            super.onClick(dialogInterface, i);
        }
    }

    public static class ListDialogFragment extends BaseAlarmAlertActivity.BaseDialogFragment {
        ArrayList<Bundle> bundles;

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment
        public void setBundles(ArrayList<Bundle> arrayList) {
            if (arrayList != null) {
                this.bundles = (ArrayList) arrayList.clone();
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.notify_alarm_title);
            builder.setNegativeButton(R.string.notealert_ok, this);
            builder.setItems(getListItems(), this);
            AlertDialog alertDialogCreate = builder.create();
            if (alertDialogCreate.getWindow() != null) {
                alertDialogCreate.getWindow().addFlags(524288);
                if (Settings.canDrawOverlays(NoteApp.getInstance()) && !NotesUtils.isInLockState(NoteApp.getInstance()) && (PreferenceUtils.getIsFullWindow(getContext()) || com.miui.notes.ai.utils.PreferenceUtils.isAiFloatingWindowShowing(getContext()))) {
                    alertDialogCreate.getWindow().setType(R2.attr.preferenceCheckableItemSetMaskPaddingEnd);
                }
                if (isAdded() && UIUtils.isInFullWindowGestureMode(getActivity())) {
                    alertDialogCreate.getWindow().addFlags(Integer.MIN_VALUE);
                }
            }
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            return alertDialogCreate;
        }

        private CharSequence[] getListItems() {
            CharSequence[] charSequenceArr = new CharSequence[this.bundles.size()];
            int size = this.bundles.size();
            for (int i = 0; i < size; i++) {
                charSequenceArr[i] = this.bundles.get(i).getCharSequence(BaseAlarmAlertActivity.EXTRA_FORMATTED_SNIPPET);
            }
            return charSequenceArr;
        }

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment, android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Logger.INSTANCE.d(AlarmAlertActivity.TAG, "ListDialogFragment onClick: which=" + i);
            if (this.activity != null && this.activity.mBundles != null) {
                if (i >= 0) {
                    openData(this.bundles.get(i));
                    this.activity.initialize(false);
                } else if (i == -2) {
                    this.activity.mBundles.clear();
                }
            }
            super.onClick(dialogInterface, i);
        }
    }
}
