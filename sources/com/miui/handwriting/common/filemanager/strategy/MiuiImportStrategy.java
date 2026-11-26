package com.miui.handwriting.common.filemanager.strategy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.miui.creation.common.R;
import com.miui.handwriting.common.engine.secret.ImportStrategy;
import com.miui.handwriting.common.filemanager.base.ImportCallback;
import com.miui.handwriting.common.tools.ExternalAppUtils;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import java.util.ArrayList;
import miuix.appcompat.app.AppCompatActivity;
import miuix.os.Build;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiImportStrategy extends ImportStrategy {
    private static final String FILE_ACTIVITY = "com.android.fileexplorer.activity.FileActivity";
    private static final String FILE_MANAGER_PACKAGE;
    private static final String FILE_MANAGER_PACKAGE_CN = "com.android.fileexplorer";
    private static final String FILE_MANAGER_PACKAGE_GL = "com.mi.android.globalFileexplorer";
    private static final String TAG = "MiuiImportStrategy";
    protected ActivityResultLauncher fromGalleryLauncher;

    static {
        FILE_MANAGER_PACKAGE = Build.IS_INTERNATIONAL_BUILD ? "com.mi.android.globalFileexplorer" : "com.android.fileexplorer";
    }

    public MiuiImportStrategy(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        Log.d(TAG, "init");
        this.fromGalleryLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.handwriting.common.filemanager.strategy.MiuiImportStrategy$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) throws Throwable {
                this.f$0.m722x5f702ce0((ActivityResult) obj);
            }
        });
        bindActivity(appCompatActivity);
        initRegister();
    }

    /* JADX INFO: renamed from: lambda$new$0$com-miui-handwriting-common-filemanager-strategy-MiuiImportStrategy, reason: not valid java name */
    /* synthetic */ void m722x5f702ce0(ActivityResult activityResult) throws Throwable {
        Uri data;
        String copyPath;
        Point imageSize;
        Intent data2 = activityResult.getData();
        if (activityResult.getResultCode() != -1 || activityResult.getData() == null) {
            return;
        }
        ArrayList parcelableArrayListExtra = data2.getParcelableArrayListExtra("pick-result-data");
        String str = TAG;
        Log.d(str, "onActivityResult add images, count: " + (parcelableArrayListExtra == null ? 0 : parcelableArrayListExtra.size()));
        if (parcelableArrayListExtra == null) {
            data = data2.getData();
        } else {
            data = parcelableArrayListExtra.size() > 0 ? (Uri) parcelableArrayListExtra.get(0) : null;
        }
        Log.d(str, "resUri:" + data);
        if (useUri()) {
            imageSize = BitmapUtil.getImageSizeFromUri(this.mContext, data);
            copyPath = "";
        } else {
            copyPath = getCopyPath(this.mContext, data);
            imageSize = BitmapUtil.getImageSize(copyPath);
        }
        Log.d(str, "realPath:" + copyPath);
        if (this.isCheckImageSize && (((double) imageSize.y) * 1.0d) / ((double) imageSize.x) > 2.1d) {
            Toast.makeText(getContext(), R.string.import_image_long_error, 0).show();
        } else if (getImportCallback() != null) {
            ImportCallback importCallback = getImportCallback();
            if (useUri()) {
                copyPath = data.toString();
            }
            importCallback.call(2, copyPath);
        }
    }

    @Override // com.miui.handwriting.common.engine.secret.ImportStrategy, com.miui.handwriting.common.engine.export.ImportStrategy
    public void importImage() {
        Log.d(TAG, "importImage");
        ExternalAppUtils.selectImage(this.mContext, this.fromGalleryLauncher, 1);
    }

    @Override // com.miui.handwriting.common.engine.secret.ImportStrategy, com.miui.handwriting.common.engine.export.ImportStrategy
    public void importFiles() {
        Intent intentCreateFilePickIntent = createFilePickIntent();
        if (!isFindActivity(this.mContext, intentCreateFilePickIntent)) {
            intentCreateFilePickIntent = createCommonPickIntent();
        }
        if (this.mPickFileLauncher != null) {
            this.mPickFileLauncher.launch(intentCreateFilePickIntent);
        }
    }

    @Override // com.miui.handwriting.common.engine.secret.ImportStrategy, com.miui.handwriting.common.engine.export.ImportStrategy
    public void importPdf() {
        if (!isResolvePdfIntent(this.mContext)) {
            importFiles();
            return;
        }
        Log.d(TAG, "importPdf");
        Intent intentCreatePdfPickIntent = createPdfPickIntent();
        if (!isFindActivity(this.mContext, intentCreatePdfPickIntent)) {
            intentCreatePdfPickIntent = createFilePickIntent();
        }
        if (!isFindActivity(this.mContext, intentCreatePdfPickIntent)) {
            intentCreatePdfPickIntent = createCommonPickIntent();
        }
        if (this.mPickFileLauncher != null) {
            this.mPickFileLauncher.launch(intentCreatePdfPickIntent);
        }
    }

    private Intent createFilePickIntent() {
        Intent intent = new Intent();
        String str = FILE_MANAGER_PACKAGE;
        intent.setComponent(new ComponentName(str, FILE_ACTIVITY));
        intent.setPackage(str);
        intent.setAction("android.intent.action.PICK");
        intent.setType("*/*");
        return intent;
    }

    public static Intent createPdfPickIntent() {
        Intent intent = new Intent();
        intent.setPackage(FILE_MANAGER_PACKAGE);
        intent.setAction("android.intent.action.PICK");
        intent.setType("pdf/*");
        return intent;
    }

    public static boolean isResolvePdfIntent(Context context) {
        return isFindActivity(context, createPdfPickIntent());
    }

    private Intent createCommonPickIntent() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage(FILE_MANAGER_PACKAGE);
        intent.setType("*/*");
        return intent;
    }

    private static boolean isFindActivity(Context context, Intent intent) {
        try {
            return context.getPackageManager().resolveActivity(intent, 0) != null;
        } catch (Exception e) {
            Log.e(TAG, "isFindActivity error", e);
        }
    }
}
