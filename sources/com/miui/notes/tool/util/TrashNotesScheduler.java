package com.miui.notes.tool.util;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.store.NoteStore;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TrashNotesScheduler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/tool/util/TrashNotesScheduler;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mBackgroundHandler", "Landroid/os/Handler;", "mRunnable", "Ljava/lang/Runnable;", "workThread", "Landroid/os/HandlerThread;", "schedule", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "delayMills", "", ToDoStat.CANCEL, "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TrashNotesScheduler {
    public static final TrashNotesScheduler INSTANCE = new TrashNotesScheduler();
    private static final String TAG = "TrashNotesScheduler";
    private static Handler mBackgroundHandler;
    private static Runnable mRunnable;
    private static HandlerThread workThread;

    private TrashNotesScheduler() {
    }

    public static /* synthetic */ void schedule$default(TrashNotesScheduler trashNotesScheduler, Context context, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS;
        }
        trashNotesScheduler.schedule(context, j);
    }

    public final void schedule(final Context context, long delayMills) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (workThread != null) {
            Handler handler = mBackgroundHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = workThread;
            Intrinsics.checkNotNull(handlerThread);
            handlerThread.quit();
        }
        HandlerThread handlerThread2 = new HandlerThread(TAG);
        workThread = handlerThread2;
        Intrinsics.checkNotNull(handlerThread2);
        handlerThread2.start();
        HandlerThread handlerThread3 = workThread;
        Intrinsics.checkNotNull(handlerThread3);
        mBackgroundHandler = new Handler(handlerThread3.getLooper());
        mRunnable = new Runnable() { // from class: com.miui.notes.tool.util.TrashNotesScheduler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TrashNotesScheduler.schedule$lambda$0(context);
            }
        };
        Handler handler2 = mBackgroundHandler;
        Intrinsics.checkNotNull(handler2);
        Runnable runnable = mRunnable;
        Intrinsics.checkNotNull(runnable);
        handler2.postDelayed(runnable, delayMills);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void schedule$lambda$0(Context context) {
        try {
            Logger.INSTANCE.i(TAG, "schedule->hideExpiredTrashNotes" + NoteStore.hideExpiredTrashNotes(context));
            INSTANCE.cancel();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in schedule ,exception:" + e);
        }
    }

    public final void cancel() {
        HandlerThread handlerThread = workThread;
        if (handlerThread != null) {
            Handler handler = mBackgroundHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            handlerThread.quitSafely();
            Logger.INSTANCE.i(TAG, "worker thread finished!!!");
        }
    }
}
