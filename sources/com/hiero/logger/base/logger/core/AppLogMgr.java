package com.hiero.logger.base.logger.core;

import android.os.Process;
import android.util.Log;
import com.hiero.logger.base.logger.Constants;
import com.hiero.logger.base.logger.interfaces.ILogger;
import com.hiero.logger.base.logger.utils.FileLogHelper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0019\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Jt\u0010\u001f\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010(\u001a\u00020 2\b\b\u0002\u0010)\u001a\u00020 2\b\b\u0002\u0010*\u001a\u00020 2\b\b\u0002\u0010+\u001a\u00020\u00072\b\b\u0002\u0010,\u001a\u00020\u00072\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010#\u001a\u00020\u00052\b\b\u0002\u0010.\u001a\u00020 H\u0007J\u001e\u0010\u001f\u001a\u00020$2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u0005J\u000e\u0010/\u001a\u00020$2\u0006\u00100\u001a\u000201J\u001c\u0010\u001f\u001a\u00020$2\u0006\u00102\u001a\u00020\u00052\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u0005H\u0002JN\u00103\u001a\u00020$2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00072\u0006\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020 2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010.\u001a\u00020 J\u0006\u00104\u001a\u00020$J\u0018\u00105\u001a\u00020$2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020 H\u0002J\u000e\u00109\u001a\u00020$2\u0006\u00106\u001a\u000207J\u0006\u0010:\u001a\u00020$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u000e\u0010\u0010\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0017\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u0018\u0010\u0014R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0016\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lcom/hiero/logger/base/logger/core/AppLogMgr;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "MAX_CACHE_SIZE", "", "MAX_LOG_CAPACITY", "getMAX_LOG_CAPACITY", "()I", "setMAX_LOG_CAPACITY", "(I)V", "MAX_FILE_SIZE", "getMAX_FILE_SIZE", "setMAX_FILE_SIZE", "logDir", "mainBuff", "Ljava/nio/CharBuffer;", "getMainBuff", "()Ljava/nio/CharBuffer;", "mainBuff$delegate", "Lkotlin/Lazy;", "tempBuff", "getTempBuff", "tempBuff$delegate", "fileWriteTag", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getFileWriteTag", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "fileWriteTag$delegate", "init", "", "BREAK_IN_FLUSH", "logFileName", "headerInfo", "", "loggerType", "Lcom/hiero/logger/base/logger/core/LoggerType;", "cacheDir", "debug", "consolePrint", "compress", "maxFileSize", "maxLogFilesCapacity", "logName", "fastMode", "trimLogFileWithCapacity", "targetCap", "", "mLogPathDir", "initNative", "flush", "write2BufferInner", "value", "", "writeImmediately", "invokeMethod", "release", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AppLogMgr {
    private static final String BREAK_IN_FLUSH = "-----------------flush-----------------\r\n\r\n";
    private static final int MAX_CACHE_SIZE = 2097152;
    private static final String TAG = "AppLogMgr";
    private static String headerInfo;
    private static boolean init;
    private static volatile String logDir;
    public static final AppLogMgr INSTANCE = new AppLogMgr();
    private static int MAX_LOG_CAPACITY = 15728640;
    private static int MAX_FILE_SIZE = 5242880;

    /* JADX INFO: renamed from: mainBuff$delegate, reason: from kotlin metadata */
    private static final Lazy mainBuff = LazyKt.lazy(new Function0() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AppLogMgr.mainBuff_delegate$lambda$0();
        }
    });

    /* JADX INFO: renamed from: tempBuff$delegate, reason: from kotlin metadata */
    private static final Lazy tempBuff = LazyKt.lazy(new Function0() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AppLogMgr.tempBuff_delegate$lambda$1();
        }
    });

    /* JADX INFO: renamed from: fileWriteTag$delegate, reason: from kotlin metadata */
    private static final Lazy fileWriteTag = LazyKt.lazy(new Function0() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AppLogMgr.fileWriteTag_delegate$lambda$2();
        }
    });
    private static String logFileName = "log.log";

    /* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoggerType.values().length];
            try {
                iArr[LoggerType.ALOG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LoggerType.PHOENIX_LOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.hiero.logger.base.logger.core.AppLogMgr$flush$1, reason: invalid class name */
    /* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.hiero.logger.base.logger.core.AppLogMgr$flush$1", f = "AppLogMgr.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AppLogMgr appLogMgr = AppLogMgr.INSTANCE;
            if (appLogMgr.getMainBuff().position() > 0) {
                Log.d(Constants.MODULE_TAG, " flush file:" + AppLogMgr.logFileName);
                char[] charArray = AppLogMgr.BREAK_IN_FLUSH.toCharArray();
                Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
                appLogMgr.write2BufferInner(charArray, true);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    private AppLogMgr() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AtomicBoolean fileWriteTag_delegate$lambda$2() {
        return new AtomicBoolean(false);
    }

    private final AtomicBoolean getFileWriteTag() {
        return (AtomicBoolean) fileWriteTag.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CharBuffer getMainBuff() {
        Object value = mainBuff.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (CharBuffer) value;
    }

    private final CharBuffer getTempBuff() {
        Object value = tempBuff.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (CharBuffer) value;
    }

    public static /* synthetic */ void init$default(AppLogMgr appLogMgr, LoggerType loggerType, String str, String str2, boolean z, boolean z2, boolean z3, int i, int i2, String str3, String str4, boolean z4, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            loggerType = LoggerType.PHOENIX_LOG;
        }
        if ((i3 & 8) != 0) {
            z = true;
        }
        if ((i3 & 16) != 0) {
            z2 = true;
        }
        if ((i3 & 32) != 0) {
            z3 = true;
        }
        if ((i3 & 64) != 0) {
            i = 5242880;
        }
        if ((i3 & 128) != 0) {
            i2 = MAX_LOG_CAPACITY;
        }
        if ((i3 & 256) != 0) {
            str3 = null;
        }
        if ((i3 & 512) != 0) {
            str4 = "";
        }
        appLogMgr.init(loggerType, str, str2, z, z2, z3, i, i2, str3, str4, (i3 & 1024) != 0 ? true : z4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharBuffer mainBuff_delegate$lambda$0() {
        return CharBuffer.allocate(2097152);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharBuffer tempBuff_delegate$lambda$1() {
        return CharBuffer.allocate(1048576);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean trimLogFileWithCapacity$lambda$4(Ref.LongRef longRef, TreeMap treeMap, long j, Ref.IntRef intRef, File file, String str) {
        boolean z = false;
        if ((str != null && StringsKt.endsWith(str, ".dst", false)) || (str != null && StringsKt.endsWith(str, "-log.zip", false))) {
            z = true;
        }
        if (z) {
            File file2 = new File(file, str);
            longRef.element = file2.length() + longRef.element;
            treeMap.put(Long.valueOf(file2.lastModified()), file2);
            if (longRef.element < j) {
                intRef.element++;
            }
            LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "target " + str + " ,match size:" + file2.length() + " ,total cap:" + longRef.element + " vs " + j);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void write2BufferInner(char[] value, boolean writeImmediately) {
        if (FileLogHelper.INSTANCE.write2Buffer(getMainBuff(), getTempBuff(), value, writeImmediately)) {
            return;
        }
        if (getFileWriteTag().get()) {
            Log.i(Constants.MODULE_TAG, "flush stop when writing: " + getFileWriteTag().get());
            return;
        }
        getFileWriteTag().set(true);
        String str = logFileName;
        int iPosition = getMainBuff().position();
        String str2 = logDir;
        String str3 = null;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
            str2 = null;
        }
        Log.i(Constants.MODULE_TAG, str + " is going to write with buffer size " + iPosition + ",logDir:" + str2 + ",tid " + Thread.currentThread().getId());
        String str4 = logDir;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
            str4 = null;
        }
        File file = new File(str4);
        if (file.exists()) {
            file = null;
        }
        if (file != null) {
            file.mkdirs();
        }
        String str5 = logDir;
        if (str5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
        } else {
            str3 = str5;
        }
        final File file2 = new File(str3, logFileName);
        new Thread(new Runnable() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AppLogMgr.write2BufferInner$lambda$15(file2);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void write2BufferInner$lambda$15(final File file) {
        FileLogHelper fileLogHelper = FileLogHelper.INSTANCE;
        AppLogMgr appLogMgr = INSTANCE;
        fileLogHelper.write2File(file, appLogMgr.getMainBuff(), new Function0() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AppLogMgr.write2BufferInner$lambda$15$lambda$13(file);
            }
        });
        synchronized (appLogMgr.getTempBuff()) {
            Log.e(Constants.MODULE_TAG, "lock temp buff(pos" + appLogMgr.getTempBuff().position() + ") ,tid " + Thread.currentThread().getId());
            int iPosition = appLogMgr.getTempBuff().position();
            appLogMgr.getTempBuff().put(appLogMgr.getTempBuff().array(), 0, iPosition);
            appLogMgr.getTempBuff().clear();
            Log.e(Constants.MODULE_TAG, "unlock temp buff,re-assign " + iPosition + " to buffer,tid " + Thread.currentThread().getId());
        }
        appLogMgr.getFileWriteTag().set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write2BufferInner$lambda$15$lambda$13(File file) {
        if (file.length() > MAX_FILE_SIZE) {
            Log.d(Constants.MODULE_TAG, file.getName() + "  file length > MAX_SIZE,do file split action " + Thread.currentThread().getId());
            FileLogHelper fileLogHelper = FileLogHelper.INSTANCE;
            String str = logDir;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("logDir");
                str = null;
            }
            FileLogHelper.shirkLogWithTimeRolling$default(fileLogHelper, str, file, MAX_LOG_CAPACITY, MAX_FILE_SIZE, null, 16, null);
        }
        return Unit.INSTANCE;
    }

    public final void flush() {
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        ILogger logger = loggerUtils.getLogger();
        PhoenixLogger phoenixLogger = PhoenixLogger.INSTANCE;
        if (Intrinsics.areEqual(logger, phoenixLogger)) {
            phoenixLogger.flush();
        } else if (Intrinsics.areEqual(loggerUtils.getLogger(), ALogUtils.INSTANCE)) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(null), 3, null);
        }
    }

    public final int getMAX_FILE_SIZE() {
        return MAX_FILE_SIZE;
    }

    public final int getMAX_LOG_CAPACITY() {
        return MAX_LOG_CAPACITY;
    }

    public final void init(LoggerType loggerType, String cacheDir, String logDir2, boolean debug, boolean consolePrint, boolean compress, int maxFileSize, int maxLogFilesCapacity, String logName, String headerInfo2, boolean fastMode) {
        String str;
        Intrinsics.checkNotNullParameter(loggerType, "loggerType");
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        Intrinsics.checkNotNullParameter(logDir2, "logDir");
        Intrinsics.checkNotNullParameter(headerInfo2, "headerInfo");
        if (logName != null) {
            logFileName = logName;
        }
        LoggerUtils.INSTANCE.setType(loggerType);
        logDir = logDir2;
        MAX_FILE_SIZE = maxFileSize;
        MAX_LOG_CAPACITY = maxLogFilesCapacity;
        headerInfo = headerInfo2;
        int i = WhenMappings.$EnumSwitchMapping$0[loggerType.ordinal()];
        if (i == 1) {
            init(logDir2, logFileName);
            return;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        if (fastMode) {
            str = logFileName;
        } else {
            str = Process.myProcessName() + "_" + logFileName;
        }
        initNative(str, cacheDir, logDir2, maxFileSize, debug, consolePrint, compress, headerInfo2, fastMode);
    }

    public final void initNative(String logName, String cacheDir, String logDir2, int maxFileSize, boolean debug, boolean consolePrint, boolean compress, String headerInfo2, boolean fastMode) {
        Intrinsics.checkNotNullParameter(logName, "logName");
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        Intrinsics.checkNotNullParameter(logDir2, "logDir");
        Intrinsics.checkNotNullParameter(headerInfo2, "headerInfo");
        new File(cacheDir).mkdirs();
        new File(logDir2).mkdirs();
        PhoenixLogger.INSTANCE.init(logName, cacheDir, logDir2, maxFileSize, debug, consolePrint, compress, headerInfo2, fastMode);
        init = true;
        Log.i(TAG, "initNative finished");
    }

    public final void invokeMethod(char[] value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (init) {
            write2BufferInner(value, false);
        } else {
            Log.e(TAG, "not init!!!!");
        }
    }

    public final void release() {
        if (LoggerUtils.INSTANCE.getType() == LoggerType.PHOENIX_LOG) {
            PhoenixLogger.INSTANCE.uninit();
        }
    }

    public final void setMAX_FILE_SIZE(int i) {
        MAX_FILE_SIZE = i;
    }

    public final void setMAX_LOG_CAPACITY(int i) {
        MAX_LOG_CAPACITY = i;
    }

    public final void trimLogFileWithCapacity(final long targetCap) {
        LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
        String str = logDir;
        String str2 = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
            str = null;
        }
        loggerUtils.d(Constants.MODULE_TAG, TAG, "trimLogFileWithCapacity " + targetCap + " ->" + str + "  " + logFileName);
        final Ref.LongRef longRef = new Ref.LongRef();
        final TreeMap treeMap = new TreeMap();
        final Ref.IntRef intRef = new Ref.IntRef();
        String str3 = logDir;
        if (str3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
        } else {
            str2 = str3;
        }
        new File(str2).listFiles(new FilenameFilter() { // from class: com.hiero.logger.base.logger.core.AppLogMgr$$ExternalSyntheticLambda4
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str4) {
                return AppLogMgr.trimLogFileWithCapacity$lambda$4(longRef, treeMap, targetCap, intRef, file, str4);
            }
        });
        long j = longRef.element;
        if (j <= targetCap) {
            loggerUtils.d(Constants.MODULE_TAG, TAG, "target files  not over limited!! " + j + " vs " + targetCap);
            return;
        }
        int size = treeMap.size() - 1;
        loggerUtils.d(Constants.MODULE_TAG, TAG, "target files over limited!! with overIndex:" + intRef.element);
        Collection collectionValues = treeMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        long j2 = 0;
        int i = 0;
        for (Object obj : collectionValues) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            File file = (File) obj;
            if (i <= size - intRef.element) {
                long length = file.length() + j2;
                file.delete();
                LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "delete old file:" + file.getName());
                j2 = length;
            }
            i = i2;
        }
        LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "trim finished,delete capacity:" + j2 + ", " + (longRef.element - j2) + ")");
    }

    public static /* synthetic */ void init$default(AppLogMgr appLogMgr, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        appLogMgr.init(str, str2);
    }

    public final void init(String cacheDir, String logDir2, String logName) {
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        Intrinsics.checkNotNullParameter(logDir2, "logDir");
        Intrinsics.checkNotNullParameter(logName, "logName");
        init$default(this, null, cacheDir, logDir2, true, false, false, 0, 0, logName, null, false, R2.attr.miuixSubheaderInsetEnd, null);
    }

    private final void init(String mLogPathDir, String logName) {
        logDir = mLogPathDir;
        if (logName != null) {
            logFileName = logName;
        }
        init = true;
        String str = logDir;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logDir");
            str = null;
        }
        Log.d(Constants.MODULE_TAG, "init app log dir=" + str);
    }
}
