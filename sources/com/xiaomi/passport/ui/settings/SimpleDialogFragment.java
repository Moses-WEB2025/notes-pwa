package com.xiaomi.passport.ui.settings;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleDialogFragment extends DialogFragment {
    public static final String ARG_CANCELABLE = "cancelable";
    public static final String ARG_MESSAGE = "msg_res_id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_TYPE = "type";
    private static final String TAG = "SimpleDialogFragment";
    public static final int TYPE_ALERT = 1;
    public static final int TYPE_PROGRESS = 2;
    private boolean mCancelable = true;
    private CharSequence mMessage;
    private DialogInterface.OnClickListener mNegativeButtonClickListener;
    private int mNegativeButtonTextRes;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnClickListener mPositiveButtonClickListener;
    private int mPositiveButtonTextRes;
    private String mTitle;
    private int mType;

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("no argument");
        }
        this.mType = arguments.getInt("type");
        this.mMessage = arguments.getCharSequence(ARG_MESSAGE);
        this.mTitle = arguments.getString("title");
        this.mCancelable = arguments.getBoolean(ARG_CANCELABLE, true);
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        int i = this.mType;
        if (i != 1) {
            if (i == 2) {
                try {
                    if (isMiuiActivity()) {
                        Class<?> cls = Class.forName("miui.app.ProgressDialog");
                        Object objNewInstance = cls.getConstructor(Context.class).newInstance(getActivity());
                        cls.getMethod("setMessage", CharSequence.class).invoke(objNewInstance, this.mMessage);
                        cls.getMethod("setCancelable", Boolean.TYPE).invoke(objNewInstance, Boolean.valueOf(this.mCancelable));
                        ((Window) cls.getMethod("getWindow", new Class[0]).invoke(objNewInstance, new Object[0])).setGravity(80);
                        return (Dialog) objNewInstance;
                    }
                } catch (ClassNotFoundException e) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e);
                } catch (IllegalAccessException e2) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e2);
                } catch (IllegalArgumentException e3) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e3);
                } catch (InstantiationException e4) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e4);
                } catch (NoSuchMethodException e5) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e5);
                } catch (InvocationTargetException e6) {
                    AccountLogger.log(TAG, "ProgressDialog reflect exception: ", e6);
                }
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(this.mMessage);
                progressDialog.setCancelable(this.mCancelable);
                progressDialog.getWindow().setGravity(80);
                return progressDialog;
            }
            throw new IllegalStateException("unknown dialog type:" + this.mType);
        }
        try {
            if (isMiuiActivity()) {
                Class<?> cls2 = Class.forName("miui.app.AlertDialog$Builder");
                Object objNewInstance2 = cls2.getConstructor(Context.class).newInstance(getActivity());
                cls2.getMethod("setTitle", CharSequence.class).invoke(objNewInstance2, this.mTitle);
                cls2.getMethod("setMessage", CharSequence.class).invoke(objNewInstance2, this.mMessage);
                cls2.getMethod("setCancelable", Boolean.TYPE).invoke(objNewInstance2, Boolean.valueOf(this.mCancelable));
                if (this.mPositiveButtonTextRes > 0) {
                    cls2.getMethod("setPositiveButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(objNewInstance2, Integer.valueOf(this.mPositiveButtonTextRes), this.mPositiveButtonClickListener);
                }
                if (this.mNegativeButtonTextRes > 0) {
                    cls2.getMethod("setNegativeButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(objNewInstance2, Integer.valueOf(this.mNegativeButtonTextRes), this.mNegativeButtonClickListener);
                }
                return (Dialog) cls2.getMethod("create", new Class[0]).invoke(objNewInstance2, new Object[0]);
            }
        } catch (ClassNotFoundException e7) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e7);
        } catch (IllegalAccessException e8) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e8);
        } catch (IllegalArgumentException e9) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e9);
        } catch (InstantiationException e10) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e10);
        } catch (NoSuchMethodException e11) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e11);
        } catch (InvocationTargetException e12) {
            AccountLogger.log(TAG, "AlertDialog reflect exception: ", e12);
        }
        AlertDialog.Builder title = new AlertDialog.Builder(getActivity()).setMessage(this.mMessage).setCancelable(this.mCancelable).setTitle(this.mTitle);
        int i2 = this.mPositiveButtonTextRes;
        if (i2 > 0) {
            title.setPositiveButton(i2, this.mPositiveButtonClickListener);
        }
        int i3 = this.mNegativeButtonTextRes;
        if (i3 > 0) {
            title.setNegativeButton(i3, this.mNegativeButtonClickListener);
        }
        return title.create();
    }

    private boolean isMiuiActivity() {
        try {
            return Class.forName("miui.app.Activity").isInstance(getActivity());
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // android.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButtonTextRes = i;
        this.mNegativeButtonClickListener = onClickListener;
    }

    public void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveButtonTextRes = i;
        this.mPositiveButtonClickListener = onClickListener;
    }

    public static final class AlertDialogFragmentBuilder {
        private boolean mCancelable = true;
        private boolean mCreated;
        private CharSequence mMessage;
        private String mTitle;
        private int mType;

        public AlertDialogFragmentBuilder(int i) {
            this.mType = i;
        }

        public AlertDialogFragmentBuilder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public AlertDialogFragmentBuilder setCancelable(boolean z) {
            this.mCancelable = z;
            return this;
        }

        public AlertDialogFragmentBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public SimpleDialogFragment create() {
            if (this.mCreated) {
                throw new IllegalStateException("dialog has been created");
            }
            this.mCreated = true;
            SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", this.mTitle);
            bundle.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, this.mMessage);
            bundle.putBoolean(SimpleDialogFragment.ARG_CANCELABLE, this.mCancelable);
            bundle.putInt("type", this.mType);
            simpleDialogFragment.setArguments(bundle);
            return simpleDialogFragment;
        }
    }

    @Override // android.app.DialogFragment
    @Deprecated
    public void dismiss() {
        super.dismiss();
    }

    public void showAllowingStateLoss(FragmentManager fragmentManager, String str) {
        if (fragmentManager == null) {
            AccountLogger.log(TAG, "invalid parameter");
            return;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.add(this, str);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }
}
