package org.jsoup.parser;

/* JADX INFO: loaded from: classes6.dex */
public class ParseError {
    private final String cursorPos;
    private final String errorMsg;
    private final int pos;

    ParseError(CharacterReader characterReader, String str) {
        this.pos = characterReader.pos();
        this.cursorPos = characterReader.posLineCol();
        this.errorMsg = str;
    }

    ParseError(CharacterReader characterReader, String str, Object... objArr) {
        this.pos = characterReader.pos();
        this.cursorPos = characterReader.posLineCol();
        this.errorMsg = String.format(str, objArr);
    }

    ParseError(int i, String str) {
        this.pos = i;
        this.cursorPos = String.valueOf(i);
        this.errorMsg = str;
    }

    ParseError(int i, String str, Object... objArr) {
        this.pos = i;
        this.cursorPos = String.valueOf(i);
        this.errorMsg = String.format(str, objArr);
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public int getPosition() {
        return this.pos;
    }

    public String getCursorPos() {
        return this.cursorPos;
    }

    public String toString() {
        return "<" + this.cursorPos + ">: " + this.errorMsg;
    }
}
