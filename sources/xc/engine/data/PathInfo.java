package xc.engine.data;

import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.data.shape.ShapeType;
import xc.engine.data.shape.StrokeType;
import xc.engine.storage.ent.file_io.InkEntFileIO;

/* JADX INFO: loaded from: classes6.dex */
public class PathInfo implements Cloneable {
    private static final String TAG = "PathInfo";
    public Map<Integer, List<BaseData>> mAllDataList;

    public PathInfo() {
        this.mAllDataList = new ConcurrentHashMap(0);
    }

    public PathInfo(Map<Integer, List<BaseData>> map) {
        this.mAllDataList = new ConcurrentHashMap(map);
    }

    public static PathInfo loadEntFile(String str, DataInterfaceSet dataInterfaceSet, int i) {
        PathInfo pathInfo = new PathInfo();
        Log.i(TAG, String.format("--debug-- [loadEntFile] page[%d] path[%s]", Integer.valueOf(i), str));
        if (!TextUtils.isEmpty(str)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                try {
                    DataSet dataSet = new DataSet();
                    InkEntFileIO inkEntFileIO = new InkEntFileIO(null);
                    inkEntFileIO.mDataInterfaceSet = dataInterfaceSet;
                    inkEntFileIO.read(dataSet, new DataInputStream(bufferedInputStream), i);
                    pathInfo.getAllDataList().putAll(dataSet.mAllDataMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (FileNotFoundException unused) {
                return null;
            }
        }
        return pathInfo;
    }

    static /* synthetic */ List lambda$serialize$0(Integer num) {
        return new ArrayList();
    }

    public void serialize(DataOutputStream dataOutputStream, int i) throws IOException {
        boolean z;
        List<BaseData> listComputeIfAbsent = this.mAllDataList.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.PathInfo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PathInfo.lambda$serialize$0((Integer) obj);
            }
        });
        Iterator<BaseData> it = listComputeIfAbsent.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().getDataState() != 3) {
                    z = false;
                    break;
                }
            } else {
                z = true;
                break;
            }
        }
        dataOutputStream.writeBoolean(z);
        dataOutputStream.writeInt(this.mAllDataList.size());
        for (BaseData baseData : listComputeIfAbsent) {
            int dataSize = baseData.getDataSize();
            if (dataSize != 0) {
                dataOutputStream.writeInt(dataSize);
                int size = dataOutputStream.size();
                baseData.writeData(dataOutputStream);
                baseData.clearPointsData();
                if (dataOutputStream.size() - size != dataSize) {
                    throw new IOException("serialize data size error");
                }
            }
        }
        this.mAllDataList.clear();
    }

    public SparseArray<DataCurve> unSerialize(DataInputStream dataInputStream, int i) throws IOException {
        int i2;
        dataInputStream.readBoolean();
        int i3 = dataInputStream.readInt();
        SparseArray<DataCurve> sparseArray = new SparseArray<>();
        int i4 = 0;
        while (i4 < i3) {
            dataInputStream.readInt();
            short s = dataInputStream.readShort();
            if (s == 1) {
                CurveProp curveProp = new CurveProp(PenType.getPenType(dataInputStream.readInt()), dataInputStream.readInt(), dataInputStream.readFloat(), dataInputStream.readInt());
                curveProp.setPenColorWheel(dataInputStream.readInt());
                curveProp.setSensitivity(dataInputStream.readFloat());
                curveProp.setTipSize(dataInputStream.readFloat());
                DataCurve dataCurve = new DataCurve(curveProp, 0L);
                dataCurve.pageIndex = i;
                dataCurve.mId = dataInputStream.readInt();
                dataCurve.mCount = dataInputStream.readInt();
                dataCurve.mTableFlag = dataInputStream.readShort();
                dataCurve.mDrawRatio = dataInputStream.readFloat();
                dataCurve.setDataState(dataInputStream.readInt());
                dataCurve.e = dataInputStream.readInt();
                dataCurve.mTime = dataInputStream.readLong();
                dataCurve.mItemPositionIndex = dataInputStream.readInt();
                dataCurve.mCurveType = dataInputStream.readShort();
                dataCurve.mRotateDegrees = dataInputStream.readFloat();
                dataCurve.r = dataInputStream.readBoolean();
                dataCurve.e = -1;
                dataCurve.mPenTypeValue = dataInputStream.readInt();
                float f = dataInputStream.readFloat();
                float f2 = dataInputStream.readFloat();
                if (f != -1.0f || f2 != -1.0f) {
                    dataCurve.mStartPoint = new PointF(f, f2);
                }
                dataCurve.mRect.set(new RectF(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat()));
                dataCurve.mScaleRate = dataInputStream.readFloat();
                float f3 = dataInputStream.readFloat();
                float f4 = dataInputStream.readFloat();
                PointF pointF = dataCurve.mBasePoint;
                pointF.x = f3;
                pointF.y = f4;
                int i5 = dataInputStream.readInt() * 40;
                if (i5 > 0) {
                    byte[] bArr = new byte[i5];
                    if (dataInputStream.read(bArr) != i5) {
                        throw new IOException("unSerialize point error");
                    }
                    DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
                    int i6 = i5 / 40;
                    dataCurve.clearPoints(dataCurve.mSavePoints);
                    float[] fArr = new float[i6];
                    float[] fArr2 = new float[i6];
                    float[] fArr3 = new float[i6];
                    float[] fArr4 = new float[i6];
                    float[] fArr5 = new float[i6];
                    float[] fArr6 = new float[i6];
                    float[] fArr7 = new float[i6];
                    long[] jArr = new long[i6];
                    short[] sArr = new short[i6];
                    int i7 = 0;
                    while (i7 < i6) {
                        fArr[i7] = dataInputStream2.readFloat();
                        fArr2[i7] = dataInputStream2.readFloat();
                        fArr3[i7] = dataInputStream2.readFloat();
                        fArr4[i7] = dataInputStream2.readFloat();
                        float[] fArr8 = fArr6;
                        jArr[i7] = dataInputStream2.readLong() - dataCurve.mTime;
                        fArr5[i7] = dataInputStream2.readFloat();
                        fArr8[i7] = dataInputStream2.readFloat();
                        fArr7[i7] = dataInputStream2.readFloat();
                        sArr[i7] = 1;
                        i7++;
                        fArr6 = fArr8;
                        i4 = i4;
                    }
                    i2 = i4;
                    dataCurve.insertPoints(dataCurve.mSavePoints, i6, fArr, fArr2, fArr3, fArr4, jArr, fArr5, fArr6, fArr7, sArr);
                    dataInputStream2.close();
                } else {
                    i2 = i4;
                }
                if (this.mAllDataList == null) {
                    this.mAllDataList = new ConcurrentHashMap();
                }
                Log.i(TAG, String.format("--debug-- [unSerialize] index[%d] dataCurve index[%d]", Integer.valueOf(i), Integer.valueOf(dataCurve.pageIndex)));
                this.mAllDataList.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.PathInfo$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return PathInfo.lambda$unSerialize$1((Integer) obj);
                    }
                }).add(dataCurve);
                sparseArray.put(dataCurve.mCount, dataCurve);
            } else {
                i3 = i3;
                i2 = i4;
                if (s == 28) {
                    byte b = dataInputStream.readByte();
                    byte b2 = dataInputStream.readByte();
                    int i8 = dataInputStream.readInt();
                    float f5 = dataInputStream.readFloat();
                    int i9 = dataInputStream.readInt();
                    int i10 = dataInputStream.readInt();
                    int i11 = dataInputStream.readInt();
                    int i12 = dataInputStream.readInt();
                    int i13 = dataInputStream.readInt();
                    ShapeProp shapeProp = new ShapeProp(i8, f5, i9);
                    shapeProp.setShapeType(ShapeType.getShapeType(b));
                    shapeProp.setStrokeType(StrokeType.getStrokeType(b2));
                    shapeProp.setPenColorWheel(i10);
                    shapeProp.setFillColor(i11);
                    shapeProp.setFillAlpha(i12);
                    shapeProp.setFillColorWheel(i13);
                    DataShape dataShape = new DataShape(shapeProp, 0L);
                    dataShape.mId = dataInputStream.readInt();
                    dataShape.mCount = dataInputStream.readInt();
                    dataShape.mDrawRatio = dataInputStream.readFloat();
                    dataShape.setDataState(dataInputStream.readInt());
                    dataShape.mCurveType = dataInputStream.readShort();
                    dataShape.mTime = dataInputStream.readLong();
                    dataShape.mRotateDegrees = dataInputStream.readFloat();
                    dataShape.mItemPositionIndex = dataInputStream.readInt();
                    dataShape.setFilletPointF(dataInputStream.readFloat(), dataInputStream.readFloat());
                    dataShape.mAnchorPoint4[0] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[1] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[2] = dataInputStream.readFloat();
                    dataShape.mAnchorPoint4[3] = dataInputStream.readFloat();
                    dataShape.mScaleRate = dataInputStream.readFloat();
                    float f6 = dataInputStream.readFloat();
                    float f7 = dataInputStream.readFloat();
                    PointF pointF2 = dataShape.mBasePoint;
                    pointF2.x = f6;
                    pointF2.y = f7;
                    dataShape.readData(dataInputStream, dataInputStream.readInt() * 8);
                    if (this.mAllDataList == null) {
                        this.mAllDataList = new ConcurrentHashMap();
                    }
                    dataShape.calculateBoundingRect();
                    Log.i(TAG, String.format("--debug-- [unSerialize] index[%d] dataShape index[%d]", Integer.valueOf(i), Integer.valueOf(dataShape.pageIndex)));
                    this.mAllDataList.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.PathInfo$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return PathInfo.lambda$unSerialize$2((Integer) obj);
                        }
                    }).add(dataShape);
                    sparseArray.put(dataShape.mCount, dataShape);
                }
            }
            i4 = i2 + 1;
            i3 = i3;
        }
        return sparseArray;
    }

    static /* synthetic */ List lambda$unSerialize$1(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$unSerialize$2(Integer num) {
        return new ArrayList();
    }

    public void resetData(PathInfo pathInfo) {
        if (this.mAllDataList == null) {
            this.mAllDataList = new ConcurrentHashMap(0);
        }
        this.mAllDataList.clear();
        if (pathInfo != null) {
            this.mAllDataList.putAll(pathInfo.mAllDataList);
        }
    }

    public Map<Integer, List<BaseData>> getAllDataList() {
        return this.mAllDataList;
    }

    public PathInfo clone() throws CloneNotSupportedException {
        PathInfo pathInfo = (PathInfo) super.clone();
        pathInfo.mAllDataList = new ConcurrentHashMap();
        for (int i = 0; i < this.mAllDataList.size(); i++) {
            Iterator<BaseData> it = this.mAllDataList.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.PathInfo$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PathInfo.lambda$clone$3((Integer) obj);
                }
            }).iterator();
            while (it.hasNext()) {
                pathInfo.mAllDataList.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.data.PathInfo$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return PathInfo.lambda$clone$4((Integer) obj);
                    }
                }).add(it.next().clone());
            }
        }
        return pathInfo;
    }

    static /* synthetic */ List lambda$clone$3(Integer num) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$clone$4(Integer num) {
        return new ArrayList();
    }
}
