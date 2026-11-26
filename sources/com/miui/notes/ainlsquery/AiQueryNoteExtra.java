package com.miui.notes.ainlsquery;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiQueryNoteExtra.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\f¢\u0006\u0004\b\r\u0010\u000eJ\b\u0010#\u001a\u00020\u0003H\u0016J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0006HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0006HÆ\u0003J\t\u0010)\u001a\u00020\u0006HÆ\u0003J\u0019\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\fHÆ\u0003J_\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\u0018\b\u0002\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\fHÆ\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010/\u001a\u00020\u0006HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0016\"\u0004\b\u001c\u0010\u0018R\u001a\u0010\t\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u0018R*\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"¨\u00060"}, d2 = {"Lcom/miui/notes/ainlsquery/AiQueryNoteExtra;", "", "title", "", "date", "size", "", "chunkText", "chunkStart", "chunkEnd", "highlightKeywords", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IILjava/util/ArrayList;)V", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getDate", "setDate", "getSize", "()I", "setSize", "(I)V", "getChunkText", "setChunkText", "getChunkStart", "setChunkStart", "getChunkEnd", "setChunkEnd", "getHighlightKeywords", "()Ljava/util/ArrayList;", "setHighlightKeywords", "(Ljava/util/ArrayList;)V", "toString", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class AiQueryNoteExtra {
    private int chunkEnd;
    private int chunkStart;
    private String chunkText;
    private String date;
    private ArrayList<String> highlightKeywords;
    private int size;
    private String title;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AiQueryNoteExtra copy$default(AiQueryNoteExtra aiQueryNoteExtra, String str, String str2, int i, String str3, int i2, int i3, ArrayList arrayList, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            str = aiQueryNoteExtra.title;
        }
        if ((i4 & 2) != 0) {
            str2 = aiQueryNoteExtra.date;
        }
        if ((i4 & 4) != 0) {
            i = aiQueryNoteExtra.size;
        }
        if ((i4 & 8) != 0) {
            str3 = aiQueryNoteExtra.chunkText;
        }
        if ((i4 & 16) != 0) {
            i2 = aiQueryNoteExtra.chunkStart;
        }
        if ((i4 & 32) != 0) {
            i3 = aiQueryNoteExtra.chunkEnd;
        }
        if ((i4 & 64) != 0) {
            arrayList = aiQueryNoteExtra.highlightKeywords;
        }
        int i5 = i3;
        ArrayList arrayList2 = arrayList;
        int i6 = i2;
        int i7 = i;
        return aiQueryNoteExtra.copy(str, str2, i7, str3, i6, i5, arrayList2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDate() {
        return this.date;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getSize() {
        return this.size;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getChunkText() {
        return this.chunkText;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getChunkStart() {
        return this.chunkStart;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getChunkEnd() {
        return this.chunkEnd;
    }

    public final ArrayList<String> component7() {
        return this.highlightKeywords;
    }

    public final AiQueryNoteExtra copy(String title, String date, int size, String chunkText, int chunkStart, int chunkEnd, ArrayList<String> highlightKeywords) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(date, "date");
        Intrinsics.checkNotNullParameter(chunkText, "chunkText");
        Intrinsics.checkNotNullParameter(highlightKeywords, "highlightKeywords");
        return new AiQueryNoteExtra(title, date, size, chunkText, chunkStart, chunkEnd, highlightKeywords);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AiQueryNoteExtra)) {
            return false;
        }
        AiQueryNoteExtra aiQueryNoteExtra = (AiQueryNoteExtra) other;
        return Intrinsics.areEqual(this.title, aiQueryNoteExtra.title) && Intrinsics.areEqual(this.date, aiQueryNoteExtra.date) && this.size == aiQueryNoteExtra.size && Intrinsics.areEqual(this.chunkText, aiQueryNoteExtra.chunkText) && this.chunkStart == aiQueryNoteExtra.chunkStart && this.chunkEnd == aiQueryNoteExtra.chunkEnd && Intrinsics.areEqual(this.highlightKeywords, aiQueryNoteExtra.highlightKeywords);
    }

    public int hashCode() {
        return (((((((((((this.title.hashCode() * 31) + this.date.hashCode()) * 31) + Integer.hashCode(this.size)) * 31) + this.chunkText.hashCode()) * 31) + Integer.hashCode(this.chunkStart)) * 31) + Integer.hashCode(this.chunkEnd)) * 31) + this.highlightKeywords.hashCode();
    }

    public AiQueryNoteExtra(String title, String date, int i, String chunkText, int i2, int i3, ArrayList<String> highlightKeywords) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(date, "date");
        Intrinsics.checkNotNullParameter(chunkText, "chunkText");
        Intrinsics.checkNotNullParameter(highlightKeywords, "highlightKeywords");
        this.title = title;
        this.date = date;
        this.size = i;
        this.chunkText = chunkText;
        this.chunkStart = i2;
        this.chunkEnd = i3;
        this.highlightKeywords = highlightKeywords;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getDate() {
        return this.date;
    }

    public final void setDate(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.date = str;
    }

    public final int getSize() {
        return this.size;
    }

    public final void setSize(int i) {
        this.size = i;
    }

    public final String getChunkText() {
        return this.chunkText;
    }

    public final void setChunkText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chunkText = str;
    }

    public final int getChunkStart() {
        return this.chunkStart;
    }

    public final void setChunkStart(int i) {
        this.chunkStart = i;
    }

    public final int getChunkEnd() {
        return this.chunkEnd;
    }

    public final void setChunkEnd(int i) {
        this.chunkEnd = i;
    }

    public final ArrayList<String> getHighlightKeywords() {
        return this.highlightKeywords;
    }

    public final void setHighlightKeywords(ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.highlightKeywords = arrayList;
    }

    public String toString() {
        return "title " + this.title + " chunkText " + this.chunkText + " highlightKeywords " + this.highlightKeywords;
    }
}
