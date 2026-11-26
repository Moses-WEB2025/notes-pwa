package com.miui.notes.widget.notelist;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Outline;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.hardware.DataSpace;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.WidgetStat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.DialogManagedActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadWidgetNoteListActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0018H\u0014J\u0006\u0010\u001c\u001a\u00020\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0002J\b\u0010\u001e\u001a\u00020\u0016H\u0002J\u0010\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$H\u0014J\b\u0010%\u001a\u00020\u0016H\u0016J\"\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00112\b\u0010)\u001a\u0004\u0018\u00010$H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/miui/notes/widget/notelist/PadWidgetNoteListActivity;", "Lcom/miui/notes/ui/activity/DialogManagedActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNormalFragment", "Lcom/miui/notes/widget/notelist/WidgetNoteListFragment;", "mFloatFragment", "Lcom/miui/notes/widget/notelist/FoldWidgetNoteListFragment;", "mRootView", "Landroid/view/View;", "mFragmentContainer", "mCurFragment", "mLargeScreenMode", "", "mMiuiWidgetScreenBound", "Landroid/os/Parcelable;", "mFixedSpace", "", "mPageWidth", "mPageHeight", "mWidgetType", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "computeEditLocation", "onSaveInstanceState", "outState", "showContent", "showNormalFragment", "showFloatFragment", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onNewIntent", "intent", "Landroid/content/Intent;", "onBackPressed", "onActivityResult", "requestCode", "resultCode", "data", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadWidgetNoteListActivity extends DialogManagedActivity {
    private static final String TAG = "PadWidgetNoteListActivity";
    private static final String TAG_LIST_FLOAT = "WIDGET_NOTE_LIST_FLOAT";
    private static final String TAG_LIST_NORMAL = "WIDGET_NOTE_LIST_NORMAL";
    private WidgetNoteListFragment mCurFragment;
    private int mFixedSpace;
    private FoldWidgetNoteListFragment mFloatFragment;
    private View mFragmentContainer;
    private boolean mLargeScreenMode;
    private Parcelable mMiuiWidgetScreenBound;
    private WidgetNoteListFragment mNormalFragment;
    private int mPageHeight;
    private int mPageWidth;
    private View mRootView;
    private int mWidgetType = -1;

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(DataSpace.RANGE_FULL);
        if (savedInstanceState != null) {
            this.mLargeScreenMode = savedInstanceState.getBoolean("isLargeScreenMode", false);
            this.mMiuiWidgetScreenBound = savedInstanceState.getParcelable("miuiWidgetScreenBound");
            this.mWidgetType = savedInstanceState.getInt("widgetType");
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                this.mLargeScreenMode = intent.getBooleanExtra("isLargeScreenMode", false);
                this.mMiuiWidgetScreenBound = intent.getParcelableExtra("miuiWidgetScreenBound");
                Logger.INSTANCE.d(TAG, "largeScreenMode:" + this.mLargeScreenMode + " bound:" + this.mMiuiWidgetScreenBound);
                if (TextUtils.equals(intent.getAction(), NoteIntent.ACTION_BIND_WIDGET)) {
                    this.mWidgetType = intent.getIntExtra("com.miui.notes.widget_type", -1);
                } else {
                    Uri data = intent.getData();
                    if (data != null) {
                        String str = data.getPathSegments().get(3);
                        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
                        this.mWidgetType = Integer.parseInt(str);
                    }
                }
            }
        }
        if (this.mWidgetType != 4 && !LiteUtils.isLiteOrMiddle()) {
            setTheme(R.style.NoteTheme_WidgetList_Activity);
        } else {
            setTheme(R.style.NoteTheme_Pad_WidgetList_Activity);
        }
        PadWidgetNoteListActivity padWidgetNoteListActivity = this;
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(padWidgetNoteListActivity);
        setRequestedOrientation(screenSizeDp[0] > screenSizeDp[1] ? 6 : 7);
        Logger.INSTANCE.d(TAG, "height dp:" + screenSizeDp[1] + " width dp" + screenSizeDp[0]);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_activity_widget_note_list);
        View viewFindViewById = findViewById(R.id.activity_widget_note_list_root_view);
        this.mRootView = viewFindViewById;
        Intrinsics.checkNotNull(viewFindViewById);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.PadWidgetNoteListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finishAndRemoveTask();
            }
        });
        View viewFindViewById2 = findViewById(R.id.widget_edit_list_container);
        this.mFragmentContainer = viewFindViewById2;
        Intrinsics.checkNotNull(viewFindViewById2);
        viewFindViewById2.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.widget.notelist.PadWidgetNoteListActivity.onCreate.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), PadWidgetNoteListActivity.this.getResources().getDimensionPixelOffset(R.dimen.widget_select_list_float_radius));
            }
        });
        View view = this.mFragmentContainer;
        Intrinsics.checkNotNull(view);
        view.setClipToOutline(true);
        this.mFixedSpace = getResources().getDimensionPixelOffset(R.dimen.widget_note_list_fixed_space);
        this.mPageWidth = getResources().getDimensionPixelOffset(R.dimen.widget_note_list_page_width);
        this.mPageHeight = getResources().getDimensionPixelOffset(R.dimen.widget_note_list_page_height);
        if (this.mWidgetType != 4 && !LiteUtils.isLiteOrMiddle()) {
            showContent();
        } else {
            showNormalFragment();
        }
        WidgetStat.handleWidgetEditEnter(getIntent());
        if (PreferenceUtils.isCTAAccepted(getApplicationContext()) || !PreferenceUtils.isFirstHandle(getApplicationContext())) {
            return;
        }
        Intent intent2 = new Intent();
        intent2.setClass(padWidgetNoteListActivity, NotesListActivity.class);
        startActivity(intent2);
        finish();
    }

    private final void computeEditLocation() {
        Parcelable parcelable = this.mMiuiWidgetScreenBound;
        if (parcelable instanceof Rect) {
            Intrinsics.checkNotNull(parcelable, "null cannot be cast to non-null type android.graphics.Rect");
            Rect rect = (Rect) parcelable;
            Rect rect2 = new Rect();
            PadWidgetNoteListActivity padWidgetNoteListActivity = this;
            int realWidth = DisplayUtils.getRealWidth(padWidgetNoteListActivity);
            int realHeight = DisplayUtils.getRealHeight(padWidgetNoteListActivity);
            Logger.INSTANCE.d(TAG, " miui widget screen bound is " + this.mMiuiWidgetScreenBound);
            if (Utils.isRTL()) {
                if ((rect.left + rect.right) / 2 < realWidth / 2) {
                    rect2.left = realWidth - (rect.right + this.mFixedSpace);
                    rect2.right = rect2.left - this.mPageWidth;
                } else {
                    rect2.right = realWidth - (rect.left - this.mFixedSpace);
                    rect2.left = rect2.right + this.mPageWidth;
                }
            } else if ((rect.left + rect.right) / 2 > realWidth / 2) {
                rect2.right = rect.left - this.mFixedSpace;
                rect2.left = rect2.right - this.mPageWidth;
            } else {
                rect2.left = rect.right + this.mFixedSpace;
            }
            if (rect.top + this.mPageHeight <= realHeight) {
                rect2.top = rect.top;
            } else if (rect.bottom >= this.mPageHeight) {
                rect2.bottom = rect.bottom;
                rect2.top = rect2.bottom - this.mPageHeight;
            } else {
                rect2.top = UIUtils.getStatusBarHeight(padWidgetNoteListActivity);
            }
            View view = this.mFragmentContainer;
            Intrinsics.checkNotNull(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                layoutParams2.width = this.mPageWidth;
                layoutParams2.height = this.mPageHeight;
                layoutParams2.topMargin = rect2.top;
                layoutParams2.leftMargin = rect2.left;
                layoutParams2.rightMargin = rect2.right;
                layoutParams2.bottomMargin = rect2.bottom;
                Logger.INSTANCE.d(TAG, " leftMargin " + layoutParams2.leftMargin + " topMargin " + layoutParams2.topMargin + " rightMargin " + layoutParams2.rightMargin + " bottomMargin " + layoutParams2.bottomMargin);
            }
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putBoolean("isLargeScreenMode", this.mLargeScreenMode);
        outState.putParcelable("miuiWidgetScreenBound", this.mMiuiWidgetScreenBound);
        outState.putInt("widgetType", this.mWidgetType);
    }

    public final void showContent() {
        if (this.mLargeScreenMode) {
            showFloatFragment();
        } else {
            showNormalFragment();
        }
    }

    private final void showNormalFragment() {
        View view = this.mFragmentContainer;
        Intrinsics.checkNotNull(view);
        view.setClipToOutline(false);
        View view2 = this.mRootView;
        Intrinsics.checkNotNull(view2);
        view2.setPadding(0, 0, 0, UIUtils.getNavigationBarHeight(this));
        View view3 = this.mRootView;
        Intrinsics.checkNotNull(view3);
        view3.setBackgroundResource(R.color.homepage_primary_color);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        WidgetNoteListFragment widgetNoteListFragment = (WidgetNoteListFragment) supportFragmentManager.findFragmentByTag(TAG_LIST_NORMAL);
        this.mNormalFragment = widgetNoteListFragment;
        if (widgetNoteListFragment == null) {
            this.mNormalFragment = new WidgetNoteListFragment();
        }
        this.mCurFragment = this.mNormalFragment;
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        WidgetNoteListFragment widgetNoteListFragment2 = this.mNormalFragment;
        Intrinsics.checkNotNull(widgetNoteListFragment2);
        fragmentTransactionBeginTransaction.replace(R.id.widget_edit_list_container, widgetNoteListFragment2, TAG_LIST_NORMAL);
        fragmentTransactionBeginTransaction.commit();
    }

    private final void showFloatFragment() {
        computeEditLocation();
        View view = this.mFragmentContainer;
        Intrinsics.checkNotNull(view);
        view.setClipToOutline(true);
        View view2 = this.mRootView;
        Intrinsics.checkNotNull(view2);
        view2.setBackgroundResource(android.R.color.transparent);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        FoldWidgetNoteListFragment foldWidgetNoteListFragment = (FoldWidgetNoteListFragment) supportFragmentManager.findFragmentByTag(TAG_LIST_FLOAT);
        this.mFloatFragment = foldWidgetNoteListFragment;
        if (foldWidgetNoteListFragment == null) {
            this.mFloatFragment = new FoldWidgetNoteListFragment();
        }
        this.mCurFragment = this.mFloatFragment;
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        FoldWidgetNoteListFragment foldWidgetNoteListFragment2 = this.mFloatFragment;
        Intrinsics.checkNotNull(foldWidgetNoteListFragment2);
        fragmentTransactionBeginTransaction.replace(R.id.widget_edit_list_container, foldWidgetNoteListFragment2, TAG_LIST_FLOAT);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        int i = configuration.screenLayout & 15;
        Logger.INSTANCE.d(TAG, "screenSize:" + i);
        this.mLargeScreenMode = i == 3;
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        WidgetNoteListFragment widgetNoteListFragment = this.mCurFragment;
        if (widgetNoteListFragment != null) {
            Intrinsics.checkNotNull(widgetNoteListFragment);
            widgetNoteListFragment.onNewIntent(intent);
        }
        WidgetStat.handleWidgetEditEnter(getIntent());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == 1) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(this, true);
                CTAManager.getInstance().notifyAccept();
                Bundle bundle = new Bundle();
                bundle.putInt("push_type", 0);
                SyncUtils.requestSync(BaseNoteApp.INSTANCE.getInstance(), bundle, true);
            } else if (resultCode == 666) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                CTAManager.getInstance().notifyReject();
            }
            PermissionUtils.processCtaResult(this, resultCode);
        }
    }
}
