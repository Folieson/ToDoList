package com.folies.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDoData {
    private static ToDoData instance = new ToDoData();
    private static String fileName = "ToDoListItems.txt";

    private ObservableList<ToDoItem> toDoItems;
    private DateTimeFormatter formatter;

    private ToDoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static ToDoData getInstance() {
        return instance;
    }

    public ObservableList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public void addToDoItem(ToDoItem toDoItem) {
        toDoItems.add(toDoItem);
    }

    public void loadToDoItems() throws IOException {
        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(toDoItem);
            }
        }
    }

    public void storeToDoItems() throws IOException {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (ToDoItem toDoItem : toDoItems) {
                bufferedWriter.write(String.format("%s\t%s\t%s",
                        toDoItem.getShortDescription(),
                        toDoItem.getDetails(),
                        toDoItem.getDeadline().format(formatter)));
                bufferedWriter.newLine();
            }
        }
    }

    public void replaceToDoItem(ToDoItem oldItem, ToDoItem newItem) {
        int indexOfOldItem = toDoItems.indexOf(oldItem);
        toDoItems.set(indexOfOldItem, newItem);
    }

    public void deleteToDoItem(ToDoItem item) {
        toDoItems.remove(item);
    }
}
