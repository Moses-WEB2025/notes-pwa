package org.apache.commons.lang3.stream;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes6.dex */
public final class LangCollectors {
    private static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    /* JADX INFO: renamed from: $r8$lambda$V_uu1X1UKpbECUj-5gbjPr-ONSE, reason: not valid java name */
    public static /* synthetic */ StringBuilder m4569$r8$lambda$V_uu1X1UKpbECUj5gbjPrONSE() {
        return new StringBuilder();
    }

    private static final class SimpleCollector<T, A, R> implements Collector<T, A, R> {
        private final BiConsumer<A, T> accumulator;
        private final Set<Collector.Characteristics> characteristics;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Supplier<A> supplier;

        private SimpleCollector(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Set<Collector.Characteristics> set) {
            this.supplier = supplier;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.finisher = function;
            this.characteristics = set;
        }

        @Override // java.util.stream.Collector
        public BiConsumer<A, T> accumulator() {
            return this.accumulator;
        }

        @Override // java.util.stream.Collector
        public Set<Collector.Characteristics> characteristics() {
            return this.characteristics;
        }

        @Override // java.util.stream.Collector
        public BinaryOperator<A> combiner() {
            return this.combiner;
        }

        @Override // java.util.stream.Collector
        public Function<A, R> finisher() {
            return this.finisher;
        }

        @Override // java.util.stream.Collector
        public Supplier<A> supplier() {
            return this.supplier;
        }
    }

    @SafeVarargs
    public static <T, R, A> R collect(Collector<? super T, A, R> collector, T... tArr) {
        return (R) Streams.of(tArr).collect(collector);
    }

    public static Collector<Object, ?, String> joining() {
        return new SimpleCollector(new Supplier() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return LangCollectors.m4569$r8$lambda$V_uu1X1UKpbECUj5gbjPrONSE();
            }
        }, new BiConsumer() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((StringBuilder) obj).append(obj2);
            }
        }, new BinaryOperator() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((StringBuilder) obj).append((CharSequence) obj2);
            }
        }, new Function() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StringBuilder) obj).toString();
            }
        }, CH_NOID);
    }

    public static Collector<Object, ?, String> joining(CharSequence charSequence) {
        return joining(charSequence, "", "");
    }

    public static Collector<Object, ?, String> joining(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return joining(charSequence, charSequence2, charSequence3, new Function() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Objects.toString(obj);
            }
        });
    }

    public static Collector<Object, ?, String> joining(final CharSequence charSequence, final CharSequence charSequence2, final CharSequence charSequence3, final Function<Object, String> function) {
        return new SimpleCollector(new Supplier() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return LangCollectors.lambda$joining$0(charSequence, charSequence2, charSequence3);
            }
        }, new BiConsumer() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((StringJoiner) obj).add((CharSequence) function.apply(obj2));
            }
        }, new BinaryOperator() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((StringJoiner) obj).merge((StringJoiner) obj2);
            }
        }, new Function() { // from class: org.apache.commons.lang3.stream.LangCollectors$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StringJoiner) obj).toString();
            }
        }, CH_NOID);
    }

    static /* synthetic */ StringJoiner lambda$joining$0(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new StringJoiner(charSequence, charSequence2, charSequence3);
    }

    private LangCollectors() {
    }
}
