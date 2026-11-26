package com.miui.floatwindow;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.frame.AppInstallBroadcastReceiver;
import com.miui.floatwindow.utils.GestureBroadcastUtils;
import com.miui.notes.R;
import com.miui.notes.excerpt.ExcerptInfo;
import com.miui.notes.excerpt.MediaIdInfo;
import com.miui.notes.excerpt.MediaUtils;
import com.miui.notes.excerpt.PicInfo;
import com.miui.richeditor.IRichEditor;
import defpackage.R2;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import miuix.android.content.MiuiIntent;
import miuix.os.DeviceHelper;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: FloatService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 Z2\u00020\u0001:\u0002YZB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010,\u001a\u00020-H\u0016J\"\u0010.\u001a\u00020\u000b2\b\u0010/\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u000bH\u0016J\b\u00103\u001a\u00020-H\u0016J\u0012\u00104\u001a\u0004\u0018\u0001052\u0006\u0010/\u001a\u000200H\u0016J\b\u00106\u001a\u00020-H\u0016J\u0010\u00107\u001a\u00020-2\u0006\u00108\u001a\u00020\u000bH\u0016J\u0012\u00109\u001a\u00020-2\b\u0010:\u001a\u0004\u0018\u000100H\u0016J\b\u0010=\u001a\u00020-H\u0002J\b\u0010$\u001a\u00020-H\u0002J\u0010\u0010>\u001a\u00020-2\u0006\u0010?\u001a\u00020@H\u0016J\b\u0010A\u001a\u00020\tH\u0003J\b\u0010B\u001a\u00020\tH\u0002J\b\u0010C\u001a\u00020-H\u0002J\u0018\u0010V\u001a\u00020-2\u0006\u0010W\u001a\u00020\u001a2\u0006\u0010X\u001a\u00020\u001aH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0018\u00010\rR\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010'\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b(\u0010)R\u000e\u0010;\u001a\u00020<X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010D\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010I\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u001a\u0010N\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010K\"\u0004\bP\u0010MR\u001b\u0010Q\u001a\u00020R8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bU\u0010+\u001a\u0004\bS\u0010T¨\u0006["}, d2 = {"Lcom/miui/floatwindow/FloatService;", "Landroid/app/Service;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNotificationManager", "Landroid/app/NotificationManager;", "mLocale", "Ljava/util/Locale;", "mBuilder", "Landroid/app/Notification$Builder;", "mCurrentNightMode", "", "mContentOb", "Lcom/miui/floatwindow/FloatService$SettingsContentObserver;", "mIsShow", "", "mCurrentFontScale", "", "mCurrentFontWeight", "mClipboardManager", "Landroid/content/ClipboardManager;", "mOnPrimaryClipChangedListener", "Landroid/content/ClipboardManager$OnPrimaryClipChangedListener;", "mContentString", "", "mClipChangeTime", "", "mMediaIdInfo", "Lcom/miui/notes/excerpt/MediaIdInfo;", "mAppInstallRemovedReceiver", "Lcom/miui/floatwindow/frame/AppInstallBroadcastReceiver;", "mScreenWidth", "mIsFocusMode", "mIsKidMode", "mHandler", "Landroid/os/Handler;", "init", "mRunnable", "Ljava/lang/Runnable;", "handler", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "onCreate", "", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "onDestroy", "onBind", "Landroid/os/IBinder;", "onLowMemory", "onTrimMemory", "level", "onTaskRemoved", "rootIntent", "mBroadcastReceiver", "Landroid/content/BroadcastReceiver;", "runAsForeground", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "createNotificationBuilderAboveSDK26", "createNotificationBuilderBelowSDK26", "registerClipEvents", "count", "getCount", "()I", "setCount", "(I)V", "lastTime", "getLastTime", "()J", "setLastTime", "(J)V", "lastImageId", "getLastImageId", "setLastImageId", "mPicObserver", "Landroid/database/ContentObserver;", "getMPicObserver", "()Landroid/database/ContentObserver;", "mPicObserver$delegate", "notifyImgChange", "id", "nowTime", "SettingsContentObserver", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatService extends Service {
    public static final String ACTION_FSG_STATE_CHANGE = "com.android.systemui.fsgesture";
    public static final String ACTION_SCREEN_SHOT = "miui.intent.TAKE_SCREENSHOT";
    public static final String ACTION_SHOW_QUICK_NOTE_UI = "miui.intent.action.SHOW_QUICK_NOTE_UI";
    public static final String ACTION_STOP_EXCERPT_SERVICE = "miui.intent.action.STOP_EXCERPT_SERVICE";
    public static final String ACTION_STOP_FLOAT_SERVICE = "miui.intent.action.STOP_FLOAT_SERVICE";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String FOCUS_MODE_STATUS = "settings_focus_mode_status";
    private static final String GROUP_NOTIFICATION_FLOAT = "group1";
    public static final String KID_MODE_STATUS = "kid_mode_status";
    private static final String TAG = "FloatService";
    private int count;
    private boolean init;
    private long lastImageId;
    private AppInstallBroadcastReceiver mAppInstallRemovedReceiver;
    private Notification.Builder mBuilder;
    private long mClipChangeTime;
    private ClipboardManager mClipboardManager;
    private SettingsContentObserver mContentOb;
    private int mCurrentNightMode;
    private Handler mHandler;
    private int mIsFocusMode;
    private int mIsKidMode;
    private boolean mIsShow;
    private Locale mLocale;
    private MediaIdInfo mMediaIdInfo;
    private NotificationManager mNotificationManager;
    private ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;
    private int mScreenWidth;
    private float mCurrentFontScale = 1.0f;
    private int mCurrentFontWeight = 50;
    private String mContentString = "";
    private final Runnable mRunnable = new Runnable() { // from class: com.miui.floatwindow.FloatService$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            FloatService.mRunnable$lambda$0(this.f$0);
        }
    };

    /* JADX INFO: renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.FloatService$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FloatService.handler_delegate$lambda$1();
        }
    });
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.miui.floatwindow.FloatService$mBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            String action = intent.getAction();
            if (Intrinsics.areEqual(FloatService.ACTION_STOP_FLOAT_SERVICE, action)) {
                this.this$0.stopSelf();
                return;
            }
            if (Intrinsics.areEqual("miui.intent.TAKE_SCREENSHOT", action)) {
                if (this.this$0.getResources().getConfiguration().orientation == 2) {
                    return;
                }
                FloatWindowManagerAgent.changeGuideVisible(intent.getBooleanExtra(MiuiIntent.EXTRA_IS_FINISHED, false));
                return;
            }
            if (Intrinsics.areEqual(FloatService.ACTION_SHOW_QUICK_NOTE_UI, action)) {
                FloatWindowManagerAgent.INSTANCE.showQuickNoteUI(true);
                return;
            }
            if (Intrinsics.areEqual("com.android.systemui.fsgesture", action)) {
                boolean booleanExtra = intent.getBooleanExtra(GestureBroadcastUtils.IS_ENTER, false);
                String stringExtra = intent.getStringExtra(GestureBroadcastUtils.TYPE_FROM);
                Logger.INSTANCE.d("FloatService", "onReceive: isEnter = " + booleanExtra + " typeFrom = " + stringExtra);
                if (booleanExtra && stringExtra != null && !Intrinsics.areEqual(stringExtra, GestureBroadcastUtils.TYPE_FROM_TASK_TOOLBOX)) {
                    FloatWindowManagerAgent.INSTANCE.showQuickNoteUI(false);
                }
                if (stringExtra == null || Intrinsics.areEqual(stringExtra, GestureBroadcastUtils.TYPE_FROM_TASK_TOOLBOX)) {
                    return;
                }
                FloatWindowManagerAgent.INSTANCE.hideOrShowCoverView(!booleanExtra);
                return;
            }
            if (TextUtils.equals(FloatService.ACTION_STOP_EXCERPT_SERVICE, action)) {
                Logger.INSTANCE.d("FloatService", "get Action ACTION_STOP_EXCERPT_SERVICE");
                FloatWindowManagerAgent.stopContinuousExcerpt();
            }
        }
    };
    private long lastTime = -1;

    /* JADX INFO: renamed from: mPicObserver$delegate, reason: from kotlin metadata */
    private final Lazy mPicObserver = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.FloatService$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FloatService.mPicObserver_delegate$lambda$4(this.f$0);
        }
    });

    @JvmStatic
    public static final void startExcerptService(Context context, String str) {
        INSTANCE.startExcerptService(context, str);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mRunnable$lambda$0(FloatService floatService) {
        FloatWindowManagerAgent.INSTANCE.startFloatWindowService();
        floatService.mIsShow = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Handler handler_delegate$lambda$1() {
        return new Handler(Looper.getMainLooper());
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT <= 30) {
            runAsForeground();
        }
        Logger.INSTANCE.d(TAG, "onCreate");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT > 30) {
            runAsForeground();
        }
        Logger.INSTANCE.d(TAG, "onStartCommand");
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logger.INSTANCE.d(TAG, "onDestroy");
        this.init = false;
        if (this.mIsShow) {
            FloatWindowManagerAgent.INSTANCE.stopFloatWindowService();
            this.mIsShow = false;
        }
        try {
            unregisterReceiver(this.mBroadcastReceiver);
            unregisterReceiver(this.mAppInstallRemovedReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.INSTANCE.e(TAG, "onDestroy/with exception:" + ExceptionsKt.stackTraceToString(e));
        }
        SettingsContentObserver settingsContentObserver = this.mContentOb;
        if (settingsContentObserver != null) {
            BaseNoteApp.INSTANCE.getInstance().getContentResolver().unregisterContentObserver(settingsContentObserver);
        }
        ClipboardManager clipboardManager = this.mClipboardManager;
        if (clipboardManager != null) {
            clipboardManager.removePrimaryClipChangedListener(this.mOnPrimaryClipChangedListener);
        }
        getContentResolver().unregisterContentObserver(getMPicObserver());
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Logger.INSTANCE.d(TAG, "onLowMemory");
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logger.INSTANCE.d(TAG, "onTrimMemory");
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Logger.INSTANCE.d(TAG, "onTaskRemoved");
    }

    private final void runAsForeground() {
        Notification notificationBuild = createNotificationBuilderAboveSDK26().build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        if (Build.VERSION.SDK_INT >= 31) {
            try {
                startForeground(110, notificationBuild);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "runAsForeground failed: " + e.getMessage());
                stopSelf();
                return;
            }
        }
        startForeground(110, notificationBuild);
    }

    private final synchronized void init() {
        if (this.init) {
            Logger.INSTANCE.e(TAG, "init: already init!!!!");
            return;
        }
        Companion companion = INSTANCE;
        Configuration configuration = getResources().getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        this.mLocale = companion.getLocaleFromConfig(configuration);
        Logger.INSTANCE.d(TAG, "init(), " + this.mLocale);
        this.mCurrentNightMode = DisplayUtils.isNightMode() ? 32 : 16;
        if (Settings.Global.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), FOCUS_MODE_STATUS, 0) == 1 || Settings.Global.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), KID_MODE_STATUS, 0) == 1) {
            this.mIsShow = false;
        } else {
            FloatWindowManagerAgent.INSTANCE.startFloatWindowService();
            this.mIsShow = true;
        }
        this.mAppInstallRemovedReceiver = new AppInstallBroadcastReceiver();
        if (Utils.isAboveSDK34()) {
            AppInstallBroadcastReceiver appInstallBroadcastReceiver = this.mAppInstallRemovedReceiver;
            Intrinsics.checkNotNull(appInstallBroadcastReceiver);
            registerReceiver(appInstallBroadcastReceiver, appInstallBroadcastReceiver.getIntentFilter(), 2);
        } else {
            AppInstallBroadcastReceiver appInstallBroadcastReceiver2 = this.mAppInstallRemovedReceiver;
            Intrinsics.checkNotNull(appInstallBroadcastReceiver2);
            registerReceiver(appInstallBroadcastReceiver2, appInstallBroadcastReceiver2.getIntentFilter());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_STOP_FLOAT_SERVICE);
        intentFilter.addAction(ACTION_SHOW_QUICK_NOTE_UI);
        intentFilter.addAction("com.android.systemui.fsgesture");
        intentFilter.addAction("miui.intent.TAKE_SCREENSHOT");
        intentFilter.addAction(ACTION_STOP_EXCERPT_SERVICE);
        if (Utils.isAboveSDK34()) {
            registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
        } else {
            registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
        this.mContentOb = new SettingsContentObserver();
        ContentResolver contentResolver = BaseNoteApp.INSTANCE.getInstance().getContentResolver();
        Uri uriFor = Settings.Global.getUriFor(FOCUS_MODE_STATUS);
        SettingsContentObserver settingsContentObserver = this.mContentOb;
        Intrinsics.checkNotNull(settingsContentObserver);
        contentResolver.registerContentObserver(uriFor, false, settingsContentObserver);
        ContentResolver contentResolver2 = BaseNoteApp.INSTANCE.getInstance().getContentResolver();
        Uri uriFor2 = Settings.Global.getUriFor(KID_MODE_STATUS);
        SettingsContentObserver settingsContentObserver2 = this.mContentOb;
        Intrinsics.checkNotNull(settingsContentObserver2);
        contentResolver2.registerContentObserver(uriFor2, false, settingsContentObserver2);
        this.mScreenWidth = DisplayUtils.getRealWidth(this);
        this.mCurrentFontScale = Settings.System.getFloat(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "font_scale", 1.0f);
        this.mCurrentFontWeight = Settings.System.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "key_miui_font_weight_scale", 50);
        Logger.INSTANCE.d(TAG, " mCurrentFontScale = " + this.mCurrentFontScale + " mCurrentFontWeight = " + this.mCurrentFontWeight);
        registerClipEvents();
        this.mMediaIdInfo = new MediaIdInfo(-1L, 0L, "");
        getContentResolver().registerContentObserver(MediaUtils.INSTANCE.getMEDIA_QUERY_URL(), true, getMPicObserver());
        this.init = true;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x008e  */
    /* JADX WARN: Code duplicated, block: B:45:0x0120  */
    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        boolean z;
        Locale locale;
        Notification.Builder builder;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if (RomUtils.isFlipFoldDevice() && DeviceHelper.isTinyScreen(getApplicationContext())) {
            Logger.INSTANCE.i(TAG, "onConfigurationChanged, stopFloatWindowService");
            FloatWindowManagerAgent.stopContinuousExcerpt();
            FloatWindowManagerAgent.INSTANCE.stopFloatWindowService();
            Handler handler = this.mHandler;
            if (handler != null) {
                Intrinsics.checkNotNull(handler);
                handler.removeCallbacks(this.mRunnable);
            }
            this.mIsShow = false;
            return;
        }
        Locale localeFromConfig = INSTANCE.getLocaleFromConfig(newConfig);
        int realWidth = DisplayUtils.getRealWidth(this);
        boolean z2 = true;
        if (localeFromConfig == null || ((locale = this.mLocale) != null && Intrinsics.areEqual(locale, localeFromConfig))) {
            z = false;
        } else {
            this.mLocale = localeFromConfig;
            if (this.mNotificationManager == null || (builder = this.mBuilder) == null) {
                z = false;
            } else {
                Intrinsics.checkNotNull(builder);
                builder.setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.app_notification_info));
                NotificationManager notificationManager = this.mNotificationManager;
                Intrinsics.checkNotNull(notificationManager);
                Notification.Builder builder2 = this.mBuilder;
                Intrinsics.checkNotNull(builder2);
                notificationManager.notify(110, builder2.build());
                z = true;
            }
        }
        int i = newConfig.uiMode & 48;
        if (this.mCurrentNightMode != i) {
            this.mCurrentNightMode = i;
            z = true;
        }
        if (this.mScreenWidth != realWidth) {
            this.mScreenWidth = realWidth;
            z = true;
        }
        float f = newConfig.fontScale;
        int i2 = Settings.System.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "key_miui_font_weight_scale", 50);
        Logger.INSTANCE.d(TAG, " newFontScale = " + newConfig.fontScale + " oldFontScale = " + this.mCurrentFontScale + " newFontWeight = " + i2 + " oldFontWeight = " + this.mCurrentFontWeight);
        if (this.mCurrentFontScale != f) {
            this.mCurrentFontScale = f;
        } else {
            if (this.mCurrentFontWeight != i2) {
                this.mCurrentFontWeight = i2;
            }
            FloatWindowManagerAgent.INSTANCE.onServiceConfigChanged(newConfig);
            if (!z || this.mIsShow || !RomUtils.isFlipFoldDevice() || DeviceHelper.isTinyScreen(getApplicationContext())) {
                z2 = z;
            } else {
                Logger.INSTANCE.i(TAG, "onConfigurationChanged, flip inner need create");
            }
            if (!z2 && this.mIsFocusMode == 0 && this.mIsKidMode == 0) {
                FloatWindowManagerAgent.INSTANCE.stopFloatWindowService();
                this.mIsShow = false;
                if (this.mHandler == null) {
                    this.mHandler = new Handler();
                }
                Handler handler2 = this.mHandler;
                Intrinsics.checkNotNull(handler2);
                handler2.postDelayed(this.mRunnable, 200L);
                FloatWindowManagerAgent.updateDockMode();
                return;
            }
            return;
        }
        z = true;
        FloatWindowManagerAgent.INSTANCE.onServiceConfigChanged(newConfig);
        if (z) {
            z2 = z;
        } else {
            z2 = z;
        }
        if (!z2) {
        }
    }

    private final Notification.Builder createNotificationBuilderAboveSDK26() {
        NotificationChannel notificationChannel = new NotificationChannel("110", getString(R.string.pref_title_todo_float_mode), 3);
        notificationChannel.enableLights(false);
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        this.mNotificationManager = notificationManager;
        if (notificationManager != null) {
            Intrinsics.checkNotNull(notificationManager);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        FloatService floatService = this;
        this.mBuilder = new Notification.Builder(floatService, "110");
        Intent intent = new Intent("");
        Notification.Builder builder = this.mBuilder;
        Intrinsics.checkNotNull(builder);
        builder.setContentIntent(PendingIntent.getBroadcast(floatService, 0, intent, 67108864)).setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.app_notification_info)).setSmallIcon(R.drawable.notifi_small_icon).setOngoing(true).setGroup(GROUP_NOTIFICATION_FLOAT).setWhen(0L);
        Notification.Builder builder2 = this.mBuilder;
        Intrinsics.checkNotNull(builder2);
        return builder2;
    }

    private final Notification.Builder createNotificationBuilderBelowSDK26() {
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        this.mNotificationManager = (NotificationManager) systemService;
        this.mBuilder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent("");
        Notification.Builder builder = this.mBuilder;
        Intrinsics.checkNotNull(builder);
        builder.setContentIntent(PendingIntent.getBroadcast(this, 0, intent, 67108864)).setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.app_notification_info)).setSmallIcon(R.drawable.notifi_small_icon).setOngoing(true).setWhen(0L);
        Notification.Builder builder2 = this.mBuilder;
        Intrinsics.checkNotNull(builder2);
        return builder2;
    }

    /* JADX INFO: compiled from: FloatService.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lcom/miui/floatwindow/FloatService$SettingsContentObserver;", "Landroid/database/ContentObserver;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/FloatService;)V", "onChange", "", "selfChange", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            FloatService.this.mIsFocusMode = Settings.Global.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), FloatService.FOCUS_MODE_STATUS, 0);
            FloatService.this.mIsKidMode = Settings.Global.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), FloatService.KID_MODE_STATUS, 0);
            if ((FloatService.this.mIsFocusMode == 1 || FloatService.this.mIsKidMode == 1) && FloatService.this.mIsShow) {
                FloatWindowManagerAgent.INSTANCE.stopFloatWindowService();
                FloatService.this.mIsShow = false;
            }
            if (FloatService.this.mIsFocusMode == 0 && FloatService.this.mIsKidMode == 0) {
                FloatWindowManagerAgent.INSTANCE.startFloatWindowService();
                FloatService.this.mIsShow = true;
            }
        }
    }

    private final void registerClipEvents() {
        Logger.INSTANCE.d(TAG, "registerClipEvents ");
        Object systemService = getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        this.mClipboardManager = (ClipboardManager) systemService;
        this.mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() { // from class: com.miui.floatwindow.FloatService$$ExternalSyntheticLambda0
            @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
            public final void onPrimaryClipChanged() {
                FloatService.registerClipEvents$lambda$3(this.f$0);
            }
        };
        ClipboardManager clipboardManager = this.mClipboardManager;
        Intrinsics.checkNotNull(clipboardManager);
        clipboardManager.addPrimaryClipChangedListener(this.mOnPrimaryClipChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerClipEvents$lambda$3(FloatService floatService) {
        ClipDescription description;
        Logger.INSTANCE.d(TAG, "onPrimaryClipChanged ");
        ClipboardManager clipboardManager = floatService.mClipboardManager;
        if (clipboardManager != null) {
            Intrinsics.checkNotNull(clipboardManager);
            if (clipboardManager.hasPrimaryClip()) {
                ClipboardManager clipboardManager2 = floatService.mClipboardManager;
                Intrinsics.checkNotNull(clipboardManager2);
                ClipData primaryClip = clipboardManager2.getPrimaryClip();
                CharSequence label = (primaryClip == null || (description = primaryClip.getDescription()) == null) ? null : description.getLabel();
                if (Intrinsics.areEqual(String.valueOf(label), IRichEditor.CLIP_LABEL_RICH_EDITOR)) {
                    Logger.INSTANCE.d(TAG, "onPrimaryClipChanged description" + ((Object) label) + " notes not support excerpt from itself !!!");
                    return;
                }
                if (primaryClip == null || primaryClip.getItemCount() <= 0) {
                    return;
                }
                ClipData.Item itemAt = primaryClip.getItemAt(0);
                if (itemAt.getText() != null) {
                    String string = new StringBuilder().append((Object) itemAt.getText()).toString();
                    Logger.INSTANCE.d(TAG, "registerClipEvents: clipChangeTime = " + floatService.mClipChangeTime + " currentTime = " + System.currentTimeMillis());
                    if (TextUtils.isEmpty(string)) {
                        return;
                    }
                    if ((!Intrinsics.areEqual(string, floatService.mContentString) || Math.abs(floatService.mClipChangeTime - System.currentTimeMillis()) >= 3000.0d) && Math.abs(floatService.mClipChangeTime - System.currentTimeMillis()) >= 100.0d) {
                        floatService.mContentString = string;
                        floatService.mClipChangeTime = System.currentTimeMillis();
                        ExcerptInfo excerptInfo = new ExcerptInfo(0, floatService.mContentString, System.currentTimeMillis());
                        Logger.INSTANCE.d(TAG, "clip info to excerptInfo");
                        FloatWindowManagerAgent.INSTANCE.excerptInfoChange(excerptInfo);
                    }
                }
            }
        }
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int i) {
        this.count = i;
    }

    public final long getLastTime() {
        return this.lastTime;
    }

    public final void setLastTime(long j) {
        this.lastTime = j;
    }

    public final long getLastImageId() {
        return this.lastImageId;
    }

    public final void setLastImageId(long j) {
        this.lastImageId = j;
    }

    private final ContentObserver getMPicObserver() {
        return (ContentObserver) this.mPicObserver.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.miui.floatwindow.FloatService$mPicObserver$2$1] */
    public static final FloatService$mPicObserver$2$1 mPicObserver_delegate$lambda$4(final FloatService floatService) {
        final Handler handler = floatService.getHandler();
        return new ContentObserver(handler) { // from class: com.miui.floatwindow.FloatService$mPicObserver$2$1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Logger.INSTANCE.i("FloatService", " mContentObserver.onChange  uri " + uri);
                if (uri == null || !MediaUtils.INSTANCE.isMediaUri(uri)) {
                    Logger.INSTANCE.e("FloatService", "uri is null or not media uri");
                    return;
                }
                try {
                    long id = ContentUris.parseId(uri);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (this.this$0.getLastImageId() == id) {
                        this.this$0.setCount(this.this$0.getCount() + 1);
                        if (this.this$0.getCount() > 1) {
                            this.this$0.notifyImgChange(id, jCurrentTimeMillis);
                            this.this$0.setCount(0);
                        }
                    }
                    this.this$0.setLastImageId(id);
                } catch (Exception e) {
                    Logger.INSTANCE.e("FloatService", "catch a exception:" + e);
                }
            }
        };
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1, reason: invalid class name */
    /* JADX INFO: compiled from: FloatService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1", f = "FloatService.kt", i = {}, l = {R2.attr.buttonInlineShrinkBackground}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $id;
        final /* synthetic */ long $nowTime;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, long j2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$id = j;
            this.$nowTime = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FloatService.this.new AnonymousClass1(this.$id, this.$nowTime, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/excerpt/ExcerptInfo;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1$1", f = "FloatService.kt", i = {0, 0, 0, 0}, l = {R2.attr.buttonBarStyle}, m = "invokeSuspend", n = {"$this$flow", Constants.JSON_FILTER_INFO, "$this$invokeSuspend_u24lambda_u240", "$i$a$-run-FloatService$notifyImgChange$1$1$1"}, s = {"L$0", "L$1", "L$2", "I$0"})
        static final class C00431 extends SuspendLambda implements Function2<FlowCollector<? super ExcerptInfo>, Continuation<? super Unit>, Object> {
            final /* synthetic */ long $id;
            final /* synthetic */ long $nowTime;
            int I$0;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            int label;
            final /* synthetic */ FloatService this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00431(FloatService floatService, long j, long j2, Continuation<? super C00431> continuation) {
                super(2, continuation);
                this.this$0 = floatService;
                this.$id = j;
                this.$nowTime = j2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00431 c00431 = new C00431(this.this$0, this.$id, this.$nowTime, continuation);
                c00431.L$0 = obj;
                return c00431;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super ExcerptInfo> flowCollector, Continuation<? super Unit> continuation) {
                return ((C00431) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:26:0x0143  */
            /* JADX WARN: Code duplicated, block: B:28:0x0147  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ExcerptInfo excerptInfo;
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MediaUtils mediaUtils = MediaUtils.INSTANCE;
                    Context applicationContext = this.this$0.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    PicInfo newPhotoImg = mediaUtils.getNewPhotoImg(applicationContext);
                    if (newPhotoImg != null) {
                        if (Intrinsics.areEqual(new StringBuilder().append(this.$id).toString(), newPhotoImg.getMediaId())) {
                            long j = this.$nowTime;
                            MediaIdInfo mediaIdInfo = this.this$0.mMediaIdInfo;
                            Intrinsics.checkNotNull(mediaIdInfo);
                            if (Math.abs(j - mediaIdInfo.getChangeTime()) <= 60000.0d) {
                                MediaIdInfo mediaIdInfo2 = this.this$0.mMediaIdInfo;
                                Intrinsics.checkNotNull(mediaIdInfo2);
                                if (Intrinsics.areEqual(mediaIdInfo2.getFilePath(), newPhotoImg.getFilePath())) {
                                    Logger.INSTANCE.i(FloatService.TAG, " not valid excerpt, " + (newPhotoImg != null ? newPhotoImg.getFilePath() : null));
                                }
                            }
                            Logger.INSTANCE.i(FloatService.TAG, StringUtils.SPACE + this.$id + " vs " + newPhotoImg.getMediaId() + ",path: " + newPhotoImg.getFilePath());
                            String filePath = newPhotoImg.getFilePath();
                            if (filePath != null && !StringsKt.contains$default((CharSequence) filePath, (CharSequence) "IMG_SHARE", false, 2, (Object) null)) {
                                this.this$0.mMediaIdInfo = new MediaIdInfo(this.$id, this.$nowTime, newPhotoImg.getFilePath());
                                String filePath2 = newPhotoImg.getFilePath();
                                Intrinsics.checkNotNull(filePath2);
                                ExcerptInfo excerptInfo2 = new ExcerptInfo(1, filePath2, System.currentTimeMillis());
                                this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                                this.L$1 = SpillingKt.nullOutSpilledVariable(newPhotoImg);
                                this.L$2 = excerptInfo2;
                                this.I$0 = 0;
                                this.label = 1;
                                if (flowCollector.emit(excerptInfo2, this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                excerptInfo = excerptInfo2;
                            } else {
                                Logger.INSTANCE.i(FloatService.TAG, " share pic " + newPhotoImg.getFilePath() + ",abort!!!");
                            }
                        } else {
                            Logger.INSTANCE.i(FloatService.TAG, " not valid excerpt, " + (newPhotoImg != null ? newPhotoImg.getFilePath() : null));
                        }
                    } else {
                        Logger.INSTANCE.i(FloatService.TAG, " not valid excerpt, " + (newPhotoImg != null ? newPhotoImg.getFilePath() : null));
                    }
                    return Unit.INSTANCE;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                excerptInfo = (ExcerptInfo) this.L$2;
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.i(FloatService.TAG, "emit new value " + excerptInfo);
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (FlowKt.onEach(FlowKt.onEmpty(FlowKt.m4091catch(FlowKt.onCompletion(FlowKt.flowOn(FlowKt.flow(new C00431(FloatService.this, this.$id, this.$nowTime, null)), Dispatchers.getIO()), new AnonymousClass2(null)), new AnonymousClass3(null)), new AnonymousClass4(null)), new AnonymousClass5(null)).collect(new AnonymousClass6(FloatService.this), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/excerpt/ExcerptInfo;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1$2", f = "FloatService.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super ExcerptInfo>, Throwable, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super ExcerptInfo> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                return new AnonymousClass2(continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.d(FloatService.TAG, "onCompletion");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/excerpt/ExcerptInfo;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1$3", f = "FloatService.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super ExcerptInfo>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super ExcerptInfo> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
                anonymousClass3.L$0 = th;
                return anonymousClass3.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Throwable th = (Throwable) this.L$0;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.e(FloatService.TAG, ExceptionsKt.stackTraceToString(th));
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/excerpt/ExcerptInfo;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1$4", f = "FloatService.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function2<FlowCollector<? super ExcerptInfo>, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super ExcerptInfo> flowCollector, Continuation<? super Unit> continuation) {
                return ((AnonymousClass4) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.i(FloatService.TAG, "onEmpty....");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$5, reason: invalid class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", "Lcom/miui/notes/excerpt/ExcerptInfo;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.FloatService$notifyImgChange$1$5", f = "FloatService.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass5 extends SuspendLambda implements Function2<ExcerptInfo, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            AnonymousClass5(Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                AnonymousClass5 anonymousClass5 = new AnonymousClass5(continuation);
                anonymousClass5.L$0 = obj;
                return anonymousClass5;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(ExcerptInfo excerptInfo, Continuation<? super Unit> continuation) {
                return ((AnonymousClass5) create(excerptInfo, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ExcerptInfo excerptInfo = (ExcerptInfo) this.L$0;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.i(FloatService.TAG, "onEach...collect new value " + excerptInfo + ".");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.FloatService$notifyImgChange$1$6, reason: invalid class name */
        /* JADX INFO: compiled from: FloatService.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        static final class AnonymousClass6<T> implements FlowCollector {
            final /* synthetic */ FloatService this$0;

            AnonymousClass6(FloatService floatService) {
                this.this$0 = floatService;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit((ExcerptInfo) obj, (Continuation<? super Unit>) continuation);
            }

            public final Object emit(final ExcerptInfo excerptInfo, Continuation<? super Unit> continuation) {
                Logger.INSTANCE.i(FloatService.TAG, "collect new value " + excerptInfo + " and notify excerptInfoChange with delay 150ms");
                this.this$0.getHandler().postDelayed(new Runnable() { // from class: com.miui.floatwindow.FloatService$notifyImgChange$1$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FloatService.AnonymousClass1.AnonymousClass6.emit$lambda$0(excerptInfo);
                    }
                }, 150L);
                return Unit.INSTANCE;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void emit$lambda$0(ExcerptInfo excerptInfo) {
                FloatWindowManagerAgent.INSTANCE.excerptInfoChange(excerptInfo);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyImgChange(long id, long nowTime) {
        Logger.INSTANCE.d(TAG, "notifyImgChange id: " + id + " with now time " + nowTime);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(id, nowTime, null), 3, null);
    }

    /* JADX INFO: compiled from: FloatService.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/floatwindow/FloatService$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "ACTION_STOP_FLOAT_SERVICE", "ACTION_STOP_EXCERPT_SERVICE", "ACTION_SCREEN_SHOT", "ACTION_FSG_STATE_CHANGE", "GROUP_NOTIFICATION_FLOAT", "ACTION_SHOW_QUICK_NOTE_UI", "FOCUS_MODE_STATUS", "KID_MODE_STATUS", "getLocaleFromConfig", "Ljava/util/Locale;", "configuration", "Landroid/content/res/Configuration;", "startExcerptService", "", "app", "Landroid/content/Context;", "invokeSource", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Locale getLocaleFromConfig(Configuration configuration) {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Locale locale = configuration.getLocales().get(0);
            Intrinsics.checkNotNullExpressionValue(locale, "get(...)");
            return locale;
        }

        @JvmStatic
        public final void startExcerptService(Context app, String invokeSource) {
            Intrinsics.checkNotNullParameter(app, "app");
            Intrinsics.checkNotNullParameter(invokeSource, "invokeSource");
            Logger.INSTANCE.d(FloatService.TAG, "startExcerptService. invokeSource:" + invokeSource);
            app.startForegroundService(new Intent(app, (Class<?>) FloatService.class));
        }
    }
}
