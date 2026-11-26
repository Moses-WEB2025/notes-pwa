package org.jsoup.helper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes6.dex */
public class W3CDom {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String ContextNodeProperty = "jsoupContextNode";
    private static final String ContextProperty = "jsoupContextSource";
    public static final String SourceProperty = "jsoupSource";
    public static final String XPathFactoryProperty = "javax.xml.xpath.XPathFactory:jsoup";
    protected DocumentBuilderFactory factory;
    private boolean namespaceAware = true;

    public W3CDom() {
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        this.factory = documentBuilderFactoryNewInstance;
        documentBuilderFactoryNewInstance.setNamespaceAware(true);
    }

    public boolean namespaceAware() {
        return this.namespaceAware;
    }

    public W3CDom namespaceAware(boolean z) {
        this.namespaceAware = z;
        this.factory.setNamespaceAware(z);
        return this;
    }

    public static Document convert(org.jsoup.nodes.Document document) {
        return new W3CDom().fromJsoup(document);
    }

    public static String asString(Document document, Map<String, String> map) {
        try {
            DOMSource dOMSource = new DOMSource(document);
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            if (map != null) {
                transformerNewTransformer.setOutputProperties(propertiesFromMap(map));
            }
            if (document.getDoctype() != null) {
                DocumentType doctype = document.getDoctype();
                if (!StringUtil.isBlank(doctype.getPublicId())) {
                    transformerNewTransformer.setOutputProperty(XMLWriter.DOCTYPE_PUBLIC, doctype.getPublicId());
                }
                if (!StringUtil.isBlank(doctype.getSystemId())) {
                    transformerNewTransformer.setOutputProperty(XMLWriter.DOCTYPE_SYSTEM, doctype.getSystemId());
                } else if (doctype.getName().equalsIgnoreCase("html") && StringUtil.isBlank(doctype.getPublicId()) && StringUtil.isBlank(doctype.getSystemId())) {
                    transformerNewTransformer.setOutputProperty(XMLWriter.DOCTYPE_SYSTEM, "about:legacy-compat");
                }
            }
            transformerNewTransformer.transform(dOMSource, streamResult);
            return stringWriter.toString();
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }

    static Properties propertiesFromMap(Map<String, String> map) {
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    public static HashMap<String, String> OutputHtml() {
        return methodMap("html");
    }

    public static HashMap<String, String> OutputXml() {
        return methodMap(StringLookupFactory.KEY_XML);
    }

    private static HashMap<String, String> methodMap(String str) {
        HashMap<String, String> map = new HashMap<>();
        map.put(XMLWriter.METHOD, str);
        return map;
    }

    public Document fromJsoup(org.jsoup.nodes.Document document) {
        return fromJsoup((Element) document);
    }

    public Document fromJsoup(Element element) {
        Validate.notNull(element);
        try {
            DocumentBuilder documentBuilderNewDocumentBuilder = this.factory.newDocumentBuilder();
            DOMImplementation dOMImplementation = documentBuilderNewDocumentBuilder.getDOMImplementation();
            Document documentNewDocument = documentBuilderNewDocumentBuilder.newDocument();
            org.jsoup.nodes.Document documentOwnerDocument = element.ownerDocument();
            org.jsoup.nodes.DocumentType documentType = documentOwnerDocument != null ? documentOwnerDocument.documentType() : null;
            if (documentType != null) {
                try {
                    documentNewDocument.appendChild(dOMImplementation.createDocumentType(documentType.name(), documentType.publicId(), documentType.systemId()));
                } catch (DOMException unused) {
                }
            }
            documentNewDocument.setXmlStandalone(true);
            documentNewDocument.setUserData(ContextProperty, element instanceof org.jsoup.nodes.Document ? element.firstElementChild() : element, null);
            if (documentOwnerDocument != null) {
                element = documentOwnerDocument;
            }
            convert(element, documentNewDocument);
            return documentNewDocument;
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public void convert(org.jsoup.nodes.Document document, Document document2) {
        convert((Element) document, document2);
    }

    public void convert(Element element, Document document) {
        W3CBuilder w3CBuilder = new W3CBuilder(document);
        w3CBuilder.namespaceAware = this.namespaceAware;
        org.jsoup.nodes.Document documentOwnerDocument = element.ownerDocument();
        if (documentOwnerDocument != null) {
            if (!StringUtil.isBlank(documentOwnerDocument.location())) {
                document.setDocumentURI(documentOwnerDocument.location());
            }
            w3CBuilder.syntax = documentOwnerDocument.outputSettings().syntax();
        }
        if (element instanceof org.jsoup.nodes.Document) {
            element = element.firstElementChild();
        }
        w3CBuilder.traverse(element);
    }

    public NodeList selectXpath(String str, Document document) {
        return selectXpath(str, (Node) document);
    }

    public NodeList selectXpath(String str, Node node) {
        XPathFactory xPathFactoryNewInstance;
        Validate.notEmptyParam(str, "xpath");
        Validate.notNullParam(node, "contextNode");
        try {
            if (System.getProperty(XPathFactoryProperty) != null) {
                xPathFactoryNewInstance = XPathFactory.newInstance("jsoup");
            } else {
                xPathFactoryNewInstance = XPathFactory.newInstance();
            }
            NodeList nodeList = (NodeList) xPathFactoryNewInstance.newXPath().compile(str).evaluate(node, XPathConstants.NODESET);
            Validate.notNull(nodeList);
            return nodeList;
        } catch (XPathExpressionException | XPathFactoryConfigurationException e) {
            throw new Selector.SelectorParseException(e, "Could not evaluate XPath query [%s]: %s", str, e.getMessage());
        }
    }

    public <T extends org.jsoup.nodes.Node> List<T> sourceNodes(NodeList nodeList, Class<T> cls) {
        Validate.notNull(nodeList);
        Validate.notNull(cls);
        ArrayList arrayList = new ArrayList(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Object userData = nodeList.item(i).getUserData(SourceProperty);
            if (cls.isInstance(userData)) {
                arrayList.add(cls.cast(userData));
            }
        }
        return arrayList;
    }

    public Node contextNode(Document document) {
        return (Node) document.getUserData(ContextNodeProperty);
    }

    public String asString(Document document) {
        return asString(document, null);
    }

    protected static class W3CBuilder implements NodeVisitor {
        private static final String undefinedNs = "undefined";
        private final Element contextElement;
        private Node dest;
        private final Document doc;
        private boolean namespaceAware = true;
        private org.jsoup.nodes.Document.OutputSettings.Syntax syntax = org.jsoup.nodes.Document.OutputSettings.Syntax.xml;

        public W3CBuilder(Document document) {
            this.doc = document;
            this.dest = document;
            this.contextElement = (Element) document.getUserData(W3CDom.ContextProperty);
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(org.jsoup.nodes.Node node, int i) {
            if (node instanceof Element) {
                Element element = (Element) node;
                String strNamespace = this.namespaceAware ? element.tag().namespace() : null;
                String strXmlSafeTagName = Normalizer.xmlSafeTagName(element.tagName());
                if (strNamespace == null) {
                    try {
                        if (strXmlSafeTagName.contains(":")) {
                            strNamespace = "";
                        }
                    } catch (DOMException unused) {
                        append(this.doc.createTextNode("<" + strXmlSafeTagName + ">"), element);
                        return;
                    }
                }
                org.w3c.dom.Element elementCreateElementNS = this.doc.createElementNS(strNamespace, strXmlSafeTagName);
                copyAttributes(element, elementCreateElementNS);
                append(elementCreateElementNS, element);
                if (element == this.contextElement) {
                    this.doc.setUserData(W3CDom.ContextNodeProperty, elementCreateElementNS, null);
                }
                this.dest = elementCreateElementNS;
                return;
            }
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                append(this.doc.createTextNode(textNode.getWholeText()), textNode);
            } else if (node instanceof Comment) {
                Comment comment = (Comment) node;
                append(this.doc.createComment(comment.getData()), comment);
            } else if (node instanceof DataNode) {
                DataNode dataNode = (DataNode) node;
                append(this.doc.createTextNode(dataNode.getWholeData()), dataNode);
            }
        }

        private void append(Node node, org.jsoup.nodes.Node node2) {
            node.setUserData(W3CDom.SourceProperty, node2, null);
            this.dest.appendChild(node);
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(org.jsoup.nodes.Node node, int i) {
            if ((node instanceof Element) && (this.dest.getParentNode() instanceof org.w3c.dom.Element)) {
                this.dest = this.dest.getParentNode();
            }
        }

        private void copyAttributes(Element element, org.w3c.dom.Element element2) {
            for (Attribute attribute : element.attributes()) {
                try {
                    setAttribute(element, element2, attribute, this.syntax);
                } catch (DOMException unused) {
                    if (this.syntax != org.jsoup.nodes.Document.OutputSettings.Syntax.xml) {
                        setAttribute(element, element2, attribute, org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
                    }
                }
            }
        }

        private void setAttribute(Element element, org.w3c.dom.Element element2, Attribute attribute, org.jsoup.nodes.Document.OutputSettings.Syntax syntax) throws DOMException {
            String validKey = Attribute.getValidKey(attribute.getKey(), syntax);
            if (validKey != null) {
                String strNamespace = attribute.namespace();
                if (this.namespaceAware && !strNamespace.isEmpty()) {
                    element2.setAttributeNS(strNamespace, validKey, attribute.getValue());
                } else {
                    element2.setAttribute(validKey, attribute.getValue());
                }
                maybeAddUndeclaredNs(strNamespace, validKey, element, element2);
            }
        }

        private void maybeAddUndeclaredNs(String str, String str2, Element element, org.w3c.dom.Element element2) {
            int iIndexOf;
            if (this.namespaceAware && str.isEmpty() && (iIndexOf = str2.indexOf(58)) != -1) {
                String strSubstring = str2.substring(0, iIndexOf);
                if (strSubstring.equals("xmlns")) {
                    return;
                }
                org.jsoup.nodes.Document documentOwnerDocument = element.ownerDocument();
                if (documentOwnerDocument != null && (documentOwnerDocument.parser().getTreeBuilder() instanceof HtmlTreeBuilder)) {
                    for (Element elementParent = element; elementParent != null; elementParent = elementParent.parent()) {
                        String strAttr = elementParent.attr("xmlns:" + strSubstring);
                        if (!strAttr.isEmpty()) {
                            element2.setAttributeNS(strAttr, str2, element.attr(str2));
                            return;
                        }
                    }
                }
                element2.setAttribute("xmlns:" + strSubstring, undefinedNs);
            }
        }
    }
}
