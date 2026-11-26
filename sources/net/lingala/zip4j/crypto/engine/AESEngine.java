package net.lingala.zip4j.crypto.engine;

import com.google.common.base.Ascii;
import com.xiaomi.aicr.paintservice.PaintErrorCode;
import defpackage.R2;
import java.lang.reflect.Array;
import kotlin.io.encoding.Base64;
import net.lingala.zip4j.exception.ZipException;
import okio.Utf8;

/* JADX INFO: loaded from: classes6.dex */
public class AESEngine {
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int rounds;
    private int[][] workingKey = null;
    private static final byte[] S = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, Utf8.REPLACEMENT_BYTE, -9, -52, 52, -91, -27, -15, 113, -40, 49, Ascii.NAK, 4, -57, 35, -61, Ascii.CAN, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, PaintErrorCode.UNKNOWN_ERROR, 95, -105, 68, Ascii.ETB, -60, -89, 126, Base64.padSymbol, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, Ascii.SYN};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, R2.array.global_ai_panel_blend_colors, R2.array.ai_shader_panel_rim_colors, 77, R2.array.ai_panel_widget_blend_modes, 47, 94, R2.array.big_screen_layouts, 99, 198, R2.array.ai_panel_close_blend_colors, 53, 106, 212, R2.array.ai_tag_input_send_blend_colors, 125, 250, R2.attr.SharedValueId, R2.array.chinese_symbol_animals, R2.array.ai_input_state_blend_modes};
    private static final int[] T0 = {-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};

    private int shift(int i, int i2) {
        return (i >>> i2) | (i << (-i2));
    }

    public AESEngine(byte[] bArr) throws ZipException {
        init(bArr);
    }

    private void init(byte[] bArr) throws ZipException {
        this.workingKey = generateWorkingKey(bArr);
    }

    private int[][] generateWorkingKey(byte[] bArr) throws ZipException {
        int length = bArr.length / 4;
        if ((length != 4 && length != 6 && length != 8) || length * 4 != bArr.length) {
            throw new ZipException("invalid key length (not 128/192/256)");
        }
        this.rounds = length + 6;
        int i = 0;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length + 7, 4);
        int i2 = 0;
        while (i < bArr.length) {
            iArr[i2 >> 2][i2 & 3] = (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << Ascii.CAN);
            i += 4;
            i2++;
        }
        int i3 = (this.rounds + 1) << 2;
        for (int i4 = length; i4 < i3; i4++) {
            int i5 = i4 - 1;
            int iSubWord = iArr[i5 >> 2][i5 & 3];
            int i6 = i4 % length;
            if (i6 == 0) {
                iSubWord = subWord(shift(iSubWord, 8)) ^ rcon[(i4 / length) - 1];
            } else if (length > 6 && i6 == 4) {
                iSubWord = subWord(iSubWord);
            }
            int i7 = i4 - length;
            iArr[i4 >> 2][i4 & 3] = iSubWord ^ iArr[i7 >> 2][i7 & 3];
        }
        return iArr;
    }

    public int processBlock(byte[] bArr, byte[] bArr2) throws ZipException {
        return processBlock(bArr, 0, bArr2, 0);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws ZipException {
        if (this.workingKey == null) {
            throw new ZipException("AES engine not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new ZipException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new ZipException("output buffer too short");
        }
        stateIn(bArr, i);
        encryptBlock(this.workingKey);
        stateOut(bArr2, i2);
        return 16;
    }

    private void stateIn(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        this.C0 = i2;
        int i3 = ((bArr[i + 1] & 255) << 8) | i2;
        this.C0 = i3;
        int i4 = i3 | ((bArr[i + 2] & 255) << 16);
        this.C0 = i4;
        this.C0 = i4 | (bArr[i + 3] << Ascii.CAN);
        int i5 = bArr[i + 4] & 255;
        this.C1 = i5;
        int i6 = ((bArr[i + 5] & 255) << 8) | i5;
        this.C1 = i6;
        int i7 = i6 | ((bArr[i + 6] & 255) << 16);
        this.C1 = i7;
        this.C1 = i7 | (bArr[i + 7] << Ascii.CAN);
        int i8 = bArr[i + 8] & 255;
        this.C2 = i8;
        int i9 = ((bArr[i + 9] & 255) << 8) | i8;
        this.C2 = i9;
        int i10 = i9 | ((bArr[i + 10] & 255) << 16);
        this.C2 = i10;
        this.C2 = i10 | (bArr[i + 11] << Ascii.CAN);
        int i11 = bArr[i + 12] & 255;
        this.C3 = i11;
        int i12 = ((bArr[i + 13] & 255) << 8) | i11;
        this.C3 = i12;
        int i13 = i12 | ((bArr[i + 14] & 255) << 16);
        this.C3 = i13;
        this.C3 = (bArr[i + 15] << Ascii.CAN) | i13;
    }

    private void stateOut(byte[] bArr, int i) {
        int i2 = this.C0;
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >> 8);
        bArr[i + 2] = (byte) (i2 >> 16);
        bArr[i + 3] = (byte) (i2 >> 24);
        int i3 = this.C1;
        bArr[i + 4] = (byte) i3;
        bArr[i + 5] = (byte) (i3 >> 8);
        bArr[i + 6] = (byte) (i3 >> 16);
        bArr[i + 7] = (byte) (i3 >> 24);
        int i4 = this.C2;
        bArr[i + 8] = (byte) i4;
        bArr[i + 9] = (byte) (i4 >> 8);
        bArr[i + 10] = (byte) (i4 >> 16);
        bArr[i + 11] = (byte) (i4 >> 24);
        int i5 = this.C3;
        bArr[i + 12] = (byte) i5;
        bArr[i + 13] = (byte) (i5 >> 8);
        bArr[i + 14] = (byte) (i5 >> 16);
        bArr[i + 15] = (byte) (i5 >> 24);
    }

    private void encryptBlock(int[][] iArr) {
        int i = this.C0;
        char c = 0;
        int[] iArr2 = iArr[0];
        this.C0 = i ^ iArr2[0];
        this.C1 ^= iArr2[1];
        this.C2 ^= iArr2[2];
        this.C3 ^= iArr2[3];
        int i2 = 1;
        while (i2 < this.rounds - 1) {
            int[] iArr3 = T0;
            int iShift = (((iArr3[this.C0 & 255] ^ shift(iArr3[(this.C1 >> 8) & 255], 24)) ^ shift(iArr3[(this.C2 >> 16) & 255], 16)) ^ shift(iArr3[(this.C3 >> 24) & 255], 8)) ^ iArr[i2][c];
            int iShift2 = (((iArr3[this.C1 & 255] ^ shift(iArr3[(this.C2 >> 8) & 255], 24)) ^ shift(iArr3[(this.C3 >> 16) & 255], 16)) ^ shift(iArr3[(this.C0 >> 24) & 255], 8)) ^ iArr[i2][1];
            int iShift3 = (((iArr3[this.C2 & 255] ^ shift(iArr3[(this.C3 >> 8) & 255], 24)) ^ shift(iArr3[(this.C0 >> 16) & 255], 16)) ^ shift(iArr3[(this.C1 >> 24) & 255], 8)) ^ iArr[i2][2];
            int i3 = i2 + 1;
            int iShift4 = (((iArr3[this.C3 & 255] ^ shift(iArr3[(this.C0 >> 8) & 255], 24)) ^ shift(iArr3[(this.C1 >> 16) & 255], 16)) ^ shift(iArr3[(this.C2 >> 24) & 255], 8)) ^ iArr[i2][3];
            char c2 = c;
            this.C0 = (((shift(iArr3[(iShift2 >> 8) & 255], 24) ^ iArr3[iShift & 255]) ^ shift(iArr3[(iShift3 >> 16) & 255], 16)) ^ shift(iArr3[(iShift4 >> 24) & 255], 8)) ^ iArr[i3][c2];
            this.C1 = (((iArr3[iShift2 & 255] ^ shift(iArr3[(iShift3 >> 8) & 255], 24)) ^ shift(iArr3[(iShift4 >> 16) & 255], 16)) ^ shift(iArr3[(iShift >> 24) & 255], 8)) ^ iArr[i3][1];
            this.C2 = (((iArr3[iShift3 & 255] ^ shift(iArr3[(iShift4 >> 8) & 255], 24)) ^ shift(iArr3[(iShift >> 16) & 255], 16)) ^ shift(iArr3[(iShift2 >> 24) & 255], 8)) ^ iArr[i3][2];
            i2 += 2;
            this.C3 = (((iArr3[iShift4 & 255] ^ shift(iArr3[(iShift >> 8) & 255], 24)) ^ shift(iArr3[(iShift2 >> 16) & 255], 16)) ^ shift(iArr3[(iShift3 >> 24) & 255], 8)) ^ iArr[i3][3];
            c = c2;
        }
        char c3 = c;
        int[] iArr4 = T0;
        int iShift5 = (((iArr4[this.C0 & 255] ^ shift(iArr4[(this.C1 >> 8) & 255], 24)) ^ shift(iArr4[(this.C2 >> 16) & 255], 16)) ^ shift(iArr4[(this.C3 >> 24) & 255], 8)) ^ iArr[i2][c3];
        int iShift6 = (((iArr4[this.C1 & 255] ^ shift(iArr4[(this.C2 >> 8) & 255], 24)) ^ shift(iArr4[(this.C3 >> 16) & 255], 16)) ^ shift(iArr4[(this.C0 >> 24) & 255], 8)) ^ iArr[i2][1];
        int iShift7 = (((iArr4[this.C2 & 255] ^ shift(iArr4[(this.C3 >> 8) & 255], 24)) ^ shift(iArr4[(this.C0 >> 16) & 255], 16)) ^ shift(iArr4[(this.C1 >> 24) & 255], 8)) ^ iArr[i2][2];
        int i4 = i2 + 1;
        int iShift8 = iArr[i2][3] ^ (shift(iArr4[(this.C2 >> 24) & 255], 8) ^ ((iArr4[this.C3 & 255] ^ shift(iArr4[(this.C0 >> 8) & 255], 24)) ^ shift(iArr4[(this.C1 >> 16) & 255], 16)));
        byte[] bArr = S;
        int i5 = (((bArr[iShift5 & 255] & 255) ^ ((bArr[(iShift6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift8 >> 24) & 255] << Ascii.CAN);
        int[] iArr5 = iArr[i4];
        this.C0 = i5 ^ iArr5[c3];
        this.C1 = iArr5[1] ^ ((((bArr[iShift6 & 255] & 255) ^ ((bArr[(iShift7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift5 >> 24) & 255] << Ascii.CAN));
        this.C2 = ((((bArr[iShift7 & 255] & 255) ^ ((bArr[(iShift8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift6 >> 24) & 255] << Ascii.CAN)) ^ iArr5[2];
        this.C3 = ((((bArr[iShift8 & 255] & 255) ^ ((bArr[(iShift5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift6 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift7 >> 24) & 255] << Ascii.CAN)) ^ iArr5[3];
    }

    private int subWord(int i) {
        byte[] bArr = S;
        return (bArr[(i >> 24) & 255] << Ascii.CAN) | (bArr[i & 255] & 255) | ((bArr[(i >> 8) & 255] & 255) << 8) | ((bArr[(i >> 16) & 255] & 255) << 16);
    }
}
