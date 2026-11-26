package com.miui.notes.page;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.widget.FloatingActionButton;
import com.miui.pad.feature.todo.navigation.PadSmallWindowTodoListFragment;
import com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment;
import java.lang.ref.WeakReference;
import miuix.appcompat.app.Fragment;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorFragmentListener;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoPageContainerFragment extends Fragment implements NavigatorFragmentListener {
    private static final String TAG = "PadTodoPageContainerFragment";
    private HomeViewModel homeViewModel;
    public FloatingActionButton mBtnContentAdd;
    private Fragment mCurrentContentFragment = null;
    private boolean mIsSingleColumnMode = false;
    private PadSmallWindowTodoListFragment mPadSmallWindowTodoListFragment;
    private PadTodoCategoryFragment mPadTodoCategoryFragment;
    private MainHandler<PadTodoPageContainerFragment> mainHandler;

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        if (getActivity() != null) {
            this.mainHandler = new MainHandler<>(getActivity().getMainLooper(), new WeakReference(this));
        } else {
            Logger.INSTANCE.e(TAG, "PadTodoPageContainerFragment onCreate,getActivity is null!!!!");
        }
        setThemeRes(R.style.NavigatorContentChildTheme);
        boolean z = getFlatMode() || Navigator.get(this).getNavigationMode() == Navigator.Mode.C;
        this.mIsSingleColumnMode = z;
        refreshContentFragment(z);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        Logger.INSTANCE.d(TAG, "todo container onContentInsetChanged:" + rect);
        int size = getChildFragmentManager().getFragments().size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) getChildFragmentManager().getFragments().get(i);
            if (fragment != null && !fragment.hasActionBar()) {
                fragment.onContentInsetChanged(rect);
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        Logger.INSTANCE.d(TAG, "container  onExtraPaddingChanged:" + i);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.pad_todo_navigation, viewGroup, false);
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) {
        super.onUpdateArguments(bundle);
        Logger.INSTANCE.d(TAG, " onUpdateArguments:" + bundle);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        setExtraHorizontalPaddingEnable(true);
        Logger.INSTANCE.d(TAG, " onViewInflated:" + getArguments() + ",navMode:" + Navigator.get(this).getNavigationMode() + ",isSingleColumnMode:" + this.mIsSingleColumnMode + ",isContentOpen:" + Navigator.get(this).isContentOpen());
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Logger.INSTANCE.d(TAG, " onDestroyView:");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.INSTANCE.d(TAG, " onStart:");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(TAG, " onResume:" + this.mCurrentContentFragment);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.INSTANCE.d(TAG, " onPause:");
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onNavigatorModeChanged(Navigator.Mode mode, Navigator.Mode mode2) {
        this.mIsSingleColumnMode = getFlatMode() || mode2 == Navigator.Mode.C;
        Logger.INSTANCE.d(TAG, "onNavigatorModeChanged:" + mode + "-> " + mode2 + ",isSingleColumnMode:" + this.mIsSingleColumnMode + ",isContentOpen:" + Navigator.get(this).isContentOpen());
        refreshContentFragment(this.mIsSingleColumnMode);
        if (this.homeViewModel.getPageSelected() != 1 || mode2 == Navigator.Mode.C) {
            return;
        }
        openPadTodoCategory(0);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        if (this.homeViewModel.getPageSelected() == 1) {
            this.mainHandler.post(new Runnable() { // from class: com.miui.notes.page.PadTodoPageContainerFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onResponsiveLayout$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponsiveLayout$0() {
        if (isAdded()) {
            Navigator.get(this).requestFocus(true);
        }
    }

    public void openPadTodoCategory(int i) {
        Logger.INSTANCE.d(TAG, "openPadTodoCategory mPadTodoCategoryFragment:" + this.mPadTodoCategoryFragment);
        PadTodoCategoryFragment padTodoCategoryFragment = this.mPadTodoCategoryFragment;
        if (padTodoCategoryFragment != null) {
            padTodoCategoryFragment.openTodoCategory(i);
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x005f  */
    private void refreshContentFragment(boolean z) {
        boolean z2;
        Logger.INSTANCE.d(TAG, "refreshContentFragment,isSingleColumnMode:" + z);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
        if (childFragmentManager.getFragments().isEmpty()) {
            z2 = true;
        } else {
            Fragment fragment = (Fragment) childFragmentManager.getFragments().get(0);
            if ((fragment instanceof PadSmallWindowTodoListFragment) && z) {
                Logger.INSTANCE.d(TAG, "refreshContentFragment:PadSmallWindowTodoListFragment");
                PadSmallWindowTodoListFragment padSmallWindowTodoListFragment = (PadSmallWindowTodoListFragment) fragment;
                this.mPadSmallWindowTodoListFragment = padSmallWindowTodoListFragment;
                this.mCurrentContentFragment = padSmallWindowTodoListFragment;
            } else if (!(fragment instanceof PadTodoCategoryFragment) || z) {
                z2 = true;
            } else {
                Logger.INSTANCE.d(TAG, "refreshContentFragment:PadTodoCategoryFragment");
                PadTodoCategoryFragment padTodoCategoryFragment = (PadTodoCategoryFragment) fragment;
                this.mPadTodoCategoryFragment = padTodoCategoryFragment;
                this.mCurrentContentFragment = padTodoCategoryFragment;
            }
            z2 = false;
        }
        Logger.INSTANCE.d(TAG, "refreshContentFragment,needReplace:" + z2);
        if (z2) {
            Fragment fragment2 = this.mCurrentContentFragment;
            if (fragment2 instanceof PadSmallWindowTodoListFragment) {
                Logger.INSTANCE.d(TAG, "refreshContentFragment,needReplace,PadSmallWindowTodoListFragment,exit search");
                ((PadSmallWindowTodoListFragment) this.mCurrentContentFragment).exitEdit(false);
                ((PadSmallWindowTodoListFragment) this.mCurrentContentFragment).exitSearchMode();
            } else if (fragment2 instanceof PadTodoCategoryFragment) {
                Logger.INSTANCE.d(TAG, "refreshContentFragment,needReplace,PadTodoCategoryFragment,exit search");
                ((PadTodoCategoryFragment) this.mCurrentContentFragment).exitSearchMode();
            }
            Fragment fragmentCreateTodoListFragment = createTodoListFragment(z);
            this.mCurrentContentFragment = fragmentCreateTodoListFragment;
            fragmentTransactionBeginTransaction.replace(R.id.todo_content, fragmentCreateTodoListFragment);
            fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
            if (z) {
                this.mPadTodoCategoryFragment = null;
            } else {
                this.mPadSmallWindowTodoListFragment = null;
            }
        }
    }

    public void bindComposeAction(View view) {
        Fragment fragment = this.mCurrentContentFragment;
        if (fragment == null || !(fragment instanceof PadSmallWindowTodoListFragment)) {
            return;
        }
        ((PadSmallWindowTodoListFragment) fragment).bindComposeAction(view);
    }

    private Fragment createTodoListFragment(boolean z) {
        if (z) {
            return createFoldTodoListFragment();
        }
        return createTodoNavigationFragment();
    }

    private PadTodoCategoryFragment createTodoNavigationFragment() {
        if (this.mPadTodoCategoryFragment == null) {
            this.mPadTodoCategoryFragment = new PadTodoCategoryFragment();
            Logger.INSTANCE.d(TAG, "createTodoNavigationFragment:PadTodoCategoryFragment");
        }
        return this.mPadTodoCategoryFragment;
    }

    private PadSmallWindowTodoListFragment createFoldTodoListFragment() {
        if (this.mPadSmallWindowTodoListFragment == null) {
            PadSmallWindowTodoListFragment padSmallWindowTodoListFragment = new PadSmallWindowTodoListFragment();
            this.mPadSmallWindowTodoListFragment = padSmallWindowTodoListFragment;
            padSmallWindowTodoListFragment.mBtnContentAdd = this.mBtnContentAdd;
            Logger.INSTANCE.d(TAG, "createFoldTodoListFragment:PadSmallWindowTodoListFragment");
        }
        return this.mPadSmallWindowTodoListFragment;
    }

    private boolean getFlatMode() {
        return !RomUtils.isPadDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        MainHandler<PadTodoPageContainerFragment> mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }
}
