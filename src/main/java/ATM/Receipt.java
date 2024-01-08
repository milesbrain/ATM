package ATM;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Receipt implements Initializable {
    @FXML
    Label SenderAccNo, ReferenceNo, SenderName, ReceiverName;
    @FXML

    Label Amount;
    @FXML
    Label Date;
    @FXML
    Label ReceiverAccNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}
