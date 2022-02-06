package aoc2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class AoC2 {

    private static List<String> lines;

    public static void main(String[] args) {
        try {
            lines = new BufferedReader(new FileReader("resources/input2.txt")).lines().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Answer part one: " +
                lines.stream().filter(s-> s.contains("forward")).mapToInt(s-> Integer.parseInt(s.substring(8))).sum() *
                (lines.stream().filter(s -> s.contains("down")).mapToInt(s -> Integer.parseInt(s.substring(5))).sum()
                - lines.stream().filter(s -> s.contains("up")).mapToInt(s -> Integer.parseInt(s.substring(3))).sum()));

        int horizontal = 0;
        int depth = 0;
        int aim = 0;
        for (int i = 0; i <lines.size() ; i++) {
            List<String> l = Arrays.asList(lines.get(i).split(" "));
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
