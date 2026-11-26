package com.google.protobuf;

/* JADX INFO: loaded from: classes2.dex */
public interface ProtocolMessageEnum extends Internal.EnumLite {
    Descriptors.EnumDescriptor getDescriptorForType();

    @Override // com.google.protobuf.Internal.EnumLite
    int getNumber();

    Descriptors.EnumValueDescriptor getValueDescriptor();
}
