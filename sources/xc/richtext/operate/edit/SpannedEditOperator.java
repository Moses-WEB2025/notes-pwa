package xc.richtext.operate.edit;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import xc.richtext.EngineBindContext;
import xc.richtext.api.OnSpannedHightListener;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;
import xc.richtext.data.TextInfo;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.operate.scale.ScaleParams;
import xc.richtext.operate.step.AddTextStep;
import xc.richtext.operate.step.DeleteStep;
import xc.richtext.operate.step.ISpannedStep;
import xc.richtext.operate.step.ITextChangeStep;
import xc.richtext.operate.step.InsertStep;
import xc.richtext.operate.step.ReduceTextStep;
import xc.richtext.operate.step.RemovePageStep;
import xc.richtext.operate.step.UpdateTextBoxStep;
import xc.richtext.utils.ScreenRenderUtil;
import xc.richtext.utils.SpannedUtils;
import xc.richtext.utils.TextLayoutUtil;
import xc.richtext.view.SpannedLayoutChanger;
import xc.richtext.view.SpannedLayoutModel;
import xc.richtext.view.img.CsImageDrawModel;
import xc.richtext.view.text.CsEditText;
import xc.richtext.view.text.CsEditViewModel;

/* JADX INFO: loaded from: classes.dex */
public class SpannedEditOperator implements ISpannedEditOperator {
    public EngineBindContext mEngineBindContext;
    public String mHightText;
    public SpannedEditObject mSpannedEditObject;
    public SpannedLayoutChanger mSpannedLayoutChanger;
    public SpannedSelectObject mSpannedSelectObject;
    private ScreenRenderProvider screenRenderProvider;
    private final String TAG = "SpannedEditOperator";
    public int mTextBgColor = 0;
    public int mMinLimitSize = 72;
    public int mHighColor = -16776961;
    public int mHightAlpha = 255;

    public SpannedEditOperator(EngineBindContext engineBindContext, ScreenRenderProvider screenRenderProvider) {
        this.screenRenderProvider = screenRenderProvider;
        this.mEngineBindContext = engineBindContext;
    }

