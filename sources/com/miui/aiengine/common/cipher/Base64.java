package com.miui.aiengine.common.cipher;

import cn.kuaipan.android.http.multipart.StringPart;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: Base64.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003\u0015\u0016\u0017B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0005J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J\u0016\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0005J&\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0005J&\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/aiengine/common/cipher/Base64;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DEFAULT", "", "NO_PADDING", "NO_WRAP", "CRLF", "URL_SAFE", "NO_CLOSE", "decode", "", "str", "", "flags", HtmlParser.InputElement.TAG, "offset", "len", "encodeToString", "encode", "Coder", "Decoder", "Encoder", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Base64 {
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final Base64 INSTANCE = new Base64();
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    private Base64() {
    }

    public final byte[] decode(String str, int flags) {
        Intrinsics.checkNotNullParameter(str, "str");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return decode(bytes, flags);
    }

    public final byte[] decode(byte[] input, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        return decode(input, 0, input.length, flags);
    }

    public final byte[] decode(byte[] input, int offset, int len, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        Decoder decoder = new Decoder(flags, new byte[(len * 3) / 4]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64".toString());
        }
        if (decoder.getOp() == decoder.getOutput().length) {
            return decoder.getOutput();
        }
        byte[] bArr = new byte[decoder.getOp()];
        System.arraycopy(decoder.getOutput(), 0, bArr, 0, decoder.getOp());
        return bArr;
    }

    public final String encodeToString(byte[] input, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        try {
            byte[] bArrEncode = encode(input, flags);
            Charset charsetForName = Charset.forName(StringPart.DEFAULT_CHARSET);
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            return new String(bArrEncode, charsetForName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public final String encodeToString(byte[] input, int offset, int len, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        try {
            byte[] bArrEncode = encode(input, offset, len, flags);
            Charset charsetForName = Charset.forName(StringPart.DEFAULT_CHARSET);
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            return new String(bArrEncode, charsetForName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public final byte[] encode(byte[] input, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        return encode(input, 0, input.length, flags);
    }

    public final byte[] encode(byte[] input, int offset, int len, int flags) {
        Intrinsics.checkNotNullParameter(input, "input");
        Encoder encoder = new Encoder(flags, null);
        int i = (len / 3) * 4;
        if (encoder.getDo_padding()) {
            if (len % 3 > 0) {
                i += 4;
            }
        } else {
            int i2 = len % 3;
            if (i2 == 1) {
                i += 2;
            } else if (i2 == 2) {
                i += 3;
            }
        }
        if (encoder.getDo_newline() && len > 0) {
            i += (((len - 1) / 57) + 1) * (encoder.getDo_cr() ? 2 : 1);
        }
        encoder.setOutput(new byte[i]);
        encoder.process(input, offset, len, true);
        encoder.getOp();
        byte[] output = encoder.getOutput();
        Intrinsics.checkNotNull(output);
        return output;
    }

    /* JADX INFO: compiled from: Base64.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\b \u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0011H&J\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH&R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/miui/aiengine/common/cipher/Base64$Coder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "output", "", "getOutput", "()[B", "setOutput", "([B)V", "op", "", "getOp", "()I", "setOp", "(I)V", "process", "", HtmlParser.InputElement.TAG, "offset", "len", "finish", "maxOutputSize", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class Coder {
        private int op;
        public byte[] output;

        public abstract int maxOutputSize(int len);

        public abstract boolean process(byte[] input, int offset, int len, boolean finish);

        public final byte[] getOutput() {
            byte[] bArr = this.output;
            if (bArr != null) {
                return bArr;
            }
            Intrinsics.throwUninitializedPropertyAccessException("output");
            return null;
        }

        public final void setOutput(byte[] bArr) {
            Intrinsics.checkNotNullParameter(bArr, "<set-?>");
            this.output = bArr;
        }

        public final int getOp() {
            return this.op;
        }

        public final void setOp(int i) {
            this.op = i;
        }
    }

    /* JADX INFO: compiled from: Base64.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u000fH\u0016R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/aiengine/common/cipher/Base64$Decoder;", "Lcom/miui/aiengine/common/cipher/Base64$Coder;", "flags", "", "output", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I[B)V", "state", "value", "alphabet", "", "maxOutputSize", "len", "process", "", HtmlParser.InputElement.TAG, "offset", "finish", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int i, byte[] bArr) {
            Intrinsics.checkNotNull(bArr);
            setOutput(bArr);
            this.alphabet = (i & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // com.miui.aiengine.common.cipher.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 3) / 4) + 10;
        }

        /* JADX WARN: Code duplicated, block: B:51:0x00e7  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v10 */
        /* JADX WARN: Type inference failed for: r6v13 */
        /* JADX WARN: Type inference failed for: r6v18 */
        /* JADX WARN: Type inference failed for: r6v19 */
        /* JADX WARN: Type inference failed for: r6v2, types: [int] */
        /* JADX WARN: Type inference failed for: r6v3, types: [int] */
        /* JADX WARN: Type inference failed for: r6v4 */
        /* JADX WARN: Type inference failed for: r6v5 */
        /* JADX WARN: Type inference failed for: r6v6 */
        /* JADX WARN: Type inference failed for: r6v7, types: [int] */
        @Override // com.miui.aiengine.common.cipher.Base64.Coder
        public boolean process(byte[] input, int offset, int len, boolean finish) {
            boolean z;
            Intrinsics.checkNotNullParameter(input, "input");
            int i = this.state;
            boolean z2 = false;
            if (i == 6) {
                return false;
            }
            int i2 = len + offset;
            int i3 = this.value;
            byte[] output = getOutput();
            int[] iArr = this.alphabet;
            int i4 = 0;
            int i5 = i3;
            ?? r6 = i;
            int i6 = offset;
            while (true) {
                if (i6 >= i2) {
                    z = z2;
                    break;
                }
                if (r6 == 0) {
                    while (true) {
                        int i7 = i6 + 4;
                        if (i7 > i2) {
                            z = z2;
                            break;
                        }
                        z = z2;
                        i5 = iArr[input[i6 + 3] & 255] | (iArr[input[i6 + 1] & 255] << 12) | (iArr[input[i6] & 255] << 18) | (iArr[input[i6 + 2] & 255] << 6);
                        if (i5 < 0) {
                            break;
                        }
                        output[i4 + 2] = (byte) i5;
                        output[i4 + 1] = (byte) (i5 >> 8);
                        output[i4] = (byte) (i5 >> 16);
                        i4 += 3;
                        i6 = i7;
                        z2 = z;
                    }
                    if (i6 >= i2) {
                        break;
                    }
                } else {
                    z = z2;
                }
                i6++;
                int i8 = iArr[input[i6] & 255];
                if (r6 != 0) {
                    if (r6 != 1) {
                        if (r6 == 2) {
                            if (i8 >= 0) {
                                i5 = (i5 << 6) | i8;
                                r6++;
                            } else if (i8 == -2) {
                                output[i4] = (byte) (i5 >> 4);
                                i4++;
                                r6 = 4;
                            } else if (i8 != -1) {
                                this.state = 6;
                                return z;
                            }
                            z2 = z;
                        } else if (r6 != 3) {
                            if (r6 != 4) {
                                if (r6 == 5 && i8 != -1) {
                                    this.state = 6;
                                    return z;
                                }
                            } else if (i8 == -2) {
                                r6++;
                            } else if (i8 != -1) {
                                this.state = 6;
                                return z;
                            }
                        } else if (i8 >= 0) {
                            i5 = (i5 << 6) | i8;
                            output[i4 + 2] = (byte) i5;
                            output[i4 + 1] = (byte) (i5 >> 8);
                            output[i4] = (byte) (i5 >> 16);
                            i4 += 3;
                            i6 = i6;
                            z2 = z;
                            r6 = z2;
                        } else {
                            if (i8 == -2) {
                                output[i4 + 1] = (byte) (i5 >> 2);
                                output[i4] = (byte) (i5 >> 10);
                                i4 += 2;
                                r6 = 5;
                            } else if (i8 != -1) {
                                this.state = 6;
                                return z;
                            }
                            z2 = z;
                        }
                    } else if (i8 >= 0) {
                        i5 = (i5 << 6) | i8;
                        r6++;
                    } else if (i8 != -1) {
                        this.state = 6;
                        return z;
                    }
                } else if (i8 >= 0) {
                    r6++;
                    i5 = i8;
                } else if (i8 != -1) {
                    this.state = 6;
                    return z;
                }
                r6 = r6;
                z2 = z;
            }
            if (!finish) {
                this.state = r6;
                this.value = i5;
                setOp(i4);
                return true;
            }
            if (r6 == 1) {
                this.state = 6;
                return z;
            }
            if (r6 == 2) {
                output[i4] = (byte) (i5 >> 4);
                i4++;
            } else if (r6 == 3) {
                int i9 = i4 + 1;
                output[i4] = (byte) (i5 >> 10);
                i4 += 2;
                output[i9] = (byte) (i5 >> 2);
            } else if (r6 == 4) {
                this.state = 6;
                return z;
            }
            this.state = r6;
            setOp(i4);
            return true;
        }
    }

    /* JADX INFO: compiled from: Base64.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0000\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003H\u0016J(\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0010H\u0016R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/aiengine/common/cipher/Base64$Encoder;", "Lcom/miui/aiengine/common/cipher/Base64$Coder;", "flags", "", "output", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I[B)V", "tail", "tailLen", "getTailLen", "()I", "setTailLen", "(I)V", "count", "do_padding", "", "getDo_padding", "()Z", "do_newline", "getDo_newline", "do_cr", "getDo_cr", "alphabet", "maxOutputSize", "len", "process", HtmlParser.InputElement.TAG, "offset", "finish", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Encoder extends Coder {
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        private final boolean do_cr;
        private final boolean do_newline;
        private final boolean do_padding;
        private final byte[] tail;
        private int tailLen;

        public Encoder(int i, byte[] bArr) {
            if (bArr != null) {
                setOutput(bArr);
            }
            this.do_padding = (i & 1) == 0;
            boolean z = (i & 2) == 0;
            this.do_newline = z;
            this.do_cr = (i & 4) != 0;
            this.alphabet = (i & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = z ? 19 : -1;
        }

        public final int getTailLen() {
            return this.tailLen;
        }

        public final void setTailLen(int i) {
            this.tailLen = i;
        }

        public final boolean getDo_padding() {
            return this.do_padding;
        }

        public final boolean getDo_newline() {
            return this.do_newline;
        }

        public final boolean getDo_cr() {
            return this.do_cr;
        }

        @Override // com.miui.aiengine.common.cipher.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 8) / 5) + 10;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0059  */
        @Override // com.miui.aiengine.common.cipher.Base64.Coder
        public boolean process(byte[] input, int offset, int len, boolean finish) {
            int i;
            int i2;
            int i3;
            int i4;
            byte b;
            int i5;
            byte b2;
            byte b3;
            int i6;
            int i7;
            int i8;
            Intrinsics.checkNotNullParameter(input, "input");
            byte[] bArr = this.alphabet;
            byte[] output = getOutput();
            int i9 = this.count;
            int i10 = len + offset;
            int i11 = this.tailLen;
            char c = 2;
            int i12 = 0;
            if (i11 != 1) {
                if (i11 == 2 && (i8 = offset + 1) <= i10) {
                    byte[] bArr2 = this.tail;
                    i2 = ((bArr2[1] & 255) << 8) | ((bArr2[0] & 255) << 16) | (input[offset] & 255);
                    this.tailLen = 0;
                    i = i8;
                } else {
                    i = offset;
                    i2 = -1;
                }
            } else if (offset + 2 <= i10) {
                i = offset + 2;
                i2 = (input[offset + 1] & 255) | ((this.tail[0] & 255) << 16) | ((input[offset] & 255) << 8);
                this.tailLen = 0;
            } else {
                i = offset;
                i2 = -1;
            }
            if (i2 != -1) {
                output[0] = bArr[(i2 >> 18) & 63];
                output[1] = bArr[(i2 >> 12) & 63];
                output[2] = bArr[(i2 >> 6) & 63];
                output[3] = bArr[i2 & 63];
                i9--;
                if (i9 == 0) {
                    if (this.do_cr) {
                        output[4] = 13;
                        i7 = 5;
                    } else {
                        i7 = 4;
                    }
                    i3 = i7 + 1;
                    output[i7] = 10;
                    i9 = 19;
                } else {
                    i3 = 4;
                }
            } else {
                i3 = 0;
            }
            while (true) {
                i += 3;
                if (i > i10) {
                    break;
                }
                c = c;
                i12 = i12;
                int i13 = ((input[i] & 255) << 16) | ((input[i + 1] & 255) << 8) | (input[i + 2] & 255);
                output[i3] = bArr[(i13 >> 18) & 63];
                output[i3 + 1] = bArr[(i13 >> 12) & 63];
                output[i3 + 2] = bArr[(i13 >> 6) & 63];
                output[i3 + 3] = bArr[i13 & 63];
                int i14 = i3 + 4;
                i9--;
                if (i9 == 0) {
                    if (this.do_cr) {
                        output[i14] = 13;
                        i14 = i3 + 5;
                    }
                    i3 = i14 + 1;
                    output[i14] = 10;
                    i9 = 19;
                } else {
                    i3 = i14;
                }
            }
            int i15 = i12;
            if (finish) {
                int i16 = this.tailLen;
                if (i - i16 == i10 - 1) {
                    if (i16 > 0) {
                        b3 = this.tail[i15];
                        i6 = 1;
                    } else {
                        b3 = input[i];
                        i6 = i15;
                    }
                    int i17 = (b3 & 255) << 4;
                    this.tailLen = i16 - i6;
                    output[i3] = bArr[(i17 >> 6) & 63];
                    int i18 = i3 + 2;
                    output[i3 + 1] = bArr[i17 & 63];
                    if (this.do_padding) {
                        int i19 = i3 + 3;
                        output[i18] = kotlin.io.encoding.Base64.padSymbol;
                        i3 += 4;
                        output[i19] = kotlin.io.encoding.Base64.padSymbol;
                    } else {
                        i3 = i18;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            output[i3] = 13;
                            i3++;
                        }
                        i4 = i3 + 1;
                        output[i3] = 10;
                        i3 = i4;
                    }
                } else if (i - i16 == i10 - 2) {
                    if (i16 > 1) {
                        b = this.tail[i15];
                        i5 = 1;
                    } else {
                        byte b4 = input[i];
                        i++;
                        b = b4;
                        i5 = i15;
                    }
                    int i20 = (b & 255) << 10;
                    if (i16 > 0) {
                        b2 = this.tail[i5];
                        i5++;
                    } else {
                        b2 = input[i];
                    }
                    int i21 = ((b2 & 255) << 2) | i20;
                    this.tailLen = i16 - i5;
                    output[i3] = bArr[(i21 >> 12) & 63];
                    output[i3 + 1] = bArr[(i21 >> 6) & 63];
                    int i22 = i3 + 3;
                    output[i3 + 2] = bArr[i21 & 63];
                    if (this.do_padding) {
                        i3 += 4;
                        output[i22] = kotlin.io.encoding.Base64.padSymbol;
                    } else {
                        i3 = i22;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            output[i3] = 13;
                            i3++;
                        }
                        i4 = i3 + 1;
                        output[i3] = 10;
                        i3 = i4;
                    }
                } else if (this.do_newline && i3 > 0 && i9 != 19) {
                    if (this.do_cr) {
                        output[i3] = 13;
                        i3++;
                    }
                    i4 = i3 + 1;
                    output[i3] = 10;
                    i3 = i4;
                }
            } else if (i == i10 - 1) {
                byte[] bArr3 = this.tail;
                int i23 = this.tailLen;
                this.tailLen = i23 + 1;
                bArr3[i23] = input[i];
            } else if (i == i10 - 2) {
                byte[] bArr4 = this.tail;
                int i24 = this.tailLen;
                int i25 = i24 + 1;
                this.tailLen = i25;
                bArr4[i24] = input[i];
                this.tailLen = i24 + 2;
                bArr4[i25] = input[i + 1];
            }
            setOp(i3);
            this.count = i9;
            return true;
        }
    }
}
