package ho;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes4.dex */
public final class b {
    public static volatile b c;
    public HandlerThread a;
    public Handler b;

    public b() {
        HandlerThread handlerThread = new HandlerThread("HD");
        this.a = handlerThread;
        handlerThread.start();
        this.b = new Handler(this.a.getLooper());
    }

    public static b b() {
        if (c == null) {
            synchronized (b.class) {
                if (c == null) {
                    c = new b();
                }
            }
        }
        return c;
    }

    public final Handler a() {
        return this.b;
    }
}
