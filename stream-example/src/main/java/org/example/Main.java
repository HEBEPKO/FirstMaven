package org.example;

import org.example.model.*;
import org.example.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.mapping;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
        task11();
//        task12();
        task13();
//        task14();
//        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int numberOfAnimals = 7;
        int zooNumber = 3;
        System.out.println("Seven animals will come to the third zoo: ");
        animals.stream()
                .filter(animal -> animal.getAge() > 10 && animal.getAge() < 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .skip((zooNumber - 1) * numberOfAnimals)
                .limit(numberOfAnimals)
                .forEach(x -> System.out.println("This: " + x));
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .map(animal ->
                        "Female".equalsIgnoreCase(animal.getGender()) ?
                                animal.getBread().toUpperCase() :
                                animal.getBread())
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
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
                .filter(animal -> animal.getAge() > 20 && animal.getAge() < 30)
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
        Optional<Animal> animalAge = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparing(Animal::getAge));
        System.out.println("Самое возрастное животное: \n" + animalAge.get());
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int shortestArrayLength = animals.stream()
                .map(animal -> animal.getBread()
                        .toCharArray())
                .map(chars -> chars.length)
                .min(Integer::compare)
                .orElse(0);
        System.out.println("Длина самого короткого массива: " + shortestArrayLength);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int totalAge = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println("Суммарный возраст всех животных: " + totalAge);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        OptionalDouble totalAgeOfIndonesian = animals.stream()
                .filter(animal -> "Indonesian".equalsIgnoreCase(animal.getOrigin()))
                .mapToInt(Animal::getAge)
                .average();

        System.out.println("Средний возраст животных из Indonesian: " + totalAgeOfIndonesian.getAsDouble());
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> person.getGender().equals("Male"))
                .filter(person ->
                        Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() > 18 &&
                                Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Predicate<House> hospitalPredicate =
                house -> "Hospital".equals(house.getBuildingType());
        Predicate<Person> upTo_18AndMen_65AndWomen_60 = person -> {
            return ((person.getDateOfBirth().atStartOfDay().isBefore(LocalDateTime.now().minusYears(18))) ||
                    (person.getDateOfBirth().atStartOfDay().isBefore(LocalDateTime.now().minusYears(65))
                            && "Female".equalsIgnoreCase(person.getGender())) ||
                            (person.getDateOfBirth().atStartOfDay().isBefore(LocalDateTime.now().minusYears(60))
                            && "Male".equalsIgnoreCase(person.getGender()))
                    );
        };
        Function<House, Stream<? extends Person>> houseStreamToPersonStreamFunction =
                house -> house.getPersonList().stream();

        List<Person> evacuationPriority = Stream.concat(houses.stream()
                                .filter(hospitalPredicate)
                                .flatMap(houseStreamToPersonStreamFunction),
                        Stream.concat(houses.stream()
                                        .filter(hospitalPredicate.negate())
                                        .flatMap(houseStreamToPersonStreamFunction)
                                        .filter(upTo_18AndMen_65AndWomen_60),
                                houses.stream()
                                        .filter(hospitalPredicate.negate())
                                        .flatMap(houseStreamToPersonStreamFunction)
                                        .filter(upTo_18AndMen_65AndWomen_60.negate())))
                .limit(500)
                .toList();
        System.out.println("Первоя очередь эвакуации " + evacuationPriority.size() + " человек:\n" + evacuationPriority + "\n");
    }

//    private static void task14() throws IOException {
//        List<Car> cars = Util.getCars();
//        double transportCostPerTon = 7.14;
//        List<Double> coutCost = new ArrayList<Double>();
//
//        cars.stream()
//                .filter(car -> Boolean.parseBoolean(sortedCarForCountry(car)))
//                .collect(Collectors.groupingBy(car -> sortedCarForCountry(car)))
//                .forEach((country, carList) -> {
//                    int totalMass = carList.stream().mapToInt(Car::getMass).sum();
//                    double transportCosts = totalMass * transportCostPerTon;
//                    coutCost.add(transportCosts);
//                    System.out.println("Транспортные расходы для " + country + ": $" + formatDouble(transportCosts));
//                });
//
//        Optional<Double> x = coutCost.stream().reduce(Double::sum);
//
////        System.out.println("Доход транспортной компании составит: " + formatDouble(x.get()));
//    }

//    private static void task15() throws IOException {
//        List<Flower> flowers = Util.getFlowers();
//        double totalMaintenanceCost;
//        totalMaintenanceCost =
//                flowers.stream()
//                .sorted(Comparator.comparing(Flower::getOrigin).reversed())
//                .sorted(Comparator.comparing(Flower::getPrice))
//                .sorted(Comparator.comparing(Flower::getWaterConsumptionPerDay))
//                .sorted(Comparator.comparing(Flower::getCommonName).reversed())
//                        .dropWhile(flower -> !flower.getCommonName().startsWith("S"))
//                        .takeWhile(flower -> !flower.getCommonName().startsWith("C"))
//                .mapToDouble(flower -> {
//                    if ((flower.isShadePreferred()) &&
//                            (flower.getFlowerVaseMaterial().contains("Glass") ||
//                                    flower.getFlowerVaseMaterial().contains("Aluminum") ||
//                                    flower.getFlowerVaseMaterial().contains("Steel"))){
//                        return flower.getPrice() + flower.getWaterConsumptionPerDay()*5*365*1.39;
//                    }
//                    return 0;
//                })
//                .sum();
//        System.out.println("Общая стоимость обслуживания за 5 лет всех отобраных растений составит: $" + String.format("%.2f",totalMaintenanceCost));
//    }

//    private static String sortedCarForCountry(Car car) {
//        if ("Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor())) {
//            return "Turkmenistan";
//        } else if (car.getMass() <= 1500 ||
//                ("BMW".equals(car.getCarMake()) ||
//                        "Lexus".equals(car.getCarMake()) ||
//                        "Chrysler".equals(car.getCarMake()) ||
//                        "Toyota".equals(car.getCarMake())
//                )) {
//            return "Uzbekistan";
//        } else if (("Black".equals(car.getColor()) &&
//                car.getMass() > 4000) ||
//                ("GMC".equals(car.getCarMake()) ||
//                        "Dodge".equals(car.getCarMake()))) {
//            return "Kazakhstan";
//        } else if (car.getReleaseYear() < 1982 ||
//                ("Civic".equals(car.getCarModel()) &&
//                        "Cherokee".equals(car.getCarModel()))) {
//            return "Kyrgyzstan";
//        } else if (
//                (!("Yellow".equals(car.getColor()) ||
//                "Red".equals(car.getColor()) ||
//                "Green".equals(car.getColor()) ||
//                "Blue".equals(car.getColor()))) ||
//                car.getPrice() > 40000
//        ){
//            return "Russia";
//        } else if (car.getVin().contains("59")) {
//            return "Mongolia";
//        } else {
//            return "V";
//        }
//    }

//    private static String formatDouble(double d){
//        BigDecimal result = new BigDecimal(d);
//        result = result.setScale(2, BigDecimal.ROUND_DOWN);
//        return String.valueOf(result);
//    }
}