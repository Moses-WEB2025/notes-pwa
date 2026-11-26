package org.jsoup.parser;

import defpackage.R2;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;

/* JADX INFO: loaded from: classes6.dex */
public class StreamParser implements Closeable {
    private Document document;
    private final ElementIterator it;
    private final Parser parser;
    private boolean stopped;
    private final TreeBuilder treeBuilder;

    public StreamParser(Parser parser) {
        ElementIterator elementIterator = new ElementIterator();
        this.it = elementIterator;
        this.stopped = false;
        this.parser = parser;
        TreeBuilder treeBuilder = parser.getTreeBuilder();
        this.treeBuilder = treeBuilder;
        treeBuilder.nodeListener(elementIterator);
    }

    public StreamParser parse(Reader reader, String str) {
        close();
        this.it.reset();
        this.treeBuilder.initialiseParse(reader, str, this.parser);
        this.document = this.treeBuilder.doc;
        return this;
    }

    public StreamParser parse(String str, String str2) {
        return parse(new StringReader(str), str2);
    }

    public StreamParser parseFragment(Reader reader, Element element, String str) {
        parse(reader, str);
        this.treeBuilder.initialiseParseFragment(element);
        return this;
    }

    public StreamParser parseFragment(String str, Element element, String str2) {
        return parseFragment(new StringReader(str), element, str2);
    }

    public Stream<Element> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.it, R2.attr.actionBarRemoveBlacklistIcon), false);
    }

    public Iterator<Element> iterator() {
        return this.it;
    }

    public StreamParser stop() {
        this.stopped = true;
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.treeBuilder.completeParse();
    }

    public Document document() {
        Document document = this.treeBuilder.doc;
        this.document = document;
        Validate.notNull(document, "Must run parse() before calling.");
        return this.document;
    }

    public Document complete() throws IOException {
        Document document = document();
        this.treeBuilder.runParser();
        return document;
    }

    public List<Node> completeFragment() throws IOException {
        this.treeBuilder.runParser();
        return this.treeBuilder.completeParseFragment();
    }

    public Element selectFirst(String str) throws IOException {
        return selectFirst(Selector.evaluatorOf(str));
    }

    public Element expectFirst(String str) throws IOException {
        return (Element) Validate.expectNotNull(selectFirst(str), "No elements matched the query '%s' in the document.", str);
    }

    public Element selectFirst(Evaluator evaluator) throws IOException {
        Element elementSelectFirst = document().selectFirst(evaluator);
        return elementSelectFirst != null ? elementSelectFirst : selectNext(evaluator);
    }

    public Element selectNext(String str) throws IOException {
        return selectNext(Selector.evaluatorOf(str));
    }

    public Element expectNext(String str) throws IOException {
        return (Element) Validate.expectNotNull(selectNext(str), "No elements matched the query '%s' in the document.", str);
    }

    public Element selectNext(Evaluator evaluator) throws IOException {
        try {
            return stream().filter(evaluator.asPredicate(document())).findFirst().orElse(null);
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    final class ElementIterator implements Iterator<Element>, NodeVisitor {
        private Element current;
        private final Queue<Element> emitQueue = new LinkedList();
        private Element next;
        private Element tail;

        ElementIterator() {
        }

        void reset() {
            this.emitQueue.clear();
            this.tail = null;
            this.next = null;
            this.current = null;
            StreamParser.this.stopped = false;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            maybeFindNext();
            return this.next != null;
        }

        @Override // java.util.Iterator
        public Element next() {
            maybeFindNext();
            Element element = this.next;
            if (element == null) {
                throw new NoSuchElementException();
            }
            this.current = element;
            this.next = null;
            return element;
        }

        private void maybeFindNext() {
            if (StreamParser.this.stopped || this.next != null) {
                return;
            }
            if (this.emitQueue.isEmpty()) {
                while (StreamParser.this.treeBuilder.stepParser()) {
                    if (!this.emitQueue.isEmpty()) {
                        this.next = this.emitQueue.remove();
                        return;
                    }
                }
                StreamParser.this.stop();
                StreamParser.this.close();
                Element element = this.tail;
                if (element != null) {
                    this.next = element;
                    this.tail = null;
                    return;
                }
                return;
            }
            this.next = this.emitQueue.remove();
        }

        @Override // java.util.Iterator
        public void remove() {
            Element element = this.current;
            if (element == null) {
                throw new NoSuchElementException();
            }
            element.remove();
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i) {
            Element elementPreviousElementSibling;
            if (!(node instanceof Element) || (elementPreviousElementSibling = node.previousElementSibling()) == null) {
                return;
            }
            this.emitQueue.add(elementPreviousElementSibling);
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i) {
            if (node instanceof Element) {
                Element element = (Element) node;
                this.tail = element;
                Element elementLastElementChild = element.lastElementChild();
                if (elementLastElementChild != null) {
                    this.emitQueue.add(elementLastElementChild);
                }
            }
        }
    }
}
