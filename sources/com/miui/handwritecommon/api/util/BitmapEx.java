package com.miui.handwritecommon.api.util;

import android.graphics.Bitmap;
import defpackage.R2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapEx {
    private static final int BI_RGB = 0;
    private static final int DIB_HEADER_SIZE = 40;
    private static final int FILE_HEADER_SIZE = 14;
    private static final int[] PIXELS_PER_METER = {R2.color.action_button_color_selected, R2.color.action_button_color_selected};
    private Bitmap _bmp;

    public BitmapEx(Bitmap bitmap) {
        this._bmp = bitmap;
    }

    public Bitmap getBitmap() {
        return this._bmp;
    }

    public void saveAsBMP(OutputStream outputStream) throws IOException {
        FileHeader fileHeader = new FileHeader("BM", (this._bmp.getWidth() * 4 * this._bmp.getHeight()) + 54, (short) 0, (short) 0, 54);
        DataOutputStreamLittleEndian dataOutputStreamLittleEndian = new DataOutputStreamLittleEndian(new DataOutputStream(outputStream));
        fileHeader.write(dataOutputStreamLittleEndian);
        new DIBHeaderBitmapInfo(40, new int[]{this._bmp.getWidth(), this._bmp.getHeight()}, (short) 1, (short) 32, 0, this._bmp.getWidth() * this._bmp.getHeight(), PIXELS_PER_METER, 0, 0).writeToStream(dataOutputStreamLittleEndian);
        int width = this._bmp.getWidth();
        int[] iArr = new int[width];
        for (int height = this._bmp.getHeight() - 1; height >= 0; height--) {
            this._bmp.getPixels(iArr, 0, width, 0, height, width, 1);
            for (int i = 0; i < width; i++) {
                iArr[i] = ByteOrder.reverse(iArr[i]);
            }
            dataOutputStreamLittleEndian.write(iArr);
        }
        dataOutputStreamLittleEndian.close();
    }

    private class FileHeader {
        public int fileOffsetToPixelArray;
        public int fileSize;
        public short reserved1;
        public short reserved2;
        public String signature;

        public FileHeader(String str, int i, short s, short s2, int i2) {
            this.signature = str;
            this.fileSize = i;
            this.reserved1 = s;
            this.reserved2 = s2;
            this.fileOffsetToPixelArray = i2;
        }

        public void write(DataOutputStreamLittleEndian dataOutputStreamLittleEndian) throws IOException {
            dataOutputStreamLittleEndian.writeString(this.signature);
            dataOutputStreamLittleEndian.writeInt(this.fileSize);
            dataOutputStreamLittleEndian.writeShort(this.reserved1);
            dataOutputStreamLittleEndian.writeShort(this.reserved2);
            dataOutputStreamLittleEndian.writeInt(this.fileOffsetToPixelArray);
        }
    }

    public static abstract class DIBHeader {
        public String name;
        public int sizeInBytes;

        public abstract void writeToStream(DataOutputStreamLittleEndian dataOutputStreamLittleEndian);

        public DIBHeader(String str, int i) {
            this.name = str;
            this.sizeInBytes = i;
        }
    }

    public static class DIBHeaderBitmapInfo extends DIBHeader {
        public short bitsPerPixel;
        public int compression;
        public int imageSizeInBytes;
        public int[] imageSizeInBytesXY;
        public int[] imageSizeInPixelsXY;
        public int numberOfColorsInPalette;
        public int numberOfColorsUsed;
        public int[] pixelsPerMeterXY;
        public short planes;

        public DIBHeaderBitmapInfo() {
            super("BitmapInfo", 40);
        }

        public DIBHeaderBitmapInfo(int i, int[] iArr, short s, short s2, int i2, int i3, int[] iArr2, int i4, int i5) {
            this();
            this.sizeInBytes = i;
            this.imageSizeInPixelsXY = iArr;
            this.planes = s;
            this.bitsPerPixel = s2;
            this.compression = i2;
            this.imageSizeInBytes = i3;
            this.pixelsPerMeterXY = iArr2;
            this.numberOfColorsInPalette = i4;
            this.numberOfColorsUsed = i5;
            if (i3 == 0) {
                int[] iArr3 = this.imageSizeInBytesXY;
                this.imageSizeInBytes = ((iArr3[0] * iArr3[1]) * s2) / 8;
            }
        }

        @Override // com.miui.handwritecommon.api.util.BitmapEx.DIBHeader
        public void writeToStream(DataOutputStreamLittleEndian dataOutputStreamLittleEndian) {
            try {
                dataOutputStreamLittleEndian.writeInt(this.sizeInBytes);
                dataOutputStreamLittleEndian.writeInt(this.imageSizeInPixelsXY[0]);
                dataOutputStreamLittleEndian.writeInt(this.imageSizeInPixelsXY[1]);
                dataOutputStreamLittleEndian.writeShort(this.planes);
                dataOutputStreamLittleEndian.writeShort(this.bitsPerPixel);
                dataOutputStreamLittleEndian.writeInt(this.compression);
                dataOutputStreamLittleEndian.writeInt(this.imageSizeInBytes);
                dataOutputStreamLittleEndian.writeInt(this.pixelsPerMeterXY[0]);
                dataOutputStreamLittleEndian.writeInt(this.pixelsPerMeterXY[1]);
                dataOutputStreamLittleEndian.writeInt(this.numberOfColorsInPalette);
                dataOutputStreamLittleEndian.writeInt(this.numberOfColorsUsed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
