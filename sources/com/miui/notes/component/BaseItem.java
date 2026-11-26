package com.miui.notes.component;

import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.ui.widget.CheckBoxEditableViewHolder;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseItem extends CheckBoxEditableViewHolder {
    protected boolean isTrash;
    protected boolean mIsCallNote;

    public abstract void bind(ItemCache itemCache, BindContext bindContext, int i);

    public void bind(ItemCache itemCache, BindContext bindContext, int i, boolean z) {
    }

    public abstract int getContentType();

    public abstract ItemCache getItemCache();

    public abstract void onViewAttachedToWindow();

    public abstract void onViewDetachedFromWindow();

    public BaseItem(View view) {
        super(view);
        this.isTrash = false;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    public void setTrash(boolean z) {
        this.isTrash = z;
    }

    protected String extractCleanContent(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            Character chValueOf = Character.valueOf(str.charAt(i));
            if (z || (chValueOf.charValue() != '\n' && chValueOf.charValue() != ' ' && chValueOf.charValue() != 12288)) {
                if (!z && chValueOf.charValue() != '\n' && chValueOf.charValue() != ' ' && chValueOf.charValue() != 12288) {
                    stringBuffer.append(chValueOf);
                    z = true;
                } else if (z) {
                    stringBuffer.append(chValueOf);
                }
            }
        }
        return stringBuffer.toString();
    }

    protected Pair<Integer, String> extractAiQueryCleanContent(String str) {
        if (str.startsWith(StringUtils.LF) && str.endsWith(StringUtils.LF)) {
            int iCalculateContent = calculateContent(str);
            return new Pair<>(Integer.valueOf(iCalculateContent + 1), str.substring(iCalculateContent, Math.max(iCalculateContent, str.length() - 1)));
        }
        if (str.startsWith(StringUtils.LF)) {
            int iCalculateContent2 = calculateContent(str);
            return new Pair<>(Integer.valueOf(iCalculateContent2 + 1), str.substring(iCalculateContent2));
        }
        if (str.endsWith(StringUtils.LF)) {
            return new Pair<>(1, str.substring(0, Math.max(0, str.length() - 1)));
        }
        return new Pair<>(1, str);
    }

    protected int calculateContent(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length() && str.charAt(i2) == '\n'; i2++) {
            i++;
        }
        return i;
    }

    protected String cleanExtraSpace(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.replaceAll("(\n)+", StringUtils.LF);
        }
        return "";
    }
}
