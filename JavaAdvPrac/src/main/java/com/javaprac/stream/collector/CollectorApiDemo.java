package com.javaprac.stream.collector;

import java.util.List;
import java.util.Optional;

public class CollectorApiDemo {
    public static void main(String[] args) {

        System.out.println("Collector Hello world!");
        List<Integer> listOfInt = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Optional<Integer> first = listOfInt.stream().filter(e -> e % 2 == 0).findFirst();
        //first.isPresent()

    }
}
