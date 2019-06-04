package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class buildTables {
    void build(Controller controller, ArrayList list, ArrayList list2, ArrayList list3){

        for (Object o :
                list) {
            Task task = buildTask(o, controller);
            controller.toDoList.getItems().add(task);
        }
        controller.makeColor(controller.toDoList);

        for (Object o :
                list2) {
            Task task = buildTask(o, controller);
            controller.inProgressList.getItems().add(task);
        }
        controller.makeColor(controller.inProgressList);

        for (Object o :
                list3) {
            Task task = buildTask(o, controller);
            controller.doneList.getItems().add(task);
        }
        controller.makeColor(controller.doneList);
    }

    private Task buildTask(Object o, Controller controller) {
        String s = o.toString();
        int firstEq = s.indexOf("=") + 1;
        int firstComa = s.indexOf(",");

        int scndEq = s.indexOf("=", firstComa)+ 1;
        int scndComa = s.indexOf(",", scndEq);

        int thirdEq = s.indexOf("=", scndComa)+ 1;
        int thirdComa = s.indexOf(",", thirdEq);

        int forthEq = s.indexOf("=", thirdComa)+ 1;
        int forthComa = s.indexOf(",", forthEq);

        int fifthEq = s.indexOf("=", forthComa)+ 1;
        int fifthSign = s.indexOf("}", fifthEq);

        String name = s.substring(firstEq,firstComa);
        String desc = s.substring(scndEq,scndComa);
        String date = s.substring(thirdEq,thirdComa);
        String prString = s.substring(forthEq,forthComa);
        String listName = s.substring(fifthEq, fifthSign);
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
//        System.out.println(name);
//        System.out.println(desc);
        return meh;

    }
}
