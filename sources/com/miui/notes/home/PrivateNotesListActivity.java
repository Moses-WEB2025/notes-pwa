package com.miui.notes.home;

import android.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.hardware.DataSpace;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.EncryptNewUtil;
import com.miui.notes.ui.NotesListActivity;
import com.miui.pad.feature.notes.share.ExportShareActivity;
import com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.Fragment;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorStrategy;
import miuix.navigator.navigatorinfo.NavigatorInfoProvider;
import miuix.navigator.navigatorinfo.UpdateFragmentNavInfo;

/* JADX INFO: compiled from: PrivateNotesListActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 92\u00020\u0001:\u00019B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001c\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001e\u001a\u00020\u0013H\u0014J\u0010\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u0019H\u0014J\u0014\u0010!\u001a\u000e\u0012\b\b\u0001\u0012\u0004\u0018\u00010#\u0018\u00010\"H\u0016J\b\u0010$\u001a\u00020%H\u0016J\n\u0010&\u001a\u0004\u0018\u00010'H\u0016J\u001a\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0019H\u0016J\u0018\u0010,\u001a\u00020\u00132\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002J\b\u00101\u001a\u00020\u0013H\u0014J\b\u00102\u001a\u00020\u0013H\u0014J\b\u00103\u001a\u00020\u0013H\u0014J\b\u00104\u001a\u00020\u0013H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00158\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00105\u001a\u0002068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b7\u00108¨\u0006:"}, d2 = {"Lcom/miui/notes/home/PrivateNotesListActivity;", "Lcom/miui/notes/ui/NotesListActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "setTAG", "(Ljava/lang/String;)V", "mContent", "Landroid/view/View;", "PREF_KEY_PRIVATE_ACTIVITY_STATE", "PREF_KEY_PRIVATE_ACTIVITY_SHOWING", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "mPendingHideContentTask", "Ljava/lang/Runnable;", "onBackAction", "", "mPasswordController", "Lcom/miui/notes/controller/PasswordController;", "mLockSateListener", "Lcom/miui/notes/controller/PasswordController$LockStateListener;", "getNavigatorInitArgs", "Landroid/os/Bundle;", "mFinishReceiver", "Landroid/content/BroadcastReceiver;", "onCreate", "savedInstanceState", "onStart", "onSaveInstanceState", "outState", "getDefaultContentFragment", "Ljava/lang/Class;", "Lmiuix/appcompat/app/Fragment;", "getBottomTabMenu", "", "getBottomTabMenuNavInfoProvider", "Lmiuix/navigator/navigatorinfo/NavigatorInfoProvider;", "onCreatePrimaryNavigation", "navigator", "Lmiuix/navigator/Navigator;", "bundle", "setPrivateActivityShowing", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "showing", "", "onResume", "onPause", "onStop", "onDestroy", "padActivityController", "Lcom/miui/notes/home/PadActivityController;", "getPadActivityController", "()Lcom/miui/notes/home/PadActivityController;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PrivateNotesListActivity extends NotesListActivity {
    public static boolean ACTION_FROM_PRIVACY;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean isShowing;
    private HomeViewModel homeViewModel;
    private View mContent;
    private BroadcastReceiver mFinishReceiver;
    public PasswordController mPasswordController;
    private String TAG = "PrivateNotesListActivity";
    private final String PREF_KEY_PRIVATE_ACTIVITY_STATE = "private_activity_state";
    private final String PREF_KEY_PRIVATE_ACTIVITY_SHOWING = "private_activity_showing";
    private final Runnable mPendingHideContentTask = new Runnable() { // from class: com.miui.notes.home.PrivateNotesListActivity$mPendingHideContentTask$1
        @Override // java.lang.Runnable
        public void run() {
            if (this.this$0.isFinishing() || this.this$0.mContent == null) {
                return;
            }
            View view = this.this$0.mContent;
            Intrinsics.checkNotNull(view);
            view.setVisibility(8);
        }
    };
    private final PasswordController.LockStateListener mLockSateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.home.PrivateNotesListActivity$mLockSateListener$1
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController controller, int lockState) {
            super.onLockStateChanged(controller, lockState);
            Logger.INSTANCE.d(this.this$0.getTAG(), "onLockStateChanged lockState: " + lockState + " mContent: " + this.this$0.mContent);
            if (this.this$0.mContent == null) {
                return;
            }
            if (lockState == 0) {
                View view = this.this$0.mContent;
                Intrinsics.checkNotNull(view);
                view.postDelayed(this.this$0.mPendingHideContentTask, 600L);
                BaseHybridNoteEditFragment.INSTANCE.hideAiFloatingWindowForLock();
                return;
            }
            if (lockState != 2) {
                return;
            }
            View view2 = this.this$0.mContent;
            Intrinsics.checkNotNull(view2);
            view2.removeCallbacks(this.this$0.mPendingHideContentTask);
            View view3 = this.this$0.mContent;
            Intrinsics.checkNotNull(view3);
            view3.setVisibility(0);
        }
    };

    @JvmStatic
    public static final void openPrivateNote(Context context, long j) {
        INSTANCE.openPrivateNote(context, j);
    }

    @Override // com.miui.notes.ui.NotesListActivity, miuix.navigator.app.NavigatorBuilder
    public int getBottomTabMenu() {
        return 0;
    }

    @Override // com.miui.notes.ui.NotesListActivity, miuix.navigator.app.NavigatorBuilder
    public NavigatorInfoProvider getBottomTabMenuNavInfoProvider() {
        return null;
    }

    @Override // com.miui.notes.ui.NotesListActivity
    public String getTAG() {
        return this.TAG;
    }

    public void setTAG(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.TAG = str;
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity
    protected void onBackAction() {
        finish();
    }

    @Override // com.miui.notes.ui.NotesListActivity, miuix.navigator.app.NavigatorBuilder
    public Bundle getNavigatorInitArgs() {
        NavigatorStrategy navigatorStrategy = new NavigatorStrategy();
        navigatorStrategy.setCompactMode(Navigator.Mode.C).setRegularMode(Navigator.Mode.LC, Navigator.Mode.LC).setLargeMode(Navigator.Mode.LC);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Navigator.KEY_MIUIX_NAVIGATOR_STRATEGY, navigatorStrategy);
        return bundle;
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.INSTANCE.d(getTAG(), "onCreate");
        View viewFindViewById = findViewById(R.id.content);
        this.mContent = viewFindViewById;
        if (viewFindViewById != null) {
            Intrinsics.checkNotNull(viewFindViewById);
            viewFindViewById.setVisibility(8);
            if (savedInstanceState == null && getIntent().getFlags() != 268435456) {
                Logger.INSTANCE.d(getTAG(), "onCreate set lockState 2");
                this.mPasswordController = new PasswordController(this, true, 2);
            } else if (savedInstanceState != null) {
                Logger.INSTANCE.d(getTAG(), "onCreate set lockState savedInstanceState = " + savedInstanceState.getInt("lockState"));
                this.mPasswordController = new PasswordController(this, true, savedInstanceState.getInt("lockState"));
            } else {
                Logger.INSTANCE.d(getTAG(), "onCreate set lockState 0");
                this.mPasswordController = new PasswordController(this, true);
            }
            PasswordController passwordController = this.mPasswordController;
            Intrinsics.checkNotNull(passwordController);
            passwordController.setLockSateListener(this.mLockSateListener);
        }
        HomeViewModel homeViewModel = (HomeViewModel) new ViewModelProvider(this).get(HomeViewModel.class);
        this.homeViewModel = homeViewModel;
        HomeViewModel homeViewModel2 = null;
        if (homeViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
            homeViewModel = null;
        }
        homeViewModel.getToDetailResult().observe(this, new Observer() { // from class: com.miui.notes.home.PrivateNotesListActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PrivateNotesListActivity.onCreate$lambda$0(this.f$0, (Boolean) obj);
            }
        });
        PasswordController passwordController2 = this.mPasswordController;
        Intrinsics.checkNotNull(passwordController2);
        passwordController2.setOnScreenOffListener(new PasswordController.OnScreenOffListener() { // from class: com.miui.notes.home.PrivateNotesListActivity$$ExternalSyntheticLambda1
            @Override // com.miui.notes.controller.PasswordController.OnScreenOffListener
            public final void oScreenOff() {
                PrivateNotesListActivity.onCreate$lambda$1(this.f$0);
            }
        });
        if (savedInstanceState != null) {
            HomeViewModel homeViewModel3 = this.homeViewModel;
            if (homeViewModel3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
                homeViewModel3 = null;
            }
            homeViewModel3.setToDetailPage(false);
            PasswordController passwordController3 = this.mPasswordController;
            if (passwordController3 != null && passwordController3.mLockState != 0) {
                HomeViewModel homeViewModel4 = this.homeViewModel;
                if (homeViewModel4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
                } else {
                    homeViewModel2 = homeViewModel4;
                }
                homeViewModel2.toDetailResultCallback();
            }
        }
        Logger.INSTANCE.d(getTAG(), "onCreate register broadcast: ");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordController.ACTIN_CANCEL);
        intentFilter.addAction(ExportShareActivity.INTENT_ACTIVITY_RESUME);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.notes.home.PrivateNotesListActivity.onCreate.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.d(PrivateNotesListActivity.this.getTAG(), "receive action:" + (intent != null ? intent.getAction() : null));
                if (Intrinsics.areEqual(ExportShareActivity.INTENT_ACTIVITY_RESUME, intent != null ? intent.getAction() : null)) {
                    PasswordController passwordController4 = PrivateNotesListActivity.this.mPasswordController;
                    Intrinsics.checkNotNull(passwordController4);
                    if (passwordController4.mLockState == 0) {
                        PasswordController passwordController5 = PrivateNotesListActivity.this.mPasswordController;
                        Intrinsics.checkNotNull(passwordController5);
                        passwordController5.lock();
                        PasswordController passwordController6 = PrivateNotesListActivity.this.mPasswordController;
                        Intrinsics.checkNotNull(passwordController6);
                        passwordController6.unlock();
                        PrivateNotesListActivity.INSTANCE.setShowing(true);
                        PrivateNotesListActivity privateNotesListActivity = PrivateNotesListActivity.this;
                        privateNotesListActivity.setPrivateActivityShowing(privateNotesListActivity, true);
                        return;
                    }
                    return;
                }
                Logger.INSTANCE.d(PrivateNotesListActivity.this.getTAG(), "onReceive finish private activity: ");
                PrivateNotesListActivity.this.finish();
            }
        };
        this.mFinishReceiver = broadcastReceiver;
        registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PrivateNotesListActivity privateNotesListActivity, Boolean bool) {
        PasswordController passwordController = privateNotesListActivity.mPasswordController;
        if (passwordController != null) {
            Intrinsics.checkNotNull(passwordController);
            passwordController.activeUnlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PrivateNotesListActivity privateNotesListActivity) {
        if (PreferenceUtils.getIsInPrivateDetail()) {
            PasswordController passwordController = privateNotesListActivity.mPasswordController;
            Intrinsics.checkNotNull(passwordController);
            passwordController.activeUnlock();
        }
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Logger.INSTANCE.d(getTAG(), "onStart");
        if (Utils.isAboveSDK34() && RomUtils.isHyperOS3Rom() && !EncryptNewUtil.INSTANCE.isServiceConnected()) {
            Logger.INSTANCE.d(getTAG(), "onStart: start MiUnifiedEncrypt service connection");
            try {
                EncryptNewUtil.INSTANCE.connectToService(BaseNoteApp.INSTANCE.getInstance(), new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.notes.home.PrivateNotesListActivity.onStart.1
                    @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                    public void onServiceReady() {
                        Logger.INSTANCE.d(PrivateNotesListActivity.this.getTAG(), "onStart: connect service successfully");
                    }

                    @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                    public void onServiceLost() {
                        Logger.INSTANCE.d(PrivateNotesListActivity.this.getTAG(), "onStart: connecting service failed");
                    }
                });
            } catch (Exception e) {
                Logger.INSTANCE.e(getTAG(), "onStart: MiUnifiedEncrypt service connection failed, e: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        PasswordController passwordController = this.mPasswordController;
        outState.putInt("lockState", (passwordController == null || !isChangingConfigurations()) ? 0 : passwordController.mLockState);
    }

    @Override // com.miui.notes.ui.NotesListActivity, miuix.navigator.app.NavigatorBuilder
    public Class<? extends Fragment> getDefaultContentFragment() {
        return PrivateContentFragment.class;
    }

    @Override // com.miui.notes.ui.NotesListActivity, miuix.navigator.app.NavigatorBuilder
    public void onCreatePrimaryNavigation(Navigator navigator, Bundle bundle) {
        Intrinsics.checkNotNullParameter(navigator, "navigator");
        UpdateFragmentNavInfo updateFragmentNavInfo = new UpdateFragmentNavInfo(0, getDefaultContentFragment(), new Bundle());
        newLabel(getResources().getString(com.miui.notes.R.string.hidden_notes_fragment_title), com.miui.notes.R.drawable.miui_ic_notes, updateFragmentNavInfo);
        navigator.navigate(updateFragmentNavInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setPrivateActivityShowing(Context context, boolean showing) {
        context.getSharedPreferences(this.PREF_KEY_PRIVATE_ACTIVITY_STATE, 0).edit().putBoolean(this.PREF_KEY_PRIVATE_ACTIVITY_SHOWING, showing).apply();
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        String tag = getTAG();
        PasswordController passwordController = this.mPasswordController;
        Intrinsics.checkNotNull(passwordController);
        Log.d(tag, "onResume mLockState: " + passwordController.mLockState);
        PasswordController passwordController2 = this.mPasswordController;
        Intrinsics.checkNotNull(passwordController2);
        if (passwordController2.mLockState == 0) {
            PasswordController passwordController3 = this.mPasswordController;
            Intrinsics.checkNotNull(passwordController3);
            passwordController3.unlock();
        }
        isShowing = true;
        setPrivateActivityShowing(this, true);
    }

    @Override // com.miui.notes.ui.NotesListActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        PrivateNotesListActivity privateNotesListActivity = this;
        if (Intrinsics.areEqual(NoteContentStat.MULTI_HANDWRITE_ACTIVITY, com.miui.handwriting.common.tools.Utils.getCurrentForegroundActivity(privateNotesListActivity))) {
            Log.i(getTAG(), "onStop foregroundActivity no verification required");
            return;
        }
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
            homeViewModel = null;
        }
        if (!Intrinsics.areEqual((Object) homeViewModel.isToDetailPage(), (Object) true) && !isChangingConfigurations()) {
            PasswordController passwordController = this.mPasswordController;
            Intrinsics.checkNotNull(passwordController);
            passwordController.lock();
        }
        isShowing = false;
        setPrivateActivityShowing(privateNotesListActivity, false);
    }

    @Override // com.miui.notes.ui.NotesListActivity, com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Log.d(getTAG(), "onDestroy mPasswordController destroy");
        PasswordController passwordController = this.mPasswordController;
        Intrinsics.checkNotNull(passwordController);
        passwordController.destroy();
        unregisterReceiver(this.mFinishReceiver);
        super.onDestroy();
    }

    @Override // com.miui.notes.ui.NotesListActivity
    protected PadActivityController getPadActivityController() {
        return new PrivatePadActivityController(this);
    }

    /* JADX INFO: compiled from: PrivateNotesListActivity.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/home/PrivateNotesListActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ACTION_FROM_PRIVACY", "", "isShowing", "()Z", "setShowing", "(Z)V", "openPrivateNote", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", NotifyUtils.KEY_NOTE_ID, "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isShowing() {
            return PrivateNotesListActivity.isShowing;
        }

        public final void setShowing(boolean z) {
            PrivateNotesListActivity.isShowing = z;
        }

        @JvmStatic
        public final void openPrivateNote(Context context, long noteId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) PrivateNotesListActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(DataSpace.RANGE_LIMITED);
            }
            intent.putExtra("com.miui.notes.folder_id", -4L);
            intent.putExtra("android.intent.extra.UID", noteId);
            intent.setAction(NoteIntent.ACTION_VIEW_HIDDEN_NOTE_BY_ID);
            context.startActivity(intent);
        }
    }
}
