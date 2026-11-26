package org.ccil.cowan.tagsoup;

import ch.qos.logback.classic.spi.CallerData;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

/* JADX INFO: loaded from: classes6.dex */
public class CommandLine {
    static Hashtable options;
    private static String theOutputEncoding;
    private static Parser theParser;
    private static HTMLSchema theSchema;

    static {
        Hashtable hashtable = new Hashtable();
        options = hashtable;
        hashtable.put("--nocdata", Boolean.FALSE);
        options.put("--files", Boolean.FALSE);
        options.put("--reuse", Boolean.FALSE);
        options.put("--nons", Boolean.FALSE);
        options.put("--nobogons", Boolean.FALSE);
        options.put("--any", Boolean.FALSE);
        options.put("--emptybogons", Boolean.FALSE);
        options.put("--norootbogons", Boolean.FALSE);
        options.put("--pyxin", Boolean.FALSE);
        options.put("--lexical", Boolean.FALSE);
        options.put("--pyx", Boolean.FALSE);
        options.put("--html", Boolean.FALSE);
        options.put("--method=", Boolean.FALSE);
        options.put("--doctype-public=", Boolean.FALSE);
        options.put("--doctype-system=", Boolean.FALSE);
        options.put("--output-encoding=", Boolean.FALSE);
        options.put("--omit-xml-declaration", Boolean.FALSE);
        options.put("--encoding=", Boolean.FALSE);
        options.put("--help", Boolean.FALSE);
        options.put("--version", Boolean.FALSE);
        options.put("--nodefaults", Boolean.FALSE);
        options.put("--nocolons", Boolean.FALSE);
        options.put("--norestart", Boolean.FALSE);
        options.put("--ignorable", Boolean.FALSE);
        theParser = null;
        theSchema = null;
        theOutputEncoding = null;
    }

    public static void main(String[] strArr) throws SAXException, IOException {
        String str;
        int i = getopts(options, strArr);
        if (hasOption(options, "--help")) {
            doHelp();
            return;
        }
        if (hasOption(options, "--version")) {
            System.err.println("TagSoup version 1.2");
            return;
        }
        if (strArr.length == i) {
            process("", System.out);
            return;
        }
        if (hasOption(options, "--files")) {
            while (i < strArr.length) {
                String str2 = strArr[i];
                int iLastIndexOf = str2.lastIndexOf(46);
                if (iLastIndexOf == -1) {
                    str = str2 + ".xhtml";
                } else {
                    str = str2.endsWith(".xhtml") ? str2 + "_" : str2.substring(0, iLastIndexOf) + ".xhtml";
                }
                System.err.println("src: " + str2 + " dst: " + str);
                process(str2, new FileOutputStream(str));
                i++;
            }
            return;
        }
        while (i < strArr.length) {
            System.err.println("src: " + strArr[i]);
            process(strArr[i], System.out);
            i++;
        }
    }

    private static void doHelp() {
        System.err.print("usage: java -jar tagsoup-*.jar ");
        System.err.print(" [ ");
        Enumeration enumerationKeys = options.keys();
        boolean z = true;
        while (enumerationKeys.hasMoreElements()) {
            if (!z) {
                System.err.print("| ");
            }
            String str = (String) enumerationKeys.nextElement();
            System.err.print(str);
            if (str.endsWith("=")) {
                System.err.print(CallerData.NA);
            }
            System.err.print(StringUtils.SPACE);
            z = false;
        }
        System.err.println("]*");
    }

