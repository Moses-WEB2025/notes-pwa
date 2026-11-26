package xc.engine.math.iink.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: InkRepositoryImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lxc/engine/math/iink/data/InkRepositoryImpl;", "Lxc/engine/math/iink/data/InkRepository;", "dataDir", "Ljava/io/File;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/io/File;)V", "inkFile", "getInkFile", "()Ljava/io/File;", "readInkFromFile", "", "saveInkToFile", "", "jsonString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class InkRepositoryImpl implements InkRepository {
    private final File dataDir;

    public InkRepositoryImpl(File dataDir) {
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        this.dataDir = dataDir;
    }

    private final File getInkFile() {
        return new File(this.dataDir, "current.json");
    }

    @Override // xc.engine.math.iink.data.InkRepository
    public String readInkFromFile() {
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(getInkFile()), Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                String text = TextStreamsKt.readText(bufferedReader);
                CloseableKt.closeFinally(bufferedReader, null);
                return text;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    @Override // xc.engine.math.iink.data.InkRepository
    public void saveInkToFile(String jsonString) {
        Intrinsics.checkNotNullParameter(jsonString, "jsonString");
        FilesKt.writeText$default(getInkFile(), jsonString, null, 2, null);
    }
}
