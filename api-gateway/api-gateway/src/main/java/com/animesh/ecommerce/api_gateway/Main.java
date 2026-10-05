package com.animesh.ecommerce.api_gateway;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int arr[] = {1, 2, 1, 2, 5,3, 8};

        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();

        List<Integer> list = IntStream.rangeClosed(min, max)
                .filter(i -> Arrays.stream(arr).noneMatch(x -> x == i))
                .boxed().toList();

        System.out.println(list);


        Integer i = Arrays.stream(arr).boxed()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> x.getValue() == 1)
                .map(x -> x.getKey())
                .findFirst()
                .get();


        System.out.println(i);


        System.out.println(Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .skip(1).findFirst().get());

        String sentence = "I LOve India Country";


        String max1 = Arrays.stream(sentence.split(" "))
                .max(Comparator.comparingInt(String::length)).get();

        System.out.println(max1);

        System.out.println(Arrays.stream(sentence.split(" "))
                .sorted(Comparator.comparingInt(String::length).reversed())
                .skip(2).findFirst());



        String input = "swiss";


        System.out.println(input.chars()
                .mapToObj( c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting()))
                .entrySet()
                .stream()
                .filter( x-> x.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst().get());
    }
}
