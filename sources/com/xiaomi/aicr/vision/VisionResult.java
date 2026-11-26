package com.xiaomi.aicr.vision;

import android.util.Pair;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.vision.ocr.OCRRes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VisionResult {
    private static final String TAG = "VisionResult";
    private OCRRes.OCRResult mOcrResult;
    private VisionAttribute mRequestedAttr;
    int mStatus = 0;
    private List<VisionObject> mVisionObjects;

    public void setResult(OCRRes.OCRResult oCRResult, List<VisionObject> list) {
        this.mOcrResult = oCRResult;
        this.mVisionObjects = list;
    }

    public void setRequestedAttr(VisionAttribute visionAttribute) {
        this.mRequestedAttr = visionAttribute;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(Constants.STATUS_DEFINE status_define) {
        this.mStatus = status_define.ordinal();
    }

    List<Pair<String, float[]>> getAllText(VisionAttribute visionAttribute) {
        if (this.mStatus != Constants.STATUS_DEFINE.OK.ordinal()) {
            SmartLog.e(TAG, "fail to getAllText: " + Constants.getStatusInfo(this.mStatus));
            return null;
        }
        ArrayList arrayList = new ArrayList();
        List<VisionObject> list = this.mVisionObjects;
        if (list != null) {
            Iterator<VisionObject> it = list.iterator();
            while (it.hasNext()) {
                Text textAsText = it.next().asText();
                if (textAsText != null) {
                    arrayList.add(new Pair(textAsText.getText(), textAsText.getBox()));
                }
            }
        }
        return arrayList;
    }

    public List<VisionObject> getVisionObject(VisionAttribute visionAttribute) {
        ArrayList arrayList = new ArrayList();
        List<VisionObject> list = this.mVisionObjects;
        if (list != null) {
            arrayList.addAll(list);
        }
        return arrayList;
    }

    public List<VisionObject> getVisionObject() {
        return getVisionObject(this.mRequestedAttr);
    }

    public List<Pair<String, float[]>> getAllText() {
        return getAllText(this.mRequestedAttr);
    }

    public List<Pair<String, float[]>> getAllText(float[] fArr) {
        if (this.mStatus != Constants.STATUS_DEFINE.OK.ordinal()) {
            SmartLog.e(TAG, "fail to getAllText: " + Constants.getStatusInfo(this.mStatus));
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<VisionObject> it = this.mVisionObjects.iterator();
        while (it.hasNext()) {
            Text textAsText = it.next().asText();
            if (textAsText != null && contains(fArr, textAsText.getBox())) {
                arrayList.add(new Pair(textAsText.getText(), textAsText.getBox()));
            }
        }
        return arrayList;
    }

    public List<float[]> getAllLocation() {
        return getAllLocation(this.mRequestedAttr);
    }

    public List<float[]> getAllLocation(VisionAttribute visionAttribute) {
        if (this.mStatus != Constants.STATUS_DEFINE.OK.ordinal()) {
            SmartLog.e(TAG, "fail to getAllText: " + Constants.getStatusInfo(this.mStatus));
            return null;
        }
        ArrayList arrayList = new ArrayList();
        List<VisionObject> list = this.mVisionObjects;
        if (list != null) {
            Iterator<VisionObject> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getBox());
            }
        }
        return arrayList;
    }

    private boolean contains(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length || fArr.length != 8) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (fArr[i] < fArr2[i]) {
                return false;
            }
        }
        return true;
    }
}
