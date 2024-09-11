package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomLinkedListTest {

    private CustomLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
    }

    @Test
    void getLast_shouldReturnLastElement_whenListIsNotEmpty() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.getLast()).isEqualTo(3);
    }

    @Test
    void getLast_shouldThrowNoSuchElementException_whenListIsEmpty() {
        assertThatThrownBy(() -> list.getLast())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("List is empty");
    }

    @Test
    void getFirst_shouldReturnFirstElement_whenListIsNotEmpty() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.getFirst()).isEqualTo(1);
    }

    @Test
    void getFirst_shouldThrowNoSuchElementException_whenListIsEmpty() {
        assertThatThrownBy(() -> list.getFirst())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("List is empty");
    }

    @Test
    void removeLast_shouldRemoveAndReturnLastElement_whenListIsNotEmpty() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.removeLast()).isEqualTo(3);
        assertThat(list.getLast()).isEqualTo(2);
        assertThat(list).hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void removeLast_shouldRemoveLastElement_whenListHasOneElement() {
        list.add(1);

        assertThat(list.removeLast()).isEqualTo(1);
        assertThat(list).isEmpty();
    }

    @Test
    void removeLast_shouldThrowNoSuchElementException_whenListIsEmpty() {
        assertThatThrownBy(() -> list.removeLast())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("List is empty");
    }

    @Test
    void removeFirst_shouldRemoveAndReturnFirstElement_whenListIsNotEmpty() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.removeFirst()).isEqualTo(1);
        assertThat(list.getFirst()).isEqualTo(2);
        assertThat(list).hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void removeFirst_shouldRemoveFirstElement_whenListHasOneElement() {
        list.add(1);

        assertThat(list.removeFirst()).isEqualTo(1);
        assertThat(list).isEmpty();
    }

    @Test
    void removeFirst_shouldThrowNoSuchElementException_whenListIsEmpty() {
        assertThatThrownBy(() -> list.removeFirst())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("List is empty");
    }

    @Test
    void get_shouldReturnElementAtSpecifiedIndex_whenIndexIsValid() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(20);
        assertThat(list.get(2)).isEqualTo(30);
    }


    @Test
    void get_shouldThrowIndexOutOfBoundsException_whenIndexIsNotValid() {
        assertThatThrownBy(() -> list.get(100))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index: 100");
    }

    @Test
    void remove_shouldRemoveElementAtSpecifiedIndex_whenIndexIsValid() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertThat(list.remove(1)).isEqualTo(20);

        assertThat(list).hasSize(2)
                .containsExactly(10, 30);
    }

    @Test
    void remove_shouldThrowIndexOutOfBoundsException_whenIndexIsNotValid() {
        assertThatThrownBy(() -> list.remove(100))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index: 100");
    }

    @Test
    void addLast_shouldAddElementToEmptyList() {
        boolean result = list.addLast(10);

        assertThat(result).isTrue();

        assertThat(list).hasSize(1)
                .containsExactly(10);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(10);
    }

    @Test
    void addLast_shouldAddElementToNonEmptyList() {
        list.addLast(10);
        list.addLast(20);

        assertThat(list).hasSize(2)
                .containsExactly(10, 20);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(20);
    }

    @Test
    void addFirst_shouldAddElementToEmptyList() {
        boolean result = list.addFirst(10);

        assertThat(result).isTrue();

        assertThat(list).hasSize(1)
                .containsExactly(10);


        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(10);
    }

    @Test
    void addFirst_shouldAddElementToNonEmptyList() {
        list.addFirst(10);
        list.addFirst(20);

        assertThat(list).hasSize(2)
                .containsExactly(20, 10);


        assertThat(list.getFirst()).isEqualTo(20);
        assertThat(list.getLast()).isEqualTo(10);
    }

    @Test
    void collectFromStream_shouldReturnEmptyListForEmptyStream() {
        Stream<Integer> emptyStream = Stream.empty();

        CustomLinkedList<Integer> list = CustomLinkedList.collectFromStream(emptyStream);

        assertThat(list).isNotNull().isEmpty();
    }

    @Test
    void collectFromStream_shouldReturnListWithOneElement() {
        Stream<Integer> singleElementStream = Stream.of(10);

        CustomLinkedList<Integer> list = CustomLinkedList.collectFromStream(singleElementStream);

        assertThat(list).isNotNull()
                .hasSize(1)
                .containsExactly(10);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(10);
    }

    @Test
    void collectFromStream_shouldReturnListWithMultipleElements() {
        Stream<Integer> multipleElementStream = Stream.of(10, 20, 30, 40);

        CustomLinkedList<Integer> list = CustomLinkedList.collectFromStream(multipleElementStream);

        assertThat(list).isNotNull()
                .hasSize(4)
                .containsExactly(10, 20, 30, 40);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(40);
    }

    @Test
    void reduceFromStream_shouldReturnEmptyListForEmptyStream() {
        Stream<Integer> emptyStream = Stream.empty();

        CustomLinkedList<Integer> list = CustomLinkedList.reduceFromStream(emptyStream);

        assertThat(list).isNotNull().isEmpty();
    }

    @Test
    void reduceFromStream_shouldReturnListWithOneElement() {
        Stream<Integer> singleElementStream = Stream.of(10);

        CustomLinkedList<Integer> list = CustomLinkedList.reduceFromStream(singleElementStream);

        assertThat(list).isNotNull()
                .hasSize(1)
                .containsExactly(10);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(10);
    }

    @Test
    void reduceFromStream_shouldReturnListWithMultipleElements() {
        Stream<Integer> multipleElementStream = Stream.of(10, 20, 30, 40);

        CustomLinkedList<Integer> list = CustomLinkedList.reduceFromStream(multipleElementStream);

        assertThat(list)
                .isNotNull()
                .hasSize(4)
                .containsExactly(10, 20, 30, 40);

        assertThat(list.getFirst()).isEqualTo(10);
        assertThat(list.getLast()).isEqualTo(40);
    }

    @Test
    void toArray_shouldReturnArrayWithSufficientCapacity() {
        list.add(1);
        list.add(2);
        list.add(3);

        Integer[] array = new Integer[3];

        Integer[] result = list.toArray(array);

        assertThat(result).isSameAs(array).containsExactly(1, 2, 3).hasSize(3);
    }


    @Test
    void toArray_shouldReturnNewArrayWhenInsufficientCapacity() {
        list.add(1);
        list.add(2);
        list.add(3);

        Integer[] array = new Integer[2];

        Integer[] result = list.toArray(array);

        assertThat(result).isNotSameAs(array).containsExactly(1, 2, 3).hasSize(3);
    }

    @Test
    void toArray_shouldReturnEmptyArrayForEmptyList() {
        Integer[] array = new Integer[0];

        Integer[] result = list.toArray(array);

        assertThat(result).isSameAs(array).isEmpty();
    }

    @Test
    void toArray_shouldThrowNullPointerExceptionForNullArray() {
        Integer[] array = null;
        assertThatThrownBy(() -> list.toArray(array))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void toArray_shouldThrowArrayStoreExceptionForIncompatibleArray() {
        list.add(1);
        list.add(2);

        String[] array = new String[2];

        assertThatThrownBy(() -> list.toArray(array))
                .isInstanceOf(ArrayStoreException.class);
    }

    @Test
    void toArray_shouldReturnEmptyArrayWhenListIsEmpty() {
        Object[] result = list.toArray();
        assertThat(result).isEmpty();
    }

    @Test
    void toArray_shouldReturnArrayWithSingleElement() {
        list.add(1);
        Object[] result = list.toArray();
        assertThat(result).containsExactly(1).hasSize(1);
        ;
    }

    @Test
    void toArray_shouldReturnArrayWithMultipleElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        Object[] result = list.toArray();
        assertThat(result).containsExactly(1, 2, 3).hasSize(3);
        ;
    }

    @Test
    void size_shouldReturnZeroForEmptyList() {
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void size_shouldReturnOneAfterAddingOneElement() {
        list.add(10);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void size_shouldReturnCorrectSizeAfterAddingMultipleElements() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    void retainAll_withListContainingSomeElements_shouldRetainMatchingElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> retainList = new ArrayList<>();
        retainList.add(2);
        retainList.add(4);

        boolean modified = list.retainAll(retainList);

        assertThat(modified).isTrue();

        assertThat(list).hasSize(2)
                .containsExactly(2, 4);
    }

    @Test
    void retainAll_withListContainingNoElements_shouldRemoveAllElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> retainList = new ArrayList<>();

        boolean modified = list.retainAll(retainList);

        assertThat(modified).isTrue();

        assertThat(list).isEmpty();
    }

    @Test
    void retainAll_withListContainingAllElements_shouldNotModifyList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> retainList = new ArrayList<>();
        retainList.add(1);
        retainList.add(2);
        retainList.add(3);
        retainList.add(4);

        boolean modified = list.retainAll(retainList);

        assertThat(modified).isFalse();

        assertThat(list).hasSize(4)
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void retainAll_withNullCollection_shouldThrowNullPointerException() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThatThrownBy(() -> list.retainAll(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void removeAll_withElementsInList_shouldRemoveMatchingElements() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        List<Integer> toRemove = List.of(2, 4);

        boolean modified = list.removeAll(toRemove);

        assertThat(modified).isTrue();
        assertThat(list).hasSize(2)
                .containsExactly(1, 3);
    }

    @Test
    void removeAll_withEmptyList_shouldNotModifyList() {
        List<Integer> toRemove = List.of(1, 2, 3);

        boolean modified = list.removeAll(toRemove);

        assertThat(modified).isFalse();
        assertThat(list).isEmpty();
    }

    @Test
    void removeAll_withEmptyCollection_shouldNotModifyList() {
        list.add(1);
        list.add(2);

        List<Integer> toRemove = new ArrayList<>();

        boolean modified = list.removeAll(toRemove);

        assertThat(modified).isFalse();
        assertThat(list).hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void removeAll_withNullCollection_shouldThrowNullPointerException() {
        list.add(1);
        list.add(2);

        assertThatThrownBy(() -> list.removeAll(null))
                .isInstanceOf(NullPointerException.class);
    }

    private CustomLinkedList<String> createCustomLinkedListwithStringElements() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        ;
        list.add("one");
        list.add("two");
        list.add("three");
        return list;
    }

    @Test
    void remove_existingElement_shouldRemoveAndReturnTrue() {
        CustomLinkedList<String> list = createCustomLinkedListwithStringElements();

        boolean result = list.remove("two");

        assertThat(result).isTrue();
        assertThat(list).doesNotContain("two")
                .containsExactly("one", "three");
    }

    @Test
    void remove_nonExistingElement_shouldReturnFalse() {
        CustomLinkedList<String> list = createCustomLinkedListwithStringElements();

        boolean result = list.remove("four");

        assertThat(result).isFalse();
        assertThat(list).containsExactly("one", "two", "three");
    }

    @Test
    void iterator_shouldIterateOverElementsInOrder() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        Iterator<Integer> iterator = list.iterator();

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void iterator_shouldThrowNoSuchElementExceptionWhenNoMoreElements() {
        list.add(1);

        Iterator<Integer> iterator = list.iterator();
        iterator.next();

        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void iterator_shouldWorkOnEmptyList() {
        Iterator<Integer> iterator = list.iterator();

        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void contains_shouldReturnTrueIfElementIsPresent() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.contains(2)).isTrue();
    }

    @Test
    void contains_shouldReturnFalseIfElementIsNotPresent() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.contains(4)).isFalse();
    }

    @Test
    void contains_shouldReturnFalseIfListIsEmpty() {
        assertThat(list.contains(1)).isFalse();
    }

    @Test
    void isEmpty_shouldReturnTrueIfListIsEmpty() {
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_shouldReturnFalseIfListHasElements() {
        list.add(1);
        assertThat(list.isEmpty()).isFalse();
    }

    @Test
    void containsAll_shouldReturnTrueIfAllElementsAreContained() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        List<Integer> elements = List.of(1, 2);

        assertThat(list.containsAll(elements)).isTrue();
    }

    @Test
    void containsAll_shouldReturnFalseIfSomeElementsAreNotContained() {
        list.addLast(1);
        list.addLast(2);

        List<Integer> elements = List.of(1, 3);

        assertThat(list.containsAll(elements)).isFalse();
    }

    @Test
    void containsAll_shouldReturnFalseIfCollectionIsEmpty() {
        list.addLast(1);
        list.addLast(2);

        List<Integer> elements = new ArrayList<>();

        assertThat(list.containsAll(elements)).isTrue();
    }

    @Test
    void containsAll_shouldReturnFalseIfCollectionIsNull() {
        list.add(1);
        list.add(2);

        assertThatThrownBy(() -> list.containsAll(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void containsAll_shouldHandleEmptyListCorrectly() {
        List<Integer> elements = List.of(1, 2);

        assertThat(list.containsAll(elements)).isFalse();
    }

    @Test
    void clear_shouldRemoveAllElements() {
        list.add(1);
        list.add(2);
        list.add(3);

        list.clear();

        assertThat(list).isEmpty();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void clear_shouldHandleEmptyList() {
        list.clear();

        assertThat(list.isEmpty()).isTrue();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void addAll_shouldAddElementsFromCollection() {
        List<Integer> elementsToAdd = List.of(1, 2, 3, 4);

        boolean modified = list.addAll(elementsToAdd);

        assertThat(modified).isTrue();

        assertThat(list).containsExactly(1, 2, 3, 4)
                .hasSize(4);
    }

    @Test
    void addAll_shouldNotModifyListWhenCollectionIsEmpty() {
        List<Integer> emptyCollection = new ArrayList<>();

        boolean modified = list.addAll(emptyCollection);

        assertThat(modified).isFalse();

        assertThat(list).isEmpty();
    }

    @Test
    void addAll_shouldHandleNullCollection() {
        assertThatThrownBy(() -> list.addAll(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Collection cannot be null");
    }

    @Test
    void addAll_shouldAddAllElementsFromCollectionToNonEmptyList() {
        list.addLast(5);
        list.addLast(6);

        List<Integer> elementsToAdd = List.of(7, 8);

        boolean modified = list.addAll(elementsToAdd);

        assertThat(modified).isTrue();

        assertThat(list).containsExactly(5, 6, 7, 8)
                .hasSize(4);
    }

    @Test
    void add_shouldAddElementToEmptyList() {
        addLast_shouldAddElementToEmptyList();
    }

    @Test
    void add_shouldAddElementToNonEmptyList() {
        addLast_shouldAddElementToNonEmptyList();
    }
}
