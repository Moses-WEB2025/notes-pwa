package org.jsoup.select;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jsoup.internal.Functions;
import org.jsoup.internal.SoftPool;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeIterator;
import org.jsoup.nodes.TextNode;

/* JADX INFO: loaded from: classes6.dex */
abstract class StructuralEvaluator extends Evaluator {
    final Evaluator evaluator;
    final ThreadLocal<IdentityHashMap<Node, IdentityHashMap<Node, Boolean>>> threadMemo = ThreadLocal.withInitial(new Supplier() { // from class: org.jsoup.select.StructuralEvaluator$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return StructuralEvaluator.$r8$lambda$g1bKX7VgjnMTK9HK56GvBI6Fqxo();
        }
    });
    boolean wantsNodes;

    public static /* synthetic */ IdentityHashMap $r8$lambda$g1bKX7VgjnMTK9HK56GvBI6Fqxo() {
        return new IdentityHashMap();
    }

    abstract boolean evaluateMatch(Element element, Node node);

    public StructuralEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
        this.wantsNodes = evaluator.wantsNodes();
    }

    @Override // org.jsoup.select.Evaluator
    boolean wantsNodes() {
        return this.wantsNodes;
    }

    boolean memoMatches(final Element element, Node node) {
        return this.threadMemo.get().computeIfAbsent(element, Functions.identityMapFunction()).computeIfAbsent(node, new Function() { // from class: org.jsoup.select.StructuralEvaluator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4594lambda$memoMatches$0$orgjsoupselectStructuralEvaluator(element, (Node) obj);
            }
        }).booleanValue();
    }

    /* JADX INFO: renamed from: lambda$memoMatches$0$org-jsoup-select-StructuralEvaluator, reason: not valid java name */
    /* synthetic */ Boolean m4594lambda$memoMatches$0$orgjsoupselectStructuralEvaluator(Element element, Node node) {
        return Boolean.valueOf(this.evaluator.m4592lambda$asNodePredicate$1$orgjsoupselectEvaluator(element, node));
    }

    @Override // org.jsoup.select.Evaluator
    protected void reset() {
        this.threadMemo.get().clear();
        this.evaluator.reset();
        super.reset();
    }

    @Override // org.jsoup.select.Evaluator
    /* JADX INFO: renamed from: matches */
    public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
        return evaluateMatch(element, element2);
    }

    @Override // org.jsoup.select.Evaluator
    boolean matches(Element element, LeafNode leafNode) {
        return evaluateMatch(element, leafNode);
    }

    static class Root extends Evaluator {
        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return 1;
        }

        @Override // org.jsoup.select.Evaluator
        /* JADX INFO: renamed from: matches */
        public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
            return element == element2;
        }

        Root() {
        }

        public String toString() {
            return ">";
        }
    }

    static class Has extends StructuralEvaluator {
        static final SoftPool<NodeIterator<Node>> NodeIterPool = new SoftPool<>(new Supplier() { // from class: org.jsoup.select.StructuralEvaluator$Has$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return StructuralEvaluator.Has.lambda$static$0();
            }
        });
        private final boolean checkSiblings;

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            return false;
        }

        static /* synthetic */ NodeIterator lambda$static$0() {
            return new NodeIterator(new TextNode(""), Node.class);
        }

        public Has(Evaluator evaluator) {
            super(evaluator);
            this.checkSiblings = evalWantsSiblings(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator, org.jsoup.select.Evaluator
        /* JADX INFO: renamed from: matches */
        public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
            if (this.checkSiblings) {
                for (Element elementFirstElementSibling = element2.firstElementSibling(); elementFirstElementSibling != null; elementFirstElementSibling = elementFirstElementSibling.nextElementSibling()) {
                    if (elementFirstElementSibling != element2 && this.evaluator.m4593lambda$asPredicate$0$orgjsoupselectEvaluator(element2, elementFirstElementSibling)) {
                        return true;
                    }
                }
            }
            NodeIterator<Node> nodeIteratorBorrow = NodeIterPool.borrow();
            nodeIteratorBorrow.restart(element2);
            while (nodeIteratorBorrow.hasNext()) {
                try {
                    Node next = nodeIteratorBorrow.next();
                    if (next != element2 && this.evaluator.m4592lambda$asNodePredicate$1$orgjsoupselectEvaluator(element2, next)) {
                        return true;
                    }
                } finally {
                    NodeIterPool.release(nodeIteratorBorrow);
                }
            }
            NodeIterPool.release(nodeIteratorBorrow);
            return false;
        }

        private static boolean evalWantsSiblings(Evaluator evaluator) {
            if (!(evaluator instanceof CombiningEvaluator)) {
                return false;
            }
            for (Evaluator evaluator2 : ((CombiningEvaluator) evaluator).evaluators) {
                if ((evaluator2 instanceof PreviousSibling) || (evaluator2 instanceof ImmediatePreviousSibling)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() * 10;
        }

        public String toString() {
            return String.format(":has(%s)", this.evaluator);
        }
    }

    static class Is extends StructuralEvaluator {
        public Is(Evaluator evaluator) {
            super(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            return this.evaluator.m4592lambda$asNodePredicate$1$orgjsoupselectEvaluator(element, node);
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() + 2;
        }

        public String toString() {
            return String.format(":is(%s)", this.evaluator);
        }
    }

    static class Not extends StructuralEvaluator {
        public Not(Evaluator evaluator) {
            super(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            return !memoMatches(element, node);
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() + 2;
        }

        public String toString() {
            return String.format(":not(%s)", this.evaluator);
        }
    }

    static class Ancestor extends StructuralEvaluator {
        public Ancestor(Evaluator evaluator) {
            super(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            if (element == node) {
                return false;
            }
            do {
                node = node.parent();
                if (node == null) {
                    break;
                }
                if (memoMatches(element, node)) {
                    return true;
                }
            } while (node != element);
            return false;
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() * 8;
        }

        public String toString() {
            return String.format("%s ", this.evaluator);
        }
    }

    static class ImmediateParentRun extends StructuralEvaluator {
        int cost;
        final ArrayList<Evaluator> evaluators;

        public ImmediateParentRun(Evaluator evaluator) {
            super(evaluator);
            ArrayList<Evaluator> arrayList = new ArrayList<>();
            this.evaluators = arrayList;
            this.cost = 2;
            arrayList.add(evaluator);
            this.cost += evaluator.cost();
        }

        void add(Evaluator evaluator) {
            this.evaluators.add(evaluator);
            this.cost += evaluator.cost();
            this.wantsNodes = evaluator.wantsNodes() | this.wantsNodes;
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            if (node == element) {
                return false;
            }
            for (int size = this.evaluators.size() - 1; size >= 0; size--) {
                if (node == null || !this.evaluators.get(size).m4592lambda$asNodePredicate$1$orgjsoupselectEvaluator(element, node)) {
                    return false;
                }
                node = node.parent();
            }
            return true;
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.cost;
        }

        @Override // org.jsoup.select.StructuralEvaluator, org.jsoup.select.Evaluator
        protected void reset() {
            Iterator<Evaluator> it = this.evaluators.iterator();
            while (it.hasNext()) {
                it.next().reset();
            }
            super.reset();
        }

        public String toString() {
            return StringUtil.join(this.evaluators, " > ");
        }
    }

    static class PreviousSibling extends StructuralEvaluator {
        public PreviousSibling(Evaluator evaluator) {
            super(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            if (element == node) {
                return false;
            }
            for (Node nodeFirstSibling = node.firstSibling(); nodeFirstSibling != null && nodeFirstSibling != node; nodeFirstSibling = nodeFirstSibling.nextSibling()) {
                if (memoMatches(element, nodeFirstSibling)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() * 3;
        }

        public String toString() {
            return String.format("%s ~ ", this.evaluator);
        }
    }

    static class ImmediatePreviousSibling extends StructuralEvaluator {
        public ImmediatePreviousSibling(Evaluator evaluator) {
            super(evaluator);
        }

        @Override // org.jsoup.select.StructuralEvaluator
        boolean evaluateMatch(Element element, Node node) {
            if (element == node) {
                return false;
            }
            Node nodePreviousSibling = this.wantsNodes ? node.previousSibling() : node.previousElementSibling();
            return nodePreviousSibling != null && memoMatches(element, nodePreviousSibling);
        }

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return this.evaluator.cost() + 2;
        }

        public String toString() {
            return String.format("%s + ", this.evaluator);
        }
    }
}
