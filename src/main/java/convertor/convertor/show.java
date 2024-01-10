package convertor.convertor;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class show extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.showOpenDialog(primaryStage);

    }
}
