package org.jsoup.nodes;

import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Evaluator;

/* JADX INFO: loaded from: classes6.dex */
public class Document extends Element {
    private static final Evaluator titleEval = new Evaluator.Tag("title");
    private Connection connection;
    private final String location;
    private OutputSettings outputSettings;
    private Parser parser;
    private QuirksMode quirksMode;

    public enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks
    }

    public Document(String str, String str2) {
        this(str, str2, Parser.htmlParser());
    }

    private Document(String str, String str2, Parser parser) {
        super(new Tag("#root", str), str2);
        this.outputSettings = new OutputSettings();
        this.quirksMode = QuirksMode.noQuirks;
        this.location = str2;
        this.parser = parser;
    }

    public Document(String str) {
        this(Parser.NamespaceHtml, str);
    }

    public static Document createShell(String str) {
        Validate.notNull(str);
        Document document = new Document(str);
        Element elementAppendElement = document.appendElement("html");
        elementAppendElement.appendElement("head");
        elementAppendElement.appendElement("body");
        return document;
    }

    public String location() {
        return this.location;
    }

    public Connection connection() {
        Connection connection = this.connection;
        return connection == null ? Jsoup.newSession() : connection;
    }

    public DocumentType documentType() {
        for (Node node : this.childNodes) {
            if (node instanceof DocumentType) {
                return (DocumentType) node;
            }
            if (!(node instanceof LeafNode)) {
                return null;
            }
        }
        return null;
    }

    private Element htmlEl() {
        for (Element elementFirstElementChild = firstElementChild(); elementFirstElementChild != null; elementFirstElementChild = elementFirstElementChild.nextElementSibling()) {
            if (elementFirstElementChild.nameIs("html")) {
                return elementFirstElementChild;
            }
        }
        return appendElement("html");
    }

    public Element head() {
        Element elementHtmlEl = htmlEl();
        for (Element elementFirstElementChild = elementHtmlEl.firstElementChild(); elementFirstElementChild != null; elementFirstElementChild = elementFirstElementChild.nextElementSibling()) {
            if (elementFirstElementChild.nameIs("head")) {
                return elementFirstElementChild;
            }
        }
        return elementHtmlEl.prependElement("head");
    }

    public Element body() {
        Element elementHtmlEl = htmlEl();
        for (Element elementFirstElementChild = elementHtmlEl.firstElementChild(); elementFirstElementChild != null; elementFirstElementChild = elementFirstElementChild.nextElementSibling()) {
            if (elementFirstElementChild.nameIs("body") || elementFirstElementChild.nameIs("frameset")) {
                return elementFirstElementChild;
            }
        }
        return elementHtmlEl.appendElement("body");
    }

    public List<FormElement> forms() {
        return select("form").forms();
    }

    public FormElement expectForm(String str) {
        for (Element element : select(str)) {
            if (element instanceof FormElement) {
                return (FormElement) element;
            }
        }
        Validate.fail("No form elements matched the query '%s' in the document.", str);
        return null;
    }

    public String title() {
        Element elementSelectFirst = head().selectFirst(titleEval);
        return elementSelectFirst != null ? StringUtil.normaliseWhitespace(elementSelectFirst.text()).trim() : "";
    }

    public void title(String str) {
        Validate.notNull(str);
        Element elementSelectFirst = head().selectFirst(titleEval);
        if (elementSelectFirst == null) {
            elementSelectFirst = head().appendElement("title");
        }
        elementSelectFirst.text(str);
    }

    public Element createElement(String str) {
        return new Element(this.parser.tagSet().valueOf(str, this.parser.defaultNamespace(), ParseSettings.preserveCase), baseUri());
    }

    @Override // org.jsoup.nodes.Node
    public String outerHtml() {
        return super.html();
    }

    @Override // org.jsoup.nodes.Element
    public Element text(String str) {
        body().text(str);
        return this;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public String nodeName() {
        return "#document";
    }

    public void charset(Charset charset) {
        this.outputSettings.charset(charset);
        ensureMetaCharsetElement();
    }

    public Charset charset() {
        return this.outputSettings.charset();
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Document mo4587clone() {
        Document document = (Document) super.mo4587clone();
        if (this.attributes != null) {
            document.attributes = this.attributes.clone();
        }
        document.outputSettings = this.outputSettings.clone();
        return document;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public Document shallowClone() {
        Document document = new Document(tag().namespace(), baseUri(), this.parser);
        if (this.attributes != null) {
            document.attributes = this.attributes.clone();
        }
        document.outputSettings = this.outputSettings.clone();
        return document;
    }

    private void ensureMetaCharsetElement() {
        OutputSettings.Syntax syntax = outputSettings().syntax();
        if (syntax == OutputSettings.Syntax.html) {
            Element elementSelectFirst = selectFirst("meta[charset]");
            if (elementSelectFirst != null) {
                elementSelectFirst.attr("charset", charset().displayName());
            } else {
                head().appendElement("meta").attr("charset", charset().displayName());
            }
            select("meta[name=charset]").remove();
            return;
        }
        if (syntax == OutputSettings.Syntax.xml) {
            XmlDeclaration xmlDeclarationEnsureXmlDecl = ensureXmlDecl();
            xmlDeclarationEnsureXmlDecl.attr("version", "1.0");
            xmlDeclarationEnsureXmlDecl.attr(XMLWriter.ENCODING, charset().displayName());
        }
    }

    private XmlDeclaration ensureXmlDecl() {
        Node nodeFirstChild = firstChild();
        if (nodeFirstChild instanceof XmlDeclaration) {
            XmlDeclaration xmlDeclaration = (XmlDeclaration) nodeFirstChild;
            if (xmlDeclaration.name().equals(StringLookupFactory.KEY_XML)) {
                return xmlDeclaration;
            }
        }
        XmlDeclaration xmlDeclaration2 = new XmlDeclaration(StringLookupFactory.KEY_XML, false);
        prependChild(xmlDeclaration2);
        return xmlDeclaration2;
    }

    public static class OutputSettings implements Cloneable {
        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private Charset charset = DataUtil.UTF_8;
        private boolean prettyPrint = true;
        private boolean outline = false;
        private int indentAmount = 1;
        private int maxPaddingWidth = 30;
        private Syntax syntax = Syntax.html;

        public enum Syntax {
            html,
            xml
        }

        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        public Charset charset() {
            return this.charset;
        }

        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public OutputSettings charset(String str) {
            charset(Charset.forName(str));
            return this;
        }

        public Syntax syntax() {
            return this.syntax;
        }

        public OutputSettings syntax(Syntax syntax) {
            this.syntax = syntax;
            if (syntax == Syntax.xml) {
                escapeMode(Entities.EscapeMode.xhtml);
            }
            return this;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }

        public OutputSettings prettyPrint(boolean z) {
            this.prettyPrint = z;
            return this;
        }

        public boolean outline() {
            return this.outline;
        }

        public OutputSettings outline(boolean z) {
            this.outline = z;
            return this;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        public OutputSettings indentAmount(int i) {
            Validate.isTrue(i >= 0);
            this.indentAmount = i;
            return this;
        }

        public int maxPaddingWidth() {
            return this.maxPaddingWidth;
        }

        public OutputSettings maxPaddingWidth(int i) {
            Validate.isTrue(i >= -1);
            this.maxPaddingWidth = i;
            return this;
        }

        public OutputSettings clone() {
            try {
                OutputSettings outputSettings = (OutputSettings) super.clone();
                outputSettings.charset(this.charset.name());
                outputSettings.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
                return outputSettings;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public Document outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    public Document quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }

    public Parser parser() {
        return this.parser;
    }

    public Document parser(Parser parser) {
        this.parser = parser;
        return this;
    }

    public Document connection(Connection connection) {
        Validate.notNull(connection);
        this.connection = connection;
        return this;
    }
}
