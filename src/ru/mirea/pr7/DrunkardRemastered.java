package ru.mirea.pr7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DrunkardRemastered extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    public static String play(Queue<Integer> playerFirst, Queue<Integer> playerSecond) {
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
            return "Ботва, за 106 раундов никто не смог победить :(" ;
        } else if (playerSecond.isEmpty()) {
            return "В " + part + " ход победил игрок №1";
        } else {
            return "В " + part + " ход победил игрок №2";
        }
    }

    public static Queue<Integer> parseToInteger(String[] pull) {
        Queue<Integer> player = new LinkedList<>();
        for (String s : pull) {
            player.add(Integer.parseInt(s));
        }
        return player;
    }

    public static boolean isCorrect(String[] pullFirst, String[] pullSecond) {
        if (pullFirst == null || pullSecond == null ||
                pullFirst.length < 5 || pullSecond.length < 5) {
            return false;
        }
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Пьяница оффлайн");

        TextField tfFirst = new TextField();
        TextField tfSecond = new TextField();

        Text tFirst = new Text();
        tFirst.setText("Введите 5 карт разного номинала для игрока 1:");
        Text tSecond = new Text();
        tSecond.setText("Введите 5 карт разного номинала для игрока 2:");

        Button button = new Button("Играть");

        button.setOnAction(e -> {
            String textFirst = tfFirst.getText();
            String textSecond = tfSecond.getText();
            if (isCorrect(textFirst.split(" "), textSecond.split(" "))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, getWinner(textFirst, textSecond));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Все карты должны быть без повторений!");
                alert.showAndWait();
            }

        });

        GridPane root = new GridPane();

        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(15);

        root.add(tFirst, 0, 0, 2, 1);
        root.add(tSecond, 3, 0, 2, 1);

        root.add(tfFirst, 0, 1, 1, 1);
        root.add(tfSecond, 3, 1, 1, 1);

        root.add(button, 1, 4, 2, 1);

        Scene primaryScene = new Scene(root, 650, 170);
        primaryStage.setScene(primaryScene);

        primaryStage.show();
    }

    private String getWinner(String textFirst, String textSecond) {

        String[] pullFirst, pullSecond;

        pullFirst = textFirst.split(" ");
        pullSecond = textSecond.split(" ");

        Queue<Integer> playerFirst = parseToInteger(pullFirst);
        Queue<Integer> playerSecond = parseToInteger(pullSecond);

        return play(playerFirst, playerSecond);
    }
}
