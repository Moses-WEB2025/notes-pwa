package com.miui.handwriting.common.config;

import android.content.Context;
import android.util.Log;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000b\u001a\u0004\u0018\u00010\nJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/handwriting/common/config/CommonConfig;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "TOOLBAR_CONFIG", "gson", "Lcom/google/gson/Gson;", "toolbarConfig", "Lcom/miui/handwriting/common/config/ToolbarConfig;", "getToolbarConfig", "showViewByConfig", "", "showType", "", "view", "Landroid/view/View;", "initConfig", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CommonConfig {
    private static final String TAG = "CommonConfig";
    private static final String TOOLBAR_CONFIG = "config-handwriting-toolbar.json";
    private static ToolbarConfig toolbarConfig;
    public static final CommonConfig INSTANCE = new CommonConfig();
    private static Gson gson = new Gson();

    private CommonConfig() {
    }

    public final ToolbarConfig getToolbarConfig() {
        return toolbarConfig;
    }

    public final void showViewByConfig(int showType, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (showType == 0) {
            view.setVisibility(0);
        } else if (showType == 1) {
            view.setVisibility(4);
        } else {
            if (showType != 2) {
                return;
            }
            view.setVisibility(8);
        }
    }

    public final void initConfig(Context context) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            InputStream inputStreamOpen = context.getAssets().open(TOOLBAR_CONFIG);
            try {
                toolbarConfig = (ToolbarConfig) gson.fromJson((Reader) new InputStreamReader(inputStreamOpen, Charset.forName("UTF-8")), ToolbarConfig.class);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStreamOpen, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(inputStreamOpen, th);
                    throw th2;
                }
            }
        } catch (Error e) {
            Log.e(TAG, "get assets fail: " + e);
        }
    }
}
