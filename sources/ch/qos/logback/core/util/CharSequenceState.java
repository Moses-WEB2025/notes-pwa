package ch.qos.logback.core.util;

/* JADX INFO: loaded from: classes.dex */
class CharSequenceState {
    final char c;
    int occurrences = 1;

    public CharSequenceState(char c) {
        this.c = c;
    }

    void incrementOccurrences() {
        this.occurrences++;
    }
}
