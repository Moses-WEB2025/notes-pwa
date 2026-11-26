package org.jsoup.parser;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes6.dex */
public class ParseErrorList extends ArrayList<ParseError> {
    private static final int INITIAL_CAPACITY = 16;
    private final int initialCapacity;
    private final int maxSize;

    ParseErrorList(int i, int i2) {
        super(i);
        this.initialCapacity = i;
        this.maxSize = i2;
    }

    ParseErrorList(ParseErrorList parseErrorList) {
        this(parseErrorList.initialCapacity, parseErrorList.maxSize);
    }

    boolean canAddError() {
        return size() < this.maxSize;
    }

    int getMaxSize() {
        return this.maxSize;
    }

    public static ParseErrorList noTracking() {
        return new ParseErrorList(0, 0);
    }

    public static ParseErrorList tracking(int i) {
        return new ParseErrorList(16, i);
    }

    @Override // java.util.ArrayList
    public Object clone() {
        return super.clone();
    }
}
