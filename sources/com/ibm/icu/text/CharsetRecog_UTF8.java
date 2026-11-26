package com.ibm.icu.text;

import com.xiaomi.aicr.paintservice.PaintErrorCode;

/* JADX INFO: loaded from: classes2.dex */
class CharsetRecog_UTF8 extends CharsetRecognizer {
    CharsetRecog_UTF8() {
    }

    @Override // com.ibm.icu.text.CharsetRecognizer
    String getName() {
        return "UTF-8";
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0069  */
    /* JADX WARN: Code duplicated, block: B:46:0x0073  */
    @Override // com.ibm.icu.text.CharsetRecognizer
    CharsetMatch match(CharsetDetector charsetDetector) {
        int i;
        byte[] bArr = charsetDetector.fRawInput;
        int i2 = 0;
        boolean z = charsetDetector.fRawLength >= 3 && (bArr[0] & 255) == 239 && (bArr[1] & 255) == 187 && (bArr[2] & 255) == 191;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < charsetDetector.fRawLength) {
            byte b = bArr[i3];
            if ((b & 128) != 0) {
                if ((b & 224) != 192) {
                    if ((b & 240) != 224) {
                        if ((b & PaintErrorCode.SECURITY_ERROR) != 240) {
                            i4++;
                            break;
                        }
                        i = 3;
                    } else {
                        i = 2;
                    }
                } else {
                    i = 1;
                }
                while (true) {
                    i3++;
                    if (i3 >= charsetDetector.fRawLength) {
                        break;
                    }
                    if ((bArr[i3] & 192) != 128) {
                        i4++;
                        break;
                        break;
                    }
                    i--;
                    if (i == 0) {
                        i5++;
                        break;
                    }
                }
            }
            i3++;
        }
        if (z && i4 == 0) {
            i2 = 100;
        } else if (z && i5 > i4 * 10) {
            i2 = 80;
        } else if (i5 > 3 && i4 == 0) {
            i2 = 100;
        } else if (i5 > 0 && i4 == 0) {
            i2 = 80;
        } else if (i5 == 0 && i4 == 0) {
            i2 = 15;
        } else if (i5 > i4 * 10) {
            i2 = 25;
        }
        if (i2 == 0) {
            return null;
        }
        return new CharsetMatch(charsetDetector, this, i2);
    }
}
