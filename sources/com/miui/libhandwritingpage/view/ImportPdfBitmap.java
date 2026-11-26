package com.miui.libhandwritingpage.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import com.miui.libhandwritingpage.listener.PDFLoadingListener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.storage.pdf.PDFManager;
import xc.engine.storage.pdf.PDFReader;

/* JADX INFO: loaded from: classes3.dex */
public class ImportPdfBitmap {
    private static final int LOAD_FINISH = 2;
    private static final String TAG = "ImportPdfBitmap";
    private static final int UPDATE_BITMAP = 1;
    public static final int VISIBLE_SIZE = 10;
    private WeakReference<MiCurveEngine> curveEngineRef;
    private String mFilePath;
    private final HandlerThread mHandlerThread;
    private PDFLoadingListener mLoadingListener;
    private final Handler mMainHandler;
    private final SparseIntArray mPagePdfTable;
    private final Handler mThreadHandler;
    private int mVisibleRectWidth = 0;
    private boolean mIsLoading = false;
    private final LimitedHashMap<Integer, Bitmap> mBitmapMap = new LimitedHashMap<>(this);
    private final float mScale = 1.0f;

    public ImportPdfBitmap() {
        HandlerThread handlerThread = new HandlerThread("ImportPdfBitmap");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mThreadHandler = new Handler(handlerThread.getLooper());
        this.mMainHandler = new MyHandler();
        this.mPagePdfTable = new SparseIntArray();
    }

