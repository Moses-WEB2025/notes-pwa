package miuix.internal.hybrid.webkit;

/* JADX INFO: loaded from: classes5.dex */
public class ValueCallback<T> implements miuix.hybrid.ValueCallback<T> {
    private android.webkit.ValueCallback<T> mValueCallback;

    public ValueCallback(android.webkit.ValueCallback<T> valueCallback) {
        this.mValueCallback = valueCallback;
    }

    @Override // miuix.hybrid.ValueCallback
    public void onReceiveValue(T t) {
        this.mValueCallback.onReceiveValue(t);
    }
}
