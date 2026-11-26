package miuix.autodensity;

/* JADX INFO: loaded from: classes5.dex */
public interface IDensity {
    default int getRatioUiBaseWidthDp() {
        return 0;
    }

    boolean shouldAdaptAutoDensity();
}
