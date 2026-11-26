package com.miui.notes.ui.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.Logger;
import com.miui.notes.adapter.ChooseFolderAdapter;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.store.FolderStore;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseFolderChooser.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0016\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u0000 ,2\u00020\u0001:\u0001,B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\u0018H&J\u001a\u0010\u001a\u001a\u00020\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J$\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u00072\b\u0010!\u001a\u0004\u0018\u00010\t2\b\u0010\"\u001a\u0004\u0018\u00010#H$J\b\u0010$\u001a\u00020\u0018H$J\u0010\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'H$J\b\u0010*\u001a\u00020\u0018H\u0016J\b\u0010+\u001a\u00020\u0018H\u0004R\u0012\u0010\u0002\u001a\u00020\u00038\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010(\u001a\u00020'8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006-"}, d2 = {"Lcom/miui/notes/ui/fragment/BaseFolderChooser;", "", "mFragment", "Lcom/miui/notes/ui/fragment/DialogManagedFragment;", "mCursor", "Landroid/database/Cursor;", "mCurrentId", "", "mCheckedIds", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ui/fragment/DialogManagedFragment;Landroid/database/Cursor;Ljava/lang/Long;[J)V", "getMCursor", "()Landroid/database/Cursor;", "setMCursor", "(Landroid/database/Cursor;)V", "Ljava/lang/Long;", "mAdapter", "Lcom/miui/notes/adapter/ChooseFolderAdapter;", "getMAdapter", "()Lcom/miui/notes/adapter/ChooseFolderAdapter;", "setMAdapter", "(Lcom/miui/notes/adapter/ChooseFolderAdapter;)V", "show", "", ToDoStat.CANCEL, "onFolderItemClick", "view", "Landroid/view/View;", "position", "", "onFolderChoose", "id", "checkedIds", "subject", "", "onCancel", "onDismiss", "needUpdateData", "", "isNeedShiftOut", "()Z", "showCreateFolderDialog", "refreshFolderInfo", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseFolderChooser {
    public static final int REQUEST_CODE_QUERY_FOLDER = 2;
    private ChooseFolderAdapter mAdapter;
    protected long[] mCheckedIds;
    protected Long mCurrentId;
    private Cursor mCursor;
    protected DialogManagedFragment mFragment;

    public abstract void cancel();

    protected abstract void onCancel();

    protected abstract void onDismiss(boolean needUpdateData);

    protected abstract void onFolderChoose(long id, long[] checkedIds, String subject);

    public abstract void show();

    public BaseFolderChooser(DialogManagedFragment mFragment, Cursor cursor, Long l, long[] jArr) {
        Intrinsics.checkNotNullParameter(mFragment, "mFragment");
        this.mFragment = mFragment;
        this.mCursor = cursor;
        this.mCurrentId = l;
        this.mCheckedIds = jArr;
    }

    protected final Cursor getMCursor() {
        return this.mCursor;
    }

    protected final void setMCursor(Cursor cursor) {
        this.mCursor = cursor;
    }

    protected final ChooseFolderAdapter getMAdapter() {
        return this.mAdapter;
    }

    protected final void setMAdapter(ChooseFolderAdapter chooseFolderAdapter) {
        this.mAdapter = chooseFolderAdapter;
    }

    public void onFolderItemClick(View view, int position) {
        if (this.mFragment.getActivity() == null) {
            return;
        }
        ChooseFolderAdapter chooseFolderAdapter = this.mAdapter;
        Intrinsics.checkNotNull(chooseFolderAdapter);
        long itemId = chooseFolderAdapter.getItemId(position);
        if (itemId == 0) {
            showCreateFolderDialog();
            return;
        }
        if (itemId == -1) {
            itemId = 0;
        }
        ChooseFolderAdapter chooseFolderAdapter2 = this.mAdapter;
        Intrinsics.checkNotNull(chooseFolderAdapter2);
        onFolderChoose(itemId, this.mCheckedIds, chooseFolderAdapter2.getFolderName(position));
    }

    protected final boolean isNeedShiftOut() {
        Long l = this.mCurrentId;
        if (l != null && l.longValue() == 0) {
            return true;
        }
        Long l2 = this.mCurrentId;
        if (l2 != null && l2.longValue() == -4097) {
            return true;
        }
        Long l3 = this.mCurrentId;
        return l3 != null && l3.longValue() == -4;
    }

    public void showCreateFolderDialog() {
        Logger.INSTANCE.d("BaseFolderChooser", "showCreateFolderDialog");
        FragmentActivity fragmentActivityRequireActivity = this.mFragment.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(fragmentActivityRequireActivity);
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.ui.fragment.BaseFolderChooser$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseFolderChooser.showCreateFolderDialog$lambda$0(this.f$0, dialogInterface);
            }
        });
        createFolderDialog.show();
        this.mFragment.manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCreateFolderDialog$lambda$0(BaseFolderChooser baseFolderChooser, DialogInterface dialogInterface) {
        Intrinsics.checkNotNull(dialogInterface, "null cannot be cast to non-null type com.miui.notes.feature.folder.CreateFolderDialog");
        if (((CreateFolderDialog) dialogInterface).getFolderId() > 0) {
            baseFolderChooser.refreshFolderInfo();
        } else if (baseFolderChooser.mFragment.isResumed()) {
            baseFolderChooser.show();
        }
    }

    protected final void refreshFolderInfo() {
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = this.mFragment.getLoaderManager();
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
        folderStore.query(loaderManager, 1, 2, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.ui.fragment.BaseFolderChooser.refreshFolderInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                boolean[] zArrUpdateEnableNewDefaultFolderName = FolderStore.updateEnableNewDefaultFolderName(BaseNoteApp.INSTANCE.getInstance(), cursor);
                Logger logger = Logger.INSTANCE;
                String simpleName = getClass().getSimpleName();
                Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                logger.d(simpleName, zArrUpdateEnableNewDefaultFolderName[0] + "- " + zArrUpdateEnableNewDefaultFolderName[1]);
                if (cursor != null) {
                    cursor.close();
                }
                BaseFolderChooser.this.mFragment.getLoaderManager().destroyLoader(2);
            }
        });
    }
}
