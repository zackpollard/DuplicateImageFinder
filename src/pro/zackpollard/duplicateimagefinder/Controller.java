package pro.zackpollard.duplicateimagefinder;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;

public class Controller {

    public void clickImage1(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        Stage primaryStage = Main.getInstance().getStage();

        ImageView imageView = (ImageView) primaryStage.getScene().lookup("#imageView");
        LinkedList<String> duplicate = instance.getDuplicateManager().getCurrentDuplicate();

        if(imageView != null) {

            imageView.setImage(new Image(new File(duplicate.getFirst()).toURI().toString()));
        } else {

            System.out.println("ImageView was null!");
        }
        Label lblLocation = (Label) primaryStage.getScene().lookup("#lblLocation");
        lblLocation.setText("Image Location: " + duplicate.getFirst());
    }

    public void clickImage2(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        Stage primaryStage = Main.getInstance().getStage();

        ImageView imageView = (ImageView) primaryStage.getScene().lookup("#imageView");
        LinkedList<String> duplicate = instance.getDuplicateManager().getCurrentDuplicate();

        if(imageView != null) {

            imageView.setImage(new Image(new File(duplicate.get(1)).toURI().toString()));
        } else {

            System.out.println("ImageView was null!");
        }
        Label lblLocation = (Label) primaryStage.getScene().lookup("#lblLocation");
        lblLocation.setText("Image Location: " + duplicate.get(1));
    }

    public void clickNextDuplicate(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        instance.getDuplicateManager().getNextDuplicate();
        this.clickImage1(null);
    }

    public void clickDeleteCurrent(ActionEvent actionEvent) {
    }
}