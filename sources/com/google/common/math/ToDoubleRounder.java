package com.google.common.math;

import com.google.common.base.Preconditions;
import com.miui.maml.folme.AnimatedProperty;
import java.lang.Comparable;
import java.lang.Number;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
abstract class ToDoubleRounder<X extends Number & Comparable<X>> {
    abstract X minus(X a, X b);

    abstract double roundToDoubleArbitrarily(X x);

    abstract int sign(X x);

    abstract X toX(double d, RoundingMode mode);

    ToDoubleRounder() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:20:0x005a  */
    /* JADX WARN: Code duplicated, block: B:22:0x0074  */
    /* JADX WARN: Code duplicated, block: B:24:0x007a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x007c  */
    /* JADX WARN: Code duplicated, block: B:26:0x007e  */
    /* JADX WARN: Code duplicated, block: B:29:0x0083  */
    /* JADX WARN: Code duplicated, block: B:31:0x0089 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:33:0x008d  */
    /* JADX WARN: Code duplicated, block: B:35:0x0092 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x0096  */
    /* JADX WARN: Code duplicated, block: B:39:0x009b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x009f  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ad A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:48:0x00af  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:52:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:55:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:61:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:63:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:65:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:67:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:70:0x0101  */
    /* JADX WARN: Code duplicated, block: B:72:0x0107  */
    /* JADX WARN: Code duplicated, block: B:75:0x010e  */
    /* JADX WARN: Code duplicated, block: B:79:0x011d  */
    /* JADX WARN: Code duplicated, block: B:81:0x0123 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:83:0x0126  */
    /* JADX WARN: Code duplicated, block: B:85:0x012b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:87:0x012e  */
    final double roundToDouble(X x, RoundingMode mode) {
        Number x2;
        int iCompareTo;
        double dNextDown;
        Number x3;
        double dNextUp;
        int iCompareTo2;
        int i;
        boolean z;
        Preconditions.checkNotNull(x, AnimatedProperty.PROPERTY_NAME_X);
        Preconditions.checkNotNull(mode, "mode");
        double dRoundToDoubleArbitrarily = roundToDoubleArbitrarily(x);
        if (Double.isInfinite(dRoundToDoubleArbitrarily)) {
            switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return ((double) sign(x)) * Double.MAX_VALUE;
                case 5:
                    return dRoundToDoubleArbitrarily == Double.POSITIVE_INFINITY ? Double.MAX_VALUE : Double.NEGATIVE_INFINITY;
                case 6:
                    return dRoundToDoubleArbitrarily == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : -1.7976931348623157E308d;
                case 7:
                    break;
                case 8:
                    throw new ArithmeticException(x + " cannot be represented precisely as a double");
                default:
                    x2 = toX(dRoundToDoubleArbitrarily, RoundingMode.UNNECESSARY);
                    iCompareTo = ((Comparable) x).compareTo(x2);
                    switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
                        case 1:
                            if (sign(x) >= 0) {
                                if (iCompareTo < 0) {
                                    return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                                }
                            } else if (iCompareTo > 0) {
                                return Math.nextUp(dRoundToDoubleArbitrarily);
                            }
                        case 2:
                        case 3:
                        case 4:
                            if (iCompareTo >= 0) {
                                dNextUp = Math.nextUp(dRoundToDoubleArbitrarily);
                                if (dNextUp != Double.POSITIVE_INFINITY) {
                                    x3 = toX(dNextUp, RoundingMode.CEILING);
                                    iCompareTo2 = ((Comparable) minus(x, x2)).compareTo(minus(x3, x));
                                    if (iCompareTo2 >= 0) {
                                        if (iCompareTo2 <= 0) {
                                            i = AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()];
                                            if (i != 2) {
                                                if (i != 3) {
                                                    if (i == 4) {
                                                        if (sign(x) >= 0) {
                                                        }
                                                    } else {
                                                        throw new AssertionError("impossible");
                                                    }
                                                } else if (sign(x) >= 0) {
                                                }
                                            } else if ((Double.doubleToRawLongBits(dRoundToDoubleArbitrarily) & 1) == 0) {
                                            }
                                        }
                                        return dNextUp;
                                    }
                                    return dRoundToDoubleArbitrarily;
                                }
                            } else {
                                dNextDown = DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                                if (dNextDown != Double.NEGATIVE_INFINITY) {
                                    Number x4 = toX(dNextDown, RoundingMode.FLOOR);
                                    x3 = x2;
                                    x2 = x4;
                                    dNextUp = dRoundToDoubleArbitrarily;
                                    dRoundToDoubleArbitrarily = dNextDown;
                                    iCompareTo2 = ((Comparable) minus(x, x2)).compareTo(minus(x3, x));
                                    if (iCompareTo2 >= 0) {
                                        if (iCompareTo2 <= 0) {
                                            i = AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()];
                                            if (i != 2) {
                                                if (i != 3) {
                                                    if (i == 4) {
                                                        if (sign(x) >= 0) {
                                                        }
                                                    } else {
                                                        throw new AssertionError("impossible");
                                                    }
                                                } else if (sign(x) >= 0) {
                                                }
                                            } else if ((Double.doubleToRawLongBits(dRoundToDoubleArbitrarily) & 1) == 0) {
                                            }
                                        }
                                        return dNextUp;
                                    }
                                    return dRoundToDoubleArbitrarily;
                                }
                            }
                        case 5:
                            if (iCompareTo < 0) {
                                return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                            }
                            break;
                        case 6:
                            if (iCompareTo > 0) {
                                return Math.nextUp(dRoundToDoubleArbitrarily);
                            }
                            break;
                        case 7:
                            if (sign(x) >= 0) {
                                if (iCompareTo > 0) {
                                    return Math.nextUp(dRoundToDoubleArbitrarily);
                                }
                            } else if (iCompareTo < 0) {
                                return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                            }
                        case 8:
                            if (iCompareTo == 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            MathPreconditions.checkRoundingUnnecessary(z);
                            return dRoundToDoubleArbitrarily;
                        default:
                            throw new AssertionError("impossible");
                    }
                    break;
            }
        } else {
            x2 = toX(dRoundToDoubleArbitrarily, RoundingMode.UNNECESSARY);
            iCompareTo = ((Comparable) x).compareTo(x2);
            switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
                case 1:
                    if (sign(x) >= 0) {
                        if (iCompareTo < 0) {
                            return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                        }
                    } else if (iCompareTo > 0) {
                        return Math.nextUp(dRoundToDoubleArbitrarily);
                    }
                case 2:
                case 3:
                case 4:
                    if (iCompareTo >= 0) {
                        dNextUp = Math.nextUp(dRoundToDoubleArbitrarily);
                        if (dNextUp != Double.POSITIVE_INFINITY) {
                            x3 = toX(dNextUp, RoundingMode.CEILING);
                            iCompareTo2 = ((Comparable) minus(x, x2)).compareTo(minus(x3, x));
                            if (iCompareTo2 >= 0) {
                                if (iCompareTo2 <= 0) {
                                    i = AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()];
                                    if (i != 2) {
                                        if (i != 3) {
                                            if (i == 4) {
                                                if (sign(x) >= 0) {
                                                }
                                            } else {
                                                throw new AssertionError("impossible");
                                            }
                                        } else if (sign(x) >= 0) {
                                        }
                                    } else if ((Double.doubleToRawLongBits(dRoundToDoubleArbitrarily) & 1) == 0) {
                                    }
                                }
                                return dNextUp;
                            }
                            return dRoundToDoubleArbitrarily;
                        }
                    } else {
                        dNextDown = DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                        if (dNextDown != Double.NEGATIVE_INFINITY) {
                            Number x5 = toX(dNextDown, RoundingMode.FLOOR);
                            x3 = x2;
                            x2 = x5;
                            dNextUp = dRoundToDoubleArbitrarily;
                            dRoundToDoubleArbitrarily = dNextDown;
                            iCompareTo2 = ((Comparable) minus(x, x2)).compareTo(minus(x3, x));
                            if (iCompareTo2 >= 0) {
                                if (iCompareTo2 <= 0) {
                                    i = AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()];
                                    if (i != 2) {
                                        if (i != 3) {
                                            if (i == 4) {
                                                if (sign(x) >= 0) {
                                                }
                                            } else {
                                                throw new AssertionError("impossible");
                                            }
                                        } else if (sign(x) >= 0) {
                                        }
                                    } else if ((Double.doubleToRawLongBits(dRoundToDoubleArbitrarily) & 1) == 0) {
                                    }
                                }
                                return dNextUp;
                            }
                            return dRoundToDoubleArbitrarily;
                        }
                    }
                case 5:
                    if (iCompareTo < 0) {
                        return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                    }
                    break;
                case 6:
                    if (iCompareTo > 0) {
                        return Math.nextUp(dRoundToDoubleArbitrarily);
                    }
                    break;
                case 7:
                    if (sign(x) >= 0) {
                        if (iCompareTo > 0) {
                            return Math.nextUp(dRoundToDoubleArbitrarily);
                        }
                    } else if (iCompareTo < 0) {
                        return DoubleUtils.nextDown(dRoundToDoubleArbitrarily);
                    }
                case 8:
                    if (iCompareTo == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    MathPreconditions.checkRoundingUnnecessary(z);
                    return dRoundToDoubleArbitrarily;
                default:
                    throw new AssertionError("impossible");
            }
        }
        return dRoundToDoubleArbitrarily;
    }

    /* JADX INFO: renamed from: com.google.common.math.ToDoubleRounder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}
