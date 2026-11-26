package com.miui.notes.page;

import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.tool.Logger;
import com.miui.mishare.tapshare.TapShareDataHandler;
import com.miui.mishare.tapshare.TapShareManager;
import com.miui.notes.R;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.tapshareutils.TapShareDataModel;
import com.miui.notes.tool.tapshareutils.TapShareDataPrepareUtils;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\b"}, d2 = {"com/miui/notes/page/BaseNoteListPageFragment$mTapShareSendCallback$1", "Lcom/miui/mishare/tapshare/TapShareManager$TapShareSendCallback;", "onTapShareSend", "", "tapShareDataHandler", "Lcom/miui/mishare/tapshare/TapShareDataHandler;", "onTapShareCancel", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BaseNoteListPageFragment$mTapShareSendCallback$1 implements TapShareManager.TapShareSendCallback {
    final /* synthetic */ BaseNoteListPageFragment this$0;

    BaseNoteListPageFragment$mTapShareSendCallback$1(BaseNoteListPageFragment baseNoteListPageFragment) {
        this.this$0 = baseNoteListPageFragment;
    }

    @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
    public boolean onTapShareSend(final TapShareDataHandler tapShareDataHandler) {
        final BaseNoteListPageFragment baseNoteListPageFragment = this.this$0;
        new Thread(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$mTapShareSendCallback$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BaseNoteListPageFragment$mTapShareSendCallback$1.onTapShareSend$lambda$1(baseNoteListPageFragment, tapShareDataHandler);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTapShareSend$lambda$1(final BaseNoteListPageFragment baseNoteListPageFragment, TapShareDataHandler tapShareDataHandler) {
        List<WorkingNote> selectedWorkingNotes = baseNoteListPageFragment.getSelectedWorkingNotes();
        if (selectedWorkingNotes.size() > 300) {
            baseNoteListPageFragment.handler.post(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$mTapShareSendCallback$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNoteListPageFragment$mTapShareSendCallback$1.onTapShareSend$lambda$1$lambda$0(baseNoteListPageFragment);
                }
            });
            if (tapShareDataHandler != null) {
                tapShareDataHandler.cancelSend(1, "selectedNoteCount is lager than 300");
            }
            Logger.INSTANCE.d(baseNoteListPageFragment.getTAG(), "TapShare cancelled: selected notes count " + selectedWorkingNotes.size() + " exceeds 300");
            return;
        }
        TapShareDataModel.MultipleNotesTapShareData multipleNotesTapShareDataPrepareMultipleNotesData = TapShareDataPrepareUtils.prepareMultipleNotesData(baseNoteListPageFragment.getContext(), selectedWorkingNotes, null);
        if (multipleNotesTapShareDataPrepareMultipleNotesData == null) {
            if (tapShareDataHandler != null) {
                tapShareDataHandler.cancelSend(1, "Fail to prepare data");
            }
        } else {
            if (TapShareDataPrepareUtils.shareMultipleNotes(baseNoteListPageFragment.getContext(), multipleNotesTapShareDataPrepareMultipleNotesData, tapShareDataHandler)) {
                return;
            }
            Intrinsics.checkNotNull(tapShareDataHandler);
            tapShareDataHandler.cancelSend(1, "Fail to share data");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTapShareSend$lambda$1$lambda$0(BaseNoteListPageFragment baseNoteListPageFragment) {
        FragmentActivity activity = baseNoteListPageFragment.getActivity();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = baseNoteListPageFragment.getString(R.string.tapshare_send_counts_exceed_toast);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String str = String.format(string, Arrays.copyOf(new Object[]{300}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Toast.makeText(activity, str, 0).show();
    }

    @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
    public void onTapShareCancel(TapShareDataHandler tapShareDataHandler) {
        Logger.INSTANCE.d(this.this$0.getTAG(), "Cancel tapShare");
        this.this$0.cleanTapShareFilesAsync();
    }
}
