package com.miui.pad.feature.notes.handwrite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.Fragment;

/* JADX INFO: compiled from: HandWriteHeaderController.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0002\u001b\u001cB\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\"\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000bH\u0016J\u0006\u0010\u001a\u001a\u00020\u0018R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001d"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController;", "Landroid/view/View$OnClickListener;", "mFragment", "Lmiuix/appcompat/app/Fragment;", "mCallback", "Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController$HeaderOperationCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lmiuix/appcompat/app/Fragment;Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController$HeaderOperationCallback;)V", "mShareOptionDialog", "Lmiuix/appcompat/app/AlertDialog;", "mMoreView", "Landroid/view/View;", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "layoutResource", "", "getLayoutResource", "()I", "onClick", "", "v", "dismissDialog", "HeaderOperationCallback", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWriteHeaderController implements View.OnClickListener {
    public static final int ACTION_SHARE = 1;
    private final HeaderOperationCallback mCallback;
    private final Fragment mFragment;
    private View mMoreView;
    private AlertDialog mShareOptionDialog;

    /* JADX INFO: compiled from: HandWriteHeaderController.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J-\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\b¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController$HeaderOperationCallback;", "", "onPerformAction", "", "action", "", "args", "", "(I[Ljava/lang/Object;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface HeaderOperationCallback {
        void onPerformAction(int action, Object... args);
    }

    private final int getLayoutResource() {
        return R.layout.pad_hand_write_note_header;
    }

    public HandWriteHeaderController(Fragment fragment, HeaderOperationCallback mCallback) {
        Intrinsics.checkNotNullParameter(mCallback, "mCallback");
        this.mFragment = fragment;
        this.mCallback = mCallback;
    }

    public final View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(getLayoutResource(), container, false);
        View viewFindViewById = viewInflate.findViewById(R.id.share);
        HandWriteHeaderController handWriteHeaderController = this;
        viewFindViewById.setOnClickListener(handWriteHeaderController);
        Intrinsics.checkNotNull(viewFindViewById);
        UIUtils.setFolmeHoverEffect(viewFindViewById);
        View viewFindViewById2 = viewInflate.findViewById(R.id.more);
        this.mMoreView = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(handWriteHeaderController);
        }
        View view = this.mMoreView;
        Intrinsics.checkNotNull(view);
        UIUtils.setFolmeHoverEffect(view);
        Intrinsics.checkNotNull(viewInflate);
        return viewInflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        int id = v.getId();
        if (id != R.id.more) {
            if (id != R.id.share) {
                return;
            }
            LiveDataEventBus.with(LiveDataEventBus.SEARCH_CLEAR_FOCUS).setValue(true);
            this.mCallback.onPerformAction(1, new Object[0]);
            return;
        }
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            fragment.showImmersionMenu(this.mMoreView, null);
            PadHandWriteStat.reportClickEvent(PadHandWriteStat.HAND_WEITE_BROWSER_MORE, null);
        }
    }

    public final void dismissDialog() {
        AlertDialog alertDialog = this.mShareOptionDialog;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            if (alertDialog.isShowing()) {
                AlertDialog alertDialog2 = this.mShareOptionDialog;
                Intrinsics.checkNotNull(alertDialog2);
                alertDialog2.dismiss();
            }
            this.mShareOptionDialog = null;
        }
    }
}
