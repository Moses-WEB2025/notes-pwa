package com.miui.notes.component;

import android.app.Activity;
import android.content.DialogInterface;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.model.FolderModel;
import java.util.Iterator;
import java.util.List;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteFolderDialog {

    public interface DeleteDialogCallback {
        void onPositiveClick(List<FolderModel> list);
    }

    public static AlertDialog.Builder create(Activity activity, final List<FolderModel> list, final DeleteDialogCallback deleteDialogCallback) {
        String quantityString;
        String string = NoteApp.getInstance().getString(R.string.delete_folder_dialog_tile);
        Iterator<FolderModel> it = list.iterator();
        int count = 0;
        while (it.hasNext()) {
            count += it.next().getCount();
        }
        if (Utils.isUseChinese()) {
            quantityString = NoteApp.getInstance().getString(R.string.alert_message_delete, new Object[]{Utils.getAllItemTerms(count, list.size())});
        } else {
            int size = count + list.size();
            quantityString = NoteApp.getInstance().getResources().getQuantityString(R.plurals.alert_message_delete, size, Integer.valueOf(size));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 2131951644);
        builder.setActionSheetEnabled(true);
        builder.setTitle(string);
        builder.setMessage(quantityString);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.component.DeleteFolderDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                deleteDialogCallback.onPositiveClick(list);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder;
    }
}
