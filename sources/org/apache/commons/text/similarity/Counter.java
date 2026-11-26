package org.apache.commons.text.similarity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes6.dex */
final class Counter {
    public static Map<CharSequence, Integer> of(CharSequence[] charSequenceArr) {
        final HashMap map = new HashMap();
        Stream.of((Object[]) charSequenceArr).forEach(new Consumer() { // from class: org.apache.commons.text.similarity.Counter$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                map.compute((CharSequence) obj, new BiFunction() { // from class: org.apache.commons.text.similarity.Counter$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj2, Object obj3) {
                        Integer num = (Integer) obj3;
                        return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
                    }
                });
            }
        });
        return map;
    }

    private Counter() {
    }
}
