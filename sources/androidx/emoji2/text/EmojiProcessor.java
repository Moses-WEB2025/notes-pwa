package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import java.util.Arrays;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class EmojiProcessor {
    private static final int ACTION_ADVANCE_BOTH = 1;
    private static final int ACTION_ADVANCE_END = 2;
    private static final int ACTION_FLUSH = 3;
    private static final int MAX_LOOK_AROUND_CHARACTER = 16;
    private final int[] mEmojiAsDefaultStyleExceptions;
    private EmojiCompat.GlyphChecker mGlyphChecker;
    private final MetadataRepo mMetadataRepo;
    private final EmojiCompat.SpanFactory mSpanFactory;
    private final boolean mUseEmojiAsDefaultStyle;

    private interface EmojiProcessCallback<T> {
        T getResult();

        boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer);
    }

    private static boolean hasInvalidSelection(int i, int i2) {
        return i == -1 || i2 == -1 || i != i2;
    }

    EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, EmojiCompat.GlyphChecker glyphChecker, boolean z, int[] iArr, Set<int[]> set) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = z;
        this.mEmojiAsDefaultStyleExceptions = iArr;
        initExclusions(set);
    }

    private void initExclusions(Set<int[]> set) {
        if (set.isEmpty()) {
            return;
        }
        for (int[] iArr : set) {
            String str = new String(iArr, 0, iArr.length);
            process(str, 0, str.length(), 1, true, new MarkExclusionCallback(str));
        }
    }

    int getEmojiMatch(CharSequence charSequence) {
        return getEmojiMatch(charSequence, this.mMetadataRepo.getMetadataVersion());
    }

    int getEmojiMatch(CharSequence charSequence, int i) {
        ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        int length = charSequence.length();
        int iCharCount = 0;
        int i2 = 0;
        int i3 = 0;
        while (iCharCount < length) {
            int iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            int iCheck = processorSm.check(iCodePointAt);
            TypefaceEmojiRasterizer currentMetadata = processorSm.getCurrentMetadata();
            if (iCheck == 1) {
                iCharCount += Character.charCount(iCodePointAt);
                i3 = 0;
            } else if (iCheck == 2) {
                iCharCount += Character.charCount(iCodePointAt);
            } else if (iCheck == 3) {
                currentMetadata = processorSm.getFlushMetadata();
                if (currentMetadata.getCompatAdded() <= i) {
                    i2++;
                }
            }
            if (currentMetadata != null && currentMetadata.getCompatAdded() <= i) {
                i3++;
            }
        }
        if (i2 != 0) {
            return 2;
        }
        if (!processorSm.isInFlushableState() || processorSm.getCurrentMetadata().getCompatAdded() > i) {
            return i3 == 0 ? 0 : 2;
        }
        return 1;
    }

    int getEmojiStart(CharSequence charSequence, int i) {
        if (i < 0 || i >= charSequence.length()) {
            return -1;
        }
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) spanned.getSpans(i, i + 1, EmojiSpan.class);
            if (emojiSpanArr.length > 0) {
                return spanned.getSpanStart(emojiSpanArr[0]);
            }
        }
        return ((EmojiProcessLookupCallback) process(charSequence, Math.max(0, i - 16), Math.min(charSequence.length(), i + 16), Integer.MAX_VALUE, true, new EmojiProcessLookupCallback(i))).start;
    }

    int getEmojiEnd(CharSequence charSequence, int i) {
        if (i < 0 || i >= charSequence.length()) {
            return -1;
        }
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) spanned.getSpans(i, i + 1, EmojiSpan.class);
            if (emojiSpanArr.length > 0) {
                return spanned.getSpanEnd(emojiSpanArr[0]);
            }
        }
        return ((EmojiProcessLookupCallback) process(charSequence, Math.max(0, i - 16), Math.min(charSequence.length(), i + 16), Integer.MAX_VALUE, true, new EmojiProcessLookupCallback(i))).end;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x004d A[Catch: all -> 0x002c, TryCatch #2 {all -> 0x002c, blocks: (B:6:0x000c, B:9:0x0011, B:11:0x0015, B:13:0x0024, B:21:0x003c, B:23:0x0046, B:25:0x0049, B:27:0x004d, B:29:0x0059, B:30:0x005c, B:40:0x0079), top: B:70:0x000c }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0059 A[Catch: all -> 0x002c, TryCatch #2 {all -> 0x002c, blocks: (B:6:0x000c, B:9:0x0011, B:11:0x0015, B:13:0x0024, B:21:0x003c, B:23:0x0046, B:25:0x0049, B:27:0x004d, B:29:0x0059, B:30:0x005c, B:40:0x0079), top: B:70:0x000c }] */
    /* JADX WARN: Code duplicated, block: B:64:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:73:? A[SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a7, code lost:
    
        if (r1 != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a9, code lost:
    
        ((androidx.emoji2.text.SpannableBuilder) r3).endBatchEdit();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00af, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b3, code lost:
    
        if (r1 != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b6, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.CharSequence process(java.lang.CharSequence r10, int r11, int r12, int r13, boolean r14) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r1 = r10 instanceof androidx.emoji2.text.SpannableBuilder
            if (r1 == 0) goto La
            r0 = r10
            androidx.emoji2.text.SpannableBuilder r0 = (androidx.emoji2.text.SpannableBuilder) r0
            r0.beginBatchEdit()
        La:
            if (r1 != 0) goto L31
            boolean r0 = r10 instanceof android.text.Spannable     // Catch: java.lang.Throwable -> L2c
            if (r0 == 0) goto L11
            goto L31
        L11:
            boolean r0 = r10 instanceof android.text.Spanned     // Catch: java.lang.Throwable -> L2c
            if (r0 == 0) goto L2a
            r0 = r10
            android.text.Spanned r0 = (android.text.Spanned) r0     // Catch: java.lang.Throwable -> L2c
            int r2 = r11 + (-1)
            int r3 = r12 + 1
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r4 = androidx.emoji2.text.EmojiSpan.class
            int r0 = r0.nextSpanTransition(r2, r3, r4)     // Catch: java.lang.Throwable -> L2c
            if (r0 > r12) goto L2a
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r0 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch: java.lang.Throwable -> L2c
            r0.<init>(r10)     // Catch: java.lang.Throwable -> L2c
            goto L39
        L2a:
            r0 = 0
            goto L39
        L2c:
            r0 = move-exception
            r9 = r0
            r3 = r10
            goto Lba
        L31:
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r0 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch: java.lang.Throwable -> Lb7
            r2 = r10
            android.text.Spannable r2 = (android.text.Spannable) r2     // Catch: java.lang.Throwable -> Lb7
            r0.<init>(r2)     // Catch: java.lang.Throwable -> Lb7
        L39:
            r2 = 0
            if (r0 == 0) goto L67
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r3 = androidx.emoji2.text.EmojiSpan.class
            java.lang.Object[] r3 = r0.getSpans(r11, r12, r3)     // Catch: java.lang.Throwable -> L2c
            androidx.emoji2.text.EmojiSpan[] r3 = (androidx.emoji2.text.EmojiSpan[]) r3     // Catch: java.lang.Throwable -> L2c
            if (r3 == 0) goto L67
            int r4 = r3.length     // Catch: java.lang.Throwable -> L2c
            if (r4 <= 0) goto L67
            int r4 = r3.length     // Catch: java.lang.Throwable -> L2c
            r5 = r2
        L4b:
            if (r5 >= r4) goto L67
            r6 = r3[r5]     // Catch: java.lang.Throwable -> L2c
            int r7 = r0.getSpanStart(r6)     // Catch: java.lang.Throwable -> L2c
            int r8 = r0.getSpanEnd(r6)     // Catch: java.lang.Throwable -> L2c
            if (r7 == r12) goto L5c
            r0.removeSpan(r6)     // Catch: java.lang.Throwable -> L2c
        L5c:
            int r11 = java.lang.Math.min(r7, r11)     // Catch: java.lang.Throwable -> L2c
            int r12 = java.lang.Math.max(r8, r12)     // Catch: java.lang.Throwable -> L2c
            int r5 = r5 + 1
            goto L4b
        L67:
            r4 = r11
            r5 = r12
            if (r4 == r5) goto Lb2
            int r11 = r10.length()     // Catch: java.lang.Throwable -> Lb7
            if (r4 < r11) goto L72
            goto Lb2
        L72:
            r11 = 2147483647(0x7fffffff, float:NaN)
            if (r13 == r11) goto L87
            if (r0 == 0) goto L87
            int r11 = r0.length()     // Catch: java.lang.Throwable -> L2c
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r12 = androidx.emoji2.text.EmojiSpan.class
            java.lang.Object[] r11 = r0.getSpans(r2, r11, r12)     // Catch: java.lang.Throwable -> L2c
            androidx.emoji2.text.EmojiSpan[] r11 = (androidx.emoji2.text.EmojiSpan[]) r11     // Catch: java.lang.Throwable -> L2c
            int r11 = r11.length     // Catch: java.lang.Throwable -> L2c
            int r13 = r13 - r11
        L87:
            r6 = r13
            androidx.emoji2.text.EmojiProcessor$EmojiProcessAddSpanCallback r8 = new androidx.emoji2.text.EmojiProcessor$EmojiProcessAddSpanCallback     // Catch: java.lang.Throwable -> Lb7
            androidx.emoji2.text.EmojiCompat$SpanFactory r11 = r9.mSpanFactory     // Catch: java.lang.Throwable -> Lb7
            r8.<init>(r0, r11)     // Catch: java.lang.Throwable -> Lb7
            r2 = r9
            r3 = r10
            r7 = r14
            java.lang.Object r9 = r2.process(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb0
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r9 = (androidx.emoji2.text.UnprecomputeTextOnModificationSpannable) r9     // Catch: java.lang.Throwable -> Lb0
            if (r9 == 0) goto La7
            android.text.Spannable r9 = r9.getUnwrappedSpannable()     // Catch: java.lang.Throwable -> Lb0
            if (r1 == 0) goto La6
            r10 = r3
            androidx.emoji2.text.SpannableBuilder r10 = (androidx.emoji2.text.SpannableBuilder) r10
            r10.endBatchEdit()
        La6:
            return r9
        La7:
            if (r1 == 0) goto Lb6
        La9:
            r10 = r3
            androidx.emoji2.text.SpannableBuilder r10 = (androidx.emoji2.text.SpannableBuilder) r10
            r10.endBatchEdit()
            return r3
        Lb0:
            r0 = move-exception
            goto Lb9
        Lb2:
            r3 = r10
            if (r1 == 0) goto Lb6
            goto La9
        Lb6:
            return r3
        Lb7:
            r0 = move-exception
            r3 = r10
        Lb9:
            r9 = r0
        Lba:
            if (r1 == 0) goto Lc2
            r10 = r3
            androidx.emoji2.text.SpannableBuilder r10 = (androidx.emoji2.text.SpannableBuilder) r10
            r10.endBatchEdit()
        Lc2:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiProcessor.process(java.lang.CharSequence, int, int, int, boolean):java.lang.CharSequence");
    }

    private <T> T process(CharSequence charSequence, int i, int i2, int i3, boolean z, EmojiProcessCallback<T> emojiProcessCallback) {
        int iCharCount;
        ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        int i4 = 0;
        boolean zHandleEmoji = true;
        int iCodePointAt = Character.codePointAt(charSequence, i);
        loop0: while (true) {
            iCharCount = i;
            while (true) {
                if (i >= i2 || i4 >= i3 || !zHandleEmoji) {
                    break loop0;
                }
                int iCheck = processorSm.check(iCodePointAt);
                if (iCheck == 1) {
                    iCharCount += Character.charCount(Character.codePointAt(charSequence, iCharCount));
                    if (iCharCount < i2) {
                        iCodePointAt = Character.codePointAt(charSequence, iCharCount);
                    }
                    i = iCharCount;
                } else if (iCheck == 2) {
                    i += Character.charCount(iCodePointAt);
                    if (i < i2) {
                        iCodePointAt = Character.codePointAt(charSequence, i);
                    }
                } else if (iCheck != 3) {
                }
            }
            if (z || !hasGlyph(charSequence, iCharCount, i, processorSm.getFlushMetadata())) {
                i4++;
                zHandleEmoji = emojiProcessCallback.handleEmoji(charSequence, iCharCount, i, processorSm.getFlushMetadata());
            }
        }
        if (processorSm.isInFlushableState() && i4 < i3 && zHandleEmoji && (z || !hasGlyph(charSequence, iCharCount, i, processorSm.getCurrentMetadata()))) {
            emojiProcessCallback.handleEmoji(charSequence, iCharCount, i, processorSm.getCurrentMetadata());
        }
        return emojiProcessCallback.getResult();
    }

    static boolean handleOnKeyDown(Editable editable, int i, KeyEvent keyEvent) {
        boolean zDelete;
        if (i == 67) {
            zDelete = delete(editable, keyEvent, false);
        } else {
            zDelete = i != 112 ? false : delete(editable, keyEvent, true);
        }
        if (!zDelete) {
            return false;
        }
        MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
        return true;
    }

    private static boolean delete(Editable editable, KeyEvent keyEvent, boolean z) {
        EmojiSpan[] emojiSpanArr;
        if (hasModifiers(keyEvent)) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (!hasInvalidSelection(selectionStart, selectionEnd) && (emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                    editable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int i, int i2, boolean z) {
        int iMax;
        int iMin;
        if (editable != null && inputConnection != null && i >= 0 && i2 >= 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (hasInvalidSelection(selectionStart, selectionEnd)) {
                return false;
            }
            if (z) {
                iMax = CodepointIndexFinder.findIndexBackward(editable, selectionStart, Math.max(i, 0));
                iMin = CodepointIndexFinder.findIndexForward(editable, selectionEnd, Math.max(i2, 0));
                if (iMax == -1 || iMin == -1) {
                    return false;
                }
            } else {
                iMax = Math.max(selectionStart - i, 0);
                iMin = Math.min(selectionEnd + i2, editable.length());
            }
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(iMax, iMin, EmojiSpan.class);
            if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                for (EmojiSpan emojiSpan : emojiSpanArr) {
                    int spanStart = editable.getSpanStart(emojiSpan);
                    int spanEnd = editable.getSpanEnd(emojiSpan);
                    iMax = Math.min(spanStart, iMax);
                    iMin = Math.max(spanEnd, iMin);
                }
                int iMax2 = Math.max(iMax, 0);
                int iMin2 = Math.min(iMin, editable.length());
                inputConnection.beginBatchEdit();
                editable.delete(iMax2, iMin2);
                inputConnection.endBatchEdit();
                return true;
            }
        }
        return false;
    }

    private static boolean hasModifiers(KeyEvent keyEvent) {
        return !KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState());
    }

    private boolean hasGlyph(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
        if (typefaceEmojiRasterizer.getHasGlyph() == 0) {
            typefaceEmojiRasterizer.setHasGlyph(this.mGlyphChecker.hasGlyph(charSequence, i, i2, typefaceEmojiRasterizer.getSdkAdded()));
        }
        return typefaceEmojiRasterizer.getHasGlyph() == 2;
    }

    static final class ProcessorSm {
        private static final int STATE_DEFAULT = 1;
        private static final int STATE_WALKING = 2;
        private int mCurrentDepth;
        private MetadataRepo.Node mCurrentNode;
        private final int[] mEmojiAsDefaultStyleExceptions;
        private MetadataRepo.Node mFlushNode;
        private int mLastCodepoint;
        private final MetadataRepo.Node mRootNode;
        private int mState = 1;
        private final boolean mUseEmojiAsDefaultStyle;

        private static boolean isEmojiStyle(int i) {
            return i == 65039;
        }

        private static boolean isTextStyle(int i) {
            return i == 65038;
        }

        ProcessorSm(MetadataRepo.Node node, boolean z, int[] iArr) {
            this.mRootNode = node;
            this.mCurrentNode = node;
            this.mUseEmojiAsDefaultStyle = z;
            this.mEmojiAsDefaultStyleExceptions = iArr;
        }

        int check(int i) {
            MetadataRepo.Node node = this.mCurrentNode.get(i);
            int iReset = 2;
            if (this.mState != 2) {
                if (node == null) {
                    iReset = reset();
                } else {
                    this.mState = 2;
                    this.mCurrentNode = node;
                    this.mCurrentDepth = 1;
                }
            } else if (node != null) {
                this.mCurrentNode = node;
                this.mCurrentDepth++;
            } else if (isTextStyle(i)) {
                iReset = reset();
            } else if (!isEmojiStyle(i)) {
                if (this.mCurrentNode.getData() != null) {
                    iReset = 3;
                    if (this.mCurrentDepth != 1 || shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                        this.mFlushNode = this.mCurrentNode;
                        reset();
                    } else {
                        iReset = reset();
                    }
                } else {
                    iReset = reset();
                }
            }
            this.mLastCodepoint = i;
            return iReset;
        }

        private int reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
            return 1;
        }

        TypefaceEmojiRasterizer getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        TypefaceEmojiRasterizer getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        boolean isInFlushableState() {
            if (this.mState != 2 || this.mCurrentNode.getData() == null) {
                return false;
            }
            return this.mCurrentDepth > 1 || shouldUseEmojiPresentationStyleForSingleCodepoint();
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            if (this.mCurrentNode.getData().isDefaultEmoji() || isEmojiStyle(this.mLastCodepoint)) {
                return true;
            }
            if (this.mUseEmojiAsDefaultStyle) {
                if (this.mEmojiAsDefaultStyleExceptions == null) {
                    return true;
                }
                if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, this.mCurrentNode.getData().getCodepointAt(0)) < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class CodepointIndexFinder {
        private static final int INVALID_INDEX = -1;

        private CodepointIndexFinder() {
        }

        static int findIndexBackward(CharSequence charSequence, int i, int i2) {
            int length = charSequence.length();
            if (i < 0 || length < i || i2 < 0) {
                return -1;
            }
            while (true) {
                boolean z = false;
                while (i2 != 0) {
                    i--;
                    if (i < 0) {
                        return z ? -1 : 0;
                    }
                    char cCharAt = charSequence.charAt(i);
                    if (z) {
                        if (!Character.isHighSurrogate(cCharAt)) {
                            return -1;
                        }
                        i2--;
                    } else if (!Character.isSurrogate(cCharAt)) {
                        i2--;
                    } else {
                        if (Character.isHighSurrogate(cCharAt)) {
                            return -1;
                        }
                        z = true;
                    }
                }
                return i;
            }
        }

        static int findIndexForward(CharSequence charSequence, int i, int i2) {
            int length = charSequence.length();
            if (i < 0 || length < i || i2 < 0) {
                return -1;
            }
            while (true) {
                boolean z = false;
                while (i2 != 0) {
                    if (i >= length) {
                        if (z) {
                            return -1;
                        }
                        return length;
                    }
                    char cCharAt = charSequence.charAt(i);
                    if (z) {
                        if (!Character.isLowSurrogate(cCharAt)) {
                            return -1;
                        }
                        i2--;
                        i++;
                    } else if (!Character.isSurrogate(cCharAt)) {
                        i2--;
                        i++;
                    } else {
                        if (Character.isLowSurrogate(cCharAt)) {
                            return -1;
                        }
                        i++;
                        z = true;
                    }
                }
                return i;
            }
        }
    }

    private static class EmojiProcessAddSpanCallback implements EmojiProcessCallback<UnprecomputeTextOnModificationSpannable> {
        private final EmojiCompat.SpanFactory mSpanFactory;
        public UnprecomputeTextOnModificationSpannable spannable;

        EmojiProcessAddSpanCallback(UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable, EmojiCompat.SpanFactory spanFactory) {
            this.spannable = unprecomputeTextOnModificationSpannable;
            this.mSpanFactory = spanFactory;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
            Spannable spannableString;
            if (typefaceEmojiRasterizer.isPreferredSystemRender()) {
                return true;
            }
            if (this.spannable == null) {
                if (charSequence instanceof Spannable) {
                    spannableString = (Spannable) charSequence;
                } else {
                    spannableString = new SpannableString(charSequence);
                }
                this.spannable = new UnprecomputeTextOnModificationSpannable(spannableString);
            }
            this.spannable.setSpan(this.mSpanFactory.createSpan(typefaceEmojiRasterizer), i, i2, 33);
            return true;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public UnprecomputeTextOnModificationSpannable getResult() {
            return this.spannable;
        }
    }

    private static class EmojiProcessLookupCallback implements EmojiProcessCallback<EmojiProcessLookupCallback> {
        private final int mOffset;
        public int start = -1;
        public int end = -1;

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public EmojiProcessLookupCallback getResult() {
            return this;
        }

        EmojiProcessLookupCallback(int i) {
            this.mOffset = i;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
            int i3 = this.mOffset;
            if (i > i3 || i3 >= i2) {
                return i2 <= i3;
            }
            this.start = i;
            this.end = i2;
            return false;
        }
    }

    private static class MarkExclusionCallback implements EmojiProcessCallback<MarkExclusionCallback> {
        private final String mExclusion;

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public MarkExclusionCallback getResult() {
            return this;
        }

        MarkExclusionCallback(String str) {
            this.mExclusion = str;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
            if (!TextUtils.equals(charSequence.subSequence(i, i2), this.mExclusion)) {
                return true;
            }
            typefaceEmojiRasterizer.setExclusion(true);
            return false;
        }
    }
}
