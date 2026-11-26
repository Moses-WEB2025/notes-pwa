package org.jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.internal.SharedConstants;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

/* JADX INFO: loaded from: classes6.dex */
public class Jsoup {
    private Jsoup() {
    }

    public static Document parse(String str, String str2) {
        return Parser.parse(str, str2);
    }

    public static Document parse(String str, String str2, Parser parser) {
        return parser.parseInput(str, str2);
    }

    public static Document parse(String str, Parser parser) {
        return parser.parseInput(str, "");
    }

    public static Document parse(String str) {
        return Parser.parse(str, "");
    }

    public static Connection connect(String str) {
        return HttpConnection.connect(str);
    }

    public static Connection newSession() {
        return new HttpConnection();
    }

    public static Document parse(File file, String str, String str2) throws IOException {
        return DataUtil.load(file, str, str2);
    }

    public static Document parse(File file, String str) throws IOException {
        return DataUtil.load(file, str, file.getAbsolutePath());
    }

    public static Document parse(File file) throws IOException {
        return DataUtil.load(file, (String) null, file.getAbsolutePath());
    }

    public static Document parse(File file, String str, String str2, Parser parser) throws IOException {
        return DataUtil.load(file, str, str2, parser);
    }

    public static Document parse(Path path, String str, String str2) throws IOException {
        return DataUtil.load(path, str, str2);
    }

    public static Document parse(Path path, String str) throws IOException {
        return DataUtil.load(path, str, path.toAbsolutePath().toString());
    }

    public static Document parse(Path path) throws IOException {
        return DataUtil.load(path, (String) null, path.toAbsolutePath().toString());
    }

    public static Document parse(Path path, String str, String str2, Parser parser) throws IOException {
        return DataUtil.load(path, str, str2, parser);
    }

    public static Document parse(InputStream inputStream, String str, String str2) throws IOException {
        return DataUtil.load(inputStream, str, str2);
    }

    public static Document parse(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return DataUtil.load(inputStream, str, str2, parser);
    }

    public static Document parseBodyFragment(String str, String str2) {
        return Parser.parseBodyFragment(str, str2);
    }

    public static Document parseBodyFragment(String str) {
        return Parser.parseBodyFragment(str, "");
    }

    public static Document parse(URL url, int i) throws IOException {
        Connection connectionConnect = HttpConnection.connect(url);
        connectionConnect.timeout(i);
        return connectionConnect.get();
    }

    public static String clean(String str, String str2, Safelist safelist) {
        if (str2.isEmpty() && safelist.preserveRelativeLinks()) {
            str2 = SharedConstants.DummyUri;
        }
        return new Cleaner(safelist).clean(parseBodyFragment(str, str2)).body().html();
    }

    public static String clean(String str, Safelist safelist) {
        return clean(str, "", safelist);
    }

    public static String clean(String str, String str2, Safelist safelist, Document.OutputSettings outputSettings) {
        Document documentClean = new Cleaner(safelist).clean(parseBodyFragment(str, str2));
        documentClean.outputSettings(outputSettings);
        return documentClean.body().html();
    }

    public static boolean isValid(String str, Safelist safelist) {
        return new Cleaner(safelist).isValidBodyHtml(str);
    }
}
