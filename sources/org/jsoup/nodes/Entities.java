package org.jsoup.nodes;

import ch.qos.logback.core.CoreConstants;
import cn.kuaipan.android.http.multipart.StringPart;
import defpackage.R2;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Supplier;
import kotlin.text.Typography;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Parser;

/* JADX INFO: loaded from: classes6.dex */
public class Entities {
    private static final int BaseCount = 106;
    static final int ForAttribute = 2;
    static final int ForText = 1;
    static final int Normalise = 4;
    static final int TrimLeading = 8;
    static final int TrimTrailing = 16;
    static final int codepointRadix = 36;
    private static final int empty = -1;
    private static final String emptyName = "";
    private static final char[] codeDelims = {CoreConstants.COMMA_CHAR, ';'};
    private static final HashMap<String, String> multipoints = new HashMap<>();
    private static final ArrayList<String> baseSorted = new ArrayList<>(106);
    private static final ThreadLocal<char[]> charBuf = ThreadLocal.withInitial(new Supplier() { // from class: org.jsoup.nodes.Entities$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return Entities.lambda$static$0();
        }
    });
    private static final ThreadLocal<CharsetEncoder> LocalEncoder = new ThreadLocal<>();

    private static boolean isValidXmlChar(int i) {
        if (i == 9 || i == 10 || i == 13) {
            return true;
        }
        if (i >= 32 && i <= 55295) {
            return true;
        }
        if (i < 57344 || i > 65533) {
            return i >= 65536 && i <= 1114111;
        }
        return true;
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 org.jsoup.nodes.Entities$EscapeMode, still in use, count: 1, list:
  (r0v1 org.jsoup.nodes.Entities$EscapeMode) from 0x0033: IGET (r0v1 org.jsoup.nodes.Entities$EscapeMode) A[WRAPPED] org.jsoup.nodes.Entities.EscapeMode.nameKeys java.lang.String[]
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class EscapeMode {
        xhtml(EntitiesData.xmlPoints, 4),
        base(EntitiesData.basePoints, 106),
        extended(EntitiesData.fullPoints, R2.attr.rangeFillColor);

        private int[] codeKeys;
        private int[] codeVals;
        private String[] nameKeys;
        private String[] nameVals;

        public static EscapeMode valueOf(String str) {
            return (EscapeMode) Enum.valueOf(EscapeMode.class, str);
        }

        public static EscapeMode[] values() {
            return (EscapeMode[]) $VALUES.clone();
        }

        static {
            Collections.addAll(Entities.baseSorted, new EscapeMode(EntitiesData.basePoints, 106).nameKeys);
            Entities.baseSorted.sort(new Comparator() { // from class: org.jsoup.nodes.Entities$EscapeMode$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Entities.EscapeMode.lambda$static$0((String) obj, (String) obj2);
                }
            });
        }

        static /* synthetic */ int lambda$static$0(String str, String str2) {
            return str2.length() - str.length();
        }

        private EscapeMode(String str, int i) {
            super(str, i);
            Entities.load(this, str, i);
        }

        int codepointForName(String str) {
            int iBinarySearch = Arrays.binarySearch(this.nameKeys, str);
            if (iBinarySearch >= 0) {
                return this.codeVals[iBinarySearch];
            }
            return -1;
        }

        String nameForCodepoint(int i) {
            int iBinarySearch = Arrays.binarySearch(this.codeKeys, i);
            if (iBinarySearch >= 0) {
                String[] strArr = this.nameVals;
                if (iBinarySearch < strArr.length - 1) {
                    int i2 = iBinarySearch + 1;
                    if (this.codeKeys[i2] == i) {
                        return strArr[i2];
                    }
                }
                return strArr[iBinarySearch];
            }
            return "";
        }
    }

    private Entities() {
    }

    public static boolean isNamedEntity(String str) {
        return EscapeMode.extended.codepointForName(str) != -1;
    }

    public static boolean isBaseNamedEntity(String str) {
        return EscapeMode.base.codepointForName(str) != -1;
    }

    public static String getByName(String str) {
        String str2 = multipoints.get(str);
        if (str2 != null) {
            return str2;
        }
        int iCodepointForName = EscapeMode.extended.codepointForName(str);
        if (iCodepointForName != -1) {
            return new String(new int[]{iCodepointForName}, 0, 1);
        }
        return "";
    }

    public static int codepointsForName(String str, int[] iArr) {
        String str2 = multipoints.get(str);
        if (str2 != null) {
            iArr[0] = str2.codePointAt(0);
            iArr[1] = str2.codePointAt(1);
            return 2;
        }
        int iCodepointForName = EscapeMode.extended.codepointForName(str);
        if (iCodepointForName == -1) {
            return 0;
        }
        iArr[0] = iCodepointForName;
        return 1;
    }

    public static String findPrefix(String str) {
        for (String str2 : baseSorted) {
            if (str.startsWith(str2)) {
                return str2;
            }
        }
        return "";
    }

    public static String escape(String str, Document.OutputSettings outputSettings) {
        return escapeString(str, outputSettings.escapeMode(), outputSettings.charset());
    }

    public static String escape(String str) {
        return escapeString(str, EscapeMode.base, DataUtil.UTF_8);
    }

    private static String escapeString(String str, EscapeMode escapeMode, Charset charset) {
        if (str == null) {
            return "";
        }
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        doEscape(str, QuietAppendable.wrap(sbBorrowBuilder), escapeMode, charset, 3);
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    static void escape(QuietAppendable quietAppendable, String str, Document.OutputSettings outputSettings, int i) {
        doEscape(str, quietAppendable, outputSettings.escapeMode(), outputSettings.charset(), i);
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r13v5 ??, new type: java.lang.Object
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:488)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
        	... 5 more
        */
    private static void doEscape(java.lang.String r11, org.jsoup.internal.QuietAppendable r12, org.jsoup.nodes.Entities.EscapeMode r13, java.nio.charset.Charset r14, int r15) {
        /*
            java.lang.String r0 = r14.name()
            org.jsoup.nodes.Entities$CoreCharset r5 = org.jsoup.nodes.Entities.CoreCharset.byName(r0)
            java.nio.charset.CharsetEncoder r6 = encoderFor(r14)
            int r14 = r11.length()
            r0 = 0
            r1 = r0
            r2 = r1
            r3 = r2
            r7 = r3
        L15:
            if (r7 >= r14) goto L72
            r4 = r1
            int r1 = r11.codePointAt(r7)
            r8 = r15 & 4
            if (r8 == 0) goto L5d
            boolean r8 = org.jsoup.internal.StringUtil.isWhitespace(r1)
            r9 = 32
            r10 = 1
            if (r8 == 0) goto L4c
            r8 = r15 & 8
            if (r8 == 0) goto L30
            if (r2 != 0) goto L30
            goto L32
        L30:
            if (r3 == 0) goto L39
        L32:
            r10 = r2
            r2 = r12
            r12 = r4
        L35:
            r4 = r13
            r13 = r3
        L37:
            r3 = r15
            goto L66
        L39:
            r8 = r15 & 16
            if (r8 == 0) goto L42
            r4 = r2
            r2 = r12
            r12 = r10
            r10 = r4
            goto L35
        L42:
            r12.append(r9)
            r3 = r2
            r2 = r12
            r12 = r4
            r4 = r13
            r13 = r10
            r10 = r3
            goto L37
        L4c:
            if (r4 == 0) goto L57
            r12.append(r9)
            r2 = r12
            r4 = r13
            r3 = r15
            r12 = r0
            r13 = r12
            goto L63
        L57:
            r2 = r12
            r3 = r15
            r12 = r4
            r4 = r13
            r13 = r0
            goto L63
        L5d:
            r10 = r2
            r2 = r12
            r12 = r4
            r4 = r13
            r13 = r3
            r3 = r15
        L63:
            appendEscaped(r1, r2, r3, r4, r5, r6)
        L66:
            int r15 = java.lang.Character.charCount(r1)
            int r7 = r7 + r15
            r1 = r12
            r12 = r2
            r15 = r3
            r2 = r10
            r3 = r13
            r13 = r4
            goto L15
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.nodes.Entities.doEscape(java.lang.String, org.jsoup.internal.QuietAppendable, org.jsoup.nodes.Entities$EscapeMode, java.nio.charset.Charset, int):void");
    }

    private static void appendEscaped(int i, QuietAppendable quietAppendable, int i2, EscapeMode escapeMode, CoreCharset coreCharset, CharsetEncoder charsetEncoder) {
        if (EscapeMode.xhtml != escapeMode || isValidXmlChar(i)) {
            char c = (char) i;
            if (i >= 65536) {
                if (canEncode(coreCharset, c, charsetEncoder)) {
                    char[] cArr = charBuf.get();
                    quietAppendable.append(cArr, 0, Character.toChars(i, cArr, 0));
                    return;
                } else {
                    appendEncoded(quietAppendable, escapeMode, i);
                    return;
                }
            }
            if (c == '\t' || c == '\n' || c == '\r') {
                quietAppendable.append(c);
                return;
            }
            if (c == '\"') {
                if ((i2 & 2) != 0) {
                    quietAppendable.append("&quot;");
                    return;
                } else {
                    quietAppendable.append(c);
                    return;
                }
            }
            if (c == '<') {
                quietAppendable.append("&lt;");
                return;
            }
            if (c == '>') {
                quietAppendable.append("&gt;");
                return;
            }
            if (c == 160) {
                appendNbsp(quietAppendable, escapeMode);
                return;
            }
            if (c == '&') {
                quietAppendable.append("&amp;");
                return;
            }
            if (c == '\'') {
                appendApos(quietAppendable, i2, escapeMode);
            } else if (c < ' ' || !canEncode(coreCharset, c, charsetEncoder)) {
                appendEncoded(quietAppendable, escapeMode, i);
            } else {
                quietAppendable.append(c);
            }
        }
    }

    static /* synthetic */ char[] lambda$static$0() {
        return new char[2];
    }

    private static void appendNbsp(QuietAppendable quietAppendable, EscapeMode escapeMode) {
        if (escapeMode != EscapeMode.xhtml) {
            quietAppendable.append("&nbsp;");
        } else {
            quietAppendable.append("&#xa0;");
        }
    }

    private static void appendApos(QuietAppendable quietAppendable, int i, EscapeMode escapeMode) {
        if ((i & 2) != 0 && (i & 1) != 0) {
            if (escapeMode == EscapeMode.xhtml) {
                quietAppendable.append("&#x27;");
                return;
            } else {
                quietAppendable.append("&apos;");
                return;
            }
        }
        quietAppendable.append(CoreConstants.SINGLE_QUOTE_CHAR);
    }

    private static void appendEncoded(QuietAppendable quietAppendable, EscapeMode escapeMode, int i) {
        String strNameForCodepoint = escapeMode.nameForCodepoint(i);
        if (!"".equals(strNameForCodepoint)) {
            quietAppendable.append(Typography.amp).append(strNameForCodepoint).append(';');
        } else {
            quietAppendable.append("&#x").append(Integer.toHexString(i)).append(';');
        }
    }

    public static String unescape(String str) {
        return unescape(str, false);
    }

    static String unescape(String str, boolean z) {
        return Parser.unescapeEntities(str, z);
    }

    /* JADX INFO: renamed from: org.jsoup.nodes.Entities$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$nodes$Entities$CoreCharset;

        static {
            int[] iArr = new int[CoreCharset.values().length];
            $SwitchMap$org$jsoup$nodes$Entities$CoreCharset = iArr;
            try {
                iArr[CoreCharset.ascii.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jsoup$nodes$Entities$CoreCharset[CoreCharset.utf.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static boolean canEncode(CoreCharset coreCharset, char c, CharsetEncoder charsetEncoder) {
        int i = AnonymousClass1.$SwitchMap$org$jsoup$nodes$Entities$CoreCharset[coreCharset.ordinal()];
        if (i == 1) {
            return c < 128;
        }
        if (i != 2) {
            return charsetEncoder.canEncode(c);
        }
        return c < 55296 || c >= 57344;
    }

    enum CoreCharset {
        ascii,
        utf,
        fallback;

        static CoreCharset byName(String str) {
            if (str.equals(StringPart.DEFAULT_CHARSET)) {
                return ascii;
            }
            if (str.startsWith("UTF-")) {
                return utf;
            }
            return fallback;
        }
    }

    private static CharsetEncoder encoderFor(Charset charset) {
        ThreadLocal<CharsetEncoder> threadLocal = LocalEncoder;
        CharsetEncoder charsetEncoder = threadLocal.get();
        if (charsetEncoder != null && charsetEncoder.charset().equals(charset)) {
            return charsetEncoder;
        }
        CharsetEncoder charsetEncoderNewEncoder = charset.newEncoder();
        threadLocal.set(charsetEncoderNewEncoder);
        return charsetEncoderNewEncoder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void load(EscapeMode escapeMode, String str, int i) {
        int i2;
        escapeMode.nameKeys = new String[i];
        escapeMode.codeVals = new int[i];
        escapeMode.codeKeys = new int[i];
        escapeMode.nameVals = new String[i];
        CharacterReader characterReader = new CharacterReader(str);
        int i3 = 0;
        while (!characterReader.isEmpty()) {
            try {
                String strConsumeTo = characterReader.consumeTo('=');
                characterReader.advance();
                int i4 = Integer.parseInt(characterReader.consumeToAny(codeDelims), 36);
                char cCurrent = characterReader.current();
                characterReader.advance();
                if (cCurrent == ',') {
                    i2 = Integer.parseInt(characterReader.consumeTo(';'), 36);
                    characterReader.advance();
                } else {
                    i2 = -1;
                }
                int i5 = Integer.parseInt(characterReader.consumeTo(Typography.amp), 36);
                characterReader.advance();
                escapeMode.nameKeys[i3] = strConsumeTo;
                escapeMode.codeVals[i3] = i4;
                escapeMode.codeKeys[i5] = i4;
                escapeMode.nameVals[i5] = strConsumeTo;
                if (i2 != -1) {
                    multipoints.put(strConsumeTo, new String(new int[]{i4, i2}, 0, 2));
                }
                i3++;
            } catch (Throwable th) {
                try {
                    characterReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        Validate.isTrue(i3 == i, "Unexpected count of entities loaded");
        characterReader.close();
    }
}
