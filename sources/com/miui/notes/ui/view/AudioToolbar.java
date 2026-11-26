package com.miui.notes.ui.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class AudioToolbar extends LinearLayout {
    private Chronometer mChronometer;
    private Button mRecordBtn;

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
        this.mRecordBtn = (Button) findViewById(R.id.record);
        this.mChronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    public void setOnChronometerTickListener(Chronometer.OnChronometerTickListener onChronometerTickListener) {
        this.mChronometer.setOnChronometerTickListener(onChronometerTickListener);
    }

    public void setCompleteListener(View.OnClickListener onClickListener) {
        this.mRecordBtn.setOnClickListener(onClickListener);
    }

    public void startChronometer() {
        this.mChronometer.setBase(SystemClock.elapsedRealtime());
        this.mChronometer.start();
    }

    public void stopChronometer() {
        this.mChronometer.stop();
    }
}
