package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import sun.reflect.generics.tree.Tree;

import javax.swing.tree.TreePath;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Controller {

    List<File> listResult = new ArrayList<>();
    String searchingText;
    String expansion;

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    private TreeView<String> treeView;

    //all be okay believe on that

    @FXML
    public void click(ActionEvent actionEvent) throws IOException {
        SecondForm.display();
        if (SecondForm.expansion != null && SecondForm.searchingText != null) {
            expansion = SecondForm.expansion;
            searchingText = SecondForm.searchingText;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Node source = (Node) actionEvent.getSource();
            List<File> listResultSerch = null;
            File selectedDirectory = directoryChooser.showDialog(source.getScene().getWindow());
            System.out.println(selectedDirectory.getPath());
            if (selectedDirectory != null) {
                listResultSerch = processFilesFromFolder(selectedDirectory, expansion);
            }


            //Вывод
//            for (int i = 0; i < listResultSerch.size(); i++) {
//                System.out.println(listResultSerch.get(i).toString());
//            }

            System.out.println("******************************************");
            for (int i = 0; i < listResultSerch.size(); i++) {
                if (serchOnFile(searchingText, listResultSerch.get(i)) == false) {
                    listResultSerch.remove(i);
                }
            }

            //Вывод
            for (int i = 0; i < listResultSerch.size(); i++) {
                System.out.println(listResultSerch.get(i).toString());
            }
            createTree(listResultSerch);
        }

//            //Тестовое дерево
//            TreeItem<String> rootNode = new TreeItem<>("root");
//            TreeItem<String> node1 = new TreeItem<>("111");
//            TreeItem<String> node2 = new TreeItem<>("222");
//            TreeItem<String> node3 = new TreeItem<>("333");
//            TreeItem<String> node4 = new TreeItem<>("444");
//            TreeItem<String> node12 = new TreeItem<>("112");
//            TreeItem<String> node13 = new TreeItem<>("113");
//            TreeItem<String> node14 = new TreeItem<>("114");
//            rootNode.getChildren().addAll(node1, node2, node3, node4);
//            node1.getChildren().addAll(node12, node13, node14);
//            treeView.setRoot(rootNode);
//            System.out.println("**************************");
//            List<TreeItem<String>> list = rootNode.getChildren();
//            for(TreeItem<String> in : list) {
//                System.out.println(in.getValue());
//            }
    }

    //В рекурсии передавать и listResult
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
                if (flag == false) {
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
