package com.miui.richeditor;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import com.miui.richeditor.style.BaseImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public class RichSavedState implements Parcelable {
    public static final Parcelable.Creator<RichSavedState> CREATOR = new Parcelable.Creator<RichSavedState>() { // from class: com.miui.richeditor.RichSavedState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RichSavedState createFromParcel(Parcel parcel) {
            return new RichSavedState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RichSavedState[] newArray(int i) {
            return new RichSavedState[i];
        }
    };
    public boolean isCursorVisible;
    public long noteId;
    public int scrollY;
    public int selectionEnd;
    public int selectionStart;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RichSavedState(RichEditTextView richEditTextView) {
        this.noteId = richEditTextView.getDataId();
        this.isCursorVisible = richEditTextView.isCursorVisible();
        this.scrollY = richEditTextView.getScrollY();
        if (this.isCursorVisible) {
            this.selectionStart = richEditTextView.getSelectionStart();
            this.selectionEnd = richEditTextView.getSelectionEnd();
            return;
        }
        Rect rect = new Rect();
        richEditTextView.getLocalVisibleRect(rect);
        Layout layout = richEditTextView.getLayout();
        if (layout == null) {
            this.selectionEnd = 0;
            this.selectionStart = 0;
            return;
        }
        int lineForVertical = layout.getLineForVertical(rect.top - richEditTextView.getPaddingTop());
        int lineForVertical2 = layout.getLineForVertical(rect.bottom - richEditTextView.getPaddingTop());
        int i = lineForVertical + 1;
        while (i < lineForVertical2) {
            int lineStart = layout.getLineStart(i);
            BaseImageSpan[] baseImageSpanArr = (BaseImageSpan[]) richEditTextView.getSpansAt(richEditTextView.getText(), BaseImageSpan.class, lineStart);
            if (baseImageSpanArr != null && baseImageSpanArr.length == 0) {
                this.selectionEnd = lineStart;
                this.selectionStart = lineStart;
                break;
            }
            i++;
        }
        if (i == lineForVertical2) {
            int lineStart2 = layout.getLineStart(lineForVertical);
            this.selectionEnd = lineStart2;
            this.selectionStart = lineStart2;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.noteId);
        parcel.writeInt(this.scrollY);
        parcel.writeInt(this.selectionStart);
        parcel.writeInt(this.selectionEnd);
        parcel.writeInt(this.isCursorVisible ? 1 : 0);
    }

    protected RichSavedState(Parcel parcel) {
        this.noteId = parcel.readLong();
        this.scrollY = parcel.readInt();
        this.selectionStart = parcel.readInt();
        this.selectionEnd = parcel.readInt();
        this.isCursorVisible = parcel.readInt() == 1;
    }
}
