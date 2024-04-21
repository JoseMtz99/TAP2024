package com.example.tap2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginApp extends Stage {
    private Scene escena;
    private BorderPane bpPrincipal;
    private StackPane stackPane;
    private TextField txtUsuario;
    private PasswordField pswContraseña;
    private GridPane gdpPrincipal;
    private Label lblUsuario, lblContraseña;
    private Button btnLogin;

    public LoginApp() {
        crearUI();
        this.setTitle("Login");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        bpPrincipal = new BorderPane();
        stackPane = new StackPane();
        escena = new Scene(stackPane, 400, 300);

        // Crear el objeto Image con la ruta de la imagen
        Image fondo = new Image(getClass().getResourceAsStream("/Images/Fondo.jpeg"));

        // Crear el BackgroundImage con el objeto Image
        BackgroundImage backgroundImage = new BackgroundImage(fondo,
                null, null, null, null);

        // Establecer el BackgroundImage en el BorderPane principal
        bpPrincipal.setBackground(new Background(backgroundImage));

        gdpPrincipal = new GridPane();
        gdpPrincipal.setPadding(new Insets(20));
        gdpPrincipal.setHgap(10);
        gdpPrincipal.setVgap(10);

        lblUsuario = new Label("Usuario:");
        txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingresar su Usuario");

        lblContraseña = new Label("Contraseña:");
        pswContraseña = new PasswordField();
        pswContraseña.setPromptText("Ingrese su Contraseña");

        btnLogin = new Button("Iniciar Sesión");
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = pswContraseña.getText();
            boolean administrador = false;
            if (usuario.equals("Jaime") && contraseña.equals("12345")) {
                abrirAppTaqueria(administrador);
                this.close();
            } else if (usuario.equals("Armando") && contraseña.equals("54321")) {
                administrador = true;
                abrirAppTaqueria(administrador);
                this.close();
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        });

        gdpPrincipal.add(lblUsuario, 0, 0);
        gdpPrincipal.add(txtUsuario, 1, 0);
        gdpPrincipal.add(lblContraseña, 0, 1);
        gdpPrincipal.add(pswContraseña, 1, 1);
        gdpPrincipal.add(btnLogin, 1, 2);

        bpPrincipal.setCenter(gdpPrincipal);
        bpPrincipal.setPadding(new Insets(20));

        // Agregar el BorderPane principal al StackPane
        stackPane.getChildren().add(bpPrincipal);
        btnLogin.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

    }

    private void abrirAppTaqueria(boolean administrador) {
        // Aquí debes abrir la ventana de la clase AppTaqueria
        // Por ejemplo:
         AppTaqueria appTaqueria = new AppTaqueria(administrador);
         appTaqueria.show();
    }
}



