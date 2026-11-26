package kotlin.reflect.jvm.internal.impl.name;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpecialNames.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class SpecialNames {
    public static final Name ANONYMOUS;
    public static final FqName ANONYMOUS_FQ_NAME;
    public static final Name ARRAY;
    public static final Name DEFAULT_NAME_FOR_COMPANION_OBJECT;
    public static final Name DESTRUCT;
    public static final Name ENUM_GET_ENTRIES;
    public static final Name IMPLICIT_SET_PARAMETER;
    public static final Name INIT;
    public static final SpecialNames INSTANCE = new SpecialNames();
    public static final Name ITERATOR;
    public static final Name LOCAL;
    public static final Name NO_NAME_PROVIDED;
    public static final Name RECEIVER;
    public static final Name ROOT_PACKAGE;
    public static final Name SAFE_IDENTIFIER_FOR_NO_NAME;
    public static final Name THIS;
    public static final Name UNARY;
    public static final Name UNDERSCORE_FOR_UNUSED_VAR;
    public static final Name WHEN_SUBJECT;

    private SpecialNames() {
    }

    static {
        Name nameSpecial = Name.special("<no name provided>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        NO_NAME_PROVIDED = nameSpecial;
        Name nameSpecial2 = Name.special("<root package>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial2, "special(...)");
        ROOT_PACKAGE = nameSpecial2;
        Name nameIdentifier = Name.identifier("Companion");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DEFAULT_NAME_FOR_COMPANION_OBJECT = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("no_name_in_PSI_3d19d79d_1ba9_4cd0_b7f5_b46aa3cd5d40");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        SAFE_IDENTIFIER_FOR_NO_NAME = nameIdentifier2;
        Name nameSpecial3 = Name.special("<anonymous>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial3, "special(...)");
        ANONYMOUS = nameSpecial3;
        FqName.Companion companion = FqName.Companion;
        Name nameSpecial4 = Name.special("<anonymous>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial4, "special(...)");
        ANONYMOUS_FQ_NAME = companion.topLevel(nameSpecial4);
        Name nameSpecial5 = Name.special("<unary>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial5, "special(...)");
        UNARY = nameSpecial5;
        Name nameSpecial6 = Name.special("<this>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial6, "special(...)");
        THIS = nameSpecial6;
        Name nameSpecial7 = Name.special(ReflectUtilsForMiui.OBJECT_CONSTRUCTOR);
        Intrinsics.checkNotNullExpressionValue(nameSpecial7, "special(...)");
        INIT = nameSpecial7;
        Name nameSpecial8 = Name.special("<when-subject>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial8, "special(...)");
        WHEN_SUBJECT = nameSpecial8;
        Name nameSpecial9 = Name.special("<iterator>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial9, "special(...)");
        ITERATOR = nameSpecial9;
        Name nameSpecial10 = Name.special("<destruct>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial10, "special(...)");
        DESTRUCT = nameSpecial10;
        Name nameSpecial11 = Name.special("<local>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial11, "special(...)");
        LOCAL = nameSpecial11;
        Name nameSpecial12 = Name.special("<unused var>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial12, "special(...)");
        UNDERSCORE_FOR_UNUSED_VAR = nameSpecial12;
        Name nameSpecial13 = Name.special("<set-?>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial13, "special(...)");
        IMPLICIT_SET_PARAMETER = nameSpecial13;
        Name nameSpecial14 = Name.special("<array>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial14, "special(...)");
        ARRAY = nameSpecial14;
        Name nameSpecial15 = Name.special("<receiver>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial15, "special(...)");
        RECEIVER = nameSpecial15;
        Name nameSpecial16 = Name.special("<get-entries>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial16, "special(...)");
        ENUM_GET_ENTRIES = nameSpecial16;
    }

    @JvmStatic
    public static final Name safeIdentifier(Name name) {
        return (name == null || name.isSpecial()) ? SAFE_IDENTIFIER_FOR_NO_NAME : name;
    }

    public final boolean isSafeIdentifier(Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String strAsString = name.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString.length() > 0 && !name.isSpecial();
    }
}
