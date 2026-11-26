package org.jsoup.nodes;

import com.miui.common.tool.TextProcessUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.text.Typography;
import miuix.appcompat.view.menu.HyperMenuUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Nodes;
import org.jsoup.select.Selector;

/* JADX INFO: loaded from: classes6.dex */
public class Element extends Node implements Iterable<Element> {
    private static final String childElsKey = "jsoup.childEls";
    private static final String childElsMod = "jsoup.childElsMod";
    Attributes attributes;
    NodeList childNodes;
    Tag tag;
    private static final List<Element> EmptyChildren = Collections.emptyList();
    private static final NodeList EmptyNodeList = new NodeList(0);
    private static final Pattern ClassSplit = Pattern.compile(TextProcessUtils.ONE_OR_MULTI_SPACE_REG);
    private static final String BaseUriKey = Attributes.internalKey("baseUri");

    @Override // org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ Node forEachNode(Consumer consumer) {
        return forEachNode((Consumer<? super Node>) consumer);
    }

    public Element(String str, String str2) {
        this(Tag.valueOf(str, str2, ParseSettings.preserveCase), (String) null);
    }

    public Element(String str) {
        this(str, Parser.NamespaceHtml);
    }

    public Element(Tag tag, String str, Attributes attributes) {
        Validate.notNull(tag);
        this.childNodes = EmptyNodeList;
        this.attributes = attributes;
        this.tag = tag;
        if (str != null) {
            setBaseUri(str);
        }
    }

    public Element(Tag tag, String str) {
        this(tag, str, null);
    }

    protected boolean hasChildNodes() {
        return this.childNodes != EmptyNodeList;
    }

    @Override // org.jsoup.nodes.Node
    protected List<Node> ensureChildNodes() {
        if (this.childNodes == EmptyNodeList) {
            this.childNodes = new NodeList(4);
        }
        return this.childNodes;
    }

    @Override // org.jsoup.nodes.Node
    protected boolean hasAttributes() {
        return this.attributes != null;
    }

    @Override // org.jsoup.nodes.Node
    public Attributes attributes() {
        if (this.attributes == null) {
            this.attributes = new Attributes();
        }
        return this.attributes;
    }

    @Override // org.jsoup.nodes.Node
    public String baseUri() {
        return searchUpForAttribute(this, BaseUriKey);
    }

    private static String searchUpForAttribute(Element element, String str) {
        while (element != null) {
            Attributes attributes = element.attributes;
            if (attributes != null && attributes.hasKey(str)) {
                return element.attributes.get(str);
            }
            element = element.parent();
        }
        return "";
    }

    @Override // org.jsoup.nodes.Node
    protected void doSetBaseUri(String str) {
        attributes().put(BaseUriKey, str);
    }

    @Override // org.jsoup.nodes.Node
    public int childNodeSize() {
        return this.childNodes.size();
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return this.tag.getName();
    }

    public String tagName() {
        return this.tag.getName();
    }

    @Override // org.jsoup.nodes.Node
    public String normalName() {
        return this.tag.normalName();
    }

    public boolean elementIs(String str, String str2) {
        return this.tag.normalName().equals(str) && this.tag.namespace().equals(str2);
    }

    public Element tagName(String str) {
        return tagName(str, this.tag.namespace());
    }

    public Element tagName(String str, String str2) {
        Validate.notEmptyParam(str, "tagName");
        Validate.notEmptyParam(str2, "namespace");
        Parser rVar = NodeUtils.parser(this);
        this.tag = rVar.tagSet().valueOf(str, str2, rVar.settings());
        return this;
    }

    public Tag tag() {
        return this.tag;
    }

    public Element tag(Tag tag) {
        Validate.notNull(tag);
        this.tag = tag;
        return this;
    }

    public boolean isBlock() {
        return this.tag.isBlock();
    }

    public String id() {
        Attributes attributes = this.attributes;
        return attributes != null ? attributes.getIgnoreCase("id") : "";
    }

    public Element id(String str) {
        Validate.notNull(str);
        attr("id", str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element attr(String str, String str2) {
        super.attr(str, str2);
        return this;
    }

    public Element attr(String str, boolean z) {
        attributes().put(str, z);
        return this;
    }

    public Attribute attribute(String str) {
        if (hasAttributes()) {
            return attributes().attribute(str);
        }
        return null;
    }

    public Map<String, String> dataset() {
        return attributes().dataset();
    }

    @Override // org.jsoup.nodes.Node
    public final Element parent() {
        return this.parentNode;
    }

    public Elements parents() {
        Elements elements = new Elements();
        for (Element elementParent = parent(); elementParent != null && !elementParent.nameIs("#root"); elementParent = elementParent.parent()) {
            elements.add(elementParent);
        }
        return elements;
    }

    public Element child(int i) {
        return childElementsList().get(i);
    }

    public int childrenSize() {
        return childElementsList().size();
    }

    public Elements children() {
        return new Elements(childElementsList());
    }

    List<Element> childElementsList() {
        if (childNodeSize() == 0) {
            return EmptyChildren;
        }
        List<Element> listCachedChildren = cachedChildren();
        if (listCachedChildren != null) {
            return listCachedChildren;
        }
        List<Element> listFilterNodes = filterNodes(Element.class);
        stashChildren(listFilterNodes);
        return listFilterNodes;
    }

    private List<Element> cachedChildren() {
        List<Element> list;
        Integer num;
        Map<String, Object> mapUserData = attributes().userData();
        WeakReference weakReference = (WeakReference) mapUserData.get(childElsKey);
        if (weakReference == null || (list = (List) weakReference.get()) == null || (num = (Integer) mapUserData.get(childElsMod)) == null || num.intValue() != this.childNodes.modCount()) {
            return null;
        }
        return list;
    }

    private void stashChildren(List<Element> list) {
        Map<String, Object> mapUserData = attributes().userData();
        mapUserData.put(childElsKey, new WeakReference(list));
        mapUserData.put(childElsMod, Integer.valueOf(this.childNodes.modCount()));
    }

    public Stream<Element> stream() {
        return NodeUtils.stream(this, Element.class);
    }

    private <T> List<T> filterNodes(final Class<T> cls) {
        Stream stream = this.childNodes.stream();
        Objects.requireNonNull(cls);
        Stream<T> streamFilter = stream.filter(new Predicate() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((Node) obj);
            }
        });
        Objects.requireNonNull(cls);
        return (List) streamFilter.map(new Function() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return cls.cast((Node) obj);
            }
        }).collect(Collectors.collectingAndThen(Collectors.toList(), new Function() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Collections.unmodifiableList((List) obj);
            }
        }));
    }

    public List<TextNode> textNodes() {
        return filterNodes(TextNode.class);
    }

    public List<DataNode> dataNodes() {
        return filterNodes(DataNode.class);
    }

    public Elements select(String str) {
        return Selector.select(str, this);
    }

    public Elements select(Evaluator evaluator) {
        return Selector.select(evaluator, this);
    }

    public Stream<Element> selectStream(String str) {
        return Selector.selectStream(str, this);
    }

    public Stream<Element> selectStream(Evaluator evaluator) {
        return Selector.selectStream(evaluator, this);
    }

    public Element selectFirst(String str) {
        return Selector.selectFirst(str, this);
    }

    public Element selectFirst(Evaluator evaluator) {
        return Collector.findFirst(evaluator, this);
    }

    public Element expectFirst(String str) {
        String str2;
        Element elementSelectFirst = Selector.selectFirst(str, this);
        if (parent() != null) {
            str2 = "No elements matched the query '%s' on element '%s'.";
        } else {
            str2 = "No elements matched the query '%s' in the document.";
        }
        return (Element) Validate.expectNotNull(elementSelectFirst, str2, str, tagName());
    }

    public Nodes<Node> selectNodes(Evaluator evaluator) {
        return selectNodes(evaluator, Node.class);
    }

    public Nodes<Node> selectNodes(String str) {
        return selectNodes(str, Node.class);
    }

    public <T extends Node> Nodes<T> selectNodes(Evaluator evaluator, Class<T> cls) {
        Validate.notNull(evaluator);
        return Collector.collectNodes(evaluator, this, cls);
    }

    public <T extends Node> Nodes<T> selectNodes(String str, Class<T> cls) {
        Validate.notEmpty(str);
        return selectNodes(Selector.evaluatorOf(str), cls);
    }

    public <T extends Node> T selectFirstNode(String str, Class<T> cls) {
        return (T) selectFirstNode(Selector.evaluatorOf(str), cls);
    }

    public <T extends Node> T selectFirstNode(Evaluator evaluator, Class<T> cls) {
        return (T) Collector.findFirstNode(evaluator, this, cls);
    }

    public <T extends Node> T expectFirstNode(String str, Class<T> cls) {
        String str2;
        Node nodeSelectFirstNode = selectFirstNode(str, cls);
        if (parent() != null) {
            str2 = "No nodes matched the query '%s' on element '%s'.";
        } else {
            str2 = "No nodes matched the query '%s' in the document.";
        }
        return (T) Validate.expectNotNull(nodeSelectFirstNode, str2, str, tagName());
    }

    public boolean is(String str) {
        return is(Selector.evaluatorOf(str));
    }

    public boolean is(Evaluator evaluator) {
        return evaluator.m4593lambda$asPredicate$0$orgjsoupselectEvaluator(root(), this);
    }

    public Element closest(String str) {
        return closest(Selector.evaluatorOf(str));
    }

    public Element closest(Evaluator evaluator) {
        Validate.notNull(evaluator);
        Element elementRoot = root();
        while (!evaluator.m4593lambda$asPredicate$0$orgjsoupselectEvaluator(elementRoot, this)) {
            this = this.parent();
            if (this == null) {
                return null;
            }
        }
        return this;
    }

    public Elements selectXpath(String str) {
        return new Elements((List<Element>) NodeUtils.selectXpath(str, this, Element.class));
    }

    public <T extends Node> List<T> selectXpath(String str, Class<T> cls) {
        return NodeUtils.selectXpath(str, this, cls);
    }

    public Element appendChild(Node node) {
        Validate.notNull(node);
        reparentChild(node);
        ensureChildNodes();
        this.childNodes.add(node);
        node.setSiblingIndex(this.childNodes.size() - 1);
        return this;
    }

    public Element appendChildren(Collection<? extends Node> collection) {
        insertChildren(-1, collection);
        return this;
    }

    public Element appendTo(Element element) {
        Validate.notNull(element);
        element.appendChild(this);
        return this;
    }

    public Element prependChild(Node node) {
        Validate.notNull(node);
        addChildren(0, node);
        return this;
    }

    public Element prependChildren(Collection<? extends Node> collection) {
        insertChildren(0, collection);
        return this;
    }

    public Element insertChildren(int i, Collection<? extends Node> collection) {
        Validate.notNull(collection, "Children collection to be inserted must not be null.");
        int iChildNodeSize = childNodeSize();
        if (i < 0) {
            i += iChildNodeSize + 1;
        }
        Validate.isTrue(i >= 0 && i <= iChildNodeSize, "Insert position out of bounds.");
        addChildren(i, (Node[]) new ArrayList(collection).toArray(new Node[0]));
        return this;
    }

    public Element insertChildren(int i, Node... nodeArr) {
        Validate.notNull(nodeArr, "Children collection to be inserted must not be null.");
        int iChildNodeSize = childNodeSize();
        if (i < 0) {
            i += iChildNodeSize + 1;
        }
        Validate.isTrue(i >= 0 && i <= iChildNodeSize, "Insert position out of bounds.");
        addChildren(i, nodeArr);
        return this;
    }

    public Element appendElement(String str) {
        return appendElement(str, this.tag.namespace());
    }

    public Element appendElement(String str, String str2) {
        Parser rVar = NodeUtils.parser(this);
        Element element = new Element(rVar.tagSet().valueOf(str, str2, rVar.settings()), baseUri());
        appendChild(element);
        return element;
    }

    public Element prependElement(String str) {
        return prependElement(str, this.tag.namespace());
    }

    public Element prependElement(String str, String str2) {
        Parser rVar = NodeUtils.parser(this);
        Element element = new Element(rVar.tagSet().valueOf(str, str2, rVar.settings()), baseUri());
        prependChild(element);
        return element;
    }

    public Element appendText(String str) {
        Validate.notNull(str);
        appendChild(new TextNode(str));
        return this;
    }

    public Element prependText(String str) {
        Validate.notNull(str);
        prependChild(new TextNode(str));
        return this;
    }

    public Element append(String str) {
        Validate.notNull(str);
        addChildren((Node[]) NodeUtils.parser(this).parseFragmentInput(str, this, baseUri()).toArray(new Node[0]));
        return this;
    }

    public Element prepend(String str) {
        Validate.notNull(str);
        addChildren(0, (Node[]) NodeUtils.parser(this).parseFragmentInput(str, this, baseUri()).toArray(new Node[0]));
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element before(String str) {
        return (Element) super.before(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element before(Node node) {
        return (Element) super.before(node);
    }

    @Override // org.jsoup.nodes.Node
    public Element after(String str) {
        return (Element) super.after(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element after(Node node) {
        return (Element) super.after(node);
    }

    @Override // org.jsoup.nodes.Node
    public Element empty() {
        Iterator<Node> it = this.childNodes.iterator();
        while (it.hasNext()) {
            it.next().parentNode = null;
        }
        this.childNodes.clear();
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element wrap(String str) {
        return (Element) super.wrap(str);
    }

    private String uniqueIdSelector(Document document) {
        String strId = id();
        if (!strId.isEmpty()) {
            String str = HyperMenuUtils.GROUP_TITLE_START + TokenQueue.escapeCssIdentifier(strId);
            if (document != null) {
                Elements elementsSelect = document.select(str);
                if (elementsSelect.size() != 1 || elementsSelect.get(0) != this) {
                    return "";
                }
            }
            return str;
        }
        return "";
    }

    public String cssSelector() {
        Document documentOwnerDocument = ownerDocument();
        String strUniqueIdSelector = uniqueIdSelector(documentOwnerDocument);
        if (!strUniqueIdSelector.isEmpty()) {
            return strUniqueIdSelector;
        }
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        while (this != null && !(this instanceof Document)) {
            String strUniqueIdSelector2 = this.uniqueIdSelector(documentOwnerDocument);
            if (!strUniqueIdSelector2.isEmpty()) {
                sbBorrowBuilder.insert(0, strUniqueIdSelector2);
                break;
            }
            sbBorrowBuilder.insert(0, this.cssSelectorComponent());
            this = this.parent();
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private String cssSelectorComponent() {
        StringBuilder sbAppend = StringUtil.borrowBuilder().append(TokenQueue.escapeCssIdentifier(tagName()).replace("\\:", "|"));
        String str = (String) classNames().stream().map(new Function() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TokenQueue.escapeCssIdentifier((String) obj);
            }
        }).collect(StringUtil.joining("."));
        if (!str.isEmpty()) {
            sbAppend.append('.').append(str);
        }
        if (parent() == null || (parent() instanceof Document)) {
            return StringUtil.releaseBuilder(sbAppend);
        }
        sbAppend.insert(0, " > ");
        if (parent().select(sbAppend.toString()).size() > 1) {
            sbAppend.append(String.format(":nth-child(%d)", Integer.valueOf(elementSiblingIndex() + 1)));
        }
        return StringUtil.releaseBuilder(sbAppend);
    }

    public Elements siblingElements() {
        if (this.parentNode == null) {
            return new Elements(0);
        }
        List<Element> listChildElementsList = parent().childElementsList();
        Elements elements = new Elements(listChildElementsList.size() - 1);
        for (Element element : listChildElementsList) {
            if (element != this) {
                elements.add(element);
            }
        }
        return elements;
    }

    public Elements nextElementSiblings() {
        return nextElementSiblings(true);
    }

    public Elements previousElementSiblings() {
        return nextElementSiblings(false);
    }

    private Elements nextElementSiblings(boolean z) {
        Elements elements = new Elements();
        if (this.parentNode == null) {
            return elements;
        }
        elements.add(this);
        return z ? elements.nextAll() : elements.prevAll();
    }

    public Element firstElementSibling() {
        return parent() != null ? parent().firstElementChild() : this;
    }

    public int elementSiblingIndex() {
        if (parent() == null) {
            return 0;
        }
        return indexInList(this, parent().childElementsList());
    }

    public Element lastElementSibling() {
        return parent() != null ? parent().lastElementChild() : this;
    }

    private static <E extends Element> int indexInList(Element element, List<E> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == element) {
                return i;
            }
        }
        return 0;
    }

    public Element firstElementChild() {
        for (Node nodeFirstChild = firstChild(); nodeFirstChild != null; nodeFirstChild = nodeFirstChild.nextSibling()) {
            if (nodeFirstChild instanceof Element) {
                return (Element) nodeFirstChild;
            }
        }
        return null;
    }

    public Element lastElementChild() {
        for (Node nodeLastChild = lastChild(); nodeLastChild != null; nodeLastChild = nodeLastChild.previousSibling()) {
            if (nodeLastChild instanceof Element) {
                return (Element) nodeLastChild;
            }
        }
        return null;
    }

    public Elements getElementsByTag(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Tag(Normalizer.normalize(str)), this);
    }

    public Element getElementById(String str) {
        Validate.notEmpty(str);
        return Collector.findFirst(new Evaluator.Id(str), this);
    }

    public Elements getElementsByClass(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Class(str), this);
    }

    public Elements getElementsByAttribute(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Attribute(str.trim()), this);
    }

    public Elements getElementsByAttributeStarting(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.AttributeStarting(str.trim()), this);
    }

    public Elements getElementsByAttributeValue(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValue(str, str2), this);
    }

    public Elements getElementsByAttributeValueNot(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(str, str2), this);
    }

    public Elements getElementsByAttributeValueStarting(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(str, str2), this);
    }

    public Elements getElementsByAttributeValueEnding(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(str, str2), this);
    }

    public Elements getElementsByAttributeValueContaining(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(str, str2), this);
    }

    public Elements getElementsByAttributeValueMatching(String str, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(str, pattern), this);
    }

    public Elements getElementsByAttributeValueMatching(String str, String str2) {
        try {
            return getElementsByAttributeValueMatching(str, Pattern.compile(str2));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str2, e);
        }
    }

    public Elements getElementsByIndexLessThan(int i) {
        return Collector.collect(new Evaluator.IndexLessThan(i), this);
    }

    public Elements getElementsByIndexGreaterThan(int i) {
        return Collector.collect(new Evaluator.IndexGreaterThan(i), this);
    }

    public Elements getElementsByIndexEquals(int i) {
        return Collector.collect(new Evaluator.IndexEquals(i), this);
    }

    public Elements getElementsContainingText(String str) {
        return Collector.collect(new Evaluator.ContainsText(str), this);
    }

    public Elements getElementsContainingOwnText(String str) {
        return Collector.collect(new Evaluator.ContainsOwnText(str), this);
    }

    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    public Elements getElementsMatchingText(String str) {
        try {
            return getElementsMatchingText(Pattern.compile(str));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e);
        }
    }

    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    public Elements getElementsMatchingOwnText(String str) {
        try {
            return getElementsMatchingOwnText(Pattern.compile(str));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e);
        }
    }

    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    public String text() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        new TextAccumulator(sbBorrowBuilder).traverse(this);
        return StringUtil.releaseBuilder(sbBorrowBuilder).trim();
    }

    private static class TextAccumulator implements NodeVisitor {
        private final StringBuilder accum;

        public TextAccumulator(StringBuilder sb) {
            this.accum = sb;
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i) {
            if (node instanceof TextNode) {
                Element.appendNormalisedText(this.accum, (TextNode) node);
            } else if (node instanceof Element) {
                Element element = (Element) node;
                if (this.accum.length() > 0) {
                    if ((element.isBlock() || element.nameIs("br")) && !TextNode.lastCharIsWhitespace(this.accum)) {
                        this.accum.append(' ');
                    }
                }
            }
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i) {
            if (node instanceof Element) {
                Element element = (Element) node;
                Node nodeNextSibling = node.nextSibling();
                if (element.tag.isInline()) {
                    return;
                }
                if (((nodeNextSibling instanceof TextNode) || ((nodeNextSibling instanceof Element) && ((Element) nodeNextSibling).tag.isInline())) && !TextNode.lastCharIsWhitespace(this.accum)) {
                    this.accum.append(' ');
                }
            }
        }
    }

    public String wholeText() {
        return wholeTextOf(nodeStream());
    }

    @Override // org.jsoup.nodes.Node
    public String nodeValue() {
        return wholeOwnText();
    }

    private static String wholeTextOf(Stream<Node> stream) {
        return (String) stream.map(new Function() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Element.lambda$wholeTextOf$0((Node) obj);
            }
        }).collect(StringUtil.joining(""));
    }

    static /* synthetic */ String lambda$wholeTextOf$0(Node node) {
        if (node instanceof TextNode) {
            return ((TextNode) node).getWholeText();
        }
        return node.nameIs("br") ? StringUtils.LF : "";
    }

    public String wholeOwnText() {
        return wholeTextOf(this.childNodes.stream());
    }

    public String ownText() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        ownText(sbBorrowBuilder);
        return StringUtil.releaseBuilder(sbBorrowBuilder).trim();
    }

    private void ownText(StringBuilder sb) {
        for (int i = 0; i < childNodeSize(); i++) {
            Node node = this.childNodes.get(i);
            if (node instanceof TextNode) {
                appendNormalisedText(sb, (TextNode) node);
            } else if (node.nameIs("br") && !TextNode.lastCharIsWhitespace(sb)) {
                sb.append(StringUtils.SPACE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendNormalisedText(StringBuilder sb, TextNode textNode) {
        String wholeText = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode) || (textNode instanceof CDataNode)) {
            sb.append(wholeText);
        } else {
            StringUtil.appendNormalisedWhitespace(sb, wholeText, TextNode.lastCharIsWhitespace(sb));
        }
    }

    static boolean preserveWhitespace(Node node) {
        if (node instanceof Element) {
            Element elementParent = (Element) node;
            int i = 0;
            while (!elementParent.tag.preserveWhitespace()) {
                elementParent = elementParent.parent();
                i++;
                if (i >= 6 || elementParent == null) {
                }
            }
            return true;
        }
        return false;
    }

    public Element text(String str) {
        Validate.notNull(str);
        empty();
        if (tag().is(Tag.Data)) {
            appendChild(new DataNode(str));
            return this;
        }
        appendChild(new TextNode(str));
        return this;
    }

    public boolean hasText() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        filter(new NodeFilter() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda5
            @Override // org.jsoup.select.NodeFilter
            public final NodeFilter.FilterResult head(Node node, int i) {
                return Element.lambda$hasText$1(atomicBoolean, node, i);
            }
        });
        return atomicBoolean.get();
    }

    static /* synthetic */ NodeFilter.FilterResult lambda$hasText$1(AtomicBoolean atomicBoolean, Node node, int i) {
        if ((node instanceof TextNode) && !((TextNode) node).isBlank()) {
            atomicBoolean.set(true);
            return NodeFilter.FilterResult.STOP;
        }
        return NodeFilter.FilterResult.CONTINUE;
    }

    public String data() {
        final StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        traverse(new NodeVisitor() { // from class: org.jsoup.nodes.Element$$ExternalSyntheticLambda3
            @Override // org.jsoup.select.NodeVisitor
            public final void head(Node node, int i) {
                Element.lambda$data$2(sbBorrowBuilder, node, i);
            }
        });
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    static /* synthetic */ void lambda$data$2(StringBuilder sb, Node node, int i) {
        if (node instanceof DataNode) {
            sb.append(((DataNode) node).getWholeData());
        } else if (node instanceof Comment) {
            sb.append(((Comment) node).getData());
        } else if (node instanceof CDataNode) {
            sb.append(((CDataNode) node).getWholeText());
        }
    }

    public String className() {
        return attr("class").trim();
    }

    public Set<String> classNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet(Arrays.asList(ClassSplit.split(className())));
        linkedHashSet.remove("");
        return linkedHashSet;
    }

    public Element classNames(Set<String> set) {
        Validate.notNull(set);
        if (set.isEmpty()) {
            attributes().remove("class");
            return this;
        }
        attributes().put("class", StringUtil.join(set, StringUtils.SPACE));
        return this;
    }

    public boolean hasClass(String str) {
        String str2;
        Attributes attributes = this.attributes;
        if (attributes == null) {
            return false;
        }
        String ignoreCase = attributes.getIgnoreCase("class");
        int length = ignoreCase.length();
        int length2 = str.length();
        if (length != 0 && length >= length2) {
            if (length == length2) {
                return str.equalsIgnoreCase(ignoreCase);
            }
            int i = 0;
            boolean z = false;
            int i2 = 0;
            while (i < length) {
                if (!Character.isWhitespace(ignoreCase.charAt(i))) {
                    str2 = str;
                    if (!z) {
                        i2 = i;
                        z = true;
                    }
                } else if (z) {
                    if (i - i2 == length2) {
                        str2 = str;
                        if (ignoreCase.regionMatches(true, i2, str2, 0, length2)) {
                            return true;
                        }
                    } else {
                        str2 = str;
                    }
                    z = false;
                } else {
                    str2 = str;
                }
                i++;
                str = str2;
            }
            String str3 = str;
            if (z && length - i2 == length2) {
                return ignoreCase.regionMatches(true, i2, str3, 0, length2);
            }
        }
        return false;
    }

    public Element addClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        setClassNames.add(str);
        classNames(setClassNames);
        return this;
    }

    public Element removeClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        setClassNames.remove(str);
        classNames(setClassNames);
        return this;
    }

    public Element toggleClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        if (setClassNames.contains(str)) {
            setClassNames.remove(str);
        } else {
            setClassNames.add(str);
        }
        classNames(setClassNames);
        return this;
    }

    public String val() {
        if (elementIs("textarea", Parser.NamespaceHtml)) {
            return text();
        }
        return attr("value");
    }

    public Element val(String str) {
        if (elementIs("textarea", Parser.NamespaceHtml)) {
            text(str);
            return this;
        }
        attr("value", str);
        return this;
    }

    public Range endSourceRange() {
        return Range.of(this, false);
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        String strSafeTagName = safeTagName(outputSettings.syntax());
        quietAppendable.append(Typography.less).append(strSafeTagName);
        Attributes attributes = this.attributes;
        if (attributes != null) {
            attributes.html(quietAppendable, outputSettings);
        }
        if (this.childNodes.isEmpty()) {
            boolean z = outputSettings.syntax() == Document.OutputSettings.Syntax.xml || !this.tag.namespace().equals(Parser.NamespaceHtml);
            if (z && (this.tag.is(Tag.SeenSelfClose) || (this.tag.isKnownTag() && (this.tag.isEmpty() || this.tag.isSelfClosing())))) {
                quietAppendable.append(" />");
                return;
            } else if (!z && this.tag.isEmpty()) {
                quietAppendable.append(Typography.greater);
                return;
            } else {
                quietAppendable.append("></").append(strSafeTagName).append(Typography.greater);
                return;
            }
        }
        quietAppendable.append(Typography.greater);
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        if (this.childNodes.isEmpty()) {
            return;
        }
        quietAppendable.append("</").append(safeTagName(outputSettings.syntax())).append(Typography.greater);
    }

    private String safeTagName(Document.OutputSettings.Syntax syntax) {
        Document.OutputSettings.Syntax syntax2 = Document.OutputSettings.Syntax.xml;
        String strTagName = tagName();
        return syntax == syntax2 ? Normalizer.xmlSafeTagName(strTagName) : strTagName;
    }

    public String html() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        html(sbBorrowBuilder);
        String strReleaseBuilder = StringUtil.releaseBuilder(sbBorrowBuilder);
        return NodeUtils.outputSettings(this).prettyPrint() ? strReleaseBuilder.trim() : strReleaseBuilder;
    }

    @Override // org.jsoup.nodes.Node
    public <T extends Appendable> T html(T t) {
        Node nodeFirstChild = firstChild();
        if (nodeFirstChild != null) {
            Printer printerPrinterFor = Printer.printerFor(nodeFirstChild, QuietAppendable.wrap(t));
            while (nodeFirstChild != null) {
                printerPrinterFor.traverse(nodeFirstChild);
                nodeFirstChild = nodeFirstChild.nextSibling();
            }
        }
        return t;
    }

    public Element html(String str) {
        empty();
        append(str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public Element mo4587clone() {
        return (Element) super.mo4587clone();
    }

    @Override // org.jsoup.nodes.Node
    public Element shallowClone() {
        String strBaseUri = baseUri();
        if (strBaseUri.isEmpty()) {
            strBaseUri = null;
        }
        Tag tag = this.tag;
        Attributes attributes = this.attributes;
        return new Element(tag, strBaseUri, attributes != null ? attributes.clone() : null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jsoup.nodes.Node
    public Element doClone(Node node) {
        Element element = (Element) super.doClone(node);
        NodeList nodeList = new NodeList(this.childNodes.size());
        element.childNodes = nodeList;
        nodeList.addAll(this.childNodes);
        Attributes attributes = this.attributes;
        if (attributes != null) {
            Attributes attributesClone = attributes.clone();
            element.attributes = attributesClone;
            attributesClone.userData(childElsKey, null);
        }
        return element;
    }

    @Override // org.jsoup.nodes.Node
    public Element clearAttributes() {
        if (this.attributes != null) {
            super.clearAttributes();
            if (this.attributes.size() == 0) {
                this.attributes = null;
            }
        }
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element removeAttr(String str) {
        return (Element) super.removeAttr(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element root() {
        return (Element) super.root();
    }

    @Override // org.jsoup.nodes.Node
    public Element traverse(NodeVisitor nodeVisitor) {
        return (Element) super.traverse(nodeVisitor);
    }

    @Override // org.jsoup.nodes.Node
    public Element forEachNode(Consumer<? super Node> consumer) {
        return (Element) super.forEachNode(consumer);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super Element> consumer) {
        stream().forEach(consumer);
    }

    @Override // java.lang.Iterable
    public Iterator<Element> iterator() {
        return new NodeIterator(this, Element.class);
    }

    @Override // org.jsoup.nodes.Node
    public Element filter(NodeFilter nodeFilter) {
        return (Element) super.filter(nodeFilter);
    }

    static final class NodeList extends ArrayList<Node> {
        public NodeList(int i) {
            super(i);
        }

        int modCount() {
            return this.modCount;
        }
    }
}
