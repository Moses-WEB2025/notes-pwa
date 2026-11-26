package com.miui.notes.page;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.base.BaseFragment;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.ui.widget.FloatingActionButton;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigator.NavigatorFragmentListener;

/* JADX INFO: compiled from: BaseListPageFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\rJ\b\u0010#\u001a\u00020\rH&J\b\u0010$\u001a\u00020\u0000H\u0016J\n\u0010%\u001a\u0004\u0018\u00010&H\u0016J\u0010\u0010'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u000fH\u0016J\b\u0010)\u001a\u00020\rH\u0016J\b\u0010*\u001a\u00020\rH\u0016J\u0010\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\rH\u0016J\b\u0010-\u001a\u00020\u001fH\u0016J\u0012\u0010.\u001a\u00020\u001f2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\u001a\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u000100H\u0016J\u0010\u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u000207H\u0016J\b\u00108\u001a\u00020\u001fH\u0014J\b\u00109\u001a\u00020\u001fH\u0016J\b\u0010:\u001a\u00020\u001fH\u0016J\b\u0010;\u001a\u00020\u001fH\u0016R\u001a\u0010\u0006\u001a\u00020\u0007X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00000\u001c8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\r8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lcom/miui/notes/page/BaseListPageFragment;", "Lcom/miui/common/base/BaseFragment;", "Lcom/miui/common/base/DialogManagerInterface;", "Lmiuix/navigator/NavigatorFragmentListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "setTAG", "(Ljava/lang/String;)V", "lastFoldStatus", "", "mManagedDialog", "Landroid/app/Dialog;", "mEditText", "Landroid/widget/EditText;", "mBtnContentAdd", "Lcom/miui/notes/ui/widget/FloatingActionButton;", "mBtnHandWriteAdd", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "getHomeViewModel", "()Lcom/miui/notes/home/viewmodel/HomeViewModel;", "setHomeViewModel", "(Lcom/miui/notes/home/viewmodel/HomeViewModel;)V", "handler", "Lcom/miui/notes/base/app/MainHandler;", "mIsSelected", "onSelected", "", "selected", "backPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "onBackPressed", "getThisFragment", "getThisActivity", "Landroid/app/Activity;", "manageDialog", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "dismissDialog", "isDialogShowing", "onVisibilityChanged", "visible", "onDetach", "onCreate", "bundle", "Landroid/os/Bundle;", "onViewInflated", "view", "Landroid/view/View;", "savedInstanceState", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onFoldChangeScreen", "onPause", "onDestroy", "onResume", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseListPageFragment extends BaseFragment implements DialogManagerInterface, NavigatorFragmentListener {
    private final OnBackPressedCallback backPressedCallback;
    protected final MainHandler<BaseListPageFragment> handler;
    public HomeViewModel homeViewModel;
    public FloatingActionButton mBtnContentAdd;
    public FloatingActionButton mBtnHandWriteAdd;
    private EditText mEditText;
    protected boolean mIsSelected;
    private Dialog mManagedDialog;
    private String TAG = "BaseListPageFragment";
    private boolean lastFoldStatus = true;

    @Override // com.miui.common.base.DialogManagerInterface
    public BaseListPageFragment getThisFragment() {
        return this;
    }

    public abstract boolean onBackPressed();

    protected void onFoldChangeScreen() {
    }

    public BaseListPageFragment() {
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        this.handler = new MainHandler<>(mainLooper, new WeakReference(this));
        this.backPressedCallback = new OnBackPressedCallback() { // from class: com.miui.notes.page.BaseListPageFragment$backPressedCallback$1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                OnBackPressedDispatcher onBackPressedDispatcher;
                if (this.this$0.onBackPressed()) {
                    Logger.INSTANCE.d(this.this$0.getTAG(), "handleBackPressed,consumed");
                    return;
                }
                Logger.INSTANCE.d(this.this$0.getTAG(), "handleBackPressed,not consumed");
                setEnabled(false);
                FragmentActivity activity = this.this$0.getActivity();
                if (activity == null || (onBackPressedDispatcher = activity.getOnBackPressedDispatcher()) == null) {
                    return;
                }
                onBackPressedDispatcher.onBackPressed();
            }
        };
    }

    protected String getTAG() {
        return this.TAG;
    }

    protected void setTAG(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.TAG = str;
    }

    public final HomeViewModel getHomeViewModel() {
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            return homeViewModel;
        }
        Intrinsics.throwUninitializedPropertyAccessException("homeViewModel");
        return null;
    }

    public final void setHomeViewModel(HomeViewModel homeViewModel) {
        Intrinsics.checkNotNullParameter(homeViewModel, "<set-?>");
        this.homeViewModel = homeViewModel;
    }

    public final void onSelected(boolean selected) {
        this.mIsSelected = selected;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return getActivity();
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        this.mManagedDialog = dialog;
        this.mEditText = (EditText) dialog.findViewById(R.id.et_folder_name);
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        if (!isDialogShowing()) {
            return false;
        }
        Dialog dialog = this.mManagedDialog;
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
        Dialog dialog2 = this.mManagedDialog;
        Intrinsics.checkNotNull(dialog2);
        dialog2.dismiss();
        this.mManagedDialog = null;
        return true;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        Dialog dialog = this.mManagedDialog;
        if (dialog == null) {
            return false;
        }
        Intrinsics.checkNotNull(dialog);
        return dialog.isShowing();
    }

    @Override // miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean visible) {
        Dialog dialog;
        super.onVisibilityChanged(visible);
        Logger.INSTANCE.d(getTAG(), "onVisibilityChanged:" + visible);
        if (visible || (dialog = this.mManagedDialog) == null) {
            return;
        }
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
        this.mManagedDialog = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Logger.INSTANCE.d(getTAG(), "onDetach");
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            dialog.dismiss();
            this.mManagedDialog = null;
        }
        super.onDetach();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        setHomeViewModel((HomeViewModel) new ViewModelProvider(fragmentActivityRequireActivity).get(HomeViewModel.class));
        Logger.INSTANCE.d(getTAG(), "onCreateFragment,init viewModel:" + getHomeViewModel());
        this.lastFoldStatus = RomUtils.isFoldDevice() && UIUtils.isFoldInternalScreen(getContext());
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Logger.INSTANCE.d(getTAG(), "onViewInflated");
        this.mBtnContentAdd = (FloatingActionButton) view.findViewById(R.id.content_add);
        this.mBtnHandWriteAdd = (FloatingActionButton) view.findViewById(R.id.handwrite_add);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(getTAG(), "onConfigurationChanged");
        if (!RomUtils.isFoldDevice() || getContext() == null || this.lastFoldStatus == UIUtils.isFoldInternalScreen(getContext())) {
            return;
        }
        this.lastFoldStatus = UIUtils.isFoldInternalScreen(getContext());
        onFoldChangeScreen();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        EditText editText;
        super.onPause();
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (!dialog.isShowing() || (editText = this.mEditText) == null) {
                return;
            }
            Intrinsics.checkNotNull(editText);
            editText.clearFocus();
            Utils.hideSoftInput(this.mEditText);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        OnBackPressedDispatcher onBackPressedDispatcher;
        super.onResume();
        this.backPressedCallback.remove();
        this.backPressedCallback.setEnabled(true);
        FragmentActivity activity = getActivity();
        if (activity == null || (onBackPressedDispatcher = activity.getOnBackPressedDispatcher()) == null) {
            return;
        }
        onBackPressedDispatcher.addCallback(this, this.backPressedCallback);
    }
}
