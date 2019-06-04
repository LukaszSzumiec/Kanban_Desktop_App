package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public enum NewTask {
    INSTANCE;
    public void createNewTask(Controller controller){
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        Label label = new Label("Create new task");

        Label labelOne = new Label("\nEnter task name");
        TextField textName = new TextField();

        Label labelTwo = new Label("\nEnter description");
        TextField textDescription = new TextField();

        Label labelThree = new Label("\nSet date");
        DatePicker datePicker = new DatePicker();

        Label labelFour = new Label("\nSet priority");
        ComboBox<priority> comboBox = new ComboBox<>();
        comboBox.getItems().add(priority.HIGH);
        comboBox.getItems().add(priority.MEDIUM);
        comboBox.getItems().add(priority.LOW);

        Button btnAdd = new Button();
        btnAdd.setText("Add");

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = textName.getText();
                if(name.length()==0) {
                    Task meh = new Task();
                    controller.toDoList.getItems().add(meh);
                }
                else {
                    String desc = textDescription.getText();
                    LocalDate date = datePicker.getValue();
                    priority pr = comboBox.getValue();
                    Task meh = new Task(name, desc, date, pr);
                    controller.toDoList.getItems().add(meh);
                }
                stage.close();
            }
        });

        Scene scene = new Scene(box,450,350);

        box.getChildren().add(label);

        box.getChildren().add(labelOne);
        box.getChildren().add(textName);
        box.getChildren().add(labelTwo);
        box.getChildren().add(textDescription);
        box.getChildren().add(labelThree);
        box.getChildren().add(datePicker);
        box.getChildren().add(labelFour);
        box.getChildren().add(comboBox);
        box.getChildren().add(btnAdd);
        stage.setScene(scene);
        stage.show();
    }
}
