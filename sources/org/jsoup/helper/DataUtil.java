package org.jsoup.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.internal.ControllableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.SimpleStreamReader;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;
import org.jsoup.parser.StreamParser;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;

/* JADX INFO: loaded from: classes6.dex */
public final class DataUtil {
    public static final Charset UTF_8;
    static final int boundaryLength = 32;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:[\"'])?([^\\s,;\"']*)");
    static final String defaultCharsetName;
    private static final int firstReadBufferSize = 5120;
    private static final Evaluator metaCharset;
    private static final char[] mimeBoundaryChars;

    static {
        Charset charsetForName = Charset.forName("UTF-8");
        UTF_8 = charsetForName;
        defaultCharsetName = charsetForName.name();
        mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        metaCharset = Selector.evaluatorOf("meta[http-equiv=content-type], meta[charset]");
    }

    private DataUtil() {
    }

    public static Document load(File file, String str, String str2) throws IOException {
        return load(file.toPath(), str, str2);
    }

    public static Document load(File file, String str, String str2, Parser parser) throws IOException {
        return load(file.toPath(), str, str2, parser);
    }

    public static Document load(Path path, String str, String str2) throws IOException {
        return load(path, str, str2, Parser.htmlParser());
    }

    public static Document load(Path path, String str, String str2, Parser parser) throws IOException {
        return parseInputStream(openStream(path), str, str2, parser);
    }

    public static StreamParser streamParser(Path path, Charset charset, String str, Parser parser) throws IOException {
        StreamParser streamParser = new StreamParser(parser);
        try {
            CharsetDoc charsetDocDetectCharset = detectCharset(openStream(path), charset != null ? charset.name() : null, str, parser);
            streamParser.parse(new SimpleStreamReader(charsetDocDetectCharset.input, charsetDocDetectCharset.charset), str);
            return streamParser;
        } catch (IOException e) {
            streamParser.close();
            throw e;
        }
    }

    private static ControllableInputStream openStream(Path path) throws IOException {
        SeekableByteChannel seekableByteChannelNewByteChannel = Files.newByteChannel(path, new OpenOption[0]);
        InputStream inputStreamNewInputStream = Channels.newInputStream(seekableByteChannelNewByteChannel);
        String strLowerCase = Normalizer.lowerCase(path.getFileName().toString());
        if (strLowerCase.endsWith(".gz") || strLowerCase.endsWith(".z")) {
            try {
                boolean z = inputStreamNewInputStream.read() == 31 && inputStreamNewInputStream.read() == 139;
                seekableByteChannelNewByteChannel.position(0L);
                if (z) {
                    inputStreamNewInputStream = new GZIPInputStream(inputStreamNewInputStream);
                }
            } catch (IOException e) {
                inputStreamNewInputStream.close();
                throw e;
            }
        }
        return ControllableInputStream.wrap(inputStreamNewInputStream, 0);
    }

