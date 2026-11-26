package org.junit.runners.model;

/* JADX INFO: loaded from: classes6.dex */
public interface RunnerScheduler {
    void finished();

    void schedule(Runnable runnable);
}
