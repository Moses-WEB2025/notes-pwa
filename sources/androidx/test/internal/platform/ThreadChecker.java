package androidx.test.internal.platform;

/* JADX INFO: loaded from: classes.dex */
public interface ThreadChecker {
    void checkMainThread();

    void checkNotMainThread();
}
