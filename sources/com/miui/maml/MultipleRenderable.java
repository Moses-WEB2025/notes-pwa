package com.miui.maml;

import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class MultipleRenderable implements RendererController.IRenderable {
    private static final String LOG_TAG = "MultipleRenderable";
    private int mActiveCount;
    private ArrayList<RenderableInfo> mList = new ArrayList<>();

    private static class RenderableInfo {
        public boolean paused;
        public WeakReference<RendererController.IRenderable> r;

        public RenderableInfo(RendererController.IRenderable iRenderable) {
            this.r = new WeakReference<>(iRenderable);
        }
    }

    public synchronized void add(RendererController.IRenderable iRenderable) {
        if (find(iRenderable) != null) {
            return;
        }
        Log.d(LOG_TAG, "add: " + iRenderable);
        this.mList.add(new RenderableInfo(iRenderable));
        this.mActiveCount++;
    }

    public synchronized void remove(RendererController.IRenderable iRenderable) {
        int size = this.mList.size();
        if (size == 0) {
            return;
        }
        for (int i = size - 1; i >= 0; i--) {
            RenderableInfo renderableInfo = this.mList.get(i);
            RendererController.IRenderable iRenderable2 = renderableInfo.r.get();
            if (iRenderable2 == null || iRenderable2 == iRenderable) {
                if (!renderableInfo.paused) {
                    this.mActiveCount--;
                }
                this.mList.remove(i);
                Log.d(LOG_TAG, "remove: " + iRenderable2);
            }
        }
    }

    public synchronized int pause(RendererController.IRenderable iRenderable) {
        return setPause(iRenderable, true);
    }

    public synchronized int resume(RendererController.IRenderable iRenderable) {
        return setPause(iRenderable, false);
    }

    private int setPause(RendererController.IRenderable iRenderable, boolean z) {
        Log.d(LOG_TAG, "setPause: " + z + StringUtils.SPACE + iRenderable);
        RenderableInfo renderableInfoFind = find(iRenderable);
        if (renderableInfoFind == null) {
            return this.mActiveCount;
        }
        if (renderableInfoFind.paused != z) {
            renderableInfoFind.paused = z;
            int i = this.mActiveCount;
            this.mActiveCount = z ? i - 1 : i + 1;
        }
        return this.mActiveCount;
    }

    private RenderableInfo find(RendererController.IRenderable iRenderable) {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            RenderableInfo renderableInfo = this.mList.get(i);
            if (renderableInfo.r.get() == iRenderable) {
                return renderableInfo;
            }
        }
        return null;
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public synchronized void doRender() {
        this.mActiveCount = 0;
        for (int size = this.mList.size() - 1; size >= 0; size--) {
            RenderableInfo renderableInfo = this.mList.get(size);
            if (!renderableInfo.paused) {
                RendererController.IRenderable iRenderable = renderableInfo.r.get();
                if (iRenderable != null) {
                    iRenderable.doRender();
                    this.mActiveCount++;
                } else {
                    this.mList.remove(size);
                }
            }
        }
    }

    public synchronized int size() {
        return this.mList.size();
    }
}
