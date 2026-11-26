package ch.qos.logback.core.rolling.helper;

import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class LiteralPathPart extends PathPart {
    LiteralPathPart(String str) {
        super(str);
    }

    @Override // ch.qos.logback.core.rolling.helper.PathPart
    List<File> listFiles(FileProvider fileProvider) {
        return listFiles(fileProvider, this.part);
    }

    @Override // ch.qos.logback.core.rolling.helper.PathPart
    boolean matches(File file) {
        return file.getName().equals(this.part);
    }
}
