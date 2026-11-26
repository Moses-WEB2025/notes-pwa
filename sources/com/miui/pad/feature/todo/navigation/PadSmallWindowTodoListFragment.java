package com.miui.pad.feature.todo.navigation;

import android.os.Bundle;
import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.page.TodoListPageFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorStrategy;
import miuix.os.DeviceHelper;

/* JADX INFO: compiled from: PadSmallWindowTodoListFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u000e\u001a\u00020\u000bH\u0016J\u001a\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/miui/pad/feature/todo/navigation/PadSmallWindowTodoListFragment;", "Lcom/miui/notes/page/TodoListPageFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "setTAG", "(Ljava/lang/String;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onViewInflated", "view", "Landroid/view/View;", "onDetach", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PadSmallWindowTodoListFragment extends TodoListPageFragment {
    private String TAG = "PadSmallWindowTodoListFragment";

    @Override // com.miui.notes.page.BaseListPageFragment
    protected String getTAG() {
        return this.TAG;
    }

    @Override // com.miui.notes.page.BaseListPageFragment
    protected void setTAG(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.TAG = str;
    }

    @Override // com.miui.notes.page.TodoListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new NavigatorStrategy().getCurrentMode(getResponsiveState(), DeviceHelper.detectType(getContext())) == Navigator.Mode.C && getHomeViewModel().getPageSelected() == 1) {
            Navigator.get(this).requestFocus();
        }
        Logger.INSTANCE.d(getTAG(), "PadSmallWindowTodoListFragment onCreate");
        if (this.mPresenter != null) {
            this.mPresenter.setShowListType(2, false);
        }
    }

    @Override // com.miui.notes.page.TodoListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(getTAG(), "PadSmallWindowTodoListFragment onResume");
    }

    @Override // com.miui.notes.page.TodoListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Logger.INSTANCE.d(getTAG(), "PadSmallWindowTodoListFragment onViewInflated");
        setExtraHorizontalPaddingEnable(false);
        if (RomUtils.isFoldDevice() && UIUtils.isFoldInternalScreen(requireContext()) && this.mPresenter != null) {
            this.mPresenter.subscribe();
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        finishActionMode();
    }
}
