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

    List<File> listFilesAfterSearch = new ArrayList<>();
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
    public void clickButtonOverlook(ActionEvent actionEvent) throws IOException {
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
    public void clickButtonSelectAll(ActionEvent actionEvent) {
        TextArea textArea = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        textArea.selectRange(0, textArea.getLength());
    }

    @FXML
    public void clickButtonNext(ActionEvent actionEvent) throws IOException {
        String currentTabName = tabPane.getSelectionModel().getSelectedItem().getText();
        for (int i = 0; i < listFilesAfterSearch.size(); i++) {
            String filePath = listFilesAfterSearch.get(i).getPath();
            if (filePath.contains(currentTabName)) {
                Tab tab = tabPane.getSelectionModel().getSelectedItem();
                if (i + 1 == listFilesAfterSearch.size()) {
                    changeCurrentTab(tab, listFilesAfterSearch.get(0));
                    //openTab(listFilesAfterSearch.get(0));
                } else {
                    changeCurrentTab(tab, listFilesAfterSearch.get(i + 1));
                    //openTab(listFilesAfterSearch.get(i + 1));
                }
                break;
            }
        }
    }

    @FXML
    public void clickButtonPrev(ActionEvent actionEvent) throws IOException {
        String currentTabName = tabPane.getSelectionModel().getSelectedItem().getText();
        for (int i = 0; i < listFilesAfterSearch.size(); i++) {
            String filePath = listFilesAfterSearch.get(i).getPath();
            if (filePath.contains(currentTabName)) {
                Tab tab = tabPane.getSelectionModel().getSelectedItem();
                if (i == 0) {
                    changeCurrentTab(tab, listFilesAfterSearch.get(listFilesAfterSearch.size() - 1));
                    //openTab(listFilesAfterSearch.get(listFilesAfterSearch.size() - 1));
                } else {
                    changeCurrentTab(tab, listFilesAfterSearch.get(i - 1));
                    //openTab(listFilesAfterSearch.get(i - 1));
                }
                break;
            }
        }
    }

    @FXML
    public void clickLeaf(MouseEvent event) throws IOException {

        TreeView tree = (TreeView) event.getSource();
        TreeItem<String> treeItem = (TreeItem<String>) tree.getSelectionModel().getSelectedItem();
        if (treeItem != null) {
            if (treeItem.getChildren().isEmpty() && event.getClickCount() == 2) {
                String pathFile = treeItem.getValue();
                while (treeItem.getParent() != null) {
                    treeItem = treeItem.getParent();
                    pathFile = treeItem.getValue() + "\\" + pathFile;
                }
                openTab(new File(pathFile));
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

    private void openTab(File file) throws IOException {
        String filePath = file.getPath();
        int indexLastSlach = filePath.lastIndexOf('\\');
        String tabName = filePath.substring(++indexLastSlach, filePath.length());
        Tab tab = new Tab(tabName);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        tab.setContent(textArea);
        tabPane.getTabs().addAll(tab);
        FileInputStream fis = new FileInputStream(new File(file.getPath()));
        byte data[] = new byte[fis.available()];
        fis.read(data);
        fis.close();
        textArea.setText(new String(data));
    }

    private void changeCurrentTab(Tab tab, File file) throws IOException {
        String filePath = file.getPath();
        int indexLastSlach = filePath.lastIndexOf('\\');
        String tabName = filePath.substring(++indexLastSlach, filePath.length());
        tab.setText(tabName);
        TextArea textArea = (TextArea) tab.getContent();
        FileInputStream fis = new FileInputStream(new File(file.getPath()));
        byte data[] = new byte[fis.available()];
        fis.read(data);
        fis.close();
        textArea.setText(new String(data));
    }
}
