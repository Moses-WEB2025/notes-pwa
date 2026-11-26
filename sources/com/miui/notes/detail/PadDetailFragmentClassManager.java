package com.miui.notes.detail;

import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.pad.feature.notes.commonedit.PadHybridNoteEditFragment;
import com.miui.pad.feature.notes.commonedit.PadNoteEditFragment;
import com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment;
import com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PadDetailFragmentClassManager {
    private static final String TAG = "PadDetailFragmentClassManager";
    private static boolean mIsLoad = false;
    private static Long mLastEntityId = 0L;
    public static boolean mUseHybridEditor = true;

    public static Class<? extends Fragment> observeClass(NoteLoadDataEntity noteLoadDataEntity, int i) {
        if (noteLoadDataEntity != null) {
            if (noteLoadDataEntity.getNoteType() == 0) {
                if ((!mUseHybridEditor || LiteUtils.isLiteOrMiddle()) && !noteLoadDataEntity.mHasNewStyle) {
                    Logger.INSTANCE.d(TAG, "observeClass,use PadNoteEditFragment for new entity,$invokeSource:,invokeSource:" + i);
                    return PadNoteEditFragment.class;
                }
                Logger.INSTANCE.d(TAG, "observeClass,use PadHybridNoteEditFragment for new entity ,$invokeSource:invokeSource:" + i);
                return PadHybridNoteEditFragment.class;
            }
            if (noteLoadDataEntity.getNoteType() == 1) {
                return PadWebMindNoteFragment.class;
            }
            if (noteLoadDataEntity.getNoteType() == 3) {
                Logger.INSTANCE.d(TAG, "observeClass,use PadMultiHandWriteFragment for new entity,$invokeSource:,invokeSource:" + i);
                return PadMultiHandWriteFragment.class;
            }
            if (noteLoadDataEntity.getNoteType() != 2) {
                return null;
            }
            mLastEntityId = Long.valueOf(noteLoadDataEntity.getNoteId());
            return PadMultiHandWriteFragment.class;
        }
        if (!mUseHybridEditor || LiteUtils.isLiteOrMiddle()) {
            Logger.INSTANCE.d(TAG, "observeClass,noteEntity == null, use PadNoteEditFragment,invokeSource:" + i);
            return PadNoteEditFragment.class;
        }
        Logger.INSTANCE.d(TAG, "observeClass,noteEntity == null, use  PadHybridNoteEditFragment,invokeSource:" + i);
        return PadHybridNoteEditFragment.class;
    }

    public static void isLoaded(boolean z) {
        mIsLoad = z;
    }

    public static boolean isNotSameClick(NoteLoadDataEntity noteLoadDataEntity) {
        if (!mLastEntityId.equals(Long.valueOf(noteLoadDataEntity.getNoteId()))) {
            mIsLoad = false;
        }
        return (mIsLoad && mLastEntityId.longValue() == noteLoadDataEntity.getNoteId()) ? false : true;
    }
}
