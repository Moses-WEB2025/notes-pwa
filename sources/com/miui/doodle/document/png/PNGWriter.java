package com.miui.doodle.document.png;

import com.google.common.base.Ascii;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.notes.base.utils.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PNGWriter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/doodle/document/png/PNGWriter;", "", "pngFile", "Ljava/io/File;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/io/File;)V", "write", "", "pngChunkList", "", "Lcom/miui/doodle/document/png/PNGChunk;", "createDoodleChunk", "data", "", "writeChunk", Constants.JSON_VERSION, "Ljava/io/OutputStream;", "chunk", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PNGWriter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final byte[] PNG_MAGIC_NUMBER = {-119, 80, 78, 71, 13, 10, Ascii.SUB, 10};
    private static final String TAG = "PNGWriter";
    private final File pngFile;

    public PNGWriter(File pngFile) {
        Intrinsics.checkNotNullParameter(pngFile, "pngFile");
        this.pngFile = pngFile;
    }

    public final void write(List<PNGChunk> pngChunkList) {
        Intrinsics.checkNotNullParameter(pngChunkList, "pngChunkList");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.pngFile);
            try {
                FileOutputStream fileOutputStream2 = fileOutputStream;
                fileOutputStream2.write(PNG_MAGIC_NUMBER);
                Iterator<PNGChunk> it = pngChunkList.iterator();
                while (it.hasNext()) {
                    writeChunk(fileOutputStream2, it.next());
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            Logger.INSTANCE.d(TAG, "write PNGChunk failed." + e);
        }
    }

    public final PNGChunk createDoodleChunk(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return new PNGChunk(INSTANCE.toBytes(data.length), PNGChunk.INSTANCE.getMIUI_DOODLE_TYPE_CODE(), data, null, 8, null);
    }

    private final void writeChunk(OutputStream os, PNGChunk chunk) throws IOException {
        os.write(chunk.getLength());
        os.write(chunk.getChunkTypeCode());
        os.write(chunk.getChunkData());
        os.write(chunk.getCrc());
    }

    /* JADX INFO: compiled from: PNGWriter.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lcom/miui/doodle/document/png/PNGWriter$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "PNG_MAGIC_NUMBER", "", "getPNG_MAGIC_NUMBER", "()[B", "toBytes", "value", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final byte[] getPNG_MAGIC_NUMBER() {
            return PNGWriter.PNG_MAGIC_NUMBER;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final byte[] toBytes(long value) {
            return new byte[]{(byte) (((-16777216) & value) >> 24), (byte) ((16711680 & value) >> 16), (byte) ((65280 & value) >> 8), (byte) (value & 255)};
        }
    }
}
