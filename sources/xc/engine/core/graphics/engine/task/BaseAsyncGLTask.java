package xc.engine.core.graphics.engine.task;

import android.opengl.GLES30;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.structs.TileRenderResource;

/* JADX INFO: compiled from: BaseAsyncGLTask.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0004R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\n\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001c"}, d2 = {"Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CallMethod.ARG_TASK_ID, "", "getTaskId", "createTimeInMilli", "", "getCreateTimeInMilli", "()J", "syncHandle", "getSyncHandle", "setSyncHandle", "(J)V", "bNeedRefreshRender", "", "getBNeedRefreshRender", "()Z", "setBNeedRefreshRender", "(Z)V", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "createSyncHandle", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseAsyncGLTask {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private long syncHandle;
    private final int taskId = idCounter.incrementAndGet();
    private final long createTimeInMilli = Instant.now().toEpochMilli();
    private boolean bNeedRefreshRender = true;

    public boolean execute(TileRenderResource res) {
        Intrinsics.checkNotNullParameter(res, "res");
        return false;
    }

    public void onRenderCompleted() {
    }

    public final int getTaskId() {
        return this.taskId;
    }

    public final long getCreateTimeInMilli() {
        return this.createTimeInMilli;
    }

    public final long getSyncHandle() {
        return this.syncHandle;
    }

    public final void setSyncHandle(long j) {
        this.syncHandle = j;
    }

    public final boolean getBNeedRefreshRender() {
        return this.bNeedRefreshRender;
    }

    public final void setBNeedRefreshRender(boolean z) {
        this.bNeedRefreshRender = z;
    }

    protected final void createSyncHandle() {
        this.syncHandle = GLES30.glFenceSync(37143, 0);
    }
}