    private float getDrawRatio() {
        return this.mEngineBindContext.getSpannedMap().getDrawRatio();
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void setMinLimitSize(int i, int i2) {
        this.mMinLimitSize = Math.max(i, i2);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public SpannedData addText(RectF rectF, String str) {
        SpannedData spannedData = getSpannedData(rectF, str);
        int pageIndex = getPageIndex(rectF);
        spannedData.setPageIndex(pageIndex);
        this.mEngineBindContext.getSpannedMap(pageIndex).add(spannedData);
        this.mEngineBindContext.getLocalStepManager().addStep(new InsertStep(this.mEngineBindContext, spannedData));
        updateScaleInfo(spannedData);
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(new int[]{spannedData.mId}, true, false);
        }
        return spannedData;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public SpannedData addText(TextInfo textInfo) {
        SpannedData spannedData = getSpannedData(textInfo);
        int pageIndex = getPageIndex(textInfo.getRectF());
        spannedData.setPageIndex(pageIndex);
        this.mEngineBindContext.getSpannedMap(pageIndex).add(spannedData);
        updateScaleInfo(spannedData);
        this.mEngineBindContext.getLocalStepManager().addStep(new InsertStep(this.mEngineBindContext, spannedData));
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(new int[]{spannedData.mId}, true, false);
        }
        return spannedData;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public SpannedData moveEditText(int i, int i2) {
        SpannedData spannedData = this.mEngineBindContext.getSpannedData(i);
        if (spannedData != null) {
            RectF rectF = spannedData.getRectF();
            if (i2 > 0) {
                rectF.offset(0.0f, ((i2 / spannedData.mRatio) / spannedData.mScale) + 25);
            } else {
                rectF.offset(0.0f, ((i2 / spannedData.mRatio) / spannedData.mScale) - 25);
            }
            spannedData.setSaveRect(rectF);
            updateScaleInfo(spannedData);
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedDataChanged(new int[]{spannedData.mId}, true, false);
            }
            if (ScreenRenderUtil.getProvider() != null) {
                ScreenRenderUtil.getProvider().refreshEditText(spannedData);
            }
        }
        return spannedData;
    }

    private SpannedData getSpannedData(RectF rectF, String str) {
        RectF minLimitRectf = getMinLimitRectf(rectF, this.mMinLimitSize * this.mEngineBindContext.mScaleOperator.getDrawRatio() * this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal);
        updateCanvasRectF(minLimitRectf);
        float drawRatio = getDrawRatio();
        SpannedData spannedData = new SpannedData(11, new RectF(minLimitRectf.left / drawRatio, minLimitRectf.top / drawRatio, minLimitRectf.right / drawRatio, minLimitRectf.bottom / drawRatio), "", this.screenRenderProvider);
        spannedData.mTextSize = 34.0f;
        spannedData.mTextColor = -16777216;
        spannedData.mText = str;
        spannedData.o = true;
        spannedData.mSpannedStatus = 0;
        spannedData.mBgColor = this.mTextBgColor;
        return spannedData;
    }

    private SpannedData getSpannedData(TextInfo textInfo) {
        RectF minLimitRectf = getMinLimitRectf(textInfo.getRectF(), this.mMinLimitSize * this.mEngineBindContext.mScaleOperator.getDrawRatio() * this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal);
        updateCanvasRectF(minLimitRectf);
        float drawRatio = getDrawRatio();
        RectF rectF = new RectF(minLimitRectf.left / drawRatio, minLimitRectf.top / drawRatio, minLimitRectf.right / drawRatio, minLimitRectf.bottom / drawRatio);
        float textSize = textInfo.getTextSize();
        SpannedData spannedData = new SpannedData(11, rectF, textInfo.getText(), this.screenRenderProvider);
        spannedData.mTextSize = textSize;
        spannedData.mTextColor = textInfo.getTextColor();
        spannedData.o = true;
        spannedData.mSpannedStatus = 0;
        spannedData.mBgColor = this.mTextBgColor;
        return spannedData;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public SpannedData addBitmap(String str, RectF rectF, float f) {
        return addBitmap(str, rectF, f, false);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public SpannedData addBitmap(String str, RectF rectF, float f, boolean z) {
        if (ScreenRenderUtil.getProvider() != null) {
            rectF = ScreenRenderUtil.getProvider().covertSSRectToWSRect(rectF);
        } else {
            remapRangeRect(rectF, getDefaultScreenRect());
        }
        float drawRatio = getDrawRatio();
        SpannedData spannedData = new SpannedData(new RectF(rectF.left / drawRatio, rectF.top / drawRatio, rectF.right / drawRatio, rectF.bottom / drawRatio), str, f, this.screenRenderProvider);
        spannedData.mSpannedStatus = 0;
        int pageIndex = getPageIndex(rectF);
        spannedData.setPageIndex(pageIndex);
        if (z) {
            this.mEngineBindContext.mLocalStepManager.addStepForAI(new InsertStep(this.mEngineBindContext, spannedData));
        } else {
            this.mEngineBindContext.mLocalStepManager.addStep(new InsertStep(this.mEngineBindContext, spannedData));
        }
        this.mEngineBindContext.getSpannedMap(pageIndex).add(spannedData);
        updateScaleInfo(spannedData);
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(new int[]{spannedData.mId}, true, false);
        }
        return spannedData;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public RectF getSpanAreaInCanvas(RectF rectF) {
        if (rectF == null || rectF.isEmpty()) {
            return new RectF();
        }
        updateCanvasRectF(rectF);
        float drawRatio = getDrawRatio();
        return new RectF(rectF.left / drawRatio, rectF.top / drawRatio, rectF.right / drawRatio, rectF.bottom / drawRatio);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public RectF getSpanAreaInCanvasWithoutRadio(RectF rectF) {
        if (rectF == null || rectF.isEmpty()) {
            return new RectF();
        }
        updateCanvasRectF(rectF);
        return new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public boolean canUndo() {
        return !this.mEngineBindContext.getLocalStepManager().mUndoStepList.isEmpty();
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public boolean canRedo() {
        return !this.mEngineBindContext.getLocalStepManager().mRedoSteplist.isEmpty();
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void undo(boolean z) {
        int textId;
        SpannedLayoutChanger spannedLayoutChanger;
        ISpannedStep lastUndoStep = this.mEngineBindContext.getLocalStepManager().getLastUndoStep();
        if (!((this.mSpannedLayoutChanger == null || !((lastUndoStep instanceof AddTextStep) || (lastUndoStep instanceof ReduceTextStep))) ? false : this.mSpannedLayoutChanger.handleTextUndoRedo(((ITextChangeStep) lastUndoStep).getTextId(), true))) {
            ISpannedStep lastUndoStep2 = this.mEngineBindContext.getLocalStepManager().getLastUndoStep();
            if (lastUndoStep2 instanceof UpdateTextBoxStep) {
                textId = ((ITextChangeStep) lastUndoStep2).getTextId();
            } else {
                exitEditMode();
                textId = -1;
            }
            this.mEngineBindContext.getLocalStepManager().undo(z);
            if (textId != -1 && (spannedLayoutChanger = this.mSpannedLayoutChanger) != null) {
                spannedLayoutChanger.refreshTextLayout(textId);
            }
        }
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.refreshSpan();
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void redo(boolean z) {
        int textId;
        SpannedLayoutChanger spannedLayoutChanger;
        ISpannedStep lastRedoStep = this.mEngineBindContext.getLocalStepManager().getLastRedoStep();
        boolean zHandleTextUndoRedo = false;
        if (this.mSpannedLayoutChanger != null && ((lastRedoStep instanceof AddTextStep) || (lastRedoStep instanceof ReduceTextStep))) {
            zHandleTextUndoRedo = this.mSpannedLayoutChanger.handleTextUndoRedo(((ITextChangeStep) lastRedoStep).getTextId(), false);
        }
        if (!zHandleTextUndoRedo) {
            ISpannedStep lastRedoStep2 = this.mEngineBindContext.getLocalStepManager().getLastRedoStep();
            if (lastRedoStep2 instanceof UpdateTextBoxStep) {
                textId = ((ITextChangeStep) lastRedoStep2).getTextId();
            } else {
                exitEditMode();
                textId = -1;
            }
            this.mEngineBindContext.getLocalStepManager().redo(z);
            if (textId != -1 && (spannedLayoutChanger = this.mSpannedLayoutChanger) != null) {
                spannedLayoutChanger.refreshTextLayout(textId);
            }
        }
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.refreshSpan();
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void clearAll() {
        SpannedLayoutModel spannedLayoutModel;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null && (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) != null) {
            spannedLayoutModel.removeOtherEditText(-1);
        }
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : this.mEngineBindContext.getAllDataList()) {
            if (spannedData.isVisible()) {
                arrayList.add(spannedData);
                spannedData.mSpannedStatus = 3;
            }
        }
        if (arrayList.size() < 1) {
            return;
        }
        this.mEngineBindContext.mLocalStepManager.addStep(new DeleteStep(this.mEngineBindContext, arrayList));
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((SpannedData) arrayList.get(i)).getId();
        }
        if (this.mSpannedLayoutChanger != null) {
            SpannedEditObject spannedEditObject = this.mSpannedEditObject;
            if (spannedEditObject != null) {
                spannedEditObject.mSpannedData = null;
                this.mSpannedEditObject = null;
                this.mSpannedLayoutChanger.onSpannedEdited(null);
            }
            this.mSpannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void clearPage(int i) {
        SpannedLayoutModel spannedLayoutModel;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null && (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) != null) {
            spannedLayoutModel.removeOtherEditText(-1);
        }
        ArrayList arrayList = new ArrayList();
        for (SpannedData spannedData : this.mEngineBindContext.getSpannedMap(i).getDataListAll()) {
            if (spannedData.isVisible()) {
                arrayList.add(spannedData);
                spannedData.mSpannedStatus = 3;
            }
        }
        if (arrayList.size() < 1) {
            return;
        }
        this.mEngineBindContext.mLocalStepManager.addStep(new DeleteStep(this.mEngineBindContext, arrayList));
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((SpannedData) arrayList.get(i2)).getId();
        }
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            screenRenderProvider.refreshSpan();
        }
        if (this.mSpannedLayoutChanger != null) {
            SpannedEditObject spannedEditObject = this.mSpannedEditObject;
            if (spannedEditObject != null) {
                spannedEditObject.mSpannedData = null;
                this.mSpannedEditObject = null;
                this.mSpannedLayoutChanger.onSpannedEdited(null);
            }
            this.mSpannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void deletePage(int i) {
        RemovePageStep removePageStep = new RemovePageStep(this.mEngineBindContext, i);
        if (removePageStep.getSpannedMap() == null) {
            return;
        }
        List<SpannedData> dataListAll = removePageStep.getSpannedMap().getDataListAll();
        int[] iArr = new int[dataListAll.size()];
        if (!dataListAll.isEmpty()) {
            for (int i2 = 0; i2 < dataListAll.size(); i2++) {
                iArr[i2] = dataListAll.get(i2).getId();
            }
        }
        Log.i(this.TAG, "[deletePage] page: " + i + ", add removePageStep");
        this.mEngineBindContext.mLocalStepManager.addStep(removePageStep);
        if (this.mSpannedLayoutChanger != null) {
            SpannedEditObject spannedEditObject = this.mSpannedEditObject;
            if (spannedEditObject != null) {
                spannedEditObject.mSpannedData = null;
                this.mSpannedEditObject = null;
                this.mSpannedLayoutChanger.onSpannedEdited(null);
            }
            this.mSpannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void setTextBgColor(int i) {
        this.mTextBgColor = i;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPointSelect(float f, float f2) {
        doPointSelect(f, f2, false);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPointSelect(float f, float f2, boolean z) {
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            spannedSelectObject.finishSelect();
            this.mSpannedSelectObject = null;
        }
        SpannedData spannedData = this.mEngineBindContext.getSpannedData(f, f2, 11);
        if (spannedData == null) {
            spannedData = this.mEngineBindContext.getSpannedData(f, f2, 7);
        }
        if (spannedData == null) {
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedSelected(null, z);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(spannedData);
        SpannedSelectObject spannedSelectObject2 = new SpannedSelectObject(arrayList, this.mEngineBindContext);
        this.mSpannedSelectObject = spannedSelectObject2;
        SpannedLayoutChanger spannedLayoutChanger2 = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger2 != null) {
            spannedLayoutChanger2.onSpannedSelected(spannedSelectObject2, z);
            this.mSpannedLayoutChanger.updateSpannedLayout(false, arrayList);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPathSelect(Path path) {
        doPathSelect(path, false);
    }

    private PointF rectCenterToPointF(RectF rectF) {
        return new PointF((rectF.left + rectF.right) * 0.5f, (rectF.top + rectF.bottom) * 0.5f);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0123 A[LOOP:2: B:21:0x00b1->B:39:0x0123, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:61:0x011f A[SYNTHETIC] */
    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPathSelect(Path path, boolean z) {
        RectF rectF;
        Path path2 = path;
        ArrayList arrayList = new ArrayList();
        RectF rectF2 = new RectF();
        int i = 0;
        path2.computeBounds(rectF2, false);
        List<Integer> pageIndexes = getPageIndexes(rectF2);
        Collections.sort(pageIndexes);
        int iIntValue = pageIndexes.get(0).intValue();
        int i2 = 1;
        int iIntValue2 = pageIndexes.get(pageIndexes.size() - 1).intValue();
        if (iIntValue > 0) {
            pageIndexes.add(Integer.valueOf(iIntValue - 1));
        }
        if (iIntValue2 < this.mEngineBindContext.getSpannedMapCount()) {
            pageIndexes.add(Integer.valueOf(iIntValue2 + 1));
        }
        final ArrayList<SpannedMap> arrayList2 = new ArrayList();
        pageIndexes.forEach(new Consumer() { // from class: xc.richtext.operate.edit.SpannedEditOperator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m4667x2be7ee8(arrayList2, (Integer) obj);
            }
        });
        float f = -rectF2.top;
        float f2 = 0.0f;
        path2.offset(0.0f, f);
        for (SpannedMap spannedMap : arrayList2) {
            Iterator<SpannedData> it = spannedMap.getDataListAll().iterator();
            while (it.hasNext()) {
                SpannedData next = it.next();
                if (next.isVisible() && (next.getType() != 11 || !TextUtils.isEmpty(next.getText()))) {
                    RectF drawRectF = next.getDrawRectF(this.screenRenderProvider);
                    drawRectF.offset(f2, f);
                    float fCenterX = drawRectF.centerX();
                    float fCenterY = drawRectF.centerY();
                    int i3 = i;
                    while (i3 < 4) {
                        RectF rectF3 = new RectF();
                        if (i3 == 0) {
                            rectF3.set(drawRectF.left, drawRectF.top, drawRectF.left + 10.0f, drawRectF.bottom);
                        } else if (i3 == i2) {
                            rectF3.set(drawRectF.left, drawRectF.top, drawRectF.right, drawRectF.top + 10.0f);
                        } else if (i3 == 2) {
                            rectF3.set(drawRectF.right, drawRectF.top, drawRectF.right - 10.0f, drawRectF.bottom);
                        } else {
                            if (i3 != 3) {
                                rectF = rectF3;
                            } else {
                                rectF3.set(drawRectF.left, drawRectF.bottom, drawRectF.right, drawRectF.bottom - 10.0f);
                            }
                            if (spannedMap.hasIntersection(next, path2, rectF, fCenterX, fCenterY)) {
                                arrayList.add(next);
                                break;
                            } else {
                                i3++;
                                path2 = path;
                                i2 = 1;
                            }
                        }
                        rectF = rectF3;
                        spannedMap = spannedMap;
                        next = next;
                        if (spannedMap.hasIntersection(next, path2, rectF, fCenterX, fCenterY)) {
                            arrayList.add(next);
                            break;
                            break;
                        } else {
                            i3++;
                            path2 = path;
                            i2 = 1;
                        }
                    }
                }
                path2 = path;
                i = 0;
                i2 = 1;
                f2 = 0.0f;
            }
            path2 = path;
        }
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            spannedSelectObject.finishSelect();
            this.mSpannedSelectObject = null;
        }
        if (arrayList.isEmpty()) {
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedSelected(null, z);
                return;
            }
            return;
        }
        SpannedSelectObject spannedSelectObject2 = new SpannedSelectObject(arrayList, this.mEngineBindContext);
        this.mSpannedSelectObject = spannedSelectObject2;
        SpannedLayoutChanger spannedLayoutChanger2 = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger2 != null) {
            spannedLayoutChanger2.onSpannedSelected(spannedSelectObject2, z);
        }
    }

    /* JADX INFO: renamed from: lambda$doPathSelect$0$xc-richtext-operate-edit-SpannedEditOperator, reason: not valid java name */
    /* synthetic */ void m4667x2be7ee8(List list, Integer num) {
        list.add(this.mEngineBindContext.getSpannedMap(num.intValue()));
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doCopy(boolean z) {
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        List<SpannedData> listDoCopy = spannedSelectObject != null ? spannedSelectObject.doCopy() : null;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onCopyData(z, listDoCopy);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doCopyAndPast(float f, float f2) {
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            List<SpannedData> listDoCopy = spannedSelectObject.doCopy();
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onCopyData(listDoCopy);
            }
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doCut(boolean z) {
        List<SpannedData> listDoCopy;
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            listDoCopy = spannedSelectObject.doCopy();
            ArrayList arrayList = new ArrayList();
            int[] iArr = new int[this.mSpannedSelectObject.mSpannedDataList.size()];
            for (int i = 0; i < this.mSpannedSelectObject.mSpannedDataList.size(); i++) {
                SpannedData spannedData = this.mSpannedSelectObject.mSpannedDataList.get(i);
                spannedData.mSpannedStatus = 3;
                arrayList.add(spannedData);
                iArr[i] = spannedData.mId;
            }
            EngineBindContext engineBindContext = this.mSpannedSelectObject.mEngineBindContext;
            engineBindContext.mLocalStepManager.addStep(new DeleteStep(engineBindContext, arrayList));
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedSelectObject.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
            }
        } else {
            listDoCopy = null;
        }
        SpannedLayoutChanger spannedLayoutChanger2 = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger2 != null) {
            spannedLayoutChanger2.onCopyData(z, listDoCopy);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPast(List<SpannedData> list, float f, float f2) {
        Log.d(this.TAG, "onPaste " + list.size());
        if (list.isEmpty()) {
            return;
        }
        RectF rectF = new RectF();
        for (SpannedData spannedData : list) {
            if (rectF.isEmpty()) {
                rectF.set(spannedData.getRotatedBoundingBox(this.screenRenderProvider));
            } else {
                rectF.union(spannedData.getRotatedBoundingBox(this.screenRenderProvider));
            }
        }
        float f3 = this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            SpannedData spannedDataCopy = list.get(i).copy();
            arrayList.add(spannedDataCopy);
            RectF rotatedBoundingBox = spannedDataCopy.getRotatedBoundingBox(this.screenRenderProvider);
            float fCenterX = rotatedBoundingBox.centerX() - rectF.centerX();
            float fCenterY = rotatedBoundingBox.centerY() - rectF.centerY();
            float fCenterX2 = ((f - rotatedBoundingBox.centerX()) + fCenterX) / f3;
            float fCenterY2 = ((f2 - rotatedBoundingBox.centerY()) + fCenterY) / f3;
            RectF rectF2 = spannedDataCopy.getRectF();
            rectF2.offset(fCenterX2 / getDrawRatio(), fCenterY2 / getDrawRatio());
            spannedDataCopy.setSaveRect(rectF2);
            iArr[i] = spannedDataCopy.mId;
            spannedDataCopy.setRatioData(getDrawRatio());
            updateScaleInfo(spannedDataCopy);
            this.mEngineBindContext.getSpannedMap(getPageIndex(spannedDataCopy.mRatioRectF)).add(spannedDataCopy);
        }
        this.mEngineBindContext.mLocalStepManager.addStep(new InsertStep(this.mEngineBindContext, arrayList));
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
            SpannedSelectObject spannedSelectObject = new SpannedSelectObject(arrayList, this.mEngineBindContext);
            this.mSpannedSelectObject = spannedSelectObject;
            this.mSpannedLayoutChanger.onSpannedSelected(spannedSelectObject);
            this.mSpannedLayoutChanger.updateSpannedLayout(false, arrayList);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doEditClick(int i, float f, float f2) {
        SpannedLayoutModel spannedLayoutModel;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger == null || (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) == null || i <= 0) {
            return;
        }
        for (int i2 = 0; i2 < spannedLayoutModel.mSpannedEditLayout.getChildCount(); i2++) {
            View childAt = spannedLayoutModel.mSpannedEditLayout.getChildAt(i2);
            if ((childAt instanceof CsEditText) && ((SpannedData) childAt.getTag()).mId == i) {
                spannedLayoutModel.doTextClick((CsEditText) childAt);
            }
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void forceChildLayout(boolean z, int i, int i2, int i3, int i4) {
        SpannedLayoutModel spannedLayoutModel;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger == null || (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) == null) {
            return;
        }
        spannedLayoutModel.onLayout(z, i, i2, i3, i4);
        CsImageDrawModel csImageDrawModel = spannedLayoutModel.mCsImageDrawModel;
        for (BitmapBean bitmapBean : csImageDrawModel.mBitmapBeanList) {
            SpannedData spannedData = csImageDrawModel.mEngineBindContext.getSpannedData(bitmapBean.mId);
            if (spannedData != null) {
                bitmapBean.updateBean(spannedData.getDrawRectF(this.screenRenderProvider), spannedData.mDegrees, spannedData.mSpannedStatus);
            }
        }
        spannedLayoutModel.mSpannedEditLayout.invalidate();
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public float getEditTextCursorHeight() {
        SpannedLayoutModel spannedLayoutModel;
        SpannedEditObject spannedEditObject;
        SpannedData spannedData;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger == null || (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) == null || (spannedEditObject = spannedLayoutModel.mSpannedEditObject) == null || (spannedData = spannedEditObject.mSpannedData) == null) {
            return 0.0f;
        }
        RectF drawRectF = spannedData.getDrawRectF(this.screenRenderProvider);
        int i = spannedLayoutModel.mSpannedEditObject.mSpannedData.mId;
        View focusedChild = spannedLayoutModel.mSpannedEditLayout.getFocusedChild();
        if (!(focusedChild instanceof CsEditText) || !(focusedChild.getTag() instanceof SpannedData) || ((SpannedData) focusedChild.getTag()).mId != i) {
            return 0.0f;
        }
        CsEditText csEditText = (CsEditText) focusedChild;
        int selectionEnd = csEditText.getSelectionEnd();
        if (csEditText.getLayout() == null) {
            return 0.0f;
        }
        csEditText.getLayout().getLineBottom(csEditText.getLayout().getLineForOffset(selectionEnd));
        return drawRectF.bottom;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void setHightText(String str, int i, int i2, OnSpannedHightListener onSpannedHightListener) {
        SpannedLayoutModel spannedLayoutModel;
        CsEditViewModel model;
        this.mHightText = str;
        this.mHighColor = i;
        this.mHightAlpha = i2;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger == null || (spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel) == null) {
            return;
        }
        int childCount = spannedLayoutModel.mSpannedEditLayout.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = spannedLayoutModel.mSpannedEditLayout.getChildAt(i3);
            if ((childAt instanceof CsEditText) && (model = ((CsEditText) childAt).getModel()) != null) {
                if (TextUtils.isEmpty(str)) {
                    model.mRectFList.clear();
                    if (model.mCsEditText != null) {
                        model.mCsEditText.invalidate();
                    }
                } else {
                    model.mRectFList.clear();
                    List<RectF> listGenSearchTextPath = TextLayoutUtil.genSearchTextPath(model.mStaticLayout, this.mHightText);
                    if (!listGenSearchTextPath.isEmpty()) {
                        onSpannedHightListener.onSpannedHightChanged(model.mSpannedData, listGenSearchTextPath, model.mStaticLayout.getWidth(), model.mSpannedData.getDrawRectF(this.screenRenderProvider).width() / model.mStaticLayout.getWidth());
                    }
                    model.mRectFList.addAll(TextLayoutUtil.genSearchTextPath(model.mStaticLayout, model.mEngineBindContext.mSpannedEditOperator.mHightText));
                    if (model.mCsEditText != null) {
                        model.mCsEditText.invalidate();
                    }
                }
            }
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void resetViewData() {
        this.mSpannedLayoutChanger.mSpannedLayoutModel.mCsImageDrawModel.releaseAll();
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void forceReDraw() {
        this.mEngineBindContext.mScaleOperator.updateVisibleList(true);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public List<SpannedData> getAllSpanDataList(int i, RectF rectF) {
        return this.mEngineBindContext.getAllDataList(rectF, i);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public List<SpannedData> getAllSpanDataList(RectF rectF) {
        return this.mEngineBindContext.getAllDataList(rectF);
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public boolean isDark() {
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger == null) {
            return false;
        }
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider != null) {
            return screenRenderProvider.isCurrentPageBgDark();
        }
        SpannedLayoutModel spannedLayoutModel = spannedLayoutChanger.mSpannedLayoutModel;
        int iTextColorTransform = (spannedLayoutModel == null || spannedLayoutModel.mSpannedLayoutListener == null) ? -16777216 : spannedLayoutModel.mSpannedLayoutListener.textColorTransform(-16777216);
        return (-16777216 == iTextColorTransform || -16777216 == iTextColorTransform) ? false : true;
    }

    public void exitEditMode() {
        SpannedEditObject spannedEditObject = this.mSpannedEditObject;
        if (spannedEditObject != null) {
            int i = spannedEditObject.mSpannedData.mId;
            SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutChanger.mSpannedLayoutModel;
            if (spannedLayoutModel != null && !spannedLayoutModel.isEnableEdit) {
                View focusedChild = spannedLayoutModel.mSpannedEditLayout.getFocusedChild();
                if (focusedChild instanceof CsEditText) {
                    Object tag = focusedChild.getTag();
                    boolean z = tag instanceof SpannedData;
                    if ((z ? ((SpannedData) tag).getId() : -1) == i) {
                        focusedChild.setEnabled(false);
                    }
                    CsEditText csEditText = (CsEditText) focusedChild;
                    if (csEditText.getModel() != null) {
                        CsEditViewModel model = csEditText.getModel();
                        model.refreshStaticLayout();
                        float fMax = model.mSpannedData.mRatioRectF.top + Math.max(model.getHeight(model.mSpannedData) + model.mCsEditText.getPaddingTop() + model.mCsEditText.getPaddingBottom(), model.mSpannedData.mRatioRectF.height());
                        RectF rectF = model.mSpannedData.mRatioRectF;
                        rectF.bottom = fMax;
                        rectF.set(rectF);
                        model.mSpannedData.setRectF(rectF);
                    }
                    if (z) {
                        SpannedData spannedData = (SpannedData) tag;
                        if (TextUtils.isEmpty(spannedData.mText)) {
                            spannedLayoutModel.mSpannedEditLayout.removeView(focusedChild);
                            if (spannedData.o) {
                                spannedData.mSpannedStatus = 3;
                            }
                        }
                    }
                    spannedLayoutModel.mSpannedEditObject = null;
                }
            }
            this.mSpannedEditObject.mSpannedData = null;
            this.mSpannedEditObject = null;
        }
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedEdited(null);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditOperator
    public void doPointSelect(float f, float f2, int i) {
        SpannedSelectObject spannedSelectObject = this.mSpannedSelectObject;
        if (spannedSelectObject != null) {
            spannedSelectObject.finishSelect();
            this.mSpannedSelectObject = null;
        }
        SpannedData spannedData = this.mEngineBindContext.getSpannedData(f, f2, i);
        if (spannedData == null) {
            SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onSpannedSelected(null);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(spannedData);
        SpannedSelectObject spannedSelectObject2 = new SpannedSelectObject(arrayList, this.mEngineBindContext);
        this.mSpannedSelectObject = spannedSelectObject2;
        SpannedLayoutChanger spannedLayoutChanger2 = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger2 != null) {
            spannedLayoutChanger2.onSpannedSelected(spannedSelectObject2);
            this.mSpannedLayoutChanger.updateSpannedLayout(false, arrayList);
        }
    }

    private void updateScaleInfo(SpannedData spannedData) {
        ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
        PointF pointF = scaleParams.mTranslatePointF;
        spannedData.mTranslatePointF.set(pointF.x, pointF.y);
        spannedData.mScale = scaleParams.mScaleReal;
    }

    private void updateCanvasRectF(RectF rectF) {
        ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
        PointF pointF = scaleParams.mTranslatePointF;
        rectF.set(SpannedUtils.unTranslateAndScaleRectF(rectF, pointF.x, pointF.y, scaleParams.mScaleReal));
    }

    private RectF getDefaultScreenRect() {
        return new RectF(0.0f, 0.0f, this.mEngineBindContext.mScaleOperator.mWidth, this.mEngineBindContext.mScaleOperator.mHeight);
    }

    private void remapRangeRect(RectF rectF, RectF rectF2) {
        float f = this.mEngineBindContext.mScaleOperator.mWidth;
        float f2 = this.mEngineBindContext.mScaleOperator.mHeight;
        rectF.set(SpannedUtils.remapRange(rectF.left, 0.0f, f, rectF2.left, rectF2.right), SpannedUtils.remapRange(rectF.top, 0.0f, f2, rectF2.top, rectF2.bottom), SpannedUtils.remapRange(rectF.right, 0.0f, f, rectF2.left, rectF2.right), SpannedUtils.remapRange(rectF.bottom, 0.0f, f2, rectF2.top, rectF2.bottom));
    }

    private RectF getMinLimitRectf(RectF rectF, float f) {
        if (rectF.width() < f || rectF.height() < f) {
            if (rectF.width() < f) {
                rectF.right = rectF.left + f;
            }
            if (rectF.height() < f) {
                rectF.bottom = rectF.top + f;
            }
        }
        return rectF;
    }

    private int getPageIndex(RectF rectF) {
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider == null) {
            return 0;
        }
        int pageIndexByWorldSpacePoint = screenRenderProvider.getPageIndexByWorldSpacePoint(rectF.left, rectF.top);
        Log.d(this.TAG, "getPageIndex:" + pageIndexByWorldSpacePoint + " rectF:" + rectF);
        return pageIndexByWorldSpacePoint;
    }

    private List<Integer> getPageIndexes(RectF rectF) {
        PointF[] pointFArr = {new PointF(rectF.left, rectF.top), new PointF(rectF.right, rectF.top), new PointF(rectF.right, rectF.bottom), new PointF(rectF.left, rectF.bottom)};
        HashSet hashSet = new HashSet();
        for (int i = 0; i < 4; i++) {
            hashSet.add(Integer.valueOf(getPageIndex(pointFArr[i])));
        }
        return new ArrayList(hashSet);
    }

    private int getPageIndex(PointF pointF) {
        ScreenRenderProvider screenRenderProvider = this.screenRenderProvider;
        if (screenRenderProvider == null) {
            return 0;
        }
        int pageIndexByScreenSpacePoint = screenRenderProvider.getPageIndexByScreenSpacePoint(pointF.x, pointF.y);
        Log.d(this.TAG, "getPageIndex:" + pageIndexByScreenSpacePoint + " point:(" + pointF.x + ", " + pointF.y + ")");
        return pageIndexByScreenSpacePoint;
    }

    public void release() {
        this.screenRenderProvider = null;
        SpannedLayoutChanger spannedLayoutChanger = this.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.screenRenderProvider = null;
        }
    }
}
