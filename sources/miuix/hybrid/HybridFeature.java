package miuix.hybrid;

import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public interface HybridFeature {

    public enum Mode {
        SYNC,
        ASYNC,
        CALLBACK
    }

    Mode getInvocationMode(Request request);

    Response invoke(Request request);

    void setParams(Map<String, String> map);
}
