package com.xiaomi.ai.streamplayer;

import com.xiaomi.ai.AsrRequest;
import com.xiaomi.ai.utils.Log;
import defpackage.R2;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedInputStream;
import java.util.HashMap;
import miuix.media.Mp3Encoder;

/* JADX INFO: loaded from: classes4.dex */
class b extends i {
    public static final String a = "MiSpeechSDK:FrameParser";
    private static final String e = "Layer I";
    private static final String f = "Layer II";
    private static final String g = "Layer III";
    private static final String h = "MPEG2.5";
    private static final String i = "MPEG2";
    private static final String j = "MPEG1";
    private static final String k = "MPEG_UNKNOW";
    private static final String l = "Check bit 1";
    private static final String m = "Check bit 0";
    private static final String n = "Layer unkown";
    private static final char o = '1';
    private static final char p = '0';
    private static final int q = 1;
    private static final int r = 2;
    private static final int s = 0;
    private static final HashMap<String, Integer> t;
    private static final HashMap<String, Integer> u;
    private Thread v = new c(this);

    public static class a {
        String a;
        String b;
        int c;
        int d;
        int e;
        int f;
        int g;

        boolean a() {
            return this.a != b.n && this.b != b.k && this.f > 0 && this.c > 0 && this.d > 0 && this.g > 0 && this.e > 0;
        }

        public String toString() {
            return "layer=" + this.a + ";mpeg=" + this.b + ";sampleRate=" + this.c + ";bitrate=" + this.d + ";sampleRateCount=" + this.e + ";frameSize=" + this.f + "channel=" + this.g;
        }
    }

    static {
        HashMap<String, Integer> map = new HashMap<>();
        t = map;
        HashMap<String, Integer> map2 = new HashMap<>();
        u = map2;
        map.put(a(g, i, new char[]{p, p, o, o}, 0, 4), 128000);
        map.put(a(g, i, new char[]{p, o, p, p}, 0, 4), 16000);
        map.put(a(g, i, new char[]{p, p, o, p}, 0, 4), Integer.valueOf(AsrRequest.OPUS_BITRATES_32K));
        map2.put(a(g, j, null, 0, 0), Integer.valueOf(R2.attr.flow_horizontalStyle));
        String strA = a(g, i, null, 0, 0);
        Integer numValueOf = Integer.valueOf(R2.attr.cardGroupMarginTop);
        map2.put(strA, numValueOf);
        map2.put(a(g, h, null, 0, 0), numValueOf);
    }

    b() {
    }

    private static String a(byte b) {
        String binaryString = Integer.toBinaryString(b | 256);
        int length = binaryString.length();
        return binaryString.substring(length - 8, length);
    }

