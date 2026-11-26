package com.miui.maml;

import android.util.Log;
import android.view.MotionEvent;
import com.miui.maml.elements.FramerateController;
import com.miui.maml.util.HideSdkDependencyUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes3.dex */
public class RendererController implements FramerateTokenList.FramerateChangeListener {
    private static final String LOG_TAG = "RendererController";
    private static final int MAX_MSG_COUNT = 3;
    private float mCurFramerate;
    private FramerateTokenList mFramerateTokenList;
    private boolean mInited;
    private long mLastUpdateSystemTime;
    private Listener mListener;
    private LinkedList<MotionEvent> mMsgQueue;
    private boolean mNeedReset;
    private boolean mPendingRender;
    private boolean mSelfRender;
    private boolean mShouldUpdate;
    private ArrayList<FramerateController> mFramerateControllers = new ArrayList<>();
    private boolean mSelfPaused = true;
    private byte[] mLock = new byte[0];
    private long mFrameTime = Long.MAX_VALUE;
    private Object mMsgLock = new Object();
    private float mTouchX = -1.0f;
    private float mTouchY = -1.0f;
    private ArrayList<Runnable> mWriteRunnableQueue = new ArrayList<>();
    private ArrayList<Runnable> mReadRunnableQueue = new ArrayList<>();
    private Object mWriteRunnableQueueLock = new Object();

