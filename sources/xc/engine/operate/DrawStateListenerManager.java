package xc.engine.operate;

import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes6.dex */
public class DrawStateListenerManager {
    private final List<IDrawStateListener> mDrawerStateChangeListeners = new CopyOnWriteArrayList();

    public void addDrawerStateChangeListener(IDrawStateListener iDrawStateListener) {
        if (this.mDrawerStateChangeListeners.contains(iDrawStateListener)) {
            return;
        }
        this.mDrawerStateChangeListeners.add(iDrawStateListener);
    }

    public void removeDrawerStateChangeListener(IDrawStateListener iDrawStateListener) {
        if (this.mDrawerStateChangeListeners.contains(iDrawStateListener)) {
            this.mDrawerStateChangeListeners.remove(iDrawStateListener);
        }
    }

    public void onCanvasVisible(boolean z) {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanvasVisible(z);
        }
    }

    public void onRenderByCanvas(Canvas canvas) {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onRenderByCanvas(canvas);
        }
    }

    public void onCanvasRelease() {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanvasRelease();
        }
    }

    public void onCanvasScale(Matrix matrix) {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanvasScale(matrix);
        }
    }

    public void onCanvasShowSelectBitmap() {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanvasShowSelectBitmap();
        }
    }

    public void onCanvasScaleModeChange(boolean z) {
        Iterator<IDrawStateListener> it = this.mDrawerStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanvasScaleModeChange(z);
        }
    }
}
