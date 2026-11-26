package xc.engine.utils;

import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: loaded from: classes6.dex */
public class EngineConfig {
    public boolean enablePenOnlyMode;
    public boolean enableRuler;
    public boolean enableSelectedContour;
    public ParamsEstimate estimateParams;
    public boolean enableSelect = false;
    public boolean isAiModel = false;
    public boolean canSelectInvisibleData = true;
    public int operatorMode = 2;
    public CurveProp penProp = new CurveProp(PenType.BALL, -16776961, 6.0f, 255);
    public DeleteProp deleteProp = new DeleteProp(DeleteType.POINT, 6.0f);
    private final int mDefaultColor = -12539393;
    public ShapeProp shapeProp = new ShapeProp(-12539393, 0.0f, 255);
    public boolean oneShape = false;
    public ParamsRecognizeShape oneShapeParams = new ParamsRecognizeShape(850, 17.0f, 51.0f);
    public boolean enableEstimate = true;

    public EngineConfig() {
        ParamsEstimate paramsEstimate = new ParamsEstimate();
        this.estimateParams = paramsEstimate;
        paramsEstimate.setTime(37);
    }
}
