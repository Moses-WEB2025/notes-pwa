package org.apache.commons.text;

import ch.qos.logback.classic.net.SyslogAppender;
import ch.qos.logback.classic.spi.CallerData;
import com.miui.maml.elements.MusicLyricParser;
import defpackage.R2;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import miuix.appcompat.view.menu.HyperMenuUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.CsvTranslators;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.JavaUnicodeEscaper;
import org.apache.commons.text.translate.LookupTranslator;
import org.apache.commons.text.translate.NumericEntityEscaper;
import org.apache.commons.text.translate.NumericEntityUnescaper;
import org.apache.commons.text.translate.OctalUnescaper;
import org.apache.commons.text.translate.UnicodeUnescaper;
import org.apache.commons.text.translate.UnicodeUnpairedSurrogateRemover;

/* JADX INFO: loaded from: classes6.dex */
public class StringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_CSV;
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator ESCAPE_HTML3;
    public static final CharSequenceTranslator ESCAPE_HTML4;
    public static final CharSequenceTranslator ESCAPE_JAVA;
    public static final CharSequenceTranslator ESCAPE_JSON;
    public static final CharSequenceTranslator ESCAPE_XML10;
    public static final CharSequenceTranslator ESCAPE_XML11;
    public static final CharSequenceTranslator ESCAPE_XSI;
    public static final CharSequenceTranslator UNESCAPE_CSV;
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator UNESCAPE_HTML3;
    public static final CharSequenceTranslator UNESCAPE_HTML4;
    public static final CharSequenceTranslator UNESCAPE_JAVA;
    public static final CharSequenceTranslator UNESCAPE_JSON;
    public static final CharSequenceTranslator UNESCAPE_XML;
    public static final CharSequenceTranslator UNESCAPE_XSI;

    public static final class Builder {
        private final StringBuilder sb;
        private final CharSequenceTranslator translator;

        private Builder(CharSequenceTranslator charSequenceTranslator) {
            this.sb = new StringBuilder();
            this.translator = charSequenceTranslator;
        }

        public Builder append(String str) {
            this.sb.append(str);
            return this;
        }

        public Builder escape(String str) {
            this.sb.append(this.translator.translate(str));
            return this;
        }

        public String toString() {
            return this.sb.toString();
        }
    }

    static class XsiUnescaper extends CharSequenceTranslator {
        private static final char BACKSLASH = '\\';

        XsiUnescaper() {
        }

        @Override // org.apache.commons.text.translate.CharSequenceTranslator
        public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
            if (i != 0) {
                throw new IllegalStateException("XsiUnescaper should never reach the [1] index");
            }
            String string = charSequence.toString();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int iIndexOf = string.indexOf(92, i2);
                if (iIndexOf == -1) {
                    break;
                }
                if (iIndexOf > i3) {
                    writer.write(string.substring(i3, iIndexOf));
                }
                i3 = iIndexOf + 1;
                i2 = iIndexOf + 2;
            }
            if (i3 < string.length()) {
                writer.write(string.substring(i3));
            }
            return Character.codePointCount(charSequence, 0, charSequence.length());
        }
    }

    static {
        HashMap map = new HashMap();
        map.put("\"", "\\\"");
        map.put("\\", "\\\\");
        ESCAPE_JAVA = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(map)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127));
        HashMap map2 = new HashMap();
        map2.put("'", "\\'");
        map2.put("\"", "\\\"");
        map2.put("\\", "\\\\");
        map2.put(InternalZipConstants.ZIP_FILE_SEPARATOR, "\\/");
        ESCAPE_ECMASCRIPT = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(map2)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127));
        HashMap map3 = new HashMap();
        map3.put("\"", "\\\"");
        map3.put("\\", "\\\\");
        map3.put(InternalZipConstants.ZIP_FILE_SEPARATOR, "\\/");
        ESCAPE_JSON = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(map3)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 126));
        HashMap map4 = new HashMap();
        map4.put("\u0000", "");
        map4.put("\u0001", "");
        map4.put("\u0002", "");
        map4.put("\u0003", "");
        map4.put("\u0004", "");
        map4.put("\u0005", "");
        map4.put("\u0006", "");
        map4.put("\u0007", "");
        map4.put("\b", "");
        map4.put("\u000b", "");
        map4.put("\f", "");
        map4.put("\u000e", "");
        map4.put("\u000f", "");
        map4.put("\u0010", "");
        map4.put("\u0011", "");
        map4.put("\u0012", "");
        map4.put("\u0013", "");
        map4.put("\u0014", "");
        map4.put("\u0015", "");
        map4.put("\u0016", "");
        map4.put("\u0017", "");
        map4.put("\u0018", "");
        map4.put("\u0019", "");
        map4.put("\u001a", "");
        map4.put("\u001b", "");
        map4.put("\u001c", "");
        map4.put("\u001d", "");
        map4.put("\u001e", "");
        map4.put("\u001f", "");
        map4.put("\ufffe", "");
        map4.put("\uffff", "");
        ESCAPE_XML10 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.APOS_ESCAPE), new LookupTranslator(Collections.unmodifiableMap(map4)), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(R2.array.ai_edit_input_text_blend_colors, R2.array.ai_save_tag_download_gradient_border_colors), new UnicodeUnpairedSurrogateRemover());
        HashMap map5 = new HashMap();
        map5.put("\u0000", "");
        map5.put("\u000b", "&#11;");
        map5.put("\f", "&#12;");
        map5.put("\ufffe", "");
        map5.put("\uffff", "");
        ESCAPE_XML11 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.APOS_ESCAPE), new LookupTranslator(Collections.unmodifiableMap(map5)), NumericEntityEscaper.between(1, 8), NumericEntityEscaper.between(14, 31), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(R2.array.ai_edit_input_text_blend_colors, R2.array.ai_save_tag_download_gradient_border_colors), new UnicodeUnpairedSurrogateRemover());
        ESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE));
        ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE));
        ESCAPE_CSV = new CsvTranslators.CsvEscaper();
        HashMap map6 = new HashMap();
        map6.put("|", "\\|");
        map6.put("&", "\\&");
        map6.put(";", "\\;");
        map6.put("<", "\\<");
        map6.put(">", "\\>");
        map6.put("(", "\\(");
        map6.put(")", "\\)");
        map6.put("$", "\\$");
        map6.put("`", "\\`");
        map6.put("\\", "\\\\");
        map6.put("\"", "\\\"");
        map6.put("'", "\\'");
        map6.put(StringUtils.SPACE, "\\ ");
        map6.put(SyslogAppender.DEFAULT_STACKTRACE_PATTERN, "\\\t");
        map6.put(MusicLyricParser.CRLF, "");
        map6.put(StringUtils.LF, "");
        map6.put("*", "\\*");
        map6.put(CallerData.NA, "\\?");
        map6.put("[", "\\[");
        map6.put(HyperMenuUtils.GROUP_TITLE_START, "\\#");
        map6.put("~", "\\~");
        map6.put("=", "\\=");
        map6.put("%", "\\%");
        ESCAPE_XSI = new LookupTranslator(Collections.unmodifiableMap(map6));
        HashMap map7 = new HashMap();
        map7.put("\\\\", "\\");
        map7.put("\\\"", "\"");
        map7.put("\\'", "'");
        map7.put("\\", "");
        AggregateTranslator aggregateTranslator = new AggregateTranslator(new OctalUnescaper(), new UnicodeUnescaper(), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE), new LookupTranslator(Collections.unmodifiableMap(map7)));
        UNESCAPE_JAVA = aggregateTranslator;
        UNESCAPE_ECMASCRIPT = aggregateTranslator;
        UNESCAPE_JSON = aggregateTranslator;
        UNESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
        UNESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
        UNESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.APOS_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
        UNESCAPE_CSV = new CsvTranslators.CsvUnescaper();
        UNESCAPE_XSI = new XsiUnescaper();
    }

    public static Builder builder(CharSequenceTranslator charSequenceTranslator) {
        return new Builder(charSequenceTranslator);
    }

    public static String escapeCsv(String str) {
        return ESCAPE_CSV.translate(str);
    }

    public static String escapeEcmaScript(String str) {
        return ESCAPE_ECMASCRIPT.translate(str);
    }

    public static String escapeHtml3(String str) {
        return ESCAPE_HTML3.translate(str);
    }

    public static String escapeHtml4(String str) {
        return ESCAPE_HTML4.translate(str);
    }

    public static String escapeJava(String str) {
        return ESCAPE_JAVA.translate(str);
    }

    public static String escapeJson(String str) {
        return ESCAPE_JSON.translate(str);
    }

    public static String escapeXml10(String str) {
        return ESCAPE_XML10.translate(str);
    }

    public static String escapeXml11(String str) {
        return ESCAPE_XML11.translate(str);
    }

    public static String escapeXSI(String str) {
        return ESCAPE_XSI.translate(str);
    }

    public static String unescapeCsv(String str) {
        return UNESCAPE_CSV.translate(str);
    }

    public static String unescapeEcmaScript(String str) {
        return UNESCAPE_ECMASCRIPT.translate(str);
    }

    public static String unescapeHtml3(String str) {
        return UNESCAPE_HTML3.translate(str);
    }

    public static String unescapeHtml4(String str) {
        return UNESCAPE_HTML4.translate(str);
    }

    public static String unescapeJava(String str) {
        return UNESCAPE_JAVA.translate(str);
    }

    public static String unescapeJson(String str) {
        return UNESCAPE_JSON.translate(str);
    }

    public static String unescapeXml(String str) {
        return UNESCAPE_XML.translate(str);
    }

    public static String unescapeXSI(String str) {
        return UNESCAPE_XSI.translate(str);
    }
}
