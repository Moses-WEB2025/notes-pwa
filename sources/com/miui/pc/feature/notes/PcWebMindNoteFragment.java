package com.miui.pc.feature.notes;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.DragEvent;
import android.view.View;
import android.webkit.WebView;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface;
import com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcWebMindNoteFragment extends BaseWebMindNoteFragment {
    private static final int FIX_SAVE_DURATION = 1500;
    private BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.notes.PcWebMindNoteFragment.1
        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragDrop(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragEntered(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected void handleDragLocation(View view, DragEvent dragEvent) {
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragStarted(View view, DragEvent dragEvent) {
            return true;
        }
    };

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected int getLayoutId() {
        return R.layout.pc_webview_layout;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected long getSaveDataDuration() {
        return 1500L;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public int getUiMode() {
        return 2;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initKeyboardDetector() {
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initPasswordController() {
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public boolean isMobile() {
        return false;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    /* JADX INFO: renamed from: isSupportNavigator */
    protected boolean getIsSupportNavigator() {
        return false;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(R.style.NoteTheme_Light_PcEdit);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initView(View view) {
        view.setOnDragListener(this.mBlockDragListener);
        this.mWebView = (WebView) view.findViewById(R.id.web_view);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected BaseMindNoteBridgeInterface createBridgeInterFace() {
        return new PcMindNoteBridgeInterface(this);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public int getEditMarginTopInOverViewMode() {
        return !isAdded() ? 0 : 62;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public String getNightMode() {
        return Constants.LIGHT;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void disableLandscape(String str) {
        isAdded();
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public String getCreateTime() {
        if (this.mWorkingNote != null) {
            long createdDate = this.mWorkingNote.getCreatedDate();
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormatUtils.formatDateText(getContext(), createdDate)).append(StringUtils.SPACE).append(DateFormatUtils.formatTimeText(getContext(), createdDate));
            return sb.toString();
        }
        return "";
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public String getModifiedTime(String str) {
        return String.valueOf(DateFormatUtils.formatDateYearText(getActivity(), Long.parseLong(str)));
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void handleEditIntent(final Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_PENDING, false);
        this.mPendingEditIntentTask = new Runnable() { // from class: com.miui.pc.feature.notes.PcWebMindNoteFragment.2
            @Override // java.lang.Runnable
            public void run() {
                PcWebMindNoteFragment pcWebMindNoteFragment = PcWebMindNoteFragment.this;
                PcWebMindNoteFragment.this.mLoadTask = pcWebMindNoteFragment.new PcEditNoteLoadTask(intent, pcWebMindNoteFragment.getActivity());
                PcWebMindNoteFragment.this.mLoadTask.commit();
            }
        };
        if (booleanExtra || this.mPendingEditIntentTask == null) {
            return;
        }
        this.mPendingEditIntentTask.run();
        this.mPendingEditIntentTask = null;
    }

    public void saveToSynergy(String str) {
        Bitmap bitmapBase64ToBitmap2 = ImageUtils.INSTANCE.base64ToBitmap2(str);
        File file = new File(AttachmentUtils.getAttachmentPath(getContext(), AttachmentUtils.saveImageFile((Context) NoteApp.getInstance(), Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmapBase64ToBitmap2, "IMG_" + System.currentTimeMillis(), (String) null)), false)));
        if (file.exists()) {
            MiuiSynergySdk.getInstance().saveToSynergy(getActivity(), new ClipData(new ClipDescription(BaseDragListener.LABEL_NOTE_EDITOR, new String[]{ShareAppsAdapter.TYPE_IMAGE}), new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file))), "custom extra");
            if (bitmapBase64ToBitmap2 == null || bitmapBase64ToBitmap2.isRecycled()) {
                return;
            }
            bitmapBase64ToBitmap2.recycle();
        }
    }

    public class PcEditNoteLoadTask extends BaseWebMindNoteFragment.EditNoteLoadTask {
        public PcEditNoteLoadTask(Intent intent, Context context) {
            super(intent, context);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.EditNoteLoadTask, com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment.NoteLoadTask, com.miui.common.tool.async.Task
        public WorkingNote doLoad() {
            WorkingNote workingNoteLoadWorkingNote = loadWorkingNote();
            if (workingNoteLoadWorkingNote == null || PcWebMindNoteFragment.this.isWorkingNoteIdInvalid(workingNoteLoadWorkingNote.getNoteId())) {
                workingNoteLoadWorkingNote = parseWorkingNote();
            }
            if (workingNoteLoadWorkingNote != null) {
                workingNoteLoadWorkingNote.createNoteByPad(true);
                Bundle bundle = new Bundle();
                bundle.putLong("new_note_id", workingNoteLoadWorkingNote.getNoteId());
                PcWebMindNoteFragment.this.notifyAction(10, bundle);
            }
            return workingNoteLoadWorkingNote;
        }
    }
}
