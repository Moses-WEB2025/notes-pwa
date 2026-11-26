package com.miui.pc.feature.todo;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.miui.notes.R;
import com.miui.pc.view.datepopupview.view.DatePopupWindow;
import com.miui.todo.data.bean.RemindTimeConfig;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoFastCreateController {
    private EditText mEditor;
    private View mFastCreateRegion;
    private ImageView mRemindImage;
    private View mRootView;
    private int rootViewHeight;
    private SaveListener mListener = null;
    private RemindTimeConfig mRemindTimeConfig = null;
    private final TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            PcTodoFastCreateController.this.mRootView.setVisibility(8);
        }
    };

    interface SaveListener {
        void onSave(String str, RemindTimeConfig remindTimeConfig);
    }

    public PcTodoFastCreateController(View view) {
        this.rootViewHeight = 0;
        this.mRootView = view;
        view.measure(Integer.MIN_VALUE, 0);
        this.rootViewHeight = this.mRootView.getMeasuredHeight();
        initView(this.mRootView);
    }

    private void initView(View view) {
        this.mEditor = (EditText) view.findViewById(R.id.todo_fast_editor);
        this.mRemindImage = (ImageView) view.findViewById(R.id.iv_remind);
        this.mFastCreateRegion = this.mRootView.findViewById(R.id.todo_fast_create_group);
        this.mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view2, boolean z) {
                PcTodoFastCreateController.this.mFastCreateRegion.findViewById(R.id.todo_fast_create_bg).setSelected(z);
            }
        });
        this.mEditor.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.3
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view2, int i, KeyEvent keyEvent) {
                if (i != 66 || keyEvent.getAction() != 0) {
                    return false;
                }
                if (PcTodoFastCreateController.this.mEditor.getText().length() > 0 && PcTodoFastCreateController.this.mEditor.getText().subSequence(PcTodoFastCreateController.this.mEditor.getText().length() - 1, PcTodoFastCreateController.this.mEditor.getText().length()).toString().equals(StringUtils.LF)) {
                    PcTodoFastCreateController.this.mEditor.getText().delete(PcTodoFastCreateController.this.mEditor.getText().length() - 1, PcTodoFastCreateController.this.mEditor.getText().length());
                }
                if (PcTodoFastCreateController.this.mEditor.getText().length() > 0) {
                    PcTodoFastCreateController.this.saveData();
                    PcTodoFastCreateController.this.clearEditorFocus();
                    PcTodoFastCreateController.this.mEditor.getText().clear();
                    PcTodoFastCreateController.this.mRemindImage.setEnabled(false);
                }
                return true;
            }
        });
        this.mEditor.addTextChangedListener(new TextWatcher() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PcTodoFastCreateController.this.mRemindImage.setEnabled(PcTodoFastCreateController.this.mEditor.getText().length() > 0);
            }
        });
        this.mRemindImage.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PcTodoFastCreateController.this.showDateConfigDialog();
            }
        });
        this.mRemindImage.setEnabled(false);
    }

    private static int makeDropDownMeasureSpec(int i) {
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), i == -2 ? 0 : 1073741824);
    }

    public void setSaveListener(SaveListener saveListener) {
        this.mListener = saveListener;
    }

    public void clearEditorFocus() {
        this.mEditor.clearFocus();
    }

    public void saveData() {
        SaveListener saveListener = this.mListener;
        if (saveListener != null) {
            saveListener.onSave(this.mEditor.getText().toString(), this.mRemindTimeConfig);
            this.mRemindTimeConfig = null;
        }
    }

    public void show() {
        this.mRootView.setVisibility(0);
        Folme.useAt(this.mRootView).state().to(ViewProperty.HEIGHT, Integer.valueOf(this.rootViewHeight));
    }

    public void hide() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.HEIGHT, 0, new AnimConfig().addListeners(this.mDismissListener));
    }

    public void showDateConfigDialog() {
        DatePopupWindow datePopupWindow = new DatePopupWindow(this.mRootView.getContext(), System.currentTimeMillis() + 60000, 0, new PcTodoListFragment.OnTimeSetListener() { // from class: com.miui.pc.feature.todo.PcTodoFastCreateController.6
            @Override // com.miui.pc.feature.todo.PcTodoListFragment.OnTimeSetListener
            public void onCancel() {
            }

            @Override // com.miui.pc.feature.todo.PcTodoListFragment.OnTimeSetListener
            public void onTimeSet(RemindTimeConfig remindTimeConfig) {
                PcTodoFastCreateController.this.mRemindTimeConfig = remindTimeConfig;
                if (TextUtils.isEmpty(PcTodoFastCreateController.this.mEditor.getText())) {
                    return;
                }
                PcTodoFastCreateController.this.saveData();
                PcTodoFastCreateController.this.clearEditorFocus();
                PcTodoFastCreateController.this.mEditor.getText().clear();
                PcTodoFastCreateController.this.mRemindImage.setEnabled(false);
            }
        });
        datePopupWindow.getContentView().measure(makeDropDownMeasureSpec(datePopupWindow.getWidth()), makeDropDownMeasureSpec(datePopupWindow.getHeight()));
        datePopupWindow.showAsDropDown(this.mRemindImage, -(datePopupWindow.getContentView().getMeasuredWidth() - 65), -(this.mFastCreateRegion.getHeight() + 16));
    }
}
