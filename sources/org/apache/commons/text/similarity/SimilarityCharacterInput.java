package org.apache.commons.text.similarity;

import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
final class SimilarityCharacterInput implements SimilarityInput<Character> {
    private final CharSequence cs;

    SimilarityCharacterInput(CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException("CharSequence");
        }
        this.cs = charSequence;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.text.similarity.SimilarityInput
    public Character at(int i) {
        return Character.valueOf(this.cs.charAt(i));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Objects.equals(this.cs, ((SimilarityCharacterInput) obj).cs);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.cs);
    }

    @Override // org.apache.commons.text.similarity.SimilarityInput
    public int length() {
        return this.cs.length();
    }

    public String toString() {
        return this.cs.toString();
    }
}
