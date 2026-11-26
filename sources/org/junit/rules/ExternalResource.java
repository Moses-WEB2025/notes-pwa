package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ExternalResource implements TestRule {
    protected void after() {
    }

    protected void before() throws Throwable {
    }

    @Override // org.junit.rules.TestRule
    public Statement apply(Statement statement, Description description) {
        return statement(statement);
    }

    private Statement statement(final Statement statement) {
        return new Statement() { // from class: org.junit.rules.ExternalResource.1
            @Override // org.junit.runners.model.Statement
            public void evaluate() throws Throwable {
                ExternalResource.this.before();
                try {
                    statement.evaluate();
                } finally {
                    ExternalResource.this.after();
                }
            }
        };
    }
}
