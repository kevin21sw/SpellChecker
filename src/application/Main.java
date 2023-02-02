package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

public class Main extends Application {

  Stage window;
  TextField inputUser = new TextField();

  public static TextArea enderrors = new TextArea();

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.getIcons().add(new Image("C:\\Users\\user\\Downloads\\icon.png"));
    window.setTitle("Spell Checker");

    Label inputLabel = new Label("What do you want to check?");

    Button submit = new Button("Check Text");
    submit.setOnAction(new HandleButton());

    enderrors.setVisible(false);

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20, 20, 20, 20));

    layout.getChildren().addAll(inputLabel, inputUser, submit, enderrors);

    Scene scene = new Scene(layout, 500, 300);
    window.setScene(scene);

    window.show();

  }

  public static void main(String[] args) {

    launch(args);

  }

  public class HandleButton implements EventHandler < ActionEvent > {
    @Override
    public void handle(ActionEvent e) {

      String[] wordList = Functions.readDictionary("C:\\Users\\user\\Downloads\\spellChecker-main\\spellChecker-main\\src\\spellcheckerkkk\\wordlist.txt"); //The location of the list with the words.

      String input = inputUser.getText();

      Functions.errors.clear();

      if (Functions.spellCheck(input, wordList)) {
        enderrors.setStyle("-fx-text-fill: green;");
        Functions.errors.add("No errors");

      } else {
        enderrors.setStyle("-fx-text-fill: red;");
        System.out.print("Errors"); // Returns when there is one or more issues.
      }

      String listString = String.join("\n", Functions.errors);
      enderrors.setText(listString);
      enderrors.setVisible(true);

    }
  }

}