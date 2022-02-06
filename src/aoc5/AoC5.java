package aoc5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AoC5 {

    private static final List<Line> lines = new ArrayList<>();
    private static final List<List<Integer>> marked = new ArrayList<>();
    private static Scanner sc;

    public static void main(String[] args) throws FileNotFoundException {
        try {
            sc = new Scanner(new File("resources/input5.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            List<String> tempList = Arrays.stream(sc.nextLine().split(" -> ")).toList();
            List<String> c1 = Arrays.stream(tempList.get(0).split(",")).toList();
            List<String> c2 = Arrays.stream(tempList.get(1).split(",")).toList();
            lines.add(new Line(Integer.parseInt(c1.get(0)), Integer.parseInt(c1.get(1)),
                    Integer.parseInt(c2.get(0)), Integer.parseInt(c2.get(1))));
        }

        List<Line> horizontalOrVerticalLines = lines.stream().filter(
                line -> (line.getX1() == line.getX2() || line.getY1() == line.getY2())).toList();
        initializeMarkedList();
        markCoordinatesFromLines(horizontalOrVerticalLines);

        System.out.println("Answer part one: " + marked.stream().flatMap(List::stream).filter(i -> i > 1).count());

        List<Line> diagonalLines = lines.stream().filter(line -> !(line.getX1() == line.getX2() || line.getY1() == line.getY2())).toList();
        markCoordinatesFromLines(diagonalLines);

        System.out.println("Answer part two: " + marked.stream().flatMap(List::stream).filter(i -> i > 1).count());
    }

    public static void markCoordinatesFromLines(List<Line> list) {
        for (int i = 0; i < list.size() ; i++) {
            for (int j = 0; j < list.get(i).getCoordinates().size() ; j++) {
                marked.get((int) list.get(i).getCoordinates().get(j).getY())
                        .set((int)list.get(i).getCoordinates().get(j).getX(),
                                marked.get((int) list.get(i).getCoordinates().get(j).getY())
                                        .get((int) list.get(i).getCoordinates().get(j).getX()) +1);
            }
        }
    }

    public static void initializeMarkedList() {
        for (int i = 0; i < 1000 ; i++) {
            marked.add(new ArrayList<>());
            for (int j = 0; j < 1000 ; j++) {
                marked.get(i).add(0);
            }
        }
    }
}
