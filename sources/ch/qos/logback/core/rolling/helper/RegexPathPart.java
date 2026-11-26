package ch.qos.logback.core.rolling.helper;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
class RegexPathPart extends PathPart {
    private Pattern pattern;

    RegexPathPart(String str) {
        super(str);
        this.pattern = Pattern.compile(str);
    }

    @Override // ch.qos.logback.core.rolling.helper.PathPart
    List<File> listFiles(FileProvider fileProvider) {
        return listFiles(fileProvider, ".");
    }

    @Override // ch.qos.logback.core.rolling.helper.PathPart
    boolean matches(File file) {
        return this.pattern.matcher(file.getName()).find();
    }
}
