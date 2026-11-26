package org.ccil.cowan.tagsoup;

import ch.qos.logback.core.CoreConstants;
import java.io.PrintWriter;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

/* JADX INFO: loaded from: classes6.dex */
public class PYXWriter implements ScanHandler, ContentHandler, LexicalHandler {
    private static char[] dummy = new char[1];
    private String attrName;
    private PrintWriter theWriter;

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void cmnt(char[] cArr, int i, int i2) throws SAXException {
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void decl(char[] cArr, int i, int i2) throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endCDATA() throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endDTD() throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endEntity(String str) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void entity(char[] cArr, int i, int i2) throws SAXException {
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public int getEntity() {
        return 0;
    }

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException {
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void stagc(char[] cArr, int i, int i2) throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startCDATA() throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startDTD(String str, String str2, String str3) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startEntity(String str) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void adup(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.println(this.attrName);
        this.attrName = null;
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void aname(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.print('A');
        this.theWriter.write(cArr, i, i2);
        this.theWriter.print(' ');
        this.attrName = new String(cArr, i, i2);
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void aval(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.write(cArr, i, i2);
        this.theWriter.println();
        this.attrName = null;
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void eof(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.close();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void etag(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.print(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        this.theWriter.write(cArr, i, i2);
        this.theWriter.println();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void gi(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.print(CoreConstants.LEFT_PARENTHESIS_CHAR);
        this.theWriter.write(cArr, i, i2);
        this.theWriter.println();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void cdsect(char[] cArr, int i, int i2) throws SAXException {
        pcdata(cArr, i, i2);
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pcdata(char[] cArr, int i, int i2) throws SAXException {
        if (i2 == 0) {
            return;
        }
        int i3 = i2 + i;
        boolean z = false;
        while (i < i3) {
            if (cArr[i] == '\n') {
                if (z) {
                    this.theWriter.println();
                }
                this.theWriter.println("-\\n");
                z = false;
            } else {
                if (!z) {
                    this.theWriter.print(CoreConstants.DASH_CHAR);
                }
                char c = cArr[i];
                if (c == '\t') {
                    this.theWriter.print("\\t");
                } else if (c == '\\') {
                    this.theWriter.print("\\\\");
                } else {
                    this.theWriter.print(c);
                }
                z = true;
            }
            i++;
        }
        if (z) {
            this.theWriter.println();
        }
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pitarget(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.print('?');
        this.theWriter.write(cArr, i, i2);
        this.theWriter.write(32);
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pi(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.write(cArr, i, i2);
        this.theWriter.println();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void stage(char[] cArr, int i, int i2) throws SAXException {
        this.theWriter.println("!");
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        pcdata(cArr, i, i2);
    }

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        this.theWriter.close();
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        if (str3.length() != 0) {
            str2 = str3;
        }
        this.theWriter.print(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        this.theWriter.println(str2);
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        characters(cArr, i, i2);
    }

    @Override // org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
        this.theWriter.print('?');
        this.theWriter.print(str);
        this.theWriter.print(' ');
        this.theWriter.println(str2);
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (str3.length() != 0) {
            str2 = str3;
        }
        this.theWriter.print(CoreConstants.LEFT_PARENTHESIS_CHAR);
        this.theWriter.println(str2);
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String qName = attributes.getQName(i);
            if (qName.length() == 0) {
                qName = attributes.getLocalName(i);
            }
            this.theWriter.print('A');
            this.theWriter.print(qName);
            this.theWriter.print(' ');
            this.theWriter.println(attributes.getValue(i));
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void comment(char[] cArr, int i, int i2) throws SAXException {
        cmnt(cArr, i, i2);
    }

    public PYXWriter(Writer writer) {
        if (writer instanceof PrintWriter) {
            this.theWriter = (PrintWriter) writer;
        } else {
            this.theWriter = new PrintWriter(writer);
        }
    }
}
