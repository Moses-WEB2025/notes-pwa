package org.jsoup.select;

import ch.qos.logback.core.CoreConstants;
import com.google.common.base.Ascii;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.TokenQueue;

/* JADX INFO: loaded from: classes6.dex */
public class QueryParser implements AutoCloseable {
    private boolean inNodeContext;
    private final String query;
    private final TokenQueue tq;
    private static final char[] Combinators = {Typography.greater, '+', '~'};
    private static final String[] AttributeEvals = {"=", "!=", "^=", "$=", "*=", "~="};
    private static final char[] SequenceEnders = {CoreConstants.COMMA_CHAR, CoreConstants.RIGHT_PARENTHESIS_CHAR};
    private static final Pattern NthStepOffset = Pattern.compile("(([+-])?(\\d+)?)n(\\s*([+-])?\\s*\\d+)?", 2);
    private static final Pattern NthOffset = Pattern.compile("([+-])?(\\d+)");

    private QueryParser(String str) {
        Validate.notEmpty(str);
        String strTrim = str.trim();
        this.query = strTrim;
        this.tq = new TokenQueue(strTrim);
    }

    public static Evaluator parse(String str) {
        try {
            QueryParser queryParser = new QueryParser(str);
            try {
                Evaluator evaluator = queryParser.parse();
                queryParser.close();
                return evaluator;
            } catch (Throwable th) {
                try {
                    queryParser.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IllegalArgumentException e) {
            throw new Selector.SelectorParseException(e.getMessage());
        }
    }

    Evaluator parse() {
        Evaluator selectorGroup = parseSelectorGroup();
        this.tq.consumeWhitespace();
        if (this.tq.isEmpty()) {
            return selectorGroup;
        }
        throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
    }

    Evaluator parseSelectorGroup() {
        Evaluator selector = parseSelector();
        while (this.tq.matchChomp(CoreConstants.COMMA_CHAR)) {
            selector = or(selector, parseSelector());
        }
        return selector;
    }

    Evaluator parseSelector() {
        Evaluator simpleSequence;
        this.tq.consumeWhitespace();
        if (this.tq.matchesAny(Combinators)) {
            simpleSequence = new StructuralEvaluator.Root();
        } else {
            simpleSequence = parseSimpleSequence();
        }
        while (true) {
            char cConsume = this.tq.consumeWhitespace() ? ' ' : (char) 0;
            if (this.tq.matchesAny(Combinators)) {
                cConsume = this.tq.consume();
            } else {
                if (!this.tq.matchesAny(SequenceEnders)) {
                    break;
                }
                break;
            }
            if (cConsume == 0) {
                break;
            }
            simpleSequence = combinator(simpleSequence, cConsume, parseSimpleSequence());
        }
        return simpleSequence;
    }

    Evaluator parseSimpleSequence() {
        Evaluator evaluatorByTag;
        this.tq.consumeWhitespace();
        if (this.tq.matchesWord() || this.tq.matches("*|")) {
            evaluatorByTag = byTag();
        } else {
            evaluatorByTag = this.tq.matchChomp('*') ? new Evaluator.AllElements() : null;
        }
        while (true) {
            Evaluator subclass = parseSubclass();
            if (subclass == null) {
                break;
            }
            evaluatorByTag = and(evaluatorByTag, subclass);
        }
        if (evaluatorByTag != null) {
            return evaluatorByTag;
        }
        throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
    }

    static Evaluator combinator(Evaluator evaluator, char c, Evaluator evaluator2) {
        if (c == ' ') {
            return and(new StructuralEvaluator.Ancestor(evaluator), evaluator2);
        }
        if (c == '+') {
            return and(new StructuralEvaluator.ImmediatePreviousSibling(evaluator), evaluator2);
        }
        if (c != '>') {
            if (c == '~') {
                return and(new StructuralEvaluator.PreviousSibling(evaluator), evaluator2);
            }
            throw new Selector.SelectorParseException("Unknown combinator '%s'", Character.valueOf(c));
        }
        StructuralEvaluator.ImmediateParentRun immediateParentRun = evaluator instanceof StructuralEvaluator.ImmediateParentRun ? (StructuralEvaluator.ImmediateParentRun) evaluator : new StructuralEvaluator.ImmediateParentRun(evaluator);
        immediateParentRun.add(evaluator2);
        return immediateParentRun;
    }

    Evaluator parseSubclass() {
        if (this.tq.matchChomp('#')) {
            return byId();
        }
        if (this.tq.matchChomp('.')) {
            return byClass();
        }
        if (this.tq.matches('[')) {
            return byAttribute();
        }
        if (this.tq.matchChomp("::")) {
            return parseNodeSelector();
        }
        if (this.tq.matchChomp(CoreConstants.COLON_CHAR)) {
            return parsePseudoSelector();
        }
        return null;
    }

    static Evaluator or(Evaluator evaluator, Evaluator evaluator2) {
        if (evaluator instanceof CombiningEvaluator.Or) {
            ((CombiningEvaluator.Or) evaluator).add(evaluator2);
            return evaluator;
        }
        return new CombiningEvaluator.Or(evaluator, evaluator2);
    }

    static Evaluator and(Evaluator evaluator, Evaluator evaluator2) {
        if (evaluator == null) {
            return evaluator2;
        }
        if (evaluator instanceof CombiningEvaluator.And) {
            ((CombiningEvaluator.And) evaluator).add(evaluator2);
            return evaluator;
        }
        return new CombiningEvaluator.And(evaluator, evaluator2);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private Evaluator parsePseudoSelector() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        strConsumeCssIdentifier.hashCode();
        byte b = -1;
        switch (strConsumeCssIdentifier.hashCode()) {
            case -2141736343:
                if (strConsumeCssIdentifier.equals("containsData")) {
                    b = 0;
                }
                break;
            case -2136991809:
                if (strConsumeCssIdentifier.equals("first-child")) {
                    b = 1;
                }
                break;
            case -1939921007:
                if (strConsumeCssIdentifier.equals("matchesWholeText")) {
                    b = 2;
                }
                break;
            case -1754914063:
                if (strConsumeCssIdentifier.equals("nth-child")) {
                    b = 3;
                }
                break;
            case -1629748624:
                if (strConsumeCssIdentifier.equals("nth-last-child")) {
                    b = 4;
                }
                break;
            case -947996741:
                if (strConsumeCssIdentifier.equals("only-child")) {
                    b = 5;
                }
                break;
            case -897532411:
                if (strConsumeCssIdentifier.equals("nth-of-type")) {
                    b = 6;
                }
                break;
            case -872629820:
                if (strConsumeCssIdentifier.equals("nth-last-of-type")) {
                    b = 7;
                }
                break;
            case -567445985:
                if (strConsumeCssIdentifier.equals("contains")) {
                    b = 8;
                }
                break;
            case -55413797:
                if (strConsumeCssIdentifier.equals("containsWholeOwnText")) {
                    b = 9;
                }
                break;
            case R2.color.global_ai_input_paste_btn_text_color /* 3244 */:
                if (strConsumeCssIdentifier.equals("eq")) {
                    b = 10;
                }
                break;
            case R2.color.link_card_link_text_color_arty /* 3309 */:
                if (strConsumeCssIdentifier.equals("gt")) {
                    b = 11;
                }
                break;
            case R2.color.m3_dark_primary_text_disable_only /* 3370 */:
                if (strConsumeCssIdentifier.equals("is")) {
                    b = 12;
                }
                break;
            case R2.color.m3_ref_palette_dynamic_primary80 /* 3464 */:
                if (strConsumeCssIdentifier.equals("lt")) {
                    b = 13;
                }
                break;
            case 103066:
                if (strConsumeCssIdentifier.equals("has")) {
                    b = 14;
                }
                break;
            case 109267:
                if (strConsumeCssIdentifier.equals("not")) {
                    b = 15;
                }
                break;
            case 3506402:
                if (strConsumeCssIdentifier.equals(Constants.ROOT)) {
                    b = 16;
                }
                break;
            case 93819220:
                if (strConsumeCssIdentifier.equals("blank")) {
                    b = 17;
                }
                break;
            case 96634189:
                if (strConsumeCssIdentifier.equals("empty")) {
                    b = 18;
                }
                break;
            case 208017639:
                if (strConsumeCssIdentifier.equals("containsOwn")) {
                    b = 19;
                }
                break;
            case 614017170:
                if (strConsumeCssIdentifier.equals("matchText")) {
                    b = Ascii.DC4;
                }
                break;
            case 835834661:
                if (strConsumeCssIdentifier.equals("last-child")) {
                    b = Ascii.NAK;
                }
                break;
            case 840862003:
                if (strConsumeCssIdentifier.equals("matches")) {
                    b = Ascii.SYN;
                }
                break;
            case 1255901423:
                if (strConsumeCssIdentifier.equals("matchesWholeOwnText")) {
                    b = Ascii.ETB;
                }
                break;
            case 1292941139:
                if (strConsumeCssIdentifier.equals("first-of-type")) {
                    b = Ascii.CAN;
                }
                break;
            case 1455900751:
                if (strConsumeCssIdentifier.equals("only-of-type")) {
                    b = Ascii.EM;
                }
                break;
            case 1870740819:
                if (strConsumeCssIdentifier.equals("matchesOwn")) {
                    b = Ascii.SUB;
                }
                break;
            case 2014184485:
                if (strConsumeCssIdentifier.equals("containsWholeText")) {
                    b = Ascii.ESC;
                }
                break;
            case 2025926969:
                if (strConsumeCssIdentifier.equals("last-of-type")) {
                    b = Ascii.FS;
                }
                break;
        }
        switch (b) {
            case 0:
                return containsData();
            case 1:
                return new Evaluator.IsFirstChild();
            case 2:
                return matchesWholeText(false);
            case 3:
                return cssNthChild(false, false);
            case 4:
                return cssNthChild(true, false);
            case 5:
                return new Evaluator.IsOnlyChild();
            case 6:
                return cssNthChild(false, true);
            case 7:
                return cssNthChild(true, true);
            case 8:
                return contains(false);
            case 9:
                return containsWholeText(true);
            case 10:
                return new Evaluator.IndexEquals(consumeIndex());
            case 11:
                return new Evaluator.IndexGreaterThan(consumeIndex());
            case 12:
                return is();
            case 13:
                return new Evaluator.IndexLessThan(consumeIndex());
            case 14:
                return has();
            case 15:
                return not();
            case 16:
                return new Evaluator.IsRoot();
            case 17:
                return new NodeEvaluator.BlankValue();
            case 18:
                return new Evaluator.IsEmpty();
            case 19:
                return contains(true);
            case 20:
                return new Evaluator.MatchText();
            case 21:
                return new Evaluator.IsLastChild();
            case 22:
                return matches(false);
            case 23:
                return matchesWholeText(true);
            case 24:
                return new Evaluator.IsFirstOfType();
            case 25:
                return new Evaluator.IsOnlyOfType();
            case 26:
                return matches(true);
            case 27:
                return containsWholeText(false);
            case 28:
                return new Evaluator.IsLastOfType();
            default:
                throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
        }
    }

    private Evaluator parseNodeSelector() {
        Evaluator instanceType;
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        byte b = 1;
        this.inNodeContext = true;
        strConsumeCssIdentifier.hashCode();
        switch (strConsumeCssIdentifier.hashCode()) {
            case 3076010:
                b = !strConsumeCssIdentifier.equals("data") ? (byte) -1 : (byte) 0;
                break;
            case 3386882:
                if (!strConsumeCssIdentifier.equals("node")) {
                    b = -1;
                }
                break;
            case 3556653:
                b = !strConsumeCssIdentifier.equals("text") ? (byte) -1 : (byte) 2;
                break;
            case 94504589:
                b = !strConsumeCssIdentifier.equals("cdata") ? (byte) -1 : (byte) 3;
                break;
            case 950398559:
                b = !strConsumeCssIdentifier.equals("comment") ? (byte) -1 : (byte) 4;
                break;
            case 1563127392:
                b = !strConsumeCssIdentifier.equals("leafnode") ? (byte) -1 : (byte) 5;
                break;
            default:
                b = -1;
                break;
        }
        switch (b) {
            case 0:
                instanceType = new NodeEvaluator.InstanceType(DataNode.class, strConsumeCssIdentifier);
                break;
            case 1:
                instanceType = new NodeEvaluator.InstanceType(Node.class, strConsumeCssIdentifier);
                break;
            case 2:
                instanceType = new NodeEvaluator.InstanceType(TextNode.class, strConsumeCssIdentifier);
                break;
            case 3:
                instanceType = new NodeEvaluator.InstanceType(CDataNode.class, strConsumeCssIdentifier);
                break;
            case 4:
                instanceType = new NodeEvaluator.InstanceType(Comment.class, strConsumeCssIdentifier);
                break;
            case 5:
                instanceType = new NodeEvaluator.InstanceType(LeafNode.class, strConsumeCssIdentifier);
                break;
            default:
                throw new Selector.SelectorParseException("Could not parse query '%s': unknown node type '::%s'", this.query, strConsumeCssIdentifier);
        }
        while (true) {
            Evaluator subclass = parseSubclass();
            if (subclass != null) {
                instanceType = and(instanceType, subclass);
            } else {
                this.inNodeContext = false;
                return instanceType;
            }
        }
    }

    private Evaluator byId() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        return new Evaluator.Id(strConsumeCssIdentifier);
    }

    private Evaluator byClass() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        return new Evaluator.Class(strConsumeCssIdentifier.trim());
    }

