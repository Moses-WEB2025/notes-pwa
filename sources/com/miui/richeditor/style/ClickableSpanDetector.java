package com.miui.richeditor.style;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.notes.richeditor.style.MindEntrySpan;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.ClickableElement;

/* JADX INFO: loaded from: classes4.dex */
public class ClickableSpanDetector<T extends ClickableElement> {
    private long dur;
    private boolean hasPerformedLongPress;
    private float lastDownX;
    private float lastDownY;
    private RichEditTextView mEditor;
    private long mLastClickTime;
    private ViewConfiguration mViewConfiguration;
    private T nActiveSpan;
    private Class<T> nClass;
    private MotionEvent nDownEvent;
    private CheckForSpanLongPress pendingCheckForLongPress;
    private long start = 0;

    public int getVirtualViewId(float f, float f2) {
        return Integer.MIN_VALUE;
    }

    public ClickableSpanDetector(RichEditTextView richEditTextView, Class<T> cls) {
        this.mEditor = richEditTextView;
        this.mViewConfiguration = ViewConfiguration.get(richEditTextView.getContext());
        this.nClass = cls;
    }

    public boolean handleMotionEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.dur = 0L;
            this.lastDownX = motionEvent.getX();
            this.lastDownY = motionEvent.getY();
            MindEntrySpan mindEntrySpan = (T) getActiveSpan(motionEvent);
            this.nActiveSpan = mindEntrySpan;
            if (mindEntrySpan != null && (!(mindEntrySpan instanceof MindEntrySpan) || mindEntrySpan.getIsTouchIn())) {
                this.nActiveSpan.setPressed(true);
                this.nDownEvent = MotionEvent.obtain(motionEvent);
                this.start = System.currentTimeMillis();
                checkForLongClick(this.nActiveSpan, ViewConfiguration.getLongPressTimeout(), this.lastDownX, this.lastDownY);
                return true;
            }
        } else if (actionMasked == 1) {
            removeLongPressCallback();
            if (this.nActiveSpan != null && Math.abs(System.currentTimeMillis() - this.mLastClickTime) > 500) {
                this.mLastClickTime = System.currentTimeMillis();
                isTouchPointIn(this.nActiveSpan, motionEvent);
                if (isTouchIn(this.nActiveSpan, motionEvent)) {
                    this.nActiveSpan.setPressed(false);
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.start;
                    this.dur = jCurrentTimeMillis;
                    if (jCurrentTimeMillis < ViewConfiguration.getLongPressTimeout()) {
                        this.nActiveSpan.onClick((int) motionEvent.getX(), (int) motionEvent.getY());
                    } else if (Math.abs(this.lastDownX - motionEvent.getX()) < this.mViewConfiguration.getScaledDoubleTapSlop()) {
                        Math.abs(this.lastDownY - motionEvent.getY());
                        this.mViewConfiguration.getScaledDoubleTapSlop();
                    }
                    this.nActiveSpan = null;
                    ExternalTextWatcher externalTextWatcher = this.mEditor.getExternalTextWatcher();
                    if (externalTextWatcher != null) {
                        if (this.mEditor.isCursorVisible()) {
                            externalTextWatcher.afterTextActuallyChanged(this.mEditor.getEditableText());
                        } else if (this.nClass == CheckboxSpan.class) {
                            externalTextWatcher.afterCheckBoxChanged();
                        }
                    }
                }
                return true;
            }
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                removeLongPressCallback();
                T t = this.nActiveSpan;
                if (t != null) {
                    isTouchPointIn(t, motionEvent);
                    this.nActiveSpan.setPressed(false);
                    this.nActiveSpan = null;
                    return true;
                }
            } else if (actionMasked == 5) {
                removeLongPressCallback();
                T t2 = this.nActiveSpan;
                if (t2 != null) {
                    t2.setPressed(false);
                    this.nActiveSpan = null;
                    this.mEditor.dispatchTouchEvent(this.nDownEvent);
                }
            }
        } else if (this.nActiveSpan != null) {
            if (Math.abs(this.nDownEvent.getX() - motionEvent.getX()) > this.mViewConfiguration.getScaledTouchSlop() || Math.abs(this.nDownEvent.getY() - motionEvent.getY()) > this.mViewConfiguration.getScaledTouchSlop()) {
                removeLongPressCallback();
                T t3 = this.nActiveSpan;
                if (t3 instanceof AudioSpan) {
                    isTouchPointIn(t3, motionEvent);
                    return true;
                }
            }
            if (Math.abs(this.nDownEvent.getX() - motionEvent.getX()) <= this.mViewConfiguration.getScaledTouchSlop() && Math.abs(this.nDownEvent.getY() - motionEvent.getY()) <= this.mViewConfiguration.getScaledTouchSlop() && isTouchIn(this.nActiveSpan, motionEvent)) {
                return true;
            }
            this.nActiveSpan.setPressed(false);
            this.nActiveSpan = null;
            this.mEditor.dispatchTouchEvent(this.nDownEvent);
        }
        MotionEvent motionEvent2 = this.nDownEvent;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
            this.nDownEvent = null;
        }
        this.nActiveSpan = null;
        return false;
    }

    private T getActiveSpan(MotionEvent motionEvent) {
        Editable text = this.mEditor.getText();
        int i = 0;
        if (TextUtils.isEmpty(text) && !TextUtils.isEmpty(this.mEditor.getHint())) {
            SpannableString spannableString = new SpannableString(this.mEditor.getHint());
            ClickableElement[] clickableElementArr = (ClickableElement[]) spannableString.getSpans(0, spannableString.length(), this.nClass);
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
        ClickableElement[] clickableElementArr2 = (ClickableElement[]) text.getSpans(0, text.length(), this.nClass);
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
        this.mEditor.postDelayed(this.pendingCheckForLongPress, j);
    }

    private void removeLongPressCallback() {
        CheckForSpanLongPress checkForSpanLongPress;
        if (this.hasPerformedLongPress || (checkForSpanLongPress = this.pendingCheckForLongPress) == null) {
            return;
        }
        this.mEditor.removeCallbacks(checkForSpanLongPress);
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
            if (this.mOriginalPressedState != ClickableSpanDetector.this.mEditor.isPressed() || ClickableSpanDetector.this.mEditor.getParent() == null) {
                return;
            }
            HapticFeedbackTool.getInstance().performHapticLongPress(ClickableSpanDetector.this.mEditor);
            this.mActiveSpan.onLongClick((int) this.mX, (int) this.mY);
            ClickableSpanDetector.this.nActiveSpan = null;
            ExternalTextWatcher externalTextWatcher = ClickableSpanDetector.this.mEditor.getExternalTextWatcher();
            if (externalTextWatcher != null) {
                if (ClickableSpanDetector.this.mEditor.isCursorVisible()) {
                    externalTextWatcher.afterTextActuallyChanged(ClickableSpanDetector.this.mEditor.getEditableText());
                } else if (ClickableSpanDetector.this.nClass == CheckboxSpan.class) {
                    externalTextWatcher.afterCheckBoxChanged();
                }
            }
            ClickableSpanDetector.this.hasPerformedLongPress = true;
        }

        public void setAnchor(T t, float f, float f2) {
            this.mActiveSpan = t;
            this.mX = f;
            this.mY = f2;
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = ClickableSpanDetector.this.mEditor.isPressed();
        }
    }
}
