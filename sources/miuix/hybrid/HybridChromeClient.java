package miuix.hybrid;

import android.content.DialogInterface;
import android.net.Uri;
import android.view.KeyEvent;
import miuix.appcompat.app.AlertDialog;
import miuix.internal.hybrid.HybridManager;

/* JADX INFO: loaded from: classes5.dex */
public class HybridChromeClient {
    private HybridManager mManager;

    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
    }

    public void onReceivedTitle(HybridView hybridView, String str) {
    }

    public boolean onShowFileChooser(HybridView hybridView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        return false;
    }

    public void setHybridManager(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }

    public boolean onJsAlert(HybridView hybridView, String str, String str2, final JsResult jsResult) {
        new AlertDialog.Builder(this.mManager.getActivity()).setMessage(str2).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: miuix.hybrid.HybridChromeClient.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.confirm();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: miuix.hybrid.HybridChromeClient.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                jsResult.cancel();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: miuix.hybrid.HybridChromeClient.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return true;
                }
                jsResult.confirm();
                return false;
            }
        }).show();
        return true;
    }

    public boolean onJsConfirm(HybridView hybridView, String str, String str2, final JsResult jsResult) {
        new AlertDialog.Builder(this.mManager.getActivity()).setMessage(str2).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: miuix.hybrid.HybridChromeClient.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.confirm();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: miuix.hybrid.HybridChromeClient.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: miuix.hybrid.HybridChromeClient.5
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                jsResult.cancel();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: miuix.hybrid.HybridChromeClient.4
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return true;
                }
                jsResult.confirm();
                return false;
            }
        }).show();
        return true;
    }

    public void onProgressChanged(HybridView hybridView, int i) {
        hybridView.setProgress(i);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        valueCallback.onReceiveValue(null);
    }
}
