package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.matchers.JUnitMatchers;

/* JADX INFO: loaded from: classes6.dex */
class ExpectedExceptionMatcherBuilder {
    private final List<Matcher<?>> matchers = new ArrayList();

    /* JADX WARN: Multi-variable type inference failed */
    private Matcher<Throwable> cast(Matcher<?> matcher) {
        return matcher;
    }

    ExpectedExceptionMatcherBuilder() {
    }

    void add(Matcher<?> matcher) {
        this.matchers.add(matcher);
    }

    boolean expectsThrowable() {
        return !this.matchers.isEmpty();
    }

    Matcher<Throwable> build() {
        return JUnitMatchers.isThrowable(allOfTheMatchers());
    }

    private Matcher<Throwable> allOfTheMatchers() {
        if (this.matchers.size() == 1) {
            return cast(this.matchers.get(0));
        }
        return CoreMatchers.allOf(castedMatchers());
    }

    private List<Matcher<? super Throwable>> castedMatchers() {
        return new ArrayList(this.matchers);
    }
}
