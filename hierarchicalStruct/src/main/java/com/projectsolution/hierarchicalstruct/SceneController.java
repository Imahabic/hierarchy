package com.projectsolution.hierarchicalstruct;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller
 */
public class SceneController implements Initializable {
    /**
     * image
     */
    public ImageView save;
    /**
     * image
     */
    public ImageView saveImage;
    /**
     * image
     */
    public ImageView open;
    /**
     * image
     */
    public ImageView info;
    /**
     * image
     */
    public ImageView clear;
    /**
     * image
     */
    public ImageView scheme;
    /**
     * image
     */
    public ImageView deleter;
    /**
     * workPane
     */
    public AnchorPane pane;
    /**
     * add button
     */
    public ToggleButton add;
    /**
     * delete button
     */
    public ToggleButton del;
    public TreeView<String> hierarchyTree;
    /**
     * connected elements
     */
    Task connectionBlock;
    TreeItem<String> rootTreeNode;
    /**
     * list of elements
     */
    HierarchyElementsList listElements = new HierarchyElementsList();

    /**
     * @param url url
     * @param resourceBundle resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootTreeNode = new TreeItem<>("Иерархия");
        hierarchyTree.setRoot(rootTreeNode);
        save.setImage(new Image("save.png"));
        saveImage.setImage(new Image("saveImage.png"));
        open.setImage(new Image("open.png"));
        info.setImage(new Image("info.png"));
        clear.setImage(new Image("clear.png"));
        scheme.setImage(new Image("add.png"));
        deleter.setImage(new Image("delete.png"));
        pane.setOnMouseClicked(e->{
            if(add.isSelected())
                addElement(e);
        });
    }

    /**
     * @param element for drawing
     */
    private void drawElement(Task element)
    {
        element.setOnMouseDragged(mouseEvent -> {
            element.updatePosition(mouseEvent.getX(),mouseEvent.getY());
            updateConnections();
        });
        element.setObserver(new TextObserver(this::updateConnections));
        element.setOnMouseClicked(event->{
            if(del.isSelected()) {
                pane.getChildren().remove(element);
                deleteAllChilds(element);
                if(listElements.getAll().contains(element))
                    listElements.delete(element);
                else listElements.deleteChild(element);
                updateConnections();
            }
            else if(event.isShiftDown()) {
                if (connectionBlock == null) {
                    connectionBlock = element;
                    element.draw(true);
                } else {
                    connectionBlock.draw(false);
                    connectionBlock = element;
                    connectionBlock.draw(true);
                }
            }
            treeViewUpdate();
        });
        pane.getChildren().add(element);
        element.draw(false);
    }
    public void deleteAllChilds(Task element)
    {
            for(Task task: element.childrenTasks)
            {
                pane.getChildren().remove(task);
                deleteAllChilds(task);
            }
            element.childrenTasks.clear();
    }
    /**
     * @param e event of mouse
     */
    private void addElement(MouseEvent e)
    {
        if(connectionBlock==null) {
            Task element = listElements.add("Элемент", e.getX(), e.getY());
            drawElement(element);
        }
        else {
            Task child=new Task.TaskBuilder("Элемент", e.getX(), e.getY()).build();
            connectionBlock.addChildTask(child);
            drawElement(child);
            connectionBlock.draw(false);
            addTransition(connectionBlock,child);
            connectionBlock=null;
        }
       treeViewUpdate();
    }
    public void treeViewUpdate()
    {
        rootTreeNode.getChildren().clear();
        for (Task block: listElements.getAll()) {
            TreeItem<String> child = new TreeItem<>(block.getTextValue());
            block.visibleText.textProperty().addListener((observable, oldValue, newValue) ->child.setValue(newValue));
            rootTreeNode.getChildren().add(child);
            branchUpdate(block,child);
        }
    }
    public void branchUpdate(Task parent, TreeItem<String> parentBranch)
    {
        for (Task block: parent.childrenTasks) {
            TreeItem<String> child = new TreeItem<>(block.getTextValue());
            block.visibleText.textProperty().addListener((observable, oldValue, newValue) ->child.setValue(newValue));
            parentBranch.getChildren().add(child);
            branchUpdate(block, child);
        }
    }

    /**
     * update list of connections
     */
    private void updateConnections()
    {
        pane.getChildren().removeAll(listElements.getAllConnections());
        listElements.getAllConnections().clear();
        for (Task task : listElements.getAll())
            updateChildTasks(task);
    }
    public void updateChildTasks(Task task)
    {
        for (Task child: task.childrenTasks) {
            addTransition(task, child);
            if(child.childrenTasks.size()>0)
                updateChildTasks(child);
        }
    }

    /**
     * @param top block
     * @param bottom block
     */
    private void addTransition(Task top, Task bottom)
    {
        Connection connection = listElements.createConnection(top, bottom);
        pane.getChildren().add(connection);
    }

    /**
     * show information
     */
    @FXML
    private void showHelpMessage()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, """
                Данная программа предназначена для разработки иерархических структур
                Управление:
                    Верхняя панель - основные функции работы с холстом
                    Нижняя панель - функции для работы с объектами
                Shift+ЛКМ - выбор блока
                Ctrl+ЛКМ - удаление объекта
                ЛКМ(УДЕРЖ.) - перемещение объекта
                ЛКМх2 - ввод текста
                """, ButtonType.OK);
        alert.setTitle("Руководство по использованию");
        alert.setHeaderText("Иерархическая структура работ по проекту\n" +
                "Разработал: Иван Олейник");
        alert.show();
    }

    /**
     * @throws IOException file
     */
    @FXML
    private void exportIntoHSD() throws IOException {
        FileAdapter.exportIntoHSD(listElements);
    }

    /**
     * @throws IOException file
     */
    @FXML
    private void exportIntoPNG() throws IOException {
        FileAdapter.exportIntoPNG(pane.snapshot(new SnapshotParameters(), null));
    }

    /**
     * @throws IOException file
     * @throws ClassNotFoundException class
     */
    @FXML
    private void importFromHSD() throws IOException, ClassNotFoundException {
        HierarchyElementsList temp=FileAdapter.importFromHSD();
        assert temp != null;
        removeAllElements();
        for (Task element:temp.getAll())
            drawElement(listElements.add(element.copy()));
        for (Task element:listElements.getAll())
            drawingChild(element);
        updateConnections();
        System.out.println("Успех!");
    }
    private void drawingChild(Task parent)
    {
        for (Task element: parent.childrenTasks) {
            drawElement(element);
            if (element.childrenTasks.size() > 0)
                drawingChild(element);
        }
    }
    /**
     * remove all from pane
     */
    @FXML
    private void removeAllElements()
    {
        pane.getChildren().removeAll(listElements.getAll());
        for (Task task: listElements.getAll()) {
            deleteAllChilds(task);
        }
        pane.getChildren().removeAll(listElements.getAllConnections());
        listElements.removeAll();
        treeViewUpdate();
    }
}