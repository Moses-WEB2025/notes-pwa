package org.jsoup.parser;

import androidx.appsearch.ast.FunctionNode;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.CoreConstants;
import com.miui.common.stat.EditStat;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.ui.IFragmentController;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.util.a;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import miui.cloud.CloudPushConstants;
import miuix.smartaction.SmartAction;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/* JADX INFO: loaded from: classes6.dex */
public class HtmlTreeBuilder extends TreeBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int MaxScopeSearchDepth = 100;
    private static final int maxQueueDepth = 256;
    private static final int maxUsedFormattingElements = 12;
    private boolean baseUriSetFromDoc;
    private Element contextElement;
    private Token.EndTag emptyEnd;
    private FormElement formElement;
    ArrayList<Element> formattingElements;
    private boolean fosterInserts;
    private boolean fragmentParsing;
    private boolean framesetOk;
    private Element headElement;
    private HtmlTreeBuilderState originalState;
    private List<Token.Character> pendingTableCharacters;
    private final String[] specificScopeTarget = {null};
    private HtmlTreeBuilderState state;
    private ArrayList<HtmlTreeBuilderState> tmplInsertMode;
    static final String[] TagsSearchInScope = {"applet", "caption", "html", "marquee", "object", HtmlParser.TableElement.TAG, "td", "template", "th"};
    static final String[] TagSearchInScopeMath = {"annotation-xml", "mi", "mn", "mo", "ms", "mtext"};
    static final String[] TagSearchInScopeSvg = {"desc", "foreignObject", "title"};
    static final String[] TagSearchList = {"ol", "ul"};
    static final String[] TagSearchButton = {"button"};
    static final String[] TagSearchTableScope = {"html", HtmlParser.TableElement.TAG};
    static final String[] TagSearchSelectScope = {"optgroup", "option"};
    static final String[] TagSearchEndTags = {"dd", "dt", "li", "optgroup", "option", CloudPushConstants.WATERMARK_TYPE.PERSONAL, "rb", "rp", "rt", "rtc"};
    static final String[] TagThoroughSearchEndTags = {"caption", "colgroup", "dd", "dt", "li", "optgroup", "option", CloudPushConstants.WATERMARK_TYPE.PERSONAL, "rb", "rp", "rt", "rtc", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] TagSearchSpecial = {RemoteDeviceInfo.KEY_ADDRESS, "applet", a.e, "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", EditStat.ELEMENT_CENTER, "col", "colgroup", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", TypedValues.AttributesType.S_FRAME, "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", HtmlParser.SplitLineElement.TAG, "html", "iframe", HtmlParser.ImageElement.TAG, HtmlParser.InputElement.TAG, "keygen", "li", "link", "listing", "main", "marquee", "menu", "meta", IFragmentController.TAG_NAVIGATION, "noembed", "noframes", "noscript", "object", "ol", CloudPushConstants.WATERMARK_TYPE.PERSONAL, "param", "plaintext", "pre", StringLookupFactory.KEY_SCRIPT, FunctionNode.FUNCTION_NAME_SEARCH, "section", SmartAction.Feature.SELECT, "source", "style", NotesNormalConstants.SUMMARY_TABLE_NAME, HtmlParser.TableElement.TAG, "tbody", "td", "template", "textarea", "tfoot", "th", "thead", "title", "tr", "track", "ul", "wbr", "xmp"};
    static String[] TagSearchSpecialMath = {"annotation-xml", "mi", "mn", "mo", "ms", "mtext"};
    static final String[] TagMathMlTextIntegration = {"mi", "mn", "mo", "ms", "mtext"};
    static final String[] TagSvgHtmlIntegration = {"desc", "foreignObject", "title"};
    static final String[] TagFormListed = {"button", "fieldset", HtmlParser.InputElement.TAG, "keygen", "object", "output", SmartAction.Feature.SELECT, "textarea"};

    @Override // org.jsoup.parser.TreeBuilder
    ParseSettings defaultSettings() {
        return ParseSettings.htmlDefault;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jsoup.parser.TreeBuilder
    public HtmlTreeBuilder newInstance() {
        return new HtmlTreeBuilder();
    }

    @Override // org.jsoup.parser.TreeBuilder
    protected void initialiseParse(Reader reader, String str, Parser parser) {
        super.initialiseParse(reader, str, parser);
        this.state = HtmlTreeBuilderState.Initial;
        this.originalState = null;
        this.baseUriSetFromDoc = false;
        this.headElement = null;
        this.formElement = null;
        this.contextElement = null;
        this.formattingElements = new ArrayList<>();
        this.tmplInsertMode = new ArrayList<>();
        this.pendingTableCharacters = new ArrayList();
        this.emptyEnd = new Token.EndTag(this);
        this.framesetOk = true;
        this.fosterInserts = false;
        this.fragmentParsing = false;
    }

    @Override // org.jsoup.parser.TreeBuilder
    void initialiseParseFragment(Element element) {
        this.state = HtmlTreeBuilderState.Initial;
        byte b = 1;
        this.fragmentParsing = true;
        if (element != null) {
            String strNormalName = element.normalName();
            this.contextElement = new Element(tagFor(strNormalName, strNormalName, defaultNamespace(), this.settings), this.baseUri);
            if (element.ownerDocument() != null) {
                this.doc.quirksMode(element.ownerDocument().quirksMode());
            }
            strNormalName.hashCode();
            switch (strNormalName.hashCode()) {
                case -1321546630:
                    b = !strNormalName.equals("template") ? (byte) -1 : (byte) 0;
                    break;
                case -907685685:
                    if (!strNormalName.equals(StringLookupFactory.KEY_SCRIPT)) {
                        b = -1;
                    }
                    break;
                case 1973234167:
                    b = !strNormalName.equals("plaintext") ? (byte) -1 : (byte) 2;
                    break;
                default:
                    b = -1;
                    break;
            }
            switch (b) {
                case 0:
                    this.tokeniser.transition(TokeniserState.Data);
                    pushTemplateMode(HtmlTreeBuilderState.InTemplate);
                    break;
                case 1:
                    this.tokeniser.transition(TokeniserState.ScriptData);
                    break;
                case 2:
                    this.tokeniser.transition(TokeniserState.PLAINTEXT);
                    break;
                default:
                    TokeniserState tokeniserStateTextState = this.contextElement.tag().textState();
                    if (tokeniserStateTextState != null) {
                        this.tokeniser.transition(tokeniserStateTextState);
                    } else {
                        this.tokeniser.transition(TokeniserState.Data);
                    }
                    break;
            }
            this.doc.appendChild(this.contextElement);
            push(this.contextElement);
            resetInsertionMode();
            while (element != null) {
                if (element instanceof FormElement) {
                    this.formElement = (FormElement) element;
                    return;
                }
                element = element.parent();
            }
        }
    }

    @Override // org.jsoup.parser.TreeBuilder
    List<Node> completeParseFragment() {
        Element element = this.contextElement;
        if (element != null) {
            List<Node> listSiblingNodes = element.siblingNodes();
            if (!listSiblingNodes.isEmpty()) {
                this.contextElement.insertChildren(-1, listSiblingNodes);
            }
            return this.contextElement.childNodes();
        }
        return this.doc.childNodes();
    }

    @Override // org.jsoup.parser.TreeBuilder
    protected boolean process(Token token) {
        return (useCurrentOrForeignInsert(token) ? this.state : HtmlTreeBuilderState.ForeignContent).process(token, this);
    }

    boolean useCurrentOrForeignInsert(Token token) {
        if (this.stack.isEmpty()) {
            return true;
        }
        Element elementCurrentElement = currentElement();
        String strNamespace = elementCurrentElement.tag().namespace();
        if (Parser.NamespaceHtml.equals(strNamespace)) {
            return true;
        }
        if (isMathmlTextIntegration(elementCurrentElement) && ((token.isStartTag() && !"mglyph".equals(token.asStartTag().normalName) && !"malignmark".equals(token.asStartTag().normalName)) || token.isCharacter())) {
            return true;
        }
        if (Parser.NamespaceMathml.equals(strNamespace) && elementCurrentElement.nameIs("annotation-xml") && token.isStartTag() && "svg".equals(token.asStartTag().normalName)) {
            return true;
        }
        if (isHtmlIntegration(elementCurrentElement) && (token.isStartTag() || token.isCharacter())) {
            return true;
        }
        return token.isEOF();
    }

    static boolean isMathmlTextIntegration(Element element) {
        return Parser.NamespaceMathml.equals(element.tag().namespace()) && StringUtil.inSorted(element.normalName(), TagMathMlTextIntegration);
    }

    static boolean isHtmlIntegration(Element element) {
        if (Parser.NamespaceMathml.equals(element.tag().namespace()) && element.nameIs("annotation-xml")) {
            String strNormalize = Normalizer.normalize(element.attr(XMLWriter.ENCODING));
            if (strNormalize.equals(SDKConstants.MimeType.HTML) || strNormalize.equals("application/xhtml+xml")) {
                return true;
            }
        }
        return Parser.NamespaceSvg.equals(element.tag().namespace()) && StringUtil.in(element.tagName(), TagSvgHtmlIntegration);
    }

    boolean process(Token token, HtmlTreeBuilderState htmlTreeBuilderState) {
        return htmlTreeBuilderState.process(token, this);
    }

    void transition(HtmlTreeBuilderState htmlTreeBuilderState) {
        this.state = htmlTreeBuilderState;
    }

    HtmlTreeBuilderState state() {
        return this.state;
    }

    void markInsertionMode() {
        this.originalState = this.state;
    }

    HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    void framesetOk(boolean z) {
        this.framesetOk = z;
    }

    boolean framesetOk() {
        return this.framesetOk;
    }

    Document getDocument() {
        return this.doc;
    }

    String getBaseUri() {
        return this.baseUri;
    }

    void maybeSetBaseUri(Element element) {
        if (this.baseUriSetFromDoc) {
            return;
        }
        String strAbsUrl = element.absUrl(HtmlParser.LinkCardElement.KEY_LINK);
        if (strAbsUrl.length() != 0) {
            this.baseUri = strAbsUrl;
            this.baseUriSetFromDoc = true;
            this.doc.setBaseUri(strAbsUrl);
        }
    }

    boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    void error(HtmlTreeBuilderState htmlTreeBuilderState) {
        if (this.parser.getErrors().canAddError()) {
            this.parser.getErrors().add(new ParseError(this.reader, "Unexpected %s token [%s] when in state [%s]", this.currentToken.tokenType(), this.currentToken, htmlTreeBuilderState));
        }
    }

    Element createElementFor(Token.StartTag startTag, String str, boolean z) {
        Attributes attributesNormalizeAttributes = startTag.attributes;
        if (!z) {
            attributesNormalizeAttributes = this.settings.normalizeAttributes(attributesNormalizeAttributes);
        }
        if (attributesNormalizeAttributes != null && !attributesNormalizeAttributes.isEmpty() && attributesNormalizeAttributes.deduplicate(this.settings) > 0) {
            error("Dropped duplicate attribute(s) in tag [%s]", startTag.normalName);
        }
        Tag tagTagFor = tagFor(startTag.name(), startTag.normalName, str, z ? ParseSettings.preserveCase : this.settings);
        if (tagTagFor.normalName().equals("form")) {
            return new FormElement(tagTagFor, null, attributesNormalizeAttributes);
        }
        return new Element(tagTagFor, null, attributesNormalizeAttributes);
    }

    Element insertElementFor(Token.StartTag startTag) {
        Element elementCreateElementFor = createElementFor(startTag, Parser.NamespaceHtml, false);
        doInsertElement(elementCreateElementFor);
        if (startTag.isSelfClosing()) {
            Tag tag = elementCreateElementFor.tag();
            tag.setSeenSelfClose();
            if (tag.isKnownTag() && (tag.isEmpty() || tag.isSelfClosing())) {
                this.tokeniser.transition(TokeniserState.Data);
                this.tokeniser.emit(this.emptyEnd.reset().name(elementCreateElementFor.tagName()));
                return elementCreateElementFor;
            }
            this.tokeniser.error("Tag [%s] cannot be self-closing; not a void tag", tag.normalName());
        }
        return elementCreateElementFor;
    }

    Element insertForeignElementFor(Token.StartTag startTag, String str) {
        Element elementCreateElementFor = createElementFor(startTag, str, true);
        doInsertElement(elementCreateElementFor);
        if (startTag.isSelfClosing()) {
            elementCreateElementFor.tag().setSeenSelfClose();
            pop();
        }
        return elementCreateElementFor;
    }

    Element insertEmptyElementFor(Token.StartTag startTag) {
        Element elementCreateElementFor = createElementFor(startTag, Parser.NamespaceHtml, false);
        doInsertElement(elementCreateElementFor);
        pop();
        return elementCreateElementFor;
    }

    FormElement insertFormElement(Token.StartTag startTag, boolean z, boolean z2) {
        FormElement formElement = (FormElement) createElementFor(startTag, Parser.NamespaceHtml, false);
        if (!z2 || !onStack("template")) {
            setFormElement(formElement);
        }
        doInsertElement(formElement);
        if (!z) {
            pop();
        }
        return formElement;
    }

    private void doInsertElement(Element element) {
        if (this.formElement != null && element.tag().namespace.equals(Parser.NamespaceHtml) && StringUtil.inSorted(element.normalName(), TagFormListed)) {
            this.formElement.addElement(element);
        }
        if (this.parser.getErrors().canAddError() && element.hasAttr("xmlns") && !element.attr("xmlns").equals(element.tag().namespace())) {
            error("Invalid xmlns attribute [%s] on tag [%s]", element.attr("xmlns"), element.tagName());
        }
        if (isFosterInserts() && StringUtil.inSorted(currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
            insertInFosterParent(element);
        } else {
            currentElement().appendChild(element);
        }
        push(element);
    }

    void insertCommentNode(Token.Comment comment) {
        Comment comment2 = new Comment(comment.getData());
        currentElement().appendChild(comment2);
        onNodeInserted(comment2);
    }

    void insertCharacterNode(Token.Character character) {
        insertCharacterToElement(character, currentElement());
    }

    void insertCharacterToElement(Token.Character character, Element element) {
        Node textNode;
        String data = character.getData();
        if (character.isCData()) {
            textNode = new CDataNode(data);
        } else if (element.tag().is(Tag.Data)) {
            textNode = new DataNode(data);
        } else {
            textNode = new TextNode(data);
        }
        element.appendChild(textNode);
        onNodeInserted(textNode);
    }

    ArrayList<Element> getStack() {
        return this.stack;
    }

    boolean onStack(Element element) {
        return onStack(this.stack, element);
    }

    boolean onStack(String str) {
        return getFromStack(str) != null;
    }

    private static boolean onStack(ArrayList<Element> arrayList, Element element) {
        int size = arrayList.size();
        int i = size - 1;
        int i2 = i >= 256 ? size - 257 : 0;
        while (i >= i2) {
            if (arrayList.get(i) == element) {
                return true;
            }
            i--;
        }
        return false;
    }

    Element getFromStack(String str) {
        int size = this.stack.size();
        int i = size - 1;
        int i2 = i >= 256 ? size - 257 : 0;
        while (i >= i2) {
            Element element = this.stack.get(i);
            if (element.elementIs(str, Parser.NamespaceHtml)) {
                return element;
            }
            i--;
        }
        return null;
    }

    boolean removeFromStack(Element element) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            if (this.stack.get(size) == element) {
                this.stack.remove(size);
                onNodeClosed(element);
                return true;
            }
        }
        return false;
    }

    Element popStackToClose(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element elementPop = pop();
            if (elementPop.elementIs(str, Parser.NamespaceHtml)) {
                return elementPop;
            }
        }
        return null;
    }

    Element popStackToCloseAnyNamespace(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element elementPop = pop();
            if (elementPop.nameIs(str)) {
                return elementPop;
            }
        }
        return null;
    }

    void popStackToClose(String... strArr) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element elementPop = pop();
            if (StringUtil.inSorted(elementPop.normalName(), strArr) && Parser.NamespaceHtml.equals(elementPop.tag().namespace())) {
                return;
            }
        }
    }

    void clearStackToTableContext() {
        clearStackToContext(HtmlParser.TableElement.TAG, "template");
    }

    void clearStackToTableBodyContext() {
        clearStackToContext("tbody", "tfoot", "thead", "template");
    }

    void clearStackToTableRowContext() {
        clearStackToContext("tr", "template");
    }

    private void clearStackToContext(String... strArr) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element element = this.stack.get(size);
            if (Parser.NamespaceHtml.equals(element.tag().namespace()) && (StringUtil.in(element.normalName(), strArr) || element.nameIs("html"))) {
                return;
            }
            pop();
        }
    }

    Element aboveOnStack(Element element) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            if (this.stack.get(size) == element) {
                return this.stack.get(size - 1);
            }
        }
        return null;
    }

    void insertOnStackAfter(Element element, Element element2) {
        int iLastIndexOf = this.stack.lastIndexOf(element);
        Validate.isTrue(iLastIndexOf != -1);
        this.stack.add(iLastIndexOf + 1, element2);
    }

    void replaceOnStack(Element element, Element element2) {
        replaceInQueue(this.stack, element, element2);
    }

    private static void replaceInQueue(ArrayList<Element> arrayList, Element element, Element element2) {
        int iLastIndexOf = arrayList.lastIndexOf(element);
        Validate.isTrue(iLastIndexOf != -1);
        arrayList.set(iLastIndexOf, element2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:103:0x0155 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:112:0x0183 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:113:0x0184 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:127:0x0157 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:128:0x015d A[SYNTHETIC] */
    boolean resetInsertionMode() {
        int size = this.stack.size();
        int i = size - 1;
        int i2 = i >= 256 ? size - 257 : 0;
        HtmlTreeBuilderState htmlTreeBuilderState = this.state;
        if (this.stack.size() == 0) {
            transition(HtmlTreeBuilderState.InBody);
        }
        boolean z = false;
        while (i >= i2) {
            Element element = this.stack.get(i);
            if (i == i2) {
                if (this.fragmentParsing) {
                    element = this.contextElement;
                }
                z = true;
            }
            String strNormalName = element != null ? element.normalName() : "";
            if (Parser.NamespaceHtml.equals(element.tag().namespace())) {
                strNormalName.hashCode();
                switch (strNormalName) {
                    case "frameset":
                        transition(HtmlTreeBuilderState.InFrameset);
                        break;
                    case "template":
                        HtmlTreeBuilderState htmlTreeBuilderStateCurrentTemplateMode = currentTemplateMode();
                        Validate.notNull(htmlTreeBuilderStateCurrentTemplateMode, "Bug: no template insertion mode on stack!");
                        transition(htmlTreeBuilderStateCurrentTemplateMode);
                        break;
                    case "select":
                        transition(HtmlTreeBuilderState.InSelect);
                        break;
                    case "colgroup":
                        transition(HtmlTreeBuilderState.InColumnGroup);
                        break;
                    case "td":
                    case "th":
                        if (z) {
                            if (z) {
                                transition(HtmlTreeBuilderState.InBody);
                            }
                            break;
                        } else {
                            transition(HtmlTreeBuilderState.InCell);
                            break;
                        }
                        break;
                    case "tr":
                        transition(HtmlTreeBuilderState.InRow);
                        break;
                    case "body":
                        transition(HtmlTreeBuilderState.InBody);
                        break;
                    case "head":
                        if (z) {
                            if (z) {
                                transition(HtmlTreeBuilderState.InBody);
                            }
                            break;
                        } else {
                            transition(HtmlTreeBuilderState.InHead);
                            break;
                        }
                        break;
                    case "html":
                        transition(this.headElement == null ? HtmlTreeBuilderState.BeforeHead : HtmlTreeBuilderState.AfterHead);
                        break;
                    case "table":
                        transition(HtmlTreeBuilderState.InTable);
                        break;
                    case "tbody":
                    case "tfoot":
                    case "thead":
                        transition(HtmlTreeBuilderState.InTableBody);
                        break;
                    case "caption":
                        transition(HtmlTreeBuilderState.InCaption);
                        break;
                    default:
                        if (z) {
                            transition(HtmlTreeBuilderState.InBody);
                            break;
                        } else {
                            break;
                        }
                        break;
                }
                if (this.state != htmlTreeBuilderState) {
                    return true;
                }
                return false;
            }
            i--;
        }
        if (this.state != htmlTreeBuilderState) {
            return true;
        }
        return false;
    }

    void resetBody() {
        if (!onStack("body")) {
            this.stack.add(this.doc.body());
        }
        transition(HtmlTreeBuilderState.InBody);
    }

    private boolean inSpecificScope(String str, String[] strArr, String[] strArr2) {
        String[] strArr3 = this.specificScopeTarget;
        strArr3[0] = str;
        return inSpecificScope(strArr3, strArr, strArr2);
    }

    private boolean inSpecificScope(String[] strArr, String[] strArr2, String[] strArr3) {
        int size = this.stack.size();
        int i = size - 1;
        int i2 = i > 100 ? size - 101 : 0;
        while (i >= i2) {
            Element element = this.stack.get(i);
            String strNormalName = element.normalName();
            String strNamespace = element.tag().namespace();
            if (strNamespace.equals(Parser.NamespaceHtml)) {
                if (StringUtil.inSorted(strNormalName, strArr)) {
                    return true;
                }
                if (StringUtil.inSorted(strNormalName, strArr2)) {
                    return false;
                }
                if (strArr3 != null && StringUtil.inSorted(strNormalName, strArr3)) {
                    return false;
                }
            } else if (strArr2 != TagsSearchInScope) {
                continue;
            } else {
                if (strNamespace.equals(Parser.NamespaceMathml) && StringUtil.inSorted(strNormalName, TagSearchInScopeMath)) {
                    return false;
                }
                if (strNamespace.equals(Parser.NamespaceSvg) && StringUtil.inSorted(strNormalName, TagSearchInScopeSvg)) {
                    return false;
                }
            }
            i--;
        }
        return false;
    }

    boolean inScope(String[] strArr) {
        return inSpecificScope(strArr, TagsSearchInScope, (String[]) null);
    }

    boolean inScope(String str) {
        return inScope(str, null);
    }

    boolean inScope(String str, String[] strArr) {
        return inSpecificScope(str, TagsSearchInScope, strArr);
    }

    boolean inListItemScope(String str) {
        return inScope(str, TagSearchList);
    }

    boolean inButtonScope(String str) {
        return inScope(str, TagSearchButton);
    }

    boolean inTableScope(String str) {
        return inSpecificScope(str, TagSearchTableScope, (String[]) null);
    }

    boolean inSelectScope(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            String strNormalName = this.stack.get(size).normalName();
            if (strNormalName.equals(str)) {
                return true;
            }
            if (!StringUtil.inSorted(strNormalName, TagSearchSelectScope)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    boolean onStackNot(String[] strArr) {
        int size = this.stack.size();
        int i = size - 1;
        int i2 = i > 100 ? size - 101 : 0;
        while (i >= i2) {
            if (!StringUtil.inSorted(this.stack.get(i).normalName(), strArr)) {
                return true;
            }
            i--;
        }
        return false;
    }

    void setHeadElement(Element element) {
        this.headElement = element;
    }

    Element getHeadElement() {
        return this.headElement;
    }

    boolean isFosterInserts() {
        return this.fosterInserts;
    }

    void setFosterInserts(boolean z) {
        this.fosterInserts = z;
    }

    FormElement getFormElement() {
        return this.formElement;
    }

    void setFormElement(FormElement formElement) {
        this.formElement = formElement;
    }

    void resetPendingTableCharacters() {
        this.pendingTableCharacters.clear();
    }

    List<Token.Character> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    void addPendingTableCharacters(Token.Character character) {
        this.pendingTableCharacters.add(new Token.Character(character));
    }

    void generateImpliedEndTags(String str) {
        while (StringUtil.inSorted(currentElement().normalName(), TagSearchEndTags)) {
            if (str != null && currentElementIs(str)) {
                return;
            } else {
                pop();
            }
        }
    }

    void generateImpliedEndTags() {
        generateImpliedEndTags(false);
    }

    void generateImpliedEndTags(boolean z) {
        String[] strArr = z ? TagThoroughSearchEndTags : TagSearchEndTags;
        while (Parser.NamespaceHtml.equals(currentElement().tag().namespace()) && StringUtil.inSorted(currentElement().normalName(), strArr)) {
            pop();
        }
    }

    void closeElement(String str) {
        generateImpliedEndTags(str);
        if (!str.equals(currentElement().normalName())) {
            error(state());
        }
        popStackToClose(str);
    }

    static boolean isSpecial(Element element) {
        String strNamespace = element.tag().namespace();
        String strNormalName = element.normalName();
        strNamespace.hashCode();
        switch (strNamespace) {
            case "http://www.w3.org/1999/xhtml":
                return StringUtil.inSorted(strNormalName, TagSearchSpecial);
            case "http://www.w3.org/2000/svg":
                return StringUtil.inSorted(strNormalName, TagSvgHtmlIntegration);
            case "http://www.w3.org/1998/Math/MathML":
                return StringUtil.inSorted(strNormalName, TagSearchSpecialMath);
            default:
                return false;
        }
    }

    Element lastFormattingElement() {
        if (this.formattingElements.size() <= 0) {
            return null;
        }
        ArrayList<Element> arrayList = this.formattingElements;
        return arrayList.get(arrayList.size() - 1);
    }

    int positionOfElement(Element element) {
        for (int i = 0; i < this.formattingElements.size(); i++) {
            if (element == this.formattingElements.get(i)) {
                return i;
            }
        }
        return -1;
    }

    Element removeLastFormattingElement() {
        int size = this.formattingElements.size();
        if (size > 0) {
            return this.formattingElements.remove(size - 1);
        }
        return null;
    }

    void pushActiveFormattingElements(Element element) {
        checkActiveFormattingElements(element);
        this.formattingElements.add(element);
    }

    void pushWithBookmark(Element element, int i) {
        checkActiveFormattingElements(element);
        try {
            this.formattingElements.add(i, element);
        } catch (IndexOutOfBoundsException unused) {
            this.formattingElements.add(element);
        }
    }

    void checkActiveFormattingElements(Element element) {
        int size = this.formattingElements.size();
        int i = size - 13;
        int i2 = 0;
        if (i < 0) {
            i = 0;
        }
        for (int i3 = size - 1; i3 >= i; i3--) {
            Element element2 = this.formattingElements.get(i3);
            if (element2 == null) {
                return;
            }
            if (isSameFormattingElement(element, element2)) {
                i2++;
            }
            if (i2 == 3) {
                this.formattingElements.remove(i3);
                return;
            }
        }
    }

    private static boolean isSameFormattingElement(Element element, Element element2) {
        return element.normalName().equals(element2.normalName()) && element.attributes().equals(element2.attributes());
    }

    void reconstructFormattingElements() {
        Element elementLastFormattingElement;
        if (this.stack.size() > 256 || (elementLastFormattingElement = lastFormattingElement()) == null || onStack(elementLastFormattingElement)) {
            return;
        }
        int size = this.formattingElements.size();
        int i = size - 12;
        if (i < 0) {
            i = 0;
        }
        boolean z = true;
        int i2 = size - 1;
        int i3 = i2;
        while (i3 != i) {
            i3--;
            elementLastFormattingElement = this.formattingElements.get(i3);
            if (elementLastFormattingElement == null || onStack(elementLastFormattingElement)) {
                z = false;
                break;
            }
        }
        while (true) {
            if (!z) {
                i3++;
                elementLastFormattingElement = this.formattingElements.get(i3);
            }
            Validate.notNull(elementLastFormattingElement);
            Element element = new Element(tagFor(elementLastFormattingElement.nodeName(), elementLastFormattingElement.normalName(), defaultNamespace(), this.settings), null, elementLastFormattingElement.attributes().clone());
            doInsertElement(element);
            this.formattingElements.set(i3, element);
            if (i3 == i2) {
                return;
            } else {
                z = false;
            }
        }
    }

    void clearFormattingElementsToLastMarker() {
        while (!this.formattingElements.isEmpty() && removeLastFormattingElement() != null) {
        }
    }

    void removeFromActiveFormattingElements(Element element) {
        for (int size = this.formattingElements.size() - 1; size >= 0; size--) {
            if (this.formattingElements.get(size) == element) {
                this.formattingElements.remove(size);
                return;
            }
        }
    }

    boolean isInActiveFormattingElements(Element element) {
        return onStack(this.formattingElements, element);
    }

    Element getActiveFormattingElement(String str) {
        for (int size = this.formattingElements.size() - 1; size >= 0; size--) {
            Element element = this.formattingElements.get(size);
            if (element == null) {
                return null;
            }
            if (element.nameIs(str)) {
                return element;
            }
        }
        return null;
    }

    void replaceActiveFormattingElement(Element element, Element element2) {
        replaceInQueue(this.formattingElements, element, element2);
    }

    void insertMarkerToFormattingElements() {
        this.formattingElements.add(null);
    }

    void insertInFosterParent(Node node) {
        Element elementAboveOnStack;
        Element fromStack = getFromStack(HtmlParser.TableElement.TAG);
        boolean z = false;
        if (fromStack != null) {
            if (fromStack.parent() != null) {
                elementAboveOnStack = fromStack.parent();
                z = true;
            } else {
                elementAboveOnStack = aboveOnStack(fromStack);
            }
        } else {
            elementAboveOnStack = this.stack.get(0);
        }
        if (z) {
            Validate.notNull(fromStack);
            fromStack.before(node);
        } else {
            elementAboveOnStack.appendChild(node);
        }
    }

    void pushTemplateMode(HtmlTreeBuilderState htmlTreeBuilderState) {
        this.tmplInsertMode.add(htmlTreeBuilderState);
    }

    HtmlTreeBuilderState popTemplateMode() {
        if (this.tmplInsertMode.size() <= 0) {
            return null;
        }
        ArrayList<HtmlTreeBuilderState> arrayList = this.tmplInsertMode;
        return arrayList.remove(arrayList.size() - 1);
    }

    int templateModeSize() {
        return this.tmplInsertMode.size();
    }

    HtmlTreeBuilderState currentTemplateMode() {
        if (this.tmplInsertMode.size() <= 0) {
            return null;
        }
        ArrayList<HtmlTreeBuilderState> arrayList = this.tmplInsertMode;
        return arrayList.get(arrayList.size() - 1);
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + currentElement() + CoreConstants.CURLY_RIGHT;
    }
}
