package sample;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable, Serializable {


    public ListView<Task> doneList;
    public ListView<Task> inProgressList;
    public ListView<Task> toDoList;
    public MenuItem exitMenu;

    public MenuItem SaveMenu;
    public MenuItem ImportMenu;
    public MenuItem OpenMenu;
    public MenuItem ExportMenu;

    public Button addButton;
    public Button btnMoveToInProgress;
    public Button btnMoveToInProgressBack;
    public Button btnMoveToDone;
    public Button btnMoveToToDo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Delete task");
        MenuItem menuItem2 = new MenuItem("Edit task");
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        menuItem1.setOnAction(event -> {
            final int selectedIdx = toDoList.getSelectionModel().getSelectedIndex();
            toDoList.getItems().remove(selectedIdx);
        });

        menuItem2.setOnAction(event -> {
            final int selectedIdx = toDoList.getSelectionModel().getSelectedIndex();
            Task editTask = toDoList.getItems().get(selectedIdx);
            EditTask.INSTANCE.editTask(editTask);
        });

        toDoList.setOnContextMenuRequested(event -> contextMenu.show(toDoList,event.getScreenX(),event.getScreenY()));

        btnMoveToInProgress.setOnAction(event -> {
            Task potential = toDoList.getSelectionModel().getSelectedItem();
            if (potential != null) {
                toDoList.getSelectionModel().clearSelection();
                toDoList.getItems().remove(potential);
                potential.setListName("inProgressList");
                inProgressList.getItems().add(potential);
            }
        });

        btnMoveToDone.setOnAction(event -> {
            Task potential = inProgressList.getSelectionModel().getSelectedItem();
            if (potential != null) {
                inProgressList.getSelectionModel().clearSelection();
                inProgressList.getItems().remove(potential);
                potential.setListName("doneList");
                doneList.getItems().add(potential);
            }
        });
        btnMoveToInProgressBack.setOnAction(event -> {
            Task potential = doneList.getSelectionModel().getSelectedItem();
            if (potential != null) {
                doneList.getSelectionModel().clearSelection();
                doneList.getItems().remove(potential);
                potential.setListName("inProgressList");
                inProgressList.getItems().add(potential);
            }
        });
        btnMoveToToDo.setOnAction(event -> {
            Task potential = inProgressList.getSelectionModel().getSelectedItem();
            if (potential != null) {
                inProgressList.getSelectionModel().clearSelection();
                inProgressList.getItems().remove(potential);
                potential.setListName("toDoList");
                toDoList.getItems().add(potential);
            }
        });
    }

    public void newTask() {

        NewTask.INSTANCE.createNewTask(this);
        makeColor(toDoList);
    }

    void makeColor(ListView<Task> list){
        list.setCellFactory(p -> {

            @SuppressWarnings("unchecked")
            ListCell<Task> cell = new ListCell<>() {

                Tooltip tooltip = new Tooltip();

                protected void updateItem(Task t, boolean bln) {
                    super.updateItem(t, bln);
                    if (bln || t == null) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(t.getName());
//                        Color col = t.getColor();
                        Color color;
                        switch (t.getPr()){
                            case HIGH:
                                color = Color.RED;
                                break;
                            case MEDIUM:
                                color = Color.ORANGE;
                                break;
                            default:
                                color = Color.DARKGREEN;
                                break;
                        }
                        setTextFill(color);
                        tooltip.setText(t.getDesc());
                        setTooltip(tooltip);
                    }
                }
            };
            return cell;
        });
    }

    public void moveToInProgressRight() {
        Task potential = toDoList.getSelectionModel().getSelectedItem();
        makeColor(inProgressList);
    }

    public void moveToToDoBack() {
        Task potential = inProgressList.getSelectionModel().getSelectedItem();
        makeColor(toDoList);
    }

    public void moveToDone() {
        Task potential = inProgressList.getSelectionModel().getSelectedItem();
        makeColor(doneList);
    }

    public void moveToInProgressBack() {
        Task potential = doneList.getSelectionModel().getSelectedItem();
        makeColor(inProgressList);

    }

    public void close() {
        System.exit(0);
    }


    public void showAlert() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Lukasz Szumiec");
        a.show();
    }

    public void importFile() {
            ImportFile.importTable(this);
    }

    public void exportFile() throws FileNotFoundException {
        ExportFile exportFile = new ExportFile();
        exportFile.export(this);
    }

    List<String[]> toStringList(){
        ObservableList<Task> list1 = toDoList.getItems();
        ObservableList<Task> list2 = inProgressList.getItems();
        ObservableList<Task> list3 = doneList.getItems();

        List<String[]> stringList = new ArrayList<>();
        for (Task task:
                list1) {
            String[] s = task.toStringtab("toDoList");
            stringList.add(s);
        }
        for (Task task:
                list2) {
            String[] s = task.toStringtab("inProgressList");
            stringList.add(s);
        }
        for (Task task:
                list3) {
            String[] s = task.toStringtab("doneList");
            stringList.add(s);
        }
        return stringList;
    }
    JSONArray makeJSONArray(){
        ObservableList<Task> list1 = toDoList.getItems();
        ObservableList<Task> list2 = inProgressList.getItems();
        ObservableList<Task> list3 = doneList.getItems();

        JSONArray jsonArray = new JSONArray();

        for (Task task :
                list1) {
            jsonArray.add(task.makeJObject());
        }
        for (Task task :
                list2) {
            jsonArray.add(task.makeJObject());
        }
        for (Task task :
                list3) {
            jsonArray.add(task.makeJObject());
        }

        return jsonArray;
    }
    @FXML
    void serialize(){
        ArrayList<ArrayList> lists = new ArrayList<>();
        ArrayList<Task> toDoArrayList = new ArrayList<>(toDoList.getItems());
        ArrayList<Task> inProgressArrayList = new ArrayList<>(inProgressList.getItems());
        ArrayList<Task> doneArrayList = new ArrayList<>(doneList.getItems());
        lists.add(toDoArrayList);
        lists.add(inProgressArrayList);
        lists.add(doneArrayList);

        try{
            File file = new File("C:\\Users\\lukas\\Downloads\\meh");
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\lukas\\Downloads\\meh\\file");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(toDoArrayList);
            objectOutputStream.writeObject(inProgressArrayList);
            objectOutputStream.writeObject(doneArrayList);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void deserialize(){
        ArrayList list = new ArrayList<>();
        ArrayList list2 = new ArrayList<>();
        ArrayList list3 = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream("file");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            list = (ArrayList) objectInputStream.readObject();
            list2 = (ArrayList) objectInputStream.readObject();
            list3 = (ArrayList) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        buildTables buildTables = new buildTables();
        buildTables.build(this, list, list2, list3);
    }
}