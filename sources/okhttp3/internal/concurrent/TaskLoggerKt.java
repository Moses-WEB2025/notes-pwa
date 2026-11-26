package okhttp3.internal.concurrent;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: TaskLogger.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0000\u001a.\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0080\bø\u0001\u0000\u001a9\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\bH\u0080\bø\u0001\u0000¢\u0006\u0002\u0010\r\u001a$\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0002\u001a\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"taskLog", "", "Ljava/util/logging/Logger;", "task", "Lokhttp3/internal/concurrent/Task;", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "messageBlock", "Lkotlin/Function0;", "", "logElapsed", ExifInterface.GPS_DIRECTION_TRUE, "block", "(Ljava/util/logging/Logger;Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "log", "message", "formatDuration", "ns", "", "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class TaskLoggerKt {
    public static final void taskLog(Logger logger, Task task, TaskQueue queue, Function0<String> messageBlock) {
        Intrinsics.checkNotNullParameter(logger, "<this>");
        Intrinsics.checkNotNullParameter(task, "task");
        Intrinsics.checkNotNullParameter(queue, "queue");
        Intrinsics.checkNotNullParameter(messageBlock, "messageBlock");
        if (logger.isLoggable(Level.FINE)) {
            log(logger, task, queue, messageBlock.invoke());
        }
    }

    public static final <T> T logElapsed(Logger logger, Task task, TaskQueue queue, Function0<? extends T> block) {
        long jNanoTime;
        Intrinsics.checkNotNullParameter(logger, "<this>");
        Intrinsics.checkNotNullParameter(task, "task");
        Intrinsics.checkNotNullParameter(queue, "queue");
        Intrinsics.checkNotNullParameter(block, "block");
        boolean zIsLoggable = logger.isLoggable(Level.FINE);
        if (zIsLoggable) {
            jNanoTime = queue.getTaskRunner().getBackend().nanoTime();
            log(logger, task, queue, "starting");
        } else {
            jNanoTime = -1;
        }
        try {
            T tInvoke = block.invoke();
            if (zIsLoggable) {
                long jNanoTime2 = queue.getTaskRunner().getBackend().nanoTime() - jNanoTime;
                StringBuilder sb = new StringBuilder("finished run in ");
            }
            return tInvoke;
        } finally {
            if (zIsLoggable) {
                log(logger, task, queue, "failed a run in " + formatDuration(queue.getTaskRunner().getBackend().nanoTime() - jNanoTime));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void log(Logger logger, Task task, TaskQueue taskQueue, String str) {
        StringBuilder sbAppend = new StringBuilder().append(taskQueue.getName()).append(' ');
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format("%-22s", Arrays.copyOf(new Object[]{str}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        logger.fine(sbAppend.append(str2).append(": ").append(task.getName()).toString());
    }

    public static final String formatDuration(long j) {
        String str;
        if (j <= -999500000) {
            str = ((j - ((long) 500000000)) / ((long) 1000000000)) + " s ";
        } else if (j <= -999500) {
            str = ((j - ((long) 500000)) / ((long) 1000000)) + " ms";
        } else if (j <= 0) {
            str = ((j - ((long) 500)) / ((long) 1000)) + " µs";
        } else if (j >= 999500) {
            str = j < 999500000 ? ((j + ((long) 500000)) / ((long) 1000000)) + " ms" : ((j + ((long) 500000000)) / ((long) 1000000000)) + " s ";
        } else {
            str = ((j + ((long) 500)) / ((long) 1000)) + " µs";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format("%6s", Arrays.copyOf(new Object[]{str}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        return str2;
    }
}
