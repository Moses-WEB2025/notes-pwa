package com.miui.maml.folme;

import androidx.collection.ArraySet;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes3.dex */
public class ConfigValue {
    public long mDelay;
    public EaseManager.EaseStyle mEase;
    public boolean mHasFromSpeed;
    public float mFromSpeed = Float.MAX_VALUE;
    public ArraySet<String> mRelatedProperty = new ArraySet<>();
    public ArraySet<String> mOnUpdateCallback = new ArraySet<>();
    public ArraySet<String> mOnBeginCallback = new ArraySet<>();
    public ArraySet<String> mOnCompleteCallback = new ArraySet<>();
}
