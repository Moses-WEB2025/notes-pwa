package com.miui.pad.feature.notes.multihandwrite;

import android.content.ContentValues;
import android.content.Intent;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.appcompat.app.Fragment;

/* JADX INFO: compiled from: HandWriteViewManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001!B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J@\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u000fJ@\u0010\u0018\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u000fJ \u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u0018\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u001a\u0010\u001e\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u001a\u0010\u001f\u001a\u0004\u0018\u00010\u00052\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/miui/pad/feature/notes/multihandwrite/HandWriteViewManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "A4_WIDTH", "", "A4_HEIGHT", "SCALE_MIN", "", "SCALE_MAX", "createEntityData", "", "isCreate", "", "handWriteEntity", "Lcom/miui/pad/feature/notes/multihandwrite/MultiHandWriteEntity;", "multiHandWriteView", "Lcom/miui/handwriting/common/MiuiHandWritingView;", "createPageLoadListener", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "pageLoadListener", "useInfiniteCanvas", "createOrLoadExternal", "dirPath", "entFilePath", "saveToDB", "updateThumbnail", "savePageItem", "loadPageItem", "getSavingTitle", "empty", "GenericTaskServiceCallback", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWriteViewManager {
    private static final int A4_HEIGHT = 1156;
    private static final int A4_WIDTH = 755;
    public static final HandWriteViewManager INSTANCE = new HandWriteViewManager();
    private static final float SCALE_MAX = 3.0f;
    private static final float SCALE_MIN = 0.55f;
    private static final String TAG = "HandWriteViewManager";

    private HandWriteViewManager() {
    }

    public static /* synthetic */ void createEntityData$default(HandWriteViewManager handWriteViewManager, boolean z, MultiHandWriteEntity multiHandWriteEntity, MiuiHandWritingView miuiHandWritingView, CommonPageLoadListener commonPageLoadListener, CommonPageLoadListener commonPageLoadListener2, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            z2 = false;
        }
        handWriteViewManager.createEntityData(z, multiHandWriteEntity, miuiHandWritingView, commonPageLoadListener, commonPageLoadListener2, z2);
    }

    public final void createEntityData(boolean isCreate, MultiHandWriteEntity handWriteEntity, MiuiHandWritingView multiHandWriteView, CommonPageLoadListener createPageLoadListener, CommonPageLoadListener pageLoadListener, boolean useInfiniteCanvas) {
        if (handWriteEntity == null) {
            return;
        }
        Log.d(TAG, "createEntityData: Uri:" + handWriteEntity.observeEntDir() + ",isCreate:" + isCreate);
        if (!isCreate && multiHandWriteView != null) {
            multiHandWriteView.setTitle(handWriteEntity.title, 1);
        }
        long sortDate = handWriteEntity.getSortDate();
        if (multiHandWriteView != null) {
            multiHandWriteView.setTitleTime(sortDate);
        }
        if (!isCreate) {
            if (multiHandWriteView != null) {
                multiHandWriteView.load(handWriteEntity.observeEntDir() + "/multi_page_ent.ent", pageLoadListener);
                return;
            }
            return;
        }
        CommonPageBean commonPageBean = new CommonPageBean();
        commonPageBean.width = 755;
        commonPageBean.height = 1156;
        commonPageBean.title = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.handwrite_description);
        commonPageBean.path = handWriteEntity.observeEntDir();
        commonPageBean.radius = BaseNoteApp.INSTANCE.getInstance().getResources().getDimensionPixelSize(R.dimen.creation_paint_round_corner_region_size);
        if (multiHandWriteView != null) {
            multiHandWriteView.create(commonPageBean, createPageLoadListener, useInfiniteCanvas);
        }
    }

    public final void createOrLoadExternal(MiuiHandWritingView multiHandWriteView, String dirPath, String entFilePath, CommonPageLoadListener createPageLoadListener, CommonPageLoadListener pageLoadListener, boolean useInfiniteCanvas) {
        Log.d(TAG, "createOrLoadExternal: entFilePath=" + entFilePath + ", dirPath=" + dirPath);
        Log.d(TAG, "createOrLoadExternal: file exists=" + (entFilePath != null ? new File(entFilePath).exists() : false));
        String str = entFilePath;
        if (str != null && str.length() != 0 && new File(entFilePath).exists()) {
            Log.d(TAG, "createOrLoadExternal: Loading existing file: " + entFilePath);
            if (multiHandWriteView != null) {
                multiHandWriteView.load(entFilePath, pageLoadListener);
                return;
            }
            return;
        }
        Log.d(TAG, "createOrLoadExternal: Creating new file: " + entFilePath);
        CommonPageBean commonPageBean = new CommonPageBean();
        commonPageBean.width = 755;
        commonPageBean.height = 1156;
        commonPageBean.title = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.handwrite_description);
        if (dirPath == null) {
            File externalCacheDir = BaseNoteApp.INSTANCE.getInstance().getExternalCacheDir();
            dirPath = externalCacheDir != null ? externalCacheDir.getAbsolutePath() : null;
        }
        commonPageBean.path = dirPath;
        commonPageBean.radius = BaseNoteApp.INSTANCE.getInstance().getResources().getDimensionPixelSize(R.dimen.creation_paint_round_corner_region_size);
        if (multiHandWriteView != null) {
            multiHandWriteView.create(commonPageBean, createPageLoadListener, useInfiniteCanvas);
        }
    }

    public final boolean saveToDB(boolean updateThumbnail, MultiHandWriteEntity handWriteEntity, MiuiHandWritingView multiHandWriteView) {
        Intrinsics.checkNotNullParameter(handWriteEntity, "handWriteEntity");
        if (updateThumbnail && multiHandWriteView != null) {
            String strSaveThumbnail = multiHandWriteView.saveThumbnail(handWriteEntity.observeEntDir());
            if (!TextUtils.isEmpty(strSaveThumbnail)) {
                handWriteEntity.thumbnail = MultiHandWritePathUtils.getRelativePathByComplete(strSaveThumbnail);
                Log.d(TAG, "saveToDB: handWriteEntity.thumbnail:" + handWriteEntity.thumbnail);
            } else {
                Log.d(TAG, "save multi Thumbnail fail");
            }
        }
        boolean zIsContentEmpty = multiHandWriteView != null ? multiHandWriteView.isContentEmpty() : handWriteEntity.isEmpty;
        handWriteEntity.isEmpty = zIsContentEmpty;
        handWriteEntity.title = multiHandWriteView != null ? getSavingTitle(zIsContentEmpty, multiHandWriteView) : handWriteEntity.title;
        handWriteEntity.modifiedTime = System.currentTimeMillis();
        List<String> allBitmapPaths = multiHandWriteView != null ? multiHandWriteView.getAllBitmapPaths() : null;
        List<String> deleteBitmapPaths = multiHandWriteView != null ? multiHandWriteView.getDeleteBitmapPaths() : null;
        if (allBitmapPaths != null && !allBitmapPaths.isEmpty()) {
            allBitmapPaths.replaceAll(new UnaryOperator() { // from class: com.miui.pad.feature.notes.multihandwrite.HandWriteViewManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MultiHandWritePathUtils.getRelativePathByComplete((String) obj);
                }
            });
        }
        if (deleteBitmapPaths != null && !deleteBitmapPaths.isEmpty()) {
            deleteBitmapPaths.replaceAll(new UnaryOperator() { // from class: com.miui.pad.feature.notes.multihandwrite.HandWriteViewManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MultiHandWritePathUtils.getRelativePathByComplete((String) obj);
                }
            });
        }
        handWriteEntity.snippet = MultiHandWriteDataUtils.resetSnippet(handWriteEntity.snippet, zIsContentEmpty);
        handWriteEntity.images = allBitmapPaths;
        handWriteEntity.deleteImages = deleteBitmapPaths;
        if (updateThumbnail) {
            return MultiHandWriteDataUtils.updateHandWrite(handWriteEntity.id, handWriteEntity.title, handWriteEntity.modifiedTime, handWriteEntity.images, handWriteEntity.deleteImages, handWriteEntity.entName, handWriteEntity.thumbnail, handWriteEntity.snippet, zIsContentEmpty, handWriteEntity.getNoteType(), handWriteEntity.getMindContent());
        }
        return MultiHandWriteDataUtils.updateHandWrite(handWriteEntity.id, handWriteEntity.title, handWriteEntity.modifiedTime, handWriteEntity.images, handWriteEntity.deleteImages, handWriteEntity.entName, handWriteEntity.snippet, zIsContentEmpty, handWriteEntity.getNoteType(), handWriteEntity.getMindContent());
    }

    public final void savePageItem(MiuiHandWritingView multiHandWriteView, MultiHandWriteEntity handWriteEntity) {
        Intrinsics.checkNotNullParameter(multiHandWriteView, "multiHandWriteView");
        int iMax = Math.max(0, (int) Math.min(multiHandWriteView.getCurrentPosition(), multiHandWriteView.getPageCount() - 1));
        float factoryScale = multiHandWriteView.getFactoryScale();
        Log.d(TAG, "savePageItem: currentPageNum:" + iMax + ",currentPageScale:" + factoryScale);
        if (handWriteEntity != null) {
            handWriteEntity.setCurrentPageNum(iMax);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("current_page_num", Integer.valueOf(iMax));
        contentValues.put(Notes.Note.CURRENT_PAGE_SCALE, Float.valueOf(factoryScale));
        MultiHandWriteDataUtils.updateValues(contentValues, handWriteEntity != null ? handWriteEntity.id : -1L);
    }

    public final void loadPageItem(MiuiHandWritingView multiHandWriteView, MultiHandWriteEntity handWriteEntity) {
        int iCoerceAtLeast = RangesKt.coerceAtLeast(0, handWriteEntity != null ? handWriteEntity.getCurrentPageNum() : 0);
        float fCoerceIn = RangesKt.coerceIn(handWriteEntity != null ? handWriteEntity.getCurrentPageScale() : 1.0f, 0.55f, 3.0f);
        Log.d(TAG, "loadPageItem: CurrentPageNum=" + iCoerceAtLeast + ",currentPageScale=" + fCoerceIn);
        if (multiHandWriteView != null) {
            multiHandWriteView.resetScale(true, Float.valueOf(fCoerceIn));
        }
        if (multiHandWriteView != null) {
            multiHandWriteView.jumpPage(iCoerceAtLeast, false);
        }
    }

    private final String getSavingTitle(boolean empty, MiuiHandWritingView multiHandWriteView) {
        String string = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.handwrite_description);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String title = multiHandWriteView.getTitle(1);
        if (empty) {
            return Intrinsics.areEqual(title, string) ? "" : title;
        }
        String str = title;
        return (str == null || str.length() == 0) ? "" : title;
    }

    /* JADX INFO: compiled from: HandWriteViewManager.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B9\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\rH\u0016J\u0018\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\rH\u0016J6\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\r2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0%2\u0006\u0010&\u001a\u00020\rH\u0016J\u0010\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u001aH\u0016J \u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u001aH\u0016J \u0010,\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\rH\u0016J\b\u0010/\u001a\u00020\tH\u0016J\b\u00100\u001a\u00020\tH\u0016J\b\u00101\u001a\u00020\u001eH\u0016J\u0018\u00102\u001a\u00020\t2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0004\u001a\u0004\u0018\u00018\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u00067"}, d2 = {"Lcom/miui/pad/feature/notes/multihandwrite/HandWriteViewManager$GenericTaskServiceCallback;", ExifInterface.GPS_DIRECTION_TRUE, "Lmiuix/appcompat/app/Fragment;", "Lcom/miui/creation/taskservice/ITaskServiceCallback$Stub;", "fragment", "handWriteViewAccessor", "Lkotlin/Function1;", "Lcom/miui/handwriting/common/MiuiHandWritingView;", "finishActivityAccessor", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lmiuix/appcompat/app/Fragment;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "TAG", "", "fragmentWeakReference", "Ljava/lang/ref/WeakReference;", "getFragmentWeakReference", "()Ljava/lang/ref/WeakReference;", "getFragment", "()Lmiuix/appcompat/app/Fragment;", "multiHandWriteView", "getMultiHandWriteView", "()Lcom/miui/handwriting/common/MiuiHandWritingView;", "recognizeAsText", "recognizeFilePath", "textColor", "", "copyAsText", "classificationForAi", "isWriting", "", "recognizeLabel", "showExportPreview", "filePath", "exportType", "title", "filePathList", "", "dirPath", "exportOnProgress", "percentDone", "exportCompleted", "isSuccess", "completedPath", "exportToAI", "exportToPcCompleted", "imagePath", "exportPreviewFail", "backToHome", "exportThumbnailFail", "onGenComplete", "id", "", "values", "Landroid/content/ContentValues;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class GenericTaskServiceCallback<T extends Fragment> extends ITaskServiceCallback.Stub {
        private final String TAG;
        private final Function1<T, Unit> finishActivityAccessor;
        private final WeakReference<T> fragmentWeakReference;
        private final Function1<T, MiuiHandWritingView> handWriteViewAccessor;

        /* JADX WARN: Multi-variable type inference failed */
        public GenericTaskServiceCallback(T fragment, Function1<? super T, ? extends MiuiHandWritingView> handWriteViewAccessor, Function1<? super T, Unit> finishActivityAccessor) {
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(handWriteViewAccessor, "handWriteViewAccessor");
            Intrinsics.checkNotNullParameter(finishActivityAccessor, "finishActivityAccessor");
            this.handWriteViewAccessor = handWriteViewAccessor;
            this.finishActivityAccessor = finishActivityAccessor;
            this.TAG = "GenericTaskServiceCallback";
            this.fragmentWeakReference = new WeakReference<>(fragment);
        }

        public final WeakReference<T> getFragmentWeakReference() {
            return this.fragmentWeakReference;
        }

        private final T getFragment() {
            return this.fragmentWeakReference.get();
        }

        private final MiuiHandWritingView getMultiHandWriteView() {
            Fragment fragment = getFragment();
            if (fragment != null) {
                return this.handWriteViewAccessor.invoke((T) fragment);
            }
            return null;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void recognizeAsText(String recognizeFilePath, int textColor) throws RemoteException {
            Intrinsics.checkNotNullParameter(recognizeFilePath, "recognizeFilePath");
            Log.d(this.TAG, "recognizeAsText: ");
            Fragment fragment = getFragment();
            if (fragment == null || !fragment.isAdded()) {
                return;
            }
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(recognizeFilePath);
            MiuiHandWritingView multiHandWriteView = getMultiHandWriteView();
            if (multiHandWriteView != null) {
                multiHandWriteView.recognizeAsText(selectRecognizeDataFromFile, textColor);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void copyAsText(String recognizeFilePath) throws RemoteException {
            Intrinsics.checkNotNullParameter(recognizeFilePath, "recognizeFilePath");
            Log.d(this.TAG, "copyAsText: ");
            Fragment fragment = getFragment();
            if (fragment == null || !fragment.isAdded()) {
                return;
            }
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(recognizeFilePath);
            MiuiHandWritingView multiHandWriteView = getMultiHandWriteView();
            if (multiHandWriteView != null) {
                multiHandWriteView.copyAsText(selectRecognizeDataFromFile);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void classificationForAi(boolean isWriting, String recognizeLabel) throws RemoteException {
            Intrinsics.checkNotNullParameter(recognizeLabel, "recognizeLabel");
            Log.d(this.TAG, "classificationForAi: ");
            Fragment fragment = getFragment();
            if (fragment == null || !fragment.isAdded() || getFragment() == null) {
                return;
            }
            MiuiHandWritingView multiHandWriteView = getMultiHandWriteView();
            if (multiHandWriteView != null) {
                multiHandWriteView.selectMoveEnable(true);
            }
            MiuiHandWritingView multiHandWriteView2 = getMultiHandWriteView();
            if (multiHandWriteView2 != null) {
                multiHandWriteView2.startAiFormClassification(isWriting, recognizeLabel);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void showExportPreview(String filePath, int exportType, String title, List<String> filePathList, String dirPath) throws RemoteException {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(filePathList, "filePathList");
            Intrinsics.checkNotNullParameter(dirPath, "dirPath");
            Log.d(this.TAG, "showExportPreview: ");
            CreationServiceManager.INSTANCE.clearLastMessage();
            Fragment fragment = getFragment();
            if (fragment != null) {
                fragment.isAdded();
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportOnProgress(int percentDone) throws RemoteException {
            Log.d(this.TAG, "exportOnProgress: ");
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onProgress");
            intent.putExtra("percentDone", percentDone);
            LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).sendBroadcast(intent);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportCompleted(boolean isSuccess, String completedPath, int exportType) throws RemoteException {
            Intrinsics.checkNotNullParameter(completedPath, "completedPath");
            Log.d(this.TAG, "exportCompleted: ");
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onCompleted");
            intent.putExtra("isCompletedSuccess", isSuccess);
            intent.putExtra("completedPath", completedPath);
            intent.putExtra("exportType", exportType);
            CreationServiceManager.INSTANCE.clearLastMessage();
            LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).sendBroadcast(intent);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToAI(boolean isSuccess, String completedPath, int exportType) throws RemoteException {
            Intrinsics.checkNotNullParameter(completedPath, "completedPath");
            Log.d(this.TAG, "exportToAI: ");
            Fragment fragment = getFragment();
            if (fragment != null) {
                fragment.isAdded();
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToPcCompleted(String imagePath) throws RemoteException {
            Intrinsics.checkNotNullParameter(imagePath, "imagePath");
            Log.d(this.TAG, "exportToPcCompleted: ");
            CreationServiceManager.INSTANCE.clearLastMessage();
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportPreviewFail() throws RemoteException {
            Log.d(this.TAG, "exportPreviewFail: ");
            CreationServiceManager.INSTANCE.clearLastMessage();
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void backToHome() throws RemoteException {
            Fragment fragment;
            Log.d(this.TAG, "backToHome: ");
            CreationServiceManager.INSTANCE.clearLastMessage();
            Fragment fragment2 = getFragment();
            if (fragment2 == null || !fragment2.isAdded() || (fragment = getFragment()) == null) {
                return;
            }
            this.finishActivityAccessor.invoke((T) fragment);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public boolean exportThumbnailFail() throws RemoteException {
            Log.d(this.TAG, "exportThumbnailFail: ");
            if (CreationServiceManager.INSTANCE.getRestartNum() < 3) {
                CreationServiceManager.INSTANCE.restartServiceTask();
                return false;
            }
            CreationServiceManager.INSTANCE.setRestartNum(0);
            backToHome();
            return true;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void onGenComplete(long id, ContentValues values) throws RemoteException {
            Intrinsics.checkNotNullParameter(values, "values");
            Log.d(this.TAG, "onGenComplete: ");
            String asString = values.getAsString("recognized_file_names");
            if (asString != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("snippet", values.getAsString(Creation.TEXT_CONTENT));
                contentValues.put("recognized_file_names", asString);
                MultiHandWriteDataUtils.updateValues(contentValues, id);
            }
        }
    }
}
