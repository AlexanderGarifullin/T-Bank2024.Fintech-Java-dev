package org.example.collection;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * A custom implementation of a doubly linked list that implements the {@link Collection} interface.
 *
 * @param <E> the type of elements in this list
 */
public class CustomLinkedList<E> {

    private Node head;
    private Node tail;
    private int size = 0;

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e the element to be appended
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        return addLast(e);
    }

    /**
     * Appends all the elements from the specified collection to the end of this list.
     *
     * @param c the collection of elements to be appended
     * @return {@code true} if the list was modified as a result of the call
     * @throws NullPointerException if the specified collection is {@code null}
     */
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException(nullPointerMsg());
        }
        boolean modified = false;
        for (E element : c) {
            if (addLast(element)) modified = true;
        }
        return modified;
    }

    /**
     * Removes all the elements from this list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns {@code true} if this list contains all the elements in the specified collection.
     *
     * <p>More formally, returns {@code true} if and only if this list contains
     * at least one element {@code e} such that {@code Objects.equals(o, e)} for each element {@code o}
     * in the specified collection.
     *
     * @param c collection to be checked for containment
     * @return {@code true} if this list contains all the elements in the specified collection
     * @throws NullPointerException if the specified collection is {@code null}
     * @throws ClassCastException if any element in the specified collection cannot be compared with this list
     * @throws NullPointerException if any element in the specified collection is {@code null} and this list does not permit {@code null} elements
     */
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            try {
                if (!contains(element)) {
                    return false;
                }
            } catch (ClassCastException | NullPointerException e) {
                throw e;
            }
        }
        return true;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     * @throws NullPointerException if the specified element is {@code null} and the list contains non-nullable elements
     * @throws ClassCastException if the element cannot be compared with this list
     */

    public boolean contains(Object o) {
        if (o == null) {
            for (Node current = head; current != null; current = current.next) {
                if (current.item == null) {
                    return true;
                }
            }
            throw new NullPointerException(nullPointerObjectMsg());
        }

        if (isEmpty()) return false;

        if (!getClassOfElements().isInstance(o)) {
            throw new ClassCastException(classCastMsg(o));
        }

        E element = (E) o;

        for (Node current = head; current != null; current = current.next) {
            if (current.item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list
     */

    public CustomIterator<E> iterator() {
        return new CustomLinkedListIterator();
    }

    private class CustomLinkedListIterator  implements CustomIterator<E> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (hasNext()) {
                action.accept(next());
            }
        }
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(Object o) {
        for (Node current = head; current != null; current = current.next) {
            if (current.item.equals(o)) {
                removeNode(current);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes from this list all of its elements that are contained in the specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list was modified as a result of the call
     * @throws NullPointerException if the specified collection is {@code null}
     */
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection.
     * In other words, removes from this list all of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list was modified as a result of the call
     * @throws NullPointerException if the specified collection is {@code null}
     */
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (Node current = head; current != null; ) {
            Node next = current.next;
            if (!c.contains(current.item)) {
                removeNode(current);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all the elements in this list.
     *
     * @return an array containing all the elements in this list
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node current = head; current != null; current = current.next) {
            array[i++] = current.item;
        }
        return array;
    }

    /**
     * Returns an array containing all the elements in this list; the runtime type of the returned array is that of the specified array.
     * If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.
     *
     * @param a the array into which the elements of this list are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose
     * @param <T> the type of the elements in the array
     * @return an array containing all the elements in this list
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this list
     * @throws NullPointerException if the specified array is {@code null}
     */
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Node current = head; current != null; current = current.next) {
            a[i++] = (T) current.item;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }


    /**
     * Converts a Stream of elements into a CustomLinkedList.
     *
     * @param stream the Stream of elements to be converted
     * @return a CustomLinkedList containing all elements from the Stream
     */
    public static <E> CustomLinkedList<E> reduceFromStream(Stream<E> stream) {
        CustomLinkedList<E> list = new CustomLinkedList<>();

        stream.reduce(list, (accumulator, element) -> {
            accumulator.addLast(element);
            return accumulator;
        }, (left, right) -> left);

        return list;
    }

    /**
     * Converts a Stream of elements into a CustomLinkedList.
     *
     * @param stream the Stream of elements to be converted
     * @return a CustomLinkedList containing all elements from the Stream
     */
    public static <E> CustomLinkedList<E> collectFromStream(Stream<E> stream) {
        return stream.collect(Collector.of(
                CustomLinkedList::new,
                CustomLinkedList::addLast,
                (left, right) -> left,
                Collector.Characteristics.IDENTITY_FINISH
        ));
    }


    /**
     * Prints all elements of the list to the standard output.
     */
    public void printAll() {
        for (Node current = head; current != null; current = current.next) {
            System.out.print(current.item + " ");
        }
        System.out.println();
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param element the element to be inserted at the beginning of this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean addFirst(E element) {
        Node newNode = new Node(element);
        if (head != null) {
            newNode.next = head;
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
        return true;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to be appended
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean addLast(E element) {
        Node newNode = new Node(element);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
        return true;
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public E remove(int index) {
        checkIndex(index);
        Node current = nodeAt(index);
        final E item = current.item;

        removeNode(current);

        return item;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public E get(int index) {
        checkIndex(index);
        final Node current = nodeAt(index);
        return current.item;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the element that was removed from the beginning of this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException(emptyListMsg());
        }
        final E item = head.item;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return item;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the element that was removed from the end of this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException(emptyListMsg());
        }
        final E item = tail.item;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
        return item;
    }

    /**
     * Returns the first element of this list.
     *
     * @return the first element of this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException(emptyListMsg());
        }
        return head.item;
    }

    /**
     * Returns the last element of this list.
     *
     * @return the last element of this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException(emptyListMsg());
        }
        return tail.item;
    }

    /**
     * Removes the specified node from the list.
     *
     * @param node the node to be removed
     */
    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        size--;
    }

    /**
     * Returns the node at the specified position in this list.
     *
     * @param index the index of the node to return
     * @return the node at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    private Node nodeAt(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Checks if the index is out of bounds.
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private String emptyListMsg() {
        return "List is empty";
    }

    private String nullPointerMsg() {
        return "Collection cannot be null";
    }

    private String nullPointerObjectMsg() {
        return "Element cannot be null";
    }

    private String classCastMsg(Object o) {
        return "Incompatible type: " + o.getClass();
    }

    private Class<?> getClassOfElements() {
        if (head != null) {
            return head.item.getClass();
        }
        throw new IllegalStateException(emptyListMsg());
    }


    /**
     * A node in the linked list.
     */
    private class Node {
        E item;
        Node next;
        Node prev;

        Node(E element) {
            this.item = element;
            next = null;
            prev = null;
        }
    }
}
