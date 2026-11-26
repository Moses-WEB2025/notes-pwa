package com.miui.common.base;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.DesktopRecommendInfo;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.NotificationUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.android.content.MiuiIntent;

/* JADX INFO: compiled from: BaseAlarmAlertActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000 H2\u00020\u0001:\u0002GHB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010*\u001a\u00020+H\u0014J\u000e\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u000fJ\n\u0010.\u001a\u0004\u0018\u00010\u0007H\u0014J\u0010\u0010/\u001a\u00020+2\u0006\u0010-\u001a\u00020\u000fH\u0014J\b\u00100\u001a\u00020+H\u0004J\b\u00101\u001a\u00020\u000fH\u0002J\b\u00102\u001a\u00020+H\u0002J\b\u00103\u001a\u00020+H\u0004J\b\u00104\u001a\u00020+H\u0004J\b\u00105\u001a\u00020+H\u0004J\u0006\u00106\u001a\u00020+J\b\u00107\u001a\u00020+H\u0002J\b\u00108\u001a\u00020+H\u0004J\b\u00109\u001a\u00020+H\u0002J\u000e\u0010:\u001a\u00020+2\u0006\u0010;\u001a\u00020<J\b\u0010=\u001a\u00020+H\u0002J\u0018\u0010>\u001a\u00020+2\u0006\u0010;\u001a\u00020<2\u0006\u0010?\u001a\u00020\u000fH\u0002J(\u0010@\u001a\u00060\u0013R\u00020\u00142\u0006\u0010A\u001a\u00020B2\f\u0010C\u001a\b\u0018\u00010\u0013R\u00020\u00142\u0006\u0010D\u001a\u00020<J\u001c\u0010E\u001a\u00020+2\u0006\u0010A\u001a\u00020B2\f\u0010C\u001a\b\u0018\u00010\u0013R\u00020\u0014J\b\u0010F\u001a\u00020+H\u0004R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\r8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\b\u0018\u00010\u0013R\u00020\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u001b\u001a\u00020\u001c8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u001c8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010!R\u0014\u0010$\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010!R\u0014\u0010&\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010!R\u0014\u0010(\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010!¨\u0006I"}, d2 = {"Lcom/miui/common/base/BaseAlarmAlertActivity;", "Lcom/miui/common/base/BaseActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mPlayer", "Landroid/media/MediaPlayer;", "mDialogFragment", "Lcom/miui/common/base/BaseAlarmAlertActivity$BaseDialogFragment;", "mBundles", "Ljava/util/ArrayList;", "Landroid/os/Bundle;", "Lkotlin/collections/ArrayList;", "mAudioManager", "Landroid/media/AudioManager;", "mIsOnAlarmFocus", "", "mAudioFocusChangeListener", "Landroid/media/AudioManager$OnAudioFocusChangeListener;", "mWakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "mHandler", "Lcom/miui/common/base/AlarmHandler;", "getMHandler", "()Lcom/miui/common/base/AlarmHandler;", "mHandler$delegate", "Lkotlin/Lazy;", "mAlarmReceiver", "Landroid/content/BroadcastReceiver;", "mPcAlarmStateReceiver", "notificationTag", "", "getNotificationTag", "()Ljava/lang/String;", "alarmChannelId", "getAlarmChannelId", "viewDataAction", "getViewDataAction", "extraDataKey", "getExtraDataKey", "extraDataId", "getExtraDataId", "onDestroy", "", "initialize", "needPlayMusic", "createDialogFragment", "showAlarmNotification", "cancelAlarmNotification", "dismissKeyguard", "playAlarmSound", "playMusic", "muteMusic", "resetMusic", "stopAlarmSound", "releasePlayer", "requestAlarmFocus", "abandonAlarmFocus", "registerAlarmLater", "date", "", "unregisterAlarmLater", "updateAlarmLater", ToDoStat.CANCEL, "acquireWakeLock", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/app/Activity;", "wakeLock", "timeout", "releaseWakeLock", "setTranslucentSystemBars", "BaseDialogFragment", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseAlarmAlertActivity extends BaseActivity {
    public static final int ACTION_CODE_MUTE = 2;
    protected static final String ALARM_ACTION_REPEAT = "com.miui.notes.ui.AlarmAlertActivity.REPEAT";
    protected static final String ALARM_ACTION_SKIP = "com.miui.notes.ui.AlarmAlertActivity.SKIP";
    protected static final String ALARM_ACTION_VIEW = "com.miui.notes.ui.AlarmAlertActivity.VIEW";
    public static final String DIALOG_FRAGMENT_TAG = "dialog";
    public static final String EXTRA_FORMATTED_BUNDLES = "extra_formatted_bundles";
    public static final String EXTRA_FORMATTED_SNIPPET = "extra_formatted_snippet";
    protected static final int PLAY_DURATION = 300000;
    public static final int TURN_SCREEN_ON_FLAGS = 2097281;
    public static final int WAKE_LOCK_TIMEOUT = 305000;
    protected AudioManager mAudioManager;
    public ArrayList<Bundle> mBundles;
    private BaseDialogFragment mDialogFragment;
    private boolean mIsOnAlarmFocus;
    private MediaPlayer mPlayer;
    protected PowerManager.WakeLock mWakeLock;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static String TAG = "BaseAlarmAlertActivity";
    private static final int[] MUTE_DURATIONS = {300000, 600000, 1200000};
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.miui.common.base.BaseAlarmAlertActivity$$ExternalSyntheticLambda0
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public final void onAudioFocusChange(int i) {
            BaseAlarmAlertActivity.mAudioFocusChangeListener$lambda$0(this.f$0, i);
        }
    };

    /* JADX INFO: renamed from: mHandler$delegate, reason: from kotlin metadata */
    private final Lazy mHandler = LazyKt.lazy(new Function0() { // from class: com.miui.common.base.BaseAlarmAlertActivity$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BaseAlarmAlertActivity.mHandler_delegate$lambda$1(this.f$0);
        }
    });
    protected BroadcastReceiver mAlarmReceiver = new BroadcastReceiver() { // from class: com.miui.common.base.BaseAlarmAlertActivity$mAlarmReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
            BaseAlarmAlertActivity baseAlarmAlertActivity = this.this$0;
            Logger.INSTANCE.i(BaseAlarmAlertActivity.TAG, "finish alarm for skip action received");
            baseAlarmAlertActivity.muteMusic();
            baseAlarmAlertActivity.cancelAlarmNotification();
            BaseAlarmAlertActivity baseAlarmAlertActivity2 = this.this$0;
            baseAlarmAlertActivity2.releaseWakeLock(baseAlarmAlertActivity2, baseAlarmAlertActivity2.mWakeLock);
            this.this$0.finish();
        }
    };
    protected BroadcastReceiver mPcAlarmStateReceiver = new BaseAlarmAlertActivity$mPcAlarmStateReceiver$1(this);

    @JvmStatic
    public static final void scheduleAlarmExact(AlarmManager alarmManager, long j, PendingIntent pendingIntent) {
        INSTANCE.scheduleAlarmExact(alarmManager, j, pendingIntent);
    }

    protected BaseDialogFragment createDialogFragment() {
        return null;
    }

    public abstract String getAlarmChannelId();

    public abstract String getExtraDataId();

    public abstract String getExtraDataKey();

    public abstract String getNotificationTag();

    public abstract String getViewDataAction();

    protected void showAlarmNotification(boolean needPlayMusic) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mAudioFocusChangeListener$lambda$0(BaseAlarmAlertActivity baseAlarmAlertActivity, int i) {
        MediaPlayer mediaPlayer;
        if (i != -2 || (mediaPlayer = baseAlarmAlertActivity.mPlayer) == null) {
            return;
        }
        Intrinsics.checkNotNull(mediaPlayer);
        if (mediaPlayer.isPlaying()) {
            baseAlarmAlertActivity.stopAlarmSound();
        }
    }

    private final AlarmHandler getMHandler() {
        return (AlarmHandler) this.mHandler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AlarmHandler mHandler_delegate$lambda$1(BaseAlarmAlertActivity baseAlarmAlertActivity) {
        WeakReference weakReference = new WeakReference(baseAlarmAlertActivity);
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        return new AlarmHandler(weakReference, mainLooper);
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        BaseDialogFragment baseDialogFragment = this.mDialogFragment;
        if (baseDialogFragment != null) {
            Intrinsics.checkNotNull(baseDialogFragment);
            if (baseDialogFragment.isAdded()) {
                BaseDialogFragment baseDialogFragment2 = this.mDialogFragment;
                Intrinsics.checkNotNull(baseDialogFragment2);
                baseDialogFragment2.dismissAllowingStateLoss();
            }
            this.mDialogFragment = null;
        }
        getMHandler().removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public final void initialize(boolean needPlayMusic) {
        Logger.INSTANCE.d(TAG, "initialize");
        ArrayList<Bundle> arrayList = this.mBundles;
        Intrinsics.checkNotNull(arrayList);
        if (arrayList.isEmpty()) {
            Logger.INSTANCE.w(TAG, "mIntents is empty");
            muteMusic();
            releaseWakeLock(this, this.mWakeLock);
            cancelAlarmNotification();
            finish();
            return;
        }
        BaseDialogFragment baseDialogFragment = this.mDialogFragment;
        if (baseDialogFragment != null) {
            Intrinsics.checkNotNull(baseDialogFragment);
            if (baseDialogFragment.isAdded()) {
                BaseDialogFragment baseDialogFragment2 = this.mDialogFragment;
                Intrinsics.checkNotNull(baseDialogFragment2);
                baseDialogFragment2.dismissAllowingStateLoss();
            }
            this.mDialogFragment = null;
        }
        BaseDialogFragment baseDialogFragmentCreateDialogFragment = createDialogFragment();
        this.mDialogFragment = baseDialogFragmentCreateDialogFragment;
        Intrinsics.checkNotNull(baseDialogFragmentCreateDialogFragment);
        baseDialogFragmentCreateDialogFragment.setBundles(this.mBundles);
        BaseDialogFragment baseDialogFragment3 = this.mDialogFragment;
        Intrinsics.checkNotNull(baseDialogFragment3);
        if (!baseDialogFragment3.isAdded()) {
            BaseDialogFragment baseDialogFragment4 = this.mDialogFragment;
            Intrinsics.checkNotNull(baseDialogFragment4);
            baseDialogFragment4.show(getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
        }
        showAlarmNotification(needPlayMusic);
    }

    protected final void cancelAlarmNotification() {
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.cancel(getNotificationTag(), 0);
        notificationManager.deleteNotificationChannel(getAlarmChannelId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean dismissKeyguard() {
        Object systemService = getSystemService("keyguard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        KeyguardManager keyguardManager = (KeyguardManager) systemService;
        if (keyguardManager.isKeyguardLocked()) {
            getWindow().addFlags(4194304);
        }
        return keyguardManager.isKeyguardLocked();
    }

    private final void playAlarmSound() {
        final Uri validRingtoneUri;
        Logger.INSTANCE.i(TAG, "playAlarmSound,acquire wake lock");
        this.mWakeLock = acquireWakeLock(this, this.mWakeLock, 305000L);
        try {
            if (UIUtils.isMiuiXISdkSupported() && (!PreferenceUtils.getRemindType(this) || !PermissionUtils.isNotificationPermissionGranted(this))) {
                validRingtoneUri = NotificationUtils.getDefaultSoundActualUri(this);
            } else {
                validRingtoneUri = RingtoneManager.getValidRingtoneUri(this);
            }
            if (validRingtoneUri == null) {
                Logger.INSTANCE.i(TAG, "url is null.");
                return;
            }
            if (this.mPlayer != null) {
                releasePlayer();
            }
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mPlayer = mediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer);
            mediaPlayer.setAudioStreamType(PreferenceUtils.getRemindType(this) ? 4 : 5);
            MediaPlayer mediaPlayer2 = this.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer2);
            mediaPlayer2.setDataSource(this, validRingtoneUri);
            boolean z = !UIUtils.isMiuiXISdkSupported() || (PreferenceUtils.getRemindType(this) && PermissionUtils.isNotificationPermissionGranted(this));
            MediaPlayer mediaPlayer3 = this.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer3);
            mediaPlayer3.setLooping(z);
            MediaPlayer mediaPlayer4 = this.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer4);
            mediaPlayer4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.miui.common.base.BaseAlarmAlertActivity$$ExternalSyntheticLambda3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer5) {
                    BaseAlarmAlertActivity.playAlarmSound$lambda$2(this.f$0, validRingtoneUri, mediaPlayer5);
                }
            });
            MediaPlayer mediaPlayer5 = this.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer5);
            mediaPlayer5.prepareAsync();
            Logger.INSTANCE.i(TAG, "playAlarmSound: url=" + validRingtoneUri + ",prepared!");
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "playAlarmSound failed." + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playAlarmSound$lambda$2(BaseAlarmAlertActivity baseAlarmAlertActivity, Uri uri, MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            baseAlarmAlertActivity.requestAlarmFocus();
            try {
                mediaPlayer.start();
                Logger.INSTANCE.i(TAG, "playAlarmSound: url=" + uri + ",started!");
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "playAlarmSound error: " + e);
            }
        }
    }

    protected final void playMusic() {
        Logger.INSTANCE.i(TAG, "playMusic");
        playAlarmSound();
        getMHandler().sendEmptyMessageDelayed(2, DesktopRecommendInfo.DEFAULT_CACHE_TIME);
    }

    protected final void muteMusic() {
        Logger.INSTANCE.i(TAG, "muteMusic");
        unregisterAlarmLater();
        getMHandler().resetCounter();
        getMHandler().removeMessages(2);
        stopAlarmSound();
    }

    protected final void resetMusic() {
        Logger.INSTANCE.i(TAG, "resetMusic");
        if (this.mPlayer == null) {
            Logger.INSTANCE.i(TAG, "resetMusic: restart music");
            muteMusic();
            playMusic();
        } else {
            Logger.INSTANCE.i(TAG, "resetMusic: clear and resend message");
            unregisterAlarmLater();
            getMHandler().resetCounter();
            getMHandler().removeMessages(2);
            getMHandler().sendEmptyMessageDelayed(2, DesktopRecommendInfo.DEFAULT_CACHE_TIME);
        }
    }

    public final void stopAlarmSound() {
        Logger.INSTANCE.i(TAG, "stopAlarmSound");
        releasePlayer();
        abandonAlarmFocus();
    }

    private final void releasePlayer() {
        Logger.INSTANCE.i(TAG, "releasePlayer");
        try {
            Intrinsics.checkNotNull(Schedulers.single().scheduleDirect(new Runnable() { // from class: com.miui.common.base.BaseAlarmAlertActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAlarmAlertActivity.releasePlayer$lambda$3(this.f$0);
                }
            }));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in releasePlayer " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void releasePlayer$lambda$3(BaseAlarmAlertActivity baseAlarmAlertActivity) {
        MediaPlayer mediaPlayer = baseAlarmAlertActivity.mPlayer;
        if (mediaPlayer != null) {
            Intrinsics.checkNotNull(mediaPlayer);
            mediaPlayer.stop();
            MediaPlayer mediaPlayer2 = baseAlarmAlertActivity.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer2);
            mediaPlayer2.reset();
            MediaPlayer mediaPlayer3 = baseAlarmAlertActivity.mPlayer;
            Intrinsics.checkNotNull(mediaPlayer3);
            mediaPlayer3.release();
            Logger.INSTANCE.i(TAG, "releasePlayer finished");
            baseAlarmAlertActivity.mPlayer = null;
        }
    }

    protected final void requestAlarmFocus() {
        if (PreferenceUtils.getRemindType(this)) {
            AudioManager audioManager = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager);
            audioManager.requestAudioFocus(this.mAudioFocusChangeListener, 4, 2);
        } else {
            AudioManager audioManager2 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager2);
            audioManager2.requestAudioFocus(this.mAudioFocusChangeListener, 5, 2);
        }
        this.mIsOnAlarmFocus = true;
    }

    private final void abandonAlarmFocus() {
        if (this.mIsOnAlarmFocus) {
            AudioManager audioManager = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager);
            audioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
            this.mIsOnAlarmFocus = false;
        }
    }

    public final void registerAlarmLater(long date) {
        Logger.INSTANCE.i(TAG, "registerAlarmLater: date=" + date);
        updateAlarmLater(date, false);
    }

    private final void unregisterAlarmLater() {
        Logger.INSTANCE.i(TAG, "unregisterAlarmLater");
        updateAlarmLater(0L, true);
    }

    private final void updateAlarmLater(long date, boolean cancel) {
        Intent intent = new Intent(ALARM_ACTION_REPEAT);
        BaseAlarmAlertActivity baseAlarmAlertActivity = this;
        intent.setClass(baseAlarmAlertActivity, getClass());
        PendingIntent activity = PendingIntent.getActivity(baseAlarmAlertActivity, 0, intent, 201326592);
        Object systemService = getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
        AlarmManager alarmManager = (AlarmManager) systemService;
        if (cancel) {
            alarmManager.cancel(activity);
            return;
        }
        Companion companion = INSTANCE;
        Intrinsics.checkNotNull(activity);
        companion.scheduleAlarmExact(alarmManager, date, activity);
    }

    public final PowerManager.WakeLock acquireWakeLock(Activity context, PowerManager.WakeLock wakeLock, long timeout) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.i(TAG, "acquireWakeLock");
        context.getWindow().addFlags(TURN_SCREEN_ON_FLAGS);
        if (wakeLock == null) {
            Object systemService = context.getSystemService("power");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            wakeLock = ((PowerManager) systemService).newWakeLock(268435466, TAG);
        }
        Intrinsics.checkNotNull(wakeLock);
        wakeLock.setReferenceCounted(false);
        wakeLock.acquire(timeout);
        return wakeLock;
    }

    public final void releaseWakeLock(Activity context, PowerManager.WakeLock wakeLock) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.i(TAG, "releaseWakeLock");
        context.getWindow().clearFlags(TURN_SCREEN_ON_FLAGS);
        if (wakeLock == null || !wakeLock.isHeld()) {
            return;
        }
        wakeLock.release();
    }

    /* JADX INFO: compiled from: BaseAlarmAlertActivity.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J$\u0010\u0010\u001a\u00020\b2\u001a\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012j\n\u0012\u0004\u0012\u00020\u0013\u0018\u0001`\u0014H&J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0013H\u0004R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/common/base/BaseAlarmAlertActivity$BaseDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "Landroid/content/DialogInterface$OnClickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "activity", "Lcom/miui/common/base/BaseAlarmAlertActivity;", "onAttach", "", "Landroid/app/Activity;", "onClick", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/content/DialogInterface;", "which", "", "onDismiss", "setBundles", "bundles", "Ljava/util/ArrayList;", "Landroid/os/Bundle;", "Lkotlin/collections/ArrayList;", "openData", "bundle", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class BaseDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
        protected BaseAlarmAlertActivity activity;

        public abstract void setBundles(ArrayList<Bundle> bundles);

        public BaseDialogFragment() {
            setCancelable(false);
        }

        @Override // androidx.fragment.app.Fragment
        public void onAttach(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            super.onAttach(activity);
            this.activity = (BaseAlarmAlertActivity) activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            Intrinsics.checkNotNullParameter(dialog, "dialog");
            BaseAlarmAlertActivity baseAlarmAlertActivity = this.activity;
            if (baseAlarmAlertActivity != null) {
                baseAlarmAlertActivity.muteMusic();
            }
        }

        @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialog) {
            ArrayList<Bundle> arrayList;
            Intrinsics.checkNotNullParameter(dialog, "dialog");
            Logger.INSTANCE.d(BaseAlarmAlertActivity.TAG, "onDismiss");
            super.onDismiss(dialog);
            BaseAlarmAlertActivity baseAlarmAlertActivity = this.activity;
            if (baseAlarmAlertActivity == null || (arrayList = baseAlarmAlertActivity.mBundles) == null || !arrayList.isEmpty()) {
                return;
            }
            Logger.INSTANCE.d(BaseAlarmAlertActivity.TAG, "finish Activity");
            BaseAlarmAlertActivity baseAlarmAlertActivity2 = this.activity;
            if (baseAlarmAlertActivity2 != null) {
                baseAlarmAlertActivity2.cancelAlarmNotification();
            }
            BaseAlarmAlertActivity baseAlarmAlertActivity3 = this.activity;
            if (baseAlarmAlertActivity3 != null) {
                baseAlarmAlertActivity3.finish();
            }
        }

        protected final void openData(Bundle bundle) {
            ArrayList<Bundle> arrayList;
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Logger.INSTANCE.d(BaseAlarmAlertActivity.TAG, "openData");
            if (isAdded()) {
                Intent intent = new Intent(getActivity(), (Class<?>) NotesListActivity.class);
                BaseAlarmAlertActivity baseAlarmAlertActivity = this.activity;
                intent.putExtra(MiuiIntent.EXTRA_START_ACTIVITY_WHEN_LOCKED, baseAlarmAlertActivity != null ? Boolean.valueOf(baseAlarmAlertActivity.dismissKeyguard()) : null);
                intent.addFlags(603979776);
                BaseAlarmAlertActivity baseAlarmAlertActivity2 = this.activity;
                intent.setAction(baseAlarmAlertActivity2 != null ? baseAlarmAlertActivity2.getViewDataAction() : null);
                BaseAlarmAlertActivity baseAlarmAlertActivity3 = this.activity;
                String extraDataKey = baseAlarmAlertActivity3 != null ? baseAlarmAlertActivity3.getExtraDataKey() : null;
                BaseAlarmAlertActivity baseAlarmAlertActivity4 = this.activity;
                intent.putExtra(extraDataKey, bundle.getLong(baseAlarmAlertActivity4 != null ? baseAlarmAlertActivity4.getExtraDataId() : null));
                BaseAlarmAlertActivity baseAlarmAlertActivity5 = this.activity;
                if (baseAlarmAlertActivity5 != null && (arrayList = baseAlarmAlertActivity5.mBundles) != null) {
                    arrayList.remove(bundle);
                }
                startActivity(intent);
            }
        }
    }

    protected final void setTranslucentSystemBars() {
        if (getWindow() != null) {
            getWindow().addFlags(67108864);
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
    }

    /* JADX INFO: compiled from: BaseAlarmAlertActivity.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0084T¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/miui/common/base/BaseAlarmAlertActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "TURN_SCREEN_ON_FLAGS", "", "ALARM_ACTION_VIEW", "ALARM_ACTION_SKIP", "ALARM_ACTION_REPEAT", "PLAY_DURATION", "MUTE_DURATIONS", "", "getMUTE_DURATIONS", "()[I", "WAKE_LOCK_TIMEOUT", "ACTION_CODE_MUTE", "DIALOG_FRAGMENT_TAG", "EXTRA_FORMATTED_SNIPPET", "EXTRA_FORMATTED_BUNDLES", "scheduleAlarmExact", "", "am", "Landroid/app/AlarmManager;", "date", "", "pendingIntent", "Landroid/app/PendingIntent;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int[] getMUTE_DURATIONS() {
            return BaseAlarmAlertActivity.MUTE_DURATIONS;
        }

        @JvmStatic
        public final void scheduleAlarmExact(AlarmManager am, long date, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(am, "am");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            try {
                am.setAlarmClock(new AlarmManager.AlarmClockInfo(date, pendingIntent), pendingIntent);
            } catch (Exception e) {
                Logger.INSTANCE.e(BaseAlarmAlertActivity.TAG, "Fail to schedule alarm" + e);
            }
        }
    }
}
