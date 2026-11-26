package com.xiaomi.passport.ui.page;

import android.R;
import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.settings.utils.PermissionRequestHistory;
import com.xiaomi.passport.ui.settings.utils.PermissionUtils;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.OsHelper;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import defpackage.R2;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class UserAvatarUpdateFragment extends com.xiaomi.passport.ui.settings.BaseFragment {
    private static final String AVATAR_FILE_NAME = "xiaomi_user_avatar_file";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final String FILE_PROVIDER_AUTHORITY_SUFFIX = ".passport.fileprovider";
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;
    private static final int PERMISSION_SETTINGS_REQUEST_CODE = 2000;
    private static final int REQUEST_CROP_PHOTO = 1004;
    private static final int REQUEST_PICK_PHOTO_FROM_GALLERY = 1003;
    private static final int REQUEST_TAKE_PHOTO = 1002;
    private static final String TAG = "UserAvatarUpdateFragment";
    private Account mAccount;
    private File mAvatarCachedFile;
    private Uri mFileProviderUri;
    private UploadUserAvatarTask mUploadAvatarTask;

    static /* synthetic */ File access$300(UserAvatarUpdateFragment userAvatarUpdateFragment) {
        return userAvatarUpdateFragment.getFileProviderFile();
    }

    static /* synthetic */ void access$400(UserAvatarUpdateFragment userAvatarUpdateFragment, File file) {
        userAvatarUpdateFragment.deleteFile(file);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity().getPackageManager().hasSystemFeature("android.hardware.camera")) {
            AccountLogger.log(TAG, "has camera");
        } else {
            AccountLogger.log(TAG, "no camera");
        }
        this.mAccount = (Account) getArguments().getParcelable(UserAvatarUpdateActivity.EXTRA_UPDATE_ACCOUNT);
        String string = getArguments().getString(UserAvatarUpdateActivity.EXTRA_UPDATE_AVATAR_TYPE);
        if (UserAvatarUpdateActivity.CAMERA.equals(string)) {
            checkCameraPermissionAndTakePhoto();
        } else if ("gallery".equals(string)) {
            checkAgreementAndStartPickPhoto();
        } else {
            finishActivity();
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (XiaomiAccountManager.get(getActivity()).getXiaomiAccount() == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            finishActivity();
        }
        View viewFindViewById = getActivity().findViewById(R.id.content);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UserAvatarUpdateFragment.this.finishActivity();
                }
            });
        }
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            PermissionRequestHistory.setPermissionRequested(getActivity(), "android.permission.CAMERA");
            if (iArr.length > 0 && iArr[0] == 0) {
                startTakePhoto();
                return;
            } else {
                finishActivity();
                return;
            }
        }
        if (i == 200) {
            if (iArr.length > 0 && iArr[0] == 0) {
                startPickPhotoFromGallery();
            } else {
                finishActivity();
            }
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 1002:
            case 1003:
                if (i2 == -1) {
                    Uri data = intent != null ? intent.getData() : null;
                    if (data == null) {
                        data = getFileProviderUri();
                    }
                    doCropPhoto(data);
                    return;
                }
                break;
            case 1004:
                if (intent != null && intent.getData() != null) {
                    try {
                        startUploadAvatar(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData()));
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (i2 == -1) {
                    startUploadAvatar(null);
                    return;
                }
                break;
            default:
                return;
        }
        finishActivity();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        UploadUserAvatarTask uploadUserAvatarTask = this.mUploadAvatarTask;
        if (uploadUserAvatarTask != null) {
            uploadUserAvatarTask.cancel(true);
            this.mUploadAvatarTask = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRequestCameraPermission() {
        getActivity();
        requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
    }

    private void checkCameraPermissionAndTakePhoto() {
        final Activity activity = getActivity();
        if (ContextCompat.checkSelfPermission(activity, "android.permission.CAMERA") != 0) {
            int[] iArr = {com.xiaomi.account.passportsdk.account_sso.R.string.passport_request_camera_permission_message, com.xiaomi.account.passportsdk.account_sso.R.string.passport_i_know, com.xiaomi.account.passportsdk.account_sso.R.string.passport_open_settings};
            if (canShowPermissionPrompt(iArr)) {
                PassportDialog passportDialog = new PassportDialog(getContext());
                passportDialog.setPositiveButton(getString(iArr[1]), null).setNegativeButton(getString(R.string.cancel), null).setMessage(getString(iArr[0]));
                if (PermissionRequestHistory.isPermissionPermanentlyDenied(activity, "android.permission.CAMERA")) {
                    passportDialog.setNegativeButton(getString(iArr[2]), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            PermissionUtils.openPermissionSetting(activity, 2000);
                        }
                    });
                }
                passportDialog.setCancelable(false);
                passportDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        UserAvatarUpdateFragment.this.startRequestCameraPermission();
                    }
                });
                passportDialog.show();
                return;
            }
            startRequestCameraPermission();
            return;
        }
        startTakePhoto();
    }

    private boolean canShowPermissionPrompt(int[] iArr) {
        for (int i : iArr) {
            try {
                getString(i);
            } catch (Resources.NotFoundException unused) {
                return false;
            }
        }
        return true;
    }

    private void startTakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", getFileProviderUri());
        intent.addFlags(2);
        startActivityForResult(intent, 1002);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPermission() {
        if (Build.VERSION.SDK_INT >= 33 && getActivity().getApplicationInfo().targetSdkVersion >= 33) {
            return "android.permission.READ_MEDIA_IMAGES";
        }
        return "android.permission.READ_EXTERNAL_STORAGE";
    }

    private void checkAgreementAndStartPickPhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), getPermission()) != 0) {
            PassportDialog passportDialog = new PassportDialog(getActivity());
            passportDialog.setTitle(getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_request_permission_title)).setMessage(getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_request_gallery_permission_message)).setPositiveButton(getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_request_agree), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UserAvatarUpdateFragment userAvatarUpdateFragment = UserAvatarUpdateFragment.this;
                    userAvatarUpdateFragment.requestPermissions(new String[]{userAvatarUpdateFragment.getPermission()}, 200);
                }
            }).setNegativeButton(getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_request_cancel), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UserAvatarUpdateFragment.this.finishActivity();
                }
            });
            passportDialog.setCancelable(false);
            passportDialog.show();
            return;
        }
        startPickPhotoFromGallery();
    }

    private void startPickPhotoFromGallery() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        startActivityForResult(intent, 1003);
    }

    private void doCropPhoto(Uri uri) {
        if (uri == null) {
            AccountLogger.log(TAG, "inputUri is null");
            return;
        }
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri fileProviderUri = getFileProviderUri();
            intent.setDataAndType(uri, ShareAppsAdapter.TYPE_IMAGE);
            intent.putExtra("output", fileProviderUri);
            intent.addFlags(1);
            Activity activity = getActivity();
            Iterator<ResolveInfo> it = activity.getPackageManager().queryIntentActivities(intent, 0).iterator();
            while (it.hasNext()) {
                String str = it.next().activityInfo.packageName;
                activity.grantUriPermission(str, uri, 1);
                activity.grantUriPermission(str, fileProviderUri, 3);
            }
            intent.putExtra("tips", getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_account_crop_user_avatar));
            addCropExtras(intent, getPhotoPickSize());
            startActivityForResult(intent, 1004);
        } catch (Exception e) {
            AccountLogger.log(TAG, "Cannot crop image", e);
            AccountToast.showToastMessage(getActivity(), com.xiaomi.account.passportsdk.account_sso.R.string.passport_photo_picker_not_found, 1);
        }
    }

    private static void addCropExtras(Intent intent, int i) {
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        if (OsHelper.isEmui() || OsHelper.isVivo()) {
            intent.putExtra("aspectX", R2.dimen.widget_text_size);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        if (OsHelper.isEmui() || OsHelper.isOppo() || OsHelper.isVivo()) {
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
        } else {
            intent.putExtra("outputX", i);
            intent.putExtra("outputY", i);
        }
        if (Build.BRAND.equalsIgnoreCase("HONOR")) {
            return;
        }
        intent.putExtra("return-data", true);
    }

    private int getPhotoPickSize() {
        return getResources().getDimensionPixelSize(com.xiaomi.account.passportsdk.account_sso.R.dimen.passport_upload_user_avatar_size);
    }

    private Uri getFileProviderUri() {
        if (this.mFileProviderUri == null) {
            this.mFileProviderUri = FileProvider.getUriForFile(getActivity(), getCurrentPackageAuthority(), getFileProviderFile());
        }
        return this.mFileProviderUri;
    }

    private String getCurrentPackageAuthority() {
        return getActivity().getPackageName() + FILE_PROVIDER_AUTHORITY_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getFileProviderFile() {
        if (this.mAvatarCachedFile == null) {
            this.mAvatarCachedFile = new File(getActivity().getCacheDir(), AVATAR_FILE_NAME);
        }
        return this.mAvatarCachedFile;
    }

    private void startUploadAvatar(Bitmap bitmap) {
        UploadUserAvatarTask uploadUserAvatarTask = this.mUploadAvatarTask;
        if (uploadUserAvatarTask != null) {
            uploadUserAvatarTask.cancel(true);
            this.mUploadAvatarTask = null;
        }
        UploadUserAvatarTask uploadUserAvatarTask2 = new UploadUserAvatarTask(getActivity(), bitmap);
        this.mUploadAvatarTask = uploadUserAvatarTask2;
        uploadUserAvatarTask2.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
    }

    private class UploadUserAvatarTask extends AsyncTask<Void, Void, UploadAvatarTaskResult> {
        private final Bitmap mBitmap;
        private Context mContext;
        private PassportDialog mLoginLoadingDialog;

        UploadUserAvatarTask(Context context, Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mContext = context.getApplicationContext();
            PassportDialog passportDialog = new PassportDialog(UserAvatarUpdateFragment.this.getActivity());
            this.mLoginLoadingDialog = passportDialog;
            passportDialog.setLoadingProgressVisible(true);
            this.mLoginLoadingDialog.setMessage(UserAvatarUpdateFragment.this.getString(com.xiaomi.account.passportsdk.account_sso.R.string.passport_user_avatar_uploading));
            this.mLoginLoadingDialog.setCancelable(false);
            this.mLoginLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.UploadUserAvatarTask.1
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    UploadUserAvatarTask.this.cancel(true);
                }
            });
            this.mLoginLoadingDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /*  JADX ERROR: Type inference failed
            jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 3271. Try increasing type updates limit count.
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
            */
        @Override // android.os.AsyncTask
        public com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.UploadAvatarTaskResult doInBackground(java.lang.Void... r15) {
            /*
                Method dump skipped, instruction units count: 327
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.ui.page.UserAvatarUpdateFragment.UploadUserAvatarTask.doInBackground(java.lang.Void[]):com.xiaomi.passport.ui.page.UserAvatarUpdateFragment$UploadAvatarTaskResult");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(UploadAvatarTaskResult uploadAvatarTaskResult) {
            super.onPostExecute(uploadAvatarTaskResult);
            UserAvatarUpdateFragment.this.finishActivity();
            this.mLoginLoadingDialog.dismiss();
            if (uploadAvatarTaskResult != null && uploadAvatarTaskResult.bitmap == null) {
                AccountToast.showToastMessage(this.mContext, uploadAvatarTaskResult.errorMsgResId == -1 ? com.xiaomi.account.passportsdk.account_sso.R.string.passport_error_unknown : uploadAvatarTaskResult.errorMsgResId);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(UploadAvatarTaskResult uploadAvatarTaskResult) {
            if (uploadAvatarTaskResult != null && uploadAvatarTaskResult.bitmap != null) {
                uploadAvatarTaskResult.bitmap.recycle();
            }
            super.onCancelled(uploadAvatarTaskResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishActivity() {
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
    }

    private class UploadAvatarTaskResult {
        public Bitmap bitmap;
        int errorMsgResId;

        UploadAvatarTaskResult(int i, Bitmap bitmap) {
            this.errorMsgResId = i;
            this.bitmap = bitmap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFile(File file) {
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
