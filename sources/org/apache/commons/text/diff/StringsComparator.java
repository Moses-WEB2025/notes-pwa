package org.apache.commons.text.diff;

/* JADX INFO: loaded from: classes6.dex */
public class StringsComparator {
    private final String left;
    private final String right;
    private final int[] vDown;
    private final int[] vUp;

    private static final class Snake {
        private final int diag;
        private final int end;
        private final int start;

        Snake(int i, int i2, int i3) {
            this.start = i;
            this.end = i2;
            this.diag = i3;
        }

        public int getDiag() {
            return this.diag;
        }

        public int getEnd() {
            return this.end;
        }

        public int getStart() {
            return this.start;
        }
    }

    public StringsComparator(String str, String str2) {
        this.left = str;
        this.right = str2;
        int length = str.length() + str2.length() + 2;
        this.vDown = new int[length];
        this.vUp = new int[length];
    }

    private void buildScript(int i, int i2, int i3, int i4, EditScript<Character> editScript) {
        Snake middleSnake = getMiddleSnake(i, i2, i3, i4);
        if (middleSnake != null && ((middleSnake.getStart() != i2 || middleSnake.getDiag() != i2 - i4) && (middleSnake.getEnd() != i || middleSnake.getDiag() != i - i3))) {
            buildScript(i, middleSnake.getStart(), i3, middleSnake.getStart() - middleSnake.getDiag(), editScript);
            for (int start = middleSnake.getStart(); start < middleSnake.getEnd(); start++) {
                editScript.append(new KeepCommand<>(Character.valueOf(this.left.charAt(start))));
            }
            buildScript(middleSnake.getEnd(), i2, middleSnake.getEnd() - middleSnake.getDiag(), i4, editScript);
            return;
        }
        int i5 = i;
        int i6 = i3;
        while (true) {
            if (i5 >= i2 && i6 >= i4) {
                return;
            }
            if (i5 < i2 && i6 < i4 && this.left.charAt(i5) == this.right.charAt(i6)) {
                editScript.append(new KeepCommand<>(Character.valueOf(this.left.charAt(i5))));
                i5++;
            } else if (i2 - i > i4 - i3) {
                editScript.append(new DeleteCommand<>(Character.valueOf(this.left.charAt(i5))));
                i5++;
            } else {
                editScript.append(new InsertCommand<>(Character.valueOf(this.right.charAt(i6))));
            }
            i6++;
        }
    }

    private Snake buildSnake(int i, int i2, int i3, int i4) {
        int i5 = i;
        while (true) {
            int i6 = i5 - i2;
            if (i6 >= i4 || i5 >= i3 || this.left.charAt(i5) != this.right.charAt(i6)) {
                break;
            }
            i5++;
        }
        return new Snake(i, i5, i2);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0047  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b8  */
    private Snake getMiddleSnake(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i2 - i;
        int i8 = i4 - i3;
        if (i7 == 0 || i8 == 0) {
            return null;
        }
        int i9 = i7 - i8;
        int i10 = i8 + i7;
        if (i10 % 2 != 0) {
            i10++;
        }
        int i11 = i10 / 2;
        int i12 = i11 + 1;
        this.vDown[i12] = i;
        this.vUp[i12] = i2 + 1;
        for (int i13 = 0; i13 <= i11; i13++) {
            int i14 = -i13;
            for (int i15 = i14; i15 <= i13; i15 += 2) {
                int i16 = i15 + i11;
                if (i15 == i14) {
                    int[] iArr = this.vDown;
                    iArr[i16] = iArr[i16 + 1];
                } else {
                    if (i15 != i13) {
                        int[] iArr2 = this.vDown;
                        if (iArr2[i16 - 1] < iArr2[i16 + 1]) {
                            int[] iArr3 = this.vDown;
                            iArr3[i16] = iArr3[i16 + 1];
                        }
                    }
                    int[] iArr4 = this.vDown;
                    iArr4[i16] = iArr4[i16 - 1] + 1;
                }
                int i17 = this.vDown[i16];
                for (int i18 = ((i17 - i) + i3) - i15; i17 < i2 && i18 < i4 && this.left.charAt(i17) == this.right.charAt(i18); i18++) {
                    i17++;
                    this.vDown[i16] = i17;
                }
                if (i9 % 2 != 0 && i9 - i13 <= i15 && i15 <= i9 + i13 && (i6 = this.vUp[i16 - i9]) <= this.vDown[i16]) {
                    return buildSnake(i6, (i15 + i) - i3, i2, i4);
                }
            }
            int i19 = i9 - i13;
            int i20 = i19;
            while (true) {
                int i21 = i9 + i13;
                if (i20 <= i21) {
                    int i22 = (i20 + i11) - i9;
                    if (i20 == i19) {
                        int[] iArr5 = this.vUp;
                        iArr5[i22] = iArr5[i22 + 1] - 1;
                    } else {
                        if (i20 != i21) {
                            int[] iArr6 = this.vUp;
                            if (iArr6[i22 + 1] <= iArr6[i22 - 1]) {
                                int[] iArr7 = this.vUp;
                                iArr7[i22] = iArr7[i22 + 1] - 1;
                            }
                        }
                        int[] iArr8 = this.vUp;
                        iArr8[i22] = iArr8[i22 - 1];
                    }
                    int i23 = this.vUp[i22] - 1;
                    int i24 = ((i23 - i) + i3) - i20;
                    while (i23 >= i && i24 >= i3 && this.left.charAt(i23) == this.right.charAt(i24)) {
                        this.vUp[i22] = i23;
                        i24--;
                        i23--;
                    }
                    if (i9 % 2 == 0 && i14 <= i20 && i20 <= i13 && (i5 = this.vUp[i22]) <= this.vDown[i22 + i9]) {
                        return buildSnake(i5, (i20 + i) - i3, i2, i4);
                    }
                    i20 += 2;
                }
            }
        }
        throw new IllegalStateException("Internal Error");
    }

    public EditScript<Character> getScript() {
        EditScript<Character> editScript = new EditScript<>();
        buildScript(0, this.left.length(), 0, this.right.length(), editScript);
        return editScript;
    }
}
