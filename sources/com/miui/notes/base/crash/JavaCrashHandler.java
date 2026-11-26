package com.miui.notes.base.crash;

import android.content.Context;
import android.os.Process;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.DeviceUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: JavaCrashHandler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 &2\u00020\u0001:\u0001&B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u0012J\u0018\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u0012\u0010\"\u001a\u00020#2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0010\u0010$\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010%\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R(\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006'"}, d2 = {"Lcom/miui/notes/base/crash/JavaCrashHandler;", "Ljava/lang/Thread$UncaughtExceptionHandler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mContext", "Landroid/content/Context;", "getMContext", "()Landroid/content/Context;", "setMContext", "(Landroid/content/Context;)V", "mHandler", "getMHandler", "()Ljava/lang/Thread$UncaughtExceptionHandler;", "setMHandler", "(Ljava/lang/Thread$UncaughtExceptionHandler;)V", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "", "getCallback", "()Lkotlin/jvm/functions/Function1;", "setCallback", "(Lkotlin/jvm/functions/Function1;)V", "lineFormat", "getLineFormat", "()Ljava/lang/String;", "init", CoreConstants.CONTEXT_SCOPE_VALUE, "uncaughtException", "thread", "Ljava/lang/Thread;", "ex", "", "handleException", "", "getLogSummary", "getTrace", "Companion", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class JavaCrashHandler implements Thread.UncaughtExceptionHandler {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<JavaCrashHandler> instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.miui.notes.base.crash.JavaCrashHandler$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return JavaCrashHandler.instance_delegate$lambda$3();
        }
    });
    private final String TAG = "JavaCrashHandler";
    private Function1<? super String, Unit> callback;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mHandler;

    public final Context getMContext() {
        return this.mContext;
    }

    public final void setMContext(Context context) {
        this.mContext = context;
    }

    public final Thread.UncaughtExceptionHandler getMHandler() {
        return this.mHandler;
    }

    public final void setMHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.mHandler = uncaughtExceptionHandler;
    }

    public final Function1<String, Unit> getCallback() {
        return this.callback;
    }

    public final void setCallback(Function1<? super String, Unit> function1) {
        this.callback = function1;
    }

    private final String getLineFormat() {
        return ">>>>%-18s : %s \r\n";
    }

    /* JADX INFO: compiled from: JavaCrashHandler.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/miui/notes/base/crash/JavaCrashHandler$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/notes/base/crash/JavaCrashHandler;", "getInstance", "()Lcom/miui/notes/base/crash/JavaCrashHandler;", "instance$delegate", "Lkotlin/Lazy;", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaCrashHandler getInstance() {
            return (JavaCrashHandler) JavaCrashHandler.instance$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JavaCrashHandler instance_delegate$lambda$3() {
        return new JavaCrashHandler();
    }

    public final void init(Context context, Function1<? super String, Unit> callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Logger.INSTANCE.d(this.TAG, "init");
        this.callback = callback;
        this.mContext = context;
        this.mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable ex) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        Intrinsics.checkNotNullParameter(thread, "thread");
        Intrinsics.checkNotNullParameter(ex, "ex");
        if (!handleException(ex) && (uncaughtExceptionHandler = this.mHandler) != null) {
            Intrinsics.checkNotNull(uncaughtExceptionHandler);
            uncaughtExceptionHandler.uncaughtException(thread, ex);
            return;
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            Logger.INSTANCE.e(this.TAG, "error : " + e);
        }
        Logger.INSTANCE.e(this.TAG, "bye bye");
        Process.killProcess(Process.myPid());
        System.exit(0);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }

    private final boolean handleException(Throwable ex) {
        if (ex != null) {
            ex.printStackTrace();
        }
        Logger.INSTANCE.e(this.TAG, "handleException " + (ex != null ? ExceptionsKt.stackTraceToString(ex) : null));
        if (ex == null) {
            return false;
        }
        String logSummary = getLogSummary(ex);
        Logger.INSTANCE.e(this.TAG, "saveErrorInfo2File " + ExceptionsKt.stackTraceToString(ex));
        Function1<? super String, Unit> function1 = this.callback;
        if (function1 != null) {
            function1.invoke(logSummary);
        }
        return ((ex instanceof IOException) && Intrinsics.areEqual(((IOException) ex).getMessage(), "write failed: ENOSPC (No space left on device)")) ? false : true;
    }

    private final String getLogSummary(Throwable ex) {
        StringBuffer stringBuffer = new StringBuffer();
        Context context = this.mContext;
        if (context != null) {
            stringBuffer.append(">>>>环境信息 :\n");
            Set<Map.Entry<String, String>> setEntrySet = DeviceUtils.INSTANCE.buildEnvInfo(context).entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String str = String.format(getLineFormat(), Arrays.copyOf(new Object[]{entry.getKey(), entry.getValue()}, 2));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                stringBuffer.append(str);
            }
            stringBuffer.append("<<<<\r\n");
        }
        stringBuffer.append(">>>>异常进程 : " + Process.myPid() + StringUtils.LF);
        stringBuffer.append(">>>>异常类名 : " + ex.getClass() + StringUtils.LF);
        stringBuffer.append(">>>>异常信息 : " + ex.getMessage() + StringUtils.LF);
        stringBuffer.append(">>>>异常线程 : " + Thread.currentThread().getId() + StringUtils.LF);
        stringBuffer.append(">>>>堆栈信息 : ");
        stringBuffer.append(getTrace(ex));
        stringBuffer.append("<<<<\n");
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trim((CharSequence) string).toString();
    }

    private final String getTrace(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        return stringWriter.toString();
    }
}
