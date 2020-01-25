package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {
    public TextField cityField;
    public Label cityLabel;
    public Label textLabel;
    public ChoiceBox cityChoice;
    ArrayList<String> allCity = new ArrayList<String>();
//    ArrayList<Integer> typeID = new ArrayList<Integer>();
    String city;


    public void showID(ActionEvent actionEvent) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT cityID FROM City WHERE cityName = '"+cityField.getText()+"';";
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            statement.executeUpdate(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                textLabel.setText(resultSet.getString(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void button(ActionEvent actionEvent) throws IOException {

//        Parent viewBranch = FXMLLoader.load(getClass().getResource("branch .fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("receipt.fxml"));
        Parent viewBranch = loader.load();

        Scene sceneBranch = new Scene(viewBranch);
        city = "" + cityChoice.getSelectionModel().getSelectedItem();

        Receipt controller = loader.getController();
        controller.initType();
        controller.initCity(city);


        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();

    }
    public void setChoices(){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT * FROM city;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            statement.executeUpdate(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String choice = (resultSet.getString("cityName"));
//                Integer choiceID = (resultSet.getInt("cityID"));
                allCity.add(choice);
//                typeID.add(choiceID);
            }
            ObservableList options = FXCollections.observableArrayList(allCity);
            cityChoice.setItems(options);




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
