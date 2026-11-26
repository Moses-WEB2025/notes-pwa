package com.google.protobuf;

/* JADX INFO: loaded from: classes2.dex */
public final class FieldMaskProto {
    private static Descriptors.FileDescriptor descriptor;
    static final Descriptors.Descriptor internal_static_google_protobuf_FieldMask_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_FieldMask_fieldAccessorTable;

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private FieldMaskProto() {
    }

    public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
        registerAllExtensions((ExtensionRegistryLite) extensionRegistry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n google/protobuf/field_mask.proto\u0012\u000fgoogle.protobuf\"\u001a\n\tFieldMask\u0012\r\n\u0005paths\u0018\u0001 \u0003(\tB\u008c\u0001\n\u0013com.google.protobufB\u000eFieldMaskProtoP\u0001Z9google.golang.org/genproto/protobuf/field_mask;field_maskø\u0001\u0001¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3"}, new Descriptors.FileDescriptor[0], new Descriptors.FileDescriptor.InternalDescriptorAssigner() { // from class: com.google.protobuf.FieldMaskProto.1
            @Override // com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor fileDescriptor) {
                Descriptors.FileDescriptor unused = FieldMaskProto.descriptor = fileDescriptor;
                return null;
            }
        });
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        internal_static_google_protobuf_FieldMask_descriptor = descriptor2;
        internal_static_google_protobuf_FieldMask_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"Paths"});
    }
}
