package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.AppCompatEditText;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VerifyCodeEditText extends AppCompatEditText {
    private static final String TAG = "VerifyCodeEditText";
    private static final int WEIGHT_VERIFY_CODE_HEIGHT = 15;
    private static final int WEIGHT_VERIFY_CODE_MARGIN = 2;
    private static final int WEIGHT_VERIFY_CODE_WIDTH = 12;
    private InputCompletedListener mInputCompletedListener;
    private final Runnable mShowInputMethodRunnable;
    private final List<Character> mVerifyCodeInputCharList;
    private int mVerifyCodeLength;
    private int mVerifyCodeTextBgColor;
    private Paint mVerifyCodeTextBgFillPaint;
    private float mVerifyCodeTextBgRadius;
    private Paint mVerifyCodeTextBgStrokePaint;
    private int mVerifyCodeTextColor;
    private float mVerifyCodeTextHeight;
    private float mVerifyCodeTextMargin;
    private Paint mVerifyCodeTextPaint;
    private float mVerifyCodeTextSize;
    private int mVerifyCodeTextStrokeColor;
    private float mVerifyCodeTextStrokeWidth;
    private float mVerifyCodeTextWidth;

    public interface InputCompletedListener {
        void onInputCompleted(String str);
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public VerifyCodeEditText(Context context) {
        this(context, null);
    }

    public VerifyCodeEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerifyCodeEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVerifyCodeLength = 6;
        this.mVerifyCodeInputCharList = new ArrayList();
        this.mShowInputMethodRunnable = new Runnable() { // from class: com.xiaomi.passport.ui.view.VerifyCodeEditText.2
            @Override // java.lang.Runnable
            public void run() {
                VerifyCodeEditText.this.requestFocus();
                ((InputMethodManager) VerifyCodeEditText.this.getContext().getSystemService("input_method")).showSoftInput(VerifyCodeEditText.this, 1);
            }
        };
        setCursorVisible(false);
        setFocusableInTouchMode(true);
        setFilters(new InputFilter[]{new InputFilter() { // from class: com.xiaomi.passport.ui.view.VerifyCodeEditText.1
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
                if (charSequence.length() <= 0) {
                    return "";
                }
                for (int i6 = 0; i6 < charSequence.length(); i6++) {
                    char cCharAt = charSequence.charAt(i6);
                    if (cCharAt >= '0' && cCharAt <= '9' && VerifyCodeEditText.this.mVerifyCodeInputCharList.size() < VerifyCodeEditText.this.mVerifyCodeLength) {
                        VerifyCodeEditText.this.mVerifyCodeInputCharList.add(Character.valueOf(cCharAt));
                        VerifyCodeEditText.this.invalidate();
                        if (VerifyCodeEditText.this.mVerifyCodeInputCharList.size() == VerifyCodeEditText.this.mVerifyCodeLength) {
                            VerifyCodeEditText.this.onVerifyCodeInputCompleted();
                        }
                    }
                }
                return "";
            }
        }});
        initDrawingProperties(attributeSet);
        showInputMethodWhenNoInputText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVerifyCodeInputCompleted() {
        if (this.mInputCompletedListener != null) {
            StringBuilder sb = new StringBuilder();
            Iterator<Character> it = this.mVerifyCodeInputCharList.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
            }
            this.mInputCompletedListener.onInputCompleted(sb.toString());
        }
    }

    public void setInputCompleteListener(InputCompletedListener inputCompletedListener) {
        this.mInputCompletedListener = inputCompletedListener;
    }

    public void setVerifyCodeLength(int i) {
        this.mVerifyCodeLength = i;
        this.mVerifyCodeTextWidth = -1.0f;
        this.mVerifyCodeTextHeight = -1.0f;
        this.mVerifyCodeTextMargin = -1.0f;
        this.mVerifyCodeTextSize = 0.0f;
        invalidate();
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 67) {
            return false;
        }
        if (this.mVerifyCodeInputCharList.size() > 0) {
            List<Character> list = this.mVerifyCodeInputCharList;
            list.remove(list.size() - 1);
        }
        AccountLogger.log(TAG, "click delete key");
        invalidate();
        return true;
    }

    private void initDrawingProperties(AttributeSet attributeSet) {
        Resources resources = getResources();
        this.mVerifyCodeTextColor = resources.getColor(R.color.passport_verify_code_text_color);
        this.mVerifyCodeTextBgColor = resources.getColor(R.color.passport_verify_code_bg_color);
        this.mVerifyCodeTextStrokeColor = resources.getColor(R.color.passport_verify_code_focus_color);
        this.mVerifyCodeTextStrokeWidth = resources.getDimension(R.dimen.passport_edit_text_stroke_width);
        this.mVerifyCodeTextBgRadius = resources.getDimension(R.dimen.passport_edit_text_bg_radius);
    }

    private void initRuntimeDrawingProperties(int i, int i2) {
        int i3 = this.mVerifyCodeLength;
        float f = (i * 1.0f) / ((i3 * 12) + ((i3 - 1) * 2));
        this.mVerifyCodeTextWidth = 12.0f * f;
        this.mVerifyCodeTextMargin = f * 2.0f;
        this.mVerifyCodeTextHeight = i2;
        this.mVerifyCodeTextSize = Math.min(i / 2, i2 / 3);
        Paint paint = new Paint();
        this.mVerifyCodeTextPaint = paint;
        paint.setAntiAlias(true);
        this.mVerifyCodeTextPaint.setColor(this.mVerifyCodeTextColor);
        this.mVerifyCodeTextPaint.setStyle(Paint.Style.FILL);
        this.mVerifyCodeTextPaint.setTextSize(this.mVerifyCodeTextSize);
        this.mVerifyCodeTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mVerifyCodeTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        Paint paint2 = new Paint();
        this.mVerifyCodeTextBgFillPaint = paint2;
        paint2.setAntiAlias(true);
        this.mVerifyCodeTextBgFillPaint.setColor(this.mVerifyCodeTextBgColor);
        this.mVerifyCodeTextBgFillPaint.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint();
        this.mVerifyCodeTextBgStrokePaint = paint3;
        paint3.setAntiAlias(true);
        this.mVerifyCodeTextBgStrokePaint.setColor(this.mVerifyCodeTextStrokeColor);
        this.mVerifyCodeTextBgStrokePaint.setStrokeWidth(this.mVerifyCodeTextStrokeWidth);
        this.mVerifyCodeTextBgStrokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mVerifyCodeTextWidth <= 0.0f) {
            initRuntimeDrawingProperties(getWidth(), getHeight());
        }
        AccountLogger.log(TAG, "onDraw: " + this.mVerifyCodeInputCharList.size());
        boolean z = getLayoutDirection() == 1;
        if (z) {
            canvas.save();
            canvas.scale(-1.0f, 1.0f, getWidth() / 2.0f, getHeight() / 2.0f);
        }
        for (int i = 0; i < this.mVerifyCodeLength; i++) {
            float f = i;
            float f2 = this.mVerifyCodeTextWidth;
            float f3 = this.mVerifyCodeTextMargin;
            float f4 = this.mVerifyCodeTextHeight;
            float f5 = this.mVerifyCodeTextBgRadius;
            canvas.drawRoundRect((f2 + f3) * f, 0.0f, (f * (f3 + f2)) + f2, f4, f5, f5, this.mVerifyCodeTextBgFillPaint);
            if (i < this.mVerifyCodeInputCharList.size()) {
                float f6 = this.mVerifyCodeTextWidth;
                float f7 = (i * (this.mVerifyCodeTextMargin + f6)) + (f6 / 2.0f);
                float f8 = this.mVerifyCodeTextHeight;
                float f9 = f8 - ((f8 - this.mVerifyCodeTextSize) / 2.0f);
                if (z) {
                    canvas.save();
                    canvas.scale(-1.0f, 1.0f, f7, f9);
                }
                canvas.drawText("" + this.mVerifyCodeInputCharList.get(i), f7, f9, this.mVerifyCodeTextPaint);
                if (z) {
                    canvas.restore();
                }
            }
            if (i == this.mVerifyCodeInputCharList.size()) {
                float f10 = i;
                float f11 = this.mVerifyCodeTextWidth;
                float f12 = this.mVerifyCodeTextMargin;
                float f13 = this.mVerifyCodeTextStrokeWidth;
                float f14 = ((f11 + f12) * f10) + (f13 / 2.0f);
                float f15 = f13 / 2.0f;
                float f16 = ((f10 * (f12 + f11)) + f11) - (f13 / 2.0f);
                float f17 = this.mVerifyCodeTextHeight - (f13 / 2.0f);
                float f18 = this.mVerifyCodeTextBgRadius;
                canvas.drawRoundRect(f14, f15, f16, f17, f18, f18, this.mVerifyCodeTextBgStrokePaint);
            }
        }
        if (z) {
            canvas.restore();
        }
    }

    private void showInputMethodWhenNoInputText() {
        postDelayed(this.mShowInputMethodRunnable, 500L);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.mShowInputMethodRunnable);
        super.onDetachedFromWindow();
    }

    public void animateErrorAndReset() {
        this.mVerifyCodeInputCharList.clear();
        invalidate();
        showInputMethodWhenNoInputText();
    }

    public void setSmsVerifyCodeIfNeeded(String str) {
        if (this.mVerifyCodeInputCharList.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                this.mVerifyCodeInputCharList.add(Character.valueOf(str.charAt(i)));
                if (this.mVerifyCodeInputCharList.size() == this.mVerifyCodeLength) {
                    onVerifyCodeInputCompleted();
                    return;
                }
            }
        }
    }
}
