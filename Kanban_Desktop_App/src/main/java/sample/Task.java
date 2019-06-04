package sample;

import javafx.scene.paint.Color;
//import org.json.JSONObject;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String name;
    private String desc;
    private LocalDate date;
    private priority pr;
    private String listName;
    private boolean check = false;

    public Task(String name, String desc, LocalDate localDate, priority pr, String listName) {
        this.name = name;
        this.desc = desc;
        this.date = localDate;
        this.pr = pr;
        this.listName = listName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name=" + name +
                ", desc=" + desc +
                ", date=" + date +
                ", pr=" + pr +
                ", listName=" + listName +
                '}';
    }

    Task(String name, String desc, LocalDate date, priority setPR){
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.pr = setPR;
        this.listName = "toDoList";

    }
    Task(){
        this.name = "Empty";
        this.desc = "";
        this.date = null;
        this.pr = null;
    }

    Task(Task potential, String listName) {
        this.listName = listName;
        this.name = potential.name;
        this.desc = potential.desc;
//        this.color = potential.color;
        this.date = potential.date;
        this.pr = potential.pr;
    }

    String getName(){return name;}

    String getDesc(){return desc;}

    String getListName(){return listName;}

    boolean getCheck(){
        return check;
    }

//    public Color getColor() {
//        return color;
//    }

    public void setCheck() {
        this.check = true;
    }

    public priority getPr(){return this.pr; }

    public LocalDate getDate(){return this.date;}

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String[] toStringtab(String listname) {

        System.out.println(listname);
        String[] strings = {listname, name, desc, date.toString(), pr.toString()};
        return strings;
    }
    JSONObject makeJObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", desc);
        jsonObject.put("date", date.toString());
        jsonObject.put("pr", pr.toString());
        jsonObject.put("listName", listName);
        return jsonObject;
    }
}
