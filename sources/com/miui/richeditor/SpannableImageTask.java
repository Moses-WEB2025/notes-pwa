package com.miui.richeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.BitmapTask;
import java.io.File;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class SpannableImageTask extends BitmapTask {
    private static final String TAG = "SpannableImageTask";
    private Context mContext;
    private int mHeight;
    private String mImageName;
    private int mScale;
    private int mWidth;

    public SpannableImageTask(Context context, String str, int i, int i2, int i3) {
        this.mContext = context.getApplicationContext();
        this.mImageName = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mScale = i3;
    }

    @Override // com.miui.notes.tool.BitmapTask
    protected Bitmap getBitmap() {
        return AttachmentUtils.getThumbnailFromAttachment(this.mContext, this.mWidth, this.mHeight, Uri.fromFile(new File(AttachmentUtils.getAttachmentPath(this.mContext, this.mImageName))), true);
    }

    @Override // com.miui.common.tool.async.Task
    public String getDescription() {
        return String.format(Locale.US, "%s:%s:%d:%d:%d", TAG, this.mImageName, Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mScale));
    }
}
