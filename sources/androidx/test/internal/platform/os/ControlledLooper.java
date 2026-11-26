package androidx.test.internal.platform.os;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface ControlledLooper {
    public static final ControlledLooper NO_OP_CONTROLLED_LOOPER = new ControlledLooper() { // from class: androidx.test.internal.platform.os.ControlledLooper.1
        @Override // androidx.test.internal.platform.os.ControlledLooper
        public boolean areDrawCallbacksSupported() {
            return true;
        }

        @Override // androidx.test.internal.platform.os.ControlledLooper
        public void drainMainThreadUntilIdle() {
        }

        @Override // androidx.test.internal.platform.os.ControlledLooper
        public void simulateWindowFocus(View decorView) {
        }
    };

    default boolean areDrawCallbacksSupported() {
        return false;
    }

    void drainMainThreadUntilIdle();

    void simulateWindowFocus(View decorView);
}
