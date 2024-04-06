package org.example;

import org.example.model.*;
import org.example.util.Util;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int numberOfAnimals = 7;
        int zooNumber = 3;
        animals.stream()
                .filter(animal -> animal.getAge() > 10 && animal.getAge() < 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .skip((zooNumber - 1) * numberOfAnimals)
                .limit(numberOfAnimals)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .map(animal -> {
                    if (animal.getGender().equals("Female")) {
                        return animal.getBread().toUpperCase();
                    } else {
                        return animal.getBread().toLowerCase();
                    }
                })
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .filter(animal -> animal.getOrigin().startsWith("A"))
                .map(animal -> animal.getOrigin())
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();
        System.out.println("Общее колличество животных с gender='Female' равно " + count + " ос.");
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean animalHungarian = animals.stream()
                .filter(animal -> animal.getAge()>20&&animal.getAge()<30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        if (animalHungarian) {
            System.out.println("Есть животные из Hungarian");
        } else {
            System.out.println("Совподений не найдено");
        }
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean allMaleOrFemale = animals.stream()
                .allMatch(animal -> animal.getGender().equals("Male") || animal.getGender().equals("Female"));

        if (allMaleOrFemale) {
            System.out.println("Все животные являются либо самцами, либо самками.");
        } else {
            System.out.println("Среди животных есть особи другого пола.");
        }
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean anyOceania = animals.stream()
                .noneMatch(animal -> animal.getOrigin().equals("Oceania"));
        if (anyOceania) {
            System.out.println("Животных из Oceania нет");
        } else {
            System.out.println("Есть животные из Oceania");
        }
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Optional<Animal> animalAge =  animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .sorted(Comparator.comparing(Animal::getAge).reversed())
                .findAny();
        System.out.println("Самое возрастное животное: \n" + animalAge.get());
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
    }

}