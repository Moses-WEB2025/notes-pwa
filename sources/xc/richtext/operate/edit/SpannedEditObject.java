package xc.richtext.operate.edit;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.EditText;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.scale.ScaleParams;
import xc.richtext.operate.step.DeleteStep;
import xc.richtext.operate.step.UpdateTextBoxStep;
import xc.richtext.utils.SpannedUtils;
import xc.richtext.utils.TextLayoutUtil;
import xc.richtext.view.SpannedLayoutChanger;

/* JADX INFO: loaded from: classes.dex */
public class SpannedEditObject implements ISpannedEditObject {
    private int mAction = 0;
    private float mEditParamOffsetX;
    private final WeakReference<EditText> mEditTextWeakReference;
    private final EngineBindContext mEngineBindContext;
    public int mLimitWidth;
    public RectF mRectF;
    public SpannedData mSpannedData;

    public SpannedEditObject(EngineBindContext engineBindContext, EditText editText, SpannedData spannedData) {
        this.mEngineBindContext = engineBindContext;
        this.mSpannedData = spannedData;
        this.mRectF = spannedData.getRatioRectF();
        this.mEditTextWeakReference = new WeakReference<>(editText);
        checkTextLimitWidth(spannedData.getTextSize());
    }

    @Override // xc.richtext.operate.edit.ISpannedObject
    public void startAction(int i) {
        this.mAction = i;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditObject
    public RectF getEditRectF() {
        if (isOffsetEmpty()) {
            this.mRectF = this.mSpannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider);
        }
        return this.mRectF;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditObject
    public void delete() {
        this.mSpannedData.mSpannedStatus = 3;
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mSpannedData);
        this.mEngineBindContext.mLocalStepManager.addStep(new DeleteStep(this.mEngineBindContext, arrayList));
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedEdited(null);
            spannedLayoutChanger.onSpannedDataChanged(new int[]{this.mSpannedData.mId}, true, false);
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditObject
    public void doEditParam(float f) {
        StaticLayout.Builder builderObtain;
        int height = (this.mEditTextWeakReference.get() == null || this.mEditTextWeakReference.get().getLayout() == null) ? 0 : this.mEditTextWeakReference.get().getLayout().getHeight();
        this.mEditParamOffsetX = f;
        RectF rectFTransformRect = SpannedUtils.transformRect(this.mSpannedData, 0.0f, 0.0f, f, 0.0f, this.mLimitWidth * this.mEngineBindContext.mScaleOperator.getDrawRatio() * this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal, height, this.mEngineBindContext.screenRenderProvider);
        this.mRectF = rectFTransformRect;
        float fWidth = (rectFTransformRect.width() / this.mEngineBindContext.mScaleOperator.getDrawRatio()) / this.mEngineBindContext.mScaleOperator.mScaleParams.mScaleReal;
        TextLayoutUtil textLayoutUtil = new TextLayoutUtil();
        float f2 = fWidth / 1000.0f;
        float f3 = this.mSpannedData.mTextSize;
        String str = this.mSpannedData.mText;
        if (TextUtils.isEmpty(str)) {
            builderObtain = null;
        } else {
            textLayoutUtil.mTextPaint.setTextSize(f3 / f2);
            textLayoutUtil.mTextPaint.setColor(this.mSpannedData.mTextColor);
            builderObtain = StaticLayout.Builder.obtain(str, 0, str.length(), textLayoutUtil.mTextPaint, 1000);
            builderObtain.setLineSpacing(0.0f, 1.0f).setUseLineSpacingFromFallbacks(true).setTextDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        }
        if (builderObtain != null) {
            StaticLayout staticLayoutBuild = builderObtain.build();
            float height2 = staticLayoutBuild.getHeight() * (new Rect((int) this.mRectF.left, (int) this.mRectF.top, (int) this.mRectF.right, (int) this.mRectF.bottom).width() / staticLayoutBuild.getWidth());
            RectF rectF = this.mRectF;
            rectF.bottom = rectF.top + height2;
        }
        onRefreshByIds(new int[]{this.mSpannedData.mId});
    }

    @Override // xc.richtext.operate.edit.ISpannedObject
    public void stopAction() {
        if (this.mAction == 1) {
            RectF rectF = new RectF(this.mSpannedData.getRectF());
            ScaleParams scaleParams = this.mEngineBindContext.mScaleOperator.mScaleParams;
            PointF pointF = scaleParams.mTranslatePointF;
            this.mSpannedData.mRatioRectF.set(SpannedUtils.unTranslateAndScaleRectF(this.mRectF, pointF.x, pointF.y, scaleParams.mScaleReal));
            SpannedData spannedData = this.mSpannedData;
            spannedData.setRectF(spannedData.mRatioRectF);
            float fWidth = new RectF(this.mSpannedData.getRectF()).width() - rectF.width();
            this.mEditParamOffsetX = 0.0f;
            this.mEngineBindContext.mLocalStepManager.addStep(new UpdateTextBoxStep(this.mEngineBindContext, this.mSpannedData, fWidth));
        }
        this.mAction = 0;
    }

    @Override // xc.richtext.operate.edit.ISpannedEditObject
    public void finishEdit() {
        this.mEngineBindContext.mSpannedEditOperator.exitEditMode();
        if (this.mEngineBindContext.screenRenderProvider.getMathSystem() != null) {
            this.mEngineBindContext.screenRenderProvider.getMathSystem().finishEdit();
        }
    }

    @Override // xc.richtext.operate.edit.ISpannedEditObject
    public SpannedData getEditData() {
        return this.mSpannedData;
    }

    public final boolean isOffsetEmpty() {
        return this.mEditParamOffsetX == 0.0f;
    }

    private void checkTextLimitWidth(float f) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f);
        this.mLimitWidth = (int) Math.ceil(textPaint.measureText("汉字"));
    }

    private void onRefreshByIds(int[] iArr) {
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onSpannedDataChanged(iArr, true, false);
        }
    }

    public EngineBindContext getEngineBindContext() {
        return this.mEngineBindContext;
    }
}
