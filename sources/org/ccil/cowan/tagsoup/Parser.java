package org.ccil.cowan.tagsoup;

import ch.qos.logback.core.CoreConstants;
import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.text.Typography;
import okio.Utf8;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.jsoup.nodes.DocumentType;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes6.dex */
public class Parser extends DefaultHandler implements ScanHandler, XMLReader, LexicalHandler {
    public static final String CDATAElementsFeature = "http://www.ccil.org/~cowan/tagsoup/features/cdata-elements";
    private static boolean DEFAULT_BOGONS_EMPTY = false;
    private static boolean DEFAULT_CDATA_ELEMENTS = true;
    private static boolean DEFAULT_DEFAULT_ATTRIBUTES = true;
    private static boolean DEFAULT_IGNORABLE_WHITESPACE = false;
    private static boolean DEFAULT_IGNORE_BOGONS = false;
    private static boolean DEFAULT_NAMESPACES = true;
    private static boolean DEFAULT_RESTART_ELEMENTS = true;
    private static boolean DEFAULT_ROOT_BOGONS = true;
    private static boolean DEFAULT_TRANSLATE_COLONS = false;
    public static final String XML11Feature = "http://xml.org/sax/features/xml-1.1";
    public static final String autoDetectorProperty = "http://www.ccil.org/~cowan/tagsoup/properties/auto-detector";
    public static final String bogonsEmptyFeature = "http://www.ccil.org/~cowan/tagsoup/features/bogons-empty";
    public static final String defaultAttributesFeature = "http://www.ccil.org/~cowan/tagsoup/features/default-attributes";
    public static final String externalGeneralEntitiesFeature = "http://xml.org/sax/features/external-general-entities";
    public static final String externalParameterEntitiesFeature = "http://xml.org/sax/features/external-parameter-entities";
    public static final String ignorableWhitespaceFeature = "http://www.ccil.org/~cowan/tagsoup/features/ignorable-whitespace";
    public static final String ignoreBogonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons";
    public static final String isStandaloneFeature = "http://xml.org/sax/features/is-standalone";
    public static final String lexicalHandlerParameterEntitiesFeature = "http://xml.org/sax/features/lexical-handler/parameter-entities";
    public static final String lexicalHandlerProperty = "http://xml.org/sax/properties/lexical-handler";
    public static final String namespacePrefixesFeature = "http://xml.org/sax/features/namespace-prefixes";
    public static final String namespacesFeature = "http://xml.org/sax/features/namespaces";
    public static final String resolveDTDURIsFeature = "http://xml.org/sax/features/resolve-dtd-uris";
    public static final String restartElementsFeature = "http://www.ccil.org/~cowan/tagsoup/features/restart-elements";
    public static final String rootBogonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/root-bogons";
    public static final String scannerProperty = "http://www.ccil.org/~cowan/tagsoup/properties/scanner";
    public static final String schemaProperty = "http://www.ccil.org/~cowan/tagsoup/properties/schema";
    public static final String stringInterningFeature = "http://xml.org/sax/features/string-interning";
    public static final String translateColonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/translate-colons";
    public static final String unicodeNormalizationCheckingFeature = "http://xml.org/sax/features/unicode-normalization-checking";
    public static final String useAttributes2Feature = "http://xml.org/sax/features/use-attributes2";
    public static final String useEntityResolver2Feature = "http://xml.org/sax/features/use-entity-resolver2";
    public static final String useLocator2Feature = "http://xml.org/sax/features/use-locator2";
    public static final String validationFeature = "http://xml.org/sax/features/validation";
    public static final String xmlnsURIsFeature = "http://xml.org/sax/features/xmlns-uris";
    private String theAttributeName;
    private AutoDetector theAutoDetector;
    private char[] theCommentBuffer;
    private boolean theDoctypeIsPresent;
    private String theDoctypeName;
    private String theDoctypePublicId;
    private String theDoctypeSystemId;
    private int theEntity;
    private HashMap theFeatures;
    private Element theNewElement;
    private Element thePCDATA;
    private String thePITarget;
    private Element theSaved;
    private Scanner theScanner;
    private Schema theSchema;
    private Element theStack;
    private boolean virginStack;
    private static char[] etagchars = {Typography.less, '/', Typography.greater};
    private static String legal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%";
    private ContentHandler theContentHandler = this;
    private LexicalHandler theLexicalHandler = this;
    private DTDHandler theDTDHandler = this;
    private ErrorHandler theErrorHandler = this;
    private EntityResolver theEntityResolver = this;
    private boolean namespaces = DEFAULT_NAMESPACES;
    private boolean ignoreBogons = DEFAULT_IGNORE_BOGONS;
    private boolean bogonsEmpty = DEFAULT_BOGONS_EMPTY;
    private boolean rootBogons = DEFAULT_ROOT_BOGONS;
    private boolean defaultAttributes = DEFAULT_DEFAULT_ATTRIBUTES;
    private boolean translateColons = DEFAULT_TRANSLATE_COLONS;
    private boolean restartElements = DEFAULT_RESTART_ELEMENTS;
    private boolean ignorableWhitespace = DEFAULT_IGNORABLE_WHITESPACE;
    private boolean CDATAElements = DEFAULT_CDATA_ELEMENTS;

