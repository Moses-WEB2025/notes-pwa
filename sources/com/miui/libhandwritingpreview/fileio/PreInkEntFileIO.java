package com.miui.libhandwritingpreview.fileio;

import android.graphics.RectF;
import android.os.SystemClock;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.lingala.zip4j.util.InternalZipConstants;
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
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.storage.ent.tools.SinglePageEntTools;

/* JADX INFO: compiled from: PreInkEntFileIO.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 ,2\u00020\u0001:\u0003*+,B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0002J&\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J*\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!2\b\b\u0002\u0010#\u001a\u00020$H\u0007J\u001e\u0010%\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020'2\u0006\u0010 \u001a\u00020!J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006-"}, d2 = {"Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO;", "", "mPenEngine", "Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PenPreEngine;)V", "getMPenEngine", "()Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", "setMPenEngine", "mDataInterfaceSet", "Lxc/engine/data/DataInterfaceSet;", "getMDataInterfaceSet", "()Lxc/engine/data/DataInterfaceSet;", "setMDataInterfaceSet", "(Lxc/engine/data/DataInterfaceSet;)V", "tempPenSize", "", "iDataSwapListener", "Lxc/engine/data/IDataSwapListener;", "getIDataSwapListener", "()Lxc/engine/data/IDataSwapListener;", "extractNumericIdFromString", "", "str", "readSinglePage", "", "dataSet", "Lxc/engine/data/pagedata/DataSet;", "dataInputStream", "Ljava/io/DataInputStream;", "randomAccessFile", "Ljava/io/RandomAccessFile;", "pageIndex", "", "read", "filterMathResult", "", "write", "outputStream", "Ljava/io/OutputStream;", "getHeadInfo", "Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$HeadInfo;", "HeadInfo", "BaseSize", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreInkEntFileIO {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private DataInterfaceSet mDataInterfaceSet;
    private PenPreEngine mPenEngine;
    private float tempPenSize;

    public final void read(DataSet dataSet, DataInputStream dataInputStream, int i) throws IOException {
        Intrinsics.checkNotNullParameter(dataSet, "dataSet");
        Intrinsics.checkNotNullParameter(dataInputStream, "dataInputStream");
        read$default(this, dataSet, dataInputStream, i, false, 8, null);
    }

    public PreInkEntFileIO(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
    }

    public final PenPreEngine getMPenEngine() {
        return this.mPenEngine;
    }

    public final void setMPenEngine(PenPreEngine penPreEngine) {
        this.mPenEngine = penPreEngine;
    }

    public final DataInterfaceSet getMDataInterfaceSet() {
        return this.mDataInterfaceSet;
    }

    public final void setMDataInterfaceSet(DataInterfaceSet dataInterfaceSet) {
        this.mDataInterfaceSet = dataInterfaceSet;
    }

    private final IDataSwapListener getIDataSwapListener() {
        PenPreEngine penPreEngine = this.mPenEngine;
        if (penPreEngine != null) {
            Intrinsics.checkNotNull(penPreEngine);
            return penPreEngine.mDataAccess.mIDataSwapListener;
        }
        DataInterfaceSet dataInterfaceSet = this.mDataInterfaceSet;
        if (dataInterfaceSet == null) {
            return null;
        }
        Intrinsics.checkNotNull(dataInterfaceSet);
        return dataInterfaceSet.getDataSwapListener();
    }

    private final String extractNumericIdFromString(String str) {
        if (str == null) {
            return null;
        }
        String str2 = str;
        if (str2.length() == 0) {
            return null;
        }
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) InternalZipConstants.ZIP_FILE_SEPARATOR, false, 2, (Object) null)) {
            str = str.substring(StringsKt.lastIndexOf$default((CharSequence) str2, InternalZipConstants.ZIP_FILE_SEPARATOR, 0, false, 6, (Object) null) + 1);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        }
        try {
            Long.parseLong(str);
            return str;
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder();
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char cCharAt = str.charAt(i);
                if (Character.isDigit(cCharAt)) {
                    sb.append(cCharAt);
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
            return null;
        }
    }

    public final void readSinglePage(DataSet dataSet, DataInputStream dataInputStream, RandomAccessFile randomAccessFile, int pageIndex) throws IOException {
        Intrinsics.checkNotNullParameter(dataSet, "dataSet");
        Intrinsics.checkNotNullParameter(dataInputStream, "dataInputStream");
        Intrinsics.checkNotNullParameter(randomAccessFile, "randomAccessFile");
        SinglePageEntTools singlePageEntTools = SinglePageEntTools.INSTANCE;
        IDataSwapListener iDataSwapListener = getIDataSwapListener();
        PenPreEngine penPreEngine = this.mPenEngine;
        singlePageEntTools.readSinglePage(dataSet, dataInputStream, randomAccessFile, pageIndex, iDataSwapListener, penPreEngine != null ? penPreEngine.mCurveDataManager : null);
        randomAccessFile.close();
    }

    public static /* synthetic */ void read$default(PreInkEntFileIO preInkEntFileIO, DataSet dataSet, DataInputStream dataInputStream, int i, boolean z, int i2, Object obj) throws IOException {
        if ((i2 & 8) != 0) {
            z = true;
        }
        preInkEntFileIO.read(dataSet, dataInputStream, i, z);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x03fa A[Catch: Exception -> 0x0405, TryCatch #3 {Exception -> 0x0405, blocks: (B:93:0x03bd, B:95:0x03da, B:97:0x03e9, B:99:0x03f1, B:100:0x03f7, B:102:0x03fd, B:101:0x03fa, B:103:0x0400), top: B:196:0x03bd }] */
    /* JADX WARN: Code duplicated, block: B:137:0x048e A[Catch: Exception -> 0x04af, TryCatch #0 {Exception -> 0x04af, blocks: (B:114:0x043b, B:122:0x044f, B:125:0x045b, B:127:0x045e, B:129:0x0466, B:130:0x046d, B:132:0x0473, B:137:0x048e, B:134:0x0481, B:136:0x048a, B:138:0x0495, B:141:0x04a0, B:142:0x04a7, B:143:0x04ab), top: B:190:0x043b }] */
    /* JADX WARN: Code duplicated, block: B:63:0x02a0  */
    public final void read(DataSet dataSet, DataInputStream dataInputStream, int pageIndex, boolean filterMathResult) throws IOException {
        PreInkEntFileIO preInkEntFileIO;
        int i;
        float f;
        float f2;
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z;
        int i2;
        RectF rectF;
        DataInputStream dataInputStream2;
        boolean z2;
        int itemShapeSize;
        float f3;
        PreInkEntFileIO preInkEntFileIO2 = this;
        Intrinsics.checkNotNullParameter(dataSet, "dataSet");
        Intrinsics.checkNotNullParameter(dataInputStream, "dataInputStream");
        HeadInfo headInfo = preInkEntFileIO2.getHeadInfo(dataInputStream);
        BaseSize baseSize = INSTANCE.getBaseSize(dataInputStream, headInfo);
        int i3 = dataInputStream.readInt();
        int i4 = i3 - 4;
        byte[] bArr = new byte[i4];
        int i5 = dataInputStream.read(bArr);
        if (i5 != i4) {
            throw new IOException("read 1 error " + i5);
        }
        int format_head_publicSize = baseSize.getFormat_head_publicSize() + i3;
        DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bArr));
        dataInputStream3.readInt();
        int i6 = dataInputStream3.readInt();
        if (i6 > format_head_publicSize) {
            Integer.valueOf(dataInputStream.skipBytes(i6 - format_head_publicSize));
            format_head_publicSize = i6;
        } else {
            Unit unit = Unit.INSTANCE;
        }
        dataInputStream3.close();
        int i7 = dataInputStream.readInt();
        int i8 = i7 - 4;
        byte[] bArr2 = new byte[i8];
        int i9 = dataInputStream.read(bArr2);
        if (i9 != i8) {
            throw new IOException("read 2 error " + i9);
        }
        int i10 = format_head_publicSize + i7;
        DataInputStream dataInputStream4 = new DataInputStream(new ByteArrayInputStream(bArr2));
        int i11 = dataInputStream4.readInt();
        ArrayList arrayList3 = new ArrayList(i11);
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        float wSOffsetX = InkEntFileIOHelper.INSTANCE.getWSOffsetX(pageIndex);
        float wSOffsetY = InkEntFileIOHelper.INSTANCE.getWSOffsetY(pageIndex);
        float offsetX = InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
        float offsetY = InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
        int i12 = 0;
        int size = 0;
        while (i12 < i11) {
            int i13 = dataInputStream4.readInt();
            dataInputStream4 = dataInputStream4;
            int i14 = i12 + 1;
            if (i13 > i10) {
                dataInputStream.skipBytes(i13 - i10);
                i10 = i13;
            }
            int i15 = dataInputStream.readInt();
            short s = dataInputStream.readShort();
            short s2 = dataInputStream.readShort();
            int i16 = i10 + 8;
            if (s == 1) {
                i = i11;
                f2 = offsetX;
                ArrayList arrayList6 = arrayList5;
                ArrayList arrayList7 = arrayList4;
                DataCurve dataCurve = new DataCurve(new CurveProp(PenType.getPenType(s2), -16776961, 6.0f, 255), 0L);
                dataCurve.mCount = dataSet.getCount();
                dataCurve.mId = i14;
                dataCurve.mPenTypeValue = s2;
                int i17 = i13 + i15;
                byte[] bArr3 = new byte[baseSize.getItemCurveSize()];
                int i18 = dataInputStream.read(bArr3);
                if (i18 != baseSize.getItemCurveSize()) {
                    throw new IOException("read 3 error " + i18);
                }
                int itemCurveSize = i16 + baseSize.getItemCurveSize();
                DataInputStream dataInputStream5 = new DataInputStream(new ByteArrayInputStream(bArr3));
                dataInputStream5.readByte();
                dataCurve.mCurveType = dataInputStream5.readByte();
                dataCurve.mCurveProp.setPenColor(dataInputStream5.readInt());
                dataCurve.mCurveProp.setPenSize(dataInputStream5.readFloat());
                dataInputStream5.readShort();
                dataCurve.mRotateDegrees = dataInputStream5.readFloat();
                dataCurve.mCurveProp.setPenAlpha(dataInputStream5.readInt());
                dataCurve.pageIndex = pageIndex;
                dataCurve.renderOffsetX = wSOffsetX;
                dataCurve.renderOffsetY = wSOffsetY;
                dataCurve.mId = dataInputStream5.readInt();
                dataCurve.mCurveProp.setPenColorWheel(dataInputStream5.readInt());
                if (headInfo.getDataVersion() >= 13) {
                    dataCurve.mCurveProp.setSensitivity(dataInputStream5.readFloat());
                    dataCurve.mCurveProp.setTipSize(dataInputStream5.readFloat());
                }
                if (headInfo.getDataVersion() >= 14) {
                    dataCurve.mItemPositionIndex = dataInputStream5.readInt();
                    z = dataInputStream5.readShort() == 1;
                    if (z) {
                        boolean z3 = z;
                        DataShape dataShape = new DataShape(new ShapeProp(dataCurve.mCurveProp), dataCurve.mTime);
                        dataShape.mId = dataCurve.mId;
                        dataShape.mCount = dataCurve.mCount;
                        dataShape.mPenTypeValue = dataCurve.mPenTypeValue;
                        dataShape.mRotateDegrees = dataCurve.mRotateDegrees;
                        dataShape.mItemPositionIndex = dataCurve.mItemPositionIndex;
                        dataShape.pageIndex = pageIndex;
                        dataShape.renderOffsetX = wSOffsetX;
                        dataShape.renderOffsetY = wSOffsetY;
                        dataCurve = dataShape;
                        z = z3;
                    }
                } else {
                    z = false;
                }
                try {
                    dataCurve.isMathResult = dataInputStream5.readBoolean();
                    while (true) {
                        dataInputStream2 = dataInputStream5;
                        if (itemCurveSize < i17 && itemCurveSize + baseSize.getEachPointSize() <= i17) {
                            int i19 = i17 - itemCurveSize;
                            z2 = z;
                            if (i19 != 20 && i19 != 24) {
                                if (i19 < baseSize.getEachPointSize()) {
                                    if (i19 != 20 && i19 != 24) {
                                        String str = TAG;
                                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                                        String str2 = String.format("[read] unexpected remaining bytes: %d for curve mId[%d], expected strokeId(%d) or strokeId+mathBlockId(%d)", Arrays.copyOf(new Object[]{Integer.valueOf(i19), Integer.valueOf(dataCurve.mId), 20, 24}, 4));
                                        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                                        Log.w(str, str2);
                                        break;
                                    }
                                    break;
                                }
                                int i20 = itemCurveSize;
                                float f4 = wSOffsetX;
                                byte[] bArr4 = new byte[baseSize.getEachPointSize()];
                                int i21 = dataInputStream.read(bArr4);
                                if (i21 != baseSize.getEachPointSize()) {
                                    throw new IOException("read 4 error " + i21);
                                }
                                int eachPointSize = i20 + baseSize.getEachPointSize();
                                DataInputStream dataInputStream6 = new DataInputStream(new ByteArrayInputStream(bArr4));
                                float f5 = dataInputStream6.readFloat() + f2;
                                float f6 = dataInputStream6.readFloat() + offsetY;
                                float f7 = dataInputStream6.readFloat();
                                float f8 = dataInputStream6.readFloat();
                                long j = dataInputStream6.readLong();
                                if (i2 == 0) {
                                    dataCurve.mTime = j;
                                }
                                long j2 = j - dataCurve.mTime;
                                float f9 = dataInputStream6.readFloat();
                                float f10 = dataInputStream6.readFloat();
                                if (headInfo.getDataVersion() == 17) {
                                    dataInputStream6.readFloat();
                                    if (baseSize.getEachPointSize() == 40) {
                                        f3 = dataInputStream6.readFloat();
                                    } else {
                                        f3 = 0.0f;
                                    }
                                } else {
                                    f3 = 0.0f;
                                }
                                float f11 = f3;
                                CsPoint csPoint = new CsPoint(f5, f6, f7, f8, j2, f9, f10, f11);
                                dataCurve.insertPoint(dataCurve.mSavePoints, f5, f6, f7, f8, f9, f10, j2, (short) 1, f11);
                                if (i2 == 2 && !z2) {
                                    ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                                    CsPoint csPoint2 = new CsPoint(curvePointList.get(0));
                                    CsPoint csPoint3 = new CsPoint(curvePointList.get(1));
                                    rectF = new RectF((float) Math.min(csPoint2.getX(), csPoint3.getX()), (float) Math.min(csPoint2.getY(), csPoint3.getY()), (float) Math.max(csPoint2.getX(), csPoint3.getX()), (float) Math.max(csPoint2.getY(), csPoint3.getY()));
                                }
                                if (rectF != null) {
                                    rectF.union(
                                    /*  JADX ERROR: Method code generation error
                                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0342: INVOKE 
                                          (r15v16 'rectF' android.graphics.RectF)
                                          (wrap float:0x033a: INVOKE (r11v32 ?? I:??[OBJECT, ARRAY]) VIRTUAL call: xc.engine.data.CsPoint.getX():float A[MD:():float (m), WRAPPED] (LINE:315))
                                          (wrap float:0x033e: INVOKE (r28v1 'csPoint' xc.engine.data.CsPoint) VIRTUAL call: xc.engine.data.CsPoint.getY():float A[MD:():float (m), WRAPPED])
                                         VIRTUAL call: android.graphics.RectF.union(float, float):void A[MD:(float, float):void (c)] (LINE:315) in method: com.miui.libhandwritingpreview.fileio.PreInkEntFileIO.read(xc.engine.data.pagedata.DataSet, java.io.DataInputStream, int, boolean):void, file: classes3.dex
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:175)
                                        	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:173)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:320)
                                        	at jadx.core.dex.regions.TryCatchRegion.generate(TryCatchRegion.java:85)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                        	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:173)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:291)
                                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:270)
                                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:420)
                                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
                                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:299)
                                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
                                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
                                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                        	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:284)
                                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
                                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
                                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
                                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:632)
                                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:295)
                                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:284)
                                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:268)
                                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:160)
                                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:104)
                                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                                        	at jadx.core.ProcessClass.process(ProcessClass.java:89)
                                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:127)
                                        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:414)
                                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:402)
                                        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:352)
                                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r11v32 ??
                                        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
                                        */
                                    /*
                                        Method dump skipped, instruction units count: 1637
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.miui.libhandwritingpreview.fileio.PreInkEntFileIO.read(xc.engine.data.pagedata.DataSet, java.io.DataInputStream, int, boolean):void");
                                }

                                /* JADX INFO: Access modifiers changed from: private */
                                public static final List read$lambda$0(Integer num) {
                                    return new ArrayList();
                                }

                                /* JADX INFO: Access modifiers changed from: private */
                                public static final List read$lambda$1(Function1 function1, Object obj) {
                                    return (List) function1.invoke(obj);
                                }

                                /* JADX WARN: Code duplicated, block: B:76:0x02d0  */
                                /* JADX WARN: Code duplicated, block: B:90:0x032a A[DONT_INVERT] */
                                /* JADX WARN: Code duplicated, block: B:91:0x032c  */
                                /* JADX WARN: Code duplicated, block: B:92:0x032e  */
                                /* JADX WARN: Code duplicated, block: B:95:0x0333  */
                                /* JADX WARN: Code duplicated, block: B:96:0x0336  */
                                /* JADX WARN: Code duplicated, block: B:98:0x033c  */
                                public final void write(DataSet dataSet, OutputStream outputStream, int pageIndex) throws IOException {
                                    CsBufferBean csBufferBeanOnSaveListener;
                                    ArrayList arrayList;
                                    int i;
                                    short sWriteRichText;
                                    int i2;
                                    int length;
                                    int i3;
                                    int i4;
                                    int i5;
                                    int i6;
                                    Intrinsics.checkNotNullParameter(dataSet, "dataSet");
                                    Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                                    PenPreEngine penPreEngine = this.mPenEngine;
                                    if (penPreEngine != null) {
                                        Intrinsics.checkNotNull(penPreEngine);
                                        penPreEngine.mCurrentTime = System.currentTimeMillis();
                                        PenPreEngine penPreEngine2 = this.mPenEngine;
                                        Intrinsics.checkNotNull(penPreEngine2);
                                        penPreEngine2.mLastUptime = SystemClock.uptimeMillis();
                                    }
                                    DataOutputStream headBaseInfo = INSTANCE.setHeadBaseInfo(outputStream);
                                    Map<Integer, List<BaseData>> map = dataSet.mAllDataMap;
                                    Integer numValueOf = Integer.valueOf(pageIndex);
                                    final Function1 function1 = new Function1() { // from class: com.miui.libhandwritingpreview.fileio.PreInkEntFileIO$$ExternalSyntheticLambda2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return PreInkEntFileIO.write$lambda$2((Integer) obj);
                                        }
                                    };
                                    ArrayList arrayList2 = new ArrayList(map.computeIfAbsent(numValueOf, new Function() { // from class: com.miui.libhandwritingpreview.fileio.PreInkEntFileIO$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return PreInkEntFileIO.write$lambda$3(function1, obj);
                                        }
                                    }));
                                    ArrayList arrayList3 = new ArrayList(arrayList2.size());
                                    Iterator it = arrayList2.iterator();
                                    Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                                    while (it.hasNext()) {
                                        BaseData baseData = (BaseData) it.next();
                                        if (baseData.mDataState != 3 && baseData.getFlag() <= 0) {
                                            arrayList3.add(baseData);
                                        }
                                    }
                                    int size = arrayList3.size();
                                    if (getIDataSwapListener() != null) {
                                        IDataSwapListener iDataSwapListener = getIDataSwapListener();
                                        Intrinsics.checkNotNull(iDataSwapListener);
                                        csBufferBeanOnSaveListener = iDataSwapListener.onSaveListener(pageIndex);
                                    } else {
                                        csBufferBeanOnSaveListener = null;
                                    }
                                    int iDataSize = (csBufferBeanOnSaveListener != null ? csBufferBeanOnSaveListener.dataSize() : 0) + size;
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                                    int i7 = iDataSize * 4;
                                    dataOutputStream.writeInt(i7 + 8);
                                    dataOutputStream.writeInt(iDataSize);
                                    int i8 = i7 + 76;
                                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                    DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
                                    float offsetX = InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
                                    float offsetY = InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
                                    Log.i(TAG, "write: curPage = " + pageIndex + ",curPageOffsetX = " + offsetX + ",curPageOffsetY = " + offsetY);
                                    int i9 = 0;
                                    while (i9 < iDataSize) {
                                        dataOutputStream.writeInt(i8);
                                        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                                        DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream3);
                                        if (i9 < size) {
                                            Object obj = arrayList3.get(i9);
                                            arrayList = arrayList3;
                                            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                                            BaseData baseData2 = (BaseData) obj;
                                            sWriteRichText = baseData2.getType();
                                            if (sWriteRichText == 28) {
                                                sWriteRichText = 1;
                                            }
                                            dataOutputStream3.writeShort(sWriteRichText);
                                            if (sWriteRichText == 1) {
                                                DataCurve dataCurve = (DataCurve) baseData2;
                                                short s = sWriteRichText;
                                                dataOutputStream3.writeShort(dataCurve.mPenTypeValue);
                                                iDataSize = iDataSize;
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
                                                DataOutputStream dataOutputStream4 = dataOutputStream;
                                                int i10 = i8;
                                                ICurvePointList curvePointList = dataCurve.getCurvePointList(dataCurve.mSavePoints);
                                                int size2 = curvePointList.size();
                                                dataOutputStream = dataOutputStream4;
                                                int i11 = 0;
                                                while (i11 < size2) {
                                                    dataOutputStream3.writeFloat(curvePointList.getX(i11) - offsetX);
                                                    dataOutputStream3.writeFloat(curvePointList.getY(i11) - offsetY);
                                                    dataOutputStream3.writeFloat(curvePointList.getPressure(i11));
                                                    dataOutputStream3.writeFloat(curvePointList.getTilt(i11));
                                                    dataOutputStream3.writeLong(((long) curvePointList.getTime(i11)) + dataCurve.mTime);
                                                    dataOutputStream3.writeFloat(curvePointList.getOrientation(i11));
                                                    dataOutputStream3.writeFloat(curvePointList.getVelocity(i11));
                                                    dataOutputStream3.writeFloat(-9999.0f);
                                                    dataOutputStream3.writeFloat(curvePointList.getRotateAngle(i11));
                                                    i11++;
                                                    offsetX = offsetX;
                                                    i10 = i10;
                                                    offsetY = offsetY;
                                                }
                                                i8 = i10;
                                                offsetX = offsetX;
                                                offsetY = offsetY;
                                                String strSubstring = dataCurve.strokeId;
                                                if (strSubstring == null) {
                                                    strSubstring = "";
                                                }
                                                if (strSubstring.length() > 0 && strSubstring.length() < 20) {
                                                    strSubstring = StringsKt.padEnd(strSubstring, 20, '0');
                                                }
                                                if (strSubstring.length() > 20) {
                                                    strSubstring = strSubstring.substring(0, 20);
                                                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                                                }
                                                byte[] bArr = new byte[20];
                                                if (strSubstring.length() > 0) {
                                                    byte[] bytes = strSubstring.getBytes(Charsets.UTF_8);
                                                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                                                    i = 0;
                                                    System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 20));
                                                } else {
                                                    i = 0;
                                                }
                                                dataOutputStream3.write(bArr);
                                                if (dataCurve.mathBlockId != null) {
                                                    String mathBlockId = dataCurve.mathBlockId;
                                                    Intrinsics.checkNotNullExpressionValue(mathBlockId, "mathBlockId");
                                                    if (mathBlockId.length() > 0) {
                                                        try {
                                                            String mathBlockId2 = dataCurve.mathBlockId;
                                                            Intrinsics.checkNotNullExpressionValue(mathBlockId2, "mathBlockId");
                                                            i5 = Integer.parseInt(mathBlockId2);
                                                            if (i5 < 0 || i5 > 999999999) {
                                                                String str = TAG;
                                                                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                                                                String str2 = String.format("[write] mathBlockId[%s] out of range, set to invalid", Arrays.copyOf(new Object[]{dataCurve.mathBlockId}, 1));
                                                                Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                                                                Log.w(str, str2);
                                                                i5 = -1;
                                                            }
                                                        } catch (NumberFormatException unused) {
                                                            String strExtractNumericIdFromString = extractNumericIdFromString(dataCurve.mathBlockId);
                                                            if (strExtractNumericIdFromString == null || strExtractNumericIdFromString.length() <= 0) {
                                                                i5 = -1;
                                                            } else {
                                                                try {
                                                                    i5 = Integer.parseInt(strExtractNumericIdFromString);
                                                                    if (i5 < 0 || i5 > 999999999) {
                                                                        i5 = -1;
                                                                    }
                                                                } catch (NumberFormatException unused2) {
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        i5 = -1;
                                                    }
                                                } else {
                                                    i5 = -1;
                                                }
                                                dataOutputStream3.writeInt(i5);
                                                if (dataCurve.mTableFlag != -1) {
                                                    dataOutputStream3.writeShort(dataCurve.mTableFlag);
                                                    i6 = 1;
                                                } else {
                                                    i6 = i;
                                                }
                                                if (dataCurve.getType() == 28) {
                                                    INSTANCE.writeShape(dataOutputStream3, pageIndex, (DataShape) dataCurve);
                                                    sWriteRichText = s;
                                                    i2 = 1;
                                                } else {
                                                    i2 = i6;
                                                    sWriteRichText = s;
                                                }
                                            } else {
                                                i = 0;
                                            }
                                            byte[] byteArray = byteArrayOutputStream3.toByteArray();
                                            dataOutputStream3.close();
                                            length = byteArray.length + 4;
                                            if (sWriteRichText == 1) {
                                                if (i2 != 0) {
                                                    i3 = 6;
                                                } else {
                                                    i3 = i;
                                                }
                                                int i12 = length - i3;
                                                if (i2 != 0) {
                                                    i4 = 38;
                                                } else {
                                                    i4 = i;
                                                }
                                                dataOutputStream2.writeInt(i12 - i4);
                                            } else {
                                                dataOutputStream2.writeInt(length);
                                            }
                                            dataOutputStream2.write(byteArray);
                                            i9++;
                                            i8 += length;
                                            arrayList3 = arrayList;
                                            iDataSize = iDataSize;
                                            dataOutputStream = dataOutputStream;
                                            offsetX = offsetX;
                                            offsetY = offsetY;
                                        } else {
                                            arrayList = arrayList3;
                                            i = 0;
                                            Companion companion = INSTANCE;
                                            Intrinsics.checkNotNull(csBufferBeanOnSaveListener);
                                            sWriteRichText = companion.writeRichText(size, csBufferBeanOnSaveListener, i9, dataOutputStream3);
                                        }
                                        i2 = i;
                                        byte[] byteArray2 = byteArrayOutputStream3.toByteArray();
                                        dataOutputStream3.close();
                                        length = byteArray2.length + 4;
                                        if (sWriteRichText == 1) {
                                            if (i2 != 0) {
                                                i3 = 6;
                                            } else {
                                                i3 = i;
                                            }
                                            int i13 = length - i3;
                                            if (i2 != 0) {
                                                i4 = 38;
                                            } else {
                                                i4 = i;
                                            }
                                            dataOutputStream2.writeInt(i13 - i4);
                                        } else {
                                            dataOutputStream2.writeInt(length);
                                        }
                                        dataOutputStream2.write(byteArray2);
                                        i9++;
                                        i8 += length;
                                        arrayList3 = arrayList;
                                        iDataSize = iDataSize;
                                        dataOutputStream = dataOutputStream;
                                        offsetX = offsetX;
                                        offsetY = offsetY;
                                    }
                                    DataOutputStream dataOutputStream5 = headBaseInfo;
                                    byteArrayOutputStream.writeTo(dataOutputStream5);
                                    dataOutputStream.close();
                                    byteArrayOutputStream2.writeTo(dataOutputStream5);
                                    dataOutputStream2.close();
                                    headBaseInfo.close();
                                }

                                /* JADX INFO: Access modifiers changed from: private */
                                public static final List write$lambda$2(Integer num) {
                                    return new ArrayList();
                                }

                                /* JADX INFO: Access modifiers changed from: private */
                                public static final List write$lambda$3(Function1 function1, Object obj) {
                                    return (List) function1.invoke(obj);
                                }

                                private final HeadInfo getHeadInfo(DataInputStream dataInputStream) throws IOException {
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
                                    PenPreEngine penPreEngine = this.mPenEngine;
                                    if (penPreEngine != null) {
                                        Intrinsics.checkNotNull(penPreEngine);
                                        penPreEngine.setTime(System.currentTimeMillis());
                                    }
                                    dataInputStream2.readInt();
                                    dataInputStream2.readInt();
                                    dataInputStream2.close();
                                    return new HeadInfo(i5, i6);
                                }

                                /* JADX INFO: compiled from: PreInkEntFileIO.kt */
                                @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\n"}, d2 = {"Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$HeadInfo;", "", "format_headLength", "", "dataVersion", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(II)V", "getFormat_headLength", "()I", "getDataVersion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
                                private static final class HeadInfo {
                                    private final int dataVersion;
                                    private final int format_headLength;

                                    public HeadInfo(int i, int i2) {
                                        this.format_headLength = i;
                                        this.dataVersion = i2;
                                    }

                                    public final int getDataVersion() {
                                        return this.dataVersion;
                                    }

                                    public final int getFormat_headLength() {
                                        return this.format_headLength;
                                    }
                                }

                                /* JADX INFO: compiled from: PreInkEntFileIO.kt */
                                @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$BaseSize;", "", "format_head_publicSize", "", "eachPointSize", "itemCurveSize", "itemShapeSize", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIII)V", "getFormat_head_publicSize", "()I", "getEachPointSize", "getItemCurveSize", "getItemShapeSize", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
                                private static final class BaseSize {
                                    private final int eachPointSize;
                                    private final int format_head_publicSize;
                                    private final int itemCurveSize;
                                    private final int itemShapeSize;

                                    public BaseSize(int i, int i2, int i3, int i4) {
                                        this.format_head_publicSize = i;
                                        this.eachPointSize = i2;
                                        this.itemCurveSize = i3;
                                        this.itemShapeSize = i4;
                                    }

                                    public final int getFormat_head_publicSize() {
                                        return this.format_head_publicSize;
                                    }

                                    public final int getEachPointSize() {
                                        return this.eachPointSize;
                                    }

                                    public final int getItemCurveSize() {
                                        return this.itemCurveSize;
                                    }

                                    public final int getItemShapeSize() {
                                        return this.itemShapeSize;
                                    }
                                }

                                /* JADX INFO: compiled from: PreInkEntFileIO.kt */
                                @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J(\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\rH\u0002J\u0018\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "writeShape", "", "baseDataOutputStream", "Ljava/io/DataOutputStream;", "pageIndex", "", "shape", "Lxc/engine/data/pagedata/DataShape;", "writeRichText", "", "filterCurveAndShapeDataSize", "onSaveListener", "Lxc/engine/data/CsBufferBean;", "dataIndex", "setHeadBaseInfo", "outputStream", "Ljava/io/OutputStream;", "setShapeInfo", "dataInputStream", "Ljava/io/DataInputStream;", "baseSize", "Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$BaseSize;", "dataCurve", "getBaseSize", "headInfo", "Lcom/miui/libhandwritingpreview/fileio/PreInkEntFileIO$HeadInfo;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
                                public static final class Companion {
                                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                                        this();
                                    }

                                    private Companion() {
                                    }

                                    /* JADX INFO: Access modifiers changed from: private */
                                    public final void writeShape(DataOutputStream baseDataOutputStream, int pageIndex, DataShape shape) throws IOException {
                                        float offsetX = InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
                                        float offsetY = InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
                                        baseDataOutputStream.writeByte(shape.mShapeProp.getStrokeType());
                                        baseDataOutputStream.writeByte(shape.mShapeProp.getShapeType());
                                        baseDataOutputStream.writeInt(shape.mShapeProp.getFillColor());
                                        baseDataOutputStream.writeInt(shape.mShapeProp.getFillAlpha());
                                        baseDataOutputStream.writeInt(shape.mShapeProp.getFillColorWheel());
                                        baseDataOutputStream.writeFloat(shape.mAnchorPoint4[0] - offsetX);
                                        baseDataOutputStream.writeFloat(shape.mAnchorPoint4[1] - offsetY);
                                        baseDataOutputStream.writeFloat(shape.mAnchorPoint4[2] - offsetX);
                                        baseDataOutputStream.writeFloat(shape.mAnchorPoint4[3] - offsetY);
                                        baseDataOutputStream.writeFloat(shape.mFilletPointF.x);
                                        baseDataOutputStream.writeFloat(shape.mFilletPointF.y);
                                    }

                                    /* JADX INFO: Access modifiers changed from: private */
                                    public final short writeRichText(int filterCurveAndShapeDataSize, CsBufferBean onSaveListener, int dataIndex, DataOutputStream baseDataOutputStream) throws IOException {
                                        int i = dataIndex - filterCurveAndShapeDataSize;
                                        short s = (short) onSaveListener.getTypes()[i];
                                        baseDataOutputStream.writeShort(s);
                                        baseDataOutputStream.writeShort(PenType.INK.getValue());
                                        baseDataOutputStream.write(onSaveListener.getByteList().get(i));
                                        return s;
                                    }

                                    /* JADX INFO: Access modifiers changed from: private */
                                    public final DataOutputStream setHeadBaseInfo(OutputStream outputStream) throws IOException {
                                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
                                        byte[] bArr = new byte[12];
                                        byte[] bytes = "HWPENKITDATA".getBytes(Charsets.UTF_8);
                                        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                                        System.arraycopy(bytes, 0, bArr, 0, (int) Math.min(bytes.length, 12.0d));
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

                                    /* JADX INFO: Access modifiers changed from: private */
                                    public final void setShapeInfo(DataInputStream dataInputStream, BaseSize baseSize, int pageIndex, DataShape dataCurve) throws IOException {
                                        byte[] bArr = new byte[baseSize.getItemShapeSize()];
                                        int i = dataInputStream.read(bArr);
                                        if (i != baseSize.getItemShapeSize()) {
                                            throw new IOException("setShapeInfo error " + i);
                                        }
                                        float offsetX = InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
                                        float offsetY = InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
                                        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
                                        byte b = dataInputStream2.readByte();
                                        byte b2 = dataInputStream2.readByte();
                                        int i2 = dataInputStream2.readInt();
                                        int i3 = dataInputStream2.readInt();
                                        int i4 = dataInputStream2.readInt();
                                        float f = dataInputStream2.readFloat() + offsetX;
                                        float f2 = dataInputStream2.readFloat() + offsetY;
                                        float f3 = dataInputStream2.readFloat() + offsetX;
                                        float f4 = dataInputStream2.readFloat() + offsetY;
                                        float f5 = dataInputStream2.readFloat();
                                        float f6 = dataInputStream2.readFloat();
                                        dataCurve.setStrokeType(b);
                                        dataCurve.mShapeProp.setShapeType(ShapeType.getShapeType(b2));
                                        dataCurve.mShapeProp.setFillColor(i2);
                                        dataCurve.mShapeProp.setFillAlpha(i3);
                                        dataCurve.mShapeProp.setFillColorWheel(i4);
                                        dataCurve.mAnchorPoint4[0] = f;
                                        dataCurve.mAnchorPoint4[1] = f2;
                                        dataCurve.mAnchorPoint4[2] = f3;
                                        dataCurve.mAnchorPoint4[3] = f4;
                                        dataCurve.setFilletPointF(f5, f6);
                                    }

                                    /* JADX INFO: Access modifiers changed from: private */
                                    public final BaseSize getBaseSize(DataInputStream dataInputStream, HeadInfo headInfo) throws IOException {
                                        int i = dataInputStream.readInt();
                                        int i2 = i - 4;
                                        byte[] bArr = new byte[i2];
                                        int i3 = dataInputStream.read(bArr);
                                        if (i3 != i2) {
                                            throw new IOException("getBaseSize error " + i3);
                                        }
                                        int format_headLength = headInfo.getFormat_headLength() + i;
                                        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
                                        int i4 = dataInputStream2.readInt();
                                        int i5 = dataInputStream2.readInt();
                                        dataInputStream2.readInt();
                                        int i6 = headInfo.getDataVersion() >= 14 ? dataInputStream2.readInt() : 0;
                                        dataInputStream2.close();
                                        return new BaseSize(format_headLength, i4, i5, i6);
                                    }
                                }

                                static {
                                    Intrinsics.checkNotNullExpressionValue("PreInkEntFileIO", "getSimpleName(...)");
                                    TAG = "PreInkEntFileIO";
                                }
                            }
