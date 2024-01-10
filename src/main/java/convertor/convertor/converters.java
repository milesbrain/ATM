package convertor.convertor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class converters extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {


        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(parent);


        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("convertorimage.png"));
        primaryStage.setTitle("Convertor");
        primaryStage.show();

    }catch (Exception e){
            e.printStackTrace();
        }
}}
