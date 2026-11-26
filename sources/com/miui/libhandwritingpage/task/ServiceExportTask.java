package com.miui.libhandwritingpage.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import xc.engine.api.listener.PageColorListener;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.storage.pdf.PDFManager;
import xc.engine.storage.pdf.PDFWriter;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceExportTask {
    private static final int MSG_EXPORT_ITEM_TASK = 3;
    private static final int MSG_EXPORT_PROGRESS = 2;
    private static final int MSG_EXPORT_RESULT = 1;
    private static final String TAG = "ServiceExportTask";
    private ConcurrentHashMap<Integer, Bitmap> mConcurrentHashMap;
    private final Context mContext;
    private List<PageData> mDataList;
    private String mDirPath;
    private File mEntFile;
    private ExportHandler mExportHandler = new ExportHandler(this);
    private ExportListener mExportListener;
    private ThreadPoolExecutor mExportService;
    private int mExportType;
    private int mExportWidth;
    private int mItemTaskCount;
    private String mOutFilePath;
    private PageColorListener mPageColorListener;
    private ArrayList<Integer> mPageIndexList;
    private int mThumbnailHeight;
    private int mThumbnailWidth;

    public interface ExportListener {
        void onCompleted(boolean z, String str);

        void onProgress(int i);
    }

    public ServiceExportTask(Context context, String str, String str2, int i, ThreadPoolExecutor threadPoolExecutor, ExportListener exportListener) {
        this.mContext = context;
        this.mOutFilePath = str2;
        this.mDirPath = str;
        this.mExportType = i;
        this.mExportListener = exportListener;
        this.mExportService = threadPoolExecutor;
    }

    public void setWidth(int i) {
        this.mExportWidth = i;
    }

    public void setThumbnailSize(int i, int i2) {
        this.mThumbnailWidth = i;
        this.mThumbnailHeight = i2;
    }

    public void start(List<PageData> list, List<Integer> list2, PageColorListener pageColorListener) {
        if (this.mExportListener == null || TextUtils.isEmpty(this.mDirPath) || TextUtils.isEmpty(this.mOutFilePath)) {
            return;
        }
        String entPathByDir = FileUtil.getEntPathByDir(this.mDirPath);
        if (entPathByDir == null || TextUtils.isEmpty(entPathByDir)) {
            this.mExportListener.onCompleted(false, null);
            return;
        }
        File file = new File(entPathByDir);
        this.mEntFile = file;
        if (!PageEntTools.verifyFormat(file.getAbsolutePath())) {
            this.mExportListener.onCompleted(false, null);
            return;
        }
        this.mDataList = new ArrayList(list);
        this.mPageIndexList = new ArrayList<>(list2);
        this.mPageColorListener = pageColorListener;
        if (this.mDataList.isEmpty()) {
            this.mExportListener.onCompleted(false, null);
        } else {
            initEntPages(list);
            export();
        }
    }

    private void initEntPages(List<PageData> list) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        InkEntFileIOHelper.clear();
        InkEntFileIOHelper.setScreenWidth(Math.max(i, i2));
        for (int i3 = 0; i3 < list.size(); i3++) {
            InkEntFileIOHelper.addPage(i3, list.get(i3).width, list.get(i3).height);
        }
    }

    private void export() {
        int i = this.mExportType;
        if (i == 1) {
            exportPdf();
        } else {
            if (i != 2) {
                return;
            }
            exportImages();
        }
    }

    private void cropThumbnail(Bitmap bitmap) throws Throwable {
        int width;
        Matrix matrix = new Matrix();
        float width2 = (this.mThumbnailWidth * 1.0f) / bitmap.getWidth();
        if (this.mThumbnailHeight / width2 > bitmap.getHeight()) {
            width2 = (this.mThumbnailHeight * 1.0f) / bitmap.getHeight();
            width = (int) ((bitmap.getWidth() - (this.mThumbnailWidth / width2)) / 2.0f);
        } else {
            width = 0;
        }
        matrix.postScale(width2, width2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(this.mThumbnailWidth, this.mThumbnailHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmapCreateBitmap2);
        Rect rect = new Rect(width, 0, this.mThumbnailWidth + width, this.mThumbnailHeight);
        Rect rect2 = new Rect(0, 0, this.mThumbnailWidth, this.mThumbnailHeight);
        if (bitmapCreateBitmap != null && !bitmapCreateBitmap.isRecycled()) {
            canvas.drawBitmap(bitmapCreateBitmap, rect, rect2, (Paint) null);
        }
        saveThumbnail(bitmapCreateBitmap2);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if (bitmapCreateBitmap == null || bitmapCreateBitmap.isRecycled()) {
            return;
        }
        bitmapCreateBitmap.recycle();
    }

    private void saveThumbnail(Bitmap bitmap) throws Throwable {
        try {
            BitmapUtil.saveBitmapToFile(bitmap, new File(this.mOutFilePath), true);
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            ExportHandler exportHandler = this.mExportHandler;
            if (exportHandler != null) {
                exportHandler.sendMessage(exportHandler.obtainMessage(1, this.mOutFilePath));
            }
        } catch (Exception unused) {
            ExportHandler exportHandler2 = this.mExportHandler;
            if (exportHandler2 != null) {
                exportHandler2.sendEmptyMessage(1);
            }
        }
    }

    private void exportImages() {
        File file = new File(this.mOutFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str = "Creation_Image_" + System.currentTimeMillis();
    }

    private void exportPdf() {
        this.mConcurrentHashMap = new ConcurrentHashMap<>();
    }

    private int getExportWidth(int i) {
        int i2 = this.mExportWidth;
        int i3 = PenEngineUtils.isSmallPad() ? 6000 : 7000;
        if (i <= this.mExportWidth * 2) {
            return i2;
        }
        int i4 = i / 2;
        return i4 > i3 ? i3 : i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePdfFile() {
        PDFWriter pDFWriterCreateWriter = PDFManager.getInstance().createWriter(this.mOutFilePath);
        File file = new File(this.mOutFilePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        pDFWriterCreateWriter.create();
        for (int i = 0; i < this.mDataList.size(); i++) {
            String absolutePath = !TextUtils.isEmpty(this.mDataList.get(i).pdfId) ? new File(this.mDirPath, this.mDataList.get(i).pdfId).getAbsolutePath() : null;
            if (this.mConcurrentHashMap.get(Integer.valueOf(i)) != null) {
                pDFWriterCreateWriter.addPage(this.mConcurrentHashMap.get(Integer.valueOf(i)), absolutePath, this.mDataList.get(i).pdfIndex);
            }
            ExportHandler exportHandler = this.mExportHandler;
            if (exportHandler != null) {
                this.mExportHandler.sendMessage(exportHandler.obtainMessage(2, Integer.valueOf((int) ((((i + 1) * 1.0f) / this.mDataList.size()) * 100.0f))));
            }
        }
        pDFWriterCreateWriter.save();
        pDFWriterCreateWriter.close();
        this.mConcurrentHashMap.clear();
        ExportHandler exportHandler2 = this.mExportHandler;
        if (exportHandler2 != null) {
            exportHandler2.sendMessage(exportHandler2.obtainMessage(2, 100));
            ExportHandler exportHandler3 = this.mExportHandler;
            exportHandler3.sendMessage(exportHandler3.obtainMessage(1, this.mOutFilePath));
        }
    }

    public void onResult(String str) {
        ExportListener exportListener = this.mExportListener;
        if (exportListener != null) {
            this.mPageColorListener = null;
            exportListener.onCompleted(!TextUtils.isEmpty(str), str);
        }
    }

    public void onItemTask() {
        ExportHandler exportHandler;
        int i = this.mItemTaskCount + 1;
        this.mItemTaskCount = i;
        if (i == this.mDataList.size()) {
            int i2 = this.mExportType;
            if (i2 != 1) {
                if (i2 == 2) {
                    onResult(this.mOutFilePath);
                    return;
                } else {
                    if (i2 != 4 || (exportHandler = this.mExportHandler) == null) {
                        return;
                    }
                    exportHandler.postDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.task.ServiceExportTask$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m856x157a516f();
                        }
                    }, 300L);
                    return;
                }
            }
            ConcurrentHashMap<Integer, Bitmap> concurrentHashMap = this.mConcurrentHashMap;
            if (concurrentHashMap == null || concurrentHashMap.size() == 0) {
                ExportHandler exportHandler2 = this.mExportHandler;
                if (exportHandler2 != null) {
                    exportHandler2.sendEmptyMessage(1);
                    return;
                }
                return;
            }
            this.mExportService.execute(new Runnable() { // from class: com.miui.libhandwritingpage.task.ServiceExportTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.savePdfFile();
                }
            });
            return;
        }
        if (this.mExportType == 2) {
            onProgress((int) (((this.mItemTaskCount * 1.0f) / this.mDataList.size()) * 100.0f));
        }
    }

    /* JADX INFO: renamed from: lambda$onItemTask$0$com-miui-libhandwritingpage-task-ServiceExportTask, reason: not valid java name */
    /* synthetic */ void m856x157a516f() {
        onProgress(100);
        onResult(this.mOutFilePath);
    }

    public void onProgress(int i) {
        ExportListener exportListener = this.mExportListener;
        if (exportListener != null) {
            exportListener.onProgress(i);
        }
    }

    public static class ExportHandler extends Handler {
        private final WeakReference<ServiceExportTask> mWeakReference;

        public ExportHandler(ServiceExportTask serviceExportTask) {
            this.mWeakReference = new WeakReference<>(serviceExportTask);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ServiceExportTask serviceExportTask = this.mWeakReference.get();
            if (serviceExportTask != null) {
                int i = message.what;
                if (i == 1) {
                    serviceExportTask.onResult((String) message.obj);
                } else if (i == 2) {
                    serviceExportTask.onProgress(((Integer) message.obj).intValue());
                } else {
                    if (i != 3) {
                        return;
                    }
                    serviceExportTask.onItemTask();
                }
            }
        }
    }
}
