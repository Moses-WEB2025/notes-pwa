package com.xiaomi.miai.api.common;

import java.util.List;
import java.util.UUID;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractAPIUtils {
    protected static ApiNameMapping mapping;

    AbstractAPIUtils() {
    }

    static {
        try {
            mapping = (ApiNameMapping) AbstractAPIUtils.class.getClassLoader().loadClass("com.xiaomi.miai.api.AIApiNameMapping").newInstance();
        } catch (Throwable th) {
            throw new IllegalStateException(th);
        }
    }

    public static String randomRequestId() {
        return randomRequestId(false);
    }

    public static String randomRequestId(boolean z) {
        UUID uuidRandomUUID = UUID.randomUUID();
        if (z) {
            return uuidRandomUUID.toString();
        }
        return Long.toHexString(uuidRandomUUID.getMostSignificantBits()) + Long.toHexString(uuidRandomUUID.getLeastSignificantBits());
    }

    public static <T extends ContextPayload> Context<T> buildContext(T t) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Context<>(new ContextHeader(namespaceName.namespace(), namespaceName.name()), t);
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t) {
        return buildEvent(t, null, randomRequestId(false));
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t, List<Context> list) {
        return buildEvent(t, list, randomRequestId(false));
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t, List<Context> list, String str) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Event<>(list, new EventHeader(namespaceName.namespace(), namespaceName.name()).setId(str), t);
    }

    public static <T extends InstructionPayload> Instruction<T> buildInstruction(T t) {
        return buildInstruction(t, randomRequestId(false));
    }

    public static <T extends InstructionPayload> Instruction<T> buildInstruction(T t, String str) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Instruction<>(new InstructionHeader(namespaceName.namespace(), namespaceName.name()).setId(str), t);
    }

    private static NamespaceName getNamespaceName(Object obj) {
        NamespaceName namespaceName = (NamespaceName) obj.getClass().getAnnotation(NamespaceName.class);
        if (namespaceName != null) {
            return namespaceName;
        }
        throw new UnsupportedOperationException("Cannot find NamespaceName");
    }
}
