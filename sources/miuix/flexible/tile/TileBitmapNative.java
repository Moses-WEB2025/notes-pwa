package miuix.flexible.tile;

/* JADX INFO: loaded from: classes5.dex */
public class TileBitmapNative {
    public static native int getTileCache(long[] jArr, int i, int i2);

    static {
        System.loadLibrary("flexible");
    }

    private TileBitmapNative() {
    }
}
