package cs1302.omega;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import java.util.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.application.Platform;



public class App extends Application {

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}


    Button loveIt;
    Button hateIt;
    Button favPets;
    OmegaPet pet  = new OmegaPet();
    String dogUrl = "https://api.thedogapi.com/v1/images/search";
    String catUrl = "https://api.thecatapi.com/v1/images/search";

    OmegaPet dogGenerator  = new OmegaPet();
    OmegaPet catGenerator  = new OmegaPet();
    ArrayList<Image> petImageHolder = new ArrayList<Image>();
    ImageView [] petHolder  = new ImageView[100];
    VBox mainAppContainer = new VBox();
    ImageView petPic = animalRotater();
    int imageCounter = 0;
    GridPane gridpane = new GridPane();
    AnchorPane anchorPane = new AnchorPane();


    /**
     *This external application allows the user to like and dislike different photos/
     * of cats and dogs. Will the user ever find their match? We will never know :}
     * @param stage of type Stage.
     */
    @Override
    public void start(Stage stage) {

        generateAnimalPic();
        animalDisplay();

        /////scene1 code//////////////
        mainAppContainer.setPrefHeight(400.0);
        mainAppContainer.setPrefWidth(400.0);
        petPic = animalRotater();
        mainAppContainer.getChildren().addAll(appTitle(),buttonHolder(), petPic);
        Scene scene1 = new Scene(mainAppContainer);

        /////stage code/////////////////
        stage.setTitle("Pet Tinder!");
        stage.setScene(scene1);
        stage.sizeToScene();
        stage.show();

    } // start

    /**
     *This method returns the label containing the app label.
     *@return Label object
     */

    public Label  appTitle() {

        Label petTinder = new Label("PET TINDER");
        petTinder.setFont(Font.loadFont("file:resources/Rajdhani-Medium.ttf", 20));
        petTinder.setMaxWidth(Double.MAX_VALUE);
        anchorPane.setLeftAnchor(petTinder, 0.0);
        anchorPane.setRightAnchor(petTinder, 0.0);
        petTinder.setAlignment(Pos.CENTER);
        petTinder.setTextFill(Color.PINK);
        return petTinder;


    } //appTitle

    /**
     *This method returns the hbox that contains the buttons for the app.
     *@return an HBox.
     */

    public HBox buttonHolder() {
        loveIt = new Button("Love it");
        loveIt.setFont(Font.loadFont("file:resources/Rajdhani-Medium.ttf", 20));
        loveIt.setOnAction(e -> {
            Platform.runLater(() -> {
                petPic.setImage(imageUpdate());
            });
        });

        loveIt.setStyle("-fx-background-color: darkseagreen");
        hateIt = new Button("Hate it");
        hateIt.setFont(Font.loadFont("file:resources/Rajdhani-Medium.ttf", 20));
        //hate it configuration
        hateIt.setOnAction(e -> {
            Platform.runLater(() -> {
                petPic.setImage(imageUpdate());
            });
        });
        hateIt.setStyle("-fx-background-color: darkseagreen");

        HBox hbox = new HBox();
        hbox.setHgrow(loveIt, Priority.ALWAYS);
        hbox.setHgrow(hateIt, Priority.ALWAYS);
        hbox.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,
              CornerRadii.EMPTY,
              Insets.EMPTY)));
        hbox.setSpacing(15);
        hbox.setPadding(new Insets(15, 20, 5, 10));
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(loveIt,hateIt);
        return hbox;

    } //buttonHolder

    /**
     *This method activates the dog and cat api, and generates an arraylist of picures.
     */

    public void generateAnimalPic() {

        if (petImageHolder.size() < 5) {
            //adds photos to array list
            for (int i = 0; i < 20; i++) {
                petImageHolder.add(catGenerator.petParser(catUrl));
                petImageHolder.add(dogGenerator.petParser(dogUrl));
            } //for


        } //if

    } //generateAnimalPic

    /**
     * This method creates imageView objects out of the arraylist of images.
     */

    public void  animalDisplay () {

        for (int i = 0; i < petImageHolder.size() ; i++ ) {

            petHolder[i]  =  new ImageView(petImageHolder.get(i));
            petHolder[i].setFitWidth(400);
            petHolder[i].setFitHeight(400);
        } //for

    } //animalRotater

    /**
     * This method returns the first image seen on the stage.
     * @return ImageView object.
     */
    public ImageView animalRotater() {

        int i = 0;

        if ( i < petHolder.length ) {

            return petHolder[i++];


        } else {

            i = 0;

            return petHolder[i++];
        } //else

    } //animalRotater



    /**
     * This method rotates the images when a button is clicked.
     * @return a new Image object containing the new image.
     */

    public Image imageUpdate() {
        if (imageCounter < petImageHolder.size()) {

            return petImageHolder.get(imageCounter++);
        } else {

            imageCounter = 0;

            petImageHolder.clear();

            generateAnimalPic();
            //recursion utlized to re enter method if condition isnt reached.
            return imageUpdate();

        }

    } //imageUpdate



} // App
