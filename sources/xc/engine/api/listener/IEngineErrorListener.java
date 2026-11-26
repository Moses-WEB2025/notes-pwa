package xc.engine.api.listener;

/* JADX INFO: loaded from: classes6.dex */
public interface IEngineErrorListener {
    public static final int POINT_LIMIT = 0;
    public static final int TOTAL_POINT_LIMIT = 1;

    void onError(int i, String str);

    void onSuccess();
}
