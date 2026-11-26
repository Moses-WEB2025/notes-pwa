package com.miui.todo.feature.remind;

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
import android.widget.ArrayAdapter;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.tool.DualScreenManager;
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
import com.miui.notes.ui.AlarmAlertHelper;
import com.miui.notes.ui.AlarmReceiver;
import com.miui.todo.constant.TodoIntent;
import defpackage.R2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class TodoAlarmAlertActivity extends BaseAlarmAlertActivity {
    private final String EXTRA_TODO_KEY = TodoIntent.EXTRA_TODO_ID;
    private final String mChannelId = "todo_alert_id";

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        TAG = "TodoAlarmAlertActivity";
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
        boolean z = !this.mBundles.isEmpty() && bundle == null && AlarmReceiver.ACTION_LAUNCH_TODO_ALARM.equals(getIntent().getAction());
        initialize(z);
        if (z) {
            playMusic();
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_ALARM_RESULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mPcAlarmStateReceiver, intentFilter2);
        notifyToPc();
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
        notifyToPc();
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getNotificationTag() {
        return TAG;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getAlarmChannelId() {
        return "todo_alert_id";
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getViewDataAction() {
        return TodoIntent.ACTION_INSERT_OR_EDIT;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getExtraDataKey() {
        return TodoIntent.EXTRA_TODO_ID;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    public String getExtraDataId() {
        return AlarmAlertHelper.EXTRA_TODO_ID;
    }

    @Override // com.miui.common.base.BaseAlarmAlertActivity
    protected BaseAlarmAlertActivity.BaseDialogFragment createDialogFragment() {
        return new ListDialogFragment();
    }

    private void notifyToPc() {
        if (DualScreenManager.INSTANCE.isPcActivityLiving()) {
            Intent intent = new Intent();
            intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_TODO_ALARM);
            intent.putExtra(FrameConstant.PARAM_SCREEN_ID, 0);
            intent.putParcelableArrayListExtra(BaseAlarmAlertActivity.EXTRA_FORMATTED_BUNDLES, this.mBundles);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    private boolean addIntent(Intent intent) {
        String action = intent.getAction();
        if (((this.mBundles == null ? 0 : this.mBundles.size()) != 0) && ("com.miui.notes.ui.AlarmAlertActivity.REPEAT".equals(action) || "com.miui.notes.ui.AlarmAlertActivity.VIEW".equals(action))) {
            Logger.INSTANCE.i(TAG, "addIntent failed: action=" + action);
            return false;
        }
        try {
            if (this.mBundles == null) {
                Logger.INSTANCE.i(TAG, "mBundles is null");
                return false;
            }
            ArrayList<Bundle> parcelableArrayListExtra = intent.getParcelableArrayListExtra(Notes.Intents.INTENT_EXTRA_SNIPPET_LIST);
            if (parcelableArrayListExtra != null && parcelableArrayListExtra.size() != 0) {
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
                Logger.INSTANCE.i(TAG, "mBundles: " + this.mBundles.toString());
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
        String string = getString(R.string.todo_notify_alarm_title);
        notificationManager.deleteNotificationChannel("todo_alert_id");
        NotificationChannel notificationChannel = new NotificationChannel("todo_alert_id", string, 3);
        notificationChannel.enableLights(true);
        if (z) {
            notificationChannel.setSound(null, null);
        }
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(this, "todo_alert_id");
        builder.addAction(new Notification.Action.Builder(Icon.createWithResource(NoteApp.getInstance(), R.drawable.stat_action_mute), getString(R.string.notealert_ok), broadcast).build());
        Bundle bundle = new Bundle();
        bundle.putBoolean("miui.showAction", true);
        bundle.putBoolean("miui.expandableOnKeyguard", true);
        builder.setContentTitle(getString(R.string.todo_notify_alarm_title));
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
        Logger.INSTANCE.i(TAG, "onResume");
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mPcAlarmStateReceiver);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(BaseAlarmAlertActivity.EXTRA_FORMATTED_BUNDLES, this.mBundles);
    }

    public static class ListDialogFragment extends BaseAlarmAlertActivity.BaseDialogFragment {
        ArrayAdapter<String> adapter;
        ArrayList<Bundle> bundles;

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment
        public void setBundles(ArrayList<Bundle> arrayList) {
            if (arrayList != null) {
                this.bundles = (ArrayList) arrayList.clone();
            }
            ArrayAdapter<String> arrayAdapter = this.adapter;
            if (arrayAdapter != null) {
                arrayAdapter.clear();
                this.adapter.addAll(getListItems());
                this.adapter.notifyDataSetChanged();
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            Logger.INSTANCE.d(BaseAlarmAlertActivity.TAG, "ListDialogFragment onCreateDialog");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.todo_notify_alarm_title);
            builder.setNegativeButton(R.string.todoalert_done, this);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.todo_alert_text_view, R.id.tv_todo_resume, getListItems());
            this.adapter = arrayAdapter;
            builder.setAdapter(arrayAdapter, this);
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

        private List<String> getListItems() {
            ArrayList arrayList = new ArrayList();
            ArrayList<Bundle> arrayList2 = this.bundles;
            if (arrayList2 != null) {
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    CharSequence charSequence = this.bundles.get(i).getCharSequence(BaseAlarmAlertActivity.EXTRA_FORMATTED_SNIPPET);
                    arrayList.add(charSequence != null ? charSequence.toString() : "");
                }
            }
            return arrayList;
        }

        @Override // com.miui.common.base.BaseAlarmAlertActivity.BaseDialogFragment, android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Logger.INSTANCE.d(BaseAlarmAlertActivity.TAG, "onClick activity: " + this.activity);
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
