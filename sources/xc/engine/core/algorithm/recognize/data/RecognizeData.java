package xc.engine.core.algorithm.recognize.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import xc.engine.core.algorithm.TouchPoint;
import xc.engine.core.algorithm.bean.SBean;

/* JADX INFO: loaded from: classes6.dex */
public abstract class RecognizeData {
    private ArrayList<TouchPoint> touchPoints;
    private Matrix matrix = new Matrix();
    private short curveType = 0;

    public static boolean isClose(short s) {
        if (s != 23 && s != 22) {
            switch (s) {
                case 14:
                default:
                    if (s != 11 && s != 14 && s != 24 && s != 25) {
                        return false;
                    }
                case 12:
                case 13:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    return true;
            }
        }
        return true;
    }

    public abstract Path getPathResult();

    public abstract boolean parseResult(String[] strArr);

    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x0031. Please report as an issue. */
    public static RecognizeData parseShape(String str) {
        SBean sBean;
        RecognizeData recognizeLineData;
        RecognizeData recognizeData = null;
        try {
            sBean = (SBean) new Gson().fromJson(str, SBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            sBean = null;
        }
        if (sBean != null && !TextUtils.isEmpty(sBean.getResult())) {
            String[] strArrSplit = sBean.getResult().split(",");
            int shape = sBean.getShape();
            if (shape != 0) {
                try {
                    switch (shape) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            recognizeLineData = new RecognizeLineData();
                            recognizeData = recognizeLineData;
                            break;
                        case 5:
                            recognizeLineData = new RecognizeCurveData();
                            recognizeData = recognizeLineData;
                            break;
                        case 6:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            recognizeLineData = new RecognizePolygonData();
                            recognizeData = recognizeLineData;
                            break;
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            recognizeLineData = new RecognizeParabolaData();
                            recognizeData = recognizeLineData;
                            break;
                        case 23:
                            recognizeLineData = new RecognizeCircleData();
                            recognizeData = recognizeLineData;
                            break;
                        case 24:
                            recognizeLineData = new RecognizeHeartData();
                            recognizeData = recognizeLineData;
                            break;
                        case 25:
                            recognizeLineData = new RecognizeCloudData();
                            recognizeData = recognizeLineData;
                            break;
                    }
                    if (recognizeData != null && recognizeData.parseResult(strArrSplit)) {
                        recognizeData.setRecognizeCurveType((short) shape);
                    }
                } catch (Exception unused) {
                }
            }
        }
        return recognizeData;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public short getRecognizeCurveType() {
        return this.curveType;
    }

    public void setRecognizeCurveType(short s) {
        this.curveType = s;
    }

    public void setPoints(ArrayList<TouchPoint> arrayList) {
        if (arrayList == null) {
            this.touchPoints = new ArrayList<>();
        } else {
            this.touchPoints = new ArrayList<>(arrayList);
        }
    }

    public ArrayList<TouchPoint> getPoints() {
        return this.touchPoints;
    }
}
