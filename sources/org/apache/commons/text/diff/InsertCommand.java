package org.apache.commons.text.diff;

/* JADX INFO: loaded from: classes6.dex */
public class InsertCommand<T> extends EditCommand<T> {
    public InsertCommand(T t) {
        super(t);
    }

    @Override // org.apache.commons.text.diff.EditCommand
    public void accept(CommandVisitor<T> commandVisitor) {
        commandVisitor.visitInsertCommand(getObject());
    }
}
