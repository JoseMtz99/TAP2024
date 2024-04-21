package com.example.tap2024;

import com.example.tap2024.componentes.Hilo;
import com.example.tap2024.modelos.Conexion;
import com.example.tap2024.vistas.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private MenuBar mnbPrincipal;
    private Menu mnParcial1, mnParcial2, mnSalida;

    private MenuItem mitCalculadora, mitSalir, mitMemorama, mitCuadroMagico, mitEmpleado, mitPista;
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

        Conexion.crearConexion();

    }

    private void CrearMenu() {

        //Abrir calculadora
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent ->new Calculadora());

        //Abrir Cuadro Magico
        mitCuadroMagico = new MenuItem("Cuadro Magico");
        mitCuadroMagico.setOnAction(actionEvent -> new CuadroMagico());

        //Abrir Memorama
        mitMemorama = new MenuItem("Memorama");
        mitMemorama.setOnAction(actionEvent -> new Memorama());

        //Abrir la vista de empleados
        mitEmpleado=new MenuItem("Empleados Taqueria");
        mitEmpleado.setOnAction(actionEvent -> new AppTaqueria());

        //Abrir Pista
        mitPista= new MenuItem("Pista");
        mitPista.setOnAction(actionEvent -> new Pista());


        //Menu primer parcial
        mnParcial1 = new Menu("Primer parcial");
        mnParcial1.getItems().addAll(mitCalculadora,mitCuadroMagico, mitMemorama, mitEmpleado);


        //Menu Segundo parcial
        mnParcial2= new Menu("Segundo parcial");
        mnParcial2.getItems().addAll(mitPista);


        //Menu salida
        mnSalida = new Menu("Salida");
        mitSalir = new MenuItem("Salir");
        mnSalida.getItems().addAll(mitSalir);
        mitSalir.setOnAction(actionEvent -> System.exit(0));




        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(mnParcial1,mnParcial2,mnSalida);

    }


    public static void main(String[] args) {
        launch();
    }
}