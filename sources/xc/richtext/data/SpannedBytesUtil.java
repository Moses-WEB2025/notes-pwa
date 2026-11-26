package xc.richtext.data;

import android.graphics.RectF;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.bean.CsBufferBean;
import xc.richtext.data.bean.IBufferBean;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedBytesUtil {
    public static void convertBuffersToSpanDatas(ArrayList<SpannedData> arrayList, int[] iArr, List<byte[]> list, ScreenRenderProvider screenRenderProvider) throws IOException {
        int length = iArr.length;
        SpannedData spannedData = null;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            byte[] bArr = list.get(i);
            if (i2 == 7) {
                spannedData = new SpannedData();
                spannedData.mSpannedStatus = 0;
                spannedData.mType = 7;
                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                float f = dataInputStream.readFloat();
                float f2 = dataInputStream.readFloat();
                spannedData.setSaveRect(f, f2, dataInputStream.readFloat() + f, dataInputStream.readFloat() + f2);
                int i3 = dataInputStream.readInt() - 4;
                byte[] bArr2 = new byte[i3];
                int i4 = dataInputStream.read(bArr2);
                if (i4 == i3) {
                    spannedData.mBitmapPath = new String(bArr2, StandardCharsets.UTF_8);
                    spannedData.mDegrees = dataInputStream.readFloat();
                    int i5 = dataInputStream.readInt() - 4;
                    if (i5 > 0) {
                        byte[] bArr3 = new byte[i5];
                        int i6 = dataInputStream.read(bArr3);
                        if (i6 == i5) {
                            spannedData.mEntPath = new String(bArr3, StandardCharsets.UTF_8);
                        } else {
                            throw new IOException("read Path error, len = " + i6);
                        }
                    }
                    spannedData.setCropRectF(new RectF(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat()));
                    dataInputStream.close();
                } else {
                    throw new IOException("read Path error, len = " + i4);
                }
            } else if (i2 == 11) {
                spannedData = new SpannedData();
                spannedData.mSpannedStatus = 0;
                spannedData.mType = 11;
                DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
                spannedData.mTextColor = dataInputStream2.readInt();
                float f3 = dataInputStream2.readFloat();
                float f4 = dataInputStream2.readFloat();
                spannedData.setSaveRect(f3, f4, dataInputStream2.readFloat() + f3, dataInputStream2.readFloat() + f4);
                float f5 = dataInputStream2.readFloat();
                int i7 = dataInputStream2.readInt();
                float f6 = dataInputStream2.readFloat();
                spannedData.mDegrees = f5;
                spannedData.h = i7;
                spannedData.mTextSize = f6;
                int i8 = dataInputStream2.readInt() - 4;
                byte[] bArr4 = new byte[i8];
                int i9 = dataInputStream2.read(bArr4);
                if (i9 != i8) {
                    throw new IOException("readText Path error, len = " + i9);
                }
                Spanned spannedConvertHtmlToSpanned = SpanHtmlConvertor.convertHtmlToSpanned(new String(bArr4, StandardCharsets.UTF_8));
                ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) spannedConvertHtmlToSpanned.getSpans(0, spannedConvertHtmlToSpanned.length(), ForegroundColorSpan.class);
                if (foregroundColorSpanArr.length > 0) {
                    spannedData.mTextColor = foregroundColorSpanArr[0].getForegroundColor();
                }
                AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannedConvertHtmlToSpanned.getSpans(0, spannedConvertHtmlToSpanned.length(), AbsoluteSizeSpan.class);
                if (absoluteSizeSpanArr.length > 0) {
                    spannedData.mTextSize = absoluteSizeSpanArr[0].getSize();
                }
                spannedData.mText = spannedConvertHtmlToSpanned.toString();
                spannedData.mBgColor = dataInputStream2.readInt();
                dataInputStream2.close();
            }
            if (screenRenderProvider != null && spannedData != null) {
                spannedData.mRatio = screenRenderProvider.getDrawRatio();
            }
            arrayList.add(spannedData);
        }
    }

    public static IBufferBean convertSpanDataToBufferBean(List<SpannedData> list) throws IOException {
        CsBufferBean csBufferBean = new CsBufferBean(list.size());
        for (int i = 0; i < list.size(); i++) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            SpannedData spannedData = list.get(i);
            int i2 = spannedData.mType;
            if (i2 == 11) {
                dataOutputStream.writeInt(spannedData.mTextColor);
                RectF spanDataRectF = spannedData.getSpanDataRectF();
                dataOutputStream.writeFloat(spanDataRectF.left);
                dataOutputStream.writeFloat(spanDataRectF.top);
                dataOutputStream.writeFloat(spanDataRectF.width());
                dataOutputStream.writeFloat(spanDataRectF.height());
                dataOutputStream.writeFloat(spannedData.mDegrees);
                dataOutputStream.writeInt(spannedData.h);
                dataOutputStream.writeFloat(spannedData.mTextSize);
                byte[] bytes = SpanHtmlConvertor.convertTextToHtml(spannedData.mText, spannedData.mTextColor, spannedData.mTextSize).getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(bytes.length + 4);
                dataOutputStream.write(bytes);
                dataOutputStream.writeInt(spannedData.mBgColor);
                csBufferBean.addTypeAndBuffer(i, i2, byteArrayOutputStream.toByteArray());
            } else if (i2 == 7) {
                RectF spanDataRectF2 = spannedData.getSpanDataRectF();
                dataOutputStream.writeFloat(spanDataRectF2.left);
                dataOutputStream.writeFloat(spanDataRectF2.top);
                dataOutputStream.writeFloat(spanDataRectF2.width());
                dataOutputStream.writeFloat(spanDataRectF2.height());
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
                csBufferBean.addTypeAndBuffer(i, i2, byteArrayOutputStream.toByteArray());
            }
            dataOutputStream.close();
        }
        return csBufferBean;
    }
}
