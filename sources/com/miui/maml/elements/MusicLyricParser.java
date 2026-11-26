package com.miui.maml.elements;

import android.text.Html;
import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class MusicLyricParser {
    public static final String CRLF = "\r\n";
    private static final int INTERVAL_OF_LAST = 8000;
    private static final int LINE_PARSE_IGNORE = 1;
    private static final int LINE_PARSE_REGULAR = 2;
    private static final int LINE_PARSE_STOP = 0;
    public static final int MAX_VALID_TIME = 18000000;
    private static final String TAG_ALBUM = "al";
    private static final String TAG_ARTIST = "ar";
    private static final String TAG_EDITOR = "by";
    private static final Pattern TAG_EXTRA_LRC = Pattern.compile("<[0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}>");
    private static final String TAG_OFFSET = "offset";
    private static final String TAG_TITLE = "ti";
    private static final String TAG_VERSION = "ve";

    public static class LyricHeader {
        public String album;
        public String artist;
        public String editor;
        public int offset;
        public String title;
        public String version;
    }

    public static class LyricShot {
        public int lineIndex;
        public double percent;

        public LyricShot(int i, double d) {
            this.lineIndex = i;
            this.percent = d;
        }
    }

    public static class LyricEntity {
        private static final String HTML_BR_PATTERN = "%s<br/>";
        public CharSequence lyric;
        public int time;

        public LyricEntity(int i, String str) {
            this.time = i;
            this.lyric = str;
        }

        public void decorate() {
            this.lyric = Html.fromHtml(String.format(HTML_BR_PATTERN, this.lyric));
        }

        public boolean isDecorated() {
            return !(this.lyric instanceof String);
        }
    }

    public static class Lyric {
        private final LyricEntity EMPTY_AFTER;
        private final ArrayList<LyricEntity> mEntityList;
        private final LyricHeader mHeader;
        private boolean mIsModified;
        private int mOriginHeaderOffset;
        private LyricLocator mLyricLocator = new LyricLocator();
        private final long mOpenTime = System.currentTimeMillis();
        private final LyricEntity EMPTY_BEFORE = new LyricEntity(-1, StringUtils.LF);

        public Lyric(LyricHeader lyricHeader, ArrayList<LyricEntity> arrayList, boolean z) {
            this.mHeader = lyricHeader;
            this.mOriginHeaderOffset = lyricHeader.offset;
            this.mEntityList = arrayList;
            this.mIsModified = z;
            this.EMPTY_AFTER = new LyricEntity(arrayList.size(), StringUtils.LF);
        }

        public void addOffset(int i) {
            this.mHeader.offset += i;
            this.mIsModified = true;
        }

        public void resetHeaderOffset() {
            this.mHeader.offset = this.mOriginHeaderOffset;
        }

        public boolean isModified() {
            return this.mIsModified;
        }

        public int size() {
            return this.mEntityList.size();
        }

        public LyricShot getLyricShot(long j) {
            int i = this.mHeader.offset;
            if (this.mEntityList.get(0).time + i > j) {
                return new LyricShot(0, 0.0d);
            }
            for (int i2 = 1; i2 < this.mEntityList.size(); i2++) {
                int i3 = this.mEntityList.get(i2).time + i;
                if (i3 > j) {
                    int i4 = i2 - 1;
                    int i5 = this.mEntityList.get(i4).time + i;
                    return new LyricShot(i4, i3 > i5 ? (j - ((long) i5)) / ((double) (i3 - i5)) : 0.0d);
                }
            }
            long j2 = j - ((long) (this.mEntityList.get(size() - 1).time + i));
            if (j2 < 8000) {
                return new LyricShot(size() - 1, j2 / 8000.0d);
            }
            return new LyricShot(this.mEntityList.size(), 0.0d);
        }

        private long getTimeFromLyricShot(int i, double d) {
            int size = size() - 1;
            if (i >= size) {
                return ((long) (this.mEntityList.get(size).time + ((i - size) * 8000))) + ((long) (d * 8000.0d));
            }
            return (long) (((double) this.mEntityList.get(i).time) + (((double) (this.mEntityList.get(i + 1).time - this.mEntityList.get(i).time)) * d));
        }

        public void correctLyric(LyricShot lyricShot, int i, double d) {
            int size = size();
            if (i < 0 || i > size || lyricShot.lineIndex < 0 || lyricShot.lineIndex > size) {
                return;
            }
            long timeFromLyricShot = getTimeFromLyricShot(lyricShot.lineIndex, lyricShot.percent);
            long timeFromLyricShot2 = getTimeFromLyricShot(i, d);
            boolean z = i <= lyricShot.lineIndex && (i != lyricShot.lineIndex || d <= lyricShot.percent);
            if (z || timeFromLyricShot <= timeFromLyricShot2) {
                if (!z || timeFromLyricShot >= timeFromLyricShot2) {
                    addOffset((int) (timeFromLyricShot - timeFromLyricShot2));
                }
            }
        }

        public LyricEntity getLyricContent(int i) {
            if (i < 0) {
                return this.EMPTY_BEFORE;
            }
            if (i >= this.mEntityList.size()) {
                return this.EMPTY_AFTER;
            }
            return this.mEntityList.get(i);
        }

        public long getOpenTime() {
            return this.mOpenTime;
        }

        public ArrayList<CharSequence> getStringArr() {
            if (this.mEntityList.isEmpty()) {
                return null;
            }
            ArrayList<CharSequence> arrayList = new ArrayList<>(this.mEntityList.size());
            Iterator<LyricEntity> it = this.mEntityList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().lyric);
            }
            return arrayList;
        }

        public int[] getTimeArr() {
            if (this.mEntityList.isEmpty()) {
                return null;
            }
            int[] iArr = new int[this.mEntityList.size()];
            Iterator<LyricEntity> it = this.mEntityList.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = it.next().time + this.mHeader.offset;
                i++;
            }
            return iArr;
        }

        public void recycleContent() {
            this.mEntityList.clear();
        }

        public void decorate() {
            ArrayList<LyricEntity> arrayList;
            int size;
            if (!this.mEntityList.isEmpty() && (size = (arrayList = this.mEntityList).size()) > 0) {
                if (arrayList.get(0).isDecorated()) {
                    return;
                }
                for (int i = 0; i < size; i++) {
                    arrayList.get(i).decorate();
                }
            }
        }

        class LyricLine {
            CharSequence lyric;
            int pos;

            LyricLine() {
            }
        }

        class LyricLocator {
            final int CRLF_LENGTH = MusicLyricParser.CRLF.length();
            String mFullLyric;
            ArrayList<LyricLine> mLyricLines;
            int[] mTimeArr;

            LyricLocator() {
            }

            void set(int[] iArr, ArrayList<CharSequence> arrayList) {
                this.mTimeArr = iArr;
                inflateLyricLines(arrayList);
            }

            private void inflateLyricLines(ArrayList<CharSequence> arrayList) {
                int[] iArr = this.mTimeArr;
                if (iArr == null || arrayList == null || iArr.length != arrayList.size()) {
                    this.mTimeArr = null;
                    this.mLyricLines = null;
                    return;
                }
                this.mLyricLines = new ArrayList<>();
                int i = 0;
                while (i < this.mTimeArr.length) {
                    CharSequence charSequence = arrayList.get(i);
                    LyricLine lyricLine = Lyric.this.new LyricLine();
                    lyricLine.lyric = charSequence;
                    LyricLine lyricLine2 = i > 0 ? this.mLyricLines.get(i - 1) : null;
                    lyricLine.pos = lyricLine2 != null ? lyricLine2.pos + lyricLine2.lyric.length() + this.CRLF_LENGTH : 0;
                    this.mLyricLines.add(lyricLine);
                    i++;
                }
                this.mFullLyric = "";
                for (int i2 = 0; i2 < this.mLyricLines.size(); i2++) {
                    this.mFullLyric += ((Object) this.mLyricLines.get(i2).lyric) + MusicLyricParser.CRLF;
                }
            }

            String getLine(long j) {
                int lineNumber;
                if (this.mTimeArr == null || (lineNumber = getLineNumber(j)) == -1) {
                    return null;
                }
                LyricLine lyricLine = this.mLyricLines.get(lineNumber);
                return this.mFullLyric.substring(lyricLine.pos, lyricLine.pos + lyricLine.lyric.length());
            }

            private int getLineNumber(long j) {
                int i = 0;
                while (true) {
                    int[] iArr = this.mTimeArr;
                    if (i >= iArr.length) {
                        return -1;
                    }
                    if (j >= iArr[i]) {
                        if (j < (i < iArr.length + (-1) ? iArr[i + 1] : Long.MAX_VALUE)) {
                            return i;
                        }
                    }
                    i++;
                }
            }

            String getBeforeLines(long j) {
                int lineNumber;
                if (this.mTimeArr != null && (lineNumber = getLineNumber(j)) > 0) {
                    return this.mFullLyric.substring(0, this.mLyricLines.get(lineNumber).pos - this.CRLF_LENGTH);
                }
                return null;
            }

            String getAfterLines(long j) {
                if (this.mTimeArr == null) {
                    return null;
                }
                int lineNumber = getLineNumber(j);
                if (lineNumber < 0) {
                    return this.mFullLyric;
                }
                if (lineNumber >= this.mTimeArr.length - 1) {
                    return null;
                }
                LyricLine lyricLine = this.mLyricLines.get(lineNumber);
                return this.mFullLyric.substring(lyricLine.pos + lyricLine.lyric.length() + this.CRLF_LENGTH, this.mFullLyric.length());
            }

            String getLastLine(long j) {
                int lineNumber;
                if (this.mTimeArr == null || (lineNumber = getLineNumber(j)) <= 0) {
                    return null;
                }
                LyricLine lyricLine = this.mLyricLines.get(lineNumber - 1);
                return this.mFullLyric.substring(lyricLine.pos, lyricLine.pos + lyricLine.lyric.length());
            }

            String getNextLine(long j) {
                int lineNumber;
                if (this.mTimeArr == null || (lineNumber = getLineNumber(j)) < -1 || lineNumber >= this.mTimeArr.length - 1) {
                    return null;
                }
                LyricLine lyricLine = this.mLyricLines.get(lineNumber + 1);
                return this.mFullLyric.substring(lyricLine.pos, lyricLine.pos + lyricLine.lyric.length());
            }
        }

        public void set(int[] iArr, ArrayList<CharSequence> arrayList) {
            this.mLyricLocator.set(iArr, arrayList);
        }

        public String getLine(long j) {
            return this.mLyricLocator.getLine(j);
        }

        public String getBeforeLines(long j) {
            return this.mLyricLocator.getBeforeLines(j);
        }

        public String getAfterLines(long j) {
            return this.mLyricLocator.getAfterLines(j);
        }

        public String getLastLine(long j) {
            return this.mLyricLocator.getLastLine(j);
        }

        public String getNextLine(long j) {
            return this.mLyricLocator.getNextLine(j);
        }
    }

    private static void correctTime(Lyric lyric) {
        if (lyric == null) {
            return;
        }
        ArrayList arrayList = lyric.mEntityList;
        int size = arrayList.size();
        if (size > 1 && ((LyricEntity) arrayList.get(0)).time == ((LyricEntity) arrayList.get(1)).time) {
            ((LyricEntity) arrayList.get(0)).time = ((LyricEntity) arrayList.get(1)).time / 2;
        }
        int i = 1;
        while (i < size - 1) {
            int i2 = i + 1;
            if (((LyricEntity) arrayList.get(i)).time == ((LyricEntity) arrayList.get(i2)).time) {
                ((LyricEntity) arrayList.get(i)).time = (((LyricEntity) arrayList.get(i - 1)).time + ((LyricEntity) arrayList.get(i2)).time) / 2;
            }
            i = i2;
        }
    }

    public static Lyric parseLyric(String str) {
        Lyric lyricDoParse = null;
        if (str != null) {
            try {
                lyricDoParse = doParse(str);
                correctTime(lyricDoParse);
                return lyricDoParse;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lyricDoParse;
    }

    private static Lyric doParse(String str) throws IOException {
        LyricHeader lyricHeader = new LyricHeader();
        ArrayList arrayList = new ArrayList();
        String[] strArrSplit = str.split(CRLF);
        boolean z = false;
        if (strArrSplit != null) {
            boolean z2 = false;
            for (String str2 : strArrSplit) {
                int line = parseLine(str2, lyricHeader, arrayList);
                if (line == 0) {
                    break;
                }
                if (line == 1) {
                    z2 = true;
                }
            }
            z = z2;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        Collections.sort(arrayList, new EntityCompator());
        return new Lyric(lyricHeader, arrayList, z);
    }

    static class EntityCompator implements Comparator<LyricEntity> {
        EntityCompator() {
        }

        @Override // java.util.Comparator
        public int compare(LyricEntity lyricEntity, LyricEntity lyricEntity2) {
            return lyricEntity.time - lyricEntity2.time;
        }
    }

    private static int parseLine(String str, LyricHeader lyricHeader, ArrayList<LyricEntity> arrayList) {
        String strReplaceAll;
        int iLastIndexOf;
        String strTrim = str.trim();
        if (TextUtils.isEmpty(strTrim) || (iLastIndexOf = (strReplaceAll = TAG_EXTRA_LRC.matcher(strTrim).replaceAll("")).lastIndexOf("]")) == -1) {
            return 1;
        }
        String strSubstring = strReplaceAll.substring(iLastIndexOf + 1);
        int iIndexOf = strReplaceAll.indexOf("[");
        if (iIndexOf == -1) {
            return 1;
        }
        String[] strArrSplit = strReplaceAll.substring(iIndexOf, iLastIndexOf).split("]");
        int header = 2;
        for (String str2 : strArrSplit) {
            if (str2.startsWith("[")) {
                String strSubstring2 = str2.substring(1);
                String[] strArrSplit2 = strSubstring2.split(":");
                if (strArrSplit2.length >= 2) {
                    if (TextUtils.isDigitsOnly(strArrSplit2[0])) {
                        header = parseEntity(strArrSplit2, arrayList, strSubstring);
                    } else {
                        header = parseHeader(strSubstring2, lyricHeader);
                    }
                }
            }
        }
        return header;
    }

    private static int parseHeader(String str, LyricHeader lyricHeader) {
        int iIndexOf = str.indexOf(":");
        if (iIndexOf >= 0 && iIndexOf < str.length() - 1) {
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1);
            if (strSubstring.equals(TAG_ALBUM)) {
                lyricHeader.album = strSubstring2;
                return 2;
            }
            if (strSubstring.equals(TAG_ARTIST)) {
                lyricHeader.artist = strSubstring2;
                return 2;
            }
            if (strSubstring.equals(TAG_TITLE)) {
                lyricHeader.title = strSubstring2;
                return 2;
            }
            if (strSubstring.equals(TAG_EDITOR)) {
                lyricHeader.editor = strSubstring2;
                return 2;
            }
            if (strSubstring.equals(TAG_VERSION)) {
                lyricHeader.version = strSubstring2;
                return 2;
            }
            if (strSubstring.equals("offset")) {
                try {
                    lyricHeader.offset = Integer.parseInt(strSubstring2);
                    return 2;
                } catch (NumberFormatException unused) {
                }
            }
        }
        return 1;
    }

    private static int parseEntity(String[] strArr, ArrayList<LyricEntity> arrayList, String str) {
        try {
            int i = (int) (Double.parseDouble(strArr[strArr.length - 1]) * 1000.0d);
            int i2 = 0;
            int i3 = 60;
            for (int length = strArr.length - 2; length >= 0; length--) {
                int i4 = Integer.parseInt(strArr[length]) * i3;
                i3 *= 60;
                i2 += i4;
            }
            int i5 = i + (i2 * 1000);
            if (i5 < 18000000) {
                arrayList.add(new LyricEntity(i5, str));
            }
            return 2;
        } catch (NumberFormatException unused) {
            return 1;
        }
    }
}
