package miuix.internal.hybrid.webkit;

/* JADX INFO: loaded from: classes5.dex */
public class JsResult extends miuix.hybrid.JsResult {
    private android.webkit.JsResult mJsResult;

    public JsResult(android.webkit.JsResult jsResult) {
        this.mJsResult = jsResult;
    }

    @Override // miuix.hybrid.JsResult
    public void confirm() {
        this.mJsResult.confirm();
    }

    @Override // miuix.hybrid.JsResult
    public void cancel() {
        this.mJsResult.cancel();
    }
}
