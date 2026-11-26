package com.miui.richeditor;

import android.graphics.Rect;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.ClickableElement;
import com.miui.richeditor.style.ClickableSpanDetector;
import com.miui.richeditor.style.ContactSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.util.SafeSpannableStringBuilder;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class ClickableSpanDetectorManager {
    private ClickableSpanDetector<BaseAudioSpan> mClickAudioDetector;
    private ClickableSpanDetector<CheckboxSpan> mClickCheckableDetector;
    private ClickableSpanDetector<ContactSpan> mClickContactDetector;
    private ClickableSpanDetector<EditorHintEntrySpan> mClickHintDetector;
    private ClickableSpanDetector<NormalImageSpan> mClickImageDetector;
    private ClickableSpanDetector<LinkCardSpan> mClickLinkCardDetector;
    private RichEditTextView mEditor;

    ClickableSpanDetectorManager(RichEditTextView richEditTextView) {
        this.mEditor = richEditTextView;
        registerDetector();
    }

    void registerDetector() {
        this.mClickHintDetector = new ClickableSpanDetector<>(this.mEditor, EditorHintEntrySpan.class);
        this.mClickImageDetector = new ClickableSpanDetector<>(this.mEditor, NormalImageSpan.class);
        this.mClickAudioDetector = new ClickableSpanDetector<>(this.mEditor, BaseAudioSpan.class);
        this.mClickContactDetector = new ClickableSpanDetector<>(this.mEditor, ContactSpan.class);
        this.mClickCheckableDetector = new ClickableSpanDetector<>(this.mEditor, CheckboxSpan.class);
        this.mClickLinkCardDetector = new ClickableSpanDetector<>(this.mEditor, LinkCardSpan.class);
    }

    boolean hasHandleMotionEvent(MotionEvent motionEvent) {
        return this.mClickImageDetector.handleMotionEvent(motionEvent) || this.mClickAudioDetector.handleMotionEvent(motionEvent) || this.mClickCheckableDetector.handleMotionEvent(motionEvent) || this.mClickLinkCardDetector.handleMotionEvent(motionEvent) || this.mClickHintDetector.handleMotionEvent(motionEvent) || this.mClickContactDetector.handleMotionEvent(motionEvent);
    }

    int getVirtualViewId(float f, float f2) {
        SpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder(this.mEditor.getText());
        if (TextUtils.isEmpty(safeSpannableStringBuilder) && !TextUtils.isEmpty(this.mEditor.getHint())) {
            safeSpannableStringBuilder = new SpannableStringBuilder(this.mEditor.getHint());
        }
        ClickableElement[] clickableElementArr = (ClickableElement[]) safeSpannableStringBuilder.getSpans(0, safeSpannableStringBuilder.length(), ClickableElement.class);
        for (int i = 0; i < clickableElementArr.length; i++) {
            if (clickableElementArr[i].isTouchIn((int) f, (int) f2)) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    void getVisibleVirtualViews(List<Integer> list) {
        SpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder(this.mEditor.getText());
        if (TextUtils.isEmpty(safeSpannableStringBuilder) && !TextUtils.isEmpty(this.mEditor.getHint())) {
            safeSpannableStringBuilder = new SpannableStringBuilder(this.mEditor.getHint());
        }
        ClickableElement[] clickableElementArr = (ClickableElement[]) safeSpannableStringBuilder.getSpans(0, safeSpannableStringBuilder.length(), ClickableElement.class);
        for (int i = 0; i < clickableElementArr.length; i++) {
            list.add(Integer.valueOf(i));
        }
    }

    RichEditSpanAccessibilityInfo getAccessibilityInfoByVid(int i) {
        RichEditSpanAccessibilityInfo richEditSpanAccessibilityInfo = new RichEditSpanAccessibilityInfo();
        SpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder(this.mEditor.getText());
        if (TextUtils.isEmpty(safeSpannableStringBuilder) && !TextUtils.isEmpty(this.mEditor.getHint())) {
            safeSpannableStringBuilder = new SpannableStringBuilder(this.mEditor.getHint());
        }
        this.mEditor.refreshVisibleRect();
        ClickableElement[] clickableElementArr = (ClickableElement[]) safeSpannableStringBuilder.getSpans(0, safeSpannableStringBuilder.length(), ClickableElement.class);
        if (clickableElementArr.length > 0 && clickableElementArr.length > i) {
            ClickableElement clickableElement = clickableElementArr[i];
            richEditSpanAccessibilityInfo.bounds.set(clickableElement.getBoundsInParent());
            richEditSpanAccessibilityInfo.contentDescription = clickableElement.getContentDescription();
        }
        return richEditSpanAccessibilityInfo;
    }

    Rect getBoundsForIndex(int i) {
        SpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder(this.mEditor.getText());
        if (TextUtils.isEmpty(safeSpannableStringBuilder) && !TextUtils.isEmpty(this.mEditor.getHint())) {
            safeSpannableStringBuilder = new SpannableStringBuilder(this.mEditor.getHint());
        }
        ClickableElement[] clickableElementArr = (ClickableElement[]) safeSpannableStringBuilder.getSpans(0, safeSpannableStringBuilder.length(), ClickableElement.class);
        if (clickableElementArr.length > 0) {
            return clickableElementArr[i].getBoundsInParent();
        }
        return new Rect();
    }
}
