package aoc6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class AoC6 {

    private static long[] fishUnsorted;
    private static long[] fishSorted;
    private static String input;

    public static void main(String[] args) {
        try {
            input = String.join(" ", new BufferedReader(new FileReader("resources/input6.txt")).lines().toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fishUnsorted = (Arrays.stream((input.split(","))).mapToLong(Integer::valueOf).toArray());

        System.out.println("Answer part one: " + getAmountByDays(80));
        System.out.println("Answer part two: " + getAmountByDays(256));
    }

    public static long getAmountByDays(int days) {
        sortFishByTimer();
        long temp = 0;
        for (int i = 0; i < days; i++) {
            for (int j = 0; j <fishSorted.length ; j++) {
                if(j==0) {
                    temp = fishSorted[j];
                    fishSorted[j] = fishSorted[j+1];
                } else if(j < 8){
                    fishSorted[j] = fishSorted[j+1];
                }
            }
            fishSorted[6] += temp;
            fishSorted[8] = temp;
        }
        return Arrays.stream(fishSorted).sum();
    }

    public static void sortFishByTimer() {
        fishSorted = new long[9];
        for (int i = 0; i < fishUnsorted.length; i++) {
            for (int j = 0; j < 8; j++) {
                if (fishUnsorted[i] == j) {
                    fishSorted[j]++;
                }
            }
        }
    }
}
