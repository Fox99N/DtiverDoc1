package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import

/**
 * Created by fox on 29.01.18.
 */
public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DriverDoc");
        primaryStage.setScene(new Scene(root,900, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
