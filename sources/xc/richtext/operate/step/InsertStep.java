package xc.richtext.operate.step;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class InsertStep implements ISpannedStep {
    public final EngineBindContext mEngineBindContext;
    public List<SpannedData> mSpannedData;

    public InsertStep(EngineBindContext engineBindContext, SpannedData spannedData) {
        this.mEngineBindContext = engineBindContext;
        ArrayList arrayList = new ArrayList(1);
        this.mSpannedData = arrayList;
        arrayList.add(spannedData);
    }

    public InsertStep(EngineBindContext engineBindContext, List<SpannedData> list) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = new ArrayList(list);
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        if (this.mSpannedData.isEmpty()) {
            return 0;
        }
        return this.mSpannedData.get(0).getPageIndex();
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        int[] iArr = new int[this.mSpannedData.size()];
        for (int i = 0; i < this.mSpannedData.size(); i++) {
            SpannedData spannedData = this.mSpannedData.get(i);
            spannedData.mSpannedStatus = 3;
            iArr[i] = spannedData.mId;
        }
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        Iterator<SpannedData> it = this.mSpannedData.iterator();
        while (it.hasNext()) {
            it.next().mSpannedStatus = 2;
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData);
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((SpannedData) arrayList.get(i)).getId();
        }
        return iArr;
    }
}