    private static void process(String str, OutputStream outputStream) throws SAXException, IOException {
        Parser parser;
        OutputStreamWriter outputStreamWriter;
        String str2;
        if (hasOption(options, "--reuse")) {
            if (theParser == null) {
                theParser = new Parser();
            }
            parser = theParser;
        } else {
            parser = new Parser();
        }
        HTMLSchema hTMLSchema = new HTMLSchema();
        theSchema = hTMLSchema;
        parser.setProperty(Parser.schemaProperty, hTMLSchema);
        if (hasOption(options, "--nocdata")) {
            parser.setFeature(Parser.CDATAElementsFeature, false);
        }
        if (hasOption(options, "--nons") || hasOption(options, "--html")) {
            parser.setFeature(Parser.namespacesFeature, false);
        }
        if (hasOption(options, "--nobogons")) {
            parser.setFeature(Parser.ignoreBogonsFeature, true);
        }
        if (hasOption(options, "--any")) {
            parser.setFeature(Parser.bogonsEmptyFeature, false);
        } else if (hasOption(options, "--emptybogons")) {
            parser.setFeature(Parser.bogonsEmptyFeature, true);
        }
        if (hasOption(options, "--norootbogons")) {
            parser.setFeature(Parser.rootBogonsFeature, false);
        }
        if (hasOption(options, "--nodefaults")) {
            parser.setFeature(Parser.defaultAttributesFeature, false);
        }
        if (hasOption(options, "--nocolons")) {
            parser.setFeature(Parser.translateColonsFeature, true);
        }
        if (hasOption(options, "--norestart")) {
            parser.setFeature(Parser.restartElementsFeature, false);
        }
        if (hasOption(options, "--ignorable")) {
            parser.setFeature(Parser.ignorableWhitespaceFeature, true);
        }
        if (hasOption(options, "--pyxin")) {
            parser.setProperty(Parser.scannerProperty, new PYXScanner());
        }
        if (theOutputEncoding == null) {
            outputStreamWriter = new OutputStreamWriter(outputStream);
        } else {
            outputStreamWriter = new OutputStreamWriter(outputStream, theOutputEncoding);
        }
        ContentHandler contentHandlerChooseContentHandler = chooseContentHandler(outputStreamWriter);
        parser.setContentHandler(contentHandlerChooseContentHandler);
        if (hasOption(options, "--lexical") && (contentHandlerChooseContentHandler instanceof LexicalHandler)) {
            parser.setProperty(Parser.lexicalHandlerProperty, contentHandlerChooseContentHandler);
        }
        InputSource inputSource = new InputSource();
        if (str != "") {
            inputSource.setSystemId(str);
        } else {
            inputSource.setByteStream(System.in);
        }
        if (hasOption(options, "--encoding=") && (str2 = (String) options.get("--encoding=")) != null) {
            inputSource.setEncoding(str2);
        }
        parser.parse(inputSource);
    }

    private static ContentHandler chooseContentHandler(Writer writer) {
        String str;
        String str2;
        String str3;
        if (hasOption(options, "--pyx")) {
            return new PYXWriter(writer);
        }
        XMLWriter xMLWriter = new XMLWriter(writer);
        if (hasOption(options, "--html")) {
            xMLWriter.setOutputProperty(XMLWriter.METHOD, "html");
            xMLWriter.setOutputProperty(XMLWriter.OMIT_XML_DECLARATION, BooleanUtils.YES);
        }
        if (hasOption(options, "--method=") && (str3 = (String) options.get("--method=")) != null) {
            xMLWriter.setOutputProperty(XMLWriter.METHOD, str3);
        }
        if (hasOption(options, "--doctype-public=") && (str2 = (String) options.get("--doctype-public=")) != null) {
            xMLWriter.setOutputProperty(XMLWriter.DOCTYPE_PUBLIC, str2);
        }
        if (hasOption(options, "--doctype-system=") && (str = (String) options.get("--doctype-system=")) != null) {
            xMLWriter.setOutputProperty(XMLWriter.DOCTYPE_SYSTEM, str);
        }
        if (hasOption(options, "--output-encoding=")) {
            String str4 = (String) options.get("--output-encoding=");
            theOutputEncoding = str4;
            if (str4 != null) {
                xMLWriter.setOutputProperty(XMLWriter.ENCODING, str4);
            }
        }
        if (hasOption(options, "--omit-xml-declaration")) {
            xMLWriter.setOutputProperty(XMLWriter.OMIT_XML_DECLARATION, BooleanUtils.YES);
        }
        xMLWriter.setPrefix(theSchema.getURI(), "");
        return xMLWriter;
    }

    private static int getopts(Hashtable hashtable, String[] strArr) {
        String strSubstring;
        int i = 0;
        while (i < strArr.length) {
            String strSubstring2 = strArr[i];
            if (strSubstring2.charAt(0) != '-') {
                break;
            }
            int iIndexOf = strSubstring2.indexOf(61);
            if (iIndexOf != -1) {
                int i2 = iIndexOf + 1;
                strSubstring = strSubstring2.substring(i2, strSubstring2.length());
                strSubstring2 = strSubstring2.substring(0, i2);
            } else {
                strSubstring = null;
            }
            if (!hashtable.containsKey(strSubstring2)) {
                System.err.print("Unknown option ");
                System.err.println(strSubstring2);
                System.exit(1);
            } else if (strSubstring == null) {
                hashtable.put(strSubstring2, Boolean.TRUE);
            } else {
                hashtable.put(strSubstring2, strSubstring);
            }
            i++;
        }
        return i;
    }

    private static boolean hasOption(Hashtable hashtable, String str) {
        return Boolean.getBoolean(str) || hashtable.get(str) != Boolean.FALSE;
    }
}
