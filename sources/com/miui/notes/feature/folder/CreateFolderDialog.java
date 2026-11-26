package com.miui.notes.feature.folder;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.SoftKeyboardInputHelper;
import com.miui.notes.R;
import com.miui.notes.store.FolderStore;
import com.miui.notes.tool.DataUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: compiled from: CreateFolderDialog.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u001b\u001a\u00020\u001cH\u0004J\b\u0010\u001d\u001a\u00020\u001cH\u0002J\b\u0010\u001e\u001a\u00020\u001cH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\"\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/feature/folder/CreateFolderDialog;", "Lmiuix/appcompat/app/AlertDialog;", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "value", "", "folderId", "getFolderId", "()J", "", "folderName", "getFolderName", "()Ljava/lang/String;", "mFolderEditor", "Landroid/widget/EditText;", "getMFolderEditor", "()Landroid/widget/EditText;", "setMFolderEditor", "(Landroid/widget/EditText;)V", "mPositiveButton", "Landroid/widget/Button;", "getMPositiveButton", "()Landroid/widget/Button;", "setMPositiveButton", "(Landroid/widget/Button;)V", "initialize", "", "persist", "dismiss", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class CreateFolderDialog extends AlertDialog {
    private long folderId;
    private String folderName;
    private final Context mContext;
    private EditText mFolderEditor;
    private Button mPositiveButton;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateFolderDialog(Context mContext) {
        super(mContext);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        initialize();
        if (LiteUtils.isSuperLite()) {
            setEnableImmersive(true);
        }
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final String getFolderName() {
        return this.folderName;
    }

    protected final EditText getMFolderEditor() {
        return this.mFolderEditor;
    }

    protected final void setMFolderEditor(EditText editText) {
        this.mFolderEditor = editText;
    }

    protected final Button getMPositiveButton() {
        return this.mPositiveButton;
    }

    protected final void setMPositiveButton(Button button) {
        this.mPositiveButton = button;
    }

    protected final void initialize() {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "initialize");
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_text, (ViewGroup) null);
        setView(viewInflate);
        EditText editText = (EditText) viewInflate.findViewById(R.id.et_folder_name);
        this.mFolderEditor = editText;
        Intrinsics.checkNotNull(editText);
        editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.feature.folder.CreateFolderDialog.initialize.1
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
                if (CreateFolderDialog.this.getButton(-1) != null) {
                    Button button = CreateFolderDialog.this.getButton(-1);
                    EditText mFolderEditor = CreateFolderDialog.this.getMFolderEditor();
                    Intrinsics.checkNotNull(mFolderEditor);
                    String string = mFolderEditor.getText().toString();
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
        EditText editText2 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText2);
        editText2.setText(FolderStore.INSTANCE.getNewDefaultFolderName());
        EditText editText3 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText3);
        EditText editText4 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText4);
        editText3.setSelection(editText4.getText().length());
        EditText editText5 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText5);
        editText5.setFocusable(false);
        EditText editText6 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText6);
        editText6.setFocusableInTouchMode(false);
        setTitle(this.mContext.getString(R.string.menu_create_folder));
        setButton(-1, this.mContext.getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.miui.notes.feature.folder.CreateFolderDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CreateFolderDialog.initialize$lambda$0(this.f$0, dialogInterface, i);
            }
        });
        setButton(-2, this.mContext.getString(android.R.string.cancel), (DialogInterface.OnClickListener) null);
        EditText editText7 = this.mFolderEditor;
        Intrinsics.checkNotNull(editText7);
        editText7.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.notes.feature.folder.CreateFolderDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return CreateFolderDialog.initialize$lambda$1(this.f$0, view, i, keyEvent);
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.miui.notes.feature.folder.CreateFolderDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                CreateFolderDialog.initialize$lambda$4(this.f$0, dialogInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$0(CreateFolderDialog createFolderDialog, DialogInterface dialogInterface, int i) {
        Logger logger = Logger.INSTANCE;
        String simpleName = createFolderDialog.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "initialize /btn ok click " + createFolderDialog.hashCode() + ",which:" + i + ",stackTrace：" + Log.getStackTraceString(new Throwable()));
        createFolderDialog.persist();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initialize$lambda$1(CreateFolderDialog createFolderDialog, View view, int i, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(keyEvent, "keyEvent");
        Button button = createFolderDialog.getButton(-1);
        Button button2 = createFolderDialog.getButton(-2);
        Logger logger = Logger.INSTANCE;
        String simpleName = createFolderDialog.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onKey keycode: " + keyEvent);
        if (keyEvent.getKeyCode() == 66 && keyEvent.getAction() == 1 && button != null) {
            Logger logger2 = Logger.INSTANCE;
            String simpleName2 = createFolderDialog.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName2, "getSimpleName(...)");
            logger2.d(simpleName2, "call positive button ");
            button.callOnClick();
            return true;
        }
        if (keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 1 || button2 == null) {
            return false;
        }
        Logger logger3 = Logger.INSTANCE;
        String simpleName3 = createFolderDialog.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName3, "getSimpleName(...)");
        logger3.d(simpleName3, "call negative button ");
        button2.callOnClick();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$4(final CreateFolderDialog createFolderDialog, DialogInterface dialogInterface) {
        EditText editText = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText);
        editText.setFocusable(true);
        EditText editText2 = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText2);
        editText2.setFocusableInTouchMode(true);
        EditText editText3 = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText3);
        SoftKeyboardInputHelper.boostKeyboardForFocusView(editText3, new Function1() { // from class: com.miui.notes.feature.folder.CreateFolderDialog$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CreateFolderDialog.initialize$lambda$4$lambda$3(this.f$0, (Unit) obj);
            }
        });
        EditText editText4 = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText4);
        editText4.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initialize$lambda$4$lambda$3(final CreateFolderDialog createFolderDialog, Unit it) {
        Intrinsics.checkNotNullParameter(it, "it");
        EditText editText = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText);
        editText.postDelayed(new Runnable() { // from class: com.miui.notes.feature.folder.CreateFolderDialog$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                CreateFolderDialog.initialize$lambda$4$lambda$3$lambda$2(this.f$0);
            }
        }, 100L);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$4$lambda$3$lambda$2(CreateFolderDialog createFolderDialog) {
        EditText editText = createFolderDialog.mFolderEditor;
        Intrinsics.checkNotNull(editText);
        Utils.showSoftInput(editText);
    }

    private final void persist() {
        EditText editText = this.mFolderEditor;
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
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "persist/" + string2);
        if (TextUtils.isEmpty(string2)) {
            dismiss();
            return;
        }
        this.folderName = string2;
        if (DataUtils.checkVisibleFolderName(this.mContext.getContentResolver(), string2)) {
            Button button = this.mPositiveButton;
            if (button != null) {
                Intrinsics.checkNotNull(button);
                button.setEnabled(false);
            }
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.folder_exist), 1).show();
            EditText editText2 = this.mFolderEditor;
            Intrinsics.checkNotNull(editText2);
            EditText editText3 = this.mFolderEditor;
            Intrinsics.checkNotNull(editText3);
            editText2.setSelection(0, editText3.length());
            return;
        }
        long jAdd$default = FolderStore.add$default(FolderStore.INSTANCE, this.mContext, string2, 0L, 4, null);
        this.folderId = jAdd$default;
        if (jAdd$default <= 0 && DataUtils.restoreNoteFolder(this.mContext, string2)) {
            this.folderId = DataUtils.getVisibleFolderIdBySubject(this.mContext, string2);
        }
        PadHandWriteStat.reportMoreClickEvent("move_to_new");
        dismiss();
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }
}
