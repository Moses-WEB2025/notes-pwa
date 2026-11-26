package xc.engine.math.iink.api;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.miui.common.stat.NotesTextStat;
import kotlin.Metadata;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0001H&J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH&J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH&J\b\u0010\u0010\u001a\u00020\u0004H&J\b\u0010\u0011\u001a\u00020\u0004H&J\b\u0010\u0012\u001a\u00020\fH&J+\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0017\u001a\u00020\u0004H&¢\u0006\u0002\u0010\u0018J\n\u0010\u0019\u001a\u0004\u0018\u00010\nH&J\u001b\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\u0006\u0010\r\u001a\u00020\nH&¢\u0006\u0002\u0010\u001bJ\"\u0010\u001c\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH&J\u001a\u0010\u001f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\nH&J\b\u0010 \u001a\u00020\fH&J\b\u0010!\u001a\u00020\fH&J\b\u0010\"\u001a\u00020\fH&J\b\u0010#\u001a\u00020\fH&J\u001b\u0010$\u001a\u00020\f2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\n0\u0014H&¢\u0006\u0002\u0010&J1\u0010'\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00142\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00160\u0014H&¢\u0006\u0002\u0010*J%\u0010+\u001a\u0004\u0018\u00010\n2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\u0006\u0010\u001e\u001a\u00020\nH&¢\u0006\u0002\u0010,J\u0010\u0010-\u001a\u00020\f2\u0006\u0010.\u001a\u00020/H&J\u0010\u00100\u001a\u00020\f2\u0006\u0010.\u001a\u00020/H&J\n\u00101\u001a\u0004\u0018\u000102H&J\n\u00103\u001a\u0004\u0018\u000104H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00065À\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IInkEngine;", "", "getContentPackage", "initializeEngine", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "prepareRecognition", "openOrCreateForEnt", "entFilePath", "", "setJIIXData", "", "blockId", "data", "getJIIXData", "save", "saveToTemp", "closePackage", "addStrokes", "", "pointerEvents", "Lxc/engine/math/iink/api/IPointerEvent;", "isUndo", "([Lxc/engine/math/iink/api/IPointerEvent;Z)[Ljava/lang/String;", "getRecognitionResult", "getAvailableActions", "(Ljava/lang/String;)[Ljava/lang/String;", "getActionOutput", "action", "mimeType", "getDiagnostic", "clear", SplitLayout.TAG_CLOSE, NotesTextStat.UNDO, NotesTextStat.REDO, "erase", "strokeIds", "([Ljava/lang/String;)V", "replaceStrokes", "strokes", "pointEvents", "([Ljava/lang/String;[Lxc/engine/math/iink/api/IPointerEvent;)[Ljava/lang/String;", "export", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "addListener", "listener", "Lxc/engine/math/iink/api/IInkEditorListener;", "removeListener", "createItemIdHelper", "Lxc/engine/math/iink/api/IItemIdHelper;", "getHistoryManager", "Lxc/engine/math/iink/api/IHistoryManager;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IInkEngine {
    void addListener(IInkEditorListener listener);

    String[] addStrokes(IPointerEvent[] pointerEvents, boolean isUndo);

    void clear();

    void close();

    void closePackage();

    IItemIdHelper createItemIdHelper();

    void erase(String[] strokeIds);

    String export(String[] strokeIds, String mimeType);

    String getActionOutput(String blockId, String action, String mimeType);

    String[] getAvailableActions(String blockId);

    Object getContentPackage();

    String getDiagnostic(String blockId, String action);

    IHistoryManager getHistoryManager();

    String getJIIXData(String blockId);

    String getRecognitionResult();

    boolean initializeEngine(Context context);

    boolean openOrCreateForEnt(String entFilePath);

    boolean prepareRecognition(Context context);

    void redo();

    void removeListener(IInkEditorListener listener);

    String[] replaceStrokes(String[] strokes, IPointerEvent[] pointEvents);

    boolean save();

    boolean saveToTemp();

    void setJIIXData(String blockId, String data);

    void undo();
}
