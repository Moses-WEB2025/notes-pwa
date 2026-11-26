package org.apache.commons.text.similarity;

import com.miui.richeditor.schema.HtmlParser;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public interface SimilarityInput<E> {
    E at(int i);

    int length();

    static SimilarityInput<Character> input(CharSequence charSequence) {
        return new SimilarityCharacterInput(charSequence);
    }

    static <T> SimilarityInput<T> input(Object obj) {
        if (obj instanceof SimilarityInput) {
            return (SimilarityInput) obj;
        }
        if (obj instanceof CharSequence) {
            return (SimilarityInput<T>) input((CharSequence) obj);
        }
        throw new IllegalArgumentException(Objects.requireNonNull(obj, HtmlParser.InputElement.TAG).getClass().getName());
    }
}
