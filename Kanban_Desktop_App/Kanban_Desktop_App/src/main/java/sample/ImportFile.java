package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

class ImportFile {
    static void importTable(Controller controller) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("JSON or CSV", "*.csv", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if(file==null)
            return;
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(extension);
        if(extension.equals(".csv")){
            importCSV(file, controller);
        }
        if(extension.equals(".json")){
            importJSON(fileName, controller);
        }
    }

    private static void importCSV(File file, Controller controller) {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
//                System.out.println(line);
//
//                System.out.println(Arrays.toString(line.split(cvsSplitBy)));
                System.out.println();
                String[] list = line.split(",");
                String name = list[1];
                String desc = list[2];
                String date = list[3];
                String prString = list[4];
                String listName = list[0];

                priority pr;
                switch (prString){
                    case "LOW":
                        pr = priority.LOW;
                        break;
                    case "MEDIUM":
                        pr = priority.MEDIUM;
                        break;
                    default:
                        pr = priority.HIGH;
                        break;
                }
                LocalDate localDate =  LocalDate.parse(date);
                Task meh = new Task(name, desc, localDate, pr, listName);
                switch (listName) {
                    case "toDoList":
                        controller.toDoList.getItems().add(meh);
                        break;
                    case "inProgressList":
                        controller.inProgressList.getItems().add(meh);
                        break;
                    case "doneList":
                        controller.doneList.getItems().add(meh);
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.makeColor(controller.toDoList);
        controller.makeColor(controller.inProgressList);
        controller.makeColor(controller.doneList);
    }

    private static void importJSON(String fileName, Controller controller) {

            JSONParser jsonParser = new JSONParser();

            try (FileReader reader = new FileReader(fileName))
            {
                Object obj = jsonParser.parse(reader);

                JSONArray list = (JSONArray) obj;


                list.forEach(  task -> parseTask( (JSONObject) task, controller ) );
                controller.makeColor(controller.toDoList);
                controller.makeColor(controller.inProgressList);
                controller.makeColor(controller.doneList);

            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
    }
    private static void parseTask(JSONObject object, Controller controller) {

        String name = (String) object.get("name");

        String desc = (String) object.get("description");

        String date = (String) object.get("date");

        String pr = (String) object.get("pr");

        String listName = (String) object.get("listName");

        priority prior;
        switch (pr) {
            case "LOW":
                prior = priority.LOW;
                break;
            case "MEDIUM":
                prior = priority.MEDIUM;
                break;
            default:
                prior = priority.HIGH;
                break;
        }

        LocalDate localDate = LocalDate.parse(date);
        Task meh = new Task(name, desc, localDate, prior, listName);
        switch (listName) {
            case "toDoList":
                controller.toDoList.getItems().add(meh);
                break;
            case "inProgressList":
                controller.inProgressList.getItems().add(meh);
                break;
            case "doneList":
                controller.doneList.getItems().add(meh);
                break;
        }
    }
}
