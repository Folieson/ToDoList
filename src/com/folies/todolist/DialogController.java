package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoData;
import com.folies.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescriptionTextField;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private DatePicker deadlineDatePicker;

    public void processResults() {
        String shortDescription = shortDescriptionTextField.getText().trim();
        String details = detailsTextArea.getText().trim();
        LocalDate deadline = deadlineDatePicker.getValue();

        ToDoData.getInstance().addToDoItem(new ToDoItem(shortDescription, details, deadline));
    }
}
