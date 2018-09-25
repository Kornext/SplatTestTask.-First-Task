package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import javafx.stage.DirectoryChooser;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<File> listResult = new ArrayList<>();

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    //searching text all be okay believe on that

    @FXML
    public void click(ActionEvent actionEvent) throws IOException {
//        SecondForm secondForm = new SecondForm();
//        secondForm.display();
        SecondForm.display();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Node source = (Node) actionEvent.getSource();
        List<File> listResultSerch = null;
        File selectedDirectory = directoryChooser.showDialog(source.getScene().getWindow());
        if (selectedDirectory != null) {
            if(textField.getText()!= null) {
                listResultSerch = processFilesFromFolder(selectedDirectory, textField.getText());
            }
            for(int i=0; i<listResultSerch.size(); i++) {
                System.out.println(listResultSerch.get(i).toString());
            }
        }
    }

    //В рекурсии передавать и listResult
    private List<File> processFilesFromFolder(File folder, String expansion)
    {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if(entry.getPath().contains(expansion)) {
                listResult.add(entry);
            }
            if (entry.isDirectory()) {
                processFilesFromFolder(entry, expansion);
            }
        }
        return listResult;
    }
}
/*
Теперь у меня есть все файлы log. Необходимо выполнить поиск текст в них

 */
