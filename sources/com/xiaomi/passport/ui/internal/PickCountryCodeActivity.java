package com.xiaomi.passport.ui.internal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.ui.settings.CloudHelper;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.passport.utils.PhoneNumUtil;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PickCountryCodeActivity extends LayoutWrapperActivity {
    public static String KEY_INT_COUNTRY_CODE = "code";
    private static String KEY_STRING_COUNTRY_ISO = "iso";
    private BgTask<Void> mGetCloudCountryCodeTask;
    private ListView mListView;
    private PassportDialog mProgressDialog;

    @Override // com.xiaomi.passport.ui.internal.LayoutWrapperActivity, com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHeaderTitle(getString(R.string.passport_area_code_title));
        fetchCountryCodeAndUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        if (this.mProgressDialog == null || isFinishing()) {
            return;
        }
        this.mProgressDialog.dismiss();
        this.mProgressDialog = null;
    }

    private void showProgressDialog() {
        if (this.mProgressDialog == null) {
            PassportDialog passportDialog = new PassportDialog(this);
            this.mProgressDialog = passportDialog;
            passportDialog.setLoadingProgressVisible(true);
            this.mProgressDialog.setMessage(getString(R.string.passport_dialog_loading));
            this.mProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.mProgressDialog.show();
    }

    public void fetchCountryCodeAndUpdate() {
        List<PhoneNumUtil.CountryPhoneNumData> countryCodeOnLocale = CountryCodePhoneNumber.getCountryCodeOnLocale(PhoneNumUtil.getLocale());
        if (countryCodeOnLocale != null) {
            updateCountryCodeView(countryCodeOnLocale);
            return;
        }
        showProgressDialog();
        BgTask<Void> bgTask = new BgTask<>(new FetchCountryCodeBgRunnable(), new SuccessResultRunnable(this), new ErrorResultRunnable(this));
        this.mGetCloudCountryCodeTask = bgTask;
        bgTask.execute();
    }

    private static class SuccessResultRunnable<T> implements BgTask.SuccessResultRunnable<T> {
        private final WeakReference<PickCountryCodeActivity> mActivityRef;

        SuccessResultRunnable(PickCountryCodeActivity pickCountryCodeActivity) {
            this.mActivityRef = new WeakReference<>(pickCountryCodeActivity);
        }

        @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
        public void run(T t) {
            PickCountryCodeActivity pickCountryCodeActivity = this.mActivityRef.get();
            if (pickCountryCodeActivity == null || pickCountryCodeActivity.isFinishing()) {
                return;
            }
            pickCountryCodeActivity.dismissProgressDialog();
            List<PhoneNumUtil.CountryPhoneNumData> countryCodeOnLocale = CountryCodePhoneNumber.getCountryCodeOnLocale(PhoneNumUtil.getLocale());
            if (countryCodeOnLocale != null) {
                pickCountryCodeActivity.updateCountryCodeView(countryCodeOnLocale);
            } else {
                AccountToast.showToastMessage(pickCountryCodeActivity, R.string.passport_network_error);
                pickCountryCodeActivity.finish();
            }
        }
    }

    private static class ErrorResultRunnable implements BgTask.ErrorResultRunnable {
        private final WeakReference<PickCountryCodeActivity> mActivityRef;

        ErrorResultRunnable(PickCountryCodeActivity pickCountryCodeActivity) {
            this.mActivityRef = new WeakReference<>(pickCountryCodeActivity);
        }

        @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
        public void run(Throwable th) {
            PickCountryCodeActivity pickCountryCodeActivity = this.mActivityRef.get();
            if (pickCountryCodeActivity == null || pickCountryCodeActivity.isFinishing()) {
                return;
            }
            pickCountryCodeActivity.dismissProgressDialog();
            pickCountryCodeActivity.finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        dismissProgressDialog();
        BgTask<Void> bgTask = this.mGetCloudCountryCodeTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mGetCloudCountryCodeTask = null;
        }
        super.onDestroy();
    }

    @Override // com.xiaomi.passport.ui.internal.LayoutWrapperActivity
    public void onCreateContentView(ViewGroup viewGroup) {
        LayoutInflater.from(this).inflate(R.layout.passport_activity_pick_country_code, viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCountryCodeView(List<PhoneNumUtil.CountryPhoneNumData> list) {
        this.mListView = (ListView) findViewById(R.id.fast_indexer_list);
        ArrayList arrayList = new ArrayList();
        if (list.get(0).countryIndex != null) {
            for (PhoneNumUtil.CountryPhoneNumData countryPhoneNumData : list) {
                if (!TextUtils.isEmpty((CharSequence) countryPhoneNumData.countryIndex.first) && !arrayList.contains(countryPhoneNumData.countryIndex.first)) {
                    arrayList.add((String) countryPhoneNumData.countryIndex.first);
                }
            }
        }
        Collections.sort(arrayList);
        this.mListView.setDividerHeight(0);
        this.mListView.setAdapter((ListAdapter) new AreaCodePickerAdapter(this, list, (String[]) arrayList.toArray(new String[0])));
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.xiaomi.passport.ui.internal.PickCountryCodeActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                PhoneNumUtil.CountryPhoneNumData countryPhoneNumData2 = (PhoneNumUtil.CountryPhoneNumData) PickCountryCodeActivity.this.mListView.getAdapter().getItem(i);
                Intent intent = new Intent();
                intent.putExtra(PickCountryCodeActivity.KEY_STRING_COUNTRY_ISO, countryPhoneNumData2.countryISO);
                intent.putExtra(PickCountryCodeActivity.KEY_INT_COUNTRY_CODE, CountryCodePhoneNumber.parseCountryCodeAsInt(countryPhoneNumData2.countryCode));
                PickCountryCodeActivity.this.setResult(-1, intent);
                PickCountryCodeActivity.this.finish();
            }
        });
        if (arrayList.size() > 0) {
            AlphabetFastIndexer alphabetFastIndexer = (AlphabetFastIndexer) findViewById(R.id.fast_indexer);
            alphabetFastIndexer.setVisibility(0);
            alphabetFastIndexer.setSectionAlphabets((String[]) arrayList.toArray(new String[0]));
            this.mListView.setOnScrollListener(new AlphabetFastIndexer.OnScrollerDecorator(alphabetFastIndexer, null) { // from class: com.xiaomi.passport.ui.internal.PickCountryCodeActivity.2
                @Override // com.xiaomi.passport.ui.internal.AlphabetFastIndexer.OnScrollerDecorator
                protected String itemToString(Object obj) {
                    return (String) ((PhoneNumUtil.CountryPhoneNumData) obj).countryIndex.first;
                }
            });
        }
    }

    private static class FetchCountryCodeBgRunnable implements BgTask.BgTaskRunnable<Void> {
        public static final String TAG = "FetchCountryCodeBgRunnable";

        private FetchCountryCodeBgRunnable() {
        }

        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public Void run() throws Throwable {
            String countryCode;
            String locale = PhoneNumUtil.getLocale();
            try {
                countryCode = CloudHelper.getCountryCode(locale);
            } catch (AccessDeniedException | AuthenticationFailureException | IOException e) {
                AccountLogger.log(TAG, "get country code exception: ", e);
                countryCode = null;
            }
            AccountLogger.log(TAG, "get country code result: " + countryCode);
            if (!TextUtils.isEmpty(countryCode)) {
                PhoneNumUtil.saveCloudCountryCodeInfoToStorage(countryCode, locale);
            }
            return null;
        }
    }

    @Override // com.xiaomi.passport.ui.BaseActivity
    protected BaseActivity.StatParams getStatParams() {
        return new BaseActivity.StatParams("国家码选择页面", null);
    }
}
