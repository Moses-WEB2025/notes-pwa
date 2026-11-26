package com.google.common.io;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@interface IgnoreJRERequirement {
}
