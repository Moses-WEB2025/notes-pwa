package com.miui.notes.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Trace;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.WindowManager;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import com.hiero.logger.base.logger.core.AppLogMgr;
import com.market.sdk.XiaomiUpdateAgent;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.AppGlobal;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.WidgetStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.ShortcutsTools;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.assist.UpdateListener;
import com.miui.notes.basefeature.IActivityController;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.home.ContentFragment;
import com.miui.notes.home.NaviBaseActivity;
import com.miui.notes.home.PadActivityController;
import com.miui.notes.home.PadContentFragment;
import com.miui.notes.home.PhoneActivityController;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.KeyboardShortcutUtils;
import com.miui.notes.tool.NotesUtils;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.KoreaRegionUtils;
import com.miui.pad.feature.notes.handwrite.RecognizeEngine;
import com.miui.pad.feature.notes.multihandwrite.CreationEditFragment;
import com.miui.pad.feature.settings.PadAppPreferenceActivity;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.feature.todolist.TodoChildItemVh;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.internal.util.AttributeResolver;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorStrategy;
import miuix.navigator.navigatorinfo.NavigatorInfo;
import miuix.navigator.navigatorinfo.NavigatorInfoProvider;
import miuix.navigator.navigatorinfo.UpdateFragmentNavInfo;
import miuix.os.Build;

