package com.miui.pc.feature.notes;

import android.content.Context;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.common.tool.CompatUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.NormalImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PcAnnotationEditController {
    private final String TAG;
    private FrameLayout mContentView;
    private Context mContext;
    private TextView mDone;
    private EditListener mEditListener;
    private EditText mEditText;
    private NormalImageSpan mEditingSpan;
    private boolean mIsShow;
    private int mMinHeight;
    private FrameLayout mRootView;
    private TextWatcher mTextWatcher;

    public interface EditListener {
        void onCompete(String str, boolean z);

        void onOverLength();
    }

    public PcAnnotationEditController(Context context, String str) {
        this.TAG = "PadAnnotationEditController";
        this.mDone = null;
        this.mMinHeight = 0;
        this.mIsShow = false;
        this.mTextWatcher = new TextWatcher() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.1
            final int maxLength = 100;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 100) {
                    if (PcAnnotationEditController.this.mEditListener != null) {
                        PcAnnotationEditController.this.mEditListener.onOverLength();
                    }
                    PcAnnotationEditController.this.mEditText.removeTextChangedListener(this);
                    PcAnnotationEditController.this.mEditText.setText(editable.subSequence(0, 100));
                    PcAnnotationEditController.this.mEditText.setSelection(PcAnnotationEditController.this.mEditText.getText().toString().length());
                    PcAnnotationEditController.this.mEditText.addTextChangedListener(PcAnnotationEditController.this.mTextWatcher);
                }
            }
        };
        if (TextUtils.isEmpty(str)) {
            this.mRootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pc_image_annotate_layout_new, (ViewGroup) null);
        } else {
            this.mRootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pc_image_annotate_layout_edit, (ViewGroup) null);
        }
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcAnnotationEditController.this.saveAndHide();
            }
        });
        this.mContentView = (FrameLayout) this.mRootView.findViewById(R.id.annotate_group);
        EditText editText = (EditText) this.mRootView.findViewById(R.id.edit_content);
        this.mEditText = editText;
        editText.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.3
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 66 || keyEvent.getAction() != 0) {
                    return false;
                }
                PcAnnotationEditController.this.saveAndHide();
                return true;
            }
        });
        this.mEditText.addTextChangedListener(this.mTextWatcher);
        int color = this.mRootView.getResources().getColor(R.color.v8_text_appearance_list_section_text_color, null);
        CompatUtils.invokeCursorColor(this.mEditText, color, color);
        this.mEditText.requestFocus();
        TextView textView = (TextView) this.mRootView.findViewById(R.id.done);
        this.mDone = textView;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PcAnnotationEditController.this.saveAndHide();
                }
            });
        }
    }

    public PcAnnotationEditController(Context context, NormalImageSpan normalImageSpan) {
        this.TAG = "PadAnnotationEditController";
        this.mDone = null;
        this.mMinHeight = 0;
        this.mIsShow = false;
        this.mTextWatcher = new TextWatcher() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.1
            final int maxLength = 100;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 100) {
                    if (PcAnnotationEditController.this.mEditListener != null) {
                        PcAnnotationEditController.this.mEditListener.onOverLength();
                    }
                    PcAnnotationEditController.this.mEditText.removeTextChangedListener(this);
                    PcAnnotationEditController.this.mEditText.setText(editable.subSequence(0, 100));
                    PcAnnotationEditController.this.mEditText.setSelection(PcAnnotationEditController.this.mEditText.getText().toString().length());
                    PcAnnotationEditController.this.mEditText.addTextChangedListener(PcAnnotationEditController.this.mTextWatcher);
                }
            }
        };
        this.mMinHeight = context.getResources().getDimensionPixelSize(R.dimen.pc_image_annotation_panel_height) + (context.getResources().getDimensionPixelSize(R.dimen.pc_image_annotation_edit_margin_vertical) * 2);
        this.mEditingSpan = normalImageSpan;
        this.mContext = context;
        if (TextUtils.isEmpty(normalImageSpan.getAnnotation())) {
            this.mRootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pc_image_annotate_layout_new, (ViewGroup) null);
        } else {
            this.mRootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pc_image_annotate_layout_edit, (ViewGroup) null);
        }
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcAnnotationEditController.this.saveAndHide();
            }
        });
        this.mContentView = (FrameLayout) this.mRootView.findViewById(R.id.annotate_group);
        EditText editText = (EditText) this.mRootView.findViewById(R.id.edit_content);
        this.mEditText = editText;
        editText.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.6
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 66 || keyEvent.getAction() != 0) {
                    return false;
                }
                PcAnnotationEditController.this.saveAndHide();
                return true;
            }
        });
        this.mEditText.addTextChangedListener(this.mTextWatcher);
        int color = this.mRootView.getResources().getColor(R.color.v8_text_appearance_list_section_text_color, null);
        CompatUtils.invokeCursorColor(this.mEditText, color, color);
        this.mEditText.requestFocus();
        TextView textView = (TextView) this.mRootView.findViewById(R.id.done);
        this.mDone = textView;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcAnnotationEditController.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PcAnnotationEditController.this.saveAndHide();
                }
            });
        }
    }

    public void onMeasure() {
        this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(this.mEditingSpan.getBounds().width(), 0), View.MeasureSpec.makeMeasureSpec(this.mEditingSpan.getAnnotationHeight(), 0));
        this.mContentView.getLayoutParams().width = this.mEditingSpan.getBounds().width();
        this.mContentView.getLayoutParams().height = this.mMinHeight > this.mEditingSpan.getAnnotationHeight() ? this.mMinHeight : this.mEditingSpan.getAnnotationHeight();
        FrameLayout frameLayout = this.mContentView;
        frameLayout.setLayoutParams(frameLayout.getLayoutParams());
    }

    public void onScroll(int i) {
        Logger.INSTANCE.i("PadAnnotationEditController", "onScroll scrollYBy = " + i);
        FrameLayout frameLayout = this.mContentView;
        frameLayout.setTranslationY(frameLayout.getTranslationY() + i);
    }

    public void show(ViewGroup viewGroup, Point point) {
        this.mEditText.setText(this.mEditingSpan.getAnnotation());
        EditText editText = this.mEditText;
        editText.setSelection(editText.getText().toString().length());
        Logger.INSTANCE.i("PadAnnotationEditController", "show initPoint = " + point.toString());
        this.mContentView.setX(point.x);
        this.mContentView.setY(point.y);
        onMeasure();
        viewGroup.addView(this.mRootView);
        this.mIsShow = true;
    }

    public void hide() {
        try {
            ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.mRootView);
            }
        } catch (Exception e) {
            Logger.INSTANCE.e("PadAnnotationEditController", "error hide " + e.toString());
        }
        this.mIsShow = false;
    }

    public void saveAndHide() {
        EditListener editListener = this.mEditListener;
        if (editListener != null) {
            editListener.onCompete(this.mEditText.getText().toString(), true);
        }
        hide();
    }

    public void saveNoForceAndHide(Runnable runnable) {
        EditListener editListener = this.mEditListener;
        if (editListener != null) {
            editListener.onCompete(this.mEditText.getText().toString(), false);
            if (runnable != null) {
                this.mEditText.postDelayed(runnable, 300L);
            }
        }
        hide();
    }

    public boolean isShowing() {
        return this.mIsShow;
    }

    public void cancel() {
        this.mRootView.performClick();
    }

    public void setEditListener(EditListener editListener) {
        this.mEditListener = editListener;
    }
}
