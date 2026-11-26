package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.utils.PhoneNumUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AreaCodePickerAdapter extends BaseAdapter implements SectionIndexer {
    private static final String TAG = "AreaCodePickerAdapter";
    private Context mContext;
    private List<PhoneNumUtil.CountryPhoneNumData> mList;
    private int[] mPositionForSection;
    private String[] mSectionAlphabets;
    private ArrayList<Integer> mSectionForPosition;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AreaCodePickerAdapter(Context context, List<PhoneNumUtil.CountryPhoneNumData> list, String[] strArr) {
        this.mContext = context;
        this.mList = new ArrayList(list);
        this.mSectionAlphabets = strArr;
        buildPickerSectionList();
    }

    private void buildPickerSectionList() {
        this.mSectionForPosition = new ArrayList<>(Arrays.asList(new Integer[getCount()]));
        String[] strArr = this.mSectionAlphabets;
        this.mPositionForSection = new int[strArr.length];
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        int i = -1;
        for (int i2 = 0; i2 < getCount(); i2++) {
            String str = (String) ((PhoneNumUtil.CountryPhoneNumData) getItem(i2)).countryIndex.first;
            int i3 = 0;
            while (true) {
                String[] strArr2 = this.mSectionAlphabets;
                if (i3 >= strArr2.length) {
                    i3 = -1;
                    break;
                } else if (TextUtils.equals(strArr2[i3], str)) {
                    break;
                } else {
                    i3++;
                }
            }
            if (i3 == -1) {
                i3 = 0;
            }
            this.mSectionForPosition.set(i2, Integer.valueOf(i3));
            if (i != i3) {
                while (i < i3) {
                    i++;
                    this.mPositionForSection[i] = i2;
                }
                i = i3;
            }
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        CountryCodeListItem countryCodeListItem;
        if (view == null) {
            countryCodeListItem = (CountryCodeListItem) View.inflate(this.mContext, R.layout.passport_layout_country_code_list_item, null);
        } else {
            countryCodeListItem = (CountryCodeListItem) view;
        }
        PhoneNumUtil.CountryPhoneNumData countryPhoneNumData = (PhoneNumUtil.CountryPhoneNumData) getItem(i);
        String[] strArr = this.mSectionAlphabets;
        if (strArr != null && strArr.length > 0 && ((Boolean) countryPhoneNumData.countryIndex.second).booleanValue()) {
            countryCodeListItem.bind(countryPhoneNumData, (String) countryPhoneNumData.countryIndex.first, true);
            return countryCodeListItem;
        }
        countryCodeListItem.bind(countryPhoneNumData, null, false);
        return countryCodeListItem;
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return this.mSectionAlphabets;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        return this.mPositionForSection[i];
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        if (this.mSectionForPosition.get(i) == null) {
            AccountLogger.log(TAG, "try get section but null, position: " + i);
            return -1;
        }
        return this.mSectionForPosition.get(i).intValue();
    }
}
