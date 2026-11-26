package com.miui.notes.editor;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.richeditor.style.MindEntrySpan;
import com.miui.richeditor.CommonStyleFactory;
import com.miui.richeditor.MindDrawable;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.theme.Theme;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteRichEditor extends RichEditTextView implements INoteRichEditor {
    public static final boolean IS_SUPER_LITE = LiteUtils.isSuperLite();
    protected boolean mEnableToggleAlignment;
    protected long mFolderId;
    protected int mHintEntryHeight;
    protected int mHintEntryWidth;
    protected OnKeyDownAndShortCutListener mOutKeyListener;

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void done() {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void pasteMarkdown(String str, String str2) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setCount(long j) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setListBullet() {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public /* bridge */ /* synthetic */ View getView() {
        return super.getView();
    }

    public NoteRichEditor(Context context) {
        super(context);
        this.mEnableToggleAlignment = false;
        this.mHintEntryHeight = 0;
        this.mHintEntryWidth = 0;
        this.mFolderId = -1L;
    }

    public NoteRichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public NoteRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnableToggleAlignment = false;
        this.mHintEntryHeight = 0;
        this.mHintEntryWidth = 0;
        this.mFolderId = -1L;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IAudioEditorContext
    public String getAudioFileDir() {
        return AudioUtils.NOTE_MP3_DIR_NAME;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initStyleFactory() {
        this.mStyleFactory = new CommonStyleFactory(getContext(), this);
    }

    @Override // com.miui.richeditor.RichEditTextView, android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Logger.INSTANCE.d("KbTest", "onKeyDown " + i + StringUtils.SPACE + keyEvent.toString());
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mOutKeyListener;
        boolean zOnKeyDown = onKeyDownAndShortCutListener != null ? onKeyDownAndShortCutListener.onKeyDown(i, keyEvent) : false;
        if (!isEnabled()) {
            return false;
        }
        if ((i != 67 && i != 112) || this.mSelectImageSpan == null || isReadOnlyMode()) {
            return zOnKeyDown ? zOnKeyDown : super.onKeyDown(i, keyEvent);
        }
        this.mSelectImageSpan.onSelected(false);
        deleteImageSpan(this.mSelectImageSpan, false);
        this.mSelectImageSpan = null;
        return true;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setActivity(Activity activity) {
        super.setActivity(activity);
    }

    @Override // android.widget.EditText, android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Logger.INSTANCE.d("KbTest", "onKeyShortcut " + i + StringUtils.SPACE + keyEvent.toString());
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener = this.mOutKeyListener;
        boolean zOnKeyShortcut = onKeyDownAndShortCutListener != null ? onKeyDownAndShortCutListener.onKeyShortcut(i, keyEvent) : false;
        if (zOnKeyShortcut) {
            return zOnKeyShortcut;
        }
        boolean zOnKeyShortcut2 = super.onKeyShortcut(i, keyEvent);
        if (zOnKeyShortcut2 || !keyEvent.hasModifiers(4096)) {
            OnKeyDownAndShortCutListener onKeyDownAndShortCutListener2 = this.mOutKeyListener;
            if (onKeyDownAndShortCutListener2 != null) {
                onKeyDownAndShortCutListener2.onKeyShortcut(i, keyEvent);
            }
        } else {
            if (i == 30) {
                setFontBold();
                return true;
            }
            if (i == 33) {
                setAlignCenter();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
                return true;
            }
            if (i == 46) {
                setAlignRight();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
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
            if (i == 39) {
                setCheckbox();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
                return true;
            }
            if (i == 40) {
                setAlignNormal();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
                return true;
            }
            if (i == 71) {
                reduceFontSize();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
                return true;
            }
            if (i != 72) {
                switch (i) {
                    case 35:
                        setEditorBullet();
                        onSelectionChanged(getSelectionStart(), getSelectionEnd());
                        return true;
                    case 36:
                        setBgHighLight();
                        return true;
                    case 37:
                        setFontItalic();
                        return true;
                    default:
                        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener3 = this.mOutKeyListener;
                        if (onKeyDownAndShortCutListener3 != null) {
                            onKeyDownAndShortCutListener3.onKeyShortcut(i, keyEvent);
                            return zOnKeyShortcut2;
                        }
                        break;
                }
            } else {
                increaseFontSize();
                onSelectionChanged(getSelectionStart(), getSelectionEnd());
                return true;
            }
        }
        return zOnKeyShortcut2;
    }

    protected MindDrawable createMindEntryDrawable(int i, int i2) {
        return new MindDrawable(NoteApp.getInstance(), i, i2);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public SpannableString createHintEntrySpanText() {
        String string;
        int id;
        int i;
        boolean z = IS_SUPER_LITE;
        if (z) {
            string = getResources().getString(com.miui.notes.R.string.note_write_hint);
        } else {
            string = getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_hint_newversion);
        }
        SpannableString spannableString = new SpannableString(string + StringUtils.SPACE);
        int dimension = (int) getContext().getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_margin_start);
        float fMeasureText = getPaint().measureText(string) + dimension;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_text_size));
        float fMeasureText2 = textPaint.measureText(getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_icon_text_newversion));
        if (getEditorTheme() != null) {
            id = getEditorTheme().getId();
            i = getEditorTheme().getForegroundPadding(getContext()).left;
        } else {
            id = 0;
            i = 0;
        }
        MindDrawable mindDrawableCreateMindEntryDrawable = createMindEntryDrawable(id, (int) fMeasureText2);
        mindDrawableCreateMindEntryDrawable.setBounds(dimension, -((int) getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_padding_top)), mindDrawableCreateMindEntryDrawable.getIntrinsicWidth(), mindDrawableCreateMindEntryDrawable.getIntrinsicHeight());
        mindDrawableCreateMindEntryDrawable.setBounds(0, 0, mindDrawableCreateMindEntryDrawable.getIntrinsicWidth(), mindDrawableCreateMindEntryDrawable.getIntrinsicHeight());
        int bubbleWidth = mindDrawableCreateMindEntryDrawable.getBubbleWidth();
        int windowWidth = getWindowWidth() - i;
        int windowWidth2 = (getWindowWidth() - i) - bubbleWidth;
        MindEntrySpan mindEntrySpan = new MindEntrySpan(this, mindDrawableCreateMindEntryDrawable, 0, (int) fMeasureText, 0);
        float f = fMeasureText + i;
        if (f <= windowWidth2 || f > windowWidth) {
            float f2 = windowWidth;
            if (f > f2) {
                mindEntrySpan = new MindEntrySpan(this, mindDrawableCreateMindEntryDrawable, 0, (int) (f - f2), getLineHeight());
            }
        } else {
            mindEntrySpan = new MindEntrySpan(this, mindDrawableCreateMindEntryDrawable, 0, (int) getContext().getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_padding_start), getLineHeight());
        }
        this.mHintEntryWidth = bubbleWidth;
        this.mHintEntryHeight = mindDrawableCreateMindEntryDrawable.getIntrinsicHeight();
        if (!z) {
            spannableString.setSpan(mindEntrySpan, spannableString.length() - 1, spannableString.length(), 17);
        }
        return spannableString;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (getHint() != null) {
            getHint().toString();
        }
        if (accessibilityNodeInfo != null) {
            accessibilityNodeInfo.getText();
            if (!IS_SUPER_LITE) {
                accessibilityNodeInfo.setText(((Object) getHint()) + getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_icon_text_newversion));
            } else {
                accessibilityNodeInfo.setText(getHint());
            }
        }
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public int getHintSpanMarginStartByTheme(Theme theme) {
        this.mIsHintSpanTwoLines = false;
        float fMeasureText = getPaint().measureText(getResources().getString(com.miui.notes.R.string.note_mindnote_entrance_hint_newversion)) + ((int) getContext().getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_margin_start));
        int i = theme != null ? theme.getForegroundPadding(getContext()).left : 0;
        int hintEntryWidth = getHintEntryWidth();
        int windowWidth = getWindowWidth() - i;
        int windowWidth2 = (getWindowWidth() - i) - hintEntryWidth;
        float f = i + fMeasureText;
        if (f <= windowWidth2 || f > windowWidth) {
            float f2 = windowWidth;
            if (f > f2) {
                fMeasureText = (int) (f - f2);
                this.mIsHintSpanTwoLines = true;
            }
        } else {
            fMeasureText = (int) getContext().getResources().getDimension(com.miui.notes.R.dimen.mind_entrance_margin_start);
            this.mIsHintSpanTwoLines = true;
        }
        return (int) fMeasureText;
    }

    public int getHintEntryHeight() {
        return this.mHintEntryHeight;
    }

    public int getHintEntryWidth() {
        return this.mHintEntryWidth;
    }

    public void setOutKeyListener(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
        this.mOutKeyListener = onKeyDownAndShortCutListener;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setEnableToggleAlignment(boolean z) {
        this.mEnableToggleAlignment = z;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public String getTitle() {
        return this.mTitle;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setFolderId(long j) {
        this.mFolderId = j;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setData(String str, String str2, String str3, Boolean bool, ArrayList<String> arrayList) {
        updateContent(str, str3);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setEnableEdit(boolean z) {
        if (!z) {
            setTextIsSelectable(true);
        }
        setReadOnly(!z);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAddNewElement(int i) {
        CharSequence richTextData = getRichTextData();
        return richTextData == null || richTextData.length() + i <= 20000;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setSelectionToEnd() {
        if (getSpanEndAt(getText(), BaseImageSpan.class, length()) == null) {
            setSelection(length());
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean requestViewFocus() {
        return requestFocus();
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean requestViewFocus(int i) {
        return requestFocus(i);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setViewShowSoftInputOnFocus(boolean z) {
        setShowSoftInputOnFocus(z);
    }

    public boolean ismIsRemoveFocus() {
        return this.mIsRemoveFocus;
    }

    public void setmIsRemoveFocus(boolean z) {
        this.mIsRemoveFocus = z;
    }
}
