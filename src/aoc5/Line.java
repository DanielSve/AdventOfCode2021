package aoc5;

import java.util.ArrayList;
import java.util.List;

public class Line {
    int x1;
    int y1;
    int x2;
    int y2;

    List<Coordinate> allCoordinatesForLine = new ArrayList<>();

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        fillCoordinates();
    }

    public void fillCoordinates() {
        int x = x1;
        if (y1 == y2) {
            for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
                allCoordinatesForLine.add(new Coordinate(i, y1));
            }
        } else if (y1 < y2) {
            for (int i = y1; i <= y2; i++) {
                allCoordinatesForLine.add(new Coordinate(x, i));
                if (x1 < x2) {
                    x++;
                } else if (x1 > x2) {
                    x--;
                }
            }
        } else if (y1 > y2) {
            for (int i = y1; i >= y2; i--) {
                allCoordinatesForLine.add(new Coordinate(x, i));
                if (x1 < x2) {
                    x++;
                } else if (x1 > x2) {
                    x--;
                }
            }
        }
    }

    public int getX1() {return x1;}

    public int getY1() {return y1;}

    public int getX2() {return x2;}

    public int getY2() {return y2;}

    public List<Coordinate> getCoordinates() {return allCoordinatesForLine;}
}
