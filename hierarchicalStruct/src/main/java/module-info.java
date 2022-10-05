/**
 * module info
 */
module com.projectsolution.hierarchicalstruct {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens com.projectsolution.hierarchicalstruct to javafx.fxml;
    exports com.projectsolution.hierarchicalstruct;
}