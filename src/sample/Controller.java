package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<File> listResult = new ArrayList<>();
    String searchingText;
    String expansion;


    //TODO Многопоточность. Тесты?

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TabPane tabPane;

    @FXML
    public void clickButton(ActionEvent actionEvent) throws IOException {
        SecondForm.display();
        if (SecondForm.expansion != null && SecondForm.searchingText != null) {
            expansion = SecondForm.expansion;
            searchingText = SecondForm.searchingText;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Node source = (Node) actionEvent.getSource();
            List<File> listFiles;
            File selectedDirectory = directoryChooser.showDialog(source.getScene().getWindow());
            if (selectedDirectory != null) {
                listFiles = processFilesFromFolder(selectedDirectory, expansion);
                List<File> listFilesAfterSearch = new ArrayList<>();
                for (int i = 0; i < listFiles.size(); i++) {
                    if (serchOnFile(searchingText, listFiles.get(i))) {
                        listFilesAfterSearch.add(listFiles.get(i));
                    }
                }
                createTree(listFilesAfterSearch);
            }
        }
    }

    @FXML
    public void clickLeaf(MouseEvent event) throws IOException {

        Tab tab;
        TreeView tree = (TreeView) event.getSource();
        TreeItem<String> treeItem = (TreeItem<String>) tree.getSelectionModel().getSelectedItem();
        if(treeItem != null) {
            if(treeItem.getChildren().isEmpty() && event.getClickCount() == 2) {
                tab = new Tab(treeItem.getValue());
                String pathFile = treeItem.getValue();
                while(treeItem.getParent() != null) {
                    treeItem = treeItem.getParent();
                    pathFile = treeItem.getValue() + "\\" + pathFile;
                }
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                tab.setContent(textArea);
                tabPane.getTabs().addAll(tab);
                FileInputStream fis=new FileInputStream(new File(pathFile));
                byte data[]=new byte[fis.available()];
                fis.read(data);
                fis.close();
                textArea.setText(new String(data));
            }
        }
    }

    private List<File> processFilesFromFolder(File folder, String expansion) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.getPath().contains(expansion)) {
                listResult.add(entry);
            }
            if (entry.isDirectory()) {
                processFilesFromFolder(entry, expansion);
            }
        }
        return listResult;
    }

    private boolean serchOnFile(String text, File file) throws IOException {
        boolean find = false;
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = in.readLine()) != null) {
            if (line.contains(text)) {
                find = true;
                break;
            }
        }
        in.close();
        return find;
    }

    private void createTree(List<File> listResultSerch) {
        TreeItem<String> rootNode = null;
        for (int i = 0; i < listResultSerch.size(); i++) {
            int slash = 0;
            String path = listResultSerch.get(i).getPath();
            String[] words = path.split("\\\\");
            if (i == 0) {
                rootNode = new TreeItem<>(words[0]);
            }
            TreeItem<String> prefNode = rootNode;
            for (int j = 1; j < words.length; j++) {
                boolean flag = false;
                List<TreeItem<String>> listChildPrefNode = prefNode.getChildren();
                for (TreeItem<String> in : listChildPrefNode) {
                    if (in.getValue().equals(words[j])) {
                        prefNode = in;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    TreeItem<String> currentNode = new TreeItem<>(words[j]);
                    prefNode.getChildren().addAll(currentNode);
                    prefNode = currentNode;
                }
            }
        }
        if (rootNode != null) {
            treeView.setRoot(rootNode);
        }
    }
}
