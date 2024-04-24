package com.example.tap2024.vistas;

import com.example.tap2024.vistas.EmpleadoTaqueria;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class VistaAdmon extends Stage {
    private Scene escena;

    public VistaAdmon() {
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        Image imgFondo = new Image(getClass().getResourceAsStream("/images/Fondo.jpeg"));
        BackgroundImage backgroundImage = new BackgroundImage(imgFondo, null, null, null, null);
        Background background = new Background(backgroundImage);

        Button btnCategorias = new Button("Categorias");
        Image imgCategorias = new Image(getClass().getResourceAsStream("/Images/categorias.png"));
        ImageView imvCategorias = new ImageView(imgCategorias);
        imvCategorias.setFitWidth(50);
        imvCategorias.setFitHeight(50);
        btnCategorias.setGraphic(imvCategorias);
        btnCategorias.setOnAction(actionEvent -> new Categorias());

        Button btnProductos = new Button("Productos");
        Image imgproductos = new Image(getClass().getResourceAsStream("/Images/productos.png"));
        ImageView imvProductos = new ImageView(imgproductos);
        imvProductos.setFitWidth(50);
        imvProductos.setFitHeight(50);
        btnProductos.setGraphic(imvProductos);

        Button btnEmpleados = new Button("Empleados");
        Image imgEmpleados = new Image(getClass().getResourceAsStream("/Images/empleados.png"));
        ImageView imvEmpleados = new ImageView(imgEmpleados);
        imvEmpleados.setFitWidth(50);
        imvEmpleados.setFitHeight(50);
        btnEmpleados.setGraphic(imvEmpleados);
        btnEmpleados.setOnAction(actionEvent -> new EmpleadoTaqueria());


        VBox vbBotones = new VBox(btnCategorias, btnProductos, btnEmpleados);
        VBox vbGraficas = new VBox();
        HBox hbPrincipal = new HBox(vbBotones, vbGraficas);
        hbPrincipal.setPrefSize(800, 600); // Ajusta el tamaño según sea necesario
        Panel pnContenedor = new Panel();
        pnContenedor.setBackground(background);
        pnContenedor.getChildren().add(hbPrincipal);

        escena = new Scene(pnContenedor, 800, 600);
    }
}