    private Evaluator byTag() {
        String strNormalize = Normalizer.normalize(this.tq.consumeElementSelector());
        Validate.notEmpty(strNormalize);
        if (strNormalize.startsWith("*|")) {
            String strSubstring = strNormalize.substring(2);
            return new CombiningEvaluator.Or(new Evaluator.Tag(strSubstring), new Evaluator.TagEndsWith(":" + strSubstring));
        }
        if (strNormalize.endsWith("|*")) {
            return new Evaluator.TagStartsWith(strNormalize.substring(0, strNormalize.length() - 2) + ":");
        }
        if (strNormalize.contains("|")) {
            strNormalize = strNormalize.replace("|", ":");
        }
        return new Evaluator.Tag(strNormalize);
    }

    private Evaluator byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.tq.chompBalanced('[', ']'));
        try {
            Evaluator evaluatorEvaluatorForAttribute = evaluatorForAttribute(tokenQueue);
            tokenQueue.close();
            return evaluatorEvaluatorForAttribute;
        } catch (Throwable th) {
            try {
                tokenQueue.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private Evaluator evaluatorForAttribute(TokenQueue tokenQueue) {
        String strConsumeToAny = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(strConsumeToAny);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (strConsumeToAny.startsWith("^")) {
                return new Evaluator.AttributeStarting(strConsumeToAny.substring(1));
            }
            if (strConsumeToAny.equals("*")) {
                return new Evaluator.AttributeStarting("");
            }
            return new Evaluator.Attribute(strConsumeToAny);
        }
        if (tokenQueue.matchChomp('=')) {
            return new Evaluator.AttributeWithValue(strConsumeToAny, tokenQueue.remainder());
        }
        if (tokenQueue.matchChomp("!=")) {
            return new Evaluator.AttributeWithValueNot(strConsumeToAny, tokenQueue.remainder());
        }
        if (tokenQueue.matchChomp("^=")) {
            return new Evaluator.AttributeWithValueStarting(strConsumeToAny, tokenQueue.remainder());
        }
        if (tokenQueue.matchChomp("$=")) {
            return new Evaluator.AttributeWithValueEnding(strConsumeToAny, tokenQueue.remainder());
        }
        if (tokenQueue.matchChomp("*=")) {
            return new Evaluator.AttributeWithValueContaining(strConsumeToAny, tokenQueue.remainder());
        }
        if (tokenQueue.matchChomp("~=")) {
            return new Evaluator.AttributeWithValueMatching(strConsumeToAny, Pattern.compile(tokenQueue.remainder()));
        }
        throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001d A[PHI: r2
  0x001d: PHI (r2v7 int) = (r2v0 int), (r2v5 int) binds: [B:6:0x001b, B:18:0x0057] A[DONT_GENERATE, DONT_INLINE]] */
    private Evaluator cssNthChild(boolean z, boolean z2) {
        String strNormalize = Normalizer.normalize(consumeParens());
        int i = 1;
        int i2 = 2;
        if (!"odd".equals(strNormalize)) {
            if ("even".equals(strNormalize)) {
                i = 0;
            } else {
                Matcher matcher = NthStepOffset.matcher(strNormalize);
                if (matcher.matches()) {
                    if (matcher.group(3) != null) {
                        i2 = Integer.parseInt(matcher.group(1).replaceFirst("^\\+", ""));
                    } else {
                        i2 = "-".equals(matcher.group(2)) ? -1 : 1;
                    }
                    if (matcher.group(4) != null) {
                        i = Integer.parseInt(matcher.group(4).replaceFirst("^\\+", ""));
                    } else {
                        i = 0;
                    }
                } else {
                    Matcher matcher2 = NthOffset.matcher(strNormalize);
                    if (matcher2.matches()) {
                        i = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
                        i2 = 0;
                    } else {
                        throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", strNormalize);
                    }
                }
            }
        }
        if (z2) {
            return z ? new Evaluator.IsNthLastOfType(i2, i) : new Evaluator.IsNthOfType(i2, i);
        }
        return z ? new Evaluator.IsNthLastChild(i2, i) : new Evaluator.IsNthChild(i2, i);
    }

    private String consumeParens() {
        return this.tq.chompBalanced(CoreConstants.LEFT_PARENTHESIS_CHAR, CoreConstants.RIGHT_PARENTHESIS_CHAR);
    }

    private int consumeIndex() {
        String strTrim = consumeParens().trim();
        Validate.isTrue(StringUtil.isNumeric(strTrim), "Index must be numeric");
        return Integer.parseInt(strTrim);
    }

    private Evaluator has() {
        return parseNested(new Function() { // from class: org.jsoup.select.QueryParser$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new StructuralEvaluator.Has((Evaluator) obj);
            }
        }, ":has() must have a selector");
    }

    private Evaluator is() {
        return parseNested(new Function() { // from class: org.jsoup.select.QueryParser$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new StructuralEvaluator.Is((Evaluator) obj);
            }
        }, ":is() must have a selector");
    }

    private Evaluator parseNested(Function<Evaluator, Evaluator> function, String str) {
        Validate.isTrue(this.tq.matchChomp(CoreConstants.LEFT_PARENTHESIS_CHAR), str);
        Evaluator selectorGroup = parseSelectorGroup();
        Validate.isTrue(this.tq.matchChomp(CoreConstants.RIGHT_PARENTHESIS_CHAR), str);
        return function.apply(selectorGroup);
    }

    private Evaluator contains(boolean z) {
        String str = z ? ":containsOwn" : ":contains";
        String strUnescape = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(strUnescape, str.concat("(text) query must not be empty"));
        if (this.inNodeContext) {
            return new NodeEvaluator.ContainsValue(strUnescape);
        }
        if (z) {
            return new Evaluator.ContainsOwnText(strUnescape);
        }
        return new Evaluator.ContainsText(strUnescape);
    }

    private Evaluator containsWholeText(boolean z) {
        String str = z ? ":containsWholeOwnText" : ":containsWholeText";
        String strUnescape = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(strUnescape, str.concat("(text) query must not be empty"));
        if (z) {
            return new Evaluator.ContainsWholeOwnText(strUnescape);
        }
        return new Evaluator.ContainsWholeText(strUnescape);
    }

    private Evaluator containsData() {
        String strUnescape = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(strUnescape, ":containsData(text) query must not be empty");
        return new Evaluator.ContainsData(strUnescape);
    }

    private Evaluator matches(boolean z) {
        String str = z ? ":matchesOwn" : ":matches";
        String strConsumeParens = consumeParens();
        Validate.notEmpty(strConsumeParens, str.concat("(regex) query must not be empty"));
        Pattern patternCompile = Pattern.compile(strConsumeParens);
        if (this.inNodeContext) {
            return new NodeEvaluator.MatchesValue(patternCompile);
        }
        if (z) {
            return new Evaluator.MatchesOwn(patternCompile);
        }
        return new Evaluator.Matches(patternCompile);
    }

    private Evaluator matchesWholeText(boolean z) {
        String str = z ? ":matchesWholeOwnText" : ":matchesWholeText";
        String strConsumeParens = consumeParens();
        Validate.notEmpty(strConsumeParens, str.concat("(regex) query must not be empty"));
        if (z) {
            return new Evaluator.MatchesWholeOwnText(Pattern.compile(strConsumeParens));
        }
        return new Evaluator.MatchesWholeText(Pattern.compile(strConsumeParens));
    }

    private Evaluator not() {
        String strConsumeParens = consumeParens();
        Validate.notEmpty(strConsumeParens, ":not(selector) subselect must not be empty");
        return new StructuralEvaluator.Not(parse(strConsumeParens));
    }

    public String toString() {
        return this.query;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.tq.close();
    }
}
