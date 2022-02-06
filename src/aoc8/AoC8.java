package aoc8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class AoC8 {

    private static Scanner sc;

    public AoC8() {
        try {
            sc = new Scanner(new File("resources/input8.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> rightList = new ArrayList<>();
        List<String> leftList = new ArrayList<>();

        while (sc.hasNextLine()) {
            List<String> tempList = Arrays.stream(sc.nextLine().split(" \\| ")).toList();
            leftList.add(tempList.get(0));
            rightList.add(tempList.get(1));
        }
        
        long amountUnique = getAmountUnique(rightList);
        System.out.println("Answer part one: " + amountUnique);

        List<List<String>> leftSortedList = getLeftSortedList(leftList);
        List<List<String>> rightSortedList = getRightSortedList(rightList, leftSortedList);
        List<Integer> outputValues = getOutputList(leftSortedList,rightSortedList);
        System.out.println("Answer part two: " + outputValues.stream().mapToInt(Integer::intValue).sum());
    }

    public long getAmountUnique(List<String> listRight) {
        return Arrays.stream(String.join(" ", listRight).split(" "))
                .filter(s-> s.length()==2 || s.length()==4 || s.length() == 3 || s.length() == 7).count();
    }

    public List<List<String>> getLeftSortedList(List<String> left) {
        List<List<String>> leftSortedList = new ArrayList<>();
        left.forEach(s -> leftSortedList.add(sortLeftSection(Arrays.stream(s.split(" ")).toList())));
        return leftSortedList;
    }

    public List<List<String>> getRightSortedList(List<String> right, List<List<String>> left) {
        List<List<String>> rightSortedList = new ArrayList<>();
        for (int i = 0; i <left.size() ; i++) {
            rightSortedList.add(sortRightSection(Arrays.stream(right.get(i).split(" ")).toList(),left.get(i)));
        }
        return rightSortedList;
    }

    public List<String> sortLeftSection(List<String> left) {

        final String one = left.stream().filter(s-> s.length()==2).toList().get(0);
        final String four = left.stream().filter(s -> s.length()==4).toList().get(0);
        final String seven = left.stream().filter(s->s.length()==3).toList().get(0);
        final String eight = left.stream().filter(s -> s.length()==7).toList().get(0);

        List<String> patternsLengthFive = new ArrayList<>();
        left.stream().filter(s -> s.length()==5).forEach(patternsLengthFive::add);
        String horizontalSegments = findHorizontalsegments(patternsLengthFive);

        final String three = getCorrectOrder(left,horizontalSegments + one);
        final String zero = getCorrectOrder(left,removePart(eight, getMutualPart(horizontalSegments,four)));
        final String nine = getCorrectOrder(left,getDeviantFrom(four,three)+ three) ;

        final List<String> numbersSoFar = Arrays.asList(zero,one,three,four,seven,eight,nine);
        List<String> remaining = left.stream().filter(s -> !numbersSoFar.contains(s)).toList();

        final String six = remaining.stream().filter(s -> s.length() == 6).toList().get(0);
        final String five = getCorrectOrder(left, removePart(six, getDeviantFrom(eight,nine)));

        final List<String> numbersSoFar2 = Arrays.asList(zero,one,three,four,seven,eight,nine,six,five);
        remaining = left.stream().filter(s -> !numbersSoFar2.contains(s)).toList();
        final String two = remaining.get(0);

        return Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);
    }

    public List<String> sortRightSection(List<String> right, List<String> left) {
        return right.stream().map(e -> e = getCorrectOrder(left,e)).collect(Collectors.toList());
    }

    public List<Integer> getOutputList(List<List<String>> leftSorted, List<List<String>> rightSorted) {
        List<Integer> outputValues = new ArrayList<>();
        for (int i = 0; i <leftSorted.size() ; i++) {
            outputValues.add(getOutputValue(leftSorted.get(i),rightSorted.get(i)));
        }
        return outputValues;
    }

    public static int getOutputValue(List<String> listLeft, List<String> listRight) {
        String temp = "";
        for (int i = 0; i < listRight.size() ; i++) {
            for (int j = 0; j <listLeft.size() ; j++) {
                if (listRight.get(i).equals(listLeft.get(j))) {
                    temp += j;
                }
            }
        }
        return Integer.parseInt(temp);
    }

    public static String findHorizontalsegments(List<String> l) {
        List<String> horizontals = new ArrayList<>();
        for (int i = 0; i < l.get(0).length() ; i++) {
            if(l.get(1).contains(l.get(0).charAt(i)+"") && l.get(2).contains(l.get(0).charAt(i)+"")) {
                horizontals.add(l.get(0).substring(i, i+1));
            }
        }
        return String.join("",horizontals);
    }

    public static String getCorrectOrder(List<String> strings, String s) {
        for (int i = 0; i <strings.size() ; i++) {
            int counter = 0;
            for (int j = 0; j <s.length() ; j++) {
                if(strings.get(i).contains(s.charAt(j) +"")) {
                    counter++;
                }
                if (counter == s.length() && strings.get(i).length() == s.length()) {
                    return strings.get(i);
                }
            }
        }
        return null;
    }

    public static String getDeviantFrom(String one, String two) {
        for (int i = 0; i < one.length() ; i++) {
            if(two.indexOf(one.charAt(i))==-1) {
                return one.charAt(i) + "";
            }
        }
        return null;
    }

    public static String getMutualPart(String one, String two) {
        for (int i = 0; i < one.length() ; i++) {
            if(two.indexOf(one.charAt(i))!=-1) {
                return one.charAt(i) +"";
            }
        }
        return null;
    }

    public static String removePart(String toRemoveFrom, String toRemove) {
        return toRemoveFrom.replaceAll(toRemove, "");
    }

    public static void main(String[] args) throws FileNotFoundException {
        AoC8 aoC8 = new AoC8();
    }
}
