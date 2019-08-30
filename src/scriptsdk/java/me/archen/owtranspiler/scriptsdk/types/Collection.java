package me.archen.owtranspiler.scriptsdk.types;

import me.archen.owtranspiler.scriptsdk.types.ArrayFilter;
import me.archen.owtranspiler.scriptsdk.types.ArrayMapper;

public interface Collection<T> {

    int length();

    T get(int index);

    boolean contains(T element);

    int indexOf(T element);

    Collection<T> subArray(int startIndex, int length);

    Collection<T> filter(ArrayFilter<T> filter);

    Collection<T> set(int index, T element);

    Collection<T> removeElement(T element);

    T randomElement();

    Collection<T> append(T element);

    Collection<T> randomize();

    Collection<T> sort(ArrayMapper<T, ?> mapper);
}
