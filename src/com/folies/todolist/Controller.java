package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoData;
import com.folies.todolist.datamodel.ToDoItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainBorderPain;

    @FXML
    private ListView<ToDoItem> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    public void initialize() {
        contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem editMenuItem = new MenuItem("Edit");
        deleteMenuItem.setOnAction(event -> {
            ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
            deleteItem(selectedItem);
        });
        editMenuItem.setOnAction(event -> {
            ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
            showEditItemDialog(selectedItem);
        });
        contextMenu.getItems().addAll(deleteMenuItem, editMenuItem);
        toDoListView.getSelectionModel().selectedItemProperty().addListener((observableValue, toDoItem, newValue) -> {
            if (newValue != null) {
                itemDetailsTextArea.setText(newValue.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMMM yyyy");
                deadlineLabel.setText(df.format(newValue.getDeadline()));
            }
        });

        toDoListView.setItems(ToDoData.getInstance().getToDoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
        toDoListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadline().isEqual(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener((observable, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });

                return cell;
            }
        });
    }

    @FXML
    private void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPain.getScene().getWindow());
        dialog.setTitle("Add New ToDo Item");
        dialog.setHeaderText("Use this dialog to create new toDo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println("Couldn't load the dialog");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController dialogController = fxmlLoader.getController();
            ToDoItem newItem = dialogController.processResults();
            toDoListView.getSelectionModel().select(newItem);
        }
    }

    private void showEditItemDialog(ToDoItem item) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPain.getScene().getWindow());
        dialog.setTitle("Edit ToDo Item");
        dialog.setHeaderText("Use this dialog to edit selected toDo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        DialogController dialogController = fxmlLoader.getController();
        dialogController.initWith(item);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoItem newItem = dialogController.processResults();
            toDoListView.getSelectionModel().select(newItem);
        }
    }

    private void deleteItem(ToDoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ToDo Item");
        alert.setHeaderText("Delete item: " + item.getShortDescription());
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    private void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit from ToDoList");
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Platform.exit();
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }
}
