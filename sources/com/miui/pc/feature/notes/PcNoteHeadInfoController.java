package com.miui.pc.feature.notes;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteHeadInfoController implements IPcNoteInfoHeaderController {
    private View mHeadInfo;
    private TextView mTvCreateTime;
    private TextView mTvModifiedTime;
    private TextView mTvTextCount;

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void bindFragment(Fragment fragment) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void dismissMenu() {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onDestroy() {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onNoteMenuClick(int i) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setFolderGroupVisibility(int i) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setHeadMenuInfo(Cursor cursor, long[] jArr, long j) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setNoteMenuClickListener(INoteInfoHeader.NoteMenuClickListener noteMenuClickListener) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateAlarm(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateFolderName(String str) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateNoteHiddenState(WorkingNote workingNote) {
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
    }

    public PcNoteHeadInfoController(View view) {
        initView(view);
    }

    private void initView(View view) {
        this.mHeadInfo = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcNoteHeadInfoController.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        this.mTvCreateTime = (TextView) view.findViewById(R.id.create_time);
        this.mTvModifiedTime = (TextView) view.findViewById(R.id.modified_time);
        this.mTvTextCount = (TextView) view.findViewById(R.id.text_count);
    }

    @Override // com.miui.pc.feature.notes.IPcNoteInfoHeaderController
    public void show(boolean z) {
        Folme.useAt(this.mHeadInfo).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    @Override // com.miui.pc.feature.notes.IPcNoteInfoHeaderController
    public void hide(boolean z) {
        Folme.useAt(this.mHeadInfo).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f));
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public View getView() {
        return this.mHeadInfo;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void update(WorkingNote workingNote, int i) {
        Logger.INSTANCE.d("PcNoteHeadInfoController", "update textCount " + i);
        updateDataTime(workingNote);
        updateTextCount(i);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateTextCount(int i) {
        this.mTvTextCount.setText(Utils.obtainLocalInteger(i, R.plurals.word_count));
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateDataTime(WorkingNote workingNote) {
        this.mTvCreateTime.setText(DateFormatUtils.formatDateText(this.mHeadInfo.getContext(), workingNote.getCreatedDate()));
        this.mTvModifiedTime.setText(DateFormatUtils.formatDateText(this.mHeadInfo.getContext(), workingNote.getModifiedDate()));
    }
}
