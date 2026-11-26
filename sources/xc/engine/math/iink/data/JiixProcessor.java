package xc.engine.math.iink.data;

import java.util.List;
import kotlin.Metadata;
import xc.engine.math.iink.bean.BoundingBox;

/* JADX INFO: compiled from: JiixParser.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H&J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H&J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H&J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H&J \u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/data/JiixProcessor;", "", "parseMathElements", "", "Lxc/engine/math/iink/data/ParsedMathElement;", "jiixData", "", "parseItemElements", "parseAndFormatResult", "Lxc/engine/math/iink/data/ParsedComputationResult;", "resultData", "getSmartAlignedBoundingBox", "Lxc/engine/math/iink/bean/BoundingBox;", "blockId", "getFormulaBoundingBox", "containsEqualSign", "", "getEqualSignStrokeIds", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface JiixProcessor {
    boolean containsEqualSign(String jiixData, String blockId);

    List<String> getEqualSignStrokeIds(String jiixData, String blockId);

    BoundingBox getFormulaBoundingBox(String jiixData, String blockId);

    BoundingBox getSmartAlignedBoundingBox(String jiixData, String blockId);

    ParsedComputationResult parseAndFormatResult(String resultData);

    List<ParsedMathElement> parseItemElements(String jiixData);

    List<ParsedMathElement> parseMathElements(String jiixData);
}
