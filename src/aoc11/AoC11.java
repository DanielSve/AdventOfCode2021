package aoc11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AoC11 {
        private static Scanner sc;
        private static final int[][] energyLevels = new int[10][10];
        private static int sumFlashes = 0;

    public static void main(String[] args) {
        try {
            sc = new Scanner(new File("resources/input11.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initializeEnergyLevels();
        //partOne();
        partTwo();
    }

    private static void initializeEnergyLevels() {
        for (int i = 0; sc.hasNextLine(); i++) {
            String line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                energyLevels[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }
    }

    public static void partOne() {
        repeatProcedure(1,100);
        System.out.println("Amount of flashes = " + sumFlashes);
    }

    public static void partTwo() {
        System.out.println("First step for synchronizity " + repeatProcedure(2, 1000));
    }

    public static int repeatProcedure(int part, int repeat) {
        for (int i = 0; i <repeat ; i++) {
            increaseAllByOne();
            flash();
            countFlashes();
            if(part == 2 && isSynchronized()) {
               return i+1;
            }
        }
        return 0;
    }

    public static void increaseAllByOne() {
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 10 ; j++) {
               energyLevels[i][j]++;
            }
        }
    }

    public static void flash() {
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <10 ; j++) {
              if(energyLevels[i][j] > 9) {
                 flashOctopus(i, j);
              }
            }
        }
    }

    public static void flashOctopus(int i, int j) {
        if(energyLevels[i][j] != 0) {
            energyLevels[i][j]++;
            if(energyLevels[i][j] > 9) {
                energyLevels[i][j] = 0;
                List<Coordinate> toFlash = getSurrounding(i, j);
                toFlash.forEach(c -> flashOctopus(c.getX(),c.getY()));
            }
        }
    }

    public static List<Coordinate> getSurrounding(int x, int y) {
        List<Coordinate> coordinates = new ArrayList<>();
        if(x > 0)
            coordinates.add(new Coordinate(x-1, y));
        if(x < 9)
            coordinates.add(new Coordinate(x+1,y));
        if(y > 0)
            coordinates.add(new Coordinate(x, y-1));
        if(y < 9)
            coordinates.add(new Coordinate(x, y+1));
        if(x > 0 && y > 0)
            coordinates.add(new Coordinate(x-1, y-1));
        if(x > 0 && y < 9)
            coordinates.add(new Coordinate(x-1, y+1));
        if(x < 9 && y > 0)
            coordinates.add(new Coordinate(x+1, y-1));
        if(x < 9 && y < 9)
            coordinates.add(new Coordinate(x+1,y+1));

        return coordinates;
    }

    public static void countFlashes() {
        sumFlashes += Arrays.stream(energyLevels).flatMapToInt(Arrays::stream).filter(i -> i==0).count();
    }

    public static boolean isSynchronized() {
        return Arrays.stream(energyLevels).flatMapToInt(Arrays::stream).filter(i -> i == energyLevels[0][0]).count()==100;
    }

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {return x;}
        public int getY() {return y;}
    }
}