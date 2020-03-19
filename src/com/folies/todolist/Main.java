package com.folies.todolist;

import com.folies.todolist.datamodel.ToDoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        try {
            ToDoData.getInstance().storeToDoItems();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            ToDoData.getInstance().loadToDoItems();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
