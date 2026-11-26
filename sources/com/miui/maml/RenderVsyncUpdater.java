package com.miui.maml;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import androidx.collection.ArraySet;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class RenderVsyncUpdater {
    private static final String LOG_TAG = "RenderVsyncUpdater";
    private FrameDisplayEventReceiver mDisplayEventReceiver;
    private Handler mHandler;
    private boolean mPaused;
    private ArraySet<WeakReference<RendererController>> mRendererControllerList;
    private Runnable mScheduleFrame;
    private boolean mStopRefresh;
    private int mSyncInterval;
    private long mVsyncLeft;

    private static class RenderVsyncUpdaterHolder {
        public static final RenderVsyncUpdater INSTANCE = new RenderVsyncUpdater();

        private RenderVsyncUpdaterHolder() {
        }
    }

    public static RenderVsyncUpdater getInstance() {
        return RenderVsyncUpdaterHolder.INSTANCE;
    }

    private RenderVsyncUpdater() {
        Looper mainLooper;
        this.mRendererControllerList = new ArraySet<>();
        this.mSyncInterval = 16;
        this.mScheduleFrame = new Runnable() { // from class: com.miui.maml.RenderVsyncUpdater.1
            @Override // java.lang.Runnable
            public void run() {
                RenderVsyncUpdater.this.scheduleFrame();
            }
        };
        if ("android.ui".equals(Thread.currentThread().getName())) {
            mainLooper = Looper.myLooper();
        } else {
            mainLooper = Looper.getMainLooper();
        }
        this.mHandler = new Handler(mainLooper);
        this.mDisplayEventReceiver = new FrameDisplayEventReceiver(mainLooper);
    }

    public void triggerUpdate() {
        doRunUpdater();
    }

    public void forceUpdate() {
        this.mVsyncLeft = 0L;
        doRunUpdater();
    }

    private void doRunUpdater() {
        if (this.mVsyncLeft > 0) {
            this.mDisplayEventReceiver.scheduleVsync();
        } else {
            if (this.mHandler.hasCallbacks(this.mScheduleFrame)) {
                return;
            }
            this.mHandler.post(this.mScheduleFrame);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleFrame() {
        long j;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mRendererControllerList) {
            int size = this.mRendererControllerList.size();
            boolean z = true;
            j = Long.MAX_VALUE;
            for (int i = size - 1; i >= 0; i--) {
                RendererController rendererController = this.mRendererControllerList.valueAt(i).get();
                if (rendererController == null) {
                    this.mRendererControllerList.removeAt(i);
                } else if (!rendererController.isSelfPaused() || rendererController.hasRunnable()) {
                    if (!rendererController.hasInited()) {
                        rendererController.init();
                    }
                    long jUpdateIfNeeded = rendererController.updateIfNeeded(jElapsedRealtime);
                    if (jUpdateIfNeeded < j) {
                        z = false;
                        j = jUpdateIfNeeded;
                    } else {
                        z = false;
                    }
                }
            }
            if (size == 0 || z) {
                this.mPaused = true;
                Log.i(LOG_TAG, "All controllers paused.");
            } else {
                this.mPaused = false;
            }
        }
        if (j == Long.MAX_VALUE) {
            this.mStopRefresh = true;
        } else {
            this.mStopRefresh = false;
        }
        if (this.mStopRefresh || this.mPaused || j <= 0) {
            return;
        }
        long j2 = j / ((long) this.mSyncInterval);
        this.mVsyncLeft = j2;
        if (j2 > 0) {
            this.mVsyncLeft = j2 - 1;
        }
        this.mDisplayEventReceiver.scheduleVsync();
    }

    public void addRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            int size = this.mRendererControllerList.size();
            for (int i = 0; i < size; i++) {
                if (this.mRendererControllerList.valueAt(i).get() == rendererController) {
                    return;
                }
            }
            this.mRendererControllerList.add(new WeakReference<>(rendererController));
        }
    }

    public void removeRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            for (int size = this.mRendererControllerList.size() - 1; size >= 0; size--) {
                RendererController rendererController2 = this.mRendererControllerList.valueAt(size).get();
                if (rendererController2 != null && rendererController2 != rendererController) {
                }
                this.mRendererControllerList.removeAt(size);
                break;
            }
        }
    }

    public void onResume() {
        this.mPaused = false;
        this.mStopRefresh = false;
        forceUpdate();
    }

    public void setSyncInterval(int i) {
        this.mSyncInterval = i;
    }

    private final class FrameDisplayEventReceiver extends MamlDisplayEventReceiver implements Runnable {
        public FrameDisplayEventReceiver(Looper looper) {
            super(looper);
        }

        @Override // com.miui.maml.MamlDisplayEventReceiver
        public void onVsync(long j) {
            RenderVsyncUpdater.this.mVsyncLeft--;
            RenderVsyncUpdater.this.mHandler.sendMessageAtTime(Message.obtain(RenderVsyncUpdater.this.mHandler, this), j / 1000000);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (RenderVsyncUpdater.this.mVsyncLeft <= 0) {
                RenderVsyncUpdater.this.scheduleFrame();
            } else {
                if (RenderVsyncUpdater.this.mPaused || RenderVsyncUpdater.this.mStopRefresh) {
                    return;
                }
                scheduleVsync();
            }
        }
    }
}
