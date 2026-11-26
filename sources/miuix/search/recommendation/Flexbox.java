package miuix.search.recommendation;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes6.dex */
final class Flexbox extends RecyclerView.ViewHolder {
    private TextView mTextView;

    Flexbox(View view) {
        super(view);
        TextView textView = (TextView) view;
        this.mTextView = textView;
        Folme.useAt(textView).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).handleTouchOf(this.mTextView, new AnimConfig[0]);
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        this.mTextView.setBackgroundTintList(colorStateList);
    }

    public void setTextColor(int i) {
        this.mTextView.setTextColor(i);
    }
}
