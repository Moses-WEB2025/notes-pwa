package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
public class Parser implements Cloneable {
    public static final String NamespaceHtml = "http://www.w3.org/1999/xhtml";
    public static final String NamespaceMathml = "http://www.w3.org/1998/Math/MathML";
    public static final String NamespaceSvg = "http://www.w3.org/2000/svg";
    public static final String NamespaceXml = "http://www.w3.org/XML/1998/namespace";
    private ParseErrorList errors;
    private final ReentrantLock lock;
    private ParseSettings settings;
    private TagSet tagSet;
    private boolean trackPosition;
    private final TreeBuilder treeBuilder;

    public Parser(TreeBuilder treeBuilder) {
        this.trackPosition = false;
        this.lock = new ReentrantLock();
        this.treeBuilder = treeBuilder;
        this.settings = treeBuilder.defaultSettings();
        this.errors = ParseErrorList.noTracking();
    }

    public Parser newInstance() {
        return new Parser(this);
    }

    public Parser clone() {
        return new Parser(this);
    }

    private Parser(Parser parser) {
        this.trackPosition = false;
        this.lock = new ReentrantLock();
        this.treeBuilder = parser.treeBuilder.newInstance();
        this.errors = new ParseErrorList(parser.errors);
        this.settings = new ParseSettings(parser.settings);
        this.trackPosition = parser.trackPosition;
    }

    public Document parseInput(String str, String str2) {
        return parseInput(new StringReader(str), str2);
    }

    public Document parseInput(Reader reader, String str) {
        try {
            this.lock.lock();
            return this.treeBuilder.parse(reader, str, this);
        } finally {
            this.lock.unlock();
        }
    }

    public List<Node> parseFragmentInput(String str, Element element, String str2) {
        return parseFragmentInput(new StringReader(str), element, str2);
    }

    public List<Node> parseFragmentInput(Reader reader, Element element, String str) {
        try {
            this.lock.lock();
            return this.treeBuilder.parseFragment(reader, element, str, this);
        } finally {
            this.lock.unlock();
        }
    }

    public TreeBuilder getTreeBuilder() {
        return this.treeBuilder;
    }

    public boolean isTrackErrors() {
        return this.errors.getMaxSize() > 0;
    }

    public Parser setTrackErrors(int i) {
        this.errors = i > 0 ? ParseErrorList.tracking(i) : ParseErrorList.noTracking();
        return this;
    }

    public ParseErrorList getErrors() {
        return this.errors;
    }

    public boolean isTrackPosition() {
        return this.trackPosition;
    }

    public Parser setTrackPosition(boolean z) {
        this.trackPosition = z;
        return this;
    }

    public Parser settings(ParseSettings parseSettings) {
        this.settings = parseSettings;
        return this;
    }

    public ParseSettings settings() {
        return this.settings;
    }

    public Parser tagSet(TagSet tagSet) {
        Validate.notNull(tagSet);
        this.tagSet = new TagSet(tagSet);
        return this;
    }

    public TagSet tagSet() {
        if (this.tagSet == null) {
            this.tagSet = this.treeBuilder.defaultTagSet();
        }
        return this.tagSet;
    }

    public String defaultNamespace() {
        return getTreeBuilder().defaultNamespace();
    }

    public static Document parse(String str, String str2) {
        HtmlTreeBuilder htmlTreeBuilder = new HtmlTreeBuilder();
        return htmlTreeBuilder.parse(new StringReader(str), str2, new Parser(htmlTreeBuilder));
    }

    public static List<Node> parseFragment(String str, Element element, String str2) {
        HtmlTreeBuilder htmlTreeBuilder = new HtmlTreeBuilder();
        return htmlTreeBuilder.parseFragment(new StringReader(str), element, str2, new Parser(htmlTreeBuilder));
    }

    public static List<Node> parseFragment(String str, Element element, String str2, ParseErrorList parseErrorList) {
        HtmlTreeBuilder htmlTreeBuilder = new HtmlTreeBuilder();
        Parser parser = new Parser(htmlTreeBuilder);
        parser.errors = parseErrorList;
        return htmlTreeBuilder.parseFragment(new StringReader(str), element, str2, parser);
    }

    public static List<Node> parseXmlFragment(String str, String str2) {
        XmlTreeBuilder xmlTreeBuilder = new XmlTreeBuilder();
        return xmlTreeBuilder.parseFragment(new StringReader(str), null, str2, new Parser(xmlTreeBuilder));
    }

    public static Document parseBodyFragment(String str, String str2) {
        Document documentCreateShell = Document.createShell(str2);
        Element elementBody = documentCreateShell.body();
        List<Node> fragment = parseFragment(str, elementBody, str2);
        Node[] nodeArr = (Node[]) fragment.toArray(new Node[0]);
        for (int length = nodeArr.length - 1; length > 0; length--) {
            nodeArr[length].remove();
        }
        for (Node node : nodeArr) {
            elementBody.appendChild(node);
        }
        return documentCreateShell;
    }

    public static String unescapeEntities(String str, boolean z) {
        Parser parserHtmlParser = htmlParser();
        parserHtmlParser.treeBuilder.initialiseParse(new StringReader(str), "", parserHtmlParser);
        return new Tokeniser(parserHtmlParser.treeBuilder).unescapeEntities(z);
    }

    public static Parser htmlParser() {
        return new Parser(new HtmlTreeBuilder());
    }

    public static Parser xmlParser() {
        return new Parser(new XmlTreeBuilder());
    }
}
