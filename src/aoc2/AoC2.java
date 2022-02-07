package aoc2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class AoC2 {

    private static List<String> lines;
    private static int depth;
    private static int horizontal;

    public static void main(String[] args) {
        try {
            lines = new BufferedReader(new FileReader("resources/input2.txt")).lines().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        partOne();
        partTwo();
    }

    public static void partOne() {
        horizontal = 0;
        depth = 0;
        for (String line : lines) {
            List<String> l = Arrays.asList(line.split(" "));
            switch (l.get(0)) {
                case "down" -> depth += Integer.parseInt(l.get(1));
                case "up" -> depth -= Integer.parseInt(l.get(1));
                case "forward" -> horizontal += Integer.parseInt(l.get(1));
            }
        }
        System.out.println("Answer part one: " + depth * horizontal);
    }

    public static void partTwo() {
        horizontal = 0;
        depth = 0;
        int aim = 0;
        for (String line : lines) {
            List<String> l = Arrays.asList(line.split(" "));
            switch (l.get(0)) {
                case "down" -> aim += Integer.parseInt(l.get(1));
                case "up" -> aim -= Integer.parseInt(l.get(1));
                case "forward" -> {
                    horizontal += Integer.parseInt(l.get(1));
                    depth = depth + aim * Integer.parseInt(l.get(1));
                }
            }
        }
        System.out.println("Answer part two: " + horizontal * depth);
    }
}
