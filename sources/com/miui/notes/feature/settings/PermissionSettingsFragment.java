package com.miui.notes.feature.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.preference.Preference;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.RxStandardAsyncTask;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.Map;
import miuix.preference.PreferenceFragment;
import miuix.preference.TextPreference;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String PREFERENCE_PERMISSION_MANAGE_KEY_CONTACT = "pref_permission_manage_key_contact";
    private static final String PREFERENCE_PERMISSION_MANAGE_KEY_RECORDING = "pref_permission_manage_key_recording";
    private static final String PREFERENCE_PERMISSION_MANAGE_KEY_STORAGE = "pref_permission_manage_key_storage";
    private static final String TAG = "PermissionSettingsFragment";
    private TextPreference prefPermissionContact;
    private TextPreference prefPermissionRecording;
    private TextPreference prefPermissionStorage;
    private ActivityResultLauncher<String[]> requestXPermission;
    private Handler mHandler = new Handler();
    private RxStandardAsyncTask<Integer> mPermissionStatusGetTask = null;
    private RxStandardAsyncTask<Integer> task = null;

    static /* synthetic */ void lambda$initResultLauncher$1(Map map) {
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        addPreferencesFromResource(R.xml.permission_manage_preferences);
        TextPreference textPreference = (TextPreference) findPreference(PREFERENCE_PERMISSION_MANAGE_KEY_STORAGE);
        this.prefPermissionStorage = textPreference;
        if (textPreference != null && RomUtils.isPadDevice()) {
            this.prefPermissionStorage.setSummary(getString(R.string.permission_storage_desc_pad_new_v12));
        }
        if (this.prefPermissionStorage != null && Utils.isAboveSDK33()) {
            this.prefPermissionStorage.setTitle(R.string.access_photos_and_videos);
            this.prefPermissionStorage.setSummary(RomUtils.isPadDevice() ? R.string.permission_photos_desc_pad : R.string.permission_photos_desc);
        }
        this.prefPermissionRecording = (TextPreference) findPreference(PREFERENCE_PERMISSION_MANAGE_KEY_RECORDING);
        this.prefPermissionContact = (TextPreference) findPreference(PREFERENCE_PERMISSION_MANAGE_KEY_CONTACT);
        TextPreference textPreference2 = this.prefPermissionStorage;
        if (textPreference2 != null) {
            textPreference2.setOnPreferenceClickListener(this);
            this.prefPermissionStorage.setVisible(!LiteUtils.isSuperLite());
        }
        this.prefPermissionRecording.setOnPreferenceClickListener(this);
        this.prefPermissionContact.setOnPreferenceClickListener(this);
        this.prefPermissionRecording.setVisible((LiteUtils.isSuperLite() || AudioUtils.isSupportAIRecorder()) ? false : true);
        updatePermissionItems();
        initResultLauncher();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.feature.settings.PermissionSettingsFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onResume$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$0() {
        if (isAdded()) {
            updatePermissionItems();
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        Logger.INSTANCE.d(TAG, "onCreatePreferences");
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Logger.INSTANCE.d(TAG, "onDestroyView");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.INSTANCE.d(TAG, "onDestroy");
        RxStandardAsyncTask<Integer> rxStandardAsyncTask = this.mPermissionStatusGetTask;
        if (rxStandardAsyncTask != null) {
            rxStandardAsyncTask.cancelAll();
            this.mPermissionStatusGetTask = null;
        }
        RxStandardAsyncTask<Integer> rxStandardAsyncTask2 = this.task;
        if (rxStandardAsyncTask2 != null) {
            rxStandardAsyncTask2.cancelAll();
            this.task = null;
        }
        super.onDestroy();
    }

    private void updatePermissionItems() {
        setPermissionText(this.prefPermissionStorage, Utils.isAboveSDK33() ? "android.permission.READ_MEDIA_IMAGES" : "android.permission.WRITE_EXTERNAL_STORAGE");
        setPermissionText(this.prefPermissionContact, "android.permission.READ_CONTACTS");
        setPermissionText(this.prefPermissionRecording, "android.permission.RECORD_AUDIO");
    }

    public void setPermissionText(final TextPreference textPreference, final String str) {
        RxStandardAsyncTask<Integer> rxStandardAsyncTask = this.task;
        if (rxStandardAsyncTask != null) {
            rxStandardAsyncTask.cancel();
            this.task = null;
        }
        final WeakReference weakReference = new WeakReference(getContext());
        RxStandardAsyncTask<Integer> rxStandardAsyncTask2 = new RxStandardAsyncTask<Integer>(null, Schedulers.newThread(), AndroidSchedulers.mainThread()) { // from class: com.miui.notes.feature.settings.PermissionSettingsFragment.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.common.tool.RxStandardAsyncTask
            public Integer doInBackground() {
                Logger.INSTANCE.d(PermissionSettingsFragment.TAG, "RxStandardAsyncTask/doingBackground");
                Thread.currentThread().setName("PermissionSettingText");
                if (((Context) weakReference.get()) == null) {
                    Logger.INSTANCE.d(PermissionSettingsFragment.TAG, "setPermissionText context == null");
                    return -1;
                }
                return Integer.valueOf(PermissionUtils.getPermissionStatus(PermissionSettingsFragment.this.getContext(), str));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.miui.common.tool.RxStandardAsyncTask
            public void onPost(Integer num) {
                if (!PermissionSettingsFragment.this.isAdded() || PermissionSettingsFragment.this.getActivity() == null || PermissionSettingsFragment.this.getActivity().isFinishing()) {
                    return;
                }
                int iIntValue = num.intValue();
                if (iIntValue != -1) {
                    if (iIntValue != 0) {
                        if (iIntValue != 1) {
                            if (iIntValue != 2) {
                                Logger.INSTANCE.d(PermissionSettingsFragment.TAG, "setPermTextView： get permission status occurred error!");
                                return;
                            }
                        }
                    }
                    textPreference.setText(NoteApp.getInstance().getString(R.string.pref_permission_allowed));
                    return;
                }
                textPreference.setText(NoteApp.getInstance().getString(R.string.pref_permission_not_allowed));
            }
        };
        this.task = rxStandardAsyncTask2;
        rxStandardAsyncTask2.execute();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        key.hashCode();
        switch (key) {
            case "pref_permission_manage_key_storage":
                permissionStatusController(Utils.isAboveSDK33() ? "android.permission.READ_MEDIA_IMAGES" : "android.permission.WRITE_EXTERNAL_STORAGE");
                return true;
            case "pref_permission_manage_key_recording":
                permissionStatusController("android.permission.RECORD_AUDIO");
                return true;
            case "pref_permission_manage_key_contact":
                permissionStatusController("android.permission.READ_CONTACTS");
                return true;
            default:
                return false;
        }
    }

    private void permissionStatusController(final String str) {
        RxStandardAsyncTask<Integer> rxStandardAsyncTask = this.mPermissionStatusGetTask;
        if (rxStandardAsyncTask != null) {
            rxStandardAsyncTask.cancel();
            this.mPermissionStatusGetTask = null;
        }
        Context context = getContext();
        if (context == null) {
            return;
        }
        final WeakReference weakReference = new WeakReference(context);
        RxStandardAsyncTask<Integer> rxStandardAsyncTask2 = new RxStandardAsyncTask<Integer>(null, Schedulers.io(), AndroidSchedulers.mainThread()) { // from class: com.miui.notes.feature.settings.PermissionSettingsFragment.2
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.common.tool.RxStandardAsyncTask
            public Integer doInBackground() {
                Logger.INSTANCE.d(PermissionSettingsFragment.TAG, "RxStandardAsyncTask/doingBackground.......");
                if (((Context) weakReference.get()) == null) {
                    return -1;
                }
                return Integer.valueOf(PermissionUtils.getPermissionStatus(PermissionSettingsFragment.this.getContext(), str));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.miui.common.tool.RxStandardAsyncTask
            public void onPost(Integer num) {
                if (!PermissionSettingsFragment.this.isAdded() || PermissionSettingsFragment.this.getActivity() == null || PermissionSettingsFragment.this.getActivity().isFinishing()) {
                    Logger.INSTANCE.d(PermissionSettingsFragment.TAG, " getActivity() == null");
                    return;
                }
                WeakReference weakReference2 = weakReference;
                if (weakReference2 == null) {
                    Logger.INSTANCE.d(PermissionSettingsFragment.TAG, " contextRef == null");
                    return;
                }
                Context context2 = (Context) weakReference2.get();
                Logger.INSTANCE.d(PermissionSettingsFragment.TAG, "result:" + num);
                int iIntValue = num.intValue();
                if (iIntValue != -1 && iIntValue != 0) {
                    if (iIntValue == 1) {
                        PermissionSettingsFragment.this.requestPermission(str);
                        return;
                    } else if (iIntValue != 2) {
                        return;
                    }
                }
                if (context2 != null) {
                    PermissionUtils.startNewAppPermsManager(context2);
                }
            }
        };
        this.mPermissionStatusGetTask = rxStandardAsyncTask2;
        rxStandardAsyncTask2.execute();
    }

    public void requestPermission(String str) {
        str.hashCode();
        switch (str) {
            case "android.permission.READ_MEDIA_IMAGES":
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                PermissionNewUtils.requestOnStoragePermission(this.requestXPermission);
                break;
            case "android.permission.RECORD_AUDIO":
                PermissionNewUtils.requestOnAudioNote(this.requestXPermission);
                break;
            case "android.permission.READ_CONTACTS":
                PermissionNewUtils.requestOnReadContacts(this.requestXPermission);
                break;
            default:
                Logger.INSTANCE.d(TAG, "requestPermission: no permission need to request");
                break;
        }
    }

    public void initResultLauncher() {
        this.requestXPermission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.miui.notes.feature.settings.PermissionSettingsFragment$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                PermissionSettingsFragment.lambda$initResultLauncher$1((Map) obj);
            }
        });
    }
}
