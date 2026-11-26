package xc.engine.operate.edit;

import android.graphics.Path;
import xc.engine.operate.edit.select.selectobject.ISelectObject;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes6.dex */
public interface IEditListener {
    void onDataCopied(byte[] bArr, int i);

    void onDataCopiedAndPaste(byte[] bArr, int i);

    void onObjectSelect(ISelectObject iSelectObject);

    void onObjectSelect(ISelectObject iSelectObject, boolean z);

    void onOutLineComplete(Path path, short s);

    void onPageSizeChanged(PageSizeConfig pageSizeConfig);

    void onSelectLimit(int i, float f);

    void onSelectedDeletedEnd();

    void onStepChanged(int i, int i2);

    void onStepChanged(int i, int i2, boolean z);
}
