package com.miui.notes.controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.security.ChooseLockSettingsHelper;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.hardware.DataSpace;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.constant.FrameConstant;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.ReflectUtil;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.android.content.MiuiIntent;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.Fragment;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class PasswordController {
    public static final String ACTIN_CANCEL = "action.password_cancel";
    public static final int LOCK = 0;
    private static final int LOCK_DELAY = 0;
    public static final int UNLOCKED = 2;
    public static final int UNLOCKING = 1;
    public static final int UNLOCK_CANCEL = 3;
    public static final int USER_CODE_CONFIRM_PASSWORD = 5103;
    public static final int USER_CODE_CONFIRM_PASSWORD_WITHOUT_OPEN = 5104;
    public static final int USER_CODE_HIDE_NOTE = 5102;
    public static final int USER_CODE_OPEN_PRIVATE_FOLDER = 5101;
    public static AtomicBoolean isSendAction = new AtomicBoolean(false);
    public static int pUserCode = 0;
    private final String NOTES_BUSINESS_ID;
    private String TAG;
    private ActivityResultLauncher<Intent> confirmOldAndResetNewResultLauncher;
    private ActivityResultLauncher<Intent> confirmOldPasswordResultLauncher;
    private ActivityResultLauncher<Intent> confirmPasswordResultLauncher;
    private ActivityResultLauncher<Intent> confirmPasswordWithoutOpenPrivateResultLauncher;
    private HomeViewModel homeViewModel;
    private final IntentFilter mIntentFilter;
    private final boolean mLockOnScreenOff;
    private final Runnable mLockRunnable;
    public int mLockState;
    private LockStateListener mLockStateListener;
    private OnScreenOffListener mOnScreenOffListener;
    private OnSetPasswordListener mOnSetPasswordListener;
    private final BroadcastReceiver mScreenOffReceiver;
    private boolean mTempDisableLockOnScreenOff;
    private final WeakReference<Activity> mWkActivity;
    private final WeakReference<DialogManagerInterface> mWkDialogManager;
    protected ActivityResultLauncher<Intent> privateActivityLauncher;
    private final DialogInterface.OnClickListener sClickListener;
    private boolean screenReceiverRegistered;
    private ActivityResultLauncher<Intent> setAuthUserPasswordResultLauncher;
    private ActivityResultLauncher<Intent> setPasswordResultLauncher;
    private ActivityResultLauncher<Intent> setPasswordWithConfirmResultLauncher;

    public interface LockStateListener {
        void onLockStateChanged(PasswordController passwordController, int i);

        boolean shouldAutoLock();
    }

    public interface OnScreenOffListener {
        void oScreenOff();
    }

    public interface OnSetPasswordListener {
        void onFailed(int i);

        void onSuccess(int i);
    }

    public static class SimpleOnSetPasswordListener implements OnSetPasswordListener {
        @Override // com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onFailed(int i) {
        }

        @Override // com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int i) {
        }
    }

    public static class SimpleLockStateListener implements LockStateListener {
        @Override // com.miui.notes.controller.PasswordController.LockStateListener
        public boolean shouldAutoLock() {
            return true;
        }

        @Override // com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            Logger.INSTANCE.d("SimpleLockStateListener", "onLockStateChanged lockState = " + i);
            if (i == 3) {
                passwordController.finish();
            }
        }
    }

    public PasswordController(DialogManagerInterface dialogManagerInterface, boolean z) {
        this.TAG = getClass().getSimpleName();
        this.NOTES_BUSINESS_ID = "com.miui.notes.privacy";
        this.mLockState = 0;
        this.mTempDisableLockOnScreenOff = false;
        this.mScreenOffReceiver = new BroadcastReceiver() { // from class: com.miui.notes.controller.PasswordController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (PasswordController.this.mTempDisableLockOnScreenOff) {
                    PasswordController.this.mTempDisableLockOnScreenOff = false;
                    return;
                }
                UIUtils.removeRunnable(PasswordController.this.mLockRunnable);
                PasswordController.this.lock();
                if (PasswordController.this.mOnScreenOffListener != null) {
                    PasswordController.this.mOnScreenOffListener.oScreenOff();
                }
            }
        };
        this.mIntentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        this.screenReceiverRegistered = false;
        this.mLockRunnable = new Runnable() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lock();
            }
        };
        this.sClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        };
        isSendAction.set(false);
        this.mWkDialogManager = new WeakReference<>(dialogManagerInterface);
        this.mWkActivity = new WeakReference<>(dialogManagerInterface.getThisActivity());
        if (!(dialogManagerInterface instanceof ActivityResultCaller)) {
            throw new IllegalArgumentException("Please use an activity or Fragment that implements the ActivityResultCaller,otherwise there will be no registerForActivityResult function");
        }
        this.mLockOnScreenOff = z;
        if (z) {
            registerScreenOffReceiver();
        }
        this.homeViewModel = (HomeViewModel) new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(HomeViewModel.class);
        initResultLauncher();
    }

    public PasswordController(DialogManagerInterface dialogManagerInterface, boolean z, int i) {
        this.TAG = getClass().getSimpleName();
        this.NOTES_BUSINESS_ID = "com.miui.notes.privacy";
        this.mLockState = 0;
        this.mTempDisableLockOnScreenOff = false;
        this.mScreenOffReceiver = new BroadcastReceiver() { // from class: com.miui.notes.controller.PasswordController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (PasswordController.this.mTempDisableLockOnScreenOff) {
                    PasswordController.this.mTempDisableLockOnScreenOff = false;
                    return;
                }
                UIUtils.removeRunnable(PasswordController.this.mLockRunnable);
                PasswordController.this.lock();
                if (PasswordController.this.mOnScreenOffListener != null) {
                    PasswordController.this.mOnScreenOffListener.oScreenOff();
                }
            }
        };
        this.mIntentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        this.screenReceiverRegistered = false;
        this.mLockRunnable = new Runnable() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lock();
            }
        };
        this.sClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.cancel();
            }
        };
        this.mLockState = i;
        this.mWkDialogManager = new WeakReference<>(dialogManagerInterface);
        this.mWkActivity = new WeakReference<>(dialogManagerInterface.getThisActivity());
        if (!(dialogManagerInterface instanceof ActivityResultCaller)) {
            throw new IllegalArgumentException("Please use an activity or Fragment that implements the ActivityResultCaller,otherwise there will be no registerForActivityResult function");
        }
        this.mLockOnScreenOff = z;
        if (z) {
            registerScreenOffReceiver();
        }
        initResultLauncher();
    }

    private Activity getActivity() {
        WeakReference<Activity> weakReference = this.mWkActivity;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private DialogManagerInterface getDialogManager() {
        WeakReference<DialogManagerInterface> weakReference = this.mWkDialogManager;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private void initResultLauncher() {
        Logger.INSTANCE.d(this.TAG, "initResultLauncher");
        DialogManagerInterface dialogManager = getDialogManager();
        if (dialogManager == null) {
            return;
        }
        ActivityResultCaller activityResultCaller = (ActivityResultCaller) dialogManager;
        this.setPasswordResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$0((ActivityResult) obj);
            }
        });
        this.setAuthUserPasswordResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$1((ActivityResult) obj);
            }
        });
        this.setPasswordWithConfirmResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$2((ActivityResult) obj);
            }
        });
        this.privateActivityLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda7
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$3((ActivityResult) obj);
            }
        });
        this.confirmPasswordResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda8
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$4((ActivityResult) obj);
            }
        });
        this.confirmPasswordWithoutOpenPrivateResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda9
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$5((ActivityResult) obj);
            }
        });
        this.confirmOldPasswordResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda10
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$6((ActivityResult) obj);
            }
        });
        this.confirmOldAndResetNewResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda11
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$7((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$0(ActivityResult activityResult) {
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setIsPassword(false);
        }
        if (activityResult.getResultCode() == -1) {
            PreferenceUtils.setHintPasswordUpgrade(NoteApp.getInstance(), true);
            notifySetPasswordSuccess(pUserCode);
        } else {
            notifySetPasswordFailed(pUserCode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$1(ActivityResult activityResult) {
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setIsPassword(false);
        }
        if (activityResult.getResultCode() == -1) {
            setLockState(2);
            int i = pUserCode;
            if (i == 5103 || i == 5101) {
                PreferenceUtils.setHintPasswordUpgrade(NoteApp.getInstance(), true);
                startPrivateActivity();
                return;
            } else {
                if (i == 5104) {
                    Logger.INSTANCE.d(this.TAG, "no need to open private page");
                    return;
                }
                return;
            }
        }
        if (activityResult.getResultCode() == 0) {
            Logger.INSTANCE.d(this.TAG, "initResultLauncher RESULT_CANCELED: ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$2(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            setLockState(2);
            return;
        }
        if (activityResult.getResultCode() == 0) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.sendBroadcast(new Intent(ACTIN_CANCEL));
                activity.finish();
                return;
            }
            return;
        }
        setLockState(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$3(ActivityResult activityResult) {
        Logger.INSTANCE.d(this.TAG, "privateActivityLauncher :" + activityResult.getResultCode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$4(ActivityResult activityResult) {
        Logger.INSTANCE.d(this.TAG, "confirmPasswordResultLauncher :" + activityResult.getResultCode());
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setIsPassword(false);
        }
        if (activityResult.getResultCode() == -1) {
            setLockState(2);
            Logger.INSTANCE.d(this.TAG, "initResultLauncher: " + getActivity());
            startPrivateActivity();
        } else {
            if (activityResult.getResultCode() == 0) {
                Logger.INSTANCE.d(this.TAG, "initResultLauncher: Activity.RESULT_CANCELED");
                Activity activity = getActivity();
                if (activity == null || activity.getClass() == NotesListActivity.class) {
                    return;
                }
                activity.sendBroadcast(new Intent(ACTIN_CANCEL));
                activity.finish();
                return;
            }
            setLockState(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$5(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            setLockState(2);
            Logger.INSTANCE.d(this.TAG, "initResultLauncher: " + getActivity());
        } else {
            if (activityResult.getResultCode() == 0) {
                Activity activity = getActivity();
                if (activity != null && activity.getClass() != NotesListActivity.class) {
                    activity.sendBroadcast(new Intent(ACTIN_CANCEL));
                    activity.finish();
                }
                Logger.INSTANCE.d(this.TAG, "initResultLauncher: Activity.RESULT_CANCELED");
                return;
            }
            setLockState(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$6(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            setLockState(1);
            showUsePrivacyPasswordHint(pUserCode);
        } else {
            setLockState(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$7(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            showPasswordResetHint(pUserCode);
        } else {
            notifySetPasswordFailed(pUserCode);
        }
    }

    private void startPrivateActivity() {
        ScreenSpec screenSpec = new ScreenSpec();
        screenSpec.screenMode = 4103;
        Intent intent = new Intent(getActivity(), (Class<?>) PrivateNotesListActivity.class);
        if (!(getActivity() instanceof Activity)) {
            intent.addFlags(DataSpace.RANGE_LIMITED);
        }
        intent.putExtra("com.miui.notes.folder_id", -4L);
        intent.putExtra(FrameConstant.PARAM_SCREEN_MODE, screenSpec.screenMode);
        intent.setAction(NoteIntent.ACTION_VIEW_HIDDEN_NOTES);
        this.privateActivityLauncher.launch(intent);
    }

    public void activeUnlock() {
        setLockState(2);
    }

    public void destroy() {
        Logger.INSTANCE.d(this.TAG, "destroy");
        UIUtils.removeRunnable(this.mLockRunnable);
        setOnSetPasswordListener(null);
        if (this.mLockOnScreenOff) {
            unregisterScreenOffReceiver();
        }
    }

    public void setOnSetPasswordListener(OnSetPasswordListener onSetPasswordListener) {
        this.mOnSetPasswordListener = onSetPasswordListener;
    }

    public void setLockSateListener(LockStateListener lockStateListener) {
        this.mLockStateListener = lockStateListener;
        notifyLockStateChange();
    }

    public void setLockSateListener(LockStateListener lockStateListener, int i) {
        this.mLockStateListener = lockStateListener;
        this.mLockState = i;
        notifyLockStateChange();
    }

    public void unlock() {
        Log.d(this.TAG, "unlock: ", new Throwable());
        if (isSendAction.get()) {
            return;
        }
        Logger.INSTANCE.d(this.TAG, "unlock() " + this.mLockState);
        UIUtils.removeRunnable(this.mLockRunnable);
        if (!shouldAutoLock()) {
            setLockState(2);
            return;
        }
        if (this.mLockState == 0) {
            setLockState(1);
            Activity activity = getActivity();
            if (activity == null) {
                return;
            }
            boolean zHasPrivacyPassword = hasPrivacyPassword(activity);
            if (!zHasPrivacyPassword && isOldUser(activity)) {
                confirmOldPassword(5103);
                return;
            }
            if (!zHasPrivacyPassword) {
                if (isSupportAuthUser()) {
                    setAuthUserPrivacyPassword(5104);
                    return;
                } else {
                    setPrivacyPasswordWithConfirm(5103);
                    return;
                }
            }
            confirmPrivacyPasswordWithoutOpenPrivate(5103);
        }
    }

    public void lockDelay() {
        Logger.INSTANCE.d(this.TAG, "lockDelay() ");
        UIUtils.runOnUIThread(this.mLockRunnable, 0L);
    }

    public void lock() {
        Logger.INSTANCE.d(this.TAG, "lock() " + this.mLockState + " shouldAutoLock: " + shouldAutoLock());
        if (this.mLockState == 2 && shouldAutoLock()) {
            setLockState(0);
        }
    }

    public void setPassword(int i) {
        Logger.INSTANCE.d(this.TAG, "setPassword ");
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        boolean zHasPrivacyPassword = hasPrivacyPassword(activity);
        Log.d(this.TAG, "setPassword /hasPrivacyPassword: " + zHasPrivacyPassword);
        if (zHasPrivacyPassword) {
            Logger.INSTANCE.d(this.TAG, "setPassword/hasPrivacyPwd");
            if (PreferenceUtils.getHintPasswordUpgrade(activity)) {
                notifySetPasswordSuccess(i);
                return;
            } else {
                showUsePrivacyPasswordHint(i);
                return;
            }
        }
        if (isSupportAuthUser()) {
            setAuthUserPrivacyPassword(i);
            return;
        }
        if (isOldUser(activity)) {
            Logger.INSTANCE.d(this.TAG, "setPassword/isOldUser");
            confirmOldPasswordAndReset(i);
        } else if (PreferenceUtils.getHintPasswordUpgrade(activity)) {
            Logger.INSTANCE.d(this.TAG, "setPassword/hint pwd upgrade");
            setPrivacyPassword(i);
        } else {
            Logger.INSTANCE.d(this.TAG, "setPassword/ show pwd hint");
            showSetPrivacyPasswordHint(i);
        }
    }

    public void setOnScreenOffListener(OnScreenOffListener onScreenOffListener) {
        this.mOnScreenOffListener = onScreenOffListener;
    }

    private void registerScreenOffReceiver() {
        Activity activity = getActivity();
        if (activity != null) {
            if (Utils.isAboveSDK34()) {
                activity.registerReceiver(this.mScreenOffReceiver, this.mIntentFilter, 2);
            } else {
                activity.registerReceiver(this.mScreenOffReceiver, this.mIntentFilter);
            }
            this.screenReceiverRegistered = true;
        }
    }

    private void unregisterScreenOffReceiver() {
        Activity activity = getActivity();
        if (activity == null || !this.screenReceiverRegistered) {
            return;
        }
        try {
            activity.unregisterReceiver(this.mScreenOffReceiver);
            this.screenReceiverRegistered = false;
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e(this.TAG, "unregisterReceiver error" + e.getMessage());
        }
    }

    private boolean hasPrivacyPassword(Activity activity) {
        return new ChooseLockSettingsHelper(activity).isPrivacyPasswordEnabled();
    }

    private void setPrivacyPassword(int i) {
        if (this.setPasswordResultLauncher == null) {
            return;
        }
        Intent setPasswordIntent = getSetPasswordIntent();
        pUserCode = i;
        this.setPasswordResultLauncher.launch(setPasswordIntent);
    }

    private void setAuthUserPrivacyPassword(int i) {
        if (this.setPasswordResultLauncher == null) {
            return;
        }
        Intent setAuthUserPasswordIntent = getSetAuthUserPasswordIntent();
        pUserCode = i;
        this.setAuthUserPasswordResultLauncher.launch(setAuthUserPasswordIntent);
    }

    private void setPrivacyPasswordWithConfirm(int i) {
        if (this.setPasswordWithConfirmResultLauncher == null) {
            return;
        }
        Intent setPasswordIntent = getSetPasswordIntent();
        pUserCode = i;
        this.setPasswordWithConfirmResultLauncher.launch(setPasswordIntent);
    }

    private Intent getSetPasswordIntent() {
        Logger.INSTANCE.d(this.TAG, "getSetPasswordIntent: ");
        Intent intent = new Intent(MiuiIntent.ACTION_PRIVACY_PASSWORD_CHOOSE_ACCESS_CONTROL);
        intent.putExtra("android.intent.action.CREATE_SHORTCUT", NoteApp.getInstance().getPackageName());
        intent.putExtra("android.intent.extra.shortcut.NAME", NoteApp.getInstance().getPackageName());
        return intent;
    }

    private Intent getSetAuthUserPasswordIntent() {
        Logger.INSTANCE.d(this.TAG, "getSetPasswordIntent: ");
        Intent intent = new Intent(MiuiIntent.ACTION_PRIVACY_PASSWORD_CHOOSE_ACCESS_CONTROL);
        intent.setClassName(TapShareConstant.SETTING_PACKAGE_NAME, "com.android.settings.PrivacyPasswordChooseLockPattern");
        intent.putExtra("android.intent.action.CREATE_SHORTCUT", NoteApp.getInstance().getPackageName());
        intent.putExtra("android.intent.extra.shortcut.NAME", NoteApp.getInstance().getPackageName());
        return intent;
    }

    public void confirmPrivacyPassword(int i) {
        if (this.confirmPasswordResultLauncher == null) {
            return;
        }
        Log.d(this.TAG, "confirmPrivacyPassword: ", new Throwable());
        Intent intent = new Intent(MiuiIntent.ACTION_PRIVACY_PASSWORD_CHECK_ACCESS_CONTROL);
        intent.setClassName(TapShareConstant.SETTING_PACKAGE_NAME, "com.android.settings.PrivacyPasswordConfirmLockPattern");
        intent.putExtra("android.intent.action.CREATE_SHORTCUT", NoteApp.getInstance().getPackageName());
        intent.putExtra("android.intent.extra.shortcut.NAME", NoteApp.getInstance().getPackageName());
        pUserCode = i;
        this.confirmPasswordResultLauncher.launch(intent);
    }

    public void confirmPrivacyPasswordWithoutOpenPrivate(int i) {
        if (this.confirmPasswordResultLauncher == null) {
            return;
        }
        Log.d(this.TAG, "confirmPrivacyPassword: ", new Throwable());
        Intent intent = new Intent(MiuiIntent.ACTION_PRIVACY_PASSWORD_CHECK_ACCESS_CONTROL);
        intent.setClassName(TapShareConstant.SETTING_PACKAGE_NAME, "com.android.settings.PrivacyPasswordConfirmLockPattern");
        intent.putExtra("android.intent.action.CREATE_SHORTCUT", NoteApp.getInstance().getPackageName());
        intent.putExtra("android.intent.extra.shortcut.NAME", NoteApp.getInstance().getPackageName());
        pUserCode = i;
        this.confirmPasswordWithoutOpenPrivateResultLauncher.launch(intent);
    }

    private void confirmOldPassword(int i) {
        if (this.confirmOldPasswordResultLauncher == null) {
            return;
        }
        Intent oldConfirmPasswordIntent = getOldConfirmPasswordIntent();
        pUserCode = i;
        this.confirmOldPasswordResultLauncher.launch(oldConfirmPasswordIntent);
    }

    private void confirmOldPasswordAndReset(int i) {
        if (this.confirmOldAndResetNewResultLauncher == null) {
            return;
        }
        Intent oldConfirmPasswordIntent = getOldConfirmPasswordIntent();
        pUserCode = i;
        this.confirmOldAndResetNewResultLauncher.launch(oldConfirmPasswordIntent);
    }

    private Intent getOldConfirmPasswordIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(TapShareConstant.SETTING_PACKAGE_NAME, "com.android.settings.MiuiConfirmCommonPassword"));
        intent.putExtra(MiuiIntent.EXTRA_BUSINESS_ID, "com.miui.notes.privacy");
        return intent;
    }

    private void showSetPrivacyPasswordHint(int i) {
        Activity activity = getActivity();
        DialogManagerInterface dialogManager = getDialogManager();
        if (dialogManager == null || dialogManager.isDialogShowing() || activity == null) {
            return;
        }
        dialogManager.manageDialog(getSetPasswordDialog(i, activity).setTitle(R.string.dlg_title_set_password).setMessage(R.string.dlg_message_set_password).show());
    }

    public void showUsePrivacyPasswordHint(int i) {
        Activity activity = getActivity();
        DialogManagerInterface dialogManager = getDialogManager();
        if (activity == null || dialogManager == null) {
            return;
        }
        dialogManager.manageDialog(getConfirmPasswordDialog(i, activity).setTitle(R.string.dlg_title_use_privacy_password).setMessage(R.string.dlg_message_use_privacy_password).show());
    }

    private void showPasswordResetHint(int i) {
        Activity activity = getActivity();
        DialogManagerInterface dialogManager = getDialogManager();
        if (activity == null || dialogManager == null) {
            return;
        }
        dialogManager.manageDialog(getSetPasswordDialog(i, activity).setTitle(R.string.dlg_title_password_reset).setMessage(R.string.dlg_message_password_reset).show());
    }

    private AlertDialog.Builder getSetPasswordDialog(final int i, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.dlg_btn_set_password, new DialogInterface.OnClickListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.lambda$getSetPasswordDialog$8(i, dialogInterface, i2);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, this.sClickListener);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.lambda$getSetPasswordDialog$9(i, dialogInterface);
            }
        });
        builder.setCancelable(false);
        if (LiteUtils.isSuperLite()) {
            builder.setEnableDialogImmersive(true);
        }
        return builder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSetPasswordDialog$8(int i, DialogInterface dialogInterface, int i2) {
        setPrivacyPassword(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSetPasswordDialog$9(int i, DialogInterface dialogInterface) {
        notifySetPasswordFailed(i);
    }

    private AlertDialog.Builder getConfirmPasswordDialog(final int i, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.lambda$getConfirmPasswordDialog$10(context, i, dialogInterface, i2);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, this.sClickListener);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.controller.PasswordController$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.lambda$getConfirmPasswordDialog$11(dialogInterface);
            }
        });
        builder.setCancelable(false);
        if (LiteUtils.isSuperLite()) {
            builder.setEnableDialogImmersive(true);
        }
        return builder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfirmPasswordDialog$10(Context context, int i, DialogInterface dialogInterface, int i2) {
        PreferenceUtils.setHintPasswordUpgrade(context, true);
        notifySetPasswordSuccess(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfirmPasswordDialog$11(DialogInterface dialogInterface) {
        setLockState(3);
    }

    private void setLockState(int i) {
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setIsPassword(false);
        }
        Logger.INSTANCE.d(this.TAG, "setLockState: lockState = " + i + " mLockState = " + this.mLockState);
        if (this.mLockState != i) {
            this.mLockState = i;
            notifyLockStateChange();
        }
    }

    private boolean shouldAutoLock() {
        LockStateListener lockStateListener = this.mLockStateListener;
        if (lockStateListener != null) {
            return lockStateListener.shouldAutoLock();
        }
        return false;
    }

    private void notifySetPasswordSuccess(int i) {
        OnSetPasswordListener onSetPasswordListener = this.mOnSetPasswordListener;
        if (onSetPasswordListener != null) {
            onSetPasswordListener.onSuccess(i);
        }
    }

    private void notifySetPasswordFailed(int i) {
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setIsPassword(false);
        }
        OnSetPasswordListener onSetPasswordListener = this.mOnSetPasswordListener;
        if (onSetPasswordListener != null) {
            onSetPasswordListener.onFailed(i);
        }
    }

    private void notifyLockStateChange() {
        LockStateListener lockStateListener = this.mLockStateListener;
        if (lockStateListener != null) {
            lockStateListener.onLockStateChanged(this, this.mLockState);
        }
    }

    private boolean isOldUser(Context context) {
        return !PreferenceUtils.getHintPasswordUpgrade(context) && CompatUtils.invokeIsScreenLocked(context);
    }

    public static boolean isSupportAuthUser() {
        try {
            return Boolean.TRUE.equals(ReflectUtil.INSTANCE.getStaticObjectField(Class.forName("miui.security.SecurityManager"), "IS_SUPPORT_AUTH_USER", Boolean.TYPE));
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void finish() {
        Logger.INSTANCE.d(this.TAG, "finish: " + this.mLockState);
        DialogManagerInterface dialogManager = getDialogManager();
        if (dialogManager == 0) {
            return;
        }
        if (dialogManager instanceof Activity) {
            ((Activity) dialogManager).finish();
            return;
        }
        Fragment thisFragment = dialogManager.getThisFragment();
        if (thisFragment != null) {
            FragmentManager parentFragmentManager = thisFragment.getParentFragmentManager();
            if (parentFragmentManager.getBackStackEntryCount() > 0) {
                parentFragmentManager.popBackStack();
            } else {
                thisFragment.getActivity().onBackPressed();
            }
        }
    }
}
