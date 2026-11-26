package ch.qos.logback.core.rolling.helper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class FileSorter {
    private final List<FilenameParser> parsers;

    FileSorter(FilenameParser... filenameParserArr) {
        this.parsers = Arrays.asList(filenameParserArr);
    }

    void sort(String[] strArr) {
        Arrays.sort(strArr, new Comparator<String>() { // from class: ch.qos.logback.core.rolling.helper.FileSorter.1
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                int iCompareTo = 0;
                for (FilenameParser filenameParser : FileSorter.this.parsers) {
                    Comparable filename = filenameParser.parseFilename(str2);
                    Comparable filename2 = filenameParser.parseFilename(str);
                    if (filename != null && filename2 != null) {
                        iCompareTo += filename.compareTo(filename2);
                    }
                }
                return iCompareTo == 0 ? str2.compareTo(str) : iCompareTo;
            }
        });
    }
}
