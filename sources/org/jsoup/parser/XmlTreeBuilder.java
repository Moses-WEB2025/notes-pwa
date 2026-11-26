package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Elements;

/* JADX INFO: loaded from: classes6.dex */
public class XmlTreeBuilder extends TreeBuilder {
    static final String XmlnsKey = "xmlns";
    static final String XmlnsPrefix = "xmlns:";
    private static final int maxQueueDepth = 256;
    private final ArrayDeque<HashMap<String, String>> namespacesStack = new ArrayDeque<>();

    @Override // org.jsoup.parser.TreeBuilder
    ParseSettings defaultSettings() {
        return ParseSettings.preserveCase;
    }

    @Override // org.jsoup.parser.TreeBuilder
    protected void initialiseParse(Reader reader, String str, Parser parser) {
        super.initialiseParse(reader, str, parser);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml).prettyPrint(false);
        this.namespacesStack.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put(StringLookupFactory.KEY_XML, Parser.NamespaceXml);
        map.put("", Parser.NamespaceXml);
        this.namespacesStack.push(map);
    }

    @Override // org.jsoup.parser.TreeBuilder
    void initialiseParseFragment(Element element) {
        super.initialiseParseFragment(element);
        if (element == null) {
            return;
        }
        TokeniserState tokeniserStateTextState = element.tag().textState();
        if (tokeniserStateTextState != null) {
            this.tokeniser.transition(tokeniserStateTextState);
        }
        Elements elementsParents = element.parents();
        elementsParents.add(0, element);
        for (int size = elementsParents.size() - 1; size >= 0; size--) {
            Element element2 = (Element) elementsParents.get(size);
            HashMap<String, String> map = new HashMap<>(this.namespacesStack.peek());
            this.namespacesStack.push(map);
            if (element2.attributesSize() > 0) {
                processNamespaces(element2.attributes(), map);
            }
        }
    }

    Document parse(Reader reader, String str) {
        return parse(reader, str, new Parser(this));
    }

    Document parse(String str, String str2) {
        return parse(new StringReader(str), str2, new Parser(this));
    }

    @Override // org.jsoup.parser.TreeBuilder
    List<Node> completeParseFragment() {
        return this.doc.childNodes();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jsoup.parser.TreeBuilder
    public XmlTreeBuilder newInstance() {
        return new XmlTreeBuilder();
    }

    @Override // org.jsoup.parser.TreeBuilder
    public String defaultNamespace() {
        return Parser.NamespaceXml;
    }

    @Override // org.jsoup.parser.TreeBuilder
    TagSet defaultTagSet() {
        return new TagSet();
    }

    @Override // org.jsoup.parser.TreeBuilder
    protected boolean process(Token token) {
        this.currentToken = token;
        switch (AnonymousClass1.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
            case 1:
                insertElementFor(token.asStartTag());
                break;
            case 2:
                popStackToClose(token.asEndTag());
                break;
            case 3:
                insertCommentFor(token.asComment());
                break;
            case 4:
                insertCharacterFor(token.asCharacter());
                break;
            case 5:
                insertDoctypeFor(token.asDoctype());
                break;
            case 6:
                insertXmlDeclarationFor(token.asXmlDecl());
                break;
            case 7:
                break;
            default:
                Validate.fail("Unexpected token type: " + token.type);
                break;
        }
        return true;
    }

    /* JADX INFO: renamed from: org.jsoup.parser.XmlTreeBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$parser$Token$TokenType;

        static {
            int[] iArr = new int[Token.TokenType.values().length];
            $SwitchMap$org$jsoup$parser$Token$TokenType = iArr;
            try {
                iArr[Token.TokenType.StartTag.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EndTag.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Comment.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Character.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Doctype.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.XmlDecl.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EOF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    void insertElementFor(Token.StartTag startTag) {
        HashMap<String, String> map = new HashMap<>(this.namespacesStack.peek());
        this.namespacesStack.push(map);
        Attributes attributes = startTag.attributes;
        if (attributes != null) {
            attributes.deduplicate(this.settings);
            processNamespaces(attributes, map);
            applyNamespacesToAttributes(attributes, map);
        }
        String strValue = startTag.tagName.value();
        Tag tagTagFor = tagFor(strValue, startTag.normalName, resolveNamespace(strValue, map), this.settings);
        Element element = new Element(tagTagFor, null, this.settings.normalizeAttributes(attributes));
        currentElement().appendChild(element);
        push(element);
        if (startTag.isSelfClosing()) {
            tagTagFor.setSeenSelfClose();
            pop();
        } else {
            if (tagTagFor.isEmpty()) {
                pop();
                return;
            }
            TokeniserState tokeniserStateTextState = tagTagFor.textState();
            if (tokeniserStateTextState != null) {
                this.tokeniser.transition(tokeniserStateTextState);
            }
        }
    }

    private static void processNamespaces(Attributes attributes, HashMap<String, String> map) {
        for (Attribute attribute : attributes) {
            String key = attribute.getKey();
            String value = attribute.getValue();
            if (key.equals(XmlnsKey)) {
                map.put("", value);
            } else if (key.startsWith(XmlnsPrefix)) {
                map.put(key.substring(XmlnsPrefix.length()), value);
            }
        }
    }

    private static void applyNamespacesToAttributes(Attributes attributes, HashMap<String, String> map) {
        String str;
        HashMap map2 = new HashMap();
        Iterator<Attribute> it = attributes.iterator();
        while (it.hasNext()) {
            String strPrefix = it.next().prefix();
            if (!strPrefix.isEmpty() && !strPrefix.equals(XmlnsKey) && (str = map.get(strPrefix)) != null) {
                map2.put(SharedConstants.XmlnsAttr + strPrefix, str);
            }
        }
        for (Map.Entry entry : map2.entrySet()) {
            attributes.userData((String) entry.getKey(), entry.getValue());
        }
    }

    private static String resolveNamespace(String str, HashMap<String, String> map) {
        String str2 = map.get("");
        int iIndexOf = str.indexOf(58);
        if (iIndexOf > 0) {
            String strSubstring = str.substring(0, iIndexOf);
            if (map.containsKey(strSubstring)) {
                return map.get(strSubstring);
            }
        }
        return str2;
    }

    void insertLeafNode(LeafNode leafNode) {
        currentElement().appendChild(leafNode);
        onNodeInserted(leafNode);
    }

    void insertCommentFor(Token.Comment comment) {
        insertLeafNode(new Comment(comment.getData()));
    }

    void insertCharacterFor(Token.Character character) {
        LeafNode dataNode;
        String data = character.getData();
        if (character.isCData()) {
            dataNode = new CDataNode(data);
        } else {
            dataNode = currentElement().tag().is(Tag.Data) ? new DataNode(data) : new TextNode(data);
        }
        insertLeafNode(dataNode);
    }

    void insertDoctypeFor(Token.Doctype doctype) {
        DocumentType documentType = new DocumentType(this.settings.normalizeTag(doctype.getName()), doctype.getPublicIdentifier(), doctype.getSystemIdentifier());
        documentType.setPubSysKey(doctype.getPubSysKey());
        insertLeafNode(documentType);
    }

    void insertXmlDeclarationFor(Token.XmlDecl xmlDecl) {
        XmlDeclaration xmlDeclaration = new XmlDeclaration(xmlDecl.name(), xmlDecl.isDeclaration);
        if (xmlDecl.attributes != null) {
            xmlDeclaration.attributes().addAll(xmlDecl.attributes);
        }
        insertLeafNode(xmlDeclaration);
    }

    @Override // org.jsoup.parser.TreeBuilder
    Element pop() {
        this.namespacesStack.pop();
        return super.pop();
    }

    protected void popStackToClose(Token.EndTag endTag) {
        Element element;
        String strNormalizeTag = this.settings.normalizeTag(endTag.name());
        int size = this.stack.size();
        int i = size + (-1) >= 256 ? size - 257 : 0;
        int size2 = this.stack.size() - 1;
        while (true) {
            if (size2 < i) {
                element = null;
                break;
            }
            element = this.stack.get(size2);
            if (element.nodeName().equals(strNormalizeTag)) {
                break;
            } else {
                size2--;
            }
        }
        if (element == null) {
            return;
        }
        for (int size3 = this.stack.size() - 1; size3 >= 0 && pop() != element; size3--) {
        }
    }
}
