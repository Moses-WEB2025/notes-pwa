package org.apache.commons.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntUnaryOperator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: classes6.dex */
public final class RandomStringGenerator {
    private final List<Character> characterList;
    private final Set<CharacterPredicate> inclusivePredicates;
    private final int maximumCodePoint;
    private final int minimumCodePoint;
    private final IntUnaryOperator random;

    public static class Builder implements org.apache.commons.text.Builder<RandomStringGenerator> {
        public static final int DEFAULT_LENGTH = 0;
        public static final int DEFAULT_MAXIMUM_CODE_POINT = 1114111;
        public static final int DEFAULT_MINIMUM_CODE_POINT = 0;
        private boolean accumulate;
        private Set<CharacterPredicate> inclusivePredicates;
        private IntUnaryOperator random;
        private int minimumCodePoint = 0;
        private int maximumCodePoint = DEFAULT_MAXIMUM_CODE_POINT;
        private Set<Character> characterSet = new HashSet();

        @Override // org.apache.commons.text.Builder
        @Deprecated
        public RandomStringGenerator build() {
            return get();
        }

        public Builder filteredBy(CharacterPredicate... characterPredicateArr) {
            if (ArrayUtils.isEmpty(characterPredicateArr)) {
                this.inclusivePredicates = null;
                return this;
            }
            Set<CharacterPredicate> set = this.inclusivePredicates;
            if (set == null) {
                this.inclusivePredicates = new HashSet();
            } else {
                set.clear();
            }
            Collections.addAll(this.inclusivePredicates, characterPredicateArr);
            return this;
        }

        @Override // java.util.function.Supplier
        public RandomStringGenerator get() {
            return new RandomStringGenerator(this);
        }

        private void initCharList() {
            if (this.accumulate) {
                return;
            }
            this.characterSet = new HashSet();
        }

        public Builder selectFrom(char... cArr) {
            initCharList();
            if (cArr != null) {
                for (char c : cArr) {
                    this.characterSet.add(Character.valueOf(c));
                }
            }
            return this;
        }

        public Builder setAccumulate(boolean z) {
            this.accumulate = z;
            return this;
        }

        public Builder usingRandom(IntUnaryOperator intUnaryOperator) {
            this.random = intUnaryOperator;
            return this;
        }

        public Builder usingRandom(TextRandomProvider textRandomProvider) {
            this.random = textRandomProvider;
            return this;
        }

        public Builder withinRange(char[]... cArr) {
            initCharList();
            if (cArr != null) {
                for (char[] cArr2 : cArr) {
                    Validate.isTrue(cArr2.length == 2, "Each pair must contain minimum and maximum code point", new Object[0]);
                    char c = cArr2[0];
                    char c2 = cArr2[1];
                    Validate.isTrue(c <= c2, "Minimum code point %d is larger than maximum code point %d", Integer.valueOf(c), Integer.valueOf(c2));
                    for (int i = c; i <= c2; i++) {
                        this.characterSet.add(Character.valueOf((char) i));
                    }
                }
            }
            return this;
        }

        public Builder withinRange(int i, int i2) {
            Validate.isTrue(i <= i2, "Minimum code point %d is larger than maximum code point %d", Integer.valueOf(i), Integer.valueOf(i2));
            Validate.isTrue(i >= 0, "Minimum code point %d is negative", i);
            Validate.isTrue(i2 <= 1114111, "Value %d is larger than Character.MAX_CODE_POINT.", i2);
            this.minimumCodePoint = i;
            this.maximumCodePoint = i2;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private RandomStringGenerator(Builder builder) {
        this.minimumCodePoint = builder.minimumCodePoint;
        this.maximumCodePoint = builder.maximumCodePoint;
        this.inclusivePredicates = builder.inclusivePredicates;
        this.random = builder.random;
        this.characterList = new ArrayList(builder.characterSet);
    }

    public String generate(int i) {
        int iGenerateRandomNumber;
        if (i == 0) {
            return "";
        }
        long j = i;
        Validate.isTrue(i > 0, "Length %d is smaller than zero.", j);
        StringBuilder sb = new StringBuilder(i);
        do {
            List<Character> list = this.characterList;
            if (list != null && !list.isEmpty()) {
                iGenerateRandomNumber = generateRandomNumber(this.characterList);
            } else {
                iGenerateRandomNumber = generateRandomNumber(this.minimumCodePoint, this.maximumCodePoint);
            }
            int type = Character.getType(iGenerateRandomNumber);
            if (type != 0 && type != 18 && type != 19) {
                Set<CharacterPredicate> set = this.inclusivePredicates;
                if (set != null) {
                    Iterator<CharacterPredicate> it = set.iterator();
                    while (it.hasNext()) {
                        if (it.next().test(iGenerateRandomNumber)) {
                            sb.appendCodePoint(iGenerateRandomNumber);
                            j--;
                            break;
                        }
                    }
                } else {
                    sb.appendCodePoint(iGenerateRandomNumber);
                    j--;
                    break;
                }
            }
        } while (j != 0);
        return sb.toString();
    }

    public String generate(int i, int i2) {
        Validate.isTrue(i >= 0, "Minimum length %d is smaller than zero.", i);
        Validate.isTrue(i <= i2, "Maximum length %d is smaller than minimum length %d.", Integer.valueOf(i2), Integer.valueOf(i));
        return generate(generateRandomNumber(i, i2));
    }

    private int generateRandomNumber(int i, int i2) {
        IntUnaryOperator intUnaryOperator = this.random;
        if (intUnaryOperator != null) {
            return intUnaryOperator.applyAsInt((i2 - i) + 1) + i;
        }
        return ThreadLocalRandom.current().nextInt(i, i2 + 1);
    }

    private int generateRandomNumber(List<Character> list) {
        int size = list.size();
        IntUnaryOperator intUnaryOperator = this.random;
        if (intUnaryOperator != null) {
            return String.valueOf(list.get(intUnaryOperator.applyAsInt(size))).codePointAt(0);
        }
        return String.valueOf(list.get(ThreadLocalRandom.current().nextInt(0, size))).codePointAt(0);
    }
}
