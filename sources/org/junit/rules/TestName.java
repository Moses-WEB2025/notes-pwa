package org.junit.rules;

import org.junit.runner.Description;

/* JADX INFO: loaded from: classes6.dex */
public class TestName extends TestWatcher {
    private String name;

    @Override // org.junit.rules.TestWatcher
    protected void starting(Description description) {
        this.name = description.getMethodName();
    }

    public String getMethodName() {
        return this.name;
    }
}