    public static String a(String str, String str2, char[] cArr, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            sb.append(cArr[i4]);
        }
        return sb.toString();
    }

    private static boolean a(char[] cArr) {
        for (int i2 = 21; i2 < 31; i2++) {
            if (cArr[i2] != '1') {
                return false;
            }
        }
        return true;
    }

    private static char[] a(byte[] bArr) {
        char[] cArrB = b(bArr);
        char[] cArr = new char[cArrB.length];
        for (int i2 = 0; i2 < cArrB.length; i2++) {
            cArr[(cArrB.length - i2) - 1] = cArrB[i2];
        }
        return cArr;
    }

    private static int b(char[] cArr) {
        char c = cArr[7];
        if (c == '0' && cArr[6] == '0') {
            return 2;
        }
        return (c == '1' && cArr[6] == '1') ? 1 : 0;
    }

    private static char[] b(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%s", a(b)));
        }
        return sb.toString().toCharArray();
    }

    private static int c(char[] cArr) {
        Integer num = u.get(a(g(cArr), i(cArr), null, 0, 0));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    private static int d(char[] cArr) {
        Integer num = t.get(a(g(cArr), i(cArr), cArr, 12, 4));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x006d A[Catch: all -> 0x00e5, IOException -> 0x00e7, TryCatch #0 {IOException -> 0x00e7, blocks: (B:3:0x0005, B:5:0x000b, B:6:0x0010, B:7:0x0014, B:13:0x0021, B:15:0x0027, B:18:0x0030, B:19:0x003d, B:23:0x005e, B:25:0x006d, B:26:0x0097, B:28:0x009d, B:30:0x00ac, B:32:0x00b4, B:34:0x00bd, B:35:0x00c5, B:37:0x00cd, B:39:0x00d1, B:20:0x0046, B:22:0x0050), top: B:63:0x0005, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x00ac A[Catch: all -> 0x00e5, IOException -> 0x00e7, TryCatch #0 {IOException -> 0x00e7, blocks: (B:3:0x0005, B:5:0x000b, B:6:0x0010, B:7:0x0014, B:13:0x0021, B:15:0x0027, B:18:0x0030, B:19:0x003d, B:23:0x005e, B:25:0x006d, B:26:0x0097, B:28:0x009d, B:30:0x00ac, B:32:0x00b4, B:34:0x00bd, B:35:0x00c5, B:37:0x00cd, B:39:0x00d1, B:20:0x0046, B:22:0x0050), top: B:63:0x0005, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x00bd A[Catch: all -> 0x00e5, IOException -> 0x00e7, EDGE_INSN: B:34:0x00bd->B:36:0x00cb BREAK  A[LOOP:2: B:29:0x00aa->B:35:0x00c5], TryCatch #0 {IOException -> 0x00e7, blocks: (B:3:0x0005, B:5:0x000b, B:6:0x0010, B:7:0x0014, B:13:0x0021, B:15:0x0027, B:18:0x0030, B:19:0x003d, B:23:0x005e, B:25:0x006d, B:26:0x0097, B:28:0x009d, B:30:0x00ac, B:32:0x00b4, B:34:0x00bd, B:35:0x00c5, B:37:0x00cd, B:39:0x00d1, B:20:0x0046, B:22:0x0050), top: B:63:0x0005, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x00c5 A[Catch: all -> 0x00e5, IOException -> 0x00e7, LOOP:2: B:29:0x00aa->B:35:0x00c5, LOOP_END, TryCatch #0 {IOException -> 0x00e7, blocks: (B:3:0x0005, B:5:0x000b, B:6:0x0010, B:7:0x0014, B:13:0x0021, B:15:0x0027, B:18:0x0030, B:19:0x003d, B:23:0x005e, B:25:0x006d, B:26:0x0097, B:28:0x009d, B:30:0x00ac, B:32:0x00b4, B:34:0x00bd, B:35:0x00c5, B:37:0x00cd, B:39:0x00d1, B:20:0x0046, B:22:0x0050), top: B:63:0x0005, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x00d8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:0x009d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00b4 A[SYNTHETIC] */
    public void d() {
        StringBuilder sbAppend;
        char[] cArrA;
        a aVar;
        byte[] bArr;
        int i2;
        byte[] bArr2;
        int i3;
        int i4;
        int i5;
        int i6;
        PipedInputStream pipedInputStream = this.c;
        try {
            try {
                byte[] bArr3 = new byte[4];
                if (this.b != null) {
                    this.b.a(this);
                }
                byte[] bArr4 = new byte[4];
                boolean z = false;
                while (!this.d) {
                    if (z) {
                        System.arraycopy(bArr3, 1, bArr3, 0, 3);
                        int i7 = pipedInputStream.read(bArr3, 3, 1);
                        if (i7 <= 0) {
                            sbAppend = new StringBuilder().append("doParserFrame:read end ret=").append(i7);
                            Log.e(a, sbAppend.toString());
                            break;
                        }
                        cArrA = a(bArr3);
                        aVar = new a();
                        if (a(cArrA)) {
                            aVar.a = g(cArrA);
                            aVar.b = i(cArrA);
                            aVar.d = d(cArrA);
                            aVar.c = f(cArrA);
                            aVar.e = c(cArrA);
                            aVar.f = e(cArrA);
                            aVar.g = b(cArrA);
                        }
                        if (aVar.a()) {
                            int i8 = aVar.f;
                            bArr = new byte[i8];
                            i2 = i8 - 4;
                            bArr2 = new byte[i8];
                            System.arraycopy(bArr3, 0, bArr, 0, 4);
                            i3 = 4;
                            i4 = 0;
                            while (i2 != i4) {
                                i5 = pipedInputStream.read(bArr2, 0, i2 - i4);
                                if (i5 < 0) {
                                    Log.e(a, "doParserFrame:read end frame data miss ");
                                    if (this.b != null) {
                                        break;
                                    }
                                    this.b.a(this, "frame  misss ");
                                    break;
                                }
                                i4 += i5;
                                System.arraycopy(bArr2, 0, bArr, i3, i5);
                                i3 += i5;
                            }
                            if (i2 != i4) {
                            }
                        } else {
                            z = true;
                        }
                    } else {
                        int i9 = 0;
                        int i10 = 0;
                        do {
                            i6 = pipedInputStream.read(bArr4, 0, 4 - i9);
                            if (i6 <= 0) {
                                break;
                            }
                            i9 += i6;
                            System.arraycopy(bArr4, 0, bArr3, i10, i6);
                            i10 += i6;
                        } while (i9 != 4);
                        if (i6 <= 0) {
                            sbAppend = new StringBuilder().append("doParserFrame:read end ret=").append(i6);
                            Log.e(a, sbAppend.toString());
                            break;
                        }
                        cArrA = a(bArr3);
                        aVar = new a();
                        if (a(cArrA)) {
                            aVar.a = g(cArrA);
                            aVar.b = i(cArrA);
                            aVar.d = d(cArrA);
                            aVar.c = f(cArrA);
                            aVar.e = c(cArrA);
                            aVar.f = e(cArrA);
                            aVar.g = b(cArrA);
                        }
                        if (aVar.a()) {
                            int i11 = aVar.f;
                            bArr = new byte[i11];
                            i2 = i11 - 4;
                            bArr2 = new byte[i11];
                            System.arraycopy(bArr3, 0, bArr, 0, 4);
                            i3 = 4;
                            i4 = 0;
                            while (i2 != i4) {
                                i5 = pipedInputStream.read(bArr2, 0, i2 - i4);
                                if (i5 < 0) {
                                    Log.e(a, "doParserFrame:read end frame data miss ");
                                    if (this.b != null) {
                                        break;
                                    }
                                    this.b.a(this, "frame  misss ");
                                    break;
                                }
                                i4 += i5;
                                System.arraycopy(bArr2, 0, bArr, i3, i5);
                                i3 += i5;
                            }
                            if (i2 != i4 && this.b != null) {
                                this.b.a(this, aVar, bArr);
                            }
                        } else {
                            z = true;
                        }
                    }
                }
                if (this.b != null) {
                    this.b.b(this);
                }
            } catch (IOException e2) {
                if (e2 instanceof InterruptedIOException) {
                    Log.e(a, "InterruptedIOException");
                } else {
                    Log.e(a, "doParserFrame", e2);
                    if (this.b != null) {
                        this.b.a(this, e2.getMessage());
                    }
                }
                if (this.b != null) {
                    this.b.b(this);
                }
            }
        } catch (Throwable th) {
            if (this.b != null) {
                this.b.b(this);
            }
            throw th;
        }
    }

    private static int e(char[] cArr) {
        if (g(cArr) == e) {
            return ((int) Math.floor(((c(cArr) / 8.0f) * d(cArr)) / f(cArr))) + (cArr[9] == '1' ? 4 : 0);
        }
        return ((int) Math.floor(((c(cArr) / 8.0f) * d(cArr)) / f(cArr))) + (cArr[9] == '1' ? 1 : 0);
    }

    private static int f(char[] cArr) {
        char c = cArr[20];
        if (c == '0' && cArr[19] == '0') {
            char c2 = cArr[11];
            if (c2 == '0' && cArr[10] == '0') {
                return R2.drawable.miuix_appcompat_action_mode_title_button_bg_pressed_dark;
            }
            if (c2 == '0' && cArr[10] == '1') {
                return 12000;
            }
            if (c2 == '1' && cArr[10] == '0') {
                return R2.dimen.miuix_appcompat_action_bar_subtitle_bg_end_margin;
            }
            return -1;
        }
        if (c == '1' && cArr[19] == '0') {
            char c3 = cArr[11];
            if (c3 == '0' && cArr[10] == '0') {
                return R2.styleable.StateSet_defaultState;
            }
            if (c3 == '0' && cArr[10] == '1') {
                return 24000;
            }
            return (c3 == '1' && cArr[10] == '0') ? 16000 : -1;
        }
        if (c == '1' && cArr[19] == '1') {
            char c4 = cArr[11];
            if (c4 == '0' && cArr[10] == '0') {
                return Mp3Encoder.DEFAULT_SAMPLE_RATE;
            }
            if (c4 == '0' && cArr[10] == '1') {
                return 48000;
            }
            if (c4 == '1' && cArr[10] == '0') {
                return AsrRequest.OPUS_BITRATES_32K;
            }
        }
        return -1;
    }

    private static String g(char[] cArr) {
        char c = cArr[18];
        if (c == '0' && cArr[17] == '1') {
            return g;
        }
        if (c == '1' && cArr[17] == '0') {
            return f;
        }
        return (c == '1' && cArr[17] == '1') ? e : n;
    }

    private static String h(char[] cArr) {
        return cArr[16] == '0' ? m : l;
    }

    private static String i(char[] cArr) {
        char c = cArr[20];
        if (c == '0' && cArr[19] == '0') {
            return h;
        }
        if (c == '1' && cArr[19] == '0') {
            return i;
        }
        return (c == '1' && cArr[19] == '1') ? j : k;
    }

    @Override // com.xiaomi.ai.streamplayer.i
    public void a() {
        super.a();
        this.v.start();
    }

    @Override // com.xiaomi.ai.streamplayer.i
    public void b() {
        super.b();
        this.d = true;
        this.v.interrupt();
    }
}
