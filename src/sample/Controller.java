package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;

public class Controller {

    @FXML
    public Button button;

    @FXML
    public void click(ActionEvent event) {
        button.setText("Thanks!");
    }

    @FXML
    public void handleButtonAction(EventHandler event) {
        // Button was clicked, do something
    }


}
