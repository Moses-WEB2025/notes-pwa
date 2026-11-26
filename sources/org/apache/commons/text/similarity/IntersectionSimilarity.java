package org.apache.commons.text.similarity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public class IntersectionSimilarity<T> implements SimilarityScore<IntersectionResult> {
    private final Function<CharSequence, Collection<T>> converter;

    /* JADX INFO: Access modifiers changed from: private */
    static final class BagCount {
        private static final BagCount ZERO = new BagCount();
        private int count;

        static /* synthetic */ int access$008(BagCount bagCount) {
            int i = bagCount.count;
            bagCount.count = i + 1;
            return i;
        }

        private BagCount() {
            this.count = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TinyBag {
        private final Map<T, BagCount> map;

        private TinyBag(int i) {
            this.map = new HashMap(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void add(T t) {
            BagCount.access$008(this.map.computeIfAbsent(t, new Function() { // from class: org.apache.commons.text.similarity.IntersectionSimilarity$TinyBag$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return IntersectionSimilarity.TinyBag.lambda$add$0(obj);
                }
            }));
        }

        static /* synthetic */ BagCount lambda$add$0(Object obj) {
            return new BagCount();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Set<Map.Entry<T, BagCount>> entrySet() {
            return this.map.entrySet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCount(Object obj) {
            return this.map.getOrDefault(obj, BagCount.ZERO).count;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int uniqueElementSize() {
            return this.map.size();
        }
    }

    private static <T> int getIntersection(Set<T> set, Set<T> set2) {
        Iterator<T> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (set2.contains(it.next())) {
                i++;
            }
        }
        return i;
    }

    public IntersectionSimilarity(Function<CharSequence, Collection<T>> function) {
        if (function == null) {
            throw new IllegalArgumentException("Converter must not be null");
        }
        this.converter = function;
    }

    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public IntersectionResult apply(CharSequence charSequence, CharSequence charSequence2) {
        int intersection;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        Collection<T> collectionApply = this.converter.apply(charSequence);
        Collection<T> collectionApply2 = this.converter.apply(charSequence2);
        int size = collectionApply.size();
        int size2 = collectionApply2.size();
        if (Math.min(size, size2) == 0) {
            return new IntersectionResult(size, size2, 0);
        }
        if (!(collectionApply instanceof Set) || !(collectionApply2 instanceof Set)) {
            IntersectionSimilarity<T>.TinyBag bag = toBag(collectionApply);
            IntersectionSimilarity<T>.TinyBag bag2 = toBag(collectionApply2);
            if (bag.uniqueElementSize() < bag2.uniqueElementSize()) {
                intersection = getIntersection(bag, bag2);
            } else {
                intersection = getIntersection(bag2, bag);
            }
        } else if (size < size2) {
            intersection = getIntersection((Set) collectionApply, (Set) collectionApply2);
        } else {
            intersection = getIntersection((Set) collectionApply2, (Set) collectionApply);
        }
        return new IntersectionResult(size, size2, intersection);
    }

    private int getIntersection(IntersectionSimilarity<T>.TinyBag tinyBag, IntersectionSimilarity<T>.TinyBag tinyBag2) {
        int iMin = 0;
        for (Map.Entry entry : tinyBag.entrySet()) {
            iMin += Math.min(((BagCount) entry.getValue()).count, tinyBag2.getCount(entry.getKey()));
        }
        return iMin;
    }

    private IntersectionSimilarity<T>.TinyBag toBag(Collection<T> collection) {
        final IntersectionSimilarity<T>.TinyBag tinyBag = new TinyBag(collection.size());
        Objects.requireNonNull(tinyBag);
        collection.forEach(new Consumer() { // from class: org.apache.commons.text.similarity.IntersectionSimilarity$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                tinyBag.add(obj);
            }
        });
        return tinyBag;
    }
}