    public static Document load(InputStream inputStream, String str, String str2) throws IOException {
        return parseInputStream(ControllableInputStream.wrap(inputStream, 0), str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return parseInputStream(ControllableInputStream.wrap(inputStream, 0), str, str2, parser);
    }

    static void crossStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    static class CharsetDoc {
        Charset charset;
        Document doc;
        InputStream input;

        CharsetDoc(Charset charset, Document document, InputStream inputStream) {
            this.charset = charset;
            this.input = inputStream;
            this.doc = document;
        }
    }

    static Document parseInputStream(ControllableInputStream controllableInputStream, String str, String str2, Parser parser) throws Throwable {
        CharsetDoc charsetDocDetectCharset;
        if (controllableInputStream == null) {
            return new Document(str2);
        }
        try {
            charsetDocDetectCharset = detectCharset(controllableInputStream, str, str2, parser);
            try {
                Document inputStream = parseInputStream(charsetDocDetectCharset, str2, parser);
                if (charsetDocDetectCharset != null) {
                    charsetDocDetectCharset.input.close();
                }
                return inputStream;
            } catch (Throwable th) {
                th = th;
                if (charsetDocDetectCharset != null) {
                    charsetDocDetectCharset.input.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            charsetDocDetectCharset = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x008f  */
    static CharsetDoc detectCharset(ControllableInputStream controllableInputStream, String str, String str2, Parser parser) throws IOException {
        XmlDeclaration xmlDeclarationAsXmlDeclaration;
        String strDetectCharsetFromBom = detectCharsetFromBom(controllableInputStream);
        if (strDetectCharsetFromBom != null) {
            str = strDetectCharsetFromBom;
        }
        Document document = null;
        if (str == null) {
            int iMax = controllableInputStream.max();
            controllableInputStream.max(5120);
            controllableInputStream.mark(5120);
            controllableInputStream.allowClose(false);
            try {
                try {
                    SimpleStreamReader simpleStreamReader = new SimpleStreamReader(controllableInputStream, UTF_8);
                    try {
                        Document input = parser.parseInput(simpleStreamReader, str2);
                        controllableInputStream.reset();
                        controllableInputStream.max(iMax);
                        simpleStreamReader.close();
                        controllableInputStream.allowClose(true);
                        String strAttr = null;
                        for (Element element : input.select(metaCharset)) {
                            if (element.hasAttr("http-equiv")) {
                                strAttr = getCharsetFromContentType(element.attr("content"));
                            }
                            if (strAttr == null && element.hasAttr("charset")) {
                                strAttr = element.attr("charset");
                            }
                            if (strAttr != null) {
                                break;
                            }
                        }
                        if (strAttr == null && input.childNodeSize() > 0) {
                            Node nodeChildNode = input.childNode(0);
                            if (nodeChildNode instanceof XmlDeclaration) {
                                xmlDeclarationAsXmlDeclaration = (XmlDeclaration) nodeChildNode;
                            } else if (nodeChildNode instanceof Comment) {
                                Comment comment = (Comment) nodeChildNode;
                                if (comment.isXmlDeclaration()) {
                                    xmlDeclarationAsXmlDeclaration = comment.asXmlDeclaration();
                                } else {
                                    xmlDeclarationAsXmlDeclaration = null;
                                }
                            } else {
                                xmlDeclarationAsXmlDeclaration = null;
                            }
                            if (xmlDeclarationAsXmlDeclaration != null && xmlDeclarationAsXmlDeclaration.name().equalsIgnoreCase(StringLookupFactory.KEY_XML)) {
                                strAttr = xmlDeclarationAsXmlDeclaration.attr(XMLWriter.ENCODING);
                            }
                        }
                        String strValidateCharset = validateCharset(strAttr);
                        if (strValidateCharset != null && !strValidateCharset.equalsIgnoreCase(defaultCharsetName)) {
                            str = strValidateCharset.trim().replaceAll("[\"']", "");
                        } else if (controllableInputStream.baseReadFully()) {
                            controllableInputStream.close();
                            document = input;
                        }
                    } catch (Throwable th) {
                        try {
                            simpleStreamReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (UncheckedIOException e) {
                    throw e.getCause();
                }
            } catch (Throwable th3) {
                controllableInputStream.allowClose(true);
                throw th3;
            }
        } else {
            Validate.notEmpty(str, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        }
        if (str == null) {
            str = defaultCharsetName;
        }
        return new CharsetDoc(str.equals(defaultCharsetName) ? UTF_8 : Charset.forName(str), document, controllableInputStream);
    }

    static Document parseInputStream(CharsetDoc charsetDoc, String str, Parser parser) throws IOException {
        if (charsetDoc.doc != null) {
            return charsetDoc.doc;
        }
        InputStream inputStream = charsetDoc.input;
        Validate.notNull(inputStream);
        Charset charset = charsetDoc.charset;
        SimpleStreamReader simpleStreamReader = new SimpleStreamReader(inputStream, charset);
        try {
            try {
                Document input = parser.parseInput(simpleStreamReader, str);
                input.outputSettings().charset(charset);
                if (!charset.canEncode()) {
                    input.charset(UTF_8);
                }
                simpleStreamReader.close();
                return input;
            } catch (Throwable th) {
                try {
                    simpleStreamReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream, int i) throws IOException {
        return ControllableInputStream.readToByteBuffer(inputStream, i);
    }

    static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }

    static String getCharsetFromContentType(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = charsetPattern.matcher(str);
        if (matcher.find()) {
            return validateCharset(matcher.group(1).trim().replace("charset=", ""));
        }
        return null;
    }

    private static String validateCharset(String str) {
        if (str != null && str.length() != 0) {
            String strReplaceAll = str.trim().replaceAll("[\"']", "");
            try {
                if (Charset.isSupported(strReplaceAll)) {
                    return strReplaceAll;
                }
                String upperCase = strReplaceAll.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(upperCase)) {
                    return upperCase;
                }
            } catch (IllegalCharsetNameException unused) {
            }
        }
        return null;
    }

    static String mimeBoundary() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            char[] cArr = mimeBoundaryChars;
            sbBorrowBuilder.append(cArr[random.nextInt(cArr.length)]);
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private static String detectCharsetFromBom(ControllableInputStream controllableInputStream) throws IOException {
        byte[] bArr = new byte[4];
        controllableInputStream.mark(4);
        controllableInputStream.read(bArr, 0, 4);
        controllableInputStream.reset();
        byte b = bArr[0];
        if (b == 0 && bArr[1] == 0 && bArr[2] == -2 && bArr[3] == -1) {
            return "UTF-32";
        }
        if (b == -1 && bArr[1] == -2 && bArr[2] == 0 && bArr[3] == 0) {
            return "UTF-32";
        }
        if (b == -2 && bArr[1] == -1) {
            return "UTF-16";
        }
        if (b == -1 && bArr[1] == -2) {
            return "UTF-16";
        }
        if (b != -17 || bArr[1] != -69 || bArr[2] != -65) {
            return null;
        }
        controllableInputStream.read(bArr, 0, 3);
        return "UTF-8";
    }
}
