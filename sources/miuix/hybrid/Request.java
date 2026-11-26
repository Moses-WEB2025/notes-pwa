package miuix.hybrid;

import android.view.View;

/* JADX INFO: loaded from: classes5.dex */
public class Request {
    private String action;
    private Callback callback;
    private NativeInterface nativeInterface;
    private PageContext pageContext;
    private String rawParams;
    private View view;

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public String getRawParams() {
        return this.rawParams;
    }

    public void setRawParams(String str) {
        this.rawParams = str;
    }

    public Callback getCallback() {
        return this.callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public PageContext getPageContext() {
        return this.pageContext;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public NativeInterface getNativeInterface() {
        return this.nativeInterface;
    }

    public void setNativeInterface(NativeInterface nativeInterface) {
        this.nativeInterface = nativeInterface;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
