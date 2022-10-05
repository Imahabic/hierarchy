package com.projectsolution.hierarchicalstruct;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;

/**
 * static class for work with files
 */
public class FileAdapter {

    /**
     * @param listElements for export
     * @throws IOException for file
     */
    public static void exportIntoHSD(HierarchyElementsList listElements) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор папки для сохранения");
        fileChooser.setInitialFileName("Иерархическая_Структура");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Иерархическая Структура", "*.hsd"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
        {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream outStream = new ObjectOutputStream(fos);
            outStream.writeObject(listElements);
            outStream.flush();
            outStream.close();
            System.out.println("Успех!");
        }
    }

    /**
     * @param screenShoot for export
     * @throws IOException for file
     */
    public static void exportIntoPNG(WritableImage screenShoot) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения (*.png)", "*.png"));
        fileChooser.setInitialFileName("Иерархия");
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(screenShoot, null);
            ImageIO.write(renderedImage, "png", file);
        }
    }

    /**
     * @return list of elements
     * @throws IOException for file
     * @throws ClassNotFoundException for classes
     */
    public static HierarchyElementsList importFromHSD() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл .hsd");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Иерархическая Структура", "*.hsd");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file!=null) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            HierarchyElementsList list = (HierarchyElementsList) inputStream.readObject();
            inputStream.close();
            return list;
        }
        return null;
    }
}