    public static abstract class EmptyListener implements Listener {
        @Override // com.miui.maml.RendererController.IRenderable
        public void doRender() {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void finish() {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void init() {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void onHover(MotionEvent motionEvent) {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void onTouch(MotionEvent motionEvent) {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void pause() {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void resume() {
        }

        @Override // com.miui.maml.RendererController.Listener
        public void tick(long j) {
        }
    }

    public interface IRenderable {
        void doRender();
    }

    public interface ISelfUpdateRenderable extends IRenderable {
        void forceUpdate();

        void triggerUpdate();
    }

    public interface Listener extends ISelfUpdateRenderable {
        void finish();

        void init();

        void onHover(MotionEvent motionEvent);

        void onTouch(MotionEvent motionEvent);

        void pause();

        void resume();

        void tick(long j);
    }

    public RendererController() {
        this.mFramerateTokenList = new FramerateTokenList();
        this.mFramerateTokenList = new FramerateTokenList(this);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public final FramerateTokenList.FramerateToken createToken(String str) {
        return this.mFramerateTokenList.createToken(str);
    }

    public final void removeToken(FramerateTokenList.FramerateToken framerateToken) {
        this.mFramerateTokenList.removeToken(framerateToken);
    }

    public final void requestUpdate() {
        this.mShouldUpdate = true;
        forceUpdate();
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0025 A[Catch: all -> 0x0050, LOOP:0: B:18:0x0025->B:20:0x002d, LOOP_START, TryCatch #0 {, blocks: (B:16:0x0021, B:18:0x0025, B:20:0x002d, B:21:0x0039), top: B:40:0x0021, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:20:0x002d A[Catch: all -> 0x0050, LOOP:0: B:18:0x0025->B:20:0x002d, LOOP_END, TryCatch #0 {, blocks: (B:16:0x0021, B:18:0x0025, B:20:0x002d, B:21:0x0039), top: B:40:0x0021, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x0021 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public void finish() {
        synchronized (this.mLock) {
            if (this.mInited) {
                Listener listener = this.mListener;
                if (listener != null) {
                    try {
                        listener.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, e.toString());
                    }
                    synchronized (this.mMsgLock) {
                        if (this.mMsgQueue != null) {
                            while (this.mMsgQueue.size() > 0) {
                                this.mMsgQueue.poll().recycle();
                            }
                        }
                    }
                    synchronized (this.mWriteRunnableQueueLock) {
                        this.mWriteRunnableQueue.clear();
                    }
                    this.mInited = false;
                    this.mFramerateTokenList.clear();
                    return;
                }
                synchronized (this.mMsgLock) {
                    if (this.mMsgQueue != null) {
                        while (this.mMsgQueue.size() > 0) {
                            this.mMsgQueue.poll().recycle();
                        }
                    }
                    synchronized (this.mWriteRunnableQueueLock) {
                        this.mWriteRunnableQueue.clear();
                        this.mInited = false;
                        this.mFramerateTokenList.clear();
                        return;
                    }
                }
                throw th;
            }
        }
    }

    public void init() {
        synchronized (this.mLock) {
            if (this.mInited) {
                return;
            }
            Listener listener = this.mListener;
            if (listener != null) {
                try {
                    listener.init();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.toString());
                }
            }
            this.mInited = true;
        }
    }

    public void addFramerateController(FramerateController framerateController) {
        if (this.mFramerateControllers.contains(framerateController)) {
            return;
        }
        this.mFramerateControllers.add(framerateController);
    }

    public void removeFramerateController(FramerateController framerateController) {
        this.mFramerateControllers.remove(framerateController);
    }

    public final long updateFramerate(long j) {
        int size = this.mFramerateControllers.size();
        long j2 = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            long jUpdateFramerate = this.mFramerateControllers.get(i).updateFramerate(j);
            if (jUpdateFramerate < j2) {
                j2 = jUpdateFramerate;
            }
        }
        float framerate = this.mFramerateTokenList.getFramerate();
        float f = this.mCurFramerate;
        if (f != framerate) {
            if (f >= 1.0f && framerate < 1.0f) {
                requestUpdate();
            }
            this.mCurFramerate = framerate;
            this.mFrameTime = framerate != 0.0f ? (long) (1000.0f / framerate) : Long.MAX_VALUE;
        }
        long j3 = this.mFrameTime;
        return j3 < j2 ? j3 : j2;
    }

    public final void doRender() {
        Listener listener = this.mListener;
        if (listener != null) {
            this.mPendingRender = true;
            listener.doRender();
        }
    }

    public final boolean pendingRender() {
        return this.mPendingRender;
    }

    public void selfPause() {
        if (this.mInited) {
            synchronized (this.mLock) {
                if (!this.mSelfPaused) {
                    this.mSelfPaused = true;
                    Listener listener = this.mListener;
                    if (listener != null) {
                        listener.pause();
                    }
                }
            }
            this.mPendingRender = false;
        }
    }

    public void selfResume() {
        if (this.mInited) {
            synchronized (this.mLock) {
                if (this.mSelfPaused) {
                    this.mSelfPaused = false;
                    Listener listener = this.mListener;
                    if (listener != null) {
                        listener.resume();
                    }
                }
            }
            RenderVsyncUpdater.getInstance().onResume();
        }
    }

    public final boolean isSelfPaused() {
        return this.mSelfPaused;
    }

    public final void doneRender() {
        this.mPendingRender = false;
        triggerUpdate();
    }

    public void triggerUpdate() {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.triggerUpdate();
        }
        RenderVsyncUpdater.getInstance().triggerUpdate();
    }

    public void forceUpdate() {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.forceUpdate();
        }
        RenderVsyncUpdater.getInstance().forceUpdate();
    }

    public long updateIfNeeded(long j) {
        long jUpdateFramerate = updateFramerate(j);
        long j2 = this.mFrameTime;
        long j3 = j2 < Long.MAX_VALUE ? j2 - (j - this.mLastUpdateSystemTime) : Long.MAX_VALUE;
        boolean zHasRunnable = hasRunnable();
        if (j3 <= 0 || this.mShouldUpdate || hasMessage() || zHasRunnable) {
            if (!this.mPendingRender || zHasRunnable) {
                runRunnables();
                MotionEvent message = getMessage();
                if (message != null) {
                    if (HideSdkDependencyUtils.MotionEvent_isTouchEvent(message)) {
                        onTouch(message);
                    } else {
                        onHover(message);
                    }
                }
                tick(j);
                if (!this.mSelfRender) {
                    doRender();
                }
                if (this.mShouldUpdate || hasMessage()) {
                    return 0L;
                }
            }
        } else if (j3 < jUpdateFramerate) {
            return j3;
        }
        return jUpdateFramerate;
    }

    public long update(long j) {
        long jUpdateFramerate = updateFramerate(j);
        boolean zHasRunnable = hasRunnable();
        if (!this.mPendingRender || zHasRunnable) {
            runRunnables();
            MotionEvent message = getMessage();
            if (message != null) {
                if (HideSdkDependencyUtils.MotionEvent_isTouchEvent(message)) {
                    onTouch(message);
                } else {
                    onHover(message);
                }
            }
            tick(j);
            if (!this.mSelfRender) {
                doRender();
            }
            if (this.mShouldUpdate || hasMessage()) {
                return 0L;
            }
        }
        return jUpdateFramerate;
    }

    public void tick(long j) {
        this.mShouldUpdate = false;
        Listener listener = this.mListener;
        if (listener != null) {
            try {
                listener.tick(j);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.toString());
            }
        }
        this.mLastUpdateSystemTime = j;
    }

    public void onTouch(MotionEvent motionEvent) {
        Listener listener;
        if (motionEvent == null || (listener = this.mListener) == null) {
            return;
        }
        try {
            listener.onTouch(motionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            Log.e(LOG_TAG, e2.toString());
        }
    }

    public void onHover(MotionEvent motionEvent) {
        Listener listener;
        if (motionEvent == null || (listener = this.mListener) == null) {
            return;
        }
        try {
            listener.onHover(motionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            Log.e(LOG_TAG, e2.toString());
        }
    }

    public final boolean hasMessage() {
        boolean z = false;
        if (this.mMsgQueue == null) {
            return false;
        }
        synchronized (this.mMsgLock) {
            LinkedList<MotionEvent> linkedList = this.mMsgQueue;
            if (linkedList != null && linkedList.size() > 0) {
                z = true;
            }
        }
        return z;
    }

    public final MotionEvent getMessage() {
        MotionEvent motionEventPoll = null;
        if (this.mMsgQueue == null) {
            return null;
        }
        synchronized (this.mMsgLock) {
            LinkedList<MotionEvent> linkedList = this.mMsgQueue;
            if (linkedList != null) {
                motionEventPoll = linkedList.poll();
            }
        }
        return motionEventPoll;
    }

    @Deprecated
    public void postMessage(MotionEvent motionEvent) {
        MotionEvent next;
        synchronized (this.mMsgLock) {
            if (this.mMsgQueue == null) {
                this.mMsgQueue = new LinkedList<>();
            }
            if (motionEvent.getActionMasked() != 2 || motionEvent.getX() != this.mTouchX || motionEvent.getY() != this.mTouchY) {
                this.mMsgQueue.add(motionEvent);
                this.mTouchX = motionEvent.getX();
                this.mTouchY = motionEvent.getY();
            }
            if (this.mMsgQueue.size() > 3) {
                Iterator<MotionEvent> it = this.mMsgQueue.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (next.getActionMasked() != 2);
                if (next != null) {
                    this.mMsgQueue.remove(next);
                    next.recycle();
                }
            }
        }
        forceUpdate();
    }

    public final boolean hasRunnable() {
        boolean z;
        synchronized (this.mWriteRunnableQueueLock) {
            z = !this.mWriteRunnableQueue.isEmpty();
        }
        return z;
    }

    public void postRunnable(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("postRunnable null");
        }
        synchronized (this.mWriteRunnableQueueLock) {
            if (!this.mWriteRunnableQueue.contains(runnable)) {
                this.mWriteRunnableQueue.add(runnable);
            }
        }
        requestUpdate();
    }

    public void postRunnableAtFrontOfQueue(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("postRunnable null");
        }
        synchronized (this.mWriteRunnableQueueLock) {
            if (!this.mWriteRunnableQueue.contains(runnable)) {
                this.mWriteRunnableQueue.add(0, runnable);
            }
        }
        requestUpdate();
    }

    private void runRunnables() {
        ArrayList<Runnable> arrayList;
        if (this.mNeedReset) {
            return;
        }
        synchronized (this.mWriteRunnableQueueLock) {
            arrayList = this.mWriteRunnableQueue;
            this.mWriteRunnableQueue = this.mReadRunnableQueue;
            this.mReadRunnableQueue = arrayList;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.mReadRunnableQueue.get(i).run();
        }
        this.mReadRunnableQueue.clear();
    }

    @Override // com.miui.maml.FramerateTokenList.FramerateChangeListener
    public void onFrameRateChage(float f, float f2) {
        if (f2 > 0.0f) {
            triggerUpdate();
        }
    }

    public final boolean hasInited() {
        return this.mInited;
    }

    public void setNeedReset(boolean z) {
        this.mNeedReset = z;
    }

    public void setSelfRender(boolean z) {
        this.mSelfRender = z;
    }
}
