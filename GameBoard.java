import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

abstract class GameBoard {
    final Scanner scan = new Scanner(System.in);

    int[] scores = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    String[] scoreTypes = new String[] { "one", "two", "three", "four", "five", "six", "pair", "straight", "chance",
            "three-zee" };
    int[] numbers;

    public void displayScoreBoard(int[] numbers, boolean displayScores) {
        this.numbers = numbers;

        for (int i = 0; i < scores.length; i++) {
            int score = scores[i];
            String type = scoreTypes[i];
            if (score >= 0) {
                System.out.println(type + ": " + score);
            } else {
                int tempScore = calcTempScore(type);
                System.out.println(type + ": " + (displayScores ? tempScore : "") + " (Choosable)");
            }
        }
    }

    public int getTotal() {
        int total = 0;

        for (int i = 0; i < scores.length; i++)
            total += scores[i];

        return total;
    }

    public void selectOption() {
        System.out.print("\nChoose Option: ");
        String type = scan.nextLine();
        type = type.toLowerCase();
        int tempScore = calcTempScore(type);

        int indexOfScore = -1;

        for (int i = 0; i < scoreTypes.length; i++) {
            if (scoreTypes[i].equals(type))
                indexOfScore = i;
        }

        if (indexOfScore >= 0) {
            scores[indexOfScore] = tempScore;
        }

        System.out.println();
        this.displayScoreBoard(this.numbers, true);
    }

    public int calcTempScore(String type) {
        int tempScore = 0;

        switch (type) {
            case "one":
                tempScore = calcNum(1);
                break;
            case "two":
                tempScore = calcNum(2);
                break;
            case "three":
                tempScore = calcNum(3);
                break;
            case "four":
                tempScore = calcNum(4);
                break;
            case "five":
                tempScore = calcNum(5);
                break;
            case "six":
                tempScore = calcNum(6);
                break;
            case "chance":
                tempScore = calcChance();
                break;
            case "straight":
                tempScore = calcStraight();
                break;
            case "pair":
                tempScore = calcPair();
                break;
            case "three-zee":
                tempScore = calcThreeZee();
                break;
            default: {
                tempScore = -1;
            }
        }

        return tempScore;
    }

    public int calcPair() {
        HashMap<String, String> tallies = new HashMap<String, String>();
        for (int i = 0; i < numbers.length; i++) {
            String current = Objects.isNull(tallies.get(Integer.toString(numbers[i]))) ? "0"
                    : tallies.get(Integer.toString(numbers[i]));
            int currentInt = Integer.parseInt(current);
            tallies.put(Integer.toString(numbers[i]), Integer.toString(1 + currentInt));
        }

        int score = 0;

        for (String i : tallies.keySet()) {
            int count = Integer.parseInt(tallies.get(i));
            if (count >= 2) {
                score = 15;
            }
        }

        return score;
    }

    public int calcStraight() {
        int[] sortedArr = new int[numbers.length];
        int[] searchBox = numbers;
        int maxNum = Integer.MAX_VALUE;
        int minValue = maxNum;

        for (int k = 0; k < numbers.length; k++) {
            for (int i = 0; i < searchBox.length; i++) {
                if (searchBox[i] < minValue)
                    minValue = searchBox[i];
            }

            sortedArr[k] = minValue;

            int[] tempSearchBox = new int[searchBox.length - 1];
            int repeatCount = 0;
            int currentNum = 0;
            for (int i = 0; i < searchBox.length; i++) {
                if (searchBox[i] != minValue) {
                    tempSearchBox[currentNum] = searchBox[i];
                    currentNum++;
                } else if (searchBox[i] == minValue && repeatCount > 0) {
                    tempSearchBox[currentNum] = searchBox[i];
                    currentNum++;
                } else if (searchBox[i] == minValue && repeatCount == 0) {
                    repeatCount++;
                }
            }

            searchBox = tempSearchBox;
            minValue = maxNum;
        }

        // System.out.println(Arrays.toString(sortedArr));

        for (int i = 1; i < sortedArr.length; i++) {
            if (sortedArr[i - 1] != sortedArr[i] - 1)
                return 0;
        }

        return 20;
    }

    public int calcThreeZee() {
        int firstNum = numbers[0];

        for (int i = 0; i < numbers.length; i++) {
            if (firstNum != numbers[i])
                return 0;
        }

        return 30;
    }

    public int calcChance() {
        int score = 0;

        for (int i = 0; i < numbers.length; i++) {
            score += numbers[i];
        }

        return score;
    }

    public int calcNum(int num) {
        int foundCount = 0;

        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] == num)
                foundCount++;

        int score = foundCount * num;
        return score;
    }
}
