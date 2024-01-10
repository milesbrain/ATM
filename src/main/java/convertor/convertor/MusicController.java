package convertor.convertor;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;



import java.util.List;

public class MusicController {
    @FXML
    Stage stage;
    @FXML
    TextField textField,path_to_save;
    File selectedFile;
    @FXML
    Label status;


    String outputAudioPath;
    String outputDirectoryPath;

    @FXML

    private void filemanager(ActionEvent event) {

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Video File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Video Files (*.mp4, *.mkv, *.avi)", "*.mp4", "*.mkv", "*.avi");
        fileChooser.getExtensionFilters().add(extFilter);


        selectedFile = fileChooser.showOpenDialog(stage);


        if (selectedFile != null) {

            setTextField(textField);
        } else {
            System.out.println("No video file selected.");
        }
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
        textField.setText(selectedFile.getAbsolutePath());
    }

    @FXML
    private void clickToExtract() {
        convertVideoToAudio(selectedFile);

    }


    private void convertVideoToAudio(File videoFile  ) {
        String videoFilePath = textField.getText().trim();
        outputAudioPath = videoFile.getParent() + File.separator + selectedFile.getAbsolutePath();
        outputDirectoryPath = videoFile.getParent() + File.separator + "AudioOutput";

        if (videoFilePath.isEmpty()) {
            setStatus("Please enter a video file path.");
            return;
        }

        File videoFiles = new File(videoFilePath);

        if (!videoFiles.exists()) {
            setStatus("File does not exist.");
            return;
        }

        // Start the conversion
        setStatus("Converting...");

        try {
            extractAudio();
            setStatus("Conversion complete!");
        } catch (Exception e) {
            setStatus("Error during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setStatus(String message) {
        status.setText(message);
    }


    public void OpenDirectory(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       DirectoryChooser chooser = new DirectoryChooser();
       File selectedDirectory = chooser.showDialog(stage);
       if (selectedDirectory != null){
           String audio = selectedDirectory.getAbsolutePath()+File.separator + "outputAudio.mp3";
           path_to_save.setText(audio);
       }
    }
    private void extractAudio() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File videoFile = fileChooser.showOpenDialog(null);

        if (videoFile != null) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
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
    }}

