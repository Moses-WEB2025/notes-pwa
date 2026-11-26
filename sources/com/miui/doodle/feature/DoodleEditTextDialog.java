package com.miui.doodle.feature;

import android.content.Context;
import android.graphics.Rect;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.R;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.component.EditTextDialog;
import com.miui.doodle.feature.action.TextEditAction;
import com.miui.doodle.feature.draw.DoodleText;
import com.miui.doodle.feature.draw.DoodleView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleEditTextDialog.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\b\u0010\u0016\u001a\u00020\u0014H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/miui/doodle/feature/DoodleEditTextDialog;", "Lcom/miui/doodle/component/EditTextDialog;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "doodleText", "Lcom/miui/doodle/feature/draw/DoodleText;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/base/core/IDoodle;Lcom/miui/doodle/feature/draw/DoodleText;)V", "oldText", "", "getOldText", "()Ljava/lang/String;", "setOldText", "(Ljava/lang/String;)V", "newText", "getNewText", "setNewText", "onInitialized", "", "onPositiveButtonClick", "onNegativeButtonClick", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleEditTextDialog extends EditTextDialog {
    private final IDoodle doodle;
    private final DoodleText doodleText;
    public String newText;
    public String oldText;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleEditTextDialog(Context context, IDoodle doodle, DoodleText doodleText) {
        super(context, R.style.AlertDialog_Theme_Light);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(doodleText, "doodleText");
        this.doodle = doodle;
        this.doodleText = doodleText;
    }

    public final String getOldText() {
        String str = this.oldText;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("oldText");
        return null;
    }

    public final void setOldText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oldText = str;
    }

    public final String getNewText() {
        String str = this.newText;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("newText");
        return null;
    }

    public final void setNewText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newText = str;
    }

    @Override // com.miui.doodle.component.EditTextDialog
    protected void onInitialized() {
        getMEditor().setText(this.doodleText.getText());
        setOldText(this.doodleText.getText());
    }

    @Override // com.miui.doodle.component.EditTextDialog
    public void onPositiveButtonClick() {
        Rect rect = new Rect();
        rect.union(this.doodleText.getBoundsInCanvas());
        this.doodleText.setText(getMEditor().getText().toString());
        setNewText(this.doodleText.getText());
        rect.union(this.doodleText.getBoundsInCanvas());
        IDoodle iDoodle = this.doodle;
        Intrinsics.checkNotNull(iDoodle, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleView");
        ((DoodleView) iDoodle).resetTextOrBitmap(this.doodleText, rect);
        if (!Intrinsics.areEqual(getOldText(), getNewText())) {
            IDoodle iDoodle2 = this.doodle;
            Intrinsics.checkNotNull(iDoodle2, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleView");
            ((DoodleView) iDoodle2).pushToActionHistory(new TextEditAction(this.doodleText, getOldText(), getNewText()));
        }
        dismiss();
        this.doodle.getMarkDoodleOperation().invoke(9);
    }

    @Override // com.miui.doodle.component.EditTextDialog
    public void onNegativeButtonClick() {
        dismiss();
        this.doodle.getMarkDoodleOperation().invoke(10);
    }
}
