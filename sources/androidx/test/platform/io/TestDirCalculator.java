package androidx.test.platform.io;

import android.content.Context;
import android.os.Environment;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TestDirCalculator.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\u0004H\u0002J\b\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\u0004H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006¨\u0006\u000f"}, d2 = {"Landroidx/test/platform/io/TestDirCalculator;", "", "()V", "inputDir", "Ljava/io/File;", "getInputDir", "()Ljava/io/File;", "inputDir$delegate", "Lkotlin/Lazy;", "outputDir", "getOutputDir", "outputDir$delegate", "calculateDefaultRootDir", "calculateInputDir", "calculateOutputDir", "runner_monitor_java_androidx_test-monitor_kt"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TestDirCalculator {

    /* JADX INFO: renamed from: outputDir$delegate, reason: from kotlin metadata */
    private final Lazy outputDir = LazyKt.lazy(new Function0<File>() { // from class: androidx.test.platform.io.TestDirCalculator$outputDir$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return this.this$0.calculateOutputDir();
        }
    });

    /* JADX INFO: renamed from: inputDir$delegate, reason: from kotlin metadata */
    private final Lazy inputDir = LazyKt.lazy(new Function0<File>() { // from class: androidx.test.platform.io.TestDirCalculator$inputDir$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return this.this$0.calculateInputDir();
        }
    });

    public final File getOutputDir() {
        return (File) this.outputDir.getValue();
    }

    public final File getInputDir() {
        return (File) this.inputDir.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File calculateOutputDir() {
        String string = InstrumentationRegistry.getArguments().getString("additionalTestOutputDir");
        if (string != null) {
            return new File(string);
        }
        return new File(calculateDefaultRootDir(), "additionalTestOutputDir");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File calculateInputDir() {
        String string = InstrumentationRegistry.getArguments().getString("testInputDir");
        if (string != null) {
            return new File(string);
        }
        return new File(calculateDefaultRootDir(), "testInputDir");
    }

    private final File calculateDefaultRootDir() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        File[] externalMediaDirs = targetContext.getExternalMediaDirs();
        Intrinsics.checkNotNullExpressionValue(externalMediaDirs, "getExternalMediaDirs(...)");
        for (File file : externalMediaDirs) {
            if (Intrinsics.areEqual(Environment.getExternalStorageState(file), "mounted")) {
                Intrinsics.checkNotNull(file);
                return file;
            }
        }
        if (targetContext.getExternalCacheDir() != null) {
            File externalCacheDir = targetContext.getExternalCacheDir();
            Intrinsics.checkNotNull(externalCacheDir);
            return externalCacheDir;
        }
        File cacheDir = targetContext.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "getCacheDir(...)");
        return cacheDir;
    }
}
