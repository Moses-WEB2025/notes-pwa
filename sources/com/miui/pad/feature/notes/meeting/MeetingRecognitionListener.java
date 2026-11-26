package com.miui.pad.feature.notes.meeting;

/* JADX INFO: loaded from: classes4.dex */
public interface MeetingRecognitionListener {
    void onMeetingMaxAmplitudeUpdate(short s);

    void onMeetingMp3FileName(String str);

    void onMeetingTextFull(String str);

    void onMeetingTextResult(String str, boolean z);

    void onSocketClosed();

    void onSocketFailure();
}
