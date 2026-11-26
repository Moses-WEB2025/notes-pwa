package miuix.preference.flexible;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import miuix.preference.R;

/* JADX INFO: loaded from: classes6.dex */
public class TextPreferenceTemplate extends AbstractBaseTemplate {
    private boolean mHasTextView = false;

    @Override // miuix.preference.flexible.AbstractBaseTemplate
    public int onLargeLayoutSelected() {
        if (this.mHasTitle && this.mHasSummary && this.mHasTextView) {
            return 22002;
        }
        if (this.mHasTitle && this.mHasTextView && !this.mHasSummary) {
            return 21004;
        }
        if (this.mHasTitle && this.mHasSummary && !this.mHasTextView) {
            return 22001;
        }
        return (!this.mHasTitle || this.mHasSummary || this.mHasTextView) ? 21004 : 20001;
    }

    @Override // miuix.preference.flexible.AbstractBaseTemplate
    public void checkView(ViewGroup viewGroup) {
        View viewFindViewByAreaId = findViewByAreaId(viewGroup, R.id.area_end);
        if (this.mHasWidget && (viewFindViewByAreaId instanceof LinearLayout)) {
            View childAt = ((LinearLayout) viewFindViewByAreaId).getChildAt(0);
            if (childAt instanceof TextView) {
                this.mHasTextView = childAt.getVisibility() == 0;
            }
        }
    }
}
