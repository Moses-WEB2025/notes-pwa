package com.miui.libhandwritingpage.view.select;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import java.util.ArrayList;
import java.util.List;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.edit.select.SRectF;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbsSelectView extends View {
    public void cancelSelect() {
    }

    public void cancelSelectAction(float f, float f2) {
    }

    public void destroy() {
    }

    public int findSelectTypeBy(float f, float f2) {
        return 0;
    }

    public void finishSelect() {
    }

    public List<SpannedData> getAllFromAISpanned() {
        return null;
    }

    public List<SpannedData> getAllFromInsertSpanned() {
        return null;
    }

    public List<SpannedData> getAllSelectImageSpanned() {
        return null;
    }

    public CanvasMaskEntity getBitMapAndMask(boolean z, boolean z2, float f, float f2, int i) {
        return null;
    }

    public Bitmap getBitmap(boolean z) {
        return null;
    }

    public Bitmap getBitmap(boolean z, boolean z2) {
        return null;
    }

    public Bitmap getBitmapBySize(boolean z, int i) {
        return null;
    }

    public SpannedData getClickImage() {
        return null;
    }

    public int getCount() {
        return 0;
    }

    public RectF getRectF() {
        return null;
    }

    public void getSelectedOffset(PointF pointF, PointF pointF2) {
    }

    public int getType() {
        return -1;
    }

    public boolean hasCopySpannedBitmap(List<SpannedData> list) {
        return false;
    }

    public boolean isFromAi() {
        return false;
    }

    public boolean isPastLimit(float f, float f2, byte[] bArr, List<SpannedData> list) {
        return false;
    }

    public boolean isSelectDouble() {
        return false;
    }

    public boolean isSelectRectNotEmpty() {
        return false;
    }

    public void moveSelectPath(float f, float f2, Path path) {
    }

    public void onCopy(boolean z) {
    }

    public void onCopyPaste(boolean z) {
    }

    public void onCrop() {
    }

    public void onCropCancel() {
    }

    public void onCropFinish() {
    }

    public void onCut(boolean z) {
    }

    public void onDelete(boolean z) {
    }

    public void onDelete(boolean z, boolean z2) {
    }

    public void onDoingAction(int i, SRectF sRectF) {
    }

    public void onObjectSelected(SRectF sRectF, List<DataCurve> list) {
    }

    public void onObjectSelected(SRectF sRectF, List<DataCurve> list, boolean z) {
    }

    public void onPaste(float f, float f2, byte[] bArr, List<SpannedData> list, boolean z) {
    }

    public void onSelectLimit() {
    }

    public void onSelectedClick() {
    }

    public void onSelectedHashCode(int i) {
    }

    public void onStartPasteFromCopy(List<SpannedData> list) {
    }

    public void onStepCount(int i) {
    }

    public void onStopAction() {
    }

    public void onUp(MotionEvent motionEvent) {
    }

    public void replaceView(List<TouchPosition> list) {
    }

    public void setAction(int i) {
    }

    public void setSelectedOffset(PointF pointF, PointF pointF2) {
    }

    public void spannedCancel() {
    }

    public AbsSelectView(Context context) {
        super(context);
    }

    public List<TouchPosition> getTouchPositionList() {
        return new ArrayList();
    }

    public List<SpannedData> getAllSpanByRectF(RectF rectF) {
        return new ArrayList();
    }

    public String getAiPath() {
        return "";
    }
}
