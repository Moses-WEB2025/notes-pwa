package com.miui.handwriting.common.view.popup;

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
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.Utils;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EditTextDialog extends AlertDialog {
    protected TextView mDescriptionTextView;
    protected EditText mEditor;
    protected Button mNegativeButton;
    protected Button mPositiveButton;
    protected TextView mResultTextView;

    protected abstract void onInitialized();

    public abstract void onNegativeButtonClick();

    public abstract void onPositiveButtonClick();

    public EditTextDialog(Context context) {
        this(context, false);
    }

    public EditTextDialog(Context context, boolean z) {
        this(context, z, false);
    }

    public EditTextDialog(Context context, boolean z, boolean z2) {
        super(context);
        initialize(z, z2);
    }

    protected void initialize(boolean z, boolean z2) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_text, (ViewGroup) null);
        setView(viewInflate);
        this.mDescriptionTextView = (TextView) viewInflate.findViewById(R.id.tv_description);
        this.mResultTextView = (TextView) viewInflate.findViewById(R.id.tv_result);
        if (z) {
            this.mDescriptionTextView.setVisibility(0);
        } else {
            this.mDescriptionTextView.setVisibility(8);
        }
        if (z2) {
            this.mResultTextView.setVisibility(0);
        } else {
            this.mResultTextView.setVisibility(8);
        }
        EditText editText = (EditText) viewInflate.findViewById(R.id.et_input);
        this.mEditor = editText;
        editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.handwriting.common.view.popup.EditTextDialog.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (EditTextDialog.this.getButton(-1) != null) {
                    if (TextUtils.isEmpty(EditTextDialog.this.mEditor.getText().toString().trim())) {
                        EditTextDialog.this.getButton(-1).setEnabled(false);
                    } else {
                        EditTextDialog.this.getButton(-1).setEnabled(true);
                    }
                }
            }
        });
        setButton(-1, getContext().getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.EditTextDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m758x3be6f68c(dialogInterface, i);
            }
        });
        setButton(-2, getContext().getString(android.R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.EditTextDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m759x653b4bcd(dialogInterface, i);
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.handwriting.common.view.popup.EditTextDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.m761xb7e3f64f(dialogInterface);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initialize$0$com-miui-handwriting-common-view-popup-EditTextDialog, reason: not valid java name */
    /* synthetic */ void m758x3be6f68c(DialogInterface dialogInterface, int i) {
        onPositiveButtonClick();
    }

    /* JADX INFO: renamed from: lambda$initialize$1$com-miui-handwriting-common-view-popup-EditTextDialog, reason: not valid java name */
    /* synthetic */ void m759x653b4bcd(DialogInterface dialogInterface, int i) {
        onNegativeButtonClick();
    }

    /* JADX INFO: renamed from: lambda$initialize$3$com-miui-handwriting-common-view-popup-EditTextDialog, reason: not valid java name */
    /* synthetic */ void m761xb7e3f64f(DialogInterface dialogInterface) {
        this.mEditor.requestFocus();
        this.mEditor.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.popup.EditTextDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m760x8e8fa10e();
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: lambda$initialize$2$com-miui-handwriting-common-view-popup-EditTextDialog, reason: not valid java name */
    /* synthetic */ void m760x8e8fa10e() {
        Utils.showSoftInput(this.mEditor);
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
        this.mEditor.requestFocus();
    }
}
