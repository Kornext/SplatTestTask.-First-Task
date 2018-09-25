package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class SecondForm {

    public static void display() throws IOException {
        Button btnOk = new Button("OK");
        Button btnCancel = new Button("Cancel");
        TextArea textArea = new TextArea();
        Label captionLable = new Label("Укажите текст для поиска");
        Label expansionLabel = new Label("Расширение");
        TextField textField = new TextField(".log");

        btnCancel.setLayoutX(292);
        btnCancel.setLayoutY(203);
        btnCancel.setPrefSize(59,25);
        btnCancel.setMnemonicParsing(false);
        btnOk.setLayoutX(16);
        btnOk.setLayoutY(203);
        btnOk.setMnemonicParsing(false);
        btnOk.setPrefSize(59,25);
        textArea.setLayoutX(14);
        textArea.setLayoutY(44);
        textArea.setPrefSize(336, 135);
        captionLable.setLayoutX(112);
        captionLable.setLayoutY(14);
        expansionLabel.setLayoutX(103);
        expansionLabel.setLayoutY(207);
        textField.setLayoutX(183);
        textField.setLayoutY(204);
        textField.setPrefSize(68,25);

        AnchorPane rootAnchorPane = new AnchorPane();
        rootAnchorPane.setBottomAnchor(btnCancel, 15D);
        rootAnchorPane.setRightAnchor(btnCancel, 15D);
        rootAnchorPane.setLeftAnchor(btnOk, 15D);
        rootAnchorPane.setBottomAnchor(btnOk, 15D);
        rootAnchorPane.setLeftAnchor(textArea, 15D);
        rootAnchorPane.setRightAnchor(textArea, 15D);
        rootAnchorPane.setLeftAnchor(captionLable, 112D);
        rootAnchorPane.setRightAnchor(captionLable, 112D);

        rootAnchorPane.getChildren().addAll(btnCancel, btnOk, expansionLabel,
                textArea, captionLable, textField);
        rootAnchorPane.setPrefSize(365, 244);
        Scene scene = new Scene(rootAnchorPane);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.showAndWait();
    }
}
