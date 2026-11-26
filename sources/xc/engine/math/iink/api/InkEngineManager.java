package xc.engine.math.iink.api;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.FeatureManager;

/* JADX INFO: compiled from: InkEngineManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\u0006\u001a\u00020\u0007J\n\u0010\r\u001a\u0004\u0018\u00010\tH\u0002J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lxc/engine/math/iink/api/InkEngineManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "hasIinkDependency", "", "engineFactory", "Lxc/engine/math/iink/api/IInkEngineFactory;", "initialize", "", "getEngineFactory", "createRealInkEngineFactory", "createEngine", "Lxc/engine/math/iink/api/IInkEngine;", "createPointerEvent", "Lxc/engine/math/iink/api/IPointerEvent;", "createItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "engine", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class InkEngineManager {
    public static final InkEngineManager INSTANCE = new InkEngineManager();
    private static final String TAG = "InkEngineManager";
    private static IInkEngineFactory engineFactory;
    private static final boolean hasIinkDependency = false;

    private InkEngineManager() {
    }

    public final void initialize() {
        StubInkEngineFactory stubInkEngineFactory;
        boolean z = hasIinkDependency;
        if (z) {
            Log.d(TAG, "iink.aar dependency detected (from BuildConfig)");
        } else {
            Log.d(TAG, "iink.aar dependency not available (from BuildConfig), using stub implementation");
        }
        if (z && FeatureManager.getInstance().isEnableMathRecognition()) {
            stubInkEngineFactory = createRealInkEngineFactory();
        } else {
            stubInkEngineFactory = new StubInkEngineFactory();
        }
        engineFactory = stubInkEngineFactory;
    }

    public final IInkEngineFactory getEngineFactory() {
        if (engineFactory == null) {
            initialize();
        }
        IInkEngineFactory iInkEngineFactory = engineFactory;
        Intrinsics.checkNotNull(iInkEngineFactory);
        return iInkEngineFactory;
    }

    public final boolean hasIinkDependency() {
        return hasIinkDependency;
    }

    private final IInkEngineFactory createRealInkEngineFactory() {
        try {
            Object objNewInstance = Class.forName("xc.engine.math.iink.api.RealInkEngineFactory").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type xc.engine.math.iink.api.IInkEngineFactory");
            return (IInkEngineFactory) objNewInstance;
        } catch (Throwable th) {
            Log.e(TAG, "Failed to create real ink engine factory", th);
            return new StubInkEngineFactory();
        }
    }

    public final IInkEngine createEngine() {
        return getEngineFactory().createEngine();
    }

    public final IPointerEvent createPointerEvent() {
        return getEngineFactory().createPointerEvent();
    }

    public final IItemIdHelper createItemIdHelper(IInkEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        return getEngineFactory().createItemIdHelper(engine);
    }
}
