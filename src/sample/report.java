package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class report {
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

    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> address = new ArrayList<String>();
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


    public void allOrders(String city){
        cityName = city;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT SUM(o.totalPrice), o.branchID, b.address FROM Orders o INNER JOIN Branch b ON o.branchID = b.branchID GROUP BY branchID;";

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                price.add(rs.getInt(1));
                id.add(rs.getInt(2));
                address.add(rs.getString(3));
            }
            ObservableList allBranch = FXCollections.observableArrayList(id);
            ObservableList branchAddress = FXCollections.observableArrayList(address);
            ObservableList branchPrice = FXCollections.observableArrayList(price);

            System.out.println(price);
            System.out.println(id);

            orders.getItems().addAll(allBranch);
            orders1.getItems().addAll(branchAddress);
            orders2.getItems().addAll(branchPrice);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
