package org.jsoup.parser;

import com.miui.richeditor.schema.HtmlParser;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Range;
import org.jsoup.select.NodeVisitor;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes6.dex */
public abstract class TreeBuilder {
    String baseUri;
    Token currentToken;
    Document doc;
    private final Token.EndTag end = new Token.EndTag(this);
    NodeVisitor nodeListener;
    protected Parser parser;
    CharacterReader reader;
    ParseSettings settings;
    ArrayList<Element> stack;
    private Token.StartTag start;
    TagSet tagSet;
    Tokeniser tokeniser;
    boolean trackSourceRange;

    abstract List<Node> completeParseFragment();

    abstract ParseSettings defaultSettings();

    void initialiseParseFragment(Element element) {
    }

    abstract TreeBuilder newInstance();

    abstract boolean process(Token token);

    TreeBuilder() {
    }

    void initialiseParse(Reader reader, String str, Parser parser) {
        Validate.notNullParam(reader, HtmlParser.InputElement.TAG);
        Validate.notNullParam(str, "baseUri");
        Validate.notNull(parser);
        Document document = new Document(parser.defaultNamespace(), str);
        this.doc = document;
        document.parser(parser);
        this.parser = parser;
        this.settings = parser.settings();
        this.reader = new CharacterReader(reader);
        this.trackSourceRange = parser.isTrackPosition();
        this.reader.trackNewlines(parser.isTrackErrors() || this.trackSourceRange);
        if (parser.isTrackErrors()) {
            parser.getErrors().clear();
        }
        this.tokeniser = new Tokeniser(this);
        this.stack = new ArrayList<>(32);
        this.tagSet = parser.tagSet();
        Token.StartTag startTag = new Token.StartTag(this);
        this.start = startTag;
        this.currentToken = startTag;
        this.baseUri = str;
        onNodeInserted(this.doc);
    }

    void completeParse() {
        CharacterReader characterReader = this.reader;
        if (characterReader == null) {
            return;
        }
        characterReader.close();
        this.reader = null;
        this.tokeniser = null;
        this.stack = null;
    }

    Document parse(Reader reader, String str, Parser parser) {
        initialiseParse(reader, str, parser);
        runParser();
        return this.doc;
    }

    List<Node> parseFragment(Reader reader, Element element, String str, Parser parser) {
        initialiseParse(reader, str, parser);
        initialiseParseFragment(element);
        runParser();
        return completeParseFragment();
    }

    void nodeListener(NodeVisitor nodeVisitor) {
        this.nodeListener = nodeVisitor;
    }

    void runParser() {
        while (stepParser()) {
        }
        completeParse();
    }

    boolean stepParser() {
        if (this.currentToken.type == Token.TokenType.EOF) {
            ArrayList<Element> arrayList = this.stack;
            if (arrayList == null) {
                return false;
            }
            if (arrayList.isEmpty()) {
                onNodeClosed(this.doc);
                this.stack = null;
                return true;
            }
            pop();
            return true;
        }
        Token token = this.tokeniser.read();
        this.currentToken = token;
        process(token);
        token.reset();
        return true;
    }

    boolean processStartTag(String str) {
        Token.StartTag startTag = this.start;
        if (this.currentToken == startTag) {
            return process(new Token.StartTag(this).name(str));
        }
        return process(startTag.reset().name(str));
    }

    boolean processStartTag(String str, Attributes attributes) {
        Token.StartTag startTag = this.start;
        if (this.currentToken == startTag) {
            return process(new Token.StartTag(this).nameAttr(str, attributes));
        }
        startTag.reset();
        startTag.nameAttr(str, attributes);
        return process(startTag);
    }

    boolean processEndTag(String str) {
        Token token = this.currentToken;
        Token.EndTag endTag = this.end;
        if (token == endTag) {
            return process(new Token.EndTag(this).name(str));
        }
        return process(endTag.reset().name(str));
    }

    Element pop() {
        Element elementRemove = this.stack.remove(this.stack.size() - 1);
        onNodeClosed(elementRemove);
        return elementRemove;
    }

    final void push(Element element) {
        this.stack.add(element);
        onNodeInserted(element);
    }

    Element currentElement() {
        int size = this.stack.size();
        return size > 0 ? this.stack.get(size - 1) : this.doc;
    }

    boolean currentElementIs(String str) {
        Element elementCurrentElement;
        return this.stack.size() != 0 && (elementCurrentElement = currentElement()) != null && elementCurrentElement.normalName().equals(str) && elementCurrentElement.tag().namespace().equals(Parser.NamespaceHtml);
    }

    boolean currentElementIs(String str, String str2) {
        Element elementCurrentElement;
        return this.stack.size() != 0 && (elementCurrentElement = currentElement()) != null && elementCurrentElement.normalName().equals(str) && elementCurrentElement.tag().namespace().equals(str2);
    }

    void error(String str) {
        error(str, null);
    }

    void error(String str, Object... objArr) {
        ParseErrorList errors = this.parser.getErrors();
        if (errors.canAddError()) {
            errors.add(new ParseError(this.reader, str, objArr));
        }
    }

    Tag tagFor(String str, String str2, String str3, ParseSettings parseSettings) {
        return this.tagSet.valueOf(str, str2, str3, parseSettings.preserveTagCase());
    }

    Tag tagFor(Token.Tag tag) {
        return this.tagSet.valueOf(tag.name(), tag.normalName, defaultNamespace(), this.settings.preserveTagCase());
    }

    String defaultNamespace() {
        return Parser.NamespaceHtml;
    }

    TagSet defaultTagSet() {
        return TagSet.Html();
    }

    void onNodeInserted(Node node) {
        trackNodePosition(node, true);
        NodeVisitor nodeVisitor = this.nodeListener;
        if (nodeVisitor != null) {
            nodeVisitor.head(node, this.stack.size());
        }
    }

    void onNodeClosed(Node node) {
        trackNodePosition(node, false);
        NodeVisitor nodeVisitor = this.nodeListener;
        if (nodeVisitor != null) {
            nodeVisitor.tail(node, this.stack.size());
        }
    }

    void trackNodePosition(Node node, boolean z) {
        if (this.trackSourceRange) {
            Token token = this.currentToken;
            int iStartPos = token.startPos();
            int iEndPos = token.endPos();
            if (node instanceof Element) {
                Element element = (Element) node;
                if (token.isEOF()) {
                    if (element.endSourceRange().isTracked()) {
                        return;
                    } else {
                        iStartPos = this.reader.pos();
                    }
                } else if (!z ? !(element.tag().isEmpty() || element.tag().isSelfClosing() || (token.isEndTag() && element.normalName().equals(token.asEndTag().normalName))) : !(token.isStartTag() && element.normalName().equals(token.asStartTag().normalName))) {
                }
                iEndPos = iStartPos;
            }
            node.attributes().userData(z ? SharedConstants.RangeKey : SharedConstants.EndRangeKey, new Range(new Range.Position(iStartPos, this.reader.lineNumber(iStartPos), this.reader.columnNumber(iStartPos)), new Range.Position(iEndPos, this.reader.lineNumber(iEndPos), this.reader.columnNumber(iEndPos))));
        }
    }
}
