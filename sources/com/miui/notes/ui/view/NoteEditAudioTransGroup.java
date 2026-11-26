package com.miui.notes.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.RecordingWaveView;
import com.miui.notes.R;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public class NoteEditAudioTransGroup implements View.OnClickListener {
    private LinearLayout mAudioTrans;
    private ConstraintLayout mAudioTransTools;
    private Context mContext;
    private TextView mFlag;
    private View mMode;
    private TextView mModeDetail;
    private FrameLayout mModeGroup;
    private LinearLayout mModeLarge;
    private TextView mModeMeeting;
    private TextView mModeSingle;
    private RecordingWaveView mRecordingWaveView;
    private View mRootView;
    private ScreenSpec mScreenSpec;
    private OnTransClickListener mSetOnTransListener;
    private TextView mStop;
    private TextView mTranslation;

    public interface OnTransClickListener {
        void onViewClick(View view);
    }

    public NoteEditAudioTransGroup(Context context, View view, ScreenSpec screenSpec, boolean z, boolean z2) {
        this.mContext = context;
        this.mRootView = view;
        this.mScreenSpec = screenSpec;
        init(z, z2);
    }

    private void init(boolean z, boolean z2) {
        LinearLayout linearLayout = (LinearLayout) this.mRootView.findViewById(R.id.audio_trans);
        this.mAudioTrans = linearLayout;
        this.mAudioTransTools = (ConstraintLayout) linearLayout.findViewById(R.id.audio_trans_tools);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.stop);
        this.mStop = textView;
        UIUtils.setFolmeCommonTouchEffect(textView);
        UIUtils.setFolmeHoverEffect(this.mStop);
        TextView textView2 = (TextView) this.mRootView.findViewById(R.id.flag);
        this.mFlag = textView2;
        UIUtils.setFolmeCommonTouchEffect(textView2);
        UIUtils.setFolmeHoverEffect(this.mFlag);
        TextView textView3 = (TextView) this.mRootView.findViewById(R.id.translation);
        this.mTranslation = textView3;
        UIUtils.setFolmeCommonTouchEffect(textView3);
        UIUtils.setFolmeHoverEffect(this.mTranslation);
        this.mModeGroup = (FrameLayout) this.mRootView.findViewById(R.id.mode_group);
        View viewFindViewById = this.mRootView.findViewById(R.id.mode);
        this.mMode = viewFindViewById;
        UIUtils.setFolmeCommonTouchEffect(viewFindViewById);
        UIUtils.setFolmeHoverEffect(this.mMode);
        this.mModeDetail = (TextView) this.mRootView.findViewById(R.id.mode_detail);
        this.mModeLarge = (LinearLayout) this.mRootView.findViewById(R.id.mode_large);
        TextView textView4 = (TextView) this.mRootView.findViewById(R.id.mode_single);
        this.mModeSingle = textView4;
        UIUtils.setFolmeCommonTouchEffect(textView4);
        UIUtils.setFolmeHoverEffect(this.mModeSingle);
        TextView textView5 = (TextView) this.mRootView.findViewById(R.id.mode_meeting);
        this.mModeMeeting = textView5;
        UIUtils.setFolmeCommonTouchEffect(textView5);
        UIUtils.setFolmeHoverEffect(this.mModeMeeting);
        updateMode(z, z2, null);
        this.mRecordingWaveView = (RecordingWaveView) this.mRootView.findViewById(R.id.record_wave);
        this.mStop.setOnClickListener(this);
        this.mFlag.setOnClickListener(this);
        this.mTranslation.setOnClickListener(this);
    }

    public void updateMode(boolean z, boolean z2, ScreenSpec screenSpec) {
        if (this.mAudioTrans == null) {
            return;
        }
        if (screenSpec != null) {
            updateScreenStatus(screenSpec);
        } else {
            updateView(this.mScreenSpec);
        }
        updateModeText(z);
        updateTrans(z2);
    }

    public void updateScreenStatus(ScreenSpec screenSpec) {
        if (this.mScreenSpec.screenMode == screenSpec.screenMode && this.mScreenSpec.type == screenSpec.type) {
            return;
        }
        this.mScreenSpec = screenSpec;
        updateView(screenSpec);
    }

    private void updateView(ScreenSpec screenSpec) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_audio_content_margin);
        if (screenSpec.type == 1) {
            this.mModeLarge.setVisibility(8);
            if (this.mModeGroup.getVisibility() == 8) {
                this.mModeGroup.setVisibility(0);
            }
            this.mMode.setOnClickListener(this);
            if (RomUtils.isFoldDevice()) {
                int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_audio_content_margin_narrow);
                this.mAudioTransTools.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
                return;
            } else {
                this.mAudioTransTools.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                return;
            }
        }
        this.mModeLarge.setVisibility(0);
        this.mModeGroup.setVisibility(8);
        this.mModeSingle.setOnClickListener(this);
        this.mModeMeeting.setOnClickListener(this);
        this.mAudioTransTools.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
    }

    public View getModeMenuAnchorView() {
        return this.mModeGroup;
    }

    public void updateModeText(boolean z) {
        if (this.mModeGroup.getVisibility() == 0) {
            this.mModeDetail.setText(z ? R.string.audio_btn_mode_single : R.string.audio_btn_mode_meeting);
            return;
        }
        if (this.mModeLarge.getVisibility() == 0) {
            if (z) {
                this.mModeSingle.setTextColor(this.mContext.getColor(R.color.pad_note_audio_mode_item_text_color_s));
                this.mModeSingle.setBackgroundResource(R.drawable.pad_note_audio_mode_item_bg_s);
                this.mModeMeeting.setTextColor(this.mContext.getColor(R.color.pad_note_edit_header_info));
                this.mModeMeeting.setBackground(null);
                return;
            }
            this.mModeMeeting.setTextColor(this.mContext.getColor(R.color.pad_note_audio_mode_item_text_color_s));
            this.mModeMeeting.setBackgroundResource(R.drawable.pad_note_audio_mode_item_bg_s);
            this.mModeSingle.setTextColor(this.mContext.getColor(R.color.pad_note_edit_header_info));
            this.mModeSingle.setBackground(null);
        }
    }

    public void updateTrans(boolean z) {
        this.mTranslation.setSelected(z);
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

    public void setOnTransListener(OnTransClickListener onTransClickListener) {
        this.mSetOnTransListener = onTransClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnTransClickListener onTransClickListener = this.mSetOnTransListener;
        if (onTransClickListener != null) {
            onTransClickListener.onViewClick(view);
        }
    }
}
