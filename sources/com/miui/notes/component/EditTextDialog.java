package com.miui.notes.component;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: compiled from: EditTextDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0004J\b\u0010\r\u001a\u00020\fH$J\b\u0010\u000e\u001a\u00020\fH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016J\b\u0010\u0010\u001a\u00020\fH&J\b\u0010\u0011\u001a\u00020\fH&R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u0004\u0018\u00010\t8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/component/EditTextDialog;", "Lmiuix/appcompat/app/AlertDialog;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mEditor", "Landroid/widget/EditText;", "mPositiveButton", "Landroid/widget/Button;", "mNegativeButton", "initialize", "", "onInitialized", "dismiss", "show", "onPositiveButtonClick", "onNegativeButtonClick", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class EditTextDialog extends AlertDialog {
    protected EditText mEditor;
    protected Button mNegativeButton;
    protected Button mPositiveButton;

    protected abstract void onInitialized();

    public abstract void onNegativeButtonClick();

    public abstract void onPositiveButtonClick();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTextDialog(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        initialize();
    }

    protected final void initialize() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_text, (ViewGroup) null);
        setView(viewInflate);
        EditText editText = (EditText) viewInflate.findViewById(R.id.et_folder_name);
        this.mEditor = editText;
        Intrinsics.checkNotNull(editText);
        editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.component.EditTextDialog.initialize.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intrinsics.checkNotNullParameter(s, "s");
                if (EditTextDialog.this.getButton(-1) != null) {
                    Button button = EditTextDialog.this.getButton(-1);
                    EditText editText2 = EditTextDialog.this.mEditor;
                    Intrinsics.checkNotNull(editText2);
                    String string = editText2.getText().toString();
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
                    button.setEnabled(!TextUtils.isEmpty(string.subSequence(i, length + 1).toString()));
                }
            }
        });
        setButton(-1, getContext().getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.miui.notes.component.EditTextDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.onPositiveButtonClick();
            }
        });
        setButton(-2, getContext().getString(android.R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.miui.notes.component.EditTextDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.onNegativeButtonClick();
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.notes.component.EditTextDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                EditTextDialog.initialize$lambda$3(this.f$0, dialogInterface);
            }
        });
        if (LiteUtils.isSuperLite()) {
            setEnableImmersive(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$3(final EditTextDialog editTextDialog, DialogInterface dialogInterface) {
        EditText editText = editTextDialog.mEditor;
        Intrinsics.checkNotNull(editText);
        editText.requestFocus();
        EditText editText2 = editTextDialog.mEditor;
        Intrinsics.checkNotNull(editText2);
        editText2.postDelayed(new Runnable() { // from class: com.miui.notes.component.EditTextDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EditTextDialog.initialize$lambda$3$lambda$2(this.f$0);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$3$lambda$2(EditTextDialog editTextDialog) {
        EditText editText = editTextDialog.mEditor;
        Intrinsics.checkNotNull(editText);
        Utils.showSoftInput(editText, true);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Utils.hideSoftInput(this.mEditor);
        super.dismiss();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        onInitialized();
        super.show();
        EditText editText = this.mEditor;
        Intrinsics.checkNotNull(editText);
        editText.requestFocus();
    }
}
