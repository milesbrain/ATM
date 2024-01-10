package Music;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.*;

public class ControllerMusic {
    @FXML
    Stage stage;


    private List<File> songs = new ArrayList<>();



    @FXML
    Media media;

    MediaPlayer player;
    @FXML
    Pane pane;
    @FXML
    Label music_player;
    @FXML
    Button repeattbutton;


    @FXML
    private ListView<String> songshowView;

    int songNumber;
    TimerTask task;
    Timer timer;
    int selectedIndex;
    boolean running, repeat;
    @FXML
    Slider volumeSlider;
    @FXML
    ProgressBar progressBar;


    public void NextMusic(ActionEvent event) {
        try {

                if (songNumber < songs.size() - 1) {
                    songNumber++;
                } else {
                    songNumber = 0;
                }
                player.stop();
                if (running) {
                    terminateTimer();
                }
                media = new Media(songs.get(songNumber).toURI().toString());
                player = new MediaPlayer(media);
                music_player.setText(songs.get(songNumber).getName());
                player.play();
                Repeat();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void PlayMusic(ActionEvent event) {
        if (songshowView.getSelectionModel().getSelectedItem() != null) {
            selectedIndex = songshowView.getSelectionModel().getSelectedIndex();
            if (player != null) {
                player.stop();
            }
            media = new Media(songs.get(selectedIndex).toURI().toString());
            player = new MediaPlayer(media);

            player.setVolume(volumeSlider.getValue() * 0.01);
            player.play();
            beginTimer();

        }
    }

    public void PauseButton(ActionEvent event) {
        if (player != null) {
            player.pause();
            terminateTimer();
        }


    }


    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {


            @Override
            public void run() {
                running = true;
                double currentM = player.getCurrentTime().toSeconds();
                double stop = media.getDuration().toSeconds();
                System.out.println(currentM / stop);
                progressBar.setProgress(currentM / stop);
                if (currentM / stop == 1) {
                    terminateTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void terminateTimer() {
        running = false;
        timer.cancel();
    }


    @FXML
    public void AddMusicTo() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        List<File> choosefile = chooser.showOpenMultipleDialog(stage);
        if (choosefile != null) {
            songs.addAll(choosefile);

        }
        songshowView.getItems().clear();
        for (File file : songs) {
            songshowView.getItems().add(file.getName());

        }


    }


    public void initialize() {
        AddMusicTo();
        songshowView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    music_player.setText(newValue);
                }

                volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                                                             @Override
                                                             public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                                                                 player.setVolume(volumeSlider.getValue() * 0.01);
                                                             }


                                                         }


                );
                progressBar.setStyle("-fx-accent: #00FF");
                progressBar.setOnMousePressed(event -> {
                    double mouseX = event.getX();
                    double progressBarWidth = progressBar.getWidth();
                    double newPosition = mouseX / progressBarWidth;


                    player.seek(media.getDuration().multiply(newPosition));

                });
                progressBar.setOnMouseDragged(event -> {
                    double mouseX = event.getX();
                    double progressBarWidth = progressBar.getWidth();
                    double progress = mouseX / progressBarWidth;
                    player.seek(media.getDuration().multiply(progress));
                });
            }


        });
    }

    public void Previous(ActionEvent event) {

        if (songNumber > 0) {
            songNumber--;
            player.stop();
            if (running) {
                terminateTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            player = new MediaPlayer(media);
            music_player.setText(songs.get(songNumber).getName());
            player.play();
        } else {

            songNumber = songs.size() - 1;
            player.stop();
            if (running) {
                terminateTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            player = new MediaPlayer(media);
            music_player.setText(songs.get(songNumber).getName());
            player.play();
        }
    }

    public void Repeat() {

            repeat = !repeat;
            playSelectedSong();


    }

    private void playSelectedSong() {
       if (repeat){
           player.setOnEndOfMedia(()->{
               player.seek(player.getStartTime());

           });
           repeattbutton.setStyle("-fx-background-color: #00FF");

       }
       else
       {
           repeattbutton.setStyle("-fx-background-color: #FF0000");
       }
    }
}




