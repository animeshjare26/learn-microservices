package com.animesh.ecommerce.order_service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

 class Employee {

    private int id;
    private String name;
    private int age;
    private int salary;
    private String gender;
    private String department;
    private String city;
    private int yearOfJoining;

    // Constructor
    public Employee(int id, String name, int age, int salary, String gender,
                    String department, String city, int yearOfJoining) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        this.department = department;
        this.city = city;
        this.yearOfJoining = yearOfJoining;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getCity() {
        return city;
    }

    public int getYearOfJoining() {
        return yearOfJoining;
    }

    // toString()
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", city='" + city + '\'' +
                ", yearOfJoining=" + yearOfJoining +
                '}';
    }
}
public class Main {

    public static void main1(String[] args) {

        int arr[] = {1,2,1,2,5,8};

        int min = Arrays.stream(arr).min().getAsInt();
        int max = Arrays.stream(arr).max().getAsInt();

        Set<Integer> set = Arrays.stream(arr).boxed().collect(Collectors.toSet());

        List<Integer> list = IntStream.rangeClosed(min, max)
                .boxed()
                .filter(x -> !set.contains(x))
                .toList();

        System.out.println(list);



        List<Employee> empList=new ArrayList<>();

        empList.add(new Employee(1, "abc", 28, 123, "F", "HR", "Blore", 2020));

        empList.add(new Employee(2, "xyz", 29, 120, "F", "HR", "Hyderabad", 2015));

        empList.add(new Employee(3, "efg", 30, 115, "M", "HR", "Chennai", 2014));

        empList.add(new Employee(4, "def", 32, 125, "F", "HR", "Chennai", 2013));

        empList.add(new Employee(5, "ijk", 22, 150, "F", "IT", "Noida", 2013));

        empList.add(new Employee(6, "mno", 27, 140, "M", "IT", "Gurugram", 2017));

        empList.add(new Employee(7, "uvw", 26, 130, "F", "IT", "Pune", 2016));

        empList.add(new Employee(8, "pqr", 23, 145, "M", "IT", "Trivandrum", 2015));


        Map<String, Long> collect = empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));

        System.out.println(collect);

        System.out.println(empList.stream()
                .filter(x-> x.getGender().equals("F"))
                .min(Comparator.comparing(Employee::getAge)));


        String str = "swiss";

        System.out.println(str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting()))
                .entrySet()
                .stream()
                .filter( x-> x.getValue() > 1)
                .map(x-> x.getKey())
                .findFirst()
                .get());
    }

    public static void main(String[] args) {

        int[] arr = {1,4,6,8};

        System.out.println(IntStream.rangeClosed(arr[0],arr[arr.length-1])
                .filter(i -> Arrays.stream(arr ).noneMatch( x-> x== i ))
                .boxed().toList());

        List<String> stringList = Arrays.asList("Hello", null, "1222", "a", "qq", "");


        Map<Character, Long> collect = stringList
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> !x.isEmpty())
                .flatMap(c -> c.chars().mapToObj(v -> (char) v))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(collect);

    }
}
