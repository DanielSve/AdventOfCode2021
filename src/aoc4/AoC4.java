package aoc4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class AoC4 {

    public static List<String[][]> allTrays= new ArrayList<>();
    public static List<Integer> roundForBingo = new ArrayList<>();
    public static List<String> inputLines;
    public static List<String> numbersDrawn;

    public static void main(String[] args) {
        try {
            inputLines = new LinkedList<>(new BufferedReader(new FileReader("resources/input4.txt")).lines().toList());
        } catch (FileNotFoundException e) {e.printStackTrace();}

        numbersDrawn = Arrays.stream(inputLines.get(0).split(",")).toList();
        fillTraysFromInput();
        getBingoRoundAllTrays();

        int earliest = roundForBingo.stream().sorted().toList().get(0);
        int earliestTray = getTrayFromBingoRound(earliest);
        int amountEarliest = getAmountForTray(allTrays.get(earliestTray));
        System.out.println("Answer part one " + amountEarliest * Integer.parseInt(numbersDrawn.get(earliest)));

        int latest = roundForBingo.stream().sorted().toList().get(roundForBingo.size()-1);
        int latestTray = getTrayFromBingoRound(latest);
        int amountLatest = getAmountForTray(allTrays.get(latestTray));
        System.out.println("Answer part two " + amountLatest * Integer.parseInt(numbersDrawn.get(latest)));
    }

    public static void getBingoRoundAllTrays() {
        allTrays.forEach(t -> roundForBingo.add(checkRoundForBingo(t,numbersDrawn)));
    }

    public static int checkRoundForBingo(String[][] s, List<String> l) {
        for (int i = 0; i <l.size() ; i++) {
            for (int j = 0; j <s.length ; j++) {
                for (int k = 0; k <s.length ; k++) {
                    if(s[j][k].equals(l.get(i))){
                        s[j][k]="--";
                    }
                    if(bingo(s)) {
                        return i;
                    }
                }
            }
        }
        return 1000;
    }

    public static boolean bingo(String[][] s){
        for (int i = 0; i < s.length; i++) {
            int counterRow= 0;
            int counterCol=0;
            for (int j = 0; j < s.length ; j++) {
                if(s[i][j].equals("--")) {
                    counterRow++;
                }
                if(s[j][i].equals("--")) {
                    counterCol++;
                }
                if(counterCol==5 || counterRow==5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getAmountForTray(String [][] tray) {
        int sum = 0;
        for (int i = 0; i <tray.length ; i++) {
            for (int j = 0; j < tray.length ; j++) {
                if(!tray[i][j].equals("--")) {
                    sum = sum + Integer.parseInt(tray[i][j]);
                }
            }
        }
        return sum;
    }

    public static int getTrayFromBingoRound(int round) {
        for (int i = 0; i <roundForBingo.size() ; i++) {
            if(roundForBingo.get(i) == round) {
                return i;
            }
        }
        return -1;
    }

    public static void fillTraysFromInput() {
        inputLines = inputLines.subList(2, inputLines.size());
        String longString = String.join("-", inputLines).replaceAll("--", ":")
        .replaceAll("- ", "-").replaceAll("\s{2}", " ");
        inputLines = new LinkedList<>(List.of(longString.split(":")));
        for (int i = 0; i < inputLines.size(); i++) {
            List<String> temp = Arrays.stream(inputLines.get(i).split("-")).toList();
            allTrays.add(toStringArr(temp));
        }
    }

    public static String [][] toStringArr (List<String> s) {
        String[][] strings = new String[5][5];
        for (int i = 0; i <s.size() ; i++) {
            String[] temp = s.get(i).split(" ");
            for (int j = 0; j < 5; j++) {
                strings[i][j]=temp[j];
            }
        }
        return strings;
    }
}






