package org.jsoup.select;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.parser.TokenQueue;

/* JADX INFO: loaded from: classes6.dex */
public class Selector {
    private Selector() {
    }

    public static Elements select(String str, Element element) {
        Validate.notEmpty(str);
        return select(evaluatorOf(str), element);
    }

    public static Elements select(Evaluator evaluator, Element element) {
        Validate.notNull(evaluator);
        Validate.notNull(element);
        return Collector.collect(evaluator, element);
    }

    public static Stream<Element> selectStream(String str, Element element) {
        Validate.notEmpty(str);
        return selectStream(evaluatorOf(str), element);
    }

    public static Stream<Element> selectStream(Evaluator evaluator, Element element) {
        Validate.notNull(evaluator);
        Validate.notNull(element);
        return Collector.stream(evaluator, element);
    }

    public static Elements select(String str, Iterable<Element> iterable) {
        Validate.notEmpty(str);
        Validate.notNull(iterable);
        Evaluator evaluatorEvaluatorOf = evaluatorOf(str);
        final Elements elements = new Elements();
        final HashSet hashSet = new HashSet();
        Iterator<Element> it = iterable.iterator();
        while (it.hasNext()) {
            Stream<Element> streamSelectStream = selectStream(evaluatorEvaluatorOf, it.next());
            Objects.requireNonNull(hashSet);
            Stream<Element> streamFilter = streamSelectStream.filter(new Predicate() { // from class: org.jsoup.select.Selector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return hashSet.add((Element) obj);
                }
            });
            Objects.requireNonNull(elements);
            streamFilter.forEach(new Consumer() { // from class: org.jsoup.select.Selector$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    elements.add((Element) obj);
                }
            });
        }
        return elements;
    }

    static Elements filterOut(Collection<Element> collection, Collection<Element> collection2) {
        Elements elements = new Elements();
        for (Element element : collection) {
            Iterator<Element> it = collection2.iterator();
            do {
                if (!it.hasNext()) {
                    elements.add(element);
                    break;
                }
            } while (!element.equals(it.next()));
        }
        return elements;
    }

    public static Element selectFirst(String str, Element element) {
        Validate.notEmpty(str);
        return Collector.findFirst(evaluatorOf(str), element);
    }

    public static Element selectFirst(String str, Iterable<Element> iterable) {
        Validate.notEmpty(str);
        Validate.notNull(iterable);
        Evaluator evaluatorEvaluatorOf = evaluatorOf(str);
        Iterator<Element> it = iterable.iterator();
        while (it.hasNext()) {
            Element elementFindFirst = Collector.findFirst(evaluatorEvaluatorOf, it.next());
            if (elementFindFirst != null) {
                return elementFindFirst;
            }
        }
        return null;
    }

    public static String escapeCssIdentifier(String str) {
        return TokenQueue.escapeCssIdentifier(str);
    }

    public static String unescapeCssIdentifier(String str) {
        TokenQueue tokenQueue = new TokenQueue(str);
        try {
            String strConsumeCssIdentifier = tokenQueue.consumeCssIdentifier();
            tokenQueue.close();
            return strConsumeCssIdentifier;
        } catch (Throwable th) {
            try {
                tokenQueue.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static Evaluator evaluatorOf(String str) {
        return QueryParser.parse(str);
    }

    public static class SelectorParseException extends IllegalStateException {
        public SelectorParseException(String str) {
            super(str);
        }

        public SelectorParseException(String str, Object... objArr) {
            super(String.format(str, objArr));
        }

        public SelectorParseException(Throwable th, String str, Object... objArr) {
            super(String.format(str, objArr), th);
        }
    }
}
