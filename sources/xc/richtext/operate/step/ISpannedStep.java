package xc.richtext.operate.step;

/* JADX INFO: loaded from: classes2.dex */
public interface ISpannedStep {
    int getPageIndex();

    int[] redo();

    int[] undo();
}
