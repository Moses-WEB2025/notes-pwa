package com.ibm.icu.text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/* JADX INFO: loaded from: classes2.dex */
public class CharsetMatch implements Comparable<CharsetMatch> {
    private String fCharsetName;
    private int fConfidence;
    private InputStream fInputStream;
    private String fLang;
    private byte[] fRawInput;
    private int fRawLength;

    public Reader getReader() {
        InputStream byteArrayInputStream = this.fInputStream;
        if (byteArrayInputStream == null) {
            byteArrayInputStream = new ByteArrayInputStream(this.fRawInput, 0, this.fRawLength);
        }
        try {
            byteArrayInputStream.reset();
            return new InputStreamReader(byteArrayInputStream, getName());
        } catch (IOException unused) {
            return null;
        }
    }

    public String getString() throws IOException {
        return getString(-1);
    }

    public String getString(int i) throws IOException {
        if (this.fInputStream != null) {
            StringBuilder sb = new StringBuilder();
            char[] cArr = new char[1024];
            Reader reader = getReader();
            if (i < 0) {
                i = Integer.MAX_VALUE;
            }
            while (true) {
                int i2 = reader.read(cArr, 0, Math.min(i, 1024));
                if (i2 >= 0) {
                    sb.append(cArr, 0, i2);
                    i -= i2;
                } else {
                    reader.close();
                    return sb.toString();
                }
            }
        } else {
            String name = getName();
            int iIndexOf = name.indexOf(name.indexOf("_rtl") < 0 ? "_ltr" : "_rtl");
            if (iIndexOf > 0) {
                name = name.substring(0, iIndexOf);
            }
            return new String(this.fRawInput, name);
        }
    }

    public int getConfidence() {
        return this.fConfidence;
    }

    public String getName() {
        return this.fCharsetName;
    }

    public String getLanguage() {
        return this.fLang;
    }

    @Override // java.lang.Comparable
    public int compareTo(CharsetMatch charsetMatch) {
        int i = this.fConfidence;
        int i2 = charsetMatch.fConfidence;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int i) {
        this.fRawInput = null;
        this.fInputStream = null;
        this.fConfidence = i;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = charsetRecognizer.getName();
        this.fLang = charsetRecognizer.getLanguage();
    }

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int i, String str, String str2) {
        this.fRawInput = null;
        this.fInputStream = null;
        this.fConfidence = i;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = str;
        this.fLang = str2;
    }
}
