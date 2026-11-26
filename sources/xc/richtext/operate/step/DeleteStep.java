package xc.richtext.operate.step;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class DeleteStep implements ISpannedStep {
    public final EngineBindContext mEngineBindContext;
    public List<SpannedData> mSpannedDataList;

    public DeleteStep(EngineBindContext engineBindContext, List<SpannedData> list) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedDataList = new ArrayList(list);
        engineBindContext.addDeleteImages(list);
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        if (this.mSpannedDataList.isEmpty()) {
            return 0;
        }
        return this.mSpannedDataList.get(0).getPageIndex();
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        this.mEngineBindContext.removeDeleteImages(this.mSpannedDataList);
        Iterator<SpannedData> it = this.mSpannedDataList.iterator();
        while (it.hasNext()) {
            it.next().mSpannedStatus = 2;
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedDataList);
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((SpannedData) arrayList.get(i)).getId();
        }
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        this.mEngineBindContext.addDeleteImages(this.mSpannedDataList);
        int[] iArr = new int[this.mSpannedDataList.size()];
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            spannedData.mSpannedStatus = 3;
            iArr[i] = spannedData.mId;
        }
        return iArr;
    }
}