    @Override // org.xml.sax.ext.LexicalHandler
    public void comment(char[] cArr, int i, int i2) throws SAXException {
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

    @Override // org.xml.sax.ext.LexicalHandler
    public void startCDATA() throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startDTD(String str, String str2, String str3) throws SAXException {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startEntity(String str) throws SAXException {
    }

    public Parser() {
        HashMap map = new HashMap();
        this.theFeatures = map;
        map.put(namespacesFeature, truthValue(DEFAULT_NAMESPACES));
        this.theFeatures.put(namespacePrefixesFeature, Boolean.FALSE);
        this.theFeatures.put(externalGeneralEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(externalParameterEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(isStandaloneFeature, Boolean.FALSE);
        this.theFeatures.put(lexicalHandlerParameterEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(resolveDTDURIsFeature, Boolean.TRUE);
        this.theFeatures.put(stringInterningFeature, Boolean.TRUE);
        this.theFeatures.put(useAttributes2Feature, Boolean.FALSE);
        this.theFeatures.put(useLocator2Feature, Boolean.FALSE);
        this.theFeatures.put(useEntityResolver2Feature, Boolean.FALSE);
        this.theFeatures.put(validationFeature, Boolean.FALSE);
        this.theFeatures.put(xmlnsURIsFeature, Boolean.FALSE);
        this.theFeatures.put(xmlnsURIsFeature, Boolean.FALSE);
        this.theFeatures.put(XML11Feature, Boolean.FALSE);
        this.theFeatures.put(ignoreBogonsFeature, truthValue(DEFAULT_IGNORE_BOGONS));
        this.theFeatures.put(bogonsEmptyFeature, truthValue(DEFAULT_BOGONS_EMPTY));
        this.theFeatures.put(rootBogonsFeature, truthValue(DEFAULT_ROOT_BOGONS));
        this.theFeatures.put(defaultAttributesFeature, truthValue(DEFAULT_DEFAULT_ATTRIBUTES));
        this.theFeatures.put(translateColonsFeature, truthValue(DEFAULT_TRANSLATE_COLONS));
        this.theFeatures.put(restartElementsFeature, truthValue(DEFAULT_RESTART_ELEMENTS));
        this.theFeatures.put(ignorableWhitespaceFeature, truthValue(DEFAULT_IGNORABLE_WHITESPACE));
        this.theFeatures.put(CDATAElementsFeature, truthValue(DEFAULT_CDATA_ELEMENTS));
        this.theNewElement = null;
        this.theAttributeName = null;
        this.theDoctypeIsPresent = false;
        this.theDoctypePublicId = null;
        this.theDoctypeSystemId = null;
        this.theDoctypeName = null;
        this.thePITarget = null;
        this.theStack = null;
        this.theSaved = null;
        this.thePCDATA = null;
        this.theEntity = 0;
        this.virginStack = true;
        this.theCommentBuffer = new char[2000];
    }

    private static Boolean truthValue(boolean z) {
        return z ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override // org.xml.sax.XMLReader
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        Boolean bool = (Boolean) this.theFeatures.get(str);
        if (bool == null) {
            throw new SAXNotRecognizedException("Unknown feature " + str);
        }
        return bool.booleanValue();
    }

    @Override // org.xml.sax.XMLReader
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (((Boolean) this.theFeatures.get(str)) == null) {
            throw new SAXNotRecognizedException("Unknown feature " + str);
        }
        if (z) {
            this.theFeatures.put(str, Boolean.TRUE);
        } else {
            this.theFeatures.put(str, Boolean.FALSE);
        }
        if (str.equals(namespacesFeature)) {
            this.namespaces = z;
            return;
        }
        if (str.equals(ignoreBogonsFeature)) {
            this.ignoreBogons = z;
            return;
        }
        if (str.equals(bogonsEmptyFeature)) {
            this.bogonsEmpty = z;
            return;
        }
        if (str.equals(rootBogonsFeature)) {
            this.rootBogons = z;
            return;
        }
        if (str.equals(defaultAttributesFeature)) {
            this.defaultAttributes = z;
            return;
        }
        if (str.equals(translateColonsFeature)) {
            this.translateColons = z;
            return;
        }
        if (str.equals(restartElementsFeature)) {
            this.restartElements = z;
        } else if (str.equals(ignorableWhitespaceFeature)) {
            this.ignorableWhitespace = z;
        } else if (str.equals(CDATAElementsFeature)) {
            this.CDATAElements = z;
        }
    }

    @Override // org.xml.sax.XMLReader
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str.equals(lexicalHandlerProperty)) {
            LexicalHandler lexicalHandler = this.theLexicalHandler;
            if (lexicalHandler == this) {
                return null;
            }
            return lexicalHandler;
        }
        if (str.equals(scannerProperty)) {
            return this.theScanner;
        }
        if (str.equals(schemaProperty)) {
            return this.theSchema;
        }
        if (str.equals(autoDetectorProperty)) {
            return this.theAutoDetector;
        }
        throw new SAXNotRecognizedException("Unknown property " + str);
    }

    @Override // org.xml.sax.XMLReader
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str.equals(lexicalHandlerProperty)) {
            if (obj == null) {
                this.theLexicalHandler = this;
                return;
            } else {
                if (obj instanceof LexicalHandler) {
                    this.theLexicalHandler = (LexicalHandler) obj;
                    return;
                }
                throw new SAXNotSupportedException("Your lexical handler is not a LexicalHandler");
            }
        }
        if (str.equals(scannerProperty)) {
            if (obj instanceof Scanner) {
                this.theScanner = (Scanner) obj;
                return;
            }
            throw new SAXNotSupportedException("Your scanner is not a Scanner");
        }
        if (str.equals(schemaProperty)) {
            if (obj instanceof Schema) {
                this.theSchema = (Schema) obj;
                return;
            }
            throw new SAXNotSupportedException("Your schema is not a Schema");
        }
        if (str.equals(autoDetectorProperty)) {
            if (obj instanceof AutoDetector) {
                this.theAutoDetector = (AutoDetector) obj;
                return;
            }
            throw new SAXNotSupportedException("Your auto-detector is not an AutoDetector");
        }
        throw new SAXNotRecognizedException("Unknown property " + str);
    }

