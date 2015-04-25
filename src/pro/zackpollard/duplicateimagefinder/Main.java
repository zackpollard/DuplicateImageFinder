package pro.zackpollard.duplicateimagefinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private Stage stage;
    private static Main instance;
    private DuplicateManager duplicateManager;

    @Override
    public void start(Stage primaryStage) throws Exception {

        instance = this;
        this.stage = primaryStage;
        this.duplicateManager = new DuplicateManager(this);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Image Duplicate Finder!");
        primaryStage.setScene(new Scene(root, 657, 650));
        primaryStage.show();

        ImageView imageView = (ImageView) primaryStage.getScene().lookup("#imageView");
        Duplicate duplicate = duplicateManager.getCurrentDuplicate();
        if(imageView != null) {
            imageView.setImage(new Image(new File(duplicate.getKey()).toURI().toString()));
        } else {

            System.out.println("ImageView was null!");
        }
        Label lblLocation = (Label) primaryStage.getScene().lookup("#lblLocation");
        lblLocation.setText("Image Location: " + duplicate.getKey());
    }

    public Stage getStage() {

        return stage;
    }

    public DuplicateManager getDuplicateManager() {

        return duplicateManager;
    }

    public static Main getInstance() {

        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
