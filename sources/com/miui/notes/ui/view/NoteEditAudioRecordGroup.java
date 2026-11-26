package com.miui.notes.ui.view;

import android.content.Context;
import android.view.View;
import com.miui.common.view.RecordingWaveView;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class NoteEditAudioRecordGroup {
    private Context mContext;
    private RecordingWaveView mRecordingWaveView;
    private View mRootView;

    public NoteEditAudioRecordGroup(Context context, View view) {
        this.mContext = context;
        this.mRootView = view;
        init();
    }

    private void init() {
        this.mRecordingWaveView = (RecordingWaveView) this.mRootView.findViewById(R.id.record_wave);
    }

    public void updateWave(double d) {
        this.mRecordingWaveView.addVolumes(d);
    }

    public void reset() {
        this.mRecordingWaveView.reset();
    }

    public void startWaveAnim() {
        this.mRecordingWaveView.startDraw();
    }

    public void stopWaveAnim() {
        this.mRecordingWaveView.stopDraw();
    }
}
