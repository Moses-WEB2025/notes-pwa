package okio;

import defpackage.R2;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Utf8.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\n\u001a%\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u0006\u001a\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\u0080\b\u001a\u0011\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\bH\u0080\b\u001a4\u0010\u0011\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\u001a4\u0010\u0015\u001a\u00020\u0012*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\u001a4\u0010\u0019\u001a\u00020\u0012*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\u001a4\u0010\u001b\u001a\u00020\u0004*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\u001a4\u0010\u001d\u001a\u00020\u0004*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\u001a4\u0010\u001f\u001a\u00020\u0004*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u0014H\u0080\bø\u0001\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\nX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006 "}, d2 = {"utf8Size", "", "", "beginIndex", "", "endIndex", "size", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "processUtf8Bytes", "", "yield", "Lkotlin/Function1;", "processUtf8CodePoints", "", "HIGH_SURROGATE_HEADER", "LOG_SURROGATE_HEADER", "processUtf16Chars", "MASK_2BYTES", "process2Utf8Bytes", "MASK_3BYTES", "process3Utf8Bytes", "MASK_4BYTES", "process4Utf8Bytes", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = 65533;
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final boolean isIsoControl(int i) {
        if (i < 0 || i >= 32) {
            return 127 <= i && i < 160;
        }
        return true;
    }

    public static final boolean isUtf8Continuation(byte b) {
        return (b & 192) == 128;
    }

    public static final long size(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, 0, 0, 3, null);
    }

    public static final long size(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, i, 0, 2, null);
    }

    public static /* synthetic */ long size$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return size(str, i, i2);
    }

    public static final long size(String str, int i, int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (i < 0) {
            throw new IllegalArgumentException(("beginIndex < 0: " + i).toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + i2 + " < " + i).toString());
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException(("endIndex > string.length: " + i2 + " > " + str.length()).toString());
        }
        long j = 0;
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 128) {
                j++;
            } else {
                if (cCharAt < 2048) {
                    i3 = 2;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    i3 = 3;
                } else {
                    int i4 = i + 1;
                    char cCharAt2 = i4 < i2 ? str.charAt(i4) : (char) 0;
                    if (cCharAt > 56319 || cCharAt2 < 56320 || cCharAt2 > 57343) {
                        j++;
                        i = i4;
                    } else {
                        j += (long) 4;
                        i += 2;
                    }
                }
                j += (long) i3;
            }
            i++;
        }
        return j;
    }

    public static final void processUtf8Bytes(String str, int i, int i2, Function1<? super Byte, Unit> yield) {
        int i3;
        char cCharAt;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        while (i < i2) {
            char cCharAt2 = str.charAt(i);
            if (Intrinsics.compare((int) cCharAt2, 128) < 0) {
                yield.invoke(Byte.valueOf((byte) cCharAt2));
                i++;
                while (i < i2 && Intrinsics.compare((int) str.charAt(i), 128) < 0) {
                    yield.invoke(Byte.valueOf((byte) str.charAt(i)));
                    i++;
                }
            } else {
                if (Intrinsics.compare((int) cCharAt2, 2048) < 0) {
                    yield.invoke(Byte.valueOf((byte) ((cCharAt2 >> 6) | 192)));
                    yield.invoke(Byte.valueOf((byte) ((cCharAt2 & '?') | 128)));
                } else if (55296 > cCharAt2 || cCharAt2 >= 57344) {
                    yield.invoke(Byte.valueOf((byte) ((cCharAt2 >> '\f') | R2.array.passport_account_user_gender_name)));
                    yield.invoke(Byte.valueOf((byte) (((cCharAt2 >> 6) & 63) | 128)));
                    yield.invoke(Byte.valueOf((byte) ((cCharAt2 & '?') | 128)));
                } else if (Intrinsics.compare((int) cCharAt2, 56319) > 0 || i2 <= (i3 = i + 1) || 56320 > (cCharAt = str.charAt(i3)) || cCharAt >= 57344) {
                    yield.invoke(Byte.valueOf(REPLACEMENT_BYTE));
                } else {
                    int iCharAt = ((cCharAt2 << '\n') + str.charAt(i3)) - 56613888;
                    yield.invoke(Byte.valueOf((byte) ((iCharAt >> 18) | R2.attr.StretchableWidgetStyle)));
                    yield.invoke(Byte.valueOf((byte) (((iCharAt >> 12) & 63) | 128)));
                    yield.invoke(Byte.valueOf((byte) (((iCharAt >> 6) & 63) | 128)));
                    yield.invoke(Byte.valueOf((byte) ((iCharAt & 63) | 128)));
                    i += 2;
                }
                i++;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0042  */
    public static final void processUtf8CodePoints(byte[] bArr, int i, int i2, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        while (i < i2) {
            byte b = bArr[i];
            if (b >= 0) {
                yield.invoke(Integer.valueOf(b));
                i++;
                while (i < i2) {
                    byte b2 = bArr[i];
                    if (b2 < 0) {
                        break;
                    }
                    i++;
                    yield.invoke(Integer.valueOf(b2));
                }
            } else {
                int i3 = 2;
                if ((b >> 5) == -2) {
                    int i4 = i + 1;
                    if (i2 > i4) {
                        byte b3 = bArr[i4];
                        if ((b3 & 192) == 128) {
                            int i5 = (b << 6) ^ (b3 ^ 3968);
                            yield.invoke(i5 < 128 ? Integer.valueOf(REPLACEMENT_CODE_POINT) : Integer.valueOf(i5));
                            Unit unit = Unit.INSTANCE;
                        }
                        i += i3;
                    }
                    yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                    Unit unit2 = Unit.INSTANCE;
                    i3 = 1;
                    i += i3;
                } else if ((b >> 4) == -2) {
                    int i6 = i + 2;
                    if (i2 <= i6) {
                        yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        Unit unit3 = Unit.INSTANCE;
                        int i7 = i + 1;
                        if (i2 <= i7 || (bArr[i7] & 192) != 128) {
                            i3 = 1;
                        }
                    } else {
                        byte b4 = bArr[i + 1];
                        if ((b4 & 192) == 128) {
                            byte b5 = bArr[i6];
                            if ((b5 & 192) == 128) {
                                int i8 = (b << 12) ^ ((b5 ^ (-123008)) ^ (b4 << 6));
                                yield.invoke((i8 >= 2048 && (55296 > i8 || i8 >= 57344)) ? Integer.valueOf(i8) : Integer.valueOf(REPLACEMENT_CODE_POINT));
                                Unit unit4 = Unit.INSTANCE;
                                i3 = 3;
                            } else {
                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                Unit unit5 = Unit.INSTANCE;
                            }
                        } else {
                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            Unit unit6 = Unit.INSTANCE;
                            i3 = 1;
                        }
                    }
                    i += i3;
                } else if ((b >> 3) == -2) {
                    int i9 = i + 3;
                    if (i2 <= i9) {
                        yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        Unit unit7 = Unit.INSTANCE;
                        int i10 = i + 1;
                        if (i2 <= i10 || (bArr[i10] & 192) != 128) {
                            i3 = 1;
                        } else {
                            int i11 = i + 2;
                            if (i2 > i11 && (bArr[i11] & 192) == 128) {
                                i3 = 3;
                            }
                        }
                    } else {
                        byte b6 = bArr[i + 1];
                        if ((b6 & 192) == 128) {
                            byte b7 = bArr[i + 2];
                            if ((b7 & 192) == 128) {
                                byte b8 = bArr[i9];
                                if ((b8 & 192) == 128) {
                                    int i12 = (b << 18) ^ (((b8 ^ 3678080) ^ (b7 << 6)) ^ (b6 << 12));
                                    yield.invoke((i12 <= 1114111 && (55296 > i12 || i12 >= 57344) && i12 >= 65536) ? Integer.valueOf(i12) : Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    Unit unit8 = Unit.INSTANCE;
                                    i3 = 4;
                                } else {
                                    yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    Unit unit9 = Unit.INSTANCE;
                                    i3 = 3;
                                }
                            } else {
                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                Unit unit10 = Unit.INSTANCE;
                            }
                        } else {
                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            Unit unit11 = Unit.INSTANCE;
                            i3 = 1;
                        }
                    }
                    i += i3;
                } else {
                    yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                    i++;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0045  */
    public static final void processUtf16Chars(byte[] bArr, int i, int i2, Function1<? super Character, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        while (i < i2) {
            byte b = bArr[i];
            if (b >= 0) {
                yield.invoke(Character.valueOf((char) b));
                i++;
                while (i < i2) {
                    byte b2 = bArr[i];
                    if (b2 < 0) {
                        break;
                    }
                    i++;
                    yield.invoke(Character.valueOf((char) b2));
                }
            } else {
                int i3 = 2;
                if ((b >> 5) == -2) {
                    int i4 = i + 1;
                    if (i2 > i4) {
                        byte b3 = bArr[i4];
                        if ((b3 & 192) == 128) {
                            int i5 = (b << 6) ^ (b3 ^ 3968);
                            yield.invoke(Character.valueOf(i5 < 128 ? (char) REPLACEMENT_CODE_POINT : (char) i5));
                            Unit unit = Unit.INSTANCE;
                        }
                        i += i3;
                    }
                    yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                    Unit unit2 = Unit.INSTANCE;
                    i3 = 1;
                    i += i3;
                } else if ((b >> 4) == -2) {
                    int i6 = i + 2;
                    if (i2 <= i6) {
                        yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                        Unit unit3 = Unit.INSTANCE;
                        int i7 = i + 1;
                        if (i2 <= i7 || (bArr[i7] & 192) != 128) {
                            i3 = 1;
                        }
                    } else {
                        byte b4 = bArr[i + 1];
                        if ((b4 & 192) == 128) {
                            byte b5 = bArr[i6];
                            if ((b5 & 192) == 128) {
                                int i8 = (b << 12) ^ ((b5 ^ (-123008)) ^ (b4 << 6));
                                yield.invoke(Character.valueOf((i8 >= 2048 && (55296 > i8 || i8 >= 57344)) ? (char) i8 : (char) REPLACEMENT_CODE_POINT));
                                Unit unit4 = Unit.INSTANCE;
                                i3 = 3;
                            } else {
                                yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                Unit unit5 = Unit.INSTANCE;
                            }
                        } else {
                            yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            Unit unit6 = Unit.INSTANCE;
                            i3 = 1;
                        }
                    }
                    i += i3;
                } else if ((b >> 3) == -2) {
                    int i9 = i + 3;
                    if (i2 <= i9) {
                        yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                        Unit unit7 = Unit.INSTANCE;
                        int i10 = i + 1;
                        if (i2 <= i10 || (bArr[i10] & 192) != 128) {
                            i3 = 1;
                        } else {
                            int i11 = i + 2;
                            if (i2 > i11 && (bArr[i11] & 192) == 128) {
                                i3 = 3;
                            }
                        }
                    } else {
                        byte b6 = bArr[i + 1];
                        if ((b6 & 192) == 128) {
                            byte b7 = bArr[i + 2];
                            if ((b7 & 192) == 128) {
                                byte b8 = bArr[i9];
                                if ((b8 & 192) == 128) {
                                    int i12 = (b << 18) ^ (((b8 ^ 3678080) ^ (b7 << 6)) ^ (b6 << 12));
                                    if (i12 <= 1114111 && ((55296 > i12 || i12 >= 57344) && i12 >= 65536 && i12 != 65533)) {
                                        yield.invoke(Character.valueOf((char) ((i12 >>> 10) + HIGH_SURROGATE_HEADER)));
                                        yield.invoke(Character.valueOf((char) ((i12 & R2.attr.endActionMenuEnabled) + LOG_SURROGATE_HEADER)));
                                    } else {
                                        yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                    }
                                    Unit unit8 = Unit.INSTANCE;
                                    i3 = 4;
                                } else {
                                    yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                    Unit unit9 = Unit.INSTANCE;
                                    i3 = 3;
                                }
                            } else {
                                yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                Unit unit10 = Unit.INSTANCE;
                            }
                        } else {
                            yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                            Unit unit11 = Unit.INSTANCE;
                            i3 = 1;
                        }
                    }
                    i += i3;
                } else {
                    yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                    i++;
                }
            }
        }
    }

    public static final int process2Utf8Bytes(byte[] bArr, int i, int i2, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i3 = i + 1;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i2 <= i3) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b = bArr[i];
        byte b2 = bArr[i3];
        if ((b2 & 192) != 128) {
            yield.invoke(numValueOf);
            return 1;
        }
        int i4 = (b2 ^ 3968) ^ (b << 6);
        if (i4 < 128) {
            yield.invoke(numValueOf);
            return 2;
        }
        yield.invoke(Integer.valueOf(i4));
        return 2;
    }

    public static final int process3Utf8Bytes(byte[] bArr, int i, int i2, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i3 = i + 2;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i2 <= i3) {
            yield.invoke(numValueOf);
            int i4 = i + 1;
            return (i2 <= i4 || (bArr[i4] & 192) != 128) ? 1 : 2;
        }
        byte b = bArr[i];
        byte b2 = bArr[i + 1];
        if ((b2 & 192) != 128) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b3 = bArr[i3];
        if ((b3 & 192) != 128) {
            yield.invoke(numValueOf);
            return 2;
        }
        int i5 = ((b3 ^ (-123008)) ^ (b2 << 6)) ^ (b << 12);
        if (i5 < 2048) {
            yield.invoke(numValueOf);
            return 3;
        }
        if (55296 <= i5 && i5 < 57344) {
            yield.invoke(numValueOf);
            return 3;
        }
        yield.invoke(Integer.valueOf(i5));
        return 3;
    }

    public static final int process4Utf8Bytes(byte[] bArr, int i, int i2, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i3 = i + 3;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i2 <= i3) {
            yield.invoke(numValueOf);
            int i4 = i + 1;
            if (i2 <= i4 || (bArr[i4] & 192) != 128) {
                return 1;
            }
            int i5 = i + 2;
            return (i2 <= i5 || (bArr[i5] & 192) != 128) ? 2 : 3;
        }
        byte b = bArr[i];
        byte b2 = bArr[i + 1];
        if ((b2 & 192) != 128) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b3 = bArr[i + 2];
        if ((b3 & 192) != 128) {
            yield.invoke(numValueOf);
            return 2;
        }
        byte b4 = bArr[i3];
        if ((b4 & 192) != 128) {
            yield.invoke(numValueOf);
            return 3;
        }
        int i6 = (((b4 ^ 3678080) ^ (b3 << 6)) ^ (b2 << 12)) ^ (b << 18);
        if (i6 > 1114111) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (55296 <= i6 && i6 < 57344) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (i6 < 65536) {
            yield.invoke(numValueOf);
            return 4;
        }
        yield.invoke(Integer.valueOf(i6));
        return 4;
    }
}
