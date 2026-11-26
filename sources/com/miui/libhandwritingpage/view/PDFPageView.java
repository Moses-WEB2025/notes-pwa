package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import com.miui.libhandwritingpage.listener.PDFLoadingListener;
import java.io.File;
import java.lang.ref.WeakReference;
import xc.engine.storage.pdf.PDFManager;
import xc.engine.storage.pdf.PDFReader;

/* JADX INFO: loaded from: classes3.dex */
public class PDFPageView extends View {
    private static final int LOAD_FINISH = 2;
    private static final String TAG = "PDFPageView";
    private static final int UPDATE_BITMAP = 1;
    private Bitmap mBitmap;
    private final RectF mDstRectF;
    private String mFilePath;
    private final HandlerThread mHandlerThread;
    private boolean mIsLoaded;
    private boolean mIsLoading;
    private PDFLoadingListener mLoadingListener;
    private final Handler mMainHandler;
    private float mOffsetX;
    private float mOffsetY;
    private int mPageIndex;
    private float mScale;
    private final Rect mSrcRect;
    private final Handler mThreadHandler;
    private int mVisibleRectWidth;
    private int mVisibleWidth;

    public PDFPageView(Context context) {
        super(context);
        this.mScale = 1.0f;
        this.mSrcRect = new Rect();
        this.mDstRectF = new RectF();
        HandlerThread handlerThread = new HandlerThread("");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mThreadHandler = new Handler(handlerThread.getLooper());
        this.mMainHandler = new MyHandler();
    }

    public void setLoadListener(PDFLoadingListener pDFLoadingListener) {
        this.mLoadingListener = pDFLoadingListener;
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public void setData(String str, int i) {
        this.mIsLoading = false;
        if (TextUtils.isEmpty(str)) {
            this.mFilePath = null;
            this.mPageIndex = -1;
            if (this.mIsLoaded) {
                this.mIsLoaded = false;
                clearMessageAndBitmap();
                return;
            }
            return;
        }
        this.mFilePath = str;
        this.mPageIndex = i;
        this.mIsLoaded = false;
        if (!TextUtils.isEmpty(str) && i >= 0 && new File(str).exists()) {
            load();
            this.mIsLoaded = true;
        } else {
            clearMessageAndBitmap();
        }
    }

    public void doScale(float f, float f2, float f3, int i) {
        if (TextUtils.isEmpty(this.mFilePath) || !this.mIsLoaded) {
            return;
        }
        this.mScale = f;
        this.mOffsetX = f2;
        this.mOffsetY = f3;
        this.mVisibleRectWidth = i;
        invalidate();
    }

    public void doScaleEnd() {
        if (TextUtils.isEmpty(this.mFilePath) || !this.mIsLoaded || this.mPageIndex < 0) {
            return;
        }
        invalidate();
        Handler handler = this.mThreadHandler;
        Handler handler2 = this.mMainHandler;
        String str = this.mFilePath;
        int i = this.mPageIndex;
        float f = this.mScale;
        int i2 = this.mVisibleRectWidth;
        if (f > 1.0f) {
            i2 = (int) (i2 * f);
        }
        handler.post(new UpdateRunnable(handler2, str, i, i2));
    }

    public void release() {
        this.mMainHandler.removeCallbacksAndMessages(null);
        this.mThreadHandler.removeCallbacksAndMessages(null);
        this.mHandlerThread.quitSafely();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mVisibleWidth = i;
        if (this.mVisibleRectWidth == 0) {
            this.mVisibleRectWidth = i;
            if (this.mIsLoaded) {
                load();
            }
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        if (!this.mIsLoaded || (bitmap = this.mBitmap) == null || bitmap.isRecycled()) {
            return;
        }
        canvas.save();
        canvas.translate(this.mOffsetX, this.mOffsetY);
        float f = this.mScale;
        canvas.scale(f, f);
        RectF rectF = this.mDstRectF;
        rectF.set(0.0f, 0.0f, rectF.left + this.mVisibleWidth, this.mDstRectF.top + (((this.mVisibleWidth * this.mBitmap.getHeight()) * 1.0f) / this.mBitmap.getWidth()));
        this.mSrcRect.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
        canvas.drawBitmap(this.mBitmap, this.mSrcRect, this.mDstRectF, (Paint) null);
        canvas.restore();
    }

    private void clearMessageAndBitmap() {
        this.mThreadHandler.removeCallbacksAndMessages(null);
        this.mBitmap = null;
        invalidate();
    }

    private void load() {
        if (this.mVisibleRectWidth == 0) {
            return;
        }
        this.mIsLoading = true;
        clearMessageAndBitmap();
        Handler handler = this.mThreadHandler;
        Handler handler2 = this.mMainHandler;
        String str = this.mFilePath;
        int i = this.mPageIndex;
        float f = this.mScale;
        int i2 = this.mVisibleRectWidth;
        if (f > 1.0f) {
            i2 = (int) (i2 * f);
        }
        handler.post(new UpdateRunnable(handler2, str, i, i2));
    }

    private static class MyHandler extends Handler {
        private final WeakReference<PDFPageView> weakReference;

        private MyHandler(PDFPageView pDFPageView) {
            this.weakReference = new WeakReference<>(pDFPageView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PDFPageView pDFPageView = this.weakReference.get();
            if (pDFPageView == null) {
                return;
            }
            if (message.what == 1) {
                pDFPageView.mBitmap = (Bitmap) message.obj;
                pDFPageView.invalidate();
            } else if (message.what == 2) {
                pDFPageView.mIsLoading = false;
                if (pDFPageView.mLoadingListener != null) {
                    pDFPageView.mLoadingListener.onLoaded();
                }
            }
        }
    }

    public static class UpdateRunnable implements Runnable {
        private final Handler handler;
        private final int pageIndex;
        private final PDFReader pdfReader;
        private final int width;

        private UpdateRunnable(Handler handler, String str, int i, int i2) {
            this.handler = handler;
            this.pageIndex = i;
            this.width = i2;
            this.pdfReader = PDFManager.getInstance().createReader(str);
        }

        @Override // java.lang.Runnable
        public void run() {
            PDFReader pDFReader = this.pdfReader;
            if (pDFReader == null) {
                Handler handler = this.handler;
                if (handler != null) {
                    handler.sendEmptyMessage(2);
                    return;
                }
                return;
            }
            try {
                pDFReader.load();
                int[] pageSize = this.pdfReader.getPageSize(this.pageIndex);
                int i = 0;
                int i2 = pageSize == null ? 0 : pageSize[0];
                if (pageSize != null) {
                    i = pageSize[1];
                }
                int i3 = this.width;
                if (i3 > 0) {
                    if (i3 > i2 * 3) {
                        i = (int) (((i3 * i) / i2) / 2.0f);
                        i2 = (int) (i3 / 2.0f);
                    } else {
                        i = (i * i3) / i2;
                        i2 = i3;
                    }
                }
                int i4 = 1;
                while (((i2 * i) * 2) / i4 > 209715200) {
                    i4 *= 2;
                    i2 /= 2;
                    i /= 2;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_4444);
                this.pdfReader.render(bitmapCreateBitmap, this.pageIndex);
                Handler handler2 = this.handler;
                if (handler2 != null) {
                    this.handler.sendMessage(handler2.obtainMessage(1, bitmapCreateBitmap));
                }
                this.pdfReader.close();
            } catch (Exception unused) {
            }
            Handler handler3 = this.handler;
            if (handler3 != null) {
                handler3.sendEmptyMessage(2);
            }
        }
    }
}
