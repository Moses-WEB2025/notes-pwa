package com.miui.notes.widget.notelist;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.hardware.DataSpace;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.BitmapCacheManager;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.ActionStateListener;
import com.miui.notes.ui.PhoneNumberCacheHelper;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.fragment.ModuleFragment;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.util.ResourceParser;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.appcompat.app.ActionBar;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.smartaction.SmartAction;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: compiled from: WidgetNoteListFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000 \u0087\u00012\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0004\u0086\u0001\u0087\u0001B\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\u0012\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010DH\u0016J\u0010\u0010E\u001a\u00020B2\b\u0010F\u001a\u0004\u0018\u00010GJ\u0012\u0010H\u001a\u00020B2\b\u0010F\u001a\u0004\u0018\u00010GH\u0002J\b\u0010I\u001a\u00020BH\u0016J\b\u0010J\u001a\u00020BH\u0016J\b\u0010K\u001a\u00020BH\u0016J\b\u0010L\u001a\u00020BH\u0016J\u0012\u0010M\u001a\u00020B2\b\u0010N\u001a\u0004\u0018\u00010DH\u0016J\"\u0010O\u001a\u00020\n2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020S2\b\u0010N\u001a\u0004\u0018\u00010DH\u0016J\n\u0010Y\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010Z\u001a\u00020BH\u0002J\b\u0010[\u001a\u00020BH\u0002J\u0010\u0010\\\u001a\u00020B2\u0006\u0010]\u001a\u00020\nH\u0002J\b\u0010^\u001a\u00020BH\u0002J\b\u0010a\u001a\u00020BH\u0002J\u0018\u0010b\u001a\u00020B2\u0006\u00108\u001a\u0002072\u0006\u00106\u001a\u000207H\u0002J\b\u0010c\u001a\u00020BH\u0002J\u0010\u0010g\u001a\u00020B2\u0006\u0010h\u001a\u00020iH\u0004J\u0012\u0010j\u001a\u00020B2\b\u0010k\u001a\u0004\u0018\u00010\bH\u0002J\u0012\u0010l\u001a\u00020B2\b\u0010k\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010m\u001a\u00020B2\u0006\u0010n\u001a\u00020*H\u0002J\b\u0010o\u001a\u00020BH\u0004J$\u0010p\u001a\u00020B2\b\u0010]\u001a\u0004\u0018\u00010\n2\u0006\u0010q\u001a\u00020*2\b\u0010k\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010r\u001a\u00020*H\u0016J\u0010\u0010s\u001a\u00020*2\u0006\u0010t\u001a\u00020\bH\u0016J\u0010\u0010u\u001a\u00020*2\u0006\u0010v\u001a\u00020\bH\u0016J\u0010\u0010w\u001a\u00020B2\u0006\u0010x\u001a\u00020*H\u0016J\u0018\u0010y\u001a\u00020B2\u0006\u0010x\u001a\u00020*2\u0006\u0010z\u001a\u00020{H\u0016J\u0010\u0010|\u001a\u00020B2\u0006\u0010x\u001a\u00020*H\u0016J\u001a\u0010}\u001a\u00020B2\u0006\u0010~\u001a\u00020\u007f2\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001H\u0016J\u0011\u0010\u0082\u0001\u001a\u00020B2\u0006\u0010~\u001a\u00020\u007fH\u0016J\u001a\u0010\u0083\u0001\u001a\u00020B2\u0006\u0010v\u001a\u00020\b2\u0007\u0010\u0084\u0001\u001a\u000207H\u0016J\t\u0010\u0085\u0001\u001a\u00020BH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0010\u0010/\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000207X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u0004\u0018\u00010;X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010T\u001a\u0002078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0014\u0010W\u001a\u0002078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bX\u0010VR\u000e\u0010_\u001a\u00020`X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010d\u001a\u0002038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\be\u0010f¨\u0006\u0088\u0001"}, d2 = {"Lcom/miui/notes/widget/notelist/WidgetNoteListFragment;", "Lcom/miui/notes/ui/fragment/ModuleFragment;", "Landroid/widget/SearchView$OnQueryTextListener;", "Lcom/miui/notes/ui/view/SearchCallback$OnSearchListener;", "Lcom/miui/notes/ui/view/SearchCallback$TextSearch;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mRootView", "Landroid/view/View;", "getMRootView", "()Landroid/view/View;", "setMRootView", "(Landroid/view/View;)V", "mSpringBackLayout", "Lmiuix/springback/view/SpringBackLayout;", "mBlankViewContainer", "mRecyclerView", "Lcom/miui/notes/ui/view/BaseNoteRecyclerView;", "getMRecyclerView", "()Lcom/miui/notes/ui/view/BaseNoteRecyclerView;", "setMRecyclerView", "(Lcom/miui/notes/ui/view/BaseNoteRecyclerView;)V", "mDefaultAnimator", "Landroidx/recyclerview/widget/RecyclerView$ItemAnimator;", "mSearchModeListener", "Lcom/miui/notes/ui/view/SearchCallback;", "mSearchBarTextView", "Landroid/widget/TextView;", "mSearchBar", "mAdapter", "Lcom/miui/notes/widget/notelist/WidgetNoteListAdapter;", "mBindContext", "Lcom/miui/notes/adapter/BindContext;", "mHandler", "Lcom/miui/notes/widget/notelist/MHandler;", "getMHandler", "()Lcom/miui/notes/widget/notelist/MHandler;", "setMHandler", "(Lcom/miui/notes/widget/notelist/MHandler;)V", "mInSearchMode", "", "getMInSearchMode", "()Z", "setMInSearchMode", "(Z)V", "mSearchWords", "mIsUpdateText", "mText", "mLoaderCallbacks", "Lcom/miui/notes/tool/util/CursorLoaderBuilder$DefaultLoaderCallbacks;", "mActivity", "Landroid/app/Activity;", "widgetId", "", "widgetType", "fromEdit", "nestedHeaderLayout", "Lmiuix/nestedheader/widget/NestedHeaderLayout;", "mSetItemAnimatorRunnable", "Ljava/lang/Runnable;", "mDataEmptyRunnable", "mHandlePhoneNumberRunnable", "mItemAnimatorRunnable", "onCreate", "", "bundle", "Landroid/os/Bundle;", "onNewIntent", "intent", "Landroid/content/Intent;", "obtainIntent", "onStart", "onDestroyView", "onDestroy", "onFragmentResume", "onActivityCreated", "savedInstanceState", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "themeRes", "getThemeRes", "()I", "layoutRes", "getLayoutRes", "getSearchContent", "initActionBar", "finishPage", "initView", "view", "initAdapterIfNeeded", "mItemClickListener", "Landroid/view/View$OnClickListener;", "unbindWidget", "onWidgetChanged", "startQueryNote", "loaderCallbacks", "getLoaderCallbacks", "()Lcom/miui/notes/tool/util/CursorLoaderBuilder$DefaultLoaderCallbacks;", "onNotesLoadFinished", "data", "Landroid/database/Cursor;", "setSearchText", "text", "realSetSearchText", "onDataEmpty", "isEmpty", "cachePhoneNumberResource", "onSearchRequest", "isUpdateText", "onBackPressed", "onQueryTextSubmit", SmartAction.Feature.QUERY, "onQueryTextChange", "newText", "onSearchModeAnimStart", "animateToVisible", "onSearchModeAnimUpdate", "progress", "", "onSearchModeAnimStop", "onCreateSearchMode", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "onDestroySearchMode", "onTextSearch", "searchType", "finishIfNeeded", "NoteLoaderCallbacks", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class WidgetNoteListFragment extends ModuleFragment implements SearchView.OnQueryTextListener, SearchCallback.OnSearchListener, SearchCallback.TextSearch {
    private static final int REQUEST_CODE_QUERY_NORMAL = 0;
    private boolean fromEdit;
    private Activity mActivity;
    private WidgetNoteListAdapter mAdapter;
    private BindContext mBindContext;
    private View mBlankViewContainer;
    private RecyclerView.ItemAnimator mDefaultAnimator;
    private MHandler mHandler;
    private boolean mInSearchMode;
    private boolean mIsUpdateText;
    private CursorLoaderBuilder.DefaultLoaderCallbacks mLoaderCallbacks;
    private BaseNoteRecyclerView mRecyclerView;
    private View mRootView;
    private View mSearchBar;
    private TextView mSearchBarTextView;
    private SearchCallback mSearchModeListener;
    private String mSearchWords;
    private SpringBackLayout mSpringBackLayout;
    private String mText;
    private NestedHeaderLayout nestedHeaderLayout;
    private int widgetId;
    private final String TAG = "WidgetNoteListFragment";
    private int widgetType = -1;
    private final Runnable mSetItemAnimatorRunnable = new Runnable() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            WidgetNoteListFragment.mSetItemAnimatorRunnable$lambda$0(this.f$0);
        }
    };
    private final Runnable mDataEmptyRunnable = new Runnable() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            WidgetNoteListFragment.mDataEmptyRunnable$lambda$1(this.f$0);
        }
    };
    private final Runnable mHandlePhoneNumberRunnable = new Runnable() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda4
        @Override // java.lang.Runnable
        public final void run() {
            PhoneNumberCacheHelper.cachePhoneNumberResource();
        }
    };
    private final Runnable mItemAnimatorRunnable = new Runnable() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
            WidgetNoteListFragment.mItemAnimatorRunnable$lambda$3(this.f$0);
        }
    };
    private final View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda6
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            WidgetNoteListFragment.mItemClickListener$lambda$8(this.f$0, view);
        }
    };

    private final int getLayoutRes() {
        return R.layout.widget_note_list_fragment;
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public String getSearchContent() {
        return null;
    }

    protected int getThemeRes() {
        return R.style.NoteTheme_Widget_Note_List;
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public boolean onBackPressed() {
        return false;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String query) {
        Intrinsics.checkNotNullParameter(query, "query");
        return false;
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean animateToVisible, float progress) {
    }

    protected final View getMRootView() {
        return this.mRootView;
    }

    protected final void setMRootView(View view) {
        this.mRootView = view;
    }

    protected final BaseNoteRecyclerView getMRecyclerView() {
        return this.mRecyclerView;
    }

    protected final void setMRecyclerView(BaseNoteRecyclerView baseNoteRecyclerView) {
        this.mRecyclerView = baseNoteRecyclerView;
    }

    protected final MHandler getMHandler() {
        return this.mHandler;
    }

    protected final void setMHandler(MHandler mHandler) {
        this.mHandler = mHandler;
    }

    protected final boolean getMInSearchMode() {
        return this.mInSearchMode;
    }

    protected final void setMInSearchMode(boolean z) {
        this.mInSearchMode = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mSetItemAnimatorRunnable$lambda$0(WidgetNoteListFragment widgetNoteListFragment) {
        BaseNoteRecyclerView baseNoteRecyclerView = widgetNoteListFragment.mRecyclerView;
        if (baseNoteRecyclerView == null || widgetNoteListFragment.mDefaultAnimator == null) {
            return;
        }
        Intrinsics.checkNotNull(baseNoteRecyclerView);
        baseNoteRecyclerView.setItemAnimator(widgetNoteListFragment.mDefaultAnimator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mDataEmptyRunnable$lambda$1(WidgetNoteListFragment widgetNoteListFragment) {
        BaseNoteRecyclerView baseNoteRecyclerView = widgetNoteListFragment.mRecyclerView;
        if (baseNoteRecyclerView == null || widgetNoteListFragment.mBlankViewContainer == null || widgetNoteListFragment.mSpringBackLayout == null) {
            return;
        }
        Intrinsics.checkNotNull(baseNoteRecyclerView);
        baseNoteRecyclerView.setVisibility(8);
        View view = widgetNoteListFragment.mBlankViewContainer;
        Intrinsics.checkNotNull(view);
        view.setVisibility(0);
        SpringBackLayout springBackLayout = widgetNoteListFragment.mSpringBackLayout;
        Intrinsics.checkNotNull(springBackLayout);
        View view2 = widgetNoteListFragment.mBlankViewContainer;
        Intrinsics.checkNotNull(view2);
        springBackLayout.setTarget(view2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mItemAnimatorRunnable$lambda$3(WidgetNoteListFragment widgetNoteListFragment) {
        if (!widgetNoteListFragment.isAdded() || widgetNoteListFragment.getView() == null) {
            return;
        }
        widgetNoteListFragment.startActionMode(widgetNoteListFragment.mSearchModeListener);
        if (widgetNoteListFragment.mIsUpdateText) {
            widgetNoteListFragment.realSetSearchText(widgetNoteListFragment.mText);
        }
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        setThemeRes(getThemeRes());
        this.mBindContext = new BindContext();
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        this.mHandler = new MHandler(mainLooper);
        initAdapterIfNeeded();
        this.mLoaderCallbacks = getLoaderCallbacks();
        startQueryNote();
        try {
            Activity activity = this.mActivity;
            Intrinsics.checkNotNull(activity);
            obtainIntent(activity.getIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onNewIntent(Intent intent) {
        try {
            obtainIntent(intent);
            startQueryNote();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void obtainIntent(Intent intent) {
        if (this.mActivity != null) {
            this.fromEdit = false;
            if (intent != null) {
                if (TextUtils.equals(intent.getAction(), NoteIntent.ACTION_BIND_WIDGET)) {
                    this.widgetId = intent.getIntExtra("com.miui.notes.widget_id", 0);
                    this.widgetType = intent.getIntExtra("com.miui.notes.widget_type", -1);
                    return;
                }
                this.fromEdit = true;
                this.widgetId = intent.getIntExtra("appWidgetId", 0);
                Uri data = intent.getData();
                if (data != null) {
                    String str = data.getPathSegments().get(3);
                    Intrinsics.checkNotNullExpressionValue(str, "get(...)");
                    this.widgetType = Integer.parseInt(str);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        startQueryNote();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        View view = getView();
        if (view != null) {
            view.removeCallbacks(this.mSetItemAnimatorRunnable);
            view.removeCallbacks(this.mItemAnimatorRunnable);
        }
        NestedHeaderLayout nestedHeaderLayout = this.nestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            Folme.clean(nestedHeaderLayout);
        }
        super.onDestroyView();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        getLoaderManager().destroyLoader(0);
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            Intrinsics.checkNotNull(baseNoteRecyclerView);
            baseNoteRecyclerView.removeCallbacks(this.mDataEmptyRunnable);
        }
        MHandler mHandler = this.mHandler;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (this.mSearchModeListener != null) {
            this.mSearchModeListener = null;
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public void onFragmentResume() {
        super.onFragmentResume();
        if (isAdded()) {
            BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(baseNoteRecyclerView);
            baseNoteRecyclerView.setItemAnimator(null);
            startQueryNote();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(container, "container");
        View viewInflate = inflater.inflate(getLayoutRes(), container, false);
        Intrinsics.checkNotNull(viewInflate);
        initView(viewInflate);
        return viewInflate;
    }

    private final void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setExpandState(0);
            ImageView imageView = new ImageView(BaseNoteApp.INSTANCE.getInstance());
            imageView.setBackgroundResource(R.drawable.miui_action_bar_cancel);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.finishPage();
                }
            });
            imageView.setContentDescription(getResources().getString(R.string.dialog_button_close));
            actionBar.setStartView(imageView);
            ImageView imageView2 = new ImageView(BaseNoteApp.INSTANCE.getInstance());
            imageView2.setBackgroundResource(R.drawable.ic_widget_note_list_add_note);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WidgetNoteListFragment.initActionBar$lambda$6(this.f$0, view);
                }
            });
            imageView2.setContentDescription(getResources().getString(R.string.menu_add_description));
            actionBar.setEndView(imageView2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActionBar$lambda$6(WidgetNoteListFragment widgetNoteListFragment, View view) {
        if (widgetNoteListFragment.fromEdit) {
            widgetNoteListFragment.unbindWidget();
            widgetNoteListFragment.onWidgetChanged(widgetNoteListFragment.widgetType, widgetNoteListFragment.widgetId);
        }
        Intent intent = new Intent(BaseNoteApp.INSTANCE.getInstance(), (Class<?>) EditActivity.class);
        intent.setFlags(268468224);
        intent.putExtra("com.miui.notes.folder_id", 0);
        intent.putExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, ResourceParser.getDefaultBgId(null));
        intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.EDIT);
        intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, 0);
        intent.putExtra(NoteIntent.KEY_IS_PENDING, false);
        intent.putExtra("com.miui.notes.widget_id", widgetNoteListFragment.widgetId);
        intent.putExtra("com.miui.notes.widget_type", widgetNoteListFragment.widgetType);
        intent.setAction("android.intent.action.INSERT_OR_EDIT");
        widgetNoteListFragment.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finishPage() {
        Activity activity = this.mActivity;
        if (activity != null) {
            Intrinsics.checkNotNull(activity);
            activity.finishAndRemoveTask();
        }
    }

    private final void initView(View view) {
        this.mRootView = view;
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) view).setMotionEventSplittingEnabled(false);
        this.mSpringBackLayout = (SpringBackLayout) view.findViewById(R.id.scrollable_view_drawer);
        this.nestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nested_header_layout);
        this.mBlankViewContainer = view.findViewById(R.id.sv_blank);
        this.mRecyclerView = (BaseNoteRecyclerView) view.findViewById(R.id.recycler_view);
        SpringBackLayout springBackLayout = this.mSpringBackLayout;
        Intrinsics.checkNotNull(springBackLayout);
        springBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment.initView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                Intrinsics.checkNotNullParameter(view2, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                int dimensionPixelOffset = WidgetNoteListFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                outline.setRoundRect(WidgetNoteListFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_single_column_recycler_horizontal_padding), WidgetNoteListFragment.this.getResources().getDimensionPixelOffset(R.dimen.data_list_padding_top), view2.getWidth() - WidgetNoteListFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_single_column_recycler_horizontal_padding), view2.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
            }
        });
        SpringBackLayout springBackLayout2 = this.mSpringBackLayout;
        Intrinsics.checkNotNull(springBackLayout2);
        springBackLayout2.setClipToOutline(true);
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView);
        baseNoteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        BaseNoteRecyclerView baseNoteRecyclerView2 = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView2);
        this.mDefaultAnimator = baseNoteRecyclerView2.getItemAnimator();
        BaseNoteRecyclerView baseNoteRecyclerView3 = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView3);
        baseNoteRecyclerView3.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        BaseNoteRecyclerView baseNoteRecyclerView4 = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView4);
        baseNoteRecyclerView4.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapterIfNeeded();
        BaseNoteRecyclerView baseNoteRecyclerView5 = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView5);
        baseNoteRecyclerView5.setAdapter(this.mAdapter);
        View viewFindViewById = view.findViewById(R.id.search_bar);
        this.mSearchBar = viewFindViewById;
        Intrinsics.checkNotNull(viewFindViewById);
        this.mSearchBarTextView = (TextView) viewFindViewById.findViewById(android.R.id.input);
        View view2 = this.mSearchBar;
        Intrinsics.checkNotNull(view2);
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.onSearchRequest(view3, false, "");
            }
        });
        TextView textView = this.mSearchBarTextView;
        Intrinsics.checkNotNull(textView);
        textView.setTextSize(DisplayUtils.getXmlDef(getContext(), R.dimen.search_bar_text_size));
        TextView textView2 = this.mSearchBarTextView;
        Intrinsics.checkNotNull(textView2);
        textView2.setHint(R.string.search_hint);
        TextView textView3 = this.mSearchBarTextView;
        Intrinsics.checkNotNull(textView3);
        textView3.setTextColor(getResources().getColor(R.color.v11_text_appearance_secondary_text_color));
        if (DisplayUtils.isNightMode()) {
            TextView textView4 = this.mSearchBarTextView;
            Intrinsics.checkNotNull(textView4);
            textView4.setHintTextColor(getResources().getColor(R.color.action_bar_search_hint_color));
        }
    }

    private final void initAdapterIfNeeded() {
        if (this.mAdapter == null) {
            WidgetNoteListAdapter widgetNoteListAdapter = new WidgetNoteListAdapter();
            this.mAdapter = widgetNoteListAdapter;
            Intrinsics.checkNotNull(widgetNoteListAdapter);
            widgetNoteListAdapter.setOnClickListener(this.mItemClickListener);
            WidgetNoteListAdapter widgetNoteListAdapter2 = this.mAdapter;
            Intrinsics.checkNotNull(widgetNoteListAdapter2);
            widgetNoteListAdapter2.setBindContext(this.mBindContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mItemClickListener$lambda$8(WidgetNoteListFragment widgetNoteListFragment, View view) {
        BaseNoteRecyclerView baseNoteRecyclerView = widgetNoteListFragment.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView);
        int childLayoutPosition = baseNoteRecyclerView.getChildLayoutPosition(view);
        WidgetNoteListAdapter widgetNoteListAdapter = widgetNoteListFragment.mAdapter;
        Intrinsics.checkNotNull(widgetNoteListAdapter);
        ItemCache item = widgetNoteListAdapter.getItem(childLayoutPosition);
        if (item != null) {
            Object cacheObject = item.getCacheObject();
            Intrinsics.checkNotNull(cacheObject, "null cannot be cast to non-null type com.miui.notes.cache.NoteCache");
            NoteModel note = ((NoteCache) cacheObject).getNote();
            if (note.getWidgetId() != 0 && AppWidgetManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).getAppWidgetInfo(note.getWidgetId()) != null) {
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.error_widget_exist_for_send_to_desktop, 0).show();
                Logger.INSTANCE.d(widgetNoteListFragment.TAG, "already exist,widgetId :" + note.getWidgetId() + " id " + note.getId());
                return;
            }
            if (item.getCacheType() == 0) {
                Object cacheObject2 = item.getCacheObject();
                Intrinsics.checkNotNull(cacheObject2, "null cannot be cast to non-null type com.miui.notes.cache.NoteCache");
                NoteCache noteCache = (NoteCache) cacheObject2;
                if (noteCache.getNoteContentType() == 5) {
                    if (LiteUtils.isSuperLite()) {
                        Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.note_mindnote_not_support_in_phone_model, 0).show();
                        return;
                    }
                } else if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                    Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.note_handwrite_not_support_in_phone_model, 0).show();
                    return;
                }
            }
            widgetNoteListFragment.unbindWidget();
            ContentValues contentValues = new ContentValues();
            contentValues.put("widget_id", Integer.valueOf(widgetNoteListFragment.widgetId));
            contentValues.put("widget_type", Integer.valueOf(widgetNoteListFragment.widgetType));
            BaseNoteApp.INSTANCE.getInstance().getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, "_id=?", new String[]{String.valueOf(note.getId())});
            widgetNoteListFragment.onWidgetChanged(widgetNoteListFragment.widgetType, widgetNoteListFragment.widgetId);
            widgetNoteListFragment.finishPage();
        }
    }

    private final void unbindWidget() {
        NoteEntity noteEntityLoad = NoteEntityHelper.load(BaseNoteApp.INSTANCE.getInstance(), "widget_id=? AND parent_id!=-3", new String[]{String.valueOf(this.widgetId)});
        if (noteEntityLoad != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("widget_id", (Integer) 0);
            contentValues.put("widget_type", (Integer) (-1));
            BaseNoteApp.INSTANCE.getInstance().getContentResolver().update(Notes.Note.CONTENT_URI, contentValues, "_id=?", new String[]{String.valueOf(noteEntityLoad.getId())});
        }
    }

    private final void onWidgetChanged(int widgetType, int widgetId) {
        Logger.INSTANCE.d(this.TAG, "onWidgetChanged widgetType:" + widgetType + " widgetId " + widgetId);
        UIUtils.updateWidget(BaseNoteApp.INSTANCE.getInstance(), widgetId, widgetType);
    }

    private final void startQueryNote() {
        String str;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        if (UIUtils.isMiuiWidgetSupported(contextRequireContext)) {
            str = " AND note_content_type is not 'handwrite'";
        } else {
            str = " AND note_content_type is not 'handwrite' AND note_content_type is not 'mind'";
        }
        NoteStore.widgetSelectQuery(BaseNoteApp.INSTANCE.getInstance(), getLoaderManager(), -4097L, this.mBindContext, 0, this.mLoaderCallbacks, str);
    }

    /* JADX INFO: compiled from: WidgetNoteListFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\n"}, d2 = {"Lcom/miui/notes/widget/notelist/WidgetNoteListFragment$NoteLoaderCallbacks;", "Lcom/miui/notes/tool/util/CursorLoaderBuilder$DefaultLoaderCallbacks;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/widget/notelist/WidgetNoteListFragment;)V", "onLoadFinished", "", "loader", "Landroidx/loader/content/Loader;", "Landroid/database/Cursor;", "data", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class NoteLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        public NoteLoaderCallbacks() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Intrinsics.checkNotNullParameter(loader, "loader");
            if (loader.getId() == 0 && data != null) {
                WidgetNoteListFragment.this.onNotesLoadFinished(data);
                WidgetNoteListFragment.this.cachePhoneNumberResource();
            }
            BitmapCacheManager.resize(BaseNoteApp.INSTANCE.getInstance());
        }
    }

    private final CursorLoaderBuilder.DefaultLoaderCallbacks getLoaderCallbacks() {
        return new NoteLoaderCallbacks();
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0057  */
    /* JADX WARN: Code duplicated, block: B:19:0x005b  */
    protected final void onNotesLoadFinished(Cursor data) {
        SearchCallback searchCallback;
        MHandler mHandler;
        Intrinsics.checkNotNullParameter(data, "data");
        WidgetNoteListAdapter widgetNoteListAdapter = this.mAdapter;
        Intrinsics.checkNotNull(widgetNoteListAdapter);
        widgetNoteListAdapter.setSelectId(this.widgetId);
        WidgetNoteListAdapter widgetNoteListAdapter2 = this.mAdapter;
        Intrinsics.checkNotNull(widgetNoteListAdapter2);
        widgetNoteListAdapter2.setCursor(data);
        onDataEmpty(data.getCount() == 0);
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView);
        if (baseNoteRecyclerView.getItemAnimator() == null && (mHandler = this.mHandler) != null) {
            mHandler.postDelayed(this.mSetItemAnimatorRunnable, 100L);
        }
        if (!TextUtils.isEmpty(this.mSearchWords)) {
            SearchCallback searchCallback2 = this.mSearchModeListener;
            if (searchCallback2 != null) {
                String str = this.mSearchWords;
                Intrinsics.checkNotNull(searchCallback2);
                if (!Intrinsics.areEqual(str, searchCallback2.getSearchText())) {
                    searchCallback = this.mSearchModeListener;
                    if (searchCallback != null) {
                        Intrinsics.checkNotNull(searchCallback);
                        searchCallback.removeTextChangedListener();
                    }
                    setSearchText(this.mSearchWords);
                }
            } else {
                searchCallback = this.mSearchModeListener;
                if (searchCallback != null) {
                    Intrinsics.checkNotNull(searchCallback);
                    searchCallback.removeTextChangedListener();
                }
                setSearchText(this.mSearchWords);
            }
        }
        this.mSearchWords = null;
    }

    private final void setSearchText(String text) {
        View view;
        String str = text;
        if (str == null || str.length() == 0) {
            return;
        }
        if (this.mSearchModeListener == null && (view = this.mSearchBar) != null) {
            onSearchRequest(view, true, text);
            return;
        }
        if (!this.mInSearchMode) {
            BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(baseNoteRecyclerView);
            baseNoteRecyclerView.startActionMode(this.mSearchModeListener);
        }
        realSetSearchText(text);
    }

    private final void realSetSearchText(String text) {
        BindContext bindContext = this.mBindContext;
        Intrinsics.checkNotNull(bindContext);
        if (bindContext.getSearchType() == 2) {
            SearchCallback searchCallback = this.mSearchModeListener;
            Intrinsics.checkNotNull(searchCallback);
            searchCallback.setSearchText(text, false, true);
        } else {
            SearchCallback searchCallback2 = this.mSearchModeListener;
            Intrinsics.checkNotNull(searchCallback2);
            searchCallback2.setSearchText(text, false, false);
        }
    }

    private final void onDataEmpty(boolean isEmpty) {
        if (this.mListBlankView == null) {
            this.mListBlankView = new EmptyStateView(this.mActivity);
            EmptyStateView emptyStateView = this.mListBlankView;
            if (emptyStateView != null) {
                emptyStateView.setIcon(R.drawable.note_blank_icon);
            }
            EmptyStateView emptyStateView2 = this.mListBlankView;
            if (emptyStateView2 != null) {
                emptyStateView2.setTitle(getString(R.string.data_empty_note));
            }
        }
        this.mListBlankView.setVisibility(isEmpty ? 0 : 8);
        if (isEmpty) {
            BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(baseNoteRecyclerView);
            if (baseNoteRecyclerView.getVisibility() != 8) {
                WidgetNoteListAdapter widgetNoteListAdapter = this.mAdapter;
                Intrinsics.checkNotNull(widgetNoteListAdapter);
                widgetNoteListAdapter.notifyDataSetChanged();
                MHandler mHandler = this.mHandler;
                if (mHandler != null) {
                    mHandler.postDelayed(this.mDataEmptyRunnable, 300L);
                    return;
                }
                return;
            }
        }
        BaseNoteRecyclerView baseNoteRecyclerView2 = this.mRecyclerView;
        Intrinsics.checkNotNull(baseNoteRecyclerView2);
        if (baseNoteRecyclerView2.getVisibility() != 0) {
            BaseNoteRecyclerView baseNoteRecyclerView3 = this.mRecyclerView;
            Intrinsics.checkNotNull(baseNoteRecyclerView3);
            baseNoteRecyclerView3.setVisibility(0);
            View view = this.mBlankViewContainer;
            Intrinsics.checkNotNull(view);
            view.setVisibility(8);
            SpringBackLayout springBackLayout = this.mSpringBackLayout;
            Intrinsics.checkNotNull(springBackLayout);
            BaseNoteRecyclerView baseNoteRecyclerView4 = this.mRecyclerView;
            Intrinsics.checkNotNull(baseNoteRecyclerView4);
            springBackLayout.setTarget(baseNoteRecyclerView4);
        }
    }

    protected final void cachePhoneNumberResource() {
        if (PhoneNumberCacheHelper.hasPhoneNumberResourceCached()) {
            return;
        }
        MHandler mHandler = this.mHandler;
        Intrinsics.checkNotNull(mHandler);
        mHandler.post(this.mHandlePhoneNumberRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSearchRequest(View view, boolean isUpdateText, String text) {
        if (this.mSearchModeListener == null) {
            this.mSearchModeListener = new SearchCallback(BaseNoteApp.INSTANCE.getInstance(), this, this, this.mActivity);
        }
        SearchCallback searchCallback = this.mSearchModeListener;
        Intrinsics.checkNotNull(searchCallback);
        NestedHeaderLayout nestedHeaderLayout = this.nestedHeaderLayout;
        Intrinsics.checkNotNull(nestedHeaderLayout);
        searchCallback.setup(view, nestedHeaderLayout, nestedHeaderLayout.getScrollableView());
        this.mIsUpdateText = isUpdateText;
        this.mText = text;
        MHandler mHandler = this.mHandler;
        if (mHandler != null) {
            mHandler.postDelayed(this.mItemAnimatorRunnable, 100L);
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String newText) {
        Intrinsics.checkNotNullParameter(newText, "newText");
        this.mSearchWords = newText;
        WidgetNoteListAdapter widgetNoteListAdapter = this.mAdapter;
        Intrinsics.checkNotNull(widgetNoteListAdapter);
        widgetNoteListAdapter.markManualModifyDataTime();
        BindContext bindContext = this.mBindContext;
        Intrinsics.checkNotNull(bindContext);
        SearchCallback searchCallback = this.mSearchModeListener;
        Intrinsics.checkNotNull(searchCallback);
        bindContext.setSearchToken(searchCallback.getSearchText());
        startQueryNote();
        return true;
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean animateToVisible) {
        if (isAdded() && animateToVisible) {
            NestedHeaderLayout nestedHeaderLayout = this.nestedHeaderLayout;
            Intrinsics.checkNotNull(nestedHeaderLayout);
            nestedHeaderLayout.setInSearchMode(true);
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean animateToVisible) {
        if (isAdded()) {
            if (animateToVisible) {
                ActionStateListener actionStateListener = this.mActionStateListener;
                if (actionStateListener != null) {
                    actionStateListener.onEnterSearch();
                    return;
                }
                return;
            }
            NestedHeaderLayout nestedHeaderLayout = this.nestedHeaderLayout;
            Intrinsics.checkNotNull(nestedHeaderLayout);
            nestedHeaderLayout.setInSearchMode(false);
            ActionStateListener actionStateListener2 = this.mActionStateListener;
            if (actionStateListener2 != null) {
                actionStateListener2.onExitSearch();
            }
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode mode, Menu menu) {
        Activity activity;
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(menu, "menu");
        if (isAdded() && UIUtils.isInFullWindowGestureMode(getContext()) && (activity = this.mActivity) != null) {
            Intrinsics.checkNotNull(activity);
            activity.getWindow().clearFlags(DataSpace.RANGE_FULL);
        }
        this.mInSearchMode = true;
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode mode) {
        Activity activity;
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (isAdded() && UIUtils.isInFullWindowGestureMode(getContext()) && (activity = this.mActivity) != null) {
            Intrinsics.checkNotNull(activity);
            activity.getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        this.mInSearchMode = false;
        startQueryNote();
        finishIfNeeded();
    }

    @Override // com.miui.notes.ui.view.SearchCallback.TextSearch
    public void onTextSearch(String newText, int searchType) {
        Intrinsics.checkNotNullParameter(newText, "newText");
        this.mInSearchMode = true;
        this.mSearchWords = newText;
        if (this.mBindContext != null) {
            WidgetNoteListAdapter widgetNoteListAdapter = this.mAdapter;
            Intrinsics.checkNotNull(widgetNoteListAdapter);
            widgetNoteListAdapter.markManualModifyDataTime();
            BindContext bindContext = this.mBindContext;
            Intrinsics.checkNotNull(bindContext);
            bindContext.setSearchToken(newText);
            BindContext bindContext2 = this.mBindContext;
            Intrinsics.checkNotNull(bindContext2);
            bindContext2.setSearchType(searchType);
            startQueryNote();
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.TextSearch
    public void finishIfNeeded() {
        BindContext bindContext = this.mBindContext;
        Intrinsics.checkNotNull(bindContext);
        if (bindContext.getSearchType() != 1) {
            BindContext bindContext2 = this.mBindContext;
            Intrinsics.checkNotNull(bindContext2);
            if (bindContext2.getSearchType() != 2) {
                return;
            }
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            Intrinsics.checkNotNull(activity);
            activity.finish();
        }
    }
}
