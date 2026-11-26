package com.miui.handwriting.common.engine.secret;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.miui.creation.common.R;
import com.miui.handwriting.common.filemanager.base.BaseImport;
import com.miui.handwriting.common.filemanager.base.ImportCallback;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.io.File;
import java.util.HashMap;
import miuix.appcompat.app.AppCompatActivity;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.api.PenEngine;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ImportStrategy extends BaseImport implements com.miui.handwriting.common.engine.export.ImportStrategy {
    private static final float SIZE_VALUE = 0.7f;
    private static final String TAG = "ImportStrategy";
    protected boolean isCheckImageSize = false;
    protected ActivityResultLauncher mActivityResultLauncher;
    protected ActivityResultLauncher mPickFileLauncher;

    public static void deleteImportCache() {
    }

    protected static boolean useUri() {
        return true;
    }

    public ImportStrategy setCheckImageSize(boolean z) {
        this.isCheckImageSize = z;
        return this;
    }

    public ImportStrategy(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
        this.mContext = appCompatActivity.getApplicationContext();
    }

    public void initRegister() {
        Log.d(TAG, "init context" + this.mContext + "___" + this.mImportCallback);
        initPickFile(this.mActivity);
        initPickImage(this.mActivity);
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importPdf() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(ShareAppsAdapter.TYPE_PDF);
        ActivityResultLauncher activityResultLauncher = this.mPickFileLauncher;
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(intent);
        }
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importImage() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        ActivityResultLauncher activityResultLauncher = this.mActivityResultLauncher;
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(intent);
        }
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importFiles() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        ActivityResultLauncher activityResultLauncher = this.mPickFileLauncher;
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(intent);
        }
    }

    public void initPickFile(AppCompatActivity appCompatActivity) {
        this.mPickFileLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.handwriting.common.engine.secret.ImportStrategy$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.m721x9a2c8709((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initPickFile$0$com-miui-handwriting-common-engine-secret-ImportStrategy, reason: not valid java name */
    /* synthetic */ void m721x9a2c8709(ActivityResult activityResult) {
        String str = TAG;
        Log.d(str, "onActivityResult pickFileLauncher:" + activityResult.getResultCode() + "_" + activityResult.getData());
        if (activityResult.getResultCode() != -1 || activityResult.getData() == null) {
            return;
        }
        Uri data = activityResult.getData().getData();
        Log.d(str, "onActivityResult uri:" + data);
        if (data != null) {
            Log.d(str, "context:" + getContext());
            if (!FileUtil.isPDF(data.getPath())) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.import_pick_pdf_error), 0).show();
                return;
            }
            String copyPath = getCopyPath(this.mContext, data);
            Log.d(str, "onActivityResult realPath:" + data + "_" + getImportCallback());
            if (getImportCallback() != null) {
                ImportCallback importCallback = getImportCallback();
                if (useUri()) {
                    copyPath = data.toString();
                }
                importCallback.call(1, copyPath);
            }
        }
    }

    public void initPickImage(AppCompatActivity appCompatActivity) {
        this.mActivityResultLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.handwriting.common.engine.secret.ImportStrategy.1
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) throws Throwable {
                Uri data;
                Point imageSize;
                Log.d(ImportStrategy.TAG, "onActivityResult:" + activityResult.getResultCode());
                if (activityResult.getResultCode() != -1 || activityResult.getData() == null || (data = activityResult.getData().getData()) == null) {
                    return;
                }
                Log.d(ImportStrategy.TAG, "uri:" + data);
                String copyPath = "";
                if (ImportStrategy.useUri()) {
                    imageSize = BitmapUtil.getImageSizeFromUri(ImportStrategy.this.mContext, data);
                    Log.d(ImportStrategy.TAG, "imageSize:" + imageSize.x + "_" + imageSize.y);
                } else {
                    imageSize = BitmapUtil.getImageSize("");
                    ImportStrategy importStrategy = ImportStrategy.this;
                    copyPath = importStrategy.getCopyPath(importStrategy.mContext, data);
                }
                Log.d(ImportStrategy.TAG, "imageSizeYx:" + ((((double) imageSize.y) * 1.0d) / ((double) imageSize.x)));
                if (ImportStrategy.this.isCheckImageSize && (((double) imageSize.y) * 1.0d) / ((double) imageSize.x) > 2.1d) {
                    Toast.makeText(ImportStrategy.this.getContext(), R.string.import_image_long_error, 0).show();
                } else if (ImportStrategy.this.getImportCallback() != null) {
                    ImportCallback importCallback = ImportStrategy.this.getImportCallback();
                    if (ImportStrategy.useUri()) {
                        copyPath = data.toString();
                    }
                    importCallback.call(2, copyPath);
                }
            }
        });
    }

    public static void importImageBgToPage(final Context context, PageInkModel pageInkModel, String str) {
        Log.d(TAG, "bitmapPath:" + str);
        try {
            HashMap map = new HashMap();
            PagePreviewData pagePreviewData = new PagePreviewData();
            pagePreviewData.bgImage = str;
            map.put(0, pagePreviewData);
            pageInkModel.setPageBackground(map);
        } catch (Exception unused) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.handwriting.common.engine.secret.ImportStrategy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(context, "Insert Image Failure", 0).show();
                }
            });
        }
    }

    public String getCopyPath(Context context, Uri uri) {
        if (uri == null) {
            return "";
        }
        if (!useUri()) {
            return FileUtil.getFileByPath(uri.toString()).getAbsolutePath();
        }
        String str = context.getExternalCacheDir() + "/import_tmp_copy";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
        }
        String path = uri.getPath();
        String str2 = str + path.substring(path.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR)).replace(InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + "_");
        try {
            FileUtil.copyUriToFile(context, uri, new File(str2));
        } catch (Exception e) {
            Log.e(TAG, "copyUriToFile error", e);
        }
        Log.e(TAG, "targetPath:" + str2);
        return str2;
    }

    public static void insertImageCenter(PageInkModel pageInkModel, String str) throws Throwable {
        if (pageInkModel != null) {
            if (PenEngine.isEmptyCreationFromCreate()) {
                PenEngine.setIsEmptyCreationFromCreate(false);
            }
            pageInkModel.addBitmap(str, getPicRectF(pageInkModel, str));
        }
    }

    public static void insertImagePosition(PageInkModel pageInkModel, String str, float f, float f2) throws Throwable {
        if (pageInkModel != null) {
            if (PenEngine.isEmptyCreationFromCreate()) {
                PenEngine.setIsEmptyCreationFromCreate(false);
            }
            pageInkModel.addBitmap(str, getPicRectF(pageInkModel, str), new PointF(f, f2));
        }
    }

    private static RectF getPicRectF(PageInkModel pageInkModel, String str) {
        if (pageInkModel == null) {
            return new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        }
        RectF itemRectF = pageInkModel.getItemRectF();
        RectF visibleRectF = pageInkModel.getVisibleRectF();
        String str2 = TAG;
        Log.d(str2, "itemRectF：" + itemRectF);
        Log.d(str2, "getPicRectF: " + visibleRectF);
        int[] imageSizeBySize = BitmapUtil.getImageSizeBySize(str, (int) (itemRectF.width() * 0.7f), (int) (itemRectF.height() * 0.7f));
        Log.d(str2, " width:" + imageSizeBySize[0] + " height:" + imageSizeBySize[1]);
        float fWidth = visibleRectF.left;
        float fHeight = visibleRectF.top;
        if (visibleRectF.width() >= imageSizeBySize[0]) {
            fWidth = ((visibleRectF.width() - imageSizeBySize[0]) / 2.0f) + visibleRectF.left;
        } else if (itemRectF.width() > imageSizeBySize[0] && itemRectF.left < 0.0f && imageSizeBySize[0] > itemRectF.right) {
            fWidth = itemRectF.right - imageSizeBySize[0];
        }
        if (visibleRectF.height() >= imageSizeBySize[1]) {
            fHeight = ((visibleRectF.height() - imageSizeBySize[1]) / 2.0f) + visibleRectF.top;
        } else if (itemRectF.height() > imageSizeBySize[1] && itemRectF.top < 0.0f && imageSizeBySize[1] > itemRectF.bottom) {
            fHeight = itemRectF.bottom - imageSizeBySize[1];
        }
        RectF rectF = new RectF(fWidth, fHeight, imageSizeBySize[0] + fWidth, imageSizeBySize[1] + fHeight);
        Log.d(str2, "locationRectF:" + rectF + " left:" + fWidth + " top：" + fHeight);
        return rectF;
    }
}
