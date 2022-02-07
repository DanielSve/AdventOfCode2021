package aoc1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AoC1 {

    private static List<Integer> depths;

    public static void main(String[] args) {
        try {
            depths = new BufferedReader(
                new FileReader("resources/input1.txt")).lines().mapToInt(Integer::parseInt).boxed().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int sum = (int) IntStream.range(1, depths.size()).filter(i -> depths.get(i) > depths.get(i - 1)).count();

        System.out.println("Answer part one: " + sum);

        int previousSum = 0;
        int counter = 0;
        for (int i = 2; i <depths.size() ; i++) {
                int currentSum = (depths.get(i)) + (depths.get(i - 1)) + (depths.get(i - 2));
                if (currentSum > previousSum && previousSum!=0)
                    counter++;
                previousSum = currentSum;
        }
        System.out.println("Answer part two: " + counter);
    }
}
