module ninemanmorris {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens ninemanmorris to javafx.fxml;
    opens ninemanmorris.screen.screencontroller to javafx.fxml;
    opens ninemanmorris.screen.screencontroller.gamescreen to javafx.fxml;

    exports ninemanmorris;
    exports ninemanmorris.screen;
}
