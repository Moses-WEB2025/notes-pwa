package xc.engine.api;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDActionThread;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.algorithm.AlgorithmNative;

/* JADX INFO: loaded from: classes6.dex */
public class CreationPageSDK {
    public static final int A4_HEIGHT = 1156;
    public static final int A4_WIDTH = 755;
    public static final String ALL_POINT_LIMIT = "all pages points over limit";
    public static final int BITMAP_MAX_BYTE_COUNT = 29360128;
    public static final int EXPORT_SIZE = 1440;
    public static final int EXPORT_SIZE_UPPER_LIMIT = 7000;
    public static final int EXPORT_SIZE_UPPER_LIMIT_LITE = 6000;
    public static final int MAX_PAGE = 500;
    public static final int PAD_PAGE_MAX = 500;
    public static final String PAGE_POINT_LIMIT = "singe page points over limit";
    public static final int REDRAW_POOL_SIZE = 2;
    public static final int TEXT_COLOR_DARK = -1;
    public static final int TEXT_COLOR_LIGHT = -16777216;
    public static final int TEXT_MAX_COUNT = 100;
    public static final float TEXT_SIZE = 34.0f;
    public static final int TOTAL_MAX_POINTS = 20000000;
    public static Application sApplication = null;
    public static DrawErrorCallback sErrorCallback = null;
    public static ThreadPoolExecutor sExecutorService = null;
    public static Handler sHandler = null;
    private static boolean sIsSuperLite = false;
    public static float sOffX;
    private static final Map<Context, Object> sTokenMap = new WeakHashMap();

    public static void init(Application application, boolean z) {
        sApplication = application;
        PDFBoxResourceLoader.init(application);
        sHandler = new Handler(Looper.getMainLooper());
        sIsSuperLite = z;
        sExecutorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: xc.engine.api.CreationPageSDK$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return CreationPageSDK.lambda$init$0(runnable);
            }
        });
    }

    static /* synthetic */ Thread lambda$init$0(Runnable runnable) {
        return new Thread(runnable, PDActionThread.SUB_TYPE);
    }

    public static int getMaxPoints() {
        return sIsSuperLite ? 200000 : 500000;
    }

    public static void stopAlgorithmEngine() {
        AlgorithmNative.getInstance().onStop();
    }

    static /* synthetic */ Object lambda$setToken$1(Context context) {
        return new Object();
    }

    public static void setToken(Context context) {
        if (context == null) {
            return;
        }
        sTokenMap.computeIfAbsent(context, new Function() { // from class: xc.engine.api.CreationPageSDK$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CreationPageSDK.lambda$setToken$1((Context) obj);
            }
        });
    }

    public static Object getToken(Context context) {
        if (context == null) {
            return null;
        }
        return sTokenMap.getOrDefault(context, null);
    }

    public static void removeToken(Context context) {
        if (context == null) {
            return;
        }
        sTokenMap.remove(context);
    }

    public static void clearHandler(Object obj) {
        Handler handler = sHandler;
        if (handler == null || obj == null) {
            return;
        }
        handler.removeCallbacksAndMessages(obj);
    }

    public static TokenProxy with(Context context) {
        return new TokenProxy(context, sHandler);
    }

    public static class TokenProxy {
        private final WeakReference<Context> mContextRef;
        private final Handler mHandler;
        private final Object mToken;

        public TokenProxy(Context context, Handler handler) {
            this.mContextRef = new WeakReference<>(context);
            this.mHandler = handler;
            this.mToken = CreationPageSDK.getToken(context);
        }

        public void postDelayed(Runnable runnable, long j) {
            if (this.mContextRef.get() == null) {
                return;
            }
            this.mHandler.postDelayed(wrapRunnable(runnable), this.mToken, j);
        }

        public void post(Runnable runnable) {
            postDelayed(runnable, 0L);
        }

        public void removeCallbacks(Runnable runnable) {
            if (this.mContextRef.get() == null) {
                return;
            }
            this.mHandler.removeCallbacks(runnable, this.mToken);
        }

        private Runnable wrapRunnable(final Runnable runnable) {
            return new Runnable() { // from class: xc.engine.api.CreationPageSDK$TokenProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4595lambda$wrapRunnable$0$xcengineapiCreationPageSDK$TokenProxy(runnable);
                }
            };
        }

        /* JADX INFO: renamed from: lambda$wrapRunnable$0$xc-engine-api-CreationPageSDK$TokenProxy, reason: not valid java name */
        /* synthetic */ void m4595lambda$wrapRunnable$0$xcengineapiCreationPageSDK$TokenProxy(Runnable runnable) {
            if (this.mContextRef.get() == null) {
                return;
            }
            runnable.run();
        }
    }
}
