package ru.mirea.pr7;

import java.util.*;

public class DrunkardLinkedList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] pullFirst, pullSecond;
        do {
            System.out.println("Введите 5 карт разного номинала для игрока 1:");
            pullFirst = scanner.nextLine().split(" ");
            System.out.println("Введите 5 карт разного номинала для игрока 2:");
            pullSecond = scanner.nextLine().split(" ");
        } while (!isCorrect(pullFirst, pullSecond));

        LinkedList<Integer> playerFirst = parseToInteger(pullFirst);
        LinkedList<Integer> playerSecond = parseToInteger(pullSecond);

        play(playerFirst, playerSecond);

    }

    public static void play(LinkedList<Integer> playerFirst, LinkedList<Integer> playerSecond) {
        int part = 0;
        while (!playerFirst.isEmpty() && !playerSecond.isEmpty() && part < 107) {
            int cardFirst = playerFirst.remove();
            int cardSecond = playerSecond.remove();

            if (cardFirst == 0 && cardSecond == 9) {
                playerFirst.add(cardFirst);
                playerFirst.add(cardSecond);
            } else if (cardFirst == 9 && cardSecond == 0) {
                playerSecond.add(cardFirst);
                playerSecond.add(cardSecond);
            } else if (cardFirst > cardSecond) {
                playerFirst.add(cardFirst);
                playerFirst.add(cardSecond);
            } else {
                playerSecond.add(cardFirst);
                playerSecond.add(cardSecond);
            }
            part++;
        }

        if (part == 107) {
            System.out.println("botva");
        } else if (playerSecond.isEmpty()) {
            System.out.println("first " + part);
        } else {
            System.out.println("second " + part);
        }
    }

    public static LinkedList<Integer> parseToInteger(String[] pull) {
        LinkedList<Integer> player = new LinkedList();
        for (String s : pull) {
            player.add(Integer.parseInt(s));
        }
        return player;
    }

    public static boolean isCorrect(String[] pullFirst, String[] pullSecond) {
        String[] check = new String[10];
        System.arraycopy(pullFirst, 0, check, 0, 5);
        System.arraycopy(pullSecond, 0, check, 5, 5);
        Arrays.sort(check);
        for (int i = 0; i < 10; i++) {
            if (i != Integer.parseInt(check[i])) {
                System.out.println("Все карты должны быть без повторений!");
                return false;
            }
        }
        return true;
    }
}
