package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public enum EditTask {
    INSTANCE;
    public void editTask(Task editTask){
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        Label label = new Label("Edit task");

        Label labelOne = new Label("\nEnter task name");
        TextField textName = new TextField();
        textName.setText(editTask.getName());

        Label labelTwo = new Label("\nEnter description");
        TextField textDescription = new TextField();
        textDescription.setText(editTask.getDesc());

        Label labelThree = new Label("\nSet date");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(editTask.getDate());

        Label labelFour = new Label("\nSet priority");
        ComboBox<priority> comboBox = new ComboBox<>();
        comboBox.getItems().add(priority.HIGH);
        comboBox.getItems().add(priority.MEDIUM);
        comboBox.getItems().add(priority.LOW);
        comboBox.getSelectionModel().select(editTask.getPr());

        Button btnAdd = new Button();
        btnAdd.setText("Add");

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = textName.getText();
                String desc = textDescription.getText();
                LocalDate date = datePicker.getValue();
                priority pr = comboBox.getValue();
                Task meh = new Task(name, desc, date, pr);

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