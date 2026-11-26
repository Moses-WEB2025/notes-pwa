package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: SieveCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b!\n\u0002\u0010\u0015\n\u0002\b\u0006\u001a\u0011\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u0001H\u0080\b\u001a\u0019\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\bH\u0080\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\bH\u0080\b\u001a)\u0010(\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\b2\u0006\u0010'\u001a\u00020\b2\u0006\u0010%\u001a\u00020*H\u0080\b\u001a)\u0010(\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\b2\u0006\u0010'\u001a\u00020\b2\u0006\u0010%\u001a\u00020\u0003H\u0080\b\u001a\u0019\u0010+\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0080\b\u001a\u0019\u0010,\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\bH\u0080\b\u001a\u0011\u0010-\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0001H\u0080\b\u001a\u0019\u0010.\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\bH\u0080\b\u001a\u0019\u0010/\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\bH\u0080\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0016\u0010\n\u001a\u00020\b8\u0000X\u0081T¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\f\"\u0016\u0010\r\u001a\u00020\u00018\u0000X\u0081T¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\f\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0019\u0010\u0014\u001a\u00020\b*\u00020\u00018À\u0002X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u001f\u0010\u0017\u001a\u00020\b*\u00020\u00018À\u0002X\u0081\u0004¢\u0006\f\u0012\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u0016\"\u001f\u0010\u001b\u001a\u00020\b*\u00020\u00018À\u0002X\u0081\u0004¢\u0006\f\u0012\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001d\u0010\u0016\"\u0019\u0010\u001e\u001a\u00020\b*\u00020\u00018À\u0002X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0016\"\u0019\u0010 \u001a\u00020\b*\u00020\u00018À\u0002X\u0080\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0016¨\u00060"}, d2 = {"EmptyNode", "", "EmptyNodes", "", "getEmptyNodes", "()[J", "InvalidMapping", "InvalidMappingLink", "", "MaxSize", "NodeInvalidLink", "getNodeInvalidLink$annotations", "()V", "NodeLinkMask", "getNodeLinkMask$annotations", "NodeLinksMask", "NodeMetaAndNextMask", "NodeMetaAndPreviousMask", "NodeMetaMask", "NodeVisitedBit", "dst", "getDst", "(J)I", "nextNode", "getNextNode$annotations", "(J)V", "getNextNode", "previousNode", "getPreviousNode$annotations", "getPreviousNode", "src", "getSrc", "visited", "getVisited", "clearVisitedBit", "node", "createDstMapping", "mapping", "createLinkToNext", "next", "createLinks", "previous", "", "createMapping", "createSrcMapping", "eraseSrcMapping", "setLinkToNext", "setLinkToPrevious", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SieveCacheKt {
    public static final long EmptyNode = 4611686018427387903L;
    private static final long[] EmptyNodes = new long[0];
    public static final long InvalidMapping = 9223372034707292159L;
    public static final int InvalidMappingLink = Integer.MAX_VALUE;
    private static final long MaxSize = 2147483646;
    public static final int NodeInvalidLink = Integer.MAX_VALUE;
    public static final long NodeLinkMask = 2147483647L;
    public static final long NodeLinksMask = 4611686018427387903L;
    public static final long NodeMetaAndNextMask = -4611686016279904257L;
    public static final long NodeMetaAndPreviousMask = -2147483648L;
    public static final long NodeMetaMask = -4611686018427387904L;
    public static final long NodeVisitedBit = 4611686018427387904L;

    public static final long clearVisitedBit(long j) {
        return j & 4611686018427387903L;
    }

    public static final long createDstMapping(long j, int i) {
        return (j & (-4294967296L)) | ((long) i);
    }

    public static final long createLinkToNext(int i) {
        return (((long) i) & NodeLinkMask) | 4611686016279904256L;
    }

    public static final long createMapping(int i, int i2) {
        return ((long) i2) | (((long) i) << 32);
    }

    public static final long createSrcMapping(long j, int i) {
        return (j & InternalZipConstants.ZIP_64_SIZE_LIMIT) | (((long) i) << 32);
    }

    public static final long eraseSrcMapping(long j) {
        return (j & InternalZipConstants.ZIP_64_SIZE_LIMIT) | (-4294967296L);
    }

    public static final int getDst(long j) {
        return (int) (j & InternalZipConstants.ZIP_64_SIZE_LIMIT);
    }

    public static final int getNextNode(long j) {
        return (int) (j & NodeLinkMask);
    }

    public static /* synthetic */ void getNextNode$annotations(long j) {
    }

    public static /* synthetic */ void getNodeInvalidLink$annotations() {
    }

    public static /* synthetic */ void getNodeLinkMask$annotations() {
    }

    public static final int getPreviousNode(long j) {
        return (int) ((j >> 31) & NodeLinkMask);
    }

    public static /* synthetic */ void getPreviousNode$annotations(long j) {
    }

    public static final int getSrc(long j) {
        return (int) ((j >> 32) & InternalZipConstants.ZIP_64_SIZE_LIMIT);
    }

    public static final int getVisited(long j) {
        return (int) ((j >> 62) & 1);
    }

    public static final long setLinkToNext(long j, int i) {
        return (j & NodeMetaAndPreviousMask) | (((long) i) & NodeLinkMask);
    }

    public static final long setLinkToPrevious(long j, int i) {
        return (j & NodeMetaAndNextMask) | ((((long) i) & NodeLinkMask) << 31);
    }

    public static final long[] getEmptyNodes() {
        return EmptyNodes;
    }

    public static final long createLinks(long j, int i, int i2, long[] mapping) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        return (((j & NodeMetaMask) | ((long) (i == Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) (mapping[i] & InternalZipConstants.ZIP_64_SIZE_LIMIT)))) << 31) | ((long) (i2 != Integer.MAX_VALUE ? (int) (mapping[i2] & InternalZipConstants.ZIP_64_SIZE_LIMIT) : Integer.MAX_VALUE));
    }

    public static final long createLinks(long j, int i, int i2, int[] mapping) {
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        return (((j & NodeMetaMask) | ((long) (i == Integer.MAX_VALUE ? Integer.MAX_VALUE : mapping[i]))) << 31) | ((long) (i2 != Integer.MAX_VALUE ? mapping[i2] : Integer.MAX_VALUE));
    }
}
