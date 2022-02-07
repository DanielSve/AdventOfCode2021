package aoc10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AoC10 {

    private static final List<String> sortedRows = new ArrayList<>();
    private static final List<String> corruptedSigns = new ArrayList<>();
    private static List<String> allStrings = new ArrayList<>();
    private static List<String> incompleteSorted = new ArrayList<>();
    private static final List<String> reversed = new ArrayList<>();
    private static List<Long> sortedScores = new ArrayList<>();

    public static void main(String[] args) {
        try {
            allStrings = new BufferedReader(new FileReader("resources/input10.txt")).lines().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        partOne();
        partTwo();
    }

    public static void partOne() {
        sortRows();
        getCorruptedSigns();
        System.out.println("Answer part one: " + getScorePartOne(corruptedSigns));
    }

    public static void partTwo() {
        getIncompleteSorted();
        getReversedList();
        getSortedScores();
        System.out.println("Answer part two: " + sortedScores.get(sortedScores.size() / 2));
    }

    public static void sortRows() {
        allStrings.forEach(s -> sortedRows.add(getSortedString(s)));
    }

    public static void getCorruptedSigns() {
        sortedRows.stream().filter(s -> checkCorrupted(s).length()==1).toList().forEach(s -> corruptedSigns.add(checkCorrupted(s)));
    }

    public static long getScorePartOne(List<String> corrupted) {
        long sum = 0;
        for (int i = 0; i < corrupted.size() ; i++) {
            switch (corrupted.get(i)) {
                case ")" -> sum += 3;
                case "]" -> sum += 57;
                case "}" -> sum += 1197;
                case ">" -> sum += 25137;
            }
        }
        return sum;
    }

    public static void getIncompleteSorted() {
        incompleteSorted = sortedRows.stream().filter(s -> checkCorrupted(s).length()>1).toList();
    }

    public static void getReversedList() {
        incompleteSorted.forEach(e -> reversed.add(reverseBrackets(e)));
    }

    public static String reverseBrackets(String s) {
        return new StringBuilder(s).reverse().toString().replaceAll("\\{","\\}").replaceAll("\\(",
                "\\)").replaceAll("\\<", "\\>").replaceAll("\\[","\\]");
    }

    public static void getSortedScores() {
        reversed.forEach(e -> sortedScores.add(getScorePartTwo(e)));
        sortedScores = sortedScores.stream().sorted().toList();
    }

    public static long getScorePartTwo(String s) {
        long sum = 0;
        for (int i = 0; i <s.length() ; i++) {
            sum = sum * 5;
            switch (s.charAt(i)) {
                case ')' -> sum += 1;
                case ']' -> sum += 2;
                case '>' -> sum += 4;
                case '}' -> sum += 3;
            }
        }
        return sum;
    }

    public static String getSortedString(String s) {
        boolean hasPair = true;
        while (hasPair) {
            hasPair = false;
            for (int i = 0; i < s.length() - 1; i++) {
                String pairToCheck = s.substring(i, i +2);
                if (pairToCheck.equals("()") || pairToCheck.equals("<>") ||
                        pairToCheck.equals("{}") || pairToCheck.equals("[]")) {
                    s = s.substring(0, i) + s.substring(i + 2);
                    hasPair = true;
                }
            }
        }
        return s;
    }

    public static String checkCorrupted(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' || s.charAt(i) == ']' || s.charAt(i) == '>' || s.charAt(i) == '}') {
                return s.charAt(i) + "";
            }
        }
        return s;
    }
}




