import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static int[][] tastenfeld = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    Pair<Integer, Integer> pos = new Pair<>(1, 1);
    Stage window;
    TextField tfOutput;
    private String[] befehlszeilen;
    private List<Integer> code = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);


        tfOutput = new TextField();
        Button btnGo = new Button("GO");
        btnGo.setOnAction(e -> processInput());

        layout.getChildren().addAll(btnGo, tfOutput);

        StackPane root = new StackPane();
        root.getChildren().add(layout);
        window.setScene(new Scene(root, 300, 300));
        window.show();
    }

    private void processInput() {
        parseTextField();
        for (String befehlslatte : befehlszeilen) {
            code.add(processBefehlslatte(befehlslatte));
        }
        tfOutput.setText(code.toString());

    }

    private int processBefehlslatte(String befehlslatte) {
        for (int i = 0; i < befehlslatte.length(); i++) {
            move(befehlslatte.charAt(i));
        }
        return tastenfeld[pos.getKey()][pos.getValue()];
    }

    private void move(char c) {
        switch (c) {
            case 'U':
                if (pos.getKey() != 0) {
                    pos = new Pair<>(pos.getKey() - 1, pos.getValue());
                }
                break;
            case 'D':
                if (pos.getKey() != 2) {
                    pos = new Pair<>(pos.getKey() + 1, pos.getValue());
                }
                break;
            case 'L':
                if (pos.getValue() != 0) {
                    pos = new Pair<>(pos.getKey(), pos.getValue() - 1);
                }
                break;
            case 'R':
                if (pos.getValue() != 2) {
                    pos = new Pair<>(pos.getKey(), pos.getValue() + 1);
                }
                break;
        }
    }

    private void parseTextField() {
        List<String> befehle = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))) {
            line = br.readLine();
            while (line != null) {
                befehle.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FILE NOT FOUND" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOEXCEPTION" + e.getMessage());
        }
        befehlszeilen = new String[befehle.size()];
        for (int i = 0; i < befehle.size(); i++) {
            befehlszeilen[i] = befehle.get(i);
        }
    }
}
