package ATM;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

public class UserRegistrationApp {

        public static void main(String[] args) {
            // Example number
            double number = 2333333333.0;

            // Create a NumberFormat for the specified locale
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

            // Format the number with commas
            String formattedNumber = numberFormat.format(number);

            // Print the formatted number
            System.out.println("Formatted Number: " + formattedNumber);
        }
    }



