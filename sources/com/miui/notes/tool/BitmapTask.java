package com.miui.notes.tool;

import android.graphics.Bitmap;
import com.miui.common.tool.async.Task;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BitmapTask extends Task<Bitmap> {
    private static final String TAG = "BitmapTask";

    protected abstract Bitmap getBitmap();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.miui.common.tool.async.Task
    public Bitmap doLoad() throws Exception {
        String description = getDescription();
        if (description == null) {
            LogUtils.d(TAG, "success to load image is null");
            return null;
        }
        Bitmap bitmap = BitmapCacheManager.getInstance().get(description);
        if (bitmap != null) {
            return bitmap;
        }
        Bitmap bitmap2 = getBitmap();
        if (bitmap2 == null) {
            Logger.INSTANCE.w(TAG, "failed to load image: " + description);
            return bitmap2;
        }
        BitmapCacheManager.getInstance().put(description, bitmap2);
        LogUtils.d(TAG, "success to load image: " + description);
        return bitmap2;
    }

    public void commit() {
        TaskManagerUtils.getTaskManager().add(this);
    }
}
