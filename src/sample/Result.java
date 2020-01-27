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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public String cityName;
    public int kembalian;
    public int order;
    public ListView orders1;
    public ListView orders2;

    ArrayList<Integer> qty = new ArrayList<Integer>();
    ArrayList<String> item = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();

    public void buttonback(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("receipt.fxml"));
        Parent viewBranch = loader.load();

        Scene sceneBranch = new Scene(viewBranch);

        Receipt controller = loader.getController();
        controller.initType();
        controller.initCity(cityName);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }

    public void initTop(String city, int orderID, String date, int employeeID){
        cityName = city;
        orderLabel.setText(String.valueOf(orderID));
        dateLabel.setText(date);
        order = orderID;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT employeeName FROM employee WHERE employeeID = '"+employeeID+"' ;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            statement.executeUpdate(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                nameLabel.setText(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void initBot(int total, int bayar){
        totalLabel.setText(String.valueOf(total));
        bayarLabel.setText(String.valueOf(bayar));
        cashLabel.setText(String.valueOf(bayar));

        kembalian = bayar - total;
        kembalianLabel.setText(String.valueOf(kembalian));

    }

    public void allOrders(){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT orderItemName, quantity, itemPrice FROM orderItem WHERE orderID = '"+order+"' ORDER BY itemPrice ASC;";

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                item.add(rs.getString(1));
                qty.add(rs.getInt(2));
                price.add(rs.getInt(3));
            }
            ObservableList amount = FXCollections.observableArrayList(qty);
            ObservableList orderItems = FXCollections.observableArrayList(item);
            ObservableList itemPrice = FXCollections.observableArrayList(price);

            orders.getItems().addAll(amount);
            orders1.getItems().addAll(orderItems);
            orders2.getItems().addAll(itemPrice);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
