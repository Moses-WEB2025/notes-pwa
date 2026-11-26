package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;

/* JADX INFO: loaded from: classes6.dex */
public abstract class CombiningEvaluator extends Evaluator {
    int cost;
    final ArrayList<Evaluator> evaluators;
    int num;
    final List<Evaluator> sortedEvaluators;
    boolean wantsNodes;

    CombiningEvaluator() {
        this.num = 0;
        this.cost = 0;
        this.evaluators = new ArrayList<>();
        this.sortedEvaluators = new ArrayList();
    }

    CombiningEvaluator(Collection<Evaluator> collection) {
        this();
        this.evaluators.addAll(collection);
        updateEvaluators();
    }

    public void add(Evaluator evaluator) {
        this.evaluators.add(evaluator);
        updateEvaluators();
    }

    @Override // org.jsoup.select.Evaluator
    protected void reset() {
        Iterator<Evaluator> it = this.evaluators.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        super.reset();
    }

    @Override // org.jsoup.select.Evaluator
    protected int cost() {
        return this.cost;
    }

    @Override // org.jsoup.select.Evaluator
    boolean wantsNodes() {
        return this.wantsNodes;
    }

    void updateEvaluators() {
        this.num = this.evaluators.size();
        this.cost = 0;
        Iterator<Evaluator> it = this.evaluators.iterator();
        while (it.hasNext()) {
            this.cost += it.next().cost();
        }
        this.sortedEvaluators.clear();
        this.sortedEvaluators.addAll(this.evaluators);
        this.sortedEvaluators.sort(Comparator.comparingInt(new ToIntFunction() { // from class: org.jsoup.select.CombiningEvaluator$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Evaluator) obj).cost();
            }
        }));
        Iterator<Evaluator> it2 = this.evaluators.iterator();
        while (it2.hasNext()) {
            if (it2.next().wantsNodes()) {
                this.wantsNodes = true;
                return;
            }
        }
    }

    public static final class And extends CombiningEvaluator {
        public And(Collection<Evaluator> collection) {
            super(collection);
        }

        And(Evaluator... evaluatorArr) {
            this(Arrays.asList(evaluatorArr));
        }

        @Override // org.jsoup.select.Evaluator
        /* JADX INFO: renamed from: matches */
        public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
            for (int i = 0; i < this.num; i++) {
                if (!this.sortedEvaluators.get(i).m4593lambda$asPredicate$0$orgjsoupselectEvaluator(element, element2)) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jsoup.select.Evaluator
        public boolean matches(Element element, LeafNode leafNode) {
            for (int i = 0; i < this.num; i++) {
                if (!this.sortedEvaluators.get(i).matches(element, leafNode)) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return StringUtil.join(this.evaluators, "");
        }
    }

    public static final class Or extends CombiningEvaluator {
        public Or(Collection<Evaluator> collection) {
            if (this.num > 1) {
                this.evaluators.add(new And(collection));
            } else {
                this.evaluators.addAll(collection);
            }
            updateEvaluators();
        }

        Or(Evaluator... evaluatorArr) {
            this(Arrays.asList(evaluatorArr));
        }

        Or() {
        }

        @Override // org.jsoup.select.Evaluator
        /* JADX INFO: renamed from: matches */
        public boolean m4593lambda$asPredicate$0$orgjsoupselectEvaluator(Element element, Element element2) {
            for (int i = 0; i < this.num; i++) {
                if (this.sortedEvaluators.get(i).m4593lambda$asPredicate$0$orgjsoupselectEvaluator(element, element2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jsoup.select.Evaluator
        public boolean matches(Element element, LeafNode leafNode) {
            for (int i = 0; i < this.num; i++) {
                if (this.sortedEvaluators.get(i).matches(element, leafNode)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return StringUtil.join(this.evaluators, ", ");
        }
    }
}
