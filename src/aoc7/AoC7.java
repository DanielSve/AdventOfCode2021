package aoc7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class AoC7 {

    private static List<Integer> positions;

    public static void main(String[] args) {
        try {
            String longString = String.join("", new BufferedReader(
                    new FileReader(new File("resources/input7.txt"))).lines().toList());
            positions = Arrays.stream(longString.split(",")).mapToInt(Integer::valueOf).boxed().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int lowest = 1000000000;
        int max = (positions.stream().mapToInt(v -> v).max()).getAsInt();

        System.out.println("Answer part one: " + getLowest(max,lowest,positions,"one"));
        System.out.println("Answer part two: " + getLowest(max,lowest,positions,"two"));
    }

    public static int getLowest(int max, int lowest, List<Integer> positions, String part) {
        for (int i = 0; i < max ; i++) {
            int sum = 0;
            for (Integer integer : positions) {
                sum = part.equals("one") ? sum + Math.abs(integer - i) : sum + getTotalSum(Math.abs(integer - i));
            }
            lowest = Math.min(lowest, sum);
        }
        return lowest;
    }

    public static int getTotalSum(int a) {
        int sum = 0;
        for (int i = 0 ; i <= a ; i++) {
            sum = sum + i;
        }
        return sum;
    }
}
