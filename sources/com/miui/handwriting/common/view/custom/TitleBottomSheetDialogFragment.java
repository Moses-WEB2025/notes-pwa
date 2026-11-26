package com.miui.handwriting.common.view.custom;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.creation.common.R;
import miuix.bottomsheet.BottomSheetBehavior;
import miuix.bottomsheet.BottomSheetDialog;
import miuix.bottomsheet.BottomSheetDialogFragment;

/* JADX INFO: loaded from: classes3.dex */
public class TitleBottomSheetDialogFragment extends BottomSheetDialogFragment {
    BaseFragment.OnInnerActionBarListener InnerActionBarListener;
    int pageHeight;
    int pageUnit;
    int pageWidth;

    public void setInnerActionBarListener(BaseFragment.OnInnerActionBarListener onInnerActionBarListener) {
        this.InnerActionBarListener = onInnerActionBarListener;
    }

    public void setPageWidth(int i) {
        this.pageWidth = i;
    }

    public void setPageHeight(int i) {
        this.pageHeight = i;
    }

    public void setPageUnit(int i) {
        this.pageUnit = i;
    }

    @Override // miuix.bottomsheet.BottomSheetDialogFragment, androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), getTheme());
        BottomSheetBehavior<FrameLayout> behavior = bottomSheetDialog.getBehavior();
        behavior.setState(3);
        behavior.setForceFullHeight(true);
        behavior.setSkipCollapsed(false);
        return bottomSheetDialog;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
        }
        return layoutInflater.inflate(R.layout.dialog_fragment_bottom_sheet, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        NestedFragment nestedFragment = new NestedFragment();
        nestedFragment.setPageHeight(this.pageHeight);
        nestedFragment.setPageWidth(this.pageWidth);
        nestedFragment.setPageUnit(this.pageUnit);
        nestedFragment.setInnerActionBarListener(this.InnerActionBarListener);
        getChildFragmentManager().beginTransaction().replace(R.id.container_view, nestedFragment).commit();
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager fragmentManager, String str) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.add(this, str);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }
}
