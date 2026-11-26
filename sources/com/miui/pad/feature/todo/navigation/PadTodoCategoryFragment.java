package com.miui.pad.feature.todo.navigation;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.detail.BaseTodoDetailFragment;
import com.miui.notes.detail.PadTodoDetailFragment;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.SyncStateController;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.data.provider.TodoProvider;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.appcompat.app.Fragment;
import miuix.micloudview.MiCloudStateView;
import miuix.micloudview.accounts.ExtraAccountManager;
import miuix.navigator.Navigator;
import miuix.navigator.navigatorinfo.UpdateDetailFragmentNavInfo;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoCategoryFragment extends Fragment {
    private static final String TAG = "PadTodoCategoryFragment";
    public static final String TODO_LIST_TYPE = "todo_list_type";
    private View cancleView;
    private HomeViewModel homeViewModel;
    private MiCloudStateView mMiCloudStateView;
    private NestedHeaderLayout mNestedHeaderLayout;
    private View mRootView;
    private View mSearchBar;
    private TextView mSearchBarTextView;
    private PadTodoSearchCallback mSearchCallBack;
    private SyncStateController mSyncStateController;
    private TodoCategoryAdapter mTodoCategoryListAdapter;
    private RecyclerView mTodoCategoryRv;
    private TodoNumObserver mTodoNumObserver;
    private ActivityResultLauncher<Intent> toCtaBeforeCloudLauncher;
    private int mCurrentCategory = 0;
    private int mLastCurrentCategory = 0;
    private boolean mInSearchMode = false;
    private View.OnClickListener mTodoCategoryListener = new View.OnClickListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            PadTodoCategoryFragment padTodoCategoryFragment = PadTodoCategoryFragment.this;
            padTodoCategoryFragment.openTodoCategory(padTodoCategoryFragment.mTodoCategoryRv.getChildAdapterPosition(view));
        }
    };
    private View.OnLayoutChangeListener mLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.2
        boolean firstLoadView = true;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (this.firstLoadView) {
                this.firstLoadView = false;
                if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                    return;
                }
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setAutoAllClose(false);
            }
        }
    };
    private final SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.3
        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String str) {
            return false;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            if (TextUtils.isEmpty(str)) {
                if (PadTodoCategoryFragment.this.mCurrentCategory == 3) {
                    PadTodoCategoryFragment padTodoCategoryFragment = PadTodoCategoryFragment.this;
                    padTodoCategoryFragment.mCurrentCategory = padTodoCategoryFragment.mLastCurrentCategory;
                }
                if (PadTodoCategoryFragment.this.mTodoCategoryListAdapter != null) {
                    PadTodoCategoryFragment.this.mTodoCategoryListAdapter.setSelectedType(PadTodoCategoryFragment.this.mCurrentCategory);
                }
            } else {
                if (PadTodoCategoryFragment.this.mCurrentCategory != 3) {
                    PadTodoCategoryFragment padTodoCategoryFragment2 = PadTodoCategoryFragment.this;
                    padTodoCategoryFragment2.mLastCurrentCategory = padTodoCategoryFragment2.mCurrentCategory;
                }
                PadTodoCategoryFragment.this.mCurrentCategory = 3;
                if (PadTodoCategoryFragment.this.mTodoCategoryListAdapter != null) {
                    PadTodoCategoryFragment.this.mTodoCategoryListAdapter.setSelectedType(PadTodoCategoryFragment.this.mCurrentCategory);
                    PadTodoCategoryFragment.this.mTodoCategoryListAdapter.notifyDataSetChanged();
                }
            }
            if (PadTodoCategoryFragment.this.homeViewModel.getPageSelected() == 1) {
                PadTodoCategoryFragment padTodoCategoryFragment3 = PadTodoCategoryFragment.this;
                padTodoCategoryFragment3.searchNavi(padTodoCategoryFragment3.mCurrentCategory, str);
            }
            return true;
        }
    };
    private final PadTodoSearchCallback.OnSearchListener mSearchListener = new PadTodoSearchCallback.OnSearchListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.4
        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void onSearchModeAnimUpdate(boolean z, float f) {
        }

        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void onSearchModeAnimStart(boolean z) {
            if (PadTodoCategoryFragment.this.isAdded() && z) {
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setInSearchMode(true);
            }
        }

        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void onSearchModeAnimStop(boolean z) {
            if (PadTodoCategoryFragment.this.isAdded() && !z) {
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setInSearchMode(false);
            }
        }

        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
            if (PadTodoCategoryFragment.this.isAdded()) {
                PadTodoCategoryFragment.this.homeViewModel.setTodoKeyboardChange(true);
                PadTodoCategoryFragment.this.mInSearchMode = true;
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setTriggerViewVisible(false);
                PadTodoCategoryFragment.this.setForAccessibility(true);
                PadTodoCategoryFragment padTodoCategoryFragment = PadTodoCategoryFragment.this;
                padTodoCategoryFragment.cancleView = padTodoCategoryFragment.getActivity().getWindow().getDecorView().findViewById(R.id.search_text_cancel);
                AccessibilityDelegateUtil.setButtonAccessibilityDelegate(PadTodoCategoryFragment.this.cancleView);
            }
        }

        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void onDestroySearchMode(ActionMode actionMode) {
            if (PadTodoCategoryFragment.this.isAdded()) {
                PadTodoCategoryFragment.this.homeViewModel.setTodoKeyboardChange(false);
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setTriggerViewVisible(true);
                PadTodoCategoryFragment.this.setForAccessibility(false);
            }
        }

        @Override // com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.OnSearchListener
        public void updateExitSearch() {
            PadTodoCategoryFragment.this.mInSearchMode = false;
        }
    };
    private MiCloudStateView.ILayoutUpdateListener mLayoutUpdateListener = new MiCloudStateView.ILayoutUpdateListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.8
        @Override // miuix.micloudview.MiCloudStateView.ILayoutUpdateListener
        public void onLayoutUpdate(boolean z, boolean z2, int[] iArr) {
            if (z && iArr != null && iArr.length > 0) {
                int i = 0;
                for (int i2 : iArr) {
                    i += i2;
                }
                if (i == 0 && PadTodoCategoryFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                    PadTodoCategoryFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
                    return;
                }
            }
            if (PadTodoCategoryFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                PadTodoCategoryFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
            }
        }
    };
    private final View.OnClickListener mCloudOnClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.9
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (Utils.isFastClick(view.getId(), 500L)) {
                return;
            }
            if (!PreferenceUtils.isCTAAccepted(PadTodoCategoryFragment.this.getContext()) && PermissionUtils.supportNewPermissionStyle()) {
                PermissionNewUtils.showCtaDialog(PadTodoCategoryFragment.this.toCtaBeforeCloudLauncher);
            } else {
                PadTodoCategoryFragment.this.fragmentViewCloud();
            }
        }
    };

    public void setForAccessibility(boolean z) {
        NestedHeaderLayout nestedHeaderLayout;
        int i = z ? 4 : 1;
        if (this.mTodoCategoryRv == null || (nestedHeaderLayout = this.mNestedHeaderLayout) == null) {
            return;
        }
        nestedHeaderLayout.setImportantForAccessibility(i);
        this.mTodoCategoryRv.setImportantForAccessibility(i);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mCurrentCategory = bundle.getInt("todo_list_type", 0);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                this.mCurrentCategory = arguments.getInt("todo_list_type", 0);
            }
        }
        setThemeRes(R.style.TodoTheme_PageList);
        this.mTodoCategoryListAdapter = new TodoCategoryAdapter();
        this.mTodoNumObserver = new TodoNumObserver(this);
        initResultLauncher();
        this.mCurrentCategory = 0;
    }

    public void initResultLauncher() {
        this.toCtaBeforeCloudLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.5
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(PadTodoCategoryFragment.TAG, "toCtaBeforeCloud result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    PadTodoCategoryFragment.this.fragmentViewCloud();
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(PadTodoCategoryFragment.this.toCtaBeforeCloudLauncher);
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("todo_list_type", this.mCurrentCategory);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mSyncStateController.onResume();
        NoteApp.getInstance().getContentResolver().registerContentObserver(Uri.parse(TodoProvider.URI_TODO_PRIVATE), true, this.mTodoNumObserver);
        refreshTodoCategoryNum();
        if (isHideCloudGuide()) {
            this.mMiCloudStateView.setVisibility(8);
        } else {
            if (this.mInSearchMode) {
                return;
            }
            this.mMiCloudStateView.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mSyncStateController.onPause();
        NoteApp.getInstance().getContentResolver().unregisterContentObserver(this.mTodoNumObserver);
        LiveDataEventBus.with(LiveDataEventBus.CREATE_NEW_TODO).setValue(false);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mSyncStateController = null;
        unregisterCoordinateScrollView(this.mNestedHeaderLayout);
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            nestedHeaderLayout.removeOnLayoutChangeListener(this.mLayoutChangeListener);
            Folme.clean(this.mNestedHeaderLayout);
        }
        this.mTodoCategoryListAdapter = null;
        RecyclerView recyclerView = this.mTodoCategoryRv;
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
            this.mTodoCategoryRv.getRecycledViewPool().clear();
        }
        super.onDestroyView();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        Logger.INSTANCE.d(TAG, "Pad TodoCategory onContentInsetChanged");
        RecyclerView recyclerView = this.mTodoCategoryRv;
        if (recyclerView != null) {
            recyclerView.setPadding(recyclerView.getPaddingLeft(), this.mTodoCategoryRv.getPaddingTop(), this.mTodoCategoryRv.getPaddingRight(), rect.bottom);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        Logger.INSTANCE.d(TAG, "onViewInflated");
        registerCoordinateScrollView(this.mNestedHeaderLayout);
        HomeViewModel homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.homeViewModel = homeViewModel;
        homeViewModel.getPageSelectedLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$0((Integer) obj);
            }
        });
        if (this.homeViewModel.getPageSelected() == 1) {
            openTodoCategory(this.mCurrentCategory);
        }
        this.homeViewModel.getIntent().observe(this, new Observer() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$1((Intent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$0(Integer num) {
        if (num.intValue() == 1) {
            openTodoCategory(this.mCurrentCategory);
        } else if (num.intValue() == 0) {
            exitSearchMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$1(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !action.equals(TodoIntent.ACTION_SEARCH_NOTES)) {
            return;
        }
        onTextSearch(intent.getStringExtra(TodoIntent.EXTRA_SEARCH_TODO));
        this.homeViewModel.setIntent(new Intent("android.intent.action.MAIN"));
    }

    public void onTextSearch(String str) {
        View view = this.mSearchBar;
        if (view != null) {
            onSearchRequest(view, true, str);
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.pad_todo_category, viewGroup, false);
        this.mRootView = viewInflate;
        NestedHeaderLayout nestedHeaderLayout = (NestedHeaderLayout) viewInflate.findViewById(R.id.nhl_group);
        this.mNestedHeaderLayout = nestedHeaderLayout;
        nestedHeaderLayout.addOnLayoutChangeListener(this.mLayoutChangeListener);
        TodoCategoryAdapter todoCategoryAdapter = this.mTodoCategoryListAdapter;
        if (todoCategoryAdapter != null) {
            todoCategoryAdapter.setOnClickListener(this.mTodoCategoryListener);
            this.mTodoCategoryListAdapter.setSelectedType(this.mCurrentCategory);
        }
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.todo_category_list);
        this.mTodoCategoryRv = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mTodoCategoryRv.setRecycledViewPool(new androidx.recyclerview.widget.RecyclerView.RecycledViewPool());
        this.mTodoCategoryRv.setAdapter(this.mTodoCategoryListAdapter);
        View viewFindViewById = viewInflate.findViewById(R.id.search_bar);
        this.mSearchBar = viewFindViewById;
        TextView textView = (TextView) viewFindViewById.findViewById(android.R.id.input);
        this.mSearchBarTextView = textView;
        textView.setHint(getString(R.string.todo_search_hint));
        this.mSearchBar.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PadTodoCategoryFragment padTodoCategoryFragment = PadTodoCategoryFragment.this;
                padTodoCategoryFragment.onSearchRequest(padTodoCategoryFragment.mSearchBar, false, "");
                PadTodoCategoryFragment padTodoCategoryFragment2 = PadTodoCategoryFragment.this;
                padTodoCategoryFragment2.openTodoCategory(padTodoCategoryFragment2.mCurrentCategory);
            }
        });
        this.mSearchBarTextView.setTextSize(DisplayUtils.getXmlDef(getContext(), R.dimen.search_bar_text_size));
        MiCloudStateView miCloudStateView = (MiCloudStateView) viewInflate.findViewById(R.id.micloud_state_view);
        this.mMiCloudStateView = miCloudStateView;
        miCloudStateView.setLayoutUpdateListener(this.mLayoutUpdateListener);
        this.mSyncStateController = new SyncStateController(this.mMiCloudStateView);
        this.mMiCloudStateView.setOnClickListener(this.mCloudOnClickListener);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchRequest(View view, boolean z, final String str) {
        View view2;
        PadTodoSearchCallback padTodoSearchCallback = new PadTodoSearchCallback(getActivity(), this.mQueryTextListener, this.mSearchListener);
        this.mSearchCallBack = padTodoSearchCallback;
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        padTodoSearchCallback.setup(view, nestedHeaderLayout, nestedHeaderLayout.getScrollableView());
        startActionMode(this.mSearchCallBack);
        if (!z || (view2 = getView()) == null) {
            return;
        }
        view2.postDelayed(new Runnable() { // from class: com.miui.pad.feature.todo.navigation.PadTodoCategoryFragment.7
            @Override // java.lang.Runnable
            public void run() {
                if (PadTodoCategoryFragment.this.isAdded()) {
                    PadTodoCategoryFragment.this.mSearchCallBack.setSearchText(str, false, false);
                }
            }
        }, 300L);
    }

    public void exitSearchMode() {
        PadTodoSearchCallback padTodoSearchCallback = this.mSearchCallBack;
        if (padTodoSearchCallback != null) {
            padTodoSearchCallback.finish();
        }
    }

    public void searchNavi(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("todo_list_type", i);
        bundle.putString(BaseTodoDetailFragment.SEARCH_STRING, str);
        bundle.putBoolean(BaseTodoDetailFragment.FORCE_EXIT, true);
        bundle.putBoolean(BaseTodoDetailFragment.CAN_ITEM_LONG_CLICK, !this.mInSearchMode);
        Navigator.get(this).navigate(new UpdateDetailFragmentNavInfo(-1, PadTodoDetailFragment.class, bundle));
    }

    public void openTodoCategory(int i) {
        String searchText;
        PadTodoSearchCallback padTodoSearchCallback;
        Logger.INSTANCE.d(TAG, "openTodoCategory position:" + i);
        if (i == -1) {
            return;
        }
        TodoCategoryAdapter todoCategoryAdapter = this.mTodoCategoryListAdapter;
        if (todoCategoryAdapter != null) {
            todoCategoryAdapter.setSelectedType(i);
        }
        if (this.mInSearchMode && (padTodoSearchCallback = this.mSearchCallBack) != null) {
            searchText = padTodoSearchCallback.getSearchText();
        } else {
            searchText = "";
        }
        Bundle bundle = new Bundle();
        bundle.putInt("todo_list_type", i);
        bundle.putString(BaseTodoDetailFragment.SEARCH_STRING, searchText);
        bundle.putBoolean(BaseTodoDetailFragment.FORCE_EXIT, false);
        bundle.putBoolean(BaseTodoDetailFragment.CAN_ITEM_LONG_CLICK, !this.mInSearchMode);
        Navigator.get(this).navigate(new UpdateDetailFragmentNavInfo(-1, PadTodoDetailFragment.class, bundle));
        if (i == 0) {
            this.mCurrentCategory = 0;
        } else if (i == 1) {
            this.mCurrentCategory = 1;
        }
    }

    private static class TodoNumObserver extends ContentObserver {
        private WeakReference<PadTodoCategoryFragment> mRef;

        public TodoNumObserver(PadTodoCategoryFragment padTodoCategoryFragment) {
            super(new Handler(Looper.getMainLooper()));
            this.mRef = new WeakReference<>(padTodoCategoryFragment);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            PadTodoCategoryFragment padTodoCategoryFragment = this.mRef.get();
            if (padTodoCategoryFragment != null) {
                padTodoCategoryFragment.refreshTodoCategoryNum();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTodoCategoryNum() {
        int count;
        Cursor cursorQueryAllRealUnFinishItem = TodoProviderAccessUtils.queryAllRealUnFinishItem();
        int count2 = 0;
        if (cursorQueryAllRealUnFinishItem != null) {
            count = cursorQueryAllRealUnFinishItem.getCount();
            cursorQueryAllRealUnFinishItem.close();
        } else {
            count = 0;
        }
        Cursor cursorQueryAllRealFinishItem = TodoProviderAccessUtils.queryAllRealFinishItem();
        if (cursorQueryAllRealFinishItem != null) {
            count2 = cursorQueryAllRealFinishItem.getCount();
            cursorQueryAllRealFinishItem.close();
        }
        TodoCategoryAdapter todoCategoryAdapter = this.mTodoCategoryListAdapter;
        if (todoCategoryAdapter != null) {
            int unFinishNum = todoCategoryAdapter.getUnFinishNum();
            int finishNum = this.mTodoCategoryListAdapter.getFinishNum();
            if (unFinishNum == count && finishNum == count2) {
                return;
            }
            this.mTodoCategoryListAdapter.setUnFinishNum(count);
            this.mTodoCategoryListAdapter.setFinishNum(count2);
            this.mTodoCategoryListAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fragmentViewCloud() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ExternalAppUtils.viewCloud(activity);
        }
    }

    private boolean isHideCloudGuide() {
        if (!LiteUtils.isSuperLite()) {
            return false;
        }
        if (!PreferenceUtils.isCTAAccepted(getContext())) {
            return true;
        }
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance());
        return !ContentResolver.getSyncAutomatically(xiaomiAccount, "notes") || xiaomiAccount == null;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.homeViewModel.getPageSelected() == 1 && keyEvent.hasModifiers(4096) && keyEvent.getRepeatCount() == 0 && i == 42 && this.mCurrentCategory == 0) {
            Logger.INSTANCE.d(TAG, "onKeyDown KEYCODE_N,CREATE_NEW_TODO");
            LiveDataEventBus.with(LiveDataEventBus.CREATE_NEW_TODO).setValue(true);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}
