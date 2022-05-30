package aoc12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Aoc12 {

        private static Scanner sc;
        private static List<String> connections = new ArrayList<>();
        private static final Set<String> pathsPartOne = new HashSet<>();
        private static final Set<String> pathsPartTwo = new HashSet<>();

    public static void main(String[] args) {
        try {
            sc = new Scanner(new File("resources/input12.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine()) {
            connections.add(sc.nextLine());
        }
        connections = connections.stream().map(c -> c.replaceAll("end", "q")).toList();
        getAllPaths();
        System.out.println("Answer part one: " + pathsPartOne.size());
        System.out.println("Answer part two: " + pathsPartTwo.size());
    }

    public static void getAllPaths() {
        for (String connection : connections) {
            if (connection.contains("start")) {
                String s = connection.replace("start", "").replace("-", "");
                getPathsPartOne(s, "start" + "," + s, "");
                getPathsPartTwo(s, "start" + "," + s, "", false);
            }
        }
    }

    public static void getPathsPartOne(String current, String acc, String smallCaves) {
        if(pathsPartOne.contains(acc) || smallCaves.contains(current)) {
            return;
        } else if(acc.contains("q")) {
            pathsPartOne.add(acc);
            return;
        }
        if(Character.isLowerCase(current.charAt(0))) {
            smallCaves += current + ",";
        }
        for (String connection : connections) {
            if (connection.contains(current) && !connection.contains("start")) {
                String next = connection.replace(current, "").replace("-", "");
                getPathsPartOne(next, acc + "," + next, smallCaves);
            }
        }
    }

    public static void getPathsPartTwo(String current, String acc, String smallCaves, boolean allreadyVisitedTwice) {
        if(pathsPartTwo.contains(acc) || (smallCaves.contains(current) && allreadyVisitedTwice)) {
            return;
        } else if(acc.contains("q")) {
            pathsPartTwo.add(acc);
            return;
        }
        if(Character.isLowerCase(current.charAt(0))) {
            if(smallCaves.contains(current)) {
                allreadyVisitedTwice = true;
            }
            smallCaves += current + ",";
        }
        for (String connection : connections) {
            if (connection.contains(current) && !connection.contains("start")) {
                String next = connection.replace(current, "").replace("-", "");
                getPathsPartTwo(next, acc + "," + next, smallCaves, allreadyVisitedTwice);
            }
        }
    }
}