package xc.engine.core.graphics.pen;

/* JADX INFO: loaded from: classes6.dex */
public enum PenType {
    INK(1),
    BALL(2),
    PENCIL(3),
    MARK(4),
    WATER_COLOR(10);

    public final int mType;

    PenType(int i) {
        this.mType = i;
    }

    public static PenType getPenType(int i) {
        if (i == 2) {
            return BALL;
        }
        if (i == 3) {
            return PENCIL;
        }
        if (i == 4) {
            return MARK;
        }
        if (i == 10) {
            return WATER_COLOR;
        }
        return INK;
    }

    public int getValue() {
        return this.mType;
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.pen.PenType$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$xc$engine$core$graphics$pen$PenType;

        static {
            int[] iArr = new int[PenType.values().length];
            $SwitchMap$xc$engine$core$graphics$pen$PenType = iArr;
            try {
                iArr[PenType.INK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.BALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.PENCIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.MARK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.WATER_COLOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static int convertToBrushStock(PenType penType, boolean z) {
        if (z) {
            return 5;
        }
        int i = AnonymousClass1.$SwitchMap$xc$engine$core$graphics$pen$PenType[penType.ordinal()];
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 0;
        }
        if (i == 3) {
            return 1;
        }
        if (i != 4) {
            return i != 5 ? 5 : 4;
        }
        return 3;
    }
}
