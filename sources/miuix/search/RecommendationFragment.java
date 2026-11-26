package miuix.search;

import android.content.Context;
import androidx.fragment.app.Fragment;

/* JADX INFO: loaded from: classes6.dex */
public abstract class RecommendationFragment extends Fragment {
    private SearchFragment mSearchFragment;

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = this;
        while (parentFragment != null && !(parentFragment instanceof SearchFragment)) {
            parentFragment = parentFragment.getParentFragment();
        }
        if (parentFragment == null) {
            throw new IllegalStateException("no SearchFragment found in parents");
        }
        this.mSearchFragment = (SearchFragment) parentFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mSearchFragment = null;
    }

    protected final void notifyInputUpdate(CharSequence charSequence) {
        this.mSearchFragment.onInputUpdate(charSequence);
    }
}
