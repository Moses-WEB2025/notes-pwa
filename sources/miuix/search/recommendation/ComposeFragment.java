package miuix.search.recommendation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
public class ComposeFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.compose_layout, viewGroup, false);
    }

    protected final void addFragment(Fragment fragment, String str) {
        requireFragmentManager().beginTransaction().add(R.id.compose_container, fragment, str).commit();
    }

    protected final void addFragment(Fragment fragment) {
        requireFragmentManager().beginTransaction().add(R.id.compose_container, fragment).commit();
    }
}
