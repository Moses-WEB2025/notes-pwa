package com.ibm.icu.text;

import com.google.common.base.Ascii;

/* JADX INFO: loaded from: classes2.dex */
abstract class CharsetRecog_2022 extends CharsetRecognizer {
    CharsetRecog_2022() {
    }

    /* JADX WARN: Code duplicated, block: B:25:0x003e  */
    int match(byte[] bArr, int i, byte[][] bArr2) {
        byte b;
        int length = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (length < i) {
            if (bArr[length] == 27) {
                int i5 = 0;
                while (true) {
                    if (i5 < bArr2.length) {
                        byte[] bArr3 = bArr2[i5];
                        if (i - length >= bArr3.length) {
                            int i6 = 1;
                            while (true) {
                                if (i6 < bArr3.length) {
                                    if (bArr3[i6] == bArr[length + i6]) {
                                        i6++;
                                    }
                                } else {
                                    i2++;
                                    length += bArr3.length - 1;
                                }
                            }
                        }
                        i5++;
                    } else {
                        i3++;
                        b = bArr[length];
                        if (b != 14 || b == 15) {
                            i4++;
                        }
                    }
                }
            } else {
                b = bArr[length];
                if (b != 14) {
                    i4++;
                } else {
                    i4++;
                }
            }
            length++;
        }
        if (i2 == 0) {
            return 0;
        }
        int i7 = ((i2 * 100) - (i3 * 100)) / (i3 + i2);
        int i8 = i2 + i4;
        if (i8 < 5) {
            i7 -= (5 - i8) * 10;
        }
        if (i7 < 0) {
            return 0;
        }
        return i7;
    }

    static class CharsetRecog_2022JP extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{Ascii.ESC, 36, 40, 67}, new byte[]{Ascii.ESC, 36, 40, 68}, new byte[]{Ascii.ESC, 36, 64}, new byte[]{Ascii.ESC, 36, 65}, new byte[]{Ascii.ESC, 36, 66}, new byte[]{Ascii.ESC, 38, 64}, new byte[]{Ascii.ESC, 40, 66}, new byte[]{Ascii.ESC, 40, 72}, new byte[]{Ascii.ESC, 40, 73}, new byte[]{Ascii.ESC, 40, 74}, new byte[]{Ascii.ESC, 46, 65}, new byte[]{Ascii.ESC, 46, 70}};

        CharsetRecog_2022JP() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-JP";
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }

    static class CharsetRecog_2022KR extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{Ascii.ESC, 36, 41, 67}};

        CharsetRecog_2022KR() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-KR";
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }

    static class CharsetRecog_2022CN extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{Ascii.ESC, 36, 41, 65}, new byte[]{Ascii.ESC, 36, 41, 71}, new byte[]{Ascii.ESC, 36, 42, 72}, new byte[]{Ascii.ESC, 36, 41, 69}, new byte[]{Ascii.ESC, 36, 43, 73}, new byte[]{Ascii.ESC, 36, 43, 74}, new byte[]{Ascii.ESC, 36, 43, 75}, new byte[]{Ascii.ESC, 36, 43, 76}, new byte[]{Ascii.ESC, 36, 43, 77}, new byte[]{Ascii.ESC, 78}, new byte[]{Ascii.ESC, 79}};

        CharsetRecog_2022CN() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-CN";
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }
}
