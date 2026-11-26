package miuix.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import miuix.appcompat.app.Fragment;
import miuix.internal.hybrid.HybridManager;

/* JADX INFO: loaded from: classes5.dex */
public class HybridFragment extends Fragment {
    private Set<HybridView> mHybridViews = new HashSet();

    protected int getConfigResId() {
        return 0;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return getContentView();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intent intent;
        super.onViewCreated(view, bundle);
        View viewFindViewById = view.findViewById(R.id.hybrid_view);
        if (viewFindViewById == null || !(viewFindViewById instanceof HybridView)) {
            return;
        }
        String string = bundle != null ? bundle.getString(HybridActivity.EXTRA_URL) : null;
        if (string == null && (intent = getActivity().getIntent()) != null) {
            string = intent.getStringExtra(HybridActivity.EXTRA_URL);
        }
        registerHybridView((HybridView) viewFindViewById, getConfigResId(), string);
    }

    protected View getContentView() {
        return getActivity().getLayoutInflater().inflate(R.layout.hybrid_main, (ViewGroup) null);
    }

    protected final void registerHybridView(View view) {
        registerHybridView(view, getConfigResId());
    }

    protected final void registerHybridView(View view, int i) {
        registerHybridView(view, i, null);
    }

    protected final void registerHybridView(View view, int i, String str) {
        if (!(view instanceof HybridView)) {
            throw new IllegalArgumentException("view being registered is not a hybrid view");
        }
        HybridView hybridView = (HybridView) view;
        HybridManager hybridManager = new HybridManager(getActivity(), hybridView);
        hybridView.setHybridManager(hybridManager);
        this.mHybridViews.add(hybridView);
        hybridManager.init(i, str);
    }

    protected final void unregisterHybridView(View view) {
        if (!(view instanceof HybridView)) {
            throw new IllegalArgumentException("view being unregistered is not a hybrid view");
        }
        this.mHybridViews.remove(view);
    }

    private void destroyHybridView() {
        for (HybridView hybridView : this.mHybridViews) {
            if (hybridView != null) {
                if (hybridView.getParent() != null) {
                    ((ViewGroup) hybridView.getParent()).removeView(hybridView);
                }
                hybridView.destroy();
            }
        }
        this.mHybridViews.clear();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onStart();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onResume();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onPause();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onStop();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onDestroy();
        }
        destroyHybridView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Iterator<HybridView> it = this.mHybridViews.iterator();
        while (it.hasNext()) {
            it.next().getHybridManager().onActivityResult(i, i2, intent);
        }
    }
}
