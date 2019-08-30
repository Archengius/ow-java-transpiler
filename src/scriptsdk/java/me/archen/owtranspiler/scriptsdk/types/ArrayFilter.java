package me.archen.owtranspiler.scriptsdk.types;

@FunctionalInterface
public interface ArrayFilter<T> {
    boolean test(T element);
}
