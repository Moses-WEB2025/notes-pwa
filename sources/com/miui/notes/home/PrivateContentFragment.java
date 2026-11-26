package com.miui.notes.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.page.BaseNoteListPageFragment;
import com.miui.notes.page.PadPrivateNoteListPageFragment;
import com.miui.notes.page.PhonePrivateNoteListPageFragment;
import com.miui.notes.tool.PreferenceUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.Fragment;

/* JADX INFO: compiled from: PrivateContentFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J&\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u001a\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0018\u001a\u00020\u0005H\u0002J\b\u0010\u0019\u001a\u00020\rH\u0002J\u0012\u0010\u001a\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000fH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/notes/home/PrivateContentFragment;", "Lmiuix/appcompat/app/Fragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "notePageFragment", "Lcom/miui/notes/page/BaseNoteListPageFragment;", "mActionBar", "Lmiuix/appcompat/app/ActionBar;", "mActionModeSwitcher", "Landroid/widget/ImageView;", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onInflateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewInflated", "view", "getNotePageFragment", "initActionBar", "onUpdateArguments", "newArguments", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PrivateContentFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private ActionBar mActionBar;
    private ImageView mActionModeSwitcher;
    private BaseNoteListPageFragment notePageFragment;

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeRes(R.style.NotesNavigatorContentTheme);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.private_content_fragment, container, false);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(fragmentActivityRequireActivity).get(HomeViewModel.class);
        this.notePageFragment = getNotePageFragment();
        HomeViewModel homeViewModel = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel);
        homeViewModel.setFolderId(-4L);
        FragmentTransaction fragmentTransactionBeginTransaction = getChildFragmentManager().beginTransaction();
        BaseNoteListPageFragment baseNoteListPageFragment = this.notePageFragment;
        Intrinsics.checkNotNull(baseNoteListPageFragment);
        fragmentTransactionBeginTransaction.replace(R.id.fragment_content, baseNoteListPageFragment).commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
        initActionBar();
    }

    private final BaseNoteListPageFragment getNotePageFragment() {
        if (RomUtils.isPadMode()) {
            return new PadPrivateNoteListPageFragment();
        }
        return new PhonePrivateNoteListPageFragment();
    }

    private final void initActionBar() {
        this.mActionBar = getActionBar();
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(R.drawable.miui_action_bar_back);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.home.PrivateContentFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrivateContentFragment.initActionBar$lambda$0(this.f$0, view);
            }
        });
        imageView.setContentDescription(getString(R.string.actionbar_home_description));
        ActionBar actionBar = this.mActionBar;
        Intrinsics.checkNotNull(actionBar);
        actionBar.setStartView(imageView);
        ActionBar actionBar2 = this.mActionBar;
        Intrinsics.checkNotNull(actionBar2);
        actionBar2.setTitle(getString(R.string.hidden_notes_fragment_title));
        if (LiteUtils.isLiteOrMiddle() || RomUtils.isPadMode()) {
            return;
        }
        this.mActionModeSwitcher = new ImageView(getThemedContext());
        boolean gridMode = PreferenceUtils.getGridMode(getActivity());
        ImageView imageView2 = this.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setSelected(gridMode);
        ImageView imageView3 = this.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_menu_mode_switcher);
        int i = gridMode ? R.string.action_mode_switcher_list_description : R.string.action_mode_switcher_grid_description;
        ImageView imageView4 = this.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView4);
        imageView4.setContentDescription(getString(i));
        ImageView imageView5 = this.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.home.PrivateContentFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PrivateContentFragment.initActionBar$lambda$1(this.f$0, view);
            }
        });
        ActionBar actionBar3 = this.mActionBar;
        Intrinsics.checkNotNull(actionBar3);
        actionBar3.setEndView(this.mActionModeSwitcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActionBar$lambda$0(PrivateContentFragment privateContentFragment, View view) {
        privateContentFragment.requireActivity().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActionBar$lambda$1(PrivateContentFragment privateContentFragment, View view) {
        if (Utils.isFastClick(privateContentFragment.requireView().getId(), 300L)) {
            return;
        }
        boolean gridMode = PreferenceUtils.getGridMode(privateContentFragment.getActivity());
        PreferenceUtils.setGridMode(privateContentFragment.getActivity(), !gridMode);
        LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_GRID_MODE).setValue(Boolean.valueOf(!gridMode));
        ImageView imageView = privateContentFragment.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView);
        imageView.setSelected(!gridMode);
        int i = !gridMode ? R.string.action_mode_switcher_list_description : R.string.action_mode_switcher_grid_description;
        ImageView imageView2 = privateContentFragment.mActionModeSwitcher;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setContentDescription(privateContentFragment.getString(i));
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle newArguments) {
        super.onUpdateArguments(newArguments);
        Logger.INSTANCE.d("PrivateContentFragment", "onUpdateArguments");
    }
}
