package xc.engine.math.iink.api;

import com.miui.common.stat.NotesTextStat;
import kotlin.Metadata;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IHistoryManager;", "", NotesTextStat.UNDO, "", NotesTextStat.REDO, "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IHistoryManager {
    void redo();

    void undo();
}
