package miuix.search.recommendation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes6.dex */
public class MutableListFragment extends RecyclerFragment {
    private Impl mImpl;

    @Override // miuix.search.recommendation.RecyclerFragment
    public /* bridge */ /* synthetic */ void attachDataSet(SimpleList simpleList) {
        super.attachDataSet(simpleList);
    }

    @Override // miuix.search.recommendation.RecyclerFragment
    public /* bridge */ /* synthetic */ void detachDataSet() {
        super.detachDataSet();
    }

    @Override // miuix.search.recommendation.RecyclerFragment, miuix.search.RecommendationFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // miuix.search.recommendation.RecyclerFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // miuix.search.recommendation.RecyclerFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ void onDestroyView() {
        super.onDestroyView();
    }

    @Override // miuix.search.recommendation.RecyclerFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // miuix.search.recommendation.RecyclerFragment
    public /* bridge */ /* synthetic */ void setGroupTitle(int i) {
        super.setGroupTitle(i);
    }

    @Override // miuix.search.recommendation.RecyclerFragment
    public /* bridge */ /* synthetic */ void setGroupTitle(CharSequence charSequence) {
        super.setGroupTitle(charSequence);
    }

    public MutableListFragment() {
        Impl impl = new Impl();
        this.mImpl = impl;
        attachBaseDelegate(impl);
    }

    private class Impl extends MutableListDelegate {
        private Impl() {
        }

        @Override // miuix.search.recommendation.ListDelegate
        void onItemClick(CharSequence charSequence) {
            MutableListFragment.this.notifyInputUpdate(charSequence);
        }
    }
}
