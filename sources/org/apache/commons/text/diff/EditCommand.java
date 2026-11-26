package org.apache.commons.text.diff;

/* JADX INFO: loaded from: classes6.dex */
public abstract class EditCommand<T> {
    private final T object;

    public abstract void accept(CommandVisitor<T> commandVisitor);

    protected EditCommand(T t) {
        this.object = t;
    }

    protected T getObject() {
        return this.object;
    }
}
