package com.miui.doodle.store;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.DoodleDocument;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleZipStore.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\b\u0016\u0018\u00002\u00020\u0001B\u001d\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016J\u0012\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0012\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u0014\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0014J\b\u0010\u0015\u001a\u00020\u0010H\u0014J\b\u0010\u0016\u001a\u00020\u0010H\u0014J\u0012\u0010\u0017\u001a\u00020\u00102\b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u0019\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u001b\u001a\u00020\u00102\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0014¨\u0006\u001d"}, d2 = {"Lcom/miui/doodle/store/DoodleZipStore;", "Lcom/miui/doodle/store/DoodleStore;", "document", "Lcom/miui/doodle/document/DoodleDocument;", "filePath", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/document/DoodleDocument;Ljava/lang/String;)V", "write", "", "data", "", "length", "directoryList", "", "enterDirectory", "", "directory", "openWrite", "name", "openRead", "closeWrite", "closeOpen", "enterRelativeDirectory", "dirName", "enterAbsoluteDirectory", "path", "fileExists", "absPath", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleZipStore extends DoodleStore {
    @Override // com.miui.doodle.store.DoodleStore
    protected boolean closeOpen() {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean closeWrite() {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    public List<String> directoryList() {
        return null;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean enterAbsoluteDirectory(String path) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    public boolean enterDirectory(String directory) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean enterRelativeDirectory(String dirName) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean fileExists(String absPath) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean openRead(String name) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    protected boolean openWrite(String name) {
        return false;
    }

    @Override // com.miui.doodle.store.DoodleStore
    public long write(byte[] data, long length) {
        return 0L;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected DoodleZipStore(DoodleDocument doodleDocument, String str) {
        super(doodleDocument, str);
        Intrinsics.checkNotNull(doodleDocument);
        Intrinsics.checkNotNull(str);
    }
}
