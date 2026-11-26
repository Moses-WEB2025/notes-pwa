package org.jsoup.select;

import java.util.regex.Pattern;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
abstract class NodeEvaluator extends Evaluator {
    abstract boolean evaluateMatch(Node node);

    @Override // org.jsoup.select.Evaluator
    boolean wantsNodes() {
        return true;
    }

    NodeEvaluator() {
    }

    @Override // org.jsoup.select.Evaluator
    /* JADX INFO: renamed from: matches */
    public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
        return evaluateMatch(element2);
    }

    @Override // org.jsoup.select.Evaluator
    boolean matches(Element element, LeafNode leafNode) {
        return evaluateMatch(leafNode);
    }

    static class InstanceType extends NodeEvaluator {
        final String selector;
        final Class<? extends Node> type;

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return 1;
        }

        InstanceType(Class<? extends Node> cls, String str) {
            this.type = cls;
            this.selector = "::" + str;
        }

        @Override // org.jsoup.select.NodeEvaluator
        boolean evaluateMatch(Node node) {
            return this.type.isInstance(node);
        }

        public String toString() {
            return this.selector;
        }
    }

    static class ContainsValue extends NodeEvaluator {
        private final String searchText;

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return 6;
        }

        public ContainsValue(String str) {
            this.searchText = Normalizer.lowerCase(StringUtil.normaliseWhitespace(str));
        }

        @Override // org.jsoup.select.NodeEvaluator
        boolean evaluateMatch(Node node) {
            return Normalizer.lowerCase(node.nodeValue()).contains(this.searchText);
        }

        public String toString() {
            return String.format(":contains(%s)", this.searchText);
        }
    }

    static class BlankValue extends NodeEvaluator {
        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return 4;
        }

        BlankValue() {
        }

        @Override // org.jsoup.select.NodeEvaluator
        boolean evaluateMatch(Node node) {
            return StringUtil.isBlank(node.nodeValue());
        }

        public String toString() {
            return ":blank";
        }
    }

    static class MatchesValue extends NodeEvaluator {
        private final Pattern pattern;

        @Override // org.jsoup.select.Evaluator
        protected int cost() {
            return 8;
        }

        protected MatchesValue(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override // org.jsoup.select.NodeEvaluator
        boolean evaluateMatch(Node node) {
            return this.pattern.matcher(node.nodeValue()).find();
        }

        public String toString() {
            return String.format(":matches(%s)", this.pattern);
        }
    }
}
