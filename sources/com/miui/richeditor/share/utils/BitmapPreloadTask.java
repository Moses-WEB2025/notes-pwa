package com.miui.richeditor.share.utils;

import android.graphics.Bitmap;
import com.miui.common.tool.async.TaskManager;
import com.miui.notes.tool.BitmapTask;
import com.miui.richeditor.share.render.Render;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class BitmapPreloadTask extends BitmapTask {
    private WeakReference<Render.BitmapLoadListener> mListener;
    private WeakReference<ImageElementPreloader> mPreloader;

    public BitmapPreloadTask(ImageElementPreloader imageElementPreloader, Render.BitmapLoadListener bitmapLoadListener) {
        this.mPreloader = new WeakReference<>(imageElementPreloader);
        this.mListener = new WeakReference<>(bitmapLoadListener);
    }

    @Override // com.miui.notes.tool.BitmapTask
    protected Bitmap getBitmap() {
        if (this.mPreloader.get() != null) {
            return this.mPreloader.get().createBitmap();
        }
        return null;
    }

    @Override // com.miui.common.tool.async.Task
    public String getDescription() {
        return this.mPreloader.get() != null ? "render:" + this.mPreloader.get().getCacheKey() : "render:";
    }

    @Override // com.miui.common.tool.async.Task
    public void onResult(TaskManager taskManager, Bitmap bitmap) {
        if (this.mPreloader.get() != null) {
            this.mPreloader.get().cancelTask();
            this.mPreloader.get().setBitmap(bitmap);
            WeakReference<Render.BitmapLoadListener> weakReference = this.mListener;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.mListener.get().onLoadFinish(this.mPreloader.get().getElement(), bitmap);
        }
    }

    @Override // com.miui.common.tool.async.Task
    public void onException(TaskManager taskManager, Exception exc) {
        WeakReference<ImageElementPreloader> weakReference = this.mPreloader;
        if (weakReference != null) {
            weakReference.get().cancelTask();
        }
    }
}