    @Override // org.xml.sax.XMLReader
    public void setEntityResolver(EntityResolver entityResolver) {
        if (entityResolver == null) {
            entityResolver = this;
        }
        this.theEntityResolver = entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public EntityResolver getEntityResolver() {
        EntityResolver entityResolver = this.theEntityResolver;
        if (entityResolver == this) {
            return null;
        }
        return entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public void setDTDHandler(DTDHandler dTDHandler) {
        if (dTDHandler == null) {
            dTDHandler = this;
        }
        this.theDTDHandler = dTDHandler;
    }

    @Override // org.xml.sax.XMLReader
    public DTDHandler getDTDHandler() {
        DTDHandler dTDHandler = this.theDTDHandler;
        if (dTDHandler == this) {
            return null;
        }
        return dTDHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void setContentHandler(ContentHandler contentHandler) {
        if (contentHandler == null) {
            contentHandler = this;
        }
        this.theContentHandler = contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public ContentHandler getContentHandler() {
        ContentHandler contentHandler = this.theContentHandler;
        if (contentHandler == this) {
            return null;
        }
        return contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            errorHandler = this;
        }
        this.theErrorHandler = errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public ErrorHandler getErrorHandler() {
        ErrorHandler errorHandler = this.theErrorHandler;
        if (errorHandler == this) {
            return null;
        }
        return errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void parse(InputSource inputSource) throws SAXException, IOException {
        setup();
        Reader reader = getReader(inputSource);
        this.theContentHandler.startDocument();
        this.theScanner.resetDocumentLocator(inputSource.getPublicId(), inputSource.getSystemId());
        Scanner scanner = this.theScanner;
        if (scanner instanceof Locator) {
            this.theContentHandler.setDocumentLocator((Locator) scanner);
        }
        if (!this.theSchema.getURI().equals("")) {
            this.theContentHandler.startPrefixMapping(this.theSchema.getPrefix(), this.theSchema.getURI());
        }
        this.theScanner.scan(reader, this);
    }

    @Override // org.xml.sax.XMLReader
    public void parse(String str) throws SAXException, IOException {
        parse(new InputSource(str));
    }

    private void setup() {
        if (this.theSchema == null) {
            this.theSchema = new HTMLSchema();
        }
        if (this.theScanner == null) {
            this.theScanner = new HTMLScanner();
        }
        if (this.theAutoDetector == null) {
            this.theAutoDetector = new AutoDetector() { // from class: org.ccil.cowan.tagsoup.Parser.1
                @Override // org.ccil.cowan.tagsoup.AutoDetector
                public Reader autoDetectingReader(InputStream inputStream) {
                    return new InputStreamReader(inputStream);
                }
            };
        }
        this.theStack = new Element(this.theSchema.getElementType("<root>"), this.defaultAttributes);
        this.thePCDATA = new Element(this.theSchema.getElementType("<pcdata>"), this.defaultAttributes);
        this.theNewElement = null;
        this.theAttributeName = null;
        this.thePITarget = null;
        this.theSaved = null;
        this.theEntity = 0;
        this.virginStack = true;
        this.theDoctypeSystemId = null;
        this.theDoctypePublicId = null;
        this.theDoctypeName = null;
    }

    private Reader getReader(InputSource inputSource) throws SAXException, IOException {
        Reader characterStream = inputSource.getCharacterStream();
        InputStream byteStream = inputSource.getByteStream();
        String encoding = inputSource.getEncoding();
        String publicId = inputSource.getPublicId();
        String systemId = inputSource.getSystemId();
        if (characterStream != null) {
            return characterStream;
        }
        if (byteStream == null) {
            byteStream = getInputStream(publicId, systemId);
        }
        if (encoding == null) {
            return this.theAutoDetector.autoDetectingReader(byteStream);
        }
        try {
            return new InputStreamReader(byteStream, encoding);
        } catch (UnsupportedEncodingException unused) {
            return new InputStreamReader(byteStream);
        }
    }

    private InputStream getInputStream(String str, String str2) throws SAXException, IOException {
        return new URL(new URL("file", "", System.getProperty("user.dir") + "/."), str2).openConnection().getInputStream();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void adup(char[] cArr, int i, int i2) throws SAXException {
        String str;
        Element element = this.theNewElement;
        if (element == null || (str = this.theAttributeName) == null) {
            return;
        }
        element.setAttribute(str, null, str);
        this.theAttributeName = null;
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void aname(char[] cArr, int i, int i2) throws SAXException {
        if (this.theNewElement == null) {
            return;
        }
        this.theAttributeName = makeName(cArr, i, i2).toLowerCase();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void aval(char[] cArr, int i, int i2) throws SAXException {
        if (this.theNewElement == null || this.theAttributeName == null) {
            return;
        }
        this.theNewElement.setAttribute(this.theAttributeName, null, expandEntities(new String(cArr, i, i2)));
        this.theAttributeName = null;
    }

    private String expandEntities(String str) {
        int length = str.length();
        char[] cArr = new char[length];
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            int i4 = i2 + 1;
            cArr[i2] = cCharAt;
            if (cCharAt == '&' && i == -1) {
                i2 = i4;
                i = i2;
            } else if (i == -1 || Character.isLetter(cCharAt) || Character.isDigit(cCharAt) || cCharAt == '#') {
                i2 = i4;
            } else if (cCharAt == ';') {
                int iLookupEntity = lookupEntity(cArr, i, (i4 - i) - 1);
                if (iLookupEntity > 65535) {
                    int i5 = iLookupEntity - 65536;
                    cArr[i - 1] = (char) ((i5 >> 10) + 55296);
                    cArr[i] = (char) ((i5 & R2.attr.endActionMenuEnabled) + Utf8.LOG_SURROGATE_HEADER);
                    i++;
                } else if (iLookupEntity != 0) {
                    cArr[i - 1] = (char) iLookupEntity;
                } else {
                    i = i4;
                }
                i2 = i;
                i = -1;
            } else {
                i = -1;
                i2 = i4;
            }
        }
        return new String(cArr, 0, i2);
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void entity(char[] cArr, int i, int i2) throws SAXException {
        this.theEntity = lookupEntity(cArr, i, i2);
    }

    private int lookupEntity(char[] cArr, int i, int i2) {
        char c;
        if (i2 < 1) {
            return 0;
        }
        if (cArr[i] != '#') {
            return this.theSchema.getEntity(new String(cArr, i, i2));
        }
        if (i2 > 1 && ((c = cArr[i + 1]) == 'x' || c == 'X')) {
            try {
                return Integer.parseInt(new String(cArr, i + 2, i2 - 2), 16);
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
        try {
            return Integer.parseInt(new String(cArr, i + 1, i2 - 1), 10);
        } catch (NumberFormatException unused2) {
            return 0;
        }
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void eof(char[] cArr, int i, int i2) throws SAXException {
        if (this.virginStack) {
            rectify(this.thePCDATA);
        }
        while (this.theStack.next() != null) {
            pop();
        }
        if (!this.theSchema.getURI().equals("")) {
            this.theContentHandler.endPrefixMapping(this.theSchema.getPrefix());
        }
        this.theContentHandler.endDocument();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void etag(char[] cArr, int i, int i2) throws SAXException {
        if (etag_cdata(cArr, i, i2)) {
            return;
        }
        etag_basic(cArr, i, i2);
    }

    public boolean etag_cdata(char[] cArr, int i, int i2) throws SAXException {
        String strName = this.theStack.name();
        if (this.CDATAElements && (this.theStack.flags() & 2) != 0) {
            boolean z = i2 == strName.length();
            if (z) {
                for (int i3 = 0; i3 < i2; i3++) {
                    if (Character.toLowerCase(cArr[i + i3]) != Character.toLowerCase(strName.charAt(i3))) {
                        z = false;
                        break;
                    }
                }
            }
            if (!z) {
                this.theContentHandler.characters(etagchars, 0, 2);
                this.theContentHandler.characters(cArr, i, i2);
                this.theContentHandler.characters(etagchars, 2, 1);
                this.theScanner.startCDATA();
                return true;
            }
        }
        return false;
    }

    public void etag_basic(char[] cArr, int i, int i2) throws SAXException {
        String strName;
        this.theNewElement = null;
        if (i2 != 0) {
            ElementType elementType = this.theSchema.getElementType(makeName(cArr, i, i2));
            if (elementType == null) {
                return;
            } else {
                strName = elementType.name();
            }
        } else {
            strName = this.theStack.name();
        }
        Element next = this.theStack;
        boolean z = false;
        while (next != null && !next.name().equals(strName)) {
            if ((next.flags() & 4) != 0) {
                z = true;
            }
            next = next.next();
        }
        if (next == null || next.next() == null || next.next().next() == null) {
            return;
        }
        if (z) {
            next.preclose();
        } else {
            while (this.theStack != next) {
                restartablyPop();
            }
            pop();
        }
        while (this.theStack.isPreclosed()) {
            pop();
        }
        restart(null);
    }

    private void restart(Element element) throws SAXException {
        while (true) {
            Element element2 = this.theSaved;
            if (element2 == null || !this.theStack.canContain(element2)) {
                return;
            }
            if (element != null && !this.theSaved.canContain(element)) {
                return;
            }
            Element next = this.theSaved.next();
            push(this.theSaved);
            this.theSaved = next;
        }
    }

    private void pop() throws SAXException {
        Element element = this.theStack;
        if (element == null) {
            return;
        }
        String strName = element.name();
        String strLocalName = this.theStack.localName();
        String strNamespace = this.theStack.namespace();
        String strPrefixOf = prefixOf(strName);
        if (!this.namespaces) {
            strLocalName = "";
            strNamespace = "";
        }
        this.theContentHandler.endElement(strNamespace, strLocalName, strName);
        if (foreign(strPrefixOf, strNamespace)) {
            this.theContentHandler.endPrefixMapping(strPrefixOf);
        }
        AttributesImpl attributesImplAtts = this.theStack.atts();
        for (int length = attributesImplAtts.getLength() - 1; length >= 0; length--) {
            String uri = attributesImplAtts.getURI(length);
            String strPrefixOf2 = prefixOf(attributesImplAtts.getQName(length));
            if (foreign(strPrefixOf2, uri)) {
                this.theContentHandler.endPrefixMapping(strPrefixOf2);
            }
        }
        this.theStack = this.theStack.next();
    }

    private void restartablyPop() throws SAXException {
        Element element = this.theStack;
        pop();
        if (!this.restartElements || (element.flags() & 1) == 0) {
            return;
        }
        element.anonymize();
        element.setNext(this.theSaved);
        this.theSaved = element;
    }

    private void push(Element element) throws SAXException {
        String strName = element.name();
        String strLocalName = element.localName();
        String strNamespace = element.namespace();
        String strPrefixOf = prefixOf(strName);
        element.clean();
        if (!this.namespaces) {
            strLocalName = "";
            strNamespace = "";
        }
        if (this.virginStack && strLocalName.equalsIgnoreCase(this.theDoctypeName)) {
            try {
                this.theEntityResolver.resolveEntity(this.theDoctypePublicId, this.theDoctypeSystemId);
            } catch (IOException unused) {
            }
        }
        if (foreign(strPrefixOf, strNamespace)) {
            this.theContentHandler.startPrefixMapping(strPrefixOf, strNamespace);
        }
        AttributesImpl attributesImplAtts = element.atts();
        int length = attributesImplAtts.getLength();
        for (int i = 0; i < length; i++) {
            String uri = attributesImplAtts.getURI(i);
            String strPrefixOf2 = prefixOf(attributesImplAtts.getQName(i));
            if (foreign(strPrefixOf2, uri)) {
                this.theContentHandler.startPrefixMapping(strPrefixOf2, uri);
            }
        }
        this.theContentHandler.startElement(strNamespace, strLocalName, strName, element.atts());
        element.setNext(this.theStack);
        this.theStack = element;
        this.virginStack = false;
        if (!this.CDATAElements || (element.flags() & 2) == 0) {
            return;
        }
        this.theScanner.startCDATA();
    }

    private String prefixOf(String str) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            return str.substring(0, iIndexOf);
        }
        return "";
    }

    private boolean foreign(String str, String str2) {
        return (str.equals("") || str2.equals("") || str2.equals(this.theSchema.getURI())) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0053  */
    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void decl(char[] cArr, int i, int i2) throws SAXException {
        String str;
        String str2;
        String[] strArrSplit = split(new String(cArr, i, i2));
        String str3 = null;
        if (strArrSplit.length <= 0 || !"DOCTYPE".equals(strArrSplit[0])) {
            str = null;
            str2 = null;
        } else {
            if (this.theDoctypeIsPresent) {
                return;
            }
            this.theDoctypeIsPresent = true;
            if (strArrSplit.length > 1) {
                str2 = strArrSplit[1];
                if (strArrSplit.length > 3 && DocumentType.SYSTEM_KEY.equals(strArrSplit[2])) {
                    str = strArrSplit[3];
                } else if (strArrSplit.length <= 3 || !DocumentType.PUBLIC_KEY.equals(strArrSplit[2])) {
                    str = null;
                } else {
                    str3 = strArrSplit[3];
                    if (strArrSplit.length > 4) {
                        str = strArrSplit[4];
                    } else {
                        str = "";
                    }
                }
            } else {
                str = null;
                str2 = null;
            }
        }
        String strTrimquotes = trimquotes(str3);
        String strTrimquotes2 = trimquotes(str);
        if (str2 != null) {
            String strCleanPublicid = cleanPublicid(strTrimquotes);
            this.theLexicalHandler.startDTD(str2, strCleanPublicid, strTrimquotes2);
            this.theLexicalHandler.endDTD();
            this.theDoctypeName = str2;
            this.theDoctypePublicId = strCleanPublicid;
            Scanner scanner = this.theScanner;
            if (scanner instanceof Locator) {
                this.theDoctypeSystemId = ((Locator) scanner).getSystemId();
                try {
                    this.theDoctypeSystemId = new URL(new URL(this.theDoctypeSystemId), strTrimquotes2).toString();
                } catch (Exception unused) {
                }
            }
        }
    }

    private static String trimquotes(String str) {
        int length;
        char cCharAt;
        if (str == null || (length = str.length()) == 0 || (cCharAt = str.charAt(0)) != str.charAt(length - 1)) {
            return str;
        }
        return (cCharAt == '\'' || cCharAt == '\"') ? str.substring(1, str.length() - 1) : str;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0030 A[PHI: r5 r7
  0x0030: PHI (r5v4 boolean) = (r5v1 boolean), (r5v2 boolean), (r5v1 boolean) binds: [B:32:0x0058, B:21:0x003c, B:14:0x002e] A[DONT_GENERATE, DONT_INLINE]
  0x0030: PHI (r7v3 boolean) = (r7v1 boolean), (r7v1 boolean), (r7v4 boolean) binds: [B:32:0x0058, B:21:0x003c, B:14:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    private static String[] split(String str) throws IllegalArgumentException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return new String[0];
        }
        ArrayList arrayList = new ArrayList();
        int length = strTrim.length();
        int i = 0;
        int i2 = 0;
        boolean z = false;
        char c = 0;
        boolean z2 = false;
        while (i < length) {
            char cCharAt = strTrim.charAt(i);
            if (!z && cCharAt == '\'' && c != '\\') {
                z2 = !z2;
                if (i2 < 0) {
                    i2 = i;
                }
            } else if (!z2 && cCharAt == '\"' && c != '\\') {
                z = !z;
                if (i2 < 0) {
                    i2 = i;
                }
            } else if (!z2 && !z) {
                if (Character.isWhitespace(cCharAt)) {
                    if (i2 >= 0) {
                        arrayList.add(strTrim.substring(i2, i));
                    }
                    i2 = -1;
                } else if (i2 < 0 && cCharAt != ' ') {
                    i2 = i;
                }
            }
            i++;
            c = cCharAt;
        }
        arrayList.add(strTrim.substring(i2, i));
        return (String[]) arrayList.toArray(new String[0]);
    }

    private String cleanPublicid(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        boolean z = true;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (legal.indexOf(cCharAt) != -1) {
                stringBuffer.append(cCharAt);
                z = false;
            } else if (!z) {
                stringBuffer.append(' ');
                z = true;
            }
        }
        return stringBuffer.toString().trim();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void gi(char[] cArr, int i, int i2) throws SAXException {
        String strMakeName;
        if (this.theNewElement == null && (strMakeName = makeName(cArr, i, i2)) != null) {
            ElementType elementType = this.theSchema.getElementType(strMakeName);
            if (elementType == null) {
                if (this.ignoreBogons) {
                    return;
                }
                this.theSchema.elementType(strMakeName, this.bogonsEmpty ? 0 : -1, this.rootBogons ? -1 : Integer.MAX_VALUE, 0);
                if (!this.rootBogons) {
                    Schema schema = this.theSchema;
                    schema.parent(strMakeName, schema.rootElementType().name());
                }
                elementType = this.theSchema.getElementType(strMakeName);
            }
            this.theNewElement = new Element(elementType, this.defaultAttributes);
        }
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void cdsect(char[] cArr, int i, int i2) throws SAXException {
        this.theLexicalHandler.startCDATA();
        pcdata(cArr, i, i2);
        this.theLexicalHandler.endCDATA();
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pcdata(char[] cArr, int i, int i2) throws SAXException {
        if (i2 == 0) {
            return;
        }
        boolean z = true;
        for (int i3 = 0; i3 < i2; i3++) {
            if (!Character.isWhitespace(cArr[i + i3])) {
                z = false;
            }
        }
        if (z && !this.theStack.canContain(this.thePCDATA)) {
            if (this.ignorableWhitespace) {
                this.theContentHandler.ignorableWhitespace(cArr, i, i2);
            }
        } else {
            rectify(this.thePCDATA);
            this.theContentHandler.characters(cArr, i, i2);
        }
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pitarget(char[] cArr, int i, int i2) throws SAXException {
        if (this.theNewElement != null) {
            return;
        }
        this.thePITarget = makeName(cArr, i, i2).replace(CoreConstants.COLON_CHAR, '_');
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void pi(char[] cArr, int i, int i2) throws SAXException {
        String str;
        if (this.theNewElement != null || (str = this.thePITarget) == null || StringLookupFactory.KEY_XML.equalsIgnoreCase(str)) {
            return;
        }
        if (i2 > 0 && cArr[i2 - 1] == '?') {
            i2--;
        }
        this.theContentHandler.processingInstruction(this.thePITarget, new String(cArr, i, i2));
        this.thePITarget = null;
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void stagc(char[] cArr, int i, int i2) throws SAXException {
        Element element = this.theNewElement;
        if (element == null) {
            return;
        }
        rectify(element);
        if (this.theStack.model() == 0) {
            etag_basic(cArr, i, i2);
        }
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void stage(char[] cArr, int i, int i2) throws SAXException {
        Element element = this.theNewElement;
        if (element == null) {
            return;
        }
        rectify(element);
        etag_basic(cArr, i, i2);
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public void cmnt(char[] cArr, int i, int i2) throws SAXException {
        this.theLexicalHandler.comment(cArr, i, i2);
    }

    private void rectify(Element element) throws SAXException {
        Element next;
        ElementType elementTypeParent;
        while (true) {
            next = this.theStack;
            while (next != null && !next.canContain(element)) {
                next = next.next();
            }
            if (next != null || (elementTypeParent = element.parent()) == null) {
                break;
            }
            Element element2 = new Element(elementTypeParent, this.defaultAttributes);
            element2.setNext(element);
            element = element2;
        }
        if (next == null) {
            return;
        }
        while (true) {
            Element element3 = this.theStack;
            if (element3 == next || element3 == null || element3.next() == null || this.theStack.next().next() == null) {
                break;
            } else {
                restartablyPop();
            }
        }
        while (element != null) {
            Element next2 = element.next();
            if (!element.name().equals("<pcdata>")) {
                push(element);
            }
            restart(next2);
            element = next2;
        }
        this.theNewElement = null;
    }

    @Override // org.ccil.cowan.tagsoup.ScanHandler
    public int getEntity() {
        return this.theEntity;
    }

    private String makeName(char[] cArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer(i2 + 2);
        boolean z = false;
        boolean z2 = true;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            char c = cArr[i];
            if (Character.isLetter(c) || c == '_') {
                stringBuffer.append(c);
            } else {
                if (Character.isDigit(c) || c == '-' || c == '.') {
                    if (z2) {
                        stringBuffer.append('_');
                    }
                    stringBuffer.append(c);
                } else if (c == ':' && !z) {
                    if (z2) {
                        stringBuffer.append('_');
                    }
                    stringBuffer.append(this.translateColons ? '_' : c);
                    z2 = true;
                    z = true;
                }
                i++;
                i2 = i3;
            }
            z2 = false;
            i++;
            i2 = i3;
        }
        int length = stringBuffer.length();
        if (length == 0 || stringBuffer.charAt(length - 1) == ':') {
            stringBuffer.append('_');
        }
        return stringBuffer.toString().intern();
    }
}
