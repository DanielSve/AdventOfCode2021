package aoc9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AoC9 {

    private static final List<List<Integer>> heightMap = new ArrayList<>();
    private static final List<List<Integer>> countedCoordinates = new ArrayList<>();
    private static final List<Lowpoint> lowPoints= new ArrayList<>();
    private static Scanner sc;

    public static void main(String[] args) {
        try {
            sc = new Scanner(new File("resources/input9.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            List<Integer> ints = new ArrayList<>();
            String line = sc.nextLine();
            line.chars().forEach(c -> ints.add(Character.getNumericValue(c)));
            heightMap.add(ints);
        }
        partOne();
        partTwo();
    }

    public static void partOne() {
        initializeCountedCoordinates();
        registerLowPoints();
        System.out.println("Answer to part one: " + lowPoints.stream().mapToInt(Lowpoint::getValue).sum());
    }

    public static void partTwo() {
        List<Integer> basinSizes = getBasinsizes();
        basinSizes.sort(Comparator.reverseOrder());

        System.out.println("Answer to part two: " + basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
    }

    public static void initializeCountedCoordinates() {
        for (int i = 0; i < heightMap.size() ; i++) {
            countedCoordinates.add(new ArrayList<>());
            for (int j = 0; j < heightMap.get(0).size() ; j++) {
                countedCoordinates.get(i).add(-1);
            }
        }
    }

    public static void registerLowPoints() {
        for (int i = 0; i < heightMap.size() ; i++) {
            for (int j = 0; j < heightMap.get(0).size() ; j++) {
                List<Integer> listOfSurrounding = getSurrounding(i,j);
                int current = heightMap.get(i).get(j);
                if(listOfSurrounding.stream().filter(s -> s >current).count() == listOfSurrounding.size()) {
                    lowPoints.add(new Lowpoint(i,j, heightMap.get(i).get(j)));
                }
            }
        }
    }

    public static List<Integer> getBasinsizes() {
        List<Integer> basinSizes = new ArrayList<>();
        lowPoints.forEach(l -> basinSizes.add(getRecSum(0,l.getX(), l.getY())));
        return basinSizes;
    }

    public static List<Integer> getSurrounding(int i, int j) {
        List<Integer> list = new ArrayList<>();
        if(i < heightMap.size()-1) {
            list.add(heightMap.get(i+1).get(j));
        }
        if(i > 0) {
            list.add(heightMap.get(i-1).get(j));
        }
        if(j < heightMap.get(0).size()-1) {
            list.add(heightMap.get(i).get(j+1));
        }
        if(j > 0) {
            list.add(heightMap.get(i).get(j-1));
        }
        return list;
    }

    public static int getRecSum(int accSum, int i, int j) {
        if(countedCoordinates.get(i).get(j) == -1) {
            accSum++;
            countedCoordinates.get(i).set(j, 1);
            if (j != heightMap.get(0).size() - 1 && heightMap.get(i).get(j + 1) < 9) {
                accSum = getRecSum(accSum, i, j + 1);
            }
            if (j != 0 && heightMap.get(i).get(j - 1) < 9) {
                accSum = getRecSum(accSum, i, j - 1);
            }
            if (i != heightMap.size() - 1 && heightMap.get(i + 1).get(j) < 9) {
                accSum = getRecSum(accSum, i + 1, j);
            }
            if (i != 0 && heightMap.get(i - 1).get(j) < 9) {
                accSum = getRecSum(accSum, i - 1, j);
            }
        }
        return accSum;
    }

    public static class Lowpoint {
        int x;
        int y;
        int value;

        public Lowpoint(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value + 1;
        }
        public int getX() {return x;}
        public int getY() {return y;}
        public int getValue() {return value;}
    }
}
