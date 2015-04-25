package pro.zackpollard.duplicateimagefinder;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;

public class Controller {

    private int currentImage = 0;

    public void clickPreviousImage(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        Stage primaryStage = instance.getStage();

        ImageView imageView = (ImageView) primaryStage.getScene().lookup("#imageView");
        LinkedList<String> duplicate = instance.getDuplicateManager().getCurrentDuplicate();

        if(imageView != null) {

            if(--currentImage >= 0) {

                imageView.setImage(new Image(new File(duplicate.get(currentImage)).toURI().toString()));
            } else {

                ++currentImage;
            }
        } else {

            System.out.println("ImageView was null!");
        }

        Label lblLocation = (Label) primaryStage.getScene().lookup("#lblLocation");
        lblLocation.setText("Image Location: " + duplicate.get(currentImage));

        Label lblImagePos = (Label) primaryStage.getScene().lookup("#lblImagePos");
        lblImagePos.setText("Image " + (currentImage + 1) + "/" + duplicate.size());
    }

    public void clickNextImage(ActionEvent actionEvent) {


        Main instance = Main.getInstance();
        Stage primaryStage = instance.getStage();

        ImageView imageView = (ImageView) primaryStage.getScene().lookup("#imageView");
        LinkedList<String> duplicate = instance.getDuplicateManager().getCurrentDuplicate();

        if(imageView != null) {

            if(++currentImage < duplicate.size()) {

                imageView.setImage(new Image(new File(duplicate.get(currentImage)).toURI().toString()));
            } else {

                --currentImage;
            }
        } else {

            System.out.println("ImageView was null!");
        }

        Label lblLocation = (Label) primaryStage.getScene().lookup("#lblLocation");
        lblLocation.setText("Image Location: " + duplicate.get(currentImage));

        Label lblImagePos = (Label) primaryStage.getScene().lookup("#lblImagePos");
        lblImagePos.setText("Image " + (currentImage + 1) + "/" + duplicate.size());
    }

    public void clickNextDuplicate(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        DuplicateManager duplicateManager = instance.getDuplicateManager();
        duplicateManager.getNextDuplicate();
        currentImage = 1;

        Stage primaryStage = instance.getStage();

        Label lblImagePos = (Label) primaryStage.getScene().lookup("#lblDuplicatePos");
        lblImagePos.setText("Duplicate " + (duplicateManager.getCurrentDuplicateID() + 1) + "/" + duplicateManager.getTotalDuplicates());

        this.clickPreviousImage(null);
    }


    public void clickPreviousDuplicate(ActionEvent actionEvent) {

        Main instance = Main.getInstance();
        DuplicateManager duplicateManager = instance.getDuplicateManager();
        duplicateManager.getPreviousDuplicate();
        currentImage = 1;

        Stage primaryStage = instance.getStage();

        Label lblImagePos = (Label) primaryStage.getScene().lookup("#lblDuplicatePos");
        lblImagePos.setText("Duplicate " + (duplicateManager.getCurrentDuplicateID() + 1) + "/" + duplicateManager.getTotalDuplicates());

        this.clickPreviousImage(null);
    }

    public void clickKeepCurrent(ActionEvent actionEvent) {

        //TODO: Add code to remove all pictures but currently viewed picture.
    }
}