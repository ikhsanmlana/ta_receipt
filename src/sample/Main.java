package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent viewBranch = loader.load();


        Controller controller = loader.getController();
        controller.setChoices();

        primaryStage.setTitle("AEON Receipt");
        primaryStage.setScene(new Scene(viewBranch, 500, 250));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
