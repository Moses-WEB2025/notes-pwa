package com.miui.doodle;

import android.app.Application;
import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleApplication.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0005H\u0007J\b\u0010\u0012\u001a\u00020\rH\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/doodle/DoodleApplication;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "appInstance", "Landroid/content/Context;", "getAppInstance", "()Landroid/content/Context;", "setAppInstance", "(Landroid/content/Context;)V", "SaveFlagNoChange", "", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/app/Application;", "preloadData", "imagePath", "cancelAndDeletePreloadData", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleApplication {
    public static final DoodleApplication INSTANCE = new DoodleApplication();
    public static final String SaveFlagNoChange = "no_change";
    public static Context appInstance;

    private DoodleApplication() {
    }

    public final Context getAppInstance() {
        Context context = appInstance;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("appInstance");
        return null;
    }

    public final void setAppInstance(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        appInstance = context;
    }

    @JvmStatic
    public static final void init(Application context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d("DoodleApplication", "init " + context);
        DoodleApplication doodleApplication = INSTANCE;
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        doodleApplication.setAppInstance(applicationContext);
        DoodleCacheManager.INSTANCE.init(doodleApplication.getAppInstance());
        DoodleCacheManager.INSTANCE.clearPreloadCache();
    }

    @JvmStatic
    public static final void preloadData(String imagePath, Context context) {
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        Intrinsics.checkNotNullParameter(context, "context");
        DoodleCacheManager.INSTANCE.preloadData(imagePath, context);
    }

    @JvmStatic
    public static final void cancelAndDeletePreloadData() {
        DoodleCacheManager.INSTANCE.cancelAndDeletePreloadData();
    }
}
