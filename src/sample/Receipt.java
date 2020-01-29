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
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Label cityName;
    public Label cityID;
    public ChoiceBox typeChoices;
    public Label currentTime;
    public TextField cashField;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> allType = new ArrayList<String>();
    ArrayList<Integer> typeID = new ArrayList<Integer>();
    public int totalPrice;
    public String type;
    public int orderID;
    public String branchID;

    public String allItems = "";
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public void addItem(ActionEvent actionEvent) {
        if (priceField.getText().trim().isEmpty()|| qtyField.getText().trim().isEmpty()){
            System.out.println("Fill the fields");
        }
        else {
            items.add(itemField.getText());
            int price = Integer.parseInt(priceField.getText());
            int amt = Integer.parseInt(qtyField.getText());
            totalPrice += (price * amt);

            totalLabel.setText("" + totalPrice);

            System.out.println(items);

            Date date = new Date();
            date.setTime(timestamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            currentTime.setText(formattedDate);
            type = "" + typeChoices.getSelectionModel().getSelectedItem();


            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();


            String sql = "INSERT INTO Orderitem (orderItemName, typeID, quantity, itemPrice) VALUES (?,?,?,?)";
//        Statement statement = null;
            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setString(1, itemField.getText());
                preparedStatement.setInt(2, typeID.get(allType.indexOf(type)));
                preparedStatement.setInt(3, Integer.parseInt(qtyField.getText()));
                preparedStatement.setInt(4, Integer.parseInt(priceField.getText()));
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            qtyField.clear();
            priceField.clear();
            itemField.clear();
        }


    }

    @FXML
    public void finish(ActionEvent actionEvent) throws IOException {
        if (cashField.getText().trim().isEmpty() || Integer.parseInt(cashField.getText()) < totalPrice){
            System.out.println("Invalid Cash Field");
        }
        else {
            for (String s : items) {
                allItems += s + " , ";
            }
            System.out.println(allItems);
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            Date date = new Date();
            date.setTime(timestamp.getTime());
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

            String sql = "INSERT INTO Orders (orderItem, orderTime, employeeID, totalPrice, branchID) VALUES (?,?,?,?,?)";
            String sql2 = "SELECT OrderID FROM orders WHERE orderTime = '"+formattedDate+"';";
            String sql3 = "UPDATE OrderItem SET orderID = ? WHERE orderID is null";
            PreparedStatement preparedStatement = null;
            Statement statement = null;

            try {
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setString(1, allItems);
                preparedStatement.setTimestamp(2, timestamp);
                preparedStatement.setInt(3, Integer.parseInt(IDField.getText()));
                preparedStatement.setInt(4, totalPrice);
                preparedStatement.setInt(5, Integer.parseInt(cityID.getText()));
                preparedStatement.executeUpdate();

                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql2);
                while (rs.next()) {
                    orderID = (rs.getInt(1));
                }
                System.out.println(orderID);
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql3);
                preparedStatement.setInt(1, orderID);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }


            items.clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("result.fxml"));
            Parent viewBranch = loader.load();

            Scene sceneBranch = new Scene(viewBranch);

            Result controller = loader.getController();
            controller.initTop(cityName.getText(), orderID, formattedDate, Integer.parseInt(IDField.getText()));
            controller.initBot(totalPrice, Integer.parseInt(cashField.getText()));
            controller.allOrders();

            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(sceneBranch);
            window.show();


        }


    }
    public void initType(){
        List<String> listOptions;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "SELECT * FROM Type;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            statement.executeUpdate(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String choice = (resultSet.getString("typeName"));
                Integer choiceID = (resultSet.getInt("typeID"));
                allType.add(choice);
                typeID.add(choiceID);
            }
            ObservableList options = FXCollections.observableArrayList(allType);
            typeChoices.setItems(options);




        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void buttonback(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent viewBranch = loader.load();

        Scene sceneBranch = new Scene(viewBranch);

        Controller controller = loader.getController();
        controller.setChoices();

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }

    @FXML
    public void report(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("report.fxml"));
        Parent viewBranch = loader.load();

        Scene sceneBranch = new Scene(viewBranch);

        report controller = loader.getController();
//        controller.initOrderID(orderID);
        controller.allOrders(cityName.getText());

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneBranch);
        window.show();
    }
}
