package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


public class Receipt {
    public TextField IDField;
    public TextField itemField;
    public TextField qtyField;
    public TextField priceField;
    public Label totalLabel;
    public TextField dateField;

    public void addItem(ActionEvent actionEvent) {
    }

    @FXML
    public void finish(ActionEvent actionEvent) throws IOException {
        Parent viewBranch = FXMLLoader.load(getClass().getResource("result.fxml"));

        Scene sceneBranch = new Scene(viewBranch);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }
}
