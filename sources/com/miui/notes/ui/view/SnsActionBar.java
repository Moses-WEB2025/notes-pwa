package com.miui.notes.ui.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class SnsActionBar extends LinearLayout {
    private SnsActionBarCallback mCallback;
    private Button mLeft;
    private View.OnClickListener mOnClickListener;
    private Button mRight;
    private TextView mTitle;

    public interface SnsActionBarCallback {
        void onLeftClick();

        void onRightClick();
    }

    public SnsActionBar(Context context) {
        super(context);
        this.mOnClickListener = new View.OnClickListener() { // from class: com.miui.notes.ui.view.SnsActionBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case 16908313:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onLeftClick();
                        }
                        break;
                    case 16908314:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onRightClick();
                        }
                        break;
                }
            }
        };
    }

    public SnsActionBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnClickListener = new View.OnClickListener() { // from class: com.miui.notes.ui.view.SnsActionBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case 16908313:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onLeftClick();
                        }
                        break;
                    case 16908314:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onRightClick();
                        }
                        break;
                }
            }
        };
    }

    public SnsActionBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnClickListener = new View.OnClickListener() { // from class: com.miui.notes.ui.view.SnsActionBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case 16908313:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onLeftClick();
                        }
                        break;
                    case 16908314:
                        if (SnsActionBar.this.mCallback != null) {
                            SnsActionBar.this.mCallback.onRightClick();
                        }
                        break;
                }
            }
        };
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        Button button = (Button) findViewById(16908313);
        this.mLeft = button;
        button.setOnClickListener(this.mOnClickListener);
        this.mTitle = (TextView) findViewById(R.id.title);
        Button button2 = (Button) findViewById(16908314);
        this.mRight = button2;
        button2.setOnClickListener(this.mOnClickListener);
    }

    public void setLeftText(int i) {
        this.mLeft.setText(i);
    }

    public void setTitleText(int i) {
        this.mTitle.setText(i);
    }

    public void setRightText(int i) {
        this.mRight.setText(i);
    }

    public void setCallback(SnsActionBarCallback snsActionBarCallback) {
        this.mCallback = snsActionBarCallback;
    }

    public Button getmLeft() {
        return this.mLeft;
    }

    public Button getmRight() {
        return this.mRight;
    }
}
