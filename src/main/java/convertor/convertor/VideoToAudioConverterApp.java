package convertor.convertor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class VideoToAudioConverterApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Video to Audio Converter");

        Button convertButton = new Button("Convert Video to Audio");
        convertButton.setOnAction(e -> {
            File videoFile = chooseVideoFile();
            if (videoFile != null) {
                convertVideoToAudio(videoFile);
            }
        });

        VBox vBox = new VBox(convertButton);
        Scene scene = new Scene(vBox, 300, 100);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private File chooseVideoFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi")
        );
        return fileChooser.showOpenDialog(null);
    }

    private void convertVideoToAudio(File videoFile) {
        if (videoFile != null) {
            String inputVideoPath = videoFile.getAbsolutePath();
            String outputAudioPath = inputVideoPath.substring(0, inputVideoPath.lastIndexOf('.')) + "_audio.mp3";

            // FFmpeg command to convert video to audio
            String ffmpegCommand = "ffmpeg -i " + inputVideoPath + " -vn -acodec mp3 -ab 192k " + outputAudioPath;

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", ffmpegCommand);
                Process process = processBuilder.start();

                // Wait for the process to complete
                int exitCode = process.waitFor();

                // Check the exit code to determine if the conversion was successful
                if (exitCode == 0) {
                    System.out.println("Video to audio conversion successful!");
                } else {
                    System.out.println("Video to audio conversion failed. Exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
