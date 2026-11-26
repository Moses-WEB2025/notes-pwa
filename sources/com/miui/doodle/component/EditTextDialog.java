package com.miui.doodle.component;

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
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.doodle.R;
import com.miui.doodle.utils.Utils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: compiled from: EditTextDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\u0015\u001a\u00020\u0016H$J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0016J\b\u0010\u0019\u001a\u00020\u0016H&J\b\u0010\u001a\u001a\u00020\u0016H&R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011¨\u0006\u001b"}, d2 = {"Lcom/miui/doodle/component/EditTextDialog;", "Lmiuix/appcompat/app/AlertDialog;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "themeResId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;I)V", "mEditor", "Landroid/widget/EditText;", "getMEditor", "()Landroid/widget/EditText;", "mPositiveButton", "Landroid/widget/Button;", "getMPositiveButton", "()Landroid/widget/Button;", "setMPositiveButton", "(Landroid/widget/Button;)V", "mNegativeButton", "getMNegativeButton", "setMNegativeButton", "onInitialized", "", "dismiss", "show", "onPositiveButtonClick", "onNegativeButtonClick", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class EditTextDialog extends AlertDialog {
    private final EditText mEditor;
    private Button mNegativeButton;
    private Button mPositiveButton;

    protected abstract void onInitialized();

    public abstract void onNegativeButtonClick();

    public abstract void onPositiveButtonClick();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTextDialog(Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_text, (ViewGroup) null);
        setView(viewInflate);
        View viewFindViewById = viewInflate.findViewById(R.id.et_folder_name);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        EditText editText = (EditText) viewFindViewById;
        this.mEditor = editText;
        editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.doodle.component.EditTextDialog.1
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
                Button mPositiveButton = EditTextDialog.this.getMPositiveButton();
                if (mPositiveButton != null) {
                    String string = EditTextDialog.this.getMEditor().getText().toString();
                    int length = string.length() - 1;
                    int i2 = 0;
                    boolean z = false;
                    while (i2 <= length) {
                        boolean z2 = Intrinsics.compare((int) string.charAt(!z ? i2 : length), 32) <= 0;
                        if (z) {
                            if (!z2) {
                                break;
                            } else {
                                length--;
                            }
                        } else if (z2) {
                            i2++;
                        } else {
                            z = true;
                        }
                    }
                    mPositiveButton.setEnabled(!TextUtils.isEmpty(string.subSequence(i2, length + 1).toString()));
                }
            }
        });
        setButton(-1, context.getString(android.R.string.ok), (DialogInterface.OnClickListener) null);
        setButton(-2, context.getString(android.R.string.cancel), (DialogInterface.OnClickListener) null);
        setOnShowListener(new AnonymousClass2());
    }

    protected final EditText getMEditor() {
        return this.mEditor;
    }

    protected final Button getMPositiveButton() {
        return this.mPositiveButton;
    }

    protected final void setMPositiveButton(Button button) {
        this.mPositiveButton = button;
    }

    protected final Button getMNegativeButton() {
        return this.mNegativeButton;
    }

    protected final void setMNegativeButton(Button button) {
        this.mNegativeButton = button;
    }

    /* JADX INFO: renamed from: com.miui.doodle.component.EditTextDialog$2, reason: invalid class name */
    /* JADX INFO: compiled from: EditTextDialog.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/miui/doodle/component/EditTextDialog$2", "Landroid/content/DialogInterface$OnShowListener;", "onShow", "", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/content/DialogInterface;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass2 implements DialogInterface.OnShowListener {
        AnonymousClass2() {
        }

        @Override // android.content.DialogInterface.OnShowListener
        public void onShow(DialogInterface dialog) {
            EditTextDialog.this.getMEditor().requestFocus();
            EditText mEditor = EditTextDialog.this.getMEditor();
            final EditTextDialog editTextDialog = EditTextDialog.this;
            mEditor.postDelayed(new Runnable() { // from class: com.miui.doodle.component.EditTextDialog$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EditTextDialog.AnonymousClass2.onShow$lambda$0(editTextDialog);
                }
            }, 100L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onShow$lambda$0(EditTextDialog editTextDialog) {
            Utils.INSTANCE.showSoftInput(editTextDialog.getMEditor(), true);
        }
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Utils.INSTANCE.hideSoftInput(this.mEditor);
        super.dismiss();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        onInitialized();
        super.show();
        Button button = getButton(-1);
        this.mPositiveButton = button;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.component.EditTextDialog$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.onPositiveButtonClick();
                }
            });
        }
        Button button2 = getButton(-2);
        this.mNegativeButton = button2;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.component.EditTextDialog$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.onNegativeButtonClick();
                }
            });
        }
    }
}
