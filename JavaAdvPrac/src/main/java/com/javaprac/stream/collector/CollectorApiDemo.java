package com.javaprac.stream.collector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CollectorApiDemo {
    public static void main(String[] args) {

        System.out.println("Collector Hello world!");
        List<Integer> listOfInt = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Optional<Integer> first = listOfInt.stream().filter(e -> e % 2 == 0).findFirst();
        //first.isPresent()
        Stream<Integer> integerStream = listOfInt.stream().filter(e -> e % 2 == 0);

        var intgerStrm = listOfInt.stream().filter(e -> e % 2 == 0);
        intgerStrm.forEach(System.out::println);

        var count = listOfInt.stream().filter(CollectorApiDemo::isEven).count();
        System.out.println("count=" + count);

    }

     private static boolean isEven(int e){
        return e % 2 == 0;
     }

    private static Integer doubleIt(int e){
        return 3 * 2;
    }
}
