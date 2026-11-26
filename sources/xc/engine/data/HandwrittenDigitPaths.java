package xc.engine.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: HandwrittenDigitPaths.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\f\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0004\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u00052\u0006\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\r\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u000e\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u000f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0010\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0011\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0012\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0013\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0014\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0015\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0016\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0017\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002J \u0010\u0018\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u00050\u0005H\u0002¨\u0006\u0019"}, d2 = {"Lxc/engine/data/HandwrittenDigitPaths;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getStrokesForChar", "", "Lkotlin/Pair;", "", "char", "", "letterEStrokes", "digitZeroStrokes", "digitOneStrokes", "digitTwoStrokes", "digitThreeStrokes", "digitFourStrokes", "digitFiveStrokes", "digitSixStrokes", "digitSevenStrokes", "digitEightStrokes", "digitNineStrokes", "decimalPointStrokes", "minusStrokes", "plusStrokes", "percentStrokes", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandwrittenDigitPaths {
    public static final HandwrittenDigitPaths INSTANCE = new HandwrittenDigitPaths();

    private HandwrittenDigitPaths() {
    }

    public final List<List<Pair<Float, Float>>> getStrokesForChar(char c) {
        if (c == '%') {
            return percentStrokes();
        }
        if (c == '+') {
            return plusStrokes();
        }
        if (c == 'e') {
            return letterEStrokes();
        }
        if (c == '-') {
            return minusStrokes();
        }
        if (c != '.') {
            switch (c) {
                case '0':
                    return digitZeroStrokes();
                case '1':
                    return digitOneStrokes();
                case '2':
                    return digitTwoStrokes();
                case '3':
                    return digitThreeStrokes();
                case '4':
                    return digitFourStrokes();
                case '5':
                    return digitFiveStrokes();
                case '6':
                    return digitSixStrokes();
                case '7':
                    return digitSevenStrokes();
                case '8':
                    return digitEightStrokes();
                case '9':
                    return digitNineStrokes();
                default:
                    return CollectionsKt.emptyList();
            }
        }
        return decimalPointStrokes();
    }

    private final List<List<Pair<Float, Float>>> letterEStrokes() {
        Float fValueOf = Float.valueOf(0.7362f);
        Float fValueOf2 = Float.valueOf(0.0192f);
        Float fValueOf3 = Float.valueOf(0.3144f);
        Float fValueOf4 = Float.valueOf(0.0951f);
        Float fValueOf5 = Float.valueOf(0.1711f);
        Float fValueOf6 = Float.valueOf(0.9808f);
        Pair[] pairArr = {new Pair(fValueOf, fValueOf2), new Pair(Float.valueOf(0.6434f), Float.valueOf(0.0475f)), new Pair(Float.valueOf(0.5337f), Float.valueOf(0.0899f)), new Pair(Float.valueOf(0.4578f), Float.valueOf(0.1394f)), new Pair(fValueOf3, Float.valueOf(0.2525f)), new Pair(Float.valueOf(0.2554f), Float.valueOf(0.3162f)), new Pair(Float.valueOf(0.2048f), Float.valueOf(0.3798f)), new Pair(fValueOf4, Float.valueOf(0.5566f)), new Pair(Float.valueOf(0.0867f), Float.valueOf(0.5848f)), new Pair(Float.valueOf(0.0699f), Float.valueOf(0.6272f)), new Pair(Float.valueOf(0.053f), Float.valueOf(0.6697f)), new Pair(Float.valueOf(0.0361f), Float.valueOf(0.705f)), new Pair(Float.valueOf(0.0277f), Float.valueOf(0.7969f)), new Pair(Float.valueOf(0.0445f), Float.valueOf(0.903f)), new Pair(fValueOf4, Float.valueOf(0.9525f)), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf3, fValueOf6), new Pair(Float.valueOf(0.4072f), Float.valueOf(0.9596f)), new Pair(Float.valueOf(0.5253f), Float.valueOf(0.9383f)), new Pair(Float.valueOf(0.6181f), Float.valueOf(0.9101f)), new Pair(fValueOf6, Float.valueOf(0.7545f))};
        Float fValueOf7 = Float.valueOf(0.9133f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) pairArr), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(Float.valueOf(0.2072f), Float.valueOf(0.6396f)), new Pair(Float.valueOf(0.2301f), Float.valueOf(0.6202f)), new Pair(Float.valueOf(0.2976f), Float.valueOf(0.5919f)), new Pair(Float.valueOf(0.4663f), Float.valueOf(0.5283f)), new Pair(Float.valueOf(0.5506f), Float.valueOf(0.4859f)), new Pair(Float.valueOf(0.6518f), Float.valueOf(0.4293f)), new Pair(Float.valueOf(0.8121f), Float.valueOf(0.3091f)), new Pair(Float.valueOf(0.888f), Float.valueOf(0.2243f)), new Pair(fValueOf7, Float.valueOf(0.1818f)), new Pair(Float.valueOf(0.9217f), Float.valueOf(0.1324f)), new Pair(Float.valueOf(0.9302f), Float.valueOf(0.1041f)), new Pair(fValueOf7, Float.valueOf(0.0617f)), new Pair(Float.valueOf(0.8796f), Float.valueOf(0.0334f)), new Pair(Float.valueOf(0.7446f), fValueOf2)})});
    }

    private final List<List<Pair<Float, Float>>> digitZeroStrokes() {
        Float fValueOf = Float.valueOf(0.5f);
        Float fValueOf2 = Float.valueOf(0.965f);
        Float fValueOf3 = Float.valueOf(0.6f);
        Float fValueOf4 = Float.valueOf(0.9518f);
        Float fValueOf5 = Float.valueOf(0.7f);
        Float fValueOf6 = Float.valueOf(0.9254f);
        Float fValueOf7 = Float.valueOf(0.78f);
        Float fValueOf8 = Float.valueOf(0.8902f);
        Float fValueOf9 = Float.valueOf(0.84f);
        Float fValueOf10 = Float.valueOf(0.8374f);
        Float fValueOf11 = Float.valueOf(0.89f);
        Float fValueOf12 = Float.valueOf(0.767f);
        Float fValueOf13 = Float.valueOf(0.92f);
        Float fValueOf14 = Float.valueOf(0.6878f);
        Float fValueOf15 = Float.valueOf(0.93f);
        Float fValueOf16 = Float.valueOf(0.5998f);
        Float fValueOf17 = Float.valueOf(0.5118f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf7, fValueOf8), new Pair(fValueOf9, fValueOf10), new Pair(fValueOf11, fValueOf12), new Pair(fValueOf13, fValueOf14), new Pair(fValueOf15, fValueOf16), new Pair(fValueOf13, fValueOf17), new Pair(fValueOf11, Float.valueOf(0.4238f)), new Pair(Float.valueOf(0.85f), Float.valueOf(0.3446f)), new Pair(fValueOf7, Float.valueOf(0.2742f)), new Pair(fValueOf5, Float.valueOf(0.2302f)), new Pair(fValueOf3, Float.valueOf(0.195f)), new Pair(fValueOf, Float.valueOf(0.1862f)), new Pair(Float.valueOf(0.4f), Float.valueOf(0.195f)), new Pair(Float.valueOf(0.3f), Float.valueOf(0.2302f)), new Pair(Float.valueOf(0.22f), Float.valueOf(0.2742f)), new Pair(Float.valueOf(0.15f), Float.valueOf(0.3446f)), new Pair(Float.valueOf(0.11f), Float.valueOf(0.4238f)), new Pair(Float.valueOf(0.08f), fValueOf17), new Pair(Float.valueOf(0.07f), fValueOf16), new Pair(Float.valueOf(0.08f), fValueOf14), new Pair(Float.valueOf(0.11f), fValueOf12), new Pair(Float.valueOf(0.16f), fValueOf10), new Pair(Float.valueOf(0.22f), fValueOf8), new Pair(Float.valueOf(0.3f), fValueOf6), new Pair(Float.valueOf(0.4f), fValueOf4), new Pair(fValueOf, fValueOf2)}));
    }

    private final List<List<Pair<Float, Float>>> digitOneStrokes() {
        Float fValueOf = Float.valueOf(0.28f);
        Float fValueOf2 = Float.valueOf(0.5f);
        Float fValueOf3 = Float.valueOf(0.12f);
        Pair[] pairArr = {new Pair(fValueOf, Float.valueOf(0.32f)), new Pair(Float.valueOf(0.34f), Float.valueOf(0.25f)), new Pair(Float.valueOf(0.42f), Float.valueOf(0.18f)), new Pair(fValueOf2, fValueOf3)};
        Float fValueOf4 = Float.valueOf(0.51f);
        Float fValueOf5 = Float.valueOf(0.52f);
        Float fValueOf6 = Float.valueOf(0.53f);
        Float fValueOf7 = Float.valueOf(0.48f);
        Float fValueOf8 = Float.valueOf(0.9f);
        Pair[] pairArr2 = {new Pair(fValueOf2, fValueOf3), new Pair(fValueOf4, Float.valueOf(0.2f)), new Pair(fValueOf5, fValueOf), new Pair(fValueOf6, Float.valueOf(0.36f)), new Pair(fValueOf6, Float.valueOf(0.44f)), new Pair(fValueOf5, fValueOf5), new Pair(fValueOf4, Float.valueOf(0.6f)), new Pair(fValueOf2, Float.valueOf(0.68f)), new Pair(fValueOf2, Float.valueOf(0.76f)), new Pair(Float.valueOf(0.49f), Float.valueOf(0.84f)), new Pair(fValueOf7, fValueOf8)};
        Float fValueOf9 = Float.valueOf(0.43f);
        Float fValueOf10 = Float.valueOf(0.91f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) pairArr), CollectionsKt.listOf((Object[]) pairArr2), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(Float.valueOf(0.38f), fValueOf8), new Pair(fValueOf9, fValueOf10), new Pair(fValueOf7, fValueOf10), new Pair(fValueOf6, fValueOf8), new Pair(Float.valueOf(0.58f), Float.valueOf(0.89f))})});
    }

    private final List<List<Pair<Float, Float>>> digitTwoStrokes() {
        Float fValueOf = Float.valueOf(0.2f);
        Float fValueOf2 = Float.valueOf(0.25f);
        Float fValueOf3 = Float.valueOf(0.23f);
        Float fValueOf4 = Float.valueOf(0.19f);
        Float fValueOf5 = Float.valueOf(0.3f);
        Float fValueOf6 = Float.valueOf(0.13f);
        Float fValueOf7 = Float.valueOf(0.4f);
        Float fValueOf8 = Float.valueOf(0.09f);
        Float fValueOf9 = Float.valueOf(0.5f);
        Float fValueOf10 = Float.valueOf(0.82f);
        Float fValueOf11 = Float.valueOf(0.89f);
        Float fValueOf12 = Float.valueOf(0.35f);
        Float fValueOf13 = Float.valueOf(0.7f);
        Float fValueOf14 = Float.valueOf(0.88f);
        Float fValueOf15 = Float.valueOf(0.9f);
        Float fValueOf16 = Float.valueOf(0.91f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf7, fValueOf8), new Pair(fValueOf9, Float.valueOf(0.08f)), new Pair(Float.valueOf(0.62f), fValueOf8), new Pair(Float.valueOf(0.73f), fValueOf6), new Pair(fValueOf10, fValueOf4), new Pair(Float.valueOf(0.87f), Float.valueOf(0.27f)), new Pair(fValueOf11, fValueOf12), new Pair(Float.valueOf(0.86f), Float.valueOf(0.42f)), new Pair(Float.valueOf(0.8f), Float.valueOf(0.48f)), new Pair(fValueOf13, Float.valueOf(0.54f)), new Pair(Float.valueOf(0.6f), Float.valueOf(0.59f)), new Pair(fValueOf9, Float.valueOf(0.64f)), new Pair(fValueOf7, fValueOf13), new Pair(Float.valueOf(0.32f), Float.valueOf(0.76f)), new Pair(fValueOf2, fValueOf10), new Pair(fValueOf, fValueOf14), new Pair(fValueOf2, fValueOf15), new Pair(fValueOf12, fValueOf16), new Pair(Float.valueOf(0.45f), fValueOf16), new Pair(Float.valueOf(0.55f), fValueOf16), new Pair(Float.valueOf(0.65f), fValueOf15), new Pair(Float.valueOf(0.75f), fValueOf11), new Pair(Float.valueOf(0.85f), fValueOf14)}));
    }

    private final List<List<Pair<Float, Float>>> digitThreeStrokes() {
        Float fValueOf = Float.valueOf(0.18f);
        Float fValueOf2 = Float.valueOf(0.23f);
        Float fValueOf3 = Float.valueOf(0.32f);
        Float fValueOf4 = Float.valueOf(0.42f);
        Float fValueOf5 = Float.valueOf(0.08f);
        Float fValueOf6 = Float.valueOf(0.53f);
        Float fValueOf7 = Float.valueOf(0.64f);
        Float fValueOf8 = Float.valueOf(0.74f);
        Float fValueOf9 = Float.valueOf(0.82f);
        Float fValueOf10 = Float.valueOf(0.87f);
        Float fValueOf11 = Float.valueOf(0.89f);
        Float fValueOf12 = Float.valueOf(0.6f);
        Float fValueOf13 = Float.valueOf(0.49f);
        Float fValueOf14 = Float.valueOf(0.57f);
        Float fValueOf15 = Float.valueOf(0.75f);
        Float fValueOf16 = Float.valueOf(0.91f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, Float.valueOf(0.2f)), new Pair(fValueOf2, Float.valueOf(0.14f)), new Pair(fValueOf3, Float.valueOf(0.1f)), new Pair(fValueOf4, fValueOf5), new Pair(fValueOf6, fValueOf5), new Pair(fValueOf7, Float.valueOf(0.09f)), new Pair(fValueOf8, Float.valueOf(0.13f)), new Pair(fValueOf9, Float.valueOf(0.19f)), new Pair(fValueOf10, Float.valueOf(0.26f)), new Pair(fValueOf11, Float.valueOf(0.33f)), new Pair(fValueOf10, Float.valueOf(0.4f)), new Pair(Float.valueOf(0.8f), Float.valueOf(0.45f)), new Pair(Float.valueOf(0.7f), Float.valueOf(0.48f)), new Pair(fValueOf12, fValueOf13), new Pair(Float.valueOf(0.5f), fValueOf13), new Pair(fValueOf14, Float.valueOf(0.51f)), new Pair(Float.valueOf(0.65f), fValueOf6), new Pair(fValueOf15, fValueOf14), new Pair(fValueOf9, Float.valueOf(0.62f)), new Pair(fValueOf10, Float.valueOf(0.68f)), new Pair(fValueOf11, fValueOf15), new Pair(fValueOf10, fValueOf9), new Pair(fValueOf9, fValueOf10), new Pair(fValueOf8, fValueOf16), new Pair(fValueOf7, Float.valueOf(0.93f)), new Pair(fValueOf6, Float.valueOf(0.94f)), new Pair(fValueOf4, Float.valueOf(0.93f)), new Pair(fValueOf3, fValueOf16), new Pair(fValueOf2, fValueOf10), new Pair(fValueOf, fValueOf9)}));
    }

    private final List<List<Pair<Float, Float>>> digitFourStrokes() {
        Float fValueOf = Float.valueOf(0.75f);
        Float fValueOf2 = Float.valueOf(0.08f);
        Float fValueOf3 = Float.valueOf(0.73f);
        Float fValueOf4 = Float.valueOf(0.7f);
        Float fValueOf5 = Float.valueOf(0.2f);
        Float fValueOf6 = Float.valueOf(0.55f);
        Float fValueOf7 = Float.valueOf(0.5f);
        Float fValueOf8 = Float.valueOf(0.48f);
        Float fValueOf9 = Float.valueOf(0.28f);
        Float fValueOf10 = Float.valueOf(0.76f);
        Float fValueOf11 = Float.valueOf(0.17f);
        Float fValueOf12 = Float.valueOf(0.78f);
        Pair[] pairArr = {new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, Float.valueOf(0.14f)), new Pair(fValueOf4, fValueOf5), new Pair(Float.valueOf(0.65f), Float.valueOf(0.27f)), new Pair(Float.valueOf(0.6f), Float.valueOf(0.34f)), new Pair(fValueOf6, Float.valueOf(0.41f)), new Pair(fValueOf7, fValueOf8), new Pair(Float.valueOf(0.43f), fValueOf6), new Pair(Float.valueOf(0.36f), Float.valueOf(0.62f)), new Pair(fValueOf9, Float.valueOf(0.69f)), new Pair(fValueOf5, fValueOf10), new Pair(fValueOf11, fValueOf12)};
        Float fValueOf13 = Float.valueOf(0.1f);
        Float fValueOf14 = Float.valueOf(0.74f);
        Pair[] pairArr2 = {new Pair(fValueOf13, fValueOf14), new Pair(Float.valueOf(0.18f), fValueOf14), new Pair(fValueOf9, fValueOf14), new Pair(Float.valueOf(0.38f), fValueOf14), new Pair(fValueOf8, fValueOf14), new Pair(Float.valueOf(0.58f), fValueOf14), new Pair(Float.valueOf(0.68f), fValueOf14), new Pair(fValueOf12, fValueOf3), new Pair(Float.valueOf(0.88f), fValueOf3), new Pair(Float.valueOf(0.92f), Float.valueOf(0.72f))};
        Float fValueOf15 = Float.valueOf(0.77f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) pairArr), CollectionsKt.listOf((Object[]) pairArr2), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf10, fValueOf2), new Pair(fValueOf15, Float.valueOf(0.18f)), new Pair(fValueOf15, fValueOf9), new Pair(fValueOf15, Float.valueOf(0.38f)), new Pair(fValueOf15, fValueOf8), new Pair(fValueOf15, Float.valueOf(0.58f)), new Pair(fValueOf10, Float.valueOf(0.68f)), new Pair(fValueOf10, fValueOf12), new Pair(fValueOf, Float.valueOf(0.88f)), new Pair(fValueOf14, Float.valueOf(0.93f))})});
    }

    private final List<List<Pair<Float, Float>>> digitFiveStrokes() {
        Float fValueOf = Float.valueOf(0.82f);
        Float fValueOf2 = Float.valueOf(0.13f);
        Float fValueOf3 = Float.valueOf(0.72f);
        Float fValueOf4 = Float.valueOf(0.12f);
        Float fValueOf5 = Float.valueOf(0.52f);
        Float fValueOf6 = Float.valueOf(0.11f);
        Float fValueOf7 = Float.valueOf(0.22f);
        Float fValueOf8 = Float.valueOf(0.18f);
        Float fValueOf9 = Float.valueOf(0.14f);
        Pair[] pairArr = {new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(Float.valueOf(0.62f), fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(Float.valueOf(0.42f), fValueOf6), new Pair(Float.valueOf(0.32f), fValueOf4), new Pair(fValueOf7, fValueOf2), new Pair(fValueOf8, fValueOf9)};
        Float fValueOf10 = Float.valueOf(0.2f);
        Float fValueOf11 = Float.valueOf(0.45f);
        Float fValueOf12 = Float.valueOf(0.25f);
        Float fValueOf13 = Float.valueOf(0.48f);
        Float fValueOf14 = Float.valueOf(0.35f);
        Float fValueOf15 = Float.valueOf(0.55f);
        Float fValueOf16 = Float.valueOf(0.65f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) pairArr), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf7, fValueOf9), new Pair(Float.valueOf(0.21f), fValueOf7), new Pair(fValueOf10, Float.valueOf(0.3f)), new Pair(fValueOf10, Float.valueOf(0.38f)), new Pair(fValueOf7, fValueOf11), new Pair(fValueOf12, fValueOf13), new Pair(fValueOf14, Float.valueOf(0.49f)), new Pair(fValueOf11, fValueOf13), new Pair(fValueOf15, fValueOf13), new Pair(fValueOf16, Float.valueOf(0.5f)), new Pair(Float.valueOf(0.75f), Float.valueOf(0.54f)), new Pair(Float.valueOf(0.83f), Float.valueOf(0.6f)), new Pair(Float.valueOf(0.88f), Float.valueOf(0.67f)), new Pair(Float.valueOf(0.89f), Float.valueOf(0.75f)), new Pair(Float.valueOf(0.87f), fValueOf), new Pair(fValueOf, Float.valueOf(0.87f)), new Pair(Float.valueOf(0.74f), Float.valueOf(0.91f)), new Pair(fValueOf16, Float.valueOf(0.93f)), new Pair(fValueOf15, Float.valueOf(0.94f)), new Pair(fValueOf11, Float.valueOf(0.94f)), new Pair(fValueOf14, Float.valueOf(0.93f)), new Pair(fValueOf12, Float.valueOf(0.9f)), new Pair(Float.valueOf(0.17f), Float.valueOf(0.85f)), new Pair(fValueOf4, Float.valueOf(0.78f)), new Pair(Float.valueOf(0.1f), Float.valueOf(0.7f))})});
    }

    private final List<List<Pair<Float, Float>>> digitSixStrokes() {
        Float fValueOf = Float.valueOf(0.75f);
        Float fValueOf2 = Float.valueOf(0.13f);
        Float fValueOf3 = Float.valueOf(0.65f);
        Float fValueOf4 = Float.valueOf(0.11f);
        Float fValueOf5 = Float.valueOf(0.55f);
        Float fValueOf6 = Float.valueOf(0.1f);
        Float fValueOf7 = Float.valueOf(0.25f);
        Float fValueOf8 = Float.valueOf(0.19f);
        Float fValueOf9 = Float.valueOf(0.18f);
        Float fValueOf10 = Float.valueOf(0.34f);
        Float fValueOf11 = Float.valueOf(0.43f);
        Float fValueOf12 = Float.valueOf(0.61f);
        Float fValueOf13 = Float.valueOf(0.7f);
        Float fValueOf14 = Float.valueOf(0.84f);
        Float fValueOf15 = Float.valueOf(0.89f);
        Float fValueOf16 = Float.valueOf(0.4f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(Float.valueOf(0.45f), fValueOf4), new Pair(Float.valueOf(0.35f), Float.valueOf(0.14f)), new Pair(fValueOf7, fValueOf8), new Pair(fValueOf9, Float.valueOf(0.26f)), new Pair(fValueOf2, fValueOf10), new Pair(fValueOf6, fValueOf11), new Pair(Float.valueOf(0.09f), Float.valueOf(0.52f)), new Pair(fValueOf6, fValueOf12), new Pair(fValueOf2, fValueOf13), new Pair(fValueOf9, Float.valueOf(0.78f)), new Pair(fValueOf7, fValueOf14), new Pair(fValueOf10, fValueOf15), new Pair(Float.valueOf(0.44f), Float.valueOf(0.92f)), new Pair(fValueOf5, Float.valueOf(0.93f)), new Pair(Float.valueOf(0.66f), Float.valueOf(0.92f)), new Pair(Float.valueOf(0.76f), Float.valueOf(0.88f)), new Pair(fValueOf14, Float.valueOf(0.82f)), new Pair(fValueOf15, Float.valueOf(0.74f)), new Pair(Float.valueOf(0.91f), fValueOf3), new Pair(Float.valueOf(0.9f), Float.valueOf(0.56f)), new Pair(Float.valueOf(0.86f), Float.valueOf(0.48f)), new Pair(Float.valueOf(0.8f), fValueOf11), new Pair(fValueOf13, fValueOf16), new Pair(Float.valueOf(0.6f), Float.valueOf(0.39f)), new Pair(Float.valueOf(0.5f), fValueOf16), new Pair(fValueOf16, Float.valueOf(0.42f)), new Pair(Float.valueOf(0.3f), Float.valueOf(0.47f)), new Pair(Float.valueOf(0.22f), Float.valueOf(0.54f)), new Pair(fValueOf8, fValueOf12), new Pair(Float.valueOf(0.2f), Float.valueOf(0.67f))}));
    }

    private final List<List<Pair<Float, Float>>> digitSevenStrokes() {
        Float fValueOf = Float.valueOf(0.17f);
        Float fValueOf2 = Float.valueOf(0.15f);
        Float fValueOf3 = Float.valueOf(0.27f);
        Float fValueOf4 = Float.valueOf(0.13f);
        Float fValueOf5 = Float.valueOf(0.37f);
        Float fValueOf6 = Float.valueOf(0.12f);
        Float fValueOf7 = Float.valueOf(0.47f);
        Float fValueOf8 = Float.valueOf(0.11f);
        Float fValueOf9 = Float.valueOf(0.57f);
        Float fValueOf10 = Float.valueOf(0.77f);
        Float fValueOf11 = Float.valueOf(0.85f);
        Pair[] pairArr = {new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf7, fValueOf8), new Pair(fValueOf9, fValueOf8), new Pair(Float.valueOf(0.67f), fValueOf6), new Pair(fValueOf10, fValueOf4), new Pair(fValueOf11, fValueOf2)};
        Float fValueOf12 = Float.valueOf(0.83f);
        Float fValueOf13 = Float.valueOf(0.7f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) pairArr), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf11, fValueOf2), new Pair(fValueOf12, Float.valueOf(0.25f)), new Pair(Float.valueOf(0.8f), Float.valueOf(0.35f)), new Pair(Float.valueOf(0.76f), Float.valueOf(0.45f)), new Pair(fValueOf13, Float.valueOf(0.55f)), new Pair(Float.valueOf(0.64f), Float.valueOf(0.63f)), new Pair(fValueOf9, fValueOf13), new Pair(Float.valueOf(0.5f), fValueOf10), new Pair(Float.valueOf(0.43f), fValueOf12), new Pair(Float.valueOf(0.36f), Float.valueOf(0.89f)), new Pair(Float.valueOf(0.3f), Float.valueOf(0.94f)), new Pair(Float.valueOf(0.26f), Float.valueOf(0.96f))})});
    }

    private final List<List<Pair<Float, Float>>> digitEightStrokes() {
        Float fValueOf = Float.valueOf(0.5f);
        Float fValueOf2 = Float.valueOf(0.4f);
        Float fValueOf3 = Float.valueOf(0.47f);
        Float fValueOf4 = Float.valueOf(0.3f);
        Float fValueOf5 = Float.valueOf(0.42f);
        Float fValueOf6 = Float.valueOf(0.22f);
        Float fValueOf7 = Float.valueOf(0.35f);
        Float fValueOf8 = Float.valueOf(0.17f);
        Float fValueOf9 = Float.valueOf(0.27f);
        Float fValueOf10 = Float.valueOf(0.15f);
        Float fValueOf11 = Float.valueOf(0.2f);
        Float fValueOf12 = Float.valueOf(0.14f);
        Float fValueOf13 = Float.valueOf(0.6f);
        Float fValueOf14 = Float.valueOf(0.7f);
        Float fValueOf15 = Float.valueOf(0.83f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf), new Pair(fValueOf2, fValueOf3), new Pair(fValueOf4, fValueOf5), new Pair(fValueOf6, fValueOf7), new Pair(fValueOf8, fValueOf9), new Pair(fValueOf10, fValueOf11), new Pair(fValueOf8, fValueOf12), new Pair(Float.valueOf(0.23f), Float.valueOf(0.1f)), new Pair(fValueOf4, Float.valueOf(0.07f)), new Pair(fValueOf2, Float.valueOf(0.05f)), new Pair(fValueOf, Float.valueOf(0.04f)), new Pair(fValueOf13, Float.valueOf(0.05f)), new Pair(fValueOf14, Float.valueOf(0.07f)), new Pair(Float.valueOf(0.77f), Float.valueOf(0.1f)), new Pair(fValueOf15, fValueOf12), new Pair(Float.valueOf(0.85f), fValueOf11), new Pair(fValueOf15, fValueOf9), new Pair(Float.valueOf(0.78f), fValueOf7), new Pair(fValueOf14, fValueOf5), new Pair(fValueOf13, fValueOf3), new Pair(fValueOf, fValueOf), new Pair(fValueOf13, Float.valueOf(0.53f)), new Pair(fValueOf14, Float.valueOf(0.58f)), new Pair(Float.valueOf(0.78f), Float.valueOf(0.65f)), new Pair(fValueOf15, Float.valueOf(0.73f)), new Pair(Float.valueOf(0.85f), Float.valueOf(0.8f)), new Pair(fValueOf15, Float.valueOf(0.86f)), new Pair(Float.valueOf(0.77f), Float.valueOf(0.9f)), new Pair(fValueOf14, Float.valueOf(0.93f)), new Pair(fValueOf13, Float.valueOf(0.95f)), new Pair(fValueOf, Float.valueOf(0.96f)), new Pair(fValueOf2, Float.valueOf(0.95f)), new Pair(fValueOf4, Float.valueOf(0.93f)), new Pair(Float.valueOf(0.23f), Float.valueOf(0.9f)), new Pair(fValueOf8, Float.valueOf(0.86f)), new Pair(fValueOf10, Float.valueOf(0.8f)), new Pair(fValueOf8, Float.valueOf(0.73f)), new Pair(fValueOf6, Float.valueOf(0.65f)), new Pair(fValueOf4, Float.valueOf(0.58f)), new Pair(fValueOf2, Float.valueOf(0.53f)), new Pair(fValueOf, fValueOf)}));
    }

    private final List<List<Pair<Float, Float>>> digitNineStrokes() {
        Float fValueOf = Float.valueOf(0.35f);
        Float fValueOf2 = Float.valueOf(0.88f);
        Float fValueOf3 = Float.valueOf(0.45f);
        Float fValueOf4 = Float.valueOf(0.9f);
        Float fValueOf5 = Float.valueOf(0.55f);
        Float fValueOf6 = Float.valueOf(0.91f);
        Float fValueOf7 = Float.valueOf(0.65f);
        Float fValueOf8 = Float.valueOf(0.75f);
        Float fValueOf9 = Float.valueOf(0.83f);
        Float fValueOf10 = Float.valueOf(0.76f);
        Float fValueOf11 = Float.valueOf(0.67f);
        Float fValueOf12 = Float.valueOf(0.92f);
        Float fValueOf13 = Float.valueOf(0.57f);
        Float fValueOf14 = Float.valueOf(0.47f);
        Float fValueOf15 = Float.valueOf(0.37f);
        Float fValueOf16 = Float.valueOf(0.16f);
        Float fValueOf17 = Float.valueOf(0.56f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(Float.valueOf(0.25f), Float.valueOf(0.85f)), new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf7, fValueOf4), new Pair(fValueOf8, fValueOf2), new Pair(fValueOf9, fValueOf9), new Pair(fValueOf2, fValueOf10), new Pair(fValueOf6, fValueOf11), new Pair(fValueOf12, fValueOf13), new Pair(fValueOf6, fValueOf14), new Pair(fValueOf2, fValueOf15), new Pair(fValueOf9, Float.valueOf(0.28f)), new Pair(fValueOf10, Float.valueOf(0.21f)), new Pair(fValueOf11, fValueOf16), new Pair(fValueOf13, Float.valueOf(0.13f)), new Pair(fValueOf14, Float.valueOf(0.11f)), new Pair(fValueOf15, Float.valueOf(0.12f)), new Pair(Float.valueOf(0.27f), Float.valueOf(0.15f)), new Pair(Float.valueOf(0.19f), Float.valueOf(0.2f)), new Pair(Float.valueOf(0.14f), Float.valueOf(0.27f)), new Pair(Float.valueOf(0.11f), fValueOf), new Pair(Float.valueOf(0.1f), Float.valueOf(0.43f)), new Pair(Float.valueOf(0.12f), Float.valueOf(0.5f)), new Pair(fValueOf16, fValueOf17), new Pair(Float.valueOf(0.24f), Float.valueOf(0.59f)), new Pair(fValueOf, Float.valueOf(0.6f)), new Pair(Float.valueOf(0.46f), Float.valueOf(0.59f)), new Pair(fValueOf17, fValueOf17), new Pair(fValueOf7, Float.valueOf(0.51f)), new Pair(Float.valueOf(0.72f), fValueOf3), new Pair(fValueOf8, Float.valueOf(0.38f)), new Pair(fValueOf10, Float.valueOf(0.32f)), new Pair(Float.valueOf(0.77f), Float.valueOf(0.26f))}));
    }

    private final List<List<Pair<Float, Float>>> decimalPointStrokes() {
        Float fValueOf = Float.valueOf(0.49f);
        Float fValueOf2 = Float.valueOf(0.495f);
        Float fValueOf3 = Float.valueOf(0.5f);
        Float fValueOf4 = Float.valueOf(0.505f);
        Float fValueOf5 = Float.valueOf(0.51f);
        Float fValueOf6 = Float.valueOf(0.485f);
        Float fValueOf7 = Float.valueOf(0.515f);
        Float fValueOf8 = Float.valueOf(0.52f);
        Float fValueOf9 = Float.valueOf(0.48f);
        Float fValueOf10 = Float.valueOf(0.525f);
        Float fValueOf11 = Float.valueOf(0.475f);
        Float fValueOf12 = Float.valueOf(0.53f);
        Float fValueOf13 = Float.valueOf(0.47f);
        Float fValueOf14 = Float.valueOf(0.535f);
        Float fValueOf15 = Float.valueOf(0.54f);
        Float fValueOf16 = Float.valueOf(0.94f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, Float.valueOf(0.86f)), new Pair(fValueOf2, Float.valueOf(0.862f)), new Pair(fValueOf3, Float.valueOf(0.864f)), new Pair(fValueOf4, Float.valueOf(0.866f)), new Pair(fValueOf5, Float.valueOf(0.868f)), new Pair(fValueOf6, Float.valueOf(0.87f)), new Pair(fValueOf, Float.valueOf(0.872f)), new Pair(fValueOf2, Float.valueOf(0.874f)), new Pair(fValueOf3, Float.valueOf(0.876f)), new Pair(fValueOf4, Float.valueOf(0.878f)), new Pair(fValueOf5, Float.valueOf(0.88f)), new Pair(fValueOf7, Float.valueOf(0.882f)), new Pair(fValueOf8, Float.valueOf(0.884f)), new Pair(fValueOf9, Float.valueOf(0.88f)), new Pair(fValueOf6, Float.valueOf(0.882f)), new Pair(fValueOf, Float.valueOf(0.884f)), new Pair(fValueOf2, Float.valueOf(0.886f)), new Pair(fValueOf3, Float.valueOf(0.888f)), new Pair(fValueOf4, Float.valueOf(0.89f)), new Pair(fValueOf5, Float.valueOf(0.892f)), new Pair(fValueOf7, Float.valueOf(0.894f)), new Pair(fValueOf8, Float.valueOf(0.896f)), new Pair(fValueOf10, Float.valueOf(0.898f)), new Pair(fValueOf11, Float.valueOf(0.89f)), new Pair(fValueOf9, Float.valueOf(0.892f)), new Pair(fValueOf6, Float.valueOf(0.894f)), new Pair(fValueOf, Float.valueOf(0.896f)), new Pair(fValueOf2, Float.valueOf(0.898f)), new Pair(fValueOf3, Float.valueOf(0.9f)), new Pair(fValueOf4, Float.valueOf(0.902f)), new Pair(fValueOf5, Float.valueOf(0.904f)), new Pair(fValueOf7, Float.valueOf(0.906f)), new Pair(fValueOf8, Float.valueOf(0.908f)), new Pair(fValueOf10, Float.valueOf(0.91f)), new Pair(fValueOf12, Float.valueOf(0.912f)), new Pair(fValueOf13, Float.valueOf(0.9f)), new Pair(fValueOf11, Float.valueOf(0.902f)), new Pair(fValueOf9, Float.valueOf(0.904f)), new Pair(fValueOf6, Float.valueOf(0.906f)), new Pair(fValueOf, Float.valueOf(0.908f)), new Pair(fValueOf2, Float.valueOf(0.91f)), new Pair(fValueOf3, Float.valueOf(0.912f)), new Pair(fValueOf4, Float.valueOf(0.914f)), new Pair(fValueOf5, Float.valueOf(0.916f)), new Pair(fValueOf7, Float.valueOf(0.918f)), new Pair(fValueOf8, Float.valueOf(0.92f)), new Pair(fValueOf10, Float.valueOf(0.922f)), new Pair(fValueOf12, Float.valueOf(0.924f)), new Pair(fValueOf14, Float.valueOf(0.926f)), new Pair(Float.valueOf(0.465f), Float.valueOf(0.91f)), new Pair(fValueOf13, Float.valueOf(0.912f)), new Pair(fValueOf11, Float.valueOf(0.914f)), new Pair(fValueOf9, Float.valueOf(0.916f)), new Pair(fValueOf6, Float.valueOf(0.918f)), new Pair(fValueOf, Float.valueOf(0.92f)), new Pair(fValueOf2, Float.valueOf(0.922f)), new Pair(fValueOf3, Float.valueOf(0.924f)), new Pair(fValueOf4, Float.valueOf(0.926f)), new Pair(fValueOf5, Float.valueOf(0.928f)), new Pair(fValueOf7, Float.valueOf(0.93f)), new Pair(fValueOf8, Float.valueOf(0.932f)), new Pair(fValueOf10, Float.valueOf(0.934f)), new Pair(fValueOf12, Float.valueOf(0.936f)), new Pair(fValueOf14, Float.valueOf(0.938f)), new Pair(fValueOf15, fValueOf16), new Pair(Float.valueOf(0.46f), Float.valueOf(0.92f)), new Pair(Float.valueOf(0.465f), Float.valueOf(0.922f)), new Pair(fValueOf13, Float.valueOf(0.924f)), new Pair(fValueOf11, Float.valueOf(0.926f)), new Pair(fValueOf9, Float.valueOf(0.928f)), new Pair(fValueOf6, Float.valueOf(0.93f)), new Pair(fValueOf, Float.valueOf(0.932f)), new Pair(fValueOf2, Float.valueOf(0.934f)), new Pair(fValueOf3, Float.valueOf(0.936f)), new Pair(fValueOf4, Float.valueOf(0.938f)), new Pair(fValueOf5, fValueOf16), new Pair(fValueOf7, Float.valueOf(0.942f)), new Pair(fValueOf8, Float.valueOf(0.944f)), new Pair(fValueOf10, Float.valueOf(0.946f)), new Pair(fValueOf12, Float.valueOf(0.948f)), new Pair(fValueOf14, Float.valueOf(0.95f)), new Pair(Float.valueOf(0.54f), Float.valueOf(0.952f)), new Pair(Float.valueOf(0.545f), Float.valueOf(0.954f)), new Pair(Float.valueOf(0.465f), Float.valueOf(0.93f)), new Pair(fValueOf13, Float.valueOf(0.932f)), new Pair(fValueOf11, Float.valueOf(0.934f)), new Pair(fValueOf9, Float.valueOf(0.936f)), new Pair(fValueOf6, Float.valueOf(0.938f)), new Pair(fValueOf, fValueOf16), new Pair(fValueOf2, Float.valueOf(0.942f)), new Pair(fValueOf3, Float.valueOf(0.944f)), new Pair(fValueOf4, Float.valueOf(0.946f)), new Pair(fValueOf5, Float.valueOf(0.948f)), new Pair(fValueOf7, Float.valueOf(0.95f)), new Pair(fValueOf8, Float.valueOf(0.952f)), new Pair(fValueOf10, Float.valueOf(0.954f)), new Pair(fValueOf12, Float.valueOf(0.956f)), new Pair(fValueOf14, Float.valueOf(0.958f)), new Pair(Float.valueOf(0.54f), Float.valueOf(0.96f)), new Pair(fValueOf13, fValueOf16), new Pair(fValueOf11, Float.valueOf(0.942f)), new Pair(fValueOf9, Float.valueOf(0.944f)), new Pair(fValueOf6, Float.valueOf(0.946f)), new Pair(fValueOf, Float.valueOf(0.948f)), new Pair(fValueOf2, Float.valueOf(0.95f)), new Pair(fValueOf3, Float.valueOf(0.952f)), new Pair(fValueOf4, Float.valueOf(0.954f)), new Pair(fValueOf5, Float.valueOf(0.956f)), new Pair(fValueOf7, Float.valueOf(0.958f)), new Pair(fValueOf8, Float.valueOf(0.96f)), new Pair(fValueOf10, Float.valueOf(0.962f)), new Pair(fValueOf12, Float.valueOf(0.964f)), new Pair(fValueOf14, Float.valueOf(0.966f)), new Pair(fValueOf11, Float.valueOf(0.95f)), new Pair(fValueOf9, Float.valueOf(0.952f)), new Pair(fValueOf6, Float.valueOf(0.954f)), new Pair(fValueOf, Float.valueOf(0.956f)), new Pair(fValueOf2, Float.valueOf(0.958f)), new Pair(fValueOf3, Float.valueOf(0.96f)), new Pair(fValueOf4, Float.valueOf(0.962f)), new Pair(fValueOf5, Float.valueOf(0.964f)), new Pair(fValueOf7, Float.valueOf(0.966f)), new Pair(fValueOf8, Float.valueOf(0.968f)), new Pair(fValueOf10, Float.valueOf(0.97f)), new Pair(fValueOf12, Float.valueOf(0.972f)), new Pair(fValueOf9, Float.valueOf(0.96f)), new Pair(fValueOf6, Float.valueOf(0.962f)), new Pair(fValueOf, Float.valueOf(0.964f)), new Pair(fValueOf2, Float.valueOf(0.966f)), new Pair(fValueOf3, Float.valueOf(0.968f)), new Pair(fValueOf4, Float.valueOf(0.97f)), new Pair(fValueOf5, Float.valueOf(0.972f)), new Pair(fValueOf7, Float.valueOf(0.974f)), new Pair(fValueOf8, Float.valueOf(0.976f)), new Pair(fValueOf10, Float.valueOf(0.978f)), new Pair(fValueOf6, Float.valueOf(0.97f)), new Pair(fValueOf, Float.valueOf(0.972f)), new Pair(fValueOf2, Float.valueOf(0.974f)), new Pair(fValueOf3, Float.valueOf(0.976f)), new Pair(fValueOf4, Float.valueOf(0.978f)), new Pair(fValueOf5, Float.valueOf(0.98f)), new Pair(fValueOf7, Float.valueOf(0.982f)), new Pair(fValueOf, Float.valueOf(0.98f)), new Pair(fValueOf2, Float.valueOf(0.982f)), new Pair(fValueOf3, Float.valueOf(0.984f)), new Pair(fValueOf4, Float.valueOf(0.986f)), new Pair(fValueOf5, Float.valueOf(0.988f))}));
    }

    private final List<List<Pair<Float, Float>>> minusStrokes() {
        Float fValueOf = Float.valueOf(0.15f);
        Float fValueOf2 = Float.valueOf(0.507f);
        Float fValueOf3 = Float.valueOf(0.27f);
        Float fValueOf4 = Float.valueOf(0.5f);
        Float fValueOf5 = Float.valueOf(0.38f);
        Float fValueOf6 = Float.valueOf(0.495f);
        return CollectionsKt.listOf(CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf4, Float.valueOf(0.492f)), new Pair(Float.valueOf(0.62f), fValueOf6), new Pair(Float.valueOf(0.73f), fValueOf4), new Pair(Float.valueOf(0.85f), fValueOf2)}));
    }

    private final List<List<Pair<Float, Float>>> plusStrokes() {
        Float fValueOf = Float.valueOf(0.15f);
        Float fValueOf2 = Float.valueOf(0.51f);
        Float fValueOf3 = Float.valueOf(0.2f);
        Float fValueOf4 = Float.valueOf(0.5f);
        Float fValueOf5 = Float.valueOf(0.3f);
        Float fValueOf6 = Float.valueOf(0.49f);
        Float fValueOf7 = Float.valueOf(0.4f);
        Float fValueOf8 = Float.valueOf(0.48f);
        Float fValueOf9 = Float.valueOf(0.85f);
        return CollectionsKt.listOf((Object[]) new List[]{CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf4), new Pair(fValueOf5, fValueOf6), new Pair(fValueOf7, fValueOf8), new Pair(fValueOf4, fValueOf8), new Pair(Float.valueOf(0.6f), fValueOf8), new Pair(Float.valueOf(0.7f), fValueOf6), new Pair(Float.valueOf(0.8f), fValueOf4), new Pair(fValueOf9, fValueOf2)}), CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf4, fValueOf), new Pair(fValueOf2, Float.valueOf(0.25f)), new Pair(fValueOf2, Float.valueOf(0.35f)), new Pair(fValueOf4, fValueOf8), new Pair(fValueOf6, Float.valueOf(0.65f)), new Pair(fValueOf8, Float.valueOf(0.75f)), new Pair(Float.valueOf(0.47f), fValueOf9)})});
    }

    private final List<List<Pair<Float, Float>>> percentStrokes() {
        Float fValueOf = Float.valueOf(0.3f);
        Float fValueOf2 = Float.valueOf(0.2f);
        Float fValueOf3 = Float.valueOf(0.25f);
        Float fValueOf4 = Float.valueOf(0.35f);
        List listListOf = CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf, fValueOf2), new Pair(fValueOf3, fValueOf3), new Pair(fValueOf3, fValueOf), new Pair(fValueOf, fValueOf4), new Pair(fValueOf4, fValueOf), new Pair(fValueOf4, fValueOf3), new Pair(fValueOf, fValueOf2)});
        Float fValueOf5 = Float.valueOf(0.7f);
        Float fValueOf6 = Float.valueOf(0.65f);
        Float fValueOf7 = Float.valueOf(0.75f);
        Float fValueOf8 = Float.valueOf(0.8f);
        List listListOf2 = CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf5, fValueOf6), new Pair(fValueOf6, fValueOf5), new Pair(fValueOf6, fValueOf7), new Pair(fValueOf5, fValueOf8), new Pair(fValueOf7, fValueOf7), new Pair(fValueOf7, fValueOf5), new Pair(fValueOf5, fValueOf6)});
        Float fValueOf9 = Float.valueOf(0.4f);
        Float fValueOf10 = Float.valueOf(0.5f);
        Float fValueOf11 = Float.valueOf(0.6f);
        return CollectionsKt.listOf((Object[]) new List[]{listListOf, listListOf2, CollectionsKt.listOf((Object[]) new Pair[]{new Pair(fValueOf2, fValueOf2), new Pair(fValueOf, fValueOf), new Pair(fValueOf9, fValueOf9), new Pair(fValueOf10, fValueOf10), new Pair(fValueOf11, fValueOf11), new Pair(fValueOf5, fValueOf5), new Pair(fValueOf8, fValueOf8)})});
    }
}
