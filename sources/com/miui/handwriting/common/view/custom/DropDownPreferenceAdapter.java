package com.miui.handwriting.common.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.res.TypedArrayUtils;
import com.miui.creation.common.R;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class DropDownPreferenceAdapter extends ArrayAdapter {
    private CharSequence[] mDemoItems;
    private LayoutInflater mInflater;

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DropDownPreferenceAdapter(Context context, AttributeSet attributeSet) {
        super(context, R.layout.miuix_appcompat_simple_spinner_layout, android.R.id.text1);
        this.mInflater = LayoutInflater.from(context);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.preference.R.styleable.DropDownPreference);
            this.mDemoItems = TypedArrayUtils.getTextArray(typedArrayObtainStyledAttributes, miuix.preference.R.styleable.DropDownPreference_entries, 0);
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        this.mDemoItems = new CharSequence[]{"DropDown1", "DropDown2", "DropDown3"};
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public int getCount() {
        return this.mDemoItems.length;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public Object getItem(int i) {
        return this.mDemoItems[i];
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.dropdown_adapter_layout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view;
            view.setTag(viewHolder);
        }
        Object tag = view.getTag();
        if (tag != null) {
            ((ViewHolder) tag).text.setText((CharSequence) getItem(i));
        }
        return view;
    }

    @Override // android.widget.ArrayAdapter
    public void clear() {
        this.mDemoItems = new CharSequence[0];
    }

    @Override // android.widget.ArrayAdapter
    public void addAll(Object[] objArr) {
        CharSequence[] charSequenceArr = this.mDemoItems;
        CharSequence[] charSequenceArr2 = (CharSequence[]) Arrays.copyOf(charSequenceArr, charSequenceArr.length + objArr.length);
        System.arraycopy(objArr, 0, charSequenceArr2, this.mDemoItems.length, objArr.length);
        this.mDemoItems = charSequenceArr2;
    }

    private static class ViewHolder {
        TextView text;

        private ViewHolder() {
        }
    }
}
