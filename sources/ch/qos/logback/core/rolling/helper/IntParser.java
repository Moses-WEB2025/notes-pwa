package ch.qos.logback.core.rolling.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
class IntParser implements FilenameParser<Integer> {
    private final Pattern pathPattern;

    IntParser(FileNamePattern fileNamePattern) {
        this.pathPattern = Pattern.compile(FileFinder.unescapePath(fileNamePattern.toRegex(false, true)));
    }

    private String findToken(String str) {
        Matcher matcher = this.pathPattern.matcher(str);
        return (!matcher.find() || matcher.groupCount() < 1) ? "" : matcher.group(1);
    }

    @Override // ch.qos.logback.core.rolling.helper.FilenameParser
    public Integer parseFilename(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(findToken(str), 10));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}
