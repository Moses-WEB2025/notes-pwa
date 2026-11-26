package com.miui.richeditor.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import com.miui.notes.R;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.style.LinkCardSnippetSpan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class EditorUtils {
    public static final String MP3_SUFFIX = ".mp3";
    public static final char PARAGRAPH_END = '\n';
    public static final char ZERO_WIDTH_CHAR = 8204;
    public static final String ZERO_WIDTH_STRING = "\u200c";

    public static ClipData getPrimaryClip(ClipboardManager clipboardManager) {
        if (clipboardManager == null) {
            return null;
        }
        try {
            return (ClipData) clipboardManager.getClass().getMethod("getUserPrimaryClip", new Class[0]).invoke(clipboardManager, new Object[0]);
        } catch (Exception unused) {
            return clipboardManager.getPrimaryClip();
        }
    }

    public static CharSequence normalizeSnippet(Context context, String str) {
        return normalizeSnippet(context, str, false);
    }

    public static CharSequence normalizeSnippet(Context context, String str, boolean z) {
        SnippetMediaHandler snippetMediaHandler;
        if (z) {
            snippetMediaHandler = new SnippetMediaHandler(context) { // from class: com.miui.richeditor.util.EditorUtils.1
                @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
                protected CharSequence getImageText(String str2) {
                    return "";
                }
            };
        } else {
            snippetMediaHandler = new SnippetMediaHandler(context);
        }
        return normalizeSnippet(str, snippetMediaHandler, context);
    }

    public static CharSequence normalizeSnippet(String str, SnippetMediaHandler snippetMediaHandler, Context context) {
        SpannableStringBuilder spannableStringBuilder;
        Spannable ancient = NoteSchema.parseAncient(str, snippetMediaHandler, context);
        if (ancient instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) ancient;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(ancient);
        }
        StrikethroughSpan[] strikethroughSpanArr = (StrikethroughSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), StrikethroughSpan.class);
        if (strikethroughSpanArr.length == 0) {
            return ancient;
        }
        ArrayList<StrikethroughSpan> arrayList = new ArrayList(strikethroughSpanArr.length);
        Collections.addAll(arrayList, strikethroughSpanArr);
        sortStrikethroughSpan(spannableStringBuilder, arrayList);
        filterStrikethroughSpan(spannableStringBuilder, arrayList);
        for (StrikethroughSpan strikethroughSpan : arrayList) {
            int spanStart = spannableStringBuilder.getSpanStart(strikethroughSpan);
            spannableStringBuilder.delete(spanStart, spanStart + 1);
            int iIndexOf = spannableStringBuilder.toString().indexOf(10, spanStart);
            if (iIndexOf < 0) {
                iIndexOf = spannableStringBuilder.length();
            }
            spannableStringBuilder.removeSpan(strikethroughSpan);
            spannableStringBuilder.setSpan(strikethroughSpan, spanStart, iIndexOf, 17);
        }
        return spannableStringBuilder;
    }

    private static void sortStrikethroughSpan(final SpannableStringBuilder spannableStringBuilder, ArrayList<StrikethroughSpan> arrayList) {
        Collections.sort(arrayList, new Comparator<StrikethroughSpan>() { // from class: com.miui.richeditor.util.EditorUtils.2
            @Override // java.util.Comparator
            public int compare(StrikethroughSpan strikethroughSpan, StrikethroughSpan strikethroughSpan2) {
                return spannableStringBuilder.getSpanEnd(strikethroughSpan2) - spannableStringBuilder.getSpanStart(strikethroughSpan);
            }
        });
    }

    private static void filterStrikethroughSpan(SpannableStringBuilder spannableStringBuilder, ArrayList<StrikethroughSpan> arrayList) {
        Iterator<StrikethroughSpan> it = arrayList.iterator();
        while (it.hasNext()) {
            StrikethroughSpan next = it.next();
            int spanStart = spannableStringBuilder.getSpanStart(next);
            if (spanStart != spannableStringBuilder.getSpanEnd(next) - 1 || spannableStringBuilder.charAt(spanStart) != 65532) {
                it.remove();
            }
        }
    }

    public static List<String> retrieveImages(Context context, String str) {
        MyImageHandler myImageHandler = new MyImageHandler(context);
        normalizeSnippet(str, myImageHandler, context);
        return myImageHandler.getImageList();
    }

    public static boolean isMp3Suffix(String str) {
        return !TextUtils.isEmpty(str) && str.endsWith(".mp3");
    }

    public static class SnippetMediaHandler implements HtmlParser.IMediaHandler {
        protected Context nContext;

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleBullet(SpannableStringBuilder spannableStringBuilder, HtmlParser.BulletElement bulletElement, int i) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleEndQuote() {
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleOrder(SpannableStringBuilder spannableStringBuilder, HtmlParser.OrderElement orderElement, int i, int i2) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleQuote(SpannableStringBuilder spannableStringBuilder, HtmlParser.QuoteElement quoteElement) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleSplitLine(SpannableStringBuilder spannableStringBuilder, HtmlParser.SplitLineElement splitLineElement) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleTag(SpannableStringBuilder spannableStringBuilder, String str, boolean z) {
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public boolean handleText(SpannableStringBuilder spannableStringBuilder, String str) {
            return false;
        }

        protected HtmlParser.IElementSpan handleUnknown(SpannableStringBuilder spannableStringBuilder, HtmlParser.GeneralElement generalElement) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleUpdateIndent(int i) {
        }

        public SnippetMediaHandler(Context context) {
            this.nContext = context;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleInput(SpannableStringBuilder spannableStringBuilder, HtmlParser.InputElement inputElement, int i) {
            if (!(inputElement instanceof HtmlParser.CheckBoxElement) || !((HtmlParser.CheckBoxElement) inputElement).isChecked()) {
                return null;
            }
            spannableStringBuilder.append(HtmlParser.MEDIA_DUMMY_CHAR);
            spannableStringBuilder.setSpan(new StrikethroughSpan(), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 17);
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleImage(SpannableStringBuilder spannableStringBuilder, HtmlParser.ImageElement imageElement) {
            spannableStringBuilder.append(getImageText(imageElement.getFileId()));
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleLinkCard(SpannableStringBuilder spannableStringBuilder, HtmlParser.LinkCardElement linkCardElement, boolean z) {
            if (!z && linkCardElement != null) {
                spannableStringBuilder.append(getLinkCardText(linkCardElement));
            }
            if (z) {
                return new LinkCardSnippetSpan(linkCardElement);
            }
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleDoodle(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiDoodleElement miDoodleElement) {
            spannableStringBuilder.append(getImageText(miDoodleElement.getFileId()));
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleContact(SpannableStringBuilder spannableStringBuilder, HtmlParser.ContactElement contactElement) {
            spannableStringBuilder.append(getContactText(contactElement.getName()));
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleVideo(SpannableStringBuilder spannableStringBuilder, HtmlParser.VideoElement videoElement) {
            return handleUnknown(spannableStringBuilder, videoElement);
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, HtmlParser.SoundElement soundElement) {
            spannableStringBuilder.append(getAudioText(soundElement.getFileId()));
            return handleUnknown(spannableStringBuilder, soundElement);
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleTable(SpannableStringBuilder spannableStringBuilder, HtmlParser.TableElement tableElement) {
            return handleUnknown(spannableStringBuilder, tableElement);
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleMiui(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiuiElement miuiElement) {
            return handleUnknown(spannableStringBuilder, miuiElement);
        }

        protected CharSequence getLinkCardText(HtmlParser.LinkCardElement linkCardElement) {
            return StringUtils.LF + linkCardElement.getLink();
        }

        protected CharSequence getImageText(String str) {
            return this.nContext.getString(R.string.snippet_image_stub);
        }

        protected CharSequence getAudioText(String str) {
            Context context = this.nContext;
            return context != null ? context.getString(R.string.snippet_audio_stub) : "";
        }

        protected CharSequence getContactText(String str) {
            return String.format("[%s]", str);
        }
    }

    private static class MyImageHandler extends SnippetMediaHandler {
        private ArrayList<String> mImageList;

        public MyImageHandler(Context context) {
            super(context);
            this.mImageList = new ArrayList<>();
        }

        public ArrayList<String> getImageList() {
            return this.mImageList;
        }

        @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
        protected CharSequence getImageText(String str) {
            this.mImageList.add(str);
            return "";
        }
    }
}
