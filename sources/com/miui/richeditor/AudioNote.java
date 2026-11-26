package com.miui.richeditor;

/* JADX INFO: loaded from: classes4.dex */
public interface AudioNote extends AudioInputListener {
    public static final String AUDIO_DEFAULT_FILE_PATH = "temp_audio_file";
    public static final String AUDIO_DEFAULT_PLACE_HOLDER = "<sound fileid=\"temp_audio_file\" />";
    public static final String NOTE_AUDIO_PLACE_HOLDER_HEAD = "<sound fileid=\"";
    public static final String NOTE_AUDIO_PLACE_HOLDER_TAIL = "\" />";

    void insertAudioBubble();
}
