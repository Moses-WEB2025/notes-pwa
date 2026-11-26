package com.miui.todo.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.common.tool.DisplayUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class AudioToolbar extends LinearLayout {
    private TextView mCancelBtn;
    private Chronometer mChronometer;
    private TextView mRecordBtn;

    public AudioToolbar(Context context) {
        super(context);
    }

    public AudioToolbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AudioToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mRecordBtn = (TextView) findViewById(R.id.record);
        this.mCancelBtn = (TextView) findViewById(R.id.cancel);
        this.mChronometer = (Chronometer) findViewById(R.id.chronometer);
        DisplayUtils.setMiuiMediumTypeFace(this.mRecordBtn);
        TextView textView = this.mCancelBtn;
        if (textView != null) {
            DisplayUtils.setMiuiMediumTypeFace(textView);
        }
    }

    public void setOnChronometerTickListener(Chronometer.OnChronometerTickListener onChronometerTickListener) {
        this.mChronometer.setOnChronometerTickListener(onChronometerTickListener);
    }

    public void setCompleteListener(View.OnClickListener onClickListener) {
        this.mRecordBtn.setOnClickListener(onClickListener);
        TextView textView = this.mCancelBtn;
        if (textView != null) {
            textView.setOnClickListener(onClickListener);
        }
    }

    public void startChronometer() {
        this.mChronometer.setBase(SystemClock.elapsedRealtime());
        this.mChronometer.start();
    }

    public void stopChronometer() {
        this.mChronometer.stop();
    }
}
