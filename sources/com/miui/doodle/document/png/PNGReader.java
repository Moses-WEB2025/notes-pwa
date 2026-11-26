package com.miui.doodle.document.png;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PNGReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001f\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tJ\b\u0010\u000b\u001a\u0004\u0018\u00010\nJ\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0012\u001a\u00020\u0010H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0005H\u0003R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/doodle/document/png/PNGReader;", "", "pngFile", "Ljava/io/File;", "pngData", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/io/File;[B)V", "read", "", "Lcom/miui/doodle/document/png/PNGChunk;", "readDoodleChunk", "hasDoodleChunk", "", "readInternal", "ios", "Ljava/io/InputStream;", "readChunk", "ins", "toInt", "", "data", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PNGReader {
    private static final String TAG = "PNGReader";
    private final byte[] pngData;
    private final File pngFile;

    public PNGReader() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public PNGReader(File file, byte[] bArr) {
        this.pngFile = file;
        this.pngData = bArr;
    }

    public /* synthetic */ PNGReader(File file, byte[] bArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : file, (i & 2) != 0 ? null : bArr);
    }

    public final List<PNGChunk> read() {
        if (this.pngFile != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(this.pngFile);
                try {
                    List<PNGChunk> internal = readInternal(fileInputStream);
                    CloseableKt.closeFinally(fileInputStream, null);
                    return internal;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            } catch (IOException e) {
                Logger.INSTANCE.e(TAG, "read png failed." + e.getMessage());
            }
        } else if (this.pngData != null) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.pngData);
                try {
                    List<PNGChunk> internal2 = readInternal(byteArrayInputStream);
                    CloseableKt.closeFinally(byteArrayInputStream, null);
                    return internal2;
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(byteArrayInputStream, th3);
                        throw th4;
                    }
                }
            } catch (IOException e2) {
                Logger.INSTANCE.e(TAG, "read png failed." + e2);
            }
        }
        return null;
    }

    public final PNGChunk readDoodleChunk() {
        List<PNGChunk> list = read();
        if (list == null) {
            return null;
        }
        for (PNGChunk pNGChunk : list) {
            byte[] chunkTypeCode = pNGChunk.getChunkTypeCode();
            if (chunkTypeCode[0] == PNGChunk.INSTANCE.getMIUI_DOODLE_TYPE_CODE()[0] && chunkTypeCode[1] == PNGChunk.INSTANCE.getMIUI_DOODLE_TYPE_CODE()[1] && chunkTypeCode[2] == PNGChunk.INSTANCE.getMIUI_DOODLE_TYPE_CODE()[2] && chunkTypeCode[3] == PNGChunk.INSTANCE.getMIUI_DOODLE_TYPE_CODE()[3]) {
                return pNGChunk;
            }
        }
        return null;
    }

    public final boolean hasDoodleChunk() {
        return readDoodleChunk() != null;
    }

    private final List<PNGChunk> readInternal(InputStream ios) throws IOException {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = new byte[8];
        if (ios.read(bArr) != 8) {
            return null;
        }
        for (int i = 0; i < 8; i++) {
            if (bArr[i] != PNGWriter.INSTANCE.getPNG_MAGIC_NUMBER()[i]) {
                return null;
            }
        }
        while (true) {
            PNGChunk chunk = readChunk(ios);
            if (chunk == null) {
                return arrayList;
            }
            if (chunk != null) {
                arrayList.add(chunk);
            }
        }
    }

    private final PNGChunk readChunk(InputStream ins) throws IOException {
        byte[] bArr;
        byte[] bArr2 = new byte[4];
        if (ins.read(bArr2) != 4) {
            return null;
        }
        int i = toInt(bArr2);
        byte[] bArr3 = new byte[4];
        if (ins.read(bArr3) != 4) {
            return null;
        }
        if (i > 0) {
            bArr = new byte[i];
            if (ins.read(bArr) != i) {
                return null;
            }
        } else {
            bArr = new byte[0];
        }
        byte[] bArr4 = new byte[4];
        if (ins.read(bArr4) != 4) {
            return null;
        }
        return new PNGChunk(bArr2, bArr3, bArr, bArr4);
    }

    private final int toInt(byte[] data) {
        long jM2550constructorimpl = 0;
        for (byte b : data) {
            jM2550constructorimpl = (jM2550constructorimpl << 8) | (((long) UByte.m2550constructorimpl((byte) (UByte.m2550constructorimpl(b) & UByte.m2550constructorimpl((byte) 255)))) & 255);
        }
        return (int) jM2550constructorimpl;
    }
}
