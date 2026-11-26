package androidx.profileinstaller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class ProfileInstallerInitializer implements Initializer<Result> {
    private static final int DELAY_MS = 5000;

    public static class Result {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    public Result create(Context context) {
        final Context applicationContext = context.getApplicationContext();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                this.f$0.m143x451635a(applicationContext, j);
            }
        });
        return new Result();
    }

    /* JADX INFO: renamed from: lambda$create$0$androidx-profileinstaller-ProfileInstallerInitializer, reason: not valid java name */
    /* synthetic */ void m143x451635a(Context context, long j) {
        installAfterDelay(context);
    }

    void installAfterDelay(final Context context) {
        Handler28Impl.createAsync(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ProfileInstallerInitializer.writeInBackground(context);
            }
        }, new Random().nextInt(Math.max(1000, 1)) + 5000);
    }

    @Override // androidx.startup.Initializer
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeInBackground(final Context context) {
        new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()).execute(new Runnable() { // from class: androidx.profileinstaller.ProfileInstallerInitializer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ProfileInstaller.writeProfile(context);
            }
        });
    }

    private static class Handler28Impl {
        private Handler28Impl() {
        }

        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }
    }
}
