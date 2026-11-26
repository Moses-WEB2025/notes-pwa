package androidx.test.platform.ui;

import android.view.KeyEvent;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public interface UiController {
    boolean injectKeyEvent(KeyEvent event) throws InjectEventSecurityException;

    boolean injectMotionEvent(MotionEvent event) throws InjectEventSecurityException;

    boolean injectString(String str) throws InjectEventSecurityException;

    void loopMainThreadForAtLeast(long millisDelay);

    void loopMainThreadUntilIdle();
}
