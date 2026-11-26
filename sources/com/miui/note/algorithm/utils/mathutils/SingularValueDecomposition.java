package com.miui.note.algorithm.utils.mathutils;

import java.io.Serializable;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes3.dex */
public class SingularValueDecomposition implements Serializable {
    private double[][] U;
    private double[][] V;
    private int m;
    private int n;
    private double[] s;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.note.algorithm.utils.mathutils.SingularValueDecomposition, java.lang.Object] */
    public SingularValueDecomposition(Matrix matrix) {
        double d;
        int i;
        int i2;
        double d2;
        int i3;
        double[] dArr;
        char c;
        double d3;
        double d4;
        double d5;
        ?? obj = new Object();
        double[][] arrayCopy = matrix.getArrayCopy();
        obj.m = matrix.getRowDimension();
        int columnDimension = matrix.getColumnDimension();
        obj.n = columnDimension;
        int iMin = Math.min(obj.m, columnDimension);
        int i4 = 1;
        obj.s = new double[Math.min(obj.m + 1, obj.n)];
        obj.U = (double[][]) Array.newInstance((Class<?>) Double.TYPE, obj.m, iMin);
        int i5 = obj.n;
        obj.V = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i5, i5);
        int i6 = obj.n;
        double[] dArr2 = new double[i6];
        int i7 = obj.m;
        double[] dArr3 = new double[i7];
        int iMin2 = Math.min(i7 - 1, i6);
        int iMax = Math.max(0, Math.min(obj.n - 2, obj.m));
        int i8 = 0;
        while (true) {
            double d6 = 0.0d;
            if (i8 >= Math.max(iMin2, iMax)) {
                break;
            }
            if (i8 < iMin2) {
                obj.s[i8] = 0.0d;
                int i9 = i8;
                while (i9 < obj.m) {
                    double[] dArr4 = obj.s;
                    dArr4[i8] = Maths.hypot(dArr4[i8], arrayCopy[i9][i8]);
                    i9++;
                    d6 = d6;
                }
                d4 = d6;
                d5 = 1.0d;
                double[] dArr5 = obj.s;
                double d7 = dArr5[i8];
                if (d7 != d4) {
                    if (arrayCopy[i8][i8] < d4) {
                        dArr5[i8] = -d7;
                    }
                    for (int i10 = i8; i10 < obj.m; i10++) {
                        double[] dArr6 = arrayCopy[i10];
                        dArr6[i8] = dArr6[i8] / obj.s[i8];
                    }
                    double[] dArr7 = arrayCopy[i8];
                    dArr7[i8] = dArr7[i8] + 1.0d;
                }
                double[] dArr8 = obj.s;
                dArr8[i8] = -dArr8[i8];
            } else {
                d4 = 0.0d;
                d5 = 1.0d;
            }
            int i11 = i8 + 1;
            for (int i12 = i11; i12 < obj.n; i12++) {
                if ((i8 < iMin2) & (obj.s[i8] != d4)) {
                    double d8 = d4;
                    for (int i13 = i8; i13 < obj.m; i13++) {
                        double[] dArr9 = arrayCopy[i13];
                        d8 += dArr9[i8] * dArr9[i12];
                    }
                    double d9 = (-d8) / arrayCopy[i8][i8];
                    for (int i14 = i8; i14 < obj.m; i14++) {
                        double[] dArr10 = arrayCopy[i14];
                        dArr10[i12] = dArr10[i12] + (dArr10[i8] * d9);
                    }
                }
                dArr2[i12] = arrayCopy[i8][i12];
            }
            if (i8 < iMin2) {
                for (int i15 = i8; i15 < obj.m; i15++) {
                    obj.U[i15][i8] = arrayCopy[i15][i8];
                }
            }
            if (i8 < iMax) {
                dArr2[i8] = d4;
                for (int i16 = i11; i16 < obj.n; i16++) {
                    dArr2[i8] = Maths.hypot(dArr2[i8], dArr2[i16]);
                }
                double d10 = dArr2[i8];
                if (d10 != d4) {
                    if (dArr2[i11] < d4) {
                        dArr2[i8] = -d10;
                    }
                    for (int i17 = i11; i17 < obj.n; i17++) {
                        dArr2[i17] = dArr2[i17] / dArr2[i8];
                    }
                    dArr2[i11] = dArr2[i11] + d5;
                }
                double d11 = -dArr2[i8];
                dArr2[i8] = d11;
                if ((d11 != d4) & (i11 < obj.m)) {
                    for (int i18 = i11; i18 < obj.m; i18++) {
                        dArr3[i18] = d4;
                    }
                    for (int i19 = i11; i19 < obj.n; i19++) {
                        for (int i20 = i11; i20 < obj.m; i20++) {
                            dArr3[i20] = dArr3[i20] + (dArr2[i19] * arrayCopy[i20][i19]);
                        }
                    }
                    for (int i21 = i11; i21 < obj.n; i21++) {
                        double d12 = (-dArr2[i21]) / dArr2[i11];
                        for (int i22 = i11; i22 < obj.m; i22++) {
                            double[] dArr11 = arrayCopy[i22];
                            dArr11[i21] = dArr11[i21] + (dArr3[i22] * d12);
                        }
                    }
                }
                for (int i23 = i11; i23 < obj.n; i23++) {
                    obj.V[i23][i8] = dArr2[i23];
                }
            }
            i8 = i11;
        }
        int iMin3 = Math.min(obj.n, obj.m + 1);
        if (iMin2 < obj.n) {
            obj.s[iMin2] = arrayCopy[iMin2][iMin2];
        }
        if (obj.m < iMin3) {
            obj.s[iMin3 - 1] = 0.0d;
        }
        if (iMax + 1 < iMin3) {
            dArr2[iMax] = arrayCopy[iMax][iMin3 - 1];
        }
        int i24 = iMin3 - 1;
        dArr2[i24] = 0.0d;
        for (int i25 = iMin2; i25 < iMin; i25++) {
            for (int i26 = 0; i26 < obj.m; i26++) {
                obj.U[i26][i25] = 0.0d;
            }
            obj.U[i25][i25] = 1.0d;
        }
        for (int i27 = iMin2 - 1; i27 >= 0; i27--) {
            if (obj.s[i27] != 0.0d) {
                for (int i28 = i27 + 1; i28 < iMin; i28++) {
                    double d13 = 0.0d;
                    for (int i29 = i27; i29 < obj.m; i29++) {
                        double[] dArr12 = obj.U[i29];
                        d13 += dArr12[i27] * dArr12[i28];
                    }
                    double d14 = (-d13) / obj.U[i27][i27];
                    for (int i30 = i27; i30 < obj.m; i30++) {
                        double[] dArr13 = obj.U[i30];
                        dArr13[i28] = dArr13[i28] + (dArr13[i27] * d14);
                    }
                }
                for (int i31 = i27; i31 < obj.m; i31++) {
                    double[] dArr14 = obj.U[i31];
                    dArr14[i27] = -dArr14[i27];
                }
                double[] dArr15 = obj.U[i27];
                dArr15[i27] = dArr15[i27] + 1.0d;
                for (int i32 = 0; i32 < i27 - 1; i32++) {
                    obj.U[i32][i27] = 0.0d;
                }
            } else {
                for (int i33 = 0; i33 < obj.m; i33++) {
                    obj.U[i33][i27] = 0.0d;
                }
                obj.U[i27][i27] = 1.0d;
            }
        }
        int i34 = obj.n - 1;
        while (i34 >= 0) {
            if ((i34 < iMax) & (dArr2[i34] != 0.0d)) {
                int i35 = i34 + 1;
                for (int i36 = i35; i36 < iMin; i36++) {
                    double d15 = 0.0d;
                    for (int i37 = i35; i37 < obj.n; i37++) {
                        double[] dArr16 = obj.V[i37];
                        d15 += dArr16[i34] * dArr16[i36];
                    }
                    double d16 = (-d15) / obj.V[i35][i34];
                    for (int i38 = i35; i38 < obj.n; i38++) {
                        double[] dArr17 = obj.V[i38];
                        dArr17[i36] = dArr17[i36] + (dArr17[i34] * d16);
                    }
                }
            }
            for (int i39 = 0; i39 < obj.n; i39++) {
                obj.V[i39][i34] = 0.0d;
            }
            obj.V[i34][i34] = 1.0d;
            i34--;
        }
        double d17 = 2.0d;
        double dPow = Math.pow(2.0d, -52.0d);
        double dPow2 = Math.pow(2.0d, -966.0d);
        SingularValueDecomposition singularValueDecomposition = obj;
        while (iMin3 > 0) {
            int i40 = iMin3 - 2;
            int i41 = i40;
            while (true) {
                if (i41 < -1 || i41 == -1) {
                    d = d17;
                    break;
                }
                d = d17;
                if (Math.abs(dArr2[i41]) <= ((Math.abs(singularValueDecomposition.s[i41]) + Math.abs(singularValueDecomposition.s[i41 + 1])) * dPow) + dPow2) {
                    dArr2[i41] = 0.0d;
                    break;
                } else {
                    i41--;
                    d17 = d;
                }
            }
            if (i41 == i40) {
                i = 4;
            } else {
                int i42 = iMin3 - 1;
                int i43 = i42;
                while (i43 >= i41 && i43 != i41) {
                    if (Math.abs(singularValueDecomposition.s[i43]) <= dPow2 + (((i43 != iMin3 ? Math.abs(dArr2[i43]) : 0.0d) + (i43 != i41 + 1 ? Math.abs(dArr2[i43 - 1]) : 0.0d)) * dPow)) {
                        singularValueDecomposition.s[i43] = 0.0d;
                        break;
                    }
                    i43--;
                }
                if (i43 == i41) {
                    i = 3;
                } else if (i43 == i42) {
                    i = i4;
                } else {
                    i41 = i43;
                    i = 2;
                }
            }
            int i44 = i41 + 1;
            if (i != i4) {
                c = 2;
                if (i == 2) {
                    i2 = i24;
                    d2 = dPow;
                    i3 = i4;
                    dArr = dArr2;
                    double d18 = dArr[i41];
                    dArr[i41] = 0.0d;
                    while (i44 < iMin3) {
                        double dHypot = Maths.hypot(singularValueDecomposition.s[i44], d18);
                        double[] dArr18 = singularValueDecomposition.s;
                        double d19 = dArr18[i44] / dHypot;
                        double d20 = d18 / dHypot;
                        dArr18[i44] = dHypot;
                        double d21 = -d20;
                        double d22 = dArr[i44];
                        double d23 = d21 * d22;
                        dArr[i44] = d22 * d19;
                        for (int i45 = 0; i45 < singularValueDecomposition.m; i45++) {
                            double[] dArr19 = singularValueDecomposition.U[i45];
                            double d24 = dArr19[i44];
                            double d25 = dArr19[i41];
                            dArr19[i41] = (d24 * d21) + (d25 * d19);
                            dArr19[i44] = (d19 * d24) + (d20 * d25);
                        }
                        i44++;
                        d18 = d23;
                    }
                } else if (i != 3) {
                    if (i == 4) {
                        double[] dArr20 = singularValueDecomposition.s;
                        double d26 = dArr20[i44];
                        if (d26 <= 0.0d) {
                            dArr20[i44] = d26 < 0.0d ? -d26 : 0.0d;
                            for (int i46 = 0; i46 <= i24; i46++) {
                                double[] dArr21 = singularValueDecomposition.V[i46];
                                dArr21[i44] = -dArr21[i44];
                            }
                        }
                        while (i44 < i24) {
                            double[] dArr22 = singularValueDecomposition.s;
                            double d27 = dArr22[i44];
                            int i47 = i44 + 1;
                            double d28 = dArr22[i47];
                            if (d27 >= d28) {
                                break;
                            }
                            dArr22[i44] = d28;
                            dArr22[i47] = d27;
                            if (i44 < singularValueDecomposition.n - i4) {
                                for (int i48 = 0; i48 < singularValueDecomposition.n; i48++) {
                                    double[] dArr23 = singularValueDecomposition.V[i48];
                                    double d29 = dArr23[i47];
                                    dArr23[i47] = dArr23[i44];
                                    dArr23[i44] = d29;
                                }
                            }
                            if (i44 < singularValueDecomposition.m - i4) {
                                for (int i49 = 0; i49 < singularValueDecomposition.m; i49++) {
                                    double[] dArr24 = singularValueDecomposition.U[i49];
                                    double d30 = dArr24[i47];
                                    dArr24[i47] = dArr24[i44];
                                    dArr24[i44] = d30;
                                }
                            }
                            i44 = i47;
                        }
                        iMin3--;
                    }
                    i2 = i24;
                    d2 = dPow;
                    i3 = i4;
                    dArr = dArr2;
                } else {
                    int i50 = iMin3 - 1;
                    i3 = i4;
                    double dMax = Math.max(Math.max(Math.max(Math.max(Math.abs(singularValueDecomposition.s[i50]), Math.abs(singularValueDecomposition.s[i40])), Math.abs(dArr2[i40])), Math.abs(singularValueDecomposition.s[i44])), Math.abs(dArr2[i44]));
                    double[] dArr25 = singularValueDecomposition.s;
                    double d31 = dArr25[i50] / dMax;
                    double d32 = dArr25[i40] / dMax;
                    double d33 = dArr2[i40] / dMax;
                    double d34 = dArr25[i44] / dMax;
                    double d35 = dArr2[i44] / dMax;
                    double d36 = (((d32 + d31) * (d32 - d31)) + (d33 * d33)) / d;
                    double d37 = d33 * d31;
                    double d38 = d37 * d37;
                    if (((d36 != 0.0d ? i3 : 0) | (d38 != 0.0d ? i3 : 0)) != 0) {
                        double dSqrt = Math.sqrt((d36 * d36) + d38);
                        d3 = d38 / (d36 + (d36 < 0.0d ? -dSqrt : dSqrt));
                    } else {
                        d3 = 0.0d;
                    }
                    double d39 = ((d34 + d31) * (d34 - d31)) + d3;
                    double d40 = d34 * d35;
                    int i51 = i44;
                    while (i51 < i50) {
                        double dHypot2 = Maths.hypot(d39, d40);
                        double d41 = d39 / dHypot2;
                        double d42 = d40 / dHypot2;
                        if (i51 != i44) {
                            dArr2[i51 - 1] = dHypot2;
                        }
                        double[] dArr26 = singularValueDecomposition.s;
                        double d43 = dArr26[i51];
                        double d44 = dArr2[i51];
                        int i52 = i24;
                        double d45 = dPow;
                        double d46 = (d41 * d43) + (d42 * d44);
                        dArr2[i51] = (d44 * d41) - (d43 * d42);
                        int i53 = i51 + 1;
                        double d47 = dArr26[i53];
                        int i54 = i53;
                        double d48 = d42 * d47;
                        dArr26[i54] = d47 * d41;
                        int i55 = 0;
                        while (i55 < singularValueDecomposition.n) {
                            double[] dArr27 = singularValueDecomposition.V[i55];
                            double d49 = dArr27[i51];
                            double d50 = dArr27[i54];
                            int i56 = i54;
                            dArr27[i56] = ((-d42) * d49) + (d41 * d50);
                            dArr27[i51] = (d41 * d49) + (d42 * d50);
                            i55++;
                            dArr2 = dArr2;
                            i54 = i56;
                        }
                        int i57 = i54;
                        double[] dArr28 = dArr2;
                        double dHypot3 = Maths.hypot(d46, d48);
                        double d51 = d46 / dHypot3;
                        double d52 = d48 / dHypot3;
                        double[] dArr29 = singularValueDecomposition.s;
                        dArr29[i51] = dHypot3;
                        double d53 = dArr28[i51];
                        double d54 = dArr29[i57];
                        double d55 = (d51 * d53) + (d52 * d54);
                        double d56 = -d52;
                        dArr29[i57] = (d53 * d56) + (d54 * d51);
                        double d57 = dArr28[i57];
                        d40 = d52 * d57;
                        dArr28[i57] = d57 * d51;
                        if (i51 < singularValueDecomposition.m - 1) {
                            for (int i58 = 0; i58 < singularValueDecomposition.m; i58++) {
                                double[] dArr30 = singularValueDecomposition.U[i58];
                                double d58 = dArr30[i51];
                                double d59 = dArr30[i57];
                                dArr30[i57] = (d58 * d56) + (d59 * d51);
                                dArr30[i51] = (d51 * d58) + (d52 * d59);
                            }
                        }
                        d39 = d55;
                        i24 = i52;
                        dPow = d45;
                        dArr2 = dArr28;
                        i51 = i57;
                    }
                    i2 = i24;
                    d2 = dPow;
                    dArr = dArr2;
                    dArr[i40] = d39;
                }
            } else {
                i2 = i24;
                d2 = dPow;
                i3 = i4;
                dArr = dArr2;
                c = 2;
                double d60 = dArr[i40];
                dArr[i40] = 0.0d;
                SingularValueDecomposition singularValueDecomposition2 = singularValueDecomposition;
                while (i40 >= i44) {
                    double dHypot4 = Maths.hypot(singularValueDecomposition2.s[i40], d60);
                    double[] dArr31 = singularValueDecomposition2.s;
                    double d61 = dArr31[i40] / dHypot4;
                    double d62 = d60 / dHypot4;
                    dArr31[i40] = dHypot4;
                    if (i40 != i44) {
                        int i59 = i40 - 1;
                        double d63 = dArr[i59];
                        d60 = (-d62) * d63;
                        dArr[i59] = d63 * d61;
                    }
                    int i60 = 0;
                    SingularValueDecomposition singularValueDecomposition3 = singularValueDecomposition2;
                    while (i60 < singularValueDecomposition3.n) {
                        double[] dArr32 = singularValueDecomposition3.V[i60];
                        double d64 = dArr32[i40];
                        int i61 = iMin3 - 1;
                        double d65 = dArr32[i61];
                        dArr32[i61] = ((-d62) * d64) + (d65 * d61);
                        dArr32[i40] = (d61 * d64) + (d62 * d65);
                        i60++;
                        singularValueDecomposition3 = this;
                        d60 = d60;
                    }
                    i40--;
                    singularValueDecomposition2 = this;
                }
            }
            singularValueDecomposition = this;
            d17 = d;
            dPow2 = dPow2;
            i4 = i3;
            i24 = i2;
            dPow = d2;
            dArr2 = dArr;
        }
    }

    public Matrix getU() {
        double[][] dArr = this.U;
        int i = this.m;
        return new Matrix(dArr, i, Math.min(i + 1, this.n));
    }

    public Matrix getV() {
        double[][] dArr = this.V;
        int i = this.n;
        return new Matrix(dArr, i, i);
    }

    public double[] getSingularValues() {
        return this.s;
    }

    public Matrix getS() {
        int i = this.n;
        Matrix matrix = new Matrix(i, i);
        double[][] array = matrix.getArray();
        for (int i2 = 0; i2 < this.n; i2++) {
            for (int i3 = 0; i3 < this.n; i3++) {
                array[i2][i3] = 0.0d;
            }
            array[i2][i2] = this.s[i2];
        }
        return matrix;
    }

    public double norm2() {
        return this.s[0];
    }

    public double cond() {
        double[] dArr = this.s;
        return dArr[0] / dArr[Math.min(this.m, this.n) - 1];
    }

    public int rank() {
        int i = 0;
        double dMax = ((double) Math.max(this.m, this.n)) * this.s[0] * Math.pow(2.0d, -52.0d);
        int i2 = 0;
        while (true) {
            double[] dArr = this.s;
            if (i >= dArr.length) {
                return i2;
            }
            if (dArr[i] > dMax) {
                i2++;
            }
            i++;
        }
    }
}
