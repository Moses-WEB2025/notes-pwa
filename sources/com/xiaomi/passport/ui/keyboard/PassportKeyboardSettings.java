package com.xiaomi.passport.ui.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import com.xiaomi.passport.ui.settings.utils.PopNoRecordProxy;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class PassportKeyboardSettings {
    private static volatile AbsPassportKeyboard sPasswordKeyboard;

    public interface KeyboardController {
        boolean handlePressCode(Activity activity, EditText editText, int i);

        void init(Activity activity);
    }

    public static void setPasswordKeyboard(AbsPassportKeyboard absPassportKeyboard) {
        sPasswordKeyboard = absPassportKeyboard;
    }

    public static AbsPassportKeyboard getPasswordKeyboard() {
        return sPasswordKeyboard;
    }

    public static abstract class AbsPassportKeyboard extends KeyboardView implements KeyboardView.OnKeyboardActionListener, KeyboardController {
        private PopupWindow mKeyboardWindow;
        private Activity mTargetActivity;
        private EditText mTargetEditText;

        @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void onPress(int i) {
        }

        @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void onRelease(int i) {
        }

        @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void onText(CharSequence charSequence) {
        }

        @Override // android.inputmethodservice.KeyboardView, android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void swipeDown() {
        }

        @Override // android.inputmethodservice.KeyboardView, android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void swipeLeft() {
        }

        @Override // android.inputmethodservice.KeyboardView, android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void swipeRight() {
        }

        @Override // android.inputmethodservice.KeyboardView, android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void swipeUp() {
        }

        public AbsPassportKeyboard(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public AbsPassportKeyboard(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public void attachToActivity(Activity activity) {
            this.mTargetActivity = activity;
            setOnKeyboardActionListener(this);
            init(activity);
        }

        public void detachFromActivity() {
            this.mTargetActivity = null;
            this.mKeyboardWindow = null;
        }

        public void target(EditText editText) {
            this.mTargetEditText = editText;
            this.mTargetActivity.getWindow().setSoftInputMode(3);
            try {
                Method method = EditText.class.getMethod("setShowSoftInputOnFocus", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.passport.ui.keyboard.PassportKeyboardSettings.AbsPassportKeyboard.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 1) {
                        return false;
                    }
                    AbsPassportKeyboard.this.show(view);
                    return false;
                }
            });
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.xiaomi.passport.ui.keyboard.PassportKeyboardSettings.AbsPassportKeyboard.2
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view, boolean z) {
                    if (!z) {
                        AbsPassportKeyboard.this.dismiss();
                    } else {
                        AbsPassportKeyboard.this.show(view);
                    }
                }
            });
        }

        public void untarget() {
            this.mTargetEditText = null;
        }

        public void show(View view) {
            this.mTargetActivity.getWindow().setSoftInputMode(2);
            ((InputMethodManager) this.mTargetActivity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            PopupWindow popupWindow = this.mKeyboardWindow;
            if (popupWindow == null || !popupWindow.isShowing()) {
                PopupWindow popupWindow2 = new PopupWindow(this.mTargetActivity.getWindow().getDecorView(), -1, -2, true);
                this.mKeyboardWindow = popupWindow2;
                popupWindow2.setContentView(PassportKeyboardSettings.sPasswordKeyboard);
                PopNoRecordProxy.instance().noScreenRecord(this.mKeyboardWindow);
                this.mKeyboardWindow.setOutsideTouchable(false);
                this.mKeyboardWindow.setFocusable(false);
                this.mKeyboardWindow.showAtLocation(this.mTargetActivity.getWindow().getDecorView(), 80, 0, 0);
            }
        }

        public void dismiss() {
            PopupWindow popupWindow = this.mKeyboardWindow;
            if (popupWindow == null || !popupWindow.isShowing()) {
                return;
            }
            this.mKeyboardWindow.dismiss();
        }

        @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
        public void onKey(int i, int[] iArr) {
            if (handlePressCode(this.mTargetActivity, this.mTargetEditText, i)) {
                return;
            }
            Editable text = this.mTargetEditText.getText();
            int selectionStart = this.mTargetEditText.getSelectionStart();
            if (i == -5) {
                if (this.mTargetEditText.hasFocus() && !TextUtils.isEmpty(text) && selectionStart > 0) {
                    text.delete(selectionStart - 1, selectionStart);
                }
            } else if (this.mTargetEditText.hasFocus()) {
                text.insert(selectionStart, Character.toString((char) i));
            }
            PassportKeyboardSettings.sPasswordKeyboard.postInvalidate();
        }
    }
}
