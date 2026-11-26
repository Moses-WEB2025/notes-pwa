package xc.engine.storage.ent.file_io;

import android.graphics.RectF;
import android.os.SystemClock;
import android.util.Log;
import com.miui.handwriting.common.view.check.CheckStatus;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CsBufferBean;
import xc.engine.data.CsPoint;
import xc.engine.data.CurveProp;
import xc.engine.data.DataInterfaceSet;
import xc.engine.data.IDataSwapListener;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.shape.ShapeProp;
import xc.engine.data.shape.ShapeType;
import xc.engine.math.iink.manager.MathResultCurveManager;
import xc.engine.storage.ent.tools.SinglePageEntTools;

/* JADX INFO: loaded from: classes6.dex */
public class InkEntFileIO {
    private static final String TAG = "InkEntFileIO";
    public DataInterfaceSet mDataInterfaceSet;
    public PenEngine mPenEngine;
    private Set<String> syncedMathBlockIdsInCurrentRead = new HashSet();

    public InkEntFileIO(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    private IDataSwapListener getIDataSwapListener() {
        PenEngine penEngine = this.mPenEngine;
        if (penEngine != null) {
            return penEngine.mDataAccess.mIDataSwapListener;
        }
        DataInterfaceSet dataInterfaceSet = this.mDataInterfaceSet;
        if (dataInterfaceSet != null) {
            return dataInterfaceSet.getDataSwapListener();
        }
        return null;
    }

    private String extractNumericIdFromString(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.contains(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            str = str.substring(str.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1);
        }
        try {
            Long.parseLong(str);
            return str;
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:138:0x04c1 A[Catch: Exception -> 0x0529, TryCatch #2 {Exception -> 0x0529, blocks: (B:115:0x0442, B:123:0x0458, B:126:0x047b, B:128:0x047e, B:130:0x0486, B:131:0x048b, B:133:0x0491, B:138:0x04c1, B:135:0x049f, B:137:0x04a5, B:139:0x04e1, B:142:0x0503, B:143:0x0521, B:144:0x0525), top: B:210:0x0442 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x0296  */
    public void read(DataSet dataSet, DataInputStream dataInputStream, int i, boolean z) throws IOException {
        int i2;
        int i3;
        float f;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        boolean z2;
        int i4;
        int i5;
        RectF rectF;
        boolean z3;
        int i6;
        int size;
        float f2;
        RectF rectF2;
        this.syncedMathBlockIdsInCurrentRead.clear();
        HeadInfo headInfo = getHeadInfo(dataInputStream);
        BaseSize baseSize = getBaseSize(dataInputStream, headInfo);
        int i7 = dataInputStream.readInt();
        int i8 = i7 - 4;
        byte[] bArr = new byte[i8];
        int i9 = dataInputStream.read(bArr);
        if (i9 != i8) {
            throw new IOException("read 1 error " + i9);
        }
        int i10 = baseSize.format_head_publicSize + i7;
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        dataInputStream2.readInt();
        int i11 = dataInputStream2.readInt();
        if (i11 > i10) {
            dataInputStream.skipBytes(i11 - i10);
            i10 = i11;
        }
        dataInputStream2.close();
        int i12 = dataInputStream.readInt();
        int i13 = i12 - 4;
        byte[] bArr2 = new byte[i13];
        int i14 = dataInputStream.read(bArr2);
        if (i14 != i13) {
            throw new IOException("read 2 error " + i14);
        }
        DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bArr2));
        int i15 = dataInputStream3.readInt();
        ArrayList arrayList4 = new ArrayList(i15);
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        float wSOffsetX = InkEntFileIOHelper.getWSOffsetX(i);
        float wSOffsetY = InkEntFileIOHelper.getWSOffsetY(i);
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        ArrayList arrayList7 = arrayList6;
        ArrayList arrayList8 = arrayList5;
        Log.i(TAG, String.format("read: curPage[%d] pageOffset[%f, %f] worldOffset[%f, %f]", Integer.valueOf(i), Float.valueOf(offsetX), Float.valueOf(offsetY), Float.valueOf(wSOffsetX), Float.valueOf(wSOffsetY)));
        int i16 = i10 + i12;
        int i17 = 0;
        int i18 = 0;
        while (i17 < i15) {
            int i19 = dataInputStream3.readInt();
            i17++;
            if (i19 > i16) {
                dataInputStream.skipBytes(i19 - i16);
                i16 = i19;
            }
            int i20 = dataInputStream.readInt();
            short s = dataInputStream.readShort();
            short s2 = dataInputStream.readShort();
            int i21 = i16 + 8;
            if (s == 1) {
                i2 = i15;
                int i22 = i18;
                ArrayList arrayList9 = arrayList4;
                DataCurve dataCurve = new DataCurve(new CurveProp(PenType.getPenType(s2), -16776961, 6.0f, 255), 0L);
                dataCurve.mCount = dataSet.getCount();
                dataCurve.mId = i17;
                dataCurve.mPenTypeValue = s2;
                int i23 = i19 + i20;
                byte[] bArr3 = new byte[baseSize.itemCurveSize];
                int i24 = dataInputStream.read(bArr3);
                if (i24 != baseSize.itemCurveSize) {
                    throw new IOException("read 3 error " + i24);
                }
                int i25 = i21 + baseSize.itemCurveSize;
                DataInputStream dataInputStream4 = new DataInputStream(new ByteArrayInputStream(bArr3));
                dataInputStream4.readByte();
                dataCurve.mCurveType = dataInputStream4.readByte();
                dataCurve.mCurveProp.setPenColor(dataInputStream4.readInt());
                dataCurve.mCurveProp.setPenSize(dataInputStream4.readFloat());
                dataInputStream4.readShort();
                dataCurve.mRotateDegrees = dataInputStream4.readFloat();
                dataCurve.mCurveProp.setPenAlpha(dataInputStream4.readInt());
                dataCurve.pageIndex = i;
                dataCurve.renderOffsetX = wSOffsetX;
                dataCurve.renderOffsetY = wSOffsetY;
                dataCurve.mId = dataInputStream4.readInt();
                if (dataCurve.mId == -9999) {
                    int i26 = i23 - i25;
                    if (i26 > 0) {
                        dataInputStream.skipBytes(i26);
                    }
                    dataInputStream4.close();
                    i16 = i23;
                    arrayList4 = arrayList9;
                    i15 = i2;
                    i18 = i22;
                } else {
                    dataCurve.mCurveProp.setPenColorWheel(dataInputStream4.readInt());
                    if (headInfo.dataVersion >= 13) {
                        dataCurve.mCurveProp.setSensitivity(dataInputStream4.readFloat());
                        dataCurve.mCurveProp.setTipSize(dataInputStream4.readFloat());
                    }
                    if (headInfo.dataVersion >= 14) {
                        dataCurve.mItemPositionIndex = dataInputStream4.readInt();
                        z2 = dataInputStream4.readShort() == 1;
                        if (z2) {
                            boolean z4 = z2;
                            DataShape dataShape = new DataShape(new ShapeProp(dataCurve.mCurveProp), dataCurve.mTime);
                            dataShape.mId = dataCurve.mId;
                            dataShape.mCount = dataCurve.mCount;
                            dataShape.mPenTypeValue = dataCurve.mPenTypeValue;
                            dataShape.mRotateDegrees = dataCurve.mRotateDegrees;
                            dataShape.mItemPositionIndex = dataCurve.mItemPositionIndex;
                            dataShape.pageIndex = i;
                            dataShape.renderOffsetX = wSOffsetX;
                            dataShape.renderOffsetY = wSOffsetY;
                            dataCurve = dataShape;
                            z2 = z4;
                        }
                    } else {
                        z2 = false;
                    }
                    try {
                        dataCurve.isMathResult = dataInputStream4.readBoolean();
                        while (true) {
                            if (i4 < i23 && baseSize.eachPointSize + i4 <= i23) {
                                int i27 = i23 - i4;
                                z3 = z2;
                                if (i27 != 20 && i27 != 24) {
                                    if (i27 < baseSize.eachPointSize) {
                                        if (i27 != 20 && i27 != 24) {
                                            Log.w(TAG, String.format("[read] unexpected remaining bytes: %d for curve mId[%d], expected strokeId(%d) or strokeId+mathBlockId(%d)", Integer.valueOf(i27), Integer.valueOf(dataCurve.mId), 20, 24));
                                            break;
                                        }
                                        break;
                                    }
                                    int i28 = i17;
                                    int i29 = i4;
                                    byte[] bArr4 = new byte[baseSize.eachPointSize];
                                    int i30 = dataInputStream.read(bArr4);
                                    if (i30 != baseSize.eachPointSize) {
                                        throw new IOException("read 4 error " + i30);
                                    }
                                    int i31 = i29 + baseSize.eachPointSize;
                                    DataInputStream dataInputStream5 = new DataInputStream(new ByteArrayInputStream(bArr4));
                                    float f3 = dataInputStream5.readFloat() + offsetX;
                                    float f4 = dataInputStream5.readFloat() + offsetY;
                                    float f5 = dataInputStream5.readFloat();
                                    float f6 = dataInputStream5.readFloat();
                                    long j = dataInputStream5.readLong();
                                    if (i5 == 0) {
                                        dataCurve.mTime = j;
                                    }
                                    long j2 = j - dataCurve.mTime;
                                    float f7 = dataInputStream5.readFloat();
                                    float f8 = dataInputStream5.readFloat();
                                    if (headInfo.dataVersion == 17) {
                                        dataInputStream5.readFloat();
                                        if (baseSize.eachPointSize == 40) {
                                            f2 = dataInputStream5.readFloat();
                                        } else {
                                            f2 = 0.0f;
                                        }
                                    } else {
                                        f2 = 0.0f;
                                    }
                                    float f9 = f2;
                                    CsPoint csPoint = new CsPoint(f3, f4, f5, f6, j2, f7, f8, f9);
                                    dataCurve.insertPoint(dataCurve.mSavePoints, f3, f4, f5, f6, f7, f8, j2, (short) 1, f9);
                                    if (i5 != 2 || z3) {
                                        rectF2 = rectF;
                                    } else {
                                        ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                                        CsPoint csPoint2 = new CsPoint(curvePointList.get(0));
                                        CsPoint csPoint3 = new CsPoint(curvePointList.get(1));
                                        rectF2 = new RectF(Math.min(csPoint2.getX(), csPoint3.getX()), Math.min(csPoint2.getY(), csPoint3.getY()), Math.max(csPoint2.getX(), csPoint3.getX()), Math.max(csPoint2.getY(), csPoint3.getY()));
                                    }
                                    if (rectF2 != null) {
                                        rectF2.union(csPoint.getX(), csPoint.getY());
                                    }
                                    i5++;
                                    dataInputStream5.close();
                                    rectF = rectF2;
                                    i4 = i31;
                                    wSOffsetX = wSOffsetX;
                                    dataInputStream4 = dataInputStream4;
                                    z2 = z3;
                                    i17 = i28;
                                }
                                break;
                                break;
                            }
                            z3 = z2;
                            break;
                        }
                    } catch (Exception unused) {
                        dataCurve.isMathResult = false;
                    }
                    i4 = i25;
                    i5 = 0;
                    rectF = null;
                    DataInputStream dataInputStream6 = dataInputStream4;
                    f = wSOffsetX;
                    if (z3) {
                        setShapeInfo(dataInputStream, baseSize, i, (DataShape) dataCurve);
                        i6 = i4 + baseSize.itemShapeSize;
                    } else {
                        i6 = i4;
                    }
                    if (i6 < i23 && i23 - i6 >= 20) {
                        try {
                            byte[] bArr5 = new byte[20];
                            if (dataInputStream.read(bArr5) == 20) {
                                String str = new String(bArr5, "UTF-8");
                                int length = str.length();
                                for (int length2 = str.length() - 1; length2 >= 0; length2--) {
                                    if (str.charAt(length2) != 0) {
                                        length = length2 + 1;
                                        break;
                                    }
                                }
                                String strSubstring = str.substring(0, length);
                                if (foundNonZero(strSubstring.trim())) {
                                    if (strSubstring.length() < 20) {
                                        StringBuilder sb = new StringBuilder(strSubstring);
                                        for (int i32 = 20; sb.length() < i32; i32 = 20) {
                                            sb.append('0');
                                        }
                                        strSubstring = sb.toString();
                                        Log.d(TAG, String.format("[read] padded strokeId for curve mId[%d] to %d chars", Integer.valueOf(dataCurve.mId), 20));
                                    }
                                    dataCurve.strokeId = strSubstring;
                                    i6 += 20;
                                    Log.d(TAG, String.format("[read] load curve mId[%d] strokeId[%s] length[%d]", Integer.valueOf(dataCurve.mId), dataCurve.strokeId, Integer.valueOf(dataCurve.strokeId.length())));
                                } else {
                                    dataCurve.strokeId = null;
                                    i6 += 20;
                                }
                            } else {
                                dataCurve.strokeId = null;
                            }
                        } catch (Exception e) {
                            Log.w(TAG, String.format("[read] failed to read strokeId for curve mId[%d]: %s", Integer.valueOf(dataCurve.mId), e.getMessage()));
                            dataCurve.strokeId = null;
                        }
                    } else {
                        dataCurve.strokeId = null;
                    }
                    if (i6 < i23 && i23 - i6 >= 4) {
                        try {
                            int i33 = dataInputStream.readInt();
                            i6 += 4;
                            if (i33 == -1 || i33 < 0) {
                                dataCurve.mathBlockId = null;
                            } else if (i33 > 10 && i33 <= 999999999) {
                                dataCurve.mathBlockId = String.valueOf(i33);
                                Log.d(TAG, String.format("[read] load curve mId[%d] mathBlockId[%s] (int format)", Integer.valueOf(dataCurve.mId), dataCurve.mathBlockId));
                            } else if (i33 > 0 && i33 <= 10 && i23 - i6 >= i33) {
                                byte[] bArr6 = new byte[i33];
                                if (dataInputStream.read(bArr6) == i33) {
                                    String str2 = new String(bArr6, "UTF-8");
                                    try {
                                        if (Integer.parseInt(str2) != i33 || str2.length() != String.valueOf(i33).length()) {
                                            if (!str2.isEmpty()) {
                                                dataCurve.mathBlockId = str2;
                                                i6 += i33;
                                                Log.d(TAG, String.format("[read] load curve mId[%d] mathBlockId[%s] (UTF-8 format, old file)", Integer.valueOf(dataCurve.mId), dataCurve.mathBlockId));
                                            } else {
                                                dataCurve.mathBlockId = String.valueOf(i33);
                                                Log.w(TAG, String.format("[read] ambiguous format for curve mId[%d], value=%d, treating as new format (may cause subsequent read error)", Integer.valueOf(dataCurve.mId), Integer.valueOf(i33)));
                                            }
                                        } else {
                                            dataCurve.mathBlockId = String.valueOf(i33);
                                            Log.w(TAG, String.format("[read] ambiguous format for curve mId[%d], value=%d, treating as new format (may cause subsequent read error)", Integer.valueOf(dataCurve.mId), Integer.valueOf(i33)));
                                        }
                                    } catch (NumberFormatException unused2) {
                                    }
                                } else {
                                    dataCurve.mathBlockId = String.valueOf(i33);
                                    Log.d(TAG, String.format("[read] load curve mId[%d] mathBlockId[%s] (int format, old format read failed)", Integer.valueOf(dataCurve.mId), dataCurve.mathBlockId));
                                }
                            } else if (i33 > 0 && i33 <= 10) {
                                dataCurve.mathBlockId = String.valueOf(i33);
                                Log.d(TAG, String.format("[read] load curve mId[%d] mathBlockId[%s] (int format, value<=10)", Integer.valueOf(dataCurve.mId), dataCurve.mathBlockId));
                            } else {
                                dataCurve.mathBlockId = null;
                            }
                        } catch (Exception e2) {
                            Log.w(TAG, String.format("[read] failed to read mathBlockId for curve mId[%d]: %s", Integer.valueOf(dataCurve.mId), e2.getMessage()));
                            dataCurve.mathBlockId = null;
                        }
                    } else {
                        dataCurve.mathBlockId = null;
                    }
                    RectF rectF3 = rectF;
                    if (rectF3 != null) {
                        dataCurve.initRectF(rectF3);
                    } else {
                        dataCurve.calculateBoundingRect();
                    }
                    dataInputStream6.close();
                    int i34 = i21 + (i6 - i21);
                    if (dataCurve.isMathResult && dataCurve.mathBlockId != null && !dataCurve.mathBlockId.isEmpty() && this.mPenEngine != null) {
                        try {
                            String str3 = "raw-content/" + dataCurve.mathBlockId;
                            if (!this.syncedMathBlockIdsInCurrentRead.contains(str3)) {
                                CurveProp curveProp = dataCurve.getCurveProp();
                                if (curveProp != null) {
                                    this.mPenEngine.getCurveEngine().getMathSystem().syncCurvePropForMathBlock(str3, curveProp);
                                    this.syncedMathBlockIdsInCurrentRead.add(str3);
                                    Log.d(TAG, String.format("[read] immediately synced mathBlockId[%s] (full: %s) curveProp for curve mId[%d], penType=%s, penSize=%.1f", dataCurve.mathBlockId, str3, Integer.valueOf(dataCurve.mId), curveProp.getPenType(), Float.valueOf(curveProp.getPenSize())));
                                }
                            } else {
                                Log.d(TAG, String.format("[read] mathBlockId[%s] already synced in this read, skip", str3));
                            }
                        } catch (Exception e3) {
                            Log.e(TAG, String.format("[read] failed to sync mathBlockId for curve mId[%d]", Integer.valueOf(dataCurve.mId)), e3);
                        }
                    }
                    if (MathResultCurveManager.shouldAddToPreviewPage(dataCurve, z) && !dataCurve.isMathResult) {
                        arrayList = arrayList9;
                        arrayList.add(dataCurve);
                        Log.i(TAG, String.format("[read] load curve mId[%d] page[%d] size[%d] isMathResult[%s] filterMathResult[%s]", Integer.valueOf(dataCurve.mId), Integer.valueOf(i), Integer.valueOf(dataCurve.getDataSize()), Boolean.valueOf(dataCurve.isMathResult), Boolean.valueOf(z)));
                        dataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.storage.ent.file_io.InkEntFileIO$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return InkEntFileIO.lambda$read$0((Integer) obj);
                            }
                        }).add(dataCurve);
                        size = i22 + dataCurve.size(dataCurve.mSavePoints);
                    } else {
                        arrayList = arrayList9;
                        Log.d(TAG, String.format("[read] skip curve mId[%d] page[%d] size[%d] isMathResult[%s] filterMathResult[%s]", Integer.valueOf(dataCurve.mId), Integer.valueOf(i), Integer.valueOf(dataCurve.getDataSize()), Boolean.valueOf(dataCurve.isMathResult), Boolean.valueOf(z)));
                        dataCurve.releasePointList();
                        size = i22;
                    }
                    PenEngine penEngine = this.mPenEngine;
                    if (penEngine == null) {
                        arrayList4 = arrayList;
                        baseSize = baseSize;
                        i15 = i2;
                        wSOffsetX = f;
                        i18 = size;
                        i16 = i34;
                    } else {
                        penEngine.mCurveDataManager.addDataToPool(dataCurve);
                        i3 = size;
                        i16 = i34;
                        arrayList3 = arrayList7;
                        arrayList2 = arrayList8;
                    }
                    i17 = i17;
                }
            } else {
                baseSize = baseSize;
                i17 = i17;
                i2 = i15;
                i3 = i18;
                f = wSOffsetX;
                arrayList = arrayList4;
                arrayList2 = arrayList8;
                arrayList2.add(Integer.valueOf(s));
                int i35 = i20 - 8;
                byte[] bArr7 = new byte[i35];
                if (dataInputStream.read(bArr7) != i35) {
                    throw new IOException("read 5 error");
                }
                arrayList3 = arrayList7;
                arrayList3.add(bArr7);
                i16 = i19 + i20;
            }
            arrayList8 = arrayList2;
            arrayList7 = arrayList3;
            arrayList4 = arrayList;
            baseSize = baseSize;
            i15 = i2;
            i18 = i3;
            wSOffsetX = f;
            i17 = i17;
        }
        int i36 = i18;
        ArrayList arrayList10 = arrayList7;
        ArrayList arrayList11 = arrayList8;
        ArrayList arrayList12 = arrayList4;
        if (getIDataSwapListener() != null) {
            int[] iArr = new int[arrayList11.size()];
            for (int i37 = 0; i37 < arrayList11.size(); i37++) {
                iArr[i37] = ((Integer) arrayList11.get(i37)).intValue();
            }
            getIDataSwapListener().onLoadListener(headInfo.dataVersion, iArr, i, arrayList10);
        }
        dataInputStream3.close();
        dataSet.mDataCurves.addAll(arrayList12);
        dataSet.mAllData.addAll(arrayList12);
        dataSet.setDataAndPoint(i36, 0, arrayList12.size(), 0, i);
        if (!arrayList12.isEmpty()) {
            dataSet.addId(((DataCurve) arrayList12.get(arrayList12.size() - 1)).mId + 1);
        }
        dataInputStream.close();
    }

    static /* synthetic */ List lambda$read$0(Integer num) {
        return new ArrayList();
    }

    private static boolean foundNonZero(String str) {
        if (!str.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != '0') {
                    return true;
                }
            }
        }
        return false;
    }

    public void readSinglePage(DataSet dataSet, DataInputStream dataInputStream, RandomAccessFile randomAccessFile, int i) throws IOException {
        SinglePageEntTools.INSTANCE.readSinglePage(dataSet, dataInputStream, randomAccessFile, i, getIDataSwapListener(), this.mPenEngine.mCurveDataManager);
        randomAccessFile.close();
    }

    public void read(DataSet dataSet, DataInputStream dataInputStream, int i) throws IOException {
        read(dataSet, dataInputStream, i, false);
    }

    /* JADX WARN: Code duplicated, block: B:95:0x02a4  */
    public void write(DataSet dataSet, OutputStream outputStream, int i) throws IOException {
        int i2;
        ByteArrayOutputStream byteArrayOutputStream;
        float f;
        float f2;
        short sWriteRichText;
        boolean z;
        boolean z2;
        short s;
        boolean z3;
        int i3;
        int i4;
        PenEngine penEngine = this.mPenEngine;
        if (penEngine != null) {
            penEngine.mCurrentTime = System.currentTimeMillis();
            this.mPenEngine.mLastUptime = SystemClock.uptimeMillis();
        }
        DataOutputStream headBaseInfo = setHeadBaseInfo(outputStream);
        ArrayList<BaseData> arrayList = new ArrayList(dataSet.mAllDataMap.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: xc.engine.storage.ent.file_io.InkEntFileIO$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InkEntFileIO.lambda$write$1((Integer) obj);
            }
        }));
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (BaseData baseData : arrayList) {
            if (baseData.mDataState != 3 && baseData.getFlag() <= 0) {
                arrayList2.add(baseData);
            }
        }
        int size = arrayList2.size();
        DataCurve dataCurveCreateDummyCurve = null;
        CsBufferBean csBufferBeanOnSaveListener = getIDataSwapListener() != null ? getIDataSwapListener().onSaveListener(i) : null;
        int iDataSize = (csBufferBeanOnSaveListener == null ? 0 : csBufferBeanOnSaveListener.dataSize()) + size;
        if (size == 0 && iDataSize > 0) {
            dataCurveCreateDummyCurve = createDummyCurve();
            arrayList2.add(dataCurveCreateDummyCurve);
            size = arrayList2.size();
            iDataSize = (csBufferBeanOnSaveListener == null ? 0 : csBufferBeanOnSaveListener.dataSize()) + size;
        }
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream2);
        int i5 = iDataSize * 4;
        dataOutputStream.writeInt(i5 + 8);
        dataOutputStream.writeInt(iDataSize);
        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream3);
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        Log.i(TAG, "write: curPage = " + i + ",curPageOffsetX = " + offsetX + ",curPageOffsetY = " + offsetY);
        int i6 = i5 + 76;
        int i7 = 0;
        while (i7 < iDataSize) {
            dataOutputStream.writeInt(i6);
            ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
            int i8 = iDataSize;
            DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream4);
            DataOutputStream dataOutputStream4 = dataOutputStream;
            if (i7 < size) {
                BaseData baseData2 = (BaseData) arrayList2.get(i7);
                z2 = dataCurveCreateDummyCurve != null && baseData2 == dataCurveCreateDummyCurve;
                short type = baseData2.getType();
                if (type == 28) {
                    type = 1;
                }
                dataOutputStream3.writeShort(type);
                if (type == 1) {
                    DataCurve dataCurve = (DataCurve) baseData2;
                    dataOutputStream3.writeShort(dataCurve.mPenTypeValue);
                    s = type;
                    dataOutputStream3.writeByte(dataCurve.getPenType() == PenType.MARK ? 1 : 0);
                    dataOutputStream3.writeByte(dataCurve.mCurveType);
                    dataOutputStream3.writeInt(dataCurve.getCurveProp().getPenColor());
                    dataOutputStream3.writeFloat(dataCurve.getCurvePenWidth());
                    if (dataCurve.mTableFlag != -1) {
                        dataOutputStream3.writeShort(1);
                    } else {
                        dataOutputStream3.writeShort(0);
                    }
                    dataOutputStream3.writeFloat(dataCurve.mRotateDegrees);
                    dataOutputStream3.writeInt(dataCurve.getCurveProp().getPenAlpha());
                    dataOutputStream3.writeInt(dataCurve.mId);
                    dataOutputStream3.writeInt(dataCurve.getCurveProp().getPenColorWheel());
                    dataOutputStream3.writeFloat(dataCurve.mCurveProp.getSensitivity());
                    dataOutputStream3.writeFloat(dataCurve.mCurveProp.getTipSize());
                    dataOutputStream3.writeInt(dataCurve.mItemPositionIndex);
                    if (dataCurve.getType() == 28) {
                        dataOutputStream3.writeShort(1);
                    } else {
                        dataOutputStream3.writeShort(0);
                    }
                    dataOutputStream3.writeBoolean(dataCurve.isMathResult);
                    float f3 = offsetX;
                    f2 = offsetY;
                    ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                    f = f3;
                    int i9 = 0;
                    while (i9 < curvePointList.size()) {
                        dataOutputStream3.writeFloat(curvePointList.getX(i9) - f);
                        dataOutputStream3.writeFloat(curvePointList.getY(i9) - f2);
                        dataOutputStream3.writeFloat(curvePointList.getPressure(i9));
                        dataOutputStream3.writeFloat(curvePointList.getTilt(i9));
                        dataOutputStream3.writeLong(((long) curvePointList.getTime(i9)) + dataCurve.mTime);
                        dataOutputStream3.writeFloat(curvePointList.getOrientation(i9));
                        dataOutputStream3.writeFloat(curvePointList.getVelocity(i9));
                        dataOutputStream3.writeFloat(-9999.0f);
                        dataOutputStream3.writeFloat(curvePointList.getRotateAngle(i9));
                        i9++;
                        i6 = i6;
                        byteArrayOutputStream4 = byteArrayOutputStream4;
                    }
                    i2 = i6;
                    byteArrayOutputStream = byteArrayOutputStream4;
                    String strSubstring = dataCurve.strokeId != null ? dataCurve.strokeId : "";
                    if (!strSubstring.isEmpty() && strSubstring.length() < 20) {
                        StringBuilder sb = new StringBuilder(strSubstring);
                        while (sb.length() < 20) {
                            sb.append('0');
                        }
                        strSubstring = sb.toString();
                    }
                    if (strSubstring.length() > 20) {
                        i3 = 0;
                        strSubstring = strSubstring.substring(0, 20);
                    } else {
                        i3 = 0;
                    }
                    byte[] bArr = new byte[20];
                    if (!strSubstring.isEmpty()) {
                        byte[] bytes = strSubstring.getBytes("UTF-8");
                        System.arraycopy(bytes, i3, bArr, i3, Math.min(bytes.length, 20));
                    }
                    dataOutputStream3.write(bArr);
                    if (dataCurve.mathBlockId == null || dataCurve.mathBlockId.isEmpty()) {
                        i4 = -1;
                    } else {
                        try {
                            i4 = Integer.parseInt(dataCurve.mathBlockId);
                            if (i4 < 0 || i4 > 999999999) {
                                Log.w(TAG, String.format("[write] mathBlockId[%s] out of range, set to invalid", dataCurve.mathBlockId));
                                i4 = -1;
                            }
                        } catch (NumberFormatException unused) {
                            String strExtractNumericIdFromString = extractNumericIdFromString(dataCurve.mathBlockId);
                            if (strExtractNumericIdFromString == null || strExtractNumericIdFromString.isEmpty()) {
                                i4 = -1;
                            } else {
                                try {
                                    i4 = Integer.parseInt(strExtractNumericIdFromString);
                                    if (i4 < 0 || i4 > 999999999) {
                                        i4 = -1;
                                    }
                                } catch (NumberFormatException unused2) {
                                }
                            }
                        }
                    }
                    dataOutputStream3.writeInt(i4);
                    if (dataCurve.mTableFlag != -1) {
                        dataOutputStream3.writeShort(dataCurve.mTableFlag);
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (dataCurve.getType() == 28) {
                        writeShape(dataOutputStream3, i, (DataShape) dataCurve);
                        z3 = true;
                    }
                } else {
                    s = type;
                    i2 = i6;
                    byteArrayOutputStream = byteArrayOutputStream4;
                    f = offsetX;
                    f2 = offsetY;
                    z3 = false;
                }
                z = z3;
                sWriteRichText = s;
            } else {
                i2 = i6;
                byteArrayOutputStream = byteArrayOutputStream4;
                f = offsetX;
                f2 = offsetY;
                sWriteRichText = writeRichText(size, csBufferBeanOnSaveListener, i7, dataOutputStream3);
                z = false;
                z2 = false;
            }
            dataOutputStream3.close();
            int size2 = byteArrayOutputStream.size();
            int i10 = (!z2 && sWriteRichText == 1) ? (size2 + 4) - (z ? 44 : 0) : size2 + 4;
            dataOutputStream2.writeInt(i10);
            ByteArrayOutputStream byteArrayOutputStream5 = byteArrayOutputStream;
            byte[] internalBuffer = getInternalBuffer(byteArrayOutputStream5);
            if (internalBuffer == null) {
                Log.e(TAG, "get baseDayaByteArrayOutputStream internalBuffer null");
                internalBuffer = byteArrayOutputStream5.toByteArray();
            }
            dataOutputStream2.write(internalBuffer, 0, size2);
            i6 = i2 + i10;
            i7++;
            iDataSize = i8;
            offsetY = f2;
            dataOutputStream = dataOutputStream4;
            arrayList2 = arrayList2;
            dataCurveCreateDummyCurve = dataCurveCreateDummyCurve;
            offsetX = f;
        }
        byteArrayOutputStream2.writeTo(headBaseInfo);
        dataOutputStream.close();
        byteArrayOutputStream3.writeTo(headBaseInfo);
        dataOutputStream2.close();
        headBaseInfo.close();
    }

    static /* synthetic */ List lambda$write$1(Integer num) {
        return new ArrayList();
    }

    private byte[] getInternalBuffer(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            Field declaredField = ByteArrayOutputStream.class.getDeclaredField("buf");
            declaredField.setAccessible(true);
            return (byte[]) declaredField.get(byteArrayOutputStream);
        } catch (Exception e) {
            Log.e(TAG, "getInternalBuffer caught exception: " + e.getMessage());
            return null;
        }
    }

    private DataCurve createDummyCurve() {
        try {
            DataCurve dataCurve = new DataCurve(new CurveProp(PenType.INK, -16776961, 1.0f, 1), System.currentTimeMillis());
            dataCurve.mId = CheckStatus.SERVICE_UNKNOWN_ERROR;
            dataCurve.mCount = 0;
            dataCurve.mPenTypeValue = PenType.INK.getValue();
            dataCurve.mCurveType = (short) 0;
            dataCurve.isMathResult = false;
            dataCurve.strokeId = "dummy_stroke_id_0000";
            dataCurve.mathBlockId = null;
            dataCurve.mItemPositionIndex = 0;
            dataCurve.mRotateDegrees = 0.0f;
            dataCurve.getCurveProp().setPenColor(-16776961);
            dataCurve.getCurveProp().setPenSize(1.0f);
            dataCurve.getCurveProp().setPenAlpha(1);
            dataCurve.getCurveProp().setPenColorWheel(-16777216);
            dataCurve.getCurveProp().setSensitivity(0.5f);
            dataCurve.getCurveProp().setTipSize(0.0f);
            dataCurve.insertPoint(dataCurve.mSavePoints, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0L, (short) 1, 0.0f);
            return dataCurve;
        } catch (Exception e) {
            Log.e(TAG, "createDummyCurve error:" + e.getMessage());
            return null;
        }
    }

    private static void writeShape(DataOutputStream dataOutputStream, int i, DataShape dataShape) throws IOException {
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        dataOutputStream.writeByte(dataShape.mShapeProp.getStrokeType());
        dataOutputStream.writeByte(dataShape.mShapeProp.getShapeType());
        dataOutputStream.writeInt(dataShape.mShapeProp.getFillColor());
        dataOutputStream.writeInt(dataShape.mShapeProp.getFillAlpha());
        dataOutputStream.writeInt(dataShape.mShapeProp.getFillColorWheel());
        dataOutputStream.writeFloat(dataShape.mAnchorPoint4[0] - offsetX);
        dataOutputStream.writeFloat(dataShape.mAnchorPoint4[1] - offsetY);
        dataOutputStream.writeFloat(dataShape.mAnchorPoint4[2] - offsetX);
        dataOutputStream.writeFloat(dataShape.mAnchorPoint4[3] - offsetY);
        dataOutputStream.writeFloat(dataShape.mFilletPointF.x);
        dataOutputStream.writeFloat(dataShape.mFilletPointF.y);
    }

    private static short writeRichText(int i, CsBufferBean csBufferBean, int i2, DataOutputStream dataOutputStream) throws IOException {
        int i3 = i2 - i;
        short s = (short) csBufferBean.getTypes()[i3];
        dataOutputStream.writeShort(s);
        dataOutputStream.writeShort(PenType.INK.getValue());
        dataOutputStream.write(csBufferBean.getByteList().get(i3));
        return s;
    }

    private static DataOutputStream setHeadBaseInfo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
        byte[] bArr = new byte[12];
        byte[] bytes = "HWPENKITDATA".getBytes();
        System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 12));
        dataOutputStream.write(bArr);
        dataOutputStream.writeInt(24);
        dataOutputStream.writeInt(17);
        dataOutputStream.writeLong(System.currentTimeMillis());
        dataOutputStream.writeInt(1000);
        dataOutputStream.writeInt(1600);
        dataOutputStream.writeInt(20);
        dataOutputStream.writeInt(40);
        dataOutputStream.writeInt(43);
        dataOutputStream.writeInt(6);
        dataOutputStream.writeInt(38);
        dataOutputStream.writeInt(12);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeInt(68);
        return dataOutputStream;
    }

    private HeadInfo getHeadInfo(DataInputStream dataInputStream) throws IOException {
        int i = dataInputStream.read(new byte[12]);
        if (i != 12) {
            throw new IOException("getHeadInfo 1 error " + i);
        }
        int i2 = dataInputStream.readInt();
        if (i2 <= 4) {
            throw new IOException("getHeadInfo 2 error " + i2);
        }
        int i3 = i2 - 4;
        byte[] bArr = new byte[i3];
        int i4 = dataInputStream.read(bArr);
        if (i4 != i3) {
            throw new IOException("getHeadInfo 3 error " + i4);
        }
        int i5 = i2 + 12;
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        int i6 = dataInputStream2.readInt();
        dataInputStream2.readLong();
        PenEngine penEngine = this.mPenEngine;
        if (penEngine != null) {
            penEngine.setTime(System.currentTimeMillis());
        }
        dataInputStream2.readInt();
        dataInputStream2.readInt();
        dataInputStream2.close();
        return new HeadInfo(i5, i6);
    }

    private static class HeadInfo {
        public final int dataVersion;
        public final int format_headLength;

        public HeadInfo(int i, int i2) {
            this.format_headLength = i;
            this.dataVersion = i2;
        }
    }

    private static void setShapeInfo(DataInputStream dataInputStream, BaseSize baseSize, int i, DataShape dataShape) throws IOException {
        byte[] bArr = new byte[baseSize.itemShapeSize];
        int i2 = dataInputStream.read(bArr);
        if (i2 != baseSize.itemShapeSize) {
            throw new IOException("setShapeInfo error " + i2);
        }
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        byte b = dataInputStream2.readByte();
        byte b2 = dataInputStream2.readByte();
        int i3 = dataInputStream2.readInt();
        int i4 = dataInputStream2.readInt();
        int i5 = dataInputStream2.readInt();
        float f = dataInputStream2.readFloat() + offsetX;
        float f2 = dataInputStream2.readFloat() + offsetY;
        float f3 = dataInputStream2.readFloat() + offsetX;
        float f4 = dataInputStream2.readFloat() + offsetY;
        float f5 = dataInputStream2.readFloat();
        float f6 = dataInputStream2.readFloat();
        dataShape.setStrokeType(b);
        dataShape.mShapeProp.setShapeType(ShapeType.getShapeType(b2));
        dataShape.mShapeProp.setFillColor(i3);
        dataShape.mShapeProp.setFillAlpha(i4);
        dataShape.mShapeProp.setFillColorWheel(i5);
        dataShape.mAnchorPoint4[0] = f;
        dataShape.mAnchorPoint4[1] = f2;
        dataShape.mAnchorPoint4[2] = f3;
        dataShape.mAnchorPoint4[3] = f4;
        dataShape.setFilletPointF(f5, f6);
    }

    private static BaseSize getBaseSize(DataInputStream dataInputStream, HeadInfo headInfo) throws IOException {
        int i = dataInputStream.readInt();
        int i2 = i - 4;
        byte[] bArr = new byte[i2];
        int i3 = dataInputStream.read(bArr);
        if (i3 != i2) {
            throw new IOException("getBaseSize error " + i3);
        }
        int i4 = headInfo.format_headLength + i;
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        int i5 = dataInputStream2.readInt();
        int i6 = dataInputStream2.readInt();
        dataInputStream2.readInt();
        int i7 = headInfo.dataVersion >= 14 ? dataInputStream2.readInt() : 0;
        dataInputStream2.close();
        return new BaseSize(i4, i5, i6, i7);
    }

    private static class BaseSize {
        public final int eachPointSize;
        public final int format_head_publicSize;
        public final int itemCurveSize;
        public final int itemShapeSize;

        public BaseSize(int i, int i2, int i3, int i4) {
            this.format_head_publicSize = i;
            this.eachPointSize = i2;
            this.itemCurveSize = i3;
            this.itemShapeSize = i4;
        }
    }
}
