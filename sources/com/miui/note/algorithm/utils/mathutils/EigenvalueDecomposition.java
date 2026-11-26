package com.miui.note.algorithm.utils.mathutils;

import java.io.Serializable;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes3.dex */
public class EigenvalueDecomposition implements Serializable {
    private double[][] H;
    private double[][] V;
    private transient double cdivi;
    private transient double cdivr;
    private double[] d;
    private double[] e;
    private boolean issymmetric;
    private int n;
    private double[] ort;

    private void tred2() {
        int i;
        int i2 = 0;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            this.d[i2] = this.V[i - 1][i2];
            i2++;
        }
        while (true) {
            i--;
            double d = 0.0d;
            if (i <= 0) {
                break;
            }
            double dAbs = 0.0d;
            for (int i3 = 0; i3 < i; i3++) {
                dAbs += Math.abs(this.d[i3]);
            }
            if (dAbs == 0.0d) {
                int i4 = i - 1;
                this.e[i] = this.d[i4];
                for (int i5 = 0; i5 < i; i5++) {
                    double[] dArr = this.d;
                    double[][] dArr2 = this.V;
                    dArr[i5] = dArr2[i4][i5];
                    dArr2[i][i5] = 0.0d;
                    dArr2[i5][i] = 0.0d;
                }
            } else {
                double d2 = 0.0d;
                for (int i6 = 0; i6 < i; i6++) {
                    double[] dArr3 = this.d;
                    double d3 = dArr3[i6] / dAbs;
                    dArr3[i6] = d3;
                    d2 += d3 * d3;
                }
                int i7 = i - 1;
                double d4 = this.d[i7];
                double dSqrt = Math.sqrt(d2);
                if (d4 > 0.0d) {
                    dSqrt = -dSqrt;
                }
                this.e[i] = dAbs * dSqrt;
                double d5 = d2 - (d4 * dSqrt);
                this.d[i7] = d4 - dSqrt;
                for (int i8 = 0; i8 < i; i8++) {
                    this.e[i8] = 0.0d;
                }
                int i9 = 0;
                while (i9 < i) {
                    double d6 = this.d[i9];
                    double[] dArr4 = this.V[i9];
                    dArr4[i] = d6;
                    double d7 = this.e[i9] + (dArr4[i9] * d6);
                    int i10 = i9 + 1;
                    for (int i11 = i10; i11 <= i7; i11++) {
                        double d8 = this.V[i11][i9];
                        d7 += this.d[i11] * d8;
                        double[] dArr5 = this.e;
                        dArr5[i11] = dArr5[i11] + (d8 * d6);
                    }
                    this.e[i9] = d7;
                    i9 = i10;
                }
                double d9 = 0.0d;
                for (int i12 = 0; i12 < i; i12++) {
                    double[] dArr6 = this.e;
                    double d10 = dArr6[i12] / d5;
                    dArr6[i12] = d10;
                    d9 += d10 * this.d[i12];
                }
                double d11 = d9 / (d5 + d5);
                for (int i13 = 0; i13 < i; i13++) {
                    double[] dArr7 = this.e;
                    dArr7[i13] = dArr7[i13] - (this.d[i13] * d11);
                }
                int i14 = 0;
                while (i14 < i) {
                    double d12 = this.d[i14];
                    double d13 = this.e[i14];
                    int i15 = i14;
                    while (i15 <= i7) {
                        double[] dArr8 = this.V[i15];
                        int i16 = i14;
                        dArr8[i16] = dArr8[i14] - ((this.e[i15] * d12) + (this.d[i15] * d13));
                        i15++;
                        i14 = i16;
                        d = d;
                    }
                    int i17 = i14;
                    double d14 = d;
                    double[] dArr9 = this.d;
                    double[][] dArr10 = this.V;
                    dArr9[i17] = dArr10[i7][i17];
                    dArr10[i][i17] = d14;
                    i14 = i17 + 1;
                    d = d14;
                }
                d = d5;
            }
            this.d[i] = d;
        }
        int i18 = 0;
        while (true) {
            int i19 = this.n;
            if (i18 >= i19 - 1) {
                break;
            }
            double[][] dArr11 = this.V;
            double[] dArr12 = dArr11[i19 - 1];
            double[] dArr13 = dArr11[i18];
            dArr12[i18] = dArr13[i18];
            dArr13[i18] = 1.0d;
            int i20 = i18 + 1;
            double d15 = this.d[i20];
            if (d15 != 0.0d) {
                for (int i21 = 0; i21 <= i18; i21++) {
                    this.d[i21] = this.V[i21][i20] / d15;
                }
                for (int i22 = 0; i22 <= i18; i22++) {
                    double d16 = 0.0d;
                    for (int i23 = 0; i23 <= i18; i23++) {
                        double[] dArr14 = this.V[i23];
                        d16 += dArr14[i20] * dArr14[i22];
                    }
                    for (int i24 = 0; i24 <= i18; i24++) {
                        double[] dArr15 = this.V[i24];
                        dArr15[i22] = dArr15[i22] - (this.d[i24] * d16);
                    }
                }
            }
            for (int i25 = 0; i25 <= i18; i25++) {
                this.V[i25][i20] = 0.0d;
            }
            i18 = i20;
        }
        int i26 = 0;
        while (true) {
            int i27 = this.n;
            if (i26 < i27) {
                double[] dArr16 = this.d;
                double[][] dArr17 = this.V;
                dArr16[i26] = dArr17[i27 - 1][i26];
                dArr17[i27 - 1][i26] = 0.0d;
                i26++;
            } else {
                this.V[i27 - 1][i27 - 1] = 1.0d;
                this.e[0] = 0.0d;
                return;
            }
        }
    }

    private void tql2() {
        int i;
        double d;
        double d2;
        boolean z = true;
        int i2 = 1;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            double[] dArr = this.e;
            dArr[i2 - 1] = dArr[i2];
            i2++;
        }
        double d3 = 0.0d;
        this.e[i - 1] = 0.0d;
        double d4 = 2.0d;
        double dPow = Math.pow(2.0d, -52.0d);
        double dMax = 0.0d;
        double d5 = 0.0d;
        int i3 = 0;
        while (i3 < this.n) {
            boolean z2 = z;
            dMax = Math.max(dMax, Math.abs(this.d[i3]) + Math.abs(this.e[i3]));
            int i4 = i3;
            while (i4 < this.n && Math.abs(this.e[i4]) > dPow * dMax) {
                i4++;
            }
            if (i4 > i3) {
                while (true) {
                    double[] dArr2 = this.d;
                    double d6 = dArr2[i3];
                    int i5 = i3 + 1;
                    d2 = d3;
                    double d7 = (dArr2[i5] - d6) / (this.e[i3] * d4);
                    int i6 = i3;
                    double dHypot = Maths.hypot(d7, 1.0d);
                    if (d7 < d2) {
                        dHypot = -dHypot;
                    }
                    double[] dArr3 = this.d;
                    double[] dArr4 = this.e;
                    double d8 = d7 + dHypot;
                    dArr3[i6] = dArr4[i6] / d8;
                    double d9 = dArr4[i6] * d8;
                    dArr3[i5] = d9;
                    double d10 = d6 - dArr3[i6];
                    for (int i7 = i6 + 2; i7 < this.n; i7++) {
                        double[] dArr5 = this.d;
                        dArr5[i7] = dArr5[i7] - d10;
                    }
                    d5 += d10;
                    double d11 = this.d[i4];
                    double d12 = this.e[i5];
                    int i8 = i4 - 1;
                    i3 = i6;
                    double d13 = d2;
                    double d14 = d13;
                    double d15 = 1.0d;
                    double d16 = 1.0d;
                    double d17 = 1.0d;
                    while (i8 >= i3) {
                        int i9 = i4;
                        double d18 = dPow;
                        double d19 = this.e[i8];
                        double d20 = d15 * d19;
                        double d21 = d15 * d11;
                        double dHypot2 = Maths.hypot(d11, d19);
                        double[] dArr6 = this.e;
                        int i10 = i8 + 1;
                        dArr6[i10] = d13 * dHypot2;
                        double d22 = dArr6[i8] / dHypot2;
                        double d23 = d11 / dHypot2;
                        double[] dArr7 = this.d;
                        double d24 = dArr7[i8];
                        double d25 = (d23 * d24) - (d22 * d20);
                        dArr7[i10] = d21 + (((d20 * d23) + (d24 * d22)) * d22);
                        for (int i11 = 0; i11 < this.n; i11++) {
                            double[] dArr8 = this.V[i11];
                            double d26 = dArr8[i10];
                            dArr8[i10] = (dArr8[i8] * d22) + (d23 * d26);
                            dArr8[i8] = (dArr8[i8] * d23) - (d26 * d22);
                        }
                        i8--;
                        d14 = d13;
                        d16 = d17;
                        d13 = d22;
                        d11 = d25;
                        d17 = d15;
                        d15 = d23;
                        i4 = i9;
                        dPow = d18;
                    }
                    int i12 = i4;
                    d = dPow;
                    double[] dArr9 = this.e;
                    double d27 = (((((-d13) * d14) * d16) * d12) * dArr9[i3]) / d9;
                    dArr9[i3] = d13 * d27;
                    this.d[i3] = d15 * d27;
                    if (Math.abs(dArr9[i3]) <= d * dMax) {
                        break;
                    }
                    d3 = d2;
                    i4 = i12;
                    dPow = d;
                    d4 = 2.0d;
                }
            } else {
                d = dPow;
                d2 = d3;
            }
            double[] dArr10 = this.d;
            dArr10[i3] = dArr10[i3] + d5;
            this.e[i3] = d2;
            i3++;
            z = z2;
            d3 = d2;
            dPow = d;
            d4 = 2.0d;
        }
        int i13 = 0;
        while (i13 < this.n - 1) {
            double d28 = this.d[i13];
            int i14 = i13 + 1;
            int i15 = i13;
            for (int i16 = i14; i16 < this.n; i16++) {
                double d29 = this.d[i16];
                if (d29 < d28) {
                    i15 = i16;
                    d28 = d29;
                }
            }
            if (i15 != i13) {
                double[] dArr11 = this.d;
                dArr11[i15] = dArr11[i13];
                dArr11[i13] = d28;
                for (int i17 = 0; i17 < this.n; i17++) {
                    double[] dArr12 = this.V[i17];
                    double d30 = dArr12[i13];
                    dArr12[i13] = dArr12[i15];
                    dArr12[i15] = d30;
                }
            }
            i13 = i14;
        }
    }

    private void orthes() {
        int i;
        int i2;
        int i3 = this.n;
        int i4 = i3 - 1;
        int i5 = 1;
        while (true) {
            i = i3 - 2;
            double d = 0.0d;
            if (i5 > i) {
                break;
            }
            double dAbs = 0.0d;
            for (int i6 = i5; i6 <= i4; i6++) {
                dAbs += Math.abs(this.H[i6][i5 - 1]);
            }
            if (dAbs != 0.0d) {
                double d2 = 0.0d;
                for (int i7 = i4; i7 >= i5; i7--) {
                    double[] dArr = this.ort;
                    double d3 = this.H[i7][i5 - 1] / dAbs;
                    dArr[i7] = d3;
                    d2 += d3 * d3;
                }
                double dSqrt = Math.sqrt(d2);
                double[] dArr2 = this.ort;
                double d4 = dArr2[i5];
                if (d4 > 0.0d) {
                    dSqrt = -dSqrt;
                }
                double d5 = d2 - (d4 * dSqrt);
                dArr2[i5] = d4 - dSqrt;
                for (int i8 = i5; i8 < this.n; i8++) {
                    double d6 = d;
                    for (int i9 = i4; i9 >= i5; i9--) {
                        d6 += this.ort[i9] * this.H[i9][i8];
                    }
                    double d7 = d6 / d5;
                    int i10 = i5;
                    while (i10 <= i4) {
                        double[] dArr3 = this.H[i10];
                        dArr3[i8] = dArr3[i8] - (this.ort[i10] * d7);
                        i10++;
                        d = d;
                    }
                }
                double d8 = d;
                for (int i11 = 0; i11 <= i4; i11++) {
                    double d9 = d8;
                    for (int i12 = i4; i12 >= i5; i12--) {
                        d9 += this.ort[i12] * this.H[i11][i12];
                    }
                    double d10 = d9 / d5;
                    int i13 = i5;
                    while (i13 <= i4) {
                        double[] dArr4 = this.H[i11];
                        dArr4[i13] = dArr4[i13] - (this.ort[i13] * d10);
                        i13++;
                        i3 = i3;
                    }
                }
                i2 = i3;
                double[] dArr5 = this.ort;
                dArr5[i5] = dArr5[i5] * dAbs;
                this.H[i5][i5 - 1] = dAbs * dSqrt;
            } else {
                i2 = i3;
            }
            i5++;
            i3 = i2;
        }
        int i14 = 0;
        while (i14 < this.n) {
            int i15 = 0;
            while (i15 < this.n) {
                this.V[i14][i15] = i14 == i15 ? 1.0d : 0.0d;
                i15++;
            }
            i14++;
        }
        while (i >= 1) {
            int i16 = i - 1;
            if (this.H[i][i16] != 0.0d) {
                for (int i17 = i + 1; i17 <= i4; i17++) {
                    this.ort[i17] = this.H[i17][i16];
                }
                for (int i18 = i; i18 <= i4; i18++) {
                    double d11 = 0.0d;
                    for (int i19 = i; i19 <= i4; i19++) {
                        d11 += this.ort[i19] * this.V[i19][i18];
                    }
                    double d12 = (d11 / this.ort[i]) / this.H[i][i16];
                    for (int i20 = i; i20 <= i4; i20++) {
                        double[] dArr6 = this.V[i20];
                        dArr6[i18] = dArr6[i18] + (this.ort[i20] * d12);
                    }
                }
            }
            i--;
        }
    }

    private void cdiv(double d, double d2, double d3, double d4) {
        if (Math.abs(d3) > Math.abs(d4)) {
            double d5 = d4 / d3;
            double d6 = d3 + (d4 * d5);
            this.cdivr = ((d5 * d2) + d) / d6;
            this.cdivi = (d2 - (d5 * d)) / d6;
            return;
        }
        double d7 = d3 / d4;
        double d8 = d4 + (d3 * d7);
        this.cdivr = ((d7 * d) + d2) / d8;
        this.cdivi = ((d7 * d2) - d) / d8;
    }

    private void hqr2() {
        int i;
        int i2;
        double d;
        double d2;
        double d3;
        int i3;
        int i4;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        int i5;
        boolean z;
        int i6 = this.n;
        int i7 = i6 - 1;
        double d10 = 2.0d;
        double dPow = Math.pow(2.0d, -52.0d);
        int i8 = 0;
        int i9 = 0;
        double dAbs = 0.0d;
        while (true) {
            if (i9 >= i6) {
                break;
            }
            if ((i9 < 0) | (i9 > i7)) {
                this.d[i9] = this.H[i9][i9];
                this.e[i9] = 0.0d;
            }
            for (int iMax = Math.max(i9 - 1, 0); iMax < i6; iMax++) {
                dAbs += Math.abs(this.H[i9][iMax]);
            }
            i9++;
        }
        int i10 = i7;
        int i11 = 0;
        double d11 = 0.0d;
        double d12 = 0.0d;
        double dSqrt = 0.0d;
        double dSqrt2 = 0.0d;
        double d13 = 0.0d;
        double d14 = 0.0d;
        while (i10 >= 0) {
            double d15 = d10;
            int i12 = i10;
            while (i12 > 0) {
                int i13 = i12 - 1;
                double dAbs2 = Math.abs(this.H[i13][i13]) + Math.abs(this.H[i12][i12]);
                if (dAbs2 == 0.0d) {
                    dAbs2 = dAbs;
                }
                if (Math.abs(this.H[i12][i13]) < dPow * dAbs2) {
                    dSqrt = dAbs2;
                    break;
                } else {
                    i12--;
                    dSqrt = dAbs2;
                }
            }
            if (i12 == i10) {
                double[] dArr = this.H[i10];
                double d16 = dArr[i10] + d11;
                dArr[i10] = d16;
                this.d[i10] = d16;
                this.e[i10] = 0.0d;
                i10--;
                i11 = i8;
            } else {
                int i14 = i10 - 1;
                if (i12 == i14) {
                    double[][] dArr2 = this.H;
                    double[] dArr3 = dArr2[i10];
                    double d17 = dArr3[i14];
                    double[] dArr4 = dArr2[i14];
                    double d18 = d17 * dArr4[i10];
                    double d19 = (dArr4[i14] - dArr3[i10]) / d15;
                    double d20 = (d19 * d19) + d18;
                    double d21 = d11;
                    double dSqrt3 = Math.sqrt(Math.abs(d20));
                    double[][] dArr5 = this.H;
                    double[] dArr6 = dArr5[i10];
                    dArr6[i10] = dArr6[i10] + d21;
                    double[] dArr7 = dArr5[i14];
                    dArr7[i14] = dArr7[i14] + d21;
                    double d22 = dArr6[i10];
                    if (d20 >= 0.0d) {
                        double d23 = d19 >= 0.0d ? d19 + dSqrt3 : d19 - dSqrt3;
                        double[] dArr8 = this.d;
                        double d24 = d22 + d23;
                        dArr8[i14] = d24;
                        dArr8[i10] = d24;
                        if (d23 != 0.0d) {
                            dArr8[i10] = d22 - (d18 / d23);
                        }
                        double[] dArr9 = this.e;
                        dArr9[i14] = 0.0d;
                        dArr9[i10] = 0.0d;
                        double d25 = dArr6[i14];
                        dSqrt = Math.abs(d25) + Math.abs(d23);
                        double d26 = d25 / dSqrt;
                        double d27 = d23 / dSqrt;
                        dSqrt2 = Math.sqrt((d26 * d26) + (d27 * d27));
                        double d28 = d26 / dSqrt2;
                        double d29 = d27 / dSqrt2;
                        for (int i15 = i14; i15 < i6; i15++) {
                            double[][] dArr10 = this.H;
                            double[] dArr11 = dArr10[i14];
                            d23 = dArr11[i15];
                            double[] dArr12 = dArr10[i10];
                            dArr11[i15] = (d29 * d23) + (dArr12[i15] * d28);
                            dArr12[i15] = (dArr12[i15] * d29) - (d28 * d23);
                        }
                        for (int i16 = i8; i16 <= i10; i16++) {
                            double[] dArr13 = this.H[i16];
                            d23 = dArr13[i14];
                            dArr13[i14] = (d29 * d23) + (dArr13[i10] * d28);
                            dArr13[i10] = (dArr13[i10] * d29) - (d28 * d23);
                        }
                        for (int i17 = i8; i17 <= i7; i17++) {
                            double[] dArr14 = this.V[i17];
                            d23 = dArr14[i14];
                            dArr14[i14] = (d29 * d23) + (dArr14[i10] * d28);
                            dArr14[i10] = (dArr14[i10] * d29) - (d28 * d23);
                        }
                        double d30 = d23;
                        d14 = d29;
                        d12 = d30;
                        d13 = d28;
                    } else {
                        double[] dArr15 = this.d;
                        double d31 = d22 + d19;
                        dArr15[i14] = d31;
                        dArr15[i10] = d31;
                        double[] dArr16 = this.e;
                        dArr16[i14] = dSqrt3;
                        dArr16[i10] = -dSqrt3;
                        d13 = d19;
                        d14 = d20;
                        d12 = dSqrt3;
                    }
                    i10 -= 2;
                    i11 = i8;
                    d11 = d21;
                } else {
                    double d32 = d11;
                    double[][] dArr17 = this.H;
                    double[] dArr18 = dArr17[i10];
                    double dAbs3 = dArr18[i10];
                    if (i12 < i10) {
                        double[] dArr19 = dArr17[i14];
                        d6 = dArr19[i14];
                        d7 = dArr18[i14] * dArr19[i10];
                    } else {
                        d6 = 0.0d;
                        d7 = 0.0d;
                    }
                    if (i11 == 10) {
                        d8 = d32 + dAbs3;
                        for (int i18 = i8; i18 <= i10; i18++) {
                            double[] dArr20 = this.H[i18];
                            dArr20[i18] = dArr20[i18] - dAbs3;
                        }
                        dSqrt = Math.abs(this.H[i10][i14]) + Math.abs(this.H[i14][i10 - 2]);
                        dAbs3 = 0.75d * dSqrt;
                        d7 = (-0.4375d) * dSqrt * dSqrt;
                        d6 = dAbs3;
                    } else {
                        d8 = d32;
                    }
                    if (i11 == 30) {
                        double d33 = (d6 - dAbs3) / d15;
                        double d34 = (d33 * d33) + d7;
                        double d35 = d8;
                        if (d34 > 0.0d) {
                            double dSqrt4 = Math.sqrt(d34);
                            if (d6 < dAbs3) {
                                dSqrt4 = -dSqrt4;
                            }
                            dSqrt = dAbs3 - (d7 / (d33 + dSqrt4));
                            for (int i19 = 0; i19 <= i10; i19++) {
                                double[] dArr21 = this.H[i19];
                                dArr21[i19] = dArr21[i19] - dSqrt;
                            }
                            d8 = d35 + dSqrt;
                            dAbs3 = 0.964d;
                            d6 = 0.964d;
                            d7 = 0.964d;
                        } else {
                            dSqrt = d34;
                        }
                    }
                    int i20 = i11 + 1;
                    int i21 = i10 - 2;
                    while (true) {
                        if (i21 < i12) {
                            d9 = d8;
                            break;
                        }
                        double[][] dArr22 = this.H;
                        double[] dArr23 = dArr22[i21];
                        double d36 = dArr23[i21];
                        double d37 = dAbs3 - d36;
                        double d38 = d6 - d36;
                        int i22 = i21 + 1;
                        double[] dArr24 = dArr22[i22];
                        double d39 = (((d37 * d38) - d7) / dArr24[i21]) + dArr23[i22];
                        double d40 = ((dArr24[i22] - d36) - d37) - d38;
                        double d41 = dArr22[i21 + 2][i22];
                        double dAbs4 = Math.abs(d39) + Math.abs(d40) + Math.abs(d41);
                        d13 = d39 / dAbs4;
                        d14 = d40 / dAbs4;
                        double d42 = d41 / dAbs4;
                        if (i21 == i12) {
                            d9 = d8;
                        } else {
                            d9 = d8;
                            int i23 = i21 - 1;
                            if (Math.abs(this.H[i21][i23]) * (Math.abs(d14) + Math.abs(d42)) >= Math.abs(d13) * (Math.abs(this.H[i23][i23]) + Math.abs(d36) + Math.abs(this.H[i22][i22])) * dPow) {
                                i21--;
                                dSqrt2 = d42;
                                d12 = d36;
                                dSqrt = dAbs4;
                                d8 = d9;
                            }
                        }
                        dSqrt2 = d42;
                        d12 = d36;
                        dSqrt = dAbs4;
                        break;
                    }
                    int i24 = i21 + 2;
                    int i25 = i24;
                    while (i25 <= i10) {
                        int i26 = i20;
                        double[] dArr25 = this.H[i25];
                        dArr25[i25 - 2] = 0.0d;
                        if (i25 > i24) {
                            dArr25[i25 - 3] = 0.0d;
                        }
                        i25++;
                        i20 = i26;
                    }
                    int i27 = i20;
                    int i28 = i21;
                    while (i28 <= i14) {
                        boolean z2 = i28 != i14;
                        if (i28 != i21) {
                            double[][] dArr26 = this.H;
                            int i29 = i28 - 1;
                            d13 = dArr26[i28][i29];
                            d14 = dArr26[i28 + 1][i29];
                            dSqrt2 = z2 ? dArr26[i28 + 2][i29] : 0.0d;
                            dAbs3 = Math.abs(d13) + Math.abs(d14) + Math.abs(dSqrt2);
                            if (dAbs3 != 0.0d) {
                                d13 /= dAbs3;
                                d14 /= dAbs3;
                                dSqrt2 /= dAbs3;
                            }
                        }
                        if (dAbs3 == 0.0d) {
                            break;
                        }
                        dSqrt = Math.sqrt((d13 * d13) + (d14 * d14) + (dSqrt2 * dSqrt2));
                        if (d13 < 0.0d) {
                            dSqrt = -dSqrt;
                        }
                        if (dSqrt != 0.0d) {
                            if (i28 != i21) {
                                i5 = i28;
                                z = z2;
                                this.H[i28][i28 - 1] = (-dSqrt) * dAbs3;
                            } else {
                                i5 = i28;
                                z = z2;
                                if (i12 != i21) {
                                    double[] dArr27 = this.H[i5];
                                    int i30 = i5 - 1;
                                    dArr27[i30] = -dArr27[i30];
                                }
                            }
                            d13 += dSqrt;
                            double d43 = d13 / dSqrt;
                            double d44 = d14 / dSqrt;
                            double d45 = dSqrt2 / dSqrt;
                            d14 /= d13;
                            dSqrt2 /= d13;
                            int i31 = i5;
                            while (i31 < i6) {
                                double d46 = d43;
                                double[][] dArr28 = this.H;
                                double[] dArr29 = dArr28[i5];
                                double d47 = dArr29[i31];
                                double[] dArr30 = dArr28[i5 + 1];
                                d13 = d47 + (dArr30[i31] * d14);
                                if (z) {
                                    double[] dArr31 = dArr28[i5 + 2];
                                    double d48 = dArr31[i31];
                                    d13 += dSqrt2 * d48;
                                    dArr31[i31] = d48 - (d13 * d45);
                                }
                                dArr29[i31] = dArr29[i31] - (d13 * d46);
                                dArr30[i31] = dArr30[i31] - (d13 * d44);
                                i31++;
                                d43 = d46;
                            }
                            double d49 = d43;
                            for (int i32 = 0; i32 <= Math.min(i10, i5 + 3); i32++) {
                                double[] dArr32 = this.H[i32];
                                int i33 = i5 + 1;
                                d13 = (dArr32[i5] * d49) + (dArr32[i33] * d44);
                                if (z) {
                                    int i34 = i5 + 2;
                                    double d50 = dArr32[i34];
                                    d13 += d45 * d50;
                                    dArr32[i34] = d50 - (d13 * dSqrt2);
                                }
                                dArr32[i5] = dArr32[i5] - d13;
                                dArr32[i33] = dArr32[i33] - (d13 * d14);
                            }
                            for (int i35 = 0; i35 <= i7; i35++) {
                                double[] dArr33 = this.V[i35];
                                int i36 = i5 + 1;
                                d13 = (dArr33[i5] * d49) + (dArr33[i36] * d44);
                                if (z) {
                                    int i37 = i5 + 2;
                                    double d51 = dArr33[i37];
                                    d13 += d45 * d51;
                                    dArr33[i37] = d51 - (d13 * dSqrt2);
                                }
                                dArr33[i5] = dArr33[i5] - d13;
                                dArr33[i36] = dArr33[i36] - (d13 * d14);
                            }
                            d12 = d45;
                            dAbs3 = d49;
                        } else {
                            i5 = i28;
                        }
                        i28 = i5 + 1;
                    }
                    i11 = i27;
                    d11 = d9;
                }
            }
            d10 = d15;
            i8 = 0;
        }
        double d52 = d10;
        if (dAbs == 0.0d) {
            return;
        }
        double d53 = d12;
        int i38 = i7;
        double d54 = dSqrt2;
        double d55 = dSqrt;
        while (i38 >= 0) {
            double d56 = this.d[i38];
            double d57 = this.e[i38];
            if (d57 == 0.0d) {
                this.H[i38][i38] = 1.0d;
                int i39 = i38 - 1;
                int i40 = i38;
                double d58 = d55;
                while (i39 >= 0) {
                    double d59 = this.H[i39][i39] - d56;
                    int i41 = i39;
                    int i42 = i40;
                    double d60 = 0.0d;
                    while (i42 <= i38) {
                        int i43 = i42;
                        double[][] dArr34 = this.H;
                        d60 += dArr34[i41][i43] * dArr34[i43][i38];
                        i42 = i43 + 1;
                    }
                    double d61 = this.e[i41];
                    if (d61 < 0.0d) {
                        d53 = d59;
                        d58 = d60;
                    } else {
                        if (d61 != 0.0d) {
                            double[][] dArr35 = this.H;
                            double[] dArr36 = dArr35[i41];
                            int i44 = i41 + 1;
                            double d62 = dArr36[i44];
                            double d63 = dArr35[i44][i41];
                            double d64 = this.d[i41];
                            double d65 = ((d62 * d58) - (d53 * d60)) / (((d64 - d56) * (d64 - d56)) + (d61 * d61));
                            dArr36[i38] = d65;
                            if (Math.abs(d62) > Math.abs(d53)) {
                                this.H[i44][i38] = ((-d60) - (d65 * d59)) / d62;
                            } else {
                                this.H[i44][i38] = ((-d58) - (d63 * d65)) / d53;
                            }
                        } else if (d59 != 0.0d) {
                            this.H[i41][i38] = (-d60) / d59;
                        } else {
                            this.H[i41][i38] = (-d60) / (dPow * dAbs);
                        }
                        double dAbs5 = Math.abs(this.H[i41][i38]);
                        if (dPow * dAbs5 * dAbs5 > 1.0d) {
                            for (int i45 = i41; i45 <= i38; i45++) {
                                double[] dArr37 = this.H[i45];
                                dArr37[i38] = dArr37[i38] / dAbs5;
                            }
                        }
                        i40 = i41;
                    }
                    i39 = i41 - 1;
                    d54 = d60;
                }
                d55 = d58;
            } else {
                if (d57 < 0.0d) {
                    int i46 = i38 - 1;
                    if (Math.abs(this.H[i38][i46]) > Math.abs(this.H[i46][i38])) {
                        double[][] dArr38 = this.H;
                        double[] dArr39 = dArr38[i46];
                        double[] dArr40 = dArr38[i38];
                        dArr39[i46] = d57 / dArr40[i46];
                        dArr39[i38] = (-(dArr40[i38] - d56)) / dArr40[i46];
                    } else {
                        double[] dArr41 = this.H[i46];
                        cdiv(0.0d, -dArr41[i38], dArr41[i46] - d56, d57);
                        double[] dArr42 = this.H[i46];
                        dArr42[i46] = this.cdivr;
                        dArr42[i38] = this.cdivi;
                    }
                    double[] dArr43 = this.H[i38];
                    dArr43[i46] = 0.0d;
                    dArr43[i38] = 1.0d;
                    double d66 = d53;
                    int i47 = i38 - 2;
                    double d67 = d55;
                    double d68 = d66;
                    int i48 = i46;
                    while (i47 >= 0) {
                        int i49 = i48;
                        int i50 = i47;
                        double d69 = 0.0d;
                        double d70 = 0.0d;
                        while (i49 <= i38) {
                            double d71 = d67;
                            double[][] dArr44 = this.H;
                            double d72 = dArr44[i50][i49];
                            double[] dArr45 = dArr44[i49];
                            d69 += dArr45[i46] * d72;
                            d70 += d72 * dArr45[i38];
                            i49++;
                            d67 = d71;
                        }
                        double d73 = d67;
                        double[][] dArr46 = this.H;
                        double[] dArr47 = dArr46[i50];
                        double d74 = dArr47[i50] - d56;
                        double d75 = this.e[i50];
                        if (d75 < 0.0d) {
                            d54 = d69;
                            i3 = i6;
                            i4 = i7;
                            d67 = d70;
                            d2 = dPow;
                            d68 = d74;
                        } else {
                            if (d75 == 0.0d) {
                                cdiv(-d69, -d70, d74, d57);
                                double[] dArr48 = this.H[i50];
                                dArr48[i46] = this.cdivr;
                                dArr48[i38] = this.cdivi;
                                d2 = dPow;
                                d5 = d68;
                                d3 = d54;
                                i3 = i6;
                                i4 = i7;
                                d4 = d73;
                            } else {
                                double d76 = d54;
                                double d77 = d57;
                                int i51 = i50 + 1;
                                double d78 = dArr47[i51];
                                double d79 = dArr46[i51][i50];
                                double d80 = this.d[i50];
                                double dAbs6 = (((d80 - d56) * (d80 - d56)) + (d75 * d75)) - (d77 * d77);
                                double d81 = (d80 - d56) * d52 * d77;
                                if ((dAbs6 == 0.0d) & (d81 == 0.0d)) {
                                    dAbs6 = dPow * dAbs * (Math.abs(d74) + Math.abs(d77) + Math.abs(d78) + Math.abs(d79) + Math.abs(d68));
                                }
                                double d82 = ((d78 * d73) - (d68 * d70)) - (d77 * d69);
                                d2 = dPow;
                                d3 = d76;
                                i3 = i6;
                                i4 = i7;
                                double d83 = d69;
                                cdiv(((d78 * d76) - (d68 * d69)) + (d77 * d70), d82, dAbs6, d81);
                                double[] dArr49 = this.H[i50];
                                dArr49[i46] = this.cdivr;
                                dArr49[i38] = this.cdivi;
                                if (Math.abs(d78) > Math.abs(d68) + Math.abs(d77)) {
                                    double[][] dArr50 = this.H;
                                    double[] dArr51 = dArr50[i51];
                                    double[] dArr52 = dArr50[i50];
                                    dArr51[i46] = (((-d83) - (dArr52[i46] * d74)) + (d77 * dArr52[i38])) / d78;
                                    dArr51[i38] = (((-d70) - (dArr52[i38] * d74)) - (d77 * dArr52[i46])) / d78;
                                    d5 = d68;
                                    d57 = d77;
                                    d4 = d73;
                                } else {
                                    double[] dArr53 = this.H[i50];
                                    d4 = d73;
                                    d5 = d68;
                                    d57 = d77;
                                    cdiv((-d3) - (dArr53[i46] * d79), (-d4) - (d79 * dArr53[i38]), d5, d57);
                                    double[] dArr54 = this.H[i51];
                                    dArr54[i46] = this.cdivr;
                                    dArr54[i38] = this.cdivi;
                                }
                            }
                            double dMax = Math.max(Math.abs(this.H[i50][i46]), Math.abs(this.H[i50][i38]));
                            if (d2 * dMax * dMax > 1.0d) {
                                for (int i52 = i50; i52 <= i38; i52++) {
                                    double[] dArr55 = this.H[i52];
                                    dArr55[i46] = dArr55[i46] / dMax;
                                    dArr55[i38] = dArr55[i38] / dMax;
                                }
                            }
                            d68 = d5;
                            d67 = d4;
                            d54 = d3;
                            i48 = i50;
                        }
                        i47 = i50 - 1;
                        i6 = i3;
                        i7 = i4;
                        dPow = d2;
                    }
                    i = i6;
                    i2 = i7;
                    d = dPow;
                    d53 = d68;
                    d55 = d67;
                }
                i38--;
                i6 = i;
                i7 = i2;
                dPow = d;
            }
            i = i6;
            i2 = i7;
            d = dPow;
            i38--;
            i6 = i;
            i7 = i2;
            dPow = d;
        }
        int i53 = i7;
        int i54 = i6;
        int i55 = 0;
        while (i55 < i54) {
            int i56 = i53;
            if ((i55 < 0) | (i55 > i56)) {
                for (int i57 = i55; i57 < i54; i57++) {
                    this.V[i55][i57] = this.H[i55][i57];
                }
            }
            i55++;
            i53 = i56;
        }
        int i58 = i53;
        for (int i59 = i58; i59 >= 0; i59--) {
            for (int i60 = 0; i60 <= i58; i60++) {
                double d84 = 0.0d;
                for (int i61 = 0; i61 <= Math.min(i59, i58); i61++) {
                    d84 += this.V[i60][i61] * this.H[i61][i59];
                }
                this.V[i60][i59] = d84;
            }
        }
    }

    public EigenvalueDecomposition(Matrix matrix) {
        int i;
        boolean z;
        double[][] array = matrix.getArray();
        int columnDimension = matrix.getColumnDimension();
        this.n = columnDimension;
        this.V = (double[][]) Array.newInstance((Class<?>) Double.TYPE, columnDimension, columnDimension);
        int i2 = this.n;
        this.d = new double[i2];
        this.e = new double[i2];
        this.issymmetric = true;
        int i3 = 0;
        while (true) {
            i = this.n;
            boolean z2 = i3 < i;
            z = this.issymmetric;
            if (!z2 || !z) {
                break;
            }
            int i4 = 0;
            while (true) {
                if ((i4 < this.n) & this.issymmetric) {
                    this.issymmetric = array[i4][i3] == array[i3][i4];
                    i4++;
                }
            }
            i3++;
        }
        if (z) {
            for (int i5 = 0; i5 < this.n; i5++) {
                for (int i6 = 0; i6 < this.n; i6++) {
                    this.V[i5][i6] = array[i5][i6];
                }
            }
            tred2();
            tql2();
            return;
        }
        this.H = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i, i);
        this.ort = new double[this.n];
        for (int i7 = 0; i7 < this.n; i7++) {
            for (int i8 = 0; i8 < this.n; i8++) {
                this.H[i8][i7] = array[i8][i7];
            }
        }
        orthes();
        hqr2();
    }

    public Matrix getV() {
        double[][] dArr = this.V;
        int i = this.n;
        return new Matrix(dArr, i, i);
    }

    public double[] getRealEigenvalues() {
        return this.d;
    }

    public double[] getImagEigenvalues() {
        return this.e;
    }

    public Matrix getD() {
        int i = this.n;
        Matrix matrix = new Matrix(i, i);
        double[][] array = matrix.getArray();
        for (int i2 = 0; i2 < this.n; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                array[i2][i3] = 0.0d;
            }
            double[] dArr = array[i2];
            dArr[i2] = this.d[i2];
            double d = this.e[i2];
            if (d > 0.0d) {
                dArr[i2 + 1] = d;
            } else if (d < 0.0d) {
                dArr[i2 - 1] = d;
            }
        }
        return matrix;
    }
}
