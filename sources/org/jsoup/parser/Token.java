package org.jsoup.parser;

import okio.Utf8;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Range;

/* JADX INFO: loaded from: classes6.dex */
abstract class Token {
    static final int UnsetPos = -1;
    int endPos;
    int startPos;
    final TokenType type;

    public enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        XmlDecl,
        EOF
    }

    private Token(TokenType tokenType) {
        this.endPos = -1;
        this.type = tokenType;
    }

    String tokenType() {
        return getClass().getSimpleName();
    }

    Token reset() {
        this.startPos = -1;
        this.endPos = -1;
        return this;
    }

    int startPos() {
        return this.startPos;
    }

    void startPos(int i) {
        this.startPos = i;
    }

    int endPos() {
        return this.endPos;
    }

    void endPos(int i) {
        this.endPos = i;
    }

    static final class Doctype extends Token {
        boolean forceQuirks;
        final TokenData name;
        String pubSysKey;
        final TokenData publicIdentifier;
        final TokenData systemIdentifier;

        Doctype() {
            super(TokenType.Doctype);
            this.name = new TokenData();
            this.pubSysKey = null;
            this.publicIdentifier = new TokenData();
            this.systemIdentifier = new TokenData();
            this.forceQuirks = false;
        }

        @Override // org.jsoup.parser.Token
        Token reset() {
            super.reset();
            this.name.reset();
            this.pubSysKey = null;
            this.publicIdentifier.reset();
            this.systemIdentifier.reset();
            this.forceQuirks = false;
            return this;
        }

        String getName() {
            return this.name.value();
        }

        String getPubSysKey() {
            return this.pubSysKey;
        }

        String getPublicIdentifier() {
            return this.publicIdentifier.value();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.value();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }

        public String toString() {
            return "<!doctype " + getName() + ">";
        }
    }

    static abstract class Tag extends Token {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int MaxAttributes = 512;
        private final TokenData attrName;
        int attrNameEnd;
        int attrNameStart;
        int attrValEnd;
        int attrValStart;
        private final TokenData attrValue;
        Attributes attributes;
        private boolean hasEmptyAttrValue;
        protected String normalName;
        boolean selfClosing;
        protected TokenData tagName;
        final boolean trackSource;
        final TreeBuilder treeBuilder;

        public abstract String toString();

        Tag(TokenType tokenType, TreeBuilder treeBuilder) {
            super(tokenType);
            this.tagName = new TokenData();
            this.selfClosing = false;
            this.attrName = new TokenData();
            this.attrValue = new TokenData();
            this.hasEmptyAttrValue = false;
            this.treeBuilder = treeBuilder;
            this.trackSource = treeBuilder.trackSourceRange;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.Token
        public Tag reset() {
            super.reset();
            this.tagName.reset();
            this.normalName = null;
            this.selfClosing = false;
            this.attributes = null;
            resetPendingAttr();
            return this;
        }

        private void resetPendingAttr() {
            this.attrName.reset();
            this.attrValue.reset();
            this.hasEmptyAttrValue = false;
            if (this.trackSource) {
                this.attrValEnd = -1;
                this.attrValStart = -1;
                this.attrNameEnd = -1;
                this.attrNameStart = -1;
            }
        }

        final void newAttribute() {
            String strValue;
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.attrName.hasData() && this.attributes.size() < 512) {
                String strTrim = this.attrName.value().trim();
                if (!strTrim.isEmpty()) {
                    if (this.attrValue.hasData()) {
                        strValue = this.attrValue.value();
                    } else {
                        strValue = this.hasEmptyAttrValue ? "" : null;
                    }
                    this.attributes.add(strTrim, strValue);
                    trackAttributeRange(strTrim);
                }
            }
            resetPendingAttr();
        }

        private void trackAttributeRange(String str) {
            if (this.trackSource && isStartTag()) {
                StartTag startTagAsStartTag = asStartTag();
                CharacterReader characterReader = startTagAsStartTag.treeBuilder.reader;
                if (!startTagAsStartTag.treeBuilder.settings.preserveAttributeCase()) {
                    str = Normalizer.lowerCase(str);
                }
                if (this.attributes.sourceRange(str).nameRange().isTracked()) {
                    return;
                }
                if (!this.attrValue.hasData()) {
                    int i = this.attrNameEnd;
                    this.attrValEnd = i;
                    this.attrValStart = i;
                }
                int i2 = this.attrNameStart;
                Range.Position position = new Range.Position(i2, characterReader.lineNumber(i2), characterReader.columnNumber(this.attrNameStart));
                int i3 = this.attrNameEnd;
                Range range = new Range(position, new Range.Position(i3, characterReader.lineNumber(i3), characterReader.columnNumber(this.attrNameEnd)));
                int i4 = this.attrValStart;
                Range.Position position2 = new Range.Position(i4, characterReader.lineNumber(i4), characterReader.columnNumber(this.attrValStart));
                int i5 = this.attrValEnd;
                this.attributes.sourceRange(str, new Range.AttributeRange(range, new Range(position2, new Range.Position(i5, characterReader.lineNumber(i5), characterReader.columnNumber(this.attrValEnd)))));
            }
        }

        final boolean hasAttributes() {
            return this.attributes != null;
        }

        final boolean hasAttributeIgnoreCase(String str) {
            Attributes attributes = this.attributes;
            return attributes != null && attributes.hasKeyIgnoreCase(str);
        }

        final void finaliseTag() {
            if (this.attrName.hasData()) {
                newAttribute();
            }
        }

        final String name() {
            return this.tagName.value();
        }

        final String normalName() {
            String str = this.normalName;
            Validate.isFalse(str == null || str.isEmpty());
            return this.normalName;
        }

        final String toStringName() {
            String strValue = this.tagName.value();
            return strValue.isEmpty() ? "[unset]" : strValue;
        }

        final Tag name(String str) {
            this.tagName.set(str);
            this.normalName = ParseSettings.normalName(this.tagName.value());
            return this;
        }

        final boolean isSelfClosing() {
            return this.selfClosing;
        }

        final void appendTagName(String str) {
            this.tagName.append(str.replace((char) 0, Utf8.REPLACEMENT_CHARACTER));
            this.normalName = ParseSettings.normalName(this.tagName.value());
        }

        final void appendTagName(char c) {
            appendTagName(String.valueOf(c));
        }

        final void appendAttributeName(String str, int i, int i2) {
            this.attrName.append(str.replace((char) 0, Utf8.REPLACEMENT_CHARACTER));
            attrNamePos(i, i2);
        }

        final void appendAttributeName(char c, int i, int i2) {
            this.attrName.append(c);
            attrNamePos(i, i2);
        }

        final void appendAttributeValue(String str, int i, int i2) {
            this.attrValue.append(str);
            attrValPos(i, i2);
        }

        final void appendAttributeValue(char c, int i, int i2) {
            this.attrValue.append(c);
            attrValPos(i, i2);
        }

        final void appendAttributeValue(int[] iArr, int i, int i2) {
            for (int i3 : iArr) {
                this.attrValue.appendCodePoint(i3);
            }
            attrValPos(i, i2);
        }

        final void setEmptyAttributeValue() {
            this.hasEmptyAttrValue = true;
        }

        private void attrNamePos(int i, int i2) {
            if (this.trackSource) {
                int i3 = this.attrNameStart;
                if (i3 > -1) {
                    i = i3;
                }
                this.attrNameStart = i;
                this.attrNameEnd = i2;
            }
        }

        private void attrValPos(int i, int i2) {
            if (this.trackSource) {
                int i3 = this.attrValStart;
                if (i3 > -1) {
                    i = i3;
                }
                this.attrValStart = i;
                this.attrValEnd = i2;
            }
        }
    }

    static final class StartTag extends Tag {
        StartTag(TreeBuilder treeBuilder) {
            super(TokenType.StartTag, treeBuilder);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.Token.Tag, org.jsoup.parser.Token
        public Tag reset() {
            super.reset();
            this.attributes = null;
            return this;
        }

        StartTag nameAttr(String str, Attributes attributes) {
            this.tagName.set(str);
            this.attributes = attributes;
            this.normalName = ParseSettings.normalName(str);
            return this;
        }

        @Override // org.jsoup.parser.Token.Tag
        public String toString() {
            String str = isSelfClosing() ? "/>" : ">";
            if (hasAttributes() && this.attributes.size() > 0) {
                return "<" + toStringName() + StringUtils.SPACE + this.attributes.toString() + str;
            }
            return "<" + toStringName() + str;
        }
    }

    static final class EndTag extends Tag {
        EndTag(TreeBuilder treeBuilder) {
            super(TokenType.EndTag, treeBuilder);
        }

        @Override // org.jsoup.parser.Token.Tag
        public String toString() {
            return "</" + toStringName() + ">";
        }
    }

    static final class Comment extends Token {
        boolean bogus;
        private final TokenData data;

        @Override // org.jsoup.parser.Token
        Token reset() {
            super.reset();
            this.data.reset();
            this.bogus = false;
            return this;
        }

        Comment() {
            super(TokenType.Comment);
            this.data = new TokenData();
            this.bogus = false;
        }

        String getData() {
            return this.data.value();
        }

        Comment append(String str) {
            this.data.append(str);
            return this;
        }

        Comment append(char c) {
            this.data.append(c);
            return this;
        }

        public String toString() {
            return "<!--" + getData() + "-->";
        }
    }

    static class Character extends Token {
        final TokenData data;

        Character() {
            super(TokenType.Character);
            this.data = new TokenData();
        }

        Character(Character character) {
            super(TokenType.Character);
            TokenData tokenData = new TokenData();
            this.data = tokenData;
            this.startPos = character.startPos;
            this.endPos = character.endPos;
            tokenData.set(character.data.value());
        }

        @Override // org.jsoup.parser.Token
        Token reset() {
            super.reset();
            this.data.reset();
            return this;
        }

        Character data(String str) {
            this.data.set(str);
            return this;
        }

        Character append(String str) {
            this.data.append(str);
            return this;
        }

        String getData() {
            return this.data.value();
        }

        public String toString() {
            return getData();
        }
    }

    static final class CData extends Character {
        CData(String str) {
            data(str);
        }

        @Override // org.jsoup.parser.Token.Character
        public String toString() {
            return "<![CDATA[" + getData() + "]]>";
        }
    }

    static final class XmlDecl extends Tag {
        boolean isDeclaration;

        public XmlDecl(TreeBuilder treeBuilder) {
            super(TokenType.XmlDecl, treeBuilder);
            this.isDeclaration = true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.Token.Tag, org.jsoup.parser.Token
        public XmlDecl reset() {
            super.reset();
            this.isDeclaration = true;
            return this;
        }

        @Override // org.jsoup.parser.Token.Tag
        public String toString() {
            boolean z = this.isDeclaration;
            String str = z ? "<!" : "<?";
            String str2 = z ? ">" : "?>";
            if (hasAttributes() && this.attributes.size() > 0) {
                return str + toStringName() + StringUtils.SPACE + this.attributes.toString() + str2;
            }
            return str + toStringName() + str2;
        }
    }

    static final class EOF extends Token {
        EOF() {
            super(TokenType.EOF);
        }

        @Override // org.jsoup.parser.Token
        Token reset() {
            super.reset();
            return this;
        }

        public String toString() {
            return "";
        }
    }

    final boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }

    final Doctype asDoctype() {
        return (Doctype) this;
    }

    final boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }

    final StartTag asStartTag() {
        return (StartTag) this;
    }

    final boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }

    final EndTag asEndTag() {
        return (EndTag) this;
    }

    final boolean isComment() {
        return this.type == TokenType.Comment;
    }

    final Comment asComment() {
        return (Comment) this;
    }

    final boolean isCharacter() {
        return this.type == TokenType.Character;
    }

    final boolean isCData() {
        return this instanceof CData;
    }

    final Character asCharacter() {
        return (Character) this;
    }

    final XmlDecl asXmlDecl() {
        return (XmlDecl) this;
    }

    final boolean isEOF() {
        return this.type == TokenType.EOF;
    }
}
