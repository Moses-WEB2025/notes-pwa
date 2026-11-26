package com.miui.pc.component;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.model.MindEntity;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.thumbnail.Thumbnail;
import com.miui.richeditor.theme.Theme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteListItem extends PcBaseItem {
    private View mFakeImage;
    private ImageView mHandWrite;
    private ImageView mImage;
    private ImageView mMind;
    private ImageView mMindOverview;
    private PcMindThumbnailView mMindThumbnailView;
    private ImageView mVoice;

    public static PcNoteListItem newInstance(ViewGroup viewGroup, boolean z, boolean z2) {
        PcNoteListItem pcNoteListItem = new PcNoteListItem(UIUtils.inflateView(viewGroup, R.layout.pc_note_item_list));
        if (z) {
            pcNoteListItem.setContent(PcCallNoteListContent.newInstance(pcNoteListItem));
            return pcNoteListItem;
        }
        if (z2) {
            pcNoteListItem.setContent(PcMindListContent.newInstance(pcNoteListItem));
            return pcNoteListItem;
        }
        pcNoteListItem.setContent(PcNoteListContent.newInstance(pcNoteListItem));
        return pcNoteListItem;
    }

    protected PcNoteListItem(View view) {
        super(view);
        if (this.mItemViewFolme != null) {
            this.mItemViewFolme.touch().handleTouchOf(view, new AnimConfig[0]);
        }
    }

    @Override // com.miui.pc.component.PcBaseItem
    protected void initNoteGroup() {
        super.initNoteGroup();
        this.mFakeImage = this.mNoteContainer.findViewById(R.id.image_vh);
        this.mImage = (ImageView) this.mNoteContainer.findViewById(R.id.image);
        this.mVoice = (ImageView) this.mNoteContainer.findViewById(R.id.voice);
        this.mMind = (ImageView) this.mNoteContainer.findViewById(R.id.mind_map);
        this.mMindOverview = (ImageView) this.mNoteContainer.findViewById(R.id.mind_outline);
        this.mMindThumbnailView = (PcMindThumbnailView) this.mNoteContainer.findViewById(R.id.mind_thumbnail);
        this.mHandWrite = (ImageView) this.mNoteContainer.findViewById(R.id.hand_write_icon);
    }

    @Override // com.miui.pc.component.PcBaseItem
    public ViewGroup getNoteContentContainer() {
        return this.mNoteContent;
    }

    @Override // com.miui.pc.component.PcBaseItem
    public void bind(ItemCache itemCache, final BindContext bindContext, int i, long j) {
        String str;
        boolean z;
        super.bind(itemCache, bindContext, i, j);
        if (itemCache.getCacheType() != 0) {
            this.mImage.setVisibility(8);
            this.mMindThumbnailView.setVisibility(8);
            this.mMind.setVisibility(8);
            this.mMindOverview.setVisibility(8);
            return;
        }
        this.mImage.setVisibility(0);
        final NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
        this.mBindContext = bindContext;
        this.mVoice.setVisibility(noteCache.getNoteContentType() == 3 ? 0 : 8);
        Theme theme = ResourceManager.getTheme(10);
        this.mAlarm.setImageResource(R.drawable.ic_data_item_clock);
        this.mVoice.setImageResource(R.drawable.ic_data_item_audio);
        this.mStick.setImageResource(R.drawable.ic_data_item_stick);
        this.mMindThumbnailView.setVisibility(8);
        this.mMind.setVisibility(8);
        this.mMindOverview.setVisibility(8);
        this.mHandWrite.setVisibility(8);
        String firstImage = noteCache.getFirstImage();
        if (noteCache.getNoteContentType() == 5) {
            String mindContent = noteCache.getNote().getMindContent();
            if (TextUtils.isEmpty(mindContent)) {
                str = "";
                z = false;
            } else {
                MindEntity mindEntity = (MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class);
                z = mindEntity.isMap;
                str = mindEntity.content;
                if (z) {
                    firstImage = mindEntity.thumbnail;
                }
                this.mMindOverview.setVisibility(!z ? 0 : 8);
                this.mMind.setVisibility(z ? 0 : 8);
                this.mMindThumbnailView.setVisibility(!z ? 0 : 8);
            }
            if (!z) {
                this.mMindThumbnailView.setVisibility(0);
                this.mMindThumbnailView.setStyle(theme);
                this.mMindThumbnailView.removeAllViews();
                if (!TextUtils.isEmpty(str)) {
                    this.mMindThumbnailView.setContent(str, bindContext.getSearchToken());
                }
            }
        } else if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
            this.mHandWrite.setVisibility(0);
        }
        boolean zIsEmpty = TextUtils.isEmpty(firstImage);
        final boolean z2 = !zIsEmpty;
        if (!zIsEmpty) {
            if (TextUtils.isEmpty(this.mLastImageRes) || !this.mLastImageRes.equals(firstImage)) {
                this.mLastImageRes = firstImage;
                this.mImage.setVisibility(0);
                Thumbnail.instance().load(noteCache.getNote()).resize(NoteConfig.LIST_THUMBNAIL_SIZE * 2, NoteConfig.LIST_THUMBNAIL_SIZE * 2).into(this.mImage);
            }
            this.mFakeImage.setVisibility(0);
            this.mMindThumbnailView.setVisibility(8);
        } else {
            this.mLastImageRes = null;
            this.mImage.setImageDrawable(null);
            this.mImage.setVisibility(8);
            this.mFakeImage.setVisibility(8);
        }
        this.mNoteBaseContent.getItemView().post(new Runnable() { // from class: com.miui.pc.component.PcNoteListItem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bind$0(noteCache, bindContext, z2);
            }
        });
        this.mStick.setVisibility(noteCache.getNote().getStickAt() > 0 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$0(NoteCache noteCache, BindContext bindContext, boolean z) {
        this.mNoteBaseContent.bind(noteCache, bindContext, z);
    }
}
