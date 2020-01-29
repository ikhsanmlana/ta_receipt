package connectivity;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
private static Connection connection;
    public Connection getConnection(){
        if (connection == null) {
            String dbName = "13_sushidb";
//            String user = "root";
//            String password = "";

            String user = "IKH7993";
            String password = "8bnjsfcp";

            try {
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection("jdbc:mysql://dbta.1ez.xyz/13_sushidb", user, password);
//                connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName ,user,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return connection;
    }
}
