package com.miui.common.app;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDiskIOException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import ch.qos.logback.core.CoreConstants;
import com.hiero.logger.base.logger.core.AppLogMgr;
import com.hiero.logger.base.logger.core.LoggerUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.GlobalStat;
import com.miui.common.stat.NoteAppStat;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.stat.StatHelper;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.doodle.DoodleApplication;
import com.miui.floatwindow.FloatService;
import com.miui.notes.R;
import com.miui.notes.base.Constants;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.accessibility.AccessibilityWidgetType;
import com.miui.notes.base.crash.JavaCrashHandler;
import com.miui.notes.base.utils.DeviceUtils;
import com.miui.notes.cloudservice.NoteE2EEManager;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.preference.DatabaseSharedPreferences;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesDatabaseHelper;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.EncryptNewUtil;
import com.miui.notes.tool.util.TrashNotesScheduler;
import com.miui.perm.autostart.AutoStartManager;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.todo.data.Todo;
import com.miui.todo.data.utils.DragSoftPreferenceUtils;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt;
import defpackage.R2;
import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.autodensity.AutoDensityConfig;
import miuix.os.ProcessUtils;

/* JADX INFO: compiled from: NotesAutoAppInitiator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001:\u00010B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J \u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0005H\u0007J\u000e\u0010\"\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020#J\u0010\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u001fH\u0002J \u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010&\u001a\u00020#H\u0002J\u0010\u0010,\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020#H\u0002J\u0006\u0010-\u001a\u00020\tJ\u000e\u0010.\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020#J\u0010\u0010/\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020#H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000b8\u0002@\u0002X\u0083\u000e¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u0003R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0017\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0018\u0010\u0003\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010$\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u001aR\u0014\u0010+\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u001a¨\u00061"}, d2 = {"Lcom/miui/common/app/NotesAutoAppInitiator;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "activityReferences", "", "isActivityChangingConfigurations", "", "accessibility", "Lkotlin/Function1;", "Lcom/miui/notes/base/accessibility/AccessibilityWidgetType;", "getAccessibility$annotations", "mDualScreenRunStateReceiver", "Landroid/content/BroadcastReceiver;", "getMDualScreenRunStateReceiver", "()Landroid/content/BroadcastReceiver;", "mDualScreenRunStateReceiver$delegate", "Lkotlin/Lazy;", "logDebug", "", "msg", "initLog", "getInitLog$annotations", "getInitLog", "()Z", "setInitLog", "(Z)V", "initLogger", "app", "Landroid/content/Context;", "processName", "extraFmtInfo", "onCreate", "Landroid/app/Application;", "isMiuiPlusProcess", "hasNewEncryptedPrivateNotes", CoreConstants.CONTEXT_SCOPE_VALUE, "initDelay", "ts", "", "process", "isInRemoteProcess", "initMainProcess", "shouldAdaptAutoDensity", "onTerminate", "checkAutoStart", "AppLifeCycleObserver", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NotesAutoAppInitiator {
    private static int activityReferences;
    private static boolean initLog;
    private static boolean isActivityChangingConfigurations;
    public static final NotesAutoAppInitiator INSTANCE = new NotesAutoAppInitiator();
    private static final String TAG = "NotesAutoAppInitiator";
    private static Function1<? super AccessibilityWidgetType, Integer> accessibility = new Function1() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Integer.valueOf(NotesAutoAppInitiator.accessibility$lambda$0((AccessibilityWidgetType) obj));
        }
    };

    /* JADX INFO: renamed from: mDualScreenRunStateReceiver$delegate, reason: from kotlin metadata */
    private static final Lazy mDualScreenRunStateReceiver = LazyKt.lazy(new Function0() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NotesAutoAppInitiator.mDualScreenRunStateReceiver_delegate$lambda$1();
        }
    });

    /* JADX INFO: compiled from: NotesAutoAppInitiator.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AccessibilityWidgetType.values().length];
            try {
                iArr[AccessibilityWidgetType.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AccessibilityWidgetType.BUTTON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AccessibilityWidgetType.CHECKBOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    private static /* synthetic */ void getAccessibility$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getInitLog$annotations() {
    }

    private NotesAutoAppInitiator() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int accessibility$lambda$0(AccessibilityWidgetType it) {
        Intrinsics.checkNotNullParameter(it, "it");
        int i = WhenMappings.$EnumSwitchMapping$0[it.ordinal()];
        if (i == 1) {
            return R.string.done_talkback;
        }
        if (i == 2) {
            return R.string.button_talkback;
        }
        if (i == 3) {
            return R.string.checkbox_talkback;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final BroadcastReceiver getMDualScreenRunStateReceiver() {
        return (BroadcastReceiver) mDualScreenRunStateReceiver.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.common.app.NotesAutoAppInitiator$mDualScreenRunStateReceiver$2$1] */
    public static final NotesAutoAppInitiator$mDualScreenRunStateReceiver$2$1 mDualScreenRunStateReceiver_delegate$lambda$1() {
        return new BroadcastReceiver() { // from class: com.miui.common.app.NotesAutoAppInitiator$mDualScreenRunStateReceiver$2$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(intent, "intent");
                Logger.INSTANCE.d(NotesAutoAppInitiator.TAG, "mDualScreenRunStateReceiver-onReceive:" + intent.getAction());
                if (Intrinsics.areEqual(FrameConstant.ACTION_DUAL_SCREEN_RUN_STATE_CHANGED, intent.getAction())) {
                    Bundle extras = intent.getExtras();
                    Intrinsics.checkNotNull(extras);
                    DualScreenManager.INSTANCE.setPcActivityLiving(extras.getInt(FrameConstant.PARAM_DUAL_SCREEN_RUN_STATE, 0) != 0);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void logDebug(String msg) {
        Logger.INSTANCE.d(TAG, msg);
    }

    public static final boolean getInitLog() {
        return initLog;
    }

    public static final void setInitLog(boolean z) {
        initLog = z;
    }

    @JvmStatic
    public static final void initLogger(Context app, String processName, String extraFmtInfo) {
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(processName, "processName");
        Intrinsics.checkNotNullParameter(extraFmtInfo, "extraFmtInfo");
        if (initLog) {
            Log.w(TAG, "already init!!!");
            return;
        }
        AppLogMgr.init$default(AppLogMgr.INSTANCE, null, app.getCacheDir().getPath() + File.separator + "debug_log", app.getCacheDir().getPath() + File.separator + "debug_log", false, false, true, 0, 0, processName + "_log.log", extraFmtInfo, false, R2.array.default_card_stroke_gradient_colors_dark, null);
        initLog = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(final Application app) {
        Intrinsics.checkNotNullParameter(app, "app");
        try {
            Trace.beginSection("NoteApp.onCreate");
            final long jCurrentTimeMillis = System.currentTimeMillis();
            Log.w(TAG, "onAppInit ");
            Properties properties = System.getProperties();
            Intrinsics.checkNotNullExpressionValue(properties, "getProperties(...)");
            properties.put("rx2.purge-enabled", false);
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = "com.miui.notes";
            try {
                String processNameByPid = ProcessUtils.getProcessNameByPid(Process.myPid());
                T t = processNameByPid;
                if (processNameByPid == null) {
                    t = "com.miui.notes:" + Process.myPid();
                }
                objectRef.element = t;
            } catch (Exception e) {
                Log.e(TAG, "get processName with exp :" + e.getMessage());
            }
            DatabaseSharedPreferences.INSTANCE.getDefault();
            Request.init(app);
            NoteE2EEManager.init(BaseNoteApp.INSTANCE.getInstance());
            logDebug("onCreate: " + objectRef.element);
            ProcessLifecycleOwner.INSTANCE.get().getLifecycle().addObserver(new AppLifeCycleObserver());
            Object obj = objectRef.element;
            String processNameByPid2 = ProcessUtils.getProcessNameByPid(Process.myPid());
            Intrinsics.checkNotNullExpressionValue(processNameByPid2, "getProcessNameByPid(...)");
            logDebug("onCreate,init start,process: " + obj + ",isPlusProcess:" + StringsKt.endsWith$default(processNameByPid2, ":miuiplus", false, 2, (Object) null));
            Thread thread = new Thread(new Runnable() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    NotesAutoAppInitiator.onCreate$lambda$2(jCurrentTimeMillis, objectRef, app);
                }
            });
            thread.setName("appInitiatorWorker");
            thread.start();
            logDebug("onCreate (process:" + objectRef.element + ") finished ,ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            Trace.endSection();
            Log.w(Constants.INSTANCE.getMODULE_TAG(), "onAppInit finished");
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void onCreate$lambda$2(long j, Ref.ObjectRef objectRef, Application application) {
        INSTANCE.initDelay(j, (String) objectRef.element, application);
    }

    private static final Unit onCreate$lambda$4(long j, int i) {
        com.miui.notes.base.utils.Logger.INSTANCE.e("NoteApp", "warning!!!frame loss with ts:" + j + " ,loss:" + i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: compiled from: NotesAutoAppInitiator.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\r"}, d2 = {"Lcom/miui/common/app/NotesAutoAppInitiator$AppLifeCycleObserver;", "Landroidx/lifecycle/DefaultLifecycleObserver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "onStart", "onResume", "onPause", "onStop", "onDestroy", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class AppLifeCycleObserver implements DefaultLifecycleObserver {
        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onCreate(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onCreate(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppCreate");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStart(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onStart(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppStart");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onResume(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onResume(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppResume");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onPause(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onPause(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppPause");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStop(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onStop(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppStop");
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onDestroy(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            super.onDestroy(owner);
            com.miui.notes.base.utils.Logger.INSTANCE.i(NotesAutoAppInitiator.TAG, "onAppDestroy");
        }
    }

    private final boolean isMiuiPlusProcess() {
        String processNameByPid = ProcessUtils.getProcessNameByPid(Process.myPid());
        Intrinsics.checkNotNullExpressionValue(processNameByPid, "getProcessNameByPid(...)");
        return StringsKt.endsWith$default(processNameByPid, ":miuiplus", false, 2, (Object) null);
    }

    private final boolean hasNewEncryptedPrivateNotes(Context context) {
        try {
            Cursor cursorQuery = NotesDatabaseHelper.getInstance(context).getReadableDatabase().query("note", new String[]{"_id"}, "type = ? AND (parent_id = ? OR origin_parent_id = ?) AND (snippet LIKE ? OR plain_text LIKE ?)", new String[]{"0", "-4", "-4", "miunifiedencrypt_%", "miunifiedencrypt_%"}, null, null, null, "1");
            Intrinsics.checkNotNullExpressionValue(cursorQuery, "query(...)");
            Cursor cursor = cursorQuery;
            try {
                boolean z = cursor.getCount() > 0;
                CloseableKt.closeFinally(cursor, null);
                Log.d(TAG, "hasNewEncryptedPrivateNotes: " + z);
                return z;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(cursor, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "hasNewEncryptedPrivateNotes error: " + e.getMessage());
            return true;
        }
    }

    private final void initDelay(long ts, String process, Application context) {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.i(str, "int process:" + process + ",init start:" + ts);
        try {
            try {
                Trace.beginSection("NoteApp.init");
                if (!isInRemoteProcess() && Utils.isAboveSDK34() && RomUtils.isHyperOS3Rom() && !EncryptNewUtil.INSTANCE.isServiceConnected()) {
                    if (hasNewEncryptedPrivateNotes(context)) {
                        Log.d(str, "initDelay: found new-encrypted private notes, connect service immediately");
                        try {
                            EncryptNewUtil.INSTANCE.connectToService(BaseNoteApp.INSTANCE.getInstance(), new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.common.app.NotesAutoAppInitiator.initDelay.1
                                @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                                public void onServiceReady() {
                                    Log.d(NotesAutoAppInitiator.TAG, "initDelay: connect service successfully (private notes exist)");
                                }

                                @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                                public void onServiceLost() {
                                    Log.d(NotesAutoAppInitiator.TAG, "initDelay: connecting service failed (private notes exist)");
                                }
                            });
                            Unit unit = Unit.INSTANCE;
                        } catch (Exception e) {
                            Integer.valueOf(Log.e(TAG, "initDelay: MiUnifiedEncrypt service connection failed, e: " + e));
                        }
                    } else {
                        Log.d(str, "initDelay: no new-encrypted private notes, defer service connection to foreground");
                    }
                }
                LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
                linkedHashMap.put("hw-Level", RomUtils.getDeviceLevel());
                linkedHashMap.put("build fingerprint", RomUtils.INSTANCE.getFingerprint());
                linkedHashMap.put("db-version", "53");
                linkedHashMap.put("os-version", String.valueOf(miuix.core.util.RomUtils.getHyperOsVersion()));
                String formattedItemsInfo = DeviceUtils.INSTANCE.getFormattedItemsInfo(context, linkedHashMap);
                logDebug("init " + process + ",channel: with env:" + formattedItemsInfo);
                initLogger(context, process, formattedItemsInfo);
                JavaCrashHandler.INSTANCE.getInstance().init(context, new Function1() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return NotesAutoAppInitiator.initDelay$lambda$7((String) obj);
                    }
                });
                if (!isMiuiPlusProcess()) {
                    AutoDensityConfig.init(context);
                }
                if (DragSoftPreferenceUtils.getIsFirstTodoCloud()) {
                    logDebug("firstTodoCloud,set max sort id:" + DragSoftPreferenceUtils.getMaxSortID());
                    PreferenceUtils.setMaxSortID(DragSoftPreferenceUtils.getMaxSortID());
                    DragSoftPreferenceUtils.setIsFirstTodoCloud(false);
                }
                if (!isInRemoteProcess()) {
                    initMainProcess(context);
                    AppLogMgr.INSTANCE.trimLogFileWithCapacity(20971520L);
                }
                Function4<? super Integer, ? super String, ? super String, ? super String, Unit> function4 = new Function4() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function4
                    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                        return NotesAutoAppInitiator.initDelay$lambda$8(((Integer) obj).intValue(), (String) obj2, (String) obj3, (String) obj4);
                    }
                };
                com.miui.notes.base.utils.Logger.INSTANCE.setLogAgent(function4);
                NetConfig.INSTANCE.setLogAgent(function4);
                try {
                    Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                    declaredField.setAccessible(true);
                    declaredField.set(null, 524288000);
                } catch (Exception e2) {
                    com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, e2.toString());
                }
                context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.miui.common.app.NotesAutoAppInitiator.initDelay.3
                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityDestroyed(Activity activity) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityPaused(Activity activity) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityResumed(Activity activity) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                        Intrinsics.checkNotNullParameter(outState, "outState");
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                        BaseNoteApp.Companion companion = BaseNoteApp.INSTANCE;
                        BaseNoteApp.isInForeground = true;
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStarted(Activity activity) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                        NotesAutoAppInitiator notesAutoAppInitiator = NotesAutoAppInitiator.INSTANCE;
                        NotesAutoAppInitiator.activityReferences++;
                        if (NotesAutoAppInitiator.activityReferences == 1 && !NotesAutoAppInitiator.isActivityChangingConfigurations) {
                            BaseNoteApp.Companion companion = BaseNoteApp.INSTANCE;
                            BaseNoteApp.isInForeground = true;
                            if (Utils.isAboveSDK34() && RomUtils.isHyperOS3Rom() && !EncryptNewUtil.INSTANCE.isServiceConnected()) {
                                NotesAutoAppInitiator.INSTANCE.logDebug("onActivityStarted: connect MiUnifiedEncrypt service");
                                try {
                                    EncryptNewUtil.INSTANCE.connectToService(BaseNoteApp.INSTANCE.getInstance(), new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.common.app.NotesAutoAppInitiator$initDelay$3$onActivityStarted$1
                                        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                                        public void onServiceReady() {
                                            NotesAutoAppInitiator.INSTANCE.logDebug("onActivityStarted: connect service successfully");
                                        }

                                        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                                        public void onServiceLost() {
                                            NotesAutoAppInitiator.INSTANCE.logDebug("onActivityStarted: connecting service failed");
                                        }
                                    });
                                } catch (Exception e3) {
                                    NotesAutoAppInitiator.INSTANCE.logDebug("onActivityStarted: MiUnifiedEncrypt service connection failed, e: " + e3);
                                }
                            }
                            if (StatHelper.getStatInstance() != null) {
                                Logger.INSTANCE.d("NoteAppStat", "onActivityStarted: open APP");
                                StatHelper.getStatInstance().builderUUID();
                                NoteAppStat.reportNoteAppEnterEvent(activity);
                            }
                        }
                        NotesAutoAppInitiator notesAutoAppInitiator2 = NotesAutoAppInitiator.INSTANCE;
                        NotesAutoAppInitiator.isActivityChangingConfigurations = false;
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStopped(Activity activity) {
                        Intrinsics.checkNotNullParameter(activity, "activity");
                        NotesAutoAppInitiator notesAutoAppInitiator = NotesAutoAppInitiator.INSTANCE;
                        NotesAutoAppInitiator.isActivityChangingConfigurations = activity.isChangingConfigurations();
                        NotesAutoAppInitiator notesAutoAppInitiator2 = NotesAutoAppInitiator.INSTANCE;
                        NotesAutoAppInitiator.activityReferences--;
                        if (NotesAutoAppInitiator.activityReferences != 0 || NotesAutoAppInitiator.isActivityChangingConfigurations) {
                            return;
                        }
                        BaseNoteApp.Companion companion = BaseNoteApp.INSTANCE;
                        BaseNoteApp.isInForeground = false;
                        Logger.INSTANCE.d("NoteAppStat", "onActivityStopped: quit APP");
                        NoteAppStat.reportNoteAppQuitEvent();
                        NotesPageStat.QUIT_TYPE = "SystemBack";
                    }
                });
                logDebug("init(process:" + process + ") finished ,ts cost:" + (System.currentTimeMillis() - ts) + "ms");
            } catch (Exception e3) {
                com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "init delay,catch an exp:" + e3.getMessage());
            }
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initDelay$lambda$7(String str) {
        com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "catch a panic :" + str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initDelay$lambda$8(int i, String tag, String extra, String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(extra, "extra");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            LoggerUtils.INSTANCE.v(tag, extra, msg);
        }
        return Unit.INSTANCE;
    }

    private final boolean isInRemoteProcess() {
        String processNameByPid = ProcessUtils.getProcessNameByPid(Process.myPid());
        return processNameByPid != null && (StringsKt.endsWith$default(processNameByPid, ":remote", false, 2, (Object) null) || StringsKt.endsWith$default(processNameByPid, ":sync", false, 2, (Object) null) || StringsKt.endsWith$default(processNameByPid, ":widgetProvider", false, 2, (Object) null) || StringsKt.endsWith$default(processNameByPid, ":task", false, 2, (Object) null));
    }

    private final void initMainProcess(Application app) {
        logDebug("initializeInUiProcess");
        if (RomUtils.isPadMode()) {
            try {
                logDebug("pad mode,delete empty notes &update widget with " + NoteStore.deleteEmpty(app, null) + " notes");
            } catch (SQLiteDiskIOException e) {
                e.printStackTrace();
                Logger.INSTANCE.e(TAG, "catch a exception:" + e.getMessage());
                Toast.makeText(app, "space no enlough..", 0).show();
            }
        }
        Function1<? super AccessibilityWidgetType, Integer> function1 = accessibility;
        if (function1 != null) {
            AccessibilityDelegateUtil.INSTANCE.registerAccessibilityWidgetTypes(function1);
        }
        if (!isMiuiPlusProcess()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_RUN_STATE_CHANGED);
            try {
                if (Utils.isAboveSDK34()) {
                    app.registerReceiver(getMDualScreenRunStateReceiver(), intentFilter, 2);
                } else {
                    app.registerReceiver(getMDualScreenRunStateReceiver(), intentFilter);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                logDebug("catch a exception:" + e2.getMessage());
                Unit unit = Unit.INSTANCE;
            }
        } else {
            logDebug("in miui plus process!!!!");
            String str = app.getPackageName() + ":miuiplus";
            com.miui.notes.base.utils.Logger.INSTANCE.i(TAG, "WebView.setDataDirectorySuffix: " + str);
            WebView.setDataDirectorySuffix(str);
        }
        Object systemService = app.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.deleteNotificationChannel("notes_alarm_id");
        notificationManager.deleteNotificationChannel("todo_alarm_id");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass2(app, null), 3, null);
        Application application = app;
        TrashNotesScheduler.schedule$default(TrashNotesScheduler.INSTANCE, application, 0L, 2, null);
        PermissionUtils.initPermissionsFlagGroup();
        ResourceManager.isNightMode = UIUtils.isNightMode(application);
    }

    /* JADX INFO: renamed from: com.miui.common.app.NotesAutoAppInitiator$initMainProcess$2, reason: invalid class name */
    /* JADX INFO: compiled from: NotesAutoAppInitiator.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.common.app.NotesAutoAppInitiator$initMainProcess$2", f = "NotesAutoAppInitiator.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Application $app;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Application application, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$app = application;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$app, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Thread.currentThread().setName("ioWorker");
            if (PreferenceUtils.isCTAAccepted(this.$app.getApplicationContext()) && !PreferenceUtils.getAllowNetwork(this.$app)) {
                PreferenceUtils.setAllowNetwork(this.$app, true);
            }
            if (RomUtils.isInternationalBuild() && !PreferenceUtils.getAllowXiaoai(this.$app)) {
                PreferenceUtils.setAllowXiaoai(this.$app, true);
            }
            PreferenceUtils.getAudioTimeSharedPref();
            AttachmentUtils.INSTANCE.cleanAttachmentFile(this.$app);
            AttachmentUtils.INSTANCE.checkAudioFile(this.$app);
            NoteStore.clearSyncInfoIfNeed(this.$app);
            NotesAutoAppInitiator.INSTANCE.logDebug("startFloatServiceForExpert...");
            if (PreferenceUtils.getEnableFloatMode(this.$app) && Settings.canDrawOverlays(this.$app)) {
                if (!LiteUtils.isSuperLite()) {
                    GlobalStat.Status.isQuickNote = true;
                    FloatService.INSTANCE.startExcerptService(this.$app, NotesAutoAppInitiator.TAG + "->initializeInUiProcess");
                } else {
                    try {
                        Settings.Secure.putInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "notes_pref_enable_float_mode", 0);
                    } catch (Exception unused) {
                    }
                    PreferenceUtils.setEnableFloatMode(this.$app, false);
                }
            }
            this.$app.getContentResolver().notifyChange(Notes.Note.CONTENT_URI, (ContentObserver) null, false);
            DoodleApplication.init(this.$app);
            try {
                NotesAutoAppInitiator.INSTANCE.checkAutoStart(this.$app);
            } catch (Exception e) {
                com.miui.notes.base.utils.Logger logger = com.miui.notes.base.utils.Logger.INSTANCE;
                String message = e.getMessage();
                Intrinsics.checkNotNull(message);
                logger.d("NoteApp", message);
            }
            UIUtils.setMiuiWidgetSupported(this.$app);
            return Unit.INSTANCE;
        }
    }

    public final boolean shouldAdaptAutoDensity() {
        return !isMiuiPlusProcess();
    }

    public final void onTerminate(Application app) {
        Intrinsics.checkNotNullParameter(app, "app");
        NoteClipManager.INSTANCE.clearClipData();
        if (isInRemoteProcess() || isMiuiPlusProcess()) {
            return;
        }
        try {
            app.unregisterReceiver(getMDualScreenRunStateReceiver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkAutoStart(Application app) {
        if (AutoStartManager.checkAutoStart(app) != 0) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            booleanRef.element = PreferenceUtils.getEnableFloatMode(BaseNoteApp.INSTANCE.getInstance());
            if (!booleanRef.element) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                Logger.INSTANCE.i("NoteApp", "setupAlarms: currentDate=" + Utils.formatTime(jCurrentTimeMillis));
                Uri uriAppendFlag = NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY);
                String[] strArr = {"_id", Notes.Note.ALERTED_DATE};
                com.miui.notes.base.utils.Logger.INSTANCE.d("NoteApp", "query notes  data for alert ");
                Cursor cursorQuery = app.getContentResolver().query(uriAppendFlag, strArr, "alert_date>? AND type=0", new String[]{String.valueOf(jCurrentTimeMillis)}, null);
                if (cursorQuery != null) {
                    NoteEntityHelper.INSTANCE.useWithClose(cursorQuery, "NoteAppInit", new Function1() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return NotesAutoAppInitiator.checkAutoStart$lambda$10(booleanRef, (Cursor) obj);
                        }
                    });
                }
            }
            if (!booleanRef.element) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                Logger.INSTANCE.i("NoteApp", "setupTodoAlarms: currentDate=" + Utils.formatTime(jCurrentTimeMillis2));
                Cursor cursorQuery2 = app.getContentResolver().query(Uri.parse(Todo.URI_TODO_OUTER), null, "remind_time >? AND is_finish == 0", new String[]{String.valueOf(jCurrentTimeMillis2)}, null);
                com.miui.notes.base.utils.Logger.INSTANCE.d("NoteApp", "query notes  data for reminder ");
                if (cursorQuery2 != null) {
                    NoteEntityHelper.INSTANCE.useWithClose(cursorQuery2, "NoteApp", new Function1() { // from class: com.miui.common.app.NotesAutoAppInitiator$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return NotesAutoAppInitiator.checkAutoStart$lambda$11(booleanRef, (Cursor) obj);
                        }
                    });
                    return;
                }
                return;
            }
            Utils.checkAndSwitchAutoStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit checkAutoStart$lambda$10(Ref.BooleanRef booleanRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.getCount() > 0) {
            booleanRef.element = true;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit checkAutoStart$lambda$11(Ref.BooleanRef booleanRef, Cursor it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.getCount() > 0) {
            booleanRef.element = true;
        }
        return Unit.INSTANCE;
    }
}
