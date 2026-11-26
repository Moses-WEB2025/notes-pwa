package com.miui.doodle.store;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.DoodleDocument;
import java.util.List;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: DoodleStore.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u001b\b&\u0018\u0000 ;2\u00020\u0001:\u0002:;B\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\r\u001a\u00020\tJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u000e\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u0011J\u001a\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u0011H&J\u0006\u0010\u0016\u001a\u00020\u0011J\u0006\u0010\u0017\u001a\u00020\tJ\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u0012\u0010\u001a\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u001bH&J\u0012\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H&J\u0006\u0010\u001e\u001a\u00020\tJ\u0006\u0010\u001f\u001a\u00020\u0005J\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020!J\u0010\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0005J\u0010\u0010%\u001a\u00020\t2\b\u0010&\u001a\u0004\u0018\u00010\u0005J\u001a\u0010'\u001a\u00020\t2\b\u0010(\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u000fJ\u000e\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u0011J\u0006\u0010*\u001a\u00020\u0011J\u0006\u0010+\u001a\u00020\tJ\u0006\u0010,\u001a\u00020\tJ\u001a\u0010-\u001a\u00020!2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010.\u001a\u0004\u0018\u00010\u0005J\b\u0010/\u001a\u00020\tH\u0004J\u0012\u00100\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0005H$J\u0012\u00101\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0005H$J\b\u00102\u001a\u00020\tH$J\b\u00103\u001a\u00020\tH$J\u0012\u00104\u001a\u00020\t2\b\u00105\u001a\u0004\u0018\u00010\u0005H$J\u0012\u00106\u001a\u00020\t2\b\u00107\u001a\u0004\u0018\u00010\u0005H$J\u0012\u00108\u001a\u00020\t2\b\u00109\u001a\u0004\u0018\u00010\u0005H$R\u0011\u0010\u000b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006<"}, d2 = {"Lcom/miui/doodle/store/DoodleStore;", "", "document", "Lcom/miui/doodle/document/DoodleDocument;", "filePath", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/document/DoodleDocument;Ljava/lang/String;)V", "open", "", "name", "isOpen", "()Z", SplitLayout.TAG_CLOSE, "read", "", "max", "", "write", "data", "buffer", "length", "size", "bad", "mode", "Lcom/miui/doodle/store/DoodleStore$Mode;", "directoryList", "", "enterDirectory", "directory", "leaveDirectory", "currentPath", "pushDirectory", "", "popDirectory", "hasFile", "fileName", "hasDirectory", "directoryName", "extractFile", "sourceName", "seek", "pos", "atEnd", "finish", "setSubstitution", "substitution", "doFinalize", "openWrite", "openRead", "closeWrite", "closeOpen", "enterRelativeDirectory", "dirName", "enterAbsoluteDirectory", "path", "fileExists", "absPath", "Mode", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class DoodleStore {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: DoodleStore.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/miui/doodle/store/DoodleStore$Mode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "Read", "Write", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum Mode {
        Read,
        Write;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Mode> getEntries() {
            return $ENTRIES;
        }
    }

    public final boolean atEnd() {
        return false;
    }

    public final boolean bad() {
        return true;
    }

    public final boolean close() {
        return false;
    }

    protected abstract boolean closeOpen();

    protected abstract boolean closeWrite();

    public abstract List<String> directoryList();

    protected final boolean doFinalize() {
        return true;
    }

    protected abstract boolean enterAbsoluteDirectory(String path);

    public abstract boolean enterDirectory(String directory);

    protected abstract boolean enterRelativeDirectory(String dirName);

    public final boolean extractFile(String sourceName, byte[] data) {
        return false;
    }

    protected abstract boolean fileExists(String absPath);

    public final boolean finish() {
        return false;
    }

    public final boolean hasDirectory(String directoryName) {
        return false;
    }

    public final boolean hasFile(String fileName) {
        return false;
    }

    public final boolean isOpen() {
        return false;
    }

    public final boolean leaveDirectory() {
        return false;
    }

    public final Mode mode() {
        return null;
    }

    public final boolean open(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return false;
    }

    protected abstract boolean openRead(String name);

    protected abstract boolean openWrite(String name);

    public final void popDirectory() {
    }

    public final long pos() {
        return 0L;
    }

    public final void pushDirectory() {
    }

    public final long read(byte[] buffer, long length) {
        return 0L;
    }

    public final byte[] read(long max) {
        return null;
    }

    public final boolean seek(long pos) {
        return false;
    }

    public final void setSubstitution(String name, String substitution) {
    }

    public final long size() {
        return 0L;
    }

    public final long write(byte[] data) {
        return 0L;
    }

    public abstract long write(byte[] data, long length);

    protected DoodleStore(DoodleDocument document, String filePath) {
        Intrinsics.checkNotNullParameter(document, "document");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
    }

    public final String currentPath() {
        return "";
    }

    /* JADX INFO: compiled from: DoodleStore.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b¨\u0006\f"}, d2 = {"Lcom/miui/doodle/store/DoodleStore$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", "Lcom/miui/doodle/store/DoodleStore;", "fileName", "", "mode", "Lcom/miui/doodle/store/DoodleStore$Mode;", "appIdentification", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final DoodleStore create(String fileName, Mode mode, byte[] appIdentification) {
            return null;
        }

        private Companion() {
        }
    }
}
