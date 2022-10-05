package com.projectsolution.hierarchicalstruct;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * HierarchyElements Class
 */
public class HierarchyElement extends Group implements Serializable {

    /**
     * text
     */
    private String Text;
    /**
     * textarea
     */
    transient TextArea editableText;
    /**
     * visible text
     */
    transient Label visibleText;
    /**
     * coordinate x
     */
    private double Y, /**
     * coordinate y
     */
X;
    /**
     * width
     */
    transient double width, /**
     * height
     */
height;
    /**
     * anchor on x
     */
    transient private double mouseAnchorX, /**
     * anchor on y
     */
mouseAnchorY;
    /**
     * observer for text editing
     */
    transient private TextObserver observer;

    /**
     * @param text on block
     * @param x on pane
     * @param y on pane
     */
    public HierarchyElement(String text, double x, double y)
    {
        Text=text;
        X=x;
        Y=y;
        setTranslateX(X);
        setTranslateY(Y);
        visibleText = new Label(Text);
        visibleText.setAlignment(Pos.BASELINE_CENTER);
        editableText = new TextArea(Text);
        editableText.setVisible(false);
        editableText.setPrefRowCount(2);
        editableText.setPrefColumnCount(10);
        visibleText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        editableText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        visibleText.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                this.Text = visibleText.getText();
                editableText.setText(this.Text);
                visibleText.setVisible(false);
                editableText.setVisible(true);
                editableText.requestFocus();
            }
        });
        editableText.setOnKeyTyped(e -> {
            this.Text=editableText.getText();
            draw(false);
            notifyObservers();
        });
        editableText.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue) {
                visibleText.setVisible(true);
                editableText.setVisible(false);
                visibleText.setText(this.Text);
                draw(false);
                notifyObservers();
            }
        });
        this.width = computePrefWidth(-1);
        this.height = computePrefHeight(-1);
        setOnMousePressed(mouseEvent -> {
            setMouseAnchorX(mouseEvent.getX());
            setMouseAnchorY(mouseEvent.getY());
        });
        draw(false);
    }

    /**
     * @param observer for editing text
     */
    public void setObserver(TextObserver observer)
    {
        this.observer = observer;
    }

    /**
     * notifier
     */
    public void notifyObservers() {
            observer.update();
    }

    /**
     * @param x coordinate
     * @param y coordinate
     */
    public void updatePosition(double x, double y)
    {
        double xBound=0,yBound=0;
        setTranslateX(Math.max(getTranslateX() + x - getMouseAnchorX(), xBound));
        setTranslateY(Math.max(getTranslateY() + y - getMouseAnchorY(), yBound));
        setX(getTranslateX());
        setY(getTranslateY());
    }

    /**
     * @param mouseAnchorX on x
     */
    public void setMouseAnchorX(double mouseAnchorX) {
        this.mouseAnchorX = mouseAnchorX;
    }

    /**
     * @param mouseAnchorY on y
     */
    public void setMouseAnchorY(double mouseAnchorY) {
        this.mouseAnchorY = mouseAnchorY;
    }

    /**
     * @return set anchor on x
     */
    public double getMouseAnchorX() {
        return mouseAnchorX;
    }

    /**
     * @return set anchor on y
     */
    public double getMouseAnchorY() {
        return mouseAnchorY;
    }

    /**
     * @param x for set
     */
    public void setX(double x) {
        X = x;
    }

    /**
     * @param y for set
     */
    public void setY(double y) {
        Y = y;
    }

    /**
     * @param selected -> block red else blue
     */
    public void draw(boolean selected)
    {
        visibleText.applyCss();
        editableText.applyCss();
        getChildren().clear();
        float offset = 10f;
        Rectangle block = new Rectangle(
                0,
                0,
                visibleText.prefWidth(-1) + offset,
                visibleText.prefHeight(-1) + offset);
        width = block.prefWidth(-1);
        height = block.prefHeight(-1);
        if(selected) block.setFill(Color.RED);
        else block.setFill(Color.rgb(111, 220, 242));
        block.setArcWidth(10);
        block.setArcHeight(10);
        getChildren().addAll(block,visibleText,editableText);
        visibleText.setTranslateX(offset * 0.5f);
        visibleText.setTranslateY(offset * 0.5f);
    }

    /**
     * @return maxY
     */
    public double maxY() {
        return Y + height;
    }

    /**
     * @return maxX
     */
    public double maxX() {
        return X + width;
    }

    /**
     * @return array of points
     */
    public ArrayList<Point2D> getArrayOfMinMaxPointsTop() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(maxX() - width * 0.5, maxY()));
        return fromPoints;
    }

    /**
     * @return array of points
     */
    public ArrayList<Point2D> getArrayOfMinMaxPointsBottom() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(maxX(), maxY() - height * 0.5));
        fromPoints.add(new Point2D(maxX() - width * 0.5, Y));
        fromPoints.add(new Point2D(X, maxY() - height * 0.5));
        return fromPoints;
    }

    /**
     * @return clone
     */
    public HierarchyElement copy()
    {
        return new HierarchyElement(this.Text,this.X,this.Y);
    }

    /**
     * @param element equable
     * @return result of equals
     */
    public boolean equals(HierarchyElement element)
    {
        return (element.X==this.X && element.Y==this.Y && element.Text.equals(this.Text));
    }
}
