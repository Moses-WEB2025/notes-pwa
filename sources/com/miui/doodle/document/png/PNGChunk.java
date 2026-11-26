package com.miui.doodle.document.png;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.zip.CRC32;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PNGChunk.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0010\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/miui/doodle/document/png/PNGChunk;", "", "length", "", "chunkTypeCode", "chunkData", "crc", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([B[B[B[B)V", "getLength", "()[B", "setLength", "([B)V", "getChunkTypeCode", "setChunkTypeCode", "getChunkData", "setChunkData", "getCrc", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PNGChunk {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final byte[] MIUI_DOODLE_TYPE_CODE = {109, 105, 85, 105};
    private byte[] chunkData;
    private byte[] chunkTypeCode;
    private final byte[] crc;
    private byte[] length;

    public PNGChunk(byte[] length, byte[] chunkTypeCode, byte[] chunkData, byte[] bArr) {
        Intrinsics.checkNotNullParameter(length, "length");
        Intrinsics.checkNotNullParameter(chunkTypeCode, "chunkTypeCode");
        Intrinsics.checkNotNullParameter(chunkData, "chunkData");
        this.length = length;
        this.chunkTypeCode = chunkTypeCode;
        this.chunkData = chunkData;
        this.crc = bArr;
    }

    public final byte[] getLength() {
        return this.length;
    }

    public final void setLength(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.length = bArr;
    }

    public final byte[] getChunkTypeCode() {
        return this.chunkTypeCode;
    }

    public final void setChunkTypeCode(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.chunkTypeCode = bArr;
    }

    public final byte[] getChunkData() {
        return this.chunkData;
    }

    public final void setChunkData(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.chunkData = bArr;
    }

    public /* synthetic */ PNGChunk(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, bArr2, bArr3, (i & 8) != 0 ? INSTANCE.crc32(bArr2, bArr3) : bArr4);
    }

    public final byte[] getCrc() {
        return this.crc;
    }

    /* JADX INFO: compiled from: PNGChunk.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lcom/miui/doodle/document/png/PNGChunk$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MIUI_DOODLE_TYPE_CODE", "", "getMIUI_DOODLE_TYPE_CODE", "()[B", "crc32", "chunkTypeCode", "chunkData", "toBytes", "value", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final byte[] getMIUI_DOODLE_TYPE_CODE() {
            return PNGChunk.MIUI_DOODLE_TYPE_CODE;
        }

        public final byte[] crc32(byte[] chunkTypeCode, byte[] chunkData) {
            Intrinsics.checkNotNullParameter(chunkTypeCode, "chunkTypeCode");
            Intrinsics.checkNotNullParameter(chunkData, "chunkData");
            CRC32 crc32 = new CRC32();
            crc32.update(chunkTypeCode);
            crc32.update(chunkData);
            return toBytes(crc32.getValue());
        }

        private final byte[] toBytes(long value) {
            return new byte[]{(byte) (((-16777216) & value) >> 24), (byte) ((16711680 & value) >> 16), (byte) ((65280 & value) >> 8), (byte) (value & 255)};
        }
    }
}
