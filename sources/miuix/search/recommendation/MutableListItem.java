package miuix.search.recommendation;

import android.R;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes6.dex */
class MutableListItem extends RecyclerView.ViewHolder {
    View content;
    View delete;
    TextView text;

    MutableListItem(View view, int i) {
        super(view);
        if (i == 0) {
            this.text = (TextView) view.findViewById(R.id.title);
            this.content = view;
            this.delete = view.findViewById(miuix.search.common.R.id.delete);
            Folme.useAt(this.content).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).clearTintColor().setBackgroundColor(0.08f, 0.0f, 0.0f, 0.0f).handleTouchOf(this.content, new AnimConfig[0]);
            Folme.useAt(this.delete).touch().handleTouchOf(this.delete, new AnimConfig[0]);
            return;
        }
        if (i == 1) {
            this.text = (TextView) view.findViewById(R.id.title);
            this.content = view;
            Folme.useAt(view).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).clearTintColor().setBackgroundColor(0.08f, 0.0f, 0.0f, 0.0f).handleTouchOf(this.content, new AnimConfig[0]);
        }
    }

    void setText(CharSequence charSequence) {
        this.text.setText(charSequence);
    }
}
