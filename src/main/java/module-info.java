/**
 * @author reuzun
 */
module ceng.estu {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    requires fontawesomefx;
    requires com.jfoenix;
    opens ceng.estu.controller to javafx.fxml;
    exports ceng.estu.main;
}