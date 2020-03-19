package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoData;
import com.folies.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private ListView<ToDoItem> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    public void initialize() {
        toDoListView.getSelectionModel().selectedItemProperty().addListener((observableValue, toDoItem, newValue) -> {
            if (newValue != null) {
                itemDetailsTextArea.setText(newValue.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMMM yyyy");
                deadlineLabel.setText(df.format(newValue.getDeadline()));
            }
        });

        toDoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
    }
}
