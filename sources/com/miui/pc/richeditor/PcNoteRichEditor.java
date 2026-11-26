package com.miui.pc.richeditor;

import android.R;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.ImageUtils;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.document.DocumentReader;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.pc.feature.notes.PcImageEditMenu;
import com.miui.pc.richeditor.style.PcLinkCardSpanHelper;
import com.miui.pc.richeditor.style.PcMindEntrySpan;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.MindDrawable;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.ClickableElement;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.util.ResourceParser;
import java.io.File;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteRichEditor extends NoteRichEditor {
    private GestureDetector mDetector;
    private PcImageEditMenu mImageEditMenu;
    private boolean mIsInEditMode;
    private RightClickableSpanDetector<BaseAudioSpan> mRightClickAudioDetector;
    private RightClickableSpanDetector<NormalImageSpan> mRightClickImageDetector;
    private View mRootContainer;
    private StateListener mStateListener;

    public interface StateListener {
        void onImageEditMenu(boolean z);

        void onMindSpanTextClick();

        void onStartPlayAudio();

        void onStopPlayAudio();
    }

    public PcNoteRichEditor(Context context) {
        super(context);
        this.mStateListener = null;
        this.mImageEditMenu = null;
        this.mIsInEditMode = false;
        this.mRootContainer = null;
    }

    public PcNoteRichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public PcNoteRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStateListener = null;
        this.mImageEditMenu = null;
        this.mIsInEditMode = false;
        this.mRootContainer = null;
        this.mRightClickImageDetector = new RightClickableSpanDetector<>(NormalImageSpan.class);
        this.mRightClickAudioDetector = new RightClickableSpanDetector<>(BaseAudioSpan.class);
        this.mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.pc.richeditor.PcNoteRichEditor.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnContextClickListener
            public boolean onContextClick(MotionEvent motionEvent) {
                if (motionEvent.getActionButton() == 2) {
                    if (PcNoteRichEditor.this.mRightClickImageDetector.handleMotionEvent(motionEvent) || PcNoteRichEditor.this.mRightClickAudioDetector.handleMotionEvent(motionEvent)) {
                        return false;
                    }
                    if (Build.VERSION.SDK_INT <= 30) {
                        PcNoteRichEditor.this.dismissShowingMenu();
                        PcNoteRichEditor.this.requestFocus();
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 0, x, y, 0);
                        PcNoteRichEditor.this.onTouchEvent(motionEventObtain);
                        MotionEvent motionEventObtain2 = MotionEvent.obtain(0L, 0L, 1, x, y, 0);
                        PcNoteRichEditor.this.onTouchEvent(motionEventObtain2);
                        PcNoteRichEditor.this.onTouchEvent(motionEventObtain);
                        PcNoteRichEditor.this.onTouchEvent(motionEventObtain2);
                        Logger.INSTANCE.d("PcNoteRichEditor", "onRightClick() x = " + x + " y = " + y);
                    }
                    return super.onContextClick(motionEvent);
                }
                return super.onContextClick(motionEvent);
            }
        });
        this.mSelectLinePaint.setStyle(Paint.Style.STROKE);
        this.mSelectLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mSelectLinePaint.setStrokeWidth(getContext().getResources().getDimensionPixelSize(com.miui.notes.R.dimen.pc_notes_edit_selectline_height));
        this.mSelectLinePaint.setColor(getContext().getColor(com.miui.notes.R.color.theme_light_cursor_color));
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void updateFontSizeId(int i) {
        if (this.mFontSizeId == i) {
            return;
        }
        this.mFontSizeId = i;
        this.mEditorImageRender.clearSpanDrawableCache();
        this.mFontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        this.mFontInnerScale = ResourceParser.TextAppearanceResources.FONT_SCALE[this.mFontSizeId];
        updateLineStyleSpanByFontSizeChange();
        setTextSize(0, getContext().getResources().getDimensionPixelSize(com.miui.notes.R.dimen.pc_text_font_size));
        updateFontSize();
        refreshContainer();
    }

    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    public void setIsInEditMode(boolean z) {
        this.mIsInEditMode = z;
    }

    @Override // android.view.View
    public boolean isInEditMode() {
        return this.mIsInEditMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToSynergy(NormalImageSpan normalImageSpan) {
        if (getActionListener() != null) {
            getActionListener().onSaveToSynergy(normalImageSpan);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PcEditorActionListener getActionListener() {
        if (this.mActionListener == null) {
            return null;
        }
        return (PcEditorActionListener) this.mActionListener;
    }

    public void dismissShowingMenu() {
        PcImageEditMenu pcImageEditMenu = this.mImageEditMenu;
        if (pcImageEditMenu != null) {
            pcImageEditMenu.dismiss();
        }
    }

    @Override // android.widget.TextView, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setCursorVisible(boolean z) {
        super.setCursorVisible(z);
        if (!z || this.mSelectImageSpan == null) {
            return;
        }
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        int spanStart = getText().getSpanStart(this.mSelectImageSpan);
        int spanEnd = getText().getSpanEnd(this.mSelectImageSpan);
        if (selectionStart == spanStart && selectionEnd == spanEnd) {
            return;
        }
        this.mSelectImageSpan.setSelected(false);
        this.mSelectImageSpan = null;
        refreshContainer();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mDetector != null && motionEvent.getAction() != 8) {
            return this.mDetector.onGenericMotionEvent(motionEvent);
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // com.miui.notes.editor.NoteRichEditor, com.miui.richeditor.RichEditTextView, android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean zOnKeyDown = this.mOutKeyListener != null ? this.mOutKeyListener.onKeyDown(i, keyEvent) : false;
        if (!isEnabled()) {
            return false;
        }
        if ((i != 67 && i != 112) || this.mSelectImageSpan == null || isReadOnlyMode()) {
            return zOnKeyDown ? zOnKeyDown : super.onKeyDown(i, keyEvent);
        }
        this.mSelectImageSpan.onSelected(false);
        deleteImageSpan(this.mSelectImageSpan, false);
        this.mSelectImageSpan = null;
        dismissShowingMenu();
        return true;
    }

    @Override // com.miui.notes.editor.NoteRichEditor, android.widget.EditText, android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        boolean zOnKeyShortcut = this.mOutKeyListener != null ? this.mOutKeyListener.onKeyShortcut(i, keyEvent) : false;
        if (zOnKeyShortcut) {
            return zOnKeyShortcut;
        }
        boolean zOnKeyShortcut2 = super.onKeyShortcut(i, keyEvent);
        if (zOnKeyShortcut2 || !keyEvent.hasModifiers(4096)) {
            if (this.mOutKeyListener != null) {
                this.mOutKeyListener.onKeyShortcut(i, keyEvent);
            }
        } else {
            if (i == 30) {
                setFontBold();
                return true;
            }
            if (i == 33) {
                setAlignCenter();
                return true;
            }
            if (i == 46) {
                setAlignRight();
                return true;
            }
            if (i == 49) {
                setUnderline();
                return true;
            }
            if (i == 53) {
                redo();
                return true;
            }
            if (i == 71) {
                reduceFontSize();
                return true;
            }
            if (i != 72) {
                switch (i) {
                    case 35:
                        setEditorBullet();
                        return true;
                    case 36:
                        setBgHighLight();
                        return true;
                    case 37:
                        setFontItalic();
                        return true;
                    default:
                        if (this.mOutKeyListener != null) {
                            this.mOutKeyListener.onKeyShortcut(i, keyEvent);
                            return zOnKeyShortcut2;
                        }
                        break;
                }
            } else {
                increaseFontSize();
                return true;
            }
        }
        return zOnKeyShortcut2;
    }

    @Override // com.miui.notes.editor.NoteRichEditor, com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initStyleFactory() {
        this.mStyleFactory = new PcCommonStyleFactory(getContext(), this);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initFontSettings() {
        this.mFontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        this.mFontInnerScale = 1.0f;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initImageRender() {
        this.mEditorImageRender = new PcEditorImageRender(this, getContentWidth());
    }

    public void setRootContainer(View view) {
        this.mRootContainer = view;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public View getRootContainer() {
        View view = this.mRootContainer;
        return view != null ? view : super.getRootContainer();
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public LinkCardSpanHelper createLinkCardSpanHelper() {
        return new PcLinkCardSpanHelper(getEditorTheme());
    }

    @Override // com.miui.notes.editor.NoteRichEditor, com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public SpannableString createHintEntrySpanText() {
        SpannableString spannableString = new SpannableString(getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_hint_newversion) + StringUtils.SPACE);
        float fMeasureText = getPaint().measureText(getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_hint_newversion)) + ((int) getContext().getResources().getDimension(com.miui.notes.R.dimen.pc_mind_entrance_margin_start));
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(getResources().getDimension(com.miui.notes.R.dimen.pc_mind_entrance_text_size));
        MindDrawable mindDrawable = new MindDrawable(NoteApp.getInstance(), 10, (int) textPaint.measureText(getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_icon_text_newversion)));
        mindDrawable.setBounds(0, -((int) getResources().getDimension(com.miui.notes.R.dimen.pc_mind_entrance_padding_top)), mindDrawable.getIntrinsicWidth(), mindDrawable.getIntrinsicHeight());
        int bubbleWidth = mindDrawable.getBubbleWidth();
        int windowWidth = getWindowWidth();
        int windowWidth2 = getWindowWidth() - bubbleWidth;
        PcMindEntrySpan pcMindEntrySpan = new PcMindEntrySpan(this, mindDrawable, 0, (int) fMeasureText, 0);
        float f = fMeasureText + 0;
        if (f <= windowWidth2 || f > windowWidth) {
            float f2 = windowWidth;
            if (f > f2) {
                pcMindEntrySpan = new PcMindEntrySpan(this, mindDrawable, 0, (int) (f - f2), getLineHeight());
            }
        } else {
            pcMindEntrySpan = new PcMindEntrySpan(this, mindDrawable, 0, (int) getContext().getResources().getDimension(com.miui.notes.R.dimen.pc_mind_entrance_margin_start), getLineHeight());
        }
        spannableString.setSpan(pcMindEntrySpan, spannableString.length() - 1, spannableString.length(), 17);
        return spannableString;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IAudioEditorContext
    public void startMediaPlayer(BaseAudioSpan baseAudioSpan, String str) {
        StateListener stateListener = this.mStateListener;
        if (stateListener != null) {
            stateListener.onStartPlayAudio();
        }
        super.startMediaPlayer(baseAudioSpan, str);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IAudioEditorContext
    public void stopMediaPlayerIfNeed() {
        StateListener stateListener = this.mStateListener;
        if (stateListener != null) {
            stateListener.onStopPlayAudio();
        }
        super.stopMediaPlayerIfNeed();
    }

    @Override // android.widget.TextView
    public boolean bringPointIntoView(int i) {
        if (this.mSelectImageSpan != null) {
            return false;
        }
        return super.bringPointIntoView(i);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public void onLinkCardClick(LinkCardSpan linkCardSpan, int[] iArr) {
        if (isReadOnlyMode()) {
            return;
        }
        if (this.mSelectImageSpan != null) {
            this.mSelectImageSpan.setSelected(false);
        }
        linkCardSpan.setSelected(true);
        this.mSelectImageSpan = linkCardSpan;
        setCursorVisible(false);
        refreshContainer();
        setSelection(getText().getSpanEnd(linkCardSpan));
        if (isFocused()) {
            return;
        }
        requestFocus();
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr) {
        if (isReadOnlyMode()) {
            return;
        }
        if (this.mSelectImageSpan != null) {
            this.mSelectImageSpan.setSelected(false);
        }
        baseImageSpan.setSelected(true);
        this.mSelectImageSpan = baseImageSpan;
        setCursorVisible(false);
        refreshContainer();
        setSelection(getText().getSpanEnd(baseImageSpan));
        if (isFocused()) {
            return;
        }
        requestFocus();
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public void onImageSpanRightClick(final BaseImageSpan baseImageSpan, int[] iArr) {
        if (isReadOnlyMode()) {
            return;
        }
        Rect bounds = baseImageSpan.getBounds();
        if (iArr[0] + baseImageSpan.mSpanLeft < bounds.left || iArr[0] + baseImageSpan.mSpanLeft > bounds.right) {
            if (isInEditMode()) {
                setSelection(getText().getSpanEnd(baseImageSpan));
                return;
            }
            return;
        }
        if (baseImageSpan instanceof NormalImageSpan) {
            String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), ((NormalImageSpan) baseImageSpan).getSrcFileId());
            if (DocumentReader.INSTANCE.isDoodleFile(attachmentPath)) {
                DoodleApplication.preloadData(attachmentPath, getContext());
            }
        }
        if (this.mSelectImageSpan != null) {
            this.mSelectImageSpan.setSelected(false);
        }
        if (this.mImageEditMenu == null) {
            this.mImageEditMenu = new PcImageEditMenu(getContext());
        }
        StateListener stateListener = this.mStateListener;
        if (stateListener != null) {
            stateListener.onImageEditMenu(true);
        }
        clearFocus();
        this.mImageEditMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.pc.richeditor.PcNoteRichEditor.2
            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onShow() {
                PcNoteRichEditor.this.requestFocus();
                PcNoteRichEditor pcNoteRichEditor = PcNoteRichEditor.this;
                pcNoteRichEditor.setSelection(pcNoteRichEditor.getText().getSpanStart(baseImageSpan));
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onDismiss() {
                if (PcNoteRichEditor.this.mStateListener != null) {
                    PcNoteRichEditor.this.mStateListener.onImageEditMenu(false);
                }
                baseImageSpan.setSelected(false);
                PcNoteRichEditor.this.requestLayout();
                PcNoteRichEditor.this.invalidateDeeply();
                if (PcNoteRichEditor.this.isInEditMode()) {
                    PcNoteRichEditor.this.setCursorVisible(true);
                }
                PcNoteRichEditor.this.mImageEditMenu = null;
            }

            @Override // com.miui.common.view.IPopMenu.OnMenuListener
            public void onItemSelected(View view, int i) {
                switch (view.getId()) {
                    case R.id.cut:
                        PcNoteRichEditor.this.cutImageSpan(baseImageSpan, true);
                        break;
                    case R.id.copy:
                        PcNoteRichEditor.this.onTextContextMenuItem(R.id.copy);
                        break;
                    case com.miui.notes.R.id.annotate_image /* 2131362007 */:
                    case com.miui.notes.R.id.edit_image /* 2131362464 */:
                    case com.miui.notes.R.id.scale_image /* 2131363347 */:
                        if (PcNoteRichEditor.this.getActionListener() != null) {
                            PcNoteRichEditor.this.getActionListener().onImageMenuClick(view, baseImageSpan);
                        }
                        break;
                    case com.miui.notes.R.id.delete /* 2131362396 */:
                        PcNoteRichEditor.this.deleteImageSpan(baseImageSpan, true);
                        break;
                    case com.miui.notes.R.id.save_as /* 2131363336 */:
                        PcNoteRichEditor.this.saveToSynergy((NormalImageSpan) baseImageSpan);
                        break;
                }
            }
        });
        this.mImageEditMenu.setImageShowState(baseImageSpan.isShowBig());
        this.mImageEditMenu.show(this, iArr);
        baseImageSpan.setSelected(true);
        setCursorVisible(false);
        refreshContainer();
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public void onImageSpanLongClick(final BaseImageSpan baseImageSpan, final int[] iArr) {
        if (isEnabled()) {
            setCursorVisible(false);
            refreshContainer();
            post(new Runnable() { // from class: com.miui.pc.richeditor.PcNoteRichEditor.3
                @Override // java.lang.Runnable
                public void run() {
                    BaseImageSpan baseImageSpan2 = baseImageSpan;
                    if (baseImageSpan2 instanceof BaseAudioSpan) {
                        PcNoteRichEditor.this.setupDraggableAudioSpan((BaseAudioSpan) baseImageSpan2, iArr);
                    } else if (baseImageSpan2 instanceof NormalImageSpan) {
                        PcNoteRichEditor.this.lambda$onImageSpanLongClick$2(baseImageSpan2, iArr);
                    }
                }
            });
        }
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr) {
        super.onHintSpanClick(editorHintEntrySpan, z, iArr);
        StateListener stateListener = this.mStateListener;
        if (stateListener != null) {
            stateListener.onMindSpanTextClick();
        }
    }

    @Override // com.miui.richeditor.RichEditTextView
    public void cutImageSpan(BaseImageSpan baseImageSpan, boolean z) {
        dismissShowingMenu();
        super.cutImageSpan(baseImageSpan, z);
    }

    protected void setupDraggableAudioSpan(BaseAudioSpan baseAudioSpan, int[] iArr) {
        this.mDraggingImageSpan = baseAudioSpan;
        Logger.INSTANCE.i("AudioSpan", "setupDraggableImage " + baseAudioSpan.getBounds().toString());
        iArr[0] = (int) ((iArr[0] - baseAudioSpan.getBounds().left) * 0.69f);
        iArr[1] = (int) (((iArr[1] - baseAudioSpan.getBounds().top) + getY()) * 0.69f);
        AppCompatImageView appCompatImageView = (AppCompatImageView) View.inflate(getContext(), com.miui.notes.R.layout.note_edit_image_drag_thumbnail, null);
        if (appCompatImageView == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        Drawable drawableForShadow = baseAudioSpan.getDrawableForShadow();
        drawableForShadow.setAlpha(76);
        appCompatImageView.setImageDrawable(drawableForShadow);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        appCompatImageView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        appCompatImageView.layout(0, 0, (int) (appCompatImageView.getMeasuredWidth() * 0.69f), (int) (appCompatImageView.getMeasuredHeight() * 0.69f));
        appCompatImageView.invalidate();
        RichEditTextView.DragShadowBuilder dragShadowBuilder = new RichEditTextView.DragShadowBuilder(appCompatImageView, iArr);
        File file = new File(AttachmentUtils.getAttachmentPath(getContext(), baseAudioSpan.getSrcFileId()));
        if (file.exists()) {
            try {
                startDragAndDrop(new ClipData(new ClipDescription(BaseDragListener.LABEL_NOTE_EDITOR, new String[]{"audio/*"}), new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file))), dragShadowBuilder, baseAudioSpan, 512);
            } catch (Exception e) {
                Logger.INSTANCE.d("PcEditor", "startDragAndDrop error " + e.getMessage());
            }
        }
    }

    protected class RightClickableSpanDetector<T extends ClickableElement> {
        private boolean hasPerformedLongPress;
        private T mActiveSpan;
        private Class<T> mClass;
        private CheckForSpanLongPress pendingCheckForLongPress;

        public RightClickableSpanDetector(Class<T> cls) {
            this.mClass = cls;
        }

        public boolean handleMotionEvent(MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 11) {
                T t = (T) getActiveSpan(motionEvent);
                this.mActiveSpan = t;
                if (t != null) {
                    if (!isTouchIn(t, motionEvent)) {
                        return true;
                    }
                    this.mActiveSpan.setPressed(false);
                    this.mActiveSpan.onRightClick((int) motionEvent.getX(), (int) motionEvent.getY());
                    this.mActiveSpan = null;
                    return true;
                }
            }
            this.mActiveSpan = null;
            return false;
        }

        private T getActiveSpan(MotionEvent motionEvent) {
            Editable text = PcNoteRichEditor.this.getText();
            int i = 0;
            if (TextUtils.isEmpty(text) && !TextUtils.isEmpty(PcNoteRichEditor.this.getHint())) {
                SpannableString spannableString = new SpannableString(PcNoteRichEditor.this.getHint());
                ClickableElement[] clickableElementArr = (ClickableElement[]) spannableString.getSpans(0, spannableString.length(), this.mClass);
                int length = clickableElementArr.length;
                while (i < length) {
                    T t = (T) clickableElementArr[i];
                    if (isTouchIn(t, motionEvent)) {
                        return t;
                    }
                    i++;
                }
                return null;
            }
            ClickableElement[] clickableElementArr2 = (ClickableElement[]) text.getSpans(0, text.length(), this.mClass);
            int length2 = clickableElementArr2.length;
            while (i < length2) {
                T t2 = (T) clickableElementArr2[i];
                if (isTouchIn(t2, motionEvent)) {
                    return t2;
                }
                i++;
            }
            return null;
        }

        private boolean isTouchIn(T t, MotionEvent motionEvent) {
            return t.isTouchIn((int) motionEvent.getX(), (int) motionEvent.getY());
        }

        private void isTouchPointIn(T t, MotionEvent motionEvent) {
            isTouchIn(t, motionEvent);
            t.onTouchPoint((int) motionEvent.getX(), (int) motionEvent.getY(), motionEvent);
        }

        private void checkForLongClick(T t, long j, float f, float f2) {
            this.hasPerformedLongPress = false;
            if (this.pendingCheckForLongPress == null) {
                this.pendingCheckForLongPress = new CheckForSpanLongPress();
            }
            this.pendingCheckForLongPress.setAnchor(t, f, f2);
            this.pendingCheckForLongPress.rememberPressedState();
            PcNoteRichEditor.this.postDelayed(this.pendingCheckForLongPress, j);
        }

        private void removeLongPressCallback() {
            CheckForSpanLongPress checkForSpanLongPress;
            if (this.hasPerformedLongPress || (checkForSpanLongPress = this.pendingCheckForLongPress) == null) {
                return;
            }
            PcNoteRichEditor.this.removeCallbacks(checkForSpanLongPress);
            this.pendingCheckForLongPress = null;
        }

        private final class CheckForSpanLongPress implements Runnable {
            private T mActiveSpan;
            private boolean mOriginalPressedState;
            private int mOriginalWindowAttachCount;
            private float mX;
            private float mY;

            private CheckForSpanLongPress() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.mOriginalPressedState != PcNoteRichEditor.this.isPressed() || PcNoteRichEditor.this.getParent() == null) {
                    return;
                }
                HapticFeedbackTool.getInstance().performHapticLongPress(PcNoteRichEditor.this.getView());
                this.mActiveSpan.onLongClick((int) this.mX, (int) this.mY);
                RightClickableSpanDetector.this.mActiveSpan = null;
                ExternalTextWatcher externalTextWatcher = PcNoteRichEditor.this.getExternalTextWatcher();
                if (externalTextWatcher != null) {
                    if (PcNoteRichEditor.this.isCursorVisible()) {
                        externalTextWatcher.afterTextActuallyChanged(PcNoteRichEditor.this.getEditableText());
                    } else if (RightClickableSpanDetector.this.mClass == CheckboxSpan.class) {
                        externalTextWatcher.afterCheckBoxChanged();
                    }
                }
                RightClickableSpanDetector.this.hasPerformedLongPress = true;
            }

            public void setAnchor(T t, float f, float f2) {
                this.mActiveSpan = t;
                this.mX = f;
                this.mY = f2;
            }

            public void rememberPressedState() {
                this.mOriginalPressedState = PcNoteRichEditor.this.isPressed();
            }
        }
    }
}
