package xc.engine.storage.ent.tools;

import android.text.TextUtils;
import android.util.Log;
import com.miui.creation.logger.EngLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.storage.ent.file.FileInfo;
import xc.engine.storage.ent.ink_ent.InkEnt;

/* JADX INFO: loaded from: classes6.dex */
public class PageEntTools {
    private static final String TAG = "PageEntTools";

    public static boolean verifyFormat(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                byte[] bArr = new byte[12];
                if (fileInputStream.read(bArr) != 12) {
                    fileInputStream.close();
                    return false;
                }
                boolean zEquals = TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), PageEntConstants.FORMAT_HEAD);
                fileInputStream.close();
                return zEquals;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "ENT verify error:" + e);
            return false;
        }
        Log.e(TAG, "ENT verify error:" + e);
        return false;
    }

    public static boolean verifySinglePageFormat(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                byte[] bArr = new byte[12];
                if (fileInputStream.read(bArr) == 12) {
                    boolean zEquals = TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), "HWPENKITDATA");
                    fileInputStream.close();
                    return zEquals;
                }
                fileInputStream.close();
                fileInputStream.close();
                return false;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "ENT verify error:" + e);
            return false;
        }
    }

    public static PageEntInfo readPageEnt(String str, String str2) throws IOException {
        int i;
        int i2;
        int i3;
        String str3 = str2;
        File file = new File(str);
        Log.i(TAG, String.format("[readPageEnt] name[%s] path[%s]", str, str2));
        DataInputStream dataInputStreamCheckDataInputStream = checkDataInputStream(str, str3, file);
        PageInfo pageInfo = getPageInfo(dataInputStreamCheckDataInputStream);
        int i4 = dataInputStreamCheckDataInputStream.readInt();
        int i5 = i4 - 4;
        byte[] bArr = new byte[i5];
        if (dataInputStreamCheckDataInputStream.read(bArr) != i5) {
            throw new IOException("read Page Ent 1");
        }
        int i6 = pageInfo.format_publicLength + i4;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        int i7 = dataInputStream.readInt();
        ArrayList arrayList = new ArrayList(i7);
        int i8 = 8;
        for (int i9 = 0; i9 < i7; i9++) {
            arrayList.add(Integer.valueOf(dataInputStream.readInt()));
            i8 += 4;
        }
        dataInputStream.close();
        if (i4 < i8) {
            throw new IOException("read Page Ent 2");
        }
        ArrayList arrayList2 = new ArrayList(i7);
        for (int i10 = 0; i10 < i7; i10++) {
            int iIntValue = ((Integer) arrayList.get(i10)).intValue();
            if (iIntValue > i6) {
                dataInputStreamCheckDataInputStream.skipBytes(iIntValue - i6);
                i6 = iIntValue;
            }
            int i11 = dataInputStreamCheckDataInputStream.readInt();
            int i12 = i11 - 4;
            byte[] bArr2 = new byte[i12];
            if (dataInputStreamCheckDataInputStream.read(bArr2) != i12) {
                throw new IOException("read Page Ent 3");
            }
            i6 += i11;
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr2));
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileType(dataInputStream2.readShort());
            fileInfo.setFileUse(dataInputStream2.readShort());
            int i13 = dataInputStream2.readInt();
            if (i13 > 0) {
                byte[] bArr3 = new byte[i13];
                dataInputStream2.read(bArr3);
                fileInfo.setFilePath(new String(bArr3, StandardCharsets.UTF_8));
                i3 = i13 + 12;
            } else {
                i3 = 12;
            }
            dataInputStream2.close();
            if (i11 < i3) {
                throw new IOException("read Page Ent 4");
            }
            arrayList2.add(fileInfo);
        }
        pageInfo.pageEntInfo.getFileInfoList().clear();
        pageInfo.pageEntInfo.getFileInfoList().addAll(arrayList2);
        arrayList2.clear();
        int i14 = dataInputStreamCheckDataInputStream.readInt();
        int i15 = i14 - 4;
        byte[] bArr4 = new byte[i15];
        if (dataInputStreamCheckDataInputStream.read(bArr4) != i15) {
            throw new IOException("read Page Ent 5");
        }
        int i16 = i6 + i14;
        DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bArr4));
        int i17 = dataInputStream3.readInt();
        ArrayList arrayList3 = new ArrayList(i17);
        int i18 = 8;
        for (int i19 = 0; i19 < i17; i19++) {
            arrayList3.add(Integer.valueOf(dataInputStream3.readInt()));
            i18 += 4;
        }
        dataInputStream3.close();
        if (i14 < i18) {
            throw new IOException("read Page Ent 6");
        }
        ArrayList arrayList4 = new ArrayList(i17);
        int i20 = 0;
        while (i20 < i17) {
            int iIntValue2 = ((Integer) arrayList3.get(i20)).intValue();
            if (iIntValue2 > i16) {
                dataInputStreamCheckDataInputStream.skipBytes(iIntValue2 - i16);
            } else {
                iIntValue2 = i16;
            }
            InkEnt inkEnt = new InkEnt();
            int i21 = dataInputStreamCheckDataInputStream.readInt();
            int i22 = i21 - 4;
            if (i22 >= 800000000) {
                String str4 = TAG;
                EngLog.e(str4, "readPageEnt: happened OOM !!!!!");
                EngLog.i(str4, "readPageEnt: PageInfo.format_publicLength -> " + pageInfo.format_publicLength);
                EngLog.i(str4, "readPageEnt: fileHeadSize -> " + i4);
                EngLog.i(str4, "readPageEnt: pageHeadSize -> " + i14);
                EngLog.i(str4, "readPageEnt: format_to_pageLength -> " + i16);
                EngLog.i(str4, "readPageEnt: itemSize -> " + i21);
                break;
            }
            byte[] bArr5 = new byte[i22];
            if (dataInputStreamCheckDataInputStream.read(bArr5) != i22) {
                throw new IOException("read Page Ent 7");
            }
            int i23 = iIntValue2 + i21;
            DataInputStream dataInputStream4 = new DataInputStream(new ByteArrayInputStream(bArr5));
            byte[] bArr6 = new byte[8];
            dataInputStream4.read(bArr6);
            String strTrim = new String(bArr6, StandardCharsets.UTF_8).trim();
            String str5 = PageEntConstants.ENT_INK;
            if (!TextUtils.equals(strTrim, PageEntConstants.ENT_INK)) {
                str5 = "unknown";
            }
            inkEnt.setPageType(str5);
            inkEnt.setWidth(dataInputStream4.readShort());
            inkEnt.setHeight(dataInputStream4.readShort());
            int i24 = dataInputStream4.readInt();
            if (i24 > 0) {
                byte[] bArr7 = new byte[i24];
                dataInputStream4.read(bArr7);
                if (TextUtils.isEmpty(str3)) {
                    inkEnt.setDataBytes(bArr7);
                    i2 = 20;
                } else {
                    i2 = 20;
                    String str6 = UUID.randomUUID().toString() + ".ent";
                    File file2 = new File(str3);
                    if (!file2.exists() && file2.isDirectory()) {
                        file2.mkdirs();
                    }
                    File file3 = new File(str3 + File.separator + str6);
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(bArr7);
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                    inkEnt.setDataCachePath(file3.getAbsolutePath());
                }
                i = i2 + i24;
            } else {
                dataInputStreamCheckDataInputStream = dataInputStreamCheckDataInputStream;
                i4 = i4;
                i = 20;
            }
            int i25 = dataInputStream4.readInt();
            int i26 = i + 4;
            if (i25 > 0) {
                byte[] bArr8 = new byte[i25];
                dataInputStream4.read(bArr8);
                inkEnt.setPdfFileId(new String(bArr8, StandardCharsets.UTF_8));
                i26 += i25;
            }
            inkEnt.setPdfPageIndex(dataInputStream4.readInt());
            inkEnt.setBackgroundColor(dataInputStream4.readInt());
            inkEnt.setBgTextureIndex(dataInputStream4.readShort());
            int i27 = i26 + 10;
            if (pageInfo.pageDataVersion >= 3) {
                inkEnt.setCustom(dataInputStream4.readBoolean());
                inkEnt.setUseNewSize(dataInputStream4.readBoolean());
                inkEnt.setMaxHeight(dataInputStream4.readInt());
                inkEnt.setMaxWidth(dataInputStream4.readInt());
                inkEnt.setSizeUnit(dataInputStream4.readInt());
                inkEnt.setWidthMM(dataInputStream4.readInt());
                inkEnt.setHeightMM(dataInputStream4.readInt());
                inkEnt.setUnLimit(dataInputStream4.readBoolean());
                inkEnt.setUnLimitStart(dataInputStream4.readInt());
                inkEnt.setUnLimitEnd(dataInputStream4.readInt());
                inkEnt.setUnLimitTop(dataInputStream4.readInt());
                inkEnt.setUnLimitBottom(dataInputStream4.readInt());
                inkEnt.setStandardWidth(dataInputStream4.readInt());
                inkEnt.setExportLeft(dataInputStream4.readInt());
                inkEnt.setExportTop(dataInputStream4.readInt());
                inkEnt.setExportRight(dataInputStream4.readInt());
                inkEnt.setExportBottom(dataInputStream4.readInt());
                inkEnt.setFixSize(dataInputStream4.readBoolean());
                inkEnt.setFixWidth(dataInputStream4.readFloat());
                inkEnt.setUnLimitToNormal(dataInputStream4.readBoolean());
                inkEnt.setPageSizeType(dataInputStream4.readInt());
                i27 = i26 + 79;
            }
            dataInputStream4.close();
            if (i21 < i27) {
                throw new IOException("read Page Ent 8");
            }
            arrayList4.add(inkEnt);
            Log.i(TAG, String.format("--debug-- [readPageEnt] loadEnt Page[%d]", Integer.valueOf(i20)));
            i20++;
            str3 = str2;
            i16 = i23;
            i4 = i4;
            dataInputStreamCheckDataInputStream = dataInputStreamCheckDataInputStream;
        }
        pageInfo.pageEntInfo.getInkEntList().clear();
        pageInfo.pageEntInfo.getInkEntList().addAll(arrayList4);
        arrayList4.clear();
        dataInputStreamCheckDataInputStream.close();
        return pageInfo.pageEntInfo;
    }

    public static PageEntInfo readSinglePageEnt(String str, String str2) throws IOException {
        return SinglePageEntTools.INSTANCE.readSinglePageEnt(str, str2);
    }

    public static boolean savePageEnt(String str, PageEntInfo pageEntInfo) throws IOException {
        if (TextUtils.isEmpty(str) || pageEntInfo == null) {
            return false;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        int iSaveVerifyInfo = saveVerifyInfo(fileOutputStream);
        int iWritePageInfo = writePageInfo(fileOutputStream, pageEntInfo);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int i = 8;
        for (int i2 = 0; i2 < pageEntInfo.getFileInfoList().size(); i2++) {
            i += 4;
        }
        dataOutputStream.writeInt(i);
        dataOutputStream.writeInt(pageEntInfo.getFileInfoList().size());
        for (int i3 = 0; i3 < pageEntInfo.getFileInfoList().size(); i3++) {
            dataOutputStream.writeInt(0);
        }
        int size = dataOutputStream.size();
        byteArrayOutputStream.writeTo(fileOutputStream);
        dataOutputStream.close();
        int i4 = iSaveVerifyInfo + iWritePageInfo;
        int i5 = i + i4;
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        int i6 = 8;
        for (int i7 = 0; i7 < pageEntInfo.getInkEntList().size(); i7++) {
            i6 += 4;
        }
        dataOutputStream2.writeInt(i6);
        dataOutputStream2.writeInt(pageEntInfo.getInkEntList().size());
        for (int i8 = 0; i8 < pageEntInfo.getInkEntList().size(); i8++) {
            dataOutputStream2.writeInt(0);
        }
        int i9 = i4 + size + 8;
        byteArrayOutputStream2.writeTo(fileOutputStream);
        dataOutputStream2.close();
        PageInkInfo pageInkInfo = getPageInkInfo(pageEntInfo, fileOutputStream, i5, i6);
        fileOutputStream.close();
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "rw");
        randomAccessFile.seek(12L);
        randomAccessFile.writeInt(iWritePageInfo + 4 + size + i6 + pageInkInfo.inkEntDataSize);
        if (pageInkInfo.itemHeadIndexList.size() > 0) {
            randomAccessFile.seek(i9);
            for (int i10 = 0; i10 < pageInkInfo.itemHeadIndexList.size(); i10++) {
                randomAccessFile.writeInt(pageInkInfo.itemHeadIndexList.get(i10).intValue());
            }
        }
        randomAccessFile.close();
        return true;
    }

    private static int saveVerifyInfo(FileOutputStream fileOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] bArr = new byte[12];
        System.arraycopy(PageEntConstants.FORMAT_HEAD.getBytes(StandardCharsets.UTF_8), 0, bArr, 0, 11);
        dataOutputStream.write(bArr);
        dataOutputStream.writeInt(0);
        int size = dataOutputStream.size();
        byteArrayOutputStream.writeTo(fileOutputStream);
        dataOutputStream.close();
        return size;
    }

    private static PageInfo getPageInfo(DataInputStream dataInputStream) throws IOException {
        int i = dataInputStream.readInt();
        int i2 = i - 4;
        byte[] bArr = new byte[i2];
        if (dataInputStream.read(bArr) != i2) {
            throw new IOException("getInfo 1");
        }
        int i3 = i + 16;
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        PageEntInfo pageEntInfo = new PageEntInfo();
        int i4 = dataInputStream2.readInt();
        pageEntInfo.setLastModifyTime(dataInputStream2.readLong());
        int i5 = dataInputStream2.readInt();
        int i6 = 20;
        if (i5 > 0) {
            byte[] bArr2 = new byte[i5];
            dataInputStream2.read(bArr2);
            pageEntInfo.setTitle(new String(bArr2, StandardCharsets.UTF_8));
            i6 = 20 + i5;
        }
        pageEntInfo.setCreateTime(dataInputStream2.readLong());
        pageEntInfo.setPageFilletRadius(dataInputStream2.readFloat());
        pageEntInfo.setPageOrientation(dataInputStream2.readShort());
        pageEntInfo.setWidth(dataInputStream2.readInt());
        pageEntInfo.setHeight(dataInputStream2.readInt());
        pageEntInfo.setCoverIndex(dataInputStream2.readShort());
        int i7 = dataInputStream2.readInt();
        int i8 = i6 + 28;
        if (i7 > 0) {
            byte[] bArr3 = new byte[i7];
            dataInputStream2.read(bArr3);
            pageEntInfo.setExtInfo(new String(bArr3, StandardCharsets.UTF_8));
            i8 += i7;
        }
        dataInputStream2.close();
        if (i < i8) {
            throw new IOException("getInfo 2");
        }
        return new PageInfo(i3, i4, pageEntInfo);
    }

    private static int writePageInfo(FileOutputStream fileOutputStream, PageEntInfo pageEntInfo) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        dataOutputStream2.writeInt(3);
        dataOutputStream2.writeLong(pageEntInfo.getLastModifyTime());
        if (TextUtils.isEmpty(pageEntInfo.getTitle())) {
            dataOutputStream2.writeInt(0);
        } else {
            byte[] bytes = pageEntInfo.getTitle().getBytes(StandardCharsets.UTF_8);
            dataOutputStream2.writeInt(bytes.length);
            dataOutputStream2.write(bytes);
        }
        dataOutputStream2.writeLong(pageEntInfo.getCreateTime());
        dataOutputStream2.writeFloat(pageEntInfo.getPageFilletRadius());
        dataOutputStream2.writeShort(pageEntInfo.getPageOrientation());
        dataOutputStream2.writeInt(pageEntInfo.getWidth());
        dataOutputStream2.writeInt(pageEntInfo.getHeight());
        dataOutputStream2.writeShort(pageEntInfo.getCoverIndex());
        if (TextUtils.isEmpty(pageEntInfo.getExtInfo())) {
            dataOutputStream2.writeInt(0);
        } else {
            byte[] bytes2 = pageEntInfo.getExtInfo().getBytes(StandardCharsets.UTF_8);
            dataOutputStream2.writeInt(bytes2.length);
            dataOutputStream2.write(bytes2);
        }
        dataOutputStream.writeInt(dataOutputStream2.size() + 4);
        byteArrayOutputStream2.writeTo(dataOutputStream);
        dataOutputStream2.close();
        int size = dataOutputStream.size();
        byteArrayOutputStream.writeTo(fileOutputStream);
        dataOutputStream.close();
        return size;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:37:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:40:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:50:0x0208 A[SYNTHETIC] */
    private static PageInkInfo getPageInkInfo(PageEntInfo pageEntInfo, FileOutputStream fileOutputStream, int i, int i2) throws IOException {
        int i3;
        ArrayList arrayList = new ArrayList(pageEntInfo.getInkEntList().size());
        int size = i + i2;
        int i4 = 0;
        int i5 = 0;
        int size2 = 0;
        while (i5 < pageEntInfo.getInkEntList().size()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
            InkEnt inkEnt = pageEntInfo.getInkEntList().get(i5);
            byte[] bArr = new byte[8];
            System.arraycopy(inkEnt.getPageType().getBytes(StandardCharsets.UTF_8), i4, bArr, i4, inkEnt.getPageType().length());
            dataOutputStream2.write(bArr);
            dataOutputStream2.writeShort(inkEnt.getWidth());
            dataOutputStream2.writeShort(inkEnt.getHeight());
            if (!TextUtils.isEmpty(inkEnt.getDataCachePath())) {
                File file = new File(inkEnt.getDataCachePath());
                if (file.exists()) {
                    int length = (int) file.length();
                    Log.d(TAG, "getPageInkInfo: inkEnt cache file size = " + length);
                    if (length > 0) {
                        dataOutputStream2.writeInt(length);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
                        try {
                            byte[] bArr2 = new byte[8192];
                            while (true) {
                                int i6 = bufferedInputStream.read(bArr2);
                                if (i6 == -1) {
                                    break;
                                }
                                dataOutputStream2.write(bArr2, 0, i6);
                            }
                            bufferedInputStream.close();
                        } catch (Throwable th) {
                            try {
                                bufferedInputStream.close();
                                throw th;
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                                throw th;
                            }
                        }
                    } else {
                        dataOutputStream2.writeInt(0);
                    }
                } else {
                    dataOutputStream2.writeInt(i4);
                }
            } else {
                byte[] dataBytes = inkEnt.getDataBytes();
                if (dataBytes != null && dataBytes.length > 0) {
                    dataOutputStream2.writeInt(dataBytes.length);
                    dataOutputStream2.write(dataBytes);
                } else {
                    i3 = 0;
                    dataOutputStream2.writeInt(0);
                }
                if (TextUtils.isEmpty(inkEnt.getPdfFileId())) {
                    dataOutputStream2.writeInt(i3);
                } else {
                    byte[] bytes = inkEnt.getPdfFileId().getBytes(StandardCharsets.UTF_8);
                    dataOutputStream2.writeInt(bytes.length);
                    dataOutputStream2.write(bytes);
                }
                dataOutputStream2.writeInt(inkEnt.getPdfPageIndex());
                dataOutputStream2.writeInt(inkEnt.getBackgroundColor());
                dataOutputStream2.writeShort(inkEnt.getBgTextureIndex());
                dataOutputStream2.writeBoolean(inkEnt.isCustom());
                dataOutputStream2.writeBoolean(inkEnt.isUseNewSize());
                dataOutputStream2.writeInt(inkEnt.getMaxHeight());
                dataOutputStream2.writeInt(inkEnt.getMaxWidth());
                dataOutputStream2.writeInt(inkEnt.getSizeUnit());
                dataOutputStream2.writeInt(inkEnt.getWidthMM());
                dataOutputStream2.writeInt(inkEnt.getHeightMM());
                dataOutputStream2.writeBoolean(inkEnt.isUnLimit());
                dataOutputStream2.writeInt(inkEnt.getUnLimitStart());
                dataOutputStream2.writeInt(inkEnt.getUnLimitEnd());
                dataOutputStream2.writeInt(inkEnt.getUnLimitTop());
                dataOutputStream2.writeInt(inkEnt.getUnLimitBottom());
                dataOutputStream2.writeInt(inkEnt.getStandardWidth());
                dataOutputStream2.writeInt(inkEnt.getExportLeft());
                dataOutputStream2.writeInt(inkEnt.getExportTop());
                dataOutputStream2.writeInt(inkEnt.getExportRight());
                dataOutputStream2.writeInt(inkEnt.getExportBottom());
                dataOutputStream2.writeBoolean(inkEnt.isFixSize());
                dataOutputStream2.writeFloat(inkEnt.getFixWidth());
                dataOutputStream2.writeBoolean(inkEnt.getUnLimitToNormal());
                dataOutputStream2.writeInt(inkEnt.getPageSizeType());
                dataOutputStream.writeInt(dataOutputStream2.size() + 4);
                if (dataOutputStream2.size() + 4 > 800000000) {
                    String str = TAG;
                    EngLog.i(str, "getPageInkInfo: itemSize -> " + (dataOutputStream2.size() + 4));
                    EngLog.i(str, "getPageInkInfo: verify_pageInfo_fileHeadSize -> " + i);
                    EngLog.i(str, "getPageInkInfo: inkEntHeadSize -> " + i2);
                }
                byteArrayOutputStream2.writeTo(dataOutputStream);
                dataOutputStream2.close();
                arrayList.add(Integer.valueOf(size));
                size += dataOutputStream.size();
                size2 += dataOutputStream.size();
                byteArrayOutputStream.writeTo(fileOutputStream);
                dataOutputStream.close();
                i5++;
                i4 = i3;
            }
            i3 = 0;
            if (TextUtils.isEmpty(inkEnt.getPdfFileId())) {
                dataOutputStream2.writeInt(i3);
            } else {
                byte[] bytes2 = inkEnt.getPdfFileId().getBytes(StandardCharsets.UTF_8);
                dataOutputStream2.writeInt(bytes2.length);
                dataOutputStream2.write(bytes2);
            }
            dataOutputStream2.writeInt(inkEnt.getPdfPageIndex());
            dataOutputStream2.writeInt(inkEnt.getBackgroundColor());
            dataOutputStream2.writeShort(inkEnt.getBgTextureIndex());
            dataOutputStream2.writeBoolean(inkEnt.isCustom());
            dataOutputStream2.writeBoolean(inkEnt.isUseNewSize());
            dataOutputStream2.writeInt(inkEnt.getMaxHeight());
            dataOutputStream2.writeInt(inkEnt.getMaxWidth());
            dataOutputStream2.writeInt(inkEnt.getSizeUnit());
            dataOutputStream2.writeInt(inkEnt.getWidthMM());
            dataOutputStream2.writeInt(inkEnt.getHeightMM());
            dataOutputStream2.writeBoolean(inkEnt.isUnLimit());
            dataOutputStream2.writeInt(inkEnt.getUnLimitStart());
            dataOutputStream2.writeInt(inkEnt.getUnLimitEnd());
            dataOutputStream2.writeInt(inkEnt.getUnLimitTop());
            dataOutputStream2.writeInt(inkEnt.getUnLimitBottom());
            dataOutputStream2.writeInt(inkEnt.getStandardWidth());
            dataOutputStream2.writeInt(inkEnt.getExportLeft());
            dataOutputStream2.writeInt(inkEnt.getExportTop());
            dataOutputStream2.writeInt(inkEnt.getExportRight());
            dataOutputStream2.writeInt(inkEnt.getExportBottom());
            dataOutputStream2.writeBoolean(inkEnt.isFixSize());
            dataOutputStream2.writeFloat(inkEnt.getFixWidth());
            dataOutputStream2.writeBoolean(inkEnt.getUnLimitToNormal());
            dataOutputStream2.writeInt(inkEnt.getPageSizeType());
            dataOutputStream.writeInt(dataOutputStream2.size() + 4);
            if (dataOutputStream2.size() + 4 > 800000000) {
                String str2 = TAG;
                EngLog.i(str2, "getPageInkInfo: itemSize -> " + (dataOutputStream2.size() + 4));
                EngLog.i(str2, "getPageInkInfo: verify_pageInfo_fileHeadSize -> " + i);
                EngLog.i(str2, "getPageInkInfo: inkEntHeadSize -> " + i2);
            }
            byteArrayOutputStream2.writeTo(dataOutputStream);
            dataOutputStream2.close();
            arrayList.add(Integer.valueOf(size));
            size += dataOutputStream.size();
            size2 += dataOutputStream.size();
            byteArrayOutputStream.writeTo(fileOutputStream);
            dataOutputStream.close();
            i5++;
            i4 = i3;
        }
        return new PageInkInfo(arrayList, size2);
    }

    private static class PageInfo {
        public final int format_publicLength;
        public final int pageDataVersion;
        public final PageEntInfo pageEntInfo;

        public PageInfo(int i, int i2, PageEntInfo pageEntInfo) {
            this.format_publicLength = i;
            this.pageDataVersion = i2;
            this.pageEntInfo = pageEntInfo;
        }
    }

    private static class PageInkInfo {
        public final int inkEntDataSize;
        public final ArrayList<Integer> itemHeadIndexList;

        public PageInkInfo(ArrayList<Integer> arrayList, int i) {
            this.itemHeadIndexList = arrayList;
            this.inkEntDataSize = i;
        }
    }

    private static DataInputStream checkDataInputStream(String str, String str2, File file) throws IOException {
        if (TextUtils.isEmpty(str) || !file.exists() || file.length() == 0) {
            throw new IOException("checkDataInputStream error 1, filePath:" + str);
        }
        if (!TextUtils.isEmpty(str2) && !new File(str2).isDirectory()) {
            throw new IOException("checkDataInputStream error 2");
        }
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0])));
        byte[] bArr = new byte[12];
        if (dataInputStream.read(bArr) != 12) {
            throw new IOException("checkDataInputStream error 3");
        }
        if (!TextUtils.equals(new String(bArr, StandardCharsets.UTF_8).trim(), PageEntConstants.FORMAT_HEAD)) {
            throw new IOException("checkDataInputStream error 4");
        }
        if (dataInputStream.readInt() + 12 >= file.length()) {
            return dataInputStream;
        }
        throw new IOException("checkDataInputStream 5");
    }
}
