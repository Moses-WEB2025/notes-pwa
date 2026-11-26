package com.miui.handwriting.common.view.custom;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.creation.common.R;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes3.dex */
public class NestedFragment extends Fragment implements CustomPreferenceFragment.OnHeightInputListener, CustomPreferenceFragment.OnWidthInputListener {
    BaseFragment.OnInnerActionBarListener InnerActionBarListener;
    private ImageView endView;
    androidx.fragment.app.Fragment innerFragment;
    private boolean isHeightInput = false;
    private boolean isWidthInput = false;
    private int pageHeight;
    private int pageUnit;
    private int pageWidth;

    public void setPageHeight(int i) {
        this.pageHeight = i;
    }

    public void setPageUnit(int i) {
        this.pageUnit = i;
    }

    public void setPageWidth(int i) {
        this.pageWidth = i;
    }

    public void setInnerActionBarListener(BaseFragment.OnInnerActionBarListener onInnerActionBarListener) {
        this.InnerActionBarListener = onInnerActionBarListener;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        setThemeRes(R.style.ActionModeTheme);
        super.onCreate(bundle);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.custom_page_size_nested_fragment, viewGroup, false);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        androidx.fragment.app.Fragment fragmentFindFragmentById = getChildFragmentManager().findFragmentById(R.id.fragment_container);
        this.innerFragment = fragmentFindFragmentById;
        if (fragmentFindFragmentById == null) {
            CustomPreferenceFragment customPreferenceFragment = new CustomPreferenceFragment();
            this.innerFragment = customPreferenceFragment;
            customPreferenceFragment.setPageWidth(this.pageWidth);
            ((CustomPreferenceFragment) this.innerFragment).setPageHeight(this.pageHeight);
            ((CustomPreferenceFragment) this.innerFragment).setPageUnit(this.pageUnit);
            ((CustomPreferenceFragment) this.innerFragment).setOnHeightInputListener(this);
            ((CustomPreferenceFragment) this.innerFragment).setOnWidthInputListener(this);
            getChildFragmentManager().beginTransaction().add(R.id.fragment_container, this.innerFragment).commit();
        }
        handleActionbar(getActionBar());
        super.onViewInflated(view, bundle);
    }

    private void handleActionbar(ActionBar actionBar) {
        if (actionBar == null) {
            return;
        }
        actionBar.setTitle(R.string.creation_page_size_custom_title);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.page_size_dialog_color, null)));
        ImageView imageView = new ImageView(getContext());
        imageView.setContentDescription(getContext().getResources().getString(R.string.creation_tool_back));
        imageView.setBackgroundResource(R.drawable.creation_ai_back);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.custom.NestedFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NestedFragment.this.InnerActionBarListener.onStartViewClick();
            }
        });
        actionBar.setStartView(imageView);
        actionBar.setExpandState(0);
        actionBar.setResizable(false);
        ImageView imageView2 = new ImageView(getContext());
        this.endView = imageView2;
        imageView2.setContentDescription(getContext().getResources().getString(R.string.creation_edit_limit_dialog_sure));
        this.endView.setBackgroundResource(R.drawable.miui_action_bar_confirm);
        actionBar.setEndView(this.endView);
        this.endView.setEnabled(false);
        this.endView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.custom.NestedFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NestedFragment.this.innerFragment != null) {
                    NestedFragment.this.InnerActionBarListener.onEndViewClick(((CustomPreferenceFragment) NestedFragment.this.innerFragment).getPageHeight(), ((CustomPreferenceFragment) NestedFragment.this.innerFragment).getPageWidth(), ((CustomPreferenceFragment) NestedFragment.this.innerFragment).getPageUnit());
                }
            }
        });
    }

    @Override // com.miui.handwriting.common.view.custom.CustomPreferenceFragment.OnHeightInputListener
    public void onHeightInput(boolean z) {
        this.isHeightInput = z;
        ImageView imageView = this.endView;
        if (imageView != null) {
            if (z && this.isWidthInput) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
            }
        }
    }

    @Override // com.miui.handwriting.common.view.custom.CustomPreferenceFragment.OnWidthInputListener
    public void onWidthInput(boolean z) {
        this.isWidthInput = z;
        ImageView imageView = this.endView;
        if (imageView != null) {
            if (this.isHeightInput && z) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
            }
        }
    }
}
