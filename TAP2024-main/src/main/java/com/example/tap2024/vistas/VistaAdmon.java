package com.example.jesusroberto.vistas;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaAdmon extends Stage {
    private Scene escena;
    private HBox hbPrincipal;
    private VBox vbBotones, vbGraficas;
    private Button btnEmpleados, btnProductos, btnCategorias;

    public VistaAdmon() {
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        btnCategorias = new Button();
        Image imgCategorias = new Image(getClass().getResourceAsStream("/Imagenes/categorias.png"));
        ImageView imvCategorias = new ImageView(imgCategorias);
        imvCategorias.setFitWidth(50);
        imvCategorias.setFitHeight(50);
        btnCategorias.setGraphic(imvCategorias);

        btnProductos = new Button();
        Image imgproductos = new Image(getClass().getResourceAsStream("/Imagenes/productos.png"));
        ImageView imvProductos = new ImageView(imgproductos);
        imvProductos.setFitWidth(50);
        imvProductos.setFitHeight(50);
        btnProductos.setGraphic(imvProductos);

        btnEmpleados= new Button();
        Image imgEmpleados = new Image(getClass().getResourceAsStream("/Imagenes/empleados.png"));
        ImageView imvEmpleados = new ImageView(imgEmpleados);
        imvEmpleados.setFitWidth(50);
        imvEmpleados.setFitHeight(50);
        btnEmpleados.setGraphic(imvEmpleados);

        btnEmpleados.setOnAction(actionEvent -> new EmpleadoTaqueria());
        vbBotones = new VBox(btnCategorias,btnProductos, btnEmpleados);
        vbGraficas= new VBox();
        hbPrincipal= new HBox(vbBotones,vbGraficas);
        escena = new Scene(hbPrincipal,500,500);
    }
}
