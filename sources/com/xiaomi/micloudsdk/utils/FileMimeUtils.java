package com.xiaomi.micloudsdk.utils;

import android.text.TextUtils;
import android.util.Log;
import androidx.collection.SieveCacheKt;
import com.google.common.base.Ascii;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes4.dex */
public class FileMimeUtils {
    private static final int MAX_END_LENGTH;
    private static final int MAX_HEAD_LENGTH;
    private static final Mime[] MIMES;
    public static final String TAG = "FileMimeUtils";

    private static class Mime {
        private final byte[] mEnd;
        private final byte[] mHead;
        private final String mMime;

        public Mime(String str, byte[] bArr, byte[] bArr2) {
            this.mMime = str;
            this.mHead = bArr;
            this.mEnd = bArr2;
        }

        public boolean hasHead() {
            return this.mHead != null;
        }

        public boolean hasEnd() {
            return this.mEnd != null;
        }

        public String getMimeType() {
            return this.mMime;
        }

        public int getHeadLength() {
            return this.mHead.length;
        }

        public int getEndLength() {
            return this.mEnd.length;
        }

        public boolean isHeadRight(byte[] bArr) {
            int length = this.mHead.length;
            for (int i = 0; i < length; i++) {
                if (bArr[i] != this.mHead[i]) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEndRight(byte[] bArr) {
            int length = this.mEnd.length;
            int length2 = bArr.length - length;
            int i = 0;
            while (i < length) {
                if (bArr[length2] != this.mEnd[i]) {
                    return false;
                }
                i++;
                length2++;
            }
            return true;
        }
    }

    static {
        Mime[] mimeArr = {new Mime("image/jpeg", new byte[]{-1, -40}, new byte[]{-1, -39}), new Mime("image/jpeg", new byte[]{-1, -40}, new byte[]{0, 0}), new Mime("image/png", new byte[]{-119, 80, 78, 71, 13, 10, Ascii.SUB, 10}, null), new Mime("image/tga", new byte[]{0, 0, 2, 0, 0}, null), new Mime("image/tga", new byte[]{0, 0, 16, 0, 0}, null), new Mime("image/gif", new byte[]{71, 73, 70, 56, 55, 97}, null), new Mime("image/gif", new byte[]{71, 73, 70, 56, 57, 97}, null), new Mime("image/bmp", new byte[]{66, 77}, null), new Mime("image/tiff", new byte[]{77, 77}, null), new Mime("image/tiff", new byte[]{73, 73}, null), new Mime("video/3gpp", new byte[]{0, 0, 0, 0, 105, 115, 111, 109, 51, 103, 112, 52}, null), new Mime("video/mp4", new byte[]{0, 0, 0, Ascii.CAN, 102, 116, 121, 112, 105, 115, 111, 109}, null), new Mime("video/mp4", new byte[]{0, 0, 0, Ascii.CAN, 102, 116, 121, 112, 109, 112, 52, 50}, null), new Mime("video/mp4", new byte[]{0, 0, 0, Ascii.CAN, 102, 116, 121, 112, 51, 103, 112}, null), new Mime("video/3gp", new byte[]{0, 0, 0, Ascii.FS, 102, 116, 121, 112, 51, 103, 112, 52}, null), new Mime("video/quicktime", new byte[]{0, 0, 0, Ascii.DC4, 102, 116, 121, 112, 113, 116}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 32, 102, 116, 121, 112, 97, 118, 99, 49}, null), new Mime("video/quicktime", new byte[]{0, 0, 0, Ascii.FS, 102, 116, 121, 112, 109, 112, 52, 50}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 32, 102, 116, 121, 112, 105, 115, 111, 109}, null)};
        MIMES = mimeArr;
        int headLength = 0;
        int endLength = 0;
        for (Mime mime : mimeArr) {
            if (mime.hasHead() && mime.getHeadLength() > headLength) {
                headLength = mime.getHeadLength();
            }
            if (mime.hasEnd() && mime.getEndLength() > endLength) {
                endLength = mime.getEndLength();
            }
        }
        MAX_HEAD_LENGTH = headLength;
        MAX_END_LENGTH = endLength;
    }

    public static String getMimeType(String str) {
        try {
            return rawGetMimeType(str);
        } catch (IOException e) {
            Log.d(TAG, "rawGetMimeType", e);
            return null;
        }
    }

    public static String getFileExt(String str) {
        if (!TextUtils.isEmpty(str)) {
            int iLastIndexOf = str.lastIndexOf(47);
            int iLastIndexOf2 = str.lastIndexOf(46);
            if (iLastIndexOf2 > iLastIndexOf) {
                return str.substring(iLastIndexOf2 + 1);
            }
            return "";
        }
        return "";
    }

    private static String rawGetMimeType(String str) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, Constants.RANDOM_LONG);
        int i = MAX_HEAD_LENGTH;
        byte[] bArr = new byte[i];
        int i2 = MAX_END_LENGTH;
        byte[] bArr2 = new byte[i2];
        long j = 0;
        try {
            randomAccessFile.seek(0L);
            int i3 = randomAccessFile.read(bArr, 0, i);
            long length = randomAccessFile.length();
            long jMin = Math.min(i2, length);
            if (jMin < 0 || jMin > SieveCacheKt.NodeLinkMask) {
                Log.e(TAG, String.format("unexpected error, endBufferedLength: %d, file length: %d", Long.valueOf(jMin), Long.valueOf(length)));
            } else {
                if (jMin != 0) {
                    randomAccessFile.seek(length - jMin);
                    if (jMin == randomAccessFile.read(bArr2, 0, (int) jMin)) {
                        j = jMin;
                    }
                    for (Mime mime : MIMES) {
                        if ((!mime.hasHead() || (i3 >= mime.getHeadLength() && mime.isHeadRight(bArr))) && (!mime.hasEnd() || (j >= mime.getEndLength() && mime.isEndRight(bArr2)))) {
                            String mimeType = mime.getMimeType();
                            try {
                                return mimeType;
                            } catch (IOException e) {
                                return mimeType;
                            }
                        }
                    }
                    return null;
                }
                Log.d(TAG, "endBufferedLength is 0, just return null mime type");
            }
            try {
                return null;
            } catch (IOException e2) {
                return null;
            }
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e3) {
                Log.d(TAG, "close image file failed.", e3);
            }
        }
    }
}
