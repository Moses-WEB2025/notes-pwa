package com.miui.notes.tool;

import android.app.Activity;
import android.content.ClipData;
import android.net.Uri;
import android.view.DragEvent;
import androidx.core.app.ActivityCompat;
import androidx.core.view.DragAndDropPermissionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class ClipDataHelper {

    public interface UriGetCallback {
        void onException(String str);

        void onGetUri(ArrayList<Uri> arrayList);
    }

    public static boolean getImageUrisFromDragEventInFragment(Fragment fragment, DragEvent dragEvent, final UriGetCallback uriGetCallback) {
        String[] strArrFilterMimeTypes;
        boolean z;
        final ArrayList<Uri> arrayList = new ArrayList<>();
        final FragmentActivity activity = fragment.getActivity();
        if (dragEvent.getClipData() == null || dragEvent.getClipData().getDescription() == null || (strArrFilterMimeTypes = dragEvent.getClipData().getDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) == null || strArrFilterMimeTypes.length == 0 || dragEvent.getClipData().getItemCount() == 0) {
            return false;
        }
        DragAndDropPermissionsCompat dragAndDropPermissionsCompatRequestDragAndDropPermissions = ActivityCompat.requestDragAndDropPermissions(activity, dragEvent);
        if (dragEvent.getClipData().getItemAt(0).getUri() != null && "content".equals(dragEvent.getClipData().getItemAt(0).getUri().getScheme())) {
            Logger.INSTANCE.d("ClipDataHelper", "Requesting permissions.");
            if (dragAndDropPermissionsCompatRequestDragAndDropPermissions == null) {
                Logger.INSTANCE.d("ClipDataHelper", "Drop permission request failed.");
                return false;
            }
        }
        if (UIUtils.isAboveAndroidS()) {
            z = false;
            for (int i = 0; i < dragEvent.getClipData().getItemCount(); i++) {
                Uri uri = dragEvent.getClipData().getItemAt(i).getUri();
                if (uri != null && "media".equals(uri.getAuthority()) && "external".equals(uri.getPathSegments().get(0))) {
                    z = true;
                }
                arrayList.add(uri);
            }
        } else {
            z = false;
        }
        if (!z) {
            arrayList.clear();
            for (int i2 = 0; i2 < dragEvent.getClipData().getItemCount(); i2++) {
                Uri uri2 = dragEvent.getClipData().getItemAt(i2).getUri();
                try {
                    activity.grantUriPermission(activity.getPackageName(), uri2, 1);
                    arrayList.add(uri2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            uriGetCallback.onGetUri(arrayList);
        } else {
            PermissionUtils.ActivityAcceptListener activityAcceptListener = new PermissionUtils.ActivityAcceptListener(activity) { // from class: com.miui.notes.tool.ClipDataHelper.1
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    if (getActivity() == null) {
                        uriGetCallback.onGetUri(null);
                    }
                    int size = arrayList.size();
                    Uri[] uriArr = new Uri[size];
                    arrayList.toArray(uriArr);
                    for (int i3 = 0; i3 < size; i3++) {
                        Uri uri3 = uriArr[i3];
                        try {
                            Activity activity2 = activity;
                            activity2.grantUriPermission(activity2.getPackageName(), uri3, 1);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            arrayList.remove(uri3);
                        }
                    }
                    uriGetCallback.onGetUri(arrayList);
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                    uriGetCallback.onGetUri(null);
                }
            };
            if (Utils.isAboveSDK33()) {
                PermissionUtils.requestReadImagesPermissionWithFragment(activity, fragment, activityAcceptListener);
            } else {
                PermissionUtils.requestReadExternalPermissionWithFragment(activity, fragment, activityAcceptListener);
            }
        }
        if (dragAndDropPermissionsCompatRequestDragAndDropPermissions != null) {
            dragAndDropPermissionsCompatRequestDragAndDropPermissions.release();
        }
        return true;
    }

    public static boolean getImageUrisFromClipDataInFragment(Fragment fragment, ClipData clipData, UriGetCallback uriGetCallback) {
        String[] strArrFilterMimeTypes;
        if (clipData.getDescription() == null || (strArrFilterMimeTypes = clipData.getDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) == null || strArrFilterMimeTypes.length <= 0 || clipData.getItemCount() <= 0) {
            return false;
        }
        ArrayList<Uri> arrayList = new ArrayList<>(clipData.getItemCount());
        for (int i = 0; i < clipData.getItemCount(); i++) {
            arrayList.add(clipData.getItemAt(i).getUri());
        }
        uriGetCallback.onGetUri(arrayList);
        return true;
    }
}
