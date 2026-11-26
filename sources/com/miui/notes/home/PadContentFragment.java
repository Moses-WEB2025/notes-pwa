package com.miui.notes.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.page.PadNoteListPageFragment;
import com.miui.notes.page.PadTodoPageContainerFragment;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.widget.HomepageViewPager;
import com.miui.pad.feature.settings.PadAppPreferenceActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miuix.appcompat.app.Fragment;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorStrategy;

/* JADX INFO: compiled from: PadContentFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u000e\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u0016\u001a\u00020\tH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0018H\u0014J\b\u0010\u001a\u001a\u00020\tH\u0016J\u001c\u0010\u001b\u001a\u00020\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001f\u001a\u00020 H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0094D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"Lcom/miui/notes/home/PadContentFragment;", "Lcom/miui/notes/home/ContentFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "onViewInflated", "", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onNavigationMenuSelected", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onUpdateArguments", "newArguments", "onSaveInstanceState", "outState", "onDestroyView", "createNoteListFragment", "Lmiuix/appcompat/app/Fragment;", "createTodoListFragment", "toSettings", "onNavigatorModeChanged", "oldMode", "Lmiuix/navigator/Navigator$Mode;", "newMode", "canViewPagerScroll", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadContentFragment extends ContentFragment {
    private final String TAG = "PadContentFragment";

    @Override // com.miui.notes.home.ContentFragment
    protected String getTAG() {
        return this.TAG;
    }

    @Override // com.miui.notes.home.ContentFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        if (canViewPagerScroll()) {
            return;
        }
        HomepageViewPager homepageViewPager = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager);
        homepageViewPager.setScroll(false);
    }

    @Override // com.miui.notes.home.ContentFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RomUtils.isFoldDevice()) {
            LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_ARRAY_WAY, Boolean.TYPE).observe(this, new Observer() { // from class: com.miui.notes.home.PadContentFragment$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    PadContentFragment.onCreate$lambda$0(this.f$0, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PadContentFragment padContentFragment, Boolean bool) {
        HomeViewModel homeViewModel;
        Logger.INSTANCE.d(padContentFragment.getTAG(), "homepage_array_way is isSingleColumnMode:" + bool);
        if (Intrinsics.areEqual((Object) bool, (Object) true)) {
            NavigatorStrategy navigatorStrategy = new NavigatorStrategy();
            Logger logger = Logger.INSTANCE;
            String tag = padContentFragment.getTAG();
            PadContentFragment padContentFragment2 = padContentFragment;
            boolean zIsContentOpen = Navigator.get(padContentFragment2).isContentOpen();
            HomeViewModel homeViewModel2 = padContentFragment.homeViewModel;
            logger.d(tag, "isContentOpen: " + zIsContentOpen + " isGridMode: " + (homeViewModel2 != null ? Boolean.valueOf(homeViewModel2.isGridMode()) : null));
            if (Navigator.get(padContentFragment2).isContentOpen() && (homeViewModel = padContentFragment.homeViewModel) != null && !homeViewModel.isGridMode()) {
                Navigator.get(padContentFragment2).requestFocus();
            }
            navigatorStrategy.setCompactMode(Navigator.Mode.C).setRegularMode(Navigator.Mode.C, Navigator.Mode.C).setLargeMode(Navigator.Mode.NLC);
            Navigator.get(padContentFragment2).setStrategy(navigatorStrategy);
        } else {
            NavigatorStrategy navigatorStrategy2 = new NavigatorStrategy();
            navigatorStrategy2.setCompactMode(Navigator.Mode.C).setRegularMode(Navigator.Mode.NLC, Navigator.Mode.LC).setLargeMode(Navigator.Mode.NLC);
            Navigator.get(padContentFragment).setStrategy(navigatorStrategy2);
        }
        HomeViewModel homeViewModel3 = padContentFragment.homeViewModel;
        if (homeViewModel3 != null) {
            Intrinsics.checkNotNull(bool);
            homeViewModel3.setCurrentGridMode(bool.booleanValue());
        }
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onNavigationMenuSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        super.onNavigationMenuSelected(item);
        if (item.getItemId() != R.id.action_settings || Utils.isFastClick(R.id.action_settings, 500L)) {
            return;
        }
        toSettings();
        BaseHybridNoteEditFragment.INSTANCE.removeAiFloatingWindow();
    }

    @Override // com.miui.notes.home.ContentFragment, miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle newArguments) {
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        super.onUpdateArguments(newArguments);
        Logger.INSTANCE.i(getTAG(), "onUpdateArguments");
        if (this.mViewPager != null) {
            HomepageViewPager homepageViewPager = this.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager);
            Fragment fragmentFindCurrentFragment = homepageViewPager.findCurrentFragment(this);
            Logger logger = Logger.INSTANCE;
            String tag = getTAG();
            HomepageViewPager homepageViewPager2 = this.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager2);
            logger.i(tag, "onUpdateArguments Content:" + newArguments + " ,pos:" + homepageViewPager2.getCurrentItem() + ",realFgSize:" + getChildFragmentManager().getFragments().size() + ",current child:" + fragmentFindCurrentFragment);
            if (newArguments.containsKey("data_id") && (fragmentFindCurrentFragment instanceof PadTodoPageContainerFragment)) {
                Logger.INSTANCE.i(getTAG(), "onUpdateArguments,child is PadTodoPageContainerFragment,gonna notify");
                if (getChildFragmentManager().getFragments().size() > 1) {
                    int iIndexOf = getChildFragmentManager().getFragments().indexOf(fragmentFindCurrentFragment);
                    if (iIndexOf != -1) {
                        androidx.fragment.app.Fragment fragment = getChildFragmentManager().getFragments().get(Math.abs(iIndexOf - 1));
                        Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type miuix.appcompat.app.Fragment");
                        fragmentFindCurrentFragment = (Fragment) fragment;
                        Logger.INSTANCE.i(getTAG(), "onUpdateArguments,child gonna notify " + fragmentFindCurrentFragment);
                    }
                } else {
                    Logger.INSTANCE.e(getTAG(), "onUpdateArguments,child is PadTodoPageContainerFragment,only one child!!!!");
                }
            }
            if (fragmentFindCurrentFragment != null) {
                Logger.INSTANCE.i(getTAG(), "onUpdateArguments,child real notify " + fragmentFindCurrentFragment);
                fragmentFindCurrentFragment.onUpdateArguments(newArguments);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        PreferenceUtils.setNavigationState(Navigator.get(this).isNavigationInitOpen());
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        com.miui.notes.base.utils.Logger.INSTANCE.d(getTAG(), "content onDestroyView");
        PreferenceUtils.setNavigationState(Navigator.get(this).isNavigationInitOpen());
    }

    @Override // com.miui.notes.home.ContentFragment
    protected Fragment createNoteListFragment() {
        PadNoteListPageFragment padNoteListPageFragment = new PadNoteListPageFragment();
        Logger.INSTANCE.d(getTAG(), "PadNoteListPageFragment new");
        return padNoteListPageFragment;
    }

    @Override // com.miui.notes.home.ContentFragment
    protected Fragment createTodoListFragment() {
        PadTodoPageContainerFragment padTodoPageContainerFragment = new PadTodoPageContainerFragment();
        Logger.INSTANCE.d(getTAG(), "PadTodoPageContainerFragment new");
        return padTodoPageContainerFragment;
    }

    @Override // com.miui.notes.home.ContentFragment
    public void toSettings() {
        PadAppPreferenceActivity.Companion companion = PadAppPreferenceActivity.INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        companion.open(fragmentActivityRequireActivity);
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onNavigatorModeChanged(Navigator.Mode oldMode, Navigator.Mode newMode) {
        setActionBarVisibility(newMode);
        com.miui.notes.base.utils.Logger.INSTANCE.d(getTAG(), "onNavigatorModeChanged");
        boolean z = true;
        if (newMode == Navigator.Mode.C) {
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            if (homeViewModel.getPageSelected() == 1) {
                com.miui.notes.base.utils.Logger.INSTANCE.d(getTAG(), "PadContent modeChange:");
                HomepageViewPager homepageViewPager = this.mViewPager;
                Intrinsics.checkNotNull(homepageViewPager);
                homepageViewPager.requestFocus();
            }
        }
        HomepageViewPager homepageViewPager2 = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager2);
        if (this.mIsHyperOs && RomUtils.isPadDevice() && newMode != Navigator.Mode.C) {
            z = false;
        }
        homepageViewPager2.setScroll(z);
    }

    @Override // com.miui.notes.home.ContentFragment
    protected boolean canViewPagerScroll() {
        if (this.mIsHyperOs) {
            return !RomUtils.isPadDevice() || Navigator.get(this).getNavigationMode() == Navigator.Mode.C;
        }
        return true;
    }
}
