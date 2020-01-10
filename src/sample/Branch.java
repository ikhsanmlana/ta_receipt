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


public class Branch {

    public Label cityName;
    public Label cityID;

    @FXML
    public void branchsubmit(ActionEvent actionEvent) throws IOException {
        Parent viewBranch = FXMLLoader.load(getClass().getResource("receipt.fxml"));

        Scene sceneBranch = new Scene(viewBranch);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }
    public void initCity(String name){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        cityName.setText(name);

        String sql = "SELECT cityID FROM City WHERE cityName = '"+name+"';";
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            statement.executeUpdate(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                cityID.setText(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
