package com.miui.notes.thumbnail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.async.TaskManager;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.ThumbnailCache;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.BitmapCacheManager;
import com.miui.notes.tool.BitmapDecoder;
import com.miui.notes.tool.BitmapTask;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWritePathUtils;
import com.miui.richeditor.schema.NoteSchema;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class ThumbnailLoader {
    private Context mContext;
    private int mHeight;
    private NoteModel mNoteModel;
    private String mType = "list";
    private int mWidth;

    public ThumbnailLoader(Context context, NoteModel noteModel) {
        this.mContext = context;
        this.mNoteModel = noteModel;
    }

    public ThumbnailLoader resize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        return this;
    }

    public ThumbnailLoader type(String str) {
        this.mType = str;
        return this;
    }

    public ThumbnailTask into(ImageView imageView) {
        ThumbnailTask thumbnailTask = (ThumbnailTask) imageView.getTag();
        if (thumbnailTask != null && thumbnailTask.isRunning()) {
            thumbnailTask.cancel();
        }
        ThumbnailTask thumbnailTask2 = new ThumbnailTask(this.mContext, this.mNoteModel, imageView, this.mWidth, this.mHeight, this.mType);
        thumbnailTask2.commit();
        return thumbnailTask2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static class ThumbnailTask extends BitmapTask {
        private Context mContext;
        private int mHeight;
        private WeakReference<ImageView> mImageView;
        private NoteModel mNoteModel;
        private String mType;
        private int mWidth;

        @Override // com.miui.notes.tool.BitmapTask
        protected Bitmap getBitmap() {
            return null;
        }

        public ThumbnailTask(Context context, NoteModel noteModel, ImageView imageView, int i, int i2, String str) {
            this.mContext = context;
            this.mNoteModel = noteModel;
            this.mImageView = new WeakReference<>(imageView);
            this.mWidth = i;
            this.mHeight = i2;
            this.mType = str;
            imageView.setTag(this);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.BitmapTask, com.miui.common.tool.async.Task
        public Bitmap doLoad() throws Exception {
            String attachmentPath;
            if (this.mImageView.get() == null) {
                return null;
            }
            ThumbnailCache thumbnail = CacheManager.getDefault().getThumbnail(this.mNoteModel.getId());
            if (thumbnail == null) {
                String strRetrieveFirstImage = NoteSchema.retrieveFirstImage(this.mNoteModel.getSnippet());
                if (this.mNoteModel.getNoteType().equals(NoteModel.NoteType.TYPE_MIND)) {
                    String mindContent = this.mNoteModel.getMindContent();
                    if (!TextUtils.isEmpty(mindContent)) {
                        strRetrieveFirstImage = ((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).thumbnail;
                    }
                } else if (!TextUtils.equals(this.mNoteModel.getNoteType(), "handwrite")) {
                    if (TextUtils.equals(this.mNoteModel.getNoteType(), "multi_handwrite")) {
                        strRetrieveFirstImage = this.mNoteModel.getThumbnail();
                    }
                } else {
                    strRetrieveFirstImage = NoteSchema.retrieveHandWriteThumbnail(this.mNoteModel.getMindContent());
                }
                if (!TextUtils.isEmpty(strRetrieveFirstImage)) {
                    thumbnail = new ThumbnailCache();
                    thumbnail.setPath(strRetrieveFirstImage);
                }
            }
            if (thumbnail == null) {
                return null;
            }
            Bitmap bitmapDecodeResource = BitmapCacheManager.getInstance().get(getThumbnailKey(thumbnail));
            if (bitmapDecodeResource == null) {
                String path = thumbnail.getPath();
                if (!AttachmentUtils.isDoodleSuffix(path)) {
                    if (TextUtils.equals(this.mNoteModel.getNoteType(), "multi_handwrite")) {
                        attachmentPath = MultiHandWritePathUtils.getEntFileCompletePath(path);
                    } else {
                        attachmentPath = AttachmentUtils.getAttachmentPath(this.mContext, path);
                    }
                } else {
                    attachmentPath = AttachmentUtils.getDoodleThumbnailPath(this.mContext, path);
                }
                if (Thumbnail.GRID.equals(this.mType)) {
                    ImageView imageView = this.mImageView.get();
                    if (imageView != null) {
                        int width = ((View) imageView.getParent()).getWidth();
                        this.mWidth = width;
                        if (width == 0) {
                            defer();
                            return null;
                        }
                        bitmapDecodeResource = new BitmapDecoder(this.mContext, attachmentPath).getPolishedScaledBitmap(this.mWidth);
                    }
                } else if (this.mImageView.get() != null) {
                    bitmapDecodeResource = new BitmapDecoder(this.mContext, attachmentPath).getSquareBitmap(this.mWidth);
                }
                if (bitmapDecodeResource != null) {
                    BitmapCacheManager.getInstance().put(getThumbnailKey(thumbnail), bitmapDecodeResource);
                }
            }
            if (bitmapDecodeResource == null || bitmapDecodeResource.getWidth() == 0) {
                bitmapDecodeResource = BitmapFactory.decodeResource(NoteApp.getInstance().getResources(), R.drawable.ic_common_note_content_blank);
            }
            Rect rect = new Rect();
            rect.set(0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight());
            Canvas canvas = new Canvas();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), Bitmap.Config.ARGB_8888);
            Logger.INSTANCE.d("PreviewImageView", "finalBm.Density = " + bitmapCreateBitmap.getDensity() + " bm.Density = " + bitmapDecodeResource.getDensity());
            bitmapCreateBitmap.setDensity(NoteApp.getInstance().getResources().getDisplayMetrics().densityDpi);
            bitmapDecodeResource.setDensity(NoteApp.getInstance().getResources().getDisplayMetrics().densityDpi);
            bitmapCreateBitmap.eraseColor(-1);
            canvas.setBitmap(bitmapCreateBitmap);
            canvas.drawBitmap(bitmapDecodeResource, rect, rect, (Paint) null);
            canvas.setBitmap(null);
            return bitmapCreateBitmap;
        }

        @Override // com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, Bitmap bitmap) {
            ImageView imageView = this.mImageView.get();
            if (imageView != null) {
                if (bitmap != null) {
                    bitmap.setDensity(imageView.getContext().getResources().getDisplayMetrics().densityDpi);
                    if (imageView.getVisibility() == 0) {
                        imageView.setImageBitmap(bitmap);
                        return;
                    }
                    return;
                }
                imageView.setImageDrawable(null);
            }
        }

        private String getThumbnailKey(ThumbnailCache thumbnailCache) {
            return this.mType + thumbnailCache.getPath();
        }

        private void defer() {
            UIUtils.runOnUIThread(new Runnable() { // from class: com.miui.notes.thumbnail.ThumbnailLoader.ThumbnailTask.1
                @Override // java.lang.Runnable
                public void run() {
                    ImageView imageView = (ImageView) ThumbnailTask.this.mImageView.get();
                    if (imageView != null) {
                        ThumbnailTask thumbnailTaskCopyOf = ThumbnailTask.copyOf(ThumbnailTask.this, imageView);
                        imageView.setTag(thumbnailTaskCopyOf);
                        thumbnailTaskCopyOf.commit();
                    }
                }
            }, 10L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ThumbnailTask copyOf(ThumbnailTask thumbnailTask, ImageView imageView) {
            return new ThumbnailTask(thumbnailTask.mContext, thumbnailTask.mNoteModel, imageView, thumbnailTask.mWidth, thumbnailTask.mHeight, thumbnailTask.mType);
        }
    }
}
