package xc.engine.data.pagedata;

import android.graphics.PointF;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseData implements IData, Cloneable {
    public int mItemPositionIndex;
    public long mTime;
    public int mId = -1;
    public int mCount = -1;
    public short mTableFlag = -1;
    public int mDataState = 1;
    public int e = -1;

    public abstract void calcBound(float[] fArr);

    @Override // xc.engine.data.pagedata.IData
    public void clearPointsData() {
    }

    public abstract void doMove(float f, float f2);

    public abstract void doRotate(float f, float f2, float f3);

    public abstract void doScale(float f, float f2, float f3);

    @Override // xc.engine.data.pagedata.IData
    public int getDataSize() {
        return 0;
    }

    public float getDrawRatio() {
        return 0.0f;
    }

    @Override // xc.engine.data.pagedata.IData
    public IData readData(DataInputStream dataInputStream, int i) throws IOException {
        return null;
    }

    public void releasePointList() {
    }

    public abstract void scale(PointF pointF, PointF pointF2);

    @Override // xc.engine.data.pagedata.IData
    public void writeData(DataOutputStream dataOutputStream) throws IOException {
    }

    @Override // 
    public BaseData clone() throws CloneNotSupportedException {
        BaseData baseData = (BaseData) super.clone();
        if (baseData.mDataState != 3) {
            baseData.mDataState = 1;
        }
        return baseData;
    }

    public short getFlag() {
        return this.mTableFlag;
    }

    @Override // xc.engine.data.pagedata.IData
    public int getDataState() {
        return this.mDataState;
    }

    public void setDataState(int i) {
        if (i == 2) {
            this.mDataState = 2;
        } else if (i == 3) {
            this.mDataState = 3;
        } else {
            this.mDataState = 1;
        }
    }

    @Override // xc.engine.data.pagedata.IData
    public long getTime() {
        return this.mTime;
    }
}
