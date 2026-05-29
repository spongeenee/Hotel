package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.models.RecepcionistaRol;
import com.example.hotel.models.SupervisorRol;
import com.example.hotel.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField usuario;
    @FXML private PasswordField contrasena;
    @FXML private Label loginStatus;

    @FXML
    public void handleLogin(ActionEvent event) throws IOException, SQLException {
        if (usuario.getText().isBlank() || contrasena.getText().isBlank()) {
            if (usuario.getText().isBlank())
                loginStatus.setText("Ingresa usuario");
            if (contrasena.getText().isBlank())
                loginStatus.setText("Ingresa contraseña");
            if (usuario.getText().isBlank() && contrasena.getText().isBlank())
                loginStatus.setText("Ingresa usuario y contraseña");
            loginStatus.setTextFill(Color.CRIMSON);
        }
        else {
            long ID = 1;
            loginStatus.setText("Iniciando sesión...");
            loginStatus.setTextFill(Color.LIGHTSLATEGRAY);

            Parent root = null;
            if (contrasena.getText().equals("1")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("recepcionista-view.fxml"));
                root = fxmlLoader.load();

                RecepcionistaController controller = fxmlLoader.getController();
                controller.setUsuarioID(ID);
            }
            if (contrasena.getText().equals("2")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("supervisor-view.fxml"));
                root = fxmlLoader.load();

                SupervisorController controller = fxmlLoader.getController();
                controller.setUsuarioID(ID);
            }
            if (root != null) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                stage.setTitle("Meru | Sistema de gestion de reservaciones de hotel");
                stage.setScene(new Scene(root));
                stage.show();
                stage.centerOnScreen();
            }
        }
    }
}