/* JADX INFO: compiled from: NotesListActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 r2\u00020\u0001:\u0001rB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0015J\b\u0010,\u001a\u00020)H\u0014J-\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020/2\u000e\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u0005012\u0006\u00102\u001a\u000203H\u0016¢\u0006\u0002\u00104J\u0006\u00105\u001a\u00020)J\u0012\u0010:\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\u0010\u0010;\u001a\u00020)2\u0006\u0010<\u001a\u00020\u000bH\u0014J\u0010\u0010=\u001a\u00020)2\u0006\u0010>\u001a\u00020\u0016H\u0016J\u0010\u0010?\u001a\u00020)2\u0006\u0010@\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020)2\u0006\u0010C\u001a\u00020\u0005H\u0002J\u0012\u0010D\u001a\u00020\u00162\b\u0010<\u001a\u0004\u0018\u00010\u000bH\u0002J\b\u0010E\u001a\u00020)H\u0014J\b\u0010F\u001a\u00020)H\u0014J\b\u0010G\u001a\u00020)H\u0014J\b\u0010H\u001a\u00020)H\u0014J\u0006\u0010I\u001a\u00020)J\u0010\u0010J\u001a\u00020)2\u0006\u0010<\u001a\u00020\u000bH\u0002J\b\u0010K\u001a\u00020)H\u0002J\b\u0010L\u001a\u00020+H\u0016J\u0014\u0010M\u001a\u000e\u0012\b\b\u0001\u0012\u0004\u0018\u00010O\u0018\u00010NH\u0016J\b\u0010P\u001a\u00020/H\u0016J\b\u0010Q\u001a\u00020/H\u0016J\n\u0010R\u001a\u0004\u0018\u00010SH\u0016J\u001a\u0010T\u001a\u00020)2\u0006\u0010U\u001a\u00020V2\b\u0010W\u001a\u0004\u0018\u00010+H\u0016J\u001c\u0010^\u001a\u00020)2\b\u0010U\u001a\u0004\u0018\u00010V2\b\u0010W\u001a\u0004\u0018\u00010+H\u0016J\b\u0010_\u001a\u00020)H\u0014J\b\u0010`\u001a\u00020)H\u0002J\u0010\u0010a\u001a\u00020)2\b\u0010b\u001a\u0004\u0018\u00010\u0014J\b\u0010c\u001a\u00020)H\u0002J,\u0010d\u001a\u00020)2\u0010\u0010e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010g\u0018\u00010f2\b\u0010h\u001a\u0004\u0018\u00010i2\u0006\u0010j\u001a\u00020/H\u0016J\u0010\u0010k\u001a\u00020)2\u0006\u0010l\u001a\u00020\u0016H\u0016J\u001a\u0010m\u001a\u00020\u00162\u0006\u0010n\u001a\u00020/2\b\u0010o\u001a\u0004\u0018\u00010pH\u0016J\b\u0010q\u001a\u00020)H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u0014\u00106\u001a\u0002078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010X\u001a\u00020Y8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0014\u0010\\\u001a\u00020Y8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b]\u0010[¨\u0006s"}, d2 = {"Lcom/miui/notes/ui/NotesListActivity;", "Lcom/miui/notes/home/NaviBaseActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "NOTE_DATA", "toKoreaLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "toCtaLauncher", "toCtaPasteLauncher", "receivePadMultiData", "getReceivePadMultiData", "()Landroidx/activity/result/ActivityResultLauncher;", "setReceivePadMultiData", "(Landroidx/activity/result/ActivityResultLauncher;)V", "mEditorContext", "Lcom/miui/richeditor/IEditorContext;", "needShowCtaDialog", "", "getNeedShowCtaDialog", "()Z", "setNeedShowCtaDialog", "(Z)V", "mDowngradeReceiver", "Lcom/miui/notes/ui/DowngradeReceiver;", "controller", "Lcom/miui/notes/basefeature/IActivityController;", "onCreateTs", "", "hasToggledDarkModeInDetail", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "getHomeViewModel", "()Lcom/miui/notes/home/viewmodel/HomeViewModel;", "homeViewModel$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onBackAction", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "checkTheme", "padActivityController", "Lcom/miui/notes/home/PadActivityController;", "getPadActivityController", "()Lcom/miui/notes/home/PadActivityController;", "onPostCreate", "onNewIntent", "intent", "onMultiWindowModeChanged", "isInMultiWindowMode", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "requestCta", "from", "checkCtaIntentFromMiCloud", "onPause", "onStop", "onStart", "onResume", "cancelScaleUpDownAnimation", "changeLockedFlag", "checkUpdate", "getNavigatorInitArgs", "getDefaultContentFragment", "Ljava/lang/Class;", "Lmiuix/appcompat/app/Fragment;", "getNavigationOptionMenu", "getBottomTabMenu", "getBottomTabMenuNavInfoProvider", "Lmiuix/navigator/navigatorinfo/NavigatorInfoProvider;", "onCreatePrimaryNavigation", "navigator", "Lmiuix/navigator/Navigator;", "bundle", "updateFragmentNavInfoToNote", "Lmiuix/navigator/navigatorinfo/UpdateFragmentNavInfo;", "getUpdateFragmentNavInfoToNote", "()Lmiuix/navigator/navigatorinfo/UpdateFragmentNavInfo;", "updateFragmentNavInfoToTodo", "getUpdateFragmentNavInfoToTodo", "onCreateOtherNavigation", "onDestroy", "initActivityResultLauncher", "showCtaForPaste", "editorContext", "showPostNotificationsPermission", "onProvideKeyboardShortcuts", "data", "", "Landroid/view/KeyboardShortcutGroup;", "menu", "Landroid/view/Menu;", "deviceId", "onWindowFocusChanged", "hasFocus", "onKeyUp", "keyCode", "event", "Landroid/view/KeyEvent;", "finish", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class NotesListActivity extends NaviBaseActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static boolean IS_IN_RUNNING = false;
    public static final int NAVIGATOR_ID_NOTE = 0;
    public static final int NAVIGATOR_ID_TODO = 1;
    private static int activityCount;
    public static boolean isShowingCta;
    public static int sSelectedPageIndex;
    private IActivityController controller;
    private boolean hasToggledDarkModeInDetail;
    private DowngradeReceiver mDowngradeReceiver;
    private IEditorContext mEditorContext;
    private boolean needShowCtaDialog;
    private long onCreateTs;
    private ActivityResultLauncher<Intent> receivePadMultiData;
    private ActivityResultLauncher<Intent> toCtaLauncher;
    private ActivityResultLauncher<Intent> toCtaPasteLauncher;
    private ActivityResultLauncher<Intent> toKoreaLauncher;
    private final String TAG = "NotesListActivity";
    private final String NOTE_DATA = CreationEditFragment.NOTE_DATA;

    /* JADX INFO: renamed from: homeViewModel$delegate, reason: from kotlin metadata */
    private final Lazy homeViewModel = LazyKt.lazy(new Function0() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NotesListActivity.homeViewModel_delegate$lambda$0(this.f$0);
        }
    });

    @Override // miuix.navigator.app.NavigatorBuilder
    public int getNavigationOptionMenu() {
        return R.menu.navigation_immersion;
    }

    @Override // miuix.navigator.app.NavigatorBuilder
    public void onCreateOtherNavigation(Navigator navigator, Bundle bundle) {
    }

    public String getTAG() {
        return this.TAG;
    }

    public final ActivityResultLauncher<Intent> getReceivePadMultiData() {
        return this.receivePadMultiData;
    }

    public final void setReceivePadMultiData(ActivityResultLauncher<Intent> activityResultLauncher) {
        this.receivePadMultiData = activityResultLauncher;
    }

    protected final boolean getNeedShowCtaDialog() {
        return this.needShowCtaDialog;
    }

    protected final void setNeedShowCtaDialog(boolean z) {
        this.needShowCtaDialog = z;
    }

    private final HomeViewModel getHomeViewModel() {
        return (HomeViewModel) this.homeViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final HomeViewModel homeViewModel_delegate$lambda$0(NotesListActivity notesListActivity) {
        return (HomeViewModel) new ViewModelProvider(notesListActivity).get(HomeViewModel.class);
    }

    @Override // com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            activityCount++;
            Trace.beginSection("NotesListActivity.onCreate");
            Logger.INSTANCE.d(getTAG(), "onCreate");
            this.onCreateTs = System.currentTimeMillis();
            Logger.INSTANCE.d(getTAG(), "NotesDatabase version :53");
            checkTheme();
            super.onCreate(savedInstanceState);
            if (savedInstanceState != null) {
                this.hasToggledDarkModeInDetail = true;
                Logger.INSTANCE.d(getTAG(), "onCreate: savedInstanceState != null, clear todo drawable cache");
                TodoChildItemVh.clearCachedDrawables();
                BaseTodoChildItemVh.clearCachedDrawables();
            } else {
                Logger.INSTANCE.d(getTAG(), "onCreate: savedInstanceState == null (cold Activity), rely on uiMode-guard in VH cache");
            }
            this.needShowCtaDialog = PreferenceUtils.isFirstHandle(getApplicationContext());
            boolean downgrade = PreferenceUtils.getDowngrade(this);
            Logger.INSTANCE.d(getTAG(), "onCreate,needShowCtaDialog:" + this.needShowCtaDialog + ",isDowngrade:" + downgrade);
            initActivityResultLauncher();
            final Intent intent = getIntent();
            if (intent != null) {
                Logger.INSTANCE.d(getTAG(), "onCreate intent action = " + intent.getAction());
            }
            Intrinsics.checkNotNull(intent);
            changeLockedFlag(intent);
            if (!RomUtils.isPadMode()) {
                setRequestedOrientation(1);
                this.controller = new PhoneActivityController(this);
            } else {
                this.controller = getPadActivityController();
            }
            if (downgrade) {
                DowngradeReceiver.INSTANCE.downgrade(this);
            } else {
                this.mDowngradeReceiver = new DowngradeReceiver();
                Logger.INSTANCE.d(getTAG(), "on create,register downgrade broadcast ");
                DowngradeReceiver.INSTANCE.register(this, this.mDowngradeReceiver);
            }
            IS_IN_RUNNING = true;
            IActivityController iActivityController = this.controller;
            Intrinsics.checkNotNull(iActivityController);
            iActivityController.onCreate();
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda4
                @Override // android.os.MessageQueue.IdleHandler
                public final boolean queueIdle() {
                    return NotesListActivity.onCreate$lambda$1(this.f$0, savedInstanceState, intent);
                }
            });
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback() { // from class: com.miui.notes.ui.NotesListActivity.onCreate.2
                {
                    super(true);
                }

                @Override // androidx.activity.OnBackPressedCallback
                public void handleOnBackPressed() {
                    if (com.miui.notes.ai.utils.RomUtils.isFoldDevice() && !UIUtils.isFoldInternalScreen(NotesListActivity.this.getApplicationContext())) {
                        Navigator byTag = NotesListActivity.this.getNavigator().getByTag(Navigator.TAG_SECONDARY_CONTENT);
                        List<Fragment> fragments = byTag.getFragmentManager().getFragments();
                        Intrinsics.checkNotNullExpressionValue(fragments, "getFragments(...)");
                        if (!fragments.isEmpty()) {
                            byTag.getFragmentManager().popBackStack();
                            Log.d(NotesListActivity.this.getTAG(), "fold device pop second fragment");
                            return;
                        }
                    }
                    Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "handleBackPressed");
                    NotesListActivity.this.onBackAction();
                }
            });
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C03223(null), 2, null);
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onCreate$lambda$1(NotesListActivity notesListActivity, Bundle bundle, Intent intent) {
        Logger.INSTANCE.d(notesListActivity.getTAG(), "idleHandler: deferred init start");
        if (!notesListActivity.needShowCtaDialog) {
            Logger.INSTANCE.d(notesListActivity.getTAG(), "idleHandler,init shortcut");
            ShortcutsTools.initShortcuts();
        }
        NotesListActivity notesListActivity2 = notesListActivity;
        if (!com.miui.notes.ai.utils.RomUtils.isSupportLocalAI(notesListActivity2)) {
            Logger.INSTANCE.d(notesListActivity.getTAG(), "idleHandler cloudLLMConfig false");
            com.miui.notes.ai.utils.PreferenceUtils.setModelSwitch(notesListActivity2, false);
        }
        if (bundle == null && !isShowingCta && !notesListActivity.checkCtaIntentFromMiCloud(intent)) {
            notesListActivity.requestCta("idleHandler");
        }
        notesListActivity.checkUpdate();
        WidgetStat.handleEditEnter(intent, "");
        return false;
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.NotesListActivity$onCreate$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NotesListActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ui.NotesListActivity$onCreate$3", f = "NotesListActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03223 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03223(Continuation<? super C03223> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NotesListActivity.this.new C03223(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03223) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "onCreate Settings.Secure.getInt: " + Settings.Secure.getInt(NotesListActivity.this.getApplicationContext().getContentResolver(), "notes_pref_enable_float_mode"));
            } catch (Exception e) {
                Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "onCreate Settings.Secure Exception: " + e);
                if (e instanceof Settings.SettingNotFoundException) {
                    Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "onCreate Settings.Secure.setInt: 0");
                    try {
                        Boxing.boxBoolean(Settings.Secure.putInt(NotesListActivity.this.getApplicationContext().getContentResolver(), "notes_pref_enable_float_mode", 0));
                    } catch (Exception e2) {
                        Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "onCreate Settings.Secure.putInt failed: " + e2);
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Override // com.miui.notes.home.NaviBaseActivity
    protected void onBackAction() {
        AppLogMgr.INSTANCE.flush();
        moveTaskToBack(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4) {
            PermissionUtils.processOnStoragePermission(this, permissions, grantResults);
        }
    }

    public final void checkTheme() {
        if (AttributeResolver.resolve(this, R.attr.isNavigatorTheme) < 0) {
            Logger.INSTANCE.d(getTAG(), "reset Theme");
            setTheme(R.style.NavigatorActivityTheme);
        }
    }

    protected PadActivityController getPadActivityController() {
        return new PadActivityController(this);
    }

    @Override // android.app.Activity
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Logger.INSTANCE.d(getTAG(), "onPostCreate");
        IActivityController iActivityController = this.controller;
        Intrinsics.checkNotNull(iActivityController);
        iActivityController.onPostCreate(savedInstanceState);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        Logger.INSTANCE.d(getTAG(), "onNewIntent ,isShowingCta:" + isShowingCta + ",intent:" + intent.getType() + ",controller:" + this.controller);
        Log.d(getTAG(), "onNewIntent: PreferenceUtils.isFirstEnterNote: " + PreferenceUtils.isFirstEnterNote(getApplicationContext()));
        if (!checkCtaIntentFromMiCloud(getIntent()) && !isShowingCta && PreferenceUtils.isFirstEnterNote(getApplicationContext())) {
            requestCta("onNewIntent");
        }
        WidgetStat.handleEditEnter(getIntent(), "");
        changeLockedFlag(intent);
        IActivityController iActivityController = this.controller;
        Intrinsics.checkNotNull(iActivityController);
        iActivityController.onNewIntent(intent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Logger.INSTANCE.d(getTAG(), "onMultiWindowModeChanged:" + isInMultiWindowMode);
        IActivityController iActivityController = this.controller;
        Intrinsics.checkNotNull(iActivityController);
        iActivityController.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    @Override // com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        IActivityController iActivityController = this.controller;
        Intrinsics.checkNotNull(iActivityController);
        iActivityController.onConfigurationChanged(newConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestCta(String from) {
        String stringExtra;
        Logger.INSTANCE.d(getTAG(), "requestCTA " + from);
        boolean z = false;
        boolean z2 = getIntent() == null;
        if (getIntent() == null) {
            stringExtra = "";
        } else {
            String action = getIntent().getAction();
            stringExtra = getIntent().getStringExtra(Notes.Intents.INTENT_EXTRA_CONTACT_NAME);
            Logger.INSTANCE.d(getTAG(), "requestCta:action = " + action);
            boolean booleanExtra = getIntent().getBooleanExtra("EXTRA_TAP_SHARE_BATCH_RESTORE", false);
            if (RomUtils.isPadMode() || booleanExtra || (!Intrinsics.areEqual("android.intent.action.VIEW", action) && !Intrinsics.areEqual("android.intent.action.INSERT_OR_EDIT", action) && !Intrinsics.areEqual("android.intent.action.SEND", action))) {
                z = true;
            }
            z2 = z;
        }
        Object systemService = getSystemService("keyguard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        boolean zIsKeyguardLocked = ((KeyguardManager) systemService).isKeyguardLocked();
        Logger.INSTANCE.d(getTAG(), "requestCta:showCta==" + z2 + " needShowCtaDialog:" + this.needShowCtaDialog + " isShowingCta:" + isShowingCta + " isKeyguardLocked:" + zIsKeyguardLocked);
        boolean zIsEmpty = TextUtils.isEmpty(stringExtra);
        Logger.INSTANCE.d(getTAG(), "requestCta:hasContactName:" + (!zIsEmpty));
        if (z2 && !zIsKeyguardLocked && zIsEmpty && this.needShowCtaDialog && !PreferenceUtils.isCTAAccepted(getApplicationContext()) && PermissionUtils.supportNewPermissionStyle()) {
            Logger.INSTANCE.d(getTAG(), "requestCta:showCtaDialog");
            isShowingCta = true;
            PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
        }
    }

    private final boolean checkCtaIntentFromMiCloud(Intent intent) {
        Uri data;
        boolean z = false;
        if (intent == null) {
            return false;
        }
        Logger.INSTANCE.d(getTAG(), "checkCtaIntentFromMiCloud ");
        String action = intent.getAction();
        if (action != null && Intrinsics.areEqual(action, "android.intent.action.VIEW") && (data = intent.getData()) != null && Intrinsics.areEqual("micloud", data.getScheme())) {
            intent.setAction("android.intent.action.MAIN");
            setIntent(intent);
            z = true;
            if (!PreferenceUtils.isCTAAccepted(getApplicationContext()) && PermissionUtils.supportNewPermissionStyle()) {
                isShowingCta = true;
                PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
            }
        }
        return z;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        try {
            Trace.beginSection("NotesListActivity.onPause");
            super.onPause();
            Logger.INSTANCE.d(getTAG(), "onPause");
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.miui.notes.home.NaviBaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        try {
            isShowingCta = false;
            Trace.beginSection("NotesListActivity.onStop");
            super.onStop();
            Logger.INSTANCE.d(getTAG(), "onStop");
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.miui.notes.home.NaviBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        Logger.INSTANCE.d(getTAG(), "onStart");
    }

    @Override // com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        Configuration configuration;
        try {
            Trace.beginSection("NotesListActivity.onResume");
            super.onResume();
            Logger.INSTANCE.d(getTAG(), "onResume");
            if (PreferenceUtils.isCTAAccepted(getApplicationContext())) {
                Logger.INSTANCE.d(getTAG(), "onResume,CTA ACCEPTED");
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
            } else {
                Logger.INSTANCE.i(getTAG(), "onResume,CTA NOT ACCEPTED!!");
                if (!isShowingCta && !checkCtaIntentFromMiCloud(getIntent())) {
                    Logger.INSTANCE.d(getTAG(), "onResume,checkCtaIntentFromMiCloud false");
                    requestCta("onResume");
                }
            }
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 3;
            getWindow().setAttributes(attributes);
            Object systemService = getSystemService("keyguard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
            boolean zIsKeyguardLocked = ((KeyguardManager) systemService).isKeyguardLocked();
            if (!zIsKeyguardLocked) {
                KoreaRegionUtils.showPermissionsForKorea(this.toKoreaLauncher);
            }
            if (!zIsKeyguardLocked && RomUtils.isInternationalBuild() && !Utils.isInThisCountry(KoreaRegionUtils.REGION_KOREA)) {
                showPostNotificationsPermission();
            }
            IActivityController iActivityController = this.controller;
            Intrinsics.checkNotNull(iActivityController);
            iActivityController.onResume();
            Resources resources = getResources();
            if (resources != null && (configuration = resources.getConfiguration()) != null && (UIUtils.isFreeFormScreen(configuration) || this.hasToggledDarkModeInDetail)) {
                if (this.hasToggledDarkModeInDetail) {
                    this.hasToggledDarkModeInDetail = false;
                }
                cancelScaleUpDownAnimation();
            }
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.NotesListActivity$onResume$1, reason: invalid class name */
    /* JADX INFO: compiled from: NotesListActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ui.NotesListActivity$onResume$1", f = "NotesListActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    public final void cancelScaleUpDownAnimation() {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            Bundle bundle = new Bundle();
            bundle.putParcelable("scaleDownBitmap", bitmapCreateBitmap);
            bundle.putInt("scaleDownColor", -16777216);
            bundle.putInt("xInScreen", 0);
            bundle.putInt("yInScreen", 0);
            bundle.putBoolean("disableBackAnimation", true);
            Method declaredMethod = Activity.class.getDeclaredMethod("updateScaleUpDownData", Bundle.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, bundle);
        } catch (Exception e) {
            Logger.INSTANCE.d(getTAG(), "invoke failed " + e);
        }
    }

    private final void changeLockedFlag(Intent intent) {
        if (NotesUtils.isAddLockFlag(this, intent)) {
            getWindow().addFlags(524288);
            Logger.INSTANCE.d(getTAG(), "changeLockedFlag,lock status ");
        } else {
            getWindow().clearFlags(524288);
            Logger.INSTANCE.d(getTAG(), "changeLockedFlag,clear lock flag  ");
        }
    }

    private final void checkUpdate() {
        if (Build.IS_INTERNATIONAL_BUILD || !CTAManager.getInstance().isAccepted(getApplicationContext())) {
            return;
        }
        new Thread(new Runnable() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NotesListActivity.checkUpdate$lambda$4(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkUpdate$lambda$4(NotesListActivity notesListActivity) {
        Logger.INSTANCE.d(notesListActivity.getTAG(), "checkUpdate() invoked");
        AppGlobal.setContext(notesListActivity.getApplicationContext());
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.update(false);
        XiaomiUpdateAgent.setUpdateListener(new UpdateListener(notesListActivity));
        Logger.INSTANCE.d(notesListActivity.getTAG(), "checkUpdate invoked finished");
    }

    public Bundle getNavigatorInitArgs() {
        NavigatorStrategy navigatorStrategy = new NavigatorStrategy();
        boolean z = RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getThisActivity(), 0) == 1;
        Logger.INSTANCE.d(getTAG(), "getNavigatorInitArgs ,isGridMode " + z);
        if (z) {
            getHomeViewModel().setCurrentGridMode(true);
            navigatorStrategy.setCompactMode(Navigator.Mode.C).setRegularMode(Navigator.Mode.C, Navigator.Mode.C).setLargeMode(Navigator.Mode.NLC);
        } else {
            getHomeViewModel().setCurrentGridMode(false);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Navigator.KEY_MIUIX_NAVIGATOR_STRATEGY, navigatorStrategy);
        bundle.putBoolean(Navigator.KEY_MIUIX_NAVIGATION_INIT_OPEN, PreferenceUtils.getNavigationState());
        return bundle;
    }

    public Class<? extends miuix.appcompat.app.Fragment> getDefaultContentFragment() {
        if (RomUtils.isPadMode()) {
            return PadContentFragment.class;
        }
        return ContentFragment.class;
    }

    public int getBottomTabMenu() {
        if (UIUtils.isHyperOsAndAbove()) {
            return R.menu.bottom_nav_menu;
        }
        return 0;
    }

    public NavigatorInfoProvider getBottomTabMenuNavInfoProvider() {
        if (UIUtils.isHyperOsAndAbove()) {
            return new NavigatorInfoProvider() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda5
                @Override // miuix.navigator.navigatorinfo.NavigatorInfoProvider
                public final NavigatorInfo onCreateNavigatorInfo(int i) {
                    return NotesListActivity.getBottomTabMenuNavInfoProvider$lambda$5(this.f$0, i);
                }
            };
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NavigatorInfo getBottomTabMenuNavInfoProvider$lambda$5(NotesListActivity notesListActivity, int i) {
        Bundle bundle = new Bundle();
        if (i != 0 && i != 1) {
            return null;
        }
        Logger.INSTANCE.d(notesListActivity.getTAG(), "arg page " + i);
        bundle.putInt(ContentFragment.ARG_PAGE, i);
        return new UpdateFragmentNavInfo(i, notesListActivity.getDefaultContentFragment(), bundle);
    }

    public void onCreatePrimaryNavigation(Navigator navigator, Bundle bundle) {
        Intrinsics.checkNotNullParameter(navigator, "navigator");
        Logger.INSTANCE.d(getTAG(), "onCreatePrimaryNavigation");
        UpdateFragmentNavInfo updateFragmentNavInfoToNote = getUpdateFragmentNavInfoToNote();
        newLabel(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.app_name), R.drawable.miui_ic_notes, updateFragmentNavInfoToNote);
        UpdateFragmentNavInfo updateFragmentNavInfoToTodo = getUpdateFragmentNavInfoToTodo();
        newLabel(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.todo_module_name), R.drawable.miui_ic_todo, updateFragmentNavInfoToTodo);
        sSelectedPageIndex = bundle == null ? PreferenceUtils.getPrefLastUseModule(this) : getHomeViewModel().getPageSelected();
        Logger.INSTANCE.d(getTAG(), "ContentFragment,sSelectedPageIndex:" + sSelectedPageIndex);
        if (sSelectedPageIndex == 1) {
            Logger.INSTANCE.d(getTAG(), "navigate to todo");
            navigator.navigate(updateFragmentNavInfoToTodo);
        } else {
            Logger.INSTANCE.d(getTAG(), "navigate to notes");
            navigator.navigate(updateFragmentNavInfoToNote);
        }
    }

    private final UpdateFragmentNavInfo getUpdateFragmentNavInfoToNote() {
        Bundle bundle = new Bundle();
        bundle.putInt(ContentFragment.ARG_PAGE, 0);
        bundle.putLong("data_id", -4097L);
        return new UpdateFragmentNavInfo(0, getDefaultContentFragment(), bundle);
    }

    private final UpdateFragmentNavInfo getUpdateFragmentNavInfoToTodo() {
        Bundle bundle = new Bundle();
        bundle.putInt(ContentFragment.ARG_PAGE, 1);
        return new UpdateFragmentNavInfo(1, getDefaultContentFragment(), bundle);
    }

    @Override // com.miui.notes.home.NaviBaseActivity, miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mDowngradeReceiver != null) {
            DowngradeReceiver.INSTANCE.unRegister(this, this.mDowngradeReceiver);
        }
        ResourceManager.clearThemes();
        IS_IN_RUNNING = false;
        IActivityController iActivityController = this.controller;
        Intrinsics.checkNotNull(iActivityController);
        iActivityController.onDestroy();
        Utils.fixInputMethodMemLeak(this, getWindow().getDecorView().getWindowToken());
        RecognizeEngine.destroy();
        super.onDestroy();
        Log.d(getTAG(), "onDestroy");
        activityCount--;
    }

    private final void initActivityResultLauncher() {
        this.toKoreaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                NotesListActivity.initActivityResultLauncher$lambda$6(this.f$0, (ActivityResult) obj);
            }
        });
        this.toCtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ui.NotesListActivity.initActivityResultLauncher.2
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "toCtaLauncher result:" + result.getResultCode());
                Companion companion = NotesListActivity.INSTANCE;
                NotesListActivity.isShowingCta = false;
                if (result.getResultCode() == 1 || result.getResultCode() == 666) {
                    NotesListActivity.this.setNeedShowCtaDialog(false);
                    NotesListActivity.this.showPostNotificationsPermission();
                } else if (result.getResultCode() == -3) {
                    NotesListActivity.this.requestCta("onResult local change");
                }
                if (NotesListActivity.this.getNeedShowCtaDialog()) {
                    return;
                }
                ShortcutsTools.initShortcuts();
            }
        });
        this.toCtaPasteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ui.NotesListActivity.initActivityResultLauncher.3
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                Logger.INSTANCE.d(NotesListActivity.this.getTAG(), "toCtaPasteLauncher result:" + result.getResultCode());
                Companion companion = NotesListActivity.INSTANCE;
                NotesListActivity.isShowingCta = false;
                if (result.getResultCode() == 1) {
                    NotesListActivity.this.setNeedShowCtaDialog(false);
                } else if (result.getResultCode() == 666) {
                    NotesListActivity.this.setNeedShowCtaDialog(false);
                    if (NotesListActivity.this.mEditorContext != null && (NotesListActivity.this.mEditorContext instanceof RichEditTextView)) {
                        NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                        BaseNoteApp companion2 = BaseNoteApp.INSTANCE.getInstance();
                        IEditorContext iEditorContext = NotesListActivity.this.mEditorContext;
                        Intrinsics.checkNotNull(iEditorContext);
                        noteClipManager.buildClipSpannableString(companion2, iEditorContext);
                    } else {
                        NoteClipManager.INSTANCE.buildJsonObject(BaseNoteApp.INSTANCE.getInstance());
                    }
                } else if (result.getResultCode() == -3) {
                    NotesListActivity notesListActivity = NotesListActivity.this;
                    notesListActivity.showCtaForPaste(notesListActivity.mEditorContext);
                }
                if (NotesListActivity.this.getNeedShowCtaDialog()) {
                    return;
                }
                ShortcutsTools.initShortcuts();
            }
        });
        this.receivePadMultiData = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.ui.NotesListActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                NotesListActivity.initActivityResultLauncher$lambda$7(this.f$0, (ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActivityResultLauncher$lambda$6(NotesListActivity notesListActivity, ActivityResult activityResult) {
        Intrinsics.checkNotNull(activityResult);
        if (activityResult.getResultCode() == 2308) {
            PreferenceUtils.setKoreaPermissionInfoShowed(BaseNoteApp.INSTANCE.getInstance(), true);
            notesListActivity.showPostNotificationsPermission();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActivityResultLauncher$lambda$7(NotesListActivity notesListActivity, ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            Intent data = activityResult.getData();
            Serializable serializableExtra = null;
            if (android.os.Build.VERSION.SDK_INT < 33) {
                serializableExtra = data != null ? data.getSerializableExtra(notesListActivity.NOTE_DATA) : null;
                Intrinsics.checkNotNull(serializableExtra, "null cannot be cast to non-null type com.miui.common.base.NoteLoadDataEntity");
                serializableExtra = (NoteLoadDataEntity) serializableExtra;
            } else if (data != null) {
                serializableExtra = (NoteLoadDataEntity) data.getSerializableExtra(notesListActivity.NOTE_DATA, NoteLoadDataEntity.class);
            }
            notesListActivity.getHomeViewModel().getCreateEntityData().postValue(serializableExtra);
        }
    }

    public final void showCtaForPaste(IEditorContext editorContext) {
        this.mEditorContext = editorContext;
        PermissionNewUtils.showCtaDialog(this.toCtaPasteLauncher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showPostNotificationsPermission() {
        if (PreferenceUtils.getPostNotificationsPermissionInfoShowed(getApplicationContext())) {
            return;
        }
        PermissionUtils.showNotificationPermission(this);
        PreferenceUtils.setPostNotificationsPermissionInfoShowed(getApplicationContext(), true);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
        if (!RomUtils.isPadMode() || data == null) {
            return;
        }
        data.add(KeyboardShortcutUtils.createNoteEditShortcut(getApplicationContext()));
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Logger.INSTANCE.i(getTAG(), "onWindowFocusChanged true, appStartup ts cost:" + (System.currentTimeMillis() - BaseNoteApp.appAttachTime) + ",ListPageCost:" + (System.currentTimeMillis() - this.onCreateTs));
        } else {
            Logger.INSTANCE.i(getTAG(), "onWindowFocus false");
        }
        IActivityController iActivityController = this.controller;
        if (iActivityController != null) {
            Intrinsics.checkNotNull(iActivityController);
            iActivityController.onWindowFocusChanged(hasFocus);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 82) {
            Logger.INSTANCE.d(getTAG(), "key code menu,go to settings act");
            if (RomUtils.isPadMode()) {
                PadAppPreferenceActivity.INSTANCE.open(this);
                return true;
            }
            NotesPreferenceActivity.open(this);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    public void finish() {
        super.finish();
        Logger.INSTANCE.i(getTAG(), "finish " + Log.getStackTraceString(new Throwable()));
    }

    /* JADX INFO: compiled from: NotesListActivity.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/ui/NotesListActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sSelectedPageIndex", "", "isShowingCta", "", "IS_IN_RUNNING", "NAVIGATOR_ID_NOTE", "NAVIGATOR_ID_TODO", "activityCount", "getActivityCount", "()I", "setActivityCount", "(I)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getActivityCount() {
            return NotesListActivity.activityCount;
        }

        public final void setActivityCount(int i) {
            NotesListActivity.activityCount = i;
        }
    }
}
