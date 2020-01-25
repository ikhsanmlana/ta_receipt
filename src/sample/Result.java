package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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


public class Result {
    public Label nameLabel;
    public Label dateLabel;
    public Label orderLabel;
    public Label cashLabel;
    public Label kembalianLabel;
    public Label bayarLabel;
    public Label totalLabel;
    public ListView orders;

    public void buttonback(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("receipt.fxml"));
        Parent viewBranch = loader.load();

        Scene sceneBranch = new Scene(viewBranch);

        Receipt controller = loader.getController();
        controller.initType();

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }
}
