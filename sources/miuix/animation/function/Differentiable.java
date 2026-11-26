package miuix.animation.function;

/* JADX INFO: loaded from: classes5.dex */
public interface Differentiable extends Function {
    @Override // miuix.animation.function.Function
    double apply(double d);

    Function derivative();
}
