package com.miui.notes.component;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.store.FolderStore;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreateFolderDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0019B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\b\u0010\u0016\u001a\u00020\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u0004\u0018\u00010\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\tR\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/component/CreateFolderDialog;", "Lcom/miui/notes/component/EditTextDialog;", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "tag", "", "getTag", "()Ljava/lang/String;", "value", "", "folderId", "getFolderId", "()J", "folderSubject", "getFolderSubject", "mMenuUpdateListener", "Lcom/miui/notes/component/CreateFolderDialog$MenuUpdateListener;", "onInitialized", "", "onPositiveButtonClick", "onNegativeButtonClick", "setMenuUpdateListener", "listener", "MenuUpdateListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreateFolderDialog extends EditTextDialog {
    private long folderId;
    private String folderSubject;
    private final Context mContext;
    private MenuUpdateListener mMenuUpdateListener;
    private final String tag;

    /* JADX INFO: compiled from: CreateFolderDialog.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/notes/component/CreateFolderDialog$MenuUpdateListener;", "", "updateMenuList", "", "newDir", "", "updateMenuListFailed", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface MenuUpdateListener {
        void updateMenuList(String newDir);

        void updateMenuListFailed();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateFolderDialog(Context mContext) {
        super(mContext);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        this.tag = "CreateFolderDialog";
    }

    public final String getTag() {
        return this.tag;
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final String getFolderSubject() {
        return this.folderSubject;
    }

    @Override // com.miui.notes.component.EditTextDialog
    protected void onInitialized() {
        EditText editText = this.mEditor;
        Intrinsics.checkNotNull(editText);
        editText.setText(FolderStore.INSTANCE.getNewDefaultFolderName());
        EditText editText2 = this.mEditor;
        Intrinsics.checkNotNull(editText2);
        EditText editText3 = this.mEditor;
        Intrinsics.checkNotNull(editText3);
        editText2.setSelection(editText3.getText().length());
        setTitle(this.mContext.getString(R.string.menu_create_folder));
    }

    @Override // com.miui.notes.component.EditTextDialog
    public void onPositiveButtonClick() {
        EditText editText = this.mEditor;
        Intrinsics.checkNotNull(editText);
        String string = editText.getText().toString();
        int length = string.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) string.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                } else {
                    length--;
                }
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        String string2 = string.subSequence(i, length + 1).toString();
        this.folderSubject = string2;
        Logger.INSTANCE.d(this.tag, "onPositiveButtonClick-with folder subject");
        if (FolderStore.exist(this.mContext, string2)) {
            Button button = this.mPositiveButton;
            if (button != null) {
                button.setEnabled(false);
            }
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.folder_exist), 1).show();
            EditText editText2 = this.mEditor;
            Intrinsics.checkNotNull(editText2);
            EditText editText3 = this.mEditor;
            Intrinsics.checkNotNull(editText3);
            editText2.setSelection(0, editText3.length());
            MenuUpdateListener menuUpdateListener = this.mMenuUpdateListener;
            if (menuUpdateListener != null) {
                Intrinsics.checkNotNull(menuUpdateListener);
                menuUpdateListener.updateMenuListFailed();
                return;
            }
            return;
        }
        long jAdd$default = FolderStore.add$default(FolderStore.INSTANCE, this.mContext, string2, 0L, 4, null);
        this.folderId = jAdd$default;
        if (jAdd$default <= 0 && FolderStore.restore(this.mContext, string2)) {
            this.folderId = FolderStore.subjectToId(this.mContext, string2);
        }
        MenuUpdateListener menuUpdateListener2 = this.mMenuUpdateListener;
        if (menuUpdateListener2 != null) {
            Intrinsics.checkNotNull(menuUpdateListener2);
            menuUpdateListener2.updateMenuList(string2);
        }
        dismiss();
    }

    @Override // com.miui.notes.component.EditTextDialog
    public void onNegativeButtonClick() {
        MenuUpdateListener menuUpdateListener = this.mMenuUpdateListener;
        if (menuUpdateListener != null) {
            Intrinsics.checkNotNull(menuUpdateListener);
            menuUpdateListener.updateMenuListFailed();
        }
        dismiss();
    }

    public final void setMenuUpdateListener(MenuUpdateListener listener) {
        this.mMenuUpdateListener = listener;
    }
}
