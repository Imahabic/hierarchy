package com.projectsolution.hierarchicalstruct;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    /**
     * connected elements
     */
    HierarchyElement[] connectionBlocks = new HierarchyElement[2];
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
    private void drawElement(HierarchyElement element)
    {
        element.setOnMouseDragged(mouseEvent -> {
            element.updatePosition(mouseEvent.getX(),mouseEvent.getY());
            updateConnections();
        });
        element.setObserver(new TextObserver(this::updateConnections));
        element.setOnMouseClicked(event->{
            if(del.isSelected()) {
                pane.getChildren().remove(element);
                listElements.delete(element);
                updateConnections();
            }
            else if(event.isShiftDown()) {
                if (connectionBlocks[0] == null) {
                    connectionBlocks[0] = element;
                    element.draw(true);
                } else if (connectionBlocks[1] == null) {
                    connectionBlocks[1] = element;
                    connectionBlocks[0].draw(false);
                    addTransition(connectionBlocks[0], connectionBlocks[1]);
                    connectionBlocks = new HierarchyElement[2];
                }
            }
        });
        pane.getChildren().add(element);
        element.draw(false);
    }

    /**
     * @param e event of mouse
     */
    private void addElement(MouseEvent e)
    {
        HierarchyElement element = listElements.add("Элемент",e.getX(),e.getY());
        drawElement(element);
    }

    /**
     * update list of connections
     */
    private void updateConnections()
    {
        List<HierarchyElement[]> transitionElements=new ArrayList<>();
        for (Connection transition : listElements.getAllConnections()) {
            transitionElements.add(new HierarchyElement[]{transition.top, transition.bottom});
            pane.getChildren().remove(transition);
        }
        listElements.getAllConnections().clear();
        for (HierarchyElement[] connections : transitionElements)
            if(listElements.getAll().contains(connections[0]) && listElements.getAll().contains(connections[1])) {
                addTransition(connections[0], connections[1]);
            }
    }

    /**
     * @param top block
     * @param bottom block
     */
    private void addTransition(HierarchyElement top, HierarchyElement bottom)
    {
        Connection connection = listElements.createConnection(top, bottom);
        pane.getChildren().add(connection);
        connection.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.isControlDown()) {
                pane.getChildren().remove(connection);
                listElements.deleteConnection(connection);
            }
        });
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
        for (HierarchyElement element:temp.getAll()) {
            drawElement(listElements.add(element.copy()));
        }
        for (Connection transition:temp.getAllConnections()) {
            HierarchyElement[] connected = new HierarchyElement[2];
            for (HierarchyElement element:listElements.getAll()) {
                if (element.equals(transition.top))
                    connected[0] = element;
                if (element.equals(transition.bottom))
                    connected[1] = element;
            }
            addTransition(connected[0], connected[1]);
        }
        System.out.println("Успех!");
    }

    /**
     * remove all from pane
     */
    @FXML
    private void removeAllElements()
    {
        pane.getChildren().removeAll(listElements.getAll());
        pane.getChildren().removeAll(listElements.getAllConnections());
        listElements.removeAll();
    }
}