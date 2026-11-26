package org.apache.commons.text.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes6.dex */
public class EditScript<T> {
    private final List<EditCommand<T>> commands = new ArrayList();
    private int lcsLength = 0;
    private int modifications = 0;

    public void append(DeleteCommand<T> deleteCommand) {
        this.commands.add(deleteCommand);
        this.modifications++;
    }

    public void append(InsertCommand<T> insertCommand) {
        this.commands.add(insertCommand);
        this.modifications++;
    }

    public void append(KeepCommand<T> keepCommand) {
        this.commands.add(keepCommand);
        this.lcsLength++;
    }

    public int getLCSLength() {
        return this.lcsLength;
    }

    public int getModifications() {
        return this.modifications;
    }

    public void visit(final CommandVisitor<T> commandVisitor) {
        this.commands.forEach(new Consumer() { // from class: org.apache.commons.text.diff.EditScript$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((EditCommand) obj).accept(commandVisitor);
            }
        });
    }
}
