package com.agog.mathdisplay.render;

import android.content.res.AssetManager;
import com.agog.mathdisplay.parse.MathDisplayException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFont.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0007J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0005J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001d2\u0006\u0010\u001b\u001a\u00020\u0005J\u000e\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u001aJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u0005R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\""}, d2 = {"Lcom/agog/mathdisplay/render/MTFont;", "", "assets", "Landroid/content/res/AssetManager;", "name", "", "fontSize", "", "isCopy", "", "(Landroid/content/res/AssetManager;Ljava/lang/String;FZ)V", "getFontSize", "()F", "mathTable", "Lcom/agog/mathdisplay/render/MTFontMathTable;", "getMathTable", "()Lcom/agog/mathdisplay/render/MTFontMathTable;", "setMathTable", "(Lcom/agog/mathdisplay/render/MTFontMathTable;)V", "getName", "()Ljava/lang/String;", "copyFontWithSize", "size", "findGlyphForCharacterAtIndex", "Lcom/agog/mathdisplay/render/CGGlyph;", "index", "", "str", "getGidListForString", "", "getGlyphName", "gid", "getGlyphWithName", "glyphName", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFont {
    private final AssetManager assets;
    private final float fontSize;
    private MTFontMathTable mathTable;
    private final String name;

    public MTFont(AssetManager assets, String name, float f, boolean z) throws MathDisplayException, IOException {
        Intrinsics.checkNotNullParameter(assets, "assets");
        Intrinsics.checkNotNullParameter(name, "name");
        this.assets = assets;
        this.name = name;
        this.fontSize = f;
        this.mathTable = new MTFontMathTable(this, null);
        String str = "fonts/" + name + ".otf";
        if (z) {
            return;
        }
        InputStream inputStreamOpen = assets.open(str);
        if (inputStreamOpen == null) {
            throw new MathDisplayException(Intrinsics.stringPlus("Missing font asset for ", name));
        }
        this.mathTable = new MTFontMathTable(this, inputStreamOpen);
        inputStreamOpen.close();
    }

    public /* synthetic */ MTFont(AssetManager assetManager, String str, float f, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(assetManager, str, f, (i & 8) != 0 ? false : z);
    }

    public final float getFontSize() {
        return this.fontSize;
    }

    public final String getName() {
        return this.name;
    }

    public final MTFontMathTable getMathTable() {
        return this.mathTable;
    }

    public final void setMathTable(MTFontMathTable mTFontMathTable) {
        Intrinsics.checkNotNullParameter(mTFontMathTable, "<set-?>");
        this.mathTable = mTFontMathTable;
    }

    public final CGGlyph findGlyphForCharacterAtIndex(int index, String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        char[] charArray = str.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        return new CGGlyph(this.mathTable.getGlyphForCodepoint(Character.codePointAt(charArray, index)), 0.0f, 0.0f, 0.0f, 14, null);
    }

    public final List<Integer> getGidListForString(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        char[] charArray = str.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int iCharCount = 0;
        ArrayList arrayList = new ArrayList(0);
        while (iCharCount < charArray.length) {
            int iCodePointAt = Character.codePointAt(charArray, iCharCount);
            iCharCount += Character.charCount(iCodePointAt);
            int glyphForCodepoint = this.mathTable.getGlyphForCodepoint(iCodePointAt);
            if (glyphForCodepoint == 0) {
                MTFontKt.PackageWarning("getGidListForString codepoint " + iCodePointAt + " mapped to missing glyph");
            }
            arrayList.add(Integer.valueOf(glyphForCodepoint));
        }
        return arrayList;
    }

    public final MTFont copyFontWithSize(float size) {
        MTFont mTFont = new MTFont(this.assets, this.name, size, true);
        mTFont.mathTable = this.mathTable.copyFontTableWithSize(size);
        return mTFont;
    }

    public final String getGlyphName(int gid) {
        return this.mathTable.getGlyphName(gid);
    }

    public final int getGlyphWithName(String glyphName) {
        Intrinsics.checkNotNullParameter(glyphName, "glyphName");
        return this.mathTable.getGlyphWithName(glyphName);
    }
}
