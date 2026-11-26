package org.apache.commons.text.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

/* JADX INFO: loaded from: classes6.dex */
public class StringSubstitutorReader extends FilterReader {
    private static final int EOS = -1;
    private final TextStringBuilder buffer;
    private boolean eos;
    private final StringMatcher prefixEscapeMatcher;
    private final char[] read1CharBuffer;
    private final StringSubstitutor stringSubstitutor;
    private int toDrain;

    public StringSubstitutorReader(Reader reader, StringSubstitutor stringSubstitutor) {
        super(reader);
        this.buffer = new TextStringBuilder();
        this.read1CharBuffer = new char[]{0};
        this.stringSubstitutor = new StringSubstitutor(stringSubstitutor);
        this.prefixEscapeMatcher = StringMatcherFactory.INSTANCE.charMatcher(stringSubstitutor.getEscapeChar()).andThen(stringSubstitutor.getVariablePrefixMatcher());
    }

    private int buffer(int i) throws IOException {
        int from = this.buffer.readFrom(((FilterReader) this).in, i);
        this.eos = from == -1;
        return from;
    }

    private int bufferOrDrainOnEos(int i, char[] cArr, int i2, int i3) throws IOException {
        return drainOnEos(buffer(i), cArr, i2, i3);
    }

    private int drain(char[] cArr, int i, int i2) {
        int iDrainChars = this.buffer.drainChars(0, Math.min(this.buffer.length(), i2), cArr, i);
        this.toDrain -= iDrainChars;
        if (!this.buffer.isEmpty() && this.toDrain != 0) {
            return iDrainChars;
        }
        this.toDrain = 0;
        return iDrainChars;
    }

    private int drainOnEos(int i, char[] cArr, int i2, int i3) {
        if (i != -1) {
            return i;
        }
        if (!this.buffer.isNotEmpty()) {
            return -1;
        }
        this.toDrain = this.buffer.size();
        return drain(cArr, i2, i3);
    }

    private boolean isBufferMatchAt(StringMatcher stringMatcher, int i) {
        return stringMatcher.isMatch(this.buffer, i) == stringMatcher.size();
    }

    private boolean isDraining() {
        return this.toDrain > 0;
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read() throws IOException {
        int i;
        do {
            i = read(this.read1CharBuffer, 0, 1);
            if (i == -1) {
                return -1;
            }
        } while (i < 1);
        return this.read1CharBuffer[0];
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        int size;
        int i5;
        int size2;
        if (this.eos && this.buffer.isEmpty()) {
            return -1;
        }
        if (i2 <= 0) {
            return 0;
        }
        if (isDraining()) {
            int iDrain = drain(cArr, i, Math.min(this.toDrain, i2));
            if (iDrain != i2) {
                i3 = i + iDrain;
                i4 = i2 - iDrain;
            }
            return i2;
        }
        i3 = i;
        i4 = i2;
        int size3 = this.prefixEscapeMatcher.size();
        buffer(readCount(size3, 0));
        if (this.buffer.length() < size3 && i4 < size3) {
            int iDrain2 = (i3 + drain(cArr, i3, i4)) - i;
            if (!this.eos || iDrain2 > 0) {
                return iDrain2;
            }
            return -1;
        }
        if (this.eos) {
            this.stringSubstitutor.replaceIn(this.buffer);
            this.toDrain = this.buffer.size();
            int iDrain3 = (i3 + drain(cArr, i3, i4)) - i;
            if (!this.eos || iDrain3 > 0) {
                return iDrain3;
            }
            return -1;
        }
        StringMatcher variablePrefixMatcher = this.stringSubstitutor.getVariablePrefixMatcher();
        while (true) {
            if (i4 <= 0) {
                size = 0;
                i5 = 0;
                break;
            }
            i5 = 1;
            if (isBufferMatchAt(variablePrefixMatcher, 0)) {
                size = variablePrefixMatcher.size();
                break;
            }
            if (isBufferMatchAt(this.prefixEscapeMatcher, 0)) {
                size = this.prefixEscapeMatcher.size();
                break;
            }
            int iDrain4 = drain(cArr, i3, 1);
            i3 += iDrain4;
            i4 -= iDrain4;
            if (this.buffer.size() < size3) {
                int iBufferOrDrainOnEos = bufferOrDrainOnEos(size3, cArr, i3, i4);
                if (this.eos || isDraining()) {
                    if (iBufferOrDrainOnEos != -1) {
                        i3 += iBufferOrDrainOnEos;
                    }
                    int i6 = i3 - i;
                    if (i6 > 0) {
                        return i6;
                    }
                    return -1;
                }
            }
        }
        if (i4 > 0) {
            StringMatcher variableSuffixMatcher = this.stringSubstitutor.getVariableSuffixMatcher();
            int iMax = Math.max(size3, variableSuffixMatcher.size());
            buffer(readCount(iMax, size));
            if (this.eos) {
                this.stringSubstitutor.replaceIn(this.buffer);
                this.toDrain = this.buffer.size();
                return (i3 + drain(cArr, i3, i4)) - i;
            }
            while (true) {
                if (isBufferMatchAt(variableSuffixMatcher, size)) {
                    i5--;
                    size++;
                    if (i5 != 0) {
                        break;
                    }
                    break;
                }
                if (isBufferMatchAt(variablePrefixMatcher, size)) {
                    i5++;
                    size2 = variablePrefixMatcher.size();
                } else if (isBufferMatchAt(this.prefixEscapeMatcher, size)) {
                    i5++;
                    size2 = this.prefixEscapeMatcher.size();
                } else {
                    size++;
                }
                size += size2;
                if (buffer(readCount(iMax, size)) == -1 && size >= this.buffer.size()) {
                    break;
                }
            }
            int iMax2 = Math.max(0, this.buffer.size() - size);
            StringSubstitutor stringSubstitutor = this.stringSubstitutor;
            TextStringBuilder textStringBuilder = this.buffer;
            stringSubstitutor.replaceIn(textStringBuilder, 0, Math.min(textStringBuilder.size(), size + 1));
            int size4 = this.buffer.size() - iMax2;
            int iMin = Math.min(i4, size4);
            this.toDrain = size4;
            drain(cArr, i3, iMin);
            return (i3 - i) + iMin;
        }
        return i2;
    }

    private int readCount(int i, int i2) {
        int size = this.buffer.size() - i2;
        if (size >= i) {
            return 0;
        }
        return i - size;
    }
}
