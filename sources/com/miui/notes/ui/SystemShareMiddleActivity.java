package com.miui.notes.ui;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.LifecycleOwnerKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.tool.PermissionNewUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.android.content.SystemIntent;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: compiled from: SystemShareMiddleActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\tH\u0002J\b\u0010\u001c\u001a\u00020\u0012H\u0002J\b\u0010\u001d\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R,\u0010\n\u001a\u0014\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\f\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/miui/notes/ui/SystemShareMiddleActivity;", "Lmiuix/appcompat/app/AppCompatActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mDialog", "Lmiuix/appcompat/app/ProgressDialog;", "imageUri", "Landroid/net/Uri;", "requestStoragePermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "getRequestStoragePermissionLauncher", "()Landroidx/activity/result/ActivityResultLauncher;", "setRequestStoragePermissionLauncher", "(Landroidx/activity/result/ActivityResultLauncher;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "getClipDataUri", "intent", "Landroid/content/Intent;", "checkPermissionAndSaveToGallery", "doSaveToGallery", "mShowCreatingProgressDialogTask", "Ljava/lang/Runnable;", "dismissProgressDialog", "registerForActivityResult", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SystemShareMiddleActivity extends AppCompatActivity {
    private Uri imageUri;
    private ProgressDialog mDialog;
    private ActivityResultLauncher<String[]> requestStoragePermissionLauncher;
    private final String TAG = "SystemShareMiddleActivity";
    private Runnable mShowCreatingProgressDialogTask = new Runnable() { // from class: com.miui.notes.ui.SystemShareMiddleActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            SystemShareMiddleActivity.mShowCreatingProgressDialogTask$lambda$1(this.f$0);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mShowCreatingProgressDialogTask$lambda$1$lambda$0(DialogInterface dialogInterface) {
    }

    private final void registerForActivityResult() {
    }

    public final ActivityResultLauncher<String[]> getRequestStoragePermissionLauncher() {
        return this.requestStoragePermissionLauncher;
    }

    public final void setRequestStoragePermissionLauncher(ActivityResultLauncher<String[]> activityResultLauncher) {
        this.requestStoragePermissionLauncher = activityResultLauncher;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.imageUri = (Uri) getIntent().getParcelableExtra("android.intent.extra.STREAM");
        Logger.INSTANCE.d(this.TAG, "onCreate imageUri: " + this.imageUri);
        if (this.imageUri == null) {
            Logger.INSTANCE.d(this.TAG, "onCreate: ");
            finish();
        }
        Runnable runnable = this.mShowCreatingProgressDialogTask;
        if (runnable != null) {
            runnable.run();
        }
        Uri uri = this.imageUri;
        Intrinsics.checkNotNull(uri);
        checkPermissionAndSaveToGallery(uri);
    }

    public final Uri getClipDataUri(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        ClipData clipData = intent.getClipData();
        if (clipData == null || clipData.getItemCount() <= 0) {
            return null;
        }
        return clipData.getItemAt(0).getUri();
    }

    private final void checkPermissionAndSaveToGallery(Uri imageUri) {
        if (Utils.isAboveSDK33() || LiteUtils.isSuperLite()) {
            doSaveToGallery(imageUri);
        } else {
            doSaveToGallery(imageUri);
            PermissionNewUtils.requestOnStoragePermission(this.requestStoragePermissionLauncher);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1, reason: invalid class name */
    /* JADX INFO: compiled from: SystemShareMiddleActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1", f = "SystemShareMiddleActivity.kt", i = {0}, l = {72}, m = "invokeSuspend", n = {"result"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Uri $imageUri;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Uri uri, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$imageUri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SystemShareMiddleActivity.this.new AnonymousClass1(this.$imageUri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.ObjectRef objectRef;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                this.L$0 = objectRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01041(objectRef2, SystemShareMiddleActivity.this, this.$imageUri, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            SystemShareMiddleActivity.this.dismissProgressDialog();
            if (objectRef.element == 0) {
                final SystemShareMiddleActivity systemShareMiddleActivity = SystemShareMiddleActivity.this;
                systemShareMiddleActivity.runOnUiThread(new Runnable() { // from class: com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemShareMiddleActivity.AnonymousClass1.invokeSuspend$lambda$0(systemShareMiddleActivity);
                    }
                });
            } else {
                SystemShareMiddleActivity systemShareMiddleActivity2 = SystemShareMiddleActivity.this;
                Toast.makeText(systemShareMiddleActivity2, systemShareMiddleActivity2.getResources().getString(R.string.save_to_local_success_message), 0).show();
                ExternalAppUtils.openGallery(SystemShareMiddleActivity.this);
                SystemShareMiddleActivity.this.finish();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SystemShareMiddleActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1$1", f = "SystemShareMiddleActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01041 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Uri $imageUri;
            final /* synthetic */ Ref.ObjectRef<File> $result;
            int label;
            final /* synthetic */ SystemShareMiddleActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01041(Ref.ObjectRef<File> objectRef, SystemShareMiddleActivity systemShareMiddleActivity, Uri uri, Continuation<? super C01041> continuation) {
                super(2, continuation);
                this.$result = objectRef;
                this.this$0 = systemShareMiddleActivity;
                this.$imageUri = uri;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01041(this.$result, this.this$0, this.$imageUri, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01041) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:39:0x00a0 A[Catch: IOException -> 0x009c, TRY_LEAVE, TryCatch #11 {IOException -> 0x009c, blocks: (B:35:0x0098, B:39:0x00a0), top: B:74:0x0098 }] */
            /* JADX WARN: Code duplicated, block: B:50:0x00c8 A[Catch: IOException -> 0x00c4, TRY_LEAVE, TryCatch #7 {IOException -> 0x00c4, blocks: (B:46:0x00c0, B:50:0x00c8), top: B:70:0x00c0 }] */
            /* JADX WARN: Code duplicated, block: B:59:0x00e9 A[DONT_INVERT] */
            /* JADX WARN: Code duplicated, block: B:60:0x00eb A[Catch: IOException -> 0x00e7, TRY_LEAVE, TryCatch #5 {IOException -> 0x00e7, blocks: (B:56:0x00e3, B:60:0x00eb), top: B:68:0x00e3 }] */
            /* JADX WARN: Code duplicated, block: B:68:0x00e3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v3, types: [T, java.io.File] */
            /* JADX WARN: Type inference failed for: r2v0 */
            /* JADX WARN: Type inference failed for: r2v1, types: [java.io.InputStream] */
            /* JADX WARN: Type inference failed for: r2v3 */
            /* JADX WARN: Type inference failed for: r2v4 */
            /* JADX WARN: Type inference failed for: r2v5 */
            /* JADX WARN: Type inference failed for: r8v10 */
            /* JADX WARN: Type inference failed for: r8v11 */
            /* JADX WARN: Type inference failed for: r8v12, types: [java.io.InputStream] */
            /* JADX WARN: Type inference failed for: r8v15, types: [java.io.InputStream] */
            /* JADX WARN: Type inference failed for: r8v23, types: [java.io.InputStream, java.lang.Object] */
            /* JADX WARN: Type inference failed for: r8v4, types: [T] */
            /* JADX WARN: Type inference failed for: r8v9 */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                FileOutputStream fileOutputStream;
                Logger logger;
                String str;
                Unit unit;
                StringBuilder sb;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$result.element = Utils.obtainJpgFile(this.this$0);
                ?? OpenInputStream = this.$result.element;
                ?? r2 = 0;
                ?? r3 = 0;
                try {
                    if (OpenInputStream != 0) {
                        try {
                            ContentResolver contentResolver = this.this$0.getContentResolver();
                            Uri uri = this.$imageUri;
                            Intrinsics.checkNotNull(uri);
                            OpenInputStream = contentResolver.openInputStream(uri);
                            try {
                                fileOutputStream = new FileOutputStream(this.$result.element);
                                try {
                                    byte[] bArr = new byte[1024];
                                    while (true) {
                                        Intrinsics.checkNotNull(OpenInputStream);
                                        int i = OpenInputStream.read(bArr);
                                        if (i == -1) {
                                            break;
                                        }
                                        fileOutputStream.write(bArr, 0, i);
                                    }
                                    fileOutputStream.flush();
                                    try {
                                        OpenInputStream.close();
                                        fileOutputStream.close();
                                    } catch (IOException e) {
                                        logger = Logger.INSTANCE;
                                        str = this.this$0.TAG;
                                        e.printStackTrace();
                                        unit = Unit.INSTANCE;
                                        sb = new StringBuilder("onCreate write to file: ");
                                        logger.d(str, sb.append(unit).toString());
                                    }
                                } catch (FileNotFoundException e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    if (OpenInputStream != 0) {
                                        try {
                                            OpenInputStream.close();
                                            if (fileOutputStream != null) {
                                                fileOutputStream.close();
                                            }
                                        } catch (IOException e3) {
                                            logger = Logger.INSTANCE;
                                            str = this.this$0.TAG;
                                            e3.printStackTrace();
                                            unit = Unit.INSTANCE;
                                            sb = new StringBuilder("onCreate write to file: ");
                                            logger.d(str, sb.append(unit).toString());
                                        }
                                    } else if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                } catch (IOException e4) {
                                    e = e4;
                                    e.printStackTrace();
                                    if (OpenInputStream != 0) {
                                        try {
                                            OpenInputStream.close();
                                            if (fileOutputStream != null) {
                                                fileOutputStream.close();
                                            }
                                        } catch (IOException e5) {
                                            logger = Logger.INSTANCE;
                                            str = this.this$0.TAG;
                                            e5.printStackTrace();
                                            unit = Unit.INSTANCE;
                                            sb = new StringBuilder("onCreate write to file: ");
                                            logger.d(str, sb.append(unit).toString());
                                        }
                                    } else if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                }
                            } catch (FileNotFoundException e6) {
                                e = e6;
                                fileOutputStream = null;
                            } catch (IOException e7) {
                                e = e7;
                                fileOutputStream = null;
                            } catch (Throwable th) {
                                th = th;
                                r2 = OpenInputStream;
                                if (r2 != 0) {
                                    try {
                                        r2.close();
                                        if (r2 != 0) {
                                            (r3 == true ? 1 : 0).close();
                                        }
                                    } catch (IOException e8) {
                                        Logger logger2 = Logger.INSTANCE;
                                        String str2 = this.this$0.TAG;
                                        e8.printStackTrace();
                                        logger2.d(str2, "onCreate write to file: " + Unit.INSTANCE);
                                        throw th;
                                    }
                                } else if (r2 != 0) {
                                    (r3 == true ? 1 : 0).close();
                                }
                                throw th;
                            }
                        } catch (FileNotFoundException e9) {
                            e = e9;
                            OpenInputStream = 0;
                            fileOutputStream = null;
                        } catch (IOException e10) {
                            e = e10;
                            OpenInputStream = 0;
                            fileOutputStream = null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (r2 != 0) {
                                r2.close();
                                if (r2 != 0) {
                                    (r3 == true ? 1 : 0).close();
                                }
                            } else if (r2 != 0) {
                                (r3 == true ? 1 : 0).close();
                            }
                            throw th;
                        }
                    }
                    SystemShareMiddleActivity systemShareMiddleActivity = this.this$0;
                    String[] strArr = {String.valueOf(this.$result.element)};
                    final SystemShareMiddleActivity systemShareMiddleActivity2 = this.this$0;
                    MediaScannerConnection.scanFile(systemShareMiddleActivity, strArr, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.ui.SystemShareMiddleActivity$doSaveToGallery$1$1$$ExternalSyntheticLambda0
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public final void onScanCompleted(String str3, Uri uri2) {
                            SystemShareMiddleActivity.AnonymousClass1.C01041.invokeSuspend$lambda$1(systemShareMiddleActivity2, str3, uri2);
                        }
                    });
                    this.this$0.sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                    return Unit.INSTANCE;
                } catch (Throwable th3) {
                    th = th3;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void invokeSuspend$lambda$1(SystemShareMiddleActivity systemShareMiddleActivity, String str, Uri uri) {
                com.miui.notes.base.utils.Logger.INSTANCE.i(systemShareMiddleActivity.TAG, "Scanned " + str + " completely.");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(SystemShareMiddleActivity systemShareMiddleActivity) {
            Toast.makeText(systemShareMiddleActivity, systemShareMiddleActivity.getResources().getString(R.string.save_to_local_failure_message), 0).show();
        }
    }

    private final void doSaveToGallery(Uri imageUri) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(imageUri, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mShowCreatingProgressDialogTask$lambda$1(SystemShareMiddleActivity systemShareMiddleActivity) {
        ProgressDialog progressDialog;
        Logger.INSTANCE.d(systemShareMiddleActivity.TAG, "run init dialog: ");
        ProgressDialog progressDialog2 = new ProgressDialog(systemShareMiddleActivity);
        systemShareMiddleActivity.mDialog = progressDialog2;
        progressDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.ui.SystemShareMiddleActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                SystemShareMiddleActivity.mShowCreatingProgressDialogTask$lambda$1$lambda$0(dialogInterface);
            }
        });
        ProgressDialog progressDialog3 = systemShareMiddleActivity.mDialog;
        if (progressDialog3 != null) {
            progressDialog3.setMessage(systemShareMiddleActivity.getResources().getString(R.string.dlg_info_creating_message));
        }
        ProgressDialog progressDialog4 = systemShareMiddleActivity.mDialog;
        if (progressDialog4 != null) {
            progressDialog4.setIndeterminate(true);
        }
        ProgressDialog progressDialog5 = systemShareMiddleActivity.mDialog;
        if (progressDialog5 != null) {
            progressDialog5.setCanceledOnTouchOutside(false);
        }
        if (systemShareMiddleActivity.isFinishing() || (progressDialog = systemShareMiddleActivity.mDialog) == null) {
            return;
        }
        progressDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissProgressDialog() {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            try {
                Intrinsics.checkNotNull(progressDialog);
                progressDialog.dismiss();
            } catch (Exception e) {
                Log.d(this.TAG, "dismissProgressDialog dismiss exception ");
                e.printStackTrace();
            }
            this.mDialog = null;
        }
    }
}
