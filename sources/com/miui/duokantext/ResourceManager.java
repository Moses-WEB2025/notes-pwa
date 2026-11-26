package com.miui.duokantext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class ResourceManager {
    private Context mContext;
    private Map<Object, ImageInfo> mImageTable;

    public void destroy() {
    }

    public class ImageInfo {
        public int image_height;
        public int image_width;
        public Object source;

        ImageInfo(Object obj, int i, int i2) {
            this.source = obj;
            this.image_width = i;
            this.image_height = i2;
        }

        public Bitmap getImage() {
            return ResourceManager.this.getImage(this);
        }

        public int getImageWidth() {
            return ResourceManager.this.getImageWidth(this);
        }

        public int getImageHeight() {
            return ResourceManager.this.getImageHeight(this);
        }
    }

    public ResourceManager(Context context) {
        this.mContext = context;
    }

    protected Object appendImage(Object obj, int i, int i2) {
        ImageInfo imageInfo;
        Map<Object, ImageInfo> map = this.mImageTable;
        if (map == null) {
            this.mImageTable = new HashMap();
            imageInfo = null;
        } else {
            imageInfo = map.get(obj);
        }
        if (imageInfo == null) {
            imageInfo = new ImageInfo(obj, i, i2);
            this.mImageTable.put(obj, imageInfo);
        }
        if (i > 0) {
            imageInfo.image_width = i;
        }
        if (i2 > 0) {
            imageInfo.image_height = i2;
        }
        return imageInfo;
    }

    public Object appendImage(int i, int i2, int i3) {
        return appendImage(Integer.valueOf(i), i2, i3);
    }

    public Object appendImage(String str, int i, int i2) {
        return appendImage((Object) str, i, i2);
    }

    public Object appendImage(int i) {
        return appendImage(i, 0, 0);
    }

    public Object appendImage(Uri uri, int i, int i2) {
        return appendImage((Object) uri, i, i2);
    }

    public Object appendImage(Uri uri) {
        return appendImage(uri, 0, 0);
    }

    public Object appendImage(String str) {
        return appendImage(str, 0, 0);
    }

    public int getImageWidth(Object obj) {
        Bitmap image;
        ImageInfo imageInfo = (ImageInfo) obj;
        if (imageInfo.image_width == 0 && (image = getImage(obj)) != null) {
            imageInfo.image_width = image.getWidth();
        }
        return imageInfo.image_width;
    }

    public int getImageHeight(Object obj) {
        Bitmap image;
        ImageInfo imageInfo = (ImageInfo) obj;
        if (imageInfo.image_height == 0 && (image = getImage(obj)) != null) {
            imageInfo.image_height = image.getHeight();
        }
        return imageInfo.image_height;
    }

    public Bitmap getImage(Object obj) {
        return loadImage((ImageInfo) obj);
    }

    protected Bitmap loadImage(ImageInfo imageInfo) {
        if (imageInfo.source instanceof Integer) {
            return BitmapFactory.decodeResource(this.mContext.getResources(), ((Integer) imageInfo.source).intValue());
        }
        Bitmap bitmapDecodeStream = null;
        if (!(imageInfo.source instanceof String)) {
            return null;
        }
        String str = (String) imageInfo.source;
        if (str.startsWith("assets://")) {
            try {
                InputStream inputStreamOpen = this.mContext.getAssets().open(str.substring("assets://".length()));
                bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                inputStreamOpen.close();
                return bitmapDecodeStream;
            } catch (IOException e) {
                e.printStackTrace();
                return bitmapDecodeStream;
            }
        }
        return BitmapFactory.decodeFile(str);
    }
}
