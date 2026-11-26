package xc.richtext.api;

import java.util.ArrayList;
import java.util.List;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedInfo;
import xc.richtext.data.bean.IBufferBean;

/* JADX INFO: loaded from: classes6.dex */
public class ItemSpannedData {
    private SpannedInfo mSpannedInfo;

    public ItemSpannedData(SpannedInfo spannedInfo) {
        this.mSpannedInfo = spannedInfo;
    }

    public SpannedInfo getSpannedInfo() {
        return this.mSpannedInfo;
    }

    public void undo() {
        SpannedInfo spannedInfo = this.mSpannedInfo;
        if (spannedInfo == null || !spannedInfo.canUndo()) {
            return;
        }
        this.mSpannedInfo.undo();
    }

    public void redo() {
        SpannedInfo spannedInfo = this.mSpannedInfo;
        if (spannedInfo == null || !spannedInfo.canRedo()) {
            return;
        }
        this.mSpannedInfo.redo();
    }

    public void release() {
        this.mSpannedInfo.release();
        this.mSpannedInfo = null;
    }

    public IBufferBean getSpannedBuffer(String str, int i) {
        SpannedInfo spannedInfo = this.mSpannedInfo;
        if (spannedInfo == null) {
            return null;
        }
        return spannedInfo.getSpannedBuffer(str, i);
    }

    public List<SpannedData> getDataList(int i) {
        SpannedInfo spannedInfo = this.mSpannedInfo;
        return spannedInfo == null ? new ArrayList() : spannedInfo.getDataList(i);
    }
}
