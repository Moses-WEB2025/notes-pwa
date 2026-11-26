package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/* JADX INFO: loaded from: classes6.dex */
public class Elements extends Nodes<Element> {
    public Elements() {
    }

    public Elements(int i) {
        super(i);
    }

    public Elements(Collection<Element> collection) {
        super(collection);
    }

    public Elements(List<Element> list) {
        super((List) list);
    }

    public Elements(Element... elementArr) {
        super(Arrays.asList(elementArr));
    }

    @Override // org.jsoup.select.Nodes, java.util.ArrayList
    public Elements clone() {
        Elements elements = new Elements(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            elements.add(((Element) it.next()).mo4587clone());
        }
        return elements;
    }

    @Override // org.jsoup.select.Nodes
    public ArrayList<Element> asList() {
        return new ArrayList<>(this);
    }

    public String attr(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.hasAttr(str)) {
                return element.attr(str);
            }
        }
        return "";
    }

    public boolean hasAttr(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasAttr(str)) {
                return true;
            }
        }
        return false;
    }

    public List<String> eachAttr(String str) {
        ArrayList arrayList = new ArrayList(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.hasAttr(str)) {
                arrayList.add(element.attr(str));
            }
        }
        return arrayList;
    }

    public Elements attr(String str, String str2) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).attr(str, str2);
        }
        return this;
    }

    public Elements removeAttr(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).removeAttr(str);
        }
        return this;
    }

    public Elements addClass(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).addClass(str);
        }
        return this;
    }

    public Elements removeClass(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).removeClass(str);
        }
        return this;
    }

    public Elements toggleClass(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).toggleClass(str);
        }
        return this;
    }

    public boolean hasClass(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasClass(str)) {
                return true;
            }
        }
        return false;
    }

    public String val() {
        if (size() > 0) {
            return first().val();
        }
        return "";
    }

    public Elements val(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).val(str);
        }
        return this;
    }

    public String text() {
        return (String) stream().map(new Function() { // from class: org.jsoup.select.Elements$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Element) obj).text();
            }
        }).collect(StringUtil.joining(StringUtils.SPACE));
    }

    public boolean hasText() {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasText()) {
                return true;
            }
        }
        return false;
    }

    public List<String> eachText() {
        ArrayList arrayList = new ArrayList(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.hasText()) {
                arrayList.add(element.text());
            }
        }
        return arrayList;
    }

    public String html() {
        return (String) stream().map(new Function() { // from class: org.jsoup.select.Elements$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Element) obj).html();
            }
        }).collect(StringUtil.joining(StringUtils.LF));
    }

    public Elements tagName(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).tagName(str);
        }
        return this;
    }

    public Elements html(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).html(str);
        }
        return this;
    }

    public Elements prepend(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).prepend(str);
        }
        return this;
    }

    public Elements append(String str) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).append(str);
        }
        return this;
    }

    @Override // org.jsoup.select.Nodes
    public Elements before(String str) {
        super.before(str);
        return this;
    }

    @Override // org.jsoup.select.Nodes
    public Elements after(String str) {
        super.after(str);
        return this;
    }

    @Override // org.jsoup.select.Nodes
    public Elements wrap(String str) {
        super.wrap(str);
        return this;
    }

    public Elements unwrap() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).unwrap();
        }
        return this;
    }

    public Elements empty() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).empty();
        }
        return this;
    }

    @Override // org.jsoup.select.Nodes
    public Elements remove() {
        super.remove();
        return this;
    }

    public Elements select(String str) {
        return Selector.select(str, this);
    }

    public Element selectFirst(String str) {
        return Selector.selectFirst(str, this);
    }

    public Element expectFirst(String str) {
        return (Element) Validate.expectNotNull(Selector.selectFirst(str, this), "No elements matched the query '%s' in the elements.", str);
    }

    public Elements not(String str) {
        return Selector.filterOut(this, Selector.select(str, this));
    }

    public Elements eq(int i) {
        return size() > i ? new Elements((Element) get(i)) : new Elements();
    }

    public boolean is(String str) {
        Evaluator evaluatorEvaluatorOf = Selector.evaluatorOf(str);
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).is(evaluatorEvaluatorOf)) {
                return true;
            }
        }
        return false;
    }

    public Elements next() {
        return siblings(null, true, false);
    }

    public Elements next(String str) {
        return siblings(str, true, false);
    }

    public Elements nextAll() {
        return siblings(null, true, true);
    }

    public Elements nextAll(String str) {
        return siblings(str, true, true);
    }

    public Elements prev() {
        return siblings(null, false, false);
    }

    public Elements prev(String str) {
        return siblings(str, false, false);
    }

    public Elements prevAll() {
        return siblings(null, false, true);
    }

    public Elements prevAll(String str) {
        return siblings(str, false, true);
    }

    private Elements siblings(String str, boolean z, boolean z2) {
        Elements elements = new Elements();
        Evaluator evaluatorEvaluatorOf = str != null ? Selector.evaluatorOf(str) : null;
        Iterator it = iterator();
        while (it.hasNext()) {
            Element elementNextElementSibling = (Element) it.next();
            do {
                elementNextElementSibling = z ? elementNextElementSibling.nextElementSibling() : elementNextElementSibling.previousElementSibling();
                if (elementNextElementSibling == null) {
                    break;
                }
                if (evaluatorEvaluatorOf == null || elementNextElementSibling.is(evaluatorEvaluatorOf)) {
                    elements.add(elementNextElementSibling);
                }
            } while (z2);
        }
        return elements;
    }

    public Elements parents() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = iterator();
        while (it.hasNext()) {
            linkedHashSet.addAll(((Element) it.next()).parents());
        }
        return new Elements(linkedHashSet);
    }

    @Override // org.jsoup.select.Nodes
    public Element first() {
        return (Element) super.first();
    }

    @Override // org.jsoup.select.Nodes
    public Element last() {
        return (Element) super.last();
    }

    public Elements traverse(NodeVisitor nodeVisitor) {
        NodeTraversor.traverse(nodeVisitor, this);
        return this;
    }

    public Elements filter(NodeFilter nodeFilter) {
        NodeTraversor.filter(nodeFilter, this);
        return this;
    }

    public List<FormElement> forms() {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element instanceof FormElement) {
                arrayList.add((FormElement) element);
            }
        }
        return arrayList;
    }

    public List<Comment> comments() {
        return childNodesOfType(Comment.class);
    }

    public List<TextNode> textNodes() {
        return childNodesOfType(TextNode.class);
    }

    public List<DataNode> dataNodes() {
        return childNodesOfType(DataNode.class);
    }

    private <T extends Node> List<T> childNodesOfType(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            for (int i = 0; i < element.childNodeSize(); i++) {
                Node nodeChildNode = element.childNode(i);
                if (cls.isInstance(nodeChildNode)) {
                    arrayList.add(cls.cast(nodeChildNode));
                }
            }
        }
        return arrayList;
    }

    @Override // org.jsoup.select.Nodes
    public Element set(int i, Element element) {
        return (Element) super.set(i, element);
    }

    @Override // org.jsoup.select.Nodes, java.util.ArrayList, java.util.AbstractList, java.util.List
    public Element remove(int i) {
        return (Element) super.remove(i);
    }

    @Override // org.jsoup.select.Nodes
    public Element deselect(int i) {
        return (Element) super.deselect(i);
    }
}
