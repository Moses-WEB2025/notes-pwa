package com.xiaomi.passport.ui.page;

import android.accounts.Account;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.tool.TextProcessUtils;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.account.data.Gender;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.PassportSDK;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.task.QueryUserInfoTask;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.settings.AccountPreferenceView;
import com.xiaomi.passport.ui.settings.UploadMiUserProfileTask;
import com.xiaomi.passport.ui.settings.UploadProfileType;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.utils.PrivacyMasker;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class UserInfoFragment extends BaseFragment {
    private static final String TAG = "UserInfoFragment";
    private Account mAccount;
    private Activity mActivity;
    private AccountPreferenceView mPrefUserAvatar;
    private AccountPreferenceView mPrefUserEmail;
    private AccountPreferenceView mPrefUserGender;
    private AccountPreferenceView mPrefUserID;
    private AccountPreferenceView mPrefUserName;
    private AccountPreferenceView mPrefUserPassword;
    private AccountPreferenceView mPrefUserPhone;
    private PassportDialog mQueryUserInfoLoadingDialog;
    private QueryUserInfoTask mQueryUserInfoTask;
    private Bitmap mUserAvatarBitmap;
    private boolean mHasQueryUserInfoResult = false;
    private HashMap<UploadProfileType, UploadMiUserProfileTask> mUploadTask = new HashMap<>();
    private final DialogInterface.OnClickListener mUpdateAvatarOnClickListener = new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserInfoFragment.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == 0) {
                UserInfoFragment.this.startUpdateAvatarActivity(UserAvatarUpdateActivity.CAMERA);
            } else if (i == 1) {
                UserInfoFragment.this.startUpdateAvatarActivity("gallery");
            }
            dialogInterface.dismiss();
        }
    };
    private final View.OnClickListener mPreferenceItemCallback = new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserInfoFragment.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                if (view != UserInfoFragment.this.mPrefUserName) {
                    if (view == UserInfoFragment.this.mPrefUserGender) {
                        UserInfoFragment.this.showUserGenderUpdateDialog();
                        return;
                    }
                    if (view != UserInfoFragment.this.mPrefUserAvatar) {
                        if (view == UserInfoFragment.this.mPrefUserPhone) {
                            UserInfoFragment.this.statClickWithBoolean(R.string.passport_stat_tip_user_info_page_click_safe_phone, Boolean.valueOf(!UserInfoFragment.this.mPrefUserPhone.getValue().equals(UserInfoFragment.this.getString(R.string.passport_account_none_bind_info))).booleanValue());
                            UserInfoFragment userInfoFragment = UserInfoFragment.this;
                            userInfoFragment.startActivity(PassportPageIntent.getModifyBoundPhonePageIntent(userInfoFragment.getContext()));
                            return;
                        } else if (view == UserInfoFragment.this.mPrefUserEmail) {
                            UserInfoFragment.this.statClickWithBoolean(R.string.passport_stat_tip_user_info_page_click_safe_email, Boolean.valueOf(!UserInfoFragment.this.mPrefUserEmail.getValue().equals(UserInfoFragment.this.getString(R.string.passport_account_none_bind_info))).booleanValue());
                            UserInfoFragment userInfoFragment2 = UserInfoFragment.this;
                            userInfoFragment2.startActivity(PassportPageIntent.getModifyBoundEmailPageIntent(userInfoFragment2.getContext()));
                            return;
                        } else {
                            if (view == UserInfoFragment.this.mPrefUserPassword) {
                                UserInfoFragment.this.statClickWithBoolean(R.string.passport_stat_tip_user_info_page_click_change_password, XiaomiAccountManager.get(UserInfoFragment.this.getActivity()).getUserData(UserInfoFragment.this.mAccount, AccountIntent.EXTRA_HAS_PASSWORD).equals("true"));
                                UserInfoFragment userInfoFragment3 = UserInfoFragment.this;
                                userInfoFragment3.startActivity(PassportPageIntent.getModifyPasswordPageIntent(userInfoFragment3.getContext()));
                                return;
                            }
                            return;
                        }
                    }
                    UserInfoFragment.this.statClick(R.string.passport_stat_tip_user_info_page_click_avatar);
                    new PassportDialog(UserInfoFragment.this.getContext()).setTitle(UserInfoFragment.this.getString(R.string.passport_user_avatar_update_title)).setList(new String[]{UserInfoFragment.this.getString(R.string.passport_account_user_avatar_from_camera), UserInfoFragment.this.getString(R.string.passport_account_user_avatar_from_album)}, -1, UserInfoFragment.this.mUpdateAvatarOnClickListener).show();
                    return;
                }
                UserInfoFragment.this.statClick(R.string.passport_stat_tip_user_info_page_click_nickname);
                UserInfoFragment.this.showUserNameUpdateDialog();
            } catch (ActivityNotFoundException e) {
                AccountLogger.log(UserInfoFragment.TAG, "activity not found", e);
                AccountToast.showToastMessage(UserInfoFragment.this.getActivity(), R.string.passport_activity_not_found_notice, 1);
            }
        }
    };

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mAccount = XiaomiAccountManager.get(activity).getXiaomiAccount();
        PassportDialog message = new PassportDialog(getContext()).setLoadingProgressVisible(true).setMessage(getString(R.string.passport_dialog_loading));
        this.mQueryUserInfoLoadingDialog = message;
        message.setCancelable(false);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_user_info, viewGroup, false);
        this.mPrefUserAvatar = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_avatar);
        this.mPrefUserName = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_user_name);
        this.mPrefUserID = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_user_id);
        this.mPrefUserGender = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_user_gender);
        this.mPrefUserPhone = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_user_phone);
        this.mPrefUserEmail = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_user_email);
        this.mPrefUserPassword = (AccountPreferenceView) viewInflate.findViewById(R.id.pref_account_password);
        this.mPrefUserAvatar.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserName.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserID.setRightArrowVisible(false);
        this.mPrefUserGender.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserPhone.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserEmail.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserPassword.setOnClickListener(this.mPreferenceItemCallback);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateAvatarActivity(String str) {
        Intent intent = new Intent(getActivity(), (Class<?>) UserAvatarUpdateActivity.class);
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra(UserAvatarUpdateActivity.EXTRA_UPDATE_AVATAR_TYPE, str);
        intent.putExtra(UserAvatarUpdateActivity.EXTRA_UPDATE_ACCOUNT, this.mAccount);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void statClickWithBoolean(int i, boolean z) {
        HashMap map = new HashMap();
        map.put(StatConstants.Param.BOOLEAN_EXTRA, Boolean.valueOf(z));
        statClick(i, map);
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        queryUserInfoOnline();
    }

    private void queryUserInfoOnline() {
        if (this.mQueryUserInfoTask == null || AsyncTask.Status.RUNNING != this.mQueryUserInfoTask.getStatus()) {
            if (!this.mHasQueryUserInfoResult) {
                this.mQueryUserInfoLoadingDialog.show();
            }
            QueryUserInfoTask queryUserInfoTask = new QueryUserInfoTask(this.mActivity.getApplicationContext(), new UpdateOnlineUserInfoCallback(this));
            this.mQueryUserInfoTask = queryUserInfoTask;
            queryUserInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mActivity = getActivity();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        cancelUploadProfileTask();
        Bitmap bitmap = this.mUserAvatarBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mUserAvatarBitmap = null;
        }
        this.mActivity = null;
        super.onDestroy();
    }

    private void cancelUploadProfileTask() {
        HashMap<UploadProfileType, UploadMiUserProfileTask> map = this.mUploadTask;
        if (map == null) {
            return;
        }
        Iterator<UploadProfileType> it = map.keySet().iterator();
        while (it.hasNext()) {
            UploadMiUserProfileTask uploadMiUserProfileTask = this.mUploadTask.get(it.next());
            if (uploadMiUserProfileTask != null) {
                uploadMiUserProfileTask.cancel(true);
            }
            it.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUserInfo() {
        String string;
        String strMaskPlainPhoneAfterLogin;
        PassportDialog passportDialog = this.mQueryUserInfoLoadingDialog;
        if (passportDialog != null) {
            passportDialog.dismiss();
        }
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (this.mAccount == null) {
            this.mActivity.finish();
            return;
        }
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(this.mActivity);
        String userData = xiaomiAccountManager.getUserData(this.mAccount, Constants.ACCOUNT_USER_NAME);
        if (TextUtils.isEmpty(userData)) {
            userData = getString(R.string.passport_account_none_user_name);
        }
        this.mPrefUserName.setValue(userData);
        this.mPrefUserID.setValue(this.mAccount.name);
        String userData2 = xiaomiAccountManager.getUserData(this.mAccount, Constants.ACCOUNT_USER_GENDER);
        if (TextUtils.isEmpty(userData2)) {
            string = getString(R.string.passport_account_no_set);
        } else {
            string = getResources().getStringArray(R.array.passport_account_user_gender_name)[!userData2.equals(Gender.MALE.getType()) ? 1 : 0];
        }
        this.mPrefUserGender.setValue(string);
        String userData3 = xiaomiAccountManager.getUserData(this.mAccount, Constants.ACCOUNT_AVATAR_FILE_NAME);
        Bitmap bitmap = this.mUserAvatarBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        Bitmap bitmapCreatePhoto = SysHelper.createPhoto(getActivity(), userData3);
        this.mUserAvatarBitmap = bitmapCreatePhoto;
        if (bitmapCreatePhoto != null) {
            this.mPrefUserAvatar.setImageBitmap(bitmapCreatePhoto);
        }
        String userData4 = xiaomiAccountManager.getUserData(this.mAccount, Constants.ACCOUNT_USER_EMAIL);
        this.mPrefUserEmail.setValue(TextUtils.isEmpty(userData4) ? getString(R.string.passport_account_none_bind_info) : PrivacyMasker.maskEmailAferLogin(userData4));
        String userData5 = xiaomiAccountManager.getUserData(this.mAccount, Constants.ACCOUNT_USER_PHONE);
        if (TextUtils.isEmpty(userData5) && PassportSDK.isInternational()) {
            this.mPrefUserPhone.setVisibility(8);
            return;
        }
        this.mPrefUserPhone.setVisibility(0);
        AccountPreferenceView accountPreferenceView = this.mPrefUserPhone;
        if (TextUtils.isEmpty(userData5)) {
            strMaskPlainPhoneAfterLogin = getString(R.string.passport_account_none_bind_info);
        } else {
            strMaskPlainPhoneAfterLogin = PrivacyMasker.maskPlainPhoneAfterLogin(userData5);
        }
        accountPreferenceView.setValue(strMaskPlainPhoneAfterLogin);
    }

    private class UpdateOnlineUserInfoCallback implements QueryUserInfoTask.QueryUserInfoCallback {
        private final WeakReference<UserInfoFragment> mFragmentWeakRef;

        UpdateOnlineUserInfoCallback(UserInfoFragment userInfoFragment) {
            this.mFragmentWeakRef = new WeakReference<>(userInfoFragment);
        }

        @Override // com.xiaomi.passport.task.QueryUserInfoTask.QueryUserInfoCallback
        public void onResult(XiaomiUserCoreInfo xiaomiUserCoreInfo) {
            if (!UserInfoFragment.this.mHasQueryUserInfoResult) {
                UserInfoFragment.this.mHasQueryUserInfoResult = xiaomiUserCoreInfo != null;
            }
            UserInfoFragment userInfoFragment = this.mFragmentWeakRef.get();
            if (userInfoFragment != null) {
                userInfoFragment.refreshUserInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserNameUpdateDialog() {
        View viewInflate = View.inflate(getContext(), R.layout.passport_layout_dialog_nickname_input, null);
        final EditTextGroupView editTextGroupView = (EditTextGroupView) viewInflate.findViewById(R.id.nick_name);
        editTextGroupView.setInputText(this.mPrefUserName.getValue().toString());
        new PassportDialog(getContext()).setTitle(getString(R.string.passport_account_user_name_dialog_title)).setPositiveButton(getString(android.R.string.ok), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserInfoFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String inputText = editTextGroupView.getInputText();
                String userNameInvalidReason = UserInfoFragment.this.getUserNameInvalidReason(inputText);
                if (TextUtils.isEmpty(userNameInvalidReason)) {
                    UserInfoFragment.this.startUploadUserProfileTask(UploadProfileType.TYPE_USER_NAME, inputText, null, null);
                } else {
                    UserInfoFragment.this.showToast(userNameInvalidReason);
                }
            }
        }).setNegativeButton(getString(android.R.string.cancel), null).setView(viewInflate).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUploadUserProfileTask(UploadProfileType uploadProfileType, String str, Calendar calendar, Gender gender) {
        if (uploadProfileType == null) {
            return;
        }
        UploadMiUserProfileTask uploadMiUserProfileTask = this.mUploadTask.get(uploadProfileType);
        if (uploadMiUserProfileTask != null) {
            uploadMiUserProfileTask.cancel(true);
        }
        new UploadMiUserProfileTask(getActivity(), str, gender, new setUserProfileValueAfterTask()).executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
    }

    private class setUserProfileValueAfterTask implements UploadMiUserProfileTask.IUploadUserProfile {
        private setUserProfileValueAfterTask() {
        }

        @Override // com.xiaomi.passport.ui.settings.UploadMiUserProfileTask.IUploadUserProfile
        public void onFinishUploading(String str, Gender gender) {
            XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(UserInfoFragment.this.getActivity());
            if (!TextUtils.isEmpty(str)) {
                UserInfoFragment.this.mPrefUserName.setValue(str);
                xiaomiAccountManager.setUserData(UserInfoFragment.this.mAccount, Constants.ACCOUNT_USER_NAME, str);
            } else if (gender != null) {
                UserInfoFragment.this.mPrefUserGender.setValue(UserInfoFragment.this.getResources().getStringArray(R.array.passport_account_user_gender_name)[gender == Gender.MALE ? (char) 0 : (char) 1]);
                xiaomiAccountManager.setUserData(UserInfoFragment.this.mAccount, Constants.ACCOUNT_USER_GENDER, gender.getType());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getUserNameInvalidReason(String str) {
        if (TextUtils.isEmpty(str)) {
            return getString(R.string.passport_account_empty_user_name);
        }
        if (str.length() < 2) {
            return getString(R.string.passport_account_error_shorter_user_name, 2);
        }
        if (str.length() > 20) {
            return getString(R.string.passport_account_error_longer_user_name, 20);
        }
        if (str.matches(TextProcessUtils.ONE_OR_MULTI_SPACE_REG)) {
            return getString(R.string.passport_account_error_all_space_user_name);
        }
        if (str.contains("<") || str.contains(">") || str.contains(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            return getString(R.string.passport_account_error_invalid_user_name);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserGenderUpdateDialog() {
        FragmentActivity activity = getActivity();
        String[] stringArray = getResources().getStringArray(R.array.passport_account_user_gender_name);
        String string = this.mPrefUserGender.getValue().toString();
        PassportDialog passportDialog = new PassportDialog(activity);
        PassportDialog title = passportDialog.setTitle(getString(R.string.passport_account_user_gender));
        boolean zEquals = string.equals(stringArray[1]);
        title.setList(stringArray, zEquals ? 1 : 0, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserInfoFragment.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UserInfoFragment.this.startUploadUserProfileTask(UploadProfileType.TYPE_GENDER, null, null, i == 0 ? Gender.MALE : Gender.FEMALE);
            }
        });
        passportDialog.show();
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        return new BaseFragment.StatParams("用户中心页面", getString(R.string.passport_stat_tip_user_info_page_browse));
    }
}
