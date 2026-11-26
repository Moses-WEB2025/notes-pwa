package xc.engine.core.graphics.engine;

import android.content.Context;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.gl.GLEnv;

/* JADX INFO: compiled from: MiCurveEngineCore.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"renderToBitmap", "", "dataList", "", "Lxc/engine/core/graphics/engine/RenderData;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "libXCEngine_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class MiCurveEngineCoreKt {
    public static final void renderToBitmap(final List<RenderData> dataList, final Context context) throws InterruptedException {
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Intrinsics.checkNotNullParameter(context, "context");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: xc.engine.core.graphics.engine.MiCurveEngineCoreKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                MiCurveEngineCoreKt.renderToBitmap$lambda$1(dataList, context, countDownLatch);
            }
        }).start();
        countDownLatch.await();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderToBitmap$lambda$1(List list, Context context, CountDownLatch countDownLatch) throws Throwable {
        GLEnv gLEnv = new GLEnv();
        if (gLEnv.initGLEnv()) {
            gLEnv.makeCurrent();
            Log.d("Engine", "xl-- render dataList size: " + list.size());
            MiCurveEngineCore miCurveEngineCore = new MiCurveEngineCore();
            miCurveEngineCore.init(context);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                miCurveEngineCore.renderToBitmap((RenderData) it.next());
            }
            countDownLatch.countDown();
            Log.d("Engine", "xl-- render complete");
            miCurveEngineCore.destroy();
            gLEnv.onDestroy();
            return;
        }
        Log.e("Engine", "xl- egl init failed");
    }
}
