package convertor.convertor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class New_windowController {
    @FXML
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    private void videocon(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/music.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Convert Your Video");
        stage.show();

    }
    @FXML
    private void photocon(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Photo.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Convert Your Photo");
        stage.show();


    }



}
