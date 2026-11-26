package miuix.search.recommendation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import miuix.search.RecommendationFragment;

/* JADX INFO: loaded from: classes6.dex */
abstract class RecyclerFragment extends RecommendationFragment {
    private ListDelegate mDelegate;

    RecyclerFragment() {
    }

    void attachBaseDelegate(ListDelegate listDelegate) {
        this.mDelegate = listDelegate;
    }

    @Override // miuix.search.RecommendationFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mDelegate.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.mDelegate.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mDelegate.onViewCreated(view, bundle);
    }

    public void attachDataSet(SimpleList simpleList) {
        this.mDelegate.attachDataSet(simpleList);
    }

    public void detachDataSet() {
        this.mDelegate.detachDataSet();
    }

    public void setGroupTitle(int i) {
        this.mDelegate.setGroupTitle(i);
    }

    public void setGroupTitle(CharSequence charSequence) {
        this.mDelegate.setGroupTitle(charSequence);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mDelegate.onDestroyView();
    }
}