    public synchronized Bitmap getBitmap(int i) {
        int i2 = this.mPagePdfTable.get(i, -1);
        if (i2 == -1) {
            return null;
        }
        return this.mBitmapMap.get(Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPageIndexByPdfIndex(int i) {
        for (int i2 = 0; i2 < this.mPagePdfTable.size(); i2++) {
            if (this.mPagePdfTable.valueAt(i2) == i) {
                return this.mPagePdfTable.keyAt(i2);
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void insertBitmap(int i, Bitmap bitmap) {
        Log.d(TAG, "insertBitmap pdfIndex:" + i);
        this.mBitmapMap.put(Integer.valueOf(i), bitmap);
    }

    public void setVisibleRect(int i) {
        Log.d(TAG, "setVisibleRect:" + i);
        this.mVisibleRectWidth = i;
    }

    public void setLoadListener(PDFLoadingListener pDFLoadingListener) {
        this.mLoadingListener = pDFLoadingListener;
    }

    public void setCurveEngineRef(MiCurveEngine miCurveEngine) {
        this.curveEngineRef = new WeakReference<>(miCurveEngine);
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public void setData(String str, LinkedHashMap<Integer, Integer> linkedHashMap) {
        if (this.mIsLoading) {
            Log.d(TAG, "setData isLoading");
        }
        if (TextUtils.isEmpty(str) || linkedHashMap == null || linkedHashMap.isEmpty()) {
            return;
        }
        String str2 = TAG;
        Log.d(str2, "setData path:" + str + " pages count:" + linkedHashMap.size());
        this.mFilePath = str;
        if (new File(str).exists()) {
            load(linkedHashMap);
        } else {
            Log.d(str2, "setData file not exists:" + str);
        }
    }

    public void release() {
        String str = TAG;
        Log.d(str, "release begin");
        this.mMainHandler.removeCallbacksAndMessages(null);
        this.mThreadHandler.removeCallbacksAndMessages(null);
        this.mHandlerThread.interrupt();
        this.mHandlerThread.quitSafely();
        this.mBitmapMap.clear();
        this.mPagePdfTable.clear();
        this.mLoadingListener = null;
        Log.d(str, "release end");
    }

    private void clearMessageAndBitmap() {
        this.mThreadHandler.removeCallbacksAndMessages(null);
    }

    private void load(LinkedHashMap<Integer, Integer> linkedHashMap) {
        this.mIsLoading = true;
        clearMessageAndBitmap();
        float f = this.mScale;
        this.mThreadHandler.post(new UpdateRunnable(this, this.mFilePath, linkedHashMap, f <= 1.0f ? this.mVisibleRectWidth : (int) (this.mVisibleRectWidth * f)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIndexTable(int i, int i2) {
        Log.i(TAG, String.format("updateIndexTable: [%d]-[%d]", Integer.valueOf(i), Integer.valueOf(i2)));
        this.mPagePdfTable.put(i, i2);
        PDFLoadingListener pDFLoadingListener = this.mLoadingListener;
        if (pDFLoadingListener != null) {
            pDFLoadingListener.onLoaded(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadFinish() {
        this.mIsLoading = false;
        PDFLoadingListener pDFLoadingListener = this.mLoadingListener;
        if (pDFLoadingListener != null) {
            pDFLoadingListener.onFinish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRemove(int i) {
        PDFLoadingListener pDFLoadingListener = this.mLoadingListener;
        if (pDFLoadingListener != null) {
            pDFLoadingListener.onRemove(getPageIndexByPdfIndex(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLoadFinishToMainThread() {
        Handler handler = this.mMainHandler;
        if (handler == null) {
            return;
        }
        handler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateToMainThread(int i, int i2, Bitmap bitmap) {
        Handler handler = this.mMainHandler;
        if (handler == null) {
            return;
        }
        this.mMainHandler.sendMessage(handler.obtainMessage(1, i, i2, bitmap));
    }

    private static class LimitedHashMap<K, V> extends LinkedHashMap<K, V> {
        private static final int MAX_ENTRIES = 30;
        private ImportPdfBitmap mImportPdfBitmap;

        public LimitedHashMap(ImportPdfBitmap importPdfBitmap) {
            super(16, 0.75f, false);
            this.mImportPdfBitmap = importPdfBitmap;
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
            if (size() <= 30) {
                return false;
            }
            if (this.mImportPdfBitmap.getPageIndexByPdfIndex(((Integer) entry.getKey()).intValue()) == 0) {
                remove(entry.getKey());
                put(entry.getKey(), entry.getValue());
                return false;
            }
            if (!(entry.getValue() instanceof Bitmap)) {
                return true;
            }
            this.mImportPdfBitmap.notifyRemove(((Integer) entry.getKey()).intValue());
            return true;
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<ImportPdfBitmap> weakReference;

        private MyHandler(ImportPdfBitmap importPdfBitmap) {
            super(Looper.getMainLooper());
            this.weakReference = new WeakReference<>(importPdfBitmap);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ImportPdfBitmap importPdfBitmap = this.weakReference.get();
            if (importPdfBitmap == null) {
                return;
            }
            if (message.what == 1) {
                int i = message.arg1;
                int i2 = message.arg2;
                Bitmap bitmap = (Bitmap) message.obj;
                if (bitmap == null) {
                    Log.e(ImportPdfBitmap.TAG, "bitmap is null");
                    return;
                } else {
                    importPdfBitmap.insertBitmap(i2, bitmap);
                    importPdfBitmap.updateIndexTable(i, i2);
                    return;
                }
            }
            if (message.what == 2) {
                importPdfBitmap.loadFinish();
            }
        }
    }

    private class UpdateRunnable implements Runnable {
        private final LinkedHashMap<Integer, Integer> mPagePdfLoadingTable;
        private final PDFReader pdfReader;
        private final WeakReference<MiCurveEngine> refEngine;
        private final WeakReference<ImportPdfBitmap> reference;
        private final int width;

        private UpdateRunnable(ImportPdfBitmap importPdfBitmap, String str, LinkedHashMap<Integer, Integer> linkedHashMap, int i) {
            this.reference = new WeakReference<>(importPdfBitmap);
            this.refEngine = importPdfBitmap.curveEngineRef;
            this.mPagePdfLoadingTable = linkedHashMap;
            this.width = i;
            this.pdfReader = PDFManager.getInstance().createReader(str);
        }

        private void loadBitmap(int i, int i2) {
            if (i2 < 0) {
                return;
            }
            try {
                int[] pageSize = this.pdfReader.getPageSize(i2);
                int i3 = 0;
                int i4 = pageSize == null ? 0 : pageSize[0];
                int i5 = 1;
                if (pageSize != null) {
                    i3 = pageSize[1];
                }
                int i6 = this.width;
                if (i6 > 0) {
                    if (i6 > i4 * 3) {
                        i3 = (int) (((i6 * i3) / i4) / 2.0f);
                        i4 = (int) (i6 / 2.0f);
                    } else {
                        i3 = (i3 * i6) / i4;
                        i4 = i6;
                    }
                }
                while (((i4 * i3) * 2) / i5 > 209715200) {
                    i5 *= 2;
                    i4 /= 2;
                    i3 /= 2;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i3, Bitmap.Config.ARGB_8888);
                this.pdfReader.render(bitmapCreateBitmap, i2);
                if (this.reference.get() != null) {
                    this.reference.get().sendUpdateToMainThread(i, i2, bitmapCreateBitmap);
                }
            } catch (Exception e) {
                Log.e(ImportPdfBitmap.TAG, "loadBitmap error: " + e.getMessage());
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            LinkedHashMap<Integer, Integer> linkedHashMap;
            ImportPdfBitmap importPdfBitmap = this.reference.get();
            if (importPdfBitmap == null) {
                return;
            }
            if (this.pdfReader == null || (linkedHashMap = this.mPagePdfLoadingTable) == null || linkedHashMap.isEmpty()) {
                importPdfBitmap.sendLoadFinishToMainThread();
            } else {
                startLoadTask(importPdfBitmap);
            }
        }

        private void startLoadTask(ImportPdfBitmap importPdfBitmap) {
            this.pdfReader.load();
            for (Map.Entry<Integer, Integer> entry : this.mPagePdfLoadingTable.entrySet()) {
                int iIntValue = entry.getKey().intValue();
                int iIntValue2 = entry.getValue().intValue();
                Bitmap bitmap = importPdfBitmap.getBitmap(iIntValue);
                if (bitmap != null) {
                    if (iIntValue2 == importPdfBitmap.mPagePdfTable.get(iIntValue, -1)) {
                        MiCurveEngine miCurveEngine = this.refEngine.get();
                        if (miCurveEngine == null || !miCurveEngine.getPageSystem().pageHasBgImage(iIntValue)) {
                            Log.d(ImportPdfBitmap.TAG, "startLoadTask: dont hasBgImage " + iIntValue + " ," + iIntValue2);
                        }
                    }
                    importPdfBitmap.sendUpdateToMainThread(iIntValue, iIntValue2, bitmap);
                } else {
                    loadBitmap(iIntValue, iIntValue2);
                }
            }
            this.pdfReader.close();
            importPdfBitmap.sendLoadFinishToMainThread();
        }
    }
}
