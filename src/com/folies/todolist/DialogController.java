package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoData;
import com.folies.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    private ToDoItem itemToEdit = null;

    @FXML
    private TextField shortDescriptionTextField;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private DatePicker deadlineDatePicker;

    public ToDoItem processResults() {
        String shortDescription = shortDescriptionTextField.getText().trim();
        String details = detailsTextArea.getText().trim();
        LocalDate deadline = deadlineDatePicker.getValue();
        ToDoItem newItem = new ToDoItem(shortDescription, details, deadline);
        if (itemToEdit != null) {
            ToDoData.getInstance().replaceToDoItem(itemToEdit, newItem);
        } else {
            ToDoData.getInstance().addToDoItem(newItem);
        }
        return newItem;
    }

    public void initWith(ToDoItem item) {
        itemToEdit = item;
        shortDescriptionTextField.setText(item.getShortDescription());
        detailsTextArea.setText(item.getDetails());
        deadlineDatePicker.setValue(item.getDeadline());
    }
}
