package miuix.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.os.Process;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.CharUtils;

/* JADX INFO: loaded from: classes6.dex */
public class DropBoxLog {
    private static final String CLOUD_DROP_BOX_LOG_TAG = "micloud";
    private static final SoftReferenceSingleton<SimpleDateFormat> DATE_FORMAT = new SoftReferenceSingleton<SimpleDateFormat>() { // from class: miuix.util.DropBoxLog.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // miuix.util.SoftReferenceSingleton
        public SimpleDateFormat createInstance() {
            return new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        }
    };
    private StringBuilder mStringBuilder = new StringBuilder();

    public DropBoxLog v(String str, String str2) {
        android.util.Log.v(str, str2);
        appendLog("V", str, str2, null);
        return this;
    }

    public DropBoxLog v(String str, String str2, Throwable th) {
        android.util.Log.v(str, str2, th);
        appendLog("V", str, str2, th);
        return this;
    }

    public DropBoxLog d(String str, String str2) {
        android.util.Log.d(str, str2);
        appendLog(Field.DOUBLE_SIGNATURE_PRIMITIVE, str, str2, null);
        return this;
    }

    public DropBoxLog d(String str, String str2, Throwable th) {
        android.util.Log.d(str, str2, th);
        appendLog(Field.DOUBLE_SIGNATURE_PRIMITIVE, str, str2, th);
        return this;
    }

    public DropBoxLog i(String str, String str2) {
        android.util.Log.i(str, str2);
        appendLog(Field.INT_SIGNATURE_PRIMITIVE, str, str2, null);
        return this;
    }

    public DropBoxLog i(String str, String str2, Throwable th) {
        android.util.Log.i(str, str2, th);
        appendLog(Field.INT_SIGNATURE_PRIMITIVE, str, str2, th);
        return this;
    }

    public DropBoxLog w(String str, String str2) {
        android.util.Log.w(str, str2);
        appendLog(ExifInterface.LONGITUDE_WEST, str, str2, null);
        return this;
    }

    public DropBoxLog w(String str, String str2, Throwable th) {
        android.util.Log.w(str, str2, th);
        appendLog(ExifInterface.LONGITUDE_WEST, str, str2, th);
        return this;
    }

    public DropBoxLog e(String str, String str2) {
        android.util.Log.e(str, str2);
        appendLog(ExifInterface.LONGITUDE_EAST, str, str2, null);
        return this;
    }

    public DropBoxLog e(String str, String str2, Throwable th) {
        android.util.Log.e(str, str2, th);
        appendLog(ExifInterface.LONGITUDE_EAST, str, str2, th);
        return this;
    }

    public String getMessage() {
        return this.mStringBuilder.toString();
    }

    public void commit(Context context, String str) {
        if (str == null) {
            str = context.getPackageName();
        }
        writeLog(context, this.mStringBuilder, "micloud_" + str);
        this.mStringBuilder.setLength(0);
    }

    private void appendLog(String str, String str2, String str3, Throwable th) {
        this.mStringBuilder.append(DATE_FORMAT.get().format(new Date()));
        this.mStringBuilder.append(' ');
        this.mStringBuilder.append(str);
        this.mStringBuilder.append('/');
        this.mStringBuilder.append(str2);
        this.mStringBuilder.append(CoreConstants.LEFT_PARENTHESIS_CHAR);
        this.mStringBuilder.append(Process.myPid());
        this.mStringBuilder.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        this.mStringBuilder.append(CoreConstants.COLON_CHAR);
        this.mStringBuilder.append(str3);
        if (th != null) {
            this.mStringBuilder.append(CoreConstants.COLON_CHAR);
            this.mStringBuilder.append(android.util.Log.getStackTraceString(th));
        }
        this.mStringBuilder.append(CharUtils.CR);
    }

    public static void writeLog(Context context, CharSequence charSequence, String str) {
        DropBoxManager dropBoxManager = (DropBoxManager) context.getSystemService("dropbox");
        if (charSequence == null || charSequence.length() <= 0 || dropBoxManager == null || !dropBoxManager.isTagEnabled(str)) {
            return;
        }
        dropBoxManager.addText(str, charSequence.toString());
    }
}
