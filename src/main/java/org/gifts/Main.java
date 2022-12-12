package org.gifts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/org.gifts/main.fxml"));
        Scene scene = new Scene(root);

        Image image = new Image("D:\\Yuliia\\Java\\NewYearGift\\src\\main\\java\\org\\gifts\\images\\box-removebg-preview.png");
        stage.getIcons().add(image);
        stage.setScene(scene);

        stage.setTitle("Candy store");
        stage.setWidth(700);
        stage.setHeight(500);
        stage.show();
    }
}
