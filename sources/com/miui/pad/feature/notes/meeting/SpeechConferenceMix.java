package com.miui.pad.feature.notes.meeting;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.miai.api.SpeechMiAiSpeechConference;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpeechConferenceMix.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR*\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u001ej\b\u0012\u0004\u0012\u00020\u0007`\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/SpeechConferenceMix;", "", "id", "", "sentenceResult", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$ConferenceServiceResult;", "time", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILcom/xiaomi/miai/api/SpeechMiAiSpeechConference$ConferenceServiceResult;Ljava/lang/String;)V", "getId", "()I", "getSentenceResult", "()Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$ConferenceServiceResult;", "getTime", "()Ljava/lang/String;", "isSingle", "", "()Z", "setSingle", "(Z)V", "isTranslate", "setTranslate", "vorResult", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$VorResult;", "getVorResult", "()Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$VorResult;", "setVorResult", "(Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$VorResult;)V", "enterFlagList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getEnterFlagList", "()Ljava/util/ArrayList;", "setEnterFlagList", "(Ljava/util/ArrayList;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SpeechConferenceMix {
    private ArrayList<String> enterFlagList;
    private final int id;
    private boolean isSingle;
    private boolean isTranslate;
    private final SpeechMiAiSpeechConference.ConferenceServiceResult sentenceResult;
    private final String time;
    private SpeechMiAiSpeechConference.VorResult vorResult;

    public SpeechConferenceMix(int i, SpeechMiAiSpeechConference.ConferenceServiceResult sentenceResult, String time) {
        Intrinsics.checkNotNullParameter(sentenceResult, "sentenceResult");
        Intrinsics.checkNotNullParameter(time, "time");
        this.id = i;
        this.sentenceResult = sentenceResult;
        this.time = time;
        this.enterFlagList = new ArrayList<>();
    }

    public final int getId() {
        return this.id;
    }

    public final SpeechMiAiSpeechConference.ConferenceServiceResult getSentenceResult() {
        return this.sentenceResult;
    }

    public final String getTime() {
        return this.time;
    }

    /* JADX INFO: renamed from: isSingle, reason: from getter */
    public final boolean getIsSingle() {
        return this.isSingle;
    }

    public final void setSingle(boolean z) {
        this.isSingle = z;
    }

    /* JADX INFO: renamed from: isTranslate, reason: from getter */
    public final boolean getIsTranslate() {
        return this.isTranslate;
    }

    public final void setTranslate(boolean z) {
        this.isTranslate = z;
    }

    public final SpeechMiAiSpeechConference.VorResult getVorResult() {
        return this.vorResult;
    }

    public final void setVorResult(SpeechMiAiSpeechConference.VorResult vorResult) {
        this.vorResult = vorResult;
    }

    public final ArrayList<String> getEnterFlagList() {
        return this.enterFlagList;
    }

    public final void setEnterFlagList(ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.enterFlagList = arrayList;
    }
}
