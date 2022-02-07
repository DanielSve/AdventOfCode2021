package aoc3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AoC3 {

    public static List<String> numbers;

    public static void main(String[] args) {
        try{
            numbers = new LinkedList<>(new BufferedReader(
                    new FileReader("resources/input3.txt")).lines().toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        partOne();
        partTwo();
    }

    public static void partOne() {
        List<Integer> mostCommonBits = getmostCommonBits(numbers);
        String mostCommon = getFirstString(mostCommonBits);
        String leastCommon = getSecondString(mostCommonBits);
        System.out.println("Answer 1: " + Integer.parseInt(String.valueOf(mostCommon),2) * Integer.parseInt(String.valueOf(leastCommon),2));
    }

    public static void partTwo() {
        char findOxygen = '1';
        char findCO2 = '0';
        String s1 = reduceToCorrectNr(findOxygen, numbers);
        String s2 = reduceToCorrectNr(findCO2, numbers);
        System.out.println("Answer 2: " + Integer.parseInt(s1,2) * Integer.parseInt(s2,2));
    }

    public static List<Integer> getmostCommonBits(List<String> strings) {
        int[] calculated = calculateMostCommon(strings);
        List<Integer> mostCommon = new ArrayList<>();
        Arrays.stream(calculated).forEach(e -> mostCommon.add(e > 0 ? 1 : 0));
        return mostCommon;
    }

    public static int[] calculateMostCommon(List<String> strings) {
        int[] ints = new int[strings.get(0).length()];
        for (int i = 0; i < strings.size() ; i++) {
            for (int j = 0; j < strings.get(0).length() ; j++) {
                switch (strings.get(i).charAt(j)) {
                    case '0' -> ints[j]--;
                    case '1' -> ints[j]++;
                }
            }
        }
        return ints;
    }

    public static String getFirstString(List<Integer> mostCommonBits) {
        AtomicReference<String> first = new AtomicReference<>("");
        mostCommonBits.forEach(i -> first.updateAndGet(v -> v + i));
        return first.toString();
    }

    public static String getSecondString(List<Integer> mostCommonBits) {
        AtomicReference<String> second = new AtomicReference<>("");
        mostCommonBits.forEach(i -> second.updateAndGet(v -> v + Math.abs(i - 1)));
        return second.toString();
    }

    public static String reduceToCorrectNr(char c, List<String> strings) {
        int position = 0;
        boolean equal = false;
        String correct = "";
        while(!equal) {
            int finalPosition = position;
            int sum1 = (int) strings.stream().filter(s -> s.charAt(finalPosition) == c).count();
            int sum0 = (int) strings.stream().filter(s -> s.charAt(finalPosition) != c).count();
            if (sum1 == sum0) {
                correct = strings.stream().filter(s -> s.charAt(finalPosition) == c).toList().get(0);
                equal = true;
            } else {
                char temp = sum1 > sum0 ? '1' : '0';
                strings = new LinkedList<>(strings.stream().filter(s-> s.charAt(finalPosition)==temp).toList());
            }
            position ++;
        }
        return correct;
    }
}
