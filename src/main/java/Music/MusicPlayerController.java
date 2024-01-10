package Music;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerController {
    @FXML
    private ListView<String> songListView;


    private Stage stage;
    private MediaPlayer mediaPlayer;
    private List<File> songs = new ArrayList<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        loadSongs();
    }

    @FXML
    private void loadSongs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null) {
            songs.addAll(selectedFiles);

            // Display the song names in the ListView
            songListView.getItems().clear();
            for (File song : songs) {
                songListView.getItems().add(song.getName());
            }
        }
    }

    @FXML
    private void playM(ActionEvent event) {
        if (songListView.getSelectionModel().getSelectedItem() != null) {
            int selectedIndex = songListView.getSelectionModel().getSelectedIndex();
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            Media media = new Media(songs.get(selectedIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    @FXML
    private void pauseM(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void stopM(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }


}
