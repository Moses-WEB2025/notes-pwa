package xc.engine.api.listener;

/* JADX INFO: loaded from: classes6.dex */
public interface DrawErrorCallback {
    public static final int BITMAP_LIMIT = 2;
    public static final int POINT_LIMIT = 0;
    public static final int TEXT_LIMIT = 3;
    public static final int TOTAL_POINT_LIMIT = 1;

    void onError(int i);

    void onSuccess();
}
