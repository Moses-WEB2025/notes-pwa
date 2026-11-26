package xc.richtext.data;

import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.step.LocalStepManager;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedInfo {
    public final SpannedMap mSpannedMap;

    public SpannedInfo(SpannedMap spannedMap) {
        LocalStepManager localStepManager = new LocalStepManager();
        LocalStepManager stepManager = spannedMap.getStepManager();
        if (stepManager != null) {
            localStepManager.setUndoList(new ArrayList(stepManager.getUndoList()));
            localStepManager.setRedoList(new ArrayList(stepManager.getRedoList()));
        }
        SpannedMap spannedMap2 = new SpannedMap(localStepManager);
        this.mSpannedMap = spannedMap2;
        spannedMap2.add(new ArrayList(spannedMap.getDataListAll()));
    }

    public boolean canUndo() {
        LocalStepManager stepManager;
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null || (stepManager = spannedMap.getStepManager()) == null) {
            return false;
        }
        return !stepManager.mUndoStepList.isEmpty();
    }

    public boolean canRedo() {
        LocalStepManager stepManager;
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null || (stepManager = spannedMap.getStepManager()) == null) {
            return false;
        }
        return !stepManager.mRedoSteplist.isEmpty();
    }

    public void undo() {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null || spannedMap.getStepManager() == null) {
            return;
        }
        this.mSpannedMap.getStepManager().undo();
    }

    public void redo() {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null || spannedMap.getStepManager() == null) {
            return;
        }
        this.mSpannedMap.getStepManager().redo();
    }

    public IBufferBean getSpannedBuffer(String str, int i) {
        if (this.mSpannedMap == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : this.mSpannedMap.getDataListVisible()) {
            if (spannedData.getType() != 11 || !TextUtils.isEmpty(spannedData.getText())) {
                arrayList.add(spannedData);
            }
        }
        try {
            return new SpannedBufferHelper().readBufferBean(str, i, arrayList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void release() {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap != null) {
            LocalStepManager stepManager = spannedMap.getStepManager();
            if (stepManager != null) {
                stepManager.clearAllList();
            }
            this.mSpannedMap.clear();
        }
    }

    public boolean isContentEmpty() {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap != null) {
            for (SpannedData spannedData : spannedMap.getDataListAll()) {
                if (spannedData.mType != 11 || !TextUtils.isEmpty(spannedData.mText)) {
                    if (spannedData.isVisible() || spannedData.mSpannedStatus == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<SpannedData> getDataList(int i) {
        SpannedMap spannedMap = this.mSpannedMap;
        if (spannedMap == null) {
            return new ArrayList();
        }
        return spannedMap.getDataList(i);
    }
}
