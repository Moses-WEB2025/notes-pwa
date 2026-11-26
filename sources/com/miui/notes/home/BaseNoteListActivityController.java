package com.miui.notes.home;

import android.content.res.Configuration;
import androidx.lifecycle.ViewModelProvider;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.basefeature.IActivityController;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseNoteListActivityController.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/home/BaseNoteListActivityController;", "Lcom/miui/notes/basefeature/IActivityController;", "activity", "Lcom/miui/notes/ui/NotesListActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ui/NotesListActivity;)V", "getActivity", "()Lcom/miui/notes/ui/NotesListActivity;", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "getHomeViewModel", "()Lcom/miui/notes/home/viewmodel/HomeViewModel;", "onResume", "", "onDestroy", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onMultiWindowModeChanged", "isInMultiWindowMode", "", "handleBackPress", "onWindowFocusChanged", "hasFocus", "logDebug", "msg", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseNoteListActivityController implements IActivityController {
    private final NotesListActivity activity;
    private final HomeViewModel homeViewModel;

    public BaseNoteListActivityController(NotesListActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        HomeViewModel homeViewModel = (HomeViewModel) new ViewModelProvider(activity).get(HomeViewModel.class);
        this.homeViewModel = homeViewModel;
        logDebug("init id = " + homeViewModel.getFolderId() + " ,init viewModel:" + homeViewModel);
        homeViewModel.setFolderId(PreferenceUtils.getLongPreference(activity, PreferenceUtils.PREF_LAST_FOLDER_ID, NoteConfig.getDefaultFolderId()));
        Unit unit = Unit.INSTANCE;
        logDebug("set folder id by config");
    }

    public final NotesListActivity getActivity() {
        return this.activity;
    }

    public final HomeViewModel getHomeViewModel() {
        return this.homeViewModel;
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onResume() {
        logDebug("onResume");
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onDestroy() {
        logDebug("onDestroy");
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onConfigurationChanged(Configuration newConfig) {
        logDebug("onConfigurationChanged");
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        logDebug("onMultiWindowModeChanged");
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public boolean handleBackPress() {
        logDebug("handleBackPressed-false");
        return false;
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onWindowFocusChanged(boolean hasFocus) {
        logDebug("onWindowFocusChanged:" + hasFocus);
        this.homeViewModel.changeWindowsFocus(hasFocus);
    }

    protected final void logDebug(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, msg);
    }
}
