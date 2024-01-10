package Music;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav"));

        Button playButton = new Button("Play Music");
        playButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                playMusic(selectedFile.toURI().toString());
            }
        });

        VBox root = new VBox(playButton);
        Scene scene = new Scene(root, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Music Player App");
        primaryStage.show();
    }

    private void playMusic(String fileUrl) {
        Media media = new Media(fileUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Optional: Set volume, balance, etc.
        mediaPlayer.setVolume(0.5);

        // Play the music
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

