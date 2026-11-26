package com.agog.mathdisplay.parse;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.onetrack.b.m;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: compiled from: MTMathListBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 A2\u00020\u0001:\u0001AB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00032\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001f\u001a\u00020\u0003H\u0002J\b\u0010 \u001a\u0004\u0018\u00010!J\u0012\u0010\"\u001a\u0004\u0018\u00010!2\u0006\u0010#\u001a\u00020\u0019H\u0002J\u001a\u0010\"\u001a\u0004\u0018\u00010!2\u0006\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u0016H\u0002J&\u0010%\u001a\u0004\u0018\u00010\u001d2\b\u0010&\u001a\u0004\u0018\u00010\u00032\b\u0010'\u001a\u0004\u0018\u00010!2\u0006\u0010(\u001a\u00020\u0019H\u0002J\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0014J\u0006\u0010,\u001a\u00020\u0019J\u0010\u0010-\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0016H\u0002J\u0012\u0010/\u001a\u0004\u0018\u00010\u001d2\u0006\u00100\u001a\u00020\u0003H\u0002J\b\u00101\u001a\u00020\u0016H\u0002J\b\u00102\u001a\u00020\u0019H\u0002J\u0010\u00103\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0016H\u0002J\n\u00104\u001a\u0004\u0018\u00010\u0003H\u0002J\b\u00105\u001a\u00020\u0003H\u0002J\n\u00106\u001a\u0004\u0018\u00010\u0003H\u0002J\n\u00107\u001a\u0004\u0018\u00010\u0003H\u0002J\b\u00108\u001a\u00020\u0003H\u0002J\u0018\u00109\u001a\u00020*2\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\u0003H\u0002J\b\u0010=\u001a\u00020*H\u0002J\"\u0010>\u001a\u0004\u0018\u00010!2\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010?\u001a\u00020!2\u0006\u0010$\u001a\u00020\u0016H\u0002J\b\u0010@\u001a\u00020*H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R6\u0010\u000f\u001a*\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00110\u0010j\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0011`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0011X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathListBuilder;", "", "str", "", "(Ljava/lang/String;)V", "charlength", "", "chars", "currentCharIndex", "currentEnv", "Lcom/agog/mathdisplay/parse/MTEnvProperties;", "currentFontStyle", "Lcom/agog/mathdisplay/parse/MTFontStyle;", "currentInnerAtom", "Lcom/agog/mathdisplay/parse/MTInner;", "fractionCommands", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "parseerror", "Lcom/agog/mathdisplay/parse/MTParseError;", "singleCharCommands", "", "[Ljava/lang/Character;", "spacesAllowed", "", "applyModifier", "modifier", "atom", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "atomForCommand", "command", "build", "Lcom/agog/mathdisplay/parse/MTMathList;", "buildInternal", "oneCharOnly", "stopChar", "buildTable", "env", "firstList", "isRow", "copyError", "", "dst", "errorActive", "expectCharacter", "ch", "getBoundaryAtom", "delimiterType", "getNextCharacter", "hasCharacters", "nonSpaceChar", "readColor", "readCommand", "readDelimiter", "readEnvironment", "readString", "setError", "errorcode", "Lcom/agog/mathdisplay/parse/MTParseErrors;", "message", "skipSpaces", "stopCommand", "list", "unlookCharacter", "Factory", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathListBuilder {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final HashMap<Float, String> spaceToCommands = MapsKt.hashMapOf(TuplesKt.to(Float.valueOf(3.0f), ","), TuplesKt.to(Float.valueOf(4.0f), ">"), TuplesKt.to(Float.valueOf(5.0f), ";"), TuplesKt.to(Float.valueOf(-3.0f), "!"), TuplesKt.to(Float.valueOf(18.0f), "quad"), TuplesKt.to(Float.valueOf(36.0f), "qquad"));
    private static final HashMap<MTLineStyle, String> styleToCommands = MapsKt.hashMapOf(TuplesKt.to(MTLineStyle.KMTLineStyleDisplay, "displaystyle"), TuplesKt.to(MTLineStyle.KMTLineStyleText, "textstyle"), TuplesKt.to(MTLineStyle.KMTLineStyleScript, "scriptstyle"), TuplesKt.to(MTLineStyle.KMTLineStyleScriptScript, "scriptscriptstyle"));
    private int charlength;
    private String chars;
    private int currentCharIndex;
    private MTEnvProperties currentEnv;
    private MTFontStyle currentFontStyle;
    private MTInner currentInnerAtom;
    private final HashMap<String, String[]> fractionCommands;
    private MTParseError parseerror;
    private Character[] singleCharCommands;
    private boolean spacesAllowed;

    private final boolean nonSpaceChar(char ch2) {
        return ch2 < '!' || ch2 > '~';
    }

    public MTMathListBuilder(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        this.chars = str;
        this.charlength = str.length();
        this.currentFontStyle = MTFontStyle.KMTFontStyleDefault;
        this.singleCharCommands = new Character[]{Character.valueOf(CoreConstants.CURLY_LEFT), Character.valueOf(CoreConstants.CURLY_RIGHT), '$', '#', Character.valueOf(CoreConstants.PERCENT_CHAR), '_', '|', ' ', Character.valueOf(CoreConstants.COMMA_CHAR), Character.valueOf(Typography.greater), ';', '!', Character.valueOf(CoreConstants.ESCAPE_CHAR)};
        this.fractionCommands = MapsKt.hashMapOf(TuplesKt.to("over", new String[]{""}), TuplesKt.to("atop", new String[]{""}), TuplesKt.to("choose", new String[]{"(", ")"}), TuplesKt.to("brack", new String[]{"[", "]"}), TuplesKt.to("brace", new String[]{"{", StringSubstitutor.DEFAULT_VAR_END}));
    }

    private final boolean hasCharacters() {
        return this.currentCharIndex < this.charlength;
    }

    private final char getNextCharacter() throws MathDisplayException {
        int i = this.currentCharIndex;
        if (i >= this.charlength) {
            throw new MathDisplayException("Retrieving character at index " + this.currentCharIndex + " beyond length " + this.charlength);
        }
        String str = this.chars;
        this.currentCharIndex = i + 1;
        return str.charAt(i);
    }

    private final void unlookCharacter() throws MathDisplayException {
        int i = this.currentCharIndex;
        if (i <= 0) {
            throw new MathDisplayException("Unlooking when at the first character.");
        }
        this.currentCharIndex = i - 1;
    }

    public final MTMathList build() {
        MTMathList mTMathListBuildInternal = buildInternal(false);
        if (!hasCharacters()) {
            return mTMathListBuildInternal;
        }
        setError(MTParseErrors.MismatchBraces, Intrinsics.stringPlus("Mismatched braces: ", this.chars));
        return null;
    }

    private final MTMathList buildInternal(boolean oneCharOnly) {
        return buildInternal(oneCharOnly, (char) 0);
    }

    /* JADX WARN: Code duplicated, block: B:109:0x0177  */
    /* JADX WARN: Code duplicated, block: B:146:0x0182 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x01a5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x0017 A[SYNTHETIC] */
    private final MTMathList buildInternal(boolean oneCharOnly, char stopChar) throws MathDisplayException {
        MTMathList mTMathList = new MTMathList(new MTMathAtom[0]);
        if (oneCharOnly && stopChar > 0) {
            throw new MathDisplayException("Cannot set both oneCharOnly and stopChar.");
        }
        MTMathAtom mTMathAtom = null;
        while (hasCharacters()) {
            if (errorActive()) {
                return null;
            }
            char nextCharacter = getNextCharacter();
            if (oneCharOnly && (nextCharacter == '^' || nextCharacter == '}' || nextCharacter == '_' || nextCharacter == '&')) {
                unlookCharacter();
                return mTMathList;
            }
            if (stopChar <= 0 || nextCharacter != stopChar) {
                if (nextCharacter == '^') {
                    if (oneCharOnly) {
                        throw new MathDisplayException("This should have been handled before");
                    }
                    if (mTMathAtom == null || mTMathAtom.getSuperScript() != null || !mTMathAtom.scriptsAllowed()) {
                        mTMathAtom = new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "");
                        mTMathList.addAtom(mTMathAtom);
                    }
                    mTMathAtom.setSuperScript(buildInternal(true));
                } else if (nextCharacter == '_') {
                    if (oneCharOnly) {
                        throw new MathDisplayException("This should have been handled before");
                    }
                    if (mTMathAtom == null || mTMathAtom.getSubScript() != null || !mTMathAtom.scriptsAllowed()) {
                        mTMathAtom = new MTMathAtom(MTMathAtomType.KMTMathAtomOrdinary, "");
                        mTMathList.addAtom(mTMathAtom);
                    }
                    mTMathAtom.setSubScript(buildInternal(true));
                } else if (nextCharacter == '{') {
                    MTMathList mTMathListBuildInternal = buildInternal(false, CoreConstants.CURLY_RIGHT);
                    if (mTMathListBuildInternal != null) {
                        mTMathAtom = (MTMathAtom) CollectionsKt.lastOrNull((List) mTMathListBuildInternal.getAtoms());
                        mTMathList.append(mTMathListBuildInternal);
                    }
                    if (oneCharOnly) {
                    }
                } else {
                    if (nextCharacter == '}') {
                        if (oneCharOnly) {
                            throw new MathDisplayException("This should have been handled before");
                        }
                        if (stopChar != 0) {
                            throw new MathDisplayException("This should have been handled before");
                        }
                        setError(MTParseErrors.MismatchBraces, "Mismatched braces.");
                        return null;
                    }
                    if (nextCharacter == '\\') {
                        String command = readCommand();
                        MTMathList mTMathListStopCommand = stopCommand(command, mTMathList, stopChar);
                        if (mTMathListStopCommand != null) {
                            return mTMathListStopCommand;
                        }
                        if (errorActive()) {
                            return null;
                        }
                        if (applyModifier(command, mTMathAtom)) {
                            continue;
                        } else {
                            MTFontStyle mTFontStyle = MTMathAtom.INSTANCE.getFontStyleWithName().get(command);
                            if (mTFontStyle != null) {
                                boolean z = this.spacesAllowed;
                                this.spacesAllowed = Intrinsics.areEqual(command, "text");
                                MTFontStyle mTFontStyle2 = this.currentFontStyle;
                                this.currentFontStyle = mTFontStyle;
                                MTMathList mTMathListBuildInternal2 = buildInternal(true);
                                this.currentFontStyle = mTFontStyle2;
                                this.spacesAllowed = z;
                                if (mTMathListBuildInternal2 != null) {
                                    mTMathAtom = (MTMathAtom) CollectionsKt.lastOrNull((List) mTMathListBuildInternal2.getAtoms());
                                    mTMathList.append(mTMathListBuildInternal2);
                                }
                                if (oneCharOnly) {
                                }
                            } else {
                                mTMathAtom = atomForCommand(command);
                                if (mTMathAtom == null) {
                                    setError(MTParseErrors.InternalError, "Internal error");
                                    return null;
                                }
                                if (mTMathAtom != null) {
                                    throw new MathDisplayException("Atom shouldn't be null");
                                }
                                mTMathAtom.setFontStyle(this.currentFontStyle);
                                mTMathList.addAtom(mTMathAtom);
                                if (oneCharOnly) {
                                }
                            }
                        }
                    } else if (nextCharacter != '&') {
                        if (this.spacesAllowed && nextCharacter == ' ') {
                            mTMathAtom = MTMathAtom.INSTANCE.atomForLatexSymbolName(StringUtils.SPACE);
                        } else {
                            MTMathAtom mTMathAtomAtomForCharacter = MTMathAtom.INSTANCE.atomForCharacter(nextCharacter);
                            if (mTMathAtomAtomForCharacter == null) {
                                continue;
                            } else {
                                mTMathAtom = mTMathAtomAtomForCharacter;
                            }
                        }
                        if (mTMathAtom != null) {
                            throw new MathDisplayException("Atom shouldn't be null");
                        }
                        mTMathAtom.setFontStyle(this.currentFontStyle);
                        mTMathList.addAtom(mTMathAtom);
                        if (oneCharOnly) {
                        }
                    } else {
                        if (oneCharOnly) {
                            throw new MathDisplayException("This should have been handled before");
                        }
                        if (this.currentEnv == null) {
                            MTMathAtom mTMathAtomBuildTable = buildTable(null, mTMathList, false);
                            if (mTMathAtomBuildTable != null) {
                                return new MTMathList(mTMathAtomBuildTable);
                            }
                            return null;
                        }
                    }
                }
            }
            return mTMathList;
        }
        if (stopChar > 0) {
            if (stopChar == '}') {
                setError(MTParseErrors.MismatchBraces, "Missing closing brace");
                return mTMathList;
            }
            setError(MTParseErrors.CharacterNotFound, Intrinsics.stringPlus("Expected character not found: ", Character.valueOf(stopChar)));
        }
        return mTMathList;
    }

    private final String readString() throws MathDisplayException {
        StringBuilder sb = new StringBuilder();
        while (hasCharacters()) {
            char nextCharacter = getNextCharacter();
            if (('a' <= nextCharacter && nextCharacter < '{') || ('A' <= nextCharacter && nextCharacter < '[')) {
                sb.append(nextCharacter);
            } else {
                unlookCharacter();
                break;
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "mutable.toString()");
        return string;
    }

    private final String readColor() throws MathDisplayException {
        if (!expectCharacter(CoreConstants.CURLY_LEFT)) {
            setError(MTParseErrors.CharacterNotFound, "Missing {");
            return null;
        }
        skipSpaces();
        StringBuilder sb = new StringBuilder();
        while (hasCharacters()) {
            char nextCharacter = getNextCharacter();
            if (nextCharacter == '#' || (('A' <= nextCharacter && nextCharacter < 'G') || (('a' <= nextCharacter && nextCharacter < 'g') || ('0' <= nextCharacter && nextCharacter < ':')))) {
                sb.append(nextCharacter);
            } else {
                unlookCharacter();
                break;
            }
        }
        if (!expectCharacter(CoreConstants.CURLY_RIGHT)) {
            setError(MTParseErrors.CharacterNotFound, "Missing }");
            return null;
        }
        return sb.toString();
    }

    private final void skipSpaces() throws MathDisplayException {
        while (hasCharacters()) {
            if (!nonSpaceChar(getNextCharacter())) {
                unlookCharacter();
                return;
            }
        }
    }

    private final boolean expectCharacter(char ch2) throws MathDisplayException {
        if (nonSpaceChar(ch2)) {
            throw new MathDisplayException(Intrinsics.stringPlus("Expected non space character ", Character.valueOf(ch2)));
        }
        skipSpaces();
        if (!hasCharacters()) {
            return false;
        }
        char nextCharacter = getNextCharacter();
        if (nonSpaceChar(nextCharacter)) {
            throw new MathDisplayException(Intrinsics.stringPlus("Expected non space character ", Character.valueOf(nextCharacter)));
        }
        if (nextCharacter == ch2) {
            return true;
        }
        unlookCharacter();
        return false;
    }

    private final String readCommand() throws MathDisplayException {
        if (hasCharacters()) {
            char nextCharacter = getNextCharacter();
            if (ArraysKt.contains(this.singleCharCommands, Character.valueOf(nextCharacter))) {
                return String.valueOf(nextCharacter);
            }
            unlookCharacter();
        }
        return readString();
    }

    private final String readDelimiter() throws MathDisplayException {
        skipSpaces();
        if (!hasCharacters()) {
            return null;
        }
        char nextCharacter = getNextCharacter();
        if (nonSpaceChar(nextCharacter)) {
            throw new MathDisplayException(Intrinsics.stringPlus("Expected non space character ", Character.valueOf(nextCharacter)));
        }
        if (nextCharacter == '\\') {
            String command = readCommand();
            return Intrinsics.areEqual(command, "|") ? "||" : command;
        }
        return String.valueOf(nextCharacter);
    }

    private final String readEnvironment() throws MathDisplayException {
        if (!expectCharacter(CoreConstants.CURLY_LEFT)) {
            setError(MTParseErrors.CharacterNotFound, "Missing {");
            return null;
        }
        skipSpaces();
        String string = readString();
        if (expectCharacter(CoreConstants.CURLY_RIGHT)) {
            return string;
        }
        setError(MTParseErrors.CharacterNotFound, "Missing }");
        return null;
    }

    private final MTMathAtom getBoundaryAtom(String delimiterType) throws MathDisplayException {
        String delimiter = readDelimiter();
        if (delimiter == null) {
            setError(MTParseErrors.MissingDelimiter, Intrinsics.stringPlus("Missing delimiter for ", delimiterType));
            return null;
        }
        MTMathAtom mTMathAtomBoundaryAtomForDelimiterName = MTMathAtom.INSTANCE.boundaryAtomForDelimiterName(delimiter);
        if (mTMathAtomBoundaryAtomForDelimiterName != null) {
            return mTMathAtomBoundaryAtomForDelimiterName;
        }
        setError(MTParseErrors.InvalidDelimiter, "Invalid delimiter for " + delimiterType + ": " + ((Object) delimiter));
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final MTMathAtom atomForCommand(String command) throws MathDisplayException {
        MTMathAtom mTMathAtomAtomForLatexSymbolName = MTMathAtom.INSTANCE.atomForLatexSymbolName(command);
        if (mTMathAtomAtomForLatexSymbolName != null) {
            return mTMathAtomAtomForLatexSymbolName;
        }
        MTAccent mTAccentAccentWithName = MTMathAtom.INSTANCE.accentWithName(command);
        if (mTAccentAccentWithName != null) {
            mTAccentAccentWithName.setInnerList(buildInternal(true));
            return mTAccentAccentWithName;
        }
        switch (command.hashCode()) {
            case -1026963764:
                if (command.equals("underline")) {
                    MTUnderLine mTUnderLine = new MTUnderLine();
                    mTUnderLine.setInnerList(buildInternal(true));
                    return mTUnderLine;
                }
                break;
            case 3151342:
                if (command.equals("frac")) {
                    MTFraction mTFraction = new MTFraction();
                    mTFraction.setNumerator(buildInternal(true));
                    mTFraction.setDenominator(buildInternal(true));
                    return mTFraction;
                }
                break;
            case 3317767:
                if (command.equals("left")) {
                    MTInner mTInner = this.currentInnerAtom;
                    MTInner mTInner2 = new MTInner();
                    this.currentInnerAtom = mTInner2;
                    mTInner2.setLeftBoundary(getBoundaryAtom("left"));
                    MTInner mTInner3 = this.currentInnerAtom;
                    if ((mTInner3 == null ? null : mTInner3.getLeftBoundary()) == null) {
                        return null;
                    }
                    MTInner mTInner4 = this.currentInnerAtom;
                    if (mTInner4 != null) {
                        mTInner4.setInnerList(buildInternal(false));
                    }
                    MTInner mTInner5 = this.currentInnerAtom;
                    if ((mTInner5 == null ? null : mTInner5.getRightBoundary()) == null) {
                        setError(MTParseErrors.MissingRight, "Missing \\right");
                        return null;
                    }
                    MTInner mTInner6 = this.currentInnerAtom;
                    this.currentInnerAtom = mTInner;
                    return mTInner6;
                }
                break;
            case 3538208:
                if (command.equals("sqrt")) {
                    MTRadical mTRadical = new MTRadical();
                    if (getNextCharacter() == '[') {
                        mTRadical.setDegree(buildInternal(false, ']'));
                        mTRadical.setRadicand(buildInternal(true));
                    } else {
                        unlookCharacter();
                        mTRadical.setRadicand(buildInternal(true));
                    }
                    return mTRadical;
                }
                break;
            case 93616297:
                if (command.equals("begin")) {
                    String environment = readEnvironment();
                    if (environment == null) {
                        return null;
                    }
                    return buildTable(environment, null, false);
                }
                break;
            case 93742373:
                if (command.equals("binom")) {
                    MTFraction mTFraction2 = new MTFraction(false);
                    mTFraction2.setNumerator(buildInternal(true));
                    mTFraction2.setDenominator(buildInternal(true));
                    mTFraction2.setLeftDelimiter("(");
                    mTFraction2.setRightDelimiter(")");
                    return mTFraction2;
                }
                break;
            case 94842723:
                if (command.equals("color")) {
                    MTMathColor mTMathColor = new MTMathColor();
                    mTMathColor.setColorString(readColor());
                    mTMathColor.setInnerList(buildInternal(true));
                    return mTMathColor;
                }
                break;
            case 529818312:
                if (command.equals("overline")) {
                    MTOverLine mTOverLine = new MTOverLine();
                    mTOverLine.setInnerList(buildInternal(true));
                    return mTOverLine;
                }
                break;
        }
        setError(MTParseErrors.InvalidCommand, Intrinsics.stringPlus("Invalid command ", command));
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0043, code lost:
    
        if (r7.equals("brack") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004d, code lost:
    
        if (r7.equals("brace") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        if (r7.equals("over") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005f, code lost:
    
        if (r7.equals("atop") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c0, code lost:
    
        if (r7.equals("cr") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ca, code lost:
    
        if (r7.equals("\\") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00ce, code lost:
    
        r7 = r6.currentEnv;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d0, code lost:
    
        if (r7 == null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d2, code lost:
    
        r7.setNumRows(r7.getNumRows() + 1);
        r6.currentEnv = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00de, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00df, code lost:
    
        r6 = buildTable(null, r8, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e3, code lost:
    
        if (r6 == null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00ee, code lost:
    
        return new com.agog.mathdisplay.parse.MTMathList(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ef, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00f6, code lost:
    
        if (r7.equals("choose") == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00fd, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7, "over") == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00ff, code lost:
    
        r0 = new com.agog.mathdisplay.parse.MTFraction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0105, code lost:
    
        r0 = new com.agog.mathdisplay.parse.MTFraction(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x010a, code lost:
    
        r7 = r6.fractionCommands.get(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0112, code lost:
    
        if (r7 == null) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0116, code lost:
    
        if (r7.length != 2) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0118, code lost:
    
        r0.setLeftDelimiter(r7[0]);
        r0.setRightDelimiter(r7[1]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0122, code lost:
    
        r0.setNumerator(r8);
        r0.setDenominator(buildInternal(false, r9));
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0130, code lost:
    
        if (errorActive() == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0132, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0133, code lost:
    
        r6 = new com.agog.mathdisplay.parse.MTMathList(new com.agog.mathdisplay.parse.MTMathAtom[0]);
        r6.addAtom(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x013f, code lost:
    
        return r6;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final com.agog.mathdisplay.parse.MTMathList stopCommand(java.lang.String r7, com.agog.mathdisplay.parse.MTMathList r8, char r9) throws com.agog.mathdisplay.parse.MathDisplayException {
        /*
            Method dump skipped, instruction units count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.agog.mathdisplay.parse.MTMathListBuilder.stopCommand(java.lang.String, com.agog.mathdisplay.parse.MTMathList, char):com.agog.mathdisplay.parse.MTMathList");
    }

    private final boolean applyModifier(String modifier, MTMathAtom atom) {
        if (Intrinsics.areEqual(modifier, "limits")) {
            if (atom == null || atom.getType() != MTMathAtomType.KMTMathAtomLargeOperator) {
                setError(MTParseErrors.InvalidLimits, "limits can only be applied to an operator.");
            } else {
                ((MTLargeOperator) atom).setHasLimits(true);
            }
            return true;
        }
        if (!Intrinsics.areEqual(modifier, "nolimits")) {
            return false;
        }
        if (atom == null || atom.getType() != MTMathAtomType.KMTMathAtomLargeOperator) {
            setError(MTParseErrors.InvalidLimits, "nolimits can only be applied to an operator.");
            return true;
        }
        ((MTLargeOperator) atom).setHasLimits(false);
        return true;
    }

    public final void copyError(MTParseError dst) {
        Intrinsics.checkNotNullParameter(dst, "dst");
        dst.copyFrom(this.parseerror);
    }

    public final boolean errorActive() {
        return this.parseerror != null;
    }

    private final void setError(MTParseErrors errorcode, String message) {
        if (this.parseerror == null) {
            this.parseerror = new MTParseError(errorcode, message);
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0071 -> B:7:0x0043). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:11:0x004f
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private final com.agog.mathdisplay.parse.MTMathAtom buildTable(java.lang.String r9, com.agog.mathdisplay.parse.MTMathList r10, boolean r11) {
        /*
            r8 = this;
            com.agog.mathdisplay.parse.MTEnvProperties r0 = r8.currentEnv
            com.agog.mathdisplay.parse.MTEnvProperties r1 = new com.agog.mathdisplay.parse.MTEnvProperties
            r6 = 6
            r7 = 0
            r3 = 0
            r4 = 0
            r2 = r9
            r1.<init>(r2, r3, r4, r6, r7)
            r8.currentEnv = r1
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.List r9 = (java.util.List) r9
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.List r2 = (java.util.List) r2
            r9.add(r3, r2)
            r2 = 1
            if (r10 == 0) goto L48
            java.lang.Object r4 = r9.get(r3)
            java.util.List r4 = (java.util.List) r4
            r4.add(r3, r10)
            if (r11 == 0) goto L45
            long r10 = r1.getNumRows()
            r4 = 1
            long r10 = r10 + r4
            r1.setNumRows(r10)
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.List r10 = (java.util.List) r10
            r9.add(r2, r10)
            r10 = r2
        L43:
            r11 = r3
            goto L4a
        L45:
            r11 = r2
            r10 = r3
            goto L4a
        L48:
            r10 = r3
            r11 = r10
        L4a:
            boolean r4 = r1.getEnded()
            r5 = 0
            if (r4 != 0) goto L81
            boolean r4 = r8.hasCharacters()
            if (r4 == 0) goto L81
            com.agog.mathdisplay.parse.MTMathList r4 = r8.buildInternal(r3)
            if (r4 != 0) goto L5e
            return r5
        L5e:
            java.lang.Object r5 = r9.get(r10)
            java.util.List r5 = (java.util.List) r5
            r5.add(r11, r4)
            int r11 = r11 + r2
            long r4 = r1.getNumRows()
            long r6 = (long) r10
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L4a
            long r10 = r1.getNumRows()
            int r10 = (int) r10
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.List r11 = (java.util.List) r11
            r9.add(r10, r11)
            goto L43
        L81:
            boolean r10 = r1.getEnded()
            if (r10 != 0) goto L95
            java.lang.String r10 = r1.getEnvName()
            if (r10 == 0) goto L95
            com.agog.mathdisplay.parse.MTParseErrors r9 = com.agog.mathdisplay.parse.MTParseErrors.MissingEnd
            java.lang.String r10 = "Missing \\end"
            r8.setError(r9, r10)
            return r5
        L95:
            com.agog.mathdisplay.parse.MTParseError r10 = new com.agog.mathdisplay.parse.MTParseError
            r11 = 3
            r10.<init>(r5, r5, r11, r5)
            com.agog.mathdisplay.parse.MTMathAtom$Factory r11 = com.agog.mathdisplay.parse.MTMathAtom.INSTANCE
            java.lang.String r1 = r1.getEnvName()
            com.agog.mathdisplay.parse.MTMathAtom r9 = r11.tableWithEnvironment(r1, r9, r10)
            if (r9 != 0) goto Laa
            r8.parseerror = r10
            return r5
        Laa:
            r8.currentEnv = r0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.agog.mathdisplay.parse.MTMathListBuilder.buildTable(java.lang.String, com.agog.mathdisplay.parse.MTMathList, boolean):com.agog.mathdisplay.parse.MTMathAtom");
    }

    /* JADX INFO: renamed from: com.agog.mathdisplay.parse.MTMathListBuilder$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: MTMathListBuilder.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0006J\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u000bR*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/agog/mathdisplay/parse/MTMathListBuilder$Factory;", "", "()V", "spaceToCommands", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "styleToCommands", "Lcom/agog/mathdisplay/parse/MTLineStyle;", "buildFromString", "Lcom/agog/mathdisplay/parse/MTMathList;", "str", SNSAuthProvider.VALUE_SNS_ERROR, "Lcom/agog/mathdisplay/parse/MTParseError;", "delimToLatexString", "delim", "Lcom/agog/mathdisplay/parse/MTMathAtom;", "toLatexString", m.g, "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MTMathList buildFromString(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            return new MTMathListBuilder(str).build();
        }

        public final MTMathList buildFromString(String str, MTParseError error) {
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(error, "error");
            MTMathListBuilder mTMathListBuilder = new MTMathListBuilder(str);
            MTMathList mTMathListBuild = mTMathListBuilder.build();
            if (!mTMathListBuilder.errorActive()) {
                return mTMathListBuild;
            }
            mTMathListBuilder.copyError(error);
            return null;
        }

        private final String delimToLatexString(MTMathAtom delim) {
            String strDelimiterNameForBoundaryAtom = MTMathAtom.INSTANCE.delimiterNameForBoundaryAtom(delim);
            if (strDelimiterNameForBoundaryAtom != null) {
                if (ArraysKt.contains(new String[]{"(", ")", "[", "]", "<", ">", "|", ".", InternalZipConstants.ZIP_FILE_SEPARATOR}, strDelimiterNameForBoundaryAtom)) {
                    return strDelimiterNameForBoundaryAtom;
                }
                if (Intrinsics.areEqual(strDelimiterNameForBoundaryAtom, "||")) {
                    return "\\|";
                }
                return Intrinsics.stringPlus("\\", strDelimiterNameForBoundaryAtom);
            }
            return "";
        }

        public final String toLatexString(MTMathList ml) {
            String latexString;
            String str;
            Intrinsics.checkNotNullParameter(ml, "ml");
            StringBuilder sb = new StringBuilder();
            MTFontStyle fontStyle = MTFontStyle.KMTFontStyleDefault;
            for (MTMathAtom mTMathAtom : ml.getAtoms()) {
                if (fontStyle != mTMathAtom.getFontStyle()) {
                    if (fontStyle != MTFontStyle.KMTFontStyleDefault) {
                        sb.append(StringSubstitutor.DEFAULT_VAR_END);
                    }
                    if (mTMathAtom.getFontStyle() != MTFontStyle.KMTFontStyleDefault) {
                        sb.append("\\" + MTMathAtom.INSTANCE.fontNameForStyle(mTMathAtom.getFontStyle()) + CoreConstants.CURLY_LEFT);
                    }
                    fontStyle = mTMathAtom.getFontStyle();
                }
                if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomFraction) {
                    MTFraction mTFraction = (MTFraction) mTMathAtom;
                    MTMathList numerator = mTFraction.getNumerator();
                    String latexString2 = "";
                    if (numerator == null) {
                        latexString = "";
                    } else {
                        latexString = toLatexString(numerator);
                    }
                    MTMathList denominator = mTFraction.getDenominator();
                    if (denominator != null) {
                        latexString2 = toLatexString(denominator);
                    }
                    if (mTFraction.getHasRule()) {
                        sb.append("\\frac{" + latexString + "}{" + latexString2 + CoreConstants.CURLY_RIGHT);
                    } else {
                        if (mTFraction.getLeftDelimiter() == null && mTFraction.getRightDelimiter() == null) {
                            str = "atop";
                        } else if (Intrinsics.areEqual(mTFraction.getLeftDelimiter(), "(") && Intrinsics.areEqual(mTFraction.getRightDelimiter(), ")")) {
                            str = "choose";
                        } else if (Intrinsics.areEqual(mTFraction.getLeftDelimiter(), "{") && Intrinsics.areEqual(mTFraction.getRightDelimiter(), StringSubstitutor.DEFAULT_VAR_END)) {
                            str = "brace";
                        } else if (Intrinsics.areEqual(mTFraction.getLeftDelimiter(), "[") && Intrinsics.areEqual(mTFraction.getRightDelimiter(), "]")) {
                            str = "brack";
                        } else {
                            str = "atopwithdelims" + ((Object) mTFraction.getLeftDelimiter()) + ((Object) mTFraction.getRightDelimiter());
                        }
                        sb.append("{" + latexString + " \\" + ((Object) str) + ' ' + latexString2 + CoreConstants.CURLY_RIGHT);
                    }
                } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomRadical) {
                    sb.append(((MTRadical) mTMathAtom).toLatexString());
                } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomInner) {
                    MTInner mTInner = (MTInner) mTMathAtom;
                    MTMathAtom leftBoundary = mTInner.getLeftBoundary();
                    MTMathAtom rightBoundary = mTInner.getRightBoundary();
                    if (leftBoundary != null || rightBoundary != null) {
                        if (leftBoundary != null) {
                            sb.append("\\left" + delimToLatexString(leftBoundary) + ' ');
                        } else {
                            sb.append("\\left. ");
                        }
                        MTMathList innerList = mTInner.getInnerList();
                        if (innerList != null) {
                            sb.append(toLatexString(innerList));
                        }
                        if (rightBoundary != null) {
                            sb.append("\\right" + delimToLatexString(rightBoundary) + ' ');
                        } else {
                            sb.append("\\right. ");
                        }
                    } else {
                        sb.append("{");
                        MTMathList innerList2 = mTInner.getInnerList();
                        if (innerList2 != null) {
                            sb.append(toLatexString(innerList2));
                        }
                        sb.append(StringSubstitutor.DEFAULT_VAR_END);
                    }
                } else {
                    int i = 1;
                    if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomTable) {
                        MTMathTable mTMathTable = (MTMathTable) mTMathAtom;
                        if (mTMathTable.getEnvironment() != null) {
                            sb.append("\\begin{");
                            sb.append(mTMathTable.getEnvironment());
                            sb.append(StringSubstitutor.DEFAULT_VAR_END);
                        }
                        int iNumRows = mTMathTable.numRows();
                        int i2 = 0;
                        while (i2 < iNumRows) {
                            int i3 = i2 + 1;
                            List<MTMathList> list = mTMathTable.getCells().get(i2);
                            int size = list.size();
                            int i4 = 0;
                            while (i4 < size) {
                                int i5 = i4 + 1;
                                MTMathList mTMathList = list.get(i4);
                                if (Intrinsics.areEqual(mTMathTable.getEnvironment(), "matrix") && mTMathList.getAtoms().size() >= i && mTMathList.getAtoms().get(0).getType() == MTMathAtomType.KMTMathAtomStyle) {
                                    mTMathList = new MTMathList(mTMathList.getAtoms().subList(i, mTMathList.getAtoms().size()));
                                }
                                sb.append(toLatexString(((Intrinsics.areEqual(mTMathTable.getEnvironment(), "eqalign") || Intrinsics.areEqual(mTMathTable.getEnvironment(), "aligned") || Intrinsics.areEqual(mTMathTable.getEnvironment(), "split")) && i4 == i && mTMathList.getAtoms().size() >= i && mTMathList.getAtoms().get(0).getType() == MTMathAtomType.KMTMathAtomOrdinary && mTMathList.getAtoms().get(0).getNucleus().length() == 0) ? new MTMathList(mTMathList.getAtoms().subList(1, mTMathList.getAtoms().size())) : mTMathList));
                                if (i4 < list.size() - 1) {
                                    sb.append("&");
                                }
                                i4 = i5;
                                i = 1;
                            }
                            if (i2 < mTMathTable.numRows() - 1) {
                                sb.append("\\\\ ");
                            }
                            i2 = i3;
                            i = 1;
                        }
                        if (mTMathTable.getEnvironment() != null) {
                            sb.append("\\end{");
                            sb.append(mTMathTable.getEnvironment());
                            sb.append(StringSubstitutor.DEFAULT_VAR_END);
                        }
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomOverline) {
                        sb.append("\\overline");
                        sb.append(((MTOverLine) mTMathAtom).toLatexString());
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomUnderline) {
                        sb.append("\\underline");
                        sb.append(((MTUnderLine) mTMathAtom).toLatexString());
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomAccent) {
                        MTAccent mTAccent = (MTAccent) mTMathAtom;
                        sb.append(Intrinsics.stringPlus("\\", MTMathAtom.INSTANCE.accentName(mTAccent)));
                        sb.append(mTAccent.toLatexString());
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomLargeOperator) {
                        MTLargeOperator mTLargeOperator = (MTLargeOperator) mTMathAtom;
                        String strLatexSymbolNameForAtom = MTMathAtom.INSTANCE.latexSymbolNameForAtom(mTMathAtom);
                        if (strLatexSymbolNameForAtom != null) {
                            MTMathAtom mTMathAtomAtomForLatexSymbolName = MTMathAtom.INSTANCE.atomForLatexSymbolName(strLatexSymbolNameForAtom);
                            if (mTMathAtomAtomForLatexSymbolName == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.agog.mathdisplay.parse.MTLargeOperator");
                            }
                            sb.append("\\" + ((Object) strLatexSymbolNameForAtom) + ' ');
                            if (((MTLargeOperator) mTMathAtomAtomForLatexSymbolName).getHasLimits() != mTLargeOperator.getHasLimits()) {
                                if (mTLargeOperator.getHasLimits()) {
                                    sb.append("\\limits ");
                                } else {
                                    sb.append("\\nolimits ");
                                }
                            }
                        }
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomSpace) {
                        MTMathSpace mTMathSpace = (MTMathSpace) mTMathAtom;
                        String str2 = (String) MTMathListBuilder.spaceToCommands.get(Float.valueOf(mTMathSpace.getSpace()));
                        if (str2 != null) {
                            sb.append("\\" + ((Object) str2) + ' ');
                        } else {
                            String str3 = String.format("\\mkern%.1fmu", Arrays.copyOf(new Object[]{Float.valueOf(mTMathSpace.getSpace())}, 1));
                            Intrinsics.checkNotNullExpressionValue(str3, "format(this, *args)");
                            sb.append(str3);
                        }
                    } else if (mTMathAtom.getType() == MTMathAtomType.KMTMathAtomStyle) {
                        sb.append("\\" + MTMathListBuilder.styleToCommands.get(((MTMathStyle) mTMathAtom).getStyle()) + ' ');
                    } else if (mTMathAtom.getNucleus().length() == 0) {
                        sb.append("{}");
                    } else if (Intrinsics.areEqual(mTMathAtom.getNucleus(), "∶")) {
                        sb.append(":");
                    } else if (Intrinsics.areEqual(mTMathAtom.getNucleus(), "−")) {
                        sb.append("-");
                    } else {
                        String strLatexSymbolNameForAtom2 = MTMathAtom.INSTANCE.latexSymbolNameForAtom(mTMathAtom);
                        if (strLatexSymbolNameForAtom2 != null) {
                            sb.append("\\" + ((Object) strLatexSymbolNameForAtom2) + ' ');
                        } else {
                            sb.append(mTMathAtom.getNucleus());
                        }
                    }
                }
                MTMathList superScript = mTMathAtom.getSuperScript();
                if (superScript != null) {
                    sb.append("^{" + toLatexString(superScript) + CoreConstants.CURLY_RIGHT);
                }
                MTMathList subScript = mTMathAtom.getSubScript();
                if (subScript != null) {
                    sb.append("_{" + toLatexString(subScript) + CoreConstants.CURLY_RIGHT);
                }
            }
            if (fontStyle != MTFontStyle.KMTFontStyleDefault) {
                sb.append(StringSubstitutor.DEFAULT_VAR_END);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "str.toString()");
            return string;
        }
    }
}
