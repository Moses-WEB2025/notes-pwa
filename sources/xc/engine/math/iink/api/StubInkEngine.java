package xc.engine.math.iink.api;

import android.content.Context;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: StubInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 82\u00020\u0001:\u00018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\n\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\rH\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\rH\u0016J+\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00172\u0006\u0010\u001a\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u001bJ\n\u0010\u001c\u001a\u0004\u0018\u00010\rH\u0016J\u001b\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\u0006\u0010\u0013\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u001eJ\"\u0010\u001f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\rH\u0016J\u001a\u0010\"\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010 \u001a\u00020\rH\u0016J\b\u0010#\u001a\u00020\u0011H\u0016J\b\u0010$\u001a\u00020\u0011H\u0016J\b\u0010%\u001a\u00020\u0011H\u0016J\b\u0010&\u001a\u00020\u0011H\u0016J\u001b\u0010'\u001a\u00020\u00112\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\r0\u0017H\u0016¢\u0006\u0002\u0010)J1\u0010*\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00172\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00190\u0017H\u0016¢\u0006\u0002\u0010-J%\u0010.\u001a\u0004\u0018\u00010\r2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\u0006\u0010!\u001a\u00020\rH\u0016¢\u0006\u0002\u0010/J\u0010\u00100\u001a\u00020\u00112\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00112\u0006\u00101\u001a\u000202H\u0016J\n\u00104\u001a\u0004\u0018\u000105H\u0016J\n\u00106\u001a\u0004\u0018\u000107H\u0016¨\u00069"}, d2 = {"Lxc/engine/math/iink/api/StubInkEngine;", "Lxc/engine/math/iink/api/IInkEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "initializeEngine", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getContentPackage", "", "prepareRecognition", "openOrCreateForEnt", "entFilePath", "", "save", "saveToTemp", "closePackage", "", "setJIIXData", "blockId", "data", "getJIIXData", "addStrokes", "", "pointerEvents", "Lxc/engine/math/iink/api/IPointerEvent;", "isUndo", "([Lxc/engine/math/iink/api/IPointerEvent;Z)[Ljava/lang/String;", "getRecognitionResult", "getAvailableActions", "(Ljava/lang/String;)[Ljava/lang/String;", "getActionOutput", "action", "mimeType", "getDiagnostic", "clear", SplitLayout.TAG_CLOSE, NotesTextStat.UNDO, NotesTextStat.REDO, "erase", "strokeIds", "([Ljava/lang/String;)V", "replaceStrokes", "strokes", "pointEvents", "([Ljava/lang/String;[Lxc/engine/math/iink/api/IPointerEvent;)[Ljava/lang/String;", "export", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "addListener", "listener", "Lxc/engine/math/iink/api/IInkEditorListener;", "removeListener", "createItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "getHistoryManager", "Lxc/engine/math/iink/api/IHistoryManager;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StubInkEngine implements IInkEngine {
    private static final String TAG = "StubInkEngine";

    @Override // xc.engine.math.iink.api.IInkEngine
    public Object getContentPackage() {
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public boolean initializeEngine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.w(TAG, "StubInkEngine: initializeEngine called but iink.aar not available");
        return false;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public boolean prepareRecognition(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.w(TAG, "StubInkEngine: prepareRecognition called but iink.aar not available");
        return false;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public boolean openOrCreateForEnt(String entFilePath) {
        Intrinsics.checkNotNullParameter(entFilePath, "entFilePath");
        Log.w(TAG, "StubInkEngine: openOrCreateForEnt called but iink.aar not available");
        return false;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public boolean save() {
        Log.w(TAG, "StubInkEngine: save called but iink.aar not available");
        return false;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public boolean saveToTemp() {
        Log.w(TAG, "StubInkEngine: saveToTemp called but iink.aar not available");
        return false;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void closePackage() {
        Log.w(TAG, "StubInkEngine: closePackage called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void setJIIXData(String blockId, String data) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        Intrinsics.checkNotNullParameter(data, "data");
        Log.w(TAG, "StubInkEngine: setJIIXData(blockId: String, data: String)");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String getJIIXData(String blockId) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        return "";
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String[] addStrokes(IPointerEvent[] pointerEvents, boolean isUndo) {
        Intrinsics.checkNotNullParameter(pointerEvents, "pointerEvents");
        Log.w(TAG, "StubInkEngine: addStrokes called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String getRecognitionResult() {
        Log.w(TAG, "StubInkEngine: getRecognitionResult called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String[] getAvailableActions(String blockId) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        Log.w(TAG, "StubInkEngine: getAvailableActions called but iink.aar not available");
        return new String[0];
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String getActionOutput(String blockId, String action, String mimeType) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(mimeType, "mimeType");
        Log.w(TAG, "StubInkEngine: getActionOutput called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String getDiagnostic(String blockId, String action) {
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        Intrinsics.checkNotNullParameter(action, "action");
        Log.w(TAG, "StubInkEngine: getDiagnostic called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void clear() {
        Log.w(TAG, "StubInkEngine: clear called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void close() {
        Log.w(TAG, "StubInkEngine: close called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void undo() {
        Log.w(TAG, "StubInkEngine: undo called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void redo() {
        Log.w(TAG, "StubInkEngine: redo called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void erase(String[] strokeIds) {
        Intrinsics.checkNotNullParameter(strokeIds, "strokeIds");
        Log.w(TAG, "StubInkEngine: erase called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String[] replaceStrokes(String[] strokes, IPointerEvent[] pointEvents) {
        Intrinsics.checkNotNullParameter(strokes, "strokes");
        Intrinsics.checkNotNullParameter(pointEvents, "pointEvents");
        Log.w(TAG, "StubInkEngine: replaceStrokes called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public String export(String[] strokeIds, String mimeType) {
        Intrinsics.checkNotNullParameter(strokeIds, "strokeIds");
        Intrinsics.checkNotNullParameter(mimeType, "mimeType");
        Log.w(TAG, "StubInkEngine: export called but iink.aar not available");
        return null;
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void addListener(IInkEditorListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Log.w(TAG, "StubInkEngine: addListener called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public void removeListener(IInkEditorListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Log.w(TAG, "StubInkEngine: removeListener called but iink.aar not available");
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public IItemIdHelper createItemIdHelper() {
        Log.w(TAG, "StubInkEngine: createItemIdHelper called but iink.aar not available");
        return new StubItemIdHelper();
    }

    @Override // xc.engine.math.iink.api.IInkEngine
    public IHistoryManager getHistoryManager() {
        Log.w(TAG, "StubInkEngine: getHistoryManager called but iink.aar not available");
        return new StubHistoryManager();
    }
}
