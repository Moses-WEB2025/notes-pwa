package org.apache.commons.lang3.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;

/* JADX INFO: loaded from: classes6.dex */
public class Streams {

    public static class ArrayCollector<E> implements Collector<E, List<E>, E[]> {
        private static final Set<Collector.Characteristics> characteristics = Collections.emptySet();
        private final Class<E> elementType;

        public static /* synthetic */ ArrayList $r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc() {
            return new ArrayList();
        }

        public ArrayCollector(Class<E> cls) {
            this.elementType = (Class) Objects.requireNonNull(cls, "elementType");
        }

        @Override // java.util.stream.Collector
        public BiConsumer<List<E>, E> accumulator() {
            return new org.apache.commons.lang3.Streams$ArrayCollector$$ExternalSyntheticLambda2();
        }

        @Override // java.util.stream.Collector
        public Set<Collector.Characteristics> characteristics() {
            return characteristics;
        }

        @Override // java.util.stream.Collector
        public BinaryOperator<List<E>> combiner() {
            return new BinaryOperator() { // from class: org.apache.commons.lang3.stream.Streams$ArrayCollector$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Streams.ArrayCollector.lambda$combiner$0((List) obj, (List) obj2);
                }
            };
        }

        static /* synthetic */ List lambda$combiner$0(List list, List list2) {
            list.addAll(list2);
            return list;
        }

        @Override // java.util.stream.Collector
        public Function<List<E>, E[]> finisher() {
            return new Function() { // from class: org.apache.commons.lang3.stream.Streams$ArrayCollector$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m4571x3e66eb69((List) obj);
                }
            };
        }

        /* JADX INFO: renamed from: lambda$finisher$1$org-apache-commons-lang3-stream-Streams$ArrayCollector, reason: not valid java name */
        /* synthetic */ Object[] m4571x3e66eb69(List list) {
            return list.toArray(ArrayUtils.newInstance(this.elementType, list.size()));
        }

        @Override // java.util.stream.Collector
        public Supplier<List<E>> supplier() {
            return new Supplier() { // from class: org.apache.commons.lang3.stream.Streams$ArrayCollector$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Streams.ArrayCollector.$r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc();
                }
            };
        }
    }

    private static final class EnumerationSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
        private final Enumeration<T> enumeration;

        protected EnumerationSpliterator(long j, int i, Enumeration<T> enumeration) {
            super(j, i);
            this.enumeration = (Enumeration) Objects.requireNonNull(enumeration, "enumeration");
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            while (this.enumeration.hasMoreElements()) {
                next(consumer);
            }
        }

        private boolean next(Consumer<? super T> consumer) {
            consumer.accept(this.enumeration.nextElement());
            return true;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            return this.enumeration.hasMoreElements() && next(consumer);
        }
    }

    public static class FailableStream<T> {
        private Stream<T> stream;
        private boolean terminated;

        public FailableStream(Stream<T> stream) {
            this.stream = stream;
        }

        public boolean allMatch(FailablePredicate<T, ?> failablePredicate) {
            assertNotTerminated();
            return stream().allMatch(Failable.asPredicate(failablePredicate));
        }

        public boolean anyMatch(FailablePredicate<T, ?> failablePredicate) {
            assertNotTerminated();
            return stream().anyMatch(Failable.asPredicate(failablePredicate));
        }

        protected void assertNotTerminated() {
            if (this.terminated) {
                throw new IllegalStateException("This stream is already terminated.");
            }
        }

        public <A, R> R collect(Collector<? super T, A, R> collector) {
            makeTerminated();
            return (R) stream().collect(collector);
        }

        public <A, R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> biConsumer, BiConsumer<R, R> biConsumer2) {
            makeTerminated();
            return (R) stream().collect(supplier, biConsumer, biConsumer2);
        }

        public FailableStream<T> filter(FailablePredicate<T, ?> failablePredicate) {
            assertNotTerminated();
            this.stream = this.stream.filter(Failable.asPredicate(failablePredicate));
            return this;
        }

        public void forEach(FailableConsumer<T, ?> failableConsumer) {
            makeTerminated();
            stream().forEach(Failable.asConsumer(failableConsumer));
        }

        protected void makeTerminated() {
            assertNotTerminated();
            this.terminated = true;
        }

        public <R> FailableStream<R> map(FailableFunction<T, R, ?> failableFunction) {
            assertNotTerminated();
            return new FailableStream<>(this.stream.map(Failable.asFunction(failableFunction)));
        }

        public T reduce(T t, BinaryOperator<T> binaryOperator) {
            makeTerminated();
            return stream().reduce(t, binaryOperator);
        }

        public Stream<T> stream() {
            return this.stream;
        }
    }

    public static <T> FailableStream<T> failableStream(Collection<T> collection) {
        return failableStream(of((Collection) collection));
    }

    public static <T> FailableStream<T> failableStream(Stream<T> stream) {
        return new FailableStream<>(stream);
    }

    public static <T> FailableStream<T> failableStream(T t) {
        return failableStream(streamOf(t));
    }

    @SafeVarargs
    public static <T> FailableStream<T> failableStream(T... tArr) {
        return failableStream(of(tArr));
    }

    public static <E> Stream<E> instancesOf(Class<? super E> cls, Collection<? super E> collection) {
        return instancesOf(cls, (Stream<?>) of((Collection) collection));
    }

    private static <E> Stream<E> instancesOf(final Class<? super E> cls, Stream<?> stream) {
        Stream streamOf = of(stream);
        Objects.requireNonNull(cls);
        return streamOf.filter(new Predicate() { // from class: org.apache.commons.lang3.stream.Streams$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance(obj);
            }
        });
    }

    public static <E> Stream<E> nonNull(Collection<E> collection) {
        return of((Collection) collection).filter(new Streams$$ExternalSyntheticLambda0());
    }

    public static <E> Stream<E> nonNull(E e) {
        return nonNull(streamOf(e));
    }

    @SafeVarargs
    public static <E> Stream<E> nonNull(E... eArr) {
        return nonNull(of(eArr));
    }

    public static <E> Stream<E> nonNull(Stream<E> stream) {
        return of(stream).filter(new Streams$$ExternalSyntheticLambda0());
    }

    public static <E> Stream<E> of(Collection<E> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

    public static <E> Stream<E> of(Enumeration<E> enumeration) {
        return StreamSupport.stream(new EnumerationSpliterator(Long.MAX_VALUE, 16, enumeration), false);
    }

    public static <E> Stream<E> of(Iterable<E> iterable) {
        return iterable == null ? Stream.empty() : StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <E> Stream<E> of(Iterator<E> it) {
        return it == null ? Stream.empty() : StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 16), false);
    }

    private static <E> Stream<E> of(Stream<E> stream) {
        return stream == null ? Stream.empty() : stream;
    }

    @SafeVarargs
    public static <T> Stream<T> of(T... tArr) {
        return tArr == null ? Stream.empty() : Stream.of((Object[]) tArr);
    }

    @Deprecated
    public static <E> FailableStream<E> stream(Collection<E> collection) {
        return failableStream((Collection) collection);
    }

    @Deprecated
    public static <T> FailableStream<T> stream(Stream<T> stream) {
        return failableStream((Stream) stream);
    }

    private static <T> Stream<T> streamOf(T t) {
        return t == null ? Stream.empty() : Stream.of(t);
    }

    public static <T> Collector<T, List<T>, T[]> toArray(Class<T> cls) {
        return new ArrayCollector(cls);
    }

    @Deprecated
    public Streams() {
    }
}
