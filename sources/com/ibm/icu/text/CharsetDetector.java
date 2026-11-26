package com.ibm.icu.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CharsetDetector {
    private static final List<CSRecognizerInfo> ALL_CS_RECOGNIZERS;
    private static final int kBufSize = 8000;
    String fDeclaredEncoding;
    private boolean[] fEnabledRecognizers;
    int fInputLen;
    InputStream fInputStream;
    byte[] fRawInput;
    int fRawLength;
    byte[] fInputBytes = new byte[8000];
    short[] fByteStats = new short[256];
    boolean fC1Bytes = false;
    private boolean fStripTags = false;

    public CharsetDetector setDeclaredEncoding(String str) {
        this.fDeclaredEncoding = str;
        return this;
    }

    public CharsetDetector setText(byte[] bArr) {
        this.fRawInput = bArr;
        this.fRawLength = bArr.length;
        return this;
    }

    public CharsetDetector setText(InputStream inputStream) throws IOException {
        this.fInputStream = inputStream;
        int i = 8000;
        inputStream.mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        while (i > 0) {
            int i2 = this.fInputStream.read(this.fRawInput, this.fRawLength, i);
            if (i2 <= 0) {
                break;
            }
            this.fRawLength += i2;
            i -= i2;
        }
        this.fInputStream.reset();
        return this;
    }

    public CharsetMatch detect() {
        CharsetMatch[] charsetMatchArrDetectAll = detectAll();
        if (charsetMatchArrDetectAll == null || charsetMatchArrDetectAll.length == 0) {
            return null;
        }
        return charsetMatchArrDetectAll[0];
    }

    public CharsetMatch[] detectAll() {
        CharsetMatch charsetMatchMatch;
        ArrayList arrayList = new ArrayList();
        MungeInput();
        int i = 0;
        while (true) {
            List<CSRecognizerInfo> list = ALL_CS_RECOGNIZERS;
            if (i < list.size()) {
                CSRecognizerInfo cSRecognizerInfo = list.get(i);
                boolean[] zArr = this.fEnabledRecognizers;
                if ((zArr != null ? zArr[i] : cSRecognizerInfo.isDefaultEnabled) && (charsetMatchMatch = cSRecognizerInfo.recognizer.match(this)) != null) {
                    arrayList.add(charsetMatchMatch);
                }
                i++;
            } else {
                Collections.sort(arrayList);
                Collections.reverse(arrayList);
                return (CharsetMatch[]) arrayList.toArray(new CharsetMatch[arrayList.size()]);
            }
        }
    }

    public Reader getReader(InputStream inputStream, String str) {
        this.fDeclaredEncoding = str;
        try {
            setText(inputStream);
            CharsetMatch charsetMatchDetect = detect();
            if (charsetMatchDetect == null) {
                return null;
            }
            return charsetMatchDetect.getReader();
        } catch (IOException unused) {
            return null;
        }
    }

    public String getString(byte[] bArr, String str) {
        this.fDeclaredEncoding = str;
        try {
            setText(bArr);
            CharsetMatch charsetMatchDetect = detect();
            if (charsetMatchDetect == null) {
                return null;
            }
            return charsetMatchDetect.getString(-1);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String[] getAllDetectableCharsets() {
        int size = ALL_CS_RECOGNIZERS.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ALL_CS_RECOGNIZERS.get(i).recognizer.getName();
        }
        return strArr;
    }

    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }

    public boolean enableInputFilter(boolean z) {
        boolean z2 = this.fStripTags;
        this.fStripTags = z;
        return z2;
    }

    private void MungeInput() {
        int i;
        int i2;
        if (this.fStripTags) {
            int i3 = 0;
            i = 0;
            i2 = 0;
            boolean z = false;
            for (int i4 = 0; i4 < this.fRawLength; i4++) {
                byte[] bArr = this.fInputBytes;
                if (i3 >= bArr.length) {
                    break;
                }
                byte b = this.fRawInput[i4];
                if (b == 60) {
                    if (z) {
                        i2++;
                    }
                    i++;
                    z = true;
                }
                if (!z) {
                    bArr[i3] = b;
                    i3++;
                }
                if (b == 62) {
                    z = false;
                }
            }
            this.fInputLen = i3;
        } else {
            i = 0;
            i2 = 0;
        }
        if (i < 5 || i / 5 < i2 || (this.fInputLen < 100 && this.fRawLength > 600)) {
            int i5 = this.fRawLength;
            if (i5 > 8000) {
                i5 = 8000;
            }
            int i6 = 0;
            while (i6 < i5) {
                this.fInputBytes[i6] = this.fRawInput[i6];
                i6++;
            }
            this.fInputLen = i6;
        }
        Arrays.fill(this.fByteStats, (short) 0);
        for (int i7 = 0; i7 < this.fInputLen; i7++) {
            int i8 = this.fInputBytes[i7] & 255;
            short[] sArr = this.fByteStats;
            sArr[i8] = (short) (sArr[i8] + 1);
        }
        this.fC1Bytes = false;
        for (int i9 = 128; i9 <= 159; i9++) {
            if (this.fByteStats[i9] != 0) {
                this.fC1Bytes = true;
                return;
            }
        }
    }

    private static class CSRecognizerInfo {
        boolean isDefaultEnabled;
        CharsetRecognizer recognizer;

        CSRecognizerInfo(CharsetRecognizer charsetRecognizer, boolean z) {
            this.recognizer = charsetRecognizer;
            this.isDefaultEnabled = z;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_UTF8(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022JP(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022CN(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022KR(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_big5(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
        ALL_CS_RECOGNIZERS = Collections.unmodifiableList(arrayList);
    }

    @Deprecated
    public String[] getDetectableCharsets() {
        ArrayList arrayList = new ArrayList(ALL_CS_RECOGNIZERS.size());
        int i = 0;
        while (true) {
            List<CSRecognizerInfo> list = ALL_CS_RECOGNIZERS;
            if (i < list.size()) {
                CSRecognizerInfo cSRecognizerInfo = list.get(i);
                boolean[] zArr = this.fEnabledRecognizers;
                if (zArr == null ? cSRecognizerInfo.isDefaultEnabled : zArr[i]) {
                    arrayList.add(cSRecognizerInfo.recognizer.getName());
                }
                i++;
            } else {
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        }
    }

    @Deprecated
    public CharsetDetector setDetectableCharset(String str, boolean z) {
        List<CSRecognizerInfo> list;
        boolean z2;
        int i = 0;
        int i2 = 0;
        while (true) {
            list = ALL_CS_RECOGNIZERS;
            if (i2 < list.size()) {
                CSRecognizerInfo cSRecognizerInfo = list.get(i2);
                if (!cSRecognizerInfo.recognizer.getName().equals(str)) {
                    i2++;
                } else if (cSRecognizerInfo.isDefaultEnabled == z) {
                    z2 = true;
                    break;
                }
            } else {
                i2 = -1;
            }
            z2 = false;
            break;
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid encoding: \"" + str + "\"");
        }
        if (this.fEnabledRecognizers == null && !z2) {
            this.fEnabledRecognizers = new boolean[list.size()];
            while (true) {
                List<CSRecognizerInfo> list2 = ALL_CS_RECOGNIZERS;
                if (i >= list2.size()) {
                    break;
                }
                this.fEnabledRecognizers[i] = list2.get(i).isDefaultEnabled;
                i++;
            }
        }
        boolean[] zArr = this.fEnabledRecognizers;
        if (zArr != null) {
            zArr[i2] = z;
        }
        return this;
    }
}
