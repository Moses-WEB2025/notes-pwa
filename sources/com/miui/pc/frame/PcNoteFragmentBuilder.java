package com.miui.pc.frame;

import com.miui.common.base.NoteLoadDataEntity;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.pc.feature.notes.PcHandWriteFragment;
import com.miui.pc.feature.notes.PcHybridNoteEditFragment;
import com.miui.pc.feature.notes.PcMultiHandWriteFragment;
import com.miui.pc.feature.notes.PcNoteEditFragment;
import com.miui.pc.feature.notes.PcWebMindNoteFragment;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteFragmentBuilder {
    public static boolean mUseHybridEditor = true;

    public static BaseNoteDetailFragment newInstance(NoteLoadDataEntity noteLoadDataEntity) {
        BaseNoteDetailFragment pcHandWriteFragment;
        if (noteLoadDataEntity.getNoteType() == 0) {
            if (mUseHybridEditor) {
                pcHandWriteFragment = new PcHybridNoteEditFragment();
            } else {
                pcHandWriteFragment = new PcNoteEditFragment();
            }
        } else if (noteLoadDataEntity.getNoteType() == 1) {
            pcHandWriteFragment = new PcWebMindNoteFragment();
        } else if (noteLoadDataEntity.getNoteType() == 3) {
            pcHandWriteFragment = new PcMultiHandWriteFragment();
        } else {
            pcHandWriteFragment = new PcHandWriteFragment();
        }
        pcHandWriteFragment.setArguments(noteLoadDataEntity.toBundle());
        return pcHandWriteFragment;
    }
}
