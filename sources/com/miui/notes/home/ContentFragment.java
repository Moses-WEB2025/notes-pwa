package com.miui.notes.home;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.OriginalViewPager2;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.adapter.HomepageFragmentPagerAdapter;
import com.miui.notes.adapter.OnCreateFragmentCallback;
import com.miui.notes.assist.EmptyNavigatorInfo;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.page.PhoneNoteListPageFragment;
import com.miui.notes.page.TodoListPageFragment;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.NotesPreferenceActivity;
import com.miui.notes.ui.view.HomepageSpringBackLayout;
import com.miui.notes.ui.widget.HomepageViewPager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.Fragment;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorFragmentListener;

/* JADX INFO: compiled from: ContentFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000 K2\u00020\u00012\u00020\u0002:\u0001KB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\"H\u0014J&\u0010&\u001a\u0004\u0018\u00010\u001c2\u0006\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010+\u001a\u00020\"H\u0016J\b\u0010,\u001a\u00020\"H\u0016J\b\u0010-\u001a\u00020\"H\u0016J\u0010\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u000200H\u0016J\u001a\u00101\u001a\u00020\"2\u0006\u00102\u001a\u00020\u001c2\b\u0010#\u001a\u0004\u0018\u00010$H\u0017J\b\u00103\u001a\u00020\"H\u0002J\u0014\u00104\u001a\u00020\"2\n\b\u0002\u00105\u001a\u0004\u0018\u00010$H\u0003J\u001a\u00106\u001a\u00020\"2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:H\u0002J\u0012\u0010;\u001a\u00020\"2\b\u00109\u001a\u0004\u0018\u00010:H\u0002J\u0010\u0010<\u001a\u00020\n2\u0006\u0010=\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u0014H\u0016J\u0012\u0010A\u001a\u00020\"2\b\u0010B\u001a\u0004\u0018\u00010CH\u0004J\u0010\u0010D\u001a\u00020\"2\u0006\u0010E\u001a\u00020$H\u0016J\u0010\u0010F\u001a\u00020\"2\u0006\u00107\u001a\u000208H\u0002J\b\u0010G\u001a\u00020\u0001H\u0014J\b\u0010H\u001a\u00020\u0001H\u0014J\b\u0010I\u001a\u00020\nH\u0014J\b\u0010J\u001a\u00020\"H\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0094D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\n8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006L"}, d2 = {"Lcom/miui/notes/home/ContentFragment;", "Lmiuix/appcompat/app/Fragment;", "Lmiuix/navigator/NavigatorFragmentListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "mIsTabSelected", "", "mSpringBackLayout", "Lcom/miui/notes/ui/view/HomepageSpringBackLayout;", "mViewPager", "Lcom/miui/notes/ui/widget/HomepageViewPager;", "mVpFragmentAdapter", "Lcom/miui/notes/adapter/HomepageFragmentPagerAdapter;", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "mFolder", "Landroid/view/MenuItem;", "getMFolder", "()Landroid/view/MenuItem;", "setMFolder", "(Landroid/view/MenuItem;)V", "mMenuSetting", "mIsHyperOs", "mFragmentContent", "Landroid/view/View;", "getMFragmentContent", "()Landroid/view/View;", "setMFragmentContent", "(Landroid/view/View;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "toSettings", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onResume", "onStop", "onDestroy", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onViewInflated", "view", "initViewModel", "setupViewPager", "bundle", "setupHyperOsViewPager", "position", "", "actionBar", "Lmiuix/appcompat/app/ActionBar;", "setupMIUIViewPager", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", CloudPushConstants.XML_ITEM, "setActionBarVisibility", "mode", "Lmiuix/navigator/Navigator$Mode;", "onUpdateArguments", "newArguments", "switchTabState", "createNoteListFragment", "createTodoListFragment", "canViewPagerScroll", "smallWindowAdapter", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class ContentFragment extends Fragment implements NavigatorFragmentListener {
    public static final String ARG_DATA_ID = "data_id";
    public static final String ARG_PAGE = "page";
    public static final int ARG_PAGE_NOTE = 0;
    public static final int ARG_PAGE_TODO = 1;
    public static final int INDEX_NOTELIST_FRAGMENT = 0;
    public static final int INDEX_TODOLIST_FRAGMENT = 1;
    private final String TAG = "ContentFragment";
    protected HomeViewModel homeViewModel;
    private MenuItem mFolder;
    private View mFragmentContent;
    protected boolean mIsHyperOs;
    private boolean mIsTabSelected;
    protected MenuItem mMenuSetting;
    private HomepageSpringBackLayout mSpringBackLayout;
    protected HomepageViewPager mViewPager;
    protected HomepageFragmentPagerAdapter mVpFragmentAdapter;

    protected boolean canViewPagerScroll() {
        return true;
    }

    protected String getTAG() {
        return this.TAG;
    }

    protected final MenuItem getMFolder() {
        return this.mFolder;
    }

    protected final void setMFolder(MenuItem menuItem) {
        this.mFolder = menuItem;
    }

    protected final View getMFragmentContent() {
        return this.mFragmentContent;
    }

    protected final void setMFragmentContent(View view) {
        this.mFragmentContent = view;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeRes(R.style.NotesNavigatorContentTheme);
        this.mIsHyperOs = UIUtils.isHyperOsAndAbove();
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(fragmentActivityRequireActivity).get(HomeViewModel.class);
        initViewModel();
        Logger.INSTANCE.d(getTAG(), "onCreate:isHyperOS:" + this.mIsHyperOs);
    }

    protected void toSettings() {
        NotesPreferenceActivity.open(getActivity());
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        long jCurrentTimeMillis = System.currentTimeMillis();
        View viewInflate = inflater.inflate(R.layout.homepage_fragment, container, false);
        Logger.INSTANCE.d(getTAG(), "onInflateView ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
        return viewInflate;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        smallWindowAdapter();
        Logger.INSTANCE.d(getTAG(), "content onResume");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.d(getTAG(), "content onStop");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        com.miui.notes.base.utils.Logger.INSTANCE.d(getTAG(), "content onDestroy");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        smallWindowAdapter();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        com.miui.notes.base.utils.Logger.INSTANCE.d(getTAG(), "onViewInflated");
        super.onViewInflated(view, savedInstanceState);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mSpringBackLayout = (HomepageSpringBackLayout) view.findViewById(R.id.sbl_fragments);
        this.mViewPager = (HomepageViewPager) view.findViewById(R.id.vp_fragments);
        this.mFragmentContent = view.findViewById(R.id.fragment_content);
        HomepageViewPager homepageViewPager = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager);
        homepageViewPager.setScroll(true);
        setCorrectNestedScrollMotionEventEnabled(true);
        setupViewPager(savedInstanceState);
        smallWindowAdapter();
        Logger.INSTANCE.d(getTAG(), "onViewInflated ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
    }

    private final void initViewModel() {
        HomeViewModel homeViewModel = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel);
        ContentFragment contentFragment = this;
        homeViewModel.getNoteMultiModeLiveData().observe(contentFragment, new ContentFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.notes.home.ContentFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentFragment.initViewModel$lambda$0(this.f$0, (Integer) obj);
            }
        }));
        HomeViewModel homeViewModel2 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel2);
        homeViewModel2.getDragModeLiveData().observe(contentFragment, new ContentFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.notes.home.ContentFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentFragment.initViewModel$lambda$1(this.f$0, (Integer) obj);
            }
        }));
        HomeViewModel homeViewModel3 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel3);
        homeViewModel3.getDragModeLiveData().observe(contentFragment, new Observer() { // from class: com.miui.notes.home.ContentFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ContentFragment.initViewModel$lambda$2(this.f$0, (Integer) obj);
            }
        });
        HomeViewModel homeViewModel4 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel4);
        homeViewModel4.getVpScrollableStatusLiveData().observe(contentFragment, new Observer() { // from class: com.miui.notes.home.ContentFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ContentFragment.initViewModel$lambda$3(this.f$0, ((Boolean) obj).booleanValue());
            }
        });
        HomeViewModel homeViewModel5 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel5);
        homeViewModel5.getCurrentItemLiveData().observe(contentFragment, new Observer() { // from class: com.miui.notes.home.ContentFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ContentFragment.initViewModel$lambda$4(this.f$0, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initViewModel$lambda$0(ContentFragment contentFragment, Integer num) {
        if (num != null && num.intValue() == 1) {
            HomepageSpringBackLayout homepageSpringBackLayout = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout);
            homepageSpringBackLayout.internalRequestDisallowInterceptTouchEvent(true);
        } else if (num != null && num.intValue() == 0) {
            HomepageSpringBackLayout homepageSpringBackLayout2 = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout2);
            homepageSpringBackLayout2.internalRequestDisallowInterceptTouchEvent(false);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initViewModel$lambda$1(ContentFragment contentFragment, Integer num) {
        if (num != null && num.intValue() == 1) {
            HomepageSpringBackLayout homepageSpringBackLayout = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout);
            homepageSpringBackLayout.internalRequestDisallowInterceptTouchEvent(true);
        } else if (num != null && num.intValue() == 0) {
            HomepageSpringBackLayout homepageSpringBackLayout2 = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout2);
            homepageSpringBackLayout2.internalRequestDisallowInterceptTouchEvent(false);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViewModel$lambda$2(ContentFragment contentFragment, Integer num) {
        if (num != null && num.intValue() == 1) {
            HomepageSpringBackLayout homepageSpringBackLayout = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout);
            homepageSpringBackLayout.internalRequestDisallowInterceptTouchEvent(true);
        } else if (num != null && num.intValue() == 0) {
            HomepageSpringBackLayout homepageSpringBackLayout2 = contentFragment.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout2);
            homepageSpringBackLayout2.internalRequestDisallowInterceptTouchEvent(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViewModel$lambda$3(ContentFragment contentFragment, boolean z) {
        HomepageViewPager homepageViewPager = contentFragment.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager);
        homepageViewPager.setScroll(z && contentFragment.canViewPagerScroll());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViewModel$lambda$4(ContentFragment contentFragment, Integer num) {
        if (num != null && num.intValue() == 0) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(contentFragment.getTAG(), "setupViewPager: note selected");
            HomepageViewPager homepageViewPager = contentFragment.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager);
            homepageViewPager.setCurrentItem(0);
            return;
        }
        if (num != null && num.intValue() == 1) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(contentFragment.getTAG(), "setupViewPager: todo selected");
            HomepageViewPager homepageViewPager2 = contentFragment.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager2);
            homepageViewPager2.setCurrentItem(1);
        }
    }

    static /* synthetic */ void setupViewPager$default(ContentFragment contentFragment, Bundle bundle, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setupViewPager");
        }
        if ((i & 1) != 0) {
            bundle = null;
        }
        contentFragment.setupViewPager(bundle);
    }

    private final void setupViewPager(Bundle bundle) {
        Logger.INSTANCE.d(getTAG(), "setupViewPager " + bundle);
        final ActionBar actionBar = getActionBar();
        ContentFragment contentFragment = this;
        final Navigator navigator = Navigator.get(contentFragment);
        this.mVpFragmentAdapter = new HomepageFragmentPagerAdapter(contentFragment, new OnCreateFragmentCallback() { // from class: com.miui.notes.home.ContentFragment.setupViewPager.1
            @Override // com.miui.notes.adapter.OnCreateFragmentCallback
            public int getItemCount() {
                return 2;
            }

            @Override // com.miui.notes.adapter.OnCreateFragmentCallback
            public Fragment onCreateFragment(int position) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onCreateFragment:" + position);
                if (position == 0) {
                    return ContentFragment.this.createNoteListFragment();
                }
                return ContentFragment.this.createTodoListFragment();
            }
        });
        Logger.INSTANCE.d(getTAG(), "set adapter with existCount: " + getChildFragmentManager().getFragments().size());
        HomepageViewPager homepageViewPager = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager);
        homepageViewPager.registerOnPageChangeCallback(new OriginalViewPager2.OnPageChangeCallback() { // from class: com.miui.notes.home.ContentFragment.setupViewPager.2
            @Override // androidx.viewpager2.widget.OriginalViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "viewpager2--onPageSelected:" + position);
                try {
                    if (ContentFragment.this.mIsHyperOs) {
                        Navigator navigator2 = navigator;
                        Intrinsics.checkNotNull(navigator2);
                        navigator2.selectTab(position);
                    }
                } catch (IndexOutOfBoundsException e) {
                    com.miui.notes.base.utils.Logger.INSTANCE.e(ContentFragment.this.getTAG(), "warn!!!!IndexOutOfBoundsException!!!! " + e);
                }
                if (actionBar != null) {
                    if (position == 1) {
                        if (ContentFragment.this.mIsHyperOs) {
                            actionBar.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.todo_module_name));
                        }
                        NotesPageStat.reportSwitchEvent(ContentFragment.this.mIsTabSelected);
                    } else {
                        if (ContentFragment.this.mIsHyperOs) {
                            actionBar.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.app_name));
                        }
                        ContentFragment.this.mIsTabSelected = false;
                    }
                }
                ContentFragment.this.switchTabState(position);
                LiveDataEventBus.with(LiveDataEventBus.SELECT_POSITION).setValue(Integer.valueOf(position));
                HomeViewModel homeViewModel = ContentFragment.this.homeViewModel;
                Intrinsics.checkNotNull(homeViewModel);
                homeViewModel.setGlobalComposeBtnShow(true, "onPageSelected");
            }

            @Override // androidx.viewpager2.widget.OriginalViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.INSTANCE.v(ContentFragment.this.getTAG(), "viewpager2--onPageScrolled: position = " + position + " positionOffset = " + positionOffset + " positionOffsetPixels = " + positionOffsetPixels);
                if (positionOffset == 0.0f || positionOffset == 1.0f) {
                    return;
                }
                HomeViewModel homeViewModel = ContentFragment.this.homeViewModel;
                Intrinsics.checkNotNull(homeViewModel);
                if (homeViewModel.getGlobalComposeBtnShow()) {
                    HomeViewModel homeViewModel2 = ContentFragment.this.homeViewModel;
                    Intrinsics.checkNotNull(homeViewModel2);
                    homeViewModel2.setGlobalComposeBtnShow(false, "onScrolling");
                }
            }

            @Override // androidx.viewpager2.widget.OriginalViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                Logger logger = Logger.INSTANCE;
                String tag = ContentFragment.this.getTAG();
                HomeViewModel homeViewModel = ContentFragment.this.homeViewModel;
                Intrinsics.checkNotNull(homeViewModel);
                logger.d(tag, "viewpager2--onPageScrollStateChanged: state = " + state + " AddButtonValue = " + homeViewModel.getGlobalComposeBtnShow());
                if (state == 0) {
                    HomeViewModel homeViewModel2 = ContentFragment.this.homeViewModel;
                    Intrinsics.checkNotNull(homeViewModel2);
                    if (!homeViewModel2.getGlobalComposeBtnShow()) {
                        HomeViewModel homeViewModel3 = ContentFragment.this.homeViewModel;
                        Intrinsics.checkNotNull(homeViewModel3);
                        homeViewModel3.setGlobalComposeBtnShow(true, "statle idle");
                    }
                    Logger.INSTANCE.d(ContentFragment.this.getTAG(), "viewpager2--onPageScrollStateChanged: state = idle");
                }
                if (state == 1) {
                    Logger.INSTANCE.d(ContentFragment.this.getTAG(), "viewpager2--onPageScrollStateChanged: state = drag");
                }
                if (state == 2) {
                    Logger.INSTANCE.d(ContentFragment.this.getTAG(), "viewpager2--onPageScrollStateChanged: state = settling");
                }
            }
        });
        HomepageViewPager homepageViewPager2 = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager2);
        homepageViewPager2.setOffscreenPageLimit(1);
        HomepageViewPager homepageViewPager3 = this.mViewPager;
        Intrinsics.checkNotNull(homepageViewPager3);
        homepageViewPager3.setAdapter(this.mVpFragmentAdapter);
        if (getArguments() != null && requireArguments().containsKey(ARG_PAGE)) {
            int i = requireArguments().getInt(ARG_PAGE);
            Logger.INSTANCE.d(getTAG(), "position page " + i);
            HomepageViewPager homepageViewPager4 = this.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager4);
            homepageViewPager4.setCurrentItem(i, false);
            Logger.INSTANCE.d(getTAG(), "setup vp: do set current item :" + i);
            if (navigator != null) {
                Menu bottomTabMenu = navigator.getBottomTabMenu();
                Intrinsics.checkNotNullExpressionValue(bottomTabMenu, "getBottomTabMenu(...)");
                if (bottomTabMenu.size() != 0) {
                    Menu bottomTabMenu2 = navigator.getBottomTabMenu();
                    Intrinsics.checkNotNullExpressionValue(bottomTabMenu2, "getBottomTabMenu(...)");
                    bottomTabMenu2.getItem(i).setChecked(true);
                }
            }
            if (this.mIsHyperOs) {
                setupHyperOsViewPager(i, actionBar);
            } else {
                setupMIUIViewPager(actionBar);
            }
            switchTabState(i);
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            homeViewModel.setGlobalComposeBtnShow(true, "setupViewPager init");
        }
        Logger.INSTANCE.d(getTAG(), "set vp finished");
    }

    private final void setupHyperOsViewPager(int position, ActionBar actionBar) {
        if (actionBar == null) {
            return;
        }
        if (position == 1) {
            actionBar.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.todo_module_name));
        } else {
            actionBar.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.app_name));
        }
    }

    private final void setupMIUIViewPager(ActionBar actionBar) {
        if (actionBar == null || this.mViewPager == null) {
            return;
        }
        actionBar.setNavigationMode(2);
        androidx.appcompat.app.ActionBar.Tab tabNewTab = actionBar.newTab();
        if (RomUtils.isInternationalBuild()) {
            tabNewTab.setContentDescription(getString(R.string.app_name));
        } else {
            tabNewTab.setText(getString(R.string.app_name));
        }
        androidx.appcompat.app.ActionBar.Tab tabNewTab2 = actionBar.newTab();
        if (RomUtils.isInternationalBuild()) {
            tabNewTab2.setContentDescription(getString(R.string.todo_module_name));
        } else {
            tabNewTab2.setText(getString(R.string.todo_module_name));
        }
        actionBar.addTab(tabNewTab);
        actionBar.addTab(tabNewTab2);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        tabNewTab.setTabListener(new androidx.appcompat.app.ActionBar.TabListener() { // from class: com.miui.notes.home.ContentFragment.setupMIUIViewPager.1
            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabSelected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabSelected: " + ((Object) (tab != null ? tab.getText() : null)));
                if (NotesListActivity.sSelectedPageIndex != 0) {
                    com.miui.notes.base.utils.Logger.INSTANCE.d(ContentFragment.this.getTAG(), "setupViewPager: onTabChanged,do set current item notes");
                    HomepageViewPager homepageViewPager = ContentFragment.this.mViewPager;
                    Intrinsics.checkNotNull(homepageViewPager);
                    homepageViewPager.setCurrentItem(0);
                }
            }

            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabUnselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabUnselected: " + ((Object) (tab != null ? tab.getText() : null)));
            }

            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabReselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabReselected: " + ((Object) (tab != null ? tab.getText() : null)));
            }
        });
        tabNewTab2.setTabListener(new androidx.appcompat.app.ActionBar.TabListener() { // from class: com.miui.notes.home.ContentFragment.setupMIUIViewPager.2
            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabSelected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabSelected: " + ((Object) (tab != null ? tab.getText() : null)));
                ContentFragment.this.mIsTabSelected = true;
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabSelected: " + ((Object) (tab != null ? tab.getText() : null)) + ",do set current item todo");
                HomepageViewPager homepageViewPager = ContentFragment.this.mViewPager;
                Intrinsics.checkNotNull(homepageViewPager);
                homepageViewPager.setCurrentItem(1, true);
            }

            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabUnselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabUnselected: " + ((Object) (tab != null ? tab.getText() : null)));
            }

            @Override // androidx.appcompat.app.ActionBar.TabListener
            public void onTabReselected(androidx.appcompat.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Logger.INSTANCE.d(ContentFragment.this.getTAG(), "onTabReselected: " + ((Object) (tab != null ? tab.getText() : null)));
            }
        });
        if (RomUtils.isInternationalBuild() && UIUtils.isMiuiXIISdkSupported()) {
            actionBar.setExpandState(0);
            actionBar.setResizable(false);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        this.mFolder = menu.findItem(R.id.action_folder);
        this.mMenuSetting = menu.findItem(R.id.action_setting);
        Navigator navigator = Navigator.get(this);
        if (navigator != null) {
            setActionBarVisibility(navigator.getNavigationMode());
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.action_folder) {
            if (Utils.isFastClick(R.id.action_folder, 500L)) {
                return true;
            }
            Logger.INSTANCE.d(getTAG(), "action folder click,to folder page");
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            homeViewModel.setToFolderList();
            return true;
        }
        if (itemId != R.id.action_setting || Utils.isFastClick(R.id.action_folder, 500L)) {
            return true;
        }
        Logger.INSTANCE.d(getTAG(), "action settings click,to settings");
        toSettings();
        NotesPageStat.reportClickEvent(NotesPageStat.TIP_HOMEPAGE_SETTING_CLICK, null);
        return true;
    }

    protected final void setActionBarVisibility(Navigator.Mode mode) {
        MenuItem menuItem = this.mFolder;
        if (menuItem != null) {
            Intrinsics.checkNotNull(menuItem);
            menuItem.setVisible((!this.mIsHyperOs || mode == Navigator.Mode.NLC || NotesListActivity.sSelectedPageIndex == 1) ? false : true);
        }
        MenuItem menuItem2 = this.mMenuSetting;
        if (menuItem2 != null) {
            Intrinsics.checkNotNull(menuItem2);
            menuItem2.setVisible(mode != Navigator.Mode.NLC);
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0074  */
    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle newArguments) {
        boolean z;
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        super.onUpdateArguments(newArguments);
        HomepageViewPager homepageViewPager = this.mViewPager;
        if (homepageViewPager != null) {
            Intrinsics.checkNotNull(homepageViewPager);
            int i = newArguments.getInt(ARG_PAGE, homepageViewPager.getCurrentItem());
            Logger.INSTANCE.d(getTAG(), "onUpdateArguments page:" + i + ",rtl:" + Utils.isRTL());
            this.mIsTabSelected = true;
            Logger.INSTANCE.d(getTAG(), "onUpdateArguments: do set current item :" + i);
            HomepageViewPager homepageViewPager2 = this.mViewPager;
            Intrinsics.checkNotNull(homepageViewPager2);
            if (!RomUtils.isPadDevice()) {
                HomepageViewPager homepageViewPager3 = this.mViewPager;
                Intrinsics.checkNotNull(homepageViewPager3);
                z = homepageViewPager3.isUserInputEnabled();
            }
            homepageViewPager2.setCurrentItem(i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void switchTabState(int position) {
        ActionBar actionBar;
        MenuItem menuItem;
        if (NotesListActivity.sSelectedPageIndex != position) {
            Logger.INSTANCE.d(getTAG(), "switchTabState:" + position + " , " + NotesListActivity.sSelectedPageIndex + "  ");
            PreferenceUtils.setPrefLastUseModule(getContext(), position);
            NotesListActivity.sSelectedPageIndex = position;
        }
        Logger.INSTANCE.d(getTAG(), "switchTabState:" + position);
        ContentFragment contentFragment = this;
        Navigator navigator = Navigator.get(contentFragment);
        boolean z = (navigator == null || navigator.getNavigationMode() == Navigator.Mode.NLC) ? false : true;
        if (position == 0) {
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            homeViewModel.selectPage(0);
            HomepageSpringBackLayout homepageSpringBackLayout = this.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout);
            homepageSpringBackLayout.setInTodo(false);
            if (this.mIsHyperOs && z && (menuItem = this.mFolder) != null) {
                Intrinsics.checkNotNull(menuItem);
                menuItem.setVisible(true);
            }
            BaseNoteApp.Companion companion = BaseNoteApp.INSTANCE;
            BaseNoteApp.isTodoInForeground = false;
            Navigator navigator2 = Navigator.get(contentFragment);
            HomeViewModel homeViewModel2 = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel2);
            navigator2.navigate(new EmptyNavigatorInfo((int) (homeViewModel2.getFolderId() - ((long) (-4097)))));
        } else {
            if (getActivity() != null) {
                requireActivity().getWindow().clearFlags(131072);
            }
            BaseNoteApp.Companion companion2 = BaseNoteApp.INSTANCE;
            BaseNoteApp.isTodoInForeground = true;
            HomeViewModel homeViewModel3 = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel3);
            homeViewModel3.selectPage(1);
            MenuItem menuItem2 = this.mFolder;
            if (menuItem2 != null) {
                Intrinsics.checkNotNull(menuItem2);
                menuItem2.setVisible(false);
            }
            HomepageSpringBackLayout homepageSpringBackLayout2 = this.mSpringBackLayout;
            Intrinsics.checkNotNull(homepageSpringBackLayout2);
            homepageSpringBackLayout2.setInTodo(true);
            Navigator.get(contentFragment).navigate(new EmptyNavigatorInfo(1));
        }
        if (this.mIsHyperOs || (actionBar = getActionBar()) == null) {
            return;
        }
        if (RomUtils.isInternationalBuild()) {
            actionBar.getTabAt(0).setIcon(position == 0 ? R.drawable.ic_tab_global_notes_selected : R.drawable.ic_tab_global_notes_normal);
            actionBar.getTabAt(1).setIcon(position == 1 ? R.drawable.ic_tab_global_tasks_selected : R.drawable.ic_tab_global_tasks_normal);
        }
        actionBar.setSelectedNavigationItem(position);
    }

    protected Fragment createNoteListFragment() {
        return new PhoneNoteListPageFragment();
    }

    protected Fragment createTodoListFragment() {
        return new TodoListPageFragment();
    }

    private final void smallWindowAdapter() {
        final View view = this.mFragmentContent;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.home.ContentFragment$smallWindowAdapter$1$1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    Intrinsics.checkNotNullParameter(v, "v");
                    Intrinsics.checkNotNullParameter(insets, "insets");
                    Activity activity = UIUtils.getActivity(this.this$0.getContext());
                    if (activity != null && UIUtils.isInMultiWindowMode(activity)) {
                        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(v);
                        if (rootWindowInsets == null) {
                            return insets;
                        }
                        Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        Intrinsics.checkNotNullExpressionValue(insetsIgnoringVisibility, "getInsetsIgnoringVisibility(...)");
                        view.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                        return insets;
                    }
                    view.setPadding(0, 0, 0, 0);
                    return insets;
                }
            });
        }
    }
}
