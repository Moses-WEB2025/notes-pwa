package com.xiaomi.miai.api.common;

import com.xiaomi.common.Optional;

/* JADX INFO: loaded from: classes4.dex */
public class Instruction<T> extends Message<InstructionHeader, T> {
    public Instruction() {
    }

    public Instruction(InstructionHeader instructionHeader, T t) {
        super(instructionHeader, t);
    }

    public String getId() {
        return getHeader().getId();
    }

    public Optional<String> getDialogId() {
        return getHeader().getDialogId();
    }

    public Instruction<T> setDialogId(String str) {
        getHeader().setDialogId(str);
        return this;
    }
}
