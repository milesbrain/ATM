package ATM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

public class TransferController implements Initializable {
    @FXML
    ComboBox<String> combobox;
    @FXML
    ComboBox<String> combo;
    @FXML
    TextField AccountNoSender;
    @FXML
    TextField AmountSend;
    @FXML
    TextField RecipientAccNo;


    private String senderName;
    private String senderAccNo;
    private double transferAmount;
    private String receiverName;
    private String receiverAccNo;
    private String referenceNo;
    String formattedDate;
    String senderAccountNo ;
    String recipientAccountNo ;
    double amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combobox.getItems().addAll("MILES BANK", "UBA BANK", "ACCESS BANK", "ZENITH BANK", "FIDELITY BANK");
        initializeCombo();
        setNumericTextFormatter(AccountNoSender);
        setNumericTextFormatter( AmountSend);
        setNumericTextFormatter(RecipientAccNo);
    }

    private void setNumericTextFormatter(TextField textField) {
        TextFormatter<Object> textFormatter = new TextFormatter<>(this::filterNumericInput);
        textField.setTextFormatter(textFormatter);
    }

    private TextFormatter.Change filterNumericInput(TextFormatter.Change change) {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*")) {
            return change;
        } else {
            return null;
        }
    }

    public void initializeCombo() {
        combo.getItems().addAll("MILES BANK", "UBA BANK", "ACCESS BANK", "ZENITH BANK", "FIDELITY BANK");
    }

    public void sendmoney(ActionEvent event) {
        String senderBank = combobox.getValue();
        String recipientBank = combo.getValue();
        senderAccountNo = AccountNoSender.getText();
        recipientAccountNo = RecipientAccNo.getText();


        try {
            amount = Double.parseDouble(AmountSend.getText());
        } catch (NumberFormatException e) {
            showalert("Invalid amount. Please enter a valid number.");
            return;
        }


        senderName = getSenderName(senderAccountNo);
        senderAccNo = senderAccountNo;
        transferAmount = amount;
        try {
            if (recipientBank==null||senderBank==null) {
                System.out.println("Kindly input the bank");
            }
            else
            {
                if (recipientBank.equals("MILES BANK") && senderBank.equals("MILES BANK")){
                 if (senderAccountNo.equals(recipientAccountNo)) {
                     showalert("Sender and Receiver account numbers cannot be the same.");
                 } else
                 {
                     if (!(senderAccountNo == null) || (recipientAccountNo == null)){
                         if (transferMoney(senderAccountNo, recipientAccountNo, amount)) {
                             System.out.println("Transfer successful!");
                             showalert("Transfer successful!");
                             receiverName = getReceiverName(recipientAccountNo);
                             receiverAccNo = recipientAccountNo;

                             referenceNo = generateReferenceNumber();
                             LocalDate currentDate = LocalDate.now();
                             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                             formattedDate = LocalDateTime.now().format(formatter);


                             openReceiptScene(formattedDate);
                         } else {

                             showalert("Transfer failed");
                         }
                     }else{
                         showalert("Kindly Insert Your Information");
                     }
                 }
             }} {

                     }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean transferMoney(String senderAccountNo, String recipientAccountNo, double amount) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newaccount", "root", "Godbless101")) {

            if (!accountExists(connection, recipientAccountNo)) {
                showalert("Recipient's account not found. Transfer failed.");
                return false;
            }

            String updateSenderQuery = "UPDATE balance_table_customerid SET balance = balance - ? WHERE CustomerID = ?";
            String updateRecipientQuery = "UPDATE balance_table_customerid SET balance = balance + ? WHERE CustomerID = ?";

            try (PreparedStatement updateSenderStmt = connection.prepareStatement(updateSenderQuery);
                 PreparedStatement updateRecipientStmt = connection.prepareStatement(updateRecipientQuery)) {

                connection.setAutoCommit(false);

                updateSenderStmt.setDouble(1, amount);
                updateSenderStmt.setString(2, senderAccountNo);
                updateSenderStmt.executeUpdate();

                updateRecipientStmt.setDouble(1, amount);
                updateRecipientStmt.setString(2, recipientAccountNo);
                updateRecipientStmt.executeUpdate();

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean accountExists(Connection connection, String accountNo) throws SQLException {
        String query = "SELECT COUNT(*) FROM balance_table_customerid WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, accountNo);
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    private String getSenderName(String accountNo) {

        String query = "SELECT Surname,First_Name FROM registration WHERE CustomerID = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newaccount", "root", "Godbless101");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, accountNo);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Surname") + " " + resultSet.getString("First_Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getReceiverName(String accountNo) {
        String query = "SELECT Surname,First_Name FROM registration WHERE CustomerID = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newaccount", "root", "Godbless101");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, accountNo);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Surname") + " " + resultSet.getString("First_Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateReferenceNumber() {

        Random random = new Random();
        StringBuilder referenceNumber = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            referenceNumber.append(random.nextInt(15));
        }
        return referenceNumber.toString();
    }

    private void openReceiptScene(String formattedDate) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OpenAnotherScene.fxml"));
            Parent root = loader.load();

            Receipt receiptController = loader.getController();

            receiptController.SenderName.setText(senderName);
            receiptController.SenderAccNo.setText(senderAccNo);
            receiptController.Amount.setText(String.valueOf(transferAmount));
            receiptController.ReceiverName.setText(receiverName);
            receiptController.ReceiverAccNo.setText(receiverAccNo);
            receiptController.ReferenceNo.setText(referenceNo);
            receiptController.Date.setText(formattedDate);


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image("myAtmLogo.png"));
            stage.setTitle("Receipt");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showalert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return false;
    }
}
