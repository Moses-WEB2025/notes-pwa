package miuix.hybrid;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;

/* JADX INFO: loaded from: classes5.dex */
public abstract class FileChooserParams {
    public static final int MODE_OPEN = 0;
    public static final int MODE_OPEN_FOLDER = 2;
    public static final int MODE_OPEN_MULTIPLE = 1;
    public static final int MODE_SAVE = 3;

    public abstract Intent createIntent();

    public abstract String[] getAcceptTypes();

    public abstract String getFilenameHint();

    public abstract int getMode();

    public abstract CharSequence getTitle();

    public abstract boolean isCaptureEnabled();

    public static Uri[] parseResult(int i, Intent intent) {
        return WebChromeClient.FileChooserParams.parseResult(i, intent);
    }
}
