package xc.engine.storage.ent.tools;

import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.Charsets;
import xc.engine.core.graphics.curve.ICurveDataManager;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CsPoint;
import xc.engine.data.CurveProp;
import xc.engine.data.IDataSwapListener;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataSet;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.storage.ent.ink_ent.InkEnt;

/* JADX INFO: compiled from: SinglePageEntTools.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001:\u00019B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000bJ:\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 JF\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00052\u0006\u0010'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0017J>\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020 2\u0016\u0010+\u001a\u0012\u0012\u0004\u0012\u00020 0,j\b\u0012\u0004\u0012\u00020 `-2\u0016\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u00050,j\b\u0012\u0004\u0012\u00020\u0005`-J>\u0010/\u001a\u00020\u00112\u0006\u0010*\u001a\u00020 2\u0016\u0010+\u001a\u0012\u0012\u0004\u0012\u00020 0,j\b\u0012\u0004\u0012\u00020 `-2\u0016\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u00050,j\b\u0012\u0004\u0012\u00020\u0005`-J \u00100\u001a\u00020 2\u0006\u00101\u001a\u00020 2\u0006\u00102\u001a\u00020 2\u0006\u00103\u001a\u00020 H\u0002J\u000e\u00104\u001a\u00020 2\u0006\u00105\u001a\u00020 J\u001d\u00106\u001a\u0004\u0018\u00010\u00052\u0006\u00107\u001a\u00020 2\u0006\u00102\u001a\u00020 ¢\u0006\u0002\u00108R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lxc/engine/storage/ent/tools/SinglePageEntTools;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", HtmlParser.LinkCardElement.TAG, "", "b", "c", DateTokenConverter.CONVERTER_KEY, "e", "TAG", "", "readSinglePageEnt", "Lxc/engine/storage/ent/PageEntInfo;", "entPath", "tempDir", "readSinglePage", "", "dataSet", "Lxc/engine/data/pagedata/DataSet;", "dataInputStream", "Ljava/io/DataInputStream;", "randomAccessFile", "Ljava/io/RandomAccessFile;", "pageIndex", "iDataSwapListener", "Lxc/engine/data/IDataSwapListener;", "curveDataManager", "Lxc/engine/core/graphics/curve/ICurveDataManager;", "readPublicProtoData", "Lxc/engine/storage/ent/tools/SinglePageEntTools$PublicProtoData;", "bytes", "", "readCurve", "Lxc/engine/data/pagedata/DataCurve;", "curveId", "curveProtoSize", "subTypeIndex", "eachPointSize", "itemSize", "dataVersion", "readImage", "buffers", "spannedByteList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "spannedTypeList", "readText", "replaceColorBytes", "data", TypedValues.AttributesType.S_TARGET, "replacement", "repairHtmlEndTag", HtmlParser.InputElement.TAG, "findLastSubarray", "array", "([B[B)Ljava/lang/Integer;", "PublicProtoData", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SinglePageEntTools {
    private static final String TAG = "SinglePageEntTools";
    public static final SinglePageEntTools INSTANCE = new SinglePageEntTools();
    private static final int a = 5;
    private static final int b = 6;
    private static final int c = 7;
    private static final int d = 8;
    private static final int e = 8;

    private SinglePageEntTools() {
    }

    /* JADX INFO: compiled from: SinglePageEntTools.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\t¨\u0006\u0016"}, d2 = {"Lxc/engine/storage/ent/tools/SinglePageEntTools$PublicProtoData;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "eachPointSize", "", "getEachPointSize", "()I", "setEachPointSize", "(I)V", "itemCurveProtoSize", "getItemCurveProtoSize", "setItemCurveProtoSize", "itemTableProtoSize", "getItemTableProtoSize", "setItemTableProtoSize", "itemShapeProtoSize", "getItemShapeProtoSize", "setItemShapeProtoSize", "itemVertexShapeSize", "getItemVertexShapeSize", "setItemVertexShapeSize", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PublicProtoData {
        private int eachPointSize = -1;
        private int itemCurveProtoSize = -1;
        private int itemTableProtoSize = -1;
        private int itemShapeProtoSize = -1;
        private int itemVertexShapeSize = -1;

        public final int getEachPointSize() {
            return this.eachPointSize;
        }

        public final void setEachPointSize(int i) {
            this.eachPointSize = i;
        }

        public final int getItemCurveProtoSize() {
            return this.itemCurveProtoSize;
        }

        public final void setItemCurveProtoSize(int i) {
            this.itemCurveProtoSize = i;
        }

        public final int getItemTableProtoSize() {
            return this.itemTableProtoSize;
        }

        public final void setItemTableProtoSize(int i) {
            this.itemTableProtoSize = i;
        }

        public final int getItemShapeProtoSize() {
            return this.itemShapeProtoSize;
        }

        public final void setItemShapeProtoSize(int i) {
            this.itemShapeProtoSize = i;
        }

        public final int getItemVertexShapeSize() {
            return this.itemVertexShapeSize;
        }

        public final void setItemVertexShapeSize(int i) {
            this.itemVertexShapeSize = i;
        }
    }

    public final PageEntInfo readSinglePageEnt(String entPath, String tempDir) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        byte[] byteArray;
        Intrinsics.checkNotNullParameter(entPath, "entPath");
        PageEntInfo pageEntInfo = new PageEntInfo();
        File file = new File(entPath);
        if (TextUtils.isEmpty(entPath) || !file.exists() || file.length() == 0) {
            throw new IOException("readSinglePageEnt error");
        }
        String str = tempDir;
        if (!TextUtils.isEmpty(str) && !new File(tempDir).isDirectory()) {
            throw new IOException("checkDataInputStream error 2");
        }
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(entPath, new String[0]), new OpenOption[0])));
        if (dataInputStream.read(new byte[12]) != 12) {
            throw new IOException("checkDataInputStream error 3");
        }
        int i = dataInputStream.readInt() - 4;
        byte[] bArr = new byte[i];
        int i2 = dataInputStream.read(bArr);
        if (i2 != i) {
            throw new IOException("read HEARD_SIZE err, read len = " + i2);
        }
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        int i3 = dataInputStream2.readInt();
        long j = dataInputStream2.readLong();
        int i4 = dataInputStream2.readInt();
        int i5 = dataInputStream2.readInt();
        pageEntInfo.setWidth(i4);
        pageEntInfo.setHeight(i5);
        pageEntInfo.setLastModifyTime(j);
        dataInputStream2.close();
        int i6 = dataInputStream.readInt() - 4;
        byte[] bArr2 = new byte[i6];
        int i7 = dataInputStream.read(bArr2);
        if (i7 != i6) {
            throw new IOException("read PUBLIC_PRO_SIZE err, read len = " + i7);
        }
        PublicProtoData publicProtoData = readPublicProtoData(bArr2);
        dataInputStream.skipBytes(dataInputStream.readInt() - 4);
        int i8 = dataInputStream.readInt() - 4;
        byte[] bArr3 = new byte[i8];
        if (dataInputStream.read(bArr3) != i8) {
            throw new IOException("read TEM_HEAD_SIZE err, read len = " + i7);
        }
        DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bArr3));
        int i9 = dataInputStream3.readInt();
        int[] iArr = new int[i9];
        if (i9 > 0) {
            for (int i10 = 0; i10 < i9; i10++) {
                iArr[i10] = dataInputStream3.readInt();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, Constants.RANDOM_LONG);
            int i11 = i9 - 1;
            randomAccessFile.seek(iArr[i11]);
            byte[] bArr4 = new byte[4];
            randomAccessFile.read(bArr4);
            int i12 = iArr[i11] + new DataInputStream(new ByteArrayInputStream(bArr4)).readInt();
            int i13 = iArr[0];
            int i14 = i12 - i13;
            byte[] bArr5 = new byte[i14];
            randomAccessFile.seek(i13);
            randomAccessFile.read(bArr5);
            int i15 = (i9 * 4) + 28;
            byteArrayOutputStream = new ByteArrayOutputStream(i14 + i15);
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
            byte[] bytes = PageEntConstants.SUNIA_OLD_SINGLE_PAGE_FORMAT_HEAD.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            dataOutputStream.write(bytes);
            dataOutputStream.writeInt(i3);
            dataOutputStream.writeInt(i9);
            for (int i16 = 0; i16 < i9; i16++) {
                dataOutputStream.writeInt((iArr[i16] - iArr[0]) + i15);
            }
            dataOutputStream.writeInt(publicProtoData.getEachPointSize());
            dataOutputStream.writeInt(publicProtoData.getItemCurveProtoSize());
            dataOutputStream.write(bArr5);
        } else {
            byteArrayOutputStream = null;
            dataOutputStream = null;
        }
        InkEnt inkEnt = new InkEnt();
        if (!TextUtils.isEmpty(str)) {
            String str2 = UUID.randomUUID() + ".ent";
            File file2 = new File(tempDir);
            if (!file2.exists() && file2.isDirectory()) {
                file2.mkdirs();
            }
            File file3 = new File(tempDir + File.separator + str2);
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            if (dataOutputStream != null) {
                dataOutputStream.flush();
            }
            if (byteArrayOutputStream == null || (byteArray = byteArrayOutputStream.toByteArray()) == null) {
                byteArray = new byte[0];
            }
            bufferedOutputStream.write(byteArray);
            bufferedOutputStream.close();
            fileOutputStream.close();
            inkEnt.setDataCachePath(file3.getAbsolutePath());
        }
        ArrayList arrayList = new ArrayList();
        inkEnt.setBackgroundColor(-1);
        inkEnt.setWidth((short) 755);
        inkEnt.setHeight((short) 1156);
        arrayList.add(inkEnt);
        pageEntInfo.setList(arrayList);
        dataInputStream.close();
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (byteArrayOutputStream != null) {
            byteArrayOutputStream.close();
        }
        return pageEntInfo;
    }

    public final void readSinglePage(DataSet dataSet, DataInputStream dataInputStream, RandomAccessFile randomAccessFile, int pageIndex, IDataSwapListener iDataSwapListener, ICurveDataManager curveDataManager) throws IOException {
        short s;
        Intrinsics.checkNotNullParameter(dataSet, "dataSet");
        Intrinsics.checkNotNullParameter(dataInputStream, "dataInputStream");
        Intrinsics.checkNotNullParameter(randomAccessFile, "randomAccessFile");
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<byte[]> arrayList2 = new ArrayList<>();
        dataInputStream.skipBytes(12);
        int i = dataInputStream.readInt();
        int i2 = dataInputStream.readInt();
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = dataInputStream.readInt();
        }
        int i4 = dataInputStream.readInt();
        int i5 = dataInputStream.readInt();
        int i6 = 0;
        int i7 = 0;
        while (i6 < i2) {
            int i8 = i6;
            int[] iArr2 = iArr;
            randomAccessFile.seek(iArr[i6]);
            int i9 = randomAccessFile.readInt();
            short s2 = randomAccessFile.readShort();
            int i10 = i7;
            short s3 = randomAccessFile.readShort();
            if (s2 == 1) {
                int i11 = i10 + 1;
                s = s2;
                DataCurve curve = INSTANCE.readCurve(i11, i5, s3, i4, i9, i, pageIndex, randomAccessFile);
                if (curveDataManager != null) {
                    curveDataManager.addDataToPool(curve);
                }
                Map<Integer, List<BaseData>> map = dataSet.mAllDataMap;
                Integer numValueOf = Integer.valueOf(pageIndex);
                final Function1 function1 = new Function1() { // from class: xc.engine.storage.ent.tools.SinglePageEntTools$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return SinglePageEntTools.readSinglePage$lambda$0((Integer) obj);
                    }
                };
                map.computeIfAbsent(numValueOf, new Function() { // from class: xc.engine.storage.ent.tools.SinglePageEntTools$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return SinglePageEntTools.readSinglePage$lambda$1(function1, obj);
                    }
                }).add(curve);
                dataSet.mDataCurves.add(curve);
                dataSet.mAllData.add(curve);
                i7 = i11;
            } else {
                s = s2;
                i7 = i10;
            }
            if (s == 7) {
                Log.d(TAG, "ww readImage: ");
                byte[] bArr = new byte[i9];
                randomAccessFile.read(bArr);
                readImage(bArr, arrayList2, arrayList);
            }
            if (s == 11) {
                byte[] bArr2 = new byte[i9];
                randomAccessFile.read(bArr2);
                readText(bArr2, arrayList2, arrayList);
            }
            i6 = i8 + 1;
            iArr = iArr2;
            i2 = i2;
            i5 = i5;
        }
        if (iDataSwapListener != null) {
            int size = arrayList.size();
            int[] iArr3 = new int[size];
            for (int i12 = 0; i12 < size; i12++) {
                Integer num = arrayList.get(i12);
                Intrinsics.checkNotNullExpressionValue(num, "get(...)");
                iArr3[i12] = num.intValue();
            }
            iDataSwapListener.onLoadListener(i, iArr3, pageIndex, arrayList2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List readSinglePage$lambda$0(Integer num) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List readSinglePage$lambda$1(Function1 function1, Object obj) {
        return (List) function1.invoke(obj);
    }

    public final PublicProtoData readPublicProtoData(byte[] bytes) throws IOException {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        int length = bytes.length;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        PublicProtoData publicProtoData = new PublicProtoData();
        if (length >= 4) {
            publicProtoData.setEachPointSize(dataInputStream.readInt());
            length -= 4;
        }
        if (length >= 4) {
            publicProtoData.setItemCurveProtoSize(dataInputStream.readInt());
            length -= 4;
        }
        if (length >= 4) {
            publicProtoData.setItemTableProtoSize(dataInputStream.readInt());
            length -= 4;
        }
        if (length >= 4) {
            publicProtoData.setItemShapeProtoSize(dataInputStream.readInt());
            length -= 4;
        }
        if (length >= 4) {
            publicProtoData.setItemVertexShapeSize(dataInputStream.readInt());
        }
        dataInputStream.close();
        return publicProtoData;
    }

    public final DataCurve readCurve(int curveId, int curveProtoSize, int subTypeIndex, int eachPointSize, int itemSize, int dataVersion, int pageIndex, RandomAccessFile randomAccessFile) throws IOException {
        Intrinsics.checkNotNullParameter(randomAccessFile, "randomAccessFile");
        DataCurve dataCurve = new DataCurve(new CurveProp(PenType.getPenType(subTypeIndex), -16776961, 6.0f, 255), 0L);
        dataCurve.mId = curveId;
        byte[] bArr = new byte[curveProtoSize];
        randomAccessFile.read(bArr);
        int i = curveProtoSize + 8;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        dataInputStream.skipBytes(2);
        dataCurve.mCurveProp.setPenColor(dataInputStream.readInt());
        dataCurve.mCurveProp.setPenSize(dataInputStream.readFloat());
        if (dataInputStream.available() >= 2 && dataVersion >= c) {
            dataInputStream.skipBytes(2);
        }
        if (dataVersion >= d) {
            dataCurve.mRotateDegrees = dataInputStream.readFloat();
            dataCurve.mCurveProp.setPenAlpha(dataInputStream.readInt());
        }
        while (i < itemSize) {
            CsPoint csPoint = new CsPoint();
            byte[] bArr2 = new byte[eachPointSize];
            if (randomAccessFile.read(bArr2) != eachPointSize) {
                break;
            }
            i += eachPointSize;
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr2));
            float f = dataInputStream2.readFloat() + InkEntFileIOHelper.INSTANCE.getOffsetX(pageIndex);
            float f2 = dataInputStream2.readFloat() + InkEntFileIOHelper.INSTANCE.getOffsetY(pageIndex);
            csPoint.x = f;
            csPoint.y = f2;
            csPoint.setPressure(dataInputStream2.readFloat());
            float f3 = dataInputStream2.readFloat();
            if (dataVersion <= a && f3 == 1.0d) {
                f3 = 0.0f;
            }
            csPoint.setTilt(f3);
            csPoint.setTime((int) dataInputStream2.readLong());
            if (dataVersion >= c) {
                csPoint.setOrientation(dataInputStream2.readFloat());
            }
            dataCurve.insertPoint(dataCurve.mSavePoints, csPoint.x, csPoint.y, csPoint.getPressure(), csPoint.getTilt(), csPoint.getOrientation(), 0.0f, csPoint.getTime(), (short) 1, 0.0f);
            dataInputStream2.close();
        }
        dataCurve.calculateBoundingRect();
        dataInputStream.close();
        return dataCurve;
    }

    public final void readImage(byte[] buffers, ArrayList<byte[]> spannedByteList, ArrayList<Integer> spannedTypeList) throws IOException {
        Intrinsics.checkNotNullParameter(buffers, "buffers");
        Intrinsics.checkNotNullParameter(spannedByteList, "spannedByteList");
        Intrinsics.checkNotNullParameter(spannedTypeList, "spannedTypeList");
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(buffers));
        dataInputStream.skipBytes(16);
        int i = dataInputStream.readInt();
        dataInputStream.skipBytes(i - 4);
        float f = -dataInputStream.readFloat();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(buffers.length);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.write(CollectionsKt.toByteArray(ArraysKt.slice(buffers, new IntRange(0, i + 15))));
        dataOutputStream.writeFloat(f);
        spannedByteList.add(byteArrayOutputStream.toByteArray());
        spannedTypeList.add(7);
        byteArrayOutputStream.close();
        dataOutputStream.close();
        dataInputStream.close();
    }

    public final void readText(byte[] buffers, ArrayList<byte[]> spannedByteList, ArrayList<Integer> spannedTypeList) throws IOException {
        Intrinsics.checkNotNullParameter(buffers, "buffers");
        Intrinsics.checkNotNullParameter(spannedByteList, "spannedByteList");
        Intrinsics.checkNotNullParameter(spannedTypeList, "spannedTypeList");
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(buffers));
        dataInputStream.skipBytes(20);
        float f = -dataInputStream.readFloat();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(buffers.length);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.write(CollectionsKt.toByteArray(ArraysKt.slice(buffers, new IntRange(0, 19))));
        dataOutputStream.writeFloat(f);
        dataOutputStream.write(CollectionsKt.toByteArray(ArraysKt.slice(buffers, new IntRange(24, buffers.length - 1))));
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNull(byteArray);
        byte[] bytes = "color:#FFFFFF;".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bytes2 = "color:#000000;".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        byte[] bArrReplaceColorBytes = replaceColorBytes(byteArray, bytes, bytes2);
        byte[] bytes3 = "color:#1A1A1A;".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
        byte[] bytes4 = "color:#000000;".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes4, "getBytes(...)");
        spannedByteList.add(repairHtmlEndTag(replaceColorBytes(bArrReplaceColorBytes, bytes3, bytes4)));
        spannedTypeList.add(11);
        byteArrayOutputStream.close();
        dataOutputStream.close();
        dataInputStream.close();
    }

    private final byte[] replaceColorBytes(byte[] data, byte[] target, byte[] replacement) {
        if (target.length != replacement.length || data.length < target.length) {
            return data;
        }
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
        int length = (data.length - target.length) + 1;
        for (int i = 0; i < length; i++) {
            if (Arrays.equals(ArraysKt.copyOfRange(data, i, target.length + i), target)) {
                int length2 = target.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    bArrCopyOf[i + i2] = replacement[i2];
                }
            }
        }
        return bArrCopyOf;
    }

    public final byte[] repairHtmlEndTag(byte[] input) {
        int iIntValue;
        int iIntValue2;
        Intrinsics.checkNotNullParameter(input, "input");
        byte[] bytes = "</body>".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bytes2 = "</html>".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        int length = bytes2.length;
        Integer numFindLastSubarray = findLastSubarray(input, bytes);
        if (numFindLastSubarray != null && (iIntValue2 = (iIntValue = numFindLastSubarray.intValue() + bytes.length) + length) <= input.length && !Arrays.equals(ArraysKt.copyOfRange(input, iIntValue, iIntValue2), bytes2)) {
            input = Arrays.copyOf(input, input.length);
            Intrinsics.checkNotNullExpressionValue(input, "copyOf(...)");
            for (int i = 0; i < length; i++) {
                input[iIntValue + i] = bytes2[i];
            }
        }
        return input;
    }

    public final Integer findLastSubarray(byte[] array, byte[] target) {
        Intrinsics.checkNotNullParameter(array, "array");
        Intrinsics.checkNotNullParameter(target, "target");
        for (int length = array.length - target.length; -1 < length; length--) {
            int length2 = target.length;
            for (int i = 0; i < length2; i++) {
                if (array[length + i] != target[i]) {
                }
            }
            return Integer.valueOf(length);
        }
        return null;
    }
}
