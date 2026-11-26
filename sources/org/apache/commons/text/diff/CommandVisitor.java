package org.apache.commons.text.diff;

/* JADX INFO: loaded from: classes6.dex */
public interface CommandVisitor<T> {
    void visitDeleteCommand(T t);

    void visitInsertCommand(T t);

    void visitKeepCommand(T t);
}
