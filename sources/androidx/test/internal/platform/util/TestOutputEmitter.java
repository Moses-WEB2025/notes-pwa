package androidx.test.internal.platform.util;

import androidx.test.internal.platform.ServiceLoaderWrapper;
import java.io.Serializable;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TestOutputEmitter {
    private static final TestOutputHandler debugHandler = (TestOutputHandler) ServiceLoaderWrapper.loadSingleService(TestOutputHandler.class, new ServiceLoaderWrapper.Factory() { // from class: androidx.test.internal.platform.util.TestOutputEmitter$$ExternalSyntheticLambda0
        @Override // androidx.test.internal.platform.ServiceLoaderWrapper.Factory
        public final Object create() {
            return TestOutputEmitter.lambda$static$0();
        }
    });

    static /* synthetic */ TestOutputHandler lambda$static$0() {
        return new TestOutputHandler() { // from class: androidx.test.internal.platform.util.TestOutputEmitter.1
            @Override // androidx.test.internal.platform.util.TestOutputHandler
            public boolean addOutputProperties(Map<String, Serializable> properties) {
                return false;
            }

            @Override // androidx.test.internal.platform.util.TestOutputHandler
            public boolean captureWindowHierarchy(String outputName) {
                return false;
            }

            @Override // androidx.test.internal.platform.util.TestOutputHandler
            public void dumpThreadStates(String outputName) {
            }

            @Override // androidx.test.internal.platform.util.TestOutputHandler
            public boolean takeScreenshot(String outputName) {
                return false;
            }
        };
    }

    private TestOutputEmitter() {
    }

    public static void dumpThreadStates(String outputName) {
        debugHandler.dumpThreadStates(outputName);
    }

    public static boolean takeScreenshot(String outputName) {
        return debugHandler.takeScreenshot(outputName);
    }

    public static boolean captureWindowHierarchy(String outputName) {
        return debugHandler.captureWindowHierarchy(outputName);
    }

    public static boolean addOutputProperties(Map<String, Serializable> properties) {
        return debugHandler.addOutputProperties(properties);
    }
}
