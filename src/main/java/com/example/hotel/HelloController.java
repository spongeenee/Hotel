package com.example.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalTime;

public class HelloController {
    @FXML
    private Label date;
    @FXML
    private DatePicker datePicker;

    @FXML
    protected void showDatePicker() {
        date.setText(datePicker.getValue().atTime(LocalTime.NOON).toString());
    }
}
