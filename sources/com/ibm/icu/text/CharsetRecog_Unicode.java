package com.ibm.icu.text;

/* JADX INFO: loaded from: classes2.dex */
abstract class CharsetRecog_Unicode extends CharsetRecognizer {
    static int adjustConfidence(int i, int i2) {
        if (i == 0) {
            i2 -= 10;
        } else if ((i >= 32 && i <= 255) || i == 10) {
            i2 += 10;
        }
        if (i2 < 0) {
            return 0;
        }
        if (i2 > 100) {
            return 100;
        }
        return i2;
    }

    static int codeUnit16FromBytes(byte b, byte b2) {
        return ((b & 255) << 8) | (b2 & 255);
    }

    @Override // com.ibm.icu.text.CharsetRecognizer
    abstract String getName();

    @Override // com.ibm.icu.text.CharsetRecognizer
    abstract CharsetMatch match(CharsetDetector charsetDetector);

    CharsetRecog_Unicode() {
    }

    static class CharsetRecog_UTF_16_BE extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_BE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-16BE";
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] bArr = charsetDetector.fRawInput;
            int iMin = Math.min(bArr.length, 30);
            int iAdjustConfidence = 10;
            for (int i = 0; i < iMin - 1; i += 2) {
                int iCodeUnit16FromBytes = codeUnit16FromBytes(bArr[i], bArr[i + 1]);
                if (i == 0 && iCodeUnit16FromBytes == 65279) {
                    iAdjustConfidence = 100;
                    break;
                }
                iAdjustConfidence = adjustConfidence(iCodeUnit16FromBytes, iAdjustConfidence);
                if (iAdjustConfidence == 0 || iAdjustConfidence == 100) {
                    break;
                }
            }
            int i2 = (iMin >= 4 || iAdjustConfidence >= 100) ? iAdjustConfidence : 0;
            if (i2 > 0) {
                return new CharsetMatch(charsetDetector, this, i2);
            }
            return null;
        }
    }

    static class CharsetRecog_UTF_16_LE extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_LE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-16LE";
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] bArr = charsetDetector.fRawInput;
            int iMin = Math.min(bArr.length, 30);
            int iAdjustConfidence = 10;
            for (int i = 0; i < iMin - 1; i += 2) {
                int iCodeUnit16FromBytes = codeUnit16FromBytes(bArr[i + 1], bArr[i]);
                if (i == 0 && iCodeUnit16FromBytes == 65279) {
                    iAdjustConfidence = 100;
                    break;
                }
                iAdjustConfidence = adjustConfidence(iCodeUnit16FromBytes, iAdjustConfidence);
                if (iAdjustConfidence == 0 || iAdjustConfidence == 100) {
                    break;
                }
            }
            int i2 = (iMin >= 4 || iAdjustConfidence >= 100) ? iAdjustConfidence : 0;
            if (i2 > 0) {
                return new CharsetMatch(charsetDetector, this, i2);
            }
            return null;
        }
    }

    static abstract class CharsetRecog_UTF_32 extends CharsetRecog_Unicode {
        abstract int getChar(byte[] bArr, int i);

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        abstract String getName();

        CharsetRecog_UTF_32() {
        }

        /* JADX WARN: Code duplicated, block: B:26:0x0042  */
        /* JADX WARN: Code duplicated, block: B:31:0x004c  */
        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] bArr = charsetDetector.fRawInput;
            int i = (charsetDetector.fRawLength / 4) * 4;
            if (i == 0) {
                return null;
            }
            int i2 = 0;
            boolean z = getChar(bArr, 0) == 65279;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5 += 4) {
                int i6 = getChar(bArr, i5);
                if (i6 < 0 || i6 >= 1114111 || (i6 >= 55296 && i6 <= 57343)) {
                    i3++;
                } else {
                    i4++;
                }
            }
            if (z && i3 == 0) {
                i2 = 100;
            } else if (z && i4 > i3 * 10) {
                i2 = 80;
            } else if (i4 > 3 && i3 == 0) {
                i2 = 100;
            } else if (i4 > 0 && i3 == 0) {
                i2 = 80;
            } else if (i4 > i3 * 10) {
                i2 = 25;
            }
            if (i2 == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, i2);
        }
    }

    static class CharsetRecog_UTF_32_BE extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_BE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32
        int getChar(byte[] bArr, int i) {
            return ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255);
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32, com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-32BE";
        }
    }

    static class CharsetRecog_UTF_32_LE extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_LE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32
        int getChar(byte[] bArr, int i) {
            return ((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32, com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-32LE";
        }
    }
}
