package org.example.collection;

import java.util.function.Consumer;

/**
 * A custom iterator interface for traversing elements in a collection.
 * <p>
 * This interface provides methods to iterate over a collection,
 * access elements one by one, and perform actions on the remaining elements.
 *
 * @param <E> the type of elements returned by this iterator
 */
public interface CustomIterator<E> {

    /**
     * Checks if there are more elements to iterate over.
     *
     * @return {@code true} if there are more elements, otherwise {@code false}
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws java.util.NoSuchElementException if no more elements are available
     */
    E next();

    /**
     * Performs the given action for each remaining element in the iteration
     * until all elements have been processed or the action throws an exception.
     *
     * @param action the action to be performed for each element
     * @throws NullPointerException if the specified action is {@code null}
     */
    void forEachRemaining(Consumer<? super E> action);
}
