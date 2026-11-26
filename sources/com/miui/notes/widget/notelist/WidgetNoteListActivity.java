package com.miui.notes.widget.notelist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.WidgetStat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.DialogManagedActivity;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WidgetNoteListActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0018H\u0014J\b\u0010\u001c\u001a\u00020\u0013H\u0002J\b\u0010\u001d\u001a\u00020\u0013H\u0002J\b\u0010\u001e\u001a\u00020\u0013H\u0002J\u0010\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$H\u0014J\b\u0010%\u001a\u00020\u0013H\u0014J\b\u0010&\u001a\u00020\u0013H\u0002J\b\u0010'\u001a\u00020\u0013H\u0016J\"\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u000f2\b\u0010+\u001a\u0004\u0018\u00010$H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/miui/notes/widget/notelist/WidgetNoteListActivity;", "Lcom/miui/notes/ui/activity/DialogManagedActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "normalFragment", "Lcom/miui/notes/widget/notelist/WidgetNoteListFragment;", "floatFragment", "Lcom/miui/notes/widget/notelist/FoldWidgetNoteListFragment;", "rootView", "Landroid/view/View;", "fragmentContainer", "curFragment", "largeScreenMode", "", "miuiWidgetScreenSide", "", "holder0", "holder1", "transparentNavBar", "", "window", "Landroid/view/Window;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setPageOrientation", "onSaveInstanceState", "outState", "showContent", "showNormalFragment", "showFloatFragment", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onNewIntent", "intent", "Landroid/content/Intent;", "onResume", "checkOrientation", "onBackPressed", "onActivityResult", "requestCode", "resultCode", "data", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class WidgetNoteListActivity extends DialogManagedActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "WidgetNoteListActivity";
    private static final String TAG_LIST_FLOAT = "WIDGET_NOTE_LIST_FLOAT";
    private static final String TAG_LIST_NORMAL = "WIDGET_NOTE_LIST_NORMAL";
    private WidgetNoteListFragment curFragment;
    private FoldWidgetNoteListFragment floatFragment;
    private View fragmentContainer;
    private View holder0;
    private View holder1;
    private boolean largeScreenMode;
    private int miuiWidgetScreenSide = -1;
    private WidgetNoteListFragment normalFragment;
    private View rootView;

    private final void transparentNavBar(Window window) {
        window.setNavigationBarContrastEnforced(false);
        window.setNavigationBarColor(0);
        View decorView = window.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | R2.attr.miuix_behavior_halfExpandedRatio);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        setPageOrientation();
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
        transparentNavBar(window);
        setContentView(R.layout.activity_widget_note_list);
        this.rootView = findViewById(R.id.activity_widget_note_list_root_view);
        View viewFindViewById = findViewById(R.id.widget_edit_list_container);
        this.fragmentContainer = viewFindViewById;
        Intrinsics.checkNotNull(viewFindViewById);
        viewFindViewById.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.widget.notelist.WidgetNoteListActivity.onCreate.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), WidgetNoteListActivity.this.getResources().getDimensionPixelOffset(R.dimen.widget_select_list_float_radius));
            }
        });
        View view = this.fragmentContainer;
        Intrinsics.checkNotNull(view);
        view.setClipToOutline(true);
        this.holder0 = findViewById(R.id.place_holder0);
        this.holder1 = findViewById(R.id.place_holder1);
        View view2 = this.holder0;
        Intrinsics.checkNotNull(view2);
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.finishAndRemoveTask();
            }
        });
        View view3 = this.holder1;
        Intrinsics.checkNotNull(view3);
        view3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.widget.notelist.WidgetNoteListActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                this.f$0.finishAndRemoveTask();
            }
        });
        if (RomUtils.isFoldDevice()) {
            if (savedInstanceState != null) {
                this.largeScreenMode = savedInstanceState.getBoolean("isLargeScreenMode", false);
                this.miuiWidgetScreenSide = savedInstanceState.getInt("miuiWidgetScreenSide", -1);
            } else {
                Intent intent = getIntent();
                if (intent != null) {
                    this.largeScreenMode = intent.getBooleanExtra("isLargeScreenMode", false);
                    this.miuiWidgetScreenSide = intent.getIntExtra("miuiWidgetScreenSide", -1);
                }
            }
            showContent();
        } else {
            showNormalFragment();
        }
        WidgetStat.handleWidgetEditEnter(getIntent());
    }

    private final void setPageOrientation() {
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(BaseNoteApp.INSTANCE.getInstance());
        setRequestedOrientation(screenSizeDp[0] > screenSizeDp[1] ? 11 : 1);
    }

    @Override // com.miui.notes.cta.CTAActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putBoolean("isLargeScreenMode", this.largeScreenMode);
        outState.putInt("miuiWidgetScreenSide", this.miuiWidgetScreenSide);
    }

    private final void showContent() {
        if (this.largeScreenMode) {
            showFloatFragment();
        } else {
            showNormalFragment();
        }
    }

    private final void showNormalFragment() {
        View view = this.holder0;
        Intrinsics.checkNotNull(view);
        view.setVisibility(8);
        View view2 = this.holder1;
        Intrinsics.checkNotNull(view2);
        view2.setVisibility(8);
        View view3 = this.fragmentContainer;
        Intrinsics.checkNotNull(view3);
        view3.setClipToOutline(false);
        View view4 = this.rootView;
        Intrinsics.checkNotNull(view4);
        view4.setPadding(0, 0, 0, UIUtils.getNavigationBarHeight(BaseNoteApp.INSTANCE.getInstance()));
        View view5 = this.rootView;
        Intrinsics.checkNotNull(view5);
        view5.setBackgroundResource(R.color.homepage_primary_color);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        WidgetNoteListFragment widgetNoteListFragment = (WidgetNoteListFragment) supportFragmentManager.findFragmentByTag(TAG_LIST_NORMAL);
        this.normalFragment = widgetNoteListFragment;
        if (widgetNoteListFragment == null) {
            this.normalFragment = new WidgetNoteListFragment();
        }
        this.curFragment = this.normalFragment;
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        WidgetNoteListFragment widgetNoteListFragment2 = this.normalFragment;
        Intrinsics.checkNotNull(widgetNoteListFragment2);
        fragmentTransactionBeginTransaction.replace(R.id.widget_edit_list_container, widgetNoteListFragment2, TAG_LIST_NORMAL);
        fragmentTransactionBeginTransaction.commit();
    }

    private final void showFloatFragment() {
        int i = this.miuiWidgetScreenSide;
        if (i == 0) {
            View view = this.holder0;
            Intrinsics.checkNotNull(view);
            view.setVisibility(0);
        } else if (i == 1) {
            View view2 = this.holder1;
            Intrinsics.checkNotNull(view2);
            view2.setVisibility(0);
        }
        View view3 = this.fragmentContainer;
        Intrinsics.checkNotNull(view3);
        view3.setClipToOutline(true);
        View view4 = this.rootView;
        Intrinsics.checkNotNull(view4);
        view4.setPadding(getResources().getDimensionPixelOffset(R.dimen.widget_select_list_float_margin_left), UIUtils.getStatusBarHeight(BaseNoteApp.INSTANCE.getInstance()), getResources().getDimensionPixelOffset(R.dimen.widget_select_list_float_margin_left), UIUtils.getNavigationBarHeight(BaseNoteApp.INSTANCE.getInstance()));
        View view5 = this.rootView;
        Intrinsics.checkNotNull(view5);
        view5.setBackgroundResource(android.R.color.transparent);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        FoldWidgetNoteListFragment foldWidgetNoteListFragment = (FoldWidgetNoteListFragment) supportFragmentManager.findFragmentByTag(TAG_LIST_FLOAT);
        this.floatFragment = foldWidgetNoteListFragment;
        if (foldWidgetNoteListFragment == null) {
            this.floatFragment = new FoldWidgetNoteListFragment();
        }
        this.curFragment = this.floatFragment;
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        FoldWidgetNoteListFragment foldWidgetNoteListFragment2 = this.floatFragment;
        Intrinsics.checkNotNull(foldWidgetNoteListFragment2);
        fragmentTransactionBeginTransaction.replace(R.id.widget_edit_list_container, foldWidgetNoteListFragment2, TAG_LIST_FLOAT);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        if (RomUtils.isFoldDevice()) {
            this.largeScreenMode = UIUtils.isFoldInternalScreen(BaseNoteApp.INSTANCE.getInstance());
            showContent();
        }
        checkOrientation();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        WidgetNoteListFragment widgetNoteListFragment = this.curFragment;
        if (widgetNoteListFragment != null) {
            Intrinsics.checkNotNull(widgetNoteListFragment);
            widgetNoteListFragment.onNewIntent(intent);
        }
        WidgetStat.handleWidgetEditEnter(getIntent());
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (!PreferenceUtils.isCTAAccepted(getApplicationContext()) && PreferenceUtils.isFirstHandle(getApplicationContext())) {
            Intent intent = new Intent();
            intent.setClass(this, NotesListActivity.class);
            startActivity(intent);
        }
        checkOrientation();
    }

    private final void checkOrientation() {
        if (RomUtils.isFoldDevice()) {
            if (!UIUtils.isFoldInternalScreen(BaseNoteApp.INSTANCE.getInstance())) {
                setRequestedOrientation(1);
            } else {
                setPageOrientation();
            }
        }
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
                PreferenceUtils.setAllowNetwork(BaseNoteApp.INSTANCE.getInstance(), true);
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

    /* JADX INFO: compiled from: WidgetNoteListActivity.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/notes/widget/notelist/WidgetNoteListActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "TAG_LIST_NORMAL", "TAG_LIST_FLOAT", "open", "", "activity", "Landroid/app/Activity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void open(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            activity.startActivity(new Intent(activity, (Class<?>) WidgetNoteListActivity.class));
        }
    }
}
