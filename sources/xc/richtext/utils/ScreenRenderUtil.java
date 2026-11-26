package xc.richtext.utils;

import java.util.function.IntSupplier;
import xc.richtext.api.ScreenRenderProvider;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenRenderUtil {
    private static IntSupplier getToolViewHeightFn;
    private static ScreenRenderProvider provider;

    public static ScreenRenderProvider getProvider() {
        return provider;
    }

    public static void setProvider(ScreenRenderProvider screenRenderProvider) {
        provider = screenRenderProvider;
    }

    public static void release() {
        provider = null;
        getToolViewHeightFn = null;
    }

    public static int getToolViewHeight() {
        IntSupplier intSupplier = getToolViewHeightFn;
        if (intSupplier != null) {
            return intSupplier.getAsInt();
        }
        return 0;
    }

    public static void setGetToolViewHeight(IntSupplier intSupplier) {
        getToolViewHeightFn = intSupplier;
    }
}
