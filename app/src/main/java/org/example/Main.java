package org.example;

import org.example.collection.CustomLinkedList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // Создание и работа с CustomLinkedList
        CustomLinkedList<Integer> list = new CustomLinkedList<>();

        // Добавление элементов
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        // Вывод элементов списка
        System.out.println("Initial list:");
        list.printAll(); // Output: 1 2 3 4

        // Получение элемента по индексу
        System.out.println("Element at index 0: " + list.get(0)); // Output: 1

        // Удаление элемента по индексу
        list.remove(0);
        System.out.println("List after removing element at index 0:");
        list.printAll(); // Output: 2 3 4

        // Проверка наличия элемента
        System.out.println("List contains 1: " + list.contains(1)); // Output: false
        System.out.println("List contains 2: " + list.contains(2)); // Output: true

        // Добавление всех элементов из другой коллекции
        List<Integer> additionalElements = Arrays.asList(5, 6, 7);
        System.out.println("Additional list:");
        printCollection(additionalElements); // Output: 5 6 7

        list.addAll(additionalElements);
        System.out.println("List after adding all elements from additionalElements:");
        list.printAll(); // Output: 2 3 4 5 6 7

        // Преобразование Stream в CustomLinkedList
        List<Integer> elements = Arrays.asList(8, 9, 10);
        Stream<Integer> newStream = elements.stream();
        System.out.println("Initial stream:");
        printCollection(elements); // Output: 8 9 10

        CustomLinkedList<Integer> listFromStream = CustomLinkedList.collectFromStream(newStream);
        System.out.println("List created from stream:");
        printCustomLinkedList(listFromStream); // Output: 8 9 10
    }

    public static <T> void printCustomLinkedList(CustomLinkedList<T> input) {
        var iterator = input.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    public static <T> void printCollection(Collection<T> input) {
        for (T item : input) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
