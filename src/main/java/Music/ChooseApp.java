package Music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChooseApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SaveMusic.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("My Music_Player");
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("newmusic.png"));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
