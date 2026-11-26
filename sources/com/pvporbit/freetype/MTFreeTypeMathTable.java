package com.pvporbit.freetype;

import com.miui.richeditor.schema.HtmlParser;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFreeTypeMathTable.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u00046789B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\tJ\b\u0010\u001c\u001a\u00020\nH\u0002J\b\u0010\u001d\u001a\u00020\nH\u0002J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\u0006\u0010 \u001a\u00020\nJ:\u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\"\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f`\u000b2\u0006\u0010 \u001a\u00020\nH\u0002J\u001b\u0010#\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010$2\u0006\u0010 \u001a\u00020\n¢\u0006\u0002\u0010&J\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\u0006\u0010 \u001a\u00020\nJ\u000e\u0010(\u001a\u00020\n2\u0006\u0010 \u001a\u00020\nJ\u0015\u0010)\u001a\u0004\u0018\u00010\n2\u0006\u0010 \u001a\u00020\n¢\u0006\u0002\u0010*J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\nH\u0002J\u001b\u0010.\u001a\b\u0012\u0004\u0012\u00020\n0$2\u0006\u0010-\u001a\u00020\nH\u0002¢\u0006\u0002\u0010/J\u0010\u00100\u001a\u0002012\u0006\u0010-\u001a\u00020\nH\u0002J\u0010\u00102\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\nH\u0002J4\u00103\u001a\u00020,2\u0006\u0010-\u001a\u00020\n2\"\u00104\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bH\u0002J\u0010\u00105\u001a\u00020,2\u0006\u0010-\u001a\u00020\nH\u0002R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R*\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0019\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\bj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/pvporbit/freetype/MTFreeTypeMathTable;", "", "pointer", "", "data", "Ljava/nio/ByteBuffer;", "(JLjava/nio/ByteBuffer;)V", "constants", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "getData", "()Ljava/nio/ByteBuffer;", "horizglyphconstruction", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphConstruction;", "italicscorrectioninfo", "minConnectorOverlap", "getMinConnectorOverlap", "()I", "setMinConnectorOverlap", "(I)V", "getPointer", "()J", "topaccentattachment", "vertglyphconstruction", "getConstant", "name", "getDataRecord", "getDataSInt", "getHorizontalVariantsForGlyph", "", "gid", "getVariantsForGlyph", "construction", "getVerticalGlyphAssemblyForGlyph", "", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "(I)[Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "getVerticalVariantsForGlyph", "getitalicCorrection", "gettopAccentAttachment", "(I)Ljava/lang/Integer;", "readConstants", "", "foffset", "readCoverageTable", "(I)[Ljava/lang/Integer;", "readassembly", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphAssembly;", "readconstruction", "readmatchedtable", HtmlParser.TableElement.TAG, "readvariants", "GlyphAssembly", "GlyphPartRecord", "MathGlyphConstruction", "MathGlyphVariantRecord", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFreeTypeMathTable {
    private final HashMap<String, Integer> constants;
    private final ByteBuffer data;
    private final HashMap<Integer, MathGlyphConstruction> horizglyphconstruction;
    private final HashMap<Integer, Integer> italicscorrectioninfo;
    private int minConnectorOverlap;
    private final long pointer;
    private final HashMap<Integer, Integer> topaccentattachment;
    private final HashMap<Integer, MathGlyphConstruction> vertglyphconstruction;

    public MTFreeTypeMathTable(long j, ByteBuffer data) throws Exception {
        Intrinsics.checkNotNullParameter(data, "data");
        this.pointer = j;
        this.data = data;
        this.constants = new HashMap<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        this.italicscorrectioninfo = map;
        HashMap<Integer, Integer> map2 = new HashMap<>();
        this.topaccentattachment = map2;
        this.vertglyphconstruction = new HashMap<>();
        this.horizglyphconstruction = new HashMap<>();
        data.remaining();
        if (FreeType.FT_Load_Math_Table(j, data, data.remaining()) && data.getInt() == 65536) {
            int dataSInt = getDataSInt();
            int dataSInt2 = getDataSInt();
            int dataSInt3 = getDataSInt();
            readConstants(dataSInt);
            data.position(dataSInt2);
            int dataSInt4 = getDataSInt();
            int dataSInt5 = getDataSInt();
            readmatchedtable(dataSInt4 + dataSInt2, map);
            readmatchedtable(dataSInt2 + dataSInt5, map2);
            readvariants(dataSInt3);
        }
    }

    public final ByteBuffer getData() {
        return this.data;
    }

    public final long getPointer() {
        return this.pointer;
    }

    public final int getMinConnectorOverlap() {
        return this.minConnectorOverlap;
    }

    public final void setMinConnectorOverlap(int i) {
        this.minConnectorOverlap = i;
    }

    private final int getDataSInt() {
        return this.data.getShort();
    }

    public final int getConstant(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        Integer num = this.constants.get(name);
        Intrinsics.checkNotNull(num);
        Intrinsics.checkNotNullExpressionValue(num, "constants[name]!!");
        return num.intValue();
    }

    public final int getitalicCorrection(int gid) {
        if (this.italicscorrectioninfo.get(Integer.valueOf(gid)) == null) {
            return 0;
        }
        Integer num = this.italicscorrectioninfo.get(Integer.valueOf(gid));
        Intrinsics.checkNotNull(num);
        Intrinsics.checkNotNullExpressionValue(num, "italicscorrectioninfo[gid]!!");
        return num.intValue();
    }

    public final Integer gettopAccentAttachment(int gid) {
        return this.topaccentattachment.get(Integer.valueOf(gid));
    }

    private final List<Integer> getVariantsForGlyph(HashMap<Integer, MathGlyphConstruction> construction, int gid) {
        MathGlyphConstruction mathGlyphConstruction = construction.get(Integer.valueOf(gid));
        if (mathGlyphConstruction == null || mathGlyphConstruction.getVariants().length == 0) {
            return CollectionsKt.listOf(Integer.valueOf(gid));
        }
        ArrayList arrayList = new ArrayList();
        MathGlyphVariantRecord[] variants = mathGlyphConstruction.getVariants();
        int length = variants.length;
        int i = 0;
        while (i < length) {
            MathGlyphVariantRecord mathGlyphVariantRecord = variants[i];
            i++;
            arrayList.add(Integer.valueOf(mathGlyphVariantRecord.getVariantGlyph()));
        }
        return arrayList;
    }

    public final List<Integer> getVerticalVariantsForGlyph(int gid) {
        return getVariantsForGlyph(this.vertglyphconstruction, gid);
    }

    public final List<Integer> getHorizontalVariantsForGlyph(int gid) {
        return getVariantsForGlyph(this.horizglyphconstruction, gid);
    }

    public final GlyphPartRecord[] getVerticalGlyphAssemblyForGlyph(int gid) {
        MathGlyphConstruction mathGlyphConstruction = this.vertglyphconstruction.get(Integer.valueOf(gid));
        if ((mathGlyphConstruction == null ? null : mathGlyphConstruction.getAssembly()) == null) {
            return null;
        }
        return mathGlyphConstruction.getAssembly().getPartRecords();
    }

    private final int getDataRecord() {
        int dataSInt = getDataSInt();
        getDataSInt();
        return dataSInt;
    }

    private final void readmatchedtable(int foffset, HashMap<Integer, Integer> table) throws Exception {
        this.data.position(foffset);
        Integer[] coverageTable = readCoverageTable(foffset + getDataSInt());
        int dataSInt = getDataSInt();
        for (int i = 0; i < dataSInt; i++) {
            table.put(coverageTable[i], Integer.valueOf(getDataRecord()));
        }
    }

    private final void readConstants(int foffset) {
        this.data.position(foffset);
        for (int i = 0; i < MTFreeTypeMathTableKt.constTable.length; i += 2) {
            String str = MTFreeTypeMathTableKt.constTable[i];
            String str2 = MTFreeTypeMathTableKt.constTable[i + 1];
            if (Intrinsics.areEqual(str, "uint16") ? true : Intrinsics.areEqual(str, "int16")) {
                this.constants.put(str2, Integer.valueOf(getDataSInt()));
            } else {
                int dataSInt = getDataSInt();
                getDataSInt();
                this.constants.put(str2, Integer.valueOf(dataSInt));
            }
        }
    }

    private final Integer[] readCoverageTable(int foffset) throws Exception {
        Integer[] numArr;
        int iPosition = this.data.position();
        this.data.position(foffset);
        int dataSInt = getDataSInt();
        if (dataSInt == 1) {
            int dataSInt2 = getDataSInt();
            Integer[] numArr2 = new Integer[dataSInt2];
            for (int i = 0; i < dataSInt2; i++) {
                numArr2[i] = 0;
            }
            for (int i2 = 0; i2 < dataSInt2; i2++) {
                numArr2[i2] = Integer.valueOf(getDataSInt());
            }
            numArr = numArr2;
        } else if (dataSInt == 2) {
            int dataSInt3 = getDataSInt();
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            while (i3 < dataSInt3) {
                i3++;
                int dataSInt4 = getDataSInt();
                int dataSInt5 = getDataSInt();
                int dataSInt6 = getDataSInt();
                if (dataSInt4 <= dataSInt5) {
                    while (true) {
                        int i4 = dataSInt4 + 1;
                        int i5 = dataSInt6 + 1;
                        arrayList.add(dataSInt6, Integer.valueOf(dataSInt4));
                        if (dataSInt4 == dataSInt5) {
                            break;
                        }
                        dataSInt4 = i4;
                        dataSInt6 = i5;
                    }
                }
            }
            Object[] array = arrayList.toArray(new Integer[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            numArr = (Integer[]) array;
        } else {
            throw new Exception("Invalid coverage format");
        }
        this.data.position(iPosition);
        return numArr;
    }

    /* JADX INFO: compiled from: MTFreeTypeMathTable.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphConstruction;", "", "assembly", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphAssembly;", "variants", "", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphVariantRecord;", "(Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphAssembly;[Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphVariantRecord;)V", "getAssembly", "()Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphAssembly;", "getVariants", "()[Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphVariantRecord;", "[Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphVariantRecord;", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class MathGlyphConstruction {
        private final GlyphAssembly assembly;
        private final MathGlyphVariantRecord[] variants;

        public MathGlyphConstruction(GlyphAssembly glyphAssembly, MathGlyphVariantRecord[] variants) {
            Intrinsics.checkNotNullParameter(variants, "variants");
            this.assembly = glyphAssembly;
            this.variants = variants;
        }

        public final GlyphAssembly getAssembly() {
            return this.assembly;
        }

        public final MathGlyphVariantRecord[] getVariants() {
            return this.variants;
        }
    }

    /* JADX INFO: compiled from: MTFreeTypeMathTable.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/pvporbit/freetype/MTFreeTypeMathTable$MathGlyphVariantRecord;", "", "variantGlyph", "", "advanceMeasurement", "(II)V", "getAdvanceMeasurement", "()I", "getVariantGlyph", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class MathGlyphVariantRecord {
        private final int advanceMeasurement;
        private final int variantGlyph;

        public MathGlyphVariantRecord(int i, int i2) {
            this.variantGlyph = i;
            this.advanceMeasurement = i2;
        }

        public final int getAdvanceMeasurement() {
            return this.advanceMeasurement;
        }

        public final int getVariantGlyph() {
            return this.variantGlyph;
        }
    }

    /* JADX INFO: compiled from: MTFreeTypeMathTable.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u000f"}, d2 = {"Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "", "glyph", "", "startConnectorLength", "endConnectorLength", "fullAdvance", "partFlags", "(IIIII)V", "getEndConnectorLength", "()I", "getFullAdvance", "getGlyph", "getPartFlags", "getStartConnectorLength", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class GlyphPartRecord {
        private final int endConnectorLength;
        private final int fullAdvance;
        private final int glyph;
        private final int partFlags;
        private final int startConnectorLength;

        public GlyphPartRecord(int i, int i2, int i3, int i4, int i5) {
            this.glyph = i;
            this.startConnectorLength = i2;
            this.endConnectorLength = i3;
            this.fullAdvance = i4;
            this.partFlags = i5;
        }

        public final int getEndConnectorLength() {
            return this.endConnectorLength;
        }

        public final int getFullAdvance() {
            return this.fullAdvance;
        }

        public final int getGlyph() {
            return this.glyph;
        }

        public final int getPartFlags() {
            return this.partFlags;
        }

        public final int getStartConnectorLength() {
            return this.startConnectorLength;
        }
    }

    /* JADX INFO: compiled from: MTFreeTypeMathTable.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphAssembly;", "", "italicsCorrection", "", "partRecords", "", "Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "(I[Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;)V", "getItalicsCorrection", "()I", "getPartRecords", "()[Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "[Lcom/pvporbit/freetype/MTFreeTypeMathTable$GlyphPartRecord;", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class GlyphAssembly {
        private final int italicsCorrection;
        private final GlyphPartRecord[] partRecords;

        public GlyphAssembly(int i, GlyphPartRecord[] partRecords) {
            Intrinsics.checkNotNullParameter(partRecords, "partRecords");
            this.italicsCorrection = i;
            this.partRecords = partRecords;
        }

        public final int getItalicsCorrection() {
            return this.italicsCorrection;
        }

        public final GlyphPartRecord[] getPartRecords() {
            return this.partRecords;
        }
    }

    private final MathGlyphConstruction readconstruction(int foffset) {
        int iPosition = this.data.position();
        this.data.position(foffset);
        int dataSInt = getDataSInt();
        int dataSInt2 = getDataSInt();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dataSInt2; i++) {
            arrayList.add(i, new MathGlyphVariantRecord(getDataSInt(), getDataSInt()));
        }
        GlyphAssembly glyphAssembly = dataSInt == 0 ? null : readassembly(foffset + dataSInt);
        Object[] array = arrayList.toArray(new MathGlyphVariantRecord[0]);
        if (array != null) {
            MathGlyphConstruction mathGlyphConstruction = new MathGlyphConstruction(glyphAssembly, (MathGlyphVariantRecord[]) array);
            this.data.position(iPosition);
            return mathGlyphConstruction;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }

    private final GlyphAssembly readassembly(int foffset) {
        int iPosition = this.data.position();
        this.data.position(foffset);
        int dataRecord = getDataRecord();
        int dataSInt = getDataSInt();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dataSInt; i++) {
            arrayList.add(i, new GlyphPartRecord(getDataSInt(), getDataSInt(), getDataSInt(), getDataSInt(), getDataSInt()));
        }
        Object[] array = arrayList.toArray(new GlyphPartRecord[0]);
        if (array != null) {
            GlyphAssembly glyphAssembly = new GlyphAssembly(dataRecord, (GlyphPartRecord[]) array);
            this.data.position(iPosition);
            return glyphAssembly;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }

    private final void readvariants(int foffset) throws Exception {
        this.data.position(foffset);
        this.minConnectorOverlap = getDataSInt();
        int dataSInt = getDataSInt();
        int dataSInt2 = getDataSInt();
        int dataSInt3 = getDataSInt();
        int dataSInt4 = getDataSInt();
        Integer[] coverageTable = readCoverageTable(dataSInt + foffset);
        Integer[] coverageTable2 = readCoverageTable(dataSInt2 + foffset);
        for (int i = 0; i < dataSInt3; i++) {
            this.vertglyphconstruction.put(coverageTable[i], readconstruction(getDataSInt() + foffset));
        }
        for (int i2 = 0; i2 < dataSInt4; i2++) {
            this.horizglyphconstruction.put(coverageTable2[i2], readconstruction(getDataSInt() + foffset));
        }
    }
}
