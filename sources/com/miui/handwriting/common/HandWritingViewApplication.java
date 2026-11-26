package com.miui.handwriting.common;

import android.app.Application;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.LiteUtils;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: compiled from: HandWritingViewApplication.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007J\u0006\u0010\f\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/handwriting/common/HandWritingViewApplication;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mApplication", "Landroid/app/Application;", "getApplication", "init", "", "application", "stopAlgorithmEngine", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWritingViewApplication {
    public static final HandWritingViewApplication INSTANCE = new HandWritingViewApplication();
    private static final String TAG = "HandWritingViewApplication";
    private static Application mApplication;

    private HandWritingViewApplication() {
    }

    public final Application getApplication() {
        Log.d(TAG, "getApplication: " + (mApplication != null));
        Application application = mApplication;
        if (application == null) {
            throw new IllegalStateException("you mast init HandWritingViewApplication in process application");
        }
        if (application != null) {
            return application;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mApplication");
        return null;
    }

    public final void init(Application application) {
        Intrinsics.checkNotNullParameter(application, "application");
        mApplication = application;
        CreationPageSDK.init(application, LiteUtils.isSuperLite());
        Application application2 = mApplication;
        Application application3 = null;
        if (application2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApplication");
            application2 = null;
        }
        File externalFilesDir = application2.getExternalFilesDir("ents");
        Intrinsics.checkNotNull(externalFilesDir);
        FilePathConstant.entFilePath = externalFilesDir.getPath();
        Application application4 = mApplication;
        if (application4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApplication");
        } else {
            application3 = application4;
        }
        File externalFilesDir2 = application3.getExternalFilesDir("export");
        Intrinsics.checkNotNull(externalFilesDir2);
        FilePathConstant.exportFilePath = externalFilesDir2.getPath();
    }

    public final void stopAlgorithmEngine() {
        if (mApplication == null || CreationPageSDK.sApplication == null) {
            return;
        }
        CreationPageSDK.stopAlgorithmEngine();
    }
}
