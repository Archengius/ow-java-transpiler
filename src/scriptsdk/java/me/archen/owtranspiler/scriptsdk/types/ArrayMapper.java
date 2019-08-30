package me.archen.owtranspiler.scriptsdk.types;

@FunctionalInterface
public interface ArrayMapper<T, E> {
    E map(T element);
}
