package convertor.convertor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class boy extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FFmpeg Audio Extractor");

        Button extractButton = new Button("Extract Audio");
        extractButton.setOnAction(e -> extractAudio());

        VBox vBox = new VBox(extractButton);
        Scene scene = new Scene(vBox, 300, 100);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void extractAudio() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File videoFile = fileChooser.showOpenDialog(null);

        if (videoFile != null) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Save Extracted Audio");
            File audioFile = fileChooser.showSaveDialog(null);

            if (audioFile != null) {
                // FFmpeg command to extract audio
                String ffmpegCommand = "ffmpeg -i " + videoFile.getAbsolutePath() + " -acodec copy " + audioFile.getAbsolutePath();

                try {
                    // Execute FFmpeg command
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", ffmpegCommand);
                    Process process = processBuilder.start();

                    // Wait for the process to complete
                    int exitCode = process.waitFor();

                    // Check the exit code to determine if the extraction was successful
                    if (exitCode == 0) {
                        System.out.println("Audio extraction successful!");
                    } else {
                        System.out.println("Audio extraction failed. Exit code: " + exitCode);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
