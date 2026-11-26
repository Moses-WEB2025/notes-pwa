package xc.richtext.data;

import android.graphics.RectF;
import android.icu.util.Calendar;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.data.const_ai.AiConstHelper;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.utils.FileUtil;
import xc.richtext.data.bean.CsBufferBean;
import xc.richtext.data.bean.IBufferBean;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedBufferHelper {
    public static final int MSG_COMMON = 1;
    public static final int MSG_SELECT = 0;
    private static final String TAG = "SpannedBufferHelper";
    public int mBgColor;
    public String mBitmapPath;
    public String mEntPath;
    public int mTextColor;
    public float mTextSize;
    public String mTitle;

    private String checkFileWithExtension(String str, String str2) {
        File file = new File(str);
        if (!file.exists() || !file.isFile()) {
            if (str2.startsWith(".")) {
                str2 = str2.substring(1);
            }
            int iLastIndexOf = str.lastIndexOf(46);
            String str3 = iLastIndexOf > Math.max(str.lastIndexOf(47), str.lastIndexOf(92)) ? str.substring(0, iLastIndexOf) + "." + str2 : str + "." + str2;
            File file2 = new File(str3);
            if (file2.exists() && file2.isFile()) {
                return str3;
            }
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x006f  */
    private void readBitmapBybuffer(String str, float f, float f2, SpannedData spannedData, byte[] bArr) throws IOException {
        String str2;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        float f3 = dataInputStream.readFloat() + f;
        float f4 = dataInputStream.readFloat() + f2;
        spannedData.setSaveRect(f3, f4, dataInputStream.readFloat() + f3, dataInputStream.readFloat() + f4);
        int i = dataInputStream.readInt() - 4;
        byte[] bArr2 = new byte[i];
        int i2 = dataInputStream.read(bArr2);
        if (i2 != i) {
            throw new IOException("read Path error, index = " + i2);
        }
        String strCheckFileWithExtension = checkFileWithExtension(new String(bArr2, StandardCharsets.UTF_8), "jpeg");
        if (TextUtils.isEmpty(str)) {
            str2 = strCheckFileWithExtension;
        } else {
            File file = new File(strCheckFileWithExtension);
            if (TextUtils.equals(str, file.getParent())) {
                str2 = strCheckFileWithExtension;
            } else {
                str2 = str + File.separator + file.getName();
            }
        }
        if (!FileUtil.isFileExists(str2)) {
            FileUtil.copyFile(new File(strCheckFileWithExtension), new File(str2));
        }
        spannedData.mBitmapPath = str2;
        spannedData.mDegrees = dataInputStream.readFloat();
        try {
            int i3 = dataInputStream.readInt() - 4;
            if (i3 > 0) {
                byte[] bArr3 = new byte[i3];
                int i4 = dataInputStream.read(bArr3);
                if (i4 == i3) {
                    spannedData.mEntPath = new String(bArr3, StandardCharsets.UTF_8);
                } else {
                    throw new IOException("read path error, index = " + i4);
                }
            }
        } catch (Exception unused) {
        }
        try {
            spannedData.mCropRectF = new RectF(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat());
        } catch (Exception unused2) {
            spannedData.mCropRectF = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        }
        dataInputStream.close();
    }

    public void readTextByBuffer(SpannedMap spannedMap, String str, int i, int[] iArr, List<byte[]> list) throws IOException {
        int length = iArr.length;
        ArrayList arrayList = new ArrayList(length);
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        for (int i2 = 0; i2 < length; i2++) {
            byte[] bArr = list.get(i2);
            int i3 = iArr[i2];
            if (i3 == 7) {
                SpannedData spannedData = new SpannedData();
                spannedData.mSpannedStatus = 0;
                spannedData.mType = 7;
                spannedData.setPageIndex(i);
                readBitmapBybuffer(str, offsetX, offsetY, spannedData, bArr);
                arrayList.add(spannedData);
            } else if (i3 == 11) {
                SpannedData spannedData2 = new SpannedData();
                spannedData2.mSpannedStatus = 0;
                spannedData2.mType = 11;
                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                int i4 = dataInputStream.readInt();
                spannedData2.mTextColor = i4;
                float f = dataInputStream.readFloat() + offsetX;
                float f2 = dataInputStream.readFloat() + offsetY;
                spannedData2.setSaveRect(f, f2, dataInputStream.readFloat() + f, dataInputStream.readFloat() + f2);
                spannedData2.mDegrees = dataInputStream.readFloat();
                Log.d(TAG, "ww text rotate before: " + spannedData2.mDegrees);
                spannedData2.h = dataInputStream.readInt();
                spannedData2.mTextSize = dataInputStream.readFloat();
                spannedData2.setPageIndex(i);
                int i5 = dataInputStream.readInt() - 4;
                byte[] bArr2 = new byte[i5];
                int i6 = dataInputStream.read(bArr2);
                if (i6 != i5) {
                    throw new IOException("read path error, index = " + i6);
                }
                Spanned spannedConvertHtmlToSpanned = SpanHtmlConvertor.convertHtmlToSpanned(new String(bArr2, StandardCharsets.UTF_8));
                ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) spannedConvertHtmlToSpanned.getSpans(0, spannedConvertHtmlToSpanned.length(), ForegroundColorSpan.class);
                if (foregroundColorSpanArr.length > 0 && (i4 & (-16777216)) == -16777216) {
                    spannedData2.mTextColor = foregroundColorSpanArr[0].getForegroundColor();
                }
                AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannedConvertHtmlToSpanned.getSpans(0, spannedConvertHtmlToSpanned.length(), AbsoluteSizeSpan.class);
                if (absoluteSizeSpanArr.length > 0) {
                    spannedData2.mTextSize = absoluteSizeSpanArr[0].getSize();
                }
                spannedData2.mText = spannedConvertHtmlToSpanned.toString();
                try {
                    spannedData2.mBgColor = dataInputStream.readInt();
                } catch (Exception unused) {
                }
                dataInputStream.close();
                arrayList.add(spannedData2);
            } else {
                continue;
            }
        }
        spannedMap.refreshList(arrayList);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0165 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:108:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x011f A[Catch: all -> 0x015b, TRY_LEAVE, TryCatch #9 {all -> 0x015b, blocks: (B:37:0x00dd, B:43:0x010e, B:45:0x011f), top: B:114:0x00dd }] */
    /* JADX WARN: Code duplicated, block: B:51:0x0135 A[Catch: all -> 0x0142, TRY_LEAVE, TryCatch #1 {all -> 0x0142, blocks: (B:48:0x012d, B:49:0x012f, B:51:0x0135), top: B:98:0x012d }] */
    /* JADX WARN: Code duplicated, block: B:63:0x014a  */
    /* JADX WARN: Code duplicated, block: B:67:0x0150 A[Catch: all -> 0x0159, TryCatch #7 {all -> 0x0159, blocks: (B:65:0x014c, B:67:0x0150, B:69:0x0155), top: B:110:0x014c }] */
    /* JADX WARN: Code duplicated, block: B:69:0x0155 A[Catch: all -> 0x0159, TRY_LEAVE, TryCatch #7 {all -> 0x0159, blocks: (B:65:0x014c, B:67:0x0150, B:69:0x0155), top: B:110:0x014c }] */
    public IBufferBean readBufferBean(String str, int i, List<SpannedData> list) throws Throwable {
        float f;
        String absolutePath;
        String absolutePath2;
        UUID uuidFromString;
        boolean z;
        FileOutputStream fileOutputStream;
        String absolutePath3;
        FileInputStream fileInputStream;
        byte[] bArr;
        CsBufferBean csBufferBean = new CsBufferBean(list.size());
        float offsetX = InkEntFileIOHelper.getOffsetX(i);
        float offsetY = InkEntFileIOHelper.getOffsetY(i);
        int i2 = 0;
        while (i2 < list.size()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            SpannedData spannedData = list.get(i2);
            int i3 = spannedData.mType;
            if (i3 == 11) {
                f = offsetX;
                dataOutputStream.writeInt(spannedData.mTextColor);
                RectF spanDataRectF = spannedData.getSpanDataRectF();
                dataOutputStream.writeFloat(spanDataRectF.left - f);
                dataOutputStream.writeFloat(spanDataRectF.top - offsetY);
                dataOutputStream.writeFloat(spanDataRectF.width());
                dataOutputStream.writeFloat(spanDataRectF.height());
                dataOutputStream.writeFloat(spannedData.mDegrees);
                dataOutputStream.writeInt(spannedData.h);
                dataOutputStream.writeFloat(spannedData.mTextSize);
                byte[] bytes = SpanHtmlConvertor.convertTextToHtml(spannedData.mText, spannedData.mTextColor, spannedData.mTextSize).getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(bytes.length + 4);
                dataOutputStream.write(bytes);
                dataOutputStream.writeInt(spannedData.mBgColor);
                csBufferBean.addTypeAndBuffer(i2, i3, byteArrayOutputStream.toByteArray());
            } else if (i3 == 7) {
                RectF spanDataRectF2 = spannedData.getSpanDataRectF();
                dataOutputStream.writeFloat(spanDataRectF2.left - offsetX);
                dataOutputStream.writeFloat(spanDataRectF2.top - offsetY);
                dataOutputStream.writeFloat(spanDataRectF2.width());
                dataOutputStream.writeFloat(spanDataRectF2.height());
                String str2 = spannedData.mBitmapPath;
                File parentFile = new File(!str.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR) ? str + InternalZipConstants.ZIP_FILE_SEPARATOR : str).getParentFile();
                String strSubstring = "";
                if (parentFile == null) {
                    absolutePath = "";
                } else {
                    absolutePath = parentFile.getAbsolutePath();
                }
                File parentFile2 = new File(str2).getParentFile();
                if (parentFile2 == null) {
                    absolutePath2 = "";
                } else {
                    absolutePath2 = parentFile2.getAbsolutePath();
                }
                String name = new File(str2).getName();
                int iLastIndexOf = name.lastIndexOf(".");
                f = offsetX;
                if (iLastIndexOf != -1) {
                    name = name.substring(0, iLastIndexOf);
                }
                FileInputStream fileInputStream2 = null;
                if (absolutePath.equals(absolutePath2)) {
                    try {
                        uuidFromString = UUID.fromString(name);
                    } catch (Exception unused) {
                        uuidFromString = null;
                    }
                } else {
                    uuidFromString = null;
                }
                if (uuidFromString == null) {
                    int iLastIndexOf2 = str2.lastIndexOf(".");
                    if (iLastIndexOf2 != -1) {
                        strSubstring = str2.substring(iLastIndexOf2);
                    }
                    String str3 = spannedData.mBitmapPath;
                    String aiImageKeyByPath = AiConstHelper.ai_tag;
                    if (str3.contains(AiConstHelper.ai_tag)) {
                        aiImageKeyByPath = getAiImageKeyByPath(spannedData.mBitmapPath);
                        z = true;
                    } else {
                        z = false;
                    }
                    try {
                        String str4 = UUID.randomUUID().toString() + strSubstring;
                        if (z) {
                            try {
                                str4 = str4 + aiImageKeyByPath;
                                absolutePath3 = new File(absolutePath, str4).getAbsolutePath();
                                if (TextUtils.equals(spannedData.mBitmapPath, absolutePath3)) {
                                    fileOutputStream = null;
                                } else {
                                    fileInputStream = new FileInputStream(spannedData.mBitmapPath);
                                    try {
                                        fileOutputStream = new FileOutputStream(absolutePath3);
                                        try {
                                            bArr = new byte[4096];
                                            while (fileInputStream.available() > 0) {
                                                try {
                                                    fileOutputStream.write(bArr, 0, fileInputStream.read(bArr));
                                                } catch (Throwable th) {
                                                    th = th;
                                                    fileInputStream2 = fileInputStream;
                                                    if (fileInputStream2 != null) {
                                                        try {
                                                            fileInputStream2.close();
                                                        } catch (IOException unused2) {
                                                        }
                                                    }
                                                    if (fileOutputStream != null) {
                                                        try {
                                                            fileOutputStream.close();
                                                        } catch (IOException unused3) {
                                                        }
                                                    }
                                                    try {
                                                        throw th;
                                                    } catch (IOException unused4) {
                                                        if (fileInputStream2 != null) {
                                                            try {
                                                                fileInputStream2.close();
                                                            } catch (IOException unused5) {
                                                            }
                                                        }
                                                        if (fileOutputStream != null) {
                                                            try {
                                                                fileOutputStream.close();
                                                            } catch (IOException unused6) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            fileInputStream2 = fileInputStream;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        fileOutputStream = null;
                                    }
                                }
                                try {
                                    spannedData.mBitmapPath = absolutePath3;
                                    if (fileInputStream2 != null) {
                                        fileInputStream2.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    if (fileInputStream2 != null) {
                                        fileInputStream2.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                fileOutputStream = null;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                throw th;
                            }
                        } else {
                            absolutePath3 = new File(absolutePath, str4).getAbsolutePath();
                            if (TextUtils.equals(spannedData.mBitmapPath, absolutePath3)) {
                                fileInputStream = new FileInputStream(spannedData.mBitmapPath);
                                fileOutputStream = new FileOutputStream(absolutePath3);
                                bArr = new byte[4096];
                                while (fileInputStream.available() > 0) {
                                    fileOutputStream.write(bArr, 0, fileInputStream.read(bArr));
                                }
                                fileInputStream2 = fileInputStream;
                            } else {
                                fileOutputStream = null;
                            }
                            spannedData.mBitmapPath = absolutePath3;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        fileOutputStream = null;
                    }
                }
                byte[] bytes2 = spannedData.mBitmapPath.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(bytes2.length + 4);
                dataOutputStream.write(bytes2);
                dataOutputStream.writeFloat(spannedData.mDegrees);
                dataOutputStream.writeInt(4);
                RectF rectF = spannedData.mCropRectF;
                dataOutputStream.writeFloat(rectF.left);
                dataOutputStream.writeFloat(rectF.top);
                dataOutputStream.writeFloat(rectF.right);
                dataOutputStream.writeFloat(rectF.bottom);
                Log.d("TAG", "xl -- readBufferBean: write111 :CropRectF:" + spannedData.getSpanDataCropRectF());
                csBufferBean.addTypeAndBuffer(i2, i3, byteArrayOutputStream.toByteArray());
            } else {
                f = offsetX;
            }
            dataOutputStream.close();
            i2++;
            offsetX = f;
        }
        return csBufferBean;
    }

    public CharSequence getFormattedSortedDate(long j) {
        StringBuilder sb = new StringBuilder();
        System.currentTimeMillis();
        return sb;
    }

    public boolean isYesterday(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(5, -1);
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }

    public boolean isSameYear(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        return calendar.get(1) == calendar2.get(1);
    }

    public double div(double d, double d2, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return new BigDecimal(String.valueOf(d)).divide(new BigDecimal(String.valueOf(d2)), i, 4).doubleValue();
    }

    private float checkTextWidth(String str, float f) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(34.0f);
        float fMeasureText = (f * 34.0f) / textPaint.measureText(str);
        textPaint.setTextSize(fMeasureText);
        return fMeasureText;
    }

    public static String getAiImageKeyByPath(String str) {
        String str2 = TAG;
        Log.i(str2, "getAiImageKeyByPath imagePath:" + str);
        String strSubstring = "";
        try {
            int length = String.valueOf(System.currentTimeMillis()).length();
            int iIndexOf = str.indexOf(AiConstHelper.ai_tag);
            strSubstring = str.substring(iIndexOf, length + iIndexOf + AiConstHelper.ai_tag.length());
            Log.i(str2, "getAiImageKeyByPath key:" + strSubstring);
            return strSubstring;
        } catch (Exception e) {
            Log.i(TAG, "getAiImageKeyByPath error:" + e);
            return strSubstring;
        }
    }
}
