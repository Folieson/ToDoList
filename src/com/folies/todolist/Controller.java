package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    public void initialize() {
        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 30th birthday card for Mary",
                LocalDate.of(2021, Month.JANUARY, 1));
        ToDoItem item2 = new ToDoItem("Doctor's Appointment", "See Dr. Smith at 123 Main Street. Bring Paperwork",
                LocalDate.of(2020, Month.MARCH, 25));
        ToDoItem item3 = new ToDoItem("Finish design proposal dor client", "I promised Mark I'd email website mockups by Friday 22nd April",
                LocalDate.of(2020, Month.APRIL, 22));
        ToDoItem item4 = new ToDoItem("Pick up Doug at the train station", "Dougs's arriving on March 23 on the 5:00 train",
                LocalDate.of(2020, Month.MARCH, 23));
        ToDoItem item5 = new ToDoItem("Pick up dry cleaning", "The clothes should be ready by Wednesday",
                LocalDate.of(2020, Month.FEBRUARY, 20));

        toDoItems = new ArrayList<ToDoItem>(List.of(item1,item2,item3,item4,item5));

        toDoListView.getItems().setAll(toDoItems);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void handleClickListView() {
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(selectedItem.getDetails());
        deadlineLabel.setText(selectedItem.getDeadline().toString());
    }
}
