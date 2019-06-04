package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import org.json.JSONArray;
//import org.json.JSONObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ExportFile {

    void export(Controller controller) throws FileNotFoundException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV file", "*.csv"),
                new FileChooser.ExtensionFilter("JSON file", "*.json")
        );
        File file = fileChooser.showSaveDialog(stage);
        if(file != null) {
            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(extension);
            if (extension.equals(".csv")) {
                List<String[]> strings = controller.toStringList();
                try (PrintWriter pw = new PrintWriter(file)) {
                    strings.stream()
                            .map(this::convertToCSV)
                            .forEach(pw::println);
                }
            }
            if (extension.equals(".json")) {
                try (FileWriter fileWriter = new FileWriter(fileName)){
                   fileWriter.write(this.convertToJSON(controller).toJSONString());
                   fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("DONE");
            }
        }
    }

    private JSONArray convertToJSON(Controller controller) {
        JSONArray jsonArray = new JSONArray();
        jsonArray = controller.makeJSONArray();
        System.out.println(jsonArray);
        return jsonArray;
    }


    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }
    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}