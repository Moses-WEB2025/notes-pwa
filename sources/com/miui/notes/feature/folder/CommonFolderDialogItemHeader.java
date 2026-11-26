package com.miui.notes.feature.folder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.basefeature.folder.BaseFolderDialogItem;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.ItemStore;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class CommonFolderDialogItemHeader extends BaseFolderDialogItem {
    private final CallBack callBack;
    protected CompositeDisposable disposable;
    protected IFolme mItemViewFolme;
    protected TextView mNum;
    protected ImageView mTick;
    protected TextView mTitle;
    protected ImageView mTop;

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        return true;
    }

    public static CommonFolderDialogItemHeader newInstance(ViewGroup viewGroup) {
        return new CommonFolderDialogItemHeader(UIUtils.inflateView(viewGroup, R.layout.common_folder_dialog_item));
    }

    public CommonFolderDialogItemHeader(View view) {
        super(view);
        this.mItemViewFolme = null;
        this.disposable = new CompositeDisposable();
        IFolme iFolmeUseAt = Folme.useAt(view);
        this.mItemViewFolme = iFolmeUseAt;
        iFolmeUseAt.touch().setTint(0.03f, 0.0f, 0.0f, 0.0f);
        IFolme iFolme = this.mItemViewFolme;
        if (iFolme != null) {
            iFolme.touch().setScale(0.93f, ITouchStyle.TouchType.DOWN);
            this.mItemViewFolme.touch().handleTouchOf(view, new AnimConfig[0]);
        }
        this.mTick = (ImageView) view.findViewById(R.id.fold_folder_dialog_item_tick);
        this.mTitle = (TextView) view.findViewById(R.id.fold_folder_dialog_item_title);
        this.mNum = (TextView) view.findViewById(R.id.fold_folder_dialog_item_num);
        DisplayUtils.setMiuiRegularTypeFace(this.mTitle);
        DisplayUtils.setMiuiRegularTypeFace(this.mNum);
        this.callBack = new CallBack(new WeakReference(this));
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void bindData(FolderModel folderModel, BindContext bindContext, int i, boolean z) {
        if (this.itemView.getVisibility() != 0) {
            this.itemView.setVisibility(0);
        }
        if (z) {
            DisplayUtils.setMiuiBoldTypeFace(this.mTitle);
            DisplayUtils.setMiuiBoldTypeFace(this.mNum);
            this.mTick.setVisibility(0);
        } else {
            TextView textView = this.mTitle;
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.common_folder_dialog_item_text_color));
            this.mNum.setTextColor(ContextCompat.getColor(this.mTitle.getContext(), R.color.common_folder_dialog_item_num_color));
            DisplayUtils.setMiuiRegularTypeFace(this.mTitle);
            DisplayUtils.setMiuiRegularTypeFace(this.mNum);
            this.mTick.setVisibility(4);
        }
        this.mTitle.setText(FolderModel.getSystemSubject(-4097L));
        this.mTitle.setTextDirection(5);
        Observable.create(new ObservableOnSubscribe<Integer>() { // from class: com.miui.notes.feature.folder.CommonFolderDialogItemHeader.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(Integer.valueOf(ItemStore.queryAllNotesNum()));
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.callBack);
    }

    public static class CallBack implements Observer<Integer> {
        private final CommonFolderDialogItemHeader header;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public CallBack(WeakReference<CommonFolderDialogItemHeader> weakReference) {
            this.header = weakReference.get();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            CommonFolderDialogItemHeader commonFolderDialogItemHeader = this.header;
            if (commonFolderDialogItemHeader != null) {
                commonFolderDialogItemHeader.disposable.add(disposable);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(Integer num) {
            CommonFolderDialogItemHeader commonFolderDialogItemHeader = this.header;
            if (commonFolderDialogItemHeader != null) {
                commonFolderDialogItemHeader.mNum.setText(Utils.obtainLocalInteger(num.intValue()));
            }
        }
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        super.onRecycled();
        CompositeDisposable compositeDisposable = this.disposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
