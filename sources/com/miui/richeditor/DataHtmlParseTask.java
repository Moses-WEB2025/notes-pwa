package com.miui.richeditor;

import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.NormalImageSpan;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class DataHtmlParseTask extends AsyncTask<CharSequence, Void, CharSequence[]> implements IEditorBgTask {
    private WeakReference<RichEditTextView> mViewRef;

    public DataHtmlParseTask(RichEditTextView richEditTextView) {
        this.mViewRef = new WeakReference<>(richEditTextView);
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            richEditTextView.postDelayed(new Runnable() { // from class: com.miui.richeditor.DataHtmlParseTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPreExecute$0();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPreExecute$0() {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            richEditTextView.onDataParseStart(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public CharSequence[] doInBackground(CharSequence... charSequenceArr) {
        SpannableStringBuilder spannableStringBuilder;
        Logger.INSTANCE.d("DataHemlParseTask", "doInBackground...");
        Thread.currentThread().setName("DataHtmlParser");
        CharSequence charSequence = charSequenceArr[0];
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView == null || charSequence == null || charSequence.length() == 0) {
            return new CharSequence[]{charSequence, SpannableString.valueOf("")};
        }
        Spannable ancientForView = NoteSchema.parseAncientForView(charSequence.toString(), richEditTextView.getMediaHandler(), richEditTextView);
        if (charSequence instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) ancientForView;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(ancientForView);
        }
        try {
            richEditTextView.getStyleFactory().converterDataToView(spannableStringBuilder);
            if (richEditTextView.hasDataLoadingLocker() && isWaitAfterAnim(spannableStringBuilder)) {
                richEditTextView.getDataLoadingLocker().beginWait();
                richEditTextView.setDataLoadingLocker(null);
            }
            return new CharSequence[]{charSequence, spannableStringBuilder};
        } catch (Exception e) {
            Logger.INSTANCE.i("RichEditTextView", " DataHtmlParseTask doInBackground Exception load = " + ((Object) charSequence));
            Logger.INSTANCE.i("RichEditTextView", " DataHtmlParseTask doInBackground Exception " + e);
            return null;
        }
    }

    protected boolean isWaitAfterAnim(Spannable spannable) {
        int length = ((AudioSpan[]) spannable.getSpans(0, spannable.length(), AudioSpan.class)).length;
        return ((length * 3) + (((NormalImageSpan[]) spannable.getSpans(0, spannable.length(), NormalImageSpan.class)).length - length)) + (spannable.length() / 70) > 15;
    }

    @Override // com.miui.richeditor.IEditorBgTask
    public void restoreAndCancel() {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            richEditTextView.onDataParseCancel(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(CharSequence[] charSequenceArr) {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            richEditTextView.onDataParseComplete(this, charSequenceArr);
        }
    }
}
