package xc.richtext.operate.step;

import java.util.ArrayList;
import java.util.List;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpanUpdateInfo;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes2.dex */
public class CropStep implements ISpannedStep {
    public final EngineBindContext mEngineBindContext;
    public List<SpanUpdateInfo> mSpanUpdateInfoList;
    public List<SpannedData> mSpannedData;

    public CropStep(EngineBindContext engineBindContext, List<SpannedData> list, List<SpanUpdateInfo> list2) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = new ArrayList(list);
        this.mSpanUpdateInfoList = list2;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        for (int i = 0; i < this.mSpannedData.size(); i++) {
            SpannedData spannedData = this.mSpannedData.get(i);
            for (SpanUpdateInfo spanUpdateInfo : this.mSpanUpdateInfoList) {
                if (spannedData.mId == spanUpdateInfo.id) {
                    spannedData.setCropRectF(spanUpdateInfo.oldRectF);
                }
            }
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        for (int i = 0; i < this.mSpannedData.size(); i++) {
            SpannedData spannedData = this.mSpannedData.get(i);
            for (SpanUpdateInfo spanUpdateInfo : this.mSpanUpdateInfoList) {
                if (spannedData.mId == spanUpdateInfo.id) {
                    spannedData.setCropRectF(spanUpdateInfo.newRectF);
                }
            }
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        if (this.mSpannedData.isEmpty()) {
            return 0;
        }
        return this.mSpannedData.get(0).getPageIndex();
    }
}
