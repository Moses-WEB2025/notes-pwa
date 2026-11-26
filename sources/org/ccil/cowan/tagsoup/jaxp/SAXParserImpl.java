package org.ccil.cowan.tagsoup.jaxp;

import java.util.Map;
import javax.xml.parsers.SAXParser;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes6.dex */
public class SAXParserImpl extends SAXParser {
    final Parser parser = new Parser();

    protected SAXParserImpl() {
    }

    public static SAXParserImpl newInstance(Map map) throws SAXException {
        SAXParserImpl sAXParserImpl = new SAXParserImpl();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                sAXParserImpl.setFeature((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            }
        }
        return sAXParserImpl;
    }

    @Override // javax.xml.parsers.SAXParser
    public org.xml.sax.Parser getParser() throws SAXException {
        return new SAX1ParserAdapter(this.parser);
    }

    @Override // javax.xml.parsers.SAXParser
    public XMLReader getXMLReader() {
        return this.parser;
    }

    @Override // javax.xml.parsers.SAXParser
    public boolean isNamespaceAware() {
        try {
            return this.parser.getFeature(Parser.namespacesFeature);
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public boolean isValidating() {
        try {
            return this.parser.getFeature(Parser.validationFeature);
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.parser.setProperty(str, obj);
    }

    @Override // javax.xml.parsers.SAXParser
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.parser.getProperty(str);
    }

    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.parser.setFeature(str, z);
    }

    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.parser.getFeature(str);
    }
}
