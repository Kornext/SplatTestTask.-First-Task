package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.File;


public class Main extends Application {
    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//        primaryStage.setTitle("Task №1");
//        button = new Button("Some text");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DirectoryChooser directoryChooser = new DirectoryChooser();
//                File selectedDirectory =
//                        directoryChooser.showDialog(primaryStage);
//                if (selectedDirectory != null) {
//                    System.out.println(selectedDirectory);
//                }
//            }
//        });
//
//        StackPane layout = new StackPane();
//        layout.getChildren().add(button);
//        Scene scene = new Scene(layout, 300, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) { launch(args);
  }
}
