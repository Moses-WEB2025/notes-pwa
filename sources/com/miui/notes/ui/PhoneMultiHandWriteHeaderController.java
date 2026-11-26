package com.miui.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.notes.R;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneMultiHandWriteHeaderController implements View.OnClickListener {
    public static final int ACTION_SHARE = 1;
    private final HeaderOperationCallback mCallback;
    private final Fragment mFragment;
    private View mMoreView;

    public interface HeaderOperationCallback {
        void onPerformAction(int i, Object... objArr);
    }

    private int getLayoutResource() {
        return R.layout.phone_hand_write_note_header;
    }

    public PhoneMultiHandWriteHeaderController(Fragment fragment, HeaderOperationCallback headerOperationCallback) {
        this.mFragment = fragment;
        this.mCallback = headerOperationCallback;
    }

    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(getLayoutResource(), viewGroup, false);
        viewInflate.findViewById(R.id.share).setOnClickListener(this);
        viewInflate.findViewById(R.id.home).setOnClickListener(this);
        View viewFindViewById = viewInflate.findViewById(R.id.more);
        this.mMoreView = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        return viewInflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.home) {
            try {
                this.mFragment.getActivity().onBackPressed();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (id != R.id.more) {
            if (id != R.id.share) {
                return;
            }
            this.mCallback.onPerformAction(1, new Object[0]);
        } else {
            Fragment fragment = this.mFragment;
            if (fragment != null) {
                fragment.showImmersionMenu(this.mMoreView, null);
            }
        }
    }
}
