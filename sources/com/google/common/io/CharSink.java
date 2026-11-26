package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class CharSink {
    public abstract Writer openStream() throws IOException;

    protected CharSink() {
    }

    public Writer openBufferedStream() throws IOException {
        Writer writerOpenStream = openStream();
        if (writerOpenStream instanceof BufferedWriter) {
            return (BufferedWriter) writerOpenStream;
        }
        return new BufferedWriter(writerOpenStream);
    }

    public void write(CharSequence charSequence) throws Throwable {
        Preconditions.checkNotNull(charSequence);
        Closer closerCreate = Closer.create();
        try {
            Writer writer = (Writer) closerCreate.register(openStream());
            writer.append(charSequence);
            writer.flush();
            closerCreate.close();
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public void writeLines(Iterable<? extends CharSequence> lines) throws Throwable {
        writeLines(lines, System.getProperty(SystemProperties.LINE_SEPARATOR));
    }

    public void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator) throws Throwable {
        Preconditions.checkNotNull(lines);
        Preconditions.checkNotNull(lineSeparator);
        Closer closerCreate = Closer.create();
        try {
            Writer writer = (Writer) closerCreate.register(openBufferedStream());
            Iterator<? extends CharSequence> it = lines.iterator();
            while (it.hasNext()) {
                writer.append(it.next()).append((CharSequence) lineSeparator);
            }
            writer.flush();
            closerCreate.close();
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public long writeFrom(Readable readable) throws Throwable {
        Preconditions.checkNotNull(readable);
        Closer closerCreate = Closer.create();
        try {
            Writer writer = (Writer) closerCreate.register(openStream());
            long jCopy = CharStreams.copy(readable, writer);
            writer.flush();
            closerCreate.close();
            return jCopy;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }
}
