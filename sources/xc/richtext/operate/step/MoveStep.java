package xc.richtext.operate.step;

import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;

/* JADX INFO: loaded from: classes2.dex */
public class MoveStep implements ISpannedStep {
    public final EngineBindContext mEngineBindContext;
    public PointF mPointF = new PointF();
    public List<SpannedData> mSpannedDataList;
    public SpannedMap mSpannedMap;

    public MoveStep(EngineBindContext engineBindContext, List<SpannedData> list, PointF pointF) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedMap = engineBindContext.getSpannedMap();
        this.mSpannedDataList = new ArrayList(list);
        this.mPointF.set(pointF.x / this.mSpannedMap.getDrawRatio(), pointF.y / this.mSpannedMap.getDrawRatio());
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
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            RectF rectF = spannedData.getRectF();
            rectF.offset(-this.mPointF.x, -this.mPointF.y);
            spannedData.setSaveRect(rectF);
            spannedData.setRatioData(this.mSpannedMap.getDrawRatio());
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedDataList);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger.updateSpanDataPageIndex(arrayList);
        return iArr;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        for (int i = 0; i < this.mSpannedDataList.size(); i++) {
            SpannedData spannedData = this.mSpannedDataList.get(i);
            RectF rectF = spannedData.getRectF();
            rectF.offset(this.mPointF.x, this.mPointF.y);
            spannedData.setSaveRect(rectF);
            spannedData.setRatioData(this.mSpannedMap.getDrawRatio());
        }
        ArrayList arrayList = (ArrayList) this.mEngineBindContext.mScaleOperator.intersectData(this.mSpannedDataList);
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger.updateSpanDataPageIndex(arrayList);
        return iArr;
    }
}
