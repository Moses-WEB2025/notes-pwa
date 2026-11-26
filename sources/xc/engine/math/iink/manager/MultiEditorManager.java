package xc.engine.math.iink.manager;

import android.content.Context;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.math.iink.api.IInkEditorListener;
import xc.engine.math.iink.api.IInkEngine;
import xc.engine.math.iink.api.IItemIdHelper;
import xc.engine.math.iink.api.InkEngineManager;

/* JADX INFO: compiled from: MultiEditorManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000  2\u00020\u0001:\u0001 B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\bJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\tJ\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0019\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\bJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\bJ\u0016\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/math/iink/manager/MultiEditorManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instanceId", "", "editorMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lxc/engine/math/iink/api/IInkEngine;", "editorToEntPath", "savedContext", "Landroid/content/Context;", "initializeEngine", "", CoreConstants.CONTEXT_SCOPE_VALUE, "openOrCreateForEnt", "entFilePath", "getEditor", "getEntPath", "editor", "getItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "entPath", "saveContent", "saveToTemp", "closeEditor", "", "removeListener", "listener", "Lxc/engine/math/iink/api/IInkEditorListener;", "dispose", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MultiEditorManager {
    private static final String TAG = "MultiEditorManager";
    private volatile Context savedContext;
    private final int instanceId = hashCode();
    private final ConcurrentHashMap<String, IInkEngine> editorMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<IInkEngine, String> editorToEntPath = new ConcurrentHashMap<>();

    public final boolean initializeEngine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.savedContext = context.getApplicationContext();
        Log.d(TAG, "[" + this.instanceId + "] initializeEngine: context saved");
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v8, types: [kotlin.Unit] */
    public final boolean openOrCreateForEnt(Context context, String entFilePath) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        Context applicationContext = context.getApplicationContext();
        this.savedContext = applicationContext;
        if (this.editorMap.containsKey(entFilePath)) {
            Log.d(TAG, "[" + this.instanceId + "] openOrCreateForEnt: editor already exists for " + entFilePath);
            return true;
        }
        try {
            IInkEngine iInkEngineCreateEngine = InkEngineManager.INSTANCE.createEngine();
            if (iInkEngineCreateEngine == null) {
                Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: failed to create engine");
                return false;
            }
            Intrinsics.checkNotNull(applicationContext);
            if (!iInkEngineCreateEngine.initializeEngine(applicationContext)) {
                Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: initializeEngine failed for " + entFilePath);
                return false;
            }
            if (!iInkEngineCreateEngine.prepareRecognition(applicationContext)) {
                Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: prepareRecognition failed for " + entFilePath);
                return false;
            }
            boolean zOpenOrCreateForEnt = iInkEngineCreateEngine.openOrCreateForEnt(entFilePath);
            if (zOpenOrCreateForEnt) {
                this.editorMap.put(entFilePath, iInkEngineCreateEngine);
                this.editorToEntPath.put(iInkEngineCreateEngine, entFilePath);
                Log.d(TAG, "[" + this.instanceId + "] openOrCreateForEnt: success for " + entFilePath);
            } else {
                Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: openOrCreateForEnt failed for " + entFilePath);
                try {
                    iInkEngineCreateEngine.close();
                    this = Unit.INSTANCE;
                } catch (Exception e) {
                    Integer.valueOf(Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: error closing failed engine", e));
                }
            }
            return zOpenOrCreateForEnt;
        } catch (Exception e2) {
            Log.e(TAG, "[" + this.instanceId + "] openOrCreateForEnt: error for " + entFilePath, e2);
            return false;
        }
    }

    public final IInkEngine getEditor(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        return this.editorMap.get(entFilePath);
    }

    public final String getEntPath(IInkEngine editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        return this.editorToEntPath.get(editor);
    }

    public final IItemIdHelper getItemIdHelper(String entPath) {
        if (entPath == null) {
            Log.w(TAG, "[" + this.instanceId + "] getItemIdHelper: entPath is null, cannot get ItemIdHelper");
            return null;
        }
        IInkEngine iInkEngine = this.editorMap.get(entPath);
        if (iInkEngine != null) {
            return iInkEngine.createItemIdHelper();
        }
        return null;
    }

    public final boolean saveContent(String entPath) {
        if (entPath == null) {
            Log.w(TAG, "[" + this.instanceId + "] saveContent: entPath is null, cannot save");
            return false;
        }
        IInkEngine iInkEngine = this.editorMap.get(entPath);
        if (iInkEngine != null) {
            return iInkEngine.save();
        }
        return false;
    }

    public final boolean saveToTemp(String entPath) {
        if (entPath == null) {
            Log.w(TAG, "[" + this.instanceId + "] saveToTemp: entPath is null, cannot save");
            return false;
        }
        IInkEngine iInkEngine = this.editorMap.get(entPath);
        if (iInkEngine != null) {
            return iInkEngine.saveToTemp();
        }
        return false;
    }

    public final void closeEditor(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        IInkEngine iInkEngineRemove = this.editorMap.remove(entFilePath);
        if (iInkEngineRemove != null) {
            this.editorToEntPath.remove(iInkEngineRemove);
            try {
                iInkEngineRemove.closePackage();
                Log.d(TAG, "[" + this.instanceId + "] closeEditor: closed editor for " + entFilePath);
                return;
            } catch (Exception e) {
                Log.e(TAG, "[" + this.instanceId + "] closeEditor: error closing editor for " + entFilePath, e);
                return;
            }
        }
        Log.w(TAG, "[" + this.instanceId + "] closeEditor: editor not found for " + entFilePath);
    }

    public final void removeListener(String entFilePath, IInkEditorListener listener) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        Intrinsics.checkNotNullParameter(listener, "listener");
        IInkEngine iInkEngine = this.editorMap.get(entFilePath);
        if (iInkEngine != null) {
            iInkEngine.removeListener(listener);
        }
    }

    public final void dispose() {
        Log.d(TAG, "[" + this.instanceId + "] dispose: cleaning up " + this.editorMap.size() + " editors");
        Collection<IInkEngine> collectionValues = this.editorMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        for (IInkEngine iInkEngine : collectionValues) {
            try {
                String str = this.editorToEntPath.get(iInkEngine);
                iInkEngine.closePackage();
                iInkEngine.close();
                Log.d(TAG, "[" + this.instanceId + "] dispose: closed editor for " + str);
            } catch (Exception e) {
                Log.e(TAG, "[" + this.instanceId + "] dispose: error closing editor", e);
            }
        }
        this.editorMap.clear();
        this.editorToEntPath.clear();
        this.savedContext = null;
        Log.d(TAG, "[" + this.instanceId + "] dispose: completed");
    }
}
