package xc.richtext.operate.step;

import java.util.ArrayList;
import java.util.List;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpanUpdateInfo;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;

/* JADX INFO: loaded from: classes2.dex */
public class MixedStep implements ISpannedStep {
    public final EngineBindContext mEngineBindContext;
    public List<SpanUpdateInfo> mSpanUpdateInfoList;
    public List<SpannedData> mSpannedData;
    public SpannedMap mSpannedMap;

    public MixedStep(EngineBindContext engineBindContext, List<SpannedData> list, List<SpanUpdateInfo> list2) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedMap = engineBindContext.getSpannedMap();
        this.mSpannedData = new ArrayList(list);
        this.mSpanUpdateInfoList = list2;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        if (this.mSpannedData.isEmpty()) {
            return 0;
        }
        return this.mSpannedData.get(0).getPageIndex();
    }

    public void updateSpanUpdateInfoList(float f) {
        float drawRatio = 1.0f / this.mSpannedMap.getDrawRatio();
        for (SpanUpdateInfo spanUpdateInfo : this.mSpanUpdateInfoList) {
            float f2 = f * drawRatio;
            spanUpdateInfo.oldRectF.offset(0.0f, f2);
            spanUpdateInfo.newRectF.offset(0.0f, f2);
        }
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        for (int i = 0; i < this.mSpannedData.size(); i++) {
            SpannedData spannedData = this.mSpannedData.get(i);
            for (SpanUpdateInfo spanUpdateInfo : this.mSpanUpdateInfoList) {
                if (spannedData.mId == spanUpdateInfo.id) {
                    spannedData.setRectF(spanUpdateInfo.getScaleRectF(this.mSpannedMap.getDrawRatio(), true));
                    spannedData.setRatioData(this.mSpannedMap.getDrawRatio());
                    spannedData.mDegrees = spanUpdateInfo.oldDegrees;
                    if (spannedData.mType == 11) {
                        spannedData.mTextSize /= spanUpdateInfo.scale;
                    }
                }
            }
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger.updateSpanDataPageIndex(arrayList);
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        for (int i = 0; i < this.mSpannedData.size(); i++) {
            SpannedData spannedData = this.mSpannedData.get(i);
            for (SpanUpdateInfo spanUpdateInfo : this.mSpanUpdateInfoList) {
                if (spannedData.mId == spanUpdateInfo.id) {
                    spannedData.setRectF(spanUpdateInfo.getScaleRectF(this.mSpannedMap.getDrawRatio(), false));
                    spannedData.setRatioData(this.mSpannedMap.getDrawRatio());
                    spannedData.mDegrees = spanUpdateInfo.newDegrees;
                    if (spannedData.mType == 11) {
                        spannedData.mTextSize *= spanUpdateInfo.scale;
                    }
                }
            }
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedData);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger.updateSpanDataPageIndex(arrayList);
        return iArr;
    }
}
