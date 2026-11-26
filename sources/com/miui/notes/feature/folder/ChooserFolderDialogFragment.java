package com.miui.notes.feature.folder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.ItemStore;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.fragment.ModuleFragment;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.ActionBar;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: ChooserFolderDialogFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 <2\u00020\u0001:\u0001<B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0010J\u0012\u0010\u001c\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010#\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010$\u001a\u00020\u001aH\u0004J\"\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010+\u001a\u00020\u001aH\u0002J\u0012\u0010,\u001a\u00020\u001a2\b\u0010-\u001a\u0004\u0018\u00010.H\u0002J \u0010/\u001a\u00020\u001a2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0007H\u0016J\b\u00105\u001a\u00020\u001aH\u0016J\u0010\u00106\u001a\u00020\u001a2\u0006\u00100\u001a\u000201H\u0016J\b\u00107\u001a\u00020\u001aH\u0016J\b\u00108\u001a\u00020\u001aH\u0016J\b\u00109\u001a\u00020\u0007H\u0016J\n\u0010:\u001a\u0004\u0018\u00010;H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001f\u001a\u00020 8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"¨\u0006="}, d2 = {"Lcom/miui/notes/feature/folder/ChooserFolderDialogFragment;", "Lcom/miui/notes/ui/fragment/ModuleFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mActivity", "Landroid/app/Activity;", "isDestroyView", "", "()Z", "setDestroyView", "(Z)V", "mActionBar", "Lmiuix/appcompat/app/ActionBar;", "mFolderId", "", "mController", "Lcom/miui/notes/feature/folder/ChooserFolderDialogController;", "itemIds", "", "mViewNoteFolderChooser", "Lcom/miui/notes/feature/folder/ViewFolderChooser;", "getMViewNoteFolderChooser", "()Lcom/miui/notes/feature/folder/ViewFolderChooser;", "setMViewNoteFolderChooser", "(Lcom/miui/notes/feature/folder/ViewFolderChooser;)V", "setController", "", "controller", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "themeRes", "", "getThemeRes", "()I", "onActivityCreated", "initActionBar", "onInflateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "queryFolderToMoveNote", "dealData", "cursor", "Landroid/database/Cursor;", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "onResume", "onConfigurationChanged", "onDestroyView", "onDestroy", "onBackPressed", "getSearchContent", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class ChooserFolderDialogFragment extends ModuleFragment {
    private static final int REQUEST_CODE_QUERY_FOLDER = 1;
    private boolean isDestroyView;
    private long[] itemIds;
    private ActionBar mActionBar;
    private Activity mActivity;
    private ChooserFolderDialogController mController;
    private long mFolderId;
    private ViewFolderChooser mViewNoteFolderChooser;

    private final int getThemeRes() {
        return R.style.NoteTheme_CommonFolderDialogFragment;
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public String getSearchContent() {
        return null;
    }

    /* JADX INFO: renamed from: isDestroyView, reason: from getter */
    protected final boolean getIsDestroyView() {
        return this.isDestroyView;
    }

    protected final void setDestroyView(boolean z) {
        this.isDestroyView = z;
    }

    protected final ViewFolderChooser getMViewNoteFolderChooser() {
        return this.mViewNoteFolderChooser;
    }

    protected final void setMViewNoteFolderChooser(ViewFolderChooser viewFolderChooser) {
        this.mViewNoteFolderChooser = viewFolderChooser;
    }

    public final void setController(ChooserFolderDialogController controller) {
        this.mController = controller;
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mFolderId = arguments.getLong(ChooserFolderDialogActivity.CHOOSER_FOLDER_ID);
            this.itemIds = arguments.getLongArray(ChooserFolderDialogActivity.CHOOSER_ITEM_IDS);
            Logger logger = Logger.INSTANCE;
            String simpleName = getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
            logger.d(simpleName, "itemIds:" + this.itemIds + "->" + this.mFolderId);
        }
        this.mActivity = getActivity();
        setThemeRes(getThemeRes());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
    }

    protected final void initActionBar() {
        ActionBar actionBar = getActionBar();
        this.mActionBar = actionBar;
        if (actionBar != null) {
            Intrinsics.checkNotNull(actionBar);
            actionBar.setDisplayShowCustomEnabled(true);
            ActionBar actionBar2 = this.mActionBar;
            Intrinsics.checkNotNull(actionBar2);
            actionBar2.setDisplayHomeAsUpEnabled(false);
            ActionBar actionBar3 = this.mActionBar;
            Intrinsics.checkNotNull(actionBar3);
            actionBar3.setExpandState(0);
            ActionBar actionBar4 = this.mActionBar;
            Intrinsics.checkNotNull(actionBar4);
            actionBar4.setResizable(false);
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(R.drawable.miui_action_bar_back);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.folder.ChooserFolderDialogFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooserFolderDialogFragment.initActionBar$lambda$0(this.f$0, view);
                }
            });
            imageView.setContentDescription(getResources().getString(R.string.actionbar_home_description));
            ActionBar actionBar5 = this.mActionBar;
            Intrinsics.checkNotNull(actionBar5);
            actionBar5.setStartView(imageView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initActionBar$lambda$0(ChooserFolderDialogFragment chooserFolderDialogFragment, View view) {
        ViewFolderChooser viewFolderChooser = chooserFolderDialogFragment.mViewNoteFolderChooser;
        if (viewFolderChooser != null) {
            Intrinsics.checkNotNull(viewFolderChooser);
            viewFolderChooser.stopScroll();
        }
        Activity activity = chooserFolderDialogFragment.mActivity;
        if (activity != null) {
            Intrinsics.checkNotNull(activity);
            activity.finish();
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Intrinsics.checkNotNullParameter(container, "container");
        this.isDestroyView = false;
        View viewInflate = inflater.inflate(R.layout.chooser_folder_dialog_fragment, container, false);
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onInflateView,queryFolderToMoveNote");
        queryFolderToMoveNote();
        Intrinsics.checkNotNull(viewInflate);
        return viewInflate;
    }

    private final void queryFolderToMoveNote() {
        if (getActivity() != null) {
            FolderStore folderStore = FolderStore.INSTANCE;
            LoaderManager loaderManager = getLoaderManager();
            Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
            folderStore.query(loaderManager, 4, 1, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.feature.folder.ChooserFolderDialogFragment.queryFolderToMoveNote.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    Intrinsics.checkNotNullParameter(loader, "loader");
                    super.onLoadFinished(loader, data);
                    ChooserFolderDialogFragment.this.dealData(data);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealData(Cursor cursor) {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "dealData");
        if (this.mActivity == null || this.isDestroyView || cursor == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        cursor.moveToPosition(-1);
        FolderModel folderModel = null;
        while (cursor.moveToNext()) {
            FolderModel folder = ItemStore.parseFolder(cursor);
            long id = folder.getId();
            if (id < 0) {
                folder.setSubject(FolderModel.INSTANCE.getSystemSubject(id));
            } else if (id == 0) {
                folderModel = folder;
            }
            if (id != 0) {
                Intrinsics.checkNotNull(folder);
                arrayList.add(folder);
                Logger logger2 = Logger.INSTANCE;
                String simpleName2 = getClass().getSimpleName();
                Intrinsics.checkNotNullExpressionValue(simpleName2, "getSimpleName(...)");
                logger2.d(simpleName2, " add folder:" + folder);
            }
        }
        getLoaderManager().destroyLoader(1);
        Utils.INSTANCE.sortFolderWithName(arrayList);
        if (folderModel != null) {
            arrayList.add(folderModel);
        }
        ViewFolderChooser viewFolderChooser = new ViewFolderChooser(arrayList, this.mFolderId, this.itemIds, getContext()) { // from class: com.miui.notes.feature.folder.ChooserFolderDialogFragment.dealData.1
            {
                ChooserFolderDialogFragment chooserFolderDialogFragment = ChooserFolderDialogFragment.this;
            }

            @Override // com.miui.notes.ui.fragment.BaseFolderChooser
            protected void onFolderChoose(long id2, long[] checkedIds, String subject) {
                if (ChooserFolderDialogFragment.this.mActivity == null || id2 == ChooserFolderDialogFragment.this.mFolderId) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER, subject);
                intent.putExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, id2);
                intent.putExtra(ChooserFolderDialogActivity.CHOOSER_MOVE_NOTE_IDS, ChooserFolderDialogFragment.this.itemIds);
                Logger logger3 = Logger.INSTANCE;
                long[] jArr = ChooserFolderDialogFragment.this.itemIds;
                logger3.d("ViewFolderChooser", "onFolderChoose, noteItemIds = " + (jArr != null ? ArraysKt.joinToString$default(jArr, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null) : null) + "->to folder:" + id2 + " ,act:" + ChooserFolderDialogFragment.this.getActivity());
                Activity activity = ChooserFolderDialogFragment.this.mActivity;
                Intrinsics.checkNotNull(activity);
                activity.setResult(-1, intent);
                Activity activity2 = ChooserFolderDialogFragment.this.mActivity;
                Intrinsics.checkNotNull(activity2);
                activity2.finish();
            }

            @Override // com.miui.notes.ui.fragment.BaseFolderChooser
            protected void onCancel() {
                ChooserFolderDialogFragment.this.getLoaderManager().destroyLoader(2);
            }

            @Override // com.miui.notes.ui.fragment.BaseFolderChooser
            protected void onDismiss(boolean needUpdateData) {
                ChooserFolderDialogFragment.this.setMViewNoteFolderChooser(null);
            }
        };
        this.mViewNoteFolderChooser = viewFolderChooser;
        Intrinsics.checkNotNull(viewFolderChooser);
        viewFolderChooser.show();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ViewFolderChooser viewFolderChooser = this.mViewNoteFolderChooser;
        if (viewFolderChooser != null) {
            Intrinsics.checkNotNull(viewFolderChooser);
            viewFolderChooser.smallWindowAdapter();
        }
    }

    @Override // com.miui.notes.ui.fragment.DialogManagedFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        ViewFolderChooser viewFolderChooser = this.mViewNoteFolderChooser;
        if (viewFolderChooser != null) {
            Intrinsics.checkNotNull(viewFolderChooser);
            viewFolderChooser.smallWindowAdapter();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isDestroyView = true;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        ViewFolderChooser viewFolderChooser = this.mViewNoteFolderChooser;
        if (viewFolderChooser != null) {
            Intrinsics.checkNotNull(viewFolderChooser);
            viewFolderChooser.cancel();
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.ui.fragment.ModuleFragment
    public boolean onBackPressed() {
        ViewFolderChooser viewFolderChooser = this.mViewNoteFolderChooser;
        if (viewFolderChooser == null) {
            return false;
        }
        Intrinsics.checkNotNull(viewFolderChooser);
        viewFolderChooser.stopScroll();
        return false;
    }
}
