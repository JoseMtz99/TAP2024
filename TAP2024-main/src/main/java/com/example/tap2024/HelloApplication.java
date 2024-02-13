package com.example.tap2024;

import com.example.tap2024.vistas.Calculadora;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private MenuBar mnbPrincipal;
    private Menu mnParcial1, mnParcial2, mnSalida;
    private MenuItem mitCalculadora, mitSalir;
    private BorderPane bdpPanel;

    @Override
    public void start(Stage stage) throws IOException {
        CrearMenu();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        bdpPanel = new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene = new Scene(bdpPanel);
        scene.getStylesheets().add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    private void CrearMenu() {
        //Menu primer parcial
        mitCalculadora = new MenuItem("Calculadora");
        mnParcial1 = new Menu("Primer parcial");
        mnParcial1.getItems().addAll(mitCalculadora);

        //Menu Segundo parcial
        mnParcial2= new Menu("Segundo parcial");

        //Menu salida
        mnSalida = new Menu("Salida");
        mitSalir = new MenuItem("Salir");
        mnSalida.getItems().addAll(mitSalir);
        mitSalir.setOnAction(actionEvent -> System.exit(0));
        //mnSalida.setOnAction(actionEvent -> System.exit(0));


        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(mnParcial1,mnParcial2,mnSalida);


    }



    public static void main(String[] args) {
        launch();
    }
}