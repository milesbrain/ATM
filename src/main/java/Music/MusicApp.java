package Music;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MusicApp extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {


            Parent root =  FXMLLoader.load(getClass().getResource("/PlaySong.fxml"));
            Scene scene = new Scene(root);




            primaryStage.setTitle("Hello!");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}