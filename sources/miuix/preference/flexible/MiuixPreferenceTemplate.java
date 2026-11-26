package miuix.preference.flexible;

import android.view.ViewGroup;

/* JADX INFO: loaded from: classes6.dex */
public class MiuixPreferenceTemplate extends AbstractBaseTemplate {
    @Override // miuix.preference.flexible.AbstractBaseTemplate
    public void checkView(ViewGroup viewGroup) {
    }

    @Override // miuix.preference.flexible.AbstractBaseTemplate
    public int onLargeLayoutSelected() {
        if (this.mHasTitle && this.mHasSummary) {
            return 22001;
        }
        if (!this.mHasTitle || this.mHasSummary) {
            return (this.mHasTitle || !this.mHasSummary) ? 22001 : 20002;
        }
        return 20001;
    }
}
