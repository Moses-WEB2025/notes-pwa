package com.miui.notes.editor;

import android.app.Activity;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.document.DocumentReader;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment;
import com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper;
import com.miui.notes.basefeature.noteedit.RichTextFloatMenuInfo;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.EditorActionListener;
import com.miui.richeditor.IEditorPopMenu;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.util.NoteClipManager;

/* JADX INFO: loaded from: classes4.dex */
public class NoteRichEditorController extends BaseNoteRichEditorController {
    private static final String TAG = "NoteRichEditorController";
    protected EditorActionListener mActionListener;
    protected BaseNativeNoteEditFragment mFragment;

    public NoteRichEditorController(BaseNativeNoteEditFragment baseNativeNoteEditFragment, NoteRichEditor noteRichEditor, EditText editText) {
        this(baseNativeNoteEditFragment.getActivity(), noteRichEditor, editText);
        this.mFragment = baseNativeNoteEditFragment;
    }

    public NoteRichEditorController(Activity activity, NoteRichEditor noteRichEditor, EditText editText) {
        super(activity, noteRichEditor, editText);
    }

    @Override // com.miui.notes.editor.BaseNoteRichEditorController
    protected EditorActionListener createActionListener() {
        if (this.mActionListener == null) {
            this.mActionListener = new EditorActionListener() { // from class: com.miui.notes.editor.NoteRichEditorController.1
                @Override // com.miui.richeditor.EditorActionListener
                public void onPlayingRecord(boolean z) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onTrackEvent(String str) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onSaveData() {
                    NoteRichEditorController.this.mFragment.asyncSaveNote();
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onInsertImages(Uri... uriArr) {
                    NoteRichEditorController.this.mFragment.insertImages(uriArr);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onRichTextLoaded() {
                    if (NoteRichEditorController.this.mDataLoadedCallback != null) {
                        NoteRichEditorController.this.mDataLoadedCallback.run();
                    }
                    if (NoteRichEditorController.this.mPendingTaskOnDataLoaded != null) {
                        NoteRichEditorController.this.mPendingTaskOnDataLoaded.run();
                        NoteRichEditorController.this.mPendingTaskOnDataLoaded = null;
                    }
                    NoteRichEditorController.this.mFragment.onLoadDataFinish();
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr) {
                    Logger.INSTANCE.d(NoteRichEditorController.TAG, "onImageSpanClick");
                    NoteRichEditorController.this.showImageSpanMenu(baseImageSpan, iArr);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onImageSpanAnnotationClick(BaseImageSpan baseImageSpan, int[] iArr) {
                    Logger.INSTANCE.d(NoteRichEditorController.TAG, "onImageSpanAnnotationClick");
                    NoteRichEditorController.this.mFragment.annotateImage(baseImageSpan);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onLinkSpanClick(LinkCardSpan linkCardSpan, int[] iArr) {
                    Logger.INSTANCE.d(NoteRichEditorController.TAG, "onLinkSpanClick");
                    NoteRichEditorController.this.showLinkSpanMenu(linkCardSpan, iArr);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr) {
                    Logger.INSTANCE.d(NoteRichEditorController.TAG, "onHintSpanClick");
                    NoteRichEditorController.this.mFragment.onChangeToMindNote();
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onStopShowLoading() {
                    Logger.INSTANCE.d(NoteRichEditorController.TAG, "onStopShowLoading");
                    NoteRichEditorController.this.mFragment.onLoadDataFinish();
                }
            };
        }
        return this.mActionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showImageSpanMenu(final BaseImageSpan baseImageSpan, int[] iArr) {
        Logger.INSTANCE.d(TAG, "showImageSpanMenu,act:" + this.mActivity);
        if (!this.mFragment.isAdded() || this.mActivity == null || getContext() == null) {
            return;
        }
        if (RomUtils.isPadMode() || this.mActivity.getResources().getConfiguration().orientation != 2) {
            if (baseImageSpan instanceof NormalImageSpan) {
                String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), ((NormalImageSpan) baseImageSpan).getSrcFileId());
                if (DocumentReader.INSTANCE.isDoodleFile(attachmentPath)) {
                    Logger.INSTANCE.d(TAG, "showImageSpanMenu imagePath " + attachmentPath);
                    DoodleApplication.preloadData(attachmentPath, getContext());
                }
            }
            if (this.mNoteRichEditor.getText() != null) {
                this.mNoteRichEditor.setSelection(this.mNoteRichEditor.getText().getSpanEnd(baseImageSpan) + 1);
            }
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            prepareRichTextFloatMenu(this.mNoteRichEditor, baseImageSpan, rect2, rect);
            showRichTextFloatMenu(this.mNoteRichEditor, baseImageSpan, RichTextFloatMenuHelper.createImageActionMenu(this.mActivity, rect2, rect, !baseImageSpan.isShowBig(), new View.OnClickListener() { // from class: com.miui.notes.editor.NoteRichEditorController$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showImageSpanMenu$0(baseImageSpan, view);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showImageSpanMenu$0(BaseImageSpan baseImageSpan, View view) {
        switch (view.getId()) {
            case R.id.annotate_image /* 2131362007 */:
            case R.id.edit_image /* 2131362464 */:
            case R.id.scale_image /* 2131363347 */:
            case R.id.view_image /* 2131363771 */:
                if (this.mPopMenuListener != null) {
                    this.mPopMenuListener.onImageMenuClick(view, baseImageSpan);
                }
                break;
            case R.id.copy_image /* 2131362247 */:
                NoteClipManager.INSTANCE.copyImageSpan(baseImageSpan, getContext());
                if (this.mPopMenuListener != null) {
                    this.mPopMenuListener.onImageMenuClick(view, baseImageSpan);
                }
                break;
            case R.id.delete_image /* 2131362399 */:
                this.mNoteRichEditor.deleteImageSpan(baseImageSpan, true);
                if (this.mPopMenuListener != null) {
                    this.mPopMenuListener.onImageMenuClick(view, baseImageSpan);
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLinkSpanMenu(final LinkCardSpan linkCardSpan, int[] iArr) {
        Logger.INSTANCE.d(TAG, "showLinkSpanMenu,act:" + this.mActivity);
        if (!this.mFragment.isAdded() || this.mActivity == null) {
            return;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        prepareRichTextFloatMenu(this.mNoteRichEditor, linkCardSpan, rect2, rect);
        showRichTextFloatMenu(this.mNoteRichEditor, linkCardSpan, RichTextFloatMenuHelper.createLinkCardMenu(this.mActivity, rect2, rect, new View.OnClickListener() { // from class: com.miui.notes.editor.NoteRichEditorController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showLinkSpanMenu$1(linkCardSpan, view);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLinkSpanMenu$1(LinkCardSpan linkCardSpan, View view) {
        int id = view.getId();
        if (id != R.id.delete_image) {
            if (id == R.id.view_link && this.mPopMenuListener != null) {
                this.mPopMenuListener.onLinkCardMenuClick(view, linkCardSpan);
                return;
            }
            return;
        }
        this.mNoteRichEditor.deleteImageSpan(linkCardSpan, true);
    }

    private void prepareRichTextFloatMenu(NoteRichEditor noteRichEditor, BaseImageSpan baseImageSpan, Rect rect, Rect rect2) {
        Rect rect3 = new Rect();
        noteRichEditor.getLocalVisibleRect(rect3);
        noteRichEditor.getGlobalVisibleRect(rect2);
        rect.set(baseImageSpan.getBoundsInParent());
        rect.offset(rect2.left - rect3.left, rect2.top - rect3.top);
        Editable text = noteRichEditor.getText();
        if (text != null) {
            int spanStart = text.getSpanStart(baseImageSpan);
            int spanEnd = text.getSpanEnd(baseImageSpan);
            if (noteRichEditor.getSelectionStart() == spanStart && noteRichEditor.getSelectionEnd() == spanEnd) {
                noteRichEditor.setSelection(spanEnd);
            }
        }
        baseImageSpan.setSelected(true);
        noteRichEditor.setmIsRemoveFocus(true);
        noteRichEditor.clearFocus();
        noteRichEditor.refreshContainer();
    }

    private void showRichTextFloatMenu(final NoteRichEditor noteRichEditor, final BaseImageSpan baseImageSpan, final RichTextFloatMenuInfo richTextFloatMenuInfo) {
        richTextFloatMenuInfo.menu.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.miui.notes.editor.NoteRichEditorController$$ExternalSyntheticLambda0
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.lambda$showRichTextFloatMenu$2(baseImageSpan);
            }
        });
        IEditorPopMenu iEditorPopMenu = (IEditorPopMenu) richTextFloatMenuInfo.menu;
        this.mImagePopMenu = iEditorPopMenu;
        this.mShowingPopMenu = iEditorPopMenu;
        noteRichEditor.post(new Runnable() { // from class: com.miui.notes.editor.NoteRichEditorController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showRichTextFloatMenu$3(richTextFloatMenuInfo, noteRichEditor);
            }
        });
        if (this.mFragment.mAnnotationController == null || !this.mFragment.mAnnotationController.isShowing()) {
            return;
        }
        this.mFragment.mAnnotationController.hide();
        this.mFragment.mEditorView.restoreTextWatcher();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRichTextFloatMenu$2(BaseImageSpan baseImageSpan) {
        baseImageSpan.setSelected(false);
        if (this.mShowingPopMenu == this.mImagePopMenu) {
            this.mShowingPopMenu = null;
        }
        this.mImagePopMenu = null;
        this.mNoteRichEditor.requestLayout();
        this.mNoteRichEditor.invalidateDeeply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRichTextFloatMenu$3(RichTextFloatMenuInfo richTextFloatMenuInfo, NoteRichEditor noteRichEditor) {
        if (this.mFragment.isAdded()) {
            richTextFloatMenuInfo.menu.showAtLocation(noteRichEditor, richTextFloatMenuInfo.gravity, richTextFloatMenuInfo.showX, richTextFloatMenuInfo.showY);
        }
    }
}
