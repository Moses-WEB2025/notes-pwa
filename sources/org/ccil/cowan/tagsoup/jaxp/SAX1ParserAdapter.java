package org.ccil.cowan.tagsoup.jaxp;

import java.io.IOException;
import java.util.Locale;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes6.dex */
public class SAX1ParserAdapter implements Parser {
    final XMLReader xmlReader;

    public SAX1ParserAdapter(XMLReader xMLReader) {
        this.xmlReader = xMLReader;
    }

    @Override // org.xml.sax.Parser
    public void parse(InputSource inputSource) throws SAXException {
        try {
            this.xmlReader.parse(inputSource);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.Parser
    public void parse(String str) throws SAXException {
        try {
            this.xmlReader.parse(str);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.Parser
    public void setDocumentHandler(DocumentHandler documentHandler) {
        this.xmlReader.setContentHandler(new DocHandlerWrapper(documentHandler));
    }

    @Override // org.xml.sax.Parser
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.xmlReader.setDTDHandler(dTDHandler);
    }

    @Override // org.xml.sax.Parser
    public void setEntityResolver(EntityResolver entityResolver) {
        this.xmlReader.setEntityResolver(entityResolver);
    }

    @Override // org.xml.sax.Parser
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.xmlReader.setErrorHandler(errorHandler);
    }

    @Override // org.xml.sax.Parser
    public void setLocale(Locale locale) throws SAXException {
        throw new SAXNotSupportedException("TagSoup does not implement setLocale() method");
    }

    static final class DocHandlerWrapper implements ContentHandler {
        final DocumentHandler docHandler;
        final AttributesWrapper mAttrWrapper = new AttributesWrapper();

        @Override // org.xml.sax.ContentHandler
        public void endPrefixMapping(String str) {
        }

        @Override // org.xml.sax.ContentHandler
        public void skippedEntity(String str) {
        }

        @Override // org.xml.sax.ContentHandler
        public void startPrefixMapping(String str, String str2) {
        }

        DocHandlerWrapper(DocumentHandler documentHandler) {
            this.docHandler = documentHandler;
        }

        @Override // org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws SAXException {
            this.docHandler.characters(cArr, i, i2);
        }

        @Override // org.xml.sax.ContentHandler
        public void endDocument() throws SAXException {
            this.docHandler.endDocument();
        }

        @Override // org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            if (str3 != null) {
                str2 = str3;
            }
            this.docHandler.endElement(str2);
        }

        @Override // org.xml.sax.ContentHandler
        public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
            this.docHandler.ignorableWhitespace(cArr, i, i2);
        }

        @Override // org.xml.sax.ContentHandler
        public void processingInstruction(String str, String str2) throws SAXException {
            this.docHandler.processingInstruction(str, str2);
        }

        @Override // org.xml.sax.ContentHandler
        public void setDocumentLocator(Locator locator) {
            this.docHandler.setDocumentLocator(locator);
        }

        @Override // org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            this.docHandler.startDocument();
        }

        @Override // org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str3 != null) {
                str2 = str3;
            }
            this.mAttrWrapper.setAttributes(attributes);
            this.docHandler.startElement(str2, this.mAttrWrapper);
        }
    }

    static final class AttributesWrapper implements AttributeList {
        Attributes attrs;

        public void setAttributes(Attributes attributes) {
            this.attrs = attributes;
        }

        @Override // org.xml.sax.AttributeList
        public int getLength() {
            return this.attrs.getLength();
        }

        @Override // org.xml.sax.AttributeList
        public String getName(int i) {
            String qName = this.attrs.getQName(i);
            return qName == null ? this.attrs.getLocalName(i) : qName;
        }

        @Override // org.xml.sax.AttributeList
        public String getType(int i) {
            return this.attrs.getType(i);
        }

        @Override // org.xml.sax.AttributeList
        public String getType(String str) {
            return this.attrs.getType(str);
        }

        @Override // org.xml.sax.AttributeList
        public String getValue(int i) {
            return this.attrs.getValue(i);
        }

        @Override // org.xml.sax.AttributeList
        public String getValue(String str) {
            return this.attrs.getValue(str);
        }
    }
}
