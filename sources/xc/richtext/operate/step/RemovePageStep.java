package xc.richtext.operate.step;

import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;

/* JADX INFO: loaded from: classes2.dex */
public class RemovePageStep implements ISpannedStep {
    private EngineBindContext mEngineBindContext;
    private int pageIndex;
    private SpannedMap spannedMap;

    public RemovePageStep(EngineBindContext engineBindContext, int i) {
        this.mEngineBindContext = engineBindContext;
        this.pageIndex = i;
        setSpannedMap();
    }

    private void setSpannedMap() {
        for (SpannedData spannedData : this.mEngineBindContext.getSpannedMap(this.pageIndex).getDataListAll()) {
        }
    }

    public SpannedMap getSpannedMap() {
        return this.spannedMap;
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] undo() {
        return new int[0];
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int[] redo() {
        return new int[0];
    }

    @Override // xc.richtext.operate.step.ISpannedStep
    public int getPageIndex() {
        return this.pageIndex;
    }
}
