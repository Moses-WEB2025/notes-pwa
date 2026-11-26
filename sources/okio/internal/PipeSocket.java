package okio.internal;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Pipe;
import okio.Sink;
import okio.Socket;
import okio.Source;

/* JADX INFO: compiled from: PipeSocket.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0014"}, d2 = {"Lokio/internal/PipeSocket;", "Lokio/Socket;", "sinkPipe", "Lokio/Pipe;", "sourcePipe", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/Pipe;Lokio/Pipe;)V", "getSinkPipe", "()Lokio/Pipe;", "getSourcePipe", "source", "Lokio/Source;", "getSource", "()Lokio/Source;", "sink", "Lokio/Sink;", "getSink", "()Lokio/Sink;", ToDoStat.CANCEL, "", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PipeSocket implements Socket {
    private final Pipe sinkPipe;
    private final Pipe sourcePipe;

    public PipeSocket(Pipe sinkPipe, Pipe sourcePipe) {
        Intrinsics.checkNotNullParameter(sinkPipe, "sinkPipe");
        Intrinsics.checkNotNullParameter(sourcePipe, "sourcePipe");
        this.sinkPipe = sinkPipe;
        this.sourcePipe = sourcePipe;
    }

    public final Pipe getSinkPipe() {
        return this.sinkPipe;
    }

    public final Pipe getSourcePipe() {
        return this.sourcePipe;
    }

    @Override // okio.Socket
    public Source getSource() {
        return this.sourcePipe.source();
    }

    @Override // okio.Socket
    public Sink getSink() {
        return this.sinkPipe.sink();
    }

    @Override // okio.Socket
    public void cancel() {
        this.sourcePipe.cancel();
        this.sinkPipe.cancel();
    }
}
