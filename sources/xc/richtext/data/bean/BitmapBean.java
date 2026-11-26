package xc.richtext.data.bean;

import android.graphics.Bitmap;
import android.graphics.RectF;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes6.dex */
public class BitmapBean {
    public float mAngle;
    public int mId;
    public String mPath;
    public RectF mRectF;
    public int mSpannedStatus;
    public float mScale = 1.0f;
    public Bitmap mBitmap = null;
    public boolean isBlank = false;
    public AtomicBoolean hasLoad = new AtomicBoolean(false);

    public BitmapBean(int i, String str, RectF rectF, float f, int i2) {
        this.mPath = str;
        this.mRectF = rectF;
        this.mId = i;
        this.mAngle = f;
        this.mSpannedStatus = i2;
    }

    public void updateBean(RectF rectF, float f, int i) {
        this.mRectF = rectF;
        this.mSpannedStatus = i;
        this.mAngle = f;
    }
}
