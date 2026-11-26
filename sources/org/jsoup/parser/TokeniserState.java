package org.jsoup.parser;

import ch.qos.logback.core.CoreConstants;
import kotlin.text.Typography;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;

/* JADX INFO: loaded from: classes6.dex */
enum TokeniserState {
    Data { // from class: org.jsoup.parser.TokeniserState.1
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                tokeniser.emit(characterReader.consume());
            } else {
                if (cCurrent == '&') {
                    tokeniser.advanceTransition(CharacterReferenceInData);
                    return;
                }
                if (cCurrent == '<') {
                    tokeniser.advanceTransition(TagOpen);
                } else if (cCurrent == 65535) {
                    tokeniser.emit(new Token.EOF());
                } else {
                    tokeniser.emit(characterReader.consumeData());
                }
            }
        }
    },
    CharacterReferenceInData { // from class: org.jsoup.parser.TokeniserState.2
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readCharRef(tokeniser, Data);
        }
    },
    Rcdata { // from class: org.jsoup.parser.TokeniserState.3
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit((char) 65533);
            } else {
                if (cCurrent == '&') {
                    tokeniser.advanceTransition(CharacterReferenceInRcdata);
                    return;
                }
                if (cCurrent == '<') {
                    tokeniser.advanceTransition(RcdataLessthanSign);
                } else if (cCurrent == 65535) {
                    tokeniser.emit(new Token.EOF());
                } else {
                    tokeniser.emit(characterReader.consumeData());
                }
            }
        }
    },
    CharacterReferenceInRcdata { // from class: org.jsoup.parser.TokeniserState.4
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readCharRef(tokeniser, Rcdata);
        }
    },
    Rawtext { // from class: org.jsoup.parser.TokeniserState.5
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readRawData(tokeniser, characterReader, this, RawtextLessthanSign);
        }
    },
    ScriptData { // from class: org.jsoup.parser.TokeniserState.6
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readRawData(tokeniser, characterReader, this, ScriptDataLessthanSign);
        }
    },
    PLAINTEXT { // from class: org.jsoup.parser.TokeniserState.7
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit((char) 65533);
            } else if (cCurrent == 65535) {
                tokeniser.emit(new Token.EOF());
            } else {
                tokeniser.emit(characterReader.consumeTo((char) 0));
            }
        }
    },
    TagOpen { // from class: org.jsoup.parser.TokeniserState.8
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == '!') {
                tokeniser.advanceTransition(MarkupDeclarationOpen);
                return;
            }
            if (cCurrent == '/') {
                tokeniser.advanceTransition(EndTagOpen);
                return;
            }
            if (cCurrent == '?') {
                if (tokeniser.syntax == Document.OutputSettings.Syntax.xml) {
                    tokeniser.advanceTransition(MarkupProcessingOpen);
                    return;
                } else {
                    tokeniser.createBogusCommentPending();
                    tokeniser.transition(BogusComment);
                    return;
                }
            }
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createTagPending(true);
                tokeniser.transition(TagName);
            } else {
                tokeniser.error(this);
                tokeniser.emit(Typography.less);
                tokeniser.transition(Data);
            }
        }
    },
    EndTagOpen { // from class: org.jsoup.parser.TokeniserState.9
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.emit("</");
                tokeniser.transition(Data);
            } else if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createTagPending(false);
                tokeniser.transition(TagName);
            } else if (characterReader.matches(Typography.greater)) {
                tokeniser.error(this);
                tokeniser.advanceTransition(Data);
            } else {
                tokeniser.error(this);
                tokeniser.createBogusCommentPending();
                tokeniser.commentPending.append('/');
                tokeniser.transition(BogusComment);
            }
        }
    },
    TagName { // from class: org.jsoup.parser.TokeniserState.10
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.tagPending.appendTagName(characterReader.consumeTagName());
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.tagPending.appendTagName(TokeniserState.replacementStr);
                return;
            }
            if (cConsume != ' ') {
                if (cConsume == '/') {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                if (cConsume == '>') {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                } else if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                } else if (cConsume != '\t' && cConsume != '\n' && cConsume != '\f' && cConsume != '\r') {
                    tokeniser.tagPending.appendTagName(cConsume);
                    return;
                }
            }
            tokeniser.transition(BeforeAttributeName);
        }
    },
    RcdataLessthanSign { // from class: org.jsoup.parser.TokeniserState.11
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RCDATAEndTagOpen);
            } else if (characterReader.readFully() && characterReader.matchesAsciiAlpha() && tokeniser.appropriateEndTagName() != null && !characterReader.containsIgnoreCase(tokeniser.appropriateEndTagSeq())) {
                tokeniser.tagPending = tokeniser.createTagPending(false).name(tokeniser.appropriateEndTagName());
                tokeniser.emitTagPending();
                tokeniser.transition(TagOpen);
            } else {
                tokeniser.emit(Typography.less);
                tokeniser.transition(Rcdata);
            }
        }
    },
    RCDATAEndTagOpen { // from class: org.jsoup.parser.TokeniserState.12
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(characterReader.current());
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.advanceTransition(RCDATAEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(Rcdata);
        }
    },
    RCDATAEndTagName { // from class: org.jsoup.parser.TokeniserState.13
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                String strConsumeTagName = characterReader.consumeTagName();
                tokeniser.tagPending.appendTagName(strConsumeTagName);
                tokeniser.dataBuffer.append(strConsumeTagName);
                return;
            }
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                if (tokeniser.isAppropriateEndTagToken()) {
                    tokeniser.transition(BeforeAttributeName);
                    return;
                } else {
                    anythingElse(tokeniser, characterReader);
                    return;
                }
            }
            if (cConsume == '/') {
                if (tokeniser.isAppropriateEndTagToken()) {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                } else {
                    anythingElse(tokeniser, characterReader);
                    return;
                }
            }
            if (cConsume == '>') {
                if (tokeniser.isAppropriateEndTagToken()) {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                } else {
                    anythingElse(tokeniser, characterReader);
                    return;
                }
            }
            anythingElse(tokeniser, characterReader);
        }

        private void anythingElse(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.emit("</");
            tokeniser.emit(tokeniser.dataBuffer.value());
            characterReader.unconsume();
            tokeniser.transition(Rcdata);
        }
    },
    RawtextLessthanSign { // from class: org.jsoup.parser.TokeniserState.14
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RawtextEndTagOpen);
            } else {
                tokeniser.emit(Typography.less);
                tokeniser.transition(Rawtext);
            }
        }
    },
    RawtextEndTagOpen { // from class: org.jsoup.parser.TokeniserState.15
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readEndTag(tokeniser, characterReader, RawtextEndTagName, Rawtext);
        }
    },
    RawtextEndTagName { // from class: org.jsoup.parser.TokeniserState.16
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, Rawtext);
        }
    },
    ScriptDataLessthanSign { // from class: org.jsoup.parser.TokeniserState.17
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '!') {
                tokeniser.emit("<!");
                tokeniser.transition(ScriptDataEscapeStart);
                return;
            }
            if (cConsume == '/') {
                tokeniser.createTempBuffer();
                tokeniser.transition(ScriptDataEndTagOpen);
            } else if (cConsume == 65535) {
                tokeniser.emit(Typography.less);
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else {
                tokeniser.emit(Typography.less);
                characterReader.unconsume();
                tokeniser.transition(ScriptData);
            }
        }
    },
    ScriptDataEndTagOpen { // from class: org.jsoup.parser.TokeniserState.18
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readEndTag(tokeniser, characterReader, ScriptDataEndTagName, ScriptData);
        }
    },
    ScriptDataEndTagName { // from class: org.jsoup.parser.TokeniserState.19
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptData);
        }
    },
    ScriptDataEscapeStart { // from class: org.jsoup.parser.TokeniserState.20
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches(CoreConstants.DASH_CHAR)) {
                tokeniser.emit(CoreConstants.DASH_CHAR);
                tokeniser.advanceTransition(ScriptDataEscapeStartDash);
            } else {
                tokeniser.transition(ScriptData);
            }
        }
    },
    ScriptDataEscapeStartDash { // from class: org.jsoup.parser.TokeniserState.21
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches(CoreConstants.DASH_CHAR)) {
                tokeniser.emit(CoreConstants.DASH_CHAR);
                tokeniser.advanceTransition(ScriptDataEscapedDashDash);
            } else {
                tokeniser.transition(ScriptData);
            }
        }
    },
    ScriptDataEscaped { // from class: org.jsoup.parser.TokeniserState.22
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit((char) 65533);
            } else if (cCurrent == '-') {
                tokeniser.emit(CoreConstants.DASH_CHAR);
                tokeniser.advanceTransition(ScriptDataEscapedDash);
            } else if (cCurrent == '<') {
                tokeniser.advanceTransition(ScriptDataEscapedLessthanSign);
            } else {
                tokeniser.emit(characterReader.consumeToAny(CoreConstants.DASH_CHAR, Typography.less, 0));
            }
        }
    },
    ScriptDataEscapedDash { // from class: org.jsoup.parser.TokeniserState.23
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.emit((char) 65533);
                tokeniser.transition(ScriptDataEscaped);
            } else if (cConsume == '-') {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataEscapedDashDash);
            } else if (cConsume == '<') {
                tokeniser.transition(ScriptDataEscapedLessthanSign);
            } else {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedDashDash { // from class: org.jsoup.parser.TokeniserState.24
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.emit((char) 65533);
                tokeniser.transition(ScriptDataEscaped);
            } else {
                if (cConsume == '-') {
                    tokeniser.emit(cConsume);
                    return;
                }
                if (cConsume == '<') {
                    tokeniser.transition(ScriptDataEscapedLessthanSign);
                } else if (cConsume == '>') {
                    tokeniser.emit(cConsume);
                    tokeniser.transition(ScriptData);
                } else {
                    tokeniser.emit(cConsume);
                    tokeniser.transition(ScriptDataEscaped);
                }
            }
        }
    },
    ScriptDataEscapedLessthanSign { // from class: org.jsoup.parser.TokeniserState.25
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createTempBuffer();
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.emit(Typography.less);
                tokeniser.emit(characterReader.current());
                tokeniser.advanceTransition(ScriptDataDoubleEscapeStart);
                return;
            }
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataEscapedEndTagOpen);
            } else {
                tokeniser.emit(Typography.less);
                tokeniser.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedEndTagOpen { // from class: org.jsoup.parser.TokeniserState.26
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(characterReader.current());
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.advanceTransition(ScriptDataEscapedEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(ScriptDataEscaped);
        }
    },
    ScriptDataEscapedEndTagName { // from class: org.jsoup.parser.TokeniserState.27
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscapeStart { // from class: org.jsoup.parser.TokeniserState.28
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataDoubleEscaped, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscaped { // from class: org.jsoup.parser.TokeniserState.29
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit((char) 65533);
            } else if (cCurrent == '-') {
                tokeniser.emit(cCurrent);
                tokeniser.advanceTransition(ScriptDataDoubleEscapedDash);
            } else if (cCurrent == '<') {
                tokeniser.emit(cCurrent);
                tokeniser.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
            } else if (cCurrent == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else {
                tokeniser.emit(characterReader.consumeToAny(CoreConstants.DASH_CHAR, Typography.less, 0));
            }
        }
    },
    ScriptDataDoubleEscapedDash { // from class: org.jsoup.parser.TokeniserState.30
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.emit((char) 65533);
                tokeniser.transition(ScriptDataDoubleEscaped);
            } else if (cConsume == '-') {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataDoubleEscapedDashDash);
            } else if (cConsume == '<') {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
            } else if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataDoubleEscaped);
            }
        }
    },
    ScriptDataDoubleEscapedDashDash { // from class: org.jsoup.parser.TokeniserState.31
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.emit((char) 65533);
                tokeniser.transition(ScriptDataDoubleEscaped);
                return;
            }
            if (cConsume == '-') {
                tokeniser.emit(cConsume);
                return;
            }
            if (cConsume == '<') {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
            } else if (cConsume == '>') {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptData);
            } else if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else {
                tokeniser.emit(cConsume);
                tokeniser.transition(ScriptDataDoubleEscaped);
            }
        }
    },
    ScriptDataDoubleEscapedLessthanSign { // from class: org.jsoup.parser.TokeniserState.32
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.emit('/');
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataDoubleEscapeEnd);
                return;
            }
            tokeniser.transition(ScriptDataDoubleEscaped);
        }
    },
    ScriptDataDoubleEscapeEnd { // from class: org.jsoup.parser.TokeniserState.33
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataEscaped, ScriptDataDoubleEscaped);
        }
    },
    BeforeAttributeName { // from class: org.jsoup.parser.TokeniserState.34
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                characterReader.unconsume();
                tokeniser.error(this);
                tokeniser.tagPending.newAttribute();
                tokeniser.transition(AttributeName);
                return;
            }
            if (cConsume != ' ') {
                if (cConsume != '\"' && cConsume != '\'') {
                    if (cConsume == '/') {
                        tokeniser.transition(SelfClosingStartTag);
                        return;
                    }
                    if (cConsume == 65535) {
                        tokeniser.eofError(this);
                        tokeniser.transition(Data);
                        return;
                    }
                    if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r') {
                        return;
                    }
                    switch (cConsume) {
                        case '>':
                            tokeniser.emitTagPending();
                            tokeniser.transition(Data);
                            return;
                        case '?':
                            if (tokeniser.tagPending instanceof Token.XmlDecl) {
                                return;
                            }
                            break;
                    }
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                    return;
                }
                tokeniser.error(this);
                tokeniser.tagPending.newAttribute();
                tokeniser.tagPending.appendAttributeName(cConsume, characterReader.pos() - 1, characterReader.pos());
                tokeniser.transition(AttributeName);
            }
        }
    },
    AttributeName { // from class: org.jsoup.parser.TokeniserState.35
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            int iPos = characterReader.pos();
            tokeniser.tagPending.appendAttributeName(characterReader.consumeToAnySorted(attributeNameCharsSorted), iPos, characterReader.pos());
            int iPos2 = characterReader.pos();
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(AfterAttributeName);
                return;
            }
            if (cConsume != '\"' && cConsume != '\'') {
                if (cConsume == '/') {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                if (cConsume != 65535) {
                    switch (cConsume) {
                        case '=':
                            tokeniser.transition(BeforeAttributeValue);
                            return;
                        case '>':
                            tokeniser.emitTagPending();
                            tokeniser.transition(Data);
                            return;
                        case '?':
                            if (tokeniser.syntax == Document.OutputSettings.Syntax.xml && (tokeniser.tagPending instanceof Token.XmlDecl)) {
                                tokeniser.transition(AfterAttributeName);
                                return;
                            }
                            break;
                    }
                    tokeniser.tagPending.appendAttributeName(cConsume, iPos2, characterReader.pos());
                    return;
                }
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.tagPending.appendAttributeName(cConsume, iPos2, characterReader.pos());
        }
    },
    AfterAttributeName { // from class: org.jsoup.parser.TokeniserState.36
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeName((char) 65533, characterReader.pos() - 1, characterReader.pos());
                tokeniser.transition(AttributeName);
                return;
            }
            if (cConsume != ' ') {
                if (cConsume != '\"' && cConsume != '\'') {
                    if (cConsume == '/') {
                        tokeniser.transition(SelfClosingStartTag);
                        return;
                    }
                    if (cConsume == 65535) {
                        tokeniser.eofError(this);
                        tokeniser.transition(Data);
                        return;
                    }
                    if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r') {
                        return;
                    }
                    switch (cConsume) {
                        case '<':
                            break;
                        case '=':
                            tokeniser.transition(BeforeAttributeValue);
                            break;
                        case '>':
                            tokeniser.emitTagPending();
                            tokeniser.transition(Data);
                            break;
                        default:
                            tokeniser.tagPending.newAttribute();
                            characterReader.unconsume();
                            tokeniser.transition(AttributeName);
                            break;
                    }
                    return;
                }
                tokeniser.error(this);
                tokeniser.tagPending.newAttribute();
                tokeniser.tagPending.appendAttributeName(cConsume, characterReader.pos() - 1, characterReader.pos());
                tokeniser.transition(AttributeName);
            }
        }
    },
    BeforeAttributeValue { // from class: org.jsoup.parser.TokeniserState.37
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue((char) 65533, characterReader.pos() - 1, characterReader.pos());
                tokeniser.transition(AttributeValue_unquoted);
                return;
            }
            if (cConsume != ' ') {
                if (cConsume == '\"') {
                    tokeniser.transition(AttributeValue_doubleQuoted);
                    return;
                }
                if (cConsume != '`') {
                    if (cConsume == 65535) {
                        tokeniser.eofError(this);
                        tokeniser.emitTagPending();
                        tokeniser.transition(Data);
                        return;
                    }
                    if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r') {
                        return;
                    }
                    if (cConsume == '&') {
                        characterReader.unconsume();
                        tokeniser.transition(AttributeValue_unquoted);
                        return;
                    }
                    if (cConsume == '\'') {
                        tokeniser.transition(AttributeValue_singleQuoted);
                        return;
                    }
                    switch (cConsume) {
                        case '<':
                        case '=':
                            break;
                        case '>':
                            tokeniser.error(this);
                            tokeniser.emitTagPending();
                            tokeniser.transition(Data);
                            break;
                        default:
                            characterReader.unconsume();
                            tokeniser.transition(AttributeValue_unquoted);
                            break;
                    }
                    return;
                }
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue(cConsume, characterReader.pos() - 1, characterReader.pos());
                tokeniser.transition(AttributeValue_unquoted);
            }
        }
    },
    AttributeValue_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.38
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            int iPos = characterReader.pos();
            String strConsumeAttributeQuoted = characterReader.consumeAttributeQuoted(false);
            if (strConsumeAttributeQuoted.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(strConsumeAttributeQuoted, iPos, characterReader.pos());
            } else {
                tokeniser.tagPending.setEmptyAttributeValue();
            }
            int iPos2 = characterReader.pos();
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue((char) 65533, iPos2, characterReader.pos());
                return;
            }
            if (cConsume == '\"') {
                tokeniser.transition(AfterAttributeValue_quoted);
                return;
            }
            if (cConsume != '&') {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                } else {
                    tokeniser.tagPending.appendAttributeValue(cConsume, iPos2, characterReader.pos());
                    return;
                }
            }
            int[] iArrConsumeCharacterReference = tokeniser.consumeCharacterReference('\"', true);
            if (iArrConsumeCharacterReference != null) {
                tokeniser.tagPending.appendAttributeValue(iArrConsumeCharacterReference, iPos2, characterReader.pos());
            } else {
                tokeniser.tagPending.appendAttributeValue(Typography.amp, iPos2, characterReader.pos());
            }
        }
    },
    AttributeValue_singleQuoted { // from class: org.jsoup.parser.TokeniserState.39
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            int iPos = characterReader.pos();
            String strConsumeAttributeQuoted = characterReader.consumeAttributeQuoted(true);
            if (strConsumeAttributeQuoted.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(strConsumeAttributeQuoted, iPos, characterReader.pos());
            } else {
                tokeniser.tagPending.setEmptyAttributeValue();
            }
            int iPos2 = characterReader.pos();
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue((char) 65533, iPos2, characterReader.pos());
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            if (cConsume != '&') {
                if (cConsume == '\'') {
                    tokeniser.transition(AfterAttributeValue_quoted);
                    return;
                } else {
                    tokeniser.tagPending.appendAttributeValue(cConsume, iPos2, characterReader.pos());
                    return;
                }
            }
            int[] iArrConsumeCharacterReference = tokeniser.consumeCharacterReference(Character.valueOf(CoreConstants.SINGLE_QUOTE_CHAR), true);
            if (iArrConsumeCharacterReference != null) {
                tokeniser.tagPending.appendAttributeValue(iArrConsumeCharacterReference, iPos2, characterReader.pos());
            } else {
                tokeniser.tagPending.appendAttributeValue(Typography.amp, iPos2, characterReader.pos());
            }
        }
    },
    AttributeValue_unquoted { // from class: org.jsoup.parser.TokeniserState.40
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            int iPos = characterReader.pos();
            String strConsumeToAnySorted = characterReader.consumeToAnySorted(attributeValueUnquoted);
            if (strConsumeToAnySorted.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(strConsumeToAnySorted, iPos, characterReader.pos());
            }
            int iPos2 = characterReader.pos();
            char cConsume = characterReader.consume();
            if (cConsume != 0) {
                if (cConsume != ' ') {
                    if (cConsume != '\"' && cConsume != '`') {
                        if (cConsume == 65535) {
                            tokeniser.eofError(this);
                            tokeniser.transition(Data);
                            return;
                        }
                        if (cConsume != '\t' && cConsume != '\n' && cConsume != '\f' && cConsume != '\r') {
                            if (cConsume == '&') {
                                int[] iArrConsumeCharacterReference = tokeniser.consumeCharacterReference(Character.valueOf(Typography.greater), true);
                                if (iArrConsumeCharacterReference != null) {
                                    tokeniser.tagPending.appendAttributeValue(iArrConsumeCharacterReference, iPos2, characterReader.pos());
                                    return;
                                } else {
                                    tokeniser.tagPending.appendAttributeValue(Typography.amp, iPos2, characterReader.pos());
                                    return;
                                }
                            }
                            if (cConsume != '\'') {
                                switch (cConsume) {
                                    case '<':
                                    case '=':
                                        break;
                                    case '>':
                                        tokeniser.emitTagPending();
                                        tokeniser.transition(Data);
                                        break;
                                    default:
                                        tokeniser.tagPending.appendAttributeValue(cConsume, iPos2, characterReader.pos());
                                        break;
                                }
                                return;
                            }
                        }
                    }
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue(cConsume, iPos2, characterReader.pos());
                    return;
                }
                tokeniser.transition(BeforeAttributeName);
                return;
            }
            tokeniser.error(this);
            tokeniser.tagPending.appendAttributeValue((char) 65533, iPos2, characterReader.pos());
        }
    },
    AfterAttributeValue_quoted { // from class: org.jsoup.parser.TokeniserState.41
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BeforeAttributeName);
                return;
            }
            if (cConsume == '/') {
                tokeniser.transition(SelfClosingStartTag);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else if (cConsume == '>') {
                tokeniser.emitTagPending();
                tokeniser.transition(Data);
            } else {
                if (cConsume == '?' && (tokeniser.tagPending instanceof Token.XmlDecl)) {
                    return;
                }
                characterReader.unconsume();
                tokeniser.error(this);
                tokeniser.transition(BeforeAttributeName);
            }
        }
    },
    SelfClosingStartTag { // from class: org.jsoup.parser.TokeniserState.42
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '>') {
                tokeniser.tagPending.selfClosing = true;
                tokeniser.emitTagPending();
                tokeniser.transition(Data);
            } else if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            } else {
                characterReader.unconsume();
                tokeniser.error(this);
                tokeniser.transition(BeforeAttributeName);
            }
        }
    },
    BogusComment { // from class: org.jsoup.parser.TokeniserState.43
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.commentPending.append(characterReader.consumeTo(Typography.greater));
            char cCurrent = characterReader.current();
            if (cCurrent == '>' || cCurrent == 65535) {
                characterReader.consume();
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    MarkupDeclarationOpen { // from class: org.jsoup.parser.TokeniserState.44
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchConsume("--")) {
                tokeniser.createCommentPending();
                tokeniser.transition(CommentStart);
                return;
            }
            if (characterReader.matchConsumeIgnoreCase("DOCTYPE")) {
                tokeniser.transition(Doctype);
                return;
            }
            if (characterReader.matchConsume("[CDATA[")) {
                tokeniser.createTempBuffer();
                tokeniser.transition(CdataSection);
            } else if (tokeniser.syntax == Document.OutputSettings.Syntax.xml && characterReader.matchesAsciiAlpha()) {
                tokeniser.createXmlDeclPending(true);
                tokeniser.transition(TagName);
            } else {
                tokeniser.error(this);
                tokeniser.createBogusCommentPending();
                tokeniser.transition(BogusComment);
            }
        }
    },
    MarkupProcessingOpen { // from class: org.jsoup.parser.TokeniserState.45
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createXmlDeclPending(false);
                tokeniser.transition(TagName);
            } else {
                tokeniser.error(this);
                tokeniser.createBogusCommentPending();
                tokeniser.commentPending.append('?');
                tokeniser.transition(BogusComment);
            }
        }
    },
    CommentStart { // from class: org.jsoup.parser.TokeniserState.46
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.append((char) 65533);
                tokeniser.transition(Comment);
                return;
            }
            if (cConsume == '-') {
                tokeniser.transition(CommentStartDash);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else {
                characterReader.unconsume();
                tokeniser.transition(Comment);
            }
        }
    },
    CommentStartDash { // from class: org.jsoup.parser.TokeniserState.47
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.append((char) 65533);
                tokeniser.transition(Comment);
                return;
            }
            if (cConsume == '-') {
                tokeniser.transition(CommentEnd);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else {
                tokeniser.commentPending.append(cConsume);
                tokeniser.transition(Comment);
            }
        }
    },
    Comment { // from class: org.jsoup.parser.TokeniserState.48
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cCurrent = characterReader.current();
            if (cCurrent == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.commentPending.append((char) 65533);
            } else if (cCurrent == '-') {
                tokeniser.advanceTransition(CommentEndDash);
            } else {
                if (cCurrent == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                    return;
                }
                tokeniser.commentPending.append(characterReader.consumeToAny(CoreConstants.DASH_CHAR, 0));
            }
        }
    },
    CommentEndDash { // from class: org.jsoup.parser.TokeniserState.49
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.append(CoreConstants.DASH_CHAR).append((char) 65533);
                tokeniser.transition(Comment);
            } else {
                if (cConsume == '-') {
                    tokeniser.transition(CommentEnd);
                    return;
                }
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                } else {
                    tokeniser.commentPending.append(CoreConstants.DASH_CHAR).append(cConsume);
                    tokeniser.transition(Comment);
                }
            }
        }
    },
    CommentEnd { // from class: org.jsoup.parser.TokeniserState.50
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.append("--").append((char) 65533);
                tokeniser.transition(Comment);
                return;
            }
            if (cConsume == '!') {
                tokeniser.transition(CommentEndBang);
                return;
            }
            if (cConsume == '-') {
                tokeniser.commentPending.append(CoreConstants.DASH_CHAR);
                return;
            }
            if (cConsume == '>') {
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (cConsume != 65535) {
                tokeniser.commentPending.append("--").append(cConsume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    CommentEndBang { // from class: org.jsoup.parser.TokeniserState.51
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.append("--!").append((char) 65533);
                tokeniser.transition(Comment);
                return;
            }
            if (cConsume == '-') {
                tokeniser.commentPending.append("--!");
                tokeniser.transition(CommentEndDash);
                return;
            }
            if (cConsume == '>') {
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (cConsume != 65535) {
                tokeniser.commentPending.append("--!").append(cConsume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    Doctype { // from class: org.jsoup.parser.TokeniserState.52
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BeforeDoctypeName);
                return;
            }
            if (cConsume != '>') {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                } else {
                    tokeniser.error(this);
                    tokeniser.transition(BeforeDoctypeName);
                    return;
                }
            }
            tokeniser.error(this);
            tokeniser.createDoctypePending();
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    },
    BeforeDoctypeName { // from class: org.jsoup.parser.TokeniserState.53
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.createDoctypePending();
                tokeniser.transition(DoctypeName);
                return;
            }
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.createDoctypePending();
                tokeniser.doctypePending.name.append((char) 65533);
                tokeniser.transition(DoctypeName);
                return;
            }
            if (cConsume != ' ') {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.createDoctypePending();
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r') {
                    return;
                }
                tokeniser.createDoctypePending();
                tokeniser.doctypePending.name.append(cConsume);
                tokeniser.transition(DoctypeName);
            }
        }
    },
    DoctypeName { // from class: org.jsoup.parser.TokeniserState.54
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesAsciiAlpha()) {
                tokeniser.doctypePending.name.append(characterReader.consumeLetterSequence());
                return;
            }
            char cConsume = characterReader.consume();
            if (cConsume != 0) {
                if (cConsume != ' ') {
                    if (cConsume == '>') {
                        tokeniser.emitDoctypePending();
                        tokeniser.transition(Data);
                        return;
                    }
                    if (cConsume == 65535) {
                        tokeniser.eofError(this);
                        tokeniser.doctypePending.forceQuirks = true;
                        tokeniser.emitDoctypePending();
                        tokeniser.transition(Data);
                        return;
                    }
                    if (cConsume != '\t' && cConsume != '\n' && cConsume != '\f' && cConsume != '\r') {
                        tokeniser.doctypePending.name.append(cConsume);
                        return;
                    }
                }
                tokeniser.transition(AfterDoctypeName);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.name.append((char) 65533);
        }
    },
    AfterDoctypeName { // from class: org.jsoup.parser.TokeniserState.55
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (characterReader.matchesAny('\t', '\n', CharUtils.CR, '\f', ' ')) {
                characterReader.advance();
                return;
            }
            if (characterReader.matches(Typography.greater)) {
                tokeniser.emitDoctypePending();
                tokeniser.advanceTransition(Data);
                return;
            }
            if (characterReader.matchConsumeIgnoreCase(DocumentType.PUBLIC_KEY)) {
                tokeniser.doctypePending.pubSysKey = DocumentType.PUBLIC_KEY;
                tokeniser.transition(AfterDoctypePublicKeyword);
            } else if (characterReader.matchConsumeIgnoreCase(DocumentType.SYSTEM_KEY)) {
                tokeniser.doctypePending.pubSysKey = DocumentType.SYSTEM_KEY;
                tokeniser.transition(AfterDoctypeSystemKeyword);
            } else {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.advanceTransition(BogusDoctype);
            }
        }
    },
    AfterDoctypePublicKeyword { // from class: org.jsoup.parser.TokeniserState.56
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BeforeDoctypePublicIdentifier);
                return;
            }
            if (cConsume == '\"') {
                tokeniser.error(this);
                tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.error(this);
                tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.transition(BogusDoctype);
        }
    },
    BeforeDoctypePublicIdentifier { // from class: org.jsoup.parser.TokeniserState.57
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                return;
            }
            if (cConsume == '\"') {
                tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.transition(BogusDoctype);
        }
    },
    DoctypePublicIdentifier_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.58
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.publicIdentifier.append((char) 65533);
                return;
            }
            if (cConsume == '\"') {
                tokeniser.transition(AfterDoctypePublicIdentifier);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.doctypePending.publicIdentifier.append(cConsume);
        }
    },
    DoctypePublicIdentifier_singleQuoted { // from class: org.jsoup.parser.TokeniserState.59
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.publicIdentifier.append((char) 65533);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.transition(AfterDoctypePublicIdentifier);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.doctypePending.publicIdentifier.append(cConsume);
        }
    },
    AfterDoctypePublicIdentifier { // from class: org.jsoup.parser.TokeniserState.60
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BetweenDoctypePublicAndSystemIdentifiers);
                return;
            }
            if (cConsume == '\"') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.transition(BogusDoctype);
            }
        }
    },
    BetweenDoctypePublicAndSystemIdentifiers { // from class: org.jsoup.parser.TokeniserState.61
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                return;
            }
            if (cConsume == '\"') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.transition(BogusDoctype);
            }
        }
    },
    AfterDoctypeSystemKeyword { // from class: org.jsoup.parser.TokeniserState.62
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BeforeDoctypeSystemIdentifier);
                return;
            }
            if (cConsume == '\"') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.error(this);
                tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
        }
    },
    BeforeDoctypeSystemIdentifier { // from class: org.jsoup.parser.TokeniserState.63
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                return;
            }
            if (cConsume == '\"') {
                tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.transition(BogusDoctype);
        }
    },
    DoctypeSystemIdentifier_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.64
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.systemIdentifier.append((char) 65533);
                return;
            }
            if (cConsume == '\"') {
                tokeniser.transition(AfterDoctypeSystemIdentifier);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.doctypePending.systemIdentifier.append(cConsume);
        }
    },
    DoctypeSystemIdentifier_singleQuoted { // from class: org.jsoup.parser.TokeniserState.65
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.systemIdentifier.append((char) 65533);
                return;
            }
            if (cConsume == '\'') {
                tokeniser.transition(AfterDoctypeSystemIdentifier);
                return;
            }
            if (cConsume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (cConsume == 65535) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.doctypePending.systemIdentifier.append(cConsume);
        }
    },
    AfterDoctypeSystemIdentifier { // from class: org.jsoup.parser.TokeniserState.66
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                return;
            }
            if (cConsume == '>') {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else {
                if (cConsume == 65535) {
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                tokeniser.error(this);
                tokeniser.transition(BogusDoctype);
            }
        }
    },
    BogusDoctype { // from class: org.jsoup.parser.TokeniserState.67
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char cConsume = characterReader.consume();
            if (cConsume == '>') {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else {
                if (cConsume != 65535) {
                    return;
                }
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    CdataSection { // from class: org.jsoup.parser.TokeniserState.68
        @Override // org.jsoup.parser.TokeniserState
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.dataBuffer.append(characterReader.consumeTo("]]>"));
            if (characterReader.matchConsume("]]>") || characterReader.isEmpty()) {
                tokeniser.emit(new Token.CData(tokeniser.dataBuffer.value()));
                tokeniser.transition(Data);
            }
        }
    };

    private static final char eof = 65535;
    static final char nullChar = 0;
    private static final char replacementChar = 65533;
    static final char[] attributeNameCharsSorted = {'\t', '\n', '\f', CharUtils.CR, ' ', '\"', CoreConstants.SINGLE_QUOTE_CHAR, '/', Typography.less, '=', Typography.greater, '?'};
    static final char[] attributeValueUnquoted = {0, '\t', '\n', '\f', CharUtils.CR, ' ', '\"', Typography.amp, CoreConstants.SINGLE_QUOTE_CHAR, Typography.less, '=', Typography.greater, '`'};
    private static final String replacementStr = String.valueOf((char) 65533);

    abstract void read(Tokeniser tokeniser, CharacterReader characterReader);

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleDataEndTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState) {
        if (characterReader.matchesAsciiAlpha()) {
            String strConsumeTagName = characterReader.consumeTagName();
            tokeniser.tagPending.appendTagName(strConsumeTagName);
            tokeniser.dataBuffer.append(strConsumeTagName);
            return;
        }
        if (tokeniser.isAppropriateEndTagToken() && !characterReader.isEmpty()) {
            char cConsume = characterReader.consume();
            if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ') {
                tokeniser.transition(BeforeAttributeName);
                return;
            }
            if (cConsume == '/') {
                tokeniser.transition(SelfClosingStartTag);
                return;
            } else {
                if (cConsume == '>') {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                tokeniser.dataBuffer.append(cConsume);
            }
        }
        tokeniser.emit("</");
        tokeniser.emit(tokeniser.dataBuffer.value());
        tokeniser.transition(tokeniserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readRawData(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        char cCurrent = characterReader.current();
        if (cCurrent == 0) {
            tokeniser.error(tokeniserState);
            characterReader.advance();
            tokeniser.emit((char) 65533);
        } else if (cCurrent == '<') {
            tokeniser.advanceTransition(tokeniserState2);
        } else if (cCurrent == 65535) {
            tokeniser.emit(new Token.EOF());
        } else {
            tokeniser.emit(characterReader.consumeRawData());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readCharRef(Tokeniser tokeniser, TokeniserState tokeniserState) {
        int[] iArrConsumeCharacterReference = tokeniser.consumeCharacterReference(null, false);
        if (iArrConsumeCharacterReference == null) {
            tokeniser.emit(Typography.amp);
        } else {
            tokeniser.emit(iArrConsumeCharacterReference);
        }
        tokeniser.transition(tokeniserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readEndTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        if (characterReader.matchesAsciiAlpha()) {
            tokeniser.createTagPending(false);
            tokeniser.transition(tokeniserState);
        } else {
            tokeniser.emit("</");
            tokeniser.transition(tokeniserState2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleDataDoubleEscapeTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        if (characterReader.matchesAsciiAlpha()) {
            String strConsumeLetterSequence = characterReader.consumeLetterSequence();
            tokeniser.dataBuffer.append(strConsumeLetterSequence);
            tokeniser.emit(strConsumeLetterSequence);
            return;
        }
        char cConsume = characterReader.consume();
        if (cConsume == '\t' || cConsume == '\n' || cConsume == '\f' || cConsume == '\r' || cConsume == ' ' || cConsume == '/' || cConsume == '>') {
            if (tokeniser.dataBuffer.value().equals(StringLookupFactory.KEY_SCRIPT)) {
                tokeniser.transition(tokeniserState);
            } else {
                tokeniser.transition(tokeniserState2);
            }
            tokeniser.emit(cConsume);
            return;
        }
        characterReader.unconsume();
        tokeniser.transition(tokeniserState2);
    }
}
