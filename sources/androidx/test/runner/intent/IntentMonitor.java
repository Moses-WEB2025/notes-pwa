package androidx.test.runner.intent;

/* JADX INFO: loaded from: classes.dex */
public interface IntentMonitor {
    void addIntentCallback(IntentCallback callback);

    void removeIntentCallback(IntentCallback callback);
}
